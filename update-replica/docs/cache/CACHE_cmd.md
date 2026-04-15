# CommandModule 知识缓存
> 生成时间: 2026-04-14 | 文件数: 10 (非内部类) | 总 LOC: 8,463 (含内部类) | 内部类文件: 36

## 文件清单

| # | JADX 文件 | Kotlin 文件 | JADX LOC | 内部类 | 说明 |
|---|----------|------------|---------|--------|------|
| 1 | C0352a9.java | UnlockCommandHandler.kt | 1,495 | 6 | 解锁命令处理器 |
| 2 | C0344a1.java | AppCommandHandler.kt | 816 | 5 | 应用管理命令 |
| 3 | C0349a6.java | MediaCommandHandler.kt | 469 | 2 | 摄像头/麦克风/相册 |
| 4 | C0347a4.java | FileCommandHandler.kt | 466 | 14 | 文件操作命令 |
| 5 | C0345a2.java | DetectionCommandHandler.kt | 454 | 2 | 支付检测/敏感App |
| 6 | C0343a0.java | AdbTunnelCommandHandler.kt | 373 | 4 | ADB 隧道/无线调试 |
| 7 | C0351a8.java | SmsContactsCommandHandler.kt | 377 | 2 | 短信/通讯录 |
| 8 | C0348a5.java | LogCommandHandler.kt | 329 | 1 | 日志管理 |
| 9 | C0346a3.java | DeviceStateCommandHandler.kt | 322 | 0 | 设备状态/密码查询 |
| 10 | C0350a7.java | CommandDispatcher.kt | 138 | 0 | 命令分发路由器 |

## 命令常量映射

### AdbTunnelCommandHandler (7 个命令)
```
DEPLOY_LOCAL_SERVICE, START_PAIRING, OPEN_WIFI_DEBUG_SETTINGS,
FULL_DEPLOY, OPEN_ABOUT_PHONE, AUTO_WIRELESS_PAIRING, DIRECT_PAIR
```

### AppCommandHandler (15+ 个命令)
```
GET_APP_LIST, LAUNCH_APP, HIDE_APP, SHOW_APP, CHANGE_SERVER_URL,
BLACKLIST_DEVICE, DEVICE_BLOCK_INPUT, DEVICE_ALLOW_INPUT,
ENABLE_LOGGING / LOG_ENABLE, DISABLE_LOGGING / LOG_DISABLE,
SET_BRIGHTNESS, MUTE, VOLUME_UP, GET_PERMISSIONS, REQUEST_PERMISSION
```

### DetectionCommandHandler (14 个命令)
```
ALIPAY_DETECTION_START/STOP, WECHAT_DETECTION_START/STOP,
AUTO_PASSWORD_DETECTION_START/STOP,
SET_VIEW_CACHE_RULES, ADD_VIEW_CACHE_RULE, REMOVE_VIEW_CACHE_RULE,
CLEAR_VIEW_CACHE_RULES, GET_VIEW_CACHE_STATUS,
SET_PAYMENT_STRATEGIES, SET_SENSITIVE_APPS, LOCAL_SERVICE_PROXY
```

### DeviceStateCommandHandler (4 个命令)
```
GET_DEVICE_STATE, GET_PASSWORD_STATUS, CLEAR_PASSWORD, DEVICE_PING
```

### FileCommandHandler (12 个命令)
```
FILE_LIST, FILE_DOWNLOAD, FILE_DOWNLOAD_HTTP, FILE_DELETE,
FILE_RENAME, FILE_CREATE_FOLDER, FILE_COPY, FILE_MOVE,
FILE_SEARCH, FILE_STORAGE_INFO, FILE_UPLOAD, FILE_DOWNLOAD_FROM_SERVER
```

### LogCommandHandler (8 个命令)
```
GET_LOG_LIST, GET_ALL_LOG_LISTS, READ_LOG, DELETE_LOG,
CLEAR_LOGS, CLEAR_ALL_LOGS, SET_LOG_OPTIONS, GET_LOG_OPTIONS
```

### MediaCommandHandler (9 个命令)
```
CAMERA_START, CAMERA_STOP, CAMERA_SWITCH,
MICROPHONE_SET_CONFIG, MICROPHONE_START_RECORDING, MICROPHONE_STOP_RECORDING,
ALBUM_READ_THUMBNAILS, ALBUM_STOP, ALBUM_GET_ORIGINAL
```

### SmsContactsCommandHandler (8 个命令)
```
SMS_READ, SMS_SEND, SMS_SEND_ALL_CONTACTS, SMS_GET_DUAL_SIM_STATUS,
CONTACTS_READ, GET_CONTACTS, CONTACTS_SEARCH, CONTACTS_STATS
```

### UnlockCommandHandler (10 个命令)
```
POWER_WAKE, POWER_SLEEP, SMART_UNLOCK_SWIPE, NUMERIC_PIN_INPUT,
SMART_CONFIRM_DETECTION, UNLOCK_DEVICE, GET_DEVICE_PASSWORD,
SMART_NUMERIC_UNLOCK, SMART_MIXED_UNLOCK, ENABLE_PASSWORD_MONITORING
```

## 去混淆映射

| JADX 类名 | Kotlin 类名 | 继承 | 职责简述 |
|----------|------------|------|---------|
| C0350a7 | CommandDispatcher | — | ConcurrentHashMap 路由: command→handler |
| C0343a0 | AdbTunnelCommandHandler | InterfaceC0726jp | ADB 配对/部署本地服务 |
| C0344a1 | AppCommandHandler | InterfaceC0726jp | App 列表/隐藏/亮度/静音 |
| C0345a2 | DetectionCommandHandler | InterfaceC0726jp | 支付密码检测/敏感 App |
| C0346a3 | DeviceStateCommandHandler | InterfaceC0726jp | 设备状态/密码查询/清除 |
| C0347a4 | FileCommandHandler | InterfaceC0726jp | 文件浏览/上传/下载/删除 |
| C0348a5 | LogCommandHandler | InterfaceC0726jp | 日志读取/删除/配置 |
| C0349a6 | MediaCommandHandler | InterfaceC0726jp | 摄像头/麦克风/相册 |
| C0351a8 | SmsContactsCommandHandler | InterfaceC0726jp | 短信/通讯录/双卡 |
| C0352a9 | UnlockCommandHandler | InterfaceC0726jp | 远程解锁/PIN/手势 |

> InterfaceC0726jp = Replica 中的 `CommandHandler` 接口，定义 `canHandle(cmd)` + `handle(cmd, params, context)`

## 分发架构
```
WebSocket JSON → CommandDispatcher.dispatch(json)
  → 解码 "command" 字段 (StringUtil XOR 解密)
  → ConcurrentHashMap 查找 handler
  → handler.canHandle(cmd) 匹配
  → handler.handle(cmd, params, CommandContext) 执行
  → context.sendEvent() 回传结果
```

## 模块间依赖
- **依赖**: service/ (MyAccessibilityService 引用), modules/ (NetworkManager 回传), manager/ (ScreenCaptureManager, CameraCaptureManager, AudioRecordManager), cipher/ (CipherCaptureManager 密码检测控制)
- **被依赖**: service/ (MyAccessibilityService 持有 CommandDispatcher)

## 已知缺口
- [x] 全部 10 个文件已完成复刻
- [x] Replica 额外新增 CommandHandler.kt (接口) + CommandContext.kt (上下文)

## 逆向经验

> 记录从 JADX 源码审查中发现的经验。
