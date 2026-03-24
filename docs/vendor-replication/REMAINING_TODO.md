# 剩余 TODO 详解

> 更新日期: 2026-03-24
> 剩余 7 个 TODO + 1 个新发现 (WebView URL scheme 拦截) + 设备静音策略 TODO
> 这些 TODO 不影响厂商保活引擎的核心功能

---

## 已完成 (本次更新)

### ~~TODO #1: t0() — PowerControlStateVO 上报~~ ✅ 已完成

`AutoEngine.java:625` — 现在会构建 `PowerControlStateVO`，填充 `packageName` 和 `deviceId`，通过可覆写的 `reportPowerControlState()` 钩子调用 `SharedUtils.savePowerControlState()`。子类可覆盖此方法以自定义上报逻辑。

### ~~TODO #2: checkBatteryOptimizationDialog() — 异步点击"允许"~~ ✅ 已完成

`AutoEngine.java:1112` — 已实现完整的异步处理：检测到电池优化对话框后，通过 `scheduler.execute()` 异步调用 `dismissBatteryOptimizationDialog()`，查找 `android:id/button1` 并点击，最后从 `stateQueue` 移除 `keepInBatteryUnRestricted`。

### ~~TODO #9: 检查备份应用安装状态~~ ✅ 已完成

`PackageInstallerDelegate.java:452` — `isPackageInstalled()` 已使用 `PackageManager.getPackageInfo()` 实现真实检查。

---

## 1. MiniCapture.java (5 个 TODO)

`MiniCapture` 是无障碍截屏模块 (Android 11+ 的 `takeScreenshot` API)。vendor 对应 `o/r.java` (69行)。

### TODO #3: captureTask 类型确认

**位置**: `MiniCapture.java:30`

```java
// TODO: VENDOR_VERIFY - 需要确认 thread.k 的具体实现
public final Object captureTask;
```

**Vendor 功能**: `com.guard.wallet.thread.k` 是一个截屏任务执行器，内部持有 `MediaProjection` 或 `AccessibilityService.takeScreenshot` 的引用。它管理截图缓冲区和图片编码。

**影响范围**: 截屏功能完全依赖此对象。当前 `captureTask = null`，截屏不工作。

---

### TODO #4: captureTask 构造

**位置**: `MiniCapture.java:38`

```java
// TODO: VENDOR_VERIFY - captureTask 的具体类型
this.captureTask = null;
```

**Vendor 功能**: vendor 构造 `new com.guard.wallet.thread.k(true)`，参数 `true` 表示使用 AccessibilityService 截屏模式（而非 MediaProjection）。

---

### TODO #5: capture() 反编译失败

**位置**: `MiniCapture.java:55`

```java
// TODO: VENDOR_VERIFY - 方法反编译失败，以下为 smali 逆向重建
```

**Vendor 功能**: 核心截屏方法，判断:
1. SDK < 30: 使用 MediaProjection 截屏
2. SDK >= 30: 使用 `AccessibilityService.takeScreenshot()` (Android 11+)
3. 黑屏限流: 屏幕关闭时 30 秒内只截一次
4. 提交 captureTask 到线程池执行

**影响范围**: 截屏是远控的核心功能之一。当前骨架实现可以编译但不执行实际截屏。

---

### TODO #6: 黑屏检测逻辑

**位置**: `MiniCapture.java:65`

```java
// TODO: VENDOR_VERIFY - 黑屏检测逻辑
boolean screenOn = true; // placeholder
```

**Vendor 功能**: `com.guard.wallet.utils.e.j()` — 检查 PowerManager 的 `isInteractive()` 判断屏幕是否亮着。黑屏时截图为纯黑画面，vendor 做了 30 秒限流避免浪费资源。

**实现方案**: 使用 `PowerManager.isInteractive()` 替代 placeholder。

---

### TODO #10: captureTask 状态检查

**位置**: `MiniCapture.java:78`

```java
// TODO: VENDOR_VERIFY - captureTask 状态检查逻辑
```

**Vendor 功能**: 检查 captureTask 是否处于忙碌状态，防止并发截屏请求。

---

## 2. PackageInstallerDelegate.java (0 个 TODO，2 个可改进项)

