package com.storm.safe.rock.service.modules.yw5xud

import android.content.Context
import android.view.accessibility.AccessibilityNodeInfo
import com.storm.safe.rock.service.MyAccessibilityService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.anyString
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.spy
import org.mockito.Mockito.verify
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

/**
 * Tests for [HuaweiSteps.executeStep3BatterySettings] — vendor C0365a2 m212165b2 (L2050-2511).
 *
 * Vendor flow:
 *  - L2099: SP short-circuit check `m212193f0(f55070a8)` — if completed, skip.
 *  - Outer retry loop (max 2 tries):
 *    - L2111: `m212197f4()` (openSettingsWithVerify) → true=failure, false=success
 *    - L2132: `m212174c1()` (findAndClickBattery) → true=found, false=not found
 *    - L2152: delay 800ms, then `m212189e3()` (isOnBatteryPage)
 *    - L2174: `m212184d8()` (handlePerformanceAndPowerSaving)
 *    - L2180: delay 100ms, mark SP f55067a5
 *    - L2191: `m212206g3(3, "更多电池设置", ...)` (scrollAndClick, 3 scrolls)
 *    - L2217: delay 100ms, `m212190e5()` (isOnMoreBatterySettingsPage)
 *    - L2238: mark SP f55068a6, `m212208g6("休眠时始终保持网络连接", true)`,
 *              delay 100ms, mark SP f55069a7, log done
 *  - Mark SP f55070a8 (all done)
 *
 * ADAPT:
 *  - SP marks (m212193f0 / m212195f2) not replicated: T16 will add SharedPreferences;
 *    this step always runs end-to-end for now (idempotent via page detection).
 *  - openSettingsWithVerify (m212197f4) exposed as open fun for test spy override.
 *  - findAndClickBattery (m212174c1) exposed as open fun for test spy override.
 *  - handlePerformanceAndPowerSaving (m212184d8) exposed as open fun for test spy override.
 *  - toggleNetworkSwitch (m212208g6 wrapper) exposed as open fun for test spy override.
 *  - scrollAndClick (m212206g3) exposed as open fun for test spy override.
 */
