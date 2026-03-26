# OppoEngine CombineFilter 使用审计报告

**日期**: 2026-03-26
**文件**: `android/app/src/main/java/com/vendor/rat/auto/engine/vendor/OppoEngine.java`
**目的**: 为 GKD 选择器重构做准备，记录所有 CombineFilter/StringCondition/findNode 使用

---

## 1. 过滤器构建方法 (Filter Builders)

### 1.1 buildTextViewContainsFilter (行 198)
- **位置**: Line 198
- **用途**: k0() 窗口检测 — 验证应用名可见
- **当前逻辑**: `root.findOneByCombine(buildTextViewContainsFilter(targetName))`
- **对应 GKD 选择器**: `TextView[text*="${targetName}"]`

### 1.2 buildAppInBackgroundFilter (行 212, 330-335)
- **位置**: Line 212 (调用), Line 330-335 (定义)
- **用途**: l0() 窗口检测 — 验证"后台运行"文本可见
- **当前逻辑**:
  ```java
  CombineFilter bgFilter = buildAppInBackgroundFilter();
  return bgFilter == null || root.findOneByCombine(bgFilter) != null;
  ```
- **配置键**: `COLORS_APP_IN_BACKGROUND_TEXT`
- **对应 GKD 选择器**: `TextView[text*="${config.COLORS_APP_IN_BACKGROUND_TEXT}"]`

### 1.3 buildAllowButtonFilter (行 226, 309-314)
- **位置**: Line 226 (调用), Line 309-314 (定义)
- **用途**: j0() 窗口检测 — 验证"允许"按钮可见
- **当前逻辑**:
  ```java
  CombineFilter btnFilter = buildAllowButtonFilter();
  return btnFilter == null || root.findOneByCombine(btnFilter) != null;
  ```
- **配置键**: `COLORS_SETTINGS_ALLOW_BUTTON_TEXT`
- **对应 GKD 选择器**: `Button[text="${config.COLORS_SETTINGS_ALLOW_BUTTON_TEXT}"]`

### 1.4 buildPowerManageFilter (行 289-291)
- **位置**: Line 289-291
- **用途**: handleAppDetailState — 查找"耗电管理"栏目
- **当前逻辑**:
  ```java
  CombineFilter powerFilter1 = buildPowerManageFilter();
  target = scrollView.scrollForwardUntil(powerFilter1);
  ```
- **配置键**: `COLORS_SETTINGS_POWER_MANAGE_TEXT`
- **对应 GKD 选择器**: `TextView[text="${config.COLORS_SETTINGS_POWER_MANAGE_TEXT}"]`

### 1.5 buildPowerManage2Filter (行 293-296)
- **位置**: Line 293-296
- **用途**: handleAppDetailState — 查找"耗电管理"栏目 (备用文本)
- **当前逻辑**: Fallback for powerFilter1
- **配置键**: `COLORS_SETTINGS_POWER_MANAGE_2_TEXT`
- **对应 GKD 选择器**: `TextView[text="${config.COLORS_SETTINGS_POWER_MANAGE_2_TEXT}"]`

### 1.6 buildAllowBackgroundFilter (行 299-301)
- **位置**: Line 299-301
- **用途**: handleFullBackgroundSwitch — 查找"允许后台运行"行
- **当前逻辑**: `findRowWithChild(buildAllowBackgroundFilter())`
- **配置键**: `COLORS_SETTINGS_ALLOW_APP_IN_BACKGROUND_TEXT`
- **对应 GKD 选择器**: `TextView[text="${config.COLORS_SETTINGS_ALLOW_APP_IN_BACKGROUND_TEXT}"]`

### 1.7 buildAllowAutoStartFilter (行 304-306)
- **位置**: Line 304-306
- **用途**: handleAutoStartSwitch — 查找"允许自启动"行
- **当前逻辑**: `findRowWithChild(buildAllowAutoStartFilter())`
- **配置键**: `COLORS_SETTINGS_ALLOW_APP_AUTO_START_TEXT`
- **对应 GKD 选择器**: `TextView[text="${config.COLORS_SETTINGS_ALLOW_APP_AUTO_START_TEXT}"]`

### 1.8 buildFullBackgroundFilter (行 317-319)
- **位置**: Line 317-319
- **用途**: handleFullBackgroundSwitch — 查找"完全允许后台"行
- **当前逻辑**: `findRowWithChild(buildFullBackgroundFilter())`
- **配置键**: `COLORS_SETTINGS_ALLOW_FULL_IN_BACKGROUND_TEXT`
- **对应 GKD 选择器**: `TextView[text="${config.COLORS_SETTINGS_ALLOW_FULL_IN_BACKGROUND_TEXT}"]`

