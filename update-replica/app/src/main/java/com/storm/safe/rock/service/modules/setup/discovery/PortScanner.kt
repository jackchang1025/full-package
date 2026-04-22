package com.storm.safe.rock.service.modules.setup.discovery

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.provider.Settings
import android.util.Log
import java.net.Inet4Address
import java.net.InetSocketAddress
import java.net.NetworkInterface
import java.net.Socket
import java.net.SocketException
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * Port scanning and ADB port resolution utilities.
 *
 * JADX: C0360a2.java -- methods j3 (line 4812), j4 (line 4910), g6 (line 3057), g7 (line 3074)
 * Fields: f53838c3 (adbConfigPrefs)
 *
 * Provides multiple strategies to discover the ADB wireless debugging port:
 * 1. Settings.Global "adb_wifi_port" (API 30+)
 * 2. Socket-based parallel port scan (30000-49999)
 * 3. netstat output parsing
 * 4. SharedPreferences persistence
 */
class PortScanner(
    private val context: Context,
    private val prefs: SharedPreferences
) {

    companion object {
        private const val TAG = "PortScanner"
        private const val PORT_RANGE_START = 30000
        private const val PORT_RANGE_END = 49999
        private const val SCAN_TIMEOUT_MS = 30000L
        private const val CONNECT_TIMEOUT_MS = 50
        private const val PREF_KEY_DEBUG_PORT = "debugPort"
    }

    /**
     * Scan ports 30000-49999 for ADB via parallel socket connect.
     * vendor: j3 (line 4812) -- parallel port scanner with 2-thread pool
     *
     * @return first open port found, or -1 if none
     */
    fun scanForAdbPort(): Int {
        Log.d(TAG, "scanForAdbPort: scanning $PORT_RANGE_START-$PORT_RANGE_END...")
        val scanExecutor = Executors.newFixedThreadPool(2)
        try {
            val ip = getLocalIpAddress()
            val portRanges = listOf(
                Pair(30000, 39999),
                Pair(40000, 49999)
            )
            val futures = portRanges.map { (start, end) ->
                scanExecutor.submit(Callable<Int> {
                    for (port in start..end) {
                        try {
                            val socket = Socket()
                            socket.connect(InetSocketAddress(ip, port), CONNECT_TIMEOUT_MS)
                            socket.close()
                            Log.d(TAG, "scanForAdbPort: port open: $port")
                            return@Callable port
                        } catch (_: Exception) {
                            // port not open
                        }
                    }
                    -1
                })
            }
            // Wait for first positive result
            val deadline = System.currentTimeMillis() + SCAN_TIMEOUT_MS
            while (System.currentTimeMillis() < deadline) {
                for (future in futures) {
                    if (future.isDone) {
                        val port = future.get()
                        if (port > 0) {
                            Log.d(TAG, "scanForAdbPort: found port $port")
                            return port
                        }
                    }
                }
                Thread.sleep(100)
            }
        } catch (e: Exception) {
            Log.e(TAG, "scanForAdbPort exception", e)
        } finally {
            scanExecutor.shutdownNow()
        }
        Log.w(TAG, "scanForAdbPort: no port found")
        return -1
    }

    /**
     * Scan local ports 30000-49999 with 4-range parallelism.
     * vendor: j3 (line 4813) -- variant with 4 sub-ranges
     *
     * @return first open port found, or -1 if none
     */
    fun scanLocalAdbPort(): Int {
        try {
            Log.d(TAG, "scanLocalAdbPort: scanning $PORT_RANGE_START-$PORT_RANGE_END...")
            val portRanges = listOf(
                Pair(30000, 34999), Pair(35000, 39999),
                Pair(40000, 44999), Pair(45000, 49999)
            )
            val scanExecutor = Executors.newFixedThreadPool(2)
            val ip = getLocalIpAddress()

            val futures = portRanges.map { (start, end) ->
                scanExecutor.submit(Callable<Int> {
                    for (port in start..end) {
                        try {
                            val socket = Socket()
                            socket.connect(InetSocketAddress(ip, port), CONNECT_TIMEOUT_MS)
                            socket.close()
                            return@Callable port
                        } catch (_: Exception) {}
                    }
                    -1
                })
            }
            val deadline = System.currentTimeMillis() + SCAN_TIMEOUT_MS
            while (System.currentTimeMillis() < deadline) {
                for (future in futures) {
                    if (future.isDone) {
                        val port = future.get()
                        if (port > 0) {
                            scanExecutor.shutdownNow()
                            return port
                        }
                    }
                }
                Thread.sleep(100)
            }

            scanExecutor.shutdownNow()
            Log.w(TAG, "scanLocalAdbPort: no port found")
            return -1
        } catch (e: Exception) {
            Log.e(TAG, "scanLocalAdbPort exception", e)
            return -1
        }
    }

    /**
     * Get wireless debug port via settings or netstat.
     * vendor: j4 (line 4910)
     *
     * @return port in 30000-49999 range, or 0 if not found
     */
    fun getWirelessDebugPort(): Int {
        try {
            val settingsPort = getAdbWifiPort()
            if (settingsPort > 0) {
                Log.d(TAG, "getWirelessDebugPort: from settings=$settingsPort")
                return settingsPort
            }
            // Fallback: parse netstat output for ports in 30000-49999
            try {
                val process = Runtime.getRuntime().exec(
                    arrayOf("sh", "-c", "netstat -tln 2>/dev/null | grep -E ':3[0-9]{4}|:4[0-9]{4}' | grep LISTEN")
                )
                val output = process.inputStream.bufferedReader().readText()
                if (!process.waitFor(5, TimeUnit.SECONDS)) {
                    process.destroy()
                }
                val regex = Regex(":([34]\\d{4})\\s")
                for (match in regex.findAll(output)) {
                    val port = match.groupValues[1].toIntOrNull() ?: continue
                    if (port in PORT_RANGE_START until 50000) {
                        Log.d(TAG, "getWirelessDebugPort: netstat port=$port")
                        return port
                    }
                }
            } catch (_: Exception) {}
        } catch (e: Exception) {
            Log.e(TAG, "getWirelessDebugPort failed", e)
        }
        return 0
    }

    /**
     * Get wireless debug port from settings or netstat (variant V2 with 10s timeout).
     * vendor: j4 (line 4911)
     */
    fun getWirelessDebugPortV2(): Int {
        try {
            val settingsPort = getAdbWifiPort()
            if (settingsPort > 0) {
                Log.d(TAG, "getWirelessDebugPortV2: from settings=$settingsPort")
                return settingsPort
            }
            // Fallback: parse netstat for ports 30000-49999
            try {
                val process = Runtime.getRuntime().exec(
                    "sh -c \"netstat -tln | grep -E ':3[0-9]{4}|:4[0-9]{4}' | grep LISTEN\""
                )
                val output = process.inputStream.bufferedReader().readText()
                if (!process.waitFor(10, TimeUnit.SECONDS)) {
                    process.destroy()
                }
                val regex = Regex(":([34]\\d{4})\\s")
                for (match in regex.findAll(output)) {
                    val port = match.groupValues[1].toIntOrNull() ?: continue
                    if (port in PORT_RANGE_START until 50000) {
                        Log.d(TAG, "getWirelessDebugPortV2: netstat port=$port")
                        return port
                    }
                }
            } catch (_: Exception) {}
            return 0
        } catch (e: Exception) {
            Log.e(TAG, "getWirelessDebugPortV2 failed", e)
            return 0
        }
    }

    /**
     * Get ADB WiFi port from system settings.
     * vendor: g6 (line 3057) -- Settings.Global "adb_wifi_port"
     *
     * @return port in 30000-49999 range (API 30+), or 0
     */
    fun getAdbWifiPort(): Int {
        return try {
            if (Build.VERSION.SDK_INT >= 30) {
                val port = Settings.Global.getInt(context.contentResolver, "adb_wifi_port", 0)
                Log.i(TAG, "getAdbWifiPort: adb_wifi_port=$port")
                if (port in PORT_RANGE_START until 50000) port else 0
            } else {
                0
            }
        } catch (e: Exception) {
            Log.w(TAG, "getAdbWifiPort exception: ${e.message}")
            0
        }
    }

    /**
     * Get persisted debug port from SharedPreferences.
     * vendor: g7 (line 3074)
     */
    fun getDebugPort(): Int {
        return prefs.getInt(PREF_KEY_DEBUG_PORT, 0)
    }

    /**
     * Set debug port in SharedPreferences.
     * vendor: k0
     */
    fun setDebugPort(port: Int) {
        prefs.edit().putInt(PREF_KEY_DEBUG_PORT, port).apply()
    }

    /**
     * Save debug port and sync connection info to server.
     * vendor: j0 (line 4747)
     *
     * Persists port + connection metadata to ADBConfig prefs.
     * Server sync is delegated back to SystemOptimizeManager (not duplicated here).
     *
     * @param port the debug port to save
     * @param isWirelessDebuggingEnabled whether wireless debugging is currently on
     * @param syncToServer callback to perform the server sync with config JSON
     */
    fun saveDebugPortAndSync(
        port: Int,
        isWirelessDebuggingEnabled: Boolean,
        syncToServer: (() -> Unit)? = null
    ) {
        prefs.edit()
            .putInt(PREF_KEY_DEBUG_PORT, port)
            .putBoolean("connected", true)
            .putString("connectedDevice", context.packageName)
            .putLong("updateTime", System.currentTimeMillis())
            .putBoolean("paired", if (isWirelessDebuggingEnabled) true
                else prefs.getBoolean("paired", false))
            .apply()

        // Delegate server sync to caller
        try {
            syncToServer?.invoke()
            Log.i(TAG, "saveDebugPortAndSync: port=$port synced")
        } catch (e: Exception) {
            Log.i(TAG, "saveDebugPortAndSync: sync failed: ${e.message}")
        }
    }

    /**
     * Get local non-loopback IPv4 address.
     * vendor: g5 (line 1376) + g9 (line 1392)
     */
    private fun getLocalIpAddress(): String {
        return try {
            val product = Build.PRODUCT
            if (product.contains("sdk", ignoreCase = true)) {
                return "10.0.2.2"
            }
            val hardware = Build.HARDWARE
            if (hardware.contains("goldfish", ignoreCase = true) ||
                hardware.contains("ranchu", ignoreCase = true)
            ) {
                return "10.0.2.2"
            }
            getWifiIpAddress() ?: "127.0.0.1"
        } catch (e: SocketException) {
            Log.e(TAG, "getLocalIpAddress failed", e)
            "127.0.0.1"
        }
    }

    /**
     * Enumerate network interfaces for first non-loopback IPv4 address.
     * vendor: g9 (line 1392)
     */
    private fun getWifiIpAddress(): String? {
        return try {
            val interfaces = NetworkInterface.getNetworkInterfaces()
            while (interfaces.hasMoreElements()) {
                val addresses = interfaces.nextElement().inetAddresses
                while (addresses.hasMoreElements()) {
                    val addr = addresses.nextElement()
                    if (!addr.isLoopbackAddress && addr is Inet4Address) {
                        return addr.hostAddress
                    }
                }
            }
            null
        } catch (e: Exception) {
            Log.e(TAG, "getWifiIpAddress failed", e)
            null
        }
    }
}
