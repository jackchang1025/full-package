<?php

declare(strict_types=1);

namespace App\WebSocket\Services;

use App\WebSocket\Config\WebSocketConfig;
use App\WebSocket\ConnectionManager;
use App\WebSocket\WebSocketLog;

final class HeartbeatService
{
    public function __construct(
        private readonly ConnectionManager $connectionManager
    ) {}

    private function timeout(): int
    {
        return WebSocketConfig::heartbeatTimeout();
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

            if ($elapsed > $this->timeout()) {
                $this->handleTimeout((string) $phoneId, $elapsed);
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

        if (! $server->isEstablished($fd)) {
            $this->connectionManager->handleDisconnect($fd);

            return;
        }

        WebSocketLog::getLogger()->info("Device timeout ({$elapsed}s): {$phoneId}");
        $server->close($fd);
    }

    public function getTimeout(): int
    {
        return $this->timeout();
    }
}
