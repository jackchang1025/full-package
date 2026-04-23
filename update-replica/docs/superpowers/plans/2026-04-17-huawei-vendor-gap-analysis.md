# 华为 vendor 差异对齐 — 基于权限获取机制分析文档的复刻计划

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 基于 `docs/华为荣耀权限获取机制分析.md` 文档，对齐 replica 与 vendor 在 Switch 类名、PERMISSION_ALLOW_TEXTS、自启动 BFS 导航、SP 标记、电池弹窗确认词等 9 个维度的差异，使华为 10 步流程在 HarmonyOS 4.2 真机上每个 step 都能精准匹配 UI 控件。

**Architecture:** 保持现有 HuaweiSteps.kt 结构不变，通过扩展 companion 常量 + 新增 helper 方法 + 补齐 SP key 的方式逐项对齐。每项改动都参照文档的精确 keyword 列表和 vendor 行号。

**Tech Stack:** Kotlin 1.9 + Android AccessibilityService + JUnit 4 + Robolectric 4.11 + Mockito 5.3.1

**硬约束**：
- **不 git commit** — 后续统一 commit
- **不跑 `./gradlew test` / `./gradlew build` / `./gradlew assembleDebug`** — 太慢，后续统一执行
- **只用 `./gradlew compileDebugKotlin`** 做快速编译验证（~2s）
- **TDD 严格**：先写测试文件 → 再写实现 → compileDebugKotlin 验证编译通过
- **Subagent 模型**：opus 4.6
- 偏离 vendor 标 `// ADAPT:`

---

## 差异全景（文档 vs Replica）

| # | 维度 | 文档定义 | Replica 现状 | 差距 | 优先级 |
|---|------|---------|-------------|------|--------|
| 1 | **Switch 类名数组** | 8 个（含 HwSwitch/CheckBox/ToggleButton/CompoundButton） | `className.contains("Switch")` 简单匹配 | **遗漏 CheckBox/ToggleButton/CompoundButton** | P0 |
| 2 | **自启动 BFS 导航** | 4 组文本数组（AUTO_START_ENTRY/MANAGER/TEXTS/SWITCH_TEXTS） | 只有 Intent 直连 + SWITCH_TEXTS | **缺 3 组 BFS 导航词 = Step 5 无法 fallback 到 UI 导航** | P0 |
| 3 | **PERMISSION_ALLOW_TEXTS** | ~50 条华为专属词 | AllowKeywords.ALLOW 76 条（基础"允许"词） | **缺 "仅使用期间允许"/"每次都询问"/"忽略" 等华为弹窗专属词** | P0 |
| 4 | **SP Keys** | 10 个 | 8 个 | **缺 autostart_completed / overlay_completed** | P0 |
| 5 | **电池弹窗确认词** | 14 个（忽略/关闭/不优化/不再提醒…） | 只查 ALLOW 词库 | 缺华为电池弹窗专属确认词 | P1 |
| 6 | **电池入口关键词** | 5 个（电池/电源/耗电/Battery/Power） | BatteryEntryFinder 10 个 | 缺 "电源"/"耗电" | P1 |
| 7 | **Step 10 荣耀图库** | handleHonorGalleryPermission | 完全缺失 | 仅荣耀设备影响 | P1 |
| 8 | **HarmonyOS 检测** | 反射检测 + Build.DISPLAY | 无 | 版本特定适配路径缺失 | P2 |
| 9 | **折叠屏适配** | openSettingsWithVerify 特殊处理 | 简化版 | 折叠屏设备影响 | P2 |

---

## Task 1 — Switch 类名数组扩展（P0，影响所有 toggle 操作）

**背景**: 文档定义 vendor `f55054c0` = 8 个 Switch 识别类名。replica 的 `findFirstSwitchInTree` / `SwitchNodeFinder` 只用 `className.contains("Switch")` 匹配，遗漏了 `CheckBox`/`ToggleButton`/`CompoundButton`。真机 `toggleOverlaySwitch=false` 可能就是因为华为用了 `HwSwitch` 类名但 DFS 匹配不到。

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiSteps.kt` — `findFirstSwitchInTree` / `clickFirstSwitchOnDetailPage`
- Create: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/SwitchClassNames.kt`（如不存在）
- Test: 追加 `HuaweiStepsTest.kt`

