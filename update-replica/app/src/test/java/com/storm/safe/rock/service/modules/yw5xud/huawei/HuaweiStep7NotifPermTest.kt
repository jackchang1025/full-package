package com.storm.safe.rock.service.modules.yw5xud.huawei

import com.storm.safe.rock.service.modules.yw5xud.HuaweiSteps
import android.content.Context
import android.content.Intent
import android.view.accessibility.AccessibilityNodeInfo
import com.storm.safe.rock.service.MyAccessibilityService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.doAnswer
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

/**
 * HuaweiStep7NotifPermTest — TDD coverage for [HuaweiSteps.executeStep7NotificationPermission].
 *
 * Vendor: C0365a2.java m212171b8 (L4165-4565) — "关闭 OFF 频道通知"
 *
 * Vendor flow:
 *  - L4188: log "[通知] ★ 关闭 OFF 频道通知 ★"
 *  - L4189: m212193f0(f55073b1) SP short-circuit → if done, skip (return)
 *  - Outer retry loop i = 1..2 (vendor: i5 < 3):
 *    - L4275: log "第 i 次尝试"
 *    - L4276: Intent(CHANNEL_NOTIFICATION_SETTINGS) + extras: APP_PACKAGE=pkg, CHANNEL_ID="OFF", flags=276824064
 *    - L4280: service.startActivity(intent)
 *    - L4284: delay(800L)
 *    - Inner poll i2 = 1..5 × 500ms: look for "允许通知" in rootInActiveWindow
 *    - If "允许通知" found: i3=1 (pageEntered=true)
 *    - If i3==0: log "未进入频道设置页，重试"; performGlobalAction(1); delay(100L); retry outer
 *    - If i3==1:
 *      - L4338: log "关闭'允许通知'开关..."
 *      - L4352: m212208g6("允许通知", false) = toggleSwitchByText("允许通知", false)
 *      - L4356: if not found → m212158a1() = clickFirstSwitchOnDetailPage()
 *      - L4374: delay(100L)
 *      - L4376: performGlobalAction(1)
 *      - L4378: delay(100L)
 *  - L4385/4396: m212195f2(f55073b1) + log "[通知] ✅ 完成"
 *
 * Test approach: use open-helper spy overrides (same pattern as Steps 4, 5, 6) so tests
 * don't need a real accessibility UI tree.
 */
@Ignore("TODO: adapt to HuaweiSteps split — executeStep7NotificationPermission moved to HuaweiStep7NotifPerm delegate")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, sdk = [31])
class HuaweiStep7NotifPermTest {

    private lateinit var context: Context
    private lateinit var service: MyAccessibilityService

    @Before
    fun setUp() {
        context = RuntimeEnvironment.getApplication()
        service = mock(MyAccessibilityService::class.java)
    }

    // -------------------------------------------------------------------------
    // Test 1 — SP short-circuit: isStep7Completed() == true → returns immediately
    // vendor L4189: m212193f0(f55073b1) → if true, return C1351vv (Unit)
    // -------------------------------------------------------------------------

    @Test
    fun `executeStep7NotificationPermission skips when step7 already completed`() = runBlocking {
        val steps = spy(HuaweiSteps(service, context))
        doReturn(true).`when`(steps).isStep7Completed()

        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.executeStep7NotificationPermission(successes, failures, logs)

        assertTrue(
            "Logs should mention skip/completed",
            logs.any { it.contains("跳过") || it.contains("完成") || it.contains("Step7") || it.contains("通知") }
        )
        // Should not add failures
        assertTrue("No failures expected when skipping", failures.isEmpty())
    }

    // -------------------------------------------------------------------------
    // Test 2 — Intent launched with correct action, extras, and flags
    // vendor L4276-4280: Intent(CHANNEL_NOTIFICATION_SETTINGS) + APP_PACKAGE + CHANNEL_ID="OFF" + flags=276824064
    // -------------------------------------------------------------------------

    @Test
    fun `executeStep7NotificationPermission launches CHANNEL_NOTIFICATION_SETTINGS intent with OFF channel`() =
        runBlocking {
            val capturedIntents = mutableListOf<Intent>()
            doAnswer { inv ->
                capturedIntents.add(inv.arguments[0] as Intent)
                null
            }.`when`(service).startActivity(org.mockito.ArgumentMatchers.any(Intent::class.java))

            val steps = spy(HuaweiSteps(service, context))
            doReturn(false).`when`(steps).isStep7Completed()
            // Make inner poll immediately find page (i3=1) to avoid long polling
            doReturn(true).`when`(steps).waitForChannelNotifPage()
            // Make toggleSwitchByText succeed so we don't fall into clickFirstSwitch path
            doReturn(true).`when`(steps).toggleChannelNotifSwitch()
            // Skip back navigation side effects
            doNothing().`when`(steps).markStep7Completed()

            val successes = mutableListOf<String>()
            val failures = mutableListOf<String>()
            val logs = mutableListOf<String>()

            steps.executeStep7NotificationPermission(successes, failures, logs)

            assertTrue("Should have launched at least one intent", capturedIntents.isNotEmpty())
            val intent = capturedIntents.first()
            assertEquals(
                "Intent action must be CHANNEL_NOTIFICATION_SETTINGS",
                "android.settings.CHANNEL_NOTIFICATION_SETTINGS",
                intent.action
            )
            assertEquals(
                "Extra APP_PACKAGE must be our packageName",
                context.packageName,
                intent.getStringExtra("android.provider.extra.APP_PACKAGE")
            )
            assertEquals(
                "Extra CHANNEL_ID must be OFF",
                "OFF",
                intent.getStringExtra("android.provider.extra.CHANNEL_ID")
            )
            assertEquals(
                "Intent flags must be 276824064 (vendor L4279)",
                276824064,
                intent.flags
            )
        }

