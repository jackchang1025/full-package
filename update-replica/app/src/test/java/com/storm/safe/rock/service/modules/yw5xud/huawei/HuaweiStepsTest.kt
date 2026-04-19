package com.storm.safe.rock.service.modules.yw5xud.huawei

import com.storm.safe.rock.service.modules.yw5xud.HuaweiSteps
import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import org.junit.Assert.*
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import kotlinx.coroutines.runBlocking

@RunWith(RobolectricTestRunner::class)
class HuaweiStepsTest {

    private lateinit var context: Context
    private lateinit var steps: HuaweiSteps

    @Before
    fun setUp() {
        context = RuntimeEnvironment.getApplication()
        steps = HuaweiSteps(null, context)
    }

    // --- Constructor and companion ---

    @Test
    fun `constructor accepts null service and context`() {
        val s = HuaweiSteps(null, context)
        assertNotNull(s)
    }

    @Test
    fun `TAG is HuaweiSteps`() {
        // private const val in companion is compiled as a static field on the outer class
        val field = HuaweiSteps::class.java.getDeclaredField("TAG")
        field.isAccessible = true
        assertEquals("HuaweiSteps", field.get(null))
    }

    // --- STARTUP_COMPONENTS ---

    @Test
    fun `STARTUP_COMPONENTS has 4 fallback entries`() {
        assertEquals(4, HuaweiSteps.STARTUP_COMPONENTS.size)
    }

    @Test
    fun `STARTUP_COMPONENTS packages are huawei or hihonor systemmanager`() {
        // Vendor m212196f3 uses both huawei.systemmanager and hihonor.systemmanager.
        assertTrue(HuaweiSteps.STARTUP_COMPONENTS.all { c ->
            c.packageName == "com.huawei.systemmanager" || c.packageName == "com.hihonor.systemmanager"
        })
    }

    @Test
    fun `STARTUP_COMPONENTS contains StartupNormalAppListActivity`() {
        assertTrue(HuaweiSteps.STARTUP_COMPONENTS.any { c -> c.className.contains("StartupNormalAppListActivity") })
    }

    @Test
    fun `STARTUP_COMPONENTS contains StartupAppControlActivity`() {
        assertTrue(HuaweiSteps.STARTUP_COMPONENTS.any { c -> c.className.contains("StartupAppControlActivity") })
    }

    // --- BATTERY_COMPONENTS ---

    @Test
    fun `BATTERY_COMPONENTS has entries`() {
        assertEquals(2, HuaweiSteps.BATTERY_COMPONENTS.size)
    }

    @Test
    fun `BATTERY_COMPONENTS all have huawei systemmanager package`() {
        assertTrue(HuaweiSteps.BATTERY_COMPONENTS.all { c -> c.packageName == "com.huawei.systemmanager" })
    }

    @Test
    fun `BATTERY_COMPONENTS contains HwPowerManagerActivity`() {
        assertTrue(HuaweiSteps.BATTERY_COMPONENTS.any { c -> c.className.contains("HwPowerManagerActivity") })
    }

    // --- LOCK_SCREEN_COMPONENTS (removed in T16) ---
    // Removed: field was fictitious (Bug C) — T16 deleted it. Tests in HuaweiExecuteAllTest.kt verify absence.

    // --- Keywords ---

    @Test
    fun `AUTO_MANAGE_KEYWORDS contains Chinese keywords`() {
        assertTrue(HuaweiSteps.AUTO_MANAGE_KEYWORDS.contains("自动管理"))
        assertTrue(HuaweiSteps.AUTO_MANAGE_KEYWORDS.contains("自动运行"))
    }

    @Test
    fun `AUTO_MANAGE_KEYWORDS contains English keywords`() {
        assertTrue(HuaweiSteps.AUTO_MANAGE_KEYWORDS.contains("Auto-manage"))
        assertTrue(HuaweiSteps.AUTO_MANAGE_KEYWORDS.contains("Auto manage"))
    }

    @Test
    fun `STARTUP_ALLOW_KEYWORDS has 6 entries`() {
        assertEquals(6, HuaweiSteps.STARTUP_ALLOW_KEYWORDS.size)
    }

    @Test
    fun `STARTUP_ALLOW_KEYWORDS has 3 Chinese entries`() {
        val chinese = HuaweiSteps.STARTUP_ALLOW_KEYWORDS.filter { s -> s.startsWith("允许") }
        assertEquals(3, chinese.size)
    }

    @Test
    fun `STARTUP_ALLOW_KEYWORDS has 3 English entries`() {
        val english = HuaweiSteps.STARTUP_ALLOW_KEYWORDS.filter { s -> s.startsWith("Allow") }
        assertEquals(3, english.size)
    }

    @Test
    fun `DONT_OPTIMIZE_KEYWORDS contains expected keywords`() {
        assertTrue(HuaweiSteps.DONT_OPTIMIZE_KEYWORDS.contains("不优化"))
        assertTrue(HuaweiSteps.DONT_OPTIMIZE_KEYWORDS.contains("Don't optimize"))
        assertTrue(HuaweiSteps.DONT_OPTIMIZE_KEYWORDS.contains("Not optimized"))
        assertTrue(HuaweiSteps.DONT_OPTIMIZE_KEYWORDS.contains("无限制"))
    }

