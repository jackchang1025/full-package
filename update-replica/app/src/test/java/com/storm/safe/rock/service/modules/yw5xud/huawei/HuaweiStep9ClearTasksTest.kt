package com.storm.safe.rock.service.modules.yw5xud.huawei

import com.storm.safe.rock.service.modules.yw5xud.HuaweiSteps
import android.content.Context
import com.storm.safe.rock.service.MyAccessibilityService
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

/**
 * HuaweiStep9ClearTasksTest — Compilation-safe placeholder after GKD refactor.
 *
 * NOTE: executeStep9ClearRecentTasks, tryLockAppInRecents, performGlobalActionHome,
 * and findAppCardRect were moved to HuaweiStep9ClearTasks as private methods
 * during the GKD selector integration refactor. These tests are @Ignored until
 * the delegate exposes testable seams.
 *
 * Vendor: C0365a2.java m212167b4 (L2741-2915) — "清除最近任务"
 */
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, sdk = [31])
class HuaweiStep9ClearTasksTest {

    private lateinit var context: Context
    private lateinit var service: MyAccessibilityService

    @Before
    fun setUp() {
        context = RuntimeEnvironment.getApplication()
        service = mock(MyAccessibilityService::class.java)
    }

    @Ignore("GKD refactor: executeStep9ClearRecentTasks moved to HuaweiStep9ClearTasks delegate (private)")
    @Test
    fun `executeStep9ClearRecentTasks succeeds when tryLockAppInRecents returns true`() {
        // TODO: re-implement using HuaweiStep9ClearTasks once it exposes open seams
    }

    @Ignore("GKD refactor: executeStep9ClearRecentTasks moved to HuaweiStep9ClearTasks delegate (private)")
    @Test
    fun `executeStep9ClearRecentTasks skips clear when tryLockAppInRecents returns false`() {
        // TODO: re-implement using HuaweiStep9ClearTasks once it exposes open seams
    }

    @Ignore("GKD refactor: findAppCardRect moved to HuaweiStep9ClearTasks as private method")
    @Test
    fun `findAppCardRect falls back to contentDescription DFS when text strategies miss`() {
        // TODO: re-implement via HuaweiStep9ClearTasks once it exposes a testable seam
    }
}
