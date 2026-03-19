# APK 保活机制完整分析

> 本文档详细分析飞鹰管理系统 APK 客户端的保活机制实现，包括核心服务、周期性任务、广播监听、JobScheduler 调度等多层防护策略。

## 目录

- [概述](#概述)
- [核心服务架构](#核心服务架构)
- [周期性任务](#周期性任务)
- [广播监听机制](#广播监听机制)
- [JobScheduler 调度](#jobscheduler-调度)
- [无障碍服务保活](#无障碍服务保活)
- [保活机制协作流程](#保活机制协作流程)
- [时间节点汇总](#时间节点汇总)

---

## 概述

APK 客户端使用**多层保活机制**，确保应用在各种场景下都能存活并自动重启。

### 保活策略总览

| 机制 | 优先级 | 作用 | 触发频率 |
|------|--------|------|---------|
| **前台服务** | ⭐⭐⭐ | 提升进程优先级 | 持续 |
| **无障碍服务** | ⭐⭐⭐ | 系统级保活 | 持续 |
| **TIME_TICK 广播** | ⭐⭐⭐ | 每分钟检查 | 每分钟 |
| **周期性任务** | ⭐⭐ | 状态检查 | 5秒/10秒 |
| **开机自启动** | ⭐⭐ | 重启后恢复 | 开机时 |
| **系统广播** | ⭐⭐ | 事件触发 | 事件发生时 |
| **JobScheduler** | ⭐ | 系统调度 | 15分钟 |
| **WorkManager** | ⭐ | 后台任务 | 15分钟 |
| **WakeLock** | ⭐ | 防止休眠 | 持续 |

---

## 核心服务架构

### 服务启动顺序

```
BootReceiver (开机启动)
    ↓
EngineWorker (引擎服务 - 协调者)
    ├─→ WorkServices (工作服务 - 核心功能)
    └─→ LiveChat (通信服务 - WebSocket)
```

---

### 1. EngineWorker（引擎协调服务）

**文件位置**: `smali/com/icontrol/protector/EngineWorker.smali`

**核心职责**:
- **服务协调器** - 负责启动和管理其他两个核心服务
- **前台保活** - 维持前台通知，防止被系统杀死
- **WorkManager 调度** - 每 15 分钟执行 MyWorker 任务

**onCreate() 执行流程**:
```
1. 启动前台服务（Notification）
2. 初始化设备 ID 和用户邮箱
3. 检查 WorkServices 是否运行 → 未运行则启动
4. 检查 LiveChat 是否运行 + WebSocket 是否连接 → 未运行/未连接则启动
```

**关键代码** (第 325-477 行):
```smali
.method public onCreate()V
    # 1. 启动前台服务
    invoke-direct {p0, v0}, Lcom/icontrol/protector/EngineWorker;->h(Landroid/content/Context;)V
    
    # 2. 启动 WorkServices（如果未运行）
    const-class v1, Lcom/icontrol/protector/WorkServices;
    invoke-static {v0, v1}, ...;->a(Landroid/content/Context;Ljava/lang/Class;)Z
    if-nez v0, :cond_3
        # Android 8.0+ 使用 startForegroundService
        invoke-static {p0, v0}, ...;->a(...)
    
    # 3. 启动 LiveChat（如果未运行）
    invoke-static {}, ...;->b()Z  # 检查 WebSocket 连接状态
    const-class v1, Lcom/icontrol/protector/LiveChat;
```

**启动的组件**:
- MyWorker (WorkManager 任务，15 分钟周期)
- EngineWorker$a (Thread，用于隐藏图标等操作)
- WorkServices (Service)
- LiveChat (Service)

**AndroidManifest.xml 配置**:
```xml
<service 
    android:name="com.icontrol.protector.EngineWorker"
    android:foregroundServiceType="specialUse"
    android:persistent="true"
    android:stopWithTask="false"/>
```

---

### 2. WorkServices（核心工作服务）

**文件位置**: `smali/com/icontrol/protector/WorkServices.smali`

**核心职责**:
- **定时任务调度中心** - 启动 2 个周期性任务（5秒/10秒）
- **服务状态监控** - 检查其他服务是否运行
- **WakeLock 管理** - 保持 CPU 唤醒，防止休眠
- **悬浮窗管理** - 创建隐藏的悬浮按钮（保活技巧）

**onCreate() 执行流程**:
```
1. 保存全局 Context
2. 初始化加密通信对象（ov）
3. 启动前台服务
4. 初始化 ReentrantLock（线程同步）
5. 创建 ScheduledExecutorService（2 个线程）
6. 获取 PowerManager
7. 启动定时任务（e() 方法）
```

**关键代码** (第 227-290 行):
```smali
.method private e()V
    # 任务 1: 每 10 秒执行一次（nl0 Runnable）
    new-instance v1, Laabab/.../nl0;
    const-wide/16 v4, 0xa              # 10 秒周期
    invoke-interface scheduleAtFixedRate(Runnable, 0, 10, SECONDS)
    
    # 任务 2: 每 5 秒执行一次（ol0 Runnable）
    new-instance v1, Laabab/.../ol0;
    const-wide/16 v4, 0x5              # 5 秒周期
    invoke-interface scheduleAtFixedRate(Runnable, 0, 5, SECONDS)
```

**启动的组件**:
- nl0 Runnable (10 秒周期任务)
- ol0 Runnable (5 秒周期任务)
- pl0 Runnable (线程池执行)
- 隐藏悬浮窗 (Button)
- PowerManager.WakeLock

**AndroidManifest.xml 配置**:
```xml
<service 
    android:name="com.icontrol.protector.WorkServices"
    android:foregroundServiceType="specialUse"
    android:persistent="true"
    android:stopWithTask="false"/>
```

---

### 3. LiveChat（WebSocket 通信服务）

**文件位置**: `smali/com/icontrol/protector/LiveChat.smali`

**核心职责**:
- **WebSocket 连接管理** - 维持与服务器的长连接
- **设备状态上报** - 收集并上报设备信息（电池、网络、位置等）
- **指令接收与执行** - 接收服务器指令并分发执行
- **线程池管理** - 使用 ThreadPoolExecutor 处理并发任务

**静态初始化** (第 29-52 行):
```smali
.method static constructor <clinit>()V
    # 创建线程池：核心 4 线程，最大 8 线程，队列容量 100
    new-instance v7, Ljava/util/concurrent/ThreadPoolExecutor;
    const/4 v1, 0x4        # corePoolSize
    const/16 v2, 0x8       # maximumPoolSize
    const-wide/16 v3, 0x3c # keepAliveTime: 60 秒
    new-instance v6, Ljava/util/concurrent/LinkedBlockingQueue;
    const/16 v0, 0x64      # 队列容量 100
```

**启动的组件**:
- ThreadPoolExecutor (4-8 线程)
- Handler (消息循环)
- 心跳 Runnable (定期发送心跳包)
- PowerManager.WakeLock

**AndroidManifest.xml 配置**:
```xml
<service 
    android:name="com.icontrol.protector.LiveChat"
    android:foregroundServiceType="specialUse"
    android:persistent="true"
    android:stopWithTask="false"/>
```

---

## 周期性任务

### 任务 1: nl0 (每 10 秒执行) - 网络心跳任务

**文件位置**: `smali/aabab/.../nl0.smali`

**调用链**:
```
nl0.run() 
  ↓
WorkServices.b() 
  ↓
WorkServices.f() 
  ↓
y80.b() 检查 WebSocket 连接状态
  ↓
LiveChat.D() 发送心跳包
```

**执行内容**:
1. 检查 WebSocket 是否已连接（`y80.b()` 返回静态布尔字段）
2. 如果已连接，调用 `LiveChat.D()` 收集设备信息
3. 构建心跳数据包（包含设备 ID、状态、电池、网络等）
4. 通过 WebSocket 发送到服务器

**作用**: 每 10 秒向服务器发送心跳，保持连接活跃，上报设备状态

---

### 任务 2: ol0 (每 5 秒执行) - WakeLock 管理任务

**文件位置**: `smali/aabab/.../ol0.smali`

**调用链**:
```
ol0.run() 
  ↓
WorkServices.a(WorkServices) 
  ↓
WorkServices.g() 
  ↓
cg0.j(Context) 获取屏幕状态
  ↓
根据状态管理 WakeLock
```

**执行内容**:
1. 获取当前屏幕状态（`cg0.j(Context)` 返回状态字符串）
2. **屏幕亮起时**:
   - 创建 `SCREEN_BRIGHT_WAKE_LOCK | ON_AFTER_RELEASE` 类型 WakeLock
   - 调用 `acquire()` 获取锁
3. **屏幕熄灭时**:
   - 调用 `release()` 释放 WakeLock

**作用**: 每 5 秒检查屏幕状态，动态管理 WakeLock，防止设备休眠

**⚠️ 重要**: 这个任务**不会主动唤醒屏幕**，只是根据屏幕状态管理 WakeLock

---
## 广播监听机制

### BootReceiver（开机自启动）

**文件位置**: `smali/com/icontrol/protector/BootReceiver.smali`

**监听的广播**:
```xml
<receiver android:name="com.icontrol.protector.BootReceiver">
    <intent-filter>
        <action android:name="android.intent.action.BOOT_COMPLETED"/>
        <action android:name="android.intent.action.QUICKBOOT_POWERON"/>
        <action android:name="com.htc.intent.action.QUICKBOOT_POWERON"/>
        <action android:name="android.intent.action.REBOOT"/>
    </intent-filter>
</receiver>
```

**执行流程** (第 49-133 行):
```
接收开机广播
  ↓
检查 EngineWorker 是否运行 → 未运行则启动
  ↓
检查 WorkServices 是否运行 → 未运行则启动
  ↓
检查 LiveChat 是否运行 → 未运行则启动
```

**兼容性处理**:
- Android 8.0+ (API 26): 使用 `startForegroundService()`
- Android 8.0-: 使用 `startService()`

---

### ResetServices（系统事件监听）

**文件位置**: `smali/com/icontrol/protector/ResetServices.smali`

**监听的 8 种系统广播**:
```xml
<receiver android:name="com.icontrol.protector.ResetServices">
    <intent-filter>
        <action android:name="android.intent.action.AIRPLANE_MODE"/>
        <action android:name="android.intent.action.BATTERY_LOW"/>
        <action android:name="android.intent.action.BATTERY_OKAY"/>
        <action android:name="android.intent.action.LOCALE_CHANGED"/>
        <action android:name="android.intent.action.TIMEZONE_CHANGED"/>
        <action android:name="android.intent.action.TIME_TICK"/>
        <action android:name="android.intent.action.DEVICE_STORAGE_LOW"/>
        <action android:name="android.intent.action.DEVICE_STORAGE_OK"/>
    </intent-filter>
</receiver>
```

**广播处理表**:

| 广播 | 触发时机 | 频率 | 执行动作 |
|------|---------|------|---------|
| **TIME_TICK** | 系统时间变化 | 每分钟 | ⭐⭐⭐ 特殊处理 |
| AIRPLANE_MODE | 飞行模式切换 | 事件触发 | 检查并重启 3 个核心服务 |
| BATTERY_LOW | 电量低 | 事件触发 | 检查并重启 3 个核心服务 |
| BATTERY_OKAY | 电量恢复 | 事件触发 | 检查并重启 3 个核心服务 |
| LOCALE_CHANGED | 语言切换 | 事件触发 | 检查并重启 3 个核心服务 |
| TIMEZONE_CHANGED | 时区切换 | 事件触发 | 检查并重启 3 个核心服务 |
| DEVICE_STORAGE_LOW | 存储空间低 | 事件触发 | 检查并重启 3 个核心服务 |
| DEVICE_STORAGE_OK | 存储空间恢复 | 事件触发 | 检查并重启 3 个核心服务 |

---

### TIME_TICK 特殊处理（每分钟触发）

**关键代码** (第 38-70 行):
```smali
# 解密广播名称
invoke-static {v0, v1}, v90;->a([B[B)Ljava/lang/String;

# 检查是否为 TIME_TICK
invoke-virtual {v0, p2}, String;->equals(Object;)Z

if-eqz p2
    # 1. 注册 JobScheduler
    invoke-static {p1}, bq;->a(Context;)V
    
    # 2. 提交 MyWorker 到 WorkManager
    new-instance p2, ux$a;
    const-class v0, MyWorker;
    invoke-static {p1}, yk0;->d(Context;)
    invoke-virtual {v0, p2}, yk0;->a(kl0;)
```

**执行操作**:
```
1. 调用 bq.a(Context)
   ↓
   注册 JobScheduler（Job ID = 100）
   - 周期: 900000ms (15 分钟)
   - 需要网络
   - 持久化任务

2. 创建 MyWorker 任务
   ↓
   提交到 WorkManager 队列

3. 检查并重启 3 个核心服务
   ↓
   EngineWorker → WorkServices → LiveChat
```

---

### 通用服务检查流程

**完整流程** (第 73-154 行):
```
接收系统广播
  ↓
检查 EngineWorker 是否运行（ba.a(Context, Class)）
  ↓ 未运行
Android 8.0+ → startForegroundService()
Android 8.0- → startService()
  ↓
检查 WorkServices 是否运行
  ↓ 未运行
启动 WorkServices
  ↓
检查 LiveChat 是否运行 + WebSocket 是否连接（y80.b()）
  ↓ 未运行/未连接
启动 LiveChat
```

**关键工具类**:

#### ba.a(Context, Class) - 服务运行检查
```smali
# 获取 ActivityManager
invoke-virtual {p0, v0}, Context;->getSystemService(String)Object;
check-cast p0, ActivityManager;

# 获取所有运行中的服务
invoke-virtual {p0, v0}, ActivityManager;->getRunningServices(I)List;

# 遍历比对服务类名
invoke-virtual {p1}, Class;->getName()String;
iget-object v0, v0, ActivityManager$RunningServiceInfo;->service:ComponentName;
invoke-virtual {v1, v0}, String;->equals(Object)Z
```

#### bq.a(Context) - JobScheduler 注册
```smali
# 获取 JobScheduler 服务
invoke-virtual {p0, v0}, Context;->getSystemService(String)Object;
check-cast v0, JobScheduler;

# 创建 JobInfo
new-instance p0, JobInfo$Builder;
const/16 v3, 0x64  # Job ID = 100

# 配置任务参数
invoke-virtual {p0, v2}, Builder;->setRequiredNetworkType(I)Builder;
invoke-virtual {p0, v2}, Builder;->setPersisted(Z)Builder;
const-wide/32 v3, 0xdbba0  # 900000ms = 15分钟
invoke-virtual {p0, v3, v4}, Builder;->setPeriodic(J)Builder;
invoke-virtual {v0, p0}, JobScheduler;->schedule(JobInfo;)I
```

---
## JobScheduler 调度

### MyJobService

**文件位置**: `smali/com/icontrol/protector/MyJobService.smali`

**AndroidManifest.xml 配置**:
```xml
<service 
    android:name="com.icontrol.protector.MyJobService"
    android:permission="android.permission.BIND_JOB_SERVICE"/>
```

**作用**: 系统级任务调度，在服务被杀后自动重启

**调度参数**:
- Job ID: 100
- 周期: 15 分钟 (900000ms)
- 需要网络连接
- 持久化任务（重启后保留）

---

### MyWorker (WorkManager 任务)

**文件位置**: `smali/com/icontrol/protector/MyWorker.smali`

**调度频率**: 每 15 分钟

**调度位置**:
1. **EngineWorker.g()** 方法（第 82-124 行）
2. **ResetServices.onReceive()** 方法（TIME_TICK 广播，第 54-70 行）

**关键代码** (EngineWorker.smali):
```smali
new-instance v0, n00$a;
const-wide/16 v1, 0xf              # 15 分钟
const-class v4, MyWorker;
invoke-direct {v0, v4, v1, v2, v3}

# 设置为 REPLACE 策略
sget-object v2, zi;->f
invoke-virtual {p1, v1, v2, v0}
```

**执行内容** (MyWorker.p() 方法):
```
1. 获取 WakeLock 防止休眠
2. 创建 CountDownLatch 等待任务完成
3. 提交任务到线程池（MyWorker$a Runnable）
4. 等待 30 秒
5. 释放 WakeLock
```

**MyWorker$a 执行的操作**:
- 检查所有服务状态
- 重启已停止的服务
- 清理缓存
- 发送状态报告

---

## 无障碍服务保活

### AccessServices

**文件位置**: `smali/com/icontrol/protector/AccessServices.smali`

**AndroidManifest.xml 配置**:
```xml
<service 
    android:name="com.icontrol.protector.AccessServices"
    android:permission="android.permission.BIND_ACCESSIBILITY_SERVICE"
    android:persistent="true">
    <intent-filter>
        <action android:name="android.accessibilityservice.AccessibilityService"/>
    </intent-filter>
</service>
```

**保活优势**:
- 系统级服务，优先级极高
- 难以被杀死
- 可以监听系统事件并拉起其他服务

---

### 监听窗口事件并拉起服务

**监听的事件类型**: `TYPE_WINDOW_STATE_CHANGED (0x4000)`

**触发场景**:
- 锁屏/解锁
- 应用切换
- 窗口状态变化

**关键代码** (第 5207-5222 行):
```smali
:cond_1e
new-instance v0, Landroid/content/Intent;
const-class v4, Lcom/icontrol/protector/TransparentActivity;
invoke-direct {v0, v1, v4}

const/high16 v4, 0x10000000      # FLAG_ACTIVITY_NEW_TASK
invoke-virtual {v0, v4}, addFlags

const/high16 v4, 0x10000         # FLAG_ACTIVITY_NO_HISTORY
invoke-virtual {v0, v4}, addFlags

invoke-virtual {v1, v0}, startActivity
```

**拉起的组件**:

1. **直接拉起**: TransparentActivity（透明 Activity）
   - 作用：唤醒屏幕
   - 触发条件：检测到特定窗口事件文本

2. **间接拉起**（TransparentActivity.onCreate() 调用 b() 方法）:
   - EngineWorker
   - WorkServices
   - LiveChat

**完整流程**:
```
窗口状态变化
  ↓
AccessServices.onAccessibilityEvent()
  ↓
检测 eventType == 0x4000
  ↓
匹配事件文本（sparse-switch）
  ↓
启动 TransparentActivity
  ↓
TransparentActivity.onCreate() 调用 b() 方法
  ↓
启动 3 个核心服务
  ↓
10 秒后自动关闭 TransparentActivity
```

---
## 保活机制协作流程

### 完整协作图

```
系统开机
  ↓
BootReceiver 启动 3 个核心服务
  ├─ EngineWorker → 调度 MyWorker (15分钟)
  ├─ WorkServices → 启动周期任务 (5秒/10秒)
  └─ LiveChat → 建立 WebSocket 连接
  ↓
ResetServices 监听 8 种广播
  ├─ TIME_TICK (每分钟) → 注册 JobScheduler + 启动 MyWorker + 检查服务
  └─ 其他 7 种广播 → 检查并重启服务
  ↓
AccessServices 监听窗口事件
  └─ 窗口变化 → 启动 TransparentActivity → 拉起 3 个服务
  ↓
WorkServices 周期任务
  ├─ 10秒任务 (nl0) → WebSocket 心跳保活
  └─ 5秒任务 (ol0) → WakeLock 动态管理
  ↓
MyWorker (15分钟) → 检查并重启服务
  ↓
JobScheduler (15分钟) → 系统级调度保活
```

---

### 服务依赖关系

**启动依赖**:
- **EngineWorker** → 启动 → **WorkServices**
- **EngineWorker** → 启动 → **LiveChat**
- **WorkServices** → 调用 → **LiveChat.D()** (数据上报)

**数据流**:
```
设备信息采集 → LiveChat.D() → 加密（ov） → WebSocket 发送（y80.d()）
                ↓
                HTTP 备用通道（k()）
```

**保活机制**:
1. **前台服务** - 三个服务都使用 `startForeground()` 显示通知
2. **WakeLock** - WorkServices 和 LiveChat 持有唤醒锁
3. **WorkManager** - EngineWorker 使用 WorkManager 定期唤醒
4. **JobScheduler** - TIME_TICK 注册系统级调度
5. **悬浮窗** - WorkServices 创建隐藏悬浮窗（部分 ROM 保活技巧）

---

## 时间节点汇总

| 机制 | 频率 | 作用 | 优先级 |
|------|------|------|--------|
| **TIME_TICK 广播** | 每分钟 | 注册 JobScheduler + 启动 MyWorker + 检查服务 | ⭐⭐⭐ |
| **nl0 任务** | 每 10 秒 | WebSocket 心跳保活 | ⭐⭐⭐ |
| **ol0 任务** | 每 5 秒 | WakeLock 动态管理 | ⭐⭐ |
| **MyWorker** | 每 15 分钟 | 服务检查和重启 | ⭐⭐ |
| **JobScheduler** | 每 15 分钟 | 系统级调度 | ⭐⭐ |
| **窗口事件** | 实时 | 拉起服务 | ⭐⭐ |
| **其他广播** | 事件触发 | 检查并重启服务 | ⭐ |

---

## 关键方法速查表

| 服务 | 方法 | 行号 | 核心功能 |
|------|------|------|----------|
| **EngineWorker** | onCreate | 325-477 | 启动其他服务、初始化配置 |
| | onStartCommand | 534-546 | 重启前台服务 |
| | onDestroy | 520-532 | 调度 WorkManager |
| | g() | 82-151 | WorkManager 调度逻辑 |
| | h() | 153-207 | 前台服务启动 |
| **WorkServices** | onCreate | 890-956 | 初始化线程池、启动定时任务 |
| | onStartCommand | 1047-1149 | 创建悬浮窗、持有 WakeLock |
| | onDestroy | 984-1044 | 重启服务、设置 AlarmManager |
| | e() | 227-332 | 定时任务调度 |
| | f() | - | WebSocket 心跳检查 |
| | g() | - | WakeLock 管理 |
| **LiveChat** | D() | 587-1832 | 收集并上报设备状态 |
| | E() | 1834-1857 | WebSocket 发送（线程安全） |
| | C() | 410-585 | 构建数据包 |
| **ResetServices** | onReceive | 17-209 | 处理系统广播、检查服务 |
| **BootReceiver** | onReceive | 17-186 | 开机启动服务 |
| **AccessServices** | onAccessibilityEvent | - | 监听窗口事件、拉起服务 |

---

## 总结

APK 客户端的保活系统具有以下特点：

### 多层防护
- **9 种保活机制**互相配合
- **三层防护**: 广播监听 + JobScheduler + WorkManager
- **服务互拉**: 3 个核心服务互相监控和启动

### 高频检查
- **最高频**: TIME_TICK 每分钟 + nl0/ol0 每 5-10 秒
- **中频**: MyWorker + JobScheduler 每 15 分钟
- **事件驱动**: 8 种系统广播实时触发

### 系统级保活
- **无障碍服务** + **persistent 属性** + **前台服务**
- **WakeLock** 防止 CPU 休眠
- **隐藏悬浮窗**提升进程优先级

**这是一个极其强大且多层次的保活系统，几乎无法被正常手段杀死！** 🔒

---

## 相关文档

- [WEBSOCKET_CLIENT.md](./WEBSOCKET_CLIENT.md) - WebSocket 系统架构
- [WEBSOCKET_SERVER_PHP.md](./WEBSOCKET_SERVER_PHP.md) - PHP WebSocket 服务器
- [APK_BUILDER.md](./APK_BUILDER.md) - APK 构建服务
- [APK_BUILDER_AUTO_WAKE_SCREEN.md](./APK_BUILDER_AUTO_WAKE_SCREEN.md) - 自动唤醒屏幕功能

---

**生成时间**: 2026-03-14  
**分析工具**: explore agents (3个并行) + 直接代码审查  
**证据来源**: APK 模板 Smali 字节码 + AndroidManifest.xml
