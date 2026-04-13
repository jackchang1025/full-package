# o/ 包逆向重构实施计划

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 将 `o/` 包下 36 个混淆 Java 文件（14,179 行）重命名为语义化名称，移到正确的子包，更新所有调用者，保持编译通过。

**Architecture:**
- 按继承层次分 6 批（Task）逐步迁移：先基类后子类，先独立类后依赖类
- 每批 3-8 个文件，用 2-3 个并行 agent 执行
- 每批完成后验证 `./gradlew compileDebugJavaWithJavac` 编译通过

**Tech Stack:** Java 8+, Android AccessibilityService, Gradle 8.5

**参考源码:** `androidReverseEngineering/src/o/` (CFR 完整反编译)

---

## 目标包结构

```
com.guard.wallet.delegate/       ← 无障碍 Delegate 类 (已存在，有 AdbBridge 等)
com.guard.wallet.delegate/task/  ← Delegate dispatcher Runnable 类 (新建)
com.guard.wallet.engine/         ← 厂商保活引擎 (新建)
```

## 完整文件映射表

### 基类（2 个）
| 旧 | 新名称 | 目标包 | 行数 | 说明 |
|----|--------|--------|------|------|
| o/e.java | AccessibilityDelegate | delegate | 1214 | 所有 Delegate 的基类 |
| o/c.java | KeepAliveEngine | engine | 710 | 所有厂商引擎的基类 (extends AccessibilityDelegate) |

### 厂商引擎（7 个，全部 extends KeepAliveEngine / o.c）
| 旧 | 新名称 | 目标包 | 行数 | 说明 |
|----|--------|--------|------|------|
| o/g.java | AospKeepAliveEngine | engine | 435 | AOSP/三星/通用 |
| o/n.java | HuaweiEngine | engine | 506 | 华为/荣耀 |
| o/q.java | XiaomiEngine | engine | 625 | 小米/Redmi/POCO |
| o/v.java | OppoEngine | engine | 596 | OPPO/Realme/OnePlus |
| o/i0.java | VivoEngine | engine | 698 | Vivo/iQOO |
| o/e0.java | TranssionEngine | engine | 445 | 传音 Tecno/Itel/Infinix |
| o/g0.java | UseDeviceCredentialDelegate | delegate | 659 | 设备凭证验证 (extends o.e) |

### 无障碍 Delegate（7 个，全部 extends AccessibilityDelegate / o.e）
| 旧 | 新名称 | 目标包 | 行数 | 说明 |
|----|--------|--------|------|------|
| o/a0.java | PairAccessibilityDelegate | delegate | 1994 | ADB 无线配对流程 |
| o/t.java | OpenDevelopmentDelegate | delegate | 708 | 开发者选项开启 |
| o/a.java | ConfirmLockDelegate | delegate | 372 | 锁屏密码确认 |
| o/h.java | ConfirmDeviceCredentialDelegate | delegate | 264 | 设备凭证确认 |
| o/k.java | EnableSecureDelegate | delegate | 211 | 安全写入 |
| o/l.java | GrantPermissionDelegate | delegate | 94 | 权限授予 |
| o/o.java | MediaProjectionDelegate | delegate | 71 | 投屏权限 |
| o/x.java | PackageInstallerDelegate | delegate | 520 | APK 安装 |
| o/i.java | AutoPermissionDelegate | delegate | 525 | 自动权限 (extends o.e) |

### Dispatcher Runnable（11 个）
| 旧 | 新名称 | 目标包 | 行数 | 说明 |
|----|--------|--------|------|------|
| o/d.java | DelegateEventDispatcher | delegate/task | 316 | 事件分发—遍历监听窗口 |
| o/y.java | PairDelegateTask | delegate/task | 570 | PairDelegate dispatcher |
| o/h0.java | VivoDelegateTask | delegate/task | 354 | VivoEngine dispatcher |
| o/p.java | XiaomiDelegateTask | delegate/task | 96 | XiaomiEngine dispatcher |
| o/s.java | WirelessPairTask | delegate/task | 123 | WirelessPair dispatcher |
| o/w.java | PackageInstallerTask | delegate/task | 250 | PackageInstaller dispatcher |
| o/f0.java | DeviceCredentialTask | delegate/task | 179 | DeviceCredential dispatcher |
| o/j.java | EnableSecureTask | delegate/task | 340 | EnableSecure dispatcher |
| o/z.java | AutoEngineTask | delegate/task | 46 | AutoEngine dispatcher |
| o/f.java | PermissionGrantTask | delegate/task | 173 | 权限授予 task |
| o/m.java | ConfirmLockTask | delegate/task | 252 | 锁屏确认 task |
| o/u.java | MediaProjectionTask | delegate/task | 171 | 投屏权限 task |

