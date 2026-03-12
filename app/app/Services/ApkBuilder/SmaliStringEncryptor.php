<?php

declare(strict_types=1);

namespace App\Services\ApkBuilder;

use Illuminate\Support\Facades\File;
use RecursiveDirectoryIterator;
use RecursiveIteratorIterator;

/**
 * 全量 smali 字符串 XOR 加密器。
 * 扫描 smali 文件中的 const-string 指令，替换为加密字节数组 + XOR 解密调用。
 */
final class SmaliStringEncryptor
{
    private const XOR_KEY_LENGTH = 8;

    private const MAX_LOCALS = 16;

    private const EXTRA_REGISTERS_NEEDED = 3;

    private const EXCLUDED_DIRS = [
        '/myobfuscated/',
        '/androidx/',
        '/kotlin/',
        '/handler/verifier/',
        '/android/',
    ];

    private const SKIP_PATTERNS = [
        '/^$/',
        '/^\d+$/',
        '/^UTF-8$/',
    ];

    private string $buildDir;

    private string $decryptorClass;

    private ?\Closure $heartbeatCallback = null;

    private float $lastHeartbeatTime = 0;

    private int $encryptedCount = 0;

    public function __construct(string $buildDir, string $decryptorClass = 'Lcom/icontrol/protector/XorDecryptor;')
    {
        $this->buildDir = $buildDir;
        $this->decryptorClass = $decryptorClass;
        $this->lastHeartbeatTime = microtime(true);
    }

    public function setHeartbeatCallback(?\Closure $callback): void
    {
        $this->heartbeatCallback = $callback;
    }

    public function encryptAllStrings(): int
    {
        $this->encryptedCount = 0;

        foreach (ApkBuilderConstants::SMALI_DIRS as $smaliDir) {
            $basePath = $this->buildDir . '/' . $smaliDir;

            if (! File::isDirectory($basePath)) {
                continue;
            }

            $iterator = new RecursiveIteratorIterator(
                new RecursiveDirectoryIterator($basePath, RecursiveDirectoryIterator::SKIP_DOTS)
            );

            foreach ($iterator as $file) {
                if (! $file->isFile() || $file->getExtension() !== 'smali') {
                    continue;
                }

                if (! $this->shouldEncrypt($file->getPathname())) {
                    continue;
                }

                $this->encryptedCount += $this->processSmaliFile($file->getPathname());
                $this->emitHeartbeatIfNeeded();
            }
        }

        return $this->encryptedCount;
    }

    private function shouldEncrypt(string $filePath): bool
    {
        foreach (self::EXCLUDED_DIRS as $excluded) {
            if (str_contains($filePath, $excluded)) {
                return false;
            }
        }

        return true;
    }

    private function processSmaliFile(string $filePath): int
    {
        $content = File::get($filePath);
        $lines = explode("\n", $content);
        $modified = false;
        $count = 0;

        $result = [];
        $inMethod = false;
        $methodLines = [];
        $methodLocals = 0;

        foreach ($lines as $line) {
            if (preg_match('/^\.method\s/', $line)) {
                $inMethod = true;
                $methodLines = [$line];
                $methodLocals = 0;

                continue;
            }

            if ($inMethod && preg_match('/^\s*\.locals\s+(\d+)/', $line, $m)) {
                $methodLocals = (int) $m[1];
                $methodLines[] = $line;

                continue;
            }

            if ($inMethod && preg_match('/^\.end method/', $line)) {
                $methodLines[] = $line;

                $methodResult = $this->processMethod($methodLines, $methodLocals);
                if ($methodResult['modified']) {
                    $modified = true;
                    $count += $methodResult['count'];
                }

                foreach ($methodResult['lines'] as $ml) {
                    $result[] = $ml;
                }

                $inMethod = false;
                $methodLines = [];

                continue;
            }

            if ($inMethod) {
                $methodLines[] = $line;
            } else {
                $result[] = $line;
            }
        }

        if ($modified) {
            File::put($filePath, implode("\n", $result));
        }

        return $count;
    }

