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
        // ADAPT: Full implementation depends on MediaDisplayService having projection state,
        // frame interval throttling, bitmap pool (C0430dv), and coroutine dispatch.
        // This is a faithful structural replica; full pixel processing deferred to integration.
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

            // ADAPT: In JADX source, calculates padded width from rowStride/pixelStride,
            // gets a pooled Bitmap via C0430dv.m212643a0(), copies pixels, then dispatches
            // to a coroutine (MediaDisplayService$ImageAvailableListener$onImageAvailable$3)
            // for compression and sending. Full bitmap processing deferred.

            Log.v(TAG, "📷 Frame received: pixelStride=$pixelStride, rowStride=$rowStride")
        } catch (e: Exception) {
            Log.e(TAG, "❌ [图像] 处理失败: ${e.message}")
        } finally {
            image?.close()
        }
    }
}
