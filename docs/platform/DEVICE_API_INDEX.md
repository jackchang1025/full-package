# Android 设备信息同步 API 文档索引

**Last Updated:** 2026-04-09

## 快速导航

本索引汇总了与 Android 设备信息同步 API 相关的所有文档。

### 文档层级

| 文档 | 受众 | 内容 |
|------|------|------|
| **API.md** (设备 API 章节) | Android 客户端开发者、后端集成师 | API 端点、请求/响应格式、使用示例 |
| **DEVICE_API_IMPLEMENTATION.md** | 后端开发者、维护人员 | 架构、代码实现、数据流、安全性、性能优化 |
| **DEVICE_DATABASE_SCHEMA.md** | DBA、后端开发者、运维 | 数据库表设计、字段定义、索引策略、维护建议 |

---

## 按角色快速查找

### Android 客户端开发者

**目标：** 理解如何集成设备信息同步 API

**阅读顺序：**
1. [API.md - 认证机制](./API.md#认证机制) — Token 生成和格式
2. [API.md - 设备注册](./API.md#设备注册) — 完整注册请求与响应
3. [API.md - 设备信息轻量更新](./API.md#设备信息轻量更新) — 心跳更新
4. [API.md - 使用示例](./API.md#使用示例) — CURL 和 Java 示例

**关键点：**
- Token 格式：`{email}||{hmac}.{buildId}.{timestamp}`
- 注册 API：POST /api/device/register.json（完整同步）
- 更新 API：POST /api/device/updateDeviceInfo.json（定期心跳）
- 所有请求必须包含 `Authorization: Bearer {token}` 头

---

### 后端开发者

**目标：** 理解 API 实现、进行扩展或维护

**阅读顺序：**
1. [DEVICE_API_IMPLEMENTATION.md](./DEVICE_API_IMPLEMENTATION.md) — 架构和实现细节
2. [DEVICE_DATABASE_SCHEMA.md](./DEVICE_DATABASE_SCHEMA.md) — 数据库表设计
3. [API.md - 数据库表结构](./API.md#数据库表结构) — 快速参考

**关键模块：**
- `DeviceTokenService` — Token 生成与验证
- `AuthenticateDevice` 中间件 — 保护 API 端点
- `DeviceApiController` — 业务逻辑
- `Device` / `DeviceDetail` 模型 — 数据持久化

**关键文件：**
- `app/Services/DeviceTokenService.php`
- `app/Http/Middleware/AuthenticateDevice.php`
- `app/Http/Controllers/Api/DeviceApiController.php`
- `app/Http/Requests/Device/RegisterDeviceRequest.php`
- `app/Http/Requests/Device/UpdateDeviceInfoRequest.php`
- `app/Models/Device.php`
- `app/Models/DeviceDetail.php`

---

### DBA / 运维人员

**目标：** 理解数据库架构、监控和维护

**阅读顺序：**
1. [DEVICE_DATABASE_SCHEMA.md - 概述](./DEVICE_DATABASE_SCHEMA.md#概述) — 两表设计
2. [DEVICE_DATABASE_SCHEMA.md - devices 表](./DEVICE_DATABASE_SCHEMA.md#devices-表) — 字段和索引
3. [DEVICE_DATABASE_SCHEMA.md - device_details 表](./DEVICE_DATABASE_SCHEMA.md#device_details-表) — 详细字段
4. [DEVICE_DATABASE_SCHEMA.md - 性能考虑](./DEVICE_DATABASE_SCHEMA.md#性能考虑) — 索引策略和备份

**关键指标：**
- devices 表：34 列（19 原有 + 12 新增 + 系统字段）
- device_details 表：49 列（1:1 关联）
- 每台设备约 2-4 KB 存储空间
- 必备索引：device_uid, user_id, 推荐复合索引：(user_id, device_uid)

---

## 核心概念

### 术语说明

| 术语 | 定义 | 例子 |
|------|------|------|
| **deviceUid** | 由 Android 客户端生成的设备唯一标识 | `a1b2c3d4-e5f6-7890...` |
| **device uuid** | 由 Laravel 生成的设备 UUID，作为 deviceId 返回给客户端 | `550e8400-e29b-41d4-a716-446655440000` |
| **device_id** | 数据库 devices 表的主键 ID（内部使用） | `12345` |
| **Bearer Token** | 认证令牌，包含 email、HMAC 签名、buildId、时间戳 | `user@example.com\|\|abc123...def.123.1712692800` |
| **HMAC 秘钥** | 用于生成和验证 Token 的密钥 | 环境变量 `WEBSOCKET_DEVICE_AUTH_SECRET` |

### 数据流概览

```
Android 客户端
  ↓
生成 deviceUid (UUID)
  ↓
收集设备信息 (35+ 字段)
  ↓
调用 DeviceTokenService.generateToken(email, buildId)
  ↓
获得 Bearer Token
  ↓
POST /api/device/register.json + Token
  ↓
Laravel AuthenticateDevice 中间件
  ↓
验证 Token (HMAC-SHA256)
  ↓
DeviceApiController.register()
  ↓
创建/更新 Device + DeviceDetail 记录
  ↓
返回 device uuid
  ↓
客户端保存 device uuid，用于后续交互
```

---

## 功能特性

### 支持的功能

| 功能 | API 端点 | 请求体字段 | 说明 |
|------|---------|--------|------|
| **完整注册** | POST /api/device/register.json | 35+ | 设备首次注册或完整同步所有信息 |
| **轻量更新** | POST /api/device/updateDeviceInfo.json | 6 | 定期心跳，更新部分字段 + 在线状态 |
| **Token 认证** | Bearer Token Header | email, buildId, hmac, timestamp | HMAC-SHA256 签名验证 |
| **用户隔离** | user_id 字段 | | 每个设备绑定到具体用户，防止跨用户访问 |
| **1:1 详情关联** | device_details 表 | 50+ 字段 | 灵活的详情存储，支持选择性加载 |

### 支持的设备属性

**Build 信息（13 字段）**
- displayId, board, device, hardwareName, product, codeName, incremental, optimalABI, supportABI, factoryTime, osVersion, osName, osArch

**Screen 信息（13 字段）**
- width, height, density, scaledDensity, xdpi, ydpi, isScreenOn, state, screenOffTimeout, isScreenRound, statusBarHeight, navigationBarHeight, isBlocked

**Battery 信息（8 字段）**
- percent, status, health, voltage, temperature, technology, plugged, inPowerSaveMode

**Lock 信息（7 字段）**
- isKeyguardLocked, isDeviceLocked, isKeyguardSecure, isDeviceSecure, inKeyguardRestrictedInputMode, quality

**Admin 信息（4 字段）**
- packageName, isAdminActive, isDeviceOwner, isProfileOwner

---

## 开发指南

### 快速开始（后端）

1. **环境配置**
   ```bash
   # 设置环境变量
   WEBSOCKET_DEVICE_AUTH_SECRET=your-secret-key
   ```

2. **运行迁移**
   ```bash
   ./vendor/bin/sail artisan migrate
   ```

3. **测试 API**
   ```bash
   # 生成 Token
   php artisan tinker
   >>> $service = app(\App\Services\DeviceTokenService::class);
   >>> $token = $service->generateToken('user@example.com', 123);
   
   # 调用 API
   curl -X POST http://localhost:8000/api/device/register.json \
     -H "Authorization: Bearer $token" \
     -H "Content-Type: application/json" \
     -d '{...}'
   ```

4. **运行测试**
   ```bash
   ./vendor/bin/sail pest tests/Feature/Api/DeviceRegistrationTest.php
   ./vendor/bin/sail pest tests/Feature/Api/DeviceUpdateInfoTest.php
   ```

### 快速开始（Android 客户端）

1. **生成 Token**
   ```java
   // 调用管理后台 API 获取 Token
   String token = apiService.generateDeviceToken("user@example.com", buildId);
   ```

2. **收集设备信息**
   ```java
   Map<String, Object> deviceInfo = new HashMap<>();
   deviceInfo.put("deviceUid", UUID.randomUUID().toString());
   deviceInfo.put("model", Build.MODEL);
   deviceInfo.put("brandCode", Build.BRAND);
   // ... 其他字段
   ```

3. **发送注册请求**
   ```java
   Request request = new Request.Builder()
     .url("http://localhost:8000/api/device/register.json")
     .addHeader("Authorization", "Bearer " + token)
     .post(RequestBody.create(json, MediaType.get("application/json")))
     .build();
   Response response = client.newCall(request).execute();
   ```

4. **定期心跳更新**
   ```java
   // 每 5 分钟调用一次
   updateInfo(deviceUid, brandCode, apiGrade, langCode, phoneNumber);
   ```

---

## 常见问题

### Q: 为什么需要两个 API 端点？

**A:** 为了提供灵活性和性能：
- **注册 API** — 用于首次完整注册或需要更新所有字段时，请求较大但完整性强
- **更新 API** — 用于定期心跳，只发送必要的 6 个字段，减少网络开销

### Q: Token 多久过期？

**A:** 当前实现无过期时间检查。建议：
- 短生命周期 Token：每次构建 APK 时生成新 Token
- 或在客户端定期刷新 Token

### Q: 如何处理设备指纹碰撞？

**A:** 当前设计支持一个 deviceUid 对应一个用户的一个设备。同一 deviceUid 在不同用户间会创建不同的 Device 记录。

### Q: 设备详情 (device_details) 何时创建？

**A:** 仅在调用 `/api/device/register.json` 时创建或更新，轻量更新 API 不涉及 device_details。

### Q: 如何查询用户的所有设备？

**A:** 后端可以调用：
```php
Device::where('user_id', $userId)->with('detail')->get();
```

### Q: 数据库如何备份？

**A:** 参考 [DEVICE_DATABASE_SCHEMA.md - 维护建议](./DEVICE_DATABASE_SCHEMA.md#维护建议)

---

## 相关资源

### 代码文件

**API 层：**
- `routes/api.php` — API 路由定义
- `app/Http/Middleware/AuthenticateDevice.php` — 认证中间件
- `app/Http/Controllers/Api/DeviceApiController.php` — API 控制器
- `app/Http/Requests/Device/RegisterDeviceRequest.php` — 注册验证
- `app/Http/Requests/Device/UpdateDeviceInfoRequest.php` — 更新验证

**服务层：**
- `app/Services/DeviceTokenService.php` — Token 服务

**数据层：**
- `app/Models/Device.php` — 设备模型
- `app/Models/DeviceDetail.php` — 设备详情模型

**数据库：**
- `database/migrations/2026_04_10_000003_add_device_uid_to_devices_table.php`
- `database/migrations/2026_04_10_000004_create_device_details_table.php`

**测试：**
- `tests/Feature/Api/DeviceRegistrationTest.php`
- `tests/Feature/Api/DeviceUpdateInfoTest.php`

### 相关文档

- [API.md](./API.md) — 完整 API 文档
- [DEVELOPMENT.md](./DEVELOPMENT.md) — 平台开发指南
- [DEVICE_STATUS_FIELDS.md](./DEVICE_STATUS_FIELDS.md) — 设备状态字段详解
- [DEPLOYMENT.md](./DEPLOYMENT.md) — 部署指南

---

## 更新历史

| 日期 | 变更 |
|------|------|
| 2026-04-09 | 首次发布设备 API 文档（API.md, DEVICE_API_IMPLEMENTATION.md, DEVICE_DATABASE_SCHEMA.md） |

---

## 文档维护

**文档所有者：** Backend Team

**更新频率：** 每次 API 功能变更时更新

**反馈渠道：** GitHub Issues / Pull Requests

**最后审查：** 2026-04-09