### 辅助类（5 个）
| 旧 | 新名称 | 目标包 | 行数 | 说明 |
|----|--------|--------|------|------|
| o/b.java | DelegateSyntheticHelper | delegate | 54 | 合成工具方法 |
| o/r.java | ScreenCaptureManager | delegate | 106 | 截屏任务管理 |
| o/c0.java | DelegateUtils | delegate | 50 | Delegate 工具类 |
| o/j0.java | ListenWindowState | delegate | 67 | 监听窗口状态 Serializable |
| o/b0.java | ScreenCaptureTask | delegate/task | 133 | 截屏 Runnable |
| o/d0.java | OpenDevDelegateTask | delegate/task | 252 | 开发者选项 Runnable |

---

## Task 1: 基类迁移（o/e + o/c + 辅助类）

**优先级最高** — 所有子类都依赖这两个基类。

**Files:**
- Move: `o/e.java` → `com/guard/wallet/delegate/AccessibilityDelegate.java`
- Move: `o/c.java` → `com/guard/wallet/engine/KeepAliveEngine.java`
- Move: `o/b.java` → `com/guard/wallet/delegate/DelegateSyntheticHelper.java`
- Move: `o/c0.java` → `com/guard/wallet/delegate/DelegateUtils.java`
- Move: `o/j0.java` → `com/guard/wallet/delegate/ListenWindowState.java`
- Move: `o/r.java` → `com/guard/wallet/delegate/ScreenCaptureManager.java`
- Create: `com/guard/wallet/engine/` (新目录)
- Create: `com/guard/wallet/delegate/task/` (新目录)

**字段/方法重命名要点（o/e AccessibilityDelegate）：**
- 字段 `a` (ConcurrentLinkedQueue) → `delegateQueue`
- 读取 o/e.java 完整内容，参考 androidReverseEngineering/src/o/e.java 确认所有字段
- Override 方法名保持不变（由 Android 框架决定）

**字段/方法重命名要点（o/c KeepAliveEngine）：**
- 继承关系: `extends o.e` → `extends AccessibilityDelegate`
- 读取完整内容确认字段映射

- [ ] **Step 1: 创建目标目录**
```bash
mkdir -p vendor-replica/app/src/main/java/com/guard/wallet/engine
mkdir -p vendor-replica/app/src/main/java/com/guard/wallet/delegate/task
```

- [ ] **Step 2: Agent 1 — 迁移 o/e.java → AccessibilityDelegate.java**
读取 `o/e.java` 和 `androidReverseEngineering/src/o/e.java`，创建新文件：
- 包名改为 `com.guard.wallet.delegate`
- 类名 `e` → `AccessibilityDelegate`
- 内部对 o 包其他类的引用暂时保持全限定名（后续 Task 会更新）
- 重命名关键字段（从 androidReverseEngineering 推断语义）
- 删除旧文件 o/e.java + o/e.java.pending

- [ ] **Step 3: Agent 2 — 迁移 o/c.java → KeepAliveEngine.java + 4 个辅助类**
读取 `o/c.java`, `o/b.java`, `o/c0.java`, `o/j0.java`, `o/r.java` 和对应 androidReverseEngineering 源，创建新文件：
- o/c → `com.guard.wallet.engine.KeepAliveEngine` (extends AccessibilityDelegate)
- o/b → `com.guard.wallet.delegate.DelegateSyntheticHelper`
- o/c0 → `com.guard.wallet.delegate.DelegateUtils`
- o/j0 → `com.guard.wallet.delegate.ListenWindowState`
- o/r → `com.guard.wallet.delegate.ScreenCaptureManager`
- 删除旧文件

- [ ] **Step 4: Agent 3 — 更新外部调用者**
更新所有引用 `o.e`, `o.c`, `o.b`, `o.c0`, `o.j0`, `o.r` 的外部文件：
- `com/guard/wallet/delegate/` 下已有文件 (AdbBridge, EngineHelper, SelectorHelper 等)
- `com/guard/wallet/service/` (MyAccessibilityService, AccessibilityDelegateManager)
- `com/guard/wallet/infra/DelegateRemovePredicate.java`
- `com/guard/wallet/server/handler/` 下相关 handler
- pending 文件
- **注意**: o/ 包内部的子类暂不更新（下一 Task 统一处理）

- [ ] **Step 5: 编译验证**
```bash
cd vendor-replica && ./gradlew compileDebugJavaWithJavac
```
Expected: BUILD SUCCESSFUL

- [ ] **Step 6: Commit**
```bash
git add -A && git commit -m "refactor: migrate o/e,c,b,c0,j0,r to delegate/engine packages"
```

