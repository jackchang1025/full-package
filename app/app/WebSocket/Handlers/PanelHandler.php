<?php

declare(strict_types=1);

namespace App\WebSocket\Handlers;

use App\WebSocket\ConnectionManager;
use App\WebSocket\Handlers\Concerns\AuthorizesDeviceAccess;
use App\WebSocket\Messages\WebSocketMessage;
use App\WebSocket\Services\DeviceStatusService;
use App\WebSocket\Services\LastPingFormatter;
use App\WebSocket\WebSocketLog;

final class PanelHandler
{
    use AuthorizesDeviceAccess;

    public function __construct(
        private readonly ConnectionManager $connectionManager,
        private readonly DeviceStatusService $deviceStatusService
    ) {}

    public function handle(int $fd, WebSocketMessage $message): void
    {
        $phoneId = $this->authorizeDeviceAccess($fd, $message, $this->connectionManager);
        if ($phoneId === null) {
            return;
        }

        $subc = $message->subc();

        match ($subc) {
            'join' => $this->handleJoin($fd, $phoneId),
            'out' => $this->handleOut($phoneId),
            'ping' => $this->handlePing($fd, $phoneId),
            'disag' => $this->handleDisag($phoneId),
            default => $this->forwardAsCommand($phoneId, $message),
        };
    }

    private function handleJoin(int $fd, string $phoneId): void
    {
        $this->connectionManager->registerPanel($fd, $phoneId);

        WebSocketLog::getLogger()->info("Panel join: {$phoneId}");

        $this->connectionManager->send($fd, $this->buildStatusBatchPayload($phoneId));
    }

    private function handleOut(string $phoneId): void
    {
        $this->connectionManager->sendToDevice($phoneId, [
            'command' => 'PANEL_DISCONNECT',
            'params' => [],
        ]);
    }

    private function handlePing(int $fd, string $phoneId): void
    {
        $this->connectionManager->send($fd, $this->buildStatusBatchPayload($phoneId));
    }

    private function handleDisag(string $phoneId): void
    {
        $fd = $this->connectionManager->getDeviceFd($phoneId);
        if ($fd !== null) {
            $this->connectionManager->getServer()->close($fd);
        }
    }

    private function forwardAsCommand(string $phoneId, WebSocketMessage $message): void
    {
        $subc = $message->subc() ?? 'unknown';

        WebSocketLog::getLogger()->info("Panel command: {$subc}", ['phone_id' => $phoneId]);

        $raw = $message->toArray();
        unset($raw['itype'], $raw['pid']);

        $this->connectionManager->sendToDevice($phoneId, [
            'command' => $subc,
            'params' => $raw,
        ]);
    }

    private function buildStatusBatchPayload(string $phoneId): array
    {
        $phoneInfo = $this->deviceStatusService->formatForPanel($phoneId);
        $isOnline = $this->connectionManager->isDeviceOnline($phoneId);
        $lastPing = LastPingFormatter::format($phoneInfo['lastPing'] ?? null);

        return [
            'type' => 'statusBatch',
            'pid' => $phoneId,
            'serverToPhone' => $isOnline ? 'OPEN' : 'CLOSED',
            'lastPing' => $lastPing,
            'phoneInfo' => $phoneInfo,
        ];
    }
}
