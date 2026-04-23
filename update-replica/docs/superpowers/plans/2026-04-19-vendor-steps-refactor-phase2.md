# 厂商自动化脚本重构 Phase 2 — VendorSteps 基类 + 全厂商 UiAutomation 迁移

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 基于 Phase 1（GKD selector + MiuiSteps 试点）成果，引入 VendorSteps 抽象基类（Template Method）+ Manager/Driver 分发模式，完成 Oppo/Huawei/Generic 三大厂商的 UiAutomation 迁移，消除 ~500 行重复代码。

**Architecture:** VendorSteps 抽象基类定义 `execute()` 契约（Strategy #12），各厂商各自实现；Yw5xudHandler 用 StepsFactory 替代 7 个 executeXxxSteps 方法（Manager/Driver #5）。所有厂商 Steps 构造器统一接收 `UiAutomation` 参数。Template Method (#14) 骨架推迟到 Phase 3（需先统一各厂商 step 结构）。

**为什么不用 Template Method：** 7 个厂商的 step 结构完全不同 — HuaweiSteps 有 9 步、OppoSteps 有 9 步（但名字不同）、MiuiSteps 有 5 个子方法、其余没有子步骤。强行提取公共骨架会导致每个子类写空 stub，Template Method 变成死代码。

**Tech Stack:** Kotlin, Android AccessibilityService, GKD selector (li.songe.selector), UiAutomation, JUnit 4

**约束:** 
- 每个 Task 不执行 git/test 命令，最终统一编译+测试+提交
- 在现有 worktree `refactor/gkd-selector-miui-pilot` 上继续工作
- JSON 规则引擎（Phase 3）不在本计划范围内

**Worktree:**
- 路径: `/home/code/php/project/full-package/.worktrees/gkd-selector-miui-pilot`
- 分支: `refactor/gkd-selector-miui-pilot`
- update-replica 子目录: `.worktrees/gkd-selector-miui-pilot/update-replica/`

```bash
WT="/home/code/php/project/full-package/.worktrees/gkd-selector-miui-pilot"
cd "$WT/update-replica"
```

---

## 当前状态（Phase 1 已完成）

| 文件 | 行数 | UiAutomation | 状态 |
|------|------|-------------|------|
| MiuiSteps.kt | 1,530 | ✅ 已迁移 | 完成 |
| HuaweiSteps.kt | 4,351 | ❌ 原生 API | 待迁移 |
| GenericSteps.kt | 1,319 | ❌ 原生 API | 待迁移 |
| OppoSteps.kt | 1,267 | ❌ UiNode API | 待迁移 |
| VivoSteps.kt | 278 | ❌ 无 helper | 仅加构造器 |
| SamsungSteps.kt | 133 | ❌ 无 helper | 仅加构造器 |
| MeizuSteps.kt | 120 | ❌ 无 helper | 仅加构造器 |
| UiAutomation.kt | 272 | — | 共享操作层 |
| A11yContext.kt | 197 | — | GKD 桥接 |

---

## 文件结构

### 新建文件

| 文件 | 职责 | 行数 |
|------|------|------|
| `.../yw5xud/VendorSteps.kt` | 抽象基类：Strategy 契约 (`execute()`) + 共享 `runStep()` | ~35 |
| `.../yw5xud/StepsFactory.kt` | Manager/Driver 工厂：Brand → VendorSteps 实例 | ~30 |
| `test/.../yw5xud/VendorStepsTest.kt` | 基类测试 | ~60 |
| `test/.../yw5xud/StepsFactoryTest.kt` | 工厂测试 | ~40 |

### 修改文件

| 文件 | 修改内容 | 增减 |
|------|---------|------|
| OppoSteps.kt | 继承 VendorSteps，删 14 个 helper，改用 `ui.xxx()` | -152 行 |
| HuaweiSteps.kt | 继承 VendorSteps，删 8 个 helper，改用 `ui.xxx()` | -129 行 |
| GenericSteps.kt | 继承 VendorSteps，删 4 个 helper，改用 `ui.xxx()` | -78 行 |
| VivoSteps.kt | 继承 VendorSteps，加 `ui` 构造器参数 | ~+3 行 |
| SamsungSteps.kt | 继承 VendorSteps，加 `ui` 构造器参数 | ~+3 行 |
| MeizuSteps.kt | 继承 VendorSteps，加 `ui` 构造器参数 | ~+3 行 |
| MiuiSteps.kt | 继承 VendorSteps（已有 `ui`，仅改继承声明） | ~+1 行 |
| Yw5xudHandler.kt | 用 StepsFactory 替代 7 个 executeXxxSteps | -48 行 |

