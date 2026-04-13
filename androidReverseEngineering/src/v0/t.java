package v0;

import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import p0.f0;
import p0.i0;
import p0.j0;

public final class t implements t0.b {
   public static final List g = q0.c.l(
      "connection", "host", "keep-alive", "proxy-connection", "te", "transfer-encoding", "encoding", "upgrade", ":method", ":path", ":scheme", ":authority"
   );
   public static final List h = q0.c.l("connection", "host", "keep-alive", "proxy-connection", "te", "transfer-encoding", "encoding", "upgrade");
   public final p0.v a;
   public final s0.g b;
   public final s c;
   public volatile y d;
   public final p0.c0 e;
   public volatile boolean f;

   public t(p0.b0 var1, s0.g var2, t0.f var3, s var4) {
      this.b = var2;
      this.a = var3;
      this.c = var4;
      p0.c0 var6 = p0.c0.f;
      p0.c0 var5;
      if (var1.b.contains(var6)) {
         var5 = var6;
      } else {
         var5 = p0.c0.e;
      }

      this.e = var5;
   }

   @Override
   public final a1.t a(j0 var1) {
      return this.d.g;
   }

   @Override
   public final a1.s b(f0 param1, long param2) {
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
      // 01: getfield v0/t.d Lv0/y;
      // 04: astore 1
      // 05: aload 1
      // 06: monitorenter
      // 07: aload 1
      // 08: getfield v0/y.f Z
      // 0b: ifne 27
      // 0e: aload 1
      // 0f: invokevirtual v0/y.f ()Z
      // 12: ifeq 18
      // 15: goto 27
      // 18: new java/lang/IllegalStateException
      // 1b: astore 4
      // 1d: aload 4
      // 1f: ldc "reply before requesting the sink"
      // 21: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 24: aload 4
      // 26: athrow
      // 27: aload 1
      // 28: monitorexit
      // 29: aload 1
      // 2a: getfield v0/y.h Lv0/w;
      // 2d: areturn
      // 2e: astore 4
      // 30: aload 1
      // 31: monitorexit
      // 32: aload 4
      // 34: athrow
   }

   @Override
   public final void c() {
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
      // 01: getfield v0/t.d Lv0/y;
      // 04: astore 1
      // 05: aload 1
      // 06: monitorenter
      // 07: aload 1
      // 08: getfield v0/y.f Z
      // 0b: ifne 24
      // 0e: aload 1
      // 0f: invokevirtual v0/y.f ()Z
      // 12: ifeq 18
      // 15: goto 24
      // 18: new java/lang/IllegalStateException
      // 1b: astore 2
      // 1c: aload 2
      // 1d: ldc "reply before requesting the sink"
      // 1f: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 22: aload 2
      // 23: athrow
      // 24: aload 1
      // 25: monitorexit
      // 26: aload 1
      // 27: getfield v0/y.h Lv0/w;
      // 2a: invokevirtual v0/w.close ()V
      // 2d: return
      // 2e: astore 2
      // 2f: aload 1
      // 30: monitorexit
      // 31: aload 2
      // 32: athrow
   }

   @Override
   public final void cancel() {
      this.f = true;
      if (this.d != null) {
         this.d.e(v0.b.g);
      }
   }

   @Override
   public final void d() {
      this.c.flush();
   }

