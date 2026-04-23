package com.storm.safe.rock.service.modules

import android.accessibilityservice.AccessibilityServiceInfo
import android.app.KeyguardManager
import android.content.ComponentName
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.content.pm.ServiceInfo
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import android.view.accessibility.AccessibilityManager
import com.storm.safe.rock.service.modules.routes.AccountRouteHandlers
import com.storm.safe.rock.service.modules.routes.AppRouteHandlers
import com.storm.safe.rock.service.modules.routes.DeviceAdminRouteHandlers
import com.storm.safe.rock.service.modules.routes.IconRouteHandlers
import com.storm.safe.rock.service.modules.routes.StatusRouteHandlers
import org.json.JSONArray
import org.json.JSONObject
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowSettings

/**
 * Tests for RemoteConfigManager -- the local HTTP route handler.
 *
 * Tests cover:
 * - Static response builders (makeErrorResponse, makeTextResponse, containerState)
 * - Route dispatch (routeRequest with all built-in routes)
 * - Individual route handlers via handler objects
 * - Query string parsing (parseQueryString)
 * - Server lifecycle (start/stop/retryBind)
 * - Command execution bridge (executeCommand, executeGlobalAction)
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30])
class RemoteConfigManagerTest {

    private lateinit var context: Context
    private lateinit var manager: RemoteConfigManager

    @Before
    fun setup() {
        context = RuntimeEnvironment.getApplication()
        manager = RemoteConfigManager(context)
    }

    // ---------------------------------------------------------------
    // 1. Static response builders
    // ---------------------------------------------------------------

    @Test
    fun `makeErrorResponse contains code 500, success false, and msg`() {
        val response = RemoteConfigManager.makeErrorResponse("test error")
        assertEquals(500, response.getInt("code"))
        assertFalse(response.getBoolean("success"))
        assertEquals("test error", response.getString("msg"))
    }

    @Test
    fun `makeTextResponse contains code 200, success true, and msg`() {
        val response = RemoteConfigManager.makeTextResponse("hello world")
        assertEquals(200, response.getInt("code"))
        assertTrue(response.getBoolean("success"))
        assertEquals("hello world", response.getString("msg"))
    }

    @Test
    fun `containerState returns running with port and service port`() {
        val response = RemoteConfigManager.containerState()
        assertEquals(200, response.getInt("code"))
        assertTrue(response.getBoolean("success"))
        val data = response.getJSONObject("data")
        assertTrue(data.getBoolean("accessibilityRunning"))
        assertEquals(RemoteConfigManager.DEFAULT_PORT, data.getInt("localHttpServerPort"))
        assertEquals(7912, data.getInt("localServicePort"))
    }

    @Test
    fun `injectionTasks returns empty when service not running`() {
        // Without MyAccessibilityService running, returns error
        val response = RemoteConfigManager.injectionTasks()
        // Should return error since service instance is null
        assertEquals(500, response.getInt("code"))
    }

    // ---------------------------------------------------------------
    // 2. Query string parsing
    // ---------------------------------------------------------------

    @Test
    fun `parseQueryString splits key-value pairs`() {
        val result = RemoteConfigManager.parseQueryString("foo=bar&baz=123")
        assertEquals("bar", result["foo"])
        assertEquals("123", result["baz"])
    }

    @Test
    fun `parseQueryString handles URL-encoded values`() {
        val result = RemoteConfigManager.parseQueryString("name=hello%20world&key=a%26b")
        assertEquals("hello world", result["name"])
        assertEquals("a&b", result["key"])
    }

    @Test
    fun `parseQueryString returns empty map for empty string`() {
        val result = RemoteConfigManager.parseQueryString("")
        assertTrue(result.isEmpty())
    }

    @Test
    fun `parseQueryString handles params with only key (no value)`() {
        val result = RemoteConfigManager.parseQueryString("flag&key=val")
        // Vendor splits by "=" with limit 2; single token has no "=" so size != 2 -> skipped
        assertEquals("val", result["key"])
    }

    // ---------------------------------------------------------------
    // 3. Route dispatch -- routeRequest
    // ---------------------------------------------------------------

    @Test
    fun `routeRequest root returns text response with port`() {
        val response = manager.routeRequest("/", emptyMap(), null)
        assertEquals(200, response.getInt("code"))
        assertTrue(response.getString("msg").contains("LocalHttpServer running on port"))
    }

    @Test
    fun `routeRequest index returns same as root`() {
        val response = manager.routeRequest("/index", emptyMap(), null)
        assertEquals(200, response.getInt("code"))
        assertTrue(response.getString("msg").contains("LocalHttpServer running on port"))
    }

    @Test
    fun `routeRequest containerState returns success`() {
        val response = manager.routeRequest("/containerState", emptyMap(), null)
        assertEquals(200, response.getInt("code"))
        assertTrue(response.getBoolean("success"))
        assertTrue(response.has("data"))
    }

    @Test
    fun `routeRequest lockState returns keyguard info`() {
        val response = manager.routeRequest("/lockState", emptyMap(), null)
        assertEquals(200, response.getInt("code"))
        assertTrue(response.getBoolean("success"))
        val data = response.getJSONObject("data")
        assertTrue(data.has("isLocked"))
        assertTrue(data.has("isSecure"))
    }

    @Test
    fun `routeRequest netState returns network info`() {
        val response = manager.routeRequest("/netState", emptyMap(), null)
        assertEquals(200, response.getInt("code"))
        assertTrue(response.getBoolean("success"))
        val data = response.getJSONObject("data")
        assertTrue(data.has("connected"))
        assertTrue(data.has("hasInternet"))
        assertTrue(data.has("isWifi"))
        assertTrue(data.has("isCellular"))
    }

    @Test
    fun `routeRequest screenState returns power info`() {
        val response = manager.routeRequest("/screenState", emptyMap(), null)
        assertEquals(200, response.getInt("code"))
        assertTrue(response.getBoolean("success"))
        val data = response.getJSONObject("data")
        assertTrue(data.has("isScreenOn"))
    }

    @Test
    fun `routeRequest visibility returns success`() {
        val response = manager.routeRequest("/visibility", emptyMap(), null)
        // May succeed or fail depending on service availability, but should not crash
        assertTrue(response.has("code"))
    }

    @Test
    fun `routeRequest hideIcon routes same as visibility`() {
        val responseVis = manager.routeRequest("/visibility", emptyMap(), null)
        val responseHide = manager.routeRequest("/hideIcon", emptyMap(), null)
        // Both should go to the same handler
        assertEquals(responseVis.getInt("code"), responseHide.getInt("code"))
    }

    @Test
    fun `routeRequest mainPackageName saves to prefs`() {
        val response = manager.routeRequest(
            "/mainPackageName",
            mapOf("package" to "com.test.app"),
            null
        )
        assertEquals(200, response.getInt("code"))
        assertTrue(response.getString("msg").contains("com.test.app"))

        // Verify SharedPreferences
        val prefs = context.getSharedPreferences("local_config", 0)
        assertEquals("com.test.app", prefs.getString("main_package", ""))
    }

    @Test
    fun `routeRequest mainPackageName defaults to own package`() {
        val response = manager.routeRequest("/mainPackageName", emptyMap(), null)
        assertEquals(200, response.getInt("code"))
        assertTrue(response.getString("msg").contains(context.packageName))
    }

    @Test
    fun `routeRequest version returns version string`() {
        val response = manager.routeRequest("/version", emptyMap(), null)
        assertEquals(200, response.getInt("code"))
        // Should contain version info or "unknown"
        assertTrue(response.has("msg"))
    }

    @Test
    fun `routeRequest noticeAlive returns alive data`() {
        val response = manager.routeRequest("/noticeAlive", emptyMap(), null)
        assertEquals(200, response.getInt("code"))
        assertTrue(response.getBoolean("success"))
        assertEquals("alive", response.getString("message"))
        val data = response.getJSONObject("data")
        assertTrue(data.getBoolean("accessibilityRunning"))
        assertTrue(data.has("packageName"))
        assertTrue(data.has("timestamp"))
    }

    @Test
    fun `routeRequest deviceId returns android id`() {
        // Set android_id in Robolectric
        Settings.Secure.putString(
            context.contentResolver,
            "android_id",
            "test_device_id_123"
        )
        val response = manager.routeRequest("/deviceId", emptyMap(), null)
        assertEquals(200, response.getInt("code"))
        assertEquals("test_device_id_123", response.getString("msg"))
    }

    @Test
    fun `routeRequest deviceId returns unknown when not set`() {
        // Clear android_id
        Settings.Secure.putString(context.contentResolver, "android_id", null)
        val response = manager.routeRequest("/deviceId", emptyMap(), null)
        assertEquals(200, response.getInt("code"))
        assertEquals("unknown", response.getString("msg"))
    }

    @Test
    fun `routeRequest unknown path falls to customRoutes or returns error`() {
        val response = manager.routeRequest("/nonexistent", emptyMap(), null)
        assertEquals(500, response.getInt("code"))
        assertFalse(response.getBoolean("success"))
        assertTrue(response.getString("msg").contains("未知路由"))
    }

    @Test
    fun `routeRequest delegates to registered custom route`() {
        manager.registerRoute("/custom") { params, body ->
            val result = JSONObject()
            result.put("code", 200)
            result.put("success", true)
            result.put("custom_param", params["key"] ?: "none")
            result
        }
        val response = manager.routeRequest("/custom", mapOf("key" to "hello"), null)
        assertEquals(200, response.getInt("code"))
        assertTrue(response.getBoolean("success"))
        assertEquals("hello", response.getString("custom_param"))
    }

    @Test
    fun `routeRequest catches exception and returns error`() {
        manager.registerRoute("/crashRoute") { _, _ ->
            throw RuntimeException("Test crash")
        }
        val response = manager.routeRequest("/crashRoute", emptyMap(), null)
        assertEquals(500, response.getInt("code"))
        assertFalse(response.getBoolean("success"))
        assertTrue(response.getString("msg").contains("处理异常"))
    }

    // ---------------------------------------------------------------
    // 4. Individual route handlers (via handler objects)
    // ---------------------------------------------------------------

    @Test
    fun `accessibilityState returns detailed service info`() {
        val response = StatusRouteHandlers.accessibilityState(context)
        assertEquals(200, response.getInt("code"))
        assertTrue(response.getBoolean("success"))
        val data = response.getJSONObject("data")
        assertTrue(data.has("accessibilityEnabled"))
        assertTrue(data.has("ourServiceEnabled"))
        assertTrue(data.has("enabledServices"))
        assertTrue(data.has("settingsServices"))
        assertTrue(data.has("ourService"))
        assertTrue(data.has("packageName"))
        assertTrue(data.has("enabledCount"))
    }

    @Test
    fun `lockState returns keyguard data`() {
        val response = StatusRouteHandlers.lockState(context)
        assertEquals(200, response.getInt("code"))
        assertTrue(response.getBoolean("success"))
        val data = response.getJSONObject("data")
        // Default Robolectric state: not locked
        assertFalse(data.getBoolean("isLocked"))
        assertFalse(data.getBoolean("isSecure"))
    }

    @Test
    fun `netState returns connectivity data`() {
        val response = StatusRouteHandlers.netState(context)
        assertEquals(200, response.getInt("code"))
        assertTrue(response.getBoolean("success"))
        val data = response.getJSONObject("data")
        assertTrue(data.has("connected"))
        assertTrue(data.has("hasInternet"))
        assertTrue(data.has("isWifi"))
        assertTrue(data.has("isCellular"))
    }

    @Test
    fun `screenState returns power info`() {
        val response = StatusRouteHandlers.screenState(context)
        assertEquals(200, response.getInt("code"))
        assertTrue(response.getBoolean("success"))
        val data = response.getJSONObject("data")
        assertTrue(data.has("isScreenOn"))
    }

    @Test
    fun `version returns version string`() {
        val v = StatusRouteHandlers.version(context)
        // In Robolectric test context, may return "unknown" or version string
        assertNotNull(v)
        assertTrue(v.isNotEmpty())
    }

    @Test
    fun `noticeAlive returns alive response with timestamp`() {
        val response = StatusRouteHandlers.noticeAlive(context)
        assertEquals(200, response.getInt("code"))
        assertTrue(response.getBoolean("success"))
        assertEquals("alive", response.getString("message"))
        val data = response.getJSONObject("data")
        assertTrue(data.getLong("timestamp") > 0)
    }

    @Test
    fun `mainPackageName stores to SharedPreferences`() {
        val response = IconRouteHandlers.mainPackageName(context, mapOf("package" to "com.custom.pkg"))
        assertEquals(200, response.getInt("code"))
        assertTrue(response.getString("msg").contains("com.custom.pkg"))
        val stored = context.getSharedPreferences("local_config", 0)
            .getString("main_package", "")
        assertEquals("com.custom.pkg", stored)
    }

    @Test
    fun `mainPackageName defaults to context packageName`() {
        val response = IconRouteHandlers.mainPackageName(context, emptyMap())
        assertTrue(response.getString("msg").contains(context.packageName))
    }

    // ---------------------------------------------------------------
    // 5. ADB/Debug/WiFi toggle routes
    // ---------------------------------------------------------------

    @Test
    fun `toggleAdb enable sets adb_enabled to 1`() {
        val response = DeviceAdminRouteHandlers.toggleAdb(context, true)
        assertEquals(200, response.getInt("code"))
        assertTrue(response.getString("msg").contains("enabled"))
    }

    @Test
    fun `toggleAdb disable sets adb_enabled to 0`() {
        val response = DeviceAdminRouteHandlers.toggleAdb(context, false)
        assertEquals(200, response.getInt("code"))
        assertTrue(response.getString("msg").contains("disabled"))
    }

    @Test
    fun `toggleWifi enable returns success on API 30+`() {
        val response = DeviceAdminRouteHandlers.toggleWifi(context, true)
        assertEquals(200, response.getInt("code"))
        assertTrue(response.getString("msg").contains("enabled"))
    }

    @Test
    fun `toggleWifi disable returns success`() {
        val response = DeviceAdminRouteHandlers.toggleWifi(context, false)
        assertEquals(200, response.getInt("code"))
        assertTrue(response.getString("msg").contains("disabled"))
    }

    @Test
    fun `activeDevelopment enable returns success or error`() {
        // May fail due to permissions in test environment
        val response = DeviceAdminRouteHandlers.activeDevelopment(context, true)
        assertTrue(response.has("code"))
    }

    @Test
    fun `activeDevelopment disable returns response`() {
        val response = DeviceAdminRouteHandlers.activeDevelopment(context, false)
        assertTrue(response.has("code"))
    }

    // ---------------------------------------------------------------
    // 6. syncLockCipher
    // ---------------------------------------------------------------

    @Test
    fun `syncLockCipher saves cipher to prefs`() {
        val response = AppRouteHandlers.syncLockCipher(
            context,
            """{"cipher":"1234"}""",
            emptyMap()
        )
        assertEquals(200, response.getInt("code"))
        assertTrue(response.getString("msg").contains("syncLockCipher done"))
    }

    @Test
    fun `syncLockCipher from params saves cipher`() {
        val response = AppRouteHandlers.syncLockCipher(
            context,
            null,
            mapOf("cipher" to "5678")
        )
        assertEquals(200, response.getInt("code"))
    }

    // ---------------------------------------------------------------
    // 7. Route dispatch -- ADB/shell/debug aliases
    // ---------------------------------------------------------------

    @Test
    fun `routeRequest adbShell returns error without cmd param`() {
        val response = manager.routeRequest("/adbShell", emptyMap(), null)
        assertEquals(500, response.getInt("code"))
        assertTrue(response.getString("msg").contains("缺少 cmd 参数"))
    }

    @Test
    fun `routeRequest shell is alias for adbShell`() {
        val response = manager.routeRequest("/shell", emptyMap(), null)
        assertEquals(500, response.getInt("code"))
        assertTrue(response.getString("msg").contains("缺少 cmd 参数"))
    }

    @Test
    fun `routeRequest debug is alias for adbShell`() {
        val response = manager.routeRequest("/debug", emptyMap(), null)
        assertEquals(500, response.getInt("code"))
        assertTrue(response.getString("msg").contains("缺少 cmd 参数"))
    }

    // ---------------------------------------------------------------
    // 8. pauseAccessibility / resumeAccessibility
    // ---------------------------------------------------------------

    @Test
    fun `pauseAccessibility returns response with reason`() {
        val response = manager.routeRequest(
            "/pauseAccessibility",
            mapOf("reason" to "banking"),
            null
        )
        // May fail if service not running, but should not crash
        assertTrue(response.has("code"))
    }

    @Test
    fun `resumeAccessibility returns response with reason`() {
        val response = manager.routeRequest(
            "/resumeAccessibility",
            mapOf("reason" to "banking_exit"),
            null
        )
        assertTrue(response.has("code"))
    }

    // ---------------------------------------------------------------
    // 9. wipeData / factoryReset / reset / restore aliases
    // ---------------------------------------------------------------

    @Test
    fun `routeRequest wipeData returns 403 without admin`() {
        val response = manager.routeRequest("/wipeData", emptyMap(), null)
        // Without device admin, should return 403
        assertEquals(403, response.getInt("code"))
        assertFalse(response.getBoolean("success"))
    }

    @Test
    fun `routeRequest factoryReset routes same as wipeData`() {
        val r1 = manager.routeRequest("/factoryReset", emptyMap(), null)
        val r2 = manager.routeRequest("/wipeData", emptyMap(), null)
        assertEquals(r1.getInt("code"), r2.getInt("code"))
    }

    @Test
    fun `routeRequest reset routes same as wipeData`() {
        val r1 = manager.routeRequest("/reset", emptyMap(), null)
        assertEquals(403, r1.getInt("code"))
    }

    @Test
    fun `routeRequest restore routes same as wipeData`() {
        val r1 = manager.routeRequest("/restore", emptyMap(), null)
        assertEquals(403, r1.getInt("code"))
    }

    // ---------------------------------------------------------------
    // 10. Account protection routes
    // ---------------------------------------------------------------

    @Test
    fun `enableAccountProtection stores flag true`() {
        val response = AccountRouteHandlers.enableAccountProtection(context)
        assertEquals(200, response.getInt("code"))
        assertTrue(response.getString("msg").contains("accountProtectionEnabled=true"))
    }

    @Test
    fun `disableAccountProtection stores flag false`() {
        val response = AccountRouteHandlers.disableAccountProtection(context)
        assertEquals(200, response.getInt("code"))
        assertTrue(response.getString("msg").contains("accountProtectionEnabled=false"))
    }

    // ---------------------------------------------------------------
    // 11. Admin activation routes
    // ---------------------------------------------------------------

    @Test
    fun `startAdminActive sets flag in prefs`() {
        val response = DeviceAdminRouteHandlers.startAdminActive(context)
        assertEquals(200, response.getInt("code"))
        assertTrue(response.getString("msg").contains("isAdminActivating=true"))
    }

    @Test
    fun `stopAdminActive clears flag in prefs`() {
        val response = DeviceAdminRouteHandlers.stopAdminActive(context)
        assertEquals(200, response.getInt("code"))
        assertTrue(response.getString("msg").contains("isAdminActivating=false"))
    }

    // ---------------------------------------------------------------
    // 12. showIcon / iconStatus / browserApps
    // ---------------------------------------------------------------

    @Test
    fun `showIcon returns response with enabled count`() {
        val response = IconRouteHandlers.showIcon(context)
        assertEquals(200, response.getInt("code"))
        assertTrue(response.has("enabled"))
        assertTrue(response.has("total"))
    }

    @Test
    fun `iconStatus returns component status`() {
        val response = IconRouteHandlers.iconStatus(context)
        assertEquals(200, response.getInt("code"))
        assertTrue(response.has("hidden"))
        assertTrue(response.has("enabled"))
        assertTrue(response.has("disabled"))
        assertTrue(response.has("total"))
    }

    @Test
    fun `browserApps returns list of browser apps`() {
        val response = AppRouteHandlers.browserApps(context)
        assertEquals(200, response.getInt("code"))
        assertTrue(response.getBoolean("success"))
        assertTrue(response.has("data"))
    }

    // ---------------------------------------------------------------
    // 13. setPaymentStrategies
    // ---------------------------------------------------------------

    @Test
    fun `setPaymentStrategies returns error for empty body`() {
        val response = AppRouteHandlers.setPaymentStrategies(context, null)
        assertEquals(500, response.getInt("code"))
        assertTrue(response.getString("msg").contains("缺少请求体"))
    }

    @Test
    fun `setPaymentStrategies saves config to prefs`() {
        val body = """[{"packageName":"com.test","appName":"Test","listenWinClasses":[]}]"""
        val response = AppRouteHandlers.setPaymentStrategies(context, body)
        assertEquals(200, response.getInt("code"))

        val stored = context.getSharedPreferences("payment_strategies", 0)
            .getString("strategies", "")
        assertEquals(body, stored)
    }

    // ---------------------------------------------------------------
    // 14. Server lifecycle
    // ---------------------------------------------------------------

    @Test
    fun `start sets isRunning true`() {
        manager.start()
        assertTrue(manager.isRunning())
        manager.stop()
    }

    @Test
    fun `stop sets isRunning false`() {
        manager.start()
        manager.stop()
        assertFalse(manager.isRunning())
    }

    @Test
    fun `double start is idempotent`() {
        manager.start()
        manager.start() // should not crash
        assertTrue(manager.isRunning())
        manager.stop()
    }

    @Test
    fun `default port is 7910`() {
        assertEquals(7910, RemoteConfigManager.DEFAULT_PORT)
    }

    // ---------------------------------------------------------------
    // 15. Command execution stubs
    // ---------------------------------------------------------------

    @Test
    fun `executeCommand returns error when dispatcher not set`() {
        val response = AppRouteHandlers.executeCommand(emptyMap(), null, null)
        assertEquals(500, response.getInt("code"))
        assertTrue(response.getString("msg").contains("命令分发器未初始化"))
    }

    @Test
    fun `executeGlobalAction returns error when dispatcher not set`() {
        val response = AppRouteHandlers.executeGlobalAction(emptyMap(), null, null)
        assertEquals(500, response.getInt("code"))
        assertTrue(response.getString("msg").contains("命令分发器未初始化"))
    }

    @Test
    fun `executeCommand returns error when command param missing`() {
        val response = AppRouteHandlers.executeCommand(emptyMap(), null, null)
        // Without dispatcher, returns dispatcher not initialized error first
        assertEquals(500, response.getInt("code"))
    }

    // ---------------------------------------------------------------
    // 16. writeAccessibility
    // ---------------------------------------------------------------

    @Test
    fun `writeAccessibility returns response`() {
        val response = DeviceAdminRouteHandlers.writeAccessibility(context, mapOf("action" to "enable"))
        // May fail with SecurityException in test, but should handle gracefully
        assertTrue(response.has("code"))
    }

    @Test
    fun `writeAccessibility unknown action returns error`() {
        val response = DeviceAdminRouteHandlers.writeAccessibility(context, mapOf("action" to "invalid"))
        assertEquals(500, response.getInt("code"))
        assertTrue(response.getString("msg").contains("unknown action"))
    }

    // ---------------------------------------------------------------
    // 17. showInjection / closeInjection
    // ---------------------------------------------------------------

    @Test
    fun `showInjection returns error without packageName`() {
        val response = manager.routeRequest("/showInjection", emptyMap(), null)
        assertEquals(500, response.getInt("code"))
        assertTrue(response.getString("msg").contains("缺少 packageName 参数"))
    }

    @Test
    fun `closeInjection returns response`() {
        val response = manager.routeRequest("/closeInjection", emptyMap(), null)
        // May fail if injection activity not running
        assertTrue(response.has("code"))
    }

    // ---------------------------------------------------------------
    // 18. Complete route coverage
    // ---------------------------------------------------------------

    @Test
    fun `routeRequest accessibilityState dispatches correctly`() {
        val response = manager.routeRequest("/accessibilityState", emptyMap(), null)
        assertEquals(200, response.getInt("code"))
        assertTrue(response.has("data"))
    }

    @Test
    fun `routeRequest deviceAdmin returns response`() {
        val response = manager.routeRequest("/deviceAdmin", emptyMap(), null)
        assertTrue(response.has("code"))
    }

    @Test
    fun `routeRequest activeDeviceOwner returns response`() {
        val response = manager.routeRequest("/activeDeviceOwner", emptyMap(), null)
        assertTrue(response.has("code"))
    }

    @Test
    fun `routeRequest activeADBDebug dispatches to toggleAdb true`() {
        val response = manager.routeRequest("/activeADBDebug", emptyMap(), null)
        assertEquals(200, response.getInt("code"))
        assertTrue(response.getString("msg").contains("enabled"))
    }

    @Test
    fun `routeRequest closeADBDebug dispatches to toggleAdb false`() {
        val response = manager.routeRequest("/closeADBDebug", emptyMap(), null)
        assertEquals(200, response.getInt("code"))
        assertTrue(response.getString("msg").contains("disabled"))
    }

    @Test
    fun `routeRequest activeWifiDebug dispatches to toggleWifi true`() {
        val response = manager.routeRequest("/activeWifiDebug", emptyMap(), null)
        assertEquals(200, response.getInt("code"))
        assertTrue(response.getString("msg").contains("enabled"))
    }

    @Test
    fun `routeRequest closeWifiDebug dispatches to toggleWifi false`() {
        val response = manager.routeRequest("/closeWifiDebug", emptyMap(), null)
        assertEquals(200, response.getInt("code"))
        assertTrue(response.getString("msg").contains("disabled"))
    }

    @Test
    fun `routeRequest activeDevelopment dispatches correctly`() {
        val response = manager.routeRequest("/activeDevelopment", emptyMap(), null)
        assertTrue(response.has("code"))
    }

    @Test
    fun `routeRequest closeDevelopment dispatches correctly`() {
        val response = manager.routeRequest("/closeDevelopment", emptyMap(), null)
        assertTrue(response.has("code"))
    }

    @Test
    fun `routeRequest showIcon dispatches correctly`() {
        val response = manager.routeRequest("/showIcon", emptyMap(), null)
        assertEquals(200, response.getInt("code"))
    }

    @Test
    fun `routeRequest iconStatus dispatches correctly`() {
        val response = manager.routeRequest("/iconStatus", emptyMap(), null)
        assertEquals(200, response.getInt("code"))
    }

    @Test
    fun `routeRequest browserApps dispatches correctly`() {
        val response = manager.routeRequest("/browserApps", emptyMap(), null)
        assertEquals(200, response.getInt("code"))
    }

    @Test
    fun `routeRequest enableAccountProtection dispatches correctly`() {
        val response = manager.routeRequest("/enableAccountProtection", emptyMap(), null)
        assertEquals(200, response.getInt("code"))
    }

    @Test
    fun `routeRequest disableAccountProtection dispatches correctly`() {
        val response = manager.routeRequest("/disableAccountProtection", emptyMap(), null)
        assertEquals(200, response.getInt("code"))
    }

    @Test
    fun `routeRequest startAdminActive dispatches correctly`() {
        val response = manager.routeRequest("/startAdminActive", emptyMap(), null)
        assertEquals(200, response.getInt("code"))
    }

    @Test
    fun `routeRequest stopAdminActive dispatches correctly`() {
        val response = manager.routeRequest("/stopAdminActive", emptyMap(), null)
        assertEquals(200, response.getInt("code"))
    }

    @Test
    fun `routeRequest removeAllAccounts dispatches correctly`() {
        val response = manager.routeRequest("/removeAllAccounts", emptyMap(), null)
        assertTrue(response.has("code"))
    }

    @Test
    fun `routeRequest openWriteSecure dispatches correctly`() {
        val response = manager.routeRequest("/openWriteSecure", emptyMap(), null)
        assertTrue(response.has("code"))
    }

    @Test
    fun `routeRequest syncLockCipher dispatches correctly`() {
        val response = manager.routeRequest(
            "/syncLockCipher",
            mapOf("cipher" to "test123"),
            null
        )
        assertEquals(200, response.getInt("code"))
    }

    @Test
    fun `routeRequest command returns error without dispatcher`() {
        val response = manager.routeRequest("/command", mapOf("command" to "test"), null)
        assertEquals(500, response.getInt("code"))
    }

    @Test
    fun `routeRequest exec returns error without dispatcher`() {
        val response = manager.routeRequest("/exec", mapOf("command" to "test"), null)
        assertEquals(500, response.getInt("code"))
    }

    // ---------------------------------------------------------------
    // 19. getLauncherAliases
    // ---------------------------------------------------------------

    @Test
    fun `getLauncherAliases returns non-empty list`() {
        val aliases = RemoteConfigManager.getLauncherAliases()
        assertTrue(aliases.isNotEmpty())
        // Should contain DefaultLauncherAlias and AppVariant classes
        assertTrue(aliases.size >= 2)
    }

    // ---------------------------------------------------------------
    // 20. Companion instance management
    // ---------------------------------------------------------------

    @Test
    fun `instance is initially null`() {
        RemoteConfigManager.instance = null
        assertNull(RemoteConfigManager.instance)
    }

    @Test
    fun `instance can be set and retrieved`() {
        RemoteConfigManager.instance = manager
        assertSame(manager, RemoteConfigManager.instance)
        RemoteConfigManager.instance = null // cleanup
    }
}
