package com.storm.safe.rock.service.modules.yw5xud

import android.os.Build
import java.util.Locale

/**
 * Brand detection matching vendor C0372a9 static methods:
 * - c2() = isXiaomi
 * - c5() = isVivo
 * - c4() = isSamsung
 * - c1() = isMeizu
 * - c0() = isHuawei (checked in a separate method)
 * - c3() = isInternationalBrand
 */
object BrandDetector {

    fun isXiaomi(): Boolean {
        val brand = Build.BRAND.lowercase(Locale.ROOT)
        val manufacturer = Build.MANUFACTURER.lowercase(Locale.ROOT)
        return brand.contains("xiaomi") || brand.contains("redmi") ||
               brand.contains("poco") || brand.contains("blackshark") ||
               manufacturer.contains("xiaomi")
    }

    fun isHuawei(): Boolean {
        val brand = Build.BRAND.lowercase(Locale.ROOT)
        val manufacturer = Build.MANUFACTURER.lowercase(Locale.ROOT)
        return brand.contains("huawei") || brand.contains("honor") ||
               brand.contains("hihonor") ||
               manufacturer.contains("huawei") || manufacturer.contains("honor")
    }

    fun isOppo(): Boolean {
        val brand = Build.BRAND.lowercase(Locale.ROOT)
        val manufacturer = Build.MANUFACTURER.lowercase(Locale.ROOT)
        return brand.contains("oppo") || brand.contains("realme") ||
               brand.contains("oneplus") ||
               manufacturer.contains("oppo") || manufacturer.contains("realme")
    }

    fun isVivo(): Boolean {
        val brand = Build.BRAND.lowercase(Locale.ROOT)
        val manufacturer = Build.MANUFACTURER.lowercase(Locale.ROOT)
        return brand.contains("vivo") || brand.contains("iqoo") ||
               manufacturer.contains("vivo") || manufacturer.contains("iqoo")
    }

    fun isSamsung(): Boolean {
        val brand = Build.BRAND.lowercase(Locale.ROOT)
        val manufacturer = Build.MANUFACTURER.lowercase(Locale.ROOT)
        return brand.contains("samsung") || manufacturer.contains("samsung")
    }

    fun isMeizu(): Boolean {
        val brand = Build.BRAND.lowercase(Locale.ROOT)
        val manufacturer = Build.MANUFACTURER.lowercase(Locale.ROOT)
        return brand.contains("meizu") || manufacturer.contains("meizu")
    }

    /**
     * Check against international brand list. Vendor c3() checks a static list
     * from pl0.f59305a0 against manufacturer/brand/model.
     */
    fun isInternationalBrand(internationalBrands: List<String> = DEFAULT_INTERNATIONAL_BRANDS): Boolean {
        val manufacturer = Build.MANUFACTURER.lowercase(Locale.ROOT)
        val brand = Build.BRAND.lowercase(Locale.ROOT)
        val model = Build.MODEL.lowercase(Locale.ROOT)
        return internationalBrands.any { target ->
            manufacturer.contains(target) || brand.contains(target) || model.contains(target)
        }
    }

    /** International brand identifiers from vendor pl0.f59305a0 */
    private val DEFAULT_INTERNATIONAL_BRANDS = listOf(
        "transsion", "tecno", "infinix", "itel",
        "nokia", "motorola", "lenovo",
        "zte", "nubia", "google", "pixel",
        "nothing", "fairphone", "sharp", "sony"
    )
}
