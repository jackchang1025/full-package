package p000;

import android.content.ClipDescription;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Trace;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour;
import androidx.lifecycle.C0076a0;
import androidx.lifecycle.Lifecycle$Event;
import androidx.lifecycle.LifecycleService;
import androidx.recyclerview.widget.RecyclerView;
import androidx.startup.R$string;
import androidx.startup.StartupException;
import androidx.work.impl.WorkDatabase_Impl;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import org.conscrypt.PSKKeyManager;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class pg1 implements InterfaceC1451yb, w50 {

    /* renamed from: a4 */
    public static volatile pg1 f59226a4;

    /* renamed from: a5 */
    public static final Object f59227a5 = new Object();

    /* renamed from: a0 */
    public final /* synthetic */ int f59228a0;

    /* renamed from: a1 */
    public Object f59229a1;

    /* renamed from: a2 */
    public Object f59230a2;

    /* renamed from: a3 */
    public Object f59231a3;

    public /* synthetic */ pg1() {
        this.f59228a0 = 6;
    }

    /* renamed from: c4 */
    public static pg1 m214254c4(Context context) {
        if (f59226a4 == null) {
            synchronized (f59227a5) {
                try {
                    if (f59226a4 == null) {
                        f59226a4 = new pg1(context);
                    }
                } finally {
                }
            }
        }
        return f59226a4;
    }

    /* renamed from: d2 */
    public static pg1 m214255d2(Context context, AttributeSet attributeSet, int[] iArr, int i) {
        return new pg1(context, context.obtainStyledAttributes(attributeSet, iArr, i, 0));
    }

    @Override // p000.InterfaceC1451yb
    /* renamed from: a0 */
    public int mo214256a0() {
        return ((ExtendedFloatingActionButton) this.f59231a3).f49488c7;
    }

    @Override // p000.w50
    /* renamed from: a1 */
    public ClipDescription mo214257a1() {
        return (ClipDescription) this.f59230a2;
    }

    @Override // p000.InterfaceC1451yb
    /* renamed from: a2 */
    public int mo214258a2() {
        return ((ExtendedFloatingActionButton) this.f59231a3).f49487c6;
    }

    @Override // p000.w50
    /* renamed from: a3 */
    public Object mo214259a3() {
        return null;
    }

    @Override // p000.w50
    /* renamed from: a4 */
    public Uri mo214260a4() {
        return (Uri) this.f59229a1;
    }

    @Override // p000.w50
    /* renamed from: a6 */
    public Uri mo214262a6() {
        return (Uri) this.f59231a3;
    }

    @Override // p000.InterfaceC1451yb
    /* renamed from: a7 */
    public int mo214263a7() {
        int i = ((ExtendedFloatingActionButton) this.f59231a3).f49494d3;
        return i == -1 ? ((C1217sc) this.f59229a1).mo214263a7() : (i == 0 || i == -2) ? ((C1434xx) this.f59230a2).mo214263a7() : i;
    }

    @Override // p000.InterfaceC1451yb
    /* renamed from: a8 */
    public ViewGroup.LayoutParams mo214264a8() {
        ExtendedFloatingActionButton extendedFloatingActionButton = (ExtendedFloatingActionButton) this.f59231a3;
        int i = extendedFloatingActionButton.f49494d3;
        if (i == 0) {
            i = -2;
        }
        int i2 = extendedFloatingActionButton.f49495d4;
        return new ViewGroup.LayoutParams(i, i2 != 0 ? i2 : -2);
    }

    /* renamed from: a9 */
    public void m214265a9(double d, float f) {
        int length = ((float[]) this.f59229a1).length + 1;
        int iBinarySearch = Arrays.binarySearch((double[]) this.f59230a2, d);
        if (iBinarySearch < 0) {
            iBinarySearch = (-iBinarySearch) - 1;
        }
        this.f59230a2 = Arrays.copyOf((double[]) this.f59230a2, length);
        this.f59229a1 = Arrays.copyOf((float[]) this.f59229a1, length);
        this.f59231a3 = new double[length];
        double[] dArr = (double[]) this.f59230a2;
        System.arraycopy(dArr, iBinarySearch, dArr, iBinarySearch + 1, (length - iBinarySearch) - 1);
        ((double[]) this.f59230a2)[iBinarySearch] = d;
        ((float[]) this.f59229a1)[iBinarySearch] = f;
    }

    /* renamed from: b0 */
    public void m214266b0(View view, int i, boolean z) {
        RecyclerView recyclerView = ((fq0) this.f59229a1).f56313a0;
        int childCount = i < 0 ? recyclerView.getChildCount() : m214280c5(i);
        ((C0583hj) this.f59230a2).m213044a4(childCount, z);
        if (z) {
            m214285d0(view);
        }
        recyclerView.addView(view, childCount);
        RecyclerView.m210345d5(view);
        ArrayList arrayList = recyclerView.f45278c4;
        if (arrayList != null) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                ((cc1) recyclerView.f45278c4.get(size)).getClass();
                qq0 qq0Var = (qq0) view.getLayoutParams();
                if (((ViewGroup.MarginLayoutParams) qq0Var).width != -1 || ((ViewGroup.MarginLayoutParams) qq0Var).height != -1) {
                    throw new IllegalStateException("Pages must fill the whole ViewPager2 (use match_parent)");
                }
            }
        }
    }

    /* renamed from: b1 */
    public void m214267b1(View view, int i, ViewGroup.LayoutParams layoutParams, boolean z) {
        RecyclerView recyclerView = ((fq0) this.f59229a1).f56313a0;
        int childCount = i < 0 ? recyclerView.getChildCount() : m214280c5(i);
        ((C0583hj) this.f59230a2).m213044a4(childCount, z);
        if (z) {
            m214285d0(view);
        }
        dr0 dr0VarM210345d5 = RecyclerView.m210345d5(view);
        if (dr0VarM210345d5 != null) {
            if (!dr0VarM210345d5.m212629a9() && !dr0VarM210345d5.m212634b4()) {
                throw new IllegalArgumentException("Called attach on a child which is not detached: " + dr0VarM210345d5 + recyclerView.m210365c5());
            }
            dr0VarM210345d5.f55858a9 &= -257;
        }
        recyclerView.attachViewToParent(view, childCount, layoutParams);
    }

    /* renamed from: b2 */
    public void m214268b2(String str) {
        WorkDatabase_Impl workDatabase_Impl = (WorkDatabase_Impl) this.f59229a1;
        workDatabase_Impl.m212857a1();
        w31 w31Var = (w31) this.f59230a2;
        u00 u00VarM210428a0 = w31Var.m210428a0();
        if (str == null) {
            u00VarM210428a0.mo213343a9(1);
        } else {
            u00VarM210428a0.mo213341a6(1, str);
        }
        workDatabase_Impl.m212858a2();
        try {
            u00VarM210428a0.m214812a0();
            workDatabase_Impl.m212863b2();
        } finally {
            workDatabase_Impl.m212860a9();
            w31Var.m210431a3(u00VarM210428a0);
        }
    }

    /* renamed from: b3 */
    public void m214269b3(int i) {
        dr0 dr0VarM210345d5;
        int iM214280c5 = m214280c5(i);
        ((C0583hj) this.f59230a2).m213045a5(iM214280c5);
        RecyclerView recyclerView = ((fq0) this.f59229a1).f56313a0;
        View childAt = recyclerView.getChildAt(iM214280c5);
        if (childAt != null && (dr0VarM210345d5 = RecyclerView.m210345d5(childAt)) != null) {
            if (dr0VarM210345d5.m212629a9() && !dr0VarM210345d5.m212634b4()) {
                throw new IllegalArgumentException("called detach on an already detached child " + dr0VarM210345d5 + recyclerView.m210365c5());
            }
            dr0VarM210345d5.m212620a0(PSKKeyManager.MAX_KEY_LENGTH_BYTES);
        }
        recyclerView.detachViewFromParent(iM214280c5);
    }

    /* renamed from: b4 */
    public void m214270b4(Bundle bundle) throws ClassNotFoundException {
        HashSet hashSet = (HashSet) this.f59230a2;
        String string = ((Context) this.f59231a3).getString(R$string.androidx_startup);
        if (bundle != null) {
            try {
                HashSet hashSet2 = new HashSet();
                for (String str : bundle.keySet()) {
                    if (string.equals(bundle.getString(str, null))) {
                        Class<?> cls = Class.forName(str);
                        if (r50.class.isAssignableFrom(cls)) {
                            hashSet.add(cls);
                        }
                    }
                }
                Iterator it = hashSet.iterator();
                while (it.hasNext()) {
                    m214271b5((Class) it.next(), hashSet2);
                }
            } catch (ClassNotFoundException e) {
                throw new StartupException(e);
            }
        }
    }

    /* renamed from: b5 */
    public Object m214271b5(Class cls, HashSet hashSet) {
        Object objMo210117a1;
        HashMap map = (HashMap) this.f59229a1;
        if (AbstractC1117qo.m214444e0()) {
            try {
                Trace.beginSection(cls.getSimpleName());
            } catch (Throwable th) {
                Trace.endSection();
                throw th;
            }
        }
        if (hashSet.contains(cls)) {
            throw new IllegalStateException("Cannot initialize " + cls.getName() + ". Cycle detected.");
        }
        if (map.containsKey(cls)) {
            objMo210117a1 = map.get(cls);
        } else {
            hashSet.add(cls);
            try {
                r50 r50Var = (r50) cls.getDeclaredConstructor(null).newInstance(null);
                List<Class> listMo210116a0 = r50Var.mo210116a0();
                if (!listMo210116a0.isEmpty()) {
                    for (Class cls2 : listMo210116a0) {
                        if (!map.containsKey(cls2)) {
                            m214271b5(cls2, hashSet);
                        }
                    }
                }
                objMo210117a1 = r50Var.mo210117a1((Context) this.f59231a3);
                hashSet.remove(cls);
                map.put(cls, objMo210117a1);
            } catch (Throwable th2) {
                throw new StartupException(th2);
            }
        }
        Trace.endSection();
        return objMo210117a1;
    }

    /* renamed from: b6 */
    public void m214272b6(Runnable runnable) {
        ((ExecutorC0034an) this.f59229a1).execute(runnable);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: b7 */
    public ib1 m214273b7(Class cls, String str) {
        ib1 ib1VarMo213203a0;
        nb1 nb1Var = (nb1) this.f59230a2;
        t60.m214695b6(str, "key");
        rb1 rb1Var = (rb1) this.f59229a1;
        rb1Var.getClass();
        LinkedHashMap linkedHashMap = rb1Var.f59667a0;
        ib1 ib1Var = (ib1) linkedHashMap.get(str);
        if (!cls.isInstance(ib1Var)) {
            gh0 gh0Var = new gh0((AbstractC0926nu) this.f59231a3);
            gh0Var.m212952a1(pb1.f59189a2, str);
            try {
                ib1VarMo213203a0 = nb1Var.mo213829a1(cls, gh0Var);
            } catch (AbstractMethodError unused) {
                ib1VarMo213203a0 = nb1Var.mo213203a0(cls);
            }
            t60.m214695b6(ib1VarMo213203a0, "viewModel");
            ib1 ib1Var2 = (ib1) linkedHashMap.put(str, ib1VarMo213203a0);
            if (ib1Var2 != null) {
                ib1Var2.mo213148a1();
            }
            return ib1VarMo213203a0;
        }
        qb1 qb1Var = nb1Var instanceof qb1 ? (qb1) nb1Var : null;
        if (qb1Var != null) {
            t60.m214692b3(ib1Var);
            zt0 zt0Var = (zt0) qb1Var;
            kg1 kg1Var = zt0Var.f61585a3;
            if (kg1Var != null) {
                vt0 vt0Var = zt0Var.f61586a4;
                t60.m214692b3(vt0Var);
                b81.m210563a3(ib1Var, vt0Var, kg1Var);
            }
        }
        t60.m214693b4(ib1Var, "null cannot be cast to non-null type T of androidx.lifecycle.ViewModelProvider.get");
        return ib1Var;
    }

    /* renamed from: b8 */
    public View m214274b8(int i) {
        return ((fq0) this.f59229a1).f56313a0.getChildAt(m214280c5(i));
    }

    /* renamed from: b9 */
    public int m214275b9() {
        return ((fq0) this.f59229a1).f56313a0.getChildCount() - ((ArrayList) this.f59231a3).size();
    }

    /* renamed from: c0 */
    public ColorStateList m214276c0(int i) {
        int resourceId;
        ColorStateList colorStateListM214426c2;
        TypedArray typedArray = (TypedArray) this.f59230a2;
        return (!typedArray.hasValue(i) || (resourceId = typedArray.getResourceId(i, 0)) == 0 || (colorStateListM214426c2 = AbstractC1117qo.m214426c2((Context) this.f59229a1, resourceId)) == null) ? typedArray.getColorStateList(i) : colorStateListM214426c2;
    }

    /* renamed from: c1 */
    public Drawable m214277c1(int i) {
        int resourceId;
        TypedArray typedArray = (TypedArray) this.f59230a2;
        return (!typedArray.hasValue(i) || (resourceId = typedArray.getResourceId(i, 0)) == 0) ? typedArray.getDrawable(i) : b81.m210576b7((Context) this.f59229a1, resourceId);
    }

    /* renamed from: c2 */
    public Drawable m214278c2(int i) {
        int resourceId;
        Drawable drawableM214662a3;
        if (!((TypedArray) this.f59230a2).hasValue(i) || (resourceId = ((TypedArray) this.f59230a2).getResourceId(i, 0)) == 0) {
            return null;
        }
        C1398x1 c1398x1M215095a0 = C1398x1.m215095a0();
        Context context = (Context) this.f59229a1;
        synchronized (c1398x1M215095a0) {
            drawableM214662a3 = c1398x1M215095a0.f60990a0.m214662a3(context, resourceId, true);
        }
        return drawableM214662a3;
    }

    /* renamed from: c3 */
    public Typeface m214279c3(int i, int i2, C1449y9 c1449y9) {
        int resourceId = ((TypedArray) this.f59230a2).getResourceId(i, 0);
        if (resourceId == 0) {
            return null;
        }
        if (((TypedValue) this.f59231a3) == null) {
            this.f59231a3 = new TypedValue();
        }
        Context context = (Context) this.f59229a1;
        TypedValue typedValue = (TypedValue) this.f59231a3;
        ThreadLocal threadLocal = yr0.f61364a0;
        if (context.isRestricted()) {
            return null;
        }
        return yr0.m215305a1(context, resourceId, typedValue, i2, c1449y9, true, false);
    }

    /* renamed from: c5 */
    public int m214280c5(int i) {
        C0583hj c0583hj = (C0583hj) this.f59230a2;
        if (i < 0) {
            return -1;
        }
        int childCount = ((fq0) this.f59229a1).f56313a0.getChildCount();
        int i2 = i;
        while (i2 < childCount) {
            int iM213041a1 = i - (i2 - c0583hj.m213041a1(i2));
            if (iM213041a1 == 0) {
                while (c0583hj.m213043a3(i2)) {
                    i2++;
                }
                return i2;
            }
            i2 += iM213041a1;
        }
        return -1;
    }

    /* renamed from: c6 */
    public double m214281c6(double d) {
        if (d < 0.0d) {
            d = 0.0d;
        } else if (d > 1.0d) {
            d = 1.0d;
        }
        int iBinarySearch = Arrays.binarySearch((double[]) this.f59230a2, d);
        if (iBinarySearch > 0) {
            return 1.0d;
        }
        if (iBinarySearch == 0) {
            return 0.0d;
        }
        int i = -iBinarySearch;
        int i2 = i - 1;
        float[] fArr = (float[]) this.f59229a1;
        float f = fArr[i2];
        int i3 = i - 2;
        float f2 = fArr[i3];
        double[] dArr = (double[]) this.f59230a2;
        double d2 = dArr[i2];
        double d3 = dArr[i3];
        double d4 = (f - f2) / (d2 - d3);
        return ((((d * d) - (d3 * d3)) * d4) / 2.0d) + ((d - d3) * (f2 - (d4 * d3))) + ((double[]) this.f59231a3)[i3];
    }

    /* renamed from: c7 */
    public View m214282c7(int i) {
        return ((fq0) this.f59229a1).f56313a0.getChildAt(i);
    }

    /* renamed from: c8 */
    public int m214283c8() {
        return ((fq0) this.f59229a1).f56313a0.getChildCount();
    }

    /* renamed from: c9 */
    public double m214284c9(double d, double d2) {
        return Math.sin((m214281c6(d) + d2) * 6.283185307179586d);
    }

    /* renamed from: d0 */
    public void m214285d0(View view) {
        ((ArrayList) this.f59231a3).add(view);
        fq0 fq0Var = (fq0) this.f59229a1;
        dr0 dr0VarM210345d5 = RecyclerView.m210345d5(view);
        if (dr0VarM210345d5 != null) {
            View view2 = dr0VarM210345d5.f55849a0;
            RecyclerView recyclerView = fq0Var.f56313a0;
            int i = dr0VarM210345d5.f55865b6;
            if (i != -1) {
                dr0VarM210345d5.f55864b5 = i;
            } else {
                WeakHashMap weakHashMap = xa1.f61054a0;
                dr0VarM210345d5.f55864b5 = fa1.m212765a2(view2);
            }
            if (recyclerView.m210376d8()) {
                dr0VarM210345d5.f55865b6 = 4;
                recyclerView.f45319g5.add(dr0VarM210345d5);
            } else {
                WeakHashMap weakHashMap2 = xa1.f61054a0;
                fa1.m212781b8(view2, 4);
            }
        }
    }

    /* renamed from: d1 */
    public boolean m214286d1(int i, C0813la c0813la, C0829lq c0829lq) {
        C0418dj c0418dj = (C0418dj) this.f59230a2;
        ConstraintWidget$DimensionBehaviour[] constraintWidget$DimensionBehaviourArr = c0829lq.f58107e6;
        int[] iArr = c0829lq.f58080b9;
        c0418dj.f55819a0 = constraintWidget$DimensionBehaviourArr[0];
        c0418dj.f55820a1 = constraintWidget$DimensionBehaviourArr[1];
        c0418dj.f55821a2 = c0829lq.m213891b7();
        c0418dj.f55822a3 = c0829lq.m213887b1();
        c0418dj.f55827a8 = false;
        c0418dj.f55828a9 = i;
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour = c0418dj.f55819a0;
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour2 = ConstraintWidget$DimensionBehaviour.f44426a2;
        boolean z = constraintWidget$DimensionBehaviour == constraintWidget$DimensionBehaviour2;
        boolean z2 = c0418dj.f55820a1 == constraintWidget$DimensionBehaviour2;
        boolean z3 = z && c0829lq.f58111f0 > 0.0f;
        boolean z4 = z2 && c0829lq.f58111f0 > 0.0f;
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour3 = ConstraintWidget$DimensionBehaviour.f44424a0;
        if (z3 && iArr[0] == 4) {
            c0418dj.f55819a0 = constraintWidget$DimensionBehaviour3;
        }
        if (z4 && iArr[1] == 4) {
            c0418dj.f55820a1 = constraintWidget$DimensionBehaviour3;
        }
        c0813la.m213800a1(c0829lq, c0418dj);
        c0829lq.m213911e1(c0418dj.f55823a4);
        c0829lq.m213908d8(c0418dj.f55824a5);
        c0829lq.f58091d0 = c0418dj.f55826a7;
        c0829lq.m213905d5(c0418dj.f55825a6);
        c0418dj.f55828a9 = 0;
        return c0418dj.f55827a8;
    }

    /* renamed from: d3 */
    public void m214287d3(Lifecycle$Event lifecycle$Event) {
        vz0 vz0Var = (vz0) this.f59231a3;
        if (vz0Var != null) {
            vz0Var.run();
        }
        vz0 vz0Var2 = new vz0((C0076a0) this.f59229a1, lifecycle$Event);
        this.f59231a3 = vz0Var2;
        ((Handler) this.f59230a2).postAtFrontOfQueue(vz0Var2);
    }

    /* renamed from: d4 */
    public void m214288d4() {
        ((TypedArray) this.f59230a2).recycle();
    }

    /* renamed from: d5 */
    public void m214289d5(C0830lr c0830lr, int i, int i2, int i3) {
        int i4 = c0830lr.f58116f5;
        int i5 = c0830lr.f58117f6;
        c0830lr.f58116f5 = 0;
        c0830lr.f58117f6 = 0;
        c0830lr.m213911e1(i2);
        c0830lr.m213908d8(i3);
        if (i4 < 0) {
            c0830lr.f58116f5 = 0;
        } else {
            c0830lr.f58116f5 = i4;
        }
        if (i5 < 0) {
            c0830lr.f58117f6 = 0;
        } else {
            c0830lr.f58117f6 = i5;
        }
        C0830lr c0830lr2 = (C0830lr) this.f59231a3;
        c0830lr2.f58142h5 = i;
        c0830lr2.m213924e7();
    }

    /* renamed from: d6 */
    public void m214290d6(View view) {
        if (((ArrayList) this.f59231a3).remove(view)) {
            fq0 fq0Var = (fq0) this.f59229a1;
            dr0 dr0VarM210345d5 = RecyclerView.m210345d5(view);
            if (dr0VarM210345d5 != null) {
                RecyclerView recyclerView = fq0Var.f56313a0;
                int i = dr0VarM210345d5.f55864b5;
                if (recyclerView.m210376d8()) {
                    dr0VarM210345d5.f55865b6 = i;
                    recyclerView.f45319g5.add(dr0VarM210345d5);
                } else {
                    View view2 = dr0VarM210345d5.f55849a0;
                    WeakHashMap weakHashMap = xa1.f61054a0;
                    fa1.m212781b8(view2, i);
                }
                dr0VarM210345d5.f55864b5 = 0;
            }
        }
    }

    /* renamed from: d7 */
    public void m214291d7(C0830lr c0830lr) {
        ArrayList arrayList = (ArrayList) this.f59229a1;
        arrayList.clear();
        int size = c0830lr.f58139h2.size();
        for (int i = 0; i < size; i++) {
            C0829lq c0829lq = (C0829lq) c0830lr.f58139h2.get(i);
            ConstraintWidget$DimensionBehaviour[] constraintWidget$DimensionBehaviourArr = c0829lq.f58107e6;
            ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour = constraintWidget$DimensionBehaviourArr[0];
            ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour2 = ConstraintWidget$DimensionBehaviour.f44426a2;
            if (constraintWidget$DimensionBehaviour == constraintWidget$DimensionBehaviour2 || constraintWidget$DimensionBehaviourArr[1] == constraintWidget$DimensionBehaviour2) {
                arrayList.add(c0829lq);
            }
        }
        c0830lr.f58141h4.f59956a1 = true;
    }

    @Override // p000.InterfaceC1451yb
    public int getHeight() {
        int i = ((ExtendedFloatingActionButton) this.f59231a3).f49495d4;
        return i == -1 ? ((C1217sc) this.f59229a1).getHeight() : (i == 0 || i == -2) ? ((C1434xx) this.f59230a2).f61200a1.getMeasuredHeight() : i;
    }

    public String toString() {
        switch (this.f59228a0) {
            case 3:
                return ((C0583hj) this.f59230a2).toString() + ", hidden list:" + ((ArrayList) this.f59231a3).size();
            case 6:
                return "pos =" + Arrays.toString((double[]) this.f59230a2) + " period=" + Arrays.toString((float[]) this.f59229a1);
            default:
                return super.toString();
        }
    }

    public pg1(LifecycleService lifecycleService) {
        this.f59228a0 = 7;
        this.f59229a1 = new C0076a0(lifecycleService, true);
        this.f59230a2 = new Handler();
    }

    public pg1(WorkDatabase_Impl workDatabase_Impl) {
        this.f59228a0 = 0;
        this.f59229a1 = workDatabase_Impl;
        new C1216sb(workDatabase_Impl, 4);
        this.f59230a2 = new w31(workDatabase_Impl, 2);
        this.f59231a3 = new w31(workDatabase_Impl, 3);
    }

    public pg1(ExecutorService executorService) {
        this.f59228a0 = 10;
        this.f59230a2 = new Handler(Looper.getMainLooper());
        this.f59231a3 = new mg1(this);
        this.f59229a1 = new ExecutorC0034an(executorService);
    }

    public pg1(rb1 rb1Var, nb1 nb1Var, AbstractC0926nu abstractC0926nu) {
        this.f59228a0 = 9;
        t60.m214695b6(rb1Var, "store");
        t60.m214695b6(nb1Var, "factory");
        t60.m214695b6(abstractC0926nu, "defaultCreationExtras");
        this.f59229a1 = rb1Var;
        this.f59230a2 = nb1Var;
        this.f59231a3 = abstractC0926nu;
    }

    @Override // p000.w50
    /* renamed from: a5 */
    public void mo214261a5() {
    }

    public pg1(fq0 fq0Var) {
        this.f59228a0 = 3;
        this.f59229a1 = fq0Var;
        this.f59230a2 = new C0583hj();
        this.f59231a3 = new ArrayList();
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public pg1(rb1 rb1Var, nb1 nb1Var) {
        this(rb1Var, nb1Var, C0924ns.f58691a1);
        this.f59228a0 = 9;
        t60.m214695b6(rb1Var, "store");
        t60.m214695b6(nb1Var, "factory");
    }

    public pg1(Uri uri, ClipDescription clipDescription, Uri uri2) {
        this.f59228a0 = 5;
        this.f59229a1 = uri;
        this.f59230a2 = clipDescription;
        this.f59231a3 = uri2;
    }

    public pg1(Context context, TypedArray typedArray) {
        this.f59228a0 = 8;
        this.f59229a1 = context;
        this.f59230a2 = typedArray;
    }

    public pg1(C0830lr c0830lr) {
        this.f59228a0 = 2;
        this.f59229a1 = new ArrayList();
        this.f59230a2 = new C0418dj();
        this.f59231a3 = c0830lr;
    }

    public pg1(Context context) {
        this.f59228a0 = 1;
        this.f59231a3 = context.getApplicationContext();
        this.f59230a2 = new HashSet();
        this.f59229a1 = new HashMap();
    }

    public pg1(ExtendedFloatingActionButton extendedFloatingActionButton, C1217sc c1217sc, C1434xx c1434xx) {
        this.f59228a0 = 4;
        this.f59231a3 = extendedFloatingActionButton;
        this.f59229a1 = c1217sc;
        this.f59230a2 = c1434xx;
    }
}
