package n1;

import android.util.Log;
import e1.d;
import i1.h;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import p0.q;

public abstract class b extends e1.a implements Runnable {
   public static final int w = Runtime.getRuntime().availableProcessors();
   public final Collection i;
   public final InetSocketAddress j;
   public ServerSocketChannel k;
   public Selector l;
   public final List m;
   public Thread n;
   public final AtomicBoolean o;
   public final ArrayList p;
   public final LinkedList q;
   public final LinkedBlockingQueue r;
   public int s;
   public final AtomicInteger t;
   public final q u;
   public final int v;

   public b(InetSocketAddress var1) {
      HashSet var4 = new HashSet();
      super();
      int var2 = 0;
      this.o = new AtomicBoolean(false);
      this.s = 0;
      this.t = new AtomicInteger(0);
      this.u = new q(0);
      this.v = -1;
      int var3 = w;
      if (var3 < 1) {
         throw new IllegalArgumentException("address and connectionscontainer must not be null and you need at least 1 decoder");
      } else {
         this.m = Collections.emptyList();
         this.j = var1;
         this.i = var4;
         super.b = false;
         super.c = false;
         this.q = new LinkedList();
         this.p = new ArrayList(var3);

         for (this.r = new LinkedBlockingQueue(); var2 < var3; var2++) {
            a var5 = new a(this);
            this.p.add(var5);
         }
      }
   }

   public static void A(SelectionKey var0, e1.b var1, IOException var2) {
      if (var0 != null) {
         var0.cancel();
      }

      if (var1 != null) {
         var1.f(var2.getMessage());
      } else if (var0 != null) {
         SelectableChannel var4 = var0.channel();
         if (var4 != null && var4.isOpen()) {
            try {
               var4.close();
            } catch (IOException var3) {
            }

            a1.q.s("Connection closed because of exception", var2);
         }
      }
   }

   public static void y(SelectionKey var0) {
      d var1 = (d)var0.attachment();

      try {
         if (a1.q.c(var1, var1.e) && var0.isValid()) {
            var0.interestOps(1);
         }
      } catch (IOException var2) {
         throw new h(var1, var2);
      }
   }

   public abstract void B(e1.b var1);

   public abstract void C(Exception var1);

