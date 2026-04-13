package s0;

import a1.n;
import a1.v;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.Proxy.Type;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import l0.m;
import p0.b0;
import p0.c0;
import p0.f0;
import p0.i0;
import p0.j0;
import p0.m0;
import p0.q;
import p0.r;
import p0.u;
import v0.o;
import v0.s;
import v0.t;
import v0.y;
import v0.z;

public final class g extends o {
   public final h b;
   public final m0 c;
   public Socket d;
   public Socket e;
   public r f;
   public c0 g;
   public s h;
   public a1.o i;
   public n j;
   public boolean k;
   public int l;
   public int m;
   public int n;
   public int o = 1;
   public final ArrayList p = new ArrayList();
   public long q = Long.MAX_VALUE;

   public g(h var1, m0 var2) {
      this.b = var1;
      this.c = var2;
   }

   @Override
   public final void a(s param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield s0/g.b Ls0/h;
      // 04: astore 3
      // 05: aload 3
      // 06: monitorenter
      // 07: aload 1
      // 08: monitorenter
      // 09: aload 1
      // 0a: getfield v0/s.s Lz/d;
      // 0d: astore 4
      // 0f: aload 4
      // 11: getfield z/d.b I
      // 14: bipush 16
      // 16: iand
      // 17: ifeq 28
      // 1a: aload 4
      // 1c: getfield z/d.c Ljava/lang/Object;
      // 1f: checkcast [I
      // 22: bipush 4
      // 23: iaload
      // 24: istore 2
      // 25: goto 2b
      // 28: ldc 2147483647
      // 2a: istore 2
      // 2b: aload 1
      // 2c: monitorexit
      // 2d: aload 0
      // 2e: iload 2
      // 2f: putfield s0/g.o I
      // 32: aload 3
      // 33: monitorexit
      // 34: return
      // 35: astore 1
      // 36: goto 40
      // 39: astore 4
      // 3b: aload 1
      // 3c: monitorexit
      // 3d: aload 4
      // 3f: athrow
      // 40: aload 3
      // 41: monitorexit
      // 42: aload 1
      // 43: athrow
   }

   @Override
   public final void b(y var1) {
      var1.c(v0.b.f, null);
   }

