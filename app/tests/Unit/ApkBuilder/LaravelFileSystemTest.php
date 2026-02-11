<?php

declare(strict_types=1);

namespace Tests\Unit\ApkBuilder;

use App\Services\ApkBuilder\Contracts\FileSystemInterface;
use App\Services\ApkBuilder\LaravelFileSystem;
use Illuminate\Support\Facades\File;

beforeEach(function () {
    $this->tempDir = sys_get_temp_dir().'/fs_test_'.uniqid();
    File::ensureDirectoryExists($this->tempDir);
    $this->fileSystem = new LaravelFileSystem;
});

afterEach(function () {
    if (isset($this->tempDir) && File::isDirectory($this->tempDir)) {
        File::deleteDirectory($this->tempDir);
    }
});

describe('LaravelFileSystem', function () {
    it('implements FileSystemInterface', function () {
        expect($this->fileSystem)->toBeInstanceOf(FileSystemInterface::class);
    });

    it('exists returns true for existing file', function () {
        $file = $this->tempDir.'/test.txt';
        file_put_contents($file, 'content');

        expect($this->fileSystem->exists($file))->toBeTrue();
    });

    it('exists returns false for non-existing file', function () {
        expect($this->fileSystem->exists($this->tempDir.'/nonexistent.txt'))->toBeFalse();
    });

    it('isDirectory returns true for directory', function () {
        expect($this->fileSystem->isDirectory($this->tempDir))->toBeTrue();
    });

    it('isDirectory returns false for file', function () {
        $file = $this->tempDir.'/test.txt';
        file_put_contents($file, 'content');

        expect($this->fileSystem->isDirectory($file))->toBeFalse();
    });

    it('get reads file content', function () {
        $file = $this->tempDir.'/test.txt';
        file_put_contents($file, 'hello world');

        expect($this->fileSystem->get($file))->toBe('hello world');
    });

    it('put writes file content', function () {
        $file = $this->tempDir.'/test.txt';

        $this->fileSystem->put($file, 'new content');

        expect(file_get_contents($file))->toBe('new content');
    });

    it('copy copies file', function () {
        $src = $this->tempDir.'/src.txt';
        $dst = $this->tempDir.'/dst.txt';
        file_put_contents($src, 'source content');

        $result = $this->fileSystem->copy($src, $dst);

        expect($result)->toBeTrue();
        expect(file_get_contents($dst))->toBe('source content');
    });

    it('delete removes file', function () {
        $file = $this->tempDir.'/test.txt';
        file_put_contents($file, 'content');

        $result = $this->fileSystem->delete($file);

        expect($result)->toBeTrue();
        expect(file_exists($file))->toBeFalse();
    });

    it('ensureDirectoryExists creates nested directories', function () {
        $nested = $this->tempDir.'/a/b/c';

        $this->fileSystem->ensureDirectoryExists($nested);

        expect(is_dir($nested))->toBeTrue();
    });

    it('size returns file size', function () {
        $file = $this->tempDir.'/test.txt';
        file_put_contents($file, '12345');

        expect($this->fileSystem->size($file))->toBe(5);
    });

    it('deleteDirectory removes directory recursively', function () {
        $subDir = $this->tempDir.'/subdir';
        mkdir($subDir);
        file_put_contents($subDir.'/file.txt', 'content');

        $result = $this->fileSystem->deleteDirectory($subDir);

        expect($result)->toBeTrue();
        expect(is_dir($subDir))->toBeFalse();
    });

    it('glob returns matching files', function () {
        file_put_contents($this->tempDir.'/a.txt', '');
        file_put_contents($this->tempDir.'/b.txt', '');
        file_put_contents($this->tempDir.'/c.log', '');

        $result = $this->fileSystem->glob($this->tempDir.'/*.txt');

        expect($result)->toBeArray();
        expect(count($result))->toBe(2);
    });

    it('copyDirectory copies directory contents', function () {
        $src = $this->tempDir.'/src';
        $dst = $this->tempDir.'/dst';
        mkdir($src);
        file_put_contents($src.'/file.txt', 'content');

        $result = $this->fileSystem->copyDirectory($src, $dst);

        expect($result)->toBeTrue();
        expect(file_exists($dst.'/file.txt'))->toBeTrue();
    });
});
