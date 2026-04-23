package com.storm.safe.rock.service.modules.routes

import android.content.Context
import android.content.SharedPreferences
import android.content.ContentResolver
import android.provider.Settings
import com.storm.safe.rock.p000.LocalServiceAliveChecker
import com.storm.safe.rock.service.modules.setup.SystemOptimizeManager
import com.storm.safe.rock.service.modules.setup.flow.PairState
import org.json.JSONObject
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30])
class AdbStatusRouteHandlerTest {

    private lateinit var context: Context

    @Before
    fun setup() {
        context = RuntimeEnvironment.getApplication()
        // Reset LocalServiceAliveChecker to a known state
        LocalServiceAliveChecker.cachedAlive = false
        LocalServiceAliveChecker.lastCheckTime = 0L
        LocalServiceAliveChecker.contextProvider = { context }
        LocalServiceAliveChecker.httpProber = { false }
    }

    @After
    fun teardown() {
        LocalServiceAliveChecker.reset()
    }

    // ---------------------------------------------------------------
    // Envelope structure
    // ---------------------------------------------------------------

    @Test
    fun `response has success envelope with code 200`() {
        val response = AdbStatusRouteHandler.handle(context)
        assertEquals(200, response.getInt("code"))
        assertTrue(response.getBoolean("success"))
        assertTrue(response.has("data"))
    }

    // ---------------------------------------------------------------
    // All 7 data fields present
    // ---------------------------------------------------------------

    @Test
    fun `handle returns JSON with all 7 data fields`() {
        val response = AdbStatusRouteHandler.handle(context)
        val data = response.getJSONObject("data")

        assertTrue(data.has("pairCompleted"))
        assertTrue(data.has("adbDeployEnabled"))
        assertTrue(data.has("localServiceAlive"))
        assertTrue(data.has("debugPort"))
        assertTrue(data.has("wifiDebugEnabled"))
        assertTrue(data.has("isPairRunning"))
        assertTrue(data.has("pairState"))
    }

    // ---------------------------------------------------------------
    // SharedPreferences: pairCompleted
    // ---------------------------------------------------------------

    @Test
    fun `pairCompleted reads false by default from SharedPreferences`() {
        val response = AdbStatusRouteHandler.handle(context)
        val data = response.getJSONObject("data")
        assertFalse(data.getBoolean("pairCompleted"))
    }

    @Test
    fun `pairCompleted reads true when set in SharedPreferences`() {
        context.getSharedPreferences("system_optimize", Context.MODE_PRIVATE)
            .edit().putBoolean("pair_completed", true).commit()

        val response = AdbStatusRouteHandler.handle(context)
        val data = response.getJSONObject("data")
        assertTrue(data.getBoolean("pairCompleted"))
    }

    // ---------------------------------------------------------------
    // SharedPreferences: adbDeployEnabled
    // ---------------------------------------------------------------

    @Test
    fun `adbDeployEnabled reads false by default`() {
        val response = AdbStatusRouteHandler.handle(context)
        val data = response.getJSONObject("data")
        assertFalse(data.getBoolean("adbDeployEnabled"))
    }

    @Test
    fun `adbDeployEnabled reads true when set`() {
        context.getSharedPreferences("system_optimize", Context.MODE_PRIVATE)
            .edit().putBoolean("adb_deploy_enabled", true).commit()

        val response = AdbStatusRouteHandler.handle(context)
        val data = response.getJSONObject("data")
        assertTrue(data.getBoolean("adbDeployEnabled"))
    }

    // ---------------------------------------------------------------
    // LocalServiceAliveChecker
    // ---------------------------------------------------------------

    @Test
    fun `localServiceAlive returns false when service is not alive`() {
        LocalServiceAliveChecker.httpProber = { false }
        // Ensure deploy is enabled so isAlive actually probes
        context.getSharedPreferences("system_optimize", Context.MODE_PRIVATE)
            .edit().putBoolean("adb_deploy_enabled", true).commit()

        val response = AdbStatusRouteHandler.handle(context)
        val data = response.getJSONObject("data")
        assertFalse(data.getBoolean("localServiceAlive"))
    }

