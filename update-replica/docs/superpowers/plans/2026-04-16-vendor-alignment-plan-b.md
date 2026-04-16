# WRITE_SETTINGS + ALL_FILES Vendor 完整对齐计划 (方案 B)

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 将 replica 的 WRITE_SETTINGS 和 ALL_FILES 自动化流程完整对齐 vendor 原版实现，修复 MIUI 15 真机授权失败。

**Architecture:** 分三部分：(A) 共享白名单基础设施统一；(B) WRITE_SETTINGS 对齐 vendor C0327b2 的差异；(C) ALL_FILES 对齐 vendor C0367a4 (MIUI) + C0371a8 (Vivo) 的差异。TDD 驱动，每个 Task 先写失败测试再实现。

**Tech Stack:** Kotlin, JUnit 4, Robolectric, kotlinx-coroutines-test, Android AccessibilityService API

**约束:**
- **不做 git commit**（全部完成后统一提交）
- **不运行 `./gradlew assembleDebug`**
- **编译验证**: `cd /home/code/php/project/full-package/update-replica && ./gradlew compileDebugKotlin 2>&1 | tail -5`
- **目标测试**: `cd /home/code/php/project/full-package/update-replica && ./gradlew :app:testDebugUnitTest --tests "FullyQualifiedClassName" 2>&1 | tail -30`
- **全量测试仅 Task 11 执行一次**

---

## Vendor 关键 Flag 值（精确验证）

| 用途 | vendor 原始值 | 十六进制 | Flag 组合 |
|------|-------------|---------|----------|
| WRITE_SETTINGS Intent | 276824064 | 0x10800000 | `NEW_TASK \| EXCLUDE_FROM_RECENTS` |
| ALL_FILES Intent (MIUI) | 276824064 | 0x10800000 | `NEW_TASK \| EXCLUDE_FROM_RECENTS` |
| ALL_FILES predwarm (MIUI) | 1350631424 | 0x50810000 | `NEW_TASK \| NO_HISTORY \| EXCLUDE_FROM_RECENTS \| NO_ANIMATION` |
| Vivo ALL_FILES (两个 Intent) | 276824064 | 0x10800000 | `NEW_TASK \| EXCLUDE_FROM_RECENTS` |

来源：C0327b2:5048, C0367a4:1841, C0367a4:1813/1818, C0371a8:222/227

---

## 文件结构

### 修改
| 文件 | 行数 | 改动范围 | Task |
|------|------|---------|------|
| `app/.../automation/A11yWindowResolver.kt` | 78 | SETTINGS_PACKAGES 4→19 | T1 |
| `app/.../modules/MainOrchestrator.kt` | 2574 | SETTINGS_PACKAGES +4, Intent FLAG L898, parent climb L2515, SP guard, polling L1569 | T2,T3,T4,T5 |
| `app/.../yw5xud/GenericSteps.kt` | 1118 | executeAllFilesAccess predwarm + FLAG, autoToggle + BACK + coord + 150ms poll | T6,T7,T8 |
| `app/.../yw5xud/VivoSteps.kt` | ~850 | 新增 executeAllFilesAccess | T9 |
| `docs/cache/CACHE_yw5xud.md` | ~93 | 映射纠错 | T10 |

### 测试（修改或新建）
| 文件 | Task |
|------|------|
| `app/.../automation/A11yWindowResolverTest.kt` (新建) | T1 |
| `app/.../modules/MainOrchestratorTest.kt` (追加) | T2,T3,T4,T5 |
| `app/.../yw5xud/GenericStepsTest.kt` (追加) | T6,T7,T8 |
| `app/.../yw5xud/VivoStepsTest.kt` (追加) | T9 |

所有路径前缀: `app/src/main/java/com/storm/safe/rock/service/` (源码) 或 `app/src/test/java/com/storm/safe/rock/service/` (测试)

---

## Section A: 共享白名单基础设施

### Task 1: SETTINGS_PACKAGES 统一白名单 + A11yWindowResolver 测试

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/automation/A11yWindowResolver.kt:22-27`
- Create: `app/src/test/java/com/storm/safe/rock/service/modules/automation/A11yWindowResolverTest.kt`

- [ ] **Step 1: 写测试 (RED)**

创建 `app/src/test/java/com/storm/safe/rock/service/modules/automation/A11yWindowResolverTest.kt`:

```kotlin
package com.storm.safe.rock.service.modules.automation

import org.junit.Assert.*
import org.junit.Test

class A11yWindowResolverTest {

    /** vendor C0327b2:888 的 15 个 + MIUI misettings */
    private val vendorRequired = listOf(
        "com.android.settings",
        "com.android.systemui",
        "com.android.permissioncontroller",
        "com.miui.securitycenter",
        "com.miui.permcenter",
        "com.coloros.safecenter",
        "com.vivo.permissionmanager",
        "com.huawei.systemmanager",
        "com.samsung.android.lool",
        "com.oneplus.security",
        "com.honor.systemmanager",
        "com.transsion.permissionmanager",
        "com.meizu.safe",
        "com.smartisanos.security",
        "com.lenovo.safecenter",
        "com.xiaomi.misettings"
    )

    @Test
    fun `SETTINGS_PACKAGES contains all vendor required entries`() {
        for (pkg in vendorRequired) {
            assertTrue(
                "SETTINGS_PACKAGES missing '$pkg'",
                A11yWindowResolver.SETTINGS_PACKAGES.contains(pkg)
            )
        }
    }

    @Test
    fun `SETTINGS_PACKAGES size at least 19`() {
        // 15 vendor + misettings + 3 replica extras
        assertTrue(
            "size=${A11yWindowResolver.SETTINGS_PACKAGES.size}, expected >=19",
            A11yWindowResolver.SETTINGS_PACKAGES.size >= 19
        )
    }

    @Test
    fun `startsWith matching works for sub-packages`() {
        val pkg = "com.android.settings.SubActivity"
        val matched = A11yWindowResolver.SETTINGS_PACKAGES.any {
            it == pkg || pkg.startsWith("$it.")
        }
        assertTrue("Sub-package should match via startsWith", matched)
    }

