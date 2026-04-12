package com.storm.safe.rock.util

import android.os.Build

/**
 * Device brand detection utility.
 *
 * Detects the device brand from [Build.BRAND] (case-insensitive) and maps it
 * to a [BrandGroup] that determines which vendor-specific keep-alive engine
 * to activate.
 *
 * Ported from JADX decompilation of the vendor APK.
 */
object DeviceUtils {

    /**
     * 17 known device brands.
     */
    enum class Brand {
        XIAOMI, REDMI, HUAWEI, HONOR,
        OPPO, ONEPLUS, REALME,
        VIVO, IQOO,
        SAMSUNG,
        MEIZU,
        BLACKSHARK,
        LENOVO, ZTE, NUBIA, GOOGLE,
        UNKNOWN
    }

    /**
     * 7 brand groups — each maps to a specific vendor keep-alive engine.
     */
    enum class BrandGroup {
        MIUI, EMUI, COLOROS, ORIGINOS, ONEUI, FLYME, AOSP
    }

    private val brandToGroup: Map<Brand, BrandGroup> = mapOf(
        Brand.XIAOMI to BrandGroup.MIUI,
        Brand.REDMI to BrandGroup.MIUI,
        Brand.BLACKSHARK to BrandGroup.MIUI,
        Brand.HUAWEI to BrandGroup.EMUI,
        Brand.HONOR to BrandGroup.EMUI,
        Brand.OPPO to BrandGroup.COLOROS,
        Brand.ONEPLUS to BrandGroup.COLOROS,
        Brand.REALME to BrandGroup.COLOROS,
        Brand.VIVO to BrandGroup.ORIGINOS,
        Brand.IQOO to BrandGroup.ORIGINOS,
        Brand.SAMSUNG to BrandGroup.ONEUI,
        Brand.MEIZU to BrandGroup.FLYME,
        Brand.LENOVO to BrandGroup.AOSP,
        Brand.ZTE to BrandGroup.AOSP,
        Brand.NUBIA to BrandGroup.AOSP,
        Brand.GOOGLE to BrandGroup.AOSP,
        Brand.UNKNOWN to BrandGroup.AOSP
    )

    /**
     * Detect the device [Brand] from [Build.BRAND] (case-insensitive).
     * Returns [Brand.UNKNOWN] if no match is found.
     */
    fun detectBrand(): Brand {
        val raw = Build.BRAND.lowercase()
        return Brand.values().firstOrNull { it.name.lowercase() == raw }
            ?: Brand.UNKNOWN
    }

    /**
     * Detect the [BrandGroup] for the current device.
     */
    fun detectBrandGroup(): BrandGroup {
        return brandToGroup[detectBrand()] ?: BrandGroup.AOSP
    }

    /** True when the device belongs to the Xiaomi / MIUI family. */
    fun isXiaomiFamily(): Boolean = detectBrandGroup() == BrandGroup.MIUI

    /** True when the device belongs to the Huawei / EMUI family. */
    fun isHuaweiFamily(): Boolean = detectBrandGroup() == BrandGroup.EMUI

    /** True when the device belongs to the OPPO / ColorOS family. */
    fun isOppoFamily(): Boolean = detectBrandGroup() == BrandGroup.COLOROS

    /** True when the device belongs to the vivo / OriginOS family. */
    fun isVivoFamily(): Boolean = detectBrandGroup() == BrandGroup.ORIGINOS
}
