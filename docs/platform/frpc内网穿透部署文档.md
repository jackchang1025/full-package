# frpc 内网穿透部署文档

> 2026-04-20 — frpc 独立部署架构：App 进程直接启动 frpc，不依赖 ADB 配对或 local-service。

## 一、架构总览

```
┌─────────────────────────────────────────────────────────────────────────┐
│                          Android 设备                                   │
│                                                                         │
│  ┌──────────────────┐   ┌──────────────────┐   ┌──────────────────┐    │
│  │ HTTP Server 7910 │   │ WebSocket  7900  │   │ ADB WiFi   5555 │    │
│  │ (ApiRouter/Java) │   │ (状态推送/屏幕流) │   │ (无线调试端口)   │    │
│  └────────┬─────────┘   └────────┬─────────┘   └────────┬─────────┘    │
│           │                      │                       │              │
│           └──────────────────────┼───────────────────────┘              │
│                                  │                                      │
│                      ┌───────────▼───────────┐                          │
│                      │   frpc (libfrpc.so)   │                          │
│                      │   Runtime.exec 启动    │                          │
│                      │   frpc.ini 配置        │                          │
│                      │   admin 端口 7400      │                          │
│                      └───────────┬───────────┘                          │
│                                  │ TCP 反向隧道                          │
└──────────────────────────────────┼──────────────────────────────────────┘
                                   │
                          ┌────────▼────────┐
                          │  frps 服务器     │
                          │  协议端口 7000   │
                          │  管理面板 7500   │
                          │  数据端口 20000- │
                          │         30000   │
                          └────────┬────────┘
                                   │ Docker 内部网络
                          ┌────────▼────────┐
                          │  Laravel Panel   │
                          │  DeviceProxy     │
                          │  Service         │
                          │  → frps:20000    │
                          └─────────────────┘
```

### 三条隧道映射

| 隧道名称 | 设备本地端口 | frps 远程端口 | 用途 |
|----------|------------|-------------|------|
| `http-api-{id}` | 7910 | basePort + 0 | Java HTTP API（100+ 端点） |
| `websocket-{id}` | 7900 | basePort + 1 | WebSocket 状态推送 / 屏幕流 |
| `wifi-debug-port` | 5555 | basePort + 2 | ADB WiFi 调试端口转发 |

每台设备分配 3 个连续端口，范围 20000-30000。

---

## 二、启动流程

### 时序图

```
App 启动
  │
  ▼
MyAccessibilityService.onCreate()
  │
  ▼
initializeDeferredManagers()
  │
  ├── RemoteConfigManager.start()    ← HTTP Server 7910 启动
  ├── CommandDispatcher 初始化
  └── FrpcProcessManager.start()     ← frpc 看门狗启动
       │
       ├── 读取 deviceId (Settings.Secure.android_id)
       │    ├── 有值 → 启动 5s 定时器
       │    └── 空值 → 等待 NetworkManager 注册后回调
       │
       ▼ (每 5 秒执行)
  checkAndStart()
       │
       ├── 进程已运行? → 跳过
       │
       ├── frpc.ini 存在?
       │    ├── 否 → downloadFrpcIni()
       │    │         │
       │    │         ├── POST {server_addr}/api/agent/query.json
       │    │         │   body: {"deviceId": "..."}
       │    │         │
       │    │         ├── 响应: {"success":true, "data":{"targetFileUrl":"..."}}
       │    │         │
       │    │         ├── GET targetFileUrl → 保存到 files/frpc.ini
       │    │         │
       │    │         └── reload() → 启动 frpc
       │    │
       │    └── 是 → 继续
       │
       ├── 找 libfrpc.so (nativeLibraryDir)
       │
       └── Runtime.exec([libfrpc.so, -c, frpc.ini])
            │
            └── 保存 Process 引用，stderr 转发到 logcat
```

### 关键触发点

| 触发场景 | 说明 |
|---------|------|
| App 启动（无障碍授权后） | `initializeDeferredManagers()` 中创建并 `start()` |
| 设备首次注册 | `NetworkManager` 注册成功后调用 `updateDeviceId()` |
| frpc.ini 被删除 | `FileObserver` 检测到删除事件，自动重新下载 |
| frpc 进程崩溃 | 5s 看门狗检测到进程退出，自动重启 |
| 手动重载 | `reload()` 方法：销毁旧进程 → 重新启动 |

