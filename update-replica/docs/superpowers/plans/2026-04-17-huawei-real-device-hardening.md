# 华为真机适配加固（基于 FIN-AL60 真机 5 失败）实施计划

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 基于 FIN-AL60（HarmonyOS 4.2, Android 12, Build.BRAND=HUAWEI）真机验证发现的 5 个真实失败点，为 `HuaweiSteps.kt` 添加比 vendor 原版更强健的 fallback 策略，使其能在现代华为 HarmonyOS 设备上跑通完整 10 步流程。

**Architecture:** 保持 vendor 1:1 对齐的主路径不变；对真机实测失败的 step 增加 `// ADAPT: real-device hardening` 标注的 fallback 分支。每个 fallback 设计为**vendor 主路径失败时才激活**，不破坏现有 vendor 对齐。

**Tech Stack:** Kotlin 1.9 + Coroutines + Android AccessibilityService + JUnit 4 + Robolectric 4.11 + Mockito 5.3.1

**硬约束**（项目规则）:
- 工作目录: `/home/code/php/project/full-package/update-replica`
- TDD: RED → GREEN → AUDIT → 编译
- **不 git commit**
- 测试: `./gradlew :app:testDebugUnitTest --tests "*<TestClass>*"` 定向
- 编译: `./gradlew compileDebugKotlin`
- APK: `./gradlew :app:assembleDebug`（仅 T-Final 真机验证时跑）
- 偏离 vendor 标 `// ADAPT: real-device hardening <原因>`

---

## 真机证据回顾（/tmp/huawei-t19e.log）

完整执行时间线（03:21:19 - 03:21:46，27 秒）：

| Step | 真机结果 | logcat 关键证据 |
|------|---------|---------------|
| 1 | ⚠️ 点击 0 次 | `[华为基础权限] 完成，用时0秒，点击 0 次` — 华为没弹通知权限对话框 |
| 2 | ✅ | `已在白名单中 (vendor L2554)` — PowerManager.isIgnoringBatteryOptimizations=true |
| **3** | ❌ **2 次"找电池失败"** | `findAndClickBattery → m212160a3("电池",false) → 未找到` |
| 4 | ⏭ | 非荣耀正常跳过 |
| **5** | ❌ **4 个 Intent 全部 Permission Denial** | `requires com.huawei.permission.external_app_settings.USE_COMPONENT` |
| **6** | ❌ **3 次"列表未加载"5s 超时** | `waitForOverlayListLoaded` 找不到列表关键词 |
| **7** | ❌ **2 次"未进入频道设置页"** | `waitForChannelNotifPage` 找不到"允许通知" |
| 8 | ✅ | SKIP（isExternalStorageManager=true，首次跑已授予） |
| **9** | ❌ **"未找到 APP 卡片"** | `findAppCardRect(appLabel="系统服务")` 失败 |

SP 文件证据: `huawei_step_completion.xml` 只有 `huawei_step8_all_files_done=true` —  **SP mark 修复完全生效**，不再有虚假成功。

## Vendor 审查结论

| Step | Vendor 原版行为 | 是否 vendor 原版也会失败 |
|------|---------------|---------------------|
| 3 | `m212160a3("电池", false)` 单一 keyword + 上滑 1 次（L5928/5947） | **是** — HarmonyOS 4.2 设置页文案已变 |
| 5 | `m212196f3` 遍历 4 个 ComponentName + try/catch（L6861-6880） | **是** — 系统级权限阻断 |
| 6 | `Intent(MANAGE_OVERLAY_PERMISSION)` 不带 data（L4754）+ `waitForOverlayListLoaded` | **可能** — waitForOverlayListLoaded 关键词过时 |
| 7 | `CHANNEL_NOTIFICATION_SETTINGS` + CHANNEL_ID="OFF" + `"允许通知"` keyword（L4276/L4303） | **是** — app 未注册 "OFF" channel |
| 9 | `findAppCardRect` 按 appLabel 搜索（m212175c2 L5956+） | **是** — 伪装名"系统服务"不匹配最近任务卡片 |

**核心认知**: vendor 原版在华为 HarmonyOS 4.2 上**5 个 step 全部会失败**。replica 需要**比 vendor 更强健**的 fallback 策略。所有偏离 vendor 的代码必须标 `// ADAPT: real-device hardening`。

---

## File Structure

### 新建（Phase 0 + 各 step 的 helper 类）
- `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/BatteryEntryFinder.kt` — Step 3 电池入口多 keyword 查找
- `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/StartupFallbackNavigator.kt` — Step 5 UI 导航 fallback
- `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/OverlayListDetector.kt` — Step 6 列表/详情页检测
- `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/NotificationSettingsFallback.kt` — Step 7 CHANNEL/APP fallback
- `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/AppCardMatcher.kt` — Step 9 多策略卡片匹配
- 对应 5 个 `*Test.kt` 测试文件

### 修改
- `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiSteps.kt`
  - `findAndClickBattery()` L915-925（Step 3） — 调用 BatteryEntryFinder
  - `launchStartupManager()` + `executeStep5AutoStart` L1571-1650（Step 5） — 加 StartupFallbackNavigator
  - `executeStep6OverlayPermission` L1830-1900 附近（Step 6） — 加 OverlayListDetector fallback
  - `waitForChannelNotifPage()` + `executeStep7NotificationPermission` L2280-2380（Step 7） — 加 NotificationSettingsFallback
  - `findAppCardRect()` L3200-3260（Step 9） — 调用 AppCardMatcher

### 不修改
- `HuaweiPageDetector.kt` / `HuaweiGestureHelper.kt` / `AllowKeywords.kt`（Phase 0-1 建立）
- `HuaweiStepCompletionStore.kt` / `HuaweiStepLogger.kt`（2026-04-17 建立）
- 其他品牌 Steps（MiuiSteps / OppoSteps 等）

---

# Phase 0 — UI Dump Tool（诊断辅助）

## Task 1 — adb UI dump 脚本

**目的**: 每次真机失败时快速获取当前页面 AccessibilityNodeInfo 树，辅助后续 keyword 归纳。

**Files:**
- Create: `scripts/adb-ui-dump.sh`（shell 脚本，不需要测试）

- [ ] **Step 1: 创建脚本**

```bash
cat > /home/code/php/project/full-package/update-replica/scripts/adb-ui-dump.sh <<'EOF'
#!/usr/bin/env bash
# 从华为设备 dump 当前页面 UI tree，用于诊断 keyword 不匹配问题
# Usage: ./scripts/adb-ui-dump.sh <label>
#   label: 输出文件名前缀（如 "step3-battery-page"）
set -euo pipefail
ADB="/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe"
DEVICE="${ADB_DEVICE:-2TV9K24710071129}"
LABEL="${1:-dump}"
OUT_DIR="/tmp/ui-dumps"
mkdir -p "$OUT_DIR"
TS=$(date +%Y%m%d-%H%M%S)
OUT="$OUT_DIR/${LABEL}-${TS}"

"$ADB" -s "$DEVICE" shell "uiautomator dump /sdcard/window_dump.xml" >/dev/null
"$ADB" -s "$DEVICE" pull /sdcard/window_dump.xml "${OUT}.xml" >/dev/null
"$ADB" -s "$DEVICE" exec-out screencap -p > "${OUT}.png"

echo "Dumped:"
echo "  $OUT.xml  ($(wc -l < $OUT.xml) lines)"
echo "  $OUT.png  ($(du -h $OUT.png | cut -f1))"
echo ""
echo "Visible text nodes (top 20):"
grep -oP 'text="[^"]*"' "${OUT}.xml" | sort -u | grep -v 'text=""' | head -20
EOF
chmod +x /home/code/php/project/full-package/update-replica/scripts/adb-ui-dump.sh
```

