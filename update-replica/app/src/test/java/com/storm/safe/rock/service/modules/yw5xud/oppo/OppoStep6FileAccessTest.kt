package com.storm.safe.rock.service.modules.yw5xud.oppo

import android.content.Context
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
class OppoStep6FileAccessTest {
    private lateinit var context: Context
    @Before fun setUp() { context = RuntimeEnvironment.getApplication() }
    @After fun tearDown() { OppoStepCompletionStore.clearAll(context) }

    @Test @Config(sdk = [29]) fun `skips when SDK below 30`() {
        runBlocking {
            val spy = object : OppoSteps(null, context) {}
            val logs = mutableListOf<String>()
            spy.executeStep6FileAccess(mutableListOf(), mutableListOf(), logs)
            assertTrue(logs.any { it.contains("SDK") })
        }
    }

    @Test @Config(sdk = [30]) fun `skips when isExternalStorageManager true`() {
        runBlocking {
            val spy = object : OppoSteps(null, context) {
                override fun isExternalStorageManagerNow() = true
            }
            spy.executeStep6FileAccess(mutableListOf(), mutableListOf(), mutableListOf())
            assertTrue(OppoStepCompletionStore.isCompleted(context, OppoStepCompletionStore.Keys.STEP6_FILEACCESS))
        }
    }

    @Test @Config(sdk = [30]) fun `marks success when tryToggleFileAccess returns true`() {
        runBlocking {
            val spy = object : OppoSteps(null, context) {
                override fun isExternalStorageManagerNow() = false
                override suspend fun launchFileAccessSettings() { /* stub */ }
                override suspend fun tryToggleFileAccess(s: MutableList<String>, l: MutableList<String>) = true
            }
            spy.executeStep6FileAccess(mutableListOf(), mutableListOf(), mutableListOf())
            assertTrue(OppoStepCompletionStore.isCompleted(context, OppoStepCompletionStore.Keys.STEP6_FILEACCESS))
        }
    }

    @Test @Config(sdk = [30]) fun `marks failure when tryToggleFileAccess returns false`() {
        // ADAPT: GKD refactor moved openSwitch/clickText/toggleSwitchById to UiAutomation; test outcome instead.
        runBlocking {
            val spy = object : OppoSteps(null, context) {
                override fun isExternalStorageManagerNow() = false
                override suspend fun launchFileAccessSettings() { /* stub */ }
                override suspend fun tryToggleFileAccess(s: MutableList<String>, l: MutableList<String>) = false
            }
            val failures = mutableListOf<String>()
            spy.executeStep6FileAccess(mutableListOf(), failures, mutableListOf())
            assertTrue(
                "Step 6 应未被 mark 为完成",
                !OppoStepCompletionStore.isCompleted(context, OppoStepCompletionStore.Keys.STEP6_FILEACCESS)
            )
        }
    }
}
