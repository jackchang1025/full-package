# 模块 04：UI 自动化框架设计文档

> **模块名称**: UI Automation Framework Module
> **优先级**: P0（极高）
> **依赖**: 无障碍服务（AccessibilityService）
> **版本**: 1.0
> **日期**: 2026-03-16

---

## 一、模块概述

### 1.1 功能描述

UI 自动化框架提供了一套完整的 UI 节点查询、过滤和操作 API，是所有自动化引擎的基础设施。

### 1.2 核心能力

- ✅ 节点查询（byText / byId / byClass / byCombine）
- ✅ 节点过滤（文本条件 / 布尔条件 / 坐标条件）
- ✅ 节点操作（click / longClick / setText / scroll）
- ✅ 滚动查找（scrollForwardUntil / scrollBackwardUntil）
- ✅ 组合过滤器（多条件组合）

---

## 二、架构设计

```
┌─────────────────────────────────────────────────────────┐
│                    UiNode (节点封装)                     │
│  - 封装 AccessibilityNodeInfo                            │
│  - 提供查询和操作方法                                     │
└─────────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────────┐
│              NodeFilter (过滤器接口)                     │
│  ├─ StringCondition (文本条件)                          │
│  ├─ BoolCondition (布尔条件)                            │
│  └─ PointCondition (坐标条件)                           │
└─────────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────────┐
│            CombineFilter (组合过滤器)                    │
│  - 支持多条件 AND/OR 组合                                │
└─────────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────────┐
│                UiAction (操作执行器)                     │
│  - click / longClick / setText / scroll                 │
└─────────────────────────────────────────────────────────┘
```

---

## 三、核心类设计

### 3.1 UiNode

```java
package com.vendor.rat.auto.entity;

public class UiNode {
    private AccessibilityNodeInfo node;

    public UiNode(AccessibilityNodeInfo node) {
        this.node = node;
    }

    // ========== 查询方法 ==========

    public static List<UiNode> findByText(AccessibilityNodeInfo root, String text) {
        List<UiNode> result = new ArrayList<>();
        if (root == null) return result;

        List<AccessibilityNodeInfo> nodes = root.findAccessibilityNodeInfosByText(text);
        for (AccessibilityNodeInfo node : nodes) {
            result.add(new UiNode(node));
        }
        return result;
    }

    public static List<UiNode> findById(AccessibilityNodeInfo root, String viewId) {
        List<UiNode> result = new ArrayList<>();
        if (root == null) return result;

        List<AccessibilityNodeInfo> nodes = root.findAccessibilityNodeInfosByViewId(viewId);
        for (AccessibilityNodeInfo node : nodes) {
            result.add(new UiNode(node));
        }
        return result;
    }

    public static List<UiNode> findByClass(AccessibilityNodeInfo root, String className) {
        List<UiNode> result = new ArrayList<>();
        findByClassRecursive(root, className, result);
        return result;
    }

    private static void findByClassRecursive(AccessibilityNodeInfo node, String className, List<UiNode> result) {
        if (node == null) return;

        if (node.getClassName() != null && node.getClassName().toString().contains(className)) {
            result.add(new UiNode(node));
        }

        for (int i = 0; i < node.getChildCount(); i++) {
            findByClassRecursive(node.getChild(i), className, result);
        }
    }

    public static List<UiNode> findByCombine(AccessibilityNodeInfo root, CombineFilter filter) {
        List<UiNode> result = new ArrayList<>();
        findByCombineRecursive(root, filter, result);
        return result;
    }

    private static void findByCombineRecursive(AccessibilityNodeInfo node, CombineFilter filter, List<UiNode> result) {
        if (node == null) return;

        if (filter.match(node)) {
            result.add(new UiNode(node));
        }

        for (int i = 0; i < node.getChildCount(); i++) {
            findByCombineRecursive(node.getChild(i), filter, result);
        }
    }

    // ========== 滚动查找 ==========

    public static UiNode scrollForwardUntil(AccessibilityNodeInfo scrollable, Predicate<UiNode> predicate) {
        if (scrollable == null || !scrollable.isScrollable()) return null;

        for (int i = 0; i < 10; i++) { // 最多滚动 10 次
            // 查找当前屏幕
            List<UiNode> nodes = findAll(scrollable);
            for (UiNode node : nodes) {
                if (predicate.test(node)) {
                    return node;
                }
            }

            // 滚动
            boolean scrolled = scrollable.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
            if (!scrolled) break;

            try {
                Thread.sleep(500); // 等待滚动完成
            } catch (InterruptedException e) {
                break;
            }
        }

        return null;
    }

    public static UiNode scrollBackwardUntil(AccessibilityNodeInfo scrollable, Predicate<UiNode> predicate) {
        if (scrollable == null || !scrollable.isScrollable()) return null;

        for (int i = 0; i < 10; i++) {
            List<UiNode> nodes = findAll(scrollable);
            for (UiNode node : nodes) {
                if (predicate.test(node)) {
                    return node;
                }
            }

            boolean scrolled = scrollable.performAction(AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD);
            if (!scrolled) break;

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                break;
            }
        }

        return null;
    }

    private static List<UiNode> findAll(AccessibilityNodeInfo root) {
        List<UiNode> result = new ArrayList<>();
        findAllRecursive(root, result);
        return result;
    }

    private static void findAllRecursive(AccessibilityNodeInfo node, List<UiNode> result) {
        if (node == null) return;
        result.add(new UiNode(node));

        for (int i = 0; i < node.getChildCount(); i++) {
            findAllRecursive(node.getChild(i), result);
        }
    }

    // ========== 操作方法 ==========

    public boolean click() {
        if (node == null) return false;
        return node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
    }

    public boolean longClick() {
        if (node == null) return false;
        return node.performAction(AccessibilityNodeInfo.ACTION_LONG_CLICK);
    }

    public boolean setText(String text) {
        if (node == null) return false;

        Bundle arguments = new Bundle();
        arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, text);
        return node.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
    }

    public boolean scrollForward() {
        if (node == null) return false;
        return node.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
    }

    public boolean scrollBackward() {
        if (node == null) return false;
        return node.performAction(AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD);
    }

    // ========== 属性获取 ==========

    public String getText() {
        if (node == null || node.getText() == null) return "";
        return node.getText().toString();
    }

    public String getClassName() {
        if (node == null || node.getClassName() == null) return "";
        return node.getClassName().toString();
    }

    public String getViewId() {
        if (node == null || node.getViewIdResourceName() == null) return "";
        return node.getViewIdResourceName();
    }

    public boolean isClickable() {
        return node != null && node.isClickable();
    }

    public boolean isChecked() {
        return node != null && node.isChecked();
    }

    public boolean isEnabled() {
        return node != null && node.isEnabled();
    }

    public boolean isScrollable() {
        return node != null && node.isScrollable();
    }

    public Rect getBounds() {
        if (node == null) return new Rect();
        Rect bounds = new Rect();
        node.getBoundsInScreen(bounds);
        return bounds;
    }

    public AccessibilityNodeInfo getNode() {
        return node;
    }

    // ========== 静态工具方法 ==========

    public static void click(AccessibilityNodeInfo node) {
        if (node != null) {
            node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
    }

    public static void scrollForward(AccessibilityNodeInfo root) {
        if (root == null) return;

        List<AccessibilityNodeInfo> scrollables = findScrollableNodes(root);
        if (!scrollables.isEmpty()) {
            scrollables.get(0).performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
        }
    }

    private static List<AccessibilityNodeInfo> findScrollableNodes(AccessibilityNodeInfo root) {
        List<AccessibilityNodeInfo> result = new ArrayList<>();
        if (root == null) return result;

        if (root.isScrollable()) {
            result.add(root);
        }

        for (int i = 0; i < root.getChildCount(); i++) {
            result.addAll(findScrollableNodes(root.getChild(i)));
        }

        return result;
    }
}
```

