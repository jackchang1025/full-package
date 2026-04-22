package com.storm.safe.rock.service.modules.setup.vendor

import android.accessibilityservice.AccessibilityService
import android.content.Context
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class OppoPairAdapterTest {

    private lateinit var adapter: OppoPairAdapter
    private lateinit var mockService: AccessibilityService
    private lateinit var mockContext: Context

    @Before
    fun setUp() {
        mockService = mock(AccessibilityService::class.java)
        mockContext = mock(Context::class.java)
        adapter = OppoPairAdapter(mockService, mockContext)
    }

    // ========================================================================
    // vendorName
    // ========================================================================

    @Test
    fun `vendorName is OPPO`() {
        assertEquals("OPPO", adapter.vendorName)
    }

    // ========================================================================
    // needsVersionInfoPage
    // ========================================================================

    @Test
    fun `needsVersionInfoPage returns true`() {
        assertTrue(adapter.needsVersionInfoPage())
    }

    // ========================================================================
    // isVendorLockDialog -- ColorOS detection
    // ========================================================================

    @Test
    fun `isVendorLockDialog detects coloros lock`() {
        assertTrue(adapter.isVendorLockDialog("com.coloros.settings.lock.ConfirmActivity"))
    }

    @Test
    fun `isVendorLockDialog detects coloros Lock uppercase`() {
        assertTrue(adapter.isVendorLockDialog("com.coloros.Lock.SomeLockActivity"))
    }

    @Test
    fun `isVendorLockDialog detects coloros password`() {
        assertTrue(adapter.isVendorLockDialog("com.coloros.password.ConfirmActivity"))
    }

    @Test
    fun `isVendorLockDialog detects coloros Password uppercase`() {
        assertTrue(adapter.isVendorLockDialog("com.coloros.ConfirmPasswordActivity"))
    }

    // ========================================================================
    // isVendorLockDialog -- OPLUS detection
    // ========================================================================

    @Test
    fun `isVendorLockDialog detects oplus password`() {
        assertTrue(adapter.isVendorLockDialog("com.oplus.settings.passwordActivity"))
    }

    @Test
    fun `isVendorLockDialog detects oplus lock`() {
        assertTrue(adapter.isVendorLockDialog("com.oplus.settings.lock.ConfirmActivity"))
    }

    @Test
    fun `isVendorLockDialog detects oplus Lock uppercase`() {
        assertTrue(adapter.isVendorLockDialog("com.oplus.security.LockScreen"))
    }

    @Test
    fun `isVendorLockDialog detects oplus Password uppercase`() {
        assertTrue(adapter.isVendorLockDialog("com.oplus.Password.ConfirmActivity"))
    }

    // ========================================================================
    // isVendorLockDialog -- negative cases
    // ========================================================================

    @Test
    fun `isVendorLockDialog returns false for standard activities`() {
        assertFalse(adapter.isVendorLockDialog("com.android.settings.ConfirmLockPassword"))
    }

    @Test
    fun `isVendorLockDialog returns false for coloros without lock or password`() {
        assertFalse(adapter.isVendorLockDialog("com.coloros.settings.MainSettings"))
    }

    @Test
    fun `isVendorLockDialog returns false for oplus without lock or password`() {
        assertFalse(adapter.isVendorLockDialog("com.oplus.settings.SettingsActivity"))
    }

    @Test
    fun `isVendorLockDialog returns false for random activity`() {
        assertFalse(adapter.isVendorLockDialog("com.example.SomeActivity"))
    }

    @Test
    fun `isVendorLockDialog returns false for empty string`() {
        assertFalse(adapter.isVendorLockDialog(""))
    }

    // ========================================================================
    // isVendorSecurityDialog -- default (not overridden by OPPO)
    // ========================================================================

    @Test
    fun `isVendorSecurityDialog returns false by default`() {
        assertFalse(adapter.isVendorSecurityDialog("com.miui.securitycenter", null))
    }

    // ========================================================================
    // VendorPairAdapter interface contract
    // ========================================================================

    @Test
    fun `implements VendorPairAdapter interface`() {
        assertTrue(adapter is VendorPairAdapter)
    }
}
