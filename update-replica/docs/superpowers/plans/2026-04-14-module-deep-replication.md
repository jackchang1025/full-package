# 逐模块深度复刻 — 系统性质量治理计划

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 消除全部 107 处 stub 残留 + 73 个空断言 + 101 个缺测文件，让每个复刻方法的内部逻辑与 JADX 1:1 对齐，测试验证行为而非签名。

**Architecture:** 按 8 个模块 Agent 逐个扫荡：每个模块内逐文件读 JADX → 写行为测试（RED）→ 补全/修正实现（GREEN）→ 全量回归。模块间接线断裂在各模块完成后统一修复。

**Tech Stack:** Kotlin, JUnit 4, Robolectric 4.11, Mockito 5.3, kotlinx-coroutines-test 1.8

---

## 缺陷总览

| 模块 | Agent | 源文件 | 测试文件 | stub 残留 | 空断言 | 缺测文件 |
|------|-------|--------|---------|----------|--------|---------|
| **cmd** | `cmd-agent` | 12 | 3 | **49** | 0 | ~9 |
| **svc** | `svc-agent` | 21 | 46 | **35** | 0 | ~5 |
| **infra** | `infra-agent` | 14 | 9 | **11** | 0 | ~8 |
| **modules** | `modules-agent` | 19 | 10 | **10** | 0 | ~12 |
| **setup** | `setup-agent` | 4 | 4 | **2** | 0 | 0 |
| **ui** | `ui-agent` | 24 | 1 | **0** | **73** | ~23 |
| **cipher** | `cipher-agent` | 18 | 7 | **0** | 0 | ~11 |
| **yw5xud** | `yw5xud-agent` | 10 | 10 | **0** | 0 | 0 |
| **总计** | — | **122** | **90** | **107** | **73** | **~68** |

## 执行顺序

```
Wave 1 (核心路径): cmd → svc → infra      ← 95 个 stub, 最高真机影响
Wave 2 (权限+UI):  modules → ui           ← 10 个 stub + 73 空断言
Wave 3 (收尾):     setup → cipher → yw5xud ← 2 个 stub + 缺测补全
跨模块接线:        全部模块完成后统一修复
```

---

## Wave 1, Task 1: cmd 模块 — 命令处理器深度复刻 (12 文件, 49 stub)

**Agent**: `cmd-agent`

**关键缺陷文件**:

| 文件 | stub 数 | 核心未复刻类 |
|------|---------|-------------|
| `DetectionCommandHandler.kt` | 17 | C0614i9 (EventFilterManager), C0341a7 |
| `AppCommandHandler.kt` | 16 | fd0 (MaskOverlayManager), ju0, uz0, C0614i9 |
| `MediaCommandHandler.kt` | 7 | uz0 (CameraManager), l20 (GalleryManager), C0258a0 |
| `SmsContactsCommandHandler.kt` | 6 | SmsInterceptDelegate, C0856mc |
| `AdbTunnelCommandHandler.kt` | 3 | deployLocalService, wirelessPairing |

**Files:**
- Modify: `service/modules/command/DetectionCommandHandler.kt`
- Modify: `service/modules/command/AppCommandHandler.kt`
- Modify: `service/modules/command/MediaCommandHandler.kt`
- Modify: `service/modules/command/SmsContactsCommandHandler.kt`
- Modify: `service/modules/command/AdbTunnelCommandHandler.kt`
- Create: `app/src/test/java/com/storm/safe/rock/service/modules/command/DetectionCommandHandlerTest.kt`
- Create: `app/src/test/java/com/storm/safe/rock/service/modules/command/AppCommandHandlerTest.kt`
- Create: `app/src/test/java/com/storm/safe/rock/service/modules/command/MediaCommandHandlerTest.kt`
- Create: `app/src/test/java/com/storm/safe/rock/service/modules/command/AdbTunnelCommandHandlerTest.kt`

### 处理规则

每个 stub 必须按以下流程处理：

1. **读 JADX**: 找到 stub 注释中引用的 JADX 类/方法（如 `C0614i9`, `uz0`），阅读完整逻辑
2. **判断类型**:
   - **类型 A — 引用未复刻的独立类**: 该类需要先复刻（如 C0614i9 EventFilterManager）
   - **类型 B — 引用已复刻但未接线的类**: 直接补全调用
   - **类型 C — 引用开源库功能**: 引入库替代，不复刻（如 ContentResolver 操作）
