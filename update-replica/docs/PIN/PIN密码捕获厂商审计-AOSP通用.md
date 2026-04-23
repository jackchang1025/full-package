# AOSP 通用 — PIN 密码捕获兜底适配审计

> **样本**: update.apk (tiangong RAT)
> **适用条件**: 未匹配任何专用厂商检测的所有设备
> **匹配品牌**: Google Pixel、Nokia、Motorola、Sony、LG、Nothing、其他所有品牌
> **日期**: 2026-04-21

---

## 一、触发条件

当以下品牌检测**全部返回 false** 时进入 AOSP 通用路径：

```
m214446e2() = false  → 非 Huawei/Honor
m214448e4() = false  → 非 OPPO/Realme/OnePlus
m214449e5() = false  → 非 Vivo/iQOO
m214450e6() = false  → 非 Xiaomi/Redmi/POCO/BlackShark
Build.BRAND ≠ "samsung"
Build.BRAND ≠ "tecno"/"itel"/"infinix"
```

---

## 二、PIN 数字键盘 — 完整 6 级回退

**文件**: `C0339a5.java` 行 460-525

| 优先级 | View ID 模式 | 提取逻辑 | 标签 |
|--------|-------------|---------|------|
| **1** | `com.android.systemui:id/key{N}` | 截取 `key` 后缀；排除 `key_enter`、`key_delete` | `"SystemUI"` |
| **2** | `com.android.systemui:id/VivoPinkey{N}` | 截取 `VivoPinkey` 后缀 | `"Vivo"` |
| **3** | `com.android.systemui:id/num{N}` | 截取 `num` 后缀 | `"num/char"` |
| **4** | `com.android.systemui:id/char_{N}` | 截取 `char_` 后缀；触发混合密码标记 `f53299b3=true` | `"num/char"` |
| **5** | 任意 `:id/` 且末尾含数字 | 取 ID 最后数字字符；排除 delete/enter/cancel | `"ID尾数字"` |
| **6** | 单字符数字文本节点 | 直接取节点文本 | `"单数字"` |

### 密码类型判断

```java
// CipherExtractor.m211773a0() — 纯数字检测
if (全部字符为数字) → "PASSWORD_QUALITY_NUMERIC_COMPLEX"
else → "PASSWORD_QUALITY_ALPHANUMERIC"

// f53299b3 标记（char_ 键被按下）
if (f53299b3 == true) → type = "password" (混合)
else → type = "pin" (纯数字)
```

---

## 三、图案密码 — 完整 9 ID 查找序列

**文件**: `C0337a3.java` 方法 `m211844a7()`，行 678-705

| 顺序 | View ID | 适配 |
|------|---------|------|
| 1 | `com.android.systemui:id/lockPattern` | AOSP 标准 |
| 2 | `com.android.settings:id/lockPattern` | AOSP 设置 |
| 3 | `com.samsung.android.biometrics.app.setting:id/lockPattern` | Samsung |
| 4 | `com.android.systemui:id/biometric_lockPattern` | AOSP 生物识别 |
| 5 | `com.android.settings:id/biometric_lockPattern` | AOSP 设置 |
| 6 | `com.samsung.android.biometrics.app.setting:id/biometric_lockPattern` | Samsung |
| 7 | `com.android.systemui:id/colorLockPatternView` | OPPO (条件) |
| 8 | `com.android.systemui:id/vivo_lock_pattern_view` | Vivo (条件) |
| 9 | `com.android.systemui:id/lockPatternView` | **AOSP 兜底** |

### SystemUI 资源读取（AOSP 标准）

| 资源名 | 用途 | 说明 |
|--------|------|------|
| `lock_pattern_dot_size` | 点大小 | AOSP 标准 |
| `lock_pattern_dot_size_activated` | 选中点大小 | 不存在则 halo×1.5 |
| `lock_pattern_dot_line_width` | 线宽 | 不存在则 3×density |

### 硬编码回退参数

| 参数 | 值 | 说明 |
|------|-----|------|
| normalStateColor | 主题自适应 | 深色: `#671FFFFF`, 浅色: `#4CFFFFFF` |
| correctStateColor | 主题自适应 | 同上 |
| dotSelectedColor | 主题自适应 | 同上 |
| dotNormalSize | 30 px | 中等 |
| dotSelectedSize | 60 px | 标准 |
| pathWidth | 3×density (min 3) | 密度自适应 |
| pathColor | 主题自适应 | |
| aspectRatio | 0 | 自由比例 |
| dotAnimationDuration | 150 ms | 标准 |
| pathEndAnimationDuration | 100 ms | 标准 |

---

## 四、锁屏类型检测

### nm0.java — 多语言关键词检测

**核心方法**: `m214126a5()`（行 201-240）

```
KeyguardManager.isKeyguardSecure()
    │ YES
    ▼
m214127a6() — UI 无障碍树分析
    │ "none"
    ▼
m214122a1() — 关键词启发式检测
    │ "none"
    ▼
默认返回 "pin"
```

