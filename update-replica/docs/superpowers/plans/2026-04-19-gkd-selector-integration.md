# GKD Selector 集成 + UiAutomation 重构计划

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 引入 GKD selector 引擎替代各厂商 Steps 中重复的 UI 查询代码，建立 `UiAutomation` 共享操作层，以 MiuiSteps 为试点完成端到端迁移（收益最大：-25%，224 行重复 helper + 148 行查询模板，且有小米 13 真机验证）。

**Architecture:** 三层架构 — GKD selector (纯查询引擎，39 个 Kotlin 文件，零 Android 依赖) → A11yContext (从 GKD `app/a11y/` 精简适配，含 LruCache 节点缓存 + Transform 桥接) → UiAutomation (组合操作层：query + click + scroll + retry + toggle)。各厂商 Steps 类只负责业务编排，不再各自实现查询/点击/滚动原语。

**参考源码：** GKD `app/src/main/kotlin/li/songe/gkd/a11y/` 目录（A11yContext.kt 565行 + A11yExt.kt 141行）是 Android 绑定层的参考实现，我们精简其中的 Transform 桥接 + LruCache + getVid + compatChecked，去掉 GKD 特有的规则匹配 / Shizuku / 中断机制 / Activity 追踪。

**Tech Stack:** GKD selector (li.songe.selector, 纯 kotlin-stdlib), Android AccessibilityService, Kotlin Coroutines, JUnit 4

**约束:** 每个 Task 不执行 git/test 命令，最终统一编译+测试+提交。使用 worktree 隔离开发。

**Worktree:**
- 路径: `/home/code/php/project/full-package/.worktrees/gkd-selector-miui-pilot`
- 分支: `refactor/gkd-selector-miui-pilot`
- update-replica 子目录: `.worktrees/gkd-selector-miui-pilot/update-replica/`
- **所有文件操作必须在 worktree 内执行，不要修改主分支 (main) 的文件**

```bash
# 进入 worktree 工作区
cd /home/code/php/project/full-package/.worktrees/gkd-selector-miui-pilot/update-replica
```

---

## 文件结构

### 新建文件

| 文件 | 职责 | 行数 |
|------|------|------|
| `app/src/main/java/li/songe/selector/**/*.kt` (39 文件) | GKD selector 引擎源码（从 gkd 仓库 copy，strip KMP/JS） | ~4,200 |
| `app/src/main/java/com/storm/safe/rock/auto/a11y/A11yContext.kt` | Transform 桥接 + LruCache 节点缓存（精简自 GKD `A11yContext.kt`) | ~250 |
| `app/src/main/java/com/storm/safe/rock/auto/a11y/A11yExt.kt` | getVid + compatChecked + 常量（精简自 GKD `A11yExt.kt`） | ~40 |
| `app/src/main/java/com/storm/safe/rock/auto/a11y/UiAutomation.kt` | 共享操作层（query/click/scroll/toggle/wait） | ~200 |
| `app/src/test/java/li/songe/selector/SelectorSmokeTest.kt` | GKD selector 引擎冒烟测试 | ~60 |
| `app/src/test/java/com/storm/safe/rock/auto/a11y/A11yContextTest.kt` | Transform 桥接 + 缓存测试（参考 GKD 模式） | ~80 |
| `app/src/test/java/com/storm/safe/rock/auto/a11y/UiAutomationTest.kt` | UiAutomation 操作层测试 | ~120 |

### 修改文件（Phase 1 — MiuiSteps 试点）

| 文件 | 修改内容 | 增减 |
|------|---------|------|
| `app/src/main/java/.../yw5xud/MiuiSteps.kt` | 删除 10 个重复 helper，20 处 clickTextNode 替换为 selector | -224 行 helper, -148 行模板 |
| `app/src/main/java/.../yw5xud/Yw5xudHandler.kt` | executeMiuiSteps 传入 UiAutomation | ~+5 行 |

### 超出范围（Phase 2 — 后续迁移，本计划不含）

| 文件 | 修改内容 | 预估增减 |
|------|---------|---------|
| `app/src/main/java/.../yw5xud/OppoSteps.kt` | 删除 14 个 helper，改用 UiAutomation | -152 行 |
| `app/src/main/java/.../yw5xud/HuaweiSteps.kt` | 删除 6 个重复 helper | -129 行 |
| `app/src/main/java/.../yw5xud/GenericSteps.kt` | 删除 4 个重复 helper | -78 行 |

---

## Task 1: 引入 GKD selector 子模块（参考 `android/` 项目）

> **参考:** `android/` 项目已通过 `include ':selector'` + `implementation project(':selector')` 集成。
> 我们复制相同模式，需升级 Kotlin 版本以支持 KMP 插件。

**Files:**
- Copy: `android/selector/` → `update-replica/selector/` （整个目录，含 build.gradle.kts + src/）
- Modify: `update-replica/build.gradle` — 添加 `kotlin.multiplatform` 插件声明 + 升级 Kotlin 版本
- Modify: `update-replica/settings.gradle` — 添加 `include ':selector'`
- Modify: `update-replica/app/build.gradle` — 添加 `implementation project(':selector')`

- [ ] **Step 1: 复制 selector 子模块**

```bash
# 在 worktree 内操作
WT="/home/code/php/project/full-package/.worktrees/gkd-selector-miui-pilot"
cd "$WT/update-replica"
cp -r "$WT/android/selector" ./selector
```

- [ ] **Step 2: 调整 selector/build.gradle.kts 兼容 update-replica（compileSdk 34, Java 1.8）**

写入 `selector/build.gradle.kts`：