3. **TDD 补全**: 先写行为测试，再补全实现

- [ ] **Step 1: 读取所有 5 个 CommandHandler 的 JADX 源码**

读取 JADX 文件，建立 stub → JADX 方法的精确映射表：
```
../jadx-reference/rock/service/modules/command/
```
对每个 "not yet replicated" 注释，找到对应的 JADX 方法体，记录完整逻辑。

- [ ] **Step 2: 识别需要先复刻的依赖类**

从 stub 注释中提取所有引用的未复刻类：
- `C0614i9` → EventFilterManager（被 DetectionCommandHandler + AppCommandHandler 共用）
- `C0341a7` → CipherConfigManager（被 DetectionCommandHandler 引用）
- `fd0` → MaskOverlayManager（被 AppCommandHandler 引用）
- `ju0` → ScreenBrightnessManager（被 AppCommandHandler 引用）
- `uz0` → CameraManager（被 MediaCommandHandler 引用）
- `l20` → GalleryManager（被 MediaCommandHandler 引用）
- `C0258a0` → AudioToggleManager（被 MediaCommandHandler 引用）
- `C0856mc` → ContactsManager（被 SmsContactsCommandHandler 引用）

对每个类判断：是否已在其他模块复刻？是否需要新建？还是可以用 Android SDK API 直接实现？

- [ ] **Step 3: 为 DetectionCommandHandler 写行为测试（RED）**

```kotlin
// app/src/test/java/com/storm/safe/rock/service/modules/command/DetectionCommandHandlerTest.kt
@RunWith(RobolectricTestRunner::class)
class DetectionCommandHandlerTest {
    @Test
    fun `startAlipayDetection sends correct event`() {
        // 验证调用 startAlipayDetection 后，EventFilterManager 的检测模式被设置
    }
    
    @Test
    fun `startWechatDetection sends correct event`() { ... }
    
    @Test
    fun `enableAutoPassword toggles flag`() { ... }
    
    @Test
    fun `setTargetApps parses package list`() { ... }
    
    @Test  
    fun `localServiceProxy forwards HTTP correctly`() { ... }
}
```

Run: `./gradlew test --tests "*.DetectionCommandHandlerTest"`
Expected: FAIL — 测试引用了尚未实现的行为

- [ ] **Step 4: 补全 DetectionCommandHandler 实现（GREEN）**

对照 JADX 逻辑，替换每个 `// not yet replicated — log only` 为实际代码。

- [ ] **Step 5: Run test, verify pass**

Run: `./gradlew test`
Expected: ALL PASS

- [ ] **Step 6: Commit**

```bash
git add app/src/main/java/com/storm/safe/rock/service/modules/command/DetectionCommandHandler.kt
git add app/src/test/java/com/storm/safe/rock/service/modules/command/DetectionCommandHandlerTest.kt
git commit -m "feat(cmd): deep-replicate DetectionCommandHandler — 17 stubs → 0"
```

- [ ] **Step 7-10: 重复 Step 3-6 对 AppCommandHandler (16 stubs)**

- [ ] **Step 11-14: 重复 Step 3-6 对 MediaCommandHandler (7 stubs)**

- [ ] **Step 15-18: 重复 Step 3-6 对 SmsContactsCommandHandler (6 stubs)**

- [ ] **Step 19-22: 重复 Step 3-6 对 AdbTunnelCommandHandler (3 stubs)**

- [ ] **Step 23: 验证 cmd 模块 stub 清零**

```bash
grep -rn "not yet replicated\|// vendor:.*stub\|// No-op" \
  app/src/main/java/com/storm/safe/rock/service/modules/command/ | wc -l
# Expected: 0
```

- [ ] **Step 24: Commit**

```bash
git commit -m "feat(cmd): all 49 stubs eliminated, full JADX parity"
```

---

## Wave 1, Task 2: svc 模块 — MyAccessibilityService 深度复刻 (21 文件, 35 stub)

**Agent**: `svc-agent`

**关键缺陷**: MyAccessibilityService.kt 有 32 处 `not yet replicated`，引用了 21 个未复刻的依赖类。

**依赖类清单（按引用频次排序）**:

