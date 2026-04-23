# 厂商目录拆分 + HuaweiSteps God Class 分解 — Phase 3

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 将 37 个平铺文件按厂商分入子目录（`org-feature-folders`），同时将 HuaweiSteps 4178 行 God Class 拆分为 9 个独立 Step 文件（`solid-srp`），消除最后的屎山。

**Architecture:** 两步走 — 先做目录重组（纯 move，零逻辑改动），再做 HuaweiSteps 拆分（Extract Class，每个 step 成为独立文件）。目录重组后 package 声明更新，import 路径更新。HuaweiSteps 拆分为薄编排器 + 9 个 step delegate。

**Tech Stack:** Kotlin, Android, UiAutomation, JUnit 4

**约束:**
- 不执行 git/test/build 命令，最终统一执行
- 在现有 worktree `refactor/gkd-selector-miui-pilot` 上继续工作
- **所有 move 操作必须同步更新 package 声明 + 全项目 import 引用**

**Worktree:**
```bash
WT="/home/code/php/project/full-package/.worktrees/gkd-selector-miui-pilot"
cd "$WT/update-replica"
```

---

## 目标目录结构

```
yw5xud/
├── Yw5xudHandler.kt          # 编排器（保留在根目录）
├── VendorSteps.kt             # 抽象基类
├── StepsFactory.kt            # 工厂
├── BrandDetector.kt           # 品牌检测
├── OsFamily.kt                # OS 家族检测
├── common/                    # 跨厂商共享工具
│   ├── AllowKeywords.kt
│   ├── AppCardMatcher.kt
│   ├── AutoStartNavigator.kt
│   ├── BatteryDialogKeywords.kt
│   ├── BatteryEntryFinder.kt
│   ├── FoldableDeviceDetector.kt
│   ├── GestureTapHelper.kt
│   ├── NotificationSettingsFallback.kt
│   ├── OverlayListDetector.kt
│   ├── StartupFallbackNavigator.kt
│   ├── SwitchClassNames.kt
│   ├── SwitchNodeFinder.kt
│   ├── UiDebugger.kt
│   └── umrkmgrri.kt
├── huawei/                    # 华为/荣耀
│   ├── HuaweiSteps.kt         # 编排器（~200 行）
│   ├── HuaweiBasicPerms.kt    # Step 1
│   ├── HuaweiBatteryWhitelist.kt # Step 2
│   ├── HuaweiBatterySettings.kt  # Step 3
│   ├── HuaweiNotifListener.kt    # Step 4
│   ├── HuaweiAutoStart.kt        # Step 5
│   ├── HuaweiOverlay.kt          # Step 6
│   ├── HuaweiNotifPerm.kt        # Step 7
│   ├── HuaweiAllFiles.kt         # Step 8
│   ├── HuaweiClearTasks.kt       # Step 9
│   ├── HuaweiGestureHelper.kt
│   ├── HuaweiOverlayHelper.kt
│   ├── HuaweiPageDetector.kt
│   ├── HuaweiPermissionRequestActivity.kt
│   ├── HuaweiStepCompletionStore.kt
│   ├── HuaweiStepLogger.kt
│   └── HarmonyVersionDetector.kt
├── miui/                      # 小米
│   └── MiuiSteps.kt
├── oppo/                      # OPPO/Realme/OnePlus
│   ├── OppoSteps.kt
│   ├── OppoBatteryPaths.kt
│   ├── OppoPageDetector.kt
│   ├── OppoStepCompletionStore.kt
│   └── OppoSubBrand.kt
├── vivo/                      # vivo/iQOO
│   └── VivoSteps.kt
├── samsung/                   # 三星
│   └── SamsungSteps.kt
├── meizu/                     # 魅族
│   └── MeizuSteps.kt
└── generic/                   # 通用兜底
    └── GenericSteps.kt
```

---

## Task 1: 目录重组 — 创建子目录 + 移动文件

> 纯文件移动 + package 声明更新，零逻辑改动。
> 用 bash 脚本批量执行，避免手动操作遗漏。

