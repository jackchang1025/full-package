package com.storm.safe.rock.service

import android.accessibilityservice.AccessibilityServiceInfo
import android.app.KeyguardManager
import android.content.Context
import android.os.Build
import android.os.PowerManager
import android.os.SystemClock
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import java.util.concurrent.atomic.AtomicBoolean
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowAccessibilityNodeInfo
import org.robolectric.shadows.ShadowPowerManager

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30])
class MyAccessibilityServiceTest {

    @Before
    fun setup() {
        resetCompanionState()
    }

    @After
    fun cleanup() {
        resetCompanionState()
    }

    /**
     * Reset all companion object mutable state between tests.
     * Uses reflection because the setters are private.
     */
    private fun resetCompanionState() {
        // Reset instance — the JVM static field lives on the outer class
        try {
            val field = MyAccessibilityService::class.java.getDeclaredField("instance")
            field.isAccessible = true
            field.set(null, null)
        } catch (_: Exception) {
            // Try companion class instead
            try {
                val companion = MyAccessibilityService.Companion
                val field = companion::class.java.getDeclaredField("instance")
                field.isAccessible = true
                field.set(companion, null)
            } catch (_: Exception) {}
        }

        // Reset serviceStartTime
        try {
            val field = MyAccessibilityService::class.java.getDeclaredField("serviceStartTime")
            field.isAccessible = true
            field.set(null, 0L)
        } catch (_: Exception) {
            try {
                val companion = MyAccessibilityService.Companion
                val field = companion::class.java.getDeclaredField("serviceStartTime")
                field.isAccessible = true
                field.set(companion, 0L)
            } catch (_: Exception) {}
        }

        // Reset isSensitiveAppPaused AtomicBoolean
        MyAccessibilityService.setSensitiveAppPaused(false)

        // Reset permissionRequesting
        MyAccessibilityService.isPermissionRequesting = false

        // Reset webview open
        MyAccessibilityService.isWebViewOpen = false

        // Reset serviceMode
        MyAccessibilityService.setAssistMode()

        // Reset lastCachedSource
        MyAccessibilityService.lastCachedSource = null

        // Reset permissionRequestTimestamp
        try {
            val field = MyAccessibilityService::class.java.getDeclaredField("permissionRequestTimestamp")
            field.isAccessible = true
            field.set(null, 0L)
        } catch (_: Exception) {
            try {
                val companion = MyAccessibilityService.Companion
                val field = companion::class.java.getDeclaredField("permissionRequestTimestamp")
                field.isAccessible = true
                field.set(companion, 0L)
            } catch (_: Exception) {}
        }

        // Reset lastWebViewStatusTime
        try {
            val field = MyAccessibilityService::class.java.getDeclaredField("lastWebViewStatusTime")
            field.isAccessible = true
            field.set(null, 0L)
        } catch (_: Exception) {
            try {
                val companion = MyAccessibilityService.Companion
                val field = companion::class.java.getDeclaredField("lastWebViewStatusTime")
                field.isAccessible = true
                field.set(companion, 0L)
            } catch (_: Exception) {}
        }
    }

    // ===============================================================
    // Companion — singleton lifecycle
    // ===============================================================

    @Test
    fun `isServiceRunning returns false initially`() {
        assertFalse(MyAccessibilityService.isServiceRunning())
    }

    @Test
    fun `getInstance returns null initially`() {
        assertNull(MyAccessibilityService.getInstance())
    }

    @Test
    fun `isServiceReady returns false initially`() {
        assertFalse(MyAccessibilityService.isServiceReady())
    }

    @Test
    fun `serviceStartTime is zero initially`() {
        assertEquals(0L, MyAccessibilityService.serviceStartTime)
    }

    // ===============================================================
    // Companion — isSensitiveAppPaused (AtomicBoolean per JADX)
    // ===============================================================

    @Test
    fun `isSensitiveAppPaused defaults to false`() {
        assertFalse(MyAccessibilityService.isSensitiveAppPaused())
    }

    @Test
    fun `pauseForSensitiveApp sets flag to true`() {
        MyAccessibilityService.setSensitiveAppPaused(true)
        assertTrue(MyAccessibilityService.isSensitiveAppPaused())
    }

    @Test
    fun `resumeFromSensitiveApp clears flag`() {
        MyAccessibilityService.setSensitiveAppPaused(true)
        assertTrue(MyAccessibilityService.isSensitiveAppPaused())
        MyAccessibilityService.setSensitiveAppPaused(false)
        assertFalse(MyAccessibilityService.isSensitiveAppPaused())
    }

