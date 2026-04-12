package p000;

import android.view.ActionProvider;
import android.view.View;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class gf0 extends AbstractC0904n8 implements ActionProvider.VisibilityListener {

    /* renamed from: a1 */
    public final ActionProvider f56454a1;

    /* renamed from: a2 */
    public final /* synthetic */ jf0 f56455a2;

    /* renamed from: a3 */
    public tg0 f56456a3;

    public gf0(jf0 jf0Var, ActionProvider actionProvider) {
        this.f56455a2 = jf0Var;
        this.f56454a1 = actionProvider;
    }

    @Override // p000.AbstractC0904n8
    /* renamed from: a0 */
    public final boolean mo212941a0() {
        return this.f56454a1.isVisible();
    }

    @Override // p000.AbstractC0904n8
    /* renamed from: a1 */
    public final View mo212942a1(ff0 ff0Var) {
        return this.f56454a1.onCreateActionView(ff0Var);
    }

    @Override // p000.AbstractC0904n8
    /* renamed from: a2 */
    public final boolean mo212943a2() {
        return this.f56454a1.overridesItemVisibility();
    }

    @Override // p000.AbstractC0904n8
    /* renamed from: a3 */
    public final void mo212944a3(tg0 tg0Var) {
        this.f56456a3 = tg0Var;
        this.f56454a1.setVisibilityListener(this);
    }

    @Override // android.view.ActionProvider.VisibilityListener
    public final void onActionProviderVisibilityChanged(boolean z) {
        tg0 tg0Var = this.f56456a3;
        if (tg0Var != null) {
            bf0 bf0Var = ((ff0) tg0Var.f60218a1).f56218b3;
            bf0Var.f45873a7 = true;
            bf0Var.mo210703b5(true);
        }
    }
}
