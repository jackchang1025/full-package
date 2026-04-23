# PIN 密码捕获模块 — 厂商适配深度审计

> **样本**: update.apk (tiangong RAT)
> **图案覆盖层**: `jadx-reference/rock/service/modules/cipher/C0337a3.java` (PatternCaptureOverlay, 1048 行)
> **PIN 触摸管理**: `jadx-reference/rock/service/modules/cipher/C0339a5.java` (TouchViewManager, 745 行)
> **密码捕获管理**: `jadx-reference/rock/service/modules/cipher/C0335a1.java` (CipherCaptureManager, 3005 行)
> **图案视图组件**: `jadx-reference/rock/service/modules/cipher/C0336a2.java` (PatternView)
> **品牌检测工具**: `jadx-reference/p000/AbstractC1117qo.java`
> **锁屏类型检测**: `jadx-reference/p000/nm0.java`
> **多语言配置**: `locateValues.json` (33KB, 36 语言)
> **日期**: 2026-04-21

---

## 一、厂商覆盖总览

PIN 密码捕获模块覆盖 **7 大厂商族群 + AOSP 通用兜底**，适配分布在三个维度：

| 厂商族群 | 品牌列表 | 检测方法 | PIN 键盘适配 | 图案 View 查找 | 图案绘图参数 | SystemUI 资源 |
|---------|---------|---------|:-----------:|:------------:|:-----------:|:----------:|
| **Huawei/Honor** | huawei, honor | `m214446e2()` | AOSP 通用 | AOSP 通用 | **专用** | **4 级资源回退** |
| **OPPO/Realme/OnePlus** | oppo, realme, oneplus | `m214448e4()` | AOSP 通用 | **专用 ID** | **专用 (正方形)** | **专用颜色/透明度** |
| **Vivo/iQOO** | vivo, iqoo | `m214449e5()` | **专用 VivoPinkey** | **专用 ID** | **专用** | **4 级资源回退** |
| **Samsung** | samsung | `Build.BRAND` 匹配 | AOSP 通用 | **专用 ID (2个)** | **专用** | **专用颜色资源** |
| **Xiaomi 系** | xiaomi, redmi, poco, blackshark | `m214450e6()` | AOSP 通用 | AOSP 通用 | **专用 (快速动画)** | **专用颜色资源** |
| **非洲品牌** | tecno, itel, infinix | `Build.BRAND` 匹配 | AOSP 通用 | AOSP 通用 | **专用** | 无 |
| **AOSP 通用** | 所有其他品牌 | 兜底 | **6 级回退** | **9 ID 序列** | **主题自适应** | **AOSP 标准资源** |

---

## 二、品牌检测函数

**文件**: `AbstractC1117qo.java`

| 方法 | 行号 | 检测逻辑 | 返回 true 的品牌 |
|------|------|---------|----------------|
| `m214446e2()` | 705-711 | `Build.BRAND.toLowerCase() == "huawei" \|\| "honor"` | Huawei, Honor |
| `m214448e4()` | 720-726 | `== "oppo" \|\| "realme" \|\| "oneplus"` | OPPO, Realme, OnePlus |
| `m214449e5()` | 729-735 | `== "vivo" \|\| "iqoo"` | Vivo, iQOO |
| `m214450e6()` | 738-744 | `== "xiaomi" \|\| "redmi" \|\| "poco" \|\| "blackshark"` | Xiaomi, Redmi, POCO, BlackShark |

Samsung、Tecno/Itel/Infinix 没有专用检测函数，直接用 `AbstractC0779a1.m213656a9(Build.BRAND, "samsung")` 等字符串匹配。

---

## 三、PIN 数字键盘厂商适配

### 3.1 键盘按键识别 — 6 级回退策略

**文件**: `C0339a5.java` 行 460-525

当用户在系统锁屏键盘上按下数字键时，RAT 通过无障碍事件获取被点击 View 的 resource ID，然后按以下优先级提取数字：

