# helper/ 包逆向重构实施计划

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 将 `com.guard.wallet.helper/` 包下 18 个混淆 Java 文件（1,860 行）重命名为语义化名称，更新所有调用者，保持编译通过。

**Architecture:**
- helper/ 包已经在正确位置（`com.guard.wallet.helper`），只需类名重命名，无需移包
- 按依赖关系分 3 批：先独立类，再被依赖的父类，最后高引用核心类
- 每批用并行 agent 执行，编译验证后进入下一批

**Tech Stack:** Java 8+, Android AccessibilityService, Gradle 8.5

**参考源码:** `androidReverseEngineering/src/com/guard/wallet/helper/`

---

## 依赖分析

```
g (BlockViewManager) ─── uses ──→ e (WindowAttachListener), f (DelayedRunnable)
n (NotificationDialog) ── uses ──→ j (PositiveClickListener), k (DismissListener),
                                   l (NegativeClickListener), m (DialogRemoveTask)
o (OverlayViewHelper) ── uses ──→ f (DelayedRunnable)
r (AutomationHelper)  ── uses ──→ f (DelayedRunnable), p (NodePredicate), q (TouchDragListener)
```

`LockCipherHelper$1.java` 已命名，不处理。

## 完整文件映射表

| 旧类名 | 新类名 | 行数 | 外部引用 | 说明 |
|--------|--------|------|---------|------|
| a | NodeBoundsHelper | 109 | 8 | 节点坐标/边界计算工具 |
| b | DelegateFilterPredicate | 38 | 1 | Predicate — delegate 过滤 |
| c | StringLogConsumer | 10 | 0 | Consumer — 字符串日志消费 |
| d | ListenWindowHelper | 36 | 2 | 监听窗口抽象辅助类 |
| e | WindowAttachListener | 48 | 0 | ViewTreeObserver 窗口附加监听 |
| f | DelayedRunnable | 30 | 3 | 延迟执行 Runnable |
| g | BlockViewManager | 253 | 145 | 遮罩窗口管理器（最高引用量） |
| h | BlockViewRemoveTask | 20 | 0 | 遮罩移除延迟任务 |
| i | DialogHelper | 31 | 1 | 对话框抽象辅助类 |
| j | PositiveClickListener | 38 | 2 | 对话框确认按钮监听 |
| k | DismissListener | 21 | 1 | 对话框关闭监听 |
| l | NegativeClickListener | 18 | 0 | 对话框取消按钮监听 |
| m | DialogRemoveTask | 36 | 0 | 对话框移除 Runnable |
| n | NotificationDialog | 121 | 3 | 系统级通知对话框管理 |
| o | OverlayViewHelper | 348 | 24 | 悬浮窗/遮罩视图管理 |
| p | NodePredicate | 74 | 0 | 节点匹配 Predicate |
| q | TouchDragListener | 108 | 0 | 触摸拖拽监听 |
| r | AutomationHelper | 450 | 17 | 无障碍自动化核心（滑动/点击/事件状态） |

---

## Task 1: 独立小类（零/低外部引用，无内部依赖）

迁移 10 个独立类：a, b, c, d, e, f, h, i, l, p

**Files (全部在 `com/guard/wallet/helper/`):**
- Rename: `a.java` → `NodeBoundsHelper.java`
- Rename: `b.java` → `DelegateFilterPredicate.java`
- Rename: `c.java` → `StringLogConsumer.java`
- Rename: `d.java` → `ListenWindowHelper.java`
- Rename: `e.java` → `WindowAttachListener.java`
- Rename: `f.java` → `DelayedRunnable.java`
- Rename: `h.java` → `BlockViewRemoveTask.java`
- Rename: `i.java` → `DialogHelper.java`
- Rename: `l.java` → `NegativeClickListener.java`
- Rename: `p.java` → `NodePredicate.java`

- [ ] **Step 1: Agent 1 — 重命名 a, b, c, d, e (5 个)**
每个文件：读取完整内容 + 参考 androidReverseEngineering 对应文件，创建新命名文件，更新内部交叉引用，删除旧文件 (.java + .pending)。

- [ ] **Step 2: Agent 2 — 重命名 f, h, i, l, p (5 个)**
同上模式。

- [ ] **Step 3: Agent 3 — 更新外部调用者**
搜索并更新所有 helper/ 外部对这 10 个类的引用。主要涉及：
- `com/guard/wallet/delegate/` 下的 delegate 文件 (a 的引用)
- `com/guard/wallet/service/` (d 的引用)
- `com/guard/wallet/engine/` (f 的引用)
- pending 文件

- [ ] **Step 4: 编译验证**
```bash
cd vendor-replica && ./gradlew compileDebugJavaWithJavac
```

- [ ] **Step 5: Commit**
```bash
git add -A && git commit -m "refactor: rename 10 independent helper classes"
```

---

## Task 2: 对话框 + 遮罩组（n, j, k, m + g, q）

