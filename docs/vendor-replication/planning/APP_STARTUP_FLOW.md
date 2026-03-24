# App 启动完整时序图与组件流程

## 一、全局时序图（从安装到投屏就绪）

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

┌──────────────────────────────────────────────────────────────────────────────┐
│  阶段 2: 用户授权无障碍服务 (手动 或 ADB WRITE_SECURE_SETTINGS)               │
│                                                                              │
│  ActivMain.onResume()                                                        │
│      ├── P() == null && !isAdbSecureMode() → showGuideDialog()               │
│      │       用户看到引导弹窗 → 跳转无障碍设置 → 手动开启                      │
│      │                                                                       │
│      └── P() == null && isAdbSecureMode() → tryAutoEnableAccessibility()     │
│              通过 WRITE_SECURE_SETTINGS 自动启用                              │
└──────────────────────────────────────────────────────────────────────────────┘

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
│      └── j0()  [服务初始化]                                                   │
│          ├── f219p.set(this)  ← 保存服务实例引用                              │
│          ├── engineManager = new EngineManager(this)                          │
│          │   └── registerVendorEngines()                                      │
│          │       ├── HuaweiEngine                                            │
│          │       ├── XiaomiEngine                                            │
│          │       ├── PermissionAutoGrantEngine                                │
│          │       ├── OppoEngine                                              │
│          │       ├── VivoEngine                                              │
│          │       └── TranssionEngine                                         │
│          │                                                                   │
│          ├── isFirstOpen? → performGlobalAction(BACK) → markFirstOpenDone()  │
│          ├── p0()  ← 上报 ACCESSIBILITY_CONTAINER 事件                       │
│          ├── d0()  ← 加载 listenWindows.json                                 │
│          │                                                                   │
│          └── ★ new Thread("strategy-trigger")                                │
│              └── sleep(1500ms) → triggerKeepAliveIfNeeded()                   │
│                                  ↓↓↓ 进入阶段 4 ↓↓↓                          │
└──────────────────────────────────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────────────────────────────────┐
│  阶段 4: 保活自动化 — 遮罩 + 引擎 (~20-40s)                                  │
│                                                                              │
│  StrategyThread.triggerKeepAliveIfNeeded()                                   │
│      ├── keepAliveTriggered? → return (内存防重入)                             │
│      ├── ★ isKeepAliveCompleted()? → return (持久化检查)                       │
│      │   ↑↑↑ 更新安装时这里返回 true，整个阶段 4-6 被跳过 ↑↑↑                  │
│      ├── P() == null? → return                                               │
│      ├── !isHuawei() && !isXiaomi()? → return                                │
│      ├── compareAndSet(false, true)                                          │
│      │                                                                       │
│      └── new Thread("strategy-trigger")                                      │
│          └── sleep(500ms) → applyBlockView(null, true)                       │
│              │                                                               │
│              ▼                                                               │
│  StrategyThread.applyBlockView()                                             │
│      ├── BlockViewHelper.show()                                              │
│      │   ├── muteAll() ← 禁用旋转/震动/静音                                  │
│      │   ├── createView() [主线程]                                            │
│      │   │   ├── BlockOverlayView (production/debug)                         │
│      │   │   └── WindowManager.addView(TYPE_ACCESSIBILITY_OVERLAY)           │
│      │   └── 轮询等待 viewShowing=true (最多 10s)                             │
│      │                                                                       │
│      ├── StealthHelper.updateProgress(10)                                    │
│      │                                                                       │
│      └── launchSettingsForVendor()                                           │
│          ├── performGlobalAction(HOME)  ← 先回桌面                            │
│          ├── sleep(500ms)                                                    │
│          │                                                                   │
│          ├── [华为] Intent → com.android.settings.HWSettings                 │
│          └── [小米] Intent → ApplicationsDetailsActivity + package_name       │
│                                                                              │
│  ═══════════════════════════════════════════════════════════════════          │
│  此时: 遮罩全屏覆盖 + 设置页面在遮罩下打开                                    │
│  ═══════════════════════════════════════════════════════════════════          │
│                                                                              │
│  MyAccessibilityService.onAccessibilityEvent() [事件驱动循环]                 │
│      ├── 遮罩守卫: isShowing() && launcher出现 → restoreSettingsTask()        │
│      ├── G(event)  ← 更新根节点缓存                                          │
│      └── f0(event) ← ★ engineManager.dispatchEvent()                         │
│          │                                                                   │
│          ├── [华为] HuaweiEngine.onWindowMatched()                           │
│          │   ├── 主设置页 → 点击"电池" → 点击"启动管理"                         │
│          │   ├── 启动管理列表 → 找到本应用 → 关闭"自动管理"                     │
│          │   ├── 弹窗确认 → 勾选 允许自启动/后台活动/关联启动                    │
│          │   └── 完成 → markKeepAliveCompleted() → Z()                       │
│          │                                                                   │
│          └── [小米] XiaomiEngine.onWindowMatched()                            │
│              ├── 应用详情 → 点击"自启动"开关                                   │
│              ├── 电池优化 → 设为无限制                                         │
│              └── 完成 → markKeepAliveCompleted() → Z()                       │
└──────────────────────────────────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────────────────────────────────┐
│  阶段 5: 引擎完成 — 移除遮罩 + 触发权限请求 (~15-30s)                         │
│                                                                              │
│  AutoEngine.Z() [引擎清理]                                                   │
│      ├── StealthHelper.updateProgress(100)                                   │
│      ├── X()  ← pauseProxy()                                                │
│      ├── service.H(true, true) ← 清理缓存                                    │
│      ├── t0()  ← 上报保活状态                                                │
│      ├── scheduler.shutdownNow()                                             │
│      ├── finished=true, running=false                                        │
│      ├── service.resumeProxy() ← ★ 恢复事件处理 (权限弹窗需要!)               │
│      │                                                                       │
│      └── BlockViewHelper.removeWithDestroy()                                 │
│          ├── 启动 ActivMain (遮罩遮挡下，用户不可见)                           │
│          ├── sleep(1000ms) ← 等待 Activity 启动动画                           │
│          │                                                                   │
│          ├── ★ ActivMain.triggerPermissionRequest()                           │
│          │   └── runOnUiThread → requestAllPermissions()                      │
│          │       └── requestNextPermissionGroup()                             │
│          │                                                                   │
│          ├── 轮询等待 allPermissionsGranted() (最多 60s)                      │
│          │   │                                                               │
│          │   │  ┌── 权限弹窗出现 ──────────────────────────────┐              │
│          │   │  │  PermissionAutoGrantEngine.matchWindow()      │              │
│          │   │  │  ├── BlockViewHelper.isShowing()? → true ✓   │              │
│          │   │  │  └── autoClickAllow()                         │              │
│          │   │  │      ├── 找到"允许"按钮                       │              │
│          │   │  │      └── performAction(CLICK) → 授予权限      │              │
│          │   │  └──────────────────────────────────────────────┘              │
│          │   │                                                               │
│          │   │  onRequestPermissionsResult(1013)                             │
│          │   │  └── 500ms delay → requestNextPermissionGroup()               │
│          │   │      └── 下一组权限... (循环)                                  │
│          │   │                                                               │
│          │   └── allPermissionsGranted() == true → break                     │
│          │                                                                   │
│          │  ★ 所有权限授予完成后:                                              │
│          │  requestNextPermissionGroup()                                      │
│          │  └── "All permissions granted"                                     │
│          │      └── API < 30? → requestMediaProjectionPermission()            │
│          │          └── startActivityForResult(createScreenCaptureIntent())   │
│          │              └── 系统弹窗: "开始录制屏幕?"                          │
│          │                  └── PermissionAutoGrantEngine 自动点击"立即开始"   │
│          │                                                                   │
│          │  onActivityResult(REQUEST_MEDIA_PROJECTION=1003)                   │
│          │  └── startForegroundService(MediaLiveService)                      │
│          │      └── MediaLiveService.onStartCommand()                         │
│          │          └── service.initMediaProjection(code, data) ← ★ 截屏就绪  │
│          │                                                                   │
│          ├── doRemoveView() [主线程]                                          │
│          │   ├── windowManager.removeViewImmediate(overlay)                   │
│          │   ├── viewShowing=false                                            │
│          │   └── restoreMuteStrategy() ← 恢复旋转/震动/音量                   │
│          │                                                                   │
│          └── resetOverlayGuard()                                             │
└──────────────────────────────────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────────────────────────────────┐
│  阶段 6: 正常运行 — WebSocket 命令循环                                        │
│                                                                              │
│  [Panel 发送投屏命令]                                                         │
│  Panel → WebSocket Server (PHP) → Device WebSocket Client                    │
│      │                                                                       │
│      ▼                                                                       │
│  WebSocketClient.onMessage()                                                 │
│      └── parse JSON → commandListener.onCommand(type, subc, json)            │
│          │                                                                   │
│          ▼                                                                   │
│  CommandDispatcher.onCommand("screencomd", "Screen", json)                   │
│      └── dispatchScreenCommand("Screen", json)                               │
│          └── screenshotHandler.handle(payload)                               │
│              │                                                               │
│              ▼                                                               │
│  ScreenshotHandler.handle()                                                  │
│      └── comdtype="SN" → startStreaming("screen")                            │
│          └── scheduleAtFixedRate(captureAndSendFrame, 0, 1100ms)             │
│              │                                                               │
│              ▼ (每 1100ms)                                                   │
│  captureAndSendFrame()                                                       │
│      └── MyAccessibilityService.takeScreenshotAsync(callback)                │
│          │                                                                   │
│          ├── [API ≥ 30] takeScreenshotViaAccessibility()                     │
│          │   └── AccessibilityService.takeScreenshot()                        │
│          │       └── HardwareBuffer → Bitmap → JPEG → Base64 → WebSocket     │
│          │                                                                   │
│          └── [API < 30] takeScreenshotViaMediaProjection()                   │
│              └── MediaProjection + ImageReader + VirtualDisplay               │
│                  └── Image → Bitmap → JPEG → Base64 → WebSocket              │
│                                                                              │
│  ★ 如果 MediaProjection 未初始化:                                             │
│    callback.onError("MediaProjection not initialized")                       │
│    → 投屏数据为空 → Panel 无画面                                              │
└──────────────────────────────────────────────────────────────────────────────┘
```

## 二、组件交互关系图

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
│  │ removeWith   │  │   Projection   │  │ │  ├─ HuaweiEngine   │ │  │
│  │  Destroy()   │  │   on service   │  │ │  ├─ XiaomiEngine   │ │  │
│  │              │  │                │  │ │  ├─ PermissionAuto  │ │  │
│  │ sendProgress │  └────────────────┘  │ │  │  GrantEngine    │ │  │
│  │  ()          │                      │ │  ├─ OppoEngine     │ │  │
│  └──────────────┘                      │ │  ├─ VivoEngine     │ │  │
│         ▲                              │ │  └─ TranssionEng   │ │  │
│         │                              │ └────────────────────┘ │  │
│         │ show/remove                  │                        │  │
│         │                              │ takeScreenshotAsync()  │  │
│  ┌──────┴───────┐                      │  ├─ API≥30: Accessib  │  │
│  │ Strategy     │                      │  └─ API<30: MediaProj │  │
│  │ Thread       │                      └────────────────────────┘  │
│  │              │                               ▲                  │
│  │ triggerKeep  │                               │                  │
│  │ AliveIf      │                               │ takeScreenshot   │
│  │ Needed()     │                               │                  │
│  │              │                      ┌────────┴───────────────┐  │
│  │ applyBlock   │                      │ ScreenshotHandler      │  │
│  │ View()       │                      │                        │  │
│  │              │                      │ handle() → streaming   │  │
│  │ isKeepAlive  │                      │ captureAndSendFrame()  │  │
│  │ Completed()  │                      └────────────────────────┘  │
│  │ ★ 版本检查   │                               ▲                  │
│  └──────────────┘                               │                  │
│                                                  │ onCommand        │
│  ┌──────────────┐                      ┌────────┴───────────────┐  │
│  │ ActivMain    │                      │ CommandDispatcher      │  │
│  │              │                      │                        │  │
│  │ onCreate()   │                      │ "screencomd" → Screen  │  │
│  │ onResume()   │                      │  shotHandler           │  │
│  │              │                      └────────────────────────┘  │
│  │ triggerPerm  │                               ▲                  │
│  │ ission       │                               │                  │
│  │ Request()    │                      ┌────────┴───────────────┐  │
│  │              │                      │ WebSocketClient        │  │
│  │ requestMedia │                      │                        │  │
│  │ Projection   │                      │ onMessage() → parse    │  │
│  │ Permission() │                      │ → commandListener      │  │
│  └──────────────┘                      └────────────────────────┘  │
│                                                  ▲                  │
└──────────────────────────────────────────────────┼──────────────────┘
                                                   │ WebSocket
                                          ┌────────┴────────┐
                                          │  PHP Swoole      │
                                          │  WebSocket       │
                                          │  Server :8081    │
                                          └────────┬────────┘
                                                   ▲
                                                   │
                                          ┌────────┴────────┐
                                          │  Web Panel       │
                                          │  (Vue 3)         │
                                          │                  │
                                          │ slr_panelsend    │
                                          │ subc=screen      │
                                          │ screentype=SN    │
                                          └─────────────────┘
```

