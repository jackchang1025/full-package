<?php

declare(strict_types=1);

namespace App\Exceptions\GradleApkBuilder;

use RuntimeException;

final class GradleApkBuildException extends RuntimeException
{
    public function __construct(
        string $message,
        public readonly string $step = '',
        public readonly string $buildOutput = '',
        ?\Throwable $previous = null,
    ) {
        parent::__construct($message, 0, $previous);
    }

    public static function environmentMissing(string $tool, string $detail = ''): self
    {
        return new self(
            message: "构建环境缺失: {$tool}" . ($detail ? " ({$detail})" : ''),
            step: 'check_environment',
        );
    }

    public static function stepFailed(string $step, string $reason, string $output = ''): self
    {
        return new self(
            message: "构建步骤 [{$step}] 失败: {$reason}",
            step: $step,
            buildOutput: $output,
        );
    }
}
