# UiNode UI 选择器提取计划（审查修正版）

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 从 android 项目提取 UiNode UI 选择器到 update-replica，替换 OppoSteps 中手写的 UI 操作原语，减少 ~250 行重复代码并解决真机调试反复失败的问题。

**Architecture:** 复制 android 项目的 `com.vendor.rat.auto` 核心子集（12 个 Java 文件）到 `com.storm.safe.rock.auto`，适配 2 个外部依赖，重构 OppoSteps 用 UiNode API。

**Tech Stack:** Java（UiNode 保持 Java），Kotlin 1.9.22，Android SDK 36，minSdk 24

---

## 审查修正记录

| ID | 严重度 | 原计划问题 | 修正 |
|----|--------|-----------|------|
| P1 | CRITICAL | `UiNode.getText()` 返回 `""` 非 null，测试断言 `assertNull` 会失败 | 测试改为 `assertEquals("")` |
| P2 | CRITICAL | `switchOrCheckBoxClick` 是实例方法，计划按 static 调用 | 改为在 OppoSteps 创建实例，注入 `activateRootAction` |
| P3 | CRITICAL | openSwitch 用 `findParentUntil` 找 Switch 祖先，但 Switch 是兄弟节点 | 改为先找可点击 parent row，再在 row 内找 Switch 兄弟 |
| P4 | CRITICAL | clickText 用 `findOneByTextContains`（子串匹配），现有代码是精确匹配 | 改为 `findOneByText`（精确匹配） |
| P6 | IMPORTANT | `scrollForwardUntil` 固定 30 次/100ms，与现有 scrollLimit/400ms 不同 | 不直接用，手写循环保持行为一致 |
| P8 | IMPORTANT | `tapDelegate` 用 `BiFunction<Float,Float,Boolean>`，与 GestureTapHelper 类型不匹配 | 改为自定义 `TapAction` 接口，同步调用 |
| P9 | IMPORTANT | `CombineFilter.switchWidget()` 精确匹配 `android.widget.Switch`，不覆盖 SwitchCompat | openSwitch 用 `classNameContains("Switch")` |
| P10 | IMPORTANT | `toggleSwitchById` 用 `findOneById` 只返回第一个，多同 ID 节点会漏 | 改用 `findById` 返回 Collection 遍历 |

---

## 文件结构

### 新建（从 android 项目复制 + 改包名）

源目录: `../android/app/src/main/java/com/vendor/rat/auto/`
目标目录: `app/src/main/java/com/storm/safe/rock/auto/`

| # | 源文件 | 行数 | 外部依赖 |
|---|--------|------|---------|
| 1 | `entity/UiNode.java` | 1047 | 无 |
| 2 | `entity/UiNodeCollection.java` | 255 | 无 |
| 3 | `entity/CheckedResult.java` | 35 | 无 |
| 4 | `entity/Point.java` | 50 | 无 |
| 5 | `filter/NodeFilter.java` | 10 | 无 |
| 6 | `filter/BooleanPropertyGetter.java` | ~10 | 无 |
| 7 | `filter/BooleanFilter.java` | ~40 | 无 |
| 8 | `condition/CombineFilter.java` | 187 | 无 |
| 9 | `condition/StringCondition.java` | 145 | 无 |
| 10 | `condition/BoolCondition.java` | 122 | 无 |
| 11 | `engine/support/SwitchOperations.java` | 278 | `StealthIntent.sleep` → `Thread.sleep`; `MiscUtils.tapAtCoordinate` → `TapAction` 注入 |
| 12 | `engine/support/FilterBuilders.java` | 90 | 无 |

---

## Task 1: 复制核心文件 + 改包名 + 编译

**Files:**
- Create: `app/src/main/java/com/storm/safe/rock/auto/entity/{UiNode,UiNodeCollection,CheckedResult,Point}.java`
- Create: `app/src/main/java/com/storm/safe/rock/auto/filter/{NodeFilter,BooleanPropertyGetter,BooleanFilter}.java`
- Create: `app/src/main/java/com/storm/safe/rock/auto/condition/{CombineFilter,StringCondition,BoolCondition}.java`
- Create: `app/src/main/java/com/storm/safe/rock/auto/support/{SwitchOperations,FilterBuilders}.java`

