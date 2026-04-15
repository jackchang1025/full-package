# 审计报告：授权无障碍后的完整执行时序

> 基于 JADX 源码 `dqtvuisjd.java` (10,796 LOC), `C0329b4.java` (219 LOC),
> `obzzniixzpin$startAuthorization$1.java` (385 LOC), `C0327b2.java` (5,653 LOC)

---

## Q1: onServiceConnected 后的精确时序

```
=== JADX 执行时序 (dqtvuisjd.java line 10663-10750) ===

T+0ms:   onServiceConnected()
         ├─ super.onServiceConnected()                          [line 10665]
         ├─ ActivityMonitor.logSystem("无障碍服务已启动连接")      [line 10668]
         │
         ├─ [重装恢复检查] 读取 /data/local/tmp/app_setup_done.json  [line 10673]
         │   如果 setupDone=true 且 authorization_completed=false:
         │     → 写入 authorization_completed=true, device_registered=true, icon_hidden=true
         │     → isReinstallRecovery = true
         │
         ├─ if (isReinstallRecovery):                            [line 10715-10716]
         │   → launch(IO) { C02971 } — 重装恢复协程:
         │     T+3000ms: delay(3000)                             [line 960]
         │     T+3000ms: POST http://127.0.0.1:7912/grantMainApp [line 974]
         │     T+3000ms: launch { enableCamouflageMode }         [line 984]
         │
         ├─ [确保 coroutineScope 存在]                            [line 10718-10724]
         │   如果 scope 已取消 → 重建 CoroutineScope(Main + SupervisorJob)
         │
         ├─ serviceStartTime = currentTimeMillis()               [line 10726]
         ├─ instance = this                                      [line 10727]
         ├─ initServiceConfig() (d5)                             [line 10728]
         │   → flags=16810107 (SDK>=30) 或 123 (SDK<30)
         │   → eventTypes=-1, feedbackType=-1, notificationTimeout=0
         │
         ├─ powerManager = getSystemService("power")             [line 10730-10734]
         ├─ keyguardManager = getSystemService("keyguard")
         │
         ├─ AppCoreService.start(applicationContext)             [line 10738-10741]
         │
         ├─ 取消旧 initJob (f52379b0)                            [line 10742-10744]
         │
         └─ launch(IO) { C02982 } — 主初始化协程:                [line 10746]
             ├─ deferredInit() (a3)                              [line 1020→1672]
             │   ├─ WorkManager.init()                           [line 1690-1694]
             │   ├─ launch(IO) { deferredInit$2 }                [line 1696-1698]
             │   ├─ initializeModules() (h2)                     [line 1712]
             │   └─ InitWorkerService.start()                    [line 1715-1718]
             │
             └─ [隐含] doHeavyInit() (a4) 不在 C02982 中!
                 C02982 只调用 deferredInit (a3)
```

关键发现: `C02982.invokeSuspend()` (line 1012-1033) 只调用 `deferredInit(a3)`，不调用 `doHeavyInit(a4)`。`doHeavyInit` 是在 `initializeService(h3)` 中被调用的，而 `initializeService` 是在 `deferredInit` 内部通过 `initializeModules` 链路触发的。

实际调用链:
```
C02982 → deferredInit(a3) → initializeModules(h2) [完成后]
                          → initializeService(h3) [在 doHeavyInit 内]
                            → startPermissionGrantFlow(m8)
```

具体来说 `doHeavyInit(a4)` (line 1728) 内部:
```
doHeavyInit(a4) line 1728:
  ├─ 读取 authorization_completed
  ├─ 读取 camouflage_enabled
  ├─ if (authorized): 恢复保护功能 (NetworkManager, 日志, 防卸载)
  ├─ initializeService(h3) line 1802                    ← 关键!
  │   ├─ initializeManagers(h1) → initializeModules(h2)
  │   ├─ isInitComplete = true
  │   └─ startPermissionGrantFlow(m8)                   ← 触发权限流程
  └─ [后续] 注册广播等
```

