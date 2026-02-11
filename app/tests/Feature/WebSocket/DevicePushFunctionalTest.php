<?php

declare(strict_types=1);

use Tests\Feature\WebSocket\WebSocketFunctionalTestTrait;
use Tests\Support\MockDevice;

uses(WebSocketFunctionalTestTrait::class);

beforeEach(function () {
    $this->setUpWebSocketFunctional();
    $this->skipIfWebSocketServerUnavailable();
});

afterEach(function () {
    $this->tearDownWebSocketFunctional();
});

describe('WebSocket 设备推送功能测试', function () {
    it('设备上线时已订阅的 Panel 收到 deviceOnline 推送', function () {
        $deviceId = 'push-online-'.uniqid();
        $host = $this->getTestServerHost();
        $port = $this->getTestServerPort();
        $userEmail = $this->userA->email;

        $pushReceived = $this->runWebSocketInCoroutine(function () use ($host, $port, $userEmail, $deviceId) {
            $panel = $this->createMockPanel($userEmail);
            $device = new MockDevice($deviceId, [
                'host' => $host,
                'port' => $port,
                'user_email' => $userEmail,
            ]);

            if (! $panel->connect() || ! $device->connect()) {
                return null;
            }

            $panel->subscribe();
            $panel->waitForMessage('subscribe', 3.0);

            usleep(300000); // 300ms 等待订阅生效

            $device->sendPing();
            $push = $panel->waitForPush('deviceOnline', $deviceId, 5.0);

            $device->disconnect();
            $panel->disconnect();

            return $push;
        });

        expect($pushReceived)->not->toBeNull()
            ->and($pushReceived['type'])->toBe('deviceOnline')
            ->and($pushReceived['pid'])->toBe($deviceId)
            ->and($pushReceived)->toHaveKey('phoneInfo');

        // deviceOnline 使用 phoneInfo 携带完整信息（与 deviceUpdate 同构，formatForPanel 格式）
        $phoneInfo = $pushReceived['phoneInfo'];
        expect($phoneInfo)->toBeArray()
            ->and($phoneInfo)->toHaveKey('pid')
            ->and($phoneInfo['pid'])->toBe($deviceId)
            ->and($phoneInfo)->toHaveKey('phone_name')
            ->and($phoneInfo)->toHaveKey('is_online')
            ->and($phoneInfo['is_online'])->toBeTrue()
            ->and($phoneInfo)->toHaveKey('lastPing')
            ->and($phoneInfo)->toHaveKey('ip_location');
        expect(array_key_exists('model', $phoneInfo) || array_key_exists('battery_charge', $phoneInfo))->toBeTrue();
    });

    it('设备离线时 Panel 收到 deviceOffline 推送', function () {
        $deviceId = 'push-offline-'.uniqid();
        $host = $this->getTestServerHost();
        $port = $this->getTestServerPort();
        $userEmail = $this->userA->email;

        $pushReceived = $this->runWebSocketInCoroutine(function () use ($host, $port, $userEmail, $deviceId) {
            $panel = $this->createMockPanel($userEmail);
            $device = new MockDevice($deviceId, [
                'host' => $host,
                'port' => $port,
                'user_email' => $userEmail,
            ]);

            if (! $panel->connect() || ! $device->connect()) {
                return null;
            }

            $panel->subscribe();
            $panel->waitForMessage('subscribe', 3.0);
            usleep(300000);

            $device->sendPing();
            usleep(300000);

            $device->disconnect();
            $push = $panel->waitForPush('deviceOffline', $deviceId, 5.0);

            $panel->disconnect();

            return $push;
        });

        expect($pushReceived)->not->toBeNull()
            ->and($pushReceived['type'])->toBe('deviceOffline')
            ->and($pushReceived['pid'])->toBe($deviceId)
            ->and($pushReceived)->toHaveKey('phoneInfo')
            ->and($pushReceived['phoneInfo'])->toBeNull();
    });

    it('deviceOnline 的 phoneInfo 包含设备 ping 上报的字段', function () {
        $deviceId = 'push-phoneinfo-'.uniqid();
        $host = $this->getTestServerHost();
        $port = $this->getTestServerPort();
        $userEmail = $this->userA->email;

        $pushReceived = $this->runWebSocketInCoroutine(function () use ($host, $port, $userEmail, $deviceId) {
            $panel = $this->createMockPanel($userEmail);
            $device = new MockDevice($deviceId, [
                'host' => $host,
                'port' => $port,
                'user_email' => $userEmail,
                'phone_name' => 'Custom Name',
                'model' => 'Custom Model',
                'battery_charge' => '90',
            ]);

            if (! $panel->connect() || ! $device->connect()) {
                return null;
            }

            $panel->subscribe();
            $panel->waitForMessage('subscribe', 3.0);
            usleep(300000);

            $device->sendPing();
            $push = $panel->waitForPush('deviceOnline', $deviceId, 5.0);

            $device->disconnect();
            $panel->disconnect();

            return $push;
        });

        expect($pushReceived)->not->toBeNull()
            ->and($pushReceived['type'])->toBe('deviceOnline');

        $phoneInfo = $pushReceived['phoneInfo'];
        expect($phoneInfo)->toBeArray()
            ->and($phoneInfo['phone_name'])->toBe('Custom Name')
            ->and($phoneInfo['model'])->toBe('Custom Model')
            ->and($phoneInfo['battery_charge'])->toBe('90')
            ->and($phoneInfo['is_online'])->toBeTrue()
            ->and($phoneInfo['pid'])->toBe($deviceId);
    });
});
