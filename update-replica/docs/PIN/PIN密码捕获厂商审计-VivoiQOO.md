# Vivo / iQOO — PIN 密码捕获厂商适配审计

> **样本**: update.apk (tiangong RAT)
> **品牌检测**: `AbstractC1117qo.m214449e5()` (行 729-735)
> **匹配品牌**: `Build.BRAND.toLowerCase() == "vivo" || "iqoo"`
> **日期**: 2026-04-21

---

## 一、品牌检测

```java
// AbstractC1117qo.java:729-735
public static boolean m214449e5() {
    String lowerCase = Build.BRAND.toLowerCase(Locale.ROOT);
    return lowerCase.equals("vivo") || lowerCase.equals("iqoo");
}
```

---

## 二、PIN 数字键盘

### 2.1 键盘按键识别 — Vivo 专用分支

**文件**: `C0339a5.java` 行 491-493

Vivo/iQOO 是**唯一拥有专用 PIN 键盘 View ID 的厂商**：

| 优先级 | View ID 模式 | 说明 |
|--------|-------------|------|
| 1 | `com.android.systemui:id/key{N}` | AOSP 通用（先尝试） |
| **2** | **`com.android.systemui:id/VivoPinkey{N}`** | **Vivo 专用** |
| 3-6 | 通用回退 | |

**代码**:
```java
// C0339a5.java:491-493
if (AbstractC0779a1.m213679d2(string, false, 
        "com.android.systemui:id/VivoPinkey")) {
    linkedList3.add(AbstractC0779a1.m213682d5(string, 
        "com.android.systemui:id/VivoPinkey"));
}
```

### 2.2 确认按钮 — 4 种专用 ID

**文件**: `C0335a1.java` 行 838-850

Vivo 有 **4 种不同的确认按钮 ID**（不同机型/版本使用不同 ID）：

| View ID | 控件类型 | 场景 |
|---------|---------|------|
| `{pkg}:id/mix_confirm` | `android.view.View` | 混合密码确认 |
| `{pkg}:id/iv_complete` | `android.widget.TextView` | 完成按钮 |
| `{pkg}:id/vivo_pin_confirm` | `android.widget.Button` | PIN 专用确认 |
| `{pkg}:id/mix_normal_confirm` | `android.widget.TextView` | 普通混合确认 |

**代码**:
```java
// C0335a1.java:838-850
if (AbstractC1117qo.m214449e5()) {  // 仅 Vivo/iQOO
    List<Pair> vivoConfirmButtons = listOf(
        new Pair(pkg + ":id/mix_confirm", "android.view.View"),
        new Pair(pkg + ":id/iv_complete", "android.widget.TextView"),
        new Pair(pkg + ":id/vivo_pin_confirm", "android.widget.Button"),
        new Pair(pkg + ":id/mix_normal_confirm", "android.widget.TextView")
    );
}
```

### 2.3 锁屏设置页面包名

**文件**: `C0335a1.java` 行 780

```java
"vivo.settings"  // Vivo 设置
```

### 2.4 PIN 设置页面 View

**文件**: `C0335a1.java` 行 793

```java
"com.android.settings:id/vivo_pin_confirm"  // Vivo PIN 确认按钮
```

---

## 三、图案密码

### 3.1 图案 View 查找

**文件**: `C0337a3.java` 方法 `m211844a7()`，行 692-693

Vivo/iQOO 有**专用图案 View ID**：

| 顺序 | View ID | 说明 |
|------|---------|------|
| 1-7 | AOSP + Samsung + OPPO 通用 | 先尝试其他 |
| **8** | **`com.android.systemui:id/vivo_lock_pattern_view`** | **Vivo 专用** |
| 9 | AOSP 兜底 | |

**代码**:
```java
// C0337a3.java:692-693
if (AbstractC1117qo.m214449e5()) {  // 仅 Vivo/iQOO
    return m211843a6(rootInActiveWindow, 
        "com.android.systemui:id/vivo_lock_pattern_view");
}
```

### 3.2 SystemUI 资源读取

**文件**: `C0337a3.java` 方法 `m211846a9()`，行 837-865

Vivo/iQOO 有**最深的点大小资源回退链**（4 级 + 硬编码兜底）：

#### 点大小资源（4 级回退）

| 优先级 | SystemUI dimen 资源名 | 计算方式 | 行号 |
|--------|---------------------|---------|------|
| 1 | `vivo_keyguard_select_point_width` | innerDot = 值, halo = 值 × 2.5 | 837, 842-845 |
| 2 | `vivo_keyguard_spring_patten_point_width` | 同上 | 838, 846-849 |
| 3 | `vivo_pattern_unlock_size` | innerDot = 值/12, halo = 值/8 | 840, 850-856 |
| 兜底 | 硬编码 | innerDot = 8×density, halo = 20×density | 858-860 |

