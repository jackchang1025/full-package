package v0;

public final class q extends q0.a {
   public final int b;
   public final Object c;
   public final Object d;

   public q(q var1, Object[] var2, y var3) {
      this.b = 0;
      this.d = var1;
      this.c = var3;
      super(var2, "OkHttp %s stream %d");
   }

   public q(s var1, v var2) {
      this.b = 1;
      this.d = var1;
      super(new Object[]{var1.d}, "OkHttp %s");
      this.c = var2;
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
      // 00: getstatic v0/b.c Lv0/b;
      // 03: astore 3
      // 04: aload 0
      // 05: getfield v0/q.b I
      // 08: istore 1
      // 09: aload 0
      // 0a: getfield v0/q.d Ljava/lang/Object;
      // 0d: astore 6
      // 0f: aload 0
      // 10: getfield v0/q.c Ljava/lang/Object;
      // 13: astore 5
      // 15: iload 1
      // 16: tableswitch 18 0 0 21
      // 28: goto 80
      // 2b: aload 6
      // 2d: checkcast v0/q
      // 30: getfield v0/q.d Ljava/lang/Object;
      // 33: checkcast v0/s
      // 36: getfield v0/s.b Lv0/o;
      // 39: aload 5
      // 3b: checkcast v0/y
      // 3e: invokevirtual v0/o.b (Lv0/y;)V
      // 41: goto 7f
      // 44: astore 2
      // 45: getstatic w0/i.a Lw0/i;
      // 48: astore 4
      // 4a: new java/lang/StringBuilder
      // 4d: dup
      // 4e: ldc "Http2Connection.Listener failure for "
      // 50: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 53: astore 7
      // 55: aload 7
      // 57: aload 6
      // 59: checkcast v0/q
      // 5c: getfield v0/q.d Ljava/lang/Object;
      // 5f: checkcast v0/s
      // 62: getfield v0/s.d Ljava/lang/String;
      // 65: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 68: pop
      // 69: aload 4
      // 6b: bipush 4
      // 6c: aload 7
      // 6e: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 71: aload 2
      // 72: invokevirtual w0/i.m (ILjava/lang/String;Ljava/lang/Throwable;)V
      // 75: aload 5
      // 77: checkcast v0/y
      // 7a: aload 3
      // 7b: aload 2
      // 7c: invokevirtual v0/y.c (Lv0/b;Ljava/io/IOException;)V
      // 7f: return
      // 80: getstatic v0/b.d Lv0/b;
      // 83: astore 4
      // 85: aload 5
      // 87: checkcast v0/v
      // 8a: aload 0
      // 8b: invokevirtual v0/v.z (Lv0/q;)V
      // 8e: aload 5
      // 90: checkcast v0/v
      // 93: bipush 0
      // 94: aload 0
      // 95: invokevirtual v0/v.y (ZLv0/q;)Z
      // 98: ifeq 9e
      // 9b: goto 8e
      // 9e: getstatic v0/b.b Lv0/b;
      // a1: astore 2
      // a2: getstatic v0/b.g Lv0/b;
      // a5: astore 7
      // a7: aload 6
      // a9: checkcast v0/s
      // ac: aload 2
      // ad: aload 7
      // af: aconst_null
      // b0: invokevirtual v0/s.x (Lv0/b;Lv0/b;Ljava/io/IOException;)V
      // b3: goto e0
      // b6: astore 3
      // b7: goto be
      // ba: astore 3
      // bb: aload 4
      // bd: astore 2
      // be: aload 6
      // c0: checkcast v0/s
      // c3: aload 2
      // c4: aload 4
      // c6: aconst_null
      // c7: invokevirtual v0/s.x (Lv0/b;Lv0/b;Ljava/io/IOException;)V
      // ca: aload 5
      // cc: checkcast v0/v
      // cf: invokestatic q0/c.c (Ljava/io/Closeable;)V
      // d2: aload 3
      // d3: athrow
      // d4: astore 2
      // d5: aload 6
      // d7: checkcast v0/s
      // da: aload 3
      // db: aload 3
      // dc: aload 2
      // dd: invokevirtual v0/s.x (Lv0/b;Lv0/b;Ljava/io/IOException;)V
      // e0: aload 5
      // e2: checkcast v0/v
      // e5: invokestatic q0/c.c (Ljava/io/Closeable;)V
      // e8: return
      // e9: astore 2
      // ea: goto 7f
   }
}
