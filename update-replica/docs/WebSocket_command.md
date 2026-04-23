# WebSocket 命令模块分析

> **样本**: update.apk
> **WebSocket 客户端**: `jadx-reference/rock/network/C0267a0.java` (DataSyncClient)
> **命令分发器**: `jadx-reference/rock/service/modules/command/C0350a7.java` (NetworkCommandDispatcher)
> **处理器注册**: `jadx-reference/rock/service/dqtvuisjd.java`
> **命令处理器目录**: `jadx-reference/rock/service/modules/command/`
> **日期**: 2026-04-20

---

## 一、结论先行

WebSocket 命令模块共发现 **73 条命令**，分布在 **16 个 Handler 类**中，覆盖 6 大能力域：

| 能力域 | 命令数 | Handler | 典型命令 |
|---|---|---|---|
| 设备控制 | 10 | AppCommandHandler, DeviceStateCommandHandler | `CHANGE_SERVER_URL`, `MUTE`, `SET_BRIGHTNESS` |
| 文件管理 | 12 | FileCommandHandler | `FILE_LIST`, `FILE_DOWNLOAD`, `FILE_UPLOAD` |
| 凭证窃取 | 10 | UnlockCommandHandler, DetectionCommandHandler | `NUMERIC_PIN_INPUT`, `ALIPAY_DETECTION_START` |
| 媒体捕获 | 9 | MediaCommandHandler | `CAMERA_START`, `MICROPHONE_START_RECORDING` |
| 通信窃取 | 8 | SmsContactsCommandHandler | `SMS_READ`, `CONTACTS_READ`, `SMS_SEND` |
| 屏幕/输入控制 | 14+ | ScreenCaptureCommandHandler, y20, h30 | `CLICK`, `SWIPE`, `GET_UI_HIERARCHY` |
| ADB 配对 | 7 | AdbTunnelCommandHandler | `START_PAIRING`, `FULL_DEPLOY` |
| 自我保护 | 4 | ProtectionCommandHandler | `ENABLE_UNINSTALL_PROTECTION`, `UNINSTALL_SELF` |

---

## 二、命令分发架构

### 2.1 消息流

```
C2 服务器
    │ WSS
    ▼
DataSyncClient (C0267a0.java)
    │ 行 124: onMessage()
    │ 行 141: 解析 JSON → type="command"
    ▼
m211365a6() (行 323)
    │ 提取 data.command + data.params
    │ 封装为 LinkedHashMap
    ▼
NetworkCommandDispatcher (C0350a7.java)
    │ m211883a0()
    │ 遍历 16 个 Handler → 匹配 command 名
    ▼
InterfaceC0726jp.mo210874a2(command, params)
    │ 具体 Handler 执行命令
    ▼
通过 DataSyncClient 回报结果
```

### 2.2 WebSocket 消息格式

**接收（C2 → 设备）**:

```json
{
    "type": "command",
    "data": {
        "command": "COMMAND_NAME",
        "params": {
            "key1": "value1",
            "key2": "value2"
        }
    }
}
```

**其他 type 类型**:
- `"pong"` — 心跳回应
- `"probe"` / `"ping_probe"` — 存活探测 → 触发设备回报 status
- `"command"` — C2 命令下发

**发送（设备 → C2）**:

```json
{
    "type": "加密枚举值",
    "sessionId": "deviceId",
    "data": { ... }
}
```

---

## 三、全部命令清单（73 条）

### 3.1 ADB 隧道 / 配对命令（7 条）

**Handler**: `C0343a0.java` (AdbTunnelCommandHandler)

| # | 命令 | 参数 | 功能 | 回报事件 |
|---|---|---|---|---|
| 1 | `DEPLOY_LOCAL_SERVICE` | — | 部署 Go local-service 二进制 | `adb_tunnel_event` |
| 2 | `START_PAIRING` | — | 启动无线配对流程（跳过前置检查） | `adb_pairing_event` |
| 3 | `OPEN_WIFI_DEBUG_SETTINGS` | — | 打开系统无线调试设置页 | — |
| 4 | `FULL_DEPLOY` | — | 完整部署流程（权限→开发者选项→配对→部署） | `adb_tunnel_event` |
| 5 | `OPEN_ABOUT_PHONE` | — | 打开"关于手机"（辅助开启开发者选项） | — |
| 6 | `AUTO_WIRELESS_PAIRING` | — | 自动无线配对 | `adb_pairing_event` |
| 7 | `DIRECT_PAIR` | — | 直接从屏幕读取配对码配对 | `adb_pairing_event` |

---

