# OPPO / Realme / OnePlus — PIN 密码捕获厂商适配审计

> **样本**: update.apk (tiangong RAT)
> **品牌检测**: `AbstractC1117qo.m214448e4()` (行 720-726)
> **匹配品牌**: `Build.BRAND.toLowerCase() == "oppo" || "realme" || "oneplus"`
> **日期**: 2026-04-21

---

## 一、品牌检测

```java
// AbstractC1117qo.java:720-726
public static boolean m214448e4() {
    String lowerCase = Build.BRAND.toLowerCase(Locale.ROOT);
    return lowerCase.equals("oppo") || lowerCase.equals("realme") || lowerCase.equals("oneplus");
}
```

---

## 二、PIN 数字键盘

### 2.1 键盘按键识别

OPPO 系**没有专用 PIN 键盘 View ID**，走 AOSP 通用 6 级回退。

`locateValues.json` 显示 OPPO 使用 `desc`（contentDescription）属性匹配 UI 元素，而非 View ID。

### 2.2 锁屏设置页面包名

**文件**: `C0335a1.java` 行 780

OPPO 系有 **3 个专用设置包名**：
```java
"oplus.settings"    // OnePlus/OPLUS 统一设置
"oppo.settings"     // OPPO 传统设置
"coloros.settings"  // ColorOS 设置
```

---

## 三、图案密码

### 3.1 图案 View 查找

**文件**: `C0337a3.java` 方法 `m211844a7()`，行 689-690

OPPO 系有**专用图案 View ID**：

| 顺序 | View ID | 说明 |
|------|---------|------|
| 1-6 | AOSP + Samsung 通用 | 先尝试通用 ID |
| **7** | **`com.android.systemui:id/colorLockPatternView`** | **OPPO/ColorOS 专用** |
| 8-9 | Vivo + AOSP 兜底 | |

**条件检测**:
```java
// C0337a3.java:689-690
if (AbstractC1117qo.m214448e4()) {  // 仅 OPPO/Realme/OnePlus
    return m211843a6(rootInActiveWindow, 
        "com.android.systemui:id/colorLockPatternView");
}
```

### 3.2 SystemUI 资源读取

#### 颜色资源

| 资源名 | 用途 | 行号 |
|--------|------|------|
| `coui_lock_pattern_dot_color` | 图案点颜色 | 900 |
| `coui_lock_pattern_path_color` | 连线颜色 | 904 |

#### 专用透明度资源

**文件**: `C0337a3.java` 行 816-821

```java
// OPPO 专用外圈光晕透明度
int alphaId = resources.getIdentifier(
    "coui_lock_pattern_outer_circle_max_alpha", "dimen", "com.android.systemui");
if (alphaId != 0) {
    outerCircleAlpha = resources.getFloat(alphaId);
} else {
    outerCircleAlpha = 0.1f;  // 默认值
}
```

这是**唯一有专用透明度资源的厂商**。

#### 宽高比

**文件**: `C0337a3.java` 行 228

```java
// OPPO/Realme/OnePlus 是唯一使用正方形宽高比的厂商
c0336a2.setAspectRatio(
    (lowerCase.equals("oppo") || lowerCase.equals("realme") || lowerCase.equals("oneplus")) 
    ? 1   // 正方形
    : 0   // 自由比例
);
```

**实现原理**（`C0336a2.java` 行 574-583）：
```java
if (aspectRatio == 1) {
    suggestedMinimumHeight = Math.min(suggestedMinimumWidth, suggestedMinimumHeight);
    // 强制高度 = min(宽, 高) → 正方形
}
```

### 3.3 硬编码回退参数

当 SystemUI 资源不可用时（行 240-248）：

| 参数 | 值 | 视觉效果 |
|------|-----|---------|
| normalStateColor | `#4CFFFFFF` | **半透明白** |
| correctStateColor | `#4CFFFFFF` | 半透明白 |
| dotSelectedColor | `#4CFFFFFF` | 半透明白 |
| dotNormalSize | 30 px | |
| dotSelectedSize | 60 px | |
| pathWidth | 6 px | **最细**（所有厂商中） |
| pathColor | `#FF000000` | **黑色**（唯一使用黑色线条的厂商） |
| **aspectRatio** | **1** | **正方形**（唯一） |
| dotAnimationDuration | 150 ms | 默认 |
| pathEndAnimationDuration | 100 ms | 默认 |

---

## 四、OPPO 系独有特征

### 4.1 正方形图案网格

OPPO/Realme/OnePlus 的 ColorOS 锁屏图案使用**正方形网格**（而非其他厂商的自由比例矩形）。RAT 通过 `aspectRatio = 1` 精确匹配此布局。

### 4.2 极细线条 + 黑色路径

ColorOS 图案锁屏使用细线条（6px）和黑色路径，与其他厂商的白色/灰色粗线形成鲜明对比。RAT 硬编码回退参数精确匹配此视觉风格。

### 4.3 外圈光晕透明度

OPPO 是唯一有专用 `coui_lock_pattern_outer_circle_max_alpha` 资源的厂商，控制图案点外圈光晕的透明度。

---

## 五、检测特征

### YARA 字符串

```yara
$oppo1 = "colorLockPatternView" ascii
$oppo2 = "coui_lock_pattern_dot_color" ascii
$oppo3 = "coui_lock_pattern_path_color" ascii
$oppo4 = "coui_lock_pattern_outer_circle_max_alpha" ascii
$oppo5 = "oplus.settings" ascii
$oppo6 = "oppo.settings" ascii
$oppo7 = "coloros.settings" ascii
```

### 行为特征

| 特征 | 说明 |
|------|------|
| 读取 `coui_*` 前缀 SystemUI 资源 | ColorOS 专用前缀 |
| `aspectRatio = 1` 的图案覆盖层 | 正方形网格 = OPPO 系特征 |
| 同时检测 3 个设置包名 | oplus/oppo/coloros 三合一 |

---

## 六、OPPO 系适配特点总结

| 维度 | 评估 |
|------|------|
| PIN 键盘 | 无专用适配，但使用 `desc` 属性匹配 |
| 图案 View | **专用 ID** `colorLockPatternView` |
| SystemUI 资源 | `coui_*` 前缀颜色 + **独有透明度资源** |
| 硬编码特点 | **正方形网格 + 黑色细线**（6px） |
| 锁屏设置 | **3 个包名**（oplus/oppo/coloros） |
| 独有特征 | **唯一正方形 aspectRatio、唯一黑色路径、唯一外圈透明度** |