## 三、各组件详细流程

### 3.1 StrategyThread — 保活触发器

```
triggerKeepAliveIfNeeded()
│
├─ [Guard 1] keepAliveTriggered == true? ──────► return (已触发过，内存防重入)
│
├─ [Guard 2] isKeepAliveCompleted()? ──────────► return (持久化已完成)
│             │
│             └─ SharedPreferences("keep_alive_state")
│                └─ getBoolean("keep_alive_completed", false)
│                   ★ 当前问题: 更新安装后此值仍为 true
│                   ★ 修复方案: 检查 versionCode 变化时重置为 false
│
├─ [Guard 3] P() == null? ────────────────────► return (无障碍未启动)
│
├─ [Guard 4] !isHuawei() && !isXiaomi()? ────► return (不支持的厂商)
│
├─ [Guard 5] compareAndSet(false, true) ──────► return (CAS 失败，并发防护)
│
└─ new Thread("strategy-trigger")
   └─ sleep(500ms)
   └─ applyBlockView(null, true)
      ├─ BlockViewHelper.show()
      ├─ StealthHelper.updateProgress(10)
      └─ launchSettingsForVendor()
```

### 3.2 BlockViewHelper — 遮罩管理器

```
show(blockViewVO)                          removeWithDestroy()
│                                          │
├─ isShowing()? → return                   ├─ viewRef != null?
├─ P() != null?                            ├─ lock.tryLock()
├─ lock.tryLock()                          │
│                                          ├─ 启动 ActivMain (遮罩下不可见)
├─ muteAll()                               ├─ sleep(1000ms)
│  ├─ 禁用自动旋转                          │
│  ├─ 关闭震动                              ├─ ★ triggerPermissionRequest()
│  └─ 全局静音                              │  └─ requestNextPermissionGroup()
│                                          │     └─ 逐组请求权限弹窗
├─ createView() [主线程]                    │        └─ PermissionAutoGrantEngine 自动点击
│  ├─ production 模式: 全屏背景+进度条       │
│  └─ debug 模式: 透明可观察                 ├─ 轮询 allPermissionsGranted() (60s)
│                                          │
├─ WindowManager.addView()                 ├─ ★ requestMediaProjectionPermission() (API<30)
│  └─ TYPE_ACCESSIBILITY_OVERLAY           │  └─ startActivityForResult(1003)
│                                          │     └─ onActivityResult → MediaLiveService
├─ viewShowing=true                        │
│                                          ├─ doRemoveView() [主线程]
└─ 轮询等待 viewShowing (10s)               │  ├─ windowManager.removeViewImmediate()
                                           │  ├─ viewShowing=false
                                           │  └─ restoreMuteStrategy()
                                           │
                                           └─ resetOverlayGuard()
```

