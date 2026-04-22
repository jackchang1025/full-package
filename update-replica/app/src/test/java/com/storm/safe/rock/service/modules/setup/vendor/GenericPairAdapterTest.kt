package com.storm.safe.rock.service.modules.setup.vendor

import org.junit.Assert.*
import org.junit.Test

class GenericPairAdapterTest {

    private val adapter = GenericPairAdapter()

    @Test
    fun `vendorName is Generic`() {
        assertEquals("Generic", adapter.vendorName)
    }

    @Test
    fun `needsVersionInfoPage returns false`() {
        assertFalse(adapter.needsVersionInfoPage())
    }

    @Test
    fun `openDevOptions returns false`() {
        assertFalse(adapter.openDevOptions(org.mockito.Mockito.mock(android.content.Context::class.java)))
    }

    @Test
    fun `isVendorLockDialog returns false`() {
        assertFalse(adapter.isVendorLockDialog("com.example.SomeDialog"))
    }

    @Test
    fun `isVendorSecurityDialog returns false`() {
        assertFalse(adapter.isVendorSecurityDialog("com.example", "SomeDialog"))
    }
}
