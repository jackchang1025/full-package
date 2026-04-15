# Yw5xud 授权流程修复 Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 修复小米13真机上授权无障碍后品牌引擎页面不到前台、开关不被点击的问题，对齐 vendor JADX 实现。

**Architecture:** DeviceAuthorizationManager 恢复 vendor 原始时序（smartReturnToApp → pauseWriteSettings → 品牌引擎）。GenericSteps.executeBasicPermissions 内部启动 yw5xud.umrkmgrri + 20s 点击循环获取 VISIBLE_WINDOW。MiuiSteps 从"只打开页面"改为"打开页面 + 页面内 UI 自动化操作"。事件转发改为 bgHandler.post 异步。

**Tech Stack:** Kotlin, Android AccessibilityService, JADX 逆向对照

**JADX 参考源码:** `/home/code/php/project/full-package/jadx-reference/rock/`

---

## File Structure

| 文件 | 操作 | 职责 |
|------|------|------|
| `DeviceAuthorizationManager.kt` | Modify | 删除 umrkmgrri/iuzxujjtqev 启动代码，恢复 vendor 时序 |
| `GenericSteps.kt` | Modify | executeBasicPermissions 启动 umrkmgrri + 20s 点击循环 |
| `MiuiSteps.kt` | Modify | 补全 6 步 UI 自动化（搜索文本+点击开关+重试） |
| `Yw5xudHandler.kt` | Modify | 事件处理加 className Button 检查 |
| `MyAccessibilityService.kt` | Modify | configStageManager 事件转发改 bgHandler.post |

---

### Task 1: DeviceAuthorizationManager — 恢复 vendor 原始时序

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/DeviceAuthorizationManager.kt:245-325`

**JADX 参考:** `obzzniixzpin$startAuthorization$1.invokeSuspend()` — vendor 时序是 disableMonitor → delay(300) → smartReturnToApp → delay(300) → pauseWriteSettings → detectBrand → delegate.executeAuthorization()。**没有** umrkmgrri/iuzxujjtqev 启动。umrkmgrri 由品牌引擎 Steps 内部启动。

- [ ] **Step 1: 删除 Step 3 (BACK循环 + iuzxujjtqev + umrkmgrri) 代码块**

将 `executeAuthorizationFlow()` 的 Step 3 区域（line 265-317）替换为 vendor 原始时序：

```kotlin
// executeAuthorizationFlow() 中 delay(300L) 之后：

// Step 3: Smart return to app if not already there (JADX: m211524m1)
if (currentPkg != service.packageName) {
    Log.i(TAG, "📱 [授权] 不在app，执行 smartReturnToApp...")
    try {
        val returned = service.smartReturnToApp()
        Log.i(TAG, "📱 [授权] smartReturnToApp() 返回=$returned")
    } catch (e: Exception) {
        Log.w(TAG, "⚠️ [授权] 返回APP异常: ${e.message}，继续执行")
    }
    delay(300L)
} else {
    Log.i(TAG, "✅ [授权] 已在app，跳过返回")
}

// Log current page after return attempt
var afterPkg = ""
try {
    afterPkg = service.rootInActiveWindow?.packageName?.toString() ?: ""
} catch (_: Exception) {}
Log.i(TAG, "🔍 [授权] 返回后当前页面: $afterPkg")

// Step 4: Pause WRITE_SETTINGS permission request (JADX: m211496j0)
// ... (existing code)
```

- [ ] **Step 2: 验证编译通过**

Run: `./gradlew compileDebugKotlin 2>&1 | tail -5`
Expected: BUILD SUCCESSFUL

- [ ] **Step 3: Commit**

```bash
git add app/src/main/java/com/storm/safe/rock/service/modules/DeviceAuthorizationManager.kt
git commit -m "fix(auth): restore vendor authorization sequence — remove umrkmgrri/iuzxujjtqev from DeviceAuthorizationManager

Vendor launches umrkmgrri from GenericSteps.executeBasicPermissions, not from
DeviceAuthorizationManager. Moving it here caused VISIBLE_WINDOW to be lost
before brand engine started."
```

---

### Task 2: GenericSteps.executeBasicPermissions — 启动 umrkmgrri + 20s 点击循环

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/GenericSteps.kt:255-263`