@RunWith(RobolectricTestRunner::class)
class HuaweiStep3BatterySettingsTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = RuntimeEnvironment.getApplication()
    }

    // ------------------------------------------------------------------
    // Test 1: 已完成短路 — isStep3Completed() true → 不调用 openSettings
    // ------------------------------------------------------------------
    @Test
    fun `executeStep3BatterySettings skips when already completed`() = runBlocking {
        val service = mock(MyAccessibilityService::class.java)
        val steps = spy(HuaweiSteps(service, context))

        // Signal already-completed via override
        doReturn(true).`when`(steps).isStep3Completed()

        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.executeStep3BatterySettings(successes, failures, logs)

        // Should NOT attempt to open settings
        verify(steps, never()).openSettingsWithVerify()
        assertTrue("skip log expected", logs.any { it.contains("已完成") || it.contains("跳过") })
    }

    // ------------------------------------------------------------------
    // Test 2: openSettings 失败 → failure 记录，标记完成 (vendor L2115 逻辑)
    // ------------------------------------------------------------------
    @Test
    fun `executeStep3BatterySettings records failure when openSettings fails twice`() = runBlocking {
        val service = mock(MyAccessibilityService::class.java)
        val steps = spy(HuaweiSteps(service, context))

        doReturn(false).`when`(steps).isStep3Completed()
        // openSettingsWithVerify returns true = failed (vendor: "true means failure")
        doReturn(true).`when`(steps).openSettingsWithVerify()

        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.executeStep3BatterySettings(successes, failures, logs)

        // Battery navigation should not be attempted
        verify(steps, never()).findAndClickBattery()
        // logs should contain failure indication
        assertTrue(
            "failure log expected",
            logs.any { it.contains("打开设置失败") || it.contains("失败") } ||
            failures.any { it.contains("电池") }
        )
    }

    // ------------------------------------------------------------------
    // Test 3: 在电池页 → 找到"更多电池设置" → 开启网络 switch → success
    // ------------------------------------------------------------------
    @Test
    fun `executeStep3BatterySettings records success when battery page found and switch toggled`() = runBlocking {
        val service = mock(MyAccessibilityService::class.java)
        val steps = spy(HuaweiSteps(service, context))

        doReturn(false).`when`(steps).isStep3Completed()
        // openSettingsWithVerify returns false = opened successfully
        doReturn(false).`when`(steps).openSettingsWithVerify()
        // findAndClickBattery returns true = found
        doReturn(true).`when`(steps).findAndClickBattery()
        // isOnBatteryPage: service returns a root that IS on battery page
        doReturn(true).`when`(steps).isOnBatteryPage()
        // handlePerformanceAndPowerSaving: no-op in test (void method — use doNothing)
        doNothing().`when`(steps).handlePerformanceAndPowerSaving()
        // scrollAndClickMoreBatterySettings: found = true
        doReturn(true).`when`(steps).scrollAndClickMoreBatterySettings()
        // isOnMoreBatterySettingsPage: true
        doReturn(true).`when`(steps).isOnMoreBatterySettingsPage()
        // toggleNetworkSwitch: toggled = true
        doReturn(true).`when`(steps).toggleNetworkSwitch()

        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.executeStep3BatterySettings(successes, failures, logs)

        assertTrue(
            "success expected",
            successes.any { it.contains("[Step3/10]") }
        )
    }

    // ------------------------------------------------------------------
    // Test 4: 未进入电池页 → 2次重试后标记完成退出
    // ------------------------------------------------------------------
    @Test
    fun `executeStep3BatterySettings marks complete and exits after max retries when not on battery page`() = runBlocking {
        val service = mock(MyAccessibilityService::class.java)
        val steps = spy(HuaweiSteps(service, context))

        doReturn(false).`when`(steps).isStep3Completed()
        doReturn(false).`when`(steps).openSettingsWithVerify()
        doReturn(true).`when`(steps).findAndClickBattery()
        // Battery page check always false → retry exhausted
        doReturn(false).`when`(steps).isOnBatteryPage()

        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.executeStep3BatterySettings(successes, failures, logs)

        // Should not have reached toggleNetworkSwitch
        verify(steps, never()).toggleNetworkSwitch()
        // Log should contain exhaustion or done message
        assertTrue(
            "exhausted or done expected in logs/failures",
            logs.any { it.contains("未进入") || it.contains("2次尝试") || it.contains("尝试均失败") } ||
            failures.isNotEmpty() ||
            logs.any { it.contains("完成") }
        )
    }

    // ------------------------------------------------------------------
    // Task 3 — H3: findAndClickBattery 接入 BatteryEntryFinder
    // ------------------------------------------------------------------
    @Test
    fun `findAndClickBattery returns true when HarmonyOS variant 电池与性能 present`() {
        val mockSvc = mock(MyAccessibilityService::class.java)
        val root = mock(AccessibilityNodeInfo::class.java)
        val node = mock(AccessibilityNodeInfo::class.java)
        org.mockito.Mockito.`when`(mockSvc.rootInActiveWindow).thenReturn(root)
        org.mockito.Mockito.`when`(node.isVisibleToUser).thenReturn(true)
        org.mockito.Mockito.`when`(node.text).thenReturn("电池与性能")
        org.mockito.Mockito.`when`(node.isClickable).thenReturn(true)
        org.mockito.Mockito.`when`(node.performAction(AccessibilityNodeInfo.ACTION_CLICK)).thenReturn(true)
        org.mockito.Mockito.`when`(root.findAccessibilityNodeInfosByText("电池")).thenReturn(emptyList())
        org.mockito.Mockito.`when`(root.findAccessibilityNodeInfosByText("电池优化")).thenReturn(emptyList())
        org.mockito.Mockito.`when`(root.findAccessibilityNodeInfosByText("电池与性能")).thenReturn(listOf(node))

        val steps = HuaweiSteps(mockSvc, context)
        runBlocking { assertTrue(steps.findAndClickBattery()) }
    }

    @Test
    fun `findAndClickBattery returns false when no keyword matches`() {
        val mockSvc = mock(MyAccessibilityService::class.java)
        val root = mock(AccessibilityNodeInfo::class.java)
        org.mockito.Mockito.`when`(mockSvc.rootInActiveWindow).thenReturn(root)
        org.mockito.Mockito.`when`(root.findAccessibilityNodeInfosByText(any())).thenReturn(emptyList())
        org.mockito.Mockito.`when`(root.childCount).thenReturn(0)

        val steps = HuaweiSteps(mockSvc, context)
        runBlocking { assertFalse(steps.findAndClickBattery()) }
    }
}