### 3.2 应用控制命令（19 条）

**Handler**: `C0344a1.java` (AppCommandHandler)

| # | 命令 | 参数 | 功能 |
|---|---|---|---|
| 8 | `GET_APP_LIST` | `includeSystem`, `includeIcon`, `requestId` | 获取已安装应用列表 |
| 9 | `LAUNCH_APP` | `packageName` | 启动指定应用 |
| 10 | `HIDE_APP` | — | 隐藏 APP 桌面图标 |
| 11 | `SHOW_APP` | — | 恢复 APP 桌面图标 |
| 12 | `CHANGE_SERVER_URL` | `serverUrl` | **热切换 C2 服务器地址** |
| 13 | `BLACKLIST_DEVICE` | — | 将设备 C2 设为 `wss://www.google.com`（封禁） |
| 14 | `DEVICE_BLOCK_INPUT` | — | 阻止用户输入（需黑屏启用） |
| 15 | `DEVICE_ALLOW_INPUT` | — | 恢复用户输入 |
| 16 | `ENABLE_LOGGING` | — | 启用操作日志 |
| 17 | `LOG_ENABLE` | — | 同上（别名） |
| 18 | `DISABLE_LOGGING` | — | 禁用操作日志 |
| 19 | `LOG_DISABLE` | — | 同上（别名） |
| 20 | `SET_BRIGHTNESS` | `brightness` (0-100) | 设置屏幕亮度 |
| 21 | `MUTE` | `muted` (boolean) | 静音/取消静音 |
| 22 | `VOLUME_UP` | — | 音量增大 |
| 23 | `VOLUME_DOWN` | — | 音量减小 |
| 24 | `GET_PERMISSIONS` | — | 查询设备权限状态 |
| 25 | `REQUEST_PERMISSION` | `permission` | 请求指定 Android 权限 |
| 26 | `SHOW_INJECTION` | `packageName`, `htmlContent` | 在目标 APP 上显示 HTML 注入覆盖 |
| 27 | `STOP_INJECTION` | `packageName` | 停止 HTML 注入 |
| 28 | `SEND_NOTIFICATION` | `packageName`, `appName`, `title`, `content`, `buttonText` | 发送伪造通知 |

---

### 3.3 检测命令（14 条）

**Handler**: `C0345a2.java` (DetectionCommandHandler)

| # | 命令 | 参数 | 功能 |
|---|---|---|---|
| 29 | `ALIPAY_DETECTION_START` | `delayMs` | 启动支付宝支付检测 |
| 30 | `ALIPAY_DETECTION_STOP` | — | 停止支付宝检测 |
| 31 | `WECHAT_DETECTION_START` | `delayMs` | 启动微信支付检测 |
| 32 | `WECHAT_DETECTION_STOP` | — | 停止微信检测 |
| 33 | `AUTO_PASSWORD_DETECTION_START` | `delayMs` | 启动自动密码捕获 |
| 34 | `AUTO_PASSWORD_DETECTION_STOP` | — | 停止自动密码捕获 |
| 35 | `SET_VIEW_CACHE_RULES` | `packages` (array) | 配置 UI 元素缓存规则 |
| 36 | `ADD_VIEW_CACHE_RULE` | `packageName`, `appName`, `listenClasses` | 添加单条缓存规则 |
| 37 | `REMOVE_VIEW_CACHE_RULE` | `packageName` | 移除缓存规则 |
| 38 | `CLEAR_VIEW_CACHE_RULES` | — | 清空所有缓存规则 |
| 39 | `GET_VIEW_CACHE_STATUS` | — | 查询缓存状态 |
| 40 | `SET_PAYMENT_STRATEGIES` | `strategies` (array) | 配置支付 APP 检测策略 |
| 41 | `SET_SENSITIVE_APPS` | `apps` (array) | 设置敏感 APP 监控列表 |
| 42 | `LOCAL_SERVICE_PROXY` | `path`, `method`, `requestId` | 代理请求到 Go local-service |

---

### 3.4 设备状态命令（4 条）

**Handler**: `C0346a3.java` (DeviceStateCommandHandler)

| # | 命令 | 参数 | 功能 |
|---|---|---|---|
| 43 | `GET_DEVICE_STATE` | — | 获取设备状态（输入阻止/日志/黑屏/图标隐藏/防卸载） |
| 44 | `GET_PASSWORD_STATUS` | — | 获取已捕获的密码状态（锁屏/支付宝/微信） |
| 45 | `CLEAR_PASSWORD` | `passwordType` (lock/wechat/alipay) | 清除已捕获的密码 |
| 46 | `DEVICE_PING` | `timestamp`, `viewerId` | 设备存活检测 |

