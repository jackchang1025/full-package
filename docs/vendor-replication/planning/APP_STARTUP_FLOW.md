# App 启动完整时序图与组件流程

> 最后更新: 2026-03-31 — Pipeline 架构重构后

## 一、全局时序图（从安装到首页就绪）

```
时间轴 ──────────────────────────────────────────────────────────────────────►

┌──────────────────────────────────────────────────────────────────────────────┐
│  阶段 1: Application 初始化 (~3s)                                            │
│                                                                              │
│  ActivMain.onCreate()                                                        │
│      │                                                                       │
│      ▼                                                                       │
│  MainApplication.init(app)                                                   │
│      ├── initInternal() [主线程/同步]                                         │
│      │   ├── createCacheDirs()                                               │
│      │   ├── loadConfig()           ← 解密 assets/config.json                │
│      │   ├── MessageQueueManager()                                           │
│      │   ├── StrategyThread.getInstance()                                    │
│      │   ├── HttpCommandServer.getInstance()                                 │
│      │   └── LocalWebSocketServer.startServer()                              │
│      │                                                                       │
│      └── new Thread("init-thread") → unlockedInstance() [异步]               │
│          ├── NetworkManager.init()  ← WebSocket 连接服务器                    │
│          ├── HiddenApiBypass.bypass()                                        │
│          ├── KeepAliveManager.init()                                         │
│          │   ├── registerReceivers() ← 8 个广播接收器                         │
│          │   ├── startCheckThread()                                          │
│          │   └── initJobScheduler()  ← Job 116                              │
│          ├── DataCollectionManager.startAll()                                │
│          ├── CheckProcessThread.startTimer()                                 │
│          ├── KeepHeartThread.schedule(10s)                                   │
│          ├── triggerInitialApiRequests()                                     │
│          └── registerContentObservers() ← 6 个 ContentObserver              │
└──────────────────────────────────────────────────────────────────────────────┘
```


```
┌──────────────────────────────────────────────────────────────────────────────┐
│  阶段 2: 用户授权无障碍服务 (手动 或 ADB WRITE_SECURE_SETTINGS)               │
│                                                                              │
│  ActivMain.onResume()                                                        │
│      ├── 检查 MyAccessibilityService.P() (无障碍服务是否运行)                  │
│      ├── 检查 isAdbSecureMode() (ADB WRITE_SECURE_SETTINGS 权限)             │
│      │                                                                       │
│      ├── [无障碍未开启 & 无ADB权限]                                           │
│      │     ├── WebView 加载引导页 URL                                         │
│      │     └── showGuideDialog() → 弹窗引导用户去开启无障碍                    │
│      │                                                                       │
│      └── [无障碍已开启 或 有ADB权限]                                          │
│            ├── dismissGuideDialog()                                           │
│            ├── WebView 加载主页 URL                                           │
│            └── 进入正常使用状态                                                │
└──────────────────────────────────────────────────────────────────────────────┘
```

```
┌──────────────────────────────────────────────────────────────────────────────┐
│  阶段 3: 无障碍服务启动 (~2s)                                                │
│                                                                              │
│  MyAccessibilityService.onServiceConnected()                                 │
│      ├── r0()  [ServiceInfo 配置]                                            │
│      │   ├── feedbackType = -1 (ALL)                                         │
│      │   ├── eventTypes = 0x80783F                                           │
│      │   ├── flags = 91 (0x5B)                                               │
│      │   └── API 33+: setCacheEnabled(true)                                  │
│      │                                                                       │
│      ├── j0()  [服务初始化]                                                   │
│      │   ├── f219p.set(this)  ← 保存服务实例引用                              │
│      │   ├── engineManager = new EngineManager(this)                          │
│      │   │   └── registerVendorEngines()                                      │
│      │   │       ├── HuaweiEngine                                            │
│      │   │       ├── XiaomiEngine                                            │
│      │   │       ├── OppoEngine                                              │
│      │   │       ├── PermissionAutoGrantEngine                                │
│      │   │       ├── VivoEngine                                              │
│      │   │       └── TranssionEngine                                         │
│      │   │                                                                   │
│      │   ├── isFirstOpen? → performGlobalAction(BACK) → markFirstOpenDone()  │
│      │   ├── p0()  ← 上报 ACCESSIBILITY_CONTAINER 事件                       │
│      │   ├── d0()  ← 加载 listenWindows.json                                 │
│      │   │                                                                   │
│      │   └── ★ new Thread("strategy-trigger")                                │
│      │       └── sleep(1500ms) → triggerKeepAliveIfNeeded()                   │
│      │                           ↓↓↓ 进入阶段 4 ↓↓↓                          │
│      │                                                                       │
│      └── bringAppToFront() → 拉起 ActivMain 回前台                           │
└──────────────────────────────────────────────────────────────────────────────┘
```


