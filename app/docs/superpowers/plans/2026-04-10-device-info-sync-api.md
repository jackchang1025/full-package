# Device Info Sync API Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Build Laravel API endpoints for Android device registration (`/api/device/register.json`) and device info update (`/api/device/updateDeviceInfo.json`), matching the vendor APK's expected request/response contract.

**Architecture:** Token-based authentication via `DeviceTokenService` (HMAC-SHA256). Two endpoints: full registration (35+ fields, upsert by `device_uid`) and lightweight update (6 fields). New `device_details` table stores hardware/screen/battery/admin/lock data that doesn't fit the existing `devices` table. API controller is stateless, no session/CSRF.

**Tech Stack:** Laravel 12, PHP 8.5, MySQL 8.4, Pest (testing), Laravel Sail

---

## File Structure

| Action | File | Responsibility |
|--------|------|----------------|
| Create | `app/routes/api.php` | API route definitions |
| Modify | `app/bootstrap/app.php` | Register api.php routes |
| Create | `app/database/migrations/2026_04_10_000001_create_device_details_table.php` | Hardware/screen/battery/admin/lock detail storage |
| Modify | `app/database/migrations` (new file) `2026_04_10_000002_add_device_uid_to_devices_table.php` | Add `device_uid` column to existing `devices` table |
| Create | `app/app/Models/DeviceDetail.php` | DeviceDetail model (belongs to Device) |
| Modify | `app/app/Models/Device.php` | Add `device_uid` to fillable, add `detail()` relationship |
| Create | `app/app/Http/Middleware/AuthenticateDevice.php` | Token auth middleware for device API |
| Create | `app/app/Http/Requests/Device/RegisterDeviceRequest.php` | Validation for full registration |
| Create | `app/app/Http/Requests/Device/UpdateDeviceInfoRequest.php` | Validation for lightweight update |
| Create | `app/app/Http/Controllers/Api/DeviceApiController.php` | API controller with register + updateInfo |
| Create | `tests/Feature/Api/DeviceRegistrationTest.php` | Feature tests for register endpoint |
| Create | `tests/Feature/Api/DeviceUpdateInfoTest.php` | Feature tests for update endpoint |

---

### Task 1: Register API routes + device auth middleware

**Files:**
- Create: `app/routes/api.php`
- Modify: `app/bootstrap/app.php:8-13`
- Create: `app/app/Http/Middleware/AuthenticateDevice.php`

- [ ] **Step 1: Write failing test for auth middleware rejection**

```php
// tests/Feature/Api/DeviceRegistrationTest.php
<?php

declare(strict_types=1);

use App\Models\User;
use App\Models\AppBuild;

it('rejects requests without valid device token', function () {
    $response = $this->postJson('/api/device/register.json', []);

    $response->assertStatus(401)
        ->assertJson(['success' => false, 'msg' => 'Unauthorized']);
});

it('authenticates with valid device token', function () {
    $user = User::factory()->create();

    // AppBuild needs device_token — we'll create a minimal fixture
    $build = AppBuild::factory()->create(['user_id' => $user->id]);
    $tokenService = new \App\Services\DeviceTokenService();
    $token = $tokenService->generateToken($user->email, $build->id);

    $response = $this->postJson('/api/device/register.json', [
        'deviceUid' => 'test-android-id-123',
        'brandCode' => 'xiaomi',
        'model' => 'Redmi Note 12',
        'apiGrade' => 34,
        'release' => '14',
    ], [
        'Authorization' => 'Bearer ' . $token,
    ]);

    $response->assertStatus(200)
        ->assertJsonStructure(['success', 'code', 'data']);
});
```

- [ ] **Step 2: Run test to verify it fails**

Run: `./vendor/bin/sail pest tests/Feature/Api/DeviceRegistrationTest.php`
Expected: FAIL — route not defined

- [ ] **Step 3: Create API routes file**

```php
// app/routes/api.php
<?php

declare(strict_types=1);

use App\Http\Controllers\Api\DeviceApiController;
use Illuminate\Support\Facades\Route;

Route::prefix('device')->middleware('auth.device')->group(function () {
    Route::post('register.json', [DeviceApiController::class, 'register']);
    Route::post('updateDeviceInfo.json', [DeviceApiController::class, 'updateInfo']);
});
```

- [ ] **Step 4: Register api.php in bootstrap/app.php**

In `app/bootstrap/app.php`, add the `api` key to `withRouting`:

```php
->withRouting(
    web: __DIR__.'/../routes/web.php',
    api: __DIR__.'/../routes/api.php',
    commands: __DIR__.'/../routes/console.php',
    channels: __DIR__.'/../routes/channels.php',
    health: '/up',
)
```

- [ ] **Step 5: Create device auth middleware**

```php
// app/app/Http/Middleware/AuthenticateDevice.php
<?php

declare(strict_types=1);

namespace App\Http\Middleware;

use App\Services\DeviceTokenService;
use Closure;
use Illuminate\Http\Request;
use Symfony\Component\HttpFoundation\Response;

class AuthenticateDevice
{
    public function __construct(
        private readonly DeviceTokenService $tokenService,
    ) {}

    public function handle(Request $request, Closure $next): Response
    {
        $token = $request->bearerToken();

        if (! $token) {
            return response()->json([
                'success' => false,
                'code' => 401,
                'msg' => 'Unauthorized',
                'data' => null,
            ], 401);
        }

        $result = $this->tokenService->validateToken($token);

        if (! $result['authenticated']) {
            return response()->json([
                'success' => false,
                'code' => 401,
                'msg' => 'Unauthorized',
                'data' => null,
            ], 401);
        }

        $request->merge([
            '_device_auth_email' => $result['email'],
            '_device_auth_build_id' => $result['build_id'],
        ]);

        return $next($request);
    }
}
```

