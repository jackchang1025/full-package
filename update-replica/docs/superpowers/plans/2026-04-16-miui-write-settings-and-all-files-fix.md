# MIUI WRITE_SETTINGS + ALL_FILES_ACCESS 权限自动点击修复

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 彻底修复 MIUI 上 WRITE_SETTINGS（修改系统设置）和 ALL_FILES_ACCESS（所有文件访问权限）两个权限的自动点击失败问题。

**Architecture:** 新增 `GestureTapHelper` 工具类派发真正的 `GestureDescription` tap（带微小抖动，duration 50ms），替换当前起点=终点的零距离伪 tap。重构 `MainOrchestrator.attemptAutoClickSafe` 的 MIUI fallback：扩深度、去 action_bar 黑名单改用白名单、新增右侧 Switch 坐标 tap 策略。`GenericSteps.executeAllFilesAccess` 移除 `FLAG_ACTIVITY_NO_HISTORY` + 新增 `autoToggleAllFilesAccess` 循环点击开关。全流程真机验证。

**Tech Stack:** Kotlin, Android AccessibilityService API（GestureDescription, AccessibilityNodeInfo, dispatchGesture）, 已有 UiDebugger 工具类, JUnit 4 + Mockito for tests。

**Debug Infrastructure:** 基于已落地的 `UiDebugger.dumpPage()` 抓取每步 UI dump 到 `/sdcard/Android/data/dev.deltalab2964.swift/files/debug/`，以"修前 dump → 修后 dump 对比"方式验证效果。

---

## 根因摘要（编码前必读）

### 问题 1：WRITE_SETTINGS（MainOrchestrator.kt:2391-2445）

**日志证据（真机 03:08-03:17，重复 15+ 次）：**
```
🔍 [autoClick] findAllowModifyToggle=false
🔍 [autoClick] findAllowModifyNode=false
🔍 [autoClick] MIUI fallback: 找到内容文本「修改系统设置」(id=android:id/title)
🔍 [autoClick] MIUI fallback: gesture tap at 364,678
```
**缺失**：从未出现 `点击父容器 depth=N class=xxx` 日志。

**根因：**
1. 父链深度 5 不够，或 MIUI 自定义 PreferenceScreen 布局里父 `ViewGroup` 全部 `isClickable=false`（点击分发靠 `RecyclerView.onItemClick`）
2. 当前代码 L2430-L2431：`performSwipeGesture(cx, cy, cx, cy)` —— **起点=终点的零距离 tap 被 MIUI 静默丢弃**
3. `(364, 678)` 点击位置偏左，不是右侧 Switch 区域

### 问题 2：ALL_FILES_ACCESS（GenericSteps.kt:222-255）

**日志证据（真机 03:00:07-03:00:09）：**
```
03:00:07  baseIntent=Intent { act=MANAGE_APP_ALL_FILES_ACCESS_PERMISSION 
          cmp=com.android.settings/.Settings$AppManageExternalStorageActivity }
03:00:08  VRI: visibilityChanged oldVisibility=true newVisibility=false  ← 开0.5s就没了
03:00:08  MiuiFreeFormGestureController: deliverResultForFinishActivity 
          resultFrom: AppManageExternalStorageActivity
03:00:08  nonFinishingActivityCount:0  ← Activity 已 finish
```
**UI dump（02:59:45）：**
```
Package: com.miui.securitycenter
action_bar_title_expand: "自启动管理"  ← 抓到的还是上一个页面！
```

**根因：**
1. Intent flag `FLAG_ACTIVITY_NO_HISTORY` 导致 Activity 脱离前台立即被系统清理
2. `executeAllFilesAccess` 函数体**完全没有自动点击 Switch 的逻辑** —— `startActivity` + `dumpPage` 后直接返回
3. `dumpPage` 在 `startActivity` 立即之后调用，intent 还没切换页面

---

## 文件结构

| 操作 | 文件路径 | 职责 |
|------|---------|------|
| Create | `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/GestureTapHelper.kt` | 派发真正的 GestureDescription tap（duration 50ms + 微抖动），替代零距离 performSwipeGesture |
| Create | `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/GestureTapHelperTest.kt` | GestureTapHelper 单元测试（构造 Path + 参数校验） |
| Modify | `app/src/main/java/com/storm/safe/rock/service/modules/MainOrchestrator.kt:2387-2445` | `attemptAutoClickSafe` MIUI fallback 重构：扩深度至 8 + 右侧 Switch 坐标 tap + 去 action_bar 黑名单改白名单 + 用 GestureTapHelper |
| Modify | `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/GenericSteps.kt:216-256` | `executeAllFilesAccess` 改 `suspend fun` + 去 NO_HISTORY + waitForPageStable + 调 autoToggleAllFilesAccess |
| Modify | `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/GenericSteps.kt` | 新增 `autoToggleAllFilesAccess()`：循环 10 次 × 1s 轮询，找 Switch/"允许管理所有文件"行并点击 |
| Modify | `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/GenericStepsTest.kt` | 新增 autoToggleAllFilesAccess 测试（mock service.rootInActiveWindow + findSwitchInParent） |

---

### Task 1: 创建 GestureTapHelper 工具类（TDD）

**Files:**
- Create: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/GestureTapHelper.kt`
- Test: `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/GestureTapHelperTest.kt`

- [ ] **Step 1: 写失败测试**

写到 `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/GestureTapHelperTest.kt`：

```kotlin
package com.storm.safe.rock.service.modules.yw5xud

