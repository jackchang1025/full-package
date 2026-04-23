# Step 5 BFS Scroll + Step 3 页面验证修复

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 修复 Step 5 自启动 BFS 导航滚动无效 + Step 3 电池设置页面误匹配两个真机 bug，使华为 FIN-AL60 HarmonyOS 4.2 上 Step 3 和 Step 5 能正确执行。

**Architecture:** 两处修复都在 `HuaweiSteps.kt` 内，核心改动是：(1) Step 5 BFS scroll 改用已有 `findFirstScrollableNode` helper 替代 `rootInActiveWindow.performAction`；(2) 移除 Step 5 的 `launchAppDetailsSettings` 干扰调用（它会留一个应用详情页 Activity 影响后续 rootInActiveWindow）；(3) Step 3 `findAndClickBattery` 的 scroll 同样改用 `findFirstScrollableNode`。

**Tech Stack:** Kotlin 1.9 + Android AccessibilityService + JUnit 4 + Robolectric

**硬约束**（与 P2 相同）：
- **不 git commit** — 后续统一
- **不跑 `./gradlew test` / `build`** — 只用 `./gradlew compileDebugKotlin`
- **TDD**: 先写测试 → 编译失败 → 写实现 → 编译通过

---

## 根因分析

### 真机日志证据

```
[Step 5/10] ▶ BFS 导航: 设置主页 → 应用和服务 → 启动管理
[Step 5/10] ⚠️ BFS 第1级未找到应用和服务入口 | 已滚动 4 次
```

```
[Step 3/10] 🔍 findAndClickBattery = 首屏未找到，开始滚动查找 (max 3 scroll)
[Step 3/10] 🔍 findAndClickBattery = 3 次滚动后仍未找到
```

### 根因 #1 — scroll 对象错误（Step 5 + Step 3 共享）

`HuaweiSteps.kt` 已有 `findFirstScrollableNode(root)` (L1392-1405) 做 3 层 DFS 查找 `isScrollable=true` 节点。

但 **Step 5 BFS** (L1759-1760) 和 **Step 3 findAndClickBattery** (L1196) 都直接在 `rootInActiveWindow`（通常是 FrameLayout/DecorView，**不可滚动**）上执行 `ACTION_SCROLL_FORWARD`，导致滚动无效。

而同文件的 `scrollAndClickMoreBatterySettings` (L1292) 正确使用了 `findFirstScrollableNode` — 证明 helper 可工作，只是两处忘了调用。

### 根因 #2 — Step 5 `launchAppDetailsSettings` 干扰

Step 5 代码路径（L1712-1730 when `!launched`）：
1. L1720: `StartupFallbackNavigator.launchAppDetailsSettings(service)` — 打开**应用详情页**
2. L1732: `startActivity(Settings.ACTION_SETTINGS)` — 打开**设置主页**
3. L1750: BFS 开始搜索"应用和服务"

问题：L1720 打开了应用详情页留在 task stack 顶层，L1732 再打开设置主页。但 `rootInActiveWindow` 可能仍指向应用详情页窗口（两个 Activity 叠加时焦点窗口不确定）。移除 L1720 的 `launchAppDetailsSettings` 消除干扰。

### 根因 #3 — Step 3 误匹配 app 内界面

Run 2 时 `findAndClickBattery` 在 app 自身界面上匹配到"本应用需要无障碍权限才能正常运行"。`openSettingsWithVerify` 返回 false（成功），但实际上设置页可能被其他 Flow 抢走。需要在 `findAndClickBattery` 入口加包名验证。

---

## File Structure

### 修改文件（1 个源码 + 0 个新建）

| 文件 | 修改范围 |
|------|---------|
| `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiSteps.kt` | Step 5 BFS L1749-1762；Step 5 fallback L1712-1726；Step 3 findAndClickBattery L1194-1197；findFirstScrollableNode 可见性 L1392 |

---

