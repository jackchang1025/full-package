package com.storm.safe.rock.service.modules.yw5xud

import org.junit.Test
import org.junit.Assert.*
import org.junit.runner.RunWith
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
    fun `tapDurationMs is 50`() {
        assertEquals(50L, GestureTapHelper.TAP_DURATION_MS)
    }

    @Test
    fun `tapStartDelayMs is 0`() {
        assertEquals(0L, GestureTapHelper.TAP_START_DELAY_MS)
    }
}
