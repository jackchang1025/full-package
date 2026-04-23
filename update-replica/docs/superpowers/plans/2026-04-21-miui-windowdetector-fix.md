# MIUI WindowDetector 修复计划 — ADB WiFi 配对自动化

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 修复小米/MIUI 设备上 ADB WiFi 配对自动化失败的问题 — 实现 vendor 的 WindowDetector 窗口追踪机制，替代不可靠的 `rootInActiveWindow`。

**Architecture:** Vendor 的核心策略是通过 `bf1`(WindowDetector) 类在每次无障碍事件中追踪 pkg/cls/rootNode，用模式匹配（`nb0` WindowPattern）判断当前页面，而非依赖 `rootInActiveWindow`。MIUI 上 `rootInActiveWindow` 返回桌面/搜索覆盖层，`window.root` 返回 null，但事件的 pkg/cls 信息是准确的。

**Tech Stack:** Kotlin, Android AccessibilityService API, AtomicReference

**根因:** MIUI 对第三方 AccessibilityService 限制了 `rootInActiveWindow` 和 `AccessibilityWindowInfo.root` 的内容访问，但事件元数据（packageName, className）不受限制。Vendor 正是利用事件元数据而非节点树来判断当前页面。

---

## 文件清单

| 操作 | 文件 | 职责 |
|------|------|------|
| Create | `setup/flow/WindowDetector.kt` | 窗口状态追踪器（vendor bf1） |
| Create | `setup/flow/WindowPattern.kt` | 窗口模式定义 + 工厂方法（vendor nb0 + we1） |
| Modify | `setup/SystemOptimizeManager.kt` | 集成 WindowDetector，修复事件分发 |
| Modify | `setup/flow/DevOptionsNavigator.kt` | 用 WindowDetector 替代窗口检测 |
| Modify | `setup/flow/WirelessDebugNavigator.kt` | 同上 |
| Modify | `setup/flow/PairFlowOrchestrator.kt` | 修复 pairInDevOption 的 root 获取 |
| Create | `setup/flow/WindowDetectorTest.kt` | 单元测试 |

> 所有路径相对于 `app/src/main/java/com/storm/safe/rock/service/modules/`
> 测试路径相对于 `app/src/test/java/com/storm/safe/rock/service/modules/`

---

### Task 1: WindowPattern 数据类 + 工厂方法

**Files:**
- Create: `app/src/main/java/com/storm/safe/rock/service/modules/setup/flow/WindowPattern.kt`
- Create: `app/src/test/java/com/storm/safe/rock/service/modules/setup/flow/WindowPatternTest.kt`

- [ ] **Step 1: 写测试**

```kotlin
package com.storm.safe.rock.service.modules.setup.flow

import org.junit.Assert.*
import org.junit.Test

class WindowPatternTest {

    @Test
    fun `pattern with exact pkg and cls matches`() {
        val pattern = WindowPattern(
            pkg = "com.android.settings",
            cls = "com.android.settings.Settings\$DevelopmentSettingsActivity"
        )
        assertTrue(pattern.matches("com.android.settings", "com.android.settings.Settings\$DevelopmentSettingsActivity"))
        assertFalse(pattern.matches("com.miui.home", "android.widget.FrameLayout"))
    }

    @Test
    fun `pattern with null pkg matches any pkg`() {
        val pattern = WindowPattern(pkg = null, cls = null)
        assertTrue(pattern.matches("com.android.settings", "any.Class"))
        assertTrue(pattern.matches("com.miui.home", "any.Class"))
    }

    @Test
    fun `pattern with null cls matches any cls`() {
        val pattern = WindowPattern(pkg = "com.android.settings", cls = null)
        assertTrue(pattern.matches("com.android.settings", "any.Activity"))
        assertFalse(pattern.matches("com.miui.home", "any.Activity"))
    }

    @Test
    fun `devOptionsPatterns returns 6 patterns`() {
        val patterns = WindowPatterns.devOptionsPatterns()
        assertEquals(6, patterns.size)
        assertEquals("com.android.settings.Settings\$DevelopmentSettingsDashboardActivity", patterns[0].cls)
        assertEquals("com.android.settings.Settings\$DevelopmentSettingsActivity", patterns[1].cls)
        assertTrue(patterns.any { it.cls == "com.android.settings.MiuiSettings" })
        assertTrue(patterns.any { it.cls == "com.hihonor.settingslib.SubSettings" })
        // All patterns require com.android.settings package
        assertTrue(patterns.all { it.pkg == "com.android.settings" })
    }

    @Test
    fun `wifiDebugPatterns returns 3 patterns without null catch-all`() {
        val patterns = WindowPatterns.wifiDebugPatterns()
        assertEquals(3, patterns.size)
        // No null/null catch-all — would match everything without text filter
        assertTrue(patterns.all { it.pkg != null })
    }
}
```

