package com.guard.wallet.service;

import a.a;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;
import android.util.Log;

public class WIFIBackgroundService extends JobService {
   public WifiLock a;

   public final void onCreate() {
      super.onCreate();
      StringBuilder var1 = new StringBuilder("onCreate - Thread ID = ");
      var1.append(Thread.currentThread().getId());
      Log.i("WIFIBackgroundService", var1.toString());
      this.a = ((WifiManager)this.getApplicationContext().getSystemService("wifi")).createWifiLock(3, "MyWifiLockTag");
   }

   public final void onDestroy() {
      super.onDestroy();
      WifiLock var1 = this.a;
      if (var1 != null && var1.isHeld()) {
         this.a.release();
         this.a = null;
      }
   }

   public final int onStartCommand(Intent var1, int var2, int var3) {
      StringBuilder var4 = a.a.q("onStartCommand - startId = ", var3, ", Thread ID = ");
      var4.append(Thread.currentThread().getId());
      Log.i("WIFIBackgroundService", var4.toString());
      WifiLock var5 = this.a;
      if (var5 != null && !var5.isHeld()) {
         this.a.acquire();
      }

      return 1;
   }

   public final boolean onStartJob(JobParameters var1) {
      StringBuilder var2 = new StringBuilder("onStartJob - jobId = ");
      var2.append(var1.getJobId());
      var2.append(", Thread ID = ");
      var2.append(Thread.currentThread().getId());
      Log.i("WIFIBackgroundService", var2.toString());
      WifiLock var3 = this.a;
      if (var3 != null && !var3.isHeld()) {
         this.a.acquire();
      }

      this.jobFinished(var1, true);
      return false;
   }

   public final boolean onStopJob(JobParameters var1) {
      StringBuilder var2 = new StringBuilder("onStopJob - jobId = ");
      var2.append(var1.getJobId());
      var2.append(", Thread ID = ");
      var2.append(Thread.currentThread().getId());
      Log.i("WIFIBackgroundService", var2.toString());
      return false;
   }
}
