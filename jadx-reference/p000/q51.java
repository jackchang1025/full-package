package p000;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextPaint;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class q51 extends cq0 {

    /* renamed from: b0 */
    public final /* synthetic */ Context f59415b0;

    /* renamed from: b1 */
    public final /* synthetic */ TextPaint f59416b1;

    /* renamed from: b2 */
    public final /* synthetic */ cq0 f59417b2;

    /* renamed from: b3 */
    public final /* synthetic */ r51 f59418b3;

    public q51(r51 r51Var, Context context, TextPaint textPaint, cq0 cq0Var) {
        this.f59418b3 = r51Var;
        this.f59415b0 = context;
        this.f59416b1 = textPaint;
        this.f59417b2 = cq0Var;
    }

    @Override // p000.cq0
    /* renamed from: c6 */
    public final void mo212508c6(int i) {
        this.f59417b2.mo212508c6(i);
    }

    @Override // p000.cq0
    /* renamed from: c8 */
    public final void mo212510c8(Typeface typeface, boolean z) {
        this.f59418b3.m214490a6(this.f59415b0, this.f59416b1, typeface);
        this.f59417b2.mo212510c8(typeface, z);
    }
}
