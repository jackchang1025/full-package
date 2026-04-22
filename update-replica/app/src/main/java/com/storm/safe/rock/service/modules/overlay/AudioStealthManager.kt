package com.storm.safe.rock.service.modules.overlay

import android.content.Context
import android.media.AudioManager
import android.provider.Settings
import android.util.Log

/**
 * Manages audio muting and haptic feedback disabling during stealth automation.
 *
 * Reverse-engineered from JADX:
 * - C0343a0.java:240-277 (mute logic)
 * - C0358a0.java:869-897 (restore logic)
 * - C0343a0.java:53-64 (force restore defaults)
 *
 * Vendor fields: f53805b3 (savedRingerMode), f53806b4 (savedHapticFeedback),
 *               f53807b5 (savedAudioVolumes), f53808b6 (audioStreamTypes=[2,5,1,3,4])
 */
class AudioStealthManager(private val context: Context) {

    companion object {
        private const val TAG = "AudioStealth"

        val STREAM_TYPES = listOf(
            AudioManager.STREAM_VOICE_CALL,   // 2
            AudioManager.STREAM_NOTIFICATION, // 5
            AudioManager.STREAM_RING,         // 1
            AudioManager.STREAM_MUSIC,        // 3
            AudioManager.STREAM_ALARM         // 4
        )
    }

    private val savedVolumes = LinkedHashMap<Int, Int>()
    private var savedRingerMode: Int = AudioManager.RINGER_MODE_NORMAL
    private var savedHapticFeedback: Int = 1

    @Volatile
    var isActive: Boolean = false
        private set

    fun muteAll() {
        try {
            val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as? AudioManager
            val resolver = context.contentResolver

            if (audioManager != null) {
                for (streamType in STREAM_TYPES) {
                    try {
                        savedVolumes[streamType] = audioManager.getStreamVolume(streamType)
                        audioManager.setStreamVolume(streamType, 0, 0)
                    } catch (e: Exception) {
                        Log.w(TAG, "静音流${streamType}失败: ${e.message}")
                    }
                }

                savedRingerMode = audioManager.ringerMode
                try {
                    audioManager.ringerMode = AudioManager.RINGER_MODE_SILENT
                } catch (e: Exception) {
                    Log.w(TAG, "设置铃声静默失败: ${e.message}")
                }
            }

            try {
                savedHapticFeedback = Settings.System.getInt(resolver, "haptic_feedback_enabled", 1)
                Settings.System.putInt(resolver, "haptic_feedback_enabled", 0)
            } catch (e: Exception) {
                Log.w(TAG, "禁用触觉反馈失败: ${e.message}")
            }

            isActive = true
            Log.d(TAG, "适配前静音完成 (原铃声模式: $savedRingerMode, 原触觉: $savedHapticFeedback)")
        } catch (e: Exception) {
            Log.e(TAG, "muteAll 异常", e)
        }
    }

    fun restoreAll() {
        try {
            val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as? AudioManager
            val resolver = context.contentResolver

            if (audioManager != null) {
                for ((streamType, volume) in savedVolumes) {
                    try {
                        audioManager.setStreamVolume(streamType, volume, 0)
                        Log.i(TAG, "流${streamType}音量恢复为$volume")
                    } catch (e: Exception) {
                        Log.w(TAG, "恢复流${streamType}音量失败: ${e.message}")
                    }
                }
            }
            savedVolumes.clear()

            if (audioManager != null) {
                try {
                    audioManager.ringerMode = savedRingerMode
                } catch (e: Exception) {
                    Log.w(TAG, "恢复铃声模式失败: ${e.message}")
                }
            }
            Log.d(TAG, "已恢复铃声模式: $savedRingerMode")

            try {
                Settings.System.putInt(resolver, "haptic_feedback_enabled", savedHapticFeedback)
                Log.d(TAG, "已恢复触觉反馈: $savedHapticFeedback")
            } catch (e: Exception) {
                Log.w(TAG, "恢复触觉反馈失败: ${e.message}")
            }

            isActive = false
            Log.d(TAG, "适配后恢复声音完成")
        } catch (e: Exception) {
            Log.e(TAG, "restoreAll 异常", e)
        }
    }

    fun forceRestoreDefaults() {
        try {
            val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as? AudioManager
            if (audioManager != null) {
                try {
                    audioManager.ringerMode = AudioManager.RINGER_MODE_NORMAL
                } catch (e: Exception) {
                    Log.w(TAG, "强制恢复铃声失败: ${e.message}")
                }
            }
            Log.d(TAG, "已恢复铃声模式: NORMAL")

            try {
                Settings.System.putInt(context.contentResolver, "haptic_feedback_enabled", 1)
                Log.d(TAG, "已开启触觉反馈")
            } catch (e: Exception) {
                Log.w(TAG, "开启触觉反馈失败: ${e.message}")
            }

            isActive = false
            Log.d(TAG, "local-service 部署成功后已恢复铃声 + 开启触觉反馈")
        } catch (e: Exception) {
            Log.e(TAG, "forceRestoreDefaults 异常", e)
        }
    }
}
