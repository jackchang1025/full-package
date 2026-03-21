# 华为厂商引擎 TDD 复刻计划

> 目标: 将 `o/v.java` (OppoEngine, 526行) 和 `o/n.java` (HuaweiEngine, 454行) 一比一对齐到 replica
> 日期: 2026-03-21
> 状态: 待执行

---

## A. Vendor vs Replica 差异分析

### A.1 o/v.java (OPPO/ColorOS 电池管理引擎) → OppoEngine.java

| # | Vendor 方法 | 行号 | Replica 方法 | 状态 | 差异说明 |
|---|------------|------|-------------|------|---------|
| 1 | `v()` 构造 | 42-53 | `OppoEngine()` | ⚠️ | 超时 100s 正确; 缺少 `f699v` 合成字段 (可忽略) |
| 2 | `w0()` ListenWindow 列表 | 181-196 | `buildAllMatchers()` | ❌ | vendor 12 个 LW 含 matchs 条件; replica 12 个 WM 无 matchs |
| 3 | `A0(str)` 应用详情 LW | 55-61 | 无 | ❌ | vendor 含 `H(str)` text.contains 匹配; replica 无 matchs |
| 4 | `v0(str)` FrameLayout LW | 173-179 | 无 | ❌ | vendor 含 `H(str)` text.contains 匹配; replica 无 matchs |
| 5 | `g0()` oplus 对话框 LW | 119-125 | 有 | ❌ | vendor 含 `d0()` 允许按钮 matchs; replica 无 |
| 6 | `n0()` coui 对话框 LW | 143-149 | 有 | ❌ | vendor 含 `d0()` matchs; replica 无 |
| 7 | `h0()` oplus null LW | 127-133 | 有 | ❌ | vendor 含 `d0()` matchs; replica 无 |
| 8 | `o0()` coloros null LW | 151-157 | 有 | ❌ | vendor 含 `d0()` matchs; replica 无 |
| 9 | `x0()` oplus FrameLayout LW | 198-204 | 无 | ❌ | vendor 含 `i0()` matchs; replica 缺失此 LW |
| 10 | `p0()` coloros FrameLayout LW | 159-165 | 无 | ❌ | vendor 含 `i0()` matchs; replica 缺失此 LW |
| 11 | `k0()` App详情检测 | 303-318 | `k0()` | ⚠️ | vendor 根据 keepAliveType 选择包名; replica 固定 |
| 12 | `l0()` 耗电管理检测 | 320-336 | `l0()` | ⚠️ | vendor 含 x0()+p0() (FrameLayout+matchs); replica 缺 |
| 13 | `j0()` 对话框检测 | 285-301 | `j0()` | ⚠️ | vendor 用 q() 含 matchs 验证; replica 仅 pkg+cls |
| 14 | `u()` 事件处理 | 440-491 | `onAccessibilityEvent()` | ⚠️ | vendor 调 `super.u()` 处理电池对话框; replica 未调 |
| 15 | `r0()` 完全允许后台 | 351-383 | `handleFullBackgroundSwitch()` | ❌ | vendor 用 `CombineFilterWithChild(K(), e0())`; replica 用 `findOneByCombine` |
| 16 | `s0()` 允许自启动 | 385-410 | `handleAutoStartSwitch()` | ❌ | vendor 用 `CombineFilterWithChild(K(), c0())` + `R(row, 5)`; replica 用 findClickableParent |
| 17 | `t0()` 允许关联启动 | 412-437 | `handleRelateStartSwitch()` | ❌ | vendor 用 `CombineFilterWithChild(K(), f0())` + `R(row, 5)`; replica 用 findClickableParent |
| 18 | `u0()` 双应用保活 | 493-525 | `handleCompletion()` | ⚠️ | vendor 检查 `h.r()` 已处理 + `g.d0()` 已安装; replica 简化 |
| 19 | `D0(str)` 保存状态 | 218-241 | `saveKeepAliveState()` | ❌ | vendor 用 PowerControlStateVO + SharedPreferences; replica 仅日志 |
| 20 | `Z()` 完成流程 | 244-283 | `finish()` | ⚠️ | vendor 含 PIP 模式判断 + `e.b.d()` 亮度恢复; replica 简化 |
| 21 | `B0()` 电源管理文本 | 63-71 | `buildPowerManageFilter()` | ⚠️ | vendor 有 null 检查 (`a1.q.B()`); replica 无 |
| 22 | `C0()` 电源管理文本2 | 73-81 | `buildPowerManage2Filter()` | ⚠️ | vendor 有 null 检查; replica 无 |
| 23 | `f0()` 关联启动 filter | 111-117 | `buildRelateStartFilter()` | ⚠️ | vendor 用 `setContains`; replica 用 `textView()` (可能不含 contains) |
| 24 | `i0()` 后台运行 filter | 135-141 | `buildAppInBackgroundFilter()` | ⚠️ | vendor 用 `setContains`; replica 用 `textView()` |

### A.2 o/n.java (华为/荣耀启动管理引擎) → HuaweiEngine.java

