package p000;

import android.graphics.Typeface;
import com.google.android.material.internal.C0211a1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ja */
/* loaded from: classes2.dex */
public final class C0711ja {

    /* renamed from: a0 */
    public final /* synthetic */ int f57310a0;

    /* renamed from: a1 */
    public final /* synthetic */ C0211a1 f57311a1;

    public /* synthetic */ C0711ja(C0211a1 c0211a1, int i) {
        this.f57310a0 = i;
        this.f57311a1 = c0211a1;
    }

    /* renamed from: a0 */
    public final void m213279a0(Typeface typeface) {
        switch (this.f57310a0) {
            case 0:
                C0211a1 c0211a1 = this.f57311a1;
                if (c0211a1.m211070b2(typeface)) {
                    c0211a1.m211066a8(false);
                    break;
                }
                break;
            default:
                C0211a1 c0211a12 = this.f57311a1;
                if (c0211a12.m211072b4(typeface)) {
                    c0211a12.m211066a8(false);
                    break;
                }
                break;
        }
    }
}