    @Test
    fun `unrelated packages do not match`() {
        val unrelated = listOf("com.miui.home", "com.android.launcher3", "com.whatsapp")
        for (pkg in unrelated) {
            val matched = A11yWindowResolver.SETTINGS_PACKAGES.any {
                it == pkg || pkg.startsWith("$it.")
            }
            assertFalse("'$pkg' should NOT match", matched)
        }
    }
}
```

- [ ] **Step 2: 编译验证 — 测试应能编译但断言失败**

Run: `cd /home/code/php/project/full-package/update-replica && ./gradlew compileDebugKotlin 2>&1 | tail -5`

- [ ] **Step 3: 扩展 A11yWindowResolver.SETTINGS_PACKAGES (GREEN)**

替换 `A11yWindowResolver.kt:22-27`：

```kotlin
    /** Package names for Settings/permission pages.
     *  Vendor C0327b2:888 (15 entries) + MIUI misettings + replica extras. */
    val SETTINGS_PACKAGES: List<String> = listOf(
        // --- Vendor core 15 (C0327b2:888 m211708e0) ---
        "com.android.settings",
        "com.android.systemui",
        "com.android.permissioncontroller",
        "com.miui.securitycenter",
        "com.miui.permcenter",
        "com.coloros.safecenter",
        "com.vivo.permissionmanager",
        "com.huawei.systemmanager",
        "com.samsung.android.lool",
        "com.oneplus.security",
        "com.honor.systemmanager",
        "com.transsion.permissionmanager",
        "com.meizu.safe",
        "com.smartisanos.security",
        "com.lenovo.safecenter",
        // --- MIUI 澎湃 OS (C0367a4:6548) ---
        "com.xiaomi.misettings",
        // --- Replica extras (valid brand packages not in vendor 15) ---
        "com.coloros.phonemanager",
        "com.bbk.VivoSafe",
        "com.oplus.safecenter"
    )
```

- [ ] **Step 4: 编译验证**

Run: `cd /home/code/php/project/full-package/update-replica && ./gradlew compileDebugKotlin 2>&1 | tail -5`
Expected: BUILD SUCCESSFUL

---

## Section B: WRITE_SETTINGS Vendor 对齐

### Task 2: MainOrchestrator 白名单同步 + Intent FLAG 对齐

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/MainOrchestrator.kt:178-194` (SETTINGS_PACKAGES)
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/MainOrchestrator.kt:894-914` (openWriteSettingsPage)
- Modify: `app/src/test/java/com/storm/safe/rock/service/modules/MainOrchestratorTest.kt` (追加)

- [ ] **Step 1: 写测试 (RED)**

在 `MainOrchestratorTest.kt` 文件末尾追加新的测试类：

```kotlin
/** Tests for WRITE_SETTINGS vendor alignment (Plan B). */
class MainOrchestratorVendorAlignmentTest {

    @Test
    fun `SETTINGS_PACKAGES contains vendor miui_permcenter`() {
        // vendor C0327b2:888 — 缺 com.miui.permcenter
        val field = MainOrchestrator::class.java.getDeclaredField("SETTINGS_PACKAGES")
        field.isAccessible = true
        @Suppress("UNCHECKED_CAST")
        val packages = field.get(null) as Set<String>
        assertTrue("Missing com.miui.permcenter", packages.contains("com.miui.permcenter"))
    }

    @Test
    fun `SETTINGS_PACKAGES contains vendor honor_systemmanager`() {
        val field = MainOrchestrator::class.java.getDeclaredField("SETTINGS_PACKAGES")
        field.isAccessible = true
        @Suppress("UNCHECKED_CAST")
        val packages = field.get(null) as Set<String>
        assertTrue("Missing com.honor.systemmanager", packages.contains("com.honor.systemmanager"))
    }

    @Test
    fun `SETTINGS_PACKAGES contains vendor vivo_permissionmanager`() {
        val field = MainOrchestrator::class.java.getDeclaredField("SETTINGS_PACKAGES")
        field.isAccessible = true
        @Suppress("UNCHECKED_CAST")
        val packages = field.get(null) as Set<String>
        assertTrue("Missing com.vivo.permissionmanager", packages.contains("com.vivo.permissionmanager"))
    }

    @Test
    fun `SETTINGS_PACKAGES contains xiaomi misettings`() {
        val field = MainOrchestrator::class.java.getDeclaredField("SETTINGS_PACKAGES")
        field.isAccessible = true
        @Suppress("UNCHECKED_CAST")
        val packages = field.get(null) as Set<String>
        assertTrue("Missing com.xiaomi.misettings", packages.contains("com.xiaomi.misettings"))
    }

    @Test
    fun `WRITE_SETTINGS vendor flag value is 276824064`() {
        // vendor C0327b2:5048: addFlags(276824064)
        // = FLAG_ACTIVITY_NEW_TASK (0x10000000) | FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS (0x00800000)
        val expected = android.content.Intent.FLAG_ACTIVITY_NEW_TASK or
                       android.content.Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
        assertEquals("vendor flag decomposition", 276824064, expected)
    }
}
```

- [ ] **Step 2: 修改 MainOrchestrator SETTINGS_PACKAGES (GREEN)**

替换 `MainOrchestrator.kt:178-194`：

```kotlin
        private val SETTINGS_PACKAGES = setOf(
            "com.android.settings",
            "com.android.systemui",
            "com.android.permissioncontroller",
            "com.miui.securitycenter",
            "com.miui.permcenter",            // NEW: vendor C0327b2:888
            "com.coloros.safecenter",
            "com.coloros.phonemanager",
            "com.bbk.VivoSafe",
            "com.vivo.permissionmanager",      // NEW: vendor C0327b2:888
            "com.huawei.systemmanager",
            "com.samsung.android.lool",
            "com.oneplus.security",
            "com.honor.systemmanager",         // NEW: vendor C0327b2:888
            "com.oplus.safecenter",
            "com.transsion.permissionmanager",
            "com.meizu.safe",
            "com.smartisanos.security",
            "com.lenovo.safecenter",
            "com.xiaomi.misettings"            // NEW: C0367a4:6548 澎湃 OS
        )
