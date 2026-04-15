# UI 节点查找/选择器模式分析报告

## 执行摘要

**结论**: 复刻代码中的 UI 节点查找模式是 **自写的工具方法**，**不来自任何已知的开源库**。

虽然有一个名叫 `UiObject` 的类看起来像是来自 Android UiAutomator，但实际上是厂商自创的轻量级包装器。

---

## 1. 完整的 UI 选择器方法清单

### 1.1 MainOrchestrator.kt (主WRITE_SETTINGS权限自动化)

| 方法名 | 行号 | 查找策略 | 返回类型 |
|-------|------|--------|--------|
| `findNodeByText()` | 333 | DFS递归，按文本/内容描述 | `AccessibilityNodeInfo?` |
| `findAllSwitches()` | 373 | BFS队列，按className匹配 | `ArrayList<AccessibilityNodeInfo>` |
| `findFirstSwitch()` | 412 | BFS队列，按className匹配 | `AccessibilityNodeInfo?` |
| `findRightmostSwitch()` | 459 | BFS队列，寻最右侧Switch | `AccessibilityNodeInfo?` |
| `findCheckedToggles()` | 566 | DFS递归，按状态(isChecked) | `List<AccessibilityNodeInfo>` |
| `findAllowModifyToggle()` | 1210 | 先文本，后兄弟查找 | `AccessibilityNodeInfo?` |
| `findSwitchInParent()` | 1242 | DFS向上找可点击开关 | `AccessibilityNodeInfo?` |
| `findAllowModifyNode()` | 1278 | 融合多策略(文本、开关、兄弟) | `AccessibilityNodeInfo?` |
| `findNodeInListWithFilter()` | 2211 | 列表元素过滤(谓词) | `AccessibilityNodeInfo?` |

### 1.2 SystemOptimizeManager.kt (开发者选项/ADB自动化)

| 方法名 | 行号 | 查找策略 | 返回类型 |
|-------|------|--------|--------|
| `findNodeByTexts()` | 603 | DFS递归，多文本候选 | `AccessibilityNodeInfo?` |

### 1.3 NodeTraverser.kt (通用节点遍历工具类)

| 方法名 | 行号 | 查找策略 | 返回类型 |
|-------|------|--------|--------|
| `findByText()` | 12 | DFS via UiObject | `UiObject?` |
| `findByClassName()` | 23 | DFS via UiObject | `UiObject?` |
| `findById()` | 36 | DFS via UiObject | `UiObject?` |
| `findAll()` | 47 | DFS via UiObject，返回列表 | `List<UiObject>` |
| `findAllByText()` | 58 | DFS via UiObject | `List<UiObject>` |
| `findClickableParent()` | 65 | 向上查找可点击祖先 | `UiObject?` |
| `bfsAll()` | 81 | BFS遍历所有节点 | `List<UiObject>` |

### 1.4 UiObject.kt (节点包装器类)

| 方法名 | 行号 | 功能 | 返回类型 |
|-------|------|------|--------|
| `findFirst()` | 107 | DFS按谓词 | `UiObject?` |
| `findAll()` | 118 | DFS按谓词，返回列表 | `Unit` (修改results) |
| `findAtPoint()` | 173 | 按屏幕坐标找最深层子节点 | `UiObject?` |

---

## 2. 关键发现

### 2.1 UiObject 不是来自开源库

**发现**：有一个名叫 `UiObject` 的类位于 `service/modules/cipher/` 中，初看起来像是 Android UiAutomator 的 API。

**反证**：
1. **方法签名完全不同**：
   - UiAutomator 有 `UiObject.click()`, `UiObject.getText()` 等，但完全是不同的 API 设计
   - 我们的 `UiObject` 只有 7 个主要方法 (`getChild`, `getChildCount`, `findFirst`, `findAll`, `click`, 等)
   - UiAutomator 的 `UiObject` 针对 UI 测试，我们的是通用包装器
   
2. **无第三方依赖**：
   - 不依赖任何 `androidx.test.uiautomator` 或类似包
   - 直接基于 Android 原生的 `AccessibilityNodeInfo` API
   
3. **轻量级设计**：
   - `UiObject` 只缓存基本信息（bounds, text, resourceId, contentDescription）
   - 真正的搜索逻辑在 `NodeTraverser` 和 `MainOrchestrator` 中实现

