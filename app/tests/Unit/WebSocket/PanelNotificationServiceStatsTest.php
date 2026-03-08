<?php

declare(strict_types=1);

namespace Tests\Unit\WebSocket;

use App\Models\Device;
use App\Models\User;
use App\WebSocket\ConnectionManager;
use App\WebSocket\Services\DatabaseReconnector;
use App\WebSocket\Services\PanelNotificationService;
use Illuminate\Foundation\Testing\RefreshDatabase;
use Illuminate\Support\Str;
use Mockery;
use Tests\TestCase;

/**
 * PanelNotificationService::getDeviceStats 单元测试
 *
 * 重点验证：stats.online/offline 以 ConnectionManager::isDeviceOnline 为准，
 * 与设备列表 is_online 及控制页 serverToPhone 一致。
 */
class PanelNotificationServiceStatsTest extends TestCase
{
    use RefreshDatabase;

    protected function tearDown(): void
    {
        Mockery::close();
        parent::tearDown();
    }

    private function createService(ConnectionManager $connectionManager): PanelNotificationService
    {
        $dr = Mockery::mock(DatabaseReconnector::class);
        $dr->shouldReceive('reconnect')->andReturnNull();

        return new PanelNotificationService($connectionManager, $dr);
    }

    /** 三台设备仅一台在 CM 在线 → total=3, online=1, offline=2 */
    public function test_get_device_stats_online_count_from_connection_manager(): void
    {
        $user = User::factory()->create();
        $uuid1 = Str::uuid()->toString();
        $uuid2 = Str::uuid()->toString();
        $uuid3 = Str::uuid()->toString();
        Device::factory()->create(['uuid' => $uuid1, 'user_id' => $user->id, 'is_removed' => false]);
        Device::factory()->create(['uuid' => $uuid2, 'user_id' => $user->id, 'is_removed' => false]);
        Device::factory()->create(['uuid' => $uuid3, 'user_id' => $user->id, 'is_removed' => false]);

        $cm = Mockery::mock(ConnectionManager::class);
        $cm->shouldReceive('isDeviceOnline')->with($uuid1)->andReturn(true);
        $cm->shouldReceive('isDeviceOnline')->with($uuid2)->andReturn(false);
        $cm->shouldReceive('isDeviceOnline')->with($uuid3)->andReturn(false);

        $service = $this->createService($cm);
        $stats = $service->getDeviceStats($user->id);

        $this->assertSame(3, $stats['total']);
        $this->assertSame(1, $stats['online'], 'online 应以 ConnectionManager 在线数为准');
        $this->assertSame(2, $stats['offline']);
        $this->assertSame($stats['total'], $stats['online'] + $stats['offline']);
    }

    /** 两台设备在 CM 均在线 → online=2, offline=0 */
    public function test_get_device_stats_all_online_from_connection_manager(): void
    {
        $user = User::factory()->create();
        $uuid1 = Str::uuid()->toString();
        $uuid2 = Str::uuid()->toString();
        Device::factory()->create(['uuid' => $uuid1, 'user_id' => $user->id, 'is_removed' => false]);
        Device::factory()->create(['uuid' => $uuid2, 'user_id' => $user->id, 'is_removed' => false]);

        $cm = Mockery::mock(ConnectionManager::class);
        $cm->shouldReceive('isDeviceOnline')->with($uuid1)->andReturn(true);
        $cm->shouldReceive('isDeviceOnline')->with($uuid2)->andReturn(true);

        $service = $this->createService($cm);
        $stats = $service->getDeviceStats($user->id);

        $this->assertSame(2, $stats['total']);
        $this->assertSame(2, $stats['online']);
        $this->assertSame(0, $stats['offline']);
    }

    /** 管理员 userId=null：统计全部设备，在线数仍以 CM 为准 */
    public function test_get_device_stats_admin_counts_all_devices_online_from_connection_manager(): void
    {
        $userA = User::factory()->create();
        $userB = User::factory()->create();
        $uuidA = Str::uuid()->toString();
        $uuidB = Str::uuid()->toString();
        Device::factory()->create(['uuid' => $uuidA, 'user_id' => $userA->id, 'is_removed' => false]);
        Device::factory()->create(['uuid' => $uuidB, 'user_id' => $userB->id, 'is_removed' => false]);

        $cm = Mockery::mock(ConnectionManager::class);
        $cm->shouldReceive('isDeviceOnline')->with($uuidA)->andReturn(true);
        $cm->shouldReceive('isDeviceOnline')->with($uuidB)->andReturn(false);

        $service = $this->createService($cm);
        $stats = $service->getDeviceStats(null);

        $this->assertSame(2, $stats['total']);
        $this->assertSame(1, $stats['online']);
        $this->assertSame(1, $stats['offline']);
    }

    /** 无设备时 total=0, online=0, offline=0 */
    public function test_get_device_stats_no_devices(): void
    {
        $user = User::factory()->create();

        $cm = Mockery::mock(ConnectionManager::class);
        $cm->shouldNotReceive('isDeviceOnline');

        $service = $this->createService($cm);
        $stats = $service->getDeviceStats($user->id);

        $this->assertSame(0, $stats['total']);
        $this->assertSame(0, $stats['online']);
        $this->assertSame(0, $stats['offline']);
    }

    /** DB 中 is_online 为 true 但 CM 离线 → stats.online 仍为 0（以 CM 为准） */
    public function test_get_device_stats_ignores_database_is_online_when_cm_offline(): void
    {
        $user = User::factory()->create();
        $uuid = Str::uuid()->toString();
        Device::factory()->online()->create([
            'uuid' => $uuid,
            'user_id' => $user->id,
            'is_removed' => false,
        ]);

        $cm = Mockery::mock(ConnectionManager::class);
        $cm->shouldReceive('isDeviceOnline')->with($uuid)->andReturn(false);

        $service = $this->createService($cm);
        $stats = $service->getDeviceStats($user->id);

        $this->assertSame(1, $stats['total']);
        $this->assertSame(0, $stats['online'], '应以 ConnectionManager 为准，DB is_online 不影响 stats');
        $this->assertSame(1, $stats['offline']);
    }
}
