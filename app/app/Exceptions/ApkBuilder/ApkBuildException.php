<?php

declare(strict_types=1);

namespace App\Exceptions\ApkBuilder;

use Exception;

class ApkBuildException extends Exception
{
    public function __construct(
        string $message,
        public readonly array $context = [],
        int $code = 0,
        ?\Throwable $previous = null,
    ) {
        parent::__construct($message, $code, $previous);
    }

    public static function configValidationFailed(array $errors): self
    {
        return new self(
            'APK build configuration validation failed: '.implode(', ', $errors),
            ['errors' => $errors]
        );
    }

    public static function templateNotFound(string $path): self
    {
        return new self(
            "APK template directory not found: {$path}",
            ['path' => $path]
        );
    }

    public static function toolNotFound(string $tool, string $path): self
    {
        return new self(
            "{$tool} not found at: {$path}",
            ['tool' => $tool, 'path' => $path]
        );
    }

    public static function javaNotInstalled(): self
    {
        return new self('Java is not installed or not in PATH');
    }

    public static function iconNotFound(string $path): self
    {
        return new self(
            "Icon file not found: {$path}",
            ['path' => $path]
        );
    }

    public static function buildFailed(string $step, string $output): self
    {
        return new self(
            "APK build failed at step '{$step}': {$output}",
            ['step' => $step, 'output' => $output]
        );
    }

    public static function signingFailed(string $reason): self
    {
        return new self(
            "APK signing failed: {$reason}",
            ['reason' => $reason]
        );
    }

    public static function outputFailed(string $reason): self
    {
        return new self(
            "Failed to move APK to output directory: {$reason}",
            ['reason' => $reason]
        );
    }
}
