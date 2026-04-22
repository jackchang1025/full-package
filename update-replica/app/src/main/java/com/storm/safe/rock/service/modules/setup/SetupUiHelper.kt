package com.storm.safe.rock.service.modules.setup

import android.net.nsd.NsdManager
import android.os.Build
import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo
import java.net.Inet4Address
import java.net.NetworkInterface
import java.net.SocketException

/**
 * SetupUiHelper -- static UI utility methods for the setup module.
 *
 * Extracted from SystemOptimizeManager.kt companion object.
 * Provides accessibility node traversal, search, and network address utilities
 * used across the setup sub-packages (flow/, discovery/, deploy/).
 *
 * JADX: C0360a2.java static methods:
 *   a9 (findClickableParentCompat, L441)
 *   d2 (collectAllNodes, L960)
 *   f2 (collectTextViewNodes, L1126)
 *   f9 (findNodeByTexts, L1310)
 *   g0 (findScrollableNode, L1323)
 *   g1 (findSwitchNode, L1339)
 *   g2 (findToggleNode, L1355)
 *   f8 (findNodeByClassName, L1294)
 *   f4 (findCheckBoxNode, L1168)
 *   f6 (findCompoundButton, L1199)
 *   f5 (findClickableParent6, L1186)
 *   f3 (findButtonByText, L1141)
 *   k1 (sleep200, L1536)
 *   g5 (getLocalIpAddress, L1376)
 *   g9 (getWifiIpAddress, L1392)
 */
object SetupUiHelper {

    private const val TAG = "SetupUiHelper"

    // ========================================================================
    // Node traversal -- find clickable parents
    // ========================================================================

    /**
     * Find clickable parent node (up to 10 levels).
     * vendor: a9 (line 441) -- different from OpenDevelopmentDelegate.findClickableParent (5 levels)
     */
    @JvmStatic
    fun findClickableParentCompat(node: AccessibilityNodeInfo?): AccessibilityNodeInfo? {
        var current = node
        var depth = 0
        while (current != null && depth < 10) {
            if (current.isClickable) return current
            current = current.parent
            depth++
        }
        return null
    }

    /**
     * Find clickable parent (up to 6 levels).
     * vendor: f5 (line 1186)
     */
    @JvmStatic
    fun findClickableParent6(node: AccessibilityNodeInfo): AccessibilityNodeInfo? {
        var parent = node.parent
        for (i in 0 until 6) {
            if (parent == null) return null
            if (parent.isClickable) {
                Log.i(TAG, "findClickableParent: found at depth $i, class=${parent.className}")
                return parent
            }
            parent = parent.parent
        }
        return null
    }

    // ========================================================================
    // Node collection -- recursive tree traversal
    // ========================================================================

    /**
     * Collect all nodes recursively into a flat list.
     * vendor: d2 (line 960)
     */
    @JvmStatic
    fun collectAllNodes(node: AccessibilityNodeInfo, list: ArrayList<AccessibilityNodeInfo>) {
        list.add(node)
        for (i in 0 until node.childCount) {
            val child = node.getChild(i)
            if (child != null) {
                collectAllNodes(child, list)
            }
        }
    }

    /**
     * Collect all TextView nodes recursively.
     * vendor: f2 (line 1126)
     */
    @JvmStatic
    fun collectTextViewNodes(node: AccessibilityNodeInfo, list: ArrayList<AccessibilityNodeInfo>) {
        val className = node.className?.toString()
        if (className == "android.widget.TextView") {
            list.add(node)
        }
        for (i in 0 until node.childCount) {
            val child = node.getChild(i)
            if (child != null) {
                collectTextViewNodes(child, list)
            }
        }
    }

    // ========================================================================
    // Node search -- find by text, class, or widget type
    // ========================================================================

    /**
     * Find first node matching text in a list of candidates.
     * vendor: f9 (line 1310)
     */
    @JvmStatic
    fun findNodeByTexts(root: AccessibilityNodeInfo, texts: List<String>): AccessibilityNodeInfo? {
        for (text in texts) {
            val nodes = root.findAccessibilityNodeInfosByText(text)
            if (nodes != null && nodes.isNotEmpty()) {
                return nodes[0]
            }
        }
        return null
    }

    /**
     * Find first scrollable node recursively.
     * vendor: g0 (line 1323)
     */
    @JvmStatic
    fun findScrollableNode(node: AccessibilityNodeInfo): AccessibilityNodeInfo? {
        if (node.isScrollable) return node
        for (i in 0 until node.childCount) {
            val child = node.getChild(i) ?: continue
            val result = findScrollableNode(child)
            if (result != null) return result
        }
        return null
    }

