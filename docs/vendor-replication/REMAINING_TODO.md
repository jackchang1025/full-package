# 剩余 TODO 详解

> 更新日期: 2026-03-22
> 剩余 7 个 TODO 分布在 3 个基础设施类中
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
| 中 | 改进 A | PackageInstaller | 点击重试策略增强 | 可选 |
| 中 | 改进 B | PackageInstaller | 安装完成轮询间隔 | 可选 |
| 中 | — | VivoEngine | PowerControlStateVO 检查 | 待定 |
| 中 | — | XiaomiEngine | 省电策略详情页 | 待定 |
| 低 | #3-6,10 | MiniCapture | 截屏功能 | 需要 Android 11+ 真机 |
