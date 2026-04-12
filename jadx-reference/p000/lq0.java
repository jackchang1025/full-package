package p000;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class lq0 {

    /* renamed from: a0 */
    public fq0 f58133a0;

    /* renamed from: a1 */
    public ArrayList f58134a1;

    /* renamed from: a2 */
    public long f58135a2;

    /* renamed from: a3 */
    public long f58136a3;

    /* renamed from: a4 */
    public long f58137a4;

    /* renamed from: a5 */
    public long f58138a5;

    /* renamed from: a1 */
    public static void m213914a1(dr0 dr0Var) {
        RecyclerView recyclerView;
        int i = dr0Var.f55858a9;
        if (dr0Var.m212625a5() || (i & 4) != 0 || (recyclerView = dr0Var.f55866b7) == null) {
            return;
        }
        recyclerView.m210371d2(dr0Var);
    }

    /* renamed from: a0 */
    public abstract boolean mo213915a0(dr0 dr0Var, dr0 dr0Var2, fj0 fj0Var, fj0 fj0Var2);

    /* renamed from: a2 */
    public final void m213916a2(dr0 dr0Var) {
        fq0 fq0Var = this.f58133a0;
        if (fq0Var != null) {
            RecyclerView recyclerView = fq0Var.f56313a0;
            boolean z = true;
            dr0Var.m212633b3(true);
            View view = dr0Var.f55849a0;
            if (dr0Var.f55856a7 != null && dr0Var.f55857a8 == null) {
                dr0Var.f55856a7 = null;
            }
            dr0Var.f55857a8 = null;
            if ((dr0Var.f55858a9 & 16) != 0) {
                return;
            }
            vq0 vq0Var = recyclerView.f45255a1;
            recyclerView.m210392f4();
            pg1 pg1Var = recyclerView.f45258a4;
            C0583hj c0583hj = (C0583hj) pg1Var.f59230a2;
            fq0 fq0Var2 = (fq0) pg1Var.f59229a1;
            int iIndexOfChild = fq0Var2.f56313a0.indexOfChild(view);
            if (iIndexOfChild == -1) {
                pg1Var.m214290d6(view);
            } else if (c0583hj.m213043a3(iIndexOfChild)) {
                c0583hj.m213045a5(iIndexOfChild);
                pg1Var.m214290d6(view);
                fq0Var2.m212853a7(iIndexOfChild);
            } else {
                z = false;
            }
            if (z) {
                dr0 dr0VarM210345d5 = RecyclerView.m210345d5(view);
                vq0Var.m214947a9(dr0VarM210345d5);
                vq0Var.m214944a6(dr0VarM210345d5);
            }
            recyclerView.m210393f5(!z);
            if (z || !dr0Var.m212629a9()) {
                return;
            }
            recyclerView.removeDetachedView(view, false);
        }
    }

    /* renamed from: a3 */
    public abstract void mo213917a3(dr0 dr0Var);

    /* renamed from: a4 */
    public abstract void mo213918a4();

    /* renamed from: a5 */
    public abstract boolean mo213919a5();
}
