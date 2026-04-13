package b1;

import android.content.Context;
import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.security.auth.DestroyFailedException;

public abstract class b implements Closeable {
   public final Object a = new Object();
   public d b;
   public String c = "127.0.0.1";
   public int d = 0;
   public int e = 1;
   public long f = 30000L;
   public TimeUnit g = TimeUnit.MILLISECONDS;
   public final ExecutorService h = Executors.newFixedThreadPool(1);

   public final k A() {
      PrivateKey var1 = this.C();
      Objects.requireNonNull(var1);
      var1 = var1;
      Certificate var2 = this.B();
      Objects.requireNonNull(var2);
      return new k(var1, var2);
   }

   public abstract Certificate B();

   public abstract PrivateKey C();

   public abstract boolean D();

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final h E(String[] var1, int var2) {
      Object var4 = this.a;
      synchronized (var4){} // $VF: monitorenter 

      Throwable var10000;
      label229: {
         d var5;
         try {
            var5 = this.b;
         } catch (Throwable var35) {
            var10000 = var35;
            boolean var10001 = false;
            break label229;
         }

         if (var5 != null) {
            boolean var3;
            label219: {
               label218: {
                  try {
                     Socket var39 = var5.a;
                     if (!var39.isClosed() && var39.isConnected()) {
                        break label218;
                     }
                  } catch (Throwable var34) {
                     var10000 = var34;
                     boolean var40 = false;
                     break label229;
                  }

                  var3 = false;
                  break label219;
               }

               var3 = true;
            }

            if (var3) {
               try {
                  h var38 = this.b.z(var1, var2);
                  // $VF: monitorexit
                  return var38;
               } catch (Throwable var32) {
                  var10000 = var32;
                  boolean var42 = false;
                  break label229;
               }
            }
         }

         label208:
         try {
            IOException var36 = new IOException("Not connected to ADB.");
            throw var36;
         } catch (Throwable var33) {
            var10000 = var33;
            boolean var41 = false;
            break label208;
         }
      }

      while (true) {
         Throwable var37 = var10000;

         try {
            // $VF: monitorexit
            throw var37;
         } catch (Throwable var31) {
            var10000 = var31;
            boolean var43 = false;
            continue;
         }
      }
   }