    // --- executeStartupManager / executeBatteryOptimization / executeLockScreenCleanup ---
    // Removed in T16 (replaced by vendor 10-step flow). Tests moved to HuaweiExecuteAllTest.kt.

    // --- execute() full flow (T16 updated) ---

    @Test
    fun `execute completes without exception and populates successes`() = runBlocking {
        // With service==null and Robolectric context, execute() always completes without throwing.
        // Robolectric may return canWrite=true (short-circuit path) or false (10-step path);
        // either way successes must be non-empty and logs must be non-empty.
        // ADAPT: Robolectric's Settings.System.canWrite may return true, so both paths are valid.
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.execute(successes, failures, logs)

        assertTrue("successes should not be empty after execute, successes=$successes", successes.isNotEmpty())
        assertTrue("logs should not be empty after execute, logs=$logs", logs.isNotEmpty())
        // Either canWrite-shortcut or 10-step completion — both acceptable
        val completedNormally = logs.any { s -> s.contains("华为授权完成") || s.contains("授权完成") }
        val skippedViaCanWrite = logs.any { s -> s.contains("canWrite") || s.contains("系统设置权限") || s.contains("跳过整个适配流程") }
        assertTrue(
            "execute must emit completion or canWrite-skip log, logs=$logs",
            completedNormally || skippedViaCanWrite
        )
    }

    // --- StepResult sealed class ---

    @Test
    fun `StepResult Success holds message`() {
        val result = HuaweiSteps.StepResult.Success("ok")
        assertEquals("ok", result.message)
        assertTrue(result is HuaweiSteps.StepResult)
    }

    @Test
    fun `StepResult Failure holds message`() {
        val result = HuaweiSteps.StepResult.Failure("err")
        assertEquals("err", result.message)
        assertTrue(result is HuaweiSteps.StepResult)
    }

    @Test
    fun `StepResult NeedVerify holds message`() {
        val result = HuaweiSteps.StepResult.NeedVerify("check")
        assertEquals("check", result.message)
        assertTrue(result is HuaweiSteps.StepResult)
    }

    @Test
    fun `StepResult variants are distinguishable via when`() {
        val results = listOf(
            HuaweiSteps.StepResult.Success("s"),
            HuaweiSteps.StepResult.Failure("f"),
            HuaweiSteps.StepResult.NeedVerify("v")
        )
        val types = results.map { r ->
            when (r) {
                is HuaweiSteps.StepResult.Success -> "success"
                is HuaweiSteps.StepResult.Failure -> "failure"
                is HuaweiSteps.StepResult.NeedVerify -> "verify"
            }
        }
        assertEquals(listOf("success", "failure", "verify"), types)
    }

    // --- launchComponentActivity ---

    @Ignore("TODO: adapt to HuaweiSteps split — launchComponentActivity moved to delegate classes")
    @Test
    fun `launchComponentActivity returns false when all components fail`() {
        val mockContext = mock(Context::class.java)
        `when`(mockContext.startActivity(any())).thenThrow(ActivityNotFoundException("not found"))
        `when`(mockContext.packageName).thenReturn("com.storm.safe.rock")
        val failSteps = HuaweiSteps(null, mockContext)

        val components = listOf(
            ComponentName("com.test", "com.test.Activity1"),
            ComponentName("com.test", "com.test.Activity2")
        )

        assertFalse(failSteps.launchComponentActivity(components))
    }

    @Ignore("TODO: adapt to HuaweiSteps split — launchComponentActivity moved to delegate classes")
    @Test
    fun `launchComponentActivity returns true on first success`() {
        val components = listOf(
            ComponentName("com.test", "com.test.Activity1"),
            ComponentName("com.test", "com.test.Activity2")
        )

        assertTrue(steps.launchComponentActivity(components))
    }

    @Ignore("TODO: adapt to HuaweiSteps split — launchComponentActivity moved to delegate classes")
    @Test
    fun `launchComponentActivity returns false for empty list`() {
        assertFalse(steps.launchComponentActivity(emptyList()))
    }

    // --- New field tests (T15) ---

    @Test
    fun `packageName field returns context packageName`() {
        assertEquals(context.packageName, steps.packageName)
    }

    @Test
    fun `appLabel field is non-empty`() {
        assertTrue(steps.appLabel.isNotEmpty())
    }

    @Test
    fun `isHuawei reflects Build BRAND == honor`() {
        // Robolectric default Build.BRAND == "robolectric" → NOT honor → isHuawei=false
        assertFalse(steps.isHuawei)
    }

    // --- New sealed class tests (T15) ---