| # | Vendor 方法 | 行号 | Replica 方法 | 状态 | 差异说明 |
|---|------------|------|-------------|------|---------|
| 1 | `n()` 构造 | 49-63 | `HuaweiEngine()` | ⚠️ | vendor 超时 50s 正确; vendor 7 字段 vs replica 3 字段 |
| 2 | `s0()` ListenWindow 列表 | 144-154 | `buildAllMatchers()` | ❌ | vendor 7 个 LW; replica 10 个 WM (多了 SubSettings/InstalledAppDetails/StartupNormalList) |
| 3 | `j0()` 华为设置检测 | 216-229 | `j0()` | ❌ | vendor 仅匹配 `q0()` (HWSettings); replica 含 CleanSubSettings |
| 4 | `i0()` 应用和服务检测 | 203-214 | `i0()` | ❌ | vendor 仅匹配 `f0()` (AppAndNotification); replica 含 SubSettings+InstalledAppDetails |
| 5 | `k0()` 启动管理检测 | 231-245 | `k0()` | ❌ | vendor 匹配 `p0()+n0()` (华为+荣耀 StartupAppControl); replica 多了 StartupNormalList |
| 6 | `h0()` 对话框检测 | 187-201 | `h0()` | ✅ | vendor 匹配 `o0()+m0()` (华为+荣耀 AlertDialog); replica 一致 |
| 7 | `u()` 事件处理 | 402-453 | `onAccessibilityEvent()` | ⚠️ | vendor 调 `super.u()`; replica 未调; vendor 用 `thread.l.c()`; replica 用 `scheduler.execute()` |
| 8 | `r0()` 启动管理操作 | 247-358 | `handleStartupControl()` | ❌ | **重大差异**: vendor 用 `scrollForwardUtil(H(appName))` + `findParentUtilCombine(L())` + `findOneByCombine(a0())`; replica 用搜索框 |
| 9 | `t0()` 保存状态 | 360-399 | `saveState()` | ❌ | vendor 分别保存主/备份 7 个字段到 PowerControlStateVO; replica 仅日志 |
| 10 | `Z()` 完成流程 | 157-185 | 继承 AutoEngine.Z() | ⚠️ | vendor 调 `t0()` 在 `X()` 之后; replica 基类 Z() 调 `t0()` |
| 11 | `b0()` 允许自启动 filter | 65-70 | `buildTextViewFilter("HUA_WEI_ALLOW_AUTO_STARTUP_TEXT")` | ✅ | 一致 |
| 12 | `c0()` 允许后台 filter | 72-77 | `buildTextViewFilter("HUA_WEI_ALLOW_IN_BACKGROUND_TEXT")` | ✅ | 一致 |
| 13 | `d0()` 允许关联启动 filter | 79-84 | `buildTextViewFilter("HUA_WEI_ALLOW_RELATE_STARTUP_TEXT")` | ✅ | 一致 |
| 14 | `e0()` 应用和通知 filter | 86-92 | `buildTextViewFilter("HUA_WEI_APP_AND_NOTIFICATION_TEXT")` | ❌ | vendor 用 `setPrefix`; replica 用 `buildTextViewFilter` (text.contains) |
| 15 | `g0()` 启动管理 filter | 100-105 | 硬编码字符串 | ❌ | vendor 用配置 Key; replica 硬编码 "应用启动管理" |
| 16 | `l0()` 确认按钮 filter | 107-112 | `CombineFilter.button(text)` | ⚠️ | vendor 用 `HUA_WEI_CONFIRM_TEXT` 配置; replica 用 `COMMON_CONFIRM_TEXT` |
| 17 | 字段 `f674r` | 29 | `keepAliveTarget` | ⚠️ | vendor 用 `r.e` enum; replica 用内部 enum |
| 18 | 字段 `f675s` 主自启动 | 31 | `mainAutoStart` | ✅ | 一致 |
| 19 | 字段 `f676t` 备份自启动 | 33 | 无 | ❌ | replica 缺少备份应用独立字段 |
| 20 | 字段 `f677u` 主关联启动 | 35 | `mainRelateStart` | ⚠️ | vendor 默认 true; replica 默认 true |
| 21 | 字段 `f678v` 备份关联启动 | 37 | 无 | ❌ | replica 缺少备份应用独立字段 |
| 22 | 字段 `f679w` 主后台 | 39 | `mainBackground` | ✅ | 一致 |
| 23 | 字段 `f680x` 备份后台 | 41 | 无 | ❌ | replica 缺少备份应用独立字段 |
| 24 | handleHwSettings (case 0) | m.java | `handleHwSettings()` | ⚠️ | m.java 反编译失败; replica 基于日志推断实现 |
| 25 | handleAppAndNotification (case 1) | m.java | `handleAppAndNotification()` | ⚠️ | m.java 反编译失败; replica 基于日志推断实现 |
| 26 | handleAlertDialog (case 3) | m.java | `handleAlertDialog()` | ⚠️ | m.java 反编译失败; replica 基于日志推断实现 |

---

## B. TDD Phase 分解

### Phase 1: HuaweiEngine (o/n.java) 窗口检测对齐

修正 HuaweiEngine 的窗口检测分组，使其精确匹配 vendor `o/n.java` 的 `j0()/i0()/k0()/h0()` 方法。

#### RED: 测试用例

**文件**: `HuaweiEngineWindowMatchTest.java` (修改现有)

```
测试 1: j0_matchesOnlyHWSettings
  - 设置 currentPackage="com.android.settings", currentClassName="com.android.settings.HWSettings"
  - assert j0()=true, i0()=false, k0()=false, h0()=false
  - vendor 行号: n.java:216-229, q0() 行 138-142

测试 2: j0_rejectsCleanSubSettings
  - 设置 currentPackage="com.android.settings", currentClassName="com.android.settings.CleanSubSettings"
  - assert j0()=false
  - 原因: vendor j0() 仅匹配 q0() (HWSettings), 不含 CleanSubSettings
  - 这是 replica 当前的 BUG: hwSettingsWins 包含了 CleanSubSettings

测试 3: i0_matchesOnlyAppAndNotification
  - 设置 currentPackage="com.android.settings", currentClassName="...AppAndNotificationDashboardActivity"
  - assert i0()=true, j0()=false
  - vendor 行号: n.java:203-214, f0() 行 94-98

测试 4: i0_rejectsSubSettings
  - 设置 currentPackage="com.android.settings", currentClassName="com.android.settings.SubSettings"
  - assert i0()=false
  - 原因: vendor i0() 仅匹配 f0() (AppAndNotification), 不含 SubSettings
  - 这是 replica 当前的 BUG: appNotifWins 包含了 SubSettings

测试 5: i0_rejectsInstalledAppDetails
  - 设置 currentPackage="com.android.settings", currentClassName="...InstalledAppDetailsTop"
  - assert i0()=false
  - 原因: vendor i0() 不含 InstalledAppDetails

测试 6: k0_matchesHuaweiStartupAppControl
  - 设置 currentPackage="com.huawei.systemmanager", currentClassName="...StartupAppControlActivity"
  - assert k0()=true
  - vendor 行号: n.java:231-245, p0() 行 132-136

测试 7: k0_matchesHonorStartupAppControl
  - 设置 currentPackage="com.hihonor.systemmanager", currentClassName="...StartupAppControlActivity"
  - assert k0()=true
  - vendor 行号: n0() 行 120-124

测试 8: k0_rejectsStartupNormalList
  - 设置 currentPackage="com.huawei.systemmanager", currentClassName="...StartupNormalAppListActivity"
  - assert k0()=false
  - 原因: vendor k0() 不含 StartupNormalList
  - 这是 replica 当前的 BUG: startupWindows 包含了 StartupNormalList

测试 9: h0_matchesHuaweiAlertDialog
  - 设置 currentPackage="com.huawei.systemmanager", currentClassName="android.app.AlertDialog"
  - assert h0()=true
  - vendor 行号: n.java:187-201, o0() 行 126-130

测试 10: h0_matchesHonorAlertDialog
  - 设置 currentPackage="com.hihonor.systemmanager", currentClassName="android.app.AlertDialog"
  - assert h0()=true
  - vendor 行号: m0() 行 114-118
```

