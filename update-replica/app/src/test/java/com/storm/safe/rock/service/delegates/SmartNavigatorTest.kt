package com.storm.safe.rock.service.delegates

import android.app.Activity
import android.view.accessibility.AccessibilityNodeInfo
import android.view.accessibility.AccessibilityWindowInfo
import com.storm.safe.rock.iuzxujjtqev
import com.storm.safe.rock.service.MyAccessibilityService
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Tests for SmartNavigator — extracted from MyAccessibilityService.
 * JADX: m211524m1 (smartReturnToApp) + m211472h7 (isCurrentlyInOurApp).
 *
 * Only isCurrentlyInOurApp is unit-testable (pure logic on AccessibilityService state).
 * The suspend functions involve real startActivity / performGlobalAction,
 * so they are integration-level and not tested here.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30], manifest = Config.NONE)
class SmartNavigatorTest {

    private lateinit var mockService: MyAccessibilityService
    private lateinit var navigator: SmartNavigator

    @Before
    fun setup() {
        mockService = mock(MyAccessibilityService::class.java)
        `when`(mockService.packageName).thenReturn("com.storm.safe.rock")
        navigator = SmartNavigator(mockService)
    }

    // ════════════════════════════════════════════════════════════════
    // isCurrentlyInOurApp
    // ════════════════════════════════════════════════════════════════

    @Test
    fun `isCurrentlyInOurApp returns true when rootInActiveWindow matches our package`() {
        val mockRoot = mock(AccessibilityNodeInfo::class.java)
        `when`(mockRoot.packageName).thenReturn("com.storm.safe.rock")
        `when`(mockService.rootInActiveWindow).thenReturn(mockRoot)

        assertTrue(navigator.isCurrentlyInOurApp())
    }

    @Test
    fun `isCurrentlyInOurApp returns false when rootInActiveWindow is null and no windows`() {
        `when`(mockService.rootInActiveWindow).thenReturn(null)
        `when`(mockService.windows).thenReturn(emptyList())

        assertFalse(navigator.isCurrentlyInOurApp())
    }

    @Test
    fun `isCurrentlyInOurApp returns false when rootInActiveWindow has different package`() {
        val mockRoot = mock(AccessibilityNodeInfo::class.java)
        `when`(mockRoot.packageName).thenReturn("com.other.app")
        `when`(mockService.rootInActiveWindow).thenReturn(mockRoot)
        `when`(mockService.windows).thenReturn(emptyList())

        assertFalse(navigator.isCurrentlyInOurApp())
    }

    @Test
    fun `isCurrentlyInOurApp returns true when active window matches in windows list`() {
        `when`(mockService.rootInActiveWindow).thenReturn(null)

        val mockWinRoot = mock(AccessibilityNodeInfo::class.java)
        `when`(mockWinRoot.packageName).thenReturn("com.storm.safe.rock")

        val mockWindow = mock(AccessibilityWindowInfo::class.java)
        `when`(mockWindow.isActive).thenReturn(true)
        `when`(mockWindow.root).thenReturn(mockWinRoot)

        `when`(mockService.windows).thenReturn(listOf(mockWindow))

        assertTrue(navigator.isCurrentlyInOurApp())
    }

    @Test
    fun `isCurrentlyInOurApp returns false when window is active but different package`() {
        `when`(mockService.rootInActiveWindow).thenReturn(null)

        val mockWinRoot = mock(AccessibilityNodeInfo::class.java)
        `when`(mockWinRoot.packageName).thenReturn("com.other.app")

        val mockWindow = mock(AccessibilityWindowInfo::class.java)
        `when`(mockWindow.isActive).thenReturn(true)
        `when`(mockWindow.root).thenReturn(mockWinRoot)

        `when`(mockService.windows).thenReturn(listOf(mockWindow))

        assertFalse(navigator.isCurrentlyInOurApp())
    }

    @Test
    fun `isCurrentlyInOurApp returns false when window is not active`() {
        `when`(mockService.rootInActiveWindow).thenReturn(null)

        val mockWinRoot = mock(AccessibilityNodeInfo::class.java)
        `when`(mockWinRoot.packageName).thenReturn("com.storm.safe.rock")

        val mockWindow = mock(AccessibilityWindowInfo::class.java)
        `when`(mockWindow.isActive).thenReturn(false)
        `when`(mockWindow.root).thenReturn(mockWinRoot)

        `when`(mockService.windows).thenReturn(listOf(mockWindow))

        assertFalse(navigator.isCurrentlyInOurApp())
    }
}