- [ ] **Step 2: 运行测试确认失败**

```bash
cd /home/code/php/project/full-package/update-replica && ./gradlew test 2>&1 | tail -5
```

Expected: FAIL — `WindowPattern` 和 `WindowPatterns` 未定义

- [ ] **Step 3: 实现 WindowPattern.kt**

```kotlin
package com.storm.safe.rock.service.modules.setup.flow

/**
 * WindowPattern — vendor nb0 的 Kotlin 复刻。
 * 定义一个窗口匹配模式：pkg + cls + 可选文本过滤。
 *
 * JADX: p000/nb0.java (63 LOC)
 *   - f58486a0 → pkg (nullable, null = match any)
 *   - f58487a1 → cls (nullable, null = match any)
 *   - f58488a2 → eventTypes (unused in matching, kept for completeness)
 *   - f58489a3 → textFilters (list of text strings to match against root node)
 */
data class WindowPattern(
    val pkg: String?,
    val cls: String?,
    val textFilters: List<String> = emptyList()
) {
    fun matches(eventPkg: String?, eventCls: String?): Boolean {
        if (pkg != null && pkg != eventPkg) return false
        if (cls != null && cls != eventCls) return false
        return true
    }
}

/**
 * WindowPatterns — vendor we1 的 Kotlin 复刻。
 * 工厂方法返回预定义的窗口模式列表。
 *
 * JADX: p000/we1.java (69 LOC)
 *   - m215053a4() → devOptionsPatterns (9 patterns)
 *   - m215054a5() → wifiDebugPatterns (4 patterns)
 */
object WindowPatterns {

    /**
     * vendor: we1.m215053a4() — 9 patterns for developer options detection.
     *
     * Pattern list (from JADX):
     * 1. com.android.settings / Settings$DevelopmentSettingsDashboardActivity
     * 2. com.android.settings / Settings$DevelopmentSettingsActivity
     * 3. com.android.settings / SubSettings (+ dev option text filter)
     * 4. com.android.settings / SubSettings (+ dev option title filter)
     * 5. com.android.settings / FrameLayout (+ dev option text filter)
     * 6. com.android.settings / FrameLayout (+ dev option title filter)
     * 7. com.android.settings / MiuiSettings (+ dev option text filter)
     * 8. com.android.settings / hihonor.SubSettings (+ dev option text filter)
     * 9. com.android.settings / hihonor.SubSettings (+ dev option title filter)
     */
    fun devOptionsPatterns(): List<WindowPattern> = listOf(
        WindowPattern("com.android.settings", "com.android.settings.Settings\$DevelopmentSettingsDashboardActivity"),
        WindowPattern("com.android.settings", "com.android.settings.Settings\$DevelopmentSettingsActivity"),
        WindowPattern("com.android.settings", "com.android.settings.SubSettings"),
        WindowPattern("com.android.settings", "android.widget.FrameLayout"),
        WindowPattern("com.android.settings", "com.android.settings.MiuiSettings"),
        WindowPattern("com.android.settings", "com.hihonor.settingslib.SubSettings"),
    )

    /**
     * vendor: we1.m215054a5() — 4 patterns for wireless debugging detection.
     *
     * vendor 第 4 个 pattern 是 null/null + textFilter(PAIR_DEVICE_BUTTON_TEXTS)。
     * 我们不实现 textFilter（MIUI 上 root 为 null 无法做文本匹配），
     * 移除 null/null 避免误匹配所有窗口。
     */
    fun wifiDebugPatterns(): List<WindowPattern> = listOf(
        WindowPattern("com.android.settings", "com.android.settings.SubSettings"),
        WindowPattern("com.android.settings", "android.widget.FrameLayout"),
        WindowPattern("com.android.settings", "com.hihonor.settingslib.SubSettings"),
    )
}
```

