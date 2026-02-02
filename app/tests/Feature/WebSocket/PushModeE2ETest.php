<?php

/**
 * WebSocket Push Mode E2E Tests
 * 
 * These tests require a running WebSocket server and test real network communication.
 * They are inherently timing-sensitive and may be flaky in CI environments.
 * 
 * Run individually for best results:
 *   ./vendor/bin/sail exec laravel.test php vendor/bin/pest tests/Feature/WebSocket/PushModeE2ETest.php --filter="<test name>"
 * 
 * Or run all with the e2e group:
 *   ./vendor/bin/sail exec laravel.test php vendor/bin/pest --group=e2e
 */

declare(strict_types=1);

use App\Models\Device;
use App\Models\User;
use App\WebSocket\Services\EncryptionService;
use Tests\Support\MockDevice;
use Tests\Support\MockPanel;

uses()->group('e2e', 'websocket');

beforeEach(function () {
    $this->testUsers = [];
    $this->testDevices = [];
    
    if (env('RUN_E2E_TESTS', false) === false) {
        $this->markTestSkipped('E2E tests skipped. Set RUN_E2E_TESTS=true to run.');
    }
    
    $this->encryptionService = new EncryptionService();
    $this->encryptionService = new EncryptionService();
    $this->wsHost = env('WS_TEST_HOST', '127.0.0.1');
    $this->wsPort = (int) env('WS_TEST_PORT', 8081);
    $this->testUsers = [];
    $this->testDevices = [];
    
    // E2E tests must use the main database (same as WebSocket server)
    config(['database.connections.mysql.database' => env('DB_DATABASE_MAIN', 'feiying_v2')]);

    // Reset WebSocket server state before each test
    runInCoroutine(function () {
        $client = new \Tests\Support\WebSocketTestClient($this->wsHost, $this->wsPort);
        if ($client->connect(3.0)) {
            $client->sendTestReset();
            $client->disconnect();
            // Wait for server to fully process reset and for any stale connections to clear
            \Swoole\Coroutine::sleep(0.5);
        }
    });
});

afterEach(function () {
    foreach ($this->testDevices as $deviceId) {
        Device::on('mysql')->where('uuid', $deviceId)->delete();
    }
    foreach ($this->testUsers as $userId) {
        User::on('mysql')->where('id', $userId)->delete();
    }
});

function runInCoroutine(callable $callback): void
{
    \Swoole\Coroutine\run($callback);
}

function createE2EUser(object $test, string $email): User
{
    $user = new User();
    $user->setConnection('mysql');
    $user->username = 'e2e_' . substr(md5($email), 0, 8);
    $user->email = $email;
    $user->email_encrypted = $test->encryptionService->encryptEmail($email);
    $user->password = bcrypt('password');
    $user->save();
    
    $test->testUsers[] = $user->id;
    
    usleep(100000);
    
    return $user;
}

function trackDevice(object $test, string $deviceId): void
{
    $test->testDevices[] = $deviceId;
}