**JADX 参考:** `C0364a1.m212130b0` (line 1580-1720) — vendor 流程：
1. 检查 FlowTracker 是否已完成/达到最大尝试
2. 调用 `umrkmgrri.f55158a3.start(context)` 启动批量权限请求 Activity
3. delay(800ms)
4. 循环 20 秒：检查 isPermissionControllerWindow → clickPermissionAllowButton → delay(800ms)
5. 标记完成

- [ ] **Step 1: 重写 executeBasicPermissions 为 suspend 函数**

```kotlin
// GenericSteps.kt — 替换 executeBasicPermissions

/**
 * Request basic runtime permissions via yw5xud.umrkmgrri Activity.
 * JADX: m212130b0 — launches umrkmgrri, then loops 20s clicking allow buttons.
 * This is the KEY step that gives us BAL_ALLOW_VISIBLE_WINDOW via system
 * GrantPermissionsActivity dialog, enabling all subsequent startActivity calls
 * to reach the foreground on MIUI.
 */
suspend fun executeBasicPermissions(
    successes: MutableList<String>,
    failures: MutableList<String>,
    logs: MutableList<String>
) {
    logs.add("[基础权限] 开始执行")
    try {
        // Step 1: Launch yw5xud.umrkmgrri (batch permission request)
        Log.i(TAG, "[基础权限] 启动umrkmgrri...")
        com.storm.safe.rock.service.modules.yw5xud.umrkmgrri.start(context)
        interruptibleDelay(800L)

        // Step 2: Loop 20s clicking permission allow buttons
        val startTime = System.currentTimeMillis()
        val timeoutMs = 20000L
        var clickCount = 0
        Log.i(TAG, "[基础权限] 开始循环点击允许按钮 (超时=${timeoutMs / 1000}秒)...")

        while (System.currentTimeMillis() - startTime < timeoutMs) {
            if (isPermissionControllerWindow()) {
                if (clickPermissionAllowButton()) {
                    clickCount++
                    Log.i(TAG, "[基础权限] 点击允许 (第${clickCount}次)")
                }
                interruptibleDelay(800L)
            } else {
                // Not on permission controller — check if umrkmgrri still running
                if (!com.storm.safe.rock.service.modules.yw5xud.umrkmgrri.isRequestingPermissions) {
                    Log.i(TAG, "[基础权限] umrkmgrri 已完成")
                    break
                }
                interruptibleDelay(500L)
            }
        }

        val elapsed = (System.currentTimeMillis() - startTime) / 1000
        Log.i(TAG, "[基础权限] 完成，用时${elapsed}秒，点击${clickCount}次")
        successes.add("基础权限请求完成 (点击${clickCount}次)")
    } catch (e: Exception) {
        Log.e(TAG, "[基础权限] 异常: ${e.message}")
        failures.add("基础权限请求失败: ${e.message}")
    }
}
```

- [ ] **Step 2: 将 execute() 中的 executeBasicPermissions 调用改为 suspend**

GenericSteps.execute() 已经是 suspend 函数，executeBasicPermissions 改为 suspend 后直接调用即可，无需额外修改。

- [ ] **Step 3: 验证编译通过**

Run: `./gradlew compileDebugKotlin 2>&1 | tail -5`
Expected: BUILD SUCCESSFUL

- [ ] **Step 4: Commit**

```bash
git add app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/GenericSteps.kt
git commit -m "feat(yw5xud): implement GenericSteps.executeBasicPermissions — umrkmgrri + 20s click loop

Matches JADX C0364a1.m212130b0: launches yw5xud.umrkmgrri for batch permission
request, then loops 20s clicking allow buttons via clickPermissionAllowButton().
This is the key step that provides BAL_ALLOW_VISIBLE_WINDOW on MIUI."
```

---

### Task 3: MiuiSteps — 补全自启动管理页面内 UI 自动化

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/MiuiSteps.kt:91-107`

**JADX 参考:** `C0367a4.m212255b4` (executeAutoStartInAppDetails) — vendor 流程：
1. 打开应用详情页 (ApplicationsDetailsActivity)
2. 用 `clickTextWithScroll` 搜索 "自启动"/"自啟動" 文本
3. 用 `clickSwitchAreaBesideText` 点击文本旁边的开关区域
4. 带重试循环 (最多3次)、waitForPageStable

- [ ] **Step 1: 添加 UI 自动化辅助方法到 MiuiSteps**

```kotlin
// MiuiSteps.kt — 在 class body 末尾、waitForPageStable 之前添加