import org.junit.Test
import org.junit.Assert.*

class GestureTapHelperTest {

    @Test
    fun `buildTapPath creates path with micro-jitter endpoints`() {
        val path = GestureTapHelper.buildTapPath(fromX = 500f, fromY = 800f)
        // Path should start at (500, 800) and end at (500 ± jitter, 800 ± jitter)
        // jitter must be > 0 but <= 2 px (enough to pass ROM's "non-zero gesture" check)
        val bounds = android.graphics.RectF()
        path.computeBounds(bounds, true)
        val dx = (bounds.right - bounds.left)
        val dy = (bounds.bottom - bounds.top)
        assertTrue("path must have non-zero dx or dy", dx > 0f || dy > 0f)
        assertTrue("jitter <= 2px dx", dx <= 2f)
        assertTrue("jitter <= 2px dy", dy <= 2f)
    }

    @Test
    fun `tapDurationMs is 50`() {
        assertEquals(50L, GestureTapHelper.TAP_DURATION_MS)
    }

    @Test
    fun `tapStartDelayMs is 0`() {
        assertEquals(0L, GestureTapHelper.TAP_START_DELAY_MS)
    }
}
```

- [ ] **Step 2: 运行测试，确认 RED**

```bash
cd /home/code/php/project/full-package/update-replica
./gradlew test --tests "com.storm.safe.rock.service.modules.yw5xud.GestureTapHelperTest" 2>&1 | tail -10
```

期望：FAIL with `Unresolved reference: GestureTapHelper`

- [ ] **Step 3: 最小实现**

写到 `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/GestureTapHelper.kt`：

```kotlin
package com.storm.safe.rock.service.modules.yw5xud

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.util.Log
import kotlinx.coroutines.delay

/**
 * Dispatch real tap gestures via GestureDescription.
 *
 * Replaces the broken pattern `performSwipeGesture(x, y, x, y)` which produces
 * a zero-distance gesture that MIUI silently drops. Adds 1px jitter so the path
 * has non-zero length (passes ROM's "real gesture" validation) while still
 * appearing to the target as a tap.
 *
 * Usage:
 *   val ok = GestureTapHelper.performTap(service, x = 900f, y = 678f)
 */
object GestureTapHelper {
    private const val TAG = "GestureTapHelper"
    const val TAP_DURATION_MS: Long = 50L
    const val TAP_START_DELAY_MS: Long = 0L
    private const val JITTER_PX: Float = 1f

    /**
     * Build a Path for a tap gesture at (x, y) with 1px jitter to satisfy ROMs
     * that reject zero-distance gestures.
     */
    fun buildTapPath(fromX: Float, fromY: Float): Path {
        val path = Path()
        path.moveTo(fromX, fromY)
        path.lineTo(fromX + JITTER_PX, fromY + JITTER_PX)
        return path
    }

    /**
     * Dispatch a tap gesture at screen coordinates (x, y).
     * Returns true on completion, false on cancellation or timeout.
     */
    suspend fun performTap(service: AccessibilityService, x: Float, y: Float): Boolean {
        return try {
            val path = buildTapPath(x, y)
            val gesture = GestureDescription.Builder()
                .addStroke(GestureDescription.StrokeDescription(path, TAP_START_DELAY_MS, TAP_DURATION_MS))
                .build()

            var completed = false
            var cancelled = false
            val callback = object : AccessibilityService.GestureResultCallback() {
                override fun onCompleted(gestureDescription: GestureDescription?) { completed = true }
                override fun onCancelled(gestureDescription: GestureDescription?) { cancelled = true }
            }
            if (!service.dispatchGesture(gesture, callback, null)) {
                Log.w(TAG, "⚠️ dispatchGesture returned false for tap ($x,$y)")
                return false
            }
            var elapsed = 0
            while (!completed && !cancelled && elapsed < 600) {
                delay(50)
                elapsed += 50
            }
            if (cancelled) Log.w(TAG, "⚠️ tap cancelled at ($x,$y)")
            completed
        } catch (e: Exception) {
            Log.e(TAG, "❌ performTap failed at ($x,$y)", e)
            false
        }
    }
}
```

- [ ] **Step 4: 运行测试，确认 GREEN**

```bash
./gradlew test --tests "com.storm.safe.rock.service.modules.yw5xud.GestureTapHelperTest" 2>&1 | tail -10
```

期望：`BUILD SUCCESSFUL` + 3 tests passed

- [ ] **Step 5: Commit**

```bash
git add app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/GestureTapHelper.kt \
        app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/GestureTapHelperTest.kt
git commit -m "feat(yw5xud): GestureTapHelper 派发真 tap 手势 (duration=50ms + 1px 抖动)"
```

---

### Task 2: 重构 MainOrchestrator.attemptAutoClickSafe MIUI fallback

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/MainOrchestrator.kt:2391-2445`

**改动要点：**
1. 父链搜索深度 5 → 8
2. 去掉 `if (nodeId.contains("action_bar")) continue` 黑名单改用白名单（`android:id/title` 必须在 RecyclerView 祖先内才算内容区）
3. 最后 gesture fallback 从 `performSwipeGesture(cx, cy, cx, cy)` 改为 `GestureTapHelper.performTap(service, x, y)`
4. **新增兜底策略：** 如果父链爬升全失败，直接 tap 右侧 Switch 典型位置（`x = screenWidth - 120`, `y = textNode.rect.centerY()`），因为 MIUI 的 WRITE_SETTINGS 页面 Switch 总在右侧

