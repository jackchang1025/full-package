<?php

declare(strict_types=1);

namespace App\WebSocket\Handlers;

use App\WebSocket\ConnectionManager;
use Illuminate\Support\Facades\Log;

/**
 * 处理前端面板的订阅请求
 * 只负责注册面板用户，不返回设备列表（设备列表由 HTTP API 提供）
 */
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
            Log::channel('websocket')->warning("Subscribe: missing email, fd={$fd}");
            $this->connectionManager->send($fd, [
                'type' => 'subscribe',
                'success' => false,
                'error' => 'Email is required',
            ]);
            return;
        }

        \Illuminate\Support\Facades\DB::reconnect();

        // 查询用户判断是否为管理员
        $user = \App\Models\User::where('email', $email)->first();
        $isAdmin = $user !== null && $user->isAdmin();

        Log::channel('websocket')->info("Subscribe: fd={$fd}, email={$email}, isAdmin=" . ($isAdmin ? 'true' : 'false'));

        // 注册面板用户，用于接收设备上线/下线通知
        $this->connectionManager->registerPanelUser($fd, $email, $isAdmin);

        // 返回订阅成功响应
        $this->connectionManager->send($fd, [
            'type' => 'subscribe',
            'success' => true,
            'isAdmin' => $isAdmin,
        ]);
    }
}
