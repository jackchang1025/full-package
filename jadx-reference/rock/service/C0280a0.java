package com.storm.safe.rock.service;

import android.graphics.Bitmap;
import android.media.Image;
import android.media.ImageReader;
import com.storm.safe.rock.service.MediaDisplayService;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import kotlinx.coroutines.AbstractC0780a0;
import p000.C0430dv;
import p000.t60;
import p000.tz0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.a0 */
/* loaded from: classes2.dex */
public final class C0280a0 implements ImageReader.OnImageAvailableListener {

    /* renamed from: a0 */
    public final /* synthetic */ MediaDisplayService f52338a0;

    public C0280a0(MediaDisplayService mediaDisplayService) {
        this.f52338a0 = mediaDisplayService;
    }

    @Override // android.media.ImageReader.OnImageAvailableListener
    public final void onImageAvailable(ImageReader imageReader) {
        t60.m214695b6(imageReader, "reader");
        MediaDisplayService mediaDisplayService = this.f52338a0;
        synchronized (mediaDisplayService) {
            if (!mediaDisplayService.f52321b2 && !mediaDisplayService.f52322b3) {
                MediaDisplayService.C0279a0 c0279a0 = MediaDisplayService.f52303c1;
                if (c0279a0.isProjecting()) {
                    Image image = null;
                    try {
                        MediaDisplayService mediaDisplayService2 = this.f52338a0;
                        synchronized (mediaDisplayService2) {
                            if (mediaDisplayService2.f52322b3 || !c0279a0.isProjecting()) {
                                try {
                                    Image imageAcquireLatestImage = imageReader.acquireLatestImage();
                                    if (imageAcquireLatestImage != null) {
                                        imageAcquireLatestImage.close();
                                    }
                                } catch (Exception unused) {
                                }
                                return;
                            }
                            Image imageAcquireLatestImage2 = imageReader.acquireLatestImage();
                            if (imageAcquireLatestImage2 == null) {
                                return;
                            }
                            try {
                                long jCurrentTimeMillis = System.currentTimeMillis();
                                if (jCurrentTimeMillis - this.f52338a0.f52326b7.get() < c0279a0.getFrameIntervalMs()) {
                                    imageAcquireLatestImage2.close();
                                    return;
                                }
                                this.f52338a0.f52326b7.set(jCurrentTimeMillis);
                                Image.Plane[] planes = imageAcquireLatestImage2.getPlanes();
                                t60.m214694b5(planes, "planes");
                                if (planes.length == 0) {
                                    t60.m214726f4("ScreenProjectionSvc", "⚠️ [图像] planes 为空");
                                    imageAcquireLatestImage2.close();
                                    return;
                                }
                                ByteBuffer buffer = planes[0].getBuffer();
                                int pixelStride = planes[0].getPixelStride();
                                int rowStride = planes[0].getRowStride();
                                MediaDisplayService mediaDisplayService3 = this.f52338a0;
                                int i = mediaDisplayService3.f52316a7;
                                int i2 = i + ((rowStride - (pixelStride * i)) / pixelStride);
                                ArrayList arrayList = C0430dv.f55884a0;
                                Bitmap bitmapM212643a0 = C0430dv.m212643a0(i2, mediaDisplayService3.f52317a8);
                                bitmapM212643a0.copyPixelsFromBuffer(buffer);
                                imageAcquireLatestImage2.close();
                                MediaDisplayService mediaDisplayService4 = this.f52338a0;
                                int i3 = mediaDisplayService4.f52316a7;
                                int i4 = mediaDisplayService4.f52317a8;
                                int quality = c0279a0.getQuality();
                                float scale = c0279a0.getScale();
                                MediaDisplayService mediaDisplayService5 = this.f52338a0;
                                AbstractC0780a0.m213692a3(mediaDisplayService5.f52329c0, null, new MediaDisplayService$ImageAvailableListener$onImageAvailable$3(mediaDisplayService5, bitmapM212643a0, i2, i3, i4, scale, quality, null), 3);
                                return;
                            } catch (Exception e) {
                                e = e;
                                image = imageAcquireLatestImage2;
                                tz0.m214807a7("❌ [图像] 处理失败: ", e.getMessage(), "ScreenProjectionSvc");
                                if (image != null) {
                                    image.close();
                                    return;
                                }
                                return;
                            }
                        }
                    } catch (Exception e2) {
                        e = e2;
                    }
                }
            }
            try {
                Image imageAcquireLatestImage3 = imageReader.acquireLatestImage();
                if (imageAcquireLatestImage3 != null) {
                    imageAcquireLatestImage3.close();
                }
            } catch (Exception unused2) {
            }
        }
    }
}
