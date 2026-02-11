<?php

declare(strict_types=1);

namespace Tests\Support;

use App\Services\ApkBuilder\ApkBuilderConstants;
use Illuminate\Support\Facades\File;

final class ApkBuilderTestFixtures
{
    /**
     * 创建包含 My_Configs.smali 的 Mock 构建目录
     * 返回临时目录路径，测试后需自行清理
     */
    public static function createMockBuildDir(?string $smaliContent = null): string
    {
        $baseDir = sys_get_temp_dir().'/apk_builder_test_'.uniqid();
        $smaliPath = $baseDir.'/'.ApkBuilderConstants::CONFIGS_SMALI_RELATIVE;

        File::ensureDirectoryExists(dirname($smaliPath));

        $content = $smaliContent ?? self::getDefaultMyConfigsContent();
        File::put($smaliPath, $content);

        return $baseDir;
    }

    /**
     * 包含占位符的默认 My_Configs.smali 模板内容
     */
    public static function getDefaultMyConfigsContent(): string
    {
        return <<<'SMALI'
.class public Lcom/icontrol/protector/My_Configs;
.super Ljava/lang/Object;

.field public static clientName:Ljava/lang/String; = "[Client_N]"
.field public static userDom:Ljava/lang/String; = "[USER_DOM]"
.field public static assetsKey:Ljava/lang/String; = "[AST-PAS]"
.field public static loginTitle:Ljava/lang/String; = "[log-title]"
.field public static loginDis:Ljava/lang/String; = "[log-dis]"
.field public static loginBtn:Ljava/lang/String; = "[log-btn]"
.field public static obfs:Ljava/lang/String; = "[OBFS]"
.field public static trackingData:Ljava/lang/String; = "[NAME>LNK>ID!]"

.method static constructor <clinit>()V
    const-string v1, "wss://"
    return-void
.end method
.end class
SMALI;
    }

    /**
     * 创建包含 smali 子目录结构的 Mock 目录（用于 renamePackage 测试）
     */
    public static function createMockBuildDirWithPackage(string $packageName): string
    {
        $baseDir = self::createMockBuildDir();
        $packagePath = str_replace('.', '/', $packageName);
        $smaliDir = $baseDir.'/smali/'.$packagePath;

        File::ensureDirectoryExists($smaliDir);
        $smaliFile = $smaliDir.'/SomeClass.smali';
        File::put($smaliFile, ".class public L{$packagePath}/SomeClass;\n.super Ljava/lang/Object;\n");

        return $baseDir;
    }

    /**
     * 删除临时目录
     */
    public static function cleanupMockBuildDir(string $path): void
    {
        if (File::isDirectory($path) && str_contains($path, 'apk_builder_test_')) {
            File::deleteDirectory($path);
        }
    }
}
