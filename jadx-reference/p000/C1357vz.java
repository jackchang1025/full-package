package p000;

import android.content.Context;
import android.graphics.Color;
import com.google.android.material.R$attr;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: vz */
/* loaded from: classes2.dex */
public final class C1357vz {

    /* renamed from: a5 */
    public static final int f60718a5 = (int) Math.round(5.1000000000000005d);

    /* renamed from: a0 */
    public final boolean f60719a0;

    /* renamed from: a1 */
    public final int f60720a1;

    /* renamed from: a2 */
    public final int f60721a2;

    /* renamed from: a3 */
    public final int f60722a3;

    /* renamed from: a4 */
    public final float f60723a4;

    public C1357vz(Context context) {
        boolean zM213535e2 = kg1.m213535e2(context, R$attr.elevationOverlayEnabled, false);
        int iM213567b4 = kj1.m213567b4(context, R$attr.elevationOverlayColor, 0);
        int iM213567b42 = kj1.m213567b4(context, R$attr.elevationOverlayAccentColor, 0);
        int iM213567b43 = kj1.m213567b4(context, R$attr.colorSurface, 0);
        float f = context.getResources().getDisplayMetrics().density;
        this.f60719a0 = zM213535e2;
        this.f60720a1 = iM213567b4;
        this.f60721a2 = iM213567b42;
        this.f60722a3 = iM213567b43;
        this.f60723a4 = f;
    }

    /* renamed from: a0 */
    public final int m214972a0(float f, int i) {
        int i2;
        if (!this.f60719a0 || AbstractC0724jn.m213334a4(i, v10.MASK) != this.f60722a3) {
            return i;
        }
        float fMin = (this.f60723a4 <= 0.0f || f <= 0.0f) ? 0.0f : Math.min(((((float) Math.log1p(f / r1)) * 4.5f) + 2.0f) / 100.0f, 1.0f);
        int iAlpha = Color.alpha(i);
        int iM213577c4 = kj1.m213577c4(AbstractC0724jn.m213334a4(i, v10.MASK), fMin, this.f60720a1);
        if (fMin > 0.0f && (i2 = this.f60721a2) != 0) {
            iM213577c4 = AbstractC0724jn.m213332a2(AbstractC0724jn.m213334a4(i2, f60718a5), iM213577c4);
        }
        return AbstractC0724jn.m213334a4(iM213577c4, iAlpha);
    }
}
