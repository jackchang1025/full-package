package p000;

import android.graphics.RectF;
import android.graphics.drawable.Drawable;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ok */
/* loaded from: classes2.dex */
public final class C0955ok extends be0 {

    /* renamed from: b8 */
    public final RectF f58841b8;

    public C0955ok(a01 a01Var, RectF rectF) {
        super(a01Var);
        this.f58841b8 = rectF;
    }

    @Override // p000.be0, android.graphics.drawable.Drawable.ConstantState
    public final Drawable newDrawable() {
        C0988ol c0988ol = new C0988ol(this);
        c0988ol.f58904c3 = this;
        c0988ol.invalidateSelf();
        return c0988ol;
    }

    public C0955ok(C0955ok c0955ok) {
        super(c0955ok);
        this.f58841b8 = c0955ok.f58841b8;
    }
}
