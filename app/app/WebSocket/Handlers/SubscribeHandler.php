<?php

declare(strict_types=1);

namespace App\WebSocket\Handlers;

use App\WebSocket\ConnectionManager;
use App\WebSocket\WebSocketLog;

final class SubscribeHandler
{
    private ConnectionManager $connectionManager;

    public function __construct(ConnectionManager $connectionManager)
    {
        $this->connectionManager = $connectionManager;
    }

    public function handle(int $fd, array $data): void
    {
        $email = $data['email'] ?? '';

        if (empty($email)) {
            WebSocketLog::getLogger()->warning("Subscribe: missing email, fd={$fd}");
            $this->connectionManager->send($fd, [
                'type' => 'subscribe',
                'success' => false,
                'error' => 'Email is required',
            ]);
            return;
        }

        \Illuminate\Support\Facades\DB::reconnect();

        // 根据 admins 表判断是否为总管理员
        $isAdmin = \App\Models\Admin::where('email', $email)->exists();

        WebSocketLog::getLogger()->info("Subscribe: fd={$fd}, email={$email}, isAdmin=" . ($isAdmin ? 'true' : 'false'));

        // 解析资源归属用户（子账号 → 父账号，主账号 → 自身）
        $owner = $isAdmin ? null : $this->connectionManager->resolveResourceOwnerByEmail($email);
        $ownerEmail = $owner['email'] ?? $email;
        $ownerId = $owner['id'] ?? null;

        // 注册面板用户时使用归属用户的 email，确保与设备侧 email 一致
        $this->connectionManager->registerPanelUser($fd, $ownerEmail, $isAdmin);

        // 获取用户 ID（管理员看全部，普通用户看归属用户的设备）
        $userId = $isAdmin ? null : $ownerId;

        // 获取设备列表和统计数据
        // [core-dry] 复用 ConnectionManager 中的查询逻辑
        $devices = $this->connectionManager->getDeviceListForUser($userId);
        $stats = $this->connectionManager->getDeviceStats($userId);

        WebSocketLog::getLogger()->info("Subscribe: returning {$stats['total']} devices for fd={$fd}");

        // 返回订阅成功响应（包含完整设备列表和统计数据）
        $this->connectionManager->send($fd, [
            'type' => 'subscribe',
            'success' => true,
            'isAdmin' => $isAdmin,
            'devices' => $devices,
            'stats' => $stats,
        ]);
    }
}
