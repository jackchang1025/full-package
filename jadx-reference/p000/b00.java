package p000;

import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.AbstractComponentCallbacksC0069a5;
import androidx.fragment.app.C0068a4;
import androidx.fragment.app.C0072a8;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class b00 implements View.OnAttachStateChangeListener {

    /* renamed from: a0 */
    public final /* synthetic */ C0072a8 f45674a0;

    /* renamed from: a1 */
    public final /* synthetic */ c00 f45675a1;

    public b00(c00 c00Var, C0072a8 c0072a8) {
        this.f45675a1 = c00Var;
        this.f45674a0 = c0072a8;
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewAttachedToWindow(View view) {
        C0072a8 c0072a8 = this.f45674a0;
        AbstractComponentCallbacksC0069a5 abstractComponentCallbacksC0069a5 = c0072a8.f45157a2;
        c0072a8.m210214b0();
        C0068a4.m210125a5((ViewGroup) abstractComponentCallbacksC0069a5.f45107d0.getParent(), this.f45675a1.f46046a0.m210188c6()).m210130a4();
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewDetachedFromWindow(View view) {
    }
}
