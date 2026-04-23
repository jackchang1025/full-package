# OPPO/Realme/OnePlus ADB WiFi 配对 + 开发者选项自动化审计

> **样本**: update.apk (tiangong RAT)
> **权限自动化**: `jadx-reference/rock/service/modules/yw5xud/C0368a5.java` (OppoStepsSimplified, 11012 行, 30+ Continuation)
> **开发者选项**: `jadx-reference/rock/service/modules/setup/C0358a0.java` (OpenDevelopmentDelegate)
> **ADB 配对编排器**: `jadx-reference/rock/service/modules/setup/C0360a2.java` (SystemOptimizeManager)
> **日期**: 2026-04-21
> **审计范围**: 开发者选项开启 + ADB 无线调试配对链路中的 OPPO 系专用代码

---

## 一、品牌检测

| 品牌 | `Build.BRAND` | 检测方法 | 系统属性 |
|------|---------------|---------|---------|
| OPPO | `oppo` | `kg1.m213521c7()` | `ro.build.version.opporom` |
| Realme | `realme` | `kg1.m213521c7()` | `ro.build.version.realmeui` |
| OnePlus | `oneplus` | `kg1.m213521c7()` | `ro.oxygen.version` |
| OPLUS | `oplus` | `kg1.m213521c7()` | — |

调度器 OS 标识: `"coloros"`（三个子品牌统一路由到 OPPO 处理器）

### SubBrand 枚举 (`OppoStepsSimplified$SubBrand`)

```java
OPPO(0)      // 默认
REALME(1)    // Build.BRAND/MANUFACTURER/MODEL 含 "realme"
ONEPLUS(2)   // 含 "oneplus"
OPLUS(3)     // 含 "oplus"
```

特殊白名单机型: `RMX3823, RMX1991, PKA110, PHM110, PEDM00, PHB110`

---

## 二、OPPO 系在 ADB 配对链路中的专用代码

### 2.1 Phase 0 — 开启开发者选项

OPPO 使用标准 Intent，无特殊 ComponentName 处理。

**版本号查找**: OPPO 标记 `needsVersionInfoPage=true`，需要先进入子页面：

```java
// C0358a0.java:173
if (isOppo) { needsVersionInfoPage = true; }

// 流程:
// "关于手机" → 查找"版本信息" (dh0.f55770c0)
// → 点击进入子页面
// → 查找"ColorOS版本号" (dh0.f55802f2)
// → 连点 7 次
```

**OPPO 专有版本号标签** (`dh0.f55802f2`, 行 278):
```
"ColorOS版本号", "ColorOS版本", "ColorOS version",
"ColorOS-Version", "Version de ColorOS", ...
```

### 2.2 Phase 1/2/3 — 全部走通用路径

OPPO 系在 ADB 无线调试配对中**无专用分支**——不需要像 Vivo 那样处理总开关或 switch_bar。

| 阶段 | OPPO 处理方式 |
|------|-----------|
| Phase 1 pairInDevOption | 通用滚动查找"无线调试" |
| Phase 2 pairInWifiDebugWindow | 通用 checkbox 勾选 |
| Phase 3 pairInSecurityCenter | 通用"允许"/"下一步" |

### 2.3 OPPO 专有锁屏控件 Resource ID

```java
// C0358a0.java:1239-1242
"com.coloros.settings:id/lockPattern"       // ColorOS
"com.oplus.settings:id/lockPattern"         // OPLUS (新版)
"com.coloros.settings:id/pinEntry"
"com.coloros.settings:id/passwordEntry"
"com.oplus.settings:id/pinEntry"
"com.oplus.settings:id/passwordEntry"
```

### 2.4 OPPO 密码窗口 Activity 匹配

```java
// C0358a0.m211970c4 (行 659)
// OPPO ColorOS 专用匹配:
if (className.contains("coloros") && 
    (className.contains("lock") || className.contains("password")))
    return true;

// OPLUS 专用匹配:
if (className.contains("oplus") && 
    (className.contains("lock") || className.contains("password")))
    return true;
```

---

## 三、OPPO yw5xud 权限自动化概况

| 维度 | 值 |
|------|-----|
| 代码量 | 11,012 行（与 Vivo 并列最大） |
| FlowType 步骤数 | 9 |
| SubBrand 分发 | 4 种子品牌 |
| SharedPreferences | `oppo_simplified_v6` |
| 零加密字符串 | **是**（全部明文/标准 Intent） |

### 执行编排（9 步）

```
Step 0: 前置检查 Settings.System.canWrite()
Step 1: executeBasicPermissions (umrkmgrri 子模块)
Step 2: executeBatterySettings → SubBrand 分发
        ├─ REALME  → mRealme (4 个 SDK 版本分支)
        ├─ ONEPLUS → mOnePlus (3 个 SDK 版本分支)
        └─ OPPO/OPLUS → mOppo (四级菜单 # 分隔导航)
Step 3: executeBackgroundAndAutoStart
Step 4: executeOverlay
Step 5: executeReadAppList
Step 6: executeFileAccess (Android 11+)
Step 7: executeNotificationManagement
Step 8: executeOppoRecentTaskLock
Step 9: 返回桌面
```

### 5 个自启动管理 ComponentName

```java
("com.coloros.safecenter", ".permission.startup.StartupAppListActivity")
("com.oppo.safe",         ".permission.startup.StartupAppListActivity")
("com.oplus.safecenter",  ".permission.startup.StartupAppListActivity")
("com.coloros.safecenter", ".startupapp.view.StartupAppListActivity")
("com.oplus.safecenter",  ".startupapp.view.StartupAppListActivity")
```

---

## 四、完整时序图

```
T=0s    [前置: yw5xud 权限自动化完成]

        OpenDevelopmentDelegate 启动
        │
        ├─ Intent(DEVICE_INFO_SETTINGS) → "关于手机"
        │
        ├─ needsVersionInfoPage=true (OPPO 系)
        │   → 查找"版本信息" → 点击进入子页面
        │   → 查找"ColorOS版本号" → 连点 7 次
        │
        ├─ PIN 检测 (com.coloros.settings / com.oplus.settings)
        │
        └─ Intent(APPLICATION_DEVELOPMENT_SETTINGS) → 开发者选项

T=12s   Phase 1-3: 全部走通用路径
        → 滚动找"无线调试" → checkbox 勾选
        → 配对码 → SPAKE2+TLS → 配对完成
```

## 六、关键源码行号

| 行号 | 文件 | 功能 |
|------|------|------|
| 173 | C0358a0 | needsVersionInfoPage=true (OPPO) |
| 659 | C0358a0 | ColorOS/OPLUS 密码窗口 Activity 匹配 |
| 1239-1242 | C0358a0 | OPPO 锁屏控件 Resource ID |
| 278 | dh0 | ColorOS 版本号专用字典 |