这些类有内部依赖关系：
- n (NotificationDialog) 依赖 j, k, l(已改), m
- g (BlockViewManager) 依赖 e(已改), f(已改)
- q (TouchDragListener) 被 r 使用

**Files:**
- Rename: `j.java` → `PositiveClickListener.java`
- Rename: `k.java` → `DismissListener.java`
- Rename: `m.java` → `DialogRemoveTask.java`
- Rename: `n.java` → `NotificationDialog.java`
- Rename: `g.java` → `BlockViewManager.java`
- Rename: `q.java` → `TouchDragListener.java`

- [ ] **Step 1: Agent 1 — 重命名 j, k, m, n (对话框组 4 个)**
n.java 内部引用 `new j(`, `new k(`, `new l(`, `new m(` 需要更新为新类名。

- [ ] **Step 2: Agent 2 — 重命名 g (253 行，145 外部引用) + q**
g.java 是引用量最大的 helper 类（145 次外部引用），覆盖 20+ 个外部文件。
内部引用 `new f(` → `new DelayedRunnable(`，`e.class` → `WindowAttachListener.class`。

- [ ] **Step 3: Agent 3 — 更新全部外部调用者**
重点：`helper.g.a()`, `helper.g.c()`, `helper.g.e()` 在 delegate/、engine/、adb/、thread/ 中大量调用。
注意区分 `com.guard.wallet.utils.g` (不同类) 和 `com.guard.wallet.helper.g` (本次目标)。

- [ ] **Step 4: 编译验证**
```bash
cd vendor-replica && ./gradlew compileDebugJavaWithJavac
```

- [ ] **Step 5: Commit**
```bash
git add -A && git commit -m "refactor: rename dialog/blockview/touch helper classes"
```

---

## Task 3: 核心类（o, r）

最后处理两个最大的核心 helper：
- o (OverlayViewHelper, 348 行, 24 引用) — 悬浮窗/遮罩视图
- r (AutomationHelper, 450 行, 17 引用) — 无障碍自动化核心

**Files:**
- Rename: `o.java` → `OverlayViewHelper.java`
- Rename: `r.java` → `AutomationHelper.java`

- [ ] **Step 1: Agent 1 — 重命名 o.java (348 行)**
内部引用 `new DelayedRunnable(` (已改)。
更新外部调用者：delegate/、engine/、service/ 中 ~24 处引用。
注意：`helper.o` 不要和 `com.guard.wallet.delegate.MediaProjectionDelegate` (原 `o.o`) 混淆。

- [ ] **Step 2: Agent 2 — 重命名 r.java (450 行)**
内部引用 `new DelayedRunnable(`, `new NodePredicate(`, `new TouchDragListener(`。
更新外部调用者：delegate/、engine/、service/、thread/ 中 ~17 处引用。
包含内部枚举 `r.d` — 需确认是否需要提取或保持内部类。

- [ ] **Step 3: 编译验证**
```bash
cd vendor-replica && ./gradlew compileDebugJavaWithJavac
```

- [ ] **Step 4: Commit**
```bash
git add -A && git commit -m "refactor: rename overlay and automation helper classes"
```

---

## Task 4: 清理验证

- [ ] **Step 1: 确认无残留混淆类名**
```bash
ls com/guard/wallet/helper/*.java | grep -E '^[a-r]\.java$'
```
Expected: 只剩 `LockCipherHelper$1.java` 和新命名文件

- [ ] **Step 2: 确认零残留引用**
```bash
grep -rn 'helper\.[a-r]\b' --include="*.java" | grep -v 'Helper\|Listener\|Predicate\|Consumer\|Runnable\|Dialog\|Manager\|Automation' | head -20
```

- [ ] **Step 3: 完整编译**
```bash
cd vendor-replica && ./gradlew compileDebugJavaWithJavac
```

- [ ] **Step 4: Final commit**
```bash
git add -A && git commit -m "refactor: complete helper/ package rename — 18 files"
```

---

## 风险与注意事项

1. **helper.g 引用量巨大 (145 次)** — Task 2 的核心风险，需要 agent 逐个确认每处 `helper.g.x()` 调用
2. **同名冲突**: `com.guard.wallet.utils.g` 和 `com.guard.wallet.helper.g` 是不同类，grep 时需严格区分
3. **内部枚举**: `r.java` 包含内部枚举 `d`，引用方式为 `helper.r.d`，重命名后变为 `AutomationHelper.d`
4. **LockCipherHelper$1.java** 已命名，不在重构范围内
5. **pending 文件**: 每个 .java 都有对应 .pending，需要同步更新或删除

## 预估规模

| Task | 文件数 | 行数 | Agent 数 |
|------|--------|------|----------|
| Task 1 | 10 | ~430 | 3 |
| Task 2 | 6 | ~477 | 3 |
| Task 3 | 2 | ~798 | 2 |
| Task 4 | 0 | 验证 | 1 |
| **Total** | **18** | **~1,700** | — |
