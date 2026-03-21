# AOSP 引擎 TDD 复刻计划 (含三星设备支持)

## Context

**Vendor 没有三星专用引擎。** 三星设备使用 AOSP 通用引擎 `o/g.java` (316行)。

当前状态:
- `SamsungEngine.java` (328行) — **自创**，无 vendor 源码对标，应删除
- `AospKeepAliveEngine.java` (302行) — 已有框架，但缺陷严重

Replica `AospKeepAliveEngine.java` 需要从当前的半成品完全重写为对标 `o/g.java` 的完整实现。

---

## A. Vendor vs Replica 差异总表

### A.1 架构对比

| 维度 | Vendor o/g.java | Replica AospKeepAliveEngine | 差距 |
|------|----------------|---------------------------|------|
| 继承 | `extends o.c` | `extends AutoEngine` | ✅ 等价 |
| 构造参数 | `super(k0(), "com.android.settings")` | `super(createAllListenWindows(), SETTINGS)` | ✅ 正确 |
| 超时 | 30 秒 | 30 秒 | ✅ 一致 |
| 字段数 | 4 | 4 | ✅ 一致 |
| ListenWindow | 8 个 (含主/备份包名 matchs) | 4 个 (无 c.J()，无主/备份区分) | ❌ 缺 4 个 |
| 状态机 | 2 状态 `u()` + scheduler 任务 | `onWindowMatched` 回调 (无任务执行) | ❌ 无实际处理 |
| 事件处理 | `super.u()` 电池对话框 | 无 | ❌ 缺失 |
| 任务处理 | case 0 (App详情) + case 1 (耗电管理) | TODO 注释,无实现 | ❌ 完全缺失 |
| l0(root) | 反编译失败 (252 条指令) | 无 | ❌ 需重建 |
| finish() | 完整清理 (X+h(100)+P().x+保存+遮罩+策略) | 简化 (只有 shutdownNow+clear) | ❌ 不完整 |
| n0(String) | PowerControlStateVO 持久化 | 日志占位 | ⚠️ |
| equals/hashCode | 未定义 | 未定义 | — |

### A.2 方法逐项对比

| Vendor 方法 | 行号 | Replica 方法 | 状态 | 差距描述 |
|-------------|------|-------------|------|----------|
| `k0()` | 118-129 | `createAllListenWindows()` | ❌ | vendor 8个含matchs, replica 4个无matchs无c.J() |
| `u()` | 285-316 | `onWindowMatched()` | ❌ | 架构不同,无 super.u(), 无 scheduler 任务 |
| `Z()` | 176-211 | `finish()` | ❌ | 缺 X()/h(100)/P().x()/保存/遮罩/策略 |
| `i0()` | 226-242 | `isInAppDetailWindow()` | ⚠️ | 方向正确但 vendor 根据 keepAliveType 动态选包名 |
| `h0()` | 213-224 | `isInBatteryManageWindow()` | ✅ | 对齐 |
| `l0(root)` | 244-258 | *(缺失)* | ❌ | 反编译失败,需重建 |
| `n0(String)` | 260-283 | `savePowerControlState()` | ⚠️ | 日志占位,无实际持久化 |
| case 0 (f(this,0)) | — | *(缺失)* | ❌ | App详情处理 |
| case 1 (f(this,1)) | — | *(缺失)* | ❌ | 耗电管理处理 |
| case 2 (f(this,2)) | — | *(缺失)* | ❌ | 超时=finish() (构造函数已有) |

### A.3 ListenWindow 对比

| # | Vendor k0() | Replica | 状态 |
|---|------------|---------|------|
| 0 | `c.J()` — com.android.settings / Dialog | *(缺失)* | ❌ |
| 1 | `e0(主包名)` — InstalledAppDetailsTop + H(主) | createAppDetailWindow(null) 无matchs | ❌ |
| 2 | `e0(备包名)` — InstalledAppDetailsTop + H(备) | *(缺失)* | ❌ |
| 3 | `m0(主包名)` — SpaActivity + H(主) | createSpaActivityWindow(null) 无matchs | ❌ |
| 4 | `m0(备包名)` — SpaActivity + H(备) | *(缺失)* | ❌ |
| 5 | `j0(主包名)` — FrameLayout + H(主) | createFrameLayoutWindow(null) 无matchs | ❌ |
| 6 | `j0(备包名)` — FrameLayout + H(备) | *(缺失)* | ❌ |
| 7 | `d0()` — SubSettings | createSubSettingsWindow() | ✅ |

---

## B. TDD Phase 分解

### Phase 1: ListenWindow 4→8 + 构造函数修正

**目标**: 补全 8 个 ListenWindow (含 c.J() 和主/备份 matchs)

#### 1.1 测试

文件: `AospEngineWindowMatchTest.java` (新建)

