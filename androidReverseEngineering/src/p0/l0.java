package p0;

import java.io.Closeable;
import java.io.IOException;

public abstract class l0 implements Closeable {
   @Override
   public final void close() {
      q0.c.c(this.y());
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final byte[] x() {
      k0 var3 = (k0)this;
      long var1 = var3.b;
      if (var1 <= 2147483647L) {
         a1.g var4 = var3.c;

         try {
            var12 = var4.m();
         } catch (Throwable var10) {
            if (var4 != null) {
               try {
                  var4.close();
               } catch (Throwable var9) {
                  var10.addSuppressed(var9);
                  throw var10;
               }
            }

            throw var10;
         }

         var4.close();
         if (var1 != -1L && var1 != (long)var12.length) {
            StringBuilder var13 = new StringBuilder("Content-Length (");
            var13.append(var1);
            var13.append(") and stream length (");
            throw new IOException(a.a.m(var13, var12.length, ") disagree"));
         } else {
            return var12;
         }
      } else {
         StringBuilder var11 = new StringBuilder("Cannot buffer entire body for content length: ");
         var11.append(var1);
         throw new IOException(var11.toString());
      }
   }

   public abstract a1.g y();

   public final String z() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: aload 0
      // 01: checkcast p0/k0
      // 04: getfield p0/k0.c La1/g;
      // 07: astore 4
      // 09: aload 0
      // 0a: checkcast p0/k0
      // 0d: astore 2
      // 0e: aload 2
      // 0f: getfield p0/k0.a I
      // 12: istore 1
      // 13: aload 2
      // 14: getfield p0/k0.d Ljava/lang/Object;
      // 17: astore 2
      // 18: iload 1
      // 19: tableswitch 19 0 0 22
      // 2c: goto 37
      // 2f: aload 2
      // 30: checkcast p0/x
      // 33: astore 2
      // 34: goto 4a
      // 37: aload 2
      // 38: checkcast java/lang/String
      // 3b: astore 2
      // 3c: aload 2
      // 3d: ifnull 48
      // 40: aload 2
      // 41: invokestatic p0/x.a (Ljava/lang/String;)Lp0/x;
      // 44: astore 2
      // 45: goto 4a
      // 48: aconst_null
      // 49: astore 2
      // 4a: aload 2
      // 4b: ifnull 68
      // 4e: getstatic java/nio/charset/StandardCharsets.UTF_8 Ljava/nio/charset/Charset;
      // 51: astore 3
      // 52: aload 2
      // 53: getfield p0/x.c Ljava/lang/String;
      // 56: astore 5
      // 58: aload 3
      // 59: astore 2
      // 5a: aload 5
      // 5c: ifnull 6c
      // 5f: aload 5
      // 61: invokestatic java/nio/charset/Charset.forName (Ljava/lang/String;)Ljava/nio/charset/Charset;
      // 64: astore 2
      // 65: goto 6c
      // 68: getstatic java/nio/charset/StandardCharsets.UTF_8 Ljava/nio/charset/Charset;
      // 6b: astore 2
      // 6c: aload 4
      // 6e: getstatic q0/c.e La1/m;
      // 71: invokeinterface a1/g.b (La1/m;)I 2
      // 76: istore 1
      // 77: iload 1
      // 78: bipush -1
      // 79: if_icmpeq be
      // 7c: iload 1
      // 7d: ifeq ba
      // 80: iload 1
      // 81: bipush 1
      // 82: if_icmpeq b3
      // 85: iload 1
      // 86: bipush 2
      // 87: if_icmpeq ac
      // 8a: iload 1
      // 8b: bipush 3
      // 8c: if_icmpeq a5
      // 8f: iload 1
      // 90: bipush 4
      // 91: if_icmpne 9b
      // 94: getstatic q0/c.g Ljava/nio/charset/Charset;
      // 97: astore 2
      // 98: goto be
      // 9b: new java/lang/AssertionError
      // 9e: astore 2
      // 9f: aload 2
      // a0: invokespecial java/lang/AssertionError.<init> ()V
      // a3: aload 2
      // a4: athrow
      // a5: getstatic q0/c.f Ljava/nio/charset/Charset;
      // a8: astore 2
      // a9: goto be
      // ac: getstatic java/nio/charset/StandardCharsets.UTF_16LE Ljava/nio/charset/Charset;
      // af: astore 2
      // b0: goto be
      // b3: getstatic java/nio/charset/StandardCharsets.UTF_16BE Ljava/nio/charset/Charset;
      // b6: astore 2
      // b7: goto be
      // ba: getstatic java/nio/charset/StandardCharsets.UTF_8 Ljava/nio/charset/Charset;
      // bd: astore 2
      // be: aload 4
      // c0: aload 2
      // c1: invokeinterface a1/g.w (Ljava/nio/charset/Charset;)Ljava/lang/String; 2
      // c6: astore 2
      // c7: aload 4
      // c9: invokeinterface java/nio/channels/Channel.close ()V 1
      // ce: aload 2
      // cf: areturn
      // d0: astore 2
      // d1: aload 4
      // d3: ifnull e6
      // d6: aload 4
      // d8: invokeinterface java/nio/channels/Channel.close ()V 1
      // dd: goto e6
      // e0: astore 3
      // e1: aload 2
      // e2: aload 3
      // e3: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // e6: aload 2
      // e7: athrow
      // e8: astore 2
      // e9: goto 48
      // ec: astore 2
      // ed: aload 3
      // ee: astore 2
      // ef: goto 6c
   }
}
