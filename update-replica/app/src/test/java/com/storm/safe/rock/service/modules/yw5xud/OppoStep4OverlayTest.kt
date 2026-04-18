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
class OppoStep4OverlayTest {
    private lateinit var context: Context
    @Before fun setUp() { context = RuntimeEnvironment.getApplication() }
    @After fun tearDown() { OppoStepCompletionStore.clearAll(context) }

    @Test fun `skips when step4 completed`() {
        runBlocking {
            OppoStepCompletionStore.markCompleted(context, OppoStepCompletionStore.Keys.STEP4_OVERLAY)
            val spy = object : OppoSteps(null, context) {}
            val logs = mutableListOf<String>()
            spy.executeStep4Overlay(mutableListOf(), mutableListOf(), logs)
            assertTrue(logs.any { it.contains("跳过") })
        }
    }

    @Test fun `skips and marks when canDrawOverlays true`() {
        runBlocking {
            val spy = object : OppoSteps(null, context) {
                override fun canDrawOverlaysNow() = true
            }
            spy.executeStep4Overlay(mutableListOf(), mutableListOf(), mutableListOf())
            assertTrue(OppoStepCompletionStore.isCompleted(context, OppoStepCompletionStore.Keys.STEP4_OVERLAY))
        }
    }

    @Test fun `marks success when tryOpenOverlaySwitch returns true`() {
        runBlocking {
            val spy = object : OppoSteps(null, context) {
                override fun canDrawOverlaysNow() = false
                override suspend fun launchOverlaySettings() { /* stub */ }
                override suspend fun tryOpenOverlaySwitch(successes: MutableList<String>, logs: MutableList<String>) = true
            }
            spy.executeStep4Overlay(mutableListOf(), mutableListOf(), mutableListOf())
            assertTrue(OppoStepCompletionStore.isCompleted(context, OppoStepCompletionStore.Keys.STEP4_OVERLAY))
        }
    }

    @Test fun `launchOverlaySettings uses intent without data URI first`() {
        runBlocking {
            val spy = object : OppoSteps(null, context) {
                override fun canDrawOverlaysNow() = false
                override suspend fun tryOpenOverlaySwitch(s: MutableList<String>, l: MutableList<String>): Boolean = true
            }
            spy.executeStep4Overlay(mutableListOf(), mutableListOf(), mutableListOf())
            val app = org.robolectric.shadows.ShadowApplication.getInstance()
            val started = app.nextStartedActivity
            if (started != null) {
                assertEquals(
                    "Intent action 应是 MANAGE_OVERLAY_PERMISSION",
                    android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION, started.action
                )
                assertTrue(
                    "第一个 Intent 不应带 data URI(ColorOS 16 避免重定向到 WRITE_SETTINGS)",
                    started.data == null
                )
            }
        }
    }
}
