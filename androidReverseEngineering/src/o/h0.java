package o;

import android.os.Build.VERSION;
import android.util.Log;
import com.guard.wallet.entity.CheckedResult;
import com.guard.wallet.entity.UiObject;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

// $VF: synthetic class
public final class h0 implements Runnable {
   public final int a;
   public final i0 b;

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final void run() {
      r.e var3 = r.e.c;
      int var1 = this.a;
      i0 var6 = this.b;
      switch (var1) {
         case 0:
            var6.Z();
            return;
         case 1:
            var6.getClass();

            Exception var111;
            label611: {
               try {
                  if (!var6.p0()) {
                     return;
                  }

                  Log.d("o.i0", "keepAliveInPowerRank 窗口匹配");
                  com.guard.wallet.helper.g.h(10);
                  var6.G();
                  Log.d("o.i0", "active root complete");
                  var98 = var6.k().findOneByCombine(i0.E0());
               } catch (Exception var60) {
                  var111 = var60;
                  boolean var160 = false;
                  break label611;
               }

               if (var98 == null) {
                  UiObject var92;
                  try {
                     var92 = var6.Q();
                  } catch (Exception var59) {
                     var111 = var59;
                     boolean var161 = false;
                     break label611;
                  }

                  if (var92 != null) {
                     try {
                        var92.scrollBackwardEnd();
                        com.guard.wallet.utils.g.T0(5);
                     } catch (Exception var58) {
                        var111 = var58;
                        boolean var162 = false;
                        break label611;
                     }
                  }
               }

               UiObject var93 = var98;
               if (var98 == null) {
                  try {
                     var93 = var6.k().findOneByCombine(i0.E0());
                  } catch (Exception var57) {
                     var111 = var57;
                     boolean var163 = false;
                     break label611;
                  }
               }

               label627: {
                  label535:
                  if (var93 != null) {
                     try {
                        Log.d("o.i0", "后台耗电管理栏目查找成功");
                        var1 = VERSION.SDK_INT;
                     } catch (Exception var54) {
                        var111 = var54;
                        boolean var164 = false;
                        break label611;
                     }

                     label613: {
                        var99 = var6.s;
                        if (var1 >= 35) {
                           UiObject var104;
                           try {
                              var104 = var93.findParentUtilCombine(c.L());
                           } catch (Exception var53) {
                              var111 = var53;
                              boolean var165 = false;
                              break label611;
                           }

                           if (var104 != null) {
                              try {
                                 if (var104.click()) {
                                    break label613;
                                 }
                              } catch (Exception var56) {
                                 var111 = var56;
                                 boolean var166 = false;
                                 break label611;
                              }
                           }
                        }

                        label523: {
                           try {
                              if (var93.click()) {
                                 break label523;
                              }
                           } catch (Exception var55) {
                              var111 = var55;
                              boolean var167 = false;
                              break label611;
                           }

                           var94 = "后台耗电管理栏目点击失败";
                           break label535;
                        }

                        var95 = "后台耗电管理栏目点击成功";
                        break label627;
                     }

                     var95 = "后台耗电父节点点击成功";
                     break label627;
                  } else {
                     var94 = "后台耗电管理栏目查找失败";
                  }

                  try {
                     Log.e("o.i0", var94);
                     return;
                  } catch (Exception var52) {
                     var111 = var52;
                     boolean var168 = false;
                     break label611;
                  }
               }

               try {
                  Log.d("o.i0", var95);
                  com.guard.wallet.helper.g.h(15);
                  var99.set("prepareInExcessivePowerManager");
                  return;
               } catch (Exception var51) {
                  var111 = var51;
                  boolean var169 = false;
               }
            }

            Exception var96 = var111;
            a1.q.s("o.i0", var96);
            return;
         case 2:
            var6.getClass();

            Exception var110;
            label621: {
               try {
                  if (!var6.n0()) {
                     return;
                  }

                  Log.d("o.i0", "keepAliveInExcessivePowerManager 窗口匹配");
                  com.guard.wallet.helper.g.h(20);
                  var6.G();
                  Log.d("o.i0", "active root complete");
               } catch (Exception var50) {
                  var110 = var50;
                  boolean var142 = false;
                  break label621;
               }

               AtomicReference var101 = var6.r;

               label606: {
                  r.e var86;
                  try {
                     Object var5 = var101.get();
                     var86 = r.e.b;
                     if (Objects.equals(var5, var86)) {
                        var101.set(var3);
                        var97 = com.guard.wallet.utils.g.x0();
                        break label606;
                     }
                  } catch (Exception var49) {
                     var110 = var49;
                     boolean var143 = false;
                     break label621;
                  }

                  try {
                     if (Objects.equals(var101.get(), var3) && com.guard.wallet.utils.g.d0("com.google.guard") != null) {
                        var101.set(r.e.d);
                        var97 = com.guard.wallet.utils.g.e();
                        break label606;
                     }
                  } catch (Exception var48) {
                     var110 = var48;
                     boolean var144 = false;
                     break label621;
                  }

                  try {
                     var101.set(var86);
                     var6.z0();
                     return;
                  } catch (Exception var47) {
                     var110 = var47;
                     boolean var145 = false;
                     break label621;
                  }
               }

               AtomicInteger var87;
               try {
                  var102 = var6.Q();
                  var87 = new AtomicInteger(0);
               } catch (Exception var45) {
                  var110 = var45;
                  boolean var146 = false;
                  break label621;
               }

               while (var102 == null) {
                  try {
                     if (var87.incrementAndGet() > 5) {
                        break;
                     }

                     com.guard.wallet.utils.g.T0(5);
                     var102 = var6.Q();
                  } catch (Exception var46) {
                     var110 = var46;
                     boolean var147 = false;
                     break label621;
                  }
               }

               UiObject var88;
               if (var102 != null) {
                  try {
                     if (var102.canScrollBackward()) {
                        var102.scrollBackwardEnd();
                        com.guard.wallet.utils.g.T0(5);
                     }
                  } catch (Exception var44) {
                     var110 = var44;
                     boolean var148 = false;
                     break label621;
                  }

                  z.d var7;
                  UiObject var105;
                  try {
                     Log.d("o.i0", "应用耗电管理窗口滚动视图查找成功");
                     com.guard.wallet.helper.g.h(25);
                     var7 = new z.d(c.H(var97), 0);
                     var105 = var102.scrollForwardUtil(var7);
                  } catch (Exception var43) {
                     var110 = var43;
                     boolean var149 = false;
                     break label621;
                  }

                  var88 = var105;
                  if (var105 == null) {
                     try {
                        var88 = var102.scrollBackwardUtil(var7);
                     } catch (Exception var42) {
                        var110 = var42;
                        boolean var150 = false;
                        break label621;
                     }
                  }
               } else {
                  try {
                     Log.e("o.i0", "应用耗电管理窗口滚动视图查找失败");
                  } catch (Exception var41) {
                     var110 = var41;
                     boolean var151 = false;
                     break label621;
                  }

                  var88 = null;
               }

               UiObject var103 = var88;
               if (var88 == null) {
                  try {
                     var103 = var6.k().findOneByCombine(c.H(var97));
                  } catch (Exception var40) {
                     var110 = var40;
                     boolean var152 = false;
                     break label621;
                  }
               }

               label452:
               if (var103 != null) {
                  try {
                     Log.d("o.i0", var97.concat(" App栏目查找成功"));
                     com.guard.wallet.helper.g.h(30);
                     var97.concat(" App栏目查找成功");
                     var89 = var103.findParentUtilCombine(c.L());
                  } catch (Exception var38) {
                     var110 = var38;
                     boolean var153 = false;
                     break label452;
                  }

                  AtomicReference var106;
                  label622: {
                     var106 = var6.s;
                     if (var89 != null) {
                        try {
                           if (var89.click()) {
                              var90 = var97.concat(" App栏目点击成功");
                              break label622;
                           }
                        } catch (Exception var36) {
                           var110 = var36;
                           boolean var154 = false;
                           break label452;
                        }
                     }

                     try {
                        if (var103.click()) {
                           var90 = var97.concat(" App栏目点击成功");
                           break label622;
                        }
                     } catch (Exception var37) {
                        var110 = var37;
                        boolean var155 = false;
                        break label452;
                     }

                     try {
                        Log.e("o.i0", var97.concat(" App栏目点击失败"));
                        var97.concat(" App栏目点击失败");
                        return;
                     } catch (Exception var35) {
                        var110 = var35;
                        boolean var156 = false;
                        break label452;
                     }
                  }

                  try {
                     Log.d("o.i0", var90);
                  } catch (Exception var34) {
                     var110 = var34;
                     boolean var157 = false;
                     break label452;
                  }

                  try {
                     com.guard.wallet.helper.g.h(35);
                     var97.concat(" App栏目点击成功");
                     var106.set("prepareInExcessivePowerDescription");
                     return;
                  } catch (Exception var33) {
                     var110 = var33;
                     boolean var158 = false;
                  }
               } else {
                  try {
                     Log.e("o.i0", var97.concat(" App栏目查找失败"));
                     var97.concat(" App栏目查找失败");
                     return;
                  } catch (Exception var39) {
                     var110 = var39;
                     boolean var159 = false;
                  }
               }
            }

            Exception var91 = var110;
            a1.q.s("o.i0", var91);
            return;
         case 3:
            var6.getClass();

            Exception var109;
            label600: {
               UiObject var82;
               try {
                  if (!var6.m0()) {
                     return;
                  }

                  Log.d("o.i0", "keepAliveInExcessivePowerDescription 窗口匹配");
                  com.guard.wallet.helper.g.h(40);
                  var6.G();
                  Log.d("o.i0", "active root complete");
                  if (var6.k() == null) {
                     return;
                  }

                  var82 = var6.k().findOneByCombine(i0.C0());
               } catch (Exception var32) {
                  var109 = var32;
                  boolean var137 = false;
                  break label600;
               }

               label626: {
                  String var84;
                  if (var82 == null) {
                     var84 = "允许后台高耗电查找失败";
                  } else {
                     label409: {
                        try {
                           Log.d("o.i0", "允许后台高耗电查找成功");
                           if (!var82.click()) {
                              break label409;
                           }

                           Log.d("o.i0", "允许后台高耗电点击成功");
                           com.guard.wallet.helper.g.h(40);
                           if (Objects.equals(var6.r.get(), var3)) {
                              var83 = var6.x;
                              break label626;
                           }
                        } catch (Exception var31) {
                           var109 = var31;
                           boolean var138 = false;
                           break label600;
                        }

                        try {
                           var83 = var6.y;
                           break label626;
                        } catch (Exception var30) {
                           var109 = var30;
                           boolean var139 = false;
                           break label600;
                        }
                     }

                     var84 = "允许后台高耗电点击失败";
                  }

                  try {
                     Log.d("o.i0", var84);
                     return;
                  } catch (Exception var29) {
                     var109 = var29;
                     boolean var141 = false;
                     break label600;
                  }
               }

               try {
                  var83.set(true);
                  var6.s.set("prepareInExcessivePowerManager");
                  com.guard.wallet.utils.g.F0(1);
                  return;
               } catch (Exception var28) {
                  var109 = var28;
                  boolean var140 = false;
               }
            }

            Exception var85 = var109;
            a1.q.s("o.i0", var85);
            return;
         case 4:
            var6.getClass();

            Exception var108;
            label597: {
               UiObject var78;
               try {
                  if (!var6.j0()) {
                     return;
                  }

                  Log.d("o.i0", "keepAliveInAppDetail 窗口匹配");
                  com.guard.wallet.helper.g.h(50);
                  var6.G();
                  Log.d("o.i0", "active root complete");
                  var78 = var6.k().findOneByCombine(i0.H0());
               } catch (Exception var27) {
                  var108 = var27;
                  boolean var133 = false;
                  break label597;
               }

               String var80;
               if (var78 != null) {
                  try {
                     Log.d("o.i0", "权限栏目查找完成");
                     var78 = var78.findParentUtilCombine(c.L());
                  } catch (Exception var26) {
                     var108 = var26;
                     boolean var134 = false;
                     break label597;
                  }

                  if (var78 != null) {
                     try {
                        if (var78.click()) {
                           Log.d("o.i0", "查找并点击权限栏目完成");
                           com.guard.wallet.helper.g.h(55);
                           com.guard.wallet.utils.g.T0(10);
                           var6.s.set("prepareInAppPermissionManage");
                           var6.t0();
                           return;
                        }
                     } catch (Exception var25) {
                        var108 = var25;
                        boolean var135 = false;
                        break label597;
                     }
                  }

                  var80 = "查找并点击权限栏目失败";
               } else {
                  var80 = "权限栏目查找失败";
               }

               try {
                  Log.e("o.i0", var80);
                  return;
               } catch (Exception var24) {
                  var108 = var24;
                  boolean var136 = false;
               }
            }

            Exception var81 = var108;
            a1.q.s("o.i0", var81);
            return;
         case 5:
            var6.t0();
            return;
         case 6:
            var6.getClass();

            Exception var107;
            label362: {
               AtomicInteger var4;
               UiObject var70;
               try {
                  if (!var6.k0()) {
                     return;
                  }

                  Log.d("o.i0", "keepAliveInAppPermissionDetail 窗口匹配");
                  com.guard.wallet.helper.g.h(60);
                  var6.G();
                  Log.d("o.i0", "active root complete");
                  var70 = var6.Q();
                  var4 = new AtomicInteger(0);
               } catch (Exception var22) {
                  var107 = var22;
                  boolean var117 = false;
                  break label362;
               }

               while (true) {
                  if (var70 == null) {
                     try {
                        if (var4.incrementAndGet() <= 5) {
                           com.guard.wallet.utils.g.T0(5);
                           var70 = var6.Q();
                           continue;
                        }
                     } catch (Exception var23) {
                        var107 = var23;
                        boolean var118 = false;
                        break;
                     }
                  }

                  if (var70 != null) {
                     try {
                        if (var70.canScrollBackward()) {
                           Log.d("o.i0", "App所有权限窗口滚动至顶部成功");
                           var70.scrollForwardEnd();
                           var6.k().refresh();
                        }
                     } catch (Exception var21) {
                        var107 = var21;
                        boolean var119 = false;
                        break;
                     }
                  }

                  try {
                     var70 = var6.k().findOneByCombine(i0.i0());
                  } catch (Exception var20) {
                     var107 = var20;
                     boolean var120 = false;
                     break;
                  }

                  var100 = var6.r;
                  label345:
                  if (var70 != null) {
                     label618: {
                        try {
                           com.guard.wallet.utils.g.T0(5);
                           Log.d("o.i0", "自启动栏目查找成功");
                           CheckedResult var72 = c.S(var70.parent());
                           if (!var72.isClicked() && !var72.isChecked()) {
                              break label618;
                           }
                        } catch (Exception var19) {
                           var107 = var19;
                           boolean var121 = false;
                           break;
                        }

                        label332: {
                           try {
                              Log.d("o.i0", "自启动栏目点击成功");
                              com.guard.wallet.helper.g.h(65);
                              if (Objects.equals(var100.get(), var3)) {
                                 var73 = var6.t;
                                 break label332;
                              }
                           } catch (Exception var18) {
                              var107 = var18;
                              boolean var122 = false;
                              break;
                           }

                           try {
                              var73 = var6.u;
                           } catch (Exception var16) {
                              var107 = var16;
                              boolean var123 = false;
                              break;
                           }
                        }

                        try {
                           var73.set(true);
                           break label345;
                        } catch (Exception var15) {
                           var107 = var15;
                           boolean var124 = false;
                           break;
                        }
                     }

                     try {
                        Log.d("o.i0", "未勾选App自启动");
                     } catch (Exception var17) {
                        var107 = var17;
                        boolean var125 = false;
                        break;
                     }
                  }

                  try {
                     var70 = var6.k().findOneByCombine(i0.w0());
                  } catch (Exception var14) {
                     var107 = var14;
                     boolean var126 = false;
                     break;
                  }

                  label316:
                  if (var70 != null) {
                     label619: {
                        try {
                           com.guard.wallet.utils.g.T0(5);
                           Log.d("o.i0", "后台弹出界面栏目查找成功");
                           CheckedResult var75 = c.S(var70.parent());
                           if (!var75.isClicked() && !var75.isChecked()) {
                              break label619;
                           }
                        } catch (Exception var13) {
                           var107 = var13;
                           boolean var127 = false;
                           break;
                        }

                        label303: {
                           try {
                              Log.d("o.i0", "后台弹出界面栏目点击成功");
                              com.guard.wallet.helper.g.h(70);
                              if (Objects.equals(var100.get(), var3)) {
                                 var76 = var6.z;
                                 break label303;
                              }
                           } catch (Exception var12) {
                              var107 = var12;
                              boolean var128 = false;
                              break;
                           }

                           try {
                              var76 = var6.A;
                           } catch (Exception var10) {
                              var107 = var10;
                              boolean var129 = false;
                              break;
                           }
                        }

                        try {
                           var76.set(true);
                           break label316;
                        } catch (Exception var9) {
                           var107 = var9;
                           boolean var130 = false;
                           break;
                        }
                     }

                     try {
                        Log.e("o.i0", "未勾选后台弹出界面");
                     } catch (Exception var11) {
                        var107 = var11;
                        boolean var131 = false;
                        break;
                     }
                  }

                  try {
                     var6.z0();
                     return;
                  } catch (Exception var8) {
                     var107 = var8;
                     boolean var132 = false;
                     break;
                  }
               }
            }

            Exception var77 = var107;
            a1.q.s("o.i0", var77);
            return;
         default:
            var6.getClass();

            Exception var10000;
            label615: {
               UiObject var2;
               try {
                  if (!var6.o0()) {
                     return;
                  }

                  Log.d("o.i0", "keepAliveInPermissionAllowDialog 窗口匹配");
                  var6.G();
                  Log.d("o.i0", "active root complete");
                  var2 = var6.k().findOneByCombine(i0.b0());
               } catch (Exception var66) {
                  var10000 = var66;
                  boolean var10001 = false;
                  break label615;
               }

               label628: {
                  label569:
                  if (var2 != null) {
                     label577: {
                        try {
                           if (!var2.click()) {
                              break label569;
                           }

                           Log.d("o.i0", "查找并点击允许按钮完成");
                           com.guard.wallet.helper.g.h(80);
                           if (Objects.equals(var6.r.get(), var3)) {
                              var68 = var6.v;
                              break label577;
                           }
                        } catch (Exception var65) {
                           var10000 = var65;
                           boolean var112 = false;
                           break label615;
                        }

                        try {
                           var68 = var6.w;
                        } catch (Exception var64) {
                           var10000 = var64;
                           boolean var113 = false;
                           break label615;
                        }
                     }

                     try {
                        var68.set(true);
                        break label628;
                     } catch (Exception var63) {
                        var10000 = var63;
                        boolean var114 = false;
                        break label615;
                     }
                  }

                  try {
                     Log.e("o.i0", "查找并点击允许按钮失败");
                  } catch (Exception var62) {
                     var10000 = var62;
                     boolean var115 = false;
                     break label615;
                  }
               }

               try {
                  var6.z0();
                  return;
               } catch (Exception var61) {
                  var10000 = var61;
                  boolean var116 = false;
               }
            }

            Exception var69 = var10000;
            a1.q.s("o.i0", var69);
      }
   }
}