```kotlin
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.multiplatform")
}

kotlin {
    androidTarget()
    jvm()

    sourceSets {
        val commonMain by getting
        val jvmMain by getting
        val androidMain by getting {
            dependsOn(jvmMain)
        }
    }
}

android {
    namespace = "li.songe.selector"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

tasks.withType<Test> {
    enabled = false
}
```

- [ ] **Step 3: 升级 Kotlin 版本 + 添加 KMP 插件**

修改 `build.gradle`（根目录）：

```groovy
plugins {
    id 'com.android.application' version '8.2.2' apply false
    id 'com.android.library' version '8.2.2' apply false
    id 'org.jetbrains.kotlin.android' version '2.1.0' apply false
    id 'org.jetbrains.kotlin.multiplatform' version '2.1.0' apply false
}
```

- [ ] **Step 4: settings.gradle 添加 selector 模块**

修改 `settings.gradle`：

```groovy
rootProject.name = "update-replica"
include ':app'
include ':selector'
```

- [ ] **Step 5: app/build.gradle 添加 selector 依赖**

在 `app/build.gradle` 的 `dependencies` 块添加：

```groovy
implementation project(':selector')
```

- [ ] **Step 6: 验证集成**

```bash
# 预期 selector 模块存在
ls selector/src/commonMain/kotlin/li/songe/selector/*.kt | wc -l
# 应该输出 ~13

# 预期 build.gradle.kts 存在
cat selector/build.gradle.kts | head -3
# 应该输出 plugins { ... }
```

---

## Task 2: GKD Selector 冒烟测试

**Files:**
- Create: `app/src/test/java/li/songe/selector/SelectorSmokeTest.kt`

- [ ] **Step 1: 编写冒烟测试验证 selector 引擎编译+运行正常**

```kotlin
package li.songe.selector

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class SelectorSmokeTest {

    @Test
    fun `parse simple text selector`() {
        val selector = Selector.parse("[text=\"允许\"]")
        assertNotNull(selector)
        assertEquals("[text=\"允许\"]", selector.toString())
    }

    @Test
    fun `parse contains text selector`() {
        val selector = Selector.parse("[text*=\"自启动\"]")
        assertNotNull(selector)
        assertTrue(selector.toString().contains("自启动"))
    }

    @Test
    fun `parse class name selector`() {
        val selector = Selector.parse("Switch[checked=false]")
        assertNotNull(selector)
        assertTrue(selector.toString().contains("Switch"))
    }

    @Test
    fun `parse sibling selector with plus operator`() {
        val selector = Selector.parse("[text=\"允许自启动\"] + Switch")
        assertNotNull(selector)
    }

    @Test
    fun `parse ancestor selector with greater than operator`() {
        val selector = Selector.parse("LinearLayout > [text=\"电池\"]")
        assertNotNull(selector)
    }

    @Test
    fun `parse descendant selector with double less than`() {
        val selector = Selector.parse("[text=\"允许\"] <<n [vid=\"content\"]")
        assertNotNull(selector)
    }

    @Test
    fun `parse compound selector with AND`() {
        val selector = Selector.parse("[text=\"允许\"][clickable=true][visibleToUser=true]")
        assertNotNull(selector)
    }

    @Test
    fun `parseOrNull returns null for invalid syntax`() {
        val selector = Selector.parseOrNull("[[[invalid")
        assertNull(selector)
    }

    @Test
    fun `parse at-mark target selector`() {
        val selector = Selector.parse("@Switch[checked=false] + [text=\"允许自启动\"]")
        assertNotNull(selector)
    }

    @Test
    fun `fastQueryList extracts id and text fast queries`() {
        val selector = Selector.parse("[vid=\"switch_widget\"]")
        assertNotNull(selector)
        assertTrue(selector.fastQueryList.isNotEmpty())
    }
}
```

---

## Task 3: A11yContext 桥接层（精简自 GKD `app/a11y/`）

> **参考源码:** `gkd/app/src/main/kotlin/li/songe/gkd/a11y/A11yContext.kt` (565行) + `A11yExt.kt` (141行)
> 我们只取 Transform 桥接 + LruCache + getVid + compatChecked，去掉 GKD 特有的规则匹配 / Shizuku / 中断 / Activity 追踪。

**Files:**
- Create: `app/src/main/java/com/storm/safe/rock/auto/a11y/A11yContext.kt`（精简自 GKD 的 A11yContext，~250 行）
- Create: `app/src/main/java/com/storm/safe/rock/auto/a11y/A11yExt.kt`（精简自 GKD 的 A11yExt，~40 行）
- Create: `app/src/test/java/com/storm/safe/rock/auto/a11y/A11yContextTest.kt`

- [ ] **Step 1: 创建 A11yExt.kt（从 GKD A11yExt 精简）**

```kotlin
package com.storm.safe.rock.auto.a11y

import android.view.accessibility.AccessibilityNodeInfo

const val MAX_CHILD_SIZE = 512
const val MAX_DESCENDANTS_SIZE = 4096

fun AccessibilityNodeInfo.getVid(): CharSequence? {
    val id = viewIdResourceName ?: return null
    val appId = packageName ?: return null
    if (id.startsWith(appId) && id.startsWith(":id/", appId.length)) {
        return id.subSequence(appId.length + ":id/".length, id.length)
    }
    return null
}

@Suppress("DEPRECATION")
val AccessibilityNodeInfo.compatChecked: Boolean?
    get() = if (android.os.Build.VERSION.SDK_INT >= 36) {
        try {
            when (checked) {
                AccessibilityNodeInfo.CHECKED_STATE_TRUE -> true
                AccessibilityNodeInfo.CHECKED_STATE_FALSE -> false
                else -> null
            }
        } catch (_: Exception) { isChecked }
    } else {
        isChecked
    }
```

