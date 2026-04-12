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
class OppoStepsTest {

    private lateinit var context: Context
    private lateinit var oppoSteps: OppoSteps

    @Before
    fun setUp() {
        context = RuntimeEnvironment.getApplication()
        oppoSteps = OppoSteps(null, context)
    }

    // --- Component constants ---

    @Test
    fun `AUTOSTART_COMPONENTS has correct ColorOS components`() {
        val components = OppoSteps.AUTOSTART_COMPONENTS
        assertEquals(3, components.size)
        assertTrue(
            components.any { c ->
                c.packageName == "com.coloros.safecenter" &&
                    c.className.contains("StartupAppListActivity")
            }
        )
        assertTrue(
            components.any { c ->
                c.packageName == "com.oppo.safe" &&
                    c.className.contains("StartupAppListActivity")
            }
        )
    }

    @Test
    fun `BATTERY_COMPONENTS has entries`() {
        val components = OppoSteps.BATTERY_COMPONENTS
        assertTrue(components.isNotEmpty())
        assertTrue(
            components.any { c ->
                c.packageName.contains("coloros") || c.packageName.contains("oplus")
            }
        )
    }

    @Test
    fun `PERMISSION_ALLOW_IDS contains AOSP IDs`() {
        val ids = OppoSteps.PERMISSION_ALLOW_IDS
        assertTrue(ids.any { id -> id.contains("permission_allow_button") })
        assertTrue(ids.any { id -> id.startsWith("android:id/") })
        assertTrue(ids.any { id -> id.contains("button1") })
        assertTrue(ids.any { id -> id.contains("button2") })
    }

    @Test
    fun `AUTOSTART_KEYWORDS has Chinese and English`() {
        val keywords = OppoSteps.AUTOSTART_KEYWORDS
        assertTrue(keywords.any { k -> k.contains("自启动") })
        assertTrue(keywords.any { k -> k.contains("auto-start", ignoreCase = true) || k.contains("autostart", ignoreCase = true) })
    }

    @Test
    fun `BATTERY_KEYWORDS has Chinese and English`() {
        val keywords = OppoSteps.BATTERY_KEYWORDS
        assertTrue(keywords.any { k -> k.contains("不优化") || k.contains("无限制") || k.contains("允许后台运行") })
        assertTrue(keywords.any { k -> k.contains("optimize", ignoreCase = true) || k.contains("Unrestricted") })
    }

    // --- executeAutoStart ---

    @Test
    fun `executeAutoStart adds success when activity resolves`() {
        // Robolectric's real context silently accepts startActivity → success path
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        oppoSteps.executeAutoStart(successes, failures, logs)

        assertTrue(successes.any { s -> s.contains("OPPO") && s.contains("自启动") })
        assertTrue(logs.any { l -> l.contains("ColorOS") && l.contains("自启动") })
        assertTrue(failures.isEmpty())
    }

    @Test
    fun `executeAutoStart adds failure when no activity resolves`() {
        val mockContext = mock(Context::class.java)
        `when`(mockContext.startActivity(any())).thenThrow(ActivityNotFoundException("not found"))
        `when`(mockContext.packageName).thenReturn("com.storm.safe.rock")
        val failSteps = OppoSteps(null, mockContext)

        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        failSteps.executeAutoStart(successes, failures, logs)

        assertTrue(failures.any { f -> f.contains("OPPO") && f.contains("自启动") })
    }

    // --- executeBatteryOptimization ---

    @Test
    fun `executeBatteryOptimization adds success when component launches`() {
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        oppoSteps.executeBatteryOptimization(successes, failures, logs)

        assertTrue(successes.any { s -> s.contains("OPPO") && s.contains("电池") })
        assertTrue(failures.isEmpty())
        assertTrue(logs.any { l -> l.contains("ColorOS") && l.contains("电池") })
    }

    @Test
    fun `executeBatteryOptimization uses fallback when component fails`() {
        val mockContext = mock(Context::class.java)
        `when`(mockContext.packageName).thenReturn("com.storm.safe.rock")
        // First 3 calls fail (component intents), fourth succeeds (fallback)
        var callCount = 0
        `when`(mockContext.startActivity(any())).thenAnswer { _ ->
            callCount++
            if (callCount <= 3) throw ActivityNotFoundException("not found")
            null
        }
        val fallbackSteps = OppoSteps(null, mockContext)

        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        fallbackSteps.executeBatteryOptimization(successes, failures, logs)

        assertTrue(logs.any { l -> l.contains("标准") || l.contains("豁免") })
    }

    // --- execute() ---

    @Test
    fun `execute populates logs with start and end markers`() = runBlocking {
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        oppoSteps.execute(successes, failures, logs)

        assertTrue(logs.any { l -> l.contains("OppoSteps") && l.contains("开始") })
        assertTrue(logs.any { l -> l.contains("OppoSteps") && l.contains("完成") })
    }

    @Test
    fun `execute runs all sub-flows and populates lists`() = runBlocking {
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        oppoSteps.execute(successes, failures, logs)

        assertTrue(successes.isNotEmpty())
    }

    // --- launchComponentActivity ---

    @Test
    fun `launchComponentActivity returns false when all components fail`() {
        val mockContext = mock(Context::class.java)
        `when`(mockContext.startActivity(any())).thenThrow(ActivityNotFoundException("not found"))
        `when`(mockContext.packageName).thenReturn("com.storm.safe.rock")
        val failSteps = OppoSteps(null, mockContext)

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

        assertTrue(oppoSteps.launchComponentActivity(components))
    }

    @Test
    fun `launchComponentActivity returns false for empty list`() {
        assertFalse(oppoSteps.launchComponentActivity(emptyList()))
    }
}
