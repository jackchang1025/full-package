# 华为 Step 幂等性全面修复 — executeAll checkpoint + 所有 Step 入口 SP 幂等

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 修复华为进程被 Pged 杀死后 executeAll 完整重跑 bug，并统一所有 Step (Step 1-8) 入口的 SP 幂等检查，让已完成的 Step 在下次 executeAll 时直接跳过（用户要求："所有 step 开启权限时验证已开启就跳过执行，Step 5 关闭则已关闭就跳过"）。

**Architecture:** 三层防护：
- **Layer 1 (L1)**: executeAll 末尾即调 `markAuthCompleted` — 不等 dqtvuisjd 判定
- **Layer 2 (L2)**: `startAuthorization` 检查所有关键子步骤 SP — Step 5/6/7/8 都 mark 就视为已完成
- **Layer 3 (L3)**: **每个 Step 入口加 SP 幂等 fast-path** — 已完成直接 return，不进 UI 操作
- **额外**: Step 5 "Switch 已是 unchecked" 分支补 markCompleted（幂等等价跳过）

**Tech Stack:** Kotlin 1.9 + Android AccessibilityService + HuaweiStepCompletionStore

**硬约束**：不 git commit / 不跑 test/build / 只用 `./gradlew compileDebugKotlin`

---

## 当前每个 Step 的幂等检查现状

| Step | 幂等检查状态 | 位置 | 缺失内容 |
|------|------------|------|---------|
| Step 1 基础权限 | 无 SP，但每次只跑 10s | L550 | 无（短操作） |
| Step 2 电池白名单 | 有 `isIgnoringBatteryOptimizations()` 快路径 | L705 | **缺 SP mark + SP 幂等** |
| Step 3 电池设置 | ✅ STEP3_OVERALL + network_on_sleep | L955, L960 | 无 |
| Step 4 通知监听 (Honor) | ✅ STEP4_NOTIFICATION_LISTENER | L1560 | 无 |
| Step 5 自启动 | ❌ **无** | L1718 | **T4 补 STEP5_AUTOSTART** |
| Step 6 悬浮窗 | ✅ canDrawOverlays + STEP6_OVERLAY | L2068 | 无 |
| Step 7 通知关闭 | ✅ STEP7_NOTIFICATION_OFF | L2749 | 无 |
| Step 8 所有文件 | ✅ isExternalStorageManager + STEP8_ALL_FILES | L2901 | 无 |

---

## Task 1 — executeAll 末尾 markAuthCompleted checkpoint

**Files:** `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiSteps.kt`

- [ ] **Step 1: 顶部 import 加 DeviceAuthorizationManager**

读当前 import 块（~L1-30），在合适位置加：
```kotlin
import com.storm.safe.rock.service.modules.DeviceAuthorizationManager
```

- [ ] **Step 2: 在 executeAll 末尾追加 checkpoint**

定位当前 executeAll 末尾（搜索 `successes.add("HuaweiSteps: 华为/荣耀权限配置完成")`）：
```kotlin
        android.util.Log.i("HuaweiSteps", "╚══════════ executeAll 完成 ══════════")
        android.util.Log.i("HuaweiSteps", "║ success=${successes.size} failure=${failures.size}")
        successes.add("HuaweiSteps: 华为/荣耀权限配置完成")
    }
```

替换末尾为：
```kotlin
        android.util.Log.i("HuaweiSteps", "╚══════════ executeAll 完成 ══════════")
        android.util.Log.i("HuaweiSteps", "║ success=${successes.size} failure=${failures.size}")
        successes.add("HuaweiSteps: 华为/荣耀权限配置完成")

        // ADAPT: 真机加固 — Pged-Freezer 可能在 Step 9 清除任务时杀进程导致上层
        // markAuthCompleted 永不执行。提前 checkpoint，executeAll 核心流程完成即持久化。
        try {
            DeviceAuthorizationManager.markAuthCompleted(context)
            android.util.Log.i("HuaweiSteps", "║ ✅ markAuthCompleted (executeAll 末尾 checkpoint)")
        } catch (e: Exception) {
            android.util.Log.w("HuaweiSteps", "║ ⚠️ markAuthCompleted 失败: ${e.message}")
        }
    }
```

- [ ] **Step 3: 验证编译**

```bash
cd /home/code/php/project/full-package/update-replica && ./gradlew compileDebugKotlin 2>&1 | tail -10
```

---

## Task 2 — startAuthorization 子步骤 SP 幂等 fallback

**Files:** `app/src/main/java/com/storm/safe/rock/service/modules/DeviceAuthorizationManager.kt`

- [ ] **Step 1: 在 startAuthorization 的 L210 检查块后追加子步骤 fallback**

