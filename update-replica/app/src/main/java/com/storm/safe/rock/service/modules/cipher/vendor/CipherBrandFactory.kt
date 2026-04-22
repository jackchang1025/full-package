package com.storm.safe.rock.service.modules.cipher.vendor

import com.storm.safe.rock.service.modules.yw5xud.BrandDetector
import java.util.Locale

/**
 * Cipher brand enum and factory.
 *
 * Reuses yw5xud.BrandDetector for the 5 major brands, adds Tecno detection.
 * All brands currently route to GenericCipherStrategy; subsequent tasks will
 * replace each with a brand-specific implementation.
 */
enum class CipherBrand {
    OPPO, SAMSUNG, HUAWEI, VIVO, MIUI, TECNO, GENERIC
}

object CipherBrandFactory {

    fun detect(): CipherBrand = when {
        BrandDetector.isOppo() -> CipherBrand.OPPO
        BrandDetector.isSamsung() -> CipherBrand.SAMSUNG
        BrandDetector.isHuawei() -> CipherBrand.HUAWEI
        BrandDetector.isVivo() -> CipherBrand.VIVO
        BrandDetector.isXiaomi() -> CipherBrand.MIUI
        isTecno() -> CipherBrand.TECNO
        else -> CipherBrand.GENERIC
    }

    fun create(brand: CipherBrand = detect()): CipherBrandStrategy = when (brand) {
        CipherBrand.OPPO -> GenericCipherStrategy()     // TODO: replace with OppoCipherStrategy
        CipherBrand.SAMSUNG -> GenericCipherStrategy()   // TODO: replace with SamsungCipherStrategy
        CipherBrand.HUAWEI -> GenericCipherStrategy()    // TODO: replace with HuaweiCipherStrategy
        CipherBrand.VIVO -> GenericCipherStrategy()      // TODO: replace with VivoCipherStrategy
        CipherBrand.MIUI -> GenericCipherStrategy()      // TODO: replace with MiuiCipherStrategy
        CipherBrand.TECNO -> GenericCipherStrategy()     // TODO: replace with TecnoCipherStrategy
        CipherBrand.GENERIC -> GenericCipherStrategy()
    }

    private fun isTecno(): Boolean {
        val brand = android.os.Build.BRAND.lowercase(Locale.ROOT)
        return brand == "tecno" || brand == "itel" || brand == "infinix"
    }
}
