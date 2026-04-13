package com.guard.wallet.receiver;

import com.guard.wallet.core.AppUtils;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;
import com.guard.wallet.MainApplication;
import com.guard.wallet.req.MessageBodyVO;
import com.guard.wallet.req.MessageRecordVO;
import com.guard.wallet.utils.SystemHelper;
import com.guard.wallet.utils.SharedPrefsManager;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * vendor PowerBroadcastReceiver — handles power connected/disconnected,
 * power save mode changes, and power usage summary.
 */
public class PowerBroadcastReceiver extends BroadcastReceiver {
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

            if ("android.os.action.POWER_SAVE_MODE_CHANGED".equals(action)) {
                // Power save mode changed
                Log.d("PowerBroadcastReceiver", "省点模式 ACTION_POWER_SAVE_MODE_CHANGED");
                updatePowerSaveFlag();
            } else if ("android.intent.action.ACTION_POWER_CONNECTED".equals(action)) {
                Log.d("PowerBroadcastReceiver", "充电线已连接 ACTION_POWER_CONNECTED");
            } else if ("android.intent.action.ACTION_POWER_DISCONNECTED".equals(action)) {
                Log.d("PowerBroadcastReceiver", "充电线已断开 ACTION_POWER_DISCONNECTED");
            } else if ("android.intent.action.POWER_USAGE_SUMMARY".equals(action)) {
                Log.d("PowerBroadcastReceiver", "电力使用情况总结 ACTION_POWER_USAGE_SUMMARY");
                // Also update power save flag for USAGE_SUMMARY
                Log.d("PowerBroadcastReceiver", "省点模式 ACTION_POWER_SAVE_MODE_CHANGED");
                updatePowerSaveFlag();
            }

            // Send event to server
            MessageRecordVO record = new MessageRecordVO();
            MessageBodyVO body = new MessageBodyVO();
            record.setIntentCode(var2.getAction());
            record.setExtraBody(body);
            if (MainApplication.getInstance() != null && MainApplication.getInstance().getHandlerMsgAndTimer() != null) {
                MainApplication.getInstance().getHandlerMsgAndTimer().b(record);
            }
        } catch (Exception var26) {
            AppUtils.s("PowerBroadcastReceiver", var26);
        }
    }

    /**
     * Checks system power save mode and battery level to determine
     * whether to set the global power save flag.
     */
    private void updatePowerSaveFlag() {
        boolean isPowerSaveMode = false;
        if (SystemHelper.Z() != null) {
            PowerManager pm = (PowerManager) SystemHelper.Z().getSystemService("power");
            if (pm != null) {
                isPowerSaveMode = pm.isPowerSaveMode();
            }
        }

        AtomicBoolean powerSaveFlag = com.guard.wallet.power.PowerSaveChecker.inPowerSaveMode;
        boolean shouldSet = false;
        if (isPowerSaveMode) {
            float storedPercent = SharedPrefsManager.h();
            if (storedPercent > 0.0F && storedPercent < 5.0F) {
                shouldSet = true;
            }
        }
        powerSaveFlag.set(shouldSet);
    }
}
