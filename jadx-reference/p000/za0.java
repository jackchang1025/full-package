package p000;

import android.content.Context;
import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import androidx.recyclerview.widget.RecyclerView;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public class za0 {

    /* renamed from: a0 */
    public int f61472a0 = -1;

    /* renamed from: a1 */
    public RecyclerView f61473a1;

    /* renamed from: a2 */
    public pq0 f61474a2;

    /* renamed from: a3 */
    public boolean f61475a3;

    /* renamed from: a4 */
    public boolean f61476a4;

    /* renamed from: a5 */
    public View f61477a5;

    /* renamed from: a6 */
    public final yq0 f61478a6;

    /* renamed from: a7 */
    public final LinearInterpolator f61479a7;

    /* renamed from: a8 */
    public final DecelerateInterpolator f61480a8;

    /* renamed from: a9 */
    public PointF f61481a9;

    /* renamed from: b0 */
    public final DisplayMetrics f61482b0;

    /* renamed from: b1 */
    public boolean f61483b1;

    /* renamed from: b2 */
    public float f61484b2;

    /* renamed from: b3 */
    public int f61485b3;

    /* renamed from: b4 */
    public int f61486b4;

    public za0(Context context) {
        yq0 yq0Var = new yq0();
        yq0Var.f61358a3 = -1;
        yq0Var.f61360a5 = false;
        yq0Var.f61355a0 = 0;
        yq0Var.f61356a1 = 0;
        yq0Var.f61357a2 = Integer.MIN_VALUE;
        yq0Var.f61359a4 = null;
        this.f61478a6 = yq0Var;
        this.f61479a7 = new LinearInterpolator();
        this.f61480a8 = new DecelerateInterpolator();
        this.f61483b1 = false;
        this.f61485b3 = 0;
        this.f61486b4 = 0;
        this.f61482b0 = context.getResources().getDisplayMetrics();
    }

    /* renamed from: a0 */
    public static int m215382a0(int i, int i2, int i3, int i4, int i5) {
        if (i5 == -1) {
            return i3 - i;
        }
        if (i5 != 0) {
            if (i5 == 1) {
                return i4 - i2;
            }
            throw new IllegalArgumentException("snap preference should be one of the constants defined in SmoothScroller, starting with SNAP_");
        }
        int i6 = i3 - i;
        if (i6 > 0) {
            return i6;
        }
        int i7 = i4 - i2;
        if (i7 < 0) {
            return i7;
        }
        return 0;
    }

    /* renamed from: a1 */
    public int mo212946a1(View view, int i) {
        pq0 pq0Var = this.f61474a2;
        if (pq0Var == null || !pq0Var.mo210298a3()) {
            return 0;
        }
        qq0 qq0Var = (qq0) view.getLayoutParams();
        return m215382a0((view.getLeft() - ((qq0) view.getLayoutParams()).f59545a1.left) - ((ViewGroup.MarginLayoutParams) qq0Var).leftMargin, view.getRight() + ((qq0) view.getLayoutParams()).f59545a1.right + ((ViewGroup.MarginLayoutParams) qq0Var).rightMargin, pq0Var.m214314c7(), pq0Var.f59331b3 - pq0Var.m214315c8(), i);
    }

    /* renamed from: a2 */
    public float mo212947a2(DisplayMetrics displayMetrics) {
        return 25.0f / displayMetrics.densityDpi;
    }

    /* renamed from: a3 */
    public int mo212948a3(int i) {
        float fAbs = Math.abs(i);
        if (!this.f61483b1) {
            this.f61484b2 = mo212947a2(this.f61482b0);
            this.f61483b1 = true;
        }
        return (int) Math.ceil(fAbs * this.f61484b2);
    }

    /* renamed from: a4 */
    public PointF mo212949a4(int i) {
        Object obj = this.f61474a2;
        if (obj instanceof zq0) {
            return ((zq0) obj).mo210296a0(i);
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:50:0x00f1  */
    /* renamed from: a5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m215383a5(int i, int i2) {
        PointF pointFMo212949a4;
        RecyclerView recyclerView = this.f61473a1;
        if (this.f61472a0 == -1 || recyclerView == null) {
            m215384a7();
        }
        if (this.f61475a3 && this.f61477a5 == null && this.f61474a2 != null && (pointFMo212949a4 = mo212949a4(this.f61472a0)) != null) {
            float f = pointFMo212949a4.x;
            if (f != 0.0f || pointFMo212949a4.y != 0.0f) {
                recyclerView.m210389f1((int) Math.signum(f), (int) Math.signum(pointFMo212949a4.y), null);
            }
        }
        this.f61475a3 = false;
        View view = this.f61477a5;
        yq0 yq0Var = this.f61478a6;
        if (view != null) {
            this.f61473a1.getClass();
            dr0 dr0VarM210345d5 = RecyclerView.m210345d5(view);
            if ((dr0VarM210345d5 != null ? dr0VarM210345d5.m212621a1() : -1) == this.f61472a0) {
                View view2 = this.f61477a5;
                ar0 ar0Var = recyclerView.f45306f2;
                mo212950a6(view2, yq0Var);
                yq0Var.m215303a0(recyclerView);
                m215384a7();
            } else {
                this.f61477a5 = null;
            }
        }
        if (this.f61476a4) {
            ar0 ar0Var2 = recyclerView.f45306f2;
            if (this.f61473a1.f45265b1.m214311c1() == 0) {
                m215384a7();
            } else {
                int i3 = this.f61485b3;
                int i4 = i3 - i;
                if (i3 * i4 <= 0) {
                    i4 = 0;
                }
                this.f61485b3 = i4;
                int i5 = this.f61486b4;
                int i6 = i5 - i2;
                if (i5 * i6 <= 0) {
                    i6 = 0;
                }
                this.f61486b4 = i6;
                if (i4 == 0 && i6 == 0) {
                    PointF pointFMo212949a42 = mo212949a4(this.f61472a0);
                    if (pointFMo212949a42 != null) {
                        if (pointFMo212949a42.x == 0.0f && pointFMo212949a42.y == 0.0f) {
                            yq0Var.f61358a3 = this.f61472a0;
                            m215384a7();
                        } else {
                            float f2 = pointFMo212949a42.y;
                            float fSqrt = (float) Math.sqrt((f2 * f2) + (r10 * r10));
                            float f3 = pointFMo212949a42.x / fSqrt;
                            pointFMo212949a42.x = f3;
                            float f4 = pointFMo212949a42.y / fSqrt;
                            pointFMo212949a42.y = f4;
                            this.f61481a9 = pointFMo212949a42;
                            this.f61485b3 = (int) (f3 * 10000.0f);
                            this.f61486b4 = (int) (f4 * 10000.0f);
                            int iMo212948a3 = mo212948a3(10000);
                            yq0Var.f61355a0 = (int) (this.f61485b3 * 1.2f);
                            yq0Var.f61356a1 = (int) (this.f61486b4 * 1.2f);
                            yq0Var.f61357a2 = (int) (iMo212948a3 * 1.2f);
                            yq0Var.f61359a4 = this.f61479a7;
                            yq0Var.f61360a5 = true;
                        }
                    }
                }
            }
            boolean z = yq0Var.f61358a3 >= 0;
            yq0Var.m215303a0(recyclerView);
            if (z && this.f61476a4) {
                this.f61475a3 = true;
                recyclerView.f45303e9.m212517a0();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0015  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0029  */
    /* renamed from: a6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void mo212950a6(View view, yq0 yq0Var) {
        int i;
        PointF pointF = this.f61481a9;
        int i2 = -1;
        int iM215382a0 = 0;
        if (pointF != null) {
            float f = pointF.x;
            i = f == 0.0f ? 0 : f > 0.0f ? 1 : -1;
        }
        int iMo212946a1 = mo212946a1(view, i);
        PointF pointF2 = this.f61481a9;
        if (pointF2 != null) {
            float f2 = pointF2.y;
            if (f2 == 0.0f) {
                i2 = 0;
            } else if (f2 > 0.0f) {
                i2 = 1;
            }
        }
        pq0 pq0Var = this.f61474a2;
        if (pq0Var != null && pq0Var.mo210299a4()) {
            qq0 qq0Var = (qq0) view.getLayoutParams();
            iM215382a0 = m215382a0((view.getTop() - ((qq0) view.getLayoutParams()).f59545a1.top) - ((ViewGroup.MarginLayoutParams) qq0Var).topMargin, view.getBottom() + ((qq0) view.getLayoutParams()).f59545a1.bottom + ((ViewGroup.MarginLayoutParams) qq0Var).bottomMargin, pq0Var.m214316c9(), pq0Var.f59332b4 - pq0Var.m214313c6(), i2);
        }
        int iCeil = (int) Math.ceil(mo212948a3((int) Math.sqrt((iM215382a0 * iM215382a0) + (iMo212946a1 * iMo212946a1))) / 0.3356d);
        if (iCeil > 0) {
            yq0Var.f61355a0 = -iMo212946a1;
            yq0Var.f61356a1 = -iM215382a0;
            yq0Var.f61357a2 = iCeil;
            yq0Var.f61359a4 = this.f61480a8;
            yq0Var.f61360a5 = true;
        }
    }

    /* renamed from: a7 */
    public final void m215384a7() {
        if (this.f61476a4) {
            this.f61476a4 = false;
            this.f61486b4 = 0;
            this.f61485b3 = 0;
            this.f61481a9 = null;
            this.f61473a1.f45306f2.f45596a0 = -1;
            this.f61477a5 = null;
            this.f61472a0 = -1;
            this.f61475a3 = false;
            pq0 pq0Var = this.f61474a2;
            if (pq0Var.f59322a4 == this) {
                pq0Var.f59322a4 = null;
            }
            this.f61474a2 = null;
            this.f61473a1 = null;
        }
    }
}
