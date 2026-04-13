package b1;

import android.util.Log;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import java.util.Objects;
import javax.net.ssl.SSLContext;

public final class p implements Closeable {
   public final String a;
   public final int b;
   public final byte[] c;
   public final o d;
   public final SSLContext e;
   public DataInputStream f;
   public DataOutputStream g;
   public m h;
   public int i = 1;

   public p(String var1, int var2, byte[] var3, k var4) {
      Objects.requireNonNull(var1);
      this.a = var1;
      this.b = var2;
      Objects.requireNonNull(var3);
      this.c = var3;
      this.d = new o((byte)0, b1.i.c((RSAPublicKey)var4.b.getPublicKey(), "com.guard.wallet"));
      this.e = a1.q.y(var4);
   }

   public static boolean x(byte var0, byte var1) {
      if (var0 != var1) {
         StringBuilder var2 = new StringBuilder("Unexpected header type (expected=");
         var2.append((int)var0);
         var2.append(" actual=");
         var2.append((int)var1);
         var2.append(")");
         Log.e("p", var2.toString());
         return false;
      } else {
         return true;
      }
   }

   @Override
   public final void close() {
      Arrays.fill(this.c, (byte)0);

      try {
         this.f.close();
      } catch (IOException var3) {
      }

      try {
         this.g.close();
      } catch (IOException var2) {
      }

      if (this.i != 1) {
         this.h.destroy();
      }
   }

   public final n y() {
      String var7;
      label36: {
         byte[] var4 = new byte[6];
         this.f.readFully(var4);
         ByteBuffer var5 = ByteBuffer.wrap(var4).order(ByteOrder.BIG_ENDIAN);
         byte var1 = var5.get();
         byte var2 = var5.get();
         int var3 = var5.getInt();
         StringBuilder var6;
         if (var1 >= 1 && var1 <= 1) {
            if (var2 == 0 || var2 == 1) {
               if (var3 > 0 && var3 <= 16384) {
                  return new n(var1, var2, var3);
               }

               var7 = a.a.h("Header payload not within a safe payload size (size=", var3, ")");
               break label36;
            }

            var6 = new StringBuilder("Unknown PairingPacket type ");
            var6.append((int)var2);
         } else {
            var6 = a.a.q("PairingPacketHeader version mismatch (us=1 them=", var1, ")");
         }

         var7 = var6.toString();
      }

      Log.e("p", var7);
      return null;
   }

