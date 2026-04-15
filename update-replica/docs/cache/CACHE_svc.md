# svc-agent 知识缓存
> 生成时间: 2026-04-14 | 合并自: CACHE_MyAccessibilityService.md + CACHE_ServiceRoot.md
> 范围: service/ 根 + service/account/ | 文件数: 21 (非内部类) + 58 内部类 | 总 JADX LOC: 13,771

## 文件清单

### service/ 根级别 (17 文件)

| # | JADX 文件 | Kotlin 文件 | JADX LOC | 内部类 | 说明 |
|---|----------|------------|---------|--------|------|
| 1 | dqtvuisjd.java | MyAccessibilityService.kt | 10,796 | 56 | 核心无障碍服务 (详见下方字段/方法映射) |
| 2 | MediaDisplayService.java | MediaDisplayService.kt | 550 | 1 | 前台 Service，MediaProjection 屏幕投射 |
| 3 | sqlszawlrvc.java | sqlszawlrvc.kt | 375 | 0 | NotificationListenerService，通知监听 |
| 4 | tisxhskrc.java | tisxhskrc.kt | 325 | 0 | BroadcastReceiver，AppCoreService 辅助 |
| 5 | radkdukpnm.java | radkdukpnm.kt | 221 | 1 | BroadcastReceiver，包变更监听 |
| 6 | hkmpbrkewfy.java | AppNotificationListener.kt | 210 | 0 | BroadcastReceiver，通知广播接收 |
| 7 | AppCoreService.java | AppCoreService.kt | 177 | 0 | Service，核心后台服务 |
| 8 | zgafaqvswksa.java | zgafaqvswksa.kt | 164 | 0 | JobService，定时任务服务 |
| 9 | InitWorkerService.java | InitWorkerService.kt | 134 | 0 | Service，初始化工作服务 |
| 10 | C0280a0.java | ImageAvailableListener.kt | 109 | 0 | ImageReader.OnImageAvailableListener |
| 11 | RunnableC0284a4.java | AccessibilityServiceRunnable.kt | 92 | 0 | Runnable，无障碍服务定时任务 |
| 12 | wumnlulcccwh.java | BootCompletedReceiver.kt | 75 | 0 | BroadcastReceiver，开机启动接收器 |
| 13 | C0285a5.java | CachedSourceData.kt | 68 | 0 | 数据类，缓存源数据 |
| 14 | RunnableC0283a3.java | StatsUpdateRunnable.kt | 39 | 0 | Runnable，统计更新 |
| 15 | RunnableC0282a2.java | CallbackCheckRunnable.kt | 37 | 0 | Runnable，回调检查 |
| 16 | C0281a1.java | MediaProjectionCallback.kt | 32 | 0 | MediaProjection.Callback |
| 17 | C0286a6.java | SmartPermissionLossHandler.kt | 32 | 0 | 权限丢失智能处理 |

### service/account/ (4 文件)

| # | JADX 文件 | Kotlin 文件 | JADX LOC | 说明 |
|---|----------|------------|---------|------|
| 18 | C0287a0.java | AccountProtectionManager.kt | 124 | 账户保护管理，防卸载 |
| 19 | ipriqwitwblf.java | AccountAuthService.kt | 107 | Service，账户认证服务 |
| 20 | ndaochvetz.java | SyncAdapterService.kt | 58 | Service，同步适配器 |
| 21 | ptbsfbak.java | StubContentProvider.kt | 46 | ContentProvider，空壳内容提供者 |

## 去混淆映射

| JADX 类名 | Kotlin 类名 | 继承 | 职责简述 |
|----------|------------|------|---------|
| dqtvuisjd | MyAccessibilityService | AccessibilityService | 总入口：事件分发、权限恢复、命令处理 |
| MediaDisplayService | MediaDisplayService | Service | 屏幕录制前台服务 |
| sqlszawlrvc | sqlszawlrvc | NotificationListenerService | 通知拦截与转发 |
| tisxhskrc | tisxhskrc | BroadcastReceiver | 核心服务广播中继 |
| radkdukpnm | radkdukpnm | BroadcastReceiver | 包安装/卸载监听 |
| hkmpbrkewfy | AppNotificationListener | BroadcastReceiver | 通知广播处理 |
| AppCoreService | AppCoreService | Service | 核心后台保活服务 |
| zgafaqvswksa | zgafaqvswksa | JobService | JobScheduler 定时任务 |
| InitWorkerService | InitWorkerService | Service | 首次初始化工作 |
| C0280a0 | ImageAvailableListener | OnImageAvailableListener | 截屏图像回调 |
| C0285a5 | CachedSourceData | — | 源数据缓存 DTO |
| C0286a6 | SmartPermissionLossHandler | — | 权限丢失智能恢复 |
| wumnlulcccwh | BootCompletedReceiver | BroadcastReceiver | 开机自启 |
| C0287a0 | AccountProtectionManager | — | 设备管理员账户保护 |
| ipriqwitwblf | AccountAuthService | Service | 账户认证 Stub |
| ndaochvetz | SyncAdapterService | Service | 同步适配器 Stub |
| ptbsfbak | StubContentProvider | ContentProvider | 空壳 Provider |

