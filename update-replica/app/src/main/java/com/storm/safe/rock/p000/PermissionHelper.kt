package com.storm.safe.rock.p000

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Process
import android.text.TextUtils
import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.Collections
import java.util.Locale

/**
 * JADX: p000/AbstractC1117qo.java (1,477 LOC) — Large utility class with mixed static methods.
 *
 * This is a mega-class produced by R8 merging many unrelated utility methods into one abstract
 * class. We only replicate methods that are actually referenced from the rock/ package:
 *
 * - m214411a7(context, permission) → checkPermission
 * - m214441d7(hkdrkgzsfs) → initWithContext (loads locateValues.json config)
 * - m214459f8(activity, permissions, requestCode) → requestPermissions
 * - m214451e7(object) → toSingletonList
 * - m214424c0() → getBrandLowerCase
 * - m214437d3() → getLanguageTag
 * - m214446e2() → isHuawei
 * - m214448e4() → isOppo
 * - m214449e5() → isVivo
 * - m214450e6() → isXiaomi
 * - m214453e9() → loadLanguageConfig (private helper)
 * - m214439d5() → getPatternViewIds
 * - m214436d2(list, json) → collectIds (private helper)
 *
 * Static fields:
 * - f59540a4 → configJson: main locateValues.json content
 * - f59541a5 → languageConfig: language-specific config sub-object
 *
 * The remaining ~70 methods are AndroidX/Jetpack/Kotlin-stdlib utilities that are not
 * referenced from our rock/ codebase, so they are omitted to avoid dead code.
 */
abstract class PermissionHelper {