---

## 四、过滤器设计

### 4.1 NodeFilter (接口)

```java
package com.vendor.rat.auto.filter;

public interface NodeFilter {
    boolean match(AccessibilityNodeInfo node);
}
```

### 4.2 StringCondition

```java
package com.vendor.rat.auto.condition;

public class StringCondition implements NodeFilter {
    public enum Type {
        EQUALS,
        CONTAINS,
        STARTS_WITH,
        ENDS_WITH,
        REGEX
    }

    private Type type;
    private String value;
    private String field; // "text" or "className" or "viewId"

    public StringCondition(String field, Type type, String value) {
        this.field = field;
        this.type = type;
        this.value = value;
    }

    @Override
    public boolean match(AccessibilityNodeInfo node) {
        if (node == null) return false;

        String nodeValue = getFieldValue(node);
        if (nodeValue == null) return false;

        switch (type) {
            case EQUALS:
                return nodeValue.equals(value);
            case CONTAINS:
                return nodeValue.contains(value);
            case STARTS_WITH:
                return nodeValue.startsWith(value);
            case ENDS_WITH:
                return nodeValue.endsWith(value);
            case REGEX:
                return nodeValue.matches(value);
            default:
                return false;
        }
    }

    private String getFieldValue(AccessibilityNodeInfo node) {
        switch (field) {
            case "text":
                return node.getText() != null ? node.getText().toString() : "";
            case "className":
                return node.getClassName() != null ? node.getClassName().toString() : "";
            case "viewId":
                return node.getViewIdResourceName() != null ? node.getViewIdResourceName() : "";
            default:
                return "";
        }
    }
}
```

### 4.3 BoolCondition

