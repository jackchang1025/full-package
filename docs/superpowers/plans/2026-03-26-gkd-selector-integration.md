# GKD Selector 集成与 OPPO 权限自动化优化实施计划

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 将 GKD Selector 引擎集成到项目中，并用其重构 OppoPermissionEngine 的节点匹配逻辑，提升代码可维护性和规则外部化能力。

**Architecture:**
- 提取 GKD 的 `selector` 模块作为独立依赖
- 创建 AccessibilityNodeInfo 到 GKD Transform 的适配层
- 重构 OppoPermissionEngine 使用 GKD 选择器替代 CombineFilter
- 保持现有 Engine 架构不变，仅替换节点匹配层

**Tech Stack:**
- GKD Selector (Kotlin Multiplatform)
- Kotlin 1.9+
- Android AccessibilityService
- Gradle 8.5

---

## 文件结构规划

### 新增文件
- `android/selector/` - GKD selector 模块（从 gkd 项目复制）
- `android/app/src/main/java/com/vendor/rat/auto/selector/GkdTransform.kt` - AccessibilityNodeInfo 适配器
- `android/app/src/main/java/com/vendor/rat/auto/selector/GkdSelectorHelper.kt` - GKD 选择器辅助类
- `android/app/src/test/java/com/vendor/rat/auto/selector/GkdTransformTest.kt` - Transform 单元测试
- `android/app/src/test/java/com/vendor/rat/auto/selector/GkdSelectorHelperTest.kt` - Helper 单元测试

### 修改文件
- `android/settings.gradle` - 添加 selector 模块
- `android/app/build.gradle` - 添加 selector 依赖
- `android/app/src/main/java/com/vendor/rat/auto/engine/vendor/OppoPermissionEngine.java` - 重构为使用 GKD Selector

---

## Task 1: 提取 GKD Selector 模块

**Files:**
- Create: `android/selector/build.gradle.kts`
- Create: `android/selector/src/commonMain/kotlin/li/songe/selector/` (复制整个目录)
- Modify: `android/settings.gradle`

- [ ] **Step 1: 复制 GKD selector 模块**

```bash
cp -r gkd/selector android/
```

- [ ] **Step 2: 简化 build.gradle.kts**

创建 `android/selector/build.gradle.kts`:

```kotlin
plugins {
    id("com.android.library")
    kotlin("multiplatform")
}

kotlin {
    androidTarget()
    sourceSets {
        val commonMain by getting
    }
}

android {
    namespace = "li.songe.selector"
    compileSdk = 35
    defaultConfig { minSdk = 24 }
}
```

- [ ] **Step 3: 添加到 settings.gradle**

```groovy
include ':selector'
```

- [ ] **Step 4: 验证编译**

```bash
./gradlew :selector:build
```

- [ ] **Step 5: Commit**

```bash
git add android/selector android/settings.gradle
git commit -m "feat: add GKD selector module"
```

---

## Task 2: 创建 GKD Transform 适配层

**Files:**
- Create: `android/app/src/main/java/com/vendor/rat/auto/selector/GkdTransform.kt`
- Create: `android/app/src/test/java/com/vendor/rat/auto/selector/GkdTransformTest.kt`

- [ ] **Step 1: 添加 selector 依赖**

修改 `android/app/build.gradle`:

```groovy
dependencies {
    implementation project(':selector')
    // ... 其他依赖
}
```

- [ ] **Step 2: 编写 Transform 测试**

创建 `android/app/src/test/java/com/vendor/rat/auto/selector/GkdTransformTest.kt`:

```kotlin
package com.vendor.rat.auto.selector

import android.view.accessibility.AccessibilityNodeInfo
import org.junit.Test
import org.junit.Assert.*
import org.mockito.Mockito.*

class GkdTransformTest {
    @Test
    fun getName_returnsClassName() {
        val node = mock(AccessibilityNodeInfo::class.java)
        `when`(node.className).thenReturn("android.widget.Button")
        
        val transform = GkdTransform()
        assertEquals("android.widget.Button", transform.getName(node))
    }
}
```

- [ ] **Step 3: 运行测试验证失败**

```bash
./gradlew test --tests GkdTransformTest
```