- [ ] **Step 1: RED — 写测试**

```kotlin
@Test
fun `SWITCH_CLASS_NAMES contains all 8 vendor class names`() {
    val names = SwitchClassNames.ALL
    assertTrue(names.contains("com.huawei.hwswitchwidget.HwSwitch"))
    assertTrue(names.contains("com.hihonor.widget.Switch"))
    assertTrue(names.contains("com.hihonor.android.widget.Switch"))
    assertTrue(names.contains("androidx.appcompat.widget.SwitchCompat"))
    assertTrue(names.contains("android.widget.Switch"))
    assertTrue(names.contains("android.widget.CheckBox"))
    assertTrue(names.contains("android.widget.ToggleButton"))
    assertTrue(names.contains("android.widget.CompoundButton"))
    assertEquals(8, names.size)
}
```

- [ ] **Step 2: RED 运行** → FAIL

- [ ] **Step 3: GREEN — 创建 SwitchClassNames.kt**

```kotlin
package com.storm.safe.rock.service.modules.yw5xud

/**
 * 对齐 vendor f55054c0 — 8 个 Switch 控件类名。
 * 华为 HwSwitch / 荣耀 Switch / AndroidX / AOSP 标准控件。
 */
object SwitchClassNames {
    val ALL: List<String> = listOf(
        "com.huawei.hwswitchwidget.HwSwitch",     // 华为定制
        "com.hihonor.widget.Switch",               // 荣耀定制
        "com.hihonor.android.widget.Switch",       // 荣耀新版
        "androidx.appcompat.widget.SwitchCompat",  // AndroidX
        "android.widget.Switch",                   // AOSP
        "android.widget.CheckBox",                 // 复选框
        "android.widget.ToggleButton",             // 切换按钮
        "android.widget.CompoundButton"            // 通用复合按钮
    )

    fun isSwitch(className: String?): Boolean {
        if (className == null) return false
        return ALL.any { className == it || className.endsWith(it.substringAfterLast('.')) }
    }
}
```

- [ ] **Step 4: 修改 findFirstSwitchInTree 使用 SwitchClassNames**

定位 `findFirstSwitchInTree` / `clickFirstSwitchOnDetailPage` 里的 `className.contains("Switch")` 替换为 `SwitchClassNames.isSwitch(className)`。

- [ ] **Step 5: GREEN 运行** → PASS
- [ ] **Step 6: 编译**

---

## Task 2 — 自启动 BFS 导航文本数组（P0，Step 5 UI 导航 fallback）

**背景**: 文档定义 4 组导航文本数组。当 Intent 直连被 Permission Denial 拒绝时，vendor 通过 BFS（从设置主页逐级导航到自启动管理页面）。replica 目前只有 StartupFallbackNavigator（打开应用详情页），**没有 BFS 导航到自启动管理页的能力**。

**Files:**
- Create: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/AutoStartNavigator.kt`
- Test: `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/AutoStartNavigatorTest.kt`

- [ ] **Step 1: RED — 写测试**

```kotlin
@Test fun `AUTO_START_ENTRY_TEXTS covers 应用和服务 and variants`() {
    assertTrue(AutoStartNavigator.ENTRY_TEXTS.contains("应用和服务"))
    assertTrue(AutoStartNavigator.ENTRY_TEXTS.contains("应用与权限"))
    assertTrue(AutoStartNavigator.ENTRY_TEXTS.contains("应用管理"))
}
@Test fun `AUTO_START_MANAGER_TEXTS covers 应用启动管理`() {
    assertTrue(AutoStartNavigator.MANAGER_TEXTS.contains("应用启动管理"))
    assertTrue(AutoStartNavigator.MANAGER_TEXTS.contains("启动管理"))
    assertTrue(AutoStartNavigator.MANAGER_TEXTS.contains("自启动管理"))
}
@Test fun `navigatePath returns ordered list of 3 text groups`() {
    val path = AutoStartNavigator.navigationPath()
    assertEquals(3, path.size)
}
```

- [ ] **Step 2: RED 运行** → FAIL

- [ ] **Step 3: GREEN — 创建 AutoStartNavigator.kt**

```kotlin
package com.storm.safe.rock.service.modules.yw5xud

