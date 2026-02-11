<?php

declare(strict_types=1);

namespace App\WebSocket;

use App\WebSocket\Config\WebSocketConfig;
use App\WebSocket\Services\BatteryParser;
use Illuminate\Support\Facades\Redis;
use Swoole\Table;
use Swoole\WebSocket\Server as SwooleServer;

class ConnectionManager
{
    private SwooleServer $server;

    private Table $fdToPhoneId;

    private Table $phoneIdToFd;

    private Table $panelSubscriptions;

    private Table $panelUserSubscriptions;

    /**
     * @param  array<string, Table>  $tables  预先创建的共享内存表，必须包含:
     *                                        - fdToPhoneId: fd -> phoneId/clientType 映射
     *                                        - phoneIdToFd: phoneId -> fd 映射
     *                                        - panelSubscriptions: panel fd -> phoneId 订阅
     *                                        - panelUserSubscriptions: panel fd -> email/isAdmin
     */
    public function __construct(SwooleServer $server, array $tables)
    {
        $this->server = $server;

        // 使用外部传入的共享表（在 server->start() 之前创建，所有 Worker 共享）
        $this->fdToPhoneId = $tables['fdToPhoneId'];
        $this->phoneIdToFd = $tables['phoneIdToFd'];
        $this->panelSubscriptions = $tables['panelSubscriptions'];
        $this->panelUserSubscriptions = $tables['panelUserSubscriptions'];
    }

    public function registerDevice(int $fd, string $phoneId): void
    {
        $existingFd = $this->getDeviceFd($phoneId);
        if ($existingFd !== null && $existingFd !== $fd) {
            $this->server->close($existingFd);
        }

        $this->fdToPhoneId->set((string) $fd, [
            'phone_id' => $phoneId,
            'client_type' => 'device',
        ]);
        $this->phoneIdToFd->set($phoneId, ['fd' => $fd]);

        $this->updateDeviceStatus($phoneId, ['is_online' => true, 'last_seen_at' => time()]);

        WebSocketLog::getLogger()->info("Device registered: {$phoneId} -> fd={$fd}");
    }

    public function registerPanel(int $fd, string $phoneId): void
    {
        $this->fdToPhoneId->set((string) $fd, [
            'phone_id' => $phoneId,
            'client_type' => 'panel',
        ]);
        $this->panelSubscriptions->set((string) $fd, ['phone_id' => $phoneId]);

        WebSocketLog::getLogger()->info("Panel subscribed: fd={$fd} -> {$phoneId}");
    }

    public function handleDisconnect(int $fd): void
    {
        $info = $this->fdToPhoneId->get((string) $fd);

        if ($info === false) {
            return;
        }

        $phoneId = $info['phone_id'];
        $clientType = $info['client_type'];

        $this->fdToPhoneId->del((string) $fd);

        if ($clientType === 'device') {
            $currentFd = $this->phoneIdToFd->get($phoneId);
            if ($currentFd !== false && $currentFd['fd'] === $fd) {
                $this->phoneIdToFd->del($phoneId);
                $this->updateDeviceStatus($phoneId, ['is_online' => false]);
                $this->syncOfflineToDatabaseAndNotify($phoneId);
                $this->notifyPanelsDeviceOffline($phoneId);
            }
        } else {
            $this->panelSubscriptions->del((string) $fd);
            $this->unregisterPanelUser($fd);
        }

        WebSocketLog::getLogger()->info("Disconnected: fd={$fd}, type={$clientType}, phoneId={$phoneId}");
    }

    public function getDeviceFd(string $phoneId): ?int
    {
        $data = $this->phoneIdToFd->get($phoneId);

        return $data !== false ? $data['fd'] : null;
    }

    public function getPhoneId(int $fd): ?string
    {
        $data = $this->fdToPhoneId->get((string) $fd);

        return $data !== false ? $data['phone_id'] : null;
    }

    public function getClientType(int $fd): ?string
    {
        $data = $this->fdToPhoneId->get((string) $fd);

        return $data !== false ? $data['client_type'] : null;
    }