   public final boolean F(String param1, int param2, String param3) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield b1/b.a Ljava/lang/Object;
      // 04: astore 5
      // 06: aload 5
      // 08: monitorenter
      // 09: aload 0
      // 0a: invokevirtual b1/b.A ()Lb1/k;
      // 0d: astore 9
      // 0f: new java/util/concurrent/CountDownLatch
      // 12: astore 8
      // 14: aload 8
      // 16: bipush 1
      // 17: invokespecial java/util/concurrent/CountDownLatch.<init> (I)V
      // 1a: new java/util/concurrent/atomic/AtomicBoolean
      // 1d: astore 7
      // 1f: aload 7
      // 21: bipush 0
      // 22: invokespecial java/util/concurrent/atomic/AtomicBoolean.<init> (Z)V
      // 25: new b1/p
      // 28: astore 6
      // 2a: aload 1
      // 2b: invokestatic java/util/Objects.requireNonNull (Ljava/lang/Object;)Ljava/lang/Object;
      // 2e: pop
      // 2f: aload 3
      // 30: invokestatic java/util/Objects.requireNonNull (Ljava/lang/Object;)Ljava/lang/Object;
      // 33: pop
      // 34: aload 6
      // 36: aload 1
      // 37: iload 2
      // 38: aload 3
      // 39: invokestatic com/guard/wallet/utils/g.Y (Ljava/lang/String;)[B
      // 3c: aload 9
      // 3e: invokespecial b1/p.<init> (Ljava/lang/String;I[BLb1/k;)V
      // 41: aload 0
      // 42: getfield b1/b.h Ljava/util/concurrent/ExecutorService;
      // 45: astore 1
      // 46: new l0/i
      // 49: astore 3
      // 4a: aload 3
      // 4b: aload 6
      // 4d: aload 7
      // 4f: aload 8
      // 51: bipush 1
      // 52: invokespecial l0/i.<init> (Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;I)V
      // 55: aload 1
      // 56: aload 3
      // 57: invokeinterface java/util/concurrent/ExecutorService.submit (Ljava/lang/Runnable;)Ljava/util/concurrent/Future; 2
      // 5c: pop
      // 5d: aload 8
      // 5f: ldc2_w 15
      // 62: getstatic java/util/concurrent/TimeUnit.SECONDS Ljava/util/concurrent/TimeUnit;
      // 65: invokevirtual java/util/concurrent/CountDownLatch.await (JLjava/util/concurrent/TimeUnit;)Z
      // 68: ifne 7b
      // 6b: aload 7
      // 6d: bipush 0
      // 6e: invokevirtual java/util/concurrent/atomic/AtomicBoolean.set (Z)V
      // 71: goto 7b
      // 74: astore 1
      // 75: ldc "AbsAdbConnectionManager"
      // 77: aload 1
      // 78: invokestatic a1/q.s (Ljava/lang/String;Ljava/lang/Exception;)V
      // 7b: aload 6
      // 7d: invokevirtual b1/p.close ()V
      // 80: aload 7
      // 82: invokevirtual java/util/concurrent/atomic/AtomicBoolean.get ()Z
      // 85: istore 4
      // 87: aload 5
      // 89: monitorexit
      // 8a: iload 4
      // 8c: ireturn
      // 8d: astore 1
      // 8e: aload 5
      // 90: monitorexit
      // 91: aload 1
      // 92: athrow
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public void close() {
      d var8;
      label39: {
         DestroyFailedException var9;
         label38: {
            label42: {
               try {
                  var8 = this.b;
               } catch (DestroyFailedException var6) {
                  var9 = var6;
                  boolean var10 = false;
                  break label38;
               } catch (NoSuchMethodError var7) {
                  var10000 = var7;
                  boolean var10001 = false;
                  break label42;
               }

               if (var8 != null) {
                  try {
                     var8.close();
                     this.b = null;
                  } catch (DestroyFailedException var4) {
                     var9 = var4;
                     boolean var12 = false;
                     break label38;
                  } catch (NoSuchMethodError var5) {
                     var10000 = var5;
                     boolean var11 = false;
                     break label42;
                  }
               }

               try {
                  this.h.shutdownNow();
                  this.C().destroy();
                  return;
               } catch (DestroyFailedException var2) {
                  var9 = var2;
                  boolean var14 = false;
                  break label38;
               } catch (NoSuchMethodError var3) {
                  var10000 = var3;
                  boolean var13 = false;
               }
            }

            var8 = var10000;
            break label39;
         }

         var8 = var9;
      }

      a1.q.t("AbsAdbConnectionManager", var8);
   }