Expected: FAIL (GkdTransform not found)

- [ ] **Step 4: 实现 GkdTransform**

创建 `android/app/src/main/java/com/vendor/rat/auto/selector/GkdTransform.kt`:

```kotlin
package com.vendor.rat.auto.selector

import android.graphics.Rect
import android.view.accessibility.AccessibilityNodeInfo
import li.songe.selector.Transform

class GkdTransform : Transform<AccessibilityNodeInfo> {
    override fun getName(node: AccessibilityNodeInfo): CharSequence {
        return node.className ?: ""
    }

    override fun getAttr(node: AccessibilityNodeInfo, name: String): CharSequence? {
        return when (name) {
            "text" -> node.text
            "desc" -> node.contentDescription
            "id" -> node.viewIdResourceName
            "clickable" -> node.isClickable.toString()
            "focusable" -> node.isFocusable.toString()
            "checked" -> node.isChecked.toString()
            "enabled" -> node.isEnabled.toString()
            else -> null
        }
    }

    override fun getParent(node: AccessibilityNodeInfo): AccessibilityNodeInfo? {
        return node.parent
    }

    override fun getChildren(node: AccessibilityNodeInfo): Sequence<AccessibilityNodeInfo> {
        return sequence {
            for (i in 0 until node.childCount) {
                node.getChild(i)?.let { yield(it) }
            }
        }
    }
}
```

- [ ] **Step 5: 运行测试验证通过**

```bash
./gradlew test --tests GkdTransformTest
```

Expected: PASS

- [ ] **Step 6: Commit**

```bash
git add android/app/build.gradle android/app/src/main/java/com/vendor/rat/auto/selector/GkdTransform.kt android/app/src/test/java/com/vendor/rat/auto/selector/GkdTransformTest.kt
git commit -m "feat: add GKD Transform adapter for AccessibilityNodeInfo"
```

---

## Task 3: 创建 GKD Selector 辅助类

**Files:**
- Create: `android/app/src/main/java/com/vendor/rat/auto/selector/GkdSelectorHelper.kt`
- Create: `android/app/src/test/java/com/vendor/rat/auto/selector/GkdSelectorHelperTest.kt`

- [ ] **Step 1: 编写 Helper 测试**

```kotlin
package com.vendor.rat.auto.selector

import org.junit.Test
import org.junit.Assert.*

class GkdSelectorHelperTest {
    @Test
    fun parse_validSelector_returnsSelector() {
        val selector = GkdSelectorHelper.parse("[text='允许']")
        assertNotNull(selector)
    }
}
```

- [ ] **Step 2: 运行测试验证失败**

```bash
./gradlew test --tests GkdSelectorHelperTest
```

- [ ] **Step 3: 实现 GkdSelectorHelper**

```kotlin
package com.vendor.rat.auto.selector

import android.view.accessibility.AccessibilityNodeInfo
import li.songe.selector.MatchOption
import li.songe.selector.Selector

object GkdSelectorHelper {
    private val transform = GkdTransform()
    private val defaultOption = MatchOption()

    fun parse(source: String): Selector {
        return Selector.parse(source)
    }

    fun match(
        root: AccessibilityNodeInfo?,
        selector: String
    ): AccessibilityNodeInfo? {
        if (root == null) return null
        val sel = parse(selector)
        return sel.match(root, transform, defaultOption)
    }

    fun matchAll(
        root: AccessibilityNodeInfo?,
        selector: String
    ): List<AccessibilityNodeInfo> {
        if (root == null) return emptyList()
        val sel = parse(selector)
        val result = sel.matchContext(root, transform, defaultOption)
        return result.tracks.map { it.node }
    }
}
```

- [ ] **Step 4: 运行测试验证通过**

```bash
./gradlew test --tests GkdSelectorHelperTest
```

- [ ] **Step 5: Commit**

```bash
git add android/app/src/main/java/com/vendor/rat/auto/selector/GkdSelectorHelper.kt android/app/src/test/java/com/vendor/rat/auto/selector/GkdSelectorHelperTest.kt
git commit -m "feat: add GKD Selector helper class"
```

---

## Task 4: 重构 OppoPermissionEngine 使用 GKD Selector