---

## Task 1: VendorSteps 抽象基类

**Files:**
- Create: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/VendorSteps.kt`
- Create: `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/VendorStepsTest.kt`

- [ ] **Step 1: 编写 VendorSteps 测试**

```kotlin
package com.storm.safe.rock.service.modules.yw5xud

import android.content.Context
import com.storm.safe.rock.auto.a11y.UiAutomation
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class VendorStepsTest {

    private val context = mock(Context::class.java)
    private val ui = mock(UiAutomation::class.java)

    @Test
    fun `subclass execute is called`() = runTest {
        var called = false
        val steps = object : VendorSteps(null, context, ui) {
            override suspend fun execute(s: MutableList<String>, f: MutableList<String>, l: MutableList<String>) {
                called = true
                s.add("ok")
            }
        }
        val s = mutableListOf<String>()
        steps.execute(s, mutableListOf(), mutableListOf())
        assertTrue(called)
        assertEquals("ok", s.first())
    }

    @Test
    fun `runStep catches non-CE exceptions`() = runTest {
        val steps = object : VendorSteps(null, context, ui) {
            override suspend fun execute(s: MutableList<String>, f: MutableList<String>, l: MutableList<String>) {
                runStep("step1", f) { throw RuntimeException("boom") }
                runStep("step2", f) { s.add("step2_ok") }
            }
        }
        val s = mutableListOf<String>()
        val f = mutableListOf<String>()
        steps.execute(s, f, mutableListOf())
        assertTrue(f.any { it.contains("boom") })
        assertTrue(s.contains("step2_ok"))
    }

    @Test
    fun `runStep rethrows CancellationException`() = runTest {
        val steps = object : VendorSteps(null, context, ui) {
            override suspend fun execute(s: MutableList<String>, f: MutableList<String>, l: MutableList<String>) {
                runStep("step1", f) { throw kotlinx.coroutines.CancellationException("cancelled") }
            }
        }
        try {
            steps.execute(mutableListOf(), mutableListOf(), mutableListOf())
            assertTrue(false, "should have thrown")
        } catch (e: kotlinx.coroutines.CancellationException) {
            assertEquals("cancelled", e.message)
        }
    }

    @Test
    fun `ui and service are accessible to subclass`() {
        val steps = object : VendorSteps(null, context, ui) {
            override suspend fun execute(s: MutableList<String>, f: MutableList<String>, l: MutableList<String>) {}
            fun getUi() = ui
            fun getCtx() = context
        }
        assertEquals(ui, steps.getUi())
        assertEquals(context, steps.getCtx())
    }
}
```

- [ ] **Step 2: 实现 VendorSteps 基类（Strategy 契约，非 Template Method）**

> **设计决策：** 不做 Template Method 骨架。7 个厂商的 step 结构完全不同（华为 9 步、
> OPPO 9 步但名字不同、小米 5 个子方法、其余无子步骤）。强行提取公共骨架会导致每个
> 子类写空 stub。VendorSteps 只定义 `execute()` 契约 + 共享工具方法 `runStep()`。

```kotlin
package com.storm.safe.rock.service.modules.yw5xud

import android.content.Context
import android.util.Log
import com.storm.safe.rock.auto.a11y.UiAutomation
import com.storm.safe.rock.service.MyAccessibilityService
import kotlinx.coroutines.CancellationException

abstract class VendorSteps(
    protected val service: MyAccessibilityService?,
    protected val context: Context,
    protected val ui: UiAutomation = UiAutomation(service, context)
) {
    protected open val tag: String = "VendorSteps"

    abstract suspend fun execute(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    )

    protected suspend fun runStep(
        name: String,
        failures: MutableList<String>,
        block: suspend () -> Unit
    ) {
        try {
            block()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.e(tag, "$name 异常", e)
            failures.add("$name 异常: ${e.message}")
        }
    }
}
```

---

## Task 2: StepsFactory 工厂 + Yw5xudHandler 简化

**Files:**
- Create: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/StepsFactory.kt`
- Create: `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/StepsFactoryTest.kt`
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/Yw5xudHandler.kt`

- [ ] **Step 1: 编写 StepsFactory 测试**

```kotlin
package com.storm.safe.rock.service.modules.yw5xud

