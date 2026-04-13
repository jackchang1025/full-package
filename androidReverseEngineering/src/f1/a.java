package f1;

import a1.q;
import com.guard.wallet.http.h;
import com.guard.wallet.msg.BridgeMessage;
import e1.b;
import e1.d;
import i1.e;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import l1.c;
import l1.f;

public abstract class a extends e1.a implements Runnable, b {
   public static final int t = 0;
   public final URI i;
   public final d j;
   public Socket k;
   public OutputStream l;
   public final Proxy m;
   public Thread n;
   public Thread o;
   public final CountDownLatch p;
   public final CountDownLatch q;
   public final int r;
   public final h s;

   public a(URI var1) {
      g1.b var2 = new g1.b();
      super();
      this.i = null;
      this.j = null;
      this.k = null;
      this.m = Proxy.NO_PROXY;
      this.p = new CountDownLatch(1);
      this.q = new CountDownLatch(1);
      this.r = 0;
      this.s = null;
      if (var1 != null) {
         this.i = var1;
         this.s = new h(this, 10);
         this.r = 0;
         super.b = false;
         super.c = false;
         this.j = new d(this, var2);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public final void A() {
      this.k = ((SSLSocketFactory)SSLSocketFactory.getDefault()).createSocket(this.k, this.i.getHost(), this.v(), true);
   }

   @Override
   public final void a(byte[] var1) {
      this.j.a(var1);
   }

   @Override
   public final void b(int var1, String var2) {
      this.j.i(var2, false, var1);
   }

   @Override
   public final void c(String var1) {
      this.j.c(var1);
   }

   @Override
   public final String d() {
      return this.i.getPath();
   }

   @Override
   public final void e(int var1) {
      this.j.e(1001);
   }

   @Override
   public final void f(String var1) {
      this.j.k(var1, false, 1006);
   }

   @Override
   public final InetSocketAddress g() {
      return this.j.g();
   }

   @Override
   public final InetSocketAddress h(b var1) {
      Socket var2 = this.k;
      return var2 != null ? (InetSocketAddress)var2.getRemoteSocketAddress() : null;
   }

   @Override
   public final void i(b param1, int param2, String param3, boolean param4) {
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
      // 01: getfield e1/a.h Ljava/lang/Object;
      // 04: astore 1
      // 05: aload 1
      // 06: monitorenter
      // 07: aload 0
      // 08: getfield e1/a.d Ljava/util/concurrent/ScheduledExecutorService;
      // 0b: ifnonnull 15
      // 0e: aload 0
      // 0f: getfield e1/a.e Ljava/util/concurrent/ScheduledFuture;
      // 12: ifnull 48
      // 15: ldc "e1.a"
      // 17: ldc "Connection lost timer stopped"
      // 19: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 1c: pop
      // 1d: aload 0
      // 1e: getfield e1/a.d Ljava/util/concurrent/ScheduledExecutorService;
      // 21: astore 3
      // 22: aload 3
      // 23: ifnull 32
      // 26: aload 3
      // 27: invokeinterface java/util/concurrent/ExecutorService.shutdownNow ()Ljava/util/List; 1
      // 2c: pop
      // 2d: aload 0
      // 2e: aconst_null
      // 2f: putfield e1/a.d Ljava/util/concurrent/ScheduledExecutorService;
      // 32: aload 0
      // 33: getfield e1/a.e Ljava/util/concurrent/ScheduledFuture;
      // 36: astore 3
      // 37: aload 3
      // 38: ifnull 48
      // 3b: aload 3
      // 3c: bipush 0
      // 3d: invokeinterface java/util/concurrent/Future.cancel (Z)Z 2
      // 42: pop
      // 43: aload 0
      // 44: aconst_null
      // 45: putfield e1/a.e Ljava/util/concurrent/ScheduledFuture;
      // 48: aload 1
      // 49: monitorexit
      // 4a: aload 0
      // 4b: getfield f1/a.n Ljava/lang/Thread;
      // 4e: astore 1
      // 4f: aload 1
      // 50: ifnull 57
      // 53: aload 1
      // 54: invokevirtual java/lang/Thread.interrupt ()V
      // 57: aload 0
      // 58: checkcast com/guard/wallet/bridge/a
      // 5b: astore 1
      // 5c: aload 1
      // 5d: getfield com/guard/wallet/bridge/a.w Ljava/util/concurrent/atomic/AtomicBoolean;
      // 60: bipush 0
      // 61: invokevirtual java/util/concurrent/atomic/AtomicBoolean.set (Z)V
      // 64: aload 1
      // 65: getfield com/guard/wallet/bridge/a.u Ljava/lang/String;
      // 68: invokestatic a1/q.g (Ljava/lang/String;)V
      // 6b: aload 0
      // 6c: getfield f1/a.p Ljava/util/concurrent/CountDownLatch;
      // 6f: invokevirtual java/util/concurrent/CountDownLatch.countDown ()V
      // 72: aload 0
      // 73: getfield f1/a.q Ljava/util/concurrent/CountDownLatch;
      // 76: invokevirtual java/util/concurrent/CountDownLatch.countDown ()V
      // 79: return
      // 7a: astore 3
      // 7b: aload 1
      // 7c: monitorexit
      // 7d: aload 3
      // 7e: athrow
   }

   @Override
   public final void j() {
   }

   @Override
   public final void k() {
   }

   @Override
   public final void l(b var1, Exception var2) {
      this.w(var2);
   }

   @Override
   public final void m() {
   }

   @Override
   public final void n(b var1, String var2) {
      this.x(var2);
   }

   @Override
   public final void o(b var1, l1.b var2) {
      this.s();
      f var3 = (f)var2;
      com.guard.wallet.bridge.a var4 = (com.guard.wallet.bridge.a)this;
      var4.w.set(true);
      BridgeMessage var5 = var4.v;
      if (var5 != null) {
         var4.c(com.guard.wallet.utils.h.N(var5));
      }

      this.p.countDown();
   }

   @Override
   public final void p(b var1) {
   }

   @Override
   public final Collection r() {
      return Collections.singletonList(this.j);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final void run() {
      d var6 = this.j;

      InputStream var40;
      label172: {
         label171: {
            Exception var47;
            label176: {
               label177: {
                  boolean var3;
                  boolean var4;
                  try {
                     var3 = this.y();
                     this.k.setTcpNoDelay(super.b);
                     this.k.setReuseAddress(super.c);
                     var4 = this.k.isConnected();
                  } catch (Exception var31) {
                     var47 = var31;
                     boolean var50 = false;
                     break label176;
                  } catch (InternalError var32) {
                     var10000 = var32;
                     boolean var10001 = false;
                     break label177;
                  }

                  URI var7 = this.i;
                  if (!var4) {
                     InetSocketAddress var5;
                     if (this.s == null) {
                        try {
                           var5 = InetSocketAddress.createUnresolved(var7.getHost(), this.v());
                        } catch (Exception var29) {
                           var47 = var29;
                           boolean var52 = false;
                           break label176;
                        } catch (InternalError var30) {
                           var10000 = var30;
                           boolean var51 = false;
                           break label177;
                        }
                     } else {
                        try {
                           var5 = new InetSocketAddress(InetAddress.getByName(var7.getHost()), this.v());
                        } catch (Exception var27) {
                           var47 = var27;
                           boolean var54 = false;
                           break label176;
                        } catch (InternalError var28) {
                           var10000 = var28;
                           boolean var53 = false;
                           break label177;
                        }
                     }

                     try {
                        this.k.connect(var5, this.r);
                     } catch (Exception var25) {
                        var47 = var25;
                        boolean var56 = false;
                        break label176;
                     } catch (InternalError var26) {
                        var10000 = var26;
                        boolean var55 = false;
                        break label177;
                     }
                  }

                  if (var3) {
                     try {
                        if ("wss".equals(var7.getScheme())) {
                           this.A();
                        }
                     } catch (Exception var23) {
                        var47 = var23;
                        boolean var58 = false;
                        break label176;
                     } catch (InternalError var24) {
                        var10000 = var24;
                        boolean var57 = false;
                        break label177;
                     }
                  }

                  try {
                     Socket var36 = this.k;
                     if (var36 instanceof SSLSocket) {
                        SSLSocket var37 = (SSLSocket)var36;
                        SSLParameters var43 = var37.getSSLParameters();
                        var43.setEndpointIdentificationAlgorithm("HTTPS");
                        var37.setSSLParameters(var43);
                     }
                  } catch (Exception var21) {
                     var47 = var21;
                     boolean var60 = false;
                     break label176;
                  } catch (InternalError var22) {
                     var10000 = var22;
                     boolean var59 = false;
                     break label177;
                  }

                  try {
                     var40 = this.k.getInputStream();
                     this.l = this.k.getOutputStream();
                     this.z();
                     break label172;
                  } catch (Exception var19) {
                     var47 = var19;
                     boolean var62 = false;
                     break label176;
                  } catch (InternalError var20) {
                     var10000 = var20;
                     boolean var61 = false;
                  }
               }

               InternalError var38 = var10000;
               if (var38.getCause() instanceof InvocationTargetException && var38.getCause().getCause() instanceof IOException) {
                  var39 = (IOException)var38.getCause().getCause();
                  break label171;
               }

               throw var38;
            }

            var39 = var47;
         }

         this.w((Exception)var39);
         var6.k(var39.getMessage(), false, -1);
         return;
      }

      Thread var44 = this.n;
      if (var44 != null) {
         var44.interrupt();

         try {
            this.n.join();
         } catch (InterruptedException var8) {
         }
      }

      var44 = new Thread(new o.d(this, this, 13));
      this.n = var44;
      var44.setDaemon(super.g);
      this.n.start();
      byte[] var46 = new byte[65536];

      while (true) {
         RuntimeException var48;
         label178: {
            label179: {
               int var1;
               try {
                  var1 = var6.h;
               } catch (IOException var17) {
                  var49 = var17;
                  boolean var64 = false;
                  break label179;
               } catch (RuntimeException var18) {
                  var48 = var18;
                  boolean var63 = false;
                  break label178;
               }

               boolean var2 = true;
               boolean var33;
               if (var1 == 3) {
                  var33 = 1;
               } else {
                  var33 = 0;
               }

               if (!var33) {
                  label126: {
                     label125: {
                        try {
                           if (var6.h == 4) {
                              break label125;
                           }
                        } catch (IOException var15) {
                           var49 = var15;
                           boolean var66 = false;
                           break label179;
                        } catch (RuntimeException var16) {
                           var48 = var16;
                           boolean var65 = false;
                           break label178;
                        }

                        var33 = (boolean)0;
                        break label126;
                     }

                     var33 = var2;
                  }

                  if (!var33) {
                     try {
                        var33 = var40.read(var46);
                     } catch (IOException var13) {
                        var49 = var13;
                        boolean var68 = false;
                        break label179;
                     } catch (RuntimeException var14) {
                        var48 = var14;
                        boolean var67 = false;
                        break label178;
                     }

                     if (var33 != -1) {
                        try {
                           var6.m(ByteBuffer.wrap(var46, 0, var33));
                           continue;
                        } catch (IOException var9) {
                           var49 = var9;
                           boolean var72 = false;
                           break label179;
                        } catch (RuntimeException var10) {
                           var48 = var10;
                           boolean var71 = false;
                           break label178;
                        }
                     }
                  }
               }

               try {
                  var6.o();
                  break;
               } catch (IOException var11) {
                  var49 = var11;
                  boolean var70 = false;
               } catch (RuntimeException var12) {
                  var48 = var12;
                  boolean var69 = false;
                  break label178;
               }
            }

            IOException var42 = var49;
            if (var42 instanceof SSLException) {
               this.w(var42);
            }

            this.j.o();
            break;
         }

         RuntimeException var41 = var48;
         this.w(var41);
         var6.k(var41.getMessage(), false, 1006);
         break;
      }
   }

   public final void t() {
      if (this.n != null) {
         this.j.e(1000);
      }
   }

   public final void u() {
      if (this.o == null) {
         Thread var1 = new Thread(this);
         this.o = var1;
         var1.setDaemon(super.g);
         Thread var2 = this.o;
         StringBuilder var3 = new StringBuilder("WebSocketConnectReadThread-");
         var3.append(this.o.getId());
         var2.setName(var3.toString());
         this.o.start();
      } else {
         throw new IllegalStateException("WebSocketClient objects are not reuseable");
      }
   }

   public final int v() {
      URI var3 = this.i;
      int var1 = var3.getPort();
      String var5 = var3.getScheme();
      if ("wss".equals(var5)) {
         int var4 = var1;
         if (var1 == -1) {
            var4 = 443;
         }

         return var4;
      } else if ("ws".equals(var5)) {
         int var2 = var1;
         if (var1 == -1) {
            var2 = 80;
         }

         return var2;
      } else {
         throw new IllegalArgumentException(a.a.k("unknown scheme: ", var5));
      }
   }

   public abstract void w(Exception var1);

   public abstract void x(String var1);

   public final boolean y() {
      Proxy var3 = Proxy.NO_PROXY;
      boolean var1 = true;
      Proxy var2 = this.m;
      Socket var4;
      if (var2 != var3) {
         var4 = new Socket(var2);
      } else {
         Socket var5 = this.k;
         if (var5 != null) {
            if (var5.isClosed()) {
               throw new IOException();
            }

            var1 = false;
            return var1;
         }

         var4 = new Socket(var2);
      }

      this.k = var4;
      return var1;
   }

   public final void z() {
      String var2;
      URI var4;
      String var5;
      label76: {
         var4 = this.i;
         String var3 = var4.getRawPath();
         var5 = var4.getRawQuery();
         if (var3 != null) {
            var2 = var3;
            if (var3.length() != 0) {
               break label76;
            }
         }

         var2 = "/";
      }

      String var18 = var2;
      if (var5 != null) {
         StringBuilder var19 = new StringBuilder();
         var19.append(var2);
         var19.append('?');
         var19.append(var5);
         var18 = var19.toString();
      }

      int var1 = this.v();
      StringBuilder var24 = new StringBuilder();
      var24.append(var4.getHost());
      if (var1 != 80 && var1 != 443) {
         var2 = a.a.g(":", var1);
      } else {
         var2 = "";
      }

      var24.append(var2);
      var2 = var24.toString();
      c var22 = new c();
      if (var18 == null) {
         throw new IllegalArgumentException("http resource descriptor must not be null");
      } else {
         var22.b = var18;
         var22.b("Host", var2);
         d var20 = this.j;
         e1.c var25 = var20.c;
         g1.b var6 = var20.j;
         var6.getClass();
         var22.b("Upgrade", "websocket");
         var22.b("Connection", "Upgrade");
         byte[] var13 = new byte[16];
         var6.k.nextBytes(var13);

         try {
            var2 = a1.q.r(16, var13);
         } catch (IOException var10) {
            var2 = null;
         }

         var22.b("Sec-WebSocket-Key", var2);
         var22.b("Sec-WebSocket-Version", "13");
         StringBuilder var7 = new StringBuilder();
         Iterator var15 = var6.d.iterator();

         while (var15.hasNext()) {
            ((j1.a)var15.next()).getClass();
         }

         if (var7.length() != 0) {
            var22.b("Sec-WebSocket-Extensions", var7.toString());
         }

         StringBuilder var16 = new StringBuilder();

         for (m1.b var27 : var6.g) {
            if (var27.a.length() != 0) {
               if (var16.length() > 0) {
                  var16.append(", ");
               }

               var16.append(var27.a);
            }
         }

         if (var16.length() != 0) {
            var22.b("Sec-WebSocket-Protocol", var16.toString());
         }

         var20.m = var22;
         var20.q = var22.b;

         try {
            var25.getClass();
         } catch (i1.c var8) {
            throw new e("Handshake data rejected by client.");
         } catch (RuntimeException var9) {
            a1.q.s("Exception in startHandshake", var9);
            var25.l(var20, var9);
            StringBuilder var21 = new StringBuilder("rejected because of ");
            var21.append(var9);
            throw new e(var21.toString());
         }

         g1.b var17 = var20.j;
         l1.a var23 = var20.m;
         var17.getClass();
         var20.t(g1.a.b(var23));
      }
   }
}
