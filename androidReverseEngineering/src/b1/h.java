package b1;

import android.util.Log;
import java.io.Closeable;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Timer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public final class h implements Closeable {
   public final d a;
   public final int b;
   public volatile int c;
   public final AtomicBoolean d;
   public final ConcurrentLinkedQueue e;
   public final ByteBuffer f;
   public volatile boolean g;
   public Timer h;
   public final LinkedList i = new LinkedList();
   public final LinkedList j = new LinkedList();
   public final AtomicInteger k = new AtomicInteger(-1);

   public h(d var1, int var2) {
      this.a = var1;
      this.b = var2;
      this.e = new ConcurrentLinkedQueue();
      if (var1.k) {
         var1.B(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
         this.f = (ByteBuffer)((Buffer)ByteBuffer.allocate(var1.o)).flip();
         this.d = new AtomicBoolean(false);
         this.g = false;
      } else {
         throw new IllegalStateException("connect() must be called first");
      }
   }

   public final void A(boolean param1) {
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
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:112)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.getFinallyInformation(FinallyProcessor.java:136)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:85)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: iload 1
      // 01: ifeq 11
      // 04: aload 0
      // 05: getfield b1/h.e Ljava/util/concurrent/ConcurrentLinkedQueue;
      // 08: invokevirtual java/util/concurrent/ConcurrentLinkedQueue.isEmpty ()Z
      // 0b: ifne 11
      // 0e: goto 16
      // 11: aload 0
      // 12: bipush 1
      // 13: putfield b1/h.g Z
      // 16: aload 0
      // 17: invokevirtual b1/h.y ()V
      // 1a: aload 0
      // 1b: monitorenter
      // 1c: aload 0
      // 1d: invokevirtual java/lang/Object.notifyAll ()V
      // 20: aload 0
      // 21: monitorexit
      // 22: aload 0
      // 23: getfield b1/h.e Ljava/util/concurrent/ConcurrentLinkedQueue;
      // 26: astore 2
      // 27: aload 2
      // 28: monitorenter
      // 29: aload 0
      // 2a: getfield b1/h.e Ljava/util/concurrent/ConcurrentLinkedQueue;
      // 2d: invokevirtual java/lang/Object.notifyAll ()V
      // 30: aload 2
      // 31: monitorexit
      // 32: return
      // 33: astore 3
      // 34: aload 2
      // 35: monitorexit
      // 36: aload 3
      // 37: athrow
      // 38: astore 2
      // 39: aload 0
      // 3a: monitorexit
      // 3b: aload 2
      // 3c: athrow
   }

   public final void B(long var1) {
      if (var1 > 0L) {
         com.guard.wallet.thread.d var3 = new com.guard.wallet.thread.d(this, 3);
         Timer var4 = new Timer();
         this.h = var4;
         var4.schedule(var3, var1);
      }
   }

   @Override
   public final void close() {
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
      // 00: aload 0
      // 01: monitorenter
      // 02: aload 0
      // 03: getfield b1/h.g Z
      // 06: ifeq 0c
      // 09: aload 0
      // 0a: monitorexit
      // 0b: return
      // 0c: aload 0
      // 0d: bipush 0
      // 0e: invokevirtual b1/h.A (Z)V
      // 11: aload 0
      // 12: monitorexit
      // 13: aload 0
      // 14: getfield b1/h.a Lb1/d;
      // 17: ldc 1163086915
      // 19: aload 0
      // 1a: getfield b1/h.b I
      // 1d: aconst_null
      // 1e: aload 0
      // 1f: getfield b1/h.c I
      // 22: invokestatic b1/g.b (II[BI)[B
      // 25: invokevirtual b1/d.A ([B)V
      // 28: aload 0
      // 29: invokevirtual b1/h.z ()V
      // 2c: return
      // 2d: astore 1
      // 2e: aload 0
      // 2f: monitorexit
      // 30: aload 1
      // 31: athrow
   }

   @Override
   public final void finalize() {
      this.close();
      super.finalize();
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final void x(byte[] var1) {
      ConcurrentLinkedQueue var6 = this.e;
      synchronized (var6){} // $VF: monitorenter 

      Throwable var10000;
      label1209: {
         boolean var5;
         try {
            this.e.add(var1);
            this.e.notifyAll();
            var5 = this.i.isEmpty();
         } catch (Throwable var117) {
            var10000 = var117;
            boolean var10001 = false;
            break label1209;
         }

         boolean var2;
         byte var4;
         label1201: {
            label1200: {
               var4 = 0;
               if (var5) {
                  try {
                     if (this.j.isEmpty()) {
                        break label1200;
                     }
                  } catch (Throwable var116) {
                     var10000 = var116;
                     boolean var129 = false;
                     break label1209;
                  }
               }

               var2 = 1;
               break label1201;
            }

            var2 = 0;
         }

         label1192:
         if (var2) {
            label1190: {
               label1189: {
                  int var3;
                  label1188: {
                     label1211: {
                        Iterator var126;
                        try {
                           String var7 = new String(var1, StandardCharsets.UTF_8);
                           if (a1.q.B(var7) || var7.equals("shell")) {
                              break label1192;
                           }

                           var118 = var7.replace("\n", "");
                           Log.d("addPayload", var118);
                           if (this.k.get() == 0 || this.k.get() == 1) {
                              break label1190;
                           }

                           if (a1.q.B(var118)) {
                              break label1211;
                           }

                           LinkedList var125 = this.i;
                           if (var125.isEmpty()) {
                              break label1211;
                           }

                           var126 = var125.iterator();
                        } catch (Throwable var115) {
                           var10000 = var115;
                           boolean var130 = false;
                           break label1209;
                        }

                        byte var120 = -1;

                        while (true) {
                           var3 = var120;

                           try {
                              if (!var126.hasNext()) {
                                 break label1188;
                              }

                              var3 = ((i.a)((i.b)var126.next())).a(var118);
                           } catch (Throwable var114) {
                              var10000 = var114;
                              boolean var131 = false;
                              break label1209;
                           }

                           if (var3 == 1) {
                              var2 = var3;
                              break label1189;
                           }

                           if (var3 == 2 && var120 == -1) {
                              var120 = 3;
                           }
                        }
                     }

                     var3 = -1;
                  }

                  var2 = var3;
               }

               if (var2 != -1) {
                  try {
                     this.k.set(var2);
                  } catch (Throwable var111) {
                     var10000 = var111;
                     boolean var132 = false;
                     break label1209;
                  }
               }
            }

            label1157: {
               label1214: {
                  Iterator var128;
                  try {
                     if (this.k.get() == 0 || this.k.get() == 1 || this.k.get() == 3) {
                        break label1192;
                     }

                     if (a1.q.B(var118)) {
                        break label1214;
                     }

                     LinkedList var127 = this.j;
                     if (var127.isEmpty()) {
                        break label1214;
                     }

                     var128 = var127.iterator();
                  } catch (Throwable var113) {
                     var10000 = var113;
                     boolean var133 = false;
                     break label1209;
                  }

                  var122 = -1;

                  while (true) {
                     int var124;
                     try {
                        if (!var128.hasNext()) {
                           break label1157;
                        }

                        var124 = ((i.a)((i.b)var128.next())).a(var118);
                     } catch (Throwable var112) {
                        var10000 = var112;
                        boolean var134 = false;
                        break label1209;
                     }

                     if (var124 == 1) {
                        var122 = var4;
                        break label1157;
                     }

                     if (var124 == 2 && var122 == -1) {
                        var122 = 4;
                     }
                  }
               }

               var122 = -1;
            }

            if (var122 != -1) {
               try {
                  this.k.set(var122);
               } catch (Throwable var110) {
                  var10000 = var110;
                  boolean var135 = false;
                  break label1209;
               }
            }
         }

         label1127:
         try {
            // $VF: monitorexit
            return;
         } catch (Throwable var109) {
            var10000 = var109;
            boolean var136 = false;
            break label1127;
         }
      }

      while (true) {
         Throwable var119 = var10000;

         try {
            // $VF: monitorexit
            throw var119;
         } catch (Throwable var108) {
            var10000 = var108;
            boolean var137 = false;
            continue;
         }
      }
   }

   public final void y() {
      AtomicInteger var1 = this.k;
      if (var1.get() == 3) {
         var1.set(1);
      }

      if (var1.get() == 4) {
         var1.set(0);
      }

      if (var1.get() == -1) {
         var1.set(5);
      }

      Timer var3 = this.h;
      if (var3 != null) {
         var3.cancel();
         this.h = null;
      }

      try {
         this.close();
      } catch (Exception var2) {
         a1.q.s("AdbStream", var2);
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final void z() {
      LinkedList var1 = this.i;

      Exception var10000;
      label41: {
         try {
            if (!var1.isEmpty()) {
               var1.clear();
            }
         } catch (Exception var4) {
            var10000 = var4;
            boolean var10001 = false;
            break label41;
         }

         var1 = this.j;

         try {
            if (!var1.isEmpty()) {
               var1.clear();
            }
         } catch (Exception var3) {
            var10000 = var3;
            boolean var7 = false;
            break label41;
         }

         try {
            this.e.clear();
            ((Buffer)this.f).clear();
            return;
         } catch (Exception var2) {
            var10000 = var2;
            boolean var8 = false;
         }
      }

      Exception var6 = var10000;
      a1.q.s("AdbStream", var6);
   }
}
