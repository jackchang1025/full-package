package com.storm.safe.rock.service.modules.routes

import android.content.Context
import android.os.Build
import android.provider.Settings
import android.util.Log
import com.storm.safe.rock.p000.LocalServiceAliveChecker
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
                put("localServiceAlive", LocalServiceAliveChecker.isAlive())
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
}
