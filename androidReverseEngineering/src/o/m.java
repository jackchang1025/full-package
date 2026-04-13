package o;

import android.util.Log;
import com.guard.wallet.entity.UiObject;
import java.util.Objects;

// $VF: synthetic class
public final class m implements Runnable {
   public final int a;
   public final n b;

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final void run() {
      int var1 = this.a;
      boolean var4 = false;
      n var6 = this.b;
      switch (var1) {
         case 0:
            var6.getClass();

            Exception var71;
            label364: {
               UiObject var69;
               try {
                  if (!var6.j0()) {
                     return;
                  }

                  Log.d("o.n", "keepAliveInHwSettings 窗口匹配");
                  com.guard.wallet.helper.g.h(10);
                  var6.G();
                  Log.d("o.n", "active root complete");
                  var69 = var6.Q();
               } catch (Exception var42) {
                  var71 = var42;
                  boolean var98 = false;
                  break label364;
               }

               String var63;
               if (var69 != null) {
                  z.d var68;
                  try {
                     Log.d("o.n", "查找华为系统设置滚动视图成功");
                     com.guard.wallet.helper.g.h(15);
                     var68 = new z.d(n.e0(), 0);
                     var66 = var69.scrollForwardUtil(var68);
                  } catch (Exception var41) {
                     var71 = var41;
                     boolean var99 = false;
                     break label364;
                  }

                  UiObject var61 = var66;
                  if (var66 == null) {
                     try {
                        var61 = var69.scrollBackwardUtil(var68);
                     } catch (Exception var40) {
                        var71 = var40;
                        boolean var100 = false;
                        break label364;
                     }
                  }

                  UiObject var67 = var61;
                  if (var61 == null) {
                     try {
                        var67 = var69.scrollForwardUtil(var68);
                     } catch (Exception var39) {
                        var71 = var39;
                        boolean var101 = false;
                        break label364;
                     }
                  }

                  if (var67 != null) {
                     try {
                        com.guard.wallet.helper.g.h(20);
                        var62 = var67.findParentUtilCombine(c.K());
                     } catch (Exception var38) {
                        var71 = var38;
                        boolean var102 = false;
                        break label364;
                     }

                     if (var62 != null) {
                        try {
                           if (var62.click()) {
                              Log.d("o.n", "已点击进入应用和服务栏目");
                              com.guard.wallet.helper.g.h(25);
                              return;
                           }
                        } catch (Exception var37) {
                           var71 = var37;
                           boolean var103 = false;
                           break label364;
                        }
                     }

                     var63 = "点击进入应用和服务栏目失败";
                  } else {
                     var63 = "查找应用和服务栏目栏目失败";
                  }
               } else {
                  var63 = "查找华为系统设置滚动视图失败";
               }

               try {
                  Log.e("o.n", var63);
                  return;
               } catch (Exception var36) {
                  var71 = var36;
                  boolean var104 = false;
               }
            }

            Exception var64 = var71;
            a1.q.s("o.n", var64);
            return;
         case 1:
            var6.getClass();

            Exception var70;
            label361: {
               UiObject var7;
               try {
                  if (!var6.i0()) {
                     return;
                  }

                  Log.d("o.n", "keepAliveInAppAndNotification 窗口匹配");
                  com.guard.wallet.helper.g.h(30);
                  var6.G();
                  Log.d("o.n", "active root complete");
                  var7 = var6.Q();
               } catch (Exception var35) {
                  var70 = var35;
                  boolean var91 = false;
                  break label361;
               }

               UiObject var57;
               if (var7 != null) {
                  z.d var8;
                  try {
                     Log.d("o.n", "应用和服务窗口滚动视图查找成功");
                     com.guard.wallet.helper.g.h(35);
                     var8 = new z.d(n.g0(), 0);
                     var65 = var7.scrollForwardUtil(var8);
                  } catch (Exception var34) {
                     var70 = var34;
                     boolean var92 = false;
                     break label361;
                  }

                  var57 = var65;
                  if (var65 == null) {
                     try {
                        var57 = var7.scrollBackwardUtil(var8);
                     } catch (Exception var33) {
                        var70 = var33;
                        boolean var93 = false;
                        break label361;
                     }
                  }
               } else {
                  try {
                     Log.e("o.n", "应用和服务窗口滚动视图查找失败");
                     var57 = var6.k().findOneByCombine(n.g0());
                  } catch (Exception var32) {
                     var70 = var32;
                     boolean var94 = false;
                     break label361;
                  }
               }

               String var59;
               if (var57 != null) {
                  try {
                     Log.d("o.n", "应用启动管理栏目查找成功");
                     com.guard.wallet.helper.g.h(40);
                     var57 = var57.findParentUtilCombine(c.K());
                  } catch (Exception var31) {
                     var70 = var31;
                     boolean var95 = false;
                     break label361;
                  }

                  if (var57 != null) {
                     try {
                        if (var57.click()) {
                           Log.d("o.n", "点击应用启动管理栏目完成");
                           com.guard.wallet.helper.g.h(45);
                           return;
                        }
                     } catch (Exception var30) {
                        var70 = var30;
                        boolean var96 = false;
                        break label361;
                     }
                  }

                  var59 = "点击应用启动管理栏目失败";
               } else {
                  var59 = "应用启动管理栏目查找失败";
               }

               try {
                  Log.e("o.n", var59);
                  return;
               } catch (Exception var29) {
                  var70 = var29;
                  boolean var97 = false;
               }
            }

            Exception var60 = var70;
            a1.q.s("o.n", var60);
            return;
         case 2:
            var6.r0();
            return;
         case 3:
            var6.getClass();

            Exception var10000;
            label348: {
               UiObject var5;
               try {
                  if (!var6.h0()) {
                     return;
                  }

                  Log.d("o.n", "keepAliveInAlertDialog 窗口匹配");
                  com.guard.wallet.helper.g.h(70);
                  var6.G();
                  Log.d("o.n", "active root complete");
                  var5 = var6.k().findOneByCombine(n.b0());
               } catch (Exception var28) {
                  var10000 = var28;
                  boolean var10001 = false;
                  break label348;
               }

               boolean var2;
               label281: {
                  label368: {
                     String var43;
                     if (var5 != null) {
                        try {
                           Log.d("o.n", "自启动节点查找成功");
                           var5 = var5.findParentUtilCombine(c.U());
                        } catch (Exception var26) {
                           var10000 = var26;
                           boolean var72 = false;
                           break label348;
                        }

                        if (var5 != null) {
                           try {
                              Log.d("o.n", "自启动栏目查找成功");
                              var45 = var6.R(var5, 5);
                              if (var45.isClicked()) {
                                 Log.d("o.n", "已点击允许自启动");
                              }
                           } catch (Exception var25) {
                              var10000 = var25;
                              boolean var73 = false;
                              break label348;
                           }

                           try {
                              if (var45.isChecked()) {
                                 Log.d("o.n", "已勾选允许自启动");
                                 com.guard.wallet.helper.g.h(75);
                                 break label368;
                              }
                           } catch (Exception var27) {
                              var10000 = var27;
                              boolean var74 = false;
                              break label348;
                           }

                           var43 = "未勾选允许自启动";
                        } else {
                           var43 = "自启动栏目查找失败";
                        }
                     } else {
                        var43 = "自启动节点查找失败";
                     }

                     try {
                        Log.e("o.n", var43);
                     } catch (Exception var24) {
                        var10000 = var24;
                        boolean var75 = false;
                        break label348;
                     }

                     var2 = false;
                     break label281;
                  }

                  var2 = true;
               }

               try {
                  var5 = var6.k().findOneByCombine(n.d0());
               } catch (Exception var23) {
                  var10000 = var23;
                  boolean var76 = false;
                  break label348;
               }

               boolean var3;
               label255: {
                  label369: {
                     String var47;
                     if (var5 != null) {
                        try {
                           Log.d("o.n", "关联启动节点查找成功");
                           var5 = var5.findParentUtilCombine(c.U());
                        } catch (Exception var21) {
                           var10000 = var21;
                           boolean var77 = false;
                           break label348;
                        }

                        if (var5 != null) {
                           try {
                              Log.d("o.n", "关联启动栏目查找成功");
                              var49 = var6.R(var5, 5);
                              if (var49.isClicked()) {
                                 Log.d("o.n", "已点击允许关联启动");
                              }
                           } catch (Exception var20) {
                              var10000 = var20;
                              boolean var78 = false;
                              break label348;
                           }

                           try {
                              if (var49.isChecked()) {
                                 Log.d("o.n", "已勾选允许关联启动");
                                 com.guard.wallet.helper.g.h(80);
                                 break label369;
                              }
                           } catch (Exception var22) {
                              var10000 = var22;
                              boolean var79 = false;
                              break label348;
                           }

                           var47 = "未勾选允许关联启动";
                        } else {
                           var47 = "关联启动栏目查找失败";
                        }
                     } else {
                        var47 = "关联启动节点查找失败";
                     }

                     try {
                        Log.e("o.n", var47);
                     } catch (Exception var19) {
                        var10000 = var19;
                        boolean var80 = false;
                        break label348;
                     }

                     var3 = false;
                     break label255;
                  }

                  var3 = true;
               }

               try {
                  var5 = var6.k().findOneByCombine(n.c0());
               } catch (Exception var18) {
                  var10000 = var18;
                  boolean var81 = false;
                  break label348;
               }

               label229: {
                  label370: {
                     String var51;
                     if (var5 != null) {
                        try {
                           Log.d("o.n", "允许后台活动节点查找成功");
                           var5 = var5.findParentUtilCombine(c.U());
                        } catch (Exception var16) {
                           var10000 = var16;
                           boolean var82 = false;
                           break label348;
                        }

                        if (var5 != null) {
                           try {
                              Log.d("o.n", "允许后台活动栏目查找成功");
                              var53 = var6.R(var5, 5);
                              if (var53.isClicked()) {
                                 Log.d("o.n", "已点击允许后台活动");
                              }
                           } catch (Exception var15) {
                              var10000 = var15;
                              boolean var83 = false;
                              break label348;
                           }

                           try {
                              if (var53.isChecked()) {
                                 Log.d("o.n", "已勾选允许后台活动");
                                 com.guard.wallet.helper.g.h(85);
                                 break label370;
                              }
                           } catch (Exception var17) {
                              var10000 = var17;
                              boolean var84 = false;
                              break label348;
                           }

                           var51 = "未勾选允许后台活动";
                        } else {
                           var51 = "允许后台活动栏目查找失败";
                        }
                     } else {
                        var51 = "允许后台活动节点查找失败";
                     }

                     try {
                        Log.e("o.n", var51);
                        break label229;
                     } catch (Exception var14) {
                        var10000 = var14;
                        boolean var85 = false;
                        break label348;
                     }
                  }

                  var4 = true;
               }

               try {
                  var5 = var6.k().findOneByCombine(n.l0());
               } catch (Exception var13) {
                  var10000 = var13;
                  boolean var86 = false;
                  break label348;
               }

               label371: {
                  label193:
                  if (var5 != null) {
                     label358: {
                        try {
                           if (!var5.click()) {
                              break label193;
                           }

                           Log.d("o.n", "查找并点击确认按钮完成");
                           com.guard.wallet.helper.g.h(90);
                           if (Objects.equals(var6.r.get(), r.e.c)) {
                              var6.s.set(var2);
                              var6.u.set(var3);
                              var6.w.set(var4);
                              break label358;
                           }
                        } catch (Exception var12) {
                           var10000 = var12;
                           boolean var87 = false;
                           break label348;
                        }

                        try {
                           var6.t.set(var2);
                           var6.v.set(var3);
                           var6.x.set(var4);
                        } catch (Exception var11) {
                           var10000 = var11;
                           boolean var88 = false;
                           break label348;
                        }

                        var55 = "更新备用进程保活策略";
                        break label371;
                     }

                     var55 = "更新主进程保活策略";
                     break label371;
                  }

                  try {
                     Log.e("o.n", "查找并点击确认按钮失败");
                     return;
                  } catch (Exception var10) {
                     var10000 = var10;
                     boolean var90 = false;
                     break label348;
                  }
               }

               try {
                  Log.d("o.n", var55);
                  return;
               } catch (Exception var9) {
                  var10000 = var9;
                  boolean var89 = false;
               }
            }

            Exception var56 = var10000;
            a1.q.s("o.n", var56);
            return;
         default:
            var6.Z();
      }
   }
}
