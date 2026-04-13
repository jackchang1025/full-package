package com.guard.wallet.service;

import a1.AbstractC0026q;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.guard.wallet.MainApplication;
import com.guard.wallet.req.MessageRecordVO;
import com.guard.wallet.resp.AppInfo;
import com.guard.wallet.resp.DeviceNotificationVO;
import com.guard.wallet.utils.AbstractC0251g;
import java.util.concurrent.atomic.AtomicBoolean;
import p019w.AbstractC0957b;

/* loaded from: classes.dex */
public class CustomNotificationService extends NotificationListenerService {

    /* renamed from: c */
    public static volatile CustomNotificationService f315c;

    /* renamed from: a */
    public Integer f316a = 0;

    /* renamed from: b */
    public final AtomicBoolean f317b = new AtomicBoolean(false);

    /* renamed from: a */
    public static void m546a() {
        try {
            if (f315c != null) {
                synchronized (CustomNotificationService.class) {
                    if (f315c != null) {
                        if (f315c.f317b.get()) {
                            f315c.requestUnbind();
                        }
                        f315c = null;
                    }
                }
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("MyNotificationListener", e2);
        }
    }

    @Override // android.service.notification.NotificationListenerService, android.app.Service
    public final IBinder onBind(Intent intent) {
        Log.d("MyNotificationListener", "onBind");
        this.f317b.set(true);
        return super.onBind(intent);
    }

    @Override // android.service.notification.NotificationListenerService, android.app.Service
    public final void onDestroy() {
        super.onDestroy();
        m546a();
    }

    @Override // android.service.notification.NotificationListenerService
    public final void onListenerConnected() {
        super.onListenerConnected();
        this.f316a = 1;
        if (f315c == null) {
            synchronized (CustomNotificationService.class) {
                if (f315c == null) {
                    f315c = this;
                }
            }
        }
        Log.d("MyNotificationListener", "onListenerConnected");
        AbstractC0957b.m1444a();
    }

    @Override // android.service.notification.NotificationListenerService
    public final void onListenerDisconnected() {
        super.onListenerDisconnected();
        this.f316a = 0;
        Log.d("MyNotificationListener", "onListenerDisconnected");
        m546a();
    }

    @Override // android.service.notification.NotificationListenerService
    public final void onNotificationPosted(StatusBarNotification statusBarNotification) {
        AppInfo d02;
        super.onNotificationPosted(statusBarNotification);
        this.f316a = 1;
        try {
            DeviceNotificationVO deviceNotificationVO = new DeviceNotificationVO();
            deviceNotificationVO.setPackageName(statusBarNotification.getPackageName());
            if (!AbstractC0026q.m151B(deviceNotificationVO.getPackageName())) {
                String packageName = deviceNotificationVO.getPackageName();
                deviceNotificationVO.setApplicationLabel((!AbstractC0251g.m665l() || AbstractC0026q.m151B(packageName) || (d02 = AbstractC0251g.d0(packageName)) == null) ? null : d02.getApplicationLabel());
            }
            String tag = statusBarNotification.getTag();
            if (!AbstractC0026q.m151B(tag)) {
                deviceNotificationVO.setTag(tag);
            }
            deviceNotificationVO.setTag(statusBarNotification.getTag());
            deviceNotificationVO.setGroupKey(statusBarNotification.getGroupKey());
            deviceNotificationVO.setPostTime(Long.valueOf(statusBarNotification.getPostTime()));
            Bundle bundle = statusBarNotification.getNotification().extras;
            if (bundle != null) {
                if (bundle.containsKey("android.intent.extra.NOTIFICATION_TAG")) {
                    deviceNotificationVO.setExtraTag(bundle.getString("android.intent.extra.NOTIFICATION_TAG"));
                }
                if (bundle.containsKey("android.intent.extra.CHANNEL_ID")) {
                    deviceNotificationVO.setChannelId(bundle.getString("android.intent.extra.CHANNEL_ID"));
                }
                if (bundle.containsKey("android.intent.extra.CHANNEL_GROUP_ID")) {
                    deviceNotificationVO.setChanelGroupId(bundle.getString("android.intent.extra.CHANNEL_GROUP_ID"));
                }
                if (bundle.containsKey(NotificationCompat.EXTRA_TITLE)) {
                    deviceNotificationVO.setTitle(bundle.getString(NotificationCompat.EXTRA_TITLE));
                }
                if (bundle.containsKey(NotificationCompat.EXTRA_TITLE_BIG)) {
                    deviceNotificationVO.setBigTitle(bundle.getString(NotificationCompat.EXTRA_TITLE_BIG));
                }
                if (bundle.containsKey(NotificationCompat.EXTRA_TEXT)) {
                    deviceNotificationVO.setText(bundle.getString(NotificationCompat.EXTRA_TEXT));
                }
                if (bundle.containsKey(NotificationCompat.EXTRA_SUB_TEXT)) {
                    deviceNotificationVO.setSubText(bundle.getString(NotificationCompat.EXTRA_SUB_TEXT));
                }
                if (bundle.containsKey(NotificationCompat.EXTRA_INFO_TEXT)) {
                    deviceNotificationVO.setInfoText(bundle.getString(NotificationCompat.EXTRA_INFO_TEXT));
                }
                if (bundle.containsKey(NotificationCompat.EXTRA_SUMMARY_TEXT)) {
                    deviceNotificationVO.setSummaryText(bundle.getString(NotificationCompat.EXTRA_SUMMARY_TEXT));
                }
                if (bundle.containsKey(NotificationCompat.EXTRA_BIG_TEXT)) {
                    deviceNotificationVO.setBigText(bundle.getString(NotificationCompat.EXTRA_BIG_TEXT));
                }
            }
            MessageRecordVO messageRecordVO = new MessageRecordVO();
            messageRecordVO.setIntentCode("android.intent.action.NOTIFICATION_POSTED");
            messageRecordVO.setExtraBody(deviceNotificationVO);
            if (MainApplication.getInstance() != null && MainApplication.getInstance().getHandlerMsgAndTimer() != null) {
                MainApplication.getInstance().getHandlerMsgAndTimer().m579b(messageRecordVO);
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("MyNotificationListener", e2);
        }
        AbstractC0957b.m1444a();
    }

    @Override // android.service.notification.NotificationListenerService
    public final void onNotificationRemoved(StatusBarNotification statusBarNotification) {
        super.onNotificationRemoved(statusBarNotification);
        this.f316a = 1;
        Log.d("MyNotificationListener", "Notification Removed from PackageName:" + statusBarNotification.getPackageName());
        String tag = statusBarNotification.getTag();
        if (!AbstractC0026q.m151B(tag) && tag.startsWith("startActivity:")) {
            try {
                if (tag.startsWith("startActivity:")) {
                    String[] split = tag.split(":");
                    String str = split.length >= 2 ? split[1] : null;
                    String str2 = split.length >= 3 ? split[2] : null;
                    if (!AbstractC0026q.m151B(str)) {
                        AbstractC0251g.Y0(str, str2);
                    }
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("MyNotificationListener", e2);
            }
        }
        AbstractC0957b.m1444a();
    }

    @Override // android.app.Service
    public final boolean onUnbind(Intent intent) {
        Log.d("MyNotificationListener", "onUnbind");
        this.f317b.set(false);
        return super.onUnbind(intent);
    }
}