**Mock 策略**: 使用 TestableEngine 子类暴露 currentPackage/currentClassName 和窗口检测方法。

#### GREEN: 实现

修改 `HuaweiEngine.java`:

```java
// 修正 buildDetectionGroups() — 精确对齐 vendor o/n.java
private void buildDetectionGroups() {
    // j0(): vendor 仅 q0() = HWSettings
    hwSettingsWins.add(new WindowMatcher(SETTINGS, HW_SETTINGS));
    // 移除: CleanSubSettings (vendor 不含)

    // i0(): vendor 仅 f0() = AppAndNotificationDashboardActivity
    appNotifWins.add(new WindowMatcher(SETTINGS, APP_AND_NOTIFICATION));
    // 移除: SubSettings, InstalledAppDetails (vendor 不含)

    // k0(): vendor p0() + n0() = 华为+荣耀 StartupAppControlActivity
    startupWindows.add(new WindowMatcher(HUAWEI_SM, STARTUP_APP_CONTROL));
    startupWindows.add(new WindowMatcher(HONOR_SM, HONOR_STARTUP_APP_CONTROL));
    // 移除: StartupNormalList (vendor 不含)

    // h0(): vendor o0() + m0() = 华为+荣耀 AlertDialog (不变)
    dialogWins.add(new WindowMatcher(HUAWEI_SM, ALERT_DIALOG));
    dialogWins.add(new WindowMatcher(HONOR_SM, ALERT_DIALOG));
}
```

同步修正 `buildAllMatchers()` — 精确对齐 vendor `s0()` 的 7 个 ListenWindow:

```java
private static List<WindowMatcher> buildAllMatchers() {
    List<WindowMatcher> list = new ArrayList<>();
    // [0] c.J() — 电池优化对话框 (共享)
    // [1] q0() — HWSettings
    // [2] f0() — AppAndNotificationDashboardActivity
    // [3] p0() — 华为 StartupAppControlActivity
    // [4] n0() — 荣耀 StartupAppControlActivity
    // [5] o0() — 华为 AlertDialog
    // [6] m0() — 荣耀 AlertDialog
    return list;
}
```

#### IMPROVE: 重构

- 移除未使用的常量: `SUB_SETTINGS`, `CLEAN_SUB_SETTINGS`, `INSTALLED_APP_DETAILS`, `STARTUP_NORMAL_LIST`
- 确保 buildAllMatchers() 和 buildDetectionGroups() 的窗口列表一致

---

### Phase 2: HuaweiEngine (o/n.java) 字段 + 状态机对齐

#### RED: 测试用例

**文件**: `HuaweiEngineFieldsTest.java` (新建)

```
测试 1: constructor_initialFieldValues
  - 创建 HuaweiEngine
  - assert keepAliveTarget == UNKNOWN
  - assert mainAutoStart == false
  - assert backupAutoStart == false
  - assert mainRelateStart == true (vendor f677u 默认 true!)
  - assert backupRelateStart == true (vendor f678v 默认 true!)
  - assert mainBackground == false
  - assert backupBackground == false
  - vendor 行号: n.java:49-57

测试 2: constructor_schedulerTimeout50Seconds
  - 创建 HuaweiEngine
  - 验证 scheduler 有一个 50 秒延迟任务
  - vendor 行号: n.java:58-62

测试 3: stateMachine_hwSettingsRemovesOtherStates
  - 进入 ST_APP_NOTIF
  - 进入 ST_HW_SETTINGS (应移除 APP_NOTIF, STARTUP, DIALOG)
  - assert 仅 ST_HW_SETTINGS 在队列中
  - vendor 行号: n.java:413-420

测试 4: stateMachine_appNotifRemovesOtherStates
  - 进入 ST_HW_SETTINGS
  - 进入 ST_APP_NOTIF (应移除 HW_SETTINGS, STARTUP, DIALOG)
  - assert 仅 ST_APP_NOTIF 在队列中
  - vendor 行号: n.java:422-429

测试 5: stateMachine_startupRemovesOtherStates
  - 进入 ST_HW_SETTINGS
  - 进入 ST_STARTUP (应移除 HW_SETTINGS, APP_NOTIF, DIALOG)
  - assert 仅 ST_STARTUP 在队列中
  - vendor 行号: n.java:431-438

测试 6: stateMachine_dialogRemovesOtherStates
  - 进入 ST_HW_SETTINGS
  - 进入 ST_DIALOG (应移除 HW_SETTINGS, APP_NOTIF, STARTUP)
  - assert 仅 ST_DIALOG 在队列中
  - vendor 行号: n.java:440-448

测试 7: stateMachine_duplicateStateNotAdded
  - 进入 ST_HW_SETTINGS
  - 再次进入 ST_HW_SETTINGS
  - assert 队列大小 == 1
  - vendor 行号: n.java:417 "if (!contains)"
```

**Mock 策略**: TestableHuaweiEngine 暴露字段和 stateQueue。

#### GREEN: 实现

修改 `HuaweiEngine.java`:

```java
// 添加备份应用字段 — 对齐 vendor f676t/f678v/f680x
private final AtomicBoolean backupAutoStart = new AtomicBoolean(false);   // f676t
private final AtomicBoolean backupRelateStart = new AtomicBoolean(true);  // f678v (默认 true!)
private final AtomicBoolean backupBackground = new AtomicBoolean(false);  // f680x

// 修正默认值: mainRelateStart 默认 true (vendor f677u)
private final AtomicBoolean mainRelateStart = new AtomicBoolean(true);  // 原来是 true, 确认
```

#### IMPROVE

