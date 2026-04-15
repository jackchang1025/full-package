# UI 节点查找器 - 快速参考

## 答案总结

### ❌ 结论：不是开源库

复刻代码中的 UI 节点查找模式 **100% 自写**，不来自任何已知的开源库。

| 特征 | 复刻代码 | UiAutomator | Auto.js |
|------|---------|-----------|---------|
| **来源** | ✓ 自写（JADX 源码） | Android 框架库 | JavaScript 脚本库 |
| **依赖** | ✓ 无外部依赖 | `androidx.test.uiautomator` | Rhino 引擎 |
| **API 类型** | ✓ 谓词+递归 | Builder+Selector | 链式 DSL |
| **异常处理** | ✓ 返回 null | throw 异常 | 返回 null |
| **主用途** | ✓ 权限自动化 | UI 测试框架 | 用户脚本 |

---

## 20 个关键方法速查表

### 按功能分类

#### **文本搜索** (DFS)
```
findNodeByText()              MainOrchestrator   // 递归深度控制
findByText()                  NodeTraverser      // 包装版本
findAllByText()               NodeTraverser      // 返回列表
findNodeByTexts()             SystemOptimizeManager  // 多文本候选
```

#### **className搜索** (BFS)
```
findAllSwitches()             MainOrchestrator   // 所有Switch控件
findFirstSwitch()             MainOrchestrator   // 首个Switch
findByClassName()             NodeTraverser      // 通用类名搜索
```

#### **状态搜索**
```
findCheckedToggles()          MainOrchestrator   // isChecked 状态
isToggleWidget()              MainOrchestrator   // 类型判断
isVisibleAndChecked()         MainOrchestrator   // 双条件判断
```

#### **方向搜索**
```
findRightmostSwitch()         MainOrchestrator   // 最右侧Switch
findClickableParent()         NodeTraverser      // 向上找祖先
findSwitchInParent()          MainOrchestrator   // 向上找兄弟
getParent()                   UiObject           // 直接父节点
```

#### **融合策略** (多方法组合)
```
findAllowModifyToggle()       MainOrchestrator   // 先文本后兄弟
findAllowModifyNode()         MainOrchestrator   // 三重策略
findNodeInListWithFilter()    MainOrchestrator   // 谓词过滤
```

#### **通用遍历** (工具类)
```
findFirst()                   UiObject           // DFS 返回单个
findAll()                     UiObject           // DFS 返回列表
findAll()                     NodeTraverser      // 包装版本
bfsAll()                      NodeTraverser      // BFS 遍历全部
findAtPoint()                 UiObject           // 按坐标查找
```

---

## 架构三层

```
┌─────────────────────────────────────────────────────────────┐
│ Layer 1: 原始包装器 (UiObject)                              │
│  • 缓存 bounds, text, resourceId, contentDescription        │
│  • 提供 findFirst/findAll 谓词接口                           │
│  • 提供 findAtPoint 坐标查询                                  │
└──────────────────────┬──────────────────────────────────────┘
                       │
┌──────────────────────▼──────────────────────────────────────┐
│ Layer 2: 通用工具 (NodeTraverser)                            │
│  • findByText/findByClassName/findById                      │
│  • findAll (按谓词)                                         │
│  • bfsAll (完整遍历)                                        │
│  • findClickableParent (向上查找)                            │
└──────────────────────┬──────────────────────────────────────┘
                       │
┌──────────────────────▼──────────────────────────────────────┐
│ Layer 3: 领域特定 (MainOrchestrator, SystemOptimizeManager) │
│  • findAllSwitches / findFirstSwitch / findRightmostSwitch  │
│  • findAllowModifyToggle / findAllowModifyNode (融合策略)    │
│  • findSwitchInParent / findNodeByTexts (多候选)            │
│  • findNodeInListWithFilter (谓词过滤)                      │
└─────────────────────────────────────────────────────────────┘
```

---

## 搜索算法速查

| 方法 | 算法 | 时间 | 空间 | 特点 |
|------|------|------|------|------|
| findNodeByText | DFS递归 | O(n) | O(h) | 深度限制 |
| findAllSwitches | BFS队列 | O(n) | O(w) | 宽度优先 |
| findRightmostSwitch | BFS + max | O(n) | O(w) | 维护最大值 |
| findCheckedToggles | DFS递归 | O(n) | O(h) | 状态过滤 |
| findClickableParent | 向上链 | O(h) | O(1) | 最多20层 |
| NodeTraverser.bfsAll | BFS遍历 | O(n) | O(w) | 完整遍历 |

