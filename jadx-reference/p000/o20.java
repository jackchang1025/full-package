package p000;

import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class o20 implements Runnable {

    /* renamed from: a4 */
    public static final ThreadLocal f58720a4 = new ThreadLocal();

    /* renamed from: a5 */
    public static final C1214s9 f58721a5 = new C1214s9(7);

    /* renamed from: a0 */
    public ArrayList f58722a0;

    /* renamed from: a1 */
    public long f58723a1;

    /* renamed from: a2 */
    public long f58724a2;

    /* renamed from: a3 */
    public ArrayList f58725a3;

    /* renamed from: a2 */
    public static dr0 m214149a2(RecyclerView recyclerView, int i, long j) {
        int iM214283c8 = recyclerView.f45258a4.m214283c8();
        for (int i2 = 0; i2 < iM214283c8; i2++) {
            dr0 dr0VarM210345d5 = RecyclerView.m210345d5(recyclerView.f45258a4.m214282c7(i2));
            if (dr0VarM210345d5.f55851a2 == i && !dr0VarM210345d5.m212625a5()) {
                return null;
            }
        }
        vq0 vq0Var = recyclerView.f45255a1;
        try {
            recyclerView.m210380e2();
            dr0 dr0VarM214946a8 = vq0Var.m214946a8(i, j);
            if (dr0VarM214946a8 != null) {
                if (!dr0VarM214946a8.m212624a4() || dr0VarM214946a8.m212625a5()) {
                    vq0Var.m214938a0(dr0VarM214946a8, false);
                } else {
                    vq0Var.m214943a5(dr0VarM214946a8.f55849a0);
                }
            }
            recyclerView.m210381e3(false);
            return dr0VarM214946a8;
        } catch (Throwable th) {
            recyclerView.m210381e3(false);
            throw th;
        }
    }

    /* renamed from: a0 */
    public final void m214150a0(RecyclerView recyclerView, int i, int i2) {
        if (recyclerView.f45269b5 && this.f58723a1 == 0) {
            this.f58723a1 = recyclerView.getNanoTime();
            recyclerView.post(this);
        }
        m20 m20Var = recyclerView.f45305f1;
        m20Var.f58243a0 = i;
        m20Var.f58244a1 = i2;
    }

    /* JADX WARN: Removed duplicated region for block: B:47:0x00cb  */
    /* renamed from: a1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m214151a1(long j) {
        n20 n20Var;
        RecyclerView recyclerView;
        RecyclerView recyclerView2;
        n20 n20Var2;
        ArrayList arrayList = this.f58725a3;
        ArrayList arrayList2 = this.f58722a0;
        int size = arrayList2.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            RecyclerView recyclerView3 = (RecyclerView) arrayList2.get(i2);
            int windowVisibility = recyclerView3.getWindowVisibility();
            m20 m20Var = recyclerView3.f45305f1;
            if (windowVisibility == 0) {
                m20Var.m213933a1(recyclerView3, false);
                i += m20Var.f58246a3;
            }
        }
        arrayList.ensureCapacity(i);
        int i3 = 0;
        for (int i4 = 0; i4 < size; i4++) {
            RecyclerView recyclerView4 = (RecyclerView) arrayList2.get(i4);
            if (recyclerView4.getWindowVisibility() == 0) {
                m20 m20Var2 = recyclerView4.f45305f1;
                int iAbs = Math.abs(m20Var2.f58244a1) + Math.abs(m20Var2.f58243a0);
                for (int i5 = 0; i5 < m20Var2.f58246a3 * 2; i5 += 2) {
                    if (i3 >= arrayList.size()) {
                        n20Var2 = new n20();
                        arrayList.add(n20Var2);
                    } else {
                        n20Var2 = (n20) arrayList.get(i3);
                    }
                    int[] iArr = m20Var2.f58245a2;
                    int i6 = iArr[i5 + 1];
                    n20Var2.f58432a0 = i6 <= iAbs;
                    n20Var2.f58433a1 = iAbs;
                    n20Var2.f58434a2 = i6;
                    n20Var2.f58435a3 = recyclerView4;
                    n20Var2.f58436a4 = iArr[i5];
                    i3++;
                }
            }
        }
        Collections.sort(arrayList, f58721a5);
        for (int i7 = 0; i7 < arrayList.size() && (recyclerView = (n20Var = (n20) arrayList.get(i7)).f58435a3) != null; i7++) {
            dr0 dr0VarM214149a2 = m214149a2(recyclerView, n20Var.f58436a4, n20Var.f58432a0 ? Long.MAX_VALUE : j);
            if (dr0VarM214149a2 != null && dr0VarM214149a2.f55850a1 != null && dr0VarM214149a2.m212624a4() && !dr0VarM214149a2.m212625a5() && (recyclerView2 = (RecyclerView) dr0VarM214149a2.f55850a1.get()) != null) {
                if (recyclerView2.f45279c5 && recyclerView2.f45258a4.m214283c8() != 0) {
                    vq0 vq0Var = recyclerView2.f45255a1;
                    lq0 lq0Var = recyclerView2.f45288d4;
                    if (lq0Var != null) {
                        lq0Var.mo213918a4();
                    }
                    pq0 pq0Var = recyclerView2.f45265b1;
                    if (pq0Var != null) {
                        pq0Var.m214319f6(vq0Var);
                        recyclerView2.f45265b1.m214320f7(vq0Var);
                    }
                    vq0Var.f60667a0.clear();
                    vq0Var.m214941a3();
                }
                m20 m20Var3 = recyclerView2.f45305f1;
                m20Var3.m213933a1(recyclerView2, true);
                if (m20Var3.f58246a3 != 0) {
                    try {
                        int i8 = o71.f58750a0;
                        n71.m214052a0("RV Nested Prefetch");
                        ar0 ar0Var = recyclerView2.f45306f2;
                        gq0 gq0Var = recyclerView2.f45264b0;
                        ar0Var.f45599a3 = 1;
                        ar0Var.f45600a4 = gq0Var.mo211032a0();
                        ar0Var.f45602a6 = false;
                        ar0Var.f45603a7 = false;
                        ar0Var.f45604a8 = false;
                        for (int i9 = 0; i9 < m20Var3.f58246a3 * 2; i9 += 2) {
                            m214149a2(recyclerView2, m20Var3.f58245a2[i9], j);
                        }
                        n71.m214053a1();
                    } catch (Throwable th) {
                        int i10 = o71.f58750a0;
                        n71.m214053a1();
                        throw th;
                    }
                }
            }
            n20Var.f58432a0 = false;
            n20Var.f58433a1 = 0;
            n20Var.f58434a2 = 0;
            n20Var.f58435a3 = null;
            n20Var.f58436a4 = 0;
        }
    }

    @Override // java.lang.Runnable
    public final void run() {
        ArrayList arrayList = this.f58722a0;
        try {
            int i = o71.f58750a0;
            n71.m214052a0("RV Prefetch");
            if (!arrayList.isEmpty()) {
                int size = arrayList.size();
                long jMax = 0;
                for (int i2 = 0; i2 < size; i2++) {
                    RecyclerView recyclerView = (RecyclerView) arrayList.get(i2);
                    if (recyclerView.getWindowVisibility() == 0) {
                        jMax = Math.max(recyclerView.getDrawingTime(), jMax);
                    }
                }
                if (jMax != 0) {
                    m214151a1(TimeUnit.MILLISECONDS.toNanos(jMax) + this.f58724a2);
                }
            }
            this.f58723a1 = 0L;
            n71.m214053a1();
        } catch (Throwable th) {
            this.f58723a1 = 0L;
            int i3 = o71.f58750a0;
            n71.m214053a1();
            throw th;
        }
    }
}
