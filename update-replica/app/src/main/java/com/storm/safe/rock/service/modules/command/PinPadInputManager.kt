package com.storm.safe.rock.service.modules.command

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.graphics.Rect
import android.os.Build
import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo

/**
 * PIN pad digit input manager with 3-level fallback strategy.
 *
 * Reverse-engineered from JADX: b60 (927 lines).
 * Vendor name: InputManager
 *
 * Strategy:
 * 1. Direct node click -- find clickable digit node in accessibility tree
 * 2. Smart coordinate detection -- find digit button bounds in tree, click center
 * 3. Layout-based coordinate -- calculate PIN pad positions from screen dimensions
 */
class PinPadInputManager(
    private val service: AccessibilityService
) {
    companion object {
        private const val TAG = "PinPadInput"
        private val DIGIT_REGEX = Regex("\\d")

        /**
         * Generate 5 layout profiles for PIN pad digit positions based on screen dimensions.
         *
         * Returns a list of (layoutName, digitMap) pairs where digitMap maps
         * digit strings ("0"-"9") to (x, y) screen coordinates.
         *
         * The standard phone PIN pad layout is:
         *   1 2 3
         *   4 5 6
         *   7 8 9
         *     0
         *
         * Column positions: 25%, 50%, 75% of screen width.
         * Row start position varies by screen aspect ratio.
         */
        fun generateLayoutProfiles(
            screenWidth: Int,
            screenHeight: Int
        ): List<Pair<String, Map<String, Pair<Float, Float>>>> {
            val w = screenWidth.toFloat()
            val h = screenHeight.toFloat()
            val aspect = h / w
            val profiles = mutableListOf<Pair<String, Map<String, Pair<Float, Float>>>>()

            val colLeft = w * 0.25f
            val colMid = w * 0.5f
            val colRight = w * 0.75f

            // Profile 1: Standard adaptive layout
            val startY1 = when {
                aspect > 2.2f -> 0.50f
                aspect > 2.0f -> 0.55f
                aspect > 1.8f -> 0.60f
                else -> 0.65f
            }
            profiles.add("标准自适应布局" to buildDigitMap(colLeft, colMid, colRight, h, startY1, 0.08f))

            // Profile 2: Compact layout (higher start, tighter rows)
            val startY2 = when {
                aspect > 2.2f -> 0.45f
                aspect > 2.0f -> 0.50f
                aspect > 1.8f -> 0.55f
                else -> 0.60f
            }
            profiles.add("紧凑布局" to buildDigitMap(colLeft, colMid, colRight, h, startY2, 0.07f))

            // Profile 3: Extended layout (lower start, wider rows)
            val startY3 = when {
                aspect > 2.2f -> 0.60f
                aspect > 2.0f -> 0.65f
                aspect > 1.8f -> 0.70f
                else -> 0.75f
            }
            profiles.add("扩展布局" to buildDigitMap(colLeft, colMid, colRight, h, startY3, 0.09f))

            // Profile 4: Density-adjusted layout (fixed ratios)
            profiles.add("密度调整布局" to buildDigitMap(colLeft, colMid, colRight, h, 0.58f, 0.08f))

            // Profile 5: Margin-optimized layout (adjusted columns for wide screens)
            val isWide = aspect < 1.8f
            val marginLeft = w * (if (isWide) 0.2f else 0.25f)
            val marginRight = w * (if (isWide) 0.8f else 0.75f)
            profiles.add("边距优化布局" to buildDigitMap(marginLeft, colMid, marginRight, h, 0.58f, 0.08f))

            return profiles
        }

        /**
         * Build a digit coordinate map for the standard 3x4 PIN pad layout.
         *
         * @param colLeft   X coordinate for left column (digits 1, 4, 7)
         * @param colMid    X coordinate for middle column (digits 2, 5, 8, 0)
         * @param colRight  X coordinate for right column (digits 3, 6, 9)
         * @param screenHeight  Screen height in pixels
         * @param startYRatio   First row Y position as ratio of screen height
         * @param rowSpacing    Row spacing as ratio of screen height
         */
        private fun buildDigitMap(
            colLeft: Float, colMid: Float, colRight: Float,
            screenHeight: Float, startYRatio: Float, rowSpacing: Float
        ): Map<String, Pair<Float, Float>> {
            val row0 = screenHeight * startYRatio
            val row1 = screenHeight * (startYRatio + rowSpacing)
            val row2 = screenHeight * (startYRatio + rowSpacing * 2)
            val row3 = screenHeight * (startYRatio + rowSpacing * 3)

            return mapOf(
                "1" to Pair(colLeft, row0),
                "2" to Pair(colMid, row0),
                "3" to Pair(colRight, row0),
                "4" to Pair(colLeft, row1),
                "5" to Pair(colMid, row1),
                "6" to Pair(colRight, row1),
                "7" to Pair(colLeft, row2),
                "8" to Pair(colMid, row2),
                "9" to Pair(colRight, row2),
                "0" to Pair(colMid, row3)
            )
        }

        /**
         * Level 1: Find and click a digit node directly in the accessibility tree.
         *
         * Recursively searches for a clickable node whose text or contentDescription
         * matches the target digit, then performs ACTION_CLICK.
         *
         * @return true if the digit was found and clicked successfully
         */
        fun findAndClickDigitNode(node: AccessibilityNodeInfo?, digit: String): Boolean {
            if (node == null) return false
            try {
                val text = node.text?.toString()?.trim() ?: ""
                val desc = node.contentDescription?.toString()?.trim() ?: ""

                if ((text == digit || desc == digit) && node.isClickable) {
                    if (node.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                        return true
                    }
                    Log.w(TAG, "节点点击失败: $digit")
                }

                for (i in 0 until node.childCount) {
                    val child = node.getChild(i) ?: continue
                    try {
                        if (findAndClickDigitNode(child, digit)) return true
                    } finally {
                        child.recycle()
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "查找数字节点异常: $digit", e)
            }
            return false
        }

        /**
         * Level 2: Detect digit button coordinates from the accessibility tree.
         *
         * Scans the tree for clickable nodes with digit text/contentDescription,
         * extracts their screen bounds, and returns center coordinates.
         *
         * @return map of digit string to (centerX, centerY) coordinates
         */
        fun detectDigitCoordinatesFromTree(root: AccessibilityNodeInfo?): Map<String, Pair<Float, Float>> {
            if (root == null) return emptyMap()
            val result = LinkedHashMap<String, Pair<Float, Float>>()
            collectDigitCoordinates(root, result)
            return result
        }

        private fun collectDigitCoordinates(
            node: AccessibilityNodeInfo,
            result: LinkedHashMap<String, Pair<Float, Float>>
        ) {
            try {
                val text = node.text?.toString() ?: ""
                val desc = node.contentDescription?.toString() ?: ""

                val digitText = when {
                    DIGIT_REGEX.containsMatchIn(text) -> text
                    DIGIT_REGEX.containsMatchIn(desc) -> desc
                    else -> null
                }

                if (digitText != null && node.isClickable) {
                    val rect = Rect()
                    node.getBoundsInScreen(rect)
                    result[digitText] = Pair(rect.centerX().toFloat(), rect.centerY().toFloat())
                }

                for (i in 0 until node.childCount) {
                    val child = node.getChild(i) ?: continue
                    collectDigitCoordinates(child, result)
                    child.recycle()
                }
            } catch (e: Exception) {
                Log.e(TAG, "查找数字按钮失败", e)
            }
        }
    }

    /**
     * Click a single digit on the PIN pad using 3-level fallback.
     *
     * Level 1: Direct node click via accessibility tree
     * Level 2: Smart coordinate detection from tree node bounds
     * Level 3: Layout-based coordinate calculation from screen dimensions
     *
     * @param screenWidth  Screen width in pixels
     * @param screenHeight Screen height in pixels
     * @param digit        Single digit character as string ("0"-"9")
     * @return true if the digit was likely clicked successfully
     */
    fun clickDigit(screenWidth: Int, screenHeight: Int, digit: String): Boolean {
        try {
            // Level 1: Direct node click
            val root = service.rootInActiveWindow
            if (root != null) {
                if (findAndClickDigitNode(root, digit)) {
                    root.recycle()
                    return true
                }
                Log.w(TAG, "直接节点点击失败，尝试坐标点击: $digit")

                // Level 2: Smart coordinate detection from tree
                val coordMap = detectDigitCoordinatesFromTree(root)
                root.recycle()
                val coord = coordMap[digit]
                if (coord != null) {
                    if (dispatchTapWithRetry(coord.first.toInt(), coord.second.toInt(), digit)) {
                        return true
                    }
                    Log.w(TAG, "智能检测坐标点击失败: $digit")
                } else {
                    Log.w(TAG, "智能检测未找到数字键盘布局: $digit")
                }
            }

            // Level 3: Layout-based coordinate calculation
            val profiles = generateLayoutProfiles(screenWidth, screenHeight)
            for ((name, layout) in profiles) {
                val pos = layout[digit] ?: continue
                if (dispatchTapWithRetry(pos.first.toInt(), pos.second.toInt(), digit)) {
                    Log.d(TAG, "布局 $name 点击成功: $digit")
                    return true
                }
                Log.w(TAG, "布局 $name 点击失败，尝试下一个: $digit")
                Thread.sleep(200L)
            }

            Log.e(TAG, "所有数字密码键盘布局都失败: $digit")
            return false
        } catch (e: Exception) {
            Log.e(TAG, "数字密码键盘点击异常: $digit", e)
            return false
        }
    }

    /**
     * Input a complete numeric password by clicking each digit sequentially.
     *
     * @param password     The numeric password string (digits only)
     * @param screenWidth  Screen width in pixels
     * @param screenHeight Screen height in pixels
     */
    fun inputNumericPassword(password: String, screenWidth: Int, screenHeight: Int) {
        Log.d(TAG, "开始数字密码输入, 长度=${password.length}")
        for ((index, ch) in password.withIndex()) {
            val digit = ch.toString()
            if (!clickDigit(screenWidth, screenHeight, digit)) {
                Log.w(TAG, "数字 $ch 点击可能失败")
            }
            // Longer delay after last digit to allow the system to process
            val delay = if (index == password.length - 1) 500L else 300L
            Thread.sleep(delay)
        }
    }

    /**
     * Dispatch a tap gesture at the given coordinates with one retry on failure.
     *
     * @return true if the tap was dispatched (does not guarantee the tap hit the target)
     */
    private fun dispatchTapWithRetry(x: Int, y: Int, digit: String): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) return false
        try {
            Thread.sleep(50L)
            performTap(x.toFloat(), y.toFloat())
            Thread.sleep(200L)
            return true
        } catch (_: Exception) {
            Log.w(TAG, "点击第1次失败，重试: $digit ($x, $y)")
            try {
                Thread.sleep(100L)
                performTap(x.toFloat(), y.toFloat())
                Thread.sleep(200L)
                return true
            } catch (e: Exception) {
                Log.e(TAG, "点击重试也失败: $digit ($x, $y)", e)
                return false
            }
        }
    }

    /**
     * Perform a single tap gesture at the given screen coordinates.
     *
     * Uses AccessibilityService.dispatchGesture with a 100ms stroke.
     * Requires API 24+ (Build.VERSION_CODES.N).
     */
    private fun performTap(x: Float, y: Float) {
        val path = Path()
        path.moveTo(x, y)
        val stroke = GestureDescription.StrokeDescription(path, 0L, 100L)
        val gesture = GestureDescription.Builder()
            .addStroke(stroke)
            .build()
        service.dispatchGesture(gesture, null, null)
    }
}
