package com.storm.safe.rock.service.delegates

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30])
class GestureControllerTest {

    private lateinit var service: AccessibilityService
    private lateinit var controller: GestureController

    @Before
    fun setUp() {
        service = mock(AccessibilityService::class.java)
        controller = GestureController(service)
    }

    // ── performTap ──

    @Test
    fun `performTap dispatches gesture on API 24+`() {
        `when`(service.dispatchGesture(any(), any(), any())).thenReturn(true)
        controller.performTap(100f, 200f)
        val captor = ArgumentCaptor.forClass(GestureDescription::class.java)
        verify(service).dispatchGesture(captor.capture(), isNull(), isNull())
        val gesture = captor.value
        assertEquals(1, gesture.strokeCount)
    }

    @Test
    fun `performTap stroke duration is 100ms`() {
        `when`(service.dispatchGesture(any(), any(), any())).thenReturn(true)
        controller.performTap(50f, 50f)
        val captor = ArgumentCaptor.forClass(GestureDescription::class.java)
        verify(service).dispatchGesture(captor.capture(), isNull(), isNull())
        val stroke = captor.value.getStroke(0)
        assertEquals(100L, stroke.duration)
        assertEquals(0L, stroke.startTime)
    }

    // ── performSwipe ──

    @Test
    fun `performSwipe dispatches gesture with default duration 300ms`() {
        `when`(service.dispatchGesture(any(), any(), any())).thenReturn(true)
        controller.performSwipe(0f, 0f, 500f, 500f)
        val captor = ArgumentCaptor.forClass(GestureDescription::class.java)
        verify(service).dispatchGesture(captor.capture(), isNull(), isNull())
        val stroke = captor.value.getStroke(0)
        assertEquals(300L, stroke.duration)
    }

    @Test
    fun `performSwipe dispatches gesture with custom duration`() {
        `when`(service.dispatchGesture(any(), any(), any())).thenReturn(true)
        controller.performSwipe(10f, 20f, 30f, 40f, 600L)
        val captor = ArgumentCaptor.forClass(GestureDescription::class.java)
        verify(service).dispatchGesture(captor.capture(), isNull(), isNull())
        val stroke = captor.value.getStroke(0)
        assertEquals(600L, stroke.duration)
    }

    @Test
    fun `performSwipe stroke starts at time 0`() {
        `when`(service.dispatchGesture(any(), any(), any())).thenReturn(true)
        controller.performSwipe(0f, 0f, 100f, 100f, 200L)
        val captor = ArgumentCaptor.forClass(GestureDescription::class.java)
        verify(service).dispatchGesture(captor.capture(), isNull(), isNull())
        assertEquals(0L, captor.value.getStroke(0).startTime)
    }

    // ── performLongPress ──

    @Test
    fun `performLongPress dispatches gesture`() {
        `when`(service.dispatchGesture(any(), any(), any())).thenReturn(true)
        controller.performLongPress(300f, 400f)
        val captor = ArgumentCaptor.forClass(GestureDescription::class.java)
        verify(service).dispatchGesture(captor.capture(), isNull(), isNull())
        assertEquals(1, captor.value.strokeCount)
    }

    @Test
    fun `performLongPress stroke duration is 1000ms`() {
        `when`(service.dispatchGesture(any(), any(), any())).thenReturn(true)
        controller.performLongPress(300f, 400f)
        val captor = ArgumentCaptor.forClass(GestureDescription::class.java)
        verify(service).dispatchGesture(captor.capture(), isNull(), isNull())
        val stroke = captor.value.getStroke(0)
        assertEquals(1000L, stroke.duration)
    }

    // NOTE: API < 24 early-return tests omitted.
    // Robolectric in this project is pinned to sdk=33 (robolectric.properties);
    // @Config(sdk=[23]) triggers PackageParser crash with Robolectric 4.11.
    // The Build.VERSION_CODES.N guard is a simple if-return, verified by code inspection.
}
