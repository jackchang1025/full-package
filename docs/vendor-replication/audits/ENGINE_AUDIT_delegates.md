# ENGINE AUDIT: 通用 Delegate 引擎 (o/t, o/x, o/g0, o/k, o/i, o/h, o/l, o/o)

> 审计日期: 2026-03-21
> 覆盖 8 个通用 Delegate 文件 (extends o.e)

## 总览

| Vendor | 行数 | Replica | ListenWindow 方法 | 功能 |
|--------|------|---------|-------------------|------|
| `o/t.java` | 677 | `ScreenUnlockDelegate.java` | `X()` | 屏幕解锁 (PIN/密码/图案/版本号点击) |
| `o/x.java` | 531 | `PermissionGrantDelegate.java` | `N()` | 运行时权限自动授予 + APK 安装确认 |
| `o/g0.java` | 432 | `DeviceCredentialDelegate.java` | `T()` | 设备凭证验证 (锁屏密码采集) |
| `o/k.java` | 382 | `EnableSecureDelegate.java` | `J()` | 安全设置启用 (开发者选项中的安全开关) |
| `o/i.java` | 266 | `ConfirmLockDelegate.java` | `L()` | 锁屏密码确认 (图案/PIN/密码) |
| `o/h.java` | 196 | *(DeviceCredentialDelegate 部分)* | `M()` | 设备凭证验证2 (systemui 密码输入) |
| `o/l.java` | 71 | `PairAccessibilityDelegate.java` | `J()` | 配对无障碍服务 |
| `o/o.java` | 55 | `MediaProjectionDelegate.java` | `H()` | 媒体投影权限 (截屏授权) |

---

## 1. o/t.java — ScreenUnlockDelegate (677行)

### ListenWindow 列表 — X()

| # | packageName | className | 说明 |
|---|-------------|-----------|------|
| 0 | com.android.settings | ...DeviceInfoSettingsActivity | 设备信息 (点击版本号) |
| 1 | com.android.settings | ...MyDeviceInfoActivity | 我的设备信息 |
| 2 | com.android.settings | android.widget.FrameLayout | 设置 FrameLayout |
| 3 | com.android.settings | android.app.AlertDialog | 设置对话框 |
| 4-5 | *(引用 i.L())* | ConfirmLockPassword 等 | 锁屏确认页 |
| 6-7 | *(引用 a0.Y(), a0.i0())* | AlertDialog | 设置对话框 |

### CombineFilter 配置 Key

| 方法 | Key | 说明 |
|------|-----|------|
| `L()` | *(无)* | className=EditText |
| `V()` | COMMON_BUILD_NUMBER_TEXT | 版本号 |
| `Y()` | 10 个 COMMON_*_TEXT | 多厂商版本号文本 (OR 匹配) |

### 核心逻辑

```
1. 导航到设备信息页
2. 查找"版本号"文本 → 连续点击 7 次 → 开启开发者选项
3. 如果弹出锁屏确认 → 委托给 ConfirmLockDelegate
4. 完成后回调
```

### 字段

| 字段 | 类型 | 说明 |
|------|------|------|
| `f695n` | `ScheduledExecutorService` | 定时任务 |
| `f696o` | `AtomicReference` | 当前阶段 |
| `f697p` | `ReentrantLock` | 线程锁 |

---

## 2. o/x.java — PermissionGrantDelegate (531行)

### ListenWindow 列表 — N()

| # | packageName | className | 说明 |
|---|-------------|-----------|------|
| 0 | com.android.packageinstaller | ...PackageInstallerActivity | APK 安装确认 |
| 1 | com.miui.securitycenter | ...AdbInstallActivity | MIUI ADB 安装 |
| 2 | com.miui.securitycenter | miuix.appcompat.app.AlertDialog | MIUI 对话框 |
| 3 | com.oplus.appdetail | ...InstallGuideActivity | OPPO 安装引导 |
| 4 | com.oplus.appdetail | ...InstallFinishActivity | OPPO 安装完成 |
| 5+ | 多个厂商安装器 | *(各厂商)* | 三星/vivo/华为等 |

### M() — 权限授予按钮 (OR, 9 个匹配)

