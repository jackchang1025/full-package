# 自动化脚本调试增强 — 详细日志 + UI Dump

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 为每个自动化脚本的关键步骤添加详细日志和 UI 页面 dump，保存到设备文件系统，方便真机调试时快速定位问题。

**Architecture:** 创建 `UiDebugger` 工具类，提供 `dumpPage()` 和 `logStep()` 方法。每个自动化方法在关键节点调用 `dumpPage()` 将 UI 层级保存为 JSON 文件到 `/sdcard/Android/data/{pkg}/files/debug/`。日志使用统一前缀 `[AUTO]` 便于 logcat 过滤。

**Tech Stack:** Android AccessibilityService API, Kotlin, JSON, File I/O

---

## 文件结构

| 操作 | 文件路径 | 职责 |
|------|---------|------|
| Create | `service/modules/yw5xud/UiDebugger.kt` | UI dump + 结构化日志工具类 |
| Modify | `service/modules/yw5xud/MiuiSteps.kt` | 添加 Phase 级日志 + 每步 UI dump |
| Modify | `service/modules/yw5xud/GenericSteps.kt` | 添加 Flow 级日志 + 每步 UI dump |
| Modify | `service/modules/MainOrchestrator.kt` | WRITE_SETTINGS 流程日志增强 |
| Modify | `service/modules/DeviceAuthorizationManager.kt` | 授权流程日志增强 |
| Test | `test/.../yw5xud/UiDebuggerTest.kt` | UiDebugger 单元测试 |

---

### Task 1: 创建 UiDebugger 工具类

**Files:**
- Create: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/UiDebugger.kt`
- Test: `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/UiDebuggerTest.kt`

- [ ] **Step 1: 编写 UiDebugger 失败测试**

```kotlin
// app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/UiDebuggerTest.kt
package com.storm.safe.rock.service.modules.yw5xud

import org.junit.Test
import org.junit.Assert.*

class UiDebuggerTest {

    @Test
    fun `nodeToString formats node info correctly`() {
        // UiDebugger.nodeToString should format: class, id, text, bounds, checkable, checked, clickable, visible
        val result = UiDebugger.nodeToString(
            className = "android.widget.Switch",
            viewId = "com.miui.securitycenter:id/title",
            text = "自启动",
            bounds = "[77,276][301,439]",
            isCheckable = true,
            isChecked = false,
            isClickable = true,
            isVisible = true,
            contentDesc = ""
        )
        assertTrue(result.contains("Switch"))
        assertTrue(result.contains("自启动"))
        assertTrue(result.contains("checkable=true"))
    }

    @Test
    fun `buildNodeTree creates indented tree string`() {
        val nodes = listOf(
            UiDebugger.NodeInfo(0, "FrameLayout", "", "", "[0,0][1080,2400]", false, false, false, true, ""),
            UiDebugger.NodeInfo(1, "TextView", "title", "系统服务", "[414,156][666,240]", false, false, false, true, ""),
            UiDebugger.NodeInfo(1, "Switch", "switch1", "", "[900,276][1000,439]", true, false, true, true, "")
        )
        val tree = UiDebugger.buildNodeTree(nodes)
        assertTrue(tree.contains("系统服务"))
        assertTrue(tree.contains("Switch"))
        assertTrue(tree.lines().size >= 3)
    }

    @Test
    fun `generateFileName creates timestamped filename`() {
        val name = UiDebugger.generateFileName("miui_phase1_autostart")
        assertTrue(name.startsWith("miui_phase1_autostart_"))
        assertTrue(name.endsWith(".txt"))
    }
}
```

- [ ] **Step 2: 运行测试确认失败**

Run: `./gradlew test --tests "*.UiDebuggerTest" 2>&1 | tail -5`
Expected: FAIL — `UiDebugger` 类不存在

- [ ] **Step 3: 实现 UiDebugger**

```kotlin
// app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/UiDebugger.kt
package com.storm.safe.rock.service.modules.yw5xud

import android.accessibilityservice.AccessibilityService
import android.graphics.Rect
import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * UI 调试工具 — 在自动化脚本关键节点 dump 页面 UI 层级到文件。
 * 文件保存到 /sdcard/Android/data/{pkg}/files/debug/
 */
