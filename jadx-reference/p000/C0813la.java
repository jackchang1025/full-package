package p000;

import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Placeholder;
import androidx.constraintlayout.widget.VirtualLayout;
import org.conscrypt.PSKKeyManager;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: la */
/* loaded from: classes.dex */
public final class C0813la {

    /* renamed from: a0 */
    public final ConstraintLayout f57858a0;

    /* renamed from: a1 */
    public int f57859a1;

    /* renamed from: a2 */
    public int f57860a2;

    /* renamed from: a3 */
    public int f57861a3;

    /* renamed from: a4 */
    public int f57862a4;

    /* renamed from: a5 */
    public int f57863a5;

    /* renamed from: a6 */
    public int f57864a6;

    /* renamed from: a7 */
    public final /* synthetic */ ConstraintLayout f57865a7;

    public C0813la(ConstraintLayout constraintLayout, ConstraintLayout constraintLayout2) {
        this.f57865a7 = constraintLayout;
        this.f57858a0 = constraintLayout2;
    }

    /* renamed from: a0 */
    public static boolean m213799a0(int i, int i2, int i3) {
        if (i == i2) {
            return true;
        }
        int mode = View.MeasureSpec.getMode(i);
        View.MeasureSpec.getSize(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i2);
        if (mode2 == 1073741824) {
            return (mode == Integer.MIN_VALUE || mode == 0) && i3 == size;
        }
        return false;
    }

