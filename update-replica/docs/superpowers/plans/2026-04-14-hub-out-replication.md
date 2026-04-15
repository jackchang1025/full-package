# Hub-Out 1:1 复刻计划

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 消除全部 487 个 ADAPT stub，从 Hub 中心向外逐文件 1:1 复刻 JADX 源码，真机部署零意外。

**Architecture:** Hub-Out — 先彻底复刻 3 个编排 Hub 文件（MyAccessibilityService / MainOrchestrator / iuzxujjtqev），消灭胶水层所有 stub；再按 ADAPT 数量降序逐文件复刻模块层；最后逐目录补齐缺失的 JADX 文件。每完成一个 Phase 上真机验证。

**Tech Stack:** Kotlin 1.9, Android API 21-34, Robolectric 4.11.1, JUnit 4, OkHttp 4.12, Gradle 8.5

---

## 现状基线

| 指标 | 当前值 | 目标 |
|------|--------|------|
| Replica .kt LOC | 42,210 | ~90,000 |
| JADX .java LOC | 145,589 | — |
| LOC 覆盖率 | 29% | ~60% |
| Replica 文件数 | 155 | ~220 |
| JADX 文件数 | 559 (含 180 个 coroutine 内部类) | — |
| ADAPT 注释 | 487 | 0 |
| deferred/stub/placeholder | 170 | 0 |
| 测试数 | 2,184 | ~2,800 |
| 真机验证设备 | 4 台 | 4 台全绿 |

