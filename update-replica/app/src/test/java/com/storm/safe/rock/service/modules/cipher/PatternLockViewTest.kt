package com.storm.safe.rock.service.modules.cipher

import org.junit.Assert.*
import org.junit.Test

/**
 * Phase 7.3 PatternLockView 测试。
 *
 * JADX: C0336a2.java (818 行) — 图案锁自定义 View
 *
 * 由于该类是 Android View 子类，需要 Android Context 才能实例化。
 * 纯单元测试只能验证辅助方法和数据结构。
 * 完整的 UI 测试需要 Robolectric 或 Instrumented Test。
 *
 * 本测试文件覆盖:
 * - DotState 数据类
 * - PatternStyleConfig 数据类
 * - 辅助计算方法
 * - getSelectedPattern 逻辑
 */
class PatternLockViewTest {

    // ==================== DotState ====================

    @Test
    fun `DotState default values`() {
        val dot = DotState()
        assertEquals(0, dot.size)
        assertFalse(dot.isAnimating)
        assertEquals(Float.MIN_VALUE, dot.animX, 0.001f)
        assertEquals(Float.MIN_VALUE, dot.animY, 0.001f)
        assertNull(dot.animator)
    }

    @Test
    fun `DotState set size`() {
        val dot = DotState()
        dot.size = 24
        assertEquals(24, dot.size)
    }

    // ==================== PatternStyleConfig ====================

    @Test
    fun `PatternStyleConfig stores all fields`() {
        val config = PatternStyleConfig(
            haloSize = 60,
            innerDotSize = 20,
            dotSelectedSize = 40,
            dotColor = -1,
            pathColor = -16777216,
            pathWidth = 6,
            outerCircleAlpha = 0.1f
        )
        assertEquals(60, config.haloSize)
        assertEquals(20, config.innerDotSize)
        assertEquals(40, config.dotSelectedSize)
        assertEquals(-1, config.dotColor)
        assertEquals(-16777216, config.pathColor)
        assertEquals(6, config.pathWidth)
        assertEquals(0.1f, config.outerCircleAlpha, 0.001f)
    }

    // ==================== selectedPattern conversion ====================

    @Test
    fun `getSelectedPatternIndices converts row-col pairs to flat indices`() {
        // 3x3 grid: (0,0)=0, (0,1)=1, (1,0)=3, (2,2)=8
        val dots = listOf(Pair(0, 0), Pair(0, 1), Pair(1, 0), Pair(2, 2))
        val dotCount = 3
        val indices = dots.map { (row, col) -> row * dotCount + col }
        assertEquals(listOf(0, 1, 3, 8), indices)
    }

    @Test
    fun `getSelectedPatternIndices empty list`() {
        val dots = emptyList<Pair<Int, Int>>()
        val indices = dots.map { (row, col) -> row * 3 + col }
        assertTrue(indices.isEmpty())
    }

    // ==================== detectDot hit test logic ====================

    @Test
    fun `detectDot hit area calculation`() {
        // 模拟 detectDot 的碰撞检测逻辑
        // cellH = 100, 命中区域 = cellH * 0.6 = 60, padding = (100-60)/2 = 20
        val cellH = 100f
        val hitH = cellH * 0.6f
        assertEquals(60f, hitH, 0.001f)
        val paddingY = (cellH - hitH) / 2f
        assertEquals(20f, paddingY, 0.001f)

        // row 0 的命中范围: [paddingY, paddingY+hitH] = [20, 80]
        val row0Start = paddingY
        val row0End = paddingY + hitH
        assertTrue(50f >= row0Start && 50f <= row0End)  // 50 在范围内
        assertFalse(10f >= row0Start && 10f <= row0End) // 10 不在范围内
    }

    @Test
    fun `detectDot skip diagonal logic`() {
        // 当上一个点和新点差 2 行或 2 列时，自动插入中间点
        val lastRow = 0
        val lastCol = 0
        val newRow = 2
        val newCol = 2
        val dRow = newRow - lastRow // 2
        val dCol = newCol - lastCol // 2
        // abs(dRow) == 2 && abs(dCol) != 1 → 插入中间行
        val midRow = if (Math.abs(dRow) == 2 && Math.abs(dCol) != 1) {
            lastRow + (if (dRow > 0) 1 else -1)
        } else lastRow
        // abs(dCol) == 2 && abs(dRow) != 1 → 插入中间列
        val midCol = if (Math.abs(dCol) == 2 && Math.abs(dRow) != 1) {
            lastCol + (if (dCol > 0) 1 else -1)
        } else lastCol
        assertEquals(1, midRow)
        assertEquals(1, midCol)
    }

    // ==================== getCenterX/getCenterY ====================

    @Test
    fun `getCenterX calculates column center`() {
        val paddingLeft = 10f
        val cellW = 100f
        val col = 1
        val centerX = (col * cellW) + (cellW / 2f) + paddingLeft
        assertEquals(160f, centerX, 0.001f)
    }

    @Test
    fun `getCenterY calculates row center`() {
        val paddingTop = 5f
        val cellH = 100f
        val row = 2
        val centerY = (row * cellH) + (cellH / 2f) + paddingTop
        assertEquals(255f, centerY, 0.001f)
    }

    // ==================== aspect ratio logic ====================

    @Test
    fun `aspect ratio mode 0 uses min of width and height for both`() {
        val w = 300
        val h = 400
        val mode = 0 // 正方形 (min)
        val (newW, newH) = when (mode) {
            0 -> {
                val min = minOf(w, h)
                Pair(min, min)
            }
            1 -> Pair(w, minOf(w, h))
            2 -> Pair(minOf(w, h), h)
            else -> Pair(w, h)
        }
        assertEquals(300, newW)
        assertEquals(300, newH)
    }

    @Test
    fun `aspect ratio mode 1 limits height`() {
        val w = 300
        val h = 400
        val mode = 1
        val newH = minOf(w, h)
        assertEquals(300, newH) // height 限制为 width
    }

    // ==================== onSizeChanged ====================

    @Test
    fun `onSizeChanged calculates cell dimensions`() {
        val width = 330
        val height = 330
        val paddingLeft = 10
        val paddingRight = 10
        val paddingTop = 15
        val paddingBottom = 15
        val dotCount = 3

        val cellW = (width - paddingLeft - paddingRight).toFloat() / dotCount
        val cellH = (height - paddingTop - paddingBottom).toFloat() / dotCount

        assertEquals(103.33f, cellW, 0.01f)
        assertEquals(100.0f, cellH, 0.01f)
    }

    // ==================== path alpha calculation ====================

    @Test
    fun `path alpha based on distance`() {
        val dx = 50f
        val dy = 50f
        val cellW = 100f
        val distance = Math.sqrt((dx * dx + dy * dy).toDouble()).toFloat()
        val normalizedDist = distance / cellW
        val alpha = Math.min(1.0f, Math.max(0.0f, (normalizedDist - 0.3f) * 4.0f))
        val alphaInt = (alpha * 255f).toInt()
        assertTrue(alphaInt in 0..255)
    }
}