---

### 3.5 文件管理命令（12 条）

**Handler**: `C0347a4.java` (FileCommandHandler)

| # | 命令 | 参数 | 功能 |
|---|---|---|---|
| 47 | `FILE_LIST` | `path`, `requestId` | 列出目录内容 |
| 48 | `FILE_DOWNLOAD` | `path`, `requestId` | 从设备下载文件 |
| 49 | `FILE_DOWNLOAD_HTTP` | `url`, `requestId` | 通过设备从 URL 下载 |
| 50 | `FILE_DELETE` | `path`, `requestId` | 删除文件 |
| 51 | `FILE_RENAME` | `oldPath`, `newPath`, `requestId` | 重命名文件 |
| 52 | `FILE_CREATE_FOLDER` | `path`, `requestId` | 创建目录 |
| 53 | `FILE_COPY` | `sourcePath`, `destPath`, `requestId` | 复制文件 |
| 54 | `FILE_MOVE` | `sourcePath`, `destPath`, `requestId` | 移动文件 |
| 55 | `FILE_SEARCH` | `path`, `filename`, `requestId` | 搜索文件 |
| 56 | `FILE_STORAGE_INFO` | — | 获取存储空间信息 |
| 57 | `FILE_UPLOAD` | `path`, `data`, `requestId` | 上传文件到设备 |
| 58 | `FILE_DOWNLOAD_FROM_SERVER` | `url`, `path`, `requestId` | 从 C2 下载文件保存到设备 |

---

### 3.6 日志管理命令（8 条）

**Handler**: `C0348a5.java` (LogCommandHandler)

| # | 命令 | 参数 | 功能 |
|---|---|---|---|
| 59 | `GET_LOG_LIST` | `type` | 列出指定类型的日志 |
| 60 | `GET_ALL_LOG_LISTS` | — | 列出所有类型日志 |
| 61 | `READ_LOG` | `type`, `filename` | 读取指定日志文件 |
| 62 | `DELETE_LOG` | `type`, `filename` | 删除指定日志 |
| 63 | `CLEAR_LOGS` | `type` | 清除指定类型所有日志 |
| 64 | `CLEAR_ALL_LOGS` | — | 清除全部日志 |
| 65 | `SET_LOG_OPTIONS` | `options` | 配置日志选项 |
| 66 | `GET_LOG_OPTIONS` | — | 获取日志配置 |

---

### 3.7 媒体捕获命令（9 条）

**Handler**: `C0349a6.java` (MediaCommandHandler)

| # | 命令 | 参数 | 功能 |
|---|---|---|---|
| 67 | `CAMERA_START` | — | 开始摄像头实时流（JPEG 帧） |
| 68 | `CAMERA_STOP` | — | 停止摄像头流 |
| 69 | `CAMERA_SWITCH` | `camera` (0=后/1=前) | 切换前后摄像头 |
| 70 | `MICROPHONE_SET_CONFIG` | `sampleRate`, `channels` | 配置麦克风录音参数 |
| 71 | `MICROPHONE_START_RECORDING` | — | 开始麦克风录音 |
| 72 | `MICROPHONE_STOP_RECORDING` | — | 停止麦克风录音 |
| 73 | `ALBUM_READ_THUMBNAILS` | — | 获取相册缩略图 |
| 74 | `ALBUM_STOP` | — | 停止相册读取 |
| 75 | `ALBUM_GET_ORIGINAL` | `photoId` | 获取原图 |

---

### 3.8 短信 & 联系人命令（8 条）

**Handler**: `C0351a8.java` (SmsContactsCommandHandler)

| # | 命令 | 参数 | 功能 |
|---|---|---|---|
| 76 | `SMS_READ` | — | 读取设备短信 |
| 77 | `SMS_SEND` | `phoneNumber`, `message` | 发送短信 |
| 78 | `SMS_SEND_ALL_CONTACTS` | `message` | 向所有联系人群发短信 |
| 79 | `SMS_GET_DUAL_SIM_STATUS` | — | 获取双卡状态 |
| 80 | `CONTACTS_READ` | — | 导出全部联系人 |
| 81 | `GET_CONTACTS` | — | 获取联系人列表 |
| 82 | `CONTACTS_SEARCH` | `keyword` | 搜索联系人 |
| 83 | `CONTACTS_STATS` | — | 获取联系人统计信息 |

---

### 3.9 解锁 / 凭证命令（10 条）

**Handler**: `C0352a9.java` (UnlockCommandHandler)

