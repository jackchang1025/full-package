<?php

declare(strict_types=1);

namespace App\Services\ApkBuilder;

use App\Exceptions\ApkBuilder\ApkBuildException;
use App\Services\ApkBuilder\Contracts\FileSystemInterface;
use App\Services\ApkBuilder\Contracts\ProcessRunnerInterface;
use Illuminate\Support\Facades\Log;
use RecursiveDirectoryIterator;
use RecursiveIteratorIterator;

final class ApkBuilder
{
    private string $templateDir;
    private ?string $stubZipPath;
    private string $toolsDir;
    private string $outputDir;
    private string $workDir = '';
    private string $buildDir = '';
    private string $assetsKey = '';
    private array $stepStats = [];
    private float $startTime;
    private bool $buildSucceeded = false;
    private ?\Closure $progressCallback = null;

    private ?object $smaliProcessor = null;
    private Encryptor $encryptor;
    private \Closure $smaliProcessorFactory;
    private \Closure $obfuscatorFactory;
    private \Closure $apkProtectorFactory;

    private FileSystemInterface $fileSystem;
    private ProcessRunnerInterface $processRunner;

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

    public function __construct(
        ?Encryptor $encryptor = null,
        ?callable $smaliProcessorFactory = null,
        ?callable $obfuscatorFactory = null,
        ?callable $apkProtectorFactory = null,
        ?FileSystemInterface $fileSystem = null,
        ?ProcessRunnerInterface $processRunner = null,
    ) {
        $this->templateDir = config('apk-builder.template_path');
        $this->stubZipPath = config('apk-builder.stub_zip_path')
            ?? storage_path('app/apk/apkstub/apkstub.zip');
        $this->toolsDir = config('apk-builder.tools_path');
        $this->outputDir = config('apk-builder.output_path');
        $this->encryptor = $encryptor ?? new Encryptor();
        $this->smaliProcessorFactory = $smaliProcessorFactory ?? fn(string $buildDir): SmaliProcessor => new SmaliProcessor($buildDir);
        $this->obfuscatorFactory = $obfuscatorFactory ?? fn(string $buildDir): Obfuscator => new Obfuscator($buildDir);
        $this->apkProtectorFactory = $apkProtectorFactory ?? fn(): ApkProtector => new ApkProtector();
        $this->fileSystem = $fileSystem ?? new LaravelFileSystem();
        $this->processRunner = $processRunner ?? new LaravelProcessRunner();
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
        $this->smaliProcessor = null;

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
            $this->handleBuildFailure($e->getMessage());
            throw $e;
        } catch (\Throwable $e) {
            Log::channel('apk')->error('Build failed with unexpected error', [
                'app_id' => $config->appId,
                'error' => $e->getMessage(),
                'trace' => $e->getTraceAsString(),
            ]);
            $this->handleBuildFailure($e->getMessage());
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

    private function handleBuildFailure(string $errorMessage): void
    {
        $this->emitProgress(['type' => 'error', 'error' => $errorMessage]);
        $this->buildSucceeded = false;
        $this->cleanup();
    }

    private function getSmaliProcessor(): object
    {
        if ($this->smaliProcessor === null) {
            $this->smaliProcessor = ($this->smaliProcessorFactory)($this->buildDir);
        }

        return $this->smaliProcessor;
    }

    private function checkDependencies(): void
    {
        if (!$this->fileSystem->isDirectory($this->templateDir) || !$this->templateIsValid()) {
            $this->extractTemplateFromZip();
        }

        if (!$this->fileSystem->isDirectory($this->templateDir)) {
            throw ApkBuildException::templateNotFound($this->templateDir);
        }

        if (!$this->templateIsValid()) {
            $zipPath = $this->stubZipPath ?? 'storage/app/apk/apkstub/apkstub.zip';
            $zipExists = !empty($this->stubZipPath) && $this->fileSystem->exists($this->stubZipPath);
            throw new ApkBuildException(
                'APK 模板不完整：缺少 My_Configs.smali 等必要文件。' .
                    ($zipExists
                        ? ' 解压 apkstub.zip 失败，请检查 ZIP 文件完整性。'
                        : " 请将 apkstub.zip 部署到 {$zipPath} 后重试。"),
                [
                    'template_dir' => $this->templateDir,
                    'stub_zip_path' => $this->stubZipPath,
                    'stub_zip_exists' => $zipExists,
                ]
            );
        }

        $apktoolPath = $this->toolsDir . '/apktool.jar';
        if (!$this->fileSystem->exists($apktoolPath)) {
            throw ApkBuildException::toolNotFound('apktool.jar', $apktoolPath);
        }

        $result = $this->processRunner->run('java -version');
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
        if (empty($this->stubZipPath) || !$this->fileSystem->exists($this->stubZipPath)) {
            Log::channel('apk')->warning('Stub ZIP file not found or not configured', ['path' => $this->stubZipPath]);
            return;
        }

        Log::channel('apk')->info('Extracting template from ZIP', [
            'zip' => $this->stubZipPath,
            'target' => $this->templateDir,
        ]);

        $zip = new \ZipArchive();
        $result = $zip->open($this->stubZipPath);

        if ($result !== true) {
            throw new ApkBuildException(
                "无法打开模板 ZIP 文件: " . self::getZipArchiveErrorMessage($result),
                ['zip' => $this->stubZipPath, 'error_code' => $result]
            );
        }

        $parentDir = dirname($this->templateDir);
        $this->fileSystem->ensureDirectoryExists($parentDir);

        if (!is_writable($parentDir)) {
            $zip->close();
            throw new ApkBuildException("目标目录不可写", [
                'parent_dir' => $parentDir,
                'permissions' => substr(sprintf('%o', fileperms($parentDir)), -4),
            ]);
        }

        if ($this->fileSystem->isDirectory($this->templateDir)) {
            $this->fileSystem->deleteDirectory($this->templateDir);
        }

        if (!$zip->extractTo($this->templateDir)) {
            $statusMessage = $zip->getStatusString();
            $zip->close();
            throw new ApkBuildException("解压模板 ZIP 文件失败: {$statusMessage}", [
                'zip' => $this->stubZipPath,
                'target' => $this->templateDir,
                'zip_status' => $statusMessage,
                'disk_free_space' => disk_free_space($parentDir),
            ]);
        }

        $zip->close();

        $this->processRunner->run("chmod -R 755 " . escapeshellarg($this->templateDir));

        Log::channel('apk')->info('Template extracted successfully', ['target' => $this->templateDir]);
    }

    /**
     * 检查模板目录是否包含构建所需的核心文件
     * （用于区分 deploy 创建的空 template 目录与有效解压内容）
     */
    private function templateIsValid(): bool
    {
        return $this->fileSystem->exists($this->templateDir . '/' . ApkBuilderConstants::CONFIGS_SMALI_RELATIVE);
    }

    private function getBaseTempDir(): string
    {
        $tempPath = config('apk-builder.temp_path');

        return !empty($tempPath) ? $tempPath : sys_get_temp_dir();
    }

    private function isValidAssetFile(?string $path): bool
    {
        return $path !== null && $this->fileSystem->exists($path) && $this->fileSystem->size($path) > ApkBuilderConstants::MIN_ASSET_FILE_SIZE;
    }

    private function prepareWorkDir(): void
    {
        $this->cleanOldBuildCache();

        $baseDir = $this->getBaseTempDir();
        $this->workDir = $baseDir . '/apk_build_' . uniqid();
        $this->buildDir = $this->workDir . '/apk_source';

        $this->fileSystem->ensureDirectoryExists($this->workDir);
        $this->copyDirectory($this->templateDir, $this->buildDir);

        $requiredSmali = $this->buildDir . '/' . ApkBuilderConstants::CONFIGS_SMALI_RELATIVE;
        if (!$this->fileSystem->exists($requiredSmali)) {
            throw new ApkBuildException(
                'APK 工作目录缺少必要文件，请确保模板已正确解压。',
                [
                    'build_dir' => $this->buildDir,
                    'missing_file' => ApkBuilderConstants::CONFIGS_SMALI_RELATIVE,
                ]
            );
        }

        $this->assetsKey = Encryptor::generateKey();
    }

    private function cleanOldBuildCache(): void
    {
        $baseDir = $this->getBaseTempDir();
        $pattern = $baseDir . '/apk_build_*';
        $oldDirs = $this->fileSystem->glob($pattern, GLOB_ONLYDIR);

        foreach ($oldDirs as $dir) {
            $this->fileSystem->deleteDirectory($dir);
        }
    }

    private function modifySmali(ApkBuildConfig $config): void
    {
        $this->getSmaliProcessor()->modifyConfig($config, $this->assetsKey, $this->encryptor);
    }

    private function modifyManifest(ApkBuildConfig $config): void
    {
        $manifestPath = $this->buildDir . ApkBuilderConstants::MANIFEST_PATH;
        $content = $this->fileSystem->get($manifestPath);

        $oldPackage = ApkBuilderConstants::DEFAULT_PACKAGE;
        if ($oldPackage !== $config->appId) {
            $content = str_replace($oldPackage, $config->appId, $content);
            $this->getSmaliProcessor()->renamePackage($oldPackage, $config->appId);
        }

        $content = str_replace('@drawable/mylogo', '@drawable/app_icon', $content);
        $this->fixResourceReferences($content);

        $this->fileSystem->put($manifestPath, $content);
    }

    private static function getZipArchiveErrorMessage(int $code): string
    {
        return ApkBuilderConstants::ZIP_ERROR_MESSAGES[$code] ?? "未知错误 ({$code})";
    }

    private function fixResourceReferences(string &$content): void
    {
        $xmlDir = $this->buildDir . ApkBuilderConstants::XML_DIR_PATH;
        $this->fileSystem->ensureDirectoryExists($xmlDir);

        preg_match_all('/@xml\/([a-zA-Z0-9_]+)/', $content, $matches);

        foreach (array_unique($matches[1] ?? []) as $name) {
            $file = "{$xmlDir}/{$name}.xml";

            if ($this->fileSystem->exists($file)) {
                continue;
            }

            $existing = glob("{$xmlDir}/*.xml");

            if (!empty($existing)) {
                $this->fileSystem->copy($existing[0], $file);
            } else {
                $this->fileSystem->put($file, ApkBuilderConstants::DEFAULT_ACCESSIBILITY_XML);
            }
        }
    }

    private function modifyResources(ApkBuildConfig $config): void
    {
        $stringsPath = $this->buildDir . ApkBuilderConstants::STRINGS_XML_PATH;

        if ($this->fileSystem->exists($stringsPath)) {
            $content = $this->fileSystem->get($stringsPath);

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

            $this->fileSystem->put($stringsPath, $content);
        }

        $ymlPath = $this->buildDir . ApkBuilderConstants::APKTOOL_YML_PATH;

        if ($this->fileSystem->exists($ymlPath)) {
            $content = $this->fileSystem->get($ymlPath);
            $versionCode = (int) str_replace('.', '', $config->appVersion) * 100;
            $content = preg_replace('/versionCode: \d+/', 'versionCode: ' . $versionCode, $content);
            $content = preg_replace('/versionName: [^\n]+/', 'versionName: ' . $config->appVersion, $content);
            $this->fileSystem->put($ymlPath, $content);
        }
    }

    private function replaceIcon(ApkBuildConfig $config): void
    {
        $iconPath = $this->resolveIconPath($config);

        if (!$iconPath) {
            throw ApkBuildException::iconNotFound($config->iconPath ?: 'default icon');
        }

        foreach (ApkBuilderConstants::DRAWABLE_DIRS as $dir) {
            $target = $this->buildDir . '/res/' . $dir;

            if (!$this->fileSystem->isDirectory($target)) {
                continue;
            }

            $mylogoPath = $target . '/' . ApkBuilderConstants::ICON_FILENAME;
            if ($this->fileSystem->exists($mylogoPath)) {
                $this->fileSystem->delete($mylogoPath);
            }
            $this->fileSystem->copy($iconPath, $mylogoPath);
            $this->fileSystem->copy($iconPath, $target . '/' . ApkBuilderConstants::APP_ICON_FILENAME);
        }

        Log::channel('apk')->debug('Icon replaced', ['icon' => $iconPath]);
    }

    private function resolveIconPath(ApkBuildConfig $config): ?string
    {
        $configIcon = $this->resolveStoragePath($config->iconPath);
        if ($this->isValidAssetFile($configIcon)) {
            return $configIcon;
        }

        $defaultIcon = config('apk-builder.default_icon');
        if ($this->isValidAssetFile($defaultIcon)) {
            return $defaultIcon;
        }

        $templateIcon = $this->templateDir . '/res/drawable/' . ApkBuilderConstants::ICON_FILENAME;
        return $this->fileSystem->exists($templateIcon) ? $templateIcon : null;
    }

    private function replaceBackground(ApkBuildConfig $config): void
    {
        $bgPath = $this->resolveBackgroundPath($config);

        if (!$bgPath) {
            Log::channel('apk')->debug('Using black background');
            $this->removeBlackuiResource();
            return;
        }

        $target = $this->buildDir . ApkBuilderConstants::BLACKUI_PATH;

        if ($this->fileSystem->exists($target)) {
            $this->fileSystem->delete($target);
        }

        $this->fileSystem->copy($bgPath, $target);
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

        return $this->isValidAssetFile($bgPath) ? $bgPath : null;
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
        $blackuiPath = $this->buildDir . ApkBuilderConstants::BLACKUI_PATH;

        if ($this->fileSystem->exists($blackuiPath)) {
            $this->fileSystem->delete($blackuiPath);
        }

        $publicXmlPath = $this->buildDir . ApkBuilderConstants::PUBLIC_XML_PATH;

        if ($this->fileSystem->exists($publicXmlPath)) {
            $content = $this->fileSystem->get($publicXmlPath);
            $content = preg_replace(
                '/<public[^>]*type="drawable"[^>]*name="blackui"[^>]*\/>\s*/i',
                '',
                $content
            );
            $this->fileSystem->put($publicXmlPath, $content);
        }
    }

    private function generateJunkClasses(ApkBuildConfig $config): void
    {
        $obfuscator = ($this->obfuscatorFactory)($this->buildDir);
        $count = $obfuscator->generateJunkClasses($config->junkClassCount, $config->junkMethodCount);
        Log::channel('apk')->debug('Generated junk classes', ['count' => $count]);
    }

    private function shuffleClasses(): void
    {
        $obfuscator = ($this->obfuscatorFactory)($this->buildDir);
        $count = $obfuscator->shuffleClassNames();
        Log::channel('apk')->debug('Shuffled class names', ['count' => $count]);
    }

    private function encryptResources(): void
    {
        $assetsPath = $this->buildDir . ApkBuilderConstants::ASSETS_PATH;

        if (!$this->fileSystem->isDirectory($assetsPath)) {
            return;
        }

        $files = $this->fileSystem->glob($assetsPath . '/**/*');

        foreach ($files as $file) {
            if (is_file($file)) {
                $content = $this->fileSystem->get($file);
                $encrypted = $this->encryptor->encryptBytes($content, $this->assetsKey);
                $this->fileSystem->put($file, $encrypted);
            }
        }
    }

    private function getUnsignedApkPath(): string
    {
        return $this->workDir . '/' . ApkBuilderConstants::APK_UNSIGNED;
    }

    private function buildApk(): void
    {
        $apktoolJar = $this->toolsDir . '/apktool.jar';
        $unsignedApk = $this->getUnsignedApkPath();

        $command = sprintf(
            'java -jar %s b %s -o %s',
            escapeshellarg($apktoolJar),
            escapeshellarg($this->buildDir),
            escapeshellarg($unsignedApk)
        );

        $timeout = config('apk-builder.timeout', 300);
        $result = $this->runCommandWithHeartbeat($command, $timeout);

        if ($result === null || !$result->successful() || !$this->fileSystem->exists($unsignedApk)) {
            $errorOutput = $result ? ($result->errorOutput() ?: $result->output()) : 'Command failed to execute';
            throw ApkBuildException::buildFailed('apktool', $errorOutput);
        }
    }

    private function protectApk(): void
    {
        $protector = ($this->apkProtectorFactory)();
        $protector->protect($this->getUnsignedApkPath());
        Log::channel('apk')->debug('APK protection applied');
    }

    private function modifyDex(): void
    {
        $protector = ($this->apkProtectorFactory)();
        $count = $protector->modifyDex($this->getUnsignedApkPath());
        Log::channel('apk')->debug('DEX files modified', ['count' => $count]);
    }

    private function signApk(): void
    {
        $unsignedApk = $this->getUnsignedApkPath();
        $alignedApk = $this->workDir . '/' . ApkBuilderConstants::APK_ALIGNED;
        $signedApk = $this->workDir . '/' . ApkBuilderConstants::APK_SIGNED;
        $androidSdkTools = ApkBuilderConstants::DEFAULT_ANDROID_SDK_TOOLS;
        $zipalignPath = $androidSdkTools . '/zipalign';
        $apksignerPath = $androidSdkTools . '/apksigner';

        $alignCommand = sprintf(
            '%s -f 4 %s %s',
            escapeshellarg($zipalignPath),
            escapeshellarg($unsignedApk),
            escapeshellarg($alignedApk)
        );
        $alignResult = $this->processRunner->run($alignCommand);
        $apkToSign = $alignResult->successful() && $this->fileSystem->exists($alignedApk) ? $alignedApk : $unsignedApk;

        $keystore = $this->toolsDir . '/debug.keystore';

        if (!$this->fileSystem->exists($keystore)) {
            $this->generateKeystore($keystore);
        }

        if ($this->fileSystem->exists($apksignerPath)) {
            $command = sprintf(
                '%s sign --ks %s --ks-pass pass:android --out %s %s',
                escapeshellarg($apksignerPath),
                escapeshellarg($keystore),
                escapeshellarg($signedApk),
                escapeshellarg($apkToSign)
            );
            $result = $this->processRunner->run($command);
            Log::channel('apk')->debug('apksigner result', [
                'success' => $result->successful(),
                'output' => $result->output(),
                'error' => $result->errorOutput(),
            ]);
        }

        if (!$this->fileSystem->exists($signedApk)) {
            $command = sprintf(
                'jarsigner -keystore %s -storepass android -signedjar %s %s androiddebugkey',
                escapeshellarg($keystore),
                escapeshellarg($signedApk),
                escapeshellarg($apkToSign)
            );
            $result = $this->processRunner->run($command);
            Log::channel('apk')->debug('jarsigner result', [
                'success' => $result->successful(),
                'output' => $result->output(),
                'error' => $result->errorOutput(),
            ]);
        }

        if (!$this->fileSystem->exists($signedApk)) {
            throw ApkBuildException::signingFailed('All signing methods failed');
        }
    }

    private function generateKeystore(string $path): void
    {
        $command = sprintf(
            'keytool -genkey -v -keystore %s -storepass android -alias androiddebugkey -keypass android -keyalg RSA -keysize 2048 -validity 10000 -dname "CN=Debug,O=Android,C=US"',
            escapeshellarg($path)
        );
        $this->processRunner->run($command);
    }

    private function moveToOutput(ApkBuildConfig $config): string
    {
        $signedApk = $this->workDir . '/' . ApkBuilderConstants::APK_SIGNED;
        $outputDir = $this->outputDir . '/' . $config->userId . '/' . $config->appId;

        $this->fileSystem->ensureDirectoryExists($outputDir);

        $outputPath = $outputDir . '/' . $config->appId . '.apk';

        if (!$this->fileSystem->copy($signedApk, $outputPath)) {
            throw ApkBuildException::outputFailed('Failed to copy APK file');
        }

        return '/storage/apk/' . $config->userId . '/' . $config->appId . '/' . $config->appId . '.apk';
    }

    private function cleanup(): void
    {
        if (empty($this->workDir) || !$this->fileSystem->isDirectory($this->workDir)) {
            return;
        }

        $configKey = $this->buildSucceeded ? 'cleanup_on_success' : 'cleanup_on_failure';
        $shouldCleanup = config("apk-builder.{$configKey}", true);

        if ($shouldCleanup) {
            $this->fileSystem->deleteDirectory($this->workDir);
        }
    }

    private function copyDirectory(string $src, string $dst): void
    {
        $timeout = config('apk-builder.timeout', 300);

        $command = sprintf('rsync -a %s/ %s/', escapeshellarg($src), escapeshellarg($dst));
        $result = $this->runCommandWithHeartbeat($command, $timeout);

        if ($result !== null && $result->successful()) {
            return;
        }

        $command = sprintf(
            'tar -C %s -cf - . | tar -C %s -xf -',
            escapeshellarg($src),
            escapeshellarg($dst)
        );
        $this->fileSystem->ensureDirectoryExists($dst);
        $result = $this->runCommandWithHeartbeat($command, $timeout);

        if ($result !== null && $result->successful()) {
            return;
        }

        $this->copyDirectoryWithHeartbeat($src, $dst);
    }

    private function runCommandWithHeartbeat(string $command, int $timeout): ?\Illuminate\Contracts\Process\ProcessResult
    {
        $process = $this->processRunner->start($command, $timeout);
        $heartbeatInterval = ApkBuilderConstants::HEARTBEAT_INTERVAL_SEC;
        $lastHeartbeat = time();

        while ($process->running()) {
            if (time() - $lastHeartbeat >= $heartbeatInterval) {
                $this->emitHeartbeat();
                $lastHeartbeat = time();
            }
            usleep(ApkBuilderConstants::PROCESS_POLL_INTERVAL_US);
        }

        return $process->wait();
    }

    private function copyDirectoryWithHeartbeat(string $src, string $dst): void
    {
        $dirIterator = new RecursiveDirectoryIterator($src, RecursiveDirectoryIterator::SKIP_DOTS);
        $iterator = new RecursiveIteratorIterator($dirIterator, RecursiveIteratorIterator::SELF_FIRST);

        $this->fileSystem->ensureDirectoryExists($dst);
        $count = 0;
        $heartbeatInterval = ApkBuilderConstants::FILE_COPY_HEARTBEAT_INTERVAL;
        $srcLen = strlen(rtrim($src, DIRECTORY_SEPARATOR)) + 1;

        foreach ($iterator as $item) {
            /** @var \SplFileInfo $item */
            $relativePath = substr($item->getPathname(), $srcLen);
            $target = $dst . DIRECTORY_SEPARATOR . $relativePath;

            if ($item->isDir()) {
                $this->fileSystem->ensureDirectoryExists($target);
            } else {
                $this->fileSystem->copy($item->getPathname(), $target);
            }

            $count++;
            if ($count % $heartbeatInterval === 0) {
                $this->emitHeartbeat();
            }
        }
    }
}
