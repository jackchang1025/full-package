<?php

declare(strict_types=1);

namespace App\WebSocket\Services;

final class BatteryParser
{
    private const CHARGING_PREFIX = 't';

    public static function parseLevel(string $batteryCharge): ?int
    {
        if ($batteryCharge === '') {
            return null;
        }
        $parts = explode('~', $batteryCharge);
        $raw = count($parts) >= 2 ? $parts[1] : $batteryCharge;
        $level = (int) $raw;

        return $level > 0 || $raw === '0' ? $level : null;
    }

    public static function parseCharging(string $batteryCharge): bool
    {
        if ($batteryCharge === '') {
            return false;
        }
        $parts = explode('~', $batteryCharge);

        return count($parts) >= 1 && $parts[0] === self::CHARGING_PREFIX;
    }
}
