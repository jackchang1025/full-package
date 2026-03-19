# Vendor vs Replica 深度对比结果报告

日期: 2026-03-20
设备: HUAWEI FIN-AL60 / Android 12
测试方式: 严格单 APK 隔离测试 (Round A → Round B → Round C)

---

## 测试环境

- Round A (Vendor): 独立安装测试, 无障碍已手动授权, PID=6595
- Round B (Replica): 独立安装测试, 无障碍已手动授权, PID=15188
- 测试场景: 完全相同的操作序列 (设置页切换 → 息屏亮屏 → 后台30s → 网络等待30s)
- Vendor APK: `org.ldtape.qqlhl` v2.0
- Replica APK: `com.vendor.rat` v1.0.0

---

## 总览

| 维度 | Vendor | Replica | 差异 |
|------|--------|---------|------|
| 日志总行数 | 65,695 | 36,272 | Replica 少 45% |
| 内存 PSS Total | — | 99 MB | — |
| Crash | 0 | 0 | ✅ 一致 |
| ANR | 0 | 0 | ✅ 一致 |
| 无障碍状态 | ✅ 已启用 | ✅ 已启用 | ✅ 一致 |
| 运行服务数 | 3 | 3 | ✅ 一致 |
| 进程 30s 后台存活 | ✅ PID=9858 | ✅ PID=15188 | ✅ 一致 |

### 各模块日志行数

| 模块 | Vendor | Replica | 差异 |
|------|--------|---------|------|
| MODULE_08 启动 | 75 | 128 | ✅ Replica 更详细 |
| MODULE_01 网络 | 117 | 194 | ⚠️ Replica 多但含大量 ERR_ABORTED |
| MODULE_02 权限 | 195 | 113 | ⚠️ Replica 少 42% |
| MODULE_04 UI自动化 | 162 | 90 | ⚠️ Replica 少 44% |
| MODULE_03 厂商适配 | 52 | 35 | ⚠️ Replica 少 33% |
| MODULE_05 数据收集 | 236 | 22 | ❌ Replica 少 91% |
| MODULE_07 保活 | 122 | 147 | ✅ Replica 更详细 |
| MODULE_06 远程控制 | 59 | 10 | ❌ Replica 少 83% |

---

## 逐模块对比

### MODULE_08 启动流程

| # | 检查项 | Vendor | Replica | 状态 |
|---|--------|--------|---------|------|
| 08-01 | 进程启动 | ✅ PID=6595 | ✅ PID=15188 | ✅ |
| 08-02 | Application.onCreate | ✅ `MainApplication instance create` | ✅ `MainApplication instance create` | ✅ |
| 08-03 | Config 加载 | ✅ 隐式 | ✅ `Config loaded successfully` | ✅ |
| 08-04 | WebView 加载 | ✅ `com.huawei.webview 15.0.4.326` | ✅ `com.huawei.webview 15.0.4.326` | ✅ |
| 08-05 | 引导页 URL | ✅ `guide.accessibility.rathat.org` | ❌ 无对应日志 | ❌ |
| 08-06 | NetworkSecurityConfig | ✅ `debugBuild: false` | ⚠️ `debugBuild: true` | ⚠️ |
| 08-07 | HttpCommandServer | ✅ `asyncHttpServer 已启动` | ✅ `HttpCommandServer instance created` | ✅ |
| 08-08 | LocalWebSocketServer | ✅ `MyWebSocketServer 已启动` | ✅ `webSocketServer start on port 7900` | ✅ |
| 08-09 | 无 Crash | ✅ 0 | ✅ 0 | ✅ |

### MODULE_01 网络通信

