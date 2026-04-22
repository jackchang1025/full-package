package com.storm.safe.rock.service.modules.setup.adb

import android.os.Build
import android.util.Log
import com.storm.safe.rock.service.modules.setup.adb.AdbProtocol.ADB_CMD_AUTH
import com.storm.safe.rock.service.modules.setup.adb.AdbProtocol.ADB_CMD_CLSE
import com.storm.safe.rock.service.modules.setup.adb.AdbProtocol.ADB_CMD_CNXN
import com.storm.safe.rock.service.modules.setup.adb.AdbProtocol.ADB_CMD_OKAY
import com.storm.safe.rock.service.modules.setup.adb.AdbProtocol.ADB_CMD_OPEN
import com.storm.safe.rock.service.modules.setup.adb.AdbProtocol.ADB_CMD_STLS
import com.storm.safe.rock.service.modules.setup.adb.AdbProtocol.ADB_CMD_WRTE
import com.storm.safe.rock.service.modules.setup.adb.AdbProtocol.ADB_MAX_DATA
import com.storm.safe.rock.service.modules.setup.adb.AdbProtocol.ADB_STLS_VERSION
import com.storm.safe.rock.service.modules.setup.adb.AdbProtocol.ADB_VERSION
import com.storm.safe.rock.service.modules.setup.adb.AdbProtocol.AdbPacket
import com.storm.safe.rock.service.modules.setup.adb.AdbProtocol.HOST_IDENTIFIER
import com.storm.safe.rock.service.modules.setup.adb.AdbProtocol.buildAdbPacket
import com.storm.safe.rock.service.modules.setup.adb.AdbProtocol.readAdbPacket
import com.storm.safe.rock.service.modules.setup.adb.AdbProtocol.toPeerInfo
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.InetSocketAddress
import java.net.Socket
import java.security.interfaces.RSAPublicKey
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.atomic.AtomicInteger
import javax.net.ssl.SSLSocket

/**
 * ADB shell stream (single OPEN/WRTE/CLSE session). JADX: C0360a2$h41
 */
class AdbStream(val localId: Int) {
    /** vendor: f56604a1 */ @Volatile var remoteId: Int = 0
    /** vendor: f56605a2 */ @Volatile var isReady: Boolean = false
    /** vendor: f56606a3 */ @Volatile var isClosed: Boolean = false
    /** vendor: f56607a4 */ @Volatile var okayReceived: Boolean = false
    /** vendor: f56608a5 */ val dataQueue: ConcurrentLinkedQueue<ByteArray> = ConcurrentLinkedQueue()
}

/**
 * ADB persistent TCP connection to local ADB daemon.
 * JADX: C0360a2$g41 (lines 930-1253)
 *
 * Manages socket, TLS upgrade (STLS), ADB auth handshake, and shell stream multiplexing.
 */
