package com.guard.wallet.receiver;

import a1.AbstractC0026q;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.guard.wallet.MainApplication;
import com.guard.wallet.req.MessageBodyVO;
import com.guard.wallet.req.MessageRecordVO;

/* loaded from: classes.dex */
public class ShutDownBroadcastReceiver extends BroadcastReceiver {

    /* renamed from: a */
    public Integer f285a = 0;

    /* JADX WARN: Removed duplicated region for block: B:14:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0065 A[Catch: Exception -> 0x007b, TryCatch #0 {Exception -> 0x007b, blocks: (B:3:0x0003, B:5:0x000b, B:7:0x0015, B:16:0x004b, B:18:0x0065, B:20:0x006f, B:27:0x0044, B:29:0x0028, B:32:0x0032), top: B:2:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:25:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0048  */
    @Override // android.content.BroadcastReceiver
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onReceive(Context context, Intent intent) {
        char c;
        String str;
        try {
            this.f285a = 1;
        } catch (Exception e2) {
            AbstractC0026q.m186s("ShutDownBroadcastReceiver", e2);
        }
        if (intent == null || AbstractC0026q.m151B(intent.getAction())) {
            return;
        }
        String action = intent.getAction();
        int hashCode = action.hashCode();
        if (hashCode != 422449615) {
            if (hashCode == 1947666138 && action.equals("android.intent.action.ACTION_SHUTDOWN")) {
                c = 0;
                if (c != 0) {
                    str = "手机关机了 ACTION_SHUTDOWN";
                } else {
                    if (c != 1) {
                        MessageRecordVO messageRecordVO = new MessageRecordVO();
                        MessageBodyVO messageBodyVO = new MessageBodyVO();
                        messageRecordVO.setIntentCode(intent.getAction());
                        messageRecordVO.setExtraBody(messageBodyVO);
                        if (MainApplication.getInstance() == null || MainApplication.getInstance().getHandlerMsgAndTimer() == null) {
                            return;
                        }
                        MainApplication.getInstance().getHandlerMsgAndTimer().m579b(messageRecordVO);
                        return;
                    }
                    str = "手机关机了 QUICKBOOT_POWEROFF";
                }
                Log.d("ShutDownBroadcastReceiver", str);
                MessageRecordVO messageRecordVO2 = new MessageRecordVO();
                MessageBodyVO messageBodyVO2 = new MessageBodyVO();
                messageRecordVO2.setIntentCode(intent.getAction());
                messageRecordVO2.setExtraBody(messageBodyVO2);
                if (MainApplication.getInstance() == null) {
                    return;
                } else {
                    return;
                }
            }
            c = 65535;
            if (c != 0) {
            }
            Log.d("ShutDownBroadcastReceiver", str);
            MessageRecordVO messageRecordVO22 = new MessageRecordVO();
            MessageBodyVO messageBodyVO22 = new MessageBodyVO();
            messageRecordVO22.setIntentCode(intent.getAction());
            messageRecordVO22.setExtraBody(messageBodyVO22);
            if (MainApplication.getInstance() == null) {
            }
        } else {
            if (action.equals("android.intent.action.QUICKBOOT_POWEROFF")) {
                c = 1;
                if (c != 0) {
                }
                Log.d("ShutDownBroadcastReceiver", str);
                MessageRecordVO messageRecordVO222 = new MessageRecordVO();
                MessageBodyVO messageBodyVO222 = new MessageBodyVO();
                messageRecordVO222.setIntentCode(intent.getAction());
                messageRecordVO222.setExtraBody(messageBodyVO222);
                if (MainApplication.getInstance() == null) {
                }
            }
            c = 65535;
            if (c != 0) {
            }
            Log.d("ShutDownBroadcastReceiver", str);
            MessageRecordVO messageRecordVO2222 = new MessageRecordVO();
            MessageBodyVO messageBodyVO2222 = new MessageBodyVO();
            messageRecordVO2222.setIntentCode(intent.getAction());
            messageRecordVO2222.setExtraBody(messageBodyVO2222);
            if (MainApplication.getInstance() == null) {
            }
        }
        AbstractC0026q.m186s("ShutDownBroadcastReceiver", e2);
    }
}
