package s0;

import java.io.IOException;
import p0.e0;
import p0.i0;
import p0.q;

public final class e {
   public final l a;
   public final q b;
   public final f c;
   public final t0.b d;
   public boolean e;

   public e(l var1, e0 var2, q var3, f var4, t0.b var5) {
      this.a = var1;
      this.b = var3;
      this.c = var4;
      this.d = var5;
   }

   public final g a() {
      return this.d.h();
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final i0 b(boolean var1) {
      IOException var10000;
      label24: {
         i0 var2;
         try {
            var2 = this.d.g(var1);
         } catch (IOException var4) {
            var10000 = var4;
            boolean var10001 = false;
            break label24;
         }

         if (var2 == null) {
            return var2;
         }

         try {
            q.c.getClass();
            var2.m = this;
            return var2;
         } catch (IOException var3) {
            var10000 = var3;
            boolean var6 = false;
         }
      }

      IOException var5 = var10000;
      this.b.getClass();
      this.c(var5);
      throw var5;
   }

   public final void c(IOException param1) {
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
      // 00: aload 0
      // 01: getfield s0/e.c Ls0/f;
      // 04: astore 4
      // 06: aload 4
      // 08: getfield s0/f.c Ls0/h;
      // 0b: astore 3
      // 0c: aload 3
      // 0d: monitorenter
      // 0e: aload 4
      // 10: bipush 1
      // 11: putfield s0/f.h Z
      // 14: aload 3
      // 15: monitorexit
      // 16: aload 0
      // 17: getfield s0/e.d Lt0/b;
      // 1a: invokeinterface t0/b.h ()Ls0/g; 1
      // 1f: astore 4
      // 21: aload 4
      // 23: getfield s0/g.b Ls0/h;
      // 26: astore 3
      // 27: aload 3
      // 28: monitorenter
      // 29: aload 1
      // 2a: instanceof v0/d0
      // 2d: ifeq 65
      // 30: aload 1
      // 31: checkcast v0/d0
      // 34: getfield v0/d0.a Lv0/b;
      // 37: astore 1
      // 38: aload 1
      // 39: getstatic v0/b.f Lv0/b;
      // 3c: if_acmpne 5b
      // 3f: aload 4
      // 41: getfield s0/g.n I
      // 44: bipush 1
      // 45: iadd
      // 46: istore 2
      // 47: aload 4
      // 49: iload 2
      // 4a: putfield s0/g.n I
      // 4d: iload 2
      // 4e: bipush 1
      // 4f: if_icmple ab
      // 52: aload 4
      // 54: bipush 1
      // 55: putfield s0/g.k Z
      // 58: goto 9f
      // 5b: aload 1
      // 5c: getstatic v0/b.g Lv0/b;
      // 5f: if_acmpeq ab
      // 62: goto 52
      // 65: aload 4
      // 67: getfield s0/g.h Lv0/s;
      // 6a: ifnull 72
      // 6d: bipush 1
      // 6e: istore 2
      // 6f: goto 74
      // 72: bipush 0
      // 73: istore 2
      // 74: iload 2
      // 75: ifeq 7f
      // 78: aload 1
      // 79: instanceof v0/a
      // 7c: ifeq ab
      // 7f: aload 4
      // 81: bipush 1
      // 82: putfield s0/g.k Z
      // 85: aload 4
      // 87: getfield s0/g.m I
      // 8a: ifne ab
      // 8d: aload 1
      // 8e: ifnull 9f
      // 91: aload 4
      // 93: getfield s0/g.b Ls0/h;
      // 96: aload 4
      // 98: getfield s0/g.c Lp0/m0;
      // 9b: aload 1
      // 9c: invokevirtual s0/h.a (Lp0/m0;Ljava/io/IOException;)V
      // 9f: aload 4
      // a1: aload 4
      // a3: getfield s0/g.l I
      // a6: bipush 1
      // a7: iadd
      // a8: putfield s0/g.l I
      // ab: aload 3
      // ac: monitorexit
      // ad: return
      // ae: astore 1
      // af: aload 3
      // b0: monitorexit
      // b1: aload 1
      // b2: athrow
      // b3: astore 1
      // b4: aload 3
      // b5: monitorexit
      // b6: aload 1
      // b7: athrow
   }
}
