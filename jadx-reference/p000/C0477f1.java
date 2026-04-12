package p000;

import android.view.View;
import androidx.appcompat.widget.ActionBarContextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: f1 */
/* loaded from: classes.dex */
public final class C0477f1 implements oc1 {

    /* renamed from: a0 */
    public boolean f56131a0;

    /* renamed from: a1 */
    public int f56132a1;

    /* renamed from: a2 */
    public final View f56133a2;

    public C0477f1(FloatingActionButton floatingActionButton) {
        this.f56131a0 = false;
        this.f56132a1 = 0;
        this.f56133a2 = floatingActionButton;
    }

    @Override // p000.oc1
    /* renamed from: a0 */
    public void mo212658a0() {
        if (this.f56131a0) {
            return;
        }
        ActionBarContextView actionBarContextView = (ActionBarContextView) this.f56133a2;
        actionBarContextView.f43820a5 = null;
        super/*android.view.View*/.setVisibility(this.f56132a1);
    }

    @Override // p000.oc1
    /* renamed from: a1 */
    public void mo212659a1(View view) {
        this.f56131a0 = true;
    }

    @Override // p000.oc1
    /* renamed from: a2 */
    public void mo212660a2() {
        super/*android.view.View*/.setVisibility(0);
        this.f56131a0 = false;
    }

    public C0477f1(ActionBarContextView actionBarContextView) {
        this.f56133a2 = actionBarContextView;
        this.f56131a0 = false;
    }
}
