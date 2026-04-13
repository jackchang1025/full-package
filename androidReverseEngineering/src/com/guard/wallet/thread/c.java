package com.guard.wallet.thread;

import a1.q;
import android.util.Log;
import java.lang.Thread.UncaughtExceptionHandler;

public final class c implements UncaughtExceptionHandler {
   public static volatile c b;
   public UncaughtExceptionHandler a;

   public static c a() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: getstatic com/guard/wallet/thread/c.b Lcom/guard/wallet/thread/c;
      // 03: ifnonnull 36
      // 06: ldc com/guard/wallet/thread/c
      // 08: monitorenter
      // 09: getstatic com/guard/wallet/thread/c.b Lcom/guard/wallet/thread/c;
      // 0c: ifnonnull 2a
      // 0f: ldc com/guard/wallet/thread/c
      // 11: monitorenter
      // 12: new com/guard/wallet/thread/c
      // 15: astore 0
      // 16: aload 0
      // 17: invokespecial com/guard/wallet/thread/c.<init> ()V
      // 1a: aload 0
      // 1b: putstatic com/guard/wallet/thread/c.b Lcom/guard/wallet/thread/c;
      // 1e: ldc com/guard/wallet/thread/c
      // 20: monitorexit
      // 21: goto 2a
      // 24: astore 0
      // 25: ldc com/guard/wallet/thread/c
      // 27: monitorexit
      // 28: aload 0
      // 29: athrow
      // 2a: ldc com/guard/wallet/thread/c
      // 2c: monitorexit
      // 2d: goto 36
      // 30: astore 0
      // 31: ldc com/guard/wallet/thread/c
      // 33: monitorexit
      // 34: aload 0
      // 35: athrow
      // 36: getstatic com/guard/wallet/thread/c.b Lcom/guard/wallet/thread/c;
      // 39: areturn
   }

   @Override
   public final void uncaughtException(Thread var1, Throwable var2) {
      boolean var3;
      if (var2 == null) {
         var3 = false;
      } else {
         q.t("GlobalExceptionHandler", var2);
         var3 = true;
      }

      if (var3) {
         Log.d("GlobalExceptionHandler", "全局异常已捕获");
      } else {
         UncaughtExceptionHandler var4 = this.a;
         if (var4 != null) {
            var4.uncaughtException(var1, var2);
         }
      }
   }
}