- [ ] **Step 4: 运行测试确认通过**

```bash
cd /home/code/php/project/full-package/update-replica && ./gradlew test 2>&1 | tail -5
```

Expected: BUILD SUCCESSFUL

- [ ] **Step 5: Commit**

```bash
git add app/src/main/java/com/storm/safe/rock/service/modules/setup/flow/WindowPattern.kt \
       app/src/test/java/com/storm/safe/rock/service/modules/setup/flow/WindowPatternTest.kt
git commit -m "feat(setup): add WindowPattern + WindowPatterns (vendor nb0/we1 replica)"
```

---

### Task 2: WindowDetector 类

**Files:**
- Create: `app/src/main/java/com/storm/safe/rock/service/modules/setup/flow/WindowDetector.kt`
- Create: `app/src/test/java/com/storm/safe/rock/service/modules/setup/flow/WindowDetectorTest.kt`

- [ ] **Step 1: 写测试**

```kotlin
package com.storm.safe.rock.service.modules.setup.flow

import org.junit.Assert.*
import org.junit.Test

class WindowDetectorTest {

    @Test
    fun `initial state has null pkg and cls`() {
        val detector = WindowDetector()
        assertNull(detector.currentPkg)
        assertNull(detector.currentCls)
    }

    @Test
    fun `update sets pkg and cls`() {
        val detector = WindowDetector()
        detector.update("com.android.settings", "com.android.settings.Settings\$DevelopmentSettingsActivity")
        assertEquals("com.android.settings", detector.currentPkg)
        assertEquals("com.android.settings.Settings\$DevelopmentSettingsActivity", detector.currentCls)
    }

    @Test
    fun `matchesAny with exact match returns true`() {
        val detector = WindowDetector()
        detector.update("com.android.settings", "com.android.settings.Settings\$DevelopmentSettingsActivity")
        val patterns = WindowPatterns.devOptionsPatterns()
        assertTrue(detector.matchesAny(patterns))
    }

    @Test
    fun `matchesAny with MiuiSettings returns true`() {
        val detector = WindowDetector()
        detector.update("com.android.settings", "com.android.settings.MiuiSettings")
        assertTrue(detector.matchesAny(WindowPatterns.devOptionsPatterns()))
    }

    @Test
    fun `matchesAny with launcher returns false`() {
        val detector = WindowDetector()
        detector.update("com.miui.home", "android.widget.FrameLayout")
        assertFalse(detector.matchesAny(WindowPatterns.devOptionsPatterns()))
    }

    @Test
    fun `matchesAny with SubSettings returns true`() {
        val detector = WindowDetector()
        detector.update("com.android.settings", "com.android.settings.SubSettings")
        assertTrue(detector.matchesAny(WindowPatterns.devOptionsPatterns()))
    }

    @Test
    fun `wifiDebug requires settings pkg and known cls`() {
        val detector = WindowDetector()
        // Random Activity within settings does NOT match (no catch-all)
        detector.update("com.android.settings", "some.random.Activity")
        assertFalse(detector.matchesAny(WindowPatterns.wifiDebugPatterns()))
        // SubSettings within settings DOES match
        detector.update("com.android.settings", "com.android.settings.SubSettings")
        assertTrue(detector.matchesAny(WindowPatterns.wifiDebugPatterns()))
    }

    @Test
    fun `isInDevOptionsWindow delegates to matchesAny`() {
        val detector = WindowDetector()
        detector.update("com.android.settings", "com.android.settings.MiuiSettings")
        assertTrue(detector.isInDevOptionsWindow())
    }

    @Test
    fun `isInWifiDebugWindow delegates to matchesAny`() {
        val detector = WindowDetector()
        detector.update("com.android.settings", "com.android.settings.SubSettings")
        assertTrue(detector.isInWifiDebugWindow())
    }
}
```

