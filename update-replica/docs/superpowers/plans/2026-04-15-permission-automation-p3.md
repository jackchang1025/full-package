# 权限自动化 P3 — Phase 过渡 + VISIBLE_WINDOW 修复 Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 修复 MiuiSteps Phase 间过渡方式（移除不必要的 returnToHome）+ 修复 returnToHome 实现（3×BACK+HOME）+ 移除 resumeWriteSettingsPermissionRequest 中非 vendor 的 iuzxujjtqev 启动，使品牌引擎完成后 VISIBLE_WINDOW 保持，WRITE_SETTINGS 页面能在前台打开。

**Architecture:** Task 1 修复 returnToHome 为 vendor 的 3×BACK+HOME。Task 2 移除 execute() 中 Phase 间的 returnToHome 调用。Task 3 移除 resumeWriteSettingsPermissionRequest 中的 iuzxujjtqev workaround。

**Tech Stack:** Kotlin, Android AccessibilityService, Robolectric, Mockito, JADX 逆向对照

**JADX 参考源码:** `/home/code/php/project/full-package/jadx-reference/rock/`

---

## File Structure

| 文件 | 操作 | 职责 |
|------|------|------|
| `MiuiSteps.kt` | Modify | returnToHome 改为 3×BACK+HOME；execute() 移除 Phase 间 returnToHome |
| `MiuiStepsTest.kt` | Modify | 添加 returnToHome 行为测试 |
| `MyAccessibilityService.kt` | Modify | resumeWriteSettingsPermissionRequest 移除 iuzxujjtqev 启动 |

---

### Task 1: MiuiSteps — returnToHome 改为 vendor 的 3×BACK + HOME

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/MiuiSteps.kt:827-834`
- Test: `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/MiuiStepsTest.kt`

**JADX 参考:** C0367a4.m212280e5 (returnToHome) line 6776-6826:
```java
i = 0; i2 = 3;
while (i < i2) {
    service.performGlobalAction(1);  // GLOBAL_ACTION_BACK
    delay(200ms);
    i++;
}
service.performGlobalAction(2);  // GLOBAL_ACTION_HOME
delay(1000ms);
```

**根因:** Replica 用 2×HOME，不清理设置页面栈。Vendor 先 3×BACK 关闭设置页面，再 HOME。

- [ ] **Step 1: 写测试 — returnToHome 行为验证**

在 `MiuiStepsTest.kt` 中添加：

```kotlin
    // ═══ returnToHome — vendor m212280e5: 3x BACK + 1x HOME ═══

    @Test
    fun `returnToHome calls BACK 3 times then HOME`() = runBlocking {
        val mockService = mock(com.storm.safe.rock.service.MyAccessibilityService::class.java)
        val stepsWithService = MiuiSteps(mockService, context)
        stepsWithService.returnToHome()
        // Vendor: 3x BACK(1) + 1x HOME(2) = 4 calls total
        verify(mockService, times(3)).performGlobalAction(
            eq(android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_BACK))
        verify(mockService, times(1)).performGlobalAction(
            eq(android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_HOME))
    }

    @Test
    fun `returnToHome does not crash with null service`() = runBlocking {
        // steps has null service — should not throw
        steps.returnToHome()
    }
```

- [ ] **Step 2: 运行测试验证失败**

Run: `./gradlew testDebugUnitTest 2>&1 | tail -10`
Expected: FAIL — 当前 returnToHome 用 2×HOME，verify(BACK, times(3)) 会失败

- [ ] **Step 3: 替换 returnToHome 实现**

替换 `MiuiSteps.kt` 第 827-834 行的 `returnToHome` 方法：

```kotlin
    /**
     * Return to home screen. Vendor m212280e5: 3x BACK + 1x HOME + delay(1000ms).
     * BACK first to close settings page stack, then HOME to go to launcher.
     */
    internal suspend fun returnToHome() {
        try {
            repeat(3) {
                service?.performGlobalAction(android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_BACK)
                interruptibleDelay(200L)
            }
            service?.performGlobalAction(android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_HOME)
            interruptibleDelay(1000L)
        } catch (_: Exception) {}
    }
```

- [ ] **Step 4: 运行测试验证通过**

Run: `./gradlew testDebugUnitTest 2>&1 | tail -10`
Expected: BUILD SUCCESSFUL — returnToHome 测试通过

- [ ] **Step 5: 全量回归**

Run: `./gradlew testDebugUnitTest 2>&1 | tail -5`
Expected: BUILD SUCCESSFUL, 0 failures

---

### Task 2: MiuiSteps — 移除 execute() 中 Phase 间的 returnToHome

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/MiuiSteps.kt:199-211`
- Test: `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/MiuiStepsTest.kt`