- [ ] **Step 2: 编写 A11yContext 测试**

```kotlin
package com.storm.safe.rock.auto.a11y

import android.view.accessibility.AccessibilityNodeInfo
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class A11yContextTest {

    private fun mockNode(
        text: CharSequence? = null,
        className: CharSequence? = "android.widget.TextView",
        viewId: String? = null,
        desc: CharSequence? = null,
        clickable: Boolean = false,
        checked: Boolean = false,
        checkable: Boolean = false,
        scrollable: Boolean = false,
        visible: Boolean = true,
        editable: Boolean = false,
        focusable: Boolean = false,
        longClickable: Boolean = false,
        childCount: Int = 0,
        packageName: CharSequence? = "com.android.settings"
    ): AccessibilityNodeInfo {
        val node = mock(AccessibilityNodeInfo::class.java)
        `when`(node.text).thenReturn(text)
        `when`(node.className).thenReturn(className)
        `when`(node.viewIdResourceName).thenReturn(viewId)
        `when`(node.contentDescription).thenReturn(desc)
        `when`(node.isClickable).thenReturn(clickable)
        `when`(node.isChecked).thenReturn(checked)
        `when`(node.isCheckable).thenReturn(checkable)
        `when`(node.isScrollable).thenReturn(scrollable)
        `when`(node.isVisibleToUser).thenReturn(visible)
        `when`(node.isEditable).thenReturn(editable)
        `when`(node.isFocusable).thenReturn(focusable)
        `when`(node.isLongClickable).thenReturn(longClickable)
        `when`(node.childCount).thenReturn(childCount)
        `when`(node.packageName).thenReturn(packageName)
        return node
    }

    @Test
    fun `getCacheAttr returns text`() {
        val ctx = A11yContext()
        val node = mockNode(text = "允许")
        assertEquals("允许", (ctx.getCacheAttr(node, "text") as CharSequence).toString())
    }

    @Test
    fun `getCacheAttr returns className as name`() {
        val ctx = A11yContext()
        val node = mockNode(className = "android.widget.Switch")
        assertEquals("android.widget.Switch", (ctx.getCacheAttr(node, "name") as CharSequence).toString())
    }

    @Test
    fun `getCacheAttr returns clickable boolean`() {
        val ctx = A11yContext()
        assertEquals(true, ctx.getCacheAttr(mockNode(clickable = true), "clickable"))
    }

    @Test
    fun `getCacheAttr returns checked boolean`() {
        val ctx = A11yContext()
        assertEquals(true, ctx.getCacheAttr(mockNode(checked = true), "checked"))
    }

    @Test
    fun `getCacheAttr returns visibleToUser`() {
        val ctx = A11yContext()
        assertEquals(false, ctx.getCacheAttr(mockNode(visible = false), "visibleToUser"))
    }

    @Test
    fun `getCacheAttr returns id`() {
        val ctx = A11yContext()
        assertEquals("com.android.settings:id/switch_widget",
            ctx.getCacheAttr(mockNode(viewId = "com.android.settings:id/switch_widget"), "id"))
    }

    @Test
    fun `getCacheAttr returns vid (short id)`() {
        val ctx = A11yContext()
        val node = mockNode(
            viewId = "com.android.settings:id/switch_widget",
            packageName = "com.android.settings"
        )
        assertEquals("switch_widget", (ctx.getCacheAttr(node, "vid") as CharSequence).toString())
    }

    @Test
    fun `getCacheAttr returns desc`() {
        val ctx = A11yContext()
        assertEquals("返回", (ctx.getCacheAttr(mockNode(desc = "返回"), "desc") as CharSequence).toString())
    }

    @Test
    fun `getCacheAttr returns null for unknown`() {
        val ctx = A11yContext()
        assertNull(ctx.getCacheAttr(mockNode(), "nonexistent"))
    }

    @Test
    fun `getCacheAttr returns childCount`() {
        val ctx = A11yContext()
        assertEquals(5, ctx.getCacheAttr(mockNode(childCount = 5), "childCount"))
    }

    @Test
    fun `transform getName returns className`() {
        val ctx = A11yContext()
        val node = mockNode(className = "android.widget.Button")
        assertEquals("android.widget.Button", ctx.transform.getName(node).toString())
    }

    @Test
    fun `transform getChildren returns child sequence`() {
        val ctx = A11yContext()
        val parent = mockNode(childCount = 2)
        val child0 = mockNode(text = "c0")
        val child1 = mockNode(text = "c1")
        `when`(parent.getChild(0)).thenReturn(child0)
        `when`(parent.getChild(1)).thenReturn(child1)

        val children = ctx.transform.getChildren(parent).toList()
        assertEquals(2, children.size)
        assertEquals("c0", children[0].text.toString())
    }

    @Test
    fun `selector matches node with text=允许`() {
        val ctx = A11yContext()
        val node = mockNode(text = "允许", className = "android.widget.Button")
        val selector = li.songe.selector.Selector.parse("[text=\"允许\"]")
        val matched = selector.match(node, ctx.transform, li.songe.selector.MatchOption.default)
        assertNotNull(matched)
    }

    @Test
    fun `selector does not match wrong text`() {
        val ctx = A11yContext()
        val node = mockNode(text = "拒绝", className = "android.widget.Button")
        val selector = li.songe.selector.Selector.parse("[text=\"允许\"]")
        val matched = selector.match(node, ctx.transform, li.songe.selector.MatchOption.default)
        assertNull(matched)
    }

    @Test
    fun `selector matches Switch by name + checked`() {
        val ctx = A11yContext()
        val node = mockNode(className = "android.widget.Switch", checked = false)
        val selector = li.songe.selector.Selector.parse("Switch[checked=false]")
        val matched = selector.match(node, ctx.transform, li.songe.selector.MatchOption.default)
        assertNotNull(matched)
    }

    @Test
    fun `selector contains text match`() {
        val ctx = A11yContext()
        val node = mockNode(text = "允许自启动管理", className = "android.widget.TextView")
        val selector = li.songe.selector.Selector.parse("[text*=\"自启动\"]")
        val matched = selector.match(node, ctx.transform, li.songe.selector.MatchOption.default)
        assertNotNull(matched)
    }
}
```

