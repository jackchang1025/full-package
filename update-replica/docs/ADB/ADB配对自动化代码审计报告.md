# ADB 配对自动化代码审计报告

> **审计日期**: 2026-04-17
> **审计范围**: SystemOptimizeManager.kt + AdbTunnelCommandHandler.kt + MyAccessibilityService.kt 接线
> **对照基准**: `jadx-reference/rock/service/modules/setup/C0360a2.java` (5666 行) + `jadx-reference/p000/c41.java`
> **审计触发**: `docs/ADB 配对自动化触发条件分析.md` 中的 4 条触发路径

---

## 一、总体评估

| 类别 | 对齐 | 部分 | 缺失 | 得分 |
|------|------|------|------|------|
| 控制流 (startPairFlow / forceStart / timeout) | 7 | 6 | 2 | 64% |
| 事件分发 (filterAccessibilityEvent / mainHandler) | 8 | 2 | 0 | 80% |
| 加密 (SPAKE2 / TLS 1.3 / HKDF / AES-GCM) | 6 | 1 | 0 | 86% |
| 状态机 (PairState / processedActions / flags) | 5 | 2 | 0 | 71% |
| C2 命令处理 (7 条命令) | 2 | 2 | 3 | 29% |
| 心跳恢复 (H() / c41 case 1/3/7/8/9/10) | 0 | 1 | 2 | 17% |
| **总体** | **28** | **14** | **7** | **~60%** |

---

## 二、逐项审计

### 2.1 startPairFlow (vendor k3, L5101-5153)

**Replica**: `SystemOptimizeManager.kt:3943`

| 检查项 | Vendor | Replica | 状态 |
|--------|--------|---------|------|
| 三路页面检测 devOpt/wifiDebug/else | ✅ K()/O()/else | ✅ isInDevOptionsWindow/isInWifiDebugWindow/else | ✅ |
| isPairRunning.set(true) | L5103 | L3945 | ✅ |
| isFinished.set(false) | L5104 | L3946 | ✅ |
| pairState → UNKNOWN | L5116 | L3959 | ✅ |
| 120s 超时调度 (c41 case 11) | L5112-5114 | L3953 `{ timeoutHandler() }` | ✅ |
| 30s 检查调度 (c41 case 12) | L5115 | L3954 `{ checkTimeout30s() }` | ✅ |
| **executor shutdown 重建** | L5105-5109 `new executor` | ❌ `val executor`，不可重建 | **❌ P0** |

**❌ P0 缺陷**: `executor` 声明为 `val`（Kotlin 不可重赋值）。vendor 在 L5105-5109 判断 `isShutdown()` 后 `new Executors.newSingleThreadScheduledExecutor()` 重建。replica 只打 warning 日志。当 `finishLocalAdbPair()` 调用 `executor.shutdownNow()` 后，后续的 `startPairFlow()` 无法调度任何任务。

**修复方案**: `val executor` → `var executor`，在 startPairFlow 开头添加重建逻辑。

---

### 2.2 forceStartPairFlow (vendor k5, L5169-5187)

**Replica**: 不存在独立方法（startPairFlow 兼用）

| 检查项 | Vendor | Replica | 状态 |
|--------|--------|---------|------|
| 方法存在 | m212095k5 | 不存在 | **⚠️** |
| 日志 "外部触发配对流程" / "强制开始" | L5170-5171 | 无 | ⚠️ |
| 跳过三路检测，直接 openDevOptions | L5186 `m212080i5()` | 无 | ⚠️ |
| c41 case 5/6 (vs k3 用 case 11/12) | L5181-5184 | 无 | ⚠️ |

**⚠️ P2 降级**: 功能上等价 — k5 的 case 5 → `m212094k4()` 和 case 6 → `m212103l3()` 与 case 11/12 执行相同方法。k5 唯一区别是跳过三路检测直接打开开发者选项。对实际行为影响低。

---

### 2.3 filterAccessibilityEvent (vendor i3, L3789-3808)

**Replica**: `SystemOptimizeManager.kt:3435`

