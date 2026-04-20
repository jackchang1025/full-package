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

/**
 * OPPO Step 2 电池优化分发测试。
 *
 * 不用 Mockito spy + doReturn(Mockito.any() 返回 null,Kotlin non-null 参数 NPE)。
 * 改用 hand-written subclass 模式,与项目 SpyHuaweiSteps / SpyMiuiSteps 一致。
 */
@RunWith(RobolectricTestRunner::class)
class OppoStep2BatteryTest {
    private lateinit var context: Context

    @Before fun setUp() { context = RuntimeEnvironment.getApplication() }
    @After fun tearDown() { OppoStepCompletionStore.clearAll(context) }

    private open class SpyOppoSteps(
        context: Context,
        private val stubbedSubBrand: OppoSubBrand
    ) : OppoSteps(null, context) {
        var oppoCalled = false
        var realmeCalled = false
        var onePlusCalled = false

        override val subBrand: OppoSubBrand get() = stubbedSubBrand

        override suspend fun executeBatteryOppo(
            successes: MutableList<String>,
            failures: MutableList<String>,
            logs: MutableList<String>
        ) { oppoCalled = true }

        override suspend fun executeBatteryRealme(
            successes: MutableList<String>,
            failures: MutableList<String>,
            logs: MutableList<String>
        ) { realmeCalled = true }

        override suspend fun executeBatteryOnePlus(
            successes: MutableList<String>,
            failures: MutableList<String>,
            logs: MutableList<String>
        ) { onePlusCalled = true }
    }

    @Test fun `skips when step2 already completed within 24h`() {
        runBlocking {
            OppoStepCompletionStore.markCompleted(context, OppoStepCompletionStore.Keys.STEP2_BATTERY)
            val spy = SpyOppoSteps(context, OppoSubBrand.OPPO)
            val logs = mutableListOf<String>()

            spy.executeStep2Battery(mutableListOf(), mutableListOf(), logs)

            assertTrue("Expected skip log", logs.any { it.contains("跳过") || it.contains("skip") })
            assertTrue("No dispatch should fire on skip", !spy.oppoCalled && !spy.realmeCalled && !spy.onePlusCalled)
        }
    }

    @Test fun `dispatches to mOppo for OPPO subbrand`() {
        runBlocking {
            val spy = SpyOppoSteps(context, OppoSubBrand.OPPO)
            spy.executeStep2Battery(mutableListOf(), mutableListOf(), mutableListOf())

            assertTrue("executeBatteryOppo should be called", spy.oppoCalled)
            assertTrue("Realme/OnePlus should not be called", !spy.realmeCalled && !spy.onePlusCalled)
        }
    }

    @Test fun `dispatches to mRealme for REALME subbrand`() {
        runBlocking {
            val spy = SpyOppoSteps(context, OppoSubBrand.REALME)
            spy.executeStep2Battery(mutableListOf(), mutableListOf(), mutableListOf())

            assertTrue("executeBatteryRealme should be called", spy.realmeCalled)
            assertTrue("OPPO/OnePlus should not be called", !spy.oppoCalled && !spy.onePlusCalled)
        }
    }

    @Test fun `dispatches to mOnePlus for ONEPLUS subbrand`() {
        runBlocking {
            val spy = SpyOppoSteps(context, OppoSubBrand.ONEPLUS)
            spy.executeStep2Battery(mutableListOf(), mutableListOf(), mutableListOf())

            assertTrue("executeBatteryOnePlus should be called", spy.onePlusCalled)
            assertTrue("OPPO/Realme should not be called", !spy.oppoCalled && !spy.realmeCalled)
        }
    }

    @Test fun `executeBatteryOppo does not mark when isIgnoringBatteryOptimizations false`() {
        runBlocking {
            // ADAPT: GKD refactor moved UI methods to UiAutomation; override isIgnoringBatteryOptimizationsNow only.
            val spy = object : OppoSteps(null, context) {
                override val subBrand: OppoSubBrand get() = OppoSubBrand.OPPO
                override fun isIgnoringBatteryOptimizationsNow(): Boolean = false
            }
            val failures = mutableListOf<String>()
            spy.executeBatteryOppo(mutableListOf(), failures, mutableListOf())

            assertTrue(
                "Step 2 回验 isIgnoringBatteryOptimizations=false 时不应 mark",
                !OppoStepCompletionStore.isCompleted(context, OppoStepCompletionStore.Keys.STEP2_BATTERY)
            )
        }
    }

    @Test fun `executeBatteryOppo marks when isIgnoringBatteryOptimizations true`() {
        runBlocking {
            // ADAPT: GKD refactor moved UI methods to UiAutomation; override isIgnoringBatteryOptimizationsNow only.
            val spy = object : OppoSteps(null, context) {
                override val subBrand: OppoSubBrand get() = OppoSubBrand.OPPO
                override fun isIgnoringBatteryOptimizationsNow(): Boolean = true
            }
            spy.executeBatteryOppo(mutableListOf(), mutableListOf(), mutableListOf())

            assertTrue(
                "Step 2 回验通过时应 mark",
                OppoStepCompletionStore.isCompleted(context, OppoStepCompletionStore.Keys.STEP2_BATTERY)
            )
        }
    }
}
