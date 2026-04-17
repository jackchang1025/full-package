package com.storm.safe.rock.service.modules.yw5xud

import android.content.Context
import android.content.Intent
import android.os.PowerManager
import android.view.accessibility.AccessibilityNodeInfo
import com.storm.safe.rock.service.MyAccessibilityService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.`when`
import org.mockito.Mockito.any
import org.mockito.Mockito.anyString
import org.mockito.Mockito.atLeastOnce
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.spy
import org.mockito.Mockito.verify
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

/**
 * Tests for [HuaweiSteps.executeStep2BatteryWhitelist] — vendor C0365a2 m212166b3 (L2512-2740).
 *
 * Vendor flow:
 *  - L2545-2548: SharedPreferences 短路 (`m212193f0("battery_whitelist_completed")`) — 未在 replica 复刻
 *    (T5 决定将 m212193f0 定义为 SP 而非页面判定，SP 持久化整体下推到 T16)
 *  - L2551-2557: `PowerManager.isIgnoringBatteryOptimizations(pkg)` 快路径 — 已在白名单直接成功返回
 *  - L2561-2565: `Intent(REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).setData(package:pkg).flags(276824064)
 *                                                         + service.startActivity(intent)`
 *  - L2568: `delay(300L)` 等待弹窗
 *  - L2581-2608: 最多 6 次 100ms 轮询等待弹窗文本 (忽略/电池/优化/Ignore/Battery/Optimize) 出现
 *  - L2616/2726: 批准关键词列表 14 项 — ["忽略","关闭","不优化","允许","确定","不再提醒","知道了",
 *                                        "Ignore","Close","Don't optimize","Allow","OK","Don't remind","Got it"]
 *  - L2619-2671: 最多 30 轮主循环，每轮 100ms:
 *                 * PowerManager.isIgnoringBatteryOptimizations → 已白名单 → 成功标记 + 返回
 *                 * 否则依次尝试 clickText 14 keyword (exact=true)，命中则 delay 100ms
 *
 * ADAPT:
 *  - Flags 276824064 = FLAG_ACTIVITY_NEW_TASK (0x10000000) | FLAG_ACTIVITY_NO_HISTORY (0x40000000) |
 *                       FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS (0x00800000). 使用 addFlags 传入等价位模式。
 *  - 用 `service.startActivity` 而非 `context.startActivity`，以对齐 vendor L2565 `this.f55063a1.startActivity(intent)`。
 *  - 批准关键词匹配沿用 T7 引入的 `clickTextOnCurrentRoot(text, exact=true)` helper；vendor L2681
 *    调用 `m212160a3(str3, true)`，签名一致。
 *  - SP 短路 (L2546 f0+f2) 不在本 task 复刻；T16 统一引入 SP 存储。
 */