| 优先级 | View ID 模式 | 提取方式 | 适配厂商 | 代码行 |
|--------|-------------|---------|---------|--------|
| **1** | `com.android.systemui:id/key{N}` | 截取 `key` 后缀 | AOSP / 多数厂商 | 485-489 |
| **2** | `com.android.systemui:id/VivoPinkey{N}` | 截取 `VivoPinkey` 后缀 | **Vivo / iQOO** | 491-493 |
| **3** | `com.android.systemui:id/num{N}` | 截取 `num` 后缀 | 部分厂商 | 495-497 |
| **4** | `com.android.systemui:id/char_{N}` | 截取 `char_` 后缀 | 混合密码键盘 | 499-501 |
| **5** | 任意 `:id/` 结尾含数字 | 取 ID 最后一个数字字符 | 通用兜底 | 503-505 |
| **6** | 节点文本为单个数字 | 直接取文本 | 最终兜底 | 507-508 |

**排除列表**（优先级 1 和 5 跳过）：`key_enter`、`key_delete`、`delete`、`enter`、`cancel`

**最终选择逻辑** (行 512)：
```java
Pair pair = !linkedList2.isEmpty() ? new Pair(linkedList2, "SystemUI")
          : !linkedList3.isEmpty() ? new Pair(linkedList3, "Vivo")
          : !linkedList4.isEmpty() ? new Pair(linkedList4, "num/char")
          : !linkedList5.isEmpty() ? new Pair(linkedList5, "ID尾数字")
          : !linkedList6.isEmpty() ? new Pair(linkedList6, "单数字")
          : null;
```

### 3.2 Vivo 专用 PIN 键盘

Vivo/iQOO 的锁屏 PIN 键盘使用非标准 View ID `VivoPinkey{0-9}`（而非 AOSP 的 `key{0-9}`），因此需要单独的提取分支。

**Vivo 确认按钮检测**（`C0335a1.java` 行 838-850）：
```java
// Vivo 专用确认按钮 ID 列表
new Pair(str2.concat(":id/mix_confirm"), "android.view.View"),
new Pair(str2.concat(":id/iv_complete"), "android.widget.TextView"),
new Pair(str2.concat(":id/vivo_pin_confirm"), "android.widget.Button"),
new Pair(str2.concat(":id/mix_normal_confirm"), "android.widget.TextView")
```

### 3.3 锁屏界面包名检测

**文件**: `C0335a1.java` 行 780

RAT 识别以下包名为锁屏/密码设置页面：
```java
"com.android.settings"                           // AOSP 通用
"oplus.settings"                                  // OPPO/OnePlus
"oppo.settings"                                   // OPPO
"coloros.settings"                                // ColorOS
"vivo.settings"                                   // Vivo
"com.samsung.android.biometrics.app.setting"      // Samsung 生物识别设置
```

### 3.4 PIN 设置页面 View 检测

**文件**: `C0335a1.java` 行 793

```java
// 用于判断当前页面是否为 PIN 设置/验证页面
"com.android.settings:id/key0"              // AOSP PIN 键盘
"com.android.settings:id/key1"              // AOSP
"com.android.settings:id/lockPattern"       // AOSP 图案
"com.android.settings:id/four_to_more_key0" // 4+ 位 PIN
"com.android.settings:id/vivo_pin_confirm"  // Vivo 确认按钮
```

---

## 四、图案密码厂商适配

### 4.1 图案 View 查找 — 9 ID 序列

**文件**: `C0337a3.java` 方法 `m211844a7()`，行 678-705

按以下顺序逐一尝试查找系统图案锁屏 View：

| 顺序 | View ID | 适配厂商 | 类型 |
|------|---------|---------|------|
| 1 | `com.android.systemui:id/lockPattern` | AOSP 通用 | 通用 |
| 2 | `com.android.settings:id/lockPattern` | AOSP 设置 | 通用 |
| 3 | `com.samsung.android.biometrics.app.setting:id/lockPattern` | **Samsung** | 专用 |
| 4 | `com.android.systemui:id/biometric_lockPattern` | AOSP 生物识别 | 通用 |
| 5 | `com.android.settings:id/biometric_lockPattern` | AOSP 生物识别设置 | 通用 |
| 6 | `com.samsung.android.biometrics.app.setting:id/biometric_lockPattern` | **Samsung** | 专用 |
| 7 | `com.android.systemui:id/colorLockPatternView` | **OPPO/Realme/OnePlus** | 条件检测 `m214448e4()` |
| 8 | `com.android.systemui:id/vivo_lock_pattern_view` | **Vivo/iQOO** | 条件检测 `m214449e5()` |
| 9 | `com.android.systemui:id/lockPatternView` | AOSP 兜底 | 通用 |