```
┌──────────────────────────────────────────────────────────────────────────────┐
│  阶段 4: 自动化管道 Pipeline (~30-70s)                                       │
│                                                                              │
│  StrategyThread.triggerKeepAliveIfNeeded()                                   │
│      ├── [Guard 1] keepAliveTriggered == true? → return (内存防重入)           │
│      ├── [Guard 2] P() == null? → return (无障碍未启动)                       │
│      ├── [Guard 3] !isHuawei && !isXiaomi && !isOppo? → return              │
│      ├── [Guard 4] compareAndSet(false, true) 失败? → return (CAS 防并发)    │
│      │                                                                       │
│      └── AutomationPipeline.executeStandard(service)                         │
│          └── new Thread("automation-pipeline")                               │
│                                                                              │
│  ┌────────────────────────────────────────────────────────────────────────┐  │
│  │  Pipeline 洋葱模型 — 10 个 Stage 顺序执行                              │  │
│  │  (对齐 Laravel Illuminate\Pipeline\Pipeline)                           │  │
│  │                                                                        │  │
│  │  Stage 1: VersionCheckStage                                            │  │
│  │  └── 对比 APK versionCode vs SharedPreferences 保存值                   │  │
│  │      ├── 版本变化 → 重置 keepAliveCompleted=false                       │  │
│  │      ├── 首次安装 → 无需重置                                            │  │
│  │      └── 版本不变 → 不操作                                              │  │
│  │                                                                        │  │
│  │  Stage 2: CompletionCheckStage                                         │  │
│  │  └── 检查 keepAliveCompleted (持久化)                                   │  │
│  │      ├── [已完成 & 版本未变] → ★ 短路终止，不执行后续 Stage              │  │
│  │      └── [未完成 或 版本变化] → 继续                                    │  │
│  │                                                                        │  │
│  │  Stage 3: ShowOverlayStage                                             │  │
│  │  └── BlockViewHelper.show()                                            │  │
│  │      ├── muteAll() ← 禁用旋转/震动/静音                                │  │
│  │      ├── createView() [主线程] → TYPE_ACCESSIBILITY_OVERLAY             │  │
│  │      ├── 轮询等待 viewShowing=true (最多 10s)                           │  │
│  │      └── StealthHelper.updateProgress(10)                              │  │
│  │                                                                        │  │
│  │  ═══════════════════════════════════════════════════════════════════    │  │
│  │  此时: 遮罩全屏覆盖，用户看不到后续操作                                  │  │
│  │  ═══════════════════════════════════════════════════════════════════    │  │
│  │                                                                        │  │
│  │  Stage 4: LaunchSettingsStage                                          │  │
│  │  └── StrategyThread.launchSettingsForVendor()                          │  │
│  │      ├── performGlobalAction(HOME) ← 先回桌面                          │  │
│  │      ├── sleep(500ms)                                                  │  │
│  │      ├── [华为] Intent → HWSettings (搜索页)                           │  │
│  │      ├── [小米] Intent → ApplicationsDetailsActivity (应用详情)         │  │
│  │      └── [OPPO] Intent → ColorOS 电池优化页                            │  │
│  │                                                                        │  │
│  │  Stage 5: VendorEngineStage                                            │  │
│  │  └── CountDownLatch.await(120s) ← 阻塞管道线程                         │  │
│  │      │                                                                 │  │
│  │      │  ┌── 无障碍事件驱动循环 (主线程) ──────────────────────────┐     │  │
│  │      │  │  MyAccessibilityService.onAccessibilityEvent()          │     │  │
│  │      │  │  └── engineManager.dispatchEvent()                      │     │  │
│  │      │  │                                                         │     │  │
│  │      │  │  [华为] HuaweiEngine                                    │     │  │
│  │      │  │  ├── 搜索"启动管理" → 点击进入                          │     │  │
│  │      │  │  ├── 找到本应用 → 关闭"自动管理"                        │     │  │
│  │      │  │  ├── 弹窗 → 勾选 允许自启动/后台活动/关联启动            │     │  │
│  │      │  │  └── 完成 → Z() → latch.countDown()                    │     │  │
│  │      │  │                                                         │     │  │
│  │      │  │  [小米] XiaomiEngine                                    │     │  │
│  │      │  │  ├── 应用详情 → 点击"自启动"开关 → 确认对话框            │     │  │
│  │      │  │  ├── 点击"省电策略"/"电量消耗" → 选择"无限制"            │     │  │
│  │      │  │  └── 完成 → Z() → latch.countDown()                    │     │  │
│  │      │  │                                                         │     │  │
│  │      │  │  [OPPO] OppoEngine                                     │     │  │
│  │      │  │  ├── 电池优化 → 自启动管理 → 开启自启动                  │     │  │
│  │      │  │  ├── 权限管理 → 逐项授予权限 (失败2次则跳过)             │     │  │
│  │      │  │  └── 完成 → Z() → latch.countDown()                    │     │  │
│  │      │  └─────────────────────────────────────────────────────────┘     │  │
│  │      │                                                                 │  │
│  │      └── latch released → 继续管道                                     │  │
│  │                                                                        │  │
│  │  Stage 6: NavigateToAppStage                                           │  │
│  │  └── startActivity(launchIntent) ← 遮罩遮挡下将 App 拉回前台           │  │
│  │      └── sleep(1000ms) ← 等待启动动画                                  │  │
│  │                                                                        │  │
│  │  Stage 7: PermissionRequestStage                                       │  │
│  │  ├── [OPPO] 跳过 (已在 OppoEngine 中处理)                             │  │
│  │  └── [华为/小米] ActivMain.triggerPermissionRequest()                  │  │
│  │      └── requestNextPermissionGroup() ← 逐组弹出权限对话框             │  │
│  │          │  CAMERA → RECORD_AUDIO → LOCATION → SMS → CONTACTS →       │  │
│  │          │  CALL_LOG → CALL_PHONE → READ_PHONE_STATE → STORAGE →      │  │
│  │          │  POST_NOTIFICATIONS                                         │  │
│  │          │                                                             │  │
│  │          │  ┌── PermissionAutoGrantEngine (被动监听) ──────────┐       │  │
│  │          │  │  matchWindow(permissioncontroller) → true        │       │  │
│  │          │  │  └── autoClickAllow()                            │       │  │
│  │          │  │      ├── 找"允许"按钮 (始终允许 > 使用中允许 > 允许)│      │  │
│  │          │  │      └── performAction(CLICK)                    │       │  │
│  │          │  └──────────────────────────────────────────────────┘       │  │
│  │          │                                                             │  │
│  │          └── 轮询 allPermissionsGranted() (最多 60s)                   │  │
│  │                                                                        │  │
│  │  Stage 8: MediaProjectionStage                                         │  │
│  │  ├── [API ≥ 30] 跳过 (用 AccessibilityService.takeScreenshot)          │  │
│  │  └── [API < 30] 触发录屏授权弹窗 → 自动点击"立即开始"                  │  │
│  │                                                                        │  │
│  │  Stage 9: RemoveOverlayStage                                           │  │
│  │  └── BlockViewHelper.removeViewInternal() [主线程]                      │  │
│  │      ├── windowManager.removeViewImmediate(overlay)                     │  │
│  │      ├── viewShowing=false                                             │  │
│  │      └── restoreMuteStrategy() ← 恢复旋转/震动/音量                    │  │
│  │                                                                        │  │
│  │  Stage 10: MarkCompletedStage                                          │  │
│  │  └── SharedPreferences 持久化                                          │  │
│  │      ├── keepAliveCompleted = true                                     │  │
│  │      └── last_version_code = 当前 versionCode                          │  │
│  └────────────────────────────────────────────────────────────────────────┘  │
│                                                                              │
│  Pipeline.andFinally()                                                       │
│  └── Safety net: 如果遮罩仍在显示 → 强制移除                                 │
│  └── currentContext = null                                                    │
└──────────────────────────────────────────────────────────────────────────────┘
```


