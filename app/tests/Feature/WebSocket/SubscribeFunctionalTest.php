<?php

declare(strict_types=1);

use App\Models\Device;
use App\Models\User;
use Tests\Feature\WebSocket\WebSocketFunctionalTestTrait;
use Tests\Support\WebSocketTestClient;

uses(WebSocketFunctionalTestTrait::class);

beforeEach(function () {
    $this->setUpWebSocketFunctional();
    $this->skipIfWebSocketServerUnavailable();
});

afterEach(function () {
    $this->tearDownWebSocketFunctional();
});

describe('WebSocket Subscribe 功能测试', function () {
    it('Panel 连接后发送 subscribe 收到成功响应', function () {
        $host = $this->getTestServerHost();
        $port = $this->getTestServerPort();
        $userEmail = $this->userA->email;

        $result = $this->runWebSocketInCoroutine(function () use ($userEmail) {
            $panel = $this->createMockPanel($userEmail);
            if (! $panel->connect()) {
                return false;
            }
            $panel->subscribe();
            $msg = $panel->waitForMessage('subscribe', 5.0);
            $panel->disconnect();

            return $msg && ($msg['success'] ?? false);
        });

        expect($result)->toBeTrue();
    });

    it('Subscribe 响应包含 devices 和 stats', function () {
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
            ->and($result)->toHaveKeys(['devices', 'stats'])
            ->and($result['devices'])->toBeArray()
            ->and($result['stats'])->toHaveKeys(['total', 'online', 'offline']);
    });

    it('有设备时 subscribe 返回该用户的设备列表', function () {
        // 使用 trait 提供的方法创建设备
        $device = $this->createTestDevice([
            'uuid' => 'test-device-subscribe-'.uniqid(),
            'user_id' => $this->userA->id,
            'name' => 'Subscribe Test Device',
        ]);

        $host = $this->getTestServerHost();
        $port = $this->getTestServerPort();
        $userEmail = $this->userA->email;
        $deviceUuid = $device->uuid;

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
            ->and($result['stats']['total'])->toBeGreaterThan(0)
            ->and($result['devices'])->toBeArray()
            ->and(collect($result['devices'])->pluck('uuid')->toArray())->toContain($deviceUuid);
    });

    it('子账号 subscribe 返回父账号的设备列表', function () {
        $parent = User::create([
            'username' => 'ws_parent_'.uniqid(),
            'email' => 'parent_'.uniqid().'@ws-test.local',
            'password' => bcrypt('password'),
        ]);
        $sub = User::create([
            'username' => 'ws_sub_'.uniqid(),
            'email' => 'sub_'.uniqid().'@ws-test.local',
            'password' => bcrypt('password'),
            'parent_id' => $parent->id,
        ]);

        $deviceUuid = 'sub-share-'.uniqid();
        $device = Device::create([
            'uuid' => $deviceUuid,
            'user_id' => $parent->id,
            'name' => 'Parent Device',
            'is_online' => false,
            'is_removed' => false,
        ]);

        $host = $this->getTestServerHost();
        $port = $this->getTestServerPort();
        $subEmail = $sub->email;

        $result = $this->runWebSocketInCoroutine(function () use ($subEmail) {
            $panel = $this->createMockPanel($subEmail);
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
            ->and($result['stats']['total'])->toBeGreaterThan(0)
            ->and(collect($result['devices'])->pluck('uuid')->toArray())->toContain($deviceUuid);
    });

    it('Panel 心跳 ping 收到 pong', function () {
        $host = $this->getTestServerHost();
        $port = $this->getTestServerPort();

        $result = $this->runWebSocketInCoroutine(function () use ($host, $port) {
            $client = new WebSocketTestClient($host, $port);
            if (! $client->connect()) {
                return false;
            }
            $client->send(['subc' => 'ping']);
            $msg = $client->waitForMessage('pong', 3.0);
            $client->disconnect();

            return $msg !== null;
        });

        expect($result)->toBeTrue();
    });
});
