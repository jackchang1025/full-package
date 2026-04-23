<?php

declare(strict_types=1);

use App\Models\Device;
use App\Models\DeviceLog;
use App\Models\User;
use App\Services\DeviceTokenService;
use Illuminate\Foundation\Testing\RefreshDatabase;

uses(RefreshDatabase::class);

beforeEach(function () {
    config(['websocket.device_auth.secret' => 'test-secret']);

    $this->user = User::factory()->create();
    $this->device = Device::factory()->create([
        'user_id' => $this->user->id,
        'device_uid' => 'test_android_id_123',
        'uuid' => 'test_android_id_123',
    ]);
    $this->ownerToken = app(DeviceTokenService::class)
        ->generateOwnerToken($this->user->id);
    $this->authHeaders = [
        'Authorization' => 'Bearer '.$this->ownerToken,
        'X-Device-ID' => 'test_android_id_123',
    ];
});

test('store logs successfully', function () {
    $response = $this->postJson('/api/client/logs', [
        'deviceId' => 'test_android_id_123',
        'logs' => [
            [
                'logType' => 'KSTR',
                'content' => '微信|TEXT_CHANGED|你好',
                'timestamp' => 1713600000000,
            ],
            [
                'logType' => 'VAPS',
                'content' => '打开: 微信',
                'timestamp' => 1713600001000,
            ],
        ],
        'timestamp' => 1713600003000,
    ], $this->authHeaders);

    $response->assertOk()
        ->assertJson(['success' => true, 'data' => ['inserted' => 2]]);

    expect(DeviceLog::count())->toBe(2);
    expect(DeviceLog::where('log_type', 'KSTR')->first()->content)
        ->toBe('微信|TEXT_CHANGED|你好');
});

test('store rejects missing bearer token', function () {
    $response = $this->postJson('/api/client/logs', [
        'deviceId' => 'test_android_id_123',
        'logs' => [
            ['logType' => 'KSTR', 'content' => 'test', 'timestamp' => 1713600000000],
        ],
    ]);

    $response->assertStatus(401)
        ->assertJson(['msg' => 'Missing Authorization header']);
});

test('store rejects invalid owner_token', function () {
    $response = $this->postJson('/api/client/logs', [
        'deviceId' => 'test_android_id_123',
        'logs' => [
            ['logType' => 'KSTR', 'content' => 'test', 'timestamp' => 1713600000000],
        ],
    ], [
        'Authorization' => 'Bearer invalid_token',
        'X-Device-ID' => 'test_android_id_123',
    ]);

    $response->assertStatus(401)
        ->assertJson(['msg' => 'Invalid owner_token']);
});

test('store rejects invalid log type', function () {
    $response = $this->postJson('/api/client/logs', [
        'deviceId' => 'test_android_id_123',
        'logs' => [
            ['logType' => 'INVALID', 'content' => 'test', 'timestamp' => 1713600000000],
        ],
    ], $this->authHeaders);

    $response->assertStatus(422);
});

test('store rejects unknown device', function () {
    $response = $this->postJson('/api/client/logs', [
        'deviceId' => 'nonexistent_device',
        'logs' => [
            ['logType' => 'KSTR', 'content' => 'test', 'timestamp' => 1713600000000],
        ],
    ], [
        'Authorization' => 'Bearer '.$this->ownerToken,
        'X-Device-ID' => 'nonexistent_device',
    ]);

    $response->assertStatus(404);
});

test('store rejects empty logs array', function () {
    $response = $this->postJson('/api/client/logs', [
        'deviceId' => 'test_android_id_123',
        'logs' => [],
    ], $this->authHeaders);

    $response->assertStatus(422);
});

test('store rejects missing required fields', function () {
    $response = $this->postJson('/api/client/logs', [], $this->authHeaders);

    $response->assertStatus(422);
});

test('store sets correct device_id and user_id on log records', function () {
    $response = $this->postJson('/api/client/logs', [
        'deviceId' => 'test_android_id_123',
        'logs' => [
            ['logType' => 'ACTZ', 'content' => 'action log', 'timestamp' => 1713600000000],
        ],
    ], $this->authHeaders);

    $response->assertOk();

    $log = DeviceLog::first();
    expect($log->device_id)->toBe($this->device->id);
    expect($log->user_id)->toBe($this->user->id);
    expect($log->device_uid)->toBe('test_android_id_123');
});

test('store handles all valid log types', function () {
    $validTypes = ['ACTZ', 'KSTR', 'BLNK', 'VAPS', 'NTFS', 'ARTS', 'SEVT'];
    $logs = array_map(fn (string $type, int $i) => [
        'logType' => $type,
        'content' => "content for {$type}",
        'timestamp' => 1713600000000 + ($i * 1000),
    ], $validTypes, array_keys($validTypes));

    $response = $this->postJson('/api/client/logs', [
        'deviceId' => 'test_android_id_123',
        'logs' => $logs,
    ], $this->authHeaders);

    $response->assertOk()
        ->assertJson(['success' => true, 'data' => ['inserted' => 7]]);

    expect(DeviceLog::count())->toBe(7);
});
