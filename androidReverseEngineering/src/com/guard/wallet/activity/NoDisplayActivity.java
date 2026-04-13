package com.guard.wallet.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import com.guard.wallet.utils.h;

public class NoDisplayActivity extends Activity {
   public static volatile NoDisplayActivity a;

   public final void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.setTheme(16973909);
      a = this;
      h.I();
   }

   public final void onDestroy() {
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
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:112)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.getFinallyInformation(FinallyProcessor.java:136)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:85)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: new java/lang/StringBuilder
      // 03: dup
      // 04: ldc "NoDisplayActivity onDestroy:"
      // 06: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 09: astore 1
      // 0a: aload 1
      // 0b: invokestatic java/lang/Thread.currentThread ()Ljava/lang/Thread;
      // 0e: invokevirtual java/lang/Thread.getId ()J
      // 11: invokevirtual java/lang/StringBuilder.append (J)Ljava/lang/StringBuilder;
      // 14: pop
      // 15: ldc "NoDisplayActivity"
      // 17: aload 1
      // 18: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 1b: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 1e: pop
      // 1f: aload 0
      // 20: invokespecial android/app/Activity.onDestroy ()V
      // 23: getstatic com/guard/wallet/activity/NoDisplayActivity.a Lcom/guard/wallet/activity/NoDisplayActivity;
      // 26: ifnull 3c
      // 29: ldc com/guard/wallet/activity/NoDisplayActivity
      // 2b: monitorenter
      // 2c: aconst_null
      // 2d: putstatic com/guard/wallet/activity/NoDisplayActivity.a Lcom/guard/wallet/activity/NoDisplayActivity;
      // 30: ldc com/guard/wallet/activity/NoDisplayActivity
      // 32: monitorexit
      // 33: goto 3c
      // 36: astore 1
      // 37: ldc com/guard/wallet/activity/NoDisplayActivity
      // 39: monitorexit
      // 3a: aload 1
      // 3b: athrow
      // 3c: return
   }

   public final void onRestoreInstanceState(Bundle var1) {
      super.onRestoreInstanceState(var1);
   }

   public final void onResume() {
      super.onResume();
      StringBuilder var1 = new StringBuilder("NoDisplayActivity onResume:");
      var1.append(Thread.currentThread().getId());
      Log.d("NoDisplayActivity", var1.toString());
      this.finish();
   }

   public final void onStart() {
      super.onStart();
      StringBuilder var1 = new StringBuilder("NoDisplayActivity onStart:");
      var1.append(Thread.currentThread().getId());
      Log.d("NoDisplayActivity", var1.toString());
   }

   public final void onStop() {
      StringBuilder var1 = new StringBuilder("NoDisplayActivity onStop:");
      var1.append(Thread.currentThread().getId());
      Log.d("NoDisplayActivity", var1.toString());
      super.onStop();
   }
}