- [ ] **Step 6: Register middleware alias in bootstrap/app.php**

Add to the `withMiddleware` callback's alias array:

```php
'auth.device' => \App\Http\Middleware\AuthenticateDevice::class,
```

- [ ] **Step 7: Create minimal controller stub**

```php
// app/app/Http/Controllers/Api/DeviceApiController.php
<?php

declare(strict_types=1);

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;

class DeviceApiController extends Controller
{
    public function register(Request $request): JsonResponse
    {
        return response()->json([
            'success' => true,
            'code' => 200,
            'msg' => 'OK',
            'data' => null,
        ]);
    }

    public function updateInfo(Request $request): JsonResponse
    {
        return response()->json([
            'success' => true,
            'code' => 200,
            'msg' => 'OK',
            'data' => null,
        ]);
    }
}
```

- [ ] **Step 8: Run tests to verify they pass**

Run: `./vendor/bin/sail pest tests/Feature/Api/DeviceRegistrationTest.php`
Expected: PASS (both tests)

- [ ] **Step 9: Commit**

```bash
git add app/routes/api.php app/bootstrap/app.php app/app/Http/Middleware/AuthenticateDevice.php app/app/Http/Controllers/Api/DeviceApiController.php tests/Feature/Api/DeviceRegistrationTest.php
git commit -m "feat: add device API routes with token auth middleware"
```

---

### Task 2: Database migrations — device_uid + device_details table

**Files:**
- Create: `app/database/migrations/2026_04_10_000001_add_device_uid_to_devices_table.php`
- Create: `app/database/migrations/2026_04_10_000002_create_device_details_table.php`

- [ ] **Step 1: Write migration to add device_uid to devices table**

```php
// app/database/migrations/2026_04_10_000001_add_device_uid_to_devices_table.php
<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up(): void
    {
        Schema::table('devices', function (Blueprint $table) {
            $table->string('device_uid', 64)->nullable()->after('uuid')->index();
            $table->string('brand', 50)->nullable()->after('model');
            $table->string('manufacturer', 100)->nullable()->after('brand');
            $table->string('fingerprint', 255)->nullable()->after('manufacturer');
            $table->string('serial', 64)->nullable()->after('fingerprint');
            $table->string('package_name', 150)->nullable()->after('serial');
            $table->boolean('is_root')->default(false)->after('has_accessibility');
            $table->boolean('enable_development')->default(false)->after('is_root');
            $table->boolean('enable_debug')->default(false)->after('enable_development');
            $table->boolean('enable_wifi_debug')->default(false)->after('enable_debug');
            $table->string('lang_code', 20)->nullable()->after('network_type');
            $table->string('trustee_id', 100)->nullable()->after('lang_code');
        });
    }

    public function down(): void
    {
        Schema::table('devices', function (Blueprint $table) {
            $table->dropColumn([
                'device_uid', 'brand', 'manufacturer', 'fingerprint',
                'serial', 'package_name', 'is_root', 'enable_development',
                'enable_debug', 'enable_wifi_debug', 'lang_code', 'trustee_id',
            ]);
        });
    }
};
```

- [ ] **Step 2: Write migration to create device_details table**

```php
// app/database/migrations/2026_04_10_000002_create_device_details_table.php
<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up(): void
    {
        Schema::create('device_details', function (Blueprint $table) {
            $table->id();
            $table->foreignId('device_id')->constrained()->onDelete('cascade');

            // Build 信息
            $table->string('display_id', 255)->nullable();
            $table->string('board', 100)->nullable();
            $table->string('device_name', 100)->nullable();
            $table->string('hardware_name', 100)->nullable();
            $table->string('product', 100)->nullable();
            $table->string('code_name', 50)->nullable();
            $table->string('incremental', 100)->nullable();
            $table->string('optimal_abi', 20)->nullable();
            $table->json('support_abi')->nullable();
            $table->string('factory_time', 30)->nullable();

            // OS 信息
            $table->string('os_version', 50)->nullable();
            $table->string('os_name', 50)->nullable();
            $table->string('os_arch', 20)->nullable();

            // 屏幕信息 (ScreenMetricsVO)
            $table->unsignedSmallInteger('screen_width')->nullable();
            $table->unsignedSmallInteger('screen_height')->nullable();
            $table->unsignedSmallInteger('screen_density')->nullable();
            $table->float('screen_scaled_density')->nullable();
            $table->float('screen_xdpi')->nullable();
            $table->float('screen_ydpi')->nullable();
            $table->boolean('screen_is_on')->default(true);
            $table->unsignedTinyInteger('screen_state')->nullable();
            $table->unsignedInteger('screen_off_timeout')->nullable();
            $table->boolean('screen_is_round')->default(false);
            $table->unsignedSmallInteger('status_bar_height')->nullable();
            $table->unsignedSmallInteger('navigation_bar_height')->nullable();
            $table->boolean('screen_is_blocked')->default(false);

            // 锁屏信息 (LockPatternVO)
            $table->boolean('is_keyguard_locked')->default(false);
            $table->boolean('is_device_locked')->default(false);
            $table->boolean('is_keyguard_secure')->default(false);
            $table->boolean('is_device_secure')->default(false);
            $table->boolean('in_keyguard_restricted_input_mode')->default(false);
            $table->smallInteger('lock_quality')->default(-1);

            // 电池信息 (BatteryLevelVO)
            $table->float('battery_percent')->nullable();
            $table->unsignedTinyInteger('battery_status')->nullable();
            $table->unsignedTinyInteger('battery_health')->nullable();
            $table->unsignedSmallInteger('battery_voltage')->nullable();
            $table->smallInteger('battery_temperature')->nullable();
            $table->string('battery_technology', 30)->nullable();
            $table->unsignedTinyInteger('battery_plugged')->nullable();
            $table->boolean('in_power_save_mode')->default(false);

            // 设备管理员 (DeviceAdminVO)
            $table->string('admin_package_name', 150)->nullable();
            $table->boolean('is_admin_active')->default(false);
            $table->boolean('is_device_owner')->default(false);
            $table->boolean('is_profile_owner')->default(false);

            $table->timestamps();

            $table->unique('device_id');
        });
    }

    public function down(): void
    {
        Schema::dropIfExists('device_details');
    }
};
```

