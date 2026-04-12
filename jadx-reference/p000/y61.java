package p000;

import android.view.MenuItem;
import androidx.appcompat.widget.C0041a1;
import androidx.appcompat.widget.Toolbar;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class y61 implements InterfaceC0902n6, ze0 {

    /* renamed from: a0 */
    public final /* synthetic */ Toolbar f61259a0;

    public /* synthetic */ y61(Toolbar toolbar) {
        this.f61259a0 = toolbar;
    }

    @Override // p000.ze0
    /* renamed from: a4 */
    public boolean mo214682a4(bf0 bf0Var, MenuItem menuItem) {
        this.f61259a0.getClass();
        return false;
    }

    @Override // p000.ze0
    /* renamed from: b0 */
    public void mo214683b0(bf0 bf0Var) {
        Toolbar toolbar = this.f61259a0;
        C0041a1 c0041a1 = toolbar.f44089a0.f43868b9;
        if (c0041a1 == null || !c0041a1.m209941a7()) {
            Iterator it = ((CopyOnWriteArrayList) toolbar.f44121d2.f56089a2).iterator();
            if (it.hasNext()) {
                throw AbstractC0003a2.m25a6(it);
            }
        }
    }
}