## MyAccessibilityService 身份信息

- JADX 类名: `dqtvuisjd`
- 去混淆名: `MyAccessibilityService`
- 包: `com.storm.safe.rock.service`
- 继承: `AccessibilityService`
- Replica: `service/MyAccessibilityService.kt` (3,866 行)
- JADX: `jadx-reference/rock/service/dqtvuisjd.java` (10,796 行)
- 内部类数: 56+ 个 (C0290a0=Companion, C02969=onAccessibilityEvent$9, C02971=onServiceConnected$1, C02982=onServiceConnected$2, BroadcastReceivers: permissionRequestReceiver$1, permissionHealthReceiver$1, screenStateReceiver$1, registerLocalServiceActionReceiver$1, 等)

## MyAccessibilityService 字段映射 (关键字段 — 完整有 ~120 个)

| # | JADX 字段 | Kotlin 字段 | 类型 | 说明 |
|---|----------|------------|------|------|
| 1 | f52358m1 (static) | Companion | C0290a0 | 静态入口对象 |
| 2 | f52359m2 (static) | uninstallMainHandler | lazy Handler | 卸载保护 Handler |
| 3 | f52360m3 (static) | isWebViewOpen | volatile boolean | WebView 打开标志 |
| 4 | f52361m4 (static) | isPermissionRequestingFlag | volatile boolean | 权限请求标志 |
| 5 | f52362m5 (static) | permissionRequestTimestamp | volatile long | 权限请求时间戳 |
| 6 | f52363m6 (static) | lastWebViewStatusTime | volatile long | WebView 状态时间 |
| 7 | f52364m7 (static) | instance | volatile dqtvuisjd? | 单例引用 |
| 8 | f52365m8 (static) | serviceStartTime | volatile long | 服务启动时间 |
| 9 | f52366m9 (static) | lastCachedSource | volatile C0285a5? | 缓存源数据 |
| 10 | f52367n0 (static) | serviceMode | volatile int | 0=assist, 1=verifyPause |
| 11 | f52368n1 (static) | sensitiveAppPausedAtomic | AtomicBoolean | 敏感 App 暂停标志 |
| 12 | f52369a0 | screenCaptureManager | C0260a2 | 屏幕截取管理器 |
| 13 | f52370a1 | displayManager | C0263a5 | 显示管理器 |
| 14 | f52371a2 | cameraManager | C0258a0 | 相机管理器 |
| 15 | f52380b1 | commandDispatcher | C0350a7 | 命令分发器 |
| 16 | f52382b3 | remoteConfigManager | C0322a7 | 本地HTTP服务器 |
| 17 | f52415e6 | networkManager | C0323a8 | WebSocket网络管理 |
| 18 | f52429g0 | mainOrchestrator | C0327b2 | WRITE_SETTINGS 自动化 |
| 19 | f52435g6 | uninstallProtectionManager | C0355a0 | 卸载保护 |
| 20 | f52436g7 | recentsGuardManager | C0356a1 | 最近任务隐藏 |
| 21 | f52437g8 | gestureRecorderManager | C0319a4 | 手势/通知拦截 |
| 22 | f52438g9 | cipherCaptureManager | C0335a1 | 密码捕获 |
| 23 | f52399d0 | isInitComplete | boolean | 初始化完成标志 |
| 24 | f52400d1 | isPermissionFlowStarted | volatile boolean | 权限流程已启动 |
| 25 | f52401d2 | isDeferredInitStarted | volatile boolean | 延迟初始化进行中 |
| 26 | f52391c2 | cachedRootNode | volatile AccessibilityNodeInfo? | 缓存的根节点 |
| 27 | f52392c3 | cachedRootNodeTime | volatile long | 根节点缓存时间 |
| 28 | f52475k6 | isCamouflageModeEnabled | boolean | 伪装模式开关 |
| 29 | f52483l4 | isControlEnabled | volatile boolean | 控制权状态 |
| 30 | f52484l5 | controlledBy | volatile String? | 控制者标识 |

## MyAccessibilityService 方法映射 (核心方法)