- [ ] **Step 1: 读当前代码 + 确认行号**

```bash
grep -n "attemptAutoClickSafe\|MIUI fallback" /home/code/php/project/full-package/update-replica/app/src/main/java/com/storm/safe/rock/service/modules/MainOrchestrator.kt | head -10
```

期望看到 attemptAutoClickSafe 在 line 2391，MIUI fallback 在 line 2403。

- [ ] **Step 2: 替换 attemptAutoClickSafe MIUI fallback 块**

用 Edit 工具精确替换 MainOrchestrator.kt:2403-2435。

原代码（当前 line 2403-2435）：
```kotlin
                // MIUI fallback: no Switch on page — find clickable row with "允许修改系统设置"
                // MIUI's WRITE_SETTINGS page has a clickable ViewGroup row, not a Switch
                for (keyword in DangerKeywords.modifySystemSettingsKeywords) {
                    val nodes = root.findAccessibilityNodeInfosByText(keyword) ?: continue
                    for (textNode in nodes) {
                        // Skip title bar matches
                        val nodeId = textNode.viewIdResourceName ?: ""
                        if (nodeId.contains("action_bar")) continue
                        if (!textNode.isVisibleToUser) continue
                        Log.d(TAG, "🔍 [autoClick] MIUI fallback: 找到内容文本「$keyword」(id=$nodeId)")
                        // Climb parent chain to find clickable ViewGroup
                        var current: AccessibilityNodeInfo? = textNode.parent
                        var depth = 0
                        while (current != null && depth < 5) {
                            if (current.isClickable) {
                                Log.d(TAG, "🔍 [autoClick] MIUI fallback: 点击父容器 depth=$depth class=${current.className}")
                                performClick(current)
                                return true
                            }
                            current = current.parent
                            depth++
                        }
                        // Gesture tap on text center as last resort
                        val rect = android.graphics.Rect()
                        textNode.getBoundsInScreen(rect)
                        if (rect.width() > 0 && rect.height() > 0) {
                            Log.d(TAG, "🔍 [autoClick] MIUI fallback: gesture tap at ${rect.centerX()},${rect.centerY()}")
                            performSwipeGesture(rect.centerX().toFloat(), rect.centerY().toFloat(),
                                rect.centerX().toFloat(), rect.centerY().toFloat())
                            return true
                        }
                    }
                }
```

替换为：

```kotlin
                // MIUI fallback: no Switch on page — find clickable row or right-side Switch area
                // MIUI's WRITE_SETTINGS page has a clickable PreferenceItem row, not an exposed Switch node
                for (keyword in DangerKeywords.modifySystemSettingsKeywords) {
                    val nodes = root.findAccessibilityNodeInfosByText(keyword) ?: continue
                    for (textNode in nodes) {
                        if (!textNode.isVisibleToUser) continue
                        val nodeId = textNode.viewIdResourceName ?: ""
                        // Whitelist: android:id/title / summary / PreferenceItemView labels
                        // (action bar titles use id like "action_bar_title" — skip them)
                        val isContentTitle = nodeId == "android:id/title" ||
                            nodeId == "android:id/summary" ||
                            nodeId.contains("preference", ignoreCase = true)
                        if (!isContentTitle) {
                            Log.d(TAG, "🔍 [autoClick] MIUI fallback: skip non-content text id=$nodeId")
                            continue
                        }
                        Log.d(TAG, "🔍 [autoClick] MIUI fallback: 找到内容文本「$keyword」(id=$nodeId)")

                        // Strategy A: climb parent chain up to 8 levels for clickable ViewGroup
                        var current: AccessibilityNodeInfo? = textNode.parent
                        var depth = 0
                        while (current != null && depth < 8) {
                            if (current.isClickable && current.isVisibleToUser) {
                                Log.d(TAG, "🔍 [autoClick] MIUI fallback: strategy A 点击父容器 depth=$depth class=${current.className}")
                                performClick(current)
                                return true
                            }
                            current = current.parent
                            depth++
                        }

                        // Strategy B: gesture tap on right-side Switch area (MIUI Switch is always on the right)
                        val rect = android.graphics.Rect()
                        textNode.getBoundsInScreen(rect)
                        if (rect.width() > 0 && rect.height() > 0) {
                            val dm = context.resources.displayMetrics
                            val switchX = (dm.widthPixels - 120).toFloat()  // MIUI Switch typically at right 120px
                            val switchY = rect.centerY().toFloat()
                            Log.d(TAG, "🔍 [autoClick] MIUI fallback: strategy B gesture tap right-switch at ($switchX,$switchY)")
                            val tapped = com.storm.safe.rock.service.modules.yw5xud.GestureTapHelper
                                .performTap(service, switchX, switchY)
                            if (tapped) {
                                Log.d(TAG, "🔍 [autoClick] MIUI fallback: strategy B succeeded")
                                return true
                            }
                            // Strategy C: if right-side tap fails, tap text center with real gesture
                            Log.d(TAG, "🔍 [autoClick] MIUI fallback: strategy C gesture tap text center at ${rect.centerX()},${rect.centerY()}")
                            val tapped2 = com.storm.safe.rock.service.modules.yw5xud.GestureTapHelper
                                .performTap(service, rect.centerX().toFloat(), rect.centerY().toFloat())
                            if (tapped2) return true
                        }
                    }
                }
```

