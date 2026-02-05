<?php

namespace App\Http\Controllers;

use App\Exceptions\ApkBuilder\ApkBuildException;
use App\Models\AppBuild;
use App\Models\AppTemplate;
use App\Services\ApkBuilder\ApkBuildConfig;
use App\Services\ApkBuilder\ApkBuilder;
use Illuminate\Http\Request;
use Illuminate\Support\Str;
use Inertia\Inertia;
use Inertia\Response;
use Symfony\Component\HttpFoundation\StreamedResponse;

class AppBuildController extends Controller
{
    public function index(Request $request): Response
    {
        $this->authorize('builds.view');
        $builds = AppBuild::where('user_id', $request->user()->id)
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
            return number_format($bytes / 1073741824, 1) . ' GB';
        } elseif ($bytes >= 1048576) {
            return number_format($bytes / 1048576, 1) . ' MB';
        } elseif ($bytes >= 1024) {
            return number_format($bytes / 1024, 1) . ' KB';
        }
        return $bytes . ' B';
    }

    public function create(Request $request): Response
    {
        $this->authorize('builds.create');
        $templates = AppTemplate::where('is_active', true)->get();
        $userId = $request->user()->id;

        $iconsPath = config('apk-builder.icons_path') . '/' . $userId;
        $icons = $this->listUserImages($iconsPath, 'icons', $userId);

        $bgPath = config('apk-builder.backgrounds_path') . '/' . $userId;
        $backgrounds = $this->listUserImages($bgPath, 'backgrounds', $userId);

        return Inertia::render('Builds/Create', [
            'templates' => $templates,
            'icons' => $icons,
            'backgrounds' => $backgrounds,
        ]);
    }

    public function store(Request $request)
    {
        $this->authorize('builds.create');
        $validated = $this->validateBuildRequest($request);

        $packageName = $validated['package_name'] ?? $this->generatePackageName();
        $version = $validated['version'] ?? $this->generateVersion();
        $buildConfig = $this->prepareBuildConfig($validated);

        $build = AppBuild::create([
            'user_id' => $request->user()->id,
            'template_id' => $validated['template_id'] ?? null,
            'name' => $validated['name'],
            'package_name' => $packageName,
            'version' => $version,
            'websocket_url' => config('apk-builder.defaults.websocket_url'),
            'client_name' => $validated['client_name'] ?? '',
            'icon_path' => $validated['icon_path'] ?? '',
            'background_path' => $validated['background_path'] ?? 'black',
            'is_custom' => $validated['is_custom'] ?? true,
            'build_config' => $buildConfig,
        ]);

        return redirect()->route('builds.show', $build)
            ->with('success', 'APK 构建任务已创建');
    }

    public function stream(Request $request): StreamedResponse
    {
        $this->authorize('builds.create');
        // 预处理布尔值参数（URL 参数是字符串）
        $this->preprocessBooleanParams($request);
        $validated = $this->validateBuildRequest($request);
        $userId = $request->user()->id;
        $userEmail = $request->user()->email;

        $packageName = $validated['package_name'] ?? $this->generatePackageName();
        $version = $validated['version'] ?? $this->generateVersion();
        $buildConfigData = $this->prepareBuildConfig($validated);

        return response()->stream(function () use ($validated, $userId, $userEmail, $packageName, $version, $buildConfigData) {
            if (ob_get_level()) {
                ob_end_clean();
            }

            set_time_limit(0);
            session_write_close();

            $config = ApkBuildConfig::fromArray(array_merge($buildConfigData, [
                'app_id' => $packageName,
                'user_id' => (string) $userId,
                'email' => $userEmail,
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

                $build = AppBuild::create([
                    'user_id' => $config->userId,
                    'template_id' => null,
                    'name' => $config->appName,
                    'package_name' => $config->appId,
                    'version' => $config->appVersion,
                    'websocket_url' => $config->websocketUrl,
                    'client_name' => $config->clientName,
                    'icon_path' => $config->iconPath,
                    'background_path' => $config->backgroundPath,
                    'file_path' => $result->path,
                    'is_custom' => true,
                    'build_config' => $config->toArray(),
                    'build_stats' => $result->stats,
                    'completed_at' => now(),
                ]);

                $this->sendSSE([
                    'type' => 'complete',
                    'build_id' => $build->id,
                    'path' => $result->path,
                    'duration' => $result->totalTimeMs,
                ]);
            } catch (ApkBuildException|\Throwable $e) {
                $this->sendSSE([
                    'type' => 'error',
                    'error' => '构建失败: ' . $e->getMessage(),
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
        echo "data: " . json_encode($data, JSON_UNESCAPED_UNICODE) . "\n\n";

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
        $this->authorize('builds.view');
        abort_if($build->user_id !== $request->user()->id, 403);

        $build->load('template');
        $build->append(['download_url', 'build_duration', 'icon_url', 'background_url', 'share_url']);

        return Inertia::render('Builds/Show', [
            'build' => $build,
            'backUrl' => route('builds.index'),
        ]);
    }

    public function destroy(Request $request, AppBuild $build)
    {
        $this->authorize('builds.delete');
        abort_if($build->user_id !== $request->user()->id, 403);

        $build->delete();

        return redirect()->route('builds.index')
            ->with('success', 'APK 构建已删除');
    }

    private function validateBuildRequest(Request $request): array
    {
        return $request->validate([
            'template_id' => 'nullable|exists:app_templates,id',
            'name' => 'required|string|max:32',
            'package_name' => 'nullable|string|max:255|regex:/^[a-zA-Z][a-zA-Z0-9_]*(\.[a-zA-Z][a-zA-Z0-9_]*)*$/',
            'version' => 'nullable|string|max:20|regex:/^\d+(\.\d+){0,2}$/',
            'is_custom' => 'boolean',

            'client_name' => 'nullable|string|max:16',
            'app_url' => 'nullable|string|max:500',

            'lng_short' => 'nullable|string|max:1000',
            'use_atoprims' => 'nullable|string|max:100',
            'login_dis' => 'nullable|string|max:50',
            'login_btn' => 'nullable|string|max:50',

            'install_type' => 'nullable|string|in:f,d',
            'install_type2' => 'nullable|string|in:g,s',
            'user_allprims' => 'nullable|string|in:0,1',
            'user_blackprims' => 'nullable|string|in:0,1',

            'hide_type' => 'nullable|string|in:direct,uninstall,prompt,f',
            'use_antkill' => 'nullable|string|in:0,1',
            'diao_type' => 'nullable|string|in:0,1',
            'hidden_app' => 'nullable|string|in:0,1',
            'use_draw' => 'nullable|string|in:0,1',
            'open_access' => 'nullable|string|in:0,1',
            'use_access' => 'nullable|string|in:0,1',

            'icon_path' => 'nullable|string|max:255',
            'background_path' => 'nullable|string|max:255',
            'abg_path' => 'nullable|string|max:255',
        ]);
    }

    private function prepareBuildConfig(array $validated): array
    {
        $hideTypeMap = [
            'direct' => 'f',
            'uninstall' => 'u',
            'prompt' => 'p',
            'f' => 'f',
        ];

        $buildConfig = [
            'client_name' => $validated['client_name'] ?? '',
            'app_url' => $validated['app_url'] ?? '',
            'lng_short' => $validated['lng_short'] ?? '',
            'use_atoprims' => $validated['use_atoprims'] ?? '加载中~请勿操作或锁屏！',
            'login_dis' => $validated['login_dis'] ?? '',
            'login_btn' => $validated['login_btn'] ?? '确定',
            'install_type' => $validated['install_type'] ?? 'f',
            'user_allprims' => $validated['user_allprims'] ?? '1',
            'user_blackprims' => $validated['user_blackprims'] ?? '1',
            'hide_type' => $hideTypeMap[$validated['hide_type'] ?? 'uninstall'] ?? 'u',
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
        $suffix = Str::lower(Str::random(6));

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
     * 预处理布尔值参数（将字符串 "true"/"false" 转换为实际布尔值）
     */
    private function preprocessBooleanParams(Request $request): void
    {
        $booleanFields = ['is_custom'];

        foreach ($booleanFields as $field) {
            if ($request->has($field)) {
                $value = $request->input($field);
                if (is_string($value)) {
                    $request->merge([
                        $field => filter_var($value, FILTER_VALIDATE_BOOLEAN, FILTER_NULL_ON_FAILURE) ?? false
                    ]);
                }
            }
        }
    }

    private function listUserImages(string $path, string $type, int $userId): array
    {
        $images = [];
        if (is_dir($path)) {
            $files = scandir($path);
            foreach ($files as $file) {
                if ($file === '.' || $file === '..') continue;
                $ext = strtolower(pathinfo($file, PATHINFO_EXTENSION));
                if (in_array($ext, ['png', 'jpg', 'jpeg'])) {
                    $images[] = [
                        'name' => $file,
                        'url' => '/storage/' . $type . '/' . $userId . '/' . $file,
                    ];
                }
            }
        }
        return $images;
    }
}