- [ ] **Step 3: 实现 A11yContext（精简自 GKD A11yContext.kt）**

> 保留: Transform 桥接、LruCache (child/parent/index)、getCacheAttr、getVid
> 去掉: currentRule、guardInterrupt、interruptKey、ResolvedRule、shizuku.casted、
>       activityRuleFlow、topActivityFlow、appChangeTime、rootCache.atomic

```kotlin
package com.storm.safe.rock.auto.a11y

import android.util.LruCache
import android.view.accessibility.AccessibilityNodeInfo
import li.songe.selector.FastQuery
import li.songe.selector.MatchOption
import li.songe.selector.Selector
import li.songe.selector.Transform
import li.songe.selector.getCharSequenceAttr
import li.songe.selector.getCharSequenceInvoke
import li.songe.selector.getIntInvoke
import li.songe.selector.getBooleanInvoke

class A11yContext {

    private var childCache = LruCache<Pair<AccessibilityNodeInfo, Int>, AccessibilityNodeInfo>(MAX_DESCENDANTS_SIZE)
    private var indexCache = LruCache<AccessibilityNodeInfo, Int>(MAX_DESCENDANTS_SIZE)
    private var parentCache = LruCache<AccessibilityNodeInfo, AccessibilityNodeInfo>(MAX_DESCENDANTS_SIZE)

    fun clearNodeCache() {
        try {
            childCache.evictAll()
            parentCache.evictAll()
            indexCache.evictAll()
        } catch (_: Exception) {
            childCache = LruCache(MAX_DESCENDANTS_SIZE)
            indexCache = LruCache(MAX_DESCENDANTS_SIZE)
            parentCache = LruCache(MAX_DESCENDANTS_SIZE)
        }
    }

    private fun getCacheParent(node: AccessibilityNodeInfo): AccessibilityNodeInfo? {
        parentCache[node]?.let { return it }
        val p = try { node.parent } catch (_: Exception) { null }
        if (p != null) parentCache.put(node, p)
        return p
    }

    private fun getCacheChild(node: AccessibilityNodeInfo, index: Int): AccessibilityNodeInfo? {
        if (index !in 0 until node.childCount) return null
        childCache[node to index]?.let { return it }
        val child = try { node.getChild(index) } catch (_: Exception) { null }
        if (child != null) {
            indexCache.put(child, index)
            parentCache.put(child, node)
            childCache.put(node to index, child)
        }
        return child
    }

    private fun getCacheChildren(node: AccessibilityNodeInfo?): Sequence<AccessibilityNodeInfo> {
        if (node == null) return emptySequence()
        return sequence {
            repeat(node.childCount.coerceAtMost(MAX_CHILD_SIZE)) { index ->
                val child = getCacheChild(node, index) ?: return@sequence
                yield(child)
            }
        }
    }

    private fun getCacheIndex(node: AccessibilityNodeInfo): Int {
        indexCache[node]?.let { return it }
        val p = getCacheParent(node) ?: return 0
        getCacheChildren(p).forEachIndexed { index, child ->
            if (child == node) {
                indexCache.put(node, index)
                return index
            }
        }
        return 0
    }

    private fun getTempVid(n: AccessibilityNodeInfo): CharSequence? = n.getVid()

    fun getCacheAttr(node: AccessibilityNodeInfo, name: String): Any? = when (name) {
        "id" -> node.viewIdResourceName
        "vid" -> getTempVid(node)
        "name" -> node.className
        "text" -> node.text
        "desc" -> node.contentDescription
        "clickable" -> node.isClickable
        "focusable" -> node.isFocusable
        "checkable" -> node.isCheckable
        "checked" -> node.compatChecked
        "editable" -> node.isEditable
        "longClickable" -> node.isLongClickable
        "visibleToUser" -> node.isVisibleToUser
        "scrollable" -> node.isScrollable
        "selected" -> node.isSelected
        "index" -> getCacheIndex(node)
        "childCount" -> node.childCount
        "parent" -> getCacheParent(node)
        else -> null
    }

    private fun getFastQueryNodes(
        node: AccessibilityNodeInfo,
        fastQuery: FastQuery
    ): List<AccessibilityNodeInfo> {
        return when (fastQuery) {
            is FastQuery.Id -> try { node.findAccessibilityNodeInfosByViewId(fastQuery.value) } catch (_: Exception) { emptyList() }
            is FastQuery.Text -> try { node.findAccessibilityNodeInfosByText(fastQuery.value) } catch (_: Exception) { emptyList() }
            is FastQuery.Vid -> try { node.findAccessibilityNodeInfosByViewId("${node.packageName}:id/${fastQuery.value}") } catch (_: Exception) { emptyList() }
        }
    }

    val transform = Transform(
        getAttr = { target, name ->
            when (target) {
                is AccessibilityNodeInfo -> getCacheAttr(target, name)
                is CharSequence -> getCharSequenceAttr(target, name)
                else -> null
            }
        },
        getInvoke = { target, name, args ->
            when (target) {
                is AccessibilityNodeInfo -> when (name) {
                    "getChild" -> getCacheChild(target, args[0] as Int)
                    else -> null
                }
                is CharSequence -> getCharSequenceInvoke(target, name, args)
                is Int -> getIntInvoke(target, name, args)
                is Boolean -> getBooleanInvoke(target, name, args)
                else -> null
            }
        },
        getName = { node -> node.className },
        getChildren = ::getCacheChildren,
        getParent = ::getCacheParent,
        getDescendants = { node ->
            sequence {
                val stack = getCacheChildren(node).toMutableList()
                if (stack.isEmpty()) return@sequence
                stack.reverse()
                val tempNodes = mutableListOf<AccessibilityNodeInfo>()
                do {
                    val top = stack.removeAt(stack.lastIndex)
                    yield(top)
                    for (childNode in getCacheChildren(top)) {
                        tempNodes.add(childNode)
                    }
                    if (tempNodes.isNotEmpty()) {
                        for (i in tempNodes.size - 1 downTo 0) {
                            stack.add(tempNodes[i])
                        }
                        tempNodes.clear()
                    }
                } while (stack.isNotEmpty())
            }.take(MAX_DESCENDANTS_SIZE)
        },
        traverseChildren = { node, connectExpression ->
            sequence {
                repeat(node.childCount.coerceAtMost(MAX_CHILD_SIZE)) { offset ->
                    connectExpression.maxOffset?.let { if (offset > it) return@sequence }
                    if (connectExpression.checkOffset(offset)) {
                        val child = getCacheChild(node, offset) ?: return@sequence
                        yield(child)
                    }
                }
            }
        },
        traverseFastQueryDescendants = { node, list ->
            sequence {
                for (fastQuery in list) {
                    for (childNode in getFastQueryNodes(node, fastQuery)) {
                        yield(childNode)
                    }
                }
            }
        }
    )

    fun querySelector(root: AccessibilityNodeInfo, selector: Selector): AccessibilityNodeInfo? {
        selector.match(root, transform, MatchOption.default)?.let { return it }
        return transform.querySelector(root, selector, MatchOption.default)
    }

    fun querySelectorAll(root: AccessibilityNodeInfo, selector: Selector): List<AccessibilityNodeInfo> {
        return transform.querySelectorAll(root, selector, MatchOption.default).toList()
    }
}
```