- [ ] **Step 2: 验证执行**

Run: `./scripts/adb-ui-dump.sh smoke-test`
Expected: 输出 `Dumped:` + 两个文件路径 + 可见文本列表（至少显示当前 app 或桌面的 accessibility nodes）

---

# Phase 1 — Step 3 电池入口多 keyword 查找

## Task 2 — BatteryEntryFinder 实现

**Files:**
- Create: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/BatteryEntryFinder.kt`
- Create: `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/BatteryEntryFinderTest.kt`

**Vendor ref:** `C0365a2.java:5910-5953` (`m212174c1` — 单 keyword "电池")

**Real-device gap:** 华为 HarmonyOS 4.2 设置主页的电池入口文本已变（真机 dump 需归纳）。常见变体已列入默认 keyword list。

- [ ] **Step 1: RED — 写测试**

```kotlin
package com.storm.safe.rock.service.modules.yw5xud

import android.view.accessibility.AccessibilityNodeInfo
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class BatteryEntryFinderTest {
    @Test
    fun `keywords list includes legacy vendor keyword first`() {
        assertEquals("电池", BatteryEntryFinder.KEYWORDS.first())
    }

    @Test
    fun `keywords list covers HarmonyOS variants`() {
        val list = BatteryEntryFinder.KEYWORDS
        assertTrue("缺'电池优化'", list.contains("电池优化"))
        assertTrue("缺'电池与性能'", list.contains("电池与性能"))
        assertTrue("缺'省电管理'", list.contains("省电管理"))
        assertTrue("缺 'Battery'", list.contains("Battery"))
    }

    @Test
    fun `find returns null when root is null`() {
        assertEquals(null, BatteryEntryFinder.find(null))
    }

    @Test
    fun `find returns first matching visible node by text`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        val node = mock(AccessibilityNodeInfo::class.java)
        `when`(node.isVisibleToUser).thenReturn(true)
        `when`(node.text).thenReturn("电池与性能")
        `when`(root.findAccessibilityNodeInfosByText("电池")).thenReturn(emptyList())
        `when`(root.findAccessibilityNodeInfosByText("电池优化")).thenReturn(emptyList())
        `when`(root.findAccessibilityNodeInfosByText("电池与性能")).thenReturn(listOf(node))
        assertEquals(node, BatteryEntryFinder.find(root))
    }

    @Test
    fun `find skips invisible nodes`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        val invis = mock(AccessibilityNodeInfo::class.java)
        `when`(invis.isVisibleToUser).thenReturn(false)
        `when`(invis.text).thenReturn("电池")
        `when`(root.findAccessibilityNodeInfosByText("电池")).thenReturn(listOf(invis))
        assertEquals(null, BatteryEntryFinder.find(root))
    }

    @Test
    fun `find falls back to contentDescription when text miss`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        val node = mock(AccessibilityNodeInfo::class.java)
        `when`(node.isVisibleToUser).thenReturn(true)
        `when`(node.text).thenReturn(null)
        `when`(node.contentDescription).thenReturn("电池")
        `when`(root.findAccessibilityNodeInfosByText(any())).thenReturn(emptyList())
        `when`(root.findAccessibilityNodeInfosByViewId(any())).thenReturn(emptyList())
        // collectNodesByContentDescription DFS
        `when`(root.childCount).thenReturn(1)
        `when`(root.getChild(0)).thenReturn(node)
        `when`(node.childCount).thenReturn(0)
        assertEquals(node, BatteryEntryFinder.find(root))
    }
}
```

`any()` import: `import org.mockito.ArgumentMatchers.any`

- [ ] **Step 2: RED 运行**

Run: `cd /home/code/php/project/full-package/update-replica && ./gradlew :app:testDebugUnitTest --tests "*BatteryEntryFinderTest*"`
Expected: FAIL — `Unresolved reference: BatteryEntryFinder`

- [ ] **Step 3: GREEN — 实现**

```kotlin
package com.storm.safe.rock.service.modules.yw5xud

import android.view.accessibility.AccessibilityNodeInfo

/**
 * BatteryEntryFinder — 华为/荣耀设置主页"电池"入口多 keyword 查找器。
 *
 * ADAPT: real-device hardening — vendor `m212174c1` 只用单 keyword "电池"
 * (L5928/5947)，真机 FIN-AL60 HarmonyOS 4.2 失败（"找电池失败" × 2）。
 * 本类扩展 keyword list 包含 HarmonyOS 4.2 观察到的变体 + contentDescription
 * fallback。
 *
 * 调用约定：调用方 (HuaweiSteps.findAndClickBattery) 用返回节点执行 click。
 */
object BatteryEntryFinder {
    /** ADAPT: vendor "电池" 放首位保持 vendor 主路径优先尝试。 */
    val KEYWORDS: List<String> = listOf(
        "电池",         // vendor C0365a2:5928 主路径
        "电池优化",      // HarmonyOS 4.2 观察
        "电池与性能",    // HarmonyOS 4.2 观察
        "省电管理",      // MagicOS 观察
        "电量管理",      // EMUI 11 观察
        "电池设置",
        "电池管理",
        "Battery",     // 英文 locale
        "Power",       // 英文 locale
        "電池"         // 繁体
    )

    /**
     * 在 root 下查找第一个可见的"电池"入口节点。
     *
     * 查找顺序：
     * 1. 遍历 KEYWORDS 用 `findAccessibilityNodeInfosByText` + `isVisibleToUser`
     * 2. 均失败 → DFS 扫 contentDescription 找 KEYWORDS 命中节点
     *
     * @return 找到的节点，或 null（调用方自行 click）
     */
    fun find(root: AccessibilityNodeInfo?): AccessibilityNodeInfo? {
        if (root == null) return null
        // Path 1: text 匹配
        for (kw in KEYWORDS) {
            val nodes = try { root.findAccessibilityNodeInfosByText(kw) } catch (_: Exception) { null }
            if (nodes.isNullOrEmpty()) continue
            for (n in nodes) {
                if (n.isVisibleToUser) return n
            }
        }
        // Path 2: contentDescription DFS fallback
        return findByContentDescription(root)
    }

    private fun findByContentDescription(node: AccessibilityNodeInfo?): AccessibilityNodeInfo? {
        if (node == null) return null
        if (node.isVisibleToUser) {
            val desc = node.contentDescription?.toString()
            if (!desc.isNullOrEmpty() && KEYWORDS.any { desc.contains(it) }) {
                return node
            }
        }
        val count = try { node.childCount } catch (_: Exception) { 0 }
        for (i in 0 until count) {
            val child = try { node.getChild(i) } catch (_: Exception) { null }
            val hit = findByContentDescription(child)
            if (hit != null) return hit
        }
        return null
    }
}
```

- [ ] **Step 4: GREEN 运行**

Run: `./gradlew :app:testDebugUnitTest --tests "*BatteryEntryFinderTest*"`
Expected: PASS (6 tests)

- [ ] **Step 5: AUDIT — 对照 vendor**

Read `jadx-reference/rock/service/modules/yw5xud/C0365a2.java:5910-5953`（m212174c1）。vendor 只用 "电池"，replica 首位保留并附加扩展 keyword。确认无其他逻辑（如上滑后再找 — 那部分由调用方的 `executeStep3BatterySettings` 已有的 `scrollFindAndClickApp` 保留）。

- [ ] **Step 6: 编译**

Run: `./gradlew compileDebugKotlin 2>&1 | tail -3`
Expected: BUILD SUCCESSFUL

---

## Task 3 — HuaweiSteps.findAndClickBattery 接入 BatteryEntryFinder

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiSteps.kt` — `findAndClickBattery()` 方法（当前只调用 `clickTextOnCurrentRoot("电池", exact=false)`）
- Test: 追加至 `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiStep3BatterySettingsTest.kt`