---

## Q2: startPermissionGrantFlow (m8) 的完整逻辑

JADX line 9297-9420, method `m211530m8`

```
startPermissionGrantFlow(m8):
  │
  ├─ 读取 authorization_completed (SharedPreferences)
  │
  ├─ [分支A] authorization_completed = true:              [line 9331-9346]
  │   ├─ authorizationModule.startAuthorization() (a6)    → 检查是否需要重新授权
  │   ├─ if (!isUninstallGuardStarted): enableUninstallProtection()
  │   ├─ recentsGuardManager.enable()
  │   └─ tryShowPackageVerify() (n2)                      → 假卸载页面
  │   └─ return (不进入权限流程)
  │
  ├─ [分支B] SDK >= 30 (Android 11+):                    [line 9347-9386]
  │   ├─ 检查 screenBrightnessManager.isLowered()
  │   ├─ if (!isLowered):
  │   │   ├─ configMaskManager.show(false)                → 显示遮罩
  │   │   └─ configProgressManager.start(CHECKING_PERMISSIONS)
  │   ├─ isPermissionFlowStarted = true
  │   ├─ screenCaptureManager.stopAutoClick() (h2)        → 停止自动点击
  │   ├─ authorizationModule.startAuthorization() (a6)    → 启动授权
  │   └─ log "适配流程继续，网络连接在后台进行"
  │   注意: 没有直接调用 WRITE_SETTINGS!
  │
  └─ [分支C] SDK < 30 (Android 10):                      [line 9387-9420]
      ├─ 检查 screenBrightnessManager.isLowered()
      ├─ if (!isLowered): configMaskManager.show(false)
      ├─ delay(1000ms)                                    → 等遮罩显示
      ├─ isPermissionFlowStarted = true
      ├─ screenCaptureManager.stopAutoClick()
      └─ authorizationModule.startAuthorization() (a6)
```

关键: `startPermissionGrantFlow` 本身不启动 WRITE_SETTINGS。它只启动 `DeviceAuthorizationManager.startAuthorization()`。WRITE_SETTINGS 是在授权流程完成后的 `finally` 块中通过 `resumeWriteSettingsPermissionRequest()` 触发的。

---

## Q3: DeviceAuthorizationManager.startAuthorization 的完整协程体

### C0329b4.m211768a6() (line 187-218) — startAuthorization 入口

```
startAuthorization(a6):
  ├─ if (isRunning): return "已在进行中"
  │
  ├─ 读取 authorization SP:
  │   ├─ authorization_completed
  │   ├─ authorization_device_key
  │   └─ currentDeviceKey = detectBrand()
  │
  ├─ if (completed && deviceKey == currentKey):
  │   → log "授权已完成，直接启动配对流程"
  │   → notifyAuthorizationComplete() (a5)                ← 跳过授权，直接完成
  │   → return
  │
  └─ launch(coroutineScope) { obzzniixzpin$startAuthorization$1 }
```

### obzzniixzpin$startAuthorization$1.invokeSuspend() — 完整协程体

```
startAuthorization 协程:
  try {
    T+0ms:   isRunning = true
    T+0ms:   disableAccessibilityPageDetection() (e3)     [line 128]
             → 关闭无障碍设置页面检测定时器
    
    T+0ms:   检测当前页面包名 (getRootInActiveWindow)      [line 133-149]
    
    T+300ms: delay(300)                                    [line 142]
    
    T+300ms: if (当前包名 == 自己包名):                     [line 330]
             → log "已在app，跳过返回"
             → 跳到 Step 2
    
    T+300ms: else: smartReturnToApp() (m1)                 [line 349-352]
             → 返回 boolean
             → if (!success): delay(300) 后继续             [line 193-194]
    
    --- Step 2: 暂停 WRITE_SETTINGS ---
    T+Xms:   pauseWriteSettingsPermission() (j0)           [line 276]
             → isScreenCaptureActive = true
             → mainOrchestrator.stopPermissionRequest() (f8)
    
    --- Step 3: 执行品牌引擎 ---
    T+Xms:   detectBrand() → 获取品牌处理器
    T+Xms:   if (brandHandler != null):
             → brandHandler.execute() (a1)                 [line 284-291]
             → 返回 AuthorizationResult
    
    --- Step 4: 汇总结果 ---
    T+Xms:   logResult()
    T+Xms:   if (success): markAuthorizationComplete() (a1)
    
  } finally {                                              [line 379-383]
    isRunning = false                                      [line 380]
    notifyAuthorizationComplete() (a5)                     [line 381]
    resumeWriteSettingsPermissionRequest() (a2→k7)         [line 382]
  }
```