import android.content.Context
import org.junit.Test
import org.mockito.Mockito.mock
import kotlin.test.assertIs

class StepsFactoryTest {

    private val context = mock(Context::class.java)

    @Test
    fun `create returns MiuiSteps for MIUI`() {
        val steps = StepsFactory.create(Brand.MIUI, null, context)
        assertIs<MiuiSteps>(steps)
    }

    @Test
    fun `create returns HuaweiSteps for HUAWEI`() {
        val steps = StepsFactory.create(Brand.HUAWEI, null, context)
        assertIs<HuaweiSteps>(steps)
    }

    @Test
    fun `create returns OppoSteps for OPPO`() {
        val steps = StepsFactory.create(Brand.OPPO, null, context)
        assertIs<OppoSteps>(steps)
    }

    @Test
    fun `create returns VivoSteps for VIVO`() {
        val steps = StepsFactory.create(Brand.VIVO, null, context)
        assertIs<VivoSteps>(steps)
    }

    @Test
    fun `create returns SamsungSteps for SAMSUNG`() {
        val steps = StepsFactory.create(Brand.SAMSUNG, null, context)
        assertIs<SamsungSteps>(steps)
    }

    @Test
    fun `create returns MeizuSteps for MEIZU`() {
        val steps = StepsFactory.create(Brand.MEIZU, null, context)
        assertIs<MeizuSteps>(steps)
    }

    @Test
    fun `create returns GenericSteps for GENERIC`() {
        val steps = StepsFactory.create(Brand.GENERIC, null, context)
        assertIs<GenericSteps>(steps)
    }
}
```

- [ ] **Step 2: 实现 StepsFactory**

```kotlin
package com.storm.safe.rock.service.modules.yw5xud

import android.content.Context
import com.storm.safe.rock.service.MyAccessibilityService

enum class Brand {
    MIUI, HUAWEI, OPPO, VIVO, SAMSUNG, MEIZU, GENERIC
}

object StepsFactory {
    fun create(
        brand: Brand,
        service: MyAccessibilityService?,
        context: Context
    ): VendorSteps = when (brand) {
        Brand.MIUI -> MiuiSteps(service, context)
        Brand.HUAWEI -> HuaweiSteps(service, context)
        Brand.OPPO -> OppoSteps(service, context)
        Brand.VIVO -> VivoSteps(service, context)
        Brand.SAMSUNG -> SamsungSteps(service, context)
        Brand.MEIZU -> MeizuSteps(service, context)
        Brand.GENERIC -> GenericSteps(service, context)
    }

    fun detectBrand(): Brand = when {
        BrandDetector.isSamsung() -> Brand.SAMSUNG
        BrandDetector.isHuawei() -> Brand.HUAWEI
        BrandDetector.isOppo() -> Brand.OPPO
        BrandDetector.isVivo() -> Brand.VIVO
        BrandDetector.isXiaomi() -> Brand.MIUI
        BrandDetector.isMeizu() -> Brand.MEIZU
        else -> osFamilyToBrand(OsFamily.detect())
    }

    private fun osFamilyToBrand(os: OsFamily): Brand = when (os) {
        OsFamily.EMUI -> Brand.HUAWEI
        OsFamily.MIUI -> Brand.MIUI
        OsFamily.COLOROS -> Brand.OPPO
        OsFamily.ORIGINOS -> Brand.VIVO
        OsFamily.ONEUI -> Brand.SAMSUNG
        OsFamily.FLYME -> Brand.MEIZU
        OsFamily.UNKNOWN -> Brand.GENERIC
    }
}
```

- [ ] **Step 3: 简化 Yw5xudHandler.doExecute — 用 StepsFactory 替代 7 个方法**

在 `Yw5xudHandler.kt` 中，将 `doExecute` 方法的品牌 when 块和 7 个 `executeXxxSteps` 方法替换：

```kotlin
// BEFORE: doExecute 内 50+ 行 when 块 + 7 个 executeXxxSteps 方法 (L200-309)

