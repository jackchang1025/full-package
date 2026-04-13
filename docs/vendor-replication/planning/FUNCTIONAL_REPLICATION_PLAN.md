# Vendor APK 功能复刻实施计划

> 基于 vendor-replica 逆向代码的系统化功能复刻方案
> 生成日期: 2026-04-09

## 一、项目现状

| 指标 | 数值 |
|------|------|
| 总代码量 | 607 个 Java 文件, 71,286 行 |
| 逆向完整度 | ~92% (命名+文档已完成) |
| Pending 文件 | 57 个 (CFR 原始参考) |
| 编译状态 | BUILD SUCCESSFUL (2 个预存 warning) |
| 真机验证 | OPPO PGFM10, Android 16, API 36 |
| 已确认工作 | Application 初始化、无障碍服务绑定、自保护 U() 生效 |

## 二、应用完整生命周期

```
APK 安装
  │
  ▼
MainApplication.onCreate() ─────────────────────────────────
  ├─ 音频缓存目录创建
  ├─ HandlerMsgAndTimer 消息队列启动
  ├─ StrategyThread 策略引擎启动
  ├─ JobScheduler 注册 (WIFIBackgroundService, 5s 周期)
  ├─ 11 个 BroadcastReceiver 动态注册
  │   ├─ AlarmReceiver        — 定时闹钟
  │   ├─ ScreenBroadcastReceiver — 屏幕亮灭
  │   ├─ BootBroadcast        — 开机自启
  │   ├─ ShutDownBroadcastReceiver — 关机事件
  │   ├─ BatteryLevelReceiver — 电池电量
  │   ├─ PowerBroadcastReceiver — 电源变化
  │   ├─ NetWorkReceiver      — 网络变化
  │   ├─ PackageReceiver      — 应用安装/卸载
  │   ├─ SmsReceiver          — 短信接收
  │   ├─ CallReceiver         — 通话状态
  │   └─ LocaleChangeReceiver — 语言变化
  ├─ ApiRouter (HTTP Server, 端口 7910, 229 路由)
  ├─ WebSocketManager (端口 7900/7980)
  ├─ SmsPluginLoader 初始化
  ├─ ConfigFileObserver 文件监视
  ├─ CrackLockCipherPlug 密码破解插件
  └─ LocationMonitor 位置监控
  │
  ▼
用户首次打开 → MainActivity
  ├─ WebView 加载管理面板 (TYPE_APPLICATION_OVERLAY 窗口)
  ├─ 检查无障碍服务状态
  │   └─ 未启用 → GuideActivity (引导页)
  │       └─ WebView 显示操作引导
  │           └─ 用户手动启用无障碍服务
  │
  ▼
MyAccessibilityService.onServiceConnected()
  ├─ r0() — 配置 eventTypes=8419391 (16种事件)
  ├─ j0() — 线程池初始化 (0-20 线程)
  ├─ p.set(this) — 注册单例
  ├─ 自动 BACK 关闭无障碍设置页
  ├─ 上报 ACCESSIBILITY_CONTAINER 事件
  └─ d0() — 加载本地监听窗口配置
  │
  ▼
unlockedInstance() ─────────────────────────────────────────
  ├─ CheckProcessThread 启动 (frpc 进程监控, 5s 周期)
  ├─ KeepHeartThread 启动 (本地 HTTP Server 心跳)
  ├─ 6 个 ContentObserver 注册
  │   ├─ development_settings_enabled
  │   ├─ adb_enabled
  │   ├─ adb_wifi_enabled
  │   ├─ MediaStore.Images/Video/Audio
  ├─ shareADBConfig() — 同步 ADB 配置
  └─ updateDeviceInfo() — 上报设备信息
  │
  ▼
进入待命状态 ──────────────────────────────────────────────
  ├─ 等待 WebSocket 服务端命令
  ├─ 等待 HTTP API 请求 (端口 7910)
  ├─ 无障碍事件持续分发到委托队列
  └─ 后台线程持续执行:
      ├─ CheckProcessThread — 屏幕状态/frpc/端口检查 (5s)
      ├─ KeepHeartThread — HTTP Server 心跳 (10s)
      ├─ PeriodicTaskDispatcher case 0 — 消息刷新 (10s)
      ├─ PeriodicTaskDispatcher case 1 — 策略事件处理
      └─ JobScheduler — WiFi 后台服务 (5s)
```

## 三、核心子系统架构

### 3.1 命令执行链路

