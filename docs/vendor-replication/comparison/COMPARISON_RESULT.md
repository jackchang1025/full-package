# Vendor vs Replica 深度对比结果报告

日期: 2026-03-18
设备: HUAWEI FIN-AL60 / Android 12
测试方式: 严格单 APK 隔离测试 (Round A → Round B → Round C)
两轮均已手动授权无障碍服务

---

## 总览

| 维度 | Vendor | Replica | 状态 |
|------|--------|---------|------|
| 日志总行数 | 46,340 | 27,363 | ⚠️ Vendor 多 69% |
| 内存 PSS Total | 70 MB | 77 MB | ⚠️ Replica 多 10% |
| Native Heap | 19 MB | 14 MB | ✅ 接近 |
| GL mtrack | 0.7 MB | 3.7 MB | ⚠️ Replica 高 5x |
| 运行服务数 | 2 | 2 | ✅ 一致 |
| JobScheduler 记录 | 36 | 3 | ❌ 差距大 |
| Alarm 记录 | 1 | 0 | ❌ Replica 缺失 |
| Crash (FATAL) | 0 | 0 | ✅ |
| ANR | 0 | 0 | ✅ |
| 30s 后台存活 | ✅ PID 不变 | ✅ PID 不变 | ✅ |
| 60s 后台存活 | ✅ PID 不变 | ✅ PID 不变 | ✅ |

---

## 逐模块对比

### MODULE_08 启动流程 (Vendor=123行 vs Replica=99行)

| # | 检查项 | Vendor | Replica | 状态 |
|---|--------|--------|---------|------|
| 08-01 | 进程启动 | ✅ PID=22043 | ✅ PID=24909 | ✅ |
| 08-02 | Application.onCreate | ✅ `instance create` → `begin create` → `end create` → `正在启动` | ✅ `Config loaded successfully` → `Initialization complete` | ✅ 功能一致 |
| 08-03 | Config 加载 | ✅ `unlockedInstance` (隐式) | ✅ `Config loaded successfully` | ✅ |
| 08-04 | WebView 加载 | ✅ `onPageFinished` guide URL | ✅ `onPageFinished` guide URL | ✅ |
| 08-05 | 引导页 URL | ✅ `guide.accessibility.rathat.org/860616249851785216/guide/0` | ✅ 相同 URL | ✅ |
| 08-06 | NetworkSecurityConfig | ✅ `Using Network Security Config from resource` | ❌ `No Network Security Config specified` | ❌ |
| 08-07 | Receiver 注册 | ✅ 8 个 (ReceiverUtils) | ✅ 6 个 (KeepAliveManager) | ⚠️ 缺 2 个 |
| 08-08 | DataCollectionManager | — | ✅ `DataCollectionManager initialized` | ✅ |
| 08-09 | 无 Crash | ✅ 0 | ✅ 0 | ✅ |

### MODULE_01 网络通信 (Vendor=195行 vs Replica=38行)

| # | 检查项 | Vendor | Replica | 状态 |
|---|--------|--------|---------|------|
| 01-01 | Conscrypt TLS | ✅ (隐式) | ✅ `Conscrypt TLS provider installed` | ✅ |
| 01-02 | 服务器地址 | ✅ `api.rathat.club` (FetchClient) | ✅ `server=https://api.rathat.club` | ✅ |
| 01-03 | HttpServer 启动 | ✅ `asyncHttpServer 已启动` | ❌ 无日志 | ❌ |
| 01-04 | WebSocket Server | ✅ `MyWebSocketServer 已启动` | ❌ 无日志 | ❌ |
| 01-05 | HTTP API 请求 | ✅ register.json, agent/query, entryAppMap, windows.json | ❌ 无请求 | ❌ |
| 01-06 | WebSocket 连接 | ✅ 运行中 | ❌ `WebSocket URL is null, skipping` (重试 5 次) | ❌ |
| 01-07 | 心跳线程 | ✅ `KeepHeartThread: keep heart thread is running` | ❌ 无日志 | ❌ |
| 01-08 | NetWorkReceiver | ✅ WIFI_STATE + CONNECTIVITY_CHANGE | ✅ CONNECTIVITY_CHANGE | ⚠️ 缺 WIFI 事件 |
| 01-09 | 无 Crash | ✅ 0 | ✅ 0 | ✅ |

### MODULE_02 权限绕过 (Vendor=338行 vs Replica=30行)

