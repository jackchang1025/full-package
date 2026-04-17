package com.storm.safe.rock.service.modules.yw5xud

import android.content.Context
import com.storm.safe.rock.service.MyAccessibilityService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.anyInt
import org.mockito.Mockito.anyString
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

/**
 * Tests for [HuaweiSteps.executeStep5AutoStart] — vendor C0365a2 m212164b1 + m212196f3.
 *
 * Vendor flow (from m212164b1 coroutine SM + m212196f3 component list):
 *  - m212196f3 (L6861-6879): iterate STARTUP_COMPONENTS (4 pairs), try startActivity for each,
 *    return true on first success, false if all fail.
 *  - m212164b1 main flow:
 *    - Launch startup manager via m212196f3 (f3 method)
 *    - On fail → add failure, return
 *    - delay ~2000ms to let page load
 *    - Scroll + find app in list by appLabel (up to 8 scroll passes)
 *    - On not found → log, return (not a hard failure)
 *    - delay ~1500ms for detail page
 *    - Read rootInActiveWindow, find "允许自启动" / "允许关联启动" / "允许后台活动" switches
 *    - For each: if switch not checked → click (enable)
 *    - Record successes / log result
 *    - ADAPT: vendor may have a confirm dialog ("开启自启动将允许 xxx"); not replicated here.
 *
 * Vendor companion constants used:
 *  - AUTO_START_SWITCH_TEXTS (L192-196): ["允许自启动","允许关联启动","允许后台活动","允許..."]
 *  - STARTUP_COMPONENTS (already aligned in T4)
 */
