# GKD Selector 集成文档

> **版本**: 1.0
> **日期**: 2026-03-26
> **状态**: 已集成，CRITICAL 修复已完成

---

## 一、背景

### 1.1 为什么引入 GKD Selector

原有 UI 自动化框架基于 `CombineFilter` + `StringCondition` 手动遍历节点树，存在以下问题：

- 每个厂商引擎大量重复的节点遍历代码
- 无法表达父子/兄弟关系匹配（需要多步手动遍历）
- 文本匹配硬编码在 Java 代码中，系统更新后需要重新打包

### 1.2 调研结论

| 项目 | Stars | 定位 | 适合集成 |
|------|-------|------|---------|
| **GKD** | 37k | CSS-like 选择器引擎 | ✅ Selector 模块可独立提取 |
| Assists | 877 | AccessibilityService 封装库 | ⚠️ 无厂商适配 |
| AutoJs6 | 5k | JS 自动化 IDE | ❌ 独立 App，不是库 |

GKD 的 `selector` 模块是 Kotlin Multiplatform 库，无 Android 依赖，可独立引入。

### 1.3 Vendor APK 分析

Vendor APK **未使用任何开源框架**，纯原生 AccessibilityService 实现。我们的 GKD Selector 集成是对 vendor 架构的增强。

---

## 二、架构

### 2.1 集成层次

```
┌──────────────────────────────────────────────────────────┐
│              厂商引擎层 (OppoEngine, HuaweiEngine...)     │
│   使用 GkdSelectorHelper.findOne/findAll                  │
└──────────────────────────────────────────────────────────┘
                          ↓
┌──────────────────────────────────────────────────────────┐
│              GkdSelectorHelper (Java 桥接层)              │
│   - 选择器缓存 (ConcurrentHashMap)                        │
│   - querySelector/querySelectorAll 遍历子树               │
│   - escapeForSelector 防注入                              │
└──────────────────────────────────────────────────────────┘
                          ↓
┌──────────────────────────────────────────────────────────┐
│              GkdTransform.kt (Kotlin 适配层)              │
│   - getAttr: QueryContext 解包 + Boolean 类型              │
│   - getName: className                                    │
│   - getChildren: node.getChild(i)                         │
│   - getParent: node.parent                                │
│   - getRoot: 向上遍历到根节点                               │
└──────────────────────────────────────────────────────────┘
                          ↓
┌──────────────────────────────────────────────────────────┐
│              GKD Selector 库 (li.songe.selector)          │
│   - Selector.parse("CSS-like 选择器")                     │
│   - Transform.querySelector() 深度优先遍历                 │
│   - 属性过滤、关系选择、正则匹配                             │
└──────────────────────────────────────────────────────────┘
```

### 2.2 文件清单

| 文件 | 职责 |
|------|------|
| `android/selector/` | GKD Selector 库模块（从 gkd-kit/gkd 提取） |
| `auto/selector/GkdTransform.kt` | AccessibilityNodeInfo → GKD Transform 适配 |
| `auto/util/GkdSelectorHelper.java` | Java 桥接 API：findOne / findAll / escapeForSelector |
| `auto/util/ScreenAdaptUtil.java` | 坐标按屏幕分辨率比例适配 |
| `app/build.gradle` | `implementation project(':selector')` |
| `app/proguard-rules.pro` | `-keep class li.songe.selector.** { *; }` |

---

## 三、GKD 选择器语法

### 3.1 属性选择器

```
TextView                         — className 匹配
TextView[text="精确匹配"]          — 文本精确匹配
TextView[text*="包含"]            — 文本包含
TextView[text^="前缀"]            — 文本开头
TextView[text$="后缀"]            — 文本结尾
[id$="button1"]                  — resource-id 后缀匹配
[id*="permission_allow"]         — resource-id 包含
[clickable=true]                 — Boolean 属性 (必须返回 Boolean 非 String)
[checked=false]                  — Boolean 属性
RadioButton[checked=true]        — className + Boolean 属性
```

### 3.2 关系选择器

```
A > B       直接子节点 (B 是 A 的直接子元素)
A >n B      任意深度后代 (B 是 A 的后代，穿透中间层) ← 常用
A < B       直接父节点
A <<n B     任意深度祖先
A + B       相邻兄弟 (紧接的下一个)
A - B       前置兄弟
```

### 3.3 ⚠️ 关键陷阱：`>` vs `>n`

**OPPO UI 典型结构：**
```
LinearLayout [clickable=true]
  └─ RelativeLayout [clickable=false]    ← 中间层！
       └─ TextView [text="允许"]
```

- ❌ `[clickable=true] > TextView[text="允许"]` — **不匹配**，`>` 要求直接子节点
- ✅ `[clickable=true] >n TextView[text="允许"]` — **匹配**，`>n` 穿透中间层

**规则：项目中统一使用 `>n` 而非 `>`，除非明确知道是直接父子关系。**

### 3.4 逻辑运算

```
[text="允许" && clickable=true]    — AND
[text="允许" || text="同意"]       — OR
[text!="拒绝"]                    — NOT
```

---

## 四、使用指南

### 4.1 Java 调用

```java
import com.vendor.rat.auto.util.GkdSelectorHelper;

// 查找单个节点
UiNode node = GkdSelectorHelper.findOne(root, "TextView[text*=\"耗电管理\"]");

// 查找所有匹配节点
List<UiNode> nodes = GkdSelectorHelper.findAll(root, "TextView[text=\"不允许\"]");

// 动态文本必须 escape
String appName = getAppName();  // 可能含特殊字符
UiNode node = GkdSelectorHelper.findOne(root,
    "TextView[text*=\"" + GkdSelectorHelper.escapeForSelector(appName) + "\"]");

// 父子关系匹配（使用 >n 不用 >）
UiNode row = GkdSelectorHelper.findOne(root,
    "[clickable=true] >n TextView[text=\"完全允许后台行为\"]");
```

