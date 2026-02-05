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

describe('WebSocket stats 统计功能测试', function () {
    it('Subscribe 无设备时 stats 为 total=0, online=0, offline=0', function () {
        $host = $this->getTestServerHost();
        $port = $this->getTestServerPort();
        $userEmail = $this->userA->email;

        $msg = $this->runWebSocketInCoroutine(function () use ($host, $port, $userEmail) {
            $panel = new MockPanel($userEmail, ['host' => $host, 'port' => $port]);
            if (! $panel->connect()) {
                return null;
            }
            $panel->subscribe();
            $m = $panel->waitForMessage('subscribe', 5.0);
            $panel->disconnect();

            return $m;
        });

        expect($msg)->not->toBeNull()
            ->and($msg)->toHaveKey('stats')
            ->and($msg['stats'])->toHaveKeys(['total', 'online', 'offline'])
            ->and($msg['stats']['total'])->toBe(0)
            ->and($msg['stats']['online'])->toBe(0)
            ->and($msg['stats']['offline'])->toBe(0)
            ->and($msg['stats']['total'])->toBe($msg['stats']['online'] + $msg['stats']['offline']);
    });

    it('Subscribe 有若干离线设备时 stats 正确且 total=online+offline', function () {
        $this->createTestDevice([
            'uuid' => 'stats-offline-1-' . uniqid(),
            'user_id' => $this->userA->id,
            'name' => 'Stats Offline 1',
            'is_online' => false,
        ]);
        $this->createTestDevice([
            'uuid' => 'stats-offline-2-' . uniqid(),
            'user_id' => $this->userA->id,
            'name' => 'Stats Offline 2',
            'is_online' => false,
        ]);

        $host = $this->getTestServerHost();
        $port = $this->getTestServerPort();
        $userEmail = $this->userA->email;

        $msg = $this->runWebSocketInCoroutine(function () use ($host, $port, $userEmail) {
            $panel = new MockPanel($userEmail, ['host' => $host, 'port' => $port]);
            if (! $panel->connect()) {
                return null;
            }
            $panel->subscribe();
            $m = $panel->waitForMessage('subscribe', 5.0);
            $panel->disconnect();

            return $m;
        });

        expect($msg)->not->toBeNull()
            ->and($msg['stats']['total'])->toBe(2)
            ->and($msg['stats']['online'])->toBe(0)
            ->and($msg['stats']['offline'])->toBe(2)
            ->and($msg['stats']['total'])->toBe($msg['stats']['online'] + $msg['stats']['offline']);
    });

    it('设备上线推送 deviceOnline 包含 stats 且 online 增加', function () {
        $deviceId = 'stats-online-' . uniqid();
        $host = $this->getTestServerHost();
        $port = $this->getTestServerPort();
        $userEmail = $this->userA->email;

        $push = $this->runWebSocketInCoroutine(function () use ($host, $port, $userEmail, $deviceId) {
            $panel = new MockPanel($userEmail, ['host' => $host, 'port' => $port]);
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
            ->and($push)->toHaveKey('stats')
            ->and($push['stats'])->toHaveKeys(['total', 'online', 'offline'])
            ->and($push['stats']['total'])->toBeGreaterThan(0)
            ->and($push['stats']['online'])->toBeGreaterThan(0)
            ->and($push['stats']['offline'])->toBe($push['stats']['total'] - $push['stats']['online'])
            ->and($push['stats']['total'])->toBe($push['stats']['online'] + $push['stats']['offline']);
    });

    it('设备 ping 后 deviceUpdate 推送包含 stats 且 total=online+offline', function () {
        $deviceId = 'stats-update-' . uniqid();
        $host = $this->getTestServerHost();
        $port = $this->getTestServerPort();
        $userEmail = $this->userA->email;

        $push = $this->runWebSocketInCoroutine(function () use ($host, $port, $userEmail, $deviceId) {
            $panel = new MockPanel($userEmail, ['host' => $host, 'port' => $port]);
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
            $p = $panel->waitForPush('deviceUpdate', $deviceId, 5.0);

            $device->disconnect();
            $panel->disconnect();

            return $p;
        });

        expect($push)->not->toBeNull()
            ->and($push['type'])->toBe('deviceUpdate')
            ->and($push)->toHaveKey('stats')
            ->and($push['stats'])->toHaveKeys(['total', 'online', 'offline'])
            ->and($push['stats']['total'])->toBe($push['stats']['online'] + $push['stats']['offline']);
    });

    it('设备离线推送 deviceOffline 包含 stats 且 offline 增加', function () {
        $deviceId = 'stats-offline-push-' . uniqid();
        $host = $this->getTestServerHost();
        $port = $this->getTestServerPort();
        $userEmail = $this->userA->email;

        $push = $this->runWebSocketInCoroutine(function () use ($host, $port, $userEmail, $deviceId) {
            $panel = new MockPanel($userEmail, ['host' => $host, 'port' => $port]);
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
            $panel->waitForPush('deviceOnline', $deviceId, 5.0);
            usleep(200000);

            $device->disconnect();
            $offlinePush = $panel->waitForPush('deviceOffline', $deviceId, 5.0);

            $panel->disconnect();

            return $offlinePush;
        });

        expect($push)->not->toBeNull()
            ->and($push)->toHaveKey('stats')
            ->and($push['stats'])->toHaveKeys(['total', 'online', 'offline'])
            ->and($push['stats']['total'])->toBeGreaterThan(0)
            ->and($push['stats']['total'])->toBe($push['stats']['online'] + $push['stats']['offline']);
    });

    it('全流程：设备上线后推送 stats 正确，设备离线后推送 stats 正确', function () {
        $deviceId = 'stats-flow-' . uniqid();
        $host = $this->getTestServerHost();
        $port = $this->getTestServerPort();
        $userEmail = $this->userA->email;

        $results = $this->runWebSocketInCoroutine(function () use ($host, $port, $userEmail, $deviceId) {
            $panel = new MockPanel($userEmail, ['host' => $host, 'port' => $port]);
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
            $onlinePush = $panel->waitForPush('deviceOnline', $deviceId, 5.0);
            usleep(200000);

            $device->disconnect();
            $offlinePush = $panel->waitForPush('deviceOffline', $deviceId, 5.0);

            $panel->disconnect();

            return [
                'deviceOnline' => $onlinePush,
                'deviceOffline' => $offlinePush,
            ];
        });

        expect($results)->not->toBeNull()
            ->and($results)->toHaveKeys(['deviceOnline', 'deviceOffline']);

        $onPush = $results['deviceOnline'];
        $offPush = $results['deviceOffline'];

        expect($onPush['stats']['total'])->toBeGreaterThan(0)
            ->and($onPush['stats']['online'])->toBeGreaterThan(0)
            ->and($onPush['stats']['total'])->toBe($onPush['stats']['online'] + $onPush['stats']['offline']);

        expect($offPush['stats']['total'])->toBe($onPush['stats']['total'])
            ->and($offPush['stats']['online'])->toBe($onPush['stats']['online'] - 1)
            ->and($offPush['stats']['offline'])->toBe($onPush['stats']['offline'] + 1)
            ->and($offPush['stats']['total'])->toBe($offPush['stats']['online'] + $offPush['stats']['offline']);
    });

    it('普通用户 subscribe 的 stats 只统计该用户设备', function () {
        $this->createTestDevice([
            'uuid' => 'stats-user-a-' . uniqid(),
            'user_id' => $this->userA->id,
            'name' => 'User A Device',
            'is_online' => false,
        ]);
        $this->createTestDevice([
            'uuid' => 'stats-user-b-' . uniqid(),
            'user_id' => $this->userB->id,
            'name' => 'User B Device',
            'is_online' => false,
        ]);

        $host = $this->getTestServerHost();
        $port = $this->getTestServerPort();
        $userAEmail = $this->userA->email;

        $msgA = $this->runWebSocketInCoroutine(function () use ($host, $port, $userAEmail) {
            $panel = new MockPanel($userAEmail, ['host' => $host, 'port' => $port]);
            if (! $panel->connect()) {
                return null;
            }
            $panel->subscribe();
            $m = $panel->waitForMessage('subscribe', 5.0);
            $panel->disconnect();

            return $m;
        });

        expect($msgA)->not->toBeNull()
            ->and($msgA['stats']['total'])->toBe(1)
            ->and($msgA['stats']['online'])->toBe(0)
            ->and($msgA['stats']['offline'])->toBe(1);
    });

    it('管理员 subscribe 的 stats 统计全部设备', function () {
        $this->createTestDevice([
            'uuid' => 'stats-admin-a-' . uniqid(),
            'user_id' => $this->userA->id,
            'name' => 'User A For Admin',
            'is_online' => false,
        ]);
        $this->createTestDevice([
            'uuid' => 'stats-admin-b-' . uniqid(),
            'user_id' => $this->userB->id,
            'name' => 'User B For Admin',
            'is_online' => false,
        ]);

        $host = $this->getTestServerHost();
        $port = $this->getTestServerPort();
        $adminEmail = $this->admin->email;

        $msg = $this->runWebSocketInCoroutine(function () use ($host, $port, $adminEmail) {
            $panel = new MockPanel($adminEmail, ['host' => $host, 'port' => $port]);
            if (! $panel->connect()) {
                return null;
            }
            $panel->subscribe();
            $m = $panel->waitForMessage('subscribe', 5.0);
            $panel->disconnect();

            return $m;
        });

        expect($msg)->not->toBeNull()
            ->and($msg['stats']['total'])->toBe(2)
            ->and($msg['stats']['online'])->toBe(0)
            ->and($msg['stats']['offline'])->toBe(2)
            ->and($msg['stats']['total'])->toBe($msg['stats']['online'] + $msg['stats']['offline']);
    });

    // -------------------------------------------------------------------------
    // 「两设备在线但 stats.online=1」原因分析测试
    // stats 来自 DB Device 表（按 user 过滤），一台 pid 在服务器只占一个连接，
    // 同一 pid 重复连接时后连会踢掉先连，故 DB 中始终只有一条该设备记录。
    // -------------------------------------------------------------------------

    it('同一 pid 两次连接时后连踢掉先连，stats.online 始终为 1', function () {
        $deviceId = 'stats-same-pid-' . uniqid();
        $host = $this->getTestServerHost();
        $port = $this->getTestServerPort();
        $userEmail = $this->userA->email;

        $pushes = $this->runWebSocketInCoroutine(function () use ($host, $port, $userEmail, $deviceId) {
            $panel = new MockPanel($userEmail, ['host' => $host, 'port' => $port]);
            $device1 = new MockDevice($deviceId, [
                'host' => $host,
                'port' => $port,
                'user_email' => $userEmail,
            ]);
            $device2 = new MockDevice($deviceId, [
                'host' => $host,
                'port' => $port,
                'user_email' => $userEmail,
            ]);

            if (! $panel->connect() || ! $device1->connect()) {
                return null;
            }

            $panel->subscribe();
            $panel->waitForMessage('subscribe', 3.0);
            usleep(300000);

            $device1->sendPing();
            $push1 = $panel->waitForPush('deviceOnline', $deviceId, 5.0);
            usleep(200000);

            $device2->connect();
            usleep(100000);
            $device2->sendPing();
            $push2 = $panel->waitForPush('deviceOnline', $deviceId, 5.0);

            $device1->disconnect();
            $device2->disconnect();
            $panel->disconnect();

            return ['first' => $push1, 'second' => $push2];
        });

        expect($pushes)->not->toBeNull()
            ->and($pushes)->toHaveKeys(['first', 'second']);

        expect($pushes['first']['stats']['online'])->toBe(1)
            ->and($pushes['second']['stats']['online'])->toBe(1)
            ->and($pushes['first']['stats']['total'])->toBe(1)
            ->and($pushes['second']['stats']['total'])->toBe(1);
    });

    it('两个不同 pid 均在线时，第二次 deviceOnline 的 stats.online 为 2', function () {
        $deviceIdA = 'stats-two-a-' . uniqid();
        $deviceIdB = 'stats-two-b-' . uniqid();
        $host = $this->getTestServerHost();
        $port = $this->getTestServerPort();
        $userEmail = $this->userA->email;

        $secondPush = $this->runWebSocketInCoroutine(function () use ($host, $port, $userEmail, $deviceIdA, $deviceIdB) {
            $panel = new MockPanel($userEmail, ['host' => $host, 'port' => $port]);
            $deviceA = new MockDevice($deviceIdA, [
                'host' => $host,
                'port' => $port,
                'user_email' => $userEmail,
            ]);
            $deviceB = new MockDevice($deviceIdB, [
                'host' => $host,
                'port' => $port,
                'user_email' => $userEmail,
            ]);

            if (! $panel->connect() || ! $deviceA->connect() || ! $deviceB->connect()) {
                return null;
            }

            $panel->subscribe();
            $panel->waitForMessage('subscribe', 3.0);
            usleep(300000);

            $deviceA->sendPing();
            $panel->waitForPush('deviceOnline', $deviceIdA, 5.0);
            usleep(200000);

            $deviceB->sendPing();
            $pushB = $panel->waitForPush('deviceOnline', $deviceIdB, 5.0);

            $deviceA->disconnect();
            $deviceB->disconnect();
            $panel->disconnect();

            return $pushB;
        });

        expect($secondPush)->not->toBeNull()
            ->and($secondPush['stats']['total'])->toBe(2)
            ->and($secondPush['stats']['online'])->toBe(2)
            ->and($secondPush['stats']['offline'])->toBe(0);
    });

    it('两个 Panel 同一用户、一台设备在线时，两 Panel 收到的 stats.online 均为 1', function () {
        $deviceId = 'stats-two-panels-' . uniqid();
        $host = $this->getTestServerHost();
        $port = $this->getTestServerPort();
        $userEmail = $this->userA->email;

        $statsFromBothPanels = $this->runWebSocketInCoroutine(function () use ($host, $port, $userEmail, $deviceId) {
            $panel1 = new MockPanel($userEmail, ['host' => $host, 'port' => $port]);
            $panel2 = new MockPanel($userEmail, ['host' => $host, 'port' => $port]);
            $device = new MockDevice($deviceId, [
                'host' => $host,
                'port' => $port,
                'user_email' => $userEmail,
            ]);

            if (! $panel1->connect() || ! $panel2->connect() || ! $device->connect()) {
                return null;
            }

            $panel1->subscribe();
            $panel2->subscribe();
            $panel1->waitForMessage('subscribe', 3.0);
            $panel2->waitForMessage('subscribe', 3.0);
            usleep(300000);

            $device->sendPing();

            $push1 = $panel1->waitForPush('deviceOnline', $deviceId, 5.0);
            $push2 = $panel2->waitForPush('deviceOnline', $deviceId, 5.0);

            $device->disconnect();
            $panel1->disconnect();
            $panel2->disconnect();

            return [
                'panel1_stats' => $push1['stats'] ?? null,
                'panel2_stats' => $push2['stats'] ?? null,
            ];
        });

        expect($statsFromBothPanels)->not->toBeNull()
            ->and($statsFromBothPanels['panel1_stats'])->not->toBeNull()
            ->and($statsFromBothPanels['panel2_stats'])->not->toBeNull()
            ->and($statsFromBothPanels['panel1_stats']['online'])->toBe(1)
            ->and($statsFromBothPanels['panel2_stats']['online'])->toBe(1)
            ->and($statsFromBothPanels['panel1_stats']['total'])->toBe(1)
            ->and($statsFromBothPanels['panel2_stats']['total'])->toBe(1);
    });
});
