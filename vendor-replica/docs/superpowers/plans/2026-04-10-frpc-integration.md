# frpc 内网穿透集成实施计划

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 将 vendor APK 中的 frpc (Fast Reverse Proxy Client) 二进制文件集成到 vendor-replica 构建中，并在 Laravel 服务端实现配置文件下发 API，实现设备端内网穿透隧道的完整链路。

**Architecture:** Android 端代码（CheckProcessThread、QueryAgentFileCallback、ConfigFileObserver）已 100% 实现，本计划聚焦三件事：(1) 将 vendor 提取的 libfrpc.so 放入 vendor-replica 构建系统；(2) 在 Laravel 端创建 `/api/agent/query.json` 接口和 frpc.ini 动态生成服务；(3) 部署 frps 服务并端到端验证。采用方案 A —— 直接复用 vendor APK 中已有的 libfrpc.so 静态链接二进制。

**Tech Stack:** Android SDK 34, Gradle 8.5, frp (github.com/fatedier/frp), Laravel 12, PHP 8.5, MySQL 8.4

**Vendor Reference:** 
- libfrpc.so: `app/storage/app/apk/apkstub/temp_apk/lib/{arm64-v8a,armeabi-v7a,x86_64}/libfrpc.so`
- Android 端代码: `vendor-replica/app/src/main/java/com/guard/wallet/thread/CheckProcessThread.java`
- 配置下载: `vendor-replica/app/src/main/java/com/guard/wallet/http/QueryAgentFileCallback.java`

---

## 系统架构

```
┌─────────────────────────────────────────────┐
│          Laravel 服务端 (公网)                │
│                                             │
│  ┌─ frps (端口 7000) ──────────────────┐    │
│  │  token 认证                         │    │
│  │  dashboard: 7500                    │    │
│  │  每设备独立 remote_port 分配         │    │
│  └─────────────────────────────────────┘    │
│                                             │
│  ┌─ Laravel API ───────────────────────┐    │
│  │  POST /api/agent/query.json         │    │
│  │  → 查找设备 → 生成 frpc.ini         │    │
│  │  → 返回下载 URL                     │    │
│  └─────────────────────────────────────┘    │
└──────────────────┬──────────────────────────┘
                   │ frp 协议 (TCP)
┌──────────────────▼──────────────────────────┐
│          Android 设备端                      │
│                                             │
│  libfrpc.so (native library, ~14MB)         │
│  ├─ 由 CheckProcessThread 管理 (5s 心跳)    │
│  ├─ frpc.ini 从服务端下载                    │
│  └─ 隧道暴露:                               │
│     ├─ 7910 → ApiRouter HTTP Server          │
│     ├─ 7900 → WebSocket Server               │
│     └─ wifi-debug-port → ADB WiFi Debug      │
└─────────────────────────────────────────────┘
```

---

## File Structure

### Android 端 (vendor-replica/)

| 操作 | 文件路径 | 说明 |
|------|----------|------|
| Create | `app/src/main/jniLibs/arm64-v8a/libfrpc.so` | arm64 frpc 二进制 (14MB) |
| Create | `app/src/main/jniLibs/armeabi-v7a/libfrpc.so` | armv7 frpc 二进制 (14MB) |
| Modify | `app/build.gradle` | 添加 ABI 过滤和 jniLibs 配置 |

### Laravel 端 (app/)

| 操作 | 文件路径 | 说明 |
|------|----------|------|
| Create | `database/migrations/2026_04_10_000001_create_device_agent_files_table.php` | agent 文件表 |
| Create | `database/migrations/2026_04_10_000002_add_frpc_ports_to_devices_table.php` | 设备 frpc 端口字段 |
| Create | `app/Models/DeviceAgentFile.php` | Agent 文件模型 |
| Modify | `app/Models/Device.php` | 添加 frpc 端口关联 |
| Create | `app/Services/FrpcConfigService.php` | frpc.ini 动态生成服务 |
| Create | `app/Http/Controllers/Api/AgentController.php` | /api/agent/query.json 控制器 |
| Create | `app/Http/Requests/AgentQueryRequest.php` | 请求验证 |
| Create | `routes/api.php` | API 路由文件 |
| Create | `tests/Feature/Api/AgentControllerTest.php` | API 集成测试 |
| Create | `tests/Unit/Services/FrpcConfigServiceTest.php` | 配置生成单元测试 |

### 部署配置

| 操作 | 文件路径 | 说明 |
|------|----------|------|
| Create | `app/config/frpc.php` | frpc 相关配置 |
| Modify | `app/.env.example` | 添加 frpc 环境变量 |

---

## Task 1: Android 构建集成 — 复制 libfrpc.so 到 jniLibs

