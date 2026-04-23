package com.storm.safe.rock.service.delegates

import android.content.Context
import android.content.SharedPreferences
import com.storm.safe.rock.service.MyAccessibilityService
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

/**
 * Tests for ServiceInitializer — the assembler delegate that orchestrates
 * all 14 initialization methods extracted from MyAccessibilityService.
 *
 * Tests focus on pure-logic helpers (checkReinstallRecovery, isAlreadyAuthorized)
 * since the init methods themselves create real manager instances.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30], manifest = Config.NONE)
class ServiceInitializerTest {

    private lateinit var mockService: MyAccessibilityService
    private lateinit var initializer: ServiceInitializer

    @Before
    fun setup() {
        mockService = mock(MyAccessibilityService::class.java)
        val appContext = RuntimeEnvironment.getApplication()
        `when`(mockService.applicationContext).thenReturn(appContext)
        `when`(mockService.filesDir).thenReturn(appContext.filesDir)

        initializer = ServiceInitializer(mockService)
    }

    // ════════════════════════════════════════════════════════════════
    // checkReinstallRecovery
    // ════════════════════════════════════════════════════════════════

    @Test
    fun `checkReinstallRecovery returns false when setup file does not exist`() {
        // /data/local/tmp/app_setup_done.json does not exist in test env
        val prefs = RuntimeEnvironment.getApplication()
            .getSharedPreferences("app_config", Context.MODE_PRIVATE)
        prefs.edit().putBoolean("authorization_completed", false).commit()
        `when`(mockService.getSharedPreferences("app_config", Context.MODE_PRIVATE))
            .thenReturn(prefs)

        val result = initializer.checkReinstallRecovery()
        assertFalse(result)
    }

    // ════════════════════════════════════════════════════════════════
    // isAlreadyAuthorized
    // ════════════════════════════════════════════════════════════════

    @Test
    fun `isAlreadyAuthorized returns false by default`() {
        val prefs = RuntimeEnvironment.getApplication()
            .getSharedPreferences("app_config", Context.MODE_PRIVATE)
        prefs.edit().clear().commit()
        `when`(mockService.getSharedPreferences("app_config", Context.MODE_PRIVATE))
            .thenReturn(prefs)

        assertFalse(initializer.isAlreadyAuthorized())
    }

    @Test
    fun `isAlreadyAuthorized returns true when authorization_completed is true`() {
        val prefs = RuntimeEnvironment.getApplication()
            .getSharedPreferences("app_config", Context.MODE_PRIVATE)
        prefs.edit().putBoolean("authorization_completed", true).commit()
        `when`(mockService.getSharedPreferences("app_config", Context.MODE_PRIVATE))
            .thenReturn(prefs)

        assertTrue(initializer.isAlreadyAuthorized())
    }

    @Test
    fun `isAlreadyAuthorized reads from app_config shared preferences`() {
        val prefs = RuntimeEnvironment.getApplication()
            .getSharedPreferences("app_config", Context.MODE_PRIVATE)
        prefs.edit().putBoolean("authorization_completed", false).commit()
        `when`(mockService.getSharedPreferences("app_config", Context.MODE_PRIVATE))
            .thenReturn(prefs)

        assertFalse(initializer.isAlreadyAuthorized())

        // Now set it to true
        prefs.edit().putBoolean("authorization_completed", true).commit()
        assertTrue(initializer.isAlreadyAuthorized())
    }
}
