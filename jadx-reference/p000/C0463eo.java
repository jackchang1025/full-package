package p000;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import androidx.appcompat.widget.ActionMenuView;
import com.google.android.material.bottomappbar.BottomAppBar;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: eo */
/* loaded from: classes2.dex */
public final class C0463eo extends AnimatorListenerAdapter {

    /* renamed from: a0 */
    public boolean f56080a0;

    /* renamed from: a1 */
    public final /* synthetic */ ActionMenuView f56081a1;

    /* renamed from: a2 */
    public final /* synthetic */ int f56082a2;

    /* renamed from: a3 */
    public final /* synthetic */ boolean f56083a3;

    /* renamed from: a4 */
    public final /* synthetic */ BottomAppBar f56084a4;

    public C0463eo(BottomAppBar bottomAppBar, ActionMenuView actionMenuView, int i, boolean z) {
        this.f56084a4 = bottomAppBar;
        this.f56081a1 = actionMenuView;
        this.f56082a2 = i;
        this.f56083a3 = z;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationCancel(Animator animator) {
        this.f56080a0 = true;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationEnd(Animator animator) {
        if (this.f56080a0) {
            return;
        }
        this.f56084a4.m210931d4(this.f56081a1, this.f56082a2, this.f56083a3, false);
    }
}