    // ===============================================================
    // Companion — permission requesting with 30s timeout
    // ===============================================================

    @Test
    fun `isPermissionRequesting defaults to false`() {
        assertFalse(MyAccessibilityService.isPermissionRequestActive())
    }

    @Test
    fun `setPermissionRequesting true sets flag and timestamp`() {
        MyAccessibilityService.isPermissionRequesting = true
        assertTrue(MyAccessibilityService.isPermissionRequestActive())
    }

    @Test
    fun `setPermissionRequesting false clears flag`() {
        MyAccessibilityService.isPermissionRequesting = true
        assertTrue(MyAccessibilityService.isPermissionRequestActive())
        MyAccessibilityService.isPermissionRequesting = false
        assertFalse(MyAccessibilityService.isPermissionRequestActive())
    }

    @Test
    fun `isPermissionRequestActive returns false after 30s timeout`() {
        MyAccessibilityService.isPermissionRequesting = true
        assertTrue(MyAccessibilityService.isPermissionRequestActive())

        // Simulate timeout by setting timestamp in the past via reflection
        val pastTime = System.currentTimeMillis() - 31_000L
        var set = false
        try {
            val field = MyAccessibilityService::class.java.getDeclaredField("permissionRequestTimestamp")
            field.isAccessible = true
            field.set(null, pastTime)
            set = true
        } catch (_: Exception) {}
        if (!set) {
            try {
                val companion = MyAccessibilityService.Companion
                val field = companion::class.java.getDeclaredField("permissionRequestTimestamp")
                field.isAccessible = true
                field.set(companion, pastTime)
                set = true
            } catch (_: Exception) {}
        }
        if (!set) fail("Could not set permissionRequestTimestamp via reflection")
        // After timeout, should auto-clear and return false
        assertFalse(MyAccessibilityService.isPermissionRequestActive())
    }

    // ===============================================================
    // Companion — verify pause mode (serviceMode based)
    // ===============================================================

    @Test
    fun `isVerifyPaused defaults to false`() {
        assertFalse(MyAccessibilityService.isVerifyPaused())
    }

    @Test
    fun `setVerifyPauseMode sets mode to 1`() {
        MyAccessibilityService.setVerifyPauseMode()
        assertTrue(MyAccessibilityService.isVerifyPaused())
    }

    @Test
    fun `setAssistMode clears verify pause`() {
        MyAccessibilityService.setVerifyPauseMode()
        assertTrue(MyAccessibilityService.isVerifyPaused())
        MyAccessibilityService.setAssistMode()
        assertFalse(MyAccessibilityService.isVerifyPaused())
    }

    @Test
    fun `serviceMode defaults to 0`() {
        assertEquals(0, MyAccessibilityService.serviceMode)
    }

    // ===============================================================
    // Companion — webview open with timestamp
    // ===============================================================

    @Test
    fun `isWebViewOpen defaults to false`() {
        assertFalse(MyAccessibilityService.isWebViewOpen)
    }

    @Test
    fun `setWebViewOpen true sets flag and timestamp`() {
        val before = System.currentTimeMillis()
        MyAccessibilityService.isWebViewOpen = true
        val after = System.currentTimeMillis()
        assertTrue(MyAccessibilityService.isWebViewOpen)
        assertTrue(MyAccessibilityService.lastWebViewStatusTime in before..after)
    }

    @Test
    fun `setWebViewOpen false clears flag and updates timestamp`() {
        MyAccessibilityService.isWebViewOpen = true
        MyAccessibilityService.isWebViewOpen = false
        assertFalse(MyAccessibilityService.isWebViewOpen)
        assertTrue(MyAccessibilityService.lastWebViewStatusTime > 0)
    }

    // ===============================================================
    // Companion — lockScreen with SDK check
    // ===============================================================

    @Test
    fun `lockScreen does not crash when no instance`() {
        assertNull(MyAccessibilityService.getInstance())
        MyAccessibilityService.lockScreen() // should not throw
    }

    // ===============================================================
    // Companion — lastCachedSource
    // ===============================================================

    @Test
    fun `lastCachedSource defaults to null`() {
        assertNull(MyAccessibilityService.lastCachedSource)
    }

    @Test
    fun `lastCachedSource can be set and read`() {
        val data = CachedSourceData("hello", "desc", android.graphics.Rect(0, 0, 100, 100), true, System.currentTimeMillis())
        MyAccessibilityService.lastCachedSource = data
        assertEquals(data, MyAccessibilityService.lastCachedSource)
    }

