<?php

use App\Services\ApkBuilder\Obfuscator;
use App\Services\ApkBuilder\ApkBuilderConstants;
use Illuminate\Support\Facades\File;
use Tests\Support\ApkBuilderTestFixtures;

describe('Obfuscator generateJunkClasses', function () {
    it('generateJunkClasses creates expected number of files', function () {
        $buildDir = ApkBuilderTestFixtures::createMockBuildDir();
        $smaliDir = $buildDir.'/smali';
        File::ensureDirectoryExists($smaliDir);

        try {
            $obfuscator = new Obfuscator($buildDir);
            $count = $obfuscator->generateJunkClasses(5, 3);

            expect($count)->toBe(5);

            $junkDirs = glob($smaliDir.'/*', GLOB_ONLYDIR);
            expect($junkDirs)->not->toBeEmpty();

            $totalFiles = 0;
            foreach ($junkDirs as $dir) {
                $files = glob($dir.'/*.smali');
                $totalFiles += count($files);
            }
            expect($totalFiles)->toBe(5);
        } finally {
            ApkBuilderTestFixtures::cleanupMockBuildDir($buildDir);
        }
    });
});

describe('Obfuscator shuffleClassNames', function () {
    it('renames hardcoded target classes in com/icontrol/protector', function () {
        $buildDir = ApkBuilderTestFixtures::createMockBuildDir();
        $smaliDir = $buildDir.'/smali';
        $pkg = 'com/icontrol/protector';
        // createMockBuildDir 已创建 My_Configs.smali（也在 CLASS_RENAME_TARGETS 中）
        // 额外创建几个目标类
        $extraTargets = ['AccessServices', 'WorkServices', 'HiddenActivity', 'ScreenReceiver'];
        foreach ($extraTargets as $cls) {
            File::put($smaliDir.'/'.$pkg."/{$cls}.smali", ".class public L{$pkg}/{$cls};\n.super Ljava/lang/Object;");
        }

        // Manifest 包含这些类的引用
        $manifest = <<<'XML'
<?xml version="1.0" encoding="utf-8"?>
<manifest package="com.icontrol.protector">
    <application>
        <service android:name="com.icontrol.protector.AccessServices" />
        <service android:name="com.icontrol.protector.WorkServices" />
        <activity android:name="com.icontrol.protector.HiddenActivity" />
        <receiver android:name="com.icontrol.protector.ScreenReceiver" />
    </application>
</manifest>
XML;
        File::put($buildDir.'/AndroidManifest.xml', $manifest);

        // 记录重命名前的文件数
        $filesBefore = File::files($smaliDir.'/'.$pkg);
        $countBefore = count($filesBefore);

        try {
            $obfuscator = new Obfuscator($buildDir);
            $count = $obfuscator->shuffleClassNames();

            // 返回值 = CLASS_RENAME_TARGETS 总数（~60+）
            expect($count)->toBeGreaterThan(50);

            // 原始文件应被重命名（不再存在）
            foreach ($extraTargets as $cls) {
                expect(File::exists($smaliDir.'/'.$pkg."/{$cls}.smali"))->toBeFalse(
                    "Expected {$cls}.smali to be renamed"
                );
            }
            // My_Configs 也在目标列表中，应被重命名
            expect(File::exists($smaliDir.'/'.$pkg.'/My_Configs.smali'))->toBeFalse();

            // 目录下文件数量不变（重命名不增减文件）
            $filesAfter = File::files($smaliDir.'/'.$pkg);
            expect(count($filesAfter))->toBe($countBefore);
        } finally {
            ApkBuilderTestFixtures::cleanupMockBuildDir($buildDir);
        }
    });

    it('replaces class name references in smali file contents', function () {
        $buildDir = ApkBuilderTestFixtures::createMockBuildDir();
        $smaliDir = $buildDir.'/smali';
        $pkg = 'com/icontrol/protector';
        File::ensureDirectoryExists($smaliDir.'/'.$pkg);

        // 一个文件引用了另一个目标类
        File::put($smaliDir.'/'.$pkg.'/Consts.smali',
            ".class public L{$pkg}/Consts;\n.field ref:L{$pkg}/AccessServices;");
        File::put($smaliDir.'/'.$pkg.'/AccessServices.smali',
            ".class public L{$pkg}/AccessServices;");

        File::put($buildDir.'/AndroidManifest.xml', '<manifest><application></application></manifest>');

        try {
            $obfuscator = new Obfuscator($buildDir);
            $obfuscator->shuffleClassNames();

            // Consts 被重命名了，找到新文件
            $files = File::files($smaliDir.'/'.$pkg);
            foreach ($files as $file) {
                $content = File::get($file->getPathname());
                // 原始类名不应出现在任何文件内容中
                expect(str_contains($content, 'AccessServices'))->toBeFalse();
                expect(str_contains($content, 'Consts'))->toBeFalse();
            }
        } finally {
            ApkBuilderTestFixtures::cleanupMockBuildDir($buildDir);
        }
    });

    it('updates AndroidManifest.xml class references', function () {
        $buildDir = ApkBuilderTestFixtures::createMockBuildDir();
        $smaliDir = $buildDir.'/smali';
        $pkg = 'com/icontrol/protector';
        File::ensureDirectoryExists($smaliDir.'/'.$pkg);

        File::put($smaliDir.'/'.$pkg.'/WorkServices.smali', ".class public L{$pkg}/WorkServices;");

        $manifest = <<<'XML'
<?xml version="1.0" encoding="utf-8"?>
<manifest package="com.icontrol.protector">
    <application>
        <service android:name="com.icontrol.protector.WorkServices" />
    </application>
</manifest>
XML;
        File::put($buildDir.'/AndroidManifest.xml', $manifest);

        try {
            $obfuscator = new Obfuscator($buildDir);
            $obfuscator->shuffleClassNames();

            $manifestContent = File::get($buildDir.'/AndroidManifest.xml');
            expect(str_contains($manifestContent, 'WorkServices'))->toBeFalse();
        } finally {
            ApkBuilderTestFixtures::cleanupMockBuildDir($buildDir);
        }
    });

    it('skips android/ and androidx/ directories during content replacement', function () {
        $buildDir = ApkBuilderTestFixtures::createMockBuildDir();
        $smaliDir = $buildDir.'/smali';

        // android/ 目录下的文件不应被修改
        File::ensureDirectoryExists($smaliDir.'/android/app');
        File::put($smaliDir.'/android/app/Activity.smali',
            ".class public Landroid/app/Activity;\n# ref AccessServices");

        // androidx/ 目录下的文件不应被修改
        File::ensureDirectoryExists($smaliDir.'/androidx/core');
        File::put($smaliDir.'/androidx/core/Provider.smali',
            ".class public Landroidx/core/Provider;\n# ref WorkServices");

        File::put($buildDir.'/AndroidManifest.xml', '<manifest><application></application></manifest>');

        try {
            $obfuscator = new Obfuscator($buildDir);
            $obfuscator->shuffleClassNames();

            // android/ 和 androidx/ 下的文件内容不应被修改
            $androidContent = File::get($smaliDir.'/android/app/Activity.smali');
            expect(str_contains($androidContent, 'AccessServices'))->toBeTrue();

            $androidxContent = File::get($smaliDir.'/androidx/core/Provider.smali');
            expect(str_contains($androidxContent, 'WorkServices'))->toBeTrue();
        } finally {
            ApkBuilderTestFixtures::cleanupMockBuildDir($buildDir);
        }
    });

    it('does not rename files outside com/icontrol/protector', function () {
        $buildDir = ApkBuilderTestFixtures::createMockBuildDir();
        $smaliDir = $buildDir.'/smali';

        // 在其他包下创建同名类 — 内容会被替换但文件不会被重命名
        $otherPkg = 'com/other/pkg';
        File::ensureDirectoryExists($smaliDir.'/'.$otherPkg);
        File::put($smaliDir.'/'.$otherPkg.'/AccessServices.smali',
            ".class public L{$otherPkg}/AccessServices;");

        File::put($buildDir.'/AndroidManifest.xml', '<manifest><application></application></manifest>');

        try {
            $obfuscator = new Obfuscator($buildDir);
            $obfuscator->shuffleClassNames();

            // 文件名不变（只有 com/icontrol/protector 下的文件会被重命名）
            $files = File::files($smaliDir.'/'.$otherPkg);
            $filenames = array_map(fn ($f) => $f->getFilename(), $files);
            expect($filenames)->toContain('AccessServices.smali');

            // 但内容中的类名引用被替换了
            $content = File::get($files[0]->getPathname());
            expect(str_contains($content, 'AccessServices'))->toBeFalse();
        } finally {
            ApkBuilderTestFixtures::cleanupMockBuildDir($buildDir);
        }
    });

    it('only renames CLASS_RENAME_TARGETS, ignores other classes', function () {
        $buildDir = ApkBuilderTestFixtures::createMockBuildDir();
        $smaliDir = $buildDir.'/smali';
        $pkg = 'com/icontrol/protector';
        File::ensureDirectoryExists($smaliDir.'/'.$pkg);

        // 不在 CLASS_RENAME_TARGETS 中的类不应被重命名
        File::put($smaliDir.'/'.$pkg.'/SomeRandomClass.smali',
            ".class public L{$pkg}/SomeRandomClass;");
        // 在 CLASS_RENAME_TARGETS 中的类应被重命名
        File::put($smaliDir.'/'.$pkg.'/ActivMain.smali',
            ".class public L{$pkg}/ActivMain;");

        File::put($buildDir.'/AndroidManifest.xml', '<manifest><application></application></manifest>');

        try {
            $obfuscator = new Obfuscator($buildDir);
            $obfuscator->shuffleClassNames();

            // SomeRandomClass 不在目标列表中，文件名不变
            expect(File::exists($smaliDir.'/'.$pkg.'/SomeRandomClass.smali'))->toBeTrue();
            // ActivMain 在目标列表中，文件被重命名
            expect(File::exists($smaliDir.'/'.$pkg.'/ActivMain.smali'))->toBeFalse();
        } finally {
            ApkBuilderTestFixtures::cleanupMockBuildDir($buildDir);
        }
    });
});