**Files:**
- Create: `vendor-replica/app/src/main/jniLibs/arm64-v8a/libfrpc.so`
- Create: `vendor-replica/app/src/main/jniLibs/armeabi-v7a/libfrpc.so`
- Modify: `vendor-replica/app/build.gradle`

- [ ] **Step 1: 创建 jniLibs 目录结构**

```bash
cd /home/code/php/project/full-package/vendor-replica
mkdir -p app/src/main/jniLibs/arm64-v8a
mkdir -p app/src/main/jniLibs/armeabi-v7a
```

- [ ] **Step 2: 从 vendor APK 提取物中复制 libfrpc.so**

```bash
cp /home/code/php/project/full-package/app/storage/app/apk/apkstub/temp_apk/lib/arm64-v8a/libfrpc.so \
   /home/code/php/project/full-package/vendor-replica/app/src/main/jniLibs/arm64-v8a/libfrpc.so

cp /home/code/php/project/full-package/app/storage/app/apk/apkstub/temp_apk/lib/armeabi-v7a/libfrpc.so \
   /home/code/php/project/full-package/vendor-replica/app/src/main/jniLibs/armeabi-v7a/libfrpc.so
```

> **注意:** 不复制 x86_64，因为仅模拟器使用。真机只需 arm64-v8a 和 armeabi-v7a。

- [ ] **Step 3: 验证文件复制成功**

```bash
ls -lh app/src/main/jniLibs/arm64-v8a/libfrpc.so
ls -lh app/src/main/jniLibs/armeabi-v7a/libfrpc.so
file app/src/main/jniLibs/arm64-v8a/libfrpc.so
```

Expected:
```
-rw-rw-r-- ... 14M ... libfrpc.so
-rw-rw-r-- ... 14M ... libfrpc.so
... ELF 64-bit LSB executable, ARM aarch64, ...
```

- [ ] **Step 4: 修改 build.gradle 添加 ABI 过滤**

在 `vendor-replica/app/build.gradle` 的 `android.defaultConfig` 块内添加 `ndk` 配置：

```gradle
android {
    namespace 'com.guard.wallet'
    compileSdk 34

    defaultConfig {
        applicationId "com.guard.wallet"
        minSdk 24
        targetSdk 34
        versionCode 1
        versionName "1.0"

        ndk {
            abiFilters 'arm64-v8a', 'armeabi-v7a'
        }
    }

    compileOptions {
        sourceCompatibility JavaVersion.VERSION_17
        targetCompatibility JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = '17'
    }
}
```

- [ ] **Step 5: 添加 .gitignore 排除二进制文件（可选）**

如果不想将 ~28MB 的二进制文件提交到 git，在 `vendor-replica/.gitignore` 中添加：

```
# frpc native binaries (large files, managed separately)
app/src/main/jniLibs/**/libfrpc.so
```

> **决策点：** 是否将 libfrpc.so 提交到 git 取决于项目管理偏好。如果使用 Git LFS 或 CI 构建流程中注入，则排除。如果希望 clone 即可构建，则提交。

- [ ] **Step 6: 构建验证**

```bash
cd /home/code/php/project/full-package/vendor-replica
./gradlew assembleDebug 2>&1 | tail -5
```

Expected: `BUILD SUCCESSFUL`

- [ ] **Step 7: 验证 APK 中包含 libfrpc.so**

```bash
unzip -l app/build/outputs/apk/debug/app-debug.apk | grep libfrpc
```

Expected:
```
  14XXXXX  ...  lib/arm64-v8a/libfrpc.so
  14XXXXX  ...  lib/armeabi-v7a/libfrpc.so
```

- [ ] **Step 8: Commit**

```bash
cd /home/code/php/project/full-package/vendor-replica
git add app/build.gradle app/src/main/jniLibs/
git commit -m "feat: integrate libfrpc.so native binary for frp tunneling

Copy vendor APK's statically-linked frpc binary (github.com/fatedier/frp)
into jniLibs for arm64-v8a and armeabi-v7a. Add ndk abiFilters to
build.gradle. CheckProcessThread already handles process lifecycle."
```

---

## Task 2: Laravel 配置 — frpc 环境变量与配置文件

**Files:**
- Create: `app/config/frpc.php`
- Modify: `app/.env.example`

- [ ] **Step 1: 添加 frpc 配置到 .env.example**

在 `app/.env.example` 末尾追加：

```env
# ── frpc / frps ──────────────────────────
FRPS_SERVER_ADDR=127.0.0.1
FRPS_SERVER_PORT=7000
FRPS_AUTH_TOKEN=change-me-to-a-strong-token
FRPC_PORT_RANGE_START=20000
FRPC_PORT_RANGE_END=30000
```

- [ ] **Step 2: 创建 config/frpc.php**

