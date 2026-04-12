package p000;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.os.Build;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ol */
/* loaded from: classes2.dex */
public final class C0988ol extends ce0 {

    /* renamed from: c4 */
    public static final /* synthetic */ int f58903c4 = 0;

    /* renamed from: c3 */
    public C0955ok f58904c3;

    @Override // p000.ce0
    /* renamed from: a5 */
    public final void mo210833a5(Canvas canvas) {
        if (this.f58904c3.f58841b8.isEmpty()) {
            super.mo210833a5(canvas);
            return;
        }
        canvas.save();
        if (Build.VERSION.SDK_INT >= 26) {
            canvas.clipOutRect(this.f58904c3.f58841b8);
        } else {
            canvas.clipRect(this.f58904c3.f58841b8, Region.Op.DIFFERENCE);
        }
        super.mo210833a5(canvas);
        canvas.restore();
    }

    /* renamed from: c2 */
    public final void m214230c2(float f, float f2, float f3, float f4) {
        RectF rectF = this.f58904c3.f58841b8;
        if (f == rectF.left && f2 == rectF.top && f3 == rectF.right && f4 == rectF.bottom) {
            return;
        }
        rectF.set(f, f2, f3, f4);
        invalidateSelf();
    }

    @Override // p000.ce0, android.graphics.drawable.Drawable
    public final Drawable mutate() {
        this.f58904c3 = new C0955ok(this.f58904c3);
        return this;
    }
}
