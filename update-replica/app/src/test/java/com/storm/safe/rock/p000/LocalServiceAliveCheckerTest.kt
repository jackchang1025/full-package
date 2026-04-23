package com.storm.safe.rock.p000

import android.content.Context
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

/**
 * Tests for LocalServiceAliveChecker.
 *
 * JADX: v00.java — local-service alive checker with dual-TTL cache.
 * Vendor: abstract class v00 with static fields f60539a0 (cachedAlive), f60540a1 (lastCheckTime).
 *
 * 6 test cases covering:
 * 1. isAlive returns false when adb_deploy_enabled is false
 * 2. isAlive returns cached true within 30s TTL
 * 3. isAlive returns cached false within 300s TTL
 * 4. isAlive probes HTTP after cache expiry
 * 5. probeAlive returns true on HTTP 200
 * 6. probeAlive returns false on connection timeout or exception
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30])
class LocalServiceAliveCheckerTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = RuntimeEnvironment.getApplication()
        LocalServiceAliveChecker.reset()
        // Inject Robolectric context; vendor uses hkdrkgzsfs.getAppContext() which is null in test.
        // ADAPT: contextProvider injection for test isolation.
        LocalServiceAliveChecker.contextProvider = { context }
    }

    @After
    fun tearDown() {
        LocalServiceAliveChecker.reset()
    }

    // ==================== Test 1 ====================

    @Test
    fun `isAlive returns false when adb_deploy_enabled is false`() {
        // Ensure the pref is false (default)
        val prefs = context.getSharedPreferences("system_optimize", Context.MODE_PRIVATE)
        prefs.edit().putBoolean("adb_deploy_enabled", false).commit()

        // Inject a clock that returns a fixed time
        val fixedClock = { 1000L }
        LocalServiceAliveChecker.clockProvider = fixedClock

        val result = LocalServiceAliveChecker.isAlive()
        assertFalse("isAlive should return false when deploy is disabled", result)
        // cachedAlive should also be set to false (vendor: f60539a0 = false)
        assertFalse("cachedAlive should be false", LocalServiceAliveChecker.cachedAlive)
    }

    // ==================== Test 2 ====================

    @Test
    fun `isAlive returns cached true within 30s TTL`() {
        val prefs = context.getSharedPreferences("system_optimize", Context.MODE_PRIVATE)
        prefs.edit().putBoolean("adb_deploy_enabled", true).commit()

        // Pre-set cached state: alive=true, lastCheck=1000
        LocalServiceAliveChecker.cachedAlive = true
        LocalServiceAliveChecker.lastCheckTime = 1000L

        // Set clock to 1000 + 29999 = within 30s TTL
        LocalServiceAliveChecker.clockProvider = { 1000L + 29_999L }

        // Should NOT probe HTTP, should return cached true
        var probeCalled = false
        LocalServiceAliveChecker.httpProber = {
            probeCalled = true
            false
        }

        val result = LocalServiceAliveChecker.isAlive()
        assertTrue("isAlive should return cached true within 30s TTL", result)
        assertFalse("HTTP probe should NOT have been called", probeCalled)
    }

    // ==================== Test 3 ====================

    @Test
    fun `isAlive returns cached false within 300s TTL`() {
        val prefs = context.getSharedPreferences("system_optimize", Context.MODE_PRIVATE)
        prefs.edit().putBoolean("adb_deploy_enabled", true).commit()

        // Pre-set cached state: alive=false, lastCheck=1000
        LocalServiceAliveChecker.cachedAlive = false
        LocalServiceAliveChecker.lastCheckTime = 1000L

        // Set clock to 1000 + 299999 = within 300s TTL
        LocalServiceAliveChecker.clockProvider = { 1000L + 299_999L }

        // Should NOT probe HTTP, should return cached false
        var probeCalled = false
        LocalServiceAliveChecker.httpProber = {
            probeCalled = true
            true
        }

        val result = LocalServiceAliveChecker.isAlive()
        assertFalse("isAlive should return cached false within 300s TTL", result)
        assertFalse("HTTP probe should NOT have been called", probeCalled)
    }

    // ==================== Test 4 ====================

    @Test
    fun `isAlive probes HTTP after cache expiry`() {
        val prefs = context.getSharedPreferences("system_optimize", Context.MODE_PRIVATE)
        prefs.edit().putBoolean("adb_deploy_enabled", true).commit()

        // Pre-set cached state: alive=false, lastCheck=1000
        LocalServiceAliveChecker.cachedAlive = false
        LocalServiceAliveChecker.lastCheckTime = 1000L

        // Set clock to 1000 + 300001 = past 300s TTL (dead cache)
        val probeTime = 1000L + 300_001L
        LocalServiceAliveChecker.clockProvider = { probeTime }

        // HTTP probe returns true
        var probeCalled = false
        LocalServiceAliveChecker.httpProber = {
            probeCalled = true
            true
        }

        val result = LocalServiceAliveChecker.isAlive()
        assertTrue("HTTP probe should have been called after cache expiry", probeCalled)
        assertTrue("isAlive should return probe result (true)", result)
        assertTrue("cachedAlive should be updated to true", LocalServiceAliveChecker.cachedAlive)
        assertEquals("lastCheckTime should be updated", probeTime, LocalServiceAliveChecker.lastCheckTime)
    }

    // ==================== Test 5 ====================

    @Test
    fun `probeAlive returns true on HTTP 200`() {
        val prefs = context.getSharedPreferences("system_optimize", Context.MODE_PRIVATE)
        prefs.edit().putBoolean("adb_deploy_enabled", true).commit()

        // Inject HTTP prober that simulates 200 OK
        LocalServiceAliveChecker.httpProber = { true }

        val result = LocalServiceAliveChecker.probeAlive()
        assertTrue("probeAlive should return true on HTTP 200", result)
    }

    // ==================== Test 6 ====================

    @Test
    fun `probeAlive returns false on connection timeout or exception`() {
        val prefs = context.getSharedPreferences("system_optimize", Context.MODE_PRIVATE)
        prefs.edit().putBoolean("adb_deploy_enabled", true).commit()

        // Inject HTTP prober that simulates timeout/exception
        LocalServiceAliveChecker.httpProber = { false }

        val result = LocalServiceAliveChecker.probeAlive()
        assertFalse("probeAlive should return false on timeout/exception", result)
    }

    @Test
    fun `probeAlive returns false when adb_deploy_enabled is false`() {
        val prefs = context.getSharedPreferences("system_optimize", Context.MODE_PRIVATE)
        prefs.edit().putBoolean("adb_deploy_enabled", false).commit()

        // Even if HTTP would succeed, should return false
        LocalServiceAliveChecker.httpProber = { true }

        val result = LocalServiceAliveChecker.probeAlive()
        assertFalse("probeAlive should return false when deploy is disabled", result)
    }

    @Test
    fun `isAlive returns false when context is null`() {
        // Vendor: appContext == null → z = false → cachedAlive=false, return false
        LocalServiceAliveChecker.contextProvider = { null }

        val result = LocalServiceAliveChecker.isAlive()
        assertFalse("isAlive should return false when context is null", result)
        assertFalse("cachedAlive should be false when context is null", LocalServiceAliveChecker.cachedAlive)
    }

    @Test
    fun `isAlive with alive cache expired at 30s boundary probes again`() {
        val prefs = context.getSharedPreferences("system_optimize", Context.MODE_PRIVATE)
        prefs.edit().putBoolean("adb_deploy_enabled", true).commit()

        // Pre-set cached state: alive=true, lastCheck=1000
        LocalServiceAliveChecker.cachedAlive = true
        LocalServiceAliveChecker.lastCheckTime = 1000L

        // Set clock to exactly 30000ms later (not less than, so cache is expired)
        LocalServiceAliveChecker.clockProvider = { 1000L + 30_000L }

        var probeCalled = false
        LocalServiceAliveChecker.httpProber = {
            probeCalled = true
            false
        }

        val result = LocalServiceAliveChecker.isAlive()
        assertTrue("HTTP probe should be called at exactly 30s boundary", probeCalled)
        assertFalse("isAlive should return probe result (false)", result)
    }

    @Test
    fun `reset clears cached state`() {
        LocalServiceAliveChecker.cachedAlive = true
        LocalServiceAliveChecker.lastCheckTime = 99999L

        LocalServiceAliveChecker.reset()

        assertFalse("cachedAlive should be false after reset", LocalServiceAliveChecker.cachedAlive)
        assertEquals("lastCheckTime should be 0 after reset", 0L, LocalServiceAliveChecker.lastCheckTime)
    }
}
