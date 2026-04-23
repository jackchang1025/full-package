# 魅族 + 通用/Generic ADB WiFi 配对 + 开发者选项自动化审计

> **样本**: update.apk (tiangong RAT)
> **魅族权限自动化**: `jadx-reference/rock/service/modules/yw5xud/C0366a3.java` (MeizuSteps, 2482 行, 21 Continuation)
> **通用权限自动化**: `jadx-reference/rock/service/modules/yw5xud/C0364a1.java` (GenericSteps, 3715 行, 18 Continuation)
> **开发者选项**: `jadx-reference/rock/service/modules/setup/C0358a0.java` (OpenDevelopmentDelegate)
> **ADB 配对编排器**: `jadx-reference/rock/service/modules/setup/C0360a2.java` (SystemOptimizeManager)
> **日期**: 2026-04-21
> **审计范围**: 开发者选项开启 + ADB 无线调试配对链路中的魅族和通用/回退专用代码

---

## 一、魅族 (MeizuSteps)

### 1.1 品牌检测

| 品牌 | 检测方法 | 系统属性 |
|------|---------|---------|
| Meizu | `Build.DISPLAY` 含 `flyme` | `ro.build.display.id` |

调度器 OS 标识: `"flyme"`

### 1.2 ADB 配对链路中的魅族代码

魅族在开发者选项 + ADB 配对链路中**无任何专用分支**——全部走通用路径：

| 阶段 | 处理方式 |
|------|---------|
| Phase 0 开发者选项 | 标准 Intent，`needsVersionInfoPage=false`（走通用版本号查找） |
| Phase 1 pairInDevOption | 通用滚动查找"无线调试" |
| Phase 2 pairInWifiDebugWindow | 通用 checkbox 勾选 |
| Phase 3 pairInSecurityCenter | 通用"允许"/"下一步" |

### 1.3 魅族 yw5xud 权限自动化概况

| 维度 | 值 |
|------|-----|
| 代码量 | 2,482 行 |
| FlowType 步骤数 | **4**（所有厂商最少） |
| 安全中心包名 | `com.meizu.safe` |

**FlowType 枚举**:
```java
STARTUP_MANAGER("启动管理")
BATTERY_OPTIMIZATION("电池优化")
OVERLAY_PERMISSION("悬浮窗权限")
ALL_FILES_ACCESS("文件访问权限")
```

步骤最少是因为 Flyme OS 相比其他国产系统对后台进程的限制较少。

### 1.4 魅族检测特征

| 类型 | 特征 |
|------|------|
| 安全中心 | `com.meizu.safe` |
| OS 识别 | `Build.DISPLAY` 含 `flyme` |

---

## 二、通用/Generic (GenericSteps)

### 2.1 适用范围

当设备的系统属性不匹配任何已知厂商时，使用通用处理器。适用于：
- 原生 Android / AOSP
- Pixel 系列
- Motorola / Moto
- 国外其他小品牌
- 未识别的国产 ROM

调度器日志: `"[通用授权] ★★★ 使用国外通用适配 GenericSteps ★★★"`

### 2.2 ADB 配对链路中的通用代码

**Phase 0**: 使用标准 Intent + 通用版本号查找（dh0.f55791e1 字典 + `Build.DISPLAY` 回退）

**摩托罗拉额外处理** (`C0358a0.java:280-296`):
```java
if (brand.equals("motorola") || brand.equals("moto")) {
    // 特殊的 Build Number 搜索逻辑
    // 尝试 ScrollView 内滚动查找
}
```

**Phase 1-3**: 全部走通用路径。

### 2.3 通用 yw5xud 权限自动化概况

| 维度 | 值 |
|------|-----|
| 代码量 | 3,715 行 |
| FlowType 步骤数 | **7** |
| 独有步骤 | `UNKNOWN_SOURCES`（未知来源安装权限） |

**FlowType 枚举**:
```java
BASIC_PERMISSIONS(0)
BATTERY_OPTIMIZATION(1)
NOTIFICATION_CHANNEL(2)
OVERLAY_PERMISSION(3)
UNKNOWN_SOURCES(4)            // ← 通用独有
ALL_FILES_ACCESS(5)
NOTIFICATION_PERMISSION(6)
```

`UNKNOWN_SOURCES` 是国外设备专有——国产手机通常有自己的安装管理，不需要单独处理此权限。

### 2.4 通用权限按钮 Resource ID（最全面的兼容列表）

```java
// C0364a1 构造函数
"com.android.permissioncontroller:id/permission_allow_button"
"com.android.permissioncontroller:id/permission_allow_foreground_only_button"
"com.android.permissioncontroller:id/permission_allow_one_time_button"
"com.google.android.permissioncontroller:id/permission_allow_button"
"com.google.android.packageinstaller:id/permission_allow_button"
"com.huawei.packageinstaller:id/permission_allow_button"   // 兼容华为
"com.miui.securitycenter:id/accept"                        // 兼容小米
"android:id/button1"
"android:id/button2"
```

通用处理器包含了所有厂商的权限按钮 ID 作为**回退兼容**。

### 2.5 通用检测特征

| 类型 | 特征 |
|------|------|
| 权限按钮 | `com.google.android.permissioncontroller:id/permission_allow_button` |
| 华为兼容 | `com.huawei.packageinstaller:id/permission_allow_button` |
| 小米兼容 | `com.miui.securitycenter:id/accept` |
| 日志标识 | `"★★★ 使用国外通用适配 GenericSteps ★★★"` |

---

## 三、厂商调度器分发逻辑

### 3.1 分发优先级

```
1. 预检快速路径 (Build.BRAND):
   iQOO → Vivo, Realme → OPPO, OnePlus → OPPO, 三星, 华为

2. getprop 属性检测:
   ro.miui.ui.version.name       → miui (小米)
   ro.build.version.emui         → emui (华为)
   ro.build.version.opporom      → coloros (OPPO)
   ro.vivo.os.version            → originos (Vivo)
   ro.build.version.oneui        → oneui (三星)
   Build.DISPLAY 含 "flyme"      → flyme (魅族)

3. switch 分发:
   "emui"     → executeHuaweiAuthorization → C0365a2
   "miui"     → executeMiAuthorization → C0367a4
   "flyme"    → executeMeizuAuthorization → C0366a3
   "coloros"  → executeOppoAuthorization → C0368a5
   "oneui"    → executeSamsungAuthorization → C0370a7
   "originos" → executeVivoAuthorization → C0371a8
   default    → executeGenericAuthorization → C0364a1
```

### 3.2 全部厂商文件清单

| 厂商 | 主类 | 行数 | 文档 |
|------|------|------|------|
| 华为/荣耀 | C0365a2 | 8,907 | 华为全系开发者选项自动化审计.md |
| 小米 | C0367a4 | 8,853 | 小米MIUI开发者选项自动化审计.md |
| Vivo/iQOO | C0371a8 | 11,061 | Vivo系ADB配对与开发者选项自动化审计.md |
| 三星 | C0370a7 | 1,574 | 三星ADB配对与开发者选项自动化审计.md |
| OPPO/Realme/OnePlus | C0368a5 | 11,012 | OPPO系ADB配对与开发者选项自动化审计.md |
| 魅族 | C0366a3 | 2,482 | 本文件 |
| 通用 | C0364a1 | 3,715 | 本文件 |
| 调度器 | C0372a9 | 2,672 | 本文件 / 其他厂商开发者选项自动化审计.md |
