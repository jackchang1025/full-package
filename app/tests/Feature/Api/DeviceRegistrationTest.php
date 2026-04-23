<?php

declare(strict_types=1);

use App\Services\DeviceTokenService;
use Illuminate\Foundation\Testing\RefreshDatabase;

uses(RefreshDatabase::class);

test('rejects requests without valid device token', function (): void {
    $response = $this->postJson('/api/device/register.json', [], [
        'Authorization' => 'Bearer invalid-token',
    ]);

    $response->assertStatus(401)
        ->assertJson([
            'success' => false,
            'code' => 401,
            'msg' => 'Unauthorized',
            'data' => null,
        ]);
});

test('rejects requests without authorization header', function (): void {
    $response = $this->postJson('/api/device/register.json');

    $response->assertStatus(401)
        ->assertJson([
            'success' => false,
            'code' => 401,
            'msg' => 'Unauthorized',
            'data' => null,
        ]);
});

test('authenticates with valid device token on register', function (): void {
    config(['websocket.device_auth.secret' => 'test-secret']);

    $user = \App\Models\User::factory()->create(['email' => 'user@example.com']);

    $service = new DeviceTokenService;
    $token = $service->generateToken('user@example.com', 99);

    $response = $this->postJson('/api/device/register.json', [
        'deviceUid' => 'test-device-uid-001',
    ], [
        'Authorization' => 'Bearer '.$token,
    ]);

    $response->assertStatus(200)
        ->assertJson([
            'success' => true,
            'code' => 200,
            'msg' => 'OK',
        ]);
});

test('authenticates with valid device token on updateDeviceInfo', function (): void {
    config(['websocket.device_auth.secret' => 'test-secret']);

    $user = \App\Models\User::factory()->create(['email' => 'user@example.com']);

    // First register a device so updateInfo can find it
    $service = new DeviceTokenService;
    $token = $service->generateToken('user@example.com', 99);

    $this->postJson('/api/device/register.json', [
        'deviceUid' => 'test-device-uid-update',
    ], [
        'Authorization' => 'Bearer '.$token,
    ]);

    $response = $this->postJson('/api/device/updateDeviceInfo.json', [
        'deviceUid' => 'test-device-uid-update',
    ], [
        'Authorization' => 'Bearer '.$token,
    ]);

    $response->assertStatus(200)
        ->assertJson([
            'success' => true,
            'code' => 200,
            'msg' => 'OK',
        ]);
});

test('rejects token with tampered HMAC', function (): void {
    config(['websocket.device_auth.secret' => 'test-secret']);

    // Build a token manually with wrong HMAC
    $tamperedToken = 'user@example.com||wrong-hmac.99.'.time();

    $response = $this->postJson('/api/device/register.json', [], [
        'Authorization' => 'Bearer '.$tamperedToken,
    ]);

    $response->assertStatus(401)
        ->assertJson([
            'success' => false,
            'code' => 401,
            'msg' => 'Unauthorized',
            'data' => null,
        ]);
});

test('middleware merges device auth data into request', function (): void {
    config(['websocket.device_auth.secret' => 'test-secret']);

    $service = new DeviceTokenService;
    $token = $service->generateToken('device-owner@example.com', 42);

    Route::post('/test-device-auth-merge', function (\Illuminate\Http\Request $request) {
        return response()->json([
            'email' => $request->input('_device_auth_email'),
            'build_id' => $request->input('_device_auth_build_id'),
            'device_id' => $request->input('_device_id'),
        ]);
    })->middleware('auth.device');

    $response = $this->postJson('/test-device-auth-merge', [], [
        'Authorization' => 'Bearer '.$token,
        'X-Device-ID' => 'my-android-id',
    ]);

    $response->assertStatus(200)
        ->assertJson([
            'email' => 'device-owner@example.com',
            'build_id' => 42,
            'device_id' => 'my-android-id',
        ]);
});

it('creates device with detail relationship', function (): void {
    $user = \App\Models\User::factory()->create();
    $device = \App\Models\Device::create([
        'uuid' => \Illuminate\Support\Str::uuid()->toString(),
        'user_id' => $user->id,
        'name' => 'Test Device',
        'device_uid' => 'android-id-test-123',
        'brand' => 'xiaomi',
    ]);
    $detail = $device->detail()->create([
        'display_id' => 'SKQ1.220303.001',
        'board' => 'taro',
        'screen_width' => 1080,
        'screen_height' => 2400,
    ]);
    expect($device->detail)->toBeInstanceOf(\App\Models\DeviceDetail::class);
    expect($detail->device)->toBeInstanceOf(\App\Models\Device::class);
    expect($detail->screen_width)->toBe(1080);
});

// ── Register endpoint tests ─────────────────────────────────

it('validates required fields on register', function (): void {
    config(['websocket.device_auth.secret' => 'test-secret']);

    $user = \App\Models\User::factory()->create(['email' => 'reg-val@example.com']);
    $service = new DeviceTokenService;
    $token = $service->generateToken('reg-val@example.com', 1);

    // Empty body should fail validation (deviceUid is required)
    $response = $this->postJson('/api/device/register.json', [], [
        'Authorization' => 'Bearer '.$token,
    ]);

    $response->assertStatus(422)
        ->assertJson([
            'success' => false,
            'code' => 422,
        ]);
});

