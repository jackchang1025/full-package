# Vendor APK 真机运行时行为分析报告

**设备:** OPPO PGFM10 (Android 16, ColorOS, 192.168.31.243:36753)
**APK:** stripchat-release.apk (`org.ldtape.qqlhl`, versionCode=2, targetSdk=36)
**采集时间:** 2026-04-09 02:26:21 ~ 02:27:28 (约 67 秒)

---

## 一、启动时序图 (对应 13 阶段)

```
T+0ms    [Stage 1] MainApplication.onCreate()
         ├─ MainApplication instance create
         ├─ MainApplication begin create / end create
         └─ "com.guard.wallet 正在启动"

T+2ms    [Stage 1.1] AudioRecordManager 初始化
         └─ PCM/wav 目录: .../files/CacheAudios

T+7ms    [Stage 1.2] JobScheduler 注册
         └─ wifi-lock-server job schedule success

T+11ms   [Stage 1.3] 广播接收器批量注册 (11个, 6ms内完成)
         ├─ startAlarmReceiver 启动完成
         ├─ ScreenBroadcastReceiver 启动完成
         ├─ BootBroadcast 启动完成
         ├─ ShutDownBroadcastReceiver 启动完成
         ├─ BatteryLevelReceiver 启动完成
         ├─ PowerBroadcastReceiver 启动完成
         ├─ NetWorkReceiver 启动完成
         ├─ PackageReceiver 启动完成
         ├─ SmsReceiver 启动完成
         ├─ CallReceiver 启动完成
         └─ localeChangeReceiver 启动完成

T+18ms   [Stage 2] HttpServer 启动
         └─ "asyncHttpServer 已启动" (端口 7910 ✅)

T+20ms   [Stage 3] WebSocketServer 启动
         ├─ "webSocketServer start"
         └─ "MyWebSocketServer 已启动" (端口 7900 ✅)

T+21ms   [Stage 3.1] SmsMessageListener 初始化
         └─ 加载 smsRecognizePlugs.json (结果: null)

T+22ms   [Stage 3.2] Connection lost timer
         └─ e1.a: "Connection lost timer started"

T+24ms   [Stage 4] unlockedInstance (解锁主线程)

T+26ms   [Stage 5] CheckProcessThread 启动
         ├─ APP Lib目录 检查
         ├─ libfrpc.so 文件存在 ✅
         ├─ frpc.ini 文件不存在 ❌ (首次运行)
         └─ ApplicationUtil: 未开启ADB调试

T+37ms   [Stage 6] ShareADBConfig 请求
         └─ ❌ Failed to connect to /127.0.0.1:7911

--- Activity 阶段 ---

T+56ms   [Stage 7] CustomActivityLifecycleCallbacks
         ├─ onActivityPreCreated
         └─ onActivityCreated

T+155ms  [Stage 8] MainActivity
         ├─ onActivityStarted → "mainActivity start"
         ├─ onActivityResumed
         └─ WebView 加载 (chromium 138.0.7204.179)

T+158ms  [Stage 8.1] AccessibilityUtils 引导页
         └─ URL: https://guide.accessibility.rathat.org/860616249851785216/guide/0

T+162ms  [Stage 8.2] DeviceId 获取 (双端口轮询)
         ├─ ❌ Failed to connect to /127.0.0.1:7911/deviceId
         └─ ❌ Failed to connect to /127.0.0.1:7912/deviceId

T+180ms  [Stage 8.3] BatteryUtils 检测
         └─ BATTERY_PROPERTY_CAPACITY: 90

T+200ms  [Stage 8.4] WIFIBackgroundService
         ├─ onCreate - Thread ID = 2
         └─ onStartCommand - startId = 1

T+210ms  [Stage 8.5] NetWorkReceiver 广播响应
         ├─ android.net.wifi.WIFI_STATE_CHANGED
         ├─ android.net.conn.CONNECTIVITY_CHANGE
         └─ android.net.wifi.STATE_CHANGE

--- 后台线程阶段 (T+12s, 用户授权无障碍后) ---

T+12.04s [Stage 9] 后台线程批量启动
         ├─ HandlerMsgAndTimer: "handle msg thread is running"
         ├─ CheckProcessThread: "check process thread is running"
         ├─ KeepHeartThread: "keep heart thread is running"
         └─ KeepHeartThread: "本地HttpServer运行正常" ✅

T+12.04s [Stage 10] 设备注册 (远程 API)
         └─ ❌ Failed to connect to api.rathat.club/165.154.203.196:443
             (60秒超时后最终失败: ETIMEDOUT)

T+12.05s [Stage 11] MyAccessibilityService 启动
         ├─ "MyAccessibilityService on create"
         ├─ "辅助功能进入正常模式"
         ├─ 加载 listenWindows.json (结果: null)
         └─ 检测当前窗口: com.android.settings.SubSettings

T+12.1s  [Stage 12] AccessibilityService 自动操作
         ├─ 执行 back (performGlobalAction action=1) × 3 次
         └─ 返回桌面: com.android.launcher.Launcher
```

---

## 二、关键发现