describe('Obfuscator obfuscateStrings', function () {
    it('replaces known string variable names in smali files', function () {
        $buildDir = ApkBuilderTestFixtures::createMockBuildDir();
        $smaliDir = $buildDir.'/smali';
        $pkg = 'com/icontrol/protector';
        File::ensureDirectoryExists($smaliDir.'/'.$pkg);

        $smaliContent = <<<'SMALI'
.class public Lcom/icontrol/protector/Consts;
.super Ljava/lang/Object;

.field public static URL_PING:Ljava/lang/String;
.field public static USR_MAIL:Ljava/lang/String;
.field public static DEVICE_ID:Ljava/lang/String;
.field public static Anti_Kill:Ljava/lang/String;
.field public static AsstsKey:Ljava/lang/String;
SMALI;
        File::put($smaliDir.'/'.$pkg.'/Consts.smali', $smaliContent);

        try {
            $obfuscator = new Obfuscator($buildDir);
            $count = $obfuscator->obfuscateStrings();

            expect($count)->toBeGreaterThan(0);

            $content = File::get($smaliDir.'/'.$pkg.'/Consts.smali');
            expect(str_contains($content, 'URL_PING'))->toBeFalse();
            expect(str_contains($content, 'USR_MAIL'))->toBeFalse();
            expect(str_contains($content, 'DEVICE_ID'))->toBeFalse();
            expect(str_contains($content, 'Anti_Kill'))->toBeFalse();
            expect(str_contains($content, 'AsstsKey'))->toBeFalse();
        } finally {
            ApkBuilderTestFixtures::cleanupMockBuildDir($buildDir);
        }
    });

    it('does not modify files without target strings', function () {
        $buildDir = ApkBuilderTestFixtures::createMockBuildDir();
        $smaliDir = $buildDir.'/smali';
        $pkg = 'com/test';
        File::ensureDirectoryExists($smaliDir.'/'.$pkg);

        $original = ".class public Lcom/test/Clean;\n.field public x:I\n";
        File::put($smaliDir.'/'.$pkg.'/Clean.smali', $original);

        try {
            $obfuscator = new Obfuscator($buildDir);
            $obfuscator->obfuscateStrings();

            expect(File::get($smaliDir.'/'.$pkg.'/Clean.smali'))->toBe($original);
        } finally {
            ApkBuilderTestFixtures::cleanupMockBuildDir($buildDir);
        }
    });

    it('returns zero when no smali directories exist', function () {
        $buildDir = ApkBuilderTestFixtures::createMockBuildDir();

        try {
            $obfuscator = new Obfuscator($buildDir);
            $count = $obfuscator->obfuscateStrings();

            expect($count)->toBe(0);
        } finally {
            ApkBuilderTestFixtures::cleanupMockBuildDir($buildDir);
        }
    });

    it('replaces strings consistently across multiple files', function () {
        $buildDir = ApkBuilderTestFixtures::createMockBuildDir();
        $smaliDir = $buildDir.'/smali';
        $pkg = 'com/test';
        File::ensureDirectoryExists($smaliDir.'/'.$pkg);

        File::put($smaliDir.'/'.$pkg.'/A.smali', '.field public static URL_PING:Ljava/lang/String;');
        File::put($smaliDir.'/'.$pkg.'/B.smali', '.field public static URL_PING:Ljava/lang/String;');

        try {
            $obfuscator = new Obfuscator($buildDir);
            $obfuscator->obfuscateStrings();

            $contentA = File::get($smaliDir.'/'.$pkg.'/A.smali');
            $contentB = File::get($smaliDir.'/'.$pkg.'/B.smali');

            // 两个文件中 URL_PING 应被替换为相同的随机字符串
            expect(str_contains($contentA, 'URL_PING'))->toBeFalse();
            expect(str_contains($contentB, 'URL_PING'))->toBeFalse();
            expect($contentA)->toBe($contentB);
        } finally {
            ApkBuilderTestFixtures::cleanupMockBuildDir($buildDir);
        }
    });
});
