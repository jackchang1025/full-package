<?php

declare(strict_types=1);

namespace Tests\Unit\WebSocket;

use App\Models\Device;
use App\Models\User;
use App\WebSocket\ConnectionManager;
use App\WebSocket\Services\DatabaseReconnector;
use App\WebSocket\Services\DeviceListService;
use Illuminate\Foundation\Testing\RefreshDatabase;
use Illuminate\Support\Str;
use Mockery;
use Tests\TestCase;

/**
 * 设备列表服务单元测试
 *
 * 重点验证：设备列表的 is_online 以 ConnectionManager::isDeviceOnline 为准，
 * 与控制页 join 返回的 serverToPhone 一致，避免「列表显示在线、控制页 CLOSED」。
 */
class DeviceListServiceTest extends TestCase
{
    use RefreshDatabase;

    protected function tearDown(): void
    {
        Mockery::close();
        parent::tearDown();
    }

    private function createService(ConnectionManager $connectionManager): DeviceListService
    {
        $dr = Mockery::mock(DatabaseReconnector::class);
        $dr->shouldReceive('reconnect')->andReturnNull();

        return new DeviceListService($connectionManager, $dr);
    }

    /** 设备在 DB 为 is_online true，但 ConnectionManager 认为离线 → 列表应显示离线 */
    public function test_device_list_is_online_false_when_connection_manager_says_offline(): void
    {
        $user = User::factory()->create();
        $uuid = Str::uuid()->toString();
        Device::factory()->create([
            'uuid' => $uuid,
            'user_id' => $user->id,
            'name' => 'Device DB Online',
            'is_online' => true,
            'is_removed' => false,
        ]);

        $cm = Mockery::mock(ConnectionManager::class);
        $cm->shouldReceive('getDeviceStatus')->with($uuid)->andReturn([]);
        $cm->shouldReceive('isDeviceOnline')->with($uuid)->andReturn(false);

        $service = $this->createService($cm);
        $list = $service->getDeviceListForUser($user->id);

        $this->assertCount(1, $list);
        $this->assertSame($uuid, $list[0]['uuid']);
        $this->assertFalse($list[0]['is_online'], '列表 is_online 应以 ConnectionManager 为准，CM 离线则显示离线');
    }

    /** 设备在 DB 为 is_online false，但 ConnectionManager 认为在线 → 列表应显示在线 */
    public function test_device_list_is_online_true_when_connection_manager_says_online(): void
    {
        $user = User::factory()->create();
        $uuid = Str::uuid()->toString();
        Device::factory()->create([
            'uuid' => $uuid,
            'user_id' => $user->id,
            'name' => 'Device DB Offline',
            'is_online' => false,
            'is_removed' => false,
        ]);

        $cm = Mockery::mock(ConnectionManager::class);
        $cm->shouldReceive('getDeviceStatus')->with($uuid)->andReturn([]);
        $cm->shouldReceive('isDeviceOnline')->with($uuid)->andReturn(true);

        $service = $this->createService($cm);
        $list = $service->getDeviceListForUser($user->id);

        $this->assertCount(1, $list);
        $this->assertSame($uuid, $list[0]['uuid']);
        $this->assertTrue($list[0]['is_online'], '列表 is_online 应以 ConnectionManager 为准，CM 在线则显示在线');
    }

    /** 多设备：部分 CM 在线、部分离线，列表每台 is_online 与 CM 一致 */
    public function test_device_list_multiple_devices_each_is_online_from_connection_manager(): void
    {
        $user = User::factory()->create();
        $uuidOnline = Str::uuid()->toString();
        $uuidOffline = Str::uuid()->toString();
        Device::factory()->create([
            'uuid' => $uuidOnline,
            'user_id' => $user->id,
            'name' => 'Online Device',
            'is_online' => false,
        ]);
        Device::factory()->create([
            'uuid' => $uuidOffline,
            'user_id' => $user->id,
            'name' => 'Offline Device',
            'is_online' => true,
        ]);

        $cm = Mockery::mock(ConnectionManager::class);
        $cm->shouldReceive('getDeviceStatus')->with($uuidOnline)->andReturn([]);
        $cm->shouldReceive('getDeviceStatus')->with($uuidOffline)->andReturn([]);
        $cm->shouldReceive('isDeviceOnline')->with($uuidOnline)->andReturn(true);
        $cm->shouldReceive('isDeviceOnline')->with($uuidOffline)->andReturn(false);

        $service = $this->createService($cm);
        $list = $service->getDeviceListForUser($user->id);

        $this->assertCount(2, $list);
        $byUuid = collect($list)->keyBy('uuid');
        $this->assertTrue($byUuid->get($uuidOnline)['is_online'], 'CM 在线的设备应在列表中为 true');
        $this->assertFalse($byUuid->get($uuidOffline)['is_online'], 'CM 离线的设备应在列表中为 false');
    }

    /** 列表包含 uuid、name、user 等字段，且 is_online 来自 CM */
    public function test_device_list_structure_and_is_online_key(): void
    {
        $user = User::factory()->create();
        $uuid = Str::uuid()->toString();
        Device::factory()->create([
            'uuid' => $uuid,
            'user_id' => $user->id,
            'name' => 'Struct Test',
            'is_removed' => false,
        ]);

        $cm = Mockery::mock(ConnectionManager::class);
        $cm->shouldReceive('getDeviceStatus')->andReturn([]);
        $cm->shouldReceive('isDeviceOnline')->with($uuid)->andReturn(true);

        $service = $this->createService($cm);
        $list = $service->getDeviceListForUser($user->id);

        $this->assertCount(1, $list);
        $d = $list[0];
        $this->assertArrayHasKey('uuid', $d);
        $this->assertArrayHasKey('name', $d);
        $this->assertArrayHasKey('is_online', $d);
        $this->assertArrayHasKey('user', $d);
        $this->assertTrue($d['is_online']);
    }
}