```
testWindowMatchers_totalCount_is8

testWindowMatchers_batteryDialog_matches         // c.J()
testWindowMatchers_installedAppDetails_matches   // e0(主)
testWindowMatchers_installedAppDetails_backup    // e0(备)
testWindowMatchers_spaActivity_matches           // m0(主)
testWindowMatchers_spaActivity_backup            // m0(备)
testWindowMatchers_frameLayout_matches           // j0(主)
testWindowMatchers_frameLayout_backup            // j0(备)
testWindowMatchers_subSettings_matches           // d0()
```

测试数: 9

---

### Phase 2: 事件处理重写 + 任务处理 case 0/1

**目标**: 重写为 `onAccessibilityEvent()` 状态机，实现 case 0 (App详情) 和 case 1 (耗电管理)

#### 2.1 测试

文件: `AospEngineStateMachineTest.java` (新建)

```
// 窗口检测
testI0_installedAppDetails_matches
testI0_spaActivity_matches
testI0_frameLayout_matches
testI0_wrongWindow_returnsFalse
testH0_subSettings_matches
testH0_wrongWindow_returnsFalse

// 事件处理
testOnEvent_i0Match_enqueuesAppDetail
testOnEvent_h0Match_enqueuesAppBattery
testOnEvent_completed_skips
testOnEvent_callsCheckBatteryDialog
testOnEvent_clearsOtherState

// case 0: App详情
testHandleAppDetail_findsBatteryText_clicks
testHandleAppDetail_findsPowerText_fallback
testHandleAppDetail_findsUsePowerText_fallback

// case 1: 耗电管理 (l0 重建)
testHandleAppBattery_findsUnrestricted_clicks
testHandleAppBattery_findsAllowBackground_fallback
testHandleAppBattery_setsAllowFullBackground
```

测试数: 17

---

### Phase 3: finish() 对齐 + 状态持久化 + 清理

**目标**: 对齐 vendor Z() 完整流程，删除 SamsungEngine.java

#### 3.1 测试

```
testFinish_callsX_pause
testFinish_callsUpdateProgress100
testFinish_callsSaveState
testFinish_shutdownScheduler
testFinish_clearsStateQueue
testFinish_removesBlackScreen
testFinish_notifiesStrategy

testSaveState_setsAllFields
testEquals_sameType_returnsTrue
testEquals_differentType_returnsFalse
```

测试数: 10

---

## C. 文件清单

| 操作 | 文件 | 说明 |
|------|------|------|
| 重写 | `AospKeepAliveEngine.java` | 完整对齐 o/g.java |
| 删除 | `SamsungEngine.java` | 无 vendor 源码，三星用 AOSP 引擎 |
| 新建 | `AospEngineWindowMatchTest.java` | 9 测试 |
| 新建 | `AospEngineStateMachineTest.java` | 17 测试 |

**总计: 36 个测试用例**

---

## D. 执行顺序

```
Phase 1 (LW 4→8) ──→ Phase 2 (状态机+任务) ──→ Phase 3 (finish+清理)
                                                        │
                                                        └──→ 删除 SamsungEngine.java
```

---

## E. Vendor 源码行号索引

| 方法 | Vendor 行号 | Replica 方法 | Phase |
|------|------------|-------------|-------|
| 构造函数 | 40-51 | constructor | 1 |
| `k0()` | 118-129 | buildWindowMatchers | 1 |
| `i0()` | 226-242 | isInAppDetailWindow | 2 |
| `h0()` | 213-224 | isInBatteryManageWindow | 2 |
| `u()` | 285-316 | onAccessibilityEvent | 2 |
| f(this,0) | — | handleAppDetail | 2 |
| f(this,1) | — | handleAppBattery | 2 |
| `l0(root)` | 244-258 | performBatteryOptimization (重建) | 2 |
| `n0(String)` | 260-283 | saveState | 3 |
| `Z()` | 176-211 | finish | 3 |
| `b0()` | 53-61 | buildAllowBackgroundFilter | 2 |
| `c0()` | 63-72 | buildBatteryFilter | 2 |
| `f0()` | 88-97 | buildPowerFilter | 2 |
| `g0()` | 99-108 | buildUsePowerFilter | 2 |
| `o0()` | 139-174 | buildUnrestrictedFilter | 2 |

## F. l0(root) 反编译失败分析

252 条指令, 与传音 `o0()` (254 条指令) 几乎相同。两者共享 COMMON_* 配置 Key。

**从上下文推断逻辑 (与传音 o0 一致)**:
1. 查找"不受限"文本 (o0() OR 匹配: 不受限/无限制/已取消限制)
2. 如果找到 → scrollView 中定位 → 检查是否已选中
3. 如果未选中 → 用 R() 坐标点击选中
4. 如果没找到 → 查找"允许后台使用" (b0()) 文本
5. 操作 Switch/RadioButton
6. 返回操作后的 UiObject

实现方案: 复用 TranssionEngine.performBatteryOptimization() 的逻辑。
