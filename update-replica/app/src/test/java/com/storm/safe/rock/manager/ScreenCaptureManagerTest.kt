package com.storm.safe.rock.manager

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class ScreenCaptureManagerTest {

    @Before
    fun setup() {
        ScreenCaptureManager.instance = null
    }

    @Test
    fun `not capturing initially`() {
        val mgr = ScreenCaptureManager(RuntimeEnvironment.getApplication())
        assertFalse(mgr.isCapturing)
    }

    @Test
    fun `mediaProjection null initially`() {
        val mgr = ScreenCaptureManager(RuntimeEnvironment.getApplication())
        assertNull(mgr.mediaProjection)
    }

    @Test
    fun `stopCapture when not capturing is safe`() {
        val mgr = ScreenCaptureManager(RuntimeEnvironment.getApplication())
        mgr.stopCapture()
        assertFalse(mgr.isCapturing)
    }

    @Test
    fun `takeScreenshot returns null when not capturing`() {
        val mgr = ScreenCaptureManager(RuntimeEnvironment.getApplication())
        assertNull(mgr.takeScreenshot())
    }

    @Test
    fun `getOrCreate returns singleton`() {
        val ctx = RuntimeEnvironment.getApplication()
        val m1 = ScreenCaptureManager.getOrCreate(ctx)
        val m2 = ScreenCaptureManager.getOrCreate(ctx)
        assertSame(m1, m2)
    }

    @Test
    fun `getOrCreate uses application context`() {
        val ctx = RuntimeEnvironment.getApplication()
        val mgr = ScreenCaptureManager.getOrCreate(ctx)
        assertNotNull(mgr)
        assertSame(mgr, ScreenCaptureManager.instance)
    }
}