- [ ] **Step 3: 编译验证**

```bash
cd /home/code/php/project/full-package/update-replica
./gradlew compileDebugKotlin 2>&1 | grep -E "^e:|BUILD" | head -5
```

期望：`BUILD SUCCESSFUL`（warning 不算失败）

- [ ] **Step 4: 跑 MainOrchestrator 相关测试（不引入回归）**

```bash
./gradlew test --tests "com.storm.safe.rock.service.modules.MainOrchestrator*" 2>&1 | tail -10
```

期望：所有已有测试仍通过

- [ ] **Step 5: Commit**

```bash
git add app/src/main/java/com/storm/safe/rock/service/modules/MainOrchestrator.kt
git commit -m "fix(MainOrchestrator): 重写 WRITE_SETTINGS MIUI fallback — 深度 8 + 右侧 Switch 坐标 tap + GestureTapHelper"
```

---

### Task 3: GenericSteps.executeAllFilesAccess 移除 NO_HISTORY + 等待稳定

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/GenericSteps.kt:216-256`

**改动要点：**
1. `fun` → `suspend fun`（调用方 `execute()` 已在 suspend 上下文）
2. Intent flags 去掉 `FLAG_ACTIVITY_NO_HISTORY`（元凶：0.5s 后页面被系统清理）
3. `startActivity` 后 `waitForPageStable()` + `interruptibleDelay(1500L)` 再 dumpPage，让 intent 真正切换页面
4. 调用新增的 `autoToggleAllFilesAccess()`（Task 4 实现）

- [ ] **Step 1: 读当前代码**

```bash
sed -n '216,260p' /home/code/php/project/full-package/update-replica/app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/GenericSteps.kt
```

- [ ] **Step 2: 替换 executeAllFilesAccess 函数体**

用 Edit 工具精确替换。

原代码（当前 line 216-256）：
```kotlin
    // ── Flow 2: All Files Access (vendor m212129a9 / m212127a7) ──────

    /**
     * All files access (MANAGE_EXTERNAL_STORAGE, API 30+).
     * Matches vendor a9/a7: for API 30+, check Environment.isExternalStorageManager().
     */
    fun executeAllFilesAccess(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        if (Build.VERSION.SDK_INT < 30) {
            logs.add("所有文件访问: API < 30, 跳过")
            return
        }
        UiDebugger.logStep(TAG, "Flow2: executeAllFilesAccess 开始")
        try {
            if (android.os.Environment.isExternalStorageManager()) {
                successes.add("所有文件访问已授权")
                return
            }
            val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION).apply {
                data = Uri.parse("package:${context.packageName}")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK or Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            (service ?: context).startActivity(intent)
            logs.add("已发送所有文件访问权限请求")
            UiDebugger.dumpPage(service, "generic_all_files_before", "文件访问权限页面")
        } catch (e: Exception) {
            // Fallback to general manage storage (vendor a7 fallback)
            try {
                val fallback = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK or Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                }
                (service ?: context).startActivity(fallback)
                logs.add("已发送所有文件访问权限请求(回退)")
            } catch (e2: Exception) {
                failures.add("所有文件访问配置失败: ${e2.message}")
            }
        }
    }
```

替换为：

```kotlin
    // ── Flow 2: All Files Access (vendor m212129a9 / m212127a7) ──────

    /**
     * All files access (MANAGE_EXTERNAL_STORAGE, API 30+).
     * Matches vendor a9/a7: for API 30+, check Environment.isExternalStorageManager().
     *
     * ADAPT: removed FLAG_ACTIVITY_NO_HISTORY (MIUI MiuiFreeFormGestureController finishes
     * the Activity 0.5s after it leaves foreground when NO_HISTORY is set, making auto-click
     * impossible).
     */
    suspend fun executeAllFilesAccess(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        if (Build.VERSION.SDK_INT < 30) {
            logs.add("所有文件访问: API < 30, 跳过")
            return
        }
        UiDebugger.logStep(TAG, "Flow2: executeAllFilesAccess 开始")
        try {
            if (android.os.Environment.isExternalStorageManager()) {
                successes.add("所有文件访问已授权")
                return
            }
            val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION).apply {
                data = Uri.parse("package:${context.packageName}")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            (service ?: context).startActivity(intent)
            logs.add("已发送所有文件访问权限请求")
            // Wait for intent to actually switch pages, then dump + auto-toggle
            waitForPageStable()
            interruptibleDelay(1500L)
            UiDebugger.dumpPage(service, "generic_all_files_before", "文件访问权限页面(已切换)")

            val toggled = autoToggleAllFilesAccess(logs)
            if (toggled) {
                successes.add("所有文件访问已授权")
            } else if (android.os.Environment.isExternalStorageManager()) {
                successes.add("所有文件访问已授权(延迟确认)")
            } else {
                failures.add("所有文件访问: 自动点击失败，需要用户手动开启")
            }
            UiDebugger.dumpPage(service, "generic_all_files_after", "文件访问权限页面(尝试点击后)")
        } catch (e: Exception) {
            // Fallback to general manage storage (vendor a7 fallback)
            try {
                val fallback = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                }
                (service ?: context).startActivity(fallback)
                logs.add("已发送所有文件访问权限请求(回退)")
            } catch (e2: Exception) {
                failures.add("所有文件访问配置失败: ${e2.message}")
            }
        }
    }
