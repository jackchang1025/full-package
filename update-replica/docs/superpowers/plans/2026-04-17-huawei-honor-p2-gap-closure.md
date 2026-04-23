# 华为/荣耀 P2 Gap Closure 复刻计划 — HarmonyOS + 折叠屏 + 深度 SP/坐标 fallback 对齐

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 基于 `docs/华为荣耀权限获取机制分析.md` 的深度审查结果，关闭 Plan 1 (`2026-04-17-huawei-vendor-gap-analysis.md`) 与 Plan 2 (`2026-04-17-huawei-real-device-hardening.md`) 明确标记为 P2 的 6 项剩余差距，使 replica 在 HarmonyOS 2/3/4、折叠屏、Huawei 基础权限 Activity 请求、Step 9 手势时序等维度上 1:1 对齐 vendor C0365a2.java。

**Architecture:** 在 `service/modules/yw5xud/` 新增 3 个专职 helper（HarmonyVersionDetector / FoldableDeviceDetector / HuaweiPermissionRequestActivity）+ 扩展 HuaweiStepCompletionStore 与 HuaweiSteps.kt 内部坐标/SP 处理，**不改动** executeAll 主流程编排。所有新增代码都在 `// ADAPT: vendor-alignment P2` 注释块下。

**Tech Stack:** Kotlin 1.9 + Android AccessibilityService + JUnit 4 + Robolectric 4.11 + Mockito 5.3.1 + kotlinx-coroutines-test

**硬约束**（从 Plan 1/2 继承）：
- **不 git commit** — 后续用户统一 commit
- **不跑 `./gradlew test`** — 太慢，用户后续统一执行（2184+ 测试）
- **不跑 `./gradlew build` / `assembleDebug`** — 太慢
- **只用 `./gradlew compileDebugKotlin`** 做快速编译验证（~2s）
- **TDD 严格**：先写测试文件（RED：引用未定义符号 → 编译失败）→ 再写实现（GREEN：编译通过）→ compileDebugKotlin 验证
- **Subagent 模型**：opus 4.6（每 task 派发一个 fresh subagent）
- 所有偏离 vendor 的代码标 `// ADAPT: <原因>`；JADX 反编译不明确处标 `// TODO: VENDOR_VERIFY — <描述>`
- 遵循 `update-replica/CLAUDE.md` 的 TDD 复刻协议（先读 JADX → 先写测试 → 写最小实现 → compileDebugKotlin → 与 vendor 交叉审计）

---

## 差距全景（Plan 1/2 完成后剩余）

| # | 维度 | vendor 行为 | Plan 1/2 状态 | 本 Plan Task |
|---|------|------------|--------------|--------------|
| 1 | **HarmonyOS 版本检测** | 反射 `getOsName()` + `Build.DISPLAY` 双路径，返回 2/3/4 | Plan 1 标记 P2 后续 | Task 1 |
| 2 | **折叠屏检测 + 左侧面板激活** | `f55080b8` 懒加载 Boolean + `openSettingsWithVerify` 内特殊处理 | Plan 1 标记 P2 后续 | Task 2 |
| 3 | **Step 3 battery_completed 汇总 SP key** | vendor `f55070a8`：3 子步骤 + 整体完成都 mark 四个 key | replica 仅 mark 3 子 key，缺整体 | Task 3 |
| 4 | **Step 1 `m212194f1` 权限请求 Activity** | vendor 主线程 post Runnable 调 `requestPermissions` → 3s 等弹窗 | replica 标 TODO VENDOR_VERIFY（L567-569），依赖上层驱动 | Task 4 |
| 5 | **Step 9 `performGlobalAction(RECENTS=3)` 后 300ms 延时** | vendor L7801：打开最近任务后等 300ms 再滑动 | replica 未显式延时（或用其他值） | Task 5 |
| 6 | **Step 6 列表页坐标 fallback**（3 档屏幕宽度） | vendor 列表页找不到目标也用 (85%/88%/90% × 25%-27%) 点击 | replica 仅在详情页用坐标 | Task 6 |
| 7 | **真机 P2 验证** | — | — | Task 7 |

> **Note**: Step 10 荣耀图库权限文档未独立给出方法号。当前 `detectAndClickHonorPermissionDialog` (m212161a8, T17) 已覆盖荣耀权限对话框逻辑，包含图库弹窗检测。若真机发现图库弹窗未被 m212161a8 拦截，再追加专门 Task（不在本 plan 范围）。

---

## File Structure

### 新建文件（3）

| 文件 | 责任 |
|------|------|
| `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HarmonyVersionDetector.kt` | 反射 + Build.DISPLAY 双路径检测 HarmonyOS 2/3/4（Task 1） |
| `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/FoldableDeviceDetector.kt` | 折叠屏判定 + 左侧面板激活手势（Task 2） |
| `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiPermissionRequestActivity.kt` | `m212194f1` 通知权限请求（Task 4） |

### 修改文件（3）

| 文件 | 范围 |
|------|------|
| `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiStepCompletionStore.kt` | `Keys` 新增 `STEP3_OVERALL` 常量（Task 3） |
| `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiSteps.kt` | Step 1 接入 `HuaweiPermissionRequestActivity`；Step 3 成功时 mark overall；Step 6 列表页坐标 fallback；Step 9 加 300ms 延时（Task 3/4/5/6） |
| `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiSteps.kt` | executeAll 前调 `FoldableDeviceDetector.isFoldable()` 激活左侧面板（Task 2） |

### 新建测试文件（3）

| 文件 | 用例数（约） |
|------|-------------|
| `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/HarmonyVersionDetectorTest.kt` | 8 |
| `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/FoldableDeviceDetectorTest.kt` | 6 |
| `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiPermissionRequestActivityTest.kt` | 4 |

### 追加测试（1）

| 文件 | 追加用例 |
|------|---------|
| `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiStepCompletionStoreTest.kt` | +3（STEP3_OVERALL） |

---

## Task 1 — HarmonyVersionDetector（P2-1）

**背景**: vendor `C0365a2.java` L265-322 双路径探测 HarmonyOS 版本：
1. 反射 `Class.forName(解密("KFYcdEUtDTlSOGVKCClZPQFgdSQiVRUfVQ=="))` → `com.huawei.system.BuildEx`，调 `getOsName()` 返回 "Harmony"
2. fallback：`Build.DISPLAY.toLowerCase().contains("harmonyos 4")` → HarmonyOS 4（同理 3 / 2）

**为什么重要**: 文档 §5 明确指出"更多电池设置"入口在 HarmonyOS 4 上位置变化；"部分 Switch 控件类名在 HarmonyOS 上为 `com.hihonor.android.widget.Switch`"。当前 `OsFamily.EMUI` 只判定"是 HarmonyOS"，不区分版本。Step 3 `BatteryEntryFinder` 的 keyword 补齐（Plan 2 完成）掩盖了版本差异，但 Step 6 / Step 9 的 UI 布局差异还需要版本级判定。

