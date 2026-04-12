package p000;

import android.view.View;
import java.lang.ref.WeakReference;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class mc1 {

    /* renamed from: a0 */
    public final WeakReference f58331a0;

    public mc1(View view) {
        this.f58331a0 = new WeakReference(view);
    }

    /* renamed from: a0 */
    public final void m213967a0(float f) {
        View view = (View) this.f58331a0.get();
        if (view != null) {
            view.animate().alpha(f);
        }
    }

    /* renamed from: a1 */
    public final void m213968a1() {
        View view = (View) this.f58331a0.get();
        if (view != null) {
            view.animate().cancel();
        }
    }

    /* renamed from: a2 */
    public final void m213969a2(long j) {
        View view = (View) this.f58331a0.get();
        if (view != null) {
            view.animate().setDuration(j);
        }
    }

    /* renamed from: a3 */
    public final void m213970a3(oc1 oc1Var) {
        View view = (View) this.f58331a0.get();
        if (view != null) {
            if (oc1Var != null) {
                view.animate().setListener(new vm0(oc1Var, view, 2));
            } else {
                view.animate().setListener(null);
            }
        }
    }

    /* renamed from: a4 */
    public final void m213971a4(float f) {
        View view = (View) this.f58331a0.get();
        if (view != null) {
            view.animate().translationY(f);
        }
    }
}
