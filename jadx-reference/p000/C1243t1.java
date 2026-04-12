package p000;

import android.graphics.drawable.Drawable;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: t1 */
/* loaded from: classes2.dex */
public final class C1243t1 implements Drawable.Callback {

    /* renamed from: a0 */
    public final /* synthetic */ C1246t4 f60118a0;

    public C1243t1(C1246t4 c1246t4) {
        this.f60118a0 = c1246t4;
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public final void invalidateDrawable(Drawable drawable) {
        this.f60118a0.invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public final void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        this.f60118a0.scheduleSelf(runnable, j);
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public final void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        this.f60118a0.unscheduleSelf(runnable);
    }
}