**Files:** 移动 37 个 .kt 文件到子目录

- [ ] **Step 1: 创建目录结构**

```bash
WT="/home/code/php/project/full-package/.worktrees/gkd-selector-miui-pilot/update-replica"
BASE="$WT/app/src/main/java/com/storm/safe/rock/service/modules/yw5xud"

mkdir -p "$BASE/common"
mkdir -p "$BASE/huawei"
mkdir -p "$BASE/miui"
mkdir -p "$BASE/oppo"
mkdir -p "$BASE/vivo"
mkdir -p "$BASE/samsung"
mkdir -p "$BASE/meizu"
mkdir -p "$BASE/generic"
```

- [ ] **Step 2: 移动 common 文件（14 个）**

```bash
for f in AllowKeywords AppCardMatcher AutoStartNavigator BatteryDialogKeywords \
         BatteryEntryFinder FoldableDeviceDetector GestureTapHelper \
         NotificationSettingsFallback OverlayListDetector StartupFallbackNavigator \
         SwitchClassNames SwitchNodeFinder UiDebugger umrkmgrri; do
  mv "$BASE/${f}.kt" "$BASE/common/"
done
```

- [ ] **Step 3: 移动华为文件（8 个，不含 HuaweiSteps.kt — 它在 Task 3 拆分）**

```bash
for f in HuaweiGestureHelper HuaweiOverlayHelper HuaweiPageDetector \
         HuaweiPermissionRequestActivity HuaweiStepCompletionStore \
         HuaweiStepLogger HarmonyVersionDetector; do
  mv "$BASE/${f}.kt" "$BASE/huawei/"
done
# HuaweiSteps.kt 暂不移动 — Task 3 拆分后直接创建在 huawei/
```

- [ ] **Step 4: 移动其他厂商文件**

```bash
# OPPO (5 个)
for f in OppoSteps OppoBatteryPaths OppoPageDetector OppoStepCompletionStore OppoSubBrand; do
  mv "$BASE/${f}.kt" "$BASE/oppo/"
done

# 小米
mv "$BASE/MiuiSteps.kt" "$BASE/miui/"

# vivo
mv "$BASE/VivoSteps.kt" "$BASE/vivo/"

# 三星
mv "$BASE/SamsungSteps.kt" "$BASE/samsung/"

# 魅族
mv "$BASE/MeizuSteps.kt" "$BASE/meizu/"

# 通用
mv "$BASE/GenericSteps.kt" "$BASE/generic/"
```

- [ ] **Step 5: 保留在根目录的文件（5 个）**

以下文件不移动（它们是模块入口/共享契约）：
- `Yw5xudHandler.kt` — 模块编排器
- `VendorSteps.kt` — 抽象基类
- `StepsFactory.kt` — 工厂
- `BrandDetector.kt` — 品牌检测
- `OsFamily.kt` — OS 检测

---

## Task 2: 更新 package 声明 + import 引用

> 每个移动的文件需要更新 package 声明，全项目需要更新 import 路径。

- [ ] **Step 1: 批量更新 package 声明**

```bash
BASE="$WT/app/src/main/java/com/storm/safe/rock/service/modules/yw5xud"

# common 子包
for f in "$BASE/common/"*.kt; do
  sed -i 's/^package com\.storm\.safe\.rock\.service\.modules\.yw5xud$/package com.storm.safe.rock.service.modules.yw5xud.common/' "$f"
done

# 各厂商子包
for vendor in huawei miui oppo vivo samsung meizu generic; do
  for f in "$BASE/$vendor/"*.kt; do
    sed -i "s/^package com\.storm\.safe\.rock\.service\.modules\.yw5xud$/package com.storm.safe.rock.service.modules.yw5xud.${vendor}/" "$f"
  done
done
```

- [ ] **Step 2: 全项目搜索并更新 import 引用**

