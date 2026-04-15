package com.storm.safe.rock.service.modules

import android.content.Context
import android.view.accessibility.AccessibilityEvent
import com.storm.safe.rock.service.MyAccessibilityService
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

/**
 * Tests for EventFilterManager — C0614i9 replica.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30], manifest = Config.NONE)
class EventFilterManagerTest {

    private lateinit var context: Context
    private lateinit var mockService: MyAccessibilityService
    private lateinit var manager: EventFilterManager

    @Before
    fun setup() {
        context = RuntimeEnvironment.getApplication()
        mockService = mock(MyAccessibilityService::class.java)
        `when`(mockService.applicationContext).thenReturn(context)
        `when`(mockService.packageName).thenReturn("com.storm.safe.rock")
        manager = EventFilterManager(mockService, context)
    }

    @After
    fun teardown() {
        manager.release()
    }

    // ════════════════════════════════════════════════════════════════
    // Construction
    // ════════════════════════════════════════════════════════════════

    @Test
    fun `constructor initializes with default state`() {
        assertFalse(manager.isAlipayDetectionEnabled)
        assertFalse(manager.isWechatDetectionEnabled)
        assertFalse(manager.isAutoPasswordEnabled)
        assertFalse(manager.isPhoneManagerCamouflageEnabled)
        assertFalse(manager.isSecondaryCaptureMode)
        assertFalse(manager.isAuthStateRestored)
        assertEquals(0L, manager.lastTextSelectionTime)
    }

    // ════════════════════════════════════════════════════════════════
    // Alipay Detection
    // ════════════════════════════════════════════════════════════════

    @Test
    fun `enableAlipayDetection sets flag and delay`() {
        manager.enableAlipayDetection(3000L)
        assertTrue(manager.isAlipayDetectionEnabled)
        assertEquals(3000L, manager.alipayDetectionDelay)
    }

    @Test
    fun `disableAlipayDetection clears flag`() {
        manager.enableAlipayDetection(3000L)
        assertTrue(manager.isAlipayDetectionEnabled)

        manager.disableAlipayDetection()
        assertFalse(manager.isAlipayDetectionEnabled)
    }

    @Test
    fun `enableAlipayDetection default delay is 5000ms`() {
        assertEquals(5000L, manager.alipayDetectionDelay)
    }

    // ════════════════════════════════════════════════════════════════
    // WeChat Detection
    // ════════════════════════════════════════════════════════════════

    @Test
    fun `enableWechatDetection sets flag and delay`() {
        manager.enableWechatDetection(7000L)
        assertTrue(manager.isWechatDetectionEnabled)
        assertEquals(7000L, manager.wechatDetectionDelay)
    }

    @Test
    fun `disableWechatDetection clears flag`() {
        manager.enableWechatDetection(7000L)
        manager.disableWechatDetection()
        assertFalse(manager.isWechatDetectionEnabled)
    }

    // ════════════════════════════════════════════════════════════════
    // Auto Password Detection
    // ════════════════════════════════════════════════════════════════

    @Test
    fun `enableAutoPassword sets flag and delay`() {
        manager.enableAutoPassword(10000L)
        assertTrue(manager.isAutoPasswordEnabled)
        assertEquals(10000L, manager.autoPasswordDelay)
    }

    @Test
    fun `disableAutoPassword clears flag`() {
        manager.enableAutoPassword(10000L)
        manager.disableAutoPassword()
        assertFalse(manager.isAutoPasswordEnabled)
    }

    // ════════════════════════════════════════════════════════════════
    // Camouflage Monitoring
    // ════════════════════════════════════════════════════════════════

    @Test
    fun `enableCamouflageMonitoring sets flag and persists`() {
        manager.enableCamouflageMonitoring()
        assertTrue(manager.isPhoneManagerCamouflageEnabled)

        // Verify SharedPreferences persistence
        val prefs = context.getSharedPreferences("camouflage_state", Context.MODE_PRIVATE)
        assertTrue(prefs.getBoolean("phone_manager_camouflage_enabled", false))
    }

    @Test
    fun `disableCamouflageMonitoring clears flag and persists`() {
        manager.enableCamouflageMonitoring()
        manager.disableCamouflageMonitoring()
        assertFalse(manager.isPhoneManagerCamouflageEnabled)

        val prefs = context.getSharedPreferences("camouflage_state", Context.MODE_PRIVATE)
        assertFalse(prefs.getBoolean("phone_manager_camouflage_enabled", false))
    }

    // ════════════════════════════════════════════════════════════════
    // Event Dispatch
    // ════════════════════════════════════════════════════════════════

    @Test
    fun `onAccessibilityEvent does not throw`() {
        val event = mock(AccessibilityEvent::class.java)
        `when`(event.eventType).thenReturn(32)
        `when`(event.packageName).thenReturn("com.example.app")
        `when`(event.className).thenReturn("com.example.Activity")

        // Should not throw
        manager.onAccessibilityEvent(event)
    }

    @Test
    fun `onAccessibilityEvent in secondary capture mode returns early`() {
        manager.isSecondaryCaptureMode = true
        val event = mock(AccessibilityEvent::class.java)
        `when`(event.eventType).thenReturn(2048)

        // Should not throw and should return without reaching MainOrchestrator
        manager.onAccessibilityEvent(event)
        verify(mockService, never()).mainOrchestrator
    }

    @Test
    fun `onAccessibilityEvent dispatches to MainOrchestrator in normal mode`() {
        val mockOrchestrator = mock(com.storm.safe.rock.service.modules.MainOrchestrator::class.java)
        `when`(mockService.mainOrchestrator).thenReturn(mockOrchestrator)

        val event = mock(AccessibilityEvent::class.java)
        `when`(event.eventType).thenReturn(2048)
        `when`(event.packageName).thenReturn("com.example.app")
        `when`(event.className).thenReturn("com.example.Activity")

        manager.onAccessibilityEvent(event)
        verify(mockOrchestrator).handleAccessibilityEvent(event)
    }

    // ════════════════════════════════════════════════════════════════
    // Release
    // ════════════════════════════════════════════════════════════════

    @Test
    fun `release does not throw`() {
        manager.enableAlipayDetection(5000L)
        manager.enableWechatDetection(5000L)
        manager.release()
        // Verify idempotent
        manager.release()
    }

    // ════════════════════════════════════════════════════════════════
    // Auth State
    // ════════════════════════════════════════════════════════════════

    @Test
    fun `isAuthStateRestored default is false`() {
        assertFalse(manager.isAuthStateRestored)
    }

    @Test
    fun `isAuthStateRestored can be set`() {
        manager.isAuthStateRestored = true
        assertTrue(manager.isAuthStateRestored)
    }
}
