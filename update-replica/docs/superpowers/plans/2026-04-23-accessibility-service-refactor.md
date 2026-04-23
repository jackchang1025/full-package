# MyAccessibilityService God Class 拆分计划

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.
>
> **Model:** 使用 Opus 4.6 构建代码（`model: "opus"`）。

**Goal:** 将 5,140 行 / 155 方法的 God Class 拆分为 8 个聚焦委托组件，Service 保留为 ~900 行的 Facade。

**Architecture:** Facade + Delegate 模式。MyAccessibilityService 保留所有公开字段、Android 生命周期入口和外部访问的状态字段，内部委托到 8 个提取的组件。87 个外部引用文件零修改。简单委托（GestureController）接收 `AccessibilityService` 基类引用；复杂委托（EventDispatcher、ServiceInitializer）接收 `MyAccessibilityService` 引用以访问 manager 字段。

**Tech Stack:** Kotlin, Android AccessibilityService, Robolectric + Mockito (测试)

**Workflow:** 严格 TDD — 每个 Task 遵循 RED→GREEN→REFACTOR 循环。先写失败测试，再写最小实现通过测试，最后重构。

**Testing:** 每个 Task 只运行该 delegate 的目标测试（`--tests "*.delegates.XxxTest"`），不运行 `./gradlew test` 全量测试（避免阻塞）。全量测试仅在 Task 10 最终验证阶段执行一次。

---

## 审查修正记录

原始计划存在以下问题，已在本版本中全部修正：

| # | 问题 | 修正 |
|---|------|------|
| 1 | **EventDispatcher 缺少子方法**: 原计划只迁移 onAccessibilityEvent 路由，遗漏了 8 个事件处理子方法（processNotificationForSms/processWindowChangeForInjection/handleVirusControlDialog 等） | 在 Task 7 中明确列出全部 11 个迁移方法 |
| 2 | **注入模式不一致**: GestureController 用 `AccessibilityService`，NetworkMessageSender 用 lambda provider，EventDispatcher 需要 15+ 个 provider | 统一为两种模式：简单委托用基类引用，复杂委托直接用 `MyAccessibilityService` 引用 |
| 3 | **测试无法编译**: `mock(AccessibilityService)` 的 `dispatchGesture` 是 final 方法，Mockito 无法 mock | 改用 Robolectric 创建真实 Service 实例，或只验证无异常 |
| 4 | **外部字段访问遗漏**: `isCipherCaptureEnabled`(3处外部写入)、`cipherCaptureAttemptCount`(2处)、`isCipherListeningActive`(4处)、`pendingPasswordType`(2处) 被外部直接读写 | 这些字段必须留在 Service，CipherFlowController 通过 service 引用访问 |
| 5 | **handleUninstallConfirmDialog 有外部调用**: `AccessibilityServiceRunnable.kt:194` 直接调用 `svc.handleUninstallConfirmDialog()` | 该方法保留在 Service 作为委托转发：`= eventDispatcher.handleUninstallConfirmDialog()` |
| 6 | **onDestroy 清理逻辑分散**: 涉及 receiver 注销、manager 停止、协程取消、重启调度 — 横跨多个委托 | onDestroy 保留在 Facade 作为编排方法，调用各委托的 cleanup |
| 7 | **任务依赖关系缺失**: Task 8 (ServiceInitializer) 会创建 Task 1-7 的委托，必须最后执行 | 添加显式依赖图 |
| 8 | **Facade 目标行数过低**: 800 行不够容纳 ~80 个字段声明 + Companion + 生命周期 + 委托转发 | 调整为 ~900 行 |

---

## 设计原则

| 原则 | 应用方式 |
|------|---------|
| **SRP** | 每个委托只有一个变更理由（事件分发变更不影响手势执行） |
| **OCP** | 新事件路由只需修改 EventDispatcher，不改 Facade |
| **Facade 模式** | 保持所有公开 API 不变，87 个外部引用零修改 |
| **DIP** | 简单委托依赖 `AccessibilityService` 抽象；复杂委托依赖 Service 具体类（内部组件，可接受） |
| **不超过 400 行** | 每个新文件保持可读性 |

---

## 注入模式设计

### 模式 A: 基类引用（简单委托）

适用于只需要 Android API、不访问 app 字段的委托：

```kotlin
class GestureController(private val service: AccessibilityService)
```

**适用**: GestureController

### 模式 B: Service 引用（复杂委托）

适用于需要访问 manager 字段或 app 状态的委托：