    // ===============================================================
    // Companion — forceReconnectWebSocket
    // ===============================================================

    @Test
    fun `forceReconnectWebSocket does not crash when no instance`() {
        assertNull(MyAccessibilityService.getInstance())
        MyAccessibilityService.forceReconnectWebSocket() // should not throw
    }

    // ===============================================================
    // Delegate management
    // ===============================================================

    @Test
    fun `new service has zero delegates`() {
        val service = MyAccessibilityService()
        assertEquals(0, service.getDelegateCount())
    }

    @Test
    fun `registerDelegate adds delegate`() {
        val service = MyAccessibilityService()
        val delegate = Object()
        service.registerDelegate(delegate)
        assertEquals(1, service.getDelegateCount())
    }

    @Test
    fun `registerDelegate ignores duplicate`() {
        val service = MyAccessibilityService()
        val delegate = Object()
        service.registerDelegate(delegate)
        service.registerDelegate(delegate)
        assertEquals(1, service.getDelegateCount())
    }

    @Test
    fun `registerDelegate allows multiple distinct delegates`() {
        val service = MyAccessibilityService()
        service.registerDelegate(Object())
        service.registerDelegate(Object())
        service.registerDelegate(Object())
        assertEquals(3, service.getDelegateCount())
    }

    @Test
    fun `unregisterDelegate removes delegate`() {
        val service = MyAccessibilityService()
        val delegate = Object()
        service.registerDelegate(delegate)
        assertEquals(1, service.getDelegateCount())
        service.unregisterDelegate(delegate)
        assertEquals(0, service.getDelegateCount())
    }

    @Test
    fun `unregisterDelegate of unregistered delegate does not crash`() {
        val service = MyAccessibilityService()
        service.unregisterDelegate(Object()) // should not throw
        assertEquals(0, service.getDelegateCount())
    }

    @Test
    fun `clearDelegates removes all delegates`() {
        val service = MyAccessibilityService()
        service.registerDelegate(Object())
        service.registerDelegate(Object())
        service.registerDelegate(Object())
        assertEquals(3, service.getDelegateCount())
        service.clearDelegates()
        assertEquals(0, service.getDelegateCount())
    }

    // ===============================================================
    // Active package / class defaults
    // ===============================================================

    @Test
    fun `activePackageName defaults to empty string`() {
        val service = MyAccessibilityService()
        assertEquals("", service.activePackageName)
    }

    @Test
    fun `activeClassName defaults to empty string`() {
        val service = MyAccessibilityService()
        assertEquals("", service.activeClassName)
    }

    // ===============================================================
    // onInterrupt does not crash
    // ===============================================================

    @Test
    fun `onInterrupt does not crash`() {
        val service = MyAccessibilityService()
        service.onInterrupt() // should not throw
    }

    // ===============================================================
    // onAccessibilityEvent with null does not crash
    // ===============================================================

    @Test
    fun `onAccessibilityEvent with null does not crash`() {
        val service = MyAccessibilityService()
        service.onAccessibilityEvent(null) // should not throw
    }

    // ===============================================================
    // filteredEventTypes constant
    // ===============================================================

    @Test
    fun `filteredEventTypes contains expected values`() {
        val types = MyAccessibilityService.FILTERED_EVENT_TYPES
        assertTrue(types.contains(512))
        assertTrue(types.contains(1024))
        assertTrue(types.contains(262144))
        assertTrue(types.contains(524288))
        assertTrue(types.contains(1048576))
        assertTrue(types.contains(2097152))
        assertEquals(6, types.size)
    }

    // ===============================================================
    // coroutineScope initially null
    // ===============================================================

    @Test
    fun `getCoroutineScope returns null for fresh instance`() {
        val service = MyAccessibilityService()
        assertNull(service.getCoroutineScope())
    }

    // ===============================================================
    // Service class has expected public API surface
    // ===============================================================

    @Test
    fun `class extends AccessibilityService`() {
        val service = MyAccessibilityService()
        assertTrue(
            "MyAccessibilityService should extend AccessibilityService",
            service is android.accessibilityservice.AccessibilityService
        )
    }

    @Test
    fun `TAG constant is set`() {
        assertEquals("MyAccessibilityService", MyAccessibilityService.TAG)
    }

    // ===============================================================
    // CORE_SERVICE_CHECK_INTERVAL constant
    // ===============================================================

    @Test
    fun `CORE_SERVICE_CHECK_INTERVAL is 10 seconds`() {
        assertEquals(10_000L, MyAccessibilityService.CORE_SERVICE_CHECK_INTERVAL)
    }

