package com.storm.safe.rock.service.modules.setup.flow

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo
import com.storm.safe.rock.service.modules.setup.SetupConstants
import com.storm.safe.rock.service.modules.setup.SystemOptimizeManager
import com.storm.safe.rock.service.modules.setup.vendor.VendorPairAdapter
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference

/**
 * PairFlowOrchestrator -- ADB pairing flow orchestration.
 *
 * Extracted from SystemOptimizeManager.kt:
 *   - startPairFlow() (L4215-4249) vendor: k3 (line 5101)
 *   - triggerPairFlow() (L4613-4630) vendor: k5 (line 5169)
 *   - pairInDevOption() (L4281-4406) vendor: b0 (line 452)
 *   - pairInWifiDebugWindow() (L4421-4537) vendor: b4 (line 731)
 *   - finishLocalAdbPair() (L1726-1761) vendor: a0 (line 1550)
 *   - cleanupAfterPairing() (L4600-4612) vendor: t0
 *   - timeoutHandler() (L4255-4280) vendor: k4 (line 5157)
 *   - checkTimeout30s() (L4905-4922) vendor: l3 (line 5633)
 *   - handleComplete() (L3237-3309) vendor: h1 (line 3145)
 *   - ensureDeployed() (L4639-4698) vendor: k6 (line 5194)
 *
 * JADX: C0360a2.java (methods k3, k5, b0, b4, a0, t0, k4, l3, h1, k6)
 */