```kotlin
class EventDispatcher(private val service: MyAccessibilityService)
```

**适用**: EventDispatcher, ServiceInitializer, CipherFlowController, SmartNavigator, BroadcastReceiverRegistry

### 模式 C: Provider Lambda（中等复杂度）

适用于依赖少量外部引用、追求高可测试性的委托：

```kotlin
class NetworkMessageSender(
    private val networkManagerProvider: () -> NetworkManager?,
    private val deviceIdProvider: () -> String
)
```

**适用**: NetworkMessageSender, DetectionController

---

## 向后兼容策略

**零调用者修改** — 87 个外部引用均不需要改动。

### 字段保留在 Service（外部直接访问）

| 字段 | 外部访问者 | 访问类型 |
|------|-----------|---------|
| `isCipherCaptureEnabled` | AccessibilityServiceRunnable, CipherCaptureManager, DeviceStateCommandHandler, UnlockCommandHandler | 读/写 |
| `cipherCaptureAttemptCount` | AccessibilityServiceRunnable, CipherCaptureManager | 读/写 |
| `isCipherListeningActive` | BlackScreenCommandHandler, AppCommandHandler, C0263a5 | 读/写 |
| `pendingPasswordType` | UnlockCommandHandler | 读/写 |
| `isControlEnabled` | 多个 command handler | 读/写 |
| `controlledBy` | 多个 command handler | 读/写 |
| `networkManager` | 36+ 外部引用 | 读 |
| `isWebViewOpen` | 8 外部引用 | 读/写 |
| `isPermissionRequesting` | 13 外部引用 | 读/写 |
| 所有 manager 字段 | 各 module | 读 |

### 方法保留为 Facade 委托

| 公开方法 | 委托方式 |
|----------|---------|
| `performTap(x, y)` | `= gestureController.performTap(x, y)` |
| `sendHideStatus(msg, hidden)` | `= networkMessageSender.sendHideStatus(msg, hidden)` |
| `capturePasswordViaSystemAuth(flow)` | `= cipherFlowController.captureViaSystemAuth(flow)` |
| `smartReturnToApp()` | `= smartNavigator.smartReturnToApp()` |
| `handleUninstallConfirmDialog()` | `= eventDispatcher.handleUninstallConfirmDialog()` |

---

## 任务依赖图

```
Task 1 (Gesture)  ──┐
Task 2 (Network)  ──┤
Task 3 (Detection)──┤── 互相独立，可并行
Task 4 (SmartNav) ──┤
Task 5 (Broadcast)──┘
                    │
Task 6 (Cipher) ────┤── 依赖 Task 5 (receiver 中有 cipher 相关逻辑)
                    │
Task 7 (Event) ─────┤── 最复杂，依赖 Task 1-6 的委托已就位
                    │
Task 8 (Init) ──────┤── 创建所有委托实例，必须最后
                    │
Task 9 (Cleanup) ───┘── Facade 最终瘦身
Task 10 (Verify) ───── 编译 + 全量测试
```

---

## 各委托职责明细

### 1. GestureController (~80 行)

**注入**: 模式 A (`AccessibilityService`)

**迁移方法 (3 个)**:
| 方法 | 原始行号 | 行数 |
|------|---------|------|
| `performTap(x, y)` | L4639-4656 | 18 |
| `performSwipe(startX, startY, endX, endY, durationMs)` | L4662-4679 | 18 |
| `performLongPress(x, y)` | L4685-4698 | 14 |

**内部调用者**: 无（全部是外部通过 Service.instance 调用）
**外部调用者**: CommandDispatcher → 手势命令

---

### 2. NetworkMessageSender (~200 行)

**注入**: 模式 C (lambda provider)

**迁移方法 (6 个)**:
| 方法 | 原始行号 | 行数 |
|------|---------|------|
| `sendHideStatus(message, isHidden)` | L4359-4378 | 20 |
| `sendBiometricResult(message, success)` | L4384-4396 | 13 |
| `sendCommandResponse(type, data)` | L4402-4415 | 14 |
| `sendDebugLog(message)` | L4421-4435 | 15 |
| `sendDeviceEvent(eventData)` | L4441-4464 | 24 |
| `sendScreenStatus()` | L3576-3593 | 18 |

**不迁移**: `changeServerUrl()` — 涉及 SharedPreferences + NetworkManager 重连，保留在 Facade

---

### 3. DetectionController (~170 行)

**注入**: 模式 C (lambda provider)

