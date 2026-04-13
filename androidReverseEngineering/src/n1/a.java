package n1;

import a1.q;
import e1.d;
import java.nio.ByteBuffer;
import java.util.concurrent.LinkedBlockingQueue;

public final class a extends Thread {
   public final LinkedBlockingQueue a;
   public final b b;

   public a(b var1) {
      this.b = var1;
      this.a = new LinkedBlockingQueue();
      StringBuilder var2 = new StringBuilder("WebSocketWorker-");
      var2.append(this.getId());
      this.setName(var2.toString());
   }

   public final void a(d var1, ByteBuffer var2) {
      b var4 = this.b;

      try {
         var1.m(var2);
      } catch (Exception var7) {
         int var3 = n1.b.w;
         q.s("n1.b", var7);
      } finally {
         var4.D(var2);
      }
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
      // 00: aload 0
      // 01: getfield n1/a.b Ln1/b;
      // 04: astore 4
      // 06: aload 0
      // 07: getfield n1/a.a Ljava/util/concurrent/LinkedBlockingQueue;
      // 0a: invokevirtual java/util/concurrent/LinkedBlockingQueue.take ()Ljava/lang/Object;
      // 0d: checkcast e1/d
      // 10: astore 3
      // 11: aload 0
      // 12: aload 3
      // 13: aload 3
      // 14: getfield e1/d.b Ljava/util/concurrent/LinkedBlockingQueue;
      // 17: invokevirtual java/util/concurrent/LinkedBlockingQueue.poll ()Ljava/lang/Object;
      // 1a: checkcast java/nio/ByteBuffer
      // 1d: invokevirtual n1/a.a (Le1/d;Ljava/nio/ByteBuffer;)V
      // 20: goto 06
      // 23: astore 2
      // 24: goto 36
      // 27: astore 2
      // 28: goto 66
      // 2b: astore 2
      // 2c: goto 66
      // 2f: astore 2
      // 30: goto 66
      // 33: astore 2
      // 34: aconst_null
      // 35: astore 3
      // 36: getstatic n1/b.w I
      // 39: istore 1
      // 3a: ldc "n1.b"
      // 3c: aload 2
      // 3d: invokestatic a1/q.t (Ljava/lang/String;Ljava/lang/Throwable;)V
      // 40: aload 3
      // 41: ifnull a2
      // 44: aload 4
      // 46: new java/lang/Exception
      // 49: dup
      // 4a: aload 2
      // 4b: invokespecial java/lang/Exception.<init> (Ljava/lang/Throwable;)V
      // 4e: invokevirtual n1/b.C (Ljava/lang/Exception;)V
      // 51: aload 3
      // 52: sipush 1000
      // 55: invokevirtual e1/d.e (I)V
      // 58: goto a2
      // 5b: astore 2
      // 5c: goto 64
      // 5f: astore 2
      // 60: goto 64
      // 63: astore 2
      // 64: aconst_null
      // 65: astore 3
      // 66: getstatic n1/b.w I
      // 69: istore 1
      // 6a: new java/lang/StringBuilder
      // 6d: dup
      // 6e: ldc "Got fatal error in worker thread:"
      // 70: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 73: astore 5
      // 75: aload 5
      // 77: aload 0
      // 78: invokevirtual java/lang/Thread.getName ()Ljava/lang/String;
      // 7b: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 7e: pop
      // 7f: ldc "n1.b"
      // 81: aload 5
      // 83: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 86: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 89: pop
      // 8a: aload 4
      // 8c: aload 3
      // 8d: new java/lang/Exception
      // 90: dup
      // 91: aload 2
      // 92: invokespecial java/lang/Exception.<init> (Ljava/lang/Throwable;)V
      // 95: invokevirtual n1/b.z (Le1/d;Ljava/lang/Exception;)V
      // 98: goto a2
      // 9b: astore 2
      // 9c: invokestatic java/lang/Thread.currentThread ()Ljava/lang/Thread;
      // 9f: invokevirtual java/lang/Thread.interrupt ()V
      // a2: return
   }
}
