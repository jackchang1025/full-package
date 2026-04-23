# Huawei / Honor — PIN 密码捕获厂商适配审计

> **样本**: update.apk (tiangong RAT)
> **品牌检测**: `AbstractC1117qo.m214446e2()` (行 705-711)
> **匹配品牌**: `Build.BRAND.toLowerCase() == "huawei" || "honor"`
> **日期**: 2026-04-21

---

## 一、品牌检测

```java
// AbstractC1117qo.java:705-711
public static boolean m214446e2() {
    String lowerCase = Build.BRAND.toLowerCase(Locale.ROOT);
    return lowerCase.equals("huawei") || lowerCase.equals("honor");
}
```

---

## 二、PIN 数字键盘

### 2.1 键盘按键识别

Huawei/Honor **没有专用 PIN 键盘 View ID**，走 AOSP 通用 6 级回退策略：

| 优先级 | View ID 模式 | 说明 |
|--------|-------------|------|
| 1 | `com.android.systemui:id/key{N}` | AOSP 标准 |
| 2-6 | 通用回退 | 见 AOSP 文档 |

### 2.2 锁屏设置页面包名

**文件**: `C0335a1.java` 行 780

Huawei/Honor 未使用专用设置包名，走 `com.android.settings` 通用路径。

但 `locateValues.json` 配置了 Honor 专用设置类：
```
com.hihonor.settingslib.SubSettings
```

### 2.3 确认按钮

无 Huawei 专用确认按钮 ID，使用 AOSP 通用 `key_enter`。

---

## 三、图案密码

### 3.1 图案 View 查找

**文件**: `C0337a3.java` 方法 `m211844a7()`，行 678-705

Huawei/Honor **没有专用图案 View ID**，通过通用 AOSP ID 查找：

| 顺序 | View ID | 命中概率 |
|------|---------|---------|
| 1 | `com.android.systemui:id/lockPattern` | **主要** |
| 4 | `com.android.systemui:id/biometric_lockPattern` | 生物识别场景 |
| 9 | `com.android.systemui:id/lockPatternView` | 兜底 |

### 3.2 SystemUI 资源读取

**文件**: `C0337a3.java` 方法 `m211846a9()`，行 766-796

Huawei/Honor 有 **最深的资源回退链**（5 级 + 硬编码兜底）：

#### 点大小资源（5 级回退）

| 优先级 | SystemUI dimen 资源名 | 说明 |
|--------|---------------------|------|
| 1 | `hwlock_pattern_dot_size` | HW 前缀标准 |
| 2 | `hw_pattern_dot_size` | HW 短前缀 |
| 3 | `hw_lock_pattern_dot_size` | HW 完整前缀 |
| 4 | `keyguard_pattern_dot_size` | Keyguard 通用 |
| 5 | `lock_pattern_dot_size` | AOSP 通用 |
| 兜底 | innerDot=11×density, halo=32×density | 硬编码 |

**代码**:
```java
// C0337a3.java:767-796
List<String> hwDotResources = listOf(
    "hwlock_pattern_dot_size",
    "hw_pattern_dot_size",
    "hw_lock_pattern_dot_size",
    "keyguard_pattern_dot_size"
);
for (String resName : hwDotResources) {
    int id = resources.getIdentifier(resName, "dimen", "com.android.systemui");
    if (id != 0) {
        dotSize = resources.getDimensionPixelSize(id);
        break;
    }
}
// 如果全部失败 → AOSP lock_pattern_dot_size
// 仍然失败 → 硬编码:
innerDot = (int) (11 * density);  // ~11dp
halo = (int) (32 * density);     // ~32dp
```

#### Halo 计算

```java
haloSize = dotSize * 3;  // 行 784
```

#### 颜色资源

| 资源名 | 用途 | 行号 |
|--------|------|------|
| `hwlock_pattern_dot_color` | 图案点颜色 | 918 |
| `hwlock_pattern_path_color` | 连线颜色 | 922 |

如果颜色资源不存在，使用主题自适应色（深色模式: `#671FFFFF`, 浅色模式: `#4CFFFFFF`）。

#### 选中点大小

```java
// 尝试从资源读取
lock_pattern_dot_size_activated
// 失败则: haloSize * 1.5
```

### 3.3 硬编码回退参数

当 SystemUI 资源完全不可用时（行 265-272）：

| 参数 | 值 | 视觉效果 |
|------|-----|---------|
| normalStateColor | `#FFFFFFFF` | 白色 |
| correctStateColor | `#FFFFFFFF` | 白色 |
| dotSelectedColor | `#FFFFFFFF` | 白色 |
| dotNormalSize | 32 px | 中等 |
| dotSelectedSize | 50 px | |
| pathWidth | **20 px** | **较粗**（仅次于 Vivo 的 30px） |
| pathColor | `#FF888888` | 灰色 |
| aspectRatio | 0 | 自由比例 |
| dotAnimationDuration | 150 ms | 默认 |
| pathEndAnimationDuration | 100 ms | 默认 |

---

## 四、掩码字符

Huawei/Honor 锁屏密码框常用 `●` (U+25CF) 作为掩码字符。

RAT 的掩码过滤列表包含此字符（`C0335a1.java` 行 1983）：
```java
"•" (U+2022), "●" (U+25CF), "⬤" (U+2B24), "◉" (U+25C9), "*"
```

---

## 五、检测特征

### YARA 字符串

```yara
$hw1 = "hwlock_pattern_dot_size" ascii
$hw2 = "hw_pattern_dot_size" ascii
$hw3 = "hw_lock_pattern_dot_size" ascii
$hw4 = "keyguard_pattern_dot_size" ascii
$hw5 = "hwlock_pattern_dot_color" ascii
$hw6 = "hwlock_pattern_path_color" ascii
$hw7 = "com.hihonor.settingslib.SubSettings" ascii
```

### 行为特征

| 特征 | 说明 |
|------|------|
| 读取 `com.android.systemui` 资源中 `hwlock_*` / `hw_*` 前缀 dimen | 非系统应用不应读取这些资源 |
| 5 级资源回退链 | 单一 APK 同时尝试 4 种 HW 前缀资源名 |

---

## 六、Huawei/Honor 适配特点总结

| 维度 | 评估 |
|------|------|
| PIN 键盘 | 无专用适配，走 AOSP 通用 |
| 图案 View | 无专用 ID，走 AOSP 通用 |
| SystemUI 资源 | **最深回退链**（5 级 + 硬编码），4 种 `hw*` 前缀资源名 |
| 颜色 | 专用资源 `hwlock_pattern_dot_color` / `hwlock_pattern_path_color` |
| 硬编码特点 | 白色点 + 灰色线 + 较粗线宽 (20px) |
| 锁屏设置 | Honor 专用 `com.hihonor.settingslib.SubSettings` |