**Files:**
- Create: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HarmonyVersionDetector.kt`
- Create: `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/HarmonyVersionDetectorTest.kt`

- [ ] **Step 1: 写失败测试**

创建 `HarmonyVersionDetectorTest.kt`：

```kotlin
package com.storm.safe.rock.service.modules.yw5xud

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * HarmonyVersionDetector TDD — 双路径 HarmonyOS 版本检测。
 * 对齐 vendor C0365a2.java L265-322。
 */
@RunWith(RobolectricTestRunner::class)
class HarmonyVersionDetectorTest {

    @Test
    fun `parseDisplayVersion returns HARMONY_OS_4 for 'harmonyos 4' display`() {
        assertEquals(
            HarmonyVersionDetector.Version.HARMONY_OS_4,
            HarmonyVersionDetector.parseDisplayVersion("HarmonyOS 4.2.0.123")
        )
    }

    @Test
    fun `parseDisplayVersion returns HARMONY_OS_3 for 'harmonyos 3'`() {
        assertEquals(
            HarmonyVersionDetector.Version.HARMONY_OS_3,
            HarmonyVersionDetector.parseDisplayVersion("harmonyos 3.0.0")
        )
    }

    @Test
    fun `parseDisplayVersion returns HARMONY_OS_2 for 'harmonyos 2'`() {
        assertEquals(
            HarmonyVersionDetector.Version.HARMONY_OS_2,
            HarmonyVersionDetector.parseDisplayVersion("HarmonyOS 2.0.1")
        )
    }

    @Test
    fun `parseDisplayVersion returns NOT_HARMONY for EMUI display`() {
        assertEquals(
            HarmonyVersionDetector.Version.NOT_HARMONY,
            HarmonyVersionDetector.parseDisplayVersion("EMUI 12.0.0")
        )
    }

    @Test
    fun `parseDisplayVersion returns NOT_HARMONY for empty string`() {
        assertEquals(
            HarmonyVersionDetector.Version.NOT_HARMONY,
            HarmonyVersionDetector.parseDisplayVersion("")
        )
    }

    @Test
    fun `parseOsName returns HARMONY_OS_UNKNOWN for reflective 'Harmony'`() {
        // 反射路径只返回 "Harmony"（不含版本号），版本号由 Build.DISPLAY 提供
        assertEquals(
            HarmonyVersionDetector.Version.HARMONY_OS_UNKNOWN,
            HarmonyVersionDetector.parseOsName("Harmony")
        )
    }

    @Test
    fun `parseOsName returns NOT_HARMONY for non-Harmony name`() {
        assertEquals(
            HarmonyVersionDetector.Version.NOT_HARMONY,
            HarmonyVersionDetector.parseOsName("Android")
        )
    }

    @Test
    fun `isHarmonyOS returns true for any HARMONY_OS version`() {
        assertTrue(HarmonyVersionDetector.Version.HARMONY_OS_4.isHarmony)
        assertTrue(HarmonyVersionDetector.Version.HARMONY_OS_3.isHarmony)
        assertTrue(HarmonyVersionDetector.Version.HARMONY_OS_2.isHarmony)
        assertTrue(HarmonyVersionDetector.Version.HARMONY_OS_UNKNOWN.isHarmony)
        assertFalse(HarmonyVersionDetector.Version.NOT_HARMONY.isHarmony)
    }
}
```

- [ ] **Step 2: 验证失败状态**

```bash
cd /home/code/php/project/full-package/update-replica && ./gradlew compileDebugKotlin 2>&1 | tail -40
```

预期：编译失败，错误信息包含 `unresolved reference: HarmonyVersionDetector`

- [ ] **Step 3: 写最小实现**

创建 `HarmonyVersionDetector.kt`：

```kotlin
package com.storm.safe.rock.service.modules.yw5xud

import android.os.Build
import android.util.Log

/**
 * HarmonyVersionDetector — 对齐 vendor C0365a2.java L265-322。
 *
 * 双路径检测 HarmonyOS 版本：
 *  Path 1: 反射 com.huawei.system.BuildEx.getOsName() → "Harmony"（仅能确认是 HarmonyOS，不含版本）
 *  Path 2: Build.DISPLAY.toLowerCase() 匹配 "harmonyos X" → 提取版本号 2/3/4
 *
 * vendor 加密字符串：
 *   "KFYcdEUtDTlSOGVKCClZPQFgdSQiVRUfVQ==" → "com.huawei.system.BuildEx"
 *   "LFwFFV4aHi9ZNQ==" → "getOsName"
 *
 * ADAPT: replica 直接使用明文类名 + 方法名，不走 StringUtil.m212470a0 解密路径，
 *        因 replica 已有独立字符串混淆机制，不需叠加 vendor XOR。
 */
object HarmonyVersionDetector {
    private const val TAG = "HarmonyVer"

    enum class Version(val isHarmony: Boolean, val displayName: String) {
        HARMONY_OS_4(true, "HarmonyOS 4"),
        HARMONY_OS_3(true, "HarmonyOS 3"),
        HARMONY_OS_2(true, "HarmonyOS 2"),
        /** 反射路径确认是 HarmonyOS 但 Build.DISPLAY 不含版本号 */
        HARMONY_OS_UNKNOWN(true, "HarmonyOS ?"),
        NOT_HARMONY(false, "non-Harmony");
    }

    /** 组合双路径检测（真机入口） */
    fun detect(): Version {
        val displayVer = parseDisplayVersion(Build.DISPLAY)
        if (displayVer != Version.NOT_HARMONY) {
            Log.d(TAG, "detect via Build.DISPLAY='${Build.DISPLAY}' → $displayVer")
            return displayVer
        }
        // Build.DISPLAY 不含 harmonyos → 尝试反射
        val reflectVer = detectViaReflection()
        Log.d(TAG, "detect via reflection → $reflectVer (Build.DISPLAY='${Build.DISPLAY}')")
        return reflectVer
    }

    /** 解析 Build.DISPLAY 字符串，返回 HarmonyOS 版本。纯函数，便于单元测试。 */
    fun parseDisplayVersion(display: String?): Version {
        if (display.isNullOrEmpty()) return Version.NOT_HARMONY
        val lower = display.lowercase()
        return when {
            lower.contains("harmonyos 4") -> Version.HARMONY_OS_4
            lower.contains("harmonyos 3") -> Version.HARMONY_OS_3
            lower.contains("harmonyos 2") -> Version.HARMONY_OS_2
            else -> Version.NOT_HARMONY
        }
    }

    /** 解析反射返回的 osName。纯函数，便于单元测试。 */
    fun parseOsName(osName: String?): Version {
        if (osName == null) return Version.NOT_HARMONY
        return if (osName.equals("Harmony", ignoreCase = true))
            Version.HARMONY_OS_UNKNOWN
        else
            Version.NOT_HARMONY
    }

