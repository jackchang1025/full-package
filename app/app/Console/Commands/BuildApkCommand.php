<?php

declare(strict_types=1);

namespace App\Console\Commands;

use App\Exceptions\ApkBuilder\ApkBuildException;
use App\Models\AppBuild;
use App\Services\ApkBuilder\ApkBuildConfig;
use App\Services\ApkBuilder\ApkBuilder;
use App\Services\ApkBuilder\ApkBuildResult;
use Carbon\Carbon;
use Illuminate\Console\Command;

class BuildApkCommand extends Command
{
    protected $signature = 'apk:build
        {--app-id= : 包名 (如 com.example.app)}
        {--user-id= : 用户 ID}
        {--app-name= : 应用名称}
        {--app-version=1.0 : 版本号}
        {--websocket-url= : WebSocket 完整地址 (如 ws://example.com:8081)}
        {--client-name= : 客户端标识}
        {--email= : 用户邮箱}
        {--app-url= : 应用 URL}
        {--icon= : 图标文件路径}
        {--background=black : 背景图路径或 black}
        {--junk-classes : 启用垃圾类生成}
        {--shuffle-classes : 启用类名混淆}
        {--protect : 启用 APK 保护}
        {--modify-dex : 启用 DEX 修改}
        {--config= : JSON 配置文件路径}
        {--save : 保存构建记录到数据库}';

    protected $description = '构建 APK 文件';

    private ?Carbon $startedAt = null;

    public function handle(ApkBuilder $builder): int
    {
        $this->info('');
        $this->info('╔════════════════════════════════════════╗');
        $this->info('║       飞鹰管理系统 - APK 构建工具       ║');
        $this->info('╚════════════════════════════════════════╝');
        $this->info('');

        $config = $this->buildConfig();

        if (!$config) {
            return self::FAILURE;
        }

        $errors = $config->validate();
        if (!empty($errors)) {
            $this->error('配置验证失败:');
            foreach ($errors as $error) {
                $this->line("  • {$error}");
            }
            return self::FAILURE;
        }

        $this->displayConfig($config);

        if (!$this->confirm('确认开始构建?', true)) {
            $this->warn('构建已取消');
            return self::SUCCESS;
        }

        $this->info('');
        $this->info('开始构建...');
        $this->newLine();

        $progressBar = $this->output->createProgressBar(12);
        $progressBar->setFormat(' %current%/%max% [%bar%] %percent:3s%% %message%');
        $progressBar->setMessage('初始化...');
        $progressBar->start();

        $this->startedAt = now();

        try {
            $result = $builder->build($config);

            $progressBar->setMessage('完成');
            $progressBar->finish();
            $this->newLine(2);

            $this->displaySuccess($result);

            if ($this->option('save')) {
                $build = $this->saveToDatabase($config, $result);
                $this->info("构建记录已保存，ID: {$build->id}");
            }

            return self::SUCCESS;
        } catch (ApkBuildException $e) {
            $progressBar->setMessage('失败');
            $progressBar->finish();
            $this->newLine(2);

            $this->displayError($e);

            if ($this->option('save')) {
                $build = $this->saveFailedToDatabase($config, $e->getMessage());
                $this->info("构建失败记录已保存，ID: {$build->id}");
            }

            return self::FAILURE;
        }
    }

    private function saveToDatabase(ApkBuildConfig $config, ApkBuildResult $result): AppBuild
    {
        return AppBuild::create([
            'user_id' => $config->userId,
            'package_name' => $config->appId,
            'name' => $config->appName,
            'version' => $config->appVersion,
            'websocket_url' => $config->websocketUrl,
            'client_name' => $config->clientName,
            'icon_path' => $config->iconPath,
            'background_path' => $config->backgroundPath,
            'file_path' => $result->path,
            'status' => 'completed',
            'is_custom' => true,
            'build_config' => $config->toArray(),
            'build_stats' => $result->stats,
            'started_at' => $this->startedAt,
            'completed_at' => now(),
        ]);
    }

    private function saveFailedToDatabase(ApkBuildConfig $config, string $errorMessage): AppBuild
    {
        return AppBuild::create([
            'user_id' => $config->userId,
            'package_name' => $config->appId,
            'name' => $config->appName,
            'version' => $config->appVersion,
            'websocket_url' => $config->websocketUrl,
            'client_name' => $config->clientName,
            'icon_path' => $config->iconPath,
            'background_path' => $config->backgroundPath,
            'file_path' => null,
            'status' => 'failed',
            'is_custom' => true,
            'build_config' => $config->toArray(),
            'build_stats' => null,
            'error_message' => $errorMessage,
            'started_at' => $this->startedAt,
            'completed_at' => now(),
        ]);
    }

    private function displaySuccess(ApkBuildResult $result): void
    {
        $this->info('╔════════════════════════════════════════╗');
        $this->info('║            ✅ 构建成功!                 ║');
        $this->info('╚════════════════════════════════════════╝');
        $this->newLine();

        $this->table(
            ['项目', '值'],
            [
                ['输出路径', $result->path],
                ['总耗时', $result->formatTime()],
            ]
        );

        if (!empty($result->stats)) {
            $this->newLine();
            $this->info('构建步骤耗时:');
            $rows = [];
            foreach ($result->stats as $step => $timeMs) {
                $rows[] = [$step, $result->formatTime($timeMs)];
            }
            $this->table(['步骤', '耗时'], $rows);
        }
    }

    private function displayError(ApkBuildException $e): void
    {
        $this->error('╔════════════════════════════════════════╗');
        $this->error('║            ❌ 构建失败!                 ║');
        $this->error('╚════════════════════════════════════════╝');
        $this->newLine();
        $this->error("错误: {$e->getMessage()}");

        if (!empty($e->context)) {
            $this->newLine();
            $this->warn('详细信息:');
            foreach ($e->context as $key => $value) {
                $this->line("  {$key}: " . (is_array($value) ? json_encode($value) : $value));
            }
        }
    }

    private function buildConfig(): ?ApkBuildConfig
    {
        $configFile = $this->option('config');

        if ($configFile) {
            return $this->loadConfigFromFile($configFile);
        }

        return $this->buildConfigFromOptions();
    }

    private function loadConfigFromFile(string $path): ?ApkBuildConfig
    {
        if (!file_exists($path)) {
            $this->error("配置文件不存在: {$path}");
            return null;
        }

        $json = file_get_contents($path);
        $data = json_decode($json, true);

        if (json_last_error() !== JSON_ERROR_NONE) {
            $this->error('配置文件 JSON 格式错误: ' . json_last_error_msg());
            return null;
        }

        return ApkBuildConfig::fromArray($data);
    }

    private function buildConfigFromOptions(): ApkBuildConfig
    {
        $appId = $this->option('app-id') ?: $this->ask('请输入包名 (如 com.example.app)');
        $userId = $this->option('user-id') ?: $this->ask('请输入用户 ID');
        $appName = $this->option('app-name') ?: $this->ask('请输入应用名称');
        $websocketUrl = $this->option('websocket-url') ?: config('apk-builder.defaults.websocket_url');

        return ApkBuildConfig::fromArray([
            'app_id' => $appId,
            'user_id' => $userId,
            'app_name' => $appName,
            'app_version' => $this->option('app-version'),
            'websocket_url' => $websocketUrl,
            'client_name' => $this->option('client-name') ?? '',
            'email' => $this->option('email') ?? '',
            'app_url' => $this->option('app-url') ?? '',
            'icon_path' => $this->option('icon') ?? '',
            'background_path' => $this->option('background'),
            'enable_junk_classes' => $this->option('junk-classes'),
            'enable_class_shuffle' => $this->option('shuffle-classes'),
            'enable_apk_protection' => $this->option('protect'),
            'enable_dex_modification' => $this->option('modify-dex'),
        ]);
    }

    private function displayConfig(ApkBuildConfig $config): void
    {
        $this->info('构建配置:');
        $rows = [
            ['包名', $config->appId],
            ['用户 ID', $config->userId],
            ['应用名称', $config->appName],
            ['版本号', $config->appVersion],
            ['WebSocket 地址', $config->websocketUrl],
        ];

        if ($config->email) {
            $rows[] = ['邮箱', $config->email];
        }
        if ($config->clientName) {
            $rows[] = ['客户端标识', $config->clientName];
        }

        $rows[] = ['图标', $config->iconPath ?: '(默认)'];
        $rows[] = ['背景', $config->backgroundPath ?: 'black'];
        $rows[] = ['垃圾类生成', $config->enableJunkClasses ? '✓' : '✗'];
        $rows[] = ['类名混淆', $config->enableClassShuffle ? '✓' : '✗'];
        $rows[] = ['APK 保护', $config->enableApkProtection ? '✓' : '✗'];
        $rows[] = ['DEX 修改', $config->enableDexModification ? '✓' : '✗'];

        $this->table(['参数', '值'], $rows);
    }

}