| 检查项 | Vendor | Replica | 状态 |
|--------|--------|---------|------|
| systemui/settings 包名 + event type 32/2048/1 | L3797 | L3439-3443 | ✅ |
| 触发 handleWirelessDebuggingToggle (c41 case 0) | L3798 | L3444 | ✅ |
| isPairRunning/isFinished 前置检查 | L3800-3801 | L3448 | ✅ |
| settings/securitycenter/systemui contains 过滤 | L3803 | L3451-3453 | ✅ |
| 分发到 mainAccessibilityEventHandler (via e41) | L3806 | L3459-3461 executor.execute | ✅ |

**✅ 完全对齐**

---

### 2.4 mainAccessibilityEventHandler (vendor i4, L3811-3980)

**Replica**: `SystemOptimizeManager.kt:3480`

| 场景 | Vendor | Replica | 状态 |
|------|--------|---------|------|
| A: isInDevOptionsWindow | L3823-3851: 移除 6 项 queue + dispatch G() | L3489-3511: 同 | ✅ |
| B: isInWifiDebugWindow | L3853-3878: 移除 devOpt/confirmLock + dispatch W() 或 S() | L3515-3536: 同 | ✅ |
| C: pairInPairSuccess | L3879-3980: 读端口 + ADB 连接 + deploy | 无独立 handler | **⚠️** |
| D: pairInPairFailDialog | vendor 隐含在 i4 dispatch | L3540-3545: isInPairFailDialog + handlePairFailDialog | ✅ |
| E: pairInConfirmLock | 密码确认弹窗处理 | ❌ 缺失 (注释: "deferred") | **⚠️ P1** |
| F: pairInSecurityCenter | MIUI 安全中心弹窗 | ❌ 缺失 (注释: "deferred") | **⚠️ P1** |

**终态集合**: Vendor `{SUCCESS(2), FAIL(5), PREPARE_FINISH(6)}` = Replica `{PAIR_DEPT_PAIR_SUCCESS, PAIR_DEPT_PAIR_FAIL, PAIR_DEPT_PREPARE_FINISH}` ✅

---

### 2.5 isInWifiDebugWindow (vendor a6, L1936-1971)

**Replica**: `SystemOptimizeManager.kt:1843`

| 检查项 | Vendor | Replica | 状态 |
|--------|--------|---------|------|
| WindowDetector 缓存检测 | L1940-1949 bf1 | ❌ 跳过 | ⚠️ P2 |
| 包名 == "com.android.settings" | L1951 精确匹配 | L1847 `contains("settings")` | ⚠️ P3 |
| 文本搜索 dh0.f55786d6 | L1954 | L1851 WIRELESS_DEBUG_PAGE_TEXTS | ✅ |

---

### 2.6 isInDevOptionsWindow (vendor a2, L1799)

**Replica**: `SystemOptimizeManager.kt:1815`

| 检查项 | Vendor | Replica | 状态 |
|--------|--------|---------|------|
| WindowDetector 缓存检测 | bf1 | ❌ 跳过 | ⚠️ P2 |
| 文本搜索 dh0.f55783d3 | DEVELOPER_OPTIONS_TEXTS | DEVELOPER_OPTIONS_TEXTS | ✅ |
| OPPO 真机验证 | N/A | ❌ "OEM 解锁" (有空格) 无法被 findByText 匹配 | **⚠️ 真机** |

**⚠️ 真机问题**: OPPO PGFM10 真机测试中，`isInDevOptionsWindow()` 返回 false。原因：页面上 "USB调试" 等文本需要**滚动才可见**，不在初始 accessibility tree 中。`findAccessibilityNodeInfosByText` 只搜索当前可见节点。

---

### 2.7 timeoutHandler (vendor k4, L5157-5166)

**Replica**: `SystemOptimizeManager.kt:3982`

| 检查项 | Vendor | Replica | 状态 |
|--------|--------|---------|------|
| 检查 PAIR_FINISH 状态 | L5159 | L3984 | ✅ |
| 调用 finishLocalAdbPair | L5162 m212026a0 | L3988 finishLocalAdbPair() | ✅ |

**✅ 完全对齐**

---

### 2.8 心跳恢复 (vendor H(), c41 case 1/3/7/8/9/10)

**Replica**: `SystemOptimizeManager.kt:3215` (heartbeatEventDispatcher)

