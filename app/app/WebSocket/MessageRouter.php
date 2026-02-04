<?php

declare(strict_types=1);

namespace App\WebSocket;

use App\WebSocket\Config\WebSocketConfig;
use App\WebSocket\Handlers\DeviceHandler;
use App\WebSocket\Handlers\PanelHandler;
use App\WebSocket\Handlers\PanelSendHandler;
use App\WebSocket\Handlers\SubscribeHandler;
use App\WebSocket\Services\HeartbeatService;

final class MessageRouter
{
    public function __construct(
        private readonly ConnectionManager $connectionManager,
        private readonly HeartbeatService $heartbeatService,
        private readonly DeviceHandler $deviceHandler,
        private readonly PanelHandler $panelHandler,
        private readonly PanelSendHandler $panelSendHandler,
        private readonly SubscribeHandler $subscribeHandler
    ) {}

    public function route(int $fd, string $rawData): void
    {
        $data = $this->parseMessage($rawData);

        if ($data === null) {
            WebSocketLog::getLogger()->warning("Invalid JSON message: fd={$fd}");
            return;
        }

        $itype = $data['itype'] ?? null;
        $subc = $data['subc'] ?? null;

        // 处理面板订阅请求（subscribe 和 checkphone 都指向订阅处理器，保持兼容）
        if ($subc === 'subscribe' || $subc === 'checkphone') {
            $this->subscribeHandler->handle($fd, $data);
            return;
        }

        // 处理面板心跳 - 没有 itype 的 ping 消息来自前端面板
        if ($subc === 'ping' && $itype === null) {
            $this->connectionManager->send($fd, ['type' => 'pong', 'timestamp' => time()]);
            return;
        }

        // Test-only command to reset server state
        if ($subc === '__test_reset' && app()->environment('local', 'testing')) {
            $this->connectionManager->resetAllTables();
            $this->connectionManager->send($fd, ['type' => 'test_reset', 'success' => true]);
            return;
        }

        $clientTypes = WebSocketConfig::clientTypes();

        match ($itype) {
            $clientTypes['device'] => $this->deviceHandler->handle($fd, $data),
            $clientTypes['panel'] => $this->panelHandler->handle($fd, $data),
            $clientTypes['panel_send'] => $this->panelSendHandler->handle($fd, $data),
            default => $this->handleUnknownType($fd, $itype),
        };
    }

    private function parseMessage(string $rawData): ?array
    {
        try {
            $data = json_decode($rawData, true, 512, JSON_THROW_ON_ERROR);
            return is_array($data) ? $data : null;
        } catch (\JsonException) {
            return null;
        }
    }

    private function handleUnknownType(int $fd, ?string $itype): void
    {
        WebSocketLog::getLogger()->warning("Unknown message type: fd={$fd}, itype={$itype}");
    }
}
