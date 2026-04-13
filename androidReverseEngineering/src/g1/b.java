package g1;

import a1.q;
import android.util.Log;
import i1.c;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import k1.d;
import l1.e;
import l1.f;

public final class b extends a {
   public j1.a b = new j1.a();
   public final j1.a c = new j1.a();
   public final ArrayList d;
   public j1.a e;
   public m1.a f;
   public final ArrayList g;
   public d h;
   public final ArrayList i;
   public ByteBuffer j;
   public final SecureRandom k = new SecureRandom();
   public final int l;

   public b() {
      this(Integer.MAX_VALUE, Collections.emptyList(), Collections.singletonList(new m1.b("")));
   }

   public b(int var1, List var2, List var3) {
      if (var2 != null && var3 != null && var1 >= 1) {
         this.d = new ArrayList(var2.size());
         this.g = new ArrayList(var3.size());
         this.i = new ArrayList();
         Iterator var5 = var2.iterator();
         boolean var4 = false;

         while (var5.hasNext()) {
            if (((j1.a)var5.next()).getClass().equals(j1.a.class)) {
               var4 = true;
            }
         }

         this.d.addAll(var2);
         if (!var4) {
            ArrayList var6 = this.d;
            var6.add(var6.size(), this.b);
         }

         this.g.addAll(var3);
         this.l = var1;
         this.e = null;
      } else {
         throw new IllegalArgumentException();
      }
   }

   public static String i(String var0) {
      String var1 = a.a.z(var0.trim(), "258EAFA5-E914-47DA-95CA-C5AB0DC85B11");

      try {
         var4 = MessageDigest.getInstance("SHA1");
      } catch (NoSuchAlgorithmException var3) {
         throw new IllegalStateException(var3);
      }

      byte[] var5 = var4.digest(var1.getBytes());

      try {
         var0 = q.r(var5.length, var5);
      } catch (IOException var2) {
         var0 = null;
      }

      return var0;
   }

   public static byte k(int var0) {
      if (var0 != 1) {
         if (var0 != 2) {
            return (byte)(var0 != 3 ? 0 : 16);
         } else {
            return 32;
         }
      } else {
         return 64;
      }
   }

   public static void q(int var0, int var1) {
      if (var0 < var1) {
         Log.d("g1.b", "Incomplete frame: maxpacketsize < realpacketsize");
         throw new i1.a(var1);
      }
   }

   @Override
   public final b a() {
      ArrayList var1 = new ArrayList();
      Iterator var2 = this.d.iterator();

      while (var2.hasNext()) {
         ((j1.a)var2.next()).getClass();
         var1.add(new j1.a());
      }

      ArrayList var4 = new ArrayList();
      Iterator var3 = this.g.iterator();

      while (var3.hasNext()) {
         var4.add(new m1.b(((m1.b)((m1.a)var3.next())).a));
      }

      return new b(this.l, var1, var4);
   }

   public final int e(l1.a var1, f var2) {
      e var10 = (e)var2;
      boolean var3;
      if (var10.a("Upgrade").equalsIgnoreCase("websocket") && var10.a("Connection").toLowerCase(Locale.ENGLISH).contains("upgrade")) {
         var3 = 1;
      } else {
         var3 = 0;
      }

      String var5;
      if (!var3) {
         var5 = "acceptHandshakeAsClient - Missing/wrong upgrade or connection in handshake.";
      } else {
         e var6 = (e)var1;
         if (var6.a.containsKey("Sec-WebSocket-Key") && var10.a.containsKey("Sec-WebSocket-Accept")) {
            String var4 = var10.a("Sec-WebSocket-Accept");
            if (!i(var6.a("Sec-WebSocket-Key")).equals(var4)) {
               var5 = "acceptHandshakeAsClient - Wrong key for Sec-WebSocket-Key.";
            } else {
               var10.a("Sec-WebSocket-Extensions");
               Iterator var7 = this.d.iterator();
               if (var7.hasNext()) {
                  j1.a var8 = (j1.a)var7.next();
                  var8.getClass();
                  this.b = var8;
                  StringBuilder var9 = new StringBuilder("acceptHandshakeAsClient - Matching extension found: ");
                  var9.append(this.b);
                  Log.d("g1.b", var9.toString());
                  var3 = 1;
               } else {
                  var3 = 2;
               }

               if (this.h(var10.a("Sec-WebSocket-Protocol")) == 1 && var3 == 1) {
                  return 1;
               }

               var5 = "acceptHandshakeAsClient - No matching extension or protocol found.";
            }
         } else {
            var5 = "acceptHandshakeAsClient - Missing Sec-WebSocket-Key or Sec-WebSocket-Accept";
         }
      }

      Log.d("g1.b", var5);
      return 2;
   }