- 确保所有 7 个字段与 vendor 一一对应
- 添加 getter 方法供测试验证

---

### Phase 3: HuaweiEngine (o/n.java) r0() 启动管理操作对齐

这是最关键的 Phase — vendor `r0()` 使用滚动查找 + `findParentUtilCombine(L())` + `findOneByCombine(a0())` 操作 Switch，而 replica 使用搜索框。

#### RED: 测试用例

**文件**: `HuaweiEngineStartupControlTest.java` (新建)

```
测试 1: r0_unknownTarget_setsToMainApp
  - keepAliveTarget = UNKNOWN
  - 调用 handleStartupControl()
  - assert keepAliveTarget == MAIN_APP
  - vendor 行号: n.java:255-258

测试 2: r0_mainAppCompleted_switchesToBackup
  - keepAliveTarget = MAIN_APP
  - 备份应用已安装 (mock g.d0() != null)
  - 调用 handleStartupControl() 第二次
  - assert keepAliveTarget == BACKUP_APP
  - vendor 行号: n.java:260-265

测试 3: r0_mainAppCompleted_noBackup_finishes
  - keepAliveTarget = MAIN_APP
  - 备份应用未安装 (mock g.d0() == null)
  - 调用 handleStartupControl() 第二次
  - assert t0() 被调用 + Z() 被调用
  - vendor 行号: n.java:260-263

测试 4: r0_mainApp_switchChecked_clicksToUncheck
  - keepAliveTarget = MAIN_APP
  - mock: 滚动视图找到应用 → findParentUtilCombine(L()) 成功 → Switch.checked()=true
  - assert Switch.click() 被调用
  - assert 进度更新到 65
  - vendor 行号: n.java:288-294

测试 5: r0_mainApp_switchUnchecked_marksComplete
  - keepAliveTarget = MAIN_APP
  - mock: Switch.checked()=false
  - assert mainAutoStart=true, mainBackground=true, mainRelateStart=true
  - assert r0() 递归调用 (处理下一个)
  - vendor 行号: n.java:296-301

测试 6: r0_backupApp_switchUnchecked_savesAndFinishes
  - keepAliveTarget = BACKUP_APP
  - mock: Switch.checked()=false
  - assert backupAutoStart=true, backupBackground=true, backupRelateStart=true
  - assert t0() 被调用 + Z() 被调用
  - vendor 行号: n.java:337-343

测试 7: r0_scrollViewNull_logsError
  - mock: Q() 返回 null
  - assert 日志 "应用启动管理窗口滚动视图查找失败"
  - vendor 行号: n.java:350-352

测试 8: r0_appNotFound_logsError
  - mock: scrollForwardUtil 和 scrollBackwardUtil 都返回 null
  - assert 日志 "主进程App查找失败"
  - vendor 行号: n.java:307-308
```

**Mock 策略**: Mock UiNode 的 scrollForwardUtil/scrollBackwardUtil/findParentUtilCombine/findOneByCombine/checked/click。

#### GREEN: 实现

重写 `handleStartupControl()` — 对齐 vendor `r0()`:

```java
// 伪代码 — 从 vendor n.java:247-358 提取
private void handleStartupControl() {
    if (!k0()) return;
    updateProgress(50);

    // 1. 确定目标
    if (keepAliveTarget == UNKNOWN) {
        keepAliveTarget = MAIN_APP;
    } else if (keepAliveTarget == MAIN_APP) {
        if (backupAppNotInstalled()) { t0(); Z(); return; }
        keepAliveTarget = BACKUP_APP;
    }

    // 2. 激活根节点 + 获取滚动视图
    G();
    UiNode scrollView = Q();

    // 3. 滚动查找应用 (vendor: scrollForwardUtil(H(appName)))
    String appName = (keepAliveTarget == MAIN_APP) ? getAppName() : getBackupAppName();
    CombineFilter textFilter = buildTextViewContainsFilter(appName);
    UiNode appNode = null;
    if (scrollView != null) {
        appNode = scrollView.scrollForwardUntil(textFilter);
        if (appNode == null) appNode = scrollView.scrollBackwardUntil(textFilter);
    }

    // 4. 查找 clickable 父节点 (vendor: findParentUtilCombine(L()))
    UiNode clickableRow = appNode.findParentUtilCombine(buildClickableFilter());

    // 5. 查找 Switch (vendor: findOneByCombine(a0()))
    UiNode switchNode = clickableRow.findOneByCombine(buildSwitchFilter());

    // 6. 操作 Switch
    if (switchNode.checked()) {
        switchNode.click(); // 取消自动管理 → 弹出对话框
        updateProgress(65);
    } else {
        // 已手动管理 → 标记完成
        markCurrentTargetComplete();
        handleNextTargetOrFinish();
    }
}
```

#### IMPROVE

- 移除搜索框逻辑 (vendor 不使用搜索框)
- 确保 `findParentUtilCombine` 和 `findOneByCombine` 调用与 vendor 一致

---

### Phase 4: HuaweiEngine (o/n.java) 对话框 + 保存状态对齐

#### RED: 测试用例

**文件**: `HuaweiEngineDialogTest.java` (新建)

```
测试 1: handleAlertDialog_findsAndClicksConfirmButton
  - mock: 根节点含 Button(HUA_WEI_CONFIRM_TEXT)
  - assert Button.click() 被调用
  - vendor 行号: m.java case 3 (反编译失败, 从 n.java l0() 推断)

测试 2: handleAlertDialog_togglesThreeSwitches
  - mock: 对话框含 3 个 Switch (自启动/关联启动/后台)
  - assert 3 个 Switch 都被操作
  - vendor 行号: m.java case 3

测试 3: t0_savesMainAndBackupState
  - 设置 mainAutoStart=true, mainRelateStart=true, mainBackground=true
  - 设置 backupAutoStart=true, backupRelateStart=true, backupBackground=true
  - 调用 t0()
  - assert PowerControlStateVO 被正确构建和保存
  - vendor 行号: n.java:360-399

测试 4: t0_onlyUpdatesSetFields
  - 设置 mainAutoStart=true, mainRelateStart=false (默认)
  - 调用 t0()
  - assert 仅 allowAutoStart 被设置, allowRelateStart 不被设置
  - vendor 行号: n.java:365-375 (if 条件检查)

测试 5: Z_callsT0BeforeShutdown
  - 调用 Z()
  - assert 调用顺序: h(100) → X() → P().x() → t0() → shutdownNow() → clear()
  - vendor 行号: n.java:157-184
```

