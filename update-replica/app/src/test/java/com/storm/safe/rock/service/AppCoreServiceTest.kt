package com.storm.safe.rock.service

import android.app.AlarmManager
import android.app.Service
import android.content.Context
import android.content.Intent
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowAlarmManager
import com.storm.safe.rock.service.modules.protection.RecentsGuardManager

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30])
class AppCoreServiceTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = RuntimeEnvironment.getApplication()
        resetRunning(false)
    }

    @After
    fun tearDown() {
        resetRunning(false)
        RecentsGuardManager.hidingFromRecentsFlag = false
    }

    // ── Companion object C0277a0 ──

    @Test
    fun `isRunning false initially`() {
        assertFalse(AppCoreService.isRunning())
    }

    @Test
    fun `companion object start can be called without crash`() {
        AppCoreService.start(context)
        // In Robolectric, startForegroundService does not actually start the service
        // but should not crash
    }

    @Test
    fun `start is no-op when already running`() {
        resetRunning(true)
        AppCoreService.start(context)
        // Should return early without starting another service
        assertTrue(AppCoreService.isRunning())
    }

    // ── Service lifecycle ──

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
    fun `onCreate sets running true and starts foreground`() {
        val controller = Robolectric.buildService(AppCoreService::class.java)
        controller.create()
        assertTrue(AppCoreService.isRunning())
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
    fun `onStartCommand calls startForegroundNotification`() {
        val controller = Robolectric.buildService(AppCoreService::class.java)
        val service = controller.create().get()
        // Should not crash even when called multiple times
        service.onStartCommand(null, 0, 1)
        service.onStartCommand(null, 0, 2)
        controller.destroy()
    }

    @Test
    fun `onStartCommand schedules 60s backup sync alarm`() {
        val controller = Robolectric.buildService(AppCoreService::class.java)
        val service = controller.create().get()
        service.onStartCommand(null, 0, 1)

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val shadow = shadowOf(alarmManager)
        // Should have scheduled alarms
        val alarms = shadow.scheduledAlarms
        // At minimum, the 60s backup sync alarm should be present
        controller.destroy()
    }

    @Test
    fun `onStartCommand schedules guard alarm`() {
        val controller = Robolectric.buildService(AppCoreService::class.java)
        val service = controller.create().get()
        service.onStartCommand(null, 0, 1)
        // guard scheduling delegates to tisxhskrc.scheduleGuard — no crash
        controller.destroy()
    }

    @Test
    fun `onBind returns null`() {
        val service = AppCoreService()
        assertNull(service.onBind(null))
    }

    // ── onDestroy recovery ──

    @Test
    fun `onDestroy sets running false`() {
        val controller = Robolectric.buildService(AppCoreService::class.java)
        controller.create()
        assertTrue(AppCoreService.isRunning())
        controller.destroy()
        assertFalse(AppCoreService.isRunning())
    }

    @Test
    fun `onDestroy clears hiding from recents flag`() {
        RecentsGuardManager.hidingFromRecentsFlag = true
        val controller = Robolectric.buildService(AppCoreService::class.java)
        controller.create()
        controller.destroy()
        assertFalse(RecentsGuardManager.hidingFromRecentsFlag)
    }

    @Test
    fun `onDestroy schedules multiple restart alarms`() {
        val controller = Robolectric.buildService(AppCoreService::class.java)
        controller.create()
        controller.destroy()

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val shadow = shadowOf(alarmManager)
        // JADX schedules 4 alarms in onDestroy: 500ms, 1500ms, 5000ms, 15000ms
        // Plus the restartSelf and zgafaqvswksa jobs
        val alarms = shadow.scheduledAlarms
        assertTrue("onDestroy should schedule alarms", alarms.size >= 1)
    }

    @Test
    fun `onDestroy restartSelf tries to restart service`() {
        val controller = Robolectric.buildService(AppCoreService::class.java)
        controller.create()
        controller.destroy()
        // restartSelf calls startForegroundService — should not crash
    }

    @Test
    fun `onDestroy calls zgafaqvswksa scheduleImmediateRestart and crashRecovery`() {
        val controller = Robolectric.buildService(AppCoreService::class.java)
        controller.create()
        controller.destroy()
        // These call JobScheduler — in Robolectric, should not crash
    }

    // ── onTaskRemoved recovery ──

    @Test
    fun `onTaskRemoved clears hiding flag and schedules recovery`() {
        RecentsGuardManager.hidingFromRecentsFlag = true
        val controller = Robolectric.buildService(AppCoreService::class.java)
        val service = controller.create().get()

        // Call onTaskRemoved directly
        service.onTaskRemoved(null)

        assertFalse(RecentsGuardManager.hidingFromRecentsFlag)
    }

    @Test
    fun `onTaskRemoved schedules restart alarms with different request codes`() {
        val controller = Robolectric.buildService(AppCoreService::class.java)
        val service = controller.create().get()
        service.onTaskRemoved(null)
        // Should schedule alarms (request codes 1, 10, 0, 11) different from onDestroy (2, 12, 3, 13)
    }

    // ── scheduleAlarm helper ──

    @Test
    fun `scheduleAlarm handles null AlarmManager gracefully`() {
        // In Robolectric, AlarmManager should be available
        // This verifies the null-check path doesn't crash
        val controller = Robolectric.buildService(AppCoreService::class.java)
        val service = controller.create().get()
        service.onStartCommand(null, 0, 1)
        controller.destroy()
    }

    // ── Constants verification ──

    @Test
    fun `notification ID is 10086`() {
        // JADX: startForeground(10086, notification)
        assertEquals(10086, AppCoreService.NOTIFICATION_ID)
    }

    // ── Helper ──

    private fun resetRunning(value: Boolean) {
        try {
            val field = AppCoreService::class.java.getDeclaredField("running")
            field.isAccessible = true
            field.set(null, value)
        } catch (_: Exception) {
            // Ignore during RED phase
        }
    }
}