- [ ] **Step 3: Run migrations**

Run: `./vendor/bin/sail artisan migrate`
Expected: Both migrations run successfully

- [ ] **Step 4: Commit**

```bash
git add app/database/migrations/2026_04_10_000001_add_device_uid_to_devices_table.php app/database/migrations/2026_04_10_000002_create_device_details_table.php
git commit -m "feat: add device_uid column and device_details table for device info sync"
```

---

### Task 3: Models — DeviceDetail + update Device

**Files:**
- Create: `app/app/Models/DeviceDetail.php`
- Modify: `app/app/Models/Device.php`

- [ ] **Step 1: Write failing test for model relationships**

```php
// tests/Feature/Api/DeviceRegistrationTest.php (append to existing file)

it('creates device with detail relationship', function () {
    $user = User::factory()->create();
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
```

- [ ] **Step 2: Run test to verify it fails**

Run: `./vendor/bin/sail pest tests/Feature/Api/DeviceRegistrationTest.php --filter="creates device with detail"`
Expected: FAIL — `detail()` method not defined

- [ ] **Step 3: Create DeviceDetail model**

```php
// app/app/Models/DeviceDetail.php
<?php

declare(strict_types=1);

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsTo;

class DeviceDetail extends Model
{
    protected $fillable = [
        'device_id',
        // Build
        'display_id', 'board', 'device_name', 'hardware_name', 'product',
        'code_name', 'incremental', 'optimal_abi', 'support_abi', 'factory_time',
        // OS
        'os_version', 'os_name', 'os_arch',
        // Screen
        'screen_width', 'screen_height', 'screen_density', 'screen_scaled_density',
        'screen_xdpi', 'screen_ydpi', 'screen_is_on', 'screen_state',
        'screen_off_timeout', 'screen_is_round', 'status_bar_height',
        'navigation_bar_height', 'screen_is_blocked',
        // Lock
        'is_keyguard_locked', 'is_device_locked', 'is_keyguard_secure',
        'is_device_secure', 'in_keyguard_restricted_input_mode', 'lock_quality',
        // Battery
        'battery_percent', 'battery_status', 'battery_health', 'battery_voltage',
        'battery_temperature', 'battery_technology', 'battery_plugged',
        'in_power_save_mode',
        // Admin
        'admin_package_name', 'is_admin_active', 'is_device_owner', 'is_profile_owner',
    ];

    protected function casts(): array
    {
        return [
            'support_abi' => 'array',
            'screen_is_on' => 'boolean',
            'screen_is_round' => 'boolean',
            'screen_is_blocked' => 'boolean',
            'is_keyguard_locked' => 'boolean',
            'is_device_locked' => 'boolean',
            'is_keyguard_secure' => 'boolean',
            'is_device_secure' => 'boolean',
            'in_keyguard_restricted_input_mode' => 'boolean',
            'in_power_save_mode' => 'boolean',
            'is_admin_active' => 'boolean',
            'is_device_owner' => 'boolean',
            'is_profile_owner' => 'boolean',
        ];
    }

    public function device(): BelongsTo
    {
        return $this->belongsTo(Device::class);
    }
}
```

- [ ] **Step 4: Update Device model — add fillable fields + detail relationship**

Add to `Device.php` fillable array:

```php
'device_uid',
'brand',
'manufacturer',
'fingerprint',
'serial',
'package_name',
'is_root',
'enable_development',
'enable_debug',
'enable_wifi_debug',
'lang_code',
'trustee_id',
```

Add casts:

```php
'is_root' => 'boolean',
'enable_development' => 'boolean',
'enable_debug' => 'boolean',
'enable_wifi_debug' => 'boolean',
```

Add relationship:

```php
use Illuminate\Database\Eloquent\Relations\HasOne;

public function detail(): HasOne
{
    return $this->hasOne(DeviceDetail::class);
}
```

- [ ] **Step 5: Run tests**

Run: `./vendor/bin/sail pest tests/Feature/Api/DeviceRegistrationTest.php`
Expected: ALL PASS

- [ ] **Step 6: Commit**

```bash
git add app/app/Models/DeviceDetail.php app/app/Models/Device.php
git commit -m "feat: add DeviceDetail model and Device relationship"
```

---

### Task 4: Form Request validation — RegisterDeviceRequest

**Files:**
- Create: `app/app/Http/Requests/Device/RegisterDeviceRequest.php`

- [ ] **Step 1: Write failing test for validation**

```php
// tests/Feature/Api/DeviceRegistrationTest.php (append)

it('validates required fields on register', function () {
    $user = User::factory()->create();
    $build = AppBuild::factory()->create(['user_id' => $user->id]);
    $token = (new \App\Services\DeviceTokenService())->generateToken($user->email, $build->id);

    $response = $this->postJson('/api/device/register.json', [], [
        'Authorization' => 'Bearer ' . $token,
    ]);

    $response->assertStatus(422)
        ->assertJsonValidationErrors(['deviceUid']);
});

it('validates field types on register', function () {
    $user = User::factory()->create();
    $build = AppBuild::factory()->create(['user_id' => $user->id]);
    $token = (new \App\Services\DeviceTokenService())->generateToken($user->email, $build->id);

    $response = $this->postJson('/api/device/register.json', [
        'deviceUid' => 'valid-uid',
        'apiGrade' => 'not-a-number',
        'isRoot' => 'not-a-number',
        'phoneNumber' => str_repeat('x', 51),
    ], [
        'Authorization' => 'Bearer ' . $token,
    ]);

    $response->assertStatus(422)
        ->assertJsonValidationErrors(['apiGrade', 'isRoot']);
});
```