   public final void c(int param1, int param2, int param3, int param4, boolean param5, q param6) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 000: aload 0
      // 001: getfield s0/g.g Lp0/c0;
      // 004: ifnonnull 286
      // 007: aload 0
      // 008: getfield s0/g.c Lp0/m0;
      // 00b: getfield p0/m0.a Lp0/a;
      // 00e: astore 10
      // 010: aload 10
      // 012: getfield p0/a.f Ljava/util/List;
      // 015: astore 12
      // 017: new s0/b
      // 01a: dup
      // 01b: aload 12
      // 01d: invokespecial s0/b.<init> (Ljava/util/List;)V
      // 020: astore 11
      // 022: aload 10
      // 024: getfield p0/a.i Ljavax/net/ssl/SSLSocketFactory;
      // 027: ifnonnull 07d
      // 02a: aload 12
      // 02c: getstatic p0/k.f Lp0/k;
      // 02f: invokeinterface java/util/List.contains (Ljava/lang/Object;)Z 2
      // 034: ifeq 06c
      // 037: aload 0
      // 038: getfield s0/g.c Lp0/m0;
      // 03b: getfield p0/m0.a Lp0/a;
      // 03e: getfield p0/a.a Lp0/u;
      // 041: getfield p0/u.d Ljava/lang/String;
      // 044: astore 10
      // 046: getstatic w0/i.a Lw0/i;
      // 049: aload 10
      // 04b: invokevirtual w0/i.l (Ljava/lang/String;)Z
      // 04e: ifeq 054
      // 051: goto 08d
      // 054: new s0/i
      // 057: dup
      // 058: new java/net/UnknownServiceException
      // 05b: dup
      // 05c: ldc "CLEARTEXT communication to "
      // 05e: aload 10
      // 060: ldc " not permitted by network security policy"
      // 062: invokestatic a/a.l (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
      // 065: invokespecial java/net/UnknownServiceException.<init> (Ljava/lang/String;)V
      // 068: invokespecial s0/i.<init> (Ljava/io/IOException;)V
      // 06b: athrow
      // 06c: new s0/i
      // 06f: dup
      // 070: new java/net/UnknownServiceException
      // 073: dup
      // 074: ldc "CLEARTEXT communication not enabled for client"
      // 076: invokespecial java/net/UnknownServiceException.<init> (Ljava/lang/String;)V
      // 079: invokespecial s0/i.<init> (Ljava/io/IOException;)V
      // 07c: athrow
      // 07d: aload 10
      // 07f: getfield p0/a.e Ljava/util/List;
      // 082: getstatic p0/c0.f Lp0/c0;
      // 085: invokeinterface java/util/List.contains (Ljava/lang/Object;)Z 2
      // 08a: ifne 275
      // 08d: aconst_null
      // 08e: astore 10
      // 090: bipush 1
      // 091: istore 8
      // 093: bipush 0
      // 094: istore 9
      // 096: aload 0
      // 097: getfield s0/g.c Lp0/m0;
      // 09a: astore 12
      // 09c: aload 12
      // 09e: getfield p0/m0.a Lp0/a;
      // 0a1: getfield p0/a.i Ljavax/net/ssl/SSLSocketFactory;
      // 0a4: ifnull 0bb
      // 0a7: aload 12
      // 0a9: getfield p0/m0.b Ljava/net/Proxy;
      // 0ac: invokevirtual java/net/Proxy.type ()Ljava/net/Proxy$Type;
      // 0af: getstatic java/net/Proxy$Type.HTTP Ljava/net/Proxy$Type;
      // 0b2: if_acmpne 0bb
      // 0b5: bipush 1
      // 0b6: istore 7
      // 0b8: goto 0be
      // 0bb: bipush 0
      // 0bc: istore 7
      // 0be: iload 7
      // 0c0: ifeq 0d6
      // 0c3: aload 0
      // 0c4: iload 1
      // 0c5: iload 2
      // 0c6: iload 3
      // 0c7: aload 6
      // 0c9: invokevirtual s0/g.e (IIILp0/q;)V
      // 0cc: aload 0
      // 0cd: getfield s0/g.d Ljava/net/Socket;
      // 0d0: ifnonnull 0de
      // 0d3: goto 0f7
      // 0d6: aload 0
      // 0d7: iload 1
      // 0d8: iload 2
      // 0d9: aload 6
      // 0db: invokevirtual s0/g.d (IILp0/q;)V
      // 0de: aload 0
      // 0df: aload 11
      // 0e1: iload 4
      // 0e3: aload 6
      // 0e5: invokevirtual s0/g.f (Ls0/b;ILp0/q;)V
      // 0e8: aload 0
      // 0e9: getfield s0/g.c Lp0/m0;
      // 0ec: getfield p0/m0.c Ljava/net/InetSocketAddress;
      // 0ef: astore 12
      // 0f1: aload 6
      // 0f3: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 0f6: pop
      // 0f7: aload 0
      // 0f8: getfield s0/g.c Lp0/m0;
      // 0fb: astore 6
      // 0fd: aload 6
      // 0ff: getfield p0/m0.a Lp0/a;
      // 102: getfield p0/a.i Ljavax/net/ssl/SSLSocketFactory;
      // 105: ifnull 11c
      // 108: aload 6
      // 10a: getfield p0/m0.b Ljava/net/Proxy;
      // 10d: invokevirtual java/net/Proxy.type ()Ljava/net/Proxy$Type;
      // 110: getstatic java/net/Proxy$Type.HTTP Ljava/net/Proxy$Type;
      // 113: if_acmpne 11c
      // 116: iload 8
      // 118: istore 1
      // 119: goto 11e
      // 11c: bipush 0
      // 11d: istore 1
      // 11e: iload 1
      // 11f: ifeq 13d
      // 122: aload 0
      // 123: getfield s0/g.d Ljava/net/Socket;
      // 126: ifnull 12c
      // 129: goto 13d
      // 12c: new s0/i
      // 12f: dup
      // 130: new java/net/ProtocolException
      // 133: dup
      // 134: ldc "Too many tunnel connections attempted: 21"
      // 136: invokespecial java/net/ProtocolException.<init> (Ljava/lang/String;)V
      // 139: invokespecial s0/i.<init> (Ljava/io/IOException;)V
      // 13c: athrow
      // 13d: aload 0
      // 13e: getfield s0/g.h Lv0/s;
      // 141: ifnull 197
      // 144: aload 0
      // 145: getfield s0/g.b Ls0/h;
      // 148: astore 6
      // 14a: aload 6
      // 14c: monitorenter
      // 14d: aload 0
      // 14e: getfield s0/g.h Lv0/s;
      // 151: astore 10
      // 153: aload 10
      // 155: monitorenter
      // 156: aload 10
      // 158: getfield v0/s.s Lz/d;
      // 15b: astore 11
      // 15d: aload 11
      // 15f: getfield z/d.b I
      // 162: bipush 16
      // 164: iand
      // 165: ifeq 176
      // 168: aload 11
      // 16a: getfield z/d.c Ljava/lang/Object;
      // 16d: checkcast [I
      // 170: bipush 4
      // 171: iaload
      // 172: istore 1
      // 173: goto 179
      // 176: ldc 2147483647
      // 178: istore 1
      // 179: aload 10
      // 17b: monitorexit
      // 17c: aload 0
      // 17d: iload 1
      // 17e: putfield s0/g.o I
      // 181: aload 6
      // 183: monitorexit
      // 184: goto 197
      // 187: astore 11
      // 189: aload 10
      // 18b: monitorexit
      // 18c: aload 11
      // 18e: athrow
      // 18f: astore 10
      // 191: aload 6
      // 193: monitorexit
      // 194: aload 10
      // 196: athrow
      // 197: return
      // 198: astore 12
      // 19a: aload 0
      // 19b: getfield s0/g.e Ljava/net/Socket;
      // 19e: invokestatic q0/c.d (Ljava/net/Socket;)V
      // 1a1: aload 0
      // 1a2: getfield s0/g.d Ljava/net/Socket;
      // 1a5: invokestatic q0/c.d (Ljava/net/Socket;)V
      // 1a8: aload 0
      // 1a9: aconst_null
      // 1aa: putfield s0/g.e Ljava/net/Socket;
      // 1ad: aload 0
      // 1ae: aconst_null
      // 1af: putfield s0/g.d Ljava/net/Socket;
      // 1b2: aload 0
      // 1b3: aconst_null
      // 1b4: putfield s0/g.i La1/o;
      // 1b7: aload 0
      // 1b8: aconst_null
      // 1b9: putfield s0/g.j La1/n;
      // 1bc: aload 0
      // 1bd: aconst_null
      // 1be: putfield s0/g.f Lp0/r;
      // 1c1: aload 0
      // 1c2: aconst_null
      // 1c3: putfield s0/g.g Lp0/c0;
      // 1c6: aload 0
      // 1c7: aconst_null
      // 1c8: putfield s0/g.h Lv0/s;
      // 1cb: aload 0
      // 1cc: getfield s0/g.c Lp0/m0;
      // 1cf: getfield p0/m0.c Ljava/net/InetSocketAddress;
      // 1d2: astore 13
      // 1d4: aload 6
      // 1d6: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 1d9: pop
      // 1da: aload 10
      // 1dc: ifnonnull 1ed
      // 1df: new s0/i
      // 1e2: dup
      // 1e3: aload 12
      // 1e5: invokespecial s0/i.<init> (Ljava/io/IOException;)V
      // 1e8: astore 10
      // 1ea: goto 216
      // 1ed: aload 10
      // 1ef: getfield s0/i.a Ljava/io/IOException;
      // 1f2: astore 14
      // 1f4: getstatic q0/c.j Ljava/lang/reflect/Method;
      // 1f7: astore 13
      // 1f9: aload 13
      // 1fb: ifnull 20f
      // 1fe: aload 13
      // 200: aload 14
      // 202: bipush 1
      // 203: anewarray 194
      // 206: dup
      // 207: bipush 0
      // 208: aload 12
      // 20a: aastore
      // 20b: invokevirtual java/lang/reflect/Method.invoke (Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
      // 20e: pop
      // 20f: aload 10
      // 211: aload 12
      // 213: putfield s0/i.b Ljava/io/IOException;
      // 216: iload 5
      // 218: ifeq 272
      // 21b: aload 11
      // 21d: bipush 1
      // 21e: putfield s0/b.d Z
      // 221: aload 11
      // 223: getfield s0/b.c Z
      // 226: ifne 22c
      // 229: goto 26a
      // 22c: aload 12
      // 22e: instanceof java/net/ProtocolException
      // 231: ifeq 237
      // 234: goto 26a
      // 237: aload 12
      // 239: instanceof java/io/InterruptedIOException
      // 23c: ifeq 242
      // 23f: goto 26a
      // 242: aload 12
      // 244: instanceof javax/net/ssl/SSLHandshakeException
      // 247: ifeq 258
      // 24a: aload 12
      // 24c: invokevirtual java/lang/Throwable.getCause ()Ljava/lang/Throwable;
      // 24f: instanceof java/security/cert/CertificateException
      // 252: ifeq 258
      // 255: goto 26a
      // 258: aload 12
      // 25a: instanceof javax/net/ssl/SSLPeerUnverifiedException
      // 25d: ifeq 263
      // 260: goto 26a
      // 263: aload 12
      // 265: instanceof javax/net/ssl/SSLException
      // 268: istore 9
      // 26a: iload 9
      // 26c: ifeq 272
      // 26f: goto 090
      // 272: aload 10
      // 274: athrow
      // 275: new s0/i
      // 278: dup
      // 279: new java/net/UnknownServiceException
      // 27c: dup
      // 27d: ldc "H2_PRIOR_KNOWLEDGE cannot be used with HTTPS"
      // 27f: invokespecial java/net/UnknownServiceException.<init> (Ljava/lang/String;)V
      // 282: invokespecial s0/i.<init> (Ljava/io/IOException;)V
      // 285: athrow
      // 286: new java/lang/IllegalStateException
      // 289: dup
      // 28a: ldc_w "already connected"
      // 28d: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 290: athrow
      // 291: astore 13
      // 293: goto 20f
   }

   public final void d(int var1, int var2, q var3) {
      m0 var4 = this.c;
      Proxy var6 = var4.b;
      InetSocketAddress var5 = var4.c;
      Socket var11;
      if (var6.type() != Type.DIRECT && var6.type() != Type.HTTP) {
         var11 = new Socket(var6);
      } else {
         var11 = var4.a.c.createSocket();
      }

      this.d = var11;
      var3.getClass();
      this.d.setSoTimeout(var2);

      try {
         w0.i.a.h(this.d, var5, var1);
      } catch (ConnectException var7) {
         StringBuilder var12 = new StringBuilder("Failed to connect to ");
         var12.append(var5);
         ConnectException var13 = new ConnectException(var12.toString());
         var13.initCause(var7);
         throw var13;
      }

      try {
         a1.b var14 = a1.l.b(this.d);
         a1.o var9 = new a1.o(var14);
         this.i = var9;
         a1.a var15 = a1.l.a(this.d);
         n var10 = new n(var15);
         this.j = var10;
      } catch (NullPointerException var8) {
         if ("throw with null exception".equals(var8.getMessage())) {
            throw new IOException(var8);
         }
      }
   }

   public final void e(int var1, int var2, int var3, q var4) {
      m var8 = new m();
      m0 var9 = this.c;
      u var7 = var9.a.a;
      if (var7 != null) {
         var8.b = var7;
         var8.b("CONNECT", null);
         p0.a var17 = var9.a;
         String var20 = q0.c.j(var17.a, true);
         ((p0.f)var8.c).c("Host", var20);
         ((p0.f)var8.c).c("Proxy-Connection", "Keep-Alive");
         ((p0.f)var8.c).c("User-Agent", "android okhttp3");
         f0 var18 = var8.a();
         i0 var21 = new i0();
         var21.a = var18;
         var21.b = c0.c;
         var21.c = 407;
         var21.d = "Preemptive Authenticate";
         var21.g = q0.c.d;
         var21.k = -1L;
         var21.l = -1L;
         var21.f.c("Proxy-Authenticate", "OkHttp-Preemptive");
         var21.a();
         var17.d.getClass();
         this.d(var1, var2, var4);
         StringBuilder var13 = new StringBuilder("CONNECT ");
         var13.append(q0.c.j(var18.a, true));
         var13.append(" HTTP/1.1");
         String var10 = var13.toString();
         a1.o var14 = this.i;
         u0.g var22 = new u0.g(null, null, var14, this.j);
         v var11 = var14.a();
         long var5 = (long)var2;
         TimeUnit var15 = TimeUnit.MILLISECONDS;
         var11.g(var5, var15);
         this.j.a().g((long)var3, var15);
         var22.l(var18.c, var10);
         var22.c();
         i0 var24 = var22.g(false);
         var24.a = var18;
         j0 var19 = var24.a();
         var5 = t0.e.a(var19);
         if (var5 != -1L) {
            u0.d var23 = var22.i(var5);
            q0.c.q(var23, Integer.MAX_VALUE, var15);
            var23.close();
         }

         var1 = var19.c;
         if (var1 != 200) {
            if (var1 == 407) {
               var17.d.getClass();
               throw new IOException("Failed to authenticate with proxy");
            } else {
               throw new IOException(a.a.g("Unexpected response code for CONNECT: ", var1));
            }
         } else if (!this.i.a.n() || !this.j.a.n()) {
            throw new IOException("TLS tunnel buffered too many bytes!");
         }
      } else {
         throw new NullPointerException("url == null");
      }
   }

   public final void f(b param1, int param2, q param3) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 000: aload 0
      // 001: getfield s0/g.c Lp0/m0;
      // 004: astore 9
      // 006: aload 9
      // 008: getfield p0/m0.a Lp0/a;
      // 00b: astore 6
      // 00d: aload 6
      // 00f: getfield p0/a.i Ljavax/net/ssl/SSLSocketFactory;
      // 012: astore 8
      // 014: getstatic p0/c0.c Lp0/c0;
      // 017: astore 7
      // 019: aload 8
      // 01b: ifnonnull 052
      // 01e: getstatic p0/c0.f Lp0/c0;
      // 021: astore 1
      // 022: aload 6
      // 024: getfield p0/a.e Ljava/util/List;
      // 027: aload 1
      // 028: invokeinterface java/util/List.contains (Ljava/lang/Object;)Z 2
      // 02d: ifeq 043
      // 030: aload 0
      // 031: aload 0
      // 032: getfield s0/g.d Ljava/net/Socket;
      // 035: putfield s0/g.e Ljava/net/Socket;
      // 038: aload 0
      // 039: aload 1
      // 03a: putfield s0/g.g Lp0/c0;
      // 03d: aload 0
      // 03e: iload 2
      // 03f: invokevirtual s0/g.i (I)V
      // 042: return
      // 043: aload 0
      // 044: aload 0
      // 045: getfield s0/g.d Ljava/net/Socket;
      // 048: putfield s0/g.e Ljava/net/Socket;
      // 04b: aload 0
      // 04c: aload 7
      // 04e: putfield s0/g.g Lp0/c0;
      // 051: return
      // 052: aload 3
      // 053: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 056: pop
      // 057: aload 9
      // 059: getfield p0/m0.a Lp0/a;
      // 05c: astore 10
      // 05e: aload 10
      // 060: getfield p0/a.i Ljavax/net/ssl/SSLSocketFactory;
      // 063: astore 6
      // 065: aload 10
      // 067: getfield p0/a.a Lp0/u;
      // 06a: astore 11
      // 06c: aconst_null
      // 06d: astore 3
      // 06e: aconst_null
      // 06f: astore 9
      // 071: aconst_null
      // 072: astore 8
      // 074: aload 6
      // 076: aload 0
      // 077: getfield s0/g.d Ljava/net/Socket;
      // 07a: aload 11
      // 07c: getfield p0/u.d Ljava/lang/String;
      // 07f: aload 11
      // 081: getfield p0/u.e I
      // 084: bipush 1
      // 085: invokevirtual javax/net/ssl/SSLSocketFactory.createSocket (Ljava/net/Socket;Ljava/lang/String;IZ)Ljava/net/Socket;
      // 088: checkcast javax/net/ssl/SSLSocket
      // 08b: astore 6
      // 08d: aload 1
      // 08e: aload 6
      // 090: invokevirtual s0/b.a (Ljavax/net/ssl/SSLSocket;)Lp0/k;
      // 093: astore 3
      // 094: aload 11
      // 096: getfield p0/u.d Ljava/lang/String;
      // 099: astore 1
      // 09a: aload 3
      // 09b: getfield p0/k.b Z
      // 09e: istore 5
      // 0a0: iload 5
      // 0a2: ifeq 0b3
      // 0a5: getstatic w0/i.a Lw0/i;
      // 0a8: aload 6
      // 0aa: aload 1
      // 0ab: aload 10
      // 0ad: getfield p0/a.e Ljava/util/List;
      // 0b0: invokevirtual w0/i.g (Ljavax/net/ssl/SSLSocket;Ljava/lang/String;Ljava/util/List;)V
      // 0b3: aload 6
      // 0b5: invokevirtual javax/net/ssl/SSLSocket.startHandshake ()V
      // 0b8: aload 6
      // 0ba: invokevirtual javax/net/ssl/SSLSocket.getSession ()Ljavax/net/ssl/SSLSession;
      // 0bd: astore 9
      // 0bf: aload 9
      // 0c1: invokestatic p0/r.a (Ljavax/net/ssl/SSLSession;)Lp0/r;
      // 0c4: astore 3
      // 0c5: aload 10
      // 0c7: getfield p0/a.j Ljavax/net/ssl/HostnameVerifier;
      // 0ca: aload 1
      // 0cb: aload 9
      // 0cd: invokeinterface javax/net/ssl/HostnameVerifier.verify (Ljava/lang/String;Ljavax/net/ssl/SSLSession;)Z 3
      // 0d2: istore 4
      // 0d4: aload 3
      // 0d5: getfield p0/r.c Ljava/util/List;
      // 0d8: astore 9
      // 0da: iload 4
      // 0dc: ifne 185
      // 0df: aload 9
      // 0e1: invokeinterface java/util/List.isEmpty ()Z 1
      // 0e6: istore 4
      // 0e8: iload 4
      // 0ea: ifne 15b
      // 0ed: aload 9
      // 0ef: bipush 0
      // 0f0: invokeinterface java/util/List.get (I)Ljava/lang/Object; 2
      // 0f5: checkcast java/security/cert/X509Certificate
      // 0f8: astore 8
      // 0fa: new javax/net/ssl/SSLPeerUnverifiedException
      // 0fd: astore 7
      // 0ff: new java/lang/StringBuilder
      // 102: astore 3
      // 103: aload 3
      // 104: ldc_w "Hostname "
      // 107: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 10a: aload 3
      // 10b: aload 1
      // 10c: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 10f: pop
      // 110: aload 3
      // 111: ldc_w " not verified:\n    certificate: "
      // 114: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 117: pop
      // 118: aload 3
      // 119: aload 8
      // 11b: invokestatic p0/g.b (Ljava/security/cert/X509Certificate;)Ljava/lang/String;
      // 11e: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 121: pop
      // 122: aload 3
      // 123: ldc_w "\n    DN: "
      // 126: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 129: pop
      // 12a: aload 3
      // 12b: aload 8
      // 12d: invokevirtual java/security/cert/X509Certificate.getSubjectDN ()Ljava/security/Principal;
      // 130: invokeinterface java/security/Principal.getName ()Ljava/lang/String; 1
      // 135: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 138: pop
      // 139: aload 3
      // 13a: ldc_w "\n    subjectAltNames: "
      // 13d: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 140: pop
      // 141: aload 3
      // 142: aload 8
      // 144: invokestatic z0/c.a (Ljava/security/cert/X509Certificate;)Ljava/util/ArrayList;
      // 147: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      // 14a: pop
      // 14b: aload 7
      // 14d: aload 3
      // 14e: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 151: invokespecial javax/net/ssl/SSLPeerUnverifiedException.<init> (Ljava/lang/String;)V
      // 154: aload 7
      // 156: athrow
      // 157: astore 1
      // 158: goto 243
      // 15b: new javax/net/ssl/SSLPeerUnverifiedException
      // 15e: astore 7
      // 160: new java/lang/StringBuilder
      // 163: astore 3
      // 164: aload 3
      // 165: ldc_w "Hostname "
      // 168: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 16b: aload 3
      // 16c: aload 1
      // 16d: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 170: pop
      // 171: aload 3
      // 172: ldc_w " not verified (no certificates)"
      // 175: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 178: pop
      // 179: aload 7
      // 17b: aload 3
      // 17c: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 17f: invokespecial javax/net/ssl/SSLPeerUnverifiedException.<init> (Ljava/lang/String;)V
      // 182: aload 7
      // 184: athrow
      // 185: aload 10
      // 187: getfield p0/a.k Lp0/g;
      // 18a: aload 1
      // 18b: aload 9
      // 18d: invokevirtual p0/g.a (Ljava/lang/String;Ljava/util/List;)V
      // 190: aload 8
      // 192: astore 1
      // 193: iload 5
      // 195: ifeq 1a1
      // 198: getstatic w0/i.a Lw0/i;
      // 19b: aload 6
      // 19d: invokevirtual w0/i.j (Ljavax/net/ssl/SSLSocket;)Ljava/lang/String;
      // 1a0: astore 1
      // 1a1: aload 0
      // 1a2: aload 6
      // 1a4: putfield s0/g.e Ljava/net/Socket;
      // 1a7: aload 6
      // 1a9: invokestatic a1/l.b (Ljava/net/Socket;)La1/b;
      // 1ac: astore 9
      // 1ae: new a1/o
      // 1b1: astore 8
      // 1b3: aload 8
      // 1b5: aload 9
      // 1b7: invokespecial a1/o.<init> (La1/t;)V
      // 1ba: aload 0
      // 1bb: aload 8
      // 1bd: putfield s0/g.i La1/o;
      // 1c0: aload 0
      // 1c1: getfield s0/g.e Ljava/net/Socket;
      // 1c4: invokestatic a1/l.a (Ljava/net/Socket;)La1/a;
      // 1c7: astore 9
      // 1c9: new a1/n
      // 1cc: astore 8
      // 1ce: aload 8
      // 1d0: aload 9
      // 1d2: invokespecial a1/n.<init> (La1/s;)V
      // 1d5: aload 0
      // 1d6: aload 8
      // 1d8: putfield s0/g.j La1/n;
      // 1db: aload 0
      // 1dc: aload 3
      // 1dd: putfield s0/g.f Lp0/r;
      // 1e0: aload 7
      // 1e2: astore 3
      // 1e3: aload 1
      // 1e4: ifnull 1ec
      // 1e7: aload 1
      // 1e8: invokestatic p0/c0.a (Ljava/lang/String;)Lp0/c0;
      // 1eb: astore 3
      // 1ec: aload 0
      // 1ed: aload 3
      // 1ee: putfield s0/g.g Lp0/c0;
      // 1f1: getstatic w0/i.a Lw0/i;
      // 1f4: aload 6
      // 1f6: invokevirtual w0/i.a (Ljavax/net/ssl/SSLSocket;)V
      // 1f9: aload 0
      // 1fa: getfield s0/g.g Lp0/c0;
      // 1fd: getstatic p0/c0.e Lp0/c0;
      // 200: if_acmpne 208
      // 203: aload 0
      // 204: iload 2
      // 205: invokevirtual s0/g.i (I)V
      // 208: return
      // 209: astore 3
      // 20a: aload 6
      // 20c: astore 1
      // 20d: aload 3
      // 20e: astore 6
      // 210: goto 21c
      // 213: astore 1
      // 214: goto 240
      // 217: astore 6
      // 219: aload 9
      // 21b: astore 1
      // 21c: aload 1
      // 21d: astore 3
      // 21e: aload 6
      // 220: invokestatic q0/c.n (Ljava/lang/AssertionError;)Z
      // 223: ifeq 23b
      // 226: aload 1
      // 227: astore 3
      // 228: new java/io/IOException
      // 22b: astore 7
      // 22d: aload 1
      // 22e: astore 3
      // 22f: aload 7
      // 231: aload 6
      // 233: invokespecial java/io/IOException.<init> (Ljava/lang/Throwable;)V
      // 236: aload 1
      // 237: astore 3
      // 238: aload 7
      // 23a: athrow
      // 23b: aload 1
      // 23c: astore 3
      // 23d: aload 6
      // 23f: athrow
      // 240: aload 3
      // 241: astore 6
      // 243: aload 6
      // 245: ifnull 250
      // 248: getstatic w0/i.a Lw0/i;
      // 24b: aload 6
      // 24d: invokevirtual w0/i.a (Ljavax/net/ssl/SSLSocket;)V
      // 250: aload 6
      // 252: invokestatic q0/c.d (Ljava/net/Socket;)V
      // 255: aload 1
      // 256: athrow
   }

   public final t0.b g(b0 var1, t0.f var2) {
      if (this.h != null) {
         return new t(var1, this, var2, this.h);
      } else {
         Socket var6 = this.e;
         int var3 = var2.h;
         var6.setSoTimeout(var3);
         v var7 = this.i.a();
         long var4 = (long)var3;
         TimeUnit var8 = TimeUnit.MILLISECONDS;
         var7.g(var4, var8);
         this.j.a().g((long)var2.i, var8);
         return new u0.g(var1, this, this.i, this.j);
      }
   }

   public final void h() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield s0/g.b Ls0/h;
      // 04: astore 2
      // 05: aload 2
      // 06: monitorenter
      // 07: aload 0
      // 08: bipush 1
      // 09: putfield s0/g.k Z
      // 0c: aload 2
      // 0d: monitorexit
      // 0e: return
      // 0f: astore 1
      // 10: aload 2
      // 11: monitorexit
      // 12: aload 1
      // 13: athrow
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final void i(int var1) {
      this.e.setSoTimeout(0);
      v0.m var4 = new v0.m();
      Socket var5 = this.e;
      String var6 = this.c.a.a.d;
      a1.o var3 = this.i;
      n var7 = this.j;
      var4.a = var5;
      var4.b = var6;
      var4.c = var3;
      var4.d = var7;
      var4.e = this;
      var4.f = var1;
      s var103 = new s(var4);
      this.h = var103;
      z var101 = var103.u;
      synchronized (var101){} // $VF: monitorenter 

      Throwable var10000;
      label727: {
         label722: {
            label728: {
               try {
                  if (var101.e) {
                     break label728;
                  }

                  if (!var101.b) {
                     break label722;
                  }
               } catch (Throwable var97) {
                  var10000 = var97;
                  boolean var10001 = false;
                  break label727;
               }

               try {
                  Logger var108 = z.g;
                  if (var108.isLoggable(Level.FINE)) {
                     var108.fine(String.format(">> CONNECTION %s", v0.g.a.f()));
                  }
               } catch (Throwable var96) {
                  var10000 = var96;
                  boolean var111 = false;
                  break label727;
               }

               try {
                  var101.a.p((byte[])v0.g.a.a.clone());
                  var101.a.flush();
                  break label722;
               } catch (Throwable var95) {
                  var10000 = var95;
                  boolean var112 = false;
                  break label727;
               }
            }

            try {
               IOException var107 = new IOException("closed");
               throw var107;
            } catch (Throwable var94) {
               var10000 = var94;
               boolean var118 = false;
               break label727;
            }
         }

         // $VF: monitorexit
         z var102 = var103.u;
         z.d var109 = var103.r;
         synchronized (var102){} // $VF: monitorenter 

         label729: {
            label700: {
               try {
                  if (!var102.e) {
                     var102.z(0, Integer.bitCount(var109.b) * 6, (byte)4, (byte)0);
                     break label700;
                  }
               } catch (Throwable var93) {
                  var10000 = var93;
                  boolean var113 = false;
                  break label729;
               }

               try {
                  IOException var104 = new IOException("closed");
                  throw var104;
               } catch (Throwable var92) {
                  var10000 = var92;
                  boolean var114 = false;
                  break label729;
               }
            }

            var1 = 0;

            while (true) {
               if (var1 >= 10) {
                  try {
                     var102.a.flush();
                  } catch (Throwable var89) {
                     var10000 = var89;
                     boolean var117 = false;
                     break;
                  }

                  // $VF: monitorexit
                  var1 = var103.r.d();
                  if (var1 != 65535) {
                     var103.u.D(0, (long)(var1 - 65535));
                  }

                  new Thread(var103.v).start();
                  return;
               }

               boolean var2;
               label687: {
                  label686: {
                     try {
                        if ((1 << var1 & var109.b) != 0) {
                           break label686;
                        }
                     } catch (Throwable var91) {
                        var10000 = var91;
                        boolean var115 = false;
                        break;
                     }

                     var2 = 0;
                     break label687;
                  }

                  var2 = 1;
               }

               if (var2) {
                  if (var1 == 4) {
                     var2 = 3;
                  } else if (var1 == 7) {
                     var2 = 4;
                  } else {
                     var2 = var1;
                  }

                  try {
                     var102.a.j(var2);
                     var102.a.k(((int[])var109.c)[var1]);
                  } catch (Throwable var90) {
                     var10000 = var90;
                     boolean var116 = false;
                     break;
                  }
               }

               var1++;
            }
         }

         Throwable var105 = var10000;
         // $VF: monitorexit
         throw var105;
      }

      Throwable var106 = var10000;
      // $VF: monitorexit
      throw var106;
   }

   public final boolean j(u var1) {
      int var2 = var1.e;
      u var6 = this.c.a.a;
      int var3 = var6.e;
      boolean var5 = false;
      if (var2 != var3) {
         return false;
      } else {
         String var7 = var1.d;
         if (!var7.equals(var6.d)) {
            r var8 = this.f;
            boolean var4 = var5;
            if (var8 != null) {
               var4 = var5;
               if (z0.c.c(var7, (X509Certificate)var8.c.get(0))) {
                  var4 = true;
               }
            }

            return var4;
         } else {
            return true;
         }
      }
   }

   @Override
   public final String toString() {
      StringBuilder var2 = new StringBuilder("Connection{");
      m0 var1 = this.c;
      var2.append(var1.a.a.d);
      var2.append(":");
      var2.append(var1.a.a.e);
      var2.append(", proxy=");
      var2.append(var1.b);
      var2.append(" hostAddress=");
      var2.append(var1.c);
      var2.append(" cipherSuite=");
      r var3 = this.f;
      if (var3 != null) {
         var1 = var3.b;
      } else {
         var1 = "none";
      }

      var2.append(var1);
      var2.append(" protocol=");
      var2.append(this.g);
      var2.append('}');
      return var2.toString();
   }
}