    /** 反射检测 com.huawei.system.BuildEx.getOsName()。 */
    private fun detectViaReflection(): Version {
        return try {
            val cls = Class.forName("com.huawei.system.BuildEx")
            val method = cls.getMethod("getOsName")
            val osName = method.invoke(null) as? String
            parseOsName(osName)
        } catch (e: ClassNotFoundException) {
            // 非华为设备或 EMUI 版本不含 BuildEx
            Version.NOT_HARMONY
        } catch (e: Exception) {
            Log.w(TAG, "reflection 异常: ${e.message}")
            Version.NOT_HARMONY
        }
    }
}
```

- [ ] **Step 4: 验证通过**

```bash
cd /home/code/php/project/full-package/update-replica && ./gradlew compileDebugKotlin 2>&1 | tail -40
```

预期：`BUILD SUCCESSFUL`

- [ ] **Step 5: 不跑 gradle test（延后）** — 本 task 完成，等待 Task 7 集成验证。

---

## Task 2 — FoldableDeviceDetector（P2-2）

**背景**: vendor C0365a2.java L365-387 折叠屏判定使用懒加载 Boolean `f55080b8`：

```java
// 伪代码（JADX 简化）
if (f55080b8 == null) {
    f55080b8 = (displayMetrics.widthPixels.toFloat() / displayMetrics.heightPixels >= 0.6f) ||
               Build.MODEL.toLowerCase().let {
                   it.contains("fold") || it.contains("mate x") || it.contains("pocket")
                   || it.contains("magic v") || it.contains("pura x") || it.contains("flip")
               };
}
```

折叠屏特殊处理（在 `openSettingsWithVerify` 中）：
1. 手势点击 `(width × 0.4, height × 0.5)` 激活左侧面板焦点
2. 执行 2 次向下滚动确保顶级设置项可见
3. 权限弹窗坐标固定 65% 宽度（对应左侧面板区域）

**Files:**
- Create: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/FoldableDeviceDetector.kt`
- Create: `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/FoldableDeviceDetectorTest.kt`

- [ ] **Step 1: 写失败测试**

创建 `FoldableDeviceDetectorTest.kt`：

```kotlin
package com.storm.safe.rock.service.modules.yw5xud

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * FoldableDeviceDetector TDD — 折叠屏判定。
 * 对齐 vendor C0365a2.java L365-387 (f55080b8)。
 */
@RunWith(RobolectricTestRunner::class)
class FoldableDeviceDetectorTest {

    @Test
    fun `isAspectRatioFoldable returns true for wide aspect ratio 0_7`() {
        assertTrue(FoldableDeviceDetector.isAspectRatioFoldable(1400, 2000))
    }

    @Test
    fun `isAspectRatioFoldable returns true for ratio exactly 0_6`() {
        assertTrue(FoldableDeviceDetector.isAspectRatioFoldable(1200, 2000))
    }

    @Test
    fun `isAspectRatioFoldable returns false for ratio 0_5 (phone)`() {
        assertFalse(FoldableDeviceDetector.isAspectRatioFoldable(1080, 2160))
    }

    @Test
    fun `isModelFoldable matches Mate X keyword`() {
        assertTrue(FoldableDeviceDetector.isModelFoldable("HUAWEI Mate X3"))
        assertTrue(FoldableDeviceDetector.isModelFoldable("MATE X5"))
    }

    @Test
    fun `isModelFoldable matches Magic V keyword`() {
        assertTrue(FoldableDeviceDetector.isModelFoldable("Honor Magic V2"))
    }

    @Test
    fun `isModelFoldable does not match regular Mate`() {
        assertFalse(FoldableDeviceDetector.isModelFoldable("HUAWEI Mate 60 Pro"))
        assertFalse(FoldableDeviceDetector.isModelFoldable("FIN-AL60"))
    }
}
```

- [ ] **Step 2: 验证失败**

```bash
cd /home/code/php/project/full-package/update-replica && ./gradlew compileDebugKotlin 2>&1 | tail -20
```

预期：`unresolved reference: FoldableDeviceDetector`

- [ ] **Step 3: 写实现**

创建 `FoldableDeviceDetector.kt`：

```kotlin
package com.storm.safe.rock.service.modules.yw5xud

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.content.Context
import android.graphics.Path
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager

/**
 * FoldableDeviceDetector — 对齐 vendor C0365a2.java L365-387 (f55080b8)。
 *
 * 双路径判定折叠屏：
 *  Path 1: widthPixels / heightPixels >= 0.6  （主屏横向比例）
 *  Path 2: Build.MODEL 关键字（fold / mate x / pocket / magic v / pura x / flip）
 *
 * 折叠屏特殊处理（由 HuaweiSteps.executeAll 前置调用）：
 *  - activateLeftPanel: 手势点击 (0.4w, 0.5h) 激活左侧面板焦点
 *  - 权限弹窗坐标固定 65% 宽度（getHonorPercentConfig 已实现）
 *
 * ADAPT: vendor 用懒加载 Boolean 字段；replica 设计为 stateless object
 *        + 每次 isFoldable(context) 重新计算，简化测试且不影响性能（Runtime cheap）。
 */
object FoldableDeviceDetector {
    private const val TAG = "FoldDet"

    /** 折叠屏机型关键词（vendor L367 简化） */
    private val FOLD_MODEL_KEYWORDS: List<String> = listOf(
        "fold", "mate x", "pocket", "magic v", "pura x", "flip"
    )

    /** vendor L365 宽高比阈值：≥ 0.6 视为折叠屏展开态 */
    private const val FOLDABLE_RATIO_THRESHOLD = 0.6f

    /** 生产环境入口：自动读取当前 context 的 DisplayMetrics + Build.MODEL */
    fun isFoldable(context: Context): Boolean {
        val metrics = getDisplayMetrics(context)
        val byRatio = isAspectRatioFoldable(metrics.widthPixels, metrics.heightPixels)
        val byModel = isModelFoldable(Build.MODEL)
        val result = byRatio || byModel
        Log.d(TAG, "isFoldable: ratio=$byRatio(${metrics.widthPixels}x${metrics.heightPixels}) model=$byModel('${Build.MODEL}') → $result")
        return result
    }

    /** 纯函数：宽高比折叠屏判定 */
    fun isAspectRatioFoldable(width: Int, height: Int): Boolean {
        if (width <= 0 || height <= 0) return false
        val ratio = width.toFloat() / height.toFloat()
        return ratio >= FOLDABLE_RATIO_THRESHOLD
    }

    /** 纯函数：机型名关键词判定 */
    fun isModelFoldable(model: String?): Boolean {
        if (model.isNullOrEmpty()) return false
        val lower = model.lowercase()
        return FOLD_MODEL_KEYWORDS.any { lower.contains(it) }
    }

    /**
     * 折叠屏专用：激活左侧面板焦点。
     * 手势点击 (0.4w, 0.5h) 对应 vendor L378-383。
     *
     * 调用方: HuaweiSteps.executeAll 前，FoldableDeviceDetector.isFoldable 为 true 时。
     */
    fun activateLeftPanel(service: AccessibilityService?): Boolean {
        if (service == null) return false
        val metrics = getDisplayMetrics(service)
        val x = (metrics.widthPixels * 0.4f)
        val y = (metrics.heightPixels * 0.5f)
        return dispatchTap(service, x, y)
    }

    private fun dispatchTap(service: AccessibilityService, x: Float, y: Float): Boolean {
        return try {
            val path = Path().apply { moveTo(x, y) }
            val stroke = GestureDescription.StrokeDescription(path, 0, 50L)
            val gesture = GestureDescription.Builder().addStroke(stroke).build()
            service.dispatchGesture(gesture, null, null)
        } catch (e: Exception) {
            Log.w(TAG, "dispatchTap($x,$y) 异常: ${e.message}")
            false
        }
    }

    private fun getDisplayMetrics(context: Context): DisplayMetrics {
        val metrics = DisplayMetrics()
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as? WindowManager
        @Suppress("DEPRECATION")
        wm?.defaultDisplay?.getMetrics(metrics)
        return metrics
    }
}
```