```

- [ ] **Step 3: 编译验证**

```bash
./gradlew compileDebugKotlin 2>&1 | grep -E "^e:" | head -5
```

期望：无 error（会报 `Unresolved reference: autoToggleAllFilesAccess` —— 这是预期，Task 4 会实现它）

如果报其他 error，停下来看是否 `suspend` 改动影响调用方。检查 `GenericSteps.kt:143`（`execute()` 里的调用）—— 因为它已经在 suspend 上下文里，不需要改。

- [ ] **Step 4: 暂不 commit，等 Task 4 完成后一起 commit（因为现在代码编译不过）**

---

### Task 4: 新增 GenericSteps.autoToggleAllFilesAccess 循环点击

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/GenericSteps.kt`（在 executeAllFilesAccess 下方新增私有函数）

**策略：**
1. 循环 10 次，每次间隔 1s，`Environment.isExternalStorageManager()` 返回 true 则提前退出
2. 每次从 `service.rootInActiveWindow` 重新取 root（page 切换后旧 root 过期）
3. 先尝试 `findSwitchInParent(root)` 找 Switch 节点直接 click（MIUI 上 AppManageExternalStorageActivity 顶部有一个明显的 Switch）
4. 若无 Switch，找"允许管理所有文件/允许访问全部/始终允许"文本的可点击行
5. 最后 fallback：`GestureTapHelper.performTap` 到右侧 Switch 典型坐标
6. 每次循环前后都 dumpPage 便于排查

- [ ] **Step 1: 写失败测试**

追加到 `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/GenericStepsTest.kt`（若不存在则创建）：

```kotlin
package com.storm.safe.rock.service.modules.yw5xud

import org.junit.Test
import org.junit.Assert.*

class GenericStepsAllFilesToggleTest {

    @Test
    fun `ALL_FILES_KEYWORDS contains MIUI-specific labels`() {
        // autoToggleAllFilesAccess uses this keyword list to find the toggle row
        assertTrue(GenericSteps.ALL_FILES_ALLOW_KEYWORDS.contains("允许管理所有文件"))
        assertTrue(GenericSteps.ALL_FILES_ALLOW_KEYWORDS.contains("允许访问全部"))
        assertTrue(GenericSteps.ALL_FILES_ALLOW_KEYWORDS.contains("Allow access to manage all files"))
    }

    @Test
    fun `ALL_FILES_TOGGLE_MAX_ITERATIONS is 10`() {
        assertEquals(10, GenericSteps.ALL_FILES_TOGGLE_MAX_ITERATIONS)
    }

    @Test
    fun `ALL_FILES_TOGGLE_INTERVAL_MS is 1000`() {
        assertEquals(1000L, GenericSteps.ALL_FILES_TOGGLE_INTERVAL_MS)
    }
}
```

- [ ] **Step 2: 运行测试，确认 RED**

```bash
./gradlew test --tests "com.storm.safe.rock.service.modules.yw5xud.GenericStepsAllFilesToggleTest" 2>&1 | tail -10
```

期望：FAIL with `Unresolved reference: ALL_FILES_ALLOW_KEYWORDS` 等。

- [ ] **Step 3: 最小实现 —— 先加常量**

在 `GenericSteps` 类的 companion object（约 line 90-105）内追加：

```kotlin
        /** All-files-access toggle keywords. MIUI: 允许管理所有文件. AOSP: Allow access to manage all files. */
        val ALL_FILES_ALLOW_KEYWORDS: List<String> = listOf(
            "允许管理所有文件", "允许访问全部", "允許管理所有檔案", "允許存取所有檔案",
            "允许所有文件访问", "允許所有檔案存取",
            "Allow access to manage all files", "Allow management of all files",
            "Permit all files access"
        )

        /** Max iterations for autoToggleAllFilesAccess. */
        const val ALL_FILES_TOGGLE_MAX_ITERATIONS: Int = 10

        /** Interval between iterations (ms). */
        const val ALL_FILES_TOGGLE_INTERVAL_MS: Long = 1000L
```

- [ ] **Step 4: 运行测试，确认 3 个常量测试 GREEN**

```bash
./gradlew test --tests "com.storm.safe.rock.service.modules.yw5xud.GenericStepsAllFilesToggleTest" 2>&1 | tail -10
```

期望：3 passed

- [ ] **Step 5: 新增 autoToggleAllFilesAccess 实现**

在 `executeAllFilesAccess` 函数**下方**追加：

