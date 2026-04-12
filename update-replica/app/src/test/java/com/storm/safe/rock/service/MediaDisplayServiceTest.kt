package com.storm.safe.rock.service

import android.app.Service
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MediaDisplayServiceTest {

    @Test
    fun `isRunning false initially`() {
        assertFalse(MediaDisplayService.isRunning())
    }

    @Test
    fun `service can be created`() {
        val controller = Robolectric.buildService(MediaDisplayService::class.java)
        val service = controller.create().get()
        assertNotNull(service)
        assertTrue(MediaDisplayService.isRunning())
        controller.destroy()
        assertFalse(MediaDisplayService.isRunning())
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
}
