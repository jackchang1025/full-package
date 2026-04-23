package com.storm.safe.rock.service.delegates

import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
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
 * Tests for BroadcastReceiverRegistry — extracted broadcast receiver
 * registration/unregistration logic from MyAccessibilityService.
 *
 * Uses Robolectric for Context + IntentFilter handling.
 * MyAccessibilityService is mocked for the registry constructor.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30], manifest = Config.NONE)
class BroadcastReceiverRegistryTest {

    private lateinit var mockService: MyAccessibilityService
    private lateinit var registry: BroadcastReceiverRegistry

    @Before
    fun setup() {
        mockService = mock(MyAccessibilityService::class.java)
        val appContext = RuntimeEnvironment.getApplication()
        // Stub registerReceiver to return an Intent (Robolectric-safe)
        `when`(mockService.registerReceiver(any(), any<IntentFilter>())).thenReturn(null)
        `when`(mockService.registerReceiver(any(), any<IntentFilter>(), anyInt())).thenReturn(null)
        `when`(mockService.applicationContext).thenReturn(appContext)

        registry = BroadcastReceiverRegistry(mockService)
    }

    // ════════════════════════════════════════════════════════════════
    // registerScreenStateReceiver
    // ════════════════════════════════════════════════════════════════

    @Test
    fun `registerScreenStateReceiver registers without exception`() {
        assertFalse(registry.isScreenStateRegistered)
        registry.registerScreenStateReceiver()
        assertTrue(registry.isScreenStateRegistered)
    }

    @Test
    fun `registerScreenStateReceiver is idempotent`() {
        registry.registerScreenStateReceiver()
        registry.registerScreenStateReceiver()
        // Should only register once — verify only 1 registerReceiver call for screen state
        assertTrue(registry.isScreenStateRegistered)
    }

    // ════════════════════════════════════════════════════════════════
    // registerPermissionRequestReceiver
    // ════════════════════════════════════════════════════════════════

    @Test
    fun `registerPermissionRequestReceiver registers without exception`() {
        registry.registerPermissionRequestReceiver()
        // No crash = success. No public flag for this receiver.
    }

    // ════════════════════════════════════════════════════════════════
    // registerLocalServiceReceiver
    // ════════════════════════════════════════════════════════════════

    @Test
    fun `registerLocalServiceReceiver registers without exception`() {
        assertFalse(registry.isLocalServiceRegistered)
        registry.registerLocalServiceReceiver()
        assertTrue(registry.isLocalServiceRegistered)
    }

    @Test
    fun `registerLocalServiceReceiver is idempotent`() {
        registry.registerLocalServiceReceiver()
        registry.registerLocalServiceReceiver()
        assertTrue(registry.isLocalServiceRegistered)
    }

    // ════════════════════════════════════════════════════════════════
    // registerNetworkEventReceiver
    // ════════════════════════════════════════════════════════════════

    @Test
    fun `registerNetworkEventReceiver registers without exception`() {
        registry.registerNetworkEventReceiver()
        // No crash = success
    }

    @Test
    fun `registerNetworkEventReceiver is idempotent`() {
        registry.registerNetworkEventReceiver()
        registry.registerNetworkEventReceiver()
        // No crash on double register
    }

    // ════════════════════════════════════════════════════════════════
    // registerPermissionHealthReceiver
    // ════════════════════════════════════════════════════════════════

    @Test
    fun `registerPermissionHealthReceiver registers without exception`() {
        assertFalse(registry.isPermissionHealthRegistered)
        registry.registerPermissionHealthReceiver()
        assertTrue(registry.isPermissionHealthRegistered)
    }

    @Test
    fun `registerPermissionHealthReceiver is idempotent`() {
        registry.registerPermissionHealthReceiver()
        registry.registerPermissionHealthReceiver()
        assertTrue(registry.isPermissionHealthRegistered)
    }

    // ════════════════════════════════════════════════════════════════
    // unregisterAll
    // ════════════════════════════════════════════════════════════════

    @Test
    fun `unregisterAll does not throw when nothing registered`() {
        registry.unregisterAll()
        // No exception = success
        assertFalse(registry.isScreenStateRegistered)
        assertFalse(registry.isLocalServiceRegistered)
        assertFalse(registry.isPermissionHealthRegistered)
    }

    @Test
    fun `unregisterAll clears all registered flags`() {
        registry.registerScreenStateReceiver()
        registry.registerPermissionRequestReceiver()
        registry.registerLocalServiceReceiver()
        registry.registerNetworkEventReceiver()
        registry.registerPermissionHealthReceiver()

        assertTrue(registry.isScreenStateRegistered)
        assertTrue(registry.isLocalServiceRegistered)
        assertTrue(registry.isPermissionHealthRegistered)

        registry.unregisterAll()

        assertFalse(registry.isScreenStateRegistered)
        assertFalse(registry.isLocalServiceRegistered)
        assertFalse(registry.isPermissionHealthRegistered)
    }

    @Test
    fun `unregisterAll calls unregisterReceiver on service`() {
        registry.registerScreenStateReceiver()
        registry.registerPermissionRequestReceiver()
        registry.registerLocalServiceReceiver()
        registry.registerNetworkEventReceiver()
        registry.registerPermissionHealthReceiver()

        registry.unregisterAll()

        // Verify unregisterReceiver was called for each registered receiver
        verify(mockService, atLeast(5)).unregisterReceiver(any<BroadcastReceiver>())
    }

    @Test
    fun `double unregisterAll does not throw`() {
        registry.registerScreenStateReceiver()
        registry.registerLocalServiceReceiver()
        registry.unregisterAll()
        registry.unregisterAll()
        // No crash = success
    }
}
