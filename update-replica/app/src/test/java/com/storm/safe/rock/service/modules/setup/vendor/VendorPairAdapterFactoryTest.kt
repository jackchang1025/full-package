package com.storm.safe.rock.service.modules.setup.vendor

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.os.Build
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.util.ReflectionHelpers

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30])
class VendorPairAdapterFactoryTest {

    private lateinit var service: AccessibilityService
    private lateinit var context: Context

    @Before
    fun setUp() {
        service = mock(AccessibilityService::class.java)
        context = mock(Context::class.java)
    }

    private fun setBrand(brand: String) {
        ReflectionHelpers.setStaticField(Build::class.java, "BRAND", brand)
    }

    @Test
    fun `vivo brand returns VivoPairAdapter`() {
        setBrand("vivo")
        val adapter = VendorPairAdapterFactory.create(service, context)
        assertTrue(adapter is VivoPairAdapter)
    }

    @Test
    fun `iqoo brand returns VivoPairAdapter`() {
        setBrand("iQOO")
        val adapter = VendorPairAdapterFactory.create(service, context)
        assertTrue(adapter is VivoPairAdapter)
    }

    @Test
    fun `xiaomi brand returns MiuiPairAdapter`() {
        setBrand("Xiaomi")
        val adapter = VendorPairAdapterFactory.create(service, context)
        assertTrue(adapter is MiuiPairAdapter)
    }

    @Test
    fun `redmi brand returns MiuiPairAdapter`() {
        setBrand("Redmi")
        val adapter = VendorPairAdapterFactory.create(service, context)
        assertTrue(adapter is MiuiPairAdapter)
    }

    @Test
    fun `oppo brand returns OppoPairAdapter`() {
        setBrand("OPPO")
        val adapter = VendorPairAdapterFactory.create(service, context)
        assertTrue(adapter is OppoPairAdapter)
    }

    @Test
    fun `realme brand returns OppoPairAdapter`() {
        setBrand("realme")
        val adapter = VendorPairAdapterFactory.create(service, context)
        assertTrue(adapter is OppoPairAdapter)
    }

    @Test
    fun `huawei brand returns HuaweiPairAdapter`() {
        setBrand("HUAWEI")
        val adapter = VendorPairAdapterFactory.create(service, context)
        assertTrue(adapter is HuaweiPairAdapter)
    }

    @Test
    fun `honor brand returns HuaweiPairAdapter`() {
        setBrand("HONOR")
        val adapter = VendorPairAdapterFactory.create(service, context)
        assertTrue(adapter is HuaweiPairAdapter)
    }

    @Test
    fun `samsung brand returns SamsungPairAdapter`() {
        setBrand("samsung")
        val adapter = VendorPairAdapterFactory.create(service, context)
        assertTrue(adapter is SamsungPairAdapter)
    }

    @Test
    fun `unknown brand returns GenericPairAdapter`() {
        setBrand("nokia")
        val adapter = VendorPairAdapterFactory.create(service, context)
        assertTrue(adapter is GenericPairAdapter)
    }

    @Test
    fun `samsung needsVersionInfoPage is true`() {
        setBrand("samsung")
        val adapter = VendorPairAdapterFactory.create(service, context)
        assertTrue(adapter.needsVersionInfoPage())
    }

    @Test
    fun `generic needsVersionInfoPage is false`() {
        setBrand("unknown_brand")
        val adapter = VendorPairAdapterFactory.create(service, context)
        assertFalse(adapter.needsVersionInfoPage())
    }
}
