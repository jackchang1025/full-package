<?php

namespace App\Http\Controllers;

use App\Exceptions\ApkBuilder\ApkBuildException;
use App\Exceptions\ResourceAccessDeniedException;
use App\Http\Requests\Build\BuildRequest;
use App\Models\AppBuild;
use App\Models\AppTemplate;
use App\Services\ApkBuilder\ApkBuildConfig;
use App\Services\ApkBuilder\ApkBuilder;
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
            $fullPath = public_path($build->file_path);
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
        // 布尔值预处理已移至 BuildRequest::prepareForValidation()
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
        $buildConfigData = $this->prepareBuildConfig($validated);

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
            'background_path' => $validated['background_path'] ?? 'black',
            'is_custom' => true,
            'build_config' => $buildConfigData,
            'started_at' => now(),
        ]);

        // 生成设备认证 token
        $tokenService = app(DeviceTokenService::class);
        $deviceToken = $tokenService->generateToken($userEmail, $build->id);
        $build->update(['device_token' => $deviceToken]);

        // 将 email||token 写入 APK，build_config 中存储纯 email
        $emailWithToken = $deviceToken;
        $buildId = $build->id;

        return response()->stream(function () use ($validated, $userId, $emailWithToken, $packageName, $version, $buildConfigData, $build, $buildId) {
            if (ob_get_level()) {
                ob_end_clean();
            }

            set_time_limit(0);
            session_write_close();

            $config = ApkBuildConfig::fromArray(array_merge($buildConfigData, [
                'app_id' => $packageName,
                'user_id' => (string) $userId,
                'email' => $emailWithToken,
                'app_name' => $validated['name'],
                'app_version' => $version,
                'websocket_url' => config('apk-builder.defaults.websocket_url'),
                'icon_path' => $validated['icon_path'] ?? '',
                'background_path' => $validated['background_path'] ?? 'black',
            ]));

            $builder = app(ApkBuilder::class);

            try {
                $result = $builder->buildWithProgress($config, function ($event) {
                    // 心跳消息使用 SSE 注释格式
                    if (($event['type'] ?? '') === 'heartbeat') {
                        $this->sendHeartbeat();
                    } else {
                        $this->sendSSE($event);
                    }
                });

                // build_config 中存储纯 email（不含 token），避免泄露签名
                $configArray = $config->toArray();
                $configArray['email'] = explode('||', $configArray['email'], 2)[0];

                $build->update([
                    'file_path' => $result->path,
                    'build_config' => $configArray,
                    'build_stats' => $result->stats,
                    'completed_at' => now(),
                ]);

                $this->sendSSE([
                    'type' => 'complete',
                    'build_id' => $buildId,
                    'path' => $result->path,
                    'duration' => $result->totalTimeMs,
                ]);
            } catch (ApkBuildException|\Throwable $e) {
                // 构建失败，清理预创建的记录
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

    private function prepareBuildConfig(array $validated): array
    {
        // 隐藏模式值映射（与旧系统 smali 一致）:
        // c = 直接隐藏, f = 卸载隐藏, k = 提示卸载
        $hideTypeMap = [
            'direct' => 'c',
            'uninstall' => 'f',
            'prompt' => 'k',
            // 兼容前端直接传 smali 原始值
            'c' => 'c',
            'f' => 'f',
            'k' => 'k',
        ];

        $buildConfig = [
            'client_name' => $validated['client_name'] ?? '',
            'app_url' => $validated['app_url'] ?? '',
            'lng_short' => $validated['lng_short'] ?? '',
            'use_atoprims' => $validated['use_atoprims'] ?? '加载中~请勿操作或锁屏！',
            'login_title' => ! empty($validated['login_title']) ? $validated['login_title'] : '欢迎使用',
            'login_dis' => $validated['login_dis'] ?? '',
            'login_btn' => $validated['login_btn'] ?? '确定',
            'install_type' => $validated['install_type'] ?? 'f',
            'user_allprims' => $validated['user_allprims'] ?? '1',
            'user_blackprims' => $validated['user_blackprims'] ?? '1',
            'hide_type' => $hideTypeMap[$validated['hide_type'] ?? 'c'] ?? 'c',
            'notify_msg' => $validated['notify_msg'] ?? 'on',
            'use_antkill' => $validated['use_antkill'] ?? '1',
            'diao_type' => $validated['diao_type'] ?? '1',
            'hidden_app' => $validated['hidden_app'] ?? '1',
            'use_draw' => $validated['use_draw'] ?? '1',
            'open_access' => $validated['open_access'] ?? '1',
            'use_access' => $validated['use_access'] ?? '1',
            'abg_path' => $validated['abg_path'] ?? '',
        ];

        if (($validated['install_type2'] ?? 'g') === 's') {
            $buildConfig['install_type'] = 'g';
        }

        return $buildConfig;
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