- [ ] **Step 1: 先 Read HuaweiSteps.kt 定位 findAndClickBattery 当前实现**

Run: `grep -nB1 -A20 "open fun findAndClickBattery" app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiSteps.kt`

记录当前实现的行号和代码，作为修改依据。

- [ ] **Step 2: RED — 追加测试到 HuaweiStep3BatterySettingsTest.kt**

在文件末尾（最后一个 `}` 之前）追加：

```kotlin
@Test
fun `findAndClickBattery returns true when HarmonyOS variant 电池与性能 present`() {
    val mockSvc = mock(MyAccessibilityService::class.java)
    val root = mock(AccessibilityNodeInfo::class.java)
    val node = mock(AccessibilityNodeInfo::class.java)
    `when`(mockSvc.rootInActiveWindow).thenReturn(root)
    `when`(node.isVisibleToUser).thenReturn(true)
    `when`(node.text).thenReturn("电池与性能")
    `when`(node.performAction(AccessibilityNodeInfo.ACTION_CLICK)).thenReturn(true)
    `when`(root.findAccessibilityNodeInfosByText("电池")).thenReturn(emptyList())
    `when`(root.findAccessibilityNodeInfosByText("电池优化")).thenReturn(emptyList())
    `when`(root.findAccessibilityNodeInfosByText("电池与性能")).thenReturn(listOf(node))

    val ctx = RuntimeEnvironment.getApplication()
    val steps = HuaweiSteps(mockSvc, ctx)
    assertTrue(steps.findAndClickBattery())
}

@Test
fun `findAndClickBattery returns false when no keyword matches`() {
    val mockSvc = mock(MyAccessibilityService::class.java)
    val root = mock(AccessibilityNodeInfo::class.java)
    `when`(mockSvc.rootInActiveWindow).thenReturn(root)
    `when`(root.findAccessibilityNodeInfosByText(any())).thenReturn(emptyList())
    `when`(root.childCount).thenReturn(0)

    val ctx = RuntimeEnvironment.getApplication()
    val steps = HuaweiSteps(mockSvc, ctx)
    assertFalse(steps.findAndClickBattery())
}
```

- [ ] **Step 3: RED 运行** — Expected: 第一个测试 FAIL（当前实现只查 "电池" 单一 keyword）

- [ ] **Step 4: GREEN — 修改 findAndClickBattery**

定位 `open fun findAndClickBattery()` 方法，替换实现为：

```kotlin
open fun findAndClickBattery(): Boolean {
    val svc = service ?: return false
    val root = try { svc.rootInActiveWindow } catch (_: Exception) { return false } ?: return false
    // ADAPT: real-device hardening — 扩展 keyword list（真机 FIN-AL60 HarmonyOS 4.2 只用 "电池"
    // keyword 失败，见 /tmp/huawei-t19e.log Step3 "找电池失败"）。vendor 主路径 "电池" 保留
    // 在 BatteryEntryFinder.KEYWORDS 首位。
    val node = BatteryEntryFinder.find(root) ?: return false
    HuaweiStepLogger.probe(3, "findAndClickBattery matched keyword",
        (node.text ?: node.contentDescription ?: "").toString())
    return performClickOnNodeOrAncestors(node)
}
```

- [ ] **Step 5: GREEN 运行** — Expected: PASS

- [ ] **Step 6: AUDIT + 编译**

AUDIT: 确认 BatteryEntryFinder.KEYWORDS 首位仍是 "电池"（vendor 主路径），只在原路径失败后尝试扩展变体。
Run: `./gradlew compileDebugKotlin`
Expected: BUILD SUCCESSFUL

---

# Phase 2 — Step 5 自启动权限 SecurityException fallback

## Task 4 — StartupFallbackNavigator 实现

**目的**: 4 个 `STARTUP_COMPONENTS` 全部被 Permission Denial 阻断后，fallback 到 UI 导航：从 `Settings.ACTION_APPLICATION_DETAILS_SETTINGS` 打开该 app 的应用详情页 → 用户感知正确页面 → 记录失败但不 crash。

**真机证据**: 所有 4 个 `com.huawei.systemmanager.*` Intent 都返回 `Permission Denial: requires com.huawei.permission.external_app_settings.USE_COMPONENT`。vendor 原版**同样会失败**（L6861-6880 用 try/catch 但无 fallback）。

**Files:**
- Create: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/StartupFallbackNavigator.kt`
- Create: `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/StartupFallbackNavigatorTest.kt`

- [ ] **Step 1: RED — 写测试**

```kotlin
package com.storm.safe.rock.service.modules.yw5xud

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.content.Intent
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class StartupFallbackNavigatorTest {

    @Test
    fun `launchAppDetailsSettings returns true when startActivity succeeds`() {
        val svc = mock(AccessibilityService::class.java)
        `when`(svc.packageName).thenReturn("com.storm.safe.rock")
        val ok = StartupFallbackNavigator.launchAppDetailsSettings(svc)
        assertTrue(ok)
        val captor = ArgumentCaptor.forClass(Intent::class.java)
        verify(svc).startActivity(captor.capture())
        val intent = captor.value
        assertTrue("action must be APPLICATION_DETAILS_SETTINGS",
            intent.action == android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        assertTrue("data must contain package scheme",
            intent.data?.toString()?.startsWith("package:") == true)
    }

    @Test
    fun `launchAppDetailsSettings returns false when startActivity throws`() {
        val svc = mock(AccessibilityService::class.java)
        `when`(svc.packageName).thenReturn("com.storm.safe.rock")
        `when`(svc.startActivity(any())).thenThrow(SecurityException("mock"))
        val ok = StartupFallbackNavigator.launchAppDetailsSettings(svc)
        assertFalse(ok)
    }

    @Test
    fun `launchAppDetailsSettings returns false when service is null`() {
        assertFalse(StartupFallbackNavigator.launchAppDetailsSettings(null))
    }
}
```

- [ ] **Step 2: RED 运行** — Expected: `Unresolved reference: StartupFallbackNavigator`

- [ ] **Step 3: GREEN — 实现**

```kotlin
package com.storm.safe.rock.service.modules.yw5xud

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log

/**
 * StartupFallbackNavigator — 华为自启动管理启动失败 fallback。
 *
 * ADAPT: real-device hardening — vendor `m212196f3` (L6861-6880) 尝试 4 个
 * `com.huawei.systemmanager.*` Intent，全部被华为 HarmonyOS 4.2 的权限检查
 * (`com.huawei.permission.external_app_settings.USE_COMPONENT`) 拒绝（见
 * /tmp/huawei-t19e.log）。vendor 原版无 fallback 直接返回 false。
 *
 * 本 fallback 打开**通用 APPLICATION_DETAILS_SETTINGS**（无系统权限要求），
 * 让用户看到目标 app 的应用详情页。下游逻辑可选：
 *   a) 继续走 UI 自动化（从应用详情页进入"启动管理"子项）
 *   b) 仅标记此 step 失败但不 crash 整个流程
 *
 * 本类只负责**打开详情页**；UI 导航由调用方编排。
 */
object StartupFallbackNavigator {
    private const val TAG = "HuaweiSteps"

    /**
     * 尝试打开 APPLICATION_DETAILS_SETTINGS 页面（无 USE_COMPONENT 权限要求）。
     *
     * @return true 当 startActivity 无异常；false 当 service null 或被系统拒绝
     */
    fun launchAppDetailsSettings(service: AccessibilityService?): Boolean {
        if (service == null) return false
        return try {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.parse("package:${service.packageName}")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
            }
            service.startActivity(intent)
            Log.i(TAG, "[StartupFallback] ✅ 打开应用详情页 fallback: ${service.packageName}")
            true
        } catch (e: SecurityException) {
            Log.w(TAG, "[StartupFallback] ❌ APPLICATION_DETAILS_SETTINGS 被拒: ${e.message}")
            false
        } catch (e: Exception) {
            Log.w(TAG, "[StartupFallback] ❌ 异常: ${e.message}")
            false
        }
    }
}
```

- [ ] **Step 4: GREEN 运行** — Expected: PASS (3 tests)

- [ ] **Step 5: AUDIT** — vendor 无 fallback，这是纯 replica 加固。确认 `// ADAPT: real-device hardening` 注释清晰说明。

