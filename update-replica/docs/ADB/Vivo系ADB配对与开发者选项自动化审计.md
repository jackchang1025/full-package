# Vivo/iQOO ADB WiFi 配对 + 开发者选项自动化审计

> **样本**: update.apk (tiangong RAT)
> **权限自动化**: `jadx-reference/rock/service/modules/yw5xud/C0371a8.java` (VivoSteps, 11061 行, 44 Continuation)
> **ADB 配对编排器**: `jadx-reference/rock/service/modules/setup/C0360a2.java` (SystemOptimizeManager, 5666 行)
> **开发者选项**: `jadx-reference/rock/service/modules/setup/C0358a0.java` (OpenDevelopmentDelegate, 1401 行)
> **日期**: 2026-04-21
> **审计范围**: 开发者选项开启 + ADB 无线调试配对链路中的 Vivo/iQOO 专用代码

---

## 一、品牌检测

| 品牌 | `Build.BRAND` | 检测方法 | 系统属性 |
|------|---------------|---------|---------|
| Vivo | `vivo` | `kg1.m213522c8()` | `ro.vivo.os.version` / `ro.vivo.product.version` |
| iQOO | `iqoo` | `kg1.m213522c8()` | 同上 |

调度器 OS 标识: `"originos"`

---

## 二、Vivo/iQOO 在 ADB 配对链路中的 3 个专用分支

Vivo 是 ADB 配对链路中**专用代码最多**的厂商，在 Phase 0/1/2 都有独立分支。

### 2.1 Phase 0 — 开启开发者选项

Vivo 使用标准 Intent（`ACTION_APPLICATION_DEVELOPMENT_SETTINGS`），无需华为那样的 ComponentName 链。

**版本号查找**: Vivo 标记 `needsVersionInfoPage=true`，需要先进入"版本信息"/"软件信息"子页面才能看到版本号：

```java
// C0358a0.java:171-172
if (isVivo) { needsVersionInfoPage = true; }

// 流程:
// "关于手机" → 查找"版本信息"/"软件信息"文本 (dh0.f55770c0 / dh0.f55799e9)
// → 点击进入子页面 → 在子页面查找版本号 → 连点 7 次
```

### 2.2 Phase 1 — J0() 开发者选项总开关 (`C0360a2.java:488-496, 1596-1715`)

**Vivo 独有问题**: 开发者选项页面顶部有一个总开关 checkbox，默认关闭。不先打开这个开关，下面的所有子项（包括"无线调试"）都不可操作。

```java
// C0360a2.java:488-496 (G() 方法内)
if (isVivo) {
    if (m212027a1(scrollView)) {  // J0() 检查并开启总开关
        // 成功
    } else {
        // 失败，等 1 秒重试
        sleep(1000ms);
    }
}
```

**J0() 详细流程** (`m212027a1`, 行 1596-1715):

```
Step 1: findAccessibilityNodeInfosByViewId("android:id/checkbox")
        过滤: className 包含 "Switch" 且 isEnabled()

Step 2: 未找到 → 滚动到页面顶部 → 刷新 → 重新查找

Step 3: 检查 isChecked()
        ├─ true → 已开启，直接返回
        └─ false → performAction(CLICK)

Step 4: 等待"允许开发设置"弹窗 (dh0.f55798e8)
        轮询最多 10 次 × 200ms = 2s

Step 5: 弹窗出现 → 查找"确定"按钮 (dh0.f55752a2 + dh0.f55809f9)
        排除"取消"按钮 (dh0.f55753a3 + dh0.f55810g0)
        → performAction(CLICK)
        → 等待 3 秒让页面稳定

Step 6: 如果"确定"按钮点击失败
        ├─ 尝试 findClickableParent() 点击父节点
        └─ Vivo/iQOO 专用回退: findByViewId("android:id/button1")
```

### 2.3 Phase 2 — switch_bar 直接点击 (`C0360a2.java:719-727`)

进入无线调试详情页后，Vivo/iQOO 使用**专有的 switch_bar 控件**启用无线调试，而非通用的 checkbox：

```java
if (brand.equals("vivo") || brand.equals("iqoo")) {
    List<AccessibilityNodeInfo> switchBar = 
        rootWindow.findAccessibilityNodeInfosByViewId(
            "com.android.settings:id/switch_bar");
    if (switchBar != null && !switchBar.isEmpty() 
        && switchBar.get(0).isClickable()) {
        switchBar.get(0).performAction(ACTION_CLICK);
        sleep(2000ms);
        m212068h2();  // 处理"允许在此网络上无线调试"确认弹窗
    }
} else {
    // 其他厂商: 通用 checkbox 勾选路径
    m212033a7(rootWindow);
}
```

