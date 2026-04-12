package p000;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.recyclerview.R$styleable;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.WeakHashMap;
import okio.Segment;
import okio.internal.Buffer;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class pq0 {

    /* renamed from: a0 */
    public pg1 f59318a0;

    /* renamed from: a1 */
    public RecyclerView f59319a1;

    /* renamed from: a2 */
    public final C1217sc f59320a2;

    /* renamed from: a3 */
    public final C1217sc f59321a3;

    /* renamed from: a4 */
    public za0 f59322a4;

    /* renamed from: a5 */
    public boolean f59323a5;

    /* renamed from: a6 */
    public boolean f59324a6;

    /* renamed from: a7 */
    public final boolean f59325a7;

    /* renamed from: a8 */
    public final boolean f59326a8;

    /* renamed from: a9 */
    public int f59327a9;

    /* renamed from: b0 */
    public boolean f59328b0;

    /* renamed from: b1 */
    public int f59329b1;

    /* renamed from: b2 */
    public int f59330b2;

    /* renamed from: b3 */
    public int f59331b3;

    /* renamed from: b4 */
    public int f59332b4;

    public pq0() {
        nq0 nq0Var = new nq0(this, 0);
        nq0 nq0Var2 = new nq0(this, 1);
        this.f59320a2 = new C1217sc(nq0Var);
        this.f59321a3 = new C1217sc(nq0Var2);
        this.f59323a5 = false;
        this.f59324a6 = false;
        this.f59325a7 = true;
        this.f59326a8 = true;
    }

    /* renamed from: a6 */
    public static int m214302a6(int i, int i2, int i3) {
        int mode = View.MeasureSpec.getMode(i);
        int size = View.MeasureSpec.getSize(i);
        return mode != Integer.MIN_VALUE ? mode != 1073741824 ? Math.max(i2, i3) : size : Math.min(size, Math.max(i2, i3));
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x001a  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0022  */
    /* renamed from: c2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int m214303c2(int i, int i2, int i3, int i4, boolean z) {
        int iMax = Math.max(0, i - i3);
        if (z) {
            if (i4 < 0) {
                if (i4 != -1 || (i2 != Integer.MIN_VALUE && (i2 == 0 || i2 != 1073741824))) {
                    i2 = 0;
                    i4 = 0;
                } else {
                    i4 = iMax;
                }
            }
            i2 = 1073741824;
        } else if (i4 >= 0) {
            i2 = 1073741824;
        } else if (i4 != -1) {
            if (i4 == -2) {
                if (i2 == Integer.MIN_VALUE || i2 == 1073741824) {
                    i4 = iMax;
                    i2 = Integer.MIN_VALUE;
                } else {
                    i4 = iMax;
                    i2 = 0;
                }
            }
        }
        return View.MeasureSpec.makeMeasureSpec(i4, i2);
    }

    /* renamed from: d0 */
    public static int m214304d0(View view) {
        return ((qq0) view.getLayoutParams()).f59544a0.m212621a1();
    }

    /* renamed from: d1 */
    public static oq0 m214305d1(Context context, AttributeSet attributeSet, int i, int i2) {
        oq0 oq0Var = new oq0();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.RecyclerView, i, i2);
        oq0Var.f58908a0 = typedArrayObtainStyledAttributes.getInt(R$styleable.RecyclerView_android_orientation, 1);
        oq0Var.f58909a1 = typedArrayObtainStyledAttributes.getInt(R$styleable.RecyclerView_spanCount, 1);
        oq0Var.f58910a2 = typedArrayObtainStyledAttributes.getBoolean(R$styleable.RecyclerView_reverseLayout, false);
        oq0Var.f58911a3 = typedArrayObtainStyledAttributes.getBoolean(R$styleable.RecyclerView_stackFromEnd, false);
        typedArrayObtainStyledAttributes.recycle();
        return oq0Var;
    }

    /* renamed from: d5 */
    public static boolean m214306d5(int i, int i2, int i3) {
        int mode = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i2);
        if (i3 > 0 && i != i3) {
            return false;
        }
        if (mode == Integer.MIN_VALUE) {
            return size >= i;
        }
        if (mode != 0) {
            return mode == 1073741824 && size == i;
        }
        return true;
    }

    /* renamed from: d6 */
    public static void m214307d6(View view, int i, int i2, int i3, int i4) {
        qq0 qq0Var = (qq0) view.getLayoutParams();
        Rect rect = qq0Var.f59545a1;
        view.layout(i + rect.left + ((ViewGroup.MarginLayoutParams) qq0Var).leftMargin, i2 + rect.top + ((ViewGroup.MarginLayoutParams) qq0Var).topMargin, (i3 - rect.right) - ((ViewGroup.MarginLayoutParams) qq0Var).rightMargin, (i4 - rect.bottom) - ((ViewGroup.MarginLayoutParams) qq0Var).bottomMargin);
    }

    /* renamed from: a1 */
    public final void m214308a1(View view, int i, boolean z) {
        dr0 dr0VarM210345d5 = RecyclerView.m210345d5(view);
        if (z || dr0VarM210345d5.m212627a7()) {
            t01 t01Var = (t01) this.f59319a1.f45259a5.f56088a1;
            hb1 hb1VarM213018a0 = (hb1) t01Var.getOrDefault(dr0VarM210345d5, null);
            if (hb1VarM213018a0 == null) {
                hb1VarM213018a0 = hb1.m213018a0();
                t01Var.put(dr0VarM210345d5, hb1VarM213018a0);
            }
            hb1VarM213018a0.f56643a0 |= 1;
        } else {
            this.f59319a1.f45259a5.m212718c2(dr0VarM210345d5);
        }
        qq0 qq0Var = (qq0) view.getLayoutParams();
        if (dr0VarM210345d5.m212635b5() || dr0VarM210345d5.m212628a8()) {
            if (dr0VarM210345d5.m212628a8()) {
                dr0VarM210345d5.f55862b3.m214947a9(dr0VarM210345d5);
            } else {
                dr0VarM210345d5.f55858a9 &= -33;
            }
            this.f59318a0.m214267b1(view, i, view.getLayoutParams(), false);
        } else {
            if (view.getParent() == this.f59319a1) {
                pg1 pg1Var = this.f59318a0;
                C0583hj c0583hj = (C0583hj) pg1Var.f59230a2;
                int iIndexOfChild = ((fq0) pg1Var.f59229a1).f56313a0.indexOfChild(view);
                int iM213041a1 = (iIndexOfChild == -1 || c0583hj.m213043a3(iIndexOfChild)) ? -1 : iIndexOfChild - c0583hj.m213041a1(iIndexOfChild);
                if (i == -1) {
                    i = this.f59318a0.m214275b9();
                }
                if (iM213041a1 == -1) {
                    throw new IllegalStateException("Added View has RecyclerView as parent but view is not a real child. Unfiltered index:" + this.f59319a1.indexOfChild(view) + this.f59319a1.m210365c5());
                }
                if (iM213041a1 != i) {
                    pq0 pq0Var = this.f59319a1.f45265b1;
                    View viewM214310c0 = pq0Var.m214310c0(iM213041a1);
                    if (viewM214310c0 == null) {
                        throw new IllegalArgumentException("Cannot move a child from non-existing index:" + iM213041a1 + pq0Var.f59319a1.toString());
                    }
                    pq0Var.m214310c0(iM213041a1);
                    pq0Var.f59318a0.m214269b3(iM213041a1);
                    qq0 qq0Var2 = (qq0) viewM214310c0.getLayoutParams();
                    dr0 dr0VarM210345d52 = RecyclerView.m210345d5(viewM214310c0);
                    if (dr0VarM210345d52.m212627a7()) {
                        t01 t01Var2 = (t01) pq0Var.f59319a1.f45259a5.f56088a1;
                        hb1 hb1VarM213018a02 = (hb1) t01Var2.getOrDefault(dr0VarM210345d52, null);
                        if (hb1VarM213018a02 == null) {
                            hb1VarM213018a02 = hb1.m213018a0();
                            t01Var2.put(dr0VarM210345d52, hb1VarM213018a02);
                        }
                        hb1VarM213018a02.f56643a0 = 1 | hb1VarM213018a02.f56643a0;
                    } else {
                        pq0Var.f59319a1.f45259a5.m212718c2(dr0VarM210345d52);
                    }
                    pq0Var.f59318a0.m214267b1(viewM214310c0, i, qq0Var2, dr0VarM210345d52.m212627a7());
                }
            } else {
                this.f59318a0.m214266b0(view, i, false);
                qq0Var.f59546a2 = true;
                za0 za0Var = this.f59322a4;
                if (za0Var != null && za0Var.f61476a4) {
                    za0Var.f61473a1.getClass();
                    dr0 dr0VarM210345d53 = RecyclerView.m210345d5(view);
                    if ((dr0VarM210345d53 != null ? dr0VarM210345d53.m212621a1() : -1) == za0Var.f61472a0) {
                        za0Var.f61477a5 = view;
                    }
                }
            }
        }
        if (qq0Var.f59547a3) {
            dr0VarM210345d5.f55849a0.invalidate();
            qq0Var.f59547a3 = false;
        }
    }

    /* renamed from: a2 */
    public void mo210297a2(String str) {
        RecyclerView recyclerView = this.f59319a1;
        if (recyclerView != null) {
            recyclerView.m210349a8(str);
        }
    }

    /* renamed from: a3 */
    public abstract boolean mo210298a3();

    /* renamed from: a4 */
    public boolean mo210299a4() {
        return false;
    }

    /* renamed from: a5 */
    public boolean mo210259a5(qq0 qq0Var) {
        return qq0Var != null;
    }

    /* renamed from: a9 */
    public abstract int mo210302a9(ar0 ar0Var);

    /* renamed from: b0 */
    public abstract int mo210260b0(ar0 ar0Var);

    /* renamed from: b1 */
    public abstract int mo210261b1(ar0 ar0Var);

    /* renamed from: b2 */
    public int mo210303b2(ar0 ar0Var) {
        return 0;
    }

    /* renamed from: b3 */
    public int mo210262b3(ar0 ar0Var) {
        return 0;
    }

    /* renamed from: b4 */
    public int mo210263b4(ar0 ar0Var) {
        return 0;
    }

    /* renamed from: b5 */
    public final void m214309b5(vq0 vq0Var) {
        for (int iM214311c1 = m214311c1() - 1; iM214311c1 >= 0; iM214311c1--) {
            View viewM214310c0 = m214310c0(iM214311c1);
            dr0 dr0VarM210345d5 = RecyclerView.m210345d5(viewM214310c0);
            if (!dr0VarM210345d5.m212634b4()) {
                if (!dr0VarM210345d5.m212625a5() || dr0VarM210345d5.m212627a7() || this.f59319a1.f45264b0.f56550a1) {
                    m214310c0(iM214311c1);
                    this.f59318a0.m214269b3(iM214311c1);
                    vq0Var.m214945a7(viewM214310c0);
                    this.f59319a1.f45259a5.m212718c2(dr0VarM210345d5);
                } else {
                    m214322f9(iM214311c1);
                    vq0Var.m214944a6(dr0VarM210345d5);
                }
            }
        }
    }

    /* renamed from: b6 */
    public View mo210304b6(int i) {
        int iM214311c1 = m214311c1();
        for (int i2 = 0; i2 < iM214311c1; i2++) {
            View viewM214310c0 = m214310c0(i2);
            dr0 dr0VarM210345d5 = RecyclerView.m210345d5(viewM214310c0);
            if (dr0VarM210345d5 != null && dr0VarM210345d5.m212621a1() == i && !dr0VarM210345d5.m212634b4() && (this.f59319a1.f45306f2.f45602a6 || !dr0VarM210345d5.m212627a7())) {
                return viewM214310c0;
            }
        }
        return null;
    }

    /* renamed from: b7 */
    public abstract qq0 mo210264b7();

    /* renamed from: b8 */
    public qq0 mo210265b8(Context context, AttributeSet attributeSet) {
        return new qq0(context, attributeSet);
    }

    /* renamed from: b9 */
    public qq0 mo210266b9(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof qq0 ? new qq0((qq0) layoutParams) : layoutParams instanceof ViewGroup.MarginLayoutParams ? new qq0((ViewGroup.MarginLayoutParams) layoutParams) : new qq0(layoutParams);
    }

    /* renamed from: c0 */
    public final View m214310c0(int i) {
        pg1 pg1Var = this.f59318a0;
        if (pg1Var != null) {
            return pg1Var.m214274b8(i);
        }
        return null;
    }

    /* renamed from: c1 */
    public final int m214311c1() {
        pg1 pg1Var = this.f59318a0;
        if (pg1Var != null) {
            return pg1Var.m214275b9();
        }
        return 0;
    }

    /* renamed from: c3 */
    public int mo210267c3(vq0 vq0Var, ar0 ar0Var) {
        RecyclerView recyclerView = this.f59319a1;
        if (recyclerView == null || recyclerView.f45264b0 == null || !mo210298a3()) {
            return 1;
        }
        return this.f59319a1.f45264b0.mo211032a0();
    }

    /* renamed from: c4 */
    public void mo210969c4(View view, Rect rect) {
        int[] iArr = RecyclerView.f45251g8;
        qq0 qq0Var = (qq0) view.getLayoutParams();
        Rect rect2 = qq0Var.f59545a1;
        rect.set((view.getLeft() - rect2.left) - ((ViewGroup.MarginLayoutParams) qq0Var).leftMargin, (view.getTop() - rect2.top) - ((ViewGroup.MarginLayoutParams) qq0Var).topMargin, view.getRight() + rect2.right + ((ViewGroup.MarginLayoutParams) qq0Var).rightMargin, view.getBottom() + rect2.bottom + ((ViewGroup.MarginLayoutParams) qq0Var).bottomMargin);
    }

    /* renamed from: c5 */
    public final int m214312c5() {
        RecyclerView recyclerView = this.f59319a1;
        WeakHashMap weakHashMap = xa1.f61054a0;
        return ga1.m212904a3(recyclerView);
    }

    /* renamed from: c6 */
    public final int m214313c6() {
        RecyclerView recyclerView = this.f59319a1;
        if (recyclerView != null) {
            return recyclerView.getPaddingBottom();
        }
        return 0;
    }

    /* renamed from: c7 */
    public final int m214314c7() {
        RecyclerView recyclerView = this.f59319a1;
        if (recyclerView != null) {
            return recyclerView.getPaddingLeft();
        }
        return 0;
    }

    /* renamed from: c8 */
    public final int m214315c8() {
        RecyclerView recyclerView = this.f59319a1;
        if (recyclerView != null) {
            return recyclerView.getPaddingRight();
        }
        return 0;
    }

    /* renamed from: c9 */
    public final int m214316c9() {
        RecyclerView recyclerView = this.f59319a1;
        if (recyclerView != null) {
            return recyclerView.getPaddingTop();
        }
        return 0;
    }

    /* renamed from: d2 */
    public int mo210268d2(vq0 vq0Var, ar0 ar0Var) {
        RecyclerView recyclerView = this.f59319a1;
        if (recyclerView == null || recyclerView.f45264b0 == null || !mo210299a4()) {
            return 1;
        }
        return this.f59319a1.f45264b0.mo211032a0();
    }

    /* renamed from: d3 */
    public final void m214317d3(View view, Rect rect) {
        Matrix matrix;
        Rect rect2 = ((qq0) view.getLayoutParams()).f59545a1;
        rect.set(-rect2.left, -rect2.top, view.getWidth() + rect2.right, view.getHeight() + rect2.bottom);
        if (this.f59319a1 != null && (matrix = view.getMatrix()) != null && !matrix.isIdentity()) {
            RectF rectF = this.f59319a1.f45263a9;
            rectF.set(rect);
            matrix.mapRect(rectF);
            rect.set((int) Math.floor(rectF.left), (int) Math.floor(rectF.top), (int) Math.ceil(rectF.right), (int) Math.ceil(rectF.bottom));
        }
        rect.offset(view.getLeft(), view.getTop());
    }

    /* renamed from: d4 */
    public boolean mo210305d4() {
        return false;
    }

    /* renamed from: d7 */
    public void mo210396d7(int i) {
        RecyclerView recyclerView = this.f59319a1;
        if (recyclerView != null) {
            int iM214275b9 = recyclerView.f45258a4.m214275b9();
            for (int i2 = 0; i2 < iM214275b9; i2++) {
                recyclerView.f45258a4.m214274b8(i2).offsetLeftAndRight(i);
            }
        }
    }

    /* renamed from: d8 */
    public void mo210397d8(int i) {
        RecyclerView recyclerView = this.f59319a1;
        if (recyclerView != null) {
            int iM214275b9 = recyclerView.f45258a4.m214275b9();
            for (int i2 = 0; i2 < iM214275b9; i2++) {
                recyclerView.f45258a4.m214274b8(i2).offsetTopAndBottom(i);
            }
        }
    }

    /* renamed from: e0 */
    public View mo210269e0(View view, int i, vq0 vq0Var, ar0 ar0Var) {
        return null;
    }

    /* renamed from: e1 */
    public void mo210307e1(AccessibilityEvent accessibilityEvent) {
        RecyclerView recyclerView = this.f59319a1;
        vq0 vq0Var = recyclerView.f45255a1;
        ar0 ar0Var = recyclerView.f45306f2;
        if (recyclerView == null || accessibilityEvent == null) {
            return;
        }
        boolean z = true;
        if (!recyclerView.canScrollVertically(1) && !this.f59319a1.canScrollVertically(-1) && !this.f59319a1.canScrollHorizontally(-1) && !this.f59319a1.canScrollHorizontally(1)) {
            z = false;
        }
        accessibilityEvent.setScrollable(z);
        gq0 gq0Var = this.f59319a1.f45264b0;
        if (gq0Var != null) {
            accessibilityEvent.setItemCount(gq0Var.mo211032a0());
        }
    }

    /* renamed from: e2 */
    public void mo212583e2(vq0 vq0Var, ar0 ar0Var, C0748k7 c0748k7) {
        if (this.f59319a1.canScrollVertically(-1) || this.f59319a1.canScrollHorizontally(-1)) {
            c0748k7.m213458a0(Segment.SIZE);
            c0748k7.m213468b1(true);
        }
        if (this.f59319a1.canScrollVertically(1) || this.f59319a1.canScrollHorizontally(1)) {
            c0748k7.m213458a0(Buffer.SEGMENTING_THRESHOLD);
            c0748k7.m213468b1(true);
        }
        c0748k7.f57472a0.setCollectionInfo((AccessibilityNodeInfo.CollectionInfo) C0747k6.m213450a0(mo210268d2(vq0Var, ar0Var), mo210267c3(vq0Var, ar0Var), 0).f57459a0);
    }

    /* renamed from: e3 */
    public void mo210270e3(vq0 vq0Var, ar0 ar0Var, View view, C0748k7 c0748k7) {
        c0748k7.m213465a8(C0747k6.m213451a1(mo210299a4() ? m214304d0(view) : 0, 1, mo210298a3() ? m214304d0(view) : 0, 1, false, false));
    }

    /* renamed from: e4 */
    public final void m214318e4(View view, C0748k7 c0748k7) {
        dr0 dr0VarM210345d5 = RecyclerView.m210345d5(view);
        if (dr0VarM210345d5 == null || dr0VarM210345d5.m212627a7()) {
            return;
        }
        pg1 pg1Var = this.f59318a0;
        if (((ArrayList) pg1Var.f59231a3).contains(dr0VarM210345d5.f55849a0)) {
            return;
        }
        RecyclerView recyclerView = this.f59319a1;
        mo210270e3(recyclerView.f45255a1, recyclerView.f45306f2, view, c0748k7);
    }

    /* renamed from: f0 */
    public abstract void mo210276f0(vq0 vq0Var, ar0 ar0Var);

    /* renamed from: f1 */
    public abstract void mo210277f1(ar0 ar0Var);

    /* renamed from: f3 */
    public Parcelable mo210309f3() {
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x003f A[PHI: r2
      0x003f: PHI (r2v8 int) = (r2v4 int), (r2v12 int) binds: [B:23:0x005b, B:15:0x002f] A[DONT_GENERATE, DONT_INLINE]] */
    /* renamed from: f5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean mo212584f5(vq0 vq0Var, ar0 ar0Var, int i, Bundle bundle) {
        int iM214316c9;
        int iM214314c7;
        RecyclerView recyclerView = this.f59319a1;
        if (recyclerView != null) {
            if (i == 4096) {
                iM214316c9 = recyclerView.canScrollVertically(1) ? (this.f59332b4 - m214316c9()) - m214313c6() : 0;
                if (this.f59319a1.canScrollHorizontally(1)) {
                    iM214314c7 = (this.f59331b3 - m214314c7()) - m214315c8();
                }
                if (iM214316c9 == 0) {
                }
                this.f59319a1.m210391f3(iM214314c7, iM214316c9, true);
                return true;
            }
            if (i != 8192) {
                iM214316c9 = 0;
                iM214314c7 = 0;
            } else {
                iM214316c9 = recyclerView.canScrollVertically(-1) ? -((this.f59332b4 - m214316c9()) - m214313c6()) : 0;
                iM214314c7 = this.f59319a1.canScrollHorizontally(-1) ? -((this.f59331b3 - m214314c7()) - m214315c8()) : 0;
            }
            if (iM214316c9 == 0 || iM214314c7 != 0) {
                this.f59319a1.m210391f3(iM214314c7, iM214316c9, true);
                return true;
            }
        }
        return false;
    }

    /* renamed from: f6 */
    public final void m214319f6(vq0 vq0Var) {
        for (int iM214311c1 = m214311c1() - 1; iM214311c1 >= 0; iM214311c1--) {
            if (!RecyclerView.m210345d5(m214310c0(iM214311c1)).m212634b4()) {
                View viewM214310c0 = m214310c0(iM214311c1);
                m214322f9(iM214311c1);
                vq0Var.m214943a5(viewM214310c0);
            }
        }
    }

    /* renamed from: f7 */
    public final void m214320f7(vq0 vq0Var) {
        ArrayList arrayList = vq0Var.f60667a0;
        int size = arrayList.size();
        for (int i = size - 1; i >= 0; i--) {
            View view = ((dr0) arrayList.get(i)).f55849a0;
            dr0 dr0VarM210345d5 = RecyclerView.m210345d5(view);
            if (!dr0VarM210345d5.m212634b4()) {
                dr0VarM210345d5.m212633b3(false);
                if (dr0VarM210345d5.m212629a9()) {
                    this.f59319a1.removeDetachedView(view, false);
                }
                lq0 lq0Var = this.f59319a1.f45288d4;
                if (lq0Var != null) {
                    lq0Var.mo213917a3(dr0VarM210345d5);
                }
                dr0VarM210345d5.m212633b3(true);
                dr0 dr0VarM210345d52 = RecyclerView.m210345d5(view);
                dr0VarM210345d52.f55862b3 = null;
                dr0VarM210345d52.f55863b4 = false;
                dr0VarM210345d52.f55858a9 &= -33;
                vq0Var.m214944a6(dr0VarM210345d52);
            }
        }
        arrayList.clear();
        ArrayList arrayList2 = vq0Var.f60668a1;
        if (arrayList2 != null) {
            arrayList2.clear();
        }
        if (size > 0) {
            this.f59319a1.invalidate();
        }
    }

    /* renamed from: f8 */
    public final void m214321f8(View view, vq0 vq0Var) {
        pg1 pg1Var = this.f59318a0;
        fq0 fq0Var = (fq0) pg1Var.f59229a1;
        int iIndexOfChild = fq0Var.f56313a0.indexOfChild(view);
        if (iIndexOfChild >= 0) {
            if (((C0583hj) pg1Var.f59230a2).m213045a5(iIndexOfChild)) {
                pg1Var.m214290d6(view);
            }
            fq0Var.m212853a7(iIndexOfChild);
        }
        vq0Var.m214943a5(view);
    }

    /* renamed from: f9 */
    public final void m214322f9(int i) {
        if (m214310c0(i) != null) {
            pg1 pg1Var = this.f59318a0;
            int iM214280c5 = pg1Var.m214280c5(i);
            fq0 fq0Var = (fq0) pg1Var.f59229a1;
            View childAt = fq0Var.f56313a0.getChildAt(iM214280c5);
            if (childAt == null) {
                return;
            }
            if (((C0583hj) pg1Var.f59230a2).m213045a5(iM214280c5)) {
                pg1Var.m214290d6(childAt);
            }
            fq0Var.m212853a7(iM214280c5);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x00ae  */
    /* renamed from: g0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean mo210970g0(RecyclerView recyclerView, View view, Rect rect, boolean z, boolean z2) {
        int iM214314c7 = m214314c7();
        int iM214316c9 = m214316c9();
        int iM214315c8 = this.f59331b3 - m214315c8();
        int iM214313c6 = this.f59332b4 - m214313c6();
        int left = (view.getLeft() + rect.left) - view.getScrollX();
        int top = (view.getTop() + rect.top) - view.getScrollY();
        int iWidth = rect.width() + left;
        int iHeight = rect.height() + top;
        int i = left - iM214314c7;
        int iMin = Math.min(0, i);
        int i2 = top - iM214316c9;
        int iMin2 = Math.min(0, i2);
        int i3 = iWidth - iM214315c8;
        int iMax = Math.max(0, i3);
        int iMax2 = Math.max(0, iHeight - iM214313c6);
        if (m214312c5() != 1) {
            if (iMin == 0) {
                iMin = Math.min(i, iMax);
            }
            iMax = iMin;
        } else if (iMax == 0) {
            iMax = Math.max(iMin, i3);
        }
        if (iMin2 == 0) {
            iMin2 = Math.min(i2, iMax2);
        }
        int[] iArr = {iMax, iMin2};
        int i4 = iArr[0];
        int i5 = iArr[1];
        if (z2) {
            View focusedChild = recyclerView.getFocusedChild();
            if (focusedChild != null) {
                int iM214314c72 = m214314c7();
                int iM214316c92 = m214316c9();
                int iM214315c82 = this.f59331b3 - m214315c8();
                int iM214313c62 = this.f59332b4 - m214313c6();
                Rect rect2 = this.f59319a1.f45261a7;
                mo210969c4(focusedChild, rect2);
                if (rect2.left - i4 < iM214315c82 && rect2.right - i4 > iM214314c72 && rect2.top - i5 < iM214313c62 && rect2.bottom - i5 > iM214316c92) {
                }
            }
        } else if (i4 != 0 || i5 != 0) {
            if (z) {
                recyclerView.scrollBy(i4, i5);
                return true;
            }
            recyclerView.m210391f3(i4, i5, false);
            return true;
        }
        return false;
    }

    /* renamed from: g1 */
    public final void m214323g1() {
        RecyclerView recyclerView = this.f59319a1;
        if (recyclerView != null) {
            recyclerView.requestLayout();
        }
    }

    /* renamed from: g2 */
    public abstract int mo210278g2(int i, vq0 vq0Var, ar0 ar0Var);

    /* renamed from: g3 */
    public abstract void mo210310g3(int i);

    /* renamed from: g4 */
    public int mo210279g4(int i, vq0 vq0Var, ar0 ar0Var) {
        return 0;
    }

    /* renamed from: g5 */
    public final void m214324g5(RecyclerView recyclerView) {
        m214325g6(View.MeasureSpec.makeMeasureSpec(recyclerView.getWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(recyclerView.getHeight(), 1073741824));
    }

    /* renamed from: g6 */
    public final void m214325g6(int i, int i2) {
        this.f59331b3 = View.MeasureSpec.getSize(i);
        int mode = View.MeasureSpec.getMode(i);
        this.f59329b1 = mode;
        if (mode == 0) {
            int[] iArr = RecyclerView.f45251g8;
        }
        this.f59332b4 = View.MeasureSpec.getSize(i2);
        int mode2 = View.MeasureSpec.getMode(i2);
        this.f59330b2 = mode2;
        if (mode2 == 0) {
            int[] iArr2 = RecyclerView.f45251g8;
        }
    }

    /* renamed from: g7 */
    public void mo210280g7(Rect rect, int i, int i2) {
        int iM214315c8 = m214315c8() + m214314c7() + rect.width();
        int iM214313c6 = m214313c6() + m214316c9() + rect.height();
        RecyclerView recyclerView = this.f59319a1;
        WeakHashMap weakHashMap = xa1.f61054a0;
        this.f59319a1.setMeasuredDimension(m214302a6(i, iM214315c8, fa1.m212767a4(recyclerView)), m214302a6(i2, iM214313c6, fa1.m212766a3(this.f59319a1)));
    }

    /* renamed from: g8 */
    public final void m214326g8(int i, int i2) {
        int iM214311c1 = m214311c1();
        if (iM214311c1 == 0) {
            this.f59319a1.m210353b3(i, i2);
            return;
        }
        int i3 = Integer.MIN_VALUE;
        int i4 = Integer.MAX_VALUE;
        int i5 = Integer.MIN_VALUE;
        int i6 = Integer.MAX_VALUE;
        for (int i7 = 0; i7 < iM214311c1; i7++) {
            View viewM214310c0 = m214310c0(i7);
            Rect rect = this.f59319a1.f45261a7;
            mo210969c4(viewM214310c0, rect);
            int i8 = rect.left;
            if (i8 < i6) {
                i6 = i8;
            }
            int i9 = rect.right;
            if (i9 > i3) {
                i3 = i9;
            }
            int i10 = rect.top;
            if (i10 < i4) {
                i4 = i10;
            }
            int i11 = rect.bottom;
            if (i11 > i5) {
                i5 = i11;
            }
        }
        this.f59319a1.f45261a7.set(i6, i4, i3, i5);
        mo210280g7(this.f59319a1.f45261a7, i, i2);
    }

    /* renamed from: g9 */
    public final void m214327g9(RecyclerView recyclerView) {
        if (recyclerView == null) {
            this.f59319a1 = null;
            this.f59318a0 = null;
            this.f59331b3 = 0;
            this.f59332b4 = 0;
        } else {
            this.f59319a1 = recyclerView;
            this.f59318a0 = recyclerView.f45258a4;
            this.f59331b3 = recyclerView.getWidth();
            this.f59332b4 = recyclerView.getHeight();
        }
        this.f59329b1 = 1073741824;
        this.f59330b2 = 1073741824;
    }

    /* renamed from: h0 */
    public final boolean m214328h0(View view, int i, int i2, qq0 qq0Var) {
        return (!view.isLayoutRequested() && this.f59325a7 && m214306d5(view.getWidth(), i, ((ViewGroup.MarginLayoutParams) qq0Var).width) && m214306d5(view.getHeight(), i2, ((ViewGroup.MarginLayoutParams) qq0Var).height)) ? false : true;
    }

    /* renamed from: h1 */
    public boolean mo210311h1() {
        return false;
    }

    /* renamed from: h2 */
    public final boolean m214329h2(View view, int i, int i2, qq0 qq0Var) {
        return (this.f59325a7 && m214306d5(view.getMeasuredWidth(), i, ((ViewGroup.MarginLayoutParams) qq0Var).width) && m214306d5(view.getMeasuredHeight(), i2, ((ViewGroup.MarginLayoutParams) qq0Var).height)) ? false : true;
    }

    /* renamed from: h3 */
    public abstract void mo210312h3(RecyclerView recyclerView, int i);

    /* renamed from: h4 */
    public final void m214330h4(za0 za0Var) {
        za0 za0Var2 = this.f59322a4;
        if (za0Var2 != null && za0Var != za0Var2 && za0Var2.f61476a4) {
            za0Var2.m215384a7();
        }
        this.f59322a4 = za0Var;
        RecyclerView recyclerView = this.f59319a1;
        cr0 cr0Var = recyclerView.f45303e9;
        cr0Var.f55482a6.removeCallbacks(cr0Var);
        cr0Var.f55478a2.abortAnimation();
        za0Var.f61473a1 = recyclerView;
        za0Var.f61474a2 = this;
        int i = za0Var.f61472a0;
        if (i == -1) {
            throw new IllegalArgumentException("Invalid target position");
        }
        recyclerView.f45306f2.f45596a0 = i;
        za0Var.f61476a4 = true;
        za0Var.f61475a3 = true;
        za0Var.f61477a5 = recyclerView.f45265b1.mo210304b6(i);
        za0Var.f61473a1.f45303e9.m212517a0();
    }

    /* renamed from: h5 */
    public boolean mo210281h5() {
        return false;
    }

    /* renamed from: e6 */
    public void mo210272e6() {
    }

    /* renamed from: d9 */
    public void mo210306d9(RecyclerView recyclerView) {
    }

    /* renamed from: f2 */
    public void mo210308f2(Parcelable parcelable) {
    }

    /* renamed from: f4 */
    public void mo210398f4(int i) {
    }

    /* renamed from: a8 */
    public void mo210301a8(int i, m20 m20Var) {
    }

    /* renamed from: e5 */
    public void mo210271e5(int i, int i2) {
    }

    /* renamed from: e7 */
    public void mo210273e7(int i, int i2) {
    }

    /* renamed from: e8 */
    public void mo210274e8(int i, int i2) {
    }

    /* renamed from: e9 */
    public void mo210275e9(int i, int i2) {
    }

    /* renamed from: a7 */
    public void mo210300a7(int i, int i2, ar0 ar0Var, m20 m20Var) {
    }
}