### 4.2 SystemUI 资源读取 — 图案绘图参数

**文件**: `C0337a3.java` 方法 `m211846a9()`，行 731-991

该方法从 `com.android.systemui` 包的 Resources 中读取图案锁屏的实际绘图参数，以精确匹配系统原生外观。

#### 4.2.1 Vivo/iQOO 资源读取（行 758-896）

**检测条件**: `Build.BRAND.toLowerCase() == "vivo" || "iqoo"`

**点大小资源（4 级回退）**：

| 优先级 | SystemUI dimen 资源名 | 计算方式 | 行号 |
|--------|---------------------|---------|------|
| 1 | `vivo_keyguard_select_point_width` | innerDot = 值, halo = 值 × 2.5 | 837, 842-845 |
| 2 | `vivo_keyguard_spring_patten_point_width` | 同上 | 838, 846-849 |
| 3 | `vivo_pattern_unlock_size` | innerDot = 值/12, halo = 值/8 | 840, 850-856 |
| 4 | 硬编码兜底 | innerDot = 8×density, halo = 20×density | 858-860 |

**线宽资源**：`vivo_keyguard_path_width`（行 839, 862-864）

#### 4.2.2 Huawei/Honor 资源读取（行 766-796）

**检测条件**: `Build.BRAND.toLowerCase() == "huawei" || "honor"`

**点大小资源（5 级回退）**：

| 优先级 | SystemUI dimen 资源名 | 行号 |
|--------|---------------------|------|
| 1 | `hwlock_pattern_dot_size` | 767 |
| 2 | `hw_pattern_dot_size` | 767 |
| 3 | `hw_lock_pattern_dot_size` | 767 |
| 4 | `keyguard_pattern_dot_size` | 767 |
| 5 | `lock_pattern_dot_size`（AOSP 通用） | 778 |
| 兜底 | innerDot = 11×density, halo = 32×density | 793-795 |

**Halo 计算**: `haloSize = dotSize × 3`（行 784）

#### 4.2.3 颜色资源读取（行 900-943，所有厂商）

| 厂商 | 点颜色资源 | 线颜色资源 | 行号 |
|------|-----------|-----------|------|
| **OPPO** | `coui_lock_pattern_dot_color` | `coui_lock_pattern_path_color` | 900-904 |
| **Samsung** | `sec_lock_pattern_dot_color` | `sec_lock_pattern_path_color` | 906-910 |
| **Vivo** | `vivo_lock_pattern_dot_color` | `vivo_lock_pattern_path_color` | 912-916 |
| **Huawei** | `hwlock_pattern_dot_color` | `hwlock_pattern_path_color` | 918-922 |
| **Xiaomi** | `miui_lock_pattern_dot_color` | `miui_lock_pattern_path_color` | 939-943 |
| 兜底 | 主题色自适应 `m211842a5()` | 同上 | 927 |

#### 4.2.4 OPPO 专用透明度（行 816-821）

```java
// OPPO 图案锁屏外圈光晕透明度
int oppoAlphaId = resources.getIdentifier(
    "coui_lock_pattern_outer_circle_max_alpha", "dimen", "com.android.systemui");
// SDK < 29: 默认 0.1f
// SDK >= 29: 从资源读取
```

#### 4.2.5 返回数据结构 `xm0`

```java
new xm0(
    haloSize,           // 光晕大小 (px)
    innerDotSize,       // 内部点大小 (px)
    dotSelectedSize,    // 选中点大小 (px)
    dotColor,           // 点颜色 (ARGB)
    pathColor,          // 连线颜色 (ARGB)
    pathWidth,          // 线宽 (px)
    outerCircleAlpha    // 外圈透明度 (0.0-1.0)
);
```

### 4.3 硬编码回退参数（SystemUI 资源不可用时）

**文件**: `C0337a3.java` 行 234-310

当无法读取 SystemUI 资源时，按品牌使用硬编码参数：

#### 4.3.1 OPPO/Realme/OnePlus（行 240-248）

| 参数 | 值 | 十六进制 |
|------|-----|---------|
| normalStateColor | 1291845632 | `#4CFFFFFF`（半透明白） |
| correctStateColor | 1291845632 | `#4CFFFFFF` |
| dotSelectedColor | 1291845632 | `#4CFFFFFF` |
| dotNormalSize | 30 px | |
| dotSelectedSize | 60 px | |
| pathWidth | 6 px | |
| pathColor | -16777216 | `#FF000000`（黑色） |
| **aspectRatio** | **1** | **正方形**（唯一使用正方形的厂商） |

