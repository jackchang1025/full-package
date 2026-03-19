# MODULE_04 UI 自动化框架 — Vendor 行为审计

## 1. 模块职责

UI 节点树操作框架。通过无障碍服务获取屏幕节点树，提供查找、过滤、操作（点击/滚动/输入）能力。是厂商适配引擎 (MODULE_03) 的基础依赖。

## 2. 文件清单对比

### entity/ — UI 节点实体

| Vendor 文件 | 行数 | Replica 文件 | 行数 | 差距 |
|------------|------|-------------|------|------|
| UiObject.java | 3801 | UiNode.java | 829 | ❌ 缺 97 个方法 |
| UiObjectCollection.java | 370 | UiNodeCollection.java | 254 | ⚠️ 缺 1 方法 (toListVO) |
| ReadScreenNodeInfo.java | 145 | ReadScreenNodeInfo.java | 80 | ⚠️ 行数差 |
| ReadScreenWindow.java | 67 | ReadScreenWindow.java | 47 | ⚠️ 行数差 |
| Point.java | 59 | Point.java | 49 | ⚠️ 行数差 |
| DistanceTouchNode.java | 39 | DistanceTouchNode.java | 34 | ✅ 接近 |
| CheckedResult.java | 41 | CheckedResult.java | 34 | ✅ 接近 |

### filter/ — 过滤器

| 指标 | Vendor | Replica | 差距 |
|------|--------|---------|------|
| 文件数 | 39 | 42 | Replica 多 3 个辅助接口 |
| 总行数 | 1483 | 1116 | ⚠️ Replica 少 367 行 |

文件差异:
- Vendor 有但 Replica 缺: `CombineFilter` (在 condition/ 中), `Filter` (→ NodeFilter)
- Replica 多出: `BooleanPropertyGetter`, `IntPropertyGetter`, `StringPropertyGetter`, `UiNodeProperty`, `NodeFilter` (辅助接口)

### condition/ — 条件匹配

| Vendor 文件 | 行数 | Replica 文件 | 行数 | 差距 |
|------------|------|-------------|------|------|
| ActionValueCondition | 43 | 31 | ⚠️ |
| BoolCondition | 307 | 38 | ❌ 严重缺失 (269行) |
| BoundsCondition | 79 | 50 | ⚠️ |
| GlobalActionCondition | 86 | 64 | ⚠️ |
| IntCondition | 180 | 47 | ❌ 严重缺失 (133行) |
| PointCondition | 59 | 39 | ⚠️ |
| StringCondition | 91 | 144 | ✅ Replica 更完整 |
| TargetActionCondition | 258 | 84 | ❌ 严重缺失 (174行) |
| — | — | CombineFilter | 186 | ADAPT: 从 filter/ 移到 condition/ |

## 3. UiObject vs UiNode 核心差距

### 3.1 方法统计

| 指标 | Vendor UiObject | Replica UiNode |
|------|----------------|----------------|
| 总方法数 | 221 | 123 |
| 去重方法名 | 213 | 116 |
| Vendor 有 Replica 缺 | 127 个方法 | — |
| Replica 有 Vendor 无 | — | 37 个方法 |

### 3.2 缺失方法分类

#### A. 查找方法 (findBy* / findOneBy* / findLastBy*) — 缺 ~80 个

Vendor 有完整的三套查找方法:
```
findByXxx(str)       → 返回 UiObjectCollection (所有匹配)
findOneByXxx(str)    → 返回 UiObject (第一个匹配)
findLastByXxx(str)   → 返回 UiObject (最后一个匹配)
```

每套支持 5 种匹配模式: equals/contains/endsWith/matches/startsWith

覆盖属性: Text/Desc/Id/ClassName/PackageName/Bounds/Combine

Replica 只有部分 findByText/findById/findByClassName，缺少:
- 所有 findLastBy* 系列 (~25 个)
- 所有 findOneBy* 变体 (~20 个)
- findByBounds/findByBoundsContains/findByBoundsInside
- findByCombine/findByCombineWithChild/findByCombineWithoutChild
- findByOperateOr/findByDesc*/findByPackageName*
- findChildUtilUpLevel/findParentByCombine/findParentUtilCombine

#### B. 操作方法 — 缺 ~15 个

