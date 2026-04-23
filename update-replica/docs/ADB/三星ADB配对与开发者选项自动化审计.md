# 三星 ADB WiFi 配对 + 开发者选项自动化审计

> **样本**: update.apk (tiangong RAT)
> **权限自动化**: `jadx-reference/rock/service/modules/yw5xud/C0370a7.java` (SamsungSteps, 1574 行, 12 Continuation)
> **开发者选项**: `jadx-reference/rock/service/modules/setup/C0358a0.java` (OpenDevelopmentDelegate)
> **ADB 配对编排器**: `jadx-reference/rock/service/modules/setup/C0360a2.java` (SystemOptimizeManager)
> **日期**: 2026-04-21
> **审计范围**: 开发者选项开启 + ADB 无线调试配对链路中的三星专用代码

---

## 一、品牌检测

| 品牌 | `Build.BRAND` | 检测方法 | 系统属性 |
|------|---------------|---------|---------|
| Samsung | `samsung` | `Build.BRAND` 直接比较 | `ro.build.version.oneui` |

调度器 OS 标识: `"oneui"`

---

## 二、三星在 ADB 配对链路中的专用代码

三星是 ADB 配对链路中**最精简**的厂商——仅在 Phase 0 有一处专用分支，Phase 1-3 全部走通用路径。

### 2.1 Phase 0 — 跳过"版本信息"子页 (`C0358a0.java:178`)

```java
// P() 方法内
if (!Build.BRAND.equals("samsung")) {
    needsVersionInfoPage = false;
}
```

三星的"关于手机"页面**直接显示版本号**，不需要先进入"软件信息"子页面。这与 Vivo/OPPO（需要进入子页面）形成对比。

### 2.2 Phase 0 — 开发者选项打开

三星使用标准 Intent（`ACTION_APPLICATION_DEVELOPMENT_SETTINGS`），无特殊处理。

### 2.3 Phase 1/2/3 — 全部走通用路径

| 阶段 | 三星处理方式 |
|------|-----------|
| Phase 1 pairInDevOption | 通用滚动查找"无线调试" |
| Phase 2 pairInWifiDebugWindow | 通用 checkbox 勾选（非 Vivo switch_bar） |
| Phase 3 pairInSecurityCenter | 通用"允许"/"下一步"按钮点击 |

---

## 三、三星专有锁屏控件

三星使用独立的生物识别包处理锁屏验证（PIN 输入后开启开发者选项时触发）：

```java
// C0358a0.java:1239
"com.samsung.android.biometrics.app.setting:id/lockPattern"
"com.samsung.android.biometrics.app.setting:id/biometric_lockPattern"
```

其他厂商使用 `com.android.settings:id/lockPattern`。

---

## 四、三星权限按钮 Resource ID

三星有自己的 `permissioncontroller` 包，RAT 在权限自动化中需要匹配：

```java
// C0370a7.java 构造函数 (行 50-58)
"com.samsung.android.permissioncontroller:id/permission_allow_button"
"com.samsung.android.permissioncontroller:id/permission_allow_foreground_only_button"
"com.samsung.android.permissioncontroller:id/permission_allow_one_time_button"
// + AOSP 标准按钮 ID 作为回退
```

---

## 五、三星 yw5xud 权限自动化概况

| 维度 | 值 |
|------|-----|
| 代码量 | 1,574 行（所有厂商**最精简**） |
| FlowType 步骤数 | 6 |
| 独有步骤 | `WRITE_SETTINGS`（系统设置写入权限） |

### FlowType 枚举

```java
BASIC_PERMISSIONS(0)
BATTERY_OPTIMIZATION(1)
NOTIFICATION_CHANNEL(2)
OVERLAY_PERMISSION(3)
WRITE_SETTINGS(4)           // ← 三星独有
ALL_FILES_ACCESS(5)
```

---

## 六、完整时序图

```
T=0s    [前置: yw5xud 权限自动化完成]

        OpenDevelopmentDelegate 启动
        │
        ├─ Intent(DEVICE_INFO_SETTINGS) → "关于手机"
        │
        ├─ needsVersionInfoPage=false
        │   → 直接在"关于手机"页面查找版本号
        │   → dh0 字典匹配 / Build.DISPLAY 回退
        │
        ├─ Y(): 连续点击 7 次
        ├─ PIN 检测 (com.samsung.android.biometrics.app.setting)
        │
        └─ Intent(APPLICATION_DEVELOPMENT_SETTINGS) → 开发者选项

T=12s   Phase 1-3: 全部走通用路径
        → 滚动找"无线调试" → 勾选 → 配对码 → SPAKE2+TLS
        → 配对完成
```

## 八、关键源码行号

| 行号 | 文件 | 功能 |
|------|------|------|
| 178 | C0358a0 | 三星品牌检查 needsVersionInfoPage=false |
| 1239 | C0358a0 | 三星锁屏控件 Resource ID |
| 50-58 | C0370a7 | 三星权限按钮 Resource ID 列表 |