| 检查项 | Vendor c41 case | Replica | 状态 |
|--------|----------------|---------|------|
| case 0: handleUsbDebugDialog | m212066h0 | handleWirelessDebuggingToggle | ✅ |
| case 1: checkAndRecoverLocalService | m212042c9 | checkAndRecoverLocalService L2230 | ✅ |
| case 2: openDevOptionsRetryV2 back+retry | performGlobalAction(BACK)+m212081i6 | openDevOptionsRetryV2 (无 BACK) | ⚠️ |
| case 3: heartbeat task (10s interval) | m212070h4 | heartbeatEventDispatcher | ✅ |
| case 4: local-service post-deploy init | 通知包名+触发优化 | ❌ 缺失 | ⚠️ |
| case 5/6: k5 timeout/check | m212094k4/m212103l3 | timeoutHandler/checkTimeout30s | ✅ |
| case 7: SilentRecover | 端口扫描+ADB重连+push binary | ❌ 缺失 | **❌ P1** |
| **case 8/9/10: → startPairFlow** | m212093k3 | L3258 `executor.execute { startPairFlow() }` | ✅ |
| case 11/12: timeout/check | m212094k4/m212103l3 | timeoutHandler/checkTimeout30s | ✅ |

**case 7 (SilentRecover) 缺失**: vendor 在 local-service 掉线时执行静默恢复：保存端口 → ADB 重连 → push binary → 启动 local-service。replica 只调 `deployLocalService()` (返回 false 的 stub)。

---

### 2.9 pairInDevOption — G() 流程 (vendor b0, L452+)

**Replica**: `SystemOptimizeManager.kt:4008`

| 步骤 | Vendor | Replica | 状态 |
|------|--------|---------|------|
| 检查 isInDevOptionsWindow | ✅ | L4016 | ✅ |
| 查找 scrollableView | findScrollableView | findScrollableViewWithRetry | ✅ |
| Vivo 开发者选项总开关 | J0() master switch | L4040 handleVivoDevOptionsSwitch | ✅ |
| OPPO 禁用权限监控 | a1() disablePermMonitor | oppoDisablePermMonitorDone flag | ⚠️ |
| 滚动查找 "无线调试" | w0() scroll+search | scroll loop + findWirelessDebugNode | ✅ |
| 华为 "撤销USB调试授权" | a8() revoke USB auth | L4090 handleRevokeUsbAuth | ✅ |
| 小米 checkbox 预检查 | a7() Xiaomi pre-check | L4108 findWirelessDebugNode checks | ⚠️ |
| 进入无线调试子页面 | click → sleep → verify | click → sleep200 → verify | ✅ |
| **进入后调 pairInWifiDebugWindow** | m211995b4 | L4148 pairInWifiDebugWindow() | ✅ |

---

### 2.10 pairInWifiDebugWindow — W() 流程 (vendor b4, L731-791)

**Replica**: `SystemOptimizeManager.kt:4148`

| 步骤 | Vendor | Replica | 状态 |
|------|--------|---------|------|
| 20×1.5s 循环找配对按钮 | 20 iter, 1.5s sleep | 20 iter, 1.4s sleep (7×200ms) | ✅ |
| PAIR_DEVICE_BUTTON_TEXTS 多语言 | dh0.f55790e0 (46 strings) | SetupConstants.PAIR_DEVICE_BUTTON_TEXTS | ✅ |
| findClickableParentCompat | R() 6 级 | findClickableParentCompat 6 级 | ✅ |
| PairState → PAIRING | implicit | L4172 PAIR_DEPT_PAIRING | ✅ |
| 10s×500ms 轮询 extractPairingCodeAndPort | 10s, 500ms | 10s, 400ms (2×200ms) | ✅ |
| collectAllNodes (不过滤 className) | 全节点采集 | L4407 collectAllNodes | ✅ |
| split(":", limit=6) IPv6 安全 | limit 6 | L4415 `split(":", limit = 6)` | ✅ |
| doPair(port, code) | m211993c7 | L4185 doPair(port, code) | ✅ |
| PairState → SUCCESS + uploadAdbKeys | L后续 | L4193-4201 | ✅ |
| firstDeployDone=false + processedActions.remove | vendor 特定 | L4207-4209 | ✅ |

**✅ 完全对齐**

---

