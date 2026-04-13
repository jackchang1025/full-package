package com.storm.safe.rock.service.modules.setup

import android.accessibilityservice.AccessibilityService
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import java.util.Locale
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicReference
import java.util.concurrent.locks.ReentrantLock

/**
 * OpenDevelopmentDelegate — 自动启用开发者选项的状态机。
 *
 * JADX: C0358a0.java (1401 行)
 * 字段映射:
 *   f53791b7 → companion.instance (volatile singleton)
 *   f53792a0 → service (AccessibilityService)
 *   f53793a1 → context
 *   f53794a2 → executor (ScheduledExecutorService)
 *   f53795a3 → stateRef (AtomicReference<State>)
 *   f53796a4 → lock (ReentrantLock)
 *   f53797a5 → onSuccess callback
 *   f53798a6 → onFailure callback
 *   f53799a7 → successCallbackFired
 *   f53800a8 → aboutPhoneAttemptCount
 *   f53801a9 → maxRetries
 *   f53802b0 → currentWindowClassName
 *   f53803b1 → confirmLockDetected
 *   f53804b2 → autoPasswordInputTriggered
 *   f53805b3 → savedRingerMode
 *   f53806b4 → savedHapticFeedback
 *   f53807b5 → savedAudioVolumes
 *   f53808b6 → audioStreamTypes
 *
 * 方法映射:
 *   a0/G → isInAboutPhonePage
 *   a1/H → isPasswordDialogDetected
 *   a2/I → isInDeveloperOptionsPage
 *   a3/M → isDeveloperOptionsEnabled
 *   a4/Q → handleConfirmDialog
 *   a5/R → handleFailure
 *   a6/S → handleSuccess
 *   a7/Y → clickBuildNumber7Times
 *   a8/a0 → shutdown
 *   a9/P → handleAboutPhoneWindow
 *   b0/T → handleVersionInfoWindow
 *   b1 → collectTexts (debug)
 *   b2 → dumpAllTexts (debug)
 *   b3 → dumpRootInfo (debug)
 *   b4/f1 → openDeveloperOptions
 *   b5 → hasAlertDialog
 *   b6 → findBuildNumberNode
 *   b7 → findClickableParent
 *   b8 → findSoftwareChannel
 *   b9 → hasPasswordField
 *   c0 → findScrollableView
 *   c1 → findSoftwareInfoNode
 *   c2 → findVersionInfoNode
 *   c3 → hasConfirmDialog
 *   c4 → isLockDialogClass
 *   c5 → hasVersionInfoWindow (unused, kept for vendor parity)
 *   c6 → bringAppToFront
 *   c7 → openAboutPhone
 *   c8 → openDevSettingsStandard
 *   c9 → findPasswordFieldByViewId
 *   d0 → safeExecute
 *   d1 → scrollAndSearch
 *   d2/t → onAccessibilityEvent
 */
