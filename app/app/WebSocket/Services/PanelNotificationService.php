<?php

declare(strict_types=1);

namespace App\WebSocket\Services;

use App\Models\Device;
use App\Models\User;
use App\WebSocket\ConnectionManager;
use App\WebSocket\WebSocketLog;

class PanelNotificationService
{
    public function __construct(
        private readonly ConnectionManager $connectionManager,
        private readonly DatabaseReconnector $databaseReconnector,
    ) {}

    public function notifyDeviceOnline(string $phoneId, int $userId, array $phoneInfo): void
    {
        $this->notifyDeviceStatus($phoneId, $userId, $phoneInfo, true);
    }

    public function notifyDeviceOffline(string $phoneId, int $userId): void
    {
        $this->notifyDeviceStatus($phoneId, $userId, [], false);
    }

    public function notifyDeviceStatusUpdate(string $phoneId, array $phoneInfo): void
    {
        $device = Device::where('uuid', $phoneId)->first();
        if ($device === null) {
            return;
        }

        $payload = fn (?int $uid) => $this->buildDeviceUpdatePayload($phoneId, $phoneInfo, $uid);

        $count = $this->forEachAuthorizedPanelUser($device->user_id, $payload);
        if ($count > 0) {
            WebSocketLog::getLogger()->debug("Notified {$count} panels for device status update {$phoneId}");
        }
    }

    /**
     * 获取设备统计数据（与控制页一致：在线数以当前 WebSocket 连接为准）
     */
    public function getDeviceStats(?int $userId): array
    {
        $query = Device::where('is_removed', false);

        if ($userId !== null) {
            $query->where('user_id', $userId);
        }

        $devices = (clone $query)->get();
        $total = $devices->count();
        $online = $devices->filter(fn ($d) => $this->connectionManager->isDeviceOnline($d->uuid))->count();

        return [
            'total' => $total,
            'online' => $online,
            'offline' => $total - $online,
        ];
    }

    private function deviceStatusLabel(bool $isOnline): string
    {
        return $isOnline ? 'online' : 'offline';
    }

    private function deviceStatusEventType(bool $isOnline): string
    {
        return $isOnline ? 'deviceOnline' : 'deviceOffline';
    }

    private function notifyDeviceStatus(string $phoneId, int $userId, array $phoneInfo, bool $isOnline): void
    {
        $statusType = $this->deviceStatusLabel($isOnline);
        WebSocketLog::getLogger()->debug("Notifying panels: device={$phoneId}, status={$statusType}");

        $payload = fn (?int $uid) => $this->buildStatusChangePayload($phoneId, $phoneInfo, $isOnline, $uid);

        $count = $this->forEachAuthorizedPanelUser($userId, $payload);
        WebSocketLog::getLogger()->debug("Notified {$count} panels for device {$phoneId} {$statusType}");
    }

    private function buildDeviceUpdatePayload(string $phoneId, array $phoneInfo, ?int $uid): array
    {
        return [
            'type' => 'deviceUpdate',
            'pid' => $phoneId,
            'phoneInfo' => $phoneInfo,
            'stats' => $this->getDeviceStats($uid),
        ];
    }

    private function buildStatusChangePayload(string $phoneId, array $phoneInfo, bool $isOnline, ?int $uid): array
    {
        return [
            'type' => $this->deviceStatusEventType($isOnline),
            'pid' => $phoneId,
            'phoneInfo' => $isOnline ? $phoneInfo : null,
            'stats' => $this->getDeviceStats($uid),
        ];
    }

    private function forEachAuthorizedPanelUser(int $deviceUserId, callable $buildPayload): int
    {
        $userEmail = $this->getUserEmail($deviceUserId);
        $count = 0;

        foreach ($this->connectionManager->getPanelUserSubscriptions() as $fd => $subscription) {
            $isAdmin = $subscription['is_admin'] === 1;
            $emailMatches = $subscription['email_encrypted'] === $userEmail;

            if ($isAdmin || $emailMatches) {
                $this->connectionManager->send((int) $fd, $buildPayload($isAdmin ? null : $deviceUserId));
                $count++;
            }
        }

        return $count;
    }

    /**
     * 获取用户的明文 email（返回资源归属用户的 email）
     */
    private function getUserEmail(int $userId): ?string
    {
        $this->databaseReconnector->reconnect();
        $user = User::find($userId);

        if ($user === null) {
            return null;
        }

        // 始终返回资源归属用户（父账号）的 email，确保与面板注册的 email 一致
        $owner = $user->getResourceOwner();

        return $owner->email;
    }
}