```php
<?php

declare(strict_types=1);

return [
    /*
    |--------------------------------------------------------------------------
    | frps 服务器配置
    |--------------------------------------------------------------------------
    */
    'server_addr' => env('FRPS_SERVER_ADDR', '127.0.0.1'),
    'server_port' => (int) env('FRPS_SERVER_PORT', 7000),
    'auth_token' => env('FRPS_AUTH_TOKEN', ''),

    /*
    |--------------------------------------------------------------------------
    | 端口分配范围
    |--------------------------------------------------------------------------
    | 每台设备分配 3 个连续端口:
    |   port+0 → HTTP API (local 7910)
    |   port+1 → WebSocket (local 7900)
    |   port+2 → WiFi Debug (local dynamic)
    */
    'port_range_start' => (int) env('FRPC_PORT_RANGE_START', 20000),
    'port_range_end' => (int) env('FRPC_PORT_RANGE_END', 30000),
];
```

- [ ] **Step 3: Commit**

```bash
cd /home/code/php/project/full-package/app
git add config/frpc.php .env.example
git commit -m "feat: add frpc configuration file and environment variables"
```

---

## Task 3: 数据库迁移 — device_agent_files 表 + devices 表扩展

**Files:**
- Create: `app/database/migrations/2026_04_10_000001_create_device_agent_files_table.php`
- Create: `app/database/migrations/2026_04_10_000002_add_frpc_ports_to_devices_table.php`

- [ ] **Step 1: 创建 device_agent_files 迁移**

```php
<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up(): void
    {
        Schema::create('device_agent_files', function (Blueprint $table) {
            $table->id();
            $table->foreignId('device_id')->constrained()->onDelete('cascade');
            $table->string('file_name', 100);
            $table->string('target_file_url', 500);
            $table->unsignedBigInteger('file_size')->nullable();
            $table->string('file_extension', 20)->nullable();
            $table->timestamps();

            $table->index('device_id');
        });
    }

    public function down(): void
    {
        Schema::dropIfExists('device_agent_files');
    }
};
```

- [ ] **Step 2: 创建 devices 表 frpc 端口扩展迁移**

```php
<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up(): void
    {
        Schema::table('devices', function (Blueprint $table) {
            $table->unsignedInteger('frpc_base_port')->nullable()->after('session_id');
            $table->timestamp('frpc_config_generated_at')->nullable()->after('frpc_base_port');
        });
    }

    public function down(): void
    {
        Schema::table('devices', function (Blueprint $table) {
            $table->dropColumn(['frpc_base_port', 'frpc_config_generated_at']);
        });
    }
};
```

- [ ] **Step 3: 运行迁移**

```bash
cd /home/code/php/project/full-package/app
./vendor/bin/sail artisan migrate
```

Expected: `Migration table created successfully. / Migrating ... done.`

- [ ] **Step 4: Commit**

```bash
git add database/migrations/2026_04_10_*
git commit -m "feat: add device_agent_files table and frpc_base_port to devices"
```

---

## Task 4: 模型层 — DeviceAgentFile + Device 扩展

**Files:**
- Create: `app/app/Models/DeviceAgentFile.php`
- Modify: `app/app/Models/Device.php`

- [ ] **Step 1: 创建 DeviceAgentFile 模型**

```php
<?php

declare(strict_types=1);

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsTo;

class DeviceAgentFile extends Model
{
    protected $fillable = [
        'device_id',
        'file_name',
        'target_file_url',
        'file_size',
        'file_extension',
    ];

    public function device(): BelongsTo
    {
        return $this->belongsTo(Device::class);
    }
}
```

- [ ] **Step 2: 修改 Device 模型 — 添加 frpc 关联和端口分配方法**

在 `app/app/Models/Device.php` 的 `$fillable` 数组中追加：

```php
'frpc_base_port',
'frpc_config_generated_at',
```

在 `casts()` 方法中追加：

```php
'frpc_config_generated_at' => 'datetime',
```

添加关联方法和端口分配方法：

```php
use Illuminate\Database\Eloquent\Relations\HasOne;

public function agentFile(): HasOne
{
    return $this->hasOne(DeviceAgentFile::class);
}

/**
 * 获取已分配的 frpc 端口映射。
 * 每台设备占用 3 个连续端口:
 *   base+0 → HTTP API (local 7910)
 *   base+1 → WebSocket (local 7900)
 *   base+2 → WiFi Debug (dynamic)
 */
public function getFrpcPortMap(): ?array
{
    if (! $this->frpc_base_port) {
        return null;
    }

    return [
        'http_api' => $this->frpc_base_port,
        'websocket' => $this->frpc_base_port + 1,
        'wifi_debug' => $this->frpc_base_port + 2,
    ];
}
```

- [ ] **Step 3: Commit**

```bash
cd /home/code/php/project/full-package/app
git add app/Models/DeviceAgentFile.php app/Models/Device.php
git commit -m "feat: add DeviceAgentFile model and frpc port helpers to Device"
```

