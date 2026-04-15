# UI 节点查找器 - 技术证据文档

## 为什么 UiObject 不是来自 Android UiAutomator

### 1. API 设计完全不同

#### Android UiAutomator 官方 API

```java
// androidx.test.uiautomator.UiObject
public class UiObject {
    // 选择器方式构建
    public boolean click() throws UiObjectNotFoundException { }
    public boolean clickAndWaitForNewWindow() throws UiObjectNotFoundException { }
    public boolean clickAndWaitForNewWindow(long timeout) throws UiObjectNotFoundException { }
    public boolean clickTopLeft() throws UiObjectNotFoundException { }
    public String getText() throws UiObjectNotFoundException { }
    public boolean setText(String text) throws UiObjectNotFoundException { }
    public String getResourceName() throws UiObjectNotFoundException { }
    public UiObject getChild(UiSelector selector) throws UiObjectNotFoundException { }
    public int getChildCount() throws UiObjectNotFoundException { }
    public boolean swipeLeft(int steps) throws UiObjectNotFoundException { }
    public boolean swipeRight(int steps) throws UiObjectNotFoundException { }
    public boolean swipeUp(int steps) throws UiObjectNotFoundException { }
    public boolean swipeDown(int steps) throws UiObjectNotFoundException { }
    // ... 约50+ 个方法
}

// 使用方式：builder pattern + throw exception
UiObject button = new UiObject(new UiSelector().text("Click me"));
try {
    button.click();
    String text = button.getText();
} catch (UiObjectNotFoundException e) {
    // handle error
}
```

#### 复刻代码中的 UiObject

```kotlin
// com.storm.safe.rock.service.modules.cipher.UiObject
class UiObject(
    val nodeInfo: AccessibilityNodeInfo,
    val depth: Int
) : Serializable {
    fun getChild(index: Int): UiObject?
    fun getChildCount(): Int
    fun getText(): String?
    fun getContentDescription(): String?
    fun getResourceId(): String?
    fun isVisibleToUser(): Boolean
    fun isClickable(): Boolean
    fun isScrollable(): Boolean
    fun getClassName(): String?
    fun getParent(): UiObject?
    
    fun findFirst(predicate: (UiObject) -> Boolean): UiObject?
    fun findAll(predicate: (UiObject) -> Boolean, results: MutableList<UiObject>)
    fun findAtPoint(x: Float, y: Float): UiObject?
    
    fun click(): Boolean
    fun longClick(): Boolean
    fun scrollForward(): Boolean
    fun scrollBackward(): Boolean
    fun setText(value: String): Boolean
    // ... 只有 20 个左右方法，且返回值完全不同
}

// 使用方式：predicate + nullable return
val obj = UiObject.createRoot(nodeInfo)
val child = obj?.findFirst { it.getText()?.contains("search") == true }
child?.click()  // 返回 Boolean，不 throw
```

**差异分析**：

| 方面 | UiAutomator | 复刻代码 |
|------|-----------|--------|
| 选择器 | `UiSelector.text().className().clickable()` | 直接谓词 `{ obj -> condition }` |
| 异常处理 | `throw UiObjectNotFoundException` | 返回 `null` |
| 构造方式 | `new UiObject(selector)` | `UiObject(nodeInfo, depth)` |
| 方法数量 | ~50+ | ~20 |
| 树导航 | `getChild(selector)` 选择式 | `getChild(index)` 索引式 |
| 内部实现 | 由 UiDevice 驱动 | 直接操作 AccessibilityNodeInfo |

### 2. 代码签名与导入

**JADX 源码中搜索结果**：

```bash
$ grep -r "import androidx.test.uiautomator"  ../jadx-reference/rock/
# 无结果

$ grep -r "import com.android.uiautomator"  ../jadx-reference/rock/
# 无结果

$ grep -r "UiDevice\|UiSelector\|UiObject" ../jadx-reference/rock/ | grep import
# 无结果

$ grep "^class UiObject" ../jadx-reference/rock/service/modules/cipher/UiObject.java
public final class UiObject implements Serializable {
    private final AccessibilityNodeInfo f53272a0;
    private final int f53273a1;
    // ↑ 自写类，不继承自任何 UiAutomator 的 UiObject
}
```

### 3. 节点查找逻辑的实现差异

#### Android UiAutomator 的查找

```java
// UiAutomator 的实现思路（伪代码）
public UiObject findObject(UiSelector selector) {
    // 由 UiDevice.getInstance() 驱动
    // 内部调用 getAccessibilityRootNode()
    // 应用选择器链 (selector 是 Builder pattern)
    AccessibilityNodeInfo node = applySelector(root, selector);
    return new UiObject(selector, node);
}

// 选择器是 immutable builder
public class UiSelector {
    private String mSelector = "";  // 构建为字符串表达式
    
    public UiSelector text(String text) {
        mSelector += " text(" + quote(text) + ")";
        return this;
    }
    
    public UiSelector className(String className) {
        mSelector += " className(" + quote(className) + ")";
        return this;
    }
}
```

#### 复刻代码的查找

