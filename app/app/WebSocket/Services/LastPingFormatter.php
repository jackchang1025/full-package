<?php

declare(strict_types=1);

namespace App\WebSocket\Services;

final class LastPingFormatter
{
    public static function format(?float $lastPingMs): ?string
    {
        if ($lastPingMs === null || $lastPingMs <= 0) {
            return null;
        }

        return date('Y-m-d H:i:s', (int) ($lastPingMs / 1000));
    }
}
