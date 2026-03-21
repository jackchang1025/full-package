# PairAccessibilityDelegate 深度对比审计

> 审计日期: 2026-03-22
> Vendor 源: `o/a0.java` (2003 行, extends `e`)
> Replica 源: `android/app/src/main/java/com/vendor/rat/auto/engine/PairAccessibilityDelegate.java` (317 行)

---

## 1. 总体差距

| 指标 | Vendor | Replica | 差距 |
|------|--------|---------|------|
| 总行数 | 2003 | 317 | Replica 缺 84% |
| ListenWindow 数量 | ~14 | 4 | ❌ 缺 10 个 |
| 状态机分支数 | ~8 | 3 (2 为空) | ❌ 严重不完整 |
| 静态 Filter/Window 工厂方法 | ~35 | ~5 | ❌ 缺 ~30 个 |
| TODO 标记 | — | 5 处 | ❌ 主要逻辑未实现 |

---

## 2. 字段对齐

| Vendor 字段 | Replica 字段 | 状态 |
|------------|-------------|------|
| `f601n` (ScheduledExecutorService) | `pairScheduler` | ✅ 一致 |
| `f602o` (ConcurrentLinkedQueue) | `processedActions` | ✅ 一致 |
| `f603p` (AtomicReference pairState) | `pairState` | ✅ 一致 |
| `q` (ReentrantLock) | `pairLock` | ✅ 一致 |
| `f604r` (AtomicBoolean finished) | `pairFinished` | ✅ 一致 |
| `f605s` (usbDebuggingEnabled) | `usbDebuggingEnabled` | ✅ 一致 |
| `f606t` (wirelessDebuggingEnabled) | `wirelessDebuggingEnabled` | ✅ 一致 |
| `f607u` (pairCompleted) | `pairCompleted` | ✅ 一致 |

字段层面完全对齐。

---

## 3. 构造函数对比

| 行为 | Vendor | Replica | 状态 |
|------|--------|---------|------|
| 超时: Oppo 180s / 其他 120s | `com.guard.wallet.utils.e.m()` | `DeviceUtils.isOppo()` | ✅ 等价 |
| 主超时 schedule | ✅ | ✅ | ✅ |
| 30s 重试 schedule | `new z(this, 1)` | `handleRetry()` (空实现) | ❌ 重试体未实现 |

---

## 4. ListenWindows (E0 vs createListenWindows) 差距

### Vendor E0() 完整列表 (~14 个)

| # | 包名 | Activity/className | EventTypes | 状态 |
|---|------|-------------------|------------|------|
| 1 | settings | `DevelopmentSettingsDashboardActivity` | 32, 16384 | ✅ Replica 有 |
| 2 | settings | `DevelopmentSettingsActivity` | 32, 16384 | ✅ Replica 有 |
| 3 | settings | `I()` 条件窗口 (无线调试相关) | 32, 16384 | ❌ Replica 缺 |
| 4 | settings | `SubSettings` | 32, 16384 | ✅ Replica 有 |
| 5 | settings | `s0()` 条件窗口 | 32, 16384 | ❌ Replica 缺 |
| 6 | settings | `com.hihonor.settingslib.SubSettings` | 32, 16384 | ✅ Replica 有 |
| 7 | settings | `android.widget.FrameLayout` | 32, 16384 | ❌ Replica 缺 |
| 8 | settings | `Y0()` 条件窗口 | — | ❌ Replica 缺 |
| 9 | settings | `Z0()` 条件窗口 | — | ❌ Replica 缺 |
| 10 | systemui | `android.app.Dialog` | 32, 16384, 1 | ❌ Replica 缺 |
| 11 | settings | `null` (catch-all) | 32, 16384 | ❌ Replica 缺 |
| 12 | settings | `B0()` 条件窗口 | — | ❌ Replica 缺 |
| 13 | settings | `y0()` 条件窗口 | — | ❌ Replica 缺 |
| 14 | settings | `z0()` 条件窗口 | — | ❌ Replica 缺 |
| 15 | settings | `A0()` 条件窗口 | — | ❌ Replica 缺 |
| 16 | settings | `M0()` | — | ❌ Replica 缺 |
| 17 | settings | `I0()` | — | ❌ Replica 缺 |

