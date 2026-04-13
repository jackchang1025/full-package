# Android 设备信息同步 API 实现文档

**Last Updated:** 2026-04-09

## 概述

Android 设备信息同步 API 用于设备端上报和同步设备状态信息，包括硬件信息、屏幕指标、电池状态、锁屏信息等。API 采用 Bearer Token 认证，使用 HMAC-SHA256 签名验证请求合法性。

## 架构

```
┌─────────────────────────────────────────────────────────────────┐
│                      Android Device                              │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │ DeviceInfoCollector                                      │  │
│  │  - 收集 device info, screen, battery, admin, lock status │  │
│  │  - 调用 DeviceTokenService.generateToken()              │  │
│  │  - POST /api/device/register.json (首次/完整同步)        │  │
│  │  - POST /api/device/updateDeviceInfo.json (轻量更新)    │  │
│  └──────────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────────┘
                              │
                    Bearer Token Auth
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                    Laravel Application                           │
│                                                                  │
│  ┌────────────────────────────────────────────────────────┐   │
│  │ AuthenticateDevice Middleware                          │   │
│  │  - 提取 Bearer Token                                   │   │
│  │  - 调用 DeviceTokenService.validateToken()            │   │
│  │  - HMAC 验证 (hash_equals 防时序攻击)                  │   │
│  │  - 将 email 和 build_id 写入 $request 属性            │   │
│  └────────────────────────────────────────────────────────┘   │
│                              │                                   │
│                              ▼                                   │
│  ┌────────────────────────────────────────────────────────┐   │
│  │ DeviceApiController                                    │   │
│  │  ├─ register()                                         │   │
│  │  │  - 验证 RegisterDeviceRequest (35+ 字段)            │   │
│  │  │  - 查找或创建 Device 记录                           │   │
│  │  │  - 调用 buildDetailData() 映射嵌套对象              │   │
│  │  │  - 创建/更新 DeviceDetail 记录                      │   │
│  │  │  - 返回 device uuid                                │   │
│  │  │                                                      │   │
│  │  └─ updateInfo()                                       │   │
│  │     - 验证 UpdateDeviceInfoRequest (6 字段)            │   │
│  │     - 查找现有 Device 记录                             │   │
│  │     - 更新部分字段 + last_seen_at + is_online         │   │
│  │     - 返回 device uuid                                │   │
│  └────────────────────────────────────────────────────────┘   │
│                              │                                   │
│                              ▼                                   │
│  ┌────────────────────────────────────────────────────────┐   │
│  │ Database Layer (Eloquent)                              │   │
│  │  ├─ Device (主表)                                      │   │
│  │  │  - 35 列: uuid, device_uid, user_id, name, brand .. │   │
│  │  │  - 索引: device_uid, user_id                        │   │
│  │  │                                                      │   │
│  │  └─ DeviceDetail (1:1 关联)                            │   │
│  │     - 50+ 列: screen_*, battery_*, lock_*, admin_*    │   │
│  │     - 外键: device_id (唯一，级联删除)                 │   │
│  └────────────────────────────────────────────────────────┘   │
│                                                                  │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼
                    MySQL 8.4 Database
```

## 核心模块

### 1. DeviceTokenService

**文件：** `app/Services/DeviceTokenService.php`

Token 生成与验证服务，使用 HMAC-SHA256 算法。

#### 生成 Token

```php
public function generateToken(string $email, int $buildId): string
```

**参数：**
- `$email` (string) — 用户邮箱
- `$buildId` (int) — APK 构建 ID

**返回格式：**
```
{email}||{hmac}.{buildId}.{timestamp}
```

**算法：**
```
hmac = HMAC-SHA256("{email}|{buildId}|{timestamp}", secret)
```

**示例：**
```
user@example.com||abc123def456...789.123.1712692800
```

#### 验证 Token

```php
public function validateToken(string $rawUserEmail): array
```

**参数：**
- `$rawUserEmail` (string) — Bearer Token (去掉 "Bearer " 前缀)

**返回结构：**
```php
[
    'email' => 'user@example.com',
    'build_id' => 123,
    'authenticated' => true  // 或 false
]
```

**验证流程：**

1. 检查 `||` 分隔符存在性
2. 分解为 `email` 和 `tokenPart`
3. 使用 `.` 分割 `tokenPart` 为 `[hmac, buildId, timestamp]`
4. 重新计算期望的 HMAC
5. 使用 `hash_equals()` 进行时间恒定的比较