### 2.1 端口监听状态
| 端口 | 协议 | 状态 | 用途 |
|------|------|------|------|
| 7910 | TCP | ✅ LISTEN | HTTP Server (NIO) |
| 7900 | TCP | ✅ LISTEN | WebSocket Server |
| 7911 | TCP | ❌ 未监听 | ShareADBConfig / DeviceId (被请求但无服务) |
| 7912 | TCP | ❌ 未监听 | DeviceId 备用端口 |
| 7980 | TCP | ❌ 未监听 | 未观察到 |

### 2.2 远程 API 调用
| 端点 | 状态 | 备注 |
|------|------|------|
| `https://api.rathat.club/api/device/register.json` | ❌ ETIMEDOUT 60s | 服务器 165.154.203.196:443 不可达 |
| `http://127.0.0.1:7911/shareADBConfig` | ❌ Connection refused | 端口 7911 无服务 |
| `http://127.0.0.1:7911/deviceId` | ❌ Connection refused | 同上 |
| `http://127.0.0.1:7912/deviceId` | ❌ Connection refused | 端口 7912 无服务 |

### 2.3 线程运行周期 (实测)
| 线程 | 周期 | 说明 |
|------|------|------|
| KeepHeartThread | 10s | 本地 HTTP 健康检查 ✅ |
| HandlerMsgAndTimer | 10s | 消息处理定时器 |
| CheckProcessThread | 5s | 进程/frpc 状态检查 |
| 总线程数 | 89 | 包含系统线程 |

### 2.4 无障碍服务行为
- 启动后立即执行 3 次 `performGlobalAction(BACK)` 退出设置页
- 持续监控窗口变化：rootPackageName + windowClassName + windowTitle
- 检测到每个窗口切换事件（Settings → Launcher → 自身 APP）
- 监听 `listenWindows.json` 配置（首次为 null）

### 2.5 frpc 内网穿透
- `libfrpc.so` 存在于 APK lib 目录 ✅
- `frpc.ini` 配置文件不存在（首次运行未下发）
- CheckProcessThread 每 5s 检查 frpc 状态

### 2.6 新发现的模块
| 模块 | 发现 | vendor-replica 状态 |
|------|------|-----|
| **AudioRecordManager** | 初始化 PCM/wav 缓存目录 | ❓ 需检查 |
| **e1.a** (Connection lost timer) | 连接丢失计时器 | ❓ 需检查 |
| **DeviceIdCallback** | 双端口(7911/7912)轮询获取 DeviceId | ❓ 需检查 |
| **ShareADBConfigCallback** | 请求 7911/shareADBConfig | ❓ 需检查 |
| **RegisterCallback** | 注册到 api.rathat.club | ❓ 需检查 |
| **libfrpc.so** | 内网穿透二进制 | ❓ 需检查 |
| **AccessibilityUtils** | 引导页 URL 生成 | ❓ 需检查 |
| **CustomActivityLifecycleCallbacks** | Activity 生命周期监听 | ❓ 需检查 |

---

## 三、与 vendor-replica 对比差异

### 3.1 启动流程差异

| 步骤 | Vendor 行为 | Replica 是否实现 |
|------|-------------|:---:|
| MainApplication.onCreate | ✅ 完整初始化 | ✅ |
| AudioRecordManager 初始化 | PCM/wav 目录创建 | ❓ |
| JobScheduler 注册 | wifi-lock-server | ✅ |
| 11 个 Receiver 注册 | 全部注册成功 | ✅ |
| HttpServer 启动 (7910) | NIO 异步服务器 | ✅ |
| WebSocketServer 启动 (7900) | Java-WebSocket | ✅ |
| SmsMessageListener | 加载本地插件 JSON | ❓ |
| Connection lost timer | e1.a 计时器 | ❓ |
| ShareADBConfig 请求 (7911) | 尝试连接其他实例 | ❓ |
| DeviceId 双端口轮询 | 7911 + 7912 | ❓ |
| 设备注册 API | api.rathat.club | ❓ |
| CheckProcessThread frpc | libfrpc.so + frpc.ini | ❓ |
| 无障碍服务自动返回 | 3 次 BACK 退出设置 | ✅ |

### 3.2 关键 URL/端点
```
https://api.rathat.club/api/device/register.json     — 设备注册
https://guide.accessibility.rathat.org/{id}/guide/0   — 无障碍引导页
http://127.0.0.1:7911/shareADBConfig                  — ADB 配置共享
http://127.0.0.1:7911/deviceId                        — 设备 ID (主)
http://127.0.0.1:7912/deviceId                        — 设备 ID (备)
```

---

## 四、后续优化建议

### 优先级 P0 (核心功能)
1. **端口 7911/7912 服务** — Vendor 会请求这两个端口但未监听，说明是用于多实例通信的发现协议
2. **设备注册流程** — RegisterCallback 需要完整的 OkHttp 外网连接能力 (ConnectInterceptor)
3. **frpc 内网穿透** — CheckProcessThread 持续检查 frpc.ini，说明后续会从服务器下发配置

### 优先级 P1 (功能完善)
4. **AudioRecordManager** — 录音功能初始化
5. **e1.a Connection lost timer** — WebSocket 断线重连计时器
6. **SmsMessageListener** — 短信识别插件加载
7. **AccessibilityUtils 引导页** — URL 包含设备 ID，需要理解生成逻辑

### 优先级 P2 (增强)
8. **CustomActivityLifecycleCallbacks** — Activity 生命周期全局监听
9. **listenWindows.json** — 自定义窗口监听配置