| # | 检查项 | Vendor | Replica | 状态 |
|---|--------|--------|---------|------|
| 01-01 | Conscrypt TLS | ✅ 隐式 | ✅ `Conscrypt TLS provider installed` | ✅ |
| 01-02 | 服务器地址 | ✅ `api.rathat.club` | ✅ `server=https://api.rathat.club` | ✅ |
| 01-03 | HTTP 请求 | ✅ 多个 FetchClient 请求 | ✅ `updateDeviceInfo success: 451` | ⚠️ Replica 请求少 |
| 01-04 | Bridge WebSocket | ✅ 连接成功, 12条消息 | ❌ 无 Bridge WebSocket | ❌ |
| 01-05 | KeepHeartThread | ✅ 10s 间隔, `本地HttpServer运行正常` | ✅ 10s 间隔, `keep heart thread is running` | ✅ |
| 01-06 | WebSocket 连接 | ✅ Bridge WS 稳定 | ❌ `Software caused connection abort` 后断连 | ❌ CRITICAL |
| 01-07 | WebSocket 重连 | ✅ 自动重连成功 | ⚠️ 持续 `WebSocket not connected, attempting connect...` | ❌ |
| 01-08 | API 轮询 | ✅ getCacheTask, windows.json, register 等 | ⚠️ 仅 updateDeviceInfo | ❌ |
| 01-09 | AccountSync | ✅ `addAccountExplicitly success` + `onPerformSync` | ❌ 仅 `triggerDataSync` 日志，无实际同步 | ❌ |
| 01-10 | 无 Crash | ✅ 0 | ✅ 0 | ✅ |

### MODULE_02 权限绕过

| # | 检查项 | Vendor | Replica | 状态 |
|---|--------|--------|---------|------|
| 02-01 | 无障碍已启用 | ✅ | ✅ | ✅ |
| 02-02 | feedbackType | ✅ SPOKEN,HAPTIC,AUDIBLE,VISUAL,GENERIC,BRAILLE | ✅ 一致 | ✅ |
| 02-03 | capabilities | ✅ 161 | ✅ 161 | ✅ |
| 02-04 | eventTypes | ✅ 11种精确类型 | ✅ 11种精确类型 | ✅ |
| 02-05 | notificationTimeout | ✅ 0 | ✅ 0 | ✅ |
| 02-06 | 无障碍事件处理 | ✅ 195行事件日志 | ❌ 0行事件日志 | ❌ CRITICAL |
| 02-07 | listenWindows.json | ✅ 远程加载成功 | ⚠️ `首次加载 listenWindows` / `触发远程刷新` | ⚠️ |
| 02-08 | 无 Crash | ✅ 0 | ✅ 0 | ✅ |

### MODULE_04 UI 自动化

| # | 检查项 | Vendor | Replica | 状态 |
|---|--------|--------|---------|------|
| 04-01 | 窗口变化检测 | ✅ 162行 | ❌ 0行 (无障碍事件未分发) | ❌ |
| 04-02 | AccessibilityDelegate | ✅ 活跃 | ❌ 无日志 | ❌ |
| 04-03 | UiObject 创建 | ✅ 活跃 | ❌ 无日志 | ❌ |
| 04-04 | 窗口标题识别 | ✅ 活跃 | ❌ 无日志 | ❌ |
| 04-05 | 无 Crash | ✅ 0 | ✅ 0 | ✅ |

### MODULE_03 厂商适配

| # | 检查项 | Vendor | Replica | 状态 |
|---|--------|--------|---------|------|
| 03-01 | 设备厂商识别 | ✅ HUAWEI | ⚠️ 35行但无明确识别日志 | ⚠️ |
| 03-02 | 华为引擎加载 | ✅ 活跃 | ❌ 无引擎事件 | ❌ |
| 03-03 | 无 Crash | ✅ 0 | ✅ 0 | ✅ |

### MODULE_05 数据收集