---

## 三、Android 端组件

### FrpcProcessManager

**文件**: `update-replica/app/src/main/java/com/storm/safe/rock/service/modules/FrpcProcessManager.kt`

**对齐 vendor**: `com.guard.wallet.thread.b` (CheckProcessThread)

| 公开方法 | 说明 |
|---------|------|
| `start()` | 启动看门狗定时器（每 5 秒），需要 deviceId 有值 |
| `stop()` | 停止定时器 + 销毁 frpc 进程 + 停止 FileObserver |
| `reload()` | 销毁当前 frpc 进程并立即重启 |
| `updateDeviceId(id)` | 设备注册后回调，首次收到 id 时自动启动看门狗 |
| `onConfigDeleted()` | frpc.ini 被删除时的处理：销毁进程 + 触发重新下载 |

**配置来源**:
- `deviceId`: `Settings.Secure.getString(contentResolver, "android_id")`
- `server_addr`: `SharedPreferences("system_optimize") → "server_addr"`
- `frpc.ini 存储`: `context.filesDir/frpc.ini`
- `libfrpc.so 位置`: `context.applicationInfo.nativeLibraryDir/libfrpc.so`

### 集成点

**MyAccessibilityService** (`MyAccessibilityService.kt`):
```kotlin
// 字段声明 (line 304)
var frpcProcessManager: FrpcProcessManager? = null

// 初始化 (initializeDeferredManagers, line 3224)
val fpm = FrpcProcessManager(applicationContext)
fpm.start()
frpcProcessManager = fpm

// 清理 (onDestroy)
frpcProcessManager?.stop()
frpcProcessManager = null
```

**NetworkManager** (`NetworkManager.kt`):
```kotlin
// 注册成功回调 (onConnectionStateChanged, line 588)
result.onSuccess { response ->
    isRegistered = true
    MyAccessibilityService.getInstance()?.frpcProcessManager?.updateDeviceId(deviceId)
}
```

---

## 四、Laravel 后端组件

### API 接口

#### `POST /api/agent/query.json`

**文件**: `app/Http/Controllers/Api/AgentController.php`

Android 端调用此接口获取 frpc.ini 下载地址。

**请求**:
```json
{
  "deviceId": "99542ecd4e124a4f"
}
```

**响应**:
```json
{
  "success": true,
  "data": {
    "id": 1,
    "deviceId": 12,
    "fileName": "frpc.ini",
    "targetFileUrl": "http://192.168.31.35:8080/storage/agent-files/frpc_99542ecd4e124a4f.ini",
    "fileSize": 401,
    "fileExtension": "ini"
  }
}
```

**流程**: 查找设备 → `FrpcConfigService.generateAndStore()` → 生成 INI → 存储文件 → 返回下载 URL

#### `POST /api/tunnel/config`

**文件**: `app/Http/Controllers/Api/AgentController.php`

local-service (Go binary) 调用此接口直接获取 frpc.ini 内容（INI 文本，非 URL）。

### FrpcConfigService

**文件**: `app/Services/FrpcConfigService.php`

**端口分配逻辑**:
- 范围: `config('frpc.port_range_start')` (20000) 到 `config('frpc.port_range_end')` (30000)
- 每台设备占 3 个连续端口: `basePort`, `basePort+1`, `basePort+2`
- 从数据库查询已占用端口，分配第一个空闲的 3 端口段
- 结果写入 `devices.frpc_base_port` 字段

**生成的 frpc.ini 模板**:
```ini
[common]
server_addr = {frps 公网/局域网 IP}
server_port = 7000
token = {认证 token}
admin_addr = 127.0.0.1
admin_port = 7400
log_level = warn

[http-api-{deviceId}]
type = tcp
local_ip = 127.0.0.1
local_port = 7910
remote_port = {basePort}

[websocket-{deviceId}]
type = tcp
local_ip = 127.0.0.1
local_port = 7900
remote_port = {basePort + 1}

[wifi-debug-port]
type = tcp
local_ip = 127.0.0.1
local_port = 5555
remote_port = {basePort + 2}
```

### 配置文件

**文件**: `app/config/frpc.php`