关键时序:
1. 先关闭无障碍页面检测
2. 先返回 app (smartReturnToApp)
3. 暂停 WRITE_SETTINGS
4. 执行品牌引擎 (保活设置)
5. finally: 恢复 WRITE_SETTINGS

### notifyAuthorizationComplete (a5) — line 170-183

```
notifyAuthorizationComplete():
  ├─ log "授权流程结束，启动延迟初始化 + 配对流程"
  ├─ 写入 app_state.authorization_completed = true
  ├─ postAuthorizationInit() (j8)                          [line 175]
  │   ├─ launch(Main) { 注册广播接收器 }
  │   └─ launch(IO) { initializeDeferredManagers(b5) }
  └─ Handler.post { 自动部署已禁用 }
```

---

## Q4: smartReturnToApp (m1) 的完整实现

JADX line 8106-8414, method `m211524m1`

```
smartReturnToApp(m1):
  ├─ 获取 brand (lowercase), SDK version
  │
  ├─ [小米特殊处理] detectXiaomiVersion() (e0):
  │   ├─ if "Android 10": → smartReturnToAppForMiAndroid10() (m2)
  │   │   ├─ 检测是否在app → 如果是，直接返回 true
  │   │   ├─ performGlobalAction(BACK), delay(500)
  │   │   ├─ 检测 → 如果在app，返回 true
  │   │   ├─ performGlobalAction(BACK), delay(500)
  │   │   ├─ 检测 → 如果在app，返回 true
  │   │   └─ 启动 iuzxujjtqev(MI_ANDROID10_RETURN), delay(500), 检测
  │   │
  │   ├─ if "Android 13": → smartReturnToAppForMiAndroid13() (m3)
  │   │   ├─ 先启动 iuzxujjtqev(MI_ANDROID13_RETURN), delay(1500)
  │   │   ├─ 检测 → 如果在app，返回 true
  │   │   ├─ 最多 3 次 BACK + delay(1000) 循环
  │   │   └─ 每次检测是否回到 app
  │   │
  │   └─ 其他小米: → smartReturnToAppForMiAndroid10() (m2)
  │
  ├─ [vivo 特殊处理] detectVivoDevice() (e1):
  │   → (有 vivo 特殊设备检测，但代码中未见独立路径)
  │
  └─ [通用路径] (非小米、非 vivo 特殊设备):
      ├─ Step 1: 启动 iuzxujjtqev                         [line 8201-8207]
      │   intent.addFlags(872415232)  // NEW_TASK|CLEAR_TOP|SINGLE_TOP
      │   putExtra("SMART_RETURN_BACKUP", true)
      │   putExtra("FROM_ACCESSIBILITY_SERVICE", true)
      │   startActivity(intent)
      │
      ├─ Step 2: delay(2000)                               [line 8217]
      │
      ├─ Step 3: 检测 isCurrentlyInOurApp() (h7)           [line 8225-8229]
      │   → 如果在 app: return true
      │
      ├─ Step 4: 智能返回循环 (最多 6 次 BACK)             [line 8232-8414]
      │   每次循环:
      │   ├─ 检测 isCurrentlyInOurApp()
      │   ├─ if (在app):
      │   │   ├─ vivo+SDK31: delay(1000) 稳定性验证
      │   │   └─ 其他: delay(500) 稳定性验证
      │   │   ├─ 再次检测 → 如果稳定: return true
      │   │   └─ 不稳定: 继续 BACK
      │   ├─ performGlobalAction(BACK)
      │   ├─ vivo+SDK31: delay(1000)
      │   └─ 其他: delay(500)
      │
      └─ 6 次都失败: return false
```

