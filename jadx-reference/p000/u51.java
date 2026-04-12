package p000;

import android.content.Context;
import android.text.TextPaint;
import java.lang.ref.WeakReference;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class u51 {

    /* renamed from: a2 */
    public float f60330a2;

    /* renamed from: a4 */
    public final WeakReference f60332a4;

    /* renamed from: a5 */
    public r51 f60333a5;

    /* renamed from: a0 */
    public final TextPaint f60328a0 = new TextPaint(1);

    /* renamed from: a1 */
    public final C0586hm f60329a1 = new C0586hm(1, this);

    /* renamed from: a3 */
    public boolean f60331a3 = true;

    public u51(t51 t51Var) {
        this.f60332a4 = new WeakReference(null);
        this.f60332a4 = new WeakReference(t51Var);
    }

    /* renamed from: a0 */
    public final float m214816a0(String str) {
        if (!this.f60331a3) {
            return this.f60330a2;
        }
        float fMeasureText = str == null ? 0.0f : this.f60328a0.measureText((CharSequence) str, 0, str.length());
        this.f60330a2 = fMeasureText;
        this.f60331a3 = false;
        return fMeasureText;
    }

    /* renamed from: a1 */
    public final void m214817a1(r51 r51Var, Context context) {
        if (this.f60333a5 != r51Var) {
            this.f60333a5 = r51Var;
            if (r51Var != null) {
                TextPaint textPaint = this.f60328a0;
                C0586hm c0586hm = this.f60329a1;
                r51Var.m214489a5(context, textPaint, c0586hm);
                t51 t51Var = (t51) this.f60332a4.get();
                if (t51Var != null) {
                    textPaint.drawableState = t51Var.getState();
                }
                r51Var.m214488a4(context, textPaint, c0586hm);
                this.f60331a3 = true;
            }
            t51 t51Var2 = (t51) this.f60332a4.get();
            if (t51Var2 != null) {
                t51Var2.mo210828a0();
                t51Var2.onStateChange(t51Var2.getState());
            }
        }
    }
}
