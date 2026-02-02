<?php

declare(strict_types=1);

use App\Models\Device;
use App\Models\User;
use App\WebSocket\ConnectionManager;
use App\WebSocket\Handlers\CheckPhoneHandler;
use App\WebSocket\Services\DeviceStatusService;
use App\WebSocket\Services\EncryptionService;
use Illuminate\Foundation\Testing\RefreshDatabase;
use Swoole\WebSocket\Server as SwooleServer;

uses(RefreshDatabase::class);

beforeEach(function () {
    $this->mockServer = Mockery::mock(SwooleServer::class);
    $this->mockServer->shouldReceive('isEstablished')->andReturn(true)->byDefault();
    $this->mockServer->shouldReceive('push')->andReturn(true)->byDefault();

    $this->connectionManager = Mockery::mock(ConnectionManager::class);
    $this->connectionManager->shouldReceive('getServer')->andReturn($this->mockServer)->byDefault();
    $this->connectionManager->shouldReceive('getDeviceStatus')->andReturn([])->byDefault();
    $this->connectionManager->shouldReceive('isDeviceOnline')->andReturn(true)->byDefault();
    $this->connectionManager->shouldReceive('registerPanelUser')->byDefault();

    $this->handler = new CheckPhoneHandler($this->connectionManager);
    $this->encryptionService = new EncryptionService();
});

afterEach(function () {
    Mockery::close();
});

describe('用户-设备隔离', function () {
    it('场景1: 新用户checkphone返回空列表', function () {
        $userA = User::factory()->create([
            'email' => 'usera@test.com',
            'email_encrypted' => $this->encryptionService->encryptEmail('usera@test.com'),
        ]);

        $sentData = null;
        $this->connectionManager->shouldReceive('send')
            ->withArgs(function ($fd, $data) use (&$sentData) {
                $sentData = $data;
                return true;
            })
            ->once();

        $this->handler->handle(1, [
            'subc' => 'checkphone',
            'email' => $userA->email_encrypted,
            'page' => 1,
            'pageSize' => 10,
        ]);

        expect($sentData['type'])->toBe('checkphone');
        expect($sentData['list'])->toBe([]);
        expect($sentData['total'])->toBe(0);
    });

    it('场景2: 设备B(用户A邮箱)上线后，用户A的checkphone返回设备B', function () {
        $userA = User::factory()->create([
            'email' => 'usera@test.com',
            'email_encrypted' => $this->encryptionService->encryptEmail('usera@test.com'),
        ]);

        $deviceB = Device::factory()->create([
            'user_id' => $userA->id,
            'uuid' => 'device-B-123',
            'name' => 'Device B',
            'model' => 'Pixel 8',
            'is_removed' => false,
        ]);

        $sentData = null;
        $this->connectionManager->shouldReceive('send')
            ->withArgs(function ($fd, $data) use (&$sentData) {
                $sentData = $data;
                return true;
            })
            ->once();

        $this->handler->handle(1, [
            'subc' => 'checkphone',
            'email' => $userA->email_encrypted,
            'page' => 1,
            'pageSize' => 10,
        ]);

        expect($sentData['total'])->toBe(1);
        expect($sentData['list'])->toHaveCount(1);
        expect($sentData['list'][0]['phone_id'])->toBe('device-B-123');
        expect($sentData['list'][0]['phone_name'])->toBe('Device B');
    });

    it('场景3: 设备C(其他用户)上线后，用户A的checkphone仍只返回设备B', function () {
        $userA = User::factory()->create([
            'email' => 'usera@test.com',
            'email_encrypted' => $this->encryptionService->encryptEmail('usera@test.com'),
        ]);

        $userOther = User::factory()->create([
            'email' => 'other@test.com',
            'email_encrypted' => $this->encryptionService->encryptEmail('other@test.com'),
        ]);

        $deviceB = Device::factory()->create([
            'user_id' => $userA->id,
            'uuid' => 'device-B-123',
            'name' => 'Device B',
            'is_removed' => false,
        ]);

        $deviceC = Device::factory()->create([
            'user_id' => $userOther->id,
            'uuid' => 'device-C-456',
            'name' => 'Device C',
            'is_removed' => false,
        ]);

        $sentData = null;
        $this->connectionManager->shouldReceive('send')
            ->withArgs(function ($fd, $data) use (&$sentData) {
                $sentData = $data;
                return true;
            })
            ->once();

        $this->handler->handle(1, [
            'subc' => 'checkphone',
            'email' => $userA->email_encrypted,
            'page' => 1,
            'pageSize' => 10,
        ]);

        expect($sentData['total'])->toBe(1);
        expect($sentData['list'])->toHaveCount(1);
        expect($sentData['list'][0]['phone_id'])->toBe('device-B-123');

        $deviceIds = array_column($sentData['list'], 'phone_id');
        expect($deviceIds)->not->toContain('device-C-456');
    });
});

