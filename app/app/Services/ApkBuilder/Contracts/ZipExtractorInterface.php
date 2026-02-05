<?php

declare(strict_types=1);

namespace App\Services\ApkBuilder\Contracts;

interface ZipExtractorInterface
{
    /** @throws \App\Exceptions\ApkBuilder\ApkBuildException */
    public function extract(string $zipPath, string $targetDir): bool;

    public static function getErrorMessage(int $code): string;
}