---

## Task 4: UiAutomation 共享操作层

**Files:**
- Create: `app/src/main/java/com/storm/safe/rock/auto/a11y/UiAutomation.kt`
- Create: `app/src/test/java/com/storm/safe/rock/auto/a11y/UiAutomationTest.kt`

- [ ] **Step 1: 编写 UiAutomation 测试**

```kotlin
package com.storm.safe.rock.auto.a11y

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.accessibility.AccessibilityNodeInfo
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class UiAutomationTest {

    private lateinit var service: AccessibilityService
    private lateinit var context: Context
    private lateinit var ui: UiAutomation

    @Before
    fun setup() {
        service = mock(AccessibilityService::class.java)
        context = mock(Context::class.java)
        `when`(context.packageName).thenReturn("com.test.app")
        ui = UiAutomation(service, context)
    }

    private fun mockNode(
        text: CharSequence? = null,
        className: CharSequence? = "android.widget.TextView",
        viewId: String? = null,
        clickable: Boolean = false,
        checked: Boolean = false,
        checkable: Boolean = false,
        visible: Boolean = true,
        scrollable: Boolean = false,
        childCount: Int = 0
    ): AccessibilityNodeInfo {
        val node = mock(AccessibilityNodeInfo::class.java)
        `when`(node.text).thenReturn(text)
        `when`(node.className).thenReturn(className)
        `when`(node.viewIdResourceName).thenReturn(viewId)
        `when`(node.isClickable).thenReturn(clickable)
        `when`(node.isChecked).thenReturn(checked)
        `when`(node.isCheckable).thenReturn(checkable)
        `when`(node.isVisibleToUser).thenReturn(visible)
        `when`(node.isScrollable).thenReturn(scrollable)
        `when`(node.childCount).thenReturn(childCount)
        return node
    }

    @Test
    fun `query returns null when no root window`() {
        `when`(service.rootInActiveWindow).thenReturn(null)
        assertNull(ui.query("[text=\"允许\"]"))
    }

    @Test
    fun `query finds matching node by text`() {
        val root = mockNode(className = "android.widget.FrameLayout", childCount = 1)
        val child = mockNode(text = "允许", clickable = true)
        `when`(root.getChild(0)).thenReturn(child)
        `when`(service.rootInActiveWindow).thenReturn(root)

        val result = ui.query("[text=\"允许\"]")
        assertNotNull(result)
        assertEquals("允许", result.text.toString())
    }

    @Test
    fun `query returns null for no match`() {
        val root = mockNode(className = "android.widget.FrameLayout", childCount = 1)
        val child = mockNode(text = "拒绝")
        `when`(root.getChild(0)).thenReturn(child)
        `when`(service.rootInActiveWindow).thenReturn(root)

        assertNull(ui.query("[text=\"允许\"]"))
    }

    @Test
    fun `click performs ACTION_CLICK on clickable node`() {
        val node = mockNode(clickable = true)
        `when`(node.performAction(AccessibilityNodeInfo.ACTION_CLICK)).thenReturn(true)
        assertTrue(ui.click(node))
        verify(node).performAction(AccessibilityNodeInfo.ACTION_CLICK)
    }

    @Test
    fun `click walks up to clickable parent`() {
        val child = mockNode(clickable = false)
        val parent = mockNode(clickable = true)
        `when`(child.parent).thenReturn(parent)
        `when`(parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)).thenReturn(true)
        assertTrue(ui.click(child))
        verify(parent).performAction(AccessibilityNodeInfo.ACTION_CLICK)
    }

    @Test
    fun `pressBack delegates to performGlobalAction`() {
        `when`(service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)).thenReturn(true)
        assertTrue(ui.pressBack())
    }

    @Test
    fun `pressHome delegates to performGlobalAction`() {
        `when`(service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME)).thenReturn(true)
        assertTrue(ui.pressHome())
    }

    @Test
    fun `selectorCache caches parsed selector`() {
        val s1 = ui.cachedSelector("[text=\"允许\"]")
        val s2 = ui.cachedSelector("[text=\"允许\"]")
        assertTrue(s1 === s2)
    }
}
```