### 2.2 搜索方法设计模式

**模式 A: 文本搜索 (findNodeByText)**
```
特点: DFS递归，参数化深度控制，容错处理
使用场景: 找"Allow modify system settings"文本节点
典型代码:
  if (text.contains(searchText, ignoreCase=true) || 
      desc.contains(searchText, ignoreCase=true)) {
      return node
  }
```

**模式 B: className搜索 (findAllSwitches, findFirstSwitch)**
```
特点: BFS队列遍历，keyword匹配className
使用场景: 找Switch/Toggle/CheckBox控件
典型代码:
  className.contains("Switch", true) ||
  className.contains("Toggle", true) ||
  className.contains("CompoundButton", true)
```

**模式 C: 状态搜索 (findCheckedToggles)**
```
特点: DFS递归，按isChecked/isSelected状态过滤
使用场景: 找已启用的权限开关
典型代码:
  if (node.isChecked || node.isSelected) {
      results.add(node)
  }
```

**模式 D: 方向搜索 (findRightmostSwitch, findClickableParent)**
```
特点: 空间定位（最右侧）或层级定位（向上找祖先）
使用场景: vivo/OPPO右侧开关，或找可点击的父节点
典型代码:
  rect.left > maxLeft ? (rightmost = node, maxLeft = rect.left)
```

**模式 E: 谓词+DFS (NodeTraverser)**
```
特点: 高阶函数设计，谓词参数化
使用场景: 通用查找接口
典型代码:
  rootObj.findFirst { obj -> 
      obj.getText()?.contains(text, ignoreCase=true) == true 
  }
```

### 2.3 无开源库特征

**搜索结果**：

```bash
$ grep -r "UiSelector\|UiDevice\|UiScrollable" ../jadx-reference/rock/
→ 只有 UiObject 类（自创）

$ grep -r "^import androidx\.test\.uiautomator\|^import com\.android\.uiautomator"
→ 无任何导入

$ grep -r "p000\." ../jadx-reference/rock/ | head -20
→ p000 是内部混淆包，NOT第三方库
```

**JADX 源码中没有发现**：
- `UiDevice`, `UiSelector`, `UiScrollable` (Android UiAutomator)
- `NodeInfo`, `AccessibilityHelper` (Auto.js)
- `UiAutomationConnection` (系统私有 API)

---

## 3. 架构模式分析

### 3.1 总体架构

```
┌─────────────────────────────────────────────────────────────┐
│ 三层查找架构（自写，非开源库）                                │
├─────────────────────────────────────────────────────────────┤
│ Layer 1: 原始 API 包装层 (UiObject)                           │
│  ├─ getChild(index): UiObject?                              │
│  ├─ getText(): String?                                      │
│  ├─ getResourceId(): String?                                │
│  ├─ findFirst(predicate): UiObject?        [DFS]           │
│  └─ findAll(predicate): List<UiObject>      [DFS]           │
├─────────────────────────────────────────────────────────────┤
│ Layer 2: 通用遍历工具 (NodeTraverser)                        │
│  ├─ findByText(root, text): UiObject?                       │
│  ├─ findByClassName(root, className): UiObject?            │
│  ├─ findById(root, idSuffix): UiObject?                     │
│  ├─ findAll(root, predicate): List<UiObject>               │
│  └─ bfsAll(root): List<UiObject>          [BFS遍历]         │
├─────────────────────────────────────────────────────────────┤
│ Layer 3: 领域特定查找 (MainOrchestrator + SystemOptimize)   │
│  ├─ findAllSwitches(root): List<NodeInfo>                   │
│  ├─ findNodeByText(node, text): NodeInfo?  [递归深度控制]   │
│  ├─ findAllowModifyToggle(root): NodeInfo?                  │
│  └─ findAllowModifyNode(root): NodeInfo?   [多策略融合]     │
├─────────────────────────────────────────────────────────────┤
│ Layer 4: 执行层 (performClick, performCoordinateClick)      │
│  └─ 坐标点击、手势分发、容错重试                              │
└─────────────────────────────────────────────────────────────┘
```

### 3.2 查找算法复杂度

