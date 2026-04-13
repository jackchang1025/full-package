package f0;

import java.nio.ByteBuffer;

public abstract class r implements p {
   public p d;
   public final m e = new m();
   public g0.c f;
   public int g = Integer.MAX_VALUE;

   public r(k var1) {
      this.d = var1;
      l var2 = new l(this);
      ((b)var1).j = var2;
      this.g = 0;
   }

   public static void a(m var0) {
      StringBuilder var1 = new StringBuilder();
      var1.append(Integer.toString(var0.c, 16));
      var1.append("\r\n");
      var0.b(ByteBuffer.wrap(var1.toString().getBytes()));
      var0.a(ByteBuffer.wrap("\r\n".getBytes()));
   }

   @Override
   public final j b() {
      return this.d.b();
   }

   @Override
   public final void c(m param1) {
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
      // 01: invokevirtual f0/r.b ()Lf0/j;
      // 04: getfield f0/j.e Lf0/e;
      // 07: invokestatic java/lang/Thread.currentThread ()Ljava/lang/Thread;
      // 0a: if_acmpeq 4e
      // 0d: aload 0
      // 0e: getfield f0/r.e Lf0/m;
      // 11: astore 3
      // 12: aload 3
      // 13: monitorenter
      // 14: aload 0
      // 15: getfield f0/r.e Lf0/m;
      // 18: getfield f0/m.c I
      // 1b: aload 0
      // 1c: getfield f0/r.g I
      // 1f: if_icmplt 27
      // 22: aload 3
      // 23: monitorexit
      // 24: goto 82
      // 27: aload 1
      // 28: invokestatic f0/r.a (Lf0/m;)V
      // 2b: aload 1
      // 2c: aload 0
      // 2d: getfield f0/r.e Lf0/m;
      // 30: invokevirtual f0/m.c (Lf0/m;)V
      // 33: aload 3
      // 34: monitorexit
      // 35: aload 0
      // 36: invokevirtual f0/r.b ()Lf0/j;
      // 39: new o/a
      // 3c: dup
      // 3d: aload 0
      // 3e: bipush 6
      // 40: invokespecial o/a.<init> (Ljava/lang/Object;I)V
      // 43: invokevirtual f0/j.c (Ljava/lang/Runnable;)V
      // 46: goto 82
      // 49: astore 1
      // 4a: aload 3
      // 4b: monitorexit
      // 4c: aload 1
      // 4d: athrow
      // 4e: aload 1
      // 4f: invokestatic f0/r.a (Lf0/m;)V
      // 52: aload 0
      // 53: getfield f0/r.e Lf0/m;
      // 56: getfield f0/m.c I
      // 59: ifle 61
      // 5c: bipush 1
      // 5d: istore 2
      // 5e: goto 63
      // 61: bipush 0
      // 62: istore 2
      // 63: iload 2
      // 64: ifne 71
      // 67: aload 0
      // 68: getfield f0/r.d Lf0/p;
      // 6b: aload 1
      // 6c: invokeinterface f0/p.c (Lf0/m;)V 2
      // 71: aload 0
      // 72: getfield f0/r.e Lf0/m;
      // 75: astore 3
      // 76: aload 3
      // 77: monitorenter
      // 78: aload 1
      // 79: aload 0
      // 7a: getfield f0/r.e Lf0/m;
      // 7d: invokevirtual f0/m.c (Lf0/m;)V
      // 80: aload 3
      // 81: monitorexit
      // 82: return
      // 83: astore 1
      // 84: aload 3
      // 85: monitorexit
      // 86: aload 1
      // 87: athrow
   }

   @Override
   public final void d(g0.c var1) {
      this.f = var1;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final void e() {
      m var2 = this.e;
      synchronized (var2){} // $VF: monitorenter 

      boolean var1;
      label127: {
         Throwable var10000;
         label122: {
            label121: {
               label120: {
                  try {
                     this.d.c(this.e);
                     if (this.e.c == 0) {
                        break label120;
                     }
                  } catch (Throwable var15) {
                     var10000 = var15;
                     boolean var10001 = false;
                     break label122;
                  }

                  var1 = false;
                  break label121;
               }

               var1 = true;
            }

            label114:
            try {
               // $VF: monitorexit
               break label127;
            } catch (Throwable var14) {
               var10000 = var14;
               boolean var17 = false;
               break label114;
            }
         }

         while (true) {
            Throwable var3 = var10000;

            try {
               // $VF: monitorexit
               throw var3;
            } catch (Throwable var13) {
               var10000 = var13;
               boolean var18 = false;
               continue;
            }
         }
      }

      if (var1) {
         g0.c var16 = this.f;
         if (var16 != null) {
            var16.c();
         }
      }
   }

   @Override
   public final void f(g0.a var1) {
      this.d.f(var1);
   }

   @Override
   public final g0.c i() {
      return this.f;
   }
}
