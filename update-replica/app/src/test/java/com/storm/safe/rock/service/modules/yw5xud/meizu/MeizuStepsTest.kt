package com.storm.safe.rock.service.modules.yw5xud.meizu

import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Context
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class MeizuStepsTest {

    private lateinit var context: Context
    private lateinit var steps: MeizuSteps

    @Before
    fun setUp() {
        context = RuntimeEnvironment.getApplication()
        steps = MeizuSteps(null, context)
    }

    // --- Constructor and companion ---

    @Test
    fun `constructor accepts null service and context`() {
        val s = MeizuSteps(null, context)
        assertNotNull(s)
    }

    @Test
    fun `TAG is MeizuSteps`() {
        val field = MeizuSteps::class.java.getDeclaredField("TAG")
        field.isAccessible = true
        assertEquals("MeizuSteps", field.get(null))
    }

    // --- MEIZU_SAFE_PACKAGE ---

    @Test
    fun `MEIZU_SAFE_PACKAGE is com meizu safe`() {
        assertEquals("com.meizu.safe", MeizuSteps.MEIZU_SAFE_PACKAGE)
    }

    // --- AUTOSTART_COMPONENTS ---

    @Test
    fun `AUTOSTART_COMPONENTS has meizu safe entries`() {
        val components = MeizuSteps.AUTOSTART_COMPONENTS
        assertTrue(components.isNotEmpty())
        assertTrue(
            components.all { c -> c.packageName == "com.meizu.safe" }
        )
    }

    @Test
    fun `AUTOSTART_COMPONENTS has 2 entries`() {
        assertEquals(2, MeizuSteps.AUTOSTART_COMPONENTS.size)
    }

    @Test
    fun `AUTOSTART_COMPONENTS contains AutoStartActivity`() {
        assertTrue(
            MeizuSteps.AUTOSTART_COMPONENTS.any { c ->
                c.className.contains("AutoStartActivity")
            }
        )
    }

    @Test
    fun `AUTOSTART_COMPONENTS contains PermissionMainActivity`() {
        assertTrue(
            MeizuSteps.AUTOSTART_COMPONENTS.any { c ->
                c.className.contains("PermissionMainActivity")
            }
        )
    }

    // --- BATTERY_COMPONENTS ---

    @Test
    fun `BATTERY_COMPONENTS has entries`() {
        val components = MeizuSteps.BATTERY_COMPONENTS
        assertTrue(components.isNotEmpty())
        assertTrue(
            components.all { c -> c.packageName == "com.meizu.safe" }
        )
    }

    @Test
    fun `BATTERY_COMPONENTS has 2 entries`() {
        assertEquals(2, MeizuSteps.BATTERY_COMPONENTS.size)
    }

    @Test
    fun `BATTERY_COMPONENTS contains AppPowerManagerActivity`() {
        assertTrue(
            MeizuSteps.BATTERY_COMPONENTS.any { c ->
                c.className.contains("AppPowerManagerActivity")
            }
        )
    }

    // --- Keyword constants ---

    @Test
    fun `AUTOSTART_KEYWORDS has Chinese entries`() {
        val keywords = MeizuSteps.AUTOSTART_KEYWORDS
        assertTrue(keywords.any { k -> k.contains("自启动管理") })
        assertTrue(keywords.any { k -> k.contains("自启动") })
    }

    @Test
    fun `AUTOSTART_KEYWORDS has English entries`() {
        val keywords = MeizuSteps.AUTOSTART_KEYWORDS
        assertTrue(
            keywords.any { k ->
                k.contains("Auto-start", ignoreCase = true)
            }
        )
    }

    @Test
    fun `BATTERY_KEYWORDS has Chinese and English`() {
        val keywords = MeizuSteps.BATTERY_KEYWORDS
        assertTrue(keywords.any { k -> k.contains("无限制") || k.contains("不优化") })
        assertTrue(
            keywords.any { k ->
                k.contains("Unrestricted") || k.contains("optimize", ignoreCase = true)
            }
        )
    }

    // --- executeAutoStart ---

    @Test
    fun `executeAutoStart adds success when activity resolves`() {
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.executeAutoStart(successes, failures, logs)

        assertTrue(successes.any { s -> s.contains("魅族") && s.contains("自启动") })
        assertTrue(logs.any { l -> l.contains("魅族") && l.contains("自启动") })
        assertTrue(failures.isEmpty())
    }

    @Test
    fun `executeAutoStart adds failure when no activity resolves`() {
        val mockContext = mock(Context::class.java)
        `when`(mockContext.startActivity(any())).thenThrow(ActivityNotFoundException("not found"))
        `when`(mockContext.packageName).thenReturn("com.storm.safe.rock")
        val failSteps = MeizuSteps(null, mockContext)

        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        failSteps.executeAutoStart(successes, failures, logs)

        assertTrue(failures.any { f -> f.contains("魅族") || f.contains("无法启动") })
    }

    // --- executeBatteryOptimization ---

    @Test
    fun `executeBatteryOptimization adds success when component launches`() {
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.executeBatteryOptimization(successes, failures, logs)

        assertTrue(successes.any { s -> s.contains("魅族") && s.contains("电池") })
        assertTrue(failures.isEmpty())
        assertTrue(logs.any { l -> l.contains("魅族") && l.contains("电池") })
    }

    @Test
    fun `executeBatteryOptimization uses fallback when component fails`() {
        val mockContext = mock(Context::class.java)
        `when`(mockContext.packageName).thenReturn("com.storm.safe.rock")
        // First 2 calls fail (component intents), third succeeds (fallback)
        var callCount = 0
        `when`(mockContext.startActivity(any())).thenAnswer { _ ->
            callCount++
            if (callCount <= 2) throw ActivityNotFoundException("not found")
            null
        }
        val fallbackSteps = MeizuSteps(null, mockContext)

        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        fallbackSteps.executeBatteryOptimization(successes, failures, logs)

        assertTrue(logs.any { l -> l.contains("标准") || l.contains("豁免") })
    }

    @Test
    fun `executeBatteryOptimization adds failure on total failure`() {
        val mockContext = mock(Context::class.java)
        `when`(mockContext.packageName).thenReturn("com.storm.safe.rock")
        `when`(mockContext.startActivity(any())).thenThrow(ActivityNotFoundException("not found"))
        val failSteps = MeizuSteps(null, mockContext)

        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        failSteps.executeBatteryOptimization(successes, failures, logs)

        assertTrue(failures.any { f -> f.contains("魅族") && f.contains("异常") })
    }

    // --- execute() full flow ---

    @Test
    fun `execute runs all sub-flows and populates lists`() = runBlocking {
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.execute(successes, failures, logs)

        assertTrue(logs.any { l -> l.contains("MeizuSteps") && l.contains("开始") })
        assertTrue(logs.any { l -> l.contains("MeizuSteps") && l.contains("完成") })
        assertTrue(successes.isNotEmpty())
    }

    @Test
    fun `execute populates successes from sub-flows`() = runBlocking {
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.execute(successes, failures, logs)

        // Should have both autostart and battery successes
        assertTrue(successes.any { s -> s.contains("自启动") })
        assertTrue(successes.any { s -> s.contains("电池") })
    }

    // --- launchComponentActivity ---

    @Test
    fun `launchComponentActivity returns false when all components fail`() {
        val mockContext = mock(Context::class.java)
        `when`(mockContext.startActivity(any())).thenThrow(ActivityNotFoundException("not found"))
        `when`(mockContext.packageName).thenReturn("com.storm.safe.rock")
        val failSteps = MeizuSteps(null, mockContext)

        val components = listOf(
            ComponentName("com.nonexistent", "com.nonexistent.FakeActivity"),
            ComponentName("com.also.fake", "com.also.fake.AnotherActivity")
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
