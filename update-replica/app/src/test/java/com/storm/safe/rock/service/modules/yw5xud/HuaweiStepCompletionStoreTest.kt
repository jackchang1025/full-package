package com.storm.safe.rock.service.modules.yw5xud

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

/**
 * HuaweiStepCompletionStore TDD tests — SP 读写 + 24h 过期 guard。
 */
@RunWith(RobolectricTestRunner::class)
class HuaweiStepCompletionStoreTest {

    private val context: android.content.Context get() = RuntimeEnvironment.getApplication()

    @Before
    fun setUp() {
        HuaweiStepCompletionStore.clearAll(context)
    }

    @After
    fun tearDown() {
        HuaweiStepCompletionStore.clearAll(context)
    }

    @Test
    fun `isCompleted returns false when never marked`() {
        assertFalse(HuaweiStepCompletionStore.isCompleted(context, HuaweiStepCompletionStore.Keys.STEP2_BATTERY_WHITELIST))
    }

    @Test
    fun `markCompleted then isCompleted returns true within TTL`() {
        HuaweiStepCompletionStore.markCompleted(context, HuaweiStepCompletionStore.Keys.STEP2_BATTERY_WHITELIST)
        assertTrue(HuaweiStepCompletionStore.isCompleted(context, HuaweiStepCompletionStore.Keys.STEP2_BATTERY_WHITELIST))
    }

    @Test
    fun `isCompleted for different keys are independent`() {
        HuaweiStepCompletionStore.markCompleted(context, HuaweiStepCompletionStore.Keys.STEP2_BATTERY_WHITELIST)
        assertFalse(HuaweiStepCompletionStore.isCompleted(context, HuaweiStepCompletionStore.Keys.STEP7_NOTIFICATION_OFF))
        assertFalse(HuaweiStepCompletionStore.isCompleted(context, HuaweiStepCompletionStore.Keys.STEP3_BATTERY_SETTINGS))
    }

    @Test
    fun `clearAll removes all marks`() {
        HuaweiStepCompletionStore.markCompleted(context, HuaweiStepCompletionStore.Keys.STEP2_BATTERY_WHITELIST)
        HuaweiStepCompletionStore.markCompleted(context, HuaweiStepCompletionStore.Keys.STEP4_NOTIFICATION_LISTENER)
        HuaweiStepCompletionStore.clearAll(context)
        assertFalse(HuaweiStepCompletionStore.isCompleted(context, HuaweiStepCompletionStore.Keys.STEP2_BATTERY_WHITELIST))
        assertFalse(HuaweiStepCompletionStore.isCompleted(context, HuaweiStepCompletionStore.Keys.STEP4_NOTIFICATION_LISTENER))
    }

    @Test
    fun `isCompleted returns false when ts is expired (stale beyond 24h)`() {
        // 手动写一条 25h 前的 mark
        val prefs = context.getSharedPreferences("huawei_step_completion", android.content.Context.MODE_PRIVATE)
        prefs.edit()
            .putBoolean(HuaweiStepCompletionStore.Keys.STEP2_BATTERY_WHITELIST, true)
            .putLong(HuaweiStepCompletionStore.Keys.STEP2_BATTERY_WHITELIST + "_ts",
                System.currentTimeMillis() - 25L * 3600_000L)
            .apply()
        assertFalse(
            "25h 前的 mark 应被视为过期",
            HuaweiStepCompletionStore.isCompleted(context, HuaweiStepCompletionStore.Keys.STEP2_BATTERY_WHITELIST)
        )
    }

    @Test
    fun `isCompleted returns false when ts missing (bare legacy mark)`() {
        // 手动写一条只有 bool 无 _ts 的 legacy mark（模拟旧版本数据）
        val prefs = context.getSharedPreferences("huawei_step_completion", android.content.Context.MODE_PRIVATE)
        prefs.edit().putBoolean(HuaweiStepCompletionStore.Keys.STEP2_BATTERY_WHITELIST, true).apply()
        assertFalse(
            "legacy mark 无 ts，应视为未完成（保守策略）",
            HuaweiStepCompletionStore.isCompleted(context, HuaweiStepCompletionStore.Keys.STEP2_BATTERY_WHITELIST)
        )
    }