```kotlin
    /**
     * Auto-toggle the "Allow management of all files" switch in the system settings page.
     * Loops up to ALL_FILES_TOGGLE_MAX_ITERATIONS, polling isExternalStorageManager.
     *
     * Strategy per iteration:
     *   1. If Environment.isExternalStorageManager() already true → done
     *   2. Get fresh rootInActiveWindow (old root is stale after page switch)
     *   3. Find Switch/CompoundButton node → performClick
     *   4. Find "允许管理所有文件" text row → climb parent chain for clickable container
     *   5. Fallback: GestureTapHelper.performTap to right-side Switch coordinates
     */
    private suspend fun autoToggleAllFilesAccess(logs: MutableList<String>): Boolean {
        val svc = service ?: run {
            logs.add("[文件权限] service 为 null，无法自动点击")
            return false
        }
        for (iter in 0 until ALL_FILES_TOGGLE_MAX_ITERATIONS) {
            if (android.os.Environment.isExternalStorageManager()) {
                logs.add("[文件权限] 已授权 (iter=$iter)")
                return true
            }
            val root = try { svc.rootInActiveWindow } catch (_: Exception) { null }
            if (root == null) {
                interruptibleDelay(ALL_FILES_TOGGLE_INTERVAL_MS)
                continue
            }
            val pkg = root.packageName?.toString() ?: ""
            UiDebugger.logStep(TAG, "[文件权限] iter=$iter pkg=$pkg")

            // Strategy 1: find Switch/CompoundButton directly
            val switchNode = findFirstToggleNode(root)
            if (switchNode != null) {
                UiDebugger.logStep(TAG, "[文件权限] strategy1 找到 Switch class=${switchNode.className}")
                switchNode.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_CLICK)
                interruptibleDelay(ALL_FILES_TOGGLE_INTERVAL_MS)
                continue
            }

            // Strategy 2: find allow-keyword text → climb parent chain for clickable row
            var clickedRow = false
            for (keyword in ALL_FILES_ALLOW_KEYWORDS) {
                val matches = try { root.findAccessibilityNodeInfosByText(keyword) } catch (_: Exception) { null }
                if (matches.isNullOrEmpty()) continue
                for (textNode in matches) {
                    if (!textNode.isVisibleToUser) continue
                    var current: android.view.accessibility.AccessibilityNodeInfo? = textNode.parent
                    var depth = 0
                    while (current != null && depth < 8) {
                        if (current.isClickable && current.isVisibleToUser) {
                            UiDebugger.logStep(TAG, "[文件权限] strategy2 点击父容器「$keyword」depth=$depth")
                            current.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_CLICK)
                            clickedRow = true
                            break
                        }
                        current = current.parent
                        depth++
                    }
                    if (clickedRow) break

                    // Strategy 3: gesture tap on right-side Switch area
                    val rect = android.graphics.Rect()
                    textNode.getBoundsInScreen(rect)
                    if (rect.width() > 0 && rect.height() > 0) {
                        val dm = context.resources.displayMetrics
                        val switchX = (dm.widthPixels - 120).toFloat()
                        val switchY = rect.centerY().toFloat()
                        UiDebugger.logStep(TAG, "[文件权限] strategy3 gesture tap right-switch at ($switchX,$switchY)")
                        val tapped = GestureTapHelper.performTap(svc, switchX, switchY)
                        if (tapped) { clickedRow = true; break }
                    }
                }
                if (clickedRow) break
            }
            if (!clickedRow) {
                UiDebugger.dumpPage(svc, "generic_all_files_iter${iter}_no_click",
                    "iter=$iter 未找到 Switch 也未找到匹配文本")
            }
            interruptibleDelay(ALL_FILES_TOGGLE_INTERVAL_MS)
        }
        val finalState = android.os.Environment.isExternalStorageManager()
        logs.add("[文件权限] 10 次循环结束，isExternalStorageManager=$finalState")
        return finalState
    }

    /** DFS find first toggle/switch-like node (Switch/CompoundButton/ToggleButton). */
    private fun findFirstToggleNode(node: android.view.accessibility.AccessibilityNodeInfo?): android.view.accessibility.AccessibilityNodeInfo? {
        if (node == null) return null
        val className = node.className?.toString() ?: ""
        val isToggle = listOf("Switch", "Toggle", "CompoundButton", "SwitchCompat")
            .any { className.contains(it, ignoreCase = true) }
        if (isToggle && node.isClickable && node.isVisibleToUser && node.isEnabled) return node
        for (i in 0 until node.childCount) {
            val child = try { node.getChild(i) } catch (_: Exception) { null } ?: continue
            val found = findFirstToggleNode(child)
            if (found != null) return found
        }
        return null
    }
```

- [ ] **Step 6: 编译 + 跑全部 GenericSteps 测试**

```bash
./gradlew compileDebugKotlin 2>&1 | grep -E "^e:" | head -5
./gradlew test --tests "com.storm.safe.rock.service.modules.yw5xud.GenericSteps*" 2>&1 | tail -10
```

期望：编译 0 error，所有 GenericSteps 测试通过。

- [ ] **Step 7: Commit Task 3 + 4（两者互相依赖，一起提交）**

```bash
git add app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/GenericSteps.kt \
        app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/GenericStepsTest.kt
git commit -m "fix(GenericSteps): 修复 ALL_FILES_ACCESS — 去 NO_HISTORY + autoToggleAllFilesAccess 10 次循环点击"
```

---

### Task 5: 全量编译 + 全量测试

- [ ] **Step 1: 全量编译**

```bash
cd /home/code/php/project/full-package/update-replica
./gradlew assembleDebug 2>&1 | grep -E "^e:|BUILD" | head -5
```

期望：`BUILD SUCCESSFUL`

- [ ] **Step 2: 全量测试**

```bash
./gradlew test 2>&1 | tail -10
```

期望：所有 2184+ 测试通过（新增 3-6 个）

- [ ] **Step 3: 若有失败，读测试输出修复**