```java
package com.vendor.rat.auto.condition;

public class BoolCondition implements NodeFilter {
    public enum Type {
        CLICKABLE,
        CHECKED,
        ENABLED,
        SCROLLABLE,
        FOCUSABLE
    }

    private Type type;
    private boolean expectedValue;

    public BoolCondition(Type type, boolean expectedValue) {
        this.type = type;
        this.expectedValue = expectedValue;
    }

    @Override
    public boolean match(AccessibilityNodeInfo node) {
        if (node == null) return false;

        boolean actualValue;
        switch (type) {
            case CLICKABLE:
                actualValue = node.isClickable();
                break;
            case CHECKED:
                actualValue = node.isChecked();
                break;
            case ENABLED:
                actualValue = node.isEnabled();
                break;
            case SCROLLABLE:
                actualValue = node.isScrollable();
                break;
            case FOCUSABLE:
                actualValue = node.isFocusable();
                break;
            default:
                return false;
        }

        return actualValue == expectedValue;
    }
}
```

### 4.4 CombineFilter

```java
package com.vendor.rat.auto.filter;

public class CombineFilter implements NodeFilter {
    public enum Operator {
        AND,
        OR
    }

    private List<NodeFilter> filters;
    private Operator operator;

    public CombineFilter(Operator operator) {
        this.operator = operator;
        this.filters = new ArrayList<>();
    }

    public CombineFilter add(NodeFilter filter) {
        filters.add(filter);
        return this;
    }

    @Override
    public boolean match(AccessibilityNodeInfo node) {
        if (filters.isEmpty()) return true;

        if (operator == Operator.AND) {
            for (NodeFilter filter : filters) {
                if (!filter.match(node)) {
                    return false;
                }
            }
            return true;
        } else { // OR
            for (NodeFilter filter : filters) {
                if (filter.match(node)) {
                    return true;
                }
            }
            return false;
        }
    }

    // 流式构建器
    public static class Builder {
        private CombineFilter filter;

        public Builder() {
            this.filter = new CombineFilter(Operator.AND);
        }

        public Builder and() {
            filter.operator = Operator.AND;
            return this;
        }

        public Builder or() {
            filter.operator = Operator.OR;
            return this;
        }

        public Builder text(String text) {
            filter.add(new StringCondition("text", StringCondition.Type.CONTAINS, text));
            return this;
        }

        public Builder className(String className) {
            filter.add(new StringCondition("className", StringCondition.Type.CONTAINS, className));
            return this;
        }

        public Builder viewId(String viewId) {
            filter.add(new StringCondition("viewId", StringCondition.Type.EQUALS, viewId));
            return this;
        }

        public Builder clickable(boolean clickable) {
            filter.add(new BoolCondition(BoolCondition.Type.CLICKABLE, clickable));
            return this;
        }

        public Builder checked(boolean checked) {
            filter.add(new BoolCondition(BoolCondition.Type.CHECKED, checked));
            return this;
        }

        public CombineFilter build() {
            return filter;
        }
    }
}
```

---

## 五、使用示例

### 5.1 简单查询

```java
// 查找文本
List<UiNode> nodes = UiNode.findByText(root, "设置");

// 查找 ID
List<UiNode> nodes = UiNode.findById(root, "android:id/button1");

// 查找类名
List<UiNode> nodes = UiNode.findByClass(root, "Switch");
```

### 5.2 组合过滤

```java
// 查找可点击的、包含"确定"文本的按钮
CombineFilter filter = new CombineFilter.Builder()
    .text("确定")
    .className("Button")
    .clickable(true)
    .build();

List<UiNode> nodes = UiNode.findByCombine(root, filter);
```

### 5.3 滚动查找

```java
// 滚动查找应用名称
UiNode node = UiNode.scrollForwardUntil(scrollable, uiNode -> {
    return uiNode.getText().equals("我的应用");
});

if (node != null) {
    node.click();
}
```

### 5.4 操作节点

```java
UiNode node = nodes.get(0);

// 点击
node.click();

// 长按
node.longClick();

// 设置文本
node.setText("Hello World");

// 滚动
node.scrollForward();
```

---

## 六、实施计划

### Phase 1: 核心类（2 天）

- [ ] UiNode 实现
- [ ] 查询方法
- [ ] 操作方法

### Phase 2: 过滤器（2 天）

- [ ] NodeFilter 接口
- [ ] StringCondition
- [ ] BoolCondition
- [ ] CombineFilter

### Phase 3: 高级功能（2 天）

- [ ] 滚动查找
- [ ] 流式构建器
- [ ] 单元测试

**总计**: 6 天

---

## 七、验收标准

| 功能 | 验收标准 |
|------|---------|
| 节点查询 | 准确查找目标节点 |
| 节点过滤 | 多条件组合正确 |
| 节点操作 | 点击/滚动成功 |
| 滚动查找 | 找到目标或超时 |

---

**文档版本**: 1.0
**最后更新**: 2026-03-16
**负责人**: UI 自动化组