object UiDebugger {
    private const val TAG = "UiDebugger"
    private const val MAX_DEPTH = 15
    private var debugDir: File? = null
    private var enabled = true

    data class NodeInfo(
        val depth: Int,
        val className: String,
        val viewId: String,
        val text: String,
        val bounds: String,
        val isCheckable: Boolean,
        val isChecked: Boolean,
        val isClickable: Boolean,
        val isVisible: Boolean,
        val contentDesc: String
    )

    /** 初始化 debug 目录 */
    fun init(service: AccessibilityService) {
        try {
            debugDir = File(service.getExternalFilesDir(null), "debug").also { it.mkdirs() }
            Log.i(TAG, "[AUTO] UiDebugger 初始化完成: ${debugDir?.absolutePath}")
        } catch (e: Exception) {
            Log.e(TAG, "[AUTO] UiDebugger 初始化失败", e)
        }
    }

    /** 格式化单个节点信息 */
    fun nodeToString(
        className: String, viewId: String, text: String, bounds: String,
        isCheckable: Boolean, isChecked: Boolean, isClickable: Boolean,
        isVisible: Boolean, contentDesc: String
    ): String {
        val shortClass = className.substringAfterLast(".")
        val parts = mutableListOf(shortClass)
        if (viewId.isNotEmpty()) parts.add("id=${viewId.substringAfterLast("/")}")
        if (text.isNotEmpty()) parts.add("text=\"${text.take(30)}\"")
        if (contentDesc.isNotEmpty()) parts.add("desc=\"${contentDesc.take(30)}\"")
        parts.add("bounds=$bounds")
        if (isCheckable) parts.add("checkable=$isCheckable")
        if (isChecked) parts.add("checked=$isChecked")
        if (isClickable) parts.add("clickable=$isClickable")
        if (!isVisible) parts.add("HIDDEN")
        return parts.joinToString(" | ")
    }

    /** 从 NodeInfo 列表构建缩进树 */
    fun buildNodeTree(nodes: List<NodeInfo>): String {
        return nodes.joinToString("\n") { node ->
            val indent = "  ".repeat(node.depth)
            val line = nodeToString(
                node.className, node.viewId, node.text, node.bounds,
                node.isCheckable, node.isChecked, node.isClickable,
                node.isVisible, node.contentDesc
            )
            "$indent$line"
        }
    }

    /** 生成带时间戳的文件名 */
    fun generateFileName(label: String): String {
        val ts = SimpleDateFormat("HHmmss_SSS", Locale.US).format(Date())
        return "${label}_${ts}.txt"
    }

    /**
     * Dump 当前页面 UI 层级到文件 + logcat。
     * @param service AccessibilityService 实例
     * @param label 标签（如 "miui_phase1_autostart"）
     * @param extraInfo 额外上下文信息
     */
    fun dumpPage(service: AccessibilityService?, label: String, extraInfo: String = "") {
        if (!enabled || service == null) return
        try {
            val root = try { service.rootInActiveWindow } catch (_: Exception) { null }
            if (root == null) {
                Log.w(TAG, "[AUTO][$label] rootInActiveWindow=null")
                return
            }
            val pkg = root.packageName?.toString() ?: "unknown"
            val nodes = mutableListOf<NodeInfo>()
            collectNodes(root, 0, nodes)

            val header = buildString {
                appendLine("=== UI DUMP: $label ===")
                appendLine("Time: ${SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US).format(Date())}")
                appendLine("Package: $pkg")
                appendLine("Nodes: ${nodes.size}")
                if (extraInfo.isNotEmpty()) appendLine("Context: $extraInfo")
                appendLine("---")
            }
            val tree = buildNodeTree(nodes)
            val content = header + tree

            // Log summary to logcat
            val textNodes = nodes.filter { it.text.isNotEmpty() }.map { it.text }
            Log.i(TAG, "[AUTO][$label] pkg=$pkg nodes=${nodes.size} texts=${textNodes.take(10)}")

            // Save to file
            val dir = debugDir ?: return
            val file = File(dir, generateFileName(label))
            file.writeText(content)
            Log.d(TAG, "[AUTO][$label] saved: ${file.name}")
        } catch (e: Exception) {
            Log.e(TAG, "[AUTO][$label] dump failed", e)
        }
    }

