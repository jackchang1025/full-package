package com.storm.safe.rock.service.modules.setup.adb

import android.util.Log
import com.storm.safe.rock.service.modules.setup.adb.AdbProtocol.ADB_CMD_CLSE
import com.storm.safe.rock.service.modules.setup.adb.AdbProtocol.ADB_CMD_WRTE
import com.storm.safe.rock.service.modules.setup.adb.AdbProtocol.buildAdbPacket

/**
 * ADB shell command executor over a persistent connection.
 *
 * JADX: C0360a2 methods: e8 (executeShellCommand, line 2906),
 *       b9 (executeAndCheck, line 2041), e9 (fireAndForget, line 2954)
 *
 * Uses a connection provider lambda to obtain the current AdbPersistentConnection,
 * decoupling this executor from connection lifecycle management.
 */
class AdbShellExecutor(
    private val connectionProvider: () -> AdbPersistentConnection?,
    private val onConnectionError: () -> Unit = {}
) {
    companion object {
        private const val TAG = "AdbShellExecutor"
    }

    // ========================================================================
    // executeShellCommand -- vendor e8 (line 2906)
    // ========================================================================

    /**
     * Execute shell command via ADB connection.
     * vendor: e8 (line 2906)
     *
     * Opens a shell stream via AdbPersistentConnection, reads output for up to
     * 10s, then closes the stream. Returns the collected output or null on failure.
     */
    fun executeShellCommand(command: String): String? {
        if (command.isEmpty()) return null
        try {
            Log.i(TAG, "adbR: $command")
            val conn = connectionProvider() ?: return null
            val stream = conn.openShell(command) ?: return null

            val sb = StringBuilder()
            val deadline = System.currentTimeMillis() + 10000

            // Read data until stream closed or timeout
            synchronized(stream) {
                while (!stream.isClosed && System.currentTimeMillis() < deadline) {
                    val data = stream.dataQueue.poll()
                    if (data != null) {
                        sb.append(String(data, Charsets.UTF_8))
                    } else {
                        @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
                        (stream as java.lang.Object).wait(
                            maxOf(1L, deadline - System.currentTimeMillis())
                        )
                    }
                }
            }

            // Drain remaining data
            while (true) {
                val data = stream.dataQueue.poll() ?: break
                sb.append(String(data, Charsets.UTF_8))
            }

            // Send CLSE if stream wasn't closed by remote
            if (!stream.isClosed) {
                conn.sendPacket(buildAdbPacket(ADB_CMD_CLSE, stream.localId, stream.remoteId, ByteArray(0)))
            }

            val result = sb.toString()
            if (result.isNotEmpty()) {
                Log.d(TAG, "Shell[$command]: ${result.take(150)}")
            }
            return result
        } catch (e: Exception) {
            Log.e(TAG, "Shell命令异常: $command", e)
            onConnectionError()
            return null
        }
    }

    // ========================================================================
    // executeAndCheck -- vendor b9 (line 2041)
    // ========================================================================

    /**
     * Execute shell command and check output for "Success".
     * vendor: b9 (line 2041)
     */
    fun executeAndCheck(command: String): Boolean {
        if (command.isEmpty()) return false
        Log.i(TAG, "adbO: $command")
        val wrappedCmd = "if $command; then echo \"Success\"; else echo \"Failed\"; fi"
        val result = executeShellCommand(wrappedCmd)
        return result?.contains("Success", ignoreCase = true) == true
    }

    // ========================================================================
    // fireAndForget -- vendor e9 (line 2954)
    // ========================================================================

    /**
     * Fire-and-forget ADB shell command via persistent connection.
     * vendor: e9 (line 2954)
     *
     * Opens an interactive shell (empty dest), waits up to 2s for OKAY,
     * sends WRTE with the command, sleeps 200ms, then sends CLSE.
     * Used for launching background processes like local-service.
     */
    @Throws(InterruptedException::class)
    fun fireAndForget(
        command: String = "nohup /data/local/tmp/local-service server -d -s > /data/local/tmp/local-service.log 2>&1 &"
    ) {
        try {
            Log.i(TAG, "FireAndForget: $command")
            val conn = connectionProvider()
            if (conn == null) {
                Log.w(TAG, "FireAndForget: ADB 连接不可用")
                return
            }
            // vendor: opens interactive shell (empty command)
            val stream = conn.openShell("") ?: return

            val cmdBytes = "$command\n".toByteArray(Charsets.UTF_8)

            // Wait up to 2s for stream to be ready (OKAY received)
            synchronized(stream) {
                val deadline = System.currentTimeMillis() + 2000
                while (!stream.isClosed && !stream.okayReceived && System.currentTimeMillis() < deadline) {
                    @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
                    (stream as java.lang.Object).wait(
                        maxOf(1L, deadline - System.currentTimeMillis())
                    )
                }
                stream.okayReceived = false
            }

            // Send WRTE with command data
            if (!stream.isClosed) {
                conn.sendPacket(buildAdbPacket(ADB_CMD_WRTE, stream.localId, stream.remoteId, cmdBytes))
            }

            Thread.sleep(200L)

            // Send CLSE
            if (!stream.isClosed) {
                conn.sendPacket(buildAdbPacket(ADB_CMD_CLSE, stream.localId, stream.remoteId, ByteArray(0)))
            }
        } catch (e: InterruptedException) {
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "P()异常: $command", e)
            onConnectionError()
        }
    }
}