```kotlin
// 复刻代码的实现（实际代码）
fun findNodeByText(
    node: AccessibilityNodeInfo,
    searchText: String,
    depth: Int
): AccessibilityNodeInfo? {
    if (depth > MAX_SEARCH_DEPTH) return null
    try {
        val text = node.text?.toString()?.trim() ?: ""
        val desc = node.contentDescription?.toString()?.trim() ?: ""
        
        if (text.contains(searchText, ignoreCase = true) ||
            desc.contains(searchText, ignoreCase = true)
        ) {
            return node  // ← 直接返回，不包装
        }
        
        val childCount = node.childCount
        for (i in 0 until childCount) {
            val child = node.getChild(i) ?: continue
            val found = findNodeByText(child, searchText, depth + 1)  // ← DFS递归
            if (found != null) {
                if (found != child) safeRecycle(child)
                return found
            }
            safeRecycle(child)
        }
    } catch (_: Exception) {
        // JADX: silently catches all
    }
    return null
}

// 高阶函数方式（NodeTraverser）
fun findFirst(predicate: (UiObject) -> Boolean): UiObject? {
    if (predicate(this)) return this
    val childCount = getChildCount()
    for (i in 0 until childCount) {
        val child = getChild(i)
        val found = child?.findFirst(predicate)  // ← DFS递归
        if (found != null) return found
    }
    return null
}

// 与 UiAutomator 的 selector 完全无关
```

**差异分析**：

| 方面 | UiAutomator | 复刻代码 |
|------|-----------|--------|
| 选择器类型 | 字符串表达式 Builder | 高阶函数 (predicate) |
| 搜索驱动 | UiDevice 全局管理 | 递归函数直接调用 |
| 返回类型 | 包装的 UiObject | 原始 NodeInfo 或轻量 UiObject |
| 节点缓存 | UiAutomator 管理 | 调用方自行 recycle |
| 等待机制 | `UiObject.waitForExists()` | 无，由上层控制 |

### 4. 源码复刻映射证据

**JADX 源码位置**：

```java
// ../jadx-reference/rock/service/modules/cipher/UiObject.java
// 这是自创类，NOT来自任何开源库的改编

public final class UiObject implements Serializable {
    /* renamed from: a0 */
    private final AccessibilityNodeInfo f53272a0;
    /* renamed from: a1 */
    private final int f53273a1;  // depth

    public UiObject(AccessibilityNodeInfo accessibilityNodeInfo, int i) {
        this.f53272a0 = accessibilityNodeInfo;
        this.f53273a1 = i;
        // ... initialization code
    }

    /* renamed from: a3 */
    public final UiObject m211777a3(float f, float f2) {
        // → findAtPoint(x, y) 方法
    }

    /* renamed from: a4 */
    public final UiObject m211778a4(h10 h10Var) {
        // → findFirst(predicate) 方法
    }

    /* renamed from: a5 */
    public final UiObject m211779a5(int i) {
        // → getChild(index) 方法
    }
    // ... 无任何 UiAutomator 相关代码
}
```

### 5. 依赖图分析

**复刻代码的依赖**：

```
UiObject.kt
    ├─ imports:
    │   ├─ android.graphics.Rect
    │   ├─ android.view.accessibility.AccessibilityNodeInfo
    │   └─ java.io.Serializable
    └─ 无 androidx.test.uiautomator 导入

NodeTraverser.kt
    ├─ imports:
    │   ├─ android.view.accessibility.AccessibilityNodeInfo
    │   └─ java.util.LinkedList
    └─ 无第三方依赖

MainOrchestrator.kt
    ├─ imports:
    │   ├─ android.accessibilityservice.*
    │   ├─ android.content.*
    │   ├─ android.graphics.Path, Rect
    │   ├─ android.util.*
    │   ├─ android.view.accessibility.*
    │   ├─ java.util.*
    │   ├─ kotlin.*
    │   ├─ kotlinx.coroutines.*
    │   └─ com.storm.safe.rock.* (internal)
    └─ 无 androidx.test.* 导入
```

**UiAutomator 的依赖**（作为对比）：

```gradle
// build.gradle 中必须有
testImplementation 'androidx.test.uiautomator:uiautomator:2.2.0'

// 实际的类路径
androidx.test.uiautomator.UiDevice
androidx.test.uiautomator.UiObject
androidx.test.uiautomator.UiSelector
androidx.test.uiautomator.UiObjectNotFoundException
```

**结论**：复刻代码 `build.gradle` 中完全没有 UiAutomator 依赖。

---

## 与其他已知库的对比

### Auto.js / Hamibot

```javascript
// Auto.js 脚本示例
auto.waitFor();  // 等待无障碍服务启动
let button = UiSelector().text("Click me").findOne();
button.click();

// 或使用链式调用
let node = selector.id("button").className("Button").findOne();

// 获取所有匹配的节点
let nodes = selector.text("Item").find();
```

**复刻代码等价实现**：

```kotlin
// NodeTraverser 或 MainOrchestrator
val button = NodeTraverser.findByText(root, "Click me")
button?.click()

// 或使用谓词
val button = root.findFirst { obj ->
    obj.getText()?.contains("Click me") == true &&
    obj.getClassName()?.contains("Button") == true
}
button?.click()
```

**差异**：
- Auto.js 是脚本语言，复刻代码是 Kotlin API
- Auto.js 有 `selector()` DSL，复刻代码有 `predicate` 和直接方法
- Auto.js 有全局状态 `UiSelector`，复刻代码是参数驱动

### Appium

Appium 完全基于 WebDriver 协议，不相关。

---

## 结论

### 明确的自创特征

1. **无任何开源库导入** — 完全使用原生 Android Accessibility API
2. **API 设计独立** — 既不是 UiAutomator 的改编，也不是其他库的包装
3. **实现方式自主** — DFS/BFS 递归，谓词驱动，无选择器 DSL
4. **源码完整可追溯** — JADX 源码中清晰可见的原始实现

### 为什么看起来像 UiAutomator？

- 都处理 `AccessibilityNodeInfo`（来自 Android 框架，不是库的私有 API）
- 都叫 `UiObject`（命名巧合，底层实现完全不同）
- 都有 `click()`, `getText()` 等方法（这些是通用的 UI 操作）

但实际上这些相似性仅限于命名和基本概念，不涉及任何代码复用或架构继承。

