# 华为 FIN-AL60 真机 5 Bug 修复 — scroll 深度 + Switch 验证 + 进程重生 + 手势兜底 + 清除按钮

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 修复华为 FIN-AL60 HarmonyOS 4.2 真机验证暴露的 5 个 bug：(1) scroll DFS 3 层不够深 (2) Switch 盲点无状态验证 (3) 进程重生后 Step 3 不重新打开设置 (4) Step 5 BFS gesture swipe fallback (5) Step 9 清除按钮未命中。

**Architecture:** 全部修改集中在 `HuaweiSteps.kt` 一个文件（~4000 行），修改 5 个精确位置。不新建文件。

**Tech Stack:** Kotlin 1.9 + Android AccessibilityService

**硬约束**：不 git commit / 不跑 test/build / 只用 `./gradlew compileDebugKotlin`

---

## 问题 → Task 映射

| Bug | 日志证据 | 根因 | Task |
|-----|---------|------|------|
| Step 3/5 scroll 无效 | `BFS 第1级未找到…已滚动4次` | `findFirstScrollableNode` DFS 3 层不够 | T1 |
| Step 7 误操作开关 | `clickFirstSwitchOnDetailPage fallback = true` | 盲点不检查 `isChecked` | T2 |
| Step 3 进程重生不重试 | `findAndClickBattery = 当前窗口非设置页面…跳过` | 返回 false 后没重新 openSettings | T3 |
| Step 5 BFS scroll 无效 | 同 T1 + gesture swipe fallback | `findFirstScrollableNode` 返回 null 时无兜底 | T4 |
| Step 9 清除按钮未找到 | `查找: 清空/一键清理/…` 全部未命中 | 华为 Launcher contentDescription 不匹配 | T5 |

---

## Task 1 — findFirstScrollableNode DFS 深度扩大到 10 层

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiSteps.kt:1399-1410`

当前代码（L1399-1410）：
```kotlin
private fun findFirstScrollableNode(root: AccessibilityNodeInfo?): AccessibilityNodeInfo? {
    if (root == null) return null
    if (root.isScrollable) return root
    for (i in 0 until root.childCount) {
        val child = try { root.getChild(i) } catch (_: Exception) { null } ?: continue
        if (child.isScrollable) return child
        // One more level
        for (j in 0 until child.childCount) {
            val grandchild = try { child.getChild(j) } catch (_: Exception) { null } ?: continue
            if (grandchild.isScrollable) return grandchild
        }
    }
    return null
}
```

- [ ] **Step 1: 替换为递归 DFS（深度限制 10 层）**

```kotlin
private fun findFirstScrollableNode(root: AccessibilityNodeInfo?, depth: Int = 0): AccessibilityNodeInfo? {
    if (root == null || depth > 10) return null
    if (root.isScrollable) return root
    for (i in 0 until root.childCount) {
        val child = try { root.getChild(i) } catch (_: Exception) { null } ?: continue
        val found = findFirstScrollableNode(child, depth + 1)
        if (found != null) return found
    }
    return null
}
```

注意：`findFirstScrollableNode` 在 3 处被调用 — 所有调用者只传 `(root)` 一个参数，新增的 `depth = 0` 默认参数保持兼容。

- [ ] **Step 2: 验证编译**

```bash
cd /home/code/php/project/full-package/update-replica && ./gradlew compileDebugKotlin 2>&1 | tail -10
```

---

## Task 2 — clickFirstSwitchOnDetailPage 加 targetChecked 验证

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiSteps.kt:2631-2635`
- Modify: Step 7 调用处 L2768

当前代码（L2631-2635）：
```kotlin
open fun clickFirstSwitchOnDetailPage(): Boolean {
    val root = try { service?.rootInActiveWindow } catch (_: Exception) { null } ?: return false
    val sw = findFirstSwitchInTree(root) ?: return false
    return sw.performAction(AccessibilityNodeInfo.ACTION_CLICK)
}
```

- [ ] **Step 1: 添加 targetChecked 参数**

```kotlin
open fun clickFirstSwitchOnDetailPage(targetChecked: Boolean? = null): Boolean {
    val root = try { service?.rootInActiveWindow } catch (_: Exception) { null } ?: return false
    val sw = findFirstSwitchInTree(root) ?: return false
    // ADAPT: 真机加固 — 指定目标状态时先验证，避免把已达成的状态反转
    if (targetChecked != null && sw.isChecked == targetChecked) {
        android.util.Log.d(TAG, "clickFirstSwitchOnDetailPage: 已处于目标状态 (isChecked=$targetChecked), 跳过")
        return true
    }
    return sw.performAction(AccessibilityNodeInfo.ACTION_CLICK)
}
```

默认参数 `null` 保持所有现有调用者不受影响（不传 = 盲点行为不变）。

- [ ] **Step 2: Step 7 调用处传 targetChecked = false（目标关闭）**

找到 L2768：
```kotlin
val fallbackOk = try { clickFirstSwitchOnDetailPage() } catch (_: Exception) { false }
```