### 4.2 常用选择器模板

| 场景 | 选择器 |
|------|--------|
| 查找文本 | `TextView[text*="耗电管理"]` |
| 查找按钮 | `Button[text="允许"]` |
| 查找 clickable 行包含文本 | `[clickable=true] >n TextView[text="允许自启动"]` |
| 查找 ID | `[id$="button1"]` |
| 查找 RadioButton | `RadioButton[checked=true]` |
| 查找 Switch | `Switch[checked=false]` |
| 查找可滚动容器 | `[scrollable=true]` |

---

## 五、GkdTransform 适配细节

### 5.1 getAttr — QueryContext 解包

GKD 的 `getAttr(Any, String)` 第一个参数传入 `QueryContext<T>` 而非 `T`：

```kotlin
getAttr = { target, name ->
    when (target) {
        is QueryContext<*> -> {
            val node = target.current as? AccessibilityNodeInfo
            getNodeAttr(node, name)
        }
        is AccessibilityNodeInfo -> getNodeAttr(target, name)
        is CharSequence -> getCharSequenceAttr(target, name)
        else -> null
    }
}
```

### 5.2 Boolean 属性

`[clickable=true]` 中 GKD 用 `==` 比较，`true` 是 `Boolean` 类型。`getAttr` 必须返回 `Boolean` 而非 `String`：

```kotlin
"clickable" -> node.isClickable      // ✅ 返回 Boolean
"clickable" -> node.isClickable.toString()  // ❌ 返回 String "true"，== Boolean true 永远 false
```

### 5.3 支持的属性

| 属性名 | 返回类型 | 说明 |
|--------|---------|------|
| `text` | CharSequence? | 文本内容 |
| `desc` | CharSequence? | contentDescription |
| `id` / `vid` | String? | viewIdResourceName |
| `clickable` | Boolean | 是否可点击 |
| `checked` | Boolean | 是否选中 |
| `enabled` | Boolean | 是否启用 |
| `scrollable` | Boolean | 是否可滚动 |
| `focusable` | Boolean | 是否可聚焦 |
| `selected` | Boolean | 是否被选择 |
| `checkable` | Boolean | 是否可勾选 |
| `visibleToUser` | Boolean | 是否对用户可见 |
| `childCount` | Int | 子节点数量 |
| `index` | Int? | 在父节点中的索引 |
| `depth` | Int | 节点深度 |

---

## 六、选择器缓存

`GkdSelectorHelper` 使用 `ConcurrentHashMap` 缓存已解析的选择器：

```java
private static final ConcurrentHashMap<String, Selector> selectorCache = new ConcurrentHashMap<>();

private static Selector getOrParseSelector(String selector) {
    return selectorCache.computeIfAbsent(selector, Selector.Companion::parse);
}
```

相同选择器字符串只解析一次，后续调用直接从缓存获取。

---

## 七、PermissionController 坐标点击

Android 16 的 `com.android.permissioncontroller` 使用 `accessibilityDataSensitive`，无障碍服务完全无法获取节点树。

**Fallback 方案：** `ScreenAdaptUtil` 按屏幕比例计算坐标点击。

```java
// 基准设备: OPPO Find X6, 1240x2772
// "始终允许" 按钮 center(550, 1052)
int[] coord = ScreenAdaptUtil.getPermissionAllowCoordinate(screenWidth, screenHeight);
MiscUtils.tapAtCoordinate(coord[0], coord[1]);
```

---

## 八、测试

### 8.1 单元测试

| 测试文件 | 数量 | 说明 |
|---------|------|------|
| `OppoGkdSelectorFixtureTest.java` | 43 | GKD 选择器 + 真机 XML fixture |
| `ScreenAdaptUtilTest.java` | 7 | 坐标适配 |

### 8.2 E2E 真机测试

```bash
bash android/scripts/e2e_oppo_test.sh <device_address>
```

---

## 九、已知问题

### 9.1 待修复

| 问题 | 状态 | 说明 |
|------|------|------|
| PermissionController 坐标点击未命中 | 🔴 待修复 | 位置/摄像头/麦克风权限页面 |
| 权限循环卡在 PermissionController | 🔴 待修复 | 需要检测包名并跳过或坐标点击 |
| E2E 设备状态污染 | 🟡 待优化 | 多次运行后 Pipeline 跳过保活 |

### 9.2 已修复

| 问题 | Commit | 说明 |
|------|--------|------|
| getAttr 接收 QueryContext 失败 | `5aa2d80d` | CRITICAL — 属性过滤器全部返回 null |
| findOne 不遍历子树 | `86e7bcd1` | CRITICAL — 只检查根节点 |
| `#id` 无效语法 | `b967d362` | CRITICAL — GKD 不支持 CSS `#` 语法 |
| `>` 不穿透中间层 | `c662dcdb` | CRITICAL — OPPO UI 有 RelativeLayout 中间层 |
| ProGuard 混淆 | `d2e567a0` | release 构建崩溃 |
| Boolean 属性返回 String | `5aa2d80d` | `[clickable=true]` 永远不匹配 |
| ColorOS 16 RadioButton 模式 | `6814f6ac` | 耗电管理页无 Switch |
| ColorOS 16 自启动移除 | `54617942` | 失败阻塞整个流程 |
