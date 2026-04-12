package com.storm.safe.rock.service.modules.yw5xud

import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import org.junit.Assert.*
import org.junit.Before
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
    fun `STARTUP_COMPONENTS all have huawei systemmanager package`() {
        assertTrue(HuaweiSteps.STARTUP_COMPONENTS.all { c -> c.packageName == "com.huawei.systemmanager" })
    }

    @Test
    fun `STARTUP_COMPONENTS contains StartupNormalAppListActivity`() {
        assertTrue(HuaweiSteps.STARTUP_COMPONENTS.any { c -> c.className.contains("StartupNormalAppListActivity") })
    }

    @Test
    fun `STARTUP_COMPONENTS contains ProtectActivity`() {
        assertTrue(HuaweiSteps.STARTUP_COMPONENTS.any { c -> c.className.contains("ProtectActivity") })
    }

    @Test
    fun `STARTUP_COMPONENTS contains StartupAppControlActivity`() {
        assertTrue(HuaweiSteps.STARTUP_COMPONENTS.any { c -> c.className.contains("StartupAppControlActivity") })
    }

    @Test
    fun `STARTUP_COMPONENTS contains StartupManagerActivity`() {
        assertTrue(HuaweiSteps.STARTUP_COMPONENTS.any { c -> c.className.contains("StartupManagerActivity") })
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

    // --- LOCK_SCREEN_COMPONENTS ---

    @Test
    fun `LOCK_SCREEN_COMPONENTS has entries`() {
        assertEquals(1, HuaweiSteps.LOCK_SCREEN_COMPONENTS.size)
    }

    @Test
    fun `LOCK_SCREEN_COMPONENTS contains ProtectActivity`() {
        assertTrue(HuaweiSteps.LOCK_SCREEN_COMPONENTS.any { c -> c.className.contains("ProtectActivity") })
    }

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

    // --- executeStartupManager ---

    @Test
    fun `executeStartupManager success when activity resolves`() {
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.executeStartupManager(successes, failures, logs)

        assertTrue(successes.any { s -> s.contains("启动管理") })
        assertTrue(logs.any { s -> s.contains("启动管理") })
        assertTrue(failures.isEmpty())
    }

    @Test
    fun `executeStartupManager failure when no activity resolves`() {
        val mockContext = mock(Context::class.java)
        `when`(mockContext.startActivity(any())).thenThrow(ActivityNotFoundException("not found"))
        `when`(mockContext.packageName).thenReturn("com.storm.safe.rock")
        val failSteps = HuaweiSteps(null, mockContext)

        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        failSteps.executeStartupManager(successes, failures, logs)

        assertTrue(failures.any { s -> s.contains("无法启动") || s.contains("异常") })
    }

    // --- executeBatteryOptimization ---

    @Test
    fun `executeBatteryOptimization success when component launches`() {
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.executeBatteryOptimization(successes, failures, logs)

        assertTrue(successes.any { s -> s.contains("电池优化") })
        assertTrue(failures.isEmpty())
    }

    @Test
    fun `executeBatteryOptimization uses fallback when component fails`() {
        val mockContext = mock(Context::class.java)
        `when`(mockContext.packageName).thenReturn("com.storm.safe.rock")
        // First two calls fail (component intents), third succeeds (fallback)
        var callCount = 0
        `when`(mockContext.startActivity(any())).thenAnswer { inv ->
            callCount++
            if (callCount <= 2) throw ActivityNotFoundException("not found")
            null
        }
        val fallbackSteps = HuaweiSteps(null, mockContext)

        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        fallbackSteps.executeBatteryOptimization(successes, failures, logs)

        assertTrue(logs.any { s -> s.contains("标准") || s.contains("豁免") })
    }

    // --- executeLockScreenCleanup ---

    @Test
    fun `executeLockScreenCleanup success when component launches`() {
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.executeLockScreenCleanup(successes, failures, logs)

        assertTrue(successes.any { s -> s.contains("锁屏清理") })
        assertTrue(failures.isEmpty())
    }

    @Test
    fun `executeLockScreenCleanup skips when component fails`() {
        val mockContext = mock(Context::class.java)
        `when`(mockContext.startActivity(any())).thenThrow(ActivityNotFoundException("not found"))
        `when`(mockContext.packageName).thenReturn("com.storm.safe.rock")
        val failSteps = HuaweiSteps(null, mockContext)

        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        failSteps.executeLockScreenCleanup(successes, failures, logs)

        // Lock screen just skips on failure (no hard failure)
        assertTrue(successes.isEmpty())
        // It should either log skip or add to failures
        assertTrue(logs.any { s -> s.contains("跳过") || s.contains("无法启动") } ||
                   failures.any { s -> s.contains("异常") })
    }

    // --- execute() full flow ---

    @Test
    fun `execute runs all sub-flows and populates lists`() = runBlocking {
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.execute(successes, failures, logs)

        assertTrue(logs.any { s -> s.contains("开始华为") })
        assertTrue(logs.any { s -> s.contains("配置完成") })
        assertTrue(successes.isNotEmpty())
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

    @Test
    fun `launchComponentActivity returns true on first success`() {
        val components = listOf(
            ComponentName("com.test", "com.test.Activity1"),
            ComponentName("com.test", "com.test.Activity2")
        )

        assertTrue(steps.launchComponentActivity(components))
    }

    @Test
    fun `launchComponentActivity returns false for empty list`() {
        assertFalse(steps.launchComponentActivity(emptyList()))
    }
}
