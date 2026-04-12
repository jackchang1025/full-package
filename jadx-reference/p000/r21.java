package p000;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public class r21 extends bf0 implements SubMenu {

    /* renamed from: c5 */
    public final bf0 f59605c5;

    /* renamed from: c6 */
    public final ff0 f59606c6;

    public r21(Context context, bf0 bf0Var, ff0 ff0Var) {
        super(context);
        this.f59605c5 = bf0Var;
        this.f59606c6 = ff0Var;
    }

    @Override // p000.bf0
    /* renamed from: a3 */
    public final boolean mo210691a3(ff0 ff0Var) {
        return this.f59605c5.mo210691a3(ff0Var);
    }

    @Override // p000.bf0
    /* renamed from: a4 */
    public final boolean mo210692a4(bf0 bf0Var, MenuItem menuItem) {
        return super.mo210692a4(bf0Var, menuItem) || this.f59605c5.mo210692a4(bf0Var, menuItem);
    }

    @Override // p000.bf0
    /* renamed from: a5 */
    public final boolean mo210693a5(ff0 ff0Var) {
        return this.f59605c5.mo210693a5(ff0Var);
    }

    @Override // p000.bf0
    /* renamed from: a9 */
    public final String mo210697a9() {
        ff0 ff0Var = this.f59606c6;
        int i = ff0Var != null ? ff0Var.f56205a0 : 0;
        if (i == 0) {
            return null;
        }
        return tz0.m214802a2(i, "android:menu:actionviewstates:");
    }

    @Override // p000.bf0
    /* renamed from: b0 */
    public final bf0 mo210698b0() {
        return this.f59605c5.mo210698b0();
    }

    @Override // p000.bf0
    /* renamed from: b2 */
    public final boolean mo210700b2() {
        return this.f59605c5.mo210700b2();
    }

    @Override // p000.bf0
    /* renamed from: b3 */
    public final boolean mo210701b3() {
        return this.f59605c5.mo210701b3();
    }

    @Override // p000.bf0
    /* renamed from: b4 */
    public final boolean mo210702b4() {
        return this.f59605c5.mo210702b4();
    }

    @Override // android.view.SubMenu
    public final MenuItem getItem() {
        return this.f59606c6;
    }

    @Override // p000.bf0, androidx.core.internal.view.SupportMenu, android.view.Menu
    public final void setGroupDividerEnabled(boolean z) {
        this.f59605c5.setGroupDividerEnabled(z);
    }

    @Override // android.view.SubMenu
    public final SubMenu setHeaderIcon(Drawable drawable) {
        m210710c2(0, null, 0, drawable, null);
        return this;
    }

    @Override // android.view.SubMenu
    public final SubMenu setHeaderTitle(CharSequence charSequence) {
        m210710c2(0, charSequence, 0, null, null);
        return this;
    }

    @Override // android.view.SubMenu
    public final SubMenu setHeaderView(View view) {
        m210710c2(0, null, 0, null, view);
        return this;
    }

    @Override // android.view.SubMenu
    public final SubMenu setIcon(Drawable drawable) {
        this.f59606c6.setIcon(drawable);
        return this;
    }

    @Override // p000.bf0, android.view.Menu
    public final void setQwertyMode(boolean z) {
        this.f59605c5.setQwertyMode(z);
    }

    @Override // android.view.SubMenu
    public final SubMenu setHeaderIcon(int i) {
        m210710c2(0, null, i, null, null);
        return this;
    }

    @Override // android.view.SubMenu
    public final SubMenu setHeaderTitle(int i) {
        m210710c2(i, null, 0, null, null);
        return this;
    }

    @Override // android.view.SubMenu
    public final SubMenu setIcon(int i) {
        this.f59606c6.setIcon(i);
        return this;
    }
}
