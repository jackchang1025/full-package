package com.vendor.rat.data.collector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.vendor.rat.service.MyAccessibilityService;

import java.util.Objects;

/**
 * ADAPT: vendor com.guard.wallet.receiver.AlarmReceiver
 * Handles custom alarm actions and accessibility pause/resume.
 * Note: keepalive/receiver/AlarmReceiver is a separate MODULE_07 stub.
 */
public class AlarmReceiver extends BroadcastReceiver {

    private static final String TAG = "AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            String alarmAction = context.getPackageName().concat(".alarm.action");
            if (Objects.equals(alarmAction, intent.getAction())) {
                Log.d(TAG, "onReceive:" + alarmAction);
                // ADAPT: vendor calls e.S().B.set(true) — heartbeat flag
                // TODO: VENDOR_VERIFY — set heartbeat alive flag
                return;
            }

            String pauseAction = context.getPackageName().concat(".pause.accessibility");
            String resumeAction = context.getPackageName().concat(".resume.accessibility");

            if (Objects.equals(pauseAction, intent.getAction())) {
                Log.d(TAG, "onReceive 暂停 Accessibility Service");
                // ADAPT: vendor sets MyAccessibilityService.f220r.set(true)
                // TODO: VENDOR_VERIFY — pause accessibility
            } else if (Objects.equals(resumeAction, intent.getAction())) {
                Log.d(TAG, "onReceive 恢复 Accessibility Service");
                // ADAPT: vendor sets MyAccessibilityService.f220r.set(false)
                // TODO: VENDOR_VERIFY — resume accessibility
            }
        } catch (Exception e) {
            Log.e(TAG, "Error in onReceive", e);
        }
    }
}