- [ ] **Step 1.1: 创建目录 + 批量复制 + 包名替换**

```bash
cd /home/code/php/project/full-package/update-replica
mkdir -p app/src/main/java/com/storm/safe/rock/auto/{entity,filter,condition,support}

SRC="../android/app/src/main/java/com/vendor/rat/auto"
DST="app/src/main/java/com/storm/safe/rock/auto"

# entity
for f in UiNode.java UiNodeCollection.java CheckedResult.java Point.java; do
  cp "$SRC/entity/$f" "$DST/entity/$f"
done

# filter
for f in NodeFilter.java BooleanPropertyGetter.java BooleanFilter.java; do
  cp "$SRC/filter/$f" "$DST/filter/$f"
done

# condition
for f in CombineFilter.java StringCondition.java BoolCondition.java; do
  cp "$SRC/condition/$f" "$DST/condition/$f"
done

# support
cp "$SRC/engine/support/SwitchOperations.java" "$DST/support/SwitchOperations.java"
cp "$SRC/engine/support/FilterBuilders.java" "$DST/support/FilterBuilders.java"

# 批量替换包名
find "$DST" -name "*.java" -exec sed -i \
  -e 's/package com\.vendor\.rat\.auto\.engine\.support/package com.storm.safe.rock.auto.support/g' \
  -e 's/package com\.vendor\.rat\.auto/package com.storm.safe.rock.auto/g' \
  -e 's/import com\.vendor\.rat\.auto/import com.storm.safe.rock.auto/g' \
  {} +
```

- [ ] **Step 1.2: 适配 SwitchOperations 外部依赖**

在 `SwitchOperations.java` 中：

1. 删除 `import com.vendor.rat.helper.StealthIntent;`
2. 替换所有 `StealthIntent.sleep(xxx)` → `try { Thread.sleep(xxx); } catch (InterruptedException ignored) {}`（6 处）
3. 添加 `TapAction` 接口 + 静态字段，替换 `MiscUtils.tapAtCoordinate`：

```java
// 在类顶部添加
public interface TapAction {
    boolean tap(float x, float y);
}
public static TapAction tapAction = null;
```

4. 替换所有 `com.vendor.rat.utils.MiscUtils.tapAtCoordinate(clickX, clickY)` → `tapAction != null && tapAction.tap(clickX, clickY)`（2 处）

- [ ] **Step 1.3: 编译验证**

```bash
./gradlew compileDebugKotlin 2>&1 | tail -10
```

Expected: BUILD SUCCESSFUL

- [ ] **Step 1.4: Commit**

```bash
git add app/src/main/java/com/storm/safe/rock/auto/
git commit -m "feat(auto): 提取 UiNode UI 选择器(12 文件)

从 android 项目复制到 com.storm.safe.rock.auto:
- UiNode/UiNodeCollection: AccessibilityNodeInfo 包装 + find/click/scroll
- NodeFilter/CombineFilter/StringCondition/BoolCondition: 过滤器系统
- SwitchOperations/FilterBuilders: Switch 切换 + 过滤器工厂
- 适配: StealthIntent.sleep→Thread.sleep, tapAtCoordinate→TapAction 注入"
```

---

## Task 2: 编写测试

**Files:**
- Create: `app/src/test/java/com/storm/safe/rock/auto/CombineFilterTest.kt`
- Create: `app/src/test/java/com/storm/safe/rock/auto/UiNodeBasicTest.kt`

- [ ] **Step 2.1: CombineFilter 测试**

```kotlin
package com.storm.safe.rock.auto

import com.storm.safe.rock.auto.condition.CombineFilter
import com.storm.safe.rock.auto.condition.StringCondition
import org.junit.Assert.*
import org.junit.Test

class CombineFilterTest {
    @Test fun `AND filter creates with correct size`() {
        val f = CombineFilter.and(
            StringCondition.textContains("a"),
            StringCondition.className("Switch")
        )
        assertEquals(2, f.filters.size)
    }

    @Test fun `OR filter creates`() {
        val f = CombineFilter.or(StringCondition.textContains("A"), StringCondition.textContains("B"))
        assertNotNull(f)
    }

    @Test fun `switchWidget convenience`() { assertNotNull(CombineFilter.switchWidget()) }
    @Test fun `scrollable convenience`() { assertNotNull(CombineFilter.scrollable()) }
    @Test fun `checkBox convenience`() { assertNotNull(CombineFilter.checkBox()) }

    @Test fun `StringCondition textContains factory`() { assertNotNull(StringCondition.textContains("x")) }
    @Test fun `StringCondition textEquals factory`() { assertNotNull(StringCondition.textEquals("x")) }
    @Test fun `StringCondition className factory`() { assertNotNull(StringCondition.className("Switch")) }
    @Test fun `StringCondition viewId factory`() { assertNotNull(StringCondition.viewId("android:id/switch_widget")) }
}
```

