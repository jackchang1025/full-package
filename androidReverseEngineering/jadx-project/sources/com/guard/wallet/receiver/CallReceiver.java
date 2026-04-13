package com.guard.wallet.receiver;

import a1.AbstractC0026q;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.guard.wallet.MainApplication;
import com.guard.wallet.req.MessageRecordVO;
import com.guard.wallet.resp.CallMessageVO;

/* loaded from: classes.dex */
public class CallReceiver extends BroadcastReceiver {

    /* renamed from: a */
    public Integer f278a = 0;

    /* JADX WARN: Removed duplicated region for block: B:18:0x0061 A[Catch: Exception -> 0x0078, TRY_LEAVE, TryCatch #0 {Exception -> 0x0078, blocks: (B:3:0x0005, B:5:0x0015, B:6:0x0026, B:8:0x0030, B:15:0x0040, B:16:0x0052, B:18:0x0061, B:24:0x0049, B:26:0x001d), top: B:2:0x0005 }] */
    /* JADX WARN: Removed duplicated region for block: B:22:? A[RETURN, SYNTHETIC] */
    @Override // android.content.BroadcastReceiver
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onReceive(Context context, Intent intent) {
        String stringExtra;
        int i2;
        String str;
        String str2;
        String str3 = "android.intent.action.NEW_OUTGOING_CALL";
        try {
            this.f278a = 1;
            if ("android.intent.action.NEW_OUTGOING_CALL".equals(intent.getAction())) {
                stringExtra = intent.getStringExtra("android.intent.extra.PHONE_NUMBER");
                i2 = 0;
            } else {
                str3 = "android.intent.action.PHONE_STATE";
                stringExtra = intent.getStringExtra("incoming_number");
                i2 = 1;
            }
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (telephonyManager != null) {
                int callState = telephonyManager.getCallState();
                if (callState != 0) {
                    if (callState == 1) {
                        str = "CALL_STATE_RINGING";
                        str2 = "电话响铃中...";
                    } else if (callState == 2) {
                        str = "CALL_STATE_OFFHOOK";
                        str2 = "电话接通中...";
                    }
                    Log.d("CallReceiver", str2);
                } else {
                    Log.d("CallReceiver", "电话挂断...");
                    str = "CALL_STATE_IDLE";
                }
                CallMessageVO callMessageVO = new CallMessageVO(Integer.valueOf(i2), stringExtra, str);
                if (AbstractC0026q.m151B(str3)) {
                    MessageRecordVO messageRecordVO = new MessageRecordVO();
                    messageRecordVO.setExtraBody(callMessageVO);
                    messageRecordVO.setIntentCode(str3);
                    MainApplication.getInstance().getHandlerMsgAndTimer().m579b(messageRecordVO);
                    return;
                }
                return;
            }
            str = null;
            CallMessageVO callMessageVO2 = new CallMessageVO(Integer.valueOf(i2), stringExtra, str);
            if (AbstractC0026q.m151B(str3)) {
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("CallReceiver", e2);
        }
    }
}