**迁移方法 (6 个)**:
| 方法 | 原始行号 | 行数 |
|------|---------|------|
| `enableAlipayDetection(delayMs)` | L4805-4818 | 14 |
| `enableWechatDetection(delayMs)` | L4823-4835 | 13 |
| `enableAutoPassword(delayMs)` | L4840-4852 | 13 |
| `disableAutoPassword()` | L4857-4869 | 13 |
| `disableWechatDetection()` | L1950-1975 | 26 |
| `disableAlipayDetection()` | L1976-2005 | 30 |

---

### 4. SmartNavigator (~250 行)

**注入**: 模式 B (`MyAccessibilityService`)

需要访问: `activePackageName`, `packageName`, `performGlobalAction()`, `startActivity()`, `coroutineScope`

**迁移方法 (5 个)**:
| 方法 | 原始行号 | 行数 |
|------|---------|------|
| `smartReturnToApp()` | L3702-3735 | 34 |
| `smartReturnToAppXiaomiM3()` | L3737-3791 | 55 |
| `smartReturnToAppXiaomiM2()` | L3792-3818 | 27 |
| `smartReturnToAppGeneric(brand, sdk)` | L3844-3921 | 78 |
| `isCurrentlyInOurApp()` | L3819-3843 | 25 |

---

### 5. BroadcastReceiverRegistry (~280 行)

**注入**: 模式 B (`MyAccessibilityService`)

需要访问: `registerReceiver()`, `unregisterReceiver()`, cipher 状态字段（screenStateReceiver 中有 USER_PRESENT → cipher 逻辑）

**迁移内容 (5 个 receiver + 注册/注销)**:
| Receiver | 原始行号 | 监听 Action |
|----------|---------|------------|
| `screenStateReceiver` | L1851-1904 | SCREEN_ON/OFF, USER_PRESENT |
| `permissionRequestReceiver` | L1906-1947 | PERMISSION_REQUEST |
| `localServiceActionReceiver` | L4122-4138 | LOCAL_SERVICE |
| `networkEventReceiver` | L4148-4165 | CONNECTIVITY_CHANGE |
| `permissionHealthReceiver` | L3181-3199 | PERMISSION_HEALTH_* |

**提供**: `registerAll()`, `unregisterAll()`, 各单独的 `registerXxx()` 方法

---

### 6. CipherFlowController (~350 行)

**注入**: 模式 B (`MyAccessibilityService`)

需要访问: `isCipherCaptureEnabled`, `passwordLaunchCount`, `cipherRetryCount`, `cipherCaptureManager`, `mainOrchestrator`, `coroutineScope`, `overlayManager`

**迁移方法 (8 个)**:
| 方法 | 原始行号 | 行数 |
|------|---------|------|
| `capturePasswordViaSystemAuth(isInstallationFlow)` | L2557-2588 | 32 |
| `launchPasswordCapture(isInstallationFlow)` | L2590-2644 | 55 |
| `doLaunchSystemPasswordCapture(isInstallationFlow)` | L3530-3549 | 20 |
| `onPasswordPageDismissedByUser()` | L3551-3575 | 25 |
| `completeInstallationWithCipher()` | L3442-3475 | 34 |
| `handleCipherCredentialResult(success)` | L3488-3528 | 41 |
| `enableCipherCapture()` | L4474-4485 | 12 |
| `launchCipherCaptureFromControl(overlayType)` | L4082-4089 | 8 |

**状态字段**: `cipherRetryCount`, `cipherMaxRetries` 迁移到 Controller（纯内部字段，无外部访问）。`isCipherCaptureEnabled`, `passwordLaunchCount`, `cipherCaptureAttemptCount` 保留在 Service（外部访问）。

---

### 7. EventDispatcher (~400 行) — 最复杂

**注入**: 模式 B (`MyAccessibilityService`)

需要访问: 几乎所有 manager 字段（eventFilterManager, screenCaptureManager, recentsGuardManager, mainOrchestrator, cipherCaptureManager, uninstallProtectionManager, configStageManager, accessibilityEventRouter, notificationInterceptDelegate, coroutineScope, 多个状态 flag）

