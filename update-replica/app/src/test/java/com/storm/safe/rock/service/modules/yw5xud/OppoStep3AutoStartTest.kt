package com.storm.safe.rock.service.modules.yw5xud

import android.content.Context
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class OppoStep3AutoStartTest {
    private lateinit var context: Context
    @Before fun setUp() { context = RuntimeEnvironment.getApplication() }
    @After fun tearDown() { OppoStepCompletionStore.clearAll(context) }

    @Test fun `skips when autostart already completed`() {
        runBlocking {
            OppoStepCompletionStore.markCompleted(context, OppoStepCompletionStore.Keys.STEP3_AUTOSTART)
            val spy = object : OppoSteps(null, context) {
                var autoCalled = false
                var bgCalled = false
                override suspend fun runAutoStartSubSwitch(s: MutableList<String>, f: MutableList<String>, l: MutableList<String>): Boolean { autoCalled = true; return true }
                override suspend fun runBackgroundSubSwitch(s: MutableList<String>, f: MutableList<String>, l: MutableList<String>): Boolean { bgCalled = true; return true }
            }
            val logs = mutableListOf<String>()
            spy.executeStep3AutoStart(mutableListOf(), mutableListOf(), logs)
            assertTrue("Expected skip log", logs.any { it.contains("跳过") })
            assertTrue("No sub should be invoked", !spy.autoCalled && !spy.bgCalled)
        }
    }

    @Test fun `marks completed when both subswitches marked`() {
        runBlocking {
            val spy = object : OppoSteps(null, context) {
                override suspend fun runAutoStartSubSwitch(s: MutableList<String>, f: MutableList<String>, l: MutableList<String>) = true
                override suspend fun runBackgroundSubSwitch(s: MutableList<String>, f: MutableList<String>, l: MutableList<String>) = true
            }
            spy.executeStep3AutoStart(mutableListOf(), mutableListOf(), mutableListOf())
            assertTrue("Step3 应被 mark",
                OppoStepCompletionStore.isCompleted(context, OppoStepCompletionStore.Keys.STEP3_AUTOSTART))
        }
    }

    @Test fun `does not mark overall when only autostart sub succeeds`() {
        runBlocking {
            val spy = object : OppoSteps(null, context) {
                override suspend fun runAutoStartSubSwitch(s: MutableList<String>, f: MutableList<String>, l: MutableList<String>): Boolean {
                    OppoStepCompletionStore.markCompleted(context, OppoStepCompletionStore.Keys.STEP3_AUTOSTART_SWITCH)
                    return true
                }
                override suspend fun runBackgroundSubSwitch(s: MutableList<String>, f: MutableList<String>, l: MutableList<String>) = false
            }
            spy.executeStep3AutoStart(mutableListOf(), mutableListOf(), mutableListOf())
            assertTrue("整体 mark 不应写入",
                !OppoStepCompletionStore.isCompleted(context, OppoStepCompletionStore.Keys.STEP3_AUTOSTART))
            assertTrue("autostart_switch 子 mark 应写入",
                OppoStepCompletionStore.isCompleted(context, OppoStepCompletionStore.Keys.STEP3_AUTOSTART_SWITCH))
        }
    }

    @Test fun `falls back to SafeCenter ComponentName when Settings path fails`() {
        runBlocking {
            val spy = object : OppoSteps(null, context) {
                var safeCenterCalled = false
                override suspend fun tryOpenAutoStartViaSettings(s: MutableList<String>, f: MutableList<String>, l: MutableList<String>) = false
                override suspend fun tryOpenAutoStartViaSafeCenter(s: MutableList<String>, f: MutableList<String>, l: MutableList<String>): Boolean {
                    safeCenterCalled = true
                    return true
                }
                override suspend fun runBackgroundSubSwitch(s: MutableList<String>, f: MutableList<String>, l: MutableList<String>) = true
            }
            val logs = mutableListOf<String>()
            spy.executeStep3AutoStart(mutableListOf(), mutableListOf(), logs)
            assertTrue("SafeCenter 应被调用", spy.safeCenterCalled)
            assertTrue("Expected SafeCenter fallback log",
                logs.any { it.contains("SafeCenter") || it.contains("safecenter") })
        }
    }
}