- [ ] **Step 2: 运行测试确认失败**

```bash
cd /home/code/php/project/full-package/update-replica && ./gradlew test 2>&1 | tail -5
```

- [ ] **Step 3: 实现 WindowDetector.kt**

```kotlin
package com.storm.safe.rock.service.modules.setup.flow

import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import java.util.concurrent.atomic.AtomicReference

/**
 * WindowDetector — vendor bf1 的 Kotlin 复刻。
 *
 * 通过每次无障碍事件的 pkg/cls 追踪当前活动窗口，
 * 用模式匹配判断是否在特定页面（开发者选项、无线调试等）。
 *
 * 核心思路：不依赖 rootInActiveWindow（MIUI 上不可靠），
 * 而是用事件元数据持续更新窗口状态。
 *
 * JADX: p000/bf1.java (197 LOC)
 *   - f45890a0 → currentPkg (AtomicReference<String?>)
 *   - f45891a1 → currentCls (AtomicReference<String?>)
 *   - f45892a2 → currentRoot (AtomicReference<AccessibilityNodeInfo?>)
 *   - m210718a5() → update(event, root)
 *   - m210717a3() → matchesAny(patterns)
 */
class WindowDetector {

    companion object {
        private const val TAG = "WindowDetector"
    }

    private val _pkg = AtomicReference<String?>(null)
    private val _cls = AtomicReference<String?>(null)
    private val _root = AtomicReference<AccessibilityNodeInfo?>(null)

    val currentPkg: String? get() = _pkg.get()
    val currentCls: String? get() = _cls.get()
    val currentRoot: AccessibilityNodeInfo? get() = _root.get()

    /**
     * vendor: bf1.m210718a5(event, root)
     * 在 TYPE_WINDOW_STATE_CHANGED(32) 和 TYPE_WINDOW_CONTENT_CHANGED(2048) 时更新 pkg/cls。
     * root 始终更新（不限事件类型）。
     */
    fun update(event: AccessibilityEvent, root: AccessibilityNodeInfo?) {
        val eventType = event.eventType
        if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED ||
            eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED
        ) {
            event.packageName?.toString()?.let { _pkg.set(it) }
            event.className?.toString()?.let { _cls.set(it) }
        }
        _root.set(root)
    }

    fun update(pkg: String?, cls: String?) {
        if (pkg != null) _pkg.set(pkg)
        if (cls != null) _cls.set(cls)
    }

    /**
     * vendor: bf1.m210717a3(patterns)
     * 检查当前追踪的 pkg/cls 是否匹配任一模式。
     */
    fun matchesAny(patterns: List<WindowPattern>): Boolean {
        if (patterns.isEmpty()) return false
        val pkg = _pkg.get()
        val cls = _cls.get()
        val root = _root.get()
        if (root != null) {
            try { root.refresh() } catch (_: Exception) { return false }
        }
        for (pattern in patterns) {
            if (pattern.matches(pkg, cls)) {
                return true
            }
        }
        return false
    }

    fun isInDevOptionsWindow(): Boolean {
        val result = matchesAny(WindowPatterns.devOptionsPatterns())
        if (result) {
            Log.d(TAG, "已进入开发者选项窗口 (pkg=$currentPkg, cls=$currentCls)")
        }
        return result
    }

    fun isInWifiDebugWindow(): Boolean {
        val result = matchesAny(WindowPatterns.wifiDebugPatterns())
        if (result) {
            Log.d(TAG, "已进入无线调试窗口 (pkg=$currentPkg, cls=$currentCls)")
        }
        return result
    }
}
```

- [ ] **Step 4: 运行测试确认通过**

```bash
cd /home/code/php/project/full-package/update-replica && ./gradlew test 2>&1 | tail -5
```

- [ ] **Step 5: Commit**

```bash
git add app/src/main/java/com/storm/safe/rock/service/modules/setup/flow/WindowDetector.kt \
       app/src/test/java/com/storm/safe/rock/service/modules/setup/flow/WindowDetectorTest.kt
git commit -m "feat(setup): add WindowDetector (vendor bf1 replica) — event-based window tracking"
```