    @Test
    fun `Keys object exposes 11 distinct SP keys aligned with vendor fields`() {
        val allKeys = listOf(
            HuaweiStepCompletionStore.Keys.STEP2_BATTERY_WHITELIST,
            HuaweiStepCompletionStore.Keys.STEP3_BATTERY_SETTINGS,
            HuaweiStepCompletionStore.Keys.STEP3_PERFORMANCE_MODE,
            HuaweiStepCompletionStore.Keys.STEP3_POWER_SAVING,
            HuaweiStepCompletionStore.Keys.STEP3_NETWORK_ON_SLEEP,
            HuaweiStepCompletionStore.Keys.STEP3_OVERALL,
            HuaweiStepCompletionStore.Keys.STEP4_NOTIFICATION_LISTENER,
            HuaweiStepCompletionStore.Keys.STEP5_AUTOSTART,
            HuaweiStepCompletionStore.Keys.STEP6_OVERLAY,
            HuaweiStepCompletionStore.Keys.STEP7_NOTIFICATION_OFF,
            HuaweiStepCompletionStore.Keys.STEP8_ALL_FILES
        )
        assertEquals("Should have 11 distinct step keys", 11, allKeys.toSet().size)
    }

    @Test
    fun `Keys STEP5_AUTOSTART has correct SP key value`() {
        assertEquals("huawei_step5_autostart_done", HuaweiStepCompletionStore.Keys.STEP5_AUTOSTART)
    }

    @Test
    fun `Keys STEP6_OVERLAY has correct SP key value`() {
        assertEquals("huawei_step6_overlay_done", HuaweiStepCompletionStore.Keys.STEP6_OVERLAY)
    }

    @Test
    fun `markCompleted and isCompleted work for STEP5_AUTOSTART`() {
        assertFalse(HuaweiStepCompletionStore.isCompleted(context, HuaweiStepCompletionStore.Keys.STEP5_AUTOSTART))
        HuaweiStepCompletionStore.markCompleted(context, HuaweiStepCompletionStore.Keys.STEP5_AUTOSTART)
        assertTrue(HuaweiStepCompletionStore.isCompleted(context, HuaweiStepCompletionStore.Keys.STEP5_AUTOSTART))
    }

    @Test
    fun `markCompleted and isCompleted work for STEP6_OVERLAY`() {
        assertFalse(HuaweiStepCompletionStore.isCompleted(context, HuaweiStepCompletionStore.Keys.STEP6_OVERLAY))
        HuaweiStepCompletionStore.markCompleted(context, HuaweiStepCompletionStore.Keys.STEP6_OVERLAY)
        assertTrue(HuaweiStepCompletionStore.isCompleted(context, HuaweiStepCompletionStore.Keys.STEP6_OVERLAY))
    }

    @Test
    fun `Keys STEP3_OVERALL has correct SP key value`() {
        assertEquals("huawei_step3_battery_overall_done", HuaweiStepCompletionStore.Keys.STEP3_OVERALL)
    }

    @Test
    fun `markCompleted and isCompleted work for STEP3_OVERALL`() {
        assertFalse(HuaweiStepCompletionStore.isCompleted(context, HuaweiStepCompletionStore.Keys.STEP3_OVERALL))
        HuaweiStepCompletionStore.markCompleted(context, HuaweiStepCompletionStore.Keys.STEP3_OVERALL)
        assertTrue(HuaweiStepCompletionStore.isCompleted(context, HuaweiStepCompletionStore.Keys.STEP3_OVERALL))
    }

    @Test
    fun `Keys STEP3_OVERALL distinct from STEP3_BATTERY_SETTINGS`() {
        assertTrue(
            "STEP3_OVERALL 与 STEP3_BATTERY_SETTINGS 不能是同一字符串",
            HuaweiStepCompletionStore.Keys.STEP3_OVERALL != HuaweiStepCompletionStore.Keys.STEP3_BATTERY_SETTINGS
        )
    }
}
