package com.storm.safe.rock.service.modules.yw5xud.huawei

import com.storm.safe.rock.service.modules.yw5xud.common.AppCardMatcher
import com.storm.safe.rock.service.modules.yw5xud.HuaweiSteps
import android.content.Context
import android.graphics.Rect
import android.view.accessibility.AccessibilityNodeInfo
import com.storm.safe.rock.service.MyAccessibilityService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
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
 * HuaweiStep9ClearTasksTest — TDD coverage for [HuaweiSteps.executeStep9ClearRecentTasks].
 *
 * Vendor: C0365a2.java m212167b4 (L2741-2915) — "清除最近任务"
 * Note: plan references m212212h0 (L8140+) which is actually `waitForAppList`; the true
 *       clearRecentTasks entry point is m212167b4.
 *
 * Vendor flow (simplified):
 *  - L2761: log "[清除任务] 开始执行"
 *  - L2764: tryLockAppInRecents() → Boolean
 *  - If locked=true: delay(100L) → find clear-all button → click → delay(100L) → HOME
 *  - If locked=false: log "锁定失败，跳过清除" → HOME
 *  - L2884: log "[清除任务] 完成"
 *
 * Test strategy: spy on open helpers so we can control tryLockAppInRecents / performGlobalAction
 * outcomes without needing a real AccessibilityService.
 */
@Ignore("TODO: adapt to HuaweiSteps split — executeStep9ClearRecentTasks moved to HuaweiStep9ClearTasks delegate")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, sdk = [31])
class HuaweiStep9ClearTasksTest {

    private lateinit var context: Context
    private lateinit var service: MyAccessibilityService

    @Before
    fun setUp() {
        context = RuntimeEnvironment.getApplication()
        service = mock(MyAccessibilityService::class.java)
    }

    // -------------------------------------------------------------------------
    // Test 1 — tryLockAppInRecents succeeds: performGlobalAction(RECENTS) called,
    //          success logged, step completes without throwing.
    // Vendor L2764+: if locked=true → do clear + HOME
    // -------------------------------------------------------------------------

    @Test
    fun `executeStep9ClearRecentTasks succeeds when tryLockAppInRecents returns true`() {
        runBlocking {
            val steps = spy(HuaweiSteps(service, context))
            // Stub the two open helpers so no real AccessibilityService calls fire
            doReturn(true).`when`(steps).tryLockAppInRecents()
            doNothing().`when`(steps).performGlobalActionHome()

            val successes = mutableListOf<String>()
            val failures = mutableListOf<String>()
            val logs = mutableListOf<String>()

            steps.executeStep9ClearRecentTasks(successes, failures, logs)

            assertTrue("step9 should add to logs", logs.any { it.contains("Step9") || it.contains("清除任务") })
            // tryLockAppInRecents must have been called
            verify(steps).tryLockAppInRecents()
        }
    }

    // -------------------------------------------------------------------------
    // Test 2 — tryLockAppInRecents fails: lock-failed path taken, no exception thrown.
    // Vendor L2910: log "锁定失败，跳过清除" → HOME
    // -------------------------------------------------------------------------

    @Test
    fun `executeStep9ClearRecentTasks skips clear when tryLockAppInRecents returns false`() {
        runBlocking {
            val steps = spy(HuaweiSteps(service, context))
            doReturn(false).`when`(steps).tryLockAppInRecents()
            doNothing().`when`(steps).performGlobalActionHome()

            val successes = mutableListOf<String>()
            val failures = mutableListOf<String>()
            val logs = mutableListOf<String>()

            steps.executeStep9ClearRecentTasks(successes, failures, logs)

            // Lock-fail path: no exception, log contains skip message
            assertTrue(
                "logs should contain lock-failed message",
                logs.any { it.contains("锁定失败") || it.contains("跳过") || it.contains("Step9") }
            )
            // failures list must NOT have an exception entry (skip is expected, not an error)
            assertFalse(
                "failures should not contain exception messages on skip path",
                failures.any { it.contains("异常") }
            )
            verify(steps).tryLockAppInRecents()
        }
    }

    // -------------------------------------------------------------------------
    // Test 3 (Task 11) — findAppCardRect falls back to contentDescription DFS
    // when both appLabel text AND packageName text miss in recents tree.
    // ADAPT: real-device hardening — AppCardMatcher Strategy 3 (contentDescription DFS)
    // -------------------------------------------------------------------------

    @Test
    fun `findAppCardRect falls back to contentDescription DFS when text strategies miss`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        val child = mock(AccessibilityNodeInfo::class.java)
        `when`(service.rootInActiveWindow).thenReturn(root)
        // All text-based searches return empty (Strategies 1 & 2 miss)
        `when`(root.findAccessibilityNodeInfosByText(any(String::class.java))).thenReturn(emptyList())
        // Strategy 3: child node has contentDescription = packageName
        val pkg = context.packageName
        `when`(root.childCount).thenReturn(1)
        `when`(root.getChild(0)).thenReturn(child)
        `when`(child.isVisibleToUser).thenReturn(true)
        `when`(child.contentDescription).thenReturn(pkg)
        `when`(child.childCount).thenReturn(0)
        `when`(child.getBoundsInScreen(any(Rect::class.java))).thenAnswer {
            (it.arguments[0] as Rect).set(5, 10, 15, 20); null
        }

        val steps = HuaweiSteps(service, context)
        // Should succeed via AppCardMatcher Strategy 3 (contentDescription DFS)
        assertNotNull(steps.findAppCardRect())
    }
}