    companion object {
        private const val TAG = "LocateValuesHelper"

        /**
         * JADX: f59540a4 — Main config JSON loaded from locateValues.json.
         */
        @JvmStatic
        var configJson: JSONObject? = null

        /**
         * JADX: f59541a5 — Language-specific config sub-object.
         */
        @JvmStatic
        var languageConfig: JSONObject? = null

        // ==================== Permission methods ====================

        /**
         * JADX: m214411a7 — Check if a permission is granted.
         * Wraps context.checkPermission with special handling for POST_NOTIFICATIONS on API < 33.
         *
         * @param context The context to check permission against.
         * @param permission The permission string to check.
         * @return 0 if granted, -1 if denied.
         * @throws NullPointerException if permission is null.
         */
        @JvmStatic
        fun checkPermission(context: Context, permission: String?): Int {
            if (permission == null) {
                throw NullPointerException("permission must be non-null")
            }
            // JADX: AbstractC0496fi.m212821a0() checks if SDK >= 33 (Tiramisu)
            // On pre-33 devices, POST_NOTIFICATIONS is not a real permission;
            // vendor checks notification channel enabled status instead.
            if (Build.VERSION.SDK_INT < 33 &&
                TextUtils.equals("android.permission.POST_NOTIFICATIONS", permission)
            ) {
                // ADAPT: vendor checks NotificationManagerCompat.areNotificationsEnabled()
                // We simplify to return -1 (denied) on pre-33 since the permission doesn't exist
                return -1
            }
            return context.checkPermission(permission, Process.myPid(), Process.myUid())
        }

        /**
         * JADX: m214459f8 — Request permissions from an Activity.
         * Filters out POST_NOTIFICATIONS on API < 33, then delegates to
         * ActivityCompat.requestPermissions.
         *
         * @param activity The activity to request from.
         * @param permissions Array of permission strings.
         * @param requestCode The request code for onRequestPermissionsResult.
         */
        @JvmStatic
        fun requestPermissions(activity: Activity, permissions: Array<String>, requestCode: Int) {
            val skipIndices = HashSet<Int>()
            for (i in permissions.indices) {
                if (TextUtils.isEmpty(permissions[i])) {
                    throw IllegalArgumentException(
                        "Permission request for permissions ${permissions.contentToString()} must not contain null or empty values"
                    )
                }
                // JADX: on pre-33 devices, filter out POST_NOTIFICATIONS
                if (Build.VERSION.SDK_INT < 33 &&
                    TextUtils.equals(permissions[i], "android.permission.POST_NOTIFICATIONS")
                ) {
                    skipIndices.add(i)
                }
            }

            val filtered = if (skipIndices.isNotEmpty()) {
                if (skipIndices.size == permissions.size) {
                    // All permissions are POST_NOTIFICATIONS on pre-33 — nothing to request
                    return
                }
                val result = Array(permissions.size - skipIndices.size) { "" }
                var idx = 0
                for (i in permissions.indices) {
                    if (!skipIndices.contains(i)) {
                        result[idx] = permissions[i]
                        idx++
                    }
                }
                result
            } else {
                permissions
            }

            // JADX: AbstractC0943o8.m214162a1(activity, strArr, i)
            // Delegates to activity.requestPermissions
            activity.requestPermissions(filtered, requestCode)
        }

        // ==================== Utility methods ====================

        /**
         * JADX: m214451e7 — Wrap an object in an immutable singleton list.
         *
         * @param element The element to wrap.
         * @return An unmodifiable list containing only the given element.
         */
        @JvmStatic
        fun toSingletonList(element: Any): List<Any> {
            return Collections.singletonList(element)
        }

        /**
         * JADX: m214424c0 — Get device brand in lowercase.
         *
         * @return Build.BRAND.lowercase(Locale.ROOT)
         */
        @JvmStatic
        fun getBrandLowerCase(): String {
            return Build.BRAND.lowercase(Locale.ROOT)
        }

        /**
         * JADX: m214437d3 — Get language tag (e.g. "zh-CN", "en-US").
         *
         * @return "{language}-{country}" from default locale.
         */
        @JvmStatic
        fun getLanguageTag(): String {
            val locale = Locale.getDefault()
            return "${locale.language}-${locale.country}"
        }

        // ==================== Brand detection ====================

        /**
         * JADX: m214446e2 — Check if the device is Huawei or Honor.
         */
        @JvmStatic
        fun isHuawei(): Boolean {
            val brand = getBrandLowerCase()
            return brand == "huawei" || brand == "honor"
        }

        /**
         * JADX: m214448e4 — Check if the device is OPPO, Realme, or OnePlus.
         */
        @JvmStatic
        fun isOppo(): Boolean {
            val brand = getBrandLowerCase()
            return brand == "oppo" || brand == "realme" || brand == "oneplus"
        }

        /**
         * JADX: m214449e5 — Check if the device is vivo or iQOO.
         */
        @JvmStatic
        fun isVivo(): Boolean {
            val brand = getBrandLowerCase()
            return brand == "vivo" || brand == "iqoo"
        }

        /**
         * JADX: m214450e6 — Check if the device is Xiaomi, Redmi, POCO, or Black Shark.
         */
        @JvmStatic
        fun isXiaomi(): Boolean {
            val brand = getBrandLowerCase()
            return brand == "xiaomi" || brand == "redmi" || brand == "poco" || brand == "blackshark"
        }

        // ==================== Config loading ====================

        /**
         * JADX: m214441d7 — Initialize with application context.
         * Loads locateValues.json from encrypted assets and parses brand/language config.
         *
         * @param app The Application context (hkdrkgzsfs).
         */
        @JvmStatic
        fun initWithContext(app: Context) {
            try {
                configJson = JSONObject(EncryptedConfigStore.readAsset(app, "locateValues.json"))
                loadLanguageConfig()
                val brand = getBrandLowerCase()
                val json = configJson
                if (json != null) {
                    val brands = json.optJSONObject("brands")
                    brands?.optJSONObject(brand)
                }
                Log.d(
                    TAG,
                    "配置加载成功: language=${getLanguageTag()}, brand=${getBrandLowerCase()}"
                )
            } catch (e: Exception) {
                Log.w(TAG, "加载配置失败: ${e.message}")
            }
        }

        /**
         * JADX: m214453e9 — Load language-specific config from the parsed JSON.
         * Tries exact match (e.g. "zh-CN"), then prefix match (e.g. "zh"), then fallback to "en".
         */
        @JvmStatic
        internal fun loadLanguageConfig() {
            val langTag = getLanguageTag()
            val json = configJson ?: return
            val languages = json.optJSONObject("languages") ?: return

            var langConfig = languages.optJSONObject(langTag)
            languageConfig = langConfig

            if (langConfig == null && langTag.contains("-")) {
                // Try prefix only (before the dash)
                val prefix = langTag.split("-")[0]
                languageConfig = languages.optJSONObject(prefix)
            }

            if (languageConfig == null) {
                languageConfig = languages.optJSONObject("en")
            }
        }

        /**
         * JADX: m214439d5 — Get pattern view IDs from config.
         * Reads patternViewIds.allIds first; falls back to brand-specific + generic IDs.
         *
         * @return List of pattern view ID strings.
         */
        @JvmStatic
        @Throws(JSONException::class)
        fun getPatternViewIds(): ArrayList<String> {
            val result = ArrayList<String>()

            // Try allIds first
            val json = configJson
            if (json != null) {
                val patternViewIds = json.optJSONObject("patternViewIds")
                if (patternViewIds != null) {
                    val allIds = patternViewIds.optJSONArray("allIds")
                    if (allIds != null) {
                        for (i in 0 until allIds.length()) {
                            result.add(allIds.getString(i))
                        }
                    }
                }
            }

            // Fallback: brand-specific + generic
            if (result.isEmpty()) {
                val fallback = ArrayList<String>()
                val json2 = configJson
                var obj = json2?.optJSONObject("patternViewIds")
                if (obj != null) {
                    val systemui = obj.optJSONObject("systemui")
                    if (systemui != null) {
                        obj = systemui
                    }
                    val brandObj = obj.optJSONObject(getBrandLowerCase())
                    if (brandObj != null) {
                        collectIds(fallback, brandObj)
                    }
                    val genericObj = obj.optJSONObject("generic")
                    if (genericObj != null) {
                        collectIds(fallback, genericObj)
                    }
                }
                result.addAll(fallback)
            }

            return result
        }

        /**
         * JADX: m214436d2 — Collect ID strings from a JSON object's "ids" array.
         * Adds each unique ID to the target list.
         *
         * @param target The list to add IDs to.
         * @param json The JSON object containing an "ids" array.
         */
        @JvmStatic
        @Throws(JSONException::class)
        internal fun collectIds(target: ArrayList<String>, json: JSONObject) {
            val ids: JSONArray = json.optJSONArray("ids") ?: return
            for (i in 0 until ids.length()) {
                val id = ids.getString(i)
                if (!target.contains(id)) {
                    target.add(id)
                }
            }
        }
    }
}