**关键差异**: 其他所有厂商都走 `m212033a7()` 通用 checkbox 勾选，只有 Vivo/iQOO 走 `switch_bar` 路径。

---

## 三、Vivo yw5xud 权限自动化概况

| 维度 | 值 |
|------|-----|
| FlowType 步骤数 | **11**（所有厂商最多） |
| 独有步骤 | `BACKGROUND_POPUP`("后台弹窗"), `BATTERY_BACKGROUND_POWER`("后台高耗电管理") |

### 3.1 FlowType 枚举

```java
BASIC_PERMISSIONS("基础权限")
PERMISSION_MANAGER("权限管理")
AUTO_START("自启动")
NOTIFICATION("通知管理")
BACKGROUND_POPUP("后台弹窗")                 // ← Vivo 独有
OVERLAY_PERMISSION("悬浮窗权限")
ALL_FILES_ACCESS("所有文件访问")
BATTERY_BACKGROUND_POWER("后台高耗电管理")    // ← Vivo 独有
BATTERY_POWER_SAVING("省电模式")
BATTERY_OPTIMIZATION("电池优化")
CLEAR_RECENT_TASKS("清除最近任务")
```

---

## 四、Vivo 专有标识与 IOC

### 4.1 Resource ID

| Resource ID | 用途 |
|-------------|------|
| `com.android.settings:id/switch_bar` | 无线调试开关（ADB 配对关键控件） |
| `com.vivo.abe:id/app_name` | 应用名称 |
| `com.vivo.abe:id/vos_button_opt` | 电池优化按钮 |
| `com.vivo.abe:id/vos_button_no_opt` | 不优化按钮 |
| `com.vivo.abe:id/forbid_btn` | 禁止按钮 |
| `com.iqoo.powersaving:id/battery_mode_view` | iQOO 电池模式 |
| `com.vivo.launcher:id/lock_icon` | 启动器锁图标 |
| `com.bbk.launcher2:id/lock_icon` | BBK 启动器锁图标 |

### 4.2 Activity / 包名

```
com.vivo.permissionmanager.activity.SoftPermissionDetailActivity
com.vivo.abe              // Vivo ABE 框架
com.iqoo.powersaving      // iQOO 电池管理
com.vivo.launcher         // Vivo 启动器
com.bbk.launcher2         // BBK 启动器（Vivo 母公司）
```

### 4.3 最近任务锁定验证

`VivoSteps$VivoLockVerifyResult` 枚举:
```java
LOCKED      // 应用已锁定在最近任务
NOT_LOCKED  // 未锁定
UNKNOWN     // 无法判断
```

通过 `lock_icon` / `iv_lock` / `task_lock` 控件状态判断。

---

## 五、完整时序图

```
T=0s    [前置: yw5xud 权限自动化完成 → onComplete]

        OpenDevelopmentDelegate 启动
        │
        ├─ Intent(DEVICE_INFO_SETTINGS) → "关于手机"
        │
        ├─ needsVersionInfoPage=true
        │   → 查找"版本信息"/"软件信息" → 点击进入子页面
        │   → 在子页面查找版本号
        │
        ├─ Y(): 连续点击 7 次 (150ms 间隔)
        ├─ PIN 检测 (5s) → 等待/自动输入 (30s)
        │
        └─ Intent(APPLICATION_DEVELOPMENT_SETTINGS) → 开发者选项

T=12s   Phase 1: G() pairInDevOption
        │
        ├─ ★ Vivo 专用: J0() 开启开发者选项总开关
        │   android:id/checkbox → isChecked? → CLICK
        │   等待"允许开发设置"弹窗 → 点击"确定"
        │
        ├─ 滚动找"无线调试" (最多 28 次)
        └─ 点击进入

T=18s   Phase 2: B4() pairInWifiDebugWindow
        │
        ├─ ★ Vivo 专用: switch_bar 直接点击
        │   com.android.settings:id/switch_bar → CLICK
        │   处理"允许无线调试"确认弹窗
        │
        ├─ 查找"使用配对码配对设备" → 点击
        ├─ 等待配对码弹窗 (10s)
        ├─ K8() OCR 提取配对码 + 端口
        └─ E2() SPAKE2+TLS 1.3 配对

T=35s   Phase 3: 安全弹窗 (通用路径)
        → 配对完成
```

---

## 七、关键源码行号

| 行号 | 文件 | 功能 |
|------|------|------|
| 488-496 | C0360a2 | Vivo J0() 分支入口 |
| 1596-1715 | C0360a2 | J0() 完整实现（总开关检测+点击+弹窗处理） |
| 719-727 | C0360a2 | Vivo/iQOO switch_bar 无线调试开关 |
| 171 | C0358a0 | needsVersionInfoPage=true (Vivo) |
| 103-117 | C0371a8 | 构造函数（专有标识初始化） |