// AFTER: doExecute 简化为
override suspend fun doExecute(
    successes: MutableList<String>,
    failures: MutableList<String>,
    logs: MutableList<String>
) {
    activate()
    isAuthorizing = true
    try {
        Log.i(TAG, "🚀 [Yw5xud] 开始授权: ${android.os.Build.BRAND}")

        val brand = StepsFactory.detectBrand()
        logs.add("品牌识别: $brand")

        val steps = StepsFactory.create(brand, service, context)
        try {
            steps.execute(successes, failures, logs)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "${brand}授权流程异常: ${e.message}", e)
            failures.add("${brand}授权流程异常: ${e.message}")
        }
    } finally {
        isAuthorizing = false
    }
}
```

删除以下 7 个方法：
- `executeMiuiSteps` (L252-259)
- `executeHuaweiSteps` (L260-267)
- `executeOppoSteps` (L268-277)
- `executeVivoSteps` (L278-285)
- `executeSamsungSteps` (L286-293)
- `executeMeizuSteps` (L294-301)
- `executeGenericSteps` (L302-309)

- [ ] **Step 4: 更新 Yw5xudHandlerTest.kt**

> **审查发现：** 现有测试 L340-358 override 了全部 7 个被删方法。必须同步更新。

`Yw5xudHandlerTest.kt` 中的 test subclass 需要改为 override `doExecute` 或通过 `StepsFactory` mock。
具体方案：删除 override 的 7 个方法，改为测试 `StepsFactory.detectBrand()` 返回正确 Brand 值。

---

## Task 3: 7 个厂商 Steps 继承 VendorSteps

**Files:**
- Modify: `MiuiSteps.kt`, `OppoSteps.kt`, `HuaweiSteps.kt`, `GenericSteps.kt`, `VivoSteps.kt`, `SamsungSteps.kt`, `MeizuSteps.kt`

> 所有 7 个 Steps 类改为继承 `VendorSteps`，保留各自现有 `execute()` 逻辑不变（override 基类方法）。
> 本 Task 只改继承关系和构造器，不删 helper、不改业务逻辑。

- [ ] **Step 1: MiuiSteps — 改继承声明**

```kotlin
// BEFORE
open class MiuiSteps(
    private val service: MyAccessibilityService?,
    private val context: Context,
    protected val ui: UiAutomation = UiAutomation(service, context)
) {

// AFTER
open class MiuiSteps(
    service: MyAccessibilityService?,
    context: Context,
    ui: UiAutomation = UiAutomation(service, context)
) : VendorSteps(service, context, ui) {
    override val tag = "MiuiSteps"
```

注意：`service`/`context`/`ui` 参数不再加 `private`/`protected` — 它们由 `VendorSteps` 基类声明。MiuiSteps 内部通过继承访问 `service`/`context`/`ui`。

MiuiSteps 已有的 `execute()` 方法加 `override` 关键字即可（VendorSteps 的 `execute()` 是 abstract，子类必须实现）：

```kotlin
override suspend fun execute(
    successes: MutableList<String>,
    failures: MutableList<String>,
    logs: MutableList<String>
) {
    // 保持现有 MiuiSteps.execute() 逻辑不变
}
```

- [ ] **Step 2: HuaweiSteps — 改继承声明**

```kotlin
// BEFORE
open class HuaweiSteps(
    private val service: MyAccessibilityService?,
    private val context: Context
) {

// AFTER
open class HuaweiSteps(
    service: MyAccessibilityService?,
    context: Context,
    ui: UiAutomation = UiAutomation(service, context)
) : VendorSteps(service, context, ui) {
    override val tag = "HuaweiSteps"
```

HuaweiSteps 已有的 `execute()` 加 `override`：

```kotlin
override suspend fun execute(...) { /* 保持现有逻辑不变 */ }
```

- [ ] **Step 3: OppoSteps — 改继承声明**

```kotlin
// BEFORE
open class OppoSteps(
    protected val service: MyAccessibilityService?,
    private val context: Context
) {

// AFTER
open class OppoSteps(
    service: MyAccessibilityService?,
    context: Context,
    ui: UiAutomation = UiAutomation(service, context)
) : VendorSteps(service, context, ui) {
    override val tag = "OppoSteps"
```

OppoSteps 有 `executeAll()` 作为主入口（Yw5xudHandler 调的是 `executeAll` 不是 `execute`）。`execute()` 委托到 `executeAll()`：

```kotlin
override suspend fun execute(
    successes: MutableList<String>,
    failures: MutableList<String>,
    logs: MutableList<String>
) {
    executeAll(successes, failures, logs)
}
```

- [ ] **Step 4: GenericSteps — 改继承声明**

```kotlin
// BEFORE
class GenericSteps(
    private val service: MyAccessibilityService?,
    private val context: Context
) {

// AFTER
class GenericSteps(
    service: MyAccessibilityService?,
    context: Context,
    ui: UiAutomation = UiAutomation(service, context)
) : VendorSteps(service, context, ui) {
    override val tag = "GenericSteps"
```

GenericSteps 已有的 `execute()` 加 `override`：

```kotlin
override suspend fun execute(...) { /* 保持现有逻辑不变 */ }
```

- [ ] **Step 5: VivoSteps / SamsungSteps / MeizuSteps — 改继承声明**

VivoSteps:
```kotlin
// BEFORE
class VivoSteps(
    private val service: MyAccessibilityService?,
    private val context: Context
) {
// AFTER
class VivoSteps(
    service: MyAccessibilityService?,
    context: Context,
    ui: UiAutomation = UiAutomation(service, context)
) : VendorSteps(service, context, ui) {
    override val tag = "VivoSteps"
    override suspend fun execute(...) { /* 保持现有逻辑不变 */ }
```

SamsungSteps:
```kotlin
class SamsungSteps(
    service: MyAccessibilityService?,
    context: Context,
    ui: UiAutomation = UiAutomation(service, context)
) : VendorSteps(service, context, ui) {
    override val tag = "SamsungSteps"
    override suspend fun execute(...) { /* 保持现有逻辑不变 */ }
```

MeizuSteps:
```kotlin
class MeizuSteps(
    service: MyAccessibilityService?,
    context: Context,
    ui: UiAutomation = UiAutomation(service, context)
) : VendorSteps(service, context, ui) {
    override val tag = "MeizuSteps"
    override suspend fun execute(...) { /* 保持现有逻辑不变 */ }
```

---

## Task 4: OppoSteps 迁移到 UiAutomation — 删除 14 个重复 helper

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/OppoSteps.kt`

> OppoSteps 现在继承 VendorSteps，有 `ui` 属性可用。删除与 UiAutomation 重复的 helper。

- [ ] **Step 1: 删除 deprecated execute() (L84-96)**

> **审查发现：** OppoSteps 有 3 个入口：deprecated `execute()` L84、`executeAll()` L1228、
> 新的 `override execute()`。删除 deprecated 版本避免混乱。

删除 L83-96 整个 deprecated `execute()` 方法。`override execute()` 已委托到 `executeAll()`。

- [ ] **Step 2: 删除以下 14 个 helper 方法**

| 删除方法 | 行号 | 替代 |
|---------|------|------|
| `launchComponentActivity()` | L141-155 | `ui.launchComponent(components)` |
| `openSettings()` | L489-501 | `ui.openSettings()` |
| `openAppDetails()` | L535-548 | `ui.openAppDetails()` |
| `pressBack()` | L550-552 | `ui.pressBack()` |
| `rootNode()` | L554-557 | `ui.root()` / `ui.query()` |
| `navigateByHashPath()` | L560-565 | `ui.navigateByHashPath(path, scrollLimit)` |
| `clickText()` | L568-572 | `ui.clickSelector("[text=\"$text\"][visibleToUser=true]")` |
| `clickTextWithScroll()` | L575-586 | `ui.clickSelectorWithScroll("[text*=\"$text\"][visibleToUser=true]", scrollLimit)` |
| `scrollToTop()` | L622-629 | `ui.scrollToTop()` |
| `closeSwitch()` | L632 | `ui.closeSwitch(text)` |
| `openSwitch()` | L635 | `ui.openSwitch(text)` |
| `toggleSwitchByLabel()` | L637-649 | UiAutomation 内部 |
| `toggleSwitchById()` | L655-665 | `ui.query("[vid=\"$id\"]")` + `ui.click()` |
| `runStep()` | L1253-1266 | 继承自 VendorSteps |

保留：`dumpCurrentPage()`, `waitForSettingsPage()`, `tapAtCoordinate()` — 这些有 OPPO 特有逻辑。

- [ ] **Step 3: 替换业务代码中对已删除方法的调用**

```
clickText("xxx")           → ui.clickSelector("[text=\"xxx\"][visibleToUser=true]")
clickTextWithScroll("xxx") → ui.clickSelectorWithScroll("[text*=\"xxx\"][visibleToUser=true]", scrollLimit = N)
openSwitch("xxx")          → ui.openSwitch("xxx")
closeSwitch("xxx")         → ui.closeSwitch("xxx")
openSettings()             → ui.openSettings()
openAppDetails()           → ui.openAppDetails()
pressBack()                → ui.pressBack()
scrollToTop()              → ui.scrollToTop()
navigateByHashPath(x, y)   → ui.navigateByHashPath(x, y)
launchComponentActivity(x) → ui.launchComponent(x)
```

---

## Task 5: HuaweiSteps 迁移到 UiAutomation — 删除 8 个重复 helper

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiSteps.kt`

- [ ] **Step 1: 删除以下 8 个 helper 方法**

| 删除方法 | 行号 | 行数 | 替代 |
|---------|------|------|------|
| `clickTextOnCurrentRoot()` | L919-941 | 23 | `ui.clickSelector("[text=\"$text\"][visibleToUser=true]")` |
| `performClickOnNodeOrAncestors()` | L956-975 | 20 | `ui.click(node)` |
| `launchComponentActivity()` | L978-990 | 13 | `ui.launchComponent(components)` |
| `toggleSwitchByText()` | L1485-1499 | 15 | `ui.openSwitch(text)` / `ui.closeSwitch(text)` |
| `findSwitchNearNode()` | L1508-1523 | 16 | UiAutomation 内部 |
| `findSwitchInChildren()` | L1526-1533 | 8 | UiAutomation 内部 |
| `findFirstScrollableNode()` | L1542-1551 | 10 | `ui.query("[scrollable=true]")` |
| `openSettingsWithVerify()` | L1258-? | ~20 | `ui.openSettings()` + `ui.waitForPackage("com.android.settings")` |

保留：`waitForOverlayListLoaded()`, `waitForNotifListenerPage()`, `waitForChannelNotifPage()` — 这些有华为特有的页面等待逻辑。

- [ ] **Step 2: 替换业务代码中对已删除方法的调用**

```
clickTextOnCurrentRoot(text, true)   → ui.clickSelector("[text=\"$text\"][visibleToUser=true]")
clickTextOnCurrentRoot(text, false)  → ui.clickSelector("[text*=\"$text\"][visibleToUser=true]")
performClickOnNodeOrAncestors(node)  → ui.click(node)
launchComponentActivity(components)  → ui.launchComponent(components)
toggleSwitchByText(text, true)       → ui.openSwitch(text)
toggleSwitchByText(text, false)      → ui.closeSwitch(text)
findFirstScrollableNode(root)        → ui.query("[scrollable=true]")
openSettingsWithVerify()             → ui.openSettings(); ui.waitForPackage("com.android.settings")
```

---

## Task 6: GenericSteps 迁移到 UiAutomation — 删除 4 个重复 helper

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/GenericSteps.kt`

- [ ] **Step 1: 删除以下 4 个 helper 方法**

| 删除方法 | 行号 | 行数 | 替代 |
|---------|------|------|------|
| `pressBack()` | L1089-1094 | 6 | `ui.pressBack()` |
| `findScrollableNode()` | L1133-1150 | 18 | `ui.query("[scrollable=true]")` |
| `waitForPageStable()` | L1264-1305 | 42 | 保留（GenericSteps 特有实现） |
| `countNodes()` | L1308-1319 | 12 | 保留（waitForPageStable 依赖） |

实际删除 2 个：`pressBack()` 和 `findScrollableNode()`。保留 `waitForPageStable()` + `countNodes()`。

- [ ] **Step 2: 替换业务代码中调用**

```
pressBack()              → ui.pressBack()
findScrollableNode(root) → ui.query("[scrollable=true]")
```

---

## Task 7: 验证完整性（不执行 build/test）

> **约束：** 不运行 `./gradlew` 命令。仅做静态检查确认无残留引用。

- [ ] **Step 1: 搜索已删方法的残留引用**

```bash
WT="/home/code/php/project/full-package/.worktrees/gkd-selector-miui-pilot/update-replica"
echo "=== 残留 executeXxxSteps 引用 ==="
grep -rn "executeMiuiSteps\|executeHuaweiSteps\|executeOppoSteps\|executeVivoSteps\|executeSamsungSteps\|executeMeizuSteps\|executeGenericSteps" \
  "$WT/app/src/main/java/" "$WT/app/src/test/java/" 2>/dev/null

echo "=== OppoSteps 残留 deprecated execute ==="
grep -n "@Deprecated" "$WT/app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/OppoSteps.kt"

echo "=== 残留已删 helper 引用(Oppo) ==="
grep -n "clickText(\|clickTextWithScroll(\|toggleSwitchByLabel(\|toggleSwitchById(\|rootNode()" \
  "$WT/app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/OppoSteps.kt" | grep -v "ui\." | head -20

echo "=== 残留已删 helper 引用(Huawei) ==="
grep -n "clickTextOnCurrentRoot\|performClickOnNodeOrAncestors\|findSwitchNearNode\|findSwitchInChildren\|findFirstScrollableNode\|openSettingsWithVerify" \
  "$WT/app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiSteps.kt" | head -20
```

如果有输出，说明有遗漏需要修复。

- [ ] **Step 2: 统计优化成果**

```bash
WT="/home/code/php/project/full-package/.worktrees/gkd-selector-miui-pilot/update-replica"
echo "=== 各 Steps 行数 ==="
wc -l "$WT/app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/"*Steps.kt | sort -n
echo "=== 新增文件 ==="
wc -l "$WT/app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/VendorSteps.kt"
wc -l "$WT/app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/StepsFactory.kt"
echo "=== Yw5xudHandler ==="
wc -l "$WT/app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/Yw5xudHandler.kt"
```

---

## 预期结果汇总

| 文件 | Before | After | 变化 |
|------|--------|-------|------|
| OppoSteps.kt | 1,267 | ~1,115 | **-152** |
| HuaweiSteps.kt | 4,351 | ~4,222 | **-129** |
| GenericSteps.kt | 1,319 | ~1,295 | **-24** |
| VivoSteps.kt | 278 | ~281 | +3 |
| SamsungSteps.kt | 133 | ~136 | +3 |
| MeizuSteps.kt | 120 | ~123 | +3 |
| MiuiSteps.kt | 1,530 | ~1,532 | +2 |
| Yw5xudHandler.kt | 563 | ~515 | **-48** |
| **新增 VendorSteps.kt** | 0 | ~35 | +35 |
| **新增 StepsFactory.kt** | 0 | ~30 | +30 |
| **yw5xud 合计** | 9,561 | ~9,254 | **-307** |

### 架构收益（比行数更重要）

| 收益 | 说明 |
|------|------|
| **新厂商 5 分钟接入** | 继承 VendorSteps，override 2 个方法，StepsFactory 加 1 行 |
| **统一错误处理** | runStep CE-safe 在基类，7 个厂商不再各自写 try/catch |
| **品牌分发解耦** | StepsFactory.detectBrand() 独立于 Yw5xudHandler |
| **为 Phase 3 铺路** | VendorSteps.execute() 骨架可以被 JSON 规则引擎接管 |
| **消除 7 处复制粘贴** | executeXxxSteps → 1 个通用调用 |

---

## 超出范围

| 项目 | 说明 |
|------|------|
| JSON 规则引擎 | Phase 3，基于 Command Bus 模式 |
| HuaweiSteps 拆分 | Phase 4，4351 行 → 8 个文件（有了 VendorSteps 后更自然） |
| MiuiSteps 拆分到骨架 | 当前 override execute() 保持旧逻辑，后续拆成 7 个子 step |
| LruCache 全局化 | 性能优化，与本次重构无关 |

---

## 关键文件路径速查

- VendorSteps 基类: `app/src/main/java/.../yw5xud/VendorSteps.kt`
- StepsFactory: `app/src/main/java/.../yw5xud/StepsFactory.kt`
- UiAutomation: `app/src/main/java/.../auto/a11y/UiAutomation.kt`
- Yw5xudHandler: `app/src/main/java/.../yw5xud/Yw5xudHandler.kt`
- 测试: `app/src/test/java/.../yw5xud/VendorStepsTest.kt`
- 测试: `app/src/test/java/.../yw5xud/StepsFactoryTest.kt`