/**
 * 自启动管理 BFS 导航文本。对齐文档 §4 定义的 4 组数组。
 *
 * Intent 直连失败（Permission Denial）后，从设置主页逐级导航：
 *   "应用和服务" → "应用启动管理" → 找到目标 app → 开三 switch
 */
object AutoStartNavigator {
    /** 第 1 级导航: 设置主页 → "应用和服务"/"应用管理" */
    val ENTRY_TEXTS: List<String> = listOf(
        "应用和服务", "应用与权限", "应用管理", "应用", "Apps", "Apps & services"
    )

    /** 第 2 级导航: "应用和服务" → "应用启动管理" */
    val MANAGER_TEXTS: List<String> = listOf(
        "应用启动管理", "启动管理", "自启动管理", "Startup manager", "Auto-launch"
    )

    /** 第 3 级导航: 在启动管理列表中搜索目标 app */
    val APP_TEXTS: List<String> = listOf(
        "自启动", "自动启动", "启动管理", "Auto-start", "Autostart"
    )

    /** 三 switch 文本（已有于 HuaweiSteps companion） */
    val SWITCH_TEXTS: List<String> = listOf(
        "允许自启动", "允许关联启动", "允许后台活动",
        "允許自啟動", "允許關聯啟動", "允許後台活動",
        "Allow auto-launch", "Allow associated startup", "Allow background activity"
    )

    /** 有序导航路径：3 个层级 */
    fun navigationPath(): List<List<String>> = listOf(ENTRY_TEXTS, MANAGER_TEXTS, APP_TEXTS)
}
```

- [ ] **Step 4: GREEN 运行** → PASS
- [ ] **Step 5: 修改 executeStep5AutoStart 在 fallback 后用 AutoStartNavigator BFS 导航**

在 `!launched` 分支、`StartupFallbackNavigator.launchAppDetailsSettings` 之后追加：

```kotlin
// ADAPT: real-device hardening — Intent 直连被 Permission Denial 后，
// 用 BFS 导航：设置主页 → 应用和服务 → 应用启动管理 → 找 app → 开 3 switch
HuaweiStepLogger.phase(5, "BFS 导航 fallback", "从设置主页导航到自启动管理", logs)
for (entryText in AutoStartNavigator.ENTRY_TEXTS) {
    if (clickTextOnCurrentRoot(entryText, exact = false)) {
        delay(1000L)
        for (mgmtText in AutoStartNavigator.MANAGER_TEXTS) {
            if (clickTextOnCurrentRoot(mgmtText, exact = false)) {
                delay(1000L)
                // 到达启动管理列表 → 找 app + 开 switch
                if (scrollFindAndClickApp(appLabel, maxScrolls = 5)) {
                    delay(1000L)
                    // 三 switch
                    for (sw in AutoStartNavigator.SWITCH_TEXTS) {
                        clickTextOnCurrentRoot(sw, exact = false)
                        delay(300L)
                    }
                    successes.add("[Step5/10] BFS 导航成功 — 自启动已配置")
                    return
                }
                break
            }
        }
        break
    }
}
```

- [ ] **Step 6: 编译**

---

## Task 3 — PERMISSION_ALLOW_TEXTS 华为专属词补齐（P0）

**背景**: 文档 §1 定义了 ~50 条华为专属权限弹窗确认词。replica AllowKeywords.ALLOW 有 76 条基础"允许"词，但缺少华为特有的 "仅使用期间允许"/"每次都询问"/"忽略" 等词。

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/AllowKeywords.kt` — 在 ALLOW 列表末尾追加华为专属词
- Test: 追加 `AllowKeywordsTest.kt`

