package com.storm.safe.rock.service.modules.yw5xud.oppo

import android.content.Context
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class OppoStepCompletionStoreTest {
    private lateinit var context: Context

    @Before fun setUp() { context = RuntimeEnvironment.getApplication() }
    @After fun tearDown() { OppoStepCompletionStore.clearAll(context) }

    @Test fun `PREFS_NAME matches vendor oppo_simplified_v6 signature`() {
        // vendor 的强 YARA 特征:SP 文件名必须精确匹配
        assertEquals("oppo_simplified_v6", OppoStepCompletionStore.PREFS_NAME)
    }

    @Test fun `isCompleted returns false on fresh store`() {
        assertFalse(OppoStepCompletionStore.isCompleted(context, OppoStepCompletionStore.Keys.STEP2_BATTERY))
    }

    @Test fun `markCompleted persists and isCompleted reads true`() {
        OppoStepCompletionStore.markCompleted(context, OppoStepCompletionStore.Keys.STEP4_OVERLAY)
        assertTrue(OppoStepCompletionStore.isCompleted(context, OppoStepCompletionStore.Keys.STEP4_OVERLAY))
    }

    @Test fun `clearAll removes all marks`() {
        OppoStepCompletionStore.markCompleted(context, OppoStepCompletionStore.Keys.STEP7_NOTIFICATION)
        OppoStepCompletionStore.clearAll(context)
        assertFalse(OppoStepCompletionStore.isCompleted(context, OppoStepCompletionStore.Keys.STEP7_NOTIFICATION))
    }

    @Test fun `stale mark older than 24h is considered not completed`() {
        val prefs = context.getSharedPreferences(OppoStepCompletionStore.PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit()
            .putBoolean(OppoStepCompletionStore.Keys.STEP3_AUTOSTART, true)
            .putLong(OppoStepCompletionStore.Keys.STEP3_AUTOSTART + "_ts", System.currentTimeMillis() - 25L * 3600_000L)
            .apply()
        assertFalse(OppoStepCompletionStore.isCompleted(context, OppoStepCompletionStore.Keys.STEP3_AUTOSTART))
    }
}