#### 4.3.2 Samsung（行 251-263）

| 参数 | 值 | 十六进制 |
|------|-----|---------|
| normalStateColor | -3355444 | `#FFCCCCCC`（浅灰） |
| correctStateColor | -3355444 | `#FFCCCCCC` |
| dotSelectedColor | -3355444 | `#FFCCCCCC` |
| dotNormalSize | 36 px | |
| dotSelectedSize | 50 px | |
| pathWidth | 10 px | |
| pathColor | -1 | `#FFFFFFFF`（白色） |
| aspectRatio | 0 | 自由比例 |
| dotAnimationDuration | **100 ms** | |
| pathEndAnimationDuration | **200 ms** | |

#### 4.3.3 Huawei/Honor（行 265-272）

| 参数 | 值 | 十六进制 |
|------|-----|---------|
| normalStateColor | -1 | `#FFFFFFFF`（白色） |
| correctStateColor | -1 | `#FFFFFFFF` |
| dotSelectedColor | -1 | `#FFFFFFFF` |
| dotNormalSize | 32 px | |
| dotSelectedSize | 50 px | |
| pathWidth | 20 px | |
| pathColor | -7829368 | `#FF888888`（灰色） |
| aspectRatio | 0 | |

#### 4.3.4 Vivo/iQOO（行 273-281）

| 参数 | 值 | 十六进制 |
|------|-----|---------|
| normalStateColor | -3355444 | `#FFCCCCCC`（浅灰） |
| correctStateColor | -3355444 | `#FFCCCCCC` |
| dotSelectedColor | -256 | `#FFFFFF00`（黄色） |
| dotNormalSize | **20 px** | 最小 |
| dotSelectedSize | 40 px | |
| pathWidth | **30 px** | 最粗 |
| pathColor | `#FFF68F` | 浅黄色 |
| aspectRatio | 0 | |

#### 4.3.5 Xiaomi/Redmi/POCO/BlackShark（行 283-295）

| 参数 | 值 | 说明 |
|------|-----|------|
| normalStateColor | 主题自适应 | `m211842a5()` |
| correctStateColor | 主题自适应 | |
| dotSelectedColor | 主题自适应 | |
| dotNormalSize | 30 px | |
| dotSelectedSize | 60 px | |
| pathWidth | 3×density (min 3) | 密度自适应 |
| pathColor | 主题自适应 | |
| aspectRatio | 0 | |
| dotAnimationDuration | **50 ms** | 最快 |
| pathEndAnimationDuration | **50 ms** | 最快 |

#### 4.3.6 Tecno/Itel/Infinix（行 283, 297-303）

| 参数 | 值 | 十六进制 |
|------|-----|---------|
| normalStateColor | -1 | `#FFFFFFFF`（白色） |
| correctStateColor | -1 | `#FFFFFFFF` |
| dotSelectedColor | -1 | `#FFFFFFFF` |
| dotNormalSize | 20 px | |
| dotSelectedSize | 30 px | |
| pathWidth | 5 px | |
| pathColor | -1 | `#FFFFFFFF` |
| aspectRatio | 0 | |

#### 4.3.7 AOSP 通用兜底（行 284-295, 306-309）

| 参数 | 值 | 说明 |
|------|-----|------|
| normalStateColor | 主题自适应 | 深色: `#671FFFFF`, 浅色: `#4CFFFFFF` |
| correctStateColor | 主题自适应 | |
| dotSelectedColor | 主题自适应 | |
| dotNormalSize | 30 px | |
| dotSelectedSize | 60 px | |
| pathWidth | 3×density (min 3) | |
| pathColor | 主题自适应 | |
| aspectRatio | 0 | |
| dotAnimationDuration | 150 ms | |
| pathEndAnimationDuration | 100 ms | |

### 4.4 动画速度对比

| 厂商 | 点动画 (ms) | 线消失动画 (ms) | 特点 |
|------|-----------|---------------|------|
| OPPO/Realme/OnePlus | 150 | 100 | 默认 |
| Samsung | **100** | **200** | 线消失最慢 |
| Huawei/Honor | 150 | 100 | 默认 |
| Vivo/iQOO | 150 | 100 | 默认 |
| **Xiaomi 系** | **50** | **50** | **最快** |
| Tecno/Itel/Infinix | 150 | 100 | 默认 |
| AOSP | 150 | 100 | 默认 |