`PackageInstallerDelegate` 处理 APK 安装确认弹窗，自动点击"安装"/"允许"按钮。
代码中已无 TODO 标记，但有两个已知可改进项:

### 改进项 A: 点击重试策略增强

**位置**: `PackageInstallerDelegate.java:302-318`

当前实现已包含: 直接点击 → 父节点点击 → `R()` 坐标点击。
**可改进**: vendor 还有 500ms 间隔重试循环 (最多 3 次)，当前仅单次尝试。

### 改进项 B: waitForInstallAndFinish() 轮询间隔

**位置**: `PackageInstallerDelegate.java:436`

当前 `T0(2)` = 400ms，vendor 实际间隔为 2 秒 (`Thread.sleep(2000)`)。
**可改进**: 将 `T0(2)` 改为 `T0(10)` (2000ms) 以匹配 vendor 轮询间隔。

---

## 3. 厂商引擎残余 TODO (2 个)

### VivoEngine.java:724

```java
// TODO: 检查 PowerControlStateVO
```

功能: `isAppCompleted()` 中应检查已保存的 PowerControlStateVO 判断是否已完成保活设置。当前返回 `false`。

### XiaomiEngine.java:363

```java
// TODO: VENDOR_VERIFY — q.k0() 反编译失败，根据上下文重建
```

功能: 省电策略详情页处理 (`handlePowerDetailPage`)，vendor 原始方法有 299 条 smali 指令，反编译失败。当前为骨架实现。

---

## 优先级排序

| 优先级 | TODO # | 文件 | 功能 | 状态 |
|--------|--------|------|------|------|
| ~~高~~ | ~~#1~~ | ~~AutoEngine~~ | ~~状态上报~~ | ✅ 已完成 |
| ~~高~~ | ~~#2~~ | ~~AutoEngine~~ | ~~电池优化对话框点击~~ | ✅ 已完成 |
| ~~高~~ | ~~#9~~ | ~~PackageInstaller~~ | ~~备份应用安装检查~~ | ✅ 已完成 |
| **高** | **新** | **AppWebViewClient** | **shouldOverrideUrlLoading 拦截 js:// scheme** | **未实现** |
| **中** | **H1** | **DefaultMuteStrategy** | **华为 WRITE_SETTINGS 自动授予** | **待定** |
| **中** | **H2** | **DefaultMuteStrategy** | **华为 setStreamVolume DND 验证** | **待验证** |
| **中** | **X1** | **XiaomiMuteStrategy** | **小米触感 keys 澎湃 OS 验证** | **待验证** |
| 中 | 改进 A | PackageInstaller | 点击重试策略增强 | 可选 |
| 中 | 改进 B | PackageInstaller | 安装完成轮询间隔 | 可选 |
| 中 | — | VivoEngine | PowerControlStateVO 检查 | 待定 |
| 中 | — | XiaomiEngine | 省电策略详情页 | 待定 |
| 低 | H3 | DefaultMuteStrategy | 华为进程冻结恢复可靠性 | 待验证 |
| 低 | X2 | XiaomiMuteStrategy | 小米多版本震动 keys 兼容性 | 待验证 |
| 低 | G1 | DeviceMuteStrategy | DND 完整静音模式 | 设计决策 |
| 低 | G2 | DefaultMuteStrategy | 固定音量设备 shell 兜底 | 待验证 |
| 低 | #3-6,10 | MiniCapture | 截屏功能 | 需要 Android 11+ 真机 |

---

## 4. AppWebViewClient.java — shouldOverrideUrlLoading 未实现 (新发现 2026-03-23)

**位置**: `AppWebViewClient.java:89-92`

```java
// 当前: 空实现，未拦截任何自定义 scheme
@Override
public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
    return super.shouldOverrideUrlLoading(view, request);
}
```

**问题**: 引导页 `guide.accessibility.rathat.org` 使用 `js://` 自定义 URL scheme 与原生代码交互。
点击页面按钮后 WebView 尝试加载 `js://startAccessibility`，因无拦截导致 `ERR_UNKNOWN_URL_SCHEME` 错误。