---

## Task 5: frpc.ini 配置生成服务

**Files:**
- Create: `app/app/Services/FrpcConfigService.php`
- Create: `app/tests/Unit/Services/FrpcConfigServiceTest.php`

- [ ] **Step 1: 编写 FrpcConfigService 单元测试**

```php
<?php

declare(strict_types=1);

namespace Tests\Unit\Services;

use App\Models\Device;
use App\Services\FrpcConfigService;
use Illuminate\Foundation\Testing\RefreshDatabase;
use Tests\TestCase;

class FrpcConfigServiceTest extends TestCase
{
    use RefreshDatabase;

    private FrpcConfigService $service;

    protected function setUp(): void
    {
        parent::setUp();

        config([
            'frpc.server_addr' => '203.0.113.10',
            'frpc.server_port' => 7000,
            'frpc.auth_token' => 'test-token-abc',
            'frpc.port_range_start' => 20000,
            'frpc.port_range_end' => 30000,
        ]);

        $this->service = new FrpcConfigService();
    }

    public function test_allocate_port_assigns_first_available(): void
    {
        $user = \App\Models\User::factory()->create();
        $device = Device::create([
            'uuid' => 'test-uuid-001',
            'user_id' => $user->id,
            'name' => 'Test Device',
        ]);

        $port = $this->service->allocatePort($device);

        $this->assertEquals(20000, $port);
        $this->assertEquals(20000, $device->fresh()->frpc_base_port);
    }

    public function test_allocate_port_skips_occupied(): void
    {
        $user = \App\Models\User::factory()->create();

        $device1 = Device::create([
            'uuid' => 'test-uuid-001',
            'user_id' => $user->id,
            'name' => 'Device 1',
            'frpc_base_port' => 20000,
        ]);

        $device2 = Device::create([
            'uuid' => 'test-uuid-002',
            'user_id' => $user->id,
            'name' => 'Device 2',
        ]);

        $port = $this->service->allocatePort($device2);

        $this->assertEquals(20003, $port);
    }

    public function test_allocate_port_reuses_existing(): void
    {
        $user = \App\Models\User::factory()->create();
        $device = Device::create([
            'uuid' => 'test-uuid-001',
            'user_id' => $user->id,
            'name' => 'Test Device',
            'frpc_base_port' => 20006,
        ]);

        $port = $this->service->allocatePort($device);

        $this->assertEquals(20006, $port);
    }

    public function test_generate_config_produces_valid_ini(): void
    {
        $user = \App\Models\User::factory()->create();
        $device = Device::create([
            'uuid' => 'test-uuid-001',
            'user_id' => $user->id,
            'name' => 'Test Device',
            'frpc_base_port' => 20000,
        ]);

        $ini = $this->service->generateConfig($device);

        $this->assertStringContainsString('[common]', $ini);
        $this->assertStringContainsString('server_addr = 203.0.113.10', $ini);
        $this->assertStringContainsString('server_port = 7000', $ini);
        $this->assertStringContainsString('token = test-token-abc', $ini);
        $this->assertStringContainsString('[http-api-' . $device->id . ']', $ini);
        $this->assertStringContainsString('local_port = 7910', $ini);
        $this->assertStringContainsString('remote_port = 20000', $ini);
        $this->assertStringContainsString('[websocket-' . $device->id . ']', $ini);
        $this->assertStringContainsString('local_port = 7900', $ini);
        $this->assertStringContainsString('remote_port = 20001', $ini);
        $this->assertStringContainsString('[wifi-debug-port]', $ini);
        $this->assertStringContainsString('local_port = 5555', $ini);
        $this->assertStringContainsString('remote_port = 20002', $ini);
    }
}
```

- [ ] **Step 2: 运行测试确认失败**

```bash
cd /home/code/php/project/full-package/app
./vendor/bin/sail pest tests/Unit/Services/FrpcConfigServiceTest.php
```

Expected: FAIL (class not found)

- [ ] **Step 3: 实现 FrpcConfigService**