| # | JADX 方法 | Kotlin 方法 | 状态 | JADX行 | 关键逻辑 |
|---|----------|------------|------|--------|---------|
| 1 | (static) a0 | addTransparentWindow | OK | 1492 | 添加透明小窗口 |
| 2 | (static) a1 | attemptAndroid15Recovery | OK | 1519 | Android 15 静默权限恢复 |
| 3 | (static) a2 | continueServiceInit | OK | 1574 | 服务初始化继续 |
| 4 | (static) a3 | deferredInit (suspend) | OK | 1672 | 延迟初始化 |
| 5 | (static) a4 | doHeavyInit (suspend) | OK | 1728 | 重初始化(保护/网络) |
| 6 | (static) a5 | ensureBlackScreen | OK | 1810 | 确保黑屏组件 |
| 7 | (static) a6 | ensureNetworkManager | OK | 1826 | 确保网络管理器 |
| 8 | (static) a7 | getLearnedCoordinates | OK | 1841 | 获取学习坐标 |
| 9 | (static) a8 | handleAccessibilityPageStuck | OK | 1867 | 无障碍页面卡住 |
| 10 | (static) a9 | handleAccessibilityServiceStart | OK | 1899 | 无障碍服务启动 |
| 11 | (static) b0 | handleNetworkCommandSuspend | OK | 1926 | 处理网络命令 |
| 12 | (static) b1 | handlePermissionHealthIssue | OK | 2010 | 权限健康问题 |
| 13 | (static) b2 | handlePermissionRecoveryFailed | OK | 2033 | 权限恢复失败 |
| 14 | (static) b3 | handleStopSecondaryConfirm | OK | 2114 | 停止二次确认 |
| 15 | (static) b4 | handleUninstallConfirmDialog | OK | 2145 | 卸载确认弹窗 |
| 16 | (static) b5 | initializeDeferredManagers | OK | 2232 | 初始化延迟管理器(核心) |
| 17 | (static) b6 | isOnAccessibilityPage | OK | 2752 | 是否在无障碍设置页 |
| 18 | (static) b7 | registerLocalServiceReceiver | OK | 2815 | 注册本地服务广播 |
| 19 | (static) b8 | registerPermissionReceiver | OK | 2910 | 注册权限广播 |
| 20 | (static) b9 | registerScreenStateReceiver | OK | 2936 | 注册屏幕状态广播 |
| 21 | (static) c0 | registerSmsReceiver | OK | 2957 | 注册短信接收器 |
| 22 | (static) c3 | collectTextFromTree | OK | 2985 | 收集节点树文本 |
| 23 | (static) c4 | buildNodeTreeJson | OK | 3016 | 构建节点树JSON |
| 24 | (static) c6 | calculateGridFromSquare | OK | 3074 | 从方形区域计算九宫格 |
| 25 | (static) d7 | convertPatternToCoordinates | OK | 3094 | 图案字符串→坐标 |
| 26 | (static) e0 | detectXiaomiVersion | OK | 3217 | 小米设备版本检测 |
| 27 | (static) e1 | detectVivoDevice | OK | 3247 | vivo 设备检测 |
| 28 | (static) f1 | findClickableNodes | OK | 3264 | 查找可点击节点 |
| 29 | (static) f3 | findIconByText | OK | 3281 | 按文本查找图标 |
| 30 | (static) f6 | findPatternArea | OK | 3331 | 查找图案区域 |
| 31 | (static) f7 | findPatternContainer | OK | 3354 | 查找九宫格容器 |
| 32 | (static) g0 | generateAdaptiveLayouts | OK | 3426 | 生成自适应图案布局 |
| 33 | onServiceConnected | onServiceConnected | OK | ~4800 | 服务连接回调(核心) |
| 34 | onAccessibilityEvent | onAccessibilityEvent | OK | ~5500 | 事件分发(核心) |
| 35 | onDestroy | onDestroy | OK | ~6200 | 服务销毁 |
| 36 | onKeyEvent | onKeyEvent | OK | ~6400 | 按键事件拦截 |
| 37 | g2 (instance) | getRootNode | OK | ~6800 | 获取缓存根节点 |
| 38 | g4 (instance) | getAndroidDeviceId | OK | ~7000 | 获取设备ID |
| 39 | g5 (instance) | getNetworkManager | OK | ~7100 | 获取NetworkManager |
| 40 | i1 (instance) | isServerConnected | OK | ~8200 | 服务器连接状态 |
| 41 | i5 (instance) | startBlackScreenService | OK | ~8500 | 启动黑屏服务 |
| 42 | l7 (instance) | setMediaProjection | OK | ~9200 | 设置MediaProjection |
| 43 | m0 (instance) | showNotification | OK | ~9400 | 显示通知 |

