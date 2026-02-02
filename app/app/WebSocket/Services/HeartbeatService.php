<?php

declare(strict_types=1);

namespace App\WebSocket\Services;

use App\WebSocket\ConnectionManager;
use Illuminate\Support\Facades\Log;

class HeartbeatService
{
    private ConnectionManager $connectionManager;
    private int $timeout;

    public function __construct(ConnectionManager $connectionManager)
    {
        $this->connectionManager = $connectionManager;
        $this->timeout = config('websocket.heartbeat.timeout', 75);
    }

    public function recordPing(string $phoneId): void
    {
        $this->connectionManager->updateDeviceStatus($phoneId, [
            'last_ping' => time(),
            'is_online' => true,
        ]);
    }

    public function checkAll(): void
    {
        $now = time();
        $devices = $this->connectionManager->getAllOnlineDevices();

        foreach ($devices as $phoneId => $status) {
            $lastPing = (int) ($status['last_ping'] ?? 0);
            $elapsed = $now - $lastPing;

            if ($elapsed > $this->timeout) {
                $this->handleTimeout($phoneId, $elapsed);
            }
        }
    }

    private function handleTimeout(string $phoneId, int $elapsed): void
    {
        $fd = $this->connectionManager->getDeviceFd($phoneId);

        if ($fd === null) {
            return;
        }

        $server = $this->connectionManager->getServer();

        if (!$server->isEstablished($fd)) {
            $this->connectionManager->handleDisconnect($fd);
            return;
        }

        Log::channel('websocket')->info("Device timeout ({$elapsed}s): {$phoneId}");
        $server->close($fd);
    }

    public function getTimeout(): int
    {
        return $this->timeout;
    }
}
