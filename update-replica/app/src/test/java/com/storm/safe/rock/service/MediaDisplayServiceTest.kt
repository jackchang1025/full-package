package com.storm.safe.rock.service

import android.app.Service
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30])
class MediaDisplayServiceTest {

    @Before
    fun setup() {
        // Reset statics before each test
        MediaDisplayService.isProjecting = false
        MediaDisplayService.targetFps = 20
        MediaDisplayService.quality = 80
        MediaDisplayService.scale = 0.8f
    }

    @After
    fun tearDown() {
        MediaDisplayService.isProjecting = false
    }

    @Test
    fun `isRunning false initially`() {
        assertFalse(MediaDisplayService.isRunning())
    }

    @Test
    fun `service can be created and sets instance`() {
        val controller = Robolectric.buildService(MediaDisplayService::class.java)
        val service = controller.create().get()
        assertNotNull(service)
        assertSame(service, MediaDisplayService.getInstance())
        assertTrue(MediaDisplayService.isRunning())
        controller.destroy()
        assertNull(MediaDisplayService.getInstance())
    }

    @Test
    fun `onStartCommand returns START_NOT_STICKY`() {
        val controller = Robolectric.buildService(MediaDisplayService::class.java)
        val service = controller.create().get()
        val result = service.onStartCommand(null, 0, 1)
        assertEquals(Service.START_NOT_STICKY, result)
        controller.destroy()
    }

    @Test
    fun `onBind returns null`() {
        val service = MediaDisplayService()
        assertNull(service.onBind(null))
    }

    @Test
    fun `isPaused defaults to false`() {
        val controller = Robolectric.buildService(MediaDisplayService::class.java)
        val service = controller.create().get()
        assertFalse(service.isPaused)
        controller.destroy()
    }

    @Test
    fun `isPaused can be toggled`() {
        val controller = Robolectric.buildService(MediaDisplayService::class.java)
        val service = controller.create().get()
        service.isPaused = true
        assertTrue(service.isPaused)
        service.isPaused = false
        assertFalse(service.isPaused)
        controller.destroy()
    }

    @Test
    fun `frameCallback defaults to null`() {
        val controller = Robolectric.buildService(MediaDisplayService::class.java)
        val service = controller.create().get()
        assertNull(service.frameCallback)
        controller.destroy()
    }

    @Test
    fun `frameCallback can be set and invoked`() {
        val controller = Robolectric.buildService(MediaDisplayService::class.java)
        val service = controller.create().get()

        var received: ByteArray? = null
        service.frameCallback = { data -> received = data }

        service.deliverFrame(byteArrayOf(10, 20, 30))
        assertArrayEquals(byteArrayOf(10, 20, 30), received)
        controller.destroy()
    }

    @Test
    fun `deliverFrame without callback does not crash`() {
        val controller = Robolectric.buildService(MediaDisplayService::class.java)
        val service = controller.create().get()
        service.frameCallback = null

        // Should not throw
        service.deliverFrame(byteArrayOf(1, 2, 3))
        controller.destroy()
    }

    @Test
    fun `onDestroy clears instance and frameCallback`() {
        val controller = Robolectric.buildService(MediaDisplayService::class.java)
        val service = controller.create().get()
        MediaDisplayService.isProjecting = true
        service.frameCallback = { _ -> }

        controller.destroy()

        assertNull(MediaDisplayService.getInstance())
        assertFalse(MediaDisplayService.isProjecting)
    }

    @Test
    fun `stopImmediate clears projecting and callback`() {
        val controller = Robolectric.buildService(MediaDisplayService::class.java)
        val service = controller.create().get()
        MediaDisplayService.isProjecting = true
        service.frameCallback = { _ -> }

        MediaDisplayService.stopImmediate()

        assertFalse(MediaDisplayService.isProjecting)
        assertNull(service.frameCallback)
        controller.destroy()
    }

    @Test
    fun `getFrameIntervalMs returns correct value`() {
        MediaDisplayService.targetFps = 10
        assertEquals(100L, MediaDisplayService.getFrameIntervalMs())

        MediaDisplayService.targetFps = 20
        assertEquals(50L, MediaDisplayService.getFrameIntervalMs())

        MediaDisplayService.targetFps = 30
        assertEquals(33L, MediaDisplayService.getFrameIntervalMs())
    }

    @Test
    fun `getFrameIntervalMs clamps fps to 5-30 range`() {
        MediaDisplayService.targetFps = 2
        assertEquals(200L, MediaDisplayService.getFrameIntervalMs()) // clamped to 5fps

        MediaDisplayService.targetFps = 60
        assertEquals(33L, MediaDisplayService.getFrameIntervalMs()) // clamped to 30fps
    }

    @Test
    fun `default companion values match JADX`() {
        assertEquals(20, MediaDisplayService.targetFps)
        assertEquals(80, MediaDisplayService.quality)
        assertEquals(0.8f, MediaDisplayService.scale, 0.001f)
    }
}