    public function getClientIp(string $phoneId): ?string
    {
        try {
            $fd = $this->getDeviceFd($phoneId);
            if ($fd === null) {
                return null;
            }

            $clientInfo = $this->server->getClientInfo($fd);
            if ($clientInfo === false) {
                return null;
            }

            $ip = $clientInfo['remote_ip'] ?? '';

            // Handle IPv6-mapped IPv4 format (::ffff:192.168.1.1 → 192.168.1.1)
            if (str_starts_with($ip, '::ffff:')) {
                $ip = substr($ip, 7);
            }

            return $ip ?: null;
        } catch (\Throwable) {
            return null;
        }
    }

    public function isDeviceOnline(string $phoneId): bool
    {
        return $this->phoneIdToFd->exists($phoneId);
    }

    public function sendToDevice(string $phoneId, array $data): bool
    {
        $fd = $this->getDeviceFd($phoneId);

        if ($fd === null) {
            WebSocketLog::getLogger()->warning('sendToDevice failed: device not found', [
                'phone_id' => $phoneId,
                'data_type' => $data['type'] ?? 'unknown',
                'subc' => $data['subc'] ?? 'unknown',
            ]);

            return false;
        }

        WebSocketLog::getLogger()->debug('sendToDevice', [
            'phone_id' => $phoneId,
            'fd' => $fd,
            'data' => $data,
        ]);

        return $this->send($fd, $data);
    }

    public function sendToPanels(string $phoneId, array $data): void
    {
        foreach ($this->panelSubscriptions as $fd => $subscription) {
            if ($subscription['phone_id'] === $phoneId) {
                $this->send((int) $fd, $data);
            }
        }
    }

    public function send(int $fd, array $data): bool
    {
        // 单 Worker 模式下 isEstablished() 可以正常工作
        // 如果需要多 Worker 支持，需要改用 Redis Pub/Sub 或 Swoole Task Worker
        if (! $this->server->isEstablished($fd)) {
            return false;
        }

        try {
            $json = json_encode($data, JSON_UNESCAPED_UNICODE | JSON_THROW_ON_ERROR);

            return $this->server->push($fd, $json);
        } catch (\JsonException $e) {
            WebSocketLog::getLogger()->error("JSON encode error: fd={$fd}", ['error' => $e->getMessage()]);

            return false;
        }
    }

    public function broadcast(array $data, ?string $excludePhoneId = null): void
    {
        $json = json_encode($data, JSON_UNESCAPED_UNICODE);

        foreach ($this->server->connections as $fd) {
            if (! $this->server->isEstablished($fd)) {
                continue;
            }

            if ($excludePhoneId !== null) {
                $phoneId = $this->getPhoneId($fd);
                if ($phoneId === $excludePhoneId) {
                    continue;
                }
            }

            $this->server->push($fd, $json);
        }
    }

    public function updateDeviceStatus(string $phoneId, array $data): void
    {
        $key = WebSocketConfig::deviceStatusKey($phoneId);
        $existing = Redis::hgetall($key);
        $merged = array_merge($existing ?: [], $data);

        Redis::hmset($key, $merged);
        Redis::expire($key, WebSocketConfig::deviceStatusTtl());
    }

    public function getDeviceStatus(string $phoneId): array
    {
        $key = WebSocketConfig::deviceStatusKey($phoneId);

        return Redis::hgetall($key) ?: [];
    }

    public function getAllOnlineDevices(): array
    {
        $devices = [];

        foreach ($this->phoneIdToFd as $phoneId => $data) {
            $phoneId = (string) $phoneId;
            $devices[$phoneId] = $this->getDeviceStatus($phoneId);
        }

        return $devices;
    }

    private function notifyPanelsDeviceOffline(string $phoneId): void
    {
        $status = $this->getDeviceStatus($phoneId);

        // passwords 数据包含在 phoneInfo.phone_password 中，由前端解析
        $this->sendToPanels($phoneId, [
            'type' => 'statusBatch',
            'pid' => $phoneId,
            'serverToPhone' => 'CLOSED',
            'lastPing' => date('Y-m-d H:i:s'),
            'phoneInfo' => array_merge($status, ['is_online' => false]),
        ]);
    }

