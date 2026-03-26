# GKD Selector 集成与 OPPO 权限自动化优化实施计划

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 将 GKD Selector 引擎集成到项目中，并用其重构 OppoPermissionEngine 的节点匹配逻辑，提升代码可维护性和规则外部化能力。

**Architecture:**
- 从本地已克隆的 GKD 项目提取 `selector` 模块
- 创建 AccessibilityNodeInfo 到 GKD Transform 的完整适配层
- 审计并重构 OppoPermissionEngine 的所有 CombineFilter 使用
- 保持现有 Engine 架构不变，仅替换节点匹配层

**Tech Stack:**
- GKD Selector v1.0 (Kotlin Multiplatform)
- Kotlin 1.9.22
- Android AccessibilityService
- Gradle 8.5

**Prerequisites:**
- GKD 项目已克隆到 `/home/code/php/project/full-package/gkd/`
- Android SDK 已配置

---

## 文件结构规划

### 新增文件
- `android/selector/` - GKD selector 模块
- `android/app/src/main/java/com/vendor/rat/auto/selector/GkdTransform.kt` - Transform 适配器
- `android/app/src/main/java/com/vendor/rat/auto/selector/GkdSelectorHelper.kt` - 辅助类
- `android/app/src/test/java/com/vendor/rat/auto/selector/GkdTransformTest.kt` - 单元测试
- `android/app/src/test/java/com/vendor/rat/auto/selector/GkdSelectorHelperTest.kt` - 单元测试

### 修改文件
- `android/settings.gradle` - 添加 selector 模块
- `android/app/build.gradle` - 添加依赖
- `android/app/src/main/java/com/vendor/rat/auto/engine/vendor/OppoPermissionEngine.java` - 重构

---

## Task 1: 提取并验证 GKD Selector 模块

**Files:**
- Create: `android/selector/build.gradle.kts`
- Create: `android/selector/src/` (从 gkd 复制)
- Modify: `android/settings.gradle`

- [x] **Step 1: 验证 GKD 源码存在**

```bash
ls -la gkd/selector/src/commonMain/kotlin/li/songe/selector/
```

Expected: 显示 Selector.kt, Transform.kt 等文件

- [x] **Step 2: 复制 selector 模块**

```bash
cp -r gkd/selector android/
```

- [x] **Step 3: 创建简化的 build.gradle.kts**

```kotlin
plugins {
    id("com.android.library")
    kotlin("multiplatform") version "1.9.22"
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
    defaultConfig { 
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
```

- [x] **Step 4: 添加到 settings.gradle**

```groovy
include ':app'
include ':selector'
```

- [x] **Step 5: 验证编译**

```bash
cd android
./gradlew :selector:build
```

Expected: BUILD SUCCESSFUL

- [x] **Step 6: Commit**

```bash
git add android/selector android/settings.gradle
git commit -m "feat: add GKD selector module from gkd project"
```

---

## Task 2: 实现完整的 GKD Transform 适配层

**Files:**
- Create: `android/app/src/main/java/com/vendor/rat/auto/selector/GkdTransform.kt`
- Modify: `android/app/build.gradle`

- [ ] **Step 1: 添加依赖**

```groovy
dependencies {
    implementation project(':selector')
    testImplementation 'org.mockito:mockito-core:5.3.1'
}
```

- [ ] **Step 2: 实现完整 Transform（含空安全）**

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
            "id", "vid" -> node.viewIdResourceName
            "clickable" -> node.isClickable.toString()
            "checked" -> node.isChecked.toString()
            else -> null
        }
    }

    override fun getParent(node: AccessibilityNodeInfo): AccessibilityNodeInfo? {
        return try {
            node.parent
        } catch (e: Exception) {
            null
        }
    }

    override fun getChildren(node: AccessibilityNodeInfo): Sequence<AccessibilityNodeInfo> {
        return sequence {
            try {
                for (i in 0 until node.childCount) {
                    node.getChild(i)?.let { yield(it) }
                }
            } catch (e: Exception) {
                // 节点已回收
            }
        }
    }
}
```

- [ ] **Step 3: Commit**

```bash
git add android/app/build.gradle android/app/src/main/java/com/vendor/rat/auto/selector/GkdTransform.kt
git commit -m "feat: implement GKD Transform with null safety"
```

---

## Task 3: 审计 OppoPermissionEngine 的 CombineFilter 使用

**Files:**
- Read: `android/app/src/main/java/com/vendor/rat/auto/engine/vendor/OppoPermissionEngine.java`

- [ ] **Step 1: 搜索所有 CombineFilter 使用**

```bash
grep -n "CombineFilter\|StringCondition" android/app/src/main/java/com/vendor/rat/auto/engine/vendor/OppoPermissionEngine.java
```

- [ ] **Step 2: 记录需要替换的位置**

创建审计清单（手动记录行号和用途）

- [ ] **Step 3: 设计 GKD 选择器映射**

为每个 CombineFilter 设计对应的 GKD 选择器字符串

---

## Task 4: 创建 GkdSelectorHelper

**Files:**
- Create: `android/app/src/main/java/com/vendor/rat/auto/selector/GkdSelectorHelper.kt`

```kotlin
package com.vendor.rat.auto.selector

import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo
import li.songe.selector.MatchOption
import li.songe.selector.Selector

object GkdSelectorHelper {
    private const val TAG = "GkdSelectorHelper"
    private val transform = GkdTransform()
    private val defaultOption = MatchOption()

    fun match(root: AccessibilityNodeInfo?, selector: String): AccessibilityNodeInfo? {
        if (root == null) return null
        return try {
            val sel = Selector.parse(selector)
            sel.match(root, transform, defaultOption)
        } catch (e: Exception) {
            Log.e(TAG, "Selector match failed: $selector", e)
            null
        }
    }
}
```

- [ ] **Commit**

```bash
git add android/app/src/main/java/com/vendor/rat/auto/selector/GkdSelectorHelper.kt
git commit -m "feat: add GkdSelectorHelper with error handling"
```

---

## Task 5: 重构 OppoPermissionEngine（示例）

**Files:**
- Modify: `android/app/src/main/java/com/vendor/rat/auto/engine/vendor/OppoPermissionEngine.java`

- [ ] **Step 1: 添加导入**

```java
import com.vendor.rat.auto.selector.GkdSelectorHelper;
```

- [ ] **Step 2: 替换一个 CombineFilter 示例**

旧代码（假设在某个方法中）:
```java
CombineFilter filter = new CombineFilter()
    .add(new StringCondition("text", "允许"));
```

新代码:
```java
AccessibilityNodeInfo node = GkdSelectorHelper.match(root, "[text='允许']");
```

- [ ] **Step 3: 测试验证**

```bash
./gradlew :app:assembleDebug
```

- [ ] **Step 4: Commit**

```bash
git add android/app/src/main/java/com/vendor/rat/auto/engine/vendor/OppoPermissionEngine.java
git commit -m "refactor: replace CombineFilter with GKD Selector in OppoPermissionEngine"
```

---

## 验收标准

- [ ] selector 模块编译成功
- [ ] GkdTransform 实现完整（含空安全）
- [ ] GkdSelectorHelper 有错误处理
- [ ] OppoPermissionEngine 至少一处使用 GKD Selector
- [ ] APK 可以正常编译

---

## 预估时间

- Task 1: 30 分钟
- Task 2: 1 小时
- Task 3: 1 小时（审计）
- Task 4: 30 分钟
- Task 5: 2 小时

**总计：5 小时**