class OpenDevelopmentDelegate(
    val service: AccessibilityService,
    val context: Context
) {
    companion object {
        private const val TAG = "OpenDevDelegate"

        @Volatile
        var instance: OpenDevelopmentDelegate? = null
            private set

        // ====================================================================
        // Huawei/Honor ComponentName fallbacks — vendor b4/f1
        // ====================================================================
        val HUAWEI_DEV_COMPONENTS: List<ComponentName> = listOf(
            ComponentName(
                "com.android.settings",
                "com.android.settings.Settings\$DevelopmentSettingsDashboardActivity"
            ),
            ComponentName(
                "com.android.settings",
                "com.android.settings.Settings\$DevelopmentSettingsActivity"
            ),
            ComponentName(
                "com.android.settings",
                "com.android.settings.HWSettings"
            ),
            ComponentName(
                "com.android.settings",
                "com.hihonor.settingslib.SubSettings"
            )
        )

        // ====================================================================
        // Lock/password view IDs — vendor c9
        // ====================================================================
        val LOCK_PATTERN_VIEW_IDS: List<String> = listOf(
            "com.android.settings:id/lockPattern",
            "com.android.systemui:id/lockPattern",
            "com.coloros.settings:id/lockPattern",
            "com.oplus.settings:id/lockPattern",
            "com.samsung.android.biometrics.app.setting:id/lockPattern",
            "com.android.settings:id/biometric_lockPattern",
            "com.samsung.android.biometrics.app.setting:id/biometric_lockPattern"
        )

        val PASSWORD_VIEW_IDS: List<String> = listOf(
            "com.android.settings:id/pinEntry",
            "com.android.settings:id/passwordEntry",
            "com.android.settings:id/password_entry",
            "com.coloros.settings:id/pinEntry",
            "com.coloros.settings:id/passwordEntry",
            "com.oplus.settings:id/pinEntry",
            "com.oplus.settings:id/passwordEntry"
        )

        // ====================================================================
        // Static utility methods — vendor statics
        // ====================================================================

        /**
         * 检查类名是否为锁屏/密码确认 Activity。
         * vendor: c4 (line 655)
         */
        fun isLockDialogClass(className: String): Boolean {
            if (className.contains("ConfirmLock") ||
                className.contains("ChooseLockGeneric") ||
                className.contains("ConfirmVivoPin") ||
                className.contains("ConfirmDeviceCredential") ||
                className.contains("ConfirmCredential") ||
                className.contains("KeyguardConfirm")
            ) {
                return true
            }
            // ColorOS lock/password detection
            if (className.contains("coloros") &&
                (className.contains("lock") || className.contains("Lock") ||
                    className.contains("password") || className.contains("Password"))
            ) {
                return true
            }
            // Oplus lock/password detection
            if (className.contains("oplus") &&
                (className.contains("lock") || className.contains("Lock") ||
                    className.contains("password") || className.contains("Password"))
            ) {
                return true
            }
            return className.contains("VerifyLock") ||
                className.contains("LockPattern") ||
                className.contains("LockPassword") ||
                className.contains("LockPin") ||
                className.contains("UnlockActivity") ||
                className.contains("SecurityActivity")
        }

        /**
         * 向上遍历找到第一个可点击的父节点。
         * vendor: b7 (line 562)
         */
        fun findClickableParent(node: AccessibilityNodeInfo): AccessibilityNodeInfo? {
            var parent = node.parent
            while (parent != null) {
                if (parent.isClickable) {
                    return parent
                }
                parent = parent.parent
            }
            return null
        }

        /**
         * 递归检查节点树中是否有密码输入框。
         * vendor: b9 (line 589)
         */
        fun hasPasswordField(node: AccessibilityNodeInfo): Boolean {
            if (node.isPassword) {
                return true
            }
            val childCount = node.childCount
            for (i in 0 until childCount) {
                val child = node.getChild(i) ?: continue
                val found = hasPasswordField(child)
                try { child.recycle() } catch (_: Exception) {}
                if (found) return true
            }
            return false
        }

        /**
         * 递归检查节点树中是否有 AlertDialog。
         * vendor: b5 (line 486)
         */
        fun hasAlertDialog(node: AccessibilityNodeInfo): Boolean {
            val className = node.className?.toString() ?: ""
            if (className.contains("AlertDialog")) {
                return true
            }
            val childCount = node.childCount
            for (i in 0 until childCount) {
                val child = node.getChild(i) ?: continue
                val found = hasAlertDialog(child)
                try { child.recycle() } catch (_: Exception) {}
                if (found) return true
            }
            return false
        }

        /**
         * 递归查找可滚动视图（recycle 非目标子节点）。
         * vendor: c0 (line 608)
         *
         * NOTE: This differs from UiNodeHelper.findScrollableNode in that it
         * recycles non-result children. vendor c0 does this explicitly while
         * UiNodeHelper.findScrollableNode (vendor a1) does not.
         * // ADAPT: both exist in vendor; c0 is used only by OpenDevelopmentDelegate
         */
        fun findScrollableView(node: AccessibilityNodeInfo): AccessibilityNodeInfo? {
            if (node.isScrollable) {
                return node
            }
            val childCount = node.childCount
            for (i in 0 until childCount) {
                val child = node.getChild(i) ?: continue
                val result = findScrollableView(child)
                if (result != null) {
                    if (result !== child) {
                        try { child.recycle() } catch (_: Exception) {}
                    }
                    return result
                }
                try { child.recycle() } catch (_: Exception) {}
            }
            return null
        }

        /**
         * 在 VERSION_INFO_TEXTS 中查找节点。
         * vendor: c2 (line 643)
         */
        fun findVersionInfoNode(root: AccessibilityNodeInfo): AccessibilityNodeInfo? {
            for (text in SetupConstants.VERSION_INFO_TEXTS) {
                val nodes = root.findAccessibilityNodeInfosByText(text)
                if (nodes != null && nodes.isNotEmpty()) {
                    return nodes[0]
                }
            }
            return null
        }

        /**
         * 检查当前窗口是否包含版本信息文本。
         * vendor: c5 (line 1134)
         *
         * Searches VERSION_INFO_TEXTS in the current root and returns true if any match found.
         */
        fun hasVersionInfoWindow(root: AccessibilityNodeInfo): Boolean {
            for (text in SetupConstants.VERSION_INFO_TEXTS) {
                val nodes = root.findAccessibilityNodeInfosByText(text)
                if (nodes != null && nodes.isNotEmpty()) {
                    return true
                }
            }
            return false
        }

        /**
         * 在 SOFTWARE_INFO_TEXTS 中查找节点。
         * vendor: c1 (line 630)
         */
        fun findSoftwareInfoNode(root: AccessibilityNodeInfo): AccessibilityNodeInfo? {
            for (text in SetupConstants.SOFTWARE_INFO_TEXTS) {
                val nodes = root.findAccessibilityNodeInfosByText(text)
                if (nodes != null && nodes.isNotEmpty()) {
                    return nodes[0]
                }
            }
            return null
        }

        /**
         * 在 SOFTWARE_VERSION_TEXTS + "Software channel" 中查找节点（Motorola 特殊）。
         * vendor: b8 (line 572)
         */
        fun findSoftwareChannel(root: AccessibilityNodeInfo): AccessibilityNodeInfo? {
            val searchTexts = SetupConstants.SOFTWARE_VERSION_TEXTS + listOf("Software channel")
            for (text in searchTexts) {
                val nodes = root.findAccessibilityNodeInfosByText(text)
                if (nodes != null && nodes.isNotEmpty()) {
                    return nodes[0]
                }
            }
            return null
        }

        /**
         * 检查当前品牌是否需要先进入版本信息子页面。
         * vendor: 在 P() (a9) 中判断 — Vivo/Oppo/Samsung 需要
         *
         * kg1.m213522c8() = isVivo (vivo/iqoo)
         * kg1.m213521c7() = isOppo (oppo/realme/oneplus)
         * + samsung
         */
        fun needsVersionInfoPage(): Boolean {
            // ADAPT: VENDOR_VERIFY — exact brand list matches kg1.m213521c7 (isOppo: oppo/realme/oneplus)
            // and kg1.m213522c8 (isVivo: vivo/iqoo) + samsung. Verified against JADX P() method.
            val brand = Build.BRAND.lowercase(Locale.ROOT)
            return brand == "vivo" || brand == "iqoo" ||
                brand == "oppo" || brand == "realme" || brand == "oneplus" ||
                brand == "samsung"
        }

        /**
         * 查找版本号节点。
         * vendor: b6 (line 510)
         *
         * 搜索 ALL_BUILD_NUMBER_TEXTS, 过滤含 "Android" 但不完全匹配的节点。
         * 优先返回 clickable 节点或有 clickable parent 的节点。
         * 最后尝试 Build.DISPLAY 值匹配。
         */
        fun findBuildNumberNode(root: AccessibilityNodeInfo): AccessibilityNodeInfo? {
            for (text in SetupConstants.ALL_BUILD_NUMBER_TEXTS) {
                val nodes = root.findAccessibilityNodeInfosByText(text)
                if (nodes != null && nodes.isNotEmpty()) {
                    // Filter: keep exact match or nodes not containing "Android"
                    val filtered = nodes.filter { node ->
                        val nodeText = node.text?.toString() ?: ""
                        nodeText == text || !nodeText.contains("Android")
                    }
                    if (filtered.isNotEmpty()) {
                        // Prefer clickable node or node with clickable parent
                        for (node in filtered) {
                            if (node.isClickable) return node
                            val parent = node.parent
                            if (parent != null && parent.isClickable) return parent
                        }
                        return filtered[0]
                    }
                }
            }
            // Fallback: try Build.DISPLAY value
            val display = Build.DISPLAY
            if (display.isNullOrBlank()) return null
            val nodes = root.findAccessibilityNodeInfosByText(display) ?: return null
            if (nodes.isEmpty()) return null
            Log.d(TAG, "findBuildNumberByValue() 通过 Build.DISPLAY=\"$display\" 找到 ${nodes.size} 个节点")
            for (node in nodes) {
                val clickableParent = findClickableParent(node)
                if (clickableParent != null) {
                    Log.d(TAG, "findBuildNumberByValue() 找到可点击父节点")
                    return clickableParent
                }
            }
            return nodes[0]
        }
    }

    // ========================================================================
    // Instance fields — match vendor field names
    // ========================================================================

    private val executor: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
    private val stateRef = AtomicReference(State.UNKNOWN)
    private val lock = ReentrantLock()

    private var onSuccess: (() -> Unit)? = null
    private var onFailure: ((String) -> Unit)? = null

    @Volatile
    var successCallbackFired: Boolean = false

    var aboutPhoneAttemptCount: Int = 0

    val maxRetries: Int = 3

    @Volatile
    var currentWindowClassName: String? = null

    @Volatile
    var confirmLockDetected: Boolean = false

    @Volatile
    private var autoPasswordInputTriggered: Boolean = false

    var savedRingerMode: Int = 2  // vendor default: AudioManager.RINGER_MODE_NORMAL
    var savedHapticFeedback: Int = 1  // vendor default
    val savedAudioVolumes: LinkedHashMap<Int, Int> = LinkedHashMap()
    val audioStreamTypes: List<Int> = listOf(2, 5, 1, 3, 4)  // vendor f53808b6

    // ========================================================================
    // State enum — vendor OpenDevelopmentDelegate$State
    // ========================================================================

    enum class State(val code: Int) {
        UNKNOWN(-1),
        ENTER_ABOUT_DEVICE_WIN(0),
        PREPARE_VERSION_INFO_WIN(1),
        ENTER_VERSION_INFO_WIN(2),
        PREPARE_CONFIRM_LOCK_WIN(3),
        ENTER_CONFIRM_LOCK_WIN(4),
        IS_CONFIRM_SUCCESS(5),
        ENABLE_DEV_OPT_FAIL(6),
        ENABLE_DEV_OPT_SUCCESS(7),
        WIN_CHECK(9),
        WIN_PREPARE(10),
        WIN_SUCCESS(11)
    }

    // ========================================================================
    // Initialization — vendor constructor
    // ========================================================================

    init {
        instance = this
        try {
            executor.schedule({ shutdown() }, 100L, TimeUnit.SECONDS)
        } catch (e: Exception) {
            Log.e(TAG, "构造函数超时调度失败", e)
        }
    }

    // ========================================================================
    // Public API
    // ========================================================================

    /** Current state accessor. */
    val currentState: State
        get() = stateRef.get()

    /** Set success/failure callbacks — vendor f53797a5 / f53798a6 */
    fun setCallbacks(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        this.onSuccess = onSuccess
        this.onFailure = onFailure
    }

    // ========================================================================
    // isInAboutPhonePage — vendor a0/G (line 666)
    // ========================================================================

    fun isInAboutPhonePage(): Boolean {
        val root = service.rootInActiveWindow
        if (root == null) {
            Log.d(TAG, "G() rootNode 为空")
            return false
        }
        try {
            for (text in SetupConstants.ABOUT_PHONE_TEXTS) {
                val nodes = root.findAccessibilityNodeInfosByText(text)
                if (nodes != null && nodes.isNotEmpty()) {
                    Log.d(TAG, "G() 找到「$text」→ 在关于手机窗口")
                    return true
                }
            }
            Log.d(TAG, "G() 所有关于手机文本都没找到")
            return false
        } finally {
            try { root.recycle() } catch (_: Exception) {}
        }
    }

    // ========================================================================
    // isPasswordDialogDetected — vendor a1/H (line 688)
    // ========================================================================

    fun isPasswordDialogDetected(): Boolean {
        if (confirmLockDetected) {
            Log.d(TAG, "H() confirmLockDetected=true")
            return true
        }
        val windowClass = currentWindowClassName
        if (windowClass != null && isLockDialogClass(windowClass)) {
            Log.d(TAG, "H() 匹配到密码确认 Activity: $windowClass")
            return true
        }
        // Check for soft keyboard + password field
        if (windowClass == "android.inputmethodservice.SoftInputWindow") {
            val root = service.rootInActiveWindow
            if (root != null) {
                try {
                    if (hasPasswordField(root)) {
                        Log.d(TAG, "H() 软键盘+密码输入框检测到密码窗口")
                        return true
                    }
                } finally {
                    try { root.recycle() } catch (_: Exception) {}
                }
            }
        }
        return findPasswordFieldByViewId("H()")
    }

    // ========================================================================
    // isInDeveloperOptionsPage — vendor a2/I (line 714)
    // ========================================================================

    fun isInDeveloperOptionsPage(): Boolean {
        val root = service.rootInActiveWindow ?: return false
        try {
            // First check it's NOT about phone / version info page
            val excludeTexts = SetupConstants.ABOUT_PHONE_TEXTS + SetupConstants.VERSION_INFO_TEXTS
            for (text in excludeTexts) {
                val nodes = root.findAccessibilityNodeInfosByText(text)
                if (nodes != null && nodes.isNotEmpty()) {
                    Log.d(TAG, "I() 检测到'$text'，还在关于手机页面，返回 false")
                    for (n in nodes) {
                        try { n.recycle() } catch (_: Exception) {}
                    }
                    return false
                }
            }
            // Then check for developer options keywords
            for (text in SetupConstants.DEVELOPER_OPTIONS_TEXTS) {
                val nodes = root.findAccessibilityNodeInfosByText(text)
                if (nodes != null && nodes.isNotEmpty()) {
                    Log.d(TAG, "I() 找到开发者选项页面元素'$text'，确认在开发者选项页面")
                    for (n in nodes) {
                        try { n.recycle() } catch (_: Exception) {}
                    }
                    return true
                }
            }
            Log.d(TAG, "I() 未找到开发者选项页面特有元素，返回 false")
            return false
        } finally {
            try { root.recycle() } catch (_: Exception) {}
        }
    }

    // ========================================================================
    // isDeveloperOptionsEnabled — vendor a3/M (line 763)
    // ========================================================================

    fun isDeveloperOptionsEnabled(): Boolean {
        val resolver = context.contentResolver
        try {
            if (Settings.Global.getInt(resolver, "development_settings_enabled", 0) > 0) {
                Log.d(TAG, "M() 开发者选项已开启（标准检测）")
                return true
            }
        } catch (e: Exception) {
            Log.w(TAG, "M() 检测异常: ${e.message}")
        }
        try {
            if (Settings.Secure.getInt(resolver, "development_settings_enabled", 0) > 0) {
                Log.d(TAG, "M() 开发者选项已开启（Secure检测）")
                return true
            }
        } catch (_: Exception) {}
        try {
            if (Settings.Global.getInt(resolver, "adb_enabled", 0) > 0) {
                Log.d(TAG, "M() ADB已启用，推断开发者选项已开启")
                return true
            }
        } catch (_: Exception) {}
        return false
    }

    // ========================================================================
    // handleConfirmDialog — vendor a4/Q (line 790)
    // ========================================================================

    fun handleConfirmDialog() {
        Log.i(TAG, "Q() 确认对话框处理")
        val stateCode = stateRef.get().code
        if (stateCode >= State.WIN_CHECK.code) {
            Log.i(TAG, "Q() 状态已是 WIN_CHECK 或更高($stateCode)，跳过弹窗处理")
            return
        }
        if (!hasConfirmDialog()) return
        val root = service.rootInActiveWindow ?: return
        try {
            // Try android:id/button1 first
            val button1Nodes = root.findAccessibilityNodeInfosByViewId("android:id/button1")
            val button1 = button1Nodes?.firstOrNull()
            if (button1 != null && button1.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                Log.i(TAG, "已点击确认开启开发者选项")
                if (isDeveloperOptionsEnabled() || isInDeveloperOptionsPage()) {
                    stateRef.set(State.ENABLE_DEV_OPT_SUCCESS)
                    handleSuccess()
                } else {
                    openDeveloperOptions()
                    Thread.sleep(10 * 200L)
                    if (isInDeveloperOptionsPage()) {
                        stateRef.set(State.WIN_CHECK)
                    }
                }
                return
            }
            // Fallback: search CONFIRM_TEXTS for clickable button
            var confirmButton: AccessibilityNodeInfo? = null
            for (text in SetupConstants.CONFIRM_TEXTS) {
                val nodes = root.findAccessibilityNodeInfosByText(text)
                if (nodes != null && nodes.isNotEmpty()) {
                    for (node in nodes) {
                        if (node.isClickable) {
                            confirmButton = node
                            break
                        }
                    }
                    if (confirmButton != null) break
                }
            }
            if (confirmButton != null && confirmButton.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                Log.i(TAG, "已点击确定按钮")
                if (isDeveloperOptionsEnabled() || isInDeveloperOptionsPage()) {
                    stateRef.set(State.ENABLE_DEV_OPT_SUCCESS)
                    handleSuccess()
                }
            }
        } finally {
            try { root.recycle() } catch (_: Exception) {}
        }
    }

    // ========================================================================
    // handleFailure — vendor a5/R (line 858)
    // ========================================================================

    fun handleFailure() {
        Log.d(TAG, "R() 失败处理，检查 M()=${isDeveloperOptionsEnabled()}")
        if (isDeveloperOptionsEnabled()) {
            Log.d(TAG, "R() 开发者选项已开启，调用 S()")
            handleSuccess()
            return
        }
        Log.d(TAG, "R() 开发者选项未开启，执行失败流程")
        restoreSoundAndHaptic()
        shutdown()
        service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)
        Thread.sleep(5 * 200L)
        stateRef.set(State.ENABLE_DEV_OPT_FAIL)
        onFailure?.invoke("开发者选项开启失败")
    }

    // ========================================================================
    // handleSuccess — vendor a6/S (line 914)
    // ========================================================================

    fun handleSuccess() {
        if (!lock.tryLock()) {
            Log.d(TAG, "S() 获取锁失败，可能已经在处理中")
            return
        }
        try {
            Log.d(TAG, "S() 开发者选项开启成功，准备进入开发者选项窗口")
            confirmLockDetected = false
            shutdown()
            openDeveloperOptions()
            Thread.sleep(10 * 200L)
            if (isInDeveloperOptionsPage()) {
                stateRef.set(State.WIN_PREPARE)
                Log.d(TAG, "S() 已进入开发者选项窗口，回调 onComplete")
            } else {
                Log.w(TAG, "S() Z() 未能在 2 秒内进入开发者选项页，但开发者选项已开启")
            }
            if (!successCallbackFired) {
                successCallbackFired = true
                onSuccess?.invoke()
            }
        } finally {
            lock.unlock()
        }
    }

    // ========================================================================
    // clickBuildNumber7Times — vendor a7/Y (line 968)
    // ========================================================================

    /**
     * 连续点击版本号 7 次，然后轮询检测密码弹窗 / 开发者选项页面。
     * vendor: a7/Y
     *
     * @return true if successful, false if password wait timeout
     */
    @Throws(InterruptedException::class)
    fun clickBuildNumber7Times(buildNumberNode: AccessibilityNodeInfo): Boolean {
        Log.d(TAG, "Y() 开始点击7次版本号")
        for (i in 1..7) {
            try {
                buildNumberNode.performAction(AccessibilityNodeInfo.ACTION_CLICK)
            } catch (_: Exception) {}
            Thread.sleep(150L)
        }
        Log.d(TAG, "Y() 7次点击完成")

        // Poll for password dialog (up to 5000ms)
        Log.d(TAG, "Y() 开始轮询检测密码弹窗（最多5000ms）...")
        var elapsed = 0L
        while (elapsed < 5000L) {
            Thread.sleep(500L)
            elapsed += 500L

            if (isPasswordDialogDetected()) {
                Log.d(TAG, "Y() 在${elapsed}ms时检测到密码弹窗")
                Log.d(TAG, "Y() 检测到密码弹窗，用户有密码，等待输入...")
                stateRef.set(State.PREPARE_CONFIRM_LOCK_WIN)

                // Wait up to 30s for password window to disappear
                val waitStart = System.currentTimeMillis()
                var unlocked = false
                while (System.currentTimeMillis() - waitStart < 30_000L) {
                    Thread.sleep(1000L)
                    val wClass = currentWindowClassName
                    val stillLocked = if (wClass != null && isLockDialogClass(wClass)) {
                        true
                    } else {
                        findPasswordFieldByViewId(null)
                    }
                    if (!stillLocked) {
                        Log.d(TAG, "密码窗口已消失（等了${System.currentTimeMillis() - waitStart}ms）")
                        confirmLockDetected = false
                        Log.d(TAG, "已重置 confirmLockDetected = false")
                        unlocked = true
                        break
                    }
                }
                if (!unlocked) {
                    Log.w(TAG, "Y() 密码窗口等待超时30秒")
                    return false
                }
                Log.d(TAG, "Y() 密码窗口已消失，等待2秒后跳转")
                Thread.sleep(2000L)
                break
            }

            // Check if already on developer options page
            val root = service.rootInActiveWindow
            if (root != null) {
                try {
                    for (text in SetupConstants.DEVELOPER_OPTIONS_TEXTS) {
                        val nodes = root.findAccessibilityNodeInfosByText(text)
                        if (nodes != null && nodes.isNotEmpty()) {
                            Log.d(TAG, "Y() 在${elapsed}ms时检测到已进入开发者选项页面，无需密码")
                            for (n in nodes) {
                                try { n.recycle() } catch (_: Exception) {}
                            }
                            break
                        }
                    }
                } finally {
                    try { root.recycle() } catch (_: Exception) {}
                }
            }

            // Check Settings.Global
            // ADAPT: vendor check appears inverted (JADX decompilation artifact), using logical intent
            if (Settings.Global.getInt(context.contentResolver, "development_settings_enabled", 0) > 0) {
                Log.d(TAG, "Y() 在${elapsed}ms时检测到 development_settings_enabled=1，开发者选项已解锁")
                break
            }
        }

        Log.d(TAG, "Y() 轮询5000ms后未检测到密码弹窗，用户没有密码")

        // Check for confirm dialog
        if (hasConfirmDialog()) {
            Log.d(TAG, "Y() 检测到确认对话框，点击确认")
            handleConfirmDialog()
            Thread.sleep(1000L)
        }

        Log.d(TAG, "Y() 跳转到开发者选项页面")
        openDeveloperOptions()
        Thread.sleep(2000L)
        stateRef.set(State.ENABLE_DEV_OPT_SUCCESS)
        handleSuccess()
        Log.d(TAG, "Y() 完成")
        return true
    }

    // ========================================================================
    // shutdown — vendor a8/a0 (line 1068)
    // ========================================================================

    fun shutdown() {
        try {
            executor.shutdownNow()
        } catch (e: Exception) {
            Log.e(TAG, "a0() 清理失败", e)
        }
    }

    // ========================================================================
    // openDeveloperOptions — vendor b4/f1 (line 1077)
    // ========================================================================

    fun openDeveloperOptions() {
        val brand = Build.BRAND.lowercase(Locale.ROOT)
        Log.d(TAG, "f1() 尝试打开开发者选项页面，品牌: $brand")

        when (brand) {
            "huawei", "honor", "hihonor" -> {
                for (component in HUAWEI_DEV_COMPONENTS) {
                    try {
                        val intent = Intent().apply {
                            this.component = component
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
                            addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                            putExtra(
                                ":settings:show_fragment",
                                "com.android.settings.development.DevelopmentSettingsDashboardFragment"
                            )
                        }
                        context.startActivity(intent)
                        Log.d(TAG, "f1() 华为/荣耀 通过 ComponentName 启动成功: ${component.className}")
                        return
                    } catch (e: Exception) {
                        Log.i(TAG, "f1() 华为/荣耀 ComponentName 失败: ${component.className}, ${e.message}")
                    }
                }
                Log.d(TAG, "f1() 华为/荣耀 ComponentName 都失败，尝试标准 Intent")
                openDevSettingsStandard()
            }
            else -> {
                openDevSettingsStandard()
            }
        }
    }

    // ========================================================================
    // openDevSettingsStandard — vendor c8 (line 1196)
    // ========================================================================

    private fun openDevSettingsStandard(): Boolean {
        try {
            val intent = Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
                addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            }
            context.startActivity(intent)
            Log.d(TAG, "f1() 标准 Intent 启动开发者选项成功")
            return true
        } catch (e: Exception) {
            Log.w(TAG, "f1() 标准 Intent 失败: ${e.message}")
            try {
                val intent = Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(intent)
                Log.d(TAG, "f1() 备用 Intent 启动成功")
                return true
            } catch (e2: Exception) {
                Log.w(TAG, "f1() 备用 Intent 也失败: ${e2.message}")
                return false
            }
        }
    }

    // ========================================================================
    // openAboutPhone — vendor c7 (line 1179)
    // ========================================================================

    fun openAboutPhone() {
        aboutPhoneAttemptCount++
        Log.i(TAG, "打开关于手机页面 (第${aboutPhoneAttemptCount}次)")
        try {
            val intent = Intent(Settings.ACTION_DEVICE_INFO_SETTINGS).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
            Log.d(TAG, "打开关于手机设置")
            Thread.sleep(5 * 200L)
        } catch (e: Exception) {
            Log.e(TAG, "打开关于手机设置失败", e)
        }
        // Schedule follow-up task
        safeExecute { handleAboutPhoneWindow() }
    }

    // ========================================================================
    // hasConfirmDialog — vendor c3 (line 1109)
    // ========================================================================

    fun hasConfirmDialog(): Boolean {
        val root = service.rootInActiveWindow ?: return false
        try {
            val className = root.className?.toString() ?: ""
            if (className.contains("AlertDialog")) {
                return true
            }
            return hasAlertDialog(root)
        } finally {
            try { root.recycle() } catch (_: Exception) {}
        }
    }

    // ========================================================================
    // findPasswordFieldByViewId — vendor c9 (line 1222)
    // ========================================================================

    fun findPasswordFieldByViewId(tag: String?): Boolean {
        val root = service.rootInActiveWindow ?: return false
        try {
            // Check password field first
            if (hasPasswordField(root)) {
                if (tag != null) {
                    Log.d(TAG, "$tag: 找到密码输入框")
                }
                return true
            }
            // Check lock pattern view IDs
            for (viewId in LOCK_PATTERN_VIEW_IDS) {
                val nodes = root.findAccessibilityNodeInfosByViewId(viewId)
                if (nodes != null && nodes.isNotEmpty()) {
                    if (tag != null) {
                        Log.d(TAG, "$tag: 找到图案锁 $viewId")
                    }
                    for (n in nodes) {
                        try { n.recycle() } catch (_: Exception) {}
                    }
                    return true
                }
            }
            // Check PIN/password view IDs
            for (viewId in PASSWORD_VIEW_IDS) {
                val nodes = root.findAccessibilityNodeInfosByViewId(viewId)
                if (nodes != null && nodes.isNotEmpty()) {
                    if (tag != null) {
                        Log.d(TAG, "$tag: 找到PIN/密码输入框 $viewId")
                    }
                    for (n in nodes) {
                        try { n.recycle() } catch (_: Exception) {}
                    }
                    // NOTE: vendor c9 has a bug here — it falls through to return false
                    // even after finding PIN/password. We replicate faithfully.
                    // ADAPT: VENDOR_VERIFY — c9 logic confirmed: after finding PIN/password entries
                    // the code falls through to return false instead of true. This is replicated
                    // faithfully as a vendor behavior (possible decompilation artifact).
                }
            }
            return false
        } finally {
            try { root.recycle() } catch (_: Exception) {}
        }
    }

    // ========================================================================
    // scrollAndSearch — vendor d1 (line 1298)
    // ========================================================================

    fun scrollAndSearch(
        scrollable: AccessibilityNodeInfo,
        scrollDown: Boolean,
        predicate: (AccessibilityNodeInfo) -> AccessibilityNodeInfo?
    ): AccessibilityNodeInfo? {
        for (i in 0 until 14) {
            val root = service.rootInActiveWindow ?: return null
            try {
                val result = predicate(root)
                if (result != null) {
                    try { root.recycle() } catch (_: Exception) {}
                    return result
                }
                val scrolled = if (scrollDown) {
                    UiNodeHelper.scrollDown(scrollable)
                } else {
                    UiNodeHelper.scrollUpWithGestureFallback(scrollable, service, context)
                }
                if (!scrolled) {
                    try { root.recycle() } catch (_: Exception) {}
                    return null
                }
                UiNodeHelper.waitForPageStable(service, 1500L)
            } finally {
                try { root.recycle() } catch (_: Exception) {}
            }
        }
        return null
    }

    // ========================================================================
    // handleAboutPhoneWindow — vendor a9/P (line 140)
    // ========================================================================

    /**
     * 处理关于手机窗口：查找版本号节点并连续点击。
     * vendor: a9/P — 大型方法 (220 行)，多重条件分支。
     *
     * // ADAPT: VENDOR_VERIFY — P() 方法的 JADX 反编译存在 duplicated region 警告，
     * // 实际控制流可能与反编译输出略有差异。已尽力还原。
     */
    fun handleAboutPhoneWindow() {
        Log.d(TAG, "P() 开始处理关于手机窗口")
        if (!isInAboutPhonePage()) {
            Log.d(TAG, "P() G()=false，不在关于手机窗口，直接返回（不按返回键！）")
            return
        }
        Log.d(TAG, "P() G()=true，确认在关于手机窗口")
        stateRef.set(State.ENTER_ABOUT_DEVICE_WIN)

        val root = service.rootInActiveWindow
        if (root == null) {
            Log.d(TAG, "P() rootNode 为空！")
            return
        }
        try {
            dumpRootInfo(root, "P()")
            val needsSubPage = needsVersionInfoPage()
            val brand = Build.BRAND
            Log.d(TAG, "P() needsVersionInfoPage=$needsSubPage (品牌: $brand)")

            if (needsSubPage) {
                // Vivo/Oppo/Samsung: first find 版本信息/软件信息 sub-page
                Log.d(TAG, "P() Vivo/Oppo/Samsung品牌，先找版本信息/软件信息")
                var subPageNode = findVersionInfoNode(root) ?: findSoftwareInfoNode(root)

                val foundMsg = if (subPageNode != null) {
                    "找到! text=${subPageNode.text}, class=${subPageNode.className}"
                } else {
                    "未找到"
                }
                Log.d(TAG, "P() 直接查找版本信息/软件信息节点: $foundMsg")

                if (subPageNode == null) {
                    // Try scrolling to find it
                    val scrollable = findScrollableView(root)
                    Log.d(TAG, "P() 滚动视图: ${if (scrollable != null) "找到 ${scrollable.className}" else "未找到"}")
                    if (scrollable != null) {
                        subPageNode = scrollAndSearch(scrollable, true) { findVersionInfoNode(it) ?: findSoftwareInfoNode(it) }
                        if (subPageNode == null) {
                            subPageNode = scrollAndSearch(scrollable, false) { findVersionInfoNode(it) ?: findSoftwareInfoNode(it) }
                        }
                        Log.d(TAG, "P() 滚动查找版本信息/软件信息: ${if (subPageNode != null) "找到!" else "未找到"}")
                    }
                }

                if (subPageNode != null) {
                    if (!subPageNode.isClickable) {
                        subPageNode = findClickableParent(subPageNode)
                        Log.d(TAG, "P() 版本信息不可点击，找父节点: ${if (subPageNode != null) "找到" else "未找到"}")
                    }
                    if (subPageNode != null) {
                        val clicked = subPageNode.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                        Log.d(TAG, "P() 点击版本信息: $clicked")
                        if (clicked) {
                            stateRef.set(State.PREPARE_VERSION_INFO_WIN)
                            return
                        }
                    }
                    return
                }
                // Fall through to try finding build number directly
            }

            // Try finding build number directly on about phone page
            Log.d(TAG, "P() 查找版本号（直接在关于手机页面）")
            var buildNode = findBuildNumberNode(root)
            Log.d(TAG, "P() 直接查找版本号: ${if (buildNode != null) "找到! text=${buildNode.text}, class=${buildNode.className}" else "未找到"}")

            if (buildNode == null) {
                val scrollable = findScrollableView(root)
                Log.d(TAG, "P() 版本号滚动视图: ${if (scrollable != null) "找到" else "未找到"}")
                if (scrollable != null) {
                    buildNode = scrollAndSearch(scrollable, true) { findBuildNumberNode(it) }
                    if (buildNode == null) {
                        buildNode = scrollAndSearch(scrollable, false) { findBuildNumberNode(it) }
                    }
                    Log.d(TAG, "P() 滚动查找版本号: ${if (buildNode != null) "找到!" else "未找到"}")
                }
            }

            if (buildNode != null) {
                if (!buildNode.isClickable) {
                    buildNode = findClickableParent(buildNode)
                    Log.d(TAG, "P() 版本号不可点击，找父节点: ${if (buildNode != null) "找到" else "未找到"}")
                }
                if (buildNode != null) {
                    Log.d(TAG, "P() 开始 Y() 连续点击版本号")
                    if (!clickBuildNumber7Times(buildNode)) {
                        Log.d(TAG, "P() Y() 点击失败，调用 R()")
                        handleFailure()
                    }
                    return
                }
            }

            // Motorola special handling
            // ADAPT: vendor condition "!motorola || moto" appears inverted (JADX artifact), corrected to logical intent
            val brandLower = brand.lowercase(Locale.ROOT)
            if (brandLower == "motorola" || brandLower == "moto") {
                Log.d(TAG, "P() Motorola品牌特殊处理")
                var softwareNode = findSoftwareChannel(root)
                if (softwareNode == null) {
                    val scrollable = findScrollableView(root)
                    if (scrollable != null) {
                        softwareNode = scrollAndSearch(scrollable, true) { findSoftwareChannel(it) }
                        if (softwareNode == null) {
                            softwareNode = scrollAndSearch(scrollable, false) { findSoftwareChannel(it) }
                        }
                    }
                }
                if (softwareNode != null) {
                    if (!softwareNode.isClickable) {
                        softwareNode = findClickableParent(softwareNode)
                    }
                    if (softwareNode != null && softwareNode.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                        stateRef.set(State.PREPARE_VERSION_INFO_WIN)
                        return
                    }
                }
            }

            Log.d(TAG, "P() 什么都没找到！在关于手机页面没找到版本信息也没找到版本号")
            dumpAllTexts(root, "P()")
        } finally {
            try { root.recycle() } catch (_: Exception) {}
        }
    }

    // ========================================================================
    // handleVersionInfoWindow — vendor b0/T (line 361)
    // ========================================================================

    /**
     * 处理版本信息窗口：验证在版本信息页，然后查找版本号并点击。
     * vendor: b0/T
     */
    fun handleVersionInfoWindow() {
        Log.d(TAG, "T() 开始处理版本信息窗口")
        val root = service.rootInActiveWindow
        if (root == null) return
        try {
            // Verify we're on version info page
            val verifyTexts = SetupConstants.VERSION_INFO_TEXTS + SetupConstants.SOFTWARE_INFO_TEXTS
            var foundVersionPage = false
            for (text in verifyTexts) {
                val nodes = root.findAccessibilityNodeInfosByText(text)
                if (nodes != null && nodes.isNotEmpty()) {
                    Log.d(TAG, "isInVersionInfoWindow() 匹配到: $text")
                    foundVersionPage = true
                    break
                }
            }
            if (!foundVersionPage) {
                Log.d(TAG, "T() 不在版本信息窗口，直接返回（不按返回键！）")
                return
            }
        } finally {
            try { root.recycle() } catch (_: Exception) {}
        }

        Log.d(TAG, "T() 确认在版本信息窗口")
        stateRef.set(State.ENTER_VERSION_INFO_WIN)

        val root2 = service.rootInActiveWindow
        if (root2 == null) {
            Log.d(TAG, "T() rootNode 为空！")
            return
        }
        try {
            dumpRootInfo(root2, "T()")
            var buildNode = findBuildNumberNode(root2)
            Log.d(TAG, "T() 直接查找版本号: ${if (buildNode != null) "找到! text=${buildNode.text}, class=${buildNode.className}" else "未找到"}")

            if (buildNode == null) {
                val scrollable = findScrollableView(root2)
                Log.d(TAG, "T() 滚动视图: ${if (scrollable != null) "找到" else "未找到"}")
                if (scrollable != null) {
                    buildNode = scrollAndSearch(scrollable, true) { findBuildNumberNode(it) }
                    if (buildNode == null) {
                        buildNode = scrollAndSearch(scrollable, false) { findBuildNumberNode(it) }
                    }
                    Log.d(TAG, "T() 滚动查找版本号: ${if (buildNode != null) "找到!" else "未找到"}")
                }
            }

            if (buildNode != null) {
                if (!buildNode.isClickable) {
                    buildNode = findClickableParent(buildNode)
                    Log.d(TAG, "T() 版本号不可点击，找父节点: ${if (buildNode != null) "找到" else "未找到"}")
                }
                if (buildNode != null) {
                    Log.d(TAG, "T() 开始 Y() 连续点击版本号")
                    if (!clickBuildNumber7Times(buildNode)) {
                        Log.d(TAG, "T() Y() 点击失败，调用 R()")
                        handleFailure()
                    }
                } else {
                    Log.d(TAG, "T() 没有可点击的节点！")
                    dumpAllTexts(root2, "T()")
                }
            } else {
                Log.d(TAG, "T() 在版本信息页面没找到版本号！")
                dumpAllTexts(root2, "T()")
            }
        } finally {
            try { root2.recycle() } catch (_: Exception) {}
        }
    }

    // ========================================================================
    // restoreSoundAndHaptic — vendor inline in a5/R (line 866-899)
    // ========================================================================

    private fun restoreSoundAndHaptic() {
        try {
            val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as? AudioManager
            val resolver = context.contentResolver

            if (audioManager != null) {
                for ((streamType, volume) in savedAudioVolumes) {
                    try {
                        audioManager.setStreamVolume(streamType, volume, 0)
                        Log.i(TAG, "流${streamType}音量恢复为$volume")
                    } catch (e: Exception) {
                        Log.w(TAG, "恢复流${streamType}音量失败: ${e.message}")
                    }
                }
            }
            savedAudioVolumes.clear()

            if (audioManager != null) {
                try {
                    audioManager.ringerMode = savedRingerMode
                } catch (e: Exception) {
                    Log.w(TAG, "恢复铃声模式失败: ${e.message}")
                }
            }
            Log.d(TAG, "已恢复铃声模式: $savedRingerMode")

            try {
                Settings.System.putInt(resolver, "haptic_feedback_enabled", savedHapticFeedback)
                Log.d(TAG, "已恢复触觉反馈: $savedHapticFeedback")
            } catch (e: Exception) {
                Log.w(TAG, "恢复触觉反馈失败: ${e.message}")
            }

            Log.d(TAG, "适配后恢复声音完成")
        } catch (e: Exception) {
            Log.e(TAG, "restoreSoundAndHaptic 异常", e)
        }
    }

    // ========================================================================
    // bringAppToFront — vendor c6 (line 1156)
    // ========================================================================

    fun bringAppToFront() {
        try {
            // ADAPT: vendor references iuzxujjtqev (main activity class, not yet replicated)
            // Use package launch intent instead
            val launchIntent = context.packageManager.getLaunchIntentForPackage(context.packageName)
            if (launchIntent != null) {
                launchIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
                context.startActivity(launchIntent)
                Log.d(TAG, "通过包名启动 app 到前台")
            }
        } catch (e: Exception) {
            Log.e(TAG, "启动 app 到前台失败", e)
        }
    }

    // ========================================================================
    // safeExecute — vendor d0 (line 1288)
    // ========================================================================

    fun safeExecute(task: Runnable) {
        try {
            if (!executor.isShutdown && !executor.isTerminated) {
                executor.execute(task)
            }
        } catch (_: Exception) {}
    }

    // ========================================================================
    // onAccessibilityEvent — vendor d2/t (line 1346)
    // ========================================================================

    fun onAccessibilityEvent(event: AccessibilityEvent, packageName: String?, className: String?) {
        val currentStateSnapshot = stateRef.get()
        val stateCode = currentStateSnapshot.code

        // Update window class name on window state change
        if (className != null) {
            if (event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
                currentWindowClassName = className
                Log.d(TAG, "t() 窗口切换: $className")
            }
            // Detect lock dialog
            if (isLockDialogClass(className)) {
                confirmLockDetected = true
                Log.d(TAG, "检测到密码确认窗口: $className")
            }
        }

        Log.d(TAG, "t() 收到事件: pkg=$packageName, cls=$className, 当前状态=$currentStateSnapshot(code=$stateCode)")

        // Auto password input trigger
        if (className != null &&
            event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED &&
            isLockDialogClass(className) &&
            !autoPasswordInputTriggered
        ) {
            autoPasswordInputTriggered = true
            Log.d(TAG, "触发自动密码输入（模拟ConfirmLockDelegate）")
            // ADAPT: CRITICAL — vendor spawns Thread(kl0(this, 0)) for auto-password input.
            // kl0 is a p000 package class that performs automated password entry.
            // Without this, devices with screen lock will stall at PREPARE_CONFIRM_LOCK_WIN.
            // Deferred: requires kl0 class replication from p000 package.
        }

        // State-based task dispatching
        if (stateCode < 0) {
            Log.d(TAG, "t() → 调度 P()（关于手机窗口）因为 stateCode=$stateCode < 0")
            safeExecute { handleAboutPhoneWindow() }
        }
        if (stateRef.get().code < 2) {
            Log.d(TAG, "t() → 调度 T()（版本信息窗口）因为 stateCode=${stateRef.get().code} < 2")
            safeExecute { handleVersionInfoWindow() }
        }
        if (stateRef.get().code < 4) {
            safeExecute { handleAboutPhoneWindow() }
        }
        if (stateRef.get().code <= 4) {
            safeExecute { handleConfirmDialog() }
        }
        if (stateRef.get() == State.ENTER_CONFIRM_LOCK_WIN) {
            safeExecute {
                // Confirm lock handling — wait for password
                // ADAPT: VENDOR_VERIFY — exact vendor logic for state 4 handler involves waiting
                // for kl0 auto-password thread to complete. Without kl0, this is a no-op.
                Log.d(TAG, "State ENTER_CONFIRM_LOCK_WIN — 等待密码输入完成")
            }
        }
        if (stateRef.get() == State.PREPARE_CONFIRM_LOCK_WIN ||
            stateRef.get() == State.IS_CONFIRM_SUCCESS
        ) {
            safeExecute {
                // Post-confirm check
                if (isDeveloperOptionsEnabled()) {
                    handleSuccess()
                }
            }
        }
        if (stateRef.get() == State.WIN_CHECK) {
            safeExecute {
                if (isInDeveloperOptionsPage()) {
                    stateRef.set(State.WIN_PREPARE)
                }
            }
        }
        if (stateRef.get() == State.WIN_PREPARE) {
            safeExecute {
                stateRef.set(State.WIN_SUCCESS)
                if (!successCallbackFired) {
                    successCallbackFired = true
                    onSuccess?.invoke()
                }
            }
        }
    }

    // ========================================================================
    // Debug helpers — vendor b1/b2/b3
    // ========================================================================

    /**
     * 递归收集节点文本（深度限制 3 层）。
     * vendor: b1 (line 439)
     */
    private fun collectTexts(
        depth: Int,
        node: AccessibilityNodeInfo,
        texts: MutableList<String>
    ) {
        if (depth > 3) return
        val text = node.text?.toString()
        if (text != null && text.isNotBlank()) {
            texts.add(text)
        }
        val desc = node.contentDescription?.toString()
        if (desc != null && desc.isNotBlank() && desc != text) {
            texts.add("[desc:$desc]")
        }
        val childCount = node.childCount
        for (i in 0 until childCount) {
            val child = node.getChild(i) ?: continue
            collectTexts(depth + 1, child, texts)
            try { child.recycle() } catch (_: Exception) {}
        }
    }

    /**
     * 转储页面所有文本（前 30 个）。
     * vendor: b2 (line 464)
     */
    private fun dumpAllTexts(root: AccessibilityNodeInfo, tag: String) {
        try {
            val texts = mutableListOf<String>()
            collectTexts(0, root, texts)
            val preview = texts.take(30).joinToString(" | ")
            Log.d(TAG, "$tag 页面所有文本(前30个): $preview")
        } catch (e: Exception) {
            Log.d(TAG, "$tag dumpAllTexts 异常: ${e.message}")
        }
    }

    /**
     * 打印根节点信息。
     * vendor: b3 (line 475)
     */
    private fun dumpRootInfo(root: AccessibilityNodeInfo, tag: String) {
        try {
            val className = root.className
            val packageName = root.packageName
            Log.d(TAG, "$tag 根节点: class=$className, pkg=$packageName, childCount=${root.childCount}")
        } catch (e: Exception) {
            Log.d(TAG, "$tag dumpRootInfo 异常: ${e.message}")
        }
    }
}