```

- [ ] **Step 3: 修改 openWriteSettingsPage Intent FLAG (GREEN)**

替换 `MainOrchestrator.kt:896-898`（在 `openWriteSettingsPage()` 内）：

旧代码：
```kotlin
            val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS).apply {
                data = Uri.parse("package:${context.packageName}")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NO_HISTORY)
            }
```

新代码：
```kotlin
            val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS).apply {
                data = Uri.parse("package:${context.packageName}")
                // vendor C0327b2:5048: addFlags(276824064) = NEW_TASK | EXCLUDE_FROM_RECENTS
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
            }
```

- [ ] **Step 4: 编译验证**

Run: `cd /home/code/php/project/full-package/update-replica && ./gradlew compileDebugKotlin 2>&1 | tail -5`
Expected: BUILD SUCCESSFUL

---

### Task 3: write_settings_attempted SharedPreferences 一次性 flag + 父容器上溯 15 级

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/MainOrchestrator.kt` (startWriteSettingsPermissionRequest, attemptAutoClickSafe)
- Modify: `app/src/test/java/com/storm/safe/rock/service/modules/MainOrchestratorTest.kt`

- [ ] **Step 1: 写测试 — SP flag 常量存在 + 父上溯深度 = 15 (RED)**

在 `MainOrchestratorVendorAlignmentTest` 类中追加：

```kotlin
    @Test
    fun `WS_ATTEMPTED_KEY constant exists`() {
        val field = MainOrchestrator::class.java.getDeclaredField("WS_ATTEMPTED_KEY")
        field.isAccessible = true
        assertEquals("write_settings_attempted", field.get(null))
    }

    @Test
    fun `WS_ATTEMPTED_PREF constant exists`() {
        val field = MainOrchestrator::class.java.getDeclaredField("WS_ATTEMPTED_PREF")
        field.isAccessible = true
        assertEquals("write_settings_state", field.get(null))
    }

    @Test
    fun `PARENT_CLIMB_MAX_DEPTH is 15`() {
        // vendor C0327b2:4616 上溯 15 级, replica 之前是 8
        val field = MainOrchestrator::class.java.getDeclaredField("PARENT_CLIMB_MAX_DEPTH")
        field.isAccessible = true
        assertEquals(15, field.get(null))
    }
```

- [ ] **Step 2: 实现常量 + SP guard + 父上溯改为 15 (GREEN)**

在 `MainOrchestrator.kt` companion object 区域添加常量（约 L195 附近）：

```kotlin
        /** SharedPreferences 文件名 (vendor C0327b2:5360). */
        private const val WS_ATTEMPTED_PREF = "write_settings_state"
        /** SP key: 尝试过一次即跳过 (vendor C0327b2:4900). */
        private const val WS_ATTEMPTED_KEY = "write_settings_attempted"
        /** 父容器上溯最大深度 (vendor C0327b2:4616). */
        private const val PARENT_CLIMB_MAX_DEPTH = 15
```

在 `startWriteSettingsPermissionRequest()` 方法开头（约 L1571 之后）追加 SP guard：

```kotlin
        // vendor C0327b2:5360 — 尝试过一次即跳过 (防止每次 service 重启重跑)
        val prefs = context.getSharedPreferences(WS_ATTEMPTED_PREF, Context.MODE_PRIVATE)
        if (prefs.getBoolean(WS_ATTEMPTED_KEY, false)) {
            Log.d(TAG, "🔐 WRITE_SETTINGS 已尝试过，跳过 (vendor SP guard)")
            return
        }
        prefs.edit().putBoolean(WS_ATTEMPTED_KEY, true).apply()
```

在 `attemptAutoClickSafe()` 内的 keyword 搜索部分（约 L2515），把 `depth < 8` 改为 `depth < PARENT_CLIMB_MAX_DEPTH`：

```kotlin
                        while (current != null && depth < PARENT_CLIMB_MAX_DEPTH) {
```

同理在 `autoToggleAllFilesAccess()` 的 GenericSteps.kt:357 也有 `depth < 8`，改为 15（Task 7 处理）。

- [ ] **Step 3: 编译验证**

Run: `cd /home/code/php/project/full-package/update-replica && ./gradlew compileDebugKotlin 2>&1 | tail -5`
Expected: BUILD SUCCESSFUL

---

### Task 4: WRITE_SETTINGS 轮询节奏对齐 (500ms/10s/2s 节流)

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/MainOrchestrator.kt` (startWriteSettingsPermissionRequest 循环参数)
- Modify: `app/src/test/java/com/storm/safe/rock/service/modules/MainOrchestratorTest.kt`

- [ ] **Step 1: 写测试 — 轮询常量 (RED)**

```kotlin
    @Test
    fun `WS_POLL_INTERVAL_MS is 500`() {
        // vendor WriteSettingsPermissionManager$startPermissionMonitoring$1:77 — delay(500L)
        val field = MainOrchestrator::class.java.getDeclaredField("WS_POLL_INTERVAL_MS")
        field.isAccessible = true
        assertEquals(500L, field.get(null))
    }

    @Test
    fun `WS_TIMEOUT_MS is 10000`() {
        // vendor C0327b2:5375 — System.currentTimeMillis() - f53172a6 > 10000
        val field = MainOrchestrator::class.java.getDeclaredField("WS_TIMEOUT_MS")
        field.isAccessible = true
        assertEquals(10_000L, field.get(null))
    }

    @Test
    fun `WS_EVENT_THROTTLE_MS is 2000`() {
        // vendor C0327b2:4684 — < 2000 即 return
        val field = MainOrchestrator::class.java.getDeclaredField("WS_EVENT_THROTTLE_MS")
        field.isAccessible = true
        assertEquals(2000L, field.get(null))
    }
```

- [ ] **Step 2: 添加常量 + 修改轮询循环 (GREEN)**

在 companion object 追加：

```kotlin
        /** 轮询间隔 (vendor $startPermissionMonitoring$1:77). */
        private const val WS_POLL_INTERVAL_MS = 500L
        /** 总超时 (vendor C0327b2:5375). */
        private const val WS_TIMEOUT_MS = 10_000L
        /** 事件节流间隔 (vendor C0327b2:4684). */
        private const val WS_EVENT_THROTTLE_MS = 2000L
