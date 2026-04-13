package s0;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;
import p0.e0;
import p0.m0;
import p0.q;
import p0.t;

public final class f {
   public final l a;
   public final p0.a b;
   public final h c;
   public final q d;
   public z.d e;
   public final t f;
   public g g;
   public boolean h;
   public m0 i;

   public f(l var1, h var2, p0.a var3, e0 var4, q var5) {
      this.a = var1;
      this.c = var2;
      this.b = var3;
      this.d = var5;
      this.f = new t(var3, var2.e, var4, var5);
   }

   public final g a(int param1, int param2, int param3, int param4, boolean param5) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 000: aload 0
      // 001: getfield s0/f.c Ls0/h;
      // 004: astore 17
      // 006: aload 17
      // 008: monitorenter
      // 009: aload 0
      // 00a: getfield s0/f.a Ls0/l;
      // 00d: astore 13
      // 00f: aload 13
      // 011: getfield s0/l.b Ls0/h;
      // 014: astore 12
      // 016: aload 12
      // 018: monitorenter
      // 019: aload 13
      // 01b: getfield s0/l.m Z
      // 01e: istore 11
      // 020: aload 12
      // 022: monitorexit
      // 023: iload 11
      // 025: ifne 6fb
      // 028: bipush 0
      // 029: istore 9
      // 02b: aload 0
      // 02c: bipush 0
      // 02d: putfield s0/f.h Z
      // 030: aload 0
      // 031: getfield s0/f.a Ls0/l;
      // 034: astore 12
      // 036: aload 12
      // 038: getfield s0/l.i Ls0/g;
      // 03b: astore 15
      // 03d: aconst_null
      // 03e: astore 16
      // 040: aload 15
      // 042: ifnull 057
      // 045: aload 15
      // 047: getfield s0/g.k Z
      // 04a: ifeq 057
      // 04d: aload 12
      // 04f: invokevirtual s0/l.f ()Ljava/net/Socket;
      // 052: astore 14
      // 054: goto 05a
      // 057: aconst_null
      // 058: astore 14
      // 05a: aload 0
      // 05b: getfield s0/f.a Ls0/l;
      // 05e: astore 12
      // 060: aload 12
      // 062: getfield s0/l.i Ls0/g;
      // 065: astore 13
      // 067: aload 13
      // 069: ifnull 072
      // 06c: aconst_null
      // 06d: astore 15
      // 06f: goto 075
      // 072: aconst_null
      // 073: astore 13
      // 075: aload 13
      // 077: ifnonnull 0ca
      // 07a: aload 0
      // 07b: getfield s0/f.c Ls0/h;
      // 07e: aload 0
      // 07f: getfield s0/f.b Lp0/a;
      // 082: aload 12
      // 084: aconst_null
      // 085: bipush 0
      // 086: invokevirtual s0/h.c (Lp0/a;Ls0/l;Ljava/util/ArrayList;Z)Z
      // 089: ifeq 09e
      // 08c: aload 0
      // 08d: getfield s0/f.a Ls0/l;
      // 090: getfield s0/l.i Ls0/g;
      // 093: astore 13
      // 095: aconst_null
      // 096: astore 12
      // 098: bipush 1
      // 099: istore 6
      // 09b: goto 0d0
      // 09e: aload 0
      // 09f: getfield s0/f.i Lp0/m0;
      // 0a2: astore 12
      // 0a4: aload 12
      // 0a6: ifnull 0b1
      // 0a9: aload 0
      // 0aa: aconst_null
      // 0ab: putfield s0/f.i Lp0/m0;
      // 0ae: goto 0c4
      // 0b1: aload 0
      // 0b2: invokevirtual s0/f.d ()Z
      // 0b5: ifeq 0ca
      // 0b8: aload 0
      // 0b9: getfield s0/f.a Ls0/l;
      // 0bc: getfield s0/l.i Ls0/g;
      // 0bf: getfield s0/g.c Lp0/m0;
      // 0c2: astore 12
      // 0c4: bipush 0
      // 0c5: istore 6
      // 0c7: goto 0d0
      // 0ca: bipush 0
      // 0cb: istore 6
      // 0cd: aconst_null
      // 0ce: astore 12
      // 0d0: aload 17
      // 0d2: monitorexit
      // 0d3: aload 14
      // 0d5: invokestatic q0/c.d (Ljava/net/Socket;)V
      // 0d8: aload 15
      // 0da: ifnull 0e5
      // 0dd: aload 0
      // 0de: getfield s0/f.d Lp0/q;
      // 0e1: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 0e4: pop
      // 0e5: iload 6
      // 0e7: ifeq 0f2
      // 0ea: aload 0
      // 0eb: getfield s0/f.d Lp0/q;
      // 0ee: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 0f1: pop
      // 0f2: aload 13
      // 0f4: ifnull 0fa
      // 0f7: aload 13
      // 0f9: areturn
      // 0fa: aload 12
      // 0fc: ifnonnull 4dd
      // 0ff: aload 0
      // 100: getfield s0/f.e Lz/d;
      // 103: astore 14
      // 105: aload 14
      // 107: ifnull 12d
      // 10a: aload 14
      // 10c: getfield z/d.b I
      // 10f: aload 14
      // 111: getfield z/d.c Ljava/lang/Object;
      // 114: checkcast java/util/List
      // 117: invokeinterface java/util/List.size ()I 1
      // 11c: if_icmpge 125
      // 11f: bipush 1
      // 120: istore 7
      // 122: goto 128
      // 125: bipush 0
      // 126: istore 7
      // 128: iload 7
      // 12a: ifne 4dd
      // 12d: aload 0
      // 12e: getfield s0/f.f Lp0/t;
      // 131: astore 15
      // 133: aload 15
      // 135: getfield p0/t.c I
      // 138: aload 15
      // 13a: getfield p0/t.b Ljava/util/List;
      // 13d: invokeinterface java/util/List.size ()I 1
      // 142: if_icmpge 14b
      // 145: bipush 1
      // 146: istore 7
      // 148: goto 14e
      // 14b: bipush 0
      // 14c: istore 7
      // 14e: iload 7
      // 150: ifne 16c
      // 153: aload 15
      // 155: getfield p0/t.i Ljava/io/Serializable;
      // 158: checkcast java/util/List
      // 15b: invokeinterface java/util/List.isEmpty ()Z 1
      // 160: ifne 166
      // 163: goto 16c
      // 166: bipush 0
      // 167: istore 7
      // 169: goto 16f
      // 16c: bipush 1
      // 16d: istore 7
      // 16f: iload 7
      // 171: ifeq 4d5
      // 174: new java/util/ArrayList
      // 177: dup
      // 178: invokespecial java/util/ArrayList.<init> ()V
      // 17b: astore 17
      // 17d: aload 15
      // 17f: getfield p0/t.c I
      // 182: aload 15
      // 184: getfield p0/t.b Ljava/util/List;
      // 187: invokeinterface java/util/List.size ()I 1
      // 18c: if_icmpge 195
      // 18f: bipush 1
      // 190: istore 7
      // 192: goto 198
      // 195: bipush 0
      // 196: istore 7
      // 198: iload 7
      // 19a: ifeq 49f
      // 19d: aload 15
      // 19f: getfield p0/t.c I
      // 1a2: aload 15
      // 1a4: getfield p0/t.b Ljava/util/List;
      // 1a7: invokeinterface java/util/List.size ()I 1
      // 1ac: if_icmpge 1b5
      // 1af: bipush 1
      // 1b0: istore 7
      // 1b2: goto 1b8
      // 1b5: bipush 0
      // 1b6: istore 7
      // 1b8: iload 7
      // 1ba: ifeq 45e
      // 1bd: aload 15
      // 1bf: getfield p0/t.b Ljava/util/List;
      // 1c2: astore 14
      // 1c4: aload 15
      // 1c6: getfield p0/t.c I
      // 1c9: istore 7
      // 1cb: aload 15
      // 1cd: iload 7
      // 1cf: bipush 1
      // 1d0: iadd
      // 1d1: putfield p0/t.c I
      // 1d4: aload 14
      // 1d6: iload 7
      // 1d8: invokeinterface java/util/List.get (I)Ljava/lang/Object; 2
      // 1dd: checkcast java/net/Proxy
      // 1e0: astore 18
      // 1e2: aload 15
      // 1e4: new java/util/ArrayList
      // 1e7: dup
      // 1e8: invokespecial java/util/ArrayList.<init> ()V
      // 1eb: putfield p0/t.d Ljava/util/List;
      // 1ee: aload 18
      // 1f0: invokevirtual java/net/Proxy.type ()Ljava/net/Proxy$Type;
      // 1f3: getstatic java/net/Proxy$Type.DIRECT Ljava/net/Proxy$Type;
      // 1f6: if_acmpeq 267
      // 1f9: aload 18
      // 1fb: invokevirtual java/net/Proxy.type ()Ljava/net/Proxy$Type;
      // 1fe: getstatic java/net/Proxy$Type.SOCKS Ljava/net/Proxy$Type;
      // 201: if_acmpne 207
      // 204: goto 267
      // 207: aload 18
      // 209: invokevirtual java/net/Proxy.address ()Ljava/net/SocketAddress;
      // 20c: astore 14
      // 20e: aload 14
      // 210: instanceof java/net/InetSocketAddress
      // 213: ifeq 244
      // 216: aload 14
      // 218: checkcast java/net/InetSocketAddress
      // 21b: astore 19
      // 21d: aload 19
      // 21f: invokevirtual java/net/InetSocketAddress.getAddress ()Ljava/net/InetAddress;
      // 222: astore 14
      // 224: aload 14
      // 226: ifnonnull 233
      // 229: aload 19
      // 22b: invokevirtual java/net/InetSocketAddress.getHostName ()Ljava/lang/String;
      // 22e: astore 14
      // 230: goto 23a
      // 233: aload 14
      // 235: invokevirtual java/net/InetAddress.getHostAddress ()Ljava/lang/String;
      // 238: astore 14
      // 23a: aload 19
      // 23c: invokevirtual java/net/InetSocketAddress.getPort ()I
      // 23f: istore 7
      // 241: goto 282
      // 244: new java/lang/StringBuilder
      // 247: dup
      // 248: ldc "Proxy.address() is not an InetSocketAddress: "
      // 24a: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 24d: astore 12
      // 24f: aload 12
      // 251: aload 14
      // 253: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 256: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      // 259: pop
      // 25a: new java/lang/IllegalArgumentException
      // 25d: dup
      // 25e: aload 12
      // 260: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 263: invokespecial java/lang/IllegalArgumentException.<init> (Ljava/lang/String;)V
      // 266: athrow
      // 267: aload 15
      // 269: getfield p0/t.e Ljava/lang/Object;
      // 26c: checkcast p0/a
      // 26f: getfield p0/a.a Lp0/u;
      // 272: astore 19
      // 274: aload 19
      // 276: getfield p0/u.d Ljava/lang/String;
      // 279: astore 14
      // 27b: aload 19
      // 27d: getfield p0/u.e I
      // 280: istore 7
      // 282: iload 7
      // 284: bipush 1
      // 285: if_icmplt 423
      // 288: iload 7
      // 28a: ldc 65535
      // 28c: if_icmpgt 423
      // 28f: aload 18
      // 291: invokevirtual java/net/Proxy.type ()Ljava/net/Proxy$Type;
      // 294: getstatic java/net/Proxy$Type.SOCKS Ljava/net/Proxy$Type;
      // 297: if_acmpne 2af
      // 29a: aload 15
      // 29c: getfield p0/t.d Ljava/util/List;
      // 29f: aload 14
      // 2a1: iload 7
      // 2a3: invokestatic java/net/InetSocketAddress.createUnresolved (Ljava/lang/String;I)Ljava/net/InetSocketAddress;
      // 2a6: invokeinterface java/util/List.add (Ljava/lang/Object;)Z 2
      // 2ab: pop
      // 2ac: goto 32f
      // 2af: aload 15
      // 2b1: getfield p0/t.h Ljava/lang/Object;
      // 2b4: checkcast p0/q
      // 2b7: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 2ba: pop
      // 2bb: aload 15
      // 2bd: getfield p0/t.e Ljava/lang/Object;
      // 2c0: checkcast p0/a
      // 2c3: getfield p0/a.b Lp0/p;
      // 2c6: checkcast m0/b
      // 2c9: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 2cc: pop
      // 2cd: aload 14
      // 2cf: ifnull 418
      // 2d2: aload 14
      // 2d4: invokestatic java/net/InetAddress.getAllByName (Ljava/lang/String;)[Ljava/net/InetAddress;
      // 2d7: invokestatic java/util/Arrays.asList ([Ljava/lang/Object;)Ljava/util/List;
      // 2da: astore 19
      // 2dc: aload 19
      // 2de: invokeinterface java/util/List.isEmpty ()Z 1
      // 2e3: ifne 3c4
      // 2e6: aload 15
      // 2e8: getfield p0/t.h Ljava/lang/Object;
      // 2eb: checkcast p0/q
      // 2ee: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 2f1: pop
      // 2f2: aload 19
      // 2f4: invokeinterface java/util/List.size ()I 1
      // 2f9: istore 10
      // 2fb: bipush 0
      // 2fc: istore 8
      // 2fe: iload 8
      // 300: iload 10
      // 302: if_icmpge 32f
      // 305: aload 19
      // 307: iload 8
      // 309: invokeinterface java/util/List.get (I)Ljava/lang/Object; 2
      // 30e: checkcast java/net/InetAddress
      // 311: astore 14
      // 313: aload 15
      // 315: getfield p0/t.d Ljava/util/List;
      // 318: new java/net/InetSocketAddress
      // 31b: dup
      // 31c: aload 14
      // 31e: iload 7
      // 320: invokespecial java/net/InetSocketAddress.<init> (Ljava/net/InetAddress;I)V
      // 323: invokeinterface java/util/List.add (Ljava/lang/Object;)Z 2
      // 328: pop
      // 329: iinc 8 1
      // 32c: goto 2fe
      // 32f: aload 15
      // 331: getfield p0/t.d Ljava/util/List;
      // 334: invokeinterface java/util/List.size ()I 1
      // 339: istore 8
      // 33b: bipush 0
      // 33c: istore 7
      // 33e: iload 7
      // 340: iload 8
      // 342: if_icmpge 3b6
      // 345: new p0/m0
      // 348: dup
      // 349: aload 15
      // 34b: getfield p0/t.e Ljava/lang/Object;
      // 34e: checkcast p0/a
      // 351: aload 18
      // 353: aload 15
      // 355: getfield p0/t.d Ljava/util/List;
      // 358: iload 7
      // 35a: invokeinterface java/util/List.get (I)Ljava/lang/Object; 2
      // 35f: checkcast java/net/InetSocketAddress
      // 362: invokespecial p0/m0.<init> (Lp0/a;Ljava/net/Proxy;Ljava/net/InetSocketAddress;)V
      // 365: astore 19
      // 367: aload 15
      // 369: getfield p0/t.f Ljava/lang/Object;
      // 36c: checkcast com/guard/wallet/http/h
      // 36f: astore 14
      // 371: aload 14
      // 373: monitorenter
      // 374: aload 14
      // 376: getfield com/guard/wallet/http/h.e Ljava/lang/Object;
      // 379: checkcast java/util/Set
      // 37c: aload 19
      // 37e: invokeinterface java/util/Set.contains (Ljava/lang/Object;)Z 2
      // 383: istore 11
      // 385: aload 14
      // 387: monitorexit
      // 388: iload 11
      // 38a: ifeq 3a0
      // 38d: aload 15
      // 38f: getfield p0/t.i Ljava/io/Serializable;
      // 392: checkcast java/util/List
      // 395: aload 19
      // 397: invokeinterface java/util/List.add (Ljava/lang/Object;)Z 2
      // 39c: pop
      // 39d: goto 3a8
      // 3a0: aload 17
      // 3a2: aload 19
      // 3a4: invokevirtual java/util/ArrayList.add (Ljava/lang/Object;)Z
      // 3a7: pop
      // 3a8: iinc 7 1
      // 3ab: goto 33e
      // 3ae: astore 12
      // 3b0: aload 14
      // 3b2: monitorexit
      // 3b3: aload 12
      // 3b5: athrow
      // 3b6: aload 17
      // 3b8: invokevirtual java/util/ArrayList.isEmpty ()Z
      // 3bb: ifne 3c1
      // 3be: goto 49f
      // 3c1: goto 17d
      // 3c4: new java/lang/StringBuilder
      // 3c7: dup
      // 3c8: invokespecial java/lang/StringBuilder.<init> ()V
      // 3cb: astore 12
      // 3cd: aload 12
      // 3cf: aload 15
      // 3d1: getfield p0/t.e Ljava/lang/Object;
      // 3d4: checkcast p0/a
      // 3d7: getfield p0/a.b Lp0/p;
      // 3da: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      // 3dd: pop
      // 3de: aload 12
      // 3e0: ldc " returned no addresses for "
      // 3e2: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 3e5: pop
      // 3e6: aload 12
      // 3e8: aload 14
      // 3ea: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 3ed: pop
      // 3ee: new java/net/UnknownHostException
      // 3f1: dup
      // 3f2: aload 12
      // 3f4: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 3f7: invokespecial java/net/UnknownHostException.<init> (Ljava/lang/String;)V
      // 3fa: athrow
      // 3fb: astore 12
      // 3fd: new java/net/UnknownHostException
      // 400: dup
      // 401: ldc "Broken system behaviour for dns lookup of "
      // 403: aload 14
      // 405: invokevirtual java/lang/String.concat (Ljava/lang/String;)Ljava/lang/String;
      // 408: invokespecial java/net/UnknownHostException.<init> (Ljava/lang/String;)V
      // 40b: astore 13
      // 40d: aload 13
      // 40f: aload 12
      // 411: invokevirtual java/lang/Throwable.initCause (Ljava/lang/Throwable;)Ljava/lang/Throwable;
      // 414: pop
      // 415: aload 13
      // 417: athrow
      // 418: new java/net/UnknownHostException
      // 41b: dup
      // 41c: ldc_w "hostname == null"
      // 41f: invokespecial java/net/UnknownHostException.<init> (Ljava/lang/String;)V
      // 422: athrow
      // 423: new java/lang/StringBuilder
      // 426: dup
      // 427: ldc_w "No route to "
      // 42a: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 42d: astore 12
      // 42f: aload 12
      // 431: aload 14
      // 433: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 436: pop
      // 437: aload 12
      // 439: ldc_w ":"
      // 43c: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 43f: pop
      // 440: aload 12
      // 442: iload 7
      // 444: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 447: pop
      // 448: aload 12
      // 44a: ldc_w "; port is out of range"
      // 44d: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 450: pop
      // 451: new java/net/SocketException
      // 454: dup
      // 455: aload 12
      // 457: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 45a: invokespecial java/net/SocketException.<init> (Ljava/lang/String;)V
      // 45d: athrow
      // 45e: new java/lang/StringBuilder
      // 461: dup
      // 462: ldc_w "No route to "
      // 465: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 468: astore 12
      // 46a: aload 12
      // 46c: aload 15
      // 46e: getfield p0/t.e Ljava/lang/Object;
      // 471: checkcast p0/a
      // 474: getfield p0/a.a Lp0/u;
      // 477: getfield p0/u.d Ljava/lang/String;
      // 47a: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 47d: pop
      // 47e: aload 12
      // 480: ldc_w "; exhausted proxy configurations: "
      // 483: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 486: pop
      // 487: aload 12
      // 489: aload 15
      // 48b: getfield p0/t.b Ljava/util/List;
      // 48e: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      // 491: pop
      // 492: new java/net/SocketException
      // 495: dup
      // 496: aload 12
      // 498: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 49b: invokespecial java/net/SocketException.<init> (Ljava/lang/String;)V
      // 49e: athrow
      // 49f: aload 17
      // 4a1: invokevirtual java/util/ArrayList.isEmpty ()Z
      // 4a4: ifeq 4c2
      // 4a7: aload 17
      // 4a9: aload 15
      // 4ab: getfield p0/t.i Ljava/io/Serializable;
      // 4ae: checkcast java/util/List
      // 4b1: invokevirtual java/util/ArrayList.addAll (Ljava/util/Collection;)Z
      // 4b4: pop
      // 4b5: aload 15
      // 4b7: getfield p0/t.i Ljava/io/Serializable;
      // 4ba: checkcast java/util/List
      // 4bd: invokeinterface java/util/List.clear ()V 1
      // 4c2: aload 0
      // 4c3: new z/d
      // 4c6: dup
      // 4c7: aload 17
      // 4c9: invokespecial z/d.<init> (Ljava/util/ArrayList;)V
      // 4cc: putfield s0/f.e Lz/d;
      // 4cf: bipush 1
      // 4d0: istore 7
      // 4d2: goto 4e0
      // 4d5: new java/util/NoSuchElementException
      // 4d8: dup
      // 4d9: invokespecial java/util/NoSuchElementException.<init> ()V
      // 4dc: athrow
      // 4dd: bipush 0
      // 4de: istore 7
      // 4e0: aload 0
      // 4e1: getfield s0/f.c Ls0/h;
      // 4e4: astore 17
      // 4e6: aload 17
      // 4e8: monitorenter
      // 4e9: aload 0
      // 4ea: getfield s0/f.a Ls0/l;
      // 4ed: astore 15
      // 4ef: aload 15
      // 4f1: getfield s0/l.b Ls0/h;
      // 4f4: astore 14
      // 4f6: aload 14
      // 4f8: monitorenter
      // 4f9: aload 15
      // 4fb: getfield s0/l.m Z
      // 4fe: istore 11
      // 500: aload 14
      // 502: monitorexit
      // 503: iload 11
      // 505: ifne 6db
      // 508: iload 7
      // 50a: ifeq 557
      // 50d: aload 0
      // 50e: getfield s0/f.e Lz/d;
      // 511: astore 15
      // 513: aload 15
      // 515: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 518: pop
      // 519: new java/util/ArrayList
      // 51c: astore 14
      // 51e: aload 14
      // 520: aload 15
      // 522: getfield z/d.c Ljava/lang/Object;
      // 525: checkcast java/util/List
      // 528: invokespecial java/util/ArrayList.<init> (Ljava/util/Collection;)V
      // 52b: aload 14
      // 52d: astore 15
      // 52f: aload 0
      // 530: getfield s0/f.c Ls0/h;
      // 533: aload 0
      // 534: getfield s0/f.b Lp0/a;
      // 537: aload 0
      // 538: getfield s0/f.a Ls0/l;
      // 53b: aload 14
      // 53d: bipush 0
      // 53e: invokevirtual s0/h.c (Lp0/a;Ls0/l;Ljava/util/ArrayList;Z)Z
      // 541: ifeq 55a
      // 544: aload 0
      // 545: getfield s0/f.a Ls0/l;
      // 548: getfield s0/l.i Ls0/g;
      // 54b: astore 13
      // 54d: bipush 1
      // 54e: istore 6
      // 550: aload 14
      // 552: astore 15
      // 554: goto 55a
      // 557: aconst_null
      // 558: astore 15
      // 55a: aload 12
      // 55c: astore 14
      // 55e: iload 6
      // 560: ifne 5e1
      // 563: aload 12
      // 565: astore 14
      // 567: aload 12
      // 569: ifnonnull 5cb
      // 56c: aload 0
      // 56d: getfield s0/f.e Lz/d;
      // 570: astore 12
      // 572: iload 9
      // 574: istore 7
      // 576: aload 12
      // 578: getfield z/d.b I
      // 57b: aload 12
      // 57d: getfield z/d.c Ljava/lang/Object;
      // 580: checkcast java/util/List
      // 583: invokeinterface java/util/List.size ()I 1
      // 588: if_icmpge 58e
      // 58b: bipush 1
      // 58c: istore 7
      // 58e: iload 7
      // 590: ifeq 5be
      // 593: aload 12
      // 595: getfield z/d.c Ljava/lang/Object;
      // 598: checkcast java/util/List
      // 59b: astore 13
      // 59d: aload 12
      // 59f: getfield z/d.b I
      // 5a2: istore 7
      // 5a4: aload 12
      // 5a6: iload 7
      // 5a8: bipush 1
      // 5a9: iadd
      // 5aa: putfield z/d.b I
      // 5ad: aload 13
      // 5af: iload 7
      // 5b1: invokeinterface java/util/List.get (I)Ljava/lang/Object; 2
      // 5b6: checkcast p0/m0
      // 5b9: astore 14
      // 5bb: goto 5cb
      // 5be: new java/util/NoSuchElementException
      // 5c1: astore 12
      // 5c3: aload 12
      // 5c5: invokespecial java/util/NoSuchElementException.<init> ()V
      // 5c8: aload 12
      // 5ca: athrow
      // 5cb: new s0/g
      // 5ce: astore 13
      // 5d0: aload 13
      // 5d2: aload 0
      // 5d3: getfield s0/f.c Ls0/h;
      // 5d6: aload 14
      // 5d8: invokespecial s0/g.<init> (Ls0/h;Lp0/m0;)V
      // 5db: aload 0
      // 5dc: aload 13
      // 5de: putfield s0/f.g Ls0/g;
      // 5e1: aload 17
      // 5e3: monitorexit
      // 5e4: iload 6
      // 5e6: ifeq 5ec
      // 5e9: goto 6b8
      // 5ec: aload 13
      // 5ee: iload 1
      // 5ef: iload 2
      // 5f0: iload 3
      // 5f1: iload 4
      // 5f3: iload 5
      // 5f5: aload 0
      // 5f6: getfield s0/f.d Lp0/q;
      // 5f9: invokevirtual s0/g.c (IIIIZLp0/q;)V
      // 5fc: aload 0
      // 5fd: getfield s0/f.c Ls0/h;
      // 600: getfield s0/h.e Lcom/guard/wallet/http/h;
      // 603: aload 13
      // 605: getfield s0/g.c Lp0/m0;
      // 608: invokevirtual com/guard/wallet/http/h.h (Lp0/m0;)V
      // 60b: aload 0
      // 60c: getfield s0/f.c Ls0/h;
      // 60f: astore 17
      // 611: aload 17
      // 613: monitorenter
      // 614: aload 0
      // 615: aconst_null
      // 616: putfield s0/f.g Ls0/g;
      // 619: aload 0
      // 61a: getfield s0/f.c Ls0/h;
      // 61d: aload 0
      // 61e: getfield s0/f.b Lp0/a;
      // 621: aload 0
      // 622: getfield s0/f.a Ls0/l;
      // 625: aload 15
      // 627: bipush 1
      // 628: invokevirtual s0/h.c (Lp0/a;Ls0/l;Ljava/util/ArrayList;Z)Z
      // 62b: ifeq 64d
      // 62e: aload 13
      // 630: bipush 1
      // 631: putfield s0/g.k Z
      // 634: aload 13
      // 636: getfield s0/g.e Ljava/net/Socket;
      // 639: astore 12
      // 63b: aload 0
      // 63c: getfield s0/f.a Ls0/l;
      // 63f: getfield s0/l.i Ls0/g;
      // 642: astore 13
      // 644: aload 0
      // 645: aload 14
      // 647: putfield s0/f.i Lp0/m0;
      // 64a: goto 6b0
      // 64d: aload 0
      // 64e: getfield s0/f.c Ls0/h;
      // 651: astore 12
      // 653: aload 12
      // 655: getfield s0/h.f Z
      // 658: ifne 66c
      // 65b: aload 12
      // 65d: bipush 1
      // 65e: putfield s0/h.f Z
      // 661: getstatic s0/h.g Ljava/util/concurrent/ThreadPoolExecutor;
      // 664: aload 12
      // 666: getfield s0/h.c Lo/a;
      // 669: invokevirtual java/util/concurrent/ThreadPoolExecutor.execute (Ljava/lang/Runnable;)V
      // 66c: aload 12
      // 66e: getfield s0/h.d Ljava/util/ArrayDeque;
      // 671: aload 13
      // 673: invokevirtual java/util/ArrayDeque.add (Ljava/lang/Object;)Z
      // 676: pop
      // 677: aload 0
      // 678: getfield s0/f.a Ls0/l;
      // 67b: astore 14
      // 67d: aload 14
      // 67f: getfield s0/l.i Ls0/g;
      // 682: ifnonnull 6c8
      // 685: aload 14
      // 687: aload 13
      // 689: putfield s0/l.i Ls0/g;
      // 68c: aload 13
      // 68e: getfield s0/g.p Ljava/util/ArrayList;
      // 691: astore 15
      // 693: new s0/k
      // 696: astore 12
      // 698: aload 12
      // 69a: aload 14
      // 69c: aload 14
      // 69e: getfield s0/l.f Ljava/lang/Object;
      // 6a1: invokespecial s0/k.<init> (Ls0/l;Ljava/lang/Object;)V
      // 6a4: aload 15
      // 6a6: aload 12
      // 6a8: invokevirtual java/util/ArrayList.add (Ljava/lang/Object;)Z
      // 6ab: pop
      // 6ac: aload 16
      // 6ae: astore 12
      // 6b0: aload 17
      // 6b2: monitorexit
      // 6b3: aload 12
      // 6b5: invokestatic q0/c.d (Ljava/net/Socket;)V
      // 6b8: aload 0
      // 6b9: getfield s0/f.d Lp0/q;
      // 6bc: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 6bf: pop
      // 6c0: aload 13
      // 6c2: areturn
      // 6c3: astore 12
      // 6c5: goto 6d5
      // 6c8: new java/lang/IllegalStateException
      // 6cb: astore 12
      // 6cd: aload 12
      // 6cf: invokespecial java/lang/IllegalStateException.<init> ()V
      // 6d2: aload 12
      // 6d4: athrow
      // 6d5: aload 17
      // 6d7: monitorexit
      // 6d8: aload 12
      // 6da: athrow
      // 6db: new java/io/IOException
      // 6de: astore 12
      // 6e0: aload 12
      // 6e2: ldc_w "Canceled"
      // 6e5: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
      // 6e8: aload 12
      // 6ea: athrow
      // 6eb: astore 12
      // 6ed: aload 14
      // 6ef: monitorexit
      // 6f0: aload 12
      // 6f2: athrow
      // 6f3: astore 12
      // 6f5: aload 17
      // 6f7: monitorexit
      // 6f8: aload 12
      // 6fa: athrow
      // 6fb: new java/io/IOException
      // 6fe: astore 12
      // 700: aload 12
      // 702: ldc_w "Canceled"
      // 705: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
      // 708: aload 12
      // 70a: athrow
      // 70b: astore 13
      // 70d: aload 12
      // 70f: monitorexit
      // 710: aload 13
      // 712: athrow
      // 713: astore 12
      // 715: aload 17
      // 717: monitorexit
      // 718: aload 12
      // 71a: athrow
   }

   // $VF: Handled exception range with multiple entry points by splitting it
   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final g b(int var1, int var2, int var3, int var4, boolean var5, boolean var6) {
      while (true) {
         g var14 = this.a(var1, var2, var3, var4, var5);
         h var15 = this.c;
         synchronized (var15){} // $VF: monitorenter 

         Throwable var10000;
         label990: {
            int var7;
            try {
               var7 = var14.m;
            } catch (Throwable var186) {
               var10000 = var186;
               boolean var10001 = false;
               break label990;
            }

            boolean var8 = true;
            if (var7 == 0) {
               label979: {
                  label978: {
                     try {
                        if (var14.h != null) {
                           break label978;
                        }
                     } catch (Throwable var185) {
                        var10000 = var185;
                        boolean var196 = false;
                        break label990;
                     }

                     var187 = false;
                     break label979;
                  }

                  var187 = true;
               }

               if (!var187) {
                  try {
                     // $VF: monitorexit
                     return var14;
                  } catch (Throwable var183) {
                     var10000 = var183;
                     boolean var209 = false;
                     break label990;
                  }
               }
            }

            try {
               // $VF: monitorexit
            } catch (Throwable var184) {
               var10000 = var184;
               boolean var197 = false;
               break label990;
            }

            label964: {
               label963: {
                  label999: {
                     if (!var14.e.isClosed() && !var14.e.isInputShutdown() && !var14.e.isOutputShutdown()) {
                        var192 = var14.h;
                        label959:
                        if (var192 != null) {
                           long var10 = System.nanoTime();
                           synchronized (var192){} // $VF: monitorenter 

                           label936: {
                              label993: {
                                 try {
                                    if (var192.g) {
                                       break label993;
                                    }
                                 } catch (Throwable var173) {
                                    var10000 = var173;
                                    boolean var206 = false;
                                    break label936;
                                 }

                                 long var12;
                                 try {
                                    if (var192.n >= var192.m) {
                                       break label963;
                                    }

                                    var12 = var192.o;
                                 } catch (Throwable var172) {
                                    var10000 = var172;
                                    boolean var207 = false;
                                    break label936;
                                 }

                                 if (var10 < var12) {
                                    break label963;
                                 }
                              }

                              label928:
                              try {
                                 // $VF: monitorexit
                                 break label959;
                              } catch (Throwable var171) {
                                 var10000 = var171;
                                 boolean var208 = false;
                                 break label928;
                              }
                           }

                           Throwable var190 = var10000;
                           // $VF: monitorexit
                           throw var190;
                        } else {
                           label998: {
                              var188 = var8;
                              if (!var6) {
                                 break label964;
                              }

                              label1000: {
                                 try {
                                    var7 = var14.e.getSoTimeout();
                                 } catch (SocketTimeoutException var181) {
                                    boolean var199 = false;
                                    break label1000;
                                 } catch (IOException var182) {
                                    boolean var198 = false;
                                    break label998;
                                 }

                                 boolean var50 = false /* VF: Semaphore variable */;

                                 boolean var9;
                                 try {
                                    var50 = true;
                                    var14.e.setSoTimeout(1);
                                    var9 = var14.i.n();
                                    var50 = false;
                                 } finally {
                                    if (var50) {
                                       try {
                                          var14.e.setSoTimeout(var7);
                                       } catch (SocketTimeoutException var174) {
                                          boolean var201 = false;
                                          break label1000;
                                       } catch (IOException var175) {
                                          boolean var200 = false;
                                          break label998;
                                       }
                                    }
                                 }

                                 if (var9) {
                                    try {
                                       var14.e.setSoTimeout(var7);
                                       break label998;
                                    } catch (SocketTimeoutException var176) {
                                       boolean var203 = false;
                                    } catch (IOException var177) {
                                       boolean var202 = false;
                                       break label998;
                                    }
                                 } else {
                                    try {
                                       var14.e.setSoTimeout(var7);
                                       break label999;
                                    } catch (SocketTimeoutException var178) {
                                       boolean var205 = false;
                                    } catch (IOException var179) {
                                       boolean var204 = false;
                                       break label998;
                                    }
                                 }
                              }

                              var188 = var8;
                              break label964;
                           }
                        }
                     }

                     var188 = false;
                     break label964;
                  }

                  var188 = var8;
                  break label964;
               }

               // $VF: monitorexit
               var188 = var8;
            }

            if (!var188) {
               var14.h();
               continue;
            }

            return var14;
         }

         while (true) {
            Throwable var191 = var10000;

            try {
               // $VF: monitorexit
               throw var191;
            } catch (Throwable var170) {
               var10000 = var170;
               boolean var210 = false;
               continue;
            }
         }
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final boolean c() {
      h var4 = this.c;
      synchronized (var4){} // $VF: monitorenter 

      Throwable var10000;
      label658: {
         m0 var5;
         try {
            var5 = this.i;
         } catch (Throwable var94) {
            var10000 = var94;
            boolean var10001 = false;
            break label658;
         }

         boolean var3 = true;
         if (var5 != null) {
            label610:
            try {
               // $VF: monitorexit
               return true;
            } catch (Throwable var88) {
               var10000 = var88;
               boolean var107 = false;
               break label610;
            }
         } else {
            label660: {
               try {
                  if (this.d()) {
                     this.i = this.a.i.c;
                     // $VF: monitorexit
                     return true;
                  }
               } catch (Throwable var95) {
                  var10000 = var95;
                  boolean var101 = false;
                  break label660;
               }

               try {
                  var98 = this.e;
               } catch (Throwable var93) {
                  var10000 = var93;
                  boolean var102 = false;
                  break label660;
               }

               boolean var2;
               label659: {
                  if (var98 != null) {
                     boolean var1;
                     label636: {
                        label635: {
                           try {
                              if (var98.b < ((List)var98.c).size()) {
                                 break label635;
                              }
                           } catch (Throwable var92) {
                              var10000 = var92;
                              boolean var103 = false;
                              break label660;
                           }

                           var1 = false;
                           break label636;
                        }

                        var1 = true;
                     }

                     var2 = var3;
                     if (var1) {
                        break label659;
                     }
                  }

                  boolean var96;
                  label627: {
                     label626: {
                        try {
                           var99 = this.f;
                           if (var99.c < var99.b.size()) {
                              break label626;
                           }
                        } catch (Throwable var91) {
                           var10000 = var91;
                           boolean var104 = false;
                           break label660;
                        }

                        var96 = false;
                        break label627;
                     }

                     var96 = true;
                  }

                  label620: {
                     label619: {
                        if (!var96) {
                           try {
                              if (((List)var99.i).isEmpty()) {
                                 break label619;
                              }
                           } catch (Throwable var90) {
                              var10000 = var90;
                              boolean var105 = false;
                              break label660;
                           }
                        }

                        var96 = true;
                        break label620;
                     }

                     var96 = false;
                  }

                  if (var96) {
                     var2 = var3;
                  } else {
                     var2 = false;
                  }
               }

               label612:
               try {
                  // $VF: monitorexit
                  return var2;
               } catch (Throwable var89) {
                  var10000 = var89;
                  boolean var106 = false;
                  break label612;
               }
            }
         }
      }

      while (true) {
         Throwable var100 = var10000;

         try {
            // $VF: monitorexit
            throw var100;
         } catch (Throwable var87) {
            var10000 = var87;
            boolean var108 = false;
            continue;
         }
      }
   }

   public final boolean d() {
      g var2 = this.a.i;
      boolean var1;
      if (var2 != null && var2.l == 0 && q0.c.p(var2.c.a.a, this.b.a)) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }
}
