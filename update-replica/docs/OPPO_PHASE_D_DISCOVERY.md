# Phase D.1 Discovery Summary (OPPO PGFM10 Android 16 / ColorOS 16)

设备: OPPO PGFM10 (Android 16, API 36, ColorOS 16)
包名: dev.deltalab2964.swift (4.6.4)
日期: 2026-04-18

---

## 总览

- executeAll 跑完: **是** (完成: 1, 失败: 7，30s 内完成)
- dump 文件数: **7** (从 executeAll 运行期间捕获)
- 焦点切换次数: **7**
- logcat Step 1-9 日志是否完整: **否** (只有 Step1/9 enter 一行)

### executeAll 实际焦点时间线

| 时间    | Focus Window | 说明 |
|---------|-------------|------|
| 12:00:51 | Launcher | 初始状态 |
| 12:01:05 | (Step1 enter) | OppoSteps.executeStep1BasicPermissions 开始 |
| 12:01:06 | (onUnbind) | 无障碍服务因 umrkmgrri 触发权限弹窗而 unbind |
| 12:01:07 | GrantPermissionsActivity | NOTIFICATION 权限弹窗出现 |
| 12:01:07 | (rebind) | 无障碍服务重新绑定 |
| 12:01:18 | Settings.Settings | Settings 主页 (Step3 openSettings 路径) |
| 12:01:22 | oplus.battery/PowerControlActivity | Battery 耗电管理页 (Step2) |
| 12:01:26 | Settings$AppManageExternalStorageActivity | 所有文件访问 (Step6) |
| 12:01:27 | oplus.notificationmanager/ChannelNotificationSettingsActivity | 通知关闭 (Step7) |
| 12:01:32 | Launcher | executeAll 完成 |

---

## Step 1 发现 (基础运行时权限)

- **umrkmgrri 是否成功启动:** 是 (logcat: iuzxujjtqev已启动)
- **GrantPermissionsActivity 是否出现:** 是 (d_120107_2.xml)
- **核心问题:** umrkmgrri 启动后立即触发 onUnbind (ColorOS 16 在收到权限请求时解除无障碍绑定)
- **执行时长:** ~1s 后 onUnbind，executeStep1 协程被中断
- **实际授权数:** 0/14 (无障碍中断，click 循环未执行)

### GrantPermissionsActivity UI 结构 (dump d_120107_2.xml)

```
Package: com.android.permissioncontroller
Activity: GrantPermissionsActivity

text='允许"系统服务"向你发送通知?'  id=com.android.permissioncontroller:id/permission_message
text='通知提醒可能包括锁屏通知...'    id=com.android.permissioncontroller:id/detail_message
text='允许'   id=com.android.permissioncontroller:id/permission_allow_button   clk=true bounds=[140,2241][1100,2395]
text='不允许' id=com.android.permissioncontroller:id/permission_deny_button    clk=true bounds=[140,2422][1100,2576]
```

**关键:** 只显示 NOTIFICATION 弹窗，其余 13 个权限未请求
**原因:** onUnbind 在 request 过程中中断了 umrkmgrri Activity
**与华为差异:** 华为会等待 umrkmgrri 完成再 onUnbind

---

## Step 3 发现 (自启动/后台)

- **SafeCenter 包名 (ColorOS 16):** `com.oplus.safecenter` (已安装)
- **com.coloros.safecenter 是否存在:** 否 (pm list packages 无此包)
- **com.oppo.safe 是否存在:** 否
- **当前 5 个 ComponentName 全部失败原因:**
  - `com.coloros.safecenter/.startupapp.StartupAppListActivity` → 包不存在
  - `com.oppo.safe/.permission.startup.StartupAppListActivity` → 包不存在
  - `com.coloros.safecenter/.permission.startup.StartupAppListActivity` → 包不存在
  - `com.coloros.safecenter/.startupapp.view.StartupAppListActivity` → 包不存在
  - `com.oplus.safecenter/.permission.startup.StartupAppListActivity` → Activity 不存在
  
- **com.oplus.safecenter 实际 Activity 列表:** 只有 privacy/stealth 类 Activity，无 startup 相关
- **ColorOS 16 自启动管理真实位置:** 已移入 `com.android.settings` (Settings 主页 → 应用)
- **SDK>=35 路径:** `openSettings() → clickTextWithScroll("应用") → navigateByHashPath("自启动#自启动管理")`

### 从 executeAll 捕获的 Settings 主页 (d_120118_3.xml)
- Settings 进入后显示 WLAN/蓝牙/移动网络等，"应用" 在下方需滚动
- 捕获时机: Step3 tryOpenAutoStartViaSettings 执行时
- 结论: Step3 Settings 路径已尝试但失败（可能 clickTextWithScroll("应用") 未找到）

---

## Step 2 发现 (电池优化 - 已成功)

### PowerControlActivity UI 结构 (d_120122_4.xml)

