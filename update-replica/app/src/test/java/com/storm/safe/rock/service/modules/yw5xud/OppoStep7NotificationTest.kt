package com.storm.safe.rock.service.modules.yw5xud

import android.content.Context
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class OppoStep7NotificationTest {
    private lateinit var context: Context
    @Before fun setUp() { context = RuntimeEnvironment.getApplication() }
    @After fun tearDown() { OppoStepCompletionStore.clearAll(context) }

    @Test fun `skips when step7 completed`() {
        runBlocking {
            OppoStepCompletionStore.markCompleted(context, OppoStepCompletionStore.Keys.STEP7_NOTIFICATION)
            val spy = object : OppoSteps(null, context) {}
            val logs = mutableListOf<String>()
            spy.executeStep7Notification(mutableListOf(), mutableListOf(), logs)
            assertTrue(logs.any { it.contains("跳过") })
        }
    }

    @Test fun `launches CHANNEL_NOTIFICATION_SETTINGS with OFF channel`() {
        runBlocking {
            var launchedChannel: String? = null
            val spy = object : OppoSteps(null, context) {
                override suspend fun launchChannelSettings(channelId: String) { launchedChannel = channelId }
                override suspend fun tryCloseOffChannelSwitch(s: MutableList<String>, l: MutableList<String>) = true
            }
            spy.executeStep7Notification(mutableListOf(), mutableListOf(), mutableListOf())
            assertEquals("OFF", launchedChannel)
        }
    }

    @Test fun `marks success when tryCloseOffChannelSwitch returns true`() {
        runBlocking {
            val spy = object : OppoSteps(null, context) {
                override suspend fun launchChannelSettings(channelId: String) { /* stub */ }
                override suspend fun tryCloseOffChannelSwitch(s: MutableList<String>, l: MutableList<String>) = true
            }
            spy.executeStep7Notification(mutableListOf(), mutableListOf(), mutableListOf())
            assertTrue(OppoStepCompletionStore.isCompleted(context, OppoStepCompletionStore.Keys.STEP7_NOTIFICATION))
        }
    }

    @Test fun `marks success when OFF channel switch is already closed (no need to toggle)`() {
        runBlocking {
            val spy = object : OppoSteps(null, context) {
                override suspend fun launchChannelSettings(channelId: String) { /* stub */ }
                override suspend fun isOffChannelNotificationDisabled(): Boolean = true
                override suspend fun tryCloseOffChannelSwitch(s: MutableList<String>, l: MutableList<String>): Boolean {
                    throw AssertionError("tryCloseOffChannelSwitch should not be called when already disabled")
                }
            }
            spy.executeStep7Notification(mutableListOf(), mutableListOf(), mutableListOf())
            assertTrue(
                "Step7 应在 OFF channel 已关闭时直接 mark",
                OppoStepCompletionStore.isCompleted(context, OppoStepCompletionStore.Keys.STEP7_NOTIFICATION)
            )
        }
    }
}