```bash
SRC="$WT/app/src/main/java"
TEST="$WT/app/src/test/java"

# 为每个移动的文件更新 import
# common 文件
for cls in AllowKeywords AppCardMatcher AutoStartNavigator BatteryDialogKeywords \
           BatteryEntryFinder FoldableDeviceDetector GestureTapHelper \
           NotificationSettingsFallback OverlayListDetector StartupFallbackNavigator \
           SwitchClassNames SwitchNodeFinder UiDebugger umrkmgrri; do
  find "$SRC" "$TEST" -name "*.kt" -exec sed -i \
    "s/import com\.storm\.safe\.rock\.service\.modules\.yw5xud\.${cls}/import com.storm.safe.rock.service.modules.yw5xud.common.${cls}/g" {} +
done

# huawei 文件
for cls in HuaweiGestureHelper HuaweiOverlayHelper HuaweiPageDetector \
           HuaweiPermissionRequestActivity HuaweiStepCompletionStore \
           HuaweiStepLogger HarmonyVersionDetector; do
  find "$SRC" "$TEST" -name "*.kt" -exec sed -i \
    "s/import com\.storm\.safe\.rock\.service\.modules\.yw5xud\.${cls}/import com.storm.safe.rock.service.modules.yw5xud.huawei.${cls}/g" {} +
done

# oppo 文件
for cls in OppoSteps OppoBatteryPaths OppoPageDetector OppoStepCompletionStore OppoSubBrand; do
  find "$SRC" "$TEST" -name "*.kt" -exec sed -i \
    "s/import com\.storm\.safe\.rock\.service\.modules\.yw5xud\.${cls}/import com.storm.safe.rock.service.modules.yw5xud.oppo.${cls}/g" {} +
done

# 各厂商 Steps
for vendor_cls in "miui/MiuiSteps" "vivo/VivoSteps" "samsung/SamsungSteps" "meizu/MeizuSteps" "generic/GenericSteps"; do
  vendor=$(echo "$vendor_cls" | cut -d/ -f1)
  cls=$(echo "$vendor_cls" | cut -d/ -f2)
  find "$SRC" "$TEST" -name "*.kt" -exec sed -i \
    "s/import com\.storm\.safe\.rock\.service\.modules\.yw5xud\.${cls}/import com.storm.safe.rock.service.modules.yw5xud.${vendor}.${cls}/g" {} +
done
```

- [ ] **Step 3: 更新 StepsFactory.kt 的 import**

StepsFactory 引用所有 7 个 Steps 类，需要添加子包 import：

```kotlin
// 在 StepsFactory.kt 头部添加
import com.storm.safe.rock.service.modules.yw5xud.miui.MiuiSteps
import com.storm.safe.rock.service.modules.yw5xud.huawei.HuaweiSteps
import com.storm.safe.rock.service.modules.yw5xud.oppo.OppoSteps
import com.storm.safe.rock.service.modules.yw5xud.vivo.VivoSteps
import com.storm.safe.rock.service.modules.yw5xud.samsung.SamsungSteps
import com.storm.safe.rock.service.modules.yw5xud.meizu.MeizuSteps
import com.storm.safe.rock.service.modules.yw5xud.generic.GenericSteps
```

- [ ] **Step 4: 移动测试文件到对应子目录**

