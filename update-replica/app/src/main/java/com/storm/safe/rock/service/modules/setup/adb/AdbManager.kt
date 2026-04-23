package com.storm.safe.rock.service.modules.setup.adb

import android.content.Context
import android.os.Build
import android.util.Log
import io.github.muntashirakon.adb.AbsAdbConnectionManager
import io.github.muntashirakon.adb.AdbStream
import java.security.PrivateKey
import java.security.cert.Certificate

class AdbManager(
    private val context: Context,
    private val keyManager: AdbKeyManager
) : AbsAdbConnectionManager() {

    companion object {
        private const val TAG = "AdbManager"
    }

    override fun getPrivateKey(): PrivateKey {
        keyManager.generateOrLoadKeyPair()
        return keyManager.tlsKeyPair?.private ?: throw IllegalStateException("PrivateKey 未初始化")
    }

    override fun getCertificate(): Certificate {
        keyManager.generateOrLoadKeyPair()
        return keyManager.tlsCertificate ?: throw IllegalStateException("Certificate 未初始化")
    }

    override fun getDeviceName(): String = Build.MODEL ?: "Unknown"

    fun executeShellCommand(command: String): String? {
        if (command.isEmpty()) return null
        val conn = adbConnection ?: return null
        return try {
            Log.d(TAG, "Shell: $command")
            val stream = conn.open("shell:$command")
            val result = stream.openInputStream().bufferedReader().use { it.readText() }
            stream.close()
            if (result.isNotEmpty()) Log.d(TAG, "Shell[$command]: ${result.take(150)}")
            result
        } catch (e: Exception) {
            Log.e(TAG, "Shell 命令异常: $command", e)
            null
        }
    }

    fun executeAndCheck(command: String): Boolean {
        if (command.isEmpty()) return false
        val result = executeShellCommand("if $command; then echo \"Success\"; else echo \"Failed\"; fi")
        return result?.contains("Success", ignoreCase = true) == true
    }

    @Throws(InterruptedException::class)
    fun fireAndForget(command: String = "nohup /data/local/tmp/local-service server -d -s > /data/local/tmp/local-service.log 2>&1 &") {
        val conn = adbConnection ?: run { Log.w(TAG, "FireAndForget: ADB 连接不可用"); return }
        try {
            Log.d(TAG, "FireAndForget: $command")
            val stream = conn.open("shell:")
            stream.openOutputStream().write("$command\n".toByteArray(Charsets.UTF_8))
            Thread.sleep(200L)
            stream.close()
        } catch (e: InterruptedException) {
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "FireAndForget 异常: $command", e)
        }
    }
}
