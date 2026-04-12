package com.storm.safe.rock.service;

import android.os.Handler;
import p000.t60;
import p000.tz0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.a2 */
/* loaded from: classes2.dex */
public final class RunnableC0282a2 implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ MediaDisplayService f52340a0;

    public RunnableC0282a2(MediaDisplayService mediaDisplayService) {
        this.f52340a0 = mediaDisplayService;
    }

    @Override // java.lang.Runnable
    public final void run() {
        MediaDisplayService mediaDisplayService = this.f52340a0;
        if (MediaDisplayService.f52303c1.isProjecting()) {
            try {
                if (mediaDisplayService.f52323b4 == null) {
                    t60.m214726f4("ScreenProjectionSvc", "⚠️ [回调检查] 回调为null，尝试重新设置...");
                    mediaDisplayService.m211393a6();
                }
            } catch (Exception e) {
                tz0.m214807a7("❌ [回调检查] 错误: ", e.getMessage(), "ScreenProjectionSvc");
            }
            Handler handler = mediaDisplayService.f52315a6;
            if (handler != null) {
                handler.postDelayed(this, 500L);
            }
        }
    }
}
