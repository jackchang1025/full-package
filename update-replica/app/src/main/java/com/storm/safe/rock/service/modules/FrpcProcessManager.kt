package com.storm.safe.rock.service.modules

import android.content.Context
import android.os.FileObserver
import android.util.Log
import org.json.JSONObject
import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import java.util.Timer
import java.util.TimerTask
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference

class FrpcProcessManager(private val context: Context) {

    companion object {
        private const val TAG = "FrpcProcessManager"
        private const val CHECK_INTERVAL_MS = 5000L
        private const val FRPC_INI_NAME = "frpc.ini"
        private const val FRPC_SO_NAME = "libfrpc.so"
    }

    private var timer: Timer? = null
    private var frpcProcess: Process? = null
    private val isRunning = AtomicBoolean(false)
    private val deviceId = AtomicReference<String?>(null)
    private var fileObserver: FileObserver? = null

    private val filesDir: String
        get() = context.filesDir.absolutePath

    private val frpcIniPath: String
        get() = "$filesDir/$FRPC_INI_NAME"

    fun start() {
        if (timer != null) {
            Log.d(TAG, "已在运行，跳过重复启动")
            return
        }

        val id = context.getSharedPreferences("app_config", Context.MODE_PRIVATE)
            .getString("device_id", null)
        deviceId.set(id)

        if (id.isNullOrEmpty()) {
            Log.w(TAG, "deviceId 未注册，延迟启动 frpc")
            return
        }

        setupFileObserver()

        timer = Timer("FrpcCheckThread", true)
        timer?.schedule(object : TimerTask() {
            override fun run() {
                checkAndStart()
            }
        }, CHECK_INTERVAL_MS, CHECK_INTERVAL_MS)

        Log.i(TAG, "frpc 看门狗已启动 (每 ${CHECK_INTERVAL_MS}ms)")
    }

    fun stop() {
        timer?.cancel()
        timer = null
        try {
            frpcProcess?.destroy()
            frpcProcess = null
        } catch (e: Exception) {
            Log.w(TAG, "停止 frpc 进程异常", e)
        }
        fileObserver?.stopWatching()
        fileObserver = null
        isRunning.set(false)
        Log.i(TAG, "frpc 已停止")
    }

    fun reload() {
        Log.i(TAG, "重新加载 frpc 进程")
        try {
            frpcProcess?.destroy()
            frpcProcess = null
        } catch (_: Exception) {}
        isRunning.set(false)
        checkAndStart()
    }

    fun onConfigDeleted() {
        Log.i(TAG, "frpc.ini 被删除，触发重新下载")
        isRunning.set(false)
        try {
            frpcProcess?.destroy()
            frpcProcess = null
        } catch (_: Exception) {}
        downloadFrpcIni()
    }

    fun updateDeviceId(id: String) {
        val oldId = deviceId.getAndSet(id)
        if (oldId.isNullOrEmpty() && id.isNotEmpty()) {
            Log.i(TAG, "deviceId 已设置: $id，启动 frpc 看门狗")
            start()
        }
    }

    private fun checkAndStart() {
        try {
            if (isProcessAlive()) {
                return
            }

            val iniFile = File(frpcIniPath)
            if (!iniFile.exists()) {
                Log.d(TAG, "frpc.ini 不存在，请求下载")
                downloadFrpcIni()
                return
            }

            val soPath = findFrpcSo()
            if (soPath == null) {
                Log.w(TAG, "libfrpc.so 未找到")
                return
            }

            launchFrpc(soPath, frpcIniPath)
        } catch (e: Exception) {
            Log.e(TAG, "checkAndStart 异常", e)
        }
    }

    private fun isProcessAlive(): Boolean {
        val proc = frpcProcess ?: return false
        return try {
            proc.exitValue()
            frpcProcess = null
            isRunning.set(false)
            false
        } catch (_: IllegalThreadStateException) {
            true
        }
    }

    private fun findFrpcSo(): String? {
        val nativeLibDir = context.applicationInfo.nativeLibraryDir
        if (!nativeLibDir.isNullOrEmpty()) {
            val soPath = "$nativeLibDir/$FRPC_SO_NAME"
            if (File(soPath).exists()) {
                return soPath
            }
        }
        Log.w(TAG, "nativeLibraryDir($nativeLibDir) 下未找到 $FRPC_SO_NAME")
        return null
    }

