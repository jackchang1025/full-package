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

    $service = new DeviceTokenService;
    $token = $service->generateToken('user@example.com', 99);

    $response = $this->postJson('/api/device/register.json', [], [
        'Authorization' => 'Bearer '.$token,
    ]);

    $response->assertStatus(200)
        ->assertJson([
            'success' => true,
            'code' => 200,
            'msg' => 'OK',
            'data' => null,
        ]);
});

test('authenticates with valid device token on updateDeviceInfo', function (): void {
    config(['websocket.device_auth.secret' => 'test-secret']);

    $service = new DeviceTokenService;
    $token = $service->generateToken('user@example.com', 99);

    $response = $this->postJson('/api/device/updateDeviceInfo.json', [], [
        'Authorization' => 'Bearer '.$token,
    ]);

    $response->assertStatus(200)
        ->assertJson([
            'success' => true,
            'code' => 200,
            'msg' => 'OK',
            'data' => null,
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

    // Use a route that returns the merged request data to verify middleware behavior
    Route::post('/test-device-auth-merge', function (\Illuminate\Http\Request $request) {
        return response()->json([
            'email' => $request->input('_device_auth_email'),
            'build_id' => $request->input('_device_auth_build_id'),
        ]);
    })->middleware('auth.device');

    $response = $this->postJson('/test-device-auth-merge', [], [
        'Authorization' => 'Bearer '.$token,
    ]);

    $response->assertStatus(200)
        ->assertJson([
            'email' => 'device-owner@example.com',
            'build_id' => 42,
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
