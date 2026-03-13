<?php

use App\Services\ApkBuilder\ApkProtector;

describe('ApkProtector fake encryption flags', function () {
    it('sets encryption bits on target LFH entries', function () {
        $zipData = createMinimalZip('AndroidManifest.xml', 'test content');
        $protector = new ApkProtector(enableFakeEncryption: true);

        $tempFile = tempnam(sys_get_temp_dir(), 'apk_test_');
        file_put_contents($tempFile, $zipData);
        $protector->protect($tempFile);
        $result = file_get_contents($tempFile);
        unlink($tempFile);

        $lfhPos = strpos($result, "\x50\x4b\x03\x04");
        expect($lfhPos)->not->toBeNull();

        $flags = unpack('v', substr($result, $lfhPos + 6, 2))[1];
        expect($flags & 0x0001)->toBe(0x0001);
        expect($flags & 0x0040)->toBe(0x0040);
    });

    it('sets encryption bits on target CD entries', function () {
        $zipData = createMinimalZip('AndroidManifest.xml', 'test content');
        $protector = new ApkProtector(enableFakeEncryption: true);

        $tempFile = tempnam(sys_get_temp_dir(), 'apk_test_');
        file_put_contents($tempFile, $zipData);
        $protector->protect($tempFile);
        $result = file_get_contents($tempFile);
        unlink($tempFile);

        $cdPos = strpos($result, "\x50\x4b\x01\x02");
        expect($cdPos)->not->toBeNull();

        $flags = unpack('v', substr($result, $cdPos + 8, 2))[1];
        expect($flags & 0x0001)->toBe(0x0001);
        expect($flags & 0x0040)->toBe(0x0040);
    });

    it('does not modify non-target files', function () {
        $zipData = createMinimalZip('some_other_file.txt', 'test content');
        $protector = new ApkProtector(enableFakeEncryption: true);

        $tempFile = tempnam(sys_get_temp_dir(), 'apk_test_');
        file_put_contents($tempFile, $zipData);
        $protector->protect($tempFile);
        $result = file_get_contents($tempFile);
        unlink($tempFile);

        $lfhPos = strpos($result, "\x50\x4b\x03\x04");
        $flags = unpack('v', substr($result, $lfhPos + 6, 2))[1];
        expect($flags & 0x0041)->toBe(0);
    });
});

describe('ApkProtector EOCD tampering', function () {
    it('tampers disk number fields', function () {
        $zipData = createMinimalZip('AndroidManifest.xml', 'test');
        $protector = new ApkProtector(enableEocdTampering: true);

        $tempFile = tempnam(sys_get_temp_dir(), 'apk_test_');
        file_put_contents($tempFile, $zipData);
        $protector->protect($tempFile);
        $result = file_get_contents($tempFile);
        unlink($tempFile);

        $eocdPos = strrpos($result, "\x50\x4b\x05\x06");
        expect($eocdPos)->not->toBeFalse();

        $diskNum = unpack('v', substr($result, $eocdPos + 4, 2))[1];
        expect($diskNum)->toBeGreaterThanOrEqual(0xFFF0);
    });

    it('preserves CD offset and size', function () {
        $zipData = createMinimalZip('AndroidManifest.xml', 'test');
        $eocdPos = strrpos($zipData, "\x50\x4b\x05\x06");
        $origCdOffset = unpack('V', substr($zipData, $eocdPos + 16, 4))[1];
        $origCdSize = unpack('V', substr($zipData, $eocdPos + 12, 4))[1];

        $protector = new ApkProtector(enableEocdTampering: true);
        $tempFile = tempnam(sys_get_temp_dir(), 'apk_test_');
        file_put_contents($tempFile, $zipData);
        $protector->protect($tempFile);
        $result = file_get_contents($tempFile);
        unlink($tempFile);

        $newEocdPos = strrpos($result, "\x50\x4b\x05\x06");
        $newCdOffset = unpack('V', substr($result, $newEocdPos + 16, 4))[1];
        $newCdSize = unpack('V', substr($result, $newEocdPos + 12, 4))[1];

        expect($newCdOffset)->toBe($origCdOffset);
        expect($newCdSize)->toBe($origCdSize);
    });
});

describe('ApkProtector path traversal entries', function () {
    it('generates the requested number of fake entries', function () {
        $zipData = createMinimalZip('classes.dex', 'dex data');
        $protector = new ApkProtector(enablePathTraversalEntries: true, fakeEntryCount: 50);

        $tempFile = tempnam(sys_get_temp_dir(), 'apk_test_');
        file_put_contents($tempFile, $zipData);
        $protector->protect($tempFile);
        $result = file_get_contents($tempFile);
        unlink($tempFile);

        $cdCount = substr_count($result, "\x50\x4b\x01\x02");
        expect($cdCount)->toBeGreaterThanOrEqual(51);
    });

    it('generates diverse path patterns', function () {
        $zipData = createMinimalZip('classes.dex', 'dex data');
        $protector = new ApkProtector(enablePathTraversalEntries: true, fakeEntryCount: 120);

        $tempFile = tempnam(sys_get_temp_dir(), 'apk_test_');
        file_put_contents($tempFile, $zipData);
        $protector->protect($tempFile);
        $result = file_get_contents($tempFile);
        unlink($tempFile);

        expect($result)->toContain('AndroidManifest.xml/');
        expect($result)->toContain('/AndroidManifest.xml');
        expect($result)->toContain('classes.dex/');
    });
});

describe('ApkProtector unknown compression method', function () {
    it('injects non-standard compression in CD', function () {
        $zipData = createMinimalZip('AndroidManifest.xml', 'manifest data');
        $protector = new ApkProtector(enableUnknownCompression: true);

        $tempFile = tempnam(sys_get_temp_dir(), 'apk_test_');
        file_put_contents($tempFile, $zipData);
        $protector->protect($tempFile);
        $result = file_get_contents($tempFile);
        unlink($tempFile);

        $cdPos = strpos($result, "\x50\x4b\x01\x02");
        $compressionMethod = unpack('v', substr($result, $cdPos + 10, 2))[1];
        expect($compressionMethod)->toBe(37386);
    });
});

function createMinimalZip(string $filename, string $content): string
{
    $tempFile = tempnam(sys_get_temp_dir(), 'zip_test_');
    $zip = new ZipArchive();
    $zip->open($tempFile, ZipArchive::CREATE | ZipArchive::OVERWRITE);
    $zip->addFromString($filename, $content);
    $zip->close();
    $data = file_get_contents($tempFile);
    unlink($tempFile);

    return $data;
}
