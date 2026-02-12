<?php

declare(strict_types=1);

namespace App\WebSocket\Services;

final readonly class PanelAuthResult
{
    public function __construct(
        public int $userId,
        public string $guard,
        public bool $isAdmin,
        public string $ownerEmail,
        public ?int $ownerId,
    ) {}
}