   @Override
   public final void e(f0 param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 000: aload 0
      // 001: getfield v0/t.d Lv0/y;
      // 004: ifnull 008
      // 007: return
      // 008: aload 1
      // 009: getfield p0/f0.d La1/q;
      // 00c: ifnull 014
      // 00f: bipush 1
      // 010: istore 3
      // 011: goto 016
      // 014: bipush 0
      // 015: istore 3
      // 016: aload 1
      // 017: getfield p0/f0.c Lp0/s;
      // 01a: astore 13
      // 01c: new java/util/ArrayList
      // 01f: dup
      // 020: aload 13
      // 022: getfield p0/s.a [Ljava/lang/String;
      // 025: arraylength
      // 026: bipush 2
      // 027: idiv
      // 028: bipush 4
      // 029: iadd
      // 02a: invokespecial java/util/ArrayList.<init> (I)V
      // 02d: astore 12
      // 02f: aload 12
      // 031: new v0/c
      // 034: dup
      // 035: getstatic v0/c.f La1/h;
      // 038: aload 1
      // 039: getfield p0/f0.b Ljava/lang/String;
      // 03c: invokespecial v0/c.<init> (La1/h;Ljava/lang/String;)V
      // 03f: invokevirtual java/util/ArrayList.add (Ljava/lang/Object;)Z
      // 042: pop
      // 043: getstatic v0/c.g La1/h;
      // 046: astore 15
      // 048: aload 1
      // 049: getfield p0/f0.a Lp0/u;
      // 04c: astore 14
      // 04e: aload 12
      // 050: new v0/c
      // 053: dup
      // 054: aload 15
      // 056: aload 14
      // 058: invokestatic com/guard/wallet/utils/g.L0 (Lp0/u;)Ljava/lang/String;
      // 05b: invokespecial v0/c.<init> (La1/h;Ljava/lang/String;)V
      // 05e: invokevirtual java/util/ArrayList.add (Ljava/lang/Object;)Z
      // 061: pop
      // 062: aload 1
      // 063: ldc "Host"
      // 065: invokevirtual p0/f0.a (Ljava/lang/String;)Ljava/lang/String;
      // 068: astore 1
      // 069: aload 1
      // 06a: ifnull 07e
      // 06d: aload 12
      // 06f: new v0/c
      // 072: dup
      // 073: getstatic v0/c.i La1/h;
      // 076: aload 1
      // 077: invokespecial v0/c.<init> (La1/h;Ljava/lang/String;)V
      // 07a: invokevirtual java/util/ArrayList.add (Ljava/lang/Object;)Z
      // 07d: pop
      // 07e: aload 12
      // 080: new v0/c
      // 083: dup
      // 084: getstatic v0/c.h La1/h;
      // 087: aload 14
      // 089: getfield p0/u.a Ljava/lang/String;
      // 08c: invokespecial v0/c.<init> (La1/h;Ljava/lang/String;)V
      // 08f: invokevirtual java/util/ArrayList.add (Ljava/lang/Object;)Z
      // 092: pop
      // 093: aload 13
      // 095: getfield p0/s.a [Ljava/lang/String;
      // 098: arraylength
      // 099: bipush 2
      // 09a: idiv
      // 09b: istore 5
      // 09d: bipush 0
      // 09e: istore 4
      // 0a0: iload 4
      // 0a2: iload 5
      // 0a4: if_icmpge 0f4
      // 0a7: aload 13
      // 0a9: iload 4
      // 0ab: invokevirtual p0/s.d (I)Ljava/lang/String;
      // 0ae: getstatic java/util/Locale.US Ljava/util/Locale;
      // 0b1: invokevirtual java/lang/String.toLowerCase (Ljava/util/Locale;)Ljava/lang/String;
      // 0b4: astore 1
      // 0b5: getstatic v0/t.g Ljava/util/List;
      // 0b8: aload 1
      // 0b9: invokeinterface java/util/List.contains (Ljava/lang/Object;)Z 2
      // 0be: ifeq 0d9
      // 0c1: aload 1
      // 0c2: ldc "te"
      // 0c4: invokevirtual java/lang/String.equals (Ljava/lang/Object;)Z
      // 0c7: ifeq 0ee
      // 0ca: aload 13
      // 0cc: iload 4
      // 0ce: invokevirtual p0/s.f (I)Ljava/lang/String;
      // 0d1: ldc "trailers"
      // 0d3: invokevirtual java/lang/String.equals (Ljava/lang/Object;)Z
      // 0d6: ifeq 0ee
      // 0d9: aload 12
      // 0db: new v0/c
      // 0de: dup
      // 0df: aload 1
      // 0e0: aload 13
      // 0e2: iload 4
      // 0e4: invokevirtual p0/s.f (I)Ljava/lang/String;
      // 0e7: invokespecial v0/c.<init> (Ljava/lang/String;Ljava/lang/String;)V
      // 0ea: invokevirtual java/util/ArrayList.add (Ljava/lang/Object;)Z
      // 0ed: pop
      // 0ee: iinc 4 1
      // 0f1: goto 0a0
      // 0f4: aload 0
      // 0f5: getfield v0/t.c Lv0/s;
      // 0f8: astore 14
      // 0fa: iload 3
      // 0fb: bipush 1
      // 0fc: ixor
      // 0fd: istore 11
      // 0ff: aload 14
      // 101: getfield v0/s.u Lv0/z;
      // 104: astore 1
      // 105: aload 1
      // 106: monitorenter
      // 107: aload 14
      // 109: monitorenter
      // 10a: aload 14
      // 10c: getfield v0/s.f I
      // 10f: ldc 1073741823
      // 111: if_icmple 11c
      // 114: aload 14
      // 116: getstatic v0/b.f Lv0/b;
      // 119: invokevirtual v0/s.C (Lv0/b;)V
      // 11c: aload 14
      // 11e: getfield v0/s.g Z
      // 121: ifne 2bd
      // 124: aload 14
      // 126: getfield v0/s.f I
      // 129: istore 4
      // 12b: aload 14
      // 12d: iload 4
      // 12f: bipush 2
      // 130: iadd
      // 131: putfield v0/s.f I
      // 134: new v0/y
      // 137: astore 15
      // 139: aload 15
      // 13b: iload 4
      // 13d: aload 14
      // 13f: iload 11
      // 141: bipush 0
      // 142: aconst_null
      // 143: invokespecial v0/y.<init> (ILv0/s;ZZLp0/s;)V
      // 146: iload 3
      // 147: ifeq 166
      // 14a: aload 14
      // 14c: getfield v0/s.q J
      // 14f: lconst_0
      // 150: lcmp
      // 151: ifeq 166
      // 154: aload 15
      // 156: getfield v0/y.b J
      // 159: lconst_0
      // 15a: lcmp
      // 15b: ifne 161
      // 15e: goto 166
      // 161: bipush 0
      // 162: istore 3
      // 163: goto 168
      // 166: bipush 1
      // 167: istore 3
      // 168: aload 15
      // 16a: invokevirtual v0/y.g ()Z
      // 16d: ifeq 182
      // 170: aload 14
      // 172: getfield v0/s.c Ljava/util/LinkedHashMap;
      // 175: iload 4
      // 177: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 17a: aload 15
      // 17c: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 181: pop
      // 182: aload 14
      // 184: monitorexit
      // 185: aload 14
      // 187: getfield v0/s.u Lv0/z;
      // 18a: astore 13
      // 18c: aload 13
      // 18e: monitorenter
      // 18f: aload 13
      // 191: getfield v0/z.e Z
      // 194: ifne 2a5
      // 197: aload 13
      // 199: getfield v0/z.f Lv0/e;
      // 19c: aload 12
      // 19e: invokevirtual v0/e.d (Ljava/util/ArrayList;)V
      // 1a1: aload 13
      // 1a3: getfield v0/z.c La1/e;
      // 1a6: getfield a1/e.b J
      // 1a9: lstore 9
      // 1ab: aload 13
      // 1ad: getfield v0/z.d I
      // 1b0: i2l
      // 1b1: lload 9
      // 1b3: invokestatic java/lang/Math.min (JJ)J
      // 1b6: l2i
      // 1b7: istore 5
      // 1b9: iload 5
      // 1bb: i2l
      // 1bc: lstore 7
      // 1be: lload 9
      // 1c0: lload 7
      // 1c2: lcmp
      // 1c3: istore 6
      // 1c5: iload 6
      // 1c7: ifne 1cf
      // 1ca: bipush 4
      // 1cb: istore 2
      // 1cc: goto 1d1
      // 1cf: bipush 0
      // 1d0: istore 2
      // 1d1: iload 11
      // 1d3: ifeq 1de
      // 1d6: iload 2
      // 1d7: bipush 1
      // 1d8: ior
      // 1d9: i2b
      // 1da: istore 2
      // 1db: goto 1de
      // 1de: aload 13
      // 1e0: iload 4
      // 1e2: iload 5
      // 1e4: bipush 1
      // 1e5: iload 2
      // 1e6: invokevirtual v0/z.z (IIBB)V
      // 1e9: aload 13
      // 1eb: getfield v0/z.a La1/f;
      // 1ee: aload 13
      // 1f0: getfield v0/z.c La1/e;
      // 1f3: lload 7
      // 1f5: invokeinterface a1/s.i (La1/e;J)V 4
      // 1fa: iload 6
      // 1fc: ifle 20b
      // 1ff: aload 13
      // 201: iload 4
      // 203: lload 9
      // 205: lload 7
      // 207: lsub
      // 208: invokevirtual v0/z.E (IJ)V
      // 20b: aload 13
      // 20d: monitorexit
      // 20e: aload 1
      // 20f: monitorexit
      // 210: iload 3
      // 211: ifeq 248
      // 214: aload 14
      // 216: getfield v0/s.u Lv0/z;
      // 219: astore 1
      // 21a: aload 1
      // 21b: monitorenter
      // 21c: aload 1
      // 21d: getfield v0/z.e Z
      // 220: ifne 231
      // 223: aload 1
      // 224: getfield v0/z.a La1/f;
      // 227: invokeinterface a1/f.flush ()V 1
      // 22c: aload 1
      // 22d: monitorexit
      // 22e: goto 248
      // 231: new java/io/IOException
      // 234: astore 12
      // 236: aload 12
      // 238: ldc_w "closed"
      // 23b: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
      // 23e: aload 12
      // 240: athrow
      // 241: astore 12
      // 243: aload 1
      // 244: monitorexit
      // 245: aload 12
      // 247: athrow
      // 248: aload 0
      // 249: aload 15
      // 24b: putfield v0/t.d Lv0/y;
      // 24e: aload 0
      // 24f: getfield v0/t.f Z
      // 252: ifne 290
      // 255: aload 0
      // 256: getfield v0/t.d Lv0/y;
      // 259: getfield v0/y.i Ls0/j;
      // 25c: astore 12
      // 25e: aload 0
      // 25f: getfield v0/t.a Lp0/v;
      // 262: checkcast t0/f
      // 265: getfield t0/f.h I
      // 268: i2l
      // 269: lstore 7
      // 26b: getstatic java/util/concurrent/TimeUnit.MILLISECONDS Ljava/util/concurrent/TimeUnit;
      // 26e: astore 1
      // 26f: aload 12
      // 271: lload 7
      // 273: aload 1
      // 274: invokevirtual a1/v.g (JLjava/util/concurrent/TimeUnit;)La1/v;
      // 277: pop
      // 278: aload 0
      // 279: getfield v0/t.d Lv0/y;
      // 27c: getfield v0/y.j Ls0/j;
      // 27f: aload 0
      // 280: getfield v0/t.a Lp0/v;
      // 283: checkcast t0/f
      // 286: getfield t0/f.i I
      // 289: i2l
      // 28a: aload 1
      // 28b: invokevirtual a1/v.g (JLjava/util/concurrent/TimeUnit;)La1/v;
      // 28e: pop
      // 28f: return
      // 290: aload 0
      // 291: getfield v0/t.d Lv0/y;
      // 294: getstatic v0/b.g Lv0/b;
      // 297: invokevirtual v0/y.e (Lv0/b;)V
      // 29a: new java/io/IOException
      // 29d: dup
      // 29e: ldc_w "Canceled"
      // 2a1: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
      // 2a4: athrow
      // 2a5: new java/io/IOException
      // 2a8: astore 12
      // 2aa: aload 12
      // 2ac: ldc_w "closed"
      // 2af: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
      // 2b2: aload 12
      // 2b4: athrow
      // 2b5: astore 12
      // 2b7: aload 13
      // 2b9: monitorexit
      // 2ba: aload 12
      // 2bc: athrow
      // 2bd: new v0/a
      // 2c0: astore 12
      // 2c2: aload 12
      // 2c4: invokespecial v0/a.<init> ()V
      // 2c7: aload 12
      // 2c9: athrow
      // 2ca: astore 12
      // 2cc: aload 14
      // 2ce: monitorexit
      // 2cf: aload 12
      // 2d1: athrow
      // 2d2: astore 12
      // 2d4: aload 1
      // 2d5: monitorexit
      // 2d6: aload 12
      // 2d8: athrow
   }