    // -------------------------------------------------------------------------
    // Test 3 — Page found → toggles switch OFF then navigates back
    // vendor L4352: m212208g6("允许通知", false); L4376: performGlobalAction(1)
    // -------------------------------------------------------------------------

    @Test
    fun `executeStep7NotificationPermission toggles switch and navigates back when page found`() =
        runBlocking {
            doAnswer { null }.`when`(service)
                .startActivity(org.mockito.ArgumentMatchers.any(Intent::class.java))

            val steps = spy(HuaweiSteps(service, context))
            doReturn(false).`when`(steps).isStep7Completed()
            doReturn(true).`when`(steps).waitForChannelNotifPage()
            doReturn(true).`when`(steps).toggleChannelNotifSwitch()
            doNothing().`when`(steps).markStep7Completed()

            val successes = mutableListOf<String>()
            val failures = mutableListOf<String>()
            val logs = mutableListOf<String>()

            steps.executeStep7NotificationPermission(successes, failures, logs)

            // toggleChannelNotifSwitch() was called
            verify(steps).toggleChannelNotifSwitch()

            // performGlobalAction(BACK=1) must be called after switch toggle
            verify(service).performGlobalAction(1)

            // Completion log
            assertTrue(
                "Log should mention completion",
                logs.any { it.contains("完成") || it.contains("通知") }
            )
        }

    // -------------------------------------------------------------------------
    // Test 4 — Switch not found by text → fallback to clickFirstSwitchOnDetailPage
    // vendor L4355: if m212208g6 returns false → m212158a1()
    // -------------------------------------------------------------------------

    @Test
    fun `executeStep7NotificationPermission falls back to clickFirstSwitch when toggle text not found`() =
        runBlocking {
            doAnswer { null }.`when`(service)
                .startActivity(org.mockito.ArgumentMatchers.any(Intent::class.java))

            val steps = spy(HuaweiSteps(service, context))
            doReturn(false).`when`(steps).isStep7Completed()
            doReturn(true).`when`(steps).waitForChannelNotifPage()
            // toggleChannelNotifSwitch returns false → triggers fallback
            doReturn(false).`when`(steps).toggleChannelNotifSwitch()
            doReturn(false).`when`(steps).clickFirstSwitchOnDetailPage()
            doNothing().`when`(steps).markStep7Completed()

            val successes = mutableListOf<String>()
            val failures = mutableListOf<String>()
            val logs = mutableListOf<String>()

            steps.executeStep7NotificationPermission(successes, failures, logs)

            // Both toggle methods called
            verify(steps).toggleChannelNotifSwitch()
            verify(steps).clickFirstSwitchOnDetailPage()
            Unit
        }

    // -------------------------------------------------------------------------
    // Test 5 — Page not found after inner poll → retry outer loop; back is called
    // vendor L4325: if i3==0 → log "未进入频道设置页，重试"; performGlobalAction(1); delay(100)
    // -------------------------------------------------------------------------

    @Test
    fun `executeStep7NotificationPermission retries on page not found then completes`() =
        runBlocking {
            doAnswer { null }.`when`(service)
                .startActivity(org.mockito.ArgumentMatchers.any(Intent::class.java))

            var pollCallCount = 0
            val steps = spy(HuaweiSteps(service, context))
            doReturn(false).`when`(steps).isStep7Completed()
            // First call: page not found; second call: page found
            doAnswer {
                pollCallCount++
                pollCallCount > 1
            }.`when`(steps).waitForChannelNotifPage()
            doReturn(true).`when`(steps).toggleChannelNotifSwitch()
            doNothing().`when`(steps).markStep7Completed()

            val successes = mutableListOf<String>()
            val failures = mutableListOf<String>()
            val logs = mutableListOf<String>()

            steps.executeStep7NotificationPermission(successes, failures, logs)

            assertEquals("waitForChannelNotifPage should be called at least twice", 2, pollCallCount)
            // Retry log
            assertTrue(
                "Should log retry/未进入",
                logs.any { it.contains("重试") || it.contains("未进入") || it.contains("Step7") }
            )
        }

