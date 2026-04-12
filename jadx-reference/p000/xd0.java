package p000;

import android.view.View;
import com.google.android.material.datepicker.C0202b2;
import java.util.Iterator;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class xd0 implements View.OnClickListener {

    /* renamed from: a0 */
    public final /* synthetic */ int f61071a0;

    /* renamed from: a1 */
    public final /* synthetic */ C0202b2 f61072a1;

    public /* synthetic */ xd0(C0202b2 c0202b2, int i) {
        this.f61071a0 = i;
        this.f61072a1 = c0202b2;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        switch (this.f61071a0) {
            case 0:
                C0202b2 c0202b2 = this.f61072a1;
                Iterator it = c0202b2.f49424f9.iterator();
                if (!it.hasNext()) {
                    c0202b2.m214670d0(false, false);
                    return;
                } else {
                    if (it.next() != null) {
                        throw new ClassCastException();
                    }
                    c0202b2.m211024d2().getClass();
                    throw null;
                }
            case 1:
                C0202b2 c0202b22 = this.f61072a1;
                Iterator it2 = c0202b22.f49425g0.iterator();
                while (it2.hasNext()) {
                    ((View.OnClickListener) it2.next()).onClick(view);
                }
                c0202b22.m214670d0(false, false);
                return;
            default:
                C0202b2 c0202b23 = this.f61072a1;
                c0202b23.f49446i1.setEnabled(c0202b23.m211024d2().mo211001a5());
                c0202b23.f49444h9.toggle();
                c0202b23.m211026d6(c0202b23.f49444h9);
                c0202b23.m211025d5();
                return;
        }
    }
}