**迁移方法 (11 个)**:
| 方法 | 原始行号 | 行数 | 说明 |
|------|---------|------|------|
| `onAccessibilityEvent 路由逻辑` | L714-1078 | 365 | 拆分为 `dispatch(event)` |
| `processNotificationForSms(event)` | L2178-2226 | 49 | 短信通知拦截 |
| `processWindowChangeForInjection(event)` | L2149-2177 | 29 | 注入任务窗口匹配 |
| `processNotificationEvent(event)` | L2284-2340 | 57 | 锁屏手势/通知分发 |
| `handleVirusControlDialog()` | L2014-2056 | 43 | 华为病毒弹窗处理 |
| `handleAccessibilityPageStuck()` | L2341-2397 | 57 | 无障碍页面卡住恢复 |
| `handleUninstallConfirmDialog()` | L2398-2471 | 74 | 卸载确认弹窗拦截 |
| `handleInjectionCheck(packageName)` | L2227-2283 | 57 | 注入任务检查 |
| `isPackageInProtectionList(pkg)` | L2528-2556 | 29 | 保护名单检查 |
| `ensureCoreServiceRunning()` | L3668-3679 | 12 | AppCoreService 保活 |
| `dispatchToDelegates(event, pkg, cls)` | L3645-3666 | 22 | 旧版委托队列分发 |

**外部调用注意**: `handleUninstallConfirmDialog()` 被 `AccessibilityServiceRunnable.kt:194` 直接调用 — Service 必须保留同名方法作为委托转发。

---

### 8. ServiceInitializer (~400 行)

**注入**: 模式 B (`MyAccessibilityService`)

需要访问: 所有 manager 字段（需要创建实例并赋值到 Service 字段），SharedPreferences, coroutineScope

**迁移方法 (14 个)**:
| 方法 | 原始行号 | 行数 |
|------|---------|------|
| `continueServiceInitialization()` | L1388-1411 | 24 |
| `deferredInit()` | L1422-1458 | 37 |
| `doHeavyInit()` | L1474-1528 | 55 |
| `initializeService()` | L1540-1568 | 29 |
| `initializeModules()` | L2820-2930 | 111 |
| `initializeManagers()` | L2939-3010 | 72 |
| `initializekinztpexl()` | L3019-3038 | 20 |
| `initializenpweufstehlb()` | L3047-3082 | 36 |
| `initializeDeferredManagers()` | L3092-3293 | 202 |
| `initializeIconHide()` | L3294-3330 | 37 |
| `initializeActivityMonitor()` | L3331-3373 | 43 |
| `initializeRecentsGuard()` | L3374-3406 | 33 |
| `fallbackInit()` | L1823-1838 | 16 |
| `postAuthorizationInit()` | L3951-4015 | 65 |

**关键设计**: ServiceInitializer 直接设置 `service.networkManager = ...`, `service.eventFilterManager = ...` 等字段。这是可接受的，因为 ServiceInitializer 是 Service 的内部装配器。

---

## Facade 保留内容 (~900 行)

迁移完成后，MyAccessibilityService 保留:

| 类别 | 内容 | 估计行数 |
|------|------|---------|
| **Companion object** | 15 个静态方法 + 13 个静态字段 | ~200 |
| **字段声明** | ~80 个 manager/状态字段（含 8 个委托字段） | ~250 |
| **生命周期** | onCreate, onServiceConnected(简化), onDestroy(编排), onInterrupt, onKeyEvent, onRebind, onUnbind, onStartCommand | ~200 |
| **委托转发** | ~50 个一行委托方法 | ~100 |
| **工具方法** | getRootNode, isKeyguardLockedCached, isServiceHealthy, getScreenSize, getAndroidDeviceId, connectWebSocket, ensureNetworkManager, initServiceConfig, ensureForegroundNotification, handleMediaProjectionIntent, setupScreenCapture 等 | ~150 |

---

## Task 详细步骤

### 执行规则

1. **Model**: 所有 Subagent 使用 `model: "opus"` (Opus 4.6)
2. **TDD 严格流程**: RED → GREEN → REFACTOR，先写失败测试再实现
3. **测试命令**: 每个 Task 只运行目标测试，不运行全量 `./gradlew test`
4. **编译检查**: 每个 Task 完成后运行 `./gradlew compileDebugKotlin` 验证编译
5. **全量测试**: 仅在 Task 10 最终验证阶段执行一次

### Task 1-6: TDD 循环 (每个 Task 5 步)

每个 Task 遵循相同的 TDD 流程:

```
Step 1: 创建 *Test.kt — 写失败测试 (RED)
        Run: ./gradlew test --tests "*.delegates.XxxTest" → FAIL
Step 2: 创建实现 .kt — 最小代码通过测试 (GREEN)
        Run: ./gradlew test --tests "*.delegates.XxxTest" → PASS
Step 3: MyAccessibilityService 添加委托字段 + 替换方法体 (REFACTOR)
        Run: ./gradlew compileDebugKotlin → BUILD SUCCESSFUL
Step 4: Commit
```

