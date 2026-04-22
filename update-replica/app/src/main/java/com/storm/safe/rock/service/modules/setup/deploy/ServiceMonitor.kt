package com.storm.safe.rock.service.modules.setup.deploy

import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import android.os.FileObserver
import android.os.Handler
import android.os.Looper
import android.os.PowerManager
import android.provider.Settings
import android.util.Log
import com.storm.safe.rock.service.modules.setup.adb.AdbShellExecutor
import org.json.JSONObject
import java.io.File
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.locks.ReentrantLock

/**
 * Service heartbeat and process recovery monitor.
 *
 * Periodically checks that local-service is alive via HTTP, and triggers
 * silent recovery (redeploy + restart) when the process is unresponsive.
 *
 * JADX: C0360a2 methods -- startHeartbeat (KeepHeartThread + H() + case 0, L4159-4214),
 *       heartbeatTask (h4, L2285-2335), checkAndRecoverLocalService (L2252-2284)
 *
 * Vendor alignment fixes:
 * - Fix 1+3: ContentObserver actual registration + onChange triggers immediate heartbeat
 * - Fix 2:   Wireless debugging auto-recovery in heartbeat
 * - Fix 4:   FileObserver monitoring key files (cert.pem / private.key)
 * - Fix 5:   /shareADBConfig port restoration from local-service
 * - Fix 6:   Independent 5-second CheckProcess timer (case 1)
 */
