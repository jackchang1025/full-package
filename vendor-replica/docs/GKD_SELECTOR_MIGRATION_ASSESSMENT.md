# GKD Selector 引入 vendor-replica 替换逆向选择器 — 系统评估

## 1. 现状问题

### 1.1 逆向选择器缺陷

vendor-replica 当前的 UI 选择器系统源自 Auto.js (hyb1996/NoRootScriptDroid) 的逆向反编译，经 vendor 扩展后再次逆向，存在以下系统性问题：

| 问题 | 实例 | 影响 |
|------|------|------|
| **代码不完整** | Q0() NPE — `getStringConditions()` 在 `initFilter()` 前调用 | ADB 配对 Switch 搜索全部静默失败 |
| **混淆名残留** | `A()`~`Q()` 属性方法, `s()`/`t()`/`q()` 搜索方法 | 维护成本极高 |
| **BFS 注释错误** | 代码用 `ConcurrentLinkedQueue` (BFS) 但 TAG 写 "DFS2" | 行为不可预期 |
| **45 个 Filter 文件** | 每个都可能有逆向缺陷，无测试覆盖 | 每次修 bug 靠真机试错 |
| **条件链断裂** | `CombineFilter.toGlobalSelector()` 转换链中断 | 部分条件丢失 |
| **.pending 文件** | 4 个 Filter 有未合入的完整实现 | 实现不一致 |

### 1.2 影响面统计

```
选择器系统总文件: 57 个 (filter:45 + condition:8 + uisearch:4)
依赖该系统的业务文件: 49 个
  ├── 13 个 Delegate 文件
  ├── 11 个 Delegate Task 文件
  ├── 7 个 Engine 文件
  ├── 2 个 Server 文件
  ├── 2 个 Entity 文件
  └── 14 个其他文件
总调用点: ~558 次 (findOneByCombine 等方法)
```

---

## 2. GKD Selector 库评估

### 2.1 库概况