### 1.9 buildRelateStartFilter (行 322-327)
- **位置**: Line 322-327
- **用途**: handleRelateStartSwitch — 查找"关联启动"行
- **当前逻辑**: `findRowWithChild(buildRelateStartFilter())`
- **配置键**: `COLORS_SETTINGS_ALLOW_APP_RELATE_START_TEXT`
- **对应 GKD 选择器**: `TextView[text*="${config.COLORS_SETTINGS_ALLOW_APP_RELATE_START_TEXT}"]`

---

## 2. findNode 调用点 (Node Search Calls)

### 2.1 findOneByCombine — 应用名验证 (行 198)
- **位置**: Line 198
- **场景**: k0() 窗口检测
- **逻辑**: `root.findOneByCombine(buildTextViewContainsFilter(targetName))`
- **GKD**: `TextView[text*="${targetName}"]`

### 2.2 findOneByCombine — 后台运行文本验证 (行 213)
- **位置**: Line 213
- **场景**: l0() 窗口检测
- **逻辑**: `root.findOneByCombine(bgFilter)`
- **GKD**: `TextView[text*="${config.COLORS_APP_IN_BACKGROUND_TEXT}"]`

### 2.3 findOneByCombine — 允许按钮验证 (行 227)
- **位置**: Line 227
- **场景**: j0() 窗口检测
- **逻辑**: `root.findOneByCombine(btnFilter)`
- **GKD**: `Button[text="${config.COLORS_SETTINGS_ALLOW_BUTTON_TEXT}"]`

### 2.4 scrollForwardUntil/scrollBackwardUntil — 耗电管理栏目 (行 359-368)
- **位置**: Line 359-368
- **场景**: handleAppDetailState
- **逻辑**:
  ```java
  target = scrollView.scrollForwardUntil(powerFilter1);
  if (target == null) target = scrollView.scrollBackwardUntil(powerFilter1);
  if (target == null && powerFilter2 != null) {
      target = scrollView.scrollBackwardUntil(powerFilter2);
      if (target == null) target = scrollView.scrollForwardUntil(powerFilter2);
  }
  ```
- **GKD**:
  ```
  @ScrollableView > TextView[text="${config.COLORS_SETTINGS_POWER_MANAGE_TEXT}"]
  @ScrollableView > TextView[text="${config.COLORS_SETTINGS_POWER_MANAGE_2_TEXT}"]
  ```

### 2.5 findOneByCombine — 耗电管理栏目 (fallback, 行 374-378)
- **位置**: Line 374-378
- **场景**: handleAppDetailState (无滚动视图时)
- **逻辑**:
  ```java
  target = k().findOneByCombine(powerFilter1);
  if (target == null && powerFilter2 != null) {
      target = k().findOneByCombine(powerFilter2);
  }
  ```
- **GKD**: 同 2.4

### 2.6 findOneByCombine — 允许按钮点击 (行 435)
- **位置**: Line 435
- **场景**: handleDialogState
- **逻辑**: `UiNode btn = k().findOneByCombine(allowBtnFilter);`
- **GKD**: `Button[text="${config.COLORS_SETTINGS_ALLOW_BUTTON_TEXT}"]`

### 2.7 scrollForwardUntil + findOneByCombineWithChild — 自启动管理 (行 465-469)
- **位置**: Line 465-469
- **场景**: handleStartupState
- **逻辑**:
  ```java
  UiNode textNode = scrollView.scrollForwardUntil(textFilter);
  row = k().findOneByCombineWithChild(CombineFilter.clickable(), textFilter);
  ```
- **GKD**:
  ```
  @ScrollableView > TextView[text*="${targetName}"]
  [clickable=true] > TextView[text*="${targetName}"]
  ```

### 2.8 findOneByCombineWithChild — 自启动管理 (fallback, 行 472-474)
- **位置**: Line 472-474
- **场景**: handleStartupState (无滚动视图时)
- **逻辑**: `row = k().findOneByCombineWithChild(CombineFilter.clickable(), textFilter);`
- **GKD**: `[clickable=true] > TextView[text*="${targetName}"]`