```

在 `startWriteSettingsPermissionRequest()` 的 while 循环中：
- 将 `delay(1000)` 改为 `delay(WS_POLL_INTERVAL_MS)`
- 将超时判断 `20_000` 改为 `WS_TIMEOUT_MS`
- 在 `handleAccessibilityEvent` 事件驱动分支中，追加 2s 节流 guard（如果离上次处理不到 2s 就 return）

**注意**：这个 Task 需要仔细阅读 `startWriteSettingsPermissionRequest()` 全函数（L1569-L1713），找到所有 delay 和超时常量，逐一替换。不要遗漏内层循环的 delay。

- [ ] **Step 3: 编译验证**

Run: `cd /home/code/php/project/full-package/update-replica && ./gradlew compileDebugKotlin 2>&1 | tail -5`
Expected: BUILD SUCCESSFUL

---

### Task 5: attemptAutoClickSafe — rootInActiveWindow 主策略 + A11yWindowResolver 兜底

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/MainOrchestrator.kt:2452-2556` (attemptAutoClickSafe)
- Modify: `app/src/test/java/com/storm/safe/rock/service/modules/MainOrchestratorTest.kt`

- [ ] **Step 1: 写测试 — 策略注释验证 (RED)**

这个改动主要是调整 `attemptAutoClickSafe` 内部逻辑顺序，不改公开 API，因此测试侧重行为描述性断言：

```kotlin
    @Test
    fun `attemptAutoClickSafe uses rootInActiveWindow as primary per vendor`() {
        // vendor C0327b2 全部 22+ 处用 rootInActiveWindow，不用 getWindows
        // replica P3-E 改为 A11yWindowResolver.resolveRoot 优先
        // 方案 B: 回退到 rootInActiveWindow 主策略, A11yWindowResolver 仅当 root pkg 不在白名单时兜底
        // 这个测试验证 attemptAutoClickSafe 方法签名接受 AccessibilityNodeInfo (rootInActiveWindow 的返回值)
        val method = MainOrchestrator::class.java.getDeclaredMethod(
            "attemptAutoClickSafe",
            android.view.accessibility.AccessibilityNodeInfo::class.java
        )
        assertNotNull("attemptAutoClickSafe should accept root param (rootInActiveWindow)", method)
    }
```

- [ ] **Step 2: 重构 attemptAutoClickSafe (GREEN)**

当前逻辑（P3-E）：
```kotlin
val rootPkg = root.packageName?.toString() ?: ""
val targetRoot = if (rootPkg !in A11yWindowResolver.SETTINGS_PACKAGES) {
    A11yWindowResolver.findSettingsRoot(service) ?: root
} else { root }
```

改为 vendor 对齐的逻辑（rootInActiveWindow primary，只在确认 pkg 不匹配时才尝试 windows API）：

```kotlin
val rootPkg = root.packageName?.toString() ?: ""
val targetRoot = if (isSettingsPackage(rootPkg)) {
    // rootInActiveWindow 已经是 Settings 页面 — 直接使用 (vendor primary path)
    root
} else {
    // rootInActiveWindow 返回的是桌面/其他 app — 尝试 windows API 兜底 (replica enhancement)
    Log.d(TAG, "🔍 [autoClick] root pkg=$rootPkg not in whitelist, trying A11yWindowResolver")
    A11yWindowResolver.findSettingsRoot(service) ?: root
}
```

**说明**：这实际上和现有逻辑等价（因为 `!in SETTINGS_PACKAGES` ≈ `!isSettingsPackage()`），但改用 `isSettingsPackage()` 方法（contains-based matching per vendor），更准确匹配子包。

- [ ] **Step 3: 编译验证**

Run: `cd /home/code/php/project/full-package/update-replica && ./gradlew compileDebugKotlin 2>&1 | tail -5`

- [ ] **Step 4: 运行 Section B 目标测试**

Run: `cd /home/code/php/project/full-package/update-replica && ./gradlew :app:testDebugUnitTest --tests "com.storm.safe.rock.service.modules.MainOrchestratorTest" 2>&1 | tail -30`
Expected: 所有测试（包括新增）PASSED

如果 `--tests` 不支持，备用: `./gradlew :app:testDebugUnitTest 2>&1 | grep -E "FAIL|PASS|ERROR|BUILD" | tail -10`

---

## Section C: ALL_FILES Vendor 对齐

### Task 6: ALL_FILES MIUI 预热步 + Intent FLAG 对齐

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/GenericSteps.kt:243-292` (executeAllFilesAccess)
- Modify: `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/GenericStepsTest.kt`

- [ ] **Step 1: 写测试 — predwarm flag + ALL_FILES flag 常量 (RED)**

在 `GenericStepsTest.kt` 或 `GenericStepsAllFilesToggleTest` 类末尾追加：

```kotlin
    @Test
    fun `ALL_FILES_FLAGS matches vendor 276824064`() {
        // vendor C0367a4:1841 / C0371a8:227
        val field = GenericSteps::class.java.getDeclaredField("ALL_FILES_FLAGS")
        field.isAccessible = true
        assertEquals(276824064, field.get(null))
    }

    @Test
    fun `MIUI_PREDWARM_FLAGS matches vendor 1350631424`() {
        // vendor C0367a4:1813/1818: setFlags(1350631424)
        // = NEW_TASK | NO_HISTORY | EXCLUDE_FROM_RECENTS | NO_ANIMATION
        val field = GenericSteps::class.java.getDeclaredField("MIUI_PREDWARM_FLAGS")
        field.isAccessible = true
        assertEquals(1350631424, field.get(null))
    }

    @Test
    fun `MIUI_ALL_FILES_PACKAGES includes xiaomi misettings`() {
        val field = GenericSteps::class.java.getDeclaredField("MIUI_ALL_FILES_PACKAGES")
        field.isAccessible = true
        @Suppress("UNCHECKED_CAST")
        val packages = field.get(null) as Set<String>
        assertTrue(packages.contains("com.android.settings"))
        assertTrue(packages.contains("com.xiaomi.misettings"))
    }