**环境配置：**

Token 的密钥来自环境变量 `WEBSOCKET_DEVICE_AUTH_SECRET`，配置位置：`config/websocket.php`

```php
'device_auth' => [
    'secret' => env('WEBSOCKET_DEVICE_AUTH_SECRET', ''),
],
```

---

### 2. AuthenticateDevice 中间件

**文件：** `app/Http/Middleware/AuthenticateDevice.php`

保护所有设备 API 端点的中间件。

**注册位置：** `bootstrap/app.php`
```php
->withMiddleware(function (Middleware $middleware) {
    $middleware->alias([
        'auth.device' => AuthenticateDevice::class,
    ]);
})
```

**验证流程：**

1. 提取 Bearer Token（`$request->bearerToken()`）
2. 调用 `DeviceTokenService::validateToken()`
3. 验证失败返回 401 JSON 响应
4. 验证成功将 `email` 和 `build_id` 合并到 `$request` 中：
   - `$request->input('_device_auth_email')`
   - `$request->input('_device_auth_build_id')`

**错误响应 (401)：**
```json
{
  "success": false,
  "code": 401,
  "msg": "Unauthorized",
  "data": null
}
```

---

### 3. DeviceApiController

**文件：** `app/Http/Controllers/Api/DeviceApiController.php`

处理设备注册和信息更新的控制器。

#### register() 方法

**请求验证：** `RegisterDeviceRequest` (35+ 字段)

**处理流程：**

1. 验证请求体字段
2. 从认证中间件获取 `_device_auth_email`
3. 根据 email 查找 User，获取 `ownerId`
4. 根据 `device_uid` + `user_id` 查询现有 Device
5. 构建 Device 数据：
   - 如果是新设备，生成 UUID 和 `installed_at`
   - 更新 12 个新增字段 + 9 个现有字段
   - 保存 `last_seen_at` 和 `is_online=true`
6. 构建 DeviceDetail 数据（嵌套对象映射）
7. 调用 `DeviceDetail::updateOrCreate()` 保存详情
8. 返回 device UUID

**关键映射逻辑：** `buildDetailData()` 方法

将请求体中的嵌套对象（`screen`, `battery`, `deviceAdmin`, `lockPattern`）扁平化为 DeviceDetail 表字段，使用 camelCase → snake_case 映射：

```
screen.width → screen_width
batteryLevel.percent → battery_percent
deviceAdmin.isAdminActive → is_admin_active
lockPattern.isKeyguardLocked → is_keyguard_locked
```

#### updateInfo() 方法

**请求验证：** `UpdateDeviceInfoRequest` (6 字段)

**处理流程：**

1. 验证请求体字段（轻量：只有 6 个字段）
2. 从认证中间件获取 `_device_auth_email`
3. 根据 email 查找 User
4. 根据 `device_uid` + `user_id` 查询 Device
5. Device 不存在返回 404
6. 更新 4 个可选字段 + `last_seen_at` + `is_online=true`
7. 返回 device UUID

**使用场景：** 定期心跳更新，避免发送完整的设备信息。

---

### 4. Form Requests

#### RegisterDeviceRequest

**文件：** `app/Http/Requests/Device/RegisterDeviceRequest.php`

验证注册请求的 35+ 字段。

**验证规则：**

- 必填字段：`deviceUid` (string, max:64)
- 可选字符串字段：`model`, `brandCode`, `manufacturer`, 等（各有最大长度限制）
- 可选整数字段：`apiGrade` (1-99), `isRoot` (0|1), 等
- 可选数组字段：`supportABI` (数组元素为 string)
- 可选嵌套对象：`screen`, `batteryLevel`, `deviceAdmin`, `lockPattern`

**错误处理：** 验证失败抛出 `HttpResponseException`，返回 422 JSON 响应：

```json
{
  "success": false,
  "code": 422,
  "msg": "{first_validation_error}",
  "data": null
}
```

#### UpdateDeviceInfoRequest

**文件：** `app/Http/Requests/Device/UpdateDeviceInfoRequest.php`

验证轻量更新请求的 6 个字段。

**验证规则：**

- 必填：`deviceUid` (string, max:64)
- 可选：`brandCode`, `apiGrade`, `langCode`, `phoneNumber`, `deviceId`

---

### 5. 数据模型

#### Device 模型

**文件：** `app/Models/Device.php`

主设备模型，存储核心设备信息。

