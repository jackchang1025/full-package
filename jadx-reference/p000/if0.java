package p000;

import android.view.MenuItem;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class if0 implements MenuItem.OnActionExpandListener {

    /* renamed from: a0 */
    public final MenuItem.OnActionExpandListener f56878a0;

    /* renamed from: a1 */
    public final /* synthetic */ jf0 f56879a1;

    public if0(jf0 jf0Var, MenuItem.OnActionExpandListener onActionExpandListener) {
        this.f56879a1 = jf0Var;
        this.f56878a0 = onActionExpandListener;
    }

    @Override // android.view.MenuItem.OnActionExpandListener
    public final boolean onMenuItemActionCollapse(MenuItem menuItem) {
        return this.f56878a0.onMenuItemActionCollapse(this.f56879a1.m212539b1(menuItem));
    }

    @Override // android.view.MenuItem.OnActionExpandListener
    public final boolean onMenuItemActionExpand(MenuItem menuItem) {
        return this.f56878a0.onMenuItemActionExpand(this.f56879a1.m212539b1(menuItem));
    }
}
