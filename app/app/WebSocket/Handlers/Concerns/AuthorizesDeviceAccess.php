<?php

declare(strict_types=1);

namespace App\WebSocket\Handlers\Concerns;

use App\WebSocket\ConnectionManager;
use App\WebSocket\Messages\WebSocketMessage;
use App\WebSocket\WebSocketLog;

trait AuthorizesDeviceAccess
{
    private function authorizeDeviceAccess(int $fd, WebSocketMessage $message, ConnectionManager $connectionManager): ?string
    {
        $phoneId = $message->pid();

        if ($phoneId === null) {
            WebSocketLog::getLogger()->warning(static::class." message missing pid: fd={$fd}");

            return null;
        }

        if (! $connectionManager->isPanelAuthorizedForDevice($fd, $phoneId)) {
            WebSocketLog::getLogger()->warning(static::class." unauthorized for device: fd={$fd}, pid={$phoneId}");
            $connectionManager->send($fd, [
                'type' => 'error',
                'error' => 'Not authorized for this device',
                'pid' => $phoneId,
            ]);

            return null;
        }

        return $phoneId;
    }
}
