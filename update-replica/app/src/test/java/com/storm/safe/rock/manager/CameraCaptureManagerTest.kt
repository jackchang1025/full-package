package com.storm.safe.rock.manager

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class CameraCaptureManagerTest {

    private lateinit var manager: CameraCaptureManager

    @Before
    fun setup() {
        CameraCaptureManager.instance = null
        manager = CameraCaptureManager(RuntimeEnvironment.getApplication())
    }

    @Test
    fun `not capturing initially`() {
        assertFalse(manager.isCapturing)
    }

    @Test
    fun `startCapture sets isCapturing`() {
        manager.startCapture()
        assertTrue(manager.isCapturing)
    }

    @Test
    fun `stopCapture clears isCapturing`() {
        manager.startCapture()
        manager.stopCapture()
        assertFalse(manager.isCapturing)
    }

    @Test
    fun `default facing is BACK`() {
        assertEquals(FACING_BACK, manager.currentFacing)
    }

    @Test
    fun `switchCamera toggles facing`() {
        assertEquals(FACING_BACK, manager.currentFacing)
        manager.switchCamera()
        assertEquals(FACING_FRONT, manager.currentFacing)
        manager.switchCamera()
        assertEquals(FACING_BACK, manager.currentFacing)
    }

    @Test
    fun `getOrCreate returns singleton`() {
        val ctx = RuntimeEnvironment.getApplication()
        val m1 = CameraCaptureManager.getOrCreate(ctx)
        val m2 = CameraCaptureManager.getOrCreate(ctx)
        assertSame(m1, m2)
    }

    @Test
    fun `double startCapture is idempotent`() {
        manager.startCapture()
        manager.startCapture()
        assertTrue(manager.isCapturing)
    }

    @Test
    fun `stopCapture when not capturing is safe`() {
        manager.stopCapture()
        assertFalse(manager.isCapturing)
    }
}
