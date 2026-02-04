<?php

declare(strict_types=1);

use Tests\Feature\WebSocket\WebSocketFunctionalTestTrait;
use Tests\Support\MockDevice;
use Tests\Support\MockPanel;

uses(WebSocketFunctionalTestTrait::class);

beforeEach(function () {
    $this->setUpWebSocketFunctional();
    $this->skipIfWebSocketServerUnavailable();
});

afterEach(function () {
    $this->tearDownWebSocketFunctional();
});

describe('WebSocket Panel 设备控制功能测试', function () {
    it('Panel join 设备后收到 statusBatch 响应', function () {
        $deviceId = 'control-join-' . uniqid();
        $host = $this->getTestServerHost();
        $port = $this->getTestServerPort();
        $userEmail = $this->userA->email;

        $response = $this->runWebSocketInCoroutine(function () use ($host, $port, $userEmail, $deviceId) {
            $panel = new MockPanel($userEmail, ['host' => $host, 'port' => $port]);
            $device = new MockDevice($deviceId, [
                'host' => $host,
                'port' => $port,
                'user_email' => $userEmail,
            ]);

            if (! $panel->connect() || ! $device->connect()) {
                return null;
            }

            $device->sendPing();
            usleep(200000);

            $panel->joinDevice($deviceId);
            $msg = $panel->waitForMessage('statusBatch', 5.0);

            $device->disconnect();
            $panel->disconnect();

            return $msg;
        });

        expect($response)->not->toBeNull()
            ->and($response['type'])->toBe('statusBatch')
            ->and($response['pid'])->toBe($deviceId)
            ->and($response)->toHaveKey('phoneInfo');
    });

    it('Panel ping 设备收到 statusBatch', function () {
        $deviceId = 'control-ping-' . uniqid();
        $host = $this->getTestServerHost();
        $port = $this->getTestServerPort();
        $userEmail = $this->userA->email;

        $response = $this->runWebSocketInCoroutine(function () use ($host, $port, $userEmail, $deviceId) {
            $panel = new MockPanel($userEmail, ['host' => $host, 'port' => $port]);
            $device = new MockDevice($deviceId, [
                'host' => $host,
                'port' => $port,
                'user_email' => $userEmail,
            ]);

            if (! $panel->connect() || ! $device->connect()) {
                return null;
            }

            $device->sendPing();
            usleep(200000);

            $panel->joinDevice($deviceId);
            $panel->waitForMessage('statusBatch', 3.0);

            $panel->pingDevice($deviceId);
            $msg = $panel->waitForMessage('statusBatch', 3.0);

            $device->disconnect();
            $panel->disconnect();

            return $msg;
        });

        expect($response)->not->toBeNull()
            ->and($response['type'])->toBe('statusBatch')
            ->and($response['pid'])->toBe($deviceId);
    });

    it('Panel 请求 SMS 后设备发送数据能收到', function () {
        $deviceId = 'control-sms-' . uniqid();
        $host = $this->getTestServerHost();
        $port = $this->getTestServerPort();
        $userEmail = $this->userA->email;

        $smsReceived = $this->runWebSocketInCoroutine(function () use ($host, $port, $userEmail, $deviceId) {
            $panel = new MockPanel($userEmail, ['host' => $host, 'port' => $port]);
            $device = new MockDevice($deviceId, [
                'host' => $host,
                'port' => $port,
                'user_email' => $userEmail,
            ]);

            if (! $panel->connect() || ! $device->connect()) {
                return null;
            }

            $device->sendPing();
            usleep(200000);

            $panel->joinDevice($deviceId);
            $panel->waitForMessage('statusBatch', 3.0);
            usleep(100000);

            $panel->requestSms();
            usleep(200000);
            $device->sendSmsData();
            $msg = $panel->waitForMessage('sms', 5.0);

            $device->disconnect();
            $panel->disconnect();

            return $msg;
        });

        expect($smsReceived)->not->toBeNull()
            ->and($smsReceived['type'])->toBe('sms')
            ->and($smsReceived)->toHaveKey('data');
    });
});