```
┌──────────────────────────────────────────────────────────────────────────────┐
│  阶段 5: 回到首页 — 正常运行                                                 │
│                                                                              │
│  Pipeline 结束后:                                                             │
│  ├── ActivMain 已在 Stage 6 被拉到前台                                        │
│  ├── 遮罩在 Stage 9 移除 → 用户重新看到屏幕                                   │
│  ├── WebView 显示主页面                                                       │
│  ├── 所有权限已授予                                                           │
│  ├── 保活已配置 (自启动 + 电池优化)                                           │
│  └── 下次启动 → CompletionCheckStage 检测到已完成 → 直接跳过整个管道          │
│                                                                              │
│  [Panel 发送投屏命令]                                                         │
│  Panel → WebSocket Server (PHP) → Device WebSocket Client                    │
│      └── CommandDispatcher → ScreenshotHandler                               │
│          └── scheduleAtFixedRate(captureAndSendFrame, 0, 1100ms)             │
│              └── MyAccessibilityService.takeScreenshotAsync()                │
│                  ├── [API ≥ 30] AccessibilityService.takeScreenshot()         │
│                  └── [API < 30] MediaProjection + ImageReader                │
│                      └── Bitmap → JPEG → Base64 → WebSocket → Panel 显示     │
└──────────────────────────────────────────────────────────────────────────────┘
```

