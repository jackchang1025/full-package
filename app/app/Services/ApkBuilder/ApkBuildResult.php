<?php

declare(strict_types=1);

namespace App\Services\ApkBuilder;

final readonly class ApkBuildResult
{
    public function __construct(
        public string $path,
        public string $packageName,
        public array $stats = [],
        public float $totalTimeMs = 0,
    ) {}

    public function toArray(): array
    {
        return [
            'path' => $this->path,
            'stats' => [
                'steps' => $this->stats,
                'total_time_ms' => $this->totalTimeMs,
                'total_time_formatted' => $this->formatTime($this->totalTimeMs),
            ],
        ];
    }

    public function formatTime(?float $ms = null): string
    {
        $ms ??= $this->totalTimeMs;

        if ($ms < 1000) {
            return round($ms).'ms';
        }

        if ($ms < 60000) {
            return round($ms / 1000, 1).'s';
        }

        return round($ms / 60000, 1).'min';
    }
}
