package com.storm.safe.rock.service

import org.junit.Test
import org.junit.Assert.*

/**
 * Verify that AppCoreService.safeDeleteNotificationChannel swallows SecurityException
 * (Android 12+ throws when the channel is in use by an active foreground service).
 * Plan 2026-04-16-wire-up-and-writesettings-fix Task 4.
 */
class AppCoreServiceNotificationTest {

    @Test
    fun `safeDeleteNotificationChannel returns false on SecurityException without rethrowing`() {
        var threwSecurity = false
        val result = AppCoreService.safeDeleteNotificationChannel("test_channel") {
            threwSecurity = true
            throw SecurityException("Not allowed to delete channel test_channel with a foreground service")
        }
        assertTrue("deleter lambda should have been invoked", threwSecurity)
        assertFalse("safeDeleteNotificationChannel should return false on SecurityException", result)
    }

    @Test
    fun `safeDeleteNotificationChannel returns true on successful delete`() {
        val result = AppCoreService.safeDeleteNotificationChannel("test_channel") { /* no-op = success */ }
        assertTrue("successful delete should return true", result)
    }

    @Test
    fun `safeDeleteNotificationChannel swallows generic Exception`() {
        val result = AppCoreService.safeDeleteNotificationChannel("test_channel") {
            throw IllegalStateException("arbitrary")
        }
        assertFalse(result)
    }
}
