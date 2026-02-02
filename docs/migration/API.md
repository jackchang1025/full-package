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

### 创建构建

```
POST /builds
Content-Type: application/json

{
    "name": "My App",
    "package_name": "com.example.myapp",
    "template_id": 1,
    "is_custom": false
}
```

**响应:** 重定向到构建详情

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
