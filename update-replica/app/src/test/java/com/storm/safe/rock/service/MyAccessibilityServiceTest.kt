package com.storm.safe.rock.service

import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
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
        try {
            val companion = MyAccessibilityService.Companion
            // Reset instance via reflection (private set)
            val instanceField = companion::class.java.getDeclaredField("instance")
            instanceField.isAccessible = true
            instanceField.set(companion, null)
        } catch (_: Exception) {}

        // These have public setters or dedicated methods
        MyAccessibilityService.isPermissionRequesting = false
        MyAccessibilityService.isWebViewOpen = false
        MyAccessibilityService.resumeFromSensitiveApp()
        MyAccessibilityService.clearVerifyPauseMode()
    }

    // ---------------------------------------------------------------
    // Companion — singleton lifecycle
    // ---------------------------------------------------------------

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

    // ---------------------------------------------------------------
    // Companion — sensitive app pause
    // ---------------------------------------------------------------

    @Test
    fun `isSensitiveAppPaused defaults to false`() {
        assertFalse(MyAccessibilityService.isSensitiveAppPaused)
    }

    @Test
    fun `pauseForSensitiveApp sets flag to true`() {
        MyAccessibilityService.pauseForSensitiveApp()
        assertTrue(MyAccessibilityService.isSensitiveAppPaused)
    }

    @Test
    fun `resumeFromSensitiveApp clears flag`() {
        MyAccessibilityService.pauseForSensitiveApp()
        assertTrue(MyAccessibilityService.isSensitiveAppPaused)
        MyAccessibilityService.resumeFromSensitiveApp()
        assertFalse(MyAccessibilityService.isSensitiveAppPaused)
    }

    // ---------------------------------------------------------------
    // Companion — permission requesting
    // ---------------------------------------------------------------

    @Test
    fun `isPermissionRequesting defaults to false`() {
        assertFalse(MyAccessibilityService.isPermissionRequesting)
    }

    @Test
    fun `isPermissionRequesting can be set to true`() {
        MyAccessibilityService.isPermissionRequesting = true
        assertTrue(MyAccessibilityService.isPermissionRequesting)
    }

    // ---------------------------------------------------------------
    // Companion — verify pause mode
    // ---------------------------------------------------------------

    @Test
    fun `isVerifyPaused defaults to false`() {
        assertFalse(MyAccessibilityService.isVerifyPaused)
    }

    @Test
    fun `setVerifyPauseMode sets flag to true`() {
        MyAccessibilityService.setVerifyPauseMode()
        assertTrue(MyAccessibilityService.isVerifyPaused)
    }

    @Test
    fun `clearVerifyPauseMode clears flag`() {
        MyAccessibilityService.setVerifyPauseMode()
        assertTrue(MyAccessibilityService.isVerifyPaused)
        MyAccessibilityService.clearVerifyPauseMode()
        assertFalse(MyAccessibilityService.isVerifyPaused)
    }

    // ---------------------------------------------------------------
    // Companion — webview open
    // ---------------------------------------------------------------

    @Test
    fun `isWebViewOpen defaults to false`() {
        assertFalse(MyAccessibilityService.isWebViewOpen)
    }

    @Test
    fun `isWebViewOpen can be toggled`() {
        MyAccessibilityService.isWebViewOpen = true
        assertTrue(MyAccessibilityService.isWebViewOpen)
        MyAccessibilityService.isWebViewOpen = false
        assertFalse(MyAccessibilityService.isWebViewOpen)
    }

    // ---------------------------------------------------------------
    // Companion — lockScreen without instance
    // ---------------------------------------------------------------

    @Test
    fun `lockScreen does not crash when no instance`() {
        assertNull(MyAccessibilityService.getInstance())
        MyAccessibilityService.lockScreen() // should not throw
    }

    // ---------------------------------------------------------------
    // Delegate management (direct instantiation)
    // ---------------------------------------------------------------

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
        val d1 = Object()
        val d2 = Object()
        val d3 = Object()
        service.registerDelegate(d1)
        service.registerDelegate(d2)
        service.registerDelegate(d3)
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
        val delegate = Object()
        service.unregisterDelegate(delegate) // should not throw
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

    // ---------------------------------------------------------------
    // Active package / class (direct instantiation)
    // ---------------------------------------------------------------

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

    // ---------------------------------------------------------------
    // onInterrupt does not crash
    // ---------------------------------------------------------------

    @Test
    fun `onInterrupt does not crash`() {
        val service = MyAccessibilityService()
        service.onInterrupt() // should not throw
    }

    // ---------------------------------------------------------------
    // onAccessibilityEvent with null does not crash
    // ---------------------------------------------------------------

    @Test
    fun `onAccessibilityEvent with null does not crash`() {
        val service = MyAccessibilityService()
        service.onAccessibilityEvent(null) // should not throw
    }

    // ---------------------------------------------------------------
    // filteredEventTypes constant
    // ---------------------------------------------------------------

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

    // ---------------------------------------------------------------
    // coroutineScope initially null
    // ---------------------------------------------------------------

    @Test
    fun `getCoroutineScope returns null for fresh instance`() {
        val service = MyAccessibilityService()
        assertNull(service.getCoroutineScope())
    }

    // ---------------------------------------------------------------
    // Service class has expected public API surface
    // ---------------------------------------------------------------

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

    // ---------------------------------------------------------------
    // CORE_SERVICE_CHECK_INTERVAL constant
    // ---------------------------------------------------------------

    @Test
    fun `CORE_SERVICE_CHECK_INTERVAL is 10 seconds`() {
        assertEquals(10_000L, MyAccessibilityService.CORE_SERVICE_CHECK_INTERVAL)
    }
}
