package e1;

import a1.q;
import android.util.Log;
import i1.f;
import i1.g;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.SelectionKey;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public final class d implements b {
   public final LinkedBlockingQueue a;
   public final LinkedBlockingQueue b;
   public final c c;
   public SelectionKey d;
   public ByteChannel e;
   public n1.a f;
   public boolean g = false;
   public volatile int h = 1;
   public final List i;
   public g1.b j = null;
   public final int k;
   public ByteBuffer l = ByteBuffer.allocate(0);
   public l1.a m = null;
   public String n = null;
   public Integer o = null;
   public Boolean p = null;
   public String q = null;
   public long r = System.nanoTime();
   public final Object s = new Object();

   public d(c var1, g1.b var2) {
      if (var1 != null && (var2 != null || this.k != 2)) {
         this.a = new LinkedBlockingQueue();
         this.b = new LinkedBlockingQueue();
         this.c = var1;
         this.k = 1;
         if (var2 != null) {
            this.j = var2.a();
         }
      } else {
         throw new IllegalArgumentException("parameters must not be null");
      }
   }

   public d(c var1, List var2) {
      this(var1, null);
      this.k = 2;
      if (var2 != null && !var2.isEmpty()) {
         this.i = var2;
      } else {
         ArrayList var3 = new ArrayList();
         this.i = var3;
         var3.add(new g1.b());
      }
   }

   public static ByteBuffer q(int var0) {
      String var1;
      if (var0 != 404) {
         var1 = "500 Internal Server Error";
      } else {
         var1 = "404 WebSocket Upgrade Failure";
      }

      StringBuilder var2 = a.a.s("HTTP/1.1 ", var1, "\r\nContent-Type: text/html\r\nServer: TooTallNate Java-WebSocket\r\nContent-Length: ");
      var2.append(var1.length() + 48);
      var2.append("\r\n\r\n<html><head></head><body><h1>");
      var2.append(var1);
      var2.append("</h1></body></html>");
      String var4 = var2.toString();
      CodingErrorAction var3 = o1.a.a;
      return ByteBuffer.wrap(var4.getBytes(StandardCharsets.US_ASCII));
   }

   @Override
   public final void a(byte[] var1) {
      ByteBuffer var5 = ByteBuffer.wrap(var1);
      if (var5 != null) {
         g1.b var3 = this.j;
         boolean var2 = true;
         if (this.k != 1) {
            var2 = false;
         }

         var3.getClass();
         k1.a var6 = new k1.a(0);
         var6.c = var5;
         var6.d = var2;

         try {
            var6.b();
         } catch (i1.c var4) {
            throw new g(var4);
         }

         this.s(Collections.singletonList(var6));
      } else {
         throw new IllegalArgumentException("Cannot send 'null' data to a WebSocketImpl.");
      }
   }

   @Override
   public final void b(int var1, String var2) {
      this.i(var2, false, var1);
   }

   @Override
   public final void c(String var1) {
      if (var1 != null) {
         g1.b var4 = this.j;
         int var2 = this.k;
         boolean var3 = true;
         if (var2 != 1) {
            var3 = false;
         }

         var4.getClass();
         k1.a var5 = new k1.a(2);
         CodingErrorAction var7 = o1.a.a;
         var5.c = ByteBuffer.wrap(var1.getBytes(StandardCharsets.UTF_8));
         var5.d = var3;

         try {
            var5.b();
         } catch (i1.c var6) {
            throw new g(var6);
         }

         this.s(Collections.singletonList(var5));
      } else {
         throw new IllegalArgumentException("Cannot send 'null' data to a WebSocketImpl.");
      }
   }

   @Override
   public final String d() {
      return this.q;
   }

   @Override
   public final void e(int var1) {
      this.i("", false, var1);
   }

   @Override
   public final void f(String var1) {
      this.k(var1, false, 1006);
   }

   @Override
   public final InetSocketAddress g() {
      return this.c.h(this);
   }

   public final void h(i1.c var1) {
      this.i(var1.getMessage(), false, var1.a);
   }

   public final void i(String param1, boolean param2, int param3) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 000: aload 0
      // 001: monitorenter
      // 002: aload 0
      // 003: getfield e1/d.h I
      // 006: bipush 3
      // 007: if_icmpeq 11d
      // 00a: aload 0
      // 00b: getfield e1/d.h I
      // 00e: bipush 4
      // 00f: if_icmpeq 11d
      // 012: aload 0
      // 013: getfield e1/d.h I
      // 016: istore 5
      // 018: bipush 1
      // 019: istore 4
      // 01b: iload 5
      // 01d: bipush 2
      // 01e: if_icmpne 0e7
      // 021: iload 3
      // 022: sipush 1006
      // 025: if_icmpne 037
      // 028: aload 0
      // 029: bipush 3
      // 02a: putfield e1/d.h I
      // 02d: aload 0
      // 02e: aload 1
      // 02f: bipush 0
      // 030: iload 3
      // 031: invokevirtual e1/d.p (Ljava/lang/String;ZI)V
      // 034: aload 0
      // 035: monitorexit
      // 036: return
      // 037: aload 0
      // 038: getfield e1/d.j Lg1/b;
      // 03b: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 03e: pop
      // 03f: iload 2
      // 040: ifne 059
      // 043: aload 0
      // 044: getfield e1/d.c Le1/c;
      // 047: invokevirtual e1/c.j ()V
      // 04a: goto 059
      // 04d: astore 6
      // 04f: aload 0
      // 050: getfield e1/d.c Le1/c;
      // 053: aload 0
      // 054: aload 6
      // 056: invokevirtual e1/c.l (Le1/b;Ljava/lang/Exception;)V
      // 059: aload 0
      // 05a: getfield e1/d.h I
      // 05d: bipush 2
      // 05e: if_icmpne 064
      // 061: goto 067
      // 064: bipush 0
      // 065: istore 4
      // 067: iload 4
      // 069: ifeq 0ff
      // 06c: new k1/b
      // 06f: astore 7
      // 071: aload 7
      // 073: invokespecial k1/b.<init> ()V
      // 076: aload 1
      // 077: ifnonnull 086
      // 07a: ldc ""
      // 07c: astore 6
      // 07e: goto 089
      // 081: astore 6
      // 083: goto 0c7
      // 086: aload 1
      // 087: astore 6
      // 089: aload 7
      // 08b: aload 6
      // 08d: putfield k1/b.j Ljava/lang/String;
      // 090: aload 7
      // 092: invokevirtual k1/b.d ()V
      // 095: aload 7
      // 097: iload 3
      // 098: putfield k1/b.i I
      // 09b: iload 3
      // 09c: sipush 1015
      // 09f: if_icmpne 0b1
      // 0a2: aload 7
      // 0a4: sipush 1005
      // 0a7: putfield k1/b.i I
      // 0aa: aload 7
      // 0ac: ldc ""
      // 0ae: putfield k1/b.j Ljava/lang/String;
      // 0b1: aload 7
      // 0b3: invokevirtual k1/b.d ()V
      // 0b6: aload 7
      // 0b8: invokevirtual k1/b.b ()V
      // 0bb: aload 0
      // 0bc: aload 7
      // 0be: invokestatic java/util/Collections.singletonList (Ljava/lang/Object;)Ljava/util/List;
      // 0c1: invokevirtual e1/d.s (Ljava/util/List;)V
      // 0c4: goto 0ff
      // 0c7: ldc_w "generated frame is invalid"
      // 0ca: aload 6
      // 0cc: invokestatic a1/q.s (Ljava/lang/String;Ljava/lang/Exception;)V
      // 0cf: aload 0
      // 0d0: getfield e1/d.c Le1/c;
      // 0d3: aload 0
      // 0d4: aload 6
      // 0d6: invokevirtual e1/c.l (Le1/b;Ljava/lang/Exception;)V
      // 0d9: aload 0
      // 0da: ldc_w "generated frame is invalid"
      // 0dd: bipush 0
      // 0de: sipush 1006
      // 0e1: invokevirtual e1/d.p (Ljava/lang/String;ZI)V
      // 0e4: goto 0ff
      // 0e7: iload 3
      // 0e8: bipush -3
      // 0ea: if_icmpne 0f8
      // 0ed: aload 0
      // 0ee: aload 1
      // 0ef: bipush 1
      // 0f0: bipush -3
      // 0f2: invokevirtual e1/d.p (Ljava/lang/String;ZI)V
      // 0f5: goto 110
      // 0f8: iload 3
      // 0f9: sipush 1002
      // 0fc: if_icmpne 109
      // 0ff: aload 0
      // 100: aload 1
      // 101: iload 2
      // 102: iload 3
      // 103: invokevirtual e1/d.p (Ljava/lang/String;ZI)V
      // 106: goto 110
      // 109: aload 0
      // 10a: aload 1
      // 10b: bipush 0
      // 10c: bipush -1
      // 10d: invokevirtual e1/d.p (Ljava/lang/String;ZI)V
      // 110: aload 0
      // 111: bipush 3
      // 112: putfield e1/d.h I
      // 115: aload 0
      // 116: aconst_null
      // 117: putfield e1/d.l Ljava/nio/ByteBuffer;
      // 11a: aload 0
      // 11b: monitorexit
      // 11c: return
      // 11d: aload 0
      // 11e: monitorexit
      // 11f: return
      // 120: astore 1
      // 121: aload 0
      // 122: monitorexit
      // 123: aload 1
      // 124: athrow
   }

   public final void j(int var1) {
      this.k("", true, var1);
   }

   public final void k(String param1, boolean param2, int param3) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: aload 0
      // 01: monitorenter
      // 02: aload 0
      // 03: getfield e1/d.h I
      // 06: istore 4
      // 08: iload 4
      // 0a: bipush 4
      // 0b: if_icmpne 11
      // 0e: aload 0
      // 0f: monitorexit
      // 10: return
      // 11: aload 0
      // 12: getfield e1/d.h I
      // 15: bipush 2
      // 16: if_icmpne 25
      // 19: iload 3
      // 1a: sipush 1006
      // 1d: if_icmpne 25
      // 20: aload 0
      // 21: bipush 3
      // 22: putfield e1/d.h I
      // 25: aload 0
      // 26: getfield e1/d.d Ljava/nio/channels/SelectionKey;
      // 29: astore 5
      // 2b: aload 5
      // 2d: ifnull 35
      // 30: aload 5
      // 32: invokevirtual java/nio/channels/SelectionKey.cancel ()V
      // 35: aload 0
      // 36: getfield e1/d.e Ljava/nio/channels/ByteChannel;
      // 39: astore 5
      // 3b: aload 5
      // 3d: ifnull 7f
      // 40: aload 5
      // 42: invokeinterface java/nio/channels/Channel.close ()V 1
      // 47: goto 7f
      // 4a: astore 5
      // 4c: aload 5
      // 4e: invokevirtual java/lang/Throwable.getMessage ()Ljava/lang/String;
      // 51: ifnull 6d
      // 54: aload 5
      // 56: invokevirtual java/lang/Throwable.getMessage ()Ljava/lang/String;
      // 59: ldc_w "Broken pipe"
      // 5c: invokevirtual java/lang/String.equals (Ljava/lang/Object;)Z
      // 5f: ifeq 6d
      // 62: ldc_w "Caught IOException: Broken pipe during closeConnection()"
      // 65: aload 5
      // 67: invokestatic a1/q.s (Ljava/lang/String;Ljava/lang/Exception;)V
      // 6a: goto 7f
      // 6d: ldc_w "Exception during channel.close()"
      // 70: aload 5
      // 72: invokestatic a1/q.s (Ljava/lang/String;Ljava/lang/Exception;)V
      // 75: aload 0
      // 76: getfield e1/d.c Le1/c;
      // 79: aload 0
      // 7a: aload 5
      // 7c: invokevirtual e1/c.l (Le1/b;Ljava/lang/Exception;)V
      // 7f: aload 0
      // 80: getfield e1/d.c Le1/c;
      // 83: aload 0
      // 84: iload 3
      // 85: aload 1
      // 86: iload 2
      // 87: invokevirtual e1/c.i (Le1/b;ILjava/lang/String;Z)V
      // 8a: goto 97
      // 8d: astore 1
      // 8e: aload 0
      // 8f: getfield e1/d.c Le1/c;
      // 92: aload 0
      // 93: aload 1
      // 94: invokevirtual e1/c.l (Le1/b;Ljava/lang/Exception;)V
      // 97: aload 0
      // 98: getfield e1/d.j Lg1/b;
      // 9b: astore 1
      // 9c: aload 1
      // 9d: ifnull c0
      // a0: aload 1
      // a1: aconst_null
      // a2: putfield g1/b.j Ljava/nio/ByteBuffer;
      // a5: aload 1
      // a6: getfield g1/b.b Lj1/a;
      // a9: astore 5
      // ab: new j1/a
      // ae: astore 5
      // b0: aload 5
      // b2: invokespecial j1/a.<init> ()V
      // b5: aload 1
      // b6: aload 5
      // b8: putfield g1/b.b Lj1/a;
      // bb: aload 1
      // bc: aconst_null
      // bd: putfield g1/b.f Lm1/a;
      // c0: aload 0
      // c1: aconst_null
      // c2: putfield e1/d.m Ll1/a;
      // c5: aload 0
      // c6: bipush 4
      // c7: putfield e1/d.h I
      // ca: aload 0
      // cb: monitorexit
      // cc: return
      // cd: astore 1
      // ce: aload 0
      // cf: monitorexit
      // d0: aload 1
      // d1: athrow
   }

   public final void l(i1.c var1) {
      ByteBuffer var2 = q(404);
      this.a.add(var2);
      this.c.p(this);
      this.p(var1.getMessage(), false, var1.a);
   }

   public final void m(ByteBuffer param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 000: aload 0
      // 001: getfield e1/d.h I
      // 004: istore 2
      // 005: bipush 1
      // 006: istore 3
      // 007: iload 2
      // 008: bipush 1
      // 009: if_icmpeq 017
      // 00c: aload 0
      // 00d: getfield e1/d.h I
      // 010: bipush 2
      // 011: if_icmpne 3bf
      // 014: goto 3ba
      // 017: aload 0
      // 018: getfield e1/d.l Ljava/nio/ByteBuffer;
      // 01b: invokevirtual java/nio/Buffer.capacity ()I
      // 01e: ifne 027
      // 021: aload 1
      // 022: astore 5
      // 024: goto 077
      // 027: aload 0
      // 028: getfield e1/d.l Ljava/nio/ByteBuffer;
      // 02b: invokevirtual java/nio/Buffer.remaining ()I
      // 02e: aload 1
      // 02f: invokevirtual java/nio/Buffer.remaining ()I
      // 032: if_icmpge 060
      // 035: aload 0
      // 036: getfield e1/d.l Ljava/nio/ByteBuffer;
      // 039: invokevirtual java/nio/Buffer.capacity ()I
      // 03c: istore 2
      // 03d: aload 1
      // 03e: invokevirtual java/nio/Buffer.remaining ()I
      // 041: iload 2
      // 042: iadd
      // 043: invokestatic java/nio/ByteBuffer.allocate (I)Ljava/nio/ByteBuffer;
      // 046: astore 5
      // 048: aload 0
      // 049: getfield e1/d.l Ljava/nio/ByteBuffer;
      // 04c: invokevirtual java/nio/ByteBuffer.flip ()Ljava/nio/Buffer;
      // 04f: pop
      // 050: aload 5
      // 052: aload 0
      // 053: getfield e1/d.l Ljava/nio/ByteBuffer;
      // 056: invokevirtual java/nio/ByteBuffer.put (Ljava/nio/ByteBuffer;)Ljava/nio/ByteBuffer;
      // 059: pop
      // 05a: aload 0
      // 05b: aload 5
      // 05d: putfield e1/d.l Ljava/nio/ByteBuffer;
      // 060: aload 0
      // 061: getfield e1/d.l Ljava/nio/ByteBuffer;
      // 064: aload 1
      // 065: invokevirtual java/nio/ByteBuffer.put (Ljava/nio/ByteBuffer;)Ljava/nio/ByteBuffer;
      // 068: pop
      // 069: aload 0
      // 06a: getfield e1/d.l Ljava/nio/ByteBuffer;
      // 06d: invokevirtual java/nio/ByteBuffer.flip ()Ljava/nio/Buffer;
      // 070: pop
      // 071: aload 0
      // 072: getfield e1/d.l Ljava/nio/ByteBuffer;
      // 075: astore 5
      // 077: aload 5
      // 079: invokevirtual java/nio/ByteBuffer.mark ()Ljava/nio/Buffer;
      // 07c: pop
      // 07d: aload 0
      // 07e: getfield e1/d.k I
      // 081: istore 2
      // 082: aload 0
      // 083: getfield e1/d.c Le1/c;
      // 086: astore 7
      // 088: iload 2
      // 089: bipush 2
      // 08a: if_icmpne 21e
      // 08d: aload 0
      // 08e: getfield e1/d.j Lg1/b;
      // 091: astore 6
      // 093: aload 6
      // 095: ifnonnull 1cc
      // 098: aload 0
      // 099: getfield e1/d.i Ljava/util/List;
      // 09c: invokeinterface java/util/List.iterator ()Ljava/util/Iterator; 1
      // 0a1: astore 8
      // 0a3: aload 8
      // 0a5: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 0aa: ifeq 1a2
      // 0ad: aload 8
      // 0af: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 0b4: checkcast g1/a
      // 0b7: invokevirtual g1/a.a ()Lg1/b;
      // 0ba: astore 6
      // 0bc: aload 6
      // 0be: iload 2
      // 0bf: putfield g1/a.a I
      // 0c2: aload 5
      // 0c4: invokevirtual java/nio/ByteBuffer.reset ()Ljava/nio/Buffer;
      // 0c7: pop
      // 0c8: aload 6
      // 0ca: aload 5
      // 0cc: invokevirtual g1/a.d (Ljava/nio/ByteBuffer;)Ll1/e;
      // 0cf: astore 9
      // 0d1: aload 9
      // 0d3: instanceof l1/a
      // 0d6: ifne 0f6
      // 0d9: ldc_w "e1.d"
      // 0dc: ldc_w "Closing due to wrong handshake"
      // 0df: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 0e2: pop
      // 0e3: new i1/c
      // 0e6: astore 6
      // 0e8: aload 6
      // 0ea: sipush 1002
      // 0ed: ldc_w "wrong http function"
      // 0f0: invokespecial i1/c.<init> (ILjava/lang/String;)V
      // 0f3: goto 199
      // 0f6: aload 9
      // 0f8: checkcast l1/a
      // 0fb: astore 9
      // 0fd: aload 6
      // 0ff: aload 9
      // 101: invokevirtual g1/b.f (Ll1/a;)I
      // 104: bipush 1
      // 105: if_icmpne 0a3
      // 108: aload 0
      // 109: aload 9
      // 10b: checkcast l1/c
      // 10e: getfield l1/c.b Ljava/lang/String;
      // 111: putfield e1/d.q Ljava/lang/String;
      // 114: aload 7
      // 116: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 119: pop
      // 11a: new l1/d
      // 11d: astore 10
      // 11f: aload 10
      // 121: invokespecial l1/d.<init> ()V
      // 124: aload 6
      // 126: aload 9
      // 128: aload 10
      // 12a: invokevirtual g1/b.l (Ll1/a;Ll1/d;)Ll1/f;
      // 12d: pop
      // 12e: aload 0
      // 12f: aload 10
      // 131: invokestatic g1/a.b (Ll1/b;)Ljava/util/List;
      // 134: invokevirtual e1/d.t (Ljava/util/List;)V
      // 137: aload 0
      // 138: aload 6
      // 13a: putfield e1/d.j Lg1/b;
      // 13d: aload 0
      // 13e: aload 9
      // 140: invokevirtual e1/d.r (Ll1/b;)V
      // 143: goto 277
      // 146: astore 6
      // 148: goto 315
      // 14b: astore 9
      // 14d: ldc_w "e1.d"
      // 150: ldc_w "Closing due to internal server error"
      // 153: aload 9
      // 155: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      // 158: pop
      // 159: aload 7
      // 15b: aload 0
      // 15c: aload 9
      // 15e: invokevirtual e1/c.l (Le1/b;Ljava/lang/Exception;)V
      // 161: sipush 500
      // 164: invokestatic e1/d.q (I)Ljava/nio/ByteBuffer;
      // 167: astore 6
      // 169: aload 0
      // 16a: getfield e1/d.a Ljava/util/concurrent/LinkedBlockingQueue;
      // 16d: aload 6
      // 16f: invokeinterface java/util/concurrent/BlockingQueue.add (Ljava/lang/Object;)Z 2
      // 174: pop
      // 175: aload 0
      // 176: getfield e1/d.c Le1/c;
      // 179: aload 0
      // 17a: invokevirtual e1/c.p (Le1/b;)V
      // 17d: aload 0
      // 17e: aload 9
      // 180: invokevirtual java/lang/Throwable.getMessage ()Ljava/lang/String;
      // 183: bipush 0
      // 184: bipush -1
      // 185: invokevirtual e1/d.p (Ljava/lang/String;ZI)V
      // 188: goto 375
      // 18b: astore 6
      // 18d: ldc_w "e1.d"
      // 190: ldc_w "Closing due to wrong handshake. Possible handshake rejection"
      // 193: aload 6
      // 195: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      // 198: pop
      // 199: aload 0
      // 19a: aload 6
      // 19c: invokevirtual e1/d.l (Li1/c;)V
      // 19f: goto 375
      // 1a2: aload 0
      // 1a3: getfield e1/d.j Lg1/b;
      // 1a6: ifnonnull 375
      // 1a9: ldc_w "e1.d"
      // 1ac: ldc_w "Closing due to protocol error: no draft matches"
      // 1af: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 1b2: pop
      // 1b3: new i1/c
      // 1b6: astore 6
      // 1b8: aload 6
      // 1ba: sipush 1002
      // 1bd: ldc_w "no draft matches"
      // 1c0: invokespecial i1/c.<init> (ILjava/lang/String;)V
      // 1c3: aload 0
      // 1c4: aload 6
      // 1c6: invokevirtual e1/d.l (Li1/c;)V
      // 1c9: goto 375
      // 1cc: aload 6
      // 1ce: aload 5
      // 1d0: invokevirtual g1/a.d (Ljava/nio/ByteBuffer;)Ll1/e;
      // 1d3: astore 6
      // 1d5: aload 6
      // 1d7: instanceof l1/a
      // 1da: ifne 1ea
      // 1dd: ldc_w "e1.d"
      // 1e0: ldc_w "Closing due to protocol error: wrong http function"
      // 1e3: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 1e6: pop
      // 1e7: goto 243
      // 1ea: aload 6
      // 1ec: checkcast l1/a
      // 1ef: astore 6
      // 1f1: aload 0
      // 1f2: getfield e1/d.j Lg1/b;
      // 1f5: aload 6
      // 1f7: invokevirtual g1/b.f (Ll1/a;)I
      // 1fa: bipush 1
      // 1fb: if_icmpne 20c
      // 1fe: aload 0
      // 1ff: aload 6
      // 201: invokevirtual e1/d.r (Ll1/b;)V
      // 204: goto 277
      // 207: astore 6
      // 209: goto 300
      // 20c: ldc_w "e1.d"
      // 20f: ldc_w "Closing due to protocol error: the handshake did finally not match"
      // 212: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 215: pop
      // 216: ldc_w "the handshake did finally not match"
      // 219: astore 6
      // 21b: goto 2f3
      // 21e: iload 2
      // 21f: bipush 1
      // 220: if_icmpne 375
      // 223: aload 0
      // 224: getfield e1/d.j Lg1/b;
      // 227: astore 6
      // 229: aload 6
      // 22b: iload 2
      // 22c: putfield g1/a.a I
      // 22f: aload 6
      // 231: aload 5
      // 233: invokevirtual g1/a.d (Ljava/nio/ByteBuffer;)Ll1/e;
      // 236: astore 6
      // 238: aload 6
      // 23a: instanceof l1/f
      // 23d: ifne 251
      // 240: goto 1dd
      // 243: aload 0
      // 244: ldc_w "wrong http function"
      // 247: bipush 0
      // 248: sipush 1002
      // 24b: invokevirtual e1/d.p (Ljava/lang/String;ZI)V
      // 24e: goto 375
      // 251: aload 6
      // 253: checkcast l1/f
      // 256: astore 6
      // 258: aload 0
      // 259: getfield e1/d.j Lg1/b;
      // 25c: aload 0
      // 25d: getfield e1/d.m Ll1/a;
      // 260: aload 6
      // 262: invokevirtual g1/b.e (Ll1/a;Ll1/f;)I
      // 265: istore 2
      // 266: iload 2
      // 267: bipush 1
      // 268: if_icmpne 2c2
      // 26b: aload 7
      // 26d: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 270: pop
      // 271: aload 0
      // 272: aload 6
      // 274: invokevirtual e1/d.r (Ll1/b;)V
      // 277: bipush 1
      // 278: istore 2
      // 279: goto 377
      // 27c: astore 6
      // 27e: ldc_w "e1.d"
      // 281: ldc_w "Closing since client was never connected"
      // 284: aload 6
      // 286: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      // 289: pop
      // 28a: aload 7
      // 28c: aload 0
      // 28d: aload 6
      // 28f: invokevirtual e1/c.l (Le1/b;Ljava/lang/Exception;)V
      // 292: aload 0
      // 293: aload 6
      // 295: invokevirtual java/lang/Throwable.getMessage ()Ljava/lang/String;
      // 298: bipush 0
      // 299: bipush -1
      // 29a: invokevirtual e1/d.p (Ljava/lang/String;ZI)V
      // 29d: goto 375
      // 2a0: astore 6
      // 2a2: ldc_w "e1.d"
      // 2a5: ldc_w "Closing due to invalid data exception. Possible handshake rejection"
      // 2a8: aload 6
      // 2aa: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      // 2ad: pop
      // 2ae: aload 6
      // 2b0: getfield i1/c.a I
      // 2b3: istore 2
      // 2b4: aload 0
      // 2b5: aload 6
      // 2b7: invokevirtual java/lang/Throwable.getMessage ()Ljava/lang/String;
      // 2ba: bipush 0
      // 2bb: iload 2
      // 2bc: invokevirtual e1/d.p (Ljava/lang/String;ZI)V
      // 2bf: goto 375
      // 2c2: ldc_w "e1.d"
      // 2c5: ldc_w "Closing due to protocol error: draft {} refuses handshake"
      // 2c8: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 2cb: pop
      // 2cc: new java/lang/StringBuilder
      // 2cf: astore 6
      // 2d1: aload 6
      // 2d3: ldc_w "draft "
      // 2d6: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 2d9: aload 6
      // 2db: aload 0
      // 2dc: getfield e1/d.j Lg1/b;
      // 2df: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      // 2e2: pop
      // 2e3: aload 6
      // 2e5: ldc_w " refuses handshake"
      // 2e8: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 2eb: pop
      // 2ec: aload 6
      // 2ee: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 2f1: astore 6
      // 2f3: aload 0
      // 2f4: aload 6
      // 2f6: bipush 0
      // 2f7: sipush 1002
      // 2fa: invokevirtual e1/d.i (Ljava/lang/String;ZI)V
      // 2fd: goto 375
      // 300: ldc_w "e1.d"
      // 303: ldc_w "Closing due to invalid handshake"
      // 306: aload 6
      // 308: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      // 30b: pop
      // 30c: aload 0
      // 30d: aload 6
      // 30f: invokevirtual e1/d.h (Li1/c;)V
      // 312: goto 375
      // 315: aload 0
      // 316: getfield e1/d.l Ljava/nio/ByteBuffer;
      // 319: invokevirtual java/nio/Buffer.capacity ()I
      // 31c: ifne 353
      // 31f: aload 5
      // 321: invokevirtual java/nio/ByteBuffer.reset ()Ljava/nio/Buffer;
      // 324: pop
      // 325: aload 6
      // 327: getfield i1/b.a I
      // 32a: istore 4
      // 32c: iload 4
      // 32e: istore 2
      // 32f: iload 4
      // 331: ifne 33d
      // 334: aload 5
      // 336: invokevirtual java/nio/Buffer.capacity ()I
      // 339: bipush 16
      // 33b: iadd
      // 33c: istore 2
      // 33d: iload 2
      // 33e: invokestatic java/nio/ByteBuffer.allocate (I)Ljava/nio/ByteBuffer;
      // 341: astore 5
      // 343: aload 0
      // 344: aload 5
      // 346: putfield e1/d.l Ljava/nio/ByteBuffer;
      // 349: aload 5
      // 34b: aload 1
      // 34c: invokevirtual java/nio/ByteBuffer.put (Ljava/nio/ByteBuffer;)Ljava/nio/ByteBuffer;
      // 34f: pop
      // 350: goto 375
      // 353: aload 0
      // 354: getfield e1/d.l Ljava/nio/ByteBuffer;
      // 357: astore 5
      // 359: aload 5
      // 35b: aload 5
      // 35d: invokevirtual java/nio/Buffer.limit ()I
      // 360: invokevirtual java/nio/ByteBuffer.position (I)Ljava/nio/Buffer;
      // 363: pop
      // 364: aload 0
      // 365: getfield e1/d.l Ljava/nio/ByteBuffer;
      // 368: astore 5
      // 36a: aload 5
      // 36c: aload 5
      // 36e: invokevirtual java/nio/Buffer.capacity ()I
      // 371: invokevirtual java/nio/ByteBuffer.limit (I)Ljava/nio/Buffer;
      // 374: pop
      // 375: bipush 0
      // 376: istore 2
      // 377: iload 2
      // 378: ifeq 3bf
      // 37b: aload 0
      // 37c: getfield e1/d.h I
      // 37f: bipush 3
      // 380: if_icmpne 388
      // 383: bipush 1
      // 384: istore 2
      // 385: goto 38a
      // 388: bipush 0
      // 389: istore 2
      // 38a: iload 2
      // 38b: ifne 3bf
      // 38e: aload 0
      // 38f: getfield e1/d.h I
      // 392: bipush 4
      // 393: if_icmpne 39b
      // 396: iload 3
      // 397: istore 2
      // 398: goto 39d
      // 39b: bipush 0
      // 39c: istore 2
      // 39d: iload 2
      // 39e: ifne 3bf
      // 3a1: aload 1
      // 3a2: invokevirtual java/nio/Buffer.hasRemaining ()Z
      // 3a5: ifeq 3ab
      // 3a8: goto 3ba
      // 3ab: aload 0
      // 3ac: getfield e1/d.l Ljava/nio/ByteBuffer;
      // 3af: invokevirtual java/nio/Buffer.hasRemaining ()Z
      // 3b2: ifeq 3bf
      // 3b5: aload 0
      // 3b6: getfield e1/d.l Ljava/nio/ByteBuffer;
      // 3b9: astore 1
      // 3ba: aload 0
      // 3bb: aload 1
      // 3bc: invokevirtual e1/d.n (Ljava/nio/ByteBuffer;)V
      // 3bf: return
      // 3c0: astore 6
      // 3c2: goto 0a3
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final void n(ByteBuffer var1) {
      c var3 = this.c;

      label84: {
         Object var21;
         label71: {
            label70: {
               i1.c var26;
               label69: {
                  label68: {
                     label67: {
                        label85: {
                           label77: {
                              label64: {
                                 Iterator var4;
                                 try {
                                    var4 = this.j.n(var1).iterator();
                                 } catch (f var11) {
                                    var26 = var11;
                                    boolean var32 = false;
                                    break label64;
                                 } catch (i1.c var12) {
                                    var26 = var12;
                                    boolean var31 = false;
                                    break label69;
                                 } catch (VirtualMachineError var13) {
                                    var25 = var13;
                                    boolean var30 = false;
                                    break label68;
                                 } catch (ThreadDeath var14) {
                                    var24 = var14;
                                    boolean var29 = false;
                                    break label67;
                                 } catch (LinkageError var15) {
                                    var23 = var15;
                                    boolean var28 = false;
                                    break label85;
                                 } catch (Error var16) {
                                    var10000 = var16;
                                    boolean var10001 = false;
                                    break label77;
                                 }

                                 while (true) {
                                    try {
                                       if (!var4.hasNext()) {
                                          return;
                                       }

                                       var21 = (k1.d)var4.next();
                                       StringBuilder var20 = new StringBuilder();
                                       var20.append("matched frame: ");
                                       var20.append(var21);
                                       Log.d("e1.d", var20.toString());
                                       this.j.m(this, (k1.d)var21);
                                    } catch (f var5) {
                                       var26 = var5;
                                       boolean var38 = false;
                                       break;
                                    } catch (i1.c var6) {
                                       var26 = var6;
                                       boolean var37 = false;
                                       break label69;
                                    } catch (VirtualMachineError var7) {
                                       var25 = var7;
                                       boolean var36 = false;
                                       break label68;
                                    } catch (ThreadDeath var8) {
                                       var24 = var8;
                                       boolean var35 = false;
                                       break label67;
                                    } catch (LinkageError var9) {
                                       var23 = var9;
                                       boolean var34 = false;
                                       break label85;
                                    } catch (Error var10) {
                                       var10000 = var10;
                                       boolean var33 = false;
                                       break label77;
                                    }
                                 }
                              }

                              var19 = var26;
                              var21 = var19;
                              if (((f)var19).b != Integer.MAX_VALUE) {
                                 break label71;
                              }

                              var21 = "Closing due to invalid size of frame";
                              break label70;
                           }

                           Error var17 = var10000;
                           a1.q.t("Closing web socket due to an error during frame processing", var17);
                           var3.l(this, new Exception(var17));
                           this.i("Got error ".concat(var17.getClass().getName()), false, 1011);
                           return;
                        }

                        var18 = var23;
                        break label84;
                     }

                     var18 = var24;
                     break label84;
                  }

                  var18 = var25;
                  break label84;
               }

               var19 = var26;
               var21 = "Closing due to invalid data in frame";
            }

            a1.q.s((String)var21, (Exception)var19);
            var3.l(this, (Exception)var19);
            var21 = var19;
         }

         this.h((i1.c)var21);
         return;
      }

      a1.q.t("Got fatal error during frame processing", (Throwable)var18);
      throw var18;
   }

   public final void o() {
      short var1;
      if (this.h == 1) {
         var1 = -1;
      } else {
         if (this.g) {
            var1 = this.o;
            this.k(this.n, this.p, var1);
            return;
         }

         this.j.getClass();
         var1 = 1006;
      }

      this.j(var1);
   }

   public final void p(String param1, boolean param2, int param3) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: aload 0
      // 01: monitorenter
      // 02: aload 0
      // 03: getfield e1/d.g Z
      // 06: istore 4
      // 08: iload 4
      // 0a: ifeq 10
      // 0d: aload 0
      // 0e: monitorexit
      // 0f: return
      // 10: aload 0
      // 11: iload 3
      // 12: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 15: putfield e1/d.o Ljava/lang/Integer;
      // 18: aload 0
      // 19: aload 1
      // 1a: putfield e1/d.n Ljava/lang/String;
      // 1d: aload 0
      // 1e: iload 2
      // 1f: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 22: putfield e1/d.p Ljava/lang/Boolean;
      // 25: aload 0
      // 26: bipush 1
      // 27: putfield e1/d.g Z
      // 2a: aload 0
      // 2b: getfield e1/d.c Le1/c;
      // 2e: aload 0
      // 2f: invokevirtual e1/c.p (Le1/b;)V
      // 32: aload 0
      // 33: getfield e1/d.c Le1/c;
      // 36: invokevirtual e1/c.k ()V
      // 39: goto 4d
      // 3c: astore 1
      // 3d: ldc_w "Exception in onWebsocketClosing"
      // 40: aload 1
      // 41: invokestatic a1/q.s (Ljava/lang/String;Ljava/lang/Exception;)V
      // 44: aload 0
      // 45: getfield e1/d.c Le1/c;
      // 48: aload 0
      // 49: aload 1
      // 4a: invokevirtual e1/c.l (Le1/b;Ljava/lang/Exception;)V
      // 4d: aload 0
      // 4e: getfield e1/d.j Lg1/b;
      // 51: astore 1
      // 52: aload 1
      // 53: ifnull 76
      // 56: aload 1
      // 57: aconst_null
      // 58: putfield g1/b.j Ljava/nio/ByteBuffer;
      // 5b: aload 1
      // 5c: getfield g1/b.b Lj1/a;
      // 5f: astore 5
      // 61: new j1/a
      // 64: astore 5
      // 66: aload 5
      // 68: invokespecial j1/a.<init> ()V
      // 6b: aload 1
      // 6c: aload 5
      // 6e: putfield g1/b.b Lj1/a;
      // 71: aload 1
      // 72: aconst_null
      // 73: putfield g1/b.f Lm1/a;
      // 76: aload 0
      // 77: aconst_null
      // 78: putfield e1/d.m Ll1/a;
      // 7b: aload 0
      // 7c: monitorexit
      // 7d: return
      // 7e: astore 1
      // 7f: aload 0
      // 80: monitorexit
      // 81: aload 1
      // 82: athrow
   }

   public final void r(l1.b var1) {
      StringBuilder var2 = new StringBuilder("open using draft: ");
      var2.append(this.j);
      Log.d("e1.d", var2.toString());
      this.h = 2;
      this.r = System.nanoTime();

      try {
         this.c.o(this, var1);
      } catch (RuntimeException var3) {
         this.c.l(this, var3);
      }
   }

   public final void s(List var1) {
      int var4 = this.h;
      boolean var20;
      if (var4 == 2) {
         var20 = 1;
      } else {
         var20 = 0;
      }

      if (!var20) {
         throw new g();
      } else if (var1 == null) {
         throw new IllegalArgumentException();
      } else {
         ArrayList var12 = new ArrayList();

         for (k1.d var16 : var1) {
            StringBuilder var13 = new StringBuilder("send frame:");
            var13.append(var16.toString());
            Log.d("e1.d", var13.toString());
            g1.b var14 = this.j;
            var14.b.getClass();
            ByteBuffer var15 = var16.a();
            boolean var7;
            if (var14.a == 1) {
               var7 = true;
            } else {
               var7 = false;
            }

            byte var5;
            if (var15.remaining() <= 125) {
               var5 = 1;
            } else if (var15.remaining() <= 65535) {
               var5 = 2;
            } else {
               var5 = 8;
            }

            if (var5 > 1) {
               var20 = var5 + 1;
            } else {
               var20 = var5;
            }

            byte var6;
            if (var7) {
               var6 = 4;
            } else {
               var6 = 0;
            }

            ByteBuffer var29 = ByteBuffer.allocate(var15.remaining() + var20 + 1 + var6);
            var20 = var16.b;
            byte var23;
            if (var20 == 1) {
               var23 = 0;
            } else if (var20 == 2) {
               var23 = 1;
            } else if (var20 == 3) {
               var23 = 2;
            } else if (var20 == 6) {
               var23 = 8;
            } else if (var20 == 4) {
               var23 = 9;
            } else {
               if (var20 != 5) {
                  throw new IllegalArgumentException("Don't know how to handle ".concat(a.a.D(var20)));
               }

               var23 = 10;
            }

            boolean var9 = var16.a;
            var6 = -128;
            byte var8;
            if (var9) {
               var8 = -128;
            } else {
               var8 = 0;
            }

            byte var2 = (byte)(var23 | (byte)var8);
            byte var3 = var2;
            if (var16.e) {
               var3 = (byte)(var2 | g1.b.k(1));
            }

            var2 = var3;
            if (var16.f) {
               var2 = (byte)(var3 | g1.b.k(2));
            }

            var3 = var2;
            if (var16.g) {
               var3 = (byte)(g1.b.k(3) | var2);
            }

            var29.put(var3);
            long var10 = (long)var15.remaining();
            byte[] var30 = new byte[var5];

            for (int var24 = 0; var24 < var5; var24++) {
               var30[var24] = (byte)((int)(var10 >>> var5 * 8 - 8 - var24 * 8));
            }

            if (var5 == 1) {
               var23 = var30[0];
               if (!var7) {
                  var6 = 0;
               }

               var29.put((byte)(var23 | var6));
            } else {
               if (var5 == 2) {
                  if (!var7) {
                     var6 = 0;
                  }

                  var23 = var6 | 126;
               } else {
                  if (var5 != 8) {
                     throw new IllegalStateException("Size representation not supported/specified");
                  }

                  if (!var7) {
                     var6 = 0;
                  }

                  var23 = var6 | 127;
               }

               var29.put((byte)var23);
               var29.put(var30);
            }

            if (var7) {
               ByteBuffer var31 = ByteBuffer.allocate(4);
               var31.putInt(var14.k.nextInt());
               var29.put(var31.array());

               for (int var27 = 0; var15.hasRemaining(); var27++) {
                  var29.put((byte)(var15.get() ^ var31.get(var27 % 4)));
               }
            } else {
               var29.put(var15);
               ((Buffer)var15).flip();
            }

            ((Buffer)var29).flip();
            var12.add(var29);
         }

         this.t(var12);
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final void t(List var1) {
      Object var2 = this.s;
      synchronized (var2){} // $VF: monitorenter 

      Throwable var10000;
      label146: {
         Iterator var3;
         try {
            var3 = var1.iterator();
         } catch (Throwable var22) {
            var10000 = var22;
            boolean var10001 = false;
            break label146;
         }

         while (true) {
            try {
               if (var3.hasNext()) {
                  ByteBuffer var25 = (ByteBuffer)var3.next();
                  this.a.add(var25);
                  this.c.p(this);
                  continue;
               }
            } catch (Throwable var23) {
               var10000 = var23;
               boolean var26 = false;
               break;
            }

            try {
               // $VF: monitorexit
               return;
            } catch (Throwable var21) {
               var10000 = var21;
               boolean var27 = false;
               break;
            }
         }
      }

      while (true) {
         Throwable var24 = var10000;

         try {
            // $VF: monitorexit
            throw var24;
         } catch (Throwable var20) {
            var10000 = var20;
            boolean var28 = false;
            continue;
         }
      }
   }

   @Override
   public final String toString() {
      return super.toString();
   }
}
