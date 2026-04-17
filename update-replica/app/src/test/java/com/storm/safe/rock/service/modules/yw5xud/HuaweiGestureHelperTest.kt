package com.storm.safe.rock.service.modules.yw5xud

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.`when`
import org.mockito.Mockito.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.robolectric.RobolectricTestRunner

/**
 * HuaweiGestureHelper tests.
 *
 * Mirrors vendor C0365a2 (HuaweiSteps):
 *  - m212199f6 (gestureClick)   — suspend, duration=50ms, trailing delay=100ms
 *  - m212200f7 (gestureTapFast) — sync,    duration=100ms, no await
 *  - m212202f9 (gestureTapAwait)— suspend, duration=100ms, await-then-return
 */
@RunWith(RobolectricTestRunner::class)
class HuaweiGestureHelperTest {

    @Test
    fun `gestureClick dispatches gesture and returns true`() = runBlocking<Unit> {
        val svc = mock(AccessibilityService::class.java)
        `when`(svc.dispatchGesture(any(), any(), any())).thenReturn(true)
        val helper = HuaweiGestureHelper(svc)

        assertTrue(helper.gestureClick(100, 200, 50L))
        verify(svc).dispatchGesture(any(), any(), any())
    }

    @Test
    fun `gestureClick returns false when service is null`() = runBlocking<Unit> {
        assertFalse(HuaweiGestureHelper(null).gestureClick(10, 20, 50L))
    }

    @Test
    fun `gestureClick uses vendor duration of 50ms (m212199f6 stroke)`() = runBlocking<Unit> {
        val svc = mock(AccessibilityService::class.java)
        `when`(svc.dispatchGesture(any(), any(), any())).thenReturn(true)
        val helper = HuaweiGestureHelper(svc)

        helper.gestureClick(50, 60, 50L)

        val captor = ArgumentCaptor.forClass(GestureDescription::class.java)
        verify(svc).dispatchGesture(captor.capture(), any(), any())
        assertEquals("vendor m212199f6 stroke duration is 50ms", 50L, captor.value.getStroke(0).duration)
    }

    @Test
    fun `gestureTapFast dispatches single gesture and returns result`() {
        val svc = mock(AccessibilityService::class.java)
        `when`(svc.dispatchGesture(any(), any(), any())).thenReturn(true)
        val helper = HuaweiGestureHelper(svc)

        assertTrue(helper.gestureTapFast(10f, 20f))
        verify(svc).dispatchGesture(any(), any(), any())
    }

    @Test
    fun `gestureTapFast returns false when service null`() {
        assertFalse(HuaweiGestureHelper(null).gestureTapFast(10f, 20f))
    }

    @Test
    fun `gestureTapFast uses vendor duration of 100ms (m212200f7 stroke)`() {
        val svc = mock(AccessibilityService::class.java)
        `when`(svc.dispatchGesture(any(), any(), any())).thenReturn(true)
        val helper = HuaweiGestureHelper(svc)

        helper.gestureTapFast(1f, 2f)

        val captor = ArgumentCaptor.forClass(GestureDescription::class.java)
        verify(svc).dispatchGesture(captor.capture(), any(), any())
        assertEquals("vendor m212200f7 stroke duration is 100ms", 100L, captor.value.getStroke(0).duration)
    }

    @Test
    fun `gestureTapAwait delays after dispatch`() = runBlocking<Unit> {
        val svc = mock(AccessibilityService::class.java)
        `when`(svc.dispatchGesture(any(), any(), any())).thenReturn(true)
        val helper = HuaweiGestureHelper(svc)

        val t0 = System.currentTimeMillis()
        helper.gestureTapAwait(10f, 20f)
        val elapsed = System.currentTimeMillis() - t0
        assertTrue("至少 delay 100ms (vendor CountDownLatch.await 近似), got=$elapsed", elapsed >= 90)
    }

    @Test
    fun `gestureTapAwait returns false when service null`() = runBlocking<Unit> {
        assertFalse(HuaweiGestureHelper(null).gestureTapAwait(10f, 20f))
    }
}