   @Override
   public final boolean equals(Object var1) {
      boolean var2 = true;
      if (this == var1) {
         return true;
      } else if (var1 != null && b.class == var1.getClass()) {
         var1 = var1;
         if (this.l != var1.l) {
            return false;
         } else {
            j1.a var3 = this.b;
            if (var3 != null ? var3.equals(var1.b) : var1.b == null) {
               m1.a var5 = this.f;
               if (var5 != null) {
                  var2 = var5.equals(var1.f);
               } else if (var1.f != null) {
                  var2 = false;
               }

               return var2;
            } else {
               return false;
            }
         }
      } else {
         return false;
      }
   }

   public final int f(l1.a var1) {
      int var2;
      label32: {
         var5 = (e)var1;
         String var3 = var5.a("Sec-WebSocket-Version");
         if (var3.length() > 0) {
            try {
               var2 = Integer.parseInt(var3.trim());
               break label32;
            } catch (NumberFormatException var4) {
            }
         }

         var2 = -1;
      }

      String var6;
      if (var2 != 13) {
         var6 = "acceptHandshakeAsServer - Wrong websocket version.";
      } else {
         var5.a("Sec-WebSocket-Extensions");
         Iterator var8 = this.d.iterator();
         byte var7;
         if (var8.hasNext()) {
            j1.a var9 = (j1.a)var8.next();
            var9.getClass();
            this.b = var9;
            StringBuilder var10 = new StringBuilder("acceptHandshakeAsServer - Matching extension found:");
            var10.append(this.b);
            Log.d("g1.b", var10.toString());
            var7 = 1;
         } else {
            var7 = 2;
         }

         if (this.h(var5.a("Sec-WebSocket-Protocol")) == 1 && var7 == 1) {
            return 1;
         }

         var6 = "acceptHandshakeAsServer - No matching extension or protocol found.";
      }

      Log.d("g1.b", var6);
      return 2;
   }

