<?php

namespace ApkBuilder;

/**
 * 代码混淆处理器
 */
class Obfuscator
{
    private string $buildDir;
    private array $chars = ['I', 'l', 'O', '0', '1'];

    public function __construct(string $buildDir)
    {
        $this->buildDir = $buildDir;
    }

    /**
     * 生成垃圾类
     * 
     * @param int $classCount 类数量
     * @param int $methodCount 每个类的方法数量
     * @return int 生成的类数量
     */
    public function generateJunkClasses(int $classCount, int $methodCount): int
    {
        $smaliDir = $this->buildDir . '/smali';
        $junkPackage = $this->generateName(8);
        $junkPath = $smaliDir . '/' . $junkPackage;
        @mkdir($junkPath, 0755, true);

        for ($i = 0; $i < $classCount; $i++) {
            $className = $this->generateName(6);
            $content = $this->createJunkClass($junkPackage, $className, $methodCount);
            file_put_contents($junkPath . '/' . $className . '.smali', $content);
        }

        return $classCount;
    }

    /**
     * 混淆类名
     * 
     * @return int 混淆的类数量
     */
    public function shuffleClassNames(): int
    {
        $classMap = [];
        $smaliDirs = ['smali', 'smali_classes2', 'smali_classes3', 'smali_classes4'];

        foreach ($smaliDirs as $smaliDir) {
            $basePath = $this->buildDir . '/' . $smaliDir;
            if (!is_dir($basePath)) continue;

            $iterator = new \RecursiveIteratorIterator(
                new \RecursiveDirectoryIterator($basePath, \RecursiveDirectoryIterator::SKIP_DOTS)
            );

            foreach ($iterator as $file) {
                if ($file->isFile() && $file->getExtension() === 'smali') {
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
        }

        if (!empty($classMap)) {
            $this->applyMapping($classMap, $smaliDirs);
        }

        return count($classMap);
    }

    /**
     * 生成混淆名称
     */
    private function generateName(int $length): string
    {
        $name = $this->chars[rand(0, 2)]; // I, l, O
        for ($i = 1; $i < $length; $i++) {
            $name .= $this->chars[array_rand($this->chars)];
        }
        return $name;
    }

    /**
     * 创建垃圾类内容
     */
    private function createJunkClass(string $package, string $className, int $methodCount): string
    {
        $fullClass = "L{$package}/{$className};";
        $smali = ".class public {$fullClass}\n.super Ljava/lang/Object;\n\n";

        // 添加随机字段
        $fieldTypes = ['I', 'Z', 'J', 'Ljava/lang/String;'];
        for ($i = 0; $i < rand(3, 8); $i++) {
            $type = $fieldTypes[rand(0, count($fieldTypes) - 1)];
            $smali .= ".field private {$this->generateName(4)}:{$type}\n";
        }

        // 构造函数
        $smali .= "\n.method public constructor <init>()V\n";
        $smali .= "    .locals 1\n    invoke-direct {p0}, Ljava/lang/Object;-><init>()V\n";
        $smali .= "    return-void\n.end method\n\n";

        // 垃圾方法
        for ($i = 0; $i < $methodCount; $i++) {
            $smali .= $this->createJunkMethod($this->generateName(5));
        }

        return $smali;
    }

    /**
     * 创建垃圾方法
     */
    private function createJunkMethod(string $name): string
    {
        $method = ".method public {$name}()V\n    .locals 3\n";
        $method .= "    const/4 v0, 0x0\n    const/4 v1, 0x1\n";
        
        $ops = ['add-int', 'sub-int', 'mul-int', 'xor-int'];
        for ($i = 0; $i < rand(2, 4); $i++) {
            $op = $ops[rand(0, count($ops) - 1)];
            $method .= "    {$op} v0, v0, v1\n";
        }
        
        return $method . "    return-void\n.end method\n\n";
    }

    /**
     * 判断类是否可以混淆
     */
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
            '/My_Configs/'
        ];

        foreach ($skipPatterns as $pattern) {
            if (preg_match($pattern, $className)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 混淆类名
     */
    private function obfuscateClassName(string $name): string
    {
        $parts = explode('/', $name);
        $originalName = end($parts);
        $parts[count($parts) - 1] = $this->generateName(min(8, strlen($originalName)));
        return implode('/', $parts);
    }

    /**
     * 应用类名映射
     */
    private function applyMapping(array $map, array $smaliDirs): void
    {
        // 更新所有文件中的引用
        foreach ($smaliDirs as $smaliDir) {
            $basePath = $this->buildDir . '/' . $smaliDir;
            if (!is_dir($basePath)) continue;

            $iterator = new \RecursiveIteratorIterator(
                new \RecursiveDirectoryIterator($basePath, \RecursiveDirectoryIterator::SKIP_DOTS)
            );

            foreach ($iterator as $file) {
                if ($file->isFile() && $file->getExtension() === 'smali') {
                    $content = file_get_contents($file->getPathname());
                    foreach ($map as $old => $new) {
                        $content = str_replace("L{$old};", "L{$new};", $content);
                    }
                    file_put_contents($file->getPathname(), $content);
                }
            }

            // 重命名文件
            foreach ($map as $old => $new) {
                $oldFile = $basePath . '/' . $old . '.smali';
                $newFile = $basePath . '/' . $new . '.smali';
                if (file_exists($oldFile)) {
                    @mkdir(dirname($newFile), 0755, true);
                    rename($oldFile, $newFile);
                }
            }
        }
    }
}