读 L194-234 确认当前代码结构，在 `if (completed && savedBrand == currentBrand) { ... return }` block 之后、`// Check app_state fallback` 行之前，追加：

```kotlin
            // ADAPT: 真机加固 — 华为 Pged 可能在 executeAll 末尾杀进程导致
            // authorization_completed 全局 flag 未写。子步骤 SP 是内部 checkpoint，
            // 若 Step 5/6/7/8 全部已 mark 即视为核心授权已完成（Step 9 是清除任务非核心）。
            if (currentBrand == "huawei" || currentBrand == "honor") {
                val keys = com.storm.safe.rock.service.modules.yw5xud.HuaweiStepCompletionStore.Keys
                val store = com.storm.safe.rock.service.modules.yw5xud.HuaweiStepCompletionStore
                val step5 = store.isCompleted(context, keys.STEP5_AUTOSTART)
                val step6 = store.isCompleted(context, keys.STEP6_OVERLAY)
                val step7 = store.isCompleted(context, keys.STEP7_NOTIFICATION_OFF)
                val step8 = store.isCompleted(context, keys.STEP8_ALL_FILES)
                if (step5 && step6 && step7 && step8) {
                    Log.i(TAG, "✅ 子步骤 SP 全部已 mark（Step 5/6/7/8），视为已完成，同步全局 flag")
                    try {
                        prefs.edit()
                            .putBoolean("authorization_completed", true)
                            .putString("authorization_brand", currentBrand)
                            .putLong("authorization_time", System.currentTimeMillis())
                            .apply()
                    } catch (_: Exception) {}
                    onAuthorizationDone()
                    return
                }
            }

```

- [ ] **Step 2: 验证编译**

```bash
cd /home/code/php/project/full-package/update-replica && ./gradlew compileDebugKotlin 2>&1 | tail -10
```

---

## Task 3 — Step 5 入口加 STEP5_AUTOSTART SP 幂等 fast-path

**Files:** `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiSteps.kt` (~L1718-1725)

当前 executeStep5AutoStart 入口（~L1718-1725）：
```kotlin
    open suspend fun executeStep5AutoStart(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        android.util.Log.i("HuaweiSteps", "[Step5/10] enter executeStep5AutoStart")
        HuaweiStepLogger.phase(5, "自启动权限开始", "vendor m212164b1 + m212196f3", logs)

        // Vendor m212196f3 (L6861-6879): iterate STARTUP_COMPONENTS, try startActivity
```

- [ ] **Step 1: 在 `HuaweiStepLogger.phase(5, ...)` 之后、`// Vendor m212196f3 ...` 之前加入 SP 幂等检查**

替换为：
```kotlin
    open suspend fun executeStep5AutoStart(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        android.util.Log.i("HuaweiSteps", "[Step5/10] enter executeStep5AutoStart")
        HuaweiStepLogger.phase(5, "自启动权限开始", "vendor m212164b1 + m212196f3", logs)

        // ADAPT: 真机幂等 — 若 STEP5_AUTOSTART 24h 内已 mark，跳过整个 UI 流程
        // (对齐 Step 3/4/7 的入口幂等 fast-path)
        if (HuaweiStepCompletionStore.isCompleted(context, HuaweiStepCompletionStore.Keys.STEP5_AUTOSTART)) {
            HuaweiStepLogger.skip(5, "SP STEP5_AUTOSTART 24h 内已 mark", logs)
            successes.add("[Step5/10] 自启动权限已配置（SP 幂等跳过）")
            return
        }

        // Vendor m212196f3 (L6861-6879): iterate STARTUP_COMPONENTS, try startActivity
```

- [ ] **Step 2: 验证编译**

```bash
cd /home/code/php/project/full-package/update-replica && ./gradlew compileDebugKotlin 2>&1 | tail -10
```

---

## Task 4 — Step 5 "Switch 已是 unchecked" 分支补 markCompleted

**Files:** `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiSteps.kt` (~L1919-1922)

当前代码（L1919-1922）：
```kotlin
                    } else {
                        // checked=false = 已是手动管理，可能之前已配置
                        HuaweiStepLogger.warn(5, "Switch 已是 unchecked（手动管理）", "可能已配置", logs)
                    }
```

- [ ] **Step 1: 替换为补 markCompleted**

```kotlin
                    } else {
                        // ADAPT: 真机幂等 — checked=false 表示"自动管理"已关闭
                        // = Step 5 之前已配置过（进入手动管理模式后三 switch 由上次脚本/用户开启）
                        // 直接 markCompleted 并 skip，避免下次 executeAll 重新触发三 switch 弹窗
                        HuaweiStepLogger.warn(5, "Switch 已是 unchecked（手动管理）", "视为已配置，mark 跳过", logs)
                        HuaweiStepCompletionStore.markCompleted(context,
                            HuaweiStepCompletionStore.Keys.STEP5_AUTOSTART)
                        successes.add("[Step5/10] 自启动已是手动管理模式（幂等 mark）")
                    }
```