   public final void D(ByteBuffer var1) {
      LinkedBlockingQueue var2 = this.r;
      if (var2.size() <= this.t.intValue()) {
         var2.put(var1);
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final boolean E(e1.b var1) {
      Collection var3 = this.i;
      synchronized (var3){} // $VF: monitorenter 

      boolean var2;
      label182: {
         Throwable var10000;
         label184: {
            label183: {
               try {
                  if (this.i.contains(var1)) {
                     var2 = this.i.remove(var1);
                     break label183;
                  }
               } catch (Throwable var23) {
                  var10000 = var23;
                  boolean var10001 = false;
                  break label184;
               }

               try {
                  Log.d("n1.b", "Removing connection which is not in the connections collection! Possible no handshake received! {}");
               } catch (Throwable var22) {
                  var10000 = var22;
                  boolean var25 = false;
                  break label184;
               }

               var2 = false;
            }

            label165:
            try {
               // $VF: monitorexit
               break label182;
            } catch (Throwable var21) {
               var10000 = var21;
               boolean var26 = false;
               break label165;
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

      if (this.o.get() && this.i.isEmpty()) {
         this.n.interrupt();
      }

      return var2;
   }

   public final void F(String param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield n1/b.o Ljava/util/concurrent/atomic/AtomicBoolean;
      // 04: bipush 0
      // 05: bipush 1
      // 06: invokevirtual java/util/concurrent/atomic/AtomicBoolean.compareAndSet (ZZ)Z
      // 09: ifne 0d
      // 0c: return
      // 0d: aload 0
      // 0e: getfield n1/b.i Ljava/util/Collection;
      // 11: astore 2
      // 12: aload 2
      // 13: monitorenter
      // 14: new java/util/ArrayList
      // 17: astore 3
      // 18: aload 3
      // 19: aload 0
      // 1a: getfield n1/b.i Ljava/util/Collection;
      // 1d: invokespecial java/util/ArrayList.<init> (Ljava/util/Collection;)V
      // 20: aload 2
      // 21: monitorexit
      // 22: aload 3
      // 23: invokevirtual java/util/ArrayList.iterator ()Ljava/util/Iterator;
      // 26: astore 2
      // 27: aload 2
      // 28: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 2d: ifeq 45
      // 30: aload 2
      // 31: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 36: checkcast e1/b
      // 39: sipush 1001
      // 3c: aload 1
      // 3d: invokeinterface e1/b.b (ILjava/lang/String;)V 3
      // 42: goto 27
      // 45: aload 0
      // 46: getfield n1/b.u Lp0/q;
      // 49: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 4c: pop
      // 4d: aload 0
      // 4e: monitorenter
      // 4f: aload 0
      // 50: getfield n1/b.n Ljava/lang/Thread;
      // 53: ifnull 6d
      // 56: aload 0
      // 57: getfield n1/b.l Ljava/nio/channels/Selector;
      // 5a: astore 1
      // 5b: aload 1
      // 5c: ifnull 6d
      // 5f: aload 1
      // 60: invokevirtual java/nio/channels/Selector.wakeup ()Ljava/nio/channels/Selector;
      // 63: pop
      // 64: aload 0
      // 65: getfield n1/b.n Ljava/lang/Thread;
      // 68: bipush 0
      // 69: i2l
      // 6a: invokevirtual java/lang/Thread.join (J)V
      // 6d: aload 0
      // 6e: monitorexit
      // 6f: return
      // 70: astore 1
      // 71: aload 0
      // 72: monitorexit
      // 73: aload 1
      // 74: athrow
      // 75: astore 1
      // 76: aload 2
      // 77: monitorexit
      // 78: aload 1
      // 79: athrow
   }

   @Override
   public void finalize() {
      this.t();
      super.finalize();
   }

   @Override
   public final InetSocketAddress h(e1.b var1) {
      return (InetSocketAddress)((SocketChannel)((d)var1).d.channel()).socket().getRemoteSocketAddress();
   }

   @Override
   public final void i(e1.b var1, int var2, String var3, boolean var4) {
      this.l.wakeup();
      if (this.E(var1)) {
         this.B(var1);
      }
   }

   @Override
   public final void j() {
   }

   @Override
   public final void k() {
   }

   @Override
   public final void l(e1.b var1, Exception var2) {
      this.C(var2);
   }

   @Override
   public final void m() {
   }

   @Override
   public final void n(e1.b var1, String var2) {
      StringBuilder var3 = new StringBuilder("MyWebSocketServer onMessage getHostAddress:");
      var3.append(var1.g().getAddress().getHostAddress());
      Log.d("MyWebSocketServer", var3.toString());
      var3 = new StringBuilder("MyWebSocketServer onMessage msg:");
      var3.append(var2);
      Log.d("MyWebSocketServer", var3.toString());
      var1.c("OK");
   }

   @Override
   public final void o(e1.b param1, l1.b param2) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 000: aload 0
      // 001: getfield n1/b.o Ljava/util/concurrent/atomic/AtomicBoolean;
      // 004: invokevirtual java/util/concurrent/atomic/AtomicBoolean.get ()Z
      // 007: istore 5
      // 009: bipush 1
      // 00a: istore 4
      // 00c: iload 5
      // 00e: ifne 032
      // 011: aload 0
      // 012: getfield n1/b.i Ljava/util/Collection;
      // 015: astore 6
      // 017: aload 6
      // 019: monitorenter
      // 01a: aload 0
      // 01b: getfield n1/b.i Ljava/util/Collection;
      // 01e: aload 1
      // 01f: invokeinterface java/util/Collection.add (Ljava/lang/Object;)Z 2
      // 024: istore 5
      // 026: aload 6
      // 028: monitorexit
      // 029: goto 03e
      // 02c: astore 1
      // 02d: aload 6
      // 02f: monitorexit
      // 030: aload 1
      // 031: athrow
      // 032: aload 1
      // 033: sipush 1001
      // 036: invokeinterface e1/b.e (I)V 2
      // 03b: bipush 1
      // 03c: istore 5
      // 03e: iload 5
      // 040: ifeq 2ca
      // 043: aload 2
      // 044: checkcast l1/a
      // 047: astore 2
      // 048: aload 0
      // 049: checkcast com/guard/wallet/server/c
      // 04c: astore 2
      // 04d: aload 1
      // 04e: invokeinterface e1/b.d ()Ljava/lang/String; 1
      // 053: ldc_w "/minicap"
      // 056: invokestatic java/util/Objects.equals (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 059: ifeq 0f8
      // 05c: invokestatic com/guard/wallet/service/MyAccessibilityService.P ()Lcom/guard/wallet/service/MyAccessibilityService;
      // 05f: ifnonnull 071
      // 062: aload 1
      // 063: sipush 200
      // 066: ldc_w "无障碍容器异常,请稍后重试"
      // 069: invokeinterface e1/b.b (ILjava/lang/String;)V 3
      // 06e: goto 0f8
      // 071: aload 1
      // 072: ldc_w "welcome to minicap"
      // 075: invokeinterface e1/b.c (Ljava/lang/String;)V 2
      // 07a: aload 2
      // 07b: getfield com/guard/wallet/server/c.y Ljava/util/concurrent/ConcurrentLinkedQueue;
      // 07e: aload 1
      // 07f: invokevirtual java/util/concurrent/ConcurrentLinkedQueue.offer (Ljava/lang/Object;)Z
      // 082: pop
      // 083: getstatic android/os/Build$VERSION.SDK_INT I
      // 086: bipush 30
      // 088: if_icmpge 0ea
      // 08b: invokestatic x/a.b ()Lx/a;
      // 08e: astore 7
      // 090: aload 7
      // 092: getfield x/a.e Ljava/util/concurrent/locks/ReentrantLock;
      // 095: astore 6
      // 097: aload 6
      // 099: invokevirtual java/util/concurrent/locks/ReentrantLock.tryLock ()Z
      // 09c: ifeq 0f3
      // 09f: aload 7
      // 0a1: invokevirtual x/a.c ()Z
      // 0a4: ifeq 0dd
      // 0a7: aload 7
      // 0a9: getfield x/a.g Lx/b;
      // 0ac: getfield x/b.a Ljava/util/concurrent/atomic/AtomicReference;
      // 0af: invokevirtual java/util/concurrent/atomic/AtomicReference.get ()Ljava/lang/Object;
      // 0b2: checkcast android/graphics/Bitmap
      // 0b5: astore 7
      // 0b7: aload 7
      // 0b9: ifnull 0e2
      // 0bc: aload 7
      // 0be: ldc_w 0.25
      // 0c1: bipush 25
      // 0c3: invokestatic com/guard/wallet/utils/g.M0 (Landroid/graphics/Bitmap;FI)[B
      // 0c6: astore 7
      // 0c8: invokestatic com/guard/wallet/service/MyAccessibilityService.P ()Lcom/guard/wallet/service/MyAccessibilityService;
      // 0cb: ifnull 0e2
      // 0ce: invokestatic com/guard/wallet/service/MyAccessibilityService.P ()Lcom/guard/wallet/service/MyAccessibilityService;
      // 0d1: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 0d4: pop
      // 0d5: aload 7
      // 0d7: invokestatic com/guard/wallet/service/MyAccessibilityService.a0 ([B)V
      // 0da: goto 0e2
      // 0dd: aload 7
      // 0df: invokevirtual x/a.f ()V
      // 0e2: aload 6
      // 0e4: invokevirtual java/util/concurrent/locks/ReentrantLock.unlock ()V
      // 0e7: goto 0f3
      // 0ea: invokestatic com/guard/wallet/service/MyAccessibilityService.P ()Lcom/guard/wallet/service/MyAccessibilityService;
      // 0ed: getfield com/guard/wallet/service/AccessibilityDelegateManager.e Lo/r;
      // 0f0: invokevirtual o/r.a ()V
      // 0f3: bipush 1
      // 0f4: istore 3
      // 0f5: goto 0fa
      // 0f8: bipush 0
      // 0f9: istore 3
      // 0fa: iload 3
      // 0fb: ifeq 10c
      // 0fe: new java/lang/StringBuilder
      // 101: dup
      // 102: ldc_w "WebSocket onOpen minicap getHostAddress:"
      // 105: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 108: astore 2
      // 109: goto 29f
      // 10c: aload 1
      // 10d: invokeinterface e1/b.d ()Ljava/lang/String; 1
      // 112: ldc_w "/readScreen"
      // 115: invokestatic java/util/Objects.equals (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 118: ifeq 18b
      // 11b: invokestatic com/guard/wallet/service/MyAccessibilityService.P ()Lcom/guard/wallet/service/MyAccessibilityService;
      // 11e: ifnonnull 130
      // 121: aload 1
      // 122: sipush 200
      // 125: ldc_w "无障碍容器异常,请稍后重试"
      // 128: invokeinterface e1/b.b (ILjava/lang/String;)V 3
      // 12d: goto 18b
      // 130: aload 1
      // 131: ldc_w "welcome to read screen"
      // 134: invokeinterface e1/b.c (Ljava/lang/String;)V 2
      // 139: aload 2
      // 13a: getfield com/guard/wallet/server/c.z Ljava/util/concurrent/ConcurrentLinkedQueue;
      // 13d: aload 1
      // 13e: invokevirtual java/util/concurrent/ConcurrentLinkedQueue.offer (Ljava/lang/Object;)Z
      // 141: pop
      // 142: invokestatic com/guard/wallet/service/MyAccessibilityService.P ()Lcom/guard/wallet/service/MyAccessibilityService;
      // 145: getfield com/guard/wallet/service/AccessibilityDelegateManager.f Lo/c0;
      // 148: astore 8
      // 14a: aload 8
      // 14c: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 14f: pop
      // 150: aload 8
      // 152: getfield o/c0.b Ljava/util/concurrent/atomic/AtomicBoolean;
      // 155: invokevirtual java/util/concurrent/atomic/AtomicBoolean.get ()Z
      // 158: ifne 186
      // 15b: aload 8
      // 15d: getfield o/c0.a Ljava/util/concurrent/ExecutorService;
      // 160: astore 7
      // 162: new o/a
      // 165: astore 6
      // 167: aload 6
      // 169: aload 8
      // 16b: bipush 4
      // 16c: invokespecial o/a.<init> (Ljava/lang/Object;I)V
      // 16f: aload 7
      // 171: aload 6
      // 173: invokeinterface java/util/concurrent/ExecutorService.submit (Ljava/lang/Runnable;)Ljava/util/concurrent/Future; 2
      // 178: pop
      // 179: goto 186
      // 17c: astore 6
      // 17e: ldc_w "o.c0"
      // 181: aload 6
      // 183: invokestatic a1/q.s (Ljava/lang/String;Ljava/lang/Exception;)V
      // 186: bipush 1
      // 187: istore 3
      // 188: goto 18d
      // 18b: bipush 0
      // 18c: istore 3
      // 18d: iload 3
      // 18e: ifeq 19f
      // 191: new java/lang/StringBuilder
      // 194: dup
      // 195: ldc_w "WebSocket onOpen read model getHostAddress:"
      // 198: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 19b: astore 2
      // 19c: goto 29f
      // 19f: aload 1
      // 1a0: invokeinterface e1/b.d ()Ljava/lang/String; 1
      // 1a5: ldc_w "/frontCameraLive"
      // 1a8: invokestatic java/util/Objects.equals (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 1ab: ifeq 20b
      // 1ae: invokestatic com/guard/wallet/utils/g.k ()Z
      // 1b1: ifne 1c9
      // 1b4: invokestatic com/guard/wallet/service/MyAccessibilityService.P ()Lcom/guard/wallet/service/MyAccessibilityService;
      // 1b7: ifnonnull 1c9
      // 1ba: aload 1
      // 1bb: sipush 200
      // 1be: ldc_w "没有访问摄像头权限,不支持相机投屏"
      // 1c1: invokeinterface e1/b.b (ILjava/lang/String;)V 3
      // 1c6: goto 20b
      // 1c9: aload 1
      // 1ca: ldc_w "welcome to front camera"
      // 1cd: invokeinterface e1/b.c (Ljava/lang/String;)V 2
      // 1d2: aload 2
      // 1d3: getfield com/guard/wallet/server/c.A Ljava/util/concurrent/ConcurrentLinkedQueue;
      // 1d6: aload 1
      // 1d7: invokevirtual java/util/concurrent/ConcurrentLinkedQueue.offer (Ljava/lang/Object;)Z
      // 1da: pop
      // 1db: aload 2
      // 1dc: getfield com/guard/wallet/server/c.A Ljava/util/concurrent/ConcurrentLinkedQueue;
      // 1df: invokevirtual java/util/concurrent/ConcurrentLinkedQueue.size ()I
      // 1e2: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 1e5: invokevirtual java/lang/Integer.intValue ()I
      // 1e8: bipush 1
      // 1e9: if_icmpne 206
      // 1ec: invokestatic m/d.c ()Lm/d;
      // 1ef: astore 6
      // 1f1: aload 6
      // 1f3: bipush 1
      // 1f4: invokevirtual m/d.d (I)V
      // 1f7: aload 6
      // 1f9: getfield m/d.c Landroid/hardware/camera2/CameraDevice;
      // 1fc: ifnonnull 206
      // 1ff: aload 6
      // 201: bipush 0
      // 202: invokevirtual m/d.a (I)Z
      // 205: pop
      // 206: bipush 1
      // 207: istore 3
      // 208: goto 20d
      // 20b: bipush 0
      // 20c: istore 3
      // 20d: iload 3
      // 20e: ifeq 21f
      // 211: new java/lang/StringBuilder
      // 214: dup
      // 215: ldc_w "WebSocket onOpen front Camera getHostAddress:"
      // 218: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 21b: astore 2
      // 21c: goto 29f
      // 21f: aload 1
      // 220: invokeinterface e1/b.d ()Ljava/lang/String; 1
      // 225: ldc_w "/backCameraLive"
      // 228: invokestatic java/util/Objects.equals (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 22b: ifeq 28e
      // 22e: invokestatic com/guard/wallet/utils/g.k ()Z
      // 231: ifne 249
      // 234: invokestatic com/guard/wallet/service/MyAccessibilityService.P ()Lcom/guard/wallet/service/MyAccessibilityService;
      // 237: ifnonnull 249
      // 23a: aload 1
      // 23b: sipush 200
      // 23e: ldc_w "没有访问摄像头权限,不支持相机投屏"
      // 241: invokeinterface e1/b.b (ILjava/lang/String;)V 3
      // 246: goto 28e
      // 249: aload 1
      // 24a: ldc_w "welcome to back camera"
      // 24d: invokeinterface e1/b.c (Ljava/lang/String;)V 2
      // 252: aload 2
      // 253: getfield com/guard/wallet/server/c.B Ljava/util/concurrent/ConcurrentLinkedQueue;
      // 256: aload 1
      // 257: invokevirtual java/util/concurrent/ConcurrentLinkedQueue.offer (Ljava/lang/Object;)Z
      // 25a: pop
      // 25b: iload 4
      // 25d: istore 3
      // 25e: aload 2
      // 25f: getfield com/guard/wallet/server/c.B Ljava/util/concurrent/ConcurrentLinkedQueue;
      // 262: invokevirtual java/util/concurrent/ConcurrentLinkedQueue.size ()I
      // 265: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 268: invokevirtual java/lang/Integer.intValue ()I
      // 26b: bipush 1
      // 26c: if_icmpne 290
      // 26f: invokestatic m/d.c ()Lm/d;
      // 272: astore 2
      // 273: aload 2
      // 274: bipush 0
      // 275: invokevirtual m/d.d (I)V
      // 278: iload 4
      // 27a: istore 3
      // 27b: aload 2
      // 27c: getfield m/d.c Landroid/hardware/camera2/CameraDevice;
      // 27f: ifnonnull 290
      // 282: aload 2
      // 283: bipush 1
      // 284: invokevirtual m/d.a (I)Z
      // 287: pop
      // 288: iload 4
      // 28a: istore 3
      // 28b: goto 290
      // 28e: bipush 0
      // 28f: istore 3
      // 290: iload 3
      // 291: ifeq 2be
      // 294: new java/lang/StringBuilder
      // 297: dup
      // 298: ldc_w "WebSocket onOpen back Camera getHostAddress:"
      // 29b: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 29e: astore 2
      // 29f: aload 2
      // 2a0: aload 1
      // 2a1: invokeinterface e1/b.g ()Ljava/net/InetSocketAddress; 1
      // 2a6: invokevirtual java/net/InetSocketAddress.getAddress ()Ljava/net/InetAddress;
      // 2a9: invokevirtual java/net/InetAddress.getHostAddress ()Ljava/lang/String;
      // 2ac: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 2af: pop
      // 2b0: ldc_w "MyWebSocketServer"
      // 2b3: aload 2
      // 2b4: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 2b7: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 2ba: pop
      // 2bb: goto 2ca
      // 2be: aload 1
      // 2bf: sipush 200
      // 2c2: ldc_w "不合法的资源路径"
      // 2c5: invokeinterface e1/b.b (ILjava/lang/String;)V 3
      // 2ca: return
   }

   @Override
   public final void p(e1.b var1) {
      d var4 = (d)var1;

      try {
         var4.d.interestOps(5);
      } catch (CancelledKeyException var3) {
         var4.a.clear();
      }

      this.l.wakeup();
   }

   @Override
   public final Collection r() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield n1/b.i Ljava/util/Collection;
      // 04: astore 1
      // 05: aload 1
      // 06: monitorenter
      // 07: new java/util/ArrayList
      // 0a: astore 2
      // 0b: aload 2
      // 0c: aload 0
      // 0d: getfield n1/b.i Ljava/util/Collection;
      // 10: invokespecial java/util/ArrayList.<init> (Ljava/util/Collection;)V
      // 13: aload 2
      // 14: invokestatic java/util/Collections.unmodifiableCollection (Ljava/util/Collection;)Ljava/util/Collection;
      // 17: astore 2
      // 18: aload 1
      // 19: monitorexit
      // 1a: aload 2
      // 1b: areturn
      // 1c: astore 2
      // 1d: aload 1
      // 1e: monitorexit
      // 1f: aload 2
      // 20: athrow
   }

   @Override
   public final void run() {
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
      // 003: getfield n1/b.n Ljava/lang/Thread;
      // 006: ifnonnull 330
      // 009: aload 0
      // 00a: invokestatic java/lang/Thread.currentThread ()Ljava/lang/Thread;
      // 00d: putfield n1/b.n Ljava/lang/Thread;
      // 010: aload 0
      // 011: getfield n1/b.o Ljava/util/concurrent/atomic/AtomicBoolean;
      // 014: invokevirtual java/util/concurrent/atomic/AtomicBoolean.get ()Z
      // 017: istore 12
      // 019: bipush 1
      // 01a: istore 2
      // 01b: bipush 0
      // 01c: istore 4
      // 01e: iload 12
      // 020: ifeq 02a
      // 023: aload 0
      // 024: monitorexit
      // 025: bipush 0
      // 026: istore 1
      // 027: goto 02e
      // 02a: aload 0
      // 02b: monitorexit
      // 02c: bipush 1
      // 02d: istore 1
      // 02e: iload 1
      // 02f: ifne 033
      // 032: return
      // 033: aload 0
      // 034: getfield n1/b.n Ljava/lang/Thread;
      // 037: astore 14
      // 039: new java/lang/StringBuilder
      // 03c: dup
      // 03d: ldc_w "WebSocketSelector-"
      // 040: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 043: astore 13
      // 045: aload 13
      // 047: aload 0
      // 048: getfield n1/b.n Ljava/lang/Thread;
      // 04b: invokevirtual java/lang/Thread.getId ()J
      // 04e: invokevirtual java/lang/StringBuilder.append (J)Ljava/lang/StringBuilder;
      // 051: pop
      // 052: aload 14
      // 054: aload 13
      // 056: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 059: invokevirtual java/lang/Thread.setName (Ljava/lang/String;)V
      // 05c: aload 0
      // 05d: getfield n1/b.k Ljava/nio/channels/ServerSocketChannel;
      // 060: ifnonnull 06a
      // 063: aload 0
      // 064: invokestatic java/nio/channels/ServerSocketChannel.open ()Ljava/nio/channels/ServerSocketChannel;
      // 067: putfield n1/b.k Ljava/nio/channels/ServerSocketChannel;
      // 06a: aload 0
      // 06b: getfield n1/b.k Ljava/nio/channels/ServerSocketChannel;
      // 06e: bipush 0
      // 06f: invokevirtual java/nio/channels/SelectableChannel.configureBlocking (Z)Ljava/nio/channels/SelectableChannel;
      // 072: pop
      // 073: aload 0
      // 074: getfield n1/b.k Ljava/nio/channels/ServerSocketChannel;
      // 077: invokevirtual java/nio/channels/ServerSocketChannel.socket ()Ljava/net/ServerSocket;
      // 07a: astore 13
      // 07c: aload 13
      // 07e: aload 0
      // 07f: getfield e1/a.c Z
      // 082: invokevirtual java/net/ServerSocket.setReuseAddress (Z)V
      // 085: aload 13
      // 087: invokevirtual java/net/ServerSocket.isBound ()Z
      // 08a: ifne 09a
      // 08d: aload 13
      // 08f: aload 0
      // 090: getfield n1/b.j Ljava/net/InetSocketAddress;
      // 093: aload 0
      // 094: getfield n1/b.v I
      // 097: invokevirtual java/net/ServerSocket.bind (Ljava/net/SocketAddress;I)V
      // 09a: invokestatic java/nio/channels/Selector.open ()Ljava/nio/channels/Selector;
      // 09d: astore 14
      // 09f: aload 0
      // 0a0: aload 14
      // 0a2: putfield n1/b.l Ljava/nio/channels/Selector;
      // 0a5: aload 0
      // 0a6: getfield n1/b.k Ljava/nio/channels/ServerSocketChannel;
      // 0a9: astore 13
      // 0ab: aload 13
      // 0ad: aload 14
      // 0af: aload 13
      // 0b1: invokevirtual java/nio/channels/ServerSocketChannel.validOps ()I
      // 0b4: invokevirtual java/nio/channels/SelectableChannel.register (Ljava/nio/channels/Selector;I)Ljava/nio/channels/SelectionKey;
      // 0b7: pop
      // 0b8: aload 0
      // 0b9: invokevirtual e1/a.s ()V
      // 0bc: aload 0
      // 0bd: getfield n1/b.p Ljava/util/ArrayList;
      // 0c0: invokevirtual java/util/ArrayList.iterator ()Ljava/util/Iterator;
      // 0c3: astore 13
      // 0c5: aload 13
      // 0c7: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 0cc: ifeq 0df
      // 0cf: aload 13
      // 0d1: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 0d6: checkcast n1/a
      // 0d9: invokevirtual java/lang/Thread.start ()V
      // 0dc: goto 0c5
      // 0df: aload 0
      // 0e0: checkcast com/guard/wallet/server/c
      // 0e3: astore 13
      // 0e5: ldc_w "MyWebSocketServer"
      // 0e8: ldc_w "MyWebSocketServer 已启动"
      // 0eb: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 0ee: pop
      // 0ef: aload 13
      // 0f1: getfield com/guard/wallet/server/c.x Ljava/util/concurrent/atomic/AtomicBoolean;
      // 0f4: bipush 1
      // 0f5: invokevirtual java/util/concurrent/atomic/AtomicBoolean.set (Z)V
      // 0f8: iload 2
      // 0f9: istore 1
      // 0fa: goto 108
      // 0fd: astore 13
      // 0ff: aload 0
      // 100: aconst_null
      // 101: aload 13
      // 103: invokevirtual n1/b.z (Le1/d;Ljava/lang/Exception;)V
      // 106: bipush 0
      // 107: istore 1
      // 108: iload 1
      // 109: ifne 10d
      // 10c: return
      // 10d: bipush 5
      // 10e: istore 2
      // 10f: aload 0
      // 110: getfield n1/b.n Ljava/lang/Thread;
      // 113: invokevirtual java/lang/Thread.isInterrupted ()Z
      // 116: istore 12
      // 118: iload 12
      // 11a: ifne 322
      // 11d: iload 2
      // 11e: ifeq 322
      // 121: iload 4
      // 123: istore 3
      // 124: iload 4
      // 126: istore 10
      // 128: iload 2
      // 129: istore 11
      // 12b: iload 4
      // 12d: istore 6
      // 12f: iload 2
      // 130: istore 7
      // 132: iload 4
      // 134: istore 5
      // 136: iload 2
      // 137: istore 8
      // 139: iload 2
      // 13a: istore 9
      // 13c: aload 0
      // 13d: getfield n1/b.o Ljava/util/concurrent/atomic/AtomicBoolean;
      // 140: invokevirtual java/util/concurrent/atomic/AtomicBoolean.get ()Z
      // 143: ifeq 148
      // 146: bipush 5
      // 147: istore 3
      // 148: iload 2
      // 149: istore 1
      // 14a: iload 3
      // 14b: istore 10
      // 14d: iload 2
      // 14e: istore 11
      // 150: iload 3
      // 151: istore 6
      // 153: iload 2
      // 154: istore 7
      // 156: iload 3
      // 157: istore 5
      // 159: iload 2
      // 15a: istore 8
      // 15c: iload 3
      // 15d: istore 4
      // 15f: iload 2
      // 160: istore 9
      // 162: aload 0
      // 163: getfield n1/b.l Ljava/nio/channels/Selector;
      // 166: iload 3
      // 167: i2l
      // 168: invokevirtual java/nio/channels/Selector.select (J)I
      // 16b: ifne 196
      // 16e: iload 2
      // 16f: istore 1
      // 170: iload 3
      // 171: istore 10
      // 173: iload 2
      // 174: istore 11
      // 176: iload 3
      // 177: istore 6
      // 179: iload 2
      // 17a: istore 7
      // 17c: iload 3
      // 17d: istore 5
      // 17f: iload 2
      // 180: istore 8
      // 182: iload 3
      // 183: istore 4
      // 185: iload 2
      // 186: istore 9
      // 188: aload 0
      // 189: getfield n1/b.o Ljava/util/concurrent/atomic/AtomicBoolean;
      // 18c: invokevirtual java/util/concurrent/atomic/AtomicBoolean.get ()Z
      // 18f: ifeq 196
      // 192: iload 2
      // 193: bipush 1
      // 194: isub
      // 195: istore 1
      // 196: iload 3
      // 197: istore 10
      // 199: iload 1
      // 19a: istore 11
      // 19c: iload 3
      // 19d: istore 6
      // 19f: iload 1
      // 1a0: istore 7
      // 1a2: iload 3
      // 1a3: istore 5
      // 1a5: iload 1
      // 1a6: istore 8
      // 1a8: iload 3
      // 1a9: istore 4
      // 1ab: iload 1
      // 1ac: istore 9
      // 1ae: aload 0
      // 1af: getfield n1/b.l Ljava/nio/channels/Selector;
      // 1b2: invokevirtual java/nio/channels/Selector.selectedKeys ()Ljava/util/Set;
      // 1b5: invokeinterface java/util/Set.iterator ()Ljava/util/Iterator; 1
      // 1ba: astore 15
      // 1bc: aconst_null
      // 1bd: astore 13
      // 1bf: iload 3
      // 1c0: istore 10
      // 1c2: iload 1
      // 1c3: istore 11
      // 1c5: iload 3
      // 1c6: istore 4
      // 1c8: iload 1
      // 1c9: istore 9
      // 1cb: aload 15
      // 1cd: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 1d2: ifeq 2a1
      // 1d5: iload 3
      // 1d6: istore 10
      // 1d8: iload 1
      // 1d9: istore 11
      // 1db: iload 3
      // 1dc: istore 4
      // 1de: iload 1
      // 1df: istore 9
      // 1e1: aload 15
      // 1e3: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 1e8: checkcast java/nio/channels/SelectionKey
      // 1eb: astore 14
      // 1ed: iload 3
      // 1ee: istore 10
      // 1f0: iload 1
      // 1f1: istore 11
      // 1f3: iload 3
      // 1f4: istore 4
      // 1f6: iload 1
      // 1f7: istore 9
      // 1f9: aload 14
      // 1fb: invokevirtual java/nio/channels/SelectionKey.isValid ()Z
      // 1fe: ifne 204
      // 201: goto 280
      // 204: iload 3
      // 205: istore 10
      // 207: iload 1
      // 208: istore 11
      // 20a: iload 3
      // 20b: istore 4
      // 20d: iload 1
      // 20e: istore 9
      // 210: aload 14
      // 212: invokevirtual java/nio/channels/SelectionKey.isAcceptable ()Z
      // 215: ifeq 22d
      // 218: iload 3
      // 219: istore 10
      // 21b: iload 1
      // 21c: istore 11
      // 21e: iload 3
      // 21f: istore 4
      // 221: iload 1
      // 222: istore 9
      // 224: aload 0
      // 225: aload 15
      // 227: invokevirtual n1/b.u (Ljava/util/Iterator;)V
      // 22a: goto 280
      // 22d: iload 3
      // 22e: istore 10
      // 230: iload 1
      // 231: istore 11
      // 233: iload 3
      // 234: istore 4
      // 236: iload 1
      // 237: istore 9
      // 239: aload 14
      // 23b: invokevirtual java/nio/channels/SelectionKey.isReadable ()Z
      // 23e: ifeq 25b
      // 241: iload 3
      // 242: istore 10
      // 244: iload 1
      // 245: istore 11
      // 247: iload 3
      // 248: istore 4
      // 24a: iload 1
      // 24b: istore 9
      // 24d: aload 0
      // 24e: aload 14
      // 250: aload 15
      // 252: invokevirtual n1/b.w (Ljava/nio/channels/SelectionKey;Ljava/util/Iterator;)Z
      // 255: ifne 25b
      // 258: goto 280
      // 25b: iload 3
      // 25c: istore 10
      // 25e: iload 1
      // 25f: istore 11
      // 261: iload 3
      // 262: istore 4
      // 264: iload 1
      // 265: istore 9
      // 267: aload 14
      // 269: invokevirtual java/nio/channels/SelectionKey.isWritable ()Z
      // 26c: ifeq 280
      // 26f: iload 3
      // 270: istore 10
      // 272: iload 1
      // 273: istore 11
      // 275: iload 3
      // 276: istore 4
      // 278: iload 1
      // 279: istore 9
      // 27b: aload 14
      // 27d: invokestatic n1/b.y (Ljava/nio/channels/SelectionKey;)V
      // 280: aload 14
      // 282: astore 13
      // 284: goto 1bf
      // 287: astore 15
      // 289: aload 14
      // 28b: astore 13
      // 28d: aload 15
      // 28f: astore 14
      // 291: goto 2e0
      // 294: astore 15
      // 296: aload 14
      // 298: astore 13
      // 29a: aload 15
      // 29c: astore 14
      // 29e: goto 2fb
      // 2a1: iload 3
      // 2a2: istore 10
      // 2a4: iload 1
      // 2a5: istore 11
      // 2a7: iload 3
      // 2a8: istore 4
      // 2aa: iload 1
      // 2ab: istore 9
      // 2ad: aload 0
      // 2ae: invokevirtual n1/b.v ()V
      // 2b1: iload 3
      // 2b2: istore 4
      // 2b4: iload 1
      // 2b5: istore 2
      // 2b6: goto 10f
      // 2b9: astore 14
      // 2bb: goto 2e0
      // 2be: astore 14
      // 2c0: goto 2fb
      // 2c3: astore 13
      // 2c5: invokestatic java/lang/Thread.currentThread ()Ljava/lang/Thread;
      // 2c8: invokevirtual java/lang/Thread.interrupt ()V
      // 2cb: iload 10
      // 2cd: istore 4
      // 2cf: iload 11
      // 2d1: istore 2
      // 2d2: goto 10f
      // 2d5: astore 14
      // 2d7: aconst_null
      // 2d8: astore 13
      // 2da: iload 7
      // 2dc: istore 1
      // 2dd: iload 6
      // 2df: istore 3
      // 2e0: aload 13
      // 2e2: aconst_null
      // 2e3: aload 14
      // 2e5: invokestatic n1/b.A (Ljava/nio/channels/SelectionKey;Le1/b;Ljava/io/IOException;)V
      // 2e8: iload 3
      // 2e9: istore 4
      // 2eb: iload 1
      // 2ec: istore 2
      // 2ed: goto 10f
      // 2f0: astore 14
      // 2f2: aconst_null
      // 2f3: astore 13
      // 2f5: iload 8
      // 2f7: istore 1
      // 2f8: iload 5
      // 2fa: istore 3
      // 2fb: aload 13
      // 2fd: aload 14
      // 2ff: getfield i1/h.a Le1/b;
      // 302: aload 14
      // 304: getfield i1/h.b Ljava/io/IOException;
      // 307: invokestatic n1/b.A (Ljava/nio/channels/SelectionKey;Le1/b;Ljava/io/IOException;)V
      // 30a: iload 3
      // 30b: istore 4
      // 30d: iload 1
      // 30e: istore 2
      // 30f: goto 10f
      // 312: astore 13
      // 314: aload 0
      // 315: invokevirtual n1/b.x ()V
      // 318: return
      // 319: astore 13
      // 31b: aload 0
      // 31c: aconst_null
      // 31d: aload 13
      // 31f: invokevirtual n1/b.z (Le1/d;Ljava/lang/Exception;)V
      // 322: aload 0
      // 323: invokevirtual n1/b.x ()V
      // 326: return
      // 327: astore 13
      // 329: aload 0
      // 32a: invokevirtual n1/b.x ()V
      // 32d: aload 13
      // 32f: athrow
      // 330: new java/lang/IllegalStateException
      // 333: astore 13
      // 335: aload 13
      // 337: aload 0
      // 338: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 33b: invokevirtual java/lang/Class.getName ()Ljava/lang/String;
      // 33e: ldc_w " can only be started once."
      // 341: invokevirtual java/lang/String.concat (Ljava/lang/String;)Ljava/lang/String;
      // 344: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 347: aload 13
      // 349: athrow
      // 34a: astore 13
      // 34c: aload 0
      // 34d: monitorexit
      // 34e: aload 13
      // 350: athrow
      // 351: astore 13
      // 353: iload 9
      // 355: istore 2
      // 356: goto 10f
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final void t() {
      Exception var10000;
      label59: {
         try {
            this.F("");
         } catch (Exception var6) {
            var10000 = var6;
            boolean var10001 = false;
            break label59;
         }

         Collection var1 = this.i;
         if (var1 != null) {
            try {
               if (!var1.isEmpty()) {
                  var1.clear();
               }
            } catch (Exception var5) {
               var10000 = var5;
               boolean var11 = false;
               break label59;
            }
         }

         List var7 = this.m;
         if (var7 != null) {
            try {
               if (!var7.isEmpty()) {
                  var7.clear();
               }
            } catch (Exception var4) {
               var10000 = var4;
               boolean var12 = false;
               break label59;
            }
         }

         LinkedList var8 = this.q;
         if (var8 != null) {
            try {
               if (!var8.isEmpty()) {
                  var8.clear();
               }
            } catch (Exception var3) {
               var10000 = var3;
               boolean var13 = false;
               break label59;
            }
         }

         LinkedBlockingQueue var9 = this.r;
         if (var9 == null) {
            return;
         }

         try {
            if (!var9.isEmpty()) {
               var9.clear();
            }

            return;
         } catch (Exception var2) {
            var10000 = var2;
            boolean var14 = false;
         }
      }

      Exception var10 = var10000;
      a1.q.s("n1.b", var10);
   }

   public final void u(Iterator var1) {
      SocketChannel var3 = this.k.accept();
      if (var3 != null) {
         var3.configureBlocking(false);
         Socket var2 = var3.socket();
         var2.setTcpNoDelay(super.b);
         var2.setKeepAlive(true);
         this.u.getClass();
         d var6 = new d(this, this.m);
         var6.d = var3.register(this.l, 1, var6);

         try {
            var6.e = var3;
            var1.remove();
            AtomicInteger var5 = this.t;
            if (var5.get() < this.p.size() * 2 + 1) {
               var5.incrementAndGet();
               this.r.put(ByteBuffer.allocate(65536));
            }
         } catch (IOException var4) {
            SelectionKey var7 = var6.d;
            if (var7 != null) {
               var7.cancel();
            }

            A(var6.d, null, var4);
         }
      }
   }

   public final void v() {
      LinkedList var1 = this.q;
      if (!var1.isEmpty()) {
         a.a.x(((d)var1.remove(0)).e);
         ByteBuffer var4 = (ByteBuffer)this.r.take();

         try {
            ((Buffer)var4).clear();
         } catch (IOException var3) {
            this.D(var4);
            throw var3;
         }

         throw null;
      }
   }

   // $VF: Handled exception range with multiple entry points by splitting it
   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final boolean w(SelectionKey var1, Iterator var2) {
      d var5 = (d)var1.attachment();
      ByteBuffer var6 = (ByteBuffer)this.r.take();
      ByteChannel var7 = var5.e;
      boolean var3 = false;
      if (var7 == null) {
         var1.cancel();
         A(var1, var5, new IOException());
         return false;
      } else {
         IOException var10000;
         label64: {
            int var4;
            try {
               ((Buffer)var6).clear();
               var4 = var7.read(var6);
               ((Buffer)var6).flip();
            } catch (IOException var13) {
               var10000 = var13;
               boolean var10001 = false;
               break label64;
            }

            if (var4 == -1) {
               try {
                  var5.o();
               } catch (IOException var12) {
                  var10000 = var12;
                  boolean var16 = false;
                  break label64;
               }
            } else if (var4 != 0) {
               var3 = true;
            }

            label69: {
               label43:
               if (var3) {
                  try {
                     if (!var6.hasRemaining()) {
                        break label43;
                     }

                     var5.b.put(var6);
                     if (var5.f == null) {
                        ArrayList var14 = this.p;
                        var5.f = (a)var14.get(this.s % var14.size());
                        this.s++;
                     }
                  } catch (IOException var11) {
                     var10000 = var11;
                     boolean var17 = false;
                     break label64;
                  }

                  try {
                     var5.f.a.put(var5);
                     var2.remove();
                     break label69;
                  } catch (IOException var10) {
                     var10000 = var10;
                     boolean var18 = false;
                     break label64;
                  }
               }

               try {
                  this.D(var6);
               } catch (IOException var9) {
                  var10000 = var9;
                  boolean var19 = false;
                  break label64;
               }
            }

            try {
               return true;
            } catch (IOException var8) {
               var10000 = var8;
               boolean var20 = false;
            }
         }

         IOException var15 = var10000;
         this.D(var6);
         throw new h(var5, var15);
      }
   }

   public final void x() {
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
      // 12: ifnull 4a
      // 15: ldc_w "e1.a"
      // 18: ldc_w "Connection lost timer stopped"
      // 1b: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 1e: pop
      // 1f: aload 0
      // 20: getfield e1/a.d Ljava/util/concurrent/ScheduledExecutorService;
      // 23: astore 2
      // 24: aload 2
      // 25: ifnull 34
      // 28: aload 2
      // 29: invokeinterface java/util/concurrent/ExecutorService.shutdownNow ()Ljava/util/List; 1
      // 2e: pop
      // 2f: aload 0
      // 30: aconst_null
      // 31: putfield e1/a.d Ljava/util/concurrent/ScheduledExecutorService;
      // 34: aload 0
      // 35: getfield e1/a.e Ljava/util/concurrent/ScheduledFuture;
      // 38: astore 2
      // 39: aload 2
      // 3a: ifnull 4a
      // 3d: aload 2
      // 3e: bipush 0
      // 3f: invokeinterface java/util/concurrent/Future.cancel (Z)Z 2
      // 44: pop
      // 45: aload 0
      // 46: aconst_null
      // 47: putfield e1/a.e Ljava/util/concurrent/ScheduledFuture;
      // 4a: aload 1
      // 4b: monitorexit
      // 4c: aload 0
      // 4d: getfield n1/b.p Ljava/util/ArrayList;
      // 50: astore 1
      // 51: aload 1
      // 52: ifnull 72
      // 55: aload 1
      // 56: invokevirtual java/util/ArrayList.iterator ()Ljava/util/Iterator;
      // 59: astore 1
      // 5a: aload 1
      // 5b: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 60: ifeq 72
      // 63: aload 1
      // 64: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 69: checkcast n1/a
      // 6c: invokevirtual java/lang/Thread.interrupt ()V
      // 6f: goto 5a
      // 72: aload 0
      // 73: getfield n1/b.l Ljava/nio/channels/Selector;
      // 76: astore 1
      // 77: aload 1
      // 78: ifnull 8f
      // 7b: aload 1
      // 7c: invokevirtual java/nio/channels/Selector.close ()V
      // 7f: goto 8f
      // 82: astore 1
      // 83: ldc_w "IOException during selector.close"
      // 86: aload 1
      // 87: invokestatic a1/q.s (Ljava/lang/String;Ljava/lang/Exception;)V
      // 8a: aload 0
      // 8b: aload 1
      // 8c: invokevirtual n1/b.C (Ljava/lang/Exception;)V
      // 8f: aload 0
      // 90: getfield n1/b.k Ljava/nio/channels/ServerSocketChannel;
      // 93: astore 1
      // 94: aload 1
      // 95: ifnull ac
      // 98: aload 1
      // 99: invokevirtual java/nio/channels/spi/AbstractInterruptibleChannel.close ()V
      // 9c: goto ac
      // 9f: astore 1
      // a0: ldc_w "IOException during server.close"
      // a3: aload 1
      // a4: invokestatic a1/q.s (Ljava/lang/String;Ljava/lang/Exception;)V
      // a7: aload 0
      // a8: aload 1
      // a9: invokevirtual n1/b.C (Ljava/lang/Exception;)V
      // ac: return
      // ad: astore 2
      // ae: aload 1
      // af: monitorexit
      // b0: aload 2
      // b1: athrow
   }

   public final void z(d var1, Exception var2) {
      a1.q.s("Shutdown due to fatal error", var2);
      this.C(var2);
      String var5;
      if (var2.getCause() != null) {
         var5 = " caused by ".concat(var2.getCause().getClass().getName());
      } else {
         var5 = "";
      }

      StringBuilder var3 = new StringBuilder("Got error on server side: ");
      var3.append(var2.getClass().getName());
      var3.append(var5);
      String var6 = var3.toString();

      try {
         this.F(var6);
      } catch (InterruptedException var4) {
         Thread.currentThread().interrupt();
         a1.q.s("Interrupt during stop", var2);
         this.C(var4);
      }

      ArrayList var7 = this.p;
      if (var7 != null) {
         Iterator var8 = var7.iterator();

         while (var8.hasNext()) {
            ((a)var8.next()).interrupt();
         }
      }

      Thread var9 = this.n;
      if (var9 != null) {
         var9.interrupt();
      }
   }
}
