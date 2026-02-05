<?php

declare(strict_types=1);

namespace App\Services\ApkBuilder;

use App\Exceptions\ApkBuilder\ApkBuildException;
use App\Services\ApkBuilder\Contracts\FileSystemInterface;
use App\Services\ApkBuilder\Contracts\ZipExtractorInterface;
use ZipArchive;

final class ZipExtractor implements ZipExtractorInterface
{
    public function __construct(
        private readonly FileSystemInterface $fileSystem,
    ) {}

    public function extract(string $zipPath, string $targetDir): bool
    {
        $zip = new ZipArchive();
        $result = $zip->open($zipPath);

        if ($result !== true) {
            throw new ApkBuildException(
                "无法打开模板 ZIP 文件: " . self::getErrorMessage($result),
                ['zip' => $zipPath, 'error_code' => $result]
            );
        }

        $parentDir = dirname($targetDir);
        $this->fileSystem->ensureDirectoryExists($parentDir);

        if (!is_writable($parentDir)) {
            $zip->close();
            throw new ApkBuildException("目标目录不可写", [
                'parent_dir' => $parentDir,
                'permissions' => substr(sprintf('%o', fileperms($parentDir)), -4),
            ]);
        }

        if ($this->fileSystem->isDirectory($targetDir)) {
            $this->fileSystem->deleteDirectory($targetDir);
        }

        if (!$zip->extractTo($targetDir)) {
            $statusMessage = $zip->getStatusString();
            $zip->close();
            throw new ApkBuildException("解压模板 ZIP 文件失败: {$statusMessage}", [
                'zip' => $zipPath,
                'target' => $targetDir,
                'zip_status' => $statusMessage,
                'disk_free_space' => disk_free_space($parentDir),
            ]);
        }

        $zip->close();

        return true;
    }

    public static function getErrorMessage(int $code): string
    {
        $messages = [
            ZipArchive::ER_EXISTS => '文件已存在',
            ZipArchive::ER_INCONS => '压缩包不一致',
            ZipArchive::ER_INVAL => '无效参数',
            ZipArchive::ER_MEMORY => '内存分配失败',
            ZipArchive::ER_NOENT => '文件不存在',
            ZipArchive::ER_NOZIP => '不是有效的 ZIP 文件',
            ZipArchive::ER_OPEN => '无法打开文件',
            ZipArchive::ER_READ => '读取错误',
            ZipArchive::ER_SEEK => '定位错误',
        ];

        return $messages[$code] ?? "未知错误 ({$code})";
    }
}