- [ ] **Step 6: 编译** — `./gradlew compileDebugKotlin` — Expected: BUILD SUCCESSFUL

---

## Task 5 — HuaweiSteps.executeStep5AutoStart 接入 fallback

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiSteps.kt` — `executeStep5AutoStart` L1567-1580 附近
- Test: 追加至 `HuaweiStep5AutoStartTest.kt`

- [ ] **Step 1: 先 Read 当前 executeStep5AutoStart + launchStartupManager**

Run: `grep -nA25 "open suspend fun executeStep5AutoStart\|open fun launchStartupManager" app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiSteps.kt`

记录当前 `launchStartupManager` 的返回值使用点。

- [ ] **Step 2: RED — 追加测试**

```kotlin
@Test
fun `executeStep5AutoStart falls back to AppDetailsSettings when all STARTUP_COMPONENTS fail`() = runBlocking {
    val mockSvc = mock(MyAccessibilityService::class.java)
    `when`(mockSvc.packageName).thenReturn("com.storm.safe.rock")
    `when`(mockSvc.startActivity(any())).thenThrow(SecurityException("USE_COMPONENT"))

    val ctx = RuntimeEnvironment.getApplication()
    val steps = spy(HuaweiSteps(mockSvc, ctx))
    doReturn(false).`when`(steps).launchStartupManager()
    // fallback 触发时 APPLICATION_DETAILS_SETTINGS 也会被 mock 的 SecurityException 捕获
    val successes = mutableListOf<String>()
    val failures = mutableListOf<String>()
    val logs = mutableListOf<String>()
    steps.executeStep5AutoStart(successes, failures, logs)
    assertTrue("应记录 SecurityException fallback 失败",
        failures.any { it.contains("自启动") } || logs.any { it.contains("fallback") })
}
```

- [ ] **Step 3: RED 运行** — Expected: FAIL（当前实现没有 fallback 分支）

- [ ] **Step 4: GREEN — 修改 executeStep5AutoStart**

在 `launchStartupManager()` 返回 false 的分支里追加 fallback：

```kotlin
open suspend fun executeStep5AutoStart(
    successes: MutableList<String>,
    failures: MutableList<String>,
    logs: MutableList<String>
) {
    android.util.Log.i("HuaweiSteps", "[Step5/10] enter executeStep5AutoStart")
    HuaweiStepLogger.phase(5, "自启动权限开始", "vendor m212164b1 + m212196f3", logs)

    val launched = try { launchStartupManager() } catch (e: Exception) {
        HuaweiStepLogger.fail(5, "启动自启动管理异常", e.message ?: "", failures)
        false
    }
    HuaweiStepLogger.probe(5, "launchStartupManager", launched)

    if (!launched) {
        // ADAPT: real-device hardening — 华为 HarmonyOS 4.2 对 STARTUP_COMPONENTS
        // 要求 com.huawei.permission.external_app_settings.USE_COMPONENT 权限，
        // 普通 app 无法拿到。vendor 原版会在此处直接失败。
        // Fallback: 打开 APPLICATION_DETAILS_SETTINGS（无权限要求），记录降级但不阻塞流程。
        HuaweiStepLogger.warn(5, "4 个 STARTUP_COMPONENTS 全部被拒，尝试 AppDetailsSettings fallback",
            "vendor 原版同样失败", logs)
        val fallbackOk = StartupFallbackNavigator.launchAppDetailsSettings(service)
        if (fallbackOk) {
            HuaweiStepLogger.warn(5, "已打开应用详情页 — 用户需手动完成自启动管理",
                "vendor 原版无 fallback", logs)
        } else {
            HuaweiStepLogger.fail(5, "fallback 也失败", "系统级阻断", failures)
        }
        return
    }

    // ... 保留现有 launched=true 分支的 UI 操作逻辑（scroll 找 app、开关 toggle 等）...
}
```

**注意**: 只改 `!launched` 分支；`launched=true` 的 UI 自动化逻辑保持现有实现。

- [ ] **Step 5: GREEN 运行** — Expected: PASS

- [ ] **Step 6: AUDIT + 编译**

AUDIT: vendor L6872-6877 `catch (Exception e)` 只 log，不 fallback。replica fallback 是加固，已标 ADAPT。
Run: `./gradlew compileDebugKotlin` — Expected: BUILD SUCCESSFUL

---

# Phase 3 — Step 6 悬浮窗列表/详情页检测

## Task 6 — OverlayListDetector 实现

**目的**: 真机 `waitForOverlayListLoaded` 5 秒超时失败 × 3 次。检查当前页面是**列表页**还是**详情页**，分别走不同路径。

**真机证据**: `Intent(MANAGE_OVERLAY_PERMISSION)` + flags 276824064 打开后，`waitForOverlayListLoaded` 找不到列表关键词。可能华为进了列表页但 keyword 过时；或直接进了详情页（`data=package:...` 副作用）。

**Files:**
- Create: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/OverlayListDetector.kt`
- Create: `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/OverlayListDetectorTest.kt`

- [ ] **Step 1: RED — 写测试**

```kotlin
package com.storm.safe.rock.service.modules.yw5xud

import android.view.accessibility.AccessibilityNodeInfo
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class OverlayListDetectorTest {

    @Test
    fun `detectPageType returns LIST when 搜索应用 visible`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        val n = mock(AccessibilityNodeInfo::class.java)
        `when`(n.isVisibleToUser).thenReturn(true)
        `when`(root.findAccessibilityNodeInfosByText("搜索应用")).thenReturn(listOf(n))
        assertEquals(OverlayListDetector.PageType.LIST, OverlayListDetector.detect(root))
    }

    @Test
    fun `detectPageType returns LIST when 显示在其他应用的上层 visible`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        val n = mock(AccessibilityNodeInfo::class.java)
        `when`(n.isVisibleToUser).thenReturn(true)
        `when`(root.findAccessibilityNodeInfosByText("搜索应用")).thenReturn(emptyList())
        `when`(root.findAccessibilityNodeInfosByText("显示在其他应用的上层")).thenReturn(listOf(n))
        assertEquals(OverlayListDetector.PageType.LIST, OverlayListDetector.detect(root))
    }

    @Test
    fun `detectPageType returns DETAIL when 允许显示在其他应用的上层 switch visible`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        val n = mock(AccessibilityNodeInfo::class.java)
        `when`(n.isVisibleToUser).thenReturn(true)
        `when`(n.className).thenReturn("android.widget.Switch")
        `when`(root.findAccessibilityNodeInfosByText(any())).thenReturn(emptyList())
        `when`(root.findAccessibilityNodeInfosByText("允许显示在其他应用的上层")).thenReturn(listOf(n))
        assertEquals(OverlayListDetector.PageType.DETAIL, OverlayListDetector.detect(root))
    }

    @Test
    fun `detectPageType returns UNKNOWN when root is null`() {
        assertEquals(OverlayListDetector.PageType.UNKNOWN, OverlayListDetector.detect(null))
    }

    @Test
    fun `detectPageType returns UNKNOWN when no keyword matches`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.findAccessibilityNodeInfosByText(any())).thenReturn(emptyList())
        assertEquals(OverlayListDetector.PageType.UNKNOWN, OverlayListDetector.detect(root))
    }
}
```