   public final void g() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield g1/b.i Ljava/util/ArrayList;
      // 04: astore 3
      // 05: aload 3
      // 06: monitorenter
      // 07: aload 0
      // 08: getfield g1/b.i Ljava/util/ArrayList;
      // 0b: invokevirtual java/util/ArrayList.iterator ()Ljava/util/Iterator;
      // 0e: astore 4
      // 10: lconst_0
      // 11: lstore 1
      // 12: aload 4
      // 14: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 19: ifeq 30
      // 1c: lload 1
      // 1d: aload 4
      // 1f: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 24: checkcast java/nio/ByteBuffer
      // 27: invokevirtual java/nio/Buffer.limit ()I
      // 2a: i2l
      // 2b: ladd
      // 2c: lstore 1
      // 2d: goto 12
      // 30: aload 3
      // 31: monitorexit
      // 32: lload 1
      // 33: aload 0
      // 34: getfield g1/b.l I
      // 37: i2l
      // 38: lcmp
      // 39: ifgt 3d
      // 3c: return
      // 3d: aload 0
      // 3e: getfield g1/b.i Ljava/util/ArrayList;
      // 41: astore 3
      // 42: aload 3
      // 43: monitorenter
      // 44: aload 0
      // 45: getfield g1/b.i Ljava/util/ArrayList;
      // 48: invokevirtual java/util/ArrayList.clear ()V
      // 4b: aload 3
      // 4c: monitorexit
      // 4d: ldc "g1.b"
      // 4f: ldc_w "Payload limit reached. Allowed: %d Current: %d"
      // 52: bipush 2
      // 53: anewarray 94
      // 56: dup
      // 57: bipush 0
      // 58: aload 0
      // 59: getfield g1/b.l I
      // 5c: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 5f: aastore
      // 60: dup
      // 61: bipush 1
      // 62: lload 1
      // 63: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 66: aastore
      // 67: invokestatic java/lang/String.format (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
      // 6a: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 6d: pop
      // 6e: new i1/f
      // 71: dup
      // 72: aload 0
      // 73: getfield g1/b.l I
      // 76: invokespecial i1/f.<init> (I)V
      // 79: athrow
      // 7a: astore 4
      // 7c: aload 3
      // 7d: monitorexit
      // 7e: aload 4
      // 80: athrow
      // 81: astore 4
      // 83: aload 3
      // 84: monitorexit
      // 85: aload 4
      // 87: athrow
   }

   public final int h(String var1) {
      for (m1.a var8 : this.g) {
         boolean var2;
         label27: {
            String var6 = ((m1.b)var8).a;
            if (!"".equals(var6)) {
               String var9 = m1.b.b.matcher(var1).replaceAll("");
               String[] var11 = m1.b.c.split(var9);
               int var5 = var11.length;
               boolean var4 = false;
               int var3 = 0;

               while (true) {
                  var2 = var4;
                  if (var3 >= var5) {
                     break label27;
                  }

                  if (var6.equals(var11[var3])) {
                     break;
                  }

                  var3++;
               }
            }

            var2 = true;
         }

         if (var2) {
            this.f = var8;
            StringBuilder var10 = new StringBuilder("acceptHandshake - Matching protocol found: ");
            var10.append(this.f);
            Log.d("g1.b", var10.toString());
            return 1;
         }
      }

      return 2;
   }

   @Override
   public final int hashCode() {
      j1.a var4 = this.b;
      int var2 = 0;
      int var1;
      if (var4 != null) {
         var1 = var4.hashCode();
      } else {
         var1 = 0;
      }

      m1.a var5 = this.f;
      if (var5 != null) {
         var2 = var5.hashCode();
      }

      int var3 = this.l;
      return (var1 * 31 + var2) * 31 + (var3 ^ var3 >>> 32);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final ByteBuffer j() {
      ArrayList var3 = this.i;
      synchronized (var3){} // $VF: monitorenter 

      ByteBuffer var5;
      label300: {
         Throwable var10000;
         label301: {
            Iterator var4;
            try {
               var4 = this.i.iterator();
            } catch (Throwable var47) {
               var10000 = var47;
               boolean var10001 = false;
               break label301;
            }

            long var1 = 0L;

            while (true) {
               try {
                  if (!var4.hasNext()) {
                     break;
                  }

                  var1 += (long)((ByteBuffer)var4.next()).limit();
               } catch (Throwable var46) {
                  var10000 = var46;
                  boolean var50 = false;
                  break label301;
               }
            }

            try {
               this.g();
               var5 = ByteBuffer.allocate((int)var1);
               var4 = this.i.iterator();
            } catch (Throwable var44) {
               var10000 = var44;
               boolean var51 = false;
               break label301;
            }

            while (true) {
               try {
                  if (!var4.hasNext()) {
                     break;
                  }

                  var5.put((ByteBuffer)var4.next());
               } catch (Throwable var45) {
                  var10000 = var45;
                  boolean var52 = false;
                  break label301;
               }
            }

            label273:
            try {
               // $VF: monitorexit
               break label300;
            } catch (Throwable var43) {
               var10000 = var43;
               boolean var53 = false;
               break label273;
            }
         }

         while (true) {
            Throwable var49 = var10000;

            try {
               // $VF: monitorexit
               throw var49;
            } catch (Throwable var42) {
               var10000 = var42;
               boolean var54 = false;
               continue;
            }
         }
      }

      ((Buffer)var5).flip();
      return var5;
   }

   public final f l(l1.a var1, l1.d var2) {
      var2.b("Upgrade", "websocket");
      e var4 = (e)var1;
      var2.b("Connection", var4.a("Connection"));
      String var5 = var4.a("Sec-WebSocket-Key");
      if (!"".equals(var5)) {
         var2.b("Sec-WebSocket-Accept", i(var5));
         this.b.getClass();
         m1.a var6 = this.f;
         if (var6 != null && ((m1.b)var6).a.length() != 0) {
            var2.b("Sec-WebSocket-Protocol", ((m1.b)this.f).a);
         }

         var2.b = "Web Socket Protocol Handshake";
         var2.b("Server", "TooTallNate Java-WebSocket");
         Calendar var7 = Calendar.getInstance();
         SimpleDateFormat var3 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
         var3.setTimeZone(TimeZone.getTimeZone("GMT"));
         var2.b("Date", var3.format(var7.getTime()));
         return var2;
      } else {
         throw new i1.e("missing Sec-WebSocket-Key");
      }
   }

   public final void m(e1.d param1, d param2) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 000: aload 2
      // 001: getfield k1/d.b I
      // 004: istore 4
      // 006: iload 4
      // 008: bipush 6
      // 00a: if_icmpne 049
      // 00d: aload 2
      // 00e: instanceof k1/b
      // 011: ifeq 026
      // 014: aload 2
      // 015: checkcast k1/b
      // 018: astore 2
      // 019: aload 2
      // 01a: getfield k1/b.i I
      // 01d: istore 3
      // 01e: aload 2
      // 01f: getfield k1/b.j Ljava/lang/String;
      // 022: astore 2
      // 023: goto 02d
      // 026: ldc ""
      // 028: astore 2
      // 029: sipush 1005
      // 02c: istore 3
      // 02d: aload 1
      // 02e: getfield e1/d.h I
      // 031: bipush 3
      // 032: if_icmpne 03f
      // 035: aload 1
      // 036: aload 2
      // 037: bipush 1
      // 038: iload 3
      // 039: invokevirtual e1/d.k (Ljava/lang/String;ZI)V
      // 03c: goto 2ba
      // 03f: aload 1
      // 040: aload 2
      // 041: bipush 1
      // 042: iload 3
      // 043: invokevirtual e1/d.i (Ljava/lang/String;ZI)V
      // 046: goto 2ba
      // 049: iload 4
      // 04b: bipush 4
      // 04c: if_icmpne 06c
      // 04f: aload 1
      // 050: getfield e1/d.c Le1/c;
      // 053: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 056: pop
      // 057: aload 1
      // 058: new k1/f
      // 05b: dup
      // 05c: aload 2
      // 05d: checkcast k1/e
      // 060: invokespecial k1/f.<init> (Lk1/e;)V
      // 063: invokestatic java/util/Collections.singletonList (Ljava/lang/Object;)Ljava/util/List;
      // 066: invokevirtual e1/d.s (Ljava/util/List;)V
      // 069: goto 2ba
      // 06c: iload 4
      // 06e: bipush 5
      // 06f: if_icmpne 089
      // 072: aload 1
      // 073: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 076: pop
      // 077: aload 1
      // 078: invokestatic java/lang/System.nanoTime ()J
      // 07b: putfield e1/d.r J
      // 07e: aload 1
      // 07f: getfield e1/d.c Le1/c;
      // 082: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 085: pop
      // 086: goto 2ba
      // 089: aload 2
      // 08a: getfield k1/d.a Z
      // 08d: istore 5
      // 08f: iload 5
      // 091: ifeq 131
      // 094: iload 4
      // 096: bipush 1
      // 097: if_icmpne 09d
      // 09a: goto 131
      // 09d: aload 0
      // 09e: getfield g1/b.h Lk1/d;
      // 0a1: ifnonnull 11a
      // 0a4: iload 4
      // 0a6: bipush 2
      // 0a7: if_icmpne 0d3
      // 0aa: aload 1
      // 0ab: getfield e1/d.c Le1/c;
      // 0ae: aload 1
      // 0af: aload 2
      // 0b0: invokevirtual k1/d.a ()Ljava/nio/ByteBuffer;
      // 0b3: invokestatic o1/a.b (Ljava/nio/ByteBuffer;)Ljava/lang/String;
      // 0b6: invokevirtual e1/c.n (Le1/b;Ljava/lang/String;)V
      // 0b9: goto 2ba
      // 0bc: astore 2
      // 0bd: ldc "g1.b"
      // 0bf: ldc_w "Runtime exception during onWebsocketMessage"
      // 0c2: aload 2
      // 0c3: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      // 0c6: pop
      // 0c7: aload 1
      // 0c8: getfield e1/d.c Le1/c;
      // 0cb: aload 1
      // 0cc: aload 2
      // 0cd: invokevirtual e1/c.l (Le1/b;Ljava/lang/Exception;)V
      // 0d0: goto 2ba
      // 0d3: iload 4
      // 0d5: bipush 3
      // 0d6: if_icmpne 103
      // 0d9: aload 1
      // 0da: getfield e1/d.c Le1/c;
      // 0dd: astore 6
      // 0df: aload 2
      // 0e0: invokevirtual k1/d.a ()Ljava/nio/ByteBuffer;
      // 0e3: pop
      // 0e4: aload 6
      // 0e6: invokevirtual e1/c.m ()V
      // 0e9: goto 2ba
      // 0ec: astore 2
      // 0ed: ldc "g1.b"
      // 0ef: ldc_w "Runtime exception during onWebsocketMessage"
      // 0f2: aload 2
      // 0f3: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      // 0f6: pop
      // 0f7: aload 1
      // 0f8: getfield e1/d.c Le1/c;
      // 0fb: aload 1
      // 0fc: aload 2
      // 0fd: invokevirtual e1/c.l (Le1/b;Ljava/lang/Exception;)V
      // 100: goto 2ba
      // 103: ldc "g1.b"
      // 105: ldc_w "non control or continious frame expected"
      // 108: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 10b: pop
      // 10c: new i1/c
      // 10f: dup
      // 110: sipush 1002
      // 113: ldc_w "non control or continious frame expected"
      // 116: invokespecial i1/c.<init> (ILjava/lang/String;)V
      // 119: athrow
      // 11a: ldc "g1.b"
      // 11c: ldc_w "Protocol error: Continuous frame sequence not completed."
      // 11f: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 122: pop
      // 123: new i1/c
      // 126: dup
      // 127: sipush 1002
      // 12a: ldc_w "Continuous frame sequence not completed."
      // 12d: invokespecial i1/c.<init> (ILjava/lang/String;)V
      // 130: athrow
      // 131: iload 4
      // 133: bipush 1
      // 134: if_icmpeq 17f
      // 137: aload 0
      // 138: getfield g1/b.h Lk1/d;
      // 13b: ifnonnull 168
      // 13e: aload 0
      // 13f: aload 2
      // 140: putfield g1/b.h Lk1/d;
      // 143: aload 2
      // 144: invokevirtual k1/d.a ()Ljava/nio/ByteBuffer;
      // 147: astore 6
      // 149: aload 0
      // 14a: getfield g1/b.i Ljava/util/ArrayList;
      // 14d: astore 1
      // 14e: aload 1
      // 14f: monitorenter
      // 150: aload 0
      // 151: getfield g1/b.i Ljava/util/ArrayList;
      // 154: aload 6
      // 156: invokevirtual java/util/ArrayList.add (Ljava/lang/Object;)Z
      // 159: pop
      // 15a: aload 1
      // 15b: monitorexit
      // 15c: aload 0
      // 15d: invokevirtual g1/b.g ()V
      // 160: goto 267
      // 163: astore 2
      // 164: aload 1
      // 165: monitorexit
      // 166: aload 2
      // 167: athrow
      // 168: ldc "g1.b"
      // 16a: ldc_w "Protocol error: Previous continuous frame sequence not completed."
      // 16d: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 170: pop
      // 171: new i1/c
      // 174: dup
      // 175: sipush 1002
      // 178: ldc_w "Previous continuous frame sequence not completed."
      // 17b: invokespecial i1/c.<init> (ILjava/lang/String;)V
      // 17e: athrow
      // 17f: iload 5
      // 181: ifeq 260
      // 184: aload 0
      // 185: getfield g1/b.h Lk1/d;
      // 188: ifnull 249
      // 18b: aload 2
      // 18c: invokevirtual k1/d.a ()Ljava/nio/ByteBuffer;
      // 18f: astore 7
      // 191: aload 0
      // 192: getfield g1/b.i Ljava/util/ArrayList;
      // 195: astore 6
      // 197: aload 6
      // 199: monitorenter
      // 19a: aload 0
      // 19b: getfield g1/b.i Ljava/util/ArrayList;
      // 19e: aload 7
      // 1a0: invokevirtual java/util/ArrayList.add (Ljava/lang/Object;)Z
      // 1a3: pop
      // 1a4: aload 6
      // 1a6: monitorexit
      // 1a7: aload 0
      // 1a8: invokevirtual g1/b.g ()V
      // 1ab: aload 0
      // 1ac: getfield g1/b.h Lk1/d;
      // 1af: astore 6
      // 1b1: aload 6
      // 1b3: getfield k1/d.b I
      // 1b6: istore 3
      // 1b7: iload 3
      // 1b8: bipush 2
      // 1b9: if_icmpne 1e6
      // 1bc: aload 6
      // 1be: aload 0
      // 1bf: invokevirtual g1/b.j ()Ljava/nio/ByteBuffer;
      // 1c2: invokevirtual k1/d.c (Ljava/nio/ByteBuffer;)V
      // 1c5: aload 0
      // 1c6: getfield g1/b.h Lk1/d;
      // 1c9: invokevirtual k1/d.b ()V
      // 1cc: aload 1
      // 1cd: getfield e1/d.c Le1/c;
      // 1d0: aload 1
      // 1d1: aload 0
      // 1d2: getfield g1/b.h Lk1/d;
      // 1d5: invokevirtual k1/d.a ()Ljava/nio/ByteBuffer;
      // 1d8: invokestatic o1/a.b (Ljava/nio/ByteBuffer;)Ljava/lang/String;
      // 1db: invokevirtual e1/c.n (Le1/b;Ljava/lang/String;)V
      // 1de: goto 226
      // 1e1: astore 6
      // 1e3: goto 211
      // 1e6: iload 3
      // 1e7: bipush 3
      // 1e8: if_icmpne 226
      // 1eb: aload 6
      // 1ed: aload 0
      // 1ee: invokevirtual g1/b.j ()Ljava/nio/ByteBuffer;
      // 1f1: invokevirtual k1/d.c (Ljava/nio/ByteBuffer;)V
      // 1f4: aload 0
      // 1f5: getfield g1/b.h Lk1/d;
      // 1f8: invokevirtual k1/d.b ()V
      // 1fb: aload 1
      // 1fc: getfield e1/d.c Le1/c;
      // 1ff: astore 6
      // 201: aload 0
      // 202: getfield g1/b.h Lk1/d;
      // 205: invokevirtual k1/d.a ()Ljava/nio/ByteBuffer;
      // 208: pop
      // 209: aload 6
      // 20b: invokevirtual e1/c.m ()V
      // 20e: goto 226
      // 211: ldc "g1.b"
      // 213: ldc_w "Runtime exception during onWebsocketMessage"
      // 216: aload 6
      // 218: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      // 21b: pop
      // 21c: aload 1
      // 21d: getfield e1/d.c Le1/c;
      // 220: aload 1
      // 221: aload 6
      // 223: invokevirtual e1/c.l (Le1/b;Ljava/lang/Exception;)V
      // 226: aload 0
      // 227: aconst_null
      // 228: putfield g1/b.h Lk1/d;
      // 22b: aload 0
      // 22c: getfield g1/b.i Ljava/util/ArrayList;
      // 22f: astore 1
      // 230: aload 1
      // 231: monitorenter
      // 232: aload 0
      // 233: getfield g1/b.i Ljava/util/ArrayList;
      // 236: invokevirtual java/util/ArrayList.clear ()V
      // 239: aload 1
      // 23a: monitorexit
      // 23b: goto 267
      // 23e: astore 2
      // 23f: aload 1
      // 240: monitorexit
      // 241: aload 2
      // 242: athrow
      // 243: astore 1
      // 244: aload 6
      // 246: monitorexit
      // 247: aload 1
      // 248: athrow
      // 249: ldc "g1.b"
      // 24b: ldc_w "Protocol error: Previous continuous frame sequence not completed."
      // 24e: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 251: pop
      // 252: new i1/c
      // 255: dup
      // 256: sipush 1002
      // 259: ldc_w "Continuous frame sequence was not started."
      // 25c: invokespecial i1/c.<init> (ILjava/lang/String;)V
      // 25f: athrow
      // 260: aload 0
      // 261: getfield g1/b.h Lk1/d;
      // 264: ifnull 2bb
      // 267: iload 4
      // 269: bipush 2
      // 26a: if_icmpne 28e
      // 26d: aload 2
      // 26e: invokevirtual k1/d.a ()Ljava/nio/ByteBuffer;
      // 271: invokestatic o1/a.a (Ljava/nio/ByteBuffer;)Z
      // 274: ifeq 27a
      // 277: goto 28e
      // 27a: ldc "g1.b"
      // 27c: ldc_w "Protocol error: Payload is not UTF8"
      // 27f: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 282: pop
      // 283: new i1/c
      // 286: dup
      // 287: sipush 1007
      // 28a: invokespecial i1/c.<init> (I)V
      // 28d: athrow
      // 28e: iload 4
      // 290: bipush 1
      // 291: if_icmpne 2ba
      // 294: aload 0
      // 295: getfield g1/b.h Lk1/d;
      // 298: ifnull 2ba
      // 29b: aload 2
      // 29c: invokevirtual k1/d.a ()Ljava/nio/ByteBuffer;
      // 29f: astore 2
      // 2a0: aload 0
      // 2a1: getfield g1/b.i Ljava/util/ArrayList;
      // 2a4: astore 1
      // 2a5: aload 1
      // 2a6: monitorenter
      // 2a7: aload 0
      // 2a8: getfield g1/b.i Ljava/util/ArrayList;
      // 2ab: aload 2
      // 2ac: invokevirtual java/util/ArrayList.add (Ljava/lang/Object;)Z
      // 2af: pop
      // 2b0: aload 1
      // 2b1: monitorexit
      // 2b2: goto 2ba
      // 2b5: astore 2
      // 2b6: aload 1
      // 2b7: monitorexit
      // 2b8: aload 2
      // 2b9: athrow
      // 2ba: return
      // 2bb: ldc "g1.b"
      // 2bd: ldc_w "Protocol error: Continuous frame sequence was not started."
      // 2c0: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 2c3: pop
      // 2c4: new i1/c
      // 2c7: dup
      // 2c8: sipush 1002
      // 2cb: ldc_w "Continuous frame sequence was not started."
      // 2ce: invokespecial i1/c.<init> (ILjava/lang/String;)V
      // 2d1: athrow
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final List n(ByteBuffer var1) {
      while (true) {
         i1.a var10000;
         label54: {
            LinkedList var4 = new LinkedList();
            if (this.j != null) {
               int var2;
               int var3;
               try {
                  ((Buffer)var1).mark();
                  var2 = var1.remaining();
                  var3 = this.j.remaining();
               } catch (i1.a var9) {
                  var10000 = var9;
                  boolean var10001 = false;
                  break label54;
               }

               if (var3 > var2) {
                  try {
                     this.j.put(var1.array(), var1.position(), var2);
                     ((Buffer)var1).position(var1.position() + var2);
                     return Collections.emptyList();
                  } catch (i1.a var7) {
                     var10000 = var7;
                     boolean var15 = false;
                     break label54;
                  }
               }

               try {
                  this.j.put(var1.array(), var1.position(), var3);
                  ((Buffer)var1).position(var1.position() + var3);
                  var4.add(this.o((ByteBuffer)((Buffer)this.j.duplicate()).position(0)));
                  this.j = null;
               } catch (i1.a var8) {
                  var10000 = var8;
                  boolean var14 = false;
                  break label54;
               }
            }

            while (var1.hasRemaining()) {
               ((Buffer)var1).mark();

               try {
                  var4.add(this.o(var1));
               } catch (i1.a var6) {
                  ((Buffer)var1).reset();
                  int var10 = var6.a;
                  if (var10 >= 0) {
                     ByteBuffer var5 = ByteBuffer.allocate(var10);
                     this.j = var5;
                     var5.put(var1);
                     break;
                  }

                  throw new c(1002, "Negative count");
               }
            }

            return var4;
         }

         i1.a var12 = var10000;
         int var11 = var12.a;
         if (var11 < 0) {
            throw new c(1002, "Negative count");
         }

         ByteBuffer var13 = ByteBuffer.allocate(var11);
         ((Buffer)this.j).rewind();
         var13.put(this.j);
         this.j = var13;
      }
   }

   public final k1.c o(ByteBuffer var1) {
      if (var1 != null) {
         int var7 = var1.remaining();
         q(var7, 2);
         int var2 = var1.get();
         boolean var8;
         if (var2 >> 8 != 0) {
            var8 = true;
         } else {
            var8 = false;
         }

         boolean var9;
         if ((var2 & 64) != 0) {
            var9 = true;
         } else {
            var9 = false;
         }

         boolean var10;
         if ((var2 & 32) != 0) {
            var10 = true;
         } else {
            var10 = false;
         }

         boolean var11;
         if ((var2 & 16) != 0) {
            var11 = true;
         } else {
            var11 = false;
         }

         int var3 = var1.get();
         boolean var5;
         if ((var3 & -128) != 0) {
            var5 = true;
         } else {
            var5 = false;
         }

         var3 = (byte)(var3 & 127);
         var2 = (byte)(var2 & 15);
         if (var2 != 0) {
            if (var2 != 1) {
               if (var2 != 2) {
                  switch (var2) {
                     case 8:
                        var2 = (byte)6;
                        break;
                     case 9:
                        var2 = (byte)4;
                        break;
                     case 10:
                        var2 = (byte)5;
                        break;
                     default:
                        StringBuilder var16 = new StringBuilder("Unknown opcode ");
                        var16.append((short)var2);
                        throw new i1.d(var16.toString());
                  }
               } else {
                  var2 = (byte)3;
               }
            } else {
               var2 = (byte)2;
            }
         } else {
            var2 = (byte)1;
         }

         byte var4;
         if (var3 >= 0 && var3 <= 125) {
            var4 = 2;
         } else {
            if (var2 == 4 || var2 == 5 || var2 == 6) {
               Log.d("g1.b", "Invalid frame: more than 125 octets");
               throw new i1.d("more than 125 octets");
            }

            if (var3 == 126) {
               q(var7, 4);
               byte[] var14 = new byte[]{0, var1.get(), var1.get()};
               var3 = new BigInteger(var14).intValue();
               var4 = 4;
            } else {
               q(var7, 10);
               byte[] var26 = new byte[8];

               for (int var23 = 0; var23 < 8; var23++) {
                  var26[var23] = var1.get();
               }

               long var12 = new BigInteger(var26).longValue();
               this.p(var12);
               var3 = (int)var12;
               var4 = 10;
            }
         }

         this.p((long)var3);
         byte var6;
         if (var5) {
            var6 = 4;
         } else {
            var6 = 0;
         }

         q(var7, var4 + var6 + var3);
         if (var3 < 0) {
            throw new c(1002, "Negative count");
         } else {
            ByteBuffer var27 = ByteBuffer.allocate(var3);
            if (var5) {
               byte[] var15 = new byte[4];
               var1.get(var15);

               for (int var25 = 0; var25 < var3; var25++) {
                  var27.put((byte)(var1.get() ^ var15[var25 % 4]));
               }
            } else {
               var27.put(var1.array(), var1.position(), var27.limit());
               var3 = var1.position();
               ((Buffer)var1).position(var27.limit() + var3);
            }

            var2 = r.a.a(var2);
            Object var17;
            if (var2 != 0) {
               if (var2 != 1) {
                  if (var2 != 2) {
                     if (var2 != 3) {
                        if (var2 != 4) {
                           if (var2 != 5) {
                              throw new IllegalArgumentException("Supplied opcode is invalid");
                           }

                           var17 = new k1.b();
                        } else {
                           var17 = new k1.f();
                        }
                     } else {
                        var17 = new k1.e();
                     }
                  } else {
                     var17 = new k1.a(0);
                  }
               } else {
                  var17 = new k1.a(2);
               }
            } else {
               var17 = new k1.a(1);
            }

            ((d)var17).a = var8;
            ((d)var17).e = var9;
            ((d)var17).f = var10;
            ((d)var17).g = var11;
            ((Buffer)var27).flip();
            ((d)var17).c(var27);
            var2 = ((d)var17).b;
            j1.a var28 = this.c;
            if (var2 != 1) {
               if (!((d)var17).e && !((d)var17).f && !((d)var17).g) {
                  this.e = var28;
               } else {
                  this.e = this.b;
               }
            }

            if (this.e == null) {
               this.e = var28;
            }

            this.e.getClass();
            if (!((d)var17).e && !((d)var17).f && !((d)var17).g) {
               this.e.getClass();
               ((k1.c)var17).b();
               return (k1.c)var17;
            } else {
               StringBuilder var29 = new StringBuilder("bad rsv RSV1: ");
               var29.append(((d)var17).e);
               var29.append(" RSV2: ");
               var29.append(((d)var17).f);
               var29.append(" RSV3: ");
               var29.append(((d)var17).g);
               throw new i1.d(var29.toString());
            }
         }
      } else {
         throw new IllegalArgumentException();
      }
   }

   public final void p(long var1) {
      if (var1 <= 2147483647L) {
         int var3 = this.l;
         if (var1 <= (long)var3) {
            if (var1 < 0L) {
               Log.d("g1.b", "Limit underflow: Payloadsize is to little...");
               throw new i1.f("Payloadsize is to little...");
            }
         } else {
            Log.d("g1.b", String.format("Payload limit reached. Allowed: %d Current: %d", var3, var1));
            throw new i1.f("Payload limit reached.", var3);
         }
      } else {
         Log.d("g1.b", "Limit exedeed: Payloadsize is to big...");
         throw new i1.f("Payloadsize is to big...");
      }
   }

   @Override
   public final String toString() {
      String var2 = super.toString();
      String var1 = var2;
      if (this.b != null) {
         StringBuilder var3 = a.a.r(var2, " extension: ");
         var3.append(this.b.toString());
         var1 = var3.toString();
      }

      var2 = var1;
      if (this.f != null) {
         StringBuilder var4 = a.a.r(var1, " protocol: ");
         var4.append(((m1.b)this.f).a);
         var2 = var4.toString();
      }

      StringBuilder var5 = a.a.r(var2, " max frame size: ");
      var5.append(this.l);
      return var5.toString();
   }
}