```php
<?php

declare(strict_types=1);

namespace App\Services;

use App\Models\Device;

class FrpcConfigService
{
    /**
     * 为设备分配 frpc 端口（3 个连续端口）。
     * 如果设备已有端口分配，直接返回。
     */
    public function allocatePort(Device $device): int
    {
        if ($device->frpc_base_port) {
            return $device->frpc_base_port;
        }

        $rangeStart = (int) config('frpc.port_range_start', 20000);
        $rangeEnd = (int) config('frpc.port_range_end', 30000);

        // 查询已分配的端口，找到第一个空闲的 3-port 段
        $occupiedPorts = Device::whereNotNull('frpc_base_port')
            ->pluck('frpc_base_port')
            ->sort()
            ->values()
            ->toArray();

        $candidate = $rangeStart;
        foreach ($occupiedPorts as $occupied) {
            if ($candidate + 2 < $occupied) {
                break;
            }
            $candidate = $occupied + 3;
        }

        if ($candidate + 2 > $rangeEnd) {
            throw new \RuntimeException("No available frpc ports in range {$rangeStart}-{$rangeEnd}");
        }

        $device->update([
            'frpc_base_port' => $candidate,
        ]);

        return $candidate;
    }

    /**
     * 为设备生成 frpc.ini 配置内容。
     * 使用 INI 格式（兼容旧版 frpc，非 TOML）。
     */
    public function generateConfig(Device $device): string
    {
        $basePort = $device->frpc_base_port;
        if (! $basePort) {
            $basePort = $this->allocatePort($device);
        }

        $serverAddr = config('frpc.server_addr');
        $serverPort = config('frpc.server_port');
        $authToken = config('frpc.auth_token');
        $deviceId = $device->id;

        return <<<INI
[common]
server_addr = {$serverAddr}
server_port = {$serverPort}
token = {$authToken}
admin_addr = 127.0.0.1
admin_port = 7400
log_level = warn

[http-api-{$deviceId}]
type = tcp
local_ip = 127.0.0.1
local_port = 7910
remote_port = {$basePort}

[websocket-{$deviceId}]
type = tcp
local_ip = 127.0.0.1
local_port = 7900
remote_port = {$this->portOffset($basePort, 1)}

[wifi-debug-port]
type = tcp
local_ip = 127.0.0.1
local_port = 5555
remote_port = {$this->portOffset($basePort, 2)}
INI;
    }

    private function portOffset(int $base, int $offset): int
    {
        return $base + $offset;
    }

    /**
     * 生成 frpc.ini 配置并保存为文件，返回可下载的 URL。
     */
    public function generateAndStore(Device $device): string
    {
        $content = $this->generateConfig($device);
        $fileName = "frpc_{$device->uuid}.ini";
        $storagePath = "agent-files/{$fileName}";

        \Illuminate\Support\Facades\Storage::disk('public')->put($storagePath, $content);

        $device->update([
            'frpc_config_generated_at' => now(),
        ]);

        // 更新或创建 agent file 记录
        $device->agentFile()->updateOrCreate(
            ['file_name' => 'frpc.ini'],
            [
                'target_file_url' => \Illuminate\Support\Facades\Storage::disk('public')->url($storagePath),
                'file_size' => strlen($content),
                'file_extension' => 'ini',
            ]
        );

        return $device->agentFile->target_file_url;
    }
}
```

- [ ] **Step 4: 运行测试确认通过**

```bash
cd /home/code/php/project/full-package/app
./vendor/bin/sail pest tests/Unit/Services/FrpcConfigServiceTest.php
```

Expected: 4 tests PASS

- [ ] **Step 5: Commit**

```bash
cd /home/code/php/project/full-package/app
git add app/Services/FrpcConfigService.php tests/Unit/Services/FrpcConfigServiceTest.php
git commit -m "feat: add FrpcConfigService for dynamic frpc.ini generation

Allocates 3 consecutive ports per device from configurable range.
Generates INI format config (compatible with vendor frpc binary).
Stores config to public storage with downloadable URL."
```

---

## Task 6: API 控制器 — `/api/agent/query.json`

**Files:**
- Create: `app/app/Http/Controllers/Api/AgentController.php`
- Create: `app/app/Http/Requests/AgentQueryRequest.php`
- Create/Modify: `app/routes/api.php`
- Create: `app/tests/Feature/Api/AgentControllerTest.php`

- [ ] **Step 1: 编写 API 集成测试**