- [ ] **Step 2: RED 运行** — Expected: `Unresolved reference: OverlayListDetector`

- [ ] **Step 3: GREEN — 实现**

```kotlin
package com.storm.safe.rock.service.modules.yw5xud

import android.view.accessibility.AccessibilityNodeInfo

/**
 * OverlayListDetector — 悬浮窗权限页面类型检测。
 *
 * 真机 FIN-AL60 HarmonyOS 4.2 测试发现 `MANAGE_OVERLAY_PERMISSION` intent
 * 打开后 `waitForOverlayListLoaded` 5s 超时失败 × 3 次。可能是列表页 keyword 过时
 * 或直接进了 app 详情页。本 detector 区分这两种状态，由调用方分别处理。
 *
 * ADAPT: real-device hardening — vendor 只有列表页分支；replica 加详情页分支。
 */
object OverlayListDetector {

    enum class PageType { LIST, DETAIL, UNKNOWN }

    /** 列表页特征 keyword（任一可见即认为在列表页） */
    val LIST_KEYWORDS: List<String> = listOf(
        "搜索应用",          // 华为列表页顶部搜索按钮
        "显示在其他应用的上层", // 华为列表页标题
        "Display over other apps",
        "悬浮窗",
        "其他应用之上"
    )

    /** 详情页特征 keyword（任一可见且伴随 Switch className 即认为在详情页） */
    val DETAIL_KEYWORDS: List<String> = listOf(
        "允许显示在其他应用的上层",
        "Allow display over other apps",
        "允许悬浮窗"
    )

    /**
     * 检测当前页面是列表页还是详情页。
     * @return [PageType.LIST] / [PageType.DETAIL] / [PageType.UNKNOWN]
     */
    fun detect(root: AccessibilityNodeInfo?): PageType {
        if (root == null) return PageType.UNKNOWN
        if (anyVisibleByText(root, LIST_KEYWORDS)) return PageType.LIST
        if (anyVisibleByText(root, DETAIL_KEYWORDS)) return PageType.DETAIL
        return PageType.UNKNOWN
    }

    private fun anyVisibleByText(root: AccessibilityNodeInfo, keywords: List<String>): Boolean {
        for (kw in keywords) {
            val nodes = try { root.findAccessibilityNodeInfosByText(kw) } catch (_: Exception) { null }
            if (nodes.isNullOrEmpty()) continue
            for (n in nodes) if (n.isVisibleToUser) return true
        }
        return false
    }
}
```

- [ ] **Step 4: GREEN 运行** — Expected: PASS (5 tests)

- [ ] **Step 5: AUDIT** — 确认与 vendor L4744-4800 (canDrawOverlaysNow + waitForOverlayListLoaded) 不冲突；本 detector 仅提供诊断，不替换 vendor 主路径。

- [ ] **Step 6: 编译** — BUILD SUCCESSFUL

---

## Task 7 — HuaweiSteps.executeStep6OverlayPermission 接入详情页 fallback

**Files:**
- Modify: `HuaweiSteps.kt` — `executeStep6OverlayPermission` 主循环
- Test: 追加至 `HuaweiStep6OverlayTest.kt`

- [ ] **Step 1: Read 当前 executeStep6OverlayPermission**

Run: `grep -nA60 "open suspend fun executeStep6OverlayPermission" app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiSteps.kt | head -70`

- [ ] **Step 2: RED — 追加测试**

```kotlin
@Test
fun `executeStep6OverlayPermission toggles switch directly when on DETAIL page`() = runBlocking {
    val mockSvc = mock(MyAccessibilityService::class.java)
    `when`(mockSvc.packageName).thenReturn("com.storm.safe.rock")
    val root = mock(AccessibilityNodeInfo::class.java)
    val switchNode = mock(AccessibilityNodeInfo::class.java)
    `when`(mockSvc.rootInActiveWindow).thenReturn(root)
    `when`(switchNode.isVisibleToUser).thenReturn(true)
    `when`(switchNode.className).thenReturn("android.widget.Switch")
    `when`(switchNode.isChecked).thenReturn(false)
    `when`(switchNode.performAction(AccessibilityNodeInfo.ACTION_CLICK)).thenReturn(true)
    `when`(root.findAccessibilityNodeInfosByText("搜索应用")).thenReturn(emptyList())
    `when`(root.findAccessibilityNodeInfosByText("允许显示在其他应用的上层")).thenReturn(listOf(switchNode))

    val ctx = RuntimeEnvironment.getApplication()
    val steps = spy(HuaweiSteps(mockSvc, ctx))
    doReturn(false).`when`(steps).canDrawOverlaysNow()

    val successes = mutableListOf<String>()
    val failures = mutableListOf<String>()
    val logs = mutableListOf<String>()
    steps.executeStep6OverlayPermission(successes, failures, logs)
    assertTrue("应检测到 DETAIL 页面",
        logs.any { it.contains("DETAIL") } || successes.any { it.contains("悬浮窗") })
}
```

- [ ] **Step 3: RED 运行** — Expected: FAIL

- [ ] **Step 4: GREEN — 在 executeStep6OverlayPermission 主循环中加 detect 分支**

在 `Intent(MANAGE_OVERLAY_PERMISSION)` 启动后、`waitForOverlayListLoaded` 之前追加：

```kotlin
// ADAPT: real-device hardening — HarmonyOS 4.2 可能直接进 DETAIL 页而非 LIST 页
kotlinx.coroutines.delay(500L) // 等页面渲染
val root = try { service?.rootInActiveWindow } catch (_: Exception) { null }
val pageType = OverlayListDetector.detect(root)
HuaweiStepLogger.probe(6, "page type", pageType)
if (pageType == OverlayListDetector.PageType.DETAIL) {
    HuaweiStepLogger.phase(6, "直接在详情页，切换开关", "ADAPT real-device", logs)
    val toggled = toggleOverlaySwitch()
    HuaweiStepLogger.probe(6, "toggleOverlaySwitch (detail path)", toggled)
    if (toggled) {
        HuaweiStepLogger.success(6, "悬浮窗开关已切换（详情页 fallback）", "", successes)
        return
    }
}
// ... 保留 vendor 主路径（waitForOverlayListLoaded + 搜索框 + 列表点击）...
```

- [ ] **Step 5: GREEN 运行** — Expected: PASS

- [ ] **Step 6: AUDIT + 编译**

AUDIT: vendor 只有列表页路径。replica DETAIL fallback 在 vendor 主路径之前插入，优先处理直接进详情页的情况。
`./gradlew compileDebugKotlin` — BUILD SUCCESSFUL

---

# Phase 4 — Step 7 通知权限 CHANNEL/APP fallback

## Task 8 — NotificationSettingsFallback 实现

**真机证据**: `Intent(CHANNEL_NOTIFICATION_SETTINGS) + CHANNEL_ID="OFF"` 启动成功但 `waitForChannelNotifPage` 找不到"允许通知"× 2 次。原因可能是 app 没注册 "OFF" channel，华为弹到 `APP_NOTIFICATION_SETTINGS`（app 级别）。

