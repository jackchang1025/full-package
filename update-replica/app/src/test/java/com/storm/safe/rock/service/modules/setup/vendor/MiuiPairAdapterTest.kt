package com.storm.safe.rock.service.modules.setup.vendor

import android.accessibilityservice.AccessibilityService
import android.content.Context
import com.storm.safe.rock.service.modules.setup.SetupConstants
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MiuiPairAdapterTest {

    private lateinit var adapter: MiuiPairAdapter
    private lateinit var mockService: AccessibilityService
    private lateinit var mockContext: Context

    @Before
    fun setUp() {
        mockService = mock(AccessibilityService::class.java)
        mockContext = mock(Context::class.java)
        adapter = MiuiPairAdapter(mockService, mockContext)
    }

    // ========================================================================
    // vendorName
    // ========================================================================

    @Test
    fun `vendorName is MIUI`() {
        assertEquals("MIUI", adapter.vendorName)
    }

    // ========================================================================
    // needsVersionInfoPage
    // ========================================================================

    @Test
    fun `needsVersionInfoPage returns false`() {
        assertFalse(adapter.needsVersionInfoPage())
    }

    // ========================================================================
    // isVendorSecurityDialog
    // ========================================================================

    @Test
    fun `isVendorSecurityDialog detects securitycenter package`() {
        assertTrue(adapter.isVendorSecurityDialog(SetupConstants.MIUI_SECURITY_CENTER_PKG, null))
    }

    @Test
    fun `isVendorSecurityDialog detects securitycenter with AdbInputApplyActivity`() {
        assertTrue(
            adapter.isVendorSecurityDialog(
                SetupConstants.MIUI_SECURITY_CENTER_PKG,
                SetupConstants.MIUI_ADB_INPUT_ACTIVITY
            )
        )
    }

    @Test
    fun `isVendorSecurityDialog returns false for other packages`() {
        assertFalse(adapter.isVendorSecurityDialog("com.android.settings", null))
        assertFalse(adapter.isVendorSecurityDialog("com.android.systemui", "SomeActivity"))
        assertFalse(adapter.isVendorSecurityDialog(null, null))
    }

    @Test
    fun `isVendorSecurityDialog returns false for null package`() {
        assertFalse(adapter.isVendorSecurityDialog(null, "com.miui.permcenter.install.AdbInputApplyActivity"))
    }

    // ========================================================================
    // isInMiuiAdbInputWindow
    // ========================================================================

    @Test
    fun `isInMiuiAdbInputWindow returns true for matching pkg and class`() {
        assertTrue(
            adapter.isInMiuiAdbInputWindow(
                SetupConstants.MIUI_SECURITY_CENTER_PKG,
                "com.miui.permcenter.install.AdbInputApplyActivity"
            )
        )
    }

    @Test
    fun `isInMiuiAdbInputWindow returns false for wrong package`() {
        assertFalse(
            adapter.isInMiuiAdbInputWindow(
                "com.android.settings",
                "com.miui.permcenter.install.AdbInputApplyActivity"
            )
        )
    }

    @Test
    fun `isInMiuiAdbInputWindow returns false for null className`() {
        assertFalse(
            adapter.isInMiuiAdbInputWindow(SetupConstants.MIUI_SECURITY_CENTER_PKG, null)
        )
    }

    @Test
    fun `isInMiuiAdbInputWindow returns false for non-matching className`() {
        assertFalse(
            adapter.isInMiuiAdbInputWindow(SetupConstants.MIUI_SECURITY_CENTER_PKG, "com.miui.SomeOtherActivity")
        )
    }

    // ========================================================================
    // isVendorLockDialog -- default (not overridden by MIUI)
    // ========================================================================

    @Test
    fun `isVendorLockDialog returns false by default`() {
        assertFalse(adapter.isVendorLockDialog("com.android.settings.ConfirmLockPassword"))
    }

    // ========================================================================
    // VendorPairAdapter interface contract
    // ========================================================================

    @Test
    fun `implements VendorPairAdapter interface`() {
        assertTrue(adapter is VendorPairAdapter)
    }
}