| 配置键 | 环境变量 | 默认值 | 说明 |
|-------|---------|-------|------|
| `frpc.server_addr` | `FRPS_SERVER_ADDR` | `127.0.0.1` | 设备连接 frps 的地址（需公网可达） |
| `frpc.proxy_host` | `FRPS_PROXY_HOST` | `frps` | Laravel 内部访问 frps 的 Docker 服务名 |
| `frpc.server_port` | `FRPS_SERVER_PORT` | `7000` | frps 协议端口 |
| `frpc.auth_token` | `FRPS_AUTH_TOKEN` | (空) | frpc/frps 认证 token |
| `frpc.port_range_start` | `FRPC_PORT_RANGE_START` | `20000` | 端口分配范围起始 |
| `frpc.port_range_end` | `FRPC_PORT_RANGE_END` | `30000` | 端口分配范围结束 |

---

## 五、Docker 部署

### frps 服务 (`compose.prod.yaml`)

```yaml
frps:
    image: 'snowdreamtech/frps:0.51.3'
    container_name: feiying-frps
    restart: unless-stopped
    ports:
        - '${FRPS_BIND_PORT:-7000}:7000'        # frp 协议端口（公网开放）
        - '${FRPS_DASHBOARD_PORT:-7500}:7500'    # 管理面板
        - '127.0.0.1:20000-30000:20000-30000'    # 设备隧道数据端口（仅本机回环）
    volumes:
        - './docker/frps/frps.ini:/etc/frp/frps.ini:ro'
    healthcheck:
        test: ["CMD", "nc", "-z", "localhost", "7000"]
        interval: 30s
        timeout: 5s
        retries: 3
```

### frps.ini (`docker/frps/frps.ini`)

```ini
[common]
bind_port = 7000
token = dev-frpc-token-2026
dashboard_port = 7500
dashboard_user = admin
dashboard_pwd = admin123
log_level = info
log_max_days = 7
allow_ports = 20000-30000
max_pool_count = 50
heartbeat_timeout = 90
```

### 安全要求

- **数据端口 20000-30000 必须绑定 `127.0.0.1`**，禁止 `0.0.0.0`
- Laravel 通过 Docker 内部网络 `frps:20xxx` 访问隧道，不需要宿主机端口暴露
- 如果绑定 `0.0.0.0`，攻击者可直接访问设备 HTTP API（包括 `/global/execCommand` 任意 shell）

**验证命令**（生产主机）:
```bash
ss -tlnp | grep -E ":2[0-9]{4}\s"
# 期望: 全部绑定 127.0.0.1，无 0.0.0.0 或 :::
```

---

## 六、Panel 透明代理

Laravel 通过 `DeviceProxyService` 把 Panel 命令转发到 frpc 隧道：

```
Panel Vue → POST /devices/{uuid}/api-proxy
               │
               ▼
         DeviceProxyService::request()
               │
               ▼
         http://frps:{device.frpc_base_port}{path}
               │
               ▼ (frps 反向隧道)
         设备 127.0.0.1:7910 (ApiRouter)
```

**安全分层**:
1. 路由中间件 `permission:devices.control` + `throttle:60,1`
2. `DeviceProxyRequest` 路径白名单（8 个 Android API 路径）
3. `ensureDeviceOwnership()` 归属校验
4. `DeviceProxyService.getDeviceBaseUrl()` 端口范围校验（SSRF 纵深防御）
5. `/syncLockCipher` 专用限流 5/min/user
6. 审计日志写 `security` channel

详细文档见: `docs/platform/PANEL_HTTP_PROXY.md`

---

## 七、与 vendor 架构对照

| 功能 | Vendor 实现 | Replica 实现 |
|------|-----------|-------------|
| frpc 进程管理 | `thread/b.java` (CheckProcessThread) | `FrpcProcessManager.kt` |
| 定时器间隔 | 5000ms (`schedule(this, 5000L, 5000L)`) | 5000ms (相同) |
| frpc 二进制 | `libfrpc.so` (APK nativeLib) | `libfrpc.so` (相同) |
| 启动方式 | `Runtime.exec([so, -c, ini])` | `Runtime.exec(arrayOf(so, -c, ini))` |
| 配置下载 | `POST /api/agent/query.json` → `QueryAgentFileCallback` | `POST /api/agent/query.json` → 内联处理 |
| 文件监控 | `y/b.java` (FileObserver) 监控 frpc.ini 删除 | `FileObserver(DELETE)` (相同) |
| 进程引用 | `Process f237f` | `var frpcProcess: Process?` |
| 管理端口 | 7400 (`admin_port`) | 7400 (相同) |
| ADB 依赖 | **无** — App 进程直接启动 | **无** — 完全对齐 |
| 触发时机 | `unlockedInstance()` (用户解锁后) | `initializeDeferredManagers()` (无障碍授权后) |