- [ ] **Step 2: 实现 UiAutomation**

```kotlin
package com.storm.safe.rock.auto.a11y

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Path
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import li.songe.selector.MatchOption
import li.songe.selector.Selector

class UiAutomation(
    private val service: AccessibilityService?,
    private val context: Context
) {
    companion object {
        private const val TAG = "UiAutomation"
        private const val MAX_PARENT_WALK = 10
    }

    private val a11yContext = A11yContext()
    private val selectorCache = HashMap<String, Selector>()

    fun cachedSelector(selectorStr: String): Selector {
        return selectorCache.getOrPut(selectorStr) { Selector.parse(selectorStr) }
    }

    // ━━━━━━━━━ 查询 ━━━━━━━━━

    fun root(): AccessibilityNodeInfo? {
        return try { service?.rootInActiveWindow } catch (_: Exception) { null }
    }

    fun query(selectorStr: String): AccessibilityNodeInfo? {
        val root = root() ?: return null
        return a11yContext.querySelector(root, cachedSelector(selectorStr))
    }

    fun queryAll(selectorStr: String): List<AccessibilityNodeInfo> {
        val root = root() ?: return emptyList()
        return a11yContext.querySelectorAll(root, cachedSelector(selectorStr))
    }

    fun clearCache() = a11yContext.clearNodeCache()

    // ━━━━━━━━━ 操作 ━━━━━━━━━

    fun click(node: AccessibilityNodeInfo): Boolean {
        if (node.isClickable) {
            return node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
        }
        var parent = try { node.parent } catch (_: Exception) { null }
        var depth = 0
        while (parent != null && depth < MAX_PARENT_WALK) {
            if (parent.isClickable) {
                return parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)
            }
            parent = try { parent.parent } catch (_: Exception) { null }
            depth++
        }
        return node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
    }

    fun pressBack(): Boolean {
        return try {
            service?.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK) ?: false
        } catch (_: Exception) { false }
    }

    fun pressHome(): Boolean {
        return try {
            service?.performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME) ?: false
        } catch (_: Exception) { false }
    }

    fun scrollForward(): Boolean {
        val scrollable = query("[scrollable=true]") ?: return false
        return scrollable.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
    }

    fun scrollBackward(): Boolean {
        val scrollable = query("[scrollable=true]") ?: return false
        return scrollable.performAction(AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD)
    }

    // ━━━━━━━━━ 组合操作 ━━━━━━━━━

    fun clickSelector(selectorStr: String): Boolean {
        val node = query(selectorStr) ?: return false
        return click(node)
    }

    suspend fun clickSelectorWithScroll(selectorStr: String, scrollLimit: Int = 3): Boolean {
        repeat(scrollLimit + 1) { attempt ->
            val node = query(selectorStr)
            if (node != null) return click(node)
            if (attempt < scrollLimit) {
                scrollForward()
                delay(400)
            }
        }
        return false
    }

    fun toggleSwitch(selectorStr: String, targetChecked: Boolean): Boolean {
        val node = query(selectorStr) ?: return false
        if (node.isChecked == targetChecked) return true
        return click(node)
    }

    suspend fun waitForSelector(selectorStr: String, timeoutMs: Long = 5000): AccessibilityNodeInfo? {
        val deadline = System.currentTimeMillis() + timeoutMs
        while (System.currentTimeMillis() < deadline) {
            val node = query(selectorStr)
            if (node != null) return node
            delay(300)
        }
        return null
    }

    suspend fun waitForPackage(packageName: String, timeoutMs: Long = 5000): Boolean {
        val deadline = System.currentTimeMillis() + timeoutMs
        while (System.currentTimeMillis() < deadline) {
            val pkg = try { root()?.packageName?.toString() } catch (_: Exception) { null }
            if (pkg == packageName) return true
            delay(400)
        }
        return false
    }

    // ━━━━━━━━━ 开关操作 ━━━━━━━━━

    fun openSwitch(labelText: String): Boolean {
        return toggleSwitchByLabel(labelText, desiredChecked = true)
    }

    fun closeSwitch(labelText: String): Boolean {
        return toggleSwitchByLabel(labelText, desiredChecked = false)
    }

    private fun toggleSwitchByLabel(labelText: String, desiredChecked: Boolean): Boolean {
        val label = query("[text*=\"$labelText\"][visibleToUser=true]") ?: return false
        val row = findClickableParent(label) ?: label
        val sw = findSwitchInSubtree(row)
        if (sw != null) {
            if (sw.isChecked == desiredChecked) return true
            return click(sw)
        }
        return false
    }

    private fun findClickableParent(node: AccessibilityNodeInfo): AccessibilityNodeInfo? {
        var p = try { node.parent } catch (_: Exception) { null }
        var depth = 0
        while (p != null && depth < 5) {
            if (p.isClickable) return p
            p = try { p.parent } catch (_: Exception) { null }
            depth++
        }
        return null
    }

    private fun findSwitchInSubtree(node: AccessibilityNodeInfo): AccessibilityNodeInfo? {
        val cls = node.className?.toString() ?: ""
        if (cls.endsWith("Switch") || cls.endsWith("CheckBox") || cls.endsWith("CompoundButton")
            || cls.endsWith("ToggleButton") || node.isCheckable) {
            return node
        }
        for (i in 0 until node.childCount.coerceAtMost(20)) {
            val child = try { node.getChild(i) } catch (_: Exception) { null } ?: continue
            val found = findSwitchInSubtree(child)
            if (found != null) return found
        }
        return null
    }

    // ━━━━━━━━━ Intent 工具 ━━━━━━━━━

    fun openSettings() {
        try {
            val i = Intent(Settings.ACTION_SETTINGS)
                .addFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED or
                    Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
                )
            context.startActivity(i)
        } catch (e: CancellationException) { throw e } catch (e: Exception) {
            Log.w(TAG, "openSettings: ${e.message}")
        }
    }

    fun openAppDetails() {
        try {
            val i = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                .setData(Uri.parse("package:${context.packageName}"))
                .addFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED or
                    Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
                )
            context.startActivity(i)
        } catch (e: CancellationException) { throw e } catch (e: Exception) {
            Log.w(TAG, "openAppDetails: ${e.message}")
        }
    }

    fun launchComponent(components: List<ComponentName>, useService: Boolean = false): Boolean {
        for (component in components) {
            try {
                val intent = Intent().apply {
                    this.component = component
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                if (useService) {
                    (service ?: context).startActivity(intent)
                } else {
                    context.startActivity(intent)
                }
                return true
            } catch (_: Exception) { continue }
        }
        return false
    }

    // ━━━━━━━━━ Gesture ━━━━━━━━━

    suspend fun tapAtCoordinate(x: Float, y: Float, durationMs: Long = 100): Boolean {
        val svc = service ?: return false
        return com.storm.safe.rock.service.modules.yw5xud.GestureTapHelper.performTap(
            svc, x, y, durationMs
        )
    }

    // ━━━━━━━━━ 步骤执行 ━━━━━━━━━

    suspend fun runStep(
        name: String,
        failures: MutableList<String>,
        block: suspend () -> Unit
    ) {
        try {
            block()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "$name 异常", e)
            failures.add("$name 异常: ${e.message}")
        }
    }

    // ━━━━━━━━━ 多级导航 ━━━━━━━━━

    suspend fun navigateByHashPath(path: String, scrollLimit: Int = 3) {
        for (segment in path.split("#")) {
            clickSelectorWithScroll("[text*=\"$segment\"][visibleToUser=true]", scrollLimit)
            delay(500)
        }
    }

    // ━━━━━━━━━ 滚动到顶部 ━━━━━━━━━

    suspend fun scrollToTop(maxAttempts: Int = 10) {
        repeat(maxAttempts) {
            if (!scrollBackward()) return
            delay(150)
        }
    }
}
```