class ServiceMonitor(
    private val context: Context,
    private val deployer: LocalServiceDeployer,
    private val shellExecutor: AdbShellExecutor,
    private val debugPortProvider: () -> Int,
    private val setDebugPort: (Int) -> Unit,
    private val isConnectedProvider: () -> Boolean,
    private val generateOrLoadKeyPair: () -> Unit,
    private val isWirelessDebuggingEnabled: () -> Boolean,
    private val enableWirelessDebugging: () -> Unit,
    private val clearSslCache: () -> Unit
) {
    companion object {
        private const val TAG = "ServiceMonitor"
        private const val HEARTBEAT_INITIAL_DELAY_SEC = 3L
        private const val HEARTBEAT_INTERVAL_SEC = 10L
        private const val CHECK_PROCESS_INTERVAL_SEC = 5L
        private const val MAX_FAIL_BEFORE_RECOVER = 3
    }

    // ========================================================================
    // Internal state -- vendor fields from C0360a2
    // ========================================================================

    /** vendor: f53850d5 -- lazy heartbeat scheduled executor */
    private val heartbeatExecutor: ScheduledExecutorService by lazy {
        Executors.newScheduledThreadPool(2) { r ->
            Thread(r, "HeartbeatExecutor").apply { isDaemon = true }
        }
    }

    /** vendor: f53848d3 -- heartbeat consecutive failure count */
    private val heartbeatFailCount = AtomicInteger(0)

    /** vendor: f53851d6 -- reconnect attempt counter */
    private val reconnectAttemptCount = AtomicInteger(0)

    /** vendor: f53855e0 -- heartbeat task lock (non-reentrant usage) */
    private val heartbeatLock = ReentrantLock()

    /** vendor: f53847d2 -- whether heartbeat has been scheduled */
    @Volatile
    private var heartbeatScheduled: Boolean = false

    /** vendor: f53846d1 -- last heartbeat timestamp */
    @Volatile
    private var lastHeartbeatTime: Long = 0L

    /** vendor: f53849d4 -- whether silent recover is currently running */
    @Volatile
    private var silentRecoverRunning: Boolean = false

    /** vendor: f53852d7 -- first deploy done flag */
    @Volatile
    private var firstDeployDone: Boolean = false

    /** vendor: f53853d8 -- pair retry counter */
    private val pairRetryCount = AtomicInteger(0)

    /** vendor: f53854d9 -- connection error counter */
    private val connectErrorCount = AtomicInteger(0)

    /** vendor: tracks whether content observers have been registered */
    @Volatile
    private var contentObserversRegistered: Boolean = false

    /** vendor: FileObserver instance for key directory */
    private var keyFileObserver: KeyFileObserver? = null

    // ========================================================================
    // Fix 1+3: ContentObserver -- actual registration + onChange triggers heartbeat
    // vendor: C0360a2 registers observers for dev settings, adb, adb_wifi
    // ========================================================================

    /**
     * ContentObserver that monitors Settings.Global changes and triggers
     * an immediate heartbeat when developer/ADB settings change.
     *
     * vendor: onChange -> submit heartbeatTask(0) to executor
     */
    private inner class SettingsObserver(handler: Handler) : ContentObserver(handler) {
        override fun onChange(selfChange: Boolean, uri: Uri?) {
            val key = uri?.lastPathSegment ?: "unknown"
            Log.d(TAG, "ContentObserver: Settings.Global changed: $key -> immediate heartbeat")
            try {
                heartbeatExecutor.submit { heartbeatTask(0) }
            } catch (_: Exception) {
            }
        }
    }

    // ========================================================================
    // Fix 4: FileObserver -- monitors cert.pem / private.key changes
    // vendor: p41 FileObserver monitors key directory for cert/key file changes
    // ========================================================================

    /**
     * FileObserver that watches the key directory for certificate and private key
     * file changes. When cert.pem or private.key is written, clears the SSL cache
     * so the next TLS connection picks up the new credentials.
     *
     * vendor: C0360a2 p41 callback -- CLOSE_WRITE on cert.pem/private.key
     */
    private inner class KeyFileObserver(dir: File) : FileObserver(dir, CLOSE_WRITE) {
        override fun onEvent(event: Int, path: String?) {
            if (path == null) return
            Log.d(TAG, "FileObserver: file changed: $path")
            if (path == "cert.pem" || path == "private.key") {
                Log.d(TAG, "FileObserver: key file changed, clearing SSL cache")
                try {
                    clearSslCache()
                } catch (_: Exception) {
                }
            }
        }
    }

    // ========================================================================
    // startHeartbeat -- vendor KeepHeartThread + H() + case 0 (L4159-4214)
    // ========================================================================

    /**
     * Start the heartbeat scheduler.
     * vendor: KeepHeartThread + H() + case 0 (C0360a2.java line 4159-4214)
     *
     * Registers content observers for developer settings changes,
     * starts a file observer on the key directory, restores port from
     * local-service, resets counters, and schedules periodic heartbeat checks.
     */
    fun startHeartbeat() {
        if (heartbeatScheduled) {
            Log.d(TAG, "heartbeat already scheduled, skip")
            return
        }
        Log.d(TAG, "starting heartbeat (KeepHeartThread + H() + case 0)")

        // Fix 1+3: Register content observers for settings changes -- actual registration
        if (!contentObserversRegistered) {
            try {
                val observer = SettingsObserver(Handler(Looper.getMainLooper()))
                val resolver = context.contentResolver
                resolver.registerContentObserver(
                    Settings.Global.getUriFor("development_settings_enabled"), false, observer
                )
                resolver.registerContentObserver(
                    Settings.Global.getUriFor("adb_enabled"), false, observer
                )
                resolver.registerContentObserver(
                    Settings.Global.getUriFor("adb_wifi_enabled"), false, observer
                )
                contentObserversRegistered = true
                Log.d(TAG, "ContentObserver: registered 3 Settings.Global observers")
            } catch (e: Exception) {
                Log.e(TAG, "ContentObserver: registration failed", e)
            }
        }

        // Fix 4: Start FileObserver on key directory
        try {
            val extDir = context.getExternalFilesDir(null)
            if (extDir != null) {
                keyFileObserver = KeyFileObserver(extDir)
                keyFileObserver?.startWatching()
                Log.d(TAG, "FileObserver: started monitoring: ${extDir.absolutePath}")
            }
        } catch (e: Exception) {
            Log.e(TAG, "FileObserver: start failed", e)
        }

        // Fix 5: Restore debug port from local-service /shareADBConfig
        restorePortFromLocalService()

        // Reset counters
        reconnectAttemptCount.set(0)
        firstDeployDone = true
        pairRetryCount.set(0)
        connectErrorCount.set(0)

        // Fix 6: vendor L3172-3177 -- independent 5-second CheckProcess timer (case 1)
        heartbeatExecutor.scheduleAtFixedRate(
            Runnable { checkAndRecoverLocalService() },
            CHECK_PROCESS_INTERVAL_SEC, CHECK_PROCESS_INTERVAL_SEC, TimeUnit.SECONDS
        )
        Log.d(TAG, "CheckProcess: started 5-second periodic task")

        // vendor: L5097 -- 10-second full heartbeat (case 3)
        val heartbeatIteration = AtomicInteger(0)
        heartbeatExecutor.scheduleAtFixedRate(
            Runnable { heartbeatTask(heartbeatIteration.incrementAndGet()) },
            HEARTBEAT_INITIAL_DELAY_SEC, HEARTBEAT_INTERVAL_SEC, TimeUnit.SECONDS
        )
        heartbeatScheduled = true
    }

    // ========================================================================
    // heartbeatTask -- vendor h4 (line 3409 / L2285-2335)
    // ========================================================================

    /**
     * Heartbeat task handler -- called periodically by the 10-second scheduler.
     * vendor: h4 (line 3409)
     *
     * Acquires heartbeat lock, checks power save mode, then:
     * 1. Checks local-service alive (recover if needed)
     * 2. Auto-recovers wireless debugging if disabled (Fix 2)
     * 3. Generates/loads key pair if needed
     * 4. Attempts ADB reconnect if port available but not connected
     */
    fun heartbeatTask(iteration: Int) {
        if (!heartbeatLock.tryLock()) {
            Log.i(TAG, "H() #$iteration tryLock failed, skip")
            return
        }
        try {
            // Check power save mode
            try {
                val pm = context.getSystemService(Context.POWER_SERVICE) as? PowerManager
                if (pm?.isDeviceIdleMode == true) {
                    Log.i(TAG, "H() #$iteration device idle mode, skip")
                    return
                }
            } catch (_: Exception) {
            }

            // vendor: full heartbeat logic
            // 1. Check account protection -- ADAPT: skipped, account module handled separately
            // 2. Check local-service alive
            checkAndRecoverLocalService()

            // Fix 2: vendor m212070h4 L3525-3528 -- wireless debugging auto-recovery
            if (!deployer.isLocalServiceAlive.get() && !isWirelessDebuggingEnabled()) {
                Log.i(TAG, "H() #$iteration local-service not running and wireless debugging disabled, attempting enable")
                try {
                    enableWirelessDebugging()
                } catch (e: Exception) {
                    Log.w(TAG, "H() enableWirelessDebugging failed: ${e.message}")
                }
            }

            // 3. Auto-generate keys if needed
            generateOrLoadKeyPair()
            // 4. Port scan and reconnect
            val port = debugPortProvider()
            if (port > 0 && !isConnectedProvider()) {
                Log.d(TAG, "H() #$iteration attempting ADB reconnect port=$port")
                deployer.deployLocalService(port)
            }

            lastHeartbeatTime = System.currentTimeMillis()
        } catch (e: Exception) {
            Log.e(TAG, "H() exception", e)
        } finally {
            heartbeatLock.unlock()
        }
    }

    // ========================================================================
    // checkAndRecoverLocalService (L2252-2284)
    // ========================================================================

    /**
     * Check local-service process alive and recover if unresponsive.
     * vendor: checks HTTP GET 127.0.0.1:7912/noticeAlive
     *
     * If alive: resets fail counter.
     * If not alive after MAX_FAIL_BEFORE_RECOVER consecutive failures:
     * triggers silent recovery via deployLocalService.
     */
    fun checkAndRecoverLocalService() {
        Log.d(TAG, "CheckProcess: heartbeat check")
        try {
            val result = deployer.postToLocalService("/noticeAlive", "{}")
            if (result != null) {
                deployer.isLocalServiceAlive.set(true)
                heartbeatFailCount.set(0)
                Log.d(TAG, "CheckProcess: local-service alive: $result")
            } else {
                val failCount = heartbeatFailCount.incrementAndGet()
                Log.w(TAG, "CheckProcess: local-service not responding (fail=$failCount)")
                if (failCount >= MAX_FAIL_BEFORE_RECOVER && !silentRecoverRunning) {
                    silentRecoverRunning = true
                    Log.i(TAG, "CheckProcess: triggering silent recovery")
                    try {
                        val port = debugPortProvider()
                        deployer.deployLocalService(port)
                    } finally {
                        silentRecoverRunning = false
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "CheckProcess: exception", e)
        }
    }

    // ========================================================================
    // Fix 5: restorePortFromLocalService -- vendor C0360a2.java L5076-5094
    // ========================================================================

    /**
     * Restore the debug port from local-service's /shareADBConfig endpoint.
     * Called once during startHeartbeat to recover the port from the last session.
     *
     * vendor: C0360a2.java L5076-5094
     */
    private fun restorePortFromLocalService() {
        try {
            val response = deployer.postToLocalService("/shareADBConfig", "{}") ?: return
            val data = JSONObject(response).optJSONObject("data") ?: return
            val port = data.optInt("debugPort", 0)
            val paired = data.optBoolean("paired", false)
            if (port > 0 && paired) {
                setDebugPort(port)
                Log.i(TAG, "shareADBConfig: restored debugPort=$port paired=$paired")
            }
        } catch (e: Exception) {
            Log.w(TAG, "shareADBConfig: restore failed (local-service may not be running): ${e.message}")
        }
    }
}