   public final int x(Context param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 000: aload 0
      // 001: getfield b1/b.a Ljava/lang/Object;
      // 004: astore 6
      // 006: aload 6
      // 008: monitorenter
      // 009: new java/util/concurrent/atomic/AtomicInteger
      // 00c: astore 7
      // 00e: aload 7
      // 010: bipush -1
      // 011: invokespecial java/util/concurrent/atomic/AtomicInteger.<init> (I)V
      // 014: new java/util/concurrent/atomic/AtomicReference
      // 017: astore 10
      // 019: aconst_null
      // 01a: astore 5
      // 01c: aload 10
      // 01e: aconst_null
      // 01f: invokespecial java/util/concurrent/atomic/AtomicReference.<init> (Ljava/lang/Object;)V
      // 022: new java/util/concurrent/CountDownLatch
      // 025: astore 8
      // 027: aload 8
      // 029: bipush 1
      // 02a: invokespecial java/util/concurrent/CountDownLatch.<init> (I)V
      // 02d: new c1/d
      // 030: astore 4
      // 032: new b1/a
      // 035: astore 9
      // 037: aload 9
      // 039: aload 10
      // 03b: aload 7
      // 03d: aload 8
      // 03f: bipush 0
      // 040: invokespecial b1/a.<init> (Ljava/util/concurrent/atomic/AtomicReference;Ljava/util/concurrent/atomic/AtomicInteger;Ljava/util/concurrent/CountDownLatch;I)V
      // 043: aload 4
      // 045: aload 1
      // 046: ldc "adb"
      // 048: aload 9
      // 04a: invokespecial c1/d.<init> (Landroid/content/Context;Ljava/lang/String;Lc1/b;)V
      // 04d: aload 4
      // 04f: invokevirtual c1/d.a ()V
      // 052: new c1/d
      // 055: astore 9
      // 057: new b1/a
      // 05a: astore 11
      // 05c: aload 11
      // 05e: aload 10
      // 060: aload 7
      // 062: aload 8
      // 064: bipush 1
      // 065: invokespecial b1/a.<init> (Ljava/util/concurrent/atomic/AtomicReference;Ljava/util/concurrent/atomic/AtomicInteger;Ljava/util/concurrent/CountDownLatch;I)V
      // 068: aload 9
      // 06a: aload 1
      // 06b: ldc "adb-tls-connect"
      // 06d: aload 11
      // 06f: invokespecial c1/d.<init> (Landroid/content/Context;Ljava/lang/String;Lc1/b;)V
      // 072: aload 9
      // 074: invokevirtual c1/d.a ()V
      // 077: aload 8
      // 079: ldc2_w 10000
      // 07c: getstatic java/util/concurrent/TimeUnit.MILLISECONDS Ljava/util/concurrent/TimeUnit;
      // 07f: invokevirtual java/util/concurrent/CountDownLatch.await (JLjava/util/concurrent/TimeUnit;)Z
      // 082: ifne 08d
      // 085: ldc "AbsAdbConnectionManager"
      // 087: ldc "Timed out while trying to find a valid tls host address and port"
      // 089: invokestatic android/util/Log.e (Ljava/lang/String;Ljava/lang/String;)I
      // 08c: pop
      // 08d: aload 4
      // 08f: invokevirtual c1/d.b ()V
      // 092: aload 9
      // 094: invokevirtual c1/d.b ()V
      // 097: aload 10
      // 099: invokevirtual java/util/concurrent/atomic/AtomicReference.get ()Ljava/lang/Object;
      // 09c: checkcast java/lang/String
      // 09f: astore 4
      // 0a1: aload 7
      // 0a3: invokevirtual java/util/concurrent/atomic/AtomicInteger.get ()I
      // 0a6: istore 2
      // 0a7: aload 4
      // 0a9: ifnull 10f
      // 0ac: iload 2
      // 0ad: bipush -1
      // 0ae: if_icmpne 0b4
      // 0b1: goto 10f
      // 0b4: aload 0
      // 0b5: aload 4
      // 0b7: putfield b1/b.c Ljava/lang/String;
      // 0ba: aload 0
      // 0bb: getfield b1/b.e I
      // 0be: istore 3
      // 0bf: aload 0
      // 0c0: invokevirtual b1/b.A ()Lb1/k;
      // 0c3: astore 7
      // 0c5: new b1/d
      // 0c8: astore 1
      // 0c9: aload 1
      // 0ca: aload 4
      // 0cc: iload 2
      // 0cd: aload 7
      // 0cf: iload 3
      // 0d0: invokespecial b1/d.<init> (Ljava/lang/String;ILb1/k;I)V
      // 0d3: aload 1
      // 0d4: ldc "com.guard.wallet"
      // 0d6: putfield b1/d.r Ljava/lang/String;
      // 0d9: goto 0f1
      // 0dc: astore 4
      // 0de: goto 0e6
      // 0e1: astore 4
      // 0e3: aload 5
      // 0e5: astore 1
      // 0e6: getstatic b1/d.w I
      // 0e9: istore 3
      // 0ea: ldc "d"
      // 0ec: aload 4
      // 0ee: invokestatic a1/q.s (Ljava/lang/String;Ljava/lang/Exception;)V
      // 0f1: aload 0
      // 0f2: aload 1
      // 0f3: putfield b1/b.b Lb1/d;
      // 0f6: aload 1
      // 0f7: aload 0
      // 0f8: getfield b1/b.f J
      // 0fb: aload 0
      // 0fc: getfield b1/b.g Ljava/util/concurrent/TimeUnit;
      // 0ff: invokevirtual b1/d.y (JLjava/util/concurrent/TimeUnit;)Z
      // 102: ifeq 10a
      // 105: aload 6
      // 107: monitorexit
      // 108: iload 2
      // 109: ireturn
      // 10a: aload 6
      // 10c: monitorexit
      // 10d: bipush 0
      // 10e: ireturn
      // 10f: ldc "AbsAdbConnectionManager"
      // 111: ldc "Could not find any valid host address or port"
      // 113: invokestatic android/util/Log.e (Ljava/lang/String;Ljava/lang/String;)I
      // 116: pop
      // 117: aload 6
      // 119: monitorexit
      // 11a: bipush 0
      // 11b: ireturn
      // 11c: astore 1
      // 11d: aload 4
      // 11f: invokevirtual c1/d.b ()V
      // 122: aload 9
      // 124: invokevirtual c1/d.b ()V
      // 127: aload 1
      // 128: athrow
      // 129: astore 1
      // 12a: aload 6
      // 12c: monitorexit
      // 12d: aload 1
      // 12e: athrow
   }