- [ ] **Step 1: RED — 写测试**

```kotlin
@Test fun `ALLOW contains huawei-specific permission dialog keywords`() {
    val list = AllowKeywords.ALLOW
    assertTrue("缺 '仅使用期间允许'", list.contains("仅使用期间允许"))
    assertTrue("缺 '本次使用允许'", list.contains("本次使用允许"))
    assertTrue("缺 '每次都询问'", list.contains("每次都询问"))
    assertTrue("缺 '忽略'", list.contains("忽略"))
    assertTrue("缺 '不再提示'", list.contains("不再提示"))
    assertTrue("缺 '全部允许'", list.contains("全部允许"))
    assertTrue("缺 '允许全部'", list.contains("允许全部"))
    assertTrue("缺 'Allow always'", list.contains("Allow always"))
    assertTrue("缺 'While using the app'", list.contains("While using the app"))
}
```

- [ ] **Step 2: RED 运行** → FAIL
- [ ] **Step 3: GREEN — 在 AllowKeywords.ALLOW 末尾追加华为专属词**

```kotlin
// ADAPT: 华为专属权限弹窗确认词 (文档 §1 PERMISSION_ALLOW_TEXTS f55055c1)
"仅使用期间允许", "本次使用允许", "允许本次使用", "本次使用时允许",
"每次都询问", "忽略", "不再提示", "不再询问", "知道了", "我知道了",
"允许管理所有文件", "允许访问所有文件",
"允许使用照片和视频", "允许访问照片和视频",
"允许通知", "发送通知", "全部允许", "允许全部",
"开启", "打开", "同意",
// 繁体
"僅使用期間允許", "本次使用允許", "允許本次使用",
"允許管理所有檔案", "允許存取所有檔案",
"全部允許", "開啟", "打開", "同意",
// 英文
"Allow always", "While using the app", "Agree", "Permit"
```

- [ ] **Step 4: GREEN 运行** → PASS
- [ ] **Step 5: 编译**

---

## Task 4 — SP Keys 补齐 autostart_completed / overlay_completed（P0）

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiStepCompletionStore.kt`
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiSteps.kt` — Step 5/6 成功后 mark
- Test: 追加 `HuaweiStepCompletionStoreTest.kt`

- [ ] **Step 1: RED — 写测试**

```kotlin
@Test fun `Keys contains STEP5_AUTOSTART and STEP6_OVERLAY`() {
    assertNotNull(HuaweiStepCompletionStore.Keys.STEP5_AUTOSTART)
    assertNotNull(HuaweiStepCompletionStore.Keys.STEP6_OVERLAY)
    assertEquals("huawei_step5_autostart_done", HuaweiStepCompletionStore.Keys.STEP5_AUTOSTART)
    assertEquals("huawei_step6_overlay_done", HuaweiStepCompletionStore.Keys.STEP6_OVERLAY)
}
```

- [ ] **Step 2: RED 运行** → FAIL
- [ ] **Step 3: GREEN — 在 Keys object 追加 2 个常量**

```kotlin
/** Step 5 自启动 (vendor f55071a9 = "autostart_completed") */
const val STEP5_AUTOSTART = "huawei_step5_autostart_done"
/** Step 6 悬浮窗 (vendor f55072b0 = "overlay_completed") */
const val STEP6_OVERLAY = "huawei_step6_overlay_done"
```

- [ ] **Step 4: 在 executeStep5AutoStart 成功路径加 mark**
- [ ] **Step 5: 在 executeStep6OverlayPermission `canDrawOverlaysNow()=true` 处加 mark**
- [ ] **Step 6: GREEN 运行** → PASS
- [ ] **Step 7: 编译**

---

## Task 5 — 电池弹窗确认词扩展（P1）

