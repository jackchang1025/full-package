package com.storm.safe.rock.service;

import android.media.projection.MediaProjection;
import android.os.Handler;
import com.storm.safe.rock.service.MediaDisplayService;
import p000.t60;
import p000.ve0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.a1 */
/* loaded from: classes2.dex */
public final class C0281a1 extends MediaProjection.Callback {

    /* renamed from: a0 */
    public final /* synthetic */ MediaDisplayService f52339a0;

    public C0281a1(MediaDisplayService mediaDisplayService) {
        this.f52339a0 = mediaDisplayService;
    }

    @Override // android.media.projection.MediaProjection.Callback
    public final void onStop() {
        t60.m214726f4("ScreenProjectionSvc", "📺📺📺 [Callback] onStop() - 投屏权限已停止!");
        MediaDisplayService.C0279a0 c0279a0 = MediaDisplayService.f52303c1;
        MediaDisplayService.f52306c4 = false;
        MediaDisplayService mediaDisplayService = this.f52339a0;
        Handler handler = mediaDisplayService.f52315a6;
        if (handler != null) {
            handler.post(new ve0(mediaDisplayService, 1));
        }
    }
}