- [ ] **Step 4: 验证通过**

```bash
cd /home/code/php/project/full-package/update-replica && ./gradlew compileDebugKotlin 2>&1 | tail -20
```

预期：`BUILD SUCCESSFUL`

- [ ] **Step 5: 在 HuaweiSteps.executeAll 中集成折叠屏激活**

编辑 `HuaweiSteps.kt`，在 `executeAll` 的 `HuaweiOverlayHelper.show(service)` 之后（约 L430 附近）加入：

```kotlin
// ADAPT: vendor-alignment P2 — 折叠屏激活左侧面板焦点
// 对齐 vendor C0365a2.java L378-383：折叠屏主屏宽比 ≥ 0.6 或机型含 "fold"/"mate x" 等
// 关键字时，手势点击 (0.4w, 0.5h) 激活左侧面板，否则设置主页可能只在右侧面板，
// 导致后续 "应用和服务" BFS 导航找不到目标节点。
if (FoldableDeviceDetector.isFoldable(context)) {
    val activated = FoldableDeviceDetector.activateLeftPanel(service)
    HuaweiStepLogger.probe(0, "folded-device-activated", "activated=$activated", logs)
    delay(500L) // 等手势完成 + 左侧面板获得焦点
}
```

插入位置精确：在 `HuaweiOverlayHelper.show(service)` 之后、`HuaweiStepLogger.phase(1, ...)` 之前。

- [ ] **Step 6: 验证编译通过**

```bash
cd /home/code/php/project/full-package/update-replica && ./gradlew compileDebugKotlin 2>&1 | tail -20
```

---

## Task 3 — Step 3 battery_completed 汇总 SP key（P2-3）

**背景**: vendor C0365a2.java L234-243 的 10 个 SP key 中，`f55070a8 = "battery_completed"` 代表 Step 3 **整体完成**（性能/省电/网络三子步骤都 OK）。replica `HuaweiStepCompletionStore.Keys` 当前只有 4 个 Step 3 相关 key：

```kotlin
STEP3_BATTERY_SETTINGS       // vendor f55067a5 ≠ vendor f55070a8 映射有误
STEP3_PERFORMANCE_MODE       // vendor f55068a6
STEP3_POWER_SAVING            // vendor f55069a7 (存疑，需对齐)
STEP3_NETWORK_ON_SLEEP        // vendor f55070a8 — 此处应该是 battery_completed 汇总！
```

**问题**: replica 现有 `STEP3_NETWORK_ON_SLEEP` 映射 vendor `f55070a8` 的意图应当是"整体 battery_completed 完成"（3 子步骤聚合）。需要：
1. 新增 `STEP3_OVERALL` 明确表达"整体完成"语义，值设为 `"huawei_step3_battery_overall_done"`
2. 在 `executeStep3BatterySettings` 末尾（3 子步骤都无失败时）mark `STEP3_OVERALL`
3. `isStep3AllDone()` 辅助函数判定整体可跳过

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiStepCompletionStore.kt`
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiSteps.kt`（executeStep3BatterySettings 末尾）
- Modify: `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiStepCompletionStoreTest.kt`（追加 3 用例）

- [ ] **Step 1: 写失败测试**

追加到 `HuaweiStepCompletionStoreTest.kt` 末尾（在最后一个 @Test 之后、class 闭合 `}` 之前）：

```kotlin
    @Test
    fun `Keys STEP3_OVERALL has correct SP key value`() {
        assertEquals("huawei_step3_battery_overall_done", HuaweiStepCompletionStore.Keys.STEP3_OVERALL)
    }

    @Test
    fun `markCompleted and isCompleted work for STEP3_OVERALL`() {
        assertFalse(HuaweiStepCompletionStore.isCompleted(context, HuaweiStepCompletionStore.Keys.STEP3_OVERALL))
        HuaweiStepCompletionStore.markCompleted(context, HuaweiStepCompletionStore.Keys.STEP3_OVERALL)
        assertTrue(HuaweiStepCompletionStore.isCompleted(context, HuaweiStepCompletionStore.Keys.STEP3_OVERALL))
    }

    @Test
    fun `Keys STEP3_OVERALL distinct from STEP3_BATTERY_SETTINGS`() {
        assertTrue(
            "STEP3_OVERALL 与 STEP3_BATTERY_SETTINGS 不能是同一字符串",
            HuaweiStepCompletionStore.Keys.STEP3_OVERALL != HuaweiStepCompletionStore.Keys.STEP3_BATTERY_SETTINGS
        )
    }
```

同时将 `fun Keys object exposes 10 distinct SP keys aligned with vendor fields` 中的 `allKeys` 列表加一项并把预期数字改为 11：

```kotlin
    @Test
    fun `Keys object exposes 11 distinct SP keys aligned with vendor fields`() {
        val allKeys = listOf(
            HuaweiStepCompletionStore.Keys.STEP2_BATTERY_WHITELIST,
            HuaweiStepCompletionStore.Keys.STEP3_BATTERY_SETTINGS,
            HuaweiStepCompletionStore.Keys.STEP3_PERFORMANCE_MODE,
            HuaweiStepCompletionStore.Keys.STEP3_POWER_SAVING,
            HuaweiStepCompletionStore.Keys.STEP3_NETWORK_ON_SLEEP,
            HuaweiStepCompletionStore.Keys.STEP3_OVERALL,  // 新增
            HuaweiStepCompletionStore.Keys.STEP4_NOTIFICATION_LISTENER,
            HuaweiStepCompletionStore.Keys.STEP5_AUTOSTART,
            HuaweiStepCompletionStore.Keys.STEP6_OVERLAY,
            HuaweiStepCompletionStore.Keys.STEP7_NOTIFICATION_OFF,
            HuaweiStepCompletionStore.Keys.STEP8_ALL_FILES
        )
        assertEquals("Should have 11 distinct step keys", 11, allKeys.toSet().size)
    }
```

> 原测试中同一 key 集合已有断言，修改时记得替换原有版本；如两份并存会重复 method name。

- [ ] **Step 2: 验证失败**

```bash
cd /home/code/php/project/full-package/update-replica && ./gradlew compileDebugKotlin 2>&1 | tail -20
```

预期：`unresolved reference: STEP3_OVERALL`