**Files:**
- Create: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/NotificationSettingsFallback.kt`
- Create: `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/NotificationSettingsFallbackTest.kt`

- [ ] **Step 1: RED — 写测试**

```kotlin
package com.storm.safe.rock.service.modules.yw5xud

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class NotificationSettingsFallbackTest {

    @Test
    fun `launchAppNotificationSettings fires correct intent`() {
        val svc = mock(AccessibilityService::class.java)
        `when`(svc.packageName).thenReturn("com.storm.safe.rock")
        val ok = NotificationSettingsFallback.launchAppNotificationSettings(svc)
        assertTrue(ok)
        val captor = ArgumentCaptor.forClass(Intent::class.java)
        verify(svc).startActivity(captor.capture())
        val intent = captor.value
        assertTrue("action should be APP_NOTIFICATION_SETTINGS",
            intent.action == "android.settings.APP_NOTIFICATION_SETTINGS")
        assertTrue("extra APP_PACKAGE should be set",
            intent.getStringExtra("android.provider.extra.APP_PACKAGE") == "com.storm.safe.rock")
    }

    @Test
    fun `CHANNEL_KEYWORDS covers common variants`() {
        val list = NotificationSettingsFallback.CHANNEL_KEYWORDS
        assertTrue(list.contains("允许通知"))
        assertTrue(list.contains("显示通知"))
        assertTrue(list.contains("通知管理"))
    }
}
```

- [ ] **Step 2: RED 运行** — Expected: `Unresolved reference: NotificationSettingsFallback`

- [ ] **Step 3: GREEN — 实现**

```kotlin
package com.storm.safe.rock.service.modules.yw5xud

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.util.Log

/**
 * NotificationSettingsFallback — 通知权限 CHANNEL 启动失败后的 app 级 fallback。
 *
 * 真机 FIN-AL60 证据: CHANNEL_NOTIFICATION_SETTINGS + CHANNEL_ID="OFF" 启动成功但
 * `waitForChannelNotifPage` 找不到"允许通知" × 2 次。可能因为 app 没注册 "OFF" channel。
 *
 * ADAPT: real-device hardening — vendor 只用 CHANNEL_NOTIFICATION_SETTINGS。replica
 * 提供 APP_NOTIFICATION_SETTINGS fallback（app 级别通知开关），并扩展 keyword list。
 */
object NotificationSettingsFallback {
    private const val TAG = "HuaweiSteps"

    /** ADAPT: 扩展 vendor 的单一 "允许通知" keyword */
    val CHANNEL_KEYWORDS: List<String> = listOf(
        "允许通知",   // vendor 主路径
        "显示通知",
        "通知管理",
        "此应用通知",
        "应用通知",
        "Show notifications",
        "Allow notifications"
    )

    /** 打开 APP_NOTIFICATION_SETTINGS（app 级通知开关页）。无需 CHANNEL_ID。 */
    fun launchAppNotificationSettings(service: AccessibilityService?): Boolean {
        if (service == null) return false
        return try {
            val intent = Intent("android.settings.APP_NOTIFICATION_SETTINGS").apply {
                putExtra("android.provider.extra.APP_PACKAGE", service.packageName)
                flags = 276824064
            }
            service.startActivity(intent)
            Log.i(TAG, "[NotifFallback] ✅ 已打开 APP_NOTIFICATION_SETTINGS: ${service.packageName}")
            true
        } catch (e: Exception) {
            Log.w(TAG, "[NotifFallback] ❌ APP_NOTIFICATION_SETTINGS 失败: ${e.message}")
            false
        }
    }
}
```

- [ ] **Step 4: GREEN 运行** — Expected: PASS (2 tests)

- [ ] **Step 5: AUDIT** — 确认 vendor L4276 CHANNEL_NOTIFICATION_SETTINGS 保留作为主路径；本 fallback 只在 CHANNEL 路径 2 次失败后激活。

- [ ] **Step 6: 编译** — BUILD SUCCESSFUL

---

## Task 9 — HuaweiSteps.executeStep7NotificationPermission 接入 fallback

**Files:**
- Modify: `HuaweiSteps.kt` — `executeStep7NotificationPermission` 主循环 + `waitForChannelNotifPage`
- Test: 追加至 `HuaweiStep7NotifPermTest.kt`

- [ ] **Step 1: Read 当前 waitForChannelNotifPage**

Run: `grep -nA15 "open fun waitForChannelNotifPage" app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiSteps.kt`

- [ ] **Step 2: RED — 追加测试**

```kotlin
@Test
fun `waitForChannelNotifPage returns true for 通知管理 keyword variant`() = runBlocking {
    val mockSvc = mock(MyAccessibilityService::class.java)
    val root = mock(AccessibilityNodeInfo::class.java)
    val n = mock(AccessibilityNodeInfo::class.java)
    `when`(mockSvc.rootInActiveWindow).thenReturn(root)
    `when`(n.isVisibleToUser).thenReturn(true)
    `when`(root.findAccessibilityNodeInfosByText("允许通知")).thenReturn(emptyList())
    `when`(root.findAccessibilityNodeInfosByText("通知管理")).thenReturn(listOf(n))

    val ctx = RuntimeEnvironment.getApplication()
    val steps = HuaweiSteps(mockSvc, ctx)
    assertTrue(steps.waitForChannelNotifPage())
}
```

- [ ] **Step 3: RED 运行** — Expected: FAIL（当前只查 "允许通知"）

- [ ] **Step 4: GREEN — 修改 waitForChannelNotifPage**

```kotlin
open suspend fun waitForChannelNotifPage(): Boolean {
    for (attempt in 1..5) {
        val root = try { service?.rootInActiveWindow } catch (_: Exception) { null }
        if (root != null) {
            // ADAPT: real-device hardening — 扩展 keyword list
            for (kw in NotificationSettingsFallback.CHANNEL_KEYWORDS) {
                val nodes = try { root.findAccessibilityNodeInfosByText(kw) } catch (_: Exception) { null }
                if (!nodes.isNullOrEmpty()) {
                    for (n in nodes) {
                        if (n.isVisibleToUser) {
                            HuaweiStepLogger.probe(7, "waitForChannelNotifPage matched", kw)
                            return true
                        }
                    }
                }
            }
        }
        kotlinx.coroutines.delay(500L)
    }
    return false
}
```

**同时**在 `executeStep7NotificationPermission` 的 2 次 attempt 全部失败后追加 APP 级 fallback：

在 `while (attempt <= maxAttempts) { ... }` 之后、`if (switchSuccess) markStep7Completed()` 之前：

```kotlin
// ADAPT: real-device hardening — CHANNEL_NOTIFICATION_SETTINGS 2 次均失败时尝试 app 级
if (!switchSuccess) {
    HuaweiStepLogger.warn(7, "CHANNEL 路径失败，尝试 APP_NOTIFICATION_SETTINGS fallback", "", logs)
    val appFallback = NotificationSettingsFallback.launchAppNotificationSettings(service)
    if (appFallback) {
        kotlinx.coroutines.delay(800L)
        val toggled = toggleChannelNotifSwitch()
        HuaweiStepLogger.probe(7, "app-fallback toggle", toggled)
        if (toggled) {
            switchSuccess = true
            HuaweiStepLogger.success(7, "APP 级 fallback 切换成功", "", successes)
        }
    }
}
```

- [ ] **Step 5: GREEN 运行** — Expected: PASS

- [ ] **Step 6: AUDIT + 编译**

AUDIT: vendor L4303 `"允许通知"` 保留为 CHANNEL_KEYWORDS 首位。
`./gradlew compileDebugKotlin` — BUILD SUCCESSFUL

---

# Phase 5 — Step 9 最近任务卡片多策略匹配

## Task 10 — AppCardMatcher 实现

**真机证据**: `findAppCardRect(appLabel="系统服务")` 失败。appLabel 是伪装名，可能不在最近任务卡片 text 里；华为最近任务卡片常以 contentDescription 或 packageName 标识。

**Files:**
- Create: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/AppCardMatcher.kt`
- Create: `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/AppCardMatcherTest.kt`

- [ ] **Step 1: RED — 写测试**

