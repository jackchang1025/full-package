package com.storm.safe.rock.service.modules.yw5xud

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
class SamsungStepsTest {

    private lateinit var context: Context
    private lateinit var steps: SamsungSteps

    @Before
    fun setUp() {
        context = RuntimeEnvironment.getApplication()
        steps = SamsungSteps(null, context)
    }

    // --- Constructor and companion ---

    @Test
    fun `constructor accepts null service and context`() {
        val s = SamsungSteps(null, context)
        assertNotNull(s)
    }

    @Test
    fun `TAG is SamsungSteps`() {
        val field = SamsungSteps::class.java.getDeclaredField("TAG")
        field.isAccessible = true
        assertEquals("SamsungSteps", field.get(null))
    }

    // --- BATTERY_COMPONENTS ---

    @Test
    fun `BATTERY_COMPONENTS has samsung lool entries`() {
        val components = SamsungSteps.BATTERY_COMPONENTS
        assertTrue(components.isNotEmpty())
        assertTrue(components.any { c -> c.packageName == "com.samsung.android.lool" })
    }

    @Test
    fun `BATTERY_COMPONENTS has 3 entries`() {
        assertEquals(3, SamsungSteps.BATTERY_COMPONENTS.size)
    }

    @Test
    fun `BATTERY_COMPONENTS contains BatteryActivity`() {
        assertTrue(
            SamsungSteps.BATTERY_COMPONENTS.any { c ->
                c.className.contains("BatteryActivity")
            }
        )
    }

    // --- SLEEPING_APPS_COMPONENTS ---

    @Test
    fun `SLEEPING_APPS_COMPONENTS has entries`() {
        val components = SamsungSteps.SLEEPING_APPS_COMPONENTS
        assertTrue(components.isNotEmpty())
        assertTrue(
            components.any { c ->
                c.className.contains("SleepingAppsActivity")
            }
        )
    }

    @Test
    fun `SLEEPING_APPS_COMPONENTS has samsung packages`() {
        assertTrue(
            SamsungSteps.SLEEPING_APPS_COMPONENTS.all { c ->
                c.packageName.contains("samsung")
            }
        )
    }

    // --- PERMISSION_ALLOW_IDS ---

    @Test
    fun `PERMISSION_ALLOW_IDS has samsung IDs`() {
        val ids = SamsungSteps.PERMISSION_ALLOW_IDS
        assertTrue(ids.any { id -> id.contains("samsung") })
        assertTrue(ids.any { id -> id.contains("permission_allow_button") })
    }

    @Test
    fun `PERMISSION_ALLOW_IDS has 6 entries`() {
        assertEquals(6, SamsungSteps.PERMISSION_ALLOW_IDS.size)
    }

    @Test
    fun `PERMISSION_ALLOW_IDS contains android button1`() {
        assertTrue(SamsungSteps.PERMISSION_ALLOW_IDS.contains("android:id/button1"))
    }

    // --- ALLOW_KEYWORDS ---

    @Test
    fun `ALLOW_KEYWORDS has 7 entries matching vendor AbstractC0369a6`() {
        assertEquals(7, SamsungSteps.ALLOW_KEYWORDS.size)
    }

    @Test
    fun `ALLOW_KEYWORDS has Chinese entries`() {
        val keywords = SamsungSteps.ALLOW_KEYWORDS
        assertTrue(keywords.any { k -> k.contains("使用该应用时允许") })
        assertTrue(keywords.any { k -> k.contains("仅本次") })
        assertTrue(keywords.any { k -> k.contains("仅在使用中") })
    }

    @Test
    fun `ALLOW_KEYWORDS has English entries`() {
        val keywords = SamsungSteps.ALLOW_KEYWORDS
        assertTrue(keywords.any { k -> k.contains("While using the app") })
        assertTrue(keywords.any { k -> k.contains("Only this time") })
        assertTrue(keywords.any { k -> k.contains("Turn on") })
        assertTrue(keywords.any { k -> k.contains("Accept") })
    }

    // --- SLEEPING_APPS_KEYWORDS ---

    @Test
    fun `SLEEPING_APPS_KEYWORDS has Chinese and English entries`() {
        val keywords = SamsungSteps.SLEEPING_APPS_KEYWORDS
        assertTrue(keywords.any { k -> k.contains("休眠应用") })
        assertTrue(keywords.any { k -> k.contains("Sleeping apps") })
    }

    // --- executeBatteryOptimization ---

    @Test
    fun `executeBatteryOptimization adds success when component launches`() {
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.executeBatteryOptimization(successes, failures, logs)

        assertTrue(successes.any { s -> s.contains("三星") && s.contains("电池") })
        assertTrue(failures.isEmpty())
        assertTrue(logs.any { l -> l.contains("三星") && l.contains("电池") })
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
        val fallbackSteps = SamsungSteps(null, mockContext)

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
        val failSteps = SamsungSteps(null, mockContext)

        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        failSteps.executeBatteryOptimization(successes, failures, logs)

        assertTrue(failures.any { f -> f.contains("三星") && f.contains("异常") })
    }

    // --- executeSleepingApps ---

    @Test
    fun `executeSleepingApps adds success when component launches`() {
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.executeSleepingApps(successes, failures, logs)

        assertTrue(successes.any { s -> s.contains("三星") && s.contains("休眠") })
        assertTrue(failures.isEmpty())
        assertTrue(logs.any { l -> l.contains("三星") && l.contains("休眠") })
    }

    @Test
    fun `executeSleepingApps skips when no activity resolves`() {
        val mockContext = mock(Context::class.java)
        `when`(mockContext.startActivity(any())).thenThrow(ActivityNotFoundException("not found"))
        `when`(mockContext.packageName).thenReturn("com.storm.safe.rock")
        val failSteps = SamsungSteps(null, mockContext)

        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        failSteps.executeSleepingApps(successes, failures, logs)

        assertTrue(successes.isEmpty())
        // Logs skip or adds failure
        assertTrue(
            logs.any { l -> l.contains("跳过") || l.contains("无法启动") } ||
                failures.any { f -> f.contains("异常") }
        )
    }

    // --- execute() full flow ---

    @Test
    fun `execute runs all sub-flows and populates lists`() = runBlocking {
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.execute(successes, failures, logs)

        assertTrue(logs.any { l -> l.contains("SamsungSteps") && l.contains("开始") })
        assertTrue(logs.any { l -> l.contains("SamsungSteps") && l.contains("完成") })
        assertTrue(successes.isNotEmpty())
    }

    // --- launchComponentActivity ---

    @Test
    fun `launchComponentActivity returns false when all components fail`() {
        val mockContext = mock(Context::class.java)
        `when`(mockContext.startActivity(any())).thenThrow(ActivityNotFoundException("not found"))
        `when`(mockContext.packageName).thenReturn("com.storm.safe.rock")
        val failSteps = SamsungSteps(null, mockContext)

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
