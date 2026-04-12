package p000;

import android.content.Context;
import android.graphics.Rect;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour;
import androidx.constraintlayout.motion.widget.MotionHelper;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.Barrier;
import androidx.constraintlayout.widget.ConstraintHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Executors;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: kr */
/* loaded from: classes2.dex */
public final class C0793kr {

    /* renamed from: a0 */
    public int f57707a0;

    /* renamed from: a1 */
    public int f57708a1;

    /* renamed from: a2 */
    public Object f57709a2;

    /* renamed from: a3 */
    public Object f57710a3;

    /* renamed from: a4 */
    public Object f57711a4;

    /* renamed from: a5 */
    public Object f57712a5;

    /* renamed from: a6 */
    public final Object f57713a6;

    public C0793kr(tg0 tg0Var) {
        this.f57709a2 = Executors.newFixedThreadPool(Math.max(2, Math.min(Runtime.getRuntime().availableProcessors() - 1, 4)), new ThreadFactoryC0792kq(false));
        this.f57710a3 = Executors.newFixedThreadPool(Math.max(2, Math.min(Runtime.getRuntime().availableProcessors() - 1, 4)), new ThreadFactoryC0792kq(true));
        dh1 dh1Var = (dh1) tg0Var.f60218a1;
        if (dh1Var == null) {
            this.f57711a4 = new dh1();
        } else {
            this.f57711a4 = dh1Var;
        }
        this.f57712a5 = new C1351vv(29);
        this.f57713a6 = new tg0(14);
        this.f57707a0 = Integer.MAX_VALUE;
        this.f57708a1 = 20;
    }