```java
CombineFiltersWithOr:
  Filter1: Button + id=...permission_allow_button
  Filter2: Button + id=...permission_allow_foreground_only_button
  Filter3: Button + text=允许 (COMMON_ALLOW_TEXT)
  Filter4: Button + text=始终允许 (COMMON_ALWAYS_ALLOW_TEXT)
  Filter5: Button + text=仅使用时允许
  Filter6: Button + id=...btn_allow
  Filter7: Button + id=...btn_allow_always
  Filter8: CheckBox + id=...permission_allow_checkbox
  Filter9: RadioButton + text=允许
```

### 核心逻辑

```
1. 监听权限弹窗 (PackageInstallerActivity 等)
2. 查找"允许"/"安装"按钮 → 自动点击
3. 支持 AOSP/MIUI/ColorOS/OneUI/OriginOS 等多厂商
4. APK 安装确认也自动点击"安装"
```

### 字段

| 字段 | 类型 | 说明 |
|------|------|------|
| `f705n` | `ScheduledExecutorService` | 定时任务 |
| `f706o` | `ConcurrentLinkedQueue` | 状态队列 |
| `f707p` | `ReentrantLock` | 线程锁 |

---

## 3. o/g0.java — DeviceCredentialDelegate (432行)

### ListenWindow 列表 — T()

| # | packageName | className | 说明 |
|---|-------------|-----------|------|
| 0 | com.android.systemui | ...ConfirmDeviceCredentialActivity | 设备凭证确认 |
| 1 | com.android.systemui | *(null=任意)* | systemui 通用 |

### CombineFilter

| 方法 | 说明 |
|------|------|
| `H()` | className=EditText (密码输入框) |
| `I()` | className=Button + id 包含 "key" (数字键盘) |
| `J()` | className=TextView + id 包含 "key" (数字键盘文本) |
| `L()` | className=Button + text=确认 |
| `W(str)` | className=TextView + text.contains(str) |
| `Y()` | className=ImageView + id 包含 "key" (图案解锁) |
| `Z()` | className=View + id 包含 "key" (通用键盘) |
| `U()` | OR: EditText / Button+key / TextView+key / ImageView+key / View+key |

### 核心逻辑

```
1. 监听 ConfirmDeviceCredentialActivity
2. 检测密码类型 (PIN/密码/图案)
3. 如果有已保存的密码 → 自动输入
4. PIN: 逐个点击数字键 → 点击确认
5. 密码: 输入到 EditText → 点击确认
6. 图案: 使用手势绘制
7. 输入后发送凭证到服务端
```

### 字段

| 字段 | 类型 | 说明 |
|------|------|------|
| `f641n` | `ThreadPoolExecutor` | 线程池 (非 ScheduledExecutor) |
| `f642o` | `ConcurrentLinkedQueue` | 已处理的密码类型 |
| `f643p` | `ConcurrentLinkedQueue` | 待处理队列 |
| `q` | `ConcurrentLinkedQueue` | 凭证队列 |
| `f644r` | `AtomicReference` | 当前密码类型 |
| `f645s` | `AtomicReference` | 已保存的密码 |

---

## 4. o/k.java — EnableSecureDelegate (382行)

### ListenWindow 列表 — J()

| # | packageName | className | 说明 |
|---|-------------|-----------|------|
| 0 | com.android.settings | ...DevelopmentSettingsDashboardActivity | 开发者选项 |
| 1 | com.android.settings | ...DevelopmentSettingsActivity | 开发者选项 (旧版) |
| 2 | com.android.settings | ...SubSettings | 子设置页 |
| 3 | *(引用 a0.M0())* | ...ManageAppExternalSourcesActivity | 安装未知应用 |
| 4 | *(引用 a0.I0())* | ...ManageAppExternalSourcesActivity | 安装未知应用 (变体) |

### 核心逻辑

```
1. 在开发者选项页面
2. 查找安全相关开关 (如 "USB 调试安全设置")
3. 操作 Switch/CheckBox
4. 导航到安装未知应用页面
5. 开启允许安装
```

---

## 5. o/i.java — ConfirmLockDelegate (266行)

### ListenWindow 列表 — L()

| # | packageName | className | 说明 |
|---|-------------|-----------|------|
| 0 | com.android.settings | ...ConfirmLockPassword | 确认锁屏密码 |
| 1 | com.android.settings | ...ConfirmLockPattern | 确认锁屏图案 |
| 2 | com.android.settings | ...ConfirmLockPassword.InternalActivity | 内部确认 |
| 3 | com.android.settings | ...ConfirmLockPattern.InternalActivity | 内部确认 |
| 4 | com.android.settings | android.app.AlertDialog | 对话框 |