@RunWith(RobolectricTestRunner::class)
class HuaweiStep5AutoStartTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = RuntimeEnvironment.getApplication()
    }

    // ------------------------------------------------------------------
    // Test 1: launchStartupManager fails → fallback attempted, returns early
    // ADAPT: real-device hardening — vendor 原版加 failure + return；
    // replica 改为尝试 StartupFallbackNavigator.launchAppDetailsSettings。
    // 当 mock service 无异常时 fallback 成功 → logs 有记录，no success, returns early。
    // ------------------------------------------------------------------
    @Test
    fun `executeStep5AutoStart triggers fallback and returns early when all startup components fail`() = runBlocking {
        val service = mock(MyAccessibilityService::class.java)
        val steps = spy(HuaweiSteps(service, context))

        // launchStartupManager returns false (all components fail)
        doReturn(false).`when`(steps).launchStartupManager()

        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.executeStep5AutoStart(successes, failures, logs)

        // Fallback attempted → logs must record the STARTUP_COMPONENTS denial or fallback
        assertTrue(
            "fallback attempt must be logged (STARTUP_COMPONENTS or fallback keyword)",
            logs.any { it.contains("STARTUP_COMPONENTS") || it.contains("fallback") || it.contains("应用详情页") }
        )
        assertTrue("no success when launch fails", successes.isEmpty())
    }

    // ------------------------------------------------------------------
    // Test 2: launch succeeds, app found, scrollFindAndClickApp returns true
    // vendor: page opened → scroll to find appLabel → click app row
    // ------------------------------------------------------------------
    @Test
    fun `executeStep5AutoStart records scroll-find attempt when launch succeeds`() = runBlocking {
        val service = mock(MyAccessibilityService::class.java)
        val steps = spy(HuaweiSteps(service, context))

        doReturn(true).`when`(steps).launchStartupManager()
        // App found in scroll list
        doReturn(true).`when`(steps).scrollFindAndClickApp(anyString(), anyInt())
        // No switches visible (returns 0 toggled)
        doReturn(0).`when`(steps).enableAutoStartSwitches()

        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.executeStep5AutoStart(successes, failures, logs)

        verify(steps).scrollFindAndClickApp(anyString(), anyInt())
        assertTrue("step5 log expected", logs.any { it.contains("[Step5/10]") })
    }

    // ------------------------------------------------------------------
    // Test 3: "自动管理" switch found checked=true → turns it OFF
    // vendor: the "自动管理" switch being ON means "auto-manage = restricted";
    // replica turns it OFF so that manual switches appear.
    // ------------------------------------------------------------------
    @Test
    fun `executeStep5AutoStart turns off auto-manage switch when found checked`() = runBlocking {
        val service = mock(MyAccessibilityService::class.java)
        val steps = spy(HuaweiSteps(service, context))

        doReturn(true).`when`(steps).launchStartupManager()
        doReturn(true).`when`(steps).scrollFindAndClickApp(anyString(), anyInt())
        // Stub disableAutoManageSwitch so we can verify it's called
        doReturn(true).`when`(steps).disableAutoManageSwitch()
        doReturn(0).`when`(steps).enableAutoStartSwitches()

        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.executeStep5AutoStart(successes, failures, logs)

        verify(steps).disableAutoManageSwitch()
        Unit
    }

    // ------------------------------------------------------------------
    // Test 4: "允许自启动" / "允许关联启动" / "允许后台活动" switches enabled
    // vendor AUTO_START_SWITCH_TEXTS (L192-196): these three switches are turned ON
    // ------------------------------------------------------------------
    @Test
    fun `executeStep5AutoStart records success when allow-switches enabled`() = runBlocking {
        val service = mock(MyAccessibilityService::class.java)
        val steps = spy(HuaweiSteps(service, context))

        doReturn(true).`when`(steps).launchStartupManager()
        doReturn(true).`when`(steps).scrollFindAndClickApp(anyString(), anyInt())
        doReturn(true).`when`(steps).disableAutoManageSwitch()
        // 3 switches toggled
        doReturn(3).`when`(steps).enableAutoStartSwitches()

        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.executeStep5AutoStart(successes, failures, logs)

        assertTrue(
            "success expected when switches enabled",
            successes.any { it.contains("[Step5/10]") }
        )
    }

    // ------------------------------------------------------------------
    // Test 5: app not found in list → logs "not found", no failure added
    // vendor: scrollFindAndClickApp exhausted → log and return (not a hard failure)
    // ------------------------------------------------------------------
    @Test
    fun `executeStep5AutoStart logs app not found without adding failure`() = runBlocking {
        val service = mock(MyAccessibilityService::class.java)
        val steps = spy(HuaweiSteps(service, context))

        doReturn(true).`when`(steps).launchStartupManager()
        // App NOT found after all scroll passes
        doReturn(false).`when`(steps).scrollFindAndClickApp(anyString(), anyInt())

        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.executeStep5AutoStart(successes, failures, logs)

        assertTrue("no hard failure when app not found", failures.isEmpty())
        assertTrue(
            "log entry expected mentioning app not found",
            logs.any { it.contains("[Step5/10]") }
        )
    }

    // ------------------------------------------------------------------
    // Test 6: ADAPT real-device hardening — fallback to AppDetailsSettings
    // When all STARTUP_COMPONENTS fail (launchStartupManager=false), replica
    // attempts StartupFallbackNavigator.launchAppDetailsSettings. Even if that
    // also throws (SecurityException from mock), the failure/log lists must
    // contain evidence of the fallback attempt.
    // vendor L6872-6877: catch Exception, log only, no fallback.
    // ------------------------------------------------------------------
    @Test
    fun `executeStep5AutoStart falls back to AppDetailsSettings when all STARTUP_COMPONENTS fail`() = runBlocking {
        val mockSvc = mock(MyAccessibilityService::class.java)
        `when`(mockSvc.packageName).thenReturn("com.storm.safe.rock")
        `when`(mockSvc.startActivity(org.mockito.Mockito.any())).thenThrow(SecurityException("USE_COMPONENT"))

        val steps = spy(HuaweiSteps(mockSvc, context))
        doReturn(false).`when`(steps).launchStartupManager()

        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()
        steps.executeStep5AutoStart(successes, failures, logs)

        assertTrue(
            "应记录 fallback 尝试（failures 或 logs 中含 'fallback' 或 '自启动'）",
            failures.any { it.contains("自启动") || it.contains("fallback") } ||
                logs.any { it.contains("fallback") || it.contains("STARTUP_COMPONENTS") }
        )
    }
}
