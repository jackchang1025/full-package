<?php

declare(strict_types=1);

namespace App\WebSocket\Handlers;

use App\WebSocket\ConnectionManager;
use App\WebSocket\Handlers\Concerns\AuthorizesDeviceAccess;
use App\WebSocket\Messages\WebSocketMessage;
use App\WebSocket\WebSocketLog;

final class PanelSendHandler
{
    use AuthorizesDeviceAccess;

    private ConnectionManager $connectionManager;

    public function __construct(ConnectionManager $connectionManager)
    {
        $this->connectionManager = $connectionManager;
    }

    public function handle(int $fd, WebSocketMessage $message): void
    {
        $phoneId = $this->authorizeDeviceAccess($fd, $message, $this->connectionManager);
        if ($phoneId === null) {
            return;
        }

        $subc = $message->subc() ?? 'unknown';

        WebSocketLog::getLogger()->info("PanelSend: {$subc}", ['phone_id' => $phoneId]);

        $raw = $message->toArray();
        unset($raw['itype'], $raw['pid']);
        $raw['command'] = $subc;
        $raw['params'] = $raw;

        $this->connectionManager->sendToDevice($phoneId, [
            'command' => $subc,
            'params' => $raw,
        ]);
    }
}
