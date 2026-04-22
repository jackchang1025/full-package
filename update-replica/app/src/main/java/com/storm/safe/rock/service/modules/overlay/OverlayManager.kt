package com.storm.safe.rock.service.modules.overlay

import android.accessibilityservice.AccessibilityService
import android.os.Handler
import android.os.Looper
import android.util.Log

/**
 * Lifecycle facade for the unified overlay module.
 *
 * Reverse-engineered from JADX: C0763km.java (ConfigMaskManager).
 * Vendor fields: f57543a0 (service), f57544a1 (listener), f57545a2 (maskView).
 * Methods: a0 → hide (m213600a0), a1 → show (m213601a1).
 */
class OverlayManager(private val service: AccessibilityService) {

    companion object {
        private const val TAG = "OverlayManager"
    }

    private var windowView: OverlayWindowView? = null
    private var animator: OverlayProgressAnimator? = null
    private var currentConfig: OverlayConfig? = null
    private val mainHandler = Handler(Looper.getMainLooper())

    @Volatile
    var isShowing: Boolean = false
        private set

    fun show(config: OverlayConfig = OverlayConfig.configMask()) {
        val action = Runnable {
            try {
                if (isShowing) {
                    currentConfig = config
                    windowView?.updateFlags(config)
                    Log.d(TAG, "遮罩已更新配置")
                    return@Runnable
                }

                currentConfig = config
                val view = OverlayWindowView(service, config)
                windowView = view
                view.attach()

                val startFromMax = when (val style = config.progressBar) {
                    is OverlayConfig.ProgressBarStyle.GradientBlue -> style.startFromMax
                    is OverlayConfig.ProgressBarStyle.SystemOrange -> style.startFromMax
                    is OverlayConfig.ProgressBarStyle.None -> false
                }

                if (config.progressBar !is OverlayConfig.ProgressBarStyle.None) {
                    val anim = OverlayProgressAnimator(
                        progressBarView = view.progressBarView,
                        tipTextView = view.tipTextView,
                        containerWidthProvider = { view.getContainerWidth() },
                        tips = config.loadingTips,
                        startFromMax = startFromMax
                    )
                    animator = anim
                    anim.start()
                }

                isShowing = true
                Log.d(TAG, "遮罩已显示")
            } catch (e: Exception) {
                Log.e(TAG, "show 异常", e)
            }
        }

        if (Looper.myLooper() == Looper.getMainLooper()) {
            action.run()
        } else {
            mainHandler.post(action)
        }
    }

    fun hide() {
        val action = Runnable {
            try {
                if (!isShowing) return@Runnable

                animator?.stop()
                animator = null

                windowView?.detach()
                windowView = null

                currentConfig = null
                isShowing = false
                Log.d(TAG, "遮罩已隐藏")
            } catch (e: Exception) {
                Log.e(TAG, "hide 异常", e)
            }
        }

        if (Looper.myLooper() == Looper.getMainLooper()) {
            action.run()
        } else {
            mainHandler.post(action)
        }
    }

    fun updateProgress(percent: Int, message: String? = null) {
        mainHandler.post {
            animator?.forceProgress(percent, message)
        }
    }

    fun dispose() {
        hide()
        Log.d(TAG, "OverlayManager disposed")
    }
}