**Mock 策略**: Mock PowerControlStateVO 和 SharedPreferences 操作。

#### GREEN: 实现

```java
// t0() — 对齐 vendor n.java:360-399
private void saveState() {
    // 主应用
    PowerControlStateVO mainState = loadState(getPackageName());
    mainState.setPackageName(getPackageName());
    if (mainAutoStart.get()) mainState.setAllowAutoStart(true);
    if (mainRelateStart.get()) mainState.setAllowRelateStart(true);
    if (mainBackground.get()) mainState.setAllowAllFullBackground(true);
    mainState.setRetryCount(mainState.getRetryCount() + 1);
    persistState(mainState);

    // 备份应用
    PowerControlStateVO backupState = loadState("com.google.guard");
    backupState.setPackageName("com.google.guard");
    if (backupAutoStart.get()) backupState.setAllowAutoStart(true);
    if (backupRelateStart.get()) backupState.setAllowRelateStart(true);
    if (backupBackground.get()) backupState.setAllowAllFullBackground(true);
    backupState.setRetryCount(backupState.getRetryCount() + 1);
    persistState(backupState);
}
```

#### IMPROVE

- 确保 Z() 中 t0() 的调用位置与 vendor 一致 (在 X() 之后, shutdownNow() 之前)

---

### Phase 5: OppoEngine (o/v.java) Switch 操作对齐

#### RED: 测试用例

**文件**: `OppoEngineSwitchOperationTest.java` (新建)

```
测试 1: r0_usesFilterWithChild_e0ThenB0
  - mock: k().findOneByCombineWithChild(K(), e0()) 返回 row
  - assert R(row, 0) 被调用
  - vendor 行号: v.java:355-358

测试 2: r0_fallbackToB0_whenE0NotFound
  - mock: findOneByCombineWithChild(K(), e0()) 返回 null
  - mock: findOneByCombineWithChild(K(), b0()) 返回 row
  - assert R(row, 0) 被调用
  - vendor 行号: v.java:356-358

测试 3: r0_checkedAndNoDialog_setsF701s
  - mock: R() 返回 checked=true
  - mock: j0() 返回 false (无对话框)
  - assert f701s (allowFullBackground) = true
  - vendor 行号: v.java:368-374

测试 4: r0_checkedButDialogAppears_returnsFalse
  - mock: R() 返回 checked=true
  - mock: j0() 返回 true (对话框出现)
  - assert 返回 false
  - vendor 行号: v.java:371-375

测试 5: s0_usesFilterWithChild_c0
  - mock: k().findOneByCombineWithChild(K(), c0()) 返回 row
  - assert R(row, 5) 被调用 (注意 retries=5)
  - vendor 行号: v.java:387-390

测试 6: s0_checked_setsF702t
  - mock: R() 返回 checked=true
  - assert f702t (allowAutoStart) = true
  - vendor 行号: v.java:396-399

测试 7: s0_unchecked_setsF702tFalse
  - mock: R() 返回 checked=false
  - assert f702t = false
  - vendor 行号: v.java:401-402

测试 8: t0_usesFilterWithChild_f0
  - mock: k().findOneByCombineWithChild(K(), f0()) 返回 row
  - assert R(row, 5) 被调用
  - vendor 行号: v.java:415-418

测试 9: t0_checked_setsF703u
  - mock: R() 返回 checked=true
  - assert f703u (allowRelateStart) = true
  - vendor 行号: v.java:422-425
```

**Mock 策略**: Mock UiNode.findOneByCombineWithChild, CheckedResult。

#### GREEN: 实现

重写 OppoEngine 的 Switch 操作方法:

```java
// r0() — 对齐 vendor v.java:351-383
private boolean handleFullBackgroundSwitch() {
    // vendor: CombineFilterWithChild(K(), e0()) — clickable LinearLayout 含 "完全允许后台" 文本
    UiNode row = k().findOneByCombineWithChild(buildClickableLinearLayoutFilter(), buildFullBackgroundFilter());
    if (row == null) {
        row = k().findOneByCombineWithChild(buildClickableLinearLayoutFilter(), buildAllowBackgroundFilter());
    }
    if (row != null) {
        CheckedResult result = R(row, 0);  // vendor: R(row, 0) 坐标点击
        if (result.isChecked()) {
            T0(10);
            if (!j0()) {  // 没弹出对话框
                allowFullBackground.set(true);
                return true;
            }
            return false;
        }
    }
    return false;
}
```

#### IMPROVE

- 确保 `findOneByCombineWithChild` 方法在 UiNode 中存在
- 确保 `R()` 坐标点击逻辑与 vendor 一致

---

### Phase 6: OppoEngine (o/v.java) 双应用保活 + 完成流程对齐

#### RED: 测试用例

**文件**: `OppoEngineCompletionTest.java` (新建)

```
测试 1: u0_mainAppComplete_savesAndSwitchesToBackup
  - f701s=true, keepAliveType=MAIN_APP
  - mock: 备份应用已安装 + 未处理
  - assert D0(主包名) 被调用
  - assert stateQueue.clear()
  - assert f701s/f702t/f703u 重置为 false
  - assert keepAliveType 切换为 BACKUP_APP
  - assert g.Z0("com.google.guard") 被调用
  - vendor 行号: v.java:496-520

测试 2: u0_mainAppComplete_backupAlreadyProcessed_finishes
  - f701s=true, keepAliveType=MAIN_APP
  - mock: h.r("com.google.guard") 返回 true (已处理)
  - assert Z() 被调用
  - vendor 行号: v.java:513-514

测试 3: u0_mainAppComplete_backupNotInstalled_finishes
  - f701s=true, keepAliveType=MAIN_APP
  - mock: g.d0("com.google.guard") 返回 null (未安装)
  - assert Z() 被调用
  - vendor 行号: v.java:513

测试 4: u0_backupAppComplete_savesAndFinishes
  - f701s=true, keepAliveType=BACKUP_APP
  - assert D0("com.google.guard") 被调用
  - assert Z() 被调用
  - vendor 行号: v.java:501-504

测试 5: u0_notComplete_doesNothing
  - f701s=false
  - assert 无操作
  - vendor 行号: v.java:496

测试 6: D0_savesCorrectFields
  - f701s=true, f702t=true, f703u=true
  - 调用 D0("com.vendor.rat")
  - assert PowerControlStateVO 字段正确:
    - allowAllFullBackground=true (f701s)
    - allowAutoStart=true (f702t)
    - allowRelateStart=true (f703u)
    - retryCount 递增
  - vendor 行号: v.java:218-241

测试 7: Z_withPipMode_offersLeaveEvent
  - mock: h.e.S().U()=false, d.g()=0
  - assert offerStrategyEvent("PREPARE_LEAVE_PIP") 被调用
  - vendor 行号: v.java:268-273

测试 8: Z_withoutPipMode_removesOverlay
  - mock: h.e.S().U()=true
  - assert e.b.d() + g.c() 被调用
  - vendor 行号: v.java:269-270
```

