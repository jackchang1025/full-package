package com.storm.safe.rock.service.modules.yw5xud.oppo

import android.content.Context
import com.storm.safe.rock.service.MyAccessibilityService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class OppoExecuteAllTest {
    private lateinit var context: Context
    @Before fun setUp() { context = RuntimeEnvironment.getApplication() }

    /** 9 step counter helper subclass */
    private open class SpyOppoSteps(
        svc: MyAccessibilityService?,
        context: Context,
        val throwAtStep2: Boolean = false
    ) : OppoSteps(svc, context) {
        var s1 = 0; var s2 = 0; var s3 = 0; var s4 = 0; var s5 = 0; var s6 = 0; var s7 = 0; var s8 = 0; var s9 = 0

        override suspend fun executeStep1BasicPermissions(successes: MutableList<String>, failures: MutableList<String>, logs: MutableList<String>) { s1++ }
        override suspend fun executeStep2Battery(successes: MutableList<String>, failures: MutableList<String>, logs: MutableList<String>) {
            s2++
            if (throwAtStep2) throw RuntimeException("Step2 throws")
        }
        override suspend fun executeStep3AutoStart(successes: MutableList<String>, failures: MutableList<String>, logs: MutableList<String>) { s3++ }
        override suspend fun executeStep4Overlay(successes: MutableList<String>, failures: MutableList<String>, logs: MutableList<String>) { s4++ }
        override suspend fun executeStep5AppList(successes: MutableList<String>, failures: MutableList<String>, logs: MutableList<String>) { s5++ }
        override suspend fun executeStep6FileAccess(successes: MutableList<String>, failures: MutableList<String>, logs: MutableList<String>) { s6++ }
        override suspend fun executeStep7Notification(successes: MutableList<String>, failures: MutableList<String>, logs: MutableList<String>) { s7++ }
        override suspend fun executeStep8RecentTaskLock(successes: MutableList<String>, failures: MutableList<String>, logs: MutableList<String>) { s8++ }
        override suspend fun executeStep9ReturnHome(successes: MutableList<String>, failures: MutableList<String>, logs: MutableList<String>) { s9++ }
    }

    @Test fun `executeAll invokes all 9 steps in order`() {
        runBlocking {
            val svc = mock(MyAccessibilityService::class.java)
            val spy = SpyOppoSteps(svc, context)
            spy.executeAll(mutableListOf(), mutableListOf(), mutableListOf())
            assertTrue("Step1-9 all called once (actual: s1=${spy.s1} s2=${spy.s2} ... s9=${spy.s9})",
                spy.s1 == 1 && spy.s2 == 1 && spy.s3 == 1 && spy.s4 == 1 && spy.s5 == 1 &&
                spy.s6 == 1 && spy.s7 == 1 && spy.s8 == 1 && spy.s9 == 1)
        }
    }

    @Test fun `executeAll continues on step failure (Step3 through Step9 still run)`() {
        runBlocking {
            val svc = mock(MyAccessibilityService::class.java)
            val spy = SpyOppoSteps(svc, context, throwAtStep2 = true)
            val failures = mutableListOf<String>()
            spy.executeAll(mutableListOf(), failures, mutableListOf())
            assertTrue("Step3-9 should still run", spy.s3 == 1 && spy.s9 == 1)
            assertTrue("failures should include Step2", failures.any { it.contains("Step2") })
        }
    }

    @Test fun `executeAll returns without running any step when service is null`() {
        runBlocking {
            val spy = SpyOppoSteps(null, context)
            val failures = mutableListOf<String>()
            spy.executeAll(mutableListOf(), failures, mutableListOf())
            assertTrue("No step should run", spy.s1 == 0 && spy.s9 == 0)
            assertTrue("failures should mention service/未绑定",
                failures.any { it.contains("service") || it.contains("未绑定") })
        }
    }
}
