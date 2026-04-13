package s0;

import java.io.IOException;
import p0.b0;
import p0.f0;
import p0.j0;
import p0.w;

public final class a implements w {
   public final int a;
   public final b0 b;

   public static int c(j0 var0, int var1) {
      String var2 = var0.x("Retry-After", null);
      if (var2 == null) {
         return var1;
      } else {
         return var2.matches("\\d+") ? Integer.valueOf(var2) : Integer.MAX_VALUE;
      }
   }

   @Override
   public final j0 a(t0.f param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 000: aload 0
      // 001: getfield s0/a.a I
      // 004: istore 3
      // 005: bipush 0
      // 006: istore 2
      // 007: iload 3
      // 008: tableswitch 20 0 0 23
      // 01c: goto 149
      // 01f: aload 1
      // 020: getfield t0/f.e Lp0/f0;
      // 023: astore 10
      // 025: aload 1
      // 026: getfield t0/f.b Ls0/l;
      // 029: astore 9
      // 02b: aload 10
      // 02d: getfield p0/f0.b Ljava/lang/String;
      // 030: ldc "GET"
      // 032: invokevirtual java/lang/String.equals (Ljava/lang/Object;)Z
      // 035: istore 6
      // 037: aload 9
      // 039: getfield s0/l.b Ls0/h;
      // 03c: astore 8
      // 03e: aload 8
      // 040: monitorenter
      // 041: aload 9
      // 043: getfield s0/l.o Z
      // 046: ifne 137
      // 049: aload 9
      // 04b: getfield s0/l.j Ls0/e;
      // 04e: ifnonnull 12b
      // 051: aload 8
      // 053: monitorexit
      // 054: aload 9
      // 056: getfield s0/l.h Ls0/f;
      // 059: astore 8
      // 05b: aload 9
      // 05d: getfield s0/l.a Lp0/b0;
      // 060: astore 11
      // 062: aload 8
      // 064: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 067: pop
      // 068: aload 1
      // 069: getfield t0/f.g I
      // 06c: istore 3
      // 06d: aload 1
      // 06e: getfield t0/f.h I
      // 071: istore 2
      // 072: aload 1
      // 073: getfield t0/f.i I
      // 076: istore 4
      // 078: aload 11
      // 07a: getfield p0/b0.y I
      // 07d: istore 5
      // 07f: aload 11
      // 081: getfield p0/b0.t Z
      // 084: istore 7
      // 086: aload 8
      // 088: iload 3
      // 089: iload 2
      // 08a: iload 4
      // 08c: iload 5
      // 08e: iload 7
      // 090: iload 6
      // 092: bipush 1
      // 093: ixor
      // 094: invokevirtual s0/f.b (IIIIZZ)Ls0/g;
      // 097: aload 11
      // 099: aload 1
      // 09a: invokevirtual s0/g.g (Lp0/b0;Lt0/f;)Lt0/b;
      // 09d: astore 11
      // 09f: new s0/e
      // 0a2: dup
      // 0a3: aload 9
      // 0a5: aload 9
      // 0a7: getfield s0/l.c Lp0/e0;
      // 0aa: aload 9
      // 0ac: getfield s0/l.d Lp0/q;
      // 0af: aload 9
      // 0b1: getfield s0/l.h Ls0/f;
      // 0b4: aload 11
      // 0b6: invokespecial s0/e.<init> (Ls0/l;Lp0/e0;Lp0/q;Ls0/f;Lt0/b;)V
      // 0b9: astore 11
      // 0bb: aload 9
      // 0bd: getfield s0/l.b Ls0/h;
      // 0c0: astore 8
      // 0c2: aload 8
      // 0c4: monitorenter
      // 0c5: aload 9
      // 0c7: aload 11
      // 0c9: putfield s0/l.j Ls0/e;
      // 0cc: aload 9
      // 0ce: bipush 0
      // 0cf: putfield s0/l.k Z
      // 0d2: aload 9
      // 0d4: bipush 0
      // 0d5: putfield s0/l.l Z
      // 0d8: aload 8
      // 0da: monitorexit
      // 0db: aload 1
      // 0dc: aload 10
      // 0de: aload 9
      // 0e0: aload 11
      // 0e2: invokevirtual t0/f.b (Lp0/f0;Ls0/l;Ls0/e;)Lp0/j0;
      // 0e5: areturn
      // 0e6: astore 1
      // 0e7: aload 8
      // 0e9: monitorexit
      // 0ea: aload 1
      // 0eb: athrow
      // 0ec: astore 9
      // 0ee: aload 8
      // 0f0: getfield s0/f.c Ls0/h;
      // 0f3: astore 1
      // 0f4: aload 1
      // 0f5: monitorenter
      // 0f6: aload 8
      // 0f8: bipush 1
      // 0f9: putfield s0/f.h Z
      // 0fc: aload 1
      // 0fd: monitorexit
      // 0fe: new s0/i
      // 101: dup
      // 102: aload 9
      // 104: invokespecial s0/i.<init> (Ljava/io/IOException;)V
      // 107: athrow
      // 108: astore 8
      // 10a: aload 1
      // 10b: monitorexit
      // 10c: aload 8
      // 10e: athrow
      // 10f: astore 9
      // 111: aload 8
      // 113: getfield s0/f.c Ls0/h;
      // 116: astore 1
      // 117: aload 1
      // 118: monitorenter
      // 119: aload 8
      // 11b: bipush 1
      // 11c: putfield s0/f.h Z
      // 11f: aload 1
      // 120: monitorexit
      // 121: aload 9
      // 123: athrow
      // 124: astore 8
      // 126: aload 1
      // 127: monitorexit
      // 128: aload 8
      // 12a: athrow
      // 12b: new java/lang/IllegalStateException
      // 12e: astore 1
      // 12f: aload 1
      // 130: ldc "cannot make a new request because the previous response is still open: please call response.close()"
      // 132: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 135: aload 1
      // 136: athrow
      // 137: new java/lang/IllegalStateException
      // 13a: astore 1
      // 13b: aload 1
      // 13c: ldc "released"
      // 13e: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 141: aload 1
      // 142: athrow
      // 143: astore 1
      // 144: aload 8
      // 146: monitorexit
      // 147: aload 1
      // 148: athrow
      // 149: aload 1
      // 14a: getfield t0/f.e Lp0/f0;
      // 14d: astore 8
      // 14f: aload 1
      // 150: getfield t0/f.b Ls0/l;
      // 153: astore 14
      // 155: aconst_null
      // 156: astore 9
      // 158: aload 14
      // 15a: getfield s0/l.g Lp0/f0;
      // 15d: astore 10
      // 15f: aload 10
      // 161: ifnull 1ab
      // 164: aload 10
      // 166: getfield p0/f0.a Lp0/u;
      // 169: aload 8
      // 16b: getfield p0/f0.a Lp0/u;
      // 16e: invokestatic q0/c.p (Lp0/u;Lp0/u;)Z
      // 171: ifeq 182
      // 174: aload 14
      // 176: getfield s0/l.h Ls0/f;
      // 179: invokevirtual s0/f.c ()Z
      // 17c: ifeq 182
      // 17f: goto 251
      // 182: aload 14
      // 184: getfield s0/l.j Ls0/e;
      // 187: ifnonnull 1a3
      // 18a: aload 14
      // 18c: getfield s0/l.h Ls0/f;
      // 18f: ifnull 1ab
      // 192: aload 14
      // 194: aconst_null
      // 195: bipush 1
      // 196: invokevirtual s0/l.d (Ljava/io/IOException;Z)Ljava/io/IOException;
      // 199: pop
      // 19a: aload 14
      // 19c: aconst_null
      // 19d: putfield s0/l.h Ls0/f;
      // 1a0: goto 1ab
      // 1a3: new java/lang/IllegalStateException
      // 1a6: dup
      // 1a7: invokespecial java/lang/IllegalStateException.<init> ()V
      // 1aa: athrow
      // 1ab: aload 14
      // 1ad: aload 8
      // 1af: putfield s0/l.g Lp0/f0;
      // 1b2: aload 14
      // 1b4: getfield s0/l.b Ls0/h;
      // 1b7: astore 17
      // 1b9: aload 8
      // 1bb: getfield p0/f0.a Lp0/u;
      // 1be: astore 16
      // 1c0: aload 16
      // 1c2: getfield p0/u.a Ljava/lang/String;
      // 1c5: ldc "https"
      // 1c7: invokevirtual java/lang/String.equals (Ljava/lang/Object;)Z
      // 1ca: istore 6
      // 1cc: aload 14
      // 1ce: getfield s0/l.a Lp0/b0;
      // 1d1: astore 15
      // 1d3: iload 6
      // 1d5: ifeq 1f8
      // 1d8: aload 15
      // 1da: getfield p0/b0.j Ljavax/net/ssl/SSLSocketFactory;
      // 1dd: astore 11
      // 1df: aload 15
      // 1e1: getfield p0/b0.l Lz0/c;
      // 1e4: astore 10
      // 1e6: aload 15
      // 1e8: getfield p0/b0.m Lp0/g;
      // 1eb: astore 13
      // 1ed: aload 11
      // 1ef: astore 12
      // 1f1: aload 13
      // 1f3: astore 11
      // 1f5: goto 202
      // 1f8: aconst_null
      // 1f9: astore 12
      // 1fb: aconst_null
      // 1fc: astore 10
      // 1fe: aload 10
      // 200: astore 11
      // 202: aload 14
      // 204: new s0/f
      // 207: dup
      // 208: aload 14
      // 20a: aload 17
      // 20c: new p0/a
      // 20f: dup
      // 210: aload 16
      // 212: getfield p0/u.d Ljava/lang/String;
      // 215: aload 16
      // 217: getfield p0/u.e I
      // 21a: aload 15
      // 21c: getfield p0/b0.q Lm0/b;
      // 21f: aload 15
      // 221: getfield p0/b0.i Ljavax/net/SocketFactory;
      // 224: aload 12
      // 226: aload 10
      // 228: aload 11
      // 22a: aload 15
      // 22c: getfield p0/b0.n Lm0/b;
      // 22f: aload 15
      // 231: getfield p0/b0.b Ljava/util/List;
      // 234: aload 15
      // 236: getfield p0/b0.c Ljava/util/List;
      // 239: aload 15
      // 23b: getfield p0/b0.g Ljava/net/ProxySelector;
      // 23e: invokespecial p0/a.<init> (Ljava/lang/String;ILm0/b;Ljavax/net/SocketFactory;Ljavax/net/ssl/SSLSocketFactory;Lz0/c;Lp0/g;Lm0/b;Ljava/util/List;Ljava/util/List;Ljava/net/ProxySelector;)V
      // 241: aload 14
      // 243: getfield s0/l.c Lp0/e0;
      // 246: aload 14
      // 248: getfield s0/l.d Lp0/q;
      // 24b: invokespecial s0/f.<init> (Ls0/l;Ls0/h;Lp0/a;Lp0/e0;Lp0/q;)V
      // 24e: putfield s0/l.h Ls0/f;
      // 251: aload 14
      // 253: getfield s0/l.b Ls0/h;
      // 256: astore 10
      // 258: aload 10
      // 25a: monitorenter
      // 25b: aload 14
      // 25d: getfield s0/l.m Z
      // 260: istore 6
      // 262: aload 10
      // 264: monitorexit
      // 265: iload 6
      // 267: ifne 60d
      // 26a: aload 1
      // 26b: aload 8
      // 26d: aload 14
      // 26f: aconst_null
      // 270: invokevirtual t0/f.b (Lp0/f0;Ls0/l;Ls0/e;)Lp0/j0;
      // 273: astore 10
      // 275: aload 10
      // 277: astore 8
      // 279: aload 9
      // 27b: ifnull 2c4
      // 27e: new p0/i0
      // 281: dup
      // 282: aload 10
      // 284: invokespecial p0/i0.<init> (Lp0/j0;)V
      // 287: astore 8
      // 289: new p0/i0
      // 28c: dup
      // 28d: aload 9
      // 28f: invokespecial p0/i0.<init> (Lp0/j0;)V
      // 292: astore 9
      // 294: aload 9
      // 296: aconst_null
      // 297: putfield p0/i0.g Lp0/l0;
      // 29a: aload 9
      // 29c: invokevirtual p0/i0.a ()Lp0/j0;
      // 29f: astore 9
      // 2a1: aload 9
      // 2a3: getfield p0/j0.g Lp0/l0;
      // 2a6: ifnonnull 2ba
      // 2a9: aload 8
      // 2ab: aload 9
      // 2ad: putfield p0/i0.j Lp0/j0;
      // 2b0: aload 8
      // 2b2: invokevirtual p0/i0.a ()Lp0/j0;
      // 2b5: astore 8
      // 2b7: goto 2c4
      // 2ba: new java/lang/IllegalArgumentException
      // 2bd: dup
      // 2be: ldc "priorResponse.body != null"
      // 2c0: invokespecial java/lang/IllegalArgumentException.<init> (Ljava/lang/String;)V
      // 2c3: athrow
      // 2c4: aload 8
      // 2c6: astore 9
      // 2c8: getstatic p0/q.c Lp0/q;
      // 2cb: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 2ce: pop
      // 2cf: aload 9
      // 2d1: getfield p0/j0.m Ls0/e;
      // 2d4: astore 12
      // 2d6: aload 12
      // 2d8: ifnull 2e8
      // 2db: aload 12
      // 2dd: invokevirtual s0/e.a ()Ls0/g;
      // 2e0: getfield s0/g.c Lp0/m0;
      // 2e3: astore 8
      // 2e5: goto 2eb
      // 2e8: aconst_null
      // 2e9: astore 8
      // 2eb: aload 9
      // 2ed: getfield p0/j0.a Lp0/f0;
      // 2f0: astore 11
      // 2f2: aload 11
      // 2f4: getfield p0/f0.b Ljava/lang/String;
      // 2f7: astore 13
      // 2f9: aload 0
      // 2fa: getfield s0/a.b Lp0/b0;
      // 2fd: astore 10
      // 2ff: aload 9
      // 301: getfield p0/j0.c I
      // 304: istore 3
      // 305: iload 3
      // 306: sipush 307
      // 309: if_icmpeq 3ed
      // 30c: iload 3
      // 30d: sipush 308
      // 310: if_icmpeq 3ed
      // 313: iload 3
      // 314: sipush 401
      // 317: if_icmpeq 3dd
      // 31a: aload 9
      // 31c: getfield p0/j0.j Lp0/j0;
      // 31f: astore 15
      // 321: iload 3
      // 322: sipush 503
      // 325: if_icmpeq 3b9
      // 328: iload 3
      // 329: sipush 407
      // 32c: if_icmpeq 381
      // 32f: iload 3
      // 330: sipush 408
      // 333: if_icmpeq 357
      // 336: iload 3
      // 337: tableswitch 29 300 303 206 206 206 206
      // 354: goto 484
      // 357: aload 10
      // 359: getfield p0/b0.t Z
      // 35c: ifne 362
      // 35f: goto 484
      // 362: aload 15
      // 364: ifnull 375
      // 367: aload 15
      // 369: getfield p0/j0.c I
      // 36c: sipush 408
      // 36f: if_icmpne 375
      // 372: goto 484
      // 375: aload 9
      // 377: bipush 0
      // 378: invokestatic s0/a.c (Lp0/j0;I)I
      // 37b: ifle 3d6
      // 37e: goto 484
      // 381: aload 8
      // 383: ifnull 390
      // 386: aload 8
      // 388: getfield p0/m0.b Ljava/net/Proxy;
      // 38b: astore 8
      // 38d: goto 399
      // 390: aload 10
      // 392: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 395: pop
      // 396: aconst_null
      // 397: astore 8
      // 399: aload 8
      // 39b: invokevirtual java/net/Proxy.type ()Ljava/net/Proxy$Type;
      // 39e: getstatic java/net/Proxy$Type.HTTP Ljava/net/Proxy$Type;
      // 3a1: if_acmpne 3ae
      // 3a4: aload 10
      // 3a6: getfield p0/b0.n Lm0/b;
      // 3a9: astore 8
      // 3ab: goto 3e4
      // 3ae: new java/net/ProtocolException
      // 3b1: dup
      // 3b2: ldc_w "Received HTTP_PROXY_AUTH (407) code while not using proxy"
      // 3b5: invokespecial java/net/ProtocolException.<init> (Ljava/lang/String;)V
      // 3b8: athrow
      // 3b9: aload 15
      // 3bb: ifnull 3cc
      // 3be: aload 15
      // 3c0: getfield p0/j0.c I
      // 3c3: sipush 503
      // 3c6: if_icmpne 3cc
      // 3c9: goto 484
      // 3cc: aload 9
      // 3ce: ldc 2147483647
      // 3d0: invokestatic s0/a.c (Lp0/j0;I)I
      // 3d3: ifne 484
      // 3d6: aload 11
      // 3d8: astore 8
      // 3da: goto 51a
      // 3dd: aload 10
      // 3df: getfield p0/b0.o Lm0/b;
      // 3e2: astore 8
      // 3e4: aload 8
      // 3e6: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 3e9: pop
      // 3ea: goto 484
      // 3ed: aload 13
      // 3ef: ldc "GET"
      // 3f1: invokevirtual java/lang/String.equals (Ljava/lang/Object;)Z
      // 3f4: ifne 405
      // 3f7: aload 13
      // 3f9: ldc_w "HEAD"
      // 3fc: invokevirtual java/lang/String.equals (Ljava/lang/Object;)Z
      // 3ff: ifne 405
      // 402: goto 484
      // 405: aload 10
      // 407: getfield p0/b0.s Z
      // 40a: ifne 410
      // 40d: goto 484
      // 410: aload 9
      // 412: ldc_w "Location"
      // 415: aconst_null
      // 416: invokevirtual p0/j0.x (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
      // 419: astore 16
      // 41b: aload 16
      // 41d: ifnonnull 423
      // 420: goto 484
      // 423: aload 11
      // 425: getfield p0/f0.a Lp0/u;
      // 428: astore 15
      // 42a: aload 15
      // 42c: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 42f: pop
      // 430: new p0/t
      // 433: astore 8
      // 435: aload 8
      // 437: invokespecial p0/t.<init> ()V
      // 43a: aload 8
      // 43c: aload 15
      // 43e: aload 16
      // 440: invokevirtual p0/t.b (Lp0/u;Ljava/lang/String;)V
      // 443: goto 44b
      // 446: astore 8
      // 448: aconst_null
      // 449: astore 8
      // 44b: aload 8
      // 44d: ifnull 45a
      // 450: aload 8
      // 452: invokevirtual p0/t.a ()Lp0/u;
      // 455: astore 8
      // 457: goto 45d
      // 45a: aconst_null
      // 45b: astore 8
      // 45d: aload 8
      // 45f: ifnonnull 465
      // 462: goto 484
      // 465: aload 11
      // 467: getfield p0/f0.a Lp0/u;
      // 46a: getfield p0/u.a Ljava/lang/String;
      // 46d: astore 15
      // 46f: aload 8
      // 471: getfield p0/u.a Ljava/lang/String;
      // 474: aload 15
      // 476: invokevirtual java/lang/String.equals (Ljava/lang/Object;)Z
      // 479: ifne 48a
      // 47c: aload 10
      // 47e: getfield p0/b0.r Z
      // 481: ifne 48a
      // 484: aconst_null
      // 485: astore 8
      // 487: goto 51a
      // 48a: new l0/m
      // 48d: dup
      // 48e: aload 11
      // 490: invokespecial l0/m.<init> (Lp0/f0;)V
      // 493: astore 15
      // 495: aload 13
      // 497: invokestatic a1/q.I (Ljava/lang/String;)Z
      // 49a: ifeq 4f7
      // 49d: aload 13
      // 49f: ldc_w "PROPFIND"
      // 4a2: invokevirtual java/lang/String.equals (Ljava/lang/Object;)Z
      // 4a5: istore 6
      // 4a7: aload 13
      // 4a9: ldc_w "PROPFIND"
      // 4ac: invokevirtual java/lang/String.equals (Ljava/lang/Object;)Z
      // 4af: bipush 1
      // 4b0: ixor
      // 4b1: ifeq 4bf
      // 4b4: aload 15
      // 4b6: ldc "GET"
      // 4b8: aconst_null
      // 4b9: invokevirtual l0/m.b (Ljava/lang/String;La1/q;)V
      // 4bc: goto 4da
      // 4bf: iload 6
      // 4c1: ifeq 4ce
      // 4c4: aload 11
      // 4c6: getfield p0/f0.d La1/q;
      // 4c9: astore 10
      // 4cb: goto 4d1
      // 4ce: aconst_null
      // 4cf: astore 10
      // 4d1: aload 15
      // 4d3: aload 13
      // 4d5: aload 10
      // 4d7: invokevirtual l0/m.b (Ljava/lang/String;La1/q;)V
      // 4da: iload 6
      // 4dc: ifne 4f7
      // 4df: aload 15
      // 4e1: ldc_w "Transfer-Encoding"
      // 4e4: invokevirtual l0/m.c (Ljava/lang/String;)V
      // 4e7: aload 15
      // 4e9: ldc_w "Content-Length"
      // 4ec: invokevirtual l0/m.c (Ljava/lang/String;)V
      // 4ef: aload 15
      // 4f1: ldc_w "Content-Type"
      // 4f4: invokevirtual l0/m.c (Ljava/lang/String;)V
      // 4f7: aload 11
      // 4f9: getfield p0/f0.a Lp0/u;
      // 4fc: aload 8
      // 4fe: invokestatic q0/c.p (Lp0/u;Lp0/u;)Z
      // 501: ifne 50c
      // 504: aload 15
      // 506: ldc_w "Authorization"
      // 509: invokevirtual l0/m.c (Ljava/lang/String;)V
      // 50c: aload 15
      // 50e: aload 8
      // 510: putfield l0/m.b Ljava/lang/Object;
      // 513: aload 15
      // 515: invokevirtual l0/m.a ()Lp0/f0;
      // 518: astore 8
      // 51a: aload 8
      // 51c: ifnonnull 551
      // 51f: aload 12
      // 521: ifnull 54e
      // 524: aload 12
      // 526: getfield s0/e.e Z
      // 529: ifeq 54e
      // 52c: aload 14
      // 52e: getfield s0/l.n Z
      // 531: ifne 546
      // 534: aload 14
      // 536: bipush 1
      // 537: putfield s0/l.n Z
      // 53a: aload 14
      // 53c: getfield s0/l.e Ls0/j;
      // 53f: invokevirtual a1/d.l ()Z
      // 542: pop
      // 543: goto 54e
      // 546: new java/lang/IllegalStateException
      // 549: dup
      // 54a: invokespecial java/lang/IllegalStateException.<init> ()V
      // 54d: athrow
      // 54e: aload 9
      // 550: areturn
      // 551: aload 9
      // 553: getfield p0/j0.g Lp0/l0;
      // 556: invokestatic q0/c.c (Ljava/io/Closeable;)V
      // 559: aload 14
      // 55b: getfield s0/l.b Ls0/h;
      // 55e: astore 10
      // 560: aload 10
      // 562: monitorenter
      // 563: aload 14
      // 565: getfield s0/l.j Ls0/e;
      // 568: ifnull 570
      // 56b: bipush 1
      // 56c: istore 3
      // 56d: goto 572
      // 570: bipush 0
      // 571: istore 3
      // 572: aload 10
      // 574: monitorexit
      // 575: iload 3
      // 576: ifeq 594
      // 579: aload 12
      // 57b: getfield s0/e.d Lt0/b;
      // 57e: invokeinterface t0/b.cancel ()V 1
      // 583: aload 12
      // 585: getfield s0/e.a Ls0/l;
      // 588: aload 12
      // 58a: bipush 1
      // 58b: bipush 1
      // 58c: aconst_null
      // 58d: invokevirtual s0/l.c (Ls0/e;ZZLjava/io/IOException;)Ljava/io/IOException;
      // 590: pop
      // 591: goto 594
      // 594: iinc 2 1
      // 597: iload 2
      // 598: bipush 20
      // 59a: if_icmpgt 5a0
      // 59d: goto 158
      // 5a0: new java/net/ProtocolException
      // 5a3: dup
      // 5a4: ldc_w "Too many follow-up requests: "
      // 5a7: iload 2
      // 5a8: invokestatic a/a.g (Ljava/lang/String;I)Ljava/lang/String;
      // 5ab: invokespecial java/net/ProtocolException.<init> (Ljava/lang/String;)V
      // 5ae: athrow
      // 5af: astore 1
      // 5b0: aload 10
      // 5b2: monitorexit
      // 5b3: aload 1
      // 5b4: athrow
      // 5b5: astore 1
      // 5b6: goto 606
      // 5b9: astore 10
      // 5bb: aload 10
      // 5bd: instanceof v0/a
      // 5c0: ifne 5c9
      // 5c3: bipush 1
      // 5c4: istore 6
      // 5c6: goto 5cc
      // 5c9: bipush 0
      // 5ca: istore 6
      // 5cc: aload 0
      // 5cd: aload 10
      // 5cf: aload 14
      // 5d1: iload 6
      // 5d3: aload 8
      // 5d5: invokevirtual s0/a.b (Ljava/io/IOException;Ls0/l;ZLp0/f0;)Z
      // 5d8: ifeq 5de
      // 5db: goto 5f8
      // 5de: aload 10
      // 5e0: athrow
      // 5e1: astore 10
      // 5e3: aload 0
      // 5e4: aload 10
      // 5e6: getfield s0/i.b Ljava/io/IOException;
      // 5e9: aload 14
      // 5eb: bipush 0
      // 5ec: aload 8
      // 5ee: invokevirtual s0/a.b (Ljava/io/IOException;Ls0/l;ZLp0/f0;)Z
      // 5f1: istore 6
      // 5f3: iload 6
      // 5f5: ifeq 600
      // 5f8: aload 14
      // 5fa: invokevirtual s0/l.b ()V
      // 5fd: goto 158
      // 600: aload 10
      // 602: getfield s0/i.a Ljava/io/IOException;
      // 605: athrow
      // 606: aload 14
      // 608: invokevirtual s0/l.b ()V
      // 60b: aload 1
      // 60c: athrow
      // 60d: new java/io/IOException
      // 610: dup
      // 611: ldc_w "Canceled"
      // 614: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
      // 617: athrow
      // 618: astore 1
      // 619: aload 10
      // 61b: monitorexit
      // 61c: aload 1
      // 61d: athrow
   }

