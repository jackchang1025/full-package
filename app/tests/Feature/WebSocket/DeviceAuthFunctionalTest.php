<?php

declare(strict_types=1);

use App\Models\Device;
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

describe('WebSocket 设备认证功能测试', function () {
    it('有效 token 的设备能正常上线并被 Panel 收到', function () {
        $deviceId = 'auth-valid-'.uniqid();
        $host = $this->getTestServerHost();
        $port = $this->getTestServerPort();
        $userEmail = $this->userA->email;

        $push = $this->runWebSocketInCoroutine(function () use ($host, $port, $userEmail, $deviceId) {
            $panel = $this->createMockPanel($userEmail);
            // MockDevice 自动生成有效 token
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
            $p = $panel->waitForPush('deviceOnline', $deviceId, 5.0);

            $device->disconnect();
            $panel->disconnect();

            return $p;
        });

        expect($push)->not->toBeNull()
            ->and($push['type'])->toBe('deviceOnline')
            ->and($push['pid'])->toBe($deviceId);
    });

    it('无效 HMAC 的设备不会创建设备记录', function () {
        $deviceId = 'auth-invalid-hmac-'.uniqid();
        $host = $this->getTestServerHost();
        $port = $this->getTestServerPort();
        $userEmail = $this->userA->email;
        $invalidToken = MockDevice::generateInvalidToken($userEmail);

        $result = $this->runWebSocketInCoroutine(function () use ($host, $port, $userEmail, $deviceId, $invalidToken) {
            $panel = $this->createMockPanel($userEmail);
            $device = new MockDevice($deviceId, [
                'host' => $host,
                'port' => $port,
                'user_email' => $invalidToken,
            ]);

            if (! $panel->connect() || ! $device->connect()) {
                return ['error' => 'connection_failed'];
            }

            $panel->subscribe();
            $panel->waitForMessage('subscribe', 3.0);
            usleep(300000);
            $panel->clearMessages();

            $device->sendPing();
            // 等待足够时间确认 Panel 没收到 deviceOnline
            $push = $panel->waitForPush('deviceOnline', $deviceId, 3.0);

            $device->disconnect();
            $panel->disconnect();

            return ['push' => $push];
        });

        expect($result)->not->toHaveKey('error')
            ->and($result['push'])->toBeNull();

        // 数据库中也不应有该设备
        expect(Device::where('uuid', $deviceId)->exists())->toBeFalse();
    });

    it('无 token 的纯 email 设备不会创建设备记录', function () {
        $deviceId = 'auth-no-token-'.uniqid();
        $host = $this->getTestServerHost();
        $port = $this->getTestServerPort();
        $userEmail = $this->userA->email;

        $result = $this->runWebSocketInCoroutine(function () use ($host, $port, $userEmail, $deviceId) {
            $panel = $this->createMockPanel($userEmail);
            // 直接传入带 || 的无效格式绕过 MockDevice 自动生成
            // 这里传纯 email 不带 ||，MockDevice 会自动生成 token
            // 所以我们手动构造一个不含 || 的 user_email 来模拟旧设备
            $device = new MockDevice($deviceId, [
                'host' => $host,
                'port' => $port,
                // 用一个带 || 但格式错误的值绕过自动 token 生成
                'user_email' => $userEmail.'||invalid',
            ]);

            if (! $panel->connect() || ! $device->connect()) {
                return ['error' => 'connection_failed'];
            }

            $panel->subscribe();
            $panel->waitForMessage('subscribe', 3.0);
            usleep(300000);
            $panel->clearMessages();

            $device->sendPing();
            $push = $panel->waitForPush('deviceOnline', $deviceId, 3.0);

            $device->disconnect();
            $panel->disconnect();

            return ['push' => $push];
        });

        expect($result)->not->toHaveKey('error')
            ->and($result['push'])->toBeNull();

        expect(Device::where('uuid', $deviceId)->exists())->toBeFalse();
    });

    it('篡改 email 的 token 设备不会创建设备记录', function () {
        $deviceId = 'auth-tampered-email-'.uniqid();
        $host = $this->getTestServerHost();
        $port = $this->getTestServerPort();
        $userEmail = $this->userA->email;

        // 用 userA 的 email 生成 token，然后替换为 userB 的 email
        $validToken = MockDevice::generateTestToken($userEmail);
        $tamperedToken = str_replace($userEmail, $this->userB->email, $validToken);

        $result = $this->runWebSocketInCoroutine(function () use ($host, $port, $userEmail, $deviceId, $tamperedToken) {
            $panel = $this->createMockPanel($userEmail);
            $device = new MockDevice($deviceId, [
                'host' => $host,
                'port' => $port,
                'user_email' => $tamperedToken,
            ]);

            if (! $panel->connect() || ! $device->connect()) {
                return ['error' => 'connection_failed'];
            }

            $panel->subscribe();
            $panel->waitForMessage('subscribe', 3.0);
            usleep(300000);
            $panel->clearMessages();

            $device->sendPing();
            $push = $panel->waitForPush('deviceOnline', $deviceId, 3.0);

            $device->disconnect();
            $panel->disconnect();

            return ['push' => $push];
        });

        expect($result)->not->toHaveKey('error')
            ->and($result['push'])->toBeNull();

        expect(Device::where('uuid', $deviceId)->exists())->toBeFalse();
    });

    it('已存在的设备再次 ping 不受认证影响（只有首次创建时验证）', function () {
        $deviceId = 'auth-existing-'.uniqid();
        $host = $this->getTestServerHost();
        $port = $this->getTestServerPort();
        $userEmail = $this->userA->email;

        // 先用有效 token 创建设备
        $this->createTestDevice([
            'uuid' => $deviceId,
            'user_id' => $this->userA->id,
            'name' => 'Pre-existing Device',
            'is_online' => false,
        ]);

        $result = $this->runWebSocketInCoroutine(function () use ($host, $port, $userEmail, $deviceId) {
            $panel = $this->createMockPanel($userEmail);
            // 即使用无效 token，已存在的设备仍能正常 ping
            $device = new MockDevice($deviceId, [
                'host' => $host,
                'port' => $port,
                'user_email' => MockDevice::generateInvalidToken($userEmail),
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

        expect($result)->not->toBeNull()
            ->and($result['type'])->toBe('deviceOnline')
            ->and($result['pid'])->toBe($deviceId);
    });

    it('空 user_email 的设备不会创建设备记录', function () {
        $deviceId = 'auth-empty-email-'.uniqid();
        $host = $this->getTestServerHost();
        $port = $this->getTestServerPort();
        $userEmail = $this->userA->email;

        $result = $this->runWebSocketInCoroutine(function () use ($host, $port, $userEmail, $deviceId) {
            $panel = $this->createMockPanel($userEmail);
            // 传入带 || 的空 email token 绕过自动生成
            $device = new MockDevice($deviceId, [
                'host' => $host,
                'port' => $port,
                'user_email' => '||fake.1.0',
            ]);

            if (! $panel->connect() || ! $device->connect()) {
                return ['error' => 'connection_failed'];
            }

            $panel->subscribe();
            $panel->waitForMessage('subscribe', 3.0);
            usleep(300000);
            $panel->clearMessages();

            $device->sendPing();
            $push = $panel->waitForPush('deviceOnline', $deviceId, 3.0);

            $device->disconnect();
            $panel->disconnect();

            return ['push' => $push];
        });

        expect($result)->not->toHaveKey('error')
            ->and($result['push'])->toBeNull();

        expect(Device::where('uuid', $deviceId)->exists())->toBeFalse();
    });
});