关键发现:
- JADX 中没有按 HOME! 通用路径是先启动 iuzxujjtqev Activity，然后用 BACK 键逐步返回
- 小米 Android 10 也是用 BACK 键，不是 HOME
- 小米 Android 13 是先启动 Activity，再用 BACK
- vivo + SDK 31 有更长的等待时间 (1000ms vs 500ms)

---

## Q5: WRITE_SETTINGS 的 isOnTargetAppPage (d7) 完整逻辑

JADX C0327b2.java line 4739-4852, method `m211736d7`

```
isCorrectWriteSettingsPage(d7):
  ├─ getRootInActiveWindow()
  │   → null: return false
  │
  ├─ 构建排除列表: [自己包名, "overlay"]
  │
  ├─ 遍历所有节点 (递归 m211698b4):
  │   对每个节点:
  │   ├─ 获取 text + contentDescription → 合并为 lowercase 字符串
  │   │
  │   ├─ [关键词检查] 匹配 dh0.f55771c1 中的关键词:
  │   │   → 包含 "修改系统设置"/"modify system settings" 等
  │   │   → hasKeyword = true
  │   │
  │   └─ [控件检查] isClickable || isCheckable:
  │       → className 包含 "Switch" / "Toggle" / "Button" / "LinearLayout"
  │       → hasControl = true
  │
  ├─ 回收所有节点
  │
  └─ return hasKeyword && hasControl
```

### isOnPermissionPage (d9) — line 4854-4878

```
isOnPermissionPage(d9):
  ├─ getRootInActiveWindow()
  │   → null: return false
  │
  ├─ 获取包名 → 检查 isSettingsPackage(e0)
  │   → 如果是 settings 包名: return true
  │
  ├─ 遍历 dh0.f55771c1 关键词:
  │   → findNodeByText(c4) 查找包含关键词的节点
  │   → 找到: 回收节点, return true
  │
  └─ return false
```

区别:
- `d7` (isCorrectWriteSettingsPage): 检查关键词 AND 可点击控件都存在 → 确认在正确的 WRITE_SETTINGS 页面
- `d9` (isOnPermissionPage): 只检查包名是 settings 或页面包含权限关键词 → 更宽泛的判断

---

## Q6: onAccessibilityEvent 中的 screen capture pause 逻辑

JADX line 9803-9823

```java
// line 9804-9823
C0260a2 c0260a2 = this.f52369a0;  // screenCaptureManager
if ((c0260a2 != null ? c0260a2.f52110a2 : false)  // isCapturing
    && (tu0Var = this.f52430g1) != null) {          // screenCaptureAutoClick
    
    if (accessibilityEvent.getPackageName() == null) {
        return;  // ← 直接 return，不处理任何事件
    }
    String string10 = accessibilityEvent.getPackageName().toString();
    String[] strArr = tu0.f60269a7;  // settings 包名数组
    for (String pkg : strArr) {
        if (string10.contains(pkg)) {
            // 在 settings 页面 + 正在截屏 → 触发自动点击
            if (currentTime - tu0Var.lastClickTime >= 2000 
                && tu0Var.state == 0) {
                tu0Var.handler.post(new qu0(tu0Var, 0));
                return;  // ← 处理完后 return
            }
            return;  // ← 直接 return
        }
    }
    return;  // ← 不在 settings 页面也 return!
}
```

关键发现: 当 `isCapturing=true` 且 `screenCaptureAutoClick != null` 时，整个事件处理直接 return。这意味着:
- MainOrchestrator 不会收到事件 (因为 d4 在后面)
- 品牌引擎不会收到事件
- 卸载保护不会收到事件
- 所有后续处理都被跳过