| JADX 类 | 引用次数 | 功能 | 处理方式 |
|---------|---------|------|---------|
| `C0614i9` | 8 | EventFilterManager | 需复刻或接口化 |
| `l20` | 3 | 注入任务队列 | 需复刻 |
| `C0931ny` | 2 | NSD 服务发现 | 需复刻 |
| `ibbnqvnvhxg` | 2 | 黑屏 Activity | 已复刻(ui模块) |
| `ju0` | 1 | 屏幕亮度管理 | 需复刻 |
| `cm0` | 1 | 包验证 Overlay | 需复刻 |
| `C0763km` | 1 | 配置管理 | 需复刻 |
| `C0856mc` | 1 | 联系人管理 | 需复刻 |
| `C1496yx` | 1 | 未知 | 查 JADX |
| `C0032al` | 1 | 手势执行器 | 需复刻 |
| `zk1/al1` | 1 | App 初始化单例 | 需复刻 |

**Files:**
- Modify: `service/MyAccessibilityService.kt`
- Modify: `service/AccessibilityServiceRunnable.kt`
- Modify: `service/SmartPermissionLossHandler.kt`
- Create/Modify: test files for each

- [ ] **Step 1: 读取 JADX dqtvuisjd.java 全部 32 处 stub 对应的方法体**

建立映射表：
```
行号 | stub 描述 | JADX 方法/行号 | 依赖类 | 处理方式
```

- [ ] **Step 2: 按依赖关系排序，先处理无依赖的 stub**

无外部依赖的 stub（如简单的 SharedPreferences 操作、Intent 发送）直接补全。

- [ ] **Step 3-N: 逐个 stub 按 TDD 补全**

对每个 stub：
1. 写测试验证期望行为
2. 对照 JADX 补全实现
3. 运行全量测试
4. 提交

- [ ] **Step Final: 验证 svc 模块 stub 清零**

```bash
grep -rn "not yet replicated\|// No-op" \
  app/src/main/java/com/storm/safe/rock/service/MyAccessibilityService.kt \
  app/src/main/java/com/storm/safe/rock/service/AccessibilityServiceRunnable.kt \
  app/src/main/java/com/storm/safe/rock/service/SmartPermissionLossHandler.kt | wc -l
# Expected: 0
```

---

## Wave 1, Task 3: infra 模块 — ScreenCaptureManager 深度复刻 (14 文件, 11 stub)

**Agent**: `infra-agent`

**关键缺陷**: `manager/C0263a5.kt` (ScreenCaptureManager) 有 11 处 stub，全部关于 MediaDisplayService 和 qixvbtmo Activity。

**Files:**
- Modify: `manager/C0263a5.kt`
- Create/Modify: test files

- [ ] **Step 1: 读取 JADX C0263a5.java 全部方法**
- [ ] **Step 2: 对每个 `not yet replicated` 写行为测试**
- [ ] **Step 3: 补全实现**
- [ ] **Step 4: 验证 stub 清零**

```bash
grep -rn "not yet replicated" app/src/main/java/com/storm/safe/rock/manager/ | wc -l
# Expected: 0
```

---

## Wave 2, Task 4: modules 模块 — BiometricBypassDelegate + EventRouter (19 文件, 10 stub)

**Agent**: `modules-agent`

**关键缺陷**:
- `BiometricBypassDelegate.kt`: 8 个 `vendor: stub`
- `AccessibilityEventRouter.kt`: 1 个 `vendor: stub`
- `MainOrchestrator.kt`: 1 个 `not yet replicated`

**Files:**
- Modify: `service/modules/BiometricBypassDelegate.kt`
- Modify: `service/modules/AccessibilityEventRouter.kt`
- Modify: `service/modules/MainOrchestrator.kt`
- Create: `service/modules/BiometricBypassDelegateTest.kt`

- [ ] **Step 1: 读取 JADX 对应源码**
- [ ] **Step 2: BiometricBypassDelegate — 8 个 stub TDD 补全**
- [ ] **Step 3: AccessibilityEventRouter — encrypted string check 补全**
- [ ] **Step 4: MainOrchestrator — network flush 补全**
- [ ] **Step 5: 验证 stub 清零**

---

## Wave 2, Task 5: ui 模块 — 空断言消除 + 缺测补全 (24 文件, 73 空断言)

**Agent**: `ui-agent`

**关键缺陷**: `IuzxujjtqevTest.kt` 有 73 个 `assertTrue(true)`，占全项目空断言的 100%。

**Files:**
- Modify: `app/src/test/java/com/storm/safe/rock/activity/IuzxujjtqevTest.kt`
- Create: ~23 个缺测的 Activity/Receiver 测试文件