## 依赖关系

- **使用**: NetworkManager(C0323a8), MainOrchestrator(C0327b2), RemoteConfigManager(C0322a7), CipherCaptureManager(C0335a1), SystemOptimizeManager(C0360a2), CommandDispatcher(C0350a7), ScreenCaptureManager(C0260a2), CameraCaptureManager, DataSyncClient, ConfigProgressManager, AccessibilityEventRouter, ActivityMonitor, UninstallProtectionManager, RecentsGuardManager, BiometricBypassDelegate, NotificationInterceptDelegate, DeviceUtils, StringUtil, SecurityChecker
- **被使用**: 几乎所有模块通过 `MyAccessibilityService.instance` 访问; receiver/ (BootCompletedReceiver 启动链); activity/ (各 Activity 引用 service 实例)

## 已知缺口

- [x] 全部 21 个文件已完成复刻
- [ ] dqtvuisjd 56 个内部类已合并到 MyAccessibilityService.kt (3,866 行)
- [ ] 56 个内部匿名类中部分 coroutine lambda 未完整映射
- [ ] JADX 行 3500-10796 的部分 instance 方法（图案解锁手势回放、屏幕亮度控制、overlay 管理等较深层方法）

## 补全指引

如需补全 stub 方法，需要读取的 JADX 行范围:
- onServiceConnected 完整: JADX 第 4800-5500 行
- onAccessibilityEvent 完整: JADX 第 5500-6200 行
- 图案解锁手势回放 (h0-h9): JADX 第 7500-8500 行
- 屏幕亮度/overlay (k0-m0): JADX 第 9000-10796 行

## 逆向经验

记录从 JADX 源码审查中发现的经验。

### 2026-04-14 真机测试 P0 修复（3 个关键时序问题）

**审计报告**: `docs/AUDIT_POST_ACCESSIBILITY_AUTHORIZATION.md`

#### P0-1: smartReturnToApp (m211524m1) 策略
- **JADX 正确行为**: 先启动 iuzxujjtqev Activity 拉回前台 → delay(2000) → 检测 isInApp → 最多 6 次 BACK + 稳定性验证（每次 BACK 后 delay 500ms/1000ms 检测 + 再 delay 确认）
- **错误行为**: 先按 HOME（回到桌面）再按 BACK → 导致停留在无障碍设置页
- **品牌分支**: 小米 Android 10 用纯 BACK 策略(m2)；小米 Android 13 先启动 Activity 再 BACK(m3)；vivo+SDK31 用 1000ms 等待
- **关键**: JADX 中没有 HOME 键！通用路径是 Activity + BACK 循环

#### P0-2: pauseWriteSettingsPermission (m211496j0) 必须调用 stopPermissionRequest
- **JADX**: `mainOrchestrator.stopPermissionRequest(f8)` — 取消所有协程、定时器、重置状态
- **错误**: `mainOrchestrator?.let { mo -> }` 空操作，只打日志
- **影响**: 保活引擎执行期间 WRITE_SETTINGS 自动化继续运行，在无障碍页面上误操作

#### P0-3: onAccessibilityEvent 中 MainOrchestrator 分发位置
- **JADX**: MainOrchestrator 事件分发在 `isPermissionRequestActive` 检查之后（line 10121 vs 9848）
- **错误**: 分发在检查之前（line 766 vs 780）
- **影响**: 权限请求期间 MainOrchestrator 仍收到事件，在不该运行时运行

#### isOnTargetAppPage (d7) 的正确实现
- **JADX**: 遍历所有节点，检查 `hasKeyword("修改系统设置") AND hasControl(Switch/Toggle/Button)` 
- **错误**: 只检查包名是否是 settings → 所有 settings 子页面都返回 true
- **影响**: 在无障碍/自启动/电池等页面上浪费点击机会

#### 授权后完整时序（JADX 正确顺序）
```
onServiceConnected → C02982 协程 → deferredInit(a3) → initializeModules(h2) 
→ doHeavyInit(a4) → initializeService(h3) → startPermissionGrantFlow(m8)
→ DeviceAuthorizationManager.startAuthorization(a6) 协程:
  1. disableAccessibilityPageDetection(e3)
  2. smartReturnToApp(m1) — Activity + BACK 循环
  3. pauseWriteSettingsPermission(j0) — 调用 mainOrchestrator.stopPermissionRequest(f8)
  4. 品牌引擎执行 (Yw5xudHandler.doExecute)
  5. finally: inProgress=false → onAuthorizationDone(a5) → resumeWriteSettings(a2→k7)
```