```
Package: com.oplus.battery
Activity: PowerControlActivity (com.oplus.powermanager.fuelgaue.PowerControlActivity)
Toolbar: text='系统服务'

RecyclerView 内容:
  text='耗电行为控制' id=android:id/title
  text='完全允许后台行为' id=android:id/title  → RadioButton id=com.oplus.battery:id/coui_tail_mark (chk=false)
  text='智能优化后台运行（推荐）' id=android:id/title → RadioButton id=com.oplus.battery:id/coui_tail_mark (chk=true) ← 当前选中
  text='限制后台运行' id=android:id/title → RadioButton id=com.oplus.battery:id/coui_tail_mark (chk=false)
```

**当前状态:** "智能优化后台运行" 已选中 (而非"完全允许后台行为")
**注:** Step2 已被 mark (battery=true)，但实际选项选择失败
**RadioButton 用 coui_tail_mark 但 clickable=false** — 点击需要通过父节点

---

## Step 4 发现 (悬浮窗)

- **ACTION_MANAGE_OVERLAY_PERMISSION 在 ColorOS 16 的行为:**
  - 无 data URI: 打开 OverlaySettingsActivity (应用列表)
  - 带 data URI "package:xxx": 被重定向到 **AppWriteSettingsActivity** (修改系统设置) 而非 AppDrawOverlaySettingsActivity
- **AppWriteSettingsActivity UI:**
  ```
  text='修改系统设置' (title)
  text='允许修改系统设置' id=android:id/title
  text='关闭' id=android:id/switch_widget (Switch)
  ```
- **AppDrawOverlaySettingsActivity intent** 同样被重定向到 AppWriteSettingsActivity
- **结论:** ColorOS 16 (SDK 36) 把 Overlay 权限入口合并/重命名，`ACTION_MANAGE_OVERLAY_PERMISSION` 不再跳转到 overlay 页面，而是 write_settings 页面
- **实际 overlay 页面:** 需要通过 AppSpecialAccess 路径或应用详情→权限管理→特殊权限 导航

### 注: 执行期间没有捕获到 Overlay dump
- executeAll 运行时 Step4 没有产生焦点变化 (可能 canDrawOverlays 已 true，或页面跳转失败)

---

## Step 5 发现 (应用列表)

- SDK=36 → 代码执行路径: openAppDetails() + clickText("读取已安装应用列表")
- 执行期间无焦点切换捕获
- **推测:** SDK 36 上 QUERY_ALL_PACKAGES 已在 manifest 授予 (granted=true)，但 Step5 代码未检测
- 当前代码只对 SDK<31 自动 mark

---

## Step 6 发现 (所有文件访问)

### AppManageExternalStorageActivity UI 结构 (overlay_perm.xml dump)

```
Package: com.android.settings
Activity: Settings$AppManageExternalStorageActivity
Title: '所有文件访问权限'

text='系统服务'   id=com.android.settings:id/entity_header_title
text='4.6.4'    id=com.android.settings:id/entity_header_summary
text='授予管理所有文件的权限' id=android:id/title
text='关闭'      id=android:id/switch_widget (Switch, chk=false, enabled=true) ← 需要点击
text='同意此授权意味着...' id=android:id/summary
```

**开关状态:** 关闭 (需要点击开启)
**Switch ID:** `android:id/switch_widget`
**Switch Text:** `'关闭'` (开启后变 '开启'?)
**Path:** Intent ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION 可正确打开
**执行时 focus 变化:** 12:01:26 出现，说明 Step6 成功打开了页面，但开关未点中

### 失败原因分析:
- `autoToggleAllFilesAccess()` 查找 Switch 的逻辑可能找不到 `android:id/switch_widget`
- 需要确认 autoToggleAllFilesAccess 的 ID 列表是否包含 `android:id/switch_widget`

---

## Step 7 发现 (关闭通知)

### ChannelNotificationSettingsActivity UI 结构 (d_120130_6.xml)

```
Package: com.oplus.notificationmanager
Activity: ChannelNotificationSettingsActivity
Title: 'OFF' (系统服务的通知标签显示为 'OFF')

text='允许通知' id=android:id/title
text='关闭'    id=android:id/switch_widget (Switch, chk=false) ← 已经关闭!
```

**注意:** 通知开关已经是关闭状态！('关闭')
**可能:** Step7 之前的步骤已经关掉了通知（或者这是 Step1 GrantPermissions 中点击"不允许"的结果）
**Step7 依赖:** 代码说依赖 Step1，Step1 失败则 Step7 可能跳过

---

## Step 8 发现 (最近任务锁定)

- 执行期间无焦点变化捕获 (Step8 可能未执行或快速失败)
- uiautomator dump 无法在事后获取 RecentTasks UI
- 需要额外 discovery run

---

## 核心发现汇总

### uiautomator dump 限制 (CRITICAL)
ColorOS 16 上 uiautomator dump 只能抓到 foreground focus 的 window。
Settings 在后台时无法 dump。只有在 executeAll 运行时通过焦点变化捕获才有效。

### 关键 Resource-IDs

