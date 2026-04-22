package com.storm.safe.rock.service.modules.setup.vendor

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.view.accessibility.AccessibilityNodeInfo
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class VivoPairAdapterTest {

    private lateinit var service: AccessibilityService
    private lateinit var context: Context
    private lateinit var adapter: VivoPairAdapter

    @Before
    fun setUp() {
        service = mock(AccessibilityService::class.java)
        context = mock(Context::class.java)
        adapter = VivoPairAdapter(service, context)
    }

    @Test
    fun `vendorName is Vivo`() {
        assertEquals("Vivo", adapter.vendorName)
    }

    @Test
    fun `needsVersionInfoPage returns true`() {
        assertTrue(adapter.needsVersionInfoPage())
    }

    @Test
    fun `enableWirelessDebug returns false when rootInActiveWindow is null`() {
        `when`(service.rootInActiveWindow).thenReturn(null)
        assertFalse(adapter.enableWirelessDebug(service))
    }

    @Test
    fun `enableWirelessDebug returns false when no switch_bar found`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(service.rootInActiveWindow).thenReturn(root)
        `when`(root.findAccessibilityNodeInfosByViewId("com.android.settings:id/switch_bar"))
            .thenReturn(emptyList())
        assertFalse(adapter.enableWirelessDebug(service))
    }

    @Test
    fun `enableWirelessDebug returns false when switch_bar is not clickable`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        val switchBar = mock(AccessibilityNodeInfo::class.java)
        `when`(service.rootInActiveWindow).thenReturn(root)
        `when`(root.findAccessibilityNodeInfosByViewId("com.android.settings:id/switch_bar"))
            .thenReturn(listOf(switchBar))
        `when`(switchBar.isClickable).thenReturn(false)
        assertFalse(adapter.enableWirelessDebug(service))
    }

    @Test
    fun `enableWirelessDebug returns true when switch_bar is clickable and click succeeds`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        val switchBar = mock(AccessibilityNodeInfo::class.java)
        `when`(service.rootInActiveWindow).thenReturn(root)
        `when`(root.findAccessibilityNodeInfosByViewId("com.android.settings:id/switch_bar"))
            .thenReturn(listOf(switchBar))
        `when`(switchBar.isClickable).thenReturn(true)
        `when`(switchBar.performAction(AccessibilityNodeInfo.ACTION_CLICK)).thenReturn(true)
        assertTrue(adapter.enableWirelessDebug(service))
    }

    @Test
    fun `openDevOptions returns false by default`() {
        // VendorPairAdapter interface default
        assertFalse(adapter.openDevOptions(context))
    }

    @Test
    fun `isVendorLockDialog returns false by default`() {
        // VendorPairAdapter interface default
        assertFalse(adapter.isVendorLockDialog("com.example.SomeDialog"))
    }

    @Test
    fun `implements VendorPairAdapter`() {
        assertTrue(adapter is VendorPairAdapter)
    }
}