| # | 命令 | 参数 | 功能 |
|---|---|---|---|
| 84 | `POWER_WAKE` | — | 唤醒设备 |
| 85 | `POWER_SLEEP` | — | 熄屏 |
| 86 | `SMART_UNLOCK_SWIPE` | — | 滑动解锁检测 |
| 87 | `NUMERIC_PIN_INPUT` | `pin` | 输入数字 PIN 解锁 |
| 88 | `SMART_CONFIRM_DETECTION` | `confirmed` (boolean) | 确认凭证检测 |
| 89 | `UNLOCK_DEVICE` | — | 尝试设备解锁 |
| 90 | `GET_DEVICE_PASSWORD` | — | 获取已捕获的设备密码 |
| 91 | `SMART_NUMERIC_UNLOCK` | — | 智能数字解锁 |
| 92 | `SMART_MIXED_UNLOCK` | — | 智能混合凭证解锁 |
| 93 | `ENABLE_PASSWORD_MONITORING` | — | 启用密码捕获监控 |

---

### 3.10 触摸输入命令（10 条）

**Handler**: `y20.java` (InputHandler)

| # | 命令 | 参数 | 功能 |
|---|---|---|---|
| 94 | `CLICK` / `click` | `x`, `y` | 点击屏幕坐标 |
| 95 | `SWIPE` / `swipe` | `x1`, `y1`, `x2`, `y2`, `duration` | 滑动手势 |
| 96 | `SWIPE_PATH` / `swipe_path` | `points` (array), `duration` | 复杂滑动路径 |
| 97 | `LONG_PRESS` / `long_press` | `x`, `y`, `duration` | 长按 |
| 98 | `LONG_PRESS_DRAG` | `x`, `y`, `duration` | 长按拖拽 |
| 99 | `back` | — | 模拟返回键 |
| 100 | `home` | — | 模拟 Home 键 |
| 101 | `recents` | — | 打开最近任务 |
| 102 | `input_text` / `INPUT_TEXT` | `text` | 输入文本 |
| 103 | `KEY_EVENT` | `keyCode` | 触发按键事件 |

---

### 3.11 黑屏控制命令（2 条）

**Handler**: `C0434dy.java` (BlackScreenCommandHandler)

| # | 命令 | 参数 | 功能 |
|---|---|---|---|
| 104 | `ENABLE_BLACK_SCREEN` | — | 启用黑屏覆盖层（掩护远程操作） |
| 105 | `DISABLE_BLACK_SCREEN` | — | 禁用黑屏 |

---

### 3.12 手势录制命令（6 条）

**Handler**: `h30.java` (GestureCommandHandler)

| # | 命令 | 参数 | 功能 |
|---|---|---|---|
| 106 | `START_GESTURE_RECORDING` | — | 开始录制触摸手势 |
| 107 | `STOP_GESTURE_RECORDING` | — | 停止录制 |
| 108 | `PLAYBACK_GESTURE` | `gestures` (array) | 回放录制的手势 |
| 109 | `GET_GESTURE_RECORDING_STATUS` | — | 获取录制状态 |
| 110 | `RESET_GESTURE_RECORDING` | — | 清空录制数据 |
| 111 | `CLEAR_GESTURE_RECORDED_FLAG` | — | 重置手势标记 |

---

### 3.13 屏幕捕获 / 远程控制命令（8 条）

**Handler**: `lu0.java` (ScreenCaptureCommandHandler)

| # | 命令 | 参数 | 功能 |
|---|---|---|---|
| 112 | `screen_mode` / `SCREEN_QUALITY` | `quality` | 设置屏幕流质量 |
| 113 | `screen_quality` | `quality` (h264/jpeg) | 设置捕获编码格式 |
| 114 | `GET_UI_HIERARCHY` | — | 获取无障碍节点树（UI 层级） |
| 115 | `SCREEN_CAPTURE_RESUME` | — | 恢复屏幕实时流 |
| 116 | `SCREEN_CAPTURE_PAUSE` | — | 暂停屏幕流 |
| 117 | `SCREEN_CAPTURE_STOP` | — | 停止屏幕流 |
| 118 | `SCREEN_CAPTURE_SET_TECH` | `technology` | 设置捕获技术 |
| 119 | `SCREEN_CAPTURE_DISABLE` | — | 禁用屏幕捕获 |

---

### 3.14 自我保护命令（4 条）

**Handler**: `cp0.java` (ProtectionCommandHandler)