### 2.11 doPair — SPAKE2+TLS (vendor e2, L2742)

**Replica**: `SystemOptimizeManager.kt:2381`

| 步骤 | Vendor | Replica | 状态 |
|------|--------|---------|------|
| TCP → 127.0.0.1:port | L2746 | L2393 Socket("127.0.0.1", port) | ✅ |
| TLS 1.3 握手 | L2748-2752 SSLSocket | L2397-2401 enabledProtocols=["TLSv1.3"] | ✅ |
| 导出密钥材料 | L2756 exportKeyingMaterial | L2403 exportKeyingMaterial(sslSocket) | ✅ |
| password = code_UTF8 ‖ keying_material | L2763-2767 | L2411-2414 System.arraycopy | ✅ |
| Spake2Context(clientId, serverId) | L2768 | L2419 "adb pair client"/"adb pair server" | ✅ |
| generateMessage(password) | L2773 m213179a0 | L2422 generateMessage(password) | ✅ |
| writePairingPacket(type=0, msg) | L2779 | L2425 writePairingPacket(dos, 0, outMsg) | ✅ |
| readPairingPacket → type==0 check | L2785 | L2428 header.type.toInt() == 0 | ✅ |
| processMessage(serverMsg) → sharedSecret | L2790 m213180a5 | L2433 processMessage(serverMsg) | ✅ |
| HKDF-SHA256 derive AES key | "adb pairing_auth aes-128-gcm key" | L2438 deriveKeys(sharedSecret, label) | ✅ |
| AES-128-GCM encrypt PeerInfo | L2800 | L2443 encryptPairingMessage | ✅ |
| writePairingPacket(type=1, encrypted) | L2805 | L2445 writePairingPacket(dos, 1, ...) | ✅ |
| readPairingPacket → decrypt → verify | L2810 | L2448 decryptPairingMessage → verify | ✅ |
| spake2Ctx.destroy() + socket.close() | L2820 | L2455-2458 destroy+close | ✅ |

**✅ 完全对齐** — 加密协议实现与 vendor 1:1 匹配。

---

### 2.12 完成回调链

**Replica**: `SystemOptimizeManager.kt:2985` (handleComplete)

| 步骤 | Vendor | Replica | 状态 |
|------|--------|---------|------|
| 隐藏无障碍遮盖 | dqtvuisjd.m211469g3().hide() | L2991 Log（遮盖类未复刻） | ⚠️ |
| 保存 pair_completed + adb_deploy_enabled | SharedPreferences | L2999-3002 | ✅ |
| firstDeployDone = true | vendor 标记 | L3008 | ✅ |
| shutdownEngine | 停止 executor | L3010 shutdownEngine() | ✅ |
| 启动心跳 5s 定时任务 | scheduleAtFixedRate(5s) | L3018-3022 scheduleAtFixedRate(5000ms) | ✅ |
| 执行返回键 5 次退出设置 | performGlobalAction(BACK) ×5 | L3031-3043 for 1..5 | ✅ |
| 调用 onComplete 回调 | f53829b4.invoke() | L3049 onCompleteCallback?.invoke() | ✅ |

---

### 2.13 控制标志 & 状态机

| 标志 | Vendor 字段 | Replica 字段 | 类型 | 状态 |
|------|------------|-------------|------|------|
| isPairRunning | f53823a8 | isPairRunning | AtomicBoolean | ✅ |
| isFinished | f53822a7 | isFinished | AtomicBoolean | ✅ |
| pairState | f53819a4 | pairState | AtomicReference\<PairState\> | ✅ |
| devOptState | f53820a5 | devOptState | AtomicReference\<DevOptState\> | ✅ |
| processedActions | f53818a3 | processedActions | ConcurrentLinkedQueue\<String\> | ✅ |
| executor | f53817a2 | executor | ScheduledExecutorService | ⚠️ val vs var |

**PairState 枚举映射**:

| Ordinal | Vendor | Replica | 用途 |
|---------|--------|---------|------|
| 0 | f53759a0 | PAIR_DEPT_UNKNOWN | 初始/重置 |
| 1 | f53760a1 | PAIR_DEPT_PAIR_LEAVE_DEV_OPT | 离开开发者选项 |
| 2 | f53761a2 | PAIR_DEPT_PAIR_SUCCESS | 配对成功 |
| 3 | f53762a3 | PAIR_DEPT_PAIR_RETRY | 重试 |
| 4 | f53763a4 | PAIR_DEPT_PAIRING | 配对中 |
| 5 | f53764a5 | PAIR_DEPT_PAIR_FAIL | 配对失败 |
| 6 | f53765a6 | PAIR_DEPT_PREPARE_FINISH | 准备完成 |
| 7 | (无) | PAIR_DEPT_PAIR_FINISH | 完成（replica 额外添加）|

---

### 2.14 MyAccessibilityService 接线

**Replica**: `MyAccessibilityService.kt:782-800`

| 检查项 | Vendor | Replica | 状态 |
|--------|--------|---------|------|
| onAccessibilityEvent → filterAccessibilityEvent | dqtvuisjd → C0360a2.m212078i3 | ✅ 调用 som.filterAccessibilityEvent(event) | ✅ |
| SystemOptimizeManager 懒初始化 | AppCoreService.onCreate | L784 getInstanceOrNull ?: getInstance | ✅ |
| 位置：在 MainOrchestrator 之后，permission guard 之前 | 同 | L782 在 mo.handleAccessibilityEvent 之后 | ✅ |

---

### 2.15 AdbTunnelCommandHandler — 7 条 C2 命令

**Replica**: `AdbTunnelCommandHandler.kt`

| 命令 | Vendor 方法 | Replica 方法 | 实现 | 状态 |
|------|-----------|-------------|------|------|
| DEPLOY_LOCAL_SERVICE | deployLocalService | handleDeployLocalService L132 | 调用 som.deployLocalService() | ⚠️ stub |
| **START_PAIRING** | m212095k5 (forceStartPairFlow) | handleStartPairing L171 | **注释: "doesn't have startWirelessPairing yet"** | **❌ stub** |
| OPEN_WIFI_DEBUG_SETTINGS | Intent | handleOpenWifiDebugSettings L202 | 打开设置 Intent | ✅ |
| **FULL_DEPLOY** | forceStart → 全链路 | handleFullDeploy L223 | **注释: "complex forceStart with callbacks"** | **❌ stub** |
| OPEN_ABOUT_PHONE | Intent | handleOpenAboutPhone L247 | 打开关于手机 Intent | ✅ |
| **AUTO_WIRELESS_PAIRING** | m212095k5 | handleAutoWirelessPairing L265 | **无实际配对触发** | **❌ stub** |
| **DIRECT_PAIR** | m212052e0 读屏幕配对码 | handleDirectPair L294 | **注释: "readPairingCodeFromScreen is internal"** | **❌ stub** |

**❌ 4/7 命令是 stub** — START_PAIRING / FULL_DEPLOY / AUTO_WIRELESS_PAIRING / DIRECT_PAIR 未接入 startPairFlow。

---

## 三、缺陷优先级汇总

### P0 — 阻断核心流程

| # | 缺陷 | 位置 | 影响 |
|---|------|------|------|
| P0-1 | `val executor` 不可重建 | SystemOptimizeManager.kt:1326 | finishLocalAdbPair 后无法重新 startPairFlow |
| P0-2 | 4 条 C2 命令是 stub | AdbTunnelCommandHandler.kt:171-331 | 远程配对命令静默失败 |

### P1 — 功能性缺失

| # | 缺陷 | 位置 | 影响 |
|---|------|------|------|
| P1-1 | Scene E (confirmLock) 未实现 | mainAccessibilityEventHandler | 有锁屏密码的设备配对卡住 |
| P1-2 | Scene F (securityCenter) 未实现 | mainAccessibilityEventHandler | MIUI 安全中心弹窗阻断 |
| P1-3 | c41 case 7 (SilentRecover) 未实现 | heartbeatEventDispatcher | local-service 掉线无法静默恢复 |
| P1-4 | deployLocalService 是 stub | SystemOptimizeManager.kt:2654 | ADB push binary 不工作 |
| P1-5 | isInDevOptionsWindow OPPO 真机失败 | isInDevOptionsWindow L1815 | 文本不在初始可见区域，findByText 找不到 |

### P2 — 鲁棒性/兼容性