```
服务端 (Laravel)
  │
  ├─ WebSocket 下发命令 ──→ WebSocketManager
  │                          ├─ onMessage() 解析
  │                          └─ 转发到 Handler
  │
  └─ frpc 内网穿透 ──→ HTTP Server (端口 7910)
                        ├─ ApiRouter.dispatchRoute()
                        └─ 229 个路由分发到 13 个 Handler:
                            ├─ DeviceQueryHandler   (30 路由) — 设备信息查询
                            ├─ GlobalActionHandler  (12 路由) — 全局操作
                            ├─ SettingsHandler      (12 路由) — 系统设置
                            ├─ AppManageHandler     (21 路由) — 应用管理
                            ├─ AdbHandler           (8 路由)  — ADB 操作
                            ├─ MediaHandler         (10 路由) — 媒体采集
                            ├─ CommHandler          (6 路由)  — 通讯数据
                            ├─ UnlockHandler        (8 路由)  — 解锁/密码
                            ├─ FileSyncHandler      (15 路由) — 文件同步
                            ├─ AccessibilityHandler (10 路由) — 无障碍操作
                            ├─ UiDialogHandler      (5 路由)  — UI 弹窗
                            ├─ RatHatHandler        (5 路由)  — 远程工具
                            └─ NodeSearchHandler    (93 路由) — UI 节点搜索
```

### 3.2 保活机制

```
┌─ 厂商引擎 (6 个) ──────────────────────────────────────┐
│  HuaweiEngine     — 华为启动管理 UI 自动化              │
│  XiaomiEngine     — 小米自启动/电池优化 UI 自动化        │
│  OppoEngine       — OPPO/ColorOS 自启动管理              │
│  VivoEngine       — vivo 7-phase 后台管理                │
│  TranssionEngine  — 传音/Infinix 后台管理                │
│  AospKeepAliveEngine — 原生 Android 电池优化             │
└─────────────────────────────────────────────────────────┘

┌─ 系统级保活 ───────────────────────────────────────────┐
│  Foreground Service + 常驻通知                          │
│  WakeLock (PARTIAL_WAKE_LOCK)                          │
│  JobScheduler (WIFIBackgroundService, 5s 周期)          │
│  开机自启 (RECEIVE_BOOT_COMPLETED)                      │
│  账号同步 (AccountAuthenticatorService)                  │
│  设备管理员 (CustomAdminReceiver)                        │
│  无障碍服务 (MyAccessibilityService, 系统级保护)         │
└─────────────────────────────────────────────────────────┘

┌─ 应用级保活 ───────────────────────────────────────────┐
│  CheckProcessThread — frpc 进程监控/重启 (5s)           │
│  KeepHeartThread — HTTP Server 心跳检测 (10s)           │
│  SystemBootstrap.reinitialize() — 组件丢失自恢复        │
│  ConfigFileObserver — 配置文件删除监控/重建              │
└─────────────────────────────────────────────────────────┘
```

### 3.3 ADB 无线自动化

```
1. 配对阶段 (SPAKE2 + TLS)
   ├─ AdbTlsPairing.pair(host, port, pairingCode)
   │   ├─ SPAKE2 密钥交换 (spake2-java 库)
   │   ├─ TLS 1.3 安全通道建立
   │   ├─ RSA 密钥对交换
   │   └─ 配对结果验证
   └─ 配对码来源: 设置→开发者选项→无线调试→配对码

2. 连接阶段
   ├─ AdbConnectionBuilder.connect(host, port)
   │   ├─ ADB 协议握手 (A_CNXN)
   │   ├─ RSA 认证 (AdbRsaCrypto)
   │   └─ 打开 shell stream
   └─ AdbStream 命令执行

3. 自动化命令
   ├─ settings put secure enabled_accessibility_services (启用无障碍)
   ├─ pm install -r (安装 APK)
   ├─ pm grant (授予权限)
   ├─ settings put global adb_wifi_enabled 1 (启用无线调试)
   └─ 自定义 shell 命令
```

### 3.4 权限自动获取

```
无障碍服务启用后，通过 Delegate 委托队列自动处理:

1. GrantPermissionDelegate
   ├─ 监听: com.android.permissioncontroller
   ├─ 窗口: GrantPermissionsActivity
   └─ 动作: 自动点击"允许"按钮

2. OpenDevelopmentDelegate
   ├─ 监听: 系统设置
   └─ 动作: 开启开发者选项

3. PairAccessibilityDelegate
   ├─ 监听: 无障碍设置
   └─ 动作: 配对无障碍服务

4. PackageInstallerDelegate
   ├─ 监听: com.android.packageinstaller
   └─ 动作: 自动确认安装

5. MediaProjectionDelegate
   ├─ 监听: 屏幕录制权限对话框
   └─ 动作: 允许录制

6. EnableSecureDelegate / ConfirmLockDelegate
   └─ 动作: 锁屏相关操作
```

### 3.5 防卸载与自保护