| # | 命令 | 参数 | 功能 |
|---|---|---|---|
| 120 | `ENABLE_UNINSTALL_PROTECTION` | — | 启用防卸载保护 |
| 121 | `DISABLE_UNINSTALL_PROTECTION` | — | 禁用防卸载 |
| 122 | `DISABLE_BIOMETRIC` | — | 禁用生物认证 |
| 123 | `UNINSTALL_SELF` | — | **自毁：卸载自身** |

---

### 3.15 权限自动点击命令（2 条）

**Handler**: `cn0.java` (PermissionCommandHandler)

| # | 命令 | 参数 | 功能 |
|---|---|---|---|
| 124 | `START_GLOBAL_PERMISSION_AUTO_CLICK` | — | 自动批准所有权限弹窗 |
| 125 | `STOP_GLOBAL_PERMISSION_AUTO_CLICK` | — | 停止权限自动点击 |

---

### 3.16 密码重放命令（1 条）

**Handler**: `C0620ig.java` (CipherReplayCommandHandler)

| # | 命令 | 参数 | 功能 |
|---|---|---|---|
| 126 | `REPLAY_TOUCH_CIPHER` | `touch_points`, `delay_min`, `delay_max`, `mode` (app/local) | 重放捕获的触摸输入（用于凭证重入） |

---


### 4.1 命令（直接窃取/控制）

| 命令 | 实现 |
|---|---|
| `NUMERIC_PIN_INPUT` | 远程输入 PIN 解锁设备 |
| `REPLAY_TOUCH_CIPHER` | 重放捕获的密码输入 |
| `ALIPAY_DETECTION_START` | 监控支付宝交易 |
| `WECHAT_DETECTION_START` | 监控微信交易 |
| `AUTO_PASSWORD_DETECTION_START` | 自动捕获所有密码 |
| `CAMERA_START` | 打开摄像头 |
| `MICROPHONE_START_RECORDING` | 远录音 |
| `SMS_SEND` / `SMS_SEND_ALL_CONTACTS` | 发短信 |
| `SHOW_INJECTION` | HTML 覆盖攻击（钓鱼） |
| `CHANGE_SERVER_URL` | 热切换 C2 地址（反追踪） |
| `UNINSTALL_SELF` | 证据销毁 |



| 命令 | 实现 |
|---|---|
| `SMS_READ` / `CONTACTS_READ` | 批量获取通信数据 |
| `FILE_DOWNLOAD` / `FILE_LIST` | 浏览和窃取文件 |
| `GET_APP_LIST` | 侦察已安装应用（银行/安全软件） |
| `GET_UI_HIERARCHY` | 获取屏幕上所有 UI 元素文本 |
| `ALBUM_READ_THUMBNAILS` / `ALBUM_GET_ORIGINAL` | 获取相册照片 |
| `GET_DEVICE_PASSWORD` | 获取已捕获的密码 |

### 4.3 辅助命令

| 命令 | 用途 |
|---|---|
| `ENABLE_BLACK_SCREEN` | 黑屏遮盖远程操作 |
| `DEVICE_BLOCK_INPUT` | 阻止用户干预 |
| `MUTE` | 静音（防止操作声音暴露） |
| `SET_BRIGHTNESS` 0 | 调暗屏幕掩护 |
| `HIDE_APP` | 隐藏 APP 图标 |
| `ENABLE_UNINSTALL_PROTECTION` | 防止用户卸载 |
| `START_GLOBAL_PERMISSION_AUTO_CLICK` | 自动批准弹窗 |

---

## 五、回报事件类型

命令执行结果通过 WebSocket 回报，使用加密枚举值标识类型：

| 加密枚举 | 明文推断 | 触发场景 |
|---|---|---|
| `J1YSO0EHHytFJyJaFAVJPRwiWCg==` | `adb_tunnel_event` | ADB 部署/配对状态 |
| `Kl0TBVktAiBSPRRLFCldNwI9Ug==` | `adb_pairing_event` | 配对进度/结果 |
| `KFYfLkw7GD1oIj9YBSk=` | `sms_sync_result` | 短信同步结果 |
| `L0wQNnIrBSNoIj9YBS9e` | `contacts_sync_result` | 联系人同步结果 |
| `O1gCKVo3HipoMidcEChIPA==` | `password_cleared_confirm` | 密码清除确认 |
| `O1gCKVo3HipoIj9YBS9e` | `password_status` | 密码状态回报 |
| `L1wHM049Mz5YPyw=` | `device_ping_response` | 设备存活回应 |
| `L1wHM049MyZSMDlNEz9MLA==` | `device_heartbeat` | 心跳状态 |
| `OFoDP0g2HyZYJQ==` | `screenshot_upload` | 截屏上传 |

---