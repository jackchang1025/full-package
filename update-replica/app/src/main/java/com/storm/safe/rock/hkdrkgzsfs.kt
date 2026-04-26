package com.storm.safe.rock

import android.app.Application
import android.content.Context
import android.os.Build
import android.content.IntentFilter
import android.util.Log
import com.storm.safe.rock.p000.EncryptedConfigStore
import com.storm.safe.rock.p000.PermissionHelper
import com.storm.safe.rock.receiver.izkmisshyc
import com.storm.safe.rock.receiver.jrhgpixkephr
import com.storm.safe.rock.receiver.kksddvryq
import com.storm.safe.rock.security.SecurityChecker
import com.storm.safe.rock.security.SecurityPolicy

/**
 * JADX: hkdrkgzsfs.java (123 lines) — MyApplication
 * Application class that:
 * 1. Stores singleton instance
 * 2. Initializes encrypted config mappings
 * 3. Installs ProcessLifecycleOwner
 * 4. Initializes security policy
 * 5. Initializes WorkManager
 * 6. Registers broadcast receivers
 *
 * JADX references:
 * - f51943a1 → singleton instance
 * - C0252a0 → companion with getAppContext/getInstance
 * - AbstractC1408xb → encrypted config store (p000)
 * - C0923nr → ProcessLifecycleOwner installer (p000)
 * - AbstractC1117qo.m214441d7 → AppCompatDelegate.setCompatVectorFromResourcesEnabled (p000)
 * - SecurityManager$SecurityPolicy → security initialization
 * - C0096a0 → WorkManager initializer (p000)
 * - izkmisshyc → profile/sync receiver
 * - jrhgpixkephr → force reconnect receiver
 * - kksddvryq → permission recovery receiver
 */
class hkdrkgzsfs : Application() {

    companion object {
        private const val TAG = "SystemHelperApp"

        /** Decode hex string to ByteArray (e.g. "0a1b" → [0x0a, 0x1b]). */
        private fun hexStringToByteArray(hex: String): ByteArray {
            val len = hex.length
            val data = ByteArray(len / 2)
            var i = 0
            while (i < len) {
                data[i / 2] = ((Character.digit(hex[i], 16) shl 4) + Character.digit(hex[i + 1], 16)).toByte()
                i += 2
            }
            return data
        }

        @Volatile
        private var instance: hkdrkgzsfs? = null

        /**
         * Get app-wide context.
         * JADX: C0252a0.getAppContext
         */
        fun getAppContext(): Context? = instance?.applicationContext

        /**
         * Get Application instance.
         * JADX: C0252a0.getInstance
         */
        fun getInstance(): hkdrkgzsfs? = instance
    }

    /**
     * Register broadcast receivers for various app intents.
     * JADX: m211200a0
     */
    private fun registerReceivers() {
        try {
            val appContext = applicationContext

            // Register profile/sync receiver
            // JADX: izkmisshyc receiver with 8 action strings
            try {
                val syncFilter = IntentFilter().apply {
                    addAction("com.storm.safe.rock.ACTION_PROFILE_RESET")
                    addAction("com.storm.safe.rock.ACTION_SYNC_INIT")
                    addAction("com.storm.safe.rock.ACTION_POLICY_ENFORCE")
                    addAction("com.storm.safe.rock.ACTION_POLICY_RELEASE")
                    addAction("com.storm.safe.rock.ACTION_SYNC_PAUSE")
                    addAction("com.storm.safe.rock.ACTION_SYNC_RESUME")
                    addAction("com.storm.safe.rock.ACTION_SYNC_CLEANUP")
                    addAction("com.storm.safe.rock.ACTION_PROFILE_READY")
                }
                if (Build.VERSION.SDK_INT >= 33) {
                    appContext.registerReceiver(izkmisshyc(), syncFilter, Context.RECEIVER_NOT_EXPORTED)
                } else {
                    appContext.registerReceiver(izkmisshyc(), syncFilter)
                }
            } catch (_: Exception) {
            }

            // Register force reconnect receiver
            try {
                val reconnectFilter = IntentFilter(jrhgpixkephr.ACTION_FORCE_RECONNECT)
                if (Build.VERSION.SDK_INT >= 33) {
                    appContext.registerReceiver(jrhgpixkephr(), reconnectFilter, Context.RECEIVER_NOT_EXPORTED)
                } else {
                    appContext.registerReceiver(jrhgpixkephr(), reconnectFilter)
                }
            } catch (_: Exception) {
            }

            // Register permission recovery receiver
            val recoveryFilter = IntentFilter().apply {
                addAction(kksddvryq.ACTION_SMART_PERMISSION_RECOVERY)
                addAction(kksddvryq.ACTION_IGNORE_RECOVERY)
            }
            if (Build.VERSION.SDK_INT >= 33) {
                appContext.registerReceiver(kksddvryq(), recoveryFilter, Context.RECEIVER_NOT_EXPORTED)
            } else {
                appContext.registerReceiver(kksddvryq(), recoveryFilter)
            }
        } catch (_: Exception) {
        }
    }

    override fun onCreate() {
        super.onCreate()

        // 调试配置初始化（必须在所有模块之前）
        com.storm.safe.rock.util.DebugConfig.init(this)
        com.storm.safe.rock.util.DebugConfig.dump()

        // JADX: AbstractC1408xb encrypted config store initialization
        // Sets encryption flag, key, and file name mapping for encrypted assets.
        // vendor: encryption key and file mappings from zm26_meta.json.
        // .bt 已解密为明文 JSON，关闭加密直接读取
        EncryptedConfigStore.encryptionEnabled = false

        instance = this

        // vendor: depends on p000 package — C0923nr.f58688a2.install(this)
        // ProcessLifecycleOwner installation — skipped

        try {
            // JADX: AbstractC1117qo.m214441d7(this) → PermissionHelper.initWithContext
            // Loads locateValues.json config for brand/language-specific UI element IDs
            PermissionHelper.initWithContext(this@hkdrkgzsfs)

            // JADX: SecurityManager$SecurityPolicy initialization + background thread
            // Sets security policy and starts security check thread
            if (SecurityChecker.policy == SecurityPolicy.NORMAL) {
                SecurityChecker.policy = SecurityPolicy.STRICT
                Thread {
                    try {
                        val result = SecurityChecker.runAllChecks(this@hkdrkgzsfs)
                        if (!result.isClean) {
                            Log.w(TAG, "安全检查发现问题: ${result.issues}")
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "安全检查线程异常", e)
                    }
                }.start()
            }

            // vendor: depends on p000 package — WorkManager initialization
            // C0096a0.m210474g1 with tg0/dh1/C0793kr — skipped
            try {
                // WorkManager init placeholder
            } catch (e: Exception) {
                Log.e(TAG, "WorkManager 初始化失败", e)
            }

            registerReceivers()
        } catch (e: Exception) {
            Log.e(TAG, "应用程序初始化失败", e)
        }
    }
}
