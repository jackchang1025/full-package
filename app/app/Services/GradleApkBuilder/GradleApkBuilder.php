<?php

declare(strict_types=1);

namespace App\Services\GradleApkBuilder;

use App\Exceptions\GradleApkBuilder\GradleApkBuildException;
use App\Services\ApkBuilder\ApkBuildResult;
use App\Services\ApkBuilder\Contracts\FileSystemInterface;
use App\Services\ApkBuilder\Contracts\ProcessRunnerInterface;
use Illuminate\Support\Facades\Log;

/**
 * Gradle 源码 APK 构建服务
 *
 * 流程: 复制 android/ → 修改配置 → ./gradlew assembleDebug → 收集 APK
 */
final class GradleApkBuilder
{
    private string $androidSourcePath;

    private string $outputDir;

    private string $javaHome;

    private string $androidHome;

    private int $timeout;

    private string $aesKey;

    private bool $cleanupOnSuccess;

    private bool $cleanupOnFailure;

    private string $workDir = '';

    private array $stepStats = [];

    private float $startTime;

    private ?\Closure $progressCallback = null;

    public const STEP_LABELS = [
        'check_environment' => '检查环境',
        'prepare_work_dir' => '准备工作目录',
        'modify_config' => '修改配置',
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
        $this->androidSourcePath = config('gradle-apk-builder.android_source_path', base_path('android'));
        $this->outputDir = config('gradle-apk-builder.output_path', storage_path('app/public/apk/gradle'));
        $this->javaHome = config('gradle-apk-builder.java_home', '/usr/lib/jvm/java-17-openjdk-amd64');
        $this->androidHome = config('gradle-apk-builder.android_home', '/opt/android-sdk');
        $this->timeout = config('gradle-apk-builder.timeout', 300);
        $this->aesKey = config('gradle-apk-builder.aes_key')
            ?? throw new \RuntimeException('gradle-apk-builder.aes_key is not configured');
        $this->cleanupOnSuccess = config('gradle-apk-builder.cleanup_on_success', true);
        $this->cleanupOnFailure = config('gradle-apk-builder.cleanup_on_failure', true);
    }

    public function onProgress(\Closure $callback): self
    {
        $this->progressCallback = $callback;

        return $this;
    }

    /**
     * 构建 APK
     *
     * @throws GradleApkBuildException
     */
    public function build(GradleApkBuildConfig $config): ApkBuildResult
    {
        $this->startTime = microtime(true);
        $this->stepStats = [];

        try {
            $this->runStep('check_environment', fn () => $this->checkEnvironment());
            $this->runStep('prepare_work_dir', fn () => $this->prepareWorkDir());
            $this->runStep('modify_config', fn () => $this->modifyConfig($config));
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

            return new ApkBuildResult(
                path: $result,
                packageName: $config->applicationId,
                stats: $this->stepStats,
                totalTimeMs: $totalTime,
            );
        } catch (GradleApkBuildException $e) {
            if ($this->cleanupOnFailure && ! empty($this->workDir)) {
                $this->cleanup();
            }
            throw $e;
        }
    }

    // ============ 步骤实现 ============

