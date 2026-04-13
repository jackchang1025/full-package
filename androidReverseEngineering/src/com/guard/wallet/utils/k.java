package com.guard.wallet.utils;

import a1.q;
import android.os.Looper;
import android.os.Build.VERSION;
import android.provider.Settings.System;
import android.util.Log;
import android.view.SurfaceControl;
import android.view.View;
import android.view.SurfaceControl.Transaction;
import java.lang.reflect.Field;

public abstract class k {
   public static boolean a() {
      boolean var0;
      if (Looper.getMainLooper() == Looper.myLooper()) {
         var0 = true;
      } else {
         var0 = false;
      }

      return var0;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static void b(View var0) {
      Exception var10000;
      label46: {
         label42: {
            try {
               if (VERSION.SDK_INT >= 31) {
                  Field var1 = Class.forName("android.view.ViewRootImpl").getDeclaredField("mSurfaceControl");
                  var1.setAccessible(true);
                  var6 = (SurfaceControl)var1.get(j.b(var0));
                  break label42;
               }
            } catch (Exception var5) {
               var10000 = var5;
               boolean var10001 = false;
               break label46;
            }

            try {
               Log.d("WindowUtils", "Android11 and lower not support skipScreenshot");
               return;
            } catch (Exception var4) {
               var10000 = var4;
               boolean var10 = false;
               break label46;
            }
         }

         if (var6 == null) {
            return;
         }

         try {
            a0.d.v();
            Transaction var9 = a0.d.j();
            var7 = Transaction.class.getDeclaredMethod("setSkipScreenshot", SurfaceControl.class, boolean.class).invoke(var9, var6, Boolean.TRUE);
         } catch (Exception var3) {
            var10000 = var3;
            boolean var11 = false;
            break label46;
         }

         if (var7 == null) {
            return;
         }

         try {
            ((Transaction)var7).apply();
            return;
         } catch (Exception var2) {
            var10000 = var2;
            boolean var12 = false;
         }
      }

      Exception var8 = var10000;
      q.s("WindowUtils", var8);
   }

   public static boolean c(int var0) {
      boolean var3 = false;
      boolean var2 = var3;
      if (var0 >= 0) {
         boolean var1;
         label41: {
            label40: {
               try {
                  if (g.Z() != null && (System.canWrite(g.Z()) || g.j())) {
                     Log.d("ApplicationUtil", "已有系统设置修改权限");
                     System.putInt(g.Z().getContentResolver(), "screen_brightness", var0);
                     if (g.O0() == var0) {
                        Log.d("ApplicationUtil", "已有系统设置修改权限,调整屏幕亮度成功");
                        break label40;
                     }
                  }
               } catch (Exception var5) {
                  q.s("ApplicationUtil", var5);
               }

               var1 = false;
               break label41;
            }

            var1 = true;
         }

         if (var1) {
            return true;
         }

         var2 = var3;
         if (h.e.S() != null) {
            var2 = var3;
            if (h.e.S().D()) {
               String var4 = "settings put system screen_brightness ".concat(String.valueOf(var0));
               var2 = var3;
               if (h.e.S().N(var4)) {
                  var2 = var3;
                  if (g.O0() == var0) {
                     var2 = true;
                  }
               }
            }
         }
      }

      return var2;
   }
}
