package h0;

import f0.m;
import f0.t;
import java.nio.charset.Charset;

// $VF: synthetic class
public final class e implements g, i {
   public final Object d;
   public final Object e;

   @Override
   public final Object a(Object var1) {
      com.guard.wallet.http.h var2 = (com.guard.wallet.http.h)this.d;
      String var4 = (String)this.e;
      m var3 = (m)var1;
      Charset var7 = (Charset)var2.e;
      var1 = var7;
      if (var7 == null) {
         var1 = var7;
         if (var4 != null) {
            var1 = Charset.forName(var4);
         }
      }

      String var6 = var3.h(var1);
      var3.k();
      return var6;
   }

   @Override
   public final void b(Exception param1, Object param2, t param3) {
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
      // 01: getfield h0/e.d Ljava/lang/Object;
      // 04: checkcast h0/h
      // 07: astore 4
      // 09: aload 0
      // 0a: getfield h0/e.e Ljava/lang/Object;
      // 0d: checkcast f0/l
      // 10: astore 5
      // 12: aload 1
      // 13: ifnull 19
      // 16: goto 68
      // 19: new h0/h
      // 1c: dup
      // 1d: aload 5
      // 1f: getfield f0/l.d Ljava/lang/Object;
      // 22: checkcast h0/i
      // 25: aload 2
      // 26: invokeinterface h0/i.a (Ljava/lang/Object;)Ljava/lang/Object; 2
      // 2b: invokespecial h0/h.<init> (Ljava/lang/Object;)V
      // 2e: astore 1
      // 2f: aload 4
      // 31: monitorenter
      // 32: aload 4
      // 34: getfield h0/d.a Z
      // 37: ifeq 40
      // 3a: aload 4
      // 3c: monitorexit
      // 3d: goto 49
      // 40: aload 4
      // 42: aload 1
      // 43: putfield h0/d.c Lh0/a;
      // 46: goto 3a
      // 49: aload 1
      // 4a: aload 3
      // 4b: new h0/f
      // 4e: dup
      // 4f: aload 4
      // 51: new h0/h
      // 54: dup
      // 55: invokespecial h0/h.<init> ()V
      // 58: invokespecial h0/f.<init> (Lh0/h;Lh0/h;)V
      // 5b: invokevirtual h0/h.f (Lf0/t;Lh0/g;)V
      // 5e: goto 71
      // 61: astore 1
      // 62: aload 4
      // 64: monitorexit
      // 65: aload 1
      // 66: athrow
      // 67: astore 1
      // 68: aload 4
      // 6a: aload 1
      // 6b: aconst_null
      // 6c: aload 3
      // 6d: invokevirtual h0/h.g (Ljava/lang/Exception;Ljava/lang/Object;Lf0/t;)Z
      // 70: pop
      // 71: return
   }
}