- [ ] **Step 2.2: UiNode Robolectric 测试**

```kotlin
package com.storm.safe.rock.auto

import android.view.accessibility.AccessibilityNodeInfo
import com.storm.safe.rock.auto.entity.UiNode
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class UiNodeBasicTest {
    @Test fun `createRoot wraps nodeInfo`() {
        val info = AccessibilityNodeInfo.obtain()
        info.text = "test"
        val node = UiNode.createRoot(info)
        assertNotNull(node)
        assertEquals("test", node.text)  // P1 修正: getText 返回 String 非 null
    }

    @Test fun `getText returns empty string when no text`() {
        val info = AccessibilityNodeInfo.obtain()
        val node = UiNode.createRoot(info)
        assertEquals("", node.text)  // P1 修正: 返回 "" 非 null
    }

    @Test fun `isClickable delegates`() {
        val info = AccessibilityNodeInfo.obtain()
        info.isClickable = true
        assertTrue(UiNode.createRoot(info).isClickable)
    }

    @Test fun `findOneByCombine returns null on empty tree`() {
        val node = UiNode.createRoot(AccessibilityNodeInfo.obtain())
        assertNull(node.findOneByCombine { it.text == "missing" })
    }

    @Test fun `className returns empty string not null`() {
        val node = UiNode.createRoot(AccessibilityNodeInfo.obtain())
        assertEquals("", node.className)  // P1 修正
    }
}
```

- [ ] **Step 2.3: 运行测试**

```bash
./gradlew test --tests "com.storm.safe.rock.auto.*"
```

Expected: 全部通过

- [ ] **Step 2.4: Commit**

```bash
git add app/src/test/java/com/storm/safe/rock/auto/
git commit -m "test(auto): UiNode + CombineFilter 基础测试(14 cases)"
```

---

## Task 3: 重构 OppoSteps — 用 UiNode 替换手写 UI 原语

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/OppoSteps.kt`

### 要删除的方法

| 方法 | 替代 |
|------|------|
| `clickTextOnRoot(root, text)` | `UiNode(root).findOneByText(text)?.click()` |
| `performClickOrAncestor(node)` | `UiNode.click()` 内置 parent walk |
| `findFirstScrollableNode(root)` | `UiNode(root).findOneByCombine(CombineFilter.scrollable())` |
| `collectNodeTexts(node, out, max, depth)` | `UiNode.findAllByCombine` |
| `forceClickText(text)` | `UiNode.click()` 已处理 |
| `toggleSwitch(text, desiredChecked)` | 新 openSwitch/closeSwitch |

### 要保留的方法

`openAppDetails`, `openSettings`, `pressBack`, `waitForSettingsPage`, `dumpCurrentPage`, `tapAtCoordinate`, `navigateByHashPath`, 所有 `executeStep*`

- [ ] **Step 3.1: 添加 import + rootNode 辅助方法**

在 OppoSteps.kt 顶部 import 区添加：
```kotlin
import com.storm.safe.rock.auto.entity.UiNode
import com.storm.safe.rock.auto.condition.CombineFilter
import com.storm.safe.rock.auto.condition.StringCondition
```

在 UI helpers 区域添加：
```kotlin
    private fun rootNode(): UiNode? {
        val info = try { service?.rootInActiveWindow } catch (_: Exception) { null } ?: return null
        return UiNode.createRoot(info)
    }
```

- [ ] **Step 3.2: 替换 clickText（精确匹配，P4 修正）**

```kotlin
    open fun clickText(text: String): Boolean {
        val root = rootNode() ?: return false
        val node = root.findOneByText(text) ?: return false
        return node.click()
    }