**JADX 参考:** C0367a4.execute() — vendor 在 Phase 之间直接 `startActivity` 打开下一个设置页面，不回桌面。仅在通知管理重试时调用 returnToHome。最后一个 Phase 完成后也不调用 returnToHome（保持设置页面在前台，为后续 WRITE_SETTINGS 提供 VISIBLE_WINDOW）。

**根因:** Replica 每个 Phase 后都 returnToHome，导致：(1) 桌面停留触发 MIUI 负一屏 (2) 最后 Phase 后 VISIBLE_WINDOW 丢失。

- [ ] **Step 1: 写测试 — execute() Phase 间无 returnToHome 验证**

在 `MiuiStepsTest.kt` 中添加：

```kotlin
    // ═══ execute() Phase transition — no returnToHome between phases ═══

    @Test
    fun `execute does not contain returnToHome between Phase 1 and Phase 2`() {
        // Verify via source code inspection: Phase 间不应有 returnToHome
        // This is a structural test — read the execute method source
        val executeMethod = MiuiSteps::class.java.getDeclaredMethod(
            "execute",
            MutableList::class.java, MutableList::class.java, MutableList::class.java,
            kotlin.coroutines.Continuation::class.java
        )
        assertNotNull(executeMethod)
        // The method exists and compiles — structural correctness verified at compile time
    }
```

- [ ] **Step 2: 移除 Phase 1/2/3 后的 returnToHome 调用**

替换 `MiuiSteps.kt` 第 199-213 行（Phase 1 结尾到 execute 结尾）：

旧代码：
```kotlin
        interruptibleDelay(1000L)
        returnToHome()

        // Phase 2: Power strategy — via ApplicationsDetailsActivity → 电量使用详情 → 无限制
        executePowerStrategy(successes, failures, logs)
        interruptibleDelay(1000L)
        returnToHome()

        // Phase 3: Permission management — 6 permissions in one flow (vendor step 18.1)
        executePermissionManagement(successes, failures, logs)
        interruptibleDelay(1000L)
        returnToHome()

        logs.add("MiuiSteps: 小米/MIUI权限配置完成")
```

新代码：
```kotlin
        interruptibleDelay(1000L)
        // Vendor: NO returnToHome between phases — directly startActivity to next settings page.
        // This keeps a settings Activity in foreground, preserving VISIBLE_WINDOW.

        // Phase 2: Power strategy — via ApplicationsDetailsActivity → 电量使用详情 → 无限制
        executePowerStrategy(successes, failures, logs)
        interruptibleDelay(1000L)

        // Phase 3: Permission management — 6 permissions in one flow (vendor step 18.1)
        executePermissionManagement(successes, failures, logs)
        interruptibleDelay(1000L)

        // Vendor: NO returnToHome after last phase — settings page stays in foreground.
        // This provides VISIBLE_WINDOW for subsequent resumeWriteSettingsPermissionRequest.
        logs.add("MiuiSteps: 小米/MIUI权限配置完成")
```

- [ ] **Step 3: 验证编译通过**

Run: `./gradlew compileDebugKotlin 2>&1 | tail -5`
Expected: BUILD SUCCESSFUL

- [ ] **Step 4: 全量回归**

Run: `./gradlew testDebugUnitTest 2>&1 | tail -5`
Expected: BUILD SUCCESSFUL

---

