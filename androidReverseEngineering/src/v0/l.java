package v0;

public final class l extends q0.a {
   public final int b;
   public final a1.e c;
   public final int d;
   public final s e;

   public l(s var1, Object[] var2, int var3, a1.e var4, int var5, boolean var6) {
      super(var2, "OkHttp %s Push Data[%s]");
      this.e = var1;
      this.b = var3;
      this.c = var4;
      this.d = var5;
   }

   @Override
   public final void a() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield v0/l.e Lv0/s;
      // 04: getfield v0/s.j Lp0/q;
      // 07: astore 2
      // 08: aload 0
      // 09: getfield v0/l.c La1/e;
      // 0c: astore 3
      // 0d: aload 0
      // 0e: getfield v0/l.d I
      // 11: istore 1
      // 12: aload 2
      // 13: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 16: pop
      // 17: aload 3
      // 18: iload 1
      // 19: i2l
      // 1a: invokevirtual a1/e.skip (J)V
      // 1d: aload 0
      // 1e: getfield v0/l.e Lv0/s;
      // 21: getfield v0/s.u Lv0/z;
      // 24: aload 0
      // 25: getfield v0/l.b I
      // 28: getstatic v0/b.g Lv0/b;
      // 2b: invokevirtual v0/z.C (ILv0/b;)V
      // 2e: aload 0
      // 2f: getfield v0/l.e Lv0/s;
      // 32: astore 2
      // 33: aload 2
      // 34: monitorenter
      // 35: aload 0
      // 36: getfield v0/l.e Lv0/s;
      // 39: getfield v0/s.w Ljava/util/LinkedHashSet;
      // 3c: aload 0
      // 3d: getfield v0/l.b I
      // 40: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 43: invokeinterface java/util/Set.remove (Ljava/lang/Object;)Z 2
      // 48: pop
      // 49: aload 2
      // 4a: monitorexit
      // 4b: goto 53
      // 4e: astore 3
      // 4f: aload 2
      // 50: monitorexit
      // 51: aload 3
      // 52: athrow
      // 53: return
      // 54: astore 2
      // 55: goto 53
   }
}