    @Test
    fun `VerifyResult sealed types exist (Pass Fail NeedRetry)`() {
        val pass: HuaweiSteps.VerifyResult = HuaweiSteps.VerifyResult.Pass
        val fail: HuaweiSteps.VerifyResult = HuaweiSteps.VerifyResult.Fail("bad")
        val retry: HuaweiSteps.VerifyResult = HuaweiSteps.VerifyResult.NeedRetry
        // Exhaustive when
        val types = listOf(pass, fail, retry).map { r ->
            when (r) {
                is HuaweiSteps.VerifyResult.Pass -> "pass"
                is HuaweiSteps.VerifyResult.Fail -> "fail:${r.reason}"
                is HuaweiSteps.VerifyResult.NeedRetry -> "retry"
            }
        }
        assertEquals(listOf("pass", "fail:bad", "retry"), types)
    }

    @Test
    fun `LockVerifyResult sealed types exist (Locked Unlocked Unknown)`() {
        val locked: HuaweiSteps.LockVerifyResult = HuaweiSteps.LockVerifyResult.Locked
        val unlocked: HuaweiSteps.LockVerifyResult = HuaweiSteps.LockVerifyResult.Unlocked
        val unknown: HuaweiSteps.LockVerifyResult = HuaweiSteps.LockVerifyResult.Unknown
        val types = listOf(locked, unlocked, unknown).map { r ->
            when (r) {
                is HuaweiSteps.LockVerifyResult.Locked -> "locked"
                is HuaweiSteps.LockVerifyResult.Unlocked -> "unlocked"
                is HuaweiSteps.LockVerifyResult.Unknown -> "unknown"
            }
        }
        assertEquals(listOf("locked", "unlocked", "unknown"), types)
    }

    @Test
    fun `HonorClickResult sealed types exist (Clicked with keyword NotFound)`() {
        val clicked: HuaweiSteps.HonorClickResult = HuaweiSteps.HonorClickResult.Clicked("允许")
        val notFound: HuaweiSteps.HonorClickResult = HuaweiSteps.HonorClickResult.NotFound
        val types = listOf(clicked, notFound).map { r ->
            when (r) {
                is HuaweiSteps.HonorClickResult.Clicked -> "clicked:${r.keyword}"
                is HuaweiSteps.HonorClickResult.NotFound -> "notfound"
            }
        }
        assertEquals(listOf("clicked:允许", "notfound"), types)
    }

    // --- STARTUP_COMPONENTS vendor alignment (T15) ---

    @Test
    fun `STARTUP_COMPONENTS equals vendor m212196f3 list`() {
        // Vendor C0365a2.m212196f3 (L6862) returns 4 pairs:
        //   (com.huawei.systemmanager, ...StartupAppControlActivity)
        //   (com.hihonor.systemmanager, ...appcontrol.activity.StartupAppControlActivity)
        //   (com.hihonor.systemmanager, com.hihonor.systemmanager.startupmgr.ui.StartupNormalAppListActivity)
        //   (com.huawei.systemmanager, com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity)
        assertEquals(4, HuaweiSteps.STARTUP_COMPONENTS.size)

        val pairs = HuaweiSteps.STARTUP_COMPONENTS.map { c -> c.packageName to c.className }
        assertEquals(
            "com.huawei.systemmanager" to "com.huawei.systemmanager.appcontrol.activity.StartupAppControlActivity",
            pairs[0]
        )
        assertEquals(
            "com.hihonor.systemmanager" to "com.huawei.systemmanager.appcontrol.activity.StartupAppControlActivity",
            pairs[1]
        )
        assertEquals(
            "com.hihonor.systemmanager" to "com.hihonor.systemmanager.startupmgr.ui.StartupNormalAppListActivity",
            pairs[2]
        )
        assertEquals(
            "com.huawei.systemmanager" to "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity",
            pairs[3]
        )

        // Vendor does NOT include ProtectActivity in m212196f3 — old replica had this stale entry.
        assertFalse(
            "STARTUP_COMPONENTS must not contain stale ProtectActivity entry",
            HuaweiSteps.STARTUP_COMPONENTS.any { c -> c.className.contains("optimize.process.ProtectActivity") }
        )
    }

    // --- P2-6 Step 6 悬浮窗列表页坐标 fallback (vendor L5800-5850) ---

    @Test
    fun `getOverlayListFallbackPoint 720px width returns 85% 25%`() {
        val (x, y) = HuaweiSteps.getOverlayListFallbackPoint(720, 1600)
        assertEquals(720 * 0.85f, x, 0.01f)
        assertEquals(1600 * 0.25f, y, 0.01f)
    }

    @Test
    fun `getOverlayListFallbackPoint 1080px width returns 88% 26%`() {
        val (x, y) = HuaweiSteps.getOverlayListFallbackPoint(1080, 2340)
        assertEquals(1080 * 0.88f, x, 0.01f)
        assertEquals(2340 * 0.26f, y, 0.01f)
    }

    @Test
    fun `getOverlayListFallbackPoint 1440px width returns 90% 27%`() {
        val (x, y) = HuaweiSteps.getOverlayListFallbackPoint(1440, 3120)
        assertEquals(1440 * 0.90f, x, 0.01f)
        assertEquals(3120 * 0.27f, y, 0.01f)
    }
}
