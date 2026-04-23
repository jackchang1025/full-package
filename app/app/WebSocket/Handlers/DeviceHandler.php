<?php

declare(strict_types=1);

namespace App\WebSocket\Handlers;

use App\WebSocket\ConnectionManager;
use App\WebSocket\Enums\DeviceForwardLogLevel;
use App\WebSocket\Messages\WebSocketMessage;
use App\WebSocket\Services\DeviceStatusService;
use App\WebSocket\Services\HeartbeatService;
use App\WebSocket\Services\LastPingFormatter;
use App\WebSocket\Services\PanelNotificationService;
use App\WebSocket\WebSocketLog;

final class DeviceHandler
{
    public function __construct(
        private readonly ConnectionManager $connectionManager,
        private readonly HeartbeatService $heartbeatService,
        private readonly DeviceStatusService $deviceStatusService,
        private readonly PanelNotificationService $panelNotificationService,
    ) {}

    public function handle(int $fd, WebSocketMessage $message): void
    {
        $phoneId = $message->pid();

        if ($phoneId === null) {
            WebSocketLog::getLogger()->warning("Device message missing pid: fd={$fd}");

            return;
        }

        $existingPhoneId = $this->connectionManager->getPhoneId($fd);
        if ($existingPhoneId === null) {
            $this->connectionManager->registerDevice($fd, $phoneId);
        }

        $subc = $message->subc();

        if ($subc === 'ping') {
            $this->handlePing($phoneId, $message);

            return;
        }

        $this->forwardToPanel($phoneId, $message);
    }

    private function handlePing(string $phoneId, WebSocketMessage $message): void
    {
        try {
            $this->heartbeatService->recordPing($phoneId);

            $this->deviceStatusService->updateFromPing($phoneId, $message->toArray());

            $phoneInfo = $this->deviceStatusService->formatForPanel($phoneId);
            $lastPing = LastPingFormatter::format($phoneInfo['lastPing'] ?? null);

            $this->connectionManager->sendToPanels($phoneId, [
                'type' => 'statusBatch',
                'pid' => $phoneId,
                'serverToPhone' => 'OPEN',
                'lastPing' => $lastPing,
                'phoneInfo' => $phoneInfo,
            ]);
            $this->panelNotificationService->notifyDeviceStatusUpdate($phoneId, $phoneInfo);
        } catch (\Throwable $e) {
            WebSocketLog::getLogger()->error('Ping handling failed, connection preserved', [
                'phone_id' => $phoneId,
                'error' => $e->getMessage(),
            ]);
        }
    }

    private function forwardToPanel(string $phoneId, WebSocketMessage $message): void
    {
        $type = $message->getString('type', $message->subc() ?? 'unknown');

        // Persist operation_log messages to database
        if ($type === 'operation_log') {
            $this->persistOperationLog($phoneId, $message);
        }

        WebSocketLog::getLogger()->log(
            DeviceForwardLogLevel::forSubc($type)->toPsrLevel(),
            "Device forwarded: {$type}",
            ['phone_id' => $phoneId]
        );

        $raw = $message->toArray();
        unset($raw['itype'], $raw['owner_token'], $raw['sessionId'], $raw['ws_connected']);
        $raw['type'] = $type;
        $raw['pid'] = $phoneId;

        $this->connectionManager->sendToPanels($phoneId, $raw);
    }

    private function persistOperationLog(string $phoneId, WebSocketMessage $message): void
    {
        try {
            $data = $message->get('data');
            if (! is_array($data)) {
                $data = $message->toArray();
            }

            $device = \App\Models\Device::where('device_uid', $phoneId)
                ->orWhere('uuid', $phoneId)
                ->first();

            if (! $device) {
                return;
            }

            $logType = $data['logType'] ?? $data['log_type'] ?? 'ACTZ';
            $content = $data['content'] ?? json_encode($data);
            $timestamp = $data['timestamp'] ?? null;

            \App\Models\DeviceLog::create([
                'device_id' => $device->id,
                'user_id' => $device->user_id,
                'log_type' => $logType,
                'content' => $content,
                'device_timestamp' => $timestamp
                    ? \Carbon\Carbon::createFromTimestampMs((int) $timestamp)
                    : now(),
                'device_uid' => $phoneId,
            ]);
        } catch (\Throwable $e) {
            WebSocketLog::getLogger()->warning("Failed to persist operation_log: {$e->getMessage()}");
        }
    }
}