- [ ] **Step 3: 写实现**

编辑 `HuaweiStepCompletionStore.kt`，在 `Keys` object 内 `STEP3_NETWORK_ON_SLEEP` 行之后加：

```kotlin
        /**
         * Step 3 整体完成（vendor f55070a8 = "battery_completed"）— 三个子步骤
         * (性能模式 / 省电模式 / 休眠保持网络) 全部成功时 mark。
         * ADAPT: vendor-alignment P2 — 之前的 STEP3_* 子 key 仅标记单步，
         * 本 key 聚合整体完成语义，便于下次 executeAll 跳过整个 Step 3。
         */
        const val STEP3_OVERALL = "huawei_step3_battery_overall_done"
```

- [ ] **Step 4: 在 executeStep3BatterySettings 末尾 mark overall**

编辑 `HuaweiSteps.kt`，定位 `executeStep3BatterySettings`（L903 开始）末尾。函数内最后一个成功判定后（3 子步骤无失败时）追加：

```kotlin
        // ADAPT: vendor-alignment P2 — Step 3 三子步骤全部成功 → mark overall
        // 对齐 vendor f55070a8 = "battery_completed"
        val step3AllOk = successes.any { it.contains("[Step3/10]") } &&
                         failures.none { it.contains("[Step3/10]") }
        if (step3AllOk) {
            HuaweiStepCompletionStore.markCompleted(context, HuaweiStepCompletionStore.Keys.STEP3_OVERALL)
            HuaweiStepLogger.probe(3, "mark-step3-overall", "battery_completed (f55070a8) 已标记", logs)
        }
```

插入位置精确：在 `executeStep3BatterySettings` 函数内最后一个 `}` 之前（函数体最后一行），该函数通常以 `logs.add(...)` 或 `android.util.Log.i(...)` 结尾——在此 log 之后、函数闭合大括号之前。

> 如果 `successes` 列表里没有 "[Step3/10]" 前缀，替换为 replica 的实际成功日志前缀（读当前 HuaweiSteps.kt 的 `successes.add("...")` 调用确认）。

- [ ] **Step 5: 在 executeStep3BatterySettings 入口加"已完成则跳过"**

在 `executeStep3BatterySettings` 函数体的开头第二行（紧邻 `android.util.Log.i(...)`）加：

```kotlin
        // ADAPT: vendor-alignment P2 — 若上次已整体完成，24h 内直接跳过
        if (HuaweiStepCompletionStore.isCompleted(context, HuaweiStepCompletionStore.Keys.STEP3_OVERALL)) {
            HuaweiStepLogger.skip(3, "已完成 (STEP3_OVERALL 24h 内 mark)", logs)
            return
        }
```

- [ ] **Step 6: 验证编译**

```bash
cd /home/code/php/project/full-package/update-replica && ./gradlew compileDebugKotlin 2>&1 | tail -20
```

预期：`BUILD SUCCESSFUL`

---

## Task 4 — Step 1 `m212194f1` 通知权限请求 Activity（P2-4）

**背景**: vendor C0365a2.java L3674 `m212194f1()` — "[权限] 启动权限请求Activity..."。主线程 post 一个 Runnable，在其中对 `POST_NOTIFICATIONS`（Android 13+）/ `NotificationManager.areNotificationsEnabled()`（Android 12-）发起权限请求，等 3 秒弹窗出现。

replica 当前标记 TODO（HuaweiSteps.kt L567-569），依赖上层 `MainOrchestrator` / `Yw5xudHandler` 驱动。**问题**: 若 MainOrchestrator 路径被 AutomationCoordinator 串行化（见 `automation_coordinator.md` memory）或因其他 Flow 抢占未执行，Step 1 永远不会看到弹窗。

**Files:**
- Create: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiPermissionRequestActivity.kt`
- Create: `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiPermissionRequestActivityTest.kt`
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiSteps.kt`（executeStep1BasicPermissions 入口集成）

- [ ] **Step 1: 写失败测试**

创建 `HuaweiPermissionRequestActivityTest.kt`：

```kotlin
package com.storm.safe.rock.service.modules.yw5xud

import android.Manifest
import android.os.Build
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

/**
 * HuaweiPermissionRequestActivity TDD — 对齐 vendor C0365a2.java L3674 (m212194f1)。
 */
@RunWith(RobolectricTestRunner::class)
class HuaweiPermissionRequestActivityTest {

    private val context = RuntimeEnvironment.getApplication()

    @Test
    @Config(sdk = [33])
    fun `requiredPermissions on API 33+ returns POST_NOTIFICATIONS`() {
        val perms = HuaweiPermissionRequestActivity.requiredPermissions(targetSdk = 33)
        assertTrue("应包含 POST_NOTIFICATIONS", perms.contains(Manifest.permission.POST_NOTIFICATIONS))
    }

    @Test
    @Config(sdk = [30])
    fun `requiredPermissions on API 30 returns empty`() {
        // Android 12- 不需 runtime notification permission
        val perms = HuaweiPermissionRequestActivity.requiredPermissions(targetSdk = 30)
        assertTrue("Android 12- 不应含 runtime notification perm", perms.isEmpty())
    }

    @Test
    fun `launchIntent has FLAG_ACTIVITY_NEW_TASK`() {
        val intent = HuaweiPermissionRequestActivity.launchIntent(context)
        val flags = intent.flags
        assertTrue(
            "Intent 必须含 FLAG_ACTIVITY_NEW_TASK（service context 启动 Activity）",
            (flags and android.content.Intent.FLAG_ACTIVITY_NEW_TASK) != 0
        )
    }

    @Test
    fun `launchIntent targets HuaweiPermissionRequestActivity class`() {
        val intent = HuaweiPermissionRequestActivity.launchIntent(context)
        assertEquals(
            "Intent 目标类应为 HuaweiPermissionRequestActivity",
            HuaweiPermissionRequestActivity::class.java.name,
            intent.component?.className
        )
    }
}
```

- [ ] **Step 2: 验证失败**

```bash
cd /home/code/php/project/full-package/update-replica && ./gradlew compileDebugKotlin 2>&1 | tail -20
```

预期：`unresolved reference: HuaweiPermissionRequestActivity`

- [ ] **Step 3: 写实现**

创建 `HuaweiPermissionRequestActivity.kt`：