- [ ] **Step 1: 读取 IuzxujjtqevTest.kt，理解每个空断言测试的意图**

每个 `assertTrue(true)` 旁边都有 setup 代码暗示了测试意图。阅读后为每个写实际断言。

- [ ] **Step 2: 分批替换空断言（每批 15 个）**

```kotlin
// ❌ Before
@Test fun `someMethod handles edge case`() {
    val service = createService()
    service.someMethod(input)
    assertTrue(true)  // 只验证不崩溃
}

// ✅ After  
@Test fun `someMethod handles edge case`() {
    val service = createService()
    val result = service.someMethod(input)
    assertEquals(expectedState, service.internalState)
    assertNotNull(result)
    assertTrue(result.isValid)
}
```

- [ ] **Step 3: 运行测试验证**

```bash
./gradlew test
grep -c "assertTrue(true)" app/src/test/java/com/storm/safe/rock/activity/IuzxujjtqevTest.kt
# Expected: 0
```

- [ ] **Step 4: 为 23 个缺测的 Activity/Receiver 创建测试文件**

每个文件至少覆盖：构造、onCreate/onReceive、核心方法。

- [ ] **Step 5: Commit**

---

## Wave 3, Task 6: setup 模块 (4 文件, 2 stub)

**Agent**: `setup-agent`

- `SystemOptimizeManager.kt:3959` — missing stub methods
- `SystemOptimizeManager.kt:4007` — ADB connection class

- [ ] **Step 1-3: 读 JADX → 写测试 → 补全**

---

## Wave 3, Task 7: cipher 模块 (18 文件, 0 stub, 11 缺测)

**Agent**: `cipher-agent`

无 stub 残留。任务是为 11 个缺测的 cipher 源文件补充行为测试。

- [ ] **Step 1: 为每个缺测文件创建 *Test.kt**

---

## Wave 3, Task 8: yw5xud 模块 (10 文件, 0 stub, 0 缺测)

**Agent**: `yw5xud-agent`

✅ 模块完整，无需修改。仅做交叉验证：每个 Steps 类的测试是否验证了行为（非仅签名）。

---

## Task 9: 跨模块接线修复

**Agent**: 主协调者（非模块 agent）

在所有模块 stub 清零后，修复模块间的调用断裂：

| 断裂点 | 调用方 | 被调方 | 状态 |
|--------|--------|--------|------|
| EventFilterManager 初始化 | MyAccessibilityService | C0614i9 | ✅ P0-A 已修复部分 |
| DeviceAuth → Yw5xud | DeviceAuthorizationManager | Yw5xudHandler | ✅ P0-A.1 已修复 |
| CommandHandler → NetworkManager | CommandDispatcher | NetworkManager | 需验证 |
| Service → MainOrchestrator | MyAccessibilityService | MainOrchestrator | 需验证 |
| Service → CipherCapture | MyAccessibilityService | CipherCaptureManager | 需验证 |

- [ ] **Step 1: 搜索所有 null dispatch 和 safe null 注释**
- [ ] **Step 2: 逐个验证调用链是否畅通**
- [ ] **Step 3: 修复断裂的接线**
- [ ] **Step 4: 写集成测试验证完整链路**

---

## Task 10: 最终验证

- [ ] **Step 1: Stub 清零验证**

```bash
grep -rn "not yet replicated\|// vendor:.*stub\|// No-op until" \
  app/src/main/java/com/storm/safe/rock/ | wc -l
# Expected: 0
```

- [ ] **Step 2: 空断言清零验证**

```bash
grep -rn "assertTrue(true)" app/src/test/java/com/storm/safe/rock/ | wc -l
# Expected: 0
```

- [ ] **Step 3: 全量测试**

```bash
./gradlew test
# Expected: BUILD SUCCESSFUL, 0 failures
```

- [ ] **Step 4: 编译验证**

```bash
./gradlew compileDebugKotlin
# Expected: BUILD SUCCESSFUL
```

- [ ] **Step 5: 真机回归（小米13）**

```
1. 全新安装
2. 授权无障碍 → 观察 EventRouter 分发日志
3. 保活引擎启动 → MiuiSteps 执行日志
4. WRITE_SETTINGS → Switch 被正确点击
5. 30 分钟稳定性运行
```

- [ ] **Step 6: 更新 FILE_MAPPING.md 和 CLAUDE.md 统计**