| 缺失方法 | 功能 |
|---------|------|
| actionByName(TargetActionCondition) | 按名称执行操作 (30+ 种) |
| clickPosition(float, float) | 坐标点击 |
| enter() | 回车键 |
| repeatClick(Integer) | 重复点击 |
| simulationScrollForward/Backward() | 手势模拟滚动 |
| scrollBackwardByGesture/ForwardByGesture() | 手势滚动 |
| scrollBackwardEnd/ForwardEnd() | 滚动到底/顶 |
| scrollBackwardUtil/ForwardUtil() | 滚动直到条件满足 |
| scrollBackwardUtilMultiple/ForwardUtilMultiple() | 滚动收集多个 |
| utilRefresh/utilMultipleRefresh() | 刷新直到条件满足 |

#### C. 属性方法 — 缺 ~20 个

| 缺失方法 | 功能 |
|---------|------|
| boundsInWindow() | 窗口内边界 |
| canScrollDown/Up/Left/Right() | 方向滚动能力 |
| canOpenPopup() | 弹窗能力 |
| column/columnCount/columnSpan() | 网格列信息 |
| row/rowCount/rowSpan() | 网格行信息 |
| drawingOrder() | 绘制顺序 |
| heading() | 标题节点 |
| hintText() | 提示文本 |
| paneTitle() | 面板标题 |
| roleDesc() | 角色描述 |
| stateDesc() | 状态描述 |
| tooltipText() | 工具提示 |
| uniqueId() | 唯一标识 |
| screenReaderFocusable() | 屏幕阅读器焦点 |
| textEntryKey/textSelectable() | 文本输入 |
| importantForAccessibility() | 无障碍重要性 |
| getProperty(String) | 通用属性获取 |
| getRegionAt/regionCount/getTargetForRegion() | 区域操作 |

### 3.3 Replica 命名差异

Replica 用 Java Bean 风格 (isClickable/getClassName)，Vendor 用简短风格 (clickable/className)。
这导致引擎代码调用时方法名不匹配。

| Vendor 调用 | Replica 实际方法 |
|------------|----------------|
| node.clickable() | node.isClickable() |
| node.className() | node.getClassName() |
| node.text() | node.text() ✅ |
| node.desc() | node.getContentDescription() |
| node.id() | node.getViewIdResourceName() |

## 4. BoolCondition 差距 (307 行 vs 38 行)

Vendor BoolCondition 包含完整的属性枚举和匹配逻辑:
```java
// vendor: 支持 20+ 种布尔属性
checkable/checked/clickable/contextClickable/editable/enabled
focusable/focused/longClickable/multiLine/password/scrollable
selected/visibleToUser/accessibilityFocused/contentInvalid
dismissable/heading/importantForAccessibility/screenReaderFocusable
showingHintText/textEntryKey
```

Replica 只有基础的 property + value 字段，缺少属性枚举和 UiNode 匹配逻辑。

## 5. TargetActionCondition 差距 (258 行 vs 84 行)

Vendor 支持 30+ 种操作名称:
```
click/longClick/clickLeft/clickRight/clickPosition
scrollForward/scrollBackward/scrollUp/scrollDown/scrollLeft/scrollRight/scrollTo
focus/clearFocus/select/copy/cut/paste/dismiss/expand/collapse/show
setText/setSelection/setProgress/enter/accessibilityFocus/clearAccessibilityFocus
repeatClick/simulationScrollForward/simulationScrollBackward
```

Replica 只有基础的 actionName + actionValue 字段。

## 6. 优先修复项

### P0 (引擎依赖 — 不修复则厂商引擎无法工作)

1. UiNode 补齐 vendor 风格方法别名: clickable()/className()/desc()/id()/text() 等
2. UiNode 补齐 findByCombine/findOneByCombine — 引擎核心查找方法
3. BoolCondition 补齐属性枚举和匹配逻辑 (307行)
4. TargetActionCondition 补齐 actionByName 操作分发 (258行)
5. IntCondition 补齐属性枚举和比较逻辑 (180行)

### P1 (完整功能)

6. UiNode 补齐所有 findBy*/findOneBy*/findLastBy* 查找方法
7. UiNode 补齐滚动操作: scrollBackwardUtil/ForwardUtil/simulationScroll
8. UiNode 补齐 clickPosition/repeatClick/actionByName
9. UiObjectCollection → UiNodeCollection 补齐 toListVO

### P2 (边缘功能)

10. UiNode 补齐网格属性: column/row/span
11. UiNode 补齐区域操作: getRegionAt/regionCount
12. ReadScreenNodeInfo/ReadScreenWindow 行数对齐