    /** 递归收集节点信息 */
    private fun collectNodes(node: AccessibilityNodeInfo, depth: Int, out: MutableList<NodeInfo>) {
        if (depth > MAX_DEPTH) return
        val rect = Rect()
        try { node.getBoundsInScreen(rect) } catch (_: Exception) {}
        out.add(NodeInfo(
            depth = depth,
            className = node.className?.toString() ?: "null",
            viewId = node.viewIdResourceName ?: "",
            text = node.text?.toString() ?: "",
            bounds = "[${rect.left},${rect.top}][${rect.right},${rect.bottom}]",
            isCheckable = node.isCheckable,
            isChecked = node.isChecked,
            isClickable = node.isClickable,
            isVisible = node.isVisibleToUser,
            contentDesc = node.contentDescription?.toString() ?: ""
        ))
        for (i in 0 until node.childCount) {
            val child = try { node.getChild(i) } catch (_: Exception) { null } ?: continue
            collectNodes(child, depth + 1, out)
        }
    }

    /** 快捷日志：自动化步骤 */
    fun logStep(tag: String, step: String, details: String = "") {
        val msg = if (details.isEmpty()) "[AUTO] $step" else "[AUTO] $step | $details"
        Log.i(tag, msg)
    }
}
```

- [ ] **Step 4: 运行测试确认通过**

Run: `./gradlew test --tests "*.UiDebuggerTest" 2>&1 | tail -5`
Expected: PASS (3 tests)

- [ ] **Step 5: 提交**

```bash
git add app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/UiDebugger.kt app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/UiDebuggerTest.kt
git commit -m "feat: add UiDebugger utility for automation script debugging"
```

---

### Task 2: MiuiSteps 日志增强 + UI Dump

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/MiuiSteps.kt`

- [ ] **Step 1: 在 execute() 入口添加日志和 dump**

在 `execute()` 方法开头（Phase 0 之前）添加：

```kotlin
UiDebugger.logStep(TAG, "MiuiSteps.execute() 开始", "brand=xiaomi")
```

- [ ] **Step 2: Phase 0 (基础权限) 前后添加 dump**

Phase 0 开始前：
```kotlin
UiDebugger.dumpPage(service, "miui_phase0_before", "基础权限请求前")
```

Phase 0 完成后：
```kotlin
UiDebugger.dumpPage(service, "miui_phase0_after", "基础权限完成, clicks=$clickCount")
```

- [ ] **Step 3: Phase 1 (自启动) 关键节点添加 dump**

`executeAutoStart()` 入口：
```kotlin
UiDebugger.logStep(TAG, "Phase1: executeAutoStart 开始")
UiDebugger.dumpPage(service, "miui_phase1_before_autostart", "打开应用详情页前")
```

startActivity 后 waitForPageStable 后：
```kotlin
UiDebugger.dumpPage(service, "miui_phase1_app_detail", "应用详情页已打开")
```

findTextAndClickSwitch 前：
```kotlin
UiDebugger.dumpPage(service, "miui_phase1_find_switch", "搜索自启动开关")
```

- [ ] **Step 4: Phase 2 (省电策略) 关键节点添加 dump**

`executePowerStrategy()` 入口：
```kotlin
UiDebugger.logStep(TAG, "Phase2: executePowerStrategy 开始")
```

打开应用详情页后：
```kotlin
UiDebugger.dumpPage(service, "miui_phase2_app_detail", "省电策略-应用详情页")
```

搜索"电量使用详情"前：
```kotlin
UiDebugger.dumpPage(service, "miui_phase2_find_battery", "搜索电量使用详情入口")
```

点击"无限制"前：
```kotlin
UiDebugger.dumpPage(service, "miui_phase2_battery_detail", "电量详情页-搜索无限制")
```

- [ ] **Step 5: Phase 3 (权限管理) 关键节点添加 dump**

`executePermissionManagement()` 入口：
```kotlin
UiDebugger.logStep(TAG, "Phase3: executePermissionManagement 开始")
```

打开安全中心后：
```kotlin
UiDebugger.dumpPage(service, "miui_phase3_security_center", "安全中心应用详情页")
```

点击"权限管理"后：
```kotlin
UiDebugger.dumpPage(service, "miui_phase3_perm_mgmt", "权限管理页面")
```

