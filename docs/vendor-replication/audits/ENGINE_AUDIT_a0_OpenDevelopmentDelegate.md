# ENGINE AUDIT: o/a0.java → OpenDevelopmentDelegate

> Vendor: `decompiled_vendor/sources/o/a0.java` (2003行, 最大的引擎文件)
> Replica: `OpenDevelopmentDelegate.java`
> 审计日期: 2026-03-21

## 1. 类定义

| 属性 | Vendor |
|------|--------|
| 类名 | `o.a0` |
| 继承 | `extends o.e` (AccessibilityDelegate, 非 KeepAliveEngine) |
| 行数 | 2003 |
| 角色 | 开发者选项/ADB调试/USB调试/无线调试 自动开启 |

## 2. 字段

| Vendor 字段 | 类型 | 说明 |
|-------------|------|------|
| `f601n` | `ScheduledExecutorService` | 定时任务 |
| `f602o` | `ConcurrentLinkedQueue` | 状态队列 |
| `f603p` | `AtomicReference` | 当前阶段 |
| `q` | `ReentrantLock` | 线程锁 |
| `f604r` | `AtomicBoolean` | 运行状态 |
| `f605s` | `boolean` | USB 调试已开启 |
| `f606t` | `boolean` | 无线调试已开启 |
| `f607u` | `boolean` | ADB 已开启 |

## 3. E0() — ListenWindow 列表 (~18 个)

| # | packageName | className | 说明 |
|---|-------------|-----------|------|
| 0 | com.android.settings | ...DevelopmentSettingsDashboardActivity | 开发者选项 (新版) |
| 1 | com.android.settings | ...DevelopmentSettingsActivity | 开发者选项 (旧版) |
| 2 | com.android.settings | I() 返回 | 设置对话框 |
| 3 | com.android.settings | ...SubSettings | 子设置页 |
| 4 | com.android.settings | s0() 返回 | 特定设置页 |
| 5 | com.android.settings | ...hihonor...SubSettings | 荣耀子设置 |
| 6 | com.android.settings | android.widget.FrameLayout | 设置 FrameLayout |
| 7 | *(条件)* | Y0() | 厂商特定 |
| 8 | *(条件)* | Z0() | 厂商特定 |
| 9 | com.android.systemui | android.app.Dialog | 系统 UI 对话框 |
| 10 | com.android.settings | *(null=任意)* | 设置通用 |
| 11 | *(条件)* | B0() | 厂商特定 |
| 12-14 | *(条件)* | y0(), z0(), A0() | 厂商特定 |
| 15 | *(共享)* | M0() | 安装未知应用 |
| 16 | *(共享)* | I0() | 受限设置 |

## 4. 核心静态方法 — ListenWindow 构建

### 共享 ListenWindow (被其他引擎引用)

| 方法 | packageName | className | 说明 |
|------|-------------|-----------|------|
| `I0()` | com.android.settings | ...ManageAppExternalSourcesActivity | 安装未知应用 |
| `M0()` | com.android.settings | ...ManageAppExternalSourcesActivity | 安装未知应用 (变体) |
| `Y()` | com.android.settings | android.app.AlertDialog | 设置对话框 |
| `i0()` | com.android.settings | android.app.AlertDialog | 设置对话框 (变体) |
| `W()` | com.android.settings | ...DevelopmentSettingsDashboardActivity | 开发者选项 |

### 厂商特定 ListenWindow

| 方法 | packageName | className | 条件 |
|------|-------------|-----------|------|
| `Y0()` | com.android.settings | ...vivo...SubSettings | vivo 设备 |
| `Z0()` | com.android.settings | ...vivo...VivoSubSettings | vivo 设备 |
| `B0()` | com.android.settings | ...samsung...DevelopmentSettings | 三星设备 |
| `y0()` | com.android.settings | ...miui...DeveloperSettings | 小米设备 |
| `z0()` | com.android.settings | ...coloros...DeveloperSettings | OPPO 设备 |
| `A0()` | com.android.settings | ...hihonor...DeveloperSettings | 荣耀设备 |

## 5. CombineFilter 方法

| 方法 | 配置 Key | 说明 |
|------|----------|------|
| `J0()` | *(无)* | className=Switch |
| `K0()` | *(无)* | className=CheckBox |
| `L0()` | *(无)* | className=CompoundButton |
| `Q0()` | *(无)* | id 包含 "switch" |
| `R0()` | *(无)* | id 包含 "checkbox" |
| `S0()` | *(无)* | className=RadioButton |
| `T()` | COMMON_USB_DEBUG_TEXT | USB 调试 |
| `U()` | COMMON_WIRELESS_DEBUG_TEXT | 无线调试 |
| `V()` | COMMON_ALLOW_USB_DEBUG_TEXT | 允许 USB 调试 |
| `V0()` | COMMON_ALLOW_WIRELESS_DEBUG_TEXT | 允许无线调试 |
| `X()` | COMMON_CONFIRM_TEXT | 确认按钮 |
| `X0()` | COMMON_CANCEL_TEXT | 取消按钮 |
| `Z()` | COMMON_ALLOW_TEXT | 允许按钮 |
| `c0()` | COMMON_INSTALL_UNKNOWN_APP_TEXT | 安装未知应用 |
| `d0()` | COMMON_ALLOW_INSTALL_UNKNOWN_APP_TEXT | 允许安装未知应用 |
| `q0()` | *(无)* | className=ToggleButton |
| `u0()` | COMMON_DEVELOPER_OPTIONS_TEXT | 开发者选项 |