- [ ] **Step 2: Run tests to verify they fail**

Run: `./vendor/bin/sail pest tests/Feature/Api/DeviceRegistrationTest.php --filter="validates"`
Expected: FAIL — no validation rules applied, returns 200

- [ ] **Step 3: Create RegisterDeviceRequest**

```php
// app/app/Http/Requests/Device/RegisterDeviceRequest.php
<?php

declare(strict_types=1);

namespace App\Http\Requests\Device;

use Illuminate\Foundation\Http\FormRequest;

class RegisterDeviceRequest extends FormRequest
{
    public function authorize(): bool
    {
        return true;
    }

    public function rules(): array
    {
        return [
            // 必填
            'deviceUid'    => ['required', 'string', 'max:64'],

            // 设备标识
            'deviceToken'  => ['nullable', 'string', 'max:255'],
            'packageName'  => ['nullable', 'string', 'max:150'],
            'trusteeId'    => ['nullable', 'string', 'max:100'],

            // 硬件
            'displayId'    => ['nullable', 'string', 'max:255'],
            'board'        => ['nullable', 'string', 'max:100'],
            'brandCode'    => ['nullable', 'string', 'max:50'],
            'device'       => ['nullable', 'string', 'max:100'],
            'fingerPrint'  => ['nullable', 'string', 'max:255'],
            'serial'       => ['nullable', 'string', 'max:64'],
            'manufacturer' => ['nullable', 'string', 'max:100'],
            'model'        => ['nullable', 'string', 'max:100'],
            'hardwareName' => ['nullable', 'string', 'max:100'],
            'product'      => ['nullable', 'string', 'max:100'],
            'optimalABI'   => ['nullable', 'string', 'max:20'],
            'supportABI'   => ['nullable', 'array'],
            'supportABI.*' => ['string', 'max:20'],
            'factoryTime'  => ['nullable', 'string', 'max:30'],

            // 版本
            'codeName'     => ['nullable', 'string', 'max:50'],
            'incremental'  => ['nullable', 'string', 'max:100'],
            'release'      => ['nullable', 'string', 'max:20'],
            'apiGrade'     => ['nullable', 'integer', 'min:1', 'max:99'],

            // OS
            'osVersion'    => ['nullable', 'string', 'max:50'],
            'osName'       => ['nullable', 'string', 'max:50'],
            'osArch'       => ['nullable', 'string', 'max:20'],

            // 用户
            'langCode'     => ['nullable', 'string', 'max:20'],
            'phoneNumber'  => ['nullable', 'string', 'max:50'],

            // 状态标志
            'isRoot'              => ['nullable', 'integer', 'in:0,1'],
            'enableDevelopment'   => ['nullable', 'integer', 'in:0,1'],
            'enableDebug'         => ['nullable', 'integer', 'in:0,1'],
            'enableWifiDebug'     => ['nullable', 'integer', 'in:0,1'],

            // 嵌套: screen (ScreenMetricsVO)
            'screen'                              => ['nullable', 'array'],
            'screen.width'                        => ['nullable', 'integer'],
            'screen.height'                       => ['nullable', 'integer'],
            'screen.density'                      => ['nullable', 'integer'],
            'screen.scaledDensity'                => ['nullable', 'numeric'],
            'screen.xdpi'                         => ['nullable', 'numeric'],
            'screen.ydpi'                         => ['nullable', 'numeric'],
            'screen.isScreenOn'                   => ['nullable', 'integer', 'in:0,1'],
            'screen.state'                        => ['nullable', 'integer'],
            'screen.screenOffTimeout'             => ['nullable', 'integer'],
            'screen.isKeyguardLocked'             => ['nullable', 'integer', 'in:0,1'],
            'screen.isDeviceLocked'               => ['nullable', 'integer', 'in:0,1'],
            'screen.isKeyguardSecure'             => ['nullable', 'integer', 'in:0,1'],
            'screen.isDeviceSecure'               => ['nullable', 'integer', 'in:0,1'],
            'screen.inKeyguardRestrictedInputMode' => ['nullable', 'integer', 'in:0,1'],
            'screen.quality'                      => ['nullable', 'integer'],
            'screen.statusBarHeight'              => ['nullable', 'integer'],
            'screen.navigationBarHeight'          => ['nullable', 'integer'],
            'screen.isScreenRound'                => ['nullable', 'integer', 'in:0,1'],
            'screen.isBlocked'                    => ['nullable', 'integer', 'in:0,1'],

            // 嵌套: batteryLevel (BatteryLevelVO)
            'batteryLevel'               => ['nullable', 'array'],
            'batteryLevel.percent'       => ['nullable', 'numeric', 'min:0', 'max:100'],
            'batteryLevel.status'        => ['nullable', 'integer'],
            'batteryLevel.health'        => ['nullable', 'integer'],
            'batteryLevel.voltage'       => ['nullable', 'integer'],
            'batteryLevel.temperature'   => ['nullable', 'integer'],
            'batteryLevel.technology'    => ['nullable', 'string', 'max:30'],
            'batteryLevel.plugged'       => ['nullable', 'integer'],
            'batteryLevel.inPowerSaveMode' => ['nullable', 'integer', 'in:0,1'],

            // 嵌套: deviceAdmin (DeviceAdminVO)
            'deviceAdmin'                => ['nullable', 'array'],
            'deviceAdmin.packageName'    => ['nullable', 'string', 'max:150'],
            'deviceAdmin.isAdminActive'  => ['nullable', 'integer', 'in:0,1'],
            'deviceAdmin.isDeviceOwner'  => ['nullable', 'integer', 'in:0,1'],
            'deviceAdmin.isProfileOwner' => ['nullable', 'integer', 'in:0,1'],

            // 嵌套: lockPattern (LockPatternVO)
            'lockPattern'                              => ['nullable', 'array'],
            'lockPattern.isScreenOn'                   => ['nullable', 'integer', 'in:0,1'],
            'lockPattern.isKeyguardLocked'             => ['nullable', 'integer', 'in:0,1'],
            'lockPattern.isDeviceLocked'               => ['nullable', 'integer', 'in:0,1'],
            'lockPattern.isKeyguardSecure'             => ['nullable', 'integer', 'in:0,1'],
            'lockPattern.isDeviceSecure'               => ['nullable', 'integer', 'in:0,1'],
            'lockPattern.inKeyguardRestrictedInputMode' => ['nullable', 'integer', 'in:0,1'],
            'lockPattern.quality'                      => ['nullable', 'integer'],
        ];
    }
}
```