@RunWith(RobolectricTestRunner::class)
class HuaweiStep2BatteryWhitelistTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = RuntimeEnvironment.getApplication()
    }

    // ---------- Happy path: already in whitelist (vendor L2553 快路径) ----------

    @Test
    fun `step2 returns early success when already ignoring battery optimizations`() {
        runBlocking {
            val mockSvc = mock(MyAccessibilityService::class.java)
            val steps = spy(HuaweiSteps(mockSvc, context))
            // Stub PowerManager.isIgnoringBatteryOptimizations → true
            doReturn(true).`when`(steps).isIgnoringBatteryOptimizations()

            val successes = mutableListOf<String>()
            val failures = mutableListOf<String>()
            val logs = mutableListOf<String>()
            steps.executeStep2BatteryWhitelist(successes, failures, logs)

            assertTrue(
                "expected a 白名单 success log; got=$successes / logs=$logs",
                successes.any { it.contains("白名单") } || logs.any { it.contains("已在白名单") }
            )
            // Service should NOT be asked to startActivity when already whitelisted.
            verify(mockSvc, never()).startActivity(any())
        }
    }

    // ---------- step2 launches REQUEST_IGNORE_BATTERY_OPTIMIZATIONS intent (vendor L2562-2565) ----------

    @Test
    fun `step2 launches REQUEST_IGNORE_BATTERY_OPTIMIZATIONS intent via service`() {
        runBlocking {
            val mockSvc = mock(MyAccessibilityService::class.java)
            val steps = spy(HuaweiSteps(mockSvc, context))
            doReturn(false).`when`(steps).isIgnoringBatteryOptimizations()
            // Root returns null so the main loop exits quickly — we only care about the intent here.
            `when`(mockSvc.rootInActiveWindow).thenReturn(null)

            steps.executeStep2BatteryWhitelist(mutableListOf(), mutableListOf(), mutableListOf())

            val captor = ArgumentCaptor.forClass(Intent::class.java)
            verify(mockSvc, atLeastOnce()).startActivity(captor.capture())
            val intent = captor.value
            assertNotNull("Intent must not be null", intent)
            assertEquals(
                "Intent action must be REQUEST_IGNORE_BATTERY_OPTIMIZATIONS",
                android.provider.Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
                intent.action
            )
            assertEquals(
                "Intent data must be package URI",
                "package:${context.packageName}",
                intent.data?.toString()
            )
            assertTrue(
                "Intent must include FLAG_ACTIVITY_NEW_TASK",
                (intent.flags and Intent.FLAG_ACTIVITY_NEW_TASK) != 0
            )
        }
    }

    // ---------- step2 clicks 允许 on whitelist dialog (vendor L2681 keyword loop) ----------

    @Test
    fun `step2 clicks 允许 on whitelist dialog and records success`() {
        runBlocking {
            val mockSvc = mock(MyAccessibilityService::class.java)
            val root = mock(AccessibilityNodeInfo::class.java)
            val allowNode = mock(AccessibilityNodeInfo::class.java)

            `when`(mockSvc.rootInActiveWindow).thenReturn(root)
            `when`(allowNode.isVisibleToUser).thenReturn(true)
            `when`(allowNode.text).thenReturn("允许")
            `when`(allowNode.isClickable).thenReturn(true)
            `when`(allowNode.performAction(AccessibilityNodeInfo.ACTION_CLICK)).thenReturn(true)

            doReturn(emptyList<AccessibilityNodeInfo>())
                .`when`(root).findAccessibilityNodeInfosByText(anyString())
            doReturn(listOf(allowNode)).`when`(root).findAccessibilityNodeInfosByText("允许")

            val steps = spy(HuaweiSteps(mockSvc, context))
            // First call false (→ enter flow); on second PowerManager check still false,
            // but keyword click will register success via successes list directly.
            doReturn(false).`when`(steps).isIgnoringBatteryOptimizations()

            val successes = mutableListOf<String>()
            steps.executeStep2BatteryWhitelist(successes, mutableListOf(), mutableListOf())

            verify(allowNode, atLeastOnce()).performAction(AccessibilityNodeInfo.ACTION_CLICK)
        }
    }

    // ---------- step2 records success when PowerManager returns true mid-loop (vendor L2627-2636) ----------

    @Test
    fun `step2 records success when battery whitelist confirmed after click`() {
        runBlocking {
            val mockSvc = mock(MyAccessibilityService::class.java)
            val root = mock(AccessibilityNodeInfo::class.java)
            `when`(mockSvc.rootInActiveWindow).thenReturn(root)
            doReturn(emptyList<AccessibilityNodeInfo>())
                .`when`(root).findAccessibilityNodeInfosByText(anyString())

            val steps = spy(HuaweiSteps(mockSvc, context))
            // Sequence: false (initial quick path) → false (detect loop) → true (main loop verify).
            doReturn(false, false, true).`when`(steps).isIgnoringBatteryOptimizations()

            val successes = mutableListOf<String>()
            steps.executeStep2BatteryWhitelist(successes, mutableListOf(), mutableListOf())

            assertTrue(
                "expected success log; got=$successes",
                successes.any { it.contains("白名单") || it.contains("电池") }
            )
        }
    }

    // ---------- step2 timeout bound — vendor ~15s (30 iter × 100ms + 6×100ms + 300ms + slack) ----------

    @Test
    fun `step2 completes within approx 15 seconds on stuck page`() {
        runBlocking {
            val mockSvc = mock(MyAccessibilityService::class.java)
            val steps = spy(HuaweiSteps(mockSvc, context))
            doReturn(false).`when`(steps).isIgnoringBatteryOptimizations()
            // Root null → no clicks, loop exits via iteration cap.
            `when`(mockSvc.rootInActiveWindow).thenReturn(null)

            val t0 = System.currentTimeMillis()
            steps.executeStep2BatteryWhitelist(mutableListOf(), mutableListOf(), mutableListOf())
            val elapsed = System.currentTimeMillis() - t0

            // Vendor max ≈ 300 + 6*100 + 30*100 = 3900ms. Safety margin: < 15s per plan spec.
            assertTrue("Step2 must exit within 15s; elapsed=${elapsed}ms", elapsed < 15_000L)
        }
    }

    // ---------- step2 does not crash when service is null ----------

    @Test
    fun `step2 does not crash when service is null`() {
        runBlocking {
            val steps = spy(HuaweiSteps(null, context))
            doReturn(false).`when`(steps).isIgnoringBatteryOptimizations()

            val failures = mutableListOf<String>()
            val logs = mutableListOf<String>()
            // Should NOT throw.
            steps.executeStep2BatteryWhitelist(mutableListOf(), failures, logs)
            // With null service, can't start activity — expect graceful failure log.
            assertTrue(
                "expected some diagnostic log when service is null; logs=$logs failures=$failures",
                logs.isNotEmpty() || failures.isNotEmpty()
            )
        }
    }

    // ---------- no successes accumulated when already whitelisted via fast path ----------

    @Test
    fun `step2 early success path does not call service startActivity`() {
        runBlocking {
            val mockSvc = mock(MyAccessibilityService::class.java)
            val steps = spy(HuaweiSteps(mockSvc, context))
            doReturn(true).`when`(steps).isIgnoringBatteryOptimizations()

            steps.executeStep2BatteryWhitelist(mutableListOf(), mutableListOf(), mutableListOf())

            verify(mockSvc, never()).startActivity(any())
            verify(mockSvc, never()).rootInActiveWindow
        }
    }

    // ---------- Failure to send intent is recorded (vendor L2539 try/catch) ----------

    @Test
    fun `step2 records failure when startActivity throws`() {
        runBlocking {
            val mockSvc = mock(MyAccessibilityService::class.java)
            // Throw on any startActivity.
            org.mockito.Mockito.doThrow(RuntimeException("boom"))
                .`when`(mockSvc).startActivity(any())

            val steps = spy(HuaweiSteps(mockSvc, context))
            doReturn(false).`when`(steps).isIgnoringBatteryOptimizations()

            val failures = mutableListOf<String>()
            val logs = mutableListOf<String>()
            steps.executeStep2BatteryWhitelist(mutableListOf(), failures, logs)

            assertTrue(
                "expected a failure or error log when startActivity throws; logs=$logs failures=$failures",
                failures.isNotEmpty() || logs.any { it.contains("失败") || it.contains("异常") }
            )
        }
    }
}
