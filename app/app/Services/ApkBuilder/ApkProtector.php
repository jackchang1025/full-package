<?php

declare(strict_types=1);

namespace App\Services\ApkBuilder;

use Illuminate\Support\Facades\File;
use ZipArchive;

final class ApkProtector
{
    public function protect(string $apkPath): void
    {
        if (!File::exists($apkPath)) {
            return;
        }

        $data = File::get($apkPath);
        $data = $this->addZipComment($data);
        File::put($apkPath, $data);
    }

    public function modifyDex(string $apkPath): int
    {
        if (!File::exists($apkPath)) {
            return 0;
        }

        $zip = new ZipArchive();

        if ($zip->open($apkPath) !== true) {
            return 0;
        }

        $count = 0;

        for ($i = 0; $i < $zip->numFiles; $i++) {
            $name = $zip->getNameIndex($i);

            if (!preg_match('/^classes\d*\.dex$/', $name)) {
                continue;
            }

            $dexData = $zip->getFromIndex($i);

            if ($dexData === false) {
                continue;
            }

            $modified = $this->modifyDexFile($dexData);

            if ($modified !== $dexData) {
                $zip->deleteName($name);
                $zip->addFromString($name, $modified);
                $count++;
            }
        }

        $zip->close();

        return $count;
    }

    private function addZipComment(string $data): string
    {
        $comment = random_bytes(random_int(100, 300));
        $eocdPos = strrpos($data, "\x50\x4b\x05\x06");

        if ($eocdPos !== false && $eocdPos + 22 <= strlen($data)) {
            return substr($data, 0, $eocdPos + 20) . pack('v', strlen($comment)) . $comment;
        }

        return $data;
    }

    private function modifyDexFile(string $data): string
    {
        if (strlen($data) < 112 || !preg_match('/^dex\n\d{3}\x00$/', substr($data, 0, 8))) {
            return $data;
        }

        $junk = random_bytes(random_int(1024, 2048));
        $newSize = strlen($data) + strlen($junk);
        $data = substr($data, 0, 32) . pack('V', $newSize) . substr($data, 36) . $junk;

        return $this->recalculateChecksum($data);
    }

    private function recalculateChecksum(string $data): string
    {
        $signature = sha1(substr($data, 32), true);
        $data = substr($data, 0, 12) . $signature . substr($data, 32);

        $checksum = $this->adler32(substr($data, 12));

        return substr($data, 0, 8) . pack('V', $checksum) . substr($data, 12);
    }

    private function adler32(string $data): int
    {
        $a = 1;
        $b = 0;
        $len = strlen($data);

        for ($i = 0; $i < $len; $i++) {
            $a = ($a + ord($data[$i])) % 65521;
            $b = ($b + $a) % 65521;
        }

        return ($b << 16) | $a;
    }
}
