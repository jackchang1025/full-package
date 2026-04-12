package p000;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import okio.Segment;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class fq0 {

    /* renamed from: a0 */
    public final /* synthetic */ RecyclerView f56313a0;

    public /* synthetic */ fq0(RecyclerView recyclerView) {
        this.f56313a0 = recyclerView;
    }

    /* renamed from: a0 */
    public void m212846a0(C1093q1 c1093q1) {
        int i = c1093q1.f59354a0;
        RecyclerView recyclerView = this.f56313a0;
        if (i == 1) {
            recyclerView.f45265b1.mo210271e5(c1093q1.f59355a1, c1093q1.f59356a2);
            return;
        }
        if (i == 2) {
            recyclerView.f45265b1.mo210274e8(c1093q1.f59355a1, c1093q1.f59356a2);
        } else if (i == 4) {
            recyclerView.f45265b1.mo210275e9(c1093q1.f59355a1, c1093q1.f59356a2);
        } else {
            if (i != 8) {
                return;
            }
            recyclerView.f45265b1.mo210273e7(c1093q1.f59355a1, c1093q1.f59356a2);
        }
    }

    /* renamed from: a1 */
    public dr0 m212847a1(int i) {
        RecyclerView recyclerView = this.f56313a0;
        int iM214283c8 = recyclerView.f45258a4.m214283c8();
        int i2 = 0;
        dr0 dr0Var = null;
        while (true) {
            if (i2 >= iM214283c8) {
                break;
            }
            dr0 dr0VarM210345d5 = RecyclerView.m210345d5(recyclerView.f45258a4.m214282c7(i2));
            if (dr0VarM210345d5 != null && !dr0VarM210345d5.m212627a7() && dr0VarM210345d5.f55851a2 == i) {
                if (!((ArrayList) recyclerView.f45258a4.f59231a3).contains(dr0VarM210345d5.f55849a0)) {
                    dr0Var = dr0VarM210345d5;
                    break;
                }
                dr0Var = dr0VarM210345d5;
            }
            i2++;
        }
        if (dr0Var != null) {
            if (!((ArrayList) recyclerView.f45258a4.f59231a3).contains(dr0Var.f55849a0)) {
                return dr0Var;
            }
        }
        return null;
    }

    /* renamed from: a2 */
    public void m212848a2(int i, int i2) {
        int i3;
        int i4;
        RecyclerView recyclerView = this.f56313a0;
        int iM214283c8 = recyclerView.f45258a4.m214283c8();
        int i5 = i2 + i;
        for (int i6 = 0; i6 < iM214283c8; i6++) {
            View viewM214282c7 = recyclerView.f45258a4.m214282c7(i6);
            dr0 dr0VarM210345d5 = RecyclerView.m210345d5(viewM214282c7);
            if (dr0VarM210345d5 != null && !dr0VarM210345d5.m212634b4() && (i4 = dr0VarM210345d5.f55851a2) >= i && i4 < i5) {
                dr0VarM210345d5.m212620a0(2);
                dr0VarM210345d5.m212620a0(Segment.SHARE_MINIMUM);
                ((qq0) viewM214282c7.getLayoutParams()).f59546a2 = true;
            }
        }
        vq0 vq0Var = recyclerView.f45255a1;
        ArrayList arrayList = vq0Var.f60669a2;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            dr0 dr0Var = (dr0) arrayList.get(size);
            if (dr0Var != null && (i3 = dr0Var.f55851a2) >= i && i3 < i5) {
                dr0Var.m212620a0(2);
                vq0Var.m214942a4(size);
            }
        }
        recyclerView.f45310f6 = true;
    }

    /* renamed from: a3 */
    public void m212849a3(int i, int i2) {
        RecyclerView recyclerView = this.f56313a0;
        int iM214283c8 = recyclerView.f45258a4.m214283c8();
        for (int i3 = 0; i3 < iM214283c8; i3++) {
            dr0 dr0VarM210345d5 = RecyclerView.m210345d5(recyclerView.f45258a4.m214282c7(i3));
            if (dr0VarM210345d5 != null && !dr0VarM210345d5.m212634b4() && dr0VarM210345d5.f55851a2 >= i) {
                dr0VarM210345d5.m212631b1(i2, false);
                recyclerView.f45306f2.f45601a5 = true;
            }
        }
        ArrayList arrayList = recyclerView.f45255a1.f60669a2;
        int size = arrayList.size();
        for (int i4 = 0; i4 < size; i4++) {
            dr0 dr0Var = (dr0) arrayList.get(i4);
            if (dr0Var != null && dr0Var.f55851a2 >= i) {
                dr0Var.m212631b1(i2, true);
            }
        }
        recyclerView.requestLayout();
        recyclerView.f45309f5 = true;
    }

    /* renamed from: a4 */
    public void m212850a4(int i, int i2) {
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        RecyclerView recyclerView = this.f56313a0;
        int iM214283c8 = recyclerView.f45258a4.m214283c8();
        int i10 = -1;
        if (i < i2) {
            i4 = i;
            i3 = i2;
            i5 = -1;
        } else {
            i3 = i;
            i4 = i2;
            i5 = 1;
        }
        for (int i11 = 0; i11 < iM214283c8; i11++) {
            dr0 dr0VarM210345d5 = RecyclerView.m210345d5(recyclerView.f45258a4.m214282c7(i11));
            if (dr0VarM210345d5 != null && (i9 = dr0VarM210345d5.f55851a2) >= i4 && i9 <= i3) {
                if (i9 == i) {
                    dr0VarM210345d5.m212631b1(i2 - i, false);
                } else {
                    dr0VarM210345d5.m212631b1(i5, false);
                }
                recyclerView.f45306f2.f45601a5 = true;
            }
        }
        ArrayList arrayList = recyclerView.f45255a1.f60669a2;
        if (i < i2) {
            i7 = i;
            i6 = i2;
        } else {
            i6 = i;
            i7 = i2;
            i10 = 1;
        }
        int size = arrayList.size();
        for (int i12 = 0; i12 < size; i12++) {
            dr0 dr0Var = (dr0) arrayList.get(i12);
            if (dr0Var != null && (i8 = dr0Var.f55851a2) >= i7 && i8 <= i6) {
                if (i8 == i) {
                    dr0Var.m212631b1(i2 - i, false);
                } else {
                    dr0Var.m212631b1(i10, false);
                }
            }
        }
        recyclerView.requestLayout();
        recyclerView.f45309f5 = true;
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0020  */
    /* renamed from: a5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void m212851a5(dr0 dr0Var, fj0 fj0Var, fj0 fj0Var2) {
        boolean zM214528a6;
        RecyclerView recyclerView = this.f56313a0;
        recyclerView.getClass();
        dr0Var.m212633b3(false);
        C1176rd c1176rd = (C1176rd) recyclerView.f45288d4;
        if (fj0Var != null) {
            c1176rd.getClass();
            int i = fj0Var.f56279a0;
            int i2 = fj0Var2.f56279a0;
            if (i == i2 && fj0Var.f56280a1 == fj0Var2.f56280a1) {
                c1176rd.m214532b1(dr0Var);
                dr0Var.f55849a0.setAlpha(0.0f);
                c1176rd.f59676a8.add(dr0Var);
                zM214528a6 = true;
            } else {
                zM214528a6 = c1176rd.m214528a6(dr0Var, i, fj0Var.f56280a1, i2, fj0Var2.f56280a1);
            }
        }
        if (zM214528a6) {
            recyclerView.m210383e5();
        }
    }

    /* renamed from: a6 */
    public void m212852a6(dr0 dr0Var, fj0 fj0Var, fj0 fj0Var2) {
        boolean zM214528a6;
        RecyclerView recyclerView = this.f56313a0;
        recyclerView.f45255a1.m214947a9(dr0Var);
        recyclerView.m210346a5(dr0Var);
        dr0Var.m212633b3(false);
        C1176rd c1176rd = (C1176rd) recyclerView.f45288d4;
        c1176rd.getClass();
        int i = fj0Var.f56279a0;
        int i2 = fj0Var.f56280a1;
        View view = dr0Var.f55849a0;
        int left = fj0Var2 == null ? view.getLeft() : fj0Var2.f56279a0;
        int top = fj0Var2 == null ? view.getTop() : fj0Var2.f56280a1;
        if (dr0Var.m212627a7() || (i == left && i2 == top)) {
            c1176rd.m214532b1(dr0Var);
            c1176rd.f59675a7.add(dr0Var);
            zM214528a6 = true;
        } else {
            view.layout(left, top, view.getWidth() + left, view.getHeight() + top);
            zM214528a6 = c1176rd.m214528a6(dr0Var, i, i2, left, top);
        }
        if (zM214528a6) {
            recyclerView.m210383e5();
        }
    }

    /* renamed from: a7 */
    public void m212853a7(int i) {
        RecyclerView recyclerView = this.f56313a0;
        View childAt = recyclerView.getChildAt(i);
        if (childAt != null) {
            recyclerView.m210354b4(childAt);
            childAt.clearAnimation();
        }
        recyclerView.removeViewAt(i);
    }
}