## 二、多角色时序图

```
用户          ActivMain        MyA11yService     Pipeline线程        VendorEngine      PermAutoGrant
 │               │                  │                  │                 │                  │
 │──打开App──→ onCreate            │                  │                 │                  │
 │               ├─ WebView+背景    │                  │                 │                  │
 │               │                  │                  │                 │                  │
 │             onResume             │                  │                 │                  │
 │               ├─ P()==null       │                  │                 │                  │
 │               ├─ showGuide      │                  │                 │                  │
 │               │  Dialog()        │                  │                 │                  │
 │               │                  │                  │                 │                  │
 │──开启无障碍──→│       onServiceConnected            │                 │                  │
 │               │                  ├─ r0() 配置       │                 │                  │
 │               │                  ├─ j0() 初始化     │                 │                  │
 │               │                  │  ├─ 注册引擎     │                 │                  │
 │               │                  │  └─ 1.5s后 ─────→│                 │                  │
 │               │                  │    trigger()     │                 │                  │
 │               │                  ├─ bringAppToFront │                 │                  │
 │               │                  │                  │                 │                  │
 │             onResume             │     executeStandard()              │                  │
 │               ├─ dismissGuide   │                  │                 │                  │
 │               ├─ loadMainUrl    │   VersionCheck   │                 │                  │
 │               │                  │   CompletionCheck│                 │                  │
 │               │                  │                  │                 │                  │
 │  ┌────────────┼──────────────────┼── ShowOverlay ──┤                 │                  │
 │  │ 遮罩覆盖   │                  │                  │                 │                  │
 │  │            │                  │   LaunchSettings │                 │                  │
 │  │            │                  │   ├─ HOME        │                 │                  │
 │  │            │                  │   └─ 启动设置页  │                 │                  │
 │  │            │                  │                  │                 │                  │
 │  │            │                  │   VendorEngine   │                 │                  │
 │  │            │                  │   await(latch)───┼────────────────→│                  │
 │  │            │                  │                  │                 │                  │
 │  │            │          ←─窗口事件─┘               │    自动化操作    │                  │
 │  │            │                  │                  │    (自启动+电池) │                  │
 │  │            │                  │                  │                 │                  │
 │  │            │                  │                  │   countDown()←──┤                  │
 │  │            │                  │   ←──latch释放───┤                 │                  │
 │  │            │                  │                  │                 │                  │
 │  │            │                  │   NavigateToApp  │                 │                  │
 │  │       ←────┤ (拉回前台)       │                  │                 │                  │
 │  │            │                  │                  │                 │                  │
 │  │            │                  │   PermissionReq  │                 │                  │
 │  │            │  triggerPerm ←───┤                  │                 │                  │
 │  │            ├─ requestPerms   │                  │                 │                  │
 │  │            │                  │                  │                 │                  │
 │  │            │          ←─权限弹窗─┘               │                 │    matchWindow() │
 │  │            │                  │                  │                 │    autoClick()   │
 │  │            │                  │                  │                 │    ├─ "允许"     │
 │  │            │                  │                  │                 │    └─ 下一组     │
 │  │            │                  │                  │                 │                  │
 │  │            │                  │   MediaProjection│                 │                  │
 │  │            │                  │   (API<30 only)  │                 │                  │
 │  │            │                  │                  │                 │                  │
 │  │            │                  │   RemoveOverlay  │                 │                  │
 │  └────────────┼──────────────────┼──────────────────┤                 │                  │
 │               │                  │                  │                 │                  │
 │               │                  │   MarkCompleted  │                 │                  │
 │               │                  │   Pipeline END   │                 │                  │
 │               │                  │                  │                 │                  │
 │  ←─看到首页───┤                  │                  │                 │                  │
 │               │                  │                  │                 │                  │
```