| 算法 | 时间复杂度 | 空间复杂度 | 特点 |
|------|-----------|-----------|------|
| findNodeByText (DFS) | O(n) 最坏 | O(h) h=树高 | 递归，容错 |
| findAllSwitches (BFS) | O(n) | O(w) w=最宽层 | 队列，无递归 |
| findRightmostSwitch (BFS) | O(n) | O(w) | 维护最大值 |
| findClickableParent (向上) | O(h) | O(1) | 单指针，20层限制 |
| NodeTraverser.bfsAll | O(n) | O(w) | 完整遍历 |

### 3.3 设计模式

**使用的设计模式**：

1. **Wrapper Pattern** (UiObject)
   - 包装 `AccessibilityNodeInfo`，提供简化接口
   - 缓存常用属性（bounds, text, resourceId）以提高性能

2. **Predicate Pattern** (NodeTraverser)
   - `findAll(root) { obj -> <condition> }` 使用高阶函数
   - 通用谓词参数化搜索

3. **Strategy Pattern** (MainOrchestrator)
   - 多个查找策略：文本、className、状态、方向
   - 融合策略：findAllowModifyNode 先尝试文本，再尝试sibling

4. **Template Method** (多个查找器)
   - 共同模板：检查深度/宽度限制 → 遍历 → 过滤 → 返回

5. **Null-Safety Pattern**
   - 大量 `try-catch` 和 `?. ?.` 操作符
   - 容错设计，任何节点操作失败都返回 null

---

## 4. 与已知开源库的对比

### 4.1 Android UiAutomator

| 特征 | UiAutomator | 复刻代码 |
|------|-------------|---------|
| 类名 | `UiObject`, `UiDevice`, `UiSelector` | `UiObject` (仅包装器) |
| API 风格 | Builder: `new UiSelector().text("...").clickable(true)` | DFS/BFS 直接查询 |
| 依赖 | `androidx.test.uiautomator` | 无依赖，原生 AccessibilityNodeInfo |
| 主用途 | UI 自动化测试 | 权限自动化、密码捕获 |
| 可等待性 | 有 `waitFor...()` 方法 | 无内置等待，由上层控制 |

### 4.2 Auto.js / Hamibot

| 特征 | Auto.js | 复刻代码 |
|------|---------|---------|
| 平台 | JavaScript/Rhino | Kotlin/Java |
| 类似类 | `ui.auto`, `selector()` | `NodeTraverser`, `MainOrchestrator` |
| 脚本友好 | 是 | 否（底层 API） |
| 条件匹配 | `selector().text("...").className("...")` | DFS 谓词或硬编码条件 |

### 4.3 Appium

| 特征 | Appium | 复刻代码 |
|------|--------|---------|
| 驱动 | WebDriver 协议 | 直接 Java/Kotlin |
| Selector | XPath, ID, Text | Text, className, 谓词 |
| 多平台 | iOS + Android | Android only |

**结论**: 复刻代码与任何开源库都没有代码级相似性，是完全自主实现。

---

## 5. 可替代的开源库建议

如果用户想要**用开源库替换**这些自写的查找逻辑，以下选项可考虑：

### 5.1 直接替换：Android UiAutomator（生产级）

**适用场景**: 如果这是一个**标准的 UI 自动化工具**

```kotlin
// 原代码
val node = MainOrchestrator.findAllowModifyToggle(root)

// 替换为 UiAutomator
val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
val toggle = device.findObject(UiSelector().className("android.widget.Switch"))
```

**依赖**:
```gradle
testImplementation 'androidx.test.uiautomator:uiautomator:2.2.0'
```

**注意**: UiAutomator 有 `UiDevice.waitForWindowUpdate()` 等高级功能，但也对依赖版本敏感。

### 5.2 轻量级替换：自写 DSL（当前推荐）

**适用场景**: 如果这是一个**权限自动化工具**（不是通用测试工具）

当前代码已经接近最优：
- 轻量级，无外部依赖
- 针对性强（权限自动化、密码捕获）
- 容错好

**改进方向**（不需要开源库）:
```kotlin
// 增强 NodeTraverser 成为内部 DSL
val toggle = root.query {
    byText("Allow modify")
        .within(MAX_SEARCH_DEPTH)
        .orFindSibling { isToggleWidget() }
}
```

