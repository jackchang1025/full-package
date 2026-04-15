package com.storm.safe.rock.service.modules.automation

import android.util.Log
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * Serialize competing automation flows that each call startActivity on permission
 * settings pages. Without this coordinator, MIUI's flows (Yw5xud adaptation,
 * WRITE_SETTINGS 20s polling, ALL_FILES_ACCESS 10s polling) interrupt each other
 * by launching Activities while another flow is mid-toggle, causing pages to
 * reshuffle and no flow can stabilize long enough to complete authorization.
 *
 * Usage:
 *   coordinator.withFlow("auth") {
 *       executeAuthorizationFlow()
 *   }
 *
 * After a failure, callers should invoke markFailure(). shouldSkipDueToRecentFailure()
 * returns true for AUTH_COOLDOWN_MS (5 min) after a failure, preventing immediate retry.
 */
object AutomationCoordinator {
    private const val TAG = "AutomationCoordinator"
    const val AUTH_COOLDOWN_MS: Long = 5 * 60_000L

    private val mutex = Mutex()
    @Volatile private var currentFlowTag: String? = null
    @Volatile private var lastFailedAt: Long = 0L
    @Volatile private var lastSucceededAt: Long = 0L

    /** Acquire exclusive access for [flowTag]. Suspends if another flow holds it. */
    suspend fun <T> withFlow(flowTag: String, block: suspend () -> T): T {
        Log.d(TAG, "acquire request \"$flowTag\" (busy=${isBusy()}, current=${currentFlowTag})")
        return mutex.withLock {
            currentFlowTag = flowTag
            Log.i(TAG, "▶ acquire \"$flowTag\"")
            try {
                block()
            } finally {
                Log.i(TAG, "◀ release \"$flowTag\"")
                currentFlowTag = null
            }
        }
    }

    /** Non-suspending: true if any flow holds the mutex. */
    fun isBusy(): Boolean = currentFlowTag != null

    /** Non-suspending: current flow tag or null. */
    fun currentFlow(): String? = currentFlowTag

    /** True if a failure occurred within AUTH_COOLDOWN_MS. */
    fun shouldSkipDueToRecentFailure(): Boolean {
        val failedAt = lastFailedAt
        if (failedAt == 0L) return false
        return (System.currentTimeMillis() - failedAt) < AUTH_COOLDOWN_MS
    }

    /** Record a failure. Starts the cooldown clock. */
    fun markFailure() {
        lastFailedAt = System.currentTimeMillis()
        Log.w(TAG, "markFailure — cooldown armed for ${AUTH_COOLDOWN_MS / 1000}s")
    }

    /** Record a success. Clears any pending cooldown. */
    fun markSuccess() {
        lastSucceededAt = System.currentTimeMillis()
        lastFailedAt = 0L
        Log.i(TAG, "markSuccess — cooldown cleared")
    }
}