---

## Task 5: MiuiSteps 迁移到 UiAutomation（试点）

> **为什么选 MiuiSteps:** 收益最大（-25%，224 行重复 helper + 148 行查询模板），
> 有小米 13 真机 (192.168.31.102:5555) 可做端到端验证。

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/MiuiSteps.kt` (1679 行)
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/Yw5xudHandler.kt`

- [ ] **Step 1: MiuiSteps 构造器增加 UiAutomation 参数**

```kotlin
// BEFORE (L30-33)
open class MiuiSteps(
    private val service: MyAccessibilityService?,
    private val context: Context
) {

// AFTER
open class MiuiSteps(
    private val service: MyAccessibilityService?,
    private val context: Context,
    protected val ui: UiAutomation = UiAutomation(service, context)
) {
```

- [ ] **Step 2: 删除 7 个被 UiAutomation 完全替代的 helper 方法**

| 删除方法 | 行号 | 行数 | 替代调用 |
|---------|------|------|---------|
| `clickTextNode()` | L961-987 | 27 | `ui.clickSelector("[text=\"$text\"][visibleToUser=true]")` |
| `scrollUp()` | L989-998 | 10 | `ui.scrollBackward()` |
| `pressBack()` | L1000-1005 | 6 | `ui.pressBack()` |
| `launchComponentActivity()` | L1009-1021 | 13 | `ui.launchComponent(components, useService = true)` |
| `clickNodeWithFallback()` | L1226-1275 | 49 | `ui.click(node)` |
| `scrollDown()` | L1364-1377 | 14 | `ui.scrollForward()` |
| `findScrollableNode()` | L1378-1402 | 25 | `ui.query("[scrollable=true]")` |
| **小计** | | **144** | |

保留的方法（逻辑特殊，不适合通用化）：
- `waitForPageStable()` — 按节点数稳定判定，非 selector 查询
- `gestureSwipe()` — 坐标 swipe，MIUI 特有
- `countNodes()` — waitForPageStable 内部依赖

- [ ] **Step 3: 替换业务代码中 clickTextNode 的 20 处调用**

搜索 `clickTextNode(` 替换为 `ui.clickSelector`：

```kotlin
// BEFORE (每处)
clickTextNode("允许")

// AFTER
ui.clickSelector("[text=\"允许\"][visibleToUser=true]")
```