**关键遗漏**: `systemui / android.app.Dialog` 窗口是配对对话框的监听入口，eventType 包含 `TYPE_VIEW_CLICKED(1)`，Replica 完全缺失，导致配对码输入对话框无法被捕获。

---

## 5. 事件处理状态机 (H() vs onAccessibilityEvent) 差距

### 5.1 Vendor H() 逻辑 (从 Dalvik 字节码还原)

```
1. 检查 L() (是否为开发者选项窗口)
2. processedActions 去重 ("pairInDevOption")
3. 找 scrollable 视图
4. 调用 G0() 查找无线调试入口文本节点
5. 找 clickable 父节点 (T() filter)
6. 调用 S() — 若 USB 调试禁用节点可见则通过位置偏移点击无线调试
7. 点击后设置状态 PAIR_DEPT_PAIR_LEAVE_DEV_OPT
8. 失败路径: 从 processedActions 移除 key (允许重试)
```

### 5.2 Replica onAccessibilityEvent 实现状态

| 状态分支 | Vendor | Replica | 状态 |
|---------|--------|---------|------|
| UNKNOWN / ENTER_DEV_OPT | H() 完整实现 | `handleDevOptionWindow()` 部分实现 | ⚠️ 见 5.3 |
| FIND_WIRELESS / ENTER_WIRELESS | 无线调试开关检测 + R() Toggle | 空 TODO | ❌ 未实现 |
| PAIR_DIALOG | 配对码输入 + 点击配对 | `handlePairDialog()` 空 TODO | ❌ 未实现 |
| PAIR_FAILED 重试 | B0/y0/z0/A0 窗口触发重试 | 无 | ❌ 未实现 |
| USB_DEBUGGING 检测 | f605s 标志驱动 S() 路径 | 无 | ❌ 未实现 |
| 权限监控弹窗处理 | F0() CombineFiltersWithOr | 无 | ❌ 未实现 |

### 5.3 handleDevOptionWindow() 差距

| 步骤 | Vendor | Replica | 状态 |
|------|--------|---------|------|
| 找 scrollable | ✅ | ✅ | ✅ |
| 找无线调试文本节点 (G0) | 多条件 OR 查找 | `findWirelessDebugEntry()` | ⚠️ 需验证条件数量 |
| 找 clickable 父节点 | `findParentUtilCombine(T())` | `findParentUntil(createClickableFilter())` | ✅ 等价 |
| USB 禁用节点位置偏移路径 (S()) | ✅ 有 | ❌ 缺 | ❌ |
| 点击后状态设置 | `PAIR_DEPT_PAIR_LEAVE_DEV_OPT` | `STATE_FIND_WIRELESS` | ⚠️ 状态名不同 |
| 失败时从 processedActions 移除 | ✅ (允许重试) | ❌ 不移除 (阻止重试) | ❌ 重试机制破坏 |

---

## 6. 缺失的静态工厂方法

以下 Vendor 方法在 Replica 中完全缺失:

### Filter 工厂

| Vendor 方法 | 功能描述 | 优先级 |
|------------|---------|--------|
| `G0()` | 查找无线调试入口文本 (多 OR 条件) | P0 |
| `F0()` | 权限监控弹窗过滤器 (CombineFiltersWithOr) | P0 |
| `T()` | clickable=true 通用过滤器 | P0 |
| `R0()` | USB 安装允许文本 (`PAIR_ALLOW_USB_INSTALL_TEXT`) | P1 |
| `S0()` | USB 安全文本 (`PAIR_USB_SECURITY_TEXT`) | P1 |
| `Q0()` | Switch 控件过滤器 | P1 |
| `U()` | button1 id 过滤器 | P1 |

### ListenWindow 工厂