| # | 检查项 | Vendor | Replica | 状态 |
|---|--------|--------|---------|------|
| 05-01 | ScreenBroadcastReceiver | ✅ 启动完成 | ✅ 启动完成 | ✅ |
| 05-02 | 息屏检测 | ✅ | ✅ `手机息屏了` | ✅ |
| 05-03 | 亮屏检测 | ✅ | ✅ `手机亮屏了` | ✅ |
| 05-04 | 解锁检测 | ✅ `设备已解锁成功` | ✅ `手机解锁了` | ✅ |
| 05-05 | StrategyThread 响应 | ✅ 236行, 含 noCompletes 轮询 | ❌ 仅 22行, 无 Strategy 响应 | ❌ |
| 05-06 | 截屏功能 | ✅ `Screen Shot Success` | ❌ 无截屏日志 | ❌ |
| 05-07 | 无 Crash | ✅ 0 | ✅ 0 | ✅ |

### MODULE_07 保活机制

| # | 检查项 | Vendor | Replica | 状态 |
|---|--------|--------|---------|------|
| 07-01 | KeepHeartThread | ✅ 10s 间隔 | ✅ 10s 间隔 | ✅ |
| 07-02 | CheckProcessThread | ✅ 5s 间隔 | ✅ ~5s 间隔 | ✅ |
| 07-03 | HandlerMsgAndTimer | ✅ 10s 间隔, `同步发送消息成功` | ❌ 完全缺失 | ❌ CRITICAL |
| 07-04 | HttpServer 健康检查 | ✅ `本地HttpServer运行正常` | ⚠️ `checkHttpServer` 但无健康确认 | ⚠️ |
| 07-05 | AccountSync | ✅ `addAccountExplicitly success` | ❌ 仅 `triggerDataSync` 无实际同步 | ❌ |
| 07-06 | frpc 进程管理 | ✅ `libfrpc.so` + `frpc.ini` | ❌ 完全缺失 | ❌ |
| 07-07 | WIFIBackgroundService | ✅ `onStartCommand` | ✅ 运行中 | ✅ |
| 07-08 | JobScheduler | ✅ wifi-lock-server + AccountSync | ✅ wifi-lock-server | ⚠️ 缺 AccountSync job |
| 07-09 | 30s 后台存活 | ✅ PID 不变 | ✅ PID 不变 | ✅ |
| 07-10 | 无 Crash | ✅ 0 | ✅ 0 | ✅ |

### MODULE_06 远程控制

| # | 检查项 | Vendor | Replica | 状态 |
|---|--------|--------|---------|------|
| 06-01 | HttpCommandServer | ✅ `asyncHttpServer 已启动` | ✅ `HttpCommandServer instance created` | ⚠️ 无健康确认 |
| 06-02 | LocalWebSocketServer | ✅ `MyWebSocketServer 已启动` | ✅ `webSocketServer start on port 7900` | ✅ |
| 06-03 | HandlerMsgAndTimer | ✅ `同步发送消息成功` (持续) | ❌ 完全缺失 | ❌ CRITICAL |
| 06-04 | ADB 连接管理 | ✅ `AdbConnectionManager` | ❌ 无日志 | ❌ |
| 06-05 | Bridge WebSocket | ✅ 12条消息, getCacheTask 中转 | ❌ 完全缺失 | ❌ |
| 06-06 | API 轮询 | ✅ getCacheTask 持续轮询 | ❌ 无轮询 | ❌ |
| 06-07 | 无 Crash | ✅ 0 | ✅ 0 | ✅ |

---

## 关键差异清单

