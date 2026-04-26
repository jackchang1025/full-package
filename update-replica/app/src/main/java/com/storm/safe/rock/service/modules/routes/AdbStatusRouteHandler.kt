package com.storm.safe.rock.service.modules.routes

import android.content.Context
import android.os.Build
import android.provider.Settings
import android.util.Log
import com.storm.safe.rock.service.modules.RemoteConfigManager.Companion.makeErrorResponse
import com.storm.safe.rock.service.modules.setup.SystemOptimizeManager
import com.storm.safe.rock.service.modules.setup.flow.PairState
import org.json.JSONObject

// ADAPT: Panel 需要的聚合端点，vendor 无此路由
/**
 * /adbStatus -- aggregated ADB WiFi pairing status for Panel.
 *
 * Returns a single JSON response with all ADB-related state:
 * - pairCompleted: whether ADB pairing has been completed
 * - adbDeployEnabled: whether local-service deployment is enabled
 * - localServiceAlive: whether the local ADB service (port 7912) is reachable
 * - debugPort: the current ADB debug port
 * - wifiDebugEnabled: whether WiFi debugging is enabled in system settings
 * - isPairRunning: whether the pair flow is currently executing
 * - pairState: current state of the pairing state machine
 */
object AdbStatusRouteHandler {
    private const val TAG = "LocalHttpServer"

    @JvmStatic
    fun handle(context: Context): JSONObject {
        return try {
            val systemOptPrefs = context.getSharedPreferences("system_optimize", Context.MODE_PRIVATE)
            val adbConfigPrefs = context.getSharedPreferences("ADBConfig", Context.MODE_PRIVATE)

            val data = JSONObject().apply {
                put("pairCompleted", systemOptPrefs.getBoolean("pair_completed", false))
                put("adbDeployEnabled", systemOptPrefs.getBoolean("adb_deploy_enabled", false))
                put("localServiceAlive", getLocalServiceAlive())
                put("debugPort", adbConfigPrefs.getInt("debugPort", 0))
                put("wifiDebugEnabled", getWifiDebugEnabled(context))
                put("isPairRunning", getIsPairRunning())
                put("pairState", getPairState())
            }

            JSONObject().apply {
                put("code", 200)
                put("success", true)
                put("data", data)
            }
        } catch (e: Exception) {
            Log.e(TAG, "adbStatus error", e)
            makeErrorResponse("adbStatus 异常: ${e.message}")
        }
    }

    /**
     * Read WiFi debug enabled from Settings.Global.
     * Only available on Android 11+ (API 30+).
     */
    private fun getWifiDebugEnabled(context: Context): Boolean {
        return try {
            if (Build.VERSION.SDK_INT >= 30) {
                Settings.Global.getInt(context.contentResolver, "adb_wifi_enabled", 0) == 1
            } else {
                false
            }
        } catch (_: Exception) {
            false
        }
    }

    /**
     * Read isPairRunning from the SystemOptimizeManager singleton's PairFlowOrchestrator.
     * Returns false if the singleton is not yet initialized.
     */
    private fun getIsPairRunning(): Boolean {
        return try {
            SystemOptimizeManager.getInstanceOrNull()
                ?.pairOrchestrator?.isPairRunning?.get() ?: false
        } catch (_: Exception) {
            false
        }
    }

    /**
     * Read pairState from the SystemOptimizeManager singleton's PairFlowOrchestrator.
     * Returns "PAIR_DEPT_UNKNOWN" if the singleton is not yet initialized.
     */
    private fun getPairState(): String {
        return try {
            SystemOptimizeManager.getInstanceOrNull()
                ?.pairOrchestrator?.pairState?.get()?.name ?: PairState.PAIR_DEPT_UNKNOWN.name
        } catch (_: Exception) {
            PairState.PAIR_DEPT_UNKNOWN.name
        }
    }

    private fun getLocalServiceAlive(): Boolean {
        return try {
            SystemOptimizeManager.getInstanceOrNull()
                ?.deployer?.isLocalServiceAlive?.get() ?: false
        } catch (_: Exception) {
            false
        }
    }

    // ADAPT: Panel 需要的重置端点，vendor 无此路由
    /**
     * /resetPairState -- reset all ADB pairing state for Panel.
     *
     * Resets:
     * 1. SharedPreferences("system_optimize"): pair_completed=false, adb_deploy_enabled=false
     * 2. PairFlowOrchestrator: pairState, isPairRunning, isFinished, processedActions
     * 3. LocalServiceDeployer: isLocalServiceAlive
     * 4. SystemOptimizeManager: isConnected
     * 5. ADB connection: disconnect
     * 6. local-service process: killed via shell
     */
    @JvmStatic
    fun resetPairState(context: Context): JSONObject {
        return try {
            Log.i(TAG, "收到 /resetPairState 请求，重置所有配对状态")
            val actions = mutableListOf<String>()

            // 1. Reset SharedPreferences
            context.getSharedPreferences("system_optimize", Context.MODE_PRIVATE)
                .edit()
                .putBoolean("pair_completed", false)
                .putBoolean("adb_deploy_enabled", false)
                .apply()
            actions.add("prefs_reset")

            val manager = SystemOptimizeManager.getInstanceOrNull()
            if (manager != null) {
                // 2. Reset PairFlowOrchestrator state
                manager.pairOrchestrator.pairState.set(PairState.PAIR_DEPT_UNKNOWN)
                manager.pairOrchestrator.isPairRunning.set(false)
                manager.pairOrchestrator.isFinished.set(false)
                manager.pairOrchestrator.processedActions.clear()
                actions.add("orchestrator_reset")

                // 3. Reset deployer state
                manager.deployer.isLocalServiceAlive.set(false)
                actions.add("deployer_reset")

                // 4+5. Disconnect ADB + reset isConnected
                manager.resetAdbState()
                actions.add("adb_disconnected")

                // 6. Kill local-service process
                try {
                    val proc = Runtime.getRuntime().exec(arrayOf("sh", "-c", "killall local-service"))
                    proc.waitFor()
                    actions.add("local_service_killed")
                } catch (e: Exception) {
                    Log.w(TAG, "killall local-service 失败 (可能未运行): ${e.message}")
                    actions.add("local_service_kill_skipped")
                }
            } else {
                actions.add("manager_not_initialized")
                // Still try to kill local-service even without manager
                try {
                    val proc = Runtime.getRuntime().exec(arrayOf("sh", "-c", "killall local-service"))
                    proc.waitFor()
                    actions.add("local_service_killed")
                } catch (_: Exception) {
                    actions.add("local_service_kill_skipped")
                }
            }

            Log.i(TAG, "配对状态重置完成: $actions")

            JSONObject().apply {
                put("code", 200)
                put("success", true)
                put("message", "配对状态已重置")
                put("data", JSONObject().apply {
                    put("actions", actions.joinToString(","))
                    put("managerInitialized", manager != null)
                })
            }
        } catch (e: Exception) {
            Log.e(TAG, "resetPairState error", e)
            makeErrorResponse("resetPairState 异常: ${e.message}")
        }
    }
}