- [ ] **Step 4: Wire RegisterDeviceRequest into controller**

Update `DeviceApiController::register` method signature:

```php
use App\Http\Requests\Device\RegisterDeviceRequest;

public function register(RegisterDeviceRequest $request): JsonResponse
```

- [ ] **Step 5: Run tests**

Run: `./vendor/bin/sail pest tests/Feature/Api/DeviceRegistrationTest.php --filter="validates"`
Expected: PASS

- [ ] **Step 6: Commit**

```bash
git add app/app/Http/Requests/Device/RegisterDeviceRequest.php app/app/Http/Controllers/Api/DeviceApiController.php
git commit -m "feat: add RegisterDeviceRequest validation with nested VO rules"
```

---

### Task 5: Form Request validation — UpdateDeviceInfoRequest

**Files:**
- Create: `app/app/Http/Requests/Device/UpdateDeviceInfoRequest.php`
- Create: `tests/Feature/Api/DeviceUpdateInfoTest.php`

- [ ] **Step 1: Write failing test for update validation**

```php
// tests/Feature/Api/DeviceUpdateInfoTest.php
<?php

declare(strict_types=1);

use App\Models\User;
use App\Models\AppBuild;

it('validates required deviceUid on update', function () {
    $user = User::factory()->create();
    $build = AppBuild::factory()->create(['user_id' => $user->id]);
    $token = (new \App\Services\DeviceTokenService())->generateToken($user->email, $build->id);

    $response = $this->postJson('/api/device/updateDeviceInfo.json', [], [
        'Authorization' => 'Bearer ' . $token,
    ]);

    $response->assertStatus(422)
        ->assertJsonValidationErrors(['deviceUid']);
});

it('accepts valid lightweight update', function () {
    $user = User::factory()->create();
    $build = AppBuild::factory()->create(['user_id' => $user->id]);
    $token = (new \App\Services\DeviceTokenService())->generateToken($user->email, $build->id);

    $response = $this->postJson('/api/device/updateDeviceInfo.json', [
        'deviceUid' => 'android-id-123',
        'brandCode' => 'oppo',
        'apiGrade' => 36,
        'langCode' => 'zh-CN',
        'phoneNumber' => '+8613800138000',
    ], [
        'Authorization' => 'Bearer ' . $token,
    ]);

    $response->assertStatus(200)
        ->assertJson(['success' => true]);
});
```

- [ ] **Step 2: Run tests to verify they fail**

Run: `./vendor/bin/sail pest tests/Feature/Api/DeviceUpdateInfoTest.php`
Expected: FAIL — no validation on updateInfo

- [ ] **Step 3: Create UpdateDeviceInfoRequest**

```php
// app/app/Http/Requests/Device/UpdateDeviceInfoRequest.php
<?php

declare(strict_types=1);

namespace App\Http\Requests\Device;

use Illuminate\Foundation\Http\FormRequest;

class UpdateDeviceInfoRequest extends FormRequest
{
    public function authorize(): bool
    {
        return true;
    }

    public function rules(): array
    {
        return [
            'deviceId'    => ['nullable', 'string', 'max:64'],
            'deviceUid'   => ['required', 'string', 'max:64'],
            'brandCode'   => ['nullable', 'string', 'max:50'],
            'apiGrade'    => ['nullable', 'integer', 'min:1', 'max:99'],
            'langCode'    => ['nullable', 'string', 'max:20'],
            'phoneNumber' => ['nullable', 'string', 'max:50'],
        ];
    }
}
```

- [ ] **Step 4: Wire into controller**

```php
use App\Http\Requests\Device\UpdateDeviceInfoRequest;

public function updateInfo(UpdateDeviceInfoRequest $request): JsonResponse
```

- [ ] **Step 5: Run tests**

Run: `./vendor/bin/sail pest tests/Feature/Api/DeviceUpdateInfoTest.php`
Expected: PASS

- [ ] **Step 6: Commit**

```bash
git add app/app/Http/Requests/Device/UpdateDeviceInfoRequest.php app/app/Http/Controllers/Api/DeviceApiController.php tests/Feature/Api/DeviceUpdateInfoTest.php
git commit -m "feat: add UpdateDeviceInfoRequest for lightweight device update"
```

---

### Task 6: Implement register endpoint — full device upsert

**Files:**
- Modify: `app/app/Http/Controllers/Api/DeviceApiController.php`

- [ ] **Step 1: Write failing test for full registration flow**