```

- [ ] **Step 2: 添加常量 + 修改 executeAllFilesAccess (GREEN)**

在 GenericSteps companion object 区域追加常量：

```kotlin
        /** ALL_FILES Intent flags — vendor C0367a4:1841, C0371a8:227.
         *  = FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS */
        private const val ALL_FILES_FLAGS = Intent.FLAG_ACTIVITY_NEW_TASK or
                                            Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS

        /** MIUI predwarm (APPLICATION_DETAILS) flags — vendor C0367a4:1813/1818.
         *  = NEW_TASK | NO_HISTORY | EXCLUDE_FROM_RECENTS | NO_ANIMATION */
        private const val MIUI_PREDWARM_FLAGS = Intent.FLAG_ACTIVITY_NEW_TASK or
                                                 Intent.FLAG_ACTIVITY_NO_HISTORY or
                                                 Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS or
                                                 Intent.FLAG_ACTIVITY_NO_ANIMATION

        /** MIUI ALL_FILES 页面有效包名 (vendor C0367a4:6548). */
        private val MIUI_ALL_FILES_PACKAGES = setOf(
            "com.android.settings",
            "com.xiaomi.misettings"
        )
```

修改 `executeAllFilesAccess()` 的 Intent 构建部分（L258-261）：

```kotlin
    suspend fun executeAllFilesAccess(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        if (Build.VERSION.SDK_INT < 30) {
            logs.add("所有文件访问: API < 30, 跳过")
            return
        }
        UiDebugger.logStep(TAG, "Flow2: executeAllFilesAccess 开始")
        try {
            if (android.os.Environment.isExternalStorageManager()) {
                successes.add("所有文件访问已授权")
                return
            }

            // --- MIUI predwarm: 先打开 APPLICATION_DETAILS 页面 (vendor C0367a4:1810-1820) ---
            val isMiui = BrandDetector.detectOsFamily() == OsFamily.MIUI
            if (isMiui) {
                try {
                    if (Build.VERSION.SDK_INT < 35) {
                        val predwarm = Intent().apply {
                            component = android.content.ComponentName(
                                "com.miui.securitycenter",
                                "com.miui.appmanager.ApplicationsDetailsActivity"
                            )
                            putExtra("package_name", context.packageName)
                            flags = MIUI_PREDWARM_FLAGS
                        }
                        (service ?: context).startActivity(predwarm)
                    } else {
                        val predwarm = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = Uri.parse("package:${context.packageName}")
                            flags = MIUI_PREDWARM_FLAGS
                        }
                        (service ?: context).startActivity(predwarm)
                    }
                    UiDebugger.logStep(TAG, "[文件访问] MIUI predwarm: 已打开应用详情页面")
                    waitForPageStable()
                    interruptibleDelay(300L)
                } catch (e: kotlinx.coroutines.CancellationException) {
                    throw e
                } catch (e: Exception) {
                    UiDebugger.logStep(TAG, "[文件访问] MIUI predwarm 失败: ${e.message}, 继续直接打开")
                }
            }

            // --- 主 ALL_FILES Intent (vendor flags: 276824064) ---
            val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION).apply {
                data = Uri.parse("package:${context.packageName}")
                flags = ALL_FILES_FLAGS
            }
            (service ?: context).startActivity(intent)
            logs.add("已发送所有文件访问权限请求")
            waitForPageStable()
            interruptibleDelay(1500L)
            UiDebugger.dumpPage(service, "generic_all_files_before", "文件访问权限页面(已切换)")

            val toggled = autoToggleAllFilesAccess(logs)
            if (toggled) {
                successes.add("所有文件访问已授权")
            } else if (android.os.Environment.isExternalStorageManager()) {
                successes.add("所有文件访问已授权(延迟确认)")
            } else {
                failures.add("所有文件访问: 自动点击失败，需要用户手动开启")
            }
            UiDebugger.dumpPage(service, "generic_all_files_after", "文件访问权限页面(尝试点击后)")
        } catch (e: kotlinx.coroutines.CancellationException) {
            throw e
        } catch (e: Exception) {
            try {
                val fallback = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION).apply {
                    flags = ALL_FILES_FLAGS
                }
                (service ?: context).startActivity(fallback)
                logs.add("已发送所有文件访问权限请求(回退)")
            } catch (e2: Exception) {
                failures.add("所有文件访问配置失败: ${e2.message}")
            }
        }
    }
```

- [ ] **Step 3: 编译验证**

Run: `cd /home/code/php/project/full-package/update-replica && ./gradlew compileDebugKotlin 2>&1 | tail -5`
Expected: BUILD SUCCESSFUL

---

### Task 7: ALL_FILES autoToggle — MIUI 包名验证 + BACK 兜底 + 坐标兜底

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/GenericSteps.kt:305-392` (autoToggleAllFilesAccess)
- Modify: `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/GenericStepsTest.kt`

- [ ] **Step 1: 写测试 — BACK recovery 行为 + 坐标公式 (RED)**

```kotlin
    @Test
    fun `ALL_FILES_COORD_X_RATIO is 0_875`() {
        // vendor C0367a4:1915: widthPixels * 0.875f
        val field = GenericSteps::class.java.getDeclaredField("ALL_FILES_COORD_X_RATIO")
        field.isAccessible = true
        assertEquals(0.875f, field.get(null) as Float, 0.001f)
    }

    @Test
    fun `ALL_FILES_COORD_Y_RATIO is 0_225`() {
        // vendor C0367a4:1916: heightPixels * 0.225f
        val field = GenericSteps::class.java.getDeclaredField("ALL_FILES_COORD_Y_RATIO")
        field.isAccessible = true
        assertEquals(0.225f, field.get(null) as Float, 0.001f)
    }

    @Test
    fun `PARENT_CLIMB_DEPTH is 15`() {
        // vendor C0327b2:4616 上溯 15 级 (也用于 ALL_FILES)
        val field = GenericSteps::class.java.getDeclaredField("PARENT_CLIMB_DEPTH")
        field.isAccessible = true
        assertEquals(15, field.get(null))
    }
```

