<?php

declare(strict_types=1);

use App\Models\Device;
use App\Models\User;
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

describe('WebSocket 用户隔离功能测试', function () {
    it('User A 的 Panel 只收到 User A 设备的推送，不收 User B 的', function () {
        $deviceA = 'isolation-a-'.uniqid();
        $deviceB = 'isolation-b-'.uniqid();
        $host = $this->getTestServerHost();
        $port = $this->getTestServerPort();
        $userAEmail = $this->userA->email;
        $userBEmail = $this->userB->email;

        $result = $this->runWebSocketInCoroutine(function () use ($host, $port, $userAEmail, $userBEmail, $deviceA, $deviceB) {
            $panelA = $this->createMockPanel($userAEmail);
            $devA = new MockDevice($deviceA, ['host' => $host, 'port' => $port, 'user_email' => $userAEmail]);
            $devB = new MockDevice($deviceB, ['host' => $host, 'port' => $port, 'user_email' => $userBEmail]);

            if (! $panelA->connect() || ! $devA->connect() || ! $devB->connect()) {
                return ['error' => 'connection_failed'];
            }

            $panelA->subscribe();
            $panelA->waitForMessage('subscribe', 3.0);
            usleep(300000);

            // 清空已接收消息
            $panelA->clearMessages();

            // 设备 A 上线 - Panel A 应该收到
            $devA->sendPing();
            $pushA = $panelA->waitForPush('deviceOnline', $deviceA, 3.0);

            // 设备 B 上线 - Panel A 不应该收到
            $devB->sendPing();
            usleep(800000); // 等待足够长时间确认没收到

            // 收集所有收到的消息中的设备 ID
            $receivedDevices = [];
            foreach ($panelA->getReceivedMessages() as $m) {
                if (($m['type'] ?? '') === 'deviceOnline' && isset($m['pid'])) {
                    $receivedDevices[] = $m['pid'];
                }
            }

            $devA->disconnect();
            $devB->disconnect();
            $panelA->disconnect();

            return [
                'pushA' => $pushA,
                'receivedDevices' => $receivedDevices,
            ];
        });

        expect($result)->not->toHaveKey('error')
            ->and($result['pushA'])->not->toBeNull()
            ->and($result['receivedDevices'])->toContain($deviceA)
            ->and($result['receivedDevices'])->not->toContain($deviceB);
    });

    it('管理员的 Panel 能收到所有用户的设备上线推送', function () {
        $deviceA = 'admin-test-a-'.uniqid();
        $deviceB = 'admin-test-b-'.uniqid();
        $host = $this->getTestServerHost();
        $port = $this->getTestServerPort();
        $adminEmail = $this->admin->email;
        $userAEmail = $this->userA->email;
        $userBEmail = $this->userB->email;

        $result = $this->runWebSocketInCoroutine(function () use ($host, $port, $adminEmail, $userAEmail, $userBEmail, $deviceA, $deviceB) {
            $adminPanel = $this->createMockPanel($adminEmail);
            $devA = new MockDevice($deviceA, ['host' => $host, 'port' => $port, 'user_email' => $userAEmail]);
            $devB = new MockDevice($deviceB, ['host' => $host, 'port' => $port, 'user_email' => $userBEmail]);

            if (! $adminPanel->connect() || ! $devA->connect() || ! $devB->connect()) {
                return ['error' => 'connection_failed'];
            }

            $adminPanel->subscribe();
            $adminPanel->waitForMessage('subscribe', 3.0);
            usleep(300000);

            // 设备 A 上线
            $devA->sendPing();
            $pushA = $adminPanel->waitForPush('deviceOnline', $deviceA, 3.0);

            // 设备 B 上线
            $devB->sendPing();
            $pushB = $adminPanel->waitForPush('deviceOnline', $deviceB, 3.0);

            $devA->disconnect();
            $devB->disconnect();
            $adminPanel->disconnect();

            return [
                'pushA' => $pushA,
                'pushB' => $pushB,
            ];
        });

        expect($result)->not->toHaveKey('error')
            ->and($result['pushA'])->not->toBeNull()
            ->and($result['pushA']['pid'])->toBe($deviceA)
            ->and($result['pushB'])->not->toBeNull()
            ->and($result['pushB']['pid'])->toBe($deviceB);
    });

    it('子账号 Panel 能收到父账号设备的 deviceOnline 推送', function () {
        $parent = User::create([
            'username' => 'ws_parent_iso_'.uniqid(),
            'email' => 'parent_iso_'.uniqid().'@ws-test.local',
            'password' => bcrypt('password'),
        ]);
        $sub = User::create([
            'username' => 'ws_sub_iso_'.uniqid(),
            'email' => 'sub_iso_'.uniqid().'@ws-test.local',
            'password' => bcrypt('password'),
            'parent_id' => $parent->id,
        ]);

        $deviceUuid = 'sub-share-'.uniqid();
        Device::create([
            'uuid' => $deviceUuid,
            'user_id' => $parent->id,
            'name' => 'Parent Device',
            'is_online' => false,
            'is_removed' => false,
        ]);

        $host = $this->getTestServerHost();
        $port = $this->getTestServerPort();
        $subEmail = $sub->email;
        $parentEmail = $parent->email;

        $result = $this->runWebSocketInCoroutine(function () use ($host, $port, $subEmail, $parentEmail, $deviceUuid) {
            $panelSub = $this->createMockPanel($subEmail);
            $dev = new MockDevice($deviceUuid, ['host' => $host, 'port' => $port, 'user_email' => $parentEmail]);

            if (! $panelSub->connect() || ! $dev->connect()) {
                return ['error' => 'connection_failed'];
            }

            $panelSub->subscribe();
            $panelSub->waitForMessage('subscribe', 3.0);
            usleep(300000);
            $panelSub->clearMessages();

            $dev->sendPing();
            $push = $panelSub->waitForPush('deviceOnline', $deviceUuid, 5.0);

            $dev->disconnect();
            $panelSub->disconnect();

            return ['push' => $push];
        });

        expect($result)->not->toHaveKey('error')
            ->and($result['push'])->not->toBeNull()
            ->and($result['push']['pid'])->toBe($deviceUuid);
    });
});
