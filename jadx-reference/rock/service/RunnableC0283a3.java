package com.storm.safe.rock.service;

import android.os.Handler;
import java.util.concurrent.atomic.AtomicLong;
import p000.tz0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.a3 */
/* loaded from: classes2.dex */
public final class RunnableC0283a3 implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ MediaDisplayService f52341a0;

    public RunnableC0283a3(MediaDisplayService mediaDisplayService) {
        this.f52341a0 = mediaDisplayService;
    }

    @Override // java.lang.Runnable
    public final void run() {
        MediaDisplayService mediaDisplayService = this.f52341a0;
        AtomicLong atomicLong = mediaDisplayService.f52327b8;
        if (MediaDisplayService.f52303c1.isProjecting()) {
            try {
                long jCurrentTimeMillis = System.currentTimeMillis();
                if (jCurrentTimeMillis - atomicLong.get() >= 5000) {
                    mediaDisplayService.f52328b9.set(mediaDisplayService.f52325b6.get());
                    atomicLong.set(jCurrentTimeMillis);
                }
            } catch (Exception e) {
                tz0.m214807a7("❌ [统计] 错误: ", e.getMessage(), "ScreenProjectionSvc");
            }
            Handler handler = mediaDisplayService.f52315a6;
            if (handler != null) {
                handler.postDelayed(this, 5000L);
            }
        }
    }
}
