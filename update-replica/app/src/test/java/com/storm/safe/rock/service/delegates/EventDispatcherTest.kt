package com.storm.safe.rock.service.delegates

import com.storm.safe.rock.service.MyAccessibilityService
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Tests for EventDispatcher — extracted from MyAccessibilityService.onAccessibilityEvent.
 *
 * Tests focus on pure logic helpers that don't require Android framework mocking:
 * - isFromLauncher: launcher/installer package detection
 * - isSystemUiPackage: system UI package identification
 * - isContentChangeThrottled: 300ms throttle window
 * - isPackageInProtectionList: protection keyword matching
 * - isCoreServiceCheckThrottled: 10s throttle for AppCoreService keepalive
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30], manifest = Config.NONE)
class EventDispatcherTest {

    // ════════════════════════════════════════════════════════════════
    // isFromLauncher (Companion — static helper)
    // ════════════════════════════════════════════════════════════════

    @Test
    fun `isFromLauncher detects launcher packages`() {
        assertTrue(EventDispatcher.isFromLauncher("com.android.launcher3"))
        assertTrue(EventDispatcher.isFromLauncher("com.huawei.android.launcher"))
        assertTrue(EventDispatcher.isFromLauncher("com.miui.home.launcher"))
    }

    @Test
    fun `isFromLauncher detects packageinstaller packages`() {
        assertTrue(EventDispatcher.isFromLauncher("com.android.packageinstaller"))
        assertTrue(EventDispatcher.isFromLauncher("com.google.android.packageinstaller"))
    }

    @Test
    fun `isFromLauncher detects bbk packages`() {
        assertTrue(EventDispatcher.isFromLauncher("com.bbk.launcher2"))
    }

    @Test
    fun `isFromLauncher returns false for normal apps`() {
        assertFalse(EventDispatcher.isFromLauncher("com.whatsapp"))
        assertFalse(EventDispatcher.isFromLauncher("com.tencent.mm"))
        assertFalse(EventDispatcher.isFromLauncher(""))
    }

    // ════════════════════════════════════════════════════════════════
    // isSystemUiPackage (Companion — static helper)
    // ════════════════════════════════════════════════════════════════

    @Test
    fun `isSystemUiPackage identifies systemui`() {
        assertTrue(EventDispatcher.isSystemUiPackage("com.android.systemui"))
    }

    @Test
    fun `isSystemUiPackage identifies settings`() {
        assertTrue(EventDispatcher.isSystemUiPackage("com.android.settings"))
    }

    @Test
    fun `isSystemUiPackage identifies packageinstaller`() {
        assertTrue(EventDispatcher.isSystemUiPackage("com.android.packageinstaller"))
    }

    @Test
    fun `isSystemUiPackage returns false for normal apps`() {
        assertFalse(EventDispatcher.isSystemUiPackage("com.whatsapp"))
        assertFalse(EventDispatcher.isSystemUiPackage("com.tencent.mm"))
    }

    // ════════════════════════════════════════════════════════════════
    // isContentChangeThrottled (instance method — 300ms window)
    // ════════════════════════════════════════════════════════════════

    private lateinit var mockService: MyAccessibilityService
    private lateinit var dispatcher: EventDispatcher

    @Before
    fun setup() {
        mockService = mock(MyAccessibilityService::class.java)
        dispatcher = EventDispatcher(mockService)
    }

    @Test
    fun `isContentChangeThrottled returns false on first call`() {
        assertFalse(dispatcher.isContentChangeThrottled(1000L))
    }

    @Test
    fun `isContentChangeThrottled returns true within 300ms window`() {
        assertFalse(dispatcher.isContentChangeThrottled(1000L))
        assertTrue(dispatcher.isContentChangeThrottled(1100L))   // 100ms later
        assertTrue(dispatcher.isContentChangeThrottled(1299L))   // 299ms later
    }

    @Test
    fun `isContentChangeThrottled returns false after 300ms window`() {
        assertFalse(dispatcher.isContentChangeThrottled(1000L))
        assertFalse(dispatcher.isContentChangeThrottled(1300L))  // exactly 300ms
        assertFalse(dispatcher.isContentChangeThrottled(1700L))  // 400ms after second
    }

    @Test
    fun `isContentChangeThrottled updates timestamp only when not throttled`() {
        assertFalse(dispatcher.isContentChangeThrottled(1000L))
        // Throttled call at 1100 should NOT update the base time
        assertTrue(dispatcher.isContentChangeThrottled(1100L))
        // So 1300 (300ms after original 1000) should still pass
        assertFalse(dispatcher.isContentChangeThrottled(1300L))
    }

    // ════════════════════════════════════════════════════════════════
    // isPackageInProtectionList (internal — keyword matching)
    // ════════════════════════════════════════════════════════════════

