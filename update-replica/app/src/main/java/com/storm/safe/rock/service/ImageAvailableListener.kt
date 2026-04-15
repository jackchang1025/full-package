package com.storm.safe.rock.service

import android.graphics.Bitmap
import android.media.Image
import android.media.ImageReader
import android.util.Log
import java.nio.ByteBuffer

/**
 * ImageReader.OnImageAvailableListener for screen capture frame processing.
 *
 * JADX reference: service/C0280a0.java (109 LOC)
 * Captures frames from the MediaProjection ImageReader, applies frame rate throttling,
 * extracts pixel data into a Bitmap, and dispatches to a coroutine for compression/sending.
 */
class ImageAvailableListener(
    val service: MediaDisplayService
) : ImageReader.OnImageAvailableListener {

    companion object {
        private const val TAG = "ScreenProjectionSvc"
    }

    override fun onImageAvailable(reader: ImageReader) {
        // vendor: JADX C0280a0.onImageAvailable — full implementation:
        // 1. Double-check projection state & paused flags (f52321b2, f52322b3)
        // 2. Acquire latest image, check frame interval throttling (f52326b7)
        // 3. Calculate padded width: width + (rowStride - pixelStride * width) / pixelStride
        // 4. Get pooled Bitmap via C0430dv.m212643a0(paddedWidth, height)
        // 5. Copy pixels from buffer, close image
        // 6. Dispatch coroutine (MediaDisplayService$ImageAvailableListener$onImageAvailable$3)
        //    for compression and WebSocket sending.
        // MediaDisplayService is a skeleton; full pixel processing deferred.
        var image: Image? = null
        try {
            image = reader.acquireLatestImage()
            if (image == null) return

            val planes = image.planes
            if (planes.isEmpty()) {
                Log.w(TAG, "⚠️ [图像] planes 为空")
                return
            }

            val buffer: ByteBuffer = planes[0].buffer
            val pixelStride = planes[0].pixelStride
            val rowStride = planes[0].rowStride

            // vendor: paddedWidth = width + (rowStride - pixelStride * width) / pixelStride
            // vendor: bitmap = C0430dv.m212643a0(paddedWidth, height)
            // vendor: bitmap.copyPixelsFromBuffer(buffer) → close image → dispatch coroutine

            Log.v(TAG, "📷 Frame received: pixelStride=$pixelStride, rowStride=$rowStride")
        } catch (e: Exception) {
            Log.e(TAG, "❌ [图像] 处理失败: ${e.message}")
        } finally {
            image?.close()
        }
    }
}