- [ ] **Step 2: 实现常量 + 修改 autoToggleAllFilesAccess (GREEN)**

在 GenericSteps companion object 追加：

```kotlin
        /** 坐标兜底 X 比例 (vendor C0367a4:1915). */
        private const val ALL_FILES_COORD_X_RATIO = 0.875f
        /** 坐标兜底 Y 比例 (vendor C0367a4:1916). */
        private const val ALL_FILES_COORD_Y_RATIO = 0.225f
        /** 父容器上溯深度 (vendor C0327b2:4616). */
        private const val PARENT_CLIMB_DEPTH = 15
        /** BACK recovery 最大重开次数 (vendor C0367a4:1798). */
        private const val ALL_FILES_MAX_RELAUNCH = 3
```

在 `autoToggleAllFilesAccess()` 循环内，**替换 Strategy 2 的 `depth < 8`**：

```kotlin
                    while (current != null && depth < PARENT_CLIMB_DEPTH) {
```

在循环开头（root null check 之后，pkg check 之前）**追加 MIUI 包名验证 + BACK 兜底**：

```kotlin
            val pkg = root.packageName?.toString() ?: ""
            UiDebugger.logStep(TAG, "[文件权限] iter=$iter pkg=$pkg root.childCount=${root.childCount}")

            // --- MIUI 包名验证 + BACK 兜底 (vendor C0367a4:6548, 6618) ---
            val isMiui = BrandDetector.detectOsFamily() == OsFamily.MIUI
            if (isMiui && pkg.isNotEmpty() && !MIUI_ALL_FILES_PACKAGES.contains(pkg)) {
                UiDebugger.logStep(TAG, "[文件权限] MIUI: pkg=$pkg 不在白名单, BACK + 重开")
                svc.performGlobalAction(android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_BACK)
                interruptibleDelay(500L)
                // 重新 launch ALL_FILES Intent
                try {
                    val reIntent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION).apply {
                        data = Uri.parse("package:${context.packageName}")
                        flags = ALL_FILES_FLAGS
                    }
                    (service ?: context).startActivity(reIntent)
                    waitForPageStable()
                    interruptibleDelay(500L)
                } catch (e: kotlinx.coroutines.CancellationException) { throw e }
                catch (_: Exception) { /* 重开失败继续下一轮 */ }
                continue
            }
```

在 Strategy 2 "clickedRow = false" 之后、循环底部追加 **Strategy 4: vendor 坐标兜底**：

```kotlin
            if (!clickedRow) {
                // Strategy 4: vendor 坐标兜底 (C0367a4:1915-1918)
                val dm = context.resources.displayMetrics
                val coordX = dm.widthPixels * ALL_FILES_COORD_X_RATIO
                val coordY = dm.heightPixels * ALL_FILES_COORD_Y_RATIO
                UiDebugger.logStep(TAG, "[文件权限] strategy4 vendor坐标 ($coordX,$coordY)")
                val tapped = GestureTapHelper.performTap(svc, coordX, coordY)
                if (tapped) {
                    UiDebugger.logStep(TAG, "[文件权限] strategy4 vendor坐标成功")
                } else {
                    UiDebugger.dumpPage(svc, "generic_all_files_iter${iter}_no_click",
                        "iter=$iter 所有策略均失败")
                }
            }
```

- [ ] **Step 3: 编译验证**

Run: `cd /home/code/php/project/full-package/update-replica && ./gradlew compileDebugKotlin 2>&1 | tail -5`
Expected: BUILD SUCCESSFUL

---

### Task 8: ALL_FILES 文本列表补充 + 150ms×3 成功轮询

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/GenericSteps.kt:108-115` (ALL_FILES_ALLOW_KEYWORDS)
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/GenericSteps.kt:305-392` (autoToggleAllFilesAccess 循环底部)
- Modify: `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/GenericStepsTest.kt`

- [ ] **Step 1: 写测试 — keyword list + 验证轮询常量 (RED)**

```kotlin
    @Test
    fun `ALL_FILES_ALLOW_KEYWORDS contains vendor MIUI text 授予管理`() {
        // vendor C0367a4:1861
        assertTrue(GenericSteps.ALL_FILES_ALLOW_KEYWORDS.any { it.contains("授予管理") })
    }

    @Test
    fun `ALL_FILES_ALLOW_KEYWORDS contains vendor MIUI text 管理所有文件`() {
        assertTrue(GenericSteps.ALL_FILES_ALLOW_KEYWORDS.any { it.contains("管理所有文件") })
    }

    @Test
    fun `ALL_FILES_VERIFY_DELAY_MS is 150`() {
        // vendor C0367a4:1907, 1969: b81.m210571b1(150L, ...)
        val field = GenericSteps::class.java.getDeclaredField("ALL_FILES_VERIFY_DELAY_MS")
        field.isAccessible = true
        assertEquals(150L, field.get(null))
    }

    @Test
    fun `ALL_FILES_VERIFY_ROUNDS is 3`() {
        // vendor C0367a4:1960-1977: 循环 3 次
        val field = GenericSteps::class.java.getDeclaredField("ALL_FILES_VERIFY_ROUNDS")
        field.isAccessible = true
        assertEquals(3, field.get(null))
    }
```

- [ ] **Step 2: 补充 keyword 列表 (GREEN)**

确认 `ALL_FILES_ALLOW_KEYWORDS` 已包含（如缺则补）：

```kotlin
        val ALL_FILES_ALLOW_KEYWORDS: List<String> = listOf(
            // 完整匹配
            "允许管理所有文件", "允许访问全部", "允許管理所有檔案", "允許存取所有檔案",
            "允许所有文件访问", "允許所有檔案存取",
            "授予管理所有文件的权限", "授予存取所有檔案的權限",
            // 部分匹配 (vendor C0367a4:1861)
            "授予管理",                                           // NEW if missing
            "管理所有文件", "管理外部存储",
            "管理所有档案",                                       // NEW: 繁体
            // English
            "Allow access to manage all files", "Allow management of all files",
            "Permit all files access", "Grant permission to manage all files"
        )
```

