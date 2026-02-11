<?php

declare(strict_types=1);

namespace App\WebSocket\Config;

final class WebSocketConfig
{
    private const REDIS_PREFIX = 'ws:';

    private const DEVICE_STATUS_TTL = 86400;

    private const LAST_NOTIFIED_TTL = 300;

    private const NEW_CONNECTION_THRESHOLD = 60;

    private const HEARTBEAT_CHECK_INTERVAL = 25;

    private const HEARTBEAT_TIMEOUT = 75;

    public static function redisPrefix(): string
    {
        return config('websocket.redis.prefix', self::REDIS_PREFIX);
    }

    public static function deviceStatusTtl(): int
    {
        return (int) config('websocket.redis.device_status_ttl', self::DEVICE_STATUS_TTL);
    }

    public static function lastNotifiedTtl(): int
    {
        return self::LAST_NOTIFIED_TTL;
    }

    public static function newConnectionThreshold(): int
    {
        return self::NEW_CONNECTION_THRESHOLD;
    }

    public static function heartbeatCheckInterval(): int
    {
        return (int) config('websocket.heartbeat.check_interval', self::HEARTBEAT_CHECK_INTERVAL);
    }

    public static function heartbeatTimeout(): int
    {
        return (int) config('websocket.heartbeat.timeout', self::HEARTBEAT_TIMEOUT);
    }

    public static function deviceStatusKey(string $phoneId): string
    {
        return self::redisPrefix().'device:'.$phoneId;
    }

    public static function lastNotifiedKey(string $phoneId): string
    {
        return self::redisPrefix().'device:'.$phoneId.':last_notified';
    }

    public static function clientTypes(): array
    {
        return config('websocket.client_types', [
            'device' => 'Slr_client',
            'panel' => 'slr_panel',
            'panel_send' => 'slr_panelsend',
        ]);
    }
}