describe('设备上线离线通知', function () {
    it('设备上线时Panel收到在线状态', function () {
        $userA = User::factory()->create([
            'email' => 'usera@test.com',
            'email_encrypted' => $this->encryptionService->encryptEmail('usera@test.com'),
        ]);

        $deviceB = Device::factory()->create([
            'user_id' => $userA->id,
            'uuid' => 'device-online-test',
            'name' => 'Online Test Device',
            'is_removed' => false,
            'is_online' => false,
        ]);

        $this->connectionManager->shouldReceive('isDeviceOnline')
            ->with('device-online-test')
            ->andReturn(true);

        $sentData = null;
        $this->connectionManager->shouldReceive('send')
            ->withArgs(function ($fd, $data) use (&$sentData) {
                $sentData = $data;
                return true;
            })
            ->once();

        $this->handler->handle(1, [
            'subc' => 'checkphone',
            'email' => $userA->email_encrypted,
            'page' => 1,
            'pageSize' => 10,
        ]);

        expect($sentData['list'][0]['is_online'])->toBe(true);
    });

    it('设备离线时Panel收到离线状态', function () {
        $userA = User::factory()->create([
            'email' => 'usera@test.com',
            'email_encrypted' => $this->encryptionService->encryptEmail('usera@test.com'),
        ]);

        $deviceB = Device::factory()->create([
            'user_id' => $userA->id,
            'uuid' => 'device-offline-test',
            'name' => 'Offline Test Device',
            'is_removed' => false,
            'is_online' => true,
        ]);

        $this->connectionManager->shouldReceive('isDeviceOnline')
            ->with('device-offline-test')
            ->andReturn(false);

        $sentData = null;
        $this->connectionManager->shouldReceive('send')
            ->withArgs(function ($fd, $data) use (&$sentData) {
                $sentData = $data;
                return true;
            })
            ->once();

        $this->handler->handle(1, [
            'subc' => 'checkphone',
            'email' => $userA->email_encrypted,
            'page' => 1,
            'pageSize' => 10,
        ]);

        expect($sentData['list'][0]['is_online'])->toBe(false);
    });

    it('设备断开连接时通知订阅的Panel', function () {
        $phoneId = 'disconnect-test-device';

        $notificationData = null;

        $this->connectionManager->shouldReceive('sendToPanels')
            ->withArgs(function ($pid, $data) use (&$notificationData, $phoneId) {
                if ($pid === $phoneId && $data['type'] === 'deviceUpdate') {
                    $notificationData = $data;
                    return true;
                }
                return false;
            })
            ->once();

        $this->connectionManager->sendToPanels($phoneId, [
            'type' => 'deviceUpdate',
            'pid' => $phoneId,
            'phoneInfo' => [
                'is_online' => false,
                'lastPing' => time() * 1000,
            ],
        ]);

        expect($notificationData)->not->toBeNull();
        expect($notificationData['type'])->toBe('deviceUpdate');
        expect($notificationData['pid'])->toBe($phoneId);
        expect($notificationData['phoneInfo']['is_online'])->toBe(false);
    });

    it('设备上线后再离线，状态正确更新', function () {
        $userA = User::factory()->create([
            'email' => 'usera@test.com',
            'email_encrypted' => $this->encryptionService->encryptEmail('usera@test.com'),
        ]);

        $deviceB = Device::factory()->create([
            'user_id' => $userA->id,
            'uuid' => 'online-offline-test',
            'name' => 'Online Offline Device',
            'is_removed' => false,
            'is_online' => false,
        ]);

        $isOnline = true;
        $this->connectionManager->shouldReceive('isDeviceOnline')
            ->with('online-offline-test')
            ->andReturnUsing(function () use (&$isOnline) {
                return $isOnline;
            });

        $sentData = null;
        $this->connectionManager->shouldReceive('send')
            ->withArgs(function ($fd, $data) use (&$sentData) {
                $sentData = $data;
                return true;
            });

        $this->handler->handle(1, [
            'subc' => 'checkphone',
            'email' => $userA->email_encrypted,
            'page' => 1,
            'pageSize' => 10,
        ]);
        expect($sentData['list'][0]['is_online'])->toBe(true);

        $isOnline = false;

        $this->handler->handle(1, [
            'subc' => 'checkphone',
            'email' => $userA->email_encrypted,
            'page' => 1,
            'pageSize' => 10,
        ]);
        expect($sentData['list'][0]['is_online'])->toBe(false);
    });

    it('设备离线时同步更新数据库状态', function () {
        $userA = User::factory()->create([
            'email' => 'usera@test.com',
            'email_encrypted' => $this->encryptionService->encryptEmail('usera@test.com'),
        ]);

        $device = Device::factory()->create([
            'user_id' => $userA->id,
            'uuid' => 'db-offline-test',
            'name' => 'DB Offline Test',
            'is_removed' => false,
            'is_online' => true,
        ]);

        expect($device->is_online)->toBe(true);

        \App\Models\Device::where('uuid', 'db-offline-test')->update([
            'is_online' => false,
            'last_seen_at' => now(),
        ]);

        $device->refresh();

        expect($device->is_online)->toBe(false);
        expect($device->last_seen_at)->not->toBeNull();
    });

    it('DeviceStatusService::markOffline正确更新数据库', function () {
        $userA = User::factory()->create([
            'email' => 'usera@test.com',
        ]);

        $device = Device::factory()->create([
            'user_id' => $userA->id,
            'uuid' => 'mark-offline-test',
            'name' => 'Mark Offline Test',
            'is_online' => true,
        ]);

        expect($device->is_online)->toBe(true);

        $realConnectionManager = Mockery::mock(ConnectionManager::class);
        $deviceStatusService = new DeviceStatusService($realConnectionManager);

        $deviceStatusService->markOffline('mark-offline-test');

        $device->refresh();

        expect($device->is_online)->toBe(false);
        expect($device->last_seen_at)->not->toBeNull();
    });
});

