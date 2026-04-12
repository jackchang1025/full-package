package p000;

import android.content.Context;
import android.view.SubMenu;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class fi0 extends bf0 {

    /* renamed from: c5 */
    public final Class f56276c5;

    /* renamed from: c6 */
    public final int f56277c6;

    public fi0(Context context, Class cls, int i) {
        super(context);
        this.f56276c5 = cls;
        this.f56277c6 = i;
    }

    @Override // p000.bf0
    /* renamed from: a0 */
    public final ff0 mo210688a0(int i, int i2, int i3, CharSequence charSequence) {
        int size = this.f45871a5.size() + 1;
        int i4 = this.f56277c6;
        if (size > i4) {
            String simpleName = this.f56276c5.getSimpleName();
            throw new IllegalArgumentException(AbstractC0003a2.m35b6(AbstractC0003a2.m40c1("Maximum number of items supported by ", simpleName, " is ", i4, ". Limit can be checked with "), simpleName, "#getMaxItemCount()"));
        }
        m210712c4();
        ff0 ff0VarMo210688a0 = super.mo210688a0(i, i2, i3, charSequence);
        ff0VarMo210688a0.m212799a3(true);
        m210711c3();
        return ff0VarMo210688a0;
    }

    @Override // p000.bf0, android.view.Menu
    public final SubMenu addSubMenu(int i, int i2, int i3, CharSequence charSequence) {
        throw new UnsupportedOperationException(this.f56276c5.getSimpleName().concat(" does not support submenus"));
    }
}