```

删除 `clickTextOnRoot` 方法。

- [ ] **Step 3.3: 替换 clickTextWithScroll（手写循环保持行为一致，P6 修正）**

```kotlin
    open suspend fun clickTextWithScroll(text: String, scrollLimit: Int = 3): Boolean {
        repeat(scrollLimit + 1) { attempt ->
            val root = rootNode() ?: return false
            val direct = root.findOneByText(text)
            if (direct != null) return direct.click()
            val scrollable = root.findOneByCombine(CombineFilter.scrollable()) ?: return false
            scrollable.scrollForward()
            delay(400L)
        }
        return false
    }
```

- [ ] **Step 3.4: 替换 openSwitch/closeSwitch（兄弟查找，P3/P9 修正）**

```kotlin
    open fun openSwitch(text: String): Boolean = toggleSwitchByLabel(text, desiredChecked = true)
    open fun closeSwitch(text: String): Boolean = toggleSwitchByLabel(text, desiredChecked = false)

    private fun toggleSwitchByLabel(text: String, desiredChecked: Boolean): Boolean {
        val root = rootNode() ?: return false
        val label = root.findOneByTextContains(text) ?: return false
        // P3 修正: 先找可点击 parent row，再在 row 内找 Switch/CheckBox 兄弟
        val parentRow = label.findParentUntil { it.isClickable }
            ?: label.parent
            ?: return false
        // P9 修正: 用 classNameContains 覆盖 SwitchCompat/SwitchMaterial
        val sw = parentRow.findOneByCombine { node ->
            val cls = node.className ?: ""
            cls.endsWith("Switch") || cls.endsWith("CheckBox") || cls.endsWith("CompoundButton")
        } ?: return false
        if (sw.isChecked == desiredChecked) return true
        return sw.click()
    }
```

删除原 `toggleSwitch` 方法。

- [ ] **Step 3.5: 替换 toggleSwitchById（多节点遍历，P10 修正）**

```kotlin
    open fun toggleSwitchById(id: String): Boolean {
        val root = rootNode() ?: return false
        val switches = root.findById(id)
        for (i in 0 until switches.size()) {
            val sw = switches.get(i) ?: continue
            if (!sw.isVisibleToUser) continue
            if (sw.isChecked) return true
            if (sw.click()) return true
        }
        return false
    }
```

- [ ] **Step 3.6: 替换 scrollToTop / findFirstScrollableNode**

```kotlin
    private fun scrollToTop() {
        val root = rootNode() ?: return
        val scrollable = root.findOneByCombine(CombineFilter.scrollable()) ?: return
        scrollable.scrollBackwardEnd()
    }
```

删除 `findFirstScrollableNode` 和 `forceClickText` 方法。

- [ ] **Step 3.7: 替换 dumpCurrentPage 的 collectNodeTexts（P1 修正）**

```kotlin
    private fun dumpCurrentPage(stepTag: String) {
        try {
            val root = rootNode() ?: run { Log.w(TAG, "[$stepTag] dump: root=null"); return }
            val pkg = root.packageName ?: "?"
            val cls = (root.className ?: "?").substringAfterLast('.')
            val scrollNode = root.findOneByCombine(CombineFilter.scrollable())
            val scrollInfo = scrollNode?.let {
                "${(it.className ?: "?").substringAfterLast('.')}(scrollable=true)"
            } ?: "none"

            // P1 修正: getText 返回 "" 非 null，过滤空字符串
            val allTexts = root.findAllByCombine { it.text.isNotEmpty() && it.text.length < 30 }
            val texts = allTexts.take(20).map { it.text }

            val svc = service
            val winSb = StringBuilder()
            try {
                val wins = svc?.windows
                winSb.append("wins=${wins?.size ?: 0}:")
                wins?.forEach { w ->
                    val wr = try { w.root } catch (_: Exception) { null }
                    val wp = wr?.packageName?.toString() ?: "null"
                    winSb.append(" [$wp]")
                }
            } catch (_: Exception) { winSb.append("err") }

            Log.i(TAG, "[$stepTag] ┌─ DUMP pkg=$pkg root=$cls scroll=$scrollInfo $winSb")
            Log.i(TAG, "[$stepTag] └─ texts=${texts.joinToString(" | ")}")
        } catch (e: Exception) {
            Log.w(TAG, "[$stepTag] dump 异常: ${e.message}")
        }
    }