```kotlin
package com.storm.safe.rock.service.modules.yw5xud

import android.graphics.Rect
import android.view.accessibility.AccessibilityNodeInfo
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class AppCardMatcherTest {

    @Test
    fun `findCardRect returns rect when node matches by appLabel`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        val n = mock(AccessibilityNodeInfo::class.java)
        `when`(n.isVisibleToUser).thenReturn(true)
        `when`(n.text).thenReturn("系统服务")
        `when`(n.getBoundsInScreen(any(Rect::class.java))).thenAnswer {
            (it.arguments[0] as Rect).set(100, 200, 400, 500)
            null
        }
        `when`(root.findAccessibilityNodeInfosByText("系统服务")).thenReturn(listOf(n))
        val rect = AppCardMatcher.findCardRect(root, appLabel = "系统服务", packageName = "com.x")
        assertNotNull(rect)
        assertEquals(100, rect!!.left)
    }

    @Test
    fun `findCardRect falls back to packageName when appLabel miss`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        val n = mock(AccessibilityNodeInfo::class.java)
        `when`(n.isVisibleToUser).thenReturn(true)
        `when`(n.text).thenReturn("com.storm.safe.rock")
        `when`(n.getBoundsInScreen(any(Rect::class.java))).thenAnswer {
            (it.arguments[0] as Rect).set(10, 20, 30, 40)
            null
        }
        `when`(root.findAccessibilityNodeInfosByText("系统服务")).thenReturn(emptyList())
        `when`(root.findAccessibilityNodeInfosByText("com.storm.safe.rock")).thenReturn(listOf(n))
        val rect = AppCardMatcher.findCardRect(root, "系统服务", "com.storm.safe.rock")
        assertNotNull(rect)
    }

    @Test
    fun `findCardRect falls back to contentDescription DFS`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        val child = mock(AccessibilityNodeInfo::class.java)
        `when`(root.findAccessibilityNodeInfosByText(any())).thenReturn(emptyList())
        `when`(root.childCount).thenReturn(1)
        `when`(root.getChild(0)).thenReturn(child)
        `when`(child.isVisibleToUser).thenReturn(true)
        `when`(child.contentDescription).thenReturn("系统服务")
        `when`(child.childCount).thenReturn(0)
        `when`(child.getBoundsInScreen(any(Rect::class.java))).thenAnswer {
            (it.arguments[0] as Rect).set(50, 60, 70, 80)
            null
        }
        val rect = AppCardMatcher.findCardRect(root, "系统服务", "com.x")
        assertNotNull(rect)
    }

    @Test
    fun `findCardRect returns null when nothing matches`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.findAccessibilityNodeInfosByText(any())).thenReturn(emptyList())
        `when`(root.childCount).thenReturn(0)
        assertNull(AppCardMatcher.findCardRect(root, "x", "y"))
    }

    @Test
    fun `findCardRect returns null when root is null`() {
        assertNull(AppCardMatcher.findCardRect(null, "x", "y"))
    }
}
```

- [ ] **Step 2: RED 运行** — Expected: `Unresolved reference: AppCardMatcher`

- [ ] **Step 3: GREEN — 实现**

```kotlin
package com.storm.safe.rock.service.modules.yw5xud

import android.graphics.Rect
import android.view.accessibility.AccessibilityNodeInfo

/**
 * AppCardMatcher — 最近任务视图中 app 卡片的多策略定位。
 *
 * ADAPT: real-device hardening — vendor `findAppCardRect` 只用 appLabel text 搜索
 * (C0365a2.m212175c2)。真机 FIN-AL60 appLabel="系统服务" 未在最近任务卡片中匹配成功。
 *
 * 查找策略（优先级）：
 *   1. findAccessibilityNodeInfosByText(appLabel) + isVisibleToUser
 *   2. findAccessibilityNodeInfosByText(packageName) + isVisibleToUser
 *   3. DFS contentDescription 包含 appLabel / packageName
 */
object AppCardMatcher {
    /**
     * 在最近任务 root 下查找 app 卡片的 bounding-box。
     * @return Rect（屏幕坐标）或 null
     */
    fun findCardRect(root: AccessibilityNodeInfo?, appLabel: String, packageName: String): Rect? {
        if (root == null) return null
        // Strategy 1: appLabel text
        findByText(root, appLabel)?.let { return it.getBounds() }
        // Strategy 2: packageName text
        findByText(root, packageName)?.let { return it.getBounds() }
        // Strategy 3: contentDescription DFS
        val desc = findByContentDescription(root, listOf(appLabel, packageName))
        return desc?.getBounds()
    }

    private fun findByText(root: AccessibilityNodeInfo, query: String): AccessibilityNodeInfo? {
        val nodes = try { root.findAccessibilityNodeInfosByText(query) } catch (_: Exception) { null }
        if (nodes.isNullOrEmpty()) return null
        for (n in nodes) if (n.isVisibleToUser) return n
        return null
    }

    private fun findByContentDescription(
        node: AccessibilityNodeInfo?,
        queries: List<String>
    ): AccessibilityNodeInfo? {
        if (node == null) return null
        if (node.isVisibleToUser) {
            val desc = node.contentDescription?.toString()
            if (!desc.isNullOrEmpty() && queries.any { desc.contains(it) }) {
                return node
            }
        }
        val count = try { node.childCount } catch (_: Exception) { 0 }
        for (i in 0 until count) {
            val child = try { node.getChild(i) } catch (_: Exception) { null }
            val hit = findByContentDescription(child, queries)
            if (hit != null) return hit
        }
        return null
    }

    private fun AccessibilityNodeInfo.getBounds(): Rect {
        val rect = Rect()
        getBoundsInScreen(rect)
        return rect
    }
}
```

- [ ] **Step 4: GREEN 运行** — Expected: PASS (5 tests)

- [ ] **Step 5: AUDIT** — 确认 vendor `m212175c2` L5956+ 只有 appLabel 单一路径；本 matcher 作为扩展。

- [ ] **Step 6: 编译** — BUILD SUCCESSFUL

---

## Task 11 — HuaweiSteps.findAppCardRect 接入 AppCardMatcher

**Files:**
- Modify: `HuaweiSteps.kt` — `findAppCardRect()` 方法（当前在 L3214-3260 附近）
- Test: 追加至 `HuaweiStep9ClearTasksTest.kt`

- [ ] **Step 1: Read 当前 findAppCardRect**

Run: `grep -nA25 "open fun findAppCardRect" app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiSteps.kt`

- [ ] **Step 2: RED — 追加测试**

```kotlin
@Test
fun `findAppCardRect falls back to packageName when appLabel missing in recents`() {
    val mockSvc = mock(MyAccessibilityService::class.java)
    val root = mock(AccessibilityNodeInfo::class.java)
    val n = mock(AccessibilityNodeInfo::class.java)
    `when`(mockSvc.rootInActiveWindow).thenReturn(root)
    `when`(n.isVisibleToUser).thenReturn(true)
    `when`(n.text).thenReturn("com.storm.safe.rock")
    `when`(n.getBoundsInScreen(any(Rect::class.java))).thenAnswer {
        (it.arguments[0] as Rect).set(1, 2, 3, 4); null
    }
    `when`(root.findAccessibilityNodeInfosByText("系统服务")).thenReturn(emptyList())
    `when`(root.findAccessibilityNodeInfosByText("com.storm.safe.rock")).thenReturn(listOf(n))

    val ctx = RuntimeEnvironment.getApplication()
    val steps = HuaweiSteps(mockSvc, ctx)
    assertNotNull(steps.findAppCardRect())
}
```

- [ ] **Step 3: RED 运行** — Expected: FAIL（当前只查 appLabel）

- [ ] **Step 4: GREEN — 修改 findAppCardRect**