## 三、组件交互关系图

```
┌─────────────────────────────────────────────────────────────────────┐
│                         Android System                              │
│  ┌──────────────┐  ┌──────────────┐  ┌───────────────────────────┐  │
│  │ WindowManager │  │ MediaProjection│  │ AccessibilityFramework  │  │
│  │   (遮罩窗口)  │  │  Manager      │  │   (事件分发)             │  │
│  └──────┬───────┘  └──────┬───────┘  └───────────┬───────────────┘  │
└─────────┼─────────────────┼──────────────────────┼──────────────────┘
          │                 │                      │
          │                 │                      │ onAccessibilityEvent
          │                 │                      │
┌─────────┼─────────────────┼──────────────────────┼──────────────────┐
│         │           App Process                  │                  │
│         │                 │                      │                  │
│  ┌──────▼───────┐  ┌─────▼──────────┐  ┌────────▼───────────────┐  │
│  │ BlockView    │  │ MediaLive      │  │ MyAccessibilityService │  │
│  │ Helper       │  │ Service        │  │                        │  │
│  │              │  │                │  │ ┌─ EngineManager ────┐ │  │
│  │ show()       │  │ onStartCommand │  │ │                    │ │  │
│  │ isShowing()  │  │ → initMedia    │  │ │ dispatchEvent()    │ │  │
│  │ removeView   │  │   Projection   │  │ │  ├─ HuaweiEngine   │ │  │
│  │  Internal()  │  │                │  │ │  ├─ XiaomiEngine   │ │  │
│  │              │  └────────────────┘  │ │  ├─ OppoEngine     │ │  │
│  └──────────────┘                      │ │  ├─ PermAutoGrant  │ │  │
│         ▲                              │ │  ├─ VivoEngine     │ │  │
│         │                              │ │  └─ TranssionEng   │ │  │
│         │ show/remove                  │ └────────────────────┘ │  │
│         │                              └────────────────────────┘  │
│  ┌──────┴───────────────────────────────────────┐                  │
│  │ AutomationPipeline (洋葱模型)                 │                  │
│  │                                               │                  │
│  │ send(ctx).through(stages).thenReturn()        │                  │
│  │                                               │                  │
│  │ ┌─VersionCheck ─┐                            │                  │
│  │ │┌─CompletionChk┐│                            │                  │
│  │ ││┌─ShowOverlay─┐││                            │                  │
│  │ │││┌LaunchSett─┐│││                            │                  │
│  │ ││││┌VendorEng┐││││  ← CountDownLatch         │                  │
│  │ │││││┌NavApp──┐│││││                           │                  │
│  │ ││││││┌Perm──┐││││││                           │                  │
│  │ │││││││┌Med─┐│││││││                           │                  │
│  │ ││││││││┌Rm┐││││││││                           │                  │
│  │ │││││││││Mk│││││││││  ← MarkCompleted          │                  │
│  │ ││││││││└──┘││││││││                           │                  │
│  │ │││││││└────┘│││││││                           │                  │
│  │ ││││││└──────┘││││││                           │                  │
│  │ │││││└────────┘│││││                           │                  │
│  │ ││││└──────────┘││││                           │                  │
│  │ │││└────────────┘│││                           │                  │
│  │ ││└──────────────┘││                           │                  │
│  │ │└────────────────┘│                           │                  │
│  │ └──────────────────┘                           │                  │
│  └────────────────────────────────────────────────┘                  │
│                                                                      │
│  ┌──────────────┐                      ┌────────────────────────┐   │
│  │ ActivMain    │                      │ ScreenshotHandler      │   │
│  │              │                      │                        │   │
│  │ onCreate()   │                      │ captureAndSendFrame()  │   │
│  │ onResume()   │                      │ → takeScreenshotAsync  │   │
│  │ triggerPerm  │                      └────────────────────────┘   │
│  │ Request()    │                               ▲                   │
│  └──────────────┘                               │ onCommand         │
│                                        ┌────────┴───────────────┐   │
│  ┌──────────────┐                      │ WebSocketClient        │   │
│  │ Strategy     │                      │ → CommandDispatcher    │   │
│  │ Thread       │                      └────────────────────────┘   │
│  │              │                               ▲                   │
│  │ triggerKeep  │                               │ WebSocket         │
│  │ AliveIf      │                      ┌────────┴────────┐         │
│  │ Needed()     │                      │  PHP Swoole      │         │
│  └──────────────┘                      │  Server :8081    │         │
│                                        └────────┬────────┘         │
└──────────────────────────────────────────────────┼──────────────────┘
                                                   ▲
                                          ┌────────┴────────┐
                                          │  Web Panel       │
                                          │  (Vue 3)         │
                                          └─────────────────┘
```


