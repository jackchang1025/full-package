package p000;

import android.content.Context;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.ActionBarContextView;
import androidx.appcompat.widget.C0041a1;
import java.lang.ref.WeakReference;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class ye1 extends AbstractC0903n7 implements ze0 {

    /* renamed from: a2 */
    public final Context f61302a2;

    /* renamed from: a3 */
    public final bf0 f61303a3;

    /* renamed from: a4 */
    public eo0 f61304a4;

    /* renamed from: a5 */
    public WeakReference f61305a5;

    /* renamed from: a6 */
    public final /* synthetic */ ze1 f61306a6;

    public ye1(ze1 ze1Var, Context context, eo0 eo0Var) {
        this.f61306a6 = ze1Var;
        this.f61302a2 = context;
        this.f61304a4 = eo0Var;
        bf0 bf0Var = new bf0(context);
        bf0Var.f45877b1 = 1;
        this.f61303a3 = bf0Var;
        bf0Var.f45870a4 = this;
    }

    @Override // p000.AbstractC0903n7
    /* renamed from: a0 */
    public final void mo214038a0() {
        ze1 ze1Var = this.f61306a6;
        if (ze1Var.f61524b4 != this) {
            return;
        }
        boolean z = ze1Var.f61531c1;
        boolean z2 = ze1Var.f61532c2;
        if (z || z2) {
            ze1Var.f61525b5 = this;
            ze1Var.f61526b6 = this.f61304a4;
        } else {
            this.f61304a4.m212714b8(this);
        }
        this.f61304a4 = null;
        ze1Var.m215396e2(false);
        ActionBarContextView actionBarContextView = ze1Var.f61521b1;
        if (actionBarContextView.f43825b0 == null) {
            actionBarContextView.m209854a4();
        }
        ze1Var.f61518a8.setHideOnContentScrollEnabled(ze1Var.f61537c7);
        ze1Var.f61524b4 = null;
    }

    @Override // p000.AbstractC0903n7
    /* renamed from: a1 */
    public final View mo214039a1() {
        WeakReference weakReference = this.f61305a5;
        if (weakReference != null) {
            return (View) weakReference.get();
        }
        return null;
    }

    @Override // p000.AbstractC0903n7
    /* renamed from: a2 */
    public final bf0 mo214040a2() {
        return this.f61303a3;
    }

    @Override // p000.AbstractC0903n7
    /* renamed from: a3 */
    public final MenuInflater mo214041a3() {
        return new c31(this.f61302a2);
    }

    @Override // p000.ze0
    /* renamed from: a4 */
    public final boolean mo214682a4(bf0 bf0Var, MenuItem menuItem) {
        eo0 eo0Var = this.f61304a4;
        if (eo0Var != null) {
            return ((x31) eo0Var.f56088a1).m215112a4(this, menuItem);
        }
        return false;
    }

    @Override // p000.AbstractC0903n7
    /* renamed from: a5 */
    public final CharSequence mo214042a5() {
        return this.f61306a6.f61521b1.getSubtitle();
    }

    @Override // p000.AbstractC0903n7
    /* renamed from: a6 */
    public final CharSequence mo214043a6() {
        return this.f61306a6.f61521b1.getTitle();
    }

    @Override // p000.AbstractC0903n7
    /* renamed from: a7 */
    public final void mo214044a7() {
        if (this.f61306a6.f61524b4 != this) {
            return;
        }
        bf0 bf0Var = this.f61303a3;
        bf0Var.m210712c4();
        try {
            this.f61304a4.m212715b9(this, bf0Var);
        } finally {
            bf0Var.m210711c3();
        }
    }

    @Override // p000.AbstractC0903n7
    /* renamed from: a8 */
    public final boolean mo214045a8() {
        return this.f61306a6.f61521b1.f43833b8;
    }

    @Override // p000.AbstractC0903n7
    /* renamed from: a9 */
    public final void mo214046a9(View view) {
        this.f61306a6.f61521b1.setCustomView(view);
        this.f61305a5 = new WeakReference(view);
    }

    @Override // p000.ze0
    /* renamed from: b0 */
    public final void mo214683b0(bf0 bf0Var) {
        if (this.f61304a4 == null) {
            return;
        }
        mo214044a7();
        C0041a1 c0041a1 = this.f61306a6.f61521b1.f43818a3;
        if (c0041a1 != null) {
            c0041a1.m209942b3();
        }
    }

    @Override // p000.AbstractC0903n7
    /* renamed from: b1 */
    public final void mo214047b1(int i) {
        mo214048b2(this.f61306a6.f61516a6.getResources().getString(i));
    }

    @Override // p000.AbstractC0903n7
    /* renamed from: b2 */
    public final void mo214048b2(CharSequence charSequence) {
        this.f61306a6.f61521b1.setSubtitle(charSequence);
    }

    @Override // p000.AbstractC0903n7
    /* renamed from: b3 */
    public final void mo214049b3(int i) {
        mo214050b4(this.f61306a6.f61516a6.getResources().getString(i));
    }

    @Override // p000.AbstractC0903n7
    /* renamed from: b4 */
    public final void mo214050b4(CharSequence charSequence) {
        this.f61306a6.f61521b1.setTitle(charSequence);
    }

    @Override // p000.AbstractC0903n7
    /* renamed from: b5 */
    public final void mo214051b5(boolean z) {
        this.f58461a1 = z;
        this.f61306a6.f61521b1.setTitleOptional(z);
    }
}