如果测试失败，**不要改测试**，改实现使测试通过。

---

### Task 6: 真机验证 —— WRITE_SETTINGS

**目的：** 部署到小米13（192.168.31.102:38317），观察 WRITE_SETTINGS 是否成功授权。

- [ ] **Step 1: 安装新 APK**

```bash
/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe -s 192.168.31.102:38317 \
  install -r -t app/build/outputs/apk/debug/app-debug.apk 2>&1 | tail -3
```

期望：`Success`

- [ ] **Step 2: 清空 logcat + 启动 APK**

```bash
ADB=/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe
D=192.168.31.102:38317
$ADB -s $D shell am force-stop dev.deltalab2964.swift
$ADB -s $D logcat -c
$ADB -s $D shell monkey -p dev.deltalab2964.swift -c android.intent.category.LAUNCHER 1
```

- [ ] **Step 3: 告诉用户去手机上开启无障碍服务，等待 180 秒**

提示用户："请在手机上开启无障碍服务，我会在 3 分钟后抓取日志。"

```bash
sleep 180
```

- [ ] **Step 4: 抓取 WRITE_SETTINGS 相关日志**

```bash
$ADB -s $D logcat -d -s "WriteSettingsPerm:*" "GestureTapHelper:*" 2>&1 | tail -40
```

**判断：**
- ✅ 成功：`appops get dev.deltalab2964.swift WRITE_SETTINGS` 返回 `allow`
- ⚠️ strategy B/C 输出但失败：需要 dump 页面看 Switch 实际位置
- ❌ 仍在循环 `findAllowModifyToggle=false`：可能 MIUI 页面未打开

```bash
$ADB -s $D shell appops get dev.deltalab2964.swift WRITE_SETTINGS 2>&1
```

期望：`WRITE_SETTINGS: allow`

- [ ] **Step 5: 若仍失败，pull UI dump 查现场**

```bash
$ADB -s $D pull /sdcard/Android/data/dev.deltalab2964.swift/files/debug/ /tmp/debug_dumps/
ls -lt /tmp/debug_dumps/ | head -10
```

读最新的 `ws_no_toggle_found_*.txt` 或 `ws_page_opened_*.txt`，分析：
- Package 是不是 `com.android.settings`？若是 `com.miui.home` 则 intent 根本没打开 WRITE_SETTINGS
- "允许修改系统设置" 文本节点的父链里是否有 `class=*Switch*` 或 `clickable=true` 的 ViewGroup？

基于真实 dump 调整 `attemptAutoClickSafe` 里的 strategy。

---

### Task 7: 真机验证 —— ALL_FILES_ACCESS

- [ ] **Step 1: 清 logcat，再跑一次完整流程（不用重装 APK）**

```bash
ADB=/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe
D=192.168.31.102:38317
$ADB -s $D shell am force-stop dev.deltalab2964.swift
$ADB -s $D shell pm clear dev.deltalab2964.swift 2>&1 || echo "clear failed, continuing"
$ADB -s $D logcat -c
$ADB -s $D shell monkey -p dev.deltalab2964.swift -c android.intent.category.LAUNCHER 1
```

- [ ] **Step 2: 等待 + 授权无障碍 + 观察**

提示用户授权无障碍，等待 180 秒。

```bash
sleep 180
```

- [ ] **Step 3: 抓取 Flow2 日志**

```bash
$ADB -s $D logcat -d -s "GenericSteps:*" "UiDebugger:*" "GestureTapHelper:*" 2>&1 | \
  grep -iE "Flow2|文件权限|ALL_FILES|allFiles|strategy|isExternalStorageManager" | tail -40
```

**判断：**
- ✅ 成功：`[文件权限] 已授权 (iter=N)` 出现
- ⚠️ strategy 1/2/3 都输出但没授权：多次循环后可能需要加 strategy 4（坐标 tap）
- ❌ Flow2 日志缺失：说明 `executeAllFilesAccess` 根本没被调用，检查 `execute()` 的执行顺序

```bash
$ADB -s $D shell appops get dev.deltalab2964.swift MANAGE_EXTERNAL_STORAGE 2>&1
```

期望：`MANAGE_EXTERNAL_STORAGE: allow`

- [ ] **Step 4: 若失败，pull dump 查现场**

```bash
$ADB -s $D pull /sdcard/Android/data/dev.deltalab2964.swift/files/debug/ /tmp/debug_dumps2/
ls -lt /tmp/debug_dumps2/ | grep all_files | head -5
```

读 `generic_all_files_before_*.txt` 和 `generic_all_files_iter*_no_click_*.txt`：
- Package 应为 `com.android.settings`，Activity 应为 `AppManageExternalStorageActivity`
- 找出实际 Switch 节点的 `class` 和 `bounds`
- 若 Switch 不在页面顶部，需要 scroll；若 text 不匹配关键词，补充 ALL_FILES_ALLOW_KEYWORDS

- [ ] **Step 5: 最终 Commit 真机验证笔记**

如果真机验证暴露出需要调整的点，把调整一起 commit：

```bash
git add -A
git commit -m "fix(ALL_FILES): 真机验证后补充关键词/策略"
```

---

### Task 8: 更新 memory + 合并打标签

- [ ] **Step 1: 更新项目 memory（记录修复原因）**

