package com.vendor.rat.control.server;

import android.util.Log;

/**
 * 媒体捕获处理器 — vendor b.java 截图/录屏/录音/摄像头路由
 *
 * 覆盖路由: /screenshot/0, /screenrecord/start,
 *   /screenrecord/state, /screenrecord/stop,
 *   /startRecord, /stopRecord, /frontCameraLive,
 *   /backCameraLive, /stopCameraLive, /miniCap/scale
 */
public class MediaCaptureHandler {

    private static final String TAG = "MediaCaptureHandler";

    /** /screenshot/0 — vendor k(k) */
    public void screenshot() {
        Log.d(TAG, "screenshot");
    }

    /** /screenrecord/start */
    public void startScreenRecord() {
        Log.d(TAG, "startScreenRecord");
    }

    /** /screenrecord/state — vendor V2(k) */
    public void getScreenRecordState() {
        Log.d(TAG, "getScreenRecordState");
    }

    /** /screenrecord/stop — vendor e3(k) */
    public void stopScreenRecord() {
        Log.d(TAG, "stopScreenRecord");
    }

    /** /startRecord — vendor U2(audioSource, k) */
    public void startRecord(int audioSource) {
        Log.d(TAG, "startRecord: " + audioSource);
    }

    /** /stopRecord — vendor d3(k) */
    public void stopRecord() {
        Log.d(TAG, "stopRecord");
    }

    /** /frontCameraLive */
    public void frontCameraLive() {
        Log.d(TAG, "frontCameraLive");
    }

    /** /backCameraLive */
    public void backCameraLive() {
        Log.d(TAG, "backCameraLive");
    }

    /** /stopCameraLive */
    public void stopCameraLive() {
        Log.d(TAG, "stopCameraLive");
    }

    /** /miniCap/scale — vendor I1(scale, k) */
    public void setMiniCapScale(float scale) {
        Log.d(TAG, "setMiniCapScale: " + scale);
    }
}