| # | 检查项 | Vendor | Replica | 状态 |
|---|--------|--------|---------|------|
| 02-01 | 无障碍服务启用 | ✅ `MyAccessibilityService on create` | ✅ `MyAccessibilityService on create` | ✅ |
| 02-02 | 正常模式进入 | ✅ `辅助功能进入正常模式` | ✅ `辅助功能进入正常模式` | ✅ |
| 02-03 | EngineManager 初始化 | — (vendor 无此日志) | ✅ `Detecting vendor: Huawei`, 5 个引擎注册 | ✅ |
| 02-04 | 事件接收 | ✅ 大量 WINDOW_STATE_CHANGED | ✅ 多个 EVENT: WINDOW_STATE_CHANGED | ✅ |
| 02-05 | 窗口标题识别 | ✅ `windowTitle:已安装的服务` / `设置` | ✅ `pkg=com.android.settings cls=HWSettings` | ✅ |
| 02-06 | listenWindows.json | ✅ 从本地文件加载远程监听配置 | ❌ 无此功能 | ❌ |
| 02-07 | 截屏能力 | ✅ CAN_TAKE_SCREENSHOT | ❌ 缺失 | ❌ |
| 02-08 | 无 Crash | ✅ 0 | ✅ 0 | ✅ |

### MODULE_04 UI 自动化 + MODULE_03 厂商适配

| # | 检查项 | Vendor | Replica | 状态 |
|---|--------|--------|---------|------|
| 04-01 | 华为厂商识别 | ✅ `build factory is HUAWEI` | ✅ `Detecting vendor: Huawei` | ✅ |
| 04-02 | HuaweiEngine 注册 | ✅ (隐式，通过事件处理确认) | ✅ `Engine registered: HuaweiEngine` | ✅ |
| 04-03 | 引擎数量 | — | ✅ 5 个 (DeviceAdmin/Accessibility/LockScreen/PermissionGrant/Huawei) | ✅ |
| 04-04 | 华为引擎执行 | ✅ 打开启动管理 `StartupAppControlActivity` | ✅ `AutoEngine/HuaweiEngine: 点击应用和通知` | ✅ 都有动作 |
| 04-05 | 页面切换事件 | ✅ Settings → WiFi → Apps → Home 全部检测 | ✅ 同样检测到全部页面 | ✅ |
| 04-06 | 息屏暂停引擎 | — | ✅ `stopLocalAccessibilityDelegate` | ✅ |
| 04-07 | 无 Crash | ✅ 0 | ✅ 0 | ✅ |

### MODULE_05 数据收集 (Vendor=407行 vs Replica=38行)

| # | 检查项 | Vendor | Replica | 状态 |
|---|--------|--------|---------|------|
| 05-01 | ScreenBroadcastReceiver 注册 | ✅ `ScreenBroadcastReceiver 启动完成` | ✅ `ScreenBroadcastReceiver 启动完成` | ✅ |
| 05-02 | 息屏检测 | ✅ `手机开启屏保` → `手机息屏了` | ✅ `手机开启屏保` → `手机息屏了` | ✅ |
| 05-03 | 亮屏检测 | ✅ `手机亮屏了` | ✅ `手机亮屏了` | ✅ |
| 05-04 | 解锁检测 | ✅ `手机解锁了` | ✅ `手机解锁了` | ✅ |
| 05-05 | 屏保检测 | ✅ `手机停止屏保、退出休眠` | ✅ `手机停止屏保、退出休眠` | ✅ |
| 05-06 | Screen state 记录 | — | ✅ state: 0→4→1 (OFF→PRESENT→ON) | ✅ |
| 05-07 | 策略事件 | — | ✅ `offerStrategyEvent: KEEP_ADB_ALIVE_SCREEN_OFF/ON/USER_PRESENT` | ✅ |
| 05-08 | BatteryLevelReceiver | ✅ `BatteryLevelReceiver 启动完成` | ✅ `Battery: 33.0%` + `Battery: 32.0%` | ✅ |
| 05-09 | PackageReceiver | ✅ `PackageReceiver 启动完成` | — | ⚠️ 未观测到 |
| 05-10 | 无 Crash | ✅ 0 | ✅ 0 | ✅ |

### MODULE_07 保活机制 (Vendor=240行 vs Replica=15行)

