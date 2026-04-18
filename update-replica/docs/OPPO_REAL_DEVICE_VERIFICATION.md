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
