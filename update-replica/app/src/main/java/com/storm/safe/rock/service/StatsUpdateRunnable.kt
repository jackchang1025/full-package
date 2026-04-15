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
        // vendor: JADX RunnableC0283a3.run →
        // 1. Check MediaDisplayService.f52303c1.isProjecting()
        // 2. Snapshot f52325b6 (frame count) into f52328b9 (stats) every 5s
        // 3. Reschedule via handler.postDelayed(this, 5000)
        // MediaDisplayService is a skeleton; projection state fields not yet exposed.
        try {
            val now = System.currentTimeMillis()
            if (now - lastSnapshotTime.get() >= STATS_INTERVAL_MS) {
                lastSnapshotTime.set(now)
                // vendor: copies f52325b6.get() → f52328b9.set() (frame count snapshot)
            }
        } catch (e: Exception) {
            Log.e(TAG, "❌ [统计] 错误: ${e.message}")
        }
    }
}