| # | 检查项 | Vendor | Replica | 状态 |
|---|--------|--------|---------|------|
| 07-01 | Receiver 注册 | ✅ 8 个 (ReceiverUtils) | ✅ 6 个 (KeepAliveManager) | ⚠️ 缺 BootBroadcast + PackageReceiver |
| 07-02 | KeepHeartThread | ✅ 10s 间隔运行 | ❌ 无日志 | ❌ |
| 07-03 | CheckProcessThread | ✅ frpc.ini 检查 + libfrpc.so | ❌ 无日志 | ❌ |
| 07-04 | HandlerMsgAndTimer | ✅ `handle msg thread is running` | ❌ 无日志 | ❌ |
| 07-05 | JobScheduler | ✅ 36 条记录 (wifi-lock-server job) | ❌ 3 条 (系统级) | ❌ |
| 07-06 | WIFIBackgroundService | ✅ `onStartCommand - startId = 1` | ❌ 无日志 | ❌ |
| 07-07 | CheckThread (WebSocket) | — | ✅ 运行中 (重试 WebSocket) | ✅ |
| 07-08 | startAllServices | — | ✅ `startAllServices (MediaLiveService excluded)` | ✅ |
| 07-09 | 30s 后台存活 | ✅ PID 不变 | ✅ PID 不变 | ✅ |
| 07-10 | 无 Crash | ✅ 0 | ✅ 0 | ✅ |

### MODULE_06 远程控制 (Vendor=128行 vs Replica=5行)

| # | 检查项 | Vendor | Replica | 状态 |
|---|--------|--------|---------|------|
| 06-01 | HttpServer | ✅ `asyncHttpServer 已启动` | ❌ 无日志 | ❌ |
| 06-02 | WebSocket Server | ✅ `MyWebSocketServer 已启动` | ❌ 无日志 | ❌ |
| 06-03 | HandlerMsgAndTimer | ✅ `handle msg thread is running` | ❌ 无日志 | ❌ |
| 06-04 | FetchClient API 调用 | ✅ register.json, agent/query, windows.json, getCacheTask | ❌ 无日志 | ❌ |
| 06-05 | WIFIBackgroundService | ✅ 运行中 | ❌ 无日志 | ❌ |
| 06-06 | 无 Crash | ✅ 0 | ✅ 0 | ✅ |

---

## 与首次测试对比 (无障碍未授权 vs 已授权)

首次测试 Replica 无障碍未手动授权，本次已授权。以下是改善项:

| 模块 | 首次 (未授权) | 本次 (已授权) | 变化 |
|------|-------------|-------------|------|
| MODULE_02 事件接收 | ❌ 无事件日志 | ✅ 多个 WINDOW_STATE_CHANGED | 修复 |
| MODULE_03 华为引擎 | ❌ 无引擎日志 | ✅ HuaweiEngine 注册 + 执行 | 修复 |
| MODULE_04 UI 自动化 | ❌ 无日志 | ✅ 5 个引擎注册 + 页面检测 | 修复 |
| MODULE_05 息屏/亮屏 | ❌ 无日志 | ✅ 全部事件响应 | 修复 |

结论: 手动授权无障碍后，MODULE_02/03/04/05 的核心功能已正常工作。

---

## 仍存在的差异清单

| # | 模块 | 差异描述 | 严重度 | 修复建议 |
|---|------|---------|--------|---------|
| 1 | 08 | 缺少 network_security_config.xml | MEDIUM | 创建 res/xml/network_security_config.xml |
| 2 | 08 | Receiver 注册少 2 个 (BootBroadcast/PackageReceiver 未在 KeepAliveManager 中注册) | LOW | 补齐到 KeepAliveManager |
| 3 | 01 | HttpServer 未启动 (`asyncHttpServer`) | CRITICAL | MainApplication.init() 中启动 HttpCommandServer |
| 4 | 01 | WebSocket Server 未启动 | CRITICAL | MainApplication.init() 中启动 LocalWebSocketServer |
| 5 | 01 | WebSocket URL 为 null (重试 5 次均失败) | HIGH | 检查 config.json 中 wsUrl 字段 |
| 6 | 01 | 无 HTTP API 请求 (register/query/windows/cacheTask) | HIGH | 启动时发起初始 API 调用 |
| 7 | 02 | 缺少 listenWindows.json 远程配置加载 | HIGH | 实现从服务器/本地加载监听窗口配置 |
| 8 | 02 | 缺少 CAN_TAKE_SCREENSHOT 能力 | HIGH | accessibility_service_config.xml 添加 canTakeScreenshot |
| 9 | 07 | KeepHeartThread 未运行 | CRITICAL | MainApplication.init() 中启动 |
| 10 | 07 | CheckProcessThread 未运行 (frpc 管理) | HIGH | MainApplication.init() 中启动 |
| 11 | 07 | HandlerMsgAndTimer 未运行 | HIGH | MainApplication.init() 中启动 |
| 12 | 07 | JobScheduler 未注册 (wifi-lock-server) | HIGH | 初始化时注册 |
| 13 | 07 | WIFIBackgroundService 未启动 | MEDIUM | MainApplication.init() 中 startService |
| 14 | 06 | HttpCommandServer 未运行 | CRITICAL | 同 #3 |
| 15 | 06 | 无 FetchClient API 调用 | HIGH | 同 #6 |

