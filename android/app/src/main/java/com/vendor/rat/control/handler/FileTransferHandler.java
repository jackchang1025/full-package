package com.vendor.rat.control.handler;

import android.util.Log;

import com.google.gson.JsonObject;

/**
 * 文件传输处理器 (模块 06)
 *
 * 处理文件上传和下载指令
 */
public class FileTransferHandler {

    private static final String TAG = "FileTransferHandler";

    public void handle(JsonObject command) {
        int type = command.get("type").getAsInt();
        if (type == 12) {
            handleDownload(command);
        } else {
            handleUpload(command);
        }
    }

    private void handleDownload(JsonObject command) {
        Log.d(TAG, "File download command received");
        // TODO: 从服务器下载文件到设备
    }

    private void handleUpload(JsonObject command) {
        Log.d(TAG, "File upload command received");
        // TODO: 上传指定文件到服务器
    }
}
