<?php

use App\Services\ApkBuilder\ApkProtector;
use Illuminate\Support\Facades\File;

function createTestApk(string $dir): string
{
    File::ensureDirectoryExists($dir);
    $apkPath = $dir.'/test.apk';

    $zip = new ZipArchive;
    $zip->open($apkPath, ZipArchive::CREATE | ZipArchive::OVERWRITE);
    $zip->addFromString('AndroidManifest.xml', '<manifest/>');
    $zip->addFromString('resources.arsc', str_repeat("\x00", 100));
    $zip->addFromString('classes.dex', "dex\n035\x00".str_repeat("\x00", 104));
    $zip->addFromString('other.txt', 'hello');
    $zip->close();

    return $apkPath;
}

function createTestApkDir(): string
{
    return sys_get_temp_dir().'/apk_protector_test_'.uniqid();
}

afterEach(function () {
    $pattern = sys_get_temp_dir().'/apk_protector_test_*';
    foreach (glob($pattern, GLOB_ONLYDIR) as $dir) {
        File::deleteDirectory($dir);
    }
});

describe('ApkProtector protect', function () {
    it('modifies APK file size after protection', function () {
        $dir = createTestApkDir();
        $apkPath = createTestApk($dir);
        $originalSize = filesize($apkPath);

        $protector = new ApkProtector;
        $protector->protect($apkPath);

        clearstatcache(true, $apkPath);
        expect(filesize($apkPath))->toBeGreaterThan($originalSize);
    });

    it('does nothing when file does not exist', function () {
        $protector = new ApkProtector;
        $protector->protect('/nonexistent/path/fake.apk');

        expect(true)->toBeTrue();
    });

    it('appends fake central directory entry with BTfile.bin', function () {
        $dir = createTestApkDir();
        $apkPath = createTestApk($dir);

        $protector = new ApkProtector(
            zeroSizes: false,
            corruptCRC: false,
            corruptOffsets: false,
            addFakeExtra: false,
            addPadding: false,
            addFakeEntries: true,
            randomCompressionMethod: false,
            addFakeLocalHeaders: false,
        );
        $protector->protect($apkPath);

        $data = file_get_contents($apkPath);
        expect(str_contains($data, 'BTfile.bin'))->toBeTrue();
    });

    it('appends fake local header with AndroidManifest.xml', function () {
        $dir = createTestApkDir();
        $apkPath = createTestApk($dir);

        $protector = new ApkProtector(
            zeroSizes: false,
            corruptCRC: false,
            corruptOffsets: false,
            addFakeExtra: false,
            addPadding: false,
            addFakeEntries: false,
            randomCompressionMethod: false,
            addFakeLocalHeaders: true,
        );
        $protector->protect($apkPath);

        $data = file_get_contents($apkPath);
        // 应有多个 AndroidManifest.xml 出现（原始 + 虚假）
        expect(substr_count($data, 'AndroidManifest.xml'))->toBeGreaterThan(1);
    });

    it('appends random padding between 1-5 KB', function () {
        $dir = createTestApkDir();
        $apkPath = createTestApk($dir);
        $originalSize = filesize($apkPath);

        $protector = new ApkProtector(
            zeroSizes: false,
            corruptCRC: false,
            corruptOffsets: false,
            addFakeExtra: false,
            addPadding: true,
            addFakeEntries: false,
            randomCompressionMethod: false,
            addFakeLocalHeaders: false,
        );
        $protector->protect($apkPath);

        clearstatcache(true, $apkPath);
        $growth = filesize($apkPath) - $originalSize;
        expect($growth)->toBeGreaterThanOrEqual(1024);
        expect($growth)->toBeLessThanOrEqual(6000);
    });

    it('appends fake extra field (4 bytes 0xFF)', function () {
        $dir = createTestApkDir();
        $apkPath = createTestApk($dir);

        $protector = new ApkProtector(
            zeroSizes: false,
            corruptCRC: false,
            corruptOffsets: false,
            addFakeExtra: true,
            addPadding: false,
            addFakeEntries: false,
            randomCompressionMethod: false,
            addFakeLocalHeaders: false,
        );
        $protector->protect($apkPath);

        $data = file_get_contents($apkPath);
        expect(str_contains($data, "\xFF\xFF\xFF\xFF"))->toBeTrue();
    });

    it('respects all-disabled options and only adds zip comment', function () {
        $dir = createTestApkDir();
        $apkPath = createTestApk($dir);
        $originalSize = filesize($apkPath);

        $protector = new ApkProtector(
            zeroSizes: false,
            corruptCRC: false,
            corruptOffsets: false,
            addFakeExtra: false,
            addPadding: false,
            addFakeEntries: false,
            randomCompressionMethod: false,
            addFakeLocalHeaders: false,
        );
        $protector->protect($apkPath);

        clearstatcache(true, $apkPath);
        $growth = filesize($apkPath) - $originalSize;
        // ZIP comment 只有 100-300 字节
        expect($growth)->toBeLessThan(500);
        expect($growth)->toBeGreaterThan(0);
    });
});

describe('ApkProtector modifyDex', function () {
    it('overwrites magic, fileSize and headerSize at fixed offsets', function () {
        $dir = createTestApkDir();
        $apkPath = $dir.'/dex_test.apk';
        File::ensureDirectoryExists($dir);

        // 构造一个足够大的假 APK 文件（>= 40 字节）
        $data = str_repeat("\x00", 100);
        file_put_contents($apkPath, $data);

        $protector = new ApkProtector;
        $count = $protector->modifyDex($apkPath);

        expect($count)->toBe(1);

        $modified = file_get_contents($apkPath);

        // magic → ZIP 签名 PK\x03\x04 + 4 零字节
        expect(substr($modified, 0, 4))->toBe("\x50\x4B\x03\x04");
        expect(substr($modified, 4, 4))->toBe("\x00\x00\x00\x00");

        // file_size → 0 (offset 32, 4 bytes LE)
        $fileSize = unpack('V', substr($modified, 32, 4))[1];
        expect($fileSize)->toBe(0);

        // header_size → 9999 (offset 36, 4 bytes LE)
        $headerSize = unpack('V', substr($modified, 36, 4))[1];
        expect($headerSize)->toBe(9999);

        // 文件大小不变（不追加垃圾数据）
        expect(strlen($modified))->toBe(100);
    });

    it('returns zero for non-existent file', function () {
        $protector = new ApkProtector;
        $count = $protector->modifyDex('/nonexistent/fake.apk');

        expect($count)->toBe(0);
    });

    it('returns zero for file smaller than 40 bytes', function () {
        $dir = createTestApkDir();
        $apkPath = $dir.'/tiny.apk';
        File::ensureDirectoryExists($dir);

        file_put_contents($apkPath, str_repeat("\x00", 30));

        $protector = new ApkProtector;
        $count = $protector->modifyDex($apkPath);

        expect($count)->toBe(0);
    });

    it('does not change file size', function () {
        $dir = createTestApkDir();
        $apkPath = $dir.'/size_test.apk';
        File::ensureDirectoryExists($dir);

        $data = str_repeat("\xAB", 500);
        file_put_contents($apkPath, $data);
        $originalSize = strlen($data);

        $protector = new ApkProtector;
        $protector->modifyDex($apkPath);

        $modified = file_get_contents($apkPath);
        expect(strlen($modified))->toBe($originalSize);
    });
});
