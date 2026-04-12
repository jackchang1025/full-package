package p000;

import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public class fm0 extends rq0 {

    /* renamed from: a0 */
    public RecyclerView f56287a0;

    /* renamed from: a1 */
    public final d11 f56288a1 = new d11(this);

    /* renamed from: a2 */
    public sl0 f56289a2;

    /* renamed from: a3 */
    public sl0 f56290a3;

    /* renamed from: a2 */
    public static int m212833a2(View view, AbstractC1371wc abstractC1371wc) {
        return ((abstractC1371wc.mo214622a2(view) / 2) + abstractC1371wc.mo214624a4(view)) - ((abstractC1371wc.mo214631b1() / 2) + abstractC1371wc.mo214630b0());
    }

    /* renamed from: a3 */
    public static View m212834a3(pq0 pq0Var, AbstractC1371wc abstractC1371wc) {
        int iM214311c1 = pq0Var.m214311c1();
        View view = null;
        if (iM214311c1 == 0) {
            return null;
        }
        int iMo214631b1 = (abstractC1371wc.mo214631b1() / 2) + abstractC1371wc.mo214630b0();
        int i = Integer.MAX_VALUE;
        for (int i2 = 0; i2 < iM214311c1; i2++) {
            View viewM214310c0 = pq0Var.m214310c0(i2);
            int iAbs = Math.abs(((abstractC1371wc.mo214622a2(viewM214310c0) / 2) + abstractC1371wc.mo214624a4(viewM214310c0)) - iMo214631b1);
            if (iAbs < i) {
                view = viewM214310c0;
                i = iAbs;
            }
        }
        return view;
    }

    /* renamed from: a0 */
    public final void m212835a0(RecyclerView recyclerView) {
        RecyclerView recyclerView2 = this.f56287a0;
        if (recyclerView2 == recyclerView) {
            return;
        }
        d11 d11Var = this.f56288a1;
        if (recyclerView2 != null) {
            ArrayList arrayList = recyclerView2.f45308f4;
            if (arrayList != null) {
                arrayList.remove(d11Var);
            }
            this.f56287a0.setOnFlingListener(null);
        }
        this.f56287a0 = recyclerView;
        if (recyclerView != null) {
            if (recyclerView.getOnFlingListener() != null) {
                throw new IllegalStateException("An instance of OnFlingListener already set.");
            }
            this.f56287a0.m210348a7(d11Var);
            this.f56287a0.setOnFlingListener(this);
            new Scroller(this.f56287a0.getContext(), new DecelerateInterpolator());
            m212840a7();
        }
    }

    /* renamed from: a1 */
    public final int[] m212836a1(pq0 pq0Var, View view) {
        int[] iArr = new int[2];
        if (pq0Var.mo210298a3()) {
            iArr[0] = m212833a2(view, m212838a5(pq0Var));
        } else {
            iArr[0] = 0;
        }
        if (pq0Var.mo210299a4()) {
            iArr[1] = m212833a2(view, m212839a6(pq0Var));
            return iArr;
        }
        iArr[1] = 0;
        return iArr;
    }

    /* renamed from: a4 */
    public View mo212837a4(pq0 pq0Var) {
        if (pq0Var.mo210299a4()) {
            return m212834a3(pq0Var, m212839a6(pq0Var));
        }
        if (pq0Var.mo210298a3()) {
            return m212834a3(pq0Var, m212838a5(pq0Var));
        }
        return null;
    }

    /* renamed from: a5 */
    public final AbstractC1371wc m212838a5(pq0 pq0Var) {
        sl0 sl0Var = this.f56290a3;
        if (sl0Var == null || ((pq0) sl0Var.f60888a1) != pq0Var) {
            this.f56290a3 = new sl0(pq0Var, 0);
        }
        return this.f56290a3;
    }

    /* renamed from: a6 */
    public final AbstractC1371wc m212839a6(pq0 pq0Var) {
        sl0 sl0Var = this.f56289a2;
        if (sl0Var == null || ((pq0) sl0Var.f60888a1) != pq0Var) {
            this.f56289a2 = new sl0(pq0Var, 1);
        }
        return this.f56289a2;
    }

    /* renamed from: a7 */
    public final void m212840a7() {
        pq0 layoutManager;
        View viewMo212837a4;
        RecyclerView recyclerView = this.f56287a0;
        if (recyclerView == null || (layoutManager = recyclerView.getLayoutManager()) == null || (viewMo212837a4 = mo212837a4(layoutManager)) == null) {
            return;
        }
        int[] iArrM212836a1 = m212836a1(layoutManager, viewMo212837a4);
        int i = iArrM212836a1[0];
        if (i == 0 && iArrM212836a1[1] == 0) {
            return;
        }
        this.f56287a0.m210391f3(i, iArrM212836a1[1], false);
    }
}
