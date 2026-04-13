package com.storm.safe.rock.service.modules

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import kotlinx.coroutines.*

/**
 * Manages configuration progress tracking and broadcasts updates via Intent.
 *
 * Reverse-engineered from JADX: C0318a3 (a3, 122 lines).
 * Vendor fields:
 * - f53045a0 (context), f53046a1 (currentStage), f53047a2 (enabled),
 * - f53048a3 (currentProgress), f53049a4 (targetProgress),
 * - f53050a5 (fakeProgressJob), f53051a6 (coroutineScope), f53052a7 (intervalMs=3000)
 *
 * Methods:
 * - a0 → onConfigComplete (static)
 * - a1 → ensureFakeProgress
 * - a2 → sendProgressBroadcast
 * - a3 → startConfig
 * - a4 → updateStage
 */
class ConfigProgressManager(
    private val context: Context
) {
    companion object {
        private const val TAG = "ConfigProgressManager"
        const val ACTION_PROGRESS_UPDATE = "com.storm.safe.rock.intent.CONFIG_PROGRESS_UPDATE"
        const val EXTRA_STAGE = "progress_stage"
        const val EXTRA_PERCENTAGE = "progress_percentage"
        const val EXTRA_MESSAGE = "progress_message"
    }

    /**
     * Configuration progress stages.
     *
     * Reverse-engineered from JADX: ConfigProgressManager$ConfigStage enum.
     * Each stage has a minProgress, maxProgress, and default message.
     */
    enum class ConfigStage(
        val minProgress: Int,
        val maxProgress: Int,
        val defaultMessage: String
    ) {
        IDLE(0, 0, "准备开始配置..."),
        INITIALIZING(0, 20, "正在初始化服务..."),
        CHECKING_PERMISSIONS(20, 40, "正在检查系统权限..."),
        CONNECTING_NETWORK(40, 60, "正在连接到服务器..."),
        REGISTERING_DEVICE(60, 80, "正在注册设备信息..."),
        COMPLETED(80, 100, "配置已完成")
    }

    // --- State (mirrors vendor fields) ---

    var currentStage: ConfigStage = ConfigStage.IDLE
        private set

    var isEnabled: Boolean = true

    var currentProgress: Int = 0
        private set

    var targetProgress: Int = 0
        private set

    private var fakeProgressJob: Job? = null
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    private val progressIntervalMs: Long = 3000L
    private val mainHandler = Handler(Looper.getMainLooper())

    // --- a0: onConfigComplete (static in vendor) ---

    /**
     * Mark configuration as completed: set stage to COMPLETED, progress to 100%.
     * Vendor: m211566a0 (static).
     */
    fun onConfigComplete() {
        if (!isEnabled) {
            Log.w(TAG, "⚠️ 进度条未启用，跳过配置完成")
            return
        }
        updateStage(ConfigStage.COMPLETED, "配置已完成，正在返回应用...")
        targetProgress = 100
        ensureFakeProgress()
        // Vendor: posts a delayed Runnable after 100ms
        mainHandler.postDelayed({ /* Phase 10: navigate back */ }, 100L)
    }

    // --- a1: ensureFakeProgress ---

    /**
     * Ensures fake progress is running (incrementing towards targetProgress).
     * Vendor: m211567a1
     */
    fun ensureFakeProgress() {
        val job = fakeProgressJob
        if (job != null && job.isActive) return

        fakeProgressJob = scope.launch {
            while (isActive && currentProgress < targetProgress) {
                delay(progressIntervalMs)
                if (currentProgress < targetProgress) {
                    currentProgress++
                    sendProgressBroadcast(currentStage, currentProgress, currentStage.defaultMessage)
                }
            }
        }
    }

    // --- a2: sendProgressBroadcast ---

    /**
     * Broadcast a progress update via Intent.
     * Vendor: m211568a2
     */
    fun sendProgressBroadcast(stage: ConfigStage, progress: Int, message: String) {
        try {
            val intent = Intent(ACTION_PROGRESS_UPDATE).apply {
                putExtra(EXTRA_STAGE, stage.name)
                putExtra(EXTRA_PERCENTAGE, progress)
                putExtra(EXTRA_MESSAGE, message)
            }
            mainHandler.post {
                try {
                    context.sendBroadcast(intent)
                } catch (e: Exception) {
                    Log.e(TAG, "❌ 发送进度广播失败", e)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "❌ 发送进度广播失败", e)
        }
    }

    // --- a3: startConfig ---

    /**
     * Start the configuration process at INITIALIZING stage.
     * Vendor: m211569a3
     */
    fun startConfig() {
        if (isEnabled) {
            updateStage(ConfigStage.INITIALIZING, null)
        } else {
            Log.w(TAG, "⚠️ 进度条功能已禁用，跳过配置流程")
        }
    }

    // --- a4: updateStage ---

    /**
     * Update to a new stage with optional custom message.
     * Vendor: m211570a4
     */
    fun updateStage(stage: ConfigStage, message: String?) {
        if (!isEnabled) return

        currentStage = stage
        val displayMessage = message ?: stage.defaultMessage
        currentProgress = maxOf(currentProgress, stage.minProgress)
        targetProgress = stage.maxProgress
        sendProgressBroadcast(currentStage, currentProgress, displayMessage)
        ensureFakeProgress()
    }

    /**
     * Cancel any running fake progress job and clean up.
     */
    fun dispose() {
        fakeProgressJob?.cancel()
        scope.cancel()
    }
}
