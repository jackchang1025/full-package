package b1;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

public final class e extends OutputStream {
   public final h a;

   public e(h var1) {
      this.a = var1;
   }

   @Override
   public final void close() {
      this.flush();
   }

   @Override
   public final void finalize() {
      h var1 = this.a;
      if (var1 != null) {
         try {
            var1.close();
            var1.z();
         } catch (Exception var2) {
            a1.q.s("b1.e", var2);
         }
      }

      super.finalize();
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public final void flush() {
      h var1 = this.a;
      if (!var1.g) {
         d var23 = var1.a;
         Object var2 = var23.v;
         synchronized (var2){} // $VF: monitorenter 

         Throwable var10000;
         label157: {
            label149: {
               try {
                  if (var23.u) {
                     var24 = var23.i;
                     Objects.requireNonNull(var24);
                     break label149;
                  }
               } catch (Throwable var22) {
                  var10000 = var22;
                  boolean var10001 = false;
                  break label157;
               }

               try {
                  var24 = var23.g;
               } catch (Throwable var21) {
                  var10000 = var21;
                  boolean var26 = false;
                  break label157;
               }
            }

            label140:
            try {
               var24.flush();
               // $VF: monitorexit
               return;
            } catch (Throwable var20) {
               var10000 = var20;
               boolean var27 = false;
               break label140;
            }
         }

         while (true) {
            Throwable var25 = var10000;

            try {
               // $VF: monitorexit
               throw var25;
            } catch (Throwable var19) {
               var10000 = var19;
               boolean var28 = false;
               continue;
            }
         }
      } else {
         throw new IOException("Stream closed");
      }
   }

   @Override
   public final void write(int var1) {
      this.write(new byte[]{(byte)(var1 & 0xFF)}, 0, 1);
   }

   @Override
   public final void write(byte[] var1) {
      this.write(var1, 0, var1.length);
   }

   @Override
   public final void write(byte[] param1, int param2, int param3) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield b1/e.a Lb1/h;
      // 04: astore 8
      // 06: aload 8
      // 08: monitorenter
      // 09: aload 8
      // 0b: getfield b1/h.g Z
      // 0e: ifne 3f
      // 11: aload 8
      // 13: getfield b1/h.d Ljava/util/concurrent/atomic/AtomicBoolean;
      // 16: bipush 1
      // 17: bipush 0
      // 18: invokevirtual java/util/concurrent/atomic/AtomicBoolean.compareAndSet (ZZ)Z
      // 1b: istore 7
      // 1d: iload 7
      // 1f: ifne 3f
      // 22: aload 8
      // 24: invokevirtual java/lang/Object.wait ()V
      // 27: goto 09
      // 2a: astore 1
      // 2b: new java/io/IOException
      // 2e: astore 9
      // 30: aload 9
      // 32: invokespecial java/io/IOException.<init> ()V
      // 35: aload 9
      // 37: aload 1
      // 38: invokevirtual java/lang/Throwable.initCause (Ljava/lang/Throwable;)Ljava/lang/Throwable;
      // 3b: checkcast java/io/IOException
      // 3e: athrow
      // 3f: aload 8
      // 41: getfield b1/h.g Z
      // 44: ifne e1
      // 47: aload 8
      // 49: monitorexit
      // 4a: aload 8
      // 4c: getfield b1/h.a Lb1/d;
      // 4f: astore 9
      // 51: aload 9
      // 53: getfield b1/d.k Z
      // 56: ifeq c5
      // 59: aload 9
      // 5b: ldc2_w 9223372036854775807
      // 5e: getstatic java/util/concurrent/TimeUnit.MILLISECONDS Ljava/util/concurrent/TimeUnit;
      // 61: invokevirtual b1/d.B (JLjava/util/concurrent/TimeUnit;)Z
      // 64: pop
      // 65: aload 9
      // 67: getfield b1/d.o I
      // 6a: istore 4
      // 6c: iload 3
      // 6d: ifeq c4
      // 70: aload 8
      // 72: getfield b1/h.a Lb1/d;
      // 75: astore 9
      // 77: aload 8
      // 79: getfield b1/h.b I
      // 7c: istore 5
      // 7e: aload 8
      // 80: getfield b1/h.c I
      // 83: istore 6
      // 85: iload 3
      // 86: iload 4
      // 88: if_icmpgt a5
      // 8b: aload 9
      // 8d: ldc 1163154007
      // 8f: iload 5
      // 91: iload 6
      // 93: aload 1
      // 94: iload 2
      // 95: iload 3
      // 96: invokestatic b1/g.a (III[BII)[B
      // 99: invokevirtual b1/d.A ([B)V
      // 9c: iload 2
      // 9d: iload 3
      // 9e: iadd
      // 9f: istore 2
      // a0: bipush 0
      // a1: istore 3
      // a2: goto 6c
      // a5: aload 9
      // a7: ldc 1163154007
      // a9: iload 5
      // ab: iload 6
      // ad: aload 1
      // ae: iload 2
      // af: iload 4
      // b1: invokestatic b1/g.a (III[BII)[B
      // b4: invokevirtual b1/d.A ([B)V
      // b7: iload 2
      // b8: iload 4
      // ba: iadd
      // bb: istore 2
      // bc: iload 3
      // bd: iload 4
      // bf: isub
      // c0: istore 3
      // c1: goto 6c
      // c4: return
      // c5: new java/lang/IllegalStateException
      // c8: astore 1
      // c9: aload 1
      // ca: ldc "connect() must be called first"
      // cc: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // cf: aload 1
      // d0: athrow
      // d1: astore 1
      // d2: new java/io/IOException
      // d5: dup
      // d6: invokespecial java/io/IOException.<init> ()V
      // d9: aload 1
      // da: invokevirtual java/lang/Throwable.initCause (Ljava/lang/Throwable;)Ljava/lang/Throwable;
      // dd: checkcast java/io/IOException
      // e0: athrow
      // e1: new java/io/IOException
      // e4: astore 1
      // e5: aload 1
      // e6: ldc "Stream closed"
      // e8: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
      // eb: aload 1
      // ec: athrow
      // ed: astore 1
      // ee: aload 8
      // f0: monitorexit
      // f1: aload 1
      // f2: athrow
   }
}