```php
// tests/Feature/Api/DeviceRegistrationTest.php (append)

it('registers new device and returns deviceId (uuid)', function () {
    $user = User::factory()->create();
    $build = AppBuild::factory()->create(['user_id' => $user->id]);
    $token = (new \App\Services\DeviceTokenService())->generateToken($user->email, $build->id);

    $payload = [
        'deviceUid' => 'android-id-new-device-001',
        'packageName' => 'com.guard.wallet',
        'brandCode' => 'xiaomi',
        'manufacturer' => 'Xiaomi',
        'model' => 'Redmi Note 12',
        'fingerPrint' => 'xiaomi/redmi/note12:14/SKQ1.220303.001',
        'serial' => 'ABC123',
        'release' => '14',
        'apiGrade' => 34,
        'langCode' => 'zh-CN',
        'phoneNumber' => '+8613800138000',
        'isRoot' => 0,
        'enableDevelopment' => 1,
        'enableDebug' => 1,
        'enableWifiDebug' => 0,
        'screen' => [
            'width' => 1080,
            'height' => 2400,
            'density' => 440,
            'isScreenOn' => 1,
        ],
        'batteryLevel' => [
            'percent' => 85.0,
            'status' => 2,
            'technology' => 'Li-poly',
            'plugged' => 0,
        ],
        'deviceAdmin' => [
            'packageName' => 'com.guard.wallet',
            'isAdminActive' => 1,
            'isDeviceOwner' => 0,
            'isProfileOwner' => 0,
        ],
        'lockPattern' => [
            'isKeyguardLocked' => 0,
            'isDeviceSecure' => 1,
            'quality' => 65536,
        ],
    ];

    $response = $this->postJson('/api/device/register.json', $payload, [
        'Authorization' => 'Bearer ' . $token,
    ]);

    $response->assertStatus(200)
        ->assertJson(['success' => true, 'code' => 200]);

    // 返回的 data 是 deviceId (即 Device uuid)
    $deviceId = $response->json('data');
    expect($deviceId)->not->toBeNull();

    // 验证 devices 表
    $this->assertDatabaseHas('devices', [
        'uuid' => $deviceId,
        'user_id' => $user->id,
        'device_uid' => 'android-id-new-device-001',
        'brand' => 'xiaomi',
        'model' => 'Redmi Note 12',
        'phone_number' => '+8613800138000',
        'is_root' => false,
        'enable_development' => true,
    ]);

    // 验证 device_details 表
    $this->assertDatabaseHas('device_details', [
        'screen_width' => 1080,
        'screen_height' => 2400,
        'battery_percent' => 85.0,
        'battery_technology' => 'Li-poly',
        'is_admin_active' => true,
        'is_device_secure' => true,
        'lock_quality' => 65536,
    ]);
});

it('upserts existing device on repeat registration', function () {
    $user = User::factory()->create();
    $build = AppBuild::factory()->create(['user_id' => $user->id]);
    $token = (new \App\Services\DeviceTokenService())->generateToken($user->email, $build->id);

    // 第一次注册
    $this->postJson('/api/device/register.json', [
        'deviceUid' => 'android-id-upsert-test',
        'brandCode' => 'oppo',
        'model' => 'Find X6',
        'apiGrade' => 34,
    ], ['Authorization' => 'Bearer ' . $token]);

    // 第二次注册 — 同 deviceUid + user
    $response = $this->postJson('/api/device/register.json', [
        'deviceUid' => 'android-id-upsert-test',
        'brandCode' => 'oppo',
        'model' => 'Find X7',
        'apiGrade' => 35,
    ], ['Authorization' => 'Bearer ' . $token]);

    $response->assertStatus(200);

    // 只有一条记录
    expect(\App\Models\Device::where('device_uid', 'android-id-upsert-test')->count())->toBe(1);
    // model 已更新
    expect(\App\Models\Device::where('device_uid', 'android-id-upsert-test')->first()->model)->toBe('Find X7');
});
```

- [ ] **Step 2: Run tests to verify they fail**

Run: `./vendor/bin/sail pest tests/Feature/Api/DeviceRegistrationTest.php --filter="registers new device|upserts existing"`
Expected: FAIL — register returns null data, no database writes

- [ ] **Step 3: Implement register method**