**代码**:
```java
// C0337a3.java:837-861
int selectPointId = resources.getIdentifier(
    "vivo_keyguard_select_point_width", "dimen", "com.android.systemui");
int springPointId = resources.getIdentifier(
    "vivo_keyguard_spring_patten_point_width", "dimen", "com.android.systemui");
int pathWidthId = resources.getIdentifier(
    "vivo_keyguard_path_width", "dimen", "com.android.systemui");
int patternSizeId = resources.getIdentifier(
    "vivo_pattern_unlock_size", "dimen", "com.android.systemui");

if (selectPointId != 0) {
    innerDot = resources.getDimensionPixelSize(selectPointId);
    halo = (int) (innerDot * 2.5f);
} else if (springPointId != 0) {
    innerDot = resources.getDimensionPixelSize(springPointId);
    halo = (int) (innerDot * 2.5f);
} else if (patternSizeId != 0) {
    int viewSize = resources.getDimensionPixelSize(patternSizeId);
    innerDot = viewSize / 12;
    halo = viewSize / 8;
} else {
    innerDot = (int) (8 * density);
    halo = (int) (20 * density);
}
```

#### Halo 计算

Vivo 使用 `× 2.5` 倍率（而非 Huawei 的 `× 3`）：
```java
halo = (int) (innerDot * 2.5f);
```

#### 线宽资源

| 资源名 | 行号 |
|--------|------|
| `vivo_keyguard_path_width` | 839, 862-864 |

#### 颜色资源

| 资源名 | 用途 | 行号 |
|--------|------|------|
| `vivo_lock_pattern_dot_color` | 图案点颜色 | 912 |
| `vivo_lock_pattern_path_color` | 连线颜色 | 916 |

### 3.3 硬编码回退参数

当 SystemUI 资源不可用时（行 273-281）：

| 参数 | 值 | 视觉效果 |
|------|-----|---------|
| normalStateColor | `#FFCCCCCC` | 浅灰 |
| correctStateColor | `#FFCCCCCC` | 浅灰 |
| dotSelectedColor | `#FFFFFF00` | **黄色**（唯一使用黄色选中点的厂商） |
| dotNormalSize | **20 px** | **最小**（所有厂商中） |
| dotSelectedSize | 40 px | |
| pathWidth | **30 px** | **最粗**（所有厂商中） |
| pathColor | `#FFF68F` | **浅黄色**（唯一使用黄色系路径的厂商） |
| aspectRatio | 0 | 自由比例 |
| dotAnimationDuration | 150 ms | 默认 |
| pathEndAnimationDuration | 100 ms | 默认 |

---

## 四、掩码字符

Vivo 锁屏密码框使用 `◉` (U+25C9, Fisheye) 作为掩码字符。

RAT 的掩码过滤列表包含此字符（`C0335a1.java` 行 1983）。

---

## 五、Vivo/iQOO 独有特征

### 5.1 VivoPinkey — 唯一专用 PIN 键盘

Vivo/iQOO 是**所有适配厂商中唯一拥有专用 PIN 键盘 View ID 的**。其他厂商均使用 AOSP 标准 `key{N}` 格式。

### 5.2 4 种确认按钮

Vivo 的 PIN/密码确认按钮跨版本/机型变化大，RAT 需要维护 4 种 ID 的检测列表。

### 5.3 黄色视觉风格

Vivo/iQOO 是唯一使用黄色系配色方案的厂商：
- 选中点: 黄色 (`#FFFFFF00`)
- 路径: 浅黄色 (`#FFF68F`)

### 5.4 最小点 + 最粗线

Vivo 的图案锁屏使用最小的点（20px）和最粗的线条（30px），形成独特的细点粗线风格。

---

## 六、检测特征

### YARA 字符串

```yara
$vivo1 = "VivoPinkey" ascii
$vivo2 = "vivo_keyguard_select_point_width" ascii
$vivo3 = "vivo_keyguard_spring_patten_point_width" ascii
$vivo4 = "vivo_keyguard_path_width" ascii
$vivo5 = "vivo_pattern_unlock_size" ascii
$vivo6 = "vivo_lock_pattern_view" ascii
$vivo7 = "vivo_lock_pattern_dot_color" ascii
$vivo8 = "vivo_lock_pattern_path_color" ascii
$vivo9 = "vivo_pin_confirm" ascii
$vivo10 = "mix_confirm" ascii
$vivo11 = "iv_complete" ascii
$vivo12 = "mix_normal_confirm" ascii
```

### 行为特征

| 特征 | 说明 |
|------|------|
| 查找 `VivoPinkey` View ID | 非 Vivo 系统应用不应读取此 ID |
| 读取 `vivo_keyguard_*` SystemUI 资源 | Vivo 专用资源前缀 |
| 同时检测 4 种确认按钮 ID | 跨版本兼容的异常行为 |

---

## 七、Vivo/iQOO 适配特点总结

| 维度 | 评估 |
|------|------|
| PIN 键盘 | **唯一专用** `VivoPinkey` View ID |
| 确认按钮 | **4 种专用 ID**（最多） |
| 图案 View | **专用 ID** `vivo_lock_pattern_view` |
| SystemUI 资源 | **4 级点大小回退** + 线宽 + 2 颜色 |
| 硬编码特点 | **黄色系配色 + 最小点 + 最粗线** |
| 锁屏设置 | `vivo.settings` |
| 独有特征 | **适配最深的厂商：专用 PIN 键盘 + 专用图案 View + 专用确认按钮 + 专用资源** |
