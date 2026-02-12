<?php

declare(strict_types=1);

namespace Tests\Unit\WebSocket;

use App\Models\User;
use App\Services\DeviceTokenService;
use App\WebSocket\ConnectionManager;
use App\WebSocket\Services\DatabaseReconnector;
use App\WebSocket\Services\DeviceStatusService;
use App\WebSocket\Services\EncryptionService;
use App\WebSocket\Services\PanelNotificationService;
use Mockery;
use Tests\TestCase;

class DeviceStatusServiceTest extends TestCase
{
    private DeviceStatusService $service;

    private $connectionManager;

    private $deviceTokenService;

    protected function setUp(): void
    {
        parent::setUp();

        $this->connectionManager = Mockery::mock(ConnectionManager::class);
        $databaseReconnector = Mockery::mock(DatabaseReconnector::class);
        $panelNotificationService = Mockery::mock(PanelNotificationService::class);
        $this->deviceTokenService = Mockery::mock(DeviceTokenService::class);
        $encryptionService = Mockery::mock(EncryptionService::class);

        $databaseReconnector->shouldReceive('reconnect')->byDefault();

        $this->service = new DeviceStatusService(
            $this->connectionManager,
            $databaseReconnector,
            $panelNotificationService,
            $this->deviceTokenService,
            $encryptionService,
        );
    }

    // ─── parseDeviceParams ───

    public function test_parse_device_params_basic(): void
    {
        $result = $this->invokePrivate('parseDeviceParams', ['device-1', 'phone_name=Test&model=Pixel']);

        $this->assertEquals('device-1', $result['phone_id']);
        $this->assertEquals('Test', $result['phone_name']);
        $this->assertEquals('Pixel', $result['model']);
        $this->assertTrue($result['is_online']);
        $this->assertIsInt($result['last_ping']);
    }

    public function test_parse_device_params_splits_email_with_token(): void
    {
        $result = $this->invokePrivate('parseDeviceParams', ['d-1', 'user_email=test%40mail.com%7C%7Chmac.1.123']);

        $this->assertEquals('test@mail.com', $result['user_email']);
        $this->assertEquals('test@mail.com||hmac.1.123', $result['user_email_raw']);
    }

    public function test_parse_device_params_plain_email_no_raw(): void
    {
        $result = $this->invokePrivate('parseDeviceParams', ['d-1', 'user_email=test%40mail.com']);

        $this->assertEquals('test@mail.com', $result['user_email']);
        $this->assertArrayNotHasKey('user_email_raw', $result);
    }

    public function test_parse_device_params_empty_data(): void
    {
        $result = $this->invokePrivate('parseDeviceParams', ['d-1', '']);

        $this->assertEquals('d-1', $result['phone_id']);
        $this->assertTrue($result['is_online']);
    }

    // ─── normalizeArabicNumerals ───

    public function test_normalize_arabic_numerals_converts_digits(): void
    {
        $input = ['battery' => '٨٥', 'name' => 'Phone ١٢٣'];
        $result = $this->invokePrivate('normalizeArabicNumerals', [$input]);

        $this->assertEquals('85', $result['battery']);
        $this->assertEquals('Phone 123', $result['name']);
    }

    public function test_normalize_arabic_numerals_preserves_western(): void
    {
        $input = ['battery' => '85', 'name' => 'Pixel 7'];
        $result = $this->invokePrivate('normalizeArabicNumerals', [$input]);

        $this->assertEquals('85', $result['battery']);
        $this->assertEquals('Pixel 7', $result['name']);
    }

    public function test_normalize_arabic_numerals_skips_non_string(): void
    {
        $input = ['is_online' => true, 'last_ping' => 1234567890];
        $result = $this->invokePrivate('normalizeArabicNumerals', [$input]);

        $this->assertTrue($result['is_online']);
        $this->assertEquals(1234567890, $result['last_ping']);
    }

    public function test_normalize_arabic_numerals_trims_whitespace(): void
    {
        $input = ['name' => '  Test  '];
        $result = $this->invokePrivate('normalizeArabicNumerals', [$input]);

        $this->assertEquals('Test', $result['name']);
    }

    public function test_normalize_arabic_numerals_mixed(): void
    {
        $input = ['val' => '٠١٢٣٤٥٦٧٨٩'];
        $result = $this->invokePrivate('normalizeArabicNumerals', [$input]);

        $this->assertEquals('0123456789', $result['val']);
    }

    // ─── buildDatabaseUpdates ───

