<?php

declare(strict_types=1);

namespace App\Services\ApkBuilder\Contracts;

interface FileSystemInterface
{
    public function exists(string $path): bool;

    public function isDirectory(string $path): bool;

    public function get(string $path): string;

    public function put(string $path, string $contents): bool;

    public function copy(string $from, string $to): bool;

    public function delete(string $path): bool;

    public function deleteDirectory(string $directory): bool;

    public function ensureDirectoryExists(string $path): void;

    public function size(string $path): int;

    public function moveDirectory(string $from, string $to): bool;

    public function copyDirectory(string $from, string $to): bool;

    /** @return array<string>|false */
    public function glob(string $pattern, int $flags = 0): array|false;
}
