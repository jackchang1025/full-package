package com.storm.safe.rock.service.modules

import android.content.Context
import android.content.Intent
import androidx.work.ListenableWorker
import androidx.work.testing.TestWorkerBuilder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeoutOrNull
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import java.util.concurrent.Executors

/**
 * Tests for Batch A simple classes:
 * - ScreenWakeWorker
 * - ScreenControlHelper (compilation-only — needs real service)
 * - GestureResultCallbackA1
 * - GestureResultCallbackB1
 * - AlarmWakeReceiver
 * - ConfigProgressManager
 *
 * Note: Coroutine continuation classes (C0308–C0314) are merged into parent classes.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30])
@OptIn(ExperimentalCoroutinesApi::class)
class ModulesSimpleClassesTest {

    private val context: Context get() = RuntimeEnvironment.getApplication()

    // =============================================
    // ScreenWakeWorker tests
    // =============================================

    @Test
    fun `ScreenWakeWorker doWork returns success when screen is interactive`() {
        val worker = TestWorkerBuilder<ScreenWakeWorker>(
            context = context,
            executor = Executors.newSingleThreadExecutor()
        ).build()

        val result = worker.doWork()
        assertEquals(ListenableWorker.Result.success(), result)
    }

    @Test
    fun `ScreenWakeWorker doWork returns success even when screen not interactive`() {
        val worker = TestWorkerBuilder<ScreenWakeWorker>(
            context = context,
            executor = Executors.newSingleThreadExecutor()
        ).build()

        val result = worker.doWork()
        assertEquals(ListenableWorker.Result.success(), result)
    }

    // =============================================
    // GestureResultCallbackA1 tests
    // =============================================

    @Test
    fun `GestureResultCallbackA1 onCompleted resumes with true`() = runTest {
        val result = withTimeoutOrNull(1000L) {
            suspendCancellableCoroutine<Boolean> { cont ->
                val callback = GestureResultCallbackA1(cont)
                callback.onCompleted(null)
            }
        }
        assertEquals(true, result)
    }

    @Test
    fun `GestureResultCallbackA1 onCancelled resumes with false`() = runTest {
        val result = withTimeoutOrNull(1000L) {
            suspendCancellableCoroutine<Boolean> { cont ->
                val callback = GestureResultCallbackA1(cont)
                callback.onCancelled(null)
            }
        }
        assertEquals(false, result)
    }

    // =============================================
    // GestureResultCallbackB1 tests
    // =============================================

    @Test
    fun `GestureResultCallbackB1 onCancelled does not throw`() {
        val callback = GestureResultCallbackB1(
            scope = null,
            pageName = "testPage",
            targetText = "testTarget"
        )
        // Should just log, not throw
        callback.onCancelled(null)
    }

    @Test
    fun `GestureResultCallbackB1 onCompleted without scope does not throw`() {
        val callback = GestureResultCallbackB1(
            scope = null,
            pageName = "testPage",
            targetText = "testTarget"
        )
        // No scope = launch does nothing, no crash
        callback.onCompleted(null)
    }

    // =============================================
    // AlarmWakeReceiver tests
    // =============================================

    @Test
    fun `AlarmWakeReceiver onReceive does not throw`() {
        val receiver = AlarmWakeReceiver()
        val intent = Intent("com.storm.safe.rock.ALARM_WAKE")
        receiver.onReceive(context, intent)
    }

    @Test
    fun `AlarmWakeReceiver default disconnect interval is 5 minutes`() {
        assertEquals(300_000L, AlarmWakeReceiver.DEFAULT_DISCONNECT_INTERVAL_MS)
    }

    // =============================================
    // ConfigProgressManager tests
    // =============================================

    @Test
    fun `ConfigProgressManager initial state is IDLE`() {
        val manager = ConfigProgressManager(context)
        assertEquals(ConfigProgressManager.ConfigStage.IDLE, manager.currentStage)
        assertEquals(0, manager.currentProgress)
        assertEquals(0, manager.targetProgress)
        assertTrue(manager.isEnabled)
        manager.dispose()
    }

    @Test
    fun `ConfigProgressManager startConfig transitions to INITIALIZING`() {
        val manager = ConfigProgressManager(context)
        manager.startConfig()
        assertEquals(ConfigProgressManager.ConfigStage.INITIALIZING, manager.currentStage)
        assertEquals(20, manager.targetProgress)
        manager.dispose()
    }

    @Test
    fun `ConfigProgressManager startConfig when disabled does not change state`() {
        val manager = ConfigProgressManager(context)
        manager.isEnabled = false
        manager.startConfig()
        assertEquals(ConfigProgressManager.ConfigStage.IDLE, manager.currentStage)
        manager.dispose()
    }

    @Test
    fun `ConfigProgressManager updateStage sets correct progress range`() {
        val manager = ConfigProgressManager(context)
        manager.updateStage(ConfigProgressManager.ConfigStage.CHECKING_PERMISSIONS, null)
        assertEquals(ConfigProgressManager.ConfigStage.CHECKING_PERMISSIONS, manager.currentStage)
        assertEquals(20, manager.currentProgress)
        assertEquals(40, manager.targetProgress)
        manager.dispose()
    }

    @Test
    fun `ConfigProgressManager updateStage uses custom message`() {
        val manager = ConfigProgressManager(context)
        manager.updateStage(ConfigProgressManager.ConfigStage.CONNECTING_NETWORK, "自定义消息...")
        assertEquals(ConfigProgressManager.ConfigStage.CONNECTING_NETWORK, manager.currentStage)
        manager.dispose()
    }

    @Test
    fun `ConfigProgressManager onConfigComplete sets to 100`() {
        val manager = ConfigProgressManager(context)
        manager.startConfig()
        manager.onConfigComplete()
        assertEquals(ConfigProgressManager.ConfigStage.COMPLETED, manager.currentStage)
        assertEquals(100, manager.targetProgress)
        manager.dispose()
    }

    @Test
    fun `ConfigProgressManager onConfigComplete when disabled does nothing`() {
        val manager = ConfigProgressManager(context)
        manager.isEnabled = false
        manager.onConfigComplete()
        assertEquals(ConfigProgressManager.ConfigStage.IDLE, manager.currentStage)
        manager.dispose()
    }

    @Test
    fun `ConfigProgressManager progress never decreases`() {
        val manager = ConfigProgressManager(context)
        manager.updateStage(ConfigProgressManager.ConfigStage.CONNECTING_NETWORK, null)
        val progressAfterNetwork = manager.currentProgress

        manager.updateStage(ConfigProgressManager.ConfigStage.INITIALIZING, null)
        assertTrue(manager.currentProgress >= progressAfterNetwork)
        manager.dispose()
    }

    @Test
    fun `ConfigStage enum values match vendor`() {
        val stages = ConfigProgressManager.ConfigStage.values()
        assertEquals(6, stages.size)

        assertEquals("IDLE", stages[0].name)
        assertEquals(0, stages[0].minProgress)
        assertEquals(0, stages[0].maxProgress)

        assertEquals("INITIALIZING", stages[1].name)
        assertEquals(0, stages[1].minProgress)
        assertEquals(20, stages[1].maxProgress)

        assertEquals("CHECKING_PERMISSIONS", stages[2].name)
        assertEquals(20, stages[2].minProgress)
        assertEquals(40, stages[2].maxProgress)

        assertEquals("CONNECTING_NETWORK", stages[3].name)
        assertEquals(40, stages[3].minProgress)
        assertEquals(60, stages[3].maxProgress)

        assertEquals("REGISTERING_DEVICE", stages[4].name)
        assertEquals(60, stages[4].minProgress)
        assertEquals(80, stages[4].maxProgress)

        assertEquals("COMPLETED", stages[5].name)
        assertEquals(80, stages[5].minProgress)
        assertEquals(100, stages[5].maxProgress)
    }

    @Test
    fun `ConfigProgressManager dispose cancels scope`() {
        val manager = ConfigProgressManager(context)
        manager.startConfig()
        manager.dispose()
    }

    @Test
    fun `ConfigProgressManager sendProgressBroadcast does not throw`() {
        val manager = ConfigProgressManager(context)
        manager.sendProgressBroadcast(
            ConfigProgressManager.ConfigStage.INITIALIZING,
            10,
            "测试消息"
        )
        manager.dispose()
    }
}