| Step | Page | Key ID | Switch/Button |
|------|------|--------|---------------|
| Step 1 | GrantPermissionsActivity | `permission_allow_button` | clk=true |
| Step 2 | PowerControlActivity | `coui_tail_mark` (RadioButton) | clickable=false! |
| Step 6 | AppManageExternalStorageActivity | `android:id/switch_widget` | Switch text='关闭' |
| Step 7 | ChannelNotificationSettingsActivity | `android:id/switch_widget` | Switch text='关闭' (已关) |

### Step3 SafeCenter ComponentName 真相
所有 `com.coloros.safecenter` 包在 ColorOS 16 上均不存在。
唯一存在的包是 `com.oplus.safecenter`，但其内部无 startup 相关 Activity。
自启动功能已迁移到 `com.android.settings` 的 Settings 路径。

---

## 推荐修复方向 (给 Phase D.2)

### Step 1: umrkmgrri 被 onUnbind 中断
**原因:** ColorOS 16 在 umrkmgrri 请求权限时，系统解除无障碍绑定
**修复方向:**
1. 检测 onUnbind 后 executeAll 是否需要重新触发 Step1
2. 或者：在 umrkmgrri finish 之后（通过广播/SP 通知）再继续 executeAll
3. 或者：直接跳过 Step1（权限请求已弹出，用户手动允许后 executeAll 继续）

### Step 2: Battery RadioButton 点击
**原因:** `coui_tail_mark` RadioButton 的 clickable=false，需要点击父节点
**修复方向:**
- clickText("完全允许后台行为") 应该触发父节点点击
- 如果 openSwitch() 使用的是 Switch class 查找，需要改为查找 RadioButton 或文本点击

### Step 3: 自启动
**原因:** SafeCenter ComponentName 全部失效；Settings 路径中 "应用" clickText 可能需要滚动
**修复方向:**
1. 移除所有 `com.coloros.safecenter` 和 `com.oppo.safe` ComponentName（包不存在）
2. 将唯一的 `com.oplus.safecenter` ComponentName 也移除（无 startup Activity）
3. 只保留 SDK>=35 的 Settings 路径：`openSettings() → 滚动找"应用" → "自启动管理"`
4. 验证 "自启动管理" 文本是否正确

### Step 4: Overlay 权限
**原因:** `ACTION_MANAGE_OVERLAY_PERMISSION + data URI` 在 ColorOS 16 打开 WRITE_SETTINGS 而非 OVERLAY
**修复方向:**
1. 先用 `canDrawOverlays()` 检测，ColorOS 16 SDK36 可能已默认授予
2. 如未授予：改用 `ACTION_MANAGE_OVERLAY_PERMISSION`（不带 data URI）→ 在列表中找 app
3. 或通过 Settings → 应用详情 → 权限管理 → 特殊权限 → 显示在其他应用上层 导航

### Step 5: AppList (QUERY_ALL_PACKAGES)
**原因:** SDK=36，QUERY_ALL_PACKAGES 已在 manifest 授予（不需要运行时请求）
**修复方向:** 在 executeStep5AppList 中添加 SDK>=31 时检查 `checkSelfPermission` 或直接 mark

### Step 6: 所有文件访问
**原因:** autoToggleAllFilesAccess 找不到 `android:id/switch_widget`
**修复方向:**
- 确认 autoToggleAllFilesAccess 的 ID 列表包含 `android:id/switch_widget`
- 或使用 `openSwitch("授予管理所有文件的权限")` 文本方式
- Switch text = '关闭' (未开启状态)

### Step 7: 通知关闭
- 执行期间通知已经是关闭状态，Step7 可能已成功（关闭=OK）
- 需要确认 Step7 的成功判定逻辑

### Step 8: 最近任务锁定
- 无 dump，需要额外 discovery
- ColorOS 16 多任务 UI 变更待确认

---

## 保留不动的 Step

- **Step 2 电池:** 已 mark，但实际选择可能需修复 RadioButton 点击
- **Step 9 HOME:** performGlobalAction 不依赖 UI
- **Step 7 通知:** 已自动关闭，可能实际已成功（依赖 Step1 完成后状态）

---

## 未解决的盲点

1. **Step 3 Settings 中"应用"路径:** 未能确认 ColorOS 16 Settings 主页的"应用管理"入口文本
2. **Step 4 Overlay 真实页面:** 未成功 dump Overlay 专用页面（被重定向到 WRITE_SETTINGS）
3. **Step 8 最近任务 UI:** 无 dump，需要手动引导用户打开多任务界面补 dump
4. **Step 1 完整权限弹窗序列:** 只看到 NOTIFICATION 弹窗，其他 13 个权限的弹窗 UI 未确认
5. **Step 2 Battery "完全允许后台行为" 点击是否成功:** dump 显示仍是"智能优化"被选中

---

## 附: 设备信息

- Device: OZZL5PLZQOYP4T8T
- Model: OPPO PGFM10
- Android: 16 (SDK 36)
- ColorOS: PGFM10_16.0.3.500(CN01)
- Screen: 1240x2772