    /* renamed from: a1 */
    public final void m213800a1(C0829lq c0829lq, C0418dj c0418dj) {
        int iMakeMeasureSpec;
        int iMakeMeasureSpec2;
        int iMax;
        boolean z;
        int measuredWidth;
        int baseline;
        int i;
        if (c0829lq == null) {
            return;
        }
        C0797kv c0797kv = c0829lq.f58098d7;
        C0797kv c0797kv2 = c0829lq.f58096d5;
        if (c0829lq.f58121g0 == 8 && !c0829lq.f58092d1) {
            c0418dj.f55823a4 = 0;
            c0418dj.f55824a5 = 0;
            c0418dj.f55825a6 = 0;
            return;
        }
        if (c0829lq.f58108e7 == null) {
            return;
        }
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour = c0418dj.f55819a0;
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour2 = c0418dj.f55820a1;
        int i2 = c0418dj.f55821a2;
        int i3 = c0418dj.f55822a3;
        int i4 = this.f57859a1 + this.f57860a2;
        int i5 = this.f57861a3;
        View view = c0829lq.f58120f9;
        int iOrdinal = constraintWidget$DimensionBehaviour.ordinal();
        if (iOrdinal == 0) {
            iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i2, 1073741824);
        } else if (iOrdinal == 1) {
            iMakeMeasureSpec = ViewGroup.getChildMeasureSpec(this.f57863a5, i5, -2);
        } else if (iOrdinal == 2) {
            iMakeMeasureSpec = ViewGroup.getChildMeasureSpec(this.f57863a5, i5, -2);
            boolean z2 = c0829lq.f58078b7 == 1;
            int i6 = c0418dj.f55828a9;
            if (i6 == 1 || i6 == 2) {
                boolean z3 = view.getMeasuredHeight() == c0829lq.m213887b1();
                if (c0418dj.f55828a9 == 2 || !z2 || ((z2 && z3) || (view instanceof Placeholder) || c0829lq.mo212533c7())) {
                    iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(c0829lq.m213891b7(), 1073741824);
                }
            }
        } else if (iOrdinal != 3) {
            iMakeMeasureSpec = 0;
        } else {
            int i7 = this.f57863a5;
            int i8 = c0797kv2 != null ? c0797kv2.f57727a6 : 0;
            if (c0797kv != null) {
                i8 += c0797kv.f57727a6;
            }
            iMakeMeasureSpec = ViewGroup.getChildMeasureSpec(i7, i5 + i8, -1);
        }
        int iOrdinal2 = constraintWidget$DimensionBehaviour2.ordinal();
        if (iOrdinal2 == 0) {
            iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(i3, 1073741824);
        } else if (iOrdinal2 == 1) {
            iMakeMeasureSpec2 = ViewGroup.getChildMeasureSpec(this.f57864a6, i4, -2);
        } else if (iOrdinal2 == 2) {
            iMakeMeasureSpec2 = ViewGroup.getChildMeasureSpec(this.f57864a6, i4, -2);
            boolean z4 = c0829lq.f58079b8 == 1;
            int i9 = c0418dj.f55828a9;
            if (i9 == 1 || i9 == 2) {
                boolean z5 = view.getMeasuredWidth() == c0829lq.m213891b7();
                if (c0418dj.f55828a9 == 2 || !z4 || ((z4 && z5) || (view instanceof Placeholder) || c0829lq.mo212534c8())) {
                    iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(c0829lq.m213887b1(), 1073741824);
                }
            }
        } else if (iOrdinal2 != 3) {
            iMakeMeasureSpec2 = 0;
        } else {
            int i10 = this.f57864a6;
            int i11 = c0797kv2 != null ? c0829lq.f58097d6.f57727a6 : 0;
            if (c0797kv != null) {
                i11 += c0829lq.f58099d8.f57727a6;
            }
            iMakeMeasureSpec2 = ViewGroup.getChildMeasureSpec(i10, i4 + i11, -1);
        }
        C0830lr c0830lr = (C0830lr) c0829lq.f58108e7;
        ConstraintLayout constraintLayout = this.f57865a7;
        if (c0830lr != null && kj1.m213565b2(constraintLayout.f44779a8, PSKKeyManager.MAX_KEY_LENGTH_BYTES) && view.getMeasuredWidth() == c0829lq.m213891b7() && view.getMeasuredWidth() < c0830lr.m213891b7() && view.getMeasuredHeight() == c0829lq.m213887b1() && view.getMeasuredHeight() < c0830lr.m213887b1() && view.getBaseline() == c0829lq.f58115f4 && !c0829lq.m213900c6() && m213799a0(c0829lq.f58094d3, iMakeMeasureSpec, c0829lq.m213891b7()) && m213799a0(c0829lq.f58095d4, iMakeMeasureSpec2, c0829lq.m213887b1())) {
            c0418dj.f55823a4 = c0829lq.m213891b7();
            c0418dj.f55824a5 = c0829lq.m213887b1();
            c0418dj.f55825a6 = c0829lq.f58115f4;
            return;
        }
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour3 = ConstraintWidget$DimensionBehaviour.f44426a2;
        boolean z6 = constraintWidget$DimensionBehaviour == constraintWidget$DimensionBehaviour3;
        boolean z7 = constraintWidget$DimensionBehaviour2 == constraintWidget$DimensionBehaviour3;
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour4 = ConstraintWidget$DimensionBehaviour.f44424a0;
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour5 = ConstraintWidget$DimensionBehaviour.f44427a3;
        boolean z8 = constraintWidget$DimensionBehaviour2 == constraintWidget$DimensionBehaviour5 || constraintWidget$DimensionBehaviour2 == constraintWidget$DimensionBehaviour4;
        boolean z9 = constraintWidget$DimensionBehaviour == constraintWidget$DimensionBehaviour5 || constraintWidget$DimensionBehaviour == constraintWidget$DimensionBehaviour4;
        boolean z10 = z6 && c0829lq.f58111f0 > 0.0f;
        boolean z11 = z7 && c0829lq.f58111f0 > 0.0f;
        if (view == null) {
            return;
        }
        C0801kz c0801kz = (C0801kz) view.getLayoutParams();
        int i12 = c0418dj.f55828a9;
        if (i12 != 1 && i12 != 2 && z6 && c0829lq.f58078b7 == 0 && z7 && c0829lq.f58079b8 == 0) {
            z = false;
            measuredWidth = 0;
            baseline = 0;
            i = -1;
            iMax = 0;
        } else {
            if ((view instanceof VirtualLayout) && (c0829lq instanceof md1)) {
                ((VirtualLayout) view).mo209974b7((md1) c0829lq, iMakeMeasureSpec, iMakeMeasureSpec2);
            } else {
                view.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
            }
            c0829lq.f58094d3 = iMakeMeasureSpec;
            c0829lq.f58095d4 = iMakeMeasureSpec2;
            c0829lq.f58067a6 = false;
            int measuredWidth2 = view.getMeasuredWidth();
            int measuredHeight = view.getMeasuredHeight();
            int baseline2 = view.getBaseline();
            int i13 = c0829lq.f58081c0;
            int iMax2 = i13 > 0 ? Math.max(i13, measuredWidth2) : measuredWidth2;
            int i14 = c0829lq.f58082c1;
            if (i14 > 0) {
                iMax2 = Math.min(i14, iMax2);
            }
            int i15 = c0829lq.f58084c3;
            iMax = i15 > 0 ? Math.max(i15, measuredHeight) : measuredHeight;
            int i16 = iMakeMeasureSpec2;
            int i17 = c0829lq.f58085c4;
            if (i17 > 0) {
                iMax = Math.min(i17, iMax);
            }
            if (!kj1.m213565b2(constraintLayout.f44779a8, 1)) {
                if (z10 && z8) {
                    iMax2 = (int) ((iMax * c0829lq.f58111f0) + 0.5f);
                } else if (z11 && z9) {
                    iMax = (int) ((iMax2 / c0829lq.f58111f0) + 0.5f);
                }
            }
            if (measuredWidth2 == iMax2 && measuredHeight == iMax) {
                baseline = baseline2;
                measuredWidth = iMax2;
                z = false;
            } else {
                if (measuredWidth2 != iMax2) {
                    iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(iMax2, 1073741824);
                }
                int iMakeMeasureSpec3 = measuredHeight != iMax ? View.MeasureSpec.makeMeasureSpec(iMax, 1073741824) : i16;
                view.measure(iMakeMeasureSpec, iMakeMeasureSpec3);
                c0829lq.f58094d3 = iMakeMeasureSpec;
                c0829lq.f58095d4 = iMakeMeasureSpec3;
                z = false;
                c0829lq.f58067a6 = false;
                measuredWidth = view.getMeasuredWidth();
                int measuredHeight2 = view.getMeasuredHeight();
                baseline = view.getBaseline();
                iMax = measuredHeight2;
            }
            i = -1;
        }
        boolean z12 = baseline != i ? true : z;
        c0418dj.f55827a8 = (measuredWidth == c0418dj.f55821a2 && iMax == c0418dj.f55822a3) ? z : true;
        boolean z13 = c0801kz.f57800f4 ? true : z12;
        if (z13 && baseline != -1 && c0829lq.f58115f4 != baseline) {
            c0418dj.f55827a8 = true;
        }
        c0418dj.f55823a4 = measuredWidth;
        c0418dj.f55824a5 = iMax;
        c0418dj.f55826a7 = z13;
        c0418dj.f55825a6 = baseline;
    }
}
