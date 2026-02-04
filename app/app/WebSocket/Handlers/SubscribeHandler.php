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

        // 注册面板用户，用于接收设备上线/下线通知
        $this->connectionManager->registerPanelUser($fd, $email, $isAdmin);

        // 获取用户 ID（管理员看全部，普通用户看自己的）
        $userId = $this->resolveUserId($email, $isAdmin);

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

    private function resolveUserId(string $email, bool $isAdmin): ?int
    {
        if ($isAdmin) {
            return null; // 管理员查看全部设备
        }

        $user = \App\Models\User::where('email', $email)->first();
        return $user?->id;
    }
}