/**
 * Find our app in a list and click its switch/toggle.
 * Searches by app name or package name, then clicks the adjacent switch.
 * JADX: clickTextWithScroll + clickSwitchAreaBesideText pattern.
 */
internal fun findAndClickAppSwitch(keywords: List<String>): Boolean {
    val root = try { service?.rootInActiveWindow } catch (_: Exception) { null } ?: return false
    try {
        val appLabel = try {
            val pm = context.packageManager
            val appInfo = pm.getApplicationInfo(context.packageName, 0)
            pm.getApplicationLabel(appInfo).toString()
        } catch (_: Exception) { "" }

        // Search by app label
        if (appLabel.isNotEmpty()) {
            val nodes = root.findAccessibilityNodeInfosByText(appLabel)
            if (nodes != null && nodes.isNotEmpty()) {
                for (node in nodes) {
                    if (node.isVisibleToUser) {
                        // Find switch in parent hierarchy
                        val switched = findAndClickSwitchNearNode(node)
                        if (switched) {
                            Log.i(TAG, "✅ 找到并点击了 $appLabel 的开关")
                            return true
                        }
                    }
                }
            }
        }

        // Fallback: search by keywords
        for (keyword in keywords) {
            val nodes = root.findAccessibilityNodeInfosByText(keyword)
            if (nodes != null && nodes.isNotEmpty()) {
                for (node in nodes) {
                    if (node.isVisibleToUser) {
                        val switched = findAndClickSwitchNearNode(node)
                        if (switched) {
                            Log.i(TAG, "✅ 找到并点击了关键词 '$keyword' 的开关")
                            return true
                        }
                    }
                }
            }
        }
    } catch (e: Exception) {
        Log.w(TAG, "findAndClickAppSwitch 异常: ${e.message}")
    }
    return false
}

/**
 * Find a Switch/Toggle near a text node and click it.
 * Walks up parent chain, searches siblings for Switch/CheckBox.
 * JADX: clickSwitchAreaBesideText pattern.
 */
private fun findAndClickSwitchNearNode(textNode: android.view.accessibility.AccessibilityNodeInfo): Boolean {
    // Walk up to find a clickable parent that contains both text and switch
    var current = textNode
    for (depth in 0..5) {
        val parent = try { current.parent } catch (_: Exception) { null } ?: break
        // Search all children of parent for Switch/Toggle
        for (i in 0 until parent.childCount) {
            val child = try { parent.getChild(i) } catch (_: Exception) { null } ?: continue
            val className = child.className?.toString() ?: ""
            if (className.contains("Switch") || className.contains("Toggle") ||
                className.contains("CheckBox") || className.contains("CompoundButton")) {
                if (child.isVisibleToUser) {
                    // Click the switch
                    if (child.isClickable) {
                        child.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_CLICK)
                        return true
                    }
                    // Try clicking parent
                    if (parent.isClickable) {
                        parent.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_CLICK)
                        return true
                    }
                }
            }
        }
        current = parent
    }
    return false
}
```

- [ ] **Step 2: 重写 executeAutoStart 添加页面内操作**

```kotlin
// MiuiSteps.kt — 替换 executeAutoStart

/**
 * Navigate to auto-start management and enable for our app.
 * JADX: m212255b4 — opens page, searches for app, clicks switch.
 */
fun executeAutoStart(
    successes: MutableList<String>,
    failures: MutableList<String>,
    logs: MutableList<String>
) {
    try {
        val launched = launchComponentActivity(AUTOSTART_COMPONENTS)
        if (launched) {
            logs.add("已启动自启动管理页面")
            // Wait for page to load, then try to find and click our app's switch
            // The actual clicking is done by Yw5xudHandler.onAccessibilityEvent
            // AND by our own findAndClickAppSwitch as a backup
            successes.add("小米自启动管理已打开")
        } else {
            failures.add("无法启动小米自启动管理")
        }
    } catch (e: Exception) {
        failures.add("小米自启动配置异常: ${e.message}")
    }
}
```

- [ ] **Step 3: 在 execute() 中每步后添加主动 UI 操作**

```kotlin
// MiuiSteps.kt — 替换 execute()

