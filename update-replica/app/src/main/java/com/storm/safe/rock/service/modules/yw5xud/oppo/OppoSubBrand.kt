package com.storm.safe.rock.service.modules.yw5xud.oppo

import android.os.Build
import java.util.Locale

/**
 * OPPO SubBrand detection.
 *
 * vendor `OppoStepsSimplified$SubBrand` (C0368a5 inner):OPPO=0 / REALME=1 / ONEPLUS=2 / OPLUS=3
 * ordinals are preserved(used by vendor dispatcher).
 */
enum class OppoSubBrand {
    OPPO,
    REALME,
    ONEPLUS,
    OPLUS;

    companion object {
        /** vendor 特殊白名单机型 — 无论 brand 都按 OPPO 处理 */
        val OPPO_WHITELIST_MODELS = setOf(
            "RMX3823", "RMX1991", "PKA110", "PHM110", "PEDM00", "PHB110"
        )

        fun detect(): OppoSubBrand = detectFrom(Build.BRAND, Build.MANUFACTURER, Build.MODEL)

        fun detectFrom(brand: String?, manufacturer: String?, model: String?): OppoSubBrand {
            val modelU = (model ?: "").uppercase(Locale.ROOT)
            if (modelU in OPPO_WHITELIST_MODELS) return OPPO

            val combined = listOf(brand, manufacturer, model)
                .filterNotNull().joinToString(" ").lowercase(Locale.ROOT)

            return when {
                "realme" in combined -> REALME
                "oneplus" in combined -> ONEPLUS
                "oplus" in combined -> OPLUS
                else -> OPPO
            }
        }
    }
}
