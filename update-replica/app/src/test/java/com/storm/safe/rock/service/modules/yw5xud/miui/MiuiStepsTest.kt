package com.storm.safe.rock.service.modules.yw5xud.miui

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
import org.robolectric.Shadows.shadowOf
import kotlinx.coroutines.runBlocking

@RunWith(RobolectricTestRunner::class)
class MiuiStepsTest {

    private lateinit var context: Context
    private lateinit var steps: MiuiSteps

    @Before
    fun setUp() {
        context = RuntimeEnvironment.getApplication()
        steps = MiuiSteps(null, context)
    }

    // --- Constructor and companion ---

    @Test
    fun `constructor accepts null service and context`() {
        val s = MiuiSteps(null, context)
        assertNotNull(s)
    }

    @Test
    fun `TAG is MiuiSteps`() {
        // private const val in companion is compiled as a static field on the outer class
        val field = MiuiSteps::class.java.getDeclaredField("TAG")
        field.isAccessible = true
        assertEquals("MiuiSteps", field.get(null))
    }

    // --- AUTOSTART_COMPONENTS ---

    @Test
    fun `AUTOSTART_COMPONENTS has correct package`() {
        assertTrue(MiuiConstants.AUTOSTART_COMPONENTS.all { c -> c.packageName == "com.miui.securitycenter" })
    }

    @Test
    fun `AUTOSTART_COMPONENTS has correct class names`() {
        val classNames = MiuiConstants.AUTOSTART_COMPONENTS.map { c -> c.className }
        assertTrue(classNames.any { name -> name.contains("AutoStartManagementActivity") })
    }

    @Test
    fun `AUTOSTART_COMPONENTS has 2 entries`() {
        assertEquals(2, MiuiConstants.AUTOSTART_COMPONENTS.size)
    }

    // --- BATTERY_COMPONENTS ---

    @Test
    fun `BATTERY_COMPONENTS has correct entries`() {
        assertEquals(2, MiuiConstants.BATTERY_COMPONENTS.size)
        assertTrue(MiuiConstants.BATTERY_COMPONENTS.any { c -> c.packageName == "com.miui.powerkeeper" })
        assertTrue(MiuiConstants.BATTERY_COMPONENTS.any { c -> c.packageName == "com.miui.securitycenter" })
    }

    @Test
    fun `BATTERY_COMPONENTS contains HiddenAppsConfigActivity`() {
        assertTrue(MiuiConstants.BATTERY_COMPONENTS.any { c -> c.className.contains("HiddenAppsConfigActivity") })
    }

    // --- Keywords ---

    @Test
    fun `AUTOSTART_KEYWORDS contains Chinese keywords`() {
        assertTrue(MiuiConstants.AUTOSTART_KEYWORDS.contains("自启动"))
        assertTrue(MiuiConstants.AUTOSTART_KEYWORDS.contains("自动启动"))
    }

    @Test
    fun `AUTOSTART_KEYWORDS contains English keywords`() {
        assertTrue(MiuiConstants.AUTOSTART_KEYWORDS.contains("Auto-start"))
        assertTrue(MiuiConstants.AUTOSTART_KEYWORDS.contains("Autostart"))
        assertTrue(MiuiConstants.AUTOSTART_KEYWORDS.contains("Auto start"))
    }

    @Test
    fun `BATTERY_NO_RESTRICT_KEYWORDS contains expected keywords`() {
        assertTrue(MiuiConstants.BATTERY_NO_RESTRICT_KEYWORDS.contains("无限制"))
        assertTrue(MiuiConstants.BATTERY_NO_RESTRICT_KEYWORDS.contains("No restrictions"))
        assertTrue(MiuiConstants.BATTERY_NO_RESTRICT_KEYWORDS.contains("Unrestricted"))
        assertTrue(MiuiConstants.BATTERY_NO_RESTRICT_KEYWORDS.contains("不限制"))
    }

    @Test
    fun `BG_POPUP_KEYWORDS contains expected keywords`() {
        assertTrue(MiuiConstants.BG_POPUP_KEYWORDS.contains("后台弹出界面"))
        assertTrue(MiuiConstants.BG_POPUP_KEYWORDS.contains("后台弹窗"))
        assertTrue(MiuiConstants.BG_POPUP_KEYWORDS.contains("Background pop-up"))
        assertTrue(MiuiConstants.BG_POPUP_KEYWORDS.contains("Display pop-up"))
    }

    // --- executeAutoStart ---

    @Test
    fun `executeAutoStart success when activity resolves`() {
        // Robolectric will not throw on startActivity for unknown components,
        // so the first component will succeed
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.executeAutoStart(successes, failures, logs)

        assertTrue(successes.any { s -> s.contains("自启动") || s.contains("应用详情") })
        assertTrue(logs.any { s -> s.contains("自启动") || s.contains("应用详情") || s.contains("安全中心") })
        assertTrue(failures.isEmpty())
    }

    @Test
    fun `executeAutoStart failure when no activity resolves`() {
        // Use a mock context that always throws ActivityNotFoundException
        val mockContext = mock(Context::class.java)
        `when`(mockContext.startActivity(any())).thenThrow(ActivityNotFoundException("not found"))
        `when`(mockContext.packageName).thenReturn("com.storm.safe.rock")
        val failSteps = MiuiSteps(null, mockContext)

        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        failSteps.executeAutoStart(successes, failures, logs)

        assertTrue(failures.any { s -> s.contains("无法启动") || s.contains("异常") })
    }