| 维度 | 详情 |
|------|------|
| **仓库** | [gkd-kit/selector](https://github.com/gkd-kit/selector) |
| **主项目** | [gkd-kit/gkd](https://github.com/gkd-kit/gkd) — 37.6k stars |
| **语言** | Kotlin Multiplatform (Common + JVM + Android) |
| **代码量** | 4,416 行 / 41 个 Kotlin 文件 |
| **依赖** | 零外部依赖 (纯 Kotlin stdlib) |
| **Android SDK** | compileSdk 35, minSdk 24 |
| **许可证** | GPL-3.0 |

### 2.2 架构

```
li.songe.selector/
├── Selector.kt          — 主入口: parse() + match() + querySelector()
├── Transform.kt         — 树遍历抽象 (229 行, 核心)
│   ├── getAttr()        — 节点属性读取
│   ├── getName()        — 节点类名
│   ├── getChildren()    — 子节点序列
│   ├── getParent()      — 父节点
│   ├── getRoot()        — 根节点
│   ├── querySelector()  — 查找第一个匹配
│   └── querySelectorAll() — 查找所有匹配
├── MatchOption.kt       — 配置 (12 行, fastQuery 开关)
├── parser/              — 选择器字符串解析 (6 文件)
├── property/            — 属性表达式 (10 文件: 比较/逻辑/取反)
├── connect/             — 树关系操作符 (6 文件: >/+/~/</ancestor)
└── unit/                — 选择器表达式单元 (5 文件)
```

### 2.3 选择器语法能力

| 语法 | 示例 | 说明 |
|------|------|------|
| **属性等于** | `[text="无线调试"]` | 精确匹配 |
| **属性包含** | `[text*="无线调试"]` | 子串匹配 |
| **属性前缀** | `[text^="无线"]` | startsWith |
| **属性后缀** | `[text$="调试"]` | endsWith |
| **属性正则** | `[text~="\\d{6}"]` | regex 匹配 |
| **类名匹配** | `Switch` / `[name="android.widget.Switch"]` | className |
| **布尔属性** | `[clickable=true]` | Boolean 属性 |
| **整数属性** | `[childCount=3]` | Int 属性 |
| **逻辑 AND** | `[text="OK"][clickable=true]` | 多条件 |
| **逻辑 OR** | `[text="允许"] \|\| [text="确定"]` | 备选条件 |
| **直接子级** | `LinearLayout > Button` | parent → child |
| **任意后代** | `[clickable=true] >n TextView` | ancestor → descendant |
| **任意祖先** | `TextView[text="x"] <n [clickable=true]` | child → ancestor |
| **前兄弟** | `A - B` | 前面的 sibling |
| **后兄弟** | `A + B` | 后面的 sibling |

### 2.4 已验证的集成方案

android 项目已有成熟集成:

```
桥接层 (共 191 行):
├── GkdTransform.kt (91 行) — AccessibilityNodeInfo → Transform<T> 适配
└── GkdSelectorHelper.java (100 行) — Java 调用入口 + 缓存

已支持属性 (14 个):
├── text, desc, id/vid (字符串)
├── clickable, checked, enabled, focusable, scrollable, selected, checkable, visibleToUser (布尔)
└── childCount, index, depth (整数)

生产验证:
├── 48 个调用点 (4 个 Engine 文件)
├── 45 个单元测试 + 7 个真机 XML fixture
└── OPPO/华为真机 ADB 配对全流程通过
```

---

## 3. CombineFilter → GKD 转换可行性

### 3.1 条件类型映射

| vendor 条件类型 | 使用频率 | GKD 原生支持 | 转换难度 | 转换方式 |
|----------------|---------|-------------|---------|---------|
| **StringCondition** (text/id/className/desc) | 90% | ✅ 完全支持 | 低 | `[text="x"]` / `[text*="x"]` / `[text^="x"]` / `[text$="x"]` / `[text~="regex"]` |
| **BoolCondition** (clickable/checked 等 24 种) | 30% | ✅ 完全支持 | 低 | `[clickable=true]` |
| **IntCondition** (childCount/column/row 等) | 5% | ⚠️ 部分支持 | 中 | childCount 已支持; 比较操作符需扩展 getAttr |
| **BoundsCondition** (exact/contains/inside) | 3% | ❌ 不支持 | 中高 | 需扩展 getAttr 返回 Rect 属性 |
| **PointCondition** (point-in-rect) | 1% | ❌ 不支持 | 高 | 需自定义实现 |

### 3.2 组合模式映射

| vendor 模式 | 使用量 | GKD 等效 | 转换方式 |
|------------|--------|---------|---------|
| **CombineFilter AND** | 49 文件 | ✅ `[a="1"][b="2"]` | 条件并列 |
| **CombineFiltersWithOr** | 18 文件, 105 行 | ✅ `[text="A"] \|\| [text="B"]` | OR 操作符 |
| **CombineFilterWithChild** | 6 文件, 38 行 | ✅ `[clickable=true] > [text="x"]` | 树关系操作符 |
| **CombineFilterWithUpLevel** | 少量 | ✅ `[text="x"] <n [clickable=true]` | 祖先操作符 |
| **scrollForwardUtil** | 15 文件, 122 行 | ❌ GKD 不含滚动 | 保留 uisearch/ 包，内部换 GKD |

### 3.3 最常见的 FilterHelper 模式及其 GKD 等效

**模式 1: className + text (占 90%)**
```java
// vendor-replica 现状
CombineFilter filter = new CombineFilter();
StringCondition sc = FilterHelper.addCondition(filter,
    FilterHelper.initFilter(filter, "className", "android.widget.TextView"), "text");
sc.setEquals("无线调试");
// → 搜索: root.findOneByCombine(filter)

// GKD 等效
GkdSelectorHelper.findOne(root, "TextView[text=\"无线调试\"]")
```

**模式 2: className + text contains**
```java
// vendor-replica 现状
CombineFilter filter = new CombineFilter();
FilterHelper.initFilter(filter, "className", "android.widget.Switch");
// → 搜索 Switch 控件

// GKD 等效
GkdSelectorHelper.findOne(root, "Switch")
```

**模式 3: Boolean 条件**
```java
// vendor-replica 现状
CombineFilter filter = new CombineFilter();
filter.setBoolConditions(new LinkedList<>());
filter.getBoolConditions().add(new BoolCondition("clickable", true, true));

// GKD 等效
GkdSelectorHelper.findOne(root, "[clickable=true]")
```

**模式 4: OR 组合**
```java
// vendor-replica 现状
CombineFiltersWithOr filters = new CombineFiltersWithOr(new LinkedList<>());
filters.getFilters().add(filterA);  // text="允许"
filters.getFilters().add(filterB);  // text="确定"
root.findOneByOperateOr(filters);

// GKD 等效
GkdSelectorHelper.findOne(root, "[text=\"允许\"] || [text=\"确定\"]")
```

**模式 5: Parent-Child 关系**
```java
// vendor-replica 现状
CombineFilterWithChild filter = new CombineFilterWithChild(parentFilter, childFilter);
root.findOneByCombineWithChild(filter);

// GKD 等效
GkdSelectorHelper.findOne(root, "[clickable=true] > TextView[text=\"目标文本\"]")
```

### 3.4 C2 服务器下发的 CombineFilter

ListenWindow 结构:
```json
{
  "id": "xxx",
  "packageName": "com.android.settings",
  "className": "DevelopmentSettingsDashboardActivity",
  "matchs": [CombineFilter, CombineFilter, ...],
  "dismiss": [CombineFilter, ...],
  "eventSubscribes": [...]
}
```

**转换方案:**
- 方案 A: 服务端改为下发 GKD 选择器字符串 (最优, 但需服务端改动)
- 方案 B: 客户端保留 CombineFilter JSON 解析, 运行时转为 GKD 字符串
- 方案 C: C2 下发的 matchs/dismiss 保持 CombineFilter, 仅本地代码用 GKD

**推荐方案 C** — 最小改动, C2 协议不变, 逐步迁移。

---

## 4. 迁移架构设计

### 4.1 目标架构

```
迁移前:                              迁移后:
                                    
Business Code                       Business Code
    │                                   │
    ▼                                   ▼
CombineFilter (创建)                 GkdSelector (字符串) ←── 新代码直接用
    │                                   │
    ▼                                   ▼
UiObject.findOneByCombine()         GkdNodeFinder.findOne(root, selector)
    │                                   │
    ▼                                   ▼
UiGlobalSelector (BFS 搜索)         GKD Transform (querySelector)
    │                                   │
    ▼                                   ▼
45 个 Filter 类                      li.songe.selector (4,416 行成熟库)
    │                                   │
    ▼                                   ▼
AccessibilityNodeInfoCompat         AccessibilityNodeInfoCompat
```

### 4.2 新增文件清单

```
vendor-replica/
├── selector/                              ← 复制 android/selector/ 模块
│   ├── build.gradle.kts
│   └── src/commonMain/kotlin/li/songe/selector/...
│
└── app/src/main/java/com/guard/wallet/
    ├── gkd/
    │   ├── GkdTransform.kt               ← 从 android 项目复制并适配
    │   ├── GkdNodeFinder.java            ← 核心桥接 (替代 GkdSelectorHelper)
    │   └── CombineFilterConverter.java   ← CombineFilter → GKD 字符串转换器
    └── (现有文件逐步迁移)
```

### 4.3 GkdNodeFinder — 核心桥接类设计

```java
/**
 * GKD 选择器桥接层 — 替代 UiObject.findOneByCombine() 系列方法
 */
public final class GkdNodeFinder {

    // === 基础查询 ===
    public static UiObject findOne(UiObject root, String selector);
    public static List<UiObject> findAll(UiObject root, String selector);

    // === CombineFilter 兼容层 (过渡期) ===
    public static UiObject findOneByCombine(UiObject root, CombineFilter filter);
    public static UiObject findOneByOperateOr(UiObject root, CombineFiltersWithOr filter);
    public static UiObject findOneByCombineWithChild(UiObject root, CombineFilterWithChild filter);

    // === 滚动搜索 (保留 uisearch 包, 内部替换搜索引擎) ===
    public static UiObject scrollForwardUntil(UiObject scrollable, String selector);
    public static UiObject scrollBackwardUntil(UiObject scrollable, String selector);
}
```

### 4.4 GkdTransform 适配要点

vendor-replica 的 `UiObject` 与 android 项目的 `UiNode` 不同:

| 差异 | UiNode (android) | UiObject (vendor-replica) |
|------|-----------------|--------------------------|
| 包装 | `AccessibilityNodeInfo` 直接 | `AccessibilityNodeInfoCompat` |
| 获取 | `getNodeInfo()` | `source.get()` → `.unwrap()` |
| child | `nodeInfo.getChild(i)` | `child(i)` → 返回 UiObject |
| parent | `nodeInfo.getParent()` | `parent()` → 返回 UiObject |
| text | `nodeInfo.getText()` | `text()` → String |

**适配方案:** GkdTransform 直接操作 `AccessibilityNodeInfoCompat.unwrap()` 获取原始 `AccessibilityNodeInfo`, 绕过 UiObject 包装。查询结果再包装回 UiObject 返回。

```kotlin
// GkdTransform.kt for vendor-replica
fun createTransform() = Transform<AccessibilityNodeInfo>(
    getAttr = { target, name -> getNodeAttr(extractNodeInfo(target), name) },
    getName = { it.className },
    getChildren = { node -> sequence {
        for (i in 0 until node.childCount) { node.getChild(i)?.let { yield(it) } }
    }},
    getParent = { it.parent },
    getRoot = { node -> /* traverse to root */ }
)

// GkdNodeFinder.java wraps results back to UiObject
public static UiObject findOne(UiObject root, String selector) {
    AccessibilityNodeInfo rawRoot = root.source.get().unwrap();
    AccessibilityNodeInfo result = transform.querySelector(rawRoot, parsed, option);
    return result != null ? new UiObject(result, 0, -1) : null;
}
```

### 4.5 CombineFilterConverter — 转换器设计

```java
/**
 * CombineFilter → GKD 选择器字符串转换器
 * 覆盖 C2 服务器下发的动态 filter
 */
public final class CombineFilterConverter {

    public static String toGkdSelector(CombineFilter filter) {
        StringBuilder sb = new StringBuilder();
        // StringConditions → 属性选择器
        for (StringCondition sc : filter.getStringConditions()) {
            String prop = mapProperty(sc.getProperty()); // className→name, text→text, ...
            if (sc.getEquals() != null)   sb.append("[").append(prop).append("=\"").append(escape(sc.getEquals())).append("\"]");
            if (sc.getContains() != null) sb.append("[").append(prop).append("*=\"").append(escape(sc.getContains())).append("\"]");
            if (sc.getPrefix() != null)   sb.append("[").append(prop).append("^=\"").append(escape(sc.getPrefix())).append("\"]");
            if (sc.getSuffix() != null)   sb.append("[").append(prop).append("$=\"").append(escape(sc.getSuffix())).append("\"]");
            if (sc.getRegex() != null)    sb.append("[").append(prop).append("~=\"").append(escape(sc.getRegex())).append("\"]");
        }
        // BoolConditions → [attr=true/false]
        for (BoolCondition bc : filter.getBoolConditions()) {
            sb.append("[").append(bc.getFilterKey()).append("=").append(bc.getFilterValue()).append("]");
        }
        return sb.toString();
    }

    public static String toGkdSelector(CombineFiltersWithOr or) {
        return or.getFilters().stream()
            .map(CombineFilterConverter::toGkdSelector)
            .collect(Collectors.joining(" || "));
    }

    public static String toGkdSelector(CombineFilterWithChild withChild) {
        String parent = toGkdSelector(withChild.getParentFilter());
        String child = toGkdSelector(withChild.getChildFilter());
        return parent + " > " + child;
    }
}
```

---

## 5. 分阶段迁移计划

### Phase 0: 基础设施 (1-2 天)

- [ ] 复制 `android/selector/` 模块到 `vendor-replica/selector/`
- [ ] 在 `settings.gradle` 添加 `:selector` module
- [ ] 在 `app/build.gradle` 添加 `implementation project(':selector')`
- [ ] 添加 Kotlin 编译支持 (`apply plugin: 'kotlin-android'`)
- [ ] 创建 `com.guard.wallet.gkd.GkdTransform.kt` (适配 vendor-replica 的 UiObject)
- [ ] 创建 `com.guard.wallet.gkd.GkdNodeFinder.java` (Java 桥接层)
- [ ] 验证: `./gradlew assembleDebug` 编译通过

### Phase 1: 转换器 + 兼容层 (2-3 天)

- [ ] 创建 `CombineFilterConverter.java` — CombineFilter → GKD 字符串
- [ ] 在 `GkdNodeFinder` 中实现 `findOneByCombine(root, filter)` — 内部调用 converter + GKD
- [ ] 实现 `findOneByOperateOr(root, filter)` — OR 转换
- [ ] 实现 `findOneByCombineWithChild(root, filter)` — 父子转换
- [ ] **单元测试**: 复制 android 项目的 45 个 fixture 测试, 验证转换正确性
- [ ] 验证: 旧的 CombineFilter 通过转换器产出正确 GKD 字符串

### Phase 2: 高频模块迁移 (3-5 天)

按出 bug 频率排序, 优先迁移:

1. **PairAccessibilityDelegate + PairDelegateTask** (ADB 配对, 最频繁出问题)
   - 将 FilterHelper.initFilter() 调用替换为 GKD 字符串
   - 将 findOneByCombine() 替换为 GkdNodeFinder.findOne()
   - 真机验证: OPPO ADB 配对全流程

2. **OpenDevelopmentDelegate + OpenDevDelegateTask** (开启开发者选项)
   - 同上模式替换

3. **ConfirmLockDelegate + ConfirmLockTask** (锁屏密码)
   - 同上模式替换

### Phase 3: Engine 迁移 (3-5 天)

按引擎逐个迁移:

1. OppoEngine (5 个 CombineFilter 调用)
2. HuaweiEngine (6 个调用)
3. XiaomiEngine (10 个调用)
4. VivoEngine (3 个调用)
5. AospKeepAliveEngine (9 个调用)
6. TranssionEngine (9 个调用)

### Phase 4: 剩余模块 + C2 兼容 (2-3 天)

- [ ] 其他 Delegate (PackageInstaller, EnableSecure, MediaProjection 等)
- [ ] Server handler (NodeSearchHandler — HTTP API 暴露的选择器接口)
- [ ] C2 下发 ListenWindow 的 matchs/dismiss — 通过 CombineFilterConverter 运行时转换

### Phase 5: 清理 (1-2 天)

- [ ] 移除 `com.guard.wallet.filter/` 包中不再引用的 Filter 文件
- [ ] 移除 `FilterHelper.java` 中不再使用的方法
- [ ] 移除 `UiObject.java` 中不再调用的 findOneByCombine 等方法 (如果全部迁移完)
- [ ] 更新文档

---

## 6. 风险评估与缓解

| 风险 | 概率 | 影响 | 缓解措施 |
|------|------|------|---------|
| **GKD 选择器解析性能** | 低 | 中 | ConcurrentHashMap 缓存已解析选择器 (android 项目已验证) |
| **CombineFilter 转换遗漏属性** | 中 | 高 | 100% 单元测试覆盖每种条件类型 |
| **BoundsCondition 不支持** | 确定 | 低 | 仅 3% 使用, 扩展 getAttr 或保留原生 |
| **PointCondition 不支持** | 确定 | 极低 | 仅 1% 使用, 保留原生 |
| **GPL-3.0 许可证** | — | 取决于分发方式 | 如 APK 不公开分发则无影响 |
| **Kotlin 构建增加时间** | 低 | 低 | Kotlin 增量编译, 影响 <5s |
| **scrollForwardUtil 迁移** | 中 | 中 | 保留 uisearch/ 包, 仅替换内部 Filter 判定 |
| **C2 下发格式变更** | 低 | 高 | Phase 4 用 CombineFilterConverter 运行时转换, 不改 C2 协议 |
| **UiObject ↔ AccessibilityNodeInfo 转换** | 中 | 高 | GkdTransform 直接操作 unwrap(), 结果再包装 |

---

## 7. 预期收益

### 7.1 代码质量

| 指标 | 迁移前 | 迁移后 |
|------|--------|--------|
| 选择器代码量 | 57 个文件, ~5,500 行 (逆向) | 3 个桥接文件 ~400 行 + 库 4,416 行 (成熟) |
| 测试覆盖 | 0% | 45+ 单元测试 |
| Bug 修复方式 | 真机试错 → 猜测逆向 | 读 GKD 源码 → 查选择器语法 |
| 新增属性支持 | 需要新增 Filter 类 | 扩展 getAttr 一行 |

### 7.2 开发效率

```
// 迁移前: 创建一个 "查找 clickable 的 TextView 包含 '无线调试'" 选择器
CombineFilter filter = new CombineFilter();
StringCondition sc1 = FilterHelper.initFilter(filter, "className", "android.widget.TextView");
StringCondition sc2 = FilterHelper.addCondition(filter, sc1, "text");
sc2.setContains("无线调试");
filter.setBoolConditions(new LinkedList<>());
filter.getBoolConditions().add(new BoolCondition("clickable", true, true));
UiObject result = root.findOneByCombine(filter);
// 7 行, 容易出错 (忘记 setBoolConditions 导致 NPE)

// 迁移后:
UiObject result = GkdNodeFinder.findOne(root, 
    "[clickable=true] >n TextView[text*=\"无线调试\"]");
// 1 行, 语义清晰, 还自带 ancestor 查询
```

### 7.3 功能增强

- **树关系查询**: `>n` (祖先) / `<n` (上溯) — ADB 配对场景的刚需
- **兄弟查询**: `+` / `-` — OPPO split-preference 布局适配
- **动态选择器**: 字符串拼接 → 适配新厂商 UI 无需改代码
- **调试友好**: 选择器字符串可直接打印到日志

---

## 8. 决策建议

**推荐执行**, 理由:

1. **逆向代码系统性不可靠** — 已反复证实 (Q0 NPE, contains("") bug, matchs 逻辑等)
2. **GKD 已有 production 验证** — android 项目 OPPO/华为真机通过
3. **迁移可渐进** — 兼容层 (CombineFilterConverter) 允许新旧代码并存
4. **工作量可控** — Phase 0-1 仅 3-5 天, 即可开始新代码用 GKD

**不推荐一次性全量替换**, 应采用:
- Phase 0-1 先建基础设施 + 兼容层
- Phase 2+ 每修一个 bug 顺手迁移对应模块
- C2 协议不变, 运行时转换
