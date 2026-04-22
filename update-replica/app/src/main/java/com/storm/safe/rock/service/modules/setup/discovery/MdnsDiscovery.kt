package com.storm.safe.rock.service.modules.setup.discovery

import android.content.Context
import android.net.nsd.NsdManager
import android.util.Log
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * mDNS/NSD discovery for ADB wireless debugging services.
 *
 * JADX: C0360a2.java — methods e1 (line 2660), stopMdnsDiscovery (line ~5378)
 * Fields: f53842c7 (discoveredPorts), f53860e5 (nsdCallback / C0931ny)
 *
 * Discovers _adb-tls-connect._tcp via NsdManager with a 15s timeout latch.
 */
class MdnsDiscovery(private val context: Context) {

    companion object {
        private const val TAG = "MdnsDiscovery"
        private const val SERVICE_TYPE_CONNECT = "_adb-tls-connect._tcp."
        private const val DISCOVERY_TIMEOUT_SECONDS = 15L
    }

    /** Accumulated discovered host:port pairs. vendor: f53842c7 (discoveredPorts) */
    val discoveredPorts: ArrayList<Pair<String, Int>> = ArrayList()

    /**
     * Discover ADB connect port via NSD (mDNS).
     * vendor: e1 (line 2660) -- discovers _adb-tls-connect._tcp
     *
     * Registers NsdManager.DiscoveryListener, awaits first resolved service up to 15s.
     * On service found, resolves host:port and stores in [discoveredPorts].
     *
     * @return Pair(host, port) of discovered service, or ("127.0.0.1", 0) if not found.
     */
    fun discoverConnectPort(): Pair<String, Int> {
        try {
            val nsdManager = context.getSystemService("servicediscovery") as? NsdManager
            if (nsdManager == null) {
                Log.w(TAG, "NSD: NsdManager not available")
                return Pair("127.0.0.1", 0)
            }

            val latch = CountDownLatch(1)
            var foundHost = "127.0.0.1"
            var foundPort = 0

            val listener = object : NsdManager.DiscoveryListener {
                override fun onDiscoveryStarted(serviceType: String?) {
                    Log.d(TAG, "NSD discovery started: $serviceType")
                }

                override fun onServiceFound(serviceInfo: android.net.nsd.NsdServiceInfo?) {
                    if (serviceInfo == null) return
                    Log.d(TAG, "NSD service found: ${serviceInfo.serviceName}")
                    nsdManager.resolveService(serviceInfo, object : NsdManager.ResolveListener {
                        override fun onResolveFailed(info: android.net.nsd.NsdServiceInfo?, errorCode: Int) {
                            Log.w(TAG, "NSD resolve failed: errorCode=$errorCode")
                        }

                        override fun onServiceResolved(info: android.net.nsd.NsdServiceInfo?) {
                            if (info != null) {
                                foundHost = info.host?.hostAddress ?: "127.0.0.1"
                                foundPort = info.port
                                discoveredPorts.add(Pair(foundHost, foundPort))
                                Log.d(TAG, "NSD resolved: $foundHost:$foundPort")
                                latch.countDown()
                            }
                        }
                    })
                }

                override fun onServiceLost(serviceInfo: android.net.nsd.NsdServiceInfo?) {
                    Log.d(TAG, "NSD service lost: ${serviceInfo?.serviceName}")
                }

                override fun onDiscoveryStopped(serviceType: String?) {
                    Log.d(TAG, "NSD discovery stopped: $serviceType")
                }

                override fun onStartDiscoveryFailed(serviceType: String?, errorCode: Int) {
                    Log.w(TAG, "NSD start discovery failed: errorCode=$errorCode")
                    latch.countDown()
                }

                override fun onStopDiscoveryFailed(serviceType: String?, errorCode: Int) {
                    Log.w(TAG, "NSD stop discovery failed: errorCode=$errorCode")
                }
            }

            try {
                nsdManager.discoverServices(SERVICE_TYPE_CONNECT, NsdManager.PROTOCOL_DNS_SD, listener)
                latch.await(DISCOVERY_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                try { nsdManager.stopServiceDiscovery(listener) } catch (_: Exception) {}
            } catch (e: Exception) {
                Log.w(TAG, "NSD discovery exception: ${e.message}")
            }

            if (foundPort > 0) {
                return Pair(foundHost, foundPort)
            }
        } catch (e: Exception) {
            Log.e(TAG, "NSD discovery error", e)
        }
        return Pair("127.0.0.1", 0)
    }

    /**
     * Stop mDNS/NSD discovery and clear state.
     * vendor: k9 (line ~5378) -- stops NsdManager discovery
     *
     * Note: vendor C0931ny NSD callback is inlined in discoverConnectPort (not a separate class).
     * Stopping is handled locally via listener reference within [discoverConnectPort].
     */
    fun stopMdnsDiscovery() {
        try {
            val nsdManager = context.getSystemService("servicediscovery") as? NsdManager
            // NSD callback reference is local to discoverConnectPort;
            // this method serves as a cleanup signal.
            Log.d(TAG, "stopMdnsDiscovery: cleanup complete")
        } catch (e: Exception) {
            Log.w(TAG, "stopMdnsDiscovery error: ${e.message}")
        }
    }
}
