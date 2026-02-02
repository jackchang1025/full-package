# WebSocket E2E Tests

Node.js 端到端测试，用于验证 WebSocket 服务器与客户端的完整交互流程。

## 安装

```bash
cd tests/e2e
npm install
```

## 使用方法

### 1. 启动 WebSocket 服务器

```bash
# 在项目根目录
./vendor/bin/sail artisan websocket:serve
```

### 2. 运行 Mock 设备

模拟一个 Android 设备连接到服务器：

```bash
# 使用默认设备 ID
npm run mock-device

# 自定义设备 ID
DEVICE_ID=my-test-device npm run mock-device

# 连接到不同的服务器
WS_URL=ws://192.168.1.100:8081 npm run mock-device
```

### 3. 运行 E2E 测试

```bash
# 基础 WebSocket 测试
npm test

# 推送模式测试
npm run test:push

# 运行所有测试
npm run test:all

# 连接到不同的服务器
WS_URL=ws://192.168.1.100:8081 npm test
```

## 测试用例

### 基础测试 (websocket.test.js)

| 测试 | 描述 |
|------|------|
| Device can connect | 设备能够连接到服务器 |
| Panel can connect | 面板能够连接到服务器 |
| Panel receives checkphone | 面板能够获取设备列表 |
| Panel can join device | 面板能够订阅设备 |
| Panel receives device status | 面板能够获取设备状态 |
| Panel can request SMS | 面板能够请求并接收短信数据 |
| Panel can request contacts | 面板能够请求并接收联系人数据 |
| Panel can request files | 面板能够请求并接收文件列表 |
| Panel can request apps | 面板能够请求并接收应用列表 |
| Panel can request location | 面板能够请求并接收位置数据 |
| Panel can start screen share | 面板能够开启屏幕共享 |
| Panel can send tap | 面板能够发送点击命令 |
| Panel can send swipe | 面板能够发送滑动命令 |
| Panel can send navigation | 面板能够发送导航命令 |
| Device ping updates status | 设备心跳能够更新状态 |
| Multiple devices can connect | 多个设备能够同时连接 |

### 推送模式测试 (test-push-mode.js)

| 测试 | 描述 |
|------|------|
| Panel subscription registration | Panel 通过 checkphone 注册订阅 |
| Device online push | 设备上线时 Panel 收到 deviceOnline 推送 |
| Device offline push | 设备离线时 Panel 收到 deviceOffline 推送 |
| User isolation | 用户只能收到自己设备的推送 |
| Admin receives all devices | 管理员收到所有设备的推送 |
| Multiple panels same user | 同一用户多个 Panel 都能收到推送 |
| Panel disconnect stops push | Panel 断开后不再收到推送 |

## 在代码中使用

```javascript
const { MockDevice, MockPanel } = require('./mock-device');

// 创建 Mock 设备
const device = new MockDevice('my-device-id', {
    phoneName: 'Test Phone',
    model: 'Pixel 8',
    batteryCharge: '90',
});

await device.connect();

// 创建 Mock 面板
const panel = new MockPanel({
    userEmail: 'admin@example.com',
});

await panel.connect();

// 订阅设备
panel.joinDevice('my-device-id');

// 请求数据
panel.requestSms();
panel.requestContacts();
panel.requestFiles('/sdcard');

// 发送控制命令
panel.sendTap(500, 800);
panel.sendSwipe(500, 800, 500, 200);
panel.sendNavigation('ho');

// 监听消息
panel.onMessage((msg) => {
    console.log('Received:', msg);
});

// 断开连接
device.disconnect();
panel.disconnect();
```

## 环境变量

| 变量 | 默认值 | 描述 |
|------|--------|------|
| `WS_URL` | `ws://localhost:8081` | WebSocket 服务器地址 |
| `DEVICE_ID` | `mock-device-{timestamp}` | Mock 设备 ID |
| `USER_A_EMAIL` | `usera@test.com` | 用户 A 明文邮箱 (推送测试) |
| `USER_A_ENCRYPTED_EMAIL` | - | 用户 A 加密邮箱 (推送测试) |
| `USER_B_EMAIL` | `userb@test.com` | 用户 B 明文邮箱 (推送测试) |
| `USER_B_ENCRYPTED_EMAIL` | - | 用户 B 加密邮箱 (推送测试) |
| `ADMIN_ENCRYPTED_EMAIL` | - | 管理员加密邮箱 (推送测试) |

## 注意事项

### 加密 Email

`checkphone` 接口需要**加密后的 email**，不是明文。MockPanel 默认使用配置中的 admin 加密 email。

```javascript
// 默认使用加密的 admin email
const panel = new MockPanel();

// 自定义加密 email
const panel = new MockPanel({
    userEmail: 'YOUR_ENCRYPTED_EMAIL',
});
```

获取加密 email:
```bash
./vendor/bin/sail artisan tinker --execute="
\$service = new App\WebSocket\Services\EncryptionService();
echo \$service->encryptEmail('your@email.com');
"
```