---

## 五、锁屏类型检测

### 5.1 检测器 `nm0.java`（290 行）

**核心方法**: `m214126a5()`（行 201-240）

检测流程：
1. 调用 `KeyguardManager.isKeyguardSecure()` 确认锁屏存在
2. 调用 `m214127a6()` 通过无障碍树分析 UI 节点
3. 如果返回 `"none"`，调用 `m214122a1()` 进行关键词启发式检测
4. 仍为 `"none"` 则默认为 `"pin"`

**关键词检测**（`m214124a3()`，行 145-182）使用 4 组多语言关键词列表：

| 关键词组 | 来源 | 包含的语言/词汇 |
|---------|------|--------------|
| 图案关键词 | `dh0.f55775c5` | "图案"/"pattern"/"パターン" 等 |
| PIN 关键词 | `dh0.f55773c3` | "PIN"/"密码"/"暗证番号" 等 |
| 密码关键词 | `dh0.f55772c2` | "密码"/"password"/"パスワード" 等 |
| 解锁关键词 | `dh0.f55774c4` | "解锁"/"登录"/"Unlock"/"Login" 等 |

**UI 节点分析**（`m214125a4()`，行 185-198）检测以下类型：

| 标志 | 条件 | 含义 |
|------|------|------|
| `f58388a4` | `isPassword()` 或 text 含 "password"/"edittext" | 密码输入框 |
| `f58389a5` | className 含 "keypad" 或 "keyboard" | 数字键盘 |
| `f58390a6` | className 含 "pattern" 或 "gesture" | 图案视图 |
| `f58393a9` | 按钮计数 | 判断是否为 PIN 页面 |

**结果缓存**到 SharedPreferences：`password_type`、`has_password`、`last_detection_time`

### 5.2 密码强度分类

| cipherGradeCode | 含义 | 检测方式 |
|-----------------|------|---------|
| `PASSWORD_QUALITY_PATTERN` | 图案锁 | `f58390a6 = true` |
| `PASSWORD_QUALITY_NUMERIC` | 数字 PIN | 纯数字 + 4-6 位 |
| `PASSWORD_QUALITY_NUMERIC_COMPLEX` | 复杂 PIN | 纯数字 + 长度>6 |
| `PASSWORD_QUALITY_ALPHANUMERIC` | 混合密码 | 含字母+数字 |
| `PASSWORD_QUALITY_TOUCH_POINTS` | 触点图案 | 触摸坐标序列 |

---

## 六、侧信道捕获机制

### 6.1 TEXT_CHANGED 事件捕获（PIN/密码）

**文件**: `C0335a1.java` 行 2040-2130

当用户在系统密码框输入时，RAT 通过三重快照重建密码明文：

```
AccessibilityEvent(TYPE_VIEW_TEXT_CHANGED = 16)
    │
    ├─ 快照 1: event.getBeforeText()     ← 变化前文本
    ├─ 快照 2: event.getText()           ← 当前事件文本
    └─ 快照 3: source.getText()          ← 节点实时文本
    │
    ▼
字符位对齐 + 掩码字符过滤 → 逐步重建明文
```

### 6.2 掩码字符过滤

**文件**: `C0335a1.java` 行 1983

过滤以下 5 种掩码字符（仅保留真实输入）：

| 掩码字符 | Unicode | 常见于 |
|---------|---------|-------|
| `•` | U+2022 | AOSP / 多数厂商 |
| `●` | U+25CF | Samsung / Huawei |
| `⬤` | U+2B24 | 部分 ROM |
| `◉` | U+25C9 | Vivo |
| `*` | U+002A | 密码框通用 |

### 6.3 点击事件捕获（PIN 键盘）

**文件**: `C0335a1.java` 行 2187-2233

通过 `TYPE_VIEW_CLICKED` 事件监听 PIN 键盘按钮点击：

```java
// 按优先级识别被点击的数字键
if (viewId.contains(":id/key") || viewId.contains("VivoPinkey")) → 提取数字
if (viewId.contains(":id/num")) → 提取数字
if (viewId.contains(":id/char_")) → 标记为混合密码 (f53299b3=true)
```

