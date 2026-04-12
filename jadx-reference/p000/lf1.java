package p000;

import android.graphics.Rect;
import android.view.WindowInsets;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class lf1 extends pf1 {

    /* renamed from: a4 */
    public static Field f57906a4 = null;

    /* renamed from: a5 */
    public static boolean f57907a5 = false;

    /* renamed from: a6 */
    public static Constructor f57908a6 = null;

    /* renamed from: a7 */
    public static boolean f57909a7 = false;

    /* renamed from: a2 */
    public WindowInsets f57910a2;

    /* renamed from: a3 */
    public f60 f57911a3;

    public lf1() {
        this.f57910a2 = m213835a8();
    }

    /* renamed from: a8 */
    private static WindowInsets m213835a8() {
        if (!f57907a5) {
            try {
                f57906a4 = WindowInsets.class.getDeclaredField("CONSUMED");
            } catch (ReflectiveOperationException unused) {
            }
            f57907a5 = true;
        }
        Field field = f57906a4;
        if (field != null) {
            try {
                WindowInsets windowInsets = (WindowInsets) field.get(null);
                if (windowInsets != null) {
                    return new WindowInsets(windowInsets);
                }
            } catch (ReflectiveOperationException unused2) {
            }
        }
        if (!f57909a7) {
            try {
                f57908a6 = WindowInsets.class.getConstructor(Rect.class);
            } catch (ReflectiveOperationException unused3) {
            }
            f57909a7 = true;
        }
        Constructor constructor = f57908a6;
        if (constructor != null) {
            try {
                return (WindowInsets) constructor.newInstance(new Rect());
            } catch (ReflectiveOperationException unused4) {
            }
        }
        return null;
    }

    @Override // p000.pf1
    /* renamed from: a1 */
    public xf1 mo213836a1() {
        m214253a0();
        xf1 xf1VarM215170a6 = xf1.m215170a6(null, this.f57910a2);
        f60[] f60VarArr = this.f59225a1;
        vf1 vf1Var = xf1VarM215170a6.f61102a0;
        vf1Var.mo214395b4(f60VarArr);
        vf1Var.mo214539b6(this.f57911a3);
        return xf1VarM215170a6;
    }

    @Override // p000.pf1
    /* renamed from: a4 */
    public void mo213837a4(f60 f60Var) {
        this.f57911a3 = f60Var;
    }

    @Override // p000.pf1
    /* renamed from: a6 */
    public void mo213838a6(f60 f60Var) {
        WindowInsets windowInsets = this.f57910a2;
        if (windowInsets != null) {
            this.f57910a2 = windowInsets.replaceSystemWindowInsets(f60Var.f56154a0, f60Var.f56155a1, f60Var.f56156a2, f60Var.f56157a3);
        }
    }

    public lf1(xf1 xf1Var) {
        super(xf1Var);
        this.f57910a2 = xf1Var.m215175a5();
    }
}
