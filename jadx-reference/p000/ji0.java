package p000;

import android.view.SubMenu;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class ji0 extends bf0 {
    @Override // p000.bf0, android.view.Menu
    public final SubMenu addSubMenu(int i, int i2, int i3, CharSequence charSequence) {
        ff0 ff0VarMo210688a0 = mo210688a0(i, i2, i3, charSequence);
        xi0 xi0Var = new xi0(this.f45866a0, this, ff0VarMo210688a0);
        ff0VarMo210688a0.f56219b4 = xi0Var;
        xi0Var.setHeaderTitle(ff0VarMo210688a0.f56209a4);
        return xi0Var;
    }
}