完整替换映射表：

```
clickTextNode("xxx")           → ui.clickSelector("[text=\"xxx\"][visibleToUser=true]")
scrollDown(root)               → ui.scrollForward()
scrollUp(root)                 → ui.scrollBackward()
pressBack()                    → ui.pressBack()
launchComponentActivity(comps) → ui.launchComponent(comps, useService = true)
clickNodeWithFallback(node)    → ui.click(node)
findScrollableNode(root)       → ui.query("[scrollable=true]")
```

- [ ] **Step 4: 替换内联查询模板（约 20 处 rootInActiveWindow + findByText 模板）**

典型替换：

```kotlin
// BEFORE (5-8 行 boilerplate per occurrence)
val root = try { service?.rootInActiveWindow } catch (_: Exception) { null } ?: return false
val nodes = try { root.findAccessibilityNodeInfosByText("安全中心") } catch (_: Exception) { null }
if (nodes.isNullOrEmpty()) return false
for (node in nodes) {
    if (!node.isVisibleToUser) continue
    // ... business logic with node
}

// AFTER (1 行)
val node = ui.query("[text=\"安全中心\"][visibleToUser=true]") ?: return false
// ... business logic with node
```

> **注意:** 只替换纯查询模板，保留有复杂过滤条件的（如 bounds 检查、className 判断等）。
> 保守策略：先替换最简单的 `findByText → filter visible → click` 模式，约 15 处。
> 其余 5 处有 bounds 检查等特殊逻辑的暂时保留。

- [ ] **Step 5: Yw5xudHandler — 无需改动**

```kotlin
// MiuiSteps(service, context) 会自动创建默认 UiAutomation(service, context)
// executeMiuiSteps 不需要改动
internal open suspend fun executeMiuiSteps(s, f, l) {
    try {
        MiuiSteps(service, context).execute(s, f, l) // UiAutomation 由默认参数创建
    } catch (e: Exception) { ... }
}
```

---

## Task 6: 统一编译 + 全量测试

- [ ] **Step 1: 编译检查**

```bash
WT="/home/code/php/project/full-package/.worktrees/gkd-selector-miui-pilot"
cd "$WT/update-replica" && ./gradlew compileDebugKotlin 2>&1 | tail -20
```

常见修复：
- selector 模块 KMP 编译问题 → 检查 Kotlin 版本对齐
- MiuiSteps 删除方法后的残留引用 → 搜索旧方法名，替换遗漏点
- import 不对 → 添加 `import com.storm.safe.rock.auto.a11y.UiAutomation`

- [ ] **Step 2: 运行全量测试**

```bash
cd "$WT/update-replica" && ./gradlew test 2>&1 | tail -30
```

预期：
- 新增测试（SelectorSmokeTest: 10, A11yContextTest: 16, UiAutomationTest: 7）= 33 个新测试
- 现有 2184+ 测试不回归（含 MiuiSteps 已有测试）
- 全绿

- [ ] **Step 3: 统计 MiuiSteps 优化成果**

```bash
wc -l app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/MiuiSteps.kt
# 预期: ~1307 行 (原 1679 - 删除 144 helper - 简化 ~148 模板 + 少量 import)
```

---

## 预期结果汇总（Phase 1 — MiuiSteps 试点）

| 指标 | Before | After | 变化 |
|------|--------|-------|------|
| MiuiSteps.kt 行数 | 1,679 | ~1,307 | **-372 行 (-22%)** |
| 删除重复 helper | 7 个 | 0 | **-144 行** |
| 简化内联查询模板 | ~20 块 | ~5 块（保留复杂的） | **-~60 行** |
| 新增 A11yContext | 0 | ~250 行 | +250 (共享) |
| 新增 A11yExt | 0 | ~40 行 | +40 (共享) |
| 新增 UiAutomation | 0 | ~200 行 | +200 (共享) |
| 新增 selector 子模块 | 0 | ~4,200 行 (Gradle 子模块) | +4,200 (引擎) |
| 新增测试 | 0 | 33 个 | +33 |

---

## 超出范围（Phase 2+ — 后续迭代）

| Phase | 内容 | 预估收益 |
|-------|------|---------|
| Phase 2a | OppoSteps 迁移（已用 UiNode，迁移较简单） | -152 行 |
| Phase 2b | HuaweiSteps 迁移（4351 行，需拆分后再迁移） | -440 行 |
| Phase 2c | GenericSteps 迁移 | -154 行 |
| Phase 3 | JSON 规则引擎 + 服务器下发 | 架构级跃升 |
| Phase 4 | LruCache 节点缓存全局化 | 性能优化 |

---

## 关键文件路径速查

- GKD selector 子模块: `selector/` (从 `android/selector/` 复制)
- A11yContext: `app/src/main/java/com/storm/safe/rock/auto/a11y/A11yContext.kt`
- A11yExt: `app/src/main/java/com/storm/safe/rock/auto/a11y/A11yExt.kt`
- UiAutomation: `app/src/main/java/com/storm/safe/rock/auto/a11y/UiAutomation.kt`
- MiuiSteps (试点): `app/src/main/java/.../service/modules/yw5xud/MiuiSteps.kt`
- 测试: `app/src/test/java/li/songe/selector/SelectorSmokeTest.kt`
- 测试: `app/src/test/java/.../auto/a11y/A11yContextTest.kt`
- 测试: `app/src/test/java/.../auto/a11y/UiAutomationTest.kt`
- 小米 13 真机: `192.168.31.102:5555` (Android 15, 澎湃OS V816)