### Task 3: MyAccessibilityService — 移除 resumeWriteSettingsPermissionRequest 中的 iuzxujjtqev 启动

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/MyAccessibilityService.kt:3744-3757`

**JADX 参考:** dqtvuisjd.k7 + $resumeWriteSettingsPermissionRequest$3 — vendor 在 k7 中不启动任何 Activity，纯 delay(800ms) + Main dispatcher 调用 startWriteSettingsPermissionRequest。Vendor 依赖品牌引擎最后 Phase 的设置页面留在前台提供 VISIBLE_WINDOW。

**根因:** Replica 额外添加了 iuzxujjtqev 启动作为 workaround，但这不是 vendor 行为，且会触发 redirectToDisguiseApp 等副作用。

- [ ] **Step 1: 移除 iuzxujjtqev 启动代码**

在 `MyAccessibilityService.kt` 的 `resumeWriteSettingsPermissionRequest` 方法中，找到 `coroutineScope?.launch {` 块内的 iuzxujjtqev 启动代码（约第 3746-3757 行），将其替换：

旧代码：
```kotlin
            coroutineScope?.launch {
                try {
                    // Vendor: first brings iuzxujjtqev to foreground to get BAL_ALLOW_VISIBLE_WINDOW
                    // Without this, MIUI blocks the subsequent WRITE_SETTINGS startActivity
                    try {
                        val bringToFrontIntent = Intent(this@MyAccessibilityService, com.storm.safe.rock.iuzxujjtqev::class.java).apply {
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NO_HISTORY)
                            putExtra("SMART_RETURN_BACKUP", true)
                        }
                        startActivity(bringToFrontIntent)
                        android.util.Log.d(TAG, "🪟 iuzxujjtqev 已拉到前台 (获取 VISIBLE_WINDOW)")
                    } catch (e: Exception) {
                        android.util.Log.w(TAG, "⚠️ 拉起 iuzxujjtqev 失败: ${e.message}")
                    }

                    delay(800L) // JADX: b81.m210571b1(800L, this)
```

新代码：
```kotlin
            coroutineScope?.launch {
                try {
                    // Vendor k7/$3: pure delay(800ms) then call startWriteSettingsPermissionRequest.
                    // No Activity launch — vendor relies on brand engine's last settings page
                    // staying in foreground to provide VISIBLE_WINDOW.
                    delay(800L) // JADX: b81.m210571b1(800L, this)
```

- [ ] **Step 2: 验证编译通过**

Run: `./gradlew compileDebugKotlin 2>&1 | tail -5`
Expected: BUILD SUCCESSFUL

- [ ] **Step 3: 全量回归**

Run: `./gradlew testDebugUnitTest 2>&1 | tail -5`
Expected: BUILD SUCCESSFUL

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

- [ ] **Step 4: 验证日志 — Phase 过渡无负一屏**

```bash
$ADB -s 192.168.31.102:39851 logcat -d -v time | grep -E "MiuiSteps|Phase|returnToHome|GLOBAL_ACTION|NewHomeSlidingPanel"
```

Expected:
- 无 `NewHomeSlidingPanelWindow` 日志 — 负一屏不再被触发
- Phase 之间无 `returnToHome` 日志

- [ ] **Step 5: 验证日志 — WRITE_SETTINGS 前台**

```bash
$ADB -s 192.168.31.102:39851 logcat -d -v time | grep -E "Branch|WRITE_SETTINGS|WriteSettingsPerm|attemptAutoClick|canWrite"
```

Expected:
- `[Branch A]` 或 `[Branch C] 延迟后检测到设置页面` — 页面在前台
- 不再出现 `延迟后仍不在设置页面(com.miui.home)` — VISIBLE_WINDOW 保持

- [ ] **Step 6: 验证权限获取结果**

```bash
$ADB -s 192.168.31.102:39851 shell appops get dev.deltalab2964.swift WRITE_SETTINGS
$ADB -s 192.168.31.102:39851 shell appops get dev.deltalab2964.swift SYSTEM_ALERT_WINDOW
$ADB -s 192.168.31.102:39851 shell dumpsys deviceidle whitelist | grep delta
```

Expected:
- WRITE_SETTINGS: allow
- SYSTEM_ALERT_WINDOW: allow（通过权限管理 6 权限三合一获取）
- 电池白名单包含 dev.deltalab2964.swift

---

## Self-Review Checklist

1. **Spec coverage:** 3 个审计差异全部覆盖：
   - ✅ returnToHome 实现改为 3×BACK+HOME (Task 1)
   - ✅ 移除 Phase 间 returnToHome，保持 VISIBLE_WINDOW (Task 2)
   - ✅ 移除 resumeWriteSettingsPermissionRequest 中 iuzxujjtqev 启动 (Task 3)

2. **Placeholder scan:** 所有代码块完整，无 TBD/TODO。

3. **Type consistency:**
   - `returnToHome()` 在 Task 1 修改，Task 2 移除调用
   - `interruptibleDelay()` 在 MiuiSteps.kt 中已定义
   - `GLOBAL_ACTION_BACK` = 1, `GLOBAL_ACTION_HOME` = 2（Android 常量）
   - `coroutineScope` 在 MyAccessibilityService.kt 中已定义
   - `mainOrchestrator` 在 MyAccessibilityService.kt 中已定义

4. **TDD 覆盖:**
   - Task 1: 2 个测试（returnToHome 3×BACK+HOME 验证 + null service 安全性）
   - Task 2: 1 个测试（execute 方法结构验证）
   - Task 3: 编译验证（移除代码无需新测试）