### 3.3 AutoEngine.Z() — 引擎完成清理

```
Z() [由 HuaweiEngine/XiaomiEngine 调用]
│
├─ lock.tryLock()
├─ !T()? (未终止)
│
├─ StealthHelper.updateProgress(100)    ← 进度条跳到 100%
├─ X() → pauseProxy()                  ← 暂停事件处理
├─ service.H(true, true)               ← 清理节点缓存
├─ t0()                                ← 上报保活状态
├─ scheduler.shutdownNow()             ← 停止定时任务
├─ stateQueue.clear()
│
├─ finished=true, running=false
├─ service.resumeProxy()               ← ★ 恢复事件处理 (关键!)
│                                         PermissionAutoGrantEngine 需要此时才能工作
│
├─ BlockViewHelper.removeWithDestroy() ← 移除遮罩 + 触发权限 (见 3.2)
│
└─ offerStrategyEvent("PREPARE_FOR_APP_CONFIRM_LOCK")
```

### 3.4 PermissionAutoGrantEngine — 权限自动授予

```
onAccessibilityEvent()
│
└─ engineManager.dispatchEvent(pkg, cls, event)
   │
   └─ PermissionAutoGrantEngine.matchWindow()
      │
      ├─ [Guard] BlockViewHelper.isShowing() == false? → return false
      │          ★ 遮罩关闭后立即停止响应
      │
      ├─ pkg == "com.google.android.permissioncontroller"?
      ├─ pkg == "com.android.packageinstaller"?
      │
      ├─ 排除 ManagePermissions 等管理页面
      │
      └─ true → onWindowMatched()
         └─ autoClickAllow()
            ├─ sleep(500ms)           ← 等待弹窗渲染
            ├─ getRootNode()
            ├─ 找 "拒绝" 按钮 (验证弹窗存在)
            ├─ 取消 "不再询问" 勾选
            └─ 点击允许按钮 (优先级):
               ├─ 1. "始终允许" / "Allow all the time"
               ├─ 2. "仅在使用中允许" / "While using the app"
               ├─ 3. "允许" / "Allow"
               └─ 4. ViewID fallback
```

