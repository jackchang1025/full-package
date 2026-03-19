# MODULE_07 保活机制 — Vendor 行为审计

## 1. 模块职责

进程保活。通过多层机制确保应用在后台持续运行：广播监听（开机/息屏/亮屏/电池/网络）、定时任务（心跳/进程检查/策略执行）、系统服务（JobScheduler/AccountSync/WiFi后台）、frpc 反向代理进程管理。

## 2. Vendor 保活架构

```
4 层保活:
  Layer 1: 广播监听 (receiver/)
    ├── BootBroadcast — 开机自启 → 初始化 MainApplication
    ├── ScreenBroadcastReceiver — 息屏/亮屏/解锁 → 暂停/恢复无障碍
    ├── AlarmReceiver — 定时唤醒
    ├── BatteryLevelReceiver — 电池状态
    ├── PowerBroadcastReceiver — 充电/断电
    ├── ShutDownBroadcastReceiver — 关机事件
    ├── NetWorkReceiver — 网络变化
    └── LocaleChangeReceiver — 语言变化

  Layer 2: 定时线程 (thread/)
    ├── b.java (CheckProcessThread) — frpc 进程监控 + 重启 (10s 间隔)
    ├── f.java (HeartThread) — 心跳上报 + 同步配置 (10s 间隔)
    ├── e.java (HandlerMsgAndTimer) — 消息队列定时发送
    ├── j.java (StrategyThread) — 策略事件处理
    └── c.java (GlobalExceptionHandler) — 全局异常捕获 + 自动重启

  Layer 3: 系统服务
    ├── WIFIBackgroundService — WiFi 后台服务 (JobScheduler 持久化)
    ├── AccountAuthenticatorService — 账号同步服务
    ├── SyncService — ContentProvider 同步
    └── StubProvider — 空 ContentProvider (同步触发器)

  Layer 4: 辅助保活
    ├── KeepAliveJobService — JobScheduler 定时任务
    ├── frpc 反向代理进程 — 独立进程保活
    └── ContentObserver — 监听系统设置变化
```

## 3. 文件映射对比

### thread/ — 保活线程

| Vendor 文件 | 行数 | 功能 | Replica 文件 | 行数 | 差距 |
|------------|------|------|-------------|------|------|
| b.java | 208 | CheckProcess+frpc | CheckProcessThread | 130 | ⚠️ 缺 78 行 |
| f.java | 293 | Heart+Screen+sync | HeartThread(62)+KeepHeartThread(107) | 169 | ⚠️ 缺 124 行 |
| e.java | 147 | Message Timer | MessageQueueManager | 73 | ⚠️ 缺 74 行 |
| j.java | 220 | Strategy+sync | StrategyThread | 90 | ⚠️ 缺 130 行 |
| c.java | 45 | UncaughtException | GlobalExceptionHandler | 52 | ✅ |
| d.java | 33 | TimerTask | ScheduledTimerTask | 39 | ✅ |
| l.java | 108 | abstract Callable | TaskExecutor | 106 | ✅ |
| a.java | 122 | Audio Callable | — | — | ⚠️ 分散 |
| g.java | 54 | Screenshot Callable | ScreenshotCallable | 60 | ✅ |
| h.java | 39 | WiFi Debug Callable | WifiDebugCallable | 32 | ✅ |
| i.java | 327 | Screen Record | ScreenRecordThread | 52 | ❌ 缺 275 行 |
| k.java | 98 | Screenshot | ScreenshotCallable | 60 | ⚠️ |
| m.java | 218 | Audio+Media+Sync | MediaChangeThread(50)+DataSyncThread(63) | 113 | ⚠️ 缺 105 行 |

### service/ — 系统服务

| Vendor 文件 | 行数 | Replica 文件 | 行数 | 状态 |
|------------|------|-------------|------|------|
| WIFIBackgroundService | 63 | keepalive/service/WIFIBackgroundService | 32 | ⚠️ 缺 31 行 |
| AccountAuthenticatorService | 64 | keepalive/service/AccountAuthenticatorService | 49 | ✅ 接近 |

### sync/ — 同步服务

| Vendor 文件 | 行数 | Replica 文件 | 行数 | 状态 |
|------------|------|-------------|------|------|
| StubProvider | 39 | keepalive/sync/StubProvider | 45 | ✅ |
| SyncService | 27 | keepalive/sync/SyncService | 33 | ✅ |

## 4. 核心行为分析

### 4.1 CheckProcessThread (b.java, 208行) — frpc 进程管理

```
构造函数:
  - 初始化忽略事件类型列表 (4194304, 2048, 64, 33554432, 131072, 16777216)
  - 用户交互状态追踪 (AtomicReference<r.d>)

run() (TimerTask, 10s 间隔):
  1. 检查 frpc.ini 是否存在
  2. 如果 frpc 进程未运行 → 启动 frpc
  3. 如果 frpc 进程已运行 → 检查健康状态
  4. 管理 ADB 调试端口重写
  5. 追踪用户交互状态 (BUSY/IDLE)

关键方法:
  - a() — 检查 frpc.ini 文件
  - d() — 获取 APP Lib 目录
  - e() — 重启 frpc 进程
  - g() — 启动定时任务 (Timer, 10s)
```