- [ ] **Step 2: 验证编译**

```bash
cd /home/code/php/project/full-package/update-replica && ./gradlew compileDebugKotlin 2>&1 | tail -10
```

---

## Task 5 — Step 2 入口加 SP 幂等 fast-path + Step 2 成功时 markCompleted

**背景**: Step 2 有 `isIgnoringBatteryOptimizations()` 快路径，但**不写 SP mark**。当 OS 临时清除 granted state（例如用户手动取消后）下次仍会再试。SP mark 做持久化幂等。

**Files:** `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiSteps.kt` (~L698-710)

当前入口（~L698-710）：
```kotlin
    open suspend fun executeStep2BatteryWhitelist(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        android.util.Log.i("HuaweiSteps", "[Step2/10] enter executeStep2BatteryWhitelist")
        HuaweiStepLogger.phase(2, "检查是否已忽略电池优化", "vendor L2544", logs)

        // Vendor L2551-2557 快路径：已在白名单 → 成功返回
```

- [ ] **Step 1: 读 L710-730 范围确认"已在白名单"分支的 return 处并捕获**

```bash
cd /home/code/php/project/full-package/update-replica && awk 'NR>=705 && NR<=735' app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiSteps.kt
```

注意此分支通常是:
```kotlin
        if (isIgnoringBatteryOptimizations()) {
            logs.add("[Step2/10] 已在电池优化白名单，跳过")
            successes.add("[Step2/10] 已在白名单")
            return
        }
```

- [ ] **Step 2: 在入口 `HuaweiStepLogger.phase(2, ...)` 之后加 SP 幂等检查**

替换为：
```kotlin
    open suspend fun executeStep2BatteryWhitelist(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        android.util.Log.i("HuaweiSteps", "[Step2/10] enter executeStep2BatteryWhitelist")
        HuaweiStepLogger.phase(2, "检查是否已忽略电池优化", "vendor L2544", logs)

        // ADAPT: 真机幂等 — 若 STEP2_BATTERY_WHITELIST 24h 内已 mark 直接跳过
        if (HuaweiStepCompletionStore.isCompleted(context, HuaweiStepCompletionStore.Keys.STEP2_BATTERY_WHITELIST)) {
            HuaweiStepLogger.skip(2, "SP STEP2_BATTERY_WHITELIST 24h 内已 mark", logs)
            successes.add("[Step2/10] 电池白名单已配置（SP 幂等跳过）")
            return
        }

        // Vendor L2551-2557 快路径：已在白名单 → 成功返回
```

- [ ] **Step 3: 在"已在白名单"快路径 return 前补 markCompleted**

找到 `if (isIgnoringBatteryOptimizations()) { ... return }` 这个 block，在 `return` 之前加 `HuaweiStepCompletionStore.markCompleted(context, HuaweiStepCompletionStore.Keys.STEP2_BATTERY_WHITELIST)`。

具体代码（read 后 Edit）:

在 `successes.add("[Step2/10] 已在白名单")` 之前或之后插入：
```kotlin
            HuaweiStepCompletionStore.markCompleted(context, HuaweiStepCompletionStore.Keys.STEP2_BATTERY_WHITELIST)
```

- [ ] **Step 4: 验证编译**

```bash
cd /home/code/php/project/full-package/update-replica && ./gradlew compileDebugKotlin 2>&1 | tail -10
```

---

## Task 6 — 真机验证

- [ ] **Step 1: 构建 + 部署**

```bash
cd /home/code/php/project/full-package/update-replica
./gradlew :app:assembleDebug 2>&1 | tail -3
ADB=/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe
$ADB -s 2TV9K24710071129 shell am force-stop dev.deltalab2964.swift
$ADB -s 2TV9K24710071129 shell pm clear dev.deltalab2964.swift
$ADB -s 2TV9K24710071129 uninstall dev.deltalab2964.swift
$ADB -s 2TV9K24710071129 install -r -g app/build/outputs/apk/debug/app-debug.apk
$ADB -s 2TV9K24710071129 shell monkey -p dev.deltalab2964.swift -c android.intent.category.LAUNCHER 1
$ADB -s 2TV9K24710071129 logcat -c
```

- [ ] **Step 2: 用户开启无障碍**

- [ ] **Step 3: 第一次 run 完整等 90s 抓日志**

```bash
sleep 90
$ADB -s 2TV9K24710071129 logcat -d | grep -E "HuaweiSteps|HwStepStore|markAuthCompleted|startAuthorization|SP.*已 mark|已完成" > /tmp/verify-run1.log
wc -l /tmp/verify-run1.log
```