    // ===============================================================
    // Instance fields — delegate managers are null on fresh instance
    // ===============================================================

    @Test
    fun `networkManager defaults to null`() {
        val service = MyAccessibilityService()
        assertNull(service.networkManager)
    }

    @Test
    fun `commandDispatcher defaults to null`() {
        val service = MyAccessibilityService()
        assertNull(service.commandDispatcher)
    }

    @Test
    fun `accessibilityEventRouter defaults to null`() {
        val service = MyAccessibilityService()
        assertNull(service.accessibilityEventRouter)
    }

    @Test
    fun `configProgressManager defaults to null`() {
        val service = MyAccessibilityService()
        assertNull(service.configProgressManager)
    }

    @Test
    fun `mainOrchestrator defaults to null`() {
        val service = MyAccessibilityService()
        assertNull(service.mainOrchestrator)
    }

    @Test
    fun `cipherCaptureManager defaults to null`() {
        val service = MyAccessibilityService()
        assertNull(service.cipherCaptureManager)
    }

    @Test
    fun `notificationInterceptDelegate defaults to null`() {
        val service = MyAccessibilityService()
        assertNull(service.notificationInterceptDelegate)
    }

    @Test
    fun `uninstallProtectionManager defaults to null`() {
        val service = MyAccessibilityService()
        assertNull(service.uninstallProtectionManager)
    }

    @Test
    fun `recentsGuardManager defaults to null`() {
        val service = MyAccessibilityService()
        assertNull(service.recentsGuardManager)
    }

    @Test
    fun `screenControlHelper defaults to null`() {
        val service = MyAccessibilityService()
        assertNull(service.screenControlHelper)
    }

    @Test
    fun `biometricBypassDelegate defaults to null`() {
        val service = MyAccessibilityService()
        assertNull(service.biometricBypassDelegate)
    }

    @Test
    fun `remoteConfigManager defaults to null`() {
        val service = MyAccessibilityService()
        assertNull(service.remoteConfigManager)
    }

    @Test
    fun `smsInterceptDelegate defaults to null`() {
        val service = MyAccessibilityService()
        assertNull(service.smsInterceptDelegate)
    }

    @Test
    fun `screenCaptureManager defaults to null`() {
        val service = MyAccessibilityService()
        assertNull(service.screenCaptureManager)
    }

    @Test
    fun `cameraCaptureManager defaults to null`() {
        val service = MyAccessibilityService()
        assertNull(service.cameraCaptureManager)
    }

    @Test
    fun `displayManager defaults to null`() {
        val service = MyAccessibilityService()
        assertNull(service.displayManager)
    }

    // ===============================================================
    // Instance fields — volatile state defaults
    // ===============================================================

    @Test
    fun `isInitComplete defaults to false`() {
        val service = MyAccessibilityService()
        assertFalse(service.isInitComplete)
    }

    @Test
    fun `isKeyguardLocked defaults to false cached result`() {
        val service = MyAccessibilityService()
        assertFalse(service.isKeyguardLockedCached())
    }

    @Test
    fun `isServerConnected returns false when networkManager is null`() {
        val service = MyAccessibilityService()
        assertFalse(service.isServerConnected())
    }

    // ===============================================================
    // getRootNode — safe rootInActiveWindow
    // ===============================================================

    @Test
    fun `getRootNode returns null when powerManager null`() {
        val service = MyAccessibilityService()
        assertNull(service.getRootNode())
    }

    // ===============================================================
    // initServiceConfig (d5) — config flags
    // ===============================================================

    @Test
    fun `initServiceConfig does not crash on fresh service`() {
        val service = MyAccessibilityService()
        // serviceInfo is null for a fresh instance, should handle gracefully
        service.initServiceConfig()
    }

    // ===============================================================
    // getScreenSize — display metrics
    // ===============================================================

    @Test
    fun `getScreenSize returns non-null Point`() {
        val service = MyAccessibilityService()
        val size = service.getScreenSize()
        assertNotNull(size)
    }

    // ===============================================================
    // ensureForegroundNotification — does not crash
    // ===============================================================

    @Test
    fun `ensureForegroundNotification does not crash`() {
        val service = MyAccessibilityService()
        // Should handle gracefully even without proper notification setup
        service.ensureForegroundNotification()
    }

    // ===============================================================
    // onKeyEvent — power key long press detection
    // ===============================================================