---

## Task 2: 厂商引擎迁移（7 个 extends KeepAliveEngine）

**依赖 Task 1 完成**（KeepAliveEngine 基类已就位）。

**Files:**
- Move: `o/g.java` → `com/guard/wallet/engine/AospKeepAliveEngine.java`
- Move: `o/n.java` → `com/guard/wallet/engine/HuaweiEngine.java`
- Move: `o/q.java` → `com/guard/wallet/engine/XiaomiEngine.java`
- Move: `o/v.java` → `com/guard/wallet/engine/OppoEngine.java`
- Move: `o/i0.java` → `com/guard/wallet/engine/VivoEngine.java`
- Move: `o/e0.java` → `com/guard/wallet/engine/TranssionEngine.java`

- [ ] **Step 1: Agent 1 — 迁移 o/g, o/n, o/q (3 个引擎)**
每个文件：
- 包名 → `com.guard.wallet.engine`
- `extends c` → `extends KeepAliveEngine`
- 内部对 o 包其他类的引用更新为已迁移的名称
- 参考 androidReverseEngineering 确认字段语义
- 删除旧文件 + .pending

- [ ] **Step 2: Agent 2 — 迁移 o/v, o/i0, o/e0 (3 个引擎)**
同上模式处理 OPPO、Vivo、传音引擎。

- [ ] **Step 3: Agent 3 — 更新外部调用者**
搜索并更新所有引用 `o.g`, `o.n`, `o.q`, `o.v`, `o.i0`, `o.e0` 的外部文件。
主要在：
- `com/guard/wallet/delegate/EngineHelper.java`
- `com/guard/wallet/infra/DelegateRemovePredicate.java`
- `com/guard/wallet/service/AccessibilityDelegateManager.java`
- `com/guard/wallet/thread/` 相关线程
- pending 文件

- [ ] **Step 4: 编译验证**
```bash
cd vendor-replica && ./gradlew compileDebugJavaWithJavac
```

- [ ] **Step 5: Commit**
```bash
git add -A && git commit -m "refactor: migrate 6 vendor engines to engine/ package"
```

---

## Task 3: Delegate 子类迁移（8 个 extends AccessibilityDelegate）

**依赖 Task 1 完成**（AccessibilityDelegate 基类已就位）。

**Files:**
- Move: `o/a0.java` → `com/guard/wallet/delegate/PairAccessibilityDelegate.java` (1994 行，最大)
- Move: `o/t.java` → `com/guard/wallet/delegate/OpenDevelopmentDelegate.java`
- Move: `o/g0.java` → `com/guard/wallet/delegate/UseDeviceCredentialDelegate.java`
- Move: `o/a.java` → `com/guard/wallet/delegate/ConfirmLockDelegate.java`
- Move: `o/h.java` → `com/guard/wallet/delegate/ConfirmDeviceCredentialDelegate.java`
- Move: `o/k.java` → `com/guard/wallet/delegate/EnableSecureDelegate.java`
- Move: `o/l.java` → `com/guard/wallet/delegate/GrantPermissionDelegate.java`
- Move: `o/o.java` → `com/guard/wallet/delegate/MediaProjectionDelegate.java`
- Move: `o/x.java` → `com/guard/wallet/delegate/PackageInstallerDelegate.java`
- Move: `o/i.java` → `com/guard/wallet/delegate/AutoPermissionDelegate.java`

- [ ] **Step 1: Agent 1 — 迁移 o/a0 (1994 行) + o/t (708 行)**
最大的两个 Delegate，单独一个 agent 处理。
- `extends e` → `extends AccessibilityDelegate`
- 参考 androidReverseEngineering 确认字段

- [ ] **Step 2: Agent 2 — 迁移 o/g0, o/a, o/h, o/i, o/k (5 个中型 Delegate)**
每个文件：
- 包名 → `com.guard.wallet.delegate`
- `extends e` → `extends AccessibilityDelegate`
- 引用已迁移类使用新名称

- [ ] **Step 3: Agent 3 — 迁移 o/l, o/o, o/x (3 个) + 更新所有外部调用者**
迁移剩余 3 个小文件，并统一更新所有外部引用。

- [ ] **Step 4: 编译验证**
```bash
cd vendor-replica && ./gradlew compileDebugJavaWithJavac
```

- [ ] **Step 5: Commit**
```bash
git add -A && git commit -m "refactor: migrate 9 delegate subclasses to delegate/ package"
```

---

## Task 4: Dispatcher Runnable 迁移（12 个）

**依赖 Task 2 + Task 3 完成**（所有被 dispatch 的目标类已就位）。

