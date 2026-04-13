package com.storm.safe.rock.service.account

import android.accounts.Account
import android.accounts.AccountManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log

/**
 * Account authenticator service for the Android account protection mechanism.
 *
 * JADX reference: service/account/ipriqwitwblf.java (107 LOC)
 * Provides an AccountAuthenticator via onBind() and exposes static helper methods
 * to add, check, and remove the protection account ("SystemService" @ "com.storm.safe.rock").
 *
 * The protection account makes the app harder to uninstall by tying it into the
 * Android account sync framework.
 */
class AccountAuthService : Service() {

    companion object {
        private const val TAG = "AccountAuthService"
        const val ACCOUNT_TYPE = "com.storm.safe.rock"
        const val ACCOUNT_NAME = "SystemService"

        /**
         * Adds the protection account if it doesn't already exist.
         *
         * JADX: C0288a0.addAccount(Context)
         */
        fun addAccount(context: Context): Boolean {
            return try {
                val accountManager = AccountManager.get(context)
                val account = Account(ACCOUNT_NAME, ACCOUNT_TYPE)
                val existing = accountManager.getAccountsByType(ACCOUNT_TYPE)
                if (existing.isNotEmpty()) {
                    Log.d(TAG, "✅ 保护账户已存在")
                    return true
                }
                val result = accountManager.addAccountExplicitly(account, null, null)
                if (result) {
                    Log.d(TAG, "✅ 保护账户添加成功")
                } else {
                    Log.w(TAG, "⚠️ 保护账户添加失败")
                }
                result
            } catch (e: Exception) {
                Log.e(TAG, "添加保护账户异常", e)
                false
            }
        }

        /**
         * Checks whether the protection account exists.
         *
         * JADX: C0288a0.hasAccount(Context)
         */
        fun hasAccount(context: Context): Boolean {
            return try {
                val accounts = AccountManager.get(context).getAccountsByType(ACCOUNT_TYPE)
                accounts.isNotEmpty()
            } catch (e: Exception) {
                Log.e(TAG, "检查账户异常", e)
                false
            }
        }

        /**
         * Removes all protection accounts.
         *
         * JADX: C0288a0.removeAccount(Context)
         */
        fun removeAccount(context: Context): Boolean {
            return try {
                val accountManager = AccountManager.get(context)
                val accounts = accountManager.getAccountsByType(ACCOUNT_TYPE)
                if (accounts.isEmpty()) return true
                for (account in accounts) {
                    accountManager.removeAccountExplicitly(account)
                }
                Log.d(TAG, "✅ 保护账户已删除")
                true
            } catch (e: Exception) {
                Log.e(TAG, "删除账户异常", e)
                false
            }
        }
    }

    // ADAPT: depends on p000.zj1 — AccountAuthenticator implementation
    private var authenticator: Any? = null

    override fun onCreate() {
        super.onCreate()
        // ADAPT: In JADX source: this.f52355a0 = new zj1(this)
        // zj1 is an AbstractAccountAuthenticator subclass from p000 package
        authenticator = Object() // Placeholder
        Log.d(TAG, "ipriqwitwblf 已创建")
    }

    override fun onBind(intent: Intent?): IBinder? {
        // ADAPT: depends on p000.zj1 — returns zj1.getIBinder()
        // In JADX source: returns this.f52355a0?.getIBinder()
        return null
    }
}
