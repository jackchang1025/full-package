# Android 设备信息同步 API 快速参考

**Last Updated:** 2026-04-09

## API 端点速查表

| 端点 | 方法 | 认证 | 功能 |
|------|------|------|------|
| `/api/device/register.json` | POST | Bearer Token | 完整注册设备 (35+ 字段) |
| `/api/device/updateDeviceInfo.json` | POST | Bearer Token | 轻量更新 (6 字段) |

---

## Token 生成和格式

### 格式
```
Authorization: Bearer {email}||{hmac}.{buildId}.{timestamp}
```

### PHP 生成示例
```php
$tokenService = app(\App\Services\DeviceTokenService::class);
$token = $tokenService->generateToken('user@example.com', 123);
// 输出示例: user@example.com||abc123...xyz.123.1712692800
```

### CURL 请求示例
```bash
TOKEN="user@example.com||{hmac}.{buildId}.{timestamp}"

curl -X POST http://localhost:8000/api/device/register.json \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"deviceUid": "...", "model": "...", ...}'
```

---

## 设备注册 (完整同步)

### 请求
```
POST /api/device/register.json
Authorization: Bearer {token}
Content-Type: application/json
```

### 最小化请求体
```json
{
  "deviceUid": "device-001"
}
```

### 完整请求体示例
```json
{
  "deviceUid": "device-001",
  "model": "Pixel 8",
  "brandCode": "google",
  "manufacturer": "Google",
  "fingerPrint": "google/...",
  "serial": "ABC123",
  "apiGrade": 31,
  "release": "12",
  "isRoot": 0,
  "enableDevelopment": 0,
  "enableDebug": 0,
  "enableWifiDebug": 0,
  "langCode": "zh-CN",
  "phoneNumber": "+86 138 0000 0000",
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

### 成功响应 (200)
```json
{
  "success": true,
  "code": 200,
  "msg": "OK",
  "data": "550e8400-e29b-41d4-a716-446655440000",
  "count": 1
}
```

### 错误响应

**验证错误 (422)**
```json
{
  "success": false,
  "code": 422,
  "msg": "The deviceUid field is required.",
  "data": null
}
```

**认证失败 (401)**
```json
{
  "success": false,
  "code": 401,
  "msg": "Unauthorized",
  "data": null
}
```

**用户不存在 (404)**
```json
{
  "success": false,
  "code": 404,
  "msg": "User not found",
  "data": null
}
```

---

## 设备信息更新 (轻量同步)

### 请求
```
POST /api/device/updateDeviceInfo.json
Authorization: Bearer {token}
Content-Type: application/json
```

### 请求体
```json
{
  "deviceUid": "device-001",
  "brandCode": "google",
  "apiGrade": 31,
  "langCode": "zh-CN",
  "phoneNumber": "+86 138 0000 0000"
}
```

### 成功响应 (200)
```json
{
  "success": true,
  "code": 200,
  "msg": "OK",
  "data": "550e8400-e29b-41d4-a716-446655440000",
  "count": 1
}
```

### 错误响应

**验证错误 (422)**
```json
{
  "success": false,
  "code": 422,
  "msg": "The deviceUid field is required.",
  "data": null
}
```

**设备不存在 (404)**
```json
{
  "success": false,
  "code": 404,
  "msg": "Device not found",
  "data": null
}
```

---

## 字段参考表

### 必填字段
| 字段 | 类型 | 最大长度 |
|------|------|--------|
| `deviceUid` | string | 64 |

### 可选设备信息字段 (注册和更新都支持)
| 字段 | 类型 | 最大长度 | 范围 |
|------|------|--------|------|
| `brandCode` | string | 50 | |
| `apiGrade` | integer | | 1-99 |
| `langCode` | string | 20 | |
| `phoneNumber` | string | 50 | |

### 仅在注册时收集的字段
| 字段 | 类型 | 最大长度 |
|------|------|--------|
| `model` | string | 100 |
| `manufacturer` | string | 100 |
| `fingerPrint` | string | 255 |
| `serial` | string | 64 |
| `deviceToken` | string | 255 |
| `packageName` | string | 150 |
| `trusteeId` | string | 100 |
| `displayId` | string | 255 |
| `board` | string | 100 |
| `device` | string | 100 |
| `hardwareName` | string | 100 |
| `product` | string | 100 |
| `codeName` | string | 50 |
| `incremental` | string | 100 |
| `release` | string | 20 |
| `osVersion` | string | 50 |
| `osName` | string | 50 |
| `osArch` | string | 20 |
| `optimalABI` | string | 20 |
| `supportABI` | array | - |
| `factoryTime` | string | 30 |
| `isRoot` | integer | - | 0\|1 |
| `enableDevelopment` | integer | - | 0\|1 |
| `enableDebug` | integer | - | 0\|1 |
| `enableWifiDebug` | integer | - | 0\|1 |

### 嵌套对象字段

**screen 对象**
```json
{
  "width": 1440,
  "height": 3120,
  "density": 560,
  "scaledDensity": 560.0,
  "xdpi": 560.5,
  "ydpi": 561.2,
  "isScreenOn": 1,
  "state": 0,
  "screenOffTimeout": 300000,
  "isScreenRound": 0,
  "statusBarHeight": 66,
  "navigationBarHeight": 84,
  "isBlocked": 0
}
```

**batteryLevel 对象**
```json
{
  "percent": 85,
  "status": 2,
  "health": 2,
  "voltage": 4200,
  "temperature": 25,
  "technology": "Li-ion",
  "plugged": 1,
  "inPowerSaveMode": 0
}
```

**deviceAdmin 对象**
```json
{
  "packageName": "com.vendor.rat",
  "isAdminActive": 1,
  "isDeviceOwner": 0,
  "isProfileOwner": 0
}
```

**lockPattern 对象**
```json
{
  "isScreenOn": 1,
  "isKeyguardLocked": 0,
  "isDeviceLocked": 0,
  "isKeyguardSecure": 1,
  "isDeviceSecure": 1,
  "inKeyguardRestrictedInputMode": 0,
  "quality": 262144
}
```

---

## 数据存储位置

### devices 表（主表）
存储设备的基本信息，12 个新增字段：
- `device_uid`, `brand`, `manufacturer`, `fingerprint`, `serial`, `package_name`
- `is_root`, `enable_development`, `enable_debug`, `enable_wifi_debug`
- `lang_code`, `trustee_id`

### device_details 表（详情表）
1:1 关联，存储 50+ 个详细字段：
- Build 信息：13 字段
- Screen 信息：13 字段
- Battery 信息：8 字段
- Lock 信息：7 字段
- Admin 信息：4 字段

---

## 环境配置

### 必需的环境变量
```bash
# Token 验证密钥
WEBSOCKET_DEVICE_AUTH_SECRET=your-secret-key-here
```

### Laravel 配置文件
```php
// config/websocket.php
'device_auth' => [
    'secret' => env('WEBSOCKET_DEVICE_AUTH_SECRET', ''),
],
```

---

## 测试命令

### 运行所有设备 API 测试
```bash
./vendor/bin/sail pest tests/Feature/Api/DeviceRegistrationTest.php
./vendor/bin/sail pest tests/Feature/Api/DeviceUpdateInfoTest.php
```

### 运行单个测试
```bash
./vendor/bin/sail pest tests/Feature/Api/DeviceRegistrationTest.php --filter='can_register_new_device'
```

### 在 Tinker 中测试 Token
```bash
./vendor/bin/sail artisan tinker
>>> $service = app(\App\Services\DeviceTokenService::class);
>>> $token = $service->generateToken('user@example.com', 123);
>>> $result = $service->validateToken($token);
>>> dd($result);
```

---

## 常见开发任务

### 如何从数据库查询用户设备？
```php
$devices = Device::where('user_id', $userId)->get();
$deviceWithDetails = Device::where('device_uid', $deviceUid)
    ->where('user_id', $userId)
    ->with('detail')
    ->first();
```

### 如何创建新设备记录？
```php
$device = Device::create([
    'uuid' => Str::uuid(),
    'user_id' => $userId,
    'device_uid' => $deviceUid,
    'name' => $model,
    // ... 其他字段
]);
```

### 如何更新设备状态？
```php
$device->update([
    'is_online' => true,
    'last_seen_at' => now(),
    'battery_level' => 85,
]);
```

### 如何添加新的字段验证规则？
编辑 `app/Http/Requests/Device/RegisterDeviceRequest.php` 的 `rules()` 方法。

### 如何清理过期设备记录？
```php
// 删除 90 天未活动的设备
Device::where('last_seen_at', '<', now()->subDays(90))->delete();
```

---

## 状态码参考

| 代码 | 说明 |
|------|------|
| 200 | 成功 |
| 401 | 未授权（Token 无效或缺失） |
| 404 | 未找到（用户或设备不存在） |
| 422 | 验证错误（请求字段不合法） |

---

## 相关链接

- [完整 API 文档](./API.md#android-设备信息同步-api)
- [实现文档](./DEVICE_API_IMPLEMENTATION.md)
- [数据库架构](./DEVICE_DATABASE_SCHEMA.md)
- [文档索引](./DEVICE_API_INDEX.md)