**Files:**
- Modify: `android/app/src/main/java/com/vendor/rat/auto/engine/vendor/OppoPermissionEngine.java`
- Create: `android/app/src/test/java/com/vendor/rat/auto/engine/vendor/OppoPermissionEngineTest.java`

- [ ] **Step 1: 编写集成测试**

```java
package com.vendor.rat.auto.engine.vendor;

import org.junit.Test;
import static org.junit.Assert.*;

public class OppoPermissionEngineTest {
    @Test
    public void testGkdSelectorIntegration() {
        // 测试 GKD Selector 是否正确集成
        OppoPermissionEngine engine = new OppoPermissionEngine();
        assertNotNull(engine);
    }
}
```

- [ ] **Step 2: 运行测试**

```bash
./gradlew test --tests OppoPermissionEngineTest
```

- [ ] **Step 3: 重构 OppoPermissionEngine - 添加 GKD 支持**

在 `OppoPermissionEngine.java` 顶部添加导入：

```java
import com.vendor.rat.auto.selector.GkdSelectorHelper;
import android.view.accessibility.AccessibilityNodeInfo;
```

- [ ] **Step 4: 替换第一个节点匹配逻辑**

找到使用 CombineFilter 的地方，例如：

```java
// 旧代码
CombineFilter filter = new CombineFilter()
    .add(new StringCondition("text", "允许"));
UiNode node = findNode(root, filter);
```

替换为：

```java
// 新代码 - 使用 GKD Selector
AccessibilityNodeInfo node = GkdSelectorHelper.match(
    root, 
    "[text='允许'][clickable=true]"
);
```

- [ ] **Step 5: 运行测试验证**

```bash
./gradlew test --tests OppoPermissionEngineTest
```

- [ ] **Step 6: Commit**

```bash
git add android/app/src/main/java/com/vendor/rat/auto/engine/vendor/OppoPermissionEngine.java android/app/src/test/java/com/vendor/rat/auto/engine/vendor/OppoPermissionEngineTest.java
git commit -m "refactor: integrate GKD Selector into OppoPermissionEngine"
```

---

## Task 5: 端到端测试与验证

**Files:**
- Create: `android/scripts/test_gkd_integration.sh`

- [ ] **Step 1: 创建测试脚本**

```bash
#!/bin/bash
# 端到端测试脚本

echo "=== GKD Selector 集成测试 ==="

echo "1. 编译 selector 模块..."
./gradlew :selector:build || exit 1

echo "2. 运行单元测试..."
./gradlew test --tests "com.vendor.rat.auto.selector.*" || exit 1

echo "3. 编译 app..."
./gradlew :app:assembleDebug || exit 1

echo "✅ 所有测试通过"
```

- [ ] **Step 2: 运行测试脚本**

```bash
chmod +x android/scripts/test_gkd_integration.sh
cd android && ./scripts/test_gkd_integration.sh
```

Expected: 所有测试通过

- [ ] **Step 3: 真机测试（可选）**

```bash
# 安装到测试设备
./gradlew installDebug

# 通过 ADB 查看日志
adb logcat | grep "OppoPermissionEngine"
```

- [ ] **Step 4: Commit**

```bash
git add android/scripts/test_gkd_integration.sh
git commit -m "test: add GKD integration E2E test script"
```

---

## 验收标准

- [ ] GKD selector 模块成功编译
- [ ] GkdTransform 单元测试通过
- [ ] GkdSelectorHelper 单元测试通过
- [ ] OppoPermissionEngine 使用 GKD Selector 重构完成
- [ ] 所有现有测试仍然通过
- [ ] 代码已提交到 git

---

## 后续优化建议

1. **规则外部化**：将选择器字符串提取到 JSON 配置文件
2. **其他 Engine 迁移**：逐步将其他厂商 Engine 迁移到 GKD Selector
3. **性能优化**：使用 GKD 的 FastQuery 优化复杂选择器
4. **错误处理**：添加选择器解析失败的降级逻辑

---

## 预估时间

- Task 1: 30 分钟
- Task 2: 1 小时
- Task 3: 45 分钟
- Task 4: 2 小时
- Task 5: 30 分钟

**总计：约 5 小时**

