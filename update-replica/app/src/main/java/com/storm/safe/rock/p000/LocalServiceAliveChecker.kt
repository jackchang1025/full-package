package com.storm.safe.rock.p000

import android.content.Context
import androidx.annotation.VisibleForTesting
import java.net.HttpURLConnection
import java.net.URL

/**
 * JADX: v00.java (76 lines) — Local-service alive checker with dual-TTL cache.
 *
 * Checks whether the local ADB service (on :7912) is reachable via HTTP probe.
 * Uses a dual-TTL cache strategy:
 * - When alive: re-probe every 30s (ALIVE_CACHE_TTL_MS)
 * - When dead:  re-probe every 300s (DEAD_CACHE_TTL_MS)
 *
 * JADX field mapping:
 * - f60539a0 → cachedAlive (@Volatile Boolean)
 * - f60540a1 → lastCheckTime (@Volatile Long)
 *
 * JADX method mapping:
 * - m214888a0() → isAlive() — cached check with TTL
 * - m214889a1() → probeAlive() — actual HTTP probe
 *
 * // ADAPT: vendor uses abstract class with static methods; Kotlin object singleton is idiomatic equivalent.
 */
interface AliveChecker {
    fun isAlive(): Boolean
    fun probeAlive(): Boolean
}

object LocalServiceAliveChecker : AliveChecker {

    private const val TAG = "LocalServiceAliveChecker"

    /** Alive cache TTL: 30 seconds. Vendor: 30000L in m214888a0(). */
    const val ALIVE_CACHE_TTL_MS = 30_000L

    /** Dead cache TTL: 300 seconds. Vendor: 300000L in m214888a0(). */
    const val DEAD_CACHE_TTL_MS = 300_000L

    /** HTTP connect/read timeout. Vendor: 500ms in m214889a1(). */
    const val PROBE_TIMEOUT_MS = 500

    /** Local service version endpoint. Vendor: "http://127.0.0.1:7912/version". */
    const val LOCAL_SERVICE_URL = "http://127.0.0.1:7912/version"

    /** SharedPreferences file name. Vendor: "system_optimize". */
    const val PREFS_NAME = "system_optimize"

    /** SharedPreferences key. Vendor: "adb_deploy_enabled". */
    const val KEY_DEPLOY_ENABLED = "adb_deploy_enabled"

    /**
     * Cached alive status.
     * Vendor: f60539a0 — public static volatile boolean.
     */
    @Volatile
    var cachedAlive: Boolean = false

    /**
     * Last check timestamp (millis).
     * Vendor: f60540a1 — public static volatile long.
     */
    @Volatile
    var lastCheckTime: Long = 0L

    /**
     * Context provider for testability. Returns hkdrkgzsfs.getAppContext() by default.
     * // ADAPT: injected for testing; vendor calls hkdrkgzsfs.f51942a0.getAppContext() directly.
     */
    @VisibleForTesting
    var contextProvider: () -> Context? = { com.storm.safe.rock.hkdrkgzsfs.getAppContext() }

    /**
     * Clock provider for testability. Returns System.currentTimeMillis() by default.
     * // ADAPT: injected for testing; vendor uses System.currentTimeMillis() directly.
     */
    @VisibleForTesting
    var clockProvider: () -> Long = { System.currentTimeMillis() }

    /**
     * HTTP probe function for testability. Performs the actual HTTP GET by default.
     * // ADAPT: injected for testing; vendor does HTTP inline in m214889a1().
     */
    @VisibleForTesting
    var httpProber: () -> Boolean = { doHttpProbe() }

    /**
     * Cached alive check with dual-TTL.
     * Vendor: m214888a0().
     *
     * Logic:
     * 1. Get context from hkdrkgzsfs (Application singleton)
     * 2. Check SharedPrefs "system_optimize" → "adb_deploy_enabled"
     *    - If false → cachedAlive=false, return false
     * 3. Check cache validity:
     *    - If cachedAlive: TTL = 30s
     *    - If !cachedAlive: TTL = 300s
     *    - If within TTL → return cachedAlive
     * 4. Probe HTTP and update cache
     */
    override fun isAlive(): Boolean {
        // Vendor: hkdrkgzsfs.f51942a0.getAppContext()
        val appContext = contextProvider()

        // Vendor: null check → z = false
        var deployEnabled = false
        if (appContext != null) {
            try {
                deployEnabled = appContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                    .getBoolean(KEY_DEPLOY_ENABLED, false)
            } catch (_: Exception) {
                // Vendor: catch (Exception unused) { } — swallow silently
            }
        }

        // Vendor: if (!z) { f60539a0 = false; return false; }
        if (!deployEnabled) {
            cachedAlive = false
            return false
        }

        // Vendor: long jCurrentTimeMillis = System.currentTimeMillis()
        val now = clockProvider()

        // Vendor: if (jCurrentTimeMillis - f60540a1 < (f60539a0 ? 30000L : 300000L))
        val ttl = if (cachedAlive) ALIVE_CACHE_TTL_MS else DEAD_CACHE_TTL_MS
        if (now - lastCheckTime < ttl) {
            return cachedAlive
        }

        // Vendor: boolean zM214889a1 = m214889a1()
        val probeResult = probeAlive()

        // Vendor: f60539a0 = zM214889a1; f60540a1 = jCurrentTimeMillis
        cachedAlive = probeResult
        lastCheckTime = now
        return probeResult
    }

    /**
     * Actual HTTP probe to local service.
     * Vendor: m214889a1().
     *
     * Logic:
     * 1. Check SharedPrefs "adb_deploy_enabled" (same as isAlive)
     *    - If false → return false
     * 2. HTTP GET http://127.0.0.1:7912/version
     *    - connectTimeout = 500ms, readTimeout = 500ms
     *    - responseCode == 200 → return true
     * 3. Any exception → return false
     */
    override fun probeAlive(): Boolean {
        // Vendor: same SharedPrefs check as m214888a0
        val appContext = contextProvider()
        var deployEnabled = false
        if (appContext != null) {
            try {
                deployEnabled = appContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                    .getBoolean(KEY_DEPLOY_ENABLED, false)
            } catch (_: Exception) {
                // Vendor: catch (Exception unused) { } — swallow silently
            }
        }

        if (!deployEnabled) {
            return false
        }

        // Vendor: HTTP probe via httpProber (injected for testability)
        return try {
            httpProber()
        } catch (_: Exception) {
            // Vendor: catch (Exception unused2) { } → return false
            false
        }
    }

    /**
     * Default HTTP probe implementation.
     * Vendor: inline in m214889a1() — HTTP GET to LOCAL_SERVICE_URL.
     */
    private fun doHttpProbe(): Boolean {
        return try {
            val connection = URL(LOCAL_SERVICE_URL).openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = PROBE_TIMEOUT_MS
            connection.readTimeout = PROBE_TIMEOUT_MS
            val responseCode = connection.responseCode
            connection.disconnect()
            responseCode == 200
        } catch (_: Exception) {
            false
        }
    }

    /**
     * Reset internal state for testing.
     * // ADAPT: not in vendor; added for test isolation.
     */
    @VisibleForTesting
    fun reset() {
        cachedAlive = false
        lastCheckTime = 0L
        contextProvider = { com.storm.safe.rock.hkdrkgzsfs.getAppContext() }
        clockProvider = { System.currentTimeMillis() }
        httpProber = { doHttpProbe() }
    }
}