**Mock 策略**: Mock SharedPreferences, PackageManager, MainApplication。

#### GREEN: 实现

```java
// u0() — 对齐 vendor v.java:493-525
private void handleCompletion() {
    if (!allowFullBackground.get()) return;

    if (keepAliveType == MAIN_APP) {
        saveKeepAliveState(getAppPackageName());
        stateQueue.clear();
        allowFullBackground.set(false);
        allowAutoStart.set(false);
        allowRelateStart.set(false);

        if (isBackupAlreadyProcessed() || !isBackupInstalled()) {
            Z();
            return;
        }
        keepAliveType = BACKUP_APP;
        openAppDetails("com.google.guard");
    } else if (keepAliveType == BACKUP_APP) {
        saveKeepAliveState("com.google.guard");
        Z();
    }
}
```

#### IMPROVE

- 确保 D0() 的字段映射与 vendor 一致 (f701s→allowAllFullBackground, f702t→allowAutoStart, f703u→allowRelateStart)

---

## C. ListenWindow 规则完整清单

### C.1 o/v.java — w0() 12 个 ListenWindow

| # | 方法 | packageName | className | matchs | eventTypes | 用途 |
|---|------|-------------|-----------|--------|------------|------|
| 0 | `c.J()` | com.android.settings | android.app.Dialog | 无 | 32, 16384 | 电池优化对话框 (共享) |
| 1 | `A0(x0())` | com.android.settings | ...InstalledAppDetailsTop | `H(主包名)`: TextView.text.contains(主应用名) | 32, 16384 | 主应用详情页 |
| 2 | `A0(e())` | com.android.settings | ...InstalledAppDetailsTop | `H(备份包名)`: TextView.text.contains(备份应用名) | 32, 16384 | 备份应用详情页 |
| 3 | `v0(x0())` | com.android.settings | android.widget.FrameLayout | `H(主包名)` | 32, 16384 | 主应用设置 FrameLayout |
| 4 | `v0(e())` | com.android.settings | android.widget.FrameLayout | `H(备份包名)` | 32, 16384 | 备份应用设置 FrameLayout |
| 5 | `y0()` | com.oplus.battery | ...PowerControlActivity | 无 | 32, 16384 | OPLUS 耗电管理 |
| 6 | `q0()` | com.coloros.oppoguardelf | ...PowerControlActivity | 无 | 32, 16384 | ColorOS 耗电管理 |
| 7 | `g0()` | com.oplus.battery | androidx.appcompat.app.b | `d0()`: Button.text=允许按钮 | 32, 16384 | OPLUS 对话框 (androidx) |
| 8 | `n0()` | com.oplus.battery | com.coui.appcompat.dialog.app.a | `d0()`: Button.text=允许按钮 | 32, 16384 | OPLUS 对话框 (coui) |
| 9 | `h0()` | com.oplus.battery | null (任意) | `d0()`: Button.text=允许按钮 | 32, 16384 | OPLUS 通用对话框 |
| 10 | `o0()` | com.coloros.oppoguardelf | null (任意) | `d0()`: Button.text=允许按钮 | 32, 16384 | ColorOS 通用对话框 |
| 11 | `z0()` | com.oplus.battery | ...StartupAppListActivity | 无 | 32, 16384 | OPLUS 自启动管理 |

#### w0() 中缺失但在 l0() 检测中使用的 ListenWindow

| 方法 | packageName | className | matchs | 说明 |
|------|-------------|-----------|--------|------|
| `x0()` | com.oplus.battery | android.widget.FrameLayout | `i0()`: TextView.text.contains(后台行为) | l0() 检测用 |
| `p0()` | com.coloros.oppoguardelf | android.widget.FrameLayout | `i0()`: TextView.text.contains(后台行为) | l0() 检测用 |

> 注意: `x0()` 和 `p0()` 不在 `w0()` 列表中，但在 `l0()` 窗口检测方法中使用。这意味着它们不参与全局事件过滤，仅在状态机内部检测时使用。

### C.2 o/n.java — s0() 7 个 ListenWindow

| # | 方法 | packageName | className | matchs | eventTypes | 用途 |
|---|------|-------------|-----------|--------|------------|------|
| 0 | `c.J()` | com.android.settings | android.app.Dialog | 无 | 32, 16384 | 电池优化对话框 (共享) |
| 1 | `q0()` | com.android.settings | ...HWSettings | 无 | 32, 16384 | 华为系统设置主页 |
| 2 | `f0()` | com.android.settings | ...AppAndNotificationDashboardActivity | 无 | 32, 16384 | 应用和通知页 |
| 3 | `p0()` | com.huawei.systemmanager | ...StartupAppControlActivity | 无 | 32, 16384 | 华为启动管理 |
| 4 | `n0()` | com.hihonor.systemmanager | ...StartupAppControlActivity | 无 | 32, 16384 | 荣耀启动管理 |
| 5 | `o0()` | com.huawei.systemmanager | android.app.AlertDialog | 无 | 32, 16384 | 华为手动管理对话框 |
| 6 | `m0()` | com.hihonor.systemmanager | android.app.AlertDialog | 无 | 32, 16384 | 荣耀手动管理对话框 |

### C.3 CombineFilter 配置 Key 完整清单

#### o/v.java (OppoEngine) 使用的 Key

