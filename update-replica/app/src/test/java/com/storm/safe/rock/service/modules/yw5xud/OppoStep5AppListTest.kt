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
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
class OppoStep5AppListTest {
    private lateinit var context: Context
    @Before fun setUp() { context = RuntimeEnvironment.getApplication() }
    @After fun tearDown() { OppoStepCompletionStore.clearAll(context) }

    @Test @Config(sdk = [30]) fun `marks immediately when SDK below 31`() {
        runBlocking {
            val spy = object : OppoSteps(null, context) {}
            spy.executeStep5AppList(mutableListOf(), mutableListOf(), mutableListOf())
            assertTrue(OppoStepCompletionStore.isCompleted(context, OppoStepCompletionStore.Keys.STEP5_APPLIST))
        }
    }

    @Test @Config(sdk = [31]) fun `on SDK 31+ clicks appListSwitch`() {
        runBlocking {
            val spy = object : OppoSteps(null, context) {
                override suspend fun tryOpenAppListSwitch(successes: MutableList<String>, logs: MutableList<String>) = true
            }
            spy.executeStep5AppList(mutableListOf(), mutableListOf(), mutableListOf())
            assertTrue(OppoStepCompletionStore.isCompleted(context, OppoStepCompletionStore.Keys.STEP5_APPLIST))
        }
    }

    @Test @Config(sdk = [31]) fun `on SDK 31+ skips UI when QUERY_ALL_PACKAGES already granted`() {
        runBlocking {
            val spy = object : OppoSteps(null, context) {
                override fun hasQueryAllPackagesPermission() = true
            }
            spy.executeStep5AppList(mutableListOf(), mutableListOf(), mutableListOf())
            assertTrue(
                "Step5 应在 QUERY_ALL_PACKAGES 已授予时直接 mark,跳过 UI",
                OppoStepCompletionStore.isCompleted(context, OppoStepCompletionStore.Keys.STEP5_APPLIST)
            )
        }
    }
}