但是! 这个检查在 `isPermissionRequestActive` 检查之前 (line 9848)。而 MainOrchestrator 的事件分发在 line 10121-10133，远在这个 return 之后。

---

## 与复刻项目的差异表

| # | 步骤 | JADX 行为 | 复刻行为 | 差异 | 影响 |
|---|------|----------|---------|------|------|
| 1 | onServiceConnected 协程结构 | C02982 只调用 `deferredInit(a3)`，`doHeavyInit` 在 `deferredInit` 内部链式调用 | 复刻分开调用 `deferredInit()` 和 `doHeavyInit()` (line 671-681) | 中 | 可能导致初始化顺序不同，`doHeavyInit` 可能在 `deferredInit` 完成前就开始 |
| 2 | smartReturnToApp 策略 | 通用路径: 先启动 iuzxujjtqev Activity，然后最多 6 次 BACK 键 + 稳定性验证。没有 HOME 键 | 复刻: 先按 HOME，再按 BACK，再启动 Activity (line 3326-3331) | 严重 | HOME 键会导致回到桌面而非 app，与 vendor 行为完全不同。vendor 是先启动 Activity 拉回前台，失败后才用 BACK |
| 3 | smartReturnToApp 品牌分支 | 小米 Android 10: 纯 BACK 策略 (m2)。小米 Android 13: 先启动 Activity 再 BACK (m3)。vivo+SDK31: 更长等待 | 复刻: 无品牌分支，统一 HOME+BACK+Activity | 严重 | 小米/vivo 设备上行为完全错误 |
| 4 | smartReturnToApp 稳定性验证 | 每次 BACK 后检测 isInApp，如果在 app 还要再等 500ms/1000ms 验证稳定性 | 复刻: 只验证一次，无稳定性二次确认 | 高 | 可能误判已回到 app，实际页面还在切换中 |
| 5 | 授权协程 finally 块 | finally 中按顺序: (1) isRunning=false (2) notifyAuthorizationComplete (3) resumeWriteSettings | 复刻: DeviceAuthorizationManager.kt line 331-335 正确实现了 finally { inProgress=false; onAuthorizationDone(); resumeWriteSettings() } | 无 | 已正确实现 |
| 6 | 授权协程中暂停 WRITE_SETTINGS | 授权开始后立即调用 `pauseWriteSettingsPermission(j0)` → `mainOrchestrator.stopPermissionRequest(f8)` | 复刻 pauseWriteSettingsPermission (line 3389-3401): 只设置 flag，没有调用 mainOrchestrator.f8() | 高 | WRITE_SETTINGS 可能在保活引擎执行期间继续运行，导致在无障碍页面上误操作 |
| 7 | 授权前关闭无障碍页面检测 | `disableAccessibilityPageDetection(e3)` — 取消定时器，清零计数器 | 复刻: DeviceAuthorizationManager.kt line 238-242 正确调用 service.disableAccessibilitySettingsMonitor() | 无 | 已正确实现 |
| 8 | isCurrentlyInOurApp (h7) | 只比较 `rootInActiveWindow.packageName == getPackageName()` | 复刻一致 (line 3355-3358) | 无 | — |
| 9 | resumeWriteSettingsPermissionRequest 协程 | 800ms delay → 检查 isScreenCaptureActive → 调用 mainOrchestrator.f7() | 复刻一致 (line 3629-3644) | 无 | — |
| 10 | screen capture pause 逻辑 | isCapturing + autoClick != null → 整个事件 return，阻止所有后续处理 | 复刻 line 718-741: 有对应逻辑，但只检查 3 个系统包名 (systemui/settings/packageinstaller)，JADX 用 tu0.f60269a7 数组可能更多 | 低 | 基本一致，可能遗漏少数系统包名 |
| 11 | onAccessibilityEvent 中 MainOrchestrator 分发位置 | JADX: MainOrchestrator 事件分发在 isPermissionRequestActive 检查之后 (line 10121)，即权限请求期间 MainOrchestrator 不收事件 | 复刻: MainOrchestrator 分发在 isPermissionRequestActive 检查之前 (line 766 vs 780) | 高 | 权限请求期间 MainOrchestrator 仍会收到事件，可能导致 WRITE_SETTINGS 自动化在不该运行时运行。与 #6 (pauseWriteSettings 无效) 叠加放大问题 |
| 12 | startPermissionGrantFlow 中 screenCaptureManager.h2() | 调用 `c0260a22.m211329h2()` 停止自动点击 | 复刻: 空的 `screenCaptureManager?.let { scm -> }` (line 2514-2516) | 中 | 自动点击可能未被正确停止 |
| 13 | onServiceConnected 重装恢复路径 | 重装恢复走 C02971 协程 (delay 3s → POST grantMainApp → enableCamouflage)，正常走 C02982 | 复刻: 两个路径都会执行 (line 659-682)，重装恢复后还会执行 deferredInit | 低 | 重装恢复时可能多执行一次初始化 |

