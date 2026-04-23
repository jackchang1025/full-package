# Tecno / Itel / Infinix — PIN 密码捕获厂商适配审计

> **样本**: update.apk (tiangong RAT)
> **品牌检测**: `AbstractC0779a1.m213656a9(Build.BRAND, "tecno"/"itel"/"infinix")` (字符串包含匹配)
> **匹配品牌**: `Build.BRAND` 包含 `"tecno"` 或 `"itel"` 或 `"infinix"`
> **日期**: 2026-04-21

---

## 一、品牌检测

没有专用检测函数，通过排除法检测（行 283）：

```java
// C0337a3.java:283
if (!AbstractC1117qo.m214450e6()                          // 非 Xiaomi 系
    && !AbstractC0779a1.m213656a9(str2, "tecno")           // 是 Tecno
    && !AbstractC0779a1.m213656a9(str2, "itel")            // 或 Itel
    && !AbstractC0779a1.m213656a9(str2, "infinix")) {      // 或 Infinix
    // → 走 AOSP 通用路径
} else {
    // → 走 Tecno/Itel/Infinix 专用路径（或 Xiaomi 路径）
}
```

**传音控股**（Transsion Holdings）旗下三个品牌，主要面向非洲和东南亚市场。

---

## 二、PIN 数字键盘

无任何专用适配，完全走 AOSP 通用 6 级回退。

---

## 三、图案密码

### 3.1 图案 View 查找

无专用 View ID，走 AOSP 通用 9 ID 序列。

### 3.2 SystemUI 资源

无专用 SystemUI 资源名。不读取任何厂商前缀资源。

### 3.3 硬编码回退参数

当 SystemUI 资源不可用时（行 297-303）：

| 参数 | 值 | 视觉效果 |
|------|-----|---------|
| normalStateColor | `#FFFFFFFF` | **白色** |
| correctStateColor | `#FFFFFFFF` | 白色 |
| dotSelectedColor | `#FFFFFFFF` | 白色 |
| dotNormalSize | **20 px** | 小 |
| dotSelectedSize | **30 px** | **最小选中点**（所有厂商中） |
| pathWidth | **5 px** | 细 |
| pathColor | `#FFFFFFFF` | **白色** |
| aspectRatio | 0 | 自由比例 |
| dotAnimationDuration | 150 ms | 默认 |
| pathEndAnimationDuration | 100 ms | 默认 |

---

## 四、Tecno/Itel/Infinix 独有特征

### 4.1 全白配色

所有颜色参数统一为白色 (`#FFFFFFFF`)，匹配传音系手机锁屏的简洁白色图案风格。

### 4.2 最小选中点

`dotSelectedSize = 30px` 是所有厂商中最小的（AOSP 默认 60px、Samsung 50px）。

### 4.3 与 Xiaomi 的代码耦合

在代码中，Tecno/Itel/Infinix 的分支与 Xiaomi 系共享同一个条件块，通过排除法分离。这暗示 RAT 开发者将传音系设备归类为"类 Xiaomi"的简单 Android 设备。

---

## 五、检测特征

传音系品牌没有专用 SystemUI 资源或 View ID，YARA 检测需要依赖品牌名字符串与密码捕获基础设施的组合：

```yara
// 品牌名（低特异性，需组合）
$tecno = "tecno" ascii nocase
$itel = "itel" ascii nocase
$infinix = "infinix" ascii nocase

// 需与密码捕获代码组合
$cipher = "CipherCaptureManager" ascii
$pattern = "PatternCaptureOverlay" ascii
```

---

## 六、Tecno/Itel/Infinix 适配特点总结

| 维度 | 评估 |
|------|------|
| PIN 键盘 | 无专用适配 |
| 图案 View | 无专用 ID |
| SystemUI 资源 | 无 |
| 硬编码特点 | **全白配色 + 最小选中点 (30px) + 细线 (5px)** |
| 市场定位 | 非洲/东南亚低端市场，表明 RAT 目标包含发展中国家用户 |