---

## 系统快照对比

### 无障碍 ServiceInfo 差异

| 属性 | Vendor | Replica |
|------|--------|---------|
| feedbackType | SPOKEN,HAPTIC,AUDIBLE,VISUAL,GENERIC,BRAILLE + 更多 | GENERIC | ❌ |
| notificationTimeout | 50 | 100 | ⚠️ |
| flags | INCLUDE_NOT_IMPORTANT_VIEWS, REQUEST_TOUCH_EXPLORATION, REQUEST_ENHANCED_WEB_ACCESSIBILITY, REPORT_VIEW_IDS, RETRIEVE_INTERACTIVE_WINDOWS | INCLUDE_NOT_IMPORTANT_VIEWS, REPORT_VIEW_IDS, RETRIEVE_INTERACTIVE_WINDOWS | ❌ |
| capabilities | CAN_RETRIEVE_WINDOW_CONTENT, CAN_PERFORM_GESTURES, CAN_TAKE_SCREENSHOT | CAN_RETRIEVE_WINDOW_CONTENT, CAN_PERFORM_GESTURES | ❌ |
| eventTypes | 11 种精确选择 | ALL (32 种) | ⚠️ |

### 网络连接差异

| 项目 | Vendor | Replica |
|------|--------|---------|
| 监听端口 | 7910 (HttpServer) + 7400 (WebSocketServer) + 4 个其他 | 无监听端口 |
| 外部连接 | 165.154.199.9:443 (api) + 165.154.203.196:443/7000 (ws/frpc) | 无外部连接 |

### JobScheduler 差异

| 项目 | Vendor | Replica |
|------|--------|---------|
| 注册 Job 数 | 36 条 (含 wifi-lock-server + SyncService 15min 周期) | 3 条 (系统级) |

---

## 结论

### 已对齐的行为 (手动授权无障碍后)

1. ✅ 启动流程: Application 初始化、Config 加载、WebView 引导页
2. ✅ 无障碍服务: 创建、正常模式、事件接收
3. ✅ 厂商适配: 华为识别、5 引擎注册、HuaweiEngine 执行
4. ✅ UI 自动化: 页面切换检测、引擎分发
5. ✅ 数据收集: 息屏/亮屏/解锁/屏保/电池 全部响应
6. ✅ 进程存活: 30s/60s 后台均存活
7. ✅ 零 Crash、零 ANR

### 核心差距 (4 个 CRITICAL)

1. **HttpServer 未启动** — Vendor 在 init 中启动 asyncHttpServer (端口 7910)，Replica 未启动
2. **WebSocket Server 未启动** — Vendor 启动 MyWebSocketServer (端口 7400)，Replica 未启动
3. **KeepHeartThread 未运行** — Vendor 10s 间隔检查 HttpServer 健康 + ADB 连接，Replica 无此线程
4. **HTTP API 请求未发起** — Vendor 启动后立即调用 register/query/windows/cacheTask 4 个 API，Replica 无请求

### 根因

Replica 的 `MainApplication.init()` 完成了:
- ✅ Config 加载
- ✅ KeepAliveManager (6 个 Receiver)
- ✅ DataCollectionManager
- ✅ NetworkManager (Conscrypt + 服务器地址)

但缺少:
- ❌ HttpCommandServer 启动
- ❌ LocalWebSocketServer 启动
- ❌ KeepHeartThread / CheckProcessThread / HandlerMsgAndTimer / StrategyThread
- ❌ JobScheduler 注册
- ❌ WIFIBackgroundService 启动
- ❌ 初始 API 请求 (register/query/windows/cacheTask)

### 修复优先级

```
P0 (CRITICAL): MainApplication.init() 补全
  → HttpCommandServer 启动
  → LocalWebSocketServer 启动
  → KeepHeartThread 启动
  → 初始 API 请求

P1 (HIGH): 配置对齐
  → accessibility_service_config.xml (feedbackType/flags/capabilities)
  → config.json wsUrl 字段
  → listenWindows.json 加载机制

P2 (MEDIUM): 补全
  → network_security_config.xml
  → CheckProcessThread + HandlerMsgAndTimer
  → JobScheduler + WIFIBackgroundService
  → BootBroadcast + PackageReceiver 注册
```
