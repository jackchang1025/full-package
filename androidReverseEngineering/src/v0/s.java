package v0;

import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class s implements Closeable {
   public static final ThreadPoolExecutor x;
   public final boolean a;
   public final o b;
   public final LinkedHashMap c = new LinkedHashMap();
   public final String d;
   public int e;
   public int f;
   public boolean g;
   public final ScheduledThreadPoolExecutor h;
   public final ThreadPoolExecutor i;
   public final p0.q j;
   public long k = 0L;
   public long l = 0L;
   public long m = 0L;
   public long n = 0L;
   public long o = 0L;
   public long p = 0L;
   public long q;
   public final z.d r;
   public final z.d s;
   public final Socket t;
   public final z u;
   public final q v;
   public final LinkedHashSet w;

   static {
      TimeUnit var2 = TimeUnit.SECONDS;
      SynchronousQueue var0 = new SynchronousQueue();
      byte[] var1 = q0.c.a;
      x = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, var2, var0, new q0.b("OkHttp Http2Connection", true));
   }

   public s(m var1) {
      z.d var5 = new z.d();
      this.r = var5;
      z.d var4 = new z.d();
      this.s = var4;
      this.w = new LinkedHashSet();
      this.j = c0.a;
      this.a = true;
      this.b = var1.e;
      this.f = 3;
      var5.e(7, 16777216);
      String var6 = var1.b;
      this.d = var6;
      ScheduledThreadPoolExecutor var7 = new ScheduledThreadPoolExecutor(1, new q0.b(q0.c.i(new Object[]{var6}, "OkHttp %s Writer"), false));
      this.h = var7;
      if (var1.f != 0) {
         j var8 = new j(this);
         long var2 = (long)var1.f;
         var7.scheduleAtFixedRate(var8, var2, var2, TimeUnit.MILLISECONDS);
      }

      this.i = new ThreadPoolExecutor(
         0, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), new q0.b(q0.c.i(new Object[]{var6}, "OkHttp %s Push Observer"), true)
      );
      var4.e(7, 65535);
      var4.e(5, 16384);
      this.q = (long)var4.d();
      this.t = var1.a;
      this.u = new z(var1.d, true);
      this.v = new q(this, new v(var1.c, true));
   }

   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final void A(q0.a var1) {
      synchronized (this){} // $VF: monitorenter 

      try {
         if (!this.g) {
            this.i.execute(var1);
         }
      } finally {
         // $VF: monitorexit
      }
   }

   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final y B(int var1) {
      synchronized (this){} // $VF: monitorenter 

      y var2;
      try {
         var2 = (y)this.c.remove(var1);
         this.notifyAll();
      } finally {
         // $VF: monitorexit
      }

      return var2;
   }

   public final void C(b param1) {
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
      // 01: getfield v0/s.u Lv0/z;
      // 04: astore 3
      // 05: aload 3
      // 06: monitorenter
      // 07: aload 0
      // 08: monitorenter
      // 09: aload 0
      // 0a: getfield v0/s.g Z
      // 0d: ifeq 15
      // 10: aload 0
      // 11: monitorexit
      // 12: aload 3
      // 13: monitorexit
      // 14: return
      // 15: aload 0
      // 16: bipush 1
      // 17: putfield v0/s.g Z
      // 1a: aload 0
      // 1b: getfield v0/s.e I
      // 1e: istore 2
      // 1f: aload 0
      // 20: monitorexit
      // 21: aload 0
      // 22: getfield v0/s.u Lv0/z;
      // 25: iload 2
      // 26: aload 1
      // 27: getstatic q0/c.a [B
      // 2a: invokevirtual v0/z.A (ILv0/b;[B)V
      // 2d: aload 3
      // 2e: monitorexit
      // 2f: return
      // 30: astore 1
      // 31: aload 0
      // 32: monitorexit
      // 33: aload 1
      // 34: athrow
      // 35: astore 1
      // 36: aload 3
      // 37: monitorexit
      // 38: aload 1
      // 39: athrow
   }

   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final void D(long var1) {
      synchronized (this){} // $VF: monitorenter 

      try {
         var1 = this.p + var1;
         this.p = var1;
         if (var1 >= (long)(this.r.d() / 2)) {
            this.G(0, this.p);
            this.p = 0L;
         }
      } finally {
         // $VF: monitorexit
      }
   }

   public final void E(int param1, boolean param2, a1.e param3, long param4) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: lload 4
      // 02: lstore 8
      // 04: lload 4
      // 06: lconst_0
      // 07: lcmp
      // 08: ifne 17
      // 0b: aload 0
      // 0c: getfield v0/s.u Lv0/z;
      // 0f: iload 2
      // 10: iload 1
      // 11: aload 3
      // 12: bipush 0
      // 13: invokevirtual v0/z.y (ZILa1/e;I)V
      // 16: return
      // 17: lload 8
      // 19: lconst_0
      // 1a: lcmp
      // 1b: ifle c3
      // 1e: aload 0
      // 1f: monitorenter
      // 20: aload 0
      // 21: getfield v0/s.q J
      // 24: lstore 4
      // 26: lload 4
      // 28: lconst_0
      // 29: lcmp
      // 2a: ifgt 51
      // 2d: aload 0
      // 2e: getfield v0/s.c Ljava/util/LinkedHashMap;
      // 31: iload 1
      // 32: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 35: invokeinterface java/util/Map.containsKey (Ljava/lang/Object;)Z 2
      // 3a: ifeq 44
      // 3d: aload 0
      // 3e: invokevirtual java/lang/Object.wait ()V
      // 41: goto 20
      // 44: new java/io/IOException
      // 47: astore 3
      // 48: aload 3
      // 49: ldc_w "stream closed"
      // 4c: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
      // 4f: aload 3
      // 50: athrow
      // 51: lload 8
      // 53: lload 4
      // 55: invokestatic java/lang/Math.min (JJ)J
      // 58: l2i
      // 59: aload 0
      // 5a: getfield v0/s.u Lv0/z;
      // 5d: getfield v0/z.d I
      // 60: invokestatic java/lang/Math.min (II)I
      // 63: istore 6
      // 65: aload 0
      // 66: getfield v0/s.q J
      // 69: lstore 10
      // 6b: iload 6
      // 6d: i2l
      // 6e: lstore 4
      // 70: aload 0
      // 71: lload 10
      // 73: lload 4
      // 75: lsub
      // 76: putfield v0/s.q J
      // 79: aload 0
      // 7a: monitorexit
      // 7b: lload 8
      // 7d: lload 4
      // 7f: lsub
      // 80: lstore 8
      // 82: aload 0
      // 83: getfield v0/s.u Lv0/z;
      // 86: astore 12
      // 88: iload 2
      // 89: ifeq 99
      // 8c: lload 8
      // 8e: lconst_0
      // 8f: lcmp
      // 90: ifne 99
      // 93: bipush 1
      // 94: istore 7
      // 96: goto 9c
      // 99: bipush 0
      // 9a: istore 7
      // 9c: aload 12
      // 9e: iload 7
      // a0: iload 1
      // a1: aload 3
      // a2: iload 6
      // a4: invokevirtual v0/z.y (ZILa1/e;I)V
      // a7: goto 17
      // aa: astore 3
      // ab: goto bf
      // ae: astore 3
      // af: invokestatic java/lang/Thread.currentThread ()Ljava/lang/Thread;
      // b2: invokevirtual java/lang/Thread.interrupt ()V
      // b5: new java/io/InterruptedIOException
      // b8: astore 3
      // b9: aload 3
      // ba: invokespecial java/io/InterruptedIOException.<init> ()V
      // bd: aload 3
      // be: athrow
      // bf: aload 0
      // c0: monitorexit
      // c1: aload 3
      // c2: athrow
      // c3: return
   }

   public final void F(int var1, b var2) {
      try {
         ScheduledThreadPoolExecutor var4 = this.h;
         h var3 = new h(this, "OkHttp %s stream %d", new Object[]{this.d, var1}, var1, var2, 0);
         var4.execute(var3);
      } catch (RejectedExecutionException var5) {
      }
   }

   public final void G(int var1, long var2) {
      try {
         ScheduledThreadPoolExecutor var5 = this.h;
         i var4 = new i(this, new Object[]{this.d, var1}, var1, var2);
         var5.execute(var4);
      } catch (RejectedExecutionException var6) {
      }
   }

   @Override
   public final void close() {
      this.x(v0.b.b, v0.b.g, null);
   }

   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final void flush() {
      z var1 = this.u;
      synchronized (var1) {
         if (var1.e) {
            IOException var2 = new IOException("closed");
            throw var2;
         }

         var1.a.flush();
      }

      // $VF: monitorexit
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final void x(b var1, b var2, IOException var3) {
      try {
         this.C(var1);
      } catch (IOException var31) {
      }

      synchronized (this){} // $VF: monitorenter 

      label205: {
         Throwable var10000;
         label207: {
            label197: {
               try {
                  if (!this.c.isEmpty()) {
                     var35 = this.c.values().toArray(new y[this.c.size()]);
                     this.c.clear();
                     break label197;
                  }
               } catch (Throwable var34) {
                  var10000 = var34;
                  boolean var10001 = false;
                  break label207;
               }

               var35 = null;
            }

            label189:
            try {
               // $VF: monitorexit
               break label205;
            } catch (Throwable var33) {
               var10000 = var33;
               boolean var37 = false;
               break label189;
            }
         }

         while (true) {
            Throwable var36 = var10000;

            try {
               // $VF: monitorexit
               throw var36;
            } catch (Throwable var32) {
               var10000 = var32;
               boolean var38 = false;
               continue;
            }
         }
      }

      if (var35 != null) {
         for (y var6 : var35) {
            try {
               var6.c(var2, var3);
            } catch (IOException var30) {
            }
         }
      }

      try {
         this.u.close();
      } catch (IOException var29) {
      }

      try {
         this.t.close();
      } catch (IOException var28) {
      }

      this.h.shutdown();
      this.i.shutdown();
   }

   public final void y(IOException var1) {
      b var2 = v0.b.c;
      this.x(var2, var2, var1);
   }

   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final y z(int var1) {
      synchronized (this){} // $VF: monitorenter 

      y var2;
      try {
         var2 = (y)this.c.get(var1);
      } finally {
         // $VF: monitorexit
      }

      return var2;
   }
}
