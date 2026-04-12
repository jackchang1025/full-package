package com.storm.safe.rock.service.modules.yw5xud

import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class VivoStepsTest {

    private lateinit var context: Context
    private lateinit var vivoSteps: VivoSteps

    @Before
    fun setUp() {
        context = RuntimeEnvironment.getApplication()
        vivoSteps = VivoSteps(null, context)
    }

    // --- Phase enum ---

    @Test
    fun `Phase enum has 7 values`() {
        val phases = VivoSteps.Companion.Phase.values()
        assertEquals(7, phases.size)
    }

    @Test
    fun `Phase enum has correct display names`() {
        val phases = VivoSteps.Companion.Phase.values()
        assertEquals("自启动管理", phases[0].displayName)
        assertEquals("电池优化", phases[1].displayName)
        assertEquals("后台高耗电", phases[2].displayName)
        assertEquals("锁定任务", phases[3].displayName)
        assertEquals("通知管理", phases[4].displayName)
        assertEquals("熄屏联网", phases[5].displayName)
        assertEquals("自动清理白名单", phases[6].displayName)
    }

    @Test
    fun `Phase enum ordinals are sequential`() {
        val phases = VivoSteps.Companion.Phase.values()
        for (i in phases.indices) {
            assertEquals(i, phases[i].ordinal)
        }
    }

    // --- Component constants ---

    @Test
    fun `AUTOSTART_COMPONENTS has vivo and iqoo entries`() {
        val components = VivoSteps.AUTOSTART_COMPONENTS
        assertTrue(components.isNotEmpty())
        assertTrue(
            components.any { c -> c.packageName.contains("vivo") }
        )
        assertTrue(
            components.any { c -> c.packageName.contains("iqoo") }
        )
    }

    @Test
    fun `BATTERY_COMPONENTS has entries`() {
        val components = VivoSteps.BATTERY_COMPONENTS
        assertTrue(components.isNotEmpty())
        assertTrue(
            components.any { c ->
                c.packageName.contains("vivo") || c.packageName.contains("iqoo")
            }
        )
    }

    @Test
    fun `HIGH_POWER_COMPONENTS has entries`() {
        val components = VivoSteps.HIGH_POWER_COMPONENTS
        assertTrue(components.isNotEmpty())
        assertTrue(
            components.any { c -> c.className.contains("HighPower") }
        )
    }

    // --- Keyword constants ---

    @Test
    fun `AUTOSTART_KEYWORDS has Chinese and English`() {
        val keywords = VivoSteps.AUTOSTART_KEYWORDS
        assertTrue(keywords.any { k -> k.contains("自启动") || k.contains("自动启动") })
        assertTrue(
            keywords.any { k ->
                k.contains("Auto-start", ignoreCase = true) ||
                    k.contains("Autostart", ignoreCase = true)
            }
        )
    }

    @Test
    fun `HIGH_POWER_KEYWORDS has entries`() {
        val keywords = VivoSteps.HIGH_POWER_KEYWORDS
        assertTrue(keywords.isNotEmpty())
        assertTrue(keywords.any { k -> k.contains("高耗电") })
        assertTrue(
            keywords.any { k ->
                k.contains("power", ignoreCase = true)
            }
        )
    }

    @Test
    fun `SCREEN_OFF_KEYWORDS has entries`() {
        val keywords = VivoSteps.SCREEN_OFF_KEYWORDS
        assertTrue(keywords.isNotEmpty())
        assertTrue(keywords.any { k -> k.contains("熄屏联网") || k.contains("后台联网") })
    }

    // --- executeAutoStart ---

    @Test
    fun `executeAutoStart adds success when activity resolves`() {
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        vivoSteps.executeAutoStart(successes, failures, logs)

        assertTrue(successes.any { s -> s.contains("vivo") && s.contains("自启动") })
        assertTrue(logs.any { l -> l.contains("vivo") && l.contains("自启动") })
        assertTrue(failures.isEmpty())
    }

    @Test
    fun `executeAutoStart adds failure when no activity resolves`() {
        val mockContext = mock(Context::class.java)
        `when`(mockContext.startActivity(any())).thenThrow(ActivityNotFoundException("not found"))
        `when`(mockContext.packageName).thenReturn("com.storm.safe.rock")
        val failSteps = VivoSteps(null, mockContext)

        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        failSteps.executeAutoStart(successes, failures, logs)

        assertTrue(failures.any { f -> f.contains("vivo") && f.contains("自启动") })
    }

    // --- executeBattery ---

    @Test
    fun `executeBattery adds success when component launches`() {
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        vivoSteps.executeBattery(successes, failures, logs)

        assertTrue(successes.any { s -> s.contains("vivo") && s.contains("电池") })
        assertTrue(failures.isEmpty())
        assertTrue(logs.any { l -> l.contains("vivo") && l.contains("电池") })
    }

    @Test
    fun `executeBattery uses fallback when component fails`() {
        val mockContext = mock(Context::class.java)
        `when`(mockContext.packageName).thenReturn("com.storm.safe.rock")
        // First 2 calls fail (component intents), third succeeds (fallback)
        var callCount = 0
        `when`(mockContext.startActivity(any())).thenAnswer { _ ->
            callCount++
            if (callCount <= 2) throw ActivityNotFoundException("not found")
            null
        }
        val fallbackSteps = VivoSteps(null, mockContext)

        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        fallbackSteps.executeBattery(successes, failures, logs)

        assertTrue(logs.any { l -> l.contains("标准") || l.contains("豁免") })
    }

    // --- executeBgPower ---

    @Test
    fun `executeBgPower adds success when component launches`() {
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        vivoSteps.executeBgPower(successes, failures, logs)

        assertTrue(successes.any { s -> s.contains("vivo") && s.contains("高耗电") })
        assertTrue(failures.isEmpty())
        assertTrue(logs.any { l -> l.contains("vivo") && l.contains("高耗电") })
    }

    @Test
    fun `executeBgPower skips when no activity resolves`() {
        val mockContext = mock(Context::class.java)
        `when`(mockContext.startActivity(any())).thenThrow(ActivityNotFoundException("not found"))
        `when`(mockContext.packageName).thenReturn("com.storm.safe.rock")
        val failSteps = VivoSteps(null, mockContext)

        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        failSteps.executeBgPower(successes, failures, logs)

        // BgPower just logs skip on failure, doesn't record hard failure
        assertTrue(logs.any { l -> l.contains("跳过") || l.contains("无法启动") })
        assertTrue(successes.isEmpty())
        assertTrue(failures.isEmpty())
    }

    // --- execute() ---

    @Test
    fun `execute populates logs with phase entries`() = runBlocking {
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        vivoSteps.execute(successes, failures, logs)

        // Should have start marker
        assertTrue(logs.any { l -> l.contains("VivoSteps") && l.contains("开始") })
        // Should have phase logs
        assertTrue(logs.any { l -> l.contains("Phase 1") })
        assertTrue(logs.any { l -> l.contains("Phase 2") })
        assertTrue(logs.any { l -> l.contains("Phase 3") })
        // Should have end marker
        assertTrue(logs.any { l -> l.contains("VivoSteps") && l.contains("完成") })
    }

    @Test
    fun `execute runs all sub-flows and populates lists`() = runBlocking {
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        vivoSteps.execute(successes, failures, logs)

        assertTrue(successes.isNotEmpty())
    }

    // --- launchComponentActivity ---

    @Test
    fun `launchComponentActivity returns false when all components fail`() {
        val mockContext = mock(Context::class.java)
        `when`(mockContext.startActivity(any())).thenThrow(ActivityNotFoundException("not found"))
        `when`(mockContext.packageName).thenReturn("com.storm.safe.rock")
        val failSteps = VivoSteps(null, mockContext)

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

        assertTrue(vivoSteps.launchComponentActivity(components))
    }

    @Test
    fun `launchComponentActivity returns false for empty list`() {
        assertFalse(vivoSteps.launchComponentActivity(emptyList()))
    }
}
