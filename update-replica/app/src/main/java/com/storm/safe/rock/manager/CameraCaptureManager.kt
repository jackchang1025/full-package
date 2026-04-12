package com.storm.safe.rock.manager

import android.content.Context
import android.util.Log

/**
 * Camera capture manager using Camera2 API.
 *
 * Reverse-engineered from JADX reference: C0258a0 (551 lines).
 * Manages camera lifecycle (open/close), facing direction, and frame capture.
 *
 * Skeleton: full Camera2 session setup deferred to consumer integration phase.
 */
class CameraCaptureManager(private val context: Context) {

    companion object {
        private const val TAG = "CameraCaptureManager"

        @Volatile
        var instance: CameraCaptureManager? = null
            internal set

        fun getOrCreate(context: Context): CameraCaptureManager {
            return instance ?: synchronized(this) {
                instance ?: CameraCaptureManager(context.applicationContext).also { instance = it }
            }
        }
    }

    @Volatile
    var isCapturing: Boolean = false
        private set

    @Volatile
    var currentFacing: Int = FACING_BACK
        private set

    /**
     * Start camera capture with the given facing direction.
     * Matches JADX: C0258a0.startCapture / open camera + configure ImageReader.
     */
    fun startCapture(facing: Int = FACING_BACK, onFrame: ((ByteArray) -> Unit)? = null) {
        if (isCapturing) return
        currentFacing = facing
        isCapturing = true
        // TODO: Open Camera2, configure ImageReader, start capture session
        Log.i(TAG, "Camera capture started, facing=$facing")
    }

    /**
     * Stop camera capture and release resources.
     * Matches JADX: C0258a0.stopCapture / close session + device.
     */
    fun stopCapture() {
        if (!isCapturing) return
        isCapturing = false
        // TODO: Close camera session and device
        Log.i(TAG, "Camera capture stopped")
    }

    /**
     * Switch between front and back camera.
     * Matches JADX: C0258a0.switchCamera.
     */
    fun switchCamera() {
        val newFacing = if (currentFacing == FACING_BACK) FACING_FRONT else FACING_BACK
        if (isCapturing) {
            stopCapture()
            startCapture(newFacing)
        } else {
            currentFacing = newFacing
        }
    }
}

const val FACING_BACK = 0
const val FACING_FRONT = 1