期望：
- `✅ markAuthCompleted (executeAll 末尾 checkpoint)`
- `HwStepStore: markCompleted(huawei_step2_battery_whitelist_done)` 新增
- `HwStepStore: markCompleted(huawei_step5_autostart_done)`
- `HwStepStore: markCompleted(huawei_step6/7/8_*_done)`

- [ ] **Step 4: 第二次 run — 强杀 app + monkey 重启，验证幂等**

```bash
$ADB -s 2TV9K24710071129 shell am force-stop dev.deltalab2964.swift
sleep 3
$ADB -s 2TV9K24710071129 logcat -c
$ADB -s 2TV9K24710071129 shell monkey -p dev.deltalab2964.swift -c android.intent.category.LAUNCHER 1
sleep 15
$ADB -s 2TV9K24710071129 logcat -d | grep -E "HuaweiSteps|startAuthorization|SP.*已 mark|幂等|skip" > /tmp/verify-run2.log
cat /tmp/verify-run2.log
```

期望（Run 2 应快速结束）：
- `✅ 子步骤 SP 全部已 mark（Step 5/6/7/8），视为已完成`
- 或者进入 executeAll 但每个 Step 都走 SP 幂等 fast-path：
  - `[Step 2/10] ⏭ SKIP | SP STEP2_BATTERY_WHITELIST 24h 内已 mark`
  - `[Step 3/10] ⏭ SKIP | 已完成 (STEP3_OVERALL 24h 内 mark)`
  - `[Step 5/10] ⏭ SKIP | SP STEP5_AUTOSTART 24h 内已 mark`
  - `[Step 6/10] ✅ 已有悬浮窗权限，跳过`
  - `[Step 7/10] ⏭ SKIP | isStep7Completed`
  - `[Step 8/10] 已 Environment.isExternalStorageManager`

---

## Self-Review

### 1. Spec coverage

| 用户需求 | Task |
|---------|------|
| "串行执行" | T1 + T2（单次 executeAll 内部本来就串行，真正问题是 executeAll 重复调用） |
| "开启权限已开启就跳过" | T3 (Step 5) + T5 (Step 2) — 其他 Step (3/4/6/7/8) 已有 |
| "Step 5 关闭就是已关闭就跳过" | T4 (L1919 分支补 mark) — 对齐"关闭自动管理"语义 |
| "Step 5 完成后还重复执行" | T1 + T2 + T3 组合 |

### 2. Placeholder scan

- [x] 无 "TBD" / "TODO: fill"
- [x] Task 5 Step 1 命令返回结果可能有分支 — 但给了明确的 bash 命令读源码
- [x] 所有 Edit 有完整 old → new

### 3. Type consistency

- [x] `DeviceAuthorizationManager.markAuthCompleted(context: Context)` — `@JvmStatic` (JADX a1)
- [x] `HuaweiStepCompletionStore.isCompleted(context, key)` + `markCompleted(context, key)` — 签名正确
- [x] `HuaweiStepLogger.skip(step, message, logs)` — 3 个 step/phase/skip/warn/fail 系列均已存在
- [x] `HuaweiStepCompletionStore.Keys.STEP2_BATTERY_WHITELIST` / `STEP5_AUTOSTART` — 现有常量（BF-A2 之前已验证）

---

## 三层防护逻辑概述

```
进程 A (Run 1)
  executeAll 开始
    Step 1 done
    Step 2 done → STEP2_BATTERY_WHITELIST mark ⭐ (T5)
    ...
    Step 8 done → STEP8_ALL_FILES mark
    [L1] markAuthCompleted at end of executeAll ⭐ (T1)
  executeAll 完成
  [finally] DeviceAuthorizationManager.onAuthResult → markAuthCompleted (第二次也 OK)

进程 A 被 Pged 杀死（假设在 Step 9 之后但 finally 之前）
  → L1 已经持久化 ✅

进程 B (Run 2) 启动
  startAuthorization 被触发
    [L2] 检查 authorization_completed=true → 直接 onAuthorizationDone ✅
    (不再调用 executeAll)

极端情况：进程 A 在 Step 6 被杀（L1 未执行）
  startAuthorization
    [L2] authorization_completed=false
    [L2] 检查子步骤 SP: Step5 mark but Step6 not → fallback 不生效
    → 调用 executeAll
      Step 2 SP mark → [L3] fast-path skip ✅ (T5)
      Step 3 SP mark → [L3] fast-path skip ✅ (已有)
      Step 5 SP mark → [L3] fast-path skip ✅ (T3)
      Step 6 未 mark → 正常执行
      Step 7/8 正常执行
      [L1] markAuthCompleted ✅
```