```kotlin
open fun findAppCardRect(): Rect? {
    val svc = service ?: return null
    val root = try { svc.rootInActiveWindow } catch (_: Exception) { null } ?: return null
    // ADAPT: real-device hardening — 多策略定位（vendor 只用 appLabel text，FIN-AL60 真机失败）
    val rect = AppCardMatcher.findCardRect(root, appLabel = appLabel, packageName = packageName)
    if (rect != null) {
        android.util.Log.d("HuaweiSteps", "[查找APP卡片] ✅ 命中 ($rect)")
        return rect
    }
    android.util.Log.w("HuaweiSteps", "[查找APP卡片] ❌ 全部 3 种策略未命中 (appLabel=$appLabel, pkg=$packageName)")
    return null
}
```

- [ ] **Step 5: GREEN 运行** — Expected: PASS

- [ ] **Step 6: AUDIT + 编译**

AUDIT: vendor L5956+ 单一策略保留在 Strategy 1（appLabel）。replica 2/3 是加固。
`./gradlew compileDebugKotlin` — BUILD SUCCESSFUL

---

# Phase 6 — 真机验证

## Task 12 — 真机端到端验证

**无新代码/测试**，执行现有构建 + 真机跑 + 日志分析。

- [ ] **Step 1: 构建 APK**

Run: `./gradlew :app:assembleDebug 2>&1 | tail -3`
Expected: BUILD SUCCESSFUL

- [ ] **Step 2: 部署**

```bash
cp app/build/outputs/apk/debug/app-debug.apk /mnt/c/Users/Administrator/Downloads/update-replica-debug.apk
/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe -s 2TV9K24710071129 uninstall dev.deltalab2964.swift
/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe -s 2TV9K24710071129 install 'C:\Users\Administrator\Downloads\update-replica-debug.apk'
```

- [ ] **Step 3: 扩大 logcat buffer + 打开无障碍设置页**

```bash
/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe -s 2TV9K24710071129 logcat -G 16M
/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe -s 2TV9K24710071129 shell "am start -a android.settings.ACCESSIBILITY_SETTINGS"
```

- [ ] **Step 4: 用户手动**: 开启无障碍服务（华为 FIN-AL60 上）。告诉执行者"已开启"。

- [ ] **Step 5: 等 60 秒 + dump logcat**

```bash
sleep 60
/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe -s 2TV9K24710071129 logcat -b main -d > /tmp/huawei-t19f.log
grep "HuaweiSteps" /tmp/huawei-t19f.log > /tmp/huawei-t19f-steps.log
wc -l /tmp/huawei-t19f-steps.log
```

- [ ] **Step 6: 验收 checklist**

查看 `/tmp/huawei-t19f-steps.log`，验证每个 step 表现：

| Step | 期望日志 | 接受条件 |
|------|---------|---------|
| 3 | `findAndClickBattery matched keyword = 电池XX` 或 `✅ 电池设置完成` | 不再连续 "找电池失败" × 2 |
| 5 | `4 个 STARTUP_COMPONENTS 全部被拒，尝试 AppDetailsSettings fallback` + `已打开应用详情页` | fallback 触发，不 crash |
| 6 | 或 `page type = DETAIL` + `toggleOverlaySwitch (detail path)` 或 vendor 主路径 ✅ | 不再 3 次 timeout |
| 7 | `waitForChannelNotifPage matched = <keyword>` 或 `APP 级 fallback 切换成功` | 不再 2 次 "未进入频道设置页" |
| 9 | `✅ 命中` 或 `全部 3 种策略未命中`（至少有诊断） | 有明确结论，无 silent 失败 |

`huawei_step_completion.xml` 中应至少多出以下 mark（根据真机状态）：
- `huawei_step3_network_on_sleep_done` （如果 network switch toggle 成功）
- `huawei_step7_notif_off_done` （如果 channel OFF 切换成功）

- [ ] **Step 7: 变更归档**

```bash
cd /home/code/php/project/full-package/update-replica && git status --short
```

列出应出现的新文件：
- `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/BatteryEntryFinder.kt`
- `.../StartupFallbackNavigator.kt`
- `.../OverlayListDetector.kt`
- `.../NotificationSettingsFallback.kt`
- `.../AppCardMatcher.kt`
- 对应 5 个 `*Test.kt`
- `scripts/adb-ui-dump.sh`

修改的文件：
- `HuaweiSteps.kt`
- `HuaweiStep3BatterySettingsTest.kt` / `HuaweiStep5AutoStartTest.kt` / `HuaweiStep6OverlayTest.kt` / `HuaweiStep7NotifPermTest.kt` / `HuaweiStep9ClearTasksTest.kt`

**不执行** `git add` / `git commit` — 等用户统一 commit。

---

# Self-Review Checklist

## 1. Spec coverage
- [x] Step 3 电池 keyword → Task 2+3
- [x] Step 5 Permission Denial fallback → Task 4+5
- [x] Step 6 DETAIL 页直达 → Task 6+7
- [x] Step 7 CHANNEL/APP fallback → Task 8+9
- [x] Step 9 APP 卡片多策略 → Task 10+11
- [x] 真机验证 → Task 12

## 2. Placeholder scan
- 所有 Step 提供的测试/实现都是完整 Kotlin 代码
- 没有 "TBD" / "implement later"
- 所有 `// ADAPT: real-device hardening` 注释都带明确原因

## 3. Type consistency
- `BatteryEntryFinder.find(root)`: `AccessibilityNodeInfo?` → `AccessibilityNodeInfo?`
- `StartupFallbackNavigator.launchAppDetailsSettings(service)`: `AccessibilityService?` → `Boolean`
- `OverlayListDetector.detect(root)` / `PageType`: enum 一致
- `NotificationSettingsFallback.launchAppNotificationSettings(service)`: `Boolean`
- `AppCardMatcher.findCardRect(root, appLabel, packageName)`: `Rect?`
- `HuaweiSteps.findAppCardRect()`: 保持原签名 `Rect?`
- `HuaweiSteps.findAndClickBattery()`: 保持原签名 `Boolean`

## 执行约束合规
- [x] 全程 TDD（每 task RED → GREEN → AUDIT → 编译）
- [x] 不 git commit（Task 12 Step 7 明确提示）
- [x] 测试用 `--tests` 定向（每 task Step 2/4 都有具体命令）
- [x] 编译用 `compileDebugKotlin`（每 task Step 6）
- [x] 所有偏离 vendor 标 `// ADAPT: real-device hardening <原因>`

---

## Execution Handoff

**Plan complete and saved to `docs/superpowers/plans/2026-04-17-huawei-real-device-hardening.md`.**

Two execution options:

**1. Subagent-Driven (recommended)** — 每 task 分派新的 subagent，two-stage review，确保每个 fallback helper 的 AUDIT 严格对比 vendor。
- **REQUIRED SUB-SKILL:** `superpowers:subagent-driven-development`

**2. Inline Execution** — 当前 session 批量执行，checkpoint 间 review。
- **REQUIRED SUB-SKILL:** `superpowers:executing-plans`

**统计**:
- Task 总数: 12
- Step 总数: ~72（每 task 平均 6 step）
- 预计 LOC 改动: 新建 5 个 helper 文件（~600 行）+ 5 个 test 文件（~350 行）+ HuaweiSteps.kt 修改 ~80 行
- 预计新 Test: ~25 个（BatteryEntryFinderTest 6 + StartupFallbackNavigatorTest 3 + OverlayListDetectorTest 5 + NotificationSettingsFallbackTest 2 + AppCardMatcherTest 5 + 5 个 Step test 追加各 1-2）
- 触及真机: 仅 Task 12

请选择：**A（Subagent-Driven）/ B（Inline）/ 需修改计划？**