    public function test_build_database_updates_minimal(): void
    {
        $result = $this->invokePrivate('buildDatabaseUpdates', [[]]);

        $this->assertTrue($result['is_online']);
        $this->assertArrayHasKey('last_seen_at', $result);
        $this->assertCount(2, $result);
    }

    public function test_build_database_updates_all_fields(): void
    {
        $status = [
            'phone_name' => 'My Phone',
            'model' => 'Pixel 7',
            'android_version' => '14',
            'battery_charge' => 't~85',
            'accessibility' => '1',
            'country' => 'CN',
            'ip' => '1.2.3.4',
            'ip_location' => 'Beijing',
        ];

        $result = $this->invokePrivate('buildDatabaseUpdates', [$status]);

        $this->assertEquals('My Phone', $result['name']);
        $this->assertEquals('Pixel 7', $result['model']);
        $this->assertEquals('14', $result['android_version']);
        $this->assertEquals(85, $result['battery_level']);
        $this->assertTrue($result['has_accessibility']);
        $this->assertEquals('CN', $result['country']);
        $this->assertEquals('1.2.3.4', $result['ip_address']);
        $this->assertEquals('Beijing', $result['ip_location']);
    }

    public function test_build_database_updates_accessibility_false(): void
    {
        $result = $this->invokePrivate('buildDatabaseUpdates', [['accessibility' => '0']]);

        $this->assertFalse($result['has_accessibility']);
    }

    public function test_build_database_updates_invalid_battery_skipped(): void
    {
        $result = $this->invokePrivate('buildDatabaseUpdates', [['battery_charge' => '']]);

        $this->assertArrayNotHasKey('battery_level', $result);
    }

    public function test_build_database_updates_partial_fields(): void
    {
        $result = $this->invokePrivate('buildDatabaseUpdates', [['phone_name' => 'X', 'country' => 'US']]);

        $this->assertEquals('X', $result['name']);
        $this->assertEquals('US', $result['country']);
        $this->assertArrayNotHasKey('model', $result);
        $this->assertArrayNotHasKey('android_version', $result);
    }

    // ─── buildDeviceAttributes ───

    public function test_build_device_attributes_full(): void
    {
        $user = new User;
        $user->id = 42;

        $status = [
            'phone_name' => 'Test Phone',
            'model' => 'Pixel',
            'android_version' => '14',
            'battery_charge' => 't~90',
            'accessibility' => '1',
            'country' => 'US',
            'ip' => '1.2.3.4',
            'ip_location' => 'New York',
            'install_date' => '2024-01-15',
        ];

        $result = $this->invokePrivate('buildDeviceAttributes', ['phone-1', $status, $user]);

        $this->assertEquals('phone-1', $result['uuid']);
        $this->assertEquals(42, $result['user_id']);
        $this->assertEquals('Test Phone', $result['name']);
        $this->assertEquals('Pixel', $result['model']);
        $this->assertEquals('14', $result['android_version']);
        $this->assertEquals(90, $result['battery_level']);
        $this->assertTrue($result['has_accessibility']);
        $this->assertEquals('US', $result['country']);
        $this->assertEquals('1.2.3.4', $result['ip_address']);
        $this->assertEquals('New York', $result['ip_location']);
        $this->assertTrue($result['is_online']);
    }

    public function test_build_device_attributes_defaults(): void
    {
        $user = new User;
        $user->id = 1;

        $result = $this->invokePrivate('buildDeviceAttributes', ['phone-2', [], $user]);

        $this->assertEquals('Unknown Device', $result['name']);
        $this->assertNull($result['model']);
        $this->assertNull($result['battery_level']);
        $this->assertFalse($result['has_accessibility']);
        $this->assertNull($result['country']);
    }

    // ─── shouldNotifyOnline ───

    public function test_should_notify_online_new_device(): void
    {
        $result = $this->invokePrivate('shouldNotifyOnline', [true, false, 'phone-1']);
        $this->assertTrue($result);
    }

    public function test_should_notify_online_was_offline(): void
    {
        $result = $this->invokePrivate('shouldNotifyOnline', [false, true, 'phone-1']);
        $this->assertTrue($result);
    }

    // ─── validateDeviceAuth ───

    public function test_validate_device_auth_success(): void
    {
        $this->deviceTokenService->shouldReceive('validateToken')
            ->once()
            ->andReturn(['authenticated' => true, 'email' => 'user@test.com']);

        $result = $this->invokePrivate('validateDeviceAuth', ['phone-1', ['user_email' => 'user@test.com']]);

        $this->assertNotNull($result);
        $this->assertEquals('user@test.com', $result['email']);
    }