### 核心逻辑

```
1. 监听 ConfirmLockPassword/ConfirmLockPattern
2. 如果有已保存的密码/图案 → 自动输入
3. PIN: 点击数字键盘
4. 密码: 输入到 EditText
5. 图案: dispatchGesture 绘制
6. 验证成功后回调
```

### 字段

| 字段 | 类型 | 说明 |
|------|------|------|
| `f647n` | `String` | 已保存的密码/PIN |
| `f648o` | `ConcurrentLinkedQueue` | 已处理的操作 |

---

## 6. o/h.java — DeviceCredentialDelegate2 (196行)

### ListenWindow 列表 — M()

| # | packageName | className | 说明 |
|---|-------------|-----------|------|
| 0 | com.android.systemui | *(多个)* | systemui 密码输入 |
| 1 | com.android.settings | *(多个)* | 设置密码输入 |

### EventSubscribe (4 个)

| 方法 | eventTypes | 说明 |
|------|-----------|------|
| `N(pkg)` | 32, 16384 | 窗口内容变化 + 滚动 |
| `O(pkg)` | 32, 16384, 32768 | + 公告 |
| `P(pkg)` | 16 | 文本变化 |
| `Q(pkg)` | 1 | 点击 |

### 特殊: 这是唯一使用 EventSubscribe 的 Delegate

`h.java` 的 `M()` 方法返回的 ListenWindow 包含完整的 EventSubscribe，用于监听 systemui 中的密码输入事件。这与厂商引擎的硬编码方式不同，更接近 `listenWindows.json` 的动态规则模式。

---

## 7. o/l.java — PairAccessibilityDelegate (71行, 最简单的 Delegate)

### ListenWindow 列表 — J()

| # | packageName | className | 说明 |
|---|-------------|-----------|------|
| 0 | com.android.settings | ...AccessibilitySettingsForSetupWizardActivity | 设置向导无障碍 |
| 1 | com.android.settings | ...AccessibilitySettings | 无障碍设置 |

### 核心逻辑

```
1. 监听无障碍设置页面
2. 查找目标无障碍服务 → 点击进入
3. 开启服务开关
```

---

## 8. o/o.java — MediaProjectionDelegate (55行, 最小的 Delegate)

### ListenWindow — H()

```java
ListenWindow("com.android.systemui",
    "com.android.systemui.media.MediaProjectionPermissionActivity")
eventTypes: [32, 16384]
```

### 核心逻辑

```
1. 监听 MediaProjectionPermissionActivity (截屏授权弹窗)
2. 查找"立即开始"/"允许"按钮 → 自动点击
```

---

## 9. Runnable 异步任务映射

| Vendor Runnable | 调用者 | 说明 |
|-----------------|--------|------|
| `o/h0.java` (307L) | `o/t.java` (ScreenUnlock) | 屏幕解锁操作 (版本号点击/密码输入) |
| `o/u.java` (169L) | `o/x.java` (PermissionGrant) | 权限授予操作 (点击允许/安装) |
| `o/m.java` (32L) | `o/g0.java` (DeviceCredential) | 凭证验证操作 |
| `o/p.java` (80L) | `o/k.java` (EnableSecure) | 安全设置操作 |
| `o/s.java` (107L) | `o/i.java` (ConfirmLock) | 锁屏确认操作 (PIN/密码/图案输入) |
| `o/w.java` (33L) | `o/l.java` (PairAccessibility) | 配对操作 |
| `o/z.java` (42L) | `o/o.java` (MediaProjection) | 媒体投影操作 |
| `o/d0.java` (261L) | `o/a0.java` (OpenDevelopment) | 开发者选项操作 |
| `o/b0.java` (136L) | `o/e.java` (基类) | 事件处理异步执行 |
| `o/a.java` (346L) | `AccessibilityDelegateManager` | delegate 清理/移除 |
| `o/d.java` (292L) | `o/c.java` (KeepAliveEngine) | 厂商引擎启动任务 |
| `o/f.java` (31L) | `o/g.java` (AospEngine) | AOSP 引擎状态重置 |