### H0() — USB 调试确认对话框按钮 (OR)

```java
CombineFiltersWithOr:
  Filter1: Button + id=android:id/button1
  Filter2: Button + text=确认 (COMMON_CONFIRM_TEXT)
  Filter3: Button + text=允许 (COMMON_ALLOW_TEXT)
```

### a0() — 开关控件 (OR)

```java
CombineFiltersWithOr:
  Filter1: Switch
  Filter2: CheckBox
  Filter3: CompoundButton
  Filter4: id 包含 "switch"
  Filter5: id 包含 "checkbox"
  Filter6: ToggleButton
```

### b0() — 开发者选项开关 (OR, 带 CombineFilterWithChild)

```java
CombineFiltersWithOr:
  Filter1: clickable LinearLayout + child(USB调试文本)
  Filter2: clickable LinearLayout + child(无线调试文本)
  Filter3: clickable + child(开发者选项文本)
```

## 6. 核心实例方法

### 窗口匹配检查

| 方法 | 匹配窗口 | 说明 |
|------|----------|------|
| `K()` | DevelopmentSettingsDashboardActivity | 在开发者选项页 |
| `L()` | SubSettings / hihonor SubSettings | 在子设置页 |
| `M()` | DevelopmentSettingsActivity | 在旧版开发者选项 |
| `N()` | 厂商特定 (Y0/Z0/B0/y0/z0/A0) | 在厂商开发者选项 |
| `O()` | Dialog | 在对话框 |
| `P()` | ManageAppExternalSourcesActivity | 在安装未知应用页 |
| `Q()` | FrameLayout | 在 FrameLayout |

### 操作方法

| 方法 | 说明 |
|------|------|
| `N0()` | 主入口: 完成后清理 + 结束 |
| `D0()` | 保存开发者选项状态 |
| `G0(UiObject)` | 查找并操作开关 (Switch/CheckBox/CompoundButton) |
| `T0(UiObject)` | 操作 USB 调试/无线调试开关 |
| `R(UiObject)` | 检查 USB 调试是否已开启 |
| `S(UiObject)` | 检查无线调试是否已开启 |
| `e0(UiObject)` | 操作 Switch 开关 (CheckedResult) |
| `h0(UiObject)` | 操作 CheckBox 开关 (CheckedResult) |
| `H(a0)` | 静态: 执行完整的开发者选项开启流程 |

## 7. 状态机 — u() 事件处理

```
事件到达 → u()
  ├─ K() 开发者选项页 → keepAliveInDevelopmentSettings → 异步操作
  ├─ L() 子设置页 → keepAliveInSubSettings → 异步操作
  ├─ M() 旧版开发者选项 → keepAliveInDevelopmentSettingsOld → 异步操作
  ├─ N() 厂商开发者选项 → keepAliveInVendorDevelopment → 异步操作
  ├─ O() 对话框 → keepAliveInDialog → 点击确认/允许
  ├─ P() 安装未知应用 → keepAliveInUnknownApp → 操作开关
  └─ Q() FrameLayout → keepAliveInFrameLayout → 异步操作
```

## 8. 操作流程

```
1. 打开开发者选项页面
2. 查找 USB 调试开关 → 开启
3. 弹出确认对话框 → 点击"确认"/"允许"
4. 查找无线调试开关 → 开启
5. 弹出确认对话框 → 点击"确认"/"允许"
6. 导航到安装未知应用页面
7. 开启"允许安装未知应用"开关
8. 保存状态 → 结束
```

## 9. 被其他引擎引用的方法

`a0.java` 提供了多个被其他引擎引用的共享方法:

| 方法 | 引用者 | 说明 |
|------|--------|------|
| `M0()` | `k.J()` (EnableSecureDelegate) | 安装未知应用 ListenWindow |
| `I0()` | `k.J()` (EnableSecureDelegate) | 安装未知应用 ListenWindow |
| `Y()` | `t.X()` (ScreenUnlockDelegate) | 设置对话框 ListenWindow |
| `i0()` | `t.X()` (ScreenUnlockDelegate) | 设置对话框 ListenWindow |
| `e0()` | 多个引擎 | Switch 操作 (CheckedResult) |
| `h0()` | 多个引擎 | CheckBox 操作 (CheckedResult) |

## 10. 复刻注意事项

1. **最大文件**: 2003 行，包含大量 CombineFilter 构建方法
2. **多厂商覆盖**: 同时支持 AOSP/华为/荣耀/小米/OPPO/vivo/三星 的开发者选项页面
3. **共享方法**: 多个方法被其他引擎引用，需要作为公共工具类提取
4. **开关类型多样**: Switch/CheckBox/CompoundButton/ToggleButton/RadioButton 全部支持
5. **对话框处理**: USB 调试确认对话框有多种按钮匹配方式 (id/text)
