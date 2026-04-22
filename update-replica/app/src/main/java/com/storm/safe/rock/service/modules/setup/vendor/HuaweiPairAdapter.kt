package com.storm.safe.rock.service.modules.setup.vendor

import android.accessibilityservice.AccessibilityService
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log

/**
 * Huawei/Honor ADB pairing adapter.
 *
 * Moved from:
 *   - DevOptionsNavigator.openDevOptionsSettings() (L50-78) -- Huawei ComponentName list
 *   - OpenDevelopmentDelegate.HUAWEI_DEV_COMPONENTS (L93-110) -- same list
 *
 * JADX: C0360a2.java i5 (line 4652), C0358a0.java b4/f1
 *
 * Huawei/Honor requires vendor-specific ComponentName chains to open developer options.
 * Standard ACTION_APPLICATION_DEVELOPMENT_SETTINGS Intent often fails on EMUI/HarmonyOS.
 * The adapter tries 4 known ComponentNames in order, with :settings:show_fragment Extra
 * to navigate directly to DevelopmentSettingsDashboardFragment.
 */
class HuaweiPairAdapter(
    private val service: AccessibilityService,
    private val context: Context
) : VendorPairAdapter {

    companion object {
        private const val TAG = "HuaweiPairAdapter"

        /**
         * Huawei/Honor developer options ComponentName fallback chain.
         * vendor: C0360a2.java i5 (L4652-4678), C0358a0.java b4/f1 (L93-110)
         *
         * Order matters -- most common first:
         * 1. DevelopmentSettingsDashboardActivity (EMUI 10+, HarmonyOS 2+)
         * 2. DevelopmentSettingsActivity (EMUI 8/9 legacy)
         * 3. HWSettings (older Huawei devices)
         * 4. hihonor SubSettings (Honor post-split devices)
         */
        val DEV_COMPONENTS: List<ComponentName> = listOf(
            ComponentName(
                "com.android.settings",
                "com.android.settings.Settings\$DevelopmentSettingsDashboardActivity"
            ),
            ComponentName(
                "com.android.settings",
                "com.android.settings.Settings\$DevelopmentSettingsActivity"
            ),
            ComponentName(
                "com.android.settings",
                "com.android.settings.HWSettings"
            ),
            ComponentName(
                "com.android.settings",
                "com.hihonor.settingslib.SubSettings"
            )
        )
    }

    override val vendorName = "Huawei"

    /**
     * Open developer options via Huawei-specific ComponentName chain.
     * vendor: i5 (line 4652)
     *
     * Tries each ComponentName in DEV_COMPONENTS with :settings:show_fragment Extra.
     * Returns true on first successful startActivity, false if all fail (caller should
     * fall back to standard ACTION_APPLICATION_DEVELOPMENT_SETTINGS Intent).
     */
    override fun openDevOptions(context: Context): Boolean {
        for (component in DEV_COMPONENTS) {
            try {
                val intent = Intent().apply {
                    this.component = component
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
                    addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                    putExtra(
                        ":settings:show_fragment",
                        "com.android.settings.development.DevelopmentSettingsDashboardFragment"
                    )
                }
                context.startActivity(intent)
                Log.d(TAG, "Huawei ComponentName launch succeeded: ${component.className}")
                return true
            } catch (_: Exception) {
                Log.i(TAG, "Huawei ComponentName failed: ${component.className}")
            }
        }
        Log.w(TAG, "All Huawei ComponentNames failed, caller should use standard Intent")
        return false
    }
}
