package com.vendor.rat.control.handler;

import android.util.Log;

import com.google.gson.JsonObject;

/**
 * 截图处理器 (模块 06)
 *
 * 基于 MediaProjection API 实现屏幕截图
 */
public class ScreenshotHandler {

    private static final String TAG = "ScreenshotHandler";

    public void handle(JsonObject command) {
        Log.d(TAG, "Screenshot command received");
        // TODO: 通过 MediaLiveService 截取屏幕并上传
    }
}