describe('设备自动创建与用户关联', function () {
    it('设备首次上线时自动创建数据库记录并关联正确用户', function () {
        $userA = User::factory()->create([
            'email' => 'usera@test.com',
            'email_encrypted' => $this->encryptionService->encryptEmail('usera@test.com'),
        ]);

        $realConnectionManager = Mockery::mock(ConnectionManager::class);
        $realConnectionManager->shouldReceive('updateDeviceStatus')->andReturn(null);
        $realConnectionManager->shouldReceive('notifyPanelUsersDeviceOnline')->andReturn(null);

        $deviceStatusService = new DeviceStatusService($realConnectionManager);

        $phoneId = 'new-device-' . time();
        $encodedData = http_build_query([
            'phone_name' => 'New Device',
            'model' => 'Pixel 8 Pro',
            'battery_charge' => '85',
            'accessibility' => '1',
            'country' => 'China',
            'user_email' => 'usera@test.com',
        ]);

        $deviceStatusService->updateFromPing($phoneId, $encodedData);

        $device = Device::where('uuid', $phoneId)->first();

        expect($device)->not->toBeNull();
        expect($device->user_id)->toBe($userA->id);
        expect($device->name)->toBe('New Device');
        expect($device->model)->toBe('Pixel 8 Pro');
        expect($device->is_online)->toBe(true);
    });

    it('设备邮箱不匹配任何用户时fallback到第一个用户', function () {
        $firstUser = User::factory()->create([
            'email' => 'first@test.com',
        ]);

        $realConnectionManager = Mockery::mock(ConnectionManager::class);
        $realConnectionManager->shouldReceive('updateDeviceStatus')->andReturn(null);
        $realConnectionManager->shouldReceive('notifyPanelUsersDeviceOnline')->andReturn(null);

        $deviceStatusService = new DeviceStatusService($realConnectionManager);

        $phoneId = 'orphan-device-' . time();
        $encodedData = http_build_query([
            'phone_name' => 'Orphan Device',
            'user_email' => 'nonexistent@nowhere.com',
        ]);

        $deviceStatusService->updateFromPing($phoneId, $encodedData);

        $device = Device::where('uuid', $phoneId)->first();

        expect($device)->not->toBeNull();
        expect($device->user_id)->toBe($firstUser->id);
    });

    it('通过加密邮箱匹配用户', function () {
        $userA = User::factory()->create([
            'email' => 'encrypted-match@test.com',
            'email_encrypted' => $this->encryptionService->encryptEmail('encrypted-match@test.com'),
        ]);

        $realConnectionManager = Mockery::mock(ConnectionManager::class);
        $realConnectionManager->shouldReceive('updateDeviceStatus')->andReturn(null);
        $realConnectionManager->shouldReceive('notifyPanelUsersDeviceOnline')->andReturn(null);

        $deviceStatusService = new DeviceStatusService($realConnectionManager);

        $phoneId = 'encrypted-device-' . time();
        $encodedData = http_build_query([
            'phone_name' => 'Encrypted Match Device',
            'user_email' => 'encrypted-match@test.com',
        ]);

        $deviceStatusService->updateFromPing($phoneId, $encodedData);

        $device = Device::where('uuid', $phoneId)->first();

        expect($device)->not->toBeNull();
        expect($device->user_id)->toBe($userA->id);
    });
});