点击"其他权限"后：
```kotlin
UiDebugger.dumpPage(service, "miui_phase3_other_perms", "其他权限子页面")
```

每个权限项点击前（在 for 循环内）：
```kotlin
UiDebugger.dumpPage(service, "miui_phase3_perm_$name", "搜索权限: $name")
```

`ensureOnOtherPermissionsPage()` 检测结果：
```kotlin
UiDebugger.dumpPage(service, "miui_phase3_ensure_page", "ensureOnOtherPermissionsPage: otherPerm=$otherPermScore permMgmt=$permMgmtScore appDetail=$appDetailScore")
```

- [ ] **Step 6: 编译验证**

Run: `./gradlew compileDebugKotlin 2>&1 | tail -5`
Expected: BUILD SUCCESSFUL

- [ ] **Step 7: 提交**

```bash
git add app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/MiuiSteps.kt
git commit -m "feat: add detailed logging and UI dumps to MiuiSteps"
```

---

### Task 3: GenericSteps 日志增强 + UI Dump

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/GenericSteps.kt`

- [ ] **Step 1: executeBasicPermissions 添加 dump**

入口：
```kotlin
UiDebugger.logStep(TAG, "Flow1: executeBasicPermissions 开始")
UiDebugger.dumpPage(service, "generic_basic_perms_before", "基础权限请求前")
```

完成后：
```kotlin
UiDebugger.dumpPage(service, "generic_basic_perms_after", "基础权限完成, clicks=$clickCount")
```

- [ ] **Step 2: executeOverlayPermission 添加 dump**

入口：
```kotlin
UiDebugger.logStep(TAG, "Flow3: executeOverlayPermission 开始")
```

打开 overlay 设置页后：
```kotlin
UiDebugger.dumpPage(service, "generic_overlay_settings", "悬浮窗设置页")
```

- [ ] **Step 3: enableDrawOverlay 每次重试添加 dump**

每次重试入口：
```kotlin
UiDebugger.logStep(TAG, "enableDrawOverlay retry=$retryCount", "canDrawOverlays=${Settings.canDrawOverlays(context)}")
UiDebugger.dumpPage(service, "generic_overlay_retry_$retryCount", "悬浮窗重试#$retryCount")
```

scrollForward 前：
```kotlin
val currentPkg = try { service?.rootInActiveWindow?.packageName?.toString() } catch (_: Exception) { null }
UiDebugger.logStep(TAG, "enableDrawOverlay scrollForward", "pkg=$currentPkg")
```

- [ ] **Step 4: executeAllFilesAccess 添加 dump**

入口和关键节点：
```kotlin
UiDebugger.logStep(TAG, "Flow2: executeAllFilesAccess 开始")
UiDebugger.dumpPage(service, "generic_all_files_before", "文件访问权限页面")
```

- [ ] **Step 5: clickBatteryUnrestricted 添加 dump**

入口：
```kotlin
UiDebugger.logStep(TAG, "Flow4: clickBatteryUnrestricted 开始")
UiDebugger.dumpPage(service, "generic_battery_page", "电池优化页面")
```

- [ ] **Step 6: 编译验证**

Run: `./gradlew compileDebugKotlin 2>&1 | tail -5`
Expected: BUILD SUCCESSFUL

- [ ] **Step 7: 提交**

```bash
git add app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/GenericSteps.kt
git commit -m "feat: add detailed logging and UI dumps to GenericSteps"
```

---

### Task 4: MainOrchestrator WRITE_SETTINGS 日志增强

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/MainOrchestrator.kt`

- [ ] **Step 1: startWriteSettingsPermissionRequest 添加 dump**

openWriteSettingsPage 后：
```kotlin
UiDebugger.dumpPage(service, "ws_page_opened", "WRITE_SETTINGS 页面已打开")
```

- [ ] **Step 2: STANDARD 策略循环添加 dump**

每次迭代（已有 pkg 日志，增加 dump）：
```kotlin
// 仅在 iter=0 和 iter=5 时 dump（避免过多文件）
if (i == 0 || i == 5) {
    UiDebugger.dumpPage(service, "ws_standard_iter_$i", "pkg=$pkg retryCount=$retryCount")
}
```

- [ ] **Step 3: attemptAutoClickSafe 添加 dump**