```bash
TBASE="$WT/app/src/test/java/com/storm/safe/rock/service/modules/yw5xud"
mkdir -p "$TBASE/common" "$TBASE/huawei" "$TBASE/miui" "$TBASE/oppo" "$TBASE/vivo" "$TBASE/samsung" "$TBASE/meizu" "$TBASE/generic"

# 华为测试 (17 个)
for f in "$TBASE/"Huawei*Test.kt "$TBASE/"Harmony*Test.kt "$TBASE/"Honor*Test.kt; do
  [ -f "$f" ] && mv "$f" "$TBASE/huawei/"
done

# OPPO 测试 (13 个)
for f in "$TBASE/"Oppo*Test.kt; do
  [ -f "$f" ] && mv "$f" "$TBASE/oppo/"
done

# 小米测试
for f in "$TBASE/"Miui*Test.kt; do
  [ -f "$f" ] && mv "$f" "$TBASE/miui/"
done

# 其他厂商
[ -f "$TBASE/SamsungStepsTest.kt" ] && mv "$TBASE/SamsungStepsTest.kt" "$TBASE/samsung/"
[ -f "$TBASE/VivoStepsTest.kt" ] && mv "$TBASE/VivoStepsTest.kt" "$TBASE/vivo/"
[ -f "$TBASE/MeizuStepsTest.kt" ] && mv "$TBASE/MeizuStepsTest.kt" "$TBASE/meizu/"
[ -f "$TBASE/GenericStepsTest.kt" ] && mv "$TBASE/GenericStepsTest.kt" "$TBASE/generic/"

# common 测试
for f in AllowKeywordsTest AppCardMatcherTest AutoStartNavigatorTest BatteryDialogKeywordsTest \
         BatteryEntryFinderTest FoldableDeviceDetectorTest GestureTapHelperTest \
         NotificationSettingsFallbackTest OverlayListDetectorTest StartupFallbackNavigatorTest \
         SwitchClassNamesTest SwitchNodeFinderTest UiDebuggerTest; do
  [ -f "$TBASE/${f}.kt" ] && mv "$TBASE/${f}.kt" "$TBASE/common/"
done
```

- [ ] **Step 5: 更新测试文件的 package 声明**

同 Step 1 的逻辑，对 test 目录：

```bash
TBASE="$WT/app/src/test/java/com/storm/safe/rock/service/modules/yw5xud"
for vendor in common huawei miui oppo vivo samsung meizu generic; do
  for f in "$TBASE/$vendor/"*.kt; do
    [ -f "$f" ] && sed -i "s/^package com\.storm\.safe\.rock\.service\.modules\.yw5xud$/package com.storm.safe.rock.service.modules.yw5xud.${vendor}/" "$f"
  done
done
```

---

## Task 3: HuaweiSteps God Class 拆分

> 将 4178 行拆为编排器 + 9 个 Step 文件。每个 Step 文件包含对应的 executeStepN 方法及其 private helper。

**策略：** 不改变任何业务逻辑，纯 Extract Class。每个 Step 类接收 `HuaweiSteps` 引用以访问共享状态（`isHuawei`, `appLabel`, `stepDelay` 等）。

- [ ] **Step 1: 读取 HuaweiSteps.kt 全文，分析各 step 边界**

先完整读取文件，确认：
- 每个 executeStepN 方法的起止行号
- 每个 step 依赖的 private helper 方法
- 共享状态（companion object 常量、instance 属性）

- [ ] **Step 2: 创建 9 个 Step delegate 文件**

每个文件的模式：

```kotlin
package com.storm.safe.rock.service.modules.yw5xud.huawei

import com.storm.safe.rock.auto.a11y.UiAutomation
import com.storm.safe.rock.service.MyAccessibilityService
// ... 其他必要 import

class HuaweiBasicPerms(
    private val service: MyAccessibilityService?,
    private val ui: UiAutomation,
    private val steps: HuaweiSteps  // 访问共享状态
) {
    suspend fun execute(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        // 从 HuaweiSteps.executeStep1BasicPermissions 原样搬入
    }

    // 从 HuaweiSteps 搬入的 private helper
    private fun clickPermissionControllerAllowButton(): String? { ... }
}
```

9 个文件对应：