| Vendor 方法 | 功能描述 | 优先级 |
|------------|---------|--------|
| `I()` | 无线调试界面监听窗口 | P0 |
| `M0()`, `I0()` | 无条件附加窗口 | P0 |
| `B0()` | PAIR_FAILED 文本窗口 | P0 |
| `A0()` | PAIR_FAILED_4 文本窗口 | P0 |
| `Y0()`, `Z0()` | 配对相关条件窗口 | P1 |
| `y0()`, `z0()` | 失败重试窗口 | P1 |
| `U0()` — `x0()` | 配对码/设备名输入相关窗口 | P1 |
| `s0()` | 厂商适配条件窗口 | P2 |

### 核心静态逻辑方法

| Vendor 方法 | 功能描述 | 优先级 |
|------------|---------|--------|
| `R()` | 无线调试 Switch 勾选循环 (最多 10 次, AtomicInteger) | P0 |
| `S()` | 依 USB 禁用节点位置偏移点击无线调试 (Rect 偏移 -200) | P0 |
| `H()` | 开发者选项窗口完整处理 (含 S() 路径) | P0 |
| `h0()` | Switch 点击并验证 CheckedResult | P0 |

---

## 7. 状态常量对比

| Vendor (r.g enum) | Replica 常量 | 状态 |
|-------------------|-------------|------|
| `PAIR_DEPT_UNKNOWN` | `STATE_UNKNOWN` | ✅ |
| `PAIR_DEPT_PAIR_LEAVE_DEV_OPT` | `STATE_FIND_WIRELESS` | ⚠️ 语义有偏差 |
| `PAIR_DEPT_WIRELESS_DEBUG_WIN` | `STATE_ENTER_WIRELESS` | ✅ 等价 |
| `PAIR_DEPT_WIRELESS_DEBUG_PAIR_WIN` | `STATE_PAIR_DIALOG` | ✅ 等价 |
| `PAIR_DEPT_TIMEOUT` | `STATE_TIMEOUT` | ✅ |
| `PAIR_DEPT_FINISHED` | `STATE_FINISHED` | ✅ |

---

## 8. 优先级行动项

### P0 — 阻断功能, 必须立即补全

1. **实现 `handleWirelessDebugWindow()`** — 对应 Vendor `R()` + Switch Toggle 逻辑 (FIND_WIRELESS/ENTER_WIRELESS 状态分支)
2. **实现 `handlePairDialog()`** — 配对码输入 + 点击配对按钮
3. **补全 ListenWindow: `I()`, `M0()`, `I0()`** — 无线调试界面和通用 catch-all 窗口
4. **补全 ListenWindow: `B0()`, `A0()`** — PAIR_FAILED 重试入口
5. **补全 `systemui/android.app.Dialog` ListenWindow** (eventType 含 TYPE_VIEW_CLICKED=1)
6. **修复 processedActions 失败路径** — 失败时需 `remove("pairInDevOption")` 允许重试
7. **实现 `S()` USB 禁用节点位置偏移路径** — 华为/荣耀等厂商必须依赖此路径
8. **实现 `handleRetry()`** — 30s 定时重试逻辑

### P1 — 降低成功率

9. **补全 `G0()` OR 条件** — 确认 Replica `findWirelessDebugEntry()` 覆盖所有 Vendor 文本 key
10. **实现 `F0()` 权限监控弹窗处理** — 防止权限弹窗阻断流程
11. **补全 PAIR_FAILED 重试窗口** (`Y0`, `Z0`, `y0`, `z0`)
12. **实现 `h0()` CheckedResult** — Switch 点击验证

### P2 — 厂商适配

13. **`s0()` 条件窗口** — 特定厂商 settings 变体
14. **配对码输入相关窗口** (`U0` — `x0` 系列)

---

## 9. 结论

Replica 的 `PairAccessibilityDelegate` 在字段和构造函数层面与 Vendor 保持一致，但核心业务逻辑实现率约为 **16%**。主要功能 (无线调试开关、配对码对话框、失败重试) 均为空存根。在实设备上该代理无法完成 ADB 无线调试配对流程。补全 P0 项后预计可达到基本可用状态。
