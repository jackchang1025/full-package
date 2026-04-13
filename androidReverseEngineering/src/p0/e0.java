package p0;

public final class e0 implements Cloneable {
   public final b0 a;
   public s0.l b;
   public final f0 c;
   public final boolean d;
   public boolean e;

   public e0(b0 var1, f0 var2, boolean var3) {
      this.a = var1;
      this.c = var2;
      this.d = var3;
   }

   public static e0 d(b0 var0, f0 var1, boolean var2) {
      e0 var3 = new e0(var0, var1, var2);
      var3.b = new s0.l(var0, var3);
      return var3;
   }

   public final void a(e param1) {
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
      // 03: getfield p0/e0.e Z
      // 06: ifne 7d
      // 09: aload 0
      // 0a: bipush 1
      // 0b: putfield p0/e0.e Z
      // 0e: aload 0
      // 0f: monitorexit
      // 10: aload 0
      // 11: getfield p0/e0.b Ls0/l;
      // 14: astore 2
      // 15: aload 2
      // 16: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 19: pop
      // 1a: aload 2
      // 1b: getstatic w0/i.a Lw0/i;
      // 1e: invokevirtual w0/i.k ()Ljava/lang/Object;
      // 21: putfield s0/l.f Ljava/lang/Object;
      // 24: aload 2
      // 25: getfield s0/l.d Lp0/q;
      // 28: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 2b: pop
      // 2c: aload 0
      // 2d: getfield p0/e0.a Lp0/b0;
      // 30: getfield p0/b0.a Lp0/o;
      // 33: astore 2
      // 34: new p0/d0
      // 37: dup
      // 38: aload 0
      // 39: aload 1
      // 3a: invokespecial p0/d0.<init> (Lp0/e0;Lp0/e;)V
      // 3d: astore 1
      // 3e: aload 2
      // 3f: monitorenter
      // 40: aload 2
      // 41: getfield p0/o.b Ljava/util/ArrayDeque;
      // 44: aload 1
      // 45: invokevirtual java/util/ArrayDeque.add (Ljava/lang/Object;)Z
      // 48: pop
      // 49: aload 0
      // 4a: getfield p0/e0.d Z
      // 4d: ifne 72
      // 50: aload 2
      // 51: aload 0
      // 52: getfield p0/e0.c Lp0/f0;
      // 55: getfield p0/f0.a Lp0/u;
      // 58: getfield p0/u.d Ljava/lang/String;
      // 5b: invokevirtual p0/o.a (Ljava/lang/String;)Lp0/d0;
      // 5e: astore 3
      // 5f: aload 3
      // 60: ifnull 72
      // 63: aload 1
      // 64: aload 3
      // 65: getfield p0/d0.c Ljava/util/concurrent/atomic/AtomicInteger;
      // 68: putfield p0/d0.c Ljava/util/concurrent/atomic/AtomicInteger;
      // 6b: goto 72
      // 6e: astore 1
      // 6f: goto 79
      // 72: aload 2
      // 73: monitorexit
      // 74: aload 2
      // 75: invokevirtual p0/o.c ()V
      // 78: return
      // 79: aload 2
      // 7a: monitorexit
      // 7b: aload 1
      // 7c: athrow
      // 7d: new java/lang/IllegalStateException
      // 80: astore 1
      // 81: aload 1
      // 82: ldc "Already Executed"
      // 84: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 87: aload 1
      // 88: athrow
      // 89: astore 1
      // 8a: aload 0
      // 8b: monitorexit
      // 8c: aload 1
      // 8d: athrow
   }