### 6.4 触摸坐标捕获（图案密码）

**文件**: `ViewOnTouchListenerC0338a4.java`（300 行）

透明覆盖层拦截触摸事件，记录完整触摸序列：

```java
// onTouch 处理
ACTION_DOWN → 查找触摸位置的 UI 节点
    → 记录 Point(x, y) + System.nanoTime()
    → 封装为 ListenPropResponse("adb_coord", "x,y")
    → 添加到 CipherDataHolder.f53226a1
// 最多记录 10 个触摸点
```

---

## 七、辅助数据类

### 7.1 CipherResult — 捕获结果容器

| 字段 | 类型 | 含义 |
|------|------|------|
| `f53233a0` | String | textCipher — 文本密码 |
| `f53234a1` | ArrayList\<Point\> | touchCipher — 触摸坐标序列 |
| `f53235a2` | String | cipherGradeCode — 密码强度分类 |

### 7.2 ListenPropResponse — 无障碍事件数据

| 字段 | 类型 | 含义 |
|------|------|------|
| `f53240a0` | Integer | targetIndex — View 层级索引 |
| `f53241a1` | String | prop — 属性类型: `"id"` / `"text"` / `"desc"` / `"adb_coord"` |
| `f53242a2` | String | value — 属性值 |
| `f53243a3` | Long | timestamp — 纳秒时间戳 |

### 7.3 xm0 — SystemUI 图案样式参数

| 字段 | 类型 | 含义 |
|------|------|------|
| `f61157a0` | int | haloSize — 光晕大小 (px) |
| `f61158a1` | int | innerDotSize — 内部点大小 (px) |
| `f61159a2` | int | dotSelectedSize — 选中点大小 (px) |
| `f61160a3` | int | dotColor — 点颜色 (ARGB) |
| `f61161a4` | int | pathColor — 连线颜色 (ARGB) |
| `f61162a5` | int | pathWidth — 线宽 (px) |
| `f61163a6` | float | outerCircleAlpha — 外圈透明度 |

### 7.4 wm0 — 图案 View 边界

| 字段 | 类型 | 含义 |
|------|------|------|
| `f60946a0` | Rect | boundsInScreen — 屏幕绝对坐标 |
| `f60947a1` | Rect | boundsInParent — 父级相对坐标 |

### 7.5 DotAlign 枚举 — 图案网格对齐

11 种对齐方式（所有厂商统一使用 `ALIGN_CENTER`，无厂商差异）：

`ALIGN_TOP` / `ALIGN_TOP_CENTER` / `ALIGN_TOP_BOTTOM` ~ `ALIGN_TOP_BOTTOM_5` / `ALIGN_CENTER` / `ALIGN_CENTER_TOP` / `ALIGN_CENTER_BOTTOM` / `ALIGN_BOTTOM`

---

## 八、多语言配置（locateValues.json）

**文件**: `locateValues.json`（33KB）

覆盖 **36 种语言**的 UI 关键词：

| 语言 | 用途 |
|------|------|
| 中文简体/繁体 | 大陆/港澳台目标 |
| 英语 | 国际通用 |
| 日/韩 | 东亚目标 |
| 越/泰/印尼/马来 | 东南亚目标 |
| 印地/孟加拉 | 南亚目标 |
| 阿拉伯/希伯来/土耳其 | 中东目标 |
| 法/西/葡/意/德/荷 | 西欧目标 |
| 俄/波/乌/捷/斯洛伐克/匈/罗/保 | 东欧目标 |
| 瑞典/挪威/丹麦/芬兰 | 北欧目标 |
| 希腊/克罗地亚/斯洛文尼亚/立陶宛/拉脱维亚/爱沙尼亚 | 南欧/波罗的海 |

**厂商专用 ID 配置**（来自 locateValues.json）：

| 厂商 | 配置项 |
|------|--------|
| **Vivo** | `VivoPinkey0-9` PIN 键, `vivo_pin_confirm`/`mix_confirm`/`iv_complete`/`mix_normal_confirm` 确认键 |
| **OPPO** | 使用 `desc` 属性匹配（而非 ID） |
| **Huawei/Honor** | `com.hihonor.settingslib.SubSettings` 设置类, 备用删除/回车键 ID |
| **Samsung** | 图案样式配置 `dotColor: #2196F3`, `lineColor: #2196F3` |
| **Xiaomi** | `com.miui.securitycenter` 安全中心包名, 专用 ADB 输入 Activity |