**关键设计差异**: Vendor 的 `CheckProcessThread.run()` 方法（1218 条指令）包含完整的进程监控 + 重启 + 配置验证逻辑，JADX 反编译失败。Replica 实现了等价的核心功能：看门狗 + 自动下载 + 自动重启。

---

## 八、故障排查

### 常见问题

| 症状 | 原因 | 解决方案 |
|------|------|---------|
| `deviceId 未注册，延迟启动` | `android_id` 为空或 App 首次安装 | 等待 NetworkManager 注册成功后自动触发 |
| `server_addr 未配置` | `system_optimize` SP 中无 `server_addr` | 检查 `assets/config.json` 中 `network.server_url` |
| `frpc.ini 不存在，请求下载` | C2 服务器不可达或设备未注册 | 检查网络 + 确认 `/api/agent/query.json` 可访问 |
| `libfrpc.so 未找到` | APK 未包含 native 库 | 确认 `jniLibs/arm64-v8a/libfrpc.so` 存在 |
| `port not allowed` | frps `allow_ports` 不包含 `remote_port` | 检查设备 `frpc_base_port` 在 20000-30000 范围内 |
| 看门狗每 5s 重启 frpc | frpc 进程启动后立即崩溃 | 检查 frpc.ini 格式 + frps 可达性 + token 正确性 |

### 诊断命令

```bash
ADB="adb"
DEVICE="192.168.31.102:38073"

# 检查 frpc 进程
$ADB -s $DEVICE shell "ps -A | grep frpc"

# 检查 frpc.ini
$ADB -s $DEVICE shell "run-as dev.deltalab2964.swift cat files/frpc.ini"

# 检查 frpc admin API（隧道状态）
$ADB -s $DEVICE shell "curl -s http://127.0.0.1:7400/api/status"

# 检查 FrpcProcessManager 日志
$ADB -s $DEVICE shell "logcat -d | grep FrpcProcessManager | tail -20"

# 检查 HTTP Server 是否在线
$ADB -s $DEVICE shell "curl -s http://127.0.0.1:7910/version"

# 从 Panel 侧验证隧道
curl -s http://frps:20000/version  # Docker 内部
curl -s http://127.0.0.1:20000/version  # 宿主机
```

### frpc admin API 状态释义

```json
{"tcp":[
  {"name":"http-api-12","status":"running","remote_addr":"192.168.31.35:20000"},
  {"name":"websocket-12","status":"running","remote_addr":"192.168.31.35:20001"},
  {"name":"wifi-debug-port","status":"running","remote_addr":"192.168.31.35:20002"}
]}
```

| status | 含义 |
|--------|------|
| `running` | 隧道正常工作 |
| `start error` + `port not allowed` | frps 不允许该远程端口 |
| `start error` + `port already used` | 端口被其他设备占用 |
| `wait start` | 等待 frps 分配端口 |

---

## 九、环境变量配置（.env）

```env
# frps 服务器地址（设备需要能访问到）
FRPS_SERVER_ADDR=你的公网IP或域名

# Laravel 内部访问 frps 的地址（Docker 服务名）
FRPS_PROXY_HOST=frps

# frps 协议端口
FRPS_SERVER_PORT=7000

# frpc/frps 认证 token（两端必须一致）
FRPS_AUTH_TOKEN=你的强密码

# 设备隧道端口范围
FRPC_PORT_RANGE_START=20000
FRPC_PORT_RANGE_END=30000
```

---

## 十、版本历史

| 日期 | 变更 | 说明 |
|------|------|------|
| 2026-04-20 | frpc 独立部署 | 脱离 ADB 依赖，App 进程直接 Runtime.exec 启动 frpc |
| 2026-04-20 | 端口安全收窄 | compose.prod.yaml 数据端口绑定 127.0.0.1 |
| 2026-04-20 | Panel HTTP 代理 | 新增 `/devices/{device}/api-proxy` 透明代理端点 |
