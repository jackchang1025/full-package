package p000;

import android.view.View;
import android.view.ViewGroup;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class nq0 {

    /* renamed from: a0 */
    public final /* synthetic */ int f58686a0;

    /* renamed from: a1 */
    public final /* synthetic */ pq0 f58687a1;

    public /* synthetic */ nq0(pq0 pq0Var, int i) {
        this.f58686a0 = i;
        this.f58687a1 = pq0Var;
    }

    /* renamed from: a0 */
    public final int m214136a0(View view) {
        int right;
        int i;
        switch (this.f58686a0) {
            case 0:
                qq0 qq0Var = (qq0) view.getLayoutParams();
                right = view.getRight() + ((qq0) view.getLayoutParams()).f59545a1.right;
                i = ((ViewGroup.MarginLayoutParams) qq0Var).rightMargin;
                break;
            default:
                qq0 qq0Var2 = (qq0) view.getLayoutParams();
                right = view.getBottom() + ((qq0) view.getLayoutParams()).f59545a1.bottom;
                i = ((ViewGroup.MarginLayoutParams) qq0Var2).bottomMargin;
                break;
        }
        return right + i;
    }

    /* renamed from: a1 */
    public final int m214137a1(View view) {
        int left;
        int i;
        switch (this.f58686a0) {
            case 0:
                qq0 qq0Var = (qq0) view.getLayoutParams();
                left = view.getLeft() - ((qq0) view.getLayoutParams()).f59545a1.left;
                i = ((ViewGroup.MarginLayoutParams) qq0Var).leftMargin;
                break;
            default:
                qq0 qq0Var2 = (qq0) view.getLayoutParams();
                left = view.getTop() - ((qq0) view.getLayoutParams()).f59545a1.top;
                i = ((ViewGroup.MarginLayoutParams) qq0Var2).topMargin;
                break;
        }
        return left - i;
    }

    /* renamed from: a2 */
    public final int m214138a2() {
        int i;
        int iM214315c8;
        switch (this.f58686a0) {
            case 0:
                pq0 pq0Var = this.f58687a1;
                i = pq0Var.f59331b3;
                iM214315c8 = pq0Var.m214315c8();
                break;
            default:
                pq0 pq0Var2 = this.f58687a1;
                i = pq0Var2.f59332b4;
                iM214315c8 = pq0Var2.m214313c6();
                break;
        }
        return i - iM214315c8;
    }

    /* renamed from: a3 */
    public final int m214139a3() {
        switch (this.f58686a0) {
            case 0:
                return this.f58687a1.m214314c7();
            default:
                return this.f58687a1.m214316c9();
        }
    }
}
