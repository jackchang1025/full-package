package com.guard.wallet.receiver;

import a1.AbstractC0026q;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import com.guard.wallet.req.BatteryLevelVO;
import com.guard.wallet.req.MessageRecordVO;
import com.guard.wallet.utils.AbstractC0252h;
import java.util.concurrent.atomic.AtomicBoolean;
import p015s.C0896a;
import p019w.AbstractC0956a;

/* loaded from: classes.dex */
public class BatteryLevelReceiver extends BroadcastReceiver {

    /* renamed from: a */
    public Integer f275a = 0;

    /* renamed from: b */
    public final C0896a f276b = new C0896a(30000, 10);

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        boolean z2;
        int i2 = 1;
        try {
            this.f275a = 1;
            if (intent != null) {
                int intExtra = intent.getIntExtra("level", 0);
                int intExtra2 = intent.getIntExtra("scale", 0);
                int intExtra3 = intent.getIntExtra(NotificationCompat.CATEGORY_STATUS, 1);
                int intExtra4 = intent.getIntExtra("health", 1);
                int intExtra5 = intent.getIntExtra("voltage", 0);
                int intExtra6 = intent.getIntExtra("temperature", 0);
                String stringExtra = intent.getStringExtra("technology");
                int intExtra7 = intent.getIntExtra("plugged", -1);
                float f2 = (intExtra2 > 0 ? intExtra / intExtra2 : 0.0f) * 100.0f;
                if (AbstractC0252h.m715s()) {
                    AbstractC0252h.m683D(Integer.valueOf(intExtra3), "batteryStatus");
                    AbstractC0252h.m683D(Integer.valueOf(intExtra4), "batteryHealth");
                    AbstractC0252h.m683D(Integer.valueOf(intExtra5), "batteryVoltage");
                    AbstractC0252h.m683D(Integer.valueOf(intExtra6), "batteryTemperature");
                    AbstractC0252h.m683D(Float.valueOf(f2), "batteryPercent");
                }
                if (f2 > 0.0f) {
                    boolean z3 = f2 < 20.0f;
                    AtomicBoolean atomicBoolean = AbstractC0956a.f2271a;
                    if (z3) {
                        float m704h = AbstractC0252h.m704h();
                        if (m704h > 0.0f) {
                            if (m704h < 5.0f) {
                                z2 = true;
                                atomicBoolean.set(z2);
                            }
                        }
                    }
                    z2 = false;
                    atomicBoolean.set(z2);
                }
                BatteryLevelVO batteryLevelVO = new BatteryLevelVO();
                batteryLevelVO.setStatus(Integer.valueOf(intExtra3));
                batteryLevelVO.setVoltage(Integer.valueOf(intExtra5));
                batteryLevelVO.setTemperature(Integer.valueOf(intExtra6));
                batteryLevelVO.setPercent(Float.valueOf(f2));
                if (!AbstractC0956a.f2271a.get()) {
                    i2 = 0;
                }
                batteryLevelVO.setInPowerSaveMode(Integer.valueOf(i2));
                batteryLevelVO.setHealth(Integer.valueOf(intExtra4));
                batteryLevelVO.setPlugged(Integer.valueOf(intExtra7));
                batteryLevelVO.setTechnology(stringExtra);
                MessageRecordVO messageRecordVO = new MessageRecordVO();
                messageRecordVO.setIntentCode(intent.getAction());
                messageRecordVO.setExtraBody(batteryLevelVO);
                this.f276b.m1330a(messageRecordVO);
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("BatteryLevelReceiver", e2);
        }
    }
}