## Task 1 — Step 5 BFS scroll 修复 + 移除 launchAppDetailsSettings 干扰

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiSteps.kt`

- [ ] **Step 1: 读当前 Step 5 BFS 代码**

```bash
cd /home/code/php/project/full-package/update-replica
awk 'NR>=1710 && NR<=1770' app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiSteps.kt
```

确认：
- L1720 有 `StartupFallbackNavigator.launchAppDetailsSettings(service)`
- L1732-1741 有 `Settings.ACTION_SETTINGS` 打开设置主页
- L1759-1760 有 `svcRoot?.performAction(ACTION_SCROLL_FORWARD)` 直接在 root 上 scroll

- [ ] **Step 2: 移除 `launchAppDetailsSettings` fallback + 它的日志**

在 `executeStep5AutoStart` 的 `if (!launched) {` 分支中，找到 L1717-1726 整个 `StartupFallbackNavigator.launchAppDetailsSettings` 块：

```kotlin
            HuaweiStepLogger.warn(5, "4 个 STARTUP_COMPONENTS 全部被拒，尝试 AppDetailsSettings fallback",
                "vendor 原版同样失败（无 USE_COMPONENT 权限）", logs)
            val fallbackOk = StartupFallbackNavigator.launchAppDetailsSettings(service)
            if (fallbackOk) {
                HuaweiStepLogger.warn(5, "已打开应用详情页 — 用户需手动完成自启动管理",
                    "vendor 原版无 fallback", logs)
            } else {
                HuaweiStepLogger.fail(5, "fallback 也失败", "系统级阻断", failures)
            }
```

替换为简化的日志行（不再打开应用详情页，直接进入 BFS 导航）：

```kotlin
            HuaweiStepLogger.warn(5, "4 个 STARTUP_COMPONENTS 全部被拒",
                "直接进入 BFS 设置主页导航", logs)
```

- [ ] **Step 3: 修复 BFS scroll — 用 `findFirstScrollableNode`**

找到 L1758-1761 的 scroll 代码块：

```kotlin
                // 未找到 → 向下滚动
                val svcRoot = try { service?.rootInActiveWindow } catch (_: Exception) { null }
                svcRoot?.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
                delay(800L)
```

替换为使用 `findFirstScrollableNode`：

```kotlin
                // 未找到 → 向下滚动（DFS 查找 scrollable 子节点，不在 root 上直接 scroll）
                val svcRoot = try { service?.rootInActiveWindow } catch (_: Exception) { null }
                val scrollTarget = findFirstScrollableNode(svcRoot) ?: svcRoot
                scrollTarget?.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
                delay(800L)
```

差异：`svcRoot?.performAction(...)` → `(findFirstScrollableNode(svcRoot) ?: svcRoot)?.performAction(...)`

- [ ] **Step 4: 验证编译**

```bash
cd /home/code/php/project/full-package/update-replica && ./gradlew compileDebugKotlin 2>&1 | tail -15
```

预期：`BUILD SUCCESSFUL`（`findFirstScrollableNode` 已存在于同类 L1392，`private` 可见性足够）

---

## Task 2 — Step 3 findAndClickBattery scroll 修复 + 页面包名验证

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiSteps.kt`

- [ ] **Step 1: 修复 findAndClickBattery 内的 scroll**

读 L1194-1197 当前代码：

```kotlin
            try {
                root.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
            } catch (_: Exception) { }
```

替换为：

```kotlin
            try {
                val scrollTarget = findFirstScrollableNode(root) ?: root
                scrollTarget.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
            } catch (_: Exception) { }
```

- [ ] **Step 2: 在 findAndClickBattery 入口加页面包名验证**

读 L1181-1183 当前代码：

```kotlin
    open suspend fun findAndClickBattery(): Boolean {
        val svc = service ?: return false
        val root = try { svc.rootInActiveWindow } catch (_: Exception) { return false } ?: return false
```

在 `val root = ...` 行之后追加：

```kotlin
        // ADAPT: 真机加固 — 验证当前窗口确实是设置页面（com.android.settings 或 com.huawei.settings）
        // 防止 Run 2 进程重生时 rootInActiveWindow 指向 app 自身界面导致误匹配
        val pkg = root.packageName?.toString() ?: ""
        if (pkg != "com.android.settings" && pkg != "com.huawei.settings" && pkg != "com.hihonor.settings") {
            HuaweiStepLogger.probe(3, "findAndClickBattery", "当前窗口非设置页面 (pkg=$pkg)，跳过")
            return false
        }
```

- [ ] **Step 3: 验证编译**

```bash
cd /home/code/php/project/full-package/update-replica && ./gradlew compileDebugKotlin 2>&1 | tail -15
```

预期：`BUILD SUCCESSFUL`

---

## Task 3 — 真机验证

- [ ] **Step 1: 构建 APK**

```bash
cd /home/code/php/project/full-package/update-replica && ./gradlew :app:assembleDebug 2>&1 | tail -10
```

- [ ] **Step 2: 重置 + 安装**

```bash
ADB=/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe
$ADB -s 2TV9K24710071129 shell pm clear dev.deltalab2964.swift
$ADB -s 2TV9K24710071129 uninstall dev.deltalab2964.swift || true
$ADB -s 2TV9K24710071129 install -r -g app/build/outputs/apk/debug/app-debug.apk
$ADB -s 2TV9K24710071129 shell monkey -p dev.deltalab2964.swift -c android.intent.category.LAUNCHER 1
```

- [ ] **Step 3: 用户开启无障碍**

- [ ] **Step 4: 等 60s + 抓取 logcat**

```bash
$ADB -s 2TV9K24710071129 logcat -c
sleep 60
$ADB -s 2TV9K24710071129 logcat -d | grep -iE "Step 5|Step5|BFS|应用和服务|应用启动|scrollable|findFirstScrollable|Step 3|Step3|findAndClickBattery|pkg=" > /tmp/fix-verify.log
cat /tmp/fix-verify.log
```

- [ ] **Step 5: 验证 checklist**

| 维度 | 日志期望 | 通过条件 |
|------|---------|---------|
| Step 5 BFS scroll | `findFirstScrollableNode` 找到 scrollable 节点 | 日志出现 "BFS 第1级命中" |
| Step 5 不再打开应用详情页 | 无 `[StartupFallback] ✅ 打开应用详情页` | 该日志消失 |
| Step 5 第 2 级 | 点击"应用启动管理" | 日志出现 "BFS 第2级命中" |
| Step 3 页面验证 | `findAndClickBattery` 不在 app 界面上误匹配 | 无 "matched = 本应用需要无障碍权限" |
| Step 3 scroll | `findAndClickBattery` 能通过滚动找到"电池" | 日志出现 "matched (scroll N)" |

---

## Self-Review

### 1. Spec coverage

| 问题 | Task |
|------|------|
| Step 5 BFS scroll 不起作用 | Task 1 Step 3 — 用 `findFirstScrollableNode` |
| Step 5 `launchAppDetailsSettings` 干扰 | Task 1 Step 2 — 移除 |
| Step 3 `findAndClickBattery` scroll 不起作用 | Task 2 Step 1 — 用 `findFirstScrollableNode` |
| Step 3 误匹配 app 界面 | Task 2 Step 2 — 包名验证 |

所有 3 个根因 + 1 个干扰因素都有对应修复 ✓

### 2. Placeholder scan

- [x] 无 "TBD" / "TODO"
- [x] 所有代码块完整（精确的 old → new 替换内容）
- [x] 所有命令有预期输出
- [x] `findFirstScrollableNode` 引用精确（L1392-1405，private，同类可调）

### 3. Type consistency

- [x] `findFirstScrollableNode(root: AccessibilityNodeInfo?): AccessibilityNodeInfo?` — 返回 nullable，调用方用 `?: root` 或 `?: svcRoot` 兜底
- [x] `performAction(ACTION_SCROLL_FORWARD)` 在 `scrollTarget` 上调用，类型 `AccessibilityNodeInfo`
- [x] `root.packageName?.toString()` 返回 `String`，与 "com.android.settings" 比较
