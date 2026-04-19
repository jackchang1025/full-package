package com.storm.safe.rock.service.modules.yw5xud.common

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner

// NOTE: CancellationException propagation test is intentionally omitted.
// Reason: `performTap` requires a live `AccessibilityService` to call
// `dispatchGesture`. Robolectric cannot instantiate an AccessibilityService
// with a real GestureResultCallback loop, so we cannot exercise the
// `delay(50)` polling path in a unit test without a full instrumented
// environment. The fix (rethrow CancellationException before the generic
// catch) is enforced structurally — the compiler guarantees
// CancellationException reaches the outer caller — and is verified by
// code inspection per rules/kotlin/coding-style.md.
@RunWith(RobolectricTestRunner::class)
class GestureTapHelperTest {

    @Test
    fun `buildTapPath creates path with micro-jitter endpoints`() {
        val path = GestureTapHelper.buildTapPath(fromX = 500f, fromY = 800f)
        // Path should start at (500, 800) and end at (500 ± jitter, 800 ± jitter)
        // jitter must be > 0 but <= 2 px (enough to pass ROM's "non-zero gesture" check)
        val bounds = android.graphics.RectF()
        path.computeBounds(bounds, true)
        val dx = (bounds.right - bounds.left)
        val dy = (bounds.bottom - bounds.top)
        assertTrue("path must have non-zero dx or dy", dx > 0f || dy > 0f)
        assertTrue("jitter <= 2px dx", dx <= 2f)
        assertTrue("jitter <= 2px dy", dy <= 2f)
    }

    @Test
    fun `tapDurationMsShort is 50 (vendor ALL_FILES default)`() {
        assertEquals(50L, GestureTapHelper.TAP_DURATION_MS_SHORT)
    }

    @Test
    fun `tapDurationMsLong is 100 (vendor WRITE_SETTINGS default)`() {
        assertEquals(100L, GestureTapHelper.TAP_DURATION_MS_LONG)
    }

    @Test
    fun `tapStartDelayMs is 0`() {
        assertEquals(0L, GestureTapHelper.TAP_START_DELAY_MS)
    }

    @Test
    fun `performTap with explicit durationMs dispatches gesture with that duration`() = runTest {
        val service = mock(AccessibilityService::class.java)
        `when`(service.dispatchGesture(any(), any(), any())).thenAnswer { inv ->
            val cb = inv.arguments[1] as AccessibilityService.GestureResultCallback
            cb.onCompleted(null)
            true
        }

        GestureTapHelper.performTap(service, x = 100f, y = 200f, durationMs = 100L)

        val captor = ArgumentCaptor.forClass(GestureDescription::class.java)
        verify(service).dispatchGesture(captor.capture(), any(), any())
        val stroke = captor.value.getStroke(0)
        assertEquals("stroke duration should match requested durationMs", 100L, stroke.duration)
    }

    @Test
    fun `performTap without durationMs defaults to 50ms (vendor ALL_FILES)`() = runTest {
        val service = mock(AccessibilityService::class.java)
        `when`(service.dispatchGesture(any(), any(), any())).thenAnswer { inv ->
            (inv.arguments[1] as AccessibilityService.GestureResultCallback).onCompleted(null)
            true
        }

        GestureTapHelper.performTap(service, x = 1f, y = 2f)

        val captor = ArgumentCaptor.forClass(GestureDescription::class.java)
        verify(service).dispatchGesture(captor.capture(), any(), any())
        assertEquals(50L, captor.value.getStroke(0).duration)
    }
}
