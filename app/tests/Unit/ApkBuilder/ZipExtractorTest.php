<?php

declare(strict_types=1);

namespace Tests\Unit\ApkBuilder;

use App\Exceptions\ApkBuilder\ApkBuildException;
use App\Services\ApkBuilder\Contracts\FileSystemInterface;
use App\Services\ApkBuilder\ZipExtractor;
use Illuminate\Support\Facades\File;

beforeEach(function () {
    $this->tempDir = sys_get_temp_dir().'/zip_test_'.uniqid();
    File::ensureDirectoryExists($this->tempDir);
});

afterEach(function () {
    if (isset($this->tempDir) && File::isDirectory($this->tempDir)) {
        File::deleteDirectory($this->tempDir);
    }
});

describe('ZipExtractor', function () {
    it('getErrorMessage returns correct message for known codes', function () {
        expect(ZipExtractor::getErrorMessage(\ZipArchive::ER_NOENT))->toBe('文件不存在');
        expect(ZipExtractor::getErrorMessage(\ZipArchive::ER_NOZIP))->toBe('不是有效的 ZIP 文件');
        expect(ZipExtractor::getErrorMessage(\ZipArchive::ER_OPEN))->toBe('无法打开文件');
    });

    it('getErrorMessage returns unknown error for unknown codes', function () {
        expect(ZipExtractor::getErrorMessage(9999))->toBe('未知错误 (9999)');
    });

    it('extract throws when zip file cannot be opened', function () {
        $fileSystem = mock(FileSystemInterface::class);
        $fileSystem->shouldReceive('ensureDirectoryExists')->andReturn(null);
        $fileSystem->shouldReceive('isDirectory')->andReturn(false);
        $fileSystem->shouldReceive('deleteDirectory')->andReturn(true);

        $extractor = new ZipExtractor($fileSystem);
        $nonExistentZip = $this->tempDir.'/nonexistent.zip';

        expect(fn () => $extractor->extract($nonExistentZip, $this->tempDir.'/target'))
            ->toThrow(ApkBuildException::class, '无法打开模板 ZIP 文件');
    });

    it('extract successfully extracts valid zip', function () {
        $zipPath = $this->tempDir.'/test.zip';
        $targetDir = $this->tempDir.'/extracted';

        $zip = new \ZipArchive;
        $zip->open($zipPath, \ZipArchive::CREATE);
        $zip->addFromString('test.txt', 'hello world');
        $zip->addFromString('subdir/nested.txt', 'nested content');
        $zip->close();

        $fileSystem = mock(FileSystemInterface::class);
        $fileSystem->shouldReceive('ensureDirectoryExists')->andReturn(null);
        $fileSystem->shouldReceive('isDirectory')->andReturn(false);
        $fileSystem->shouldReceive('deleteDirectory')->andReturn(true);

        $extractor = new ZipExtractor($fileSystem);
        $result = $extractor->extract($zipPath, $targetDir);

        expect($result)->toBeTrue();
        expect(file_exists($targetDir.'/test.txt'))->toBeTrue();
        expect(file_get_contents($targetDir.'/test.txt'))->toBe('hello world');
        expect(file_exists($targetDir.'/subdir/nested.txt'))->toBeTrue();
    });

    it('extract deletes existing target directory before extraction', function () {
        $zipPath = $this->tempDir.'/test.zip';
        $targetDir = $this->tempDir.'/extracted';

        mkdir($targetDir);
        file_put_contents($targetDir.'/old.txt', 'old content');

        $zip = new \ZipArchive;
        $zip->open($zipPath, \ZipArchive::CREATE);
        $zip->addFromString('new.txt', 'new content');
        $zip->close();

        $deleteWasCalled = false;
        $fileSystem = mock(FileSystemInterface::class);
        $fileSystem->shouldReceive('ensureDirectoryExists')->andReturn(null);
        $fileSystem->shouldReceive('isDirectory')->andReturn(true);
        $fileSystem->shouldReceive('deleteDirectory')->andReturnUsing(function ($path) use (&$deleteWasCalled, $targetDir) {
            if ($path === $targetDir) {
                $deleteWasCalled = true;
                File::deleteDirectory($path);
            }

            return true;
        });

        $extractor = new ZipExtractor($fileSystem);
        $extractor->extract($zipPath, $targetDir);

        expect($deleteWasCalled)->toBeTrue();
    });
});