### 4.2 HeartThread (f.java, 293行) — 心跳 + 配置同步

```
构造函数:
  - 3 个重试策略: 5s/30s/30s
  - Timer 定时器
  - 心跳计数器

run() (TimerTask, 10s 间隔):
  1. 构建 HeartBodyVO (设备状态)
  2. 发送心跳到服务端
  3. 解析响应: 同步配置/监听窗口/缓存任务
  4. 检查 CustomNotificationService 状态
  5. 检查位置监听状态
  6. 同步账号 (AccountManager)
  7. 检查 ADB 连接状态

关键方法:
  - b() — 静态: 同步配置 + 监听窗口 + 通知服务
  - run() — 心跳主循环
```

### 4.3 HandlerMsgAndTimer (e.java, 147行) — 消息队列

```
构造函数:
  - Timer 定时器 (5s 间隔)
  - 消息队列 (ConcurrentLinkedQueue)

run() (TimerTask, 5s 间隔):
  1. 从队列取出 MessageRecordVO
  2. 添加设备信息 (deviceId, timestamp)
  3. 通过 HTTP 发送到服务端
  4. 失败重试

关键方法:
  - b(MessageRecordVO) — 入队消息
```

### 4.4 BootBroadcast (103行) — 开机自启

```
onReceive:
  1. BOOT_COMPLETED → 初始化 MainApplication
  2. LOCKED_BOOT_COMPLETED → 标记未解锁
  3. 检查 UserManager.isUserUnlocked()
  4. 构建 BootEventVO → MessageRecordVO → 消息队列
```

## 5. Vendor vs Replica 关键差距

### 5.1 KeepAliveManager (Replica 独有)

Replica 用 KeepAliveManager 统一管理，vendor 分散在 MainApplication.init() 中。
当前 KeepAliveManager.init() 只注册了 ScreenBroadcastReceiver 和 BatteryLevelReceiver，缺少:
- ❌ BootBroadcast 注册 (在 Manifest 中静态注册)
- ❌ AlarmReceiver 注册
- ❌ PowerBroadcastReceiver 注册
- ❌ ShutDownBroadcastReceiver 注册
- ❌ NetWorkReceiver 注册
- ❌ LocaleChangeReceiver 注册

### 5.2 线程差距

| 功能 | Vendor | Replica | 差距 |
|------|--------|---------|------|
| frpc 进程管理 | 完整 (启动/监控/重启) | 只有框架 | ❌ 核心缺失 |
| 心跳上报 | 完整 (HeartBodyVO + HTTP) | 只有空循环 | ❌ 核心缺失 |
| 消息队列 | 完整 (入队+定时发送) | 只有框架 | ❌ 核心缺失 |
| 策略执行 | 完整 (事件驱动) | 只有框架 | ❌ 核心缺失 |
| 屏幕录制 | 327行完整实现 | 52行框架 | ❌ 严重缺失 |
| 全局异常 | 捕获+自动重启 | 捕获+日志 | ⚠️ 缺自动重启 |

### 5.3 Receiver 差距 (所有 receiver 共同问题)

所有 receiver 都缺少 vendor 的核心模式:
```java
// vendor 模式 (每个 receiver 都有):
MessageRecordVO msg = new MessageRecordVO();
XxxVO vo = new XxxVO();
vo.setXxx(...);
msg.setExtraBody(vo);
msg.setIntentCode(intent.getAction());
MainApplication.getInstance().getHandlerMsgAndTimer().b(msg);
```

Replica 的 receiver 只有日志，没有消息上报。这依赖 MODULE_01 网络通信完成后才能对接。

## 6. 优先修复项

### P0 (保活基本运行)
1. KeepAliveManager 注册所有 receiver (当前只注册了 2 个，应注册 7+)
2. HeartThread 补齐心跳上报逻辑 (构建 HeartBodyVO + HTTP 发送)
3. MessageQueueManager 补齐消息队列发送 (依赖 MODULE_01)
4. GlobalExceptionHandler 补齐自动重启逻辑

### P1 (完整保活)
5. CheckProcessThread 补齐 frpc 进程管理
6. StrategyThread 补齐策略事件处理
7. BootBroadcast 补齐开机初始化 + 消息上报
8. WIFIBackgroundService 补齐 JobScheduler 持久化

### P2 (边缘功能)
9. ScreenRecordThread 补齐屏幕录制 (327→52行)
10. MediaChangeThread/DataSyncThread 补齐媒体同步
11. 所有 receiver 补齐 MessageRecordVO 消息上报

## 7. 真机验证要点

```bash
# 开机自启
adb reboot && adb logcat -s "BootBroadcast"

# 心跳线程
adb logcat -s "HeartThread" "KeepHeartThread"

# 进程检查
adb logcat -s "CheckProcessThread"

# 保活服务
adb shell dumpsys jobscheduler | grep "com.vendor.rat"
adb shell dumpsys account | grep "com.vendor.rat"

# 进程存活 (后台 5 分钟)
adb shell pidof com.vendor.rat  # 立即
sleep 300
adb shell pidof com.vendor.rat  # 5 分钟后
```
