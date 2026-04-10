<?php

declare(strict_types=1);

use App\Services\DeviceTokenService;
use Illuminate\Foundation\Testing\RefreshDatabase;

uses(RefreshDatabase::class);

/**
 * Helper: create user, generate auth token, register a device, return [token, user].
 */
function setupDeviceForUpdate(string $email, string $deviceUid): array
{
    $user = \App\Models\User::factory()->create(['email' => $email]);

    config(['websocket.device_auth.secret' => 'test-secret']);
    $service = new DeviceTokenService;
    $token = $service->generateToken($email, 1);

    // Register the device first
    test()->postJson('/api/device/register.json', [
        'deviceUid' => $deviceUid,
        'brandCode' => 'xiaomi',
        'model' => 'Test Phone',
    ], [
        'Authorization' => 'Bearer '.$token,
    ])->assertStatus(200);

    return [$token, $user];
}

it('validates required deviceUid on update', function (): void {
    config(['websocket.device_auth.secret' => 'test-secret']);

    $user = \App\Models\User::factory()->create(['email' => 'upd-val@example.com']);
    $service = new DeviceTokenService;
    $token = $service->generateToken('upd-val@example.com', 1);

    // Missing deviceUid should fail
    $response = $this->postJson('/api/device/updateDeviceInfo.json', [], [
        'Authorization' => 'Bearer '.$token,
    ]);

    $response->assertStatus(422)
        ->assertJson([
            'success' => false,
            'code' => 422,
        ]);
});

it('accepts valid lightweight update', function (): void {
    [$token] = setupDeviceForUpdate('upd-valid@example.com', 'upd-valid-uid');

    $response = $this->postJson('/api/device/updateDeviceInfo.json', [
        'deviceUid' => 'upd-valid-uid',
        'brandCode' => 'oppo',
        'apiGrade' => 35,
        'langCode' => 'en-US',
        'phoneNumber' => '+1234567890',
    ], [
        'Authorization' => 'Bearer '.$token,
    ]);

    $response->assertStatus(200)
        ->assertJson([
            'success' => true,
            'code' => 200,
            'msg' => 'OK',
            'count' => 1,
        ]);

    // Verify data contains a UUID
    expect($response->json('data'))->toBeString()->not->toBeEmpty();
});

it('updates existing device info fields', function (): void {
    [$token] = setupDeviceForUpdate('upd-fields@example.com', 'upd-fields-uid');

    // Update with new values
    $this->postJson('/api/device/updateDeviceInfo.json', [
        'deviceUid' => 'upd-fields-uid',
        'brandCode' => 'oppo',
        'apiGrade' => 36,
        'langCode' => 'ja-JP',
        'phoneNumber' => '+81901234567',
    ], [
        'Authorization' => 'Bearer '.$token,
    ])->assertStatus(200);

    // Verify DB was updated
    $device = \App\Models\Device::where('device_uid', 'upd-fields-uid')->first();
    expect($device)->not->toBeNull();
    expect($device->brand)->toBe('oppo');
    expect($device->android_version)->toBe('36');
    expect($device->lang_code)->toBe('ja-JP');
    expect($device->phone_number)->toBe('+81901234567');
    expect($device->is_online)->toBeTrue();
    expect($device->last_seen_at)->not->toBeNull();
});

it('returns 404 for unknown deviceUid on update', function (): void {
    config(['websocket.device_auth.secret' => 'test-secret']);

    $user = \App\Models\User::factory()->create(['email' => 'upd-404@example.com']);
    $service = new DeviceTokenService;
    $token = $service->generateToken('upd-404@example.com', 1);

    $response = $this->postJson('/api/device/updateDeviceInfo.json', [
        'deviceUid' => 'nonexistent-device-uid',
    ], [
        'Authorization' => 'Bearer '.$token,
    ]);

    $response->assertStatus(404)
        ->assertJson([
            'success' => false,
            'code' => 404,
            'msg' => 'Device not found',
            'data' => null,
        ]);
});
