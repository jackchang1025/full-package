package p000;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import androidx.recyclerview.widget.RecyclerView;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.WeakHashMap;
import okio.Segment;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class vq0 {

    /* renamed from: a0 */
    public final ArrayList f60667a0;

    /* renamed from: a1 */
    public ArrayList f60668a1;

    /* renamed from: a2 */
    public final ArrayList f60669a2;

    /* renamed from: a3 */
    public final List f60670a3;

    /* renamed from: a4 */
    public int f60671a4;

    /* renamed from: a5 */
    public int f60672a5;

    /* renamed from: a6 */
    public uq0 f60673a6;

    /* renamed from: a7 */
    public final /* synthetic */ RecyclerView f60674a7;

    public vq0(RecyclerView recyclerView) {
        this.f60674a7 = recyclerView;
        ArrayList arrayList = new ArrayList();
        this.f60667a0 = arrayList;
        this.f60668a1 = null;
        this.f60669a2 = new ArrayList();
        this.f60670a3 = Collections.unmodifiableList(arrayList);
        this.f60671a4 = 2;
        this.f60672a5 = 2;
    }

    /* renamed from: a0 */
    public final void m214938a0(dr0 dr0Var, boolean z) {
        RecyclerView.m210343a9(dr0Var);
        View view = dr0Var.f55849a0;
        RecyclerView recyclerView = this.f60674a7;
        er0 er0Var = recyclerView.f45313f9;
        if (er0Var != null) {
            C1293ua c1293ua = er0Var.f56102a4;
            xa1.m215152b4(view, c1293ua != null ? (C0608i4) ((WeakHashMap) c1293ua.f60358a5).remove(view) : null);
        }
        if (z) {
            gq0 gq0Var = recyclerView.f45264b0;
            if (gq0Var != null) {
                gq0Var.mo212979a5(dr0Var);
            }
            if (recyclerView.f45306f2 != null) {
                recyclerView.f45259a5.m212719c3(dr0Var);
            }
        }
        dr0Var.f55866b7 = null;
        uq0 uq0VarM214940a2 = m214940a2();
        uq0VarM214940a2.getClass();
        int i = dr0Var.f55854a5;
        ArrayList arrayList = uq0VarM214940a2.m214860a0(i).f60249a0;
        if (((tq0) uq0VarM214940a2.f60504a0.get(i)).f60250a1 <= arrayList.size()) {
            return;
        }
        dr0Var.m212632b2();
        arrayList.add(dr0Var);
    }

    /* renamed from: a1 */
    public final int m214939a1(int i) {
        RecyclerView recyclerView = this.f60674a7;
        if (i >= 0 && i < recyclerView.f45306f2.m210500a1()) {
            return !recyclerView.f45306f2.f45602a6 ? i : recyclerView.f45257a3.m214342a4(i, 0);
        }
        throw new IndexOutOfBoundsException("invalid position " + i + ". State item count is " + recyclerView.f45306f2.m210500a1() + recyclerView.m210365c5());
    }

    /* renamed from: a2 */
    public final uq0 m214940a2() {
        if (this.f60673a6 == null) {
            uq0 uq0Var = new uq0();
            uq0Var.f60504a0 = new SparseArray();
            uq0Var.f60505a1 = 0;
            this.f60673a6 = uq0Var;
        }
        return this.f60673a6;
    }

    /* renamed from: a3 */
    public final void m214941a3() {
        ArrayList arrayList = this.f60669a2;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            m214942a4(size);
        }
        arrayList.clear();
        int[] iArr = RecyclerView.f45251g8;
        m20 m20Var = this.f60674a7.f45305f1;
        int[] iArr2 = m20Var.f58245a2;
        if (iArr2 != null) {
            Arrays.fill(iArr2, -1);
        }
        m20Var.f58246a3 = 0;
    }

    /* renamed from: a4 */
    public final void m214942a4(int i) {
        ArrayList arrayList = this.f60669a2;
        m214938a0((dr0) arrayList.get(i), true);
        arrayList.remove(i);
    }

    /* renamed from: a5 */
    public final void m214943a5(View view) {
        dr0 dr0VarM210345d5 = RecyclerView.m210345d5(view);
        boolean zM212629a9 = dr0VarM210345d5.m212629a9();
        RecyclerView recyclerView = this.f60674a7;
        if (zM212629a9) {
            recyclerView.removeDetachedView(view, false);
        }
        if (dr0VarM210345d5.m212628a8()) {
            dr0VarM210345d5.f55862b3.m214947a9(dr0VarM210345d5);
        } else if (dr0VarM210345d5.m212635b5()) {
            dr0VarM210345d5.f55858a9 &= -33;
        }
        m214944a6(dr0VarM210345d5);
        if (recyclerView.f45288d4 == null || dr0VarM210345d5.m212626a6()) {
            return;
        }
        recyclerView.f45288d4.mo213917a3(dr0VarM210345d5);
    }

    /* JADX WARN: Code restructure failed: missing block: B:46:0x008d, code lost:
    
        r6 = r6 - 1;
     */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0032  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0074  */
    /* renamed from: a6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m214944a6(dr0 dr0Var) {
        boolean z;
        boolean z2;
        int i;
        RecyclerView recyclerView = this.f60674a7;
        m20 m20Var = recyclerView.f45305f1;
        boolean zM212628a8 = dr0Var.m212628a8();
        View view = dr0Var.f55849a0;
        boolean z3 = true;
        if (zM212628a8 || view.getParent() != null) {
            StringBuilder sb = new StringBuilder("Scrapped or attached views may not be recycled. isScrap:");
            sb.append(dr0Var.m212628a8());
            sb.append(" isAttached:");
            sb.append(view.getParent() != null);
            sb.append(recyclerView.m210365c5());
            throw new IllegalArgumentException(sb.toString());
        }
        if (dr0Var.m212629a9()) {
            throw new IllegalArgumentException("Tmp detached view should be removed from RecyclerView before it can be recycled: " + dr0Var + recyclerView.m210365c5());
        }
        if (dr0Var.m212634b4()) {
            throw new IllegalArgumentException("Trying to recycle an ignored view holder. You should first call stopIgnoringView(view) before calling recycle." + recyclerView.m210365c5());
        }
        if ((dr0Var.f55858a9 & 16) == 0) {
            WeakHashMap weakHashMap = xa1.f61054a0;
            z = fa1.m212771a8(view);
        }
        if (dr0Var.m212626a6()) {
            if (this.f60672a5 <= 0 || (dr0Var.f55858a9 & 526) != 0) {
                z2 = false;
            } else {
                ArrayList arrayList = this.f60669a2;
                int size = arrayList.size();
                if (size >= this.f60672a5 && size > 0) {
                    m214942a4(0);
                    size--;
                }
                int[] iArr = RecyclerView.f45251g8;
                if (size > 0) {
                    int i2 = dr0Var.f55851a2;
                    if (m20Var.f58245a2 != null) {
                        int i3 = m20Var.f58246a3 * 2;
                        for (int i4 = 0; i4 < i3; i4 += 2) {
                            if (m20Var.f58245a2[i4] == i2) {
                                break;
                            }
                        }
                        i = size - 1;
                        loop1: while (i >= 0) {
                            int i5 = ((dr0) arrayList.get(i)).f55851a2;
                            if (m20Var.f58245a2 == null) {
                                break;
                            }
                            int i6 = m20Var.f58246a3 * 2;
                            for (int i7 = 0; i7 < i6; i7 += 2) {
                                if (m20Var.f58245a2[i7] == i5) {
                                    break;
                                }
                            }
                            break loop1;
                        }
                        size = i + 1;
                    } else {
                        i = size - 1;
                        loop1: while (i >= 0) {
                        }
                        size = i + 1;
                    }
                }
                arrayList.add(size, dr0Var);
                z2 = true;
            }
            if (z2) {
                z3 = false;
            } else {
                m214938a0(dr0Var, true);
            }
            z = z2;
        } else {
            z3 = false;
        }
        recyclerView.f45259a5.m212719c3(dr0Var);
        if (z || z3 || !z) {
            return;
        }
        dr0Var.f55866b7 = null;
    }

    /* renamed from: a7 */
    public final void m214945a7(View view) {
        lq0 lq0Var;
        dr0 dr0VarM210345d5 = RecyclerView.m210345d5(view);
        int i = dr0VarM210345d5.f55858a9 & 12;
        RecyclerView recyclerView = this.f60674a7;
        if (i == 0 && dr0VarM210345d5.m212630b0() && (lq0Var = recyclerView.f45288d4) != null) {
            C1176rd c1176rd = (C1176rd) lq0Var;
            if (dr0VarM210345d5.m212622a2().isEmpty() && c1176rd.f59674a6 && !dr0VarM210345d5.m212625a5()) {
                if (this.f60668a1 == null) {
                    this.f60668a1 = new ArrayList();
                }
                dr0VarM210345d5.f55862b3 = this;
                dr0VarM210345d5.f55863b4 = true;
                this.f60668a1.add(dr0VarM210345d5);
                return;
            }
        }
        if (dr0VarM210345d5.m212625a5() && !dr0VarM210345d5.m212627a7() && !recyclerView.f45264b0.f56550a1) {
            throw new IllegalArgumentException("Called scrap view with an invalid view. Invalid views cannot be reused from scrap, they should rebound from recycler pool." + recyclerView.m210365c5());
        }
        dr0VarM210345d5.f55862b3 = this;
        dr0VarM210345d5.f55863b4 = false;
        this.f60667a0.add(dr0VarM210345d5);
    }

    /* JADX WARN: Removed duplicated region for block: B:110:0x01d8  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x0327  */
    /* JADX WARN: Removed duplicated region for block: B:179:0x032a  */
    /* JADX WARN: Removed duplicated region for block: B:218:0x03e0  */
    /* JADX WARN: Removed duplicated region for block: B:222:0x03ea  */
    /* JADX WARN: Removed duplicated region for block: B:223:0x03ed  */
    /* JADX WARN: Removed duplicated region for block: B:225:0x03f0  */
    /* JADX WARN: Removed duplicated region for block: B:230:0x0412  */
    /* JADX WARN: Removed duplicated region for block: B:233:0x041b  */
    /* JADX WARN: Removed duplicated region for block: B:235:0x0421  */
    /* JADX WARN: Removed duplicated region for block: B:246:0x044d  */
    /* JADX WARN: Removed duplicated region for block: B:252:0x0464  */
    /* JADX WARN: Removed duplicated region for block: B:298:0x051e  */
    /* JADX WARN: Removed duplicated region for block: B:299:0x0528  */
    /* JADX WARN: Removed duplicated region for block: B:305:0x053e A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0080  */
    /* renamed from: a8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final dr0 m214946a8(int i, long j) {
        boolean z;
        dr0 dr0VarMo211035a4;
        boolean z2;
        long j2;
        long j3;
        C0608i4 c0608i4;
        boolean z3;
        boolean z4;
        boolean z5;
        ViewGroup.LayoutParams layoutParams;
        qq0 qq0Var;
        int i2;
        dr0 dr0Var;
        int i3;
        View view;
        boolean z6;
        int size;
        int iM214342a4;
        RecyclerView recyclerView = this.f60674a7;
        ar0 ar0Var = recyclerView.f45306f2;
        if (i < 0 || i >= ar0Var.m210500a1()) {
            StringBuilder sbM38b9 = AbstractC0003a2.m38b9("Invalid item position ", i, "(", i, "). Item count:");
            sbM38b9.append(ar0Var.m210500a1());
            sbM38b9.append(recyclerView.m210365c5());
            throw new IndexOutOfBoundsException(sbM38b9.toString());
        }
        if (ar0Var.f45602a6) {
            ArrayList arrayList = this.f60668a1;
            if (arrayList == null || (size = arrayList.size()) == 0) {
                dr0VarMo211035a4 = null;
                z = dr0VarMo211035a4 == null;
            } else {
                int i4 = 0;
                while (true) {
                    if (i4 < size) {
                        dr0VarMo211035a4 = (dr0) this.f60668a1.get(i4);
                        if (!dr0VarMo211035a4.m212635b5() && dr0VarMo211035a4.m212621a1() == i) {
                            dr0VarMo211035a4.m212620a0(32);
                            break;
                        }
                        i4++;
                    } else if (recyclerView.f45264b0.f56550a1 && (iM214342a4 = recyclerView.f45257a3.m214342a4(i, 0)) > 0 && iM214342a4 < recyclerView.f45264b0.mo211032a0()) {
                        long jMo211033a1 = recyclerView.f45264b0.mo211033a1(iM214342a4);
                        for (int i5 = 0; i5 < size; i5++) {
                            dr0 dr0Var2 = (dr0) this.f60668a1.get(i5);
                            if (!dr0Var2.m212635b5() && dr0Var2.f55853a4 == jMo211033a1) {
                                dr0Var2.m212620a0(32);
                                dr0VarMo211035a4 = dr0Var2;
                                break;
                            }
                        }
                    }
                }
                dr0VarMo211035a4 = null;
                if (dr0VarMo211035a4 == null) {
                }
            }
        } else {
            z = false;
            dr0VarMo211035a4 = null;
        }
        ArrayList arrayList2 = this.f60667a0;
        ArrayList arrayList3 = this.f60669a2;
        if (dr0VarMo211035a4 == null) {
            int size2 = arrayList2.size();
            for (int i6 = 0; i6 < size2; i6++) {
                dr0 dr0Var3 = (dr0) arrayList2.get(i6);
                if (!dr0Var3.m212635b5() && dr0Var3.m212621a1() == i && !dr0Var3.m212625a5() && (ar0Var.f45602a6 || !dr0Var3.m212627a7())) {
                    dr0Var3.m212620a0(32);
                    dr0VarMo211035a4 = dr0Var3;
                    z2 = true;
                    break;
                }
            }
            ArrayList arrayList4 = (ArrayList) recyclerView.f45258a4.f59231a3;
            int size3 = arrayList4.size();
            int i7 = 0;
            while (true) {
                if (i7 >= size3) {
                    z2 = true;
                    view = null;
                    break;
                }
                view = (View) arrayList4.get(i7);
                dr0 dr0VarM210345d5 = RecyclerView.m210345d5(view);
                z2 = true;
                if (dr0VarM210345d5.m212621a1() == i && !dr0VarM210345d5.m212625a5() && !dr0VarM210345d5.m212627a7()) {
                    break;
                }
                i7++;
            }
            if (view == null) {
                int size4 = arrayList3.size();
                int i8 = 0;
                while (true) {
                    if (i8 >= size4) {
                        dr0VarMo211035a4 = null;
                        break;
                    }
                    dr0 dr0Var4 = (dr0) arrayList3.get(i8);
                    if (!dr0Var4.m212625a5() && dr0Var4.m212621a1() == i && !dr0Var4.m212623a3()) {
                        arrayList3.remove(i8);
                        dr0VarMo211035a4 = dr0Var4;
                        break;
                    }
                    i8++;
                }
            } else {
                dr0 dr0VarM210345d52 = RecyclerView.m210345d5(view);
                pg1 pg1Var = recyclerView.f45258a4;
                C0583hj c0583hj = (C0583hj) pg1Var.f59230a2;
                int iIndexOfChild = ((fq0) pg1Var.f59229a1).f56313a0.indexOfChild(view);
                if (iIndexOfChild < 0) {
                    throw new IllegalArgumentException("view is not a child, cannot hide " + view);
                }
                if (!c0583hj.m213043a3(iIndexOfChild)) {
                    throw new RuntimeException("trying to unhide a view that was not hidden" + view);
                }
                c0583hj.m213040a0(iIndexOfChild);
                pg1Var.m214290d6(view);
                pg1 pg1Var2 = recyclerView.f45258a4;
                C0583hj c0583hj2 = (C0583hj) pg1Var2.f59230a2;
                int iIndexOfChild2 = ((fq0) pg1Var2.f59229a1).f56313a0.indexOfChild(view);
                int iM213041a1 = (iIndexOfChild2 == -1 || c0583hj2.m213043a3(iIndexOfChild2)) ? -1 : iIndexOfChild2 - c0583hj2.m213041a1(iIndexOfChild2);
                if (iM213041a1 == -1) {
                    throw new IllegalStateException("layout index should not be -1 after unhiding a view:" + dr0VarM210345d52 + recyclerView.m210365c5());
                }
                recyclerView.f45258a4.m214269b3(iM213041a1);
                m214945a7(view);
                dr0VarM210345d52.m212620a0(8224);
                dr0VarMo211035a4 = dr0VarM210345d52;
            }
            if (dr0VarMo211035a4 != null) {
                if (dr0VarMo211035a4.m212627a7()) {
                    z6 = ar0Var.f45602a6;
                } else {
                    int i9 = dr0VarMo211035a4.f55851a2;
                    if (i9 < 0 || i9 >= recyclerView.f45264b0.mo211032a0()) {
                        throw new IndexOutOfBoundsException("Inconsistency detected. Invalid view holder adapter position" + dr0VarMo211035a4 + recyclerView.m210365c5());
                    }
                    if (ar0Var.f45602a6 || recyclerView.f45264b0.mo212978a2(dr0VarMo211035a4.f55851a2) == dr0VarMo211035a4.f55854a5) {
                        gq0 gq0Var = recyclerView.f45264b0;
                        z6 = (!gq0Var.f56550a1 || dr0VarMo211035a4.f55853a4 == gq0Var.mo211033a1(dr0VarMo211035a4.f55851a2)) ? z2 : false;
                    }
                }
                if (z6) {
                    z = z2;
                } else {
                    dr0VarMo211035a4.m212620a0(4);
                    if (dr0VarMo211035a4.m212628a8()) {
                        recyclerView.removeDetachedView(dr0VarMo211035a4.f55849a0, false);
                        dr0VarMo211035a4.f55862b3.m214947a9(dr0VarMo211035a4);
                    } else if (dr0VarMo211035a4.m212635b5()) {
                        dr0VarMo211035a4.f55858a9 &= -33;
                    }
                    m214944a6(dr0VarMo211035a4);
                    dr0VarMo211035a4 = null;
                }
            }
        } else {
            z2 = true;
        }
        if (dr0VarMo211035a4 == null) {
            int iM214342a42 = recyclerView.f45257a3.m214342a4(i, 0);
            if (iM214342a42 >= 0) {
                j2 = 3;
                if (iM214342a42 < recyclerView.f45264b0.mo211032a0()) {
                    int iMo212978a2 = recyclerView.f45264b0.mo212978a2(iM214342a42);
                    gq0 gq0Var2 = recyclerView.f45264b0;
                    j3 = 4;
                    if (gq0Var2.f56550a1) {
                        long jMo211033a12 = gq0Var2.mo211033a1(iM214342a42);
                        int size5 = arrayList2.size() - 1;
                        while (true) {
                            if (size5 >= 0) {
                                dr0 dr0Var5 = (dr0) arrayList2.get(size5);
                                i3 = iM214342a42;
                                long j4 = dr0Var5.f55853a4;
                                View view2 = dr0Var5.f55849a0;
                                if (j4 == jMo211033a12 && !dr0Var5.m212635b5()) {
                                    if (iMo212978a2 == dr0Var5.f55854a5) {
                                        dr0Var5.m212620a0(32);
                                        if (dr0Var5.m212627a7() && !ar0Var.f45602a6) {
                                            dr0Var5.f55858a9 = (dr0Var5.f55858a9 & (-15)) | 2;
                                        }
                                        dr0VarMo211035a4 = dr0Var5;
                                    } else {
                                        arrayList2.remove(size5);
                                        recyclerView.removeDetachedView(view2, false);
                                        dr0 dr0VarM210345d53 = RecyclerView.m210345d5(view2);
                                        dr0VarM210345d53.f55862b3 = null;
                                        dr0VarM210345d53.f55863b4 = false;
                                        dr0VarM210345d53.f55858a9 &= -33;
                                        m214944a6(dr0VarM210345d53);
                                    }
                                }
                                size5--;
                                iM214342a42 = i3;
                            } else {
                                i3 = iM214342a42;
                                int size6 = arrayList3.size() - 1;
                                while (true) {
                                    if (size6 < 0) {
                                        break;
                                    }
                                    dr0 dr0Var6 = (dr0) arrayList3.get(size6);
                                    if (dr0Var6.f55853a4 != jMo211033a12 || dr0Var6.m212623a3()) {
                                        size6--;
                                    } else if (iMo212978a2 == dr0Var6.f55854a5) {
                                        arrayList3.remove(size6);
                                        dr0VarMo211035a4 = dr0Var6;
                                    } else {
                                        m214942a4(size6);
                                    }
                                }
                                dr0VarMo211035a4 = null;
                            }
                        }
                        if (dr0VarMo211035a4 != null) {
                            dr0VarMo211035a4.f55851a2 = i3;
                            z = z2;
                        }
                    }
                    if (dr0VarMo211035a4 == null) {
                        tq0 tq0Var = (tq0) m214940a2().f60504a0.get(iMo212978a2);
                        if (tq0Var != null) {
                            ArrayList arrayList5 = tq0Var.f60249a0;
                            if (arrayList5.isEmpty()) {
                                dr0Var = null;
                                if (dr0Var != null) {
                                }
                                dr0VarMo211035a4 = dr0Var;
                            } else {
                                for (int size7 = arrayList5.size() - 1; size7 >= 0; size7--) {
                                    if (!((dr0) arrayList5.get(size7)).m212623a3()) {
                                        dr0Var = (dr0) arrayList5.remove(size7);
                                        break;
                                    }
                                }
                                dr0Var = null;
                                if (dr0Var != null) {
                                    dr0Var.m212632b2();
                                    int[] iArr = RecyclerView.f45251g8;
                                }
                                dr0VarMo211035a4 = dr0Var;
                            }
                        }
                        View view3 = dr0VarMo211035a4.f55849a0;
                        if (z && !ar0Var.f45602a6) {
                            i2 = dr0VarMo211035a4.f55858a9;
                            if ((i2 & Segment.SIZE) == 0 ? z2 : false) {
                                dr0VarMo211035a4.f55858a9 = i2 & (-8193);
                                if (ar0Var.f45605a9) {
                                    lq0.m213914a1(dr0VarMo211035a4);
                                    lq0 lq0Var = recyclerView.f45288d4;
                                    dr0VarMo211035a4.m212622a2();
                                    lq0Var.getClass();
                                    fj0 fj0Var = new fj0();
                                    fj0Var.m212823a0(dr0VarMo211035a4);
                                    recyclerView.m210385e7(dr0VarMo211035a4, fj0Var);
                                }
                            }
                        }
                        if (!ar0Var.f45602a6 || !dr0VarMo211035a4.m212624a4()) {
                            if (dr0VarMo211035a4.m212624a4()) {
                                if (((dr0VarMo211035a4.f55858a9 & 2) != 0 ? z2 : false) || dr0VarMo211035a4.m212625a5()) {
                                }
                                layoutParams = view3.getLayoutParams();
                                if (layoutParams == null) {
                                    qq0Var = (qq0) recyclerView.generateDefaultLayoutParams();
                                    view3.setLayoutParams(qq0Var);
                                } else if (recyclerView.checkLayoutParams(layoutParams)) {
                                    qq0Var = (qq0) layoutParams;
                                } else {
                                    qq0Var = (qq0) recyclerView.generateLayoutParams(layoutParams);
                                    view3.setLayoutParams(qq0Var);
                                }
                                qq0Var.f59544a0 = dr0VarMo211035a4;
                                if (z && z5) {
                                    z3 = z4;
                                }
                                qq0Var.f59547a3 = z3;
                                return dr0VarMo211035a4;
                            }
                            z3 = false;
                            int iM214342a43 = recyclerView.f45257a3.m214342a4(i, 0);
                            dr0VarMo211035a4.f55866b7 = recyclerView;
                            int i10 = dr0VarMo211035a4.f55854a5;
                            long nanoTime = recyclerView.getNanoTime();
                            if (j == Long.MAX_VALUE) {
                                long j5 = this.f60673a6.m214860a0(i10).f60252a3;
                                if (j5 == 0 || j5 + nanoTime < j) {
                                    gq0 gq0Var3 = recyclerView.f45264b0;
                                    gq0Var3.getClass();
                                    dr0VarMo211035a4.f55851a2 = iM214342a43;
                                    if (gq0Var3.f56550a1) {
                                        dr0VarMo211035a4.f55853a4 = gq0Var3.mo211033a1(iM214342a43);
                                    }
                                    dr0VarMo211035a4.f55858a9 = (dr0VarMo211035a4.f55858a9 & (-520)) | 1;
                                    int i11 = o71.f58750a0;
                                    n71.m214052a0("RV OnBindView");
                                    dr0VarMo211035a4.m212622a2();
                                    gq0Var3.mo211034a3(dr0VarMo211035a4, iM214342a43);
                                    ArrayList arrayList6 = dr0VarMo211035a4.f55859b0;
                                    if (arrayList6 != null) {
                                        arrayList6.clear();
                                    }
                                    dr0VarMo211035a4.f55858a9 &= -1025;
                                    ViewGroup.LayoutParams layoutParams2 = view3.getLayoutParams();
                                    if (layoutParams2 instanceof qq0) {
                                        ((qq0) layoutParams2).f59546a2 = z2;
                                    }
                                    n71.m214053a1();
                                    long nanoTime2 = recyclerView.getNanoTime() - nanoTime;
                                    tq0 tq0VarM214860a0 = this.f60673a6.m214860a0(dr0VarMo211035a4.f55854a5);
                                    long j6 = tq0VarM214860a0.f60252a3;
                                    if (j6 != 0) {
                                        nanoTime2 = (nanoTime2 / j3) + ((j6 / j3) * j2);
                                    }
                                    tq0VarM214860a0.f60252a3 = nanoTime2;
                                    AccessibilityManager accessibilityManager = recyclerView.f45277c3;
                                    if (accessibilityManager != null && accessibilityManager.isEnabled()) {
                                        WeakHashMap weakHashMap = xa1.f61054a0;
                                        z4 = true;
                                        if (fa1.m212765a2(view3) == 0) {
                                            fa1.m212781b8(view3, 1);
                                        }
                                        er0 er0Var = recyclerView.f45313f9;
                                        if (er0Var != null) {
                                            C1293ua c1293ua = er0Var.f56102a4;
                                            if (c1293ua != null) {
                                                View.AccessibilityDelegate accessibilityDelegateM215141a3 = xa1.m215141a3(view3);
                                                C0608i4 c0608i42 = accessibilityDelegateM215141a3 == null ? c0608i4 : accessibilityDelegateM215141a3 instanceof C0606i2 ? ((C0606i2) accessibilityDelegateM215141a3).f56784a0 : new C0608i4(accessibilityDelegateM215141a3);
                                                if (c0608i42 != null && c0608i42 != c1293ua) {
                                                    ((WeakHashMap) c1293ua.f60358a5).put(view3, c0608i42);
                                                }
                                            }
                                            xa1.m215152b4(view3, c1293ua);
                                        }
                                    } else {
                                        z4 = true;
                                    }
                                    if (ar0Var.f45602a6) {
                                        dr0VarMo211035a4.f55855a6 = i;
                                    }
                                    z5 = z4;
                                } else {
                                    z5 = false;
                                    z4 = z2;
                                }
                            }
                            layoutParams = view3.getLayoutParams();
                            if (layoutParams == null) {
                            }
                            qq0Var.f59544a0 = dr0VarMo211035a4;
                            if (z) {
                                z3 = z4;
                            }
                            qq0Var.f59547a3 = z3;
                            return dr0VarMo211035a4;
                        }
                        dr0VarMo211035a4.f55855a6 = i;
                        z4 = z2;
                        z5 = false;
                        z3 = false;
                        layoutParams = view3.getLayoutParams();
                        if (layoutParams == null) {
                        }
                        qq0Var.f59544a0 = dr0VarMo211035a4;
                        if (z) {
                        }
                        qq0Var.f59547a3 = z3;
                        return dr0VarMo211035a4;
                    }
                    if (dr0VarMo211035a4 == null) {
                        long nanoTime3 = recyclerView.getNanoTime();
                        if (j != Long.MAX_VALUE) {
                            long j7 = this.f60673a6.m214860a0(iMo212978a2).f60251a2;
                            if (!((j7 == 0 || j7 + nanoTime3 < j) ? z2 : false)) {
                                return null;
                            }
                        }
                        c0608i4 = null;
                        gq0 gq0Var4 = recyclerView.f45264b0;
                        gq0Var4.getClass();
                        try {
                            int i12 = o71.f58750a0;
                            n71.m214052a0("RV CreateView");
                            dr0VarMo211035a4 = gq0Var4.mo211035a4(recyclerView, iMo212978a2);
                            if (dr0VarMo211035a4.f55849a0.getParent() != null) {
                                throw new IllegalStateException("ViewHolder views must not be attached when created. Ensure that you are not passing 'true' to the attachToRoot parameter of LayoutInflater.inflate(..., boolean attachToRoot)");
                            }
                            dr0VarMo211035a4.f55854a5 = iMo212978a2;
                            n71.m214053a1();
                            int[] iArr2 = RecyclerView.f45251g8;
                            RecyclerView recyclerViewM210344d0 = RecyclerView.m210344d0(dr0VarMo211035a4.f55849a0);
                            if (recyclerViewM210344d0 != null) {
                                dr0VarMo211035a4.f55850a1 = new WeakReference(recyclerViewM210344d0);
                            }
                            long nanoTime4 = recyclerView.getNanoTime() - nanoTime3;
                            tq0 tq0VarM214860a02 = this.f60673a6.m214860a0(iMo212978a2);
                            long j8 = tq0VarM214860a02.f60251a2;
                            if (j8 != 0) {
                                nanoTime4 = (nanoTime4 / 4) + ((j8 / 4) * 3);
                            }
                            tq0VarM214860a02.f60251a2 = nanoTime4;
                        } catch (Throwable th) {
                            int i13 = o71.f58750a0;
                            n71.m214053a1();
                            throw th;
                        }
                    }
                    View view32 = dr0VarMo211035a4.f55849a0;
                    if (z) {
                        i2 = dr0VarMo211035a4.f55858a9;
                        if ((i2 & Segment.SIZE) == 0 ? z2 : false) {
                        }
                    }
                    if (!ar0Var.f45602a6) {
                        if (dr0VarMo211035a4.m212624a4()) {
                        }
                        z3 = false;
                        int iM214342a432 = recyclerView.f45257a3.m214342a4(i, 0);
                        dr0VarMo211035a4.f55866b7 = recyclerView;
                        int i102 = dr0VarMo211035a4.f55854a5;
                        long nanoTime5 = recyclerView.getNanoTime();
                        if (j == Long.MAX_VALUE) {
                        }
                    }
                    layoutParams = view32.getLayoutParams();
                    if (layoutParams == null) {
                    }
                    qq0Var.f59544a0 = dr0VarMo211035a4;
                    if (z) {
                    }
                    qq0Var.f59547a3 = z3;
                    return dr0VarMo211035a4;
                }
            }
            StringBuilder sbM38b92 = AbstractC0003a2.m38b9("Inconsistency detected. Invalid item position ", i, "(offset:", iM214342a42, ").state:");
            sbM38b92.append(ar0Var.m210500a1());
            sbM38b92.append(recyclerView.m210365c5());
            throw new IndexOutOfBoundsException(sbM38b92.toString());
        }
        j2 = 3;
        j3 = 4;
        c0608i4 = null;
        View view322 = dr0VarMo211035a4.f55849a0;
        if (z) {
        }
        if (!ar0Var.f45602a6) {
        }
        layoutParams = view322.getLayoutParams();
        if (layoutParams == null) {
        }
        qq0Var.f59544a0 = dr0VarMo211035a4;
        if (z) {
        }
        qq0Var.f59547a3 = z3;
        return dr0VarMo211035a4;
    }

    /* renamed from: a9 */
    public final void m214947a9(dr0 dr0Var) {
        if (dr0Var.f55863b4) {
            this.f60668a1.remove(dr0Var);
        } else {
            this.f60667a0.remove(dr0Var);
        }
        dr0Var.f55862b3 = null;
        dr0Var.f55863b4 = false;
        dr0Var.f55858a9 &= -33;
    }

    /* renamed from: b0 */
    public final void m214948b0() {
        pq0 pq0Var = this.f60674a7.f45265b1;
        this.f60672a5 = this.f60671a4 + (pq0Var != null ? pq0Var.f59327a9 : 0);
        ArrayList arrayList = this.f60669a2;
        for (int size = arrayList.size() - 1; size >= 0 && arrayList.size() > this.f60672a5; size--) {
            m214942a4(size);
        }
    }
}
