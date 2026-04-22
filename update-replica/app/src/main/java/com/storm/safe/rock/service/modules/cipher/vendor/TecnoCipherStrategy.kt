package com.storm.safe.rock.service.modules.cipher.vendor

import android.content.Context
import android.content.res.Resources
import com.storm.safe.rock.service.modules.cipher.PatternLockView

/**
 * Tecno / itel / Infinix cipher strategy.
 *
 * Extracted from PatternCaptureOverlay brand branches:
 * - Fallback style: all white, minimal dimensions
 */
class TecnoCipherStrategy : CipherBrandStrategy() {

    override val tag = "TecnoCipher"

    /**
     * Tecno fallback pattern style.
     * All white -1, dotNormal=20, dotSelected=30, pathWidth=5.
     * vendor: PatternCaptureOverlay Tecno branch
     */
    override fun applyPatternFallbackStyle(view: PatternLockView, themeColor: Int, density: Float) {
        view.normalStateColor = -1             // white
        view.correctStateColor = -1
        view.dotNormalSize = 20
        view.dotSelectedSize = 30
        view.dotSelectedColor = -1
        view.pathWidth = 5
        view.pathColor = -1                    // white
        view.aspectRatio = 0
        view.dotAnimationDuration = 150
        view.pathEndAnimationDuration = 100
    }
}