---

## 根因分析：真机测试问题

### 问题 1: 页面停留在无障碍设置页

根因: `smartReturnToApp` 实现错误。
- JADX: 先启动 iuzxujjtqev Activity 拉回前台，失败后用 BACK 键（最多 6 次 + 稳定性验证）
- 复刻: 先按 HOME (回到桌面)，再按 BACK (无效)，再启动 Activity
- HOME 键在某些设备上会导致无障碍设置页被压入后台但不关闭
- 缺少品牌分支（小米 Android 10/13 有完全不同的策略）

### 问题 2: 保活引擎在无障碍页面上误操作

根因: 两个问题叠加。
1. `pauseWriteSettingsPermission` (line 3389-3401) 是空操作 — `mainOrchestrator?.let { mo -> }` 只打日志，没有调用 `mo.stopPermissionRequest()`
2. `onAccessibilityEvent` 中 MainOrchestrator 事件分发 (line 766) 在 `isPermissionRequestActive` 检查 (line 780) 之前 — JADX 中是之后 (line 10121 vs 9848)

这意味着:
- 授权流程开始后，WRITE_SETTINGS 自动化没有被暂停
- 即使 isPermissionRequestActive=true，MainOrchestrator 仍然收到事件
- MainOrchestrator 在无障碍设置页面上检测到 settings 包名，触发了自动点击

### 问题 3: WRITE_SETTINGS 页面打开但 Switch 没被点击

可能根因:
- 如果 WRITE_SETTINGS 在授权流程期间被错误触发（问题 2），页面打开时保活引擎正在操作，两者互相干扰
- 或者 MainOrchestrator 的 `isCorrectWriteSettingsPage(d7)` 判断逻辑中关键词匹配不完整
- 需要进一步检查 MainOrchestrator 的 `attemptAutoClick` 实现

---

## 修复优先级

1. (P0) `smartReturnToApp` — 移除 HOME+BACK 策略，改为先启动 iuzxujjtqev Activity + 最多 6 次 BACK 循环 + 稳定性验证（每次 BACK 后 delay 500ms 检测 + 再 delay 500ms 二次确认）
2. (P0) `pauseWriteSettingsPermission` — 在 `mainOrchestrator?.let` 中实际调用 `mo.stopPermissionRequest()`
3. (P0) `onAccessibilityEvent` 事件分发顺序 — 将 MainOrchestrator 分发移到 `isPermissionRequestActive` 检查之后（line 780 之后）
4. (P1) 添加品牌分支 (小米 Android 10: 纯 BACK 策略; 小米 Android 13: 先启动 Activity 再 BACK; vivo+SDK31: 更长等待)
5. (P2) 修复 onServiceConnected 协程结构 — C02982 只调用 deferredInit，doHeavyInit 在内部链式调用
6. (P2) startPermissionGrantFlow 中 screenCaptureManager.h2() 空实现
