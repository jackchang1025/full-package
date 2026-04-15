package com.storm.safe.rock.service.modules

import android.content.ComponentName
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.anyInt
import org.mockito.Mockito.anyString
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.Mockito.never
import org.mockito.Mockito.eq
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.util.ReflectionHelpers
import java.lang.reflect.Field

/**
 * Tests for BiometricBypassDelegate — icon hiding/biometric bypass delegate.
 * JADX source: C0328b3.java (231 lines)
 *
 * Tests cover:
 * - getDisguiseComponent: ROM-based AppVariant selection
 * - getSafeStartIntent: returns Intent with iuzxujjtqev component
 * - hideIcon: enables disguise + disables DefaultLauncherAlias
 * - initialize: migration checks + load icon_hidden from prefs
 * - setIconHidden: persists icon_hidden + schedules guard
 * - showIcon: re-enables DefaultLauncherAlias + disables all disguise variants
 * - RomDetector field logic (xj1 mapping)
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30])
class BiometricBypassDelegateTest {

    private lateinit var mockContext: Context
    private lateinit var mockPm: PackageManager
    private lateinit var mockPrefs: SharedPreferences
    private lateinit var mockEditor: SharedPreferences.Editor

    @Before
    fun setUp() {
        mockContext = mock(Context::class.java)
        mockPm = mock(PackageManager::class.java)
        mockPrefs = mock(SharedPreferences::class.java)
        mockEditor = mock(SharedPreferences.Editor::class.java)

        `when`(mockContext.packageManager).thenReturn(mockPm)
        `when`(mockContext.packageName).thenReturn("com.storm.safe.rock")
        `when`(mockContext.getSharedPreferences(anyString(), anyInt())).thenReturn(mockPrefs)
        `when`(mockPrefs.edit()).thenReturn(mockEditor)
        `when`(mockPrefs.getBoolean(anyString(), eq(false))).thenReturn(false)
        `when`(mockEditor.putBoolean(anyString(), org.mockito.Mockito.anyBoolean())).thenReturn(mockEditor)
    }

    // ═══════════════════════════════════════════════════
    // getDisguiseComponent — JADX: a0
    // ═══════════════════════════════════════════════════

    @Test
    fun `getDisguiseComponent returns non-null ComponentName`() {
        // Default device (AOSP) should return AppVariantA
        val delegate = BiometricBypassDelegate(mockContext)
        val component = delegate.getDisguiseComponent()
        // On a generic device, RomDetector defaults to AOSP
        // which returns AppVariantA (the default case in JADX a0)
        assertNotNull(component)
    }

    @Test
    fun `getDisguiseComponent returns null for MIUI when RomDetector says isMiui`() {
        // JADX: if xj1.f61146a0 (isMiui) → cls = null
        val delegate = BiometricBypassDelegate(mockContext)
        val romDetector = delegate.romDetector
        // Use reflection to set isMiui = true
        setField(romDetector, "isMiui", true)
        setField(romDetector, "isHonor", false)
        setField(romDetector, "isHuawei", false)
        setField(romDetector, "isColorOS", false)
        setField(romDetector, "isFuntouchOS", false)
        setField(romDetector, "isOneUI", false)
        val component = delegate.getDisguiseComponent()
        assertNull(component)
    }

    @Test
    fun `getDisguiseComponent returns AppVariantF for FuntouchOS`() {
        val delegate = BiometricBypassDelegate(mockContext)
        val romDetector = delegate.romDetector
        setField(romDetector, "isMiui", false)
        setField(romDetector, "isHonor", false)
        setField(romDetector, "isHuawei", false)
        setField(romDetector, "isColorOS", false)
        setField(romDetector, "isFuntouchOS", true)
        setField(romDetector, "isOneUI", false)
        val component = delegate.getDisguiseComponent()
        assertNotNull(component)
        assertTrue(component!!.className.contains("AppVariantF"))
    }

    @Test
    fun `getDisguiseComponent returns AppVariantH for ColorOS`() {
        val delegate = BiometricBypassDelegate(mockContext)
        val romDetector = delegate.romDetector
        setField(romDetector, "isMiui", false)
        setField(romDetector, "isHonor", false)
        setField(romDetector, "isHuawei", false)
        setField(romDetector, "isColorOS", true)
        setField(romDetector, "isFuntouchOS", false)
        setField(romDetector, "isOneUI", false)
        val component = delegate.getDisguiseComponent()
        assertNotNull(component)
        assertTrue(component!!.className.contains("AppVariantH"))
    }

    @Test
    fun `getDisguiseComponent returns AppVariantA for Honor or Huawei`() {
        // JADX: if isHonor || isHuawei → cls = AppVariantA (default)
        val delegate = BiometricBypassDelegate(mockContext)
        val romDetector = delegate.romDetector
        setField(romDetector, "isMiui", false)
        setField(romDetector, "isHonor", true)
        setField(romDetector, "isHuawei", false)
        setField(romDetector, "isColorOS", false)
        setField(romDetector, "isFuntouchOS", false)
        setField(romDetector, "isOneUI", false)
        val component = delegate.getDisguiseComponent()
        assertNotNull(component)
        assertTrue(component!!.className.contains("AppVariantA"))
    }

    @Test
    fun `getDisguiseComponent returns AppVariantN for OneUI`() {
        val delegate = BiometricBypassDelegate(mockContext)
        val romDetector = delegate.romDetector
        setField(romDetector, "isMiui", false)
        setField(romDetector, "isHonor", false)
        setField(romDetector, "isHuawei", false)
        setField(romDetector, "isColorOS", false)
        setField(romDetector, "isFuntouchOS", false)
        setField(romDetector, "isOneUI", true)
        val component = delegate.getDisguiseComponent()
        assertNotNull(component)
        assertTrue(component!!.className.contains("AppVariantN"))
    }

    // ═══════════════════════════════════════════════════
    // getSafeStartIntent — JADX: a1
    // ═══════════════════════════════════════════════════

    @Test
    fun `getSafeStartIntent returns non-null Intent with iuzxujjtqev component`() {
        val delegate = BiometricBypassDelegate(mockContext)
        val intent = delegate.getSafeStartIntent()
        assertNotNull(intent)
        // Check component is set to iuzxujjtqev
        val component = intent!!.component
        assertNotNull(component)
        assertTrue(component!!.className.contains("iuzxujjtqev"))
        // Check FLAG_ACTIVITY_NEW_TASK
        assertTrue(intent.flags and android.content.Intent.FLAG_ACTIVITY_NEW_TASK != 0)
    }

    // ═══════════════════════════════════════════════════
    // hideIcon — JADX: a2
    // ═══════════════════════════════════════════════════

    @Test
    fun `hideIcon returns ALREADY_HIDDEN when already hidden and not forced`() {
        val delegate = BiometricBypassDelegate(mockContext)
        // Set isHidden via first successful hide
        setField(delegate, "isHidden", true)
        val result = delegate.hideIcon(force = false)
        assertEquals("ALREADY_HIDDEN", result.action)
        assertTrue(result.success)
    }

    @Test
    fun `hideIcon with force re-executes even when hidden`() {
        val delegate = BiometricBypassDelegate(mockContext)
        setField(delegate, "isHidden", true)
        val result = delegate.hideIcon(force = true)
        assertEquals("HIDE", result.action)
        assertTrue(result.success)
    }

    @Test
    fun `hideIcon calls setComponentEnabledSetting on disguise and DefaultLauncherAlias`() {
        val delegate = BiometricBypassDelegate(mockContext)
        val result = delegate.hideIcon()
        assertEquals("HIDE", result.action)
        assertTrue(result.success)
        assertTrue(delegate.isIconHidden())
        // Verify setComponentEnabledSetting was called (enable disguise + disable launcher)
        verify(mockPm, org.mockito.Mockito.atLeast(1)).setComponentEnabledSetting(
            org.mockito.Mockito.any(ComponentName::class.java),
            anyInt(),
            anyInt()
        )
    }

    // ═══════════════════════════════════════════════════
    // showIcon — JADX: a5
    // ═══════════════════════════════════════════════════

    @Test
    fun `showIcon returns ALREADY_SHOWN when not hidden`() {
        val delegate = BiometricBypassDelegate(mockContext)
        val result = delegate.showIcon()
        assertEquals("ALREADY_SHOWN", result.action)
        assertTrue(result.success)
    }

    @Test
    fun `showIcon re-enables DefaultLauncherAlias and disables all variants`() {
        val delegate = BiometricBypassDelegate(mockContext)
        // First hide
        delegate.hideIcon()
        assertTrue(delegate.isIconHidden())
        // Then show
        val result = delegate.showIcon()
        assertEquals("SHOW", result.action)
        assertTrue(result.success)
        assertFalse(delegate.isIconHidden())
        // Verify setComponentEnabledSetting was called multiple times
        // (once for DefaultLauncherAlias enable, 13 times for variants disable,
        // and once for TransparentHelperActivity disable)
        verify(mockPm, org.mockito.Mockito.atLeast(2)).setComponentEnabledSetting(
            org.mockito.Mockito.any(ComponentName::class.java),
            anyInt(),
            anyInt()
        )
    }

    // ═══════════════════════════════════════════════════
    // initialize — JADX: a3
    // ═══════════════════════════════════════════════════

    @Test
    fun `initialize loads icon_hidden false from prefs does not set hidden`() {
        `when`(mockPrefs.getBoolean(eq("icon_hidden"), eq(false))).thenReturn(false)
        val delegate = BiometricBypassDelegate(mockContext)
        delegate.initialize()
        assertFalse(delegate.isIconHidden())
    }

    @Test
    fun `initialize loads icon_hidden true from prefs sets hidden`() {
        `when`(mockPrefs.getBoolean(eq("icon_hidden"), eq(false))).thenReturn(true)
        val delegate = BiometricBypassDelegate(mockContext)
        delegate.initialize()
        assertTrue(delegate.isIconHidden())
    }

    // ═══════════════════════════════════════════════════
    // setIconHidden — JADX: a4
    // ═══════════════════════════════════════════════════

    @Test
    fun `setIconHidden persists to SharedPreferences`() {
        val delegate = BiometricBypassDelegate(mockContext)
        delegate.setIconHidden(true)
        verify(mockEditor).putBoolean("icon_hidden", true)
        verify(mockEditor).apply()
    }

    // ═══════════════════════════════════════════════════
    // RomDetector — JADX: xj1
    // ═══════════════════════════════════════════════════

    @Test
    fun `RomDetector fields are initialized based on Build properties`() {
        val delegate = BiometricBypassDelegate(mockContext)
        val romDetector = delegate.romDetector
        // On test devices, Build.MANUFACTURER/BRAND are typically "robolectric"
        // so all ROM flags should be false
        assertNotNull(romDetector)
    }

    @Test
    fun `IconResult data class equality`() {
        val r1 = BiometricBypassDelegate.IconResult("HIDE", true, "OK")
        val r2 = BiometricBypassDelegate.IconResult("HIDE", true, "OK")
        assertEquals(r1, r2)
    }

    @Test
    fun `disguiseVariants list has 13 entries (A-N excluding M)`() {
        val delegate = BiometricBypassDelegate(mockContext)
        assertEquals(13, delegate.disguiseVariants.size)
    }

    // ═══════════════════════════════════════════════════
    // Helpers
    // ═══════════════════════════════════════════════════

    private fun setField(obj: Any, fieldName: String, value: Any) {
        try {
            val field = obj.javaClass.getDeclaredField(fieldName)
            field.isAccessible = true
            field.set(obj, value)
        } catch (e: NoSuchFieldException) {
            // Try superclass
            val field = obj.javaClass.superclass?.getDeclaredField(fieldName)
            field?.isAccessible = true
            field?.set(obj, value)
        }
    }
}
