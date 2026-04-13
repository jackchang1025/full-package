package o;

import android.util.Log;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.filter.CombineFilterWithChild;
import java.util.Objects;

// $VF: synthetic class
public final class u implements Runnable {
   public final int a;
   public final v b;

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final void run() {
      int var1 = this.a;
      v var4 = this.b;
      switch (var1) {
         case 0:
            var4.getClass();

            Exception var54;
            label288: {
               CombineFilter var6;
               UiObject var7;
               CombineFilter var50;
               try {
                  if (!var4.k0()) {
                     return;
                  }

                  Log.d("o.v", "keepAliveInAppDetail 窗口匹配");
                  com.guard.wallet.helper.g.h(10);
                  var4.G();
                  Log.d("o.v", "active root complete");
                  var7 = var4.Q();
                  var6 = v.B0();
                  var50 = v.C0();
               } catch (Exception var35) {
                  var54 = var35;
                  boolean var71 = false;
                  break label288;
               }

               UiObject var44 = null;
               UiObject var47 = null;
               if (var7 != null) {
                  try {
                     Log.d("o.v", "应用详情窗口滚动视图查找成功");
                     com.guard.wallet.helper.g.h(15);
                  } catch (Exception var34) {
                     var54 = var34;
                     boolean var72 = false;
                     break label288;
                  }

                  if (var6 != null) {
                     try {
                        var45 = new z.d(var6, 0);
                        var47 = var7.scrollForwardUtil(var45);
                     } catch (Exception var33) {
                        var54 = var33;
                        boolean var73 = false;
                        break label288;
                     }

                     if (var47 == null) {
                        try {
                           var47 = var7.scrollBackwardUtil(var45);
                        } catch (Exception var32) {
                           var54 = var32;
                           boolean var74 = false;
                           break label288;
                        }
                     }
                  }

                  var44 = var47;
                  if (var47 == null) {
                     var44 = var47;
                     if (var50 != null) {
                        try {
                           var48 = new z.d(var50, 0);
                           var44 = var7.scrollBackwardUtil(var48);
                        } catch (Exception var31) {
                           var54 = var31;
                           boolean var75 = false;
                           break label288;
                        }

                        if (var44 == null) {
                           try {
                              var44 = var7.scrollForwardUtil(var48);
                           } catch (Exception var30) {
                              var54 = var30;
                              boolean var76 = false;
                              break label288;
                           }
                        }
                     }
                  }
               }

               var47 = var44;
               label252:
               if (var44 == null) {
                  var47 = var44;

                  try {
                     if (var4.k() == null) {
                        break label252;
                     }

                     Log.e("o.v", "应用详情窗口滚动视图查找失败");
                  } catch (Exception var29) {
                     var54 = var29;
                     boolean var77 = false;
                     break label288;
                  }

                  if (var6 != null) {
                     try {
                        var44 = var4.k().findOneByCombine(var6);
                     } catch (Exception var28) {
                        var54 = var28;
                        boolean var78 = false;
                        break label288;
                     }
                  }

                  var47 = var44;
                  if (var44 == null) {
                     var47 = var44;
                     if (var50 != null) {
                        try {
                           var47 = var4.k().findOneByCombine(var50);
                        } catch (Exception var27) {
                           var54 = var27;
                           boolean var79 = false;
                           break label288;
                        }
                     }
                  }
               }

               if (var47 != null) {
                  try {
                     if (var47.click()) {
                        Log.d("o.v", "查找并点击耗电管理栏目成功");
                        com.guard.wallet.helper.g.h(30);
                        return;
                     }
                  } catch (Exception var26) {
                     var54 = var26;
                     boolean var80 = false;
                     break label288;
                  }
               }

               try {
                  Log.e("o.v", "查找并点击耗电管理栏目失败");
                  return;
               } catch (Exception var25) {
                  var54 = var25;
                  boolean var81 = false;
               }
            }

            Exception var46 = var54;
            a1.q.s("o.v", var46);
            return;
         case 1:
            var4.getClass();

            Exception var53;
            label285: {
               try {
                  if (!var4.l0()) {
                     return;
                  }

                  Log.d("o.v", "keepAliveInPowerControl 窗口匹配");
                  com.guard.wallet.helper.g.h(40);
                  var4.G();
                  Log.d("o.v", "active root complete");
                  if (!var4.s0()) {
                     Log.e("o.v", "允许自启动行为失败");
                  }
               } catch (Exception var23) {
                  var53 = var23;
                  boolean var67 = false;
                  break label285;
               }

               try {
                  com.guard.wallet.helper.g.h(50);
                  if (!var4.t0()) {
                     Log.e("o.v", "允许关联启动行为失败");
                  }
               } catch (Exception var22) {
                  var53 = var22;
                  boolean var68 = false;
                  break label285;
               }

               try {
                  com.guard.wallet.helper.g.h(60);
                  if (!var4.r0()) {
                     Log.e("o.v", "允许完全后台行为失败");
                     return;
                  }
               } catch (Exception var24) {
                  var53 = var24;
                  boolean var69 = false;
                  break label285;
               }

               try {
                  com.guard.wallet.helper.g.h(70);
                  var4.u0();
                  return;
               } catch (Exception var21) {
                  var53 = var21;
                  boolean var70 = false;
               }
            }

            Exception var43 = var53;
            a1.q.s("o.v", var43);
            return;
         case 2:
            var4.getClass();

            Exception var51;
            label282: {
               try {
                  if (!var4.j0()) {
                     return;
                  }

                  Log.d("o.v", "checkInAndroidXDialog 窗口匹配");
                  com.guard.wallet.helper.g.h(80);
                  var4.G();
                  Log.d("o.v", "active root complete");
               } catch (Exception var20) {
                  var51 = var20;
                  boolean var62 = false;
                  break label282;
               }

               label283: {
                  UiObject var40;
                  try {
                     var40 = var4.k().findOneByCombineLoop(v.d0());
                  } catch (Exception var19) {
                     var51 = var19;
                     boolean var63 = false;
                     break label283;
                  }

                  if (var40 != null) {
                     try {
                        if (var40.click()) {
                           Log.d("o.v", "查找并点击允许确认按钮完成");
                           com.guard.wallet.helper.g.h(90);
                           return;
                        }
                     } catch (Exception var18) {
                        var51 = var18;
                        boolean var64 = false;
                        break label283;
                     }
                  }

                  try {
                     Log.e("o.v", "查找并点击允许确认按钮失败");
                     return;
                  } catch (Exception var17) {
                     var51 = var17;
                     boolean var65 = false;
                  }
               }

               Exception var41 = var51;

               try {
                  a1.q.s("o.v", var41);
                  return;
               } catch (Exception var16) {
                  var51 = var16;
                  boolean var66 = false;
               }
            }

            Exception var42 = var51;
            a1.q.s("o.v", var42);
            return;
         case 3:
            var4.getClass();

            Exception var10000;
            label291: {
               String var2;
               label177: {
                  try {
                     if (!var4.m0()) {
                        return;
                     }

                     var4.G();
                     Log.d("o.v", "active root complete");
                     if (Objects.equals(var4.r.get(), r.e.c)) {
                        var2 = com.guard.wallet.utils.g.x0();
                        break label177;
                     }
                  } catch (Exception var15) {
                     var10000 = var15;
                     boolean var10001 = false;
                     break label291;
                  }

                  try {
                     var2 = com.guard.wallet.utils.g.e();
                  } catch (Exception var13) {
                     var10000 = var13;
                     boolean var55 = false;
                     break label291;
                  }
               }

               CombineFilterWithChild var3;
               UiObject var5;
               try {
                  Log.d("o.v", "keepAliveInStartup 窗口匹配");
                  var5 = var4.Q();
                  var3 = new CombineFilterWithChild(c.K(), c.H(var2));
               } catch (Exception var12) {
                  var10000 = var12;
                  boolean var56 = false;
                  break label291;
               }

               UiObject var37;
               if (var5 != null) {
                  try {
                     z.c var36 = new z.c(var3, 0);
                     var37 = var5.scrollForwardUtil(var36);
                  } catch (Exception var11) {
                     var10000 = var11;
                     boolean var57 = false;
                     break label291;
                  }
               } else {
                  try {
                     var37 = var4.k().findOneByCombineWithChild(var3);
                  } catch (Exception var10) {
                     var10000 = var10;
                     boolean var58 = false;
                     break label291;
                  }
               }

               if (var37 == null) {
                  return;
               }

               try {
                  var38 = var4.R(var37, 5);
                  if (var38.isClicked()) {
                     Log.d("o.v", "已点击自启动");
                  }
               } catch (Exception var9) {
                  var10000 = var9;
                  boolean var59 = false;
                  break label291;
               }

               try {
                  if (var38.isChecked()) {
                     Log.d("o.v", "已勾选自启动");
                     var4.t.set(true);
                     return;
                  }
               } catch (Exception var14) {
                  var10000 = var14;
                  boolean var60 = false;
                  break label291;
               }

               try {
                  Log.e("o.v", "未勾选自启动");
                  return;
               } catch (Exception var8) {
                  var10000 = var8;
                  boolean var61 = false;
               }
            }

            Exception var39 = var10000;
            a1.q.s("o.v", var39);
            return;
         default:
            var4.Z();
      }
   }
}