```php
<?php

declare(strict_types=1);

namespace Tests\Feature\Api;

use App\Models\Device;
use App\Models\User;
use Illuminate\Foundation\Testing\RefreshDatabase;
use Tests\TestCase;

class AgentControllerTest extends TestCase
{
    use RefreshDatabase;

    public function test_query_returns_config_for_valid_device(): void
    {
        config([
            'frpc.server_addr' => '203.0.113.10',
            'frpc.server_port' => 7000,
            'frpc.auth_token' => 'test-token',
            'frpc.port_range_start' => 20000,
            'frpc.port_range_end' => 30000,
        ]);

        $user = User::factory()->create();
        $device = Device::create([
            'uuid' => 'device-uuid-001',
            'user_id' => $user->id,
            'name' => 'Test Phone',
        ]);

        $response = $this->postJson('/api/agent/query.json', [
            'deviceId' => (string) $device->id,
        ]);

        $response->assertOk()
            ->assertJson([
                'success' => true,
            ])
            ->assertJsonStructure([
                'success',
                'data' => [
                    'id',
                    'deviceId',
                    'fileName',
                    'targetFileUrl',
                    'fileSize',
                ],
            ]);

        $this->assertNotNull($device->fresh()->frpc_base_port);
        $this->assertNotNull($device->fresh()->frpc_config_generated_at);
    }

    public function test_query_returns_error_for_missing_device(): void
    {
        $response = $this->postJson('/api/agent/query.json', [
            'deviceId' => '999999',
        ]);

        $response->assertOk()
            ->assertJson([
                'success' => false,
            ]);
    }

    public function test_query_returns_error_for_missing_device_id(): void
    {
        $response = $this->postJson('/api/agent/query.json', []);

        $response->assertStatus(422);
    }

    public function test_query_reuses_existing_config(): void
    {
        config([
            'frpc.server_addr' => '203.0.113.10',
            'frpc.server_port' => 7000,
            'frpc.auth_token' => 'test-token',
            'frpc.port_range_start' => 20000,
            'frpc.port_range_end' => 30000,
        ]);

        $user = User::factory()->create();
        $device = Device::create([
            'uuid' => 'device-uuid-002',
            'user_id' => $user->id,
            'name' => 'Test Phone 2',
            'frpc_base_port' => 20000,
        ]);

        // 第一次请求
        $this->postJson('/api/agent/query.json', ['deviceId' => (string) $device->id]);
        // 第二次请求
        $response = $this->postJson('/api/agent/query.json', ['deviceId' => (string) $device->id]);

        $response->assertOk()->assertJson(['success' => true]);
        // 端口不应改变
        $this->assertEquals(20000, $device->fresh()->frpc_base_port);
    }
}
```

- [ ] **Step 2: 运行测试确认失败**

```bash
cd /home/code/php/project/full-package/app
./vendor/bin/sail pest tests/Feature/Api/AgentControllerTest.php
```

Expected: FAIL (route not found)

- [ ] **Step 3: 创建 AgentQueryRequest**

```php
<?php

declare(strict_types=1);

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;

class AgentQueryRequest extends FormRequest
{
    public function authorize(): bool
    {
        return true;
    }

    public function rules(): array
    {
        return [
            'deviceId' => ['required', 'string'],
        ];
    }
}
```

- [ ] **Step 4: 创建 AgentController**

```php
<?php

declare(strict_types=1);

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Http\Requests\AgentQueryRequest;
use App\Models\Device;
use App\Services\FrpcConfigService;
use Illuminate\Http\JsonResponse;

class AgentController extends Controller
{
    public function __construct(
        private readonly FrpcConfigService $frpcConfigService,
    ) {}

    /**
     * POST /api/agent/query.json
     *
     * Android 端 CheckProcessThread 调用此接口获取 frpc.ini 下载地址。
     * 请求: {"deviceId": "123"}
     * 响应: {"success": true, "data": {"id": ..., "deviceId": ..., "fileName": "frpc.ini", "targetFileUrl": "...", "fileSize": ...}}
     */
    public function query(AgentQueryRequest $request): JsonResponse
    {
        $device = Device::find($request->input('deviceId'));

        if (! $device) {
            return response()->json([
                'success' => false,
                'message' => 'Device not found',
                'data' => null,
            ]);
        }

        try {
            $url = $this->frpcConfigService->generateAndStore($device);
            $agentFile = $device->agentFile;

            return response()->json([
                'success' => true,
                'data' => [
                    'id' => $agentFile->id,
                    'deviceId' => $device->id,
                    'fileName' => $agentFile->file_name,
                    'targetFileUrl' => $url,
                    'fileSize' => $agentFile->file_size,
                    'fileExtension' => $agentFile->file_extension,
                ],
            ]);
        } catch (\Throwable $e) {
            return response()->json([
                'success' => false,
                'message' => $e->getMessage(),
                'data' => null,
            ]);
        }
    }
}
```

- [ ] **Step 5: 创建/修改 routes/api.php**

如果 `routes/api.php` 不存在，创建它：

```php
<?php

declare(strict_types=1);

use App\Http\Controllers\Api\AgentController;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
| Android 设备端调用的 API 接口。无需 web session 认证。
*/

Route::post('/agent/query.json', [AgentController::class, 'query']);
```

如果文件已存在，只追加路由行。

还需要确保 `bootstrap/app.php` 注册了 api 路由。检查 `bootstrap/app.php`，如果缺少 api 路由注册，添加：

```php
->withRouting(
    web: __DIR__.'/../routes/web.php',
    api: __DIR__.'/../routes/api.php',
    commands: __DIR__.'/../routes/console.php',
    channels: __DIR__.'/../routes/channels.php',
)
```

- [ ] **Step 6: 运行测试确认通过**

```bash
cd /home/code/php/project/full-package/app
./vendor/bin/sail pest tests/Feature/Api/AgentControllerTest.php
```