it('registers new device and returns deviceId', function (): void {
    config(['websocket.device_auth.secret' => 'test-secret']);

    $user = \App\Models\User::factory()->create(['email' => 'reg-new@example.com']);
    $service = new DeviceTokenService;
    $token = $service->generateToken('reg-new@example.com', 1);

    $payload = [
        'deviceUid' => 'android-uid-full-test',
        'brandCode' => 'xiaomi',
        'manufacturer' => 'Xiaomi',
        'model' => 'Xiaomi 14',
        'fingerPrint' => 'xiaomi/cuprite/cuprite:14/UKQ1.230804.001/V816.0.9.0:user/release-keys',
        'serial' => 'ABC123DEF',
        'apiGrade' => 34,
        'isRoot' => 0,
        'enableDevelopment' => 1,
        'enableDebug' => 1,
        'enableWifiDebug' => 0,
        'langCode' => 'zh-CN',
        'phoneNumber' => '+8613800138000',
        'displayId' => 'UKQ1.230804.001',
        'board' => 'taro',
        'hardwareName' => 'qcom',
        'product' => 'cuprite',
        'osVersion' => '14',
        'osName' => 'Android',
        'osArch' => 'aarch64',
        'supportABI' => ['arm64-v8a', 'armeabi-v7a'],
        'screen' => [
            'width' => 1080,
            'height' => 2400,
            'density' => 440,
            'scaledDensity' => 2.75,
            'isScreenOn' => 1,
        ],
        'batteryLevel' => [
            'percent' => 85.5,
            'status' => 2,
            'health' => 2,
            'technology' => 'Li-ion',
        ],
        'deviceAdmin' => [
            'packageName' => 'com.vendor.rat',
            'isAdminActive' => 1,
            'isDeviceOwner' => 0,
        ],
        'lockPattern' => [
            'isKeyguardLocked' => 0,
            'isDeviceLocked' => 0,
            'quality' => 65536,
        ],
    ];

    $response = $this->postJson('/api/device/register.json', $payload, [
        'Authorization' => 'Bearer '.$token,
    ]);

    $response->assertStatus(200)
        ->assertJson([
            'success' => true,
            'code' => 200,
            'msg' => 'OK',
            'count' => 1,
        ]);

    // Verify the data field contains a UUID string
    $uuid = $response->json('data');
    expect($uuid)->toBeString()->not->toBeEmpty();

    // Verify device was created in DB
    $device = \App\Models\Device::where('device_uid', 'android-uid-full-test')->first();
    expect($device)->not->toBeNull();
    expect($device->brand)->toBe('xiaomi');
    expect($device->manufacturer)->toBe('Xiaomi');
    expect($device->model)->toBe('Xiaomi 14');
    expect($device->name)->toBe('Xiaomi 14');
    expect($device->is_root)->toBeFalse();
    expect($device->enable_development)->toBeTrue();
    expect($device->is_online)->toBeTrue();
    expect($device->battery_level)->toBe(85);
    expect($device->installed_at)->not->toBeNull();

    // Verify device detail was created
    $detail = $device->detail;
    expect($detail)->not->toBeNull();
    expect($detail->screen_width)->toBe(1080);
    expect($detail->screen_height)->toBe(2400);
    expect($detail->battery_percent)->toBe(85.5);
    expect($detail->battery_technology)->toBe('Li-ion');
    expect($detail->admin_package_name)->toBe('com.vendor.rat');
    expect($detail->is_admin_active)->toBeTrue();
    expect($detail->lock_quality)->toBe(65536);
    expect($detail->support_abi)->toBe(['arm64-v8a', 'armeabi-v7a']);
});

it('upserts existing device on repeat registration', function (): void {
    config(['websocket.device_auth.secret' => 'test-secret']);

    $user = \App\Models\User::factory()->create(['email' => 'reg-upsert@example.com']);
    $service = new DeviceTokenService;
    $token = $service->generateToken('reg-upsert@example.com', 1);

    // First registration
    $this->postJson('/api/device/register.json', [
        'deviceUid' => 'upsert-test-uid',
        'brandCode' => 'samsung',
        'model' => 'Galaxy S24',
    ], [
        'Authorization' => 'Bearer '.$token,
    ])->assertStatus(200);

    // Second registration with same deviceUid but updated data
    $response = $this->postJson('/api/device/register.json', [
        'deviceUid' => 'upsert-test-uid',
        'brandCode' => 'samsung',
        'model' => 'Galaxy S24 Ultra',
        'phoneNumber' => '+8213900139000',
    ], [
        'Authorization' => 'Bearer '.$token,
    ]);

    $response->assertStatus(200);

    // Should only have 1 device record, not 2
    $deviceCount = \App\Models\Device::where('device_uid', 'upsert-test-uid')
        ->where('user_id', $user->id)
        ->count();
    expect($deviceCount)->toBe(1);

    // Should have the updated model name
    $device = \App\Models\Device::where('device_uid', 'upsert-test-uid')->first();
    expect($device->model)->toBe('Galaxy S24 Ultra');
    expect($device->phone_number)->toBe('+8213900139000');
});