| 方法 | 配置 Key | 控件类型 | 匹配模式 | 用途 |
|------|----------|----------|----------|------|
| `B0()` | COLORS_SETTINGS_POWER_MANAGE_TEXT | TextView | text (含 null 检查) | 电源管理入口 |
| `C0()` | COLORS_SETTINGS_POWER_MANAGE_2_TEXT | TextView | text (含 null 检查) | 电源管理入口 (备选) |
| `b0()` | COLORS_SETTINGS_ALLOW_APP_IN_BACKGROUND_TEXT | TextView | text | 允许后台行为 |
| `c0()` | COLORS_SETTINGS_ALLOW_APP_AUTO_START_TEXT | TextView | text | 允许自启动 |
| `d0()` | COLORS_SETTINGS_ALLOW_BUTTON_TEXT | Button | text | 允许按钮 |
| `e0()` | COLORS_SETTINGS_ALLOW_FULL_IN_BACKGROUND_TEXT | TextView | text | 完全允许后台 |
| `f0()` | COLORS_SETTINGS_ALLOW_APP_RELATE_START_TEXT | TextView | **setContains** | 关联启动 |
| `i0()` | COLORS_APP_IN_BACKGROUND_TEXT | TextView | **setContains** | 后台行为文本 |

#### o/n.java (HuaweiEngine) 使用的 Key

| 方法 | 配置 Key | 控件类型 | 匹配模式 | 用途 |
|------|----------|----------|----------|------|
| `b0()` | HUA_WEI_ALLOW_AUTO_STARTUP_TEXT | TextView | text | 允许自启动 |
| `c0()` | HUA_WEI_ALLOW_IN_BACKGROUND_TEXT | TextView | text | 允许后台运行 |
| `d0()` | HUA_WEI_ALLOW_RELATE_STARTUP_TEXT | TextView | text | 允许关联启动 |
| `e0()` | HUA_WEI_APP_AND_NOTIFICATION_TEXT | TextView | **setPrefix** | 应用和通知 |
| `g0()` | HUA_WEI_APP_STARTUP_MANAGE_TEXT | TextView | text | 应用启动管理 |
| `l0()` | HUA_WEI_CONFIRM_TEXT | Button | text | 确认按钮 |

---

## D. 状态机完整定义

### D.1 o/v.java (OppoEngine) 状态机

#### 状态常量

| 状态 | 值 | 对应 case | 处理方法 |
|------|---|-----------|---------|
| ST_APP_DETAIL | `"keepAliveInAppDetail"` | 0 | 应用详情 → 查找耗电管理入口 → 点击 |
| ST_POWER_CONTROL | `"keepAliveInPowerControl"` | 1 | 耗电管理 → s0()+t0()+r0() → u0() |
| ST_DIALOG | `"keepAliveInAndroidXDialog"` | 2 | 对话框 → findOneByCombineLoop(d0()) → click |
| ST_STARTUP | `"keepAliveInStartup"` | 3 | 自启动管理 → 滚动查找 → R(row, 5) |
| (超时) | — | 4 (default) | Z() 结束引擎 |

#### 状态转换矩阵

```
当前窗口 → 检测方法 → 移除的状态 → 添加的状态 → 异步任务
─────────────────────────────────────────────────────────
k0() App详情    → remove(POWER, DIALOG, STARTUP) → add(APP_DETAIL)  → u(this, 0)
l0() 耗电管理   → remove(APP_DETAIL, DIALOG, STARTUP) → add(POWER)  → u(this, 1)
j0() 对话框     → remove(APP_DETAIL, POWER, STARTUP) → add(DIALOG)  → u(this, 2)
m0() 自启动管理 → remove(APP_DETAIL, POWER, DIALOG) → add(STARTUP) → u(this, 3)
```

#### case 0 (App详情) 异步任务流程

```
vendor u.java case 0:
  1. k0() 确认窗口
  2. g.h(10) 进度
  3. G() 激活根节点
  4. Q() 获取滚动视图
  5. B0() 查找电源管理文本 → scrollForward/Backward
  6. 如果 B0 失败 → C0() 备选文本
  7. 如果滚动失败 → k().findOneByCombine(B0/C0)
  8. click() 点击进入耗电管理
  9. g.h(30) 进度
```

#### case 1 (耗电管理) 异步任务流程

```
vendor u.java case 1:
  1. l0() 确认窗口
  2. g.h(40) 进度
  3. G() 激活根节点
  4. s0() 允许自启动 → g.h(50)
  5. t0() 允许关联启动 → g.h(60)
  6. r0() 完全允许后台 → g.h(70)
  7. u0() 双应用保活流程
```

#### case 2 (对话框) 异步任务流程

```
vendor u.java case 2:
  1. j0() 确认窗口
  2. g.h(80) 进度
  3. G() 激活根节点
  4. k().findOneByCombineLoop(d0()) 查找允许按钮 (循环重试)
  5. click() 点击允许
  6. g.h(90) 进度
```

#### case 3 (自启动管理) 异步任务流程

```
vendor u.java case 3:
  1. m0() 确认窗口
  2. G() 激活根节点
  3. 根据 keepAliveType 选择包名
  4. Q() 获取滚动视图
  5. CombineFilterWithChild(K(), H(包名)) 构建过滤器
  6. scrollForwardUtil 或 findOneByCombineWithChild 查找
  7. R(row, 5) Switch 坐标点击
  8. 如果 checked → f702t.set(true)
```

### D.2 o/n.java (HuaweiEngine) 状态机

#### 状态常量

| 状态 | 值 | 对应 case | 处理方法 |
|------|---|-----------|---------|
| ST_HW_SETTINGS | `"keepAliveInHwSettings"` | 0 | 华为设置 → 查找"应用和通知" → 点击 |
| ST_APP_NOTIF | `"keepAliveInAppAndNotification"` | 1 | 应用和通知 → 查找"启动管理" → 点击 |
| ST_STARTUP | `"keepAlvieInStartupAppControl"` | 2 | 启动管理 → r0() 滚动查找 → 操作 Switch |
| ST_DIALOG | `"keepAliveInAlertDialog"` | 3 | 对话框 → 操作 3 个 CheckBox → 点击确认 |
| (超时) | — | 4 (default) | Z() 结束引擎 |

