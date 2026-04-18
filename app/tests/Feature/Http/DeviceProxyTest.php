<?php

declare(strict_types=1);

use App\Models\Device;
use App\Models\User;
use Database\Seeders\RolePermissionSeeder;
use Illuminate\Foundation\Testing\RefreshDatabase;
use Illuminate\Support\Facades\Http;
use Illuminate\Support\Facades\RateLimiter;

uses(RefreshDatabase::class);

beforeEach(function () {
    config([
        'frpc.proxy_host'        => 'frps',
        'frpc.port_range_start'  => 20000,
        'frpc.port_range_end'    => 30000,
        'site.user_entry_path'   => '',
    ]);

    (new RolePermissionSeeder)->run();

    $this->user = User::factory()->create([
        'subscription_expires_at' => now()->addDays(30),
    ]);
    $this->user->assignRole('client');

    $this->device = Device::factory()->create([
        'user_id'        => $this->user->id,
        'frpc_base_port' => 20000,
        'is_removed'     => false,
    ]);
});

describe('POST /devices/{device}/api-proxy', function () {

    it('forwards a GET request to the device', function () {
        Http::fake([
            'frps:20000/*' => Http::response(
                ['code' => 200, 'success' => true, 'data' => true],
                200,
            ),
        ]);

        $response = $this->actingAs($this->user)->postJson(
            "/devices/{$this->device->uuid}/api-proxy",
            [
                'method' => 'GET',
                'path'   => '/global/setText',
                'query'  => ['text' => 'hello'],
            ],
        );

        $response->assertOk();
        $response->assertJson(['success' => true, 'status' => 200]);

        Http::assertSent(fn ($req) =>
            $req->url() === 'http://frps:20000/global/setText?text=hello'
            && $req->method() === 'GET');
    });

    it('forwards a POST request with body to the device', function () {
        Http::fake([
            'frps:20000/*' => Http::response(['code' => 200, 'success' => true], 200),
        ]);

        $response = $this->actingAs($this->user)->postJson(
            "/devices/{$this->device->uuid}/api-proxy",
            [
                'method' => 'POST',
                'path'   => '/global/action',
                'body'   => ['actionName' => 'back'],
            ],
        );

        $response->assertOk();
        Http::assertSent(fn ($req) =>
            $req->url() === 'http://frps:20000/global/action'
            && $req->method() === 'POST');
    });

    it('rejects a path not in the whitelist', function () {
        $response = $this->actingAs($this->user)->postJson(
            "/devices/{$this->device->uuid}/api-proxy",
            [
                'method' => 'GET',
                'path'   => '/localAdbShell',
                'query'  => ['command' => 'rm -rf /'],
            ],
        );

        $response->assertStatus(422);
        $response->assertJsonValidationErrors(['path']);
    });

    it('rejects a path with directory traversal characters', function () {
        $response = $this->actingAs($this->user)->postJson(
            "/devices/{$this->device->uuid}/api-proxy",
            [
                'method' => 'GET',
                'path'   => '/global/../localAdbShell',
            ],
        );

        $response->assertStatus(422);
        $response->assertJsonValidationErrors(['path']);
    });

    it('rejects an unsupported HTTP method', function () {
        $response = $this->actingAs($this->user)->postJson(
            "/devices/{$this->device->uuid}/api-proxy",
            ['method' => 'DELETE', 'path' => '/unlock'],
        );

        $response->assertStatus(422);
        $response->assertJsonValidationErrors(['method']);
    });

    it('rejects global/action with an invalid actionName', function () {
        $response = $this->actingAs($this->user)->postJson(
            "/devices/{$this->device->uuid}/api-proxy",
            [
                'method' => 'POST',
                'path'   => '/global/action',
                'body'   => ['actionName' => 'recents'],
            ],
        );

        $response->assertStatus(422);
        $response->assertJsonValidationErrors(['body.actionName']);
    });

    it('rejects syncLockCipher with a non-numeric textCipher', function () {
        $response = $this->actingAs($this->user)->postJson(
            "/devices/{$this->device->uuid}/api-proxy",
            [
                'method' => 'POST',
                'path'   => '/syncLockCipher',
                'body'   => [
                    'textCipher' => 'abcd',
                    'deviceId'   => $this->device->uuid,
                ],
            ],
        );

        $response->assertStatus(422);
        $response->assertJsonValidationErrors(['body.textCipher']);
    });

    it('rejects a query value over 1024 characters', function () {
        $response = $this->actingAs($this->user)->postJson(
            "/devices/{$this->device->uuid}/api-proxy",
            [
                'method' => 'GET',
                'path'   => '/global/setText',
                'query'  => ['text' => str_repeat('A', 1025)],
            ],
        );

        $response->assertStatus(422);
        $response->assertJsonValidationErrors(['query.text']);
    });

    it('returns 403 when the device is not owned by the user', function () {
        $otherUser = User::factory()->create([
            'subscription_expires_at' => now()->addDays(30),
        ]);
        $otherUser->assignRole('client');
        $otherDevice = Device::factory()->create([
            'user_id'        => $otherUser->id,
            'frpc_base_port' => 21000,
        ]);

        $response = $this->actingAs($this->user)->postJson(
            "/devices/{$otherDevice->uuid}/api-proxy",
            ['method' => 'GET', 'path' => '/unlock'],
        );

        $response->assertForbidden();
    });

    it('requires authentication', function () {
        $response = $this->postJson(
            "/devices/{$this->device->uuid}/api-proxy",
            ['method' => 'GET', 'path' => '/unlock'],
        );

        expect(in_array($response->status(), [401, 302]))->toBeTrue(
            "Expected 401 or 302 for unauthenticated request, got {$response->status()}"
        );
    });

    it('allows a sub-account to proxy a parent device', function () {
        Http::fake(['frps:20000/*' => Http::response(['code' => 200, 'success' => true], 200)]);

        $sub = User::factory()->create([
            'parent_id'              => $this->user->id,
            'subscription_expires_at' => now()->addDays(30),
        ]);
        $sub->assignRole('client');

        $response = $this->actingAs($sub)->postJson(
            "/devices/{$this->device->uuid}/api-proxy",
            ['method' => 'GET', 'path' => '/unlock'],
        );

        $response->assertOk();
    });

    it('filters internal host details from connection error messages', function () {
        Http::fake([
            'frps:20000/*' => function () {
                throw new \Illuminate\Http\Client\ConnectionException(
                    'cURL error 7: Failed to connect to frps port 20000'
                );
            },
        ]);

        $response = $this->actingAs($this->user)->postJson(
            "/devices/{$this->device->uuid}/api-proxy",
            ['method' => 'GET', 'path' => '/unlock'],
        );

        $response->assertOk();
        $body = $response->json();
        expect($body['success'])->toBeFalse();
        expect($body['error'] ?? '')->not->toContain('frps');
        expect($body['error'])->toBe('Device is unreachable or tunnel is not active');
    });

    it('rate-limits /syncLockCipher to 5 attempts per minute', function () {
        Http::fake(['frps:20000/*' => Http::response(['code' => 200, 'success' => true], 200)]);

        // Clear any stale rate limiter state from previous test runs
        RateLimiter::clear('device-cipher:' . $this->user->id);

        $payload = [
            'method' => 'POST',
            'path'   => '/syncLockCipher',
            'body'   => ['textCipher' => '1234', 'deviceId' => $this->device->uuid],
        ];

        for ($i = 0; $i < 5; $i++) {
            $this->actingAs($this->user)
                ->postJson("/devices/{$this->device->uuid}/api-proxy", $payload)
                ->assertOk();
        }

        $this->actingAs($this->user)
            ->postJson("/devices/{$this->device->uuid}/api-proxy", $payload)
            ->assertStatus(429);
    });

});