   public final j0 b() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: aload 0
      // 01: monitorenter
      // 02: aload 0
      // 03: getfield p0/e0.e Z
      // 06: ifne b6
      // 09: aload 0
      // 0a: bipush 1
      // 0b: putfield p0/e0.e Z
      // 0e: aload 0
      // 0f: monitorexit
      // 10: aload 0
      // 11: getfield p0/e0.b Ls0/l;
      // 14: getfield s0/l.e Ls0/j;
      // 17: invokevirtual a1/d.i ()V
      // 1a: aload 0
      // 1b: getfield p0/e0.b Ls0/l;
      // 1e: astore 1
      // 1f: aload 1
      // 20: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 23: pop
      // 24: aload 1
      // 25: getstatic w0/i.a Lw0/i;
      // 28: invokevirtual w0/i.k ()Ljava/lang/Object;
      // 2b: putfield s0/l.f Ljava/lang/Object;
      // 2e: aload 1
      // 2f: getfield s0/l.d Lp0/q;
      // 32: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 35: pop
      // 36: aload 0
      // 37: getfield p0/e0.a Lp0/b0;
      // 3a: getfield p0/b0.a Lp0/o;
      // 3d: astore 2
      // 3e: aload 2
      // 3f: monitorenter
      // 40: aload 2
      // 41: getfield p0/o.d Ljava/util/ArrayDeque;
      // 44: aload 0
      // 45: invokevirtual java/util/ArrayDeque.add (Ljava/lang/Object;)Z
      // 48: pop
      // 49: aload 2
      // 4a: monitorexit
      // 4b: aload 0
      // 4c: invokevirtual p0/e0.c ()Lp0/j0;
      // 4f: astore 2
      // 50: aload 0
      // 51: getfield p0/e0.a Lp0/b0;
      // 54: getfield p0/b0.a Lp0/o;
      // 57: astore 1
      // 58: aload 1
      // 59: getfield p0/o.d Ljava/util/ArrayDeque;
      // 5c: astore 3
      // 5d: aload 1
      // 5e: monitorenter
      // 5f: aload 3
      // 60: aload 0
      // 61: invokevirtual java/util/ArrayDeque.remove (Ljava/lang/Object;)Z
      // 64: ifeq 6f
      // 67: aload 1
      // 68: monitorexit
      // 69: aload 1
      // 6a: invokevirtual p0/o.c ()V
      // 6d: aload 2
      // 6e: areturn
      // 6f: new java/lang/AssertionError
      // 72: astore 2
      // 73: aload 2
      // 74: ldc "Call wasn't in-flight!"
      // 76: invokespecial java/lang/AssertionError.<init> (Ljava/lang/Object;)V
      // 79: aload 2
      // 7a: athrow
      // 7b: astore 2
      // 7c: aload 1
      // 7d: monitorexit
      // 7e: aload 2
      // 7f: athrow
      // 80: astore 1
      // 81: aload 2
      // 82: monitorexit
      // 83: aload 1
      // 84: athrow
      // 85: astore 3
      // 86: aload 0
      // 87: getfield p0/e0.a Lp0/b0;
      // 8a: getfield p0/b0.a Lp0/o;
      // 8d: astore 1
      // 8e: aload 1
      // 8f: getfield p0/o.d Ljava/util/ArrayDeque;
      // 92: astore 2
      // 93: aload 1
      // 94: monitorenter
      // 95: aload 2
      // 96: aload 0
      // 97: invokevirtual java/util/ArrayDeque.remove (Ljava/lang/Object;)Z
      // 9a: ifeq a5
      // 9d: aload 1
      // 9e: monitorexit
      // 9f: aload 1
      // a0: invokevirtual p0/o.c ()V
      // a3: aload 3
      // a4: athrow
      // a5: new java/lang/AssertionError
      // a8: astore 2
      // a9: aload 2
      // aa: ldc "Call wasn't in-flight!"
      // ac: invokespecial java/lang/AssertionError.<init> (Ljava/lang/Object;)V
      // af: aload 2
      // b0: athrow
      // b1: astore 2
      // b2: aload 1
      // b3: monitorexit
      // b4: aload 2
      // b5: athrow
      // b6: new java/lang/IllegalStateException
      // b9: astore 1
      // ba: aload 1
      // bb: ldc "Already Executed"
      // bd: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // c0: aload 1
      // c1: athrow
      // c2: astore 1
      // c3: aload 0
      // c4: monitorexit
      // c5: aload 1
      // c6: athrow
   }

   public final j0 c() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 000: new java/util/ArrayList
      // 003: dup
      // 004: invokespecial java/util/ArrayList.<init> ()V
      // 007: astore 4
      // 009: aload 0
      // 00a: getfield p0/e0.a Lp0/b0;
      // 00d: astore 3
      // 00e: aload 4
      // 010: aload 3
      // 011: getfield p0/b0.d Ljava/util/List;
      // 014: invokevirtual java/util/ArrayList.addAll (Ljava/util/Collection;)Z
      // 017: pop
      // 018: bipush 1
      // 019: istore 1
      // 01a: aload 4
      // 01c: new s0/a
      // 01f: dup
      // 020: aload 3
      // 021: bipush 1
      // 022: invokespecial s0/a.<init> (Lp0/b0;I)V
      // 025: invokevirtual java/util/ArrayList.add (Ljava/lang/Object;)Z
      // 028: pop
      // 029: aload 4
      // 02b: new r0/a
      // 02e: dup
      // 02f: aload 3
      // 030: getfield p0/b0.h Lp0/n;
      // 033: bipush 1
      // 034: invokespecial r0/a.<init> (Lp0/n;I)V
      // 037: invokevirtual java/util/ArrayList.add (Ljava/lang/Object;)Z
      // 03a: pop
      // 03b: aload 4
      // 03d: new r0/a
      // 040: dup
      // 041: aconst_null
      // 042: bipush 0
      // 043: invokespecial r0/a.<init> (Lp0/n;I)V
      // 046: invokevirtual java/util/ArrayList.add (Ljava/lang/Object;)Z
      // 049: pop
      // 04a: aload 4
      // 04c: new s0/a
      // 04f: dup
      // 050: aload 3
      // 051: bipush 0
      // 052: invokespecial s0/a.<init> (Lp0/b0;I)V
      // 055: invokevirtual java/util/ArrayList.add (Ljava/lang/Object;)Z
      // 058: pop
      // 059: aload 0
      // 05a: getfield p0/e0.d Z
      // 05d: istore 2
      // 05e: iload 2
      // 05f: ifne 06c
      // 062: aload 4
      // 064: aload 3
      // 065: getfield p0/b0.e Ljava/util/List;
      // 068: invokevirtual java/util/ArrayList.addAll (Ljava/util/Collection;)Z
      // 06b: pop
      // 06c: aload 4
      // 06e: new t0/a
      // 071: dup
      // 072: iload 2
      // 073: invokespecial t0/a.<init> (Z)V
      // 076: invokevirtual java/util/ArrayList.add (Ljava/lang/Object;)Z
      // 079: pop
      // 07a: new t0/f
      // 07d: dup
      // 07e: aload 4
      // 080: aload 0
      // 081: getfield p0/e0.b Ls0/l;
      // 084: aconst_null
      // 085: bipush 0
      // 086: aload 0
      // 087: getfield p0/e0.c Lp0/f0;
      // 08a: aload 0
      // 08b: aload 3
      // 08c: getfield p0/b0.v I
      // 08f: aload 3
      // 090: getfield p0/b0.w I
      // 093: aload 3
      // 094: getfield p0/b0.x I
      // 097: invokespecial t0/f.<init> (Ljava/util/List;Ls0/l;Ls0/e;ILp0/f0;Lp0/e0;III)V
      // 09a: astore 3
      // 09b: aload 3
      // 09c: aload 0
      // 09d: getfield p0/e0.c Lp0/f0;
      // 0a0: invokevirtual t0/f.a (Lp0/f0;)Lp0/j0;
      // 0a3: astore 4
      // 0a5: aload 0
      // 0a6: getfield p0/e0.b Ls0/l;
      // 0a9: astore 5
      // 0ab: aload 5
      // 0ad: getfield s0/l.b Ls0/h;
      // 0b0: astore 3
      // 0b1: aload 3
      // 0b2: monitorenter
      // 0b3: aload 5
      // 0b5: getfield s0/l.m Z
      // 0b8: istore 2
      // 0b9: aload 3
      // 0ba: monitorexit
      // 0bb: iload 2
      // 0bc: ifne 0cb
      // 0bf: aload 0
      // 0c0: getfield p0/e0.b Ls0/l;
      // 0c3: aconst_null
      // 0c4: invokevirtual s0/l.e (Ljava/io/IOException;)Ljava/io/IOException;
      // 0c7: pop
      // 0c8: aload 4
      // 0ca: areturn
      // 0cb: aload 4
      // 0cd: invokestatic q0/c.c (Ljava/io/Closeable;)V
      // 0d0: new java/io/IOException
      // 0d3: astore 3
      // 0d4: aload 3
      // 0d5: ldc "Canceled"
      // 0d7: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
      // 0da: aload 3
      // 0db: athrow
      // 0dc: astore 4
      // 0de: aload 3
      // 0df: monitorexit
      // 0e0: aload 4
      // 0e2: athrow
      // 0e3: astore 3
      // 0e4: goto 0ed
      // 0e7: astore 3
      // 0e8: bipush 0
      // 0e9: istore 1
      // 0ea: goto 0f7
      // 0ed: aload 0
      // 0ee: getfield p0/e0.b Ls0/l;
      // 0f1: aload 3
      // 0f2: invokevirtual s0/l.e (Ljava/io/IOException;)Ljava/io/IOException;
      // 0f5: athrow
      // 0f6: astore 3
      // 0f7: iload 1
      // 0f8: ifne 104
      // 0fb: aload 0
      // 0fc: getfield p0/e0.b Ls0/l;
      // 0ff: aconst_null
      // 100: invokevirtual s0/l.e (Ljava/io/IOException;)Ljava/io/IOException;
      // 103: pop
      // 104: aload 3
      // 105: athrow
   }

   @Override
   public final Object clone() {
      boolean var1 = this.d;
      return d(this.a, this.c, var1);
   }

   public final String e() {
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
      // 00: new java/lang/StringBuilder
      // 03: dup
      // 04: invokespecial java/lang/StringBuilder.<init> ()V
      // 07: astore 3
      // 08: aload 0
      // 09: getfield p0/e0.b Ls0/l;
      // 0c: astore 4
      // 0e: aload 4
      // 10: getfield s0/l.b Ls0/h;
      // 13: astore 2
      // 14: aload 2
      // 15: monitorenter
      // 16: aload 4
      // 18: getfield s0/l.m Z
      // 1b: istore 1
      // 1c: aload 2
      // 1d: monitorexit
      // 1e: iload 1
      // 1f: ifeq 28
      // 22: ldc "canceled "
      // 24: astore 2
      // 25: goto 2b
      // 28: ldc ""
      // 2a: astore 2
      // 2b: aload 3
      // 2c: aload 2
      // 2d: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 30: pop
      // 31: aload 0
      // 32: getfield p0/e0.d Z
      // 35: ifeq 3e
      // 38: ldc "web socket"
      // 3a: astore 2
      // 3b: goto 41
      // 3e: ldc "call"
      // 40: astore 2
      // 41: aload 3
      // 42: aload 2
      // 43: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 46: pop
      // 47: aload 3
      // 48: ldc " to "
      // 4a: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 4d: pop
      // 4e: aload 3
      // 4f: aload 0
      // 50: getfield p0/e0.c Lp0/f0;
      // 53: getfield p0/f0.a Lp0/u;
      // 56: invokevirtual p0/u.m ()Ljava/lang/String;
      // 59: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 5c: pop
      // 5d: aload 3
      // 5e: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 61: areturn
      // 62: astore 3
      // 63: aload 2
      // 64: monitorexit
      // 65: aload 3
      // 66: athrow
   }
}
