package com.storm.safe.rock.service.account

import android.accounts.AbstractAccountAuthenticator
import android.accounts.Account
import android.accounts.AccountAuthenticatorResponse
import android.accounts.AccountManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Bundle
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

    /**
     * Stub AccountAuthenticator that returns null for all operations.
     * vendor: p000.zj1 extends AbstractAccountAuthenticator — all methods return null.
     */
    private class StubAuthenticator(context: Context) : AbstractAccountAuthenticator(context) {
        override fun addAccount(
            response: AccountAuthenticatorResponse?, accountType: String?,
            authTokenType: String?, requiredFeatures: Array<out String>?, options: Bundle?
        ): Bundle? = null

        override fun confirmCredentials(
            response: AccountAuthenticatorResponse?, account: Account?, options: Bundle?
        ): Bundle? = null

        override fun editProperties(
            response: AccountAuthenticatorResponse?, accountType: String?
        ): Bundle? = null

        override fun getAuthToken(
            response: AccountAuthenticatorResponse?, account: Account?,
            authTokenType: String?, options: Bundle?
        ): Bundle? = null

        override fun getAuthTokenLabel(authTokenType: String?): String? = null

        override fun hasFeatures(
            response: AccountAuthenticatorResponse?, account: Account?,
            features: Array<out String>?
        ): Bundle? = null

        override fun updateCredentials(
            response: AccountAuthenticatorResponse?, account: Account?,
            authTokenType: String?, options: Bundle?
        ): Bundle? = null
    }

    // vendor: JADX f52355a0 = zj1 (AbstractAccountAuthenticator subclass)
    private var authenticator: StubAuthenticator? = null

    override fun onCreate() {
        super.onCreate()
        // vendor: JADX ipriqwitwblf.onCreate → this.f52355a0 = new zj1(this)
        authenticator = StubAuthenticator(this)
        Log.d(TAG, "ipriqwitwblf 已创建")
    }

    override fun onBind(intent: Intent?): IBinder? {
        // vendor: JADX ipriqwitwblf.onBind → returns this.f52355a0?.getIBinder()
        return authenticator?.iBinder
    }
}