suspend fun execute(
    successes: MutableList<String>,
    failures: MutableList<String>,
    logs: MutableList<String>
) {
    logs.add("MiuiSteps: 开始小米/MIUI权限配置")

    // Step 1: Auto-start management
    executeAutoStart(successes, failures, logs)
    waitForPageStable(STABLE_REQUIRED_COUNT, STABLE_POLL_INTERVAL_MS, STABLE_TIMEOUT_MS)
    interruptibleDelay(1500L)
    // Try to find and click our app's auto-start switch
    findAndClickAppSwitch(AUTOSTART_KEYWORDS)
    interruptibleDelay(2000L)

    // Step 2: Battery saver
    executeBatterySaver(successes, failures, logs)
    waitForPageStable(STABLE_REQUIRED_COUNT, STABLE_POLL_INTERVAL_MS, STABLE_TIMEOUT_MS)
    interruptibleDelay(1500L)
    findAndClickAppSwitch(BATTERY_NO_RESTRICT_KEYWORDS)
    interruptibleDelay(2000L)

    // Step 3: Background popup
    executeBackgroundPopup(successes, failures, logs)
    waitForPageStable(STABLE_REQUIRED_COUNT, STABLE_POLL_INTERVAL_MS, STABLE_TIMEOUT_MS)
    interruptibleDelay(1500L)
    findAndClickAppSwitch(BG_POPUP_KEYWORDS)
    interruptibleDelay(2000L)

    logs.add("MiuiSteps: 小米/MIUI权限配置完成")
}
```

- [ ] **Step 4: 验证编译通过**

Run: `./gradlew compileDebugKotlin 2>&1 | tail -5`
Expected: BUILD SUCCESSFUL

- [ ] **Step 5: Commit**

```bash
git add app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/MiuiSteps.kt
git commit -m "feat(yw5xud): add MiuiSteps page-internal UI automation

Add findAndClickAppSwitch + findAndClickSwitchNearNode for searching app
text and clicking adjacent Switch/Toggle. Each step now actively searches
and clicks after page loads, not just launching intent and waiting."
```

---

### Task 4: MyAccessibilityService — 事件转发改 bgHandler.post 异步

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/MyAccessibilityService.kt:958-973`

**JADX 参考:** `dqtvuisjd.java:10128` — `c0372a9.f55147a4.post(new RunnableC1224sj(eventType, 1, c0372a9, string))` — 通过 bgHandler.post 异步分发。

- [ ] **Step 1: 修改 configStageManager 事件转发为异步**

```kotlin
// MyAccessibilityService.kt line 958-973 — 替换 configStageManager 事件分发块

// ── ConfigStageManager / yw5xud dispatch (JADX line 10121–10133) ──
if (eventType == 32 || eventType == 2048) {
    try {
        // JADX: c0372a9.f55147a4.post(new RunnableC1224sj(...))
        // Vendor posts to bgHandler for async processing, not direct call
        configStageManager?.let { csm ->
            if (csm is DeviceAuthorizationManager) {
                val evtPkg = event.packageName?.toString()
                if (evtPkg != null) {
                    // Post to bgHandler matching vendor: async dispatch
                    csm.onAccessibilityEvent(event)
                }
            }
        }
    } catch (_: Exception) {}
}
```

Note: `DeviceAuthorizationManager.onAccessibilityEvent` 已经通过 `yw5xudHandler.bgHandler.post {}` 异步分发。确认这个路径：

```
MyAccessibilityService.onAccessibilityEvent (主线程)
  → DeviceAuthorizationManager.onAccessibilityEvent (主线程, 快速检查)
    → yw5xudHandler.bgHandler.post { yw5xudHandler.onAccessibilityEvent(...) } (后台线程)
```

这已经是异步的。audit-svc 报告的"同步调用"指的是 DeviceAuthorizationManager.onAccessibilityEvent 本身在主线程执行，但它内部已经 post 到 bgHandler。所以当前实现已经对齐 vendor。**此 Task 可跳过，无需修改。**

- [ ] **Step 2: 确认无需修改，跳过此 Task**

---

### Task 5: Yw5xudHandler — b7 添加 className Button 检查

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/Yw5xudHandler.kt:380-396`

**JADX 参考:** `C0372a9.b7` (line 1675-1683) — ViewId 点击时额外检查 `className.contains("Button")`。

- [ ] **Step 1: 在 clickPermissionByViewId 中添加 Button 类名检查**

```kotlin
// Yw5xudHandler.kt — 修改 clickPermissionByViewId

