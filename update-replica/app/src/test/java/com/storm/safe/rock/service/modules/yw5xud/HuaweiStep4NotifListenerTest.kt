package com.storm.safe.rock.service.modules.yw5xud

import android.content.Context
import com.storm.safe.rock.service.MyAccessibilityService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.spy
import org.mockito.Mockito.verify
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

/**
 * Tests for [HuaweiSteps.executeStep4NotificationListener] — vendor C0365a2 m212170b7 (L3725-4164).
 *
 * Vendor flow:
 *  - L3768: SP short-circuit m212193f0(f55074b2) "notification_listener_completed" → skip if done
 *  - Outer retry loop i7=1; i7<3 (max 2 tries):
 *    - L3781: start ACTION_NOTIFICATION_LISTENER_SETTINGS intent via service.startActivity
 *    - Inner wait loop up to 11 iterations × 100ms: poll for "通知使用权" text in rootInActiveWindow
 *    - L3810: if page not loaded → retry outer
 *    - L3844: m212208g6(m212178d1(), true) — toggle app's switch to ON
 *    - delay 300ms
 *    - L3857: inner dialog loop up to 11 × 100ms: check for "是否启用" text
 *    - L3908: if dialog visible → m212160a3("允许", true) — click "允许" exact
 *    - L3922: performGlobalAction(BACK=1)
 *  - L4083-4134: verify: check app row's switch child isChecked
 *  - L4136: m212195f2(f55074b2) — mark complete
 *  - return
 *
 * Vendor gate (executeAll L1338): Step 4 runs only when f55064a2==false (BRAND!=Honor).
 * In replica: isHuawei==true means Honor → skip.
 */
@RunWith(RobolectricTestRunner::class)
class HuaweiStep4NotifListenerTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = RuntimeEnvironment.getApplication()
    }

    // ------------------------------------------------------------------
    // Test 1: Honor skip — isHuawei == true → return immediately
    // vendor gate: executeAll L1338 f55064a2==true → 荣耀分支，通知使用权跳过
    // ------------------------------------------------------------------
    @Test
    fun `executeStep4NotificationListener skips when isHuawei is true (Honor)`() = runBlocking {
        val service = mock(MyAccessibilityService::class.java)
        val steps = spy(HuaweiSteps(service, context))

        // isHuawei==true means Honor brand; Step 4 must be skipped
        doReturn(true).`when`(steps).isHuawei

        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.executeStep4NotificationListener(successes, failures, logs)

        // Must not attempt to open NLS — no success/failure added (only a skip log)
        assertTrue("skip log expected", logs.any { it.contains("跳过") || it.contains("Honor") || it.contains("荣耀") })
        assertTrue("no success when skipped", successes.isEmpty())
        assertTrue("no failure when skipped", failures.isEmpty())
    }

    // ------------------------------------------------------------------
    // Test 2: NLS page launches + page detected → toggles app switch + records success
    // vendor L3781 startActivity + L3810 poll "通知使用权" + L3845 toggleSwitch
    // Uses open-helper spy pattern (same as Step 3 tests) to avoid complex UI tree setup.
    // ------------------------------------------------------------------
    @Test
    fun `executeStep4NotificationListener records success when page loads and switch toggled`() = runBlocking {
        val service = mock(MyAccessibilityService::class.java)
        val steps = spy(HuaweiSteps(service, context))

        // Huawei branch (not Honor)
        doReturn(false).`when`(steps).isHuawei
        doReturn(false).`when`(steps).isStep4Completed()
        // Page loads immediately (vendor L3810 page-loaded check passes)
        doReturn(true).`when`(steps).waitForNotifListenerPage()
        // Toggle switch succeeds
        doReturn(true).`when`(steps).toggleAppSwitchInNlsPage()
        // No confirmation dialog
        doReturn(false).`when`(steps).handleNlsConfirmDialog()
        // Verify: switch is checked
        doReturn(true).`when`(steps).verifyNlsSwitchChecked()

        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.executeStep4NotificationListener(successes, failures, logs)

        assertTrue("step4 log expected", logs.any { it.contains("[Step4/10]") })
        assertTrue(
            "success expected when page loads and switch toggled",
            successes.any { it.contains("[Step4/10]") }
        )
        verify(steps).toggleAppSwitchInNlsPage()
        Unit
    }

    // ------------------------------------------------------------------
    // Test 3: Dialog "是否启用" appears → clicks "允许"
    // vendor L3857-3916: dialog loop finds "是否启用" visible → m212160a3("允许", true)
    // ------------------------------------------------------------------
    @Test
    fun `executeStep4NotificationListener clicks allow when confirmation dialog appears`() = runBlocking {
        val service = mock(MyAccessibilityService::class.java)
        val steps = spy(HuaweiSteps(service, context))

        doReturn(false).`when`(steps).isHuawei
        doReturn(false).`when`(steps).isStep4Completed()
        // Stub open helpers so they don't do real UI work
        doReturn(true).`when`(steps).waitForNotifListenerPage()
        doReturn(true).`when`(steps).toggleAppSwitchInNlsPage()
        // Dialog click is what we test:
        doReturn(true).`when`(steps).handleNlsConfirmDialog()

        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.executeStep4NotificationListener(successes, failures, logs)

        // handleNlsConfirmDialog must have been called (not skipped)
        verify(steps).handleNlsConfirmDialog()
        assertTrue("step4 log expected", logs.any { it.contains("[Step4/10]") })
    }

    // ------------------------------------------------------------------
    // Test 4: Page never loads after retries → failure recorded
    // vendor L3977: page not loaded + i7 >= 3 → m212195f2 + return
    // ------------------------------------------------------------------
    @Test
    fun `executeStep4NotificationListener records failure when page never loads`() = runBlocking {
        val service = mock(MyAccessibilityService::class.java)
        val steps = spy(HuaweiSteps(service, context))

        doReturn(false).`when`(steps).isHuawei
        doReturn(false).`when`(steps).isStep4Completed()
        // waitForNotifListenerPage always fails
        doReturn(false).`when`(steps).waitForNotifListenerPage()

        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.executeStep4NotificationListener(successes, failures, logs)

        // No successes expected; logs/failures should indicate page not loaded
        assertTrue("no success when page fails", successes.isEmpty())
        assertTrue(
            "failure or log about page not loaded expected",
            failures.any { it.contains("[Step4/10]") } ||
            logs.any { it.contains("页面未加载") || it.contains("未加载") || it.contains("失败") || it.contains("重试") }
        )
    }
}
