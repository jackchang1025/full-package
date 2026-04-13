package v0;

public final class h extends q0.a {
   public final int b;
   public final int c;
   public final Object d;
   public final s e;

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
      // 01: getfield v0/h.b I
      // 04: tableswitch 24 0 1 73 27
      // 1c: goto 72
      // 1f: aload 0
      // 20: getfield v0/h.e Lv0/s;
      // 23: getfield v0/s.j Lp0/q;
      // 26: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 29: pop
      // 2a: aload 0
      // 2b: getfield v0/h.e Lv0/s;
      // 2e: astore 2
      // 2f: aload 2
      // 30: monitorenter
      // 31: aload 0
      // 32: getfield v0/h.e Lv0/s;
      // 35: getfield v0/s.w Ljava/util/LinkedHashSet;
      // 38: aload 0
      // 39: getfield v0/h.c I
      // 3c: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 3f: invokeinterface java/util/Set.remove (Ljava/lang/Object;)Z 2
      // 44: pop
      // 45: aload 2
      // 46: monitorexit
      // 47: return
      // 48: astore 3
      // 49: aload 2
      // 4a: monitorexit
      // 4b: aload 3
      // 4c: athrow
      // 4d: aload 0
      // 4e: getfield v0/h.e Lv0/s;
      // 51: astore 2
      // 52: aload 0
      // 53: getfield v0/h.c I
      // 56: istore 1
      // 57: aload 0
      // 58: getfield v0/h.d Ljava/lang/Object;
      // 5b: checkcast v0/b
      // 5e: astore 3
      // 5f: aload 2
      // 60: getfield v0/s.u Lv0/z;
      // 63: iload 1
      // 64: aload 3
      // 65: invokevirtual v0/z.C (ILv0/b;)V
      // 68: goto 71
      // 6b: astore 3
      // 6c: aload 2
      // 6d: aload 3
      // 6e: invokevirtual v0/s.y (Ljava/io/IOException;)V
      // 71: return
      // 72: aload 0
      // 73: getfield v0/h.e Lv0/s;
      // 76: getfield v0/s.j Lp0/q;
      // 79: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 7c: pop
      // 7d: aload 0
      // 7e: getfield v0/h.e Lv0/s;
      // 81: getfield v0/s.u Lv0/z;
      // 84: aload 0
      // 85: getfield v0/h.c I
      // 88: getstatic v0/b.g Lv0/b;
      // 8b: invokevirtual v0/z.C (ILv0/b;)V
      // 8e: aload 0
      // 8f: getfield v0/h.e Lv0/s;
      // 92: astore 3
      // 93: aload 3
      // 94: monitorenter
      // 95: aload 0
      // 96: getfield v0/h.e Lv0/s;
      // 99: getfield v0/s.w Ljava/util/LinkedHashSet;
      // 9c: aload 0
      // 9d: getfield v0/h.c I
      // a0: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // a3: invokeinterface java/util/Set.remove (Ljava/lang/Object;)Z 2
      // a8: pop
      // a9: aload 3
      // aa: monitorexit
      // ab: goto b3
      // ae: astore 2
      // af: aload 3
      // b0: monitorexit
      // b1: aload 2
      // b2: athrow
      // b3: return
      // b4: astore 2
      // b5: goto b3
   }
}