| # | 模块 | 差异描述 | 严重度 | 根因分析 |
|---|------|---------|--------|---------|
| 1 | 01 | **WebSocket 连接后 ~14s 断开**: `Software caused connection abort`，之后持续重连失败 | CRITICAL | Replica 连接的是 Laravel WebSocket (ws://192.168.31.35:8081)，而非 Vendor 的 Bridge WebSocket。连接后发送 registration ping 但服务端可能不认识该格式导致断开 |
| 2 | 07 | **HandlerMsgAndTimer 完全缺失**: Vendor 每 10s 运行一次消息队列同步，Replica 无此线程 | CRITICAL | HandlerMsgAndTimer 线程未在 MainApplication.init() 中启动 |
| 3 | 02 | **无障碍事件未处理**: 配置完全一致但 Replica 0行事件日志 | CRITICAL | MyAccessibilityService.onAccessibilityEvent() 可能为空实现或未正确分发事件 |
| 4 | 06 | **Bridge WebSocket 完全缺失**: Vendor 通过 Bridge WS 与服务端实时通信 (type=7/8)，Replica 无此机制 | HIGH | bridge/a.java 未实现，Replica 的 WebSocketClient 连接的是 Laravel WS 而非 Vendor Bridge |
| 5 | 01 | **API 轮询缺失**: Vendor 启动后立即发起 register → agent/query → entryAppMap → windows.json → getCacheTask 等请求 | HIGH | MainApplication.init() 中未触发初始 API 请求链 |
| 6 | 07 | **AccountSync 未实际执行**: Vendor `addAccountExplicitly success` + `onPerformSync`，Replica 仅打印 `triggerDataSync` | HIGH | AccountUtils/SyncAdapter 为 stub 实现 |
| 7 | 05 | **StrategyThread 无响应**: Vendor 息屏后触发大量 noCompletes 轮询，Replica 仅记录息屏事件 | HIGH | StrategyThread 未实现或未连接到 ScreenBroadcastReceiver 事件 |
| 8 | 07 | **frpc 进程管理缺失**: Vendor 检查 libfrpc.so + frpc.ini，Replica 完全无此逻辑 | MEDIUM | CheckProcessThread 中 frpc 相关逻辑未实现 |
| 9 | 06 | **HttpServer 健康检查无确认**: Vendor `本地HttpServer运行正常`，Replica 仅 `checkHttpServer` 无结果 | MEDIUM | KeepHeartThread.checkHttpServer() 未实际验证 HttpServer 状态 |
| 10 | 08 | **引导页 URL 未加载**: Vendor 加载 `guide.accessibility.rathat.org`，Replica 无对应日志 | LOW | 引导页 WebView 可能加载了不同 URL 或未记录日志 |
| 11 | 01 | **大量 ERR_ABORTED**: Replica 启动时 WebView 产生 14+ 个 `net::ERR_ABORTED` 和 `ERR_CONNECTION_REFUSED` | MEDIUM | WebView 加载的页面引用了不可达的资源 |

---

## 改善点 (相比首次对比 2026-03-18)

| 项目 | 首次 (03-18) | 本次 (03-20) | 状态 |
|------|-------------|-------------|------|
| 无障碍配置 | ❌ feedbackType/capabilities/eventTypes 不一致 | ✅ 完全一致 | ✅ 已修复 |
| 无障碍授权 | ❌ 未手动授权 | ✅ 已授权 | ✅ 已修复 |
| NetworkSecurityConfig | ❌ 缺失 | ✅ 存在 (debugBuild=true) | ⚠️ 部分修复 |
| KeepHeartThread | ❌ 未启动 | ✅ 10s 间隔运行 | ✅ 已修复 |
| CheckProcessThread | ❌ 未启动 | ✅ 5s 间隔运行 | ✅ 已修复 |
| HttpCommandServer | ❌ 未启动 | ✅ 已创建 | ✅ 已修复 |
| LocalWebSocketServer | ❌ 未启动 | ✅ 已启动 | ✅ 已修复 |
| ScreenBroadcastReceiver | ❌ 无响应 | ✅ 息屏/亮屏/解锁正常 | ✅ 已修复 |
| KeepAliveManager | ❌ 未初始化 | ✅ 所有 Receiver 注册 | ✅ 已修复 |
| WIFIBackgroundService | ❌ 未运行 | ✅ 运行中 | ✅ 已修复 |
| JobScheduler | ❌ 无注册 | ✅ wifi-lock-server 已注册 | ✅ 已修复 |
| 内存 PSS | 132 MB (EGL 62MB) | 99 MB | ✅ 已改善 |

---

## WebSocket 断连根因分析

### 关键时间线 (Replica)

```
02:27:31.942  CheckThread: WebSocket disconnected, will reconnect on next KeepHeartThread tick
02:27:41.956  KeepHeartThread: WebSocket not connected, attempting connect...
02:27:41.968  WebSocketClient: Connecting to: ws://192.168.31.35:8081
              (无连接成功日志 — 第一次尝试可能超时)
02:27:51.956  KeepHeartThread: WebSocket not connected, attempting connect...
              (第二次尝试)
02:28:01.956  KeepHeartThread: keep heart thread is running
02:28:02.004  KeepHeartThread: WebSocket ping sent  ← 说明此时已连接
              (连接成功但随后断开)
```

### 对比 Vendor 的 WebSocket 行为

Vendor 使用两套 WebSocket:
1. **Bridge WebSocket** (`e1.d`): 连接到 `wss://{host}/bridge`，用于实时命令中转 (type=7/8)
2. **本地 WebSocket Server** (`MyWebSocketServer`): 监听本地端口，供 Panel 连接

Replica 只有:
1. **WebSocketClient**: 连接到 Laravel WebSocket `ws://192.168.31.35:8081` — 这是 Web 面板的 WebSocket，不是 Vendor 的 Bridge
2. **LocalWebSocketServer**: 监听 7900 端口

### 根因

Replica 的 WebSocket 连接目标错误。它连接的是 Laravel 后端的 WebSocket 服务器 (为 Web 面板设计)，而 Vendor 连接的是自己的 Bridge WebSocket 服务。两者协议不同，导致连接后被服务端断开。

---

## 修复优先级

### P0 — CRITICAL (WebSocket 断连直接原因)

| # | 修复项 | 说明 |
|---|--------|------|
| 1 | **HandlerMsgAndTimer 线程** | 实现消息队列同步线程，10s 间隔，负责 WebSocket 消息发送 |
| 2 | **无障碍事件分发** | MyAccessibilityService.onAccessibilityEvent() 需要实际处理事件并分发到 EngineManager |
| 3 | **WebSocket 协议对齐** | 确认 Replica 应该连接哪个 WebSocket (Laravel WS 还是 Bridge WS)，修复连接协议 |

### P1 — HIGH

| # | 修复项 | 说明 |
|---|--------|------|
| 4 | **Bridge WebSocket 实现** | 实现 Vendor 的 Bridge WebSocket 客户端 (type=7/8 消息协议) |
| 5 | **API 请求链** | 启动后发起 register → agent/query → entryAppMap → windows.json → getCacheTask |
| 6 | **AccountSync 实际执行** | AccountUtils.addAccountExplicitly + SyncAdapter.onPerformSync 实现 |
| 7 | **StrategyThread 事件响应** | 连接 ScreenBroadcastReceiver 事件到 StrategyThread，触发 noCompletes 轮询 |

### P2 — MEDIUM

| # | 修复项 | 说明 |
|---|--------|------|
| 8 | **frpc 进程管理** | CheckProcessThread 中实现 libfrpc.so + frpc.ini 检查逻辑 |
| 9 | **HttpServer 健康确认** | KeepHeartThread.checkHttpServer() 实际验证并打印 `本地HttpServer运行正常` |
| 10 | **WebView ERR_ABORTED** | 修复引导页 URL 或资源引用 |
| 11 | **NetworkSecurityConfig debugBuild** | Release 构建时应为 false |

---

## 结论

相比首次对比 (03-18)，Replica 已修复 12 项差异，保活基础设施 (KeepHeartThread, CheckProcessThread, KeepAliveManager, WIFIBackgroundService, JobScheduler) 已正常运行。

当前核心问题集中在 **通信层**:
1. WebSocket 连接不稳定 — 连接后 ~14s 断开，`Software caused connection abort`
2. HandlerMsgAndTimer 缺失 — 无法同步发送消息
3. 无障碍事件未分发 — 配置正确但 onAccessibilityEvent 未处理
4. Bridge WebSocket 未实现 — Vendor 的核心实时通信通道

进程存活和后台保活已基本解决，下一步重点是修复通信层。