    // --- executeBatterySaver ---

    @Test
    fun `executeBatterySaver success when component launches`() {
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.executeBatterySaver(successes, failures, logs)

        assertTrue(successes.any { s -> s.contains("省电策略") })
        assertTrue(failures.isEmpty())
    }

    @Test
    fun `executeBatterySaver uses fallback when component fails`() {
        // Mock context where component intents fail but generic intents succeed
        val mockContext = mock(Context::class.java)
        `when`(mockContext.packageName).thenReturn("com.storm.safe.rock")
        // First two calls fail (component intents), third succeeds (fallback)
        var callCount = 0
        `when`(mockContext.startActivity(any())).thenAnswer { inv ->
            callCount++
            if (callCount <= 2) throw ActivityNotFoundException("not found")
            null
        }
        val fallbackSteps = MiuiSteps(null, mockContext)

        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        fallbackSteps.executeBatterySaver(successes, failures, logs)

        assertTrue(logs.any { s -> s.contains("回退") })
    }

    // --- executeBackgroundPopup ---

    @Test
    fun `executeBackgroundPopup success`() {
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.executeBackgroundPopup(successes, failures, logs)

        assertTrue(successes.any { s -> s.contains("后台弹窗") })
        assertTrue(logs.any { s -> s.contains("权限编辑器") })
    }

    @Test
    fun `executeBackgroundPopup logs on failure`() {
        val mockContext = mock(Context::class.java)
        `when`(mockContext.packageName).thenReturn("com.storm.safe.rock")
        `when`(mockContext.startActivity(any())).thenThrow(ActivityNotFoundException("not found"))
        val failSteps = MiuiSteps(null, mockContext)

        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        failSteps.executeBackgroundPopup(successes, failures, logs)

        // On failure, it logs but doesn't add to failures (per vendor fallback behavior)
        assertTrue(logs.any { s -> s.contains("启动失败") })
        assertTrue(successes.isEmpty())
    }

    // --- execute() full flow ---

    @Test
    fun `execute runs all sub-flows and populates lists`() = runBlocking {
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.execute(successes, failures, logs)

        // Should have start and end log messages
        assertTrue(logs.any { s -> s.contains("开始小米") })
        assertTrue(logs.any { s -> s.contains("配置完成") })
        // Should have successes from each sub-flow
        assertTrue(successes.isNotEmpty())
    }

    // ═══ Permission management constants (vendor m212256b5) ═══

    @Test
    fun `PERM_MGMT_ENTRY_KEYWORDS contains Chinese and English`() {
        assertTrue(MiuiConstants.PERM_MGMT_ENTRY_KEYWORDS.contains("权限管理"))
        assertTrue(MiuiConstants.PERM_MGMT_ENTRY_KEYWORDS.any { it.contains("Permission") })
    }

    @Test
    fun `PERM_MGMT_ITEMS has 6 permission groups`() {
        assertEquals(6, MiuiConstants.PERM_MGMT_ITEMS.size)
    }

    @Test
    fun `PERM_MGMT_ITEMS contains overlay permission`() {
        val names = MiuiConstants.PERM_MGMT_ITEMS.map { it.first }
        assertTrue(names.contains("显示悬浮窗"))
    }

    @Test
    fun `PERM_MGMT_ITEMS contains background popup`() {
        val names = MiuiConstants.PERM_MGMT_ITEMS.map { it.first }
        assertTrue(names.contains("后台弹出界面"))
    }

    @Test
    fun `PERM_MGMT_ITEMS each has at least 2 keywords`() {
        for ((name, keywords) in MiuiConstants.PERM_MGMT_ITEMS) {
            assertTrue("$name should have at least 2 keywords", keywords.size >= 2)
        }
    }

    @Test
    fun `PERM_ALLOW_KEYWORDS contains always allow in Chinese and English`() {
        assertTrue(MiuiConstants.PERM_ALLOW_KEYWORDS.contains("始终允许"))
        assertTrue(MiuiConstants.PERM_ALLOW_KEYWORDS.contains("Allow always"))
    }

    @Test
    fun `APP_DETAIL_VALIDATION_KEYWORDS has Chinese and English entries`() {
        assertTrue(MiuiConstants.APP_DETAIL_VALIDATION_KEYWORDS.contains("权限管理"))
        assertTrue(MiuiConstants.APP_DETAIL_VALIDATION_KEYWORDS.contains("Permissions"))
    }

    // ═══ returnToHome — vendor m212280e5: 3x BACK + 1x HOME ═══

    @Test
    fun `returnToHome calls BACK 3 times then HOME`() = runBlocking {
        val mockService = mock(com.storm.safe.rock.service.MyAccessibilityService::class.java)
        val stepsWithService = MiuiSteps(mockService, context)
        stepsWithService.returnToHome()
        verify(mockService, times(3)).performGlobalAction(
            eq(android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_BACK))
        verify(mockService, times(1)).performGlobalAction(
            eq(android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_HOME))
        Unit
    }

    @Test
    fun `returnToHome does not crash with null service`() = runBlocking {
        steps.returnToHome()
    }
}
