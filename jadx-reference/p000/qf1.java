package p000;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.WindowInsets;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class qf1 extends vf1 {

    /* renamed from: a7 */
    public static boolean f59492a7 = false;

    /* renamed from: a8 */
    public static Method f59493a8;

    /* renamed from: a9 */
    public static Class f59494a9;

    /* renamed from: b0 */
    public static Field f59495b0;

    /* renamed from: b1 */
    public static Field f59496b1;

    /* renamed from: a2 */
    public final WindowInsets f59497a2;

    /* renamed from: a3 */
    public f60[] f59498a3;

    /* renamed from: a4 */
    public f60 f59499a4;

    /* renamed from: a5 */
    public xf1 f59500a5;

    /* renamed from: a6 */
    public f60 f59501a6;

    public qf1(xf1 xf1Var, WindowInsets windowInsets) {
        super(xf1Var);
        this.f59499a4 = null;
        this.f59497a2 = windowInsets;
    }

    @SuppressLint({"WrongConstant"})
    /* renamed from: b7 */
    private f60 m214386b7(int i, boolean z) {
        f60 f60VarM212747a0 = f60.f56153a4;
        for (int i2 = 1; i2 <= 256; i2 <<= 1) {
            if ((i & i2) != 0) {
                f60VarM212747a0 = f60.m212747a0(f60VarM212747a0, m214397b8(i2, z));
            }
        }
        return f60VarM212747a0;
    }

    /* renamed from: b9 */
    private f60 m214387b9() {
        xf1 xf1Var = this.f59500a5;
        return xf1Var != null ? xf1Var.f61102a0.mo214537a7() : f60.f56153a4;
    }

    /* renamed from: c0 */
    private f60 m214388c0(View view) throws IllegalAccessException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (Build.VERSION.SDK_INT >= 30) {
            throw new UnsupportedOperationException("getVisibleInsets() should not be called on API >= 30. Use WindowInsets.isVisible() instead.");
        }
        if (!f59492a7) {
            m214389c1();
        }
        Method method = f59493a8;
        if (method != null && f59494a9 != null && f59495b0 != null) {
            try {
                Object objInvoke = method.invoke(view, null);
                if (objInvoke != null) {
                    Rect rect = (Rect) f59495b0.get(f59496b1.get(objInvoke));
                    if (rect != null) {
                        return f60.m212748a1(rect.left, rect.top, rect.right, rect.bottom);
                    }
                }
            } catch (ReflectiveOperationException e) {
                e.getMessage();
            }
        }
        return null;
    }

    @SuppressLint({"PrivateApi"})
    /* renamed from: c1 */
    private static void m214389c1() throws ClassNotFoundException, SecurityException {
        try {
            f59493a8 = View.class.getDeclaredMethod("getViewRootImpl", null);
            Class<?> cls = Class.forName("android.view.View$AttachInfo");
            f59494a9 = cls;
            f59495b0 = cls.getDeclaredField("mVisibleInsets");
            f59496b1 = Class.forName("android.view.ViewRootImpl").getDeclaredField("mAttachInfo");
            f59495b0.setAccessible(true);
            f59496b1.setAccessible(true);
        } catch (ReflectiveOperationException e) {
            e.getMessage();
        }
        f59492a7 = true;
    }

    @Override // p000.vf1
    /* renamed from: a3 */
    public void mo214390a3(View view) throws IllegalAccessException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        f60 f60VarM214388c0 = m214388c0(view);
        if (f60VarM214388c0 == null) {
            f60VarM214388c0 = f60.f56153a4;
        }
        m214398c2(f60VarM214388c0);
    }

    @Override // p000.vf1
    /* renamed from: a5 */
    public f60 mo214391a5(int i) {
        return m214386b7(i, false);
    }

    @Override // p000.vf1
    /* renamed from: a9 */
    public final f60 mo214392a9() {
        if (this.f59499a4 == null) {
            WindowInsets windowInsets = this.f59497a2;
            this.f59499a4 = f60.m212748a1(windowInsets.getSystemWindowInsetLeft(), windowInsets.getSystemWindowInsetTop(), windowInsets.getSystemWindowInsetRight(), windowInsets.getSystemWindowInsetBottom());
        }
        return this.f59499a4;
    }

    @Override // p000.vf1
    /* renamed from: b1 */
    public xf1 mo214393b1(int i, int i2, int i3, int i4) {
        xf1 xf1VarM215170a6 = xf1.m215170a6(null, this.f59497a2);
        int i5 = Build.VERSION.SDK_INT;
        pf1 of1Var = i5 >= 30 ? new of1(xf1VarM215170a6) : i5 >= 29 ? new mf1(xf1VarM215170a6) : new lf1(xf1VarM215170a6);
        of1Var.mo213838a6(xf1.m215169a4(mo214392a9(), i, i2, i3, i4));
        of1Var.mo213837a4(xf1.m215169a4(mo214537a7(), i, i2, i3, i4));
        return of1Var.mo213836a1();
    }

    @Override // p000.vf1
    /* renamed from: b3 */
    public boolean mo214394b3() {
        return this.f59497a2.isRound();
    }

    @Override // p000.vf1
    /* renamed from: b4 */
    public void mo214395b4(f60[] f60VarArr) {
        this.f59498a3 = f60VarArr;
    }

    @Override // p000.vf1
    /* renamed from: b5 */
    public void mo214396b5(xf1 xf1Var) {
        this.f59500a5 = xf1Var;
    }

    /* renamed from: b8 */
    public f60 m214397b8(int i, boolean z) {
        f60 f60VarMo214537a7;
        int i2;
        if (i == 1) {
            return z ? f60.m212748a1(0, Math.max(m214387b9().f56155a1, mo214392a9().f56155a1), 0, 0) : f60.m212748a1(0, mo214392a9().f56155a1, 0, 0);
        }
        if (i == 2) {
            if (z) {
                f60 f60VarM214387b9 = m214387b9();
                f60 f60VarMo214537a72 = mo214537a7();
                return f60.m212748a1(Math.max(f60VarM214387b9.f56154a0, f60VarMo214537a72.f56154a0), 0, Math.max(f60VarM214387b9.f56156a2, f60VarMo214537a72.f56156a2), Math.max(f60VarM214387b9.f56157a3, f60VarMo214537a72.f56157a3));
            }
            f60 f60VarMo214392a9 = mo214392a9();
            xf1 xf1Var = this.f59500a5;
            f60VarMo214537a7 = xf1Var != null ? xf1Var.f61102a0.mo214537a7() : null;
            int iMin = f60VarMo214392a9.f56157a3;
            if (f60VarMo214537a7 != null) {
                iMin = Math.min(iMin, f60VarMo214537a7.f56157a3);
            }
            return f60.m212748a1(f60VarMo214392a9.f56154a0, 0, f60VarMo214392a9.f56156a2, iMin);
        }
        f60 f60Var = f60.f56153a4;
        if (i == 8) {
            f60[] f60VarArr = this.f59498a3;
            f60VarMo214537a7 = f60VarArr != null ? f60VarArr[b81.m210579c7(8)] : null;
            if (f60VarMo214537a7 != null) {
                return f60VarMo214537a7;
            }
            f60 f60VarMo214392a92 = mo214392a9();
            f60 f60VarM214387b92 = m214387b9();
            int i3 = f60VarMo214392a92.f56157a3;
            if (i3 > f60VarM214387b92.f56157a3) {
                return f60.m212748a1(0, 0, 0, i3);
            }
            f60 f60Var2 = this.f59501a6;
            return (f60Var2 == null || f60Var2.equals(f60Var) || (i2 = this.f59501a6.f56157a3) <= f60VarM214387b92.f56157a3) ? f60Var : f60.m212748a1(0, 0, 0, i2);
        }
        if (i == 16) {
            return mo214740a8();
        }
        if (i == 32) {
            return mo214739a6();
        }
        if (i == 64) {
            return mo214741b0();
        }
        if (i != 128) {
            return f60Var;
        }
        xf1 xf1Var2 = this.f59500a5;
        C1264tl c1264tlMo214612a4 = xf1Var2 != null ? xf1Var2.f61102a0.mo214612a4() : mo214612a4();
        if (c1264tlMo214612a4 == null) {
            return f60Var;
        }
        int i4 = Build.VERSION.SDK_INT;
        return f60.m212748a1(i4 >= 28 ? AbstractC1263tk.m214756a3(c1264tlMo214612a4.f60237a0) : 0, i4 >= 28 ? AbstractC1263tk.m214758a5(c1264tlMo214612a4.f60237a0) : 0, i4 >= 28 ? AbstractC1263tk.m214757a4(c1264tlMo214612a4.f60237a0) : 0, i4 >= 28 ? AbstractC1263tk.m214755a2(c1264tlMo214612a4.f60237a0) : 0);
    }

    /* renamed from: c2 */
    public void m214398c2(f60 f60Var) {
        this.f59501a6 = f60Var;
    }

    @Override // p000.vf1
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return Objects.equals(this.f59501a6, ((qf1) obj).f59501a6);
        }
        return false;
    }
}