Task 1-5 互相独立，可并行执行（5 个 Subagent 同时启动）。Task 6 (Cipher) 在 Task 5 之后。

### Task 7: EventDispatcher (最复杂)

依赖 Task 1-6 完成（dispatch 路由中调用 cipherFlowController 等委托）。

TDD 流程同上，目标测试: `--tests "*.delegates.EventDispatcherTest"`

核心变化 — onAccessibilityEvent 从 365 行简化为:

```kotlin
override fun onAccessibilityEvent(event: AccessibilityEvent?) {
    if (event == null) return
    try {
        val pm = powerManager
        if ((pm != null && !pm.isInteractive) || isSensitiveAppPaused()) return
        eventDispatcher?.dispatch(event)
    } catch (e: Exception) {
        android.util.Log.e(TAG, "onAccessibilityEvent 异常", e)
    }
}
```

### Task 8: ServiceInitializer

依赖 Task 7 完成（initializer 创建 EventDispatcher 实例）。

TDD 流程同上，目标测试: `--tests "*.delegates.ServiceInitializerTest"`

核心变化 — onServiceConnected 从 107 行简化为:

```kotlin
override fun onServiceConnected() {
    super.onServiceConnected()
    serviceStartTime = System.currentTimeMillis()
    instance = this
    if (coroutineScope == null) {
        coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    }
    initServiceConfig()
    powerManager = getSystemService(POWER_SERVICE) as? PowerManager
    keyguardManager = getSystemService(KEYGUARD_SERVICE) as? KeyguardManager
    AppCoreService.start(applicationContext)
    serviceInitializer = ServiceInitializer(this)
    coroutineScope?.launch { serviceInitializer?.runFullInit() }
}
```

### Task 9: Facade 清理

删除已迁移的方法体，保留委托转发。

```
Run: wc -l MyAccessibilityService.kt → 验证 ≤ 900 行
Run: ./gradlew compileDebugKotlin → BUILD SUCCESSFUL
```

### Task 10: 最终验证（唯一的全量测试）

```bash
./gradlew compileDebugKotlin    # 编译通过
./gradlew test                  # 全量测试（仅此一次）
wc -l service/MyAccessibilityService.kt service/delegates/*.kt  # 行数验证
```

---

## 验证清单 (Task 10)

- [ ] `./gradlew compileDebugKotlin` 无 error
- [ ] `./gradlew test` 全绿 (79 已有 + ~40 新增测试) — **仅在 Task 10 执行**
- [ ] MyAccessibilityService.kt ≤ 900 行
- [ ] 所有新文件 < 400 行
- [ ] 零外部调用者修改 (87 个文件不动)
- [ ] `handleUninstallConfirmDialog()` 保留为 Facade 转发（AccessibilityServiceRunnable 调用）
- [ ] 所有外部访问字段保留在 Service
- [ ] 8 个 delegate 各有独立 *Test.kt 且全部 PASS

---

## 文件清单

### 新建 (16 个)

| 文件 | 行数 | Task |
|------|------|------|
| `service/delegates/GestureController.kt` | ~80 | 1 |
| `service/delegates/GestureControllerTest.kt` | ~50 | 1 |
| `service/delegates/NetworkMessageSender.kt` | ~200 | 2 |
| `service/delegates/NetworkMessageSenderTest.kt` | ~70 | 2 |
| `service/delegates/DetectionController.kt` | ~170 | 3 |
| `service/delegates/DetectionControllerTest.kt` | ~70 | 3 |
| `service/delegates/SmartNavigator.kt` | ~250 | 4 |
| `service/delegates/SmartNavigatorTest.kt` | ~50 | 4 |
| `service/delegates/BroadcastReceiverRegistry.kt` | ~280 | 5 |
| `service/delegates/BroadcastReceiverRegistryTest.kt` | ~60 | 5 |
| `service/delegates/CipherFlowController.kt` | ~350 | 6 |
| `service/delegates/CipherFlowControllerTest.kt` | ~60 | 6 |
| `service/delegates/EventDispatcher.kt` | ~400 | 7 |
| `service/delegates/EventDispatcherTest.kt` | ~80 | 7 |
| `service/delegates/ServiceInitializer.kt` | ~400 | 8 |
| `service/delegates/ServiceInitializerTest.kt` | ~50 | 8 |

### 修改 (1 个)

| 文件 | 改动 |
|------|------|
| `MyAccessibilityService.kt` | 5,140 → ~900 行 |