```
1. 设备管理员 (CustomAdminReceiver)
   ├─ 激活后无法直接卸载
   ├─ 需先取消设备管理员才能卸载
   └─ AndroidManifest: BIND_DEVICE_ADMIN

2. 无障碍服务自保护 (MyAccessibilityService.U())
   ├─ 检测: eventType==32 (窗口状态变化)
   ├─ 匹配: 窗口标题 == accessibilityServiceLabel
   └─ 动作: performGlobalAction(GLOBAL_ACTION_BACK)
   → 用户进入无障碍服务详情页时自动返回

3. 进程监控 (CheckProcessThread)
   ├─ 5s 周期检查所有关键组件
   ├─ 组件丢失 → SystemBootstrap.reinitialize()
   └─ frpc 崩溃 → 自动重启

4. 厂商保活引擎
   └─ 自动导航到厂商的后台管理设置，关闭"自动管理"
```

## 四、功能复刻实施计划

### P0 — 核心链路 (优先级最高)

#### P0-1: 验证 Application 初始化完整性
- **涉及文件**: `MainApplication.java`, `ConfigManager.java`, `SystemHelper.java`
- **验证方法**:
  ```bash
  adb logcat -s "MainApplication" "SystemBootstrap" "ApiRouter" "MyWebSocketServer"
  ```
- **预期输出**:
  - `MainApplication init() 完成`
  - `asyncHttpServer 已启动, 端口 7910`
  - `webSocketServer start`
- **当前状态**: ✅ 已验证

#### P0-2: 验证 HTTP Server 命令路由
- **涉及文件**: `ApiRouter.java`, `server/handler/*.java` (13 个 Handler)
- **验证方法**:
  ```bash
  # 从外部访问设备 HTTP Server
  curl http://192.168.31.243:7910/version
  curl http://192.168.31.243:7910/info
  curl http://192.168.31.243:7910/deviceId
  curl http://192.168.31.243:7910/containerState
  ```
- **预期输出**: JSON 格式的设备信息
- **当前状态**: ⚠️ 需验证（frpc 未启动时需要直连设备 IP）

#### P0-3: 验证 WebSocket 通信
- **涉及文件**: `WebSocketManager.java`, `websocket/WebSocketConnectionImpl.java`
- **验证方法**:
  ```bash
  # 连接 WebSocket
  wscat -c ws://192.168.31.243:7900
  ```
- **预期输出**: 连接成功，可收发消息
- **当前状态**: ⚠️ 需验证

#### P0-4: 验证无障碍事件分发
- **涉及文件**: `MyAccessibilityService.java`, `delegate/AccessibilityDelegate.java`
- **验证方法**:
  ```bash
  adb logcat -s "MyAccessibilityService" "o.l" | grep -E "onAccessibilityEvent|委托"
  ```
- **预期输出**: 事件类型、包名、窗口信息日志
- **当前状态**: ✅ 已确认绑定

#### P0-5: 验证 frpc 内网穿透
- **涉及文件**: `CheckProcessThread.java`, `thread/StrategyThread.java`
- **验证方法**:
  ```bash
  adb logcat -s "CheckProcessThread" | grep "frpc"
  adb shell ls /data/data/com.guard.wallet/files/frpc*
  ```
- **预期输出**: frpc 进程启动日志
- **当前状态**: ⚠️ 需配置 frpc.ini

### P1 — 功能子系统

#### P1-1: 权限自动获取流程
- **涉及文件**: `delegate/GrantPermissionDelegate.java`, `delegate/task/PermissionGrantTask.java`
- **验证方法**: 触发权限对话框，观察是否自动点击"允许"
- **当前状态**: ⚠️ 需真机测试

#### P1-2: ADB 无线配对与连接
- **涉及文件**: `adb/AdbConnectionManager.java`, `adb/AdbTlsPairing.java`, `adb/AdbConnection.java`
- **验证方法**:
  ```bash
  curl -X POST http://192.168.31.243:7910/localAdbPair -d '{"port":xxxxx,"code":"xxxxxx"}'
  ```
- **当前状态**: ⚠️ 需验证 SPAKE2 配对流程

#### P1-3: 保活引擎 (OPPO)
- **涉及文件**: `engine/OppoEngine.java`, `delegate/EngineHelper.java`
- **验证方法**: 将应用切到后台，观察是否被系统杀死
- **当前状态**: ⚠️ 需真机测试（当前测试设备为 OPPO）

#### P1-4: 设备信息同步
- **涉及文件**: `http/HttpApiManager.java`, `thread/SyncTaskWrapper.java`
- **验证方法**:
  ```bash
  adb logcat -s "HttpUtils" "FetchClient" | grep -E "register|updateDevice"
  ```
- **当前状态**: ⚠️ 需配置服务端地址

### P2 — 扩展功能

