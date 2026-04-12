package p000;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class sl0 extends AbstractC1371wc {

    /* renamed from: a3 */
    public final /* synthetic */ int f60016a3;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ sl0(pq0 pq0Var, int i) {
        super(pq0Var);
        this.f60016a3 = i;
    }

    @Override // p000.AbstractC1371wc
    /* renamed from: a1 */
    public final int mo214621a1(View view) {
        int right;
        int i;
        switch (this.f60016a3) {
            case 0:
                qq0 qq0Var = (qq0) view.getLayoutParams();
                ((pq0) this.f60888a1).getClass();
                right = view.getRight() + ((qq0) view.getLayoutParams()).f59545a1.right;
                i = ((ViewGroup.MarginLayoutParams) qq0Var).rightMargin;
                break;
            default:
                qq0 qq0Var2 = (qq0) view.getLayoutParams();
                ((pq0) this.f60888a1).getClass();
                right = view.getBottom() + ((qq0) view.getLayoutParams()).f59545a1.bottom;
                i = ((ViewGroup.MarginLayoutParams) qq0Var2).bottomMargin;
                break;
        }
        return right + i;
    }

    @Override // p000.AbstractC1371wc
    /* renamed from: a2 */
    public final int mo214622a2(View view) {
        int measuredWidth;
        int i;
        switch (this.f60016a3) {
            case 0:
                qq0 qq0Var = (qq0) view.getLayoutParams();
                ((pq0) this.f60888a1).getClass();
                Rect rect = ((qq0) view.getLayoutParams()).f59545a1;
                measuredWidth = view.getMeasuredWidth() + rect.left + rect.right + ((ViewGroup.MarginLayoutParams) qq0Var).leftMargin;
                i = ((ViewGroup.MarginLayoutParams) qq0Var).rightMargin;
                break;
            default:
                qq0 qq0Var2 = (qq0) view.getLayoutParams();
                ((pq0) this.f60888a1).getClass();
                Rect rect2 = ((qq0) view.getLayoutParams()).f59545a1;
                measuredWidth = view.getMeasuredHeight() + rect2.top + rect2.bottom + ((ViewGroup.MarginLayoutParams) qq0Var2).topMargin;
                i = ((ViewGroup.MarginLayoutParams) qq0Var2).bottomMargin;
                break;
        }
        return measuredWidth + i;
    }

    @Override // p000.AbstractC1371wc
    /* renamed from: a3 */
    public final int mo214623a3(View view) {
        int measuredHeight;
        int i;
        switch (this.f60016a3) {
            case 0:
                qq0 qq0Var = (qq0) view.getLayoutParams();
                ((pq0) this.f60888a1).getClass();
                Rect rect = ((qq0) view.getLayoutParams()).f59545a1;
                measuredHeight = view.getMeasuredHeight() + rect.top + rect.bottom + ((ViewGroup.MarginLayoutParams) qq0Var).topMargin;
                i = ((ViewGroup.MarginLayoutParams) qq0Var).bottomMargin;
                break;
            default:
                qq0 qq0Var2 = (qq0) view.getLayoutParams();
                ((pq0) this.f60888a1).getClass();
                Rect rect2 = ((qq0) view.getLayoutParams()).f59545a1;
                measuredHeight = view.getMeasuredWidth() + rect2.left + rect2.right + ((ViewGroup.MarginLayoutParams) qq0Var2).leftMargin;
                i = ((ViewGroup.MarginLayoutParams) qq0Var2).rightMargin;
                break;
        }
        return measuredHeight + i;
    }

    @Override // p000.AbstractC1371wc
    /* renamed from: a4 */
    public final int mo214624a4(View view) {
        int left;
        int i;
        switch (this.f60016a3) {
            case 0:
                qq0 qq0Var = (qq0) view.getLayoutParams();
                ((pq0) this.f60888a1).getClass();
                left = view.getLeft() - ((qq0) view.getLayoutParams()).f59545a1.left;
                i = ((ViewGroup.MarginLayoutParams) qq0Var).leftMargin;
                break;
            default:
                qq0 qq0Var2 = (qq0) view.getLayoutParams();
                ((pq0) this.f60888a1).getClass();
                left = view.getTop() - ((qq0) view.getLayoutParams()).f59545a1.top;
                i = ((ViewGroup.MarginLayoutParams) qq0Var2).topMargin;
                break;
        }
        return left - i;
    }

    @Override // p000.AbstractC1371wc
    /* renamed from: a5 */
    public final int mo214625a5() {
        switch (this.f60016a3) {
            case 0:
                return ((pq0) this.f60888a1).f59331b3;
            default:
                return ((pq0) this.f60888a1).f59332b4;
        }
    }

    @Override // p000.AbstractC1371wc
    /* renamed from: a6 */
    public final int mo214626a6() {
        int i;
        int iM214315c8;
        switch (this.f60016a3) {
            case 0:
                pq0 pq0Var = (pq0) this.f60888a1;
                i = pq0Var.f59331b3;
                iM214315c8 = pq0Var.m214315c8();
                break;
            default:
                pq0 pq0Var2 = (pq0) this.f60888a1;
                i = pq0Var2.f59332b4;
                iM214315c8 = pq0Var2.m214313c6();
                break;
        }
        return i - iM214315c8;
    }

    @Override // p000.AbstractC1371wc
    /* renamed from: a7 */
    public final int mo214627a7() {
        switch (this.f60016a3) {
            case 0:
                return ((pq0) this.f60888a1).m214315c8();
            default:
                return ((pq0) this.f60888a1).m214313c6();
        }
    }

    @Override // p000.AbstractC1371wc
    /* renamed from: a8 */
    public final int mo214628a8() {
        switch (this.f60016a3) {
            case 0:
                return ((pq0) this.f60888a1).f59329b1;
            default:
                return ((pq0) this.f60888a1).f59330b2;
        }
    }

    @Override // p000.AbstractC1371wc
    /* renamed from: a9 */
    public final int mo214629a9() {
        switch (this.f60016a3) {
            case 0:
                return ((pq0) this.f60888a1).f59330b2;
            default:
                return ((pq0) this.f60888a1).f59329b1;
        }
    }

    @Override // p000.AbstractC1371wc
    /* renamed from: b0 */
    public final int mo214630b0() {
        switch (this.f60016a3) {
            case 0:
                return ((pq0) this.f60888a1).m214314c7();
            default:
                return ((pq0) this.f60888a1).m214316c9();
        }
    }

    @Override // p000.AbstractC1371wc
    /* renamed from: b1 */
    public final int mo214631b1() {
        int iM214314c7;
        int iM214315c8;
        switch (this.f60016a3) {
            case 0:
                pq0 pq0Var = (pq0) this.f60888a1;
                iM214314c7 = pq0Var.f59331b3 - pq0Var.m214314c7();
                iM214315c8 = pq0Var.m214315c8();
                break;
            default:
                pq0 pq0Var2 = (pq0) this.f60888a1;
                iM214314c7 = pq0Var2.f59332b4 - pq0Var2.m214316c9();
                iM214315c8 = pq0Var2.m214313c6();
                break;
        }
        return iM214314c7 - iM214315c8;
    }

    @Override // p000.AbstractC1371wc
    /* renamed from: b2 */
    public final int mo214632b2(View view) {
        switch (this.f60016a3) {
            case 0:
                pq0 pq0Var = (pq0) this.f60888a1;
                Rect rect = (Rect) this.f60889a2;
                pq0Var.m214317d3(view, rect);
                return rect.right;
            default:
                pq0 pq0Var2 = (pq0) this.f60888a1;
                Rect rect2 = (Rect) this.f60889a2;
                pq0Var2.m214317d3(view, rect2);
                return rect2.bottom;
        }
    }

    @Override // p000.AbstractC1371wc
    /* renamed from: b3 */
    public final int mo214633b3(View view) {
        switch (this.f60016a3) {
            case 0:
                pq0 pq0Var = (pq0) this.f60888a1;
                Rect rect = (Rect) this.f60889a2;
                pq0Var.m214317d3(view, rect);
                return rect.left;
            default:
                pq0 pq0Var2 = (pq0) this.f60888a1;
                Rect rect2 = (Rect) this.f60889a2;
                pq0Var2.m214317d3(view, rect2);
                return rect2.top;
        }
    }

    @Override // p000.AbstractC1371wc
    /* renamed from: b4 */
    public final void mo214634b4(int i) {
        switch (this.f60016a3) {
            case 0:
                ((pq0) this.f60888a1).mo210396d7(i);
                break;
            default:
                ((pq0) this.f60888a1).mo210397d8(i);
                break;
        }
    }
}
