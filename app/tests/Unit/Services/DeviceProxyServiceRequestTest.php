<?php

declare(strict_types=1);

namespace Tests\Unit\Services;

use App\Models\Device;
use App\Services\DeviceProxyService;
use Illuminate\Foundation\Testing\RefreshDatabase;
use Illuminate\Support\Facades\Http;
use Tests\TestCase;

class DeviceProxyServiceRequestTest extends TestCase
{
    use RefreshDatabase;

    protected function setUp(): void
    {
        parent::setUp();
        config([
            'frpc.proxy_host' => 'frps',
            'frpc.port_range_start' => 20000,
            'frpc.port_range_end' => 30000,
        ]);
    }

    public function test_request_get_forwards_to_frpc_base_port_with_query(): void
    {
        Http::fake([
            'frps:20000/*' => Http::response(
                ['code' => 200, 'success' => true, 'data' => true],
                200,
            ),
        ]);

        $device = Device::factory()->create(['frpc_base_port' => 20000]);
        $service = new DeviceProxyService;

        $response = $service->request($device, 'GET', '/global/setText', ['text' => 'hello']);

        $this->assertTrue($response->ok());
        $this->assertSame(200, $response->status);
        Http::assertSent(function ($req) {
            return $req->url() === 'http://frps:20000/global/setText?text=hello'
                && $req->method() === 'GET';
        });
    }

    public function test_request_post_forwards_json_body(): void
    {
        Http::fake([
            'frps:20000/*' => Http::response(['code' => 200, 'success' => true], 200),
        ]);

        $device = Device::factory()->create(['frpc_base_port' => 20000]);
        $service = new DeviceProxyService;

        $response = $service->request(
            $device,
            'POST',
            '/global/action',
            [],
            ['actionName' => 'back'],
        );

        $this->assertTrue($response->ok());
        Http::assertSent(function ($req) {
            return $req->url() === 'http://frps:20000/global/action'
                && $req->method() === 'POST'
                && $req->data() === ['actionName' => 'back'];
        });
    }

    public function test_request_returns_no_tunnel_when_port_not_allocated(): void
    {
        $device = Device::factory()->create(['frpc_base_port' => null]);
        $service = new DeviceProxyService;

        $response = $service->request($device, 'GET', '/info');

        $this->assertFalse($response->ok());
        $this->assertStringContainsString('no frpc tunnel', strtolower($response->error ?? ''));
    }

    public function test_request_rejects_unsupported_method(): void
    {
        $device = Device::factory()->create(['frpc_base_port' => 20000]);
        $service = new DeviceProxyService;

        $this->expectException(\InvalidArgumentException::class);

        $service->request($device, 'DELETE', '/info');
    }

    public function test_request_rejects_port_outside_range(): void
    {
        $device = Device::factory()->create(['frpc_base_port' => 6379]);
        $service = new DeviceProxyService;

        $response = $service->request($device, 'GET', '/info');

        $this->assertFalse($response->ok());
        $this->assertSame(0, $response->status);
        $this->assertStringContainsString('out of range', strtolower($response->error ?? ''));
    }

    public function test_request_accepts_port_at_range_boundaries(): void
    {
        Http::fake(['*' => Http::response(['code' => 200, 'success' => true], 200)]);

        $startDevice = Device::factory()->create(['frpc_base_port' => 20000]);
        $endDevice   = Device::factory()->create(['frpc_base_port' => 30000]);

        $service = new DeviceProxyService;

        $this->assertTrue($service->request($startDevice, 'GET', '/info')->ok());
        $this->assertTrue($service->request($endDevice, 'GET', '/info')->ok());
    }
}
