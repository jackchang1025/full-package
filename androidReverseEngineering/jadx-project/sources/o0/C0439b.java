package o0;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

/* renamed from: o0.b */
/* loaded from: classes.dex */
public final class C0439b extends AnimatorListenerAdapter {

    /* renamed from: a */
    public final /* synthetic */ int f984a;

    /* renamed from: b */
    public final /* synthetic */ C0445h f985b;

    /* renamed from: c */
    public final /* synthetic */ Object f986c;

    public /* synthetic */ C0439b(C0445h c0445h, Object obj, int i2) {
        this.f984a = i2;
        this.f985b = c0445h;
        this.f986c = obj;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationEnd(Animator animator) {
        int i2 = this.f984a;
        Object obj = this.f986c;
        switch (i2) {
            case 0:
                C0443f c0443f = (C0443f) obj;
                c0443f.f1007e = null;
                c0443f.f1004b = true;
                c0443f.f1003a = this.f985b.f1036m;
                break;
            default:
                Runnable runnable = (Runnable) obj;
                if (runnable != null) {
                    runnable.run();
                    break;
                }
                break;
        }
    }
}
