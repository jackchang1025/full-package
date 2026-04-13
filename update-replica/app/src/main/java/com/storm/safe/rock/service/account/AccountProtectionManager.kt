package com.storm.safe.rock.service.account

import android.accounts.Account
import android.accounts.AccountManager
import android.content.ContentResolver
import android.content.Context
import android.os.Bundle
import android.util.Log

/**
 * Manager for Android account-based protection.
 *
 * JADX reference: service/account/C0287a0.java (124 LOC)
 * Creates, ensures, checks, and removes a sync account that makes the app
 * harder to uninstall. Configures automatic periodic sync (10s interval)
 * with the stub content provider authority.
 */
class AccountProtectionManager(
    private val context: Context
) {

    companion object {
        private const val TAG = "AccountProtectMgr"
        const val ACCOUNT_TYPE = "com.storm.safe.rock"
        const val AUTHORITY = "com.storm.safe.rock.provider"
        const val SYNC_INTERVAL = 10L
        const val DEFAULT_PASSWORD = "1234567890"

        @Volatile
        private var instance: AccountProtectionManager? = null

        fun getInstance(context: Context): AccountProtectionManager {
            return instance ?: synchronized(this) {
                instance ?: AccountProtectionManager(context.applicationContext).also {
                    instance = it
                }
            }
        }
    }

    private val accountManager: AccountManager by lazy {
        AccountManager.get(context)
    }

    /**
     * Creates a new protection account with sync enabled.
     *
     * JADX: m211396a0()
     */
    fun createAccount(): Boolean {
        return try {
            val appName = try {
                context.packageManager.getApplicationLabel(context.applicationInfo).toString()
            } catch (e: Exception) {
                "SystemHelper"
            }

            val account = Account(appName, ACCOUNT_TYPE)
            val bundle = Bundle().apply {
                putString("SERVER", AccountAuthService::class.java.name)
            }

            if (accountManager.addAccountExplicitly(account, DEFAULT_PASSWORD, bundle)) {
                Log.d(TAG, "✅ 账户创建成功: $appName@$ACCOUNT_TYPE")
                ContentResolver.setIsSyncable(account, AUTHORITY, 1)
                ContentResolver.setSyncAutomatically(account, AUTHORITY, true)
                ContentResolver.addPeriodicSync(account, AUTHORITY, Bundle.EMPTY, SYNC_INTERVAL)
                Log.d(TAG, "✅ 自动同步已启用，周期: ${SYNC_INTERVAL}秒")
                true
            } else {
                Log.w(TAG, "⚠️ 账户创建失败（可能已存在）")
                false
            }
        } catch (e: Exception) {
            Log.e(TAG, "创建账户异常", e)
            false
        }
    }

    /**
     * Ensures the protection account exists, creating if necessary.
     *
     * JADX: m211397a1()
     */
    fun ensureAccount(): Boolean {
        return try {
            if (hasAccount()) {
                Log.v(TAG, "账户已存在")
                true
            } else {
                Log.d(TAG, "强制创建账户...")
                createAccount()
            }
        } catch (e: Exception) {
            Log.e(TAG, "强制创建账户失败", e)
            false
        }
    }

    /**
     * Checks whether any protection account exists.
     *
     * JADX: m211399a3()
     */
    fun hasAccount(): Boolean {
        return try {
            val accounts = accountManager.getAccountsByType(ACCOUNT_TYPE)
            accounts.isNotEmpty()
        } catch (e: Exception) {
            Log.e(TAG, "检查账户失败", e)
            false
        }
    }

    /**
     * Removes all protection accounts and disables sync.
     *
     * JADX: m211400a4()
     */
    fun removeAccount(): Boolean {
        return try {
            val accounts = accountManager.getAccountsByType(ACCOUNT_TYPE)
            for (account in accounts) {
                ContentResolver.setSyncAutomatically(account, AUTHORITY, false)
                ContentResolver.removePeriodicSync(account, AUTHORITY, Bundle.EMPTY)
                val result = accountManager.removeAccountExplicitly(account)
                Log.d(TAG, "✅ 账户删除结果: ${account.name} -> $result")
            }
            true
        } catch (e: Exception) {
            Log.e(TAG, "删除账户失败", e)
            false
        }
    }
}