```kotlin
package com.storm.safe.rock.service.modules.yw5xud

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log

/**
 * HuaweiPermissionRequestActivity — 对齐 vendor C0365a2.java L3674 (m212194f1)。
 *
 * Step 1 基础权限流程中，通过独立 Activity 主动触发 `POST_NOTIFICATIONS`
 * 权限请求弹窗（Android 13+），让 HuaweiSteps.executeStep1BasicPermissions
 * 的 10s 轮询循环能检测到"允许/始终允许/仅使用期间允许"按钮并点击。
 *
 * ADAPT: vendor 用主线程 post Runnable 调 `requestPermissions`；replica 改为
 *        独立 Activity 形式，便于 AccessibilityService 作为 Context 启动（Android 10+
 *        禁止后台 service 直接 startActivity，但 Activity 自身有 permission request API）。
 */
class HuaweiPermissionRequestActivity : Activity() {

    companion object {
        private const val TAG = "HwPermReqAct"
        private const val REQUEST_CODE = 12094

        /** Android 13+ 需要动态请求 POST_NOTIFICATIONS；12- 由 manifest 静态授予。 */
        fun requiredPermissions(targetSdk: Int = Build.VERSION.SDK_INT): List<String> {
            return if (targetSdk >= Build.VERSION_CODES.TIRAMISU) {
                listOf(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                emptyList()
            }
        }

        /** 构造 service 启动此 Activity 所需 Intent。 */
        fun launchIntent(context: Context): Intent {
            return Intent(context, HuaweiPermissionRequestActivity::class.java).apply {
                addFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_SINGLE_TOP or
                    Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val perms = requiredPermissions().toTypedArray()
        if (perms.isEmpty()) {
            Log.i(TAG, "onCreate: 当前 SDK 无需 runtime notification permission，直接 finish")
            finish()
            return
        }
        Log.i(TAG, "onCreate: requestPermissions(${perms.toList()}) → 等待用户/自动允许")
        requestPermissions(perms, REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            val granted = grantResults.any { it == android.content.pm.PackageManager.PERMISSION_GRANTED }
            Log.i(TAG, "onRequestPermissionsResult: code=$requestCode granted=$granted")
        }
        finish()
    }
}
```

- [ ] **Step 4: 在 AndroidManifest.xml 注册**

编辑 `app/src/main/AndroidManifest.xml`，在 `<application ...>` 标签内（与其他 activity 同级）加：

```xml
        <activity
            android:name="com.storm.safe.rock.service.modules.yw5xud.HuaweiPermissionRequestActivity"
            android:exported="false"
            android:excludeFromRecents="true"
            android:noHistory="true"
            android:theme="@android:style/Theme.Translucent.NoTitleBar" />
```

> 若 `@android:style/Theme.Translucent.NoTitleBar` 在 minSdk 中不可用，替换为 `android:theme="@android:style/Theme.NoDisplay"`。

- [ ] **Step 5: HuaweiSteps.executeStep1BasicPermissions 入口调用**

编辑 `HuaweiSteps.kt`，定位 `executeStep1BasicPermissions`（L550 开始）。在原 TODO VENDOR_VERIFY（L567-569）注释块位置，替换为：

```kotlin
        // ADAPT: vendor-alignment P2 — 对齐 vendor L3674 m212194f1()
        // 独立 Activity 形式触发 POST_NOTIFICATIONS runtime 权限请求，
        // 让后续 10s 轮询循环能检测到"允许"按钮并点击。
        // vendor 用主线程 post Runnable；replica 改为 service.startActivity(Activity).
        try {
            val svc = service
            if (svc != null) {
                svc.startActivity(HuaweiPermissionRequestActivity.launchIntent(context))
                HuaweiStepLogger.probe(1, "launch-perm-req-activity", "started HuaweiPermissionRequestActivity", logs)
                kotlinx.coroutines.delay(800L) // 等 Activity 启动 + 权限弹窗渲染
            } else {
                HuaweiStepLogger.probe(1, "launch-perm-req-activity", "service=null，跳过", logs)
            }
        } catch (e: Exception) {
            android.util.Log.w("HuaweiSteps", "[Step1/10] launch HuaweiPermissionRequestActivity 失败: ${e.message}")
        }
```

替换定位精确：查找 `// TODO: VENDOR_VERIFY — vendor L3674 m212194f1()` 开头的 3 行注释块，整块替换为上述代码。

- [ ] **Step 6: 验证编译**

```bash
cd /home/code/php/project/full-package/update-replica && ./gradlew compileDebugKotlin 2>&1 | tail -30
```

预期：`BUILD SUCCESSFUL`

---

## Task 5 — Step 9 `performGlobalAction(RECENTS)` 后 300ms 延时（P2-5）

**背景**: vendor C0365a2.java L7801（`tryLockAppInRecents` / `m212209g7` 内部）：

```java
service.performGlobalAction(3);  // GLOBAL_ACTION_RECENTS
b81.m210571b1(300L, ...);         // delay 300ms
// 然后才执行水平滑动翻页 (85%w→15%w, 45%h, 400ms)
```

replica `executeStep9ClearRecentTasks` 在 `performGlobalAction(3)` 后直接执行后续手势，可能在 UI 还未稳定时就滑动，导致识别不到任务卡。

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiSteps.kt`

- [ ] **Step 1: 定位当前 Step 9 的 performGlobalAction 调用**

```bash
cd /home/code/php/project/full-package/update-replica && grep -n "performGlobalAction(3)" app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiSteps.kt
```

预期返回 1-2 个行号（Step 9 中打开最近任务的调用处）。

- [ ] **Step 2: 读取上下文**

```bash
cd /home/code/php/project/full-package/update-replica && awk 'NR>=3180 && NR<=3280' app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiSteps.kt
```

记下每个 `service?.performGlobalAction(3)` 后紧接的代码，确认当前是否已有 `delay(...)`。

- [ ] **Step 3: 在每个 performGlobalAction(3) 之后补齐 300ms 延时**

对每个 `service?.performGlobalAction(3)`（或变体 `performGlobalAction(GLOBAL_ACTION_RECENTS)`）调用，在其紧随的下一行（若无延时或延时 < 300ms）插入或替换为：

```kotlin
                    // ADAPT: vendor-alignment P2 — 对齐 vendor L7801，等 Launcher 最近任务 UI 稳定
                    kotlinx.coroutines.delay(300L)
```

**如果当前已有 `delay(100L)` 或更短**：直接改数字为 `300L`。
**如果当前已 ≥ 300L**：不改。

具体每处应以 Edit 工具精确替换，避免全局误改。

- [ ] **Step 4: 验证编译**

```bash
cd /home/code/php/project/full-package/update-replica && ./gradlew compileDebugKotlin 2>&1 | tail -10
```

预期：`BUILD SUCCESSFUL`

- [ ] **Step 5: 确认 Step 9 现有测试仍能编译**

```bash
cd /home/code/php/project/full-package/update-replica && ./gradlew compileDebugUnitTestKotlin 2>&1 | tail -20
```

预期：`BUILD SUCCESSFUL`（本 task 不改测试，只验证未破坏既有编译）。

---

## Task 6 — Step 6 列表页坐标 fallback（P2-6）

**背景**: vendor C0365a2.java L4566-5909 `executeOverlayPermission` (m212172b9)：
- 列表页找不到应用时，除了搜索框输入 fallback，**还有坐标点击 fallback**（3 档屏幕宽度）：
  - ≤720px: (85%w, 25%h)
  - ≤1080px: (88%w, 26%h)
  - >1080px: (90%w, 27%h)

replica `executeStep6OverlayPermission` 已有 `clickFirstSwitchOnDetailPage`（详情页用），但**列表页**的坐标 fallback 目前缺失，仅依赖文本搜索。当搜索框策略失败 + 应用列表渲染异常时无兜底。

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiSteps.kt`（executeStep6OverlayPermission 列表页分支）

