package p000;

import android.view.View;
import android.view.ViewPropertyAnimator;
import java.util.ArrayList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: qx */
/* loaded from: classes.dex */
public final class RunnableC1159qx implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f59558a0;

    /* renamed from: a1 */
    public final /* synthetic */ ArrayList f59559a1;

    /* renamed from: a2 */
    public final /* synthetic */ C1176rd f59560a2;

    public /* synthetic */ RunnableC1159qx(C1176rd c1176rd, ArrayList arrayList, int i) {
        this.f59558a0 = i;
        this.f59560a2 = c1176rd;
        this.f59559a1 = arrayList;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.f59558a0) {
            case 0:
                ArrayList arrayList = this.f59559a1;
                int size = arrayList.size();
                int i = 0;
                while (true) {
                    C1176rd c1176rd = this.f59560a2;
                    if (i >= size) {
                        arrayList.clear();
                        c1176rd.f59680b2.remove(arrayList);
                        break;
                    } else {
                        Object obj = arrayList.get(i);
                        i++;
                        C1175rc c1175rc = (C1175rc) obj;
                        dr0 dr0Var = c1175rc.f59668a0;
                        int i2 = c1175rc.f59669a1;
                        int i3 = c1175rc.f59670a2;
                        int i4 = c1175rc.f59671a3;
                        int i5 = c1175rc.f59672a4;
                        c1176rd.getClass();
                        View view = dr0Var.f55849a0;
                        int i6 = i4 - i2;
                        int i7 = i5 - i3;
                        if (i6 != 0) {
                            view.animate().translationX(0.0f);
                        }
                        if (i7 != 0) {
                            view.animate().translationY(0.0f);
                        }
                        ViewPropertyAnimator viewPropertyAnimatorAnimate = view.animate();
                        c1176rd.f59683b5.add(dr0Var);
                        viewPropertyAnimatorAnimate.setDuration(c1176rd.f58137a4).setListener(new C1161qz(c1176rd, dr0Var, i6, view, i7, viewPropertyAnimatorAnimate)).start();
                    }
                }
            case 1:
                ArrayList arrayList2 = this.f59559a1;
                int size2 = arrayList2.size();
                int i8 = 0;
                while (true) {
                    C1176rd c1176rd2 = this.f59560a2;
                    if (i8 >= size2) {
                        arrayList2.clear();
                        c1176rd2.f59681b3.remove(arrayList2);
                        break;
                    } else {
                        Object obj2 = arrayList2.get(i8);
                        i8++;
                        C1174rb c1174rb = (C1174rb) obj2;
                        ArrayList arrayList3 = c1176rd2.f59685b7;
                        long j = c1176rd2.f58138a5;
                        dr0 dr0Var2 = c1174rb.f59660a0;
                        View view2 = dr0Var2 == null ? null : dr0Var2.f55849a0;
                        dr0 dr0Var3 = c1174rb.f59661a1;
                        View view3 = dr0Var3 != null ? dr0Var3.f55849a0 : null;
                        if (view2 != null) {
                            ViewPropertyAnimator duration = view2.animate().setDuration(j);
                            arrayList3.add(c1174rb.f59660a0);
                            duration.translationX(c1174rb.f59664a4 - c1174rb.f59662a2);
                            duration.translationY(c1174rb.f59665a5 - c1174rb.f59663a3);
                            duration.alpha(0.0f).setListener(new C1173ra(c1176rd2, c1174rb, duration, view2, 0)).start();
                        }
                        if (view3 != null) {
                            ViewPropertyAnimator viewPropertyAnimatorAnimate2 = view3.animate();
                            arrayList3.add(c1174rb.f59661a1);
                            viewPropertyAnimatorAnimate2.translationX(0.0f).translationY(0.0f).setDuration(j).alpha(1.0f).setListener(new C1173ra(c1176rd2, c1174rb, viewPropertyAnimatorAnimate2, view3, 1)).start();
                        }
                    }
                }
            default:
                ArrayList arrayList4 = this.f59559a1;
                int size3 = arrayList4.size();
                int i9 = 0;
                while (true) {
                    C1176rd c1176rd3 = this.f59560a2;
                    if (i9 >= size3) {
                        arrayList4.clear();
                        c1176rd3.f59679b1.remove(arrayList4);
                        break;
                    } else {
                        Object obj3 = arrayList4.get(i9);
                        i9++;
                        dr0 dr0Var4 = (dr0) obj3;
                        c1176rd3.getClass();
                        View view4 = dr0Var4.f55849a0;
                        ViewPropertyAnimator viewPropertyAnimatorAnimate3 = view4.animate();
                        c1176rd3.f59682b4.add(dr0Var4);
                        viewPropertyAnimatorAnimate3.alpha(1.0f).setDuration(c1176rd3.f58135a2).setListener(new C1160qy(c1176rd3, dr0Var4, view4, viewPropertyAnimatorAnimate3)).start();
                    }
                }
        }
    }
}
