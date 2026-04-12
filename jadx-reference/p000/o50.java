package p000;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class o50 extends AnimatorListenerAdapter {

    /* renamed from: a0 */
    public final /* synthetic */ int f58738a0;

    /* renamed from: a1 */
    public final /* synthetic */ TextView f58739a1;

    /* renamed from: a2 */
    public final /* synthetic */ int f58740a2;

    /* renamed from: a3 */
    public final /* synthetic */ TextView f58741a3;

    /* renamed from: a4 */
    public final /* synthetic */ q50 f58742a4;

    public o50(q50 q50Var, int i, TextView textView, int i2, TextView textView2) {
        this.f58742a4 = q50Var;
        this.f58738a0 = i;
        this.f58739a1 = textView;
        this.f58740a2 = i2;
        this.f58741a3 = textView2;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationEnd(Animator animator) {
        AppCompatTextView appCompatTextView;
        int i = this.f58738a0;
        q50 q50Var = this.f58742a4;
        q50Var.f59400b3 = i;
        q50Var.f59398b1 = null;
        TextView textView = this.f58739a1;
        if (textView != null) {
            textView.setVisibility(4);
            if (this.f58740a2 == 1 && (appCompatTextView = q50Var.f59404b7) != null) {
                appCompatTextView.setText((CharSequence) null);
            }
        }
        TextView textView2 = this.f58741a3;
        if (textView2 != null) {
            textView2.setTranslationY(0.0f);
            textView2.setAlpha(1.0f);
        }
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationStart(Animator animator) {
        TextView textView = this.f58741a3;
        if (textView != null) {
            textView.setVisibility(0);
            textView.setAlpha(0.0f);
        }
    }
}