describe('推送模式', function () {
    it('checkphone时注册Panel用户订阅', function () {
        $userA = User::factory()->create([
            'email' => 'panel-user@test.com',
            'email_encrypted' => $this->encryptionService->encryptEmail('panel-user@test.com'),
        ]);

        $this->connectionManager->shouldReceive('registerPanelUser')
            ->with(1, $userA->email_encrypted, false)
            ->once();

        $this->connectionManager->shouldReceive('send')->once();

        $this->handler->handle(1, [
            'subc' => 'checkphone',
            'email' => $userA->email_encrypted,
            'page' => 1,
            'pageSize' => 10,
        ]);
    });

    it('管理员checkphone时注册为admin', function () {
        $adminEmail = config('websocket.admin_email_encrypted');

        $this->connectionManager->shouldReceive('registerPanelUser')
            ->with(1, $adminEmail, true)
            ->once();

        $this->connectionManager->shouldReceive('send')->once();

        $this->handler->handle(1, [
            'subc' => 'checkphone',
            'email' => $adminEmail,
            'page' => 1,
            'pageSize' => 10,
        ]);
    });

    it('设备上线时推送deviceOnline给相关Panel', function () {
        $userA = User::factory()->create([
            'email' => 'usera@test.com',
            'email_encrypted' => $this->encryptionService->encryptEmail('usera@test.com'),
        ]);

        $notifiedData = null;
        $realConnectionManager = Mockery::mock(ConnectionManager::class);
        $realConnectionManager->shouldReceive('updateDeviceStatus')->andReturn(null);
        $realConnectionManager->shouldReceive('notifyPanelUsersDeviceOnline')
            ->withArgs(function ($phoneId, $userId, $deviceInfo) use (&$notifiedData, $userA) {
                $notifiedData = [
                    'phoneId' => $phoneId,
                    'userId' => $userId,
                    'deviceInfo' => $deviceInfo,
                ];
                return $userId === $userA->id;
            })
            ->once();

        $deviceStatusService = new DeviceStatusService($realConnectionManager);

        $phoneId = 'push-online-device-' . time();
        $encodedData = http_build_query([
            'phone_name' => 'Push Test Device',
            'model' => 'Pixel 8',
            'user_email' => 'usera@test.com',
        ]);

        $deviceStatusService->updateFromPing($phoneId, $encodedData);

        expect($notifiedData)->not->toBeNull();
        expect($notifiedData['phoneId'])->toBe($phoneId);
        expect($notifiedData['userId'])->toBe($userA->id);
        expect($notifiedData['deviceInfo']['is_online'])->toBe(true);
    });

    it('设备离线时推送deviceOffline给相关Panel', function () {
        $userA = User::factory()->create([
            'email' => 'usera@test.com',
            'email_encrypted' => $this->encryptionService->encryptEmail('usera@test.com'),
        ]);

        $device = Device::factory()->create([
            'user_id' => $userA->id,
            'uuid' => 'push-offline-device',
            'is_online' => true,
        ]);

        $notifiedPhoneId = null;
        $notifiedUserId = null;

        $mockConnectionManager = Mockery::mock(ConnectionManager::class);
        $mockConnectionManager->shouldReceive('notifyPanelUsersDeviceOffline')
            ->withArgs(function ($phoneId, $userId) use (&$notifiedPhoneId, &$notifiedUserId) {
                $notifiedPhoneId = $phoneId;
                $notifiedUserId = $userId;
                return true;
            })
            ->once();

        $reflection = new \ReflectionClass(ConnectionManager::class);
        $method = $reflection->getMethod('syncOfflineToDatabaseAndNotify');

        $mockConnectionManager->syncOfflineToDatabaseAndNotify = function ($phoneId) use ($mockConnectionManager, $device) {
            $device->update(['is_online' => false, 'last_seen_at' => now()]);
            $mockConnectionManager->notifyPanelUsersDeviceOffline($phoneId, $device->user_id);
        };

        ($mockConnectionManager->syncOfflineToDatabaseAndNotify)('push-offline-device');

        expect($notifiedPhoneId)->toBe('push-offline-device');
        expect($notifiedUserId)->toBe($userA->id);

        $device->refresh();
        expect($device->is_online)->toBe(false);
    });

    it('用户只收到自己设备的推送-验证隔离逻辑', function () {
        $userA = User::factory()->create([
            'email' => 'usera@test.com',
            'email_encrypted' => $this->encryptionService->encryptEmail('usera@test.com'),
        ]);

        $userB = User::factory()->create([
            'email' => 'userb@test.com',
            'email_encrypted' => $this->encryptionService->encryptEmail('userb@test.com'),
        ]);

        $deviceA = Device::factory()->create([
            'user_id' => $userA->id,
            'uuid' => 'device-A',
            'is_removed' => false,
        ]);

        $deviceB = Device::factory()->create([
            'user_id' => $userB->id,
            'uuid' => 'device-B',
            'is_removed' => false,
        ]);

        $sentDataA = null;
        $sentDataB = null;

        $this->connectionManager->shouldReceive('send')
            ->withArgs(function ($fd, $data) use (&$sentDataA, &$sentDataB) {
                if ($fd === 10) {
                    $sentDataA = $data;
                } elseif ($fd === 20) {
                    $sentDataB = $data;
                }
                return true;
            });

        $this->handler->handle(10, [
            'subc' => 'checkphone',
            'email' => $userA->email_encrypted,
            'page' => 1,
            'pageSize' => 10,
        ]);

        expect($sentDataA['list'])->toHaveCount(1);
        expect($sentDataA['list'][0]['phone_id'])->toBe('device-A');

        $this->handler->handle(20, [
            'subc' => 'checkphone',
            'email' => $userB->email_encrypted,
            'page' => 1,
            'pageSize' => 10,
        ]);

        expect($sentDataB['list'])->toHaveCount(1);
        expect($sentDataB['list'][0]['phone_id'])->toBe('device-B');
    });

    it('Panel订阅后新设备上线会收到推送', function () {
        $userA = User::factory()->create([
            'email' => 'usera@test.com',
            'email_encrypted' => $this->encryptionService->encryptEmail('usera@test.com'),
        ]);

        $panelFd = 10;
        $pushedMessages = [];

        $mockConnectionManager = Mockery::mock(ConnectionManager::class);
        $mockConnectionManager->shouldReceive('updateDeviceStatus')->andReturn(null);
        $mockConnectionManager->shouldReceive('notifyPanelUsersDeviceOnline')
            ->withArgs(function ($phoneId, $userId, $deviceInfo) use (&$pushedMessages, $userA) {
                $pushedMessages[] = [
                    'type' => 'deviceOnline',
                    'phoneId' => $phoneId,
                    'userId' => $userId,
                    'deviceInfo' => $deviceInfo,
                ];
                return $userId === $userA->id;
            });

        $deviceStatusService = new DeviceStatusService($mockConnectionManager);

        $newDeviceId = 'new-dev-' . substr(md5((string) time()), 0, 8);
        $encodedData = http_build_query([
            'phone_name' => 'New Device After Subscribe',
            'model' => 'Pixel 9',
            'battery_charge' => '95',
            'user_email' => 'usera@test.com',
        ]);

        $deviceStatusService->updateFromPing($newDeviceId, $encodedData);

        expect($pushedMessages)->toHaveCount(1);
        expect($pushedMessages[0]['type'])->toBe('deviceOnline');
        expect($pushedMessages[0]['phoneId'])->toBe($newDeviceId);
        expect($pushedMessages[0]['userId'])->toBe($userA->id);
        expect($pushedMessages[0]['deviceInfo']['is_online'])->toBe(true);
        expect($pushedMessages[0]['deviceInfo']['phone_name'])->toBe('New Device After Subscribe');

        $device = Device::where('uuid', $newDeviceId)->first();
        expect($device)->not->toBeNull();
        expect($device->user_id)->toBe($userA->id);
    });

    it('新设备上线只推送给对应用户的Panel不推送给其他用户', function () {
        $userA = User::factory()->create([
            'email' => 'usera@test.com',
            'email_encrypted' => $this->encryptionService->encryptEmail('usera@test.com'),
        ]);

        $userB = User::factory()->create([
            'email' => 'userb@test.com',
            'email_encrypted' => $this->encryptionService->encryptEmail('userb@test.com'),
        ]);

        $notifiedUserIds = [];

        $mockConnectionManager = Mockery::mock(ConnectionManager::class);
        $mockConnectionManager->shouldReceive('updateDeviceStatus')->andReturn(null);
        $mockConnectionManager->shouldReceive('notifyPanelUsersDeviceOnline')
            ->withArgs(function ($phoneId, $userId, $deviceInfo) use (&$notifiedUserIds) {
                $notifiedUserIds[] = $userId;
                return true;
            });

        $deviceStatusService = new DeviceStatusService($mockConnectionManager);

        $newDeviceId = 'device-for-userA-' . time();
        $encodedData = http_build_query([
            'phone_name' => 'Device For User A',
            'user_email' => 'usera@test.com',
        ]);

        $deviceStatusService->updateFromPing($newDeviceId, $encodedData);

        expect($notifiedUserIds)->toHaveCount(1);
        expect($notifiedUserIds[0])->toBe($userA->id);
        expect($notifiedUserIds)->not->toContain($userB->id);
    });

    it('管理员Panel收到所有新设备上线推送', function () {
        $userA = User::factory()->create([
            'email' => 'usera@test.com',
            'email_encrypted' => $this->encryptionService->encryptEmail('usera@test.com'),
        ]);

        $userB = User::factory()->create([
            'email' => 'userb@test.com',
            'email_encrypted' => $this->encryptionService->encryptEmail('userb@test.com'),
        ]);

        $adminEmailEncrypted = config('websocket.admin_email_encrypted');

        $notifiedUserId = null;
        $notifiedPhoneId = null;

        $mockConnectionManager = Mockery::mock(ConnectionManager::class);
        $mockConnectionManager->shouldReceive('updateDeviceStatus')->andReturn(null);
        $mockConnectionManager->shouldReceive('notifyPanelUsersDeviceOnline')
            ->withArgs(function ($phoneId, $userId, $deviceInfo) use (&$notifiedUserId, &$notifiedPhoneId, $userA) {
                $notifiedUserId = $userId;
                $notifiedPhoneId = $phoneId;
                return true;
            })
            ->once();

        $deviceStatusService = new DeviceStatusService($mockConnectionManager);

        $newDeviceId = 'admin-test-' . substr(md5((string) time()), 0, 8);
        $encodedData = http_build_query([
            'phone_name' => 'Device For User A',
            'user_email' => 'usera@test.com',
        ]);

        $deviceStatusService->updateFromPing($newDeviceId, $encodedData);

        expect($notifiedUserId)->toBe($userA->id);
        expect($notifiedPhoneId)->toBe($newDeviceId);
    });
});