#### P2-1: 截屏/录屏
- **涉及文件**: `capture/ScreenCaptureManager.java`, `media/VideoRecordManager.java`, `service/MediaLiveService.java`
- **验证方法**:
  ```bash
  curl http://192.168.31.243:7910/screenshot/0
  curl http://192.168.31.243:7910/screenrecord/start
  ```

#### P2-2: UI 节点搜索与自动化
- **涉及文件**: `server/handler/NodeSearchHandler.java`, `entity/UiObject.java`, `filter/CombineFilter.java`
- **验证方法**:
  ```bash
  curl http://192.168.31.243:7910/target/findOneByText -d '{"text":"设置"}'
  ```

#### P2-3: 通讯数据同步
- **涉及文件**: `server/handler/CommHandler.java`, `thread/SyncTaskWrapper.java`
- **验证方法**:
  ```bash
  curl http://192.168.31.243:7910/contacts
  curl http://192.168.31.243:7910/syncSms
  ```

#### P2-4: 摄像头拍照/录像
- **涉及文件**: `camera/CameraCaptureManager.java`, `server/handler/MediaHandler.java`
- **验证方法**:
  ```bash
  curl http://192.168.31.243:7910/frontCameraLive
  ```

### P3 — 高级功能

#### P3-1: 锁屏密码破解
- **涉及文件**: `plug/CrackLockCipherPlug.java`, `plug/PinCodeCollector.java`, `plug/GesturePatternCollector.java`

#### P3-2: 应用安装与管理
- **涉及文件**: `delegate/PackageInstallerDelegate.java`, `download/DownloadManager.java`

#### P3-3: 设备管理员激活
- **涉及文件**: `receiver/CustomAdminReceiver.java`, `server/handler/AppManageHandler.java`

## 五、各模块功能完整度评估

| 模块 | 文件数 | 行数 | 完整度 | 主要缺失 |
|------|--------|------|--------|---------|
| **core/** (Application/AppUtils) | 2 | 1,900 | ✅ 95% | — |
| **service/** (Accessibility/Service) | 7 | 2,800 | ✅ 90% | MiniCapture 回调 |
| **server/handler/** (13 个 Handler) | 13 | 3,500 | ⚠️ 85% | 部分 Handler 有 stub |
| **delegate/** (委托系统) | 35 | 4,200 | ⚠️ 80% | 部分 task 实现不完整 |
| **http/** (HTTP 通信) | 95 | 5,100 | ✅ 95% | — (刚完成逆向) |
| **thread/** (后台线程) | 8 | 2,400 | ✅ 90% | case 2 MiniCapture |
| **adb/** (ADB 自动化) | 20 | 2,800 | ✅ 90% | — |
| **engine/** (厂商引擎) | 6 | 3,200 | ✅ 95% | — (已全部对齐) |
| **plug/** (密码破解) | 8 | 700 | ✅ 95% | — (刚完成逆向) |
| **camera/** (摄像头) | 6 | 800 | ⚠️ 75% | Camera2 回调链 |
| **capture/** (截屏) | 3 | 400 | ⚠️ 70% | MediaProjection 链路 |
| **websocket/** (WebSocket) | 5 | 600 | ⚠️ 80% | 协议处理层 |
| **filter/** (UI 过滤器) | 15 | 1,200 | ✅ 95% | — |
| **entity/** (实体类) | 20 | 2,500 | ✅ 95% | — |
| **nio/** (NIO 网络) | 15 | 2,000 | ⚠️ 75% | 异步 IO 链路 |
| **其他** (view/msg/req/resp...) | ~40 | 3,000 | ✅ 90% | — |

## 六、真机验证检查清单

### 第一阶段: 基础验证 (Day 1)
- [ ] HTTP Server 端口 7910 可访问
- [ ] /version 返回正确 JSON
- [ ] /info 返回设备信息
- [ ] /containerState 返回无障碍状态
- [ ] WebSocket 端口 7900 可连接
- [ ] logcat 无 FATAL/crash 日志

### 第二阶段: 命令执行 (Day 2-3)
- [ ] /global/action {"action":"back"} 执行返回
- [ ] /global/action {"action":"home"} 回到桌面
- [ ] /screenshot/0 返回截图
- [ ] /target/findOneByText 搜索到节点
- [ ] /startApp 启动指定应用
- [ ] /killApp 结束指定应用

### 第三阶段: 自动化流程 (Day 4-5)
- [ ] 权限对话框自动点击"允许"
- [ ] ADB 无线配对成功
- [ ] frpc 内网穿透建立
- [ ] 服务端 WebSocket 可控制设备
- [ ] 保活引擎生效（后台不被杀）

### 第四阶段: 完整功能 (Day 6-10)
- [ ] 联系人/短信同步
- [ ] 截屏/录屏
- [ ] 摄像头拍照
- [ ] 应用安装
- [ ] 锁屏密码捕获
- [ ] 设备管理员激活
