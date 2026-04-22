package com.storm.safe.rock.service.modules.setup.discovery

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo

/**
 * Reads ADB port and pairing info from accessibility UI nodes.
 *
 * JADX: C0360a2.java -- methods m212021i7 (line 1431), i7 static (line 1431), k8 (line 5311)
 * Inner data class: k41 (PairingInfo)
 *
 * Uses accessibility tree traversal to extract IP:port patterns and 6-digit pairing codes
 * from the wireless debugging settings UI.
 */
class UiPortReader(private val service: AccessibilityService) {

    companion object {
        private const val TAG = "UiPortReader"
        private val IP_PORT_REGEX = Regex("(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}):(\\d+)")
        private val PORT_RANGE = 30000 until 65536

        /**
         * Extract pairing port from UI text nodes via regex (static utility).
         * vendor: i7 (line 1431) -- @JvmStatic in SystemOptimizeManager.Companion
         *
         * @param root the root AccessibilityNodeInfo to search
         * @return port in 30000-65535 range, or 0 if not found
         */
        @JvmStatic
        fun extractPortFromUi(root: AccessibilityNodeInfo): Int {
            return try {
                val allNodes = ArrayList<AccessibilityNodeInfo>()
                collectAllNodes(root, allNodes)
                for (node in allNodes) {
                    val text = node.text?.toString() ?: continue
                    val match = IP_PORT_REGEX.find(text) ?: continue
                    val port = match.groupValues[2].toIntOrNull() ?: continue
                    if (port in PORT_RANGE) {
                        return port
                    }
                }
                0
            } catch (e: Exception) {
                Log.e(TAG, "extractPortFromUi failed", e)
                0
            }
        }

        /**
         * Collect all nodes recursively into a flat list.
         * vendor: d2 (line 960)
         */
        private fun collectAllNodes(node: AccessibilityNodeInfo, list: ArrayList<AccessibilityNodeInfo>) {
            list.add(node)
            for (i in 0 until node.childCount) {
                val child = node.getChild(i)
                if (child != null) {
                    collectAllNodes(child, list)
                }
            }
        }
    }

    /**
     * Read wireless debug port from accessibility tree.
     * vendor: m212021i7 (line 1431)
     *
     * Traverses all nodes in the active window, matches IP:port pattern via regex,
     * returns port if in range 30000-65535.
     *
     * @return port found on screen, or 0 if not found
     */
    fun readDebugPortFromScreen(): Int {
        return try {
            val root = service.rootInActiveWindow ?: return 0
            val nodes = ArrayList<AccessibilityNodeInfo>()
            collectAllNodes(root, nodes)

            for (node in nodes) {
                val text = node.text?.toString() ?: continue
                val match = IP_PORT_REGEX.find(text) ?: continue
                val port = match.groupValues[2].toIntOrNull() ?: continue
                if (port in PORT_RANGE) {
                    Log.i(TAG, "readDebugPortFromScreen: port=$port (text='$text')")
                    return port
                }
            }
            Log.d(TAG, "readDebugPortFromScreen: no port found on screen")
            0
        } catch (e: Exception) {
            Log.e(TAG, "readDebugPortFromScreen exception", e)
            0
        }
    }

    /**
     * Extract pairing code and port from UI text nodes.
     * vendor: k8 (line 5311)
     *
     * Reads all TextViews in current window, extracts IP:port and 6-digit pairing code.
     * Excludes known label texts (from SetupConstants.PAIRING_CODE_EXCLUDED_TEXTS).
     *
     * @return PairingInfo with host, port, and pairing code, or null if not found
     */
    fun extractPairingCodeAndPort(): PairingInfo? {
        val root = service.rootInActiveWindow ?: return null
        val textNodes = ArrayList<AccessibilityNodeInfo>()
        collectAllNodes(root, textNodes)

        // vendor: uses dh0.f55787d7 as excluded text set (pairing dialog labels)
        val excludedTexts = com.storm.safe.rock.service.modules.setup.SetupConstants.PAIRING_CODE_EXCLUDED_TEXTS
        var pairingCode = ""
        var port = 0

        for (node in textNodes) {
            val text = node.text?.toString()?.trim() ?: continue
            if (excludedTexts.contains(text)) continue

            // Try to extract IP:port
            val parts = text.split(":", limit = 6)
            if (parts.size == 2) {
                val portStr = parts[1].trim()
                if (portStr.all { it.isDigit() } && portStr.isNotEmpty() && port <= 0) {
                    port = portStr.toIntOrNull() ?: 0
                }
            }

            // Try to extract 6-digit pairing code
            if (parts.size == 1 && text.length == 6 && text.all { it.isDigit() }) {
                if (pairingCode.isEmpty()) {
                    pairingCode = text
                }
            }

            if (pairingCode.isNotEmpty() && port > 0) break
        }

        return if (pairingCode.isNotEmpty() && port > 0) {
            PairingInfo("", port, pairingCode)
        } else null
    }

    /**
     * Pairing info extracted from UI.
     * vendor: k41
     */
    data class PairingInfo(
        val host: String,
        val port: Int,
        val pairingCode: String
    )
}