**背景**: 文档 §2 定义了 14 个电池优化弹窗确认词。replica Step 2 `executeStep2BatteryWhitelist` 使用 AllowKeywords 词库查找，但缺少华为电池弹窗专属词 "忽略"/"不优化"/"不再提醒"。

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiSteps.kt` — Step 2 确认词
- Create: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/BatteryDialogKeywords.kt`

- [ ] **Step 1: RED — 写测试**

```kotlin
@Test fun `BATTERY_CONFIRM_TEXTS covers vendor 14 keywords`() {
    val list = BatteryDialogKeywords.CONFIRM_TEXTS
    assertTrue(list.contains("忽略"))
    assertTrue(list.contains("关闭"))
    assertTrue(list.contains("不优化"))
    assertTrue(list.contains("不再提醒"))
    assertTrue(list.contains("Ignore"))
    assertTrue(list.contains("Don't optimize"))
    assertTrue(list.size >= 14)
}
```

- [ ] **Step 2: RED 运行** → FAIL
- [ ] **Step 3: GREEN — 创建 BatteryDialogKeywords.kt**

```kotlin
object BatteryDialogKeywords {
    val CONFIRM_TEXTS: List<String> = listOf(
        "忽略", "关闭", "不优化", "允许", "确定", "不再提醒", "知道了",
        "Ignore", "Close", "Don't optimize", "Allow", "OK", "Don't remind", "Got it"
    )
}
```

- [ ] **Step 4: 在 executeStep2BatteryWhitelist 的 approval 循环中使用 BatteryDialogKeywords**
- [ ] **Step 5: GREEN 运行** → PASS
- [ ] **Step 6: 编译**

---

## Task 6 — 电池入口 "电源"/"耗电" 关键词（P1）

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/BatteryEntryFinder.kt`

- [ ] **Step 1: 在 KEYWORDS 列表追加 "电源" / "耗电"**

```kotlin
val KEYWORDS: List<String> = listOf(
    "电池",         // vendor 主路径
    "电池优化", "电池与性能", "省电管理", "电量管理", "电池设置", "电池管理",
    "电源", "耗电",  // ← 新增 (文档 §3)
    "Battery", "Power", "電池"
)
```

- [ ] **Step 2: 编译验证**

---

## Task 7 — 真机验证

- [ ] **Step 1: `./gradlew :app:assembleDebug` + uninstall + install**
- [ ] **Step 2: 用户开启无障碍**
- [ ] **Step 3: `logcat -b main -d` dump 完整日志**
- [ ] **Step 4: 验证 checklist**

| 维度 | 期望 |
|------|------|
| Switch 类名 | `clickFirstSwitchOnDetailPage` 能命中 HwSwitch/CheckBox |
| 自启动 BFS | Step 5 日志出现 "BFS 导航 fallback" + "应用和服务" → "应用启动管理" |
| 权限弹窗 | Step 1 日志能匹配 "仅使用期间允许" 等华为专属词 |
| SP mark | `huawei_step5_autostart_done` / `huawei_step6_overlay_done` 出现 |
| 电池弹窗 | Step 2 日志匹配 "忽略"/"不优化" 等确认词 |

---

## Self-Review

- [x] 文档 §1 权限弹窗词 → Task 3
- [x] 文档 §2 电池白名单确认词 → Task 5
- [x] 文档 §3 电池入口 → Task 6 + 已有 BatteryEntryFinder
- [x] 文档 §4 自启动 BFS 导航 → Task 2
- [x] 文档 §6 悬浮窗 Switch → Task 1 (SwitchClassNames)
- [x] 文档 §8 SP 标记 → Task 4
- [ ] 文档 §5 通知监听 → 已实现 (Step 4)
- [ ] 文档 §7 通知渠道 → 已实现 (Step 7)
- [ ] 文档 §9 最近任务 → 已实现 (Step 9)
- [ ] 文档 §10 荣耀图库 → P2 后续迭代
- [ ] 文档 §五 HarmonyOS → P2 后续迭代
- [ ] 文档 §六 折叠屏 → P2 后续迭代
