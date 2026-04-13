package f0;

import android.os.SystemClock;
import java.util.PriorityQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class j {
   public static final j f = new j();
   public static final ThreadPoolExecutor g;
   public static final ThreadLocal h = new ThreadLocal();
   public z a;
   public final String b;
   public int c = 0;
   public PriorityQueue d = new PriorityQueue(1, i.a);
   public e e;

   static {
      g var1 = new g("AsyncServer-worker-");
      TimeUnit var0 = TimeUnit.SECONDS;
      g = new ThreadPoolExecutor(0, 4, 10L, var0, new LinkedBlockingQueue<>(), var1);
      var1 = new g("AsyncServer-resolver-");
      new ThreadPoolExecutor(0, 4, 10L, var0, new LinkedBlockingQueue<>(), var1);
   }

   public j() {
      this.b = "AsyncServer";
   }

   public static void a(j param0, z param1, PriorityQueue param2) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: aload 0
      // 01: aload 1
      // 02: aload 2
      // 03: invokestatic f0/j.f (Lf0/j;Lf0/z;Ljava/util/PriorityQueue;)V
      // 06: goto 28
      // 09: astore 3
      // 0a: aload 3
      // 0b: invokevirtual java/lang/Throwable.getCause ()Ljava/lang/Throwable;
      // 0e: instanceof java/nio/channels/ClosedSelectorException
      // 11: ifne 1d
      // 14: ldc "NIO"
      // 16: ldc "Selector exception, shutting down"
      // 18: aload 3
      // 19: invokestatic android/util/Log.i (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      // 1c: pop
      // 1d: bipush 1
      // 1e: anewarray 106
      // 21: dup
      // 22: bipush 0
      // 23: aload 1
      // 24: aastore
      // 25: invokestatic a1/q.h ([Ljava/io/Closeable;)V
      // 28: aload 0
      // 29: monitorenter
      // 2a: aload 1
      // 2b: getfield f0/z.a Ljava/nio/channels/Selector;
      // 2e: invokevirtual java/nio/channels/Selector.isOpen ()Z
      // 31: ifeq 4f
      // 34: aload 1
      // 35: getfield f0/z.a Ljava/nio/channels/Selector;
      // 38: invokevirtual java/nio/channels/Selector.keys ()Ljava/util/Set;
      // 3b: invokeinterface java/util/Set.size ()I 1
      // 40: ifgt 4a
      // 43: aload 2
      // 44: invokevirtual java/util/PriorityQueue.size ()I
      // 47: ifle 4f
      // 4a: aload 0
      // 4b: monitorexit
      // 4c: goto 00
      // 4f: aload 1
      // 50: getfield f0/z.a Ljava/nio/channels/Selector;
      // 53: invokevirtual java/nio/channels/Selector.keys ()Ljava/util/Set;
      // 56: invokeinterface java/util/Set.iterator ()Ljava/util/Iterator; 1
      // 5b: astore 2
      // 5c: aload 2
      // 5d: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 62: ifeq 84
      // 65: aload 2
      // 66: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 6b: checkcast java/nio/channels/SelectionKey
      // 6e: astore 3
      // 6f: bipush 1
      // 70: anewarray 106
      // 73: dup
      // 74: bipush 0
      // 75: aload 3
      // 76: invokevirtual java/nio/channels/SelectionKey.channel ()Ljava/nio/channels/SelectableChannel;
      // 79: aastore
      // 7a: invokestatic a1/q.h ([Ljava/io/Closeable;)V
      // 7d: aload 3
      // 7e: invokevirtual java/nio/channels/SelectionKey.cancel ()V
      // 81: goto 5c
      // 84: bipush 1
      // 85: anewarray 106
      // 88: dup
      // 89: bipush 0
      // 8a: aload 1
      // 8b: aastore
      // 8c: invokestatic a1/q.h ([Ljava/io/Closeable;)V
      // 8f: aload 0
      // 90: getfield f0/j.a Lf0/z;
      // 93: aload 1
      // 94: if_acmpne b2
      // 97: new java/util/PriorityQueue
      // 9a: astore 1
      // 9b: aload 1
      // 9c: bipush 1
      // 9d: getstatic f0/i.a Lf0/i;
      // a0: invokespecial java/util/PriorityQueue.<init> (ILjava/util/Comparator;)V
      // a3: aload 0
      // a4: aload 1
      // a5: putfield f0/j.d Ljava/util/PriorityQueue;
      // a8: aload 0
      // a9: aconst_null
      // aa: putfield f0/j.a Lf0/z;
      // ad: aload 0
      // ae: aconst_null
      // af: putfield f0/j.e Lf0/e;
      // b2: aload 0
      // b3: monitorexit
      // b4: return
      // b5: astore 1
      // b6: aload 0
      // b7: monitorexit
      // b8: aload 1
      // b9: athrow
      // ba: astore 2
      // bb: goto 84
      // be: astore 3
      // bf: goto 5c
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static long b(j var0, PriorityQueue var1) {
      long var3 = Long.MAX_VALUE;

      while (true) {
         synchronized (var0){} // $VF: monitorenter 

         long var5;
         h var9;
         label224: {
            Throwable var10000;
            label225: {
               int var2;
               long var7;
               try {
                  var7 = SystemClock.elapsedRealtime();
                  var2 = var1.size();
               } catch (Throwable var40) {
                  var10000 = var40;
                  boolean var10001 = false;
                  break label225;
               }

               Object var10 = null;
               var5 = var3;
               var9 = (h)var10;
               if (var2 > 0) {
                  try {
                     var9 = (h)var1.remove();
                     var5 = var9.c;
                  } catch (Throwable var39) {
                     var10000 = var39;
                     boolean var43 = false;
                     break label225;
                  }

                  if (var5 <= var7) {
                     var5 = var3;
                  } else {
                     try {
                        var1.add(var9);
                     } catch (Throwable var38) {
                        var10000 = var38;
                        boolean var44 = false;
                        break label225;
                     }

                     var5 -= var7;
                     var9 = (h)var10;
                  }
               }

               label206:
               try {
                  // $VF: monitorexit
                  break label224;
               } catch (Throwable var37) {
                  var10000 = var37;
                  boolean var45 = false;
                  break label206;
               }
            }

            while (true) {
               Throwable var41 = var10000;

               try {
                  // $VF: monitorexit
                  throw var41;
               } catch (Throwable var36) {
                  var10000 = var36;
                  boolean var46 = false;
                  continue;
               }
            }
         }

         if (var9 == null) {
            var0.c = 0;
            return var5;
         }

         var9.run();
         var3 = var5;
      }
   }

   public static void f(j param0, z param1, PriorityQueue param2) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 000: aload 0
      // 001: aload 2
      // 002: invokestatic f0/j.b (Lf0/j;Ljava/util/PriorityQueue;)J
      // 005: lstore 5
      // 007: aload 0
      // 008: monitorenter
      // 009: aload 1
      // 00a: getfield f0/z.a Ljava/nio/channels/Selector;
      // 00d: invokevirtual java/nio/channels/Selector.selectNow ()I
      // 010: ifne 037
      // 013: aload 1
      // 014: getfield f0/z.a Ljava/nio/channels/Selector;
      // 017: invokevirtual java/nio/channels/Selector.keys ()Ljava/util/Set;
      // 01a: invokeinterface java/util/Set.size ()I 1
      // 01f: ifne 02e
      // 022: lload 5
      // 024: ldc2_w 9223372036854775807
      // 027: lcmp
      // 028: ifne 02e
      // 02b: aload 0
      // 02c: monitorexit
      // 02d: return
      // 02e: bipush 1
      // 02f: istore 3
      // 030: goto 039
      // 033: astore 1
      // 034: goto 2b1
      // 037: bipush 0
      // 038: istore 3
      // 039: aload 0
      // 03a: monitorexit
      // 03b: iload 3
      // 03c: ifeq 093
      // 03f: lload 5
      // 041: ldc2_w 9223372036854775807
      // 044: lcmp
      // 045: ifne 06d
      // 048: aload 1
      // 049: getfield f0/z.c Ljava/util/concurrent/Semaphore;
      // 04c: astore 2
      // 04d: aload 2
      // 04e: invokevirtual java/util/concurrent/Semaphore.drainPermits ()I
      // 051: pop
      // 052: aload 1
      // 053: getfield f0/z.a Ljava/nio/channels/Selector;
      // 056: lconst_0
      // 057: invokevirtual java/nio/channels/Selector.select (J)I
      // 05a: pop
      // 05b: aload 2
      // 05c: ldc 2147483647
      // 05e: invokevirtual java/util/concurrent/Semaphore.release (I)V
      // 061: goto 093
      // 064: astore 0
      // 065: aload 2
      // 066: ldc 2147483647
      // 068: invokevirtual java/util/concurrent/Semaphore.release (I)V
      // 06b: aload 0
      // 06c: athrow
      // 06d: aload 1
      // 06e: getfield f0/z.c Ljava/util/concurrent/Semaphore;
      // 071: astore 2
      // 072: aload 2
      // 073: invokevirtual java/util/concurrent/Semaphore.drainPermits ()I
      // 076: pop
      // 077: aload 1
      // 078: getfield f0/z.a Ljava/nio/channels/Selector;
      // 07b: lload 5
      // 07d: invokevirtual java/nio/channels/Selector.select (J)I
      // 080: pop
      // 081: aload 2
      // 082: ldc 2147483647
      // 084: invokevirtual java/util/concurrent/Semaphore.release (I)V
      // 087: goto 093
      // 08a: astore 0
      // 08b: aload 2
      // 08c: ldc 2147483647
      // 08e: invokevirtual java/util/concurrent/Semaphore.release (I)V
      // 091: aload 0
      // 092: athrow
      // 093: aload 1
      // 094: getfield f0/z.a Ljava/nio/channels/Selector;
      // 097: invokevirtual java/nio/channels/Selector.selectedKeys ()Ljava/util/Set;
      // 09a: astore 9
      // 09c: aload 9
      // 09e: invokeinterface java/util/Set.iterator ()Ljava/util/Iterator; 1
      // 0a3: astore 10
      // 0a5: aload 10
      // 0a7: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 0ac: ifeq 2a9
      // 0af: aload 10
      // 0b1: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 0b6: checkcast java/nio/channels/SelectionKey
      // 0b9: astore 11
      // 0bb: aload 11
      // 0bd: invokevirtual java/nio/channels/SelectionKey.isAcceptable ()Z
      // 0c0: istore 4
      // 0c2: aconst_null
      // 0c3: astore 2
      // 0c4: aconst_null
      // 0c5: astore 8
      // 0c7: iload 4
      // 0c9: ifeq 1b4
      // 0cc: aload 11
      // 0ce: invokevirtual java/nio/channels/SelectionKey.channel ()Ljava/nio/channels/SelectableChannel;
      // 0d1: checkcast java/nio/channels/ServerSocketChannel
      // 0d4: astore 7
      // 0d6: aload 7
      // 0d8: invokevirtual java/nio/channels/ServerSocketChannel.accept ()Ljava/nio/channels/SocketChannel;
      // 0db: astore 7
      // 0dd: aload 7
      // 0df: ifnonnull 0e5
      // 0e2: goto 0a5
      // 0e5: aload 8
      // 0e7: astore 2
      // 0e8: aload 7
      // 0ea: bipush 0
      // 0eb: invokevirtual java/nio/channels/SelectableChannel.configureBlocking (Z)Ljava/nio/channels/SelectableChannel;
      // 0ee: pop
      // 0ef: aload 8
      // 0f1: astore 2
      // 0f2: aload 7
      // 0f4: aload 1
      // 0f5: getfield f0/z.a Ljava/nio/channels/Selector;
      // 0f8: bipush 1
      // 0f9: invokevirtual java/nio/channels/SelectableChannel.register (Ljava/nio/channels/Selector;I)Ljava/nio/channels/SelectionKey;
      // 0fc: astore 8
      // 0fe: aload 8
      // 100: astore 2
      // 101: aload 11
      // 103: invokevirtual java/nio/channels/SelectionKey.attachment ()Ljava/lang/Object;
      // 106: checkcast l0/e
      // 109: astore 11
      // 10b: aload 8
      // 10d: astore 2
      // 10e: new f0/b
      // 111: astore 12
      // 113: aload 8
      // 115: astore 2
      // 116: aload 12
      // 118: invokespecial f0/b.<init> ()V
      // 11b: aload 8
      // 11d: astore 2
      // 11e: aload 7
      // 120: invokevirtual java/nio/channels/SocketChannel.socket ()Ljava/net/Socket;
      // 123: invokevirtual java/net/Socket.getRemoteSocketAddress ()Ljava/net/SocketAddress;
      // 126: checkcast java/net/InetSocketAddress
      // 129: astore 13
      // 12b: aload 8
      // 12d: astore 2
      // 12e: new n0/a
      // 131: astore 13
      // 133: aload 8
      // 135: astore 2
      // 136: aload 13
      // 138: invokespecial n0/a.<init> ()V
      // 13b: aload 8
      // 13d: astore 2
      // 13e: aload 12
      // 140: aload 13
      // 142: putfield f0/b.h Ln0/a;
      // 145: aload 8
      // 147: astore 2
      // 148: new f0/a0
      // 14b: astore 13
      // 14d: aload 8
      // 14f: astore 2
      // 150: aload 13
      // 152: aload 7
      // 154: bipush 1
      // 155: invokespecial f0/a0.<init> (Ljava/nio/channels/spi/AbstractSelectableChannel;I)V
      // 158: aload 8
      // 15a: astore 2
      // 15b: aload 12
      // 15d: aload 13
      // 15f: putfield f0/b.d Lf0/a0;
      // 162: aload 8
      // 164: astore 2
      // 165: aload 12
      // 167: aload 0
      // 168: putfield f0/b.f Lf0/j;
      // 16b: aload 8
      // 16d: astore 2
      // 16e: aload 12
      // 170: aload 8
      // 172: putfield f0/b.e Ljava/nio/channels/SelectionKey;
      // 175: aload 8
      // 177: astore 2
      // 178: aload 8
      // 17a: aload 12
      // 17c: invokevirtual java/nio/channels/SelectionKey.attach (Ljava/lang/Object;)Ljava/lang/Object;
      // 17f: pop
      // 180: aload 8
      // 182: astore 2
      // 183: aload 11
      // 185: aload 12
      // 187: invokevirtual l0/e.b (Lf0/k;)V
      // 18a: goto 0a5
      // 18d: astore 8
      // 18f: goto 19d
      // 192: astore 7
      // 194: aconst_null
      // 195: astore 8
      // 197: aload 2
      // 198: astore 7
      // 19a: aload 8
      // 19c: astore 2
      // 19d: bipush 1
      // 19e: anewarray 106
      // 1a1: dup
      // 1a2: bipush 0
      // 1a3: aload 7
      // 1a5: aastore
      // 1a6: invokestatic a1/q.h ([Ljava/io/Closeable;)V
      // 1a9: aload 2
      // 1aa: ifnull 0a5
      // 1ad: aload 2
      // 1ae: invokevirtual java/nio/channels/SelectionKey.cancel ()V
      // 1b1: goto 0a5
      // 1b4: aload 11
      // 1b6: invokevirtual java/nio/channels/SelectionKey.isReadable ()Z
      // 1b9: ifeq 1ca
      // 1bc: aload 11
      // 1be: invokevirtual java/nio/channels/SelectionKey.attachment ()Ljava/lang/Object;
      // 1c1: checkcast f0/b
      // 1c4: invokevirtual f0/b.a ()V
      // 1c7: goto 0a5
      // 1ca: aload 11
      // 1cc: invokevirtual java/nio/channels/SelectionKey.isWritable ()Z
      // 1cf: ifeq 209
      // 1d2: aload 11
      // 1d4: invokevirtual java/nio/channels/SelectionKey.attachment ()Ljava/lang/Object;
      // 1d7: checkcast f0/b
      // 1da: astore 2
      // 1db: aload 2
      // 1dc: getfield f0/b.d Lf0/a0;
      // 1df: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 1e2: pop
      // 1e3: aload 2
      // 1e4: getfield f0/b.e Ljava/nio/channels/SelectionKey;
      // 1e7: astore 7
      // 1e9: aload 7
      // 1eb: aload 7
      // 1ed: invokevirtual java/nio/channels/SelectionKey.interestOps ()I
      // 1f0: bipush -5
      // 1f2: iand
      // 1f3: invokevirtual java/nio/channels/SelectionKey.interestOps (I)Ljava/nio/channels/SelectionKey;
      // 1f6: pop
      // 1f7: aload 2
      // 1f8: getfield f0/b.j Lg0/c;
      // 1fb: astore 2
      // 1fc: aload 2
      // 1fd: ifnull 0a5
      // 200: aload 2
      // 201: invokeinterface g0/c.c ()V 1
      // 206: goto 0a5
      // 209: aload 11
      // 20b: invokevirtual java/nio/channels/SelectionKey.isConnectable ()Z
      // 20e: ifeq 293
      // 211: aload 11
      // 213: invokevirtual java/nio/channels/SelectionKey.attachment ()Ljava/lang/Object;
      // 216: invokestatic a/a.w (Ljava/lang/Object;)V
      // 219: aload 11
      // 21b: invokevirtual java/nio/channels/SelectionKey.channel ()Ljava/nio/channels/SelectableChannel;
      // 21e: checkcast java/nio/channels/SocketChannel
      // 221: astore 2
      // 222: aload 11
      // 224: bipush 1
      // 225: invokevirtual java/nio/channels/SelectionKey.interestOps (I)Ljava/nio/channels/SelectionKey;
      // 228: pop
      // 229: aload 2
      // 22a: invokevirtual java/nio/channels/SocketChannel.finishConnect ()Z
      // 22d: pop
      // 22e: new f0/b
      // 231: astore 7
      // 233: aload 7
      // 235: invokespecial f0/b.<init> ()V
      // 238: aload 7
      // 23a: aload 0
      // 23b: putfield f0/b.f Lf0/j;
      // 23e: aload 7
      // 240: aload 11
      // 242: putfield f0/b.e Ljava/nio/channels/SelectionKey;
      // 245: aload 2
      // 246: invokevirtual java/nio/channels/SocketChannel.socket ()Ljava/net/Socket;
      // 249: invokevirtual java/net/Socket.getRemoteSocketAddress ()Ljava/net/SocketAddress;
      // 24c: checkcast java/net/InetSocketAddress
      // 24f: astore 8
      // 251: new n0/a
      // 254: astore 8
      // 256: aload 8
      // 258: invokespecial n0/a.<init> ()V
      // 25b: aload 7
      // 25d: aload 8
      // 25f: putfield f0/b.h Ln0/a;
      // 262: new f0/a0
      // 265: astore 8
      // 267: aload 8
      // 269: aload 2
      // 26a: bipush 1
      // 26b: invokespecial f0/a0.<init> (Ljava/nio/channels/spi/AbstractSelectableChannel;I)V
      // 26e: aload 7
      // 270: aload 8
      // 272: putfield f0/b.d Lf0/a0;
      // 275: aload 11
      // 277: aload 7
      // 279: invokevirtual java/nio/channels/SelectionKey.attach (Ljava/lang/Object;)Ljava/lang/Object;
      // 27c: pop
      // 27d: aconst_null
      // 27e: athrow
      // 27f: astore 7
      // 281: aload 11
      // 283: invokevirtual java/nio/channels/SelectionKey.cancel ()V
      // 286: bipush 1
      // 287: anewarray 106
      // 28a: dup
      // 28b: bipush 0
      // 28c: aload 2
      // 28d: aastore
      // 28e: invokestatic a1/q.h ([Ljava/io/Closeable;)V
      // 291: aconst_null
      // 292: athrow
      // 293: ldc "NIO"
      // 295: ldc_w "wtf"
      // 298: invokestatic android/util/Log.i (Ljava/lang/String;Ljava/lang/String;)I
      // 29b: pop
      // 29c: new java/lang/RuntimeException
      // 29f: astore 2
      // 2a0: aload 2
      // 2a1: ldc_w "Unknown key state."
      // 2a4: invokespecial java/lang/RuntimeException.<init> (Ljava/lang/String;)V
      // 2a7: aload 2
      // 2a8: athrow
      // 2a9: aload 9
      // 2ab: invokeinterface java/util/Set.clear ()V 1
      // 2b0: return
      // 2b1: aload 0
      // 2b2: monitorexit
      // 2b3: aload 1
      // 2b4: athrow
      // 2b5: astore 0
      // 2b6: new f0/f
      // 2b9: dup
      // 2ba: aload 0
      // 2bb: invokespecial f0/f.<init> (Ljava/lang/Exception;)V
      // 2be: athrow
      // 2bf: astore 2
      // 2c0: goto 0a5
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final void c(Runnable var1) {
      synchronized (this){} // $VF: monitorenter 

      Throwable var10000;
      label273: {
         int var2;
         try {
            var2 = this.c++;
         } catch (Throwable var48) {
            var10000 = var48;
            boolean var10001 = false;
            break label273;
         }

         long var3 = (long)var2;

         try {
            PriorityQueue var6 = this.d;
            h var5 = new h(this, var1, var3);
            var6.add(var5);
            if (this.a == null) {
               this.d();
            }
         } catch (Throwable var47) {
            var10000 = var47;
            boolean var54 = false;
            break label273;
         }

         label261: {
            label260: {
               try {
                  if (this.e == Thread.currentThread()) {
                     break label260;
                  }
               } catch (Throwable var46) {
                  var10000 = var46;
                  boolean var55 = false;
                  break label273;
               }

               var51 = false;
               break label261;
            }

            var51 = true;
         }

         if (!var51) {
            try {
               z var52 = this.a;
               ThreadPoolExecutor var53 = g;
               o.a var49 = new o.a(var52, 5);
               var53.execute(var49);
            } catch (Throwable var45) {
               var10000 = var45;
               boolean var56 = false;
               break label273;
            }
         }

         label250:
         try {
            // $VF: monitorexit
            return;
         } catch (Throwable var44) {
            var10000 = var44;
            boolean var57 = false;
            break label250;
         }
      }

      while (true) {
         Throwable var50 = var10000;

         try {
            // $VF: monitorexit
            throw var50;
         } catch (Throwable var43) {
            var10000 = var43;
            boolean var58 = false;
            continue;
         }
      }
   }

   public final void d() {
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
      // 03: getfield f0/j.a Lf0/z;
      // 06: astore 1
      // 07: aload 1
      // 08: ifnonnull 51
      // 0b: new f0/z
      // 0e: astore 3
      // 0f: aload 3
      // 10: invokestatic java/nio/channels/spi/SelectorProvider.provider ()Ljava/nio/channels/spi/SelectorProvider;
      // 13: invokevirtual java/nio/channels/spi/SelectorProvider.openSelector ()Ljava/nio/channels/spi/AbstractSelector;
      // 16: invokespecial f0/z.<init> (Ljava/nio/channels/spi/AbstractSelector;)V
      // 19: aload 0
      // 1a: aload 3
      // 1b: putfield f0/j.a Lf0/z;
      // 1e: aload 0
      // 1f: getfield f0/j.d Ljava/util/PriorityQueue;
      // 22: astore 1
      // 23: new f0/e
      // 26: astore 2
      // 27: aload 2
      // 28: aload 0
      // 29: aload 0
      // 2a: getfield f0/j.b Ljava/lang/String;
      // 2d: aload 3
      // 2e: aload 1
      // 2f: invokespecial f0/e.<init> (Lf0/j;Ljava/lang/String;Lf0/z;Ljava/util/PriorityQueue;)V
      // 32: aload 0
      // 33: aload 2
      // 34: putfield f0/j.e Lf0/e;
      // 37: aload 2
      // 38: invokevirtual java/lang/Thread.start ()V
      // 3b: aload 0
      // 3c: monitorexit
      // 3d: return
      // 3e: astore 1
      // 3f: goto 74
      // 42: astore 1
      // 43: new java/lang/RuntimeException
      // 46: astore 2
      // 47: aload 2
      // 48: ldc_w "unable to create selector?"
      // 4b: aload 1
      // 4c: invokespecial java/lang/RuntimeException.<init> (Ljava/lang/String;Ljava/lang/Throwable;)V
      // 4f: aload 2
      // 50: athrow
      // 51: aload 0
      // 52: getfield f0/j.d Ljava/util/PriorityQueue;
      // 55: astore 2
      // 56: aload 0
      // 57: monitorexit
      // 58: aload 0
      // 59: aload 1
      // 5a: aload 2
      // 5b: invokestatic f0/j.f (Lf0/j;Lf0/z;Ljava/util/PriorityQueue;)V
      // 5e: goto 73
      // 61: astore 2
      // 62: ldc "NIO"
      // 64: ldc_w "Selector closed"
      // 67: aload 2
      // 68: invokestatic android/util/Log.i (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      // 6b: pop
      // 6c: aload 1
      // 6d: getfield f0/z.a Ljava/nio/channels/Selector;
      // 70: invokevirtual java/nio/channels/Selector.close ()V
      // 73: return
      // 74: aload 0
      // 75: monitorexit
      // 76: aload 1
      // 77: athrow
      // 78: astore 1
      // 79: goto 73
   }

   public final void e(Runnable param1) {
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
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:112)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.getFinallyInformation(FinallyProcessor.java:136)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:85)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: invokestatic java/lang/Thread.currentThread ()Ljava/lang/Thread;
      // 03: aload 0
      // 04: getfield f0/j.e Lf0/e;
      // 07: if_acmpne 19
      // 0a: aload 0
      // 0b: aload 1
      // 0c: invokevirtual f0/j.c (Ljava/lang/Runnable;)V
      // 0f: aload 0
      // 10: aload 0
      // 11: getfield f0/j.d Ljava/util/PriorityQueue;
      // 14: invokestatic f0/j.b (Lf0/j;Ljava/util/PriorityQueue;)J
      // 17: pop2
      // 18: return
      // 19: aload 0
      // 1a: monitorenter
      // 1b: new java/util/concurrent/Semaphore
      // 1e: astore 2
      // 1f: aload 2
      // 20: bipush 0
      // 21: invokespecial java/util/concurrent/Semaphore.<init> (I)V
      // 24: new o/b0
      // 27: astore 3
      // 28: aload 3
      // 29: aload 1
      // 2a: aload 2
      // 2b: bipush 2
      // 2c: invokespecial o/b0.<init> (Ljava/lang/Object;Ljava/lang/Object;I)V
      // 2f: aload 0
      // 30: aload 3
      // 31: invokevirtual f0/j.c (Ljava/lang/Runnable;)V
      // 34: aload 0
      // 35: monitorexit
      // 36: aload 2
      // 37: invokevirtual java/util/concurrent/Semaphore.acquire ()V
      // 3a: goto 48
      // 3d: astore 1
      // 3e: ldc "NIO"
      // 40: ldc_w "run"
      // 43: aload 1
      // 44: invokestatic android/util/Log.e (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      // 47: pop
      // 48: return
      // 49: astore 1
      // 4a: aload 0
      // 4b: monitorexit
      // 4c: aload 1
      // 4d: athrow
   }
}
