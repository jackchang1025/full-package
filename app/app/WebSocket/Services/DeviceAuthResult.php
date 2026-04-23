<?php

declare(strict_types=1);

namespace App\WebSocket\Services;

final readonly class DeviceAuthResult
{
    public function __construct(
        public int $userId,
        public string $sessionId,
    ) {}
}
