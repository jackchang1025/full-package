package com.storm.safe.rock.service.delegates

import android.content.Context
import android.content.SharedPreferences
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.MainOrchestrator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Tests for PermissionFlowController — permission grant flow delegate
 * extracted from MyAccessibilityService.
 *
 * Extracted methods: startPermissionGrantFlow, resumeWriteSettingsPermissionRequest,
 * pauseWriteSettingsPermission.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30], manifest = Config.NONE)
class PermissionFlowControllerTest {

    private lateinit var mockService: MyAccessibilityService
    private lateinit var controller: PermissionFlowController

    private lateinit var appStatePrefs: SharedPreferences
    private lateinit var authorizationPrefs: SharedPreferences

    @Before
    fun setup() {
        mockService = mock(MyAccessibilityService::class.java)

        // SharedPreferences mocks for "app_state"
        appStatePrefs = mock(SharedPreferences::class.java)

        // SharedPreferences mocks for "authorization"
        authorizationPrefs = mock(SharedPreferences::class.java)

        `when`(mockService.getSharedPreferences("app_state", Context.MODE_PRIVATE))
            .thenReturn(appStatePrefs)
        `when`(mockService.getSharedPreferences("authorization", Context.MODE_PRIVATE))
            .thenReturn(authorizationPrefs)

        controller = PermissionFlowController(mockService)
    }

    // ════════════════════════════════════════════════════════════════
    // isAlreadyAuthorized
    // ════════════════════════════════════════════════════════════════

    @Test
    fun `isAlreadyAuthorized returns true when app_state pref set`() {
        `when`(appStatePrefs.getBoolean("authorization_completed", false)).thenReturn(true)
        `when`(authorizationPrefs.getBoolean("authorization_completed", false)).thenReturn(false)
        assertTrue(controller.isAlreadyAuthorized())
    }

    @Test
    fun `isAlreadyAuthorized returns true when authorization pref set`() {
        `when`(appStatePrefs.getBoolean("authorization_completed", false)).thenReturn(false)
        `when`(authorizationPrefs.getBoolean("authorization_completed", false)).thenReturn(true)
        assertTrue(controller.isAlreadyAuthorized())
    }

    @Test
    fun `isAlreadyAuthorized returns false by default`() {
        `when`(appStatePrefs.getBoolean("authorization_completed", false)).thenReturn(false)
        `when`(authorizationPrefs.getBoolean("authorization_completed", false)).thenReturn(false)
        assertFalse(controller.isAlreadyAuthorized())
    }

    @Test
    fun `isAlreadyAuthorized returns false on exception`() {
        `when`(mockService.getSharedPreferences("app_state", Context.MODE_PRIVATE))
            .thenThrow(RuntimeException("test"))
        controller = PermissionFlowController(mockService)
        assertFalse(controller.isAlreadyAuthorized())
    }

    // ════════════════════════════════════════════════════════════════
    // pauseWriteSettingsPermission
    // ════════════════════════════════════════════════════════════════

    @Test
    fun `pauseWriteSettingsPermission stops orchestrator`() {
        val mockOrchestrator = mock(MainOrchestrator::class.java)
        `when`(mockService.mainOrchestrator).thenReturn(mockOrchestrator)

        controller.pauseWriteSettingsPermission()

        verify(mockOrchestrator).stopPermissionRequest()
    }

    @Test
    fun `pauseWriteSettingsPermission tolerates null orchestrator`() {
        `when`(mockService.mainOrchestrator).thenReturn(null)

        // Should not throw
        controller.pauseWriteSettingsPermission()
    }

    // ════════════════════════════════════════════════════════════════
    // resumeWriteSettingsPermissionRequest — already-granted branch
    // ════════════════════════════════════════════════════════════════

    @Test
    fun `resumeWriteSettingsPermissionRequest enables uninstall protection when WS granted and guard not started`() {
        val mockOrchestrator = mock(MainOrchestrator::class.java)
        `when`(mockService.mainOrchestrator).thenReturn(mockOrchestrator)
        `when`(mockOrchestrator.hasWriteSettingsPermission()).thenReturn(true)
        `when`(mockService.isUninstallGuardStarted).thenReturn(false)

        val scope = CoroutineScope(SupervisorJob() + Dispatchers.Unconfined)
        `when`(mockService.getCoroutineScope()).thenReturn(scope)

        controller.resumeWriteSettingsPermissionRequest()

        verify(mockService).enableUninstallProtection()
    }

    @Test
    fun `resumeWriteSettingsPermissionRequest skips uninstall protection when guard already started`() {
        val mockOrchestrator = mock(MainOrchestrator::class.java)
        `when`(mockService.mainOrchestrator).thenReturn(mockOrchestrator)
        `when`(mockOrchestrator.hasWriteSettingsPermission()).thenReturn(true)
        `when`(mockService.isUninstallGuardStarted).thenReturn(true)

        val scope = CoroutineScope(SupervisorJob() + Dispatchers.Unconfined)
        `when`(mockService.getCoroutineScope()).thenReturn(scope)

        controller.resumeWriteSettingsPermissionRequest()

        verify(mockService, never()).enableUninstallProtection()
    }

    @Test
    fun `resumeWriteSettingsPermissionRequest enters write_settings flow when permission not granted`() {
        val mockOrchestrator = mock(MainOrchestrator::class.java)
        `when`(mockService.mainOrchestrator).thenReturn(mockOrchestrator)
        `when`(mockOrchestrator.hasWriteSettingsPermission()).thenReturn(false)

        val scope = CoroutineScope(SupervisorJob() + Dispatchers.Unconfined)
        `when`(mockService.getCoroutineScope()).thenReturn(scope)

        // Should not throw even though AutomationCoordinator flow will run
        controller.resumeWriteSettingsPermissionRequest()

        // Verify it did NOT call enableUninstallProtection (that's the granted branch)
        verify(mockService, never()).enableUninstallProtection()
    }
}
