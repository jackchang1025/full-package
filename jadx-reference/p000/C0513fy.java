package p000;

import android.graphics.Typeface;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: fy */
/* loaded from: classes2.dex */
public final class C0513fy extends cq0 {

    /* renamed from: b0 */
    public final Typeface f56341b0;

    /* renamed from: b1 */
    public final C0711ja f56342b1;

    /* renamed from: b2 */
    public boolean f56343b2;

    public C0513fy(C0711ja c0711ja, Typeface typeface) {
        this.f56341b0 = typeface;
        this.f56342b1 = c0711ja;
    }

    @Override // p000.cq0
    /* renamed from: c6 */
    public final void mo212508c6(int i) {
        if (this.f56343b2) {
            return;
        }
        this.f56342b1.m213279a0(this.f56341b0);
    }

    @Override // p000.cq0
    /* renamed from: c8 */
    public final void mo212510c8(Typeface typeface, boolean z) {
        if (this.f56343b2) {
            return;
        }
        this.f56342b1.m213279a0(typeface);
    }
}