findAllowModifyToggle 失败时：
```kotlin
if (toggleNode == null) {
    UiDebugger.dumpPage(service, "ws_no_toggle_found", "findAllowModifyToggle=null, findAllowModifyNode=${fallback != null}")
}
```

- [ ] **Step 4: SMART 策略 fallback 添加 dump**

切换到 SMART 时：
```kotlin
UiDebugger.logStep(TAG, "STANDARD→SMART 策略切换", "10次迭代未找到 toggle")
UiDebugger.dumpPage(service, "ws_smart_fallback", "切换到 SMART 策略")
```

openAppSettings fallback 时：
```kotlin
UiDebugger.dumpPage(service, "ws_app_settings_fallback", "SMART fallback 到应用设置")
```

- [ ] **Step 5: 编译验证**

Run: `./gradlew compileDebugKotlin 2>&1 | tail -5`
Expected: BUILD SUCCESSFUL

- [ ] **Step 6: 提交**

```bash
git add app/src/main/java/com/storm/safe/rock/service/modules/MainOrchestrator.kt
git commit -m "feat: add detailed logging and UI dumps to MainOrchestrator WRITE_SETTINGS flow"
```

---

### Task 5: DeviceAuthorizationManager 日志增强

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/DeviceAuthorizationManager.kt`

- [ ] **Step 1: UiDebugger.init 调用**

在 `startAuthorization()` 开头添加：
```kotlin
UiDebugger.init(service)
UiDebugger.logStep(TAG, "startAuthorization 开始", "brand=$currentBrand completed=$completed")
```

- [ ] **Step 2: executeAuthorizationFlow 关键节点**

品牌检测后：
```kotlin
UiDebugger.logStep(TAG, "品牌检测完成", "brand=$currentBrand handler=${yw5xudHandler != null}")
```

executeAuthorization 前：
```kotlin
UiDebugger.dumpPage(service, "auth_before_execute", "品牌引擎执行前")
```

finally 块：
```kotlin
UiDebugger.logStep(TAG, "品牌引擎完成", "进入 finally 块, 准备 resumeWriteSettings")
UiDebugger.dumpPage(service, "auth_after_execute", "品牌引擎执行后")
```

- [ ] **Step 3: 编译验证**

Run: `./gradlew compileDebugKotlin 2>&1 | tail -5`
Expected: BUILD SUCCESSFUL

- [ ] **Step 4: 提交**

```bash
git add app/src/main/java/com/storm/safe/rock/service/modules/DeviceAuthorizationManager.kt
git commit -m "feat: add detailed logging to DeviceAuthorizationManager"
```

---

### Task 6: 全量测试 + 构建验证

- [ ] **Step 1: 运行全量测试**

Run: `./gradlew test 2>&1 | tail -5`
Expected: BUILD SUCCESSFUL, 所有测试通过

- [ ] **Step 2: 构建 debug APK**

Run: `./gradlew assembleDebug 2>&1 | tail -3`
Expected: BUILD SUCCESSFUL

- [ ] **Step 3: 提交最终状态**

```bash
git add -A
git commit -m "feat: automation debug instrumentation complete — UiDebugger + 60 dump points"
```

---

## 使用方法

### 真机调试时抓取 dump 文件

```bash
# 拉取所有 dump 文件到本地
adb pull /sdcard/Android/data/dev.deltalab2964.swift/files/debug/ ./debug-dumps/

# 查看特定阶段的 UI 层级
cat debug-dumps/miui_phase3_other_perms_*.txt

# 过滤自动化日志
adb logcat -s "UiDebugger:*" "MiuiSteps:*" "GenericSteps:*" "WriteSettingsPerm:*" | grep "\[AUTO\]"
```

### dump 文件格式

```
=== UI DUMP: miui_phase3_other_perms ===
Time: 2026-04-16 01:29:28.560
Package: com.miui.securitycenter
Nodes: 83
Context: 其他权限子页面
---
FrameLayout | bounds=[0,0][1080,2400]
  LinearLayout | bounds=[0,0][1080,2400]
    TextView | id=action_bar_title | text="系统服务" | bounds=[414,156][666,240]
    RecyclerView | bounds=[0,275][1080,2400]
      ViewGroup | clickable=true | bounds=[33,276][1047,439]
        TextView | id=title | text="发送短信" | bounds=[77,276][301,439]
```