    private function syncOfflineToDatabaseAndNotify(string $phoneId): void
    {
        $device = \App\Models\Device::where('uuid', $phoneId)->first();

        if ($device === null) {
            return;
        }

        $device->update([
            'is_online' => false,
            'last_seen_at' => now(),
        ]);

        $this->notifyPanelUsersDeviceOffline($phoneId, $device->user_id);
    }

    public function getServer(): SwooleServer
    {
        return $this->server;
    }

    public function getConnectionCount(): int
    {
        return count($this->server->connections);
    }

    public function getDeviceCount(): int
    {
        return $this->phoneIdToFd->count();
    }

    public function getPanelCount(): int
    {
        return $this->panelSubscriptions->count();
    }

    /**
     * Reset all connection tables. FOR TESTING ONLY.
     */
    public function resetAllTables(): void
    {
        // Clear all entries from each table
        foreach ($this->fdToPhoneId as $key => $value) {
            $this->fdToPhoneId->del($key);
        }
        foreach ($this->phoneIdToFd as $key => $value) {
            $this->phoneIdToFd->del($key);
        }
        foreach ($this->panelSubscriptions as $key => $value) {
            $this->panelSubscriptions->del($key);
        }
        foreach ($this->panelUserSubscriptions as $key => $value) {
            $this->panelUserSubscriptions->del($key);
        }

        WebSocketLog::getLogger()->info('All connection tables reset (test mode)');
    }

    public function registerPanelUser(int $fd, string $emailEncrypted, bool $isAdmin = false, ?int $userId = null): void
    {
        $this->panelUserSubscriptions->set((string) $fd, [
            'email_encrypted' => $emailEncrypted,
            'is_admin' => $isAdmin ? 1 : 0,
            'user_id' => $userId ?? 0,
        ]);
    }

    public function unregisterPanelUser(int $fd): void
    {
        $this->panelUserSubscriptions->del((string) $fd);
    }

    public function getPanelUser(int $fd): array|false
    {
        $data = $this->panelUserSubscriptions->get((string) $fd);

        return $data !== false ? $data : false;
    }

    public function isPanelAuthorizedForDevice(int $fd, string $phoneId): bool
    {
        $panelUser = $this->getPanelUser($fd);

        if ($panelUser === false) {
            return false;
        }

        // Admin can access all devices
        if ($panelUser['is_admin'] === 1) {
            return true;
        }

        $userId = $panelUser['user_id'];
        if ($userId === 0) {
            return false;
        }

        // Check device ownership
        \Illuminate\Support\Facades\DB::reconnect();
        $device = \App\Models\Device::where('uuid', $phoneId)->first();

        // Device not yet in DB (may not be registered yet) — allow
        if ($device === null) {
            return true;
        }

        return $device->user_id === $userId;
    }

    public function notifyPanelUsersDeviceOnline(string $phoneId, int $userId, array $phoneInfo): void
    {
        $this->notifyPanelUsersDeviceStatus($phoneId, $userId, $phoneInfo, true);
    }

    public function notifyPanelUsersDeviceOffline(string $phoneId, int $userId): void
    {
        $this->notifyPanelUsersDeviceStatus($phoneId, $userId, [], false);
    }

    public function notifyPanelUsersDeviceStatusUpdate(string $phoneId, array $phoneInfo): void
    {
        $device = \App\Models\Device::where('uuid', $phoneId)->first();
        if ($device === null) {
            return;
        }

        $payload = fn (?int $uid) => [
            'type' => 'deviceUpdate',
            'pid' => $phoneId,
            'phoneInfo' => $phoneInfo,
            'stats' => $this->getDeviceStats($uid),
        ];

        $count = $this->forEachAuthorizedPanelUser($device->user_id, $payload);
        if ($count > 0) {
            WebSocketLog::getLogger()->debug("Notified {$count} panels for device status update {$phoneId}");
        }
    }

    private function notifyPanelUsersDeviceStatus(string $phoneId, int $userId, array $phoneInfo, bool $isOnline): void
    {
        $statusType = $isOnline ? 'online' : 'offline';
        WebSocketLog::getLogger()->debug("Notifying panels: device={$phoneId}, status={$statusType}");

        $payload = fn (?int $uid) => [
            'type' => $isOnline ? 'deviceOnline' : 'deviceOffline',
            'pid' => $phoneId,
            'phoneInfo' => $isOnline ? $phoneInfo : null,
            'stats' => $this->getDeviceStats($uid),
        ];

        $count = $this->forEachAuthorizedPanelUser($userId, $payload);
        WebSocketLog::getLogger()->debug("Notified {$count} panels for device {$phoneId} {$statusType}");
    }

