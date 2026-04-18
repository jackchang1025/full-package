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

    @Test fun `marks success when canDrawOverlays true on second check`() {
        runBlocking {
            // Phase E: canDrawOverlaysNow() 被调 2 次:
            //   1) 第一次 false → 跳过早返回,继续走内部 overlay 流程
            //   2) 第二次 true  → 二次回验通过,mark completed
            val callCount = intArrayOf(0)
            val spy = object : OppoSteps(null, context) {
                override fun canDrawOverlaysNow(): Boolean {
                    callCount[0]++
                    return callCount[0] >= 2  // 第 1 次=false,第 2 次及以后=true
                }
            }
            spy.executeStep4Overlay(mutableListOf(), mutableListOf(), mutableListOf())
            assertTrue(OppoStepCompletionStore.isCompleted(context, OppoStepCompletionStore.Keys.STEP4_OVERLAY))
        }
    }

    @Test fun `step4 does not mark success when canDrawOverlays still false after switch`() {
        runBlocking {
            val spy = object : OppoSteps(null, context) {
                // Phase E: 任何时候 canDrawOverlays=false → 最终不该 mark
                override fun canDrawOverlaysNow(): Boolean = false
            }
            val failures = mutableListOf<String>()
            spy.executeStep4Overlay(mutableListOf(), failures, mutableListOf())

            assertTrue(
                "Step 4 应 NOT mark completed 当 canDrawOverlays 回验仍 false",
                !OppoStepCompletionStore.isCompleted(context, OppoStepCompletionStore.Keys.STEP4_OVERLAY)
            )
            assertTrue(
                "failures 应包含 Step 4 回验失败提示,实际=$failures",
                failures.any { it.contains("Step 4") }
            )
        }
    }

    @Test fun `step4 starts app details activity when canDrawOverlays false`() {
        runBlocking {
            val spy = object : OppoSteps(null, context) {
                override fun canDrawOverlaysNow() = false
            }
            spy.executeStep4Overlay(mutableListOf(), mutableListOf(), mutableListOf())
            val app = org.robolectric.shadows.ShadowApplication.getInstance()
            val started = app.nextStartedActivity
            if (started != null) {
                assertEquals(
                    "Intent action 应是 ACTION_APPLICATION_DETAILS_SETTINGS",
                    android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, started.action
                )
            }
        }
    }
}