| 文件 | 原方法 | 原行数 | 包含的 helper |
|------|--------|--------|-------------|
| `HuaweiBasicPerms.kt` | executeStep1BasicPermissions | 147 | clickPermissionControllerAllowButton |
| `HuaweiBatteryWhitelist.kt` | executeStep2BatteryWhitelist | 229 | isIgnoringBatteryOptimizations |
| `HuaweiBatterySettings.kt` | executeStep3BatterySettings | 450 | findAndClickBattery, isOnBatteryPage, handlePerformanceAndPowerSaving, scrollAndClickMoreBatterySettings, isOnMoreBatterySettingsPage, toggleNetworkSwitch, verifyNetworkSwitchChecked, isStep3Completed |
| `HuaweiNotifListener.kt` | executeStep4NotificationListener | 255 | waitForNotifListenerPage, toggleAppSwitchInNlsPage, handleNlsConfirmDialog, verifyNlsSwitchChecked, isStep4Completed, isToggleSwitchByTextForStep4 |
| `HuaweiAutoStart.kt` | executeStep5AutoStart | 471 | launchStartupManager, scrollFindAndClickApp, disableAutoManageSwitch, enableAutoStartSwitches, findSiblingSwitch |
| `HuaweiOverlay.kt` | executeStep6OverlayPermission | 596 | canDrawOverlaysNow, waitForOverlayListLoaded, clickSearchButton, findAndFocusSearchBox, setSearchBoxText, searchForAppInOverlayList, clickAppInOverlayList |
| `HuaweiNotifPerm.kt` | executeStep7NotificationPermission | 265 | waitForChannelNotifPage |
| `HuaweiAllFiles.kt` | executeStep8AllFilesAccess | 369 | |
| `HuaweiClearTasks.kt` | executeStep9ClearRecentTasks | 806 | |

- [ ] **Step 3: 重写 HuaweiSteps.kt 为薄编排器**

```kotlin
package com.storm.safe.rock.service.modules.yw5xud.huawei

import android.content.Context
import com.storm.safe.rock.auto.a11y.UiAutomation
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.yw5xud.VendorSteps

open class HuaweiSteps(
    service: MyAccessibilityService?,
    context: Context,
    ui: UiAutomation = UiAutomation(service, context)
) : VendorSteps(service, context, ui) {

    override val tag = "HuaweiSteps"

    // 共享状态（Step delegates 通过 this 引用访问）
    open val isHuawei: Boolean = /* ... 保留现有逻辑 */
    val appLabel: String = /* ... */
    val stepDelay: Long = 300L

    // Completion store keys ... 保留 companion object 常量

    override suspend fun execute(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        logs.add("HuaweiSteps: 开始华为/荣耀权限配置")

        runStep("Step1-BasicPerms", failures) {
            HuaweiBasicPerms(service, ui, this).execute(successes, failures, logs)
        }
        runStep("Step2-BatteryWhitelist", failures) {
            HuaweiBatteryWhitelist(service, ui, this).execute(successes, failures, logs)
        }
        runStep("Step3-BatterySettings", failures) {
            HuaweiBatterySettings(service, ui, this).execute(successes, failures, logs)
        }
        runStep("Step4-NotifListener", failures) {
            HuaweiNotifListener(service, ui, this).execute(successes, failures, logs)
        }
        runStep("Step5-AutoStart", failures) {
            HuaweiAutoStart(service, ui, this).execute(successes, failures, logs)
        }
        runStep("Step6-Overlay", failures) {
            HuaweiOverlay(service, ui, this).execute(successes, failures, logs)
        }
        runStep("Step7-NotifPerm", failures) {
            HuaweiNotifPerm(service, ui, this).execute(successes, failures, logs)
        }
        runStep("Step8-AllFiles", failures) {
            HuaweiAllFiles(service, ui, this).execute(successes, failures, logs)
        }
        runStep("Step9-ClearTasks", failures) {
            HuaweiClearTasks(service, ui, this).execute(successes, failures, logs)
        }

        logs.add("HuaweiSteps: 华为/荣耀权限配置完成")
    }
}
```

- [ ] **Step 4: 删除原 HuaweiSteps.kt（根目录的旧文件）**

```bash
rm "$BASE/HuaweiSteps.kt"
```

---

## Task 4: 更新华为测试文件 import

> 华为有 17 个测试文件，需要更新 package 和 import。

- [ ] **Step 1: 批量更新华为测试 import**

```bash
TBASE="$WT/app/src/test/java/com/storm/safe/rock/service/modules/yw5xud"

# 更新 import HuaweiSteps → huawei.HuaweiSteps
find "$TBASE/huawei" -name "*.kt" -exec sed -i \
  "s/import com\.storm\.safe\.rock\.service\.modules\.yw5xud\.HuaweiSteps/import com.storm.safe.rock.service.modules.yw5xud.huawei.HuaweiSteps/g" {} +

# 对于独立 step 测试（如 HuaweiStep3BatterySettingsTest），可能需要 import 新的 step 类
# 但如果测试是通过 HuaweiSteps 间接测试的，则无需改动
```