describe('推送模式 E2E 测试', function () {
    describe('Panel 订阅注册', function () {
        it('checkphone 后 Panel 被注册到订阅表', function () {
            $email = 'e2e-panel-sub-' . time() . '@test.com';
            $user = createE2EUser($this, $email);

            runInCoroutine(function () use ($user) {
                $panel = new MockPanel($user->email_encrypted, [
                    'host' => $this->wsHost,
                    'port' => $this->wsPort,
                ]);

                $connected = $panel->connect();
                expect($connected)->toBeTrue();

                $panel->checkPhone();
                $response = $panel->waitForMessage('checkphone', 5.0);

                expect($response)->not->toBeNull();
                expect($response['type'])->toBe('checkphone');
                expect($response['list'])->toBeArray();

                $panel->disconnect();
                \Swoole\Coroutine::sleep(0.3); // Allow server to process disconnect
            });
        });
    });

    describe('设备上线推送 (deviceOnline)', function () {
        it('设备上线时 Panel 收到 deviceOnline 推送', function () {
            $email = 'e2e-online-push-' . time() . '@test.com';
            $user = createE2EUser($this, $email);
            $deviceId = 'e2e-online-' . time();
            trackDevice($this, $deviceId);

            runInCoroutine(function () use ($user, $deviceId) {
                $panel = new MockPanel($user->email_encrypted, [
                    'host' => $this->wsHost,
                    'port' => $this->wsPort,
                ]);
                $panel->connect();
                $panel->checkPhone();
                $panel->waitForMessage('checkphone', 3.0);

                \Swoole\Coroutine::sleep(0.5);

                $device = new MockDevice($deviceId, [
                    'host' => $this->wsHost,
                    'port' => $this->wsPort,
                    'user_email' => $user->email,
                    'phone_name' => 'E2E Test Device',
                ]);
                $device->connect();
                $device->sendPing();

                $pushMsg = $panel->waitForPush('deviceOnline', $deviceId, 5.0);

                expect($pushMsg)->not->toBeNull();
                expect($pushMsg['type'])->toBe('deviceOnline');
                expect($pushMsg['pid'])->toBe($deviceId);
                expect($pushMsg['deviceInfo'])->toBeArray();
                expect($pushMsg['deviceInfo']['is_online'])->toBeTrue();

                $device->disconnect();
                $panel->disconnect();
                \Swoole\Coroutine::sleep(0.3); // Allow server to process disconnects
            });
        });
    });

    describe('设备离线推送 (deviceOffline)', function () {
        it('设备断开时 Panel 收到 deviceOffline 推送', function () {
            $email = 'e2e-offline-push-' . time() . '@test.com';
            $user = createE2EUser($this, $email);
            $deviceId = 'e2e-offline-' . time();
            trackDevice($this, $deviceId);

            runInCoroutine(function () use ($user, $deviceId) {
                $panel = new MockPanel($user->email_encrypted, [
                    'host' => $this->wsHost,
                    'port' => $this->wsPort,
                ]);
                $panel->connect();
                $panel->checkPhone();
                $panel->waitForMessage('checkphone', 3.0);

                \Swoole\Coroutine::sleep(0.3);

                $device = new MockDevice($deviceId, [
                    'host' => $this->wsHost,
                    'port' => $this->wsPort,
                    'user_email' => $user->email,
                ]);
                $device->connect();
                $device->sendPing();

                $panel->waitForPush('deviceOnline', $deviceId, 3.0);

                $device->disconnect();

                $pushMsg = $panel->waitForPush('deviceOffline', $deviceId, 5.0);

                expect($pushMsg)->not->toBeNull();
                expect($pushMsg['type'])->toBe('deviceOffline');
                expect($pushMsg['pid'])->toBe($deviceId);

                $panel->disconnect();
                \Swoole\Coroutine::sleep(0.3); // Allow server to process disconnect
            });
        });
    });

    describe('用户隔离', function () {
        it('User A 只收到自己设备的推送，不收到 User B 设备的推送', function () {
            $emailA = 'e2e-usera-' . time() . '@test.com';
            $emailB = 'e2e-userb-' . time() . '@test.com';
            $userA = createE2EUser($this, $emailA);
            $userB = createE2EUser($this, $emailB);
            $deviceIdA = 'e2e-device-a-' . time();
            trackDevice($this, $deviceIdA);

            runInCoroutine(function () use ($userA, $userB, $deviceIdA) {
                $panelA = new MockPanel($userA->email_encrypted, [
                    'host' => $this->wsHost,
                    'port' => $this->wsPort,
                ]);
                $panelB = new MockPanel($userB->email_encrypted, [
                    'host' => $this->wsHost,
                    'port' => $this->wsPort,
                ]);

                $panelA->connect();
                $panelB->connect();

                $panelA->checkPhone();
                $panelB->checkPhone();

                $panelA->waitForMessage('checkphone', 3.0);
                $panelB->waitForMessage('checkphone', 3.0);

                \Swoole\Coroutine::sleep(0.5);

                $deviceA = new MockDevice($deviceIdA, [
                    'host' => $this->wsHost,
                    'port' => $this->wsPort,
                    'user_email' => $userA->email,
                ]);
                $deviceA->connect();
                $deviceA->sendPing();

                $msgA = $panelA->waitForPush('deviceOnline', $deviceIdA, 5.0);
                expect($msgA)->not->toBeNull();
                expect($msgA['pid'])->toBe($deviceIdA);

                $msgB = $panelB->waitForPush('deviceOnline', null, 1.0);
                expect($msgB)->toBeNull();

                $deviceA->disconnect();
                $panelA->disconnect();
                $panelB->disconnect();
                \Swoole\Coroutine::sleep(0.3); // Allow server to process disconnects
            });
        });
    });

    describe('多 Panel 同时在线', function () {
        it('同一用户多个 Panel 都能收到推送', function () {
            $email = 'e2e-multi-panel-' . time() . '@test.com';
            $user = createE2EUser($this, $email);
            $deviceId = 'e2e-multi-panel-' . time();
            trackDevice($this, $deviceId);

            runInCoroutine(function () use ($user, $deviceId) {
                $panel1 = new MockPanel($user->email_encrypted, [
                    'host' => $this->wsHost,
                    'port' => $this->wsPort,
                ]);
                $panel2 = new MockPanel($user->email_encrypted, [
                    'host' => $this->wsHost,
                    'port' => $this->wsPort,
                ]);

                $panel1->connect();
                $panel2->connect();

                $panel1->checkPhone();
                $panel2->checkPhone();

                $resp1 = $panel1->waitForMessage('checkphone', 3.0);
                $resp2 = $panel2->waitForMessage('checkphone', 3.0);

                expect($resp1)->not->toBeNull();
                expect($resp2)->not->toBeNull();

                \Swoole\Coroutine::sleep(0.3);

                $device = new MockDevice($deviceId, [
                    'host' => $this->wsHost,
                    'port' => $this->wsPort,
                    'user_email' => $user->email,
                ]);
                $device->connect();
                $device->sendPing();

                $msg1 = $panel1->waitForPush('deviceOnline', $deviceId, 5.0);

                expect($msg1)->not->toBeNull();
                expect($msg1['pid'])->toBe($deviceId);

                $device->disconnect();
                $panel1->disconnect();
                $panel2->disconnect();
                \Swoole\Coroutine::sleep(0.3);
            });
        });
    });

    describe('设备自动创建', function () {
        it('新设备首次上线自动创建数据库记录', function () {
            $email = 'e2e-auto-create-' . time() . '@test.com';
            $user = createE2EUser($this, $email);
            $deviceId = 'e2e-auto-create-' . time();
            trackDevice($this, $deviceId);

            expect(Device::where('uuid', $deviceId)->exists())->toBeFalse();

            runInCoroutine(function () use ($user, $deviceId) {
                $device = new MockDevice($deviceId, [
                    'host' => $this->wsHost,
                    'port' => $this->wsPort,
                    'user_email' => $user->email,
                    'phone_name' => 'Auto Created Device',
                    'model' => 'Pixel 9 Pro',
                ]);

                $device->connect();
                $device->sendPing();

                \Swoole\Coroutine::sleep(1.0);

                $device->disconnect();
            });

            $createdDevice = Device::where('uuid', $deviceId)->first();

            expect($createdDevice)->not->toBeNull();
            expect($createdDevice->user_id)->toBe($user->id);
            expect($createdDevice->name)->toBe('Auto Created Device');
            expect($createdDevice->model)->toBe('Pixel 9 Pro');
        });
    });
});