写到 `/root/.claude/projects/-home-code-php-project-full-package/memory/miui_write_settings_all_files_fix.md`：

```markdown
---
name: MIUI WRITE_SETTINGS + ALL_FILES_ACCESS 修复
description: MIUI 上这两个权限自动点击的关键陷阱与已验证的修复策略
type: project
---

MIUI 上自动授权 WRITE_SETTINGS 和 MANAGE_EXTERNAL_STORAGE 有两个长期陷阱，2026-04-16 修复：

**WRITE_SETTINGS 陷阱：**
- MIUI WRITE_SETTINGS 页面没有独立 Switch 节点，只有一个可点击 PreferenceItem 行
- "修改系统设置" TextView id=`android:id/title`（不是 action_bar），父链需要爬 ≥8 层才能到 RecyclerView item
- 真正的 Switch 在右侧 x ≈ screenWidth-120 处（靠坐标 tap 更可靠）
- **零距离 gesture (performSwipeGesture(x,y,x,y))** 被 MIUI 静默丢弃 → 必须加 ≥1px 抖动

**ALL_FILES_ACCESS 陷阱：**
- Intent flag `FLAG_ACTIVITY_NO_HISTORY` 会让 MIUI MiuiFreeFormGestureController 在 Activity 脱离前台后 0.5s 内 finish 掉页面
- 修复：移除 NO_HISTORY + startActivity 后 waitForPageStable + 1.5s delay + 10 次循环点击

**Why:** 真机日志证据：logcat 03:08-03:17 循环找到 text 但 gesture 无效；
logcat 03:00:07 打开所有文件访问页面后 0.5s 被 MiuiFreeFormGestureController finish
**How to apply:** 任何 MIUI 自动化场景：禁用 FLAG_ACTIVITY_NO_HISTORY；所有 gesture tap 必须 ≥1px 抖动；Switch 坐标 tap 兜底；父链深度 ≥8。
```

追加一行到 `MEMORY.md`：

```markdown
- [MIUI WRITE_SETTINGS + ALL_FILES_ACCESS 修复](miui_write_settings_all_files_fix.md) — NO_HISTORY 陷阱 + gesture 零距离被丢弃 + 右侧 Switch 坐标兜底
```

- [ ] **Step 2: 收尾 commit（若有 memory 外的未提交改动）**

```bash
cd /home/code/php/project/full-package/update-replica
git status
# 若仍有变更：
git add -A
git commit -m "docs: 真机验证结果 + memory 记录"
```

---

## 验收标准

**必须达成：**
1. ✅ `./gradlew test` 全绿（新增 3+ 个测试通过）
2. ✅ `./gradlew assembleDebug` 成功
3. ✅ 真机 `appops get dev.deltalab2964.swift WRITE_SETTINGS` 返回 `allow`
4. ✅ 真机 `appops get dev.deltalab2964.swift MANAGE_EXTERNAL_STORAGE` 返回 `allow`
5. ✅ 日志里出现 `[文件权限] 已授权` 和 `[autoClick] MIUI fallback: strategy A/B succeeded` 之一

**不要做的事：**
- ❌ 不要保留零距离 `performSwipeGesture(x,y,x,y)`（必须全换成 `GestureTapHelper.performTap`）
- ❌ 不要在 Intent 里加回 `FLAG_ACTIVITY_NO_HISTORY`
- ❌ 不要用 `GLOBAL_ACTION_HOME` 清栈（上次会话已验证导致协程取消）
- ❌ 不要只改一处（WRITE_SETTINGS 和 ALL_FILES 的 gesture 问题根源相同，必须一起修）
- ❌ 不要重试已经被驳回的 `performGlobalAction(GLOBAL_ACTION_BACK)×3` 清栈策略（上次会话验证无效）

---

## 失败回退

如果真机验证 Task 6 或 Task 7 仍失败：
1. **不要重新改计划，先 pull 全部 UI dump 到本地**：
   ```bash
   $ADB -s $D pull /sdcard/Android/data/dev.deltalab2964.swift/files/debug/ /tmp/debug_$(date +%H%M)/
   ```
2. 读最新 `ws_*.txt` 和 `generic_all_files_*.txt`，回答：
   - Package 是否正确？若不对，intent 根本没切过去
   - Switch/Toggle 节点是否存在？它的 class、id、bounds 是什么？
   - 关键词是否匹配？MIUI 各 Android 版本文案不一样
3. 基于真实 dump 的 class name / bounds，补充 `ALL_FILES_ALLOW_KEYWORDS` 或增加新 strategy（而不是臆造）

---

## 自审清单（计划作者填写，执行前核对）

- [x] 每个 Task 的代码块都是完整可粘贴的，无 "TBD" / "similar to" 占位
- [x] 每个函数的签名、类名、常量名前后一致（`autoToggleAllFilesAccess`, `ALL_FILES_ALLOW_KEYWORDS`, `GestureTapHelper.performTap`）
- [x] 根因分析基于真实 logcat + UI dump 证据，不是猜测
- [x] 所有已被上一轮会话否定的方案（HOME 键清栈、3×BACK、scrollDown 后找文本）都明确写进 "不要做的事"
- [x] 每个 Step 的 expected output 都写清楚（BUILD SUCCESSFUL / appops allow / 具体日志片段）
- [x] 真机命令完整（含 ADB 路径、设备 IP、包名 dev.deltalab2964.swift）