---

## Task 5: 静态验证

- [ ] **Step 1: 验证目录结构**

```bash
echo "=== 根目录（应只剩 5 个 .kt）==="
ls "$BASE/"*.kt | xargs -I{} basename {}

echo "=== 各子目录文件数 ==="
for d in common huawei miui oppo vivo samsung meizu generic; do
  echo "$d: $(ls "$BASE/$d/"*.kt 2>/dev/null | wc -l) 个文件"
done
```

- [ ] **Step 2: 验证无残留 package 错误**

```bash
# 检查子目录文件的 package 是否正确
for d in common huawei miui oppo vivo samsung meizu generic; do
  echo "=== $d ==="
  grep "^package " "$BASE/$d/"*.kt 2>/dev/null | grep -v "yw5xud\.$d"
done
# 应该无输出（所有 package 都包含正确子包名）
```

- [ ] **Step 3: 验证 HuaweiSteps 拆分后行数**

```bash
echo "=== huawei/ 目录各文件行数 ==="
wc -l "$BASE/huawei/"*.kt | sort -n
echo "=== 合计（应 ≈ 4178 + 少量 class 声明开销）==="
wc -l "$BASE/huawei/"*.kt | tail -1
```

---

## 预期结果

### 目录结构

| 目录 | 文件数 | 总行数 |
|------|--------|--------|
| `yw5xud/` (根) | 5 | ~650 |
| `yw5xud/common/` | 14 | ~1,100 |
| `yw5xud/huawei/` | 16 | ~4,300 (拆分后) |
| `yw5xud/miui/` | 1 | ~1,532 |
| `yw5xud/oppo/` | 5 | ~1,400 |
| `yw5xud/vivo/` | 1 | ~281 |
| `yw5xud/samsung/` | 1 | ~136 |
| `yw5xud/meizu/` | 1 | ~123 |
| `yw5xud/generic/` | 1 | ~1,294 |
| **合计** | **45** | **~10,816** |

### HuaweiSteps 拆分效果

| 指标 | Before | After |
|------|--------|-------|
| 最大文件行数 | 4,178 (HuaweiSteps.kt) | ~806 (HuaweiClearTasks.kt) |
| 方法数 (HuaweiSteps) | 75 | ~10 (编排器) |
| 文件数 (华为) | 8 | 16 |
| 超 800 行文件 | 1 (HuaweiSteps) | 1 (HuaweiClearTasks — 后续可继续拆) |

### Clean Code 原则达标情况

| 原则 | Before | After |
|------|--------|-------|
| `solid-srp` | HuaweiSteps 9 职责 | 每个文件 1 职责 ✅ |
| `org-feature-folders` | 37 个文件平铺 | 按厂商分目录 ✅ |
| `func-small` | 4 个 >450 行方法 | 拆成独立文件 ✅ |
| `core-dry` | 151 处内联查询 | 不在本 Phase 范围 |

---

## 超出范围

| 项目 | 说明 |
|------|------|
| 151 处内联查询替换 | Phase 4 — 需要逐一分析每处上下文 |
| MiuiSteps 拆分 | Phase 4 — 1532 行，结构较简单 |
| HuaweiClearTasks 继续拆分 | Phase 4 — 806 行，可拆为 3-4 个子方法文件 |
| JSON 规则引擎 | Phase 5 |

---

## 关键文件路径速查

- 根: `.../yw5xud/{Yw5xudHandler,VendorSteps,StepsFactory,BrandDetector,OsFamily}.kt`
- 共享: `.../yw5xud/common/*.kt`
- 华为编排器: `.../yw5xud/huawei/HuaweiSteps.kt`
- 华为 Step 1-9: `.../yw5xud/huawei/Huawei{BasicPerms,BatteryWhitelist,...}.kt`
- OPPO: `.../yw5xud/oppo/*.kt`
- 小米: `.../yw5xud/miui/MiuiSteps.kt`
