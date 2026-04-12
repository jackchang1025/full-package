package p000;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.view.View;
import java.util.ArrayList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: rd */
/* loaded from: classes.dex */
public final class C1176rd extends lq0 {

    /* renamed from: b8 */
    public static TimeInterpolator f59673b8;

    /* renamed from: a6 */
    public boolean f59674a6;

    /* renamed from: a7 */
    public ArrayList f59675a7;

    /* renamed from: a8 */
    public ArrayList f59676a8;

    /* renamed from: a9 */
    public ArrayList f59677a9;

    /* renamed from: b0 */
    public ArrayList f59678b0;

    /* renamed from: b1 */
    public ArrayList f59679b1;

    /* renamed from: b2 */
    public ArrayList f59680b2;

    /* renamed from: b3 */
    public ArrayList f59681b3;

    /* renamed from: b4 */
    public ArrayList f59682b4;

    /* renamed from: b5 */
    public ArrayList f59683b5;

    /* renamed from: b6 */
    public ArrayList f59684b6;

    /* renamed from: b7 */
    public ArrayList f59685b7;

    /* renamed from: a7 */
    public static void m214527a7(ArrayList arrayList) {
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            ((dr0) arrayList.get(size)).f55849a0.animate().cancel();
        }
    }

    @Override // p000.lq0
    /* renamed from: a0 */
    public final boolean mo213915a0(dr0 dr0Var, dr0 dr0Var2, fj0 fj0Var, fj0 fj0Var2) {
        int i;
        int i2;
        int i3 = fj0Var.f56279a0;
        int i4 = fj0Var.f56280a1;
        if (dr0Var2.m212634b4()) {
            int i5 = fj0Var.f56279a0;
            i2 = fj0Var.f56280a1;
            i = i5;
        } else {
            i = fj0Var2.f56279a0;
            i2 = fj0Var2.f56280a1;
        }
        if (dr0Var == dr0Var2) {
            return m214528a6(dr0Var, i3, i4, i, i2);
        }
        View view = dr0Var.f55849a0;
        float translationX = view.getTranslationX();
        float translationY = view.getTranslationY();
        float alpha = view.getAlpha();
        m214532b1(dr0Var);
        view.setTranslationX(translationX);
        view.setTranslationY(translationY);
        view.setAlpha(alpha);
        View view2 = dr0Var2.f55849a0;
        m214532b1(dr0Var2);
        view2.setTranslationX(-((int) ((i - i3) - translationX)));
        view2.setTranslationY(-((int) ((i2 - i4) - translationY)));
        view2.setAlpha(0.0f);
        ArrayList arrayList = this.f59678b0;
        C1174rb c1174rb = new C1174rb();
        c1174rb.f59660a0 = dr0Var;
        c1174rb.f59661a1 = dr0Var2;
        c1174rb.f59662a2 = i3;
        c1174rb.f59663a3 = i4;
        c1174rb.f59664a4 = i;
        c1174rb.f59665a5 = i2;
        arrayList.add(c1174rb);
        return true;
    }

    @Override // p000.lq0
    /* renamed from: a3 */
    public final void mo213917a3(dr0 dr0Var) {
        ArrayList arrayList = this.f59679b1;
        ArrayList arrayList2 = this.f59680b2;
        ArrayList arrayList3 = this.f59681b3;
        View view = dr0Var.f55849a0;
        view.animate().cancel();
        ArrayList arrayList4 = this.f59677a9;
        int size = arrayList4.size();
        while (true) {
            size--;
            if (size < 0) {
                break;
            }
            if (((C1175rc) arrayList4.get(size)).f59668a0 == dr0Var) {
                view.setTranslationY(0.0f);
                view.setTranslationX(0.0f);
                m213916a2(dr0Var);
                arrayList4.remove(size);
            }
        }
        m214530a9(this.f59678b0, dr0Var);
        if (this.f59675a7.remove(dr0Var)) {
            view.setAlpha(1.0f);
            m213916a2(dr0Var);
        }
        if (this.f59676a8.remove(dr0Var)) {
            view.setAlpha(1.0f);
            m213916a2(dr0Var);
        }
        for (int size2 = arrayList3.size() - 1; size2 >= 0; size2--) {
            ArrayList arrayList5 = (ArrayList) arrayList3.get(size2);
            m214530a9(arrayList5, dr0Var);
            if (arrayList5.isEmpty()) {
                arrayList3.remove(size2);
            }
        }
        for (int size3 = arrayList2.size() - 1; size3 >= 0; size3--) {
            ArrayList arrayList6 = (ArrayList) arrayList2.get(size3);
            int size4 = arrayList6.size() - 1;
            while (true) {
                if (size4 < 0) {
                    break;
                }
                if (((C1175rc) arrayList6.get(size4)).f59668a0 == dr0Var) {
                    view.setTranslationY(0.0f);
                    view.setTranslationX(0.0f);
                    m213916a2(dr0Var);
                    arrayList6.remove(size4);
                    if (arrayList6.isEmpty()) {
                        arrayList2.remove(size3);
                    }
                } else {
                    size4--;
                }
            }
        }
        for (int size5 = arrayList.size() - 1; size5 >= 0; size5--) {
            ArrayList arrayList7 = (ArrayList) arrayList.get(size5);
            if (arrayList7.remove(dr0Var)) {
                view.setAlpha(1.0f);
                m213916a2(dr0Var);
                if (arrayList7.isEmpty()) {
                    arrayList.remove(size5);
                }
            }
        }
        this.f59684b6.remove(dr0Var);
        this.f59682b4.remove(dr0Var);
        this.f59685b7.remove(dr0Var);
        this.f59683b5.remove(dr0Var);
        m214529a8();
    }

    @Override // p000.lq0
    /* renamed from: a4 */
    public final void mo213918a4() {
        ArrayList arrayList = this.f59681b3;
        ArrayList arrayList2 = this.f59679b1;
        ArrayList arrayList3 = this.f59680b2;
        ArrayList arrayList4 = this.f59678b0;
        ArrayList arrayList5 = this.f59676a8;
        ArrayList arrayList6 = this.f59675a7;
        ArrayList arrayList7 = this.f59677a9;
        int size = arrayList7.size();
        while (true) {
            size--;
            if (size < 0) {
                break;
            }
            C1175rc c1175rc = (C1175rc) arrayList7.get(size);
            View view = c1175rc.f59668a0.f55849a0;
            view.setTranslationY(0.0f);
            view.setTranslationX(0.0f);
            m213916a2(c1175rc.f59668a0);
            arrayList7.remove(size);
        }
        for (int size2 = arrayList6.size() - 1; size2 >= 0; size2--) {
            m213916a2((dr0) arrayList6.get(size2));
            arrayList6.remove(size2);
        }
        int size3 = arrayList5.size();
        while (true) {
            size3--;
            if (size3 < 0) {
                break;
            }
            dr0 dr0Var = (dr0) arrayList5.get(size3);
            dr0Var.f55849a0.setAlpha(1.0f);
            m213916a2(dr0Var);
            arrayList5.remove(size3);
        }
        for (int size4 = arrayList4.size() - 1; size4 >= 0; size4--) {
            C1174rb c1174rb = (C1174rb) arrayList4.get(size4);
            dr0 dr0Var2 = c1174rb.f59660a0;
            if (dr0Var2 != null) {
                m214531b0(c1174rb, dr0Var2);
            }
            dr0 dr0Var3 = c1174rb.f59661a1;
            if (dr0Var3 != null) {
                m214531b0(c1174rb, dr0Var3);
            }
        }
        arrayList4.clear();
        if (mo213919a5()) {
            for (int size5 = arrayList3.size() - 1; size5 >= 0; size5--) {
                ArrayList arrayList8 = (ArrayList) arrayList3.get(size5);
                for (int size6 = arrayList8.size() - 1; size6 >= 0; size6--) {
                    C1175rc c1175rc2 = (C1175rc) arrayList8.get(size6);
                    View view2 = c1175rc2.f59668a0.f55849a0;
                    view2.setTranslationY(0.0f);
                    view2.setTranslationX(0.0f);
                    m213916a2(c1175rc2.f59668a0);
                    arrayList8.remove(size6);
                    if (arrayList8.isEmpty()) {
                        arrayList3.remove(arrayList8);
                    }
                }
            }
            for (int size7 = arrayList2.size() - 1; size7 >= 0; size7--) {
                ArrayList arrayList9 = (ArrayList) arrayList2.get(size7);
                for (int size8 = arrayList9.size() - 1; size8 >= 0; size8--) {
                    dr0 dr0Var4 = (dr0) arrayList9.get(size8);
                    dr0Var4.f55849a0.setAlpha(1.0f);
                    m213916a2(dr0Var4);
                    arrayList9.remove(size8);
                    if (arrayList9.isEmpty()) {
                        arrayList2.remove(arrayList9);
                    }
                }
            }
            for (int size9 = arrayList.size() - 1; size9 >= 0; size9--) {
                ArrayList arrayList10 = (ArrayList) arrayList.get(size9);
                for (int size10 = arrayList10.size() - 1; size10 >= 0; size10--) {
                    C1174rb c1174rb2 = (C1174rb) arrayList10.get(size10);
                    dr0 dr0Var5 = c1174rb2.f59660a0;
                    if (dr0Var5 != null) {
                        m214531b0(c1174rb2, dr0Var5);
                    }
                    dr0 dr0Var6 = c1174rb2.f59661a1;
                    if (dr0Var6 != null) {
                        m214531b0(c1174rb2, dr0Var6);
                    }
                    if (arrayList10.isEmpty()) {
                        arrayList.remove(arrayList10);
                    }
                }
            }
            m214527a7(this.f59684b6);
            m214527a7(this.f59683b5);
            m214527a7(this.f59682b4);
            m214527a7(this.f59685b7);
            ArrayList arrayList11 = this.f58134a1;
            if (arrayList11.size() > 0) {
                arrayList11.get(0).getClass();
                throw new ClassCastException();
            }
            arrayList11.clear();
        }
    }

    @Override // p000.lq0
    /* renamed from: a5 */
    public final boolean mo213919a5() {
        return (this.f59676a8.isEmpty() && this.f59678b0.isEmpty() && this.f59677a9.isEmpty() && this.f59675a7.isEmpty() && this.f59683b5.isEmpty() && this.f59684b6.isEmpty() && this.f59682b4.isEmpty() && this.f59685b7.isEmpty() && this.f59680b2.isEmpty() && this.f59679b1.isEmpty() && this.f59681b3.isEmpty()) ? false : true;
    }

    /* renamed from: a6 */
    public final boolean m214528a6(dr0 dr0Var, int i, int i2, int i3, int i4) {
        View view = dr0Var.f55849a0;
        int translationX = i + ((int) view.getTranslationX());
        int translationY = i2 + ((int) dr0Var.f55849a0.getTranslationY());
        m214532b1(dr0Var);
        int i5 = i3 - translationX;
        int i6 = i4 - translationY;
        if (i5 == 0 && i6 == 0) {
            m213916a2(dr0Var);
            return false;
        }
        if (i5 != 0) {
            view.setTranslationX(-i5);
        }
        if (i6 != 0) {
            view.setTranslationY(-i6);
        }
        ArrayList arrayList = this.f59677a9;
        C1175rc c1175rc = new C1175rc();
        c1175rc.f59668a0 = dr0Var;
        c1175rc.f59669a1 = translationX;
        c1175rc.f59670a2 = translationY;
        c1175rc.f59671a3 = i3;
        c1175rc.f59672a4 = i4;
        arrayList.add(c1175rc);
        return true;
    }

    /* renamed from: a8 */
    public final void m214529a8() {
        if (mo213919a5()) {
            return;
        }
        ArrayList arrayList = this.f58134a1;
        if (arrayList.size() <= 0) {
            arrayList.clear();
        } else {
            arrayList.get(0).getClass();
            throw new ClassCastException();
        }
    }

    /* renamed from: a9 */
    public final void m214530a9(ArrayList arrayList, dr0 dr0Var) {
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            C1174rb c1174rb = (C1174rb) arrayList.get(size);
            if (m214531b0(c1174rb, dr0Var) && c1174rb.f59660a0 == null && c1174rb.f59661a1 == null) {
                arrayList.remove(c1174rb);
            }
        }
    }

    /* renamed from: b0 */
    public final boolean m214531b0(C1174rb c1174rb, dr0 dr0Var) {
        if (c1174rb.f59661a1 == dr0Var) {
            c1174rb.f59661a1 = null;
        } else {
            if (c1174rb.f59660a0 != dr0Var) {
                return false;
            }
            c1174rb.f59660a0 = null;
        }
        View view = dr0Var.f55849a0;
        View view2 = dr0Var.f55849a0;
        view.setAlpha(1.0f);
        view2.setTranslationX(0.0f);
        view2.setTranslationY(0.0f);
        m213916a2(dr0Var);
        return true;
    }

    /* renamed from: b1 */
    public final void m214532b1(dr0 dr0Var) {
        if (f59673b8 == null) {
            f59673b8 = new ValueAnimator().getInterpolator();
        }
        dr0Var.f55849a0.animate().setInterpolator(f59673b8);
        mo213917a3(dr0Var);
    }
}
