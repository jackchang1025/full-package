package b1;

import android.util.Log;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.PrivateKey;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public final class d implements Closeable {
   public static final int w = 0;
   public final Socket a;
   public final String b;
   public final int c;
   public final int d;
   public int e;
   public final InputStream f;
   public final OutputStream g;
   public volatile InputStream h;
   public volatile OutputStream i;
   public final Thread j;
   public volatile boolean k;
   public volatile boolean l;
   public volatile boolean m;
   public volatile boolean n;
   public volatile int o;
   public volatile int p;
   public final k q;
   public volatile String r = "Unknown Device";
   public volatile boolean s;
   public final ConcurrentHashMap t;
   public volatile boolean u = false;
   public final Object v = new Object();

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public d(String var1, int var2, k var3, int var4) {
      Objects.requireNonNull(var1);
      this.b = var1;
      this.c = var2;
      this.d = var4;
      byte[] var6 = b1.g.a;
      int var5;
      if (var4 >= 28) {
         var5 = 16777217;
      } else {
         var5 = 16777216;
      }

      this.p = var5;
      if (var4 >= 28) {
         var4 = 1048576;
      } else if (var4 >= 24) {
         var4 = 262144;
      } else {
         var4 = 4096;
      }

      this.o = var4;
      Objects.requireNonNull(var3);
      this.q = var3;

      try {
         var9 = new Socket(var1, var2);
         this.a = var9;
         var9.setKeepAlive(true);
      } catch (Throwable var8) {
         var8.printStackTrace();
         throw (IOException)new IOException().initCause(var8);
      }

      this.f = var9.getInputStream();
      this.g = var9.getOutputStream();
      var9.setTcpNoDelay(true);
      this.t = new ConcurrentHashMap();
      this.e = 0;
      this.j = new Thread(new o.a(this, 9));
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final void A(byte[] var1) {
      Object var3 = this.v;
      synchronized (var3){} // $VF: monitorenter 

      Throwable var10000;
      label145: {
         OutputStream var2;
         label139: {
            try {
               if (this.u) {
                  var2 = this.i;
                  Objects.requireNonNull(var2);
                  break label139;
               }
            } catch (Throwable var23) {
               var10000 = var23;
               boolean var10001 = false;
               break label145;
            }

            try {
               var2 = this.g;
            } catch (Throwable var22) {
               var10000 = var22;
               boolean var25 = false;
               break label145;
            }
         }

         label130:
         try {
            var2.write(var1);
            var2.flush();
            // $VF: monitorexit
            return;
         } catch (Throwable var21) {
            var10000 = var21;
            boolean var26 = false;
            break label130;
         }
      }

      while (true) {
         Throwable var24 = var10000;

         try {
            // $VF: monitorexit
            throw var24;
         } catch (Throwable var20) {
            var10000 = var20;
            boolean var27 = false;
            continue;
         }
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final boolean B(long var1, TimeUnit var3) {
      synchronized (this){} // $VF: monitorenter 

      Throwable var10000;
      label408: {
         try {
            long var4 = System.currentTimeMillis();
            Objects.requireNonNull(var3);
            var1 = var4 + var3.toMillis(var1);
         } catch (Throwable var58) {
            var10000 = var58;
            boolean var10001 = false;
            break label408;
         }

         while (true) {
            try {
               if (this.n || !this.k || var1 - System.currentTimeMillis() <= 0L) {
                  break;
               }

               this.wait(var1 - System.currentTimeMillis());
            } catch (Throwable var61) {
               var10000 = var61;
               boolean var66 = false;
               break label408;
            }
         }

         label410: {
            try {
               if (this.n) {
                  break label410;
               }

               if (this.k) {
                  // $VF: monitorexit
                  return false;
               }
            } catch (Throwable var60) {
               var10000 = var60;
               boolean var67 = false;
               break label408;
            }

            try {
               if (this.m) {
                  Log.e("d", "mAuthorisationFailed");
                  c var64 = new c();
                  throw var64;
               }
            } catch (Throwable var59) {
               var10000 = var59;
               boolean var68 = false;
               break label408;
            }

            try {
               Log.e("d", "Connection failed");
               IOException var63 = new IOException("Connection failed");
               throw var63;
            } catch (Throwable var56) {
               var10000 = var56;
               boolean var69 = false;
               break label408;
            }
         }

         label377:
         try {
            // $VF: monitorexit
            return true;
         } catch (Throwable var57) {
            var10000 = var57;
            boolean var70 = false;
            break label377;
         }
      }

      while (true) {
         Throwable var65 = var10000;

         try {
            // $VF: monitorexit
            throw var65;
         } catch (Throwable var55) {
            var10000 = var55;
            boolean var71 = false;
            continue;
         }
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final void close() {
      Exception var10000;
      label48: {
         PrivateKey var10;
         try {
            Socket var1 = this.a;
            Objects.requireNonNull(var1);
            var1.close();
            Thread var7 = this.j;
            Objects.requireNonNull(var7);
            var7.interrupt();
            Thread var8 = this.j;
            Objects.requireNonNull(var8);
            var8.join();
            this.x();
            k var9 = this.q;
            Objects.requireNonNull(var9);
            var10 = var9.a;
         } catch (Exception var6) {
            var10000 = var6;
            boolean var10001 = false;
            break label48;
         }

         try {
            if (!var10.isDestroyed()) {
               var10.destroy();
            }
         } catch (Exception var5) {
            Exception var11 = var5;

            try {
               a1.q.s("b1.k", var11);
            } catch (Exception var4) {
               var10000 = var4;
               boolean var17 = false;
               break label48;
            }
         }

         try {
            InputStream var12 = this.f;
            Objects.requireNonNull(var12);
            var12.close();
            OutputStream var13 = this.g;
            Objects.requireNonNull(var13);
            var13.close();
            if (this.h != null) {
               InputStream var14 = this.h;
               Objects.requireNonNull(var14);
               var14.close();
            }
         } catch (Exception var3) {
            var10000 = var3;
            boolean var18 = false;
            break label48;
         }

         try {
            if (this.i != null) {
               OutputStream var16 = this.i;
               Objects.requireNonNull(var16);
               var16.close();
            }

            return;
         } catch (Exception var2) {
            var10000 = var2;
            boolean var19 = false;
         }
      }

      Exception var15 = var10000;
      a1.q.s("d", var15);
   }

   public final void x() {
      ConcurrentHashMap var1 = this.t;
      if (!var1.isEmpty()) {
         for (h var3 : var1.values()) {
            try {
               var3.close();
            } catch (IOException var4) {
               a1.q.s("d", var4);
            }
         }
      }

      var1.clear();
   }

   public final boolean y(long var1, TimeUnit var3) {
      if (!this.n) {
         int var4 = this.d;
         byte[] var6 = b1.g.a;
         int var5;
         if (var4 >= 28) {
            var5 = 16777217;
         } else {
            var5 = 16777216;
         }

         if (var4 >= 28) {
            var4 = 1048576;
         } else if (var4 >= 24) {
            var4 = 262144;
         } else {
            var4 = 4096;
         }

         this.A(b1.g.b(1314410051, var5, b1.g.a, var4));
         this.k = true;
         this.l = false;
         this.m = false;
         this.j.start();
         Objects.requireNonNull(var3);
         return this.B(var1, var3);
      } else {
         Log.e("d", "Already connected");
         throw new IllegalStateException("Already connected");
      }
   }

   public final h z(String[] param1, int param2) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot read field "id" because the return value of "org.jetbrains.java.decompiler.modules.decompiler.flow.FlattenStatementsHelper.getDirectNode(org.jetbrains.java.decompiler.modules.decompiler.stats.Statement)" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:179)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:112)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.getFinallyInformation(FinallyProcessor.java:136)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:85)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 000: iload 2
      // 001: bipush 1
      // 002: if_icmplt 42b
      // 005: iload 2
      // 006: bipush 20
      // 008: if_icmpgt 42b
      // 00b: iload 2
      // 00c: tableswitch 96 1 20 244 237 230 223 216 209 202 195 188 181 174 167 160 153 146 139 132 125 118 111
      // 06c: new java/lang/IllegalArgumentException
      // 06f: dup
      // 070: ldc_w "Invalid service: "
      // 073: iload 2
      // 074: invokestatic a/a.g (Ljava/lang/String;I)Ljava/lang/String;
      // 077: invokespecial java/lang/IllegalArgumentException.<init> (Ljava/lang/String;)V
      // 07a: athrow
      // 07b: ldc_w "pull:"
      // 07e: astore 3
      // 07f: goto 104
      // 082: ldc_w "push:"
      // 085: astore 3
      // 086: goto 104
      // 089: ldc_w "uninstall:"
      // 08c: astore 3
      // 08d: goto 104
      // 090: ldc_w "install:"
      // 093: astore 3
      // 094: goto 104
      // 097: ldc_w "tcpip:"
      // 09a: astore 3
      // 09b: goto 104
      // 09e: ldc_w "restore:"
      // 0a1: astore 3
      // 0a2: goto 104
      // 0a5: ldc_w "backup:"
      // 0a8: astore 3
      // 0a9: goto 104
      // 0ac: ldc_w "reverse:"
      // 0af: astore 3
      // 0b0: goto 104
      // 0b3: ldc_w "sync:"
      // 0b6: astore 3
      // 0b7: goto 104
      // 0ba: ldc_w "track-jdwp"
      // 0bd: astore 3
      // 0be: goto 104
      // 0c1: ldc_w "jdwp:"
      // 0c4: astore 3
      // 0c5: goto 104
      // 0c8: ldc_w "framebuffer:"
      // 0cb: astore 3
      // 0cc: goto 104
      // 0cf: ldc_w "localfilesystem:"
      // 0d2: astore 3
      // 0d3: goto 104
      // 0d6: ldc_w "localabstract:"
      // 0d9: astore 3
      // 0da: goto 104
      // 0dd: ldc_w "localreserved:"
      // 0e0: astore 3
      // 0e1: goto 104
      // 0e4: ldc_w "local:"
      // 0e7: astore 3
      // 0e8: goto 104
      // 0eb: ldc_w "tcp:"
      // 0ee: astore 3
      // 0ef: goto 104
      // 0f2: ldc_w "dev:"
      // 0f5: astore 3
      // 0f6: goto 104
      // 0f9: ldc_w "remount:"
      // 0fc: astore 3
      // 0fd: goto 104
      // 100: ldc_w "shell:"
      // 103: astore 3
      // 104: new java/lang/StringBuilder
      // 107: dup
      // 108: aload 3
      // 109: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 10c: astore 3
      // 10d: iload 2
      // 10e: tableswitch 94 1 20 609 614 549 492 437 437 437 437 418 363 418 418 216 197 418 173 154 135 116 97
      // 16c: goto 382
      // 16f: aload 1
      // 170: arraylength
      // 171: ifeq 177
      // 174: goto 374
      // 177: new java/lang/IllegalArgumentException
      // 17a: dup
      // 17b: ldc_w "pull file name must be specified."
      // 17e: invokespecial java/lang/IllegalArgumentException.<init> (Ljava/lang/String;)V
      // 181: athrow
      // 182: aload 1
      // 183: arraylength
      // 184: ifeq 18a
      // 187: goto 374
      // 18a: new java/lang/IllegalArgumentException
      // 18d: dup
      // 18e: ldc_w "push file name must be specified."
      // 191: invokespecial java/lang/IllegalArgumentException.<init> (Ljava/lang/String;)V
      // 194: athrow
      // 195: aload 1
      // 196: arraylength
      // 197: ifeq 19d
      // 19a: goto 374
      // 19d: new java/lang/IllegalArgumentException
      // 1a0: dup
      // 1a1: ldc_w "package name must be specified."
      // 1a4: invokespecial java/lang/IllegalArgumentException.<init> (Ljava/lang/String;)V
      // 1a7: athrow
      // 1a8: aload 1
      // 1a9: arraylength
      // 1aa: ifeq 1b0
      // 1ad: goto 374
      // 1b0: new java/lang/IllegalArgumentException
      // 1b3: dup
      // 1b4: ldc_w "apk file name must be specified."
      // 1b7: invokespecial java/lang/IllegalArgumentException.<init> (Ljava/lang/String;)V
      // 1ba: athrow
      // 1bb: aload 1
      // 1bc: arraylength
      // 1bd: bipush 1
      // 1be: if_icmpne 1c8
      // 1c1: aload 1
      // 1c2: bipush 0
      // 1c3: aaload
      // 1c4: astore 1
      // 1c5: goto 37c
      // 1c8: new java/lang/IllegalArgumentException
      // 1cb: dup
      // 1cc: ldc_w "Invalid number of arguments supplied."
      // 1cf: invokespecial java/lang/IllegalArgumentException.<init> (Ljava/lang/String;)V
      // 1d2: athrow
      // 1d3: aload 1
      // 1d4: arraylength
      // 1d5: ifeq 1db
      // 1d8: goto 374
      // 1db: new java/lang/IllegalArgumentException
      // 1de: dup
      // 1df: ldc_w "At least one package must be specified or use -shared/-all."
      // 1e2: invokespecial java/lang/IllegalArgumentException.<init> (Ljava/lang/String;)V
      // 1e5: athrow
      // 1e6: aload 1
      // 1e7: arraylength
      // 1e8: ifeq 26e
      // 1eb: aload 1
      // 1ec: arraylength
      // 1ed: bipush 1
      // 1ee: if_icmpne 254
      // 1f1: aload 1
      // 1f2: bipush 0
      // 1f3: aaload
      // 1f4: astore 4
      // 1f6: aload 4
      // 1f8: ifnull 249
      // 1fb: ldc_w "list-forward"
      // 1fe: aload 4
      // 200: invokevirtual java/lang/String.equals (Ljava/lang/Object;)Z
      // 203: ifne 242
      // 206: ldc_w "killforward-all"
      // 209: aload 1
      // 20a: bipush 0
      // 20b: aaload
      // 20c: invokevirtual java/lang/String.equals (Ljava/lang/Object;)Z
      // 20f: ifeq 215
      // 212: goto 242
      // 215: aload 1
      // 216: bipush 0
      // 217: aaload
      // 218: ldc_w "forward:"
      // 21b: invokevirtual java/lang/String.startsWith (Ljava/lang/String;)Z
      // 21e: ifne 23b
      // 221: aload 1
      // 222: bipush 0
      // 223: aaload
      // 224: ldc_w "killforward:"
      // 227: invokevirtual java/lang/String.startsWith (Ljava/lang/String;)Z
      // 22a: ifeq 230
      // 22d: goto 23b
      // 230: new java/lang/IllegalArgumentException
      // 233: dup
      // 234: ldc_w "Invalid forward command."
      // 237: invokespecial java/lang/IllegalArgumentException.<init> (Ljava/lang/String;)V
      // 23a: athrow
      // 23b: aload 1
      // 23c: bipush 0
      // 23d: aaload
      // 23e: astore 1
      // 23f: goto 37c
      // 242: aload 1
      // 243: bipush 0
      // 244: aaload
      // 245: astore 1
      // 246: goto 37c
      // 249: new java/lang/IllegalArgumentException
      // 24c: dup
      // 24d: ldc_w "Forward command is empty"
      // 250: invokespecial java/lang/IllegalArgumentException.<init> (Ljava/lang/String;)V
      // 253: athrow
      // 254: new java/lang/IllegalArgumentException
      // 257: dup
      // 258: new java/lang/StringBuilder
      // 25b: dup
      // 25c: ldc_w "Service expects exactly one argument, "
      // 25f: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 262: aload 1
      // 263: arraylength
      // 264: ldc_w " supplied."
      // 267: invokestatic a/a.m (Ljava/lang/StringBuilder;ILjava/lang/String;)Ljava/lang/String;
      // 26a: invokespecial java/lang/IllegalArgumentException.<init> (Ljava/lang/String;)V
      // 26d: athrow
      // 26e: new java/lang/IllegalArgumentException
      // 271: dup
      // 272: ldc_w "Forward command must be specified."
      // 275: invokespecial java/lang/IllegalArgumentException.<init> (Ljava/lang/String;)V
      // 278: athrow
      // 279: aload 1
      // 27a: arraylength
      // 27b: ifeq 2a5
      // 27e: aload 1
      // 27f: arraylength
      // 280: bipush 1
      // 281: if_icmpne 28b
      // 284: aload 1
      // 285: bipush 0
      // 286: aaload
      // 287: astore 1
      // 288: goto 342
      // 28b: new java/lang/IllegalArgumentException
      // 28e: dup
      // 28f: new java/lang/StringBuilder
      // 292: dup
      // 293: ldc_w "Service expects exactly one argument, "
      // 296: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 299: aload 1
      // 29a: arraylength
      // 29b: ldc_w " supplied."
      // 29e: invokestatic a/a.m (Ljava/lang/StringBuilder;ILjava/lang/String;)Ljava/lang/String;
      // 2a1: invokespecial java/lang/IllegalArgumentException.<init> (Ljava/lang/String;)V
      // 2a4: athrow
      // 2a5: new java/lang/IllegalArgumentException
      // 2a8: dup
      // 2a9: ldc_w "PID must be specified."
      // 2ac: invokespecial java/lang/IllegalArgumentException.<init> (Ljava/lang/String;)V
      // 2af: athrow
      // 2b0: aload 1
      // 2b1: arraylength
      // 2b2: ifne 2b8
      // 2b5: goto 382
      // 2b8: new java/lang/IllegalArgumentException
      // 2bb: dup
      // 2bc: ldc_w "Service expects no arguments."
      // 2bf: invokespecial java/lang/IllegalArgumentException.<init> (Ljava/lang/String;)V
      // 2c2: athrow
      // 2c3: aload 1
      // 2c4: arraylength
      // 2c5: ifeq 2ef
      // 2c8: aload 1
      // 2c9: arraylength
      // 2ca: bipush 1
      // 2cb: if_icmpne 2d5
      // 2ce: aload 1
      // 2cf: bipush 0
      // 2d0: aaload
      // 2d1: astore 1
      // 2d2: goto 342
      // 2d5: new java/lang/IllegalArgumentException
      // 2d8: dup
      // 2d9: new java/lang/StringBuilder
      // 2dc: dup
      // 2dd: ldc_w "Service expects exactly one argument, "
      // 2e0: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 2e3: aload 1
      // 2e4: arraylength
      // 2e5: ldc_w " supplied."
      // 2e8: invokestatic a/a.m (Ljava/lang/StringBuilder;ILjava/lang/String;)Ljava/lang/String;
      // 2eb: invokespecial java/lang/IllegalArgumentException.<init> (Ljava/lang/String;)V
      // 2ee: athrow
      // 2ef: new java/lang/IllegalArgumentException
      // 2f2: dup
      // 2f3: ldc_w "Path must be specified."
      // 2f6: invokespecial java/lang/IllegalArgumentException.<init> (Ljava/lang/String;)V
      // 2f9: athrow
      // 2fa: aload 1
      // 2fb: arraylength
      // 2fc: ifeq 328
      // 2ff: aload 1
      // 300: arraylength
      // 301: bipush 1
      // 302: if_icmpne 30c
      // 305: aload 1
      // 306: bipush 0
      // 307: aaload
      // 308: astore 1
      // 309: goto 37c
      // 30c: aload 1
      // 30d: arraylength
      // 30e: bipush 2
      // 30f: if_icmpne 31d
      // 312: ldc_w ":"
      // 315: aload 1
      // 316: invokestatic android/text/TextUtils.join (Ljava/lang/CharSequence;[Ljava/lang/Object;)Ljava/lang/String;
      // 319: astore 1
      // 31a: goto 37c
      // 31d: new java/lang/IllegalArgumentException
      // 320: dup
      // 321: ldc_w "Invalid number of arguments supplied."
      // 324: invokespecial java/lang/IllegalArgumentException.<init> (Ljava/lang/String;)V
      // 327: athrow
      // 328: new java/lang/IllegalArgumentException
      // 32b: dup
      // 32c: ldc_w "Port number must be specified."
      // 32f: invokespecial java/lang/IllegalArgumentException.<init> (Ljava/lang/String;)V
      // 332: athrow
      // 333: aload 1
      // 334: arraylength
      // 335: ifeq 364
      // 338: aload 1
      // 339: arraylength
      // 33a: bipush 1
      // 33b: if_icmpne 34a
      // 33e: aload 1
      // 33f: bipush 0
      // 340: aaload
      // 341: astore 1
      // 342: aload 1
      // 343: invokestatic java/util/Objects.requireNonNull (Ljava/lang/Object;)Ljava/lang/Object;
      // 346: pop
      // 347: goto 37c
      // 34a: new java/lang/IllegalArgumentException
      // 34d: dup
      // 34e: new java/lang/StringBuilder
      // 351: dup
      // 352: ldc_w "Service expects exactly one argument, "
      // 355: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 358: aload 1
      // 359: arraylength
      // 35a: ldc_w " supplied."
      // 35d: invokestatic a/a.m (Ljava/lang/StringBuilder;ILjava/lang/String;)Ljava/lang/String;
      // 360: invokespecial java/lang/IllegalArgumentException.<init> (Ljava/lang/String;)V
      // 363: athrow
      // 364: new java/lang/IllegalArgumentException
      // 367: dup
      // 368: ldc_w "File name must be specified."
      // 36b: invokespecial java/lang/IllegalArgumentException.<init> (Ljava/lang/String;)V
      // 36e: athrow
      // 36f: aload 1
      // 370: arraylength
      // 371: ifle 382
      // 374: ldc_w " "
      // 377: aload 1
      // 378: invokestatic android/text/TextUtils.join (Ljava/lang/CharSequence;[Ljava/lang/Object;)Ljava/lang/String;
      // 37b: astore 1
      // 37c: aload 3
      // 37d: aload 1
      // 37e: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 381: pop
      // 382: aload 3
      // 383: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 386: astore 3
      // 387: aload 0
      // 388: getfield b1/d.e I
      // 38b: bipush 1
      // 38c: iadd
      // 38d: istore 2
      // 38e: aload 0
      // 38f: iload 2
      // 390: putfield b1/d.e I
      // 393: aload 0
      // 394: getfield b1/d.k Z
      // 397: ifeq 420
      // 39a: aload 0
      // 39b: ldc2_w 9223372036854775807
      // 39e: getstatic java/util/concurrent/TimeUnit.MILLISECONDS Ljava/util/concurrent/TimeUnit;
      // 3a1: invokevirtual b1/d.B (JLjava/util/concurrent/TimeUnit;)Z
      // 3a4: pop
      // 3a5: new b1/h
      // 3a8: dup
      // 3a9: aload 0
      // 3aa: iload 2
      // 3ab: invokespecial b1/h.<init> (Lb1/d;I)V
      // 3ae: astore 1
      // 3af: aload 0
      // 3b0: getfield b1/d.t Ljava/util/concurrent/ConcurrentHashMap;
      // 3b3: iload 2
      // 3b4: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 3b7: aload 1
      // 3b8: invokevirtual java/util/concurrent/ConcurrentHashMap.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
      // 3bb: pop
      // 3bc: aload 3
      // 3bd: invokestatic java/util/Objects.requireNonNull (Ljava/lang/Object;)Ljava/lang/Object;
      // 3c0: pop
      // 3c1: getstatic b1/g.a [B
      // 3c4: astore 4
      // 3c6: aload 3
      // 3c7: invokevirtual java/lang/String.length ()I
      // 3ca: bipush 1
      // 3cb: iadd
      // 3cc: invokestatic java/nio/ByteBuffer.allocate (I)Ljava/nio/ByteBuffer;
      // 3cf: astore 4
      // 3d1: aload 4
      // 3d3: aload 3
      // 3d4: invokestatic com/guard/wallet/utils/g.Y (Ljava/lang/String;)[B
      // 3d7: invokevirtual java/nio/ByteBuffer.put ([B)Ljava/nio/ByteBuffer;
      // 3da: pop
      // 3db: aload 4
      // 3dd: bipush 0
      // 3de: invokevirtual java/nio/ByteBuffer.put (B)Ljava/nio/ByteBuffer;
      // 3e1: pop
      // 3e2: aload 0
      // 3e3: ldc_w 1313165391
      // 3e6: iload 2
      // 3e7: aload 4
      // 3e9: invokevirtual java/nio/ByteBuffer.array ()[B
      // 3ec: bipush 0
      // 3ed: invokestatic b1/g.b (II[BI)[B
      // 3f0: invokevirtual b1/d.A ([B)V
      // 3f3: aload 1
      // 3f4: monitorenter
      // 3f5: aload 1
      // 3f6: invokevirtual java/lang/Object.wait ()V
      // 3f9: aload 1
      // 3fa: monitorexit
      // 3fb: aload 1
      // 3fc: getfield b1/h.g Z
      // 3ff: ifne 404
      // 402: aload 1
      // 403: areturn
      // 404: aload 0
      // 405: getfield b1/d.t Ljava/util/concurrent/ConcurrentHashMap;
      // 408: iload 2
      // 409: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 40c: invokevirtual java/util/concurrent/ConcurrentHashMap.remove (Ljava/lang/Object;)Ljava/lang/Object;
      // 40f: pop
      // 410: new java/net/ConnectException
      // 413: dup
      // 414: ldc_w "Stream open actively rejected by remote peer."
      // 417: invokespecial java/net/ConnectException.<init> (Ljava/lang/String;)V
      // 41a: athrow
      // 41b: astore 3
      // 41c: aload 1
      // 41d: monitorexit
      // 41e: aload 3
      // 41f: athrow
      // 420: new java/lang/IllegalStateException
      // 423: dup
      // 424: ldc_w "connect() must be called first"
      // 427: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 42a: athrow
      // 42b: new java/lang/IllegalArgumentException
      // 42e: dup
      // 42f: ldc_w "Invalid service: "
      // 432: iload 2
      // 433: invokestatic a/a.g (Ljava/lang/String;I)Ljava/lang/String;
      // 436: invokespecial java/lang/IllegalArgumentException.<init> (Ljava/lang/String;)V
      // 439: athrow
   }
}
