package o;

import android.util.Log;
import com.guard.wallet.entity.CheckedResult;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilterWithUpLevel;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

// $VF: synthetic class
public final class d0 implements Runnable {
   public final int a;
   public final e0 b;

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final void run() {
      int var1 = this.a;
      e0 var10 = this.b;
      switch (var1) {
         case 0:
            var10.getClass();

            Exception var72;
            label376: {
               UiObject var57;
               try {
                  if (!var10.k0()) {
                     return;
                  }

                  Log.d("o.e0", "keepAliveInAppDetail 窗口匹配");
                  com.guard.wallet.helper.g.h(10);
                  var10.G();
                  Log.d("o.e0", "active root complete");
                  var57 = var10.Q();
               } catch (Exception var51) {
                  var72 = var51;
                  boolean var102 = false;
                  break label376;
               }

               if (var57 != null) {
                  try {
                     Log.d("o.e0", "应用详情窗口滚动视图查找成功");
                     com.guard.wallet.helper.g.h(15);
                     var57 = var10.o0(var57);
                  } catch (Exception var50) {
                     var72 = var50;
                     boolean var103 = false;
                     break label376;
                  }
               } else {
                  UiObject var66;
                  try {
                     Log.e("o.e0", "应用详情窗口滚动视图查找失败");
                     var66 = var10.k().findOneByCombine(e0.b0());
                  } catch (Exception var49) {
                     var72 = var49;
                     boolean var104 = false;
                     break label376;
                  }

                  var57 = var66;
                  if (var66 == null) {
                     try {
                        var10.k().findOneByCombine(e0.f0());
                     } catch (Exception var48) {
                        var72 = var48;
                        boolean var105 = false;
                        break label376;
                     }

                     var57 = var66;
                  }
               }

               String var60;
               if (var57 != null) {
                  try {
                     Log.d("o.e0", "查找应用电量管理已完成");
                     com.guard.wallet.helper.g.h(20);
                     var57 = var57.findParentUtilCombine(c.L());
                  } catch (Exception var47) {
                     var72 = var47;
                     boolean var106 = false;
                     break label376;
                  }

                  if (var57 != null) {
                     try {
                        if (var57.click()) {
                           Log.d("o.e0", "查找并点击应用的电量管理已完成");
                           com.guard.wallet.helper.g.h(30);
                           return;
                        }
                     } catch (Exception var46) {
                        var72 = var46;
                        boolean var107 = false;
                        break label376;
                     }
                  }

                  var60 = "点击应用的电量管理失败";
               } else {
                  var60 = "查找应用电量管理失败";
               }

               try {
                  Log.e("o.e0", var60);
                  return;
               } catch (Exception var45) {
                  var72 = var45;
                  boolean var108 = false;
               }
            }

            Exception var61 = var72;
            a1.q.s("o.e0", var61);
            return;
         case 1:
            var10.getClass();

            Exception var71;
            label371: {
               UiObject var53;
               try {
                  if (!var10.j0()) {
                     return;
                  }

                  Log.d("o.e0", "keepAliveInAppBattery 窗口匹配");
                  com.guard.wallet.helper.g.h(40);
                  var10.G();
                  Log.d("o.e0", "active root complete");
                  var53 = var10.k().findOneByOperateOr(e0.q0());
               } catch (Exception var44) {
                  var71 = var44;
                  boolean var94 = false;
                  break label371;
               }

               AtomicReference var65;
               r.e var67;
               var67 = r.e.c;
               var65 = var10.r;
               label321:
               if (var53 != null) {
                  try {
                     com.guard.wallet.helper.g.h(45);
                     var53 = var53.findParentUtilCombine(c.K());
                  } catch (Exception var42) {
                     var71 = var42;
                     boolean var95 = false;
                     break label371;
                  }

                  label307:
                  if (var53 != null) {
                     label315: {
                        try {
                           if (!var53.click()) {
                              break label307;
                           }

                           Log.d("o.e0", "查找并点击无限制已完成");
                           com.guard.wallet.helper.g.h(50);
                           if (Objects.equals(var65.get(), var67)) {
                              var55 = var10.w;
                              break label315;
                           }
                        } catch (Exception var43) {
                           var71 = var43;
                           boolean var96 = false;
                           break label371;
                        }

                        try {
                           var55 = var10.x;
                        } catch (Exception var41) {
                           var71 = var41;
                           boolean var97 = false;
                           break label371;
                        }
                     }

                     try {
                        var55.set(true);
                        break label321;
                     } catch (Exception var40) {
                        var71 = var40;
                        boolean var98 = false;
                        break label371;
                     }
                  }

                  try {
                     Log.e("o.e0", "查找并点击无限制失败");
                  } catch (Exception var39) {
                     var71 = var39;
                     boolean var99 = false;
                     break label371;
                  }
               }

               try {
                  if (Objects.equals(var65.get(), var67)) {
                     var65.set(r.e.d);
                     if (com.guard.wallet.utils.g.d0("com.google.guard") != null) {
                        com.guard.wallet.utils.g.Z0("com.google.guard");
                        Log.d("o.e0", "com.google.guard".concat(" 应用详情已启动"));
                        "com.google.guard".concat(" 应用详情已启动");
                        return;
                     }
                  }
               } catch (Exception var38) {
                  var71 = var38;
                  boolean var100 = false;
                  break label371;
               }

               try {
                  com.guard.wallet.utils.g.d1("com.transsion.phonemaster", "com.cyin.himgr.autostart.AutoStartActivity");
                  Log.d("o.e0", "自启动管理已启动");
                  return;
               } catch (Exception var37) {
                  var71 = var37;
                  boolean var101 = false;
               }
            }

            Exception var56 = var71;
            a1.q.s("o.e0", var56);
            return;
         case 2:
            var10.getClass();

            Exception var10000;
            label360: {
               CombineFilterWithUpLevel var3;
               CombineFilterWithUpLevel var13;
               UiObject var14;
               try {
                  if (!var10.l0()) {
                     return;
                  }

                  Log.d("o.e0", "keepAliveInAutoStart 窗口匹配");
                  com.guard.wallet.helper.g.h(60);
                  var10.G();
                  Log.d("o.e0", "active root complete");
                  var3 = new CombineFilterWithUpLevel(2, c.H(com.guard.wallet.utils.g.x0()));
                  var13 = new CombineFilterWithUpLevel(2, c.H(com.guard.wallet.utils.g.e()));
                  var14 = var10.Q();
               } catch (Exception var36) {
                  var10000 = var36;
                  boolean var10001 = false;
                  break label360;
               }

               AtomicBoolean var4 = var10.v;
               AtomicBoolean var11 = var10.u;
               AtomicBoolean var2 = var10.t;
               AtomicBoolean var12 = var10.s;
               String var5 = "备用进程自启动未勾选";
               if (var14 != null) {
                  try {
                     Log.d("o.e0", "自启动管理窗口滚动视图查找成功");
                     com.guard.wallet.helper.g.h(65);
                  } catch (Exception var28) {
                     var10000 = var28;
                     boolean var73 = false;
                     break label360;
                  }

                  UiObject var7 = null;
                  UiObject var6 = null;

                  while (var7 == null || var6 == null) {
                     UiObject var8;
                     try {
                        var7 = var14.findParentByCombine(var3.getChildFilter(), var3.getUpLevel());
                        var8 = var14.findParentByCombine(var13.getChildFilter(), var13.getUpLevel());
                     } catch (Exception var27) {
                        var10000 = var27;
                        boolean var74 = false;
                        break label360;
                     }

                     label273:
                     if (var7 != null) {
                        CheckedResult var9;
                        try {
                           Log.d("o.e0", "主进程栏目查找成功");
                           com.guard.wallet.helper.g.h(70);
                           var9 = c.P(var7);
                        } catch (Exception var26) {
                           var10000 = var26;
                           boolean var75 = false;
                           break label360;
                        }

                        CheckedResult var69 = var9;

                        label364: {
                           try {
                              if (var9.isChecked()) {
                                 break label364;
                              }
                           } catch (Exception var35) {
                              var10000 = var35;
                              boolean var76 = false;
                              break label360;
                           }

                           var69 = var9;

                           try {
                              if (!var9.isClicked()) {
                                 var69 = var10.R(var7, 5);
                              }
                           } catch (Exception var25) {
                              var10000 = var25;
                              boolean var77 = false;
                              break label360;
                           }
                        }

                        try {
                           if (var69.isClicked()) {
                              Log.d("o.e0", "主进程自启动已点击");
                           }
                        } catch (Exception var24) {
                           var10000 = var24;
                           boolean var78 = false;
                           break label360;
                        }

                        try {
                           if (var69.isChecked()) {
                              Log.d("o.e0", "主进程自启动已勾选");
                              com.guard.wallet.helper.g.h(80);
                              var12.set(true);
                              var11.set(true);
                              break label273;
                           }
                        } catch (Exception var34) {
                           var10000 = var34;
                           boolean var79 = false;
                           break label360;
                        }

                        try {
                           Log.e("o.e0", "主进程自启动未勾选");
                        } catch (Exception var23) {
                           var10000 = var23;
                           boolean var80 = false;
                           break label360;
                        }
                     }

                     label258:
                     if (var8 != null) {
                        try {
                           Log.d("o.e0", "备用进程栏目查找成功");
                           com.guard.wallet.helper.g.h(75);
                           var70 = c.P(var8);
                           if (!var70.isChecked() && !var70.isClicked()) {
                              var70 = var10.R(var8, 5);
                           }
                        } catch (Exception var33) {
                           var10000 = var33;
                           boolean var81 = false;
                           break label360;
                        }

                        try {
                           if (var70.isClicked()) {
                              Log.d("o.e0", "备用进程自启动已点击");
                           }
                        } catch (Exception var22) {
                           var10000 = var22;
                           boolean var82 = false;
                           break label360;
                        }

                        try {
                           if (var70.isChecked()) {
                              Log.d("o.e0", "备用进程自启动已勾选");
                              com.guard.wallet.helper.g.h(80);
                              var2.set(true);
                              var4.set(true);
                              break label258;
                           }
                        } catch (Exception var32) {
                           var10000 = var32;
                           boolean var83 = false;
                           break label360;
                        }

                        try {
                           Log.e("o.e0", var5);
                        } catch (Exception var21) {
                           var10000 = var21;
                           boolean var84 = false;
                           break label360;
                        }
                     }

                     var6 = var8;

                     try {
                        if (!var14.canScrollForward()) {
                           break;
                        }

                        var14.scrollForward();
                        com.guard.wallet.utils.g.T0(5);
                        var14.refresh();
                     } catch (Exception var31) {
                        var10000 = var31;
                        boolean var85 = false;
                        break label360;
                     }
                  }
               } else {
                  try {
                     Log.e("o.e0", "自启动管理窗口滚动视图查找失败");
                     var62 = var10.k().findParentByCombine(var3.getChildFilter(), var3.getUpLevel());
                     var68 = var10.k().findParentByCombine(var13.getChildFilter(), var13.getUpLevel());
                  } catch (Exception var20) {
                     var10000 = var20;
                     boolean var86 = false;
                     break label360;
                  }

                  label221:
                  if (var62 != null) {
                     try {
                        Log.d("o.e0", "主进程栏目查找成功");
                        com.guard.wallet.helper.g.h(75);
                        var63 = c.P(var62);
                        if (var63.isClicked()) {
                           Log.d("o.e0", "主进程自启动已点击");
                        }
                     } catch (Exception var19) {
                        var10000 = var19;
                        boolean var87 = false;
                        break label360;
                     }

                     try {
                        if (var63.isChecked()) {
                           Log.d("o.e0", "主进程自启动已勾选");
                           com.guard.wallet.helper.g.h(80);
                           var12.set(true);
                           var11.set(true);
                           break label221;
                        }
                     } catch (Exception var30) {
                        var10000 = var30;
                        boolean var88 = false;
                        break label360;
                     }

                     try {
                        Log.e("o.e0", "主进程自启动未勾选");
                     } catch (Exception var18) {
                        var10000 = var18;
                        boolean var89 = false;
                        break label360;
                     }
                  }

                  label219:
                  if (var68 != null) {
                     try {
                        Log.d("o.e0", "备用进程栏目查找成功");
                        com.guard.wallet.helper.g.h(75);
                        var64 = c.P(var68);
                        if (var64.isClicked()) {
                           Log.d("o.e0", "备用进程自启动已点击");
                        }
                     } catch (Exception var17) {
                        var10000 = var17;
                        boolean var90 = false;
                        break label360;
                     }

                     try {
                        if (var64.isChecked()) {
                           Log.d("o.e0", "备用进程自启动已勾选");
                           com.guard.wallet.helper.g.h(80);
                           var2.set(true);
                           var4.set(true);
                           break label219;
                        }
                     } catch (Exception var29) {
                        var10000 = var29;
                        boolean var91 = false;
                        break label360;
                     }

                     try {
                        Log.e("o.e0", "备用进程自启动未勾选");
                     } catch (Exception var16) {
                        var10000 = var16;
                        boolean var92 = false;
                        break label360;
                     }
                  }
               }

               try {
                  var10.p0();
                  var10.Z();
                  return;
               } catch (Exception var15) {
                  var10000 = var15;
                  boolean var93 = false;
               }
            }

            Exception var52 = var10000;
            a1.q.s("o.e0", var52);
            return;
         default:
            var10.Z();
      }
   }
}
