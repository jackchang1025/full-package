package com.storm.safe.rock.service.modules.yw5xud.oppo

import android.accessibilityservice.AccessibilityService
import android.content.Context
import com.storm.safe.rock.service.MyAccessibilityService
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class OppoStep8RecentTaskLockTest {
    private lateinit var context: Context
    @Before fun setUp() { context = RuntimeEnvironment.getApplication() }
    @After fun tearDown() { OppoStepCompletionStore.clearAll(context) }

    @Test fun `skips when step8 completed`() {
        runBlocking {
            OppoStepCompletionStore.markCompleted(context, OppoStepCompletionStore.Keys.STEP8_APPLOCK)
            val spy = object : OppoSteps(null, context) {}
            val logs = mutableListOf<String>()
            spy.executeStep8RecentTaskLock(mutableListOf(), mutableListOf(), logs)
            assertTrue(logs.any { it.contains("跳过") })
        }
    }

    @Test fun `triggers GLOBAL_ACTION_RECENTS when not locked`() {
        runBlocking {
            val svc = mock(MyAccessibilityService::class.java)
            val spy = object : OppoSteps(svc, context) {
                override suspend fun tryLockAppCard(s: MutableList<String>, l: MutableList<String>) = true
            }
            spy.executeStep8RecentTaskLock(mutableListOf(), mutableListOf(), mutableListOf())
            verify(svc).performGlobalAction(AccessibilityService.GLOBAL_ACTION_RECENTS)
        }
    }
}
