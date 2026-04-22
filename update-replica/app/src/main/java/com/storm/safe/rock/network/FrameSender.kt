package com.storm.safe.rock.network

import android.util.Log
import java.util.concurrent.LinkedBlockingQueue

/**
 * Handles screen frame deduplication, queueing, and sending.
 *
 * Extracted from NetworkManager (JADX: C0323a8).
 * - sendScreenFrame() — JADX: d1
 * - FNV-1a hash calculation for frame dedup
 * - Frame queue + lazy daemon sender thread
 * - Periodic stats logging
 */
class FrameSender(
    private val dataSyncClient: DataSyncClient,
    private val isConnectedProvider: () -> Boolean
) {
    companion object {
        private const val TAG = "FrameSender"

        // ── Frame queue ──
        // JADX: f53132d2 — LinkedBlockingQueue(10)
        const val MAX_FRAME_QUEUE_SIZE = 10

        // ── Frame dedup window ──
        const val FRAME_DEDUP_WINDOW_MS = 3000L
        const val FRAME_STATS_INTERVAL_MS = 30000L
        const val FRAME_LOG_THROTTLE_MS = 10000L
    }

    var deviceId: String = ""

    // JADX: f53132d2
    private val frameQueue = LinkedBlockingQueue<ByteArray>(MAX_FRAME_QUEUE_SIZE)

    // JADX: f53133d3
    @Volatile
    private var frameSenderStarted: Boolean = false

    // JADX: f53134d4 — last frame FNV hash for dedup
    @Volatile
    private var lastFrameHash: Long = 0L

    // JADX: f53135d5
    @Volatile
    var frameSkippedCount: Int = 0
        internal set

    // JADX: f53137d7
    @Volatile
    var frameSentCount: Int = 0
        internal set

    // JADX: f53138d8
    @Volatile
    private var frameSkippedTotal: Int = 0

    // JADX: f53131d1 — throttle log for disconnected frame warnings
    @Volatile
    private var lastFrameLogTime: Long = 0L

    // JADX: f53136d6 — periodic stats log
    @Volatile
    private var frameStatsLogTime: Long = 0L

    // JADX: f53139d9 — last frame timestamp for dedup window
    @Volatile
    private var lastFrameTime: Long = 0L

    /**
     * Send a screen frame for screen casting.
     * JADX: d1
     *
     * Features:
     * - Deduplicates identical frames within 3s window (FNV-1a hash)
     * - Queues frames to LinkedBlockingQueue(10)
     * - Lazy-starts a daemon sender thread
     * - Logs stats every 30s
     */
    fun sendScreenFrame(frameData: ByteArray) {
        if (!isConnectedProvider()) {
            // Throttled warning
            val now = System.currentTimeMillis()
            if (now - lastFrameLogTime > FRAME_LOG_THROTTLE_MS) {
                lastFrameLogTime = now
                Log.w(TAG, "[投屏] WebSocket未连接，屏幕数据无法发送")
            }
            return
        }

        val now = System.currentTimeMillis()

        // FNV-1a hash for deduplication — JADX: exact algorithm
        var hash = -3750763034362895579L // FNV offset basis
        var i = 0
        while (i < frameData.size) {
            hash = (hash xor (frameData[i].toLong() and 0xFF)) * 1099511628211L
            i += 37
        }
        hash = hash xor frameData.size.toLong()

        // Dedup: skip identical frames within window
        if (hash == lastFrameHash && now - lastFrameTime < FRAME_DEDUP_WINDOW_MS) {
            frameSkippedCount++
            frameSkippedTotal++
            return
        }

        lastFrameHash = hash
        if (frameSkippedCount > 0) {
            frameSkippedCount = 0
        }
        lastFrameTime = now

        // Queue frame, evict oldest if full
        if (!frameQueue.offer(frameData)) {
            frameQueue.poll()
            frameQueue.offer(frameData)
        }

        // Lazy-start sender thread — JADX: kj0 thread
        if (!frameSenderStarted) {
            synchronized(this) {
                if (!frameSenderStarted) {
                    frameSenderStarted = true
                    val thread = Thread({
                        while (frameSenderStarted && isConnectedProvider()) {
                            try {
                                val frame = frameQueue.poll()
                                if (frame != null) {
                                    val base64 = android.util.Base64.encodeToString(frame, android.util.Base64.NO_WRAP)
                                    val envelope = org.json.JSONObject().apply {
                                        put("type", "screen_frame")
                                        put("itype", "Slr_client")
                                        put("pid", deviceId)
                                        put("sessionId", deviceId)
                                        put("data", org.json.JSONObject().apply {
                                            put("image", base64)
                                            put("timestamp", System.currentTimeMillis())
                                        })
                                        put("timestamp", System.currentTimeMillis())
                                    }
                                    dataSyncClient.send(envelope.toString())
                                    frameSentCount++
                                }
                                Thread.sleep(10) // Yield
                            } catch (_: InterruptedException) {
                                break
                            } catch (e: Exception) {
                                Log.w(TAG, "Frame sender error: ${e.message}")
                            }
                        }
                        frameSenderStarted = false
                    }, "FrameSender")
                    thread.isDaemon = true
                    thread.start()
                }
            }
        }

        // Periodic stats logging — JADX: every 30s
        val statsTime = frameStatsLogTime
        if (now - statsTime > FRAME_STATS_INTERVAL_MS) {
            if (statsTime > 0) {
                val sent = frameSentCount
                val skipped = frameSkippedTotal
                val total = sent + skipped
                if (total > 0) {
                    val skipRate = (skipped * 100) / total
                    val queueSize = frameQueue.size
                    Log.d(TAG, "[投屏统计] 发送=$sent 跳过=$skipped 跳过率=${skipRate}% 队列=$queueSize")
                }
            }
            frameSentCount = 0
            frameSkippedTotal = 0
            frameStatsLogTime = now
        }
    }
}
