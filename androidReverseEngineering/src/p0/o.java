package p0;

import java.util.ArrayDeque;
import java.util.concurrent.ThreadPoolExecutor;

public final class o {
   public ThreadPoolExecutor a;
   public final ArrayDeque b = new ArrayDeque();
   public final ArrayDeque c = new ArrayDeque();
   public final ArrayDeque d = new ArrayDeque();

   public final d0 a(String var1) {
      for (d0 var2 : this.c) {
         if (var2.d.c.a.d.equals(var1)) {
            return var2;
         }
      }

      for (d0 var5 : this.b) {
         if (var5.d.c.a.d.equals(var1)) {
            return var5;
         }
      }

      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final void b(d0 var1) {
      var1.c.decrementAndGet();
      ArrayDeque var2 = this.c;
      synchronized (this){} // $VF: monitorenter 

      label107: {
         Throwable var10000;
         label102: {
            try {
               if (var2.remove(var1)) {
                  // $VF: monitorexit
                  break label107;
               }
            } catch (Throwable var14) {
               var10000 = var14;
               boolean var10001 = false;
               break label102;
            }

            label95:
            try {
               AssertionError var16 = new AssertionError("Call wasn't in-flight!");
               throw var16;
            } catch (Throwable var13) {
               var10000 = var13;
               boolean var17 = false;
               break label95;
            }
         }

         while (true) {
            Throwable var15 = var10000;

            try {
               // $VF: monitorexit
               throw var15;
            } catch (Throwable var12) {
               var10000 = var12;
               boolean var18 = false;
               continue;
            }
         }
      }

      this.c();
   }

   public final void c() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 000: new java/util/ArrayList
      // 003: dup
      // 004: invokespecial java/util/ArrayList.<init> ()V
      // 007: astore 3
      // 008: aload 0
      // 009: monitorenter
      // 00a: aload 0
      // 00b: getfield p0/o.b Ljava/util/ArrayDeque;
      // 00e: invokevirtual java/util/ArrayDeque.iterator ()Ljava/util/Iterator;
      // 011: astore 5
      // 013: aload 5
      // 015: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 01a: ifeq 06b
      // 01d: aload 5
      // 01f: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 024: checkcast p0/d0
      // 027: astore 4
      // 029: aload 0
      // 02a: getfield p0/o.c Ljava/util/ArrayDeque;
      // 02d: invokevirtual java/util/ArrayDeque.size ()I
      // 030: bipush 64
      // 032: if_icmplt 038
      // 035: goto 06b
      // 038: aload 4
      // 03a: getfield p0/d0.c Ljava/util/concurrent/atomic/AtomicInteger;
      // 03d: invokevirtual java/util/concurrent/atomic/AtomicInteger.get ()I
      // 040: bipush 5
      // 041: if_icmplt 047
      // 044: goto 013
      // 047: aload 5
      // 049: invokeinterface java/util/Iterator.remove ()V 1
      // 04e: aload 4
      // 050: getfield p0/d0.c Ljava/util/concurrent/atomic/AtomicInteger;
      // 053: invokevirtual java/util/concurrent/atomic/AtomicInteger.incrementAndGet ()I
      // 056: pop
      // 057: aload 3
      // 058: aload 4
      // 05a: invokevirtual java/util/ArrayList.add (Ljava/lang/Object;)Z
      // 05d: pop
      // 05e: aload 0
      // 05f: getfield p0/o.c Ljava/util/ArrayDeque;
      // 062: aload 4
      // 064: invokevirtual java/util/ArrayDeque.add (Ljava/lang/Object;)Z
      // 067: pop
      // 068: goto 013
      // 06b: aload 0
      // 06c: monitorenter
      // 06d: aload 0
      // 06e: getfield p0/o.c Ljava/util/ArrayDeque;
      // 071: invokevirtual java/util/ArrayDeque.size ()I
      // 074: pop
      // 075: aload 0
      // 076: getfield p0/o.d Ljava/util/ArrayDeque;
      // 079: invokevirtual java/util/ArrayDeque.size ()I
      // 07c: pop
      // 07d: aload 0
      // 07e: monitorexit
      // 07f: aload 0
      // 080: monitorexit
      // 081: aload 3
      // 082: invokevirtual java/util/ArrayList.size ()I
      // 085: istore 2
      // 086: bipush 0
      // 087: istore 1
      // 088: iload 1
      // 089: iload 2
      // 08a: if_icmpge 150
      // 08d: aload 3
      // 08e: iload 1
      // 08f: invokevirtual java/util/ArrayList.get (I)Ljava/lang/Object;
      // 092: checkcast p0/d0
      // 095: astore 4
      // 097: aload 0
      // 098: monitorenter
      // 099: aload 0
      // 09a: getfield p0/o.a Ljava/util/concurrent/ThreadPoolExecutor;
      // 09d: ifnonnull 0dd
      // 0a0: new java/util/concurrent/ThreadPoolExecutor
      // 0a3: astore 5
      // 0a5: getstatic java/util/concurrent/TimeUnit.SECONDS Ljava/util/concurrent/TimeUnit;
      // 0a8: astore 6
      // 0aa: new java/util/concurrent/SynchronousQueue
      // 0ad: astore 7
      // 0af: aload 7
      // 0b1: invokespecial java/util/concurrent/SynchronousQueue.<init> ()V
      // 0b4: getstatic q0/c.a [B
      // 0b7: astore 8
      // 0b9: new q0/b
      // 0bc: astore 8
      // 0be: aload 8
      // 0c0: ldc "OkHttp Dispatcher"
      // 0c2: bipush 0
      // 0c3: invokespecial q0/b.<init> (Ljava/lang/String;Z)V
      // 0c6: aload 5
      // 0c8: bipush 0
      // 0c9: ldc 2147483647
      // 0cb: ldc2_w 60
      // 0ce: aload 6
      // 0d0: aload 7
      // 0d2: aload 8
      // 0d4: invokespecial java/util/concurrent/ThreadPoolExecutor.<init> (IIJLjava/util/concurrent/TimeUnit;Ljava/util/concurrent/BlockingQueue;Ljava/util/concurrent/ThreadFactory;)V
      // 0d7: aload 0
      // 0d8: aload 5
      // 0da: putfield p0/o.a Ljava/util/concurrent/ThreadPoolExecutor;
      // 0dd: aload 0
      // 0de: getfield p0/o.a Ljava/util/concurrent/ThreadPoolExecutor;
      // 0e1: astore 6
      // 0e3: aload 0
      // 0e4: monitorexit
      // 0e5: aload 4
      // 0e7: getfield p0/d0.d Lp0/e0;
      // 0ea: astore 5
      // 0ec: aload 6
      // 0ee: aload 4
      // 0f0: invokevirtual java/util/concurrent/ThreadPoolExecutor.execute (Ljava/lang/Runnable;)V
      // 0f3: goto 136
      // 0f6: astore 3
      // 0f7: goto 13c
      // 0fa: astore 6
      // 0fc: new java/io/InterruptedIOException
      // 0ff: astore 7
      // 101: aload 7
      // 103: ldc "executor rejected"
      // 105: invokespecial java/io/InterruptedIOException.<init> (Ljava/lang/String;)V
      // 108: aload 7
      // 10a: aload 6
      // 10c: invokevirtual java/lang/Throwable.initCause (Ljava/lang/Throwable;)Ljava/lang/Throwable;
      // 10f: pop
      // 110: aload 5
      // 112: getfield p0/e0.b Ls0/l;
      // 115: aload 7
      // 117: invokevirtual s0/l.e (Ljava/io/IOException;)Ljava/io/IOException;
      // 11a: pop
      // 11b: aload 4
      // 11d: getfield p0/d0.b Lp0/e;
      // 120: aload 5
      // 122: aload 7
      // 124: invokeinterface p0/e.b (Lp0/e0;Ljava/io/IOException;)V 3
      // 129: aload 5
      // 12b: getfield p0/e0.a Lp0/b0;
      // 12e: getfield p0/b0.a Lp0/o;
      // 131: aload 4
      // 133: invokevirtual p0/o.b (Lp0/d0;)V
      // 136: iinc 1 1
      // 139: goto 088
      // 13c: aload 5
      // 13e: getfield p0/e0.a Lp0/b0;
      // 141: getfield p0/b0.a Lp0/o;
      // 144: aload 4
      // 146: invokevirtual p0/o.b (Lp0/d0;)V
      // 149: aload 3
      // 14a: athrow
      // 14b: astore 3
      // 14c: aload 0
      // 14d: monitorexit
      // 14e: aload 3
      // 14f: athrow
      // 150: return
      // 151: astore 3
      // 152: aload 0
      // 153: monitorexit
      // 154: aload 3
      // 155: athrow
      // 156: astore 3
      // 157: aload 0
      // 158: monitorexit
      // 159: aload 3
      // 15a: athrow
   }
}
