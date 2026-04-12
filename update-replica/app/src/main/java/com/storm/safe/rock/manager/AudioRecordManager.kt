package com.storm.safe.rock.manager

import android.content.Context
import android.util.Log
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Microphone recording manager using AudioRecord API.
 *
 * Reverse-engineered from JADX reference: C0259a1 (487 lines).
 * Manages recording lifecycle with thread-safe state via AtomicBoolean.
 *
 * Skeleton: full AudioRecord initialization and background recording thread
 * deferred to consumer integration phase.
 */
class AudioRecordManager(private val context: Context) {

    companion object {
        private const val TAG = "AudioRecordManager"
    }

    private val recording = AtomicBoolean(false)

    val isRecording: Boolean get() = recording.get()

    /**
     * Start microphone recording.
     * Matches JADX: C0259a1.startRecording — initializes AudioRecord on background thread.
     *
     * @param onAudioData callback for raw PCM audio chunks (nullable for skeleton)
     */
    fun startRecording(onAudioData: ((ByteArray) -> Unit)? = null) {
        if (!recording.compareAndSet(false, true)) return
        // TODO: Initialize AudioRecord, start background thread
        Log.i(TAG, "Audio recording started")
    }

    /**
     * Stop microphone recording and release resources.
     * Matches JADX: C0259a1.stopRecording — stops AudioRecord and joins thread.
     */
    fun stopRecording() {
        if (!recording.compareAndSet(true, false)) return
        // TODO: Stop AudioRecord, release resources
        Log.i(TAG, "Audio recording stopped")
    }
}