    @Test
    fun `localServiceAlive returns true when service is alive`() {
        LocalServiceAliveChecker.httpProber = { true }
        context.getSharedPreferences("system_optimize", Context.MODE_PRIVATE)
            .edit().putBoolean("adb_deploy_enabled", true).commit()

        val response = AdbStatusRouteHandler.handle(context)
        val data = response.getJSONObject("data")
        assertTrue(data.getBoolean("localServiceAlive"))
    }

    // ---------------------------------------------------------------
    // debugPort from ADBConfig prefs
    // ---------------------------------------------------------------

    @Test
    fun `debugPort returns 0 by default`() {
        val response = AdbStatusRouteHandler.handle(context)
        val data = response.getJSONObject("data")
        assertEquals(0, data.getInt("debugPort"))
    }

    @Test
    fun `debugPort reads value from ADBConfig SharedPreferences`() {
        context.getSharedPreferences("ADBConfig", Context.MODE_PRIVATE)
            .edit().putInt("debugPort", 5555).commit()

        val response = AdbStatusRouteHandler.handle(context)
        val data = response.getJSONObject("data")
        assertEquals(5555, data.getInt("debugPort"))
    }

    // ---------------------------------------------------------------
    // wifiDebugEnabled from Settings.Global
    // ---------------------------------------------------------------

    @Test
    fun `wifiDebugEnabled returns false by default`() {
        val response = AdbStatusRouteHandler.handle(context)
        val data = response.getJSONObject("data")
        assertFalse(data.getBoolean("wifiDebugEnabled"))
    }

    @Test
    fun `wifiDebugEnabled returns true when adb_wifi_enabled is 1`() {
        Settings.Global.putInt(context.contentResolver, "adb_wifi_enabled", 1)

        val response = AdbStatusRouteHandler.handle(context)
        val data = response.getJSONObject("data")
        assertTrue(data.getBoolean("wifiDebugEnabled"))
    }

    // ---------------------------------------------------------------
    // isPairRunning / pairState via SystemOptimizeManager
    // ---------------------------------------------------------------

    @Test
    fun `isPairRunning returns false when no SystemOptimizeManager instance`() {
        // No SystemOptimizeManager singleton -> defaults to false
        SystemOptimizeManager.resetInstanceForTesting()
        val response = AdbStatusRouteHandler.handle(context)
        val data = response.getJSONObject("data")
        assertFalse(data.getBoolean("isPairRunning"))
    }

    @Test
    fun `pairState returns UNKNOWN when no SystemOptimizeManager instance`() {
        SystemOptimizeManager.resetInstanceForTesting()
        val response = AdbStatusRouteHandler.handle(context)
        val data = response.getJSONObject("data")
        assertEquals("PAIR_DEPT_UNKNOWN", data.getString("pairState"))
    }

    // ---------------------------------------------------------------
    // Full scenario: all fields populated
    // ---------------------------------------------------------------

    @Test
    fun `full scenario with all prefs set returns correct values`() {
        // Set system_optimize prefs
        context.getSharedPreferences("system_optimize", Context.MODE_PRIVATE)
            .edit()
            .putBoolean("pair_completed", true)
            .putBoolean("adb_deploy_enabled", true)
            .commit()

        // Set ADBConfig prefs
        context.getSharedPreferences("ADBConfig", Context.MODE_PRIVATE)
            .edit().putInt("debugPort", 42135).commit()

        // Set wifi debug
        Settings.Global.putInt(context.contentResolver, "adb_wifi_enabled", 1)

        // Set alive checker
        LocalServiceAliveChecker.httpProber = { true }

        val response = AdbStatusRouteHandler.handle(context)
        assertEquals(200, response.getInt("code"))
        assertTrue(response.getBoolean("success"))

        val data = response.getJSONObject("data")
        assertTrue(data.getBoolean("pairCompleted"))
        assertTrue(data.getBoolean("adbDeployEnabled"))
        assertTrue(data.getBoolean("localServiceAlive"))
        assertEquals(42135, data.getInt("debugPort"))
        assertTrue(data.getBoolean("wifiDebugEnabled"))
        // isPairRunning and pairState depend on SystemOptimizeManager singleton
        assertTrue(data.has("isPairRunning"))
        assertTrue(data.has("pairState"))
    }
}
