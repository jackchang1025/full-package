package com.guard.wallet.receiver;

import a1.AbstractC0026q;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;
import com.guard.wallet.MainApplication;
import com.guard.wallet.req.MessageBodyVO;
import com.guard.wallet.req.MessageRecordVO;
import com.guard.wallet.utils.AbstractC0251g;
import com.guard.wallet.utils.AbstractC0252h;
import java.util.concurrent.atomic.AtomicBoolean;
import p019w.AbstractC0956a;

/* loaded from: classes.dex */
public class PowerBroadcastReceiver extends BroadcastReceiver {

    /* renamed from: a */
    public Integer f282a = 0;

    /* JADX WARN: Removed duplicated region for block: B:18:0x00b5 A[Catch: Exception -> 0x00cb, TryCatch #0 {Exception -> 0x00cb, blocks: (B:3:0x0003, B:5:0x000b, B:7:0x0015, B:8:0x0020, B:16:0x009b, B:18:0x00b5, B:20:0x00bf, B:26:0x005b, B:28:0x0066, B:30:0x0074, B:31:0x007a, B:33:0x007e, B:38:0x008f, B:41:0x0056, B:43:0x0098, B:45:0x0024, B:48:0x002e, B:51:0x0038, B:54:0x0042), top: B:2:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:25:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    @Override // android.content.BroadcastReceiver
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onReceive(Context context, Intent intent) {
        char c;
        String str;
        PowerManager powerManager;
        boolean z2 = true;
        try {
            this.f282a = 1;
            if (intent == null || AbstractC0026q.m151B(intent.getAction())) {
                return;
            }
            String action = intent.getAction();
            switch (action.hashCode()) {
                case -1886648615:
                    if (action.equals("android.intent.action.ACTION_POWER_DISCONNECTED")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 60304393:
                    if (action.equals("android.intent.action.POWER_USAGE_SUMMARY")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 1019184907:
                    if (action.equals("android.intent.action.ACTION_POWER_CONNECTED")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 1779291251:
                    if (action.equals("android.os.action.POWER_SAVE_MODE_CHANGED")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            if (c == 0) {
                str = "充电线已连接 ACTION_POWER_CONNECTED";
            } else if (c != 1) {
                if (c == 2) {
                    Log.d("PowerBroadcastReceiver", "电力使用情况总结 ACTION_POWER_USAGE_SUMMARY");
                } else if (c != 3) {
                    MessageRecordVO messageRecordVO = new MessageRecordVO();
                    MessageBodyVO messageBodyVO = new MessageBodyVO();
                    messageRecordVO.setIntentCode(intent.getAction());
                    messageRecordVO.setExtraBody(messageBodyVO);
                    if (MainApplication.getInstance() != null || MainApplication.getInstance().getHandlerMsgAndTimer() == null) {
                        return;
                    }
                    MainApplication.getInstance().getHandlerMsgAndTimer().m579b(messageRecordVO);
                    return;
                }
                Log.d("PowerBroadcastReceiver", "省点模式 ACTION_POWER_SAVE_MODE_CHANGED");
                boolean isPowerSaveMode = (AbstractC0251g.m653Z() == null || (powerManager = (PowerManager) AbstractC0251g.m653Z().getSystemService("power")) == null) ? false : powerManager.isPowerSaveMode();
                AtomicBoolean atomicBoolean = AbstractC0956a.f2271a;
                if (isPowerSaveMode) {
                    float m704h = AbstractC0252h.m704h();
                    if (m704h > 0.0f) {
                        if (m704h < 5.0f) {
                            atomicBoolean.set(z2);
                        }
                    }
                    MessageRecordVO messageRecordVO2 = new MessageRecordVO();
                    MessageBodyVO messageBodyVO2 = new MessageBodyVO();
                    messageRecordVO2.setIntentCode(intent.getAction());
                    messageRecordVO2.setExtraBody(messageBodyVO2);
                    if (MainApplication.getInstance() != null) {
                        return;
                    } else {
                        return;
                    }
                }
                z2 = false;
                atomicBoolean.set(z2);
                MessageRecordVO messageRecordVO22 = new MessageRecordVO();
                MessageBodyVO messageBodyVO22 = new MessageBodyVO();
                messageRecordVO22.setIntentCode(intent.getAction());
                messageRecordVO22.setExtraBody(messageBodyVO22);
                if (MainApplication.getInstance() != null) {
                }
            } else {
                str = "充电线已断开 ACTION_POWER_DISCONNECTED";
            }
            Log.d("PowerBroadcastReceiver", str);
            MessageRecordVO messageRecordVO222 = new MessageRecordVO();
            MessageBodyVO messageBodyVO222 = new MessageBodyVO();
            messageRecordVO222.setIntentCode(intent.getAction());
            messageRecordVO222.setExtraBody(messageBodyVO222);
            if (MainApplication.getInstance() != null) {
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("PowerBroadcastReceiver", e2);
        }
    }
}
