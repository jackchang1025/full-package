<?php

declare(strict_types=1);

namespace Tests\Unit\Services;

use App\Models\Device;
use App\Models\User;
use App\Services\FrpcConfigService;
use Illuminate\Foundation\Testing\LazilyRefreshDatabase;
use Tests\TestCase;

class FrpcConfigServiceTest extends TestCase
{
    use LazilyRefreshDatabase;

    private FrpcConfigService $service;

    protected function setUp(): void
    {
        parent::setUp();

        config([
            'frpc.server_addr' => '203.0.113.10',
            'frpc.server_port' => 7000,
            'frpc.auth_token' => 'test-token-abc',
            'frpc.port_range_start' => 20000,
            'frpc.port_range_end' => 30000,
        ]);

        $this->service = new FrpcConfigService();
    }

    public function test_allocate_port_assigns_first_available(): void
    {
        $user = User::factory()->create();
        $device = Device::create([
            'uuid' => 'test-uuid-001',
            'user_id' => $user->id,
            'name' => 'Test Device',
        ]);

        $port = $this->service->allocatePort($device);

        $this->assertEquals(20000, $port);
        $this->assertEquals(20000, $device->fresh()->frpc_base_port);
    }

    public function test_allocate_port_skips_occupied(): void
    {
        $user = User::factory()->create();

        Device::create([
            'uuid' => 'test-uuid-001',
            'user_id' => $user->id,
            'name' => 'Device 1',
            'frpc_base_port' => 20000,
        ]);

        $device2 = Device::create([
            'uuid' => 'test-uuid-002',
            'user_id' => $user->id,
            'name' => 'Device 2',
        ]);

        $port = $this->service->allocatePort($device2);

        $this->assertEquals(20003, $port);
    }

    public function test_allocate_port_reuses_existing(): void
    {
        $user = User::factory()->create();
        $device = Device::create([
            'uuid' => 'test-uuid-001',
            'user_id' => $user->id,
            'name' => 'Test Device',
            'frpc_base_port' => 20006,
        ]);

        $port = $this->service->allocatePort($device);

        $this->assertEquals(20006, $port);
    }

    public function test_generate_config_produces_valid_ini(): void
    {
        $user = User::factory()->create();
        $device = Device::create([
            'uuid' => 'test-uuid-001',
            'user_id' => $user->id,
            'name' => 'Test Device',
            'frpc_base_port' => 20000,
        ]);

        $ini = $this->service->generateConfig($device);

        $this->assertStringContainsString('[common]', $ini);
        $this->assertStringContainsString('server_addr = 203.0.113.10', $ini);
        $this->assertStringContainsString('server_port = 7000', $ini);
        $this->assertStringContainsString('token = test-token-abc', $ini);
        $this->assertStringContainsString('[http-api-' . $device->id . ']', $ini);
        $this->assertStringContainsString('local_port = 7910', $ini);
        $this->assertStringContainsString('remote_port = 20000', $ini);
        $this->assertStringContainsString('[websocket-' . $device->id . ']', $ini);
        $this->assertStringContainsString('local_port = 7900', $ini);
        $this->assertStringContainsString('remote_port = 20001', $ini);
        $this->assertStringContainsString('[wifi-debug-port]', $ini);
        $this->assertStringContainsString('local_port = 5555', $ini);
        $this->assertStringContainsString('remote_port = 20002', $ini);
    }
}
