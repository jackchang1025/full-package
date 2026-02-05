<?php

declare(strict_types=1);

namespace App\Services\ApkBuilder;

use Illuminate\Support\Facades\File;
use RecursiveDirectoryIterator;
use RecursiveIteratorIterator;

final class Obfuscator
{
    private const OBFUSCATION_CHARS = ['I', 'l', 'O', '0', '1'];

    private string $buildDir;

    public function __construct(string $buildDir)
    {
        $this->buildDir = $buildDir;
    }

    public function generateJunkClasses(int $classCount, int $methodCount): int
    {
        $smaliDir = $this->buildDir . '/smali';
        $junkPackage = $this->generateName(8);
        $junkPath = $smaliDir . '/' . $junkPackage;

        File::ensureDirectoryExists($junkPath);

        for ($i = 0; $i < $classCount; $i++) {
            $className = $this->generateName(6);
            $content = $this->createJunkClass($junkPackage, $className, $methodCount);
            File::put($junkPath . '/' . $className . '.smali', $content);
        }

        return $classCount;
    }

    public function shuffleClassNames(): int
    {
        $classMap = [];
        $smaliDirs = array_slice(ApkBuilderConstants::SMALI_DIRS, 0, 4);

        foreach ($smaliDirs as $smaliDir) {
            $basePath = $this->buildDir . '/' . $smaliDir;

            if (!File::isDirectory($basePath)) {
                continue;
            }

            $iterator = new RecursiveIteratorIterator(
                new RecursiveDirectoryIterator($basePath, RecursiveDirectoryIterator::SKIP_DOTS)
            );

            foreach ($iterator as $file) {
                if (!$file->isFile() || $file->getExtension() !== 'smali') {
                    continue;
                }

                $relativePath = substr($file->getPathname(), strlen($basePath) + 1);
                $className = str_replace(['/', '.smali'], ['/', ''], $relativePath);

                if ($this->canObfuscate($className)) {
                    $newName = $this->obfuscateClassName($className);
                    if ($newName !== $className) {
                        $classMap[$className] = $newName;
                    }
                }
            }
        }

        if (!empty($classMap)) {
            $this->applyMapping($classMap, $smaliDirs);
        }

        return count($classMap);
    }

    private function generateName(int $length): string
    {
        $name = self::OBFUSCATION_CHARS[random_int(0, 2)];

        for ($i = 1; $i < $length; $i++) {
            $name .= self::OBFUSCATION_CHARS[array_rand(self::OBFUSCATION_CHARS)];
        }

        return $name;
    }

    private function createJunkClass(string $package, string $className, int $methodCount): string
    {
        $fullClass = "L{$package}/{$className};";
        $smali = ".class public {$fullClass}\n.super Ljava/lang/Object;\n\n";

        $fieldTypes = ['I', 'Z', 'J', 'Ljava/lang/String;'];
        $fieldCount = random_int(3, 8);

        for ($i = 0; $i < $fieldCount; $i++) {
            $type = $fieldTypes[array_rand($fieldTypes)];
            $smali .= ".field private {$this->generateName(4)}:{$type}\n";
        }

        $smali .= "\n.method public constructor <init>()V\n";
        $smali .= "    .locals 1\n    invoke-direct {p0}, Ljava/lang/Object;-><init>()V\n";
        $smali .= "    return-void\n.end method\n\n";

        for ($i = 0; $i < $methodCount; $i++) {
            $smali .= $this->createJunkMethod($this->generateName(5));
        }

        return $smali;
    }

    private function createJunkMethod(string $name): string
    {
        $method = ".method public {$name}()V\n    .locals 3\n";
        $method .= "    const/4 v0, 0x0\n    const/4 v1, 0x1\n";

        $ops = ['add-int', 'sub-int', 'mul-int', 'xor-int'];
        $opCount = random_int(2, 4);

        for ($i = 0; $i < $opCount; $i++) {
            $op = $ops[array_rand($ops)];
            $method .= "    {$op} v0, v0, v1\n";
        }

        return $method . "    return-void\n.end method\n\n";
    }

    private function canObfuscate(string $className): bool
    {
        $skipPatterns = [
            '/R$/',
            '/BuildConfig$/',
            '/MainActivity/',
            '/Application/',
            '/^android\//',
            '/^androidx\//',
            '/^kotlin\//',
            '/My_Configs/',
        ];

        foreach ($skipPatterns as $pattern) {
            if (preg_match($pattern, $className)) {
                return false;
            }
        }

        return true;
    }

    private function obfuscateClassName(string $name): string
    {
        $parts = explode('/', $name);
        $originalName = end($parts);
        $parts[count($parts) - 1] = $this->generateName(min(8, strlen($originalName)));

        return implode('/', $parts);
    }

    private function applyMapping(array $map, array $smaliDirs): void
    {
        foreach ($smaliDirs as $smaliDir) {
            $basePath = $this->buildDir . '/' . $smaliDir;

            if (!File::isDirectory($basePath)) {
                continue;
            }

            $iterator = new RecursiveIteratorIterator(
                new RecursiveDirectoryIterator($basePath, RecursiveDirectoryIterator::SKIP_DOTS)
            );

            foreach ($iterator as $file) {
                if (!$file->isFile() || $file->getExtension() !== 'smali') {
                    continue;
                }

                $content = File::get($file->getPathname());

                foreach ($map as $old => $new) {
                    $content = str_replace("L{$old};", "L{$new};", $content);
                }

                File::put($file->getPathname(), $content);
            }

            foreach ($map as $old => $new) {
                $oldFile = $basePath . '/' . $old . '.smali';
                $newFile = $basePath . '/' . $new . '.smali';

                if (File::exists($oldFile)) {
                    File::ensureDirectoryExists(dirname($newFile));
                    File::move($oldFile, $newFile);
                }
            }
        }
    }
}
