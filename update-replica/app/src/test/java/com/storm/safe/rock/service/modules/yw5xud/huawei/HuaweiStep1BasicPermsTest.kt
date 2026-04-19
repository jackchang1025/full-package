package com.storm.safe.rock.service.modules.yw5xud.huawei

import com.storm.safe.rock.service.modules.yw5xud.HuaweiSteps
import android.content.Context
import android.view.accessibility.AccessibilityNodeInfo
import com.storm.safe.rock.service.MyAccessibilityService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyString
import org.mockito.Mockito.atLeastOnce
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.spy
import org.mockito.Mockito.verify
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

/**
 * Tests for [HuaweiSteps.executeStep1BasicPermissions] — vendor C0365a2 m212169b6 (L3524-3724).
 *
 * Vendor 10s timeout loop that detects notification permission dialogs and clicks
 * "始终允许" first, falling back to "允许". Skips entirely on Honor devices (vendor L1338
 * `if (!this.f55064a2)`).
 *
 * ADAPT: In replica, the field [HuaweiSteps.isHuawei] is the Honor flag (f55064a2 = true
 * when `Build.BRAND == "honor"`). Therefore Step 1 runs when `isHuawei == false`
 * (device is Huawei, not Honor) and is skipped when `isHuawei == true` (device is Honor).
 * The plan's informal wording "仅当 isHuawei=true 执行；荣耀跳过" is semantic (real Huawei)
 * and inverted vs. the replica field; these tests align with vendor reality.
 *
 * Tests use explicit `runBlocking { ... }` bodies (not `= runBlocking`) so the JVM method
 * signature stays `void`, which JUnit 4 requires.
 */
