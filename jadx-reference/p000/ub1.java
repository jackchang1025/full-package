package p000;

import android.view.View;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class ub1 {

    /* renamed from: a0 */
    public final View f60372a0;

    /* renamed from: a1 */
    public int f60373a1;

    /* renamed from: a2 */
    public int f60374a2;

    /* renamed from: a3 */
    public int f60375a3;

    public ub1(View view) {
        this.f60372a0 = view;
    }

    /* renamed from: a0 */
    public final void m214830a0() {
        int i = this.f60375a3;
        View view = this.f60372a0;
        int top = i - (view.getTop() - this.f60373a1);
        WeakHashMap weakHashMap = xa1.f61054a0;
        view.offsetTopAndBottom(top);
        view.offsetLeftAndRight(0 - (view.getLeft() - this.f60374a2));
    }

    /* renamed from: a1 */
    public final boolean m214831a1(int i) {
        if (this.f60375a3 == i) {
            return false;
        }
        this.f60375a3 = i;
        m214830a0();
        return true;
    }
}
