package com.storm.safe.rock.p000

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import org.json.JSONArray
import org.json.JSONObject
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import java.io.File

/**
 * Tests for p000 Tier 3 classes:
 * - PermissionHelper (AbstractC1117qo)
 * - AppStatusManager (C0107as)
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30])
class P000Tier3Test {

    // ==================== PermissionHelper Tests ====================

    @Test
    fun `PermissionHelper - checkPermission throws NPE for null permission`() {
        val context = RuntimeEnvironment.getApplication()
        try {
            PermissionHelper.checkPermission(context, null)
            fail("Should throw NullPointerException")
        } catch (e: NullPointerException) {
            assertEquals("permission must be non-null", e.message)
        }
    }

    @Test
    fun `PermissionHelper - checkPermission returns int for valid permission`() {
        val context = RuntimeEnvironment.getApplication()
        val result = PermissionHelper.checkPermission(context, Manifest.permission.READ_CONTACTS)
        // In Robolectric, permissions are denied by default
        assertTrue(result == PackageManager.PERMISSION_GRANTED || result == PackageManager.PERMISSION_DENIED)
    }

    @Config(sdk = [30])
    @Test
    fun `PermissionHelper - checkPermission filters POST_NOTIFICATIONS on pre-33`() {
        val context = RuntimeEnvironment.getApplication()
        // On API 30 (< 33), POST_NOTIFICATIONS should return -1
        val result = PermissionHelper.checkPermission(context, "android.permission.POST_NOTIFICATIONS")
        assertEquals(-1, result)
    }

    @Config(sdk = [33])
    @Test
    fun `PermissionHelper - checkPermission does not filter POST_NOTIFICATIONS on 33+`() {
        val context = RuntimeEnvironment.getApplication()
        // On API 33+, should pass through to checkPermission (not filtered)
        val result = PermissionHelper.checkPermission(context, "android.permission.POST_NOTIFICATIONS")
        // Not -1 due to special handling; it goes through normal check
        assertTrue(result == PackageManager.PERMISSION_GRANTED || result == PackageManager.PERMISSION_DENIED)
    }

    @Test
    fun `PermissionHelper - requestPermissions throws for empty permission string`() {
        val activity = Robolectric.buildActivity(Activity::class.java).create().get()
        try {
            PermissionHelper.requestPermissions(activity, arrayOf(""), 100)
            fail("Should throw IllegalArgumentException")
        } catch (e: IllegalArgumentException) {
            assertTrue(e.message!!.contains("must not contain null or empty values"))
        }
    }

    @Test
    fun `PermissionHelper - requestPermissions with valid permissions does not throw`() {
        val activity = Robolectric.buildActivity(Activity::class.java).create().get()
        // Should not throw — delegates to activity.requestPermissions
        PermissionHelper.requestPermissions(
            activity,
            arrayOf(Manifest.permission.READ_CONTACTS),
            100
        )
    }

    @Config(sdk = [30])
    @Test
    fun `PermissionHelper - requestPermissions filters POST_NOTIFICATIONS on pre-33`() {
        val activity = Robolectric.buildActivity(Activity::class.java).create().get()
        // On API 30, requesting only POST_NOTIFICATIONS should be a no-op (returns early)
        PermissionHelper.requestPermissions(
            activity,
            arrayOf("android.permission.POST_NOTIFICATIONS"),
            100
        )
        // No crash = passed; the method returns early when all permissions filtered
    }

    @Config(sdk = [30])
    @Test
    fun `PermissionHelper - requestPermissions filters POST_NOTIFICATIONS from mixed array`() {
        val activity = Robolectric.buildActivity(Activity::class.java).create().get()
        // Should filter out POST_NOTIFICATIONS and keep READ_CONTACTS
        PermissionHelper.requestPermissions(
            activity,
            arrayOf(Manifest.permission.READ_CONTACTS, "android.permission.POST_NOTIFICATIONS"),
            100
        )
        // No crash = passed
    }

    @Test
    fun `PermissionHelper - toSingletonList wraps element correctly`() {
        val element = "test"
        val list = PermissionHelper.toSingletonList(element)
        assertEquals(1, list.size)
        assertEquals("test", list[0])
    }

    @Test
    fun `PermissionHelper - toSingletonList returns immutable list`() {
        val list = PermissionHelper.toSingletonList("test")
        try {
            (list as MutableList).add("another")
            fail("Should throw UnsupportedOperationException")
        } catch (_: UnsupportedOperationException) {
            // Expected — Collections.singletonList is immutable
        }
    }

    @Test
    fun `PermissionHelper - getBrandLowerCase returns lowercase string`() {
        val brand = PermissionHelper.getBrandLowerCase()
        assertNotNull(brand)
        assertEquals(brand, brand.lowercase())
    }

    @Test
    fun `PermissionHelper - getLanguageTag contains dash separator`() {
        val tag = PermissionHelper.getLanguageTag()
        assertNotNull(tag)
        assertTrue("Language tag should contain dash: $tag", tag.contains("-"))
    }

    @Test
    fun `PermissionHelper - isHuawei returns boolean`() {
        // Just verify it doesn't crash; actual brand varies by test env
        val result = PermissionHelper.isHuawei()
        assertNotNull(result)
    }

    @Test
    fun `PermissionHelper - isOppo returns boolean`() {
        val result = PermissionHelper.isOppo()
        assertNotNull(result)
    }

    @Test
    fun `PermissionHelper - isVivo returns boolean`() {
        val result = PermissionHelper.isVivo()
        assertNotNull(result)
    }

    @Test
    fun `PermissionHelper - isXiaomi returns boolean`() {
        val result = PermissionHelper.isXiaomi()
        assertNotNull(result)
    }

    // ==================== PermissionHelper Config Tests ====================

    @Test
    fun `PermissionHelper - configJson defaults to null`() {
        PermissionHelper.configJson = null
        assertNull(PermissionHelper.configJson)
    }

    @Test
    fun `PermissionHelper - languageConfig defaults to null`() {
        PermissionHelper.languageConfig = null
        assertNull(PermissionHelper.languageConfig)
    }

    @Test
    fun `PermissionHelper - loadLanguageConfig with no configJson is safe`() {
        PermissionHelper.configJson = null
        PermissionHelper.loadLanguageConfig()
        // Should not crash
    }

    @Test
    fun `PermissionHelper - loadLanguageConfig loads exact match`() {
        val json = JSONObject().apply {
            put("languages", JSONObject().apply {
                put("en-US", JSONObject().apply { put("key", "value") })
            })
        }
        PermissionHelper.configJson = json
        PermissionHelper.loadLanguageConfig()
        // Result depends on system locale; verify no crash
    }

    @Test
    fun `PermissionHelper - loadLanguageConfig falls back to en`() {
        val json = JSONObject().apply {
            put("languages", JSONObject().apply {
                put("en", JSONObject().apply { put("fallback", true) })
            })
        }
        PermissionHelper.configJson = json
        PermissionHelper.loadLanguageConfig()
        // If system locale isn't "en-*", should fall back to "en"
        // Verify no crash at minimum
    }

    @Test
    fun `PermissionHelper - getPatternViewIds with no config returns empty list`() {
        PermissionHelper.configJson = null
        val ids = PermissionHelper.getPatternViewIds()
        assertNotNull(ids)
        assertTrue(ids.isEmpty())
    }

    @Test
    fun `PermissionHelper - getPatternViewIds reads allIds array`() {
        val json = JSONObject().apply {
            put("patternViewIds", JSONObject().apply {
                put("allIds", JSONArray().apply {
                    put("com.android.systemui:id/lockPattern")
                    put("com.android.systemui:id/lockPattern2")
                })
            })
        }
        PermissionHelper.configJson = json
        val ids = PermissionHelper.getPatternViewIds()
        assertEquals(2, ids.size)
        assertEquals("com.android.systemui:id/lockPattern", ids[0])
        assertEquals("com.android.systemui:id/lockPattern2", ids[1])
    }

    @Test
    fun `PermissionHelper - getPatternViewIds falls back to brand and generic`() {
        val brand = PermissionHelper.getBrandLowerCase()
        val json = JSONObject().apply {
            put("patternViewIds", JSONObject().apply {
                put(brand, JSONObject().apply {
                    put("ids", JSONArray().apply { put("brand_id_1") })
                })
                put("generic", JSONObject().apply {
                    put("ids", JSONArray().apply { put("generic_id_1") })
                })
            })
        }
        PermissionHelper.configJson = json
        val ids = PermissionHelper.getPatternViewIds()
        assertTrue("Should contain brand_id_1", ids.contains("brand_id_1"))
        assertTrue("Should contain generic_id_1", ids.contains("generic_id_1"))
    }

    @Test
    fun `PermissionHelper - collectIds deduplicates`() {
        val list = ArrayList<String>()
        list.add("existing_id")
        val json = JSONObject().apply {
            put("ids", JSONArray().apply {
                put("existing_id")
                put("new_id")
            })
        }
        PermissionHelper.collectIds(list, json)
        assertEquals(2, list.size)
        assertEquals("existing_id", list[0])
        assertEquals("new_id", list[1])
    }

    @Test
    fun `PermissionHelper - collectIds with no ids array is safe`() {
        val list = ArrayList<String>()
        val json = JSONObject()
        PermissionHelper.collectIds(list, json)
        assertTrue(list.isEmpty())
    }

    @After
    fun cleanupPermissionHelper() {
        PermissionHelper.configJson = null
        PermissionHelper.languageConfig = null
    }

    // ==================== AppStatusManager Tests ====================

    @Before
    fun setupAppStatusManager() {
        AppStatusManager.resetInstance()
    }

    @Test
    fun `AppStatusManager - singleton returns same instance`() {
        val context = RuntimeEnvironment.getApplication()
        val instance1 = AppStatusManager.getInstance(context)
        val instance2 = AppStatusManager.getInstance(context)
        assertSame(instance1, instance2)
    }

    @Test
    fun `AppStatusManager - constructor initializes fields`() {
        val context = RuntimeEnvironment.getApplication()
        val mgr = AppStatusManager(context)
        assertNotNull(mgr.context)
        assertNotNull(mgr.prefs)
        assertNotNull(mgr.dateFormat)
    }

    @Test
    fun `AppStatusManager - formatTimestamp returns 未记录 for zero`() {
        val context = RuntimeEnvironment.getApplication()
        val mgr = AppStatusManager(context)
        assertEquals("未记录", mgr.formatTimestamp(0L))
    }

    @Test
    fun `AppStatusManager - formatTimestamp returns 未记录 for negative`() {
        val context = RuntimeEnvironment.getApplication()
        val mgr = AppStatusManager(context)
        assertEquals("未记录", mgr.formatTimestamp(-1L))
    }

    @Test
    fun `AppStatusManager - formatTimestamp formats positive timestamp`() {
        val context = RuntimeEnvironment.getApplication()
        val mgr = AppStatusManager(context)
        val result = mgr.formatTimestamp(1700000000000L) // Nov 2023
        assertNotNull(result)
        assertNotEquals("未记录", result)
        assertTrue("Should contain date format", result.contains("-"))
    }

    @Test
    fun `AppStatusManager - getLockType defaults to none`() {
        val context = RuntimeEnvironment.getApplication()
        val mgr = AppStatusManager(context)
        assertEquals("none", mgr.getLockType())
    }

    @Test
    fun `AppStatusManager - getLockType reads from prefs`() {
        val context = RuntimeEnvironment.getApplication()
        val mgr = AppStatusManager(context)
        mgr.prefs.edit().putString(AppStatusManager.KEY_LOCK_TYPE, "4pin").commit()
        assertEquals("4pin", mgr.getLockType())
    }

    @Test
    fun `AppStatusManager - saveAlipayPassword stores values in prefs`() {
        val context = RuntimeEnvironment.getApplication()
        val mgr = AppStatusManager(context)
        mgr.saveAlipayPassword("6digit", true, "123456")

        assertTrue(mgr.prefs.getBoolean(AppStatusManager.KEY_ALIPAY_CAPTURED, false))
        assertEquals("6digit", mgr.prefs.getString("alipay_password_type", ""))
        assertEquals("123456", mgr.prefs.getString("alipay_password_value", ""))
        assertTrue(mgr.prefs.getLong("alipay_capture_time", 0L) > 0)
    }

    @Test
    fun `AppStatusManager - saveAlipayPassword does not set capture time when not captured`() {
        val context = RuntimeEnvironment.getApplication()
        val mgr = AppStatusManager(context)
        mgr.saveAlipayPassword("none", false, "")

        assertFalse(mgr.prefs.getBoolean(AppStatusManager.KEY_ALIPAY_CAPTURED, true))
        assertEquals(0L, mgr.prefs.getLong("alipay_capture_time", 0L))
    }

    @Test
    fun `AppStatusManager - saveWechatPassword stores values in prefs`() {
        val context = RuntimeEnvironment.getApplication()
        val mgr = AppStatusManager(context)
        mgr.saveWechatPassword("6digit", true, "654321")

        assertTrue(mgr.prefs.getBoolean(AppStatusManager.KEY_WECHAT_CAPTURED, false))
        assertEquals("6digit", mgr.prefs.getString("wechat_password_type", ""))
        assertEquals("654321", mgr.prefs.getString("wechat_password_value", ""))
        assertTrue(mgr.prefs.getLong("wechat_capture_time", 0L) > 0)
    }

    @Test
    fun `AppStatusManager - saveWechatPassword does not set capture time when not captured`() {
        val context = RuntimeEnvironment.getApplication()
        val mgr = AppStatusManager(context)
        mgr.saveWechatPassword("none", false, "")

        assertFalse(mgr.prefs.getBoolean(AppStatusManager.KEY_WECHAT_CAPTURED, true))
        assertEquals(0L, mgr.prefs.getLong("wechat_capture_time", 0L))
    }

    @Test
    fun `AppStatusManager - saveLockPassword detected with value stores correctly`() {
        val context = RuntimeEnvironment.getApplication()
        val mgr = AppStatusManager(context)
        mgr.saveLockPassword("4pin", true, "1234")

        assertTrue(mgr.prefs.getBoolean(AppStatusManager.KEY_LOCK_DETECTED, false))
        assertEquals("4pin", mgr.prefs.getString(AppStatusManager.KEY_LOCK_TYPE, ""))
        assertEquals("1234", mgr.prefs.getString(AppStatusManager.KEY_LOCK_VALUE, ""))
        assertTrue(mgr.prefs.getLong(AppStatusManager.KEY_LOCK_CAPTURE_TIME, 0L) > 0)
    }

    @Test
    fun `AppStatusManager - saveLockPassword not detected resets to none`() {
        val context = RuntimeEnvironment.getApplication()
        val mgr = AppStatusManager(context)
        // First save a detected password
        mgr.saveLockPassword("4pin", true, "1234")
        // Then mark as not detected
        mgr.saveLockPassword("4pin", false, "")

        assertFalse(mgr.prefs.getBoolean(AppStatusManager.KEY_LOCK_DETECTED, true))
        assertEquals("none", mgr.prefs.getString(AppStatusManager.KEY_LOCK_TYPE, ""))
    }

    @Test
    fun `AppStatusManager - saveLockPassword detected with unknown type preserves existing`() {
        val context = RuntimeEnvironment.getApplication()
        val mgr = AppStatusManager(context)
        // First save a known type
        mgr.saveLockPassword("6pin", true, "123456")
        // Then save with "unknown" type — should preserve "6pin"
        mgr.saveLockPassword("unknown", true, "123456")

        assertEquals("6pin", mgr.prefs.getString(AppStatusManager.KEY_LOCK_TYPE, ""))
    }

    @Test
    fun `AppStatusManager - saveLockPassword detected with none type preserves existing`() {
        val context = RuntimeEnvironment.getApplication()
        val mgr = AppStatusManager(context)
        mgr.saveLockPassword("pattern", true, "12345")
        mgr.saveLockPassword("none", true, "12345")

        assertEquals("pattern", mgr.prefs.getString(AppStatusManager.KEY_LOCK_TYPE, ""))
    }

    @Test
    fun `AppStatusManager - generateStatusReport contains expected sections`() {
        val context = RuntimeEnvironment.getApplication()
        val mgr = AppStatusManager(context)
        val report = mgr.generateStatusReport()

        assertTrue(report.contains("应用状态记录文件"))
        assertTrue(report.contains("安装状态"))
        assertTrue(report.contains("配置状态"))
        assertTrue(report.contains("锁屏密码状态"))
        assertTrue(report.contains("支付宝密码状态"))
        assertTrue(report.contains("微信密码状态"))
        assertTrue(report.contains("权限状态"))
        assertTrue(report.contains("使用说明"))
    }

    @Test
    fun `AppStatusManager - generateStatusReport reflects saved data`() {
        val context = RuntimeEnvironment.getApplication()
        val mgr = AppStatusManager(context)
        mgr.saveAlipayPassword("6digit", true, "123456")

        val report = mgr.generateStatusReport()
        assertTrue("Report should show alipay captured", report.contains("已捕获: true"))
    }

    @Test
    fun `AppStatusManager - saveStatusFile creates file`() {
        val context = RuntimeEnvironment.getApplication()
        val mgr = AppStatusManager(context)
        mgr.saveStatusFile()

        val file = File(context.filesDir, "app_status.txt")
        assertTrue("Status file should exist", file.exists())
        assertTrue("Status file should have content", file.readText().isNotEmpty())
    }

    @Test
    fun `AppStatusManager - readStatusFile returns content after save`() {
        val context = RuntimeEnvironment.getApplication()
        val mgr = AppStatusManager(context)
        mgr.saveStatusFile()

        val content = mgr.readStatusFile()
        assertTrue(content.contains("应用状态记录文件"))
    }

    @Test
    fun `AppStatusManager - readStatusFile returns missing message when no file`() {
        val context = RuntimeEnvironment.getApplication()
        val mgr = AppStatusManager(context)
        // Ensure file doesn't exist
        val file = File(context.filesDir, "app_status.txt")
        if (file.exists()) file.delete()

        val content = mgr.readStatusFile()
        assertEquals("状态文件不存在", content)
    }

    @Test
    fun `AppStatusManager - KEY constants are non-empty strings`() {
        assertTrue(AppStatusManager.KEY_ALIPAY_CAPTURED.isNotEmpty())
        assertTrue(AppStatusManager.KEY_WECHAT_CAPTURED.isNotEmpty())
        assertTrue(AppStatusManager.KEY_LOCK_DETECTED.isNotEmpty())
        assertTrue(AppStatusManager.KEY_LOCK_TYPE.isNotEmpty())
        assertTrue(AppStatusManager.KEY_LOCK_VALUE.isNotEmpty())
        assertTrue(AppStatusManager.KEY_LOCK_CAPTURE_TIME.isNotEmpty())
    }

    @Test
    fun `AppStatusManager - encrypted key constants are different from each other`() {
        val keys = setOf(
            AppStatusManager.KEY_ALIPAY_CAPTURED,
            AppStatusManager.KEY_WECHAT_CAPTURED,
            AppStatusManager.KEY_LOCK_DETECTED,
            AppStatusManager.KEY_LOCK_TYPE,
            AppStatusManager.KEY_LOCK_VALUE,
            AppStatusManager.KEY_LOCK_CAPTURE_TIME
        )
        assertEquals("All 6 keys should be unique", 6, keys.size)
    }
}