## 四、Pipeline Stage 详细说明

### Stage 对照表

| # | Stage | 职责 | 短路条件 | 耗时 |
|---|-------|------|---------|------|
| 1 | VersionCheckStage | 对比 versionCode，版本变化时重置完成标志 | 无 | <1ms |
| 2 | CompletionCheckStage | 检查持久化完成状态 | 已完成 & 版本未变 → 终止 | <1ms |
| 3 | ShowOverlayStage | 显示全屏遮罩，禁用旋转/震动/静音 | 显示失败 → 终止 | ~200ms |
| 4 | LaunchSettingsStage | HOME → 启动厂商设置页 | 非支持设备 → 跳过 | ~500ms |
| 5 | VendorEngineStage | CountDownLatch 等待引擎完成 | 超时 120s 也继续 | 5-30s |
| 6 | NavigateToAppStage | 遮罩下拉回 App 前台 | 无 | ~1s |
| 7 | PermissionRequestStage | 逐组请求运行时权限 | OPPO 跳过；已全部授予 → 跳过 | 10-30s |
| 8 | MediaProjectionStage | 请求录屏权限 (API<30) | API ≥ 30 → 跳过 | ~3s |
| 9 | RemoveOverlayStage | 移除遮罩，恢复设备状态 | 遮罩未显示 → 跳过 | ~500ms |
| 10 | MarkCompletedStage | 持久化 completed + versionCode | 无 | <1ms |

### 厂商引擎对照表

| 厂商 | Engine | LaunchSettings 目标 | 自动化内容 | 权限处理 |
|------|--------|-------------------|-----------|---------|
| 华为 | HuaweiEngine | HWSettings 搜索页 | 搜索"启动管理" → 关闭自动管理 → 勾选三项 | Stage 7 标准流程 |
| 小米 | XiaomiEngine | ApplicationsDetailsActivity | 自启动 Switch → 省电策略"无限制" | Stage 7 标准流程 |
| OPPO | OppoEngine | ColorOS 电池优化页 | 电池优化 → 自启动 → 权限管理 | Engine 内部处理 |

### 关键技术点

1. **洋葱模型**: Pipeline 对齐 Laravel `Illuminate\Pipeline\Pipeline`，每个 Stage 包裹下一个，`next.run()` 继续，不调用则短路
2. **CountDownLatch**: VendorEngineStage 通过 latch 阻塞管道线程，引擎在无障碍事件线程完成后 `countDown()` 释放
3. **遮罩守卫**: 所有引擎在 `onEventSafe()` 入口检查 `BlockViewHelper.isShowing()`，遮罩移除后立即停止响应
4. **Safety net**: `Pipeline.andFinally()` 确保即使 Stage 异常，遮罩也会被移除
5. **版本感知**: VersionCheckStage + CompletionCheckStage 组合实现"更新安装后重跑管道"

## 五、真机测试结果 (2026-03-31)

| 设备 | 型号 | 结果 | Pipeline 耗时 | 备注 |
|------|------|------|-------------|------|
| 华为 | FIN-AL60 (EMUI 14.2, SDK 31) | ✅ 全部通过 | ~32s | 搜索直达+权限+遮罩 |
| 小米 | (HyperOS, SDK 34) | ✅ 全部通过 | ~66s | 自启动+电池优化+权限+遮罩 |
| OPPO | (ColorOS, Android 16) | ✅ 保活通过 | ~40s | 位置权限因 accessibilityDataSensitive 跳过 |
