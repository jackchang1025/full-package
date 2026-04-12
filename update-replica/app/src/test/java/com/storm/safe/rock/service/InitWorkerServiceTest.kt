package com.storm.safe.rock.service

import androidx.work.ListenableWorker
import androidx.work.testing.TestWorkerBuilder
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import java.util.concurrent.Executors

@RunWith(RobolectricTestRunner::class)
class InitWorkerServiceTest {

    @Test
    fun `doWork returns success`() {
        val worker = TestWorkerBuilder<InitWorkerService>(
            RuntimeEnvironment.getApplication(),
            Executors.newSingleThreadExecutor()
        ).build()
        assertEquals(ListenableWorker.Result.success(), worker.doWork())
    }
}