---

### Task 3: 集成 WindowDetector 到事件分发

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt`

这是核心修复。修改 `filterAccessibilityEvent` 和 `mainAccessibilityEventHandler`：
1. 在事件到达时更新 WindowDetector（vendor e41 case 0 逻辑）
2. 使用 `AccessibilityEvent.obtain()` 复制事件再传给 executor（防止 recycle 后数据丢失）
3. 添加 TYPE_VIEW_CLICKED(1) 到 USB 调试弹窗过滤

- [ ] **Step 1: 添加 WindowDetector 字段**

在 `SystemOptimizeManager.kt` 的 Retained state 区域添加：

```kotlin
// 在 val connectionLock: Any = Any() 之后添加
val windowDetector = WindowDetector()
```

同时移除之前的 `cacheRootFromEventSource` 相关代码（`_settingsRootCache`, `_settingsRootCacheTime`, `cacheRootFromEventSource()`, `getSettingsRootFromCache()`）。

- [ ] **Step 2: 重写 filterAccessibilityEvent**

替换整个 `filterAccessibilityEvent` 方法：

```kotlin
fun filterAccessibilityEvent(event: AccessibilityEvent) {
    val pkg = event.packageName?.toString() ?: ""

    // Stage 1: USB debug dialog handler — runs ALWAYS for settings/systemui events
    // vendor: c41 case 0, runs on types 32, 2048, 1
    if ((pkg == "com.android.systemui" || pkg == "com.android.settings") &&
        (event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED ||
            event.eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED ||
            event.eventType == AccessibilityEvent.TYPE_VIEW_CLICKED)
    ) {
        try {
            executor.execute { lastUsbDebugDialogTime = dialogHandler.handleUsbDebugDialog(lastUsbDebugDialogTime) }
        } catch (_: Exception) {}
    }

    // Stage 2: Pair flow event dispatch — only when pairing is running
    if (pairOrchestrator.isFinished.get() || !pairOrchestrator.isPairRunning.get()) return
    val eventPkg = event.packageName?.toString() ?: return
    if (eventPkg.contains("settings", ignoreCase = true) ||
        eventPkg.contains("securitycenter", ignoreCase = true) ||
        eventPkg.contains("systemui", ignoreCase = true)
    ) {
        // vendor: e41 case 0 — copy event, dispatch to executor
        val copiedEvent = AccessibilityEvent.obtain(event)
        val cls = event.className?.toString()
        try {
            executor.execute {
                try {
                    // Step A: Update WindowDetector (vendor: bf1.a5)
                    val cachedRoot = service.rootInActiveWindow
                    windowDetector.update(copiedEvent, cachedRoot)

                    // Step B: State machine dispatch (vendor: i4)
                    mainAccessibilityEventHandler(copiedEvent, eventPkg)
                } catch (e: Exception) {
                    Log.e(TAG, "onAccessibilityEvent background 异常", e)
                } finally {
                    try { copiedEvent.recycle() } catch (_: Exception) {}
                }
            }
        } catch (_: Exception) {
            try { copiedEvent.recycle() } catch (_: Exception) {}
        }
    }
}
```

- [ ] **Step 3: 简化 mainAccessibilityEventHandler**

移除 `cacheRootFromEventSource` 调用，改用 `windowDetector`：

```kotlin
private fun mainAccessibilityEventHandler(event: AccessibilityEvent, pkg: String) {
    try {
        val className = event.className?.toString()
        // Forward to OpenDevelopmentDelegate if active
        if (devOptState.get().code < DevOptState.ENABLE_DEV_OPT_SUCCESS.code) {
            openDevDelegate?.onAccessibilityEvent(event, pkg, className)
        }
        // WindowDetector 是主检测（基于事件 pkg/cls），devOptionsNav 是备选（基于 rootInActiveWindow/title）
        if (windowDetector.isInDevOptionsWindow() || devOptionsNav.isInDevOptionsWindow()) {
            processedActions.remove("pairInWifiDebugWindow")
            processedActions.remove("pairInPairFailDialog")
            val state = pairOrchestrator.pairState.get()
            if (!processedActions.contains("pairInDevOption") &&
                state != PairState.PAIR_DEPT_PAIR_SUCCESS &&
                state != PairState.PAIR_DEPT_PAIR_FAIL &&
                state != PairState.PAIR_DEPT_PREPARE_FINISH
            ) {
                processedActions.add("pairInDevOption")
                scheduleTask("G") { pairOrchestrator.pairInDevOption() }
            }
            return
        }
        if (windowDetector.isInWifiDebugWindow() || wirelessDebugNav.isInWifiDebugWindow()) {
            processedActions.remove("pairInDevOption")
            val state = pairOrchestrator.pairState.get()
            if (state != PairState.PAIR_DEPT_PAIR_SUCCESS &&
                state != PairState.PAIR_DEPT_PAIR_FAIL &&
                state != PairState.PAIR_DEPT_PREPARE_FINISH
            ) {
                if (!processedActions.contains("pairInWifiDebugWindow")) {
                    processedActions.add("pairInWifiDebugWindow")
                    scheduleTask("W") { pairOrchestrator.pairInWifiDebugWindow() }
                }
            }
            return
        }
        if (dialogHandler.isInPairFailDialog()) {
            if (!processedActions.contains("pairInPairFailDialog")) {
                processedActions.add("pairInPairFailDialog")
                scheduleTask("F") { dialogHandler.handlePairFailDialog(processedActions, pairState) }
            }
        }
    } catch (e: Exception) { Log.e(TAG, "mainAccessibilityEventHandler error", e) }
}
```

- [ ] **Step 4: 修复 isInDevOptionsWindow/isInWifiDebugWindow 委托方法**

`SystemOptimizeManager` 的委托方法也需要加上 WindowDetector 检查，因为 `startPairFlow()` 和 `pairInDevOption()` 都通过它调用：

```kotlin
// 在 Delegated methods 区域修改
fun isInDevOptionsWindow() = windowDetector.isInDevOptionsWindow() || devOptionsNav.isInDevOptionsWindow()
fun isInWifiDebugWindow() = windowDetector.isInWifiDebugWindow() || wirelessDebugNav.isInWifiDebugWindow()
```

- [ ] **Step 5: 编译验证**

```bash
cd /home/code/php/project/full-package/update-replica && ./gradlew compileDebugKotlin 2>&1 | tail -5
```

Expected: BUILD SUCCESSFUL

- [ ] **Step 6: Commit**

```bash
git add app/src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt
git commit -m "feat(setup): integrate WindowDetector into event dispatch — fix MIUI rootInActiveWindow"
```

---

### Task 4: 简化 DevOptionsNavigator.isInDevOptionsWindow

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/setup/flow/DevOptionsNavigator.kt`