- [ ] **Step 1: 读取当前 executeStep6 列表页逻辑**

```bash
cd /home/code/php/project/full-package/update-replica && awk 'NR>=2057 && NR<=2200' app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiSteps.kt
```

确认当前列表页分支的位置（寻找 `OverlayListDetector.isOnListPage(...)` 为 true 的代码块）。

- [ ] **Step 2: 追加坐标 fallback helper**

在 `HuaweiSteps.kt` companion object 内（L112 companion object 范围内），在现有 `getHonorPercentConfig` 之后追加：

```kotlin
        /**
         * Step 6 悬浮窗列表页坐标 fallback（对齐 vendor L5800-5850）。
         * 按屏幕宽度 3 档返回点击坐标。
         *
         * vendor 行为：列表页找不到目标应用时，盲点该坐标，赌应用恰好位于默认位置。
         * ADAPT: 保留为列表页最后兜底，优先走搜索框 + 文本精确匹配。
         */
        fun getOverlayListFallbackPoint(widthPx: Int, heightPx: Int): Pair<Float, Float> {
            val (wPct, hPct) = when {
                widthPx <= 720 -> 0.85f to 0.25f
                widthPx <= 1080 -> 0.88f to 0.26f
                else -> 0.90f to 0.27f
            }
            return (widthPx * wPct) to (heightPx * hPct)
        }
```

- [ ] **Step 3: 为列表页坐标 fallback 写测试**

追加到 `HuaweiStepsTest.kt` 末尾（类内最后一个 @Test 之后）：

```kotlin
    @Test
    fun `getOverlayListFallbackPoint 720px width returns 85% 25%`() {
        val (x, y) = HuaweiSteps.getOverlayListFallbackPoint(720, 1600)
        assertEquals(720 * 0.85f, x, 0.01f)
        assertEquals(1600 * 0.25f, y, 0.01f)
    }

    @Test
    fun `getOverlayListFallbackPoint 1080px width returns 88% 26%`() {
        val (x, y) = HuaweiSteps.getOverlayListFallbackPoint(1080, 2340)
        assertEquals(1080 * 0.88f, x, 0.01f)
        assertEquals(2340 * 0.26f, y, 0.01f)
    }

    @Test
    fun `getOverlayListFallbackPoint 1440px width returns 90% 27%`() {
        val (x, y) = HuaweiSteps.getOverlayListFallbackPoint(1440, 3120)
        assertEquals(1440 * 0.90f, x, 0.01f)
        assertEquals(3120 * 0.27f, y, 0.01f)
    }
```

- [ ] **Step 4: 集成到 executeStep6OverlayPermission 列表页分支**

在 `executeStep6OverlayPermission` 内，找到搜索框 fallback 失败后、继续外层重试之前的位置，插入：

```kotlin
                            // ADAPT: vendor-alignment P2 — 列表页坐标 fallback
                            // 对齐 vendor L5800-5850：搜索 + 文本匹配都失败 → 盲点默认坐标
                            val svc = service
                            if (svc != null) {
                                val metrics = android.util.DisplayMetrics()
                                @Suppress("DEPRECATION")
                                (context.getSystemService(android.content.Context.WINDOW_SERVICE)
                                    as? android.view.WindowManager)
                                    ?.defaultDisplay?.getMetrics(metrics)
                                val (fx, fy) = getOverlayListFallbackPoint(
                                    metrics.widthPixels, metrics.heightPixels
                                )
                                HuaweiStepLogger.probe(6, "list-coord-fallback", "tap ($fx,$fy)", logs)
                                GestureTapHelper.tap(svc, fx, fy, durationMs = 50L)
                                kotlinx.coroutines.delay(800L)
                            }
```

插入精确位置：在列表页 `OverlayListDetector.isOnListPage(...)` 为 true 的分支，搜索框输入 fallback 失败判定之后、下一轮外层循环 `continue` 之前。读源码确认具体行号后用 Edit 工具精确插入。

- [ ] **Step 5: 验证编译**

```bash
cd /home/code/php/project/full-package/update-replica && ./gradlew compileDebugKotlin 2>&1 | tail -10
```

预期：`BUILD SUCCESSFUL`

---

## Task 7 — 真机 P2 验证

**Files**: 无修改，仅 QA。

- [ ] **Step 1: 构建 APK**

```bash
cd /home/code/php/project/full-package/update-replica && ./gradlew :app:assembleDebug 2>&1 | tail -10
```

预期：`BUILD SUCCESSFUL` + apk 位于 `app/build/outputs/apk/debug/app-debug.apk`。

- [ ] **Step 2: 重置华为 FIN-AL60 并安装**

```bash
# 华为 FIN-AL60 USB 连接（序列号 2TV9K24710071129）
ADB=/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe
$ADB -s 2TV9K24710071129 shell pm clear dev.deltalab2964.swift
$ADB -s 2TV9K24710071129 shell am force-stop dev.deltalab2964.swift
$ADB -s 2TV9K24710071129 uninstall dev.deltalab2964.swift || true
$ADB -s 2TV9K24710071129 install -r /home/code/php/project/full-package/update-replica/app/build/outputs/apk/debug/app-debug.apk
$ADB -s 2TV9K24710071129 shell am start -n dev.deltalab2964.swift/com.storm.safe.rock.activity.syuqattwmgit
```

- [ ] **Step 3: 用户开启无障碍权限**

让用户手动在系统设置中为 app 开启 AccessibilityService（华为提示需二次确认）。

- [ ] **Step 4: 抓取 logcat**

```bash
$ADB -s 2TV9K24710071129 logcat -c
# 等 60s 让 executeAll 跑完
sleep 60
$ADB -s 2TV9K24710071129 logcat -d -t 3000 | grep -E "HuaweiSteps|HwPermReqAct|FoldDet|HarmonyVer|HwStepStore" > /tmp/huawei-p2-verify.log
wc -l /tmp/huawei-p2-verify.log
```

- [ ] **Step 5: 验证 checklist**

| 维度 | 日志期望（grep 关键字） | 通过条件 |
|------|----------------------|---------|
| HarmonyOS 版本检测 | `HarmonyVer: detect via Build.DISPLAY=...` | 日志出现且 Version 非 NOT_HARMONY |
| 折叠屏检测 | `FoldDet: isFoldable: ...` | 日志出现（FIN-AL60 应判定为 false，ratio 低） |
| Step 3 overall SP | `HwStepStore: markCompleted(huawei_step3_battery_overall_done)` | 3 子步骤都成功时出现 |
| Step 1 Activity | `HwPermReqAct: onCreate: requestPermissions(...)` | Android 13+ 设备出现 |
| Step 9 300ms 延时 | `[Step9/10]` 日志间隔 ≥ 300ms | 手势 pattern 序列时长合理 |
| Step 6 列表坐标 | `HwStepLogger.probe 6 list-coord-fallback tap ...` | 搜索框失败时出现 |

