package p000;

import android.view.View;
import java.util.List;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class ya0 {

    /* renamed from: a0 */
    public boolean f61275a0;

    /* renamed from: a1 */
    public int f61276a1;

    /* renamed from: a2 */
    public int f61277a2;

    /* renamed from: a3 */
    public int f61278a3;

    /* renamed from: a4 */
    public int f61279a4;

    /* renamed from: a5 */
    public int f61280a5;

    /* renamed from: a6 */
    public int f61281a6;

    /* renamed from: a7 */
    public int f61282a7;

    /* renamed from: a8 */
    public int f61283a8;

    /* renamed from: a9 */
    public int f61284a9;

    /* renamed from: b0 */
    public List f61285b0;

    /* renamed from: b1 */
    public boolean f61286b1;

    /* renamed from: a0 */
    public final void m215272a0(View view) {
        int iM212621a1;
        int size = this.f61285b0.size();
        View view2 = null;
        int i = Integer.MAX_VALUE;
        for (int i2 = 0; i2 < size; i2++) {
            View view3 = ((dr0) this.f61285b0.get(i2)).f55849a0;
            qq0 qq0Var = (qq0) view3.getLayoutParams();
            if (view3 != view && !qq0Var.f59544a0.m212627a7() && (iM212621a1 = (qq0Var.f59544a0.m212621a1() - this.f61278a3) * this.f61279a4) >= 0 && iM212621a1 < i) {
                view2 = view3;
                if (iM212621a1 == 0) {
                    break;
                } else {
                    i = iM212621a1;
                }
            }
        }
        if (view2 == null) {
            this.f61278a3 = -1;
        } else {
            this.f61278a3 = ((qq0) view2.getLayoutParams()).f59544a0.m212621a1();
        }
    }

    /* renamed from: a1 */
    public final View m215273a1(vq0 vq0Var) {
        List list = this.f61285b0;
        if (list == null) {
            View view = vq0Var.m214946a8(this.f61278a3, Long.MAX_VALUE).f55849a0;
            this.f61278a3 += this.f61279a4;
            return view;
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            View view2 = ((dr0) this.f61285b0.get(i)).f55849a0;
            qq0 qq0Var = (qq0) view2.getLayoutParams();
            if (!qq0Var.f59544a0.m212627a7() && this.f61278a3 == qq0Var.f59544a0.m212621a1()) {
                m215272a0(view2);
                return view2;
            }
        }
        return null;
    }
}