---

## 文件定位速查

| 文件 | 行数 | 方法数 | 关键方法 |
|------|------|--------|---------|
| MainOrchestrator.kt | ~850 | 9 | findAllowModifyNode (融合方案) |
| SystemOptimizeManager.kt | ~1600 | 1 | findNodeByTexts (多候选) |
| NodeTraverser.kt | 98 | 7 | findFirst/findAll (通用) |
| UiObject.kt | 218 | 3 | findFirst/findAll/findAtPoint |

---

## 如果要替换？（不建议）

### ❌ 不建议：Android UiAutomator

```kotlin
// 为什么不行：
// 1. 增加 APK 大小（~3MB）
// 2. 版本兼容性问题
// 3. 当前实现已经很轻量，无需替换

// 如果非要用：
testImplementation 'androidx.test.uiautomator:uiautomator:2.2.0'
val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
```

### ✓ 建议：增强现有代码

```kotlin
// 方案 A: 增强 NodeTraverser DSL
val toggle = root.query {
    byText("Allow modify")
        .within(15)  // 深度限制
        .orFindSibling { isToggleWidget() }
}

// 方案 B: 集成脚本引擎（如果需要用户脚本）
// 使用 Rhino 或 GraalVM 上层包装
```

---

## 代码示例

### 查找"Allow modify settings"开关

```kotlin
// 方法 1: 使用高层 API（推荐）
val toggle = MainOrchestrator.findAllowModifyToggle(root)
toggle?.click()

// 方法 2: 使用通用工具
val toggle = NodeTraverser.findByText(root, "Allow modify")
toggle?.click()

// 方法 3: 使用谓词
val toggle = root?.findFirst { obj ->
    obj.getText()?.contains("Allow modify") == true &&
    obj.getClassName()?.contains("Switch") == true
}
toggle?.click()

// 方法 4: 多策略融合（最鲁棒）
val toggle = MainOrchestrator.findAllowModifyNode(root)
toggle?.click()
```

### 查找所有可见的Switch控件

```kotlin
// 方法 1: 直接
val switches = MainOrchestrator.findAllSwitches(root)
switches.forEach { it.performAction(AccessibilityNodeInfo.ACTION_CLICK) }

// 方法 2: 使用通用工具
val switches = NodeTraverser.findAll(root) { obj ->
    obj.getClassName()?.contains("Switch") == true
}
switches.forEach { it.click() }

// 方法 3: 找最右侧的
val rightmost = MainOrchestrator.findRightmostSwitch(root)
rightmost?.performAction(AccessibilityNodeInfo.ACTION_CLICK)
```

### 按坐标查找（密码捕获场景）

```kotlin
// UiObject 特有：按屏幕坐标找最深层节点
val root = UiObject.createRoot(accessibilityNodeInfo)
val node = root?.findAtPoint(100f, 200f)
node?.click()
```

---

## 问题诊断

### Q: 为什么不用开源库？
A: 
1. **性能** — 当前实现是权限自动化的性能关键路径
2. **依赖** — 引入库会增加 APK 大小和兼容性风险
3. **定制性** — 当前的容错/重试/多策略融合是自动化专有需求
4. **可控性** — 自写代码可完全控制异常处理、资源回收

### Q: UiObject 是从 UiAutomator 改的吗？
A: 不是。虽然都叫 `UiObject`，但：
- UiAutomator 有 `UiSelector` 和 `UiDevice` 驱动
- 复刻代码使用谓词 + 递归
- API 完全不同（Builder vs Predicate）
- JADX 源码中无任何 UiAutomator 导入

### Q: 为什么有 NodeTraverser？
A: 
- 复刻过程中的改进（原 JADX 源码中查找方法分散）
- 作为通用工具层，提高代码复用
- 支持 UiObject 的谓词接口
- 符合 TDD 流程

---

## 许可证

本分析文档基于 update-replica 项目（APK 逆向复刻）。

生成日期: 2026-04-14