    /**
     * 处理单个方法中的所有 const-string 指令。
     *
     * @return array{modified: bool, count: int, lines: string[]}
     */
    private function processMethod(array $lines, int $locals): array
    {
        if ($locals + self::EXTRA_REGISTERS_NEEDED > self::MAX_LOCALS) {
            return ['modified' => false, 'count' => 0, 'lines' => $lines];
        }

        $modified = false;
        $count = 0;
        $newLocals = $locals;
        $result = [];
        $arrayDataBlocks = [];
        $arrayIndex = 0;

        foreach ($lines as $line) {
            if (preg_match('/^\s+(const-string(?:\/jumbo)?)\s+(v\d+|p\d+),\s*"(.*)"$/', $line, $m)) {
                $register = $m[2];
                $value = $this->unescapeSmaliString($m[3]);

                if ($this->shouldSkipString($value)) {
                    $result[] = $line;

                    continue;
                }

                if (! $modified) {
                    $newLocals = $locals + self::EXTRA_REGISTERS_NEEDED;
                }

                $key = $this->generateXorKey();
                $encrypted = $this->xorEncrypt($value, $key);

                $encLabel = "array_enc_{$arrayIndex}";
                $keyLabel = "array_key_{$arrayIndex}";
                $arrayIndex++;

                $vEnc = 'v' . $locals;
                $vKey = 'v' . ($locals + 1);

                $encLen = count($encrypted);
                $keyLen = count($key);

                $result[] = "    const/16 {$vEnc}, " . sprintf('0x%x', $encLen);
                $result[] = "    new-array {$vEnc}, {$vEnc}, [B";
                $result[] = "    fill-array-data {$vEnc}, :{$encLabel}";
                $result[] = '';
                $result[] = "    const/16 {$vKey}, " . sprintf('0x%x', $keyLen);
                $result[] = "    new-array {$vKey}, {$vKey}, [B";
                $result[] = "    fill-array-data {$vKey}, :{$keyLabel}";
                $result[] = '';
                $result[] = "    invoke-static {{$vEnc}, {$vKey}}, {$this->decryptorClass}->d([B[B)Ljava/lang/String;";
                $result[] = "    move-result-object {$register}";

                $arrayDataBlocks[] = $this->generateFillArrayData($encrypted, $encLabel);
                $arrayDataBlocks[] = $this->generateFillArrayData($key, $keyLabel);

                $modified = true;
                $count++;
            } else {
                $result[] = $line;
            }
        }

        if ($modified) {
            foreach ($result as $i => $line) {
                if (preg_match('/^(\s*)\.locals\s+\d+/', $line, $m)) {
                    $result[$i] = $m[1] . '.locals ' . $newLocals;

                    break;
                }
            }

            $endIdx = count($result) - 1;
            while ($endIdx >= 0 && ! preg_match('/^\.end method/', $result[$endIdx])) {
                $endIdx--;
            }

            if ($endIdx >= 0) {
                $before = array_slice($result, 0, $endIdx);
                $after = array_slice($result, $endIdx);

                $result = array_merge($before, [''], $arrayDataBlocks, $after);
            }
        }

        return ['modified' => $modified, 'count' => $count, 'lines' => $result];
    }

    private function shouldSkipString(string $value): bool
    {
        foreach (self::SKIP_PATTERNS as $pattern) {
            if (preg_match($pattern, $value)) {
                return true;
            }
        }

        if (mb_strlen($value) <= 1) {
            return true;
        }

        return false;
    }

    /** @return int[] */
    private function generateXorKey(): array
    {
        $key = [];
        for ($i = 0; $i < self::XOR_KEY_LENGTH; $i++) {
            $key[] = random_int(1, 255);
        }

        return $key;
    }

    /** @param int[] $key  @return int[] */
    private function xorEncrypt(string $plaintext, array $key): array
    {
        $bytes = array_values(unpack('C*', $plaintext));
        $keyLen = count($key);
        $result = [];

        foreach ($bytes as $i => $byte) {
            $result[] = $byte ^ $key[$i % $keyLen];
        }

        return $result;
    }

    /** @param int[] $bytes */
    private function generateFillArrayData(array $bytes, string $label): string
    {
        $lines = [];
        $lines[] = "    :{$label}";
        $lines[] = '    .array-data 1';

        foreach ($bytes as $byte) {
                if ($byte > 127) {
                $byte -= 256;
            }
            $lines[] = sprintf('        %st', $this->formatSmaliHexByte($byte));
        }

        $lines[] = '    .end array-data';
        $lines[] = '';

        return implode("\n", $lines);
    }

    private function formatSmaliHexByte(int $byte): string
    {
        if ($byte < 0) {
            return sprintf('-0x%x', abs($byte));
        }

        return sprintf('0x%x', $byte);
    }

    private function unescapeSmaliString(string $str): string
    {
        $str = str_replace('\\n', "\n", $str);
        $str = str_replace('\\t', "\t", $str);
        $str = str_replace('\\r', "\r", $str);
        $str = str_replace('\\"', '"', $str);
        $str = str_replace('\\\\', '\\', $str);

        return $str;
    }

    private function emitHeartbeatIfNeeded(): void
    {
        if ($this->heartbeatCallback === null) {
            return;
        }

        $now = microtime(true);
        if (($now - $this->lastHeartbeatTime) >= 10) {
            ($this->heartbeatCallback)();
            $this->lastHeartbeatTime = $now;
        }
    }
}
