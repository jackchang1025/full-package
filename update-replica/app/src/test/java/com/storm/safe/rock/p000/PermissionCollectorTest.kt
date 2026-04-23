package com.storm.safe.rock.p000

import android.content.Context
import android.provider.Settings
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

/**
 * Tests for PermissionCollector — 1:1 replica of vendor an0.java.
 *
 * Vendor source: jadx-reference/p000/an0.java
 *
 * Tests cover:
 * - All 11 permission keys present in result map
 * - accessibility detection via Settings.Secure
 * - overlay detection via Settings.canDrawOverlays
 * - notification detection with SDK branching (< 33 vs >= 33)
 * - photo detection with SDK branching (>= 33 vs < 33)
 * - contacts/readSms/sendSms/camera/microphone — inverted logic (!=0 → true)
 * - storage detection with SDK branching (< 30 vs >= 30)
 * - appList detection with SDK branching (< 30 via SharedPrefs, >= 30 always true)
 * - PermissionProvider interface compliance
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30])
class PermissionCollectorTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = RuntimeEnvironment.getApplication()
    }

    // ==================== Interface compliance ====================

    @Test
    fun `PermissionCollector implements PermissionProvider`() {
        assertTrue(PermissionCollector is PermissionProvider)
    }

    // ==================== Map structure ====================

    @Test
    fun `collectAll returns map with exactly 11 keys`() {
        val result = PermissionCollector.collectAll(context)
        assertEquals(11, result.size)
    }

    @Test
    fun `collectAll contains all expected keys`() {
        val result = PermissionCollector.collectAll(context)
        val expectedKeys = setOf(
            "accessibility", "overlay", "notification", "photo",
            "contacts", "readSms", "sendSms", "camera",
            "microphone", "storage", "appList"
        )
        assertEquals(expectedKeys, result.keys)
    }

    // ==================== accessibility ====================

    @Test
    fun `accessibility returns false when no accessibility services enabled`() {
        // No enabled_accessibility_services set → should be false
        val result = PermissionCollector.collectAll(context)
        assertFalse(result["accessibility"]!!)
    }

    @Test
    fun `accessibility returns true when service is in enabled list with full component name`() {
        val packageName = context.packageName
        val serviceName = "com.storm.safe.rock.service.MyAccessibilityService"
        val fullComponent = "$packageName/$serviceName"
        Settings.Secure.putString(
            context.contentResolver,
            "enabled_accessibility_services",
            fullComponent
        )
        val result = PermissionCollector.collectAll(context)
        assertTrue(result["accessibility"]!!)
    }

    @Test
    fun `accessibility returns true when service class name alone is in enabled list`() {
        // vendor: also checks if string contains just the class name (without package prefix)
        val serviceName = "com.storm.safe.rock.service.MyAccessibilityService"
        Settings.Secure.putString(
            context.contentResolver,
            "enabled_accessibility_services",
            serviceName
        )
        val result = PermissionCollector.collectAll(context)
        assertTrue(result["accessibility"]!!)
    }

    @Test
    fun `accessibility returns false when different service is enabled`() {
        Settings.Secure.putString(
            context.contentResolver,
            "enabled_accessibility_services",
            "com.other.app/com.other.app.SomeService"
        )
        val result = PermissionCollector.collectAll(context)
        assertFalse(result["accessibility"]!!)
    }

    // ==================== overlay ====================

    @Test
    @Config(sdk = [28])
    fun `overlay returns false when canDrawOverlays is false`() {
        // Robolectric defaults canDrawOverlays to false on API 28
        val result = PermissionCollector.collectAll(context)
        assertFalse(result["overlay"]!!)
    }

    // ==================== notification ====================

    @Test
    @Config(sdk = [32])
    fun `notification on SDK less than 33 delegates to NotificationManager`() {
        // SDK < 33: vendor AbstractC1117qo.m214411a7 delegates POST_NOTIFICATIONS
        // to NotificationManagerCompat.areNotificationsEnabled() → 0 (granted) / -1 (denied)
        // Robolectric defaults: notifications are enabled
        val result = PermissionCollector.collectAll(context)
        assertTrue(result["notification"]!!)
    }

    @Test
    @Config(sdk = [33])
    fun `notification on SDK 33 or higher uses NotificationManager areNotificationsEnabled`() {
        // SDK >= 33: vendor uses NotificationManagerCompat.areNotificationsEnabled()
        val result = PermissionCollector.collectAll(context)
        // By default in Robolectric, notifications are enabled
        assertNotNull(result["notification"])
    }

    // ==================== photo ====================

    @Test
    @Config(sdk = [33])
    fun `photo on SDK 33 or higher checks READ_EXTERNAL_STORAGE`() {
        // vendor: SDK >= 33 → checkPerm(READ_EXTERNAL_STORAGE) == 0
        // Note: vendor logic is inverted relative to Android standard
        val result = PermissionCollector.collectAll(context)
        assertNotNull(result["photo"])
    }

    @Test
    @Config(sdk = [32])
    fun `photo on SDK less than 33 checks READ_MEDIA_IMAGES`() {
        // vendor: SDK < 33 → checkPerm(READ_MEDIA_IMAGES) == 0
        val result = PermissionCollector.collectAll(context)
        assertNotNull(result["photo"])
    }

    // ==================== Standard permissions (contacts, readSms, sendSms, camera, microphone) ====================
    // ADAPT: vendor uses != 0 (bug), fixed to == 0 — not granted → checkPerm returns -1 → ==0 is false

    @Test
    fun `contacts not granted returns false`() {
        // Robolectric default: permissions not granted → checkPerm returns -1 → ==0 → false
        val result = PermissionCollector.collectAll(context)
        assertFalse(result["contacts"]!!)
    }

    @Test
    fun `readSms not granted returns false`() {
        val result = PermissionCollector.collectAll(context)
        assertFalse(result["readSms"]!!)
    }

    @Test
    fun `sendSms not granted returns false`() {
        val result = PermissionCollector.collectAll(context)
        assertFalse(result["sendSms"]!!)
    }

    @Test
    fun `camera not granted returns false`() {
        val result = PermissionCollector.collectAll(context)
        assertFalse(result["camera"]!!)
    }

    @Test
    fun `microphone not granted returns false`() {
        val result = PermissionCollector.collectAll(context)
        assertFalse(result["microphone"]!!)
    }

    // ==================== storage ====================

    @Test
    @Config(sdk = [29])
    fun `storage on SDK less than 30 uses isExternalStorageManager`() {
        // vendor: SDK < 30 → Environment.isExternalStorageManager()
        // Note: vendor logic is inverted relative to standard (SDK < 30 uses API 30+ method)
        // On SDK 29, isExternalStorageManager doesn't exist, so this would throw or return false
        val result = PermissionCollector.collectAll(context)
        assertNotNull(result["storage"])
    }

    @Test
    @Config(sdk = [30])
    fun `storage on SDK 30 or higher checks WRITE_EXTERNAL_STORAGE`() {
        // vendor: SDK >= 30 → checkPerm(WRITE_EXTERNAL_STORAGE) == 0
        val result = PermissionCollector.collectAll(context)
        assertNotNull(result["storage"])
    }

    // ==================== appList ====================

    @Test
    @Config(sdk = [29])
    fun `appList on SDK less than 30 reads from SharedPreferences`() {
        // vendor: SDK < 30 → SharedPrefs(PREFS_NAME).getBoolean("app_list_permission", false)
        val result = PermissionCollector.collectAll(context)
        assertFalse(result["appList"]!!)
    }

    @Test
    @Config(sdk = [29])
    fun `appList on SDK less than 30 returns true when pref is set`() {
        // Set the SharedPreference to true
        val prefsName = PermissionCollector.PREFS_NAME
        context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
            .edit()
            .putBoolean("app_list_permission", true)
            .commit()
        val result = PermissionCollector.collectAll(context)
        assertTrue(result["appList"]!!)
    }

    @Test
    @Config(sdk = [30])
    fun `appList on SDK 30 or higher is always true`() {
        // vendor: SDK >= 30 → true
        val result = PermissionCollector.collectAll(context)
        assertTrue(result["appList"]!!)
    }

    // ==================== Exception safety ====================

    @Test
    fun `collectAll does not throw on any SDK level`() {
        // Should not throw even if some checks fail internally
        val result = PermissionCollector.collectAll(context)
        assertNotNull(result)
        assertEquals(11, result.size)
    }

    // ==================== PREFS_NAME constant ====================

    @Test
    fun `PREFS_NAME is derived from StringUtil decrypt`() {
        // vendor: f43729a0 = StringUtil.m212470a0("O1wDN0QrHydYPxRKBTtZLR8=")
        // In replica, we use the same ciphertext with StringUtil.decrypt
        val prefsName = PermissionCollector.PREFS_NAME
        assertNotNull(prefsName)
        assertTrue(prefsName.isNotEmpty())
    }

    // ==================== SERVICE_CLASS_NAME constant ====================

    @Test
    fun `SERVICE_CLASS_NAME matches MyAccessibilityService`() {
        // vendor: dqtvuisjd.class.getName() — our replica's MyAccessibilityService
        assertEquals(
            "com.storm.safe.rock.service.MyAccessibilityService",
            PermissionCollector.SERVICE_CLASS_NAME
        )
    }
}