替换为：
```kotlin
val fallbackOk = try { clickFirstSwitchOnDetailPage(targetChecked = false) } catch (_: Exception) { false }
```

- [ ] **Step 3: 验证编译**

```bash
cd /home/code/php/project/full-package/update-replica && ./gradlew compileDebugKotlin 2>&1 | tail -10
```

---

## Task 3 — Step 3 进程重生后重新打开设置

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiSteps.kt:995-1003`

当前代码（L990-1003）：
```kotlin
            val batteryFound = try { findAndClickBattery() } catch (e: Exception) {
                logs.add("[Step3/10] findAndClickBattery 异常: ${e.message}")
                false
            }
            if (!batteryFound) {
                logs.add("[Step3/10] 找电池失败 (vendor L2135)")
                if (outerAttempt >= maxOuterRetry) {
                    logs.add("[Step3/10] 2次找电池均失败，标记完成并退出")
                    return
                }
                delay(100L) // vendor L2143
                outerAttempt++
                continue
            }
```

- [ ] **Step 1: 在 `!batteryFound` 分支中重新 openSettingsWithVerify**

替换 L995-1003 的 `if (!batteryFound)` 块为：

```kotlin
            if (!batteryFound) {
                // ADAPT: 真机加固 — findAndClickBattery 包名检查失败可能是进程重生导致
                // rootInActiveWindow 指向 app 自身界面。重新打开设置页再重试。
                logs.add("[Step3/10] 找电池失败，重新打开设置页 (vendor L2135)")
                if (outerAttempt >= maxOuterRetry) {
                    logs.add("[Step3/10] 2次找电池均失败，标记完成并退出")
                    return
                }
                try { openSettingsWithVerify() } catch (_: Exception) { }
                delay(1500L) // 等设置页重新渲染
                outerAttempt++
                continue
            }
```

差异：在 `delay(100L)` 前追加 `openSettingsWithVerify()` + 将 delay 改为 `1500L`（设置页需要更多渲染时间）。

- [ ] **Step 2: 验证编译**

```bash
cd /home/code/php/project/full-package/update-replica && ./gradlew compileDebugKotlin 2>&1 | tail -10
```

---

## Task 4 — Step 5 BFS scroll 加 gesture-based swipe fallback

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiSteps.kt` (Step 5 BFS scroll 块)

当前代码（上一次修复后）：
```kotlin
                // 未找到 → 向下滚动（DFS 查找 scrollable 子节点，不在 root 上直接 scroll）
                val svcRoot = try { service?.rootInActiveWindow } catch (_: Exception) { null }
                val scrollTarget = findFirstScrollableNode(svcRoot) ?: svcRoot
                scrollTarget?.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
                delay(800L)
```

- [ ] **Step 1: 加 gesture swipe fallback**

替换上面 4 行为：
```kotlin
                // 未找到 → 向下滚动
                val svcRoot = try { service?.rootInActiveWindow } catch (_: Exception) { null }
                val scrollTarget = findFirstScrollableNode(svcRoot)
                if (scrollTarget != null) {
                    scrollTarget.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
                } else {
                    // ADAPT: 真机加固 — findFirstScrollableNode 未找到可滚动节点
                    // 改用 gesture swipe (50%w, 70%h) → (50%w, 30%h) 模拟上滑
                    val svc = service
                    if (svc != null) {
                        val w = getScreenWidthPx()
                        val h = getScreenHeightPx()
                        HuaweiGestureHelper.gestureSwipe(
                            svc,
                            startX = w * 0.5f, startY = h * 0.7f,
                            endX = w * 0.5f, endY = h * 0.3f,
                            durationMs = 300L
                        )
                    }
                }
                delay(800L)
```

- [ ] **Step 2: 确认 HuaweiGestureHelper.gestureSwipe 签名**

```bash
cd /home/code/php/project/full-package/update-replica && grep -n "fun gestureSwipe\|fun swipe" app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiGestureHelper.kt
```

如果签名不是 `gestureSwipe(service, startX, startY, endX, endY, durationMs)` 而是其他形式（如 `performSwipe`），调整调用代码以匹配实际签名。

- [ ] **Step 3: 同样修复 Step 3 findAndClickBattery 的 scroll**

找到 `findAndClickBattery` 内的 scroll 块（当前约 L1203-1207）：
```kotlin
            try {
                val scrollTarget = findFirstScrollableNode(root) ?: root
                scrollTarget.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
            } catch (_: Exception) { }
```

替换为：
```kotlin
            try {
                val scrollTarget = findFirstScrollableNode(root)
                if (scrollTarget != null) {
                    scrollTarget.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
                } else {
                    val svc = service
                    if (svc != null) {
                        val w = getScreenWidthPx()
                        val h = getScreenHeightPx()
                        HuaweiGestureHelper.gestureSwipe(
                            svc,
                            startX = w * 0.5f, startY = h * 0.7f,
                            endX = w * 0.5f, endY = h * 0.3f,
                            durationMs = 300L
                        )
                    }
                }
            } catch (_: Exception) { }
```