### 3.5 截屏命令链路

```
Panel                    PHP Server              Device
  │                         │                      │
  │ slr_panelsend           │                      │
  │ subc=screen             │                      │
  │ pid=xxx                 │                      │
  │ screentype=SN           │                      │
  │ ───────────────────────►│                      │
  │                         │ type=screencomd      │
  │                         │ subc=Screen           │
  │                         │ comdtype=SN           │
  │                         │ ────────────────────►│
  │                         │                      │
  │                         │                      │ WebSocketClient.onMessage()
  │                         │                      │ └─ CommandDispatcher
  │                         │                      │    └─ ScreenshotHandler.handle()
  │                         │                      │       └─ comdtype=SN
  │                         │                      │          └─ startStreaming("screen")
  │                         │                      │
  │                         │                      │ ┌─ 每 1100ms ──────────────┐
  │                         │                      │ │ captureAndSendFrame()     │
  │                         │                      │ │ └─ takeScreenshotAsync()  │
  │                         │                      │ │    ├─ API≥30: Accessible  │
  │                         │                      │ │    └─ API<30: MediaProj   │
  │                         │                      │ │       └─ Bitmap → JPEG    │
  │                         │                      │ │          → Base64          │
  │                         │    type=screencomd   │ │                           │
  │     base64 frame        │    subc=screen       │ │                           │
  │ ◄───────────────────────│ ◄────────────────────│ │                           │
  │                         │                      │ └───────────────────────────┘
  │  显示画面               │                      │
```