### 2.9 findOneByCombineWithChild — 查找开关行 (行 501)
- **位置**: Line 501
- **场景**: findRowWithChild (通用方法)
- **逻辑**: `root.findOneByCombineWithChild(CombineFilter.clickable(), childFilter)`
- **GKD**: `[clickable=true] > ${childFilter}`
- **调用点**:
  - Line 512: 完全允许后台 (buildFullBackgroundFilter)
  - Line 514: 允许后台运行 (buildAllowBackgroundFilter)
  - Line 585: 允许自启动 (buildAllowAutoStartFilter)
  - Line 616: 允许关联启动 (buildRelateStartFilter)

### 2.10 findOneByCombine — 对话框按钮 (行 544-545)
- **位置**: Line 544-545
- **场景**: handleFullBackgroundSwitch (RadioButton 模式对话框)
- **逻辑**: `UiNode allowBtn = dialogRoot.findOneByCombine(StringCondition.viewId("android:id/button1"));`
- **GKD**: `* #android:id/button1`

### 2.11 findOneByCombine — 对话框按钮 (fallback, 行 556)
- **位置**: Line 556
- **场景**: handleFullBackgroundSwitch (文本匹配 fallback)
- **逻辑**: `UiNode textBtn = dialogRoot.findOneByCombine(btnFilter);`
- **GKD**: `Button[text="${config.COLORS_SETTINGS_ALLOW_BUTTON_TEXT}"]`

### 2.12 findOneByCombine — 权限管理入口 (行 749)
- **位置**: Line 749
- **场景**: handlePermissionManagement
- **逻辑**:
  ```java
  CombineFilter permFilter = CombineFilter.and(
      StringCondition.className("android.widget.TextView"),
      StringCondition.textContains("权限管理"));
  UiNode permItem = root.findOneByCombine(permFilter);
  ```
- **GKD**: `TextView[text*="权限管理"]`

### 2.13 scrollForwardUntil — 权限管理入口 (行 754)
- **位置**: Line 754
- **场景**: handlePermissionManagement (滚动查找)
- **逻辑**: `permItem = scrollView.scrollForwardUntil(permFilter);`
- **GKD**: `@ScrollableView > TextView[text*="权限管理"]`

### 2.14 findAllByCombine — 不允许状态权限 (行 822-824)
- **位置**: Line 822-824
- **场景**: findNextDeniedPermissionRow
- **逻辑**:
  ```java
  List<UiNode> deniedTexts = root.findAllByCombine(CombineFilter.and(
      StringCondition.className("android.widget.TextView"),
      StringCondition.textEquals("不允许")));
  ```
- **GKD**: `TextView[text="不允许"]`

### 2.15 findAllByCombine — 权限名提取 (行 855-856)
- **位置**: Line 855-856
- **场景**: extractPermName
- **逻辑**:
  ```java
  List<UiNode> tvs = row.findAllByCombine(
      StringCondition.className("android.widget.TextView"));
  ```
- **GKD**: `TextView`

### 2.16 findOneByCombineWithChild — 允许选项 (行 896-900)
- **位置**: Line 896-900
- **场景**: selectBestAllowOption
- **逻辑**:
  ```java
  UiNode row = root.findOneByCombineWithChild(
      CombineFilter.clickable(),
      CombineFilter.and(
          StringCondition.className("android.widget.TextView"),
          StringCondition.textEquals(allowText)));
  ```
- **GKD**: `[clickable=true] > TextView[text="${allowText}"]`
- **allowText**: "始终允许" / "使用时允许" / "允许"

---

## 3. 统计总结

| 类型 | 数量 |
|------|------|
| 过滤器构建方法 | 9 |
| findOneByCombine | 8 |
| findAllByCombine | 2 |
| findOneByCombineWithChild | 6 |
| scrollForwardUntil | 4 |
| scrollBackwardUntil | 3 |
| **总计** | **32** |

---

## 4. GKD 重构优先级

### P0 (核心流程)
1. **应用详情 → 耗电管理** (行 359-378)
2. **开关行查找** (行 501, 512, 514, 585, 616)
3. **自启动管理** (行 465-474)

### P1 (窗口检测)
4. **k0/l0/j0 窗口验证** (行 198, 213, 227)

### P2 (权限管理)
5. **权限管理自动化** (行 749-900)

---

## 5. 下一步

1. 创建 GKD 规则文件 `oppo_gkd_rules.json`
2. 实现 `GkdSelector` 类
3. 逐个替换 CombineFilter 调用
4. 单元测试验证等价性