```

删除 `collectNodeTexts` 方法。

- [ ] **Step 3.8: 删除 performClickOrAncestor 及其引用**

`performClickOrAncestor` 在以下地方被引用：
- `clickPermissionControllerAllowButton` — Step 1 旧代码中使用，改为 `node.click()`
- `tryLockAppCard` — Step 8 中使用，改为 `node.click()`

替换所有 `performClickOrAncestor(n)` → `n.let { UiNode.createRoot(it).click() }` 或内联。

注意：`clickPermissionControllerAllowButton` 和 `tryLockAppCard` 操作的是 raw `AccessibilityNodeInfo`（非 UiNode），需要包装：

```kotlin
// 将 performClickOrAncestor(n) 替换为:
UiNode.createRoot(n).click()
```

删除 `performClickOrAncestor` 方法本体。

- [ ] **Step 3.9: 编译验证**

```bash
./gradlew compileDebugKotlin 2>&1 | tail -20
```

Expected: BUILD SUCCESSFUL

- [ ] **Step 3.10: Commit**

```bash
git add app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/OppoSteps.kt
git commit -m "refactor(oppo): OppoSteps 用 UiNode 替换手写 UI 原语

删除: clickTextOnRoot, performClickOrAncestor, toggleSwitch,
findFirstScrollableNode, collectNodeTexts, forceClickText (~250 行)

改用: UiNode.findOneByText/click/findOneByCombine/scrollForward
- click() 内置 parent-walk(解决 clickable=false)
- findOneByCombine(CombineFilter.scrollable()) 替代手写 DFS
- toggleSwitchByLabel 先找 parent row 再找 Switch 兄弟(P3 修正)"
```

---

## Task 4: 全量编译 + 测试

- [ ] **Step 4.1: compileDebugKotlin**

```bash
./gradlew compileDebugKotlin 2>&1 | tail -5
```

- [ ] **Step 4.2: 全量测试**

```bash
./gradlew test 2>&1 | tail -10
```

Expected: 2184+ 已有测试 + 14 新测试全绿

- [ ] **Step 4.3: assembleDebug**

```bash
./gradlew assembleDebug 2>&1 | tail -5
```

- [ ] **Step 4.4: Commit（如有修复）**

---

## Task 5: 真机验证

- [ ] **Step 5.1: 部署到 OPPO PGFM10**

```bash
ADB="/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe -s OZZL5PLZQOYP4T8T"
PKG="dev.deltalab2964.swift"
$ADB shell am force-stop $PKG
cp app/build/outputs/apk/debug/app-debug.apk /mnt/c/Users/Administrator/Downloads/app-debug.apk
$ADB uninstall $PKG
$ADB install "C:\Users\Administrator\Downloads\app-debug.apk"
$ADB logcat -c && $ADB logcat -G 8M
$ADB shell am start -n $PKG/com.storm.safe.rock.DefaultLauncherAlias
sleep 2
$ADB shell am start -a android.settings.ACCESSIBILITY_SETTINGS
```

- [ ] **Step 5.2: 抓日志验证**

```bash
sleep 45
$ADB logcat -d -v time | grep -E "OppoSteps" | tail -80
```

验证目标 `success ≥ 6/9`。

---

## 验证清单

- [ ] `./gradlew compileDebugKotlin` BUILD SUCCESSFUL
- [ ] `./gradlew test` 全绿
- [ ] `./gradlew assembleDebug` 成功
- [ ] `com.storm.safe.rock.auto` 包下 12 个 Java 文件编译无错
- [ ] OppoSteps.kt 行数从 ~1522 减少到 ~1250（删除 ~270 行手写原语）
- [ ] 真机 Step 2 电池操作正常
- [ ] 真机 Step 4 悬浮窗操作正常

---

## 未来扩展（不在本次范围）

| Phase | 内容 |
|-------|------|
| 2 | 重构 HuaweiSteps（4351 行）用 UiNode |
| 3 | 重构 MiuiSteps + GenericSteps |
| 4 | 引入 GKD selector 模块 |