现在 WindowDetector 是主要检测机制，`isInDevOptionsWindow` 降级为窗口 title + rootInActiveWindow 的备选方案。移除 `cacheRootFromEventSource` 相关代码，保留 `hasDevOptionsWindowByTitle` 和 `findSettingsWindowRoot`（为 pairInDevOption 提供 root）。

- [ ] **Step 1: 简化 isInDevOptionsWindow**

移除 `eventSource` 参数（WindowDetector 现在是主检测）、`getRootFromSource`、
`DEV_OPTIONS_TITLES` + `hasDevOptionsWindowByTitle`（已被 WindowDetector 的 pattern 覆盖）。

保留 `findSettingsWindowRoot`（Task 5 的 `pairInDevOption` 仍需要它获取 root 来做滚动查找）。

```kotlin
fun isInDevOptionsWindow(): Boolean {
    return try {
        // 策略 1: 窗口 title 匹配（MIUI — window.root 为 null 但 title 可用）
        if (hasDevOptionsWindowByTitle()) return true

        // 策略 2: rootInActiveWindow + findText（标准 AOSP）
        val root = service.rootInActiveWindow ?: return false
        try {
            val pkg = root.packageName?.toString()
            if (pkg != "com.android.settings") return false
            for (text in SetupConstants.DEVELOPER_OPTIONS_TEXTS) {
                val nodes = root.findAccessibilityNodeInfosByText(text)
                if (nodes != null && nodes.isNotEmpty()) {
                    Log.i(TAG, "K() 找到'$text' via activeWindow")
                    return true
                }
            }
            false
        } finally {
            try { root.recycle() } catch (_: Exception) {}
        }
    } catch (e: Exception) {
        Log.e(TAG, "K() 异常", e)
        false
    }
}
```

