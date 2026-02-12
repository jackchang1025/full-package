<?php

declare(strict_types=1);

namespace App\WebSocket\Handlers;

use App\WebSocket\ConnectionManager;
use App\WebSocket\Messages\WebSocketMessage;
use App\WebSocket\Services\DeviceListService;
use App\WebSocket\Services\PanelAuthService;
use App\WebSocket\Services\PanelNotificationService;
use App\WebSocket\WebSocketLog;

final class SubscribeHandler
{
    public function __construct(
        private readonly ConnectionManager $connectionManager,
        private readonly PanelAuthService $panelAuthService,
        private readonly DeviceListService $deviceListService,
        private readonly PanelNotificationService $panelNotificationService,
        private readonly CheckPhoneHandler $checkPhoneHandler,
    ) {}

    public function handle(int $fd, WebSocketMessage $message): void
    {
        $subc = $message->subc() ?? 'subscribe';

        if ($subc === 'checkphone') {
            $this->checkPhoneHandler->handle($fd, $message);

            return;
        }

        $token = $message->token();

        if (empty($token)) {
            WebSocketLog::getLogger()->warning("Subscribe: missing token, fd={$fd}");
            $this->connectionManager->send($fd, [
                'type' => 'subscribe',
                'success' => false,
                'error' => 'Token is required',
            ]);

            return;
        }

        $authResult = $this->panelAuthService->authenticate($token);

        if ($authResult === null) {
            WebSocketLog::getLogger()->warning("Subscribe: invalid token, fd={$fd}");
            $this->connectionManager->send($fd, [
                'type' => 'subscribe',
                'success' => false,
                'error' => 'Invalid or expired token',
            ]);

            return;
        }

        WebSocketLog::getLogger()->info("Subscribe: fd={$fd}, userId={$authResult->userId}, guard={$authResult->guard}, isAdmin=".($authResult->isAdmin ? 'true' : 'false'));

        $this->connectionManager->registerPanelUser($fd, $authResult->ownerEmail, $authResult->isAdmin, $authResult->ownerId);

        // Get device list and stats
        $queryUserId = $authResult->isAdmin ? null : $authResult->ownerId;
        $devices = $this->deviceListService->getDeviceListForUser($queryUserId);
        $stats = $this->panelNotificationService->getDeviceStats($queryUserId);

        WebSocketLog::getLogger()->info("Subscribe: returning {$stats['total']} devices for fd={$fd}");

        $this->connectionManager->send($fd, [
            'type' => 'subscribe',
            'success' => true,
            'isAdmin' => $authResult->isAdmin,
            'devices' => $devices,
            'stats' => $stats,
        ]);
    }
}
