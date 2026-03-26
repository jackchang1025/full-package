# OppoPermissionEngine CombineFilter 审计报告

**审计日期**: 2026-03-26
**审计文件**: `android/app/src/main/java/com/vendor/rat/auto/engine/vendor/OppoPermissionEngine.java`
**审计目标**: 识别所有 CombineFilter 和 StringCondition 使用，设计对应的 GKD 选择器

---

## 审计结果汇总

**总计**: 5 处 CombineFilter/StringCondition 使用
**分类**:
- 窗口检测: 2 处
- 列表页操作: 2 处
- 详情页操作: 1 处

---

## 详细审计

### 1. 权限列表页检测 (isInPermissionList)

**位置**: 行 108-110
**当前代码**:
```java
UiNode denied = root.findOneByCombine(CombineFilter.and(
    StringCondition.className("android.widget.TextView"),
    StringCondition.textEquals("不允许")));
```

**用途**: 检测是否在权限管理列表页（通过查找"不允许"分组标题）

**GKD 选择器**:
```
TextView[text="不允许"]
```

**替换后代码**:
```java
UiNode denied = root.findOne("TextView[text=\"不允许\"]");
```

---

### 2. 权限详情页检测 (isInPermissionDetail)

**位置**: 行 122-123
**当前代码**:
```java
UiNode radio = root.findOneByCombine(
    StringCondition.className("android.widget.RadioButton"));
```

**用途**: 检测是否在权限子页面（通过查找 RadioButton）

**GKD 选择器**:
```
RadioButton
```

**替换后代码**:
```java
UiNode radio = root.findOne("RadioButton");
```

---

### 3. 查找"不允许"状态的权限条目 (handlePermissionList)

**位置**: 行 141-143
**当前代码**:
```java
List<UiNode> allNodes = root.findAllByCombine(CombineFilter.and(
    StringCondition.className("android.widget.TextView"),
    StringCondition.textEquals("不允许")));
```

**用途**: 查找所有状态为"不允许"的权限条目

**GKD 选择器**:
```
TextView[text="不允许"]
```

**替换后代码**:
```java
List<UiNode> allNodes = root.findAll("TextView[text=\"不允许\"]");
```

---

### 4. 提取权限名称 (getPermNameFromRow)

**位置**: 行 226-227
**当前代码**:
```java
List<UiNode> textViews = row.findAllByCombine(
    StringCondition.className("android.widget.TextView"));
```

**用途**: 从 clickable 行中提取所有 TextView（用于查找权限名称）

**GKD 选择器**:
```
TextView
```

**替换后代码**:
```java
List<UiNode> textViews = row.findAll("TextView");
```

---

### 5. 查找允许选项 (handlePermissionDetail)

**位置**: 行 197-201
**当前代码**:
```java
UiNode row = root.findOneByCombineWithChild(
    CombineFilter.clickable(),
    CombineFilter.and(
        StringCondition.className("android.widget.TextView"),
        StringCondition.textEquals(allowText)));
```

**用途**: 查找包含特定文本（"始终允许"/"使用时允许"/"允许"）的可点击行

**GKD 选择器**:
```
[clickable=true] > TextView[text="始终允许"]
[clickable=true] > TextView[text="使用时允许"]
[clickable=true] > TextView[text="允许"]
```

**替换后代码**:
```java
UiNode row = root.findOne("[clickable=true] > TextView[text=\"" + allowText + "\"]");
```

---

## 替换优先级

按影响范围和复杂度排序：

1. **优先级 1** (简单替换):
   - 位置 2: `RadioButton` 检测
   - 位置 4: `TextView` 查找

2. **优先级 2** (带属性过滤):
   - 位置 1: `TextView[text="不允许"]` 检测
   - 位置 3: `TextView[text="不允许"]` 查找

3. **优先级 3** (父子关系):
   - 位置 5: `[clickable=true] > TextView[text=...]` 查找

---

## 依赖关系

所有替换依赖于 `UiNode` 类实现以下方法：
- `findOne(String gkdSelector)` — 替代 `findOneByCombine`
- `findAll(String gkdSelector)` — 替代 `findAllByCombine`

**注意**: 位置 5 使用了 `findOneByCombineWithChild`，需要 GKD 选择器支持父子关系（`>` 运算符）。

---

## 测试建议

替换后需要验证：
1. 权限列表页识别正确
2. 权限详情页识别正确
3. "不允许"权限条目查找准确
4. 允许选项点击成功（优先级: 始终允许 > 使用时允许 > 允许）
5. 权限名称提取正确（排除状态文本）

---

## 审计结论

OppoPermissionEngine 共有 5 处需要替换的 CombineFilter/StringCondition 使用。所有位置都可以用 GKD 选择器替换，其中位置 5 需要父子关系支持（`>` 运算符）。

替换后代码将更简洁，且与 GKD 规则语法保持一致。
