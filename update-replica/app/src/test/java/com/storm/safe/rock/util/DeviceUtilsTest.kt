package com.storm.safe.rock.util

import android.os.Build
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.util.ReflectionHelpers

@RunWith(RobolectricTestRunner::class)
class DeviceUtilsTest {

    private fun setBrand(brand: String) {
        ReflectionHelpers.setStaticField(Build::class.java, "BRAND", brand)
    }

    @Test
    fun `brand enum has 17 values`() {
        assertEquals(17, DeviceUtils.Brand.values().size)
    }

    @Test
    fun `brandGroup enum has 7 values`() {
        assertEquals(7, DeviceUtils.BrandGroup.values().size)
    }

    @Test
    fun `detect xiaomi brand`() {
        setBrand("Xiaomi")
        assertEquals(DeviceUtils.Brand.XIAOMI, DeviceUtils.detectBrand())
        assertEquals(DeviceUtils.BrandGroup.MIUI, DeviceUtils.detectBrandGroup())
    }

    @Test
    fun `detect redmi brand`() {
        setBrand("Redmi")
        assertEquals(DeviceUtils.Brand.REDMI, DeviceUtils.detectBrand())
        assertEquals(DeviceUtils.BrandGroup.MIUI, DeviceUtils.detectBrandGroup())
    }

    @Test
    fun `detect huawei brand`() {
        setBrand("HUAWEI")
        assertEquals(DeviceUtils.Brand.HUAWEI, DeviceUtils.detectBrand())
        assertEquals(DeviceUtils.BrandGroup.EMUI, DeviceUtils.detectBrandGroup())
    }

    @Test
    fun `detect honor brand`() {
        setBrand("HONOR")
        assertEquals(DeviceUtils.Brand.HONOR, DeviceUtils.detectBrand())
        assertEquals(DeviceUtils.BrandGroup.EMUI, DeviceUtils.detectBrandGroup())
    }

    @Test
    fun `detect oppo brand`() {
        setBrand("OPPO")
        assertEquals(DeviceUtils.Brand.OPPO, DeviceUtils.detectBrand())
        assertEquals(DeviceUtils.BrandGroup.COLOROS, DeviceUtils.detectBrandGroup())
    }

    @Test
    fun `detect oneplus brand`() {
        setBrand("OnePlus")
        assertEquals(DeviceUtils.Brand.ONEPLUS, DeviceUtils.detectBrand())
        assertEquals(DeviceUtils.BrandGroup.COLOROS, DeviceUtils.detectBrandGroup())
    }

    @Test
    fun `detect realme brand`() {
        setBrand("realme")
        assertEquals(DeviceUtils.Brand.REALME, DeviceUtils.detectBrand())
        assertEquals(DeviceUtils.BrandGroup.COLOROS, DeviceUtils.detectBrandGroup())
    }

    @Test
    fun `detect vivo brand`() {
        setBrand("vivo")
        assertEquals(DeviceUtils.Brand.VIVO, DeviceUtils.detectBrand())
        assertEquals(DeviceUtils.BrandGroup.ORIGINOS, DeviceUtils.detectBrandGroup())
    }

    @Test
    fun `detect iqoo brand`() {
        setBrand("iQOO")
        assertEquals(DeviceUtils.Brand.IQOO, DeviceUtils.detectBrand())
        assertEquals(DeviceUtils.BrandGroup.ORIGINOS, DeviceUtils.detectBrandGroup())
    }

    @Test
    fun `detect samsung brand`() {
        setBrand("samsung")
        assertEquals(DeviceUtils.Brand.SAMSUNG, DeviceUtils.detectBrand())
        assertEquals(DeviceUtils.BrandGroup.ONEUI, DeviceUtils.detectBrandGroup())
    }

    @Test
    fun `detect meizu brand`() {
        setBrand("Meizu")
        assertEquals(DeviceUtils.Brand.MEIZU, DeviceUtils.detectBrand())
        assertEquals(DeviceUtils.BrandGroup.FLYME, DeviceUtils.detectBrandGroup())
    }

    @Test
    fun `detect blackshark brand`() {
        setBrand("blackshark")
        assertEquals(DeviceUtils.Brand.BLACKSHARK, DeviceUtils.detectBrand())
        assertEquals(DeviceUtils.BrandGroup.MIUI, DeviceUtils.detectBrandGroup())
    }

    @Test
    fun `detect lenovo brand`() {
        setBrand("Lenovo")
        assertEquals(DeviceUtils.Brand.LENOVO, DeviceUtils.detectBrand())
        assertEquals(DeviceUtils.BrandGroup.AOSP, DeviceUtils.detectBrandGroup())
    }

    @Test
    fun `detect zte brand`() {
        setBrand("ZTE")
        assertEquals(DeviceUtils.Brand.ZTE, DeviceUtils.detectBrand())
        assertEquals(DeviceUtils.BrandGroup.AOSP, DeviceUtils.detectBrandGroup())
    }

    @Test
    fun `detect nubia brand`() {
        setBrand("nubia")
        assertEquals(DeviceUtils.Brand.NUBIA, DeviceUtils.detectBrand())
        assertEquals(DeviceUtils.BrandGroup.AOSP, DeviceUtils.detectBrandGroup())
    }

    @Test
    fun `detect google brand`() {
        setBrand("google")
        assertEquals(DeviceUtils.Brand.GOOGLE, DeviceUtils.detectBrand())
        assertEquals(DeviceUtils.BrandGroup.AOSP, DeviceUtils.detectBrandGroup())
    }

    @Test
    fun `detect unknown brand falls back to AOSP`() {
        setBrand("SomeUnknownBrand")
        assertEquals(DeviceUtils.Brand.UNKNOWN, DeviceUtils.detectBrand())
        assertEquals(DeviceUtils.BrandGroup.AOSP, DeviceUtils.detectBrandGroup())
    }

    @Test
    fun `isXiaomiFamily detects xiaomi redmi blackshark`() {
        setBrand("Xiaomi")
        assertTrue(DeviceUtils.isXiaomiFamily())
        setBrand("Redmi")
        assertTrue(DeviceUtils.isXiaomiFamily())
        setBrand("blackshark")
        assertTrue(DeviceUtils.isXiaomiFamily())
        setBrand("OPPO")
        assertFalse(DeviceUtils.isXiaomiFamily())
    }

    @Test
    fun `isHuaweiFamily detects huawei honor`() {
        setBrand("HUAWEI")
        assertTrue(DeviceUtils.isHuaweiFamily())
        setBrand("HONOR")
        assertTrue(DeviceUtils.isHuaweiFamily())
        setBrand("Xiaomi")
        assertFalse(DeviceUtils.isHuaweiFamily())
    }

    @Test
    fun `isOppoFamily detects oppo oneplus realme`() {
        setBrand("OPPO")
        assertTrue(DeviceUtils.isOppoFamily())
        setBrand("OnePlus")
        assertTrue(DeviceUtils.isOppoFamily())
        setBrand("realme")
        assertTrue(DeviceUtils.isOppoFamily())
        setBrand("vivo")
        assertFalse(DeviceUtils.isOppoFamily())
    }

    @Test
    fun `isVivoFamily detects vivo iqoo`() {
        setBrand("vivo")
        assertTrue(DeviceUtils.isVivoFamily())
        setBrand("iQOO")
        assertTrue(DeviceUtils.isVivoFamily())
        setBrand("samsung")
        assertFalse(DeviceUtils.isVivoFamily())
    }
}