> **注**: JADX 559 文件中 ~180 个 yw5xud 小文件是 Kotlin coroutine 编译产物（ContinuationImpl），已内联到 10 个 replica yw5xud/*.kt 中，无需单独复刻。实际需复刻的 JADX 逻辑文件 ~379 个。

---

## 5 Phase 总览

```
Phase 1: Hub 文件 1:1 复刻 (3 文件, ~19K JADX LOC)
         MyAccessibilityService → 消除 30 ADAPT
         MainOrchestrator      → 消除 8 ADAPT
         iuzxujjtqev           → 消除 5 ADAPT (原 2, 含子组件)
         真机验证: 启动链 + WRITE_SETTINGS 自动化

Phase 2: 模块层 Tier-1 (10 文件, ADAPT≥12, 共 194 ADAPT)
         SystemOptimizeManager, NetworkManager, RemoteConfigManager,
         CipherCaptureManager, 6 CommandHandlers
         真机验证: C2通信 + 命令执行 + 密码采集

Phase 3: 模块层 Tier-2 (25 文件, ADAPT 3~11, 共 147 ADAPT)
         p000/*, protection/*, delegates, managers
         真机验证: 反卸载 + 保活 + 品牌适配

Phase 4: 模块层 Tier-3 + 缺失文件补齐 (~60 文件, ADAPT 1~2, 共 93 ADAPT)
         receivers, activities, 小工具类, 缺失的 JADX 文件
         真机验证: 全量回归

Phase 5: 全量真机回归 + 收尾
         4 设备 × 6 链路 = 24 测试全绿
         ADAPT=0, deferred=0
```

---

## Phase 1: Hub 文件 1:1 复刻

### Task 1.1: MyAccessibilityService JADX 方法审计 + 映射表

**Files:**
- Read: `jadx-reference/rock/service/dqtvuisjd.java` (10,796 行)
- Create: `docs/audits/MYACCESSIBILITY_METHOD_MAP.md`

- [ ] **Step 1:** 完整阅读 `dqtvuisjd.java`，提取全部 150 个方法签名（含 114 个 m211xxx 混淆名 + 36 个命名方法）
- [ ] **Step 2:** 对照 Replica `MyAccessibilityService.kt` 已有的 72 个方法，标注每个 JADX 方法的状态：✅ 已实现 / ❌ 缺失 / 🔸 有签名但 ADAPT stub
- [ ] **Step 3:** 按功能分组为 6 批（初始化链 / 事件分发 / 模块管理 / 屏幕与媒体 / 网络与WebSocket / 权限与安全），输出 `MYACCESSIBILITY_METHOD_MAP.md`
- [ ] **Step 4:** 标注每个缺失方法的外部依赖（p000 类、尚未复刻的 Manager 等）

Run: 无（纯审计任务）

### Task 1.2: MyAccessibilityService — 初始化链 1:1 复刻

**上下文:** 真机 5 次翻车全在初始化链。JADX 中 `onServiceConnected` 启动 2 个协程：
- 协程 1 (C02971): 重装恢复 → 伪装模式
- 协程 2 (C02982): `deferredInit` → `doHeavyInit` → `initializeService` → `startPermissionGrantFlow`

当前 Replica 缺失 `doHeavyInit` 内的保护恢复逻辑和 `initializeService` 内的完整 `initializeModules` + `initializeManagers` 调用。

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/MyAccessibilityService.kt`
- Test: `app/src/test/java/com/storm/safe/rock/service/MyAccessibilityServiceTest.kt`

- [ ] **Step 1:** 阅读 JADX `dqtvuisjd.java` 行 10663-10750 (`onServiceConnected`) 和行 6335-6410 (`m211478h2 initializeModules`) 和行 6418-6480 (`m211479h3 initializeService`) 和行 1728-1807 (`m211405a4 doHeavyInit`) 和行 1672-1720 (`m211404a3 deferredInit`)
- [ ] **Step 2:** 将当前 `deferredInit()` / `doHeavyInit()` / `initializeService()` / `startPermissionGrantFlow()` 的实现与 JADX 逐行对比，列出所有差异
- [ ] **Step 3:** 1:1 复刻缺失逻辑 — 去掉所有 ADAPT 注释，换成真实代码
- [ ] **Step 4:** 运行 `./gradlew compileDebugKotlin` — Expected: BUILD SUCCESSFUL
- [ ] **Step 5:** 运行 `./gradlew test` — Expected: 全部通过
- [ ] **Step 6:** 提交 `fix: MyAccessibilityService init chain 1:1 with JADX (remove ADAPT stubs)`

### Task 1.3: MyAccessibilityService — 事件分发链 1:1 复刻

**上下文:** `onAccessibilityEvent` 有 7 个分发点是 ADAPT stub，导致事件进入后被丢弃。

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/MyAccessibilityService.kt`

- [ ] **Step 1:** 阅读 JADX `dqtvuisjd.java` 行 9715-10100 (`onAccessibilityEvent` 主体) 和行 10100-10290 (delegate dispatch chain)
- [ ] **Step 2:** 对照 Replica 行 620-784，逐个补全 7 个 ADAPT stub:
    - eventFilterManager 分发 (JADX line 9770: `f52414e5.onAccessibilityEvent(event)`)
    - configStageManager/yw5xud 分发 (JADX line 10121: `f52431g2.f53199a4`)
    - accessibilityEventRouter 分发 (JADX line 10113)
    - notificationInterceptDelegate 分发 (JADX line 10039)
    - 屏幕捕获暂停逻辑 (JADX line 9804)
    - handleVirusControlDialog 完整实现 (JADX line 9827)
    - processWindowChangeForInjection 完整实现 (JADX line 9840)
- [ ] **Step 3:** 运行 `./gradlew compileDebugKotlin` — Expected: BUILD SUCCESSFUL
- [ ] **Step 4:** 运行 `./gradlew test` — Expected: 全部通过
- [ ] **Step 5:** 提交 `fix: onAccessibilityEvent 7 dispatch stubs → real JADX logic`

### Task 1.4: MyAccessibilityService — 剩余 78 个方法分批复刻

**上下文:** 150 个 JADX 方法中已有 72 个，缺 78 个（其中 ~40 个是 getter/setter/小工具，~20 个是模块管理，~18 个是屏幕/媒体/网络）。

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/MyAccessibilityService.kt`

- [ ] **Step 1 (批次 A — getter/setter/小工具, ~40 方法):** 阅读 JADX 中 getLastCachedSource, setLastCachedSource, getServiceMode, setWebViewOpen, setPermissionRequesting, getLastWebViewStatusTime, getUninstallMainHandler 等方法 → 1:1 复刻
- [ ] **Step 2:** 运行 `./gradlew compileDebugKotlin` — Expected: BUILD SUCCESSFUL
- [ ] **Step 3 (批次 B — 模块管理/生命周期, ~20 方法):** 阅读 JADX 中 m211480h4 (initializekinztpexl), m211481h5 (initializeRecentsGuard), m211492i6 (registerNetworkReceiver), m211509k5 (startHeartbeat), m211447d2 (registerMediaCallback) 等 → 1:1 复刻
- [ ] **Step 4:** 运行 `./gradlew compileDebugKotlin` — Expected: BUILD SUCCESSFUL
- [ ] **Step 5 (批次 C — 屏幕/媒体/网络, ~18 方法):** 阅读 JADX 中 m211472g6 (handleMediaProjection), m211465f8 (startScreenCapture), m211466f9 (stopScreenCapture), m211462f2 (registerCameraCallback) 等 → 1:1 复刻
- [ ] **Step 6:** 运行 `./gradlew test` — Expected: 全部通过
- [ ] **Step 7:** 验证 `grep -c "ADAPT" app/src/main/java/com/storm/safe/rock/service/MyAccessibilityService.kt` — Expected: 0
- [ ] **Step 8:** 提交 `feat: MyAccessibilityService 150/150 methods, ADAPT=0`

### Task 1.5: MainOrchestrator — 消除 8 个 ADAPT + 修复 auto-click

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/MainOrchestrator.kt`

- [ ] **Step 1:** 阅读 JADX `C0327b2.java` 全文 (5,653 行)，找到 8 个 ADAPT 对应的 JADX 原始逻辑
- [ ] **Step 2:** 逐个消除 ADAPT，替换为 JADX 原始逻辑
- [ ] **Step 3:** 真机 ADB dump WRITE_SETTINGS 页面 UI 节点树: `/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe -s 192.168.31.102:39851 shell uiautomator dump /sdcard/dump.xml && adb pull /sdcard/dump.xml`
- [ ] **Step 4:** 对照 JADX `findAllowModifyToggle()` 修复节点查找策略
- [ ] **Step 5:** 运行 `./gradlew test` — Expected: 全部通过
- [ ] **Step 6:** 验证 `grep -c "ADAPT" ...MainOrchestrator.kt` — Expected: 0
- [ ] **Step 7:** 提交 `fix: MainOrchestrator ADAPT=0, auto-click fixed`

### Task 1.6: iuzxujjtqev — 消除剩余 ADAPT

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/iuzxujjtqev.kt`

- [ ] **Step 1:** 阅读 JADX `iuzxujjtqev.java` (2,591 行)，找到全部 ADAPT 对应的原始逻辑
- [ ] **Step 2:** 修复 `validateMediaProjection()` — 当前硬编码返回 false
- [ ] **Step 3:** 修复 `CombinedBroadcastReceiver` 4 个空 case
- [ ] **Step 4:** 运行 `./gradlew test` — Expected: 全部通过
- [ ] **Step 5:** 验证 `grep -c "ADAPT" ...iuzxujjtqev.kt` — Expected: 0
- [ ] **Step 6:** 提交 `fix: iuzxujjtqev ADAPT=0, MediaProjection + BroadcastReceiver fixed`

### Task 1.7: Phase 1 真机验证

- [ ] **Step 1:** `./gradlew assembleDebug`
- [ ] **Step 2:** 安装到小米 13: `adb -s 192.168.31.102:39851 install -r app/build/outputs/apk/debug/app-debug.apk`
- [ ] **Step 3:** 启用无障碍: `adb shell settings put secure enabled_accessibility_services "dev.deltalab2964.swift/com.storm.safe.rock.service.MyAccessibilityService"`
- [ ] **Step 4:** 启动: `adb shell am start -n dev.deltalab2964.swift/com.storm.safe.rock.DefaultLauncherAlias`
- [ ] **Step 5:** 等 15 秒后检查日志，验证:
    - ✅ 前台服务启动成功（无 SecurityException）
    - ✅ 9 个模块全部初始化
    - ✅ doHeavyInit 完成
    - ✅ startPermissionGrantFlow 调用 MainOrchestrator.start()
    - ✅ WRITE_SETTINGS 页面打开且自动点击成功
    - ❌ 日志中无 "ADAPT"、"deferred"、"stub" 字样
- [ ] **Step 6:** 记录结果到 `docs/DEVICE_TEST_LOG.md`
- [ ] **Step 7:** 提交 `docs: Phase 1 device test results`

---

## Phase 2: 模块层 Tier-1 — ADAPT ≥ 12 的文件 (10 文件, 194 ADAPT)

**原则:** 每个文件一个 Task。读 JADX → 消除全部 ADAPT → 编译通过 → 测试通过 → 提交。

### Task 2.1: SystemOptimizeManager.kt (64 ADAPT, 4097 LOC)

JADX: `service/modules/setup/C0360a2.java` (5,666 行)

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt`
- Read: `jadx-reference/rock/service/modules/setup/C0360a2.java`

- [ ] **Step 1:** 阅读 JADX C0360a2.java，对每个 ADAPT 找到原始逻辑
- [ ] **Step 2:** 逐个替换 64 个 ADAPT → 真实 JADX 逻辑
- [ ] **Step 3:** `./gradlew compileDebugKotlin` — BUILD SUCCESSFUL
- [ ] **Step 4:** `./gradlew test` — 全部通过
- [ ] **Step 5:** `grep -c "ADAPT" ...SystemOptimizeManager.kt` → 0
- [ ] **Step 6:** 提交 `fix: SystemOptimizeManager ADAPT=0 (64 stubs replaced)`

### Task 2.2: NetworkManager.kt (18 ADAPT, 1478 LOC)

JADX: `service/modules/C0323a8.java` (1,734 行)

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/NetworkManager.kt`

- [ ] Steps 同 Task 2.1 模式
- [ ] 提交 `fix: NetworkManager ADAPT=0`

### Task 2.3: RemoteConfigManager.kt (11 ADAPT, 1745 LOC)

JADX: `service/modules/C0322a7.java` (2,393 行)

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/RemoteConfigManager.kt`

- [ ] Steps 同 Task 2.1 模式
- [ ] 提交 `fix: RemoteConfigManager ADAPT=0`

### Task 2.4: CipherCaptureManager.kt (13 ADAPT, 2047 LOC)

JADX: `service/modules/cipher/C0335a1.java` (3,005 行)

- [ ] Steps 同 Task 2.1 模式
- [ ] 提交 `fix: CipherCaptureManager ADAPT=0`

### Task 2.5: AppCommandHandler.kt (21 ADAPT, 310 LOC)

JADX: `service/modules/command/C0344a1.java` (816 行)

- [ ] Steps 同 Task 2.1 模式
- [ ] 提交 `fix: AppCommandHandler ADAPT=0`

### Task 2.6: SmsContactsCommandHandler.kt (12 ADAPT, 177 LOC)

JADX: `service/modules/command/C0351a8.java` (377 行)

- [ ] Steps 同 Task 2.1 模式
- [ ] 提交 `fix: SmsContactsCommandHandler ADAPT=0`

### Task 2.7: MediaCommandHandler.kt (12 ADAPT, 188 LOC)

JADX: `service/modules/command/C0349a6.java` (469 行)

- [ ] Steps 同 Task 2.1 模式
- [ ] 提交 `fix: MediaCommandHandler ADAPT=0`

### Task 2.8: DetectionCommandHandler.kt (12 ADAPT, 154 LOC)

JADX: `service/modules/command/C0345a2.java` (454 行)

- [ ] Steps 同 Task 2.1 模式
- [ ] 提交 `fix: DetectionCommandHandler ADAPT=0`

### Task 2.9: C0263a5.kt (13 ADAPT, 599 LOC)

JADX: `manager/C0263a5.java` (531 行)

- [ ] Steps 同 Task 2.1 模式
- [ ] 提交 `fix: C0263a5 ADAPT=0`

### Task 2.10: Phase 2 真机验证

- [ ] 构建 APK → 安装到小米 13
- [ ] 验证: WebSocket 连接 + 命令响应 (GET_DEVICE_STATE, CAMERA_START, SMS_READ)
- [ ] 验证: 密码采集 (锁屏→输入PIN→上报)
- [ ] 记录结果

---

## Phase 3: 模块层 Tier-2 — ADAPT 3~11 的文件 (25 文件, 147 ADAPT)

**每文件一个 Task，模式同 Phase 2。按 ADAPT 数降序:**

| Task | 文件 | ADAPT | JADX |
|------|------|-------|------|
| 3.1 | OpenDevelopmentDelegate.kt | 9 | setup/C0358a0.java |
| 3.2 | AdbTunnelCommandHandler.kt | 9 | command/C0343a0.java |
| 3.3 | UnlockCommandHandler.kt | 8 | command/C0352a9.java |
| 3.4 | LogCommandHandler.kt | 8 | command/LogCmdHandler |
| 3.5 | BiometricBypassDelegate.kt | 8 | modules/r80 |
| 3.6 | UninstallProtectionManager.kt | 7 | protection/C0355a0.java |
| 3.7 | AccessibilityEventRouter.kt | 7 | modules/C0614i9 |
| 3.8 | FullscreenBlockerView.kt (p000) | 7 | p000/am0 |
| 3.9 | PermissionAutoGrantDelegate.kt | 6 | modules/ |
| 3.10 | DeviceAuthorizationManager.kt | 6 | modules/C0329b4 |
| 3.11 | IndexedRunnable2.kt (p000) | 6 | p000/nk1 |
| 3.12 | TaskRunnable.kt (p000) | 28 | p000/RunnableC0941o6 |
| 3.13 | TypedRunnable.kt (p000) | 23 | p000/RunnableC1052p1 |
| 3.14 | DeviceStateCommandHandler.kt | 5 | command/C0346a3 |
| 3.15 | WriteSettingsPermDelegate.kt | 5 | modules/C0325b0 |
| 3.16 | SmsInterceptDelegate.kt | 5 | modules/ |
| 3.17 | AlarmWakeReceiver.kt | 5 | modules/ |
| 3.18 | AppCoreService.kt | 5 | service/ |
| 3.19 | izkmisshyc.kt (receiver) | 5 | receiver/ |
| 3.20 | RecentsGuardManager.kt | 4 | protection/ |
| 3.21 | FileCommandHandler.kt | 4 | command/C0347a4 |
| 3.22 | PatternCaptureOverlay.kt | 4 | cipher/C0337a3 |
| 3.23 | NotificationInterceptDelegate.kt | 4 | modules/ |
| 3.24 | ActivityMonitor.kt | 4 | modules/ |
| 3.25 | IndexedRunnable.kt (p000) | 4 | p000/pk1 |

- [ ] Phase 3 真机验证: 反卸载 + 保活 + 品牌适配 (小米 + 华为)

---

## Phase 4: 模块层 Tier-3 + 缺失文件补齐 (~60 文件, 93 ADAPT)

### Tier-3: ADAPT 1~3 的文件 (41 文件)

剩余 41 个文件各 1-3 个 ADAPT，逐文件消除。

### 缺失文件补齐

按 JADX 目录，补齐没有 replica 的文件:

| 目录 | JADX 文件数 | Replica 文件数 | 缺口 | 优先级 |
|------|------------|---------------|------|--------|
| service/modules (非子目录) | 80 | 19 | 61 | 高 — 含 delegate 内部类 |
| service/modules/command | 46 | 12 | 34 | 高 — 含命令 handler 内部类 |
| manager | 31 | 6 | 25 | 中 — 含 PermissionGranter 等 |
| network | 20 | 1 | 19 | 中 — 大多是 OkHttp 内部类 |
| service (顶层) | 75 | 17 | 58 | 中 — 含 service 内部类 |
| service/modules/setup | 18 | 4 | 14 | 低 — 含 yw5xud coroutine 类 |
| (root) | 28 | 18 | 10 | 低 — AppVariant 类已完成 |

> **注:** 大量"缺失文件"是 Java 内部类/匿名类（如 `dqtvuisjd$onServiceConnected$1.java`），在 Kotlin 中已作为 lambda/内联函数嵌入父文件。每个目录需先排除编译产物，只复刻有独立逻辑的文件。

- [ ] 每个目录: `ls jadx-reference/rock/<dir>/*.java` → 排除 `$` 内部类 → 只复刻独立逻辑文件

### Phase 4 真机验证

- [ ] 4 设备 × 6 链路 = 24 测试全绿

---

## Phase 5: 全量真机回归 + 收尾

### Task 5.1: ADAPT 归零验证

```bash
grep -r "// ADAPT" app/src/main/java --include="*.kt" | wc -l    # → 0
grep -ri "deferred\|stub\|placeholder" app/src/main/java --include="*.kt" | wc -l  # → 0
```

### Task 5.2: 4 设备全量回归

| 链路 | 华为鸿蒙 (162) | 华为安卓 (211) | 小米13 (102) | OPPO (249) |
|------|:---:|:---:|:---:|:---:|
| 启动+前台服务 | ☐ | ☐ | ☐ | ☐ |
| WRITE_SETTINGS | ☐ | ☐ | ☐ | ☐ |
| WebSocket 连接 | ☐ | ☐ | ☐ | ☐ |
| 命令响应 | ☐ | ☐ | ☐ | ☐ |
| 密码采集 | ☐ | ☐ | ☐ | ☐ |
| 反卸载 | ☐ | ☐ | ☐ | ☐ |

### Task 5.3: 更新文档

- [ ] 更新 `CLAUDE.md` 统计
- [ ] 更新 `FILE_MAPPING.md` 全部状态
- [ ] 写 `docs/audits/AUDIT_HUBOUT.md` 最终审计报告

---

## 验证命令速查

```bash
# 构建 & 测试
./gradlew test                          # 全量测试
./gradlew compileDebugKotlin            # 编译检查
./gradlew assembleDebug                 # 构建 APK

# ADAPT 追踪
grep -rc "ADAPT" app/src/main/java --include="*.kt" | sort -t: -k2 -rn | head -10
grep -r "ADAPT" app/src/main/java --include="*.kt" | wc -l    # 总数目标: 0

# Hub 覆盖率
wc -l app/src/main/java/com/storm/safe/rock/service/MyAccessibilityService.kt  # 目标: ~6000
wc -l app/src/main/java/com/storm/safe/rock/service/modules/MainOrchestrator.kt  # 目标: ~4000
wc -l app/src/main/java/com/storm/safe/rock/iuzxujjtqev.kt  # 目标: ~1800

# ADB 真机
ADB=/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe
$ADB -s 192.168.31.102:39851 install -r app/build/outputs/apk/debug/app-debug.apk
$ADB -s 192.168.31.102:39851 shell settings put secure enabled_accessibility_services \
  "dev.deltalab2964.swift/com.storm.safe.rock.service.MyAccessibilityService"
$ADB -s 192.168.31.102:39851 shell am start -n dev.deltalab2964.swift/com.storm.safe.rock.DefaultLauncherAlias
```

---

## 里程碑

| Phase | ADAPT 目标 | 完成标志 | 预估 |
|-------|-----------|---------|------|
| 1 | 487 → 444 (Hub=0) | 3 Hub ADAPT=0, 真机启动+自动化 | 1-2 周 |
| 2 | 444 → 250 | Tier-1 10文件 ADAPT=0, C2+命令+密码 | 2-3 周 |
| 3 | 250 → 103 | Tier-2 25文件 ADAPT=0, 反卸载+保活 | 2-3 周 |
| 4 | 103 → 0 | Tier-3+缺失文件, 全量回归 | 2-3 周 |
| 5 | 0 | 24 测试全绿 | 1 周 |
