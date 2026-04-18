package com.storm.safe.rock.service.modules.yw5xud

import android.content.Context
import com.storm.safe.rock.service.MyAccessibilityService
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy
import org.mockito.Mockito.verify
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class OppoStep2BatteryTest {
    private lateinit var context: Context
    @Before fun setUp() { context = RuntimeEnvironment.getApplication() }
    @After fun tearDown() { OppoStepCompletionStore.clearAll(context) }

    @Test fun `skips when step2 already completed within 24h`() {
        runBlocking {
            OppoStepCompletionStore.markCompleted(context, OppoStepCompletionStore.Keys.STEP2_BATTERY)
            val svc = mock(MyAccessibilityService::class.java)
            val steps = spy(OppoSteps(svc, context))
            val logs = mutableListOf<String>()

            steps.executeStep2Battery(mutableListOf(), mutableListOf(), logs)

            assertTrue("Expected skip log", logs.any { it.contains("跳过") || it.contains("skip") })
        }
    }

    @Test fun `dispatches to mOppo for OPPO subbrand`() {
        runBlocking {
            val svc = mock(MyAccessibilityService::class.java)
            val steps = spy(OppoSteps(svc, context))
            doReturn(OppoSubBrand.OPPO).`when`(steps).subBrand
            doReturn(Unit).`when`(steps).executeBatteryOppo(
                org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any()
            )

            steps.executeStep2Battery(mutableListOf(), mutableListOf(), mutableListOf())

            verify(steps).executeBatteryOppo(
                org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any()
            )
        }
    }

    @Test fun `dispatches to mRealme for REALME subbrand`() {
        runBlocking {
            val svc = mock(MyAccessibilityService::class.java)
            val steps = spy(OppoSteps(svc, context))
            doReturn(OppoSubBrand.REALME).`when`(steps).subBrand
            doReturn(Unit).`when`(steps).executeBatteryRealme(
                org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any()
            )

            steps.executeStep2Battery(mutableListOf(), mutableListOf(), mutableListOf())

            verify(steps).executeBatteryRealme(
                org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any()
            )
        }
    }

    @Test fun `dispatches to mOnePlus for ONEPLUS subbrand`() {
        runBlocking {
            val svc = mock(MyAccessibilityService::class.java)
            val steps = spy(OppoSteps(svc, context))
            doReturn(OppoSubBrand.ONEPLUS).`when`(steps).subBrand
            doReturn(Unit).`when`(steps).executeBatteryOnePlus(
                org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any()
            )

            steps.executeStep2Battery(mutableListOf(), mutableListOf(), mutableListOf())

            verify(steps).executeBatteryOnePlus(
                org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any()
            )
        }
    }
}