    private function checkEnvironment(): void
    {
        // 检查 JAVA_HOME
        if (! is_dir($this->javaHome)) {
            throw GradleApkBuildException::environmentMissing('JAVA_HOME', "路径不存在: {$this->javaHome}");
        }

        $javaBin = $this->javaHome . '/bin/java';
        if (! is_file($javaBin)) {
            throw GradleApkBuildException::environmentMissing('java', "java 二进制不存在: {$javaBin}");
        }

        // 检查 ANDROID_HOME
        if (! is_dir($this->androidHome)) {
            throw GradleApkBuildException::environmentMissing('ANDROID_HOME', "路径不存在: {$this->androidHome}");
        }

        // 检查源码目录
        if (! is_dir($this->androidSourcePath)) {
            throw GradleApkBuildException::environmentMissing('android_source', "源码目录不存在: {$this->androidSourcePath}");
        }

        $gradlew = $this->androidSourcePath . '/gradlew';
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

        $this->workDir = rtrim($tempBase, '/') . '/gradle-apk-' . uniqid();

        // rsync 复制源码，排除构建缓存
        $result = $this->processRunner->run(
            "rsync -a --exclude='build/' --exclude='.gradle/' --exclude='local.properties' "
            . escapeshellarg($this->androidSourcePath . '/') . ' '
            . escapeshellarg($this->workDir . '/')
        );

        if (! $result->successful()) {
            throw GradleApkBuildException::stepFailed(
                'prepare_work_dir',
                'rsync 复制源码失败',
                $result->output() . $result->errorOutput()
            );
        }

        // 写入 local.properties
        $localProps = "sdk.dir={$this->androidHome}\n";
        $this->fileSystem->put($this->workDir . '/local.properties', $localProps);

        Log::info('GradleApkBuilder: 工作目录准备完成', ['workDir' => $this->workDir]);
    }

    private function modifyConfig(GradleApkBuildConfig $config): void
    {
        $configJson = $config->toConfigJson($this->aesKey);
        $jsonContent = json_encode($configJson, JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE | JSON_UNESCAPED_SLASHES);

        $configPath = $this->workDir . '/app/src/main/assets/config.json';
        $this->fileSystem->put($configPath, $jsonContent . "\n");

        Log::info('GradleApkBuilder: config.json 已更新');
    }

    private function modifyBuildGradle(GradleApkBuildConfig $config): void
    {
        $gradlePath = $this->workDir . '/app/build.gradle';
        $content = $this->fileSystem->get($gradlePath);

        if ($content === null) {
            throw GradleApkBuildException::stepFailed('modify_build_gradle', "无法读取: {$gradlePath}");
        }

        // applicationId (只在非空时修改)
        if (! empty($config->applicationId)) {
            $content = preg_replace(
                '/applicationId\s+"[^"]+"/',
                'applicationId "' . $config->applicationId . '"',
                $content
            );
        }

        // versionCode
        $content = preg_replace(
            '/versionCode\s+\d+/',
            'versionCode ' . $config->versionCode,
            $content
        );

        // versionName
        $content = preg_replace(
            '/versionName\s+"[^"]+"/',
            'versionName "' . $config->versionName . '"',
            $content
        );

        $this->fileSystem->put($gradlePath, $content);

        Log::info('GradleApkBuilder: build.gradle 已更新', [
            'applicationId' => $config->applicationId ?: '(保持默认)',
            'versionCode' => $config->versionCode,
            'versionName' => $config->versionName,
        ]);
    }

    private function modifyStrings(GradleApkBuildConfig $config): void
    {
        $stringsPath = $this->workDir . '/app/src/main/res/values/strings.xml';
        $content = $this->fileSystem->get($stringsPath);

        if ($content === null) {
            throw GradleApkBuildException::stepFailed('modify_strings', "无法读取: {$stringsPath}");
        }

        // app_name
        $content = preg_replace(
            '/<string name="app_name">[^<]*<\/string>/',
            '<string name="app_name">' . $this->escapeXml($config->appName) . '</string>',
            $content
        );

        // accessibility_service_description
        $content = preg_replace(
            '/<string name="accessibility_service_description">[^<]*<\/string>/',
            '<string name="accessibility_service_description">' . $this->escapeXml($config->accessibilityServiceLabel) . '</string>',
            $content
        );

        $this->fileSystem->put($stringsPath, $content);

        Log::info('GradleApkBuilder: strings.xml 已更新', ['appName' => $config->appName]);
    }

