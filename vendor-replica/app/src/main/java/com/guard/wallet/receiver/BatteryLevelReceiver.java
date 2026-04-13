package com.guard.wallet.receiver;

import com.guard.wallet.core.AppUtils;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.guard.wallet.req.BatteryLevelVO;
import com.guard.wallet.req.MessageRecordVO;
import com.guard.wallet.utils.SharedPrefsManager;
import com.guard.wallet.model.EventEntity;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * vendor BatteryLevelReceiver — monitors battery status and reports via throttled events.
 */
public class BatteryLevelReceiver extends BroadcastReceiver {
    public Integer a = 0;
    public final EventEntity b = new EventEntity(30000, 10);

    @Override
    public final void onReceive(Context var1, Intent var2) {
        try {
            this.a = 1;
            if (var2 == null) {
                return;
            }

            int level = var2.getIntExtra("level", 0);
            int scale = var2.getIntExtra("scale", 0);
            int status = var2.getIntExtra("status", 1);
            int health = var2.getIntExtra("health", 1);
            int voltage = var2.getIntExtra("voltage", 0);
            int temperature = var2.getIntExtra("temperature", 0);
            String technology = var2.getStringExtra("technology");
            int plugged = var2.getIntExtra("plugged", -1);

            float ratio;
            if (scale > 0) {
                ratio = (float) level / (float) scale;
            } else {
                ratio = 0.0F;
            }
            float percent = ratio * 100.0F;

            if (SharedPrefsManager.s()) {
                SharedPrefsManager.D(status, "batteryStatus");
                SharedPrefsManager.D(health, "batteryHealth");
                SharedPrefsManager.D(voltage, "batteryVoltage");
                SharedPrefsManager.D(temperature, "batteryTemperature");
                SharedPrefsManager.D(percent, "batteryPercent");
            }

            if (percent > 0.0F) {
                boolean isLowBattery = percent < 20.0F;
                AtomicBoolean powerSaveFlag = com.guard.wallet.power.PowerSaveChecker.inPowerSaveMode;

                boolean shouldEnterPowerSave = false;
                if (isLowBattery) {
                    float storedPercent = SharedPrefsManager.h();
                    if (storedPercent > 0.0F && storedPercent < 5.0F) {
                        shouldEnterPowerSave = true;
                    }
                }
                powerSaveFlag.set(shouldEnterPowerSave);
            }

            BatteryLevelVO batteryVO = new BatteryLevelVO();
            batteryVO.setStatus(status);
            batteryVO.setVoltage(voltage);
            batteryVO.setTemperature(temperature);
            batteryVO.setPercent(percent);
            batteryVO.setInPowerSaveMode(Integer.valueOf(com.guard.wallet.power.PowerSaveChecker.inPowerSaveMode.get() ? 1 : 0));
            batteryVO.setHealth(health);
            batteryVO.setPlugged(plugged);
            batteryVO.setTechnology(technology);

            MessageRecordVO record = new MessageRecordVO();
            record.setIntentCode(var2.getAction());
            record.setExtraBody(batteryVO);
            this.b.dispatch(record);
        } catch (Exception var24) {
            AppUtils.s("BatteryLevelReceiver", var24);
        }
    }
}