   public final boolean b(IOException param1, l param2, boolean param3, f0 param4) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield s0/a.b Lp0/b0;
      // 04: getfield p0/b0.t Z
      // 07: ifne 0c
      // 0a: bipush 0
      // 0b: ireturn
      // 0c: iload 3
      // 0d: ifeq 20
      // 10: aload 4
      // 12: getfield p0/f0.d La1/q;
      // 15: astore 4
      // 17: aload 1
      // 18: instanceof java/io/FileNotFoundException
      // 1b: ifeq 20
      // 1e: bipush 0
      // 1f: ireturn
      // 20: aload 1
      // 21: instanceof java/net/ProtocolException
      // 24: ifeq 2a
      // 27: goto 5a
      // 2a: aload 1
      // 2b: instanceof java/io/InterruptedIOException
      // 2e: ifeq 3f
      // 31: aload 1
      // 32: instanceof java/net/SocketTimeoutException
      // 35: ifeq 5a
      // 38: iload 3
      // 39: ifne 5a
      // 3c: goto 60
      // 3f: aload 1
      // 40: instanceof javax/net/ssl/SSLHandshakeException
      // 43: ifeq 53
      // 46: aload 1
      // 47: invokevirtual java/lang/Throwable.getCause ()Ljava/lang/Throwable;
      // 4a: instanceof java/security/cert/CertificateException
      // 4d: ifeq 53
      // 50: goto 5a
      // 53: aload 1
      // 54: instanceof javax/net/ssl/SSLPeerUnverifiedException
      // 57: ifeq 60
      // 5a: bipush 0
      // 5b: istore 5
      // 5d: goto 63
      // 60: bipush 1
      // 61: istore 5
      // 63: iload 5
      // 65: ifne 6a
      // 68: bipush 0
      // 69: ireturn
      // 6a: aload 2
      // 6b: getfield s0/l.h Ls0/f;
      // 6e: astore 4
      // 70: aload 4
      // 72: getfield s0/f.c Ls0/h;
      // 75: astore 1
      // 76: aload 1
      // 77: monitorenter
      // 78: aload 4
      // 7a: getfield s0/f.h Z
      // 7d: istore 3
      // 7e: aload 1
      // 7f: monitorexit
      // 80: iload 3
      // 81: ifeq 94
      // 84: aload 2
      // 85: getfield s0/l.h Ls0/f;
      // 88: invokevirtual s0/f.c ()Z
      // 8b: ifeq 94
      // 8e: bipush 1
      // 8f: istore 5
      // 91: goto 97
      // 94: bipush 0
      // 95: istore 5
      // 97: iload 5
      // 99: ifne 9e
      // 9c: bipush 0
      // 9d: ireturn
      // 9e: bipush 1
      // 9f: ireturn
      // a0: astore 2
      // a1: aload 1
      // a2: monitorexit
      // a3: aload 2
      // a4: athrow
   }
}