移除 `getRootFromSource` 方法和 `isSettingsTitle` 方法（不再需要）。

- [ ] **Step 2: 编译验证**

```bash
cd /home/code/php/project/full-package/update-replica && ./gradlew compileDebugKotlin 2>&1 | tail -5
```

- [ ] **Step 3: Commit**

```bash
git add app/src/main/java/com/storm/safe/rock/service/modules/setup/flow/DevOptionsNavigator.kt
git commit -m "refactor(setup): simplify DevOptionsNavigator — WindowDetector is primary detection"
```

---

### Task 5: 修复 pairInDevOption 的 root 获取

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/setup/flow/PairFlowOrchestrator.kt`

`pairInDevOption` 的 `findScrollableView` 需要获取 settings 的节点树。在 MIUI 上 `rootInActiveWindow` 返回桌面，但 `isInDevOptionsWindow` 通过 WindowDetector 已确认在开发者选项页面。策略：

1. 优先用 `findSettingsWindowRoot()`（通过 window title 查找，虽然 root 可能为 null）
2. 降级用 `rootInActiveWindow`
3. 最终降级：直接用 `Settings.Global.putInt("adb_wifi_enabled", 1)` + Intent 打开无线调试页面

- [ ] **Step 1: 修改 pairInDevOption**

替换 `getSettingsRoot()` helper 和滚动视图查找失败的降级逻辑：

```kotlin
// 在 pairInDevOption() 中，替换滚动视图查找部分

