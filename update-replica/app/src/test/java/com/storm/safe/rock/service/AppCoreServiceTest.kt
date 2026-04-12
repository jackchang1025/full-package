package com.storm.safe.rock.service

import android.app.Service
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class AppCoreServiceTest {

    @Test
    fun `isRunning false initially`() {
        assertFalse(AppCoreService.isRunning())
    }

    @Test
    fun `service can be created`() {
        val controller = Robolectric.buildService(AppCoreService::class.java)
        val service = controller.create().get()
        assertNotNull(service)
        assertTrue(AppCoreService.isRunning())
        controller.destroy()
        assertFalse(AppCoreService.isRunning())
    }

    @Test
    fun `onStartCommand returns START_STICKY`() {
        val controller = Robolectric.buildService(AppCoreService::class.java)
        val service = controller.create().get()
        val result = service.onStartCommand(null, 0, 1)
        assertEquals(Service.START_STICKY, result)
        controller.destroy()
    }

    @Test
    fun `onBind returns null`() {
        val service = AppCoreService()
        assertNull(service.onBind(null))
    }
}