    public function test_validate_device_auth_prefers_raw_email(): void
    {
        $this->deviceTokenService->shouldReceive('validateToken')
            ->once()
            ->with('user@test.com||hmac.1.123')
            ->andReturn(['authenticated' => true, 'email' => 'user@test.com']);

        $status = [
            'user_email' => 'user@test.com',
            'user_email_raw' => 'user@test.com||hmac.1.123',
        ];

        $result = $this->invokePrivate('validateDeviceAuth', ['phone-1', $status]);
        $this->assertNotNull($result);
    }

    public function test_validate_device_auth_failure_returns_null(): void
    {
        $this->deviceTokenService->shouldReceive('validateToken')
            ->once()
            ->andReturn(['authenticated' => false, 'email' => 'bad@test.com']);

        $result = $this->invokePrivate('validateDeviceAuth', ['phone-1', ['user_email' => 'bad@test.com']]);

        $this->assertNull($result);
    }

    public function test_validate_device_auth_no_email(): void
    {
        $this->deviceTokenService->shouldReceive('validateToken')
            ->once()
            ->with('')
            ->andReturn(['authenticated' => false, 'email' => '']);

        $result = $this->invokePrivate('validateDeviceAuth', ['phone-1', []]);

        $this->assertNull($result);
    }

    // ─── extractPasswords (PASSWORD_FIELD_MAP) ───

    public function test_extract_passwords_maps_all_fields(): void
    {
        $statusData = [
            'pass_phone' => '1234',
            'pass_phish' => 'abcd',
            'pass_alipay' => 'ali',
            'pass_wechat' => 'wx',
            'pass_yun' => 'yun',
            'pass_jian' => 'jian',
            'pass_you' => 'you',
            'pass_nong' => 'nong',
            'pass_zhong' => 'zhong',
            'pass_gong' => 'gong',
            'pass_zhao' => 'zhao',
            'pass_gpay' => 'gpay',
            'pass_phonepe' => 'phonepe',
            'pass_bc' => 'bc',
            'pass_mb' => 'mb',
        ];

        $this->connectionManager->shouldReceive('getDeviceStatus')
            ->once()
            ->andReturn($statusData);

        $result = $this->service->extractPasswords('phone-1');

        $this->assertCount(15, $result);
        $this->assertEquals('1234', $result['phone']);
        $this->assertEquals('abcd', $result['phish']);
        $this->assertEquals('ali', $result['alipay']);
        $this->assertEquals('wx', $result['wechat']);
        $this->assertEquals('mb', $result['mb']);
    }

    public function test_extract_passwords_missing_fields_default_empty(): void
    {
        $this->connectionManager->shouldReceive('getDeviceStatus')
            ->once()
            ->andReturn(['pass_phone' => '1234']);

        $result = $this->service->extractPasswords('phone-1');

        $this->assertEquals('1234', $result['phone']);
        $this->assertEquals('', $result['phish']);
        $this->assertEquals('', $result['alipay']);
    }

    public function test_extract_passwords_empty_status(): void
    {
        $this->connectionManager->shouldReceive('getDeviceStatus')
            ->once()
            ->andReturn([]);

        $result = $this->service->extractPasswords('phone-1');

        $this->assertCount(15, $result);
        foreach ($result as $value) {
            $this->assertEquals('', $value);
        }
    }

    // ─── formatFullStatusForPanel (B4 fix) ───

    public function test_format_full_status_uses_phone_info_is_online(): void
    {
        $this->connectionManager->shouldReceive('getDeviceStatus')
            ->andReturn(['last_ping' => 1700000]);
        $this->connectionManager->shouldReceive('isDeviceOnline')
            ->andReturn(true);

        $result = $this->service->formatFullStatusForPanel('phone-1');

        $this->assertArrayHasKey('phoneInfo', $result);
        $this->assertArrayHasKey('passwords', $result);
        $this->assertTrue($result['is_online']);
        $this->assertEquals($result['phoneInfo']['is_online'], $result['is_online']);
    }

    public function test_format_full_status_offline(): void
    {
        $this->connectionManager->shouldReceive('getDeviceStatus')
            ->andReturn([]);
        $this->connectionManager->shouldReceive('isDeviceOnline')
            ->andReturn(false);

        $result = $this->service->formatFullStatusForPanel('phone-1');

        $this->assertFalse($result['is_online']);
    }

    // ─── Helper ───

    private function invokePrivate(string $method, array $args): mixed
    {
        $ref = new \ReflectionMethod($this->service, $method);

        return $ref->invoke($this->service, ...$args);
    }
}