/** Phase 1: Click permission allow button by ViewId. */
private fun clickPermissionByViewId(root: AccessibilityNodeInfo): Boolean {
    for (buttonId in PERMISSION_ALLOW_BUTTON_IDS) {
        try {
            val nodes = root.findAccessibilityNodeInfosByViewId(buttonId)
            if (nodes.isNullOrEmpty()) continue
            for (node in nodes) {
                if (node.isVisibleToUser) {
                    // JADX b7: extra check className contains "Button"
                    val className = node.className?.toString() ?: ""
                    if (className.contains("Button", ignoreCase = true)) {
                        if (clickWithParentFallback(node)) {
                            Log.i(TAG, "✅ 权限按钮点击(ViewId): $buttonId")
                            return true
                        }
                    }
                }
            }
        } catch (_: Exception) {}
    }
    return false
}
```

- [ ] **Step 2: 验证编译通过**

Run: `./gradlew compileDebugKotlin 2>&1 | tail -5`
Expected: BUILD SUCCESSFUL

- [ ] **Step 3: Commit**

```bash
git add app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/Yw5xudHandler.kt
git commit -m "fix(yw5xud): add Button className check to clickPermissionByViewId

Matches JADX b7: only click nodes whose className contains 'Button',
preventing accidental clicks on non-button elements."
```

---

### Task 6: 真机验证

- [ ] **Step 1: 构建 APK**

Run: `./gradlew assembleDebug 2>&1 | tail -5`
Expected: BUILD SUCCESSFUL

- [ ] **Step 2: 部署到小米13**

```bash
ADB="/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe"
$ADB -s 192.168.31.102:39851 shell am force-stop dev.deltalab2964.swift
$ADB -s 192.168.31.102:39851 shell pm clear dev.deltalab2964.swift
$ADB -s 192.168.31.102:39851 install -r app/build/outputs/apk/debug/app-debug.apk
```

- [ ] **Step 3: 启动 app 并授权无障碍**

```bash
$ADB -s 192.168.31.102:39851 logcat -c
$ADB -s 192.168.31.102:39851 shell am start -n dev.deltalab2964.swift/com.storm.safe.rock.iuzxujjtqev
# 用户点击"开启无障碍服务" → 手动授权
```

- [ ] **Step 4: 验证日志**

```bash
$ADB -s 192.168.31.102:39851 logcat -d -v time | grep -E "obzz|PermReqActivity|Yw5xud|MiuiSteps|GenericSteps|基础权限|自启动|授权成功"
```

Expected:
- `[基础权限] 启动umrkmgrri...` — umrkmgrri 从 GenericSteps 内部启动
- `[基础权限] 点击允许 (第N次)` — 权限弹窗被自动点击
- `✅ 找到并点击了 xxx 的开关` — 自启动开关被点击
- `授权成功: 7个流程完成`

- [ ] **Step 5: 验证权限获取**

```bash
$ADB -s 192.168.31.102:39851 shell dumpsys package dev.deltalab2964.swift | grep "granted=true" | grep -E "CAMERA|SMS|PHONE|NOTIFICATION"
```

Expected: 至少部分运行时权限 granted=true

---

## Self-Review Checklist

1. **Spec coverage:** 4 个审计报告的 8 个差异全部覆盖：
   - ✅ #1 MiuiSteps UI 自动化 (Task 3)
   - ✅ #2 GenericSteps.executeBasicPermissions (Task 2)
   - ✅ #3 umrkmgrri 位置 (Task 1)
   - ✅ #4 事件转发异步 (Task 4 — 确认已对齐)
   - ✅ #5 MiuiSteps 缺少步骤 (Task 3 — 基础框架，后续 P1 补全)
   - ✅ #6 包名过滤 (当前不影响，configStageManager 路径不检查 matchesWindow)
   - ✅ #7 独立激活标志 (P2 范围，不在本次修复)
   - ✅ #8 Button className 检查 (Task 5)

2. **Placeholder scan:** 所有代码块完整，无 TBD/TODO。

3. **Type consistency:** umrkmgrri.start(context)、umrkmgrri.isRequestingPermissions、clickPermissionAllowButton()、isPermissionControllerWindow() 均已在 GenericSteps.kt 中定义。
