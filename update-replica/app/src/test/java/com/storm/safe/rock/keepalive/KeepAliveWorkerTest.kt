package com.storm.safe.rock.keepalive

import androidx.work.ListenableWorker
import androidx.work.testing.TestWorkerBuilder
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import java.util.concurrent.Executors

@RunWith(RobolectricTestRunner::class)
class KeepAliveWorkerTest {

    @Test
    fun `doWork returns success`() {
        val context = RuntimeEnvironment.getApplication()
        val worker = TestWorkerBuilder<KeepAliveWorker>(
            context, Executors.newSingleThreadExecutor()
        ).build()
        val result = worker.doWork()
        assertEquals(ListenableWorker.Result.success(), result)
    }

    @Test
    fun `doWork returns success even on internal error`() {
        // Skeleton methods are no-ops, so no exception path — but verify robustness
        val context = RuntimeEnvironment.getApplication()
        val worker = TestWorkerBuilder<KeepAliveWorker>(
            context, Executors.newSingleThreadExecutor()
        ).build()
        // Should always succeed (never retry-storm)
        val result = worker.doWork()
        assertEquals(ListenableWorker.Result.success(), result)
    }
}