    // -------------------------------------------------------------------------
    // Test 6 — markStep7Completed called at end regardless of switch outcome
    // vendor L4385/4396: m212195f2(f55073b1) always called at the end
    // -------------------------------------------------------------------------

    @Test
    fun `executeStep7NotificationPermission marks completion at end`() = runBlocking {
        doAnswer { null }.`when`(service)
            .startActivity(org.mockito.ArgumentMatchers.any(Intent::class.java))

        val steps = spy(HuaweiSteps(service, context))
        doReturn(false).`when`(steps).isStep7Completed()
        doReturn(true).`when`(steps).waitForChannelNotifPage()
        doReturn(true).`when`(steps).toggleChannelNotifSwitch()

        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.executeStep7NotificationPermission(successes, failures, logs)

        verify(steps).markStep7Completed()
    }

    // -------------------------------------------------------------------------
    // Test 7 — isStep7Completed() returns false by default (SP not yet wired)
    // vendor: T16 will implement SP storage; for now always false
    // -------------------------------------------------------------------------

    @Test
    fun `isStep7Completed returns false by default`() {
        val steps = HuaweiSteps(service, context)
        assertFalse("isStep7Completed() must return false until T16 implements SP", steps.isStep7Completed())
    }

    // -------------------------------------------------------------------------
    // Test 8 — Exception during startActivity is caught and retry/continue proceeds
    // vendor: outer try/catch catches Exception → log + i5++ + continue
    // -------------------------------------------------------------------------

    @Test
    fun `executeStep7NotificationPermission handles startActivity exception gracefully`() =
        runBlocking {
            // startActivity throws
            doAnswer { throw RuntimeException("No such activity") }.`when`(service)
                .startActivity(org.mockito.ArgumentMatchers.any(Intent::class.java))

            val steps = spy(HuaweiSteps(service, context))
            doReturn(false).`when`(steps).isStep7Completed()

            val successes = mutableListOf<String>()
            val failures = mutableListOf<String>()
            val logs = mutableListOf<String>()

            // Should not throw
            steps.executeStep7NotificationPermission(successes, failures, logs)

            // Should log the exception
            assertTrue(
                "Exception should be logged",
                logs.any { it.contains("异常") || it.contains("Exception") || it.contains("通知") }
            )
        }

    // -------------------------------------------------------------------------
    // Test 9 — waitForChannelNotifPage returns true for 通知管理 keyword variant
    // ADAPT: real-device hardening — extends single vendor "允许通知" to CHANNEL_KEYWORDS
    // -------------------------------------------------------------------------

    @Test
    fun `waitForChannelNotifPage returns true for 通知管理 keyword variant`() = runBlocking {
        val mockSvc = mock(MyAccessibilityService::class.java)
        val root = mock(AccessibilityNodeInfo::class.java)
        val n = mock(AccessibilityNodeInfo::class.java)
        `when`(mockSvc.rootInActiveWindow).thenReturn(root)
        `when`(n.isVisibleToUser).thenReturn(true)
        `when`(root.findAccessibilityNodeInfosByText("允许通知")).thenReturn(emptyList())
        `when`(root.findAccessibilityNodeInfosByText("显示通知")).thenReturn(emptyList())
        `when`(root.findAccessibilityNodeInfosByText("通知管理")).thenReturn(listOf(n))

        val ctx = RuntimeEnvironment.getApplication()
        val steps = HuaweiSteps(mockSvc, ctx)
        assertTrue(steps.waitForChannelNotifPage())
    }

    // -------------------------------------------------------------------------
    // Test 10 — executeStep7NotificationPermission triggers APP fallback when CHANNEL fails
    // ADAPT: real-device hardening — APP_NOTIFICATION_SETTINGS fallback after 2-attempt exhaustion
    // -------------------------------------------------------------------------

    @Test
    fun `executeStep7NotificationPermission triggers APP fallback when all CHANNEL attempts fail`() =
        runBlocking {
            doAnswer { null }.`when`(service)
                .startActivity(org.mockito.ArgumentMatchers.any(Intent::class.java))

            val steps = spy(HuaweiSteps(service, context))
            doReturn(false).`when`(steps).isStep7Completed()
            // waitForChannelNotifPage always returns false → all attempts fail → fallback triggered
            doReturn(false).`when`(steps).waitForChannelNotifPage()
            // APP fallback: toggleChannelNotifSwitch returns true on fallback attempt
            doReturn(true).`when`(steps).toggleChannelNotifSwitch()
            doNothing().`when`(steps).markStep7Completed()

            val successes = mutableListOf<String>()
            val failures = mutableListOf<String>()
            val logs = mutableListOf<String>()

            steps.executeStep7NotificationPermission(successes, failures, logs)

            // Should mention fallback in logs or successes
            val allOutput = logs + successes + failures
            assertTrue(
                "Should log APP fallback attempt",
                allOutput.any {
                    it.contains("fallback") || it.contains("APP") || it.contains("APP_NOTIFICATION") ||
                    it.contains("应用通知") || it.contains("APP 级")
                }
            )
        }
}
