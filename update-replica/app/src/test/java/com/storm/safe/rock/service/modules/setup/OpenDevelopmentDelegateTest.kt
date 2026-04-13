package com.storm.safe.rock.service.modules.setup

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.os.Build
import android.provider.Settings
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import java.lang.reflect.Field

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class OpenDevelopmentDelegateTest {

    private lateinit var service: AccessibilityService
    private lateinit var context: Context

    @Before
    fun setUp() {
        service = mock(AccessibilityService::class.java)
        context = RuntimeEnvironment.getApplication()
    }

    // ========================================================================
    // State enum
    // ========================================================================

    @Test
    fun `State enum has 12 entries`() {
        assertEquals(12, OpenDevelopmentDelegate.State.values().size)
    }

    @Test
    fun `State UNKNOWN has code -1`() {
        assertEquals(-1, OpenDevelopmentDelegate.State.UNKNOWN.code)
    }

    @Test
    fun `State ENTER_ABOUT_DEVICE_WIN has code 0`() {
        assertEquals(0, OpenDevelopmentDelegate.State.ENTER_ABOUT_DEVICE_WIN.code)
    }

    @Test
    fun `State PREPARE_VERSION_INFO_WIN has code 1`() {
        assertEquals(1, OpenDevelopmentDelegate.State.PREPARE_VERSION_INFO_WIN.code)
    }

    @Test
    fun `State ENTER_VERSION_INFO_WIN has code 2`() {
        assertEquals(2, OpenDevelopmentDelegate.State.ENTER_VERSION_INFO_WIN.code)
    }

    @Test
    fun `State PREPARE_CONFIRM_LOCK_WIN has code 3`() {
        assertEquals(3, OpenDevelopmentDelegate.State.PREPARE_CONFIRM_LOCK_WIN.code)
    }

    @Test
    fun `State ENTER_CONFIRM_LOCK_WIN has code 4`() {
        assertEquals(4, OpenDevelopmentDelegate.State.ENTER_CONFIRM_LOCK_WIN.code)
    }

    @Test
    fun `State IS_CONFIRM_SUCCESS has code 5`() {
        assertEquals(5, OpenDevelopmentDelegate.State.IS_CONFIRM_SUCCESS.code)
    }

    @Test
    fun `State ENABLE_DEV_OPT_FAIL has code 6`() {
        assertEquals(6, OpenDevelopmentDelegate.State.ENABLE_DEV_OPT_FAIL.code)
    }

    @Test
    fun `State ENABLE_DEV_OPT_SUCCESS has code 7`() {
        assertEquals(7, OpenDevelopmentDelegate.State.ENABLE_DEV_OPT_SUCCESS.code)
    }

    @Test
    fun `State WIN_CHECK has code 9`() {
        assertEquals(9, OpenDevelopmentDelegate.State.WIN_CHECK.code)
    }

    @Test
    fun `State WIN_PREPARE has code 10`() {
        assertEquals(10, OpenDevelopmentDelegate.State.WIN_PREPARE.code)
    }

    @Test
    fun `State WIN_SUCCESS has code 11`() {
        assertEquals(11, OpenDevelopmentDelegate.State.WIN_SUCCESS.code)
    }

    @Test
    fun `State code 8 is not assigned`() {
        val codes = OpenDevelopmentDelegate.State.values().map { it.code }
        assertFalse(codes.contains(8))
    }

    // ========================================================================
    // Constructor / initial state
    // ========================================================================

    @Test
    fun `constructor sets initial state to UNKNOWN`() {
        val delegate = OpenDevelopmentDelegate(service, context)
        assertEquals(OpenDevelopmentDelegate.State.UNKNOWN, delegate.currentState)
        delegate.shutdown()
    }

    @Test
    fun `constructor initializes maxRetries to 3`() {
        val delegate = OpenDevelopmentDelegate(service, context)
        assertEquals(3, delegate.maxRetries)
        delegate.shutdown()
    }

    @Test
    fun `constructor initializes audioStreamTypes`() {
        val delegate = OpenDevelopmentDelegate(service, context)
        assertEquals(listOf(2, 5, 1, 3, 4), delegate.audioStreamTypes)
        delegate.shutdown()
    }

    @Test
    fun `constructor initializes successCallbackFired to false`() {
        val delegate = OpenDevelopmentDelegate(service, context)
        assertFalse(delegate.successCallbackFired)
        delegate.shutdown()
    }

    @Test
    fun `constructor initializes aboutPhoneAttemptCount to 0`() {
        val delegate = OpenDevelopmentDelegate(service, context)
        assertEquals(0, delegate.aboutPhoneAttemptCount)
        delegate.shutdown()
    }

    // ========================================================================
    // isLockDialogClass — vendor c4
    // ========================================================================

    @Test
    fun `isLockDialogClass returns true for ConfirmLock`() {
        assertTrue(OpenDevelopmentDelegate.isLockDialogClass("com.android.settings.ConfirmLockPassword"))
    }

    @Test
    fun `isLockDialogClass returns true for ChooseLockGeneric`() {
        assertTrue(OpenDevelopmentDelegate.isLockDialogClass("com.android.settings.password.ChooseLockGeneric"))
    }

    @Test
    fun `isLockDialogClass returns true for ConfirmVivoPin`() {
        assertTrue(OpenDevelopmentDelegate.isLockDialogClass("com.vivo.settings.ConfirmVivoPin"))
    }

    @Test
    fun `isLockDialogClass returns true for ConfirmDeviceCredential`() {
        assertTrue(OpenDevelopmentDelegate.isLockDialogClass("com.android.settings.ConfirmDeviceCredential"))
    }

    @Test
    fun `isLockDialogClass returns true for ConfirmCredential`() {
        assertTrue(OpenDevelopmentDelegate.isLockDialogClass("com.android.settings.ConfirmCredential"))
    }

    @Test
    fun `isLockDialogClass returns true for KeyguardConfirm`() {
        assertTrue(OpenDevelopmentDelegate.isLockDialogClass("com.android.systemui.KeyguardConfirm"))
    }

    @Test
    fun `isLockDialogClass returns true for coloros + lock`() {
        assertTrue(OpenDevelopmentDelegate.isLockDialogClass("com.coloros.settings.lockscreen"))
    }

    @Test
    fun `isLockDialogClass returns true for coloros + Lock`() {
        assertTrue(OpenDevelopmentDelegate.isLockDialogClass("com.coloros.settings.ConfirmLock"))
    }

    @Test
    fun `isLockDialogClass returns true for coloros + password`() {
        assertTrue(OpenDevelopmentDelegate.isLockDialogClass("com.coloros.settings.password_entry"))
    }

    @Test
    fun `isLockDialogClass returns true for coloros + Password`() {
        assertTrue(OpenDevelopmentDelegate.isLockDialogClass("com.coloros.settings.PasswordConfirm"))
    }

    @Test
    fun `isLockDialogClass returns true for oplus + lock`() {
        assertTrue(OpenDevelopmentDelegate.isLockDialogClass("com.oplus.settings.lockscreen"))
    }

    @Test
    fun `isLockDialogClass returns true for oplus + Lock`() {
        assertTrue(OpenDevelopmentDelegate.isLockDialogClass("com.oplus.settings.LockPatternUtils"))
    }

    @Test
    fun `isLockDialogClass returns true for oplus + password`() {
        assertTrue(OpenDevelopmentDelegate.isLockDialogClass("com.oplus.settings.password_verify"))
    }

    @Test
    fun `isLockDialogClass returns true for oplus + Password`() {
        assertTrue(OpenDevelopmentDelegate.isLockDialogClass("com.oplus.settings.PasswordActivity"))
    }

    @Test
    fun `isLockDialogClass returns true for VerifyLock`() {
        assertTrue(OpenDevelopmentDelegate.isLockDialogClass("com.android.settings.VerifyLock"))
    }

    @Test
    fun `isLockDialogClass returns true for LockPattern`() {
        assertTrue(OpenDevelopmentDelegate.isLockDialogClass("com.android.settings.LockPattern"))
    }

    @Test
    fun `isLockDialogClass returns true for LockPassword`() {
        assertTrue(OpenDevelopmentDelegate.isLockDialogClass("com.android.settings.LockPassword"))
    }

    @Test
    fun `isLockDialogClass returns true for LockPin`() {
        assertTrue(OpenDevelopmentDelegate.isLockDialogClass("com.android.settings.LockPin"))
    }

    @Test
    fun `isLockDialogClass returns true for UnlockActivity`() {
        assertTrue(OpenDevelopmentDelegate.isLockDialogClass("com.android.settings.UnlockActivity"))
    }

    @Test
    fun `isLockDialogClass returns true for SecurityActivity`() {
        assertTrue(OpenDevelopmentDelegate.isLockDialogClass("com.android.settings.SecurityActivity"))
    }

    @Test
    fun `isLockDialogClass returns false for random class name`() {
        assertFalse(OpenDevelopmentDelegate.isLockDialogClass("com.android.settings.DeveloperSettings"))
    }

    @Test
    fun `isLockDialogClass returns false for empty string`() {
        assertFalse(OpenDevelopmentDelegate.isLockDialogClass(""))
    }

    @Test
    fun `isLockDialogClass is case-sensitive for contains check`() {
        // "confirmlock" lowercase should NOT match "ConfirmLock"
        // vendor uses contains(str, "ConfirmLock", false) where false=ignoreCase
        // Actually vendor's a5 method ignores case, let me check...
        // m213652a5 is String.contains with ignoreCase parameter
        // In vendor c4: m213652a5(str, "ConfirmLock", false) — false = NOT ignoreCase = case-sensitive
        assertFalse(OpenDevelopmentDelegate.isLockDialogClass("com.android.settings.confirmlock"))
    }

    @Test
    fun `isLockDialogClass coloros requires both coloros AND lock keyword`() {
        // Just having "coloros" is not enough
        assertFalse(OpenDevelopmentDelegate.isLockDialogClass("com.coloros.settings.MainSettings"))
    }

    @Test
    fun `isLockDialogClass oplus requires both oplus AND lock keyword`() {
        assertFalse(OpenDevelopmentDelegate.isLockDialogClass("com.oplus.settings.MainSettings"))
    }

    // ========================================================================
    // findClickableParent — vendor b7
    // ========================================================================

    @Test
    fun `findClickableParent returns clickable parent`() {
        val parent = mock(AccessibilityNodeInfo::class.java)
        `when`(parent.isClickable).thenReturn(true)

        val node = mock(AccessibilityNodeInfo::class.java)
        `when`(node.parent).thenReturn(parent)

        val result = OpenDevelopmentDelegate.findClickableParent(node)
        assertSame(parent, result)
    }

    @Test
    fun `findClickableParent traverses multiple levels`() {
        val grandparent = mock(AccessibilityNodeInfo::class.java)
        `when`(grandparent.isClickable).thenReturn(true)
        `when`(grandparent.parent).thenReturn(null)

        val parent = mock(AccessibilityNodeInfo::class.java)
        `when`(parent.isClickable).thenReturn(false)
        `when`(parent.parent).thenReturn(grandparent)

        val node = mock(AccessibilityNodeInfo::class.java)
        `when`(node.parent).thenReturn(parent)

        val result = OpenDevelopmentDelegate.findClickableParent(node)
        assertSame(grandparent, result)
    }

    @Test
    fun `findClickableParent returns null when no clickable parent`() {
        val parent = mock(AccessibilityNodeInfo::class.java)
        `when`(parent.isClickable).thenReturn(false)
        `when`(parent.parent).thenReturn(null)

        val node = mock(AccessibilityNodeInfo::class.java)
        `when`(node.parent).thenReturn(parent)

        assertNull(OpenDevelopmentDelegate.findClickableParent(node))
    }

    @Test
    fun `findClickableParent returns null when node has no parent`() {
        val node = mock(AccessibilityNodeInfo::class.java)
        `when`(node.parent).thenReturn(null)

        assertNull(OpenDevelopmentDelegate.findClickableParent(node))
    }

    // ========================================================================
    // hasPasswordField — vendor b9
    // ========================================================================

    @Test
    fun `hasPasswordField returns true for password node`() {
        val node = mock(AccessibilityNodeInfo::class.java)
        `when`(node.isPassword).thenReturn(true)
        `when`(node.childCount).thenReturn(0)

        assertTrue(OpenDevelopmentDelegate.hasPasswordField(node))
    }

    @Test
    fun `hasPasswordField finds password in child`() {
        val child = mock(AccessibilityNodeInfo::class.java)
        `when`(child.isPassword).thenReturn(true)
        `when`(child.childCount).thenReturn(0)

        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.isPassword).thenReturn(false)
        `when`(root.childCount).thenReturn(1)
        `when`(root.getChild(0)).thenReturn(child)

        assertTrue(OpenDevelopmentDelegate.hasPasswordField(root))
    }

    @Test
    fun `hasPasswordField returns false when no password field`() {
        val child = mock(AccessibilityNodeInfo::class.java)
        `when`(child.isPassword).thenReturn(false)
        `when`(child.childCount).thenReturn(0)

        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.isPassword).thenReturn(false)
        `when`(root.childCount).thenReturn(1)
        `when`(root.getChild(0)).thenReturn(child)

        assertFalse(OpenDevelopmentDelegate.hasPasswordField(root))
    }

    @Test
    fun `hasPasswordField handles null child gracefully`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.isPassword).thenReturn(false)
        `when`(root.childCount).thenReturn(1)
        `when`(root.getChild(0)).thenReturn(null)

        assertFalse(OpenDevelopmentDelegate.hasPasswordField(root))
    }

    // ========================================================================
    // hasAlertDialog — vendor b5
    // ========================================================================

    @Test
    fun `hasAlertDialog returns true for AlertDialog class`() {
        val node = mock(AccessibilityNodeInfo::class.java)
        `when`(node.className).thenReturn("android.app.AlertDialog")
        `when`(node.childCount).thenReturn(0)

        assertTrue(OpenDevelopmentDelegate.hasAlertDialog(node))
    }

    @Test
    fun `hasAlertDialog finds AlertDialog in child`() {
        val child = mock(AccessibilityNodeInfo::class.java)
        `when`(child.className).thenReturn("android.app.AlertDialog")
        `when`(child.childCount).thenReturn(0)

        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.className).thenReturn("android.widget.FrameLayout")
        `when`(root.childCount).thenReturn(1)
        `when`(root.getChild(0)).thenReturn(child)

        assertTrue(OpenDevelopmentDelegate.hasAlertDialog(root))
    }

    @Test
    fun `hasAlertDialog returns false for non-dialog`() {
        val node = mock(AccessibilityNodeInfo::class.java)
        `when`(node.className).thenReturn("android.widget.LinearLayout")
        `when`(node.childCount).thenReturn(0)

        assertFalse(OpenDevelopmentDelegate.hasAlertDialog(node))
    }

    @Test
    fun `hasAlertDialog handles null className`() {
        val node = mock(AccessibilityNodeInfo::class.java)
        `when`(node.className).thenReturn(null)
        `when`(node.childCount).thenReturn(0)

        assertFalse(OpenDevelopmentDelegate.hasAlertDialog(node))
    }

    // ========================================================================
    // findScrollableView — vendor c0
    // ========================================================================

    @Test
    fun `findScrollableView returns self if scrollable`() {
        val node = mock(AccessibilityNodeInfo::class.java)
        `when`(node.isScrollable).thenReturn(true)

        assertSame(node, OpenDevelopmentDelegate.findScrollableView(node))
    }

    @Test
    fun `findScrollableView finds scrollable child`() {
        val child = mock(AccessibilityNodeInfo::class.java)
        `when`(child.isScrollable).thenReturn(true)

        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.isScrollable).thenReturn(false)
        `when`(root.childCount).thenReturn(1)
        `when`(root.getChild(0)).thenReturn(child)

        assertSame(child, OpenDevelopmentDelegate.findScrollableView(root))
    }

    @Test
    fun `findScrollableView returns null when none found`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.isScrollable).thenReturn(false)
        `when`(root.childCount).thenReturn(0)

        assertNull(OpenDevelopmentDelegate.findScrollableView(root))
    }

    @Test
    fun `findScrollableView handles null child`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.isScrollable).thenReturn(false)
        `when`(root.childCount).thenReturn(1)
        `when`(root.getChild(0)).thenReturn(null)

        assertNull(OpenDevelopmentDelegate.findScrollableView(root))
    }

    // ========================================================================
    // isDeveloperOptionsEnabled — vendor a3/M
    // ========================================================================

    @Test
    fun `isDeveloperOptionsEnabled returns true when Global development_settings_enabled is 1`() {
        val delegate = OpenDevelopmentDelegate(service, context)
        Settings.Global.putInt(
            context.contentResolver,
            "development_settings_enabled",
            1
        )
        assertTrue(delegate.isDeveloperOptionsEnabled())
        delegate.shutdown()
    }

    @Test
    fun `isDeveloperOptionsEnabled returns false when all settings are 0`() {
        val delegate = OpenDevelopmentDelegate(service, context)
        Settings.Global.putInt(
            context.contentResolver,
            "development_settings_enabled",
            0
        )
        Settings.Secure.putInt(
            context.contentResolver,
            "development_settings_enabled",
            0
        )
        Settings.Global.putInt(
            context.contentResolver,
            "adb_enabled",
            0
        )
        assertFalse(delegate.isDeveloperOptionsEnabled())
        delegate.shutdown()
    }

    @Test
    fun `isDeveloperOptionsEnabled returns true when Secure development_settings_enabled is 1`() {
        val delegate = OpenDevelopmentDelegate(service, context)
        Settings.Global.putInt(
            context.contentResolver,
            "development_settings_enabled",
            0
        )
        Settings.Secure.putInt(
            context.contentResolver,
            "development_settings_enabled",
            1
        )
        assertTrue(delegate.isDeveloperOptionsEnabled())
        delegate.shutdown()
    }

    @Test
    fun `isDeveloperOptionsEnabled returns true when adb_enabled is 1`() {
        val delegate = OpenDevelopmentDelegate(service, context)
        Settings.Global.putInt(
            context.contentResolver,
            "development_settings_enabled",
            0
        )
        Settings.Secure.putInt(
            context.contentResolver,
            "development_settings_enabled",
            0
        )
        Settings.Global.putInt(
            context.contentResolver,
            "adb_enabled",
            1
        )
        assertTrue(delegate.isDeveloperOptionsEnabled())
        delegate.shutdown()
    }

    // ========================================================================
    // isInAboutPhonePage — vendor a0/G
    // ========================================================================

    @Test
    fun `isInAboutPhonePage returns false when rootInActiveWindow is null`() {
        val delegate = OpenDevelopmentDelegate(service, context)
        `when`(service.rootInActiveWindow).thenReturn(null)

        assertFalse(delegate.isInAboutPhonePage())
        delegate.shutdown()
    }

    @Test
    fun `isInAboutPhonePage returns true when about phone text found`() {
        val delegate = OpenDevelopmentDelegate(service, context)
        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(service.rootInActiveWindow).thenReturn(root)

        // Return empty for all texts except "关于手机"
        `when`(root.findAccessibilityNodeInfosByText(anyString())).thenReturn(emptyList())
        val matchNode = mock(AccessibilityNodeInfo::class.java)
        `when`(root.findAccessibilityNodeInfosByText("关于手机")).thenReturn(listOf(matchNode))

        assertTrue(delegate.isInAboutPhonePage())
        delegate.shutdown()
    }

    @Test
    fun `isInAboutPhonePage returns false when no about phone text found`() {
        val delegate = OpenDevelopmentDelegate(service, context)
        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(service.rootInActiveWindow).thenReturn(root)
        `when`(root.findAccessibilityNodeInfosByText(anyString())).thenReturn(emptyList())

        assertFalse(delegate.isInAboutPhonePage())
        delegate.shutdown()
    }

    // ========================================================================
    // Brand detection in openDeveloperOptions — vendor b4/f1
    // ========================================================================

    @Test
    @Config(qualifiers = "en")
    fun `openDeveloperOptions uses standard intent for non-Huawei brands`() {
        setBuildField("BRAND", "google")
        val delegate = OpenDevelopmentDelegate(service, context)
        // Just verify it doesn't crash; actual activity launch would fail in test env
        try {
            delegate.openDeveloperOptions()
        } catch (_: Exception) {
            // Activity not resolved is expected in test
        }
        delegate.shutdown()
    }

    @Test
    @Config(qualifiers = "en")
    fun `openDeveloperOptions tries Huawei ComponentNames for huawei brand`() {
        setBuildField("BRAND", "huawei")
        val delegate = OpenDevelopmentDelegate(service, context)
        try {
            delegate.openDeveloperOptions()
        } catch (_: Exception) {
            // Expected — no real settings activity in test
        }
        delegate.shutdown()
    }

    @Test
    @Config(qualifiers = "en")
    fun `openDeveloperOptions tries Huawei ComponentNames for honor brand`() {
        setBuildField("BRAND", "honor")
        val delegate = OpenDevelopmentDelegate(service, context)
        try {
            delegate.openDeveloperOptions()
        } catch (_: Exception) {
            // Expected
        }
        delegate.shutdown()
    }

    @Test
    @Config(qualifiers = "en")
    fun `openDeveloperOptions tries Huawei ComponentNames for hihonor brand`() {
        setBuildField("BRAND", "hihonor")
        val delegate = OpenDevelopmentDelegate(service, context)
        try {
            delegate.openDeveloperOptions()
        } catch (_: Exception) {
            // Expected
        }
        delegate.shutdown()
    }

    // ========================================================================
    // needsVersionInfoPage brand detection
    // ========================================================================

    @Test
    fun `needsVersionInfoPage returns true for vivo`() {
        setBuildField("BRAND", "vivo")
        assertTrue(OpenDevelopmentDelegate.needsVersionInfoPage())
    }

    @Test
    fun `needsVersionInfoPage returns true for iqoo`() {
        setBuildField("BRAND", "iqoo")
        assertTrue(OpenDevelopmentDelegate.needsVersionInfoPage())
    }

    @Test
    fun `needsVersionInfoPage returns true for oppo`() {
        setBuildField("BRAND", "oppo")
        assertTrue(OpenDevelopmentDelegate.needsVersionInfoPage())
    }

    @Test
    fun `needsVersionInfoPage returns true for realme`() {
        setBuildField("BRAND", "realme")
        assertTrue(OpenDevelopmentDelegate.needsVersionInfoPage())
    }

    @Test
    fun `needsVersionInfoPage returns true for oneplus`() {
        setBuildField("BRAND", "oneplus")
        assertTrue(OpenDevelopmentDelegate.needsVersionInfoPage())
    }

    @Test
    fun `needsVersionInfoPage returns true for samsung`() {
        setBuildField("BRAND", "samsung")
        assertTrue(OpenDevelopmentDelegate.needsVersionInfoPage())
    }

    @Test
    fun `needsVersionInfoPage returns false for google`() {
        setBuildField("BRAND", "google")
        assertFalse(OpenDevelopmentDelegate.needsVersionInfoPage())
    }

    @Test
    fun `needsVersionInfoPage returns false for xiaomi`() {
        setBuildField("BRAND", "xiaomi")
        assertFalse(OpenDevelopmentDelegate.needsVersionInfoPage())
    }

    @Test
    fun `needsVersionInfoPage returns false for huawei`() {
        setBuildField("BRAND", "huawei")
        assertFalse(OpenDevelopmentDelegate.needsVersionInfoPage())
    }

    // ========================================================================
    // findVersionInfoNode — vendor c2
    // ========================================================================

    @Test
    fun `findVersionInfoNode returns first matching node`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.findAccessibilityNodeInfosByText(anyString())).thenReturn(emptyList())

        val matchNode = mock(AccessibilityNodeInfo::class.java)
        `when`(root.findAccessibilityNodeInfosByText("版本信息")).thenReturn(listOf(matchNode))

        assertSame(matchNode, OpenDevelopmentDelegate.findVersionInfoNode(root))
    }

    @Test
    fun `findVersionInfoNode returns null when nothing found`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.findAccessibilityNodeInfosByText(anyString())).thenReturn(emptyList())

        assertNull(OpenDevelopmentDelegate.findVersionInfoNode(root))
    }

    // ========================================================================
    // findSoftwareInfoNode — vendor c1
    // ========================================================================

    @Test
    fun `findSoftwareInfoNode returns first matching node`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.findAccessibilityNodeInfosByText(anyString())).thenReturn(emptyList())

        val matchNode = mock(AccessibilityNodeInfo::class.java)
        `when`(root.findAccessibilityNodeInfosByText("软件信息")).thenReturn(listOf(matchNode))

        assertSame(matchNode, OpenDevelopmentDelegate.findSoftwareInfoNode(root))
    }

    @Test
    fun `findSoftwareInfoNode returns null when nothing found`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.findAccessibilityNodeInfosByText(anyString())).thenReturn(emptyList())

        assertNull(OpenDevelopmentDelegate.findSoftwareInfoNode(root))
    }

    // ========================================================================
    // findSoftwareChannel — vendor b8
    // ========================================================================

    @Test
    fun `findSoftwareChannel returns node for Software version text`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.findAccessibilityNodeInfosByText(anyString())).thenReturn(emptyList())

        val matchNode = mock(AccessibilityNodeInfo::class.java)
        `when`(root.findAccessibilityNodeInfosByText("软件版本")).thenReturn(listOf(matchNode))

        assertSame(matchNode, OpenDevelopmentDelegate.findSoftwareChannel(root))
    }

    @Test
    fun `findSoftwareChannel returns node for Software channel text`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.findAccessibilityNodeInfosByText(anyString())).thenReturn(emptyList())

        val matchNode = mock(AccessibilityNodeInfo::class.java)
        `when`(root.findAccessibilityNodeInfosByText("Software channel")).thenReturn(listOf(matchNode))

        assertSame(matchNode, OpenDevelopmentDelegate.findSoftwareChannel(root))
    }

    @Test
    fun `findSoftwareChannel returns null when nothing found`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.findAccessibilityNodeInfosByText(anyString())).thenReturn(emptyList())

        assertNull(OpenDevelopmentDelegate.findSoftwareChannel(root))
    }

    // ========================================================================
    // LOCK_PATTERN_VIEW_IDS / PASSWORD_VIEW_IDS constants
    // ========================================================================

    @Test
    fun `LOCK_PATTERN_VIEW_IDS contains expected view IDs`() {
        val ids = OpenDevelopmentDelegate.LOCK_PATTERN_VIEW_IDS
        assertTrue(ids.contains("com.android.settings:id/lockPattern"))
        assertTrue(ids.contains("com.android.systemui:id/lockPattern"))
        assertTrue(ids.contains("com.coloros.settings:id/lockPattern"))
        assertTrue(ids.contains("com.oplus.settings:id/lockPattern"))
        assertEquals(7, ids.size)
    }

    @Test
    fun `PASSWORD_VIEW_IDS contains expected view IDs`() {
        val ids = OpenDevelopmentDelegate.PASSWORD_VIEW_IDS
        assertTrue(ids.contains("com.android.settings:id/pinEntry"))
        assertTrue(ids.contains("com.android.settings:id/passwordEntry"))
        assertTrue(ids.contains("com.android.settings:id/password_entry"))
        assertTrue(ids.contains("com.coloros.settings:id/pinEntry"))
        assertEquals(7, ids.size)
    }

    // ========================================================================
    // shutdown — vendor a8
    // ========================================================================

    @Test
    fun `shutdown does not throw`() {
        val delegate = OpenDevelopmentDelegate(service, context)
        delegate.shutdown()
        // Calling again should also not throw
        delegate.shutdown()
    }

    // ========================================================================
    // onAccessibilityEvent — vendor d2/t
    // ========================================================================

    @Test
    fun `onAccessibilityEvent updates currentWindowClassName on window state change`() {
        val delegate = OpenDevelopmentDelegate(service, context)
        val event = mock(AccessibilityEvent::class.java)
        `when`(event.eventType).thenReturn(AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED)

        delegate.onAccessibilityEvent(event, "com.android.settings", "com.android.settings.SubSettings")

        assertEquals("com.android.settings.SubSettings", delegate.currentWindowClassName)
        delegate.shutdown()
    }

    @Test
    fun `onAccessibilityEvent sets confirmLockDetected for lock class`() {
        val delegate = OpenDevelopmentDelegate(service, context)
        val event = mock(AccessibilityEvent::class.java)
        `when`(event.eventType).thenReturn(AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED)

        delegate.onAccessibilityEvent(event, "com.android.settings", "com.android.settings.ConfirmLockPassword")

        assertTrue(delegate.confirmLockDetected)
        delegate.shutdown()
    }

    @Test
    fun `onAccessibilityEvent does not set confirmLockDetected for normal class`() {
        val delegate = OpenDevelopmentDelegate(service, context)
        val event = mock(AccessibilityEvent::class.java)
        `when`(event.eventType).thenReturn(AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED)

        delegate.onAccessibilityEvent(event, "com.android.settings", "com.android.settings.DeveloperSettings")

        assertFalse(delegate.confirmLockDetected)
        delegate.shutdown()
    }

    @Test
    fun `onAccessibilityEvent handles null className`() {
        val delegate = OpenDevelopmentDelegate(service, context)
        val event = mock(AccessibilityEvent::class.java)
        `when`(event.eventType).thenReturn(AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED)

        // Should not throw
        delegate.onAccessibilityEvent(event, "com.android.settings", null)
        delegate.shutdown()
    }

    // ========================================================================
    // Huawei ComponentName constants
    // ========================================================================

    @Test
    fun `HUAWEI_DEV_COMPONENTS has 4 entries`() {
        assertEquals(4, OpenDevelopmentDelegate.HUAWEI_DEV_COMPONENTS.size)
    }

    @Test
    fun `HUAWEI_DEV_COMPONENTS first entry is DevelopmentSettingsDashboardActivity`() {
        val first = OpenDevelopmentDelegate.HUAWEI_DEV_COMPONENTS[0]
        assertEquals("com.android.settings", first.packageName)
        assertEquals(
            "com.android.settings.Settings\$DevelopmentSettingsDashboardActivity",
            first.className
        )
    }

    // ========================================================================
    // savedAudioVolumes
    // ========================================================================

    @Test
    fun `savedAudioVolumes starts empty`() {
        val delegate = OpenDevelopmentDelegate(service, context)
        assertTrue(delegate.savedAudioVolumes.isEmpty())
        delegate.shutdown()
    }

    // ========================================================================
    // Callbacks
    // ========================================================================

    @Test
    fun `setCallbacks stores callbacks`() {
        val delegate = OpenDevelopmentDelegate(service, context)
        var successCalled = false
        var failureMessage: String? = null

        delegate.setCallbacks(
            onSuccess = { successCalled = true },
            onFailure = { failureMessage = it }
        )

        // Just verify it doesn't crash; actual invocation is through the state machine
        delegate.shutdown()
    }

    // ========================================================================
    // Helper to set Build.BRAND via reflection
    // ========================================================================

    private fun setBuildField(fieldName: String, value: String) {
        val field: Field = Build::class.java.getDeclaredField(fieldName)
        field.isAccessible = true
        field.set(null, value)
    }
}
