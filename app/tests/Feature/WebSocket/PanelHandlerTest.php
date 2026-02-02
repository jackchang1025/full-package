<?php

declare(strict_types=1);

use App\WebSocket\ConnectionManager;
use App\WebSocket\Handlers\PanelHandler;
use Swoole\WebSocket\Server as SwooleServer;

beforeEach(function () {
    config(['websocket.logging.enabled' => false]);

    $this->mockServer = Mockery::mock(SwooleServer::class);
    $this->mockServer->shouldReceive('isEstablished')->andReturn(true)->byDefault();
    $this->mockServer->shouldReceive('push')->andReturn(true)->byDefault();
    $this->mockServer->shouldReceive('close')->andReturn(true)->byDefault();

    $this->connectionManager = Mockery::mock(ConnectionManager::class);
    $this->connectionManager->shouldReceive('getServer')->andReturn($this->mockServer)->byDefault();
    $this->connectionManager->shouldReceive('getDeviceStatus')->andReturn([])->byDefault();
    $this->connectionManager->shouldReceive('isDeviceOnline')->andReturn(true)->byDefault();

    $this->handler = new PanelHandler($this->connectionManager);
});

afterEach(function () {
    Mockery::close();
});

describe('PanelHandler', function () {
    describe('handle()', function () {
        it('ignores messages without pid', function () {
            $this->connectionManager->shouldNotReceive('registerPanel');

            $this->handler->handle(1, ['itype' => 'slr_panel', 'subc' => 'join']);

            expect(true)->toBeTrue();
        });
    });

    describe('join command', function () {
        it('registers panel subscription and sends join response', function () {
            $phoneId = 'test-device-123';
            $fd = 1;

            $this->connectionManager->shouldReceive('registerPanel')->with($fd, $phoneId)->once();
            $this->connectionManager->shouldReceive('isDeviceOnline')->with($phoneId)->andReturn(true);
            $this->connectionManager->shouldReceive('send')
                ->withArgs(function ($targetFd, $data) use ($fd, $phoneId) {
                    return $targetFd === $fd
                        && $data['type'] === 'joinResponse'
                        && $data['pid'] === $phoneId
                        && $data['is_online'] === true;
                })
                ->once();

            $this->handler->handle($fd, [
                'itype' => 'slr_panel',
                'subc' => 'join',
                'pid' => $phoneId,
                'usercheck' => 'test-hash',
            ]);
        });

        it('returns is_online false when device is offline', function () {
            $phoneId = 'test-device-123';
            $fd = 1;

            $this->connectionManager->shouldReceive('registerPanel')->once();
            $this->connectionManager->shouldReceive('isDeviceOnline')->with($phoneId)->andReturn(false);
            $this->connectionManager->shouldReceive('send')
                ->withArgs(function ($targetFd, $data) {
                    return $data['is_online'] === false;
                })
                ->once();

            $this->handler->handle($fd, [
                'itype' => 'slr_panel',
                'subc' => 'join',
                'pid' => $phoneId,
                'usercheck' => 'test-hash',
            ]);
        });
    });

    describe('out command', function () {
        it('sends out command to device', function () {
            $phoneId = 'test-device-123';

            $this->connectionManager->shouldReceive('sendToDevice')
                ->withArgs(function ($pid, $data) use ($phoneId) {
                    return $pid === $phoneId
                        && $data['type'] === 'screencomd'
                        && $data['subc'] === 'out';
                })
                ->once();

            $this->handler->handle(1, [
                'itype' => 'slr_panel',
                'subc' => 'out',
                'pid' => $phoneId,
            ]);
        });
    });

    describe('ping command', function () {
        it('returns status batch with device info', function () {
            $phoneId = 'test-device-123';
            $fd = 1;
            $deviceFd = 10;

            $this->connectionManager->shouldReceive('getDeviceFd')->with($phoneId)->andReturn($deviceFd);
            $this->connectionManager->shouldReceive('getDeviceStatus')->with($phoneId)->andReturn([
                'last_ping' => 1706745600,
                'name' => 'Test Device',
            ]);
            $this->mockServer->shouldReceive('isEstablished')->with($deviceFd)->andReturn(true);
            $this->connectionManager->shouldReceive('send')
                ->withArgs(function ($targetFd, $data) use ($fd, $phoneId) {
                    return $targetFd === $fd
                        && $data['type'] === 'statusBatch'
                        && $data['pid'] === $phoneId
                        && $data['serverToPhone'] === 'OPEN';
                })
                ->once();

            $this->handler->handle($fd, [
                'itype' => 'slr_panel',
                'subc' => 'ping',
                'pid' => $phoneId,
            ]);
        });

        it('returns CLOSED when device connection is not established', function () {
            $phoneId = 'test-device-123';
            $fd = 1;
            $deviceFd = 10;

            $this->connectionManager->shouldReceive('getDeviceFd')->with($phoneId)->andReturn($deviceFd);
            $this->connectionManager->shouldReceive('getDeviceStatus')->andReturn([]);
            $this->mockServer->shouldReceive('isEstablished')->with($deviceFd)->andReturn(false);
            $this->connectionManager->shouldReceive('send')
                ->withArgs(function ($targetFd, $data) {
                    return $data['serverToPhone'] === 'CLOSED';
                })
                ->once();

            $this->handler->handle($fd, [
                'itype' => 'slr_panel',
                'subc' => 'ping',
                'pid' => $phoneId,
            ]);
        });

        it('returns UNKNOWN when device fd is null', function () {
            $phoneId = 'test-device-123';
            $fd = 1;

            $this->connectionManager->shouldReceive('getDeviceFd')->with($phoneId)->andReturn(null);
            $this->connectionManager->shouldReceive('getDeviceStatus')->andReturn([]);
            $this->connectionManager->shouldReceive('send')
                ->withArgs(function ($targetFd, $data) {
                    return $data['serverToPhone'] === 'UNKNOWN';
                })
                ->once();

            $this->handler->handle($fd, [
                'itype' => 'slr_panel',
                'subc' => 'ping',
                'pid' => $phoneId,
            ]);
        });
    });

    describe('disag command', function () {
        it('closes device connection', function () {
            $phoneId = 'test-device-123';
            $deviceFd = 10;

            $this->connectionManager->shouldReceive('getDeviceFd')->with($phoneId)->andReturn($deviceFd);
            $this->mockServer->shouldReceive('close')->with($deviceFd)->once();

            $this->handler->handle(1, [
                'itype' => 'slr_panel',
                'subc' => 'disag',
                'pid' => $phoneId,
            ]);
        });

        it('does nothing when device is not connected', function () {
            $phoneId = 'test-device-123';

            $this->connectionManager->shouldReceive('getDeviceFd')->with($phoneId)->andReturn(null);
            $this->mockServer->shouldNotReceive('close');

            $this->handler->handle(1, [
                'itype' => 'slr_panel',
                'subc' => 'disag',
                'pid' => $phoneId,
            ]);
        });
    });

    describe('screen commands', function () {
        it('sends tap command with coordinates', function () {
            $phoneId = 'test-device-123';

            $this->connectionManager->shouldReceive('sendToDevice')
                ->withArgs(function ($pid, $data) use ($phoneId) {
                    return $pid === $phoneId
                        && $data['type'] === 'screen'
                        && $data['subc'] === 'mov'
                        && $data['poi'] === '500,800'
                        && $data['movetype'] === '0';
                })
                ->once();

            $this->handler->handle(1, [
                'itype' => 'slr_panel',
                'subc' => 'screen',
                'pid' => $phoneId,
                'comand' => 'mov',
                'movetype' => '0',
                'poi' => '500,800',
            ]);
        });

        it('sends navigation command', function () {
            $phoneId = 'test-device-123';

            $this->connectionManager->shouldReceive('sendToDevice')
                ->withArgs(function ($pid, $data) {
                    return $data['type'] === 'screen'
                        && $data['subc'] === 'nav'
                        && $data['nav'] === 'ho';
                })
                ->once();

            $this->handler->handle(1, [
                'itype' => 'slr_panel',
                'subc' => 'screen',
                'pid' => $phoneId,
                'comand' => 'nav',
                'navshort' => 'ho',
            ]);
        });

        it('sends volume command', function () {
            $phoneId = 'test-device-123';

            $this->connectionManager->shouldReceive('sendToDevice')
                ->withArgs(function ($pid, $data) {
                    return $data['type'] === 'screen'
                        && $data['subc'] === 'vol'
                        && $data['volstate'] === '1';
                })
                ->once();

            $this->handler->handle(1, [
                'itype' => 'slr_panel',
                'subc' => 'screen',
                'pid' => $phoneId,
                'comand' => 'vol',
                'volstate' => '1',
            ]);
        });

        it('sends lock screen command', function () {
            $phoneId = 'test-device-123';

            $this->connectionManager->shouldReceive('sendToDevice')
                ->withArgs(function ($pid, $data) {
                    return $data['type'] === 'screen'
                        && $data['subc'] === 'L'
                        && $data['lock'] === '1';
                })
                ->once();

            $this->handler->handle(1, [
                'itype' => 'slr_panel',
                'subc' => 'screen',
                'pid' => $phoneId,
                'comand' => 'L',
                'lockit' => '1',
            ]);
        });

        it('sends keyboard command', function () {
            $phoneId = 'test-device-123';

            $this->connectionManager->shouldReceive('sendToDevice')
                ->withArgs(function ($pid, $data) {
                    return $data['type'] === 'screen'
                        && $data['subc'] === 'kb'
                        && $data['kbstate'] === '2';
                })
                ->once();

            $this->handler->handle(1, [
                'itype' => 'slr_panel',
                'subc' => 'screen',
                'pid' => $phoneId,
                'comand' => 'kb',
                'kbstate' => '2',
            ]);
        });

        it('sends paste command', function () {
            $phoneId = 'test-device-123';

            $this->connectionManager->shouldReceive('sendToDevice')
                ->withArgs(function ($pid, $data) {
                    return $data['type'] === 'screen'
                        && $data['subc'] === 'paste'
                        && $data['txt'] === 'Hello World';
                })
                ->once();

            $this->handler->handle(1, [
                'itype' => 'slr_panel',
                'subc' => 'screen',
                'pid' => $phoneId,
                'comand' => 'paste',
                'txt' => 'Hello World',
            ]);
        });

        it('sends block screen command', function () {
            $phoneId = 'test-device-123';

            $this->connectionManager->shouldReceive('sendToDevice')
                ->withArgs(function ($pid, $data) {
                    return $data['type'] === 'screen'
                        && $data['subc'] === 'block'
                        && $data['blockstate'] === '1'
                        && $data['color'] === '#000000';
                })
                ->once();

            $this->handler->handle(1, [
                'itype' => 'slr_panel',
                'subc' => 'screen',
                'pid' => $phoneId,
                'comand' => 'block',
                'bstate' => '1',
                'color' => '#000000',
            ]);
        });

        it('sends snap command', function () {
            $phoneId = 'test-device-123';

            $this->connectionManager->shouldReceive('sendToDevice')
                ->withArgs(function ($pid, $data) {
                    return $data['type'] === 'screen'
                        && $data['subc'] === 'snap'
                        && $data['snaptype'] === '1';
                })
                ->once();

            $this->handler->handle(1, [
                'itype' => 'slr_panel',
                'subc' => 'screen',
                'pid' => $phoneId,
                'comand' => 'snap',
                'stype' => '1',
            ]);
        });
    });

    describe('browser commands', function () {
        it('sends hidden browser command', function () {
            $phoneId = 'test-device-123';

            $this->connectionManager->shouldReceive('sendToDevice')
                ->withArgs(function ($pid, $data) {
                    return $data['type'] === 'brows'
                        && $data['subc'] === 'h'
                        && $data['bcom'] === '1';
                })
                ->once();

            $this->handler->handle(1, [
                'itype' => 'slr_panel',
                'subc' => 'brows',
                'pid' => $phoneId,
                'btype' => 'h',
                'bcom' => '1',
            ]);
        });

        it('sends normal browser command', function () {
            $phoneId = 'test-device-123';

            $this->connectionManager->shouldReceive('sendToDevice')
                ->withArgs(function ($pid, $data) {
                    return $data['type'] === 'brows'
                        && $data['subc'] === 'n'
                        && $data['ltype'] === 'u'
                        && $data['extdata'] === 'https://example.com';
                })
                ->once();

            $this->handler->handle(1, [
                'itype' => 'slr_panel',
                'subc' => 'brows',
                'pid' => $phoneId,
                'btype' => 'n',
                'ltype' => 'u',
                'extdata' => 'https://example.com',
            ]);
        });
    });

    describe('proxy commands', function () {
        it('sends proxy ON command', function () {
            $phoneId = 'test-device-123';

            $this->connectionManager->shouldReceive('sendToDevice')
                ->withArgs(function ($pid, $data) {
                    return $data['type'] === 'proxy'
                        && $data['subc'] === '1';
                })
                ->once();

            $this->handler->handle(1, [
                'itype' => 'slr_panel',
                'subc' => 'proxy',
                'pid' => $phoneId,
                'prxcom' => 'ON',
            ]);
        });

        it('sends proxy OFF command', function () {
            $phoneId = 'test-device-123';

            $this->connectionManager->shouldReceive('sendToDevice')
                ->withArgs(function ($pid, $data) {
                    return $data['type'] === 'proxy'
                        && $data['subc'] === '0';
                })
                ->once();

            $this->handler->handle(1, [
                'itype' => 'slr_panel',
                'subc' => 'proxy',
                'pid' => $phoneId,
                'prxcom' => 'OFF',
            ]);
        });
    });

    describe('broadcast commands', function () {
        it('sends alert broadcast', function () {
            $phoneId = 'test-device-123';

            $this->connectionManager->shouldReceive('sendToDevice')
                ->withArgs(function ($pid, $data) {
                    return $data['type'] === 'bc'
                        && $data['subc'] === 'A'
                        && $data['thetitle'] === 'Alert Title'
                        && $data['themsg'] === 'Alert Message'
                        && $data['theype'] === '0';
                })
                ->once();

            $this->handler->handle(1, [
                'itype' => 'slr_panel',
                'subc' => 'bc',
                'pid' => $phoneId,
                'comand' => 'alert',
                'title' => 'Alert Title',
                'msg' => 'Alert Message',
                'act' => 'nothing',
            ]);
        });

        it('sends notify broadcast with openApp action', function () {
            $phoneId = 'test-device-123';

            $this->connectionManager->shouldReceive('sendToDevice')
                ->withArgs(function ($pid, $data) {
                    return $data['type'] === 'bc'
                        && $data['subc'] === 'N'
                        && $data['theype'] === '1';
                })
                ->once();

            $this->handler->handle(1, [
                'itype' => 'slr_panel',
                'subc' => 'bc',
                'pid' => $phoneId,
                'comand' => 'notify',
                'title' => 'Notify',
                'msg' => 'Message',
                'act' => 'openApp',
            ]);
        });
    });

    describe('other commands', function () {
        it('sends search command', function () {
            $phoneId = 'test-device-123';

            $this->connectionManager->shouldReceive('sendToDevice')
                ->withArgs(function ($pid, $data) {
                    return $data['type'] === 'screencomd'
                        && $data['subc'] === 'srch'
                        && $data['srchfor'] === '*.jpg'
                        && $data['srchin'] === 'G';
                })
                ->once();

            $this->handler->handle(1, [
                'itype' => 'slr_panel',
                'subc' => 'srch',
                'pid' => $phoneId,
                'srchfor' => '*.jpg',
                'srchin' => 'G',
                'targetpath' => '/sdcard',
            ]);
        });

        it('sends chat command', function () {
            $phoneId = 'test-device-123';

            $this->connectionManager->shouldReceive('sendToDevice')
                ->withArgs(function ($pid, $data) {
                    return $data['type'] === 'screencomd'
                        && $data['subc'] === 'chat'
                        && $data['msg'] === 'Hello'
                        && $data['title'] === 'Chat';
                })
                ->once();

            $this->handler->handle(1, [
                'itype' => 'slr_panel',
                'subc' => 'chat',
                'pid' => $phoneId,
                'msg' => 'Hello',
                'title' => 'Chat',
            ]);
        });

        it('forwards unknown commands to device', function () {
            $phoneId = 'test-device-123';

            $this->connectionManager->shouldReceive('sendToDevice')
                ->withArgs(function ($pid, $data) {
                    return $data['type'] === 'screencomd'
                        && $data['subc'] === 'unknown_cmd';
                })
                ->once();

            $this->handler->handle(1, [
                'itype' => 'slr_panel',
                'subc' => 'unknown_cmd',
                'pid' => $phoneId,
                'custom_field' => 'value',
            ]);
        });
    });
});