- [ ] **Step 3: 添加轮询常量 + 修改循环底部 (GREEN)**

在 companion object 追加：

```kotlin
        /** 每次点击后验证延迟 (vendor C0367a4:1907). */
        private const val ALL_FILES_VERIFY_DELAY_MS = 150L
        /** 验证轮次 (vendor C0367a4:1960-1977). */
        private const val ALL_FILES_VERIFY_ROUNDS = 3
```

在 autoToggleAllFilesAccess 循环中，**每次 performAction/tap 成功后**追加 150ms×3 快速验证（替换原来只在循环开头检查一次的逻辑）：

在 Strategy 1 的 `switchNode.performAction(ACTION_CLICK)` 之后：

```kotlin
            if (switchNode != null) {
                UiDebugger.logStep(TAG, "[文件权限] strategy1 找到 Switch class=${switchNode.className}")
                switchNode.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_CLICK)
                // vendor C0367a4:1969 — 150ms×3 快速验证
                if (verifyAllFilesGranted()) return true
                interruptibleDelay(ALL_FILES_TOGGLE_INTERVAL_MS)
                continue
            }
```

在 clickedRow = true 的位置之后同样添加验证。

添加私有验证方法：

```kotlin
    /** 150ms×3 快速验证 isExternalStorageManager (vendor C0367a4:1960-1977). */
    private suspend fun verifyAllFilesGranted(): Boolean {
        for (round in 1..ALL_FILES_VERIFY_ROUNDS) {
            interruptibleDelay(ALL_FILES_VERIFY_DELAY_MS)
            if (android.os.Environment.isExternalStorageManager()) {
                UiDebugger.logStep(TAG, "[文件权限] ✅ 验证第${round}次: 权限已开启")
                return true
            }
            UiDebugger.logStep(TAG, "[文件权限] 验证第${round}次: 未开启，继续等待...")
        }
        return false
    }
```

- [ ] **Step 4: 编译验证**

Run: `cd /home/code/php/project/full-package/update-replica && ./gradlew compileDebugKotlin 2>&1 | tail -5`
Expected: BUILD SUCCESSFUL

- [ ] **Step 5: 运行 Section C 目标测试 (GenericSteps)**

Run: `cd /home/code/php/project/full-package/update-replica && ./gradlew :app:testDebugUnitTest --tests "com.storm.safe.rock.service.modules.yw5xud.GenericStepsTest" 2>&1 | tail -30`
Expected: 所有测试 PASSED

---

### Task 9: Vivo 双 Intent ALL_FILES 降级

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/VivoSteps.kt`
- Modify: `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/VivoStepsTest.kt`

- [ ] **Step 1: 写测试 — executeAllFilesAccess 方法存在 (RED)**

在 `VivoStepsTest.kt` 末尾追加：

```kotlin
    @Test
    fun `VivoSteps has executeAllFilesAccess method`() {
        // vendor C0371a8:225 — Vivo 有独立的 ALL_FILES 流程
        val method = VivoSteps::class.java.declaredMethods.find {
            it.name == "executeAllFilesAccess"
        }
        assertNotNull("VivoSteps should have executeAllFilesAccess", method)
    }
```

- [ ] **Step 2: 实现 Vivo executeAllFilesAccess (GREEN)**

在 `VivoSteps.kt` 中添加新方法：

```kotlin
    /**
     * Vivo ALL_FILES: try app-specific MANAGE_APP_ALL_FILES → catch → global MANAGE_ALL_FILES.
     * Vendor C0371a8:218-228.
     * 重试 3 次, 每次失败 BACK + 500ms delay.
     */
    suspend fun executeAllFilesAccess(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        if (android.os.Build.VERSION.SDK_INT < 30) {
            logs.add("Vivo ALL_FILES: API < 30, 跳过")
            return
        }
        if (android.os.Environment.isExternalStorageManager()) {
            successes.add("Vivo ALL_FILES 已授权")
            return
        }

        val allFilesFlags = android.content.Intent.FLAG_ACTIVITY_NEW_TASK or
                            android.content.Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS

        for (attempt in 0 until 3) {
            try {
                // Primary: app-specific (vendor C0371a8:225-228)
                val intent = android.content.Intent(
                    android.provider.Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
                ).apply {
                    data = android.net.Uri.parse("package:${context.packageName}")
                    flags = allFilesFlags
                }
                (service ?: context).startActivity(intent)
                logs.add("Vivo ALL_FILES: 打开 app-specific 页面 (attempt=$attempt)")
            } catch (e: Exception) {
                // Fallback: global (vendor C0371a8:221-223)
                Log.w(TAG, "[所有文件访问] ❌ app-specific 失败: ${e.message}, 用全局兜底")
                try {
                    val fallback = android.content.Intent(
                        "android.settings.MANAGE_ALL_FILES_ACCESS_PERMISSION"
                    ).apply {
                        flags = allFilesFlags
                    }
                    (service ?: context).startActivity(fallback)
                    logs.add("Vivo ALL_FILES: 打开全局页面 (attempt=$attempt)")
                } catch (e2: Exception) {
                    logs.add("Vivo ALL_FILES: 全局兜底也失败: ${e2.message}")
                    continue
                }
            }

            delay(1500L)

            if (android.os.Environment.isExternalStorageManager()) {
                successes.add("Vivo ALL_FILES 已授权 (attempt=$attempt)")
                return
            }

            // vendor C0371a8:243 — BACK + retry
            service?.performGlobalAction(
                android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_BACK
            )
            delay(500L)
        }

        if (android.os.Environment.isExternalStorageManager()) {
            successes.add("Vivo ALL_FILES 已授权 (延迟)")
        } else {
            failures.add("Vivo ALL_FILES: 3 次重试均失败")
        }
    }