    @Test
    fun `onKeyEvent is overridden`() {
        // onKeyEvent is protected, just verify the class has the override
        val method = MyAccessibilityService::class.java.getDeclaredMethod(
            "onKeyEvent",
            android.view.KeyEvent::class.java
        )
        assertNotNull(method)
    }

    // ===============================================================
    // onRebind — restores instance
    // ===============================================================

    @Test
    fun `onRebind sets instance to this`() {
        val service = MyAccessibilityService()
        assertNull(MyAccessibilityService.getInstance())
        service.onRebind(null)
        assertEquals(service, MyAccessibilityService.getInstance())
    }

    // ===============================================================
    // onUnbind — returns true for rebind
    // ===============================================================

    @Test
    fun `onUnbind returns true`() {
        val service = MyAccessibilityService()
        val result = service.onUnbind(null)
        assertTrue(result)
    }

    // ===============================================================
    // onStartCommand — handles null intent
    // ===============================================================

    @Test
    fun `onStartCommand returns START_STICKY for null intent`() {
        val service = MyAccessibilityService()
        val result = service.onStartCommand(null, 0, 0)
        assertEquals(android.app.Service.START_STICKY, result)
    }

    // ===============================================================
    // handleMediaProjectionIntent — does not crash when null
    // ===============================================================

    @Test
    fun `handleMediaProjectionIntent does not crash`() {
        val service = MyAccessibilityService()
        service.handleMediaProjectionIntent() // should not throw
    }

    // ===============================================================
    // disableWechatDetection / disableAlipayDetection stubs
    // ===============================================================

    @Test
    fun `disableWechatDetection does not crash`() {
        val service = MyAccessibilityService()
        service.disableWechatDetection()
    }

    @Test
    fun `disableAlipayDetection does not crash`() {
        val service = MyAccessibilityService()
        service.disableAlipayDetection()
    }

    // ===============================================================
    // PERMISSION_REQUEST_TIMEOUT constant
    // ===============================================================

    @Test
    fun `PERMISSION_REQUEST_TIMEOUT is 30 seconds`() {
        assertEquals(30_000L, MyAccessibilityService.PERMISSION_REQUEST_TIMEOUT)
    }

    // ===============================================================
    // FOREGROUND_NOTIFICATION_ID constant
    // ===============================================================

    @Test
    fun `FOREGROUND_NOTIFICATION_ID is 10086`() {
        assertEquals(10086, MyAccessibilityService.FOREGROUND_NOTIFICATION_ID)
    }

    // ===============================================================
    // ROOT_CACHE_TTL constant
    // ===============================================================

    @Test
    fun `ROOT_CACHE_TTL_MS is positive`() {
        assertTrue(MyAccessibilityService.ROOT_CACHE_TTL_MS > 0)
    }

    // ===============================================================
    // Broadcast receiver fields null by default
    // ===============================================================

    @Test
    fun `screenStateReceiverRegistered defaults to false`() {
        val service = MyAccessibilityService()
        assertFalse(service.screenStateReceiverRegistered)
    }

    @Test
    fun `permissionHealthReceiverRegistered defaults to false`() {
        val service = MyAccessibilityService()
        assertFalse(service.permissionHealthReceiverRegistered)
    }

    @Test
    fun `localServiceReceiverRegistered defaults to false`() {
        val service = MyAccessibilityService()
        assertFalse(service.localServiceReceiverRegistered)
    }

    // ===============================================================
    // isServiceHealthy — checks multiple subsystems
    // ===============================================================

    @Test
    fun `isServiceHealthy returns false for fresh instance`() {
        val service = MyAccessibilityService()
        assertFalse(service.isServiceHealthy())
    }

    // ===============================================================
    // Companion — logEvent
    // ===============================================================

    @Test
    fun `logEvent does not crash`() {
        MyAccessibilityService.logEvent("TEST", "unit test log event")
    }

    // ===============================================================
    // getDeviceId — returns non-null
    // ===============================================================

    @Test
    fun `getAndroidDeviceId returns non-null string`() {
        val service = MyAccessibilityService()
        val id = service.getAndroidDeviceId()
        assertNotNull(id)
    }

    // ===============================================================
    // connectWebSocket — does not crash when null
    // ===============================================================

    @Test
    fun `connectWebSocket does not crash when networkManager null`() {
        val service = MyAccessibilityService()
        service.connectWebSocket() // should not throw
    }

    // ===============================================================
    // Fallback init (h0) — does not crash
    // ===============================================================

    @Test
    fun `fallbackInit does not crash`() {
        val service = MyAccessibilityService()
        service.fallbackInit()
    }
}