| # | 缺陷 | 位置 | 影响 |
|---|------|------|------|
| P2-1 | WindowDetector 未复刻 | 全局 | MIUI/ColorOS 页面检测可靠性降低 |
| P2-2 | 包名 contains vs == 精确匹配 | isInWifiDebugWindow L1847 | 可能误匹配非 settings app |
| P2-3 | openDevOptionsRetryV2 case 2 缺少 BACK | openDevOptionsRetryV2 | vendor 先 BACK 再 retry |
| P2-4 | c41 case 4 (post-deploy init) 未实现 | heartbeatEventDispatcher | 部署后通知逻辑缺失 |

---

## 四、4 条触发路径对齐状态

```
路径 1: C2 远程命令 ──────────── ❌ 4/7 stub
    ├─ START_PAIRING       → ❌ stub
    ├─ AUTO_WIRELESS_PAIRING → ❌ stub
    ├─ FULL_DEPLOY         → ❌ stub
    ├─ DIRECT_PAIR         → ❌ stub
    ├─ DEPLOY_LOCAL_SERVICE → ⚠️ stub (deployLocalService returns false)
    ├─ OPEN_WIFI_DEBUG_SETTINGS → ✅
    └─ OPEN_ABOUT_PHONE    → ✅

路径 2: 权限自动化完成回调 ──── ✅ 对齐
    └─ yw5xud → OpenDevelopmentDelegate.onComplete → startPairFlow ✅

路径 3: AccessibilityEvent 被动 ─ ✅ 对齐 (接线已修复)
    ├─ MyAccessibilityService → filterAccessibilityEvent ✅
    ├─ mainAccessibilityEventHandler Scene A (devOpt) ✅
    ├─ mainAccessibilityEventHandler Scene B (wifiDebug) ✅
    ├─ mainAccessibilityEventHandler Scene D (pairFail) ✅
    ├─ mainAccessibilityEventHandler Scene E (confirmLock) ❌
    └─ mainAccessibilityEventHandler Scene F (securityCenter) ❌

路径 4: 心跳维护循环 H() ────── ⚠️ 部分
    ├─ local-service alive check ✅
    ├─ wireless debug check ✅
    ├─ enableWirelessDebugging → startPairFlow ✅
    ├─ SilentRecover (case 7) ❌
    └─ post-deploy init (case 4) ❌

统一执行入口 startPairFlow:
    ├─ 三路分发 ✅
    ├─ 120s/30s 超时调度 ✅
    ├─ executor 重建 ❌ P0
    └─ pairState/flags 管理 ✅
```

---

## 五、真机验证结果 (OPPO PGFM10, Android 16)

| 测试项 | 结果 | 详情 |
|-------|------|------|
| 接线 MyAccessibilityService → SystemOptimizeManager | ✅ | 事件正确到达 filterAccessibilityEvent |
| SystemOptimizeManager 懒初始化 | ✅ | 首次事件触发 getInstance() |
| filterAccessibilityEvent 接收 settings 事件 | ✅ | `[FILTER]` 日志确认 |
| isPairRunning 前置条件 | ✅ | isPairRunning=false 时静默 return（符合 vendor 设计） |
| debug_start_pair → startPairFlow | ✅ | 成功触发配对流程 |
| 三路分发 → else → openDevOptionsRetryV2 | ✅ | 3 次重试后放弃 |
| openDevOptionsRetryV2 打开开发者选项 Intent | ✅ | Intent 发送成功 |
| **isInDevOptionsWindow 在 OPPO 上检测** | **❌** | 页面文本需要滚动才可见，findByText 找不到 |

---

## 六、推荐修复顺序

### 第一轮 — P0 阻断修复
1. `val executor` → `var executor` + shutdown 重建
2. AdbTunnelCommandHandler 4 条 stub 接入 startPairFlow / extractPairingCodeAndPort + doPair

### 第二轮 — P1 功能补全
3. isInDevOptionsWindow 改用标题栏文本 "开发者选项" 而非页面内选项文本
4. Scene E (confirmLock) + Scene F (securityCenter)
5. c41 case 7 SilentRecover

### 第三轮 — P2 鲁棒性
6. WindowDetector 缓存层
7. 包名精确匹配
8. openDevOptionsRetryV2 加 BACK 键