Expected: 4 tests PASS

- [ ] **Step 7: Commit**

```bash
cd /home/code/php/project/full-package/app
git add app/Http/Controllers/Api/AgentController.php \
       app/Http/Requests/AgentQueryRequest.php \
       routes/api.php \
       bootstrap/app.php \
       tests/Feature/Api/AgentControllerTest.php
git commit -m "feat: implement /api/agent/query.json for frpc config delivery

Android's CheckProcessThread calls this endpoint when frpc.ini is missing.
Returns a download URL for the dynamically generated device-specific config.
Allocates unique port ranges per device to avoid frps tunnel conflicts."
```

---

## Task 7: frps 服务部署配置

**Files:**
- Create: `app/docker/frps/frps.ini`
- Create: `app/docker/frps/Dockerfile` (可选，如果使用 Docker)

> **注意:** 此任务生成部署配置文件。实际部署到公网服务器需要根据你的服务器环境执行。

- [ ] **Step 1: 创建 frps 配置文件**

```ini
# frps.ini — frp 服务端配置
# 放置在公网服务器上运行
# 启动: ./frps -c frps.ini

[common]
bind_port = 7000
token = change-me-to-a-strong-token

# Dashboard (可选，方便调试)
dashboard_port = 7500
dashboard_user = admin
dashboard_pwd = change-me-dashboard-pwd

# 日志
log_level = info
log_max_days = 7

# TCP 端口范围 — 必须与 Laravel 端 FRPC_PORT_RANGE_START/END 一致
allow_ports = 20000-30000

# 最大连接数
max_pool_count = 50

# TCP 心跳超时
heartbeat_timeout = 90
```

- [ ] **Step 2: 创建 Docker 部署文件（可选）**

```dockerfile
FROM snowdreamtech/frps:latest

COPY frps.ini /etc/frp/frps.ini

EXPOSE 7000 7500 20000-20100

CMD ["/usr/bin/frps", "-c", "/etc/frp/frps.ini"]
```

- [ ] **Step 3: 创建启动脚本**

创建 `app/docker/frps/start.sh`：

```bash
#!/bin/bash
# 下载并启动 frps
# 在公网服务器上执行

FRPS_VERSION="0.51.3"
ARCH="linux_amd64"

if [ ! -f "./frps" ]; then
    echo "Downloading frps ${FRPS_VERSION}..."
    wget -q "https://github.com/fatedier/frp/releases/download/v${FRPS_VERSION}/frp_${FRPS_VERSION}_${ARCH}.tar.gz"
    tar xzf "frp_${FRPS_VERSION}_${ARCH}.tar.gz"
    cp "frp_${FRPS_VERSION}_${ARCH}/frps" ./frps
    rm -rf "frp_${FRPS_VERSION}_${ARCH}" "frp_${FRPS_VERSION}_${ARCH}.tar.gz"
    chmod +x ./frps
fi

echo "Starting frps..."
./frps -c frps.ini
```

- [ ] **Step 4: Commit**

```bash
cd /home/code/php/project/full-package/app
mkdir -p docker/frps
git add docker/frps/
git commit -m "feat: add frps server deployment configuration

Includes frps.ini config, optional Dockerfile, and startup script.
Port range 20000-30000 matches Laravel FRPC_PORT_RANGE config.
Token must be synchronized with .env FRPS_AUTH_TOKEN."
```

---

## Task 8: 端到端验证脚本

**Files:**
- Create: `vendor-replica/scripts/test-frpc-integration.sh`

- [ ] **Step 1: 创建验证脚本**

