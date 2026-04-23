<?php

declare(strict_types=1);

namespace App\WebSocket;

use App\WebSocket\Config\WebSocketConfig;
use App\WebSocket\Services\DatabaseReconnector;
use App\WebSocket\Services\PanelNotificationService;
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

    private DatabaseReconnector $databaseReconnector;

    private ?PanelNotificationService $panelNotificationService = null;

    /**
     * @param  array<string, Table>  $tables  预先创建的共享内存表，必须包含:
     *                                        - fdToPhoneId: fd -> phoneId/clientType 映射
     *                                        - phoneIdToFd: phoneId -> fd 映射
     *                                        - panelSubscriptions: panel fd -> phoneId 订阅
     *                                        - panelUserSubscriptions: panel fd -> email/isAdmin
     */
    public function __construct(SwooleServer $server, array $tables, DatabaseReconnector $databaseReconnector)
    {
        $this->server = $server;
        $this->databaseReconnector = $databaseReconnector;

        // 使用外部传入的共享表（在 server->start() 之前创建，所有 Worker 共享）
        $this->fdToPhoneId = $tables['fdToPhoneId'];
        $this->phoneIdToFd = $tables['phoneIdToFd'];
        $this->panelSubscriptions = $tables['panelSubscriptions'];
        $this->panelUserSubscriptions = $tables['panelUserSubscriptions'];
    }

    public function setPanelNotificationService(PanelNotificationService $service): void
    {
        $this->panelNotificationService = $service;
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

        $this->updateDeviceStatus($phoneId, ['is_online' => true, 'last_seen_at' => time(), 'last_ping' => time()]);

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
            $this->handleDeviceDisconnect($fd, $phoneId);
        } else {
            $this->handlePanelDisconnect($fd);
        }

        WebSocketLog::getLogger()->info("Disconnected: fd={$fd}, type={$clientType}, phoneId={$phoneId}");
    }

    private function handleDeviceDisconnect(int $fd, string $phoneId): void
    {
        $currentFd = $this->phoneIdToFd->get($phoneId);
        if ($currentFd !== false && $currentFd['fd'] === $fd) {
            $this->phoneIdToFd->del($phoneId);
            $this->updateDeviceStatus($phoneId, ['is_online' => false]);
            $this->syncOfflineToDatabaseAndNotify($phoneId);
            $this->notifyPanelsDeviceOffline($phoneId);
        }
    }

    private function handlePanelDisconnect(int $fd): void
    {
        $this->panelSubscriptions->del((string) $fd);
        $this->unregisterPanelUser($fd);
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

        $envelope = [
            'type' => 'command',
            'data' => $data,
        ];

        WebSocketLog::getLogger()->debug('sendToDevice', [
            'phone_id' => $phoneId,
            'fd' => $fd,
            'data' => $data,
        ]);

        return $this->send($fd, $envelope);
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

        $this->panelNotificationService?->notifyDeviceOffline($phoneId, $device->user_id);
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

    public function getPanelUserSubscriptions(): Table
    {
        return $this->panelUserSubscriptions;
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
        $this->databaseReconnector->reconnect();
        $device = \App\Models\Device::where('uuid', $phoneId)->first();

        // Device not yet in DB (may not be registered yet) — allow
        if ($device === null) {
            return true;
        }

        return $device->user_id === $userId;
    }
}