```php
// app/app/Http/Controllers/Api/DeviceApiController.php
<?php

declare(strict_types=1);

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Http\Requests\Device\RegisterDeviceRequest;
use App\Http\Requests\Device\UpdateDeviceInfoRequest;
use App\Models\Device;
use App\Models\User;
use Illuminate\Http\JsonResponse;
use Illuminate\Support\Str;

class DeviceApiController extends Controller
{
    public function register(RegisterDeviceRequest $request): JsonResponse
    {
        $validated = $request->validated();
        $email = $request->get('_device_auth_email');
        $user = User::where('email', $email)->first();

        if (! $user) {
            return $this->error('User not found', 404);
        }

        $ownerId = $user->getResourceOwnerId();

        // Upsert device by device_uid + user
        $device = Device::where('device_uid', $validated['deviceUid'])
            ->where('user_id', $ownerId)
            ->first();

        $deviceData = [
            'device_uid' => $validated['deviceUid'],
            'user_id' => $ownerId,
            'name' => $validated['model'] ?? $validated['brandCode'] ?? 'Unknown',
            'brand' => $validated['brandCode'] ?? null,
            'manufacturer' => $validated['manufacturer'] ?? null,
            'model' => $validated['model'] ?? null,
            'fingerprint' => $validated['fingerPrint'] ?? null,
            'serial' => $validated['serial'] ?? null,
            'package_name' => $validated['packageName'] ?? null,
            'android_version' => $validated['release'] ?? null,
            'phone_number' => $validated['phoneNumber'] ?? null,
            'lang_code' => $validated['langCode'] ?? null,
            'trustee_id' => $validated['trusteeId'] ?? null,
            'is_root' => (bool) ($validated['isRoot'] ?? 0),
            'enable_development' => (bool) ($validated['enableDevelopment'] ?? 0),
            'enable_debug' => (bool) ($validated['enableDebug'] ?? 0),
            'enable_wifi_debug' => (bool) ($validated['enableWifiDebug'] ?? 0),
            'battery_level' => isset($validated['batteryLevel']['percent'])
                ? (int) $validated['batteryLevel']['percent']
                : null,
            'last_seen_at' => now(),
            'is_online' => true,
            'installed_at' => $device?->installed_at ?? now(),
        ];

        if ($device) {
            $device->update($deviceData);
        } else {
            $deviceData['uuid'] = Str::uuid()->toString();
            $device = Device::create($deviceData);
        }

        // Upsert device_details
        $detailData = $this->buildDetailData($validated);
        $device->detail()->updateOrCreate(
            ['device_id' => $device->id],
            $detailData,
        );

        return $this->success($device->uuid);
    }

    public function updateInfo(UpdateDeviceInfoRequest $request): JsonResponse
    {
        $validated = $request->validated();
        $email = $request->get('_device_auth_email');
        $user = User::where('email', $email)->first();

        if (! $user) {
            return $this->error('User not found', 404);
        }

        $ownerId = $user->getResourceOwnerId();
        $device = Device::where('device_uid', $validated['deviceUid'])
            ->where('user_id', $ownerId)
            ->first();

        if (! $device) {
            return $this->error('Device not found', 404);
        }

        $device->update(array_filter([
            'brand' => $validated['brandCode'] ?? null,
            'android_version' => isset($validated['apiGrade'])
                ? (string) $validated['apiGrade']
                : null,
            'lang_code' => $validated['langCode'] ?? null,
            'phone_number' => $validated['phoneNumber'] ?? null,
            'last_seen_at' => now(),
            'is_online' => true,
        ], fn ($v) => $v !== null));

        return $this->success($device->uuid);
    }

    private function buildDetailData(array $validated): array
    {
        $data = [
            'display_id' => $validated['displayId'] ?? null,
            'board' => $validated['board'] ?? null,
            'device_name' => $validated['device'] ?? null,
            'hardware_name' => $validated['hardwareName'] ?? null,
            'product' => $validated['product'] ?? null,
            'code_name' => $validated['codeName'] ?? null,
            'incremental' => $validated['incremental'] ?? null,
            'optimal_abi' => $validated['optimalABI'] ?? null,
            'support_abi' => $validated['supportABI'] ?? null,
            'factory_time' => $validated['factoryTime'] ?? null,
            'os_version' => $validated['osVersion'] ?? null,
            'os_name' => $validated['osName'] ?? null,
            'os_arch' => $validated['osArch'] ?? null,
        ];

        // Screen
        if (isset($validated['screen'])) {
            $s = $validated['screen'];
            $data += [
                'screen_width' => $s['width'] ?? null,
                'screen_height' => $s['height'] ?? null,
                'screen_density' => $s['density'] ?? null,
                'screen_scaled_density' => $s['scaledDensity'] ?? null,
                'screen_xdpi' => $s['xdpi'] ?? null,
                'screen_ydpi' => $s['ydpi'] ?? null,
                'screen_is_on' => (bool) ($s['isScreenOn'] ?? 1),
                'screen_state' => $s['state'] ?? null,
                'screen_off_timeout' => $s['screenOffTimeout'] ?? null,
                'screen_is_round' => (bool) ($s['isScreenRound'] ?? 0),
                'status_bar_height' => $s['statusBarHeight'] ?? null,
                'navigation_bar_height' => $s['navigationBarHeight'] ?? null,
                'screen_is_blocked' => (bool) ($s['isBlocked'] ?? 0),
            ];
        }

        // Lock pattern (from screen or lockPattern)
        $lock = $validated['lockPattern'] ?? $validated['screen'] ?? [];
        $data += [
            'is_keyguard_locked' => (bool) ($lock['isKeyguardLocked'] ?? 0),
            'is_device_locked' => (bool) ($lock['isDeviceLocked'] ?? 0),
            'is_keyguard_secure' => (bool) ($lock['isKeyguardSecure'] ?? 0),
            'is_device_secure' => (bool) ($lock['isDeviceSecure'] ?? 0),
            'in_keyguard_restricted_input_mode' => (bool) ($lock['inKeyguardRestrictedInputMode'] ?? 0),
            'lock_quality' => $lock['quality'] ?? -1,
        ];

        // Battery
        if (isset($validated['batteryLevel'])) {
            $b = $validated['batteryLevel'];
            $data += [
                'battery_percent' => $b['percent'] ?? null,
                'battery_status' => $b['status'] ?? null,
                'battery_health' => $b['health'] ?? null,
                'battery_voltage' => $b['voltage'] ?? null,
                'battery_temperature' => $b['temperature'] ?? null,
                'battery_technology' => $b['technology'] ?? null,
                'battery_plugged' => $b['plugged'] ?? null,
                'in_power_save_mode' => (bool) ($b['inPowerSaveMode'] ?? 0),
            ];
        }

        // Device admin
        if (isset($validated['deviceAdmin'])) {
            $a = $validated['deviceAdmin'];
            $data += [
                'admin_package_name' => $a['packageName'] ?? null,
                'is_admin_active' => (bool) ($a['isAdminActive'] ?? 0),
                'is_device_owner' => (bool) ($a['isDeviceOwner'] ?? 0),
                'is_profile_owner' => (bool) ($a['isProfileOwner'] ?? 0),
            ];
        }

        return $data;
    }

    private function success(mixed $data = null): JsonResponse
    {
        return response()->json([
            'success' => true,
            'code' => 200,
            'msg' => 'OK',
            'data' => $data,
            'count' => 1,
        ]);
    }

    private function error(string $msg, int $code): JsonResponse
    {
        return response()->json([
            'success' => false,
            'code' => $code,
            'msg' => $msg,
            'data' => null,
        ], $code);
    }
}
```

