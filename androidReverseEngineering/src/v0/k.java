package v0;

import java.util.ArrayList;

public final class k extends q0.a {
   public final int b;
   public final s c;

   public k(s var1, Object[] var2, int var3, ArrayList var4, boolean var5) {
      super(var2, "OkHttp %s Push Headers[%s]");
      this.c = var1;
      this.b = var3;
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
      // 01: getfield v0/k.c Lv0/s;
      // 04: getfield v0/s.j Lp0/q;
      // 07: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 0a: pop
      // 0b: aload 0
      // 0c: getfield v0/k.c Lv0/s;
      // 0f: getfield v0/s.u Lv0/z;
      // 12: aload 0
      // 13: getfield v0/k.b I
      // 16: getstatic v0/b.g Lv0/b;
      // 19: invokevirtual v0/z.C (ILv0/b;)V
      // 1c: aload 0
      // 1d: getfield v0/k.c Lv0/s;
      // 20: astore 1
      // 21: aload 1
      // 22: monitorenter
      // 23: aload 0
      // 24: getfield v0/k.c Lv0/s;
      // 27: getfield v0/s.w Ljava/util/LinkedHashSet;
      // 2a: aload 0
      // 2b: getfield v0/k.b I
      // 2e: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 31: invokeinterface java/util/Set.remove (Ljava/lang/Object;)Z 2
      // 36: pop
      // 37: aload 1
      // 38: monitorexit
      // 39: goto 41
      // 3c: astore 2
      // 3d: aload 1
      // 3e: monitorexit
      // 3f: aload 2
      // 40: athrow
      // 41: return
      // 42: astore 1
      // 43: goto 41
   }
}
