package v0;

public final class r extends q0.a {
   public final boolean b;
   public final z.d c;
   public final q d;

   public r(q var1, Object[] var2, z.d var3) {
      this.d = var1;
      this.b = false;
      this.c = var3;
      super(var2, "OkHttp %s ACK Settings");
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
      // 000: aload 0
      // 001: getfield v0/r.d Lv0/q;
      // 004: astore 11
      // 006: aload 0
      // 007: getfield v0/r.b Z
      // 00a: istore 4
      // 00c: aload 0
      // 00d: getfield v0/r.c Lz/d;
      // 010: astore 9
      // 012: aload 11
      // 014: getfield v0/q.d Ljava/lang/Object;
      // 017: checkcast v0/s
      // 01a: getfield v0/s.u Lv0/z;
      // 01d: astore 10
      // 01f: aload 10
      // 021: monitorenter
      // 022: aload 11
      // 024: getfield v0/q.d Ljava/lang/Object;
      // 027: checkcast v0/s
      // 02a: astore 12
      // 02c: aload 12
      // 02e: monitorenter
      // 02f: aload 11
      // 031: getfield v0/q.d Ljava/lang/Object;
      // 034: checkcast v0/s
      // 037: getfield v0/s.s Lz/d;
      // 03a: invokevirtual z/d.d ()I
      // 03d: istore 3
      // 03e: iload 4
      // 040: ifeq 062
      // 043: aload 11
      // 045: getfield v0/q.d Ljava/lang/Object;
      // 048: checkcast v0/s
      // 04b: getfield v0/s.s Lz/d;
      // 04e: astore 13
      // 050: aload 13
      // 052: bipush 0
      // 053: putfield z/d.b I
      // 056: aload 13
      // 058: getfield z/d.c Ljava/lang/Object;
      // 05b: checkcast [I
      // 05e: bipush 0
      // 05f: invokestatic java/util/Arrays.fill ([II)V
      // 062: aload 11
      // 064: getfield v0/q.d Ljava/lang/Object;
      // 067: checkcast v0/s
      // 06a: getfield v0/s.s Lz/d;
      // 06d: astore 13
      // 06f: aload 13
      // 071: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 074: pop
      // 075: bipush 0
      // 076: istore 1
      // 077: bipush 1
      // 078: istore 2
      // 079: iload 1
      // 07a: bipush 10
      // 07c: if_icmpge 0b2
      // 07f: bipush 1
      // 080: iload 1
      // 081: ishl
      // 082: aload 9
      // 084: getfield z/d.b I
      // 087: iand
      // 088: ifeq 08e
      // 08b: goto 090
      // 08e: bipush 0
      // 08f: istore 2
      // 090: iload 2
      // 091: ifne 097
      // 094: goto 0a7
      // 097: aload 13
      // 099: iload 1
      // 09a: aload 9
      // 09c: getfield z/d.c Ljava/lang/Object;
      // 09f: checkcast [I
      // 0a2: iload 1
      // 0a3: iaload
      // 0a4: invokevirtual z/d.e (II)V
      // 0a7: iinc 1 1
      // 0aa: goto 077
      // 0ad: astore 9
      // 0af: goto 1ba
      // 0b2: aload 11
      // 0b4: getfield v0/q.d Ljava/lang/Object;
      // 0b7: checkcast v0/s
      // 0ba: getfield v0/s.s Lz/d;
      // 0bd: invokevirtual z/d.d ()I
      // 0c0: istore 1
      // 0c1: aconst_null
      // 0c2: astore 9
      // 0c4: iload 1
      // 0c5: bipush -1
      // 0c6: if_icmpeq 11d
      // 0c9: iload 1
      // 0ca: iload 3
      // 0cb: if_icmpeq 11d
      // 0ce: iload 1
      // 0cf: iload 3
      // 0d0: isub
      // 0d1: i2l
      // 0d2: lstore 7
      // 0d4: lload 7
      // 0d6: lstore 5
      // 0d8: aload 11
      // 0da: getfield v0/q.d Ljava/lang/Object;
      // 0dd: checkcast v0/s
      // 0e0: getfield v0/s.c Ljava/util/LinkedHashMap;
      // 0e3: invokeinterface java/util/Map.isEmpty ()Z 1
      // 0e8: ifne 120
      // 0eb: aload 11
      // 0ed: getfield v0/q.d Ljava/lang/Object;
      // 0f0: checkcast v0/s
      // 0f3: getfield v0/s.c Ljava/util/LinkedHashMap;
      // 0f6: invokevirtual java/util/LinkedHashMap.values ()Ljava/util/Collection;
      // 0f9: aload 11
      // 0fb: getfield v0/q.d Ljava/lang/Object;
      // 0fe: checkcast v0/s
      // 101: getfield v0/s.c Ljava/util/LinkedHashMap;
      // 104: invokeinterface java/util/Map.size ()I 1
      // 109: anewarray 90
      // 10c: invokeinterface java/util/Collection.toArray ([Ljava/lang/Object;)[Ljava/lang/Object; 2
      // 111: checkcast [Lv0/y;
      // 114: astore 9
      // 116: lload 7
      // 118: lstore 5
      // 11a: goto 120
      // 11d: lconst_0
      // 11e: lstore 5
      // 120: aload 12
      // 122: monitorexit
      // 123: aload 11
      // 125: getfield v0/q.d Ljava/lang/Object;
      // 128: astore 12
      // 12a: aload 12
      // 12c: checkcast v0/s
      // 12f: getfield v0/s.u Lv0/z;
      // 132: aload 12
      // 134: checkcast v0/s
      // 137: getfield v0/s.s Lz/d;
      // 13a: invokevirtual v0/z.x (Lz/d;)V
      // 13d: goto 14f
      // 140: astore 12
      // 142: aload 11
      // 144: getfield v0/q.d Ljava/lang/Object;
      // 147: checkcast v0/s
      // 14a: aload 12
      // 14c: invokevirtual v0/s.y (Ljava/io/IOException;)V
      // 14f: aload 10
      // 151: monitorexit
      // 152: aload 9
      // 154: ifnull 195
      // 157: aload 9
      // 159: arraylength
      // 15a: istore 2
      // 15b: bipush 0
      // 15c: istore 1
      // 15d: iload 1
      // 15e: iload 2
      // 15f: if_icmpge 195
      // 162: aload 9
      // 164: iload 1
      // 165: aaload
      // 166: astore 10
      // 168: aload 10
      // 16a: monitorenter
      // 16b: aload 10
      // 16d: aload 10
      // 16f: getfield v0/y.b J
      // 172: lload 5
      // 174: ladd
      // 175: putfield v0/y.b J
      // 178: lload 5
      // 17a: lconst_0
      // 17b: lcmp
      // 17c: ifle 184
      // 17f: aload 10
      // 181: invokevirtual java/lang/Object.notifyAll ()V
      // 184: aload 10
      // 186: monitorexit
      // 187: iinc 1 1
      // 18a: goto 15d
      // 18d: astore 9
      // 18f: aload 10
      // 191: monitorexit
      // 192: aload 9
      // 194: athrow
      // 195: getstatic v0/s.x Ljava/util/concurrent/ThreadPoolExecutor;
      // 198: new v0/j
      // 19b: dup
      // 19c: aload 11
      // 19e: ldc "OkHttp %s settings"
      // 1a0: bipush 1
      // 1a1: anewarray 62
      // 1a4: dup
      // 1a5: bipush 0
      // 1a6: aload 11
      // 1a8: getfield v0/q.d Ljava/lang/Object;
      // 1ab: checkcast v0/s
      // 1ae: getfield v0/s.d Ljava/lang/String;
      // 1b1: aastore
      // 1b2: bipush 2
      // 1b3: invokespecial v0/j.<init> (Ljava/lang/Object;Ljava/lang/String;[Ljava/lang/Object;I)V
      // 1b6: invokevirtual java/util/concurrent/ThreadPoolExecutor.execute (Ljava/lang/Runnable;)V
      // 1b9: return
      // 1ba: aload 12
      // 1bc: monitorexit
      // 1bd: aload 9
      // 1bf: athrow
      // 1c0: astore 9
      // 1c2: aload 10
      // 1c4: monitorexit
      // 1c5: aload 9
      // 1c7: athrow
   }
}