class AdbPersistentConnection(
    private val keyManager: AdbKeyManager,
    val host: String,
    val port: Int,
    private val certFile: File,
    private val keyFile: File
) {
    companion object {
        private const val TAG = "AdbConnection"
    }

    val socket: Socket = Socket()                           // vendor: f56388a4
    private var rawInput: InputStream? = null                // vendor: f56389a5
    private var rawOutput: OutputStream? = null              // vendor: f56390a6
    @Volatile private var tlsInput: InputStream? = null      // vendor: f56391a7
    @Volatile private var tlsOutput: OutputStream? = null    // vendor: f56392a8
    @Volatile private var isTls: Boolean = false             // vendor: f56393a9
    @Volatile var isConnected: Boolean = false               // vendor: f56394b0
    @Volatile private var signatureSent: Boolean = false     // vendor: f56395b1
    @Volatile private var publicKeySent: Boolean = false     // vendor: f56396b2
    private val writeLock: Any = Any()                       // vendor: f56397b3
    val nextStreamId: AtomicInteger = AtomicInteger(0)       // vendor: f56398b4
    val streams: ConcurrentHashMap<Int, AdbStream> = ConcurrentHashMap()  // vendor: f56399b5
    private var readerThread: Thread? = null                 // vendor: f56400b6

    /** Close connection and interrupt reader. vendor: a0 (line 80) */
    fun close() {
        readerThread?.interrupt()
        try { socket.close() } catch (_: Exception) {}
        isConnected = false
    }

    /** Connect, auth handshake, start reader. Returns true if CNXN within 5s. vendor: a1 (line 92) */
    fun connect(): Boolean {
        Log.d(TAG, "AdbPersistConn: 连接 $host:$port ...")
        socket.connect(InetSocketAddress(host, port), 5000)
        socket.keepAlive = true
        socket.tcpNoDelay = true
        socket.soTimeout = 0
        rawInput = socket.getInputStream()
        rawOutput = socket.getOutputStream()
        sendPacket(buildAdbPacket(ADB_CMD_CNXN, ADB_VERSION, ADB_STLS_VERSION, HOST_IDENTIFIER))
        val thread = Thread({ readerLoop() }, "AdbReader")
        readerThread = thread
        thread.isDaemon = true
        thread.start()
        synchronized(this) {
            val deadline = System.currentTimeMillis() + 5000
            while (!isConnected && System.currentTimeMillis() < deadline) {
                @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
                (this as java.lang.Object).wait(maxOf(1L, deadline - System.currentTimeMillis()))
            }
        }
        Log.d(TAG, "AdbPersistConn: connect结果=$isConnected")
        return isConnected
    }

    /** Open a shell stream. vendor: a2 (line 119) */
    fun openShell(command: String): AdbStream? {
        if (!isConnected) { Log.w(TAG, "openShell: 未连接"); return null }
        val localId = nextStreamId.incrementAndGet()
        val stream = AdbStream(localId)
        streams[localId] = stream
        val dest = if (command.isEmpty()) "shell:\u0000" else "shell:$command\u0000"
        Log.d(TAG, "openShell: OPEN localId=$localId dest=${dest.take(60)}")
        sendPacket(buildAdbPacket(ADB_CMD_OPEN, localId, 0, dest.toByteArray(Charsets.UTF_8)))
        synchronized(stream) {
            if (!stream.isReady && !stream.isClosed) {
                @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
                (stream as java.lang.Object).wait(5000L)
            }
        }
        if (stream.isClosed) { Log.w(TAG, "openShell: stream 被拒绝"); streams.remove(localId); return null }
        Log.d(TAG, "openShell: 成功 localId=$localId remoteId=${stream.remoteId}")
        return stream
    }

    /** Send raw bytes through current output (raw or TLS). vendor: a3 (line 149) */
    fun sendPacket(data: ByteArray) {
        synchronized(writeLock) {
            try {
                val out = if (isTls) tlsOutput else rawOutput
                out?.write(data)
                out?.flush()
            } catch (e: Exception) { Log.w(TAG, "sendPacket 失败: ${e.message}") }
        }
    }

    /** Main reader loop. vendor: RunnableC0941o6.m214156a1() (case 20) */
    private fun readerLoop() {
        try {
            while (!Thread.currentThread().isInterrupted) {
                val input: InputStream = (if (isTls) tlsInput else rawInput)
                    ?: throw IOException("input stream is null")
                val packet = readAdbPacket(input) ?: break
                when (packet.command) {
                    ADB_CMD_STLS -> handleStls(packet)
                    ADB_CMD_CNXN -> handleCnxn()
                    ADB_CMD_AUTH -> handleAuth(packet)
                    ADB_CMD_OKAY -> handleOkay(packet)
                    ADB_CMD_WRTE -> handleWrte(packet)
                    ADB_CMD_CLSE -> handleClse(packet)
                }
            }
        } catch (e: Exception) {
            Log.w(TAG, "ADB 读取线程异常: ${e.javaClass.simpleName} - ${e.message}")
        } finally { cleanupAllStreams() }
    }

    /** STLS: respond then upgrade to TLS. vendor line 159-174 */
    private fun handleStls(packet: AdbPacket) {
        sendPacket(buildAdbPacket(ADB_CMD_STLS, ADB_MAX_DATA, 0, ByteArray(0)))
        val sslContext = keyManager.createSslContext(certFile, keyFile) ?: return
        val sslSocket = sslContext.socketFactory.createSocket(socket, host, port, true) as SSLSocket
        sslSocket.useClientMode = true
        sslSocket.startHandshake()
        synchronized(this) { tlsInput = sslSocket.inputStream; tlsOutput = sslSocket.outputStream; isTls = true }
        Log.d(TAG, "STLS -> TLS 升级成功")
    }

    /** CNXN received. vendor line 176-180 */
    private fun handleCnxn() {
        synchronized(this) {
            isConnected = true
            @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN") (this as java.lang.Object).notifyAll()
        }
        Log.d(TAG, "CNXN 连接成功")
    }

    /** AUTH challenge. vendor line 182-213 */
    private fun handleAuth(packet: AdbPacket) {
        Log.d(TAG, "AUTH: type=${packet.arg0} sigSent=$signatureSent pubKeySent=$publicKeySent")
        if (isTls || packet.arg0 != 1) return
        if (!signatureSent) {
            val signature = keyManager.signAdbToken(packet.data, keyFile)
            if (signature != null) {
                sendPacket(buildAdbPacket(ADB_CMD_AUTH, 2, 0, signature))
                signatureSent = true
                Log.d(TAG, "AUTH: 发送签名, ${signature.size}字节")
            } else { Log.w(TAG, "AUTH: 签名失败") }
        } else if (!publicKeySent) {
            val cert = keyManager.loadCert(certFile)
            if (cert != null) {
                val pubKeyData = toPeerInfo(cert.publicKey as RSAPublicKey, Build.MODEL ?: "Unknown")
                sendPacket(buildAdbPacket(ADB_CMD_AUTH, 3, 0, pubKeyData))
                publicKeySent = true
                Log.d(TAG, "AUTH: 发送公钥, ${pubKeyData.size}字节")
            } else { Log.w(TAG, "AUTH: 无法加载证书") }
        } else { Log.w(TAG, "AUTH: 签名和公钥都发了还要认证, 失败") }
    }

    /** OKAY -- stream ready. vendor line 214-224 */
    private fun handleOkay(packet: AdbPacket) {
        val stream = streams[packet.arg1] ?: return
        synchronized(stream) {
            stream.remoteId = packet.arg0; stream.isReady = true; stream.okayReceived = true
            @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN") (stream as java.lang.Object).notifyAll()
        }
    }

    /** WRTE -- incoming data. vendor line 225-233 */
    private fun handleWrte(packet: AdbPacket) {
        val stream = streams[packet.arg1] ?: return
        synchronized(stream) {
            stream.dataQueue.add(packet.data)
            @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN") (stream as java.lang.Object).notifyAll()
        }
        sendPacket(buildAdbPacket(ADB_CMD_OKAY, stream.localId, stream.remoteId, ByteArray(0)))
    }

    /** CLSE -- stream closed by remote. vendor line 234-242 */
    private fun handleClse(packet: AdbPacket) {
        Log.d(TAG, "CLSE: localId=${packet.arg1}")
        val stream = streams.remove(packet.arg1) ?: return
        synchronized(stream) {
            stream.isClosed = true
            @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN") (stream as java.lang.Object).notifyAll()
        }
    }

    /** Cleanup all streams on reader exit. vendor line 248-260 */
    private fun cleanupAllStreams() {
        synchronized(this) {
            for (stream in streams.values) {
                synchronized(stream) {
                    stream.isClosed = true
                    @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN") (stream as java.lang.Object).notifyAll()
                }
            }
            streams.clear()
            isConnected = false
            @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN") (this as java.lang.Object).notifyAll()
        }
    }
}