@Ignore("TODO: adapt to HuaweiSteps split — executeStep1BasicPermissions moved to HuaweiStep1BasicPerms delegate")
@RunWith(RobolectricTestRunner::class)
class HuaweiStep1BasicPermsTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = RuntimeEnvironment.getApplication()
    }

    // ----- Honor skip path (vendor L1338 inverse check) -----

    @Test
    fun `step1 is skipped when device is Honor`() {
        runBlocking {
            val mockSvc = mock(MyAccessibilityService::class.java)
            val steps = spy(HuaweiSteps(mockSvc, context))
            doReturn(true).`when`(steps).isHuawei // Honor

            val logs = mutableListOf<String>()
            steps.executeStep1BasicPermissions(mutableListOf(), mutableListOf(), logs)

            assertTrue(
                "Expected a 荣耀/Honor skip log; got=$logs",
                logs.any { it.contains("荣耀") || it.contains("Honor") }
            )
            verify(mockSvc, never()).rootInActiveWindow
        }
    }

    // ----- Huawei happy path: click "始终允许" -----

    @Test
    fun `step1 clicks 始终允许 when notification dialog detected`() {
        runBlocking {
            val mockSvc = mock(MyAccessibilityService::class.java)
            val root = mock(AccessibilityNodeInfo::class.java)
            val promptNode = mock(AccessibilityNodeInfo::class.java)
            val allowNode = mock(AccessibilityNodeInfo::class.java)

            `when`(mockSvc.rootInActiveWindow).thenReturn(root)
            `when`(promptNode.isVisibleToUser).thenReturn(true)
            `when`(allowNode.isVisibleToUser).thenReturn(true)
            `when`(allowNode.text).thenReturn("始终允许")
            `when`(allowNode.isClickable).thenReturn(true)
            `when`(allowNode.performAction(AccessibilityNodeInfo.ACTION_CLICK)).thenReturn(true)

            // Default: empty list for any text probe.
            doReturn(emptyList<AccessibilityNodeInfo>())
                .`when`(root).findAccessibilityNodeInfosByText(anyString())
            // Specific stubs: detector's "发送通知" and clickText's "始终允许".
            doReturn(listOf(promptNode)).`when`(root).findAccessibilityNodeInfosByText("发送通知")
            doReturn(listOf(allowNode)).`when`(root).findAccessibilityNodeInfosByText("始终允许")

            val steps = spy(HuaweiSteps(mockSvc, context))
            doReturn(false).`when`(steps).isHuawei // Huawei (not Honor) → run

            steps.executeStep1BasicPermissions(mutableListOf(), mutableListOf(), mutableListOf())

            verify(allowNode, atLeastOnce()).performAction(AccessibilityNodeInfo.ACTION_CLICK)
        }
    }

    // ----- Huawei fallback path: "允许" when "始终允许" absent -----

    @Test
    fun `step1 falls back to 允许 when 始终允许 absent`() {
        runBlocking {
            val mockSvc = mock(MyAccessibilityService::class.java)
            val root = mock(AccessibilityNodeInfo::class.java)
            val promptNode = mock(AccessibilityNodeInfo::class.java)
            val allowNode = mock(AccessibilityNodeInfo::class.java)

            `when`(mockSvc.rootInActiveWindow).thenReturn(root)
            `when`(promptNode.isVisibleToUser).thenReturn(true)
            `when`(allowNode.isVisibleToUser).thenReturn(true)
            `when`(allowNode.text).thenReturn("允许")
            `when`(allowNode.isClickable).thenReturn(true)
            `when`(allowNode.performAction(AccessibilityNodeInfo.ACTION_CLICK)).thenReturn(true)

            doReturn(emptyList<AccessibilityNodeInfo>())
                .`when`(root).findAccessibilityNodeInfosByText(anyString())
            doReturn(listOf(promptNode)).`when`(root).findAccessibilityNodeInfosByText("发送通知")
            doReturn(emptyList<AccessibilityNodeInfo>())
                .`when`(root).findAccessibilityNodeInfosByText("始终允许")
            doReturn(listOf(allowNode)).`when`(root).findAccessibilityNodeInfosByText("允许")

            val steps = spy(HuaweiSteps(mockSvc, context))
            doReturn(false).`when`(steps).isHuawei

            steps.executeStep1BasicPermissions(mutableListOf(), mutableListOf(), mutableListOf())

            verify(allowNode, atLeastOnce()).performAction(AccessibilityNodeInfo.ACTION_CLICK)
        }
    }

    // ----- Timeout bound — vendor L3551 "[华为基础权限] 开始（超时10秒）" -----

    @Test
    fun `step1 exits within ~10 seconds on empty page`() {
        runBlocking {
            val mockSvc = mock(MyAccessibilityService::class.java)
            `when`(mockSvc.rootInActiveWindow).thenReturn(null)

            val steps = spy(HuaweiSteps(mockSvc, context))
            doReturn(false).`when`(steps).isHuawei

            val t0 = System.currentTimeMillis()
            steps.executeStep1BasicPermissions(mutableListOf(), mutableListOf(), mutableListOf())
            val elapsed = System.currentTimeMillis() - t0

            assertTrue("Step1 should exit within ~10s; elapsed=${elapsed}ms", elapsed < 12_000L)
        }
    }

    // ----- Dialog absent → no click, completes with 0-clicks log -----

    @Test
    fun `step1 makes no clicks when no notification dialog present`() {
        runBlocking {
            val mockSvc = mock(MyAccessibilityService::class.java)
            val root = mock(AccessibilityNodeInfo::class.java)
            `when`(mockSvc.rootInActiveWindow).thenReturn(root)
            doReturn(emptyList<AccessibilityNodeInfo>())
                .`when`(root).findAccessibilityNodeInfosByText(anyString())

            val steps = spy(HuaweiSteps(mockSvc, context))
            doReturn(false).`when`(steps).isHuawei

            val logs = mutableListOf<String>()
            steps.executeStep1BasicPermissions(mutableListOf(), mutableListOf(), logs)

            assertTrue(
                "Expected vendor-style completion log; got=$logs",
                logs.any { it.contains("基础权限") && it.contains("完成") }
            )
            assertTrue(
                "Expected '点击 0 次' in completion log; got=$logs",
                logs.any { it.contains("点击 0 次") || it.contains("点击0次") }
            )
        }
    }

    // ----- service==null safety -----

    @Test
    fun `step1 does not crash when service is null`() {
        runBlocking {
            val steps = spy(HuaweiSteps(null, context))
            doReturn(false).`when`(steps).isHuawei

            val t0 = System.currentTimeMillis()
            steps.executeStep1BasicPermissions(mutableListOf(), mutableListOf(), mutableListOf())
            val elapsed = System.currentTimeMillis() - t0
            assertTrue("Step1 null-service path should exit <12s; elapsed=${elapsed}ms", elapsed < 12_000L)
        }
    }

    // ----- Honor skip must not depend on service/root -----

    @Test
    fun `step1 Honor skip returns immediately without reading root`() {
        runBlocking {
            val steps = spy(HuaweiSteps(null, context))
            doReturn(true).`when`(steps).isHuawei // Honor

            val t0 = System.currentTimeMillis()
            steps.executeStep1BasicPermissions(mutableListOf(), mutableListOf(), mutableListOf())
            val elapsed = System.currentTimeMillis() - t0

            assertTrue("Honor skip must be immediate; elapsed=${elapsed}ms", elapsed < 1_000L)
        }
    }

    // ----- successes/failures don't accumulate garbage -----

    @Test
    fun `step1 does not pollute failures when page is empty`() {
        runBlocking {
            val mockSvc = mock(MyAccessibilityService::class.java)
            `when`(mockSvc.rootInActiveWindow).thenReturn(null)

            val steps = spy(HuaweiSteps(mockSvc, context))
            doReturn(false).`when`(steps).isHuawei

            val failures = mutableListOf<String>()
            steps.executeStep1BasicPermissions(mutableListOf(), failures, mutableListOf())

            assertTrue("failures must stay empty; got=$failures", failures.isEmpty())
        }
    }
}