    private function replaceIcon(GradleApkBuildConfig $config): void
    {
        if (empty($config->iconPath)) {
            Log::info('GradleApkBuilder: 跳过图标替换（未配置）');

            return;
        }

        $resDir = $this->workDir . '/app/src/main/res';

        // 删除矢量图标 XML，替换为 PNG
        $xmlBackground = $resDir . '/drawable/ic_launcher_background.xml';
        $xmlForeground = $resDir . '/drawable/ic_launcher_foreground.xml';

        if ($this->fileSystem->exists($xmlBackground)) {
            $this->fileSystem->delete($xmlBackground);
        }
        if ($this->fileSystem->exists($xmlForeground)) {
            $this->fileSystem->delete($xmlForeground);
        }

        // 复制图标为 PNG
        $this->fileSystem->copy($config->iconPath, $resDir . '/drawable/ic_launcher_background.png');
        $this->fileSystem->copy($config->iconPath, $resDir . '/drawable/ic_launcher_foreground.png');

        // 更新 adaptive icon XML 引用
        $adaptiveIconPath = $resDir . '/mipmap-anydpi-v26/ic_launcher.xml';
        if ($this->fileSystem->exists($adaptiveIconPath)) {
            $adaptiveContent = '<?xml version="1.0" encoding="utf-8"?>' . "\n"
                . '<adaptive-icon xmlns:android="http://schemas.android.com/apk/res/android">' . "\n"
                . '    <background android:drawable="@drawable/ic_launcher_background" />' . "\n"
                . '    <foreground android:drawable="@drawable/ic_launcher_foreground" />' . "\n"
                . '</adaptive-icon>' . "\n";
            $this->fileSystem->put($adaptiveIconPath, $adaptiveContent);
        }

        Log::info('GradleApkBuilder: 图标已替换', ['source' => $config->iconPath]);
    }

    private function replaceBackground(GradleApkBuildConfig $config): void
    {
        if (empty($config->backgroundPath)) {
            Log::info('GradleApkBuilder: 跳过背景图替换（未配置）');

            return;
        }

        $targetPath = $this->workDir . '/app/src/main/assets/default_bg.png';
        $this->fileSystem->copy($config->backgroundPath, $targetPath);

        Log::info('GradleApkBuilder: 背景图已替换', ['source' => $config->backgroundPath]);
    }

    private function gradleBuild(): void
    {
        $env = "JAVA_HOME={$this->javaHome} ANDROID_HOME={$this->androidHome}";
        $command = "cd " . escapeshellarg($this->workDir)
            . " && chmod +x gradlew"
            . " && {$env} ./gradlew assembleDebug -q 2>&1";

        $result = $this->processRunner->timeout($this->timeout)->run($command);

        if (! $result->successful()) {
            $output = $result->output() . $result->errorOutput();
            Log::error('GradleApkBuilder: Gradle 构建失败', ['output' => $output]);
            throw GradleApkBuildException::stepFailed('gradle_build', 'Gradle 构建失败', $output);
        }

        Log::info('GradleApkBuilder: Gradle 构建完成');
    }

    /**
     * @return string APK 输出路径
     */
    private function collectApk(GradleApkBuildConfig $config): string
    {
        $apkSource = $this->workDir . '/app/build/outputs/apk/debug/app-debug.apk';

        if (! $this->fileSystem->exists($apkSource)) {
            throw GradleApkBuildException::stepFailed('collect_apk', "APK 文件不存在: {$apkSource}");
        }

        // 确保输出目录存在
        $this->fileSystem->ensureDirectoryExists($this->outputDir);

        // 命名: {applicationId}_{versionName}.apk
        $fileName = $config->applicationId . '_' . $config->versionName . '.apk';
        $outputPath = rtrim($this->outputDir, '/') . '/' . $fileName;

        $this->fileSystem->copy($apkSource, $outputPath);

        Log::info('GradleApkBuilder: APK 已输出', ['path' => $outputPath]);

        return $outputPath;
    }

    private function cleanup(): void
    {
        if (empty($this->workDir) || ! is_dir($this->workDir)) {
            return;
        }

        $result = $this->processRunner->run('rm -rf ' . escapeshellarg($this->workDir));

        if ($result->successful()) {
            Log::info('GradleApkBuilder: 临时目录已清理', ['workDir' => $this->workDir]);
        } else {
            Log::warning('GradleApkBuilder: 临时目录清理失败', ['workDir' => $this->workDir]);
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