**4 组多语言关键词** (来自 `dh0` 字典，覆盖 36 语言)：

| 关键词组 | 检测标志 | 包含词汇示例 |
|---------|---------|------------|
| 图案 | `f58384a0` | "图案"、"pattern"、"パターン"、"패턴" |
| PIN | `f58385a1` | "PIN"、"密码"、"暗証番号"、"핀" |
| 密码 | `f58386a2` | "密码"、"password"、"パスワード" |
| 解锁 | `f58387a3` | "解锁"、"Unlock"、"ロック解除"、"잠금 해제" |

**UI 节点分析** (method `m214125a4()`)：

| 标志 | 检测条件 | 含义 |
|------|---------|------|
| `f58388a4` | `isPassword()` 或 text 含 "password"/"edittext" | 密码输入框 |
| `f58389a5` | className 含 "keypad"/"keyboard" | 数字键盘 |
| `f58390a6` | className 含 "pattern"/"gesture" | 图案视图 |
| `f58393a9` | 按钮数量统计 | PIN 页面特征 |

---

## 五、侧信道捕获通用机制

### 5.1 TYPE_VIEW_TEXT_CHANGED 三重快照

**文件**: `C0335a1.java` 行 2040-2130

```
事件触发 (eventType == 16)
    │
    ├─ 快照 1: event.getBeforeText()      ← 变化前
    ├─ 快照 2: event.getText()            ← 事件文本
    └─ 快照 3: source.getText()           ← 节点实时
    │
    ▼
逐位比对 → 填充 '*' 占位 → 逐步替换为实际字符
    │
    ▼
全部位置重建完成 → 返回明文密码
```

### 5.2 掩码字符过滤（5 字符）

```java
// C0335a1.java:1983
"•" (U+2022)  // AOSP 标准
"●" (U+25CF)  // Samsung/Huawei
"⬤" (U+2B24)  // 部分 ROM
"◉" (U+25C9)  // Vivo
"*" (U+002A)  // 通用
```

### 5.3 密码强度分类

| cipherGradeCode | 含义 | 检测条件 |
|-----------------|------|---------|
| `PASSWORD_QUALITY_PATTERN` | 图案锁 | 图案 View 检测到 |
| `PASSWORD_QUALITY_NUMERIC` | 数字 PIN | 纯数字 4-6 位 |
| `PASSWORD_QUALITY_NUMERIC_COMPLEX` | 复杂 PIN | 纯数字 >6 位 |
| `PASSWORD_QUALITY_ALPHANUMERIC` | 混合密码 | 含字母+数字 |
| `PASSWORD_QUALITY_TOUCH_POINTS` | 触点图案 | 触摸坐标 ≥4 个 |

---

## 六、数据提取优先级

**文件**: `CipherDataHolder.java` 行 32-174

从无障碍事件中聚合数据时，使用 4 种属性类型：

| 属性类型 | ListenPropResponse.prop | 优先级 | 用途 |
|---------|------------------------|--------|------|
| View ID | `"id"` | 1 | 从 resource name 提取数字 |
| 按钮文本 | `"text"` | 2 | 从可见文本提取数字 |
| 内容描述 | `"desc"` | 3 | 从 contentDescription 提取 |
| ADB 坐标 | `"adb_coord"` | 4 | 触摸坐标 (≥6 个点才使用) |

提取优先级：`ID提取 > 文本提取 > 描述提取`，多种方法的结果可以合并。

---

## 七、AOSP 通用检测特征

### YARA 字符串

```yara
// 通用基础设施（跨厂商存在）
$inf1 = "CipherCaptureManager" ascii
$inf2 = "PatternCaptureOverlay" ascii
$inf3 = "TouchViewManager" ascii
$inf4 = "CipherExtractor" ascii
$inf5 = "CipherDataHolder" ascii

// 密码质量常量
$qual1 = "PASSWORD_QUALITY_NUMERIC_COMPLEX" ascii
$qual2 = "PASSWORD_QUALITY_ALPHANUMERIC" ascii
$qual3 = "PASSWORD_QUALITY_TOUCH_POINTS" ascii

// 通用 View ID
$vid1 = "com.android.systemui:id/lockPattern" ascii
$vid2 = "com.android.systemui:id/lockPatternView" ascii
$vid3 = "com.android.systemui:id/key" ascii

// 掩码字符集
$mask = { E2 80 A2 }  // UTF-8 "•"
```

---

## 八、AOSP 通用适配特点总结

| 维度 | 评估 |
|------|------|
| PIN 键盘 | **6 级回退**覆盖所有可能的 View ID 格式 |
| 图案 View | **9 ID 序列**包含所有已知厂商 |
| SystemUI 资源 | AOSP 标准 `lock_pattern_*` 资源 |
| 硬编码特点 | 主题自适应颜色 + 标准动画 |
| 锁屏检测 | 36 语言关键词 + UI 节点分析 |
| 设计哲学 | **兜底策略：宁可多查不可遗漏** |