    private fun launchFrpc(soPath: String, iniPath: String) {
        try {
            Log.i(TAG, "启动 frpc: $soPath -c $iniPath")
            val process = Runtime.getRuntime().exec(arrayOf(soPath, "-c", iniPath))
            frpcProcess = process
            isRunning.set(true)
            Log.i(TAG, "frpc 进程已启动 (pid via Process ref)")

            Thread({
                try {
                    val reader = process.errorStream.bufferedReader()
                    reader.forEachLine { line ->
                        Log.d(TAG, "[frpc-stderr] $line")
                    }
                } catch (_: Exception) {}
            }, "frpc-stderr-reader").apply { isDaemon = true; start() }

        } catch (e: Exception) {
            Log.e(TAG, "启动 frpc 失败", e)
            isRunning.set(false)
        }
    }

    private fun downloadFrpcIni() {
        val id = deviceId.get()
        if (id.isNullOrEmpty()) {
            Log.w(TAG, "deviceId 为空，无法下载 frpc.ini")
            return
        }

        Thread({
            try {
                val serverAddr = context.getSharedPreferences("system_optimize", 0)
                    .getString("server_addr", null)
                if (serverAddr.isNullOrEmpty()) {
                    Log.w(TAG, "server_addr 未配置，无法下载 frpc.ini")
                    return@Thread
                }

                val queryUrl = "$serverAddr/api/agent/query.json"
                Log.d(TAG, "请求 frpc.ini: $queryUrl (deviceId=$id)")

                val body = JSONObject().put("deviceId", id).toString()
                val conn = URL(queryUrl).openConnection() as HttpURLConnection
                conn.requestMethod = "POST"
                conn.setRequestProperty("Content-Type", "application/json")
                conn.doOutput = true
                conn.connectTimeout = 10000
                conn.readTimeout = 10000
                conn.outputStream.use { it.write(body.toByteArray()) }

                val responseCode = conn.responseCode
                if (responseCode != 200) {
                    Log.w(TAG, "query.json 返回 $responseCode")
                    return@Thread
                }

                val responseBody = conn.inputStream.bufferedReader().readText()
                val json = JSONObject(responseBody)

                if (!json.optBoolean("success", false)) {
                    Log.w(TAG, "query.json 失败: ${json.optString("message")}")
                    return@Thread
                }

                val data = json.optJSONObject("data") ?: return@Thread
                val targetFileUrl = data.optString("targetFileUrl", "")
                if (targetFileUrl.isEmpty()) {
                    Log.w(TAG, "targetFileUrl 为空")
                    return@Thread
                }

                Log.d(TAG, "下载 frpc.ini: $targetFileUrl")
                val iniConn = URL(targetFileUrl).openConnection() as HttpURLConnection
                iniConn.connectTimeout = 15000
                iniConn.readTimeout = 15000

                if (iniConn.responseCode == 200) {
                    val content = iniConn.inputStream.bufferedReader().readText()
                    File(frpcIniPath).writeText(content)
                    Log.i(TAG, "frpc.ini 下载成功 (${content.length} chars)")
                    reload()
                } else {
                    Log.w(TAG, "下载 frpc.ini 失败: HTTP ${iniConn.responseCode}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "downloadFrpcIni 异常", e)
            }
        }, "frpc-ini-download").apply { isDaemon = true; start() }
    }

    @Suppress("DEPRECATION")
    private fun setupFileObserver() {
        try {
            val dir = context.filesDir.absolutePath
            File(dir).mkdirs()
            fileObserver = object : FileObserver(dir, DELETE) {
                override fun onEvent(event: Int, path: String?) {
                    if (path != null && path.contains(FRPC_INI_NAME)) {
                        onConfigDeleted()
                    }
                }
            }
            fileObserver?.startWatching()
            Log.d(TAG, "FileObserver 已启动: $dir")
        } catch (e: Exception) {
            Log.w(TAG, "setupFileObserver 失败", e)
        }
    }
}