**Files:**
- Move: `o/d.java` → `com/guard/wallet/delegate/task/DelegateEventDispatcher.java`
- Move: `o/y.java` → `com/guard/wallet/delegate/task/PairDelegateTask.java`
- Move: `o/h0.java` → `com/guard/wallet/delegate/task/VivoDelegateTask.java`
- Move: `o/p.java` → `com/guard/wallet/delegate/task/XiaomiDelegateTask.java`
- Move: `o/s.java` → `com/guard/wallet/delegate/task/WirelessPairTask.java`
- Move: `o/w.java` → `com/guard/wallet/delegate/task/PackageInstallerTask.java`
- Move: `o/f0.java` → `com/guard/wallet/delegate/task/DeviceCredentialTask.java`
- Move: `o/j.java` → `com/guard/wallet/delegate/task/EnableSecureTask.java`
- Move: `o/z.java` → `com/guard/wallet/delegate/task/AutoEngineTask.java`
- Move: `o/f.java` → `com/guard/wallet/delegate/task/PermissionGrantTask.java`
- Move: `o/m.java` → `com/guard/wallet/delegate/task/ConfirmLockTask.java`
- Move: `o/u.java` → `com/guard/wallet/delegate/task/MediaProjectionTask.java`
- Move: `o/b0.java` → `com/guard/wallet/delegate/task/ScreenCaptureTask.java`
- Move: `o/d0.java` → `com/guard/wallet/delegate/task/OpenDevDelegateTask.java`

- [ ] **Step 1: Agent 1 — 迁移 o/d, o/y, o/h0, o/p, o/s, o/w, o/f0 (7 个)**
每个文件：
- 包名 → `com.guard.wallet.delegate.task`
- 更新内部引用为已迁移类名
- 删除旧文件 + .pending

- [ ] **Step 2: Agent 2 — 迁移 o/j, o/z, o/f, o/m, o/u, o/b0, o/d0 (7 个)**
同上模式。

- [ ] **Step 3: Agent 3 — 更新全部外部调用者 + 清理 o/ 目录残留**
更新所有剩余外部引用，确保 o/ 目录为空后删除。

- [ ] **Step 4: 编译验证**
```bash
cd vendor-replica && ./gradlew compileDebugJavaWithJavac
```

- [ ] **Step 5: Commit**
```bash
git add -A && git commit -m "refactor: migrate 14 dispatcher tasks to delegate/task package"
```

---

## Task 5: 清理验证

- [ ] **Step 1: 确认 o/ 目录已完全删除**
```bash
ls vendor-replica/app/src/main/java/o/ 2>/dev/null && echo "STILL EXISTS" || echo "CLEAN"
```

- [ ] **Step 2: 确认零残留引用**
```bash
grep -rn '\bo\.[a-z][0-9]*\b' --include="*.java" vendor-replica/app/src/main/java/ | grep -v "f0\.o\.\|p0\.o\." | head -20
```
Expected: 仅注释中的历史引用

- [ ] **Step 3: 完整编译 + 测试**
```bash
cd vendor-replica && ./gradlew compileDebugJavaWithJavac
cd vendor-replica && ./gradlew test
```

- [ ] **Step 4: Final commit**
```bash
git add -A && git commit -m "refactor: complete o/ package migration — 36 files, 14K lines"
```

---

## 风险与注意事项

1. **o 包名冲突**: `o/` 包内部类之间大量交叉引用。迁移基类时，子类仍在 `o/` 包，需要暂时使用全限定名或分批处理
2. **同名字段/包冲突**: `o.e` 类内部有字段 `e`，`o.d` 类内部有字段 `d`，重命名类时不能误改字段名
3. **o.e vs com.guard.wallet.utils.e**: grep 时需排除 utils 包的同名类
4. **Delegate 注册表**: `AccessibilityDelegateManager` 和 `DelegateRemovePredicate` 使用 `o.X.class.getName()` 做类名匹配，迁移后类名变化会影响注册逻辑，需确认是否有字符串硬编码
5. **androidReverseEngineering 参考**: 每个文件迁移前必须读取 CFR 源确认完整方法/字段列表，避免 stub 遗漏

## 预估规模

| Task | 文件数 | 行数 | Agent 数 | 预估耗时 |
|------|--------|------|----------|---------|
| Task 1 | 6 | ~2,200 | 3 | 15 min |
| Task 2 | 6 | ~3,300 | 3 | 15 min |
| Task 3 | 9 | ~5,400 | 3 | 20 min |
| Task 4 | 14 | ~3,100 | 3 | 15 min |
| Task 5 | 0 | 验证 | 1 | 5 min |
| **Total** | **36** | **~14,000** | — | **~70 min** |