**新增字段（Fillable）：**
```php
'device_uid', 'brand', 'manufacturer', 'fingerprint', 
'serial', 'package_name', 'is_root', 'enable_development', 
'enable_debug', 'enable_wifi_debug', 'lang_code', 'trustee_id'
```

**关联：**
```php
public function detail(): HasOne
{
    return $this->hasOne(DeviceDetail::class);
}
```

#### DeviceDetail 模型

**文件：** `app/Models/DeviceDetail.php`

设备详情模型，存储完整的设备属性。

**关联：**
```php
public function device(): BelongsTo
{
    return $this->belongsTo(Device::class);
}
```

**关键字段：**
- Build Info: `display_id`, `board`, `device_name`, `hardware_name`, `product`, 等
- Screen: `screen_width`, `screen_height`, `screen_density`, `screen_*`
- Battery: `battery_percent`, `battery_status`, `battery_health`, `battery_*`
- Lock: `is_keyguard_locked`, `is_device_locked`, `is_keyguard_secure`, `lock_quality`
- Admin: `admin_package_name`, `is_admin_active`, `is_device_owner`, `is_profile_owner`

---

### 6. 数据库迁移

#### 2026_04_10_000003_add_device_uid_to_devices_table.php

向 `devices` 表新增 12 列：

```php
$table->string('device_uid', 64)->nullable()->index()->after('uuid');
$table->string('brand', 50)->nullable()->after('model');
$table->string('manufacturer', 100)->nullable()->after('brand');
// ... 其他 9 列
```

**关键点：**
- `device_uid` 索引，用于快速查询
- 所有字段 nullable，因为现有设备可能无这些信息
- 位置通过 `after()` 指定，保持逻辑分组

#### 2026_04_10_000004_create_device_details_table.php

创建新的 `device_details` 表。

**关键点：**
- `device_id` 唯一外键（1:1 关系）
- 级联删除：删除 Device 时自动删除 DeviceDetail
- 50+ 列，分组存储不同维度的信息
- `support_abi` 为 JSON 类型，存储数组

---

## 数据流

### 注册流程（首次同步）

```
1. Android 客户端
   ├─ 生成 deviceUid (UUID)
   ├─ 收集所有 device info (35+ 字段)
   ├─ 调用 POST /api/device/register.json
   └─ 携带 Bearer Token 和请求体

2. Laravel 中间件层
   ├─ AuthenticateDevice::handle()
   │  ├─ 提取 Bearer Token
   │  ├─ DeviceTokenService::validateToken()
   │  ├─ HMAC 验证 (hash_equals)
   │  └─ 将 email/build_id 合并到 $request
   └─ 通过验证继续

3. DeviceApiController::register()
   ├─ RegisterDeviceRequest 验证
   ├─ User::where('email', email)->first() 查找用户
   ├─ Device::where('device_uid', ...)->first() 查询现有记录
   ├─ 构建 Device 数据
   │  ├─ 新建：生成 UUID，设置 installed_at
   │  └─ 更新：保持原 UUID，更新字段
   ├─ Device::create() 或 ->update()
   ├─ buildDetailData() 映射嵌套对象
   │  ├─ screen 字段 → screen_* 列
   │  ├─ batteryLevel 字段 → battery_* 列
   │  ├─ deviceAdmin 字段 → admin_* 列
   │  └─ lockPattern 字段 → lock_* 列
   ├─ DeviceDetail::updateOrCreate()
   └─ 返回 device UUID

4. 数据库
   ├─ INSERT INTO devices (...) VALUES (...)
   ├─ INSERT INTO device_details (...) VALUES (...)
   └─ 数据持久化完成
```

### 轻量更新流程（心跳）

```
1. Android 客户端
   ├─ 定期调用 POST /api/device/updateDeviceInfo.json
   ├─ 只发送 6 个字段 (deviceUid + 5 个可选字段)
   └─ 携带 Bearer Token

2. Laravel 中间件层
   ├─ AuthenticateDevice::handle()
   └─ 同上，HMAC 验证

3. DeviceApiController::updateInfo()
   ├─ UpdateDeviceInfoRequest 验证
   ├─ User::where('email', email)->first() 查找用户
   ├─ Device::where('device_uid', ...)->first() 查询设备
   ├─ Device 不存在返回 404
   ├─ 更新 4 个可选字段 + last_seen_at + is_online=true
   ├─ Device->update()
   └─ 返回 device UUID

4. 数据库
   └─ UPDATE devices SET ... WHERE id = ?
```