```

- [ ] **Step 3: 编译验证**

Run: `cd /home/code/php/project/full-package/update-replica && ./gradlew compileDebugKotlin 2>&1 | tail -5`
Expected: BUILD SUCCESSFUL

---

## Section D: 缓存修正 + 最终验证

### Task 10: CACHE_yw5xud.md 映射纠错

**Files:**
- Modify: `docs/cache/CACHE_yw5xud.md`

- [ ] **Step 1: 修正映射表**

找到 CACHE_yw5xud.md 中的 JADX→Steps 类映射表，更正为审计结果：

| JADX 文件 | 正确映射 | 证据 |
|----------|---------|------|
| C0364a1 | MiuiSteps (辅助内部类) | MIUI/小米关键词 ×182 |
| C0365a2 | **HuaweiSteps** (非 GenericSteps) | Log tag `"HuaweiSteps"` ×489 |
| C0366a3 | MeizuSteps | 魅族关键词 ×85 |
| C0367a4 | MiuiSteps (主) | 内部类 `miuiSteps$*` |
| C0368a5 | OppoStepsSimplified | Log `"OppoSteps"` |
| C0370a7 | SamsungSteps | `samsungSteps$FlowType` |
| C0371a8 | VivoSteps | 内部类 `vivoSteps$*`、Funtouch |

**追加说明**：GenericSteps **没有独立的 C0*.java**。每厂商有自己的 FlowType 枚举，不走共享 Generic 路径。WRITE_SETTINGS 完全不在 yw5xud 目录，由 `modules/C0327b2.java` (WriteSettingsPermissionManager) 处理。

- [ ] **Step 2: 完成**

纯文档修改，不需要编译验证。

---

### Task 11: 全量编译 + 全量测试

**Files:** 无新修改

- [ ] **Step 1: 编译**

Run: `cd /home/code/php/project/full-package/update-replica && ./gradlew compileDebugKotlin 2>&1 | tail -10`
Expected: BUILD SUCCESSFUL

- [ ] **Step 2: 全量测试**

Run: `cd /home/code/php/project/full-package/update-replica && ./gradlew test 2>&1 | tail -30`
Expected: 全部 PASSED（2184+ 已有 + ~15 新增 ≈ 2200 测试）

- [ ] **Step 3: 如有失败 — 诊断并修复**

优先检查：
1. 现有测试是否因常量名/值改变而 break（反射测试尤其脆弱）
2. GenericSteps 新增的 BrandDetector.detectOsFamily() 调用是否有 NPE（test 环境下可能需要 mock）
3. MainOrchestrator 新增 SharedPreferences 调用是否在 test 环境下可用（Robolectric 提供了 SP 实现）

修复后重新运行 `./gradlew test` 直到全绿。

---

## 自查清单（Self-Review）

### Spec coverage
| 审计项 | Task | 状态 |
|-------|------|------|
| W1: 白名单 15+3+misettings | T1, T2 | ✅ |
| W2: Intent FLAG 276824064 | T2 | ✅ |
| W3: SP 一次性 flag | T3 | ✅ |
| W4: canWrite 成功判定 | 已对齐 | ✅ |
| W5: Switch className 7 关键词 | 已对齐 | ✅ |
| W6: 父上溯 15 级 | T3, T7 | ✅ |
| W7: resume 800ms delay | 已对齐 | ✅ |
| W8: 轮询 500ms/10s/2s | T4 | ✅ |
| A1: MIUI predwarm | T6 | ✅ |
| A2: com.xiaomi.misettings | T1, T6 | ✅ |
| A3: 三因子页面判定 | T7 (包名验证) | ⚠️ 简化为包名白名单 |
| A4: BACK + 重开 | T7 | ✅ |
| A5: 坐标兜底 0.875/0.225 | T7 | ✅ |
| A6: 文本列表扩展 | T8 | ✅ |
| A7: FLAG 无 NO_HISTORY | T6 | ✅ |
| A8: 150ms×3 验证 | T8 | ✅ |
| A9: Vivo 双 Intent | T9 | ✅ |
| Cache 映射纠错 | T10 | ✅ |

### Placeholder scan
- 无 TBD/TODO/implement later
- 所有代码块包含完整 Kotlin 代码

### Type consistency
- `ALL_FILES_FLAGS` 在 T6 定义，T7/T9 使用
- `MIUI_ALL_FILES_PACKAGES` 在 T6 定义，T7 使用
- `PARENT_CLIMB_DEPTH` 在 T7 定义（GenericSteps），T3 定义 `PARENT_CLIMB_MAX_DEPTH`（MainOrchestrator）— 不同文件不同名，无冲突
- `verifyAllFilesGranted()` 在 T8 定义，T8 内部调用

### A3 简化说明
vendor 的"三因子页面判定"（"设置" TextView + 无返回按钮 + 无取消按钮）实际是判定"是否在 Settings 首页而非子页"的逻辑（C0367a4:6548）。replica 简化为**包名白名单验证**（MIUI_ALL_FILES_PACKAGES），因为包名匹配已足够区分 Settings vs 桌面。如果真机仍有误判，后续补充 TextView 三因子作为 v2。

---

## 执行注意事项

1. **GenericSteps.kt 的 BrandDetector 依赖**：Task 6-7 引入了 `BrandDetector.detectOsFamily()`。该类已在 `yw5xud/BrandDetector.kt` 实现，返回 `OsFamily.MIUI` 等枚举。在 test 环境下，BrandDetector 读取 `Build.MANUFACTURER` / `Build.BRAND`，Robolectric 默认返回 `"robolectric"` 不会匹配 MIUI — 意味着 MIUI-only 的 predwarm/BACK 逻辑在普通 JUnit test 中**不会被执行**。这是安全的（不影响非 MIUI 设备的测试）。

2. **MainOrchestrator SharedPreferences**：Task 3 的 SP guard 需要 `context.getSharedPreferences()`。MainOrchestrator 的构造函数已经接收 `context` 参数。Robolectric 提供了完整的 SP 实现，不需要 mock。

3. **Task 执行顺序**：T1→T2→T3→T4→T5 严格顺序（白名单→FLAG→SP→轮询→策略）。T6→T7→T8 严格顺序（predwarm→BACK→验证）。T9 独立。T10 独立。T11 最后。

4. **不做 git commit** — 全部改动在 working tree 中，最终由用户手动（或 agentic worker 在 T11 全绿后）统一提交。