    private function forEachAuthorizedPanelUser(int $deviceUserId, callable $buildPayload): int
    {
        $userEmail = $this->getUserEmail($deviceUserId);
        $count = 0;

        foreach ($this->panelUserSubscriptions as $fd => $subscription) {
            $isAdmin = $subscription['is_admin'] === 1;
            $emailMatches = $subscription['email_encrypted'] === $userEmail;

            if ($isAdmin || $emailMatches) {
                $this->send((int) $fd, $buildPayload($isAdmin ? null : $deviceUserId));
                $count++;
            }
        }

        return $count;
    }

    /**
     * 获取设备统计数据
     */
    public function getDeviceStats(?int $userId): array
    {
        $query = \App\Models\Device::where('is_removed', false);

        if ($userId !== null) {
            $query->where('user_id', $userId);
        }

        $total = (clone $query)->count();
        $online = (clone $query)->where('is_online', true)->count();

        return [
            'total' => $total,
            'online' => $online,
            'offline' => $total - $online,
        ];
    }

    /**
     * 获取用户的明文 email（返回资源归属用户的 email）
     */
    private function getUserEmail(int $userId): ?string
    {
        \Illuminate\Support\Facades\DB::reconnect();
        $user = \App\Models\User::find($userId);

        if ($user === null) {
            return null;
        }

        // 始终返回资源归属用户（父账号）的 email，确保与面板注册的 email 一致
        $owner = $user->getResourceOwner();

        return $owner->email;
    }

    /**
     * 根据面板传入的 email 解析资源归属用户。
     *
     * 子账号 → 返回父账号的 id/email；主账号 → 返回自身的 id/email。
     * Admin 或未找到用户 → 返回 null（表示管理员，查看全部设备）。
     */
    public function resolveResourceOwnerByEmail(string $email): ?array
    {
        // Admin 判断已在调用方完成，此处仅处理普通用户
        \Illuminate\Support\Facades\DB::reconnect();

        $user = \App\Models\User::where('email', $email)->first();
        if ($user === null) {
            return null;
        }

        $owner = $user->getResourceOwner();

        return [
            'id' => $owner->id,
            'email' => $owner->email,
        ];
    }

    public function getDeviceListForUser(?int $userId): array
    {
        \Illuminate\Support\Facades\DB::reconnect();

        $query = \App\Models\Device::where('is_removed', false)
            ->with('user:id,username,email');

        if ($userId !== null) {
            $query->where('user_id', $userId);
        }

        return $query->orderByDesc('is_online')
            ->orderByDesc('last_seen_at')
            ->get()
            ->map(fn ($device) => $this->formatDeviceForList($device))
            ->toArray();
    }

    private function formatDeviceForList(\App\Models\Device $device): array
    {
        // 获取 Redis 中的实时状态（如果有）
        $realtimeStatus = $this->getDeviceStatus($device->uuid);

        return [
            'id' => $device->id,
            'uuid' => $device->uuid,
            'name' => $device->name,
            'remark' => $device->remark,
            'model' => $device->model,
            'android_version' => $device->android_version,
            'country' => $device->country,
            'ip_address' => $device->ip_address,
            'ip_location' => $device->ip_location,
            'network_type' => $realtimeStatus['network'] ?? null,
            'battery_level' => $device->battery_level,
            'battery_is_charging' => BatteryParser::parseCharging($realtimeStatus['battery_charge'] ?? ''),
            'is_online' => $device->is_online,
            'has_accessibility' => $device->has_accessibility,
            'last_seen_at' => $device->last_seen_at?->toIso8601String(),
            'installed_at' => $device->installed_at?->toIso8601String(),
            'user' => $device->user ? [
                'id' => $device->user->id,
                'username' => $device->user->username,
                'email' => $device->user->email,
            ] : null,
            'wallpap' => $realtimeStatus['wallpap'] ?? null,
            'screen_status' => $realtimeStatus['activz'] ?? null,
        ];
    }
}