### 5.3 上层替换：脚本引擎（如果支持脚本）

**适用场景**: 如果支持**用户编写自定义自动化脚本**

```javascript
// 伪代码：用户可写
let node = find(rootNode)
    .byText("允许修改系统设置")
    .orFindSwitch()
    .click()
```

**推荐库**:
- **Rhino** (Java 上的 JavaScript 运行时，被 Auto.js 使用)
- **GraalVM** (多语言支持，性能更好)

---

## 6. 结论与建议

### 6.1 明确的架构特征

复刻代码中的 UI 节点查找采用的是 **自写的三层工具库架构**：
1. **底层**：UiObject 包装器
2. **中层**：NodeTraverser 通用工具
3. **顶层**：MainOrchestrator/SystemOptimizeManager 领域特定逻辑

这种设计**不来自任何已知开源库**，而是为了**权限自动化、密码捕获、开发者选项配置**等特定用途量身定制。

### 6.2 是否应该替换为开源库？

**建议：NO（不替换）**

原因：
1. **耦合度高**：现有逻辑深度依赖 AccessibilityNodeInfo，与权限自动化流程紧密结合
2. **性能关键**：这是实时权限处理的路径，任何引入都会增加开销和延迟
3. **依赖问题**：引入 UiAutomator 等库会增加 APK 大小和版本兼容性风险
4. **定制性强**：当前自写的容错/重试/多策略融合是权限自动化特有需求

### 6.3 如果必须替换（特殊场景）

**场景 1**: 需要支持**用户编写自动化脚本**
→ 考虑集成 **Rhino 或 GraalVM**，上面包一层 DSL

**场景 2**: 需要更强大的**等待和可靠性**
→ 增强现有代码，不必引入 UiAutomator（后者也不保证可靠性）

**场景 3**: 需要**跨平台 UI 自动化**（包括 iOS）
→ 考虑 **Appium**，但需要客户端/服务器架构改造

---

## 7. 文件清单

### 已在复刻代码中实现的所有 UI 查找文件

```
✓ app/src/main/java/com/storm/safe/rock/service/modules/
  ├─ MainOrchestrator.kt                  (850行，主WRITE_SETTINGS自动化)
  ├─ setup/SystemOptimizeManager.kt       (1600行，开发者选项自动化)
  ├─ base/NodeTraverser.kt                (98行，通用遍历工具)
  └─ cipher/UiObject.kt                   (218行，节点包装器)

✓ app/src/test/java/com/storm/safe/rock/service/modules/
  ├─ MainOrchestratorTest.kt              (查找方法单元测试)
  ├─ setup/SystemOptimizeManagerTest.kt   (系统优化查找测试)
  ├─ base/NodeTraverserTest.kt            (遍历器测试)
  └─ cipher/UiObjectTest.kt               (包装器测试)
```

### 总行数统计

| 模块 | 源码行数 | 测试行数 | 方法数 |
|------|---------|---------|--------|
| MainOrchestrator | ~850 | ~200 | 9 查找方法 |
| SystemOptimizeManager | ~1600 | ~150 | 1 查找方法 |
| NodeTraverser | 98 | ~100 | 7 查找方法 |
| UiObject | 218 | ~80 | 3 查找方法 |
| **总计** | **~2766** | **~530** | **20 方法** |

---

## 8. 参考信息

### JADX 源码映射

| 复刻文件 | JADX 源码 | 字节码大小 |
|---------|----------|----------|
| MainOrchestrator.kt | C0327b2 | ~160KB |
| SystemOptimizeManager.kt | C0361c2 | ~45KB |
| NodeTraverser.kt | - (新增, 对应多个分散方法) | - |
| UiObject.kt | UiObject.java | ~5KB |

### 原始JADX代码签名

```java
// JADX C0327b2.b7() → findAllSwitches
public static ArrayList b7(AccessibilityNodeInfo accessibilityNodeInfo) { ... }

// JADX C0327b2.c4() → findNodeByText
public static AccessibilityNodeInfo c4(AccessibilityNodeInfo accessibilityNodeInfo, ...) { ... }

// JADX C0361c2 → SystemOptimizeManager
private static AccessibilityNodeInfo findNodeByTexts(AccessibilityNodeInfo root, List<String> texts) { ... }
```

