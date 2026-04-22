package com.storm.safe.rock.service.modules.overlay

import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView

/**
 * Dual-mode progress animation for the overlay.
 *
 * Reverse-engineered from JADX: RunnableC0707j6.java (case 0, lines 48-88).
 * Mode B (startFromMax=false): 0→80% in 30s, then 80→95% every 3s.
 * Mode A (startFromMax=true): 80→100% in 60s.
 */
class OverlayProgressAnimator(
    private val progressBarView: View?,
    private val tipTextView: TextView?,
    private val containerWidthProvider: () -> Int,
    private val tips: List<String>,
    private val startFromMax: Boolean
) {
    private var handler: Handler? = null
    private var startTime: Long = 0L
    private var lastProgress: Int = -1
    private var running = false

    companion object {
        fun calcProgress(elapsedMs: Long, startFromMax: Boolean): Int {
            return if (startFromMax) {
                ((elapsedMs / 60000.0) * 20 + 80).toInt().coerceIn(80, 100)
            } else if (elapsedMs < 30000) {
                ((elapsedMs / 30000.0) * 80).toInt().coerceIn(0, 80)
            } else {
                (((elapsedMs - 30000) / 3000).toInt() + 80).coerceIn(80, 95)
            }
        }

        fun calcInterval(elapsedMs: Long, startFromMax: Boolean): Long {
            return if (startFromMax) {
                1000L
            } else if (elapsedMs < 30000) {
                1000L
            } else {
                3000L
            }
        }

        fun calcTipIndex(progress: Int, tipCount: Int): Int {
            if (tipCount <= 0) return 0
            return ((progress / 100.0) * tipCount).toInt().coerceIn(0, tipCount - 1)
        }
    }

    private val runnable = object : Runnable {
        override fun run() {
            if (!running) return
            val elapsed = System.currentTimeMillis() - startTime
            val progress = calcProgress(elapsed, startFromMax)
            if (progress != lastProgress) {
                lastProgress = progress
                updateProgressBar(progress)
                updateTipText(progress)
            }
            val maxProgress = if (startFromMax) 100 else 95
            if (progress < maxProgress && handler != null) {
                handler?.postDelayed(this, calcInterval(elapsed, startFromMax))
            }
        }
    }

    fun start() {
        stop()
        startTime = System.currentTimeMillis()
        lastProgress = -1
        running = true
        handler = Handler(Looper.getMainLooper())
        handler?.post(runnable)
    }

    fun stop() {
        running = false
        handler?.removeCallbacks(runnable)
        handler = null
    }

    fun forceProgress(percent: Int, message: String? = null) {
        lastProgress = percent
        updateProgressBar(percent)
        if (message != null) {
            tipTextView?.text = message
        } else {
            updateTipText(percent)
        }
    }

    private fun updateProgressBar(percent: Int) {
        val bar = progressBarView ?: return
        val containerWidth = containerWidthProvider()
        if (containerWidth <= 0) return
        val newWidth = (containerWidth * percent / 100.0f).toInt()
        bar.layoutParams = FrameLayout.LayoutParams(newWidth, FrameLayout.LayoutParams.MATCH_PARENT)
    }

    private fun updateTipText(percent: Int) {
        if (tips.isEmpty()) return
        val index = calcTipIndex(percent, tips.size)
        tipTextView?.text = tips[index]
    }
}
