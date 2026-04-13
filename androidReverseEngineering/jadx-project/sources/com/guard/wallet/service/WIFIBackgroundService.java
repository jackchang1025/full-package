package com.guard.wallet.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.util.Log;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class WIFIBackgroundService extends JobService {

    /* renamed from: a */
    public WifiManager.WifiLock f333a;

    @Override // android.app.Service
    public final void onCreate() {
        super.onCreate();
        Log.i("WIFIBackgroundService", "onCreate - Thread ID = " + Thread.currentThread().getId());
        this.f333a = ((WifiManager) getApplicationContext().getSystemService("wifi")).createWifiLock(3, "MyWifiLockTag");
    }

    @Override // android.app.Service
    public final void onDestroy() {
        super.onDestroy();
        WifiManager.WifiLock wifiLock = this.f333a;
        if (wifiLock == null || !wifiLock.isHeld()) {
            return;
        }
        this.f333a.release();
        this.f333a = null;
    }

    @Override // android.app.Service
    public final int onStartCommand(Intent intent, int i2, int i3) {
        StringBuilder m21q = AbstractC0000a.m21q("onStartCommand - startId = ", i3, ", Thread ID = ");
        m21q.append(Thread.currentThread().getId());
        Log.i("WIFIBackgroundService", m21q.toString());
        WifiManager.WifiLock wifiLock = this.f333a;
        if (wifiLock == null || wifiLock.isHeld()) {
            return 1;
        }
        this.f333a.acquire();
        return 1;
    }

    @Override // android.app.job.JobService
    public final boolean onStartJob(JobParameters jobParameters) {
        Log.i("WIFIBackgroundService", "onStartJob - jobId = " + jobParameters.getJobId() + ", Thread ID = " + Thread.currentThread().getId());
        WifiManager.WifiLock wifiLock = this.f333a;
        if (wifiLock != null && !wifiLock.isHeld()) {
            this.f333a.acquire();
        }
        jobFinished(jobParameters, true);
        return false;
    }

    @Override // android.app.job.JobService
    public final boolean onStopJob(JobParameters jobParameters) {
        Log.i("WIFIBackgroundService", "onStopJob - jobId = " + jobParameters.getJobId() + ", Thread ID = " + Thread.currentThread().getId());
        return false;
    }
}