---

## 安全考虑

### 认证安全

1. **HMAC-SHA256 签名：** 使用强密码学算法确保 Token 不可伪造
2. **时间恒定比较：** `hash_equals()` 防止时序攻击
3. **密钥管理：** 从环境变量读取，不硬编码
4. **Token 格式：** 包含 email、buildId、时间戳，便于审计和验证合法性

### 数据验证

1. **表单验证：** 所有输入通过 FormRequest 验证，定义了类型和范围
2. **用户隔离：** 设备绑定到具体用户 (`user_id`)，确保用户只能访问自己的设备
3. **SQL 注入防护：** 使用 Eloquent ORM，参数化查询
4. **空值处理：** 大多数字段 nullable，避免强制填充

### 错误处理

1. **不泄露敏感信息：** 错误消息不包含内部细节
2. **401 vs 404 区分：** 认证失败返回 401，资源不存在返回 404
3. **验证错误详情：** 返回第一个验证错误，避免枚举攻击

---

## 性能优化

### 数据库索引

1. **devices 表：**
   - `device_uid` 索引（查询效率 O(log N)）
   - `user_id` 索引（通常已存在，支持用户隔离查询）
   - 复合索引 `(user_id, device_uid)` 可进一步优化

2. **device_details 表：**
   - `device_id` 唯一索引（由外键自动创建）

### 查询优化

1. **1:1 关联：** 使用 `unique()` 外键和 `updateOrCreate()` 避免重复记录
2. **选择性加载：** 可根据需要使用 `with()` 预加载关联数据
3. **缓存考虑：** 设备信息变化频繁，不建议长期缓存，可考虑短期 Redis 缓存（如 5 分钟）

---

## 测试覆盖

### 单元测试

- `tests/Feature/Api/DeviceRegistrationTest.php` (10 个测试)
  - 注册新设备
  - 更新现有设备
  - 认证失败处理
  - 验证错误处理
  - 用户不存在处理

- `tests/Feature/Api/DeviceUpdateInfoTest.php` (4 个测试)
  - 更新现有设备
  - 设备不存在处理
  - 认证失败处理
  - 验证错误处理

### 测试命令

```bash
# 运行所有设备 API 测试
./vendor/bin/sail pest tests/Feature/Api/DeviceRegistrationTest.php
./vendor/bin/sail pest tests/Feature/Api/DeviceUpdateInfoTest.php

# 运行单个测试
./vendor/bin/sail pest tests/Feature/Api/DeviceRegistrationTest.php --filter='can_register_new_device'
```

---

## 扩展点

### 未来增强

1. **Token 过期时间：** 当前无过期验证，可添加时间戳范围检查
2. **设备指纹碰撞处理：** 同一 deviceUid 在多个 user_id 下的处理策略
3. **批量操作：** 支持批量注册多个设备
4. **设备上线/离线事件：** 广播 WebSocket 事件通知管理面板
5. **历史变更追踪：** 记录设备字段变更时间线（审计日志）
6. **设备对比：** API 支持对比两个时间点的设备信息变化

---

## 相关文档

- **API 文档：** [docs/platform/API.md](./API.md) - 设备 API 章节
- **开发指南：** [docs/platform/DEVELOPMENT.md](./DEVELOPMENT.md)
- **WebSocket：** [docs/platform/websocket/](./websocket/)
- **Android 客户端：** 见 `android/` 目录

---

## 快速参考

| 概念 | 说明 |
|------|------|
| `deviceUid` | 由 Android 客户端生成的设备唯一标识，跨用户全局唯一 |
| `device_uuid` | 由 Laravel 生成的设备 UUID，作为 deviceId 返回给客户端 |
| `device_id` | 数据库 devices 表的主键 ID，内部使用 |
| Bearer Token | 格式 `{email}\|\|{hmac}.{buildId}.{timestamp}`，每次请求必须携带 |
| HMAC 秘钥 | 环境变量 `WEBSOCKET_DEVICE_AUTH_SECRET`，用于生成和验证 Token |
| Device Details | 存储在 device_details 表，1:1 关联到 Device，包含 50+ 字段 |
| 注册 API | POST /api/device/register.json，完整同步设备信息，首次或需要更新所有字段时调用 |
| 更新 API | POST /api/device/updateDeviceInfo.json，轻量更新，定期心跳时调用 |
