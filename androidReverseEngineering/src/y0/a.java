package y0;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public final class a {
   public static final byte[] e = new byte[]{42};
   public static final String[] f = new String[0];
   public static final String[] g = new String[]{"*"};
   public static final a h = new a();
   public final AtomicBoolean a = new AtomicBoolean(false);
   public final CountDownLatch b = new CountDownLatch(1);
   public byte[] c;
   public byte[] d;

   public static String a(byte[] var0, byte[][] var1, int var2) {
      int var8 = var0.length;
      int var7 = 0;

      while (var7 < var8) {
         int var3 = (var7 + var8) / 2;

         while (var3 > -1 && var0[var3] != 10) {
            var3--;
         }

         int var10 = var3 + 1;
         var3 = 1;

         while (true) {
            int var11 = var10 + var3;
            if (var0[var11] == 10) {
               int var12 = var11 - var10;
               int var5 = var2;
               int var4 = 0;
               var3 = 0;
               int var6 = var3;

               int var19;
               while (true) {
                  if (var4) {
                     var19 = 46;
                     var4 = 0;
                  } else {
                     byte var18 = var1[var5][var3];
                     var19 = var18 & 255;
                  }

                  var19 -= var0[var10 + var6] & 255;
                  if (var19 != 0) {
                     break;
                  }

                  var6++;
                  var3++;
                  if (var6 == var12) {
                     break;
                  }

                  if (var1[var5].length == var3) {
                     if (var5 == var1.length - 1) {
                        break;
                     }

                     var5++;
                     var3 = -1;
                     var4 = 1;
                  }
               }

               label48:
               if (var19 >= 0) {
                  if (var19 <= 0) {
                     var4 = var12 - var6;
                     var3 = var1[var5].length - var3;

                     while (++var5 < var1.length) {
                        var3 += var1[var5].length;
                     }

                     if (var3 < var4) {
                        break label48;
                     }

                     if (var3 <= var4) {
                        return new String(var0, var10, var12, StandardCharsets.UTF_8);
                     }
                  }

                  var7 = var11 + 1;
                  break;
               }

               var8 = var10 - 1;
               break;
            }

            var3++;
         }
      }

      return null;
   }

   public final void b() {
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
      // 00: ldc y0/a
      // 02: ldc "publicsuffixes.gz"
      // 04: invokevirtual java/lang/Class.getResourceAsStream (Ljava/lang/String;)Ljava/io/InputStream;
      // 07: astore 2
      // 08: aload 2
      // 09: ifnonnull 0d
      // 0c: return
      // 0d: getstatic a1/l.a Ljava/util/logging/Logger;
      // 10: astore 1
      // 11: new a1/o
      // 14: dup
      // 15: new a1/j
      // 18: dup
      // 19: new a1/b
      // 1c: dup
      // 1d: new a1/v
      // 20: dup
      // 21: invokespecial a1/v.<init> ()V
      // 24: aload 2
      // 25: invokespecial a1/b.<init> (La1/v;Ljava/io/InputStream;)V
      // 28: invokespecial a1/j.<init> (La1/t;)V
      // 2b: invokespecial a1/o.<init> (La1/t;)V
      // 2e: astore 1
      // 2f: aload 1
      // 30: invokevirtual a1/o.readInt ()I
      // 33: newarray 8
      // 35: astore 2
      // 36: aload 1
      // 37: aload 2
      // 38: invokevirtual a1/o.y ([B)V
      // 3b: aload 1
      // 3c: invokevirtual a1/o.readInt ()I
      // 3f: newarray 8
      // 41: astore 3
      // 42: aload 1
      // 43: aload 3
      // 44: invokevirtual a1/o.y ([B)V
      // 47: aload 1
      // 48: invokevirtual a1/o.close ()V
      // 4b: aload 0
      // 4c: monitorenter
      // 4d: aload 0
      // 4e: aload 2
      // 4f: putfield y0/a.c [B
      // 52: aload 0
      // 53: aload 3
      // 54: putfield y0/a.d [B
      // 57: aload 0
      // 58: monitorexit
      // 59: aload 0
      // 5a: getfield y0/a.b Ljava/util/concurrent/CountDownLatch;
      // 5d: invokevirtual java/util/concurrent/CountDownLatch.countDown ()V
      // 60: return
      // 61: astore 1
      // 62: aload 0
      // 63: monitorexit
      // 64: aload 1
      // 65: athrow
      // 66: astore 2
      // 67: aload 1
      // 68: invokevirtual a1/o.close ()V
      // 6b: goto 74
      // 6e: astore 1
      // 6f: aload 2
      // 70: aload 1
      // 71: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // 74: aload 2
      // 75: athrow
   }
}