---

## 九、多语言社工文本配置（app_config.json）

**文件**: `assets-decrypted/app_config.json`

两种密码验证弹窗的多语言社工文本：

### 模式 0：应用验证

| 语言 | 标题 | 副标题 | 描述 |
|------|------|--------|------|
| 中文 | 隐私保护 | 输入锁屏密码 | 为保护您的隐私，请输入锁屏密码验证身份 |
| 英文 | Verify personal identity | Privacy protection | To protect your privacy, please enter your lock screen password... |
| 韩文 | 개인 신원 확인 | 개인 정보 보호 | ... |
| 日文 | 本人確認 | プライバシー保護 | ... |

### 模式 1：系统更新

| 语言 | 标题 | 副标题 | 描述 |
|------|------|--------|------|
| 中文 | 验证锁屏密码 | 修复系统安全漏洞 | 请输入锁屏密码以完成系统更新 |
| 英文 | Verify lock screen password | Fix system security vulnerabilities | ... |

---

## 十、完整数据流

```
1. 锁屏出现 / BiometricPrompt 弹出
        │
2. nm0.m214126a5() 检测锁屏类型
   ├─ 关键词匹配（36 语言）
   ├─ UI 节点分析（className/isPassword/按钮计数）
   └─ 返回: "pin" / "pattern" / "password"
        │
3. CipherCaptureManager 启动对应捕获模式
   ├─ PIN/密码 → TYPE_VIEW_TEXT_CHANGED 三重快照
   │   ├─ 优先级 1: SystemUI key{N}
   │   ├─ 优先级 2: Vivo VivoPinkey{N}
   │   └─ 优先级 3-6: 回退策略
   │
   └─ 图案 → PatternCaptureOverlay 透明覆盖层
       ├─ 查找 View (9 ID 序列, Samsung/OPPO/Vivo 专用)
       ├─ 读取 SystemUI 资源 (颜色/大小/线宽)
       │   ├─ Huawei: hwlock_pattern_dot_size 等 4 资源
       │   ├─ Vivo: vivo_keyguard_* 等 4 资源
       │   ├─ OPPO: coui_lock_pattern_* 等 2 资源
       │   ├─ Samsung: sec_lock_pattern_* 等 2 资源
       │   └─ Xiaomi: miui_lock_pattern_* 等 2 资源
       ├─ 创建精确匹配系统外观的覆盖层
       └─ 拦截触摸事件 → 记录图案路径
        │
4. 密码提取
   ├─ CipherDataHolder 聚合 (ID/文本/描述/坐标 4 路提取)
   ├─ CipherExtractor 分类 (NUMERIC/ALPHANUMERIC/PATTERN/TOUCH_POINTS)
   └─ CipherResult 封装
        │
5. 三路上报 C2
   ├─ HTTP POST /api/sync/credentials (明文)
   ├─ WebSocket send (同步)
   └─ HTTP POST /api/sync/cipher (直连 OkHttpClient)
```

---

## 十一、检测特征

### 11.1 厂商专用 SystemUI 资源名（YARA 字符串）

```yara
// Vivo 专用
$vivo1 = "vivo_keyguard_select_point_width" ascii
$vivo2 = "vivo_keyguard_spring_patten_point_width" ascii
$vivo3 = "vivo_keyguard_path_width" ascii
$vivo4 = "vivo_pattern_unlock_size" ascii
$vivo5 = "VivoPinkey" ascii
$vivo6 = "vivo_lock_pattern_view" ascii
$vivo7 = "vivo_lock_pattern_dot_color" ascii
$vivo8 = "vivo_pin_confirm" ascii

// Huawei/Honor 专用
$hw1 = "hwlock_pattern_dot_size" ascii
$hw2 = "hw_pattern_dot_size" ascii
$hw3 = "hw_lock_pattern_dot_size" ascii
$hw4 = "hwlock_pattern_dot_color" ascii
$hw5 = "hwlock_pattern_path_color" ascii

// OPPO 专用
$oppo1 = "colorLockPatternView" ascii
$oppo2 = "coui_lock_pattern_dot_color" ascii
$oppo3 = "coui_lock_pattern_path_color" ascii
$oppo4 = "coui_lock_pattern_outer_circle_max_alpha" ascii

// Samsung 专用
$sam1 = "com.samsung.android.biometrics.app.setting" ascii
$sam2 = "sec_lock_pattern_dot_color" ascii
$sam3 = "sec_lock_pattern_path_color" ascii

// Xiaomi 专用
$mi1 = "miui_lock_pattern_dot_color" ascii
$mi2 = "miui_lock_pattern_path_color" ascii
```

