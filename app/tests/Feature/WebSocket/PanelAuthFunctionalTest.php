<?php

declare(strict_types=1);

use App\Models\Device;
use App\Models\User;
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

describe('WebSocket Panel 认证功能测试', function () {
    it('有效 token 的 Panel 能成功 subscribe 并收到设备列表', function () {
        $this->createTestDevice([
            'uuid' => 'auth-panel-dev-'.uniqid(),
            'user_id' => $this->userA->id,
            'name' => 'Auth Test Device',
        ]);

        $host = $this->getTestServerHost();
        $port = $this->getTestServerPort();
        $userEmail = $this->userA->email;

        $result = $this->runWebSocketInCoroutine(function () use ($userEmail) {
            $panel = $this->createMockPanel($userEmail);
            if (! $panel->connect()) {
                return null;
            }
            $panel->subscribe();
            $msg = $panel->waitForMessage('subscribe', 5.0);
            $panel->disconnect();

            return $msg;
        });

        expect($result)->not->toBeNull()
            ->and($result['success'])->toBeTrue()
            ->and($result['devices'])->toBeArray()
            ->and($result['stats'])->toHaveKeys(['total', 'online', 'offline'])
            ->and($result['stats']['total'])->toBeGreaterThan(0);
    });

    it('无 token 的 subscribe 被拒绝', function () {
        $host = $this->getTestServerHost();
        $port = $this->getTestServerPort();
        $userEmail = $this->userA->email;

        $result = $this->runWebSocketInCoroutine(function () use ($host, $port, $userEmail) {
            // Create panel without token
            $panel = new MockPanel($userEmail, ['host' => $host, 'port' => $port]);
            if (! $panel->connect()) {
                return null;
            }
            $panel->subscribe();
            $msg = $panel->waitForMessage('subscribe', 5.0);
            $panel->disconnect();

            return $msg;
        });

        expect($result)->not->toBeNull()
            ->and($result['success'])->toBeFalse()
            ->and($result['error'])->toBe('Token is required');
    });

    it('无效 HMAC 的 token 被拒绝', function () {
        $host = $this->getTestServerHost();
        $port = $this->getTestServerPort();
        $userEmail = $this->userA->email;

        $result = $this->runWebSocketInCoroutine(function () use ($host, $port, $userEmail) {
            $invalidToken = MockPanel::generateInvalidPanelToken();
            $panel = new MockPanel($userEmail, [
                'host' => $host,
                'port' => $port,
                'token' => $invalidToken,
            ]);
            if (! $panel->connect()) {
                return null;
            }
            $panel->subscribe();
            $msg = $panel->waitForMessage('subscribe', 5.0);
            $panel->disconnect();

            return $msg;
        });

        expect($result)->not->toBeNull()
            ->and($result['success'])->toBeFalse()
            ->and($result['error'])->toBe('Invalid or expired token');
    });

    it('过期 token 被拒绝', function () {
        $host = $this->getTestServerHost();
        $port = $this->getTestServerPort();
        $userEmail = $this->userA->email;

        $result = $this->runWebSocketInCoroutine(function () use ($host, $port, $userEmail) {
            $expiredToken = MockPanel::generateExpiredPanelToken($userEmail);
            $panel = new MockPanel($userEmail, [
                'host' => $host,
                'port' => $port,
                'token' => $expiredToken,
            ]);
            if (! $panel->connect()) {
                return null;
            }
            $panel->subscribe();
            $msg = $panel->waitForMessage('subscribe', 5.0);
            $panel->disconnect();

            return $msg;
        });

        expect($result)->not->toBeNull()
            ->and($result['success'])->toBeFalse()
            ->and($result['error'])->toBe('Invalid or expired token');
    });

    it('无效 token 的 join 被拒绝', function () {
        $deviceId = 'auth-invalid-join-'.uniqid();
        $host = $this->getTestServerHost();
        $port = $this->getTestServerPort();
        $userEmail = $this->userA->email;

        $result = $this->runWebSocketInCoroutine(function () use ($host, $port, $userEmail, $deviceId) {
            $invalidToken = MockPanel::generateInvalidPanelToken();
            $panel = new MockPanel($userEmail, [
                'host' => $host,
                'port' => $port,
                'token' => $invalidToken,
            ]);

            if (! $panel->connect()) {
                return null;
            }

            $panel->joinDevice($deviceId);
            $msg = $panel->waitForMessage('error', 3.0);
            $panel->disconnect();

            return $msg;
        });

        expect($result)->not->toBeNull()
            ->and($result['type'])->toBe('error')
            ->and($result['error'])->toContain('Not authenticated');
    });

    it('过期 token 的 join 被拒绝', function () {
        $deviceId = 'auth-expired-join-'.uniqid();
        $host = $this->getTestServerHost();
        $port = $this->getTestServerPort();
        $userEmail = $this->userA->email;

        $result = $this->runWebSocketInCoroutine(function () use ($host, $port, $userEmail, $deviceId) {
            $expiredToken = MockPanel::generateExpiredPanelToken($userEmail);
            $panel = new MockPanel($userEmail, [
                'host' => $host,
                'port' => $port,
                'token' => $expiredToken,
            ]);

            if (! $panel->connect()) {
                return null;
            }

            $panel->joinDevice($deviceId);
            $msg = $panel->waitForMessage('error', 3.0);
            $panel->disconnect();

            return $msg;
        });

        expect($result)->not->toBeNull()
            ->and($result['type'])->toBe('error')
            ->and($result['error'])->toContain('Not authenticated');
    });

    it('无 token 的 join 被拒绝', function () {
        $deviceId = 'auth-unsubscribed-'.uniqid();
        $host = $this->getTestServerHost();
        $port = $this->getTestServerPort();
        $userEmail = $this->userA->email;

        $result = $this->runWebSocketInCoroutine(function () use ($host, $port, $userEmail, $deviceId) {
            $device = new MockDevice($deviceId, [
                'host' => $host,
                'port' => $port,
                'user_email' => $userEmail,
            ]);

            if (! $device->connect()) {
                return null;
            }

            $device->sendPing();
            usleep(300000);

            // Create panel without subscribing, try to join device directly
            $panel = new MockPanel($userEmail, ['host' => $host, 'port' => $port]);
            if (! $panel->connect()) {
                $device->disconnect();

                return null;
            }

            // Try to join without subscribing first
            $panel->joinDevice($deviceId);
            $msg = $panel->waitForMessage('error', 3.0);

            $panel->disconnect();
            $device->disconnect();

            return $msg;
        });

        expect($result)->not->toBeNull()
            ->and($result['type'])->toBe('error')
            ->and($result['error'])->toContain('Not authenticated');
    });

    it('普通用户无法 join 其他用户的设备', function () {
        $deviceId = 'auth-other-user-dev-'.uniqid();
        $this->createTestDevice([
            'uuid' => $deviceId,
            'user_id' => $this->userB->id,
            'name' => 'User B Device',
        ]);

        $host = $this->getTestServerHost();
        $port = $this->getTestServerPort();
        $userAEmail = $this->userA->email;

        $result = $this->runWebSocketInCoroutine(function () use ($userAEmail, $deviceId) {
            $panel = $this->createMockPanel($userAEmail);
            if (! $panel->connect()) {
                return null;
            }

            $panel->subscribe();
            $panel->waitForMessage('subscribe', 5.0);
            usleep(300000);

            $panel->joinDevice($deviceId);
            $msg = $panel->waitForMessage('error', 3.0);

            $panel->disconnect();

            return $msg;
        });

        expect($result)->not->toBeNull()
            ->and($result['type'])->toBe('error')
            ->and($result['error'])->toContain('Not authorized');
    });

    it('管理员可以控制任意设备', function () {
        $deviceId = 'auth-admin-control-'.uniqid();
        $host = $this->getTestServerHost();
        $port = $this->getTestServerPort();
        $adminEmail = $this->admin->email;
        $userAEmail = $this->userA->email;

        $result = $this->runWebSocketInCoroutine(function () use ($host, $port, $adminEmail, $userAEmail, $deviceId) {
            $adminPanel = $this->createMockPanel($adminEmail);
            $device = new MockDevice($deviceId, [
                'host' => $host,
                'port' => $port,
                'user_email' => $userAEmail,
            ]);

            if (! $adminPanel->connect() || ! $device->connect()) {
                return null;
            }

            $adminPanel->subscribe();
            $adminPanel->waitForMessage('subscribe', 5.0);
            usleep(300000);

            $device->sendPing();
            usleep(300000);

            $adminPanel->joinDevice($deviceId);
            $msg = $adminPanel->waitForMessage('statusBatch', 5.0);

            $device->disconnect();
            $adminPanel->disconnect();

            return $msg;
        });

        expect($result)->not->toBeNull()
            ->and($result['type'])->toBe('statusBatch')
            ->and($result['pid'])->toBe($deviceId);
    });

    it('子账号可以控制父账号的设备', function () {
        $parent = User::create([
            'username' => 'ws_parent_auth_'.uniqid(),
            'email' => 'parent_auth_'.uniqid().'@ws-test.local',
            'password' => bcrypt('password'),
        ]);
        $sub = User::create([
            'username' => 'ws_sub_auth_'.uniqid(),
            'email' => 'sub_auth_'.uniqid().'@ws-test.local',
            'password' => bcrypt('password'),
            'parent_id' => $parent->id,
        ]);

        $deviceId = 'auth-sub-control-'.uniqid();
        Device::create([
            'uuid' => $deviceId,
            'user_id' => $parent->id,
            'name' => 'Parent Device',
            'is_online' => false,
            'is_removed' => false,
        ]);

        $host = $this->getTestServerHost();
        $port = $this->getTestServerPort();
        $subEmail = $sub->email;
        $parentEmail = $parent->email;

        $result = $this->runWebSocketInCoroutine(function () use ($host, $port, $subEmail, $parentEmail, $deviceId) {
            $subPanel = $this->createMockPanel($subEmail);
            $device = new MockDevice($deviceId, [
                'host' => $host,
                'port' => $port,
                'user_email' => $parentEmail,
            ]);

            if (! $subPanel->connect() || ! $device->connect()) {
                return null;
            }

            $subPanel->subscribe();
            $subPanel->waitForMessage('subscribe', 5.0);
            usleep(300000);

            $device->sendPing();
            usleep(300000);

            $subPanel->joinDevice($deviceId);
            $msg = $subPanel->waitForMessage('statusBatch', 5.0);

            $device->disconnect();
            $subPanel->disconnect();

            return $msg;
        });

        expect($result)->not->toBeNull()
            ->and($result['type'])->toBe('statusBatch')
            ->and($result['pid'])->toBe($deviceId);
    });
});