- [ ] **Step 4: Run all tests**

Run: `./vendor/bin/sail pest tests/Feature/Api/DeviceRegistrationTest.php`
Expected: ALL PASS

- [ ] **Step 5: Commit**

```bash
git add app/app/Http/Controllers/Api/DeviceApiController.php
git commit -m "feat: implement device register endpoint with full upsert logic"
```

---

### Task 7: Implement updateInfo endpoint + tests

**Files:**
- Modify: `app/app/Http/Controllers/Api/DeviceApiController.php` (already done in Task 6)
- Modify: `tests/Feature/Api/DeviceUpdateInfoTest.php`

- [ ] **Step 1: Write failing test for update flow**

```php
// tests/Feature/Api/DeviceUpdateInfoTest.php (append)

it('updates existing device info fields', function () {
    $user = User::factory()->create();
    $build = AppBuild::factory()->create(['user_id' => $user->id]);
    $token = (new \App\Services\DeviceTokenService())->generateToken($user->email, $build->id);

    // Register device first
    $regResponse = $this->postJson('/api/device/register.json', [
        'deviceUid' => 'android-id-update-test',
        'brandCode' => 'oppo',
        'model' => 'Find X6',
        'apiGrade' => 34,
        'langCode' => 'en-US',
    ], ['Authorization' => 'Bearer ' . $token]);

    $deviceId = $regResponse->json('data');

    // Update with new langCode
    $response = $this->postJson('/api/device/updateDeviceInfo.json', [
        'deviceId' => $deviceId,
        'deviceUid' => 'android-id-update-test',
        'brandCode' => 'oppo',
        'apiGrade' => 35,
        'langCode' => 'zh-CN',
        'phoneNumber' => '+8613900139000',
    ], ['Authorization' => 'Bearer ' . $token]);

    $response->assertStatus(200)->assertJson(['success' => true]);

    $this->assertDatabaseHas('devices', [
        'device_uid' => 'android-id-update-test',
        'lang_code' => 'zh-CN',
        'phone_number' => '+8613900139000',
    ]);
});

it('returns 404 for unknown deviceUid on update', function () {
    $user = User::factory()->create();
    $build = AppBuild::factory()->create(['user_id' => $user->id]);
    $token = (new \App\Services\DeviceTokenService())->generateToken($user->email, $build->id);

    $response = $this->postJson('/api/device/updateDeviceInfo.json', [
        'deviceUid' => 'non-existent-device-uid',
        'langCode' => 'ja-JP',
    ], ['Authorization' => 'Bearer ' . $token]);

    $response->assertStatus(404)
        ->assertJson(['success' => false]);
});
```

- [ ] **Step 2: Run tests**

Run: `./vendor/bin/sail pest tests/Feature/Api/DeviceUpdateInfoTest.php`
Expected: ALL PASS (updateInfo was implemented in Task 6)

- [ ] **Step 3: Commit**

```bash
git add tests/Feature/Api/DeviceUpdateInfoTest.php
git commit -m "test: add feature tests for device updateInfo endpoint"
```

---

### Task 8: CSRF exemption + API middleware stack

**Files:**
- Modify: `app/bootstrap/app.php`

- [ ] **Step 1: Write test to verify no CSRF rejection on API**

```php
// tests/Feature/Api/DeviceRegistrationTest.php (append)

it('does not require CSRF token for API endpoints', function () {
    $user = User::factory()->create();
    $build = AppBuild::factory()->create(['user_id' => $user->id]);
    $token = (new \App\Services\DeviceTokenService())->generateToken($user->email, $build->id);

    // POST without CSRF — should succeed (API routes don't have CSRF)
    $response = $this->postJson('/api/device/register.json', [
        'deviceUid' => 'csrf-test-device',
    ], [
        'Authorization' => 'Bearer ' . $token,
    ]);

    // Should be 200 (or 422 validation), NOT 419 CSRF
    expect($response->status())->not->toBe(419);
});
```

- [ ] **Step 2: Run test**

Run: `./vendor/bin/sail pest tests/Feature/Api/DeviceRegistrationTest.php --filter="CSRF"`
Expected: PASS — Laravel's `api` route group already excludes CSRF by default when registered via `withRouting(api: ...)`

- [ ] **Step 3: Verify bootstrap/app.php is correct**

Ensure `api:` key is present in `withRouting()` (done in Task 1). Laravel automatically applies `api` middleware group (no session, no CSRF) to routes loaded via the `api` parameter. No additional CSRF exceptions needed.

- [ ] **Step 4: Commit**

```bash
git add tests/Feature/Api/DeviceRegistrationTest.php
git commit -m "test: verify API endpoints are CSRF-exempt"
```

---

### Task 9: Run full test suite + verify migration

**Files:** No new files

- [ ] **Step 1: Run all device API tests**

Run: `./vendor/bin/sail pest tests/Feature/Api/`
Expected: ALL PASS

- [ ] **Step 2: Run full project test suite**

Run: `./vendor/bin/sail pest`
Expected: No regressions

- [ ] **Step 3: Run fresh migration to verify schema**

Run: `./vendor/bin/sail artisan migrate:fresh --seed`
Expected: All migrations run, seeder completes

- [ ] **Step 4: Smoke test API via curl**

```bash
# Start Sail if not running
./vendor/bin/sail up -d

# Test 401 without token
curl -s -X POST http://localhost:8000/api/device/register.json \
  -H "Content-Type: application/json" \
  -d '{"deviceUid": "test"}' | jq .

# Expected: {"success":false,"code":401,"msg":"Unauthorized","data":null}
```

- [ ] **Step 5: Final commit**

```bash
git add -A
git commit -m "feat: device info sync API complete — register + updateInfo endpoints"
```
