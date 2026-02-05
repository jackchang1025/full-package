<?php

declare(strict_types=1);

namespace App\Services\ApkBuilder;

use App\Services\ApkBuilder\Contracts\FileSystemInterface;
use Illuminate\Support\Facades\File;

final class LaravelFileSystem implements FileSystemInterface
{
    public function exists(string $path): bool
    {
        return File::exists($path);
    }

    public function isDirectory(string $path): bool
    {
        return File::isDirectory($path);
    }

    public function get(string $path): string
    {
        return File::get($path);
    }

    public function put(string $path, string $contents): bool
    {
        return (bool) File::put($path, $contents);
    }

    public function copy(string $from, string $to): bool
    {
        return File::copy($from, $to);
    }

    public function delete(string $path): bool
    {
        return File::delete($path);
    }

    public function deleteDirectory(string $directory): bool
    {
        return File::deleteDirectory($directory);
    }

    public function ensureDirectoryExists(string $path): void
    {
        File::ensureDirectoryExists($path);
    }

    public function size(string $path): int
    {
        return File::size($path);
    }

    public function moveDirectory(string $from, string $to): bool
    {
        return File::moveDirectory($from, $to);
    }

    public function copyDirectory(string $from, string $to): bool
    {
        return File::copyDirectory($from, $to);
    }

    public function glob(string $pattern, int $flags = 0): array|false
    {
        return glob($pattern, $flags);
    }
}
