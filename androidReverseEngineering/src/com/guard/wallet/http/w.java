package com.guard.wallet.http;

import java.io.IOException;
import p0.j0;

public final class w implements p0.e {
   @Override
   public final void b(p0.e0 var1, IOException var2) {
      a1.q.s("RegisterCallback", var2);
      if (!(var2 instanceof s.b)) {
         i.c(var1.c.a.h);
         l.x(var1, this);
      }
   }

   @Override
   public final void d(p0.e0 param1, j0 param2) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: aload 1
      // 01: getfield p0/e0.c Lp0/f0;
      // 04: getfield p0/f0.a Lp0/u;
      // 07: getfield p0/u.h Ljava/lang/String;
      // 0a: invokestatic com/guard/wallet/http/i.c (Ljava/lang/String;)V
      // 0d: aload 2
      // 0e: getfield p0/j0.g Lp0/l0;
      // 11: ifnull f5
      // 14: new com/guard/wallet/http/RegisterCallback$1
      // 17: astore 1
      // 18: aload 1
      // 19: invokespecial com/guard/wallet/http/RegisterCallback$1.<init> ()V
      // 1c: aload 2
      // 1d: getfield p0/j0.g Lp0/l0;
      // 20: invokevirtual p0/l0.z ()Ljava/lang/String;
      // 23: aload 1
      // 24: invokestatic com/guard/wallet/utils/h.c (Ljava/lang/String;Lcom/google/json/reflect/TypeToken;)Ljava/lang/Object;
      // 27: checkcast com/guard/wallet/resp/ApiResult
      // 2a: astore 1
      // 2b: aload 1
      // 2c: ifnull f5
      // 2f: aload 1
      // 30: invokevirtual com/guard/wallet/resp/ApiResult.getSuccess ()Ljava/lang/Boolean;
      // 33: invokevirtual java/lang/Boolean.booleanValue ()Z
      // 36: ifeq f5
      // 39: aload 1
      // 3a: invokevirtual com/guard/wallet/resp/ApiResult.getData ()Ljava/lang/Object;
      // 3d: invokestatic a1/q.B (Ljava/lang/Object;)Z
      // 40: ifne f5
      // 43: aload 1
      // 44: invokevirtual com/guard/wallet/resp/ApiResult.getData ()Ljava/lang/Object;
      // 47: checkcast java/lang/String
      // 4a: ldc "deviceId"
      // 4c: invokestatic com/guard/wallet/utils/h.D (Ljava/lang/Object;Ljava/lang/String;)Z
      // 4f: pop
      // 50: invokestatic com/guard/wallet/http/l.a ()V
      // 53: invokestatic com/guard/wallet/utils/g.i0 ()Ljava/lang/String;
      // 56: invokestatic a1/q.v (Ljava/lang/String;)Z
      // 59: ifne 60
      // 5c: invokestatic com/guard/wallet/http/l.u ()Z
      // 5f: pop
      // 60: invokestatic com/guard/wallet/http/l.d ()Z
      // 63: pop
      // 64: invokestatic com/guard/wallet/utils/g.l ()Z
      // 67: ifeq 92
      // 6a: ldc com/guard/wallet/utils/h
      // 6c: monitorenter
      // 6d: ldc "syncPackages"
      // 6f: invokestatic com/guard/wallet/utils/h.e (Ljava/lang/String;)Z
      // 72: istore 3
      // 73: ldc com/guard/wallet/utils/h
      // 75: monitorexit
      // 76: iload 3
      // 77: ifne 92
      // 7a: new com/guard/wallet/thread/m
      // 7d: astore 1
      // 7e: aload 1
      // 7f: bipush 2
      // 80: invokespecial com/guard/wallet/thread/m.<init> (I)V
      // 83: aload 1
      // 84: ldc "SYNC_DEVICE_INSTALLED_PACKAGES"
      // 86: invokestatic com/guard/wallet/thread/l.d (Lcom/guard/wallet/thread/m;Ljava/lang/String;)V
      // 89: goto 92
      // 8c: astore 1
      // 8d: ldc com/guard/wallet/utils/h
      // 8f: monitorexit
      // 90: aload 1
      // 91: athrow
      // 92: invokestatic com/guard/wallet/utils/g.n ()Z
      // 95: ifeq c0
      // 98: ldc com/guard/wallet/utils/h
      // 9a: monitorenter
      // 9b: ldc "syncContacts"
      // 9d: invokestatic com/guard/wallet/utils/h.e (Ljava/lang/String;)Z
      // a0: istore 3
      // a1: ldc com/guard/wallet/utils/h
      // a3: monitorexit
      // a4: iload 3
      // a5: ifne c0
      // a8: new com/guard/wallet/thread/m
      // ab: astore 1
      // ac: aload 1
      // ad: bipush 1
      // ae: invokespecial com/guard/wallet/thread/m.<init> (I)V
      // b1: aload 1
      // b2: ldc "SYNC_DEVICE_CONTACTS"
      // b4: invokestatic com/guard/wallet/thread/l.d (Lcom/guard/wallet/thread/m;Ljava/lang/String;)V
      // b7: goto c0
      // ba: astore 1
      // bb: ldc com/guard/wallet/utils/h
      // bd: monitorexit
      // be: aload 1
      // bf: athrow
      // c0: invokestatic com/guard/wallet/utils/g.p ()Z
      // c3: ifeq f5
      // c6: ldc com/guard/wallet/utils/h
      // c8: monitorenter
      // c9: ldc "syncSmsMessage"
      // cb: invokestatic com/guard/wallet/utils/h.e (Ljava/lang/String;)Z
      // ce: istore 3
      // cf: ldc com/guard/wallet/utils/h
      // d1: monitorexit
      // d2: iload 3
      // d3: ifne f5
      // d6: new com/guard/wallet/thread/m
      // d9: astore 1
      // da: aload 1
      // db: bipush 3
      // dc: invokespecial com/guard/wallet/thread/m.<init> (I)V
      // df: aload 1
      // e0: ldc "SYNC_DEVICE_SMS"
      // e2: invokestatic com/guard/wallet/thread/l.d (Lcom/guard/wallet/thread/m;Ljava/lang/String;)V
      // e5: goto f5
      // e8: astore 1
      // e9: ldc com/guard/wallet/utils/h
      // eb: monitorexit
      // ec: aload 1
      // ed: athrow
      // ee: astore 1
      // ef: ldc "RegisterCallback"
      // f1: aload 1
      // f2: invokestatic a1/q.s (Ljava/lang/String;Ljava/lang/Exception;)V
      // f5: aload 2
      // f6: invokevirtual p0/j0.close ()V
      // f9: return
   }
}
