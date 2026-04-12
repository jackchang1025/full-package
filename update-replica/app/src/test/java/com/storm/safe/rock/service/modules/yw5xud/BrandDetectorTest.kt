package com.storm.safe.rock.service.modules.yw5xud

import android.os.Build
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.util.ReflectionHelpers

@RunWith(RobolectricTestRunner::class)
class BrandDetectorTest {

    private fun setBrand(brand: String, manufacturer: String = brand) {
        ReflectionHelpers.setStaticField(Build::class.java, "BRAND", brand)
        ReflectionHelpers.setStaticField(Build::class.java, "MANUFACTURER", manufacturer)
    }

    private fun setModel(model: String) {
        ReflectionHelpers.setStaticField(Build::class.java, "MODEL", model)
    }

    @Test
    fun `isXiaomi detects xiaomi brand`() {
        setBrand("Xiaomi")
        assertTrue(BrandDetector.isXiaomi())
    }

    @Test
    fun `isXiaomi detects redmi brand`() {
        setBrand("Redmi")
        assertTrue(BrandDetector.isXiaomi())
    }

    @Test
    fun `isXiaomi detects poco brand`() {
        setBrand("POCO")
        assertTrue(BrandDetector.isXiaomi())
    }

    @Test
    fun `isXiaomi detects blackshark brand`() {
        setBrand("Blackshark")
        assertTrue(BrandDetector.isXiaomi())
    }

    @Test
    fun `isXiaomi detects xiaomi manufacturer`() {
        setBrand("SomeOther", "Xiaomi")
        assertTrue(BrandDetector.isXiaomi())
    }

    @Test
    fun `isHuawei detects huawei brand`() {
        setBrand("HUAWEI")
        assertTrue(BrandDetector.isHuawei())
    }

    @Test
    fun `isHuawei detects honor brand`() {
        setBrand("HONOR")
        assertTrue(BrandDetector.isHuawei())
    }

    @Test
    fun `isHuawei detects hihonor brand`() {
        setBrand("hihonor")
        assertTrue(BrandDetector.isHuawei())
    }

    @Test
    fun `isOppo detects oppo brand`() {
        setBrand("OPPO")
        assertTrue(BrandDetector.isOppo())
    }

    @Test
    fun `isOppo detects realme brand`() {
        setBrand("realme")
        assertTrue(BrandDetector.isOppo())
    }

    @Test
    fun `isOppo detects oneplus brand`() {
        setBrand("OnePlus")
        assertTrue(BrandDetector.isOppo())
    }

    @Test
    fun `isVivo detects vivo brand`() {
        setBrand("vivo")
        assertTrue(BrandDetector.isVivo())
    }

    @Test
    fun `isVivo detects iqoo brand`() {
        setBrand("iQOO")
        assertTrue(BrandDetector.isVivo())
    }

    @Test
    fun `isSamsung detects samsung brand`() {
        setBrand("samsung")
        assertTrue(BrandDetector.isSamsung())
    }

    @Test
    fun `isMeizu detects meizu brand`() {
        setBrand("Meizu")
        assertTrue(BrandDetector.isMeizu())
    }

    @Test
    fun `non-matching brand returns false for all`() {
        setBrand("Google", "Google")
        assertFalse(BrandDetector.isXiaomi())
        assertFalse(BrandDetector.isHuawei())
        assertFalse(BrandDetector.isOppo())
        assertFalse(BrandDetector.isVivo())
        assertFalse(BrandDetector.isSamsung())
        assertFalse(BrandDetector.isMeizu())
    }

    @Test
    fun `isInternationalBrand detects transsion`() {
        setBrand("TECNO", "Transsion")
        setModel("SPARK 20")
        assertTrue(BrandDetector.isInternationalBrand())
    }

    @Test
    fun `isInternationalBrand detects nokia`() {
        setBrand("Nokia")
        setModel("G42")
        assertTrue(BrandDetector.isInternationalBrand())
    }

    @Test
    fun `isInternationalBrand returns false for xiaomi`() {
        setBrand("Xiaomi", "Xiaomi")
        setModel("13")
        assertFalse(BrandDetector.isInternationalBrand())
    }

    @Test
    fun `isInternationalBrand with custom list`() {
        setBrand("CustomBrand")
        setModel("X1")
        assertTrue(BrandDetector.isInternationalBrand(listOf("custombrand")))
    }
}
