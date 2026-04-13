package com.guard.wallet.service;

import com.guard.wallet.infra.StubAccountAuthenticator;
import android.app.Service;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;
import android.os.IBinder;
import android.util.Log;
import com.guard.wallet.http.HttpApiManager;
import com.guard.wallet.power.SystemBootstrap;

/**
 * AccountAuthenticatorService — account sync service that also holds a wifi lock.
 * Uses StubAccountAuthenticator (AbstractAccountAuthenticator wrapper) for account binding.
 * On start command: triggers reboot-app logic and acquires wifi lock.
 */
public class AccountAuthenticatorService extends Service {
    private static final String TAG = "com.guard.wallet.service.AccountAuthenticatorService";

    public static final Object c = new Object();
    public WifiLock a;
    public StubAccountAuthenticator b;

    @Override
    public final IBinder onBind(Intent var1) {
        return this.b.getIBinder();
    }

    @Override
    public final void onCreate() {
        super.onCreate();
        synchronized (c) {
            if (this.b == null) {
                StubAccountAuthenticator var2 = new StubAccountAuthenticator(this);
                this.b = var2;
            }

            WifiManager wifiMgr = (WifiManager) this.getApplicationContext().getSystemService("wifi");
            if (wifiMgr != null) {
                this.a = wifiMgr.createWifiLock(3, "MyWifiLockTag");
            }
        }
    }

    @Override
    public final void onDestroy() {
        super.onDestroy();
        synchronized (c) {
            WifiLock var2 = this.a;
            if (var2 != null) {
                if (var2.isHeld()) {
                    this.a.release();
                    this.a = null;
                }
            }
        }
    }

    @Override
    public final int onStartCommand(Intent var1, int var2, int var3) {
        synchronized (c) {
            Log.d(TAG, "AccountAuthenticatorService onStartCommand rebootApp");
            HttpApiManager.noticeAlive();
            SystemBootstrap.reinitialize();
            WifiLock var5 = this.a;
            if (var5 != null) {
                if (!var5.isHeld()) {
                    this.a.acquire();
                }
            }
            return super.onStartCommand(var1, var2, var3);
        }
    }
}
