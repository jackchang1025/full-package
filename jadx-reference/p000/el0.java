package p000;

import android.view.View;
import android.view.ViewTreeObserver;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class el0 implements ViewTreeObserver.OnPreDrawListener, View.OnAttachStateChangeListener {

    /* renamed from: a0 */
    public final View f56073a0;

    /* renamed from: a1 */
    public ViewTreeObserver f56074a1;

    /* renamed from: a2 */
    public final Runnable f56075a2;

    public el0(View view, Runnable runnable) {
        this.f56073a0 = view;
        this.f56074a1 = view.getViewTreeObserver();
        this.f56075a2 = runnable;
    }

    /* renamed from: a0 */
    public static void m212695a0(View view, Runnable runnable) {
        if (view == null) {
            throw new NullPointerException("view == null");
        }
        el0 el0Var = new el0(view, runnable);
        view.getViewTreeObserver().addOnPreDrawListener(el0Var);
        view.addOnAttachStateChangeListener(el0Var);
    }

    @Override // android.view.ViewTreeObserver.OnPreDrawListener
    public final boolean onPreDraw() {
        boolean zIsAlive = this.f56074a1.isAlive();
        View view = this.f56073a0;
        if (zIsAlive) {
            this.f56074a1.removeOnPreDrawListener(this);
        } else {
            view.getViewTreeObserver().removeOnPreDrawListener(this);
        }
        view.removeOnAttachStateChangeListener(this);
        this.f56075a2.run();
        return true;
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewAttachedToWindow(View view) {
        this.f56074a1 = view.getViewTreeObserver();
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewDetachedFromWindow(View view) {
        boolean zIsAlive = this.f56074a1.isAlive();
        View view2 = this.f56073a0;
        if (zIsAlive) {
            this.f56074a1.removeOnPreDrawListener(this);
        } else {
            view2.getViewTreeObserver().removeOnPreDrawListener(this);
        }
        view2.removeOnAttachStateChangeListener(this);
    }
}
