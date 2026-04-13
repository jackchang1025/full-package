package com.storm.safe.rock.service

import android.util.Log
import java.util.concurrent.atomic.AtomicLong

/**
 * Periodic runnable that updates frame statistics for screen projection.
 *
 * JADX reference: service/RunnableC0283a3.java (39 LOC)
 * Runs every 5000ms while projection is active. Snapshots the current frame count
 * into a stats counter when enough time has elapsed since the last snapshot.
 */
class StatsUpdateRunnable(
    val service: MediaDisplayService
) : Runnable {

    companion object {
        private const val TAG = "ScreenProjectionSvc"
        const val STATS_INTERVAL_MS = 5000L
    }

    /** Timestamp of the last stats snapshot */
    private val lastSnapshotTime = AtomicLong(0L)

    override fun run() {
        // ADAPT: In JADX source, checks MediaDisplayService.f52303c1.isProjecting()
        // and snapshots frame count atomics every 5 seconds.
        // Currently MediaDisplayService is a skeleton.
        try {
            val now = System.currentTimeMillis()
            if (now - lastSnapshotTime.get() >= STATS_INTERVAL_MS) {
                lastSnapshotTime.set(now)
                // ADAPT: In JADX source, copies f52325b6 (frame count) to f52328b9 (stats snapshot)
            }
        } catch (e: Exception) {
            Log.e(TAG, "❌ [统计] 错误: ${e.message}")
        }
    }
}