    /* renamed from: a2 */
    public static void m213738a2(C0830lr c0830lr, C0830lr c0830lr2) {
        ArrayList arrayList = c0830lr.f58139h2;
        HashMap map = new HashMap();
        map.put(c0830lr, c0830lr2);
        c0830lr2.f58139h2.clear();
        c0830lr2.mo210535a6(c0830lr, map);
        int size = arrayList.size();
        int i = 0;
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            C0829lq c0829lq = (C0829lq) obj;
            C0829lq c0392cv = c0829lq instanceof C0392cv ? new C0392cv() : c0829lq instanceof o30 ? new o30() : c0829lq instanceof C0154c ? new C0154c() : c0829lq instanceof mn0 ? new mn0() : c0829lq instanceof b40 ? new b40() : new C0829lq();
            c0830lr2.f58139h2.add(c0392cv);
            C0829lq c0829lq2 = c0392cv.f58108e7;
            if (c0829lq2 != null) {
                ((C0830lr) c0829lq2).f58139h2.remove(c0392cv);
                c0392cv.mo213901c9();
            }
            c0392cv.f58108e7 = c0830lr2;
            map.put(c0829lq, c0392cv);
        }
        int size2 = arrayList.size();
        while (i < size2) {
            Object obj2 = arrayList.get(i);
            i++;
            C0829lq c0829lq3 = (C0829lq) obj2;
            ((C0829lq) map.get(c0829lq3)).mo210535a6(c0829lq3, map);
        }
    }

    /* renamed from: a3 */
    public static C0829lq m213739a3(C0830lr c0830lr, View view) {
        if (c0830lr.f58120f9 == view) {
            return c0830lr;
        }
        ArrayList arrayList = c0830lr.f58139h2;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            C0829lq c0829lq = (C0829lq) arrayList.get(i);
            if (c0829lq.f58120f9 == view) {
                return c0829lq;
            }
        }
        return null;
    }

    /* renamed from: a0 */
    public void m213740a0() {
        HashMap map;
        int[] iArr;
        MotionLayout motionLayout = (MotionLayout) this.f57713a6;
        int childCount = motionLayout.getChildCount();
        HashMap map2 = motionLayout.f44534c8;
        map2.clear();
        SparseArray sparseArray = new SparseArray();
        int[] iArr2 = new int[childCount];
        for (int i = 0; i < childCount; i++) {
            View childAt = motionLayout.getChildAt(i);
            og0 og0Var = new og0(childAt);
            int id = childAt.getId();
            iArr2[i] = id;
            sparseArray.put(id, og0Var);
            map2.put(childAt, og0Var);
        }
        int i2 = 0;
        while (i2 < childCount) {
            View childAt2 = motionLayout.getChildAt(i2);
            og0 og0Var2 = (og0) map2.get(childAt2);
            if (og0Var2 == null) {
                map = map2;
                iArr = iArr2;
            } else {
                Rect rect = og0Var2.f58799a0;
                vg0 vg0Var = og0Var2.f58804a5;
                if (((C0825lm) this.f57711a4) != null) {
                    C0829lq c0829lqM213739a3 = m213739a3((C0830lr) this.f57709a2, childAt2);
                    if (c0829lqM213739a3 != null) {
                        Rect rectM209984b5 = MotionLayout.m209984b5(motionLayout, c0829lqM213739a3);
                        C0825lm c0825lm = (C0825lm) this.f57711a4;
                        int width = motionLayout.getWidth();
                        int height = motionLayout.getHeight();
                        int i3 = c0825lm.f58049a2;
                        if (i3 != 0) {
                            og0.m214191a6(rectM209984b5, rect, i3, width, height);
                        }
                        vg0Var.f60628a2 = 0.0f;
                        vg0Var.f60629a3 = 0.0f;
                        og0Var2.m214197a5(vg0Var);
                        map = map2;
                        iArr = iArr2;
                        vg0Var.m214927a3(rectM209984b5.left, rectM209984b5.top, rectM209984b5.width(), rectM209984b5.height());
                        C0820lh c0820lhM213869a7 = c0825lm.m213869a7(og0Var2.f58801a2);
                        vg0Var.m214925a0(c0820lhM213869a7);
                        C0822lj c0822lj = c0820lhM213869a7.f57929a3;
                        og0Var2.f58810b1 = c0822lj.f58013a6;
                        og0Var2.f58806a7.m214001a2(rectM209984b5, c0825lm, i3, og0Var2.f58801a2);
                        og0Var2.f58826c7 = c0820lhM213869a7.f57931a5.f58038a8;
                        og0Var2.f58828c9 = c0822lj.f58016a9;
                        og0Var2.f58829d0 = c0822lj.f58015a8;
                        Context context = og0Var2.f58800a1.getContext();
                        int i4 = c0822lj.f58018b1;
                        og0Var2.f58830d1 = i4 != -2 ? i4 != -1 ? i4 != 0 ? i4 != 1 ? i4 != 2 ? i4 != 4 ? i4 != 5 ? null : new OvershootInterpolator() : new BounceInterpolator() : new DecelerateInterpolator() : new AccelerateInterpolator() : new AccelerateDecelerateInterpolator() : new ng0(C1347vr.m214949a2(c0822lj.f58017b0), 0) : AnimationUtils.loadInterpolator(context, c0822lj.f58019b2);
                    } else {
                        map = map2;
                        iArr = iArr2;
                        if (motionLayout.f44544d8 != 0) {
                            t60.m214710d1();
                            t60.m214712d3(childAt2);
                            childAt2.getClass();
                        }
                    }
                } else {
                    map = map2;
                    iArr = iArr2;
                }
                if (((C0825lm) this.f57712a5) != null) {
                    C0829lq c0829lqM213739a32 = m213739a3((C0830lr) this.f57710a3, childAt2);
                    if (c0829lqM213739a32 != null) {
                        Rect rectM209984b52 = MotionLayout.m209984b5(motionLayout, c0829lqM213739a32);
                        C0825lm c0825lm2 = (C0825lm) this.f57712a5;
                        int width2 = motionLayout.getWidth();
                        int height2 = motionLayout.getHeight();
                        vg0 vg0Var2 = og0Var2.f58805a6;
                        int i5 = c0825lm2.f58049a2;
                        if (i5 != 0) {
                            og0.m214191a6(rectM209984b52, rect, i5, width2, height2);
                        } else {
                            rect = rectM209984b52;
                        }
                        vg0Var2.f60628a2 = 1.0f;
                        vg0Var2.f60629a3 = 1.0f;
                        og0Var2.m214197a5(vg0Var2);
                        vg0Var2.m214927a3(rect.left, rect.top, rect.width(), rect.height());
                        vg0Var2.m214925a0(c0825lm2.m213869a7(og0Var2.f58801a2));
                        og0Var2.f58807a8.m214001a2(rect, c0825lm2, i5, og0Var2.f58801a2);
                    } else if (motionLayout.f44544d8 != 0) {
                        t60.m214710d1();
                        t60.m214712d3(childAt2);
                        childAt2.getClass();
                    }
                }
            }
            i2++;
            map2 = map;
            iArr2 = iArr;
        }
        int[] iArr3 = iArr2;
        for (int i6 = 0; i6 < childCount; i6++) {
            og0 og0Var3 = (og0) sparseArray.get(iArr3[i6]);
            int i7 = og0Var3.f58804a5.f60636b0;
            if (i7 != -1) {
                og0 og0Var4 = (og0) sparseArray.get(i7);
                og0Var3.f58804a5.m214928a5(og0Var4, og0Var4.f58804a5);
                og0Var3.f58805a6.m214928a5(og0Var4, og0Var4.f58805a6);
            }
        }
    }

    /* renamed from: a1 */
    public void m213741a1(int i, int i2) {
        MotionLayout motionLayout = (MotionLayout) this.f57713a6;
        int optimizationLevel = motionLayout.getOptimizationLevel();
        if (motionLayout.f44529c3 == motionLayout.getStartState()) {
            C0830lr c0830lr = (C0830lr) this.f57710a3;
            C0825lm c0825lm = (C0825lm) this.f57712a5;
            motionLayout.m210052b3(c0830lr, optimizationLevel, (c0825lm == null || c0825lm.f58049a2 == 0) ? i : i2, (c0825lm == null || c0825lm.f58049a2 == 0) ? i2 : i);
            C0825lm c0825lm2 = (C0825lm) this.f57711a4;
            if (c0825lm2 != null) {
                C0830lr c0830lr2 = (C0830lr) this.f57709a2;
                int i3 = c0825lm2.f58049a2;
                int i4 = i3 == 0 ? i : i2;
                if (i3 == 0) {
                    i = i2;
                }
                motionLayout.m210052b3(c0830lr2, optimizationLevel, i4, i);
                return;
            }
            return;
        }
        C0825lm c0825lm3 = (C0825lm) this.f57711a4;
        if (c0825lm3 != null) {
            C0830lr c0830lr3 = (C0830lr) this.f57709a2;
            int i5 = c0825lm3.f58049a2;
            motionLayout.m210052b3(c0830lr3, optimizationLevel, i5 == 0 ? i : i2, i5 == 0 ? i2 : i);
        }
        C0830lr c0830lr4 = (C0830lr) this.f57710a3;
        C0825lm c0825lm4 = (C0825lm) this.f57712a5;
        int i6 = (c0825lm4 == null || c0825lm4.f58049a2 == 0) ? i : i2;
        if (c0825lm4 == null || c0825lm4.f58049a2 == 0) {
            i = i2;
        }
        motionLayout.m210052b3(c0830lr4, optimizationLevel, i6, i);
    }

    /* renamed from: a4 */
    public void m213742a4(C0825lm c0825lm, C0825lm c0825lm2) {
        this.f57711a4 = c0825lm;
        this.f57712a5 = c0825lm2;
        this.f57709a2 = new C0830lr();
        C0830lr c0830lr = new C0830lr();
        this.f57710a3 = c0830lr;
        C0830lr c0830lr2 = (C0830lr) this.f57709a2;
        MotionLayout motionLayout = (MotionLayout) this.f57713a6;
        boolean z = MotionLayout.f44523i2;
        C0830lr c0830lr3 = motionLayout.f44773a2;
        C0813la c0813la = c0830lr3.f58143h6;
        c0830lr2.f58143h6 = c0813la;
        c0830lr2.f58141h4.f59960a5 = c0813la;
        C0813la c0813la2 = c0830lr3.f58143h6;
        c0830lr.f58143h6 = c0813la2;
        c0830lr.f58141h4.f59960a5 = c0813la2;
        c0830lr2.f58139h2.clear();
        ((C0830lr) this.f57710a3).f58139h2.clear();
        m213738a2(c0830lr3, (C0830lr) this.f57709a2);
        m213738a2(c0830lr3, (C0830lr) this.f57710a3);
        if (motionLayout.f44538d2 > 0.5d) {
            if (c0825lm != null) {
                m213744a6((C0830lr) this.f57709a2, c0825lm);
            }
            m213744a6((C0830lr) this.f57710a3, c0825lm2);
        } else {
            m213744a6((C0830lr) this.f57710a3, c0825lm2);
            if (c0825lm != null) {
                m213744a6((C0830lr) this.f57709a2, c0825lm);
            }
        }
        ((C0830lr) this.f57709a2).f58144h7 = motionLayout.m210050b0();
        C0830lr c0830lr4 = (C0830lr) this.f57709a2;
        c0830lr4.f58140h3.m214291d7(c0830lr4);
        ((C0830lr) this.f57710a3).f58144h7 = motionLayout.m210050b0();
        C0830lr c0830lr5 = (C0830lr) this.f57710a3;
        c0830lr5.f58140h3.m214291d7(c0830lr5);
        ViewGroup.LayoutParams layoutParams = motionLayout.getLayoutParams();
        if (layoutParams != null) {
            int i = layoutParams.width;
            ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour = ConstraintWidget$DimensionBehaviour.f44425a1;
            if (i == -2) {
                ((C0830lr) this.f57709a2).m213909d9(constraintWidget$DimensionBehaviour);
                ((C0830lr) this.f57710a3).m213909d9(constraintWidget$DimensionBehaviour);
            }
            if (layoutParams.height == -2) {
                ((C0830lr) this.f57709a2).m213910e0(constraintWidget$DimensionBehaviour);
                ((C0830lr) this.f57710a3).m213910e0(constraintWidget$DimensionBehaviour);
            }
        }
    }

    /* renamed from: a5 */
    public void m213743a5() {
        MotionLayout motionLayout = (MotionLayout) this.f57713a6;
        int i = motionLayout.f44531c5;
        int i2 = motionLayout.f44532c6;
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        motionLayout.f44572g6 = mode;
        motionLayout.f44573g7 = mode2;
        motionLayout.getOptimizationLevel();
        m213741a1(i, i2);
        int i3 = 0;
        if (!(motionLayout.getParent() instanceof MotionLayout) || mode != 1073741824 || mode2 != 1073741824) {
            m213741a1(i, i2);
            motionLayout.f44568g2 = ((C0830lr) this.f57709a2).m213891b7();
            motionLayout.f44569g3 = ((C0830lr) this.f57709a2).m213887b1();
            motionLayout.f44570g4 = ((C0830lr) this.f57710a3).m213891b7();
            int iM213887b1 = ((C0830lr) this.f57710a3).m213887b1();
            motionLayout.f44571g5 = iM213887b1;
            motionLayout.f44567g1 = (motionLayout.f44568g2 == motionLayout.f44570g4 && motionLayout.f44569g3 == iM213887b1) ? false : true;
        }
        int i4 = motionLayout.f44568g2;
        int i5 = motionLayout.f44569g3;
        int i6 = motionLayout.f44572g6;
        if (i6 == Integer.MIN_VALUE || i6 == 0) {
            i4 = (int) ((motionLayout.f44574g8 * (motionLayout.f44570g4 - i4)) + i4);
        }
        int i7 = motionLayout.f44573g7;
        if (i7 == Integer.MIN_VALUE || i7 == 0) {
            i5 = (int) ((motionLayout.f44574g8 * (motionLayout.f44571g5 - i5)) + i5);
        }
        int i8 = i5;
        C0830lr c0830lr = (C0830lr) this.f57709a2;
        motionLayout.m210051b2(i, i2, i4, i8, c0830lr.f58153i6 || ((C0830lr) this.f57710a3).f58153i6, c0830lr.f58154i7 || ((C0830lr) this.f57710a3).f58154i7);
        HashMap map = motionLayout.f44534c8;
        int childCount = motionLayout.getChildCount();
        motionLayout.f44582h6.m213740a0();
        motionLayout.f44542d6 = true;
        SparseArray sparseArray = new SparseArray();
        for (int i9 = 0; i9 < childCount; i9++) {
            View childAt = motionLayout.getChildAt(i9);
            sparseArray.put(childAt.getId(), (og0) map.get(childAt));
        }
        int width = motionLayout.getWidth();
        int height = motionLayout.getHeight();
        xg0 xg0Var = motionLayout.f44524b8.f44600a2;
        int i10 = xg0Var != null ? xg0Var.f61122b5 : -1;
        if (i10 != -1) {
            for (int i11 = 0; i11 < childCount; i11++) {
                og0 og0Var = (og0) map.get(motionLayout.getChildAt(i11));
                if (og0Var != null) {
                    og0Var.f58825c6 = i10;
                }
            }
        }
        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
        int[] iArr = new int[map.size()];
        int i12 = 0;
        for (int i13 = 0; i13 < childCount; i13++) {
            og0 og0Var2 = (og0) map.get(motionLayout.getChildAt(i13));
            int i14 = og0Var2.f58804a5.f60636b0;
            if (i14 != -1) {
                sparseBooleanArray.put(i14, true);
                iArr[i12] = og0Var2.f58804a5.f60636b0;
                i12++;
            }
        }
        if (motionLayout.f44560f4 != null) {
            for (int i15 = 0; i15 < i12; i15++) {
                og0 og0Var3 = (og0) map.get(motionLayout.findViewById(iArr[i15]));
                if (og0Var3 != null) {
                    motionLayout.f44524b8.m210010a5(og0Var3);
                }
            }
            ArrayList arrayList = motionLayout.f44560f4;
            int size = arrayList.size();
            int i16 = 0;
            while (i16 < size) {
                Object obj = arrayList.get(i16);
                i16++;
                ((MotionHelper) obj).mo209981b7(motionLayout, map);
            }
            for (int i17 = 0; i17 < i12; i17++) {
                og0 og0Var4 = (og0) map.get(motionLayout.findViewById(iArr[i17]));
                if (og0Var4 != null) {
                    og0Var4.m214198a7(motionLayout.getNanoTime(), width, height);
                }
            }
        } else {
            for (int i18 = 0; i18 < i12; i18++) {
                og0 og0Var5 = (og0) map.get(motionLayout.findViewById(iArr[i18]));
                if (og0Var5 != null) {
                    motionLayout.f44524b8.m210010a5(og0Var5);
                    og0Var5.m214198a7(motionLayout.getNanoTime(), width, height);
                }
            }
        }
        for (int i19 = 0; i19 < childCount; i19++) {
            View childAt2 = motionLayout.getChildAt(i19);
            og0 og0Var6 = (og0) map.get(childAt2);
            if (!sparseBooleanArray.get(childAt2.getId()) && og0Var6 != null) {
                motionLayout.f44524b8.m210010a5(og0Var6);
                og0Var6.m214198a7(motionLayout.getNanoTime(), width, height);
            }
        }
        xg0 xg0Var2 = motionLayout.f44524b8.f44600a2;
        float f = xg0Var2 != null ? xg0Var2.f61115a8 : 0.0f;
        if (f != 0.0f) {
            boolean z = ((double) f) < 0.0d;
            float fAbs = Math.abs(f);
            float fMax = -3.4028235E38f;
            float fMin = Float.MAX_VALUE;
            float fMax2 = -3.4028235E38f;
            float fMin2 = Float.MAX_VALUE;
            for (int i20 = 0; i20 < childCount; i20++) {
                og0 og0Var7 = (og0) map.get(motionLayout.getChildAt(i20));
                if (!Float.isNaN(og0Var7.f58810b1)) {
                    for (int i21 = 0; i21 < childCount; i21++) {
                        og0 og0Var8 = (og0) map.get(motionLayout.getChildAt(i21));
                        if (!Float.isNaN(og0Var8.f58810b1)) {
                            fMin = Math.min(fMin, og0Var8.f58810b1);
                            fMax = Math.max(fMax, og0Var8.f58810b1);
                        }
                    }
                    while (i3 < childCount) {
                        og0 og0Var9 = (og0) map.get(motionLayout.getChildAt(i3));
                        if (!Float.isNaN(og0Var9.f58810b1)) {
                            og0Var9.f58812b3 = 1.0f / (1.0f - fAbs);
                            if (z) {
                                og0Var9.f58811b2 = fAbs - (((fMax - og0Var9.f58810b1) / (fMax - fMin)) * fAbs);
                            } else {
                                og0Var9.f58811b2 = fAbs - (((og0Var9.f58810b1 - fMin) * fAbs) / (fMax - fMin));
                            }
                        }
                        i3++;
                    }
                    return;
                }
                vg0 vg0Var = og0Var7.f58805a6;
                float f2 = vg0Var.f60630a4;
                float f3 = vg0Var.f60631a5;
                float f4 = z ? f3 - f2 : f3 + f2;
                fMin2 = Math.min(fMin2, f4);
                fMax2 = Math.max(fMax2, f4);
            }
            while (i3 < childCount) {
                og0 og0Var10 = (og0) map.get(motionLayout.getChildAt(i3));
                vg0 vg0Var2 = og0Var10.f58805a6;
                float f5 = vg0Var2.f60630a4;
                float f6 = vg0Var2.f60631a5;
                float f7 = z ? f6 - f5 : f6 + f5;
                og0Var10.f58812b3 = 1.0f / (1.0f - fAbs);
                og0Var10.f58811b2 = fAbs - (((f7 - fMin2) * fAbs) / (fMax2 - fMin2));
                i3++;
            }
        }
    }

    /* renamed from: a6 */
    public void m213744a6(C0830lr c0830lr, C0825lm c0825lm) throws NumberFormatException {
        C0820lh c0820lh;
        C0820lh c0820lh2;
        SparseArray sparseArray = new SparseArray();
        C0835lu c0835lu = new C0835lu();
        sparseArray.clear();
        sparseArray.put(0, c0830lr);
        MotionLayout motionLayout = (MotionLayout) this.f57713a6;
        sparseArray.put(motionLayout.getId(), c0830lr);
        if (c0825lm != null && c0825lm.f58049a2 != 0) {
            C0830lr c0830lr2 = (C0830lr) this.f57710a3;
            int optimizationLevel = motionLayout.getOptimizationLevel();
            int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(motionLayout.getHeight(), 1073741824);
            int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(motionLayout.getWidth(), 1073741824);
            boolean z = MotionLayout.f44523i2;
            motionLayout.m210052b3(c0830lr2, optimizationLevel, iMakeMeasureSpec, iMakeMeasureSpec2);
        }
        ArrayList arrayList = c0830lr.f58139h2;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            C0829lq c0829lq = (C0829lq) obj;
            c0829lq.f58122g1 = true;
            sparseArray.put(c0829lq.f58120f9.getId(), c0829lq);
        }
        ArrayList arrayList2 = c0830lr.f58139h2;
        int size2 = arrayList2.size();
        int i2 = 0;
        while (i2 < size2) {
            int i3 = i2 + 1;
            C0829lq c0829lq2 = (C0829lq) arrayList2.get(i2);
            View view = c0829lq2.f58120f9;
            int id = view.getId();
            HashMap map = c0825lm.f58052a5;
            if (map.containsKey(Integer.valueOf(id)) && (c0820lh2 = (C0820lh) map.get(Integer.valueOf(id))) != null) {
                c0820lh2.m213845a0(c0835lu);
            }
            c0829lq2.m213911e1(c0825lm.m213869a7(view.getId()).f57930a4.f57937a2);
            c0829lq2.m213908d8(c0825lm.m213869a7(view.getId()).f57930a4.f57938a3);
            if (view instanceof ConstraintHelper) {
                ConstraintHelper constraintHelper = (ConstraintHelper) view;
                int id2 = constraintHelper.getId();
                HashMap map2 = c0825lm.f58052a5;
                if (map2.containsKey(Integer.valueOf(id2)) && (c0820lh = (C0820lh) map2.get(Integer.valueOf(id2))) != null && (c0829lq2 instanceof b40)) {
                    constraintHelper.mo209972b1(c0820lh, (b40) c0829lq2, c0835lu, sparseArray);
                }
                if (view instanceof Barrier) {
                    ((Barrier) view).m210045b6();
                }
            }
            c0835lu.resolveLayoutDirection(motionLayout.getLayoutDirection());
            boolean z2 = MotionLayout.f44523i2;
            motionLayout.m210046a6(false, view, c0829lq2, c0835lu, sparseArray);
            if (c0825lm.m213869a7(view.getId()).f57928a2.f58026a2 == 1) {
                c0829lq2.f58121g0 = view.getVisibility();
            } else {
                c0829lq2.f58121g0 = c0825lm.m213869a7(view.getId()).f57928a2.f58025a1;
            }
            i2 = i3;
        }
        ArrayList arrayList3 = c0830lr.f58139h2;
        int size3 = arrayList3.size();
        int i4 = 0;
        while (i4 < size3) {
            Object obj2 = arrayList3.get(i4);
            i4++;
            C0829lq c0829lq3 = (C0829lq) obj2;
            if (c0829lq3 instanceof md1) {
                ConstraintHelper constraintHelper2 = (ConstraintHelper) c0829lq3.f58120f9;
                b40 b40Var = (b40) c0829lq3;
                constraintHelper2.mo209983b5(b40Var, sparseArray);
                md1 md1Var = (md1) b40Var;
                for (int i5 = 0; i5 < md1Var.f45712h3; i5++) {
                    C0829lq c0829lq4 = md1Var.f45711h2[i5];
                    if (c0829lq4 != null) {
                        c0829lq4.f58093d2 = true;
                    }
                }
            }
        }
    }

    public C0793kr(MotionLayout motionLayout) {
        this.f57713a6 = motionLayout;
        this.f57709a2 = new C0830lr();
        this.f57710a3 = new C0830lr();
        this.f57711a4 = null;
        this.f57712a5 = null;
    }
}
