# Xiaomi / Redmi / POCO / BlackShark — PIN 密码捕获厂商适配审计

> **样本**: update.apk (tiangong RAT)
> **品牌检测**: `AbstractC1117qo.m214450e6()` (行 738-744)
> **匹配品牌**: `Build.BRAND.toLowerCase() == "xiaomi" || "redmi" || "poco" || "blackshark"`
> **日期**: 2026-04-21

---

## 一、品牌检测

```java
// AbstractC1117qo.java:738-744
public static boolean m214450e6() {
    String lowerCase = Build.BRAND.toLowerCase(Locale.ROOT);
    return lowerCase.equals("xiaomi") || lowerCase.equals("redmi") 
        || lowerCase.equals("poco") || lowerCase.equals("blackshark");
}
```

覆盖 Xiaomi 全系 4 个子品牌，包括游戏手机 BlackShark。

---

## 二、PIN 数字键盘

Xiaomi 系**没有专用 PIN 键盘 View ID**，走 AOSP 通用 6 级回退。

### 锁屏/安全相关包名

`locateValues.json` 配置了 Xiaomi 专用安全中心：
```
com.miui.securitycenter
```

---

## 三、图案密码

### 3.1 图案 View 查找

Xiaomi 系**没有专用图案 View ID**，通过 AOSP 通用 9 ID 序列查找。

### 3.2 SystemUI 颜色资源

| 资源名 | 用途 | 行号 |
|--------|------|------|
| `miui_lock_pattern_dot_color` | 图案点颜色 | 939 |
| `miui_lock_pattern_path_color` | 连线颜色 | 943 |

### 3.3 硬编码回退参数

当 SystemUI 资源不可用时（行 283-295）：

| 参数 | 值 | 视觉效果 |
|------|-----|---------|
| normalStateColor | 主题自适应 | 深色: `#671FFFFF`, 浅色: `#4CFFFFFF` |
| correctStateColor | 主题自适应 | 同上 |
| dotSelectedColor | 主题自适应 | 同上 |
| dotNormalSize | 30 px | |
| dotSelectedSize | 60 px | |
| pathWidth | 3×density (min 3px) | 密度自适应 |
| pathColor | 主题自适应 | |
| aspectRatio | 0 | 自由比例 |
| dotAnimationDuration | **50 ms** | **最快**（所有厂商中） |
| pathEndAnimationDuration | **50 ms** | **最快**（所有厂商中） |

---

## 四、Xiaomi 系独有特征

### 4.1 最快动画速度

Xiaomi 系使用 **50ms 动画时长**（默认 150ms 的 1/3），匹配 MIUI 锁屏图案的快速响应风格。

| 对比 | 点动画 | 线消失 |
|------|--------|--------|
| Xiaomi | **50ms** | **50ms** |
| Samsung | 100ms | 200ms |
| 其他厂商 | 150ms | 100ms |

### 4.2 主题自适应

Xiaomi 是与 AOSP 通用一样使用深色/浅色模式自适应颜色的厂商（不像其他厂商有固定硬编码颜色）。

**主题检测**:
```java
// C0337a3.java:637-642 (m211842a5)
boolean isDark = (context.getResources().getConfiguration().uiMode & 48) == 32;
return isDark ? 0x671FFFFF : 0x4CFFFFFF;
```

### 4.3 安全中心包名

`com.miui.securitycenter` 用于 Xiaomi 设备的安全/权限管理。

---

## 五、检测特征

### YARA 字符串

```yara
$mi1 = "miui_lock_pattern_dot_color" ascii
$mi2 = "miui_lock_pattern_path_color" ascii
$mi3 = "com.miui.securitycenter" ascii
```

### 行为特征

| 特征 | 说明 |
|------|------|
| 读取 `miui_*` SystemUI 资源 | MIUI 专用前缀 |
| 50ms 动画时长的图案覆盖层 | 异常快的动画 = Xiaomi 适配 |

---

## 六、Xiaomi 系适配特点总结

| 维度 | 评估 |
|------|------|
| PIN 键盘 | 无专用适配 |
| 图案 View | 无专用 ID |
| SystemUI 资源 | `miui_*` 前缀颜色资源 |
| 硬编码特点 | **最快动画 (50ms) + 主题自适应颜色** |
| 特殊包名 | `com.miui.securitycenter` |
| 品牌覆盖 | 4 个子品牌（含 BlackShark 游戏手机） |