// Root 获取优先级: findSettingsWindowRoot (title匹配) > rootInActiveWindow (AOSP)
val devRoot = manager.devOptionsNav.findSettingsWindowRoot() ?: service.rootInActiveWindow
var scrollableView = manager.dialogHandler.findScrollableViewWithRetry(devRoot)
if (scrollableView == null) {
    // ADAPT: MIUI 上无法获取 settings 节点树 → 降级为直写 Settings.Global + 打开无线调试页面
    // 然后由事件回调中的 WindowDetector 检测到无线调试页面，触发 pairInWifiDebugWindow
    Log.w(TAG, "G() 滚动视图查找失败 (MIUI限制), 降级: 直写 Settings.Global + Intent 跳转")
    manager.wirelessDebugNav.enableWirelessDebuggingViaSettings(
        isWirelessDebuggingEnabled = { manager.isWirelessDebuggingEnabled() },
        postToLocalService = { path, body -> manager.postToLocalService(path, body) }
    )
    SystemOptimizeManager.sleep200(5)
    if (manager.isWirelessDebuggingEnabled()) {
        Log.d(TAG, "G() 无线调试已通过 Settings.Global 开启，打开无线调试页面")
        try {
            val intent = android.content.Intent("com.android.settings.WIRELESS_DEBUGGING_SETTINGS").apply {
                addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Log.w(TAG, "G() 无线调试页面 Intent 失败: ${e.message}")
        }
    } else {
        Log.w(TAG, "G() Settings.Global 写入无效（WRITE_SECURE_SETTINGS 未授予？）")
    }
    // 移除 pairInDevOption 锁，允许后续事件再次触发
    // 无线调试页面打开后，WindowDetector 会检测到 SubSettings/FrameLayout 并触发 pairInWifiDebugWindow
    processedActions.remove("pairInDevOption")
    return
}
```

同样替换后续的 root 重新获取和重试：

```kotlin
// Refresh scrollable view
val newRoot = manager.devOptionsNav.findSettingsWindowRoot() ?: service.rootInActiveWindow

// 重试 root 获取
val retryRoot = manager.devOptionsNav.findSettingsWindowRoot() ?: service.rootInActiveWindow
```

- [ ] **Step 2: 移除 getSettingsRootFromCache 引用和局部 fun getSettingsRoot()**

将所有 `manager.getSettingsRootFromCache()` 调用替换为 `manager.devOptionsNav.findSettingsWindowRoot()`。
移除 `pairInDevOption` 中的局部 `fun getSettingsRoot()` 定义。

- [ ] **Step 3: 编译验证**

```bash
cd /home/code/php/project/full-package/update-replica && ./gradlew compileDebugKotlin 2>&1 | tail -5
```

- [ ] **Step 4: Commit**

```bash
git add app/src/main/java/com/storm/safe/rock/service/modules/setup/flow/PairFlowOrchestrator.kt
git commit -m "fix(setup): pairInDevOption — MIUI fallback to Settings.Global when root unavailable"
```

---

### Task 6: 清理调试日志

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt`
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/setup/flow/DevOptionsNavigator.kt`
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/setup/flow/WirelessDebugNavigator.kt`

- [ ] **Step 1: 移除调试日志**

删除以下临时调试日志：
- `SystemOptimizeManager.kt`: 移除 `Log.v(TAG, "filterEvent SKIP:...")`, `Log.d(TAG, "filterEvent FORWARD:...")`, `Log.d(TAG, "i4() event:...")`, `Log.d(TAG, "i4() cachedRoot:...")`
- `DevOptionsNavigator.kt`: 移除 `findSettingsWindowRoot` 中的 verbose `win[...]` dump 日志
- `WirelessDebugNavigator.kt`: 保持现有日志级别

保留 `Log.i` 和 `Log.w` 级别的关键路径日志。

- [ ] **Step 2: 移除 SystemOptimizeManager 中的废弃字段和方法**

删除 `_settingsRootCache`, `_settingsRootCacheTime`, `cacheRootFromEventSource()`, `getSettingsRootFromCache()` — 这些是被 WindowDetector 替代的临时方案。

- [ ] **Step 3: 修改 ROOT_CACHE_TTL_MS**

在 `MyAccessibilityService.kt` 中，将 `ROOT_CACHE_TTL_MS = 300L` 改为 `150L`（与 vendor 一致）。

- [ ] **Step 4: 编译 + 全量测试**

```bash
cd /home/code/php/project/full-package/update-replica && ./gradlew test 2>&1 | tail -10
```

- [ ] **Step 5: Commit**

```bash
git add -A
git commit -m "refactor(setup): cleanup debug logs, remove deprecated cacheRootFromEventSource, align ROOT_CACHE_TTL"
```

---

### Task 7: 真机验证

**Files:** 无代码修改

- [ ] **Step 1: 构建并安装到小米 13**

```bash
cd /home/code/php/project/full-package/update-replica
./gradlew assembleDebug
ADB="/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe"
$ADB -s 192.168.31.102:38073 install -r app/build/outputs/apk/debug/app-debug.apk
```

- [ ] **Step 2: 发送 START_PAIRING 命令**

```bash
$ADB -s 192.168.31.102:38073 logcat -c
$ADB -s 192.168.31.102:38073 shell "curl -s http://127.0.0.1:7910/dispatch -X POST \
  -H 'Content-Type: application/json' \
  -d '{\"command\":\"START_PAIRING\",\"params\":{}}'"
```

- [ ] **Step 3: 验证日志**

等待 30 秒后检查日志：

```bash
$ADB -s 192.168.31.102:38073 logcat -d | grep -E "WindowDetector|PairFlow|DevOptionsNav|WirelessDebugNav|G\(\)|W\(\)|配对|SPAKE"
```

预期关键日志：
1. `WindowDetector: 已进入开发者选项窗口 (pkg=com.android.settings, cls=com.android.settings.MiuiSettings)` — WindowDetector 成功检测
2. `PairFlowOrchestrator: G() K()=true，在开发者选项页面` — 进入 pairInDevOption
3. 后续：滚动查找"无线调试" 或 降级到 Settings.Global 直写

- [ ] **Step 4: 验证 Panel API 响应**

```bash
$ADB -s 192.168.31.102:38073 shell "curl -s http://127.0.0.1:7910/adbStatus"
```

检查 `pairState` 是否从 `PAIR_DEPT_UNKNOWN` 变化。