   public final void z() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 000: aload 0
      // 001: getfield b1/p.i I
      // 004: istore 1
      // 005: bipush 1
      // 006: istore 2
      // 007: iload 1
      // 008: bipush 1
      // 009: if_icmpne 46f
      // 00c: aload 0
      // 00d: bipush 2
      // 00e: putfield b1/p.i I
      // 011: aload 0
      // 012: getfield b1/p.a Ljava/lang/String;
      // 015: astore 5
      // 017: aload 0
      // 018: getfield b1/p.b I
      // 01b: istore 1
      // 01c: new java/net/Socket
      // 01f: dup
      // 020: aload 5
      // 022: iload 1
      // 023: invokespecial java/net/Socket.<init> (Ljava/lang/String;I)V
      // 026: astore 6
      // 028: aload 6
      // 02a: bipush 1
      // 02b: invokevirtual java/net/Socket.setTcpNoDelay (Z)V
      // 02e: aload 0
      // 02f: getfield b1/p.e Ljavax/net/ssl/SSLContext;
      // 032: invokevirtual javax/net/ssl/SSLContext.getSocketFactory ()Ljavax/net/ssl/SSLSocketFactory;
      // 035: aload 6
      // 037: aload 5
      // 039: iload 1
      // 03a: bipush 1
      // 03b: invokevirtual javax/net/ssl/SSLSocketFactory.createSocket (Ljava/net/Socket;Ljava/lang/String;IZ)Ljava/net/Socket;
      // 03e: checkcast javax/net/ssl/SSLSocket
      // 041: astore 6
      // 043: aload 6
      // 045: invokevirtual javax/net/ssl/SSLSocket.startHandshake ()V
      // 048: ldc "p"
      // 04a: ldc "Handshake succeeded."
      // 04c: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 04f: pop
      // 050: aload 0
      // 051: new java/io/DataInputStream
      // 054: dup
      // 055: aload 6
      // 057: invokevirtual java/net/Socket.getInputStream ()Ljava/io/InputStream;
      // 05a: invokespecial java/io/DataInputStream.<init> (Ljava/io/InputStream;)V
      // 05d: putfield b1/p.f Ljava/io/DataInputStream;
      // 060: aload 0
      // 061: new java/io/DataOutputStream
      // 064: dup
      // 065: aload 6
      // 067: invokevirtual java/net/Socket.getOutputStream ()Ljava/io/OutputStream;
      // 06a: invokespecial java/io/DataOutputStream.<init> (Ljava/io/OutputStream;)V
      // 06d: putfield b1/p.g Ljava/io/DataOutputStream;
      // 070: getstatic a1/q.i Z
      // 073: ifeq 081
      // 076: ldc org/conscrypt/Conscrypt
      // 078: astore 5
      // 07a: getstatic org/conscrypt/Conscrypt.a I
      // 07d: istore 1
      // 07e: goto 091
      // 081: getstatic android/os/Build$VERSION.SDK_INT I
      // 084: bipush 29
      // 086: if_icmplt 44e
      // 089: ldc_w "com.android.org.conscrypt.Conscrypt"
      // 08c: invokestatic java/lang/Class.forName (Ljava/lang/String;)Ljava/lang/Class;
      // 08f: astore 5
      // 091: aload 5
      // 093: ldc_w "exportKeyingMaterial"
      // 096: bipush 4
      // 097: anewarray 258
      // 09a: dup
      // 09b: bipush 0
      // 09c: ldc javax/net/ssl/SSLSocket
      // 09e: aastore
      // 09f: dup
      // 0a0: bipush 1
      // 0a1: ldc_w java/lang/String
      // 0a4: aastore
      // 0a5: dup
      // 0a6: bipush 2
      // 0a7: ldc_w [B
      // 0aa: aastore
      // 0ab: dup
      // 0ac: bipush 3
      // 0ad: getstatic java/lang/Integer.TYPE Ljava/lang/Class;
      // 0b0: aastore
      // 0b1: invokevirtual java/lang/Class.getMethod (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
      // 0b4: aconst_null
      // 0b5: bipush 4
      // 0b6: anewarray 4
      // 0b9: dup
      // 0ba: bipush 0
      // 0bb: aload 6
      // 0bd: aastore
      // 0be: dup
      // 0bf: bipush 1
      // 0c0: ldc_w "adb-label\u0000"
      // 0c3: aastore
      // 0c4: dup
      // 0c5: bipush 2
      // 0c6: aconst_null
      // 0c7: aastore
      // 0c8: dup
      // 0c9: bipush 3
      // 0ca: bipush 64
      // 0cc: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 0cf: aastore
      // 0d0: invokevirtual java/lang/reflect/Method.invoke (Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
      // 0d3: checkcast [B
      // 0d6: astore 5
      // 0d8: aload 0
      // 0d9: getfield b1/p.c [B
      // 0dc: astore 7
      // 0de: aload 7
      // 0e0: arraylength
      // 0e1: aload 5
      // 0e3: arraylength
      // 0e4: iadd
      // 0e5: newarray 8
      // 0e7: astore 6
      // 0e9: aload 7
      // 0eb: bipush 0
      // 0ec: aload 6
      // 0ee: bipush 0
      // 0ef: aload 7
      // 0f1: arraylength
      // 0f2: invokestatic java/lang/System.arraycopy (Ljava/lang/Object;ILjava/lang/Object;II)V
      // 0f5: aload 5
      // 0f7: bipush 0
      // 0f8: aload 6
      // 0fa: aload 7
      // 0fc: arraylength
      // 0fd: aload 5
      // 0ff: arraylength
      // 100: invokestatic java/lang/System.arraycopy (Ljava/lang/Object;ILjava/lang/Object;II)V
      // 103: getstatic b1/m.g [B
      // 106: astore 5
      // 108: new io/github/muntashirakon/crypto/spake2/Spake2Context
      // 10b: dup
      // 10c: getstatic b1/m.g [B
      // 10f: getstatic b1/m.h [B
      // 112: invokespecial io/github/muntashirakon/crypto/spake2/Spake2Context.<init> ([B[B)V
      // 115: astore 7
      // 117: new b1/m
      // 11a: astore 5
      // 11c: aload 5
      // 11e: aload 7
      // 120: aload 6
      // 122: invokespecial b1/m.<init> (Lio/github/muntashirakon/crypto/spake2/Spake2Context;[B)V
      // 125: goto 12d
      // 128: astore 5
      // 12a: aconst_null
      // 12b: astore 5
      // 12d: aload 5
      // 12f: ifnull 443
      // 132: aload 0
      // 133: aload 5
      // 135: putfield b1/p.h Lb1/m;
      // 138: aload 0
      // 139: getfield b1/p.i I
      // 13c: invokestatic r/a.a (I)I
      // 13f: istore 1
      // 140: iload 1
      // 141: ifeq 438
      // 144: iload 1
      // 145: bipush 1
      // 146: if_icmpeq 333
      // 149: iload 1
      // 14a: bipush 2
      // 14b: if_icmpeq 156
      // 14e: iload 1
      // 14f: bipush 3
      // 150: if_icmpeq 438
      // 153: goto 425
      // 156: sipush 8192
      // 159: invokestatic java/nio/ByteBuffer.allocate (I)Ljava/nio/ByteBuffer;
      // 15c: getstatic java/nio/ByteOrder.BIG_ENDIAN Ljava/nio/ByteOrder;
      // 15f: invokevirtual java/nio/ByteBuffer.order (Ljava/nio/ByteOrder;)Ljava/nio/ByteBuffer;
      // 162: astore 5
      // 164: aload 0
      // 165: getfield b1/p.d Lb1/o;
      // 168: astore 6
      // 16a: aload 5
      // 16c: aload 6
      // 16e: getfield b1/o.a B
      // 171: invokevirtual java/nio/ByteBuffer.put (B)Ljava/nio/ByteBuffer;
      // 174: aload 6
      // 176: getfield b1/o.b [B
      // 179: invokevirtual java/nio/ByteBuffer.put ([B)Ljava/nio/ByteBuffer;
      // 17c: pop
      // 17d: aload 0
      // 17e: getfield b1/p.h Lb1/m;
      // 181: astore 6
      // 183: aload 5
      // 185: invokevirtual java/nio/ByteBuffer.array ()[B
      // 188: astore 7
      // 18a: aload 6
      // 18c: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 18f: pop
      // 190: bipush 12
      // 192: invokestatic java/nio/ByteBuffer.allocate (I)Ljava/nio/ByteBuffer;
      // 195: astore 8
      // 197: getstatic java/nio/ByteOrder.LITTLE_ENDIAN Ljava/nio/ByteOrder;
      // 19a: astore 5
      // 19c: aload 8
      // 19e: aload 5
      // 1a0: invokevirtual java/nio/ByteBuffer.order (Ljava/nio/ByteOrder;)Ljava/nio/ByteBuffer;
      // 1a3: astore 8
      // 1a5: aload 6
      // 1a7: getfield b1/m.e J
      // 1aa: lstore 3
      // 1ab: aload 6
      // 1ad: lload 3
      // 1ae: lconst_1
      // 1af: ladd
      // 1b0: putfield b1/m.e J
      // 1b3: aload 6
      // 1b5: aload 7
      // 1b7: aload 8
      // 1b9: lload 3
      // 1ba: invokevirtual java/nio/ByteBuffer.putLong (J)Ljava/nio/ByteBuffer;
      // 1bd: invokevirtual java/nio/ByteBuffer.array ()[B
      // 1c0: bipush 1
      // 1c1: invokevirtual b1/m.a ([B[BZ)[B
      // 1c4: astore 7
      // 1c6: aload 7
      // 1c8: ifnonnull 1d3
      // 1cb: ldc_w "Failed to encrypt peer info"
      // 1ce: astore 5
      // 1d0: goto 298
      // 1d3: aload 7
      // 1d5: arraylength
      // 1d6: istore 1
      // 1d7: bipush 6
      // 1d9: invokestatic java/nio/ByteBuffer.allocate (I)Ljava/nio/ByteBuffer;
      // 1dc: getstatic java/nio/ByteOrder.BIG_ENDIAN Ljava/nio/ByteOrder;
      // 1df: invokevirtual java/nio/ByteBuffer.order (Ljava/nio/ByteOrder;)Ljava/nio/ByteBuffer;
      // 1e2: astore 6
      // 1e4: aload 6
      // 1e6: bipush 1
      // 1e7: invokevirtual java/nio/ByteBuffer.put (B)Ljava/nio/ByteBuffer;
      // 1ea: bipush 1
      // 1eb: invokevirtual java/nio/ByteBuffer.put (B)Ljava/nio/ByteBuffer;
      // 1ee: iload 1
      // 1ef: invokevirtual java/nio/ByteBuffer.putInt (I)Ljava/nio/ByteBuffer;
      // 1f2: pop
      // 1f3: aload 0
      // 1f4: getfield b1/p.g Ljava/io/DataOutputStream;
      // 1f7: aload 6
      // 1f9: invokevirtual java/nio/ByteBuffer.array ()[B
      // 1fc: invokevirtual java/io/OutputStream.write ([B)V
      // 1ff: aload 0
      // 200: getfield b1/p.g Ljava/io/DataOutputStream;
      // 203: aload 7
      // 205: invokevirtual java/io/OutputStream.write ([B)V
      // 208: aload 0
      // 209: invokevirtual b1/p.y ()Lb1/n;
      // 20c: astore 6
      // 20e: aload 6
      // 210: ifnull 317
      // 213: bipush 1
      // 214: aload 6
      // 216: getfield b1/n.b B
      // 219: invokestatic b1/p.x (BB)Z
      // 21c: ifne 222
      // 21f: goto 317
      // 222: aload 6
      // 224: getfield b1/n.c I
      // 227: newarray 8
      // 229: astore 7
      // 22b: aload 0
      // 22c: getfield b1/p.f Ljava/io/DataInputStream;
      // 22f: aload 7
      // 231: invokevirtual java/io/DataInputStream.readFully ([B)V
      // 234: aload 0
      // 235: getfield b1/p.h Lb1/m;
      // 238: astore 6
      // 23a: aload 6
      // 23c: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 23f: pop
      // 240: bipush 12
      // 242: invokestatic java/nio/ByteBuffer.allocate (I)Ljava/nio/ByteBuffer;
      // 245: aload 5
      // 247: invokevirtual java/nio/ByteBuffer.order (Ljava/nio/ByteOrder;)Ljava/nio/ByteBuffer;
      // 24a: astore 5
      // 24c: aload 6
      // 24e: getfield b1/m.d J
      // 251: lstore 3
      // 252: aload 6
      // 254: lconst_1
      // 255: lload 3
      // 256: ladd
      // 257: putfield b1/m.d J
      // 25a: aload 6
      // 25c: aload 7
      // 25e: aload 5
      // 260: lload 3
      // 261: invokevirtual java/nio/ByteBuffer.putLong (J)Ljava/nio/ByteBuffer;
      // 264: invokevirtual java/nio/ByteBuffer.array ()[B
      // 267: bipush 0
      // 268: invokevirtual b1/m.a ([B[BZ)[B
      // 26b: astore 5
      // 26d: aload 5
      // 26f: ifnonnull 27a
      // 272: ldc_w "Unsupported payload while decrypting peer info."
      // 275: astore 5
      // 277: goto 298
      // 27a: aload 5
      // 27c: arraylength
      // 27d: sipush 8192
      // 280: if_icmpeq 2a3
      // 283: new java/lang/StringBuilder
      // 286: dup
      // 287: ldc_w "Got size="
      // 28a: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 28d: aload 5
      // 28f: arraylength
      // 290: ldc_w " PeerInfo.size=8192"
      // 293: invokestatic a/a.m (Ljava/lang/StringBuilder;ILjava/lang/String;)Ljava/lang/String;
      // 296: astore 5
      // 298: ldc "p"
      // 29a: aload 5
      // 29c: invokestatic android/util/Log.e (Ljava/lang/String;Ljava/lang/String;)I
      // 29f: pop
      // 2a0: goto 317
      // 2a3: aload 5
      // 2a5: invokestatic java/nio/ByteBuffer.wrap ([B)Ljava/nio/ByteBuffer;
      // 2a8: astore 5
      // 2aa: aload 5
      // 2ac: invokevirtual java/nio/ByteBuffer.get ()B
      // 2af: istore 1
      // 2b0: sipush 8191
      // 2b3: newarray 8
      // 2b5: astore 6
      // 2b7: aload 5
      // 2b9: aload 6
      // 2bb: invokevirtual java/nio/ByteBuffer.get ([B)Ljava/nio/ByteBuffer;
      // 2be: pop
      // 2bf: sipush 8191
      // 2c2: newarray 8
      // 2c4: astore 5
      // 2c6: aload 6
      // 2c8: bipush 0
      // 2c9: aload 5
      // 2cb: bipush 0
      // 2cc: sipush 8191
      // 2cf: sipush 8191
      // 2d2: invokestatic java/lang/Math.min (II)I
      // 2d5: invokestatic java/lang/System.arraycopy (Ljava/lang/Object;ILjava/lang/Object;II)V
      // 2d8: new java/lang/StringBuilder
      // 2db: dup
      // 2dc: ldc_w "PeerInfo{type="
      // 2df: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 2e2: astore 6
      // 2e4: aload 6
      // 2e6: iload 1
      // 2e7: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 2ea: pop
      // 2eb: aload 6
      // 2ed: ldc_w ", data="
      // 2f0: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 2f3: pop
      // 2f4: aload 6
      // 2f6: aload 5
      // 2f8: invokestatic java/util/Arrays.toString ([B)Ljava/lang/String;
      // 2fb: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 2fe: pop
      // 2ff: aload 6
      // 301: bipush 125
      // 303: invokevirtual java/lang/StringBuilder.append (C)Ljava/lang/StringBuilder;
      // 306: pop
      // 307: ldc "p"
      // 309: aload 6
      // 30b: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 30e: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 311: pop
      // 312: iload 2
      // 313: istore 1
      // 314: goto 319
      // 317: bipush 0
      // 318: istore 1
      // 319: iload 1
      // 31a: ifeq 323
      // 31d: aload 0
      // 31e: bipush 4
      // 31f: putfield b1/p.i I
      // 322: return
      // 323: aload 0
      // 324: bipush 4
      // 325: putfield b1/p.i I
      // 328: new java/io/IOException
      // 32b: dup
      // 32c: ldc_w "Could not exchange peer info."
      // 32f: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
      // 332: athrow
      // 333: aload 0
      // 334: getfield b1/p.h Lb1/m;
      // 337: getfield b1/m.a [B
      // 33a: astore 6
      // 33c: aload 6
      // 33e: arraylength
      // 33f: istore 1
      // 340: bipush 6
      // 342: invokestatic java/nio/ByteBuffer.allocate (I)Ljava/nio/ByteBuffer;
      // 345: getstatic java/nio/ByteOrder.BIG_ENDIAN Ljava/nio/ByteOrder;
      // 348: invokevirtual java/nio/ByteBuffer.order (Ljava/nio/ByteOrder;)Ljava/nio/ByteBuffer;
      // 34b: astore 5
      // 34d: aload 5
      // 34f: bipush 1
      // 350: invokevirtual java/nio/ByteBuffer.put (B)Ljava/nio/ByteBuffer;
      // 353: bipush 0
      // 354: invokevirtual java/nio/ByteBuffer.put (B)Ljava/nio/ByteBuffer;
      // 357: iload 1
      // 358: invokevirtual java/nio/ByteBuffer.putInt (I)Ljava/nio/ByteBuffer;
      // 35b: pop
      // 35c: aload 0
      // 35d: getfield b1/p.g Ljava/io/DataOutputStream;
      // 360: aload 5
      // 362: invokevirtual java/nio/ByteBuffer.array ()[B
      // 365: invokevirtual java/io/OutputStream.write ([B)V
      // 368: aload 0
      // 369: getfield b1/p.g Ljava/io/DataOutputStream;
      // 36c: aload 6
      // 36e: invokevirtual java/io/OutputStream.write ([B)V
      // 371: aload 0
      // 372: invokevirtual b1/p.y ()Lb1/n;
      // 375: astore 5
      // 377: aload 5
      // 379: ifnull 41a
      // 37c: bipush 0
      // 37d: aload 5
      // 37f: getfield b1/n.b B
      // 382: invokestatic b1/p.x (BB)Z
      // 385: ifne 38b
      // 388: goto 41a
      // 38b: aload 5
      // 38d: getfield b1/n.c I
      // 390: newarray 8
      // 392: astore 6
      // 394: aload 0
      // 395: getfield b1/p.f Ljava/io/DataInputStream;
      // 398: aload 6
      // 39a: invokevirtual java/io/DataInputStream.readFully ([B)V
      // 39d: aload 0
      // 39e: getfield b1/p.h Lb1/m;
      // 3a1: astore 5
      // 3a3: aload 5
      // 3a5: getfield b1/m.f Z
      // 3a8: ifeq 3ae
      // 3ab: goto 41a
      // 3ae: aload 5
      // 3b0: getfield b1/m.b Lio/github/muntashirakon/crypto/spake2/Spake2Context;
      // 3b3: aload 6
      // 3b5: invokevirtual io/github/muntashirakon/crypto/spake2/Spake2Context.b ([B)[B
      // 3b8: astore 7
      // 3ba: new org/bouncycastle/crypto/generators/HKDFBytesGenerator
      // 3bd: astore 6
      // 3bf: new org/bouncycastle/crypto/digests/SHA256Digest
      // 3c2: astore 8
      // 3c4: aload 8
      // 3c6: invokespecial org/bouncycastle/crypto/digests/SHA256Digest.<init> ()V
      // 3c9: aload 6
      // 3cb: aload 8
      // 3cd: invokespecial org/bouncycastle/crypto/generators/HKDFBytesGenerator.<init> (Lorg/bouncycastle/crypto/Digest;)V
      // 3d0: new org/bouncycastle/crypto/params/HKDFParameters
      // 3d3: astore 8
      // 3d5: aload 8
      // 3d7: aload 7
      // 3d9: aconst_null
      // 3da: getstatic b1/m.i [B
      // 3dd: invokespecial org/bouncycastle/crypto/params/HKDFParameters.<init> ([B[B[B)V
      // 3e0: aload 6
      // 3e2: aload 8
      // 3e4: invokevirtual org/bouncycastle/crypto/generators/HKDFBytesGenerator.init (Lorg/bouncycastle/crypto/DerivationParameters;)V
      // 3e7: aload 5
      // 3e9: getfield b1/m.c [B
      // 3ec: astore 5
      // 3ee: aload 6
      // 3f0: aload 5
      // 3f2: bipush 0
      // 3f3: aload 5
      // 3f5: arraylength
      // 3f6: invokevirtual org/bouncycastle/crypto/generators/HKDFBytesGenerator.generateBytes ([BII)I
      // 3f9: pop
      // 3fa: bipush 1
      // 3fb: istore 1
      // 3fc: goto 41c
      // 3ff: astore 5
      // 401: ldc "p"
      // 403: ldc_w "Unable to initialize pairing cipher"
      // 406: invokestatic android/util/Log.e (Ljava/lang/String;Ljava/lang/String;)I
      // 409: pop
      // 40a: new java/io/IOException
      // 40d: dup
      // 40e: invokespecial java/io/IOException.<init> ()V
      // 411: aload 5
      // 413: invokevirtual java/lang/Throwable.initCause (Ljava/lang/Throwable;)Ljava/lang/Throwable;
      // 416: checkcast java/io/IOException
      // 419: athrow
      // 41a: bipush 0
      // 41b: istore 1
      // 41c: iload 1
      // 41d: ifeq 428
      // 420: aload 0
      // 421: bipush 3
      // 422: putfield b1/p.i I
      // 425: goto 138
      // 428: aload 0
      // 429: bipush 4
      // 42a: putfield b1/p.i I
      // 42d: new java/io/IOException
      // 430: dup
      // 431: ldc_w "Exchanging message wasn't successful."
      // 434: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
      // 437: athrow
      // 438: new java/io/IOException
      // 43b: dup
      // 43c: ldc_w "Connection closed with errors."
      // 43f: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
      // 442: athrow
      // 443: new java/io/IOException
      // 446: dup
      // 447: ldc_w "Unable to create PairingAuthCtx."
      // 44a: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
      // 44d: athrow
      // 44e: new javax/net/ssl/SSLException
      // 451: astore 5
      // 453: aload 5
      // 455: ldc_w "TLSv1.3 isn't supported on your platform. Use custom Conscrypt library instead."
      // 458: invokespecial javax/net/ssl/SSLException.<init> (Ljava/lang/String;)V
      // 45b: aload 5
      // 45d: athrow
      // 45e: astore 5
      // 460: new javax/net/ssl/SSLException
      // 463: dup
      // 464: aload 5
      // 466: invokespecial javax/net/ssl/SSLException.<init> (Ljava/lang/Throwable;)V
      // 469: athrow
      // 46a: astore 5
      // 46c: aload 5
      // 46e: athrow
      // 46f: new java/io/IOException
      // 472: dup
      // 473: ldc_w "Connection is not ready yet."
      // 476: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
      // 479: athrow
   }
}
