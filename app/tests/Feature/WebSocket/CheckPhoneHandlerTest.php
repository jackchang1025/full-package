<?php

declare(strict_types=1);

use App\Models\Device;
use App\Models\User;
use App\WebSocket\ConnectionManager;
use App\WebSocket\Handlers\CheckPhoneHandler;
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
    $this->connectionManager->shouldReceive('isDeviceOnline')->andReturn(false)->byDefault();
    $this->connectionManager->shouldReceive('registerPanelUser')->byDefault();

    $this->handler = new CheckPhoneHandler($this->connectionManager);
});

afterEach(function () {
    Mockery::close();
});

describe('CheckPhoneHandler', function () {
    describe('handle()', function () {
        it('returns empty list when no devices exist', function () {
            $fd = 1;
            $sentData = null;

            $this->connectionManager->shouldReceive('send')
                ->withArgs(function ($targetFd, $data) use (&$sentData, $fd) {
                    $sentData = $data;
                    return $targetFd === $fd;
                })
                ->once();

            $this->handler->handle($fd, [
                'subc' => 'checkphone',
                'email' => 'test@example.com',
                'page' => 1,
                'pageSize' => 10,
            ]);

            expect($sentData['type'])->toBe('checkphone');
            expect($sentData['list'])->toBe([]);
            expect($sentData['total'])->toBe(0);
            expect($sentData['page'])->toBe(1);
            expect($sentData['pageSize'])->toBe(10);
        });

        it('returns devices for user', function () {
            $user = User::factory()->create([
                'email' => 'user@example.com',
                'email_encrypted' => 'encrypted-email',
            ]);

            $device = Device::factory()->create([
                'user_id' => $user->id,
                'uuid' => 'device-uuid-123',
                'name' => 'Test Device',
                'model' => 'Pixel 8',
                'android_version' => '14',
                'battery_level' => 85,
                'has_accessibility' => true,
                'country' => 'China',
                'is_removed' => false,
            ]);

            $fd = 1;
            $sentData = null;

            $this->connectionManager->shouldReceive('getDeviceStatus')
                ->with('device-uuid-123')
                ->andReturn(['last_ping' => 1706745600]);
            $this->connectionManager->shouldReceive('isDeviceOnline')
                ->with('device-uuid-123')
                ->andReturn(true);
            $this->connectionManager->shouldReceive('send')
                ->withArgs(function ($targetFd, $data) use (&$sentData) {
                    $sentData = $data;
                    return true;
                })
                ->once();

            $this->handler->handle($fd, [
                'subc' => 'checkphone',
                'email' => 'encrypted-email',
                'page' => 1,
                'pageSize' => 10,
            ]);

            expect($sentData['type'])->toBe('checkphone');
            expect($sentData['total'])->toBe(1);
            expect($sentData['list'])->toHaveCount(1);
            expect($sentData['list'][0]['phone_id'])->toBe('device-uuid-123');
            expect($sentData['list'][0]['phone_name'])->toBe('Test Device');
            expect($sentData['list'][0]['model'])->toBe('Pixel 8');
            expect($sentData['list'][0]['is_online'])->toBe(true);
        });

        it('excludes removed devices', function () {
            $user = User::factory()->create(['email_encrypted' => 'test-email']);

            Device::factory()->create([
                'user_id' => $user->id,
                'uuid' => 'active-device',
                'is_removed' => false,
            ]);

            Device::factory()->create([
                'user_id' => $user->id,
                'uuid' => 'removed-device',
                'is_removed' => true,
            ]);

            $fd = 1;
            $sentData = null;

            $this->connectionManager->shouldReceive('send')
                ->withArgs(function ($targetFd, $data) use (&$sentData) {
                    $sentData = $data;
                    return true;
                })
                ->once();

            $this->handler->handle($fd, [
                'subc' => 'checkphone',
                'email' => 'test-email',
                'page' => 1,
                'pageSize' => 10,
            ]);

            expect($sentData['total'])->toBe(1);
            expect($sentData['list'][0]['phone_id'])->toBe('active-device');
        });

        it('paginates results correctly', function () {
            $user = User::factory()->create(['email_encrypted' => 'test-email']);

            for ($i = 1; $i <= 25; $i++) {
                Device::factory()->create([
                    'user_id' => $user->id,
                    'uuid' => "device-{$i}",
                    'is_removed' => false,
                    'last_seen_at' => now()->subMinutes($i),
                ]);
            }

            $fd = 1;
            $sentData = null;

            $this->connectionManager->shouldReceive('send')
                ->withArgs(function ($targetFd, $data) use (&$sentData) {
                    $sentData = $data;
                    return true;
                })
                ->once();

            $this->handler->handle($fd, [
                'subc' => 'checkphone',
                'email' => 'test-email',
                'page' => 2,
                'pageSize' => 10,
            ]);

            expect($sentData['total'])->toBe(25);
            expect($sentData['pageCount'])->toBe(3);
            expect($sentData['page'])->toBe(2);
            expect($sentData['list'])->toHaveCount(10);
        });

        it('limits page size to 100', function () {
            $fd = 1;
            $sentData = null;

            $this->connectionManager->shouldReceive('send')
                ->withArgs(function ($targetFd, $data) use (&$sentData) {
                    $sentData = $data;
                    return true;
                })
                ->once();

            $this->handler->handle($fd, [
                'subc' => 'checkphone',
                'email' => 'test@example.com',
                'page' => 1,
                'pageSize' => 500,
            ]);

            expect($sentData['pageSize'])->toBe(100);
        });

        it('ensures minimum page is 1', function () {
            $fd = 1;
            $sentData = null;

            $this->connectionManager->shouldReceive('send')
                ->withArgs(function ($targetFd, $data) use (&$sentData) {
                    $sentData = $data;
                    return true;
                })
                ->once();

            $this->handler->handle($fd, [
                'subc' => 'checkphone',
                'email' => 'test@example.com',
                'page' => -5,
                'pageSize' => 10,
            ]);

            expect($sentData['page'])->toBe(1);
        });
    });

    describe('filters', function () {
        beforeEach(function () {
            $this->user = User::factory()->create([
                'email' => 'filter-test@example.com',
                'email_encrypted' => 'filter-test-encrypted',
            ]);
        });

        it('filters by phone name', function () {
            Device::factory()->create([
                'user_id' => $this->user->id,
                'uuid' => 'device-1',
                'name' => 'Work Phone',
                'is_removed' => false,
            ]);

            Device::factory()->create([
                'user_id' => $this->user->id,
                'uuid' => 'device-2',
                'name' => 'Personal Phone',
                'is_removed' => false,
            ]);

            $fd = 1;
            $sentData = null;

            $this->connectionManager->shouldReceive('send')
                ->withArgs(function ($targetFd, $data) use (&$sentData) {
                    $sentData = $data;
                    return true;
                })
                ->once();

            $this->handler->handle($fd, [
                'subc' => 'checkphone',
                'email' => 'filter-test-encrypted',
                'page' => 1,
                'pageSize' => 10,
                'filters' => ['phone_name' => 'Work'],
            ]);

            expect($sentData['total'])->toBe(1);
            expect($sentData['list'][0]['phone_name'])->toBe('Work Phone');
        });

        it('filters by country', function () {
            Device::factory()->create([
                'user_id' => $this->user->id,
                'uuid' => 'device-cn',
                'country' => 'China',
                'is_removed' => false,
            ]);

            Device::factory()->create([
                'user_id' => $this->user->id,
                'uuid' => 'device-us',
                'country' => 'USA',
                'is_removed' => false,
            ]);

            $fd = 1;
            $sentData = null;

            $this->connectionManager->shouldReceive('send')
                ->withArgs(function ($targetFd, $data) use (&$sentData) {
                    $sentData = $data;
                    return true;
                })
                ->once();

            $this->handler->handle($fd, [
                'subc' => 'checkphone',
                'email' => 'filter-test-encrypted',
                'page' => 1,
                'pageSize' => 10,
                'filters' => ['country' => 'China'],
            ]);

            expect($sentData['total'])->toBe(1);
            expect($sentData['list'][0]['phone_id'])->toBe('device-cn');
        });

        it('filters by model', function () {
            Device::factory()->create([
                'user_id' => $this->user->id,
                'uuid' => 'device-pixel',
                'model' => 'Pixel 8 Pro',
                'is_removed' => false,
            ]);

            Device::factory()->create([
                'user_id' => $this->user->id,
                'uuid' => 'device-samsung',
                'model' => 'Samsung S24',
                'is_removed' => false,
            ]);

            $fd = 1;
            $sentData = null;

            $this->connectionManager->shouldReceive('send')
                ->withArgs(function ($targetFd, $data) use (&$sentData) {
                    $sentData = $data;
                    return true;
                })
                ->once();

            $this->handler->handle($fd, [
                'subc' => 'checkphone',
                'email' => 'filter-test-encrypted',
                'page' => 1,
                'pageSize' => 10,
                'filters' => ['model' => 'Pixel'],
            ]);

            expect($sentData['total'])->toBe(1);
            expect($sentData['list'][0]['model'])->toBe('Pixel 8 Pro');
        });

        it('filters by accessibility', function () {
            Device::factory()->create([
                'user_id' => $this->user->id,
                'uuid' => 'device-acc',
                'has_accessibility' => true,
                'is_removed' => false,
            ]);

            Device::factory()->create([
                'user_id' => $this->user->id,
                'uuid' => 'device-no-acc',
                'has_accessibility' => false,
                'is_removed' => false,
            ]);

            $fd = 1;
            $sentData = null;

            $this->connectionManager->shouldReceive('send')
                ->withArgs(function ($targetFd, $data) use (&$sentData) {
                    $sentData = $data;
                    return true;
                })
                ->once();

            $this->handler->handle($fd, [
                'subc' => 'checkphone',
                'email' => 'filter-test-encrypted',
                'page' => 1,
                'pageSize' => 10,
                'filters' => ['accessibility' => '1'],
            ]);

            expect($sentData['total'])->toBe(1);
            expect($sentData['list'][0]['phone_id'])->toBe('device-acc');
        });
    });

    describe('response format', function () {
        it('includes all required fields in device list', function () {
            $user = User::factory()->create(['email_encrypted' => 'format-test']);

            Device::factory()->create([
                'user_id' => $user->id,
                'uuid' => 'format-device',
                'name' => 'Format Test',
                'model' => 'Test Model',
                'android_version' => '14',
                'battery_level' => 75,
                'has_accessibility' => true,
                'country' => 'Japan',
                'installed_at' => '2026-01-15 10:30:00',
                'is_removed' => false,
            ]);

            $fd = 1;
            $sentData = null;

            $this->connectionManager->shouldReceive('getDeviceStatus')
                ->andReturn(['last_ping' => 1706745600, 'user_email' => 'encrypted-user']);
            $this->connectionManager->shouldReceive('isDeviceOnline')->andReturn(true);
            $this->connectionManager->shouldReceive('send')
                ->withArgs(function ($targetFd, $data) use (&$sentData) {
                    $sentData = $data;
                    return true;
                })
                ->once();

            $this->handler->handle($fd, [
                'subc' => 'checkphone',
                'email' => 'format-test',
                'page' => 1,
                'pageSize' => 10,
            ]);

            $device = $sentData['list'][0];

            expect($device)->toHaveKeys([
                'phone_id',
                'phone_name',
                'model',
                'android_version',
                'battery_charge',
                'accessibility',
                'country',
                'user_email',
                'install_date',
                'is_online',
                'lastPing',
            ]);

            expect($device['phone_id'])->toBe('format-device');
            expect($device['phone_name'])->toBe('Format Test');
            expect($device['model'])->toBe('Test Model');
            expect($device['android_version'])->toBe('14');
            expect($device['battery_charge'])->toBe(75);
            expect($device['accessibility'])->toBe('1');
            expect($device['country'])->toBe('Japan');
            expect($device['is_online'])->toBe(true);
            expect($device['lastPing'])->toBe(1706745600000);
        });

        it('includes pagination metadata', function () {
            $fd = 1;
            $sentData = null;

            $this->connectionManager->shouldReceive('send')
                ->withArgs(function ($targetFd, $data) use (&$sentData) {
                    $sentData = $data;
                    return true;
                })
                ->once();

            $this->handler->handle($fd, [
                'subc' => 'checkphone',
                'email' => 'test@example.com',
                'page' => 1,
                'pageSize' => 10,
            ]);

            expect($sentData)->toHaveKeys([
                'type',
                'list',
                'total',
                'pageCount',
                'page',
                'pageSize',
                'fileLastModified',
            ]);
        });
    });
});