> 注意: `keepAlvieInStartupAppControl` 中 "Alvie" 是 vendor 原始拼写错误，必须保留!

#### 状态转换矩阵

```
当前窗口 → 检测方法 → 移除的状态 → 添加的状态 → 异步任务
─────────────────────────────────────────────────────────
j0() 华为设置    → remove(APP_NOTIF, STARTUP, DIALOG) → add(HW_SETTINGS) → m(this, 0)
i0() 应用和通知  → remove(HW_SETTINGS, STARTUP, DIALOG) → add(APP_NOTIF)  → m(this, 1)
k0() 启动管理    → remove(HW_SETTINGS, APP_NOTIF, DIALOG) → add(STARTUP)  → m(this, 2)
h0() 对话框      → remove(HW_SETTINGS, APP_NOTIF, STARTUP) → add(DIALOG)  → m(this, 3)
```

#### case 0 (华为设置) 异步任务流程

```
m.java case 0 (反编译失败, 从日志推断):
  1. j0() 确认窗口
  2. G() 激活根节点
  3. Q() 获取滚动视图
  4. e0() 查找"应用和通知"文本 (setPrefix 匹配)
  5. click() 点击进入
```

#### case 1 (应用和通知) 异步任务流程

```
m.java case 1 (反编译失败, 从日志推断):
  1. i0() 确认窗口
  2. G() 激活根节点
  3. Q() 获取滚动视图
  4. g0() 查找"应用启动管理"文本
  5. click() 点击进入启动管理
```

#### case 2 (启动管理) 异步任务流程 — r0()

```
n.java r0() 行 247-358:
  1. k0() 确认窗口
  2. g.h(50) 进度
  3. 判断目标: UNKNOWN→MAIN_APP / MAIN_APP→BACKUP_APP / 其他→t0()+Z()
  4. G() 激活根节点
  5. Q() 获取滚动视图
  6. 主应用: scrollForwardUtil(H(x0())) → scrollBackwardUtil
  7. findParentUtilCombine(L()) 查找 clickable 行
  8. findOneByCombine(a0()) 查找 Switch
  9. Switch checked=true → click() → 等待对话框
  10. Switch checked=false → 标记完成 → 递归 r0() 或 t0()+Z()
```

#### case 3 (对话框) 异步任务流程

```
m.java case 3 (反编译失败, 从 n.java 字段推断):
  1. h0() 确认窗口
  2. G() 激活根节点
  3. 操作 3 个 Switch/CheckBox:
     - b0() 允许自启动
     - d0() 允许关联启动
     - c0() 允许后台运行
  4. l0() 查找确认按钮 → click()
```

---

## E. 文件清单

### E.1 要修改的文件

| 文件 | Phase | 修改内容 |
|------|-------|---------|
| `HuaweiEngine.java` | 1,2,3,4 | 窗口检测对齐 + 字段补充 + r0() 重写 + t0()/Z() 对齐 |
| `OppoEngine.java` | 5,6 | Switch 操作对齐 (CombineFilterWithChild) + u0()/D0()/Z() 对齐 |
| `AutoEngine.java` | 3 | 可能需要添加 `findOneByCombineWithChild` 辅助方法 |

### E.2 要新建的测试文件

| 文件 | Phase | 测试数量 |
|------|-------|---------|
| `HuaweiEngineWindowMatchTest.java` | 1 | 10 (修改现有) |
| `HuaweiEngineFieldsTest.java` | 2 | 7 (新建) |
| `HuaweiEngineStartupControlTest.java` | 3 | 8 (新建) |
| `HuaweiEngineDialogTest.java` | 4 | 5 (新建) |
| `OppoEngineSwitchOperationTest.java` | 5 | 9 (新建) |
| `OppoEngineCompletionTest.java` | 6 | 8 (新建) |

**测试总数**: 47 个测试用例

### E.3 验证命令

```bash
# Phase 1: 窗口检测
cd /home/code/php/project/full-package/android
./gradlew test --tests "com.vendor.rat.auto.engine.vendor.HuaweiEngineWindowMatchTest"

# Phase 2: 字段 + 状态机
./gradlew test --tests "com.vendor.rat.auto.engine.vendor.HuaweiEngineFieldsTest"
./gradlew test --tests "com.vendor.rat.auto.engine.vendor.HuaweiEngineStateMachineTest"

# Phase 3: 启动管理操作
./gradlew test --tests "com.vendor.rat.auto.engine.vendor.HuaweiEngineStartupControlTest"

# Phase 4: 对话框 + 保存状态
./gradlew test --tests "com.vendor.rat.auto.engine.vendor.HuaweiEngineDialogTest"

# Phase 5: OppoEngine Switch 操作
./gradlew test --tests "com.vendor.rat.auto.engine.vendor.OppoEngineSwitchOperationTest"

# Phase 6: OppoEngine 完成流程
./gradlew test --tests "com.vendor.rat.auto.engine.vendor.OppoEngineCompletionTest"

# 全量测试
./gradlew test

# 编译检查
./gradlew compileDebugJavaWithJavac
```

### E.4 执行顺序 (依赖关系)

```
Phase 1 (HuaweiEngine 窗口检测)
  ↓
Phase 2 (HuaweiEngine 字段+状态机)
  ↓
Phase 3 (HuaweiEngine r0() 启动管理) ← 依赖 Phase 1+2
  ↓
Phase 4 (HuaweiEngine 对话框+保存) ← 依赖 Phase 2
  ↓
Phase 5 (OppoEngine Switch 操作) ← 独立, 可与 Phase 3 并行
  ↓
Phase 6 (OppoEngine 完成流程) ← 依赖 Phase 5
```

### E.5 风险项

| 风险 | 影响 | 缓解措施 |
|------|------|---------|
| `o/m.java` 反编译失败 | HuaweiEngine case 0/1/3 逻辑不确定 | 基于日志+字段推断; 真机验证 |
| `findOneByCombineWithChild` 不存在 | OppoEngine Switch 操作无法对齐 | 在 UiNode 中实现该方法 |
| `setPrefix` vs `setContains` 差异 | HuaweiEngine e0() 匹配模式不同 | 在 StringCondition 中添加 prefix 支持 |
| 搜索框逻辑移除 | HuaweiEngine 真机可能需要搜索框 | 保留为 fallback, vendor 优先用滚动 |