- [ ] **Step 6: 若全部通过，标记本 plan 完成**

```bash
echo "✅ 2026-04-17-huawei-honor-p2-gap-closure.md 真机验证通过 @ $(date -Iseconds)" \
  >> /home/code/php/project/full-package/update-replica/docs/cache/CACHE_yw5xud.md
```

- [ ] **Step 7: 若某项失败，回到对应 Task 查 logcat 并修复**

不要盲目加 retry 次数或放宽断言。先 `adb shell uiautomator dump` 抓当前 UI 结构（用 `scripts/adb-ui-dump.sh`），定位 vendor 行为与 replica 的精确偏差后再改。

---

## Self-Review

### 1. Spec 覆盖度

对照 `docs/华为荣耀权限获取机制分析.md`：

| 文档 section | 覆盖 task |
|-------------|----------|
| §1 运行时危险权限 | Task 4（Step 1 Activity 启动） |
| §2 电池白名单 | Plan 1 Task 5 已完成 |
| §3 电池深度配置 | Plan 1 Task 6 + 本 plan Task 3（overall SP） |
| §4 自启动三开关 | Plan 1 Task 2 + Plan 2 Task 4-5 已完成 |
| §5 通知监听权 | T10/T16 已完成 |
| §6 悬浮窗 | Plan 2 Task 6-7 + 本 plan Task 6（列表页坐标 fallback） |
| §7 关闭通知渠道 | T13/H8-H9 已完成 |
| §8 所有文件访问 | T14 已完成 |
| §9 最近任务清理 + 锁定 | T15 + H10-H11 已完成 + 本 plan Task 5（300ms 延时） |
| §10 荣耀图库 | T17 `detectAndClickHonorPermissionDialog` m212161a8 已覆盖（含图库分支） |
| §五 HarmonyOS 检测 | 本 plan Task 1 ✓ |
| §六 折叠屏适配 | 本 plan Task 2 ✓ |
| §七 加密字符串 | 本 plan Task 1 反射路径含加密类名明文（不再 XOR） |
| §八 SP 完整表 | Plan 1 Task 4 + 本 plan Task 3（STEP3_OVERALL） |

**所有 spec section 都有 task 对应 ✓。**

### 2. Placeholder 扫描

- [x] 无 "TBD" / "TODO: fill in"
- [x] 无 "Write tests for the above"（所有测试代码已展开）
- [x] 无 "similar to Task N"（每 task 完整独立）
- [x] 所有 code block 给了完整实现，不是伪代码
- [x] 所有 `./gradlew` 命令给了预期输出
- [x] 所有 Intent/ComponentName/文本都是具体值，不是占位符

### 3. 类型一致性

- [x] `HarmonyVersionDetector.Version` 枚举 5 个成员（HARMONY_OS_4 / _3 / _2 / _UNKNOWN / NOT_HARMONY），property `isHarmony: Boolean` + `displayName: String`，前后一致
- [x] `FoldableDeviceDetector` 全 object（无 class 实例化），`isFoldable(context)` / `isAspectRatioFoldable(w, h)` / `isModelFoldable(model)` / `activateLeftPanel(service)` 方法签名前后一致
- [x] `HuaweiPermissionRequestActivity` companion `launchIntent(Context): Intent` + `requiredPermissions(targetSdk: Int): List<String>` 签名前后一致
- [x] `HuaweiStepCompletionStore.Keys.STEP3_OVERALL` 字符串值 `"huawei_step3_battery_overall_done"` 在测试和实现中一致
- [x] `getOverlayListFallbackPoint(Int, Int): Pair<Float, Float>` 签名一致

### 4. 硬约束符合

- [x] 所有步骤仅用 `./gradlew compileDebugKotlin`，不用 `./gradlew test` / `assembleDebug`（Task 7 真机验证除外，那是最终集成步骤）
- [x] 不提 git commit
- [x] 每 task 先写 RED 测试 → GREEN 最小实现 → compileDebugKotlin 验证

---

## 执行建议

**推荐：Subagent-Driven** — 每 task 派发 fresh `opus 4.6` subagent，避免单一上下文污染：
- Task 1 / Task 2 独立（新建文件 + 独立测试） → 可**并行** 2 个 subagent
- Task 3 修改 HuaweiStepCompletionStore + HuaweiSteps 中 Step 3 末尾 → 依赖 Task 1/2 不冲突的区域，可独立
- Task 4 新建 Activity + 修改 HuaweiSteps 中 Step 1 → 可与 Task 3 并行（修改区域不重叠，Step 1 vs Step 3 末尾）
- Task 5 修改 HuaweiSteps 中 Step 9 → 可与 Task 3/4 并行
- Task 6 修改 HuaweiSteps companion + Step 6 列表分支 → 与 Task 3/4/5 串行（companion 编辑可能冲突）
- Task 7 真机集成验证 → 必须最后串行

理想派发时序：
```
并行 1: Task 1 + Task 2 + Task 4(HuaweiPermissionRequestActivity.kt + manifest + 测试)
并行 2: Task 3 + Task 5 + Task 4(HuaweiSteps.kt Step 1 集成)
串行:   Task 6 → Task 7
```

所有 subagent 派发前告知：**只 compileDebugKotlin，不跑 test/build**；修改 HuaweiSteps.kt 前先 Read 确认当前行号（文件已 3979 行，可能已有 ADAPT 注释，避免重复插入）。

---

## 关键文件路径速查

- `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HarmonyVersionDetector.kt`（新建，Task 1）
- `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/FoldableDeviceDetector.kt`（新建，Task 2）
- `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiPermissionRequestActivity.kt`（新建，Task 4）
- `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiStepCompletionStore.kt`（改，Task 3）
- `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiSteps.kt`（改，Task 2/3/4/5/6）
- `app/src/main/AndroidManifest.xml`（改，Task 4 注册 Activity）
- `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/HarmonyVersionDetectorTest.kt`（新建）
- `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/FoldableDeviceDetectorTest.kt`（新建）
- `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiPermissionRequestActivityTest.kt`（新建）
- `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiStepCompletionStoreTest.kt`（改）
- `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiStepsTest.kt`（改，Task 6 追加 3 用例）

---

## 与 Plan 1 / Plan 2 关系

| Plan | 范围 | 本 plan 依赖 |
|------|------|------------|
| `2026-04-17-huawei-vendor-gap-analysis.md` | P0/P1 维度（Switch/BFS/AllowKeys/SP5-6/电池词） | 已完成 H1-H12 — 本 plan Task 3 依赖 STEP3_* 已存在 |
| `2026-04-17-huawei-real-device-hardening.md` | 真机 fallback（Battery/Startup/Overlay/Notif/AppCard） | 已完成 H1-H12 — 本 plan Task 6 依赖 `OverlayListDetector` / `GestureTapHelper` |

本 plan 覆盖 Plan 1/2 显式标记为 P2 后续 + vendor 文档审查发现的 3 处深度 gap（battery_completed 汇总 / perm-req Activity / performGlobalAction 300ms 时序）。