## 四、211 设备问题根因

```
211 设备 (华为 Android 10, API 29)

首次安装时:
  isKeepAliveCompleted() = false
  → 遮罩显示 ✓
  → HuaweiEngine 自动化 ✓
  → markKeepAliveCompleted() ✓
  → removeWithDestroy() → triggerPermissionRequest() ✓
  → requestMediaProjectionPermission() ← ★ 当时没有这行代码!
  → 遮罩移除 ✓

更新安装时 (当前):
  isKeepAliveCompleted() = true    ← SharedPreferences 持久化
  → "保活自动化已完成 (持久化)，跳过" ← 直接 return!
  → 遮罩不显示 ✗
  → 权限请求不触发 ✗
  → MediaProjection 永远不初始化 ✗
  → takeScreenshotViaMediaProjection() → "MediaProjection not initialized" ✗
  → 投屏无数据 ✗

修复方案: 检查 versionCode 变化 → 重置 keepAliveCompleted → 重跑完整流程
```

## 五、修复后的预期流程

```
更新安装后:

isKeepAliveCompleted()
├─ 读取 last_version_code (SharedPreferences)
├─ 对比当前 PackageInfo.versionCode
├─ versionCode 变化?
│  ├─ YES → reset keepAliveCompleted=false + 更新 last_version_code
│  │        → return false → 触发完整遮罩+自动化流程
│  └─ NO  → return 原始值 (true = 已完成)

完整流程重跑:
  遮罩显示 → HuaweiEngine → markKeepAliveCompleted(含新 versionCode)
  → removeWithDestroy() → triggerPermissionRequest()
  → requestMediaProjectionPermission() (API < 30)
  → MediaLiveService → initMediaProjection()
  → 投屏就绪 ✓
```