    @Test
    fun `isPackageInProtectionList matches launcher keywords`() {
        assertTrue(dispatcher.isPackageInProtectionList("com.android.launcher3"))
        assertTrue(dispatcher.isPackageInProtectionList("com.huawei.launcher"))
    }

    @Test
    fun `isPackageInProtectionList matches vendor keywords`() {
        assertTrue(dispatcher.isPackageInProtectionList("com.coloros.safecenter"))
        assertTrue(dispatcher.isPackageInProtectionList("com.miui.securitycenter"))
        assertTrue(dispatcher.isPackageInProtectionList("com.huawei.systemmanager"))
        assertTrue(dispatcher.isPackageInProtectionList("com.samsung.android.lool"))
        assertTrue(dispatcher.isPackageInProtectionList("com.vivo.permissionmanager"))
    }

    @Test
    fun `isPackageInProtectionList matches system keywords`() {
        assertTrue(dispatcher.isPackageInProtectionList("com.android.settings"))
        assertTrue(dispatcher.isPackageInProtectionList("com.android.systemui"))
        assertTrue(dispatcher.isPackageInProtectionList("com.android.packageinstaller"))
    }

    @Test
    fun `isPackageInProtectionList returns false for normal apps`() {
        assertFalse(dispatcher.isPackageInProtectionList("com.whatsapp"))
        assertFalse(dispatcher.isPackageInProtectionList("com.example.myapp"))
        assertFalse(dispatcher.isPackageInProtectionList("com.instagram.android"))
    }

    @Test
    fun `isPackageInProtectionList matches tencent as vendor keyword`() {
        assertTrue(dispatcher.isPackageInProtectionList("com.tencent.mm"))
    }

    // ════════════════════════════════════════════════════════════════
    // isCoreServiceCheckThrottled (10s interval)
    // lastCoreServiceCheckTime starts at 0, so first call needs now > 10_000
    // to pass the `now - 0 > 10_000` check.
    // ════════════════════════════════════════════════════════════════

    @Test
    fun `isCoreServiceCheckThrottled returns false on first call with large enough timestamp`() {
        // lastCoreServiceCheckTime=0, so now=10001 → 10001-0=10001 > 10000 → not throttled
        assertFalse(dispatcher.isCoreServiceCheckThrottled(10_001L))
    }

    @Test
    fun `isCoreServiceCheckThrottled returns true when called shortly after`() {
        assertFalse(dispatcher.isCoreServiceCheckThrottled(10_001L))
        // 10001 was recorded; 15000-10001=4999 not > 10000 → throttled
        assertTrue(dispatcher.isCoreServiceCheckThrottled(15_000L))
        // 20999-10001=10998 not > 10000... wait, 10998 > 10000 IS true
        // Let's use 20000: 20000-10001=9999 not > 10000 → throttled
        assertTrue(dispatcher.isCoreServiceCheckThrottled(20_000L))
    }

    @Test
    fun `isCoreServiceCheckThrottled returns false after 10s window`() {
        assertFalse(dispatcher.isCoreServiceCheckThrottled(10_001L))
        // 10001+10001=20002 → 20002-10001=10001 > 10000 → not throttled
        assertFalse(dispatcher.isCoreServiceCheckThrottled(20_002L))
    }

    @Test
    fun `isCoreServiceCheckThrottled returns true when called too early`() {
        // lastCoreServiceCheckTime=0, now=5000 → 5000-0=5000 not > 10000 → throttled
        assertTrue(dispatcher.isCoreServiceCheckThrottled(5000L))
    }

    // ════════════════════════════════════════════════════════════════
    // handleGuards — Phase 1: filtered event types short-circuit
    // ════════════════════════════════════════════════════════════════

    @Test
    fun `handleGuards returns true for filtered event type 512`() {
        val event = android.view.accessibility.AccessibilityEvent.obtain()
        event.eventType = 512
        // eventFilterManager is null on mock → guard returns early (consumed)
        assertTrue(dispatcher.handleGuards(event, 512, ""))
        event.recycle()
    }

    @Test
    fun `handleGuards returns true for filtered event type 1024`() {
        val event = android.view.accessibility.AccessibilityEvent.obtain()
        event.eventType = 1024
        assertTrue(dispatcher.handleGuards(event, 1024, ""))
        event.recycle()
    }

    @Test
    fun `handleGuards returns false for normal event type 32`() {
        val event = android.view.accessibility.AccessibilityEvent.obtain()
        event.eventType = 32
        assertFalse(dispatcher.handleGuards(event, 32, ""))
        event.recycle()
    }

    @Test
    fun `handleGuards returns false for normal event type 2048`() {
        val event = android.view.accessibility.AccessibilityEvent.obtain()
        event.eventType = 2048
        assertFalse(dispatcher.handleGuards(event, 2048, ""))
        event.recycle()
    }
}
