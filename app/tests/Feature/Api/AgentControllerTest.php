<?php

declare(strict_types=1);

namespace Tests\Feature\Api;

use App\Models\Device;
use App\Models\User;
use Illuminate\Foundation\Testing\RefreshDatabase;
use Tests\TestCase;

class AgentControllerTest extends TestCase
{
    use RefreshDatabase;

    public function test_query_returns_config_for_valid_device(): void
    {
        config([
            'frpc.server_addr' => '203.0.113.10',
            'frpc.server_port' => 7000,
            'frpc.auth_token' => 'test-token',
            'frpc.port_range_start' => 20000,
            'frpc.port_range_end' => 30000,
        ]);

        $user = User::factory()->create();
        $device = Device::create([
            'uuid' => 'device-uuid-001',
            'user_id' => $user->id,
            'name' => 'Test Phone',
        ]);

        $response = $this->postJson('/api/agent/query.json', [
            'deviceId' => (string) $device->id,
        ]);

        $response->assertOk()
            ->assertJson([
                'success' => true,
            ])
            ->assertJsonStructure([
                'success',
                'data' => [
                    'id',
                    'deviceId',
                    'fileName',
                    'targetFileUrl',
                    'fileSize',
                ],
            ]);

        $this->assertNotNull($device->fresh()->frpc_base_port);
        $this->assertNotNull($device->fresh()->frpc_config_generated_at);
    }

    public function test_query_returns_error_for_missing_device(): void
    {
        $response = $this->postJson('/api/agent/query.json', [
            'deviceId' => '999999',
        ]);

        $response->assertOk()
            ->assertJson([
                'success' => false,
            ]);
    }

    public function test_query_returns_error_for_missing_device_id(): void
    {
        $response = $this->postJson('/api/agent/query.json', []);

        $response->assertStatus(422);
    }

    public function test_query_reuses_existing_config(): void
    {
        config([
            'frpc.server_addr' => '203.0.113.10',
            'frpc.server_port' => 7000,
            'frpc.auth_token' => 'test-token',
            'frpc.port_range_start' => 20000,
            'frpc.port_range_end' => 30000,
        ]);

        $user = User::factory()->create();
        $device = Device::create([
            'uuid' => 'device-uuid-002',
            'user_id' => $user->id,
            'name' => 'Test Phone 2',
            'frpc_base_port' => 20000,
        ]);

        // 第一次请求
        $this->postJson('/api/agent/query.json', ['deviceId' => (string) $device->id]);
        // 第二次请求
        $response = $this->postJson('/api/agent/query.json', ['deviceId' => (string) $device->id]);

        $response->assertOk()->assertJson(['success' => true]);
        // 端口不应改变
        $this->assertEquals(20000, $device->fresh()->frpc_base_port);
    }
}