- [ ] **Step 4: 验证编译**

```bash
cd /home/code/php/project/full-package/update-replica && ./gradlew compileDebugKotlin 2>&1 | tail -10
```

---

## Task 5 — Step 9 清除按钮：用 adb dump 确认 + 补充候选词

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiSteps.kt` (清除按钮 contentDescription/text 候选)

- [ ] **Step 1: adb dump 华为最近任务页确认清除按钮文案**

```bash
ADB=/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe
# 先打开最近任务
$ADB -s 2TV9K24710071129 shell input keyevent KEYCODE_APP_SWITCH
sleep 2
# dump UI
$ADB -s 2TV9K24710071129 shell uiautomator dump /data/local/tmp/recents.xml
$ADB -s 2TV9K24710071129 pull /data/local/tmp/recents.xml /tmp/recents.xml
# 搜索清除/关闭相关
grep -oiE 'content-desc="[^"]*"' /tmp/recents.xml | sort -u
grep -oiE 'text="[^"]*"' /tmp/recents.xml | grep -iE '清|关|clear|close|delete|remove' | sort -u
```

- [ ] **Step 2: 根据 dump 结果更新 CLEAR_ALL_CONTENT_DESCRIPTIONS 和 CLEAR_ALL_BUTTON_TEXTS**

找到 `HuaweiSteps.kt` companion object 内的清除按钮候选列表（grep `CLEAR_ALL_CONTENT_DESCRIPTIONS\|CLEAR_ALL_BUTTON_TEXTS\|清空.*一键清理`）。

将 dump 中发现的华为 Launcher 实际 contentDescription/text 添加到对应列表中。

- [ ] **Step 3: 验证编译**

```bash
cd /home/code/php/project/full-package/update-replica && ./gradlew compileDebugKotlin 2>&1 | tail -10
```

---

## Task 6 — 真机验证

- [ ] **Step 1: 构建 + 部署**

```bash
cd /home/code/php/project/full-package/update-replica
./gradlew :app:assembleDebug 2>&1 | tail -5
ADB=/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe
$ADB -s 2TV9K24710071129 shell pm clear dev.deltalab2964.swift
$ADB -s 2TV9K24710071129 uninstall dev.deltalab2964.swift || true
$ADB -s 2TV9K24710071129 install -r -g app/build/outputs/apk/debug/app-debug.apk
$ADB -s 2TV9K24710071129 shell monkey -p dev.deltalab2964.swift -c android.intent.category.LAUNCHER 1
```

- [ ] **Step 2: 用户开启无障碍**

- [ ] **Step 3: 抓取日志验证 5 项**

```bash
$ADB -s 2TV9K24710071129 logcat -c
sleep 60
$ADB -s 2TV9K24710071129 logcat -d | grep "HuaweiSteps" > /tmp/5bug-verify.log
```

| Bug | 日志期望 | 通过条件 |
|-----|---------|---------|
| T1 scroll 深度 | Step 5 `BFS 第1级命中` 或 Step 3 `findAndClickBattery matched` | 至少一个 "命中" |
| T2 Switch 验证 | `clickFirstSwitchOnDetailPage: 已处于目标状态` 或正确 toggle | 无"误关闭"反馈 |
| T3 重新打开设置 | `找电池失败，重新打开设置页` → 第 2 次尝试有 `matched` | Step 3 有成功路径 |
| T4 gesture swipe | gesture swipe 日志（若 scrollTarget=null） | scroll 后有 "命中" |
| T5 清除按钮 | Step 9 不再"全部未命中" | contentDescription 命中 |

---

## Self-Review

### 1. Spec coverage

| 问题 | Task | 检查 |
|------|------|------|
| findFirstScrollableNode 3 层不够 | T1 ✓ | 扩大到 10 层递归 DFS |
| Switch 盲点无验证 | T2 ✓ | 添加 targetChecked 参数 |
| Step 3 进程重生不重试 | T3 ✓ | batteryFound=false 时重新 openSettings |
| Step 5 BFS gesture fallback | T4 ✓ | findFirstScrollableNode=null 时 gestureSwipe |
| Step 9 清除按钮 | T5 ✓ | adb dump + 更新候选列表 |

所有 5 个问题都有对应 Task ✓

### 2. Placeholder scan

- [x] 无 "TBD" / "TODO"
- [x] T5 Step 1 的 adb 命令是完整可执行的
- [x] T5 Step 2 依赖 dump 结果，无法预写代码 — 但给出了精确的 grep 模板和目标列表名，不是 placeholder
- [x] 所有代码块完整

### 3. Type consistency

- [x] `findFirstScrollableNode(root, depth=0)` — 递归签名一致
- [x] `clickFirstSwitchOnDetailPage(targetChecked: Boolean? = null)` — 默认 null 保持兼容
- [x] `HuaweiGestureHelper.gestureSwipe(svc, startX, startY, endX, endY, durationMs)` — 需 Step 2 确认实际签名
- [x] `getScreenWidthPx()` / `getScreenHeightPx()` — 已在 HuaweiSteps L3178/3204 存在