**Vendor 实现**: `e0/d.java` 的 `shouldOverrideUrlLoading` (520条指令) 拦截 `js://` scheme，
转发到本地 HTTP 服务器 `server/b.java` 的 `X1()` 方法处理 200+ 路由。

**引导页使用的 scheme**:

| URL Scheme | 原生操作 |
|------------|---------|
| `js://startAccessibility` | 打开无障碍设置 |
| `js://startAppDetailSetting` | 打开应用详情设置 |
| `js://startSettings` | 打开系统设置 |
| `js://ignoreBatteryOptimization` | 申请电池优化白名单 |
| `js://requestPermission` | 请求运行时权限 |

**详细审计**: 见 `audits/AUDIT_WEBVIEW_GUIDE_URL_SCHEME.md`

---

## 5. 设备静音策略 — 遮罩期间禁用横屏/声音/震动 (新增 2026-03-24)

**背景**: Vendor APK 的 `helper/g.java` (BlockViewHelper) **不做任何旋转/震动/音量控制**，
仅控制亮度 (`screen_brightness`)。Replica 新增了策略模式实现遮罩期间的设备状态管控。

**新增文件**:
- `helper/DeviceMuteStrategy.java` — 接口
- `helper/DefaultMuteStrategy.java` — 通用实现 (华为 + 其他)
- `helper/XiaomiMuteStrategy.java` — 小米实现 (组合 Default + 小米特有 keys)

**修改文件**:
- `helper/BlockViewHelper.java` — 删除 inline 逻辑，委托给 strategy
- `auto/engine/PermissionAutoGrantEngine.java` — 华为鸿蒙渲染等待时间增加

### 双层 Settings 写入 (对齐 vendor `utils/k.c()`)

Vendor 的亮度控制使用双层策略，replica 的静音策略对齐此模式:

| 层级 | 方法 | 说明 |
|------|------|------|
| 第 1 层 | `Settings.System.canWrite()` → `putInt()` → read-back | 标准 API，需 WRITE_SETTINGS 权限 |
| 第 2 层 | `Runtime.exec("settings put system <key> <value>")` | shell 兜底，不依赖 WRITE_SETTINGS |

### 华为 TODO

| # | 描述 | 状态 | 优先级 |
|---|------|------|--------|
| H1 | WRITE_SETTINGS 权限未自动授予，需在保活引擎中通过无障碍自动开启 `ACTION_MANAGE_WRITE_SETTINGS` | 待定 | 中 |
| H2 | `setStreamVolume()` 可能触发 DND `SecurityException`，需验证 shell 兜底对音频流是否有效 | 待验证 | 中 |
| H3 | 华为 Pged-Freezer 可能在 `restoreAll()` 执行中冻结进程，需验证恢复可靠性 | 待验证 | 低 |

### 小米 TODO

| # | 描述 | 状态 | 优先级 |
|---|------|------|--------|
| X1 | 小米 `haptic_feedback_intensity` / `touch_vibration_intensity` 在澎湃 OS 上是否仍有效，需真机验证 | 待验证 | 中 |
| X2 | 小米特有的 8 个震动 keys 可能因系统版本不同而变化，需在不同 MIUI/澎湃 OS 版本上测试 | 待验证 | 低 |

### 通用 TODO

| # | 描述 | 状态 | 优先级 |
|---|------|------|--------|
| G1 | `setRingerMode` 已移除 (SILENT 需 DND 权限，VIBRATE 语义反了)，如需完整静音模式需申请 `ACCESS_NOTIFICATION_POLICY` | 设计决策 | 低 |
| G2 | `isVolumeFixed()` 为 true 的设备 (如部分平板) 完全跳过音量控制，需确认是否需要 shell 兜底 | 待验证 | 低 |

### 权限自动授予渲染等待

| 参数 | 修改前 | 修改后 | 说明 |
|------|--------|--------|------|
| 初始等待 | 300ms | 500ms | 华为鸿蒙渲染较慢 |
| 重试次数 | 5 次 | 10 次 | |
| 重试间隔 | 300ms | 500ms | |
| 总等待上限 | 1.8 秒 | 5.5 秒 | 覆盖华为鸿蒙场景 |
