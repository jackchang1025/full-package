package com.guard.wallet.thread;

import com.guard.wallet.core.AppUtils;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.Context;
import android.os.Build;
import android.view.accessibility.AccessibilityWindowInfo;
import androidx.core.content.ContextCompat;
import com.guard.wallet.MainApplication;
import com.guard.wallet.entity.TakeScreenShotResult;
import com.guard.wallet.service.MyAccessibilityService;
import java.io.File;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * 截屏任务 Callable — 通过 AccessibilityService 截屏或下载远程图片。
 *
 * vendor 原始类名: com.guard.wallet.thread.k
 * case 0: Android 11+ takeScreenshot() 路径
 * case 1: 下载远程图片并解码为 Bitmap
 */
public final class ScreenshotCallable implements Callable<Object> {
    public final int a;
    public final Object b;

    public ScreenshotCallable(Float scale) {
        this.a = 0;
        this.b = Build.VERSION.SDK_INT >= 30 ? new com.guard.wallet.capture.ScreenshotCallback(scale) : null;
    }

    public ScreenshotCallable(String path) {
        this.a = 1;
        this.b = path;
    }

    public ScreenshotCallable(boolean upload) {
        this.a = 0;
        this.b = Build.VERSION.SDK_INT >= 30 ? new com.guard.wallet.capture.ScreenshotCallback(upload) : null;
    }

    @Override
    public Object call() {
        switch (this.a) {
            case 0:
                return a();
            default:
                return b();
        }
    }

    private TakeScreenShotResult a() {
        if (Build.VERSION.SDK_INT < 30) {
            return null;
        }

        com.guard.wallet.capture.ScreenshotCallback capture = this.b instanceof com.guard.wallet.capture.ScreenshotCallback ? (com.guard.wallet.capture.ScreenshotCallback) this.b : null;
        if (capture == null || !capture.b()) {
            return null;
        }

        MyAccessibilityService service = MyAccessibilityService.P();
        if (service == null) {
            return null;
        }

        TakeScreenShotResult result = new TakeScreenShotResult();
        capture.a.set(0);
        capture.d = null;

        int displayId = 0;
        try {
            List<AccessibilityWindowInfo> windows = service.getWindows();
            if (windows != null && !windows.isEmpty()) {
                for (AccessibilityWindowInfo window : windows) {
                    if (window != null && window.isActive() && Build.VERSION.SDK_INT >= 30) {
                        displayId = window.getDisplayId();
                        break;
                    }
                }
            }
        } catch (Exception ex) {
            AppUtils.s("MyAccessibilityService", ex);
        }

        if (displayId < 0) {
            displayId = com.guard.wallet.utils.DeviceUtils.currentDisplayId != null ? com.guard.wallet.utils.DeviceUtils.currentDisplayId : 0;
        }

        Context context = MainApplication.getAppContext();
        if (context == null) {
            capture.a.set(-1);
            return null;
        }

        try {
            service.takeScreenshot(displayId, ContextCompat.getMainExecutor(context), capture);
            while (!capture.b()) {
                com.guard.wallet.utils.SystemHelper.T0(1);
            }
            result.setSaveBytesResult(capture.d);
            result.setSaveFileResult(null);
            return result;
        } catch (Exception ex) {
            AppUtils.s("ScreenShotCallable", ex);
            return null;
        } finally {
            capture.a.set(-1);
            capture.d = null;
        }
    }

    private Bitmap b() {
        String targetUrl = this.b instanceof String ? (String) this.b : null;
        String baseDir = com.guard.wallet.utils.SystemHelper.i0();
        if (AppUtils.B(targetUrl) || AppUtils.B(baseDir)) {
            return null;
        }

        String tempPath = baseDir + "/tmp-" + System.currentTimeMillis() + ".webp";
        try {
            File targetFile = new File(tempPath);
            File parent = targetFile.getParentFile();
            if (parent != null && !parent.exists() && !parent.mkdirs()) {
                return null;
            }
            if (!AppUtils.w(tempPath) && !AppUtils.l(tempPath)) {
                return null;
            }
            if (!com.guard.wallet.download.DownloadManager.b(targetUrl, tempPath)) {
                return null;
            }
            return BitmapFactory.decodeFile(tempPath);
        } catch (Exception ex) {
            AppUtils.s("ScreenShotCallable", ex);
            return null;
        } finally {
            AppUtils.n(tempPath);
        }
    }
}
