# Samsung — PIN 密码捕获厂商适配审计

> **样本**: update.apk (tiangong RAT)
> **品牌检测**: `AbstractC0779a1.m213656a9(Build.BRAND, "samsung")` (字符串包含匹配)
> **匹配品牌**: `Build.BRAND` 包含 `"samsung"`
> **日期**: 2026-04-21

---

## 一、品牌检测

Samsung 没有专用检测函数，使用字符串包含匹配：

```java
// C0337a3.java:251
if (AbstractC0779a1.m213656a9(str2, "samsung")) { ... }
```

---

## 二、PIN 数字键盘

Samsung **没有专用 PIN 键盘 View ID**，走 AOSP 通用 6 级回退策略。

### 锁屏设置页面包名

**文件**: `C0335a1.java` 行 780

Samsung 有**专用生物识别设置包名**：
```java
"com.samsung.android.biometrics.app.setting"
```

---

## 三、图案密码

### 3.1 图案 View 查找 — 2 个 Samsung 专用 ID

**文件**: `C0337a3.java` 方法 `m211844a7()`，行 682

Samsung 拥有 **2 个专用图案 View ID**，排在通用 ID 之后：

| 顺序 | View ID | 说明 |
|------|---------|------|
| 1 | `com.android.systemui:id/lockPattern` | AOSP 通用 |
| 2 | `com.android.settings:id/lockPattern` | AOSP 设置 |
| **3** | **`com.samsung.android.biometrics.app.setting:id/lockPattern`** | **Samsung 专用** |
| 4 | `com.android.systemui:id/biometric_lockPattern` | AOSP 生物识别 |
| 5 | `com.android.settings:id/biometric_lockPattern` | AOSP 设置 |
| **6** | **`com.samsung.android.biometrics.app.setting:id/biometric_lockPattern`** | **Samsung 专用** |

### 3.2 SystemUI 颜色资源

| 资源名 | 用途 | 行号 |
|--------|------|------|
| `sec_lock_pattern_dot_color` | 图案点颜色 | 906 |
| `sec_lock_pattern_path_color` | 连线颜色 | 910 |

`locateValues.json` 中记录了 Samsung 图案样式的参考值：
```json
"dotColor": "#2196F3",   // Material Blue
"lineColor": "#2196F3"
```

### 3.3 硬编码回退参数

当 SystemUI 资源不可用时（行 251-263）：

| 参数 | 值 | 视觉效果 |
|------|-----|---------|
| normalStateColor | `#FFCCCCCC` | 浅灰 |
| correctStateColor | `#FFCCCCCC` | 浅灰 |
| dotSelectedColor | `#FFCCCCCC` | 浅灰 |
| dotNormalSize | **36 px** | **最大**（所有厂商中） |
| dotSelectedSize | 50 px | |
| pathWidth | 10 px | 中等 |
| pathColor | `#FFFFFFFF` | **白色** |
| aspectRatio | 0 | 自由比例 |
| dotAnimationDuration | **100 ms** | 较快 |
| pathEndAnimationDuration | **200 ms** | **最慢**（所有厂商中） |

### 3.4 动画特点

Samsung 的图案锁屏动画特征：
- **点动画快**（100ms vs 默认 150ms）：点亮时响应快
- **线消失慢**（200ms vs 默认 100ms）：连线在输入完成后较慢消失

这与 Samsung One UI 的实际视觉行为一致。

---

## 四、Samsung 独有特征

### 4.1 生物识别设置包名

Samsung 是唯一使用 `com.samsung.android.biometrics.app.setting` 独立包名管理生物识别/锁屏设置的厂商。

### 4.2 最大初始点

Samsung 的 dotNormalSize = 36px 是所有厂商中最大的，匹配 One UI 的圆润大点风格。

### 4.3 线消失最慢

`pathEndAnimationDuration = 200ms` 是所有厂商中最长的，匹配 Samsung 锁屏图案线条的渐隐效果。

---

## 五、检测特征

### YARA 字符串

```yara
$sam1 = "com.samsung.android.biometrics.app.setting:id/lockPattern" ascii
$sam2 = "com.samsung.android.biometrics.app.setting:id/biometric_lockPattern" ascii
$sam3 = "com.samsung.android.biometrics.app.setting" ascii
$sam4 = "sec_lock_pattern_dot_color" ascii
$sam5 = "sec_lock_pattern_path_color" ascii
```

### 行为特征

| 特征 | 说明 |
|------|------|
| 查找 `com.samsung.android.biometrics.app.setting` 包的 View ID | 非 Samsung 系统应用不应查找 |
| 读取 `sec_lock_pattern_*` SystemUI 资源 | Samsung `sec_` 前缀 |

---

## 六、Samsung 适配特点总结

| 维度 | 评估 |
|------|------|
| PIN 键盘 | 无专用适配 |
| 图案 View | **2 个专用 ID**（biometrics 包名） |
| SystemUI 资源 | `sec_*` 前缀颜色资源 |
| 硬编码特点 | **最大点 (36px) + 白色线 + 最慢线消失动画 (200ms)** |
| 锁屏设置 | `com.samsung.android.biometrics.app.setting` |