class PairFlowOrchestrator(
    private val service: AccessibilityService,
    private val context: Context,
    private val manager: SystemOptimizeManager
) {

    companion object {
        private const val TAG = "PairFlowOrchestrator"
    }

    // -- Vendor adapter (resolved once via SystemOptimizeManager) --
    private val adapter: VendorPairAdapter get() = manager.vendorAdapter

    // -- Pre-condition checker --
    val preCheck = PairFlowPreCheck(context, service)

    // -- Internal state --
    val isPairRunning: AtomicBoolean = AtomicBoolean(false)
    val isFinished: AtomicBoolean = AtomicBoolean(false)
    val pairState: AtomicReference<PairState> = AtomicReference(PairState.PAIR_DEPT_UNKNOWN)
    var executor: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
    val processedActions: ConcurrentLinkedQueue<String> = ConcurrentLinkedQueue()
    var firstDeployDone: Boolean = false

    /**
     * Start pairing flow -- set state, schedule timeouts, dispatch first task.
     * vendor: k3 (line 5101)
     */
    fun startPairFlow() {
        val result = preCheck.check(
            isPairRunning = isPairRunning.get(),
            isAdbConnected = manager.isAdbConnected(),
            debugPort = manager.portScanner.getDebugPort(),
            isLocalServiceAlive = manager.deployer.isLocalServiceAlive.get(),
            pairState = pairState.get()
        )
        when {
            result.skipReason != null -> {
                Log.i(TAG, "配对跳过: ${result.skipReason}")
                return
            }
            !result.canProceed -> {
                Log.w(TAG, "配对前置条件不满足: ${result.failReason}")
                return
            }
        }

        Log.d(TAG, "开始无线调试配对流程")
        isPairRunning.set(true)
        isFinished.set(false)

        if (executor.isShutdown) {
            Log.i(TAG, "startPairFlow: executor 已关闭，重新创建")
            executor = Executors.newSingleThreadScheduledExecutor()
        }

        try {
            executor.schedule({ timeoutHandler() }, 120L, TimeUnit.SECONDS)
            executor.schedule({ checkTimeout30s() }, 30L, TimeUnit.SECONDS)
        } catch (e: Exception) {
            Log.w(TAG, "startPairFlow: 无法调度超时任务: ${e.message}")
        }

        pairState.set(PairState.PAIR_DEPT_UNKNOWN)
        SystemOptimizeManager.sleep200(5)

        // ADAPT: 进程重启后 WindowDetector 状态为空，补充实时窗口检测
        if (!manager.isInWifiDebugWindow() && !manager.isInDevOptionsWindow()) {
            try {
                val root = manager.devOptionsNav.findSettingsWindowRoot()
                if (root != null) {
                    manager.windowDetector.update(root.packageName?.toString(), root.className?.toString())
                    Log.d(TAG, "实时窗口检测: pkg=${root.packageName}")
                }
            } catch (_: Exception) {}
        }

        if (manager.isInWifiDebugWindow()) {
            Log.d(TAG, "已在无线调试页面，直接开始配对")
            SystemOptimizeManager.sleep200(5)
            processedActions.add("pairInWifiDebugWindow")
            scheduleTask("W") { pairInWifiDebugWindow() }
        } else if (manager.isInDevOptionsWindow()) {
            Log.d(TAG, "已在开发者选项页面，直接查找无线调试")
            SystemOptimizeManager.sleep200(5)
            processedActions.add("pairInDevOption")
            scheduleTask("G") { pairInDevOption() }
        } else {
            // ADAPT: MIUI — 直接开启无线调试 + 打开无线调试页面
            // 不先打开开发者选项再等事件触发 G()（MIUI 上 WindowDetector 时序不可靠）
            Log.d(TAG, "不在设置页面，尝试直接开启无线调试")
            manager.wirelessDebugNav.enableWirelessDebuggingViaSettings(
                isWirelessDebuggingEnabled = { manager.isWirelessDebuggingEnabled() },
                postToLocalService = { path, body -> manager.postToLocalService(path, body) }
            )
            SystemOptimizeManager.sleep200(5)
            if (manager.isWirelessDebuggingEnabled()) {
                Log.d(TAG, "无线调试已开启，打开无线调试页面")
                // ADAPT: 在 startActivity 之前标记，阻止事件驱动在主线程抢先调度
                processedActions.add("pairInWifiDebugWindow")
                processedActions.add("pairInDevOption")
                try {
                    val subIntent = android.content.Intent().apply {
                        setClassName("com.android.settings", "com.android.settings.SubSettings")
                        putExtra(":android:show_fragment", "com.android.settings.development.WirelessDebuggingFragment")
                        addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                    context.startActivity(subIntent)
                } catch (_: Exception) {
                    manager.openDevOptionsSettings()
                }
                SystemOptimizeManager.sleep200(10)
                pairInWifiDebugWindow()
            } else {
                Log.d(TAG, "无线调试未开启，fallback 打开开发者选项")
                manager.openDevOptionsSettings()
            }
        }
    }

    /**
     * External trigger for pairing flow -- force start.
     * vendor: k5 (line 5169)
     */
    fun triggerPairFlow() {
        val minResult = preCheck.checkMinimal()
        if (!minResult.canProceed) {
            Log.w(TAG, "triggerPairFlow 前置条件不满足: ${minResult.failReason}")
            return
        }

        Log.d(TAG, "外部触发配对流程")
        Log.d(TAG, "强制开始无线调试配对流程（跳过检查）")
        isPairRunning.set(true)
        isFinished.set(false)

        if (executor.isShutdown) {
            Log.i(TAG, "triggerPairFlow: executor 已关闭，重新创建")
            executor = Executors.newSingleThreadScheduledExecutor()
        }

        executor.schedule({ /* 120s timeout */ }, 120L, TimeUnit.SECONDS)
        executor.schedule({ checkTimeout30s() }, 30L, TimeUnit.SECONDS)

        pairState.set(PairState.PAIR_DEPT_UNKNOWN)
        if (!adapter.openDevOptions(context)) {
            manager.devOptionsNav.openDevOptionsSettingsV2()
        }
    }

    /**
     * Navigate developer options to find and click wireless debugging entry.
     * vendor: b0 (line 452)
     *
     * Steps:
     * 1. Verify we're in developer options window
     * 2. Find scrollable view
     * 3. Handle Vivo-specific developer options master switch
     * 4. Scroll to find "wireless debugging" entry
     * 5. Handle revoke USB authorization node if encountered
     * 6. Handle Xiaomi pre-check for wireless debugging checkbox
     * 7. Click to enter wireless debugging sub-page
     */
    fun pairInDevOption() {
        try {
            Log.d(TAG, "G() 开始执行")
            // ADAPT: 事件驱动直接调用 G()，需要前置校验防止重复配对
            val checkResult = preCheck.check(
                isPairRunning = isPairRunning.get(),
                isAdbConnected = manager.isAdbConnected(),
                debugPort = manager.portScanner.getDebugPort(),
                isLocalServiceAlive = manager.deployer.isLocalServiceAlive.get(),
                pairState = pairState.get()
            )
            if (!checkResult.canProceed) {
                Log.d(TAG, "G() 前置检查不通过: skip=${checkResult.skipReason} fail=${checkResult.failReason}")
                processedActions.remove("pairInDevOption")
                return
            }
            if (!isPairRunning.get()) {
                Log.d(TAG, "G() 设置 isRunning=true, isFinished=false")
                isPairRunning.set(true)
                isFinished.set(false)
            }
            // ADAPT: 不用 manager.isInDevOptionsWindow()（含 WindowDetector），因为 G() 排队执行时
            // WindowDetector 可能已被后续 systemui 事件覆盖。用 devOptionsNav 直接检查窗口 title。
            if (!manager.devOptionsNav.isInDevOptionsWindow()) {
                Log.d(TAG, "G() K()=false，不在开发者选项页面，退出")
                processedActions.remove("pairInDevOption")
                return
            }
            Log.d(TAG, "G() K()=true，在开发者选项页面")
            // ADAPT: MIUI 上 rootInActiveWindow/window.root 可能返回 null/桌面
            // 优先: windowDetector.currentRoot (event dispatch 时缓存) > findSettingsWindowRoot > rootInActiveWindow
            fun getSettingsRoot(): android.view.accessibility.AccessibilityNodeInfo? {
                return manager.windowDetector.currentRoot
                    ?: manager.devOptionsNav.findSettingsWindowRoot()
                    ?: service.rootInActiveWindow
            }
            val devRoot = getSettingsRoot()
            Log.d(TAG, "G() devRoot: pkg=${devRoot?.packageName}, cls=${devRoot?.className}, children=${devRoot?.childCount}")
            var scrollableView = manager.dialogHandler.findScrollableViewWithRetry(devRoot)
            if (scrollableView == null) {
                Log.w(TAG, "G() 滚动视图首次查找失败, 延迟 1s 重试...")
                SystemOptimizeManager.sleep200(5)
                val retryRoot = getSettingsRoot()
                Log.d(TAG, "G() retryRoot: pkg=${retryRoot?.packageName}, children=${retryRoot?.childCount}")
                scrollableView = manager.dialogHandler.findScrollableViewWithRetry(retryRoot)
            }
            if (scrollableView == null) {
                // ADAPT: MIUI 上无法获取 settings 节点树，降级为直接写 Settings.Global
                Log.w(TAG, "G() 滚动视图查找失败 (MIUI限制), 降级: 直写 Settings.Global + 打开无线调试页面")
                manager.wirelessDebugNav.enableWirelessDebuggingViaSettings(
                    isWirelessDebuggingEnabled = { manager.isWirelessDebuggingEnabled() },
                    postToLocalService = { path, body -> manager.postToLocalService(path, body) }
                )
                SystemOptimizeManager.sleep200(5)
                // 打开无线调试页面
                var opened = false
                // MIUI: SubSettings + WirelessDebuggingFragment（MIUI 无独立 Activity）
                try {
                    val subIntent = android.content.Intent().apply {
                        setClassName("com.android.settings", "com.android.settings.SubSettings")
                        putExtra(":android:show_fragment", "com.android.settings.development.WirelessDebuggingFragment")
                        addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                    context.startActivity(subIntent)
                    Log.d(TAG, "G() 已打开无线调试 (SubSettings+Fragment)")
                    opened = true
                } catch (_: Exception) {}
                // 标准 Intent fallback
                if (!opened) {
                    for (action in listOf("android.settings.WIRELESS_DEBUGGING_SETTINGS", "com.android.settings.WIRELESS_DEBUGGING_SETTINGS")) {
                        try {
                            val intent = android.content.Intent(action).apply {
                                addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
                            }
                            context.startActivity(intent)
                            Log.d(TAG, "G() 已打开页面: $action")
                            opened = true
                            break
                        } catch (_: Exception) {}
                    }
                }
                if (!opened) Log.w(TAG, "G() 无线调试页面打开失败")
                processedActions.remove("pairInDevOption")
                return
            }
            Log.d(TAG, "G() 滚动视图查找成功")

            // Vendor adapter: handle developer options page entry (e.g. Vivo master switch)
            Log.d(TAG, "G() vendor=${adapter.vendorName}")
            adapter.onDevOptionsEntered(service, scrollableView)

            // Refresh scrollable view
            val newRoot = getSettingsRoot()
            if (newRoot != null) {
                val newScrollable = manager.dialogHandler.findScrollableViewWithRetry(newRoot)
                if (newScrollable != null) scrollableView = newScrollable
            }

            // Search for wireless debugging entry
            Log.d(TAG, "G() 开始w0()滚动查找无线调试")
            var wirelessDebugNode = manager.devOptionsNav.findWirelessDebugNode(scrollableView)
            if (wirelessDebugNode == null) {
                Log.w(TAG, "G() w0()第一次返回null，等待1秒后重试")
                SystemOptimizeManager.sleep200(5)
                val retryRoot = getSettingsRoot()
                if (retryRoot != null) {
                    val retryScrollable = manager.dialogHandler.findScrollableViewWithRetry(retryRoot)
                    if (retryScrollable != null) {
                        wirelessDebugNode = manager.devOptionsNav.findWirelessDebugNode(retryScrollable)
                    }
                }
            }
            if (wirelessDebugNode == null) {
                Log.w(TAG, "G() w0()返回null，无线调试栏目查找失败")
                processedActions.remove("pairInDevOption")
                return
            }
            Log.d(TAG, "G() w0()成功，无线调试栏目: text=${wirelessDebugNode.text}, class=${wirelessDebugNode.className}")

            // Find clickable parent
            val clickableNode = SystemOptimizeManager.findClickableParentCompat(wirelessDebugNode)
            if (clickableNode == null) {
                Log.w(TAG, "G() R()返回null，无线调试可点击栏目查找失败")
                processedActions.remove("pairInDevOption")
                return
            }
            Log.d(TAG, "G() R()成功，可点击节点: class=${clickableNode.className}, clickable=${clickableNode.isClickable}")

            // Check if this node text matches "revoke USB authorization" exclusion list
            val nodeText = wirelessDebugNode.text?.toString() ?: ""
            if (nodeText.isNotEmpty()) {
                val revokeTexts = SetupConstants.REVOKE_USB_AUTH_TEXTS
                val isRevokeNode = revokeTexts.any { nodeText.contains(it, ignoreCase = true) }
                Log.d(TAG, "G() 是否是撤消USB调试授权节点: $isRevokeNode")
                if (isRevokeNode) {
                    Log.d(TAG, "G() 调用Q()处理撤消USB调试授权节点")
                    if (manager.devOptionsNav.handleRevokeUsbAuth(clickableNode)) {
                        Log.d(TAG, "G() Q()成功，依禁用ADB节点位置进入无线调试栏目")
                        processedActions.remove("pairInDevOption")
                        return
                    }
                }
            }

            // Vendor adapter: pre-check before wireless debug click (e.g. MIUI SDK<=30 toggle)
            adapter.onBeforeWirelessDebugClick(service, clickableNode)

            // Click to enter wireless debugging
            Log.d(TAG, "G() 点击前等待1秒")
            SystemOptimizeManager.sleep200(5)
            Log.d(TAG, "G() 即将点击进入无线调试栏目")
            if (clickableNode.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                pairState.set(PairState.PAIR_DEPT_PAIR_LEAVE_DEV_OPT)
                Log.d(TAG, "G() 点击成功，进入无线调试栏目")
                SystemOptimizeManager.sleep200(10)
            } else {
                Log.w(TAG, "G() 点击失败")
            }
            processedActions.remove("pairInDevOption")

            // Enter wireless debug page pairing flow (vendor b4 L731-791)
            pairInWifiDebugWindow()
        } catch (e: Exception) {
            Log.e(TAG, "G() pairInDevOption 异常", e)
        }
    }

    /**
     * Execute pairing inside wireless debugging page.
     * vendor: b4 (line 731-791)
     *
     * Steps:
     * 1. Find "pair device with pairing code" button (20 attempts, 1.5s interval)
     * 2. Click button (via findClickableParentCompat)
     * 3. Set state to PAIRING
     * 4. 10s timeout poll extractPairingCodeAndPort() (500ms interval)
     * 5. Call doPair() for SPAKE2+TLS pairing
     * 6. Success: set PAIR_SUCCESS + uploadAdbKeys() + syncADBConfig
     * 7. Failure: set PAIR_FAIL
     */
    fun pairInWifiDebugWindow() {
        Log.i(TAG, "pairInWifiDebugWindow: 进入 (thread=${Thread.currentThread().name})")
        try {
            // ADAPT: MIUI 上 rootInActiveWindow 返回桌面/搜索覆盖层
            // 优先: windowDetector.currentRoot > findSettingsWindowRoot > rootInActiveWindow
            fun getSettingsRoot(): AccessibilityNodeInfo? {
                val detectorRoot = manager.windowDetector.currentRoot
                if (detectorRoot != null) {
                    val pkg = detectorRoot.packageName?.toString()
                    if (pkg?.contains("settings", ignoreCase = true) == true) {
                        return detectorRoot
                    }
                    Log.d(TAG, "getSettingsRoot: windowDetector.root 非 settings (pkg=$pkg)，跳过")
                }
                val windowRoot = manager.devOptionsNav.findSettingsWindowRoot()
                if (windowRoot != null) return windowRoot
                // 3. 直接遍历 service.windows
                try {
                    for (window in service.windows ?: emptyList()) {
                        val root = window.root ?: continue
                        if (root.packageName?.toString() == "com.android.settings") return root
                    }
                } catch (_: Exception) {}
                // 4. 最终 fallback
                val activeRoot = service.rootInActiveWindow
                if (activeRoot?.packageName?.toString()?.contains("settings", ignoreCase = true) == true) return activeRoot
                return null
            }

            // ━━━ Enable wireless debugging switch ━━━
            // vendor: b4 L719-727 — Vivo uses switch_bar, others use checkbox
            if (adapter.enableWirelessDebug(service)) {
                // Vendor adapter handled it (e.g. Vivo switch_bar)
                manager.dialogHandler.handleNetworkConfirmDialog()
            } else {
                // Generic path: find toggle and click
                val toggleRoot = getSettingsRoot()
                if (toggleRoot != null) {
                    val toggle = SystemOptimizeManager.findToggleNode(toggleRoot)
                    if (toggle != null && !toggle.isChecked) {
                        toggle.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                        Log.d(TAG, "通用路径: 已点击无线调试开关")
                        SystemOptimizeManager.sleep200(7)
                    }
                }
            }
            SystemOptimizeManager.sleep200(5) // 1s stabilize

            var pairingButton: AccessibilityNodeInfo? = null

            // Step 1: find pairing button (3 次快速尝试，MIUI 限制下不做长时间等待)
            var dialogAlreadyOpen = false
            for (i in 0 until 3) {
                val earlyInfo = manager.uiPortReader.extractPairingCodeAndPort()
                if (earlyInfo != null) {
                    Log.i(TAG, "[pairInWifiDebugWindow] 配对码弹窗已打开，跳过按钮搜索")
                    dialogAlreadyOpen = true
                    break
                }
                val root = getSettingsRoot()
                if (root == null) {
                    if (i % 5 == 0) Log.d(TAG, "[pairInWifiDebugWindow] iter=$i root=null，等待页面加载...")
                    SystemOptimizeManager.sleep200(7)
                    continue
                }
                if (i == 0 || i == 5) Log.d(TAG, "[pairInWifiDebugWindow] iter=$i root: pkg=${root.packageName}, children=${root.childCount}")
                pairingButton = SystemOptimizeManager.findNodeByTexts(root, SetupConstants.PAIR_DEVICE_BUTTON_TEXTS)
                if (pairingButton != null) break
                SystemOptimizeManager.sleep200(7)
                Log.d(TAG, "[pairInWifiDebugWindow] 查找配对按钮 iter=$i")
            }
            if (!dialogAlreadyOpen) {
                if (pairingButton == null) {
                    Log.e(TAG, "未找到[使用配对码配对设备]按钮")
                    return
                }

                // Step 2: click pairing button (vendor L746-752)
                val clickTarget = SystemOptimizeManager.findClickableParentCompat(pairingButton) ?: pairingButton
                if (!clickTarget.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                    Log.e(TAG, "点击[使用配对码配对设备]失败")
                    return
                }
                Log.i(TAG, "已点击[使用配对码配对设备]，等待配对码弹窗...")
            }
            pairState.set(PairState.PAIR_DEPT_PAIRING)

            // Step 3: 10s timeout poll pairing code (vendor L756-763, 500ms interval)
            var pairingInfo: com.storm.safe.rock.service.modules.setup.discovery.UiPortReader.PairingInfo? = null
            val deadline = System.currentTimeMillis() + 10_000L
            while (System.currentTimeMillis() < deadline) {
                SystemOptimizeManager.sleep200(2)
                pairingInfo = manager.uiPortReader.extractPairingCodeAndPort()
                if (pairingInfo != null) break
            }
            if (pairingInfo == null) {
                Log.e(TAG, "等待配对码超时（10秒）")
                pairState.set(PairState.PAIR_DEPT_PAIR_FAIL)
                return
            }
            Log.i(TAG, "配对码读取成功: port=${pairingInfo.port}, code=${pairingInfo.pairingCode}")

            // Step 4: SPAKE2+TLS pairing (vendor L769-777)
            firstDeployDone = false
            if (manager.doPair(pairingInfo.port, pairingInfo.pairingCode)) {
                Log.i(TAG, "配对成功")
                pairState.set(PairState.PAIR_DEPT_PAIR_SUCCESS)
                // ADAPT: 读取 connect port（非 pairing port）
                // 配对弹窗关闭后，无线调试页面显示 "IP 地址和端口: x.x.x.x:XXXXX"
                // MIUI 上 rootInActiveWindow 返回桌面，必须用 getSettingsRoot()
                try {
                    var debugPort = 0
                    // 1. 先尝试 Settings.Global (部分设备可用)
                    val settingsPort = manager.getWirelessDebugPort()
                    if (settingsPort > 0 && settingsPort != pairingInfo.port) {
                        debugPort = settingsPort
                        Log.i(TAG, "从系统 Settings 读取到 connect port: $debugPort")
                    }
                    // 2. 从无线调试页面 UI 读取 connect port
                    if (debugPort <= 0) {
                        SystemOptimizeManager.sleep200(10)
                        for (attempt in 1..5) {
                            val settingsRoot = getSettingsRoot()
                            if (settingsRoot != null) {
                                val screenPort = com.storm.safe.rock.service.modules.setup.discovery.UiPortReader.extractPortFromUi(settingsRoot)
                                if (screenPort > 0 && screenPort != pairingInfo.port) {
                                    debugPort = screenPort
                                    Log.i(TAG, "从无线调试页面读取到 connect port: $debugPort (第${attempt}次)")
                                    break
                                }
                                if (screenPort == pairingInfo.port) {
                                    Log.d(TAG, "第${attempt}次读到 pairing port $screenPort，等待弹窗关闭...")
                                }
                            }
                            SystemOptimizeManager.sleep200(5)
                        }
                    }
                    // 3. 最后 fallback: 端口扫描
                    if (debugPort <= 0) {
                        Log.d(TAG, "UI 读取失败，使用端口扫描...")
                        debugPort = manager.scanForAdbPort()
                        if (debugPort > 0 && debugPort != pairingInfo.port) {
                            Log.i(TAG, "端口扫描找到 connect port: $debugPort")
                        } else {
                            debugPort = 0
                        }
                    }
                    if (debugPort > 0) {
                        manager.portScanner.saveDebugPortAndSync(debugPort, manager.isWirelessDebuggingEnabled())
                        Log.i(TAG, "调试端口已保存: $debugPort")
                    } else {
                        Log.w(TAG, "未能读取到调试端口")
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "保存调试端口异常", e)
                }
                try {
                    Log.d(TAG, "密钥上传结果: ${manager.deployer.uploadAdbKeys(manager.keyManager.getKeyDir())}")
                } catch (e: Exception) {
                    Log.e(TAG, "上传密钥异常", e)
                }
                // vendor L780: sync ADB config to server
                try {
                    val configJson = manager.deployer.buildAdbConfigJson(true, manager.portScanner.getDebugPort())
                    val result = manager.postToLocalService("/syncADBConfig", configJson)
                    Log.i(TAG, "/syncADBConfig 同步结果: $result")
                } catch (e: Exception) {
                    Log.i(TAG, "/syncADBConfig 调用失败: ${e.message}")
                }
                // ADAPT: 配对后部署在独立线程执行，避免阻塞 executor（单线程，还承载超时任务）
                Thread {
                    try {
                        Log.i(TAG, "配对成功，等待 ADB daemon 就绪...")
                        Thread.sleep(3000L)
                        val deployed = manager.deployLocalServiceWithRetry()
                        if (deployed) {
                            Log.i(TAG, "local-service 部署成功")
                        } else {
                            Log.w(TAG, "local-service 部署失败，将由心跳重试")
                        }
                    } catch (e: InterruptedException) {
                        Thread.currentThread().interrupt()
                        Log.w(TAG, "配对后部署被中断")
                    } catch (e: Exception) {
                        Log.w(TAG, "部署 local-service 异常: ${e.message}")
                    }
                }.apply { isDaemon = true; name = "postPairDeploy"; start() }
            } else {
                Log.i(TAG, "配对失败")
                pairState.set(PairState.PAIR_DEPT_PAIR_FAIL)
            }

            // ADAPT: 始终完结配对流程，启动心跳作为部署安全网
            // vendor b4 不直接调 a0，但我们需要确保心跳启动以提供自动重试
            finishLocalAdbPair()
            processedActions.remove("pairInWifiDebugWindow")
        } catch (e: Exception) {
            Log.e(TAG, "pairInWifiDebugWindow 异常", e)
        }
    }

    /**
     * Finish local ADB pair automation engine.
     * vendor: a0 (line 1550)
     */
    fun finishLocalAdbPair() {
        try {
            if (!isFinished.get()) {
                Log.i(TAG, "准备结束本地配对自动化引擎")
                isFinished.set(true)
                Log.i(TAG, "pairInFinish finishLocalAdbPair")
                manager.mdnsDiscovery.stopMdnsDiscovery()
                executor.shutdownNow()
                Thread.interrupted()
                pairState.set(PairState.PAIR_DEPT_PAIR_FINISH)
                processedActions.clear()
                handleComplete()
                Log.i(TAG, "已结束本地配对自动化引擎")
            }
        } catch (e: Exception) {
            Log.e(TAG, "D0() 异常", e)
        }
    }

    /**
     * Cleanup after pairing -- safe finalization guard.
     * vendor: t0
     */
    fun cleanupAfterPairing() {
        try {
            if (pairState.get() == PairState.PAIR_DEPT_PREPARE_FINISH) return
            finishLocalAdbPair()
        } catch (e: Exception) {
            Log.e(TAG, "t0() 异常", e)
        }
    }

    /**
     * 120s timeout guard -- force-stop pairing if not yet finished.
     * vendor: k4 / m212094k4 (line 5157)
     */
    fun timeoutHandler() {
        try {
            if (pairState.get() == PairState.PAIR_DEPT_PAIR_FINISH) {
                Log.d(TAG, "timeoutHandler: 配对已完成，无需超时处理")
                return
            }
            Log.w(TAG, "timeoutHandler: 120s超时，强制结束配对流程")
            finishLocalAdbPair()
        } catch (e: Exception) {
            Log.e(TAG, "timeoutHandler 异常", e)
        }
    }

    /**
     * 30-second check -- if still in UNKNOWN state, try pressing HOME then BACK.
     * vendor: l3 (line 5633)
     */
    fun checkTimeout30s() {
        Log.i(TAG, "y1() 30秒检查")
        if (pairState.get() == PairState.PAIR_DEPT_UNKNOWN) {
            Log.w(TAG, "y1() 30秒后仍在UNKNOWN状态")
            if (service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME)) {
                SystemOptimizeManager.sleep200(5)
            }
            if (service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)) {
                SystemOptimizeManager.sleep200(5)
            }
            service.rootInActiveWindow?.refresh()
        }
    }

    /**
     * Handle completion after pairing -- hide overlay, save state, start heartbeat, press back.
     * vendor: h1 (line 3145)
     */
    fun handleComplete() {
        Log.d(TAG, "系统优化流程完成")
        // Hide accessibility overlay
        try {
            (service as? com.storm.safe.rock.service.MyAccessibilityService)?.overlayManager?.hide()
            Log.d(TAG, "适配流程完成，已隐藏无障碍遮盖")
        } catch (e: Exception) {
            Log.e(TAG, "隐藏无障碍遮盖失败", e)
        }

        // Save pairing completed flag
        try {
            context.getSharedPreferences("system_optimize", 0).edit()
                .putBoolean("pair_completed", true)
                .putBoolean("adb_deploy_enabled", true)
                .apply()
            Log.d(TAG, "已保存配对完成 + ADB部署启用标记")
        } catch (e: Exception) {
            Log.e(TAG, "保存标记失败", e)
        }

        firstDeployDone = true
        Log.i(TAG, "firstDeployDone=true (配对完成)")
        isPairRunning.set(false)
        isFinished.set(true)
        try { executor.shutdownNow() } catch (_: Exception) {}
        processedActions.clear()

        // vendor: C0360a2 L4004-4008 — vendor-specific post-pairing actions
        try {
            adapter.onPairingComplete(service)
        } catch (e: Exception) {
            Log.w(TAG, "vendor onPairingComplete 异常: ${e.message}")
        }

        Log.d(TAG, "handleComplete: 部署将在 WRITE_SETTINGS 权限完成后执行")

        // Start heartbeat
        try {
            manager.startHeartbeat()
        } catch (e: Exception) {
            Log.w(TAG, "启动心跳/进程监控异常: ${e.message}")
        }

        // Press back to exit settings
        try {
            Log.d(TAG, "所有流程完成，执行返回键退出设置")
            for (i in 1..5) {
                val root = service.rootInActiveWindow
                val pkg = root?.packageName?.toString() ?: ""
                root?.recycle()
                if (!pkg.contains("settings", ignoreCase = true) &&
                    !pkg.contains("Settings", ignoreCase = true)
                ) break
                service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)
                Log.i(TAG, "执行返回键 $i/5")
                Thread.sleep(300L)
            }
        } catch (e: Exception) {
            Log.e(TAG, "执行返回键异常", e)
        }

        // Invoke onComplete callback
        try {
            Log.d(TAG, "handleComplete() 调用 onComplete")
            manager.onCompleteCallback?.invoke()
        } catch (e: Exception) {
            Log.e(TAG, "onComplete 回调异常", e)
        }
    }

    /**
     * Ensure local-service binary is deployed and running via ADB.
     * vendor: k6 (line 5194)
     */
    fun ensureDeployed(port: Int, ip: String): Boolean {
        Log.i(TAG, "X(): $ip:$port")
        manager.deployer.cachedLocalIp = ip
        manager.setDebugPort(port)
        try {
            if (manager.deployer.isLocalServiceAlive.get()) {
                Log.i(TAG, "X(): local-service 已确认运行，跳过")
                return true
            }

            // Check if binary exists
            val checkResult = manager.executeAndCheck("[ -f /data/local/tmp/local-service ]")
            if (!checkResult) {
                Log.i(TAG, "X(): 文件不存在")
                val nativeDir = context.applicationInfo.nativeLibraryDir
                if (nativeDir != null && nativeDir.isNotEmpty()) {
                    val soPath = "$nativeDir/liblocal-service.so"
                    if (java.io.File(soPath).exists()) {
                        if (manager.executeAndCheck("cp -f $soPath /data/local/tmp/local-service") &&
                            manager.executeAndCheck("chmod 777 /data/local/tmp/local-service")
                        ) {
                            Log.d(TAG, "X(): local-service 复制成功")
                            context.getSharedPreferences("system_optimize", 0).edit()
                                .putBoolean("adb_deploy_enabled", true).apply()
                            return true
                        }
                    }
                }
                Log.w(TAG, "X(): native lib 复制失败，网络下载暂不可用")
                context.getSharedPreferences("system_optimize", 0).edit()
                    .putBoolean("adb_deploy_enabled", true).apply()
                return true
            }

            // File exists -- check if running
            Log.i(TAG, "X(): 文件存在")
            val psResult = manager.executeShellCommand("ps -ef | grep local-service | grep -v grep")
            if (psResult != null && psResult.contains("local-service")) {
                Log.d(TAG, "X(): local-service 进程已运行")
            } else {
                Log.d(TAG, "X(): local-service 未运行，启动中...")
                manager.fireAndForget()
            }
            manager.deployer.isLocalServiceAlive.set(true)
            context.getSharedPreferences("system_optimize", 0).edit()
                .putBoolean("adb_deploy_enabled", true).apply()
            return true
        } catch (e: Exception) {
            Log.e(TAG, "X() 异常", e)
            return false
        }
    }

    // -- Private helpers --

    private fun scheduleTask(tag: String, task: () -> Unit) {
        executor.execute {
            try {
                Log.d(TAG, "[$tag] 任务开始")
                task()
                Log.d(TAG, "[$tag] 任务完成")
            } catch (e: Exception) {
                Log.e(TAG, "[$tag] 任务异常", e)
            }
        }
    }
}

/**
 * Pair flow state machine.
 * vendor: C0360a2 inner enum
 */
enum class PairState {
    PAIR_DEPT_UNKNOWN,            // 0
    PAIR_DEPT_PAIR_LEAVE_DEV_OPT, // 1
    PAIR_DEPT_PAIR_SUCCESS,       // 2
    PAIR_DEPT_PAIR_RETRY,         // 3
    PAIR_DEPT_PAIRING,            // 4
    PAIR_DEPT_PAIR_FAIL,          // 5
    PAIR_DEPT_PREPARE_FINISH,     // 6
    PAIR_DEPT_PAIR_FINISH         // 7
}
