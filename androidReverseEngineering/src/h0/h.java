package h0;

import f0.b0;
import f0.t;
import java.util.Iterator;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class h extends d implements Future {
   public static final int i = 0;
   public com.guard.wallet.http.h d;
   public Exception e;
   public Object f;
   public boolean g;
   public g h;

   public h() {
   }

   public h(Object var1) {
      this.g(null, var1, null);
   }

   public final Object c() {
      if (this.e == null) {
         return this.f;
      } else {
         throw new ExecutionException(this.e);
      }
   }

   @Override
   public final boolean cancel() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield h0/h.g Z
      // 04: istore 1
      // 05: aload 0
      // 06: invokespecial h0/d.cancel ()Z
      // 09: ifne 11
      // 0c: bipush 0
      // 0d: istore 1
      // 0e: goto 3d
      // 11: aload 0
      // 12: monitorenter
      // 13: new java/util/concurrent/CancellationException
      // 16: astore 2
      // 17: aload 2
      // 18: invokespecial java/util/concurrent/CancellationException.<init> ()V
      // 1b: aload 0
      // 1c: aload 2
      // 1d: putfield h0/h.e Ljava/lang/Exception;
      // 20: aload 0
      // 21: invokevirtual h0/h.e ()V
      // 24: aload 0
      // 25: getfield h0/h.h Lh0/g;
      // 28: astore 2
      // 29: aload 0
      // 2a: aconst_null
      // 2b: putfield h0/h.h Lh0/g;
      // 2e: aload 0
      // 2f: iload 1
      // 30: putfield h0/h.g Z
      // 33: aload 0
      // 34: monitorexit
      // 35: aload 0
      // 36: aconst_null
      // 37: aload 2
      // 38: invokevirtual h0/h.d (Lf0/t;Lh0/g;)V
      // 3b: bipush 1
      // 3c: istore 1
      // 3d: iload 1
      // 3e: ireturn
      // 3f: astore 2
      // 40: aload 0
      // 41: monitorexit
      // 42: aload 2
      // 43: athrow
   }

   public final void d(t var1, g var2) {
      if (!this.g) {
         if (var2 != null) {
            boolean var3;
            if (var1 == null) {
               var1 = new t(2);
               var3 = true;
            } else {
               var3 = false;
            }

            var1.g = var2;
            var1.f = this.e;
            var1.e = this.f;
            if (var3) {
               while (true) {
                  var2 = (g)var1.g;
                  if (var2 == null) {
                     break;
                  }

                  Exception var4 = (Exception)var1.f;
                  Object var5 = var1.e;
                  var1.g = null;
                  var1.f = null;
                  var1.e = null;
                  var2.b(var4, var5, var1);
               }
            }
         }
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final void e() {
      com.guard.wallet.http.h var3 = this.d;
      if (var3 != null) {
         ((Semaphore)var3.e).release();
         WeakHashMap var1 = b0.c;
         synchronized (var1){} // $VF: monitorenter 

         label206: {
            Throwable var10000;
            label199: {
               Iterator var2;
               try {
                  var2 = var1.values().iterator();
               } catch (Throwable var23) {
                  var10000 = var23;
                  boolean var10001 = false;
                  break label199;
               }

               while (true) {
                  try {
                     while (var2.hasNext()) {
                        b0 var4 = (b0)var2.next();
                        if (var4.a == var3) {
                           var4.b.release();
                        }
                     }
                  } catch (Throwable var24) {
                     var10000 = var24;
                     boolean var26 = false;
                     break;
                  }

                  try {
                     // $VF: monitorexit
                     break label206;
                  } catch (Throwable var22) {
                     var10000 = var22;
                     boolean var27 = false;
                     break;
                  }
               }
            }

            while (true) {
               Throwable var25 = var10000;

               try {
                  // $VF: monitorexit
                  throw var25;
               } catch (Throwable var21) {
                  var10000 = var21;
                  boolean var28 = false;
                  continue;
               }
            }
         }

         this.d = null;
      }
   }

   public final void f(t param1, g param2) {
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
      // 03: aload 2
      // 04: putfield h0/h.h Lh0/g;
      // 07: aload 0
      // 08: getfield h0/d.a Z
      // 0b: ifne 18
      // 0e: aload 0
      // 0f: invokevirtual h0/d.isCancelled ()Z
      // 12: ifne 18
      // 15: aload 0
      // 16: monitorexit
      // 17: return
      // 18: aload 0
      // 19: getfield h0/h.h Lh0/g;
      // 1c: astore 2
      // 1d: aload 0
      // 1e: aconst_null
      // 1f: putfield h0/h.h Lh0/g;
      // 22: aload 0
      // 23: monitorexit
      // 24: aload 0
      // 25: aload 1
      // 26: aload 2
      // 27: invokevirtual h0/h.d (Lf0/t;Lh0/g;)V
      // 2a: return
      // 2b: astore 1
      // 2c: aload 0
      // 2d: monitorexit
      // 2e: aload 1
      // 2f: athrow
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final boolean g(Exception var1, Object var2, t var3) {
      synchronized (this){} // $VF: monitorenter 

      Throwable var10000;
      label104: {
         try {
            if (!this.b()) {
               // $VF: monitorexit
               return false;
            }
         } catch (Throwable var15) {
            var10000 = var15;
            boolean var10001 = false;
            break label104;
         }

         try {
            this.f = var2;
            this.e = var1;
            this.e();
            var17 = this.h;
            this.h = null;
            // $VF: monitorexit
         } catch (Throwable var14) {
            var10000 = var14;
            boolean var18 = false;
            break label104;
         }

         this.d(var3, var17);
         return true;
      }

      while (true) {
         Throwable var16 = var10000;

         try {
            // $VF: monitorexit
            throw var16;
         } catch (Throwable var13) {
            var10000 = var13;
            boolean var19 = false;
            continue;
         }
      }
   }

   @Override
   public final Object get() {
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
      // 03: invokevirtual h0/d.isCancelled ()Z
      // 06: ifne d7
      // 09: aload 0
      // 0a: getfield h0/d.a Z
      // 0d: ifeq 13
      // 10: goto d7
      // 13: aload 0
      // 14: getfield h0/h.d Lcom/guard/wallet/http/h;
      // 17: ifnonnull 28
      // 1a: new com/guard/wallet/http/h
      // 1d: astore 2
      // 1e: aload 2
      // 1f: bipush 1
      // 20: invokespecial com/guard/wallet/http/h.<init> (I)V
      // 23: aload 0
      // 24: aload 2
      // 25: putfield h0/h.d Lcom/guard/wallet/http/h;
      // 28: aload 0
      // 29: getfield h0/h.d Lcom/guard/wallet/http/h;
      // 2c: astore 4
      // 2e: aload 0
      // 2f: monitorexit
      // 30: aload 4
      // 32: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 35: pop
      // 36: invokestatic java/lang/Thread.currentThread ()Ljava/lang/Thread;
      // 39: astore 6
      // 3b: getstatic f0/b0.c Ljava/util/WeakHashMap;
      // 3e: astore 5
      // 40: aload 5
      // 42: monitorenter
      // 43: aload 5
      // 45: aload 6
      // 47: invokevirtual java/util/WeakHashMap.get (Ljava/lang/Object;)Ljava/lang/Object;
      // 4a: checkcast f0/b0
      // 4d: astore 3
      // 4e: aload 3
      // 4f: astore 2
      // 50: aload 3
      // 51: ifnonnull 65
      // 54: new f0/b0
      // 57: astore 2
      // 58: aload 2
      // 59: invokespecial f0/b0.<init> ()V
      // 5c: aload 5
      // 5e: aload 6
      // 60: aload 2
      // 61: invokevirtual java/util/WeakHashMap.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
      // 64: pop
      // 65: aload 5
      // 67: monitorexit
      // 68: aload 2
      // 69: getfield f0/b0.a Lcom/guard/wallet/http/h;
      // 6c: astore 3
      // 6d: aload 2
      // 6e: aload 4
      // 70: putfield f0/b0.a Lcom/guard/wallet/http/h;
      // 73: aload 2
      // 74: getfield f0/b0.b Ljava/util/concurrent/Semaphore;
      // 77: astore 6
      // 79: aload 4
      // 7b: getfield com/guard/wallet/http/h.e Ljava/lang/Object;
      // 7e: checkcast java/util/concurrent/Semaphore
      // 81: invokevirtual java/util/concurrent/Semaphore.tryAcquire ()Z
      // 84: ifeq 8a
      // 87: goto b3
      // 8a: aload 2
      // 8b: invokevirtual f0/b0.a ()Ljava/lang/Runnable;
      // 8e: astore 5
      // 90: aload 5
      // 92: ifnonnull bd
      // 95: aload 6
      // 97: bipush 1
      // 98: aload 6
      // 9a: invokevirtual java/util/concurrent/Semaphore.availablePermits ()I
      // 9d: invokestatic java/lang/Math.max (II)I
      // a0: invokevirtual java/util/concurrent/Semaphore.acquire (I)V
      // a3: aload 4
      // a5: getfield com/guard/wallet/http/h.e Ljava/lang/Object;
      // a8: checkcast java/util/concurrent/Semaphore
      // ab: invokevirtual java/util/concurrent/Semaphore.tryAcquire ()Z
      // ae: istore 1
      // af: iload 1
      // b0: ifeq 8a
      // b3: aload 2
      // b4: aload 3
      // b5: putfield f0/b0.a Lcom/guard/wallet/http/h;
      // b8: aload 0
      // b9: invokevirtual h0/h.c ()Ljava/lang/Object;
      // bc: areturn
      // bd: aload 5
      // bf: invokeinterface java/lang/Runnable.run ()V 1
      // c4: goto 8a
      // c7: astore 4
      // c9: aload 2
      // ca: aload 3
      // cb: putfield f0/b0.a Lcom/guard/wallet/http/h;
      // ce: aload 4
      // d0: athrow
      // d1: astore 2
      // d2: aload 5
      // d4: monitorexit
      // d5: aload 2
      // d6: athrow
      // d7: aload 0
      // d8: invokevirtual h0/h.c ()Ljava/lang/Object;
      // db: astore 2
      // dc: aload 0
      // dd: monitorexit
      // de: aload 2
      // df: areturn
      // e0: astore 2
      // e1: aload 0
      // e2: monitorexit
      // e3: aload 2
      // e4: athrow
   }

   @Override
   public final Object get(long param1, TimeUnit param3) {
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
      // 000: aload 0
      // 001: monitorenter
      // 002: aload 0
      // 003: invokevirtual h0/d.isCancelled ()Z
      // 006: ifne 12a
      // 009: aload 0
      // 00a: getfield h0/d.a Z
      // 00d: ifeq 013
      // 010: goto 12a
      // 013: aload 0
      // 014: getfield h0/h.d Lcom/guard/wallet/http/h;
      // 017: astore 10
      // 019: bipush 1
      // 01a: istore 4
      // 01c: aload 10
      // 01e: ifnonnull 032
      // 021: new com/guard/wallet/http/h
      // 024: astore 10
      // 026: aload 10
      // 028: bipush 1
      // 029: invokespecial com/guard/wallet/http/h.<init> (I)V
      // 02c: aload 0
      // 02d: aload 10
      // 02f: putfield h0/h.d Lcom/guard/wallet/http/h;
      // 032: aload 0
      // 033: getfield h0/h.d Lcom/guard/wallet/http/h;
      // 036: astore 11
      // 038: aload 0
      // 039: monitorexit
      // 03a: aload 11
      // 03c: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 03f: pop
      // 040: getstatic java/util/concurrent/TimeUnit.MILLISECONDS Ljava/util/concurrent/TimeUnit;
      // 043: lload 1
      // 044: aload 3
      // 045: invokevirtual java/util/concurrent/TimeUnit.convert (JLjava/util/concurrent/TimeUnit;)J
      // 048: lstore 7
      // 04a: invokestatic java/lang/Thread.currentThread ()Ljava/lang/Thread;
      // 04d: astore 13
      // 04f: getstatic f0/b0.c Ljava/util/WeakHashMap;
      // 052: astore 12
      // 054: aload 12
      // 056: monitorenter
      // 057: aload 12
      // 059: aload 13
      // 05b: invokevirtual java/util/WeakHashMap.get (Ljava/lang/Object;)Ljava/lang/Object;
      // 05e: checkcast f0/b0
      // 061: astore 10
      // 063: aload 10
      // 065: astore 3
      // 066: aload 10
      // 068: ifnonnull 07c
      // 06b: new f0/b0
      // 06e: astore 3
      // 06f: aload 3
      // 070: invokespecial f0/b0.<init> ()V
      // 073: aload 12
      // 075: aload 13
      // 077: aload 3
      // 078: invokevirtual java/util/WeakHashMap.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
      // 07b: pop
      // 07c: aload 12
      // 07e: monitorexit
      // 07f: aload 3
      // 080: getfield f0/b0.a Lcom/guard/wallet/http/h;
      // 083: astore 10
      // 085: aload 3
      // 086: aload 11
      // 088: putfield f0/b0.a Lcom/guard/wallet/http/h;
      // 08b: aload 3
      // 08c: getfield f0/b0.b Ljava/util/concurrent/Semaphore;
      // 08f: astore 13
      // 091: aload 11
      // 093: getfield com/guard/wallet/http/h.e Ljava/lang/Object;
      // 096: checkcast java/util/concurrent/Semaphore
      // 099: invokevirtual java/util/concurrent/Semaphore.tryAcquire ()Z
      // 09c: ifeq 0a2
      // 09f: goto 0dd
      // 0a2: invokestatic java/lang/System.currentTimeMillis ()J
      // 0a5: lstore 5
      // 0a7: aload 3
      // 0a8: invokevirtual f0/b0.a ()Ljava/lang/Runnable;
      // 0ab: astore 12
      // 0ad: aload 12
      // 0af: ifnonnull 10f
      // 0b2: aload 13
      // 0b4: bipush 1
      // 0b5: aload 13
      // 0b7: invokevirtual java/util/concurrent/Semaphore.availablePermits ()I
      // 0ba: invokestatic java/lang/Math.max (II)I
      // 0bd: lload 7
      // 0bf: getstatic java/util/concurrent/TimeUnit.MILLISECONDS Ljava/util/concurrent/TimeUnit;
      // 0c2: invokevirtual java/util/concurrent/Semaphore.tryAcquire (IJLjava/util/concurrent/TimeUnit;)Z
      // 0c5: ifne 0cb
      // 0c8: goto 0f4
      // 0cb: aload 11
      // 0cd: getfield com/guard/wallet/http/h.e Ljava/lang/Object;
      // 0d0: checkcast java/util/concurrent/Semaphore
      // 0d3: invokevirtual java/util/concurrent/Semaphore.tryAcquire ()Z
      // 0d6: istore 9
      // 0d8: iload 9
      // 0da: ifeq 0e6
      // 0dd: aload 3
      // 0de: aload 10
      // 0e0: putfield f0/b0.a Lcom/guard/wallet/http/h;
      // 0e3: goto 0fd
      // 0e6: invokestatic java/lang/System.currentTimeMillis ()J
      // 0e9: lstore 1
      // 0ea: lload 1
      // 0eb: lload 5
      // 0ed: lsub
      // 0ee: lload 7
      // 0f0: lcmp
      // 0f1: iflt 0a7
      // 0f4: aload 3
      // 0f5: aload 10
      // 0f7: putfield f0/b0.a Lcom/guard/wallet/http/h;
      // 0fa: bipush 0
      // 0fb: istore 4
      // 0fd: iload 4
      // 0ff: ifeq 107
      // 102: aload 0
      // 103: invokevirtual h0/h.c ()Ljava/lang/Object;
      // 106: areturn
      // 107: new java/util/concurrent/TimeoutException
      // 10a: dup
      // 10b: invokespecial java/util/concurrent/TimeoutException.<init> ()V
      // 10e: athrow
      // 10f: aload 12
      // 111: invokeinterface java/lang/Runnable.run ()V 1
      // 116: goto 0a7
      // 119: astore 11
      // 11b: aload 3
      // 11c: aload 10
      // 11e: putfield f0/b0.a Lcom/guard/wallet/http/h;
      // 121: aload 11
      // 123: athrow
      // 124: astore 3
      // 125: aload 12
      // 127: monitorexit
      // 128: aload 3
      // 129: athrow
      // 12a: aload 0
      // 12b: invokevirtual h0/h.c ()Ljava/lang/Object;
      // 12e: astore 3
      // 12f: aload 0
      // 130: monitorexit
      // 131: aload 3
      // 132: areturn
      // 133: astore 3
      // 134: aload 0
      // 135: monitorexit
      // 136: aload 3
      // 137: athrow
   }
}
