package com.guard.wallet.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import c0.a;

public class SyncService extends Service {
   public static a a;
   public static final Object b = new Object();

   public final IBinder onBind(Intent var1) {
      return a.getSyncAdapterBinder();
   }

   public final void onCreate() {
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
      // 00: aload 0
      // 01: invokespecial android/app/Service.onCreate ()V
      // 04: getstatic com/guard/wallet/sync/SyncService.b Ljava/lang/Object;
      // 07: astore 1
      // 08: aload 1
      // 09: monitorenter
      // 0a: new c0/a
      // 0d: astore 2
      // 0e: aload 2
      // 0f: aload 0
      // 10: invokevirtual android/content/Context.getApplicationContext ()Landroid/content/Context;
      // 13: invokespecial c0/a.<init> (Landroid/content/Context;)V
      // 16: aload 2
      // 17: putstatic com/guard/wallet/sync/SyncService.a Lc0/a;
      // 1a: aload 1
      // 1b: monitorexit
      // 1c: return
      // 1d: astore 2
      // 1e: aload 1
      // 1f: monitorexit
      // 20: aload 2
      // 21: athrow
   }
}