   public final int y(int param1, String param2) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield b1/b.a Ljava/lang/Object;
      // 04: astore 7
      // 06: aload 7
      // 08: monitorenter
      // 09: aload 0
      // 0a: invokevirtual b1/b.D ()Z
      // 0d: ifeq 1a
      // 10: aload 0
      // 11: getfield b1/b.d I
      // 14: istore 1
      // 15: aload 7
      // 17: monitorexit
      // 18: iload 1
      // 19: ireturn
      // 1a: aload 0
      // 1b: getfield b1/b.b Lb1/d;
      // 1e: astore 4
      // 20: aconst_null
      // 21: astore 6
      // 23: aload 4
      // 25: ifnull 3b
      // 28: aload 4
      // 2a: invokevirtual b1/d.close ()V
      // 2d: aload 0
      // 2e: aconst_null
      // 2f: putfield b1/b.b Lb1/d;
      // 32: ldc "AbsAdbConnectionManager"
      // 34: ldc_w "释放 mAdbConnection"
      // 37: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 3a: pop
      // 3b: aload 0
      // 3c: aload 2
      // 3d: putfield b1/b.c Ljava/lang/String;
      // 40: aload 0
      // 41: getfield b1/b.e I
      // 44: istore 3
      // 45: aload 0
      // 46: invokevirtual b1/b.A ()Lb1/k;
      // 49: astore 5
      // 4b: new b1/d
      // 4e: astore 4
      // 50: aload 4
      // 52: aload 2
      // 53: iload 1
      // 54: aload 5
      // 56: iload 3
      // 57: invokespecial b1/d.<init> (Ljava/lang/String;ILb1/k;I)V
      // 5a: aload 4
      // 5c: ldc "com.guard.wallet"
      // 5e: putfield b1/d.r Ljava/lang/String;
      // 61: goto 7f
      // 64: astore 5
      // 66: aload 4
      // 68: astore 2
      // 69: goto 71
      // 6c: astore 5
      // 6e: aload 6
      // 70: astore 2
      // 71: getstatic b1/d.w I
      // 74: istore 3
      // 75: ldc "d"
      // 77: aload 5
      // 79: invokestatic a1/q.s (Ljava/lang/String;Ljava/lang/Exception;)V
      // 7c: aload 2
      // 7d: astore 4
      // 7f: aload 0
      // 80: aload 4
      // 82: putfield b1/b.b Lb1/d;
      // 85: aload 4
      // 87: ifnull a0
      // 8a: aload 4
      // 8c: ldc2_w 10000
      // 8f: getstatic java/util/concurrent/TimeUnit.MILLISECONDS Ljava/util/concurrent/TimeUnit;
      // 92: invokevirtual b1/d.y (JLjava/util/concurrent/TimeUnit;)Z
      // 95: ifeq a0
      // 98: aload 0
      // 99: iload 1
      // 9a: putfield b1/b.d I
      // 9d: goto c4
      // a0: aload 0
      // a1: bipush 0
      // a2: putfield b1/b.d I
      // a5: goto c4
      // a8: astore 2
      // a9: ldc "AbsAdbConnectionManager"
      // ab: aload 2
      // ac: invokestatic a1/q.s (Ljava/lang/String;Ljava/lang/Exception;)V
      // af: aload 0
      // b0: bipush -2
      // b2: putfield b1/b.d I
      // b5: goto c4
      // b8: astore 2
      // b9: ldc "AbsAdbConnectionManager"
      // bb: aload 2
      // bc: invokestatic a1/q.s (Ljava/lang/String;Ljava/lang/Exception;)V
      // bf: aload 0
      // c0: bipush -1
      // c1: putfield b1/b.d I
      // c4: aload 7
      // c6: monitorexit
      // c7: aload 0
      // c8: getfield b1/b.d I
      // cb: ireturn
      // cc: astore 2
      // cd: aload 7
      // cf: monitorexit
      // d0: aload 2
      // d1: athrow
   }

   public final boolean z(int param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield b1/b.a Ljava/lang/Object;
      // 04: astore 7
      // 06: aload 7
      // 08: monitorenter
      // 09: aload 0
      // 0a: invokevirtual b1/b.D ()Z
      // 0d: ifeq 15
      // 10: aload 7
      // 12: monitorexit
      // 13: bipush 1
      // 14: ireturn
      // 15: aload 0
      // 16: getfield b1/b.b Lb1/d;
      // 19: astore 4
      // 1b: aconst_null
      // 1c: astore 6
      // 1e: aload 4
      // 20: ifnull 36
      // 23: aload 4
      // 25: invokevirtual b1/d.close ()V
      // 28: aload 0
      // 29: aconst_null
      // 2a: putfield b1/b.b Lb1/d;
      // 2d: ldc "AbsAdbConnectionManager"
      // 2f: ldc_w "释放 mAdbConnection"
      // 32: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 35: pop
      // 36: aload 0
      // 37: getfield b1/b.c Ljava/lang/String;
      // 3a: astore 8
      // 3c: aload 0
      // 3d: getfield b1/b.e I
      // 40: istore 2
      // 41: aload 0
      // 42: invokevirtual b1/b.A ()Lb1/k;
      // 45: astore 5
      // 47: new b1/d
      // 4a: astore 4
      // 4c: aload 4
      // 4e: aload 8
      // 50: iload 1
      // 51: aload 5
      // 53: iload 2
      // 54: invokespecial b1/d.<init> (Ljava/lang/String;ILb1/k;I)V
      // 57: aload 4
      // 59: ldc "com.guard.wallet"
      // 5b: putfield b1/d.r Ljava/lang/String;
      // 5e: goto 77
      // 61: astore 5
      // 63: goto 6c
      // 66: astore 5
      // 68: aload 6
      // 6a: astore 4
      // 6c: getstatic b1/d.w I
      // 6f: istore 1
      // 70: ldc "d"
      // 72: aload 5
      // 74: invokestatic a1/q.s (Ljava/lang/String;Ljava/lang/Exception;)V
      // 77: aload 0
      // 78: aload 4
      // 7a: putfield b1/b.b Lb1/d;
      // 7d: aload 4
      // 7f: aload 0
      // 80: getfield b1/b.f J
      // 83: aload 0
      // 84: getfield b1/b.g Ljava/util/concurrent/TimeUnit;
      // 87: invokevirtual b1/d.y (JLjava/util/concurrent/TimeUnit;)Z
      // 8a: istore 3
      // 8b: aload 7
      // 8d: monitorexit
      // 8e: iload 3
      // 8f: ireturn
      // 90: astore 4
      // 92: aload 7
      // 94: monitorexit
      // 95: aload 4
      // 97: athrow
   }
}