   @Override
   public final long f(j0 var1) {
      return t0.e.a(var1);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public final i0 g(boolean var1) {
      y var5 = this.d;
      synchronized (var5){} // $VF: monitorenter 

      Throwable var10000;
      label483: {
         try {
            var5.i.i();
         } catch (Throwable var65) {
            var10000 = var65;
            boolean var10001 = false;
            break label483;
         }

         while (true) {
            boolean var53 = false /* VF: Semaphore variable */;

            try {
               var53 = true;
               if (var5.e.isEmpty()) {
                  if (var5.k == null) {
                     var5.i();
                     var53 = false;
                     continue;
                  }

                  var53 = false;
                  break;
               }

               var53 = false;
               break;
            } finally {
               if (var53) {
                  try {
                     var5.i.o();
                  } catch (Throwable var61) {
                     var10000 = var61;
                     boolean var79 = false;
                     break label483;
                  }
               }
            }
         }

         p0.s var8;
         label480: {
            try {
               var5.i.o();
               if (!var5.e.isEmpty()) {
                  var8 = (p0.s)var5.e.removeFirst();
                  break label480;
               }
            } catch (Throwable var66) {
               var10000 = var66;
               boolean var80 = false;
               break label483;
            }

            Object var4;
            try {
               var4 = var5.l;
            } catch (Throwable var64) {
               var10000 = var64;
               boolean var81 = false;
               break label483;
            }

            if (var4 == null) {
               try {
                  var4 = new d0(var5.k);
               } catch (Throwable var63) {
                  var10000 = var63;
                  boolean var82 = false;
                  break label483;
               }
            }

            try {
               throw var4;
            } catch (Throwable var62) {
               var10000 = var62;
               boolean var83 = false;
               break label483;
            }
         }

         // $VF: monitorexit
         p0.c0 var7 = this.e;
         ArrayList var6 = new ArrayList(20);
         int var3 = var8.a.length / 2;
         int var2 = 0;
         l0.q var69 = null;

         while (var2 < var3) {
            String var10 = var8.d(var2);
            String var9 = var8.f(var2);
            l0.q var72;
            if (var10.equals(":status")) {
               StringBuilder var70 = new StringBuilder("HTTP/1.1 ");
               var70.append(var9);
               var72 = l0.q.a(var70.toString());
            } else {
               var72 = var69;
               if (!h.contains(var10)) {
                  p0.q.c.getClass();
                  var6.add(var10);
                  var6.add(var9.trim());
                  var72 = var69;
               }
            }

            var2++;
            var69 = var72;
         }

         if (var69 != null) {
            i0 var73 = new i0();
            var73.b = var7;
            var73.c = var69.e;
            var73.d = var69.f;
            String[] var74 = var6.toArray(new String[var6.size()]);
            p0.f var71 = new p0.f();
            Collections.addAll(var71.a, var74);
            var73.f = var71;
            if (var1) {
               p0.q.c.getClass();
               if (var73.c == 100) {
                  return null;
               }
            }

            return var73;
         }

         throw new ProtocolException("Expected ':status' header not present");
      }

      Throwable var68 = var10000;
      // $VF: monitorexit
      throw var68;
   }

   @Override
   public final s0.g h() {
      return this.b;
   }
}
