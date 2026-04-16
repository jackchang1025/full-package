package com.storm.safe.rock.service.modules.automation

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo
import android.view.accessibility.AccessibilityWindowInfo

/**
 * Resolve the accessibility root for Settings/SecurityCenter windows on MIUI.
 *
 * MIUI withholds accessibility focus from system Settings windows, so
 * [AccessibilityService.getRootInActiveWindow] returns the desktop root
 * (com.miui.home) instead of the foreground Settings Activity. This helper
 * enumerates [AccessibilityService.getWindows] and selects the Application
 * window whose root packageName matches the expected set, regardless of
 * whether that window holds a11y focus.
 */
object A11yWindowResolver {
    private const val TAG = "A11yWindowResolver"

    /** Package names for Settings/permission pages.
     *  Vendor C0327b2:888 (15 entries) + MIUI misettings + replica extras. */
    val SETTINGS_PACKAGES: List<String> = listOf(
        // --- Vendor core 15 (C0327b2:888 m211708e0) ---
        "com.android.settings",
        "com.android.systemui",
        "com.android.permissioncontroller",
        "com.miui.securitycenter",
        "com.miui.permcenter",
        "com.coloros.safecenter",
        "com.vivo.permissionmanager",
        "com.huawei.systemmanager",
        "com.samsung.android.lool",
        "com.oneplus.security",
        "com.honor.systemmanager",
        "com.transsion.permissionmanager",
        "com.meizu.safe",
        "com.smartisanos.security",
        "com.lenovo.safecenter",
        // --- MIUI 澎湃 OS (C0367a4:6548) ---
        "com.xiaomi.misettings",
        // --- Replica extras (valid brand packages not in vendor 15) ---
        "com.coloros.phonemanager",
        "com.bbk.VivoSafe",
        "com.oplus.safecenter"
    )

    /**
     * Returns the root of the top Settings/SecurityCenter Application window, or null if none found.
     *
     * Prefers windows that are [AccessibilityWindowInfo.isActive] or focused.
     * Falls back to any TYPE_APPLICATION window whose packageName is in [SETTINGS_PACKAGES].
     */
    fun findSettingsRoot(service: AccessibilityService?): AccessibilityNodeInfo? {
        if (service == null) return null
        return try {
            val windows = service.windows ?: return null
            // First pass: active or focused application window with settings package
            for (w in windows) {
                if (w.type != AccessibilityWindowInfo.TYPE_APPLICATION) continue
                if (!w.isActive && !w.isFocused) continue
                val root = try { w.root } catch (_: Exception) { null } ?: continue
                val pkg = root.packageName?.toString() ?: ""
                if (SETTINGS_PACKAGES.any { it == pkg || pkg.startsWith("$it.") }) {
                    Log.d(TAG, "[findSettingsRoot] matched active window id=${w.id} pkg=$pkg")
                    return root
                }
            }
            // Second pass: any application window with settings package (even if not focused)
            for (w in windows) {
                if (w.type != AccessibilityWindowInfo.TYPE_APPLICATION) continue
                val root = try { w.root } catch (_: Exception) { null } ?: continue
                val pkg = root.packageName?.toString() ?: ""
                if (SETTINGS_PACKAGES.any { it == pkg || pkg.startsWith("$it.") }) {
                    Log.d(TAG, "[findSettingsRoot] matched non-focused window id=${w.id} pkg=$pkg")
                    return root
                }
            }
            Log.d(TAG, "[findSettingsRoot] no Settings window found. windows=${windows.size}")
            null
        } catch (e: Exception) {
            Log.w(TAG, "[findSettingsRoot] exception", e)
            null
        }
    }

    /**
     * Returns the Settings root if available, otherwise falls back to [service.rootInActiveWindow].
     * Use this as a drop-in replacement for rootInActiveWindow at all automation call-sites
     * that target Settings pages.
     */
    fun resolveRoot(service: AccessibilityService?): AccessibilityNodeInfo? {
        val settingsRoot = findSettingsRoot(service)
        if (settingsRoot != null) return settingsRoot
        return try { service?.rootInActiveWindow } catch (_: Exception) { null }
    }
}