### 11.2 组合检测规则

```yara
rule tiangong_rat_vendor_cipher_capture {
    meta:
        description = "Tiangong RAT - Multi-vendor PIN/Pattern capture"
        date = "2026-04-21"

    strings:
        // 跨厂商图案 View ID（任一 APK 不应同时包含这些）
        $vid1 = "colorLockPatternView" ascii
        $vid2 = "vivo_lock_pattern_view" ascii
        $vid3 = "com.samsung.android.biometrics.app.setting:id/lockPattern" ascii

        // 跨厂商 SystemUI 资源（同上）
        $res1 = "hwlock_pattern_dot_size" ascii
        $res2 = "vivo_keyguard_select_point_width" ascii
        $res3 = "coui_lock_pattern_dot_color" ascii
        $res4 = "sec_lock_pattern_dot_color" ascii
        $res5 = "miui_lock_pattern_dot_color" ascii

        // 密码捕获基础设施
        $inf1 = "CipherCaptureManager" ascii
        $inf2 = "PatternCaptureOverlay" ascii
        $inf3 = "PASSWORD_QUALITY_NUMERIC_COMPLEX" ascii
        $inf4 = "PASSWORD_QUALITY_TOUCH_POINTS" ascii

        // PIN 键盘
        $pin1 = "VivoPinkey" ascii
        $pin2 = "vivo_pin_confirm" ascii

    condition:
        uint16(0) == 0x4B50 and (
            (2 of ($vid*)) or
            (3 of ($res*)) or
            ($inf1 and $inf2 and 1 of ($res*)) or
            ($pin1 and 1 of ($vid*))
        )
}
```

---

## 十二、关键文件索引

| 文件 | 类名 | 行数 | 职责 |
|------|------|------|------|
| `cipher/C0335a1.java` | CipherCaptureManager | 3005 | 主编排器: 模式选择 + 事件监听 + 密码重建 |
| `cipher/C0337a3.java` | PatternCaptureOverlay | 1048 | 图案: View 查找 + 资源读取 + 覆盖层创建 |
| `cipher/C0339a5.java` | TouchViewManager | 745 | PIN: 触摸事件收集 + 按键提取 |
| `cipher/C0336a2.java` | PatternView | ~800 | 图案覆盖层 View 组件 |
| `cipher/C0338a4.java` | ViewOnTouchListener | 300 | 触摸拦截 + 坐标查找 |
| `cipher/C0340a6.java` | ViewCacheCollector | 170 | 单例工厂 + 上传回调 |
| `cipher/C0341a7.java` | TouchViewManager Core | 563 | 前台应用监控 + 提取协调 |
| `cipher/CipherDataHolder.java` | CipherDataHolder | 175 | 多源数据聚合 + 提取优先级 |
| `cipher/CipherExtractor.java` | CipherExtractor | 50 | 单例协调器 + 数字验证 |
| `cipher/CipherResult.java` | CipherResult | 31 | 结果容器 |
| `cipher/ListenPropResponse.java` | ListenPropResponse | 32 | 事件数据 |
| `cipher/Point.java` | Point | 43 | 触摸坐标 |
| `cipher/UiObject.java` | UiObject | 266 | 无障碍节点封装 |
| `cipher/DotAlign.java` | DotAlign | 38 | 图案网格对齐枚举 |
| `p000/nm0.java` | 锁屏类型检测 | 290 | 关键词 + UI 分析 |
| `p000/xm0.java` | 图案样式参数 | 68 | SystemUI 资源数据 |
| `p000/wm0.java` | View 边界 | 39 | 屏幕/父级坐标 |
| `p000/AbstractC1117qo.java` | 品牌检测 | — | 4 个检测函数 |
| `locateValues.json` | 多语言配置 | 33KB | 36 语言 UI 关键词 |
| `app_config.json` | 社工文本 | 6.5KB | 多语言密码弹窗文本 |