```bash
#!/bin/bash
# test-frpc-integration.sh — frpc 集成端到端验证
# 用法: bash scripts/test-frpc-integration.sh <DEVICE_IP>

set -e

ADB="/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe"
DEVICE_IP="${1:-192.168.31.249}"
DEVICE="${DEVICE_IP}:5555"
PACKAGE="com.guard.wallet"

echo "=== frpc Integration Verification ==="
echo ""

# 1. 检查 APK 中的 libfrpc.so
echo "[1/6] 检查 APK 是否包含 libfrpc.so..."
APK="app/build/outputs/apk/debug/app-debug.apk"
if [ -f "$APK" ]; then
    FRPC_COUNT=$(unzip -l "$APK" | grep -c "libfrpc.so" || true)
    if [ "$FRPC_COUNT" -ge 1 ]; then
        echo "  ✅ APK 包含 libfrpc.so ($FRPC_COUNT 个架构)"
    else
        echo "  ❌ APK 中未找到 libfrpc.so"
        exit 1
    fi
else
    echo "  ⚠️ APK 未构建，跳过"
fi

# 2. 连接设备
echo "[2/6] 连接设备 $DEVICE..."
$ADB connect "$DEVICE" 2>/dev/null || true
sleep 1

# 3. 检查设备上的 libfrpc.so
echo "[3/6] 检查设备上的 libfrpc.so..."
LIB_PATH=$($ADB -s "$DEVICE" shell "pm path $PACKAGE 2>/dev/null | head -1 | sed 's/package://' | xargs dirname")/lib
SO_EXISTS=$($ADB -s "$DEVICE" shell "ls /data/app/*${PACKAGE}*/lib/*/libfrpc.so 2>/dev/null | head -1" || echo "")
if [ -n "$SO_EXISTS" ]; then
    echo "  ✅ libfrpc.so 存在于设备: $SO_EXISTS"
else
    echo "  ⚠️ 设备上未找到 libfrpc.so (可能未安装)"
fi

# 4. 检查 frpc 进程
echo "[4/6] 检查 frpc 进程..."
FRPC_PROC=$($ADB -s "$DEVICE" shell "ps -ef 2>/dev/null | grep libfrpc | grep -v grep" || echo "")
if [ -n "$FRPC_PROC" ]; then
    echo "  ✅ frpc 进程运行中"
    echo "  $FRPC_PROC"
else
    echo "  ⚠️ frpc 进程未运行 (可能缺少 frpc.ini)"
fi

# 5. 检查端口 7400
echo "[5/6] 检查 frpc 管理端口 7400..."
PORT_CHECK=$($ADB -s "$DEVICE" shell "cat /proc/net/tcp 2>/dev/null | grep '1CE8'" || echo "")
if [ -n "$PORT_CHECK" ]; then
    echo "  ✅ 端口 7400 已被占用 (frpc admin)"
else
    echo "  ⚠️ 端口 7400 未被占用"
fi

# 6. 检查 frpc.ini
echo "[6/6] 检查 frpc.ini 配置文件..."
DATA_DIR=$($ADB -s "$DEVICE" shell "run-as $PACKAGE ls files/ 2>/dev/null" || echo "")
INI_EXISTS=$($ADB -s "$DEVICE" shell "run-as $PACKAGE test -f files/frpc.ini && echo 'yes' || echo 'no'" 2>/dev/null || echo "no")
if [ "$INI_EXISTS" = "yes" ]; then
    echo "  ✅ frpc.ini 存在"
    $ADB -s "$DEVICE" shell "run-as $PACKAGE cat files/frpc.ini 2>/dev/null | head -10"
else
    echo "  ⚠️ frpc.ini 不存在 (需要服务端 /api/agent/query.json 返回)"
fi

echo ""
echo "=== 验证完成 ==="
```

- [ ] **Step 2: 添加执行权限并提交**

```bash
cd /home/code/php/project/full-package/vendor-replica
chmod +x scripts/test-frpc-integration.sh
git add scripts/test-frpc-integration.sh
git commit -m "feat: add frpc integration end-to-end verification script"
```

---

## Execution Order & Dependencies

```
Task 1: Android 构建集成 (libfrpc.so → jniLibs)     ← 独立，可立即开始
Task 2: Laravel frpc 配置文件                         ← 独立，可与 Task 1 并行
Task 3: 数据库迁移                                    ← 独立，可与 Task 1-2 并行
Task 4: 模型层                                        ← 依赖 Task 3
Task 5: FrpcConfigService                             ← 依赖 Task 4
Task 6: API 控制器                                    ← 依赖 Task 5
Task 7: frps 部署配置                                 ← 独立，可与任何 Task 并行
Task 8: 端到端验证                                    ← 依赖 Task 1 + Task 6 + Task 7
```

```
Round 1 (parallel): Task 1 + Task 2 + Task 3 + Task 7
Round 2:            Task 4
Round 3:            Task 5
Round 4:            Task 6
Round 5:            Task 8 (端到端验证)
```

## Risk Mitigation

| 风险 | 影响 | 缓解措施 |
|------|------|----------|
| libfrpc.so 体积大 (28MB) | Git 仓库膨胀 | Task 1 Step 5 提供 .gitignore 选项，或使用 Git LFS |
| frpc.ini INI 格式 vs TOML | 新版 frpc 不兼容 INI | vendor 二进制使用旧版格式，FrpcConfigService 输出 INI |
| 端口耗尽 | 超过 3333 台设备时端口不够 | 扩大 PORT_RANGE 或使用 XTCP/STCP p2p 模式 |
| frps 公网安全 | 未授权访问 | 使用 token 认证 + allow_ports 限制 + 防火墙 |
| Android 设备 API 29+ 存储限制 | frpc.ini 路径问题 | AppManagerUtils.getExternalFilePath() 已处理，返回 scoped storage 路径 |
| Device.id vs deviceId 映射 | Android 端用字符串 deviceId | Laravel API 直接使用 Device.id 作为 deviceId，与 Android 端 SharedPrefsManager 存储的一致 |