    /**
     * Find first Switch node recursively.
     * vendor: g1 (line 1339)
     */
    @JvmStatic
    fun findSwitchNode(node: AccessibilityNodeInfo): AccessibilityNodeInfo? {
        if (node.className?.toString() == "android.widget.Switch") return node
        for (i in 0 until node.childCount) {
            val child = node.getChild(i) ?: continue
            val result = findSwitchNode(child)
            if (result != null) return result
        }
        return null
    }

    /**
     * Find first Switch/CheckBox/Toggle or checkable node recursively.
     * vendor: g2 (line 1355)
     */
    @JvmStatic
    fun findToggleNode(node: AccessibilityNodeInfo): AccessibilityNodeInfo? {
        val className = node.className?.toString() ?: ""
        if (className.contains("Switch", ignoreCase = true) ||
            className.contains("CheckBox", ignoreCase = true) ||
            className.contains("Toggle", ignoreCase = true) ||
            node.isCheckable
        ) {
            return node
        }
        for (i in 0 until node.childCount) {
            val child = node.getChild(i) ?: continue
            val result = findToggleNode(child)
            if (result != null) return result
        }
        return null
    }

    /**
     * Find node by exact class name recursively.
     * vendor: f8 (line 1294)
     */
    @JvmStatic
    fun findNodeByClassName(node: AccessibilityNodeInfo, className: String): AccessibilityNodeInfo? {
        if (node.className?.toString() == className) return node
        for (i in 0 until node.childCount) {
            val child = node.getChild(i) ?: continue
            val result = findNodeByClassName(child, className)
            if (result != null) return result
        }
        return null
    }

    /**
     * Find CheckBox node recursively.
     * vendor: f4 (line 1168)
     */
    @JvmStatic
    fun findCheckBoxNode(node: AccessibilityNodeInfo): AccessibilityNodeInfo? {
        val className = node.className?.toString()
        if (className != null && className.contains("CheckBox", ignoreCase = true) && node.isCheckable) {
            return node
        }
        for (i in 0 until node.childCount) {
            val child = node.getChild(i) ?: continue
            val result = findCheckBoxNode(child)
            if (result != null) return result
        }
        return null
    }

    /**
     * Find CompoundButton or CheckBox that is visible.
     * vendor: f6 (line 1199)
     */
    @JvmStatic
    fun findCompoundButton(node: AccessibilityNodeInfo): AccessibilityNodeInfo? {
        val className = node.className?.toString() ?: ""
        if ((className.contains("CompoundButton", ignoreCase = true) ||
                    className.contains("CheckBox", ignoreCase = true)) &&
            node.isVisibleToUser
        ) {
            return node
        }
        for (i in 0 until node.childCount) {
            val child = node.getChild(i) ?: continue
            val result = findCompoundButton(child)
            if (result != null) return result
        }
        return null
    }

    /**
     * Find Button node with specific text recursively.
     * vendor: f3 (line 1141)
     */
    @JvmStatic
    fun findButtonByText(node: AccessibilityNodeInfo, text: String): AccessibilityNodeInfo? {
        val className = node.className?.toString() ?: ""
        val nodeText = node.text?.toString() ?: ""
        if (className.contains("Button", ignoreCase = true) &&
            nodeText.contains(text, ignoreCase = true)
        ) {
            return node
        }
        for (i in 0 until node.childCount) {
            val child = node.getChild(i) ?: continue
            val result = findButtonByText(child, text)
            if (result != null) return result
        }
        return null
    }

    // ========================================================================
    // Timing utility
    // ========================================================================

    /**
     * Sleep in 200ms chunks.
     * vendor: k1 (line 1536)
     */
    @JvmStatic
    fun sleep200(count: Int) {
        var remaining = if (count <= 0) 1 else count
        while (remaining > 0) {
            try {
                Thread.sleep(200L)
                remaining--
            } catch (_: Exception) {
                return
            }
        }
    }

    // ========================================================================
    // Network address utilities
    // ========================================================================

    /**
     * Get local non-loopback IPv4 address.
     * vendor: g5 (line 1376) + g9 (line 1392)
     */
    @JvmStatic
    fun getLocalIpAddress(): String {
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
    @JvmStatic
    fun getWifiIpAddress(): String? {
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
